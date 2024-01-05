package com.myblog9.Myblog.repository;

import com.myblog9.Myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
