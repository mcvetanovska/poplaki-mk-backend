package mk.poplaki.controller;

import mk.poplaki.dto.auth.UserRequest;
import mk.poplaki.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@Valid @RequestBody UserRequest data) {
        userService.createUser(data);
    }

    // create method for listing users

    // create method for updating user

    @GetMapping("count")
    public long getUserCount() {
        return userService.getUserCount();
    }
}
