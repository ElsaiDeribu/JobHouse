package com.jobhouse.finder.Repositories;

import java.util.List;

import com.jobhouse.finder.Tables.Post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findPostById(Long id);

    @Query(value = "select * from post post where post.user_user_id = :Id", nativeQuery = true)
    List<Post> findByUserId(@Param("Id") Long Id);
}
