package com.techelevator.bookmark;

import com.techelevator.bookmark.model.*;
import com.techelevator.bookmark.services.AuthenticationService;
import com.techelevator.bookmark.services.BookmarkService;
import com.techelevator.bookmark.services.TagService;
import com.techelevator.util.BasicConsole;
import com.techelevator.util.BasicLogger;

import java.util.List;

/**
 * BookmarkAdminController controls the application flow and manages all of its operations through a series of menus.
 *
 * It depends on other classes for:
 *   - interacting with the user - BookmarkAdminView
 *   - interacting with the server REST API - AuthenticationService, BookmarkService, TagService
 */

public class BookmarkAdminController {

    // Service classes for communication to the REST API
    private final AuthenticationService authService;
    private final BookmarkService bookmarkService;
    private final TagService tagService;

    // The view manages all the user interaction, inputs and outputs.
    private final BookmarkAdminView view;

    // The currently logged-in user, or null if no login
    private AuthenticatedUser currentUser;

    /**
     * Constructor - creates instances of the view and service classes. Dependencies are passed in
     * from the main Application class.
     * @param console - a class that implements BasicConsole to pass to the BookmarkAdminView
     * @param apiBaseUrl - the base url for communication with the server
     */
    public BookmarkAdminController(BasicConsole console, String apiBaseUrl) {
        view = new BookmarkAdminView(console);
        authService = new AuthenticationService(apiBaseUrl);
        bookmarkService = new BookmarkService(apiBaseUrl);
        tagService = new TagService(apiBaseUrl);
    }

