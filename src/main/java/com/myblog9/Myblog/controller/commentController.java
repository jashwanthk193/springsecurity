package com.myblog9.Myblog.controller;

import com.myblog9.Myblog.payload.CommentDto;
import com.myblog9.Myblog.service.impl.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class commentController {
    private CommentService commentservice;

    public commentController(CommentService commentservice) {
        this.commentservice = commentservice;
    }
    //http://localhost:8090/api/comments?postid=1
    @PostMapping
    public ResponseEntity<CommentDto> createPost(@RequestParam("postId")long postId,@RequestBody CommentDto dto){
        CommentDto comment = commentservice.createComment(postId, dto);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    //http://localhost:8080/api/comments?postId=1&commentId=1
    @DeleteMapping
    public ResponseEntity<String> DeleteCommentById(@RequestParam long postId,@RequestParam long commentId){
        commentservice.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }
    //http://localhost:8090/api/posts?postId=1
@GetMapping
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@RequestParam long postId){
    List<CommentDto> commentdtos = commentservice.getCommentsByPostId(postId);
    return new ResponseEntity<>(commentdtos,HttpStatus.CREATED);
}
    //http://localhost:8090/api/posts?commentId=1
@PutMapping
public ResponseEntity<CommentDto> updateComment(@RequestParam long commentId,@RequestBody CommentDto commentdto){

        CommentDto  dto=commentservice.updatecomment(commentId,commentdto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
}


}
