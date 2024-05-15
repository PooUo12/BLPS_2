package com.example.blps_2.repository;

import com.example.blps_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Modifying(clearAutomatically = true)
    @Query("update #{#entityName} u set u.ratings = u.ratings + 1 where u.username = :username")
    void incrementRatings(@Param("username") String username);
}
