package kz.com.aven.Controller;

import kz.com.aven.Entity.Status;
import kz.com.aven.Entity.User;
import kz.com.aven.Exception.UserNotFoundException;
import kz.com.aven.Response.UserStatus;
import kz.com.aven.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity <Long> createUser(@RequestBody User user) {
        return ResponseEntity.ok (userService.saveUser (user));
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public User getOneUser(@PathVariable Long userId) {
        User user = userService.getOneUserById (userId);

        if (user == null) {
            throw new UserNotFoundException ();
        }
        return user;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity <UserStatus> updateOneUser(@PathVariable Long userId, @RequestParam("status") Status status) {
        return ResponseEntity.ok (userService.updateStatus (userId, status));
    }
}
