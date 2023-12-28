package com.tickets.service;

import com.tickets.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {



    User save(UserRegistrationDTO registrationDTO);

    List<User> getAllUsers();
    List<UserRegistrationDTO> getAllUserRegistrationDTOs();

    User getUserById(Long id);

    void updateUser(User user);

    void updateUserRoles(User user);

    void deleteUserById(Long id);

    User getUserByUsername(String username);


    List<User> findLoggedInUser(String loggedInUserEmail);

    User getUserByEmail(String email);

}
