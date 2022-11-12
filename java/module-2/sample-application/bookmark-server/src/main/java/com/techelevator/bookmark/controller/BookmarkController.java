package com.techelevator.bookmark.controller;

import com.techelevator.bookmark.model.Bookmark;
import com.techelevator.bookmark.model.Tag;
import com.techelevator.bookmark.service.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * The BookmarkController is a class for handling HTTP Requests related to creating, reading,
 * updating, and deleting Bookmarks.
 *
 * It depends on an instance of BookmarkService for interacting with DAOs and handling business logic.
 * This is provided through dependency injection.
 */
@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping( path = "/bookmarks" )
public class BookmarkController {

    private BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @RequestMapping( method = RequestMethod.GET )
    public List<Bookmark> getAllBookmarks(Principal principal) {
        return bookmarkService.getBookmarksForUser(principal);
    }

    @PreAuthorize("permitAll()")
    @RequestMapping( path = "/public", method = RequestMethod.GET )
    public List<Bookmark> getPublicBookmarks(@RequestParam(defaultValue  = "") String search) {
        if (search != null) {
            return bookmarkService.filterPublicBookmarks(search);
        }
        return bookmarkService.getAllPublicBookmarks();
    }

    @RequestMapping( path = "/users/{userId}", method = RequestMethod.GET )
    public List<Bookmark> getPublicBookmarksByUserId(@PathVariable int userId) {
        return bookmarkService.getPublicBookmarksByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping( path = "/flagged", method = RequestMethod.GET )
    public List<Bookmark> getFlaggedBookmarks() {
        return bookmarkService.getFlaggedBookmarks();
    }

    @RequestMapping( path = "/{bookmarkId}", method = RequestMethod.GET)
    public Bookmark getBookmarkById(@PathVariable int bookmarkId, Principal principal) {
        Bookmark bookmark = bookmarkService.getBookmarkById(bookmarkId, principal);
        if (bookmark == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark not found");
        }
        return bookmark;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @RequestMapping( method = RequestMethod.POST )
    public Bookmark addBookmark(@Valid @RequestBody Bookmark newBookmark, Principal principal) {
        return bookmarkService.addBookmark(newBookmark, principal);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping( path = "/{bookmarkId}", method = RequestMethod.DELETE )
    public void removeBookmark(@PathVariable int bookmarkId, Principal principal) {
        if (bookmarkService.removeBookmark(bookmarkId, principal) == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark not found");
        }
    }

    @RequestMapping( path = "/{bookmarkId}", method = RequestMethod.PUT )
    public Bookmark updateBookmark(@PathVariable int bookmarkId, @Valid @RequestBody Bookmark modifiedBookmark, Principal principal) {
        // Make sure the bookmark id is set
        modifiedBookmark.setBookmarkId(bookmarkId);
        Bookmark updatedBookmark = bookmarkService.updateBookmark(modifiedBookmark, principal);
        if (updatedBookmark == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark not found");
        } else {
            return updatedBookmark;
        }
    }

    @RequestMapping( path="/{bookmarkId}/tags", method = RequestMethod.GET)
    public List<Tag> getBookmarkTags(@PathVariable int bookmarkId, Principal principal) {
        return bookmarkService.getBookmarkTags(bookmarkId, principal);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping( path="/{bookmarkId}/tags/{tagId}", method = RequestMethod.POST)
    public void addTagToBookmark(@PathVariable int bookmarkId, @PathVariable int tagId, Principal principal) {
        boolean updated = bookmarkService.addTagToBookmark(bookmarkId, tagId, principal);
        if (updated == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark or Tag not found");
        }
    }

    @RequestMapping( path="/{bookmarkId}/tags", method = RequestMethod.PUT)
    public List<Tag> updateBookmarkTags(@PathVariable int bookmarkId,  @RequestBody List<Tag>tags, Principal principal) {
        boolean updated = bookmarkService.updateBookmarkTags(bookmarkId, tags, principal);
        return bookmarkService.getBookmarkTags(bookmarkId, principal);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping( path="/{bookmarkId}/tags/{tagId}", method = RequestMethod.DELETE)
    public void removeTagFromBookmark(@PathVariable int bookmarkId, @PathVariable int tagId, Principal principal) {
        boolean updated = bookmarkService.removeTagFromBookmark(bookmarkId, tagId, principal);
        if (updated == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark or Tag not found");
        }
    }
}
