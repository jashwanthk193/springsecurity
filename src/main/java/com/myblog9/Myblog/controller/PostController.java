package com.myblog9.Myblog.controller;

import com.myblog9.Myblog.entity.Post;
import com.myblog9.Myblog.payload.PostDto;
import com.myblog9.Myblog.payload.PostResponse;
import com.myblog9.Myblog.repository.PostRepository;
import com.myblog9.Myblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto) {
        PostDto dto=postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("post is deleted", HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto postDto=postService.getPostById(id);
return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable long id,@RequestBody PostDto postDto){
        PostDto dto = postService.updatePostById(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8090/api/posts?pageNo=1&pageSize=3?sortBy=title&sortDi=desc
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(name="pageNo",required=false,defaultValue="0")int pageNo,
            @RequestParam(name="pageSize",required=false,defaultValue="5")int pageSize,
            @RequestParam(value="sortBy",defaultValue="id",required=false)String sortBy,
            @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir){
        PostResponse response = postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return response;
    }
}
