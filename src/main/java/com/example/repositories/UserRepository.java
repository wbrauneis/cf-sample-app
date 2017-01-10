package com.example.repositories;

import com.example.users.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


/**
 * Created by wolfgang on 04.01.17.
 */
@NoRepositoryBean
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    // select * from user where name = :name
    Collection<User> findByName(@Param("name") String name);

}
