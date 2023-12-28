package com.tickets.repository;

import com.tickets.entity.User;
import com.tickets.entity.ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);
    Optional<User> findById(Long id);


//    @Query("SELECT t FROM User t WHERE t.email LIKE %?1%")
//    List<User> findLoggedInUser(@Param("loggedInUserEmail") String loggedInUserEmail);

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    List<User> findLoggedInUser(@Param("email") String email);

}
