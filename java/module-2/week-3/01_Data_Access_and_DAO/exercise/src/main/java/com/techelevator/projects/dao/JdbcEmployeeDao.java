package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Employee;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcEmployeeDao implements EmployeeDao {

	private final JdbcTemplate jdbcTemplate;

	public JdbcEmployeeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			employees.add(mapRowToEmployee(results));
		}
		return employees;
	}

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> searchEmployees = new ArrayList<>();
		String sql = "SELECT employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee " +
				"WHERE first_name ILIKE ? AND last_name ILIKE ?;";
		firstNameSearch = "%" + firstNameSearch + "%";
		lastNameSearch = "%" + lastNameSearch + "%";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, firstNameSearch, lastNameSearch);
		while (results.next()) {
			searchEmployees.add(mapRowToEmployee(results));
		}
		return searchEmployees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(int projectId) {
		List<Employee> projectEmployees = new ArrayList<>();
		String sql = "SELECT employee.employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee " +
				"JOIN project_employee ON employee.employee_id = project_employee.employee_id " +
				"WHERE project_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
		while (results.next()) {
			projectEmployees.add(mapRowToEmployee(results));
		}
		return projectEmployees;
	}

	@Override
	public void addEmployeeToProject(int projectId, int employeeId) {
		String addEmployeeSql = "INSERT INTO project_employee (project_id, employee_id) VALUES (?, ?) RETURNING project_id;";
		projectId = jdbcTemplate.queryForObject(addEmployeeSql, int.class, projectId, employeeId);
	}

	@Override
	public void removeEmployeeFromProject(int projectId, int employeeId) {
		String removeEmployeeSql = "DELETE FROM project_employee WHERE project_id = ? AND employee_id = ?;";
		int numberOfRowsDeleted = jdbcTemplate.update(removeEmployeeSql, projectId, employeeId);
		if (numberOfRowsDeleted == 1) {
			System.out.println("Employee removed from project.");
		}
		else {
			System.out.println("Employee removal failed.");
		}
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> employeesWithoutProjects = new ArrayList<>();
		List<Integer> employees = new ArrayList<>();
		List<Integer> employeesWithProjects = new ArrayList<>();
		List<Integer> employeeIdWithoutProjects = new ArrayList<>();
		String sqlAllEmployees = "SELECT employee.employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllEmployees);
		while (results.next()) {
			employees.add(results.getInt("employee_id"));
		}

		String sqlEmployeesWithProjects = "SELECT employee.employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee " +
				"FULL JOIN project_employee ON employee.employee_id = project_employee.employee_id " +
				"WHERE project_id > 0;";
		SqlRowSet projectResults = jdbcTemplate.queryForRowSet(sqlEmployeesWithProjects);
		while (projectResults.next()) {
			 employeesWithProjects.add(projectResults.getInt("employee_id"));
		}
		for (Integer employeeId : employees) {
			if (employeesWithProjects.contains(employeeId)) {

			}
			else {
				employeeIdWithoutProjects.add(employeeId);
			}
		}
		String sqlEmployeesWithoutProjects = "SELECT employee.employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee WHERE employee_id = ?;";
		for (Integer employeeId : employeeIdWithoutProjects) {
			SqlRowSet noProjectResults = jdbcTemplate.queryForRowSet(sqlEmployeesWithoutProjects, employeeId);
			while (noProjectResults.next()) {
				employeesWithoutProjects.add(mapRowToEmployee(noProjectResults));
			}
		}
		return employeesWithoutProjects;
	}

	private Employee mapRowToEmployee(SqlRowSet rowSet) {
		Employee employee = new Employee();

		employee.setId(rowSet.getInt("employee_id"));
		employee.setDepartmentId(rowSet.getInt("department_id"));
		employee.setFirstName(rowSet.getString("first_name"));
		employee.setLastName(rowSet.getString("last_name"));
		employee.setBirthDate(rowSet.getDate("birth_date").toLocalDate());
		employee.setHireDate(rowSet.getDate("hire_date").toLocalDate());

		return employee;
	}

}
