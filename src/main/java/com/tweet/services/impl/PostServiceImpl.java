package com.tweet.services.impl;

import com.tweet.entites.Category;
import com.tweet.entites.Post;
import com.tweet.entites.User;
import com.tweet.exceptions.ResourceNotFoundException;
import com.tweet.payloads.PostDto;
import com.tweet.payloads.PostResponse;
import com.tweet.repositories.CategoryRepo;
import com.tweet.repositories.PostRepo;
import com.tweet.repositories.UserRepo;
import com.tweet.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto,Integer categoryId,Integer userId) {
        User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User id", userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","category id",categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost= this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","post id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatePost=this.postRepo.save(post);

        PostDto postDto1=this.modelMapper.map(updatePost,PostDto.class);


        return postDto1;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","post id",postId));
this.postRepo.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort=null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort=Sort.by(sortBy).ascending();
}else {
            sort =Sort.by(sortBy).descending();
        }

        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Post> pagePost =this.postRepo.findAll(p);
        List<Post> allPosts=pagePost.getContent();
        List<PostDto> postDtos=allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer PostId) {

       Post post= this.postRepo.findById(PostId).orElseThrow(() -> new ResourceNotFoundException("Post","post id",PostId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {

        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));
        List<Post> posts= this.postRepo.findByCategory(cat);
       List<PostDto> postDtos= posts.stream().map((post) -> this.modelMapper.map(posts,PostDto.class)).collect(Collectors.toList());

       return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer UserId) {

        User user = this.userRepo.findById(UserId).orElseThrow(()->new ResourceNotFoundException("User","user id",UserId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto>postDtos1= posts.stream().map((post)->this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
        return postDtos1 ;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts=this.postRepo.searchByTitle("%"+keyword+"%");
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }
}
