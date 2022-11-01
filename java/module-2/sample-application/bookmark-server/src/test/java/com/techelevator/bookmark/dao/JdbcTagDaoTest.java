package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.model.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;

public class JdbcTagDaoTest extends BaseDaoTests {

    private static final Tag TAG_1 = new Tag(200, "Tag 3");
    private static final Tag TAG_2 = new Tag(201, "Tag 2");
    private static final Tag TAG_3 = new Tag(202, "Tag 1");
    private static final Tag TAG_4 = new Tag(203, "Tag 4");
    // Get should sort Tags by name
    private static final List<Tag> ALL_TAGS = Arrays.asList(new Tag[] {TAG_3, TAG_2, TAG_1, TAG_4});

    private JdbcTagDao dao;

    @Before
    public void setup() {
        dao = new JdbcTagDao(dataSource);
    }

    @Test
    public void getAll_returns_correct_list_sorted_by_name() {
        List<Tag> result = dao.getAll();
        Assert.assertNotNull("getAll returned null", result);
        assertTagListsMatch("getAll returned wrong or partial data", ALL_TAGS, result);
    }

    @Test
    public void getById_returns_correct_tag() {
        Tag result = dao.getById(TAG_1.getId());
        Assert.assertNotNull("getById returned null", result);
        assertTagsMatch("getById returned wrong or partial data", TAG_1, result);
    }

    @Test
    public void getById_returns_null_when_id_not_found() {
        Tag result = dao.getById(9999);
        Assert.assertNull("getById failed to return null for id not in database", result);
    }

    @Test
    public void create_returns_tag_with_id_and_correct_values() {
        Tag newTag = new Tag(0, "New Tag");
        Tag createdTag = dao.create(newTag);

        Assert.assertNotNull("create returned null", createdTag);

        int newId = createdTag.getId();
        Assert.assertTrue("create returned a tag without an id", newId > 0);

        newTag.setId(newId);
        assertTagsMatch("create returned a tag with wrong or partial data", newTag, createdTag);
    }

    @Test
    public void created_tag_has_expected_values_when_retrieved() {
        Tag newTag = new Tag(0, "New Tag");
        Tag createdTag = dao.create(newTag);

        int newId = createdTag.getId();
        Tag retrievedTag = dao.getById(newId);

        assertTagsMatch("create did not save correct data in database", createdTag, retrievedTag);
    }


    @Test
    public void update_returns_tag_with_correct_values() {
        Tag tag = new Tag();
        tag.setId(TAG_1.getId());
        tag.setName("Modified Tag");

        Tag updatedTag = dao.update(tag);

        assertTagsMatch("update did not save correct data in database", updatedTag, tag);
    }

    @Test
    public void updated_tag_has_expected_values_when_retrieved() {
        Tag tag = new Tag();
        tag.setId(TAG_1.getId());
        tag.setName("Modified Tag");

        Tag updatedTag = dao.update(tag);
        int newId = updatedTag.getId();
        Tag retrievedTag = dao.getById(newId);

        assertTagsMatch("update did not save correct data in database", updatedTag, retrievedTag);
    }

    @Test
    public void delete_removes_tag_with_no_bookmarks() {
        dao.delete(TAG_4.getId());
        Tag tag = dao.getById(TAG_4.getId());
        Assert.assertNull("delete failed to removed tag from database", tag);

        List<Tag> tags = dao.getAll();
        Assert.assertEquals("delete removed the wrong number of tags", ALL_TAGS.size()-1, tags.size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void delete_does_not_remove_tag_with_bookmark_associations() {
        // Expect this to fail with exception as bookmarks associated to this tag
        dao.delete(TAG_1.getId());
   }

    @Test
    public void delete_does_not_error_when_id_not_found() {
        try {
            dao.delete(9999);
            List<Tag> tags = dao.getAll();
            Assert.assertEquals("no tags should have been deleted", ALL_TAGS.size(), tags.size());

        } catch (Exception e) {
            Assert.fail("Delete should not error when not found.");
        }
    }


    private void assertTagsMatch(String messagePrefix, Tag expected, Tag actual) {
        Assert.assertEquals(messagePrefix + ": Tag id field does not match expected value.", expected.getId(), actual.getId());
        Assert.assertEquals(messagePrefix + ": Tag (id=" + expected.getId() + ") contains unexpected data in field 'name'.",
                expected.getName(), actual.getName());
    }

    private void assertTagListsMatch(String messagePrefix, List<Tag> expectedList, List<Tag> actualList) {
        Assert.assertEquals(messagePrefix + ": List size incorrect.", expectedList.size(), actualList.size());
        for (int i=0; i< expectedList.size(); i++) {
            Tag expected = expectedList.get(i);
            Tag actual = actualList.get(i);
            if (i==0) {
                // For first item, if ids do not match, may indicate bad sorting
                Assert.assertEquals(messagePrefix + ": first tag id does not match expected, check sort order",
                        expected.getId(), actual.getId());
            }
            assertTagsMatch(messagePrefix, expected, actual);
        }
    }
}
