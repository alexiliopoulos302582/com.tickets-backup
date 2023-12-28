package com.tickets.controller;
import com.tickets.entity.Role;
import com.tickets.entity.User;
import com.tickets.service.UpdateUserDTO;
import com.tickets.service.UserRegistrationDTO;
import com.tickets.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adduser")
public class UserRegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDTO userRegistrationDTO() {
        return new UserRegistrationDTO();
    }
    @GetMapping
    public String showadduser(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "adduser";
    }
    @PostMapping
    public String adduser(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO) {
        userService.save(userRegistrationDTO);
        return "redirect:/adduser?success";
    }

    @GetMapping("/updateuser/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model) {
        Optional<User> userOptional  = Optional.ofNullable(userService.getUserById(id));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Role> allRoles = (List<Role>) user.getRole();
            model.addAttribute("user", userOptional.get());
            model.addAttribute("allRoles", allRoles);
            model.addAttribute("userRoles", user.getRole().stream().map(Role::getId).collect(Collectors.toList()));
        } else {
            // Handle the case where the user is not found, you might redirect or show an error page.
            return "redirect:/admin"; // Assuming you want to redirect to the admin page
        }
        return "updateuser";
    }



    @PostMapping("/updateuser")
    public String updateUser(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO,
                             @RequestParam(name = "userRoles", required = false ) List<String> roleNames,
                             Model model) {

        User existingUser = null;
        try {


            // Retrieve the existing user from the database
            existingUser = userService.getUserById(userRegistrationDTO.getId());

            // Update the existing user with the new information
            existingUser.setUsername(userRegistrationDTO.getUsername());
//            existingUser.setPassword(userRegistrationDTO.getPassword());
            existingUser.setFullname(userRegistrationDTO.getFullname());
            existingUser.setPhonenumber(userRegistrationDTO.getPhonenumber());
            existingUser.setEmail(userRegistrationDTO.getEmail());
            existingUser.setEnabled(userRegistrationDTO.getEnabled());

            // Update user details
            userService.updateUser(existingUser);


            if (roleNames != null) {  // Check if roleNames is not null before using the stream
                List<Role> selectedRoles = roleNames.stream()
                        .map(Role::new)
                        .collect(Collectors.toList());
                existingUser.setRole(selectedRoles);
                userService.updateUserRoles(existingUser);
            }

            // Redirect to a success page or show an appropriate message
            return "redirect:/admin";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("constraint [email_unique]")) {
                model.addAttribute("error", "email already exists, please choose a different one");
            } else {
                model.addAttribute("error", "an error occurred while updating the user");
            }

            return "emailerror";
        }

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/delete/{id}")
    public String deleteuser(@PathVariable Long id) {
        // Perform deletion logic here
        userService.deleteUserById(id);

        // Redirect to the user list or another appropriate page
        return "redirect:/admin";
    }

    @GetMapping("/updateprofile/{id}")
    public String updateprofile(
            @PathVariable("id") Long id,
            Model model) {
        User usertoUpdate = userService.getUserById(id);
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        userDTO.setId(usertoUpdate.getId());
        userDTO.setUsername(usertoUpdate.getUsername());
        userDTO.setFullname(usertoUpdate.getFullname());
        userDTO.setPassword(usertoUpdate.getPassword());
        userDTO.setEmail(usertoUpdate.getEmail());
        userDTO.setPhonenumber(usertoUpdate.getPhonenumber());

        model.addAttribute("user", userDTO);
        return "updateprofile";
    }


    @PostMapping("/updateprofile")
    public String updateprofileinfo(@ModelAttribute("user")
      UserRegistrationDTO userRegistrationDTO,
       BindingResult result, Model model ) {


        try {
            if (!userRegistrationDTO.getNewPassword().equals(userRegistrationDTO.getConfirmPassword())) {
                model.addAttribute("error", "provided passwords do not match");
                return "updateprofile";

            }
                User existingUser = userService.getUserById(userRegistrationDTO.getId());

            if (existingUser != null) {
                // Update the existing user with the new information
                existingUser.setUsername(userRegistrationDTO.getUsername());
                existingUser.setFullname(userRegistrationDTO.getFullname());
                existingUser.setPhonenumber(userRegistrationDTO.getPhonenumber());
                existingUser.setEmail(userRegistrationDTO.getEmail());

                if (!userRegistrationDTO.getNewPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getNewPassword()));
                }

                // Update user details
                userService.updateUser(existingUser);
                // Redirect to the same page with a success message
               model.addAttribute("success", "Profile details updated successfully");
                return "redirect:/adduser/updateprofile/"
                        +userRegistrationDTO.getId()+"?success=#successMessage";

//                return "redirect:/adduser?success";
            } else {
                return "redirect:/emailerror";
            }
        } catch (DataIntegrityViolationException e) {
            // Handle exceptions appropriately, e.g., show error messages
            return "emailerror";

        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/login_disabled")
    public String loginbanned() {
        return "login_disabled";
    }


}
