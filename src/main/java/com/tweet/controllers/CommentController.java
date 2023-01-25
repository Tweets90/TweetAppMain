package com.tweet.controllers;

import com.tweet.entites.Comment;
import com.tweet.payloads.ApiResponse;
import com.tweet.payloads.CommentDto;
import com.tweet.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto , @PathVariable Integer postId)
    {

       CommentDto createComment= this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(createComment, CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
    {

        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully !!", true), HttpStatus.OK);
    }
}
