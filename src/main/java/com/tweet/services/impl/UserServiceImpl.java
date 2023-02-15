package com.tweet.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.tweet.config.AppConstants;
import com.tweet.entites.Role;
import com.tweet.exceptions.ResourceNotFoundException;
import com.tweet.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepo;


    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);
        //encoded the password
        System.out.println(user + "wsbvy");
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        //roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);
//        UserDto savedUserDto = this.modelMapper.map(newUser, UserDto.class);
       return this.modelMapper.map(newUser, UserDto.class);
//        System.out.println(savedUserDto);
//        return savedUserDto;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // TODO Auto-generated method stub
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        // TODO Auto-generated method stub
//      User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id ",userId));
//        user.setfirstName(userDto.getfirstName());
//        user.setlastName(userDto.getlastName());
//        user.setemail(userDto.getemail());
//        user.setpassword(userDto.getpassword());
//        user.setuserName(userDto.getuserName());
//        user.setcontactNum(userDto.getcontactNum());
//
//
//
//
//        User updateUser=this.userRepo.save(user);
//        UserDto userDto1 = this.userToDto(updateUser);


        return null;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        // TODO Auto-generated method stub
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getallUsers() {
        // TODO Auto-generated method stub
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        // TODO Auto-generated method stub
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }


    User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
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
