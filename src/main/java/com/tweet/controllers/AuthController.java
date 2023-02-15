package com.tweet.controllers;

import com.tweet.payloads.JwtAuthRequest;
import com.tweet.payloads.JwtAuthResponse;
import com.tweet.payloads.UserDto;
import com.tweet.security.JwtTokenHelper;
import com.tweet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/tweets/")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;
@Autowired
private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails= this.userDetailsService.loadUserByUsername(request.getUsername());
       String token= this.jwtTokenHelper.generateToken(userDetails);
       JwtAuthResponse response = new JwtAuthResponse();
       response.setToken(token);
       return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);


    }

    private void authenticate(String username, String password) throws Exception{

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);


            try{
                this.authenticationManager.authenticate(authenticationToken);

            }catch (BadCredentialsException e){

                System.out.println("Invalid Details !! ");
                throw new Exception("Invalid username and Password !!");

            }
    }
    //register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
//        System.out.println("userdto here"+userDto.getUserName());
        UserDto registeredUser=this.userService.registerNewUser(userDto);
  return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
    }

}
