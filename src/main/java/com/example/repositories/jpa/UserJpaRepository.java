package com.example.repositories.jpa;

import com.example.repositories.UserRepository;
import com.example.users.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
@Profile({"in-memory","default"})
public interface UserJpaRepository extends JpaRepository<User, String>, UserRepository {
}
