package com.tweet.payloads;

import com.tweet.entites.Category;
import com.tweet.entites.Comment;
import com.tweet.entites.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private  Integer postId;
    private String title;
    private String content;
    private String imageName;
    private String addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<Comment> comments=new HashSet<>();



}
