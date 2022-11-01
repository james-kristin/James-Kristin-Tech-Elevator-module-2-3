package com.techelevator.bookmark.controller;

import com.techelevator.bookmark.dao.UserDao;
import com.techelevator.bookmark.model.Authority;
import com.techelevator.bookmark.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * The UserController is a class for handling HTTP Requests related to getting User information.
 *
 * It depends on an instance of a UserDAO for retrieving and storing data. This is provided
 * through dependency injection.
 *
 * Note: This class does not handle authentication (registration/login) of Users. That is
 * handled separately in the AuthenticationController.
 */
@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping( path = "/users")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public User getById(@PathVariable int userId, Principal principal) {
        return userDao.getUserById(userId);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public User updateProfile(@RequestBody User modifiedUser, Principal principal) {
        User loggedInUser = userDao.getByUsername(principal.getName());
        if (loggedInUser.getId() != modifiedUser.getId()) {
            throw new AccessDeniedException("Access denied");
        }
        return userDao.update(modifiedUser);
    }
}
