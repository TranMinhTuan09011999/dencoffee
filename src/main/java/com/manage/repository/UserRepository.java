package com.manage.repository;

import com.manage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.userId = :userId")
  public User findUserByUserId(@Param("userId") Long userId);

}