    /**
     * The run() method starts the program flow by displaying the main program menu,
     * and responding to the user's selection.
     */
    public void run() {
        try {

            // Menu options
            final String MANAGE_BOOKMARKS = "Manage Bookmarks";
            final String MANAGE_TAGS = "Manage Tags";
            final String EXIT = "Exit the program";
            final String[] MAIN_MENU_OPTIONS = {MANAGE_BOOKMARKS, MANAGE_TAGS, EXIT};

            view.displayWelcomeMessage();

            boolean finished = false;
            // The main menu loop
            while (!finished) {
                if (currentUser == null) {
                    handleLogin();
                } else {
                    String mainMenuSelection = view.selectFromMenu("Main Menu", MAIN_MENU_OPTIONS);
                    switch (mainMenuSelection) {
                        case MANAGE_BOOKMARKS:
                            showBookmarkSubmenu();
                            break;
                        case MANAGE_TAGS:
                            showTagSubmenu();
                            break;
                        case EXIT:
                            // Set finished to true so the loop exits.
                            finished = true;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            /*
             * Note: A catch for general Exceptions is used here as a best practice to prevent
             * unexpected Exceptions from halting the application and displaying a stack trace
             * and unfiltered technical error message to the application users.
             */
            view.displayErrorMessage("An unexpected error has occurred. See the log file for details.");
            BasicLogger.log(e);
        }
    }

    /**
     * This application requires both correct credentials (username & password) and the Admin role to
     * successfully log in and use the application.
     */
    private void handleLogin() {
        // Use the view to handle user interactions
        UserCredentials credentials = view.promptForCredentials();

        // Use the service to handle communication with the server
        currentUser = authService.login(credentials);

        // Check for successful login (user is not null) AND admin user role (also known as an authority)
        // Note that the view is also used to give feedback to the user
        if (currentUser == null) {
            view.displayErrorMessage("Login failed.");
        } else if (!currentUser.getUser().getAuthorities().contains(Authority.ADMIN_AUTHORITY)) {
            view.displayErrorMessage("Access denied. Only administrators may use this application.");
            currentUser = null;
        } else {
            view.displaySuccessMessage("Login successful.");
            String token = currentUser.getToken();
            bookmarkService.setAuthToken(token);
            tagService.setAuthToken(token);
        }
    }

    /**
     * Helper method for the Bookmark submenu functionality.
     */
    private void showBookmarkSubmenu() {

        // Bookmark Submenu options
        final String PUBLIC_BOOKMARKS= "View All Public Bookmarks";
        final String FLAGGED_BOOKMARKS = "Review Flagged Bookmarks";
        final String BACK = "Back to main menu";
        final String[] BOOKMARK_MENU_OPTIONS = {PUBLIC_BOOKMARKS, FLAGGED_BOOKMARKS, BACK};

        // Loop until the user selects BACK to return to the previous menu
        boolean showSubMenu = true;
        while (showSubMenu) {
 
            // Use the view to handle user interactions
            String subMenuSelection = view.selectFromMenu("Manage Bookmarks", BOOKMARK_MENU_OPTIONS);

            switch (subMenuSelection) {
                case PUBLIC_BOOKMARKS:
                    reviewPublicBookmarks();
                    break;
                case FLAGGED_BOOKMARKS:
                    reviewFlaggedBookmarks();
                    break;
                case BACK:
                    // Set showSubMenu to false so the loop exits.
                    showSubMenu = false;
                    break;
            }
        }
    }

    /**
     * Helper method to display a list of the public bookmarks for review. The user has the option to select
     * a bookmark to update or delete.
     */
    private void reviewPublicBookmarks() {

        // Submenu options
        final String UPDATE = "Update Bookmark";
        final String DELETE = "Delete Bookmark";
        final String CANCEL = "Cancel";
        final String[] BOOKMARK_EDIT_DELETE_OPTIONS = {UPDATE, DELETE, CANCEL};

        // Use the service to get information from the server REST API
        List<Bookmark> publicBookmarks = bookmarkService.getAllPublicBookmarks();
        if (publicBookmarks == null) {
            // Use the view to give error feedback to the user
            view.displayErrorMessage("Failed to get Bookmarks. Check the log for more information.");
            return;
        }

        // Use the view to display list and select a bookmark
        Bookmark selected = view.selectBookmark(publicBookmarks);

        // Display selected bookmark and prompt to update or delete
        if (selected != null) {

            // Use the view to display the selected bookmark
            view.displayBookmarkDetail(selected);
            // Use the view to prompt for an action to take on that bookmark
            String menuSelection = view.selectFromMenu("Update or Delete this bookmark?",
                    BOOKMARK_EDIT_DELETE_OPTIONS);

            // Handle menu selection
            switch (menuSelection) {
                case UPDATE:
                    updateBookmark(selected);
                    break;
                case DELETE:
                    deleteBookmark(selected);
                    break;
                case CANCEL:
                    // Set showSubMenu to false so the loop exits.
                    break;
            }
        }
    }

    /**
     * Helper method to display the flagged bookmarks one by one. For each bookmark, the user is 
     * prompted to either unflag, delete, or skip to move on to the next flagged bookmark.
     */
    private void reviewFlaggedBookmarks() {

        // Submenu options
        final String DELETE = "Delete Bookmark";
        final String UNFLAG = "Clear Flag";
        final String SKIP = "Next";
        final String END = "End";
        final String[] BOOKMARK_REVIEW_FLAG_OPTIONS = {UNFLAG, DELETE, SKIP, END};

        // Use service for interactions with server REST API
        List<Bookmark> flaggedBookmarks = bookmarkService.getAllFlaggedBookmarks();
        if (flaggedBookmarks == null) {
            // Use the view to give error feedback to the user
            view.displayErrorMessage("Failed to get Bookmarks. Check the log for more information.");
            return;
        }

        // Use view to display information to the user on how many bookmarks to review
        if (flaggedBookmarks.isEmpty()) {
            view.displayMessage("No flagged bookmarks to review.");
            view.displayBlankLine();
            return; // exit function to go back to previous menu
        } else {
            view.displayMessage(String.format("%d flagged bookmark%s to review.", flaggedBookmarks.size(),
                    flaggedBookmarks.size() > 1 ? "s" : ""));
            view.displayBlankLine();
        }

        // Loop through flagged bookmarks one by one
        for (Bookmark bookmark : flaggedBookmarks) {  

            // Use the view to display information to the user      
            view.displayBookmarkDetail(bookmark);

            // Use the view to present and get a menu selection 
            String menuSelection = view.selectFromMenu("Clear Flag or Delete Bookmark?",
                    BOOKMARK_REVIEW_FLAG_OPTIONS);

            // Handle menu selection
            switch (menuSelection) {
                case UNFLAG:
                    unflagBookmark(bookmark);
                    break;
                case DELETE:
                    deleteBookmark(bookmark);
                    break;
                case SKIP:
                    // Do nothing, just go to next bookmark
                    break;
                case END:
                    // Stop processing bookmarks, return to leave function
                    return; // exit function to go back to previous menu
            }
        }
    }

    /**
     * Helper method to delete a Bookmark.
     * @param selected the Bookmark to delete
     */
    private void deleteBookmark(Bookmark selected) {
        // Use the service to delete the bookmark from the server
        boolean successful = bookmarkService.delete(selected.getBookmarkId());
        // Use the view to give feedback to the user
        if (successful) {
            view.displaySuccessMessage("Bookmark deleted successfully.");
        } else {
            view.displayErrorMessage("Delete failed. Check the log for more information.");
        }
    }

    /**
     * Helper method to prompt the user and save all the information for a Bookmark.
     * @param selected the Bookmark to update
     */
    private void updateBookmark(Bookmark selected) {
        // Use the view to get the updated information
        Bookmark updated = view.promptForBookmarkUpdate(selected);
        // Set the id, as it is not set by the user
        updated.setBookmarkId(selected.getBookmarkId());
        // Use the service to call the server REST API to save the new information
        Bookmark returned = bookmarkService.update(updated);
        // Use the view to give feedback to the user
        if (returned == null) {
            view.displayErrorMessage("Update failed. Check the log for more information.");
        } else {
            view.displaySuccessMessage("Bookmark updated successfully.");
            view.displayBookmarkDetail(returned);
        }
    }

    /**
     * Helper method to unflag a Bookmark.
     * @param selected the Bookmark to update
     */
    private void unflagBookmark(Bookmark selected) {
        // Mark bookmark as unflagged
        selected.setFlagged(false);
        // Use service to send the updated data to the server
        Bookmark returned = bookmarkService.update(selected);
        // Use the view to give feedback to the user
        if (returned == null) {
            view.displayErrorMessage("Update failed. Check the log for more information.");
        } else {
            view.displaySuccessMessage("Bookmark updated successfully.");
        }
    }

    /** 
     * Helper method to support Tag administration.
     * Allows the user to view, create, update and delete Tags.
     */
    private void showTagSubmenu() {
        
        // Top level Tag submenu options
        final String LIST= "List All Tags";
        final String ADD = "Add New Tag";
        final String BACK = "Back to main menu";
        final String[] TAG_MENU_OPTIONS = {LIST, ADD, BACK};

        // Loop until the user selects BACK to return to the previous menu
        boolean showSubMenu = true;
        while (showSubMenu) {

            // Use the view to present and get a menu selection
            String menuSelection = view.selectFromMenu("Manage Tags", TAG_MENU_OPTIONS);
            switch (menuSelection) {
                case LIST:
                    // Use the view to display the tags to the user
                    Tag selected = view.selectTag(tagService.getAllTags());
                    if (selected == null) {
                        // User did not select a Tag, break from switch to display menu again.
                        break;
                    }

                    // A tag was selected for update or delete
                    handleTagChanges(selected);
                    break;
                case ADD:
                    addTag();
                    break;
                case BACK:
                    // Set showSubMenu to false so the loop exits.
                    showSubMenu = false;
                    break;
            }
        }
    }

    /**
     * Helper method to support updating or deleting a selected Tag.
     * @param selected the Tag selected
     */
    private void handleTagChanges(Tag selected) {

        // Menu options to Update/Delete a Tag
        final String UPDATE = "Update Tag";
        final String DELETE = "Delete Tag";
        final String CANCEL = "Cancel";
        final String[] UPDATE_DELETE_OPTIONS = {UPDATE, DELETE, CANCEL};

        // Use the view to present and get menu selection
        String menuSelection = view.selectFromMenu("Update or Delete this tag?", UPDATE_DELETE_OPTIONS);
        switch (menuSelection) {
            case UPDATE:
                updateTag(selected);
                break;
            case DELETE:
                deleteTag(selected);
                break;
            case CANCEL:
                break;
        }
    }

    /**
     * Helper method to prompt the user for and save all the information for a Tag.
     */
    private void addTag() {
        // Use the view to get tag information from the user
        Tag newTag = view.promptForNewTagValues();
        // Use the service to create the new tag on the server
        Tag returned = tagService.add(newTag);
        // Use the view to give feedback to the user
        if (returned == null) {
            view.displayErrorMessage("Add failed. Check the log for more information.");
        } else {
            view.displaySuccessMessage("Tag created successfully.");
            view.displayTagDetail(returned);
        }
    }

    /**
     * Helper method to delete a Tag.
     * @param selected the Tag to delete
     */
    private void deleteTag(Tag selected) {
        // Use the service to delete the tag from the server
        boolean successful = tagService.delete(selected.getId());
        // Use the view to give feedback to the user
        if (successful) {
            view.displaySuccessMessage("Tag deleted successfully.");
        } else {
            view.displayErrorMessage("Delete failed. Check the log for more information.");
        }
    }

    /**
     * Helper method to prompt the user for and save the information for a Tag.
     * @param selected the Tag to update
     */
    private void updateTag(Tag selected) {
        // Use the view to get updated information from the user
        Tag updated = view.promptForUpdateTagValues(selected);
        // Set the id as it is not set by the user
        updated.setId(selected.getId());
        // Use the service to save the updated information
        Tag returned = tagService.update(updated);
        // Use the view to give feedback to the user
        if (returned == null) {
            view.displayErrorMessage("Update failed. Check the log for more information.");
        } else {
            view.displaySuccessMessage("Tag updated successfully.");
            view.displayTagDetail(returned);
        }
    }
}
