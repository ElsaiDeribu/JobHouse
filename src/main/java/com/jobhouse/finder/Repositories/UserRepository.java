package com.jobhouse.finder.Repositories;

import java.util.List;

import com.jobhouse.finder.Helpers.UserHelper;
import com.jobhouse.finder.Tables.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);

    // @Query(value = "select username,full_name from users s where s.full_name like
    // %:keyword%", nativeQuery = true)
    // List<UserHelper> findByKeyword(@Param("keyword") String keyword);
}
