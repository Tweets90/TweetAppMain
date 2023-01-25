package com.tweet.services;

import java.util.List;

import com.tweet.entites.User;
import com.tweet.payloads.UserDto;

public interface UserService {
    
    
    
     UserDto createUser(UserDto user);
     UserDto updateUser(UserDto user,Integer userId);
     UserDto getUserById(Integer userId);
     List<UserDto> getallUsers();
     void deleteUser(Integer userId);


}
