package com.tweet.services;

import com.tweet.entites.Post;
import com.tweet.payloads.PostDto;
import com.tweet.payloads.PostResponse;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    PostDto getPostById(Integer PostId);
    List<PostDto> getAllPostByCategory(Integer categoryId);
    List<PostDto> getPostByUser(Integer PostId);
    List<PostDto> searchPost(String keyword);
}
