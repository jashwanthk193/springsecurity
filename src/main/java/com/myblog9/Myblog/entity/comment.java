package com.myblog9.Myblog.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name="comments")
public class comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String body;
    private String email;
    private String name;
    @ManyToOne
    @JoinColumn(name= "post_id")
    private Post post;
}
