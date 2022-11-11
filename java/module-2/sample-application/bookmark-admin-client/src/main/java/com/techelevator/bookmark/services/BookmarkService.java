package com.techelevator.bookmark.services;

import com.techelevator.bookmark.model.Bookmark;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * BookmarkService is a class for managing requests made to the server REST API for bookmarks.
 *
 * By encapsulating this logic into a separate class, it becomes easier to manage, maintain, and
 * test the interactions with the server REST API separate from the other application logic.
 **/
public class BookmarkService {

    private final String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    // credential token for the currently logged-in user
    private String authToken = null;

    /**
     * Constructor - The only outside dependency is for the base url used to access the server. Using dependency injection
     * for this value allows it to be more easily changed. It is common for this URL to change for development, testing,
     * and for live deployment.
     *
     * @param url Base URL for the server REST API
     */
    public BookmarkService(String url) {
        this.API_BASE_URL = url;
    }

    /**
     * This method is used by the controller to update the authToken following the user login.
     *
     * @param authToken - credential token for the currently logged-in user.
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Gets all public bookmarks from the server REST API.
     * Note: This endpoint is public and does not require Auth information.
     * @return the list of public bookmarks, or null if an error occurred
     */
    public List<Bookmark> getAllPublicBookmarks() {
        try {
            // No Auth needed to view public bookmarks
            Bookmark[] response = restTemplate.getForObject(API_BASE_URL + "bookmarks/public", Bookmark[].class);
            return Arrays.asList(response);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }

    /**
     * Gets all flagged bookmarks from the server REST API.
     * @return The list of flagged bookmarks, or null if an error occurred
     */
    public List<Bookmark> getAllFlaggedBookmarks() {
        try {
            // Using exchange method to send required auth headers
            ResponseEntity<Bookmark[]> response = restTemplate.exchange(API_BASE_URL + "bookmarks/flagged", HttpMethod.GET,
                    makeAuthEntity(), Bookmark[].class);
            return Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }

    /**
     * Gets a single bookmark from the server REST API.
     * @param bookmarkId the id of the bookmark to request
     * @return The specified bookmark, or null if an error occurred
     */
    public Bookmark getBookmark(int bookmarkId) {
        try {
            ResponseEntity<Bookmark> response = restTemplate.exchange(String.format("%s/bookmarks/%d", API_BASE_URL, bookmarkId),
                    HttpMethod.GET, makeAuthEntity(), Bookmark.class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }

    /**
     * Saves updates to a bookmark to the server REST API.
     * @param updatedBookmark the updated Bookmark information to save
     * @return The updated bookmark, or null if an error occurred
     */
    public Bookmark update(Bookmark updatedBookmark) {
        try {
            HttpEntity<Bookmark> entity = makeBookmarkEntity(updatedBookmark);
            ResponseEntity<Bookmark> response = restTemplate.exchange(String.format("%s/bookmarks/%d", API_BASE_URL, updatedBookmark.getBookmarkId()), HttpMethod.PUT,
                    entity, Bookmark.class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }

    /**
     * Deletes a bookmark from the server REST API.
     * @param bookmarkId the id of the bookmark to delete
     * @return true if successful, false if an error occurred
     */
    public boolean delete(int bookmarkId) {
        try {
            restTemplate.exchange(String.format("%s/bookmarks/%d", API_BASE_URL, bookmarkId), HttpMethod.DELETE,
                    makeAuthEntity(), Void.class);
            return true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return false;
        }
    }

    /**
     * Helper method to create the HTTP Entity that bundles the bookmark data and the auth information together
     * to send to the server REST API.
     *
     * This is used when the request needs to have a body containing the bookmark data, for example a POST or PUT.
     *
     * @param bookmark the bookmark information
     * @return HttpEntity containing the bookmark data and auth information
     */
    private HttpEntity<Bookmark> makeBookmarkEntity(Bookmark bookmark) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(bookmark, headers);
    }

    /**
     * Helper method to create the HTTP Entity containing the auth information to send to the server REST API.
     *
     * This is used when the request does not have a body or payload, for example a GET or DELETE.
     *
     * @return HttpEntity containing the auth information (with no request body data).
     */
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
