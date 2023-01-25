package com.tweet.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import com.tweet.payloads.UserDto;
import com.tweet.services.UserService;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    //createUser


    @GetMapping(value = "/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
       // Logger
        UserDto createUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }


    @PutMapping (value = "/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId)
    {
        UserDto updateUser=this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updateUser);

    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uId)
    {
        this.deleteUser(uId);
        return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
    }
    @GetMapping("/all-users")
    public  ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getallUsers());
    }


    @GetMapping("/userId")
    public  ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

}
