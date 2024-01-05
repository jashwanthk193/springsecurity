package com.myblog9.Myblog.repository;

import com.myblog9.Myblog.entity.comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<comment,Long> {
    List<comment> findByPostId(Long postId);
}
