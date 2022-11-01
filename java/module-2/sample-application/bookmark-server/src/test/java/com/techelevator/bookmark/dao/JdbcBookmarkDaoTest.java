package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.model.Bookmark;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class JdbcBookmarkDaoTest extends BaseDaoTests {

    private static final int USER_ID_ADMIN = 100;
    private static final int USER_ID_1 = 101;
    private static final int USER_ID_2 = 102;

    private static final Bookmark BOOKMARK_1 =
            new Bookmark(300, 101, "User 1 - Jo Tagolia", "User 1 - A", "https://www.tester.com", "Test Home.", false, false, "2020-01-20", "Tag 1, Tag 2, Tag 3");
    private static final Bookmark BOOKMARK_2 =
            new Bookmark(301, 102, "User 2", "User 2 - B", "https://www.tester.com", "User 2 - Test Home.", false, false, "2020-01-21", "Tag 3");
    private static final Bookmark BOOKMARK_3 =
            new Bookmark(302, 101, "User 1 - Jo Tagolia", "User 1 - C - public, flagged", "https://www.test.com/sub1/", null, true, true, "2020-01-21", null);
    private static final Bookmark BOOKMARK_4 =
            new Bookmark(303, 101, "User 1 - Jo Tagolia", "User 1 - B - public", "https://fake.org/", "Fake test site", true, false, "2020-01-22", null);
    private static final Bookmark BOOKMARK_5 =
            new Bookmark(304, 102, "User 2", "User 2 - A - public, flagged", "https://www.test.com/sub2/", null, true, true, "2020-01-22", "Tag 1, Tag 3");

    private static final List<Bookmark> ALL_BOOKMARKS = Arrays.asList(new Bookmark[] {BOOKMARK_1, BOOKMARK_4, BOOKMARK_3, BOOKMARK_5, BOOKMARK_2});

    private JdbcBookmarkDao dao;

    @Before
    public void setup() {
        dao = new JdbcBookmarkDao(dataSource);
    }

    @Test
    public void getById_returns_correct_bookmark() {
        Bookmark result = dao.getById(BOOKMARK_1.getBookmarkId());
        Assert.assertNotNull("getById returned null", result);
        assertBookmarksMatch("getById returned wrong or partial data", BOOKMARK_1, result);
    }

    @Test
    public void getById_returns_null_when_id_not_found() {
        Bookmark result = dao.getById(9999);
        Assert.assertNull("getById did not return null when bookmark id is not found", result);
    }

    @Test
    public void getAll_returns_correct_list_sorted_by_title() {
        List<Bookmark> result = dao.getAll();
        assertBookmarkListsMatch("getAll returned wrong or partial data", ALL_BOOKMARKS, result);
    }

    @Test
    public void getByUserId_returns_correct_list_sorted_by_title() {
        List<Bookmark> USER_1_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getUserId() == USER_ID_1;
        }).collect(Collectors.toList());

        List<Bookmark> user1Bookmarks = dao.getByUserId(USER_ID_1);
        assertBookmarkListsMatch("getByUserId returned wrong or partial data for user 1", USER_1_BOOKMARKS, user1Bookmarks);

        List<Bookmark> USER_2_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getUserId() == USER_ID_2;
        }).collect(Collectors.toList());

        List<Bookmark> user2Bookmarks = dao.getByUserId(USER_ID_2);
        assertBookmarkListsMatch("getByUserId returned wrong or partial data for user 2", USER_2_BOOKMARKS, user2Bookmarks);

        List<Bookmark> adminBookmarks = dao.getByUserId(USER_ID_ADMIN);
        assertBookmarkListsMatch("getByUserId returned wrong or partial data for admin user", new ArrayList<Bookmark>(), adminBookmarks);
    }

    @Test
    public void getPublicByUserId_returns_correct_list_sorted_by_title() {
        List<Bookmark> USER_1_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getUserId() == USER_ID_1 && item.isPublic();
        }).collect(Collectors.toList());

        List<Bookmark> user1Bookmarks = dao.getPublicByUserId(USER_ID_1);
        assertBookmarkListsMatch("getByUserId returned wrong or partial data for user 1", USER_1_BOOKMARKS, user1Bookmarks);

        List<Bookmark> USER_2_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getUserId() == USER_ID_2 && item.isPublic();
        }).collect(Collectors.toList());

        List<Bookmark> user2Bookmarks = dao.getPublicByUserId(USER_ID_2);
        assertBookmarkListsMatch("getByUserId returned wrong or partial data for user 2", USER_2_BOOKMARKS, user2Bookmarks);

        List<Bookmark> adminBookmarks = dao.getPublicByUserId(USER_ID_ADMIN);
        assertBookmarkListsMatch("getByUserId returned wrong or partial data for admin user", new ArrayList<Bookmark>(), adminBookmarks);
    }

    @Test
    public void getPublic_returns_correct_list_sorted_by_title() {
        List<Bookmark> PUBLIC_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isPublic();
        }).collect(Collectors.toList());

        List<Bookmark> result = dao.getPublic();
        assertBookmarkListsMatch("getPublic returned wrong or partial data", PUBLIC_BOOKMARKS, result);

    }

    @Test
    public void getFlagged_returns_correct_list_sorted_by_title() {
        List<Bookmark> FLAGGED_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isFlagged();
        }).collect(Collectors.toList());

        List<Bookmark> result = dao.getFlagged();
        assertBookmarkListsMatch("getFlagged returned wrong or partial data", FLAGGED_BOOKMARKS, result);
    }

    @Test
    public void filter_all_returns_correct_list_sorted_by_title() {
        // Try something in description
        String filterString = "test";
        List<Bookmark> expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getTitle().toLowerCase().contains(filterString.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString.toLowerCase()));
        }).collect(Collectors.toList());

        List<Bookmark> actual = dao.filter(filterString, false);
        assertBookmarkListsMatch("filter returned wrong or partial data", expected, actual);

        // Repeat with something in tags and display name
        String filterString2 = "tag";
        expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getTitle().toLowerCase().contains(filterString2.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString2.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString2.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString2.toLowerCase()));
        }).collect(Collectors.toList());

        actual = dao.filter(filterString2, false);
        assertBookmarkListsMatch("filter returned wrong or partial data", expected, actual);

        // Repeat with something in title, description, and display name
        String filterString3 = "user 2";
        expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getTitle().toLowerCase().contains(filterString3.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString3.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString3.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString3.toLowerCase()));
        }).collect(Collectors.toList());

        actual = dao.filter(filterString3, false);
        assertBookmarkListsMatch("filter returned wrong or partial data", expected, actual);
    }

    @Test
    public void filter_public_returns_correct_list_sorted_by_title() {
        // Try something in description
        String filterString = "test";
        List<Bookmark> expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isPublic() && (item.getTitle().toLowerCase().contains(filterString.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString.toLowerCase())));
        }).collect(Collectors.toList());

        List<Bookmark> actual = dao.filter(filterString, true);
        assertBookmarkListsMatch("filter returned wrong or partial data", expected, actual);

        // Repeat with something in tags and display name
        String filterString2 = "tag";
        expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isPublic() && (item.getTitle().toLowerCase().contains(filterString2.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString2.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString2.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString2.toLowerCase())));
        }).collect(Collectors.toList());

        actual = dao.filter(filterString2, true);
        assertBookmarkListsMatch("filter returned wrong or partial data", expected, actual);

        // Repeat with something in title, description, and display name
        String filterString3 = "user 2";
        expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isPublic() && (item.getTitle().toLowerCase().contains(filterString3.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString3.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString3.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString3.toLowerCase())));
        }).collect(Collectors.toList());

        actual = dao.filter(filterString3, true);
        assertBookmarkListsMatch("filter returned wrong or partial data", expected, actual);
    }

    @Test
    public void create_returns_newBookmark_with_id_and_correct_values() {
        Bookmark testBookmark = new Bookmark();
        testBookmark.setUserId(BOOKMARK_1.getUserId());
        testBookmark.setTitle("New Test Bookmark");
        testBookmark.setUrl("http://test.com");
        testBookmark.setDescription("Test Bookmark 1 Description");
        testBookmark.setPublic(true);
        // Cannot flag a bookmark on creation
        testBookmark.setCreateDate(LocalDate.parse("2020-01-01"));

        Bookmark result = dao.create(testBookmark);
        Assert.assertNotNull("create returned null", result);

        int newId = result.getBookmarkId();
        Assert.assertTrue("create returned a bookmark without an id", newId > 0);

        // Update the bookmarkId and user display name to expected values (not sent in with create data)
        testBookmark.setBookmarkId(newId);
        testBookmark.setUserDisplayName(BOOKMARK_1.getUserDisplayName());
        assertBookmarksMatch("create returned wrong or partial data", testBookmark, result);
    }

    @Test
    public void created_bookmark_has_expected_values_when_retrieved() {
        Bookmark testBookmark = new Bookmark();
        testBookmark.setUserId(BOOKMARK_1.getUserId());
        testBookmark.setTitle("New Test Bookmark");
        testBookmark.setUrl("http://test.com");
        testBookmark.setDescription("Test Bookmark 1 Description");
        testBookmark.setPublic(true);
        // Cannot flag a bookmark on creation
        testBookmark.setCreateDate(LocalDate.parse("2020-01-01"));

        Bookmark createdBookmark = dao.create(testBookmark);
        int newId = createdBookmark.getBookmarkId();
        Bookmark retrievedBookmark = dao.getById(newId);

        assertBookmarksMatch("create did not save correct data in database", createdBookmark, retrievedBookmark);
    }

    @Test
    public void update_returns_bookmark_with_correct_values() {
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(BOOKMARK_2.getBookmarkId());

        // Don't change non-updatable fields - tested separately
        bookmark.setUserId(BOOKMARK_2.getUserId());
        bookmark.setUserDisplayName(BOOKMARK_2.getUserDisplayName());
        bookmark.setCreateDate(BOOKMARK_2.getCreateDate());
        bookmark.setAllTags(BOOKMARK_2.getAllTags());

        // Change updatable fields
        bookmark.setTitle("Modified Bookmark");
        bookmark.setUrl("http://updated.com");
        bookmark.setDescription("Modified Description");
        bookmark.setPublic(!BOOKMARK_2.isPublic());
        bookmark.setFlagged(!BOOKMARK_2.isFlagged());

        Bookmark result = dao.update(bookmark);
        assertBookmarksMatch("update returned wrong or partial data", bookmark, result);
    }

    @Test
    public void update_does_not_modify_userId_createDate_allTags() {
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(BOOKMARK_2.getBookmarkId());

        // Don't change updatable fields - tested separately
        bookmark.setTitle(BOOKMARK_2.getTitle());
        bookmark.setUrl(BOOKMARK_2.getUrl());
        bookmark.setDescription(BOOKMARK_2.getDescription());
        bookmark.setPublic(BOOKMARK_2.isPublic());
        bookmark.setFlagged(BOOKMARK_2.isFlagged());

        // Try to modify data that must not be updatable
        bookmark.setUserId(9999);
        bookmark.setUserDisplayName("Foo");
        bookmark.setCreateDate(LocalDate.parse("1900-01-01"));
        bookmark.setAllTags("Updated");

        Bookmark result = dao.update(bookmark);
        assertBookmarksMatch("update returned wrong or partial data ", BOOKMARK_2, result);
    }

    @Test
    public void updated_bookmark_has_expected_values_when_retrieved() {
        Bookmark bookmark = new Bookmark();
        // Set data that cannot change from original
        bookmark.setBookmarkId(BOOKMARK_2.getBookmarkId());

        // Change updatable fields
        bookmark.setTitle("Modified Bookmark");
        bookmark.setUrl("http://updated.com");
        bookmark.setDescription("Modified Description");
        bookmark.setPublic(!BOOKMARK_2.isPublic());

        // Modify data that must not be updatable
        bookmark.setUserId(9999);
        bookmark.setFlagged(!BOOKMARK_2.isFlagged());
        bookmark.setCreateDate(LocalDate.parse("1900-01-01"));
        bookmark.setAllTags("Updated");

        Bookmark updatedBookmark = dao.update(bookmark);
        int newId = updatedBookmark.getBookmarkId();
        Bookmark retrievedBookmark = dao.getById(newId);

        assertBookmarksMatch("update did not save correct data in database", updatedBookmark, retrievedBookmark);
    }

    @Test
    public void delete_removes_bookmark_with_no_tags() {
        dao.delete(BOOKMARK_4.getBookmarkId());
        Bookmark bookmark = dao.getById(BOOKMARK_4.getBookmarkId());
        Assert.assertNull("delete failed to removed bookmark from database", bookmark);

        List<Bookmark> allBookmarks = dao.getAll();
        Assert.assertEquals("delete removed the wrong number of bookmarks", ALL_BOOKMARKS.size()-1, allBookmarks.size());
    }

    @Test
    public void delete_removes_bookmark_and_tag_associations() {
        dao.delete(BOOKMARK_1.getBookmarkId());
        Bookmark bookmark = dao.getById(BOOKMARK_1.getBookmarkId());
        Assert.assertNull(bookmark);

        List<Bookmark> allBookmarks = dao.getAll();
        Assert.assertEquals("delete removed the wrong number of bookmarks", ALL_BOOKMARKS.size()-1, allBookmarks.size());
    }

    @Test
    public void delete_does_not_error_when_id_not_found() {
        try {
            dao.delete(9999);
            List<Bookmark> allBookmarks = dao.getAll();
            Assert.assertEquals("no bookmarks should have been deleted", ALL_BOOKMARKS.size(), allBookmarks.size());
        } catch (Exception e) {
            Assert.fail("Delete should not error when not found.");
        }
    }

    @Test
    public void addTagToBookmark_correctly_adds_association() {
        // Check tags are as expected...
        String currentValue = "Tag 1, Tag 3";
        Assert.assertEquals("Check test data, expecting test bookmark to have Tags 1 and 3",
                currentValue, BOOKMARK_5.getAllTags());
        // Add Test Tag 2 with id 201
        dao.addTagToBookmark(BOOKMARK_5.getBookmarkId(), 201);
        Bookmark updated = dao.getById(BOOKMARK_5.getBookmarkId());
        Assert.assertEquals("Tag 2 was not added correctly. allTags field does not match expected.", "Tag 1, Tag 2, Tag 3", updated.getAllTags());
    }

    @Test
    public void removeTagFromBookmark_correctly_removes_association() {
        // Check tags are as expected...
        String currentValue = "Tag 1, Tag 3";
        Assert.assertEquals("Check test data, expecting test bookmark to have Tags 1 and 3",
                currentValue, BOOKMARK_5.getAllTags());
        // Remove Test Tag 1 with id 202
        dao.removeTagFromBookmark(BOOKMARK_5.getBookmarkId(), 202);
        Bookmark updated = dao.getById(BOOKMARK_5.getBookmarkId());
        Assert.assertEquals("Tag 1 was not removed correctly. allTags field does not match expected.", "Tag 3", updated.getAllTags());
    }

    private void assertBookmarksMatch(String messagePrefix, Bookmark expected, Bookmark actual) {
        if (expected == null) {
            Assert.assertNull(messagePrefix + ": value should be null", actual);
        } else {
            Assert.assertNotNull(messagePrefix + ": value should not be null", actual);
            Assert.assertEquals(String.format("%s Bookmark bookmark_id field does not match expected value.", messagePrefix),
                    expected.getBookmarkId(), actual.getBookmarkId());

            String message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "user_id");
            Assert.assertEquals(message, expected.getUserId(), actual.getUserId());

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "user_display_name");
            Assert.assertEquals(message, expected.getUserDisplayName(), actual.getUserDisplayName());

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "title");
            Assert.assertEquals(message, expected.getTitle(), actual.getTitle());

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "url");
            Assert.assertEquals(message, expected.getUrl(), actual.getUrl());

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "description");
            Assert.assertEquals(message, expected.getDescription(), actual.getDescription());

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "isPublic");
            Assert.assertEquals(message, expected.isPublic(), actual.isPublic());

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "isFlagged");
            Assert.assertEquals(message, expected.isFlagged(), actual.isFlagged());

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "create_date");
            Assert.assertEquals(message, expected.getCreateDate(), actual.getCreateDate());

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "allTags");
            Assert.assertEquals(message + " Verify bookmark_tag associations.", expected.getAllTags(), actual.getAllTags());
        }
    }

    private void assertBookmarkListsMatch(String messagePrefix, List<Bookmark> expectedList, List<Bookmark> actualList) {
        if (expectedList == null){
            Assert.assertNull(messagePrefix + ":List should be null", actualList);
        } else {
            Assert.assertNotNull(messagePrefix + ":List should not be null", actualList);
            Assert.assertEquals(messagePrefix + ": Number of Bookmarks does not match expected.",
                    expectedList.size(), actualList.size());
            for (int i = 0; i < expectedList.size(); i++) {
                Bookmark expected = expectedList.get(i);
                Bookmark actual = actualList.get(i);
                if (i == 0) {
                    // For first item, if ids do not match, may indicate bad sorting
                    Assert.assertEquals("First Bookmark id does not match expected, check sort order",
                            expected.getBookmarkId(), actual.getBookmarkId());
                }
                assertBookmarksMatch(messagePrefix, expected, actual);
            }
        }
    }
}
