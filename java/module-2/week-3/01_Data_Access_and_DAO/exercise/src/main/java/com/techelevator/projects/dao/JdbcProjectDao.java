package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.projects.model.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Project;

public class JdbcProjectDao implements ProjectDao {

	private final JdbcTemplate jdbcTemplate;

	public JdbcProjectDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Project getProject(int projectId) {
		Project project = null;
		String sql = "SELECT project_id, name, from_date, to_date " +
				"FROM project " +
				"WHERE project_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
		if (results.next()) {
			project = mapRowToProject(results);
		}
		return project;
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();
		String sql = "SELECT project_id, name, from_date, to_date FROM project ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			projects.add(mapRowToProject(results));
		}
		return projects;
	}

	@Override
	public Project createProject(Project newProject) {
		Project project = newProject;
		String createProjectSql = "INSERT INTO project (name, from_date, to_date) VALUES (?, ?, ?) RETURNING project_id";
		int projectId = jdbcTemplate.queryForObject(createProjectSql, int.class, newProject.getName(), newProject.getFromDate(), newProject.getToDate());
		newProject.setId(projectId);
		return newProject;
	}

	@Override
	public void deleteProject(int projectId) {
		String deleteProjectEmployeeSql = "DELETE FROM project_employee WHERE project_id = ?";
		int numberOfRowsDeleted = jdbcTemplate.update(deleteProjectEmployeeSql, projectId);
		String deleteTimeSheetSql = "DELETE FROM timesheet WHERE project_id = ?";
		numberOfRowsDeleted = jdbcTemplate.update(deleteTimeSheetSql, projectId);
		String deleteProjectSql = "DELETE FROM project WHERE project_id = ?";
		numberOfRowsDeleted = jdbcTemplate.update(deleteProjectSql, projectId);
		if (numberOfRowsDeleted == 1) {
			System.out.println("Project was safely deleted");
		}
		else {
			System.out.println("Project delete failed");
		}

	}

	private Project mapRowToProject(SqlRowSet rowSet) {
		Project project = new Project();

		project.setId(rowSet.getInt("project_id"));
		project.setName(rowSet.getString("name"));
		project.setFromDate(null);
		if (rowSet.getDate("from_date") != null) {
			project.setFromDate(rowSet.getDate("from_date").toLocalDate());
		}
		project.setToDate(null);
		if (rowSet.getDate("to_date") != null) {
			project.setToDate(rowSet.getDate("to_date").toLocalDate());
		}

		return project;
	}
	

}
