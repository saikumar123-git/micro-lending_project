package com.example.micro_lending.Project.Controller;

import com.example.micro_lending.Project.Entity.Role;
import com.example.micro_lending.Project.Entity.User;
import com.example.micro_lending.Project.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<User> registerAdmin(@RequestBody User user) {
        User saved = userService.registerAdmin(user);
        return ResponseEntity.ok(saved);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

  @PutMapping("/{id}/roles")
  public ResponseEntity<User> updateUserRoles(@PathVariable Long id, @RequestBody Set<String> roleNames) {
      User updatedUser = userService.updateUserRoles(id, roleNames);
      return ResponseEntity.ok(updatedUser);
  }

}
