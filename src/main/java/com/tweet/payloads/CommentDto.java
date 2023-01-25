package com.tweet.payloads;

import com.tweet.entites.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Integer id;

    private String content;
}
