package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.model.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * The JdbcTagDao class is used for interacting with the tag information in the datastore.
 *
 * While the DAO pattern allows us to encapsulate and abstract interactions with a data store,
 * this implementation class is specifically used to access data from a SQL database using JDBC.
 *
 * This DAO supports full CRUD (Create, Read, Update, Delete) for Tags.
 * Tags are associated to zero or more Bookmarks.
 */
@Component
public class JdbcTagDao implements TagDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTagDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Tag> getAll() {
        List<Tag> allTags = new ArrayList<>();
        String sql = "SELECT tag_id, name FROM tag ORDER BY name;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Tag tag = mapRowToTag(results);
            allTags.add(tag);
        }

        return allTags;
    }

    @Override
    public Tag getById(int tagId) {
        Tag tag = null;
        String sql = "SELECT tag_id, name FROM tag WHERE tag_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tagId);
        if (results.next()) {
            tag = mapRowToTag(results);
        }

        return tag;
    }


    @Override
    public List<Tag> getTagsByBookmarkId(int bookmarkId) {
        List<Tag> tagList = new ArrayList<>();
        String sql = "SELECT tag.tag_id, name FROM tag " +
                "JOIN bookmark_tag on bookmark_tag.tag_id = tag.tag_id " +
                "WHERE bookmark_tag.bookmark_id = ? " +
                "ORDER BY name;";


        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookmarkId);
        while (results.next()) {
            Tag tag = mapRowToTag(results);
            tagList.add(tag);
        }

        return tagList;
    }

    @Override
    public Tag create(Tag newTag) {
        String sql = "INSERT INTO tag (name) VALUES (?) RETURNING tag_id;";
        Integer tagId = jdbcTemplate.queryForObject(sql, Integer.class, newTag.getName());
        return getById(tagId);
    }

    @Override
    public Tag update(Tag tag) {
        String sql = "UPDATE tag SET name = ? WHERE tag_id=?;";
        jdbcTemplate.update(sql, tag.getName(), tag.getId());
        return getById(tag.getId());
    }

    @Override
    public void delete(int tagId) {
        String sql = "DELETE FROM tag WHERE tag_id=?;";
        jdbcTemplate.update(sql, tagId);
    }

    /*
     * Helper method to convert a SqlRowSet into a Tag object.
     */
    private Tag mapRowToTag(SqlRowSet rs) {
        Tag tag = new Tag();
        tag.setId(rs.getInt("tag_id"));
        tag.setName(rs.getString("name"));
        return tag;
    }
}
