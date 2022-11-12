package com.techelevator.bookmark;

import com.techelevator.bookmark.model.Bookmark;
import com.techelevator.bookmark.model.Tag;
import com.techelevator.bookmark.model.UserCredentials;
import com.techelevator.util.BasicConsole;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * BookmarkAdminView is used for gathering information from the user and presenting information to the user. 
 *
 * It depends on an object that implements the BasicConsole interface to handle reading from and writing to the console.
 */
public class BookmarkAdminView {

    /*
     * The following constants support printing to the console in color.
     *
     *     Example: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     *     Here we use (char)27 instead of '\u001B' (hex 1B == dec 27) shown in the example. They are the same character.
     *     Escape codes for colors: https://en.wikipedia.org/wiki/ANSI_escape_code#Escape_sequences
     */
    private final String FOREGROUND_DEFAULT = (char) 27 + "[39m";
    private final String FOREGROUND_RED = (char) 27 + "[31m";
    private final String FOREGROUND_GREEN = (char) 27 + "[32m";
    private final String FOREGROUND_BLUE = (char) 27 + "[34m";

    private final BasicConsole console;

    // Constructor uses dependency injection to get the console object to use for printing.
    public BookmarkAdminView(BasicConsole console) {
        this.console = console;
    }


    /**
     * Adds a blank line to the display.
     */
    public void displayBlankLine() {
        console.printBlankLine();
    }

    /**
     * Adds a message to the display in normal text.
     * @param message the message to show
     */
    public void displayMessage(String message) {
        console.printMessage(message);
    }

    /**
     * Adds an error message to the display in red text.
     * @param message the message to show
     */
    public void displayErrorMessage(String message) {
        console.printErrorMessage(FOREGROUND_RED + message + FOREGROUND_DEFAULT);
        console.printBlankLine();
    }

    /**
     * Adds an error message to the display in green text.
     * @param message the message to show
     */
    public void displaySuccessMessage(String message) {
        console.printMessage(FOREGROUND_GREEN + message + FOREGROUND_DEFAULT);
        console.printBlankLine();
    }

    /**
     * Displays a welcome message with a green banner.
     */
    public void displayWelcomeMessage() {
        String message = "Welcome to the Bookmark Manager Admin Application.";
        console.printBanner(FOREGROUND_GREEN + message + FOREGROUND_DEFAULT);
        console.printBlankLine();
    }


