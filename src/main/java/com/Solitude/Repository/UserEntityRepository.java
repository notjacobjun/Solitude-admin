package com.Solitude.Repository;

import com.Solitude.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// configures all the CRUD options
@RepositoryRestResource
public interface UserEntityRepository extends CrudRepository<UserEntity,Integer> {
}
