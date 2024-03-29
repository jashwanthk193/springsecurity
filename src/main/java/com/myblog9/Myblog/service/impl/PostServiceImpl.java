package com.myblog9.Myblog.service.impl;

import com.myblog9.Myblog.entity.Post;
import com.myblog9.Myblog.exception.ResourceNotFound;
import com.myblog9.Myblog.payload.PostDto;
import com.myblog9.Myblog.payload.PostResponse;
import com.myblog9.Myblog.repository.PostRepository;
import com.myblog9.Myblog.service.PostService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper modelmapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelmapper) {

        this.postRepo = postRepo;
        this.modelmapper=modelmapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
       Post post = mapToEntity(postDto);
       Post savedPost=postRepo.save(post);
       PostDto dto=mapToDto(savedPost);

        return dto;
    }

    @Override
    public void deletePostById(long id) {
        postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Resource not found with id:" + id)

        );
        postRepo.deleteById(id);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("post not found with id:" + id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("post not found with id:" + id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post save = postRepo.save(post);
        PostDto dto = mapToDto(save);
        return dto;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo,pageSize, sort);
        Page<Post> pagePostObject = postRepo.findAll(pageable);
        List<Post> posts = pagePostObject.getContent();
        List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse response = new PostResponse();
          response.setDto(dto);
          response.setPageNo(pagePostObject.getNumber());
          response.setTotalPage(pagePostObject.getTotalPages());
          response.setPageSize(pagePostObject.getSize());
          response.setLastPage(pagePostObject.isLast());
        return response;
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelmapper.map(postDto, Post.class);

//       Post post=new Post();
//       post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;

    }
    PostDto mapToDto(Post savedPost){
        PostDto postDto = modelmapper.map(savedPost, PostDto.class);
//        PostDto postDto=new PostDto();
//        postDto.setId(savedPost.getId());
//
//        postDto.setTitle(savedPost.getTitle());
//        postDto.setDescription(savedPost.getDescription());
//        postDto.setContent(savedPost.getContent());
        return postDto;

    }


    }

