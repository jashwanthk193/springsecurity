package com.myblog9.Myblog.service;

import com.myblog9.Myblog.payload.PostDto;
import com.myblog9.Myblog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto);

    void deletePostById(long id);

    PostDto getPostById(long id);

    PostDto updatePostById(long id, PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
