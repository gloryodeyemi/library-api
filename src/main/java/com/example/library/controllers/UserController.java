package com.example.library.controllers;

import com.example.library.dtos.UserDto;
import com.example.library.exceptions.ErrorException;
import com.example.library.models.UserAccount;
import com.example.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserAccount> registerUser(@RequestBody UserDto userDto) throws ErrorException {
        return ResponseEntity.ok(userService.save(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserAccount>> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/code/{userCode}")
    public ResponseEntity<UserAccount> getUserByUserCode(@PathVariable String userCode) throws ErrorException{
        return ResponseEntity.ok(userService.findByUserCode(userCode));
    }
}