    /**
     * Displays the detail view of a single bookmark.
     * @param bookmark the bookmark to display
     */
    public void displayBookmarkDetail(Bookmark bookmark) {
        displayMessage("Bookmark Details:");
        displayMessage(String.format("\tBookmark id: %s", bookmark.getBookmarkId()));
        displayMessage(String.format("\tUser id: %s", bookmark.getUserId()));
        displayMessage(String.format("\tTitle: %s", bookmark.getTitle()));
        displayMessage(String.format("\tURL: %s", bookmark.getUrl()));
        displayMessage(String.format("\tDescription: %s", bookmark.getDescription()));
        displayMessage(String.format("\tTags: %s", bookmark.getAllTags()));
        displayMessage(String.format("\tCreate Date: %s", bookmark.getCreateDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
        displayMessage(String.format("\tPublic: %s", bookmark.isPublic() ? "yes" : "no"));
        displayMessage(String.format("\tFlagged: %s", bookmark.isFlagged() ? "yes" : "no"));
        console.printBlankLine();
    }

    /**
     * Displays a list of bookmarks in a table-like format.
     * @param bookmarkList the list of bookmarks to display
     */
    public void displayBookmarkList(List<Bookmark> bookmarkList) {
        if (bookmarkList == null) {
            displayErrorMessage("There are no bookmarks to show.");
        } else {
            displayMessage("Bookmarks:");
            String heading1 = "  Id  Public  Flag  UserId  Title / URL                                  ";
            String heading2 = "====  ======  ====  ======  ========================================";
            String row1FormatString = "%4d  %6s  %4s  %6s  %-40s";
            String rowFormatString = "%26s  %-40s";
            displayMessage(heading1);
            displayMessage(heading2);
            for (Bookmark bookmark : bookmarkList) {
                displayMessage(String.format(row1FormatString, bookmark.getBookmarkId(), bookmark.isPublic() ? "yes" : "no",
                        bookmark.isFlagged() ? "yes" : "no", bookmark.getUserId(), bookmark.getTitle()));
                displayMessage(String.format(rowFormatString, "", bookmark.getUrl()));
                displayMessage(String.format(rowFormatString, "", bookmark.getAllTags()));
            }
        }
    }

    /**
     * Displays the detail view of a single tag.
     * @param tag the tag to display
     */
    public void displayTagDetail(Tag tag) {
        displayMessage("Tag Details:");
        displayMessage(String.format("\tTag id: %s", tag.getId()));
        displayMessage(String.format("\tName: %s", tag.getName()));
        console.printBlankLine();
    }

    /**
     * Displays a list of tags in a table-like format.
     *
     * @param tagList the list of tags to display
     */
    public void displayTagList(List<Tag> tagList) {
        if (tagList == null) {
            displayErrorMessage("There are no tags to show.");
        } else {
            displayMessage("All Tags:");
            String heading1 = "  Id  Name                                  ";
            String heading2 = "====  ========================================";
            String row1FormatString = "%4d  %-40s";
            displayMessage(heading1);
            displayMessage(heading2);
            for (Tag tag : tagList) {
                displayMessage(String.format(row1FormatString, tag.getId(), tag.getName()));
            }
        }
    }

    /**
     * Displays a list of menu options, prompting the user to select one
     * @param menuTitle the title of the menu
     * @param options the list of options to choose from
     * @return
     */
    public String selectFromMenu(String menuTitle, String[] options) {
        console.printBanner(FOREGROUND_BLUE + menuTitle + FOREGROUND_DEFAULT);
        String selection = console.getMenuSelection(options);
        console.printBlankLine();
        return selection;
    }

    /**
     * Displays a list of bookmarks, prompting the user to select one.
     * @param bookmarks List of bookmarks to display
     * @return the selected bookmark, or null if none was selected
     */
    public Bookmark selectBookmark(List<Bookmark> bookmarks) {
        Bookmark selected = null;

        if (bookmarks == null || bookmarks.size() == 0) {
            displayErrorMessage("There are no bookmarks to show.");
        } else {
            while (selected == null) {
                displayBookmarkList(bookmarks);
                Integer bookmarkId = console.promptForInteger("Enter bookmark id to select [0 to cancel]: ");
                if (bookmarkId == null || bookmarkId == 0) {
                    // No ID entered, cancel
                    break;
                }
                // ID entered, find bookmark to return
                selected = bookmarks.stream().filter(b -> b.getBookmarkId() == bookmarkId)
                        .findFirst().orElse(null);
                if (selected == null) {
                    displayErrorMessage("The id entered is not valid, please try again.");
                }
            }
        }
        console.printBlankLine();
        return selected;
    }

    /**
     * Displays a list of tags, prompting the user to select one.
     * @param tags List of tags to display
     * @return the selected tags, or null if none was selected
     */
    public Tag selectTag(List<Tag> tags) {
        Tag selected = null;

        if (tags == null || tags.size() == 0) {
            displayErrorMessage("There are no tags to show.");
        } else {
            while (selected == null) {
                displayTagList(tags);
                Integer tagId = console.promptForInteger("Enter tag id to select [0 to cancel]: ");
                if (tagId == null || tagId == 0) {
                    // No ID entered, cancel
                    break;
                }
                // ID was entered, find tag to return
                selected = tags.stream().filter(t -> t.getId() == tagId)
                        .findFirst().orElse(null);
                if (selected == null) {
                    displayErrorMessage("The id entered is not valid, please try again.");
                }
            }
        }
        console.printBlankLine();
        return selected;
    }

    /**
     * Prompts for the values required to log-in - username & password
     * @return a UserCredentials object
     */
    public UserCredentials promptForCredentials() {
        console.printMessage("Please login.");
        String username = console.promptForString("Username: ");
        String password = console.promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    /**
     * Prompts for all bookmark values that are updatable, showing their current value.
     *   Note: Values update to the entered value using empty/null to clear. They DO NOT default to the current value.
     *
     * @return a new Bookmark object with the entered values
     */
    public Bookmark promptForBookmarkUpdate(Bookmark existing) {
        Bookmark updated = null;
        if (existing != null) {
            console.printMessage("Enter new bookmark values");
            updated = new Bookmark();
            updated.setTitle(promptForStringUpdateValue("Title", true, existing.getTitle()));
            updated.setUrl(promptForStringUpdateValue("URL", true, existing.getUrl()));
            updated.setDescription(promptForStringUpdateValue("Description", false, existing.getDescription()));
            updated.setPublic(promptForBooleanUpdateValue("Public", existing.isPublic()));
            updated.setFlagged(promptForBooleanUpdateValue("Flagged", existing.isFlagged()));
        }
        return updated;
    }

    /**
     * Prompts for the values to create a new Tag
     * @return a new Tag object with the values entered
     */
    public Tag promptForNewTagValues() {
        Tag tag = new Tag();
        tag.setName(promptForStringUpdateValue("Name", true, null));
        return tag;
    }

    /**
     * Prompts for the values to update an existing Tag
     * @return a new Tag object with the values entered
     */
    public Tag promptForUpdateTagValues(Tag existing) {
        Tag tag = new Tag();
        tag.setName(promptForStringUpdateValue("Name", true, existing.getName()));
        return tag;
    }
    
    //
    // Helper functions to support getting user input and basic validation of input values
    //
    private String promptForStringUpdateValue(String prompt, boolean required, String currentValue) {
        prompt = promptWithValue(prompt, currentValue);
        while (true) {
            String entry = console.promptForString(prompt);
            if (!entry.isEmpty() || !required) {
                return entry;
            }
            displayErrorMessage("A value is required, please try again.");
        }
    }

    private boolean promptForBooleanUpdateValue(String prompt, boolean currentValue) {
        prompt = promptWithValue(prompt, currentValue ? "yes" : "no");
        return console.promptForYesNo(prompt);
    }

    private String promptWithValue(String prompt, Object displayedValue) {
        if (displayedValue != null) {
            return prompt + "[" + displayedValue.toString() + "]: ";
        }
        return prompt + ": ";
    }
}
