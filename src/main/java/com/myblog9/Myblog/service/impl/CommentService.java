package com.myblog9.Myblog.service.impl;

import com.myblog9.Myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentdto);
    public void deleteCommentById( long commentId,long postId);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto updatecomment(long commentId, CommentDto commentdto);
}
