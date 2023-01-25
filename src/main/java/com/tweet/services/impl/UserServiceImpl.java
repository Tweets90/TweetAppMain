package com.tweet.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tweet.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tweet.payloads.*;
import com.tweet.entites.User;
import com.tweet.repositories.UserRepo;
import com.tweet.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        // TODO Auto-generated method stub
        User user=this.dtoToUser(userDto);
      User savedUser= this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        // TODO Auto-generated method stub
      User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id ",userId));
        user.setfirstName(userDto.getfirstName());
        user.setlastName(userDto.getlastName());
        user.setemail(userDto.getemail());
        user.setpassword(userDto.getpassword());
        user.setuserName(userDto.getuserName());
        user.setcontactNum(userDto.getcontactNum());

        User updateUser=this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updateUser);


        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        // TODO Auto-generated method stub
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getallUsers() {
        // TODO Auto-generated method stub
        List<User>users=this.userRepo.findAll();
      List<UserDto> userDtos= users.stream().map(user->userToDto(user)).collect(Collectors.toList());
        return userDtos ;
    }

    @Override
    public void deleteUser(Integer userId) {
        // TODO Auto-generated method stub
       User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
this.userRepo.delete(user);
    }
    
    public static void name() {
        
    }


   User dtoToUser(UserDto userDto)
    {
        User user=this.modelMapper.map(userDto, User.class);
       /* user.setId(userDto.getId());
        user.setfirstName(userDto.getfirstName());
        user.setlastName(userDto.getlastName());
        user.setuserName(userDto.getuserName());
        user.setemail(userDto.getemail());
        user.setpassword(userDto.getpassword());
        user.setconpassword(userDto.getconpassword());
        user.setcontactNum(userDto.getcontactNum());*/
        return user;
    }
    
    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);

        return userDto;
    }

}
