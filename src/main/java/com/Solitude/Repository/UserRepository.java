package com.Solitude.Repository;

import com.Solitude.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// should come with CRUD functions
public interface UserRepository extends JpaRepository<User, Long> {
}
