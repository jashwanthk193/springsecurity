package com.myblog9.Myblog.service.impl;

import com.myblog9.Myblog.entity.Post;
import com.myblog9.Myblog.entity.comment;
import com.myblog9.Myblog.exception.ResourceNotFound;
import com.myblog9.Myblog.payload.CommentDto;
import com.myblog9.Myblog.repository.CommentRepository;
import com.myblog9.Myblog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class commentserviceimpl implements CommentService {
    private CommentRepository commentRepo;
    private PostRepository postRepo;

    public commentserviceimpl(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentdto) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("post not found with id" + postId));
        comment comment = mapToEntity(commentdto);
        comment.setPost(post);
        comment save = commentRepo.save(comment);

        CommentDto commentDto = mapToDto(save);
        return commentDto;

    }

    @Override
    public void deleteCommentById(long commentId,long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("post not found with an id" + postId));
commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

//    @Override
//    public CommentDto updatecomment(long commentId, CommentDto commentdto) {
//        comment com = commentRepo.findById(commentId).get();
//        Post post = postRepo.findById(com.getId()).get();
//        comment comment = mapToEntity(commentdto);
//        comment.setPost(post);
//        comment.setId(commentId);
//  comment savedcomment = commentRepo.save(comment);
//        CommentDto commentDto = mapToDto(savedcomment);
//        return commentDto;
//    }
@Override
public CommentDto updatecomment(long commentId, CommentDto commentdto) {
    // Retrieve the existing comment object from the database
    comment existingComment = commentRepo.findById(commentId)
            .orElseThrow(() -> new ResourceNotFound("comment not found with id" + commentId));

    // Retain the existing postId associated with the comment
    Long postId = existingComment.getPost().getId();

    // Update comment properties
    existingComment.setName(commentdto.getName());
    existingComment.setEmail(commentdto.getEmail());
    existingComment.setBody(commentdto.getBody());

    // Set the existing postId for the comment
    Post existingPost = postRepo.findById(postId)
            .orElseThrow(() -> new ResourceNotFound("post not found with id" + postId));
    existingComment.setPost(existingPost);

    // Save the updated comment
    comment savedComment = commentRepo.save(existingComment);

    CommentDto commentDto = mapToDto(savedComment);
    return commentDto;
}



    comment mapToEntity(CommentDto dto) {
        comment comment = new comment();
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        return comment;
    }

    CommentDto mapToDto(comment comment) {
        CommentDto dto = new CommentDto();
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;

    }
}
