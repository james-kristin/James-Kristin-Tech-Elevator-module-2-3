package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.model.Bookmark;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * The JdbcBookmarkDao class is used for interacting with the bookmark information in the datastore.
 *
 * While the DAO pattern allows us to encapsulate and abstract interactions with a data store,
 * this implementation class is specifically used to access data from a SQL database using JDBC.
 *
 * This DAO supports full CRUD (Create, Read, Update, Delete) for Bookmarks and also allows
 * the addition and deletion of Tag associations. Bookmarks are always associated to a single User.
 */
@Component
public class JdbcBookmarkDao implements BookmarkDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcBookmarkDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Bookmark getById(int bookmarkId) {
        Bookmark bookmark = null;
        
        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE bookmark.bookmark_id = ? " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookmarkId);
        if (results.next()) {
            bookmark = mapRowToBookmark(results);
        }

        return bookmark;

    }

    @Override
    public List<Bookmark> getAll() {
        List<Bookmark> bookmarks = new ArrayList<>();
        
        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Bookmark bookmark = mapRowToBookmark(results);
            bookmarks.add(bookmark);
        }

        return bookmarks;
    }


    @Override
    public List<Bookmark> getByUserId(int userId) {
        List<Bookmark> bookmarks = new ArrayList<>();

        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE bookmark.user_id = ? " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Bookmark bookmark = mapRowToBookmark(results);
            bookmarks.add(bookmark);
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> getPublicByUserId(int userId) {
        List<Bookmark> bookmarks = new ArrayList<>();

        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE is_public AND bookmark.user_id = ? " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Bookmark bookmark = mapRowToBookmark(results);
            bookmarks.add(bookmark);
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> getPublic() {
        List<Bookmark> bookmarks = new ArrayList<>();

        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE is_public " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Bookmark bookmark = mapRowToBookmark(results);
            bookmarks.add(bookmark);
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> getFlagged() {
        List<Bookmark> bookmarks = new ArrayList<>();

        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE is_flagged " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Bookmark bookmark = mapRowToBookmark(results);
            bookmarks.add(bookmark);
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> filter(String filter, boolean publicOnly) {
        List<Bookmark> bookmarks = new ArrayList<>();

        // Set up the WHERE to optionally filter for public bookmarks only
        String sqlWhere = publicOnly ? "WHERE is_public " : "";

        String sql = "Select * from (" +
                "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                sqlWhere +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title" +
            ") AS bookmarks " +
            "WHERE (title ILIKE ? OR description ILIKE ? OR all_tags ILIKE ? OR display_name ILIKE ?);";

        // add % to the filter string to sub into the query
        String filterString = '%' + filter + '%';
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, filterString, filterString, filterString, filterString);

        while (results.next()) {
            Bookmark bookmark = mapRowToBookmark(results);
            bookmarks.add(bookmark);
        }

        return bookmarks;
    }

    @Override
    public Bookmark create(Bookmark newBookmark) {
        Integer id;
        if (newBookmark.getCreateDate() == null) {
            String sql = "INSERT INTO bookmark (user_id, title, url, description, is_public) VALUES" +
                    "(?, ?, ?, ?, ?) RETURNING bookmark_id;";
            id = jdbcTemplate.queryForObject(sql, Integer.class, newBookmark.getUserId(), newBookmark.getTitle(),
                    newBookmark.getUrl(), newBookmark.getDescription(), newBookmark.isPublic());
        } else {
            String sql = "INSERT INTO bookmark (user_id, title, url, description, is_public, create_date) VALUES" +
                    "(?, ?, ?, ?, ?, ?) RETURNING bookmark_id;";
            id = jdbcTemplate.queryForObject(sql, Integer.class, newBookmark.getUserId(), newBookmark.getTitle(),
                    newBookmark.getUrl(), newBookmark.getDescription(), newBookmark.isPublic(), newBookmark.getCreateDate());
        }
        return getById(id);
    }

    @Override
    public int delete(int bookmarkId) {
        // Delete associations to Tags, then delete Bookmark
        // Note - Multiple statements in a single batch are automatically wrapped in a transaction -
        // these two statements will either succeed or fail as a unit.
        int count = jdbcTemplate.update("DELETE FROM bookmark_tag WHERE bookmark_id=?", bookmarkId);
        count += jdbcTemplate.update("DELETE FROM bookmark WHERE bookmark_id=?;", bookmarkId);
        return count;
    }

    @Override
    public Bookmark update(Bookmark modifiedBookmark) {
        String sql = "UPDATE bookmark SET title=?, url=?, description=?, is_public=?, is_flagged=? WHERE bookmark_id=?;";
        jdbcTemplate.update(sql, modifiedBookmark.getTitle(), modifiedBookmark.getUrl(), modifiedBookmark.getDescription(),
                modifiedBookmark.isPublic(), modifiedBookmark.isFlagged(), modifiedBookmark.getBookmarkId());
        return getById(modifiedBookmark.getBookmarkId());
    }

    @Override
    public int addTagToBookmark(int bookmarkId, int tagId){
        String sql = "INSERT INTO bookmark_tag (bookmark_id, tag_id) VALUES (?, ?);";
        int count = jdbcTemplate.update(sql, bookmarkId, tagId);
        return count;
    }

    @Override
    public int removeTagFromBookmark(int bookmarkId, int tagId){
        String sql = "DELETE FROM bookmark_tag WHERE bookmark_id=? AND tag_id=?;";
        int count = jdbcTemplate.update(sql, bookmarkId, tagId);
        return count;
    }

    /*
     * Helper method to convert a SqlRowSet into a Bookmark object.
     */
    private Bookmark mapRowToBookmark(SqlRowSet rs) {
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(rs.getInt("bookmark_id"));
        bookmark.setUserId(rs.getInt("user_id"));
        bookmark.setUserDisplayName(rs.getString("display_name"));
        bookmark.setUrl(rs.getString("url"));
        bookmark.setTitle(rs.getString("title"));
        bookmark.setDescription(rs.getString("description"));
        bookmark.setCreateDate(rs.getDate("create_date").toLocalDate());
        bookmark.setPublic(rs.getBoolean("is_public"));
        bookmark.setFlagged(rs.getBoolean("is_flagged"));
        bookmark.setAllTags(rs.getString("all_tags"));
        return bookmark;
    }
}
