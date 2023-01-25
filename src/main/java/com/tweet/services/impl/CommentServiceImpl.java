package com.tweet.services.impl;

import com.tweet.entites.Comment;
import com.tweet.entites.Post;
import com.tweet.exceptions.ResourceNotFoundException;
import com.tweet.payloads.CommentDto;
import com.tweet.payloads.PostResponse;
import com.tweet.repositories.CommentRepo;
import com.tweet.repositories.PostRepo;
import com.tweet.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;

    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment =this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment com=this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","comment id",commentId));
        this.commentRepo.delete(com);

    }
}
