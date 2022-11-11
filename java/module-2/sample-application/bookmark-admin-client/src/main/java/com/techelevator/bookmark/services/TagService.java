package com.techelevator.bookmark.services;

import com.techelevator.bookmark.model.Tag;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TagService is a class for managing requests made to the server REST API for tags.
 *
 * By encapsulating this logic into a separate class, it becomes easier to manage, maintain, and
 * test the interactions with the server REST API separate from the other application logic.
 **/
public class TagService {

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
    public TagService(String url) {
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
     * Gets all tags from the server REST API.
     * @return the list of tags, or null if an error occurred
     */
    public List<Tag> getAllTags() {
        try {
            List<Tag> tags = new ArrayList<Tag>();
            ResponseEntity<Tag[]> response = restTemplate.exchange(API_BASE_URL + "tags", HttpMethod.GET,
                    makeAuthEntity(), Tag[].class);
            tags = new ArrayList<>(Arrays.asList(response.getBody()));
            return tags;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new tag using the server REST API.
     * @param newTag the information for the new tag
     * @return the newly created tag, or null if an error occurred
     */
    public Tag add(Tag newTag) {
        try {
            HttpEntity<Tag> entity = makeTagEntity(newTag);
            Tag returnedTag = restTemplate.postForObject(API_BASE_URL + "tags", entity, Tag.class);
            return returnedTag;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }


    /**
     * Saves updates to a tag to the server REST API.
     * @param updatedTag the updated tag information to save
     * @return The updated tag, or null if an error occurred
     */
    public Tag update(Tag updatedTag) {
        try {
            HttpEntity<Tag> entity = makeTagEntity(updatedTag);
            ResponseEntity<Tag> response = restTemplate.exchange(String.format("%s/tags/%d", API_BASE_URL, updatedTag.getId()), HttpMethod.PUT,
                    entity, Tag.class);
            return response.getBody();
        }  catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }

    /**
     * Deletes a tag from the server REST API.
     * @param id the id of the tag to delete
     * @return true if successful, false if an error occurred
     */
    public boolean delete(int id) {
        try {
            restTemplate.exchange(String.format("%s/tags/%d", API_BASE_URL, id), HttpMethod.DELETE, makeAuthEntity(), Void.class);
            return true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return false;
        }
    }

    /**
     * Helper method to create the HTTP Entity that bundles the tag data and the auth information together
     * to send to the server REST API.
     *
     * This is used when the request needs to have a body containing the tag data, for example a POST or PUT.
     *
     * @param tag the tag information
     * @return HttpEntity containing the tag data and auth information
     */
    private HttpEntity<Tag> makeTagEntity(Tag tag) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(tag, headers);
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
