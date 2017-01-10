package com.example.repositories.mongodb;

import com.example.repositories.UserRepository;
import com.example.users.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
@Profile("mongodb")
public interface UserMongodbRepository extends MongoRepository<User, String>, UserRepository {
}

/*
 * @Component class UserResourceProcessor implements ResourceProcessor<Resource<User>> {
 *
 * @Override public Resource<User> process(Resource<User> userResource) { userResource.add(new Link("http://s3.com/img/"
 * + userResource.getContent().getId() + ".jpg", "profile-photo")); return userResource; } }
 */
