package com.tickets.service;

import com.tickets.entity.Role;
import com.tickets.entity.User;
import com.tickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Autowired
    private UserRepository userRepository;
    @Override
    public User save(UserRegistrationDTO registrationDTO) {

        String rawPassword = registrationDTO.getPassword();
        if (rawPassword == null) {
            // Handle the case where the password is null
            throw new IllegalArgumentException("Password cannot be null");
        }
        User user = new User(
                registrationDTO.getUsername(),
                bCryptPasswordEncoder.encode(rawPassword),

                registrationDTO.getFullname(),
                registrationDTO.getPhonenumber(),
                registrationDTO.getEmail(),
                registrationDTO.getEnabled(),
                Arrays.asList(new Role("ROLE_USER"))
        );
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserRegistrationDTO> getAllUserRegistrationDTOs() {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);

    }

    @Override
    public void updateUserRoles(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);

        if (existingUser != null) {
            existingUser.setRole(user.getRole());
            userRepository.save(existingUser);
        }

    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found "));
    }

    @Override
    public List<User> findLoggedInUser(String loggedInUserEmail) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        return new   org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorites(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorites(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).
                collect(Collectors.toList());

    }


}























