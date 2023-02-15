package com.tweet.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    
   
    
    private int id;

    private String firstName;

    private String lastName;

    private String email;
    private String userName;

    private String password;

//    private String conPassword;
       private String contactNum;
}
