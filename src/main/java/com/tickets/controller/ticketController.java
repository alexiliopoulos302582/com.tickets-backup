package com.tickets.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import com.tickets.Application;
import com.tickets.entity.*;
import com.tickets.repository.AcsCustomerRepository;
import com.tickets.repository.UserRepository;
import com.tickets.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import com.tickets.repository.ticketRepository;

import java.util.logging.Logger;
import java.util.stream.Collectors;
@Controller
//@RequiredArgsConstructor
public class ticketController  {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ticketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private AcsCustomerService acsCustomerService;
    @Autowired
    private AcsCustomerRepository acsCustomerRepository;

    @Autowired
    private  deviceService deviceService;




    @GetMapping("/newticket")
    public String showNewTicketPage(@RequestParam(name = "phoneNumber",required = false) String phoneNumber,
                                    @RequestParam(name = "serialNumber",required = false) String serialNumber,
                                    @RequestParam(name="id",required = false) Integer id,
                                    @RequestParam(name="name",required = false) String name,

                                    Model model) {

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
             List<acsCustomer> acsCustomers = acsCustomerRepository.findAllByacsPhoneNumber(phoneNumber);
            if (acsCustomers.size() == 1) {
                model.addAttribute("acsCustomerInfo", acsCustomers.get(0));
            } else if (acsCustomers.size() > 1) {
                model.addAttribute("acsCustomers", acsCustomers);
                model.addAttribute("multipleResults", true);
                model.addAttribute("serialNumber", serialNumber);
            }
        }
        if (serialNumber != null && !serialNumber.isEmpty()) {

            String deviceModel = deviceService.getDeviceModelBySerialNumber(serialNumber);
            model.addAttribute("deviceModelList", Arrays.asList(deviceModel));
            model.addAttribute("serialNumber", serialNumber);
            model.addAttribute("deviceModel", deviceModel);

        } else { model.addAttribute("deviceModelList", Collections.emptyList());

        }
        if (id != null) {
            acsCustomer customer = acsCustomerRepository.findById(id).orElse(null);
            if (customer != null) {
                model.addAttribute("acsCustomerInfo", customer);
            }
        }

        if (name != null && !name.isEmpty()) {
             List<acsCustomer> matchingCustomers  =acsCustomerRepository.findAll();
            if (!matchingCustomers .isEmpty()) {
                model.addAttribute("name", matchingCustomers );
                model.addAttribute("multipleResults", true);
                System.out.println("Matching Customers: " + matchingCustomers);
//

            }
        }

        model.addAttribute("ticket", new ticket());
        List<String> ticketSource = Arrays.asList("Phone", "Email", "Other");
        model.addAttribute("ticketSource", ticketSource);

        List<String> department = Arrays.asList("acs department", "other");
        model.addAttribute("department", department);

        List<String> priority = Arrays.asList("low", "normal", "urgent");
        model.addAttribute("priority", priority);

        List<String> helpTopic = Arrays.asList("support", "billing");
        model.addAttribute("helpTopic", helpTopic);

        List<String> deviceModel = Arrays.asList(
                "4 SLOT CRDL FOR EF550/EF550R",
                "4 SLOT ETH CRDL FOR EF550/EF550R",
                "EF550R",
                "PIDION EF500R",
                "SPP-R310PLUSiK 3inch" );
        model.addAttribute("deviceModel", deviceModel);

        List<String> category = Arrays.asList(
                "hardware issue", "software issue", "network - communications",
                "application properties", "hardware properties", "hardware misuse",
                "software misuse", "general info software/hardware", "caused damage",
                "theft / loss");
        model.addAttribute("category", category);


        List<String> assignedTo = Arrays.asList(
                "Tsaloukidis Al", "Psaras B", "Giannetakis St",
                "Maistrou M", "Kritikos Sp", "Tzouvelis St");
        model.addAttribute("assignedTo", assignedTo);


        return "newticket";
    }





    @PostMapping("/newticket")
    public String createticket(@ModelAttribute ticket ticket) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        ticket.setCreationDate(dateFormat.format(new Date()));
        ticketService.saveticket(ticket);
        return "ticketcreated";    }
    @GetMapping("/opentickets")
    public String getopentickets(@RequestParam(name = "search", required = false) String search,
                                 Model model, Authentication authentication) {
        UserDetails userDetails1 = (UserDetails) authentication.getPrincipal();
        String loggedInUserEmail = userDetails1.getUsername();
        List<User> userList = userService.getAllUsers();
        Long userid = null;
        for (User i : userList) {
            if (Objects.equals(i.getEmail(), loggedInUserEmail)) {
                userid = i.getId(); }
        }
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
        }
        List<ticket> tickets;
        if (search != null && !search.isEmpty()) {
            tickets = ticketRepository.findOpenticketsBySearch(search);
        } else {
            tickets = ticketRepository.findByticketState(1);
        }
        model.addAttribute("userid", userid);
        model.addAttribute("ticket", tickets);
        model.addAttribute("search", search);
        return "opentickets";
    }
    @GetMapping("/closedtickets")
    public String getClosedtickets(
            @RequestParam(name = "search", required = false) String search,
            Model model, Authentication authentication) {
        UserDetails userDetails1 = (UserDetails) authentication.getPrincipal();
        String loggedInUserEmail = userDetails1.getUsername();
        List<User> userList = userService.getAllUsers();
        Long userid = null;
        for (User i : userList) {
            if (Objects.equals(i.getEmail(), loggedInUserEmail)) {
                userid = i.getId();}
        }
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
        }
        List<ticket> tickets;
        if (search != null && !search.isEmpty()) {
            tickets = ticketRepository.findClosedticketsBySearch(search);
        } else {
            tickets = ticketRepository.findClosedtickets();
        }
        model.addAttribute("userid", userid);
        model.addAttribute("ticket", tickets);
        model.addAttribute("search", search);
        return "closedtickets";
    }
    @PostMapping("/closedtickets/export")
    public String exportClosedTickets() {
        ExportClosedTickets.exportClosedTickets();
        return "redirect:/closedtickets";
    }
    @PostMapping("/opentickets/export")
    public String exportOpenTickets() {
        ExportOpenTickets.exportOpenTickets();
        return "redirect:/opentickets";
    }

    @GetMapping("/closedtickets/export/txt")
    public String exportClosedTickets_txt() {
        ExportClosedTickets_txt.exportClosedTickets_txt();
        return "redirect:/closedtickets";
    }
    @GetMapping("/opentickets/export/txt")
    public String exportOpenTickets_txt() {
        ExportOpenTickets_txt.exportOpenTickets_txt();
        return "redirect:/opentickets";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        try {
            if (email == null || email.isEmpty()) {
                // Handle the case where email is empty
                return "redirect:/login?error";
            }
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                User user = userService.getUserByEmail(email);
            if (user != null) {

                if ("1".equals(user.getEnabled())) {
                    return "redirect:/home";
                } else { return "login_disabled";
                }

            } else {
                return "login_disabled";
            }
        } catch (Exception e) {
            // Handle authentication failure, redirect back to login page with an error message
            return "redirect:/login?error"; // You can handle errors in the login page

        }

    }
    @GetMapping("/landing")
    public String landing() {
        return "landingpage";
    }
    @GetMapping("/updateTicket/{id}")
    public String showUpdateTicketForm(@PathVariable("id") Long id, Model model) {
        Optional<ticket> optionalTicket = ticketService.getTicketById(id);
        ticket ticket = optionalTicket.orElse(null);

        List<String> ticketSource = Arrays.asList("Phone", "Email", "Other");
        model.addAttribute("ticketSource", ticketSource);

        model.addAttribute("ticket", ticket);
        return "updateTicket";
    }
    @PostMapping("/update")
    public String updateTicket(@ModelAttribute ticket updatedticket) {

        ticketService.saveticket(updatedticket);
        return "redirect:/home";

    }
    @GetMapping("/admin")
    public String getUserList(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("user", userList);
        ticketRepository.truncateUserTicketCounts();
        ticketRepository.updateUserTicketCounts();
        List<UserTicketCountDTO> ticketCounts = ticketRepository.getUserTicketCountsFromTable();
        model.addAttribute("ticketCounts", ticketCounts);
        return "admin";
    }
    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInUserEmail = userDetails.getUsername();

        List<User> userList = userService.getAllUsers();
        Long userid = null;
        String enableduser = "";
        for (User i : userList) {
            if (Objects.equals(i.getEmail(), loggedInUserEmail)) {
                userid = i.getId();
                if (i.getEnabled().equals("0")) {
                    return "redirect:/adduser/login_disabled";
                }break;
            } else enableduser = "enabled";
        }
        model.addAttribute("userenabled", enableduser);
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("user", userList);
        model.addAttribute("userid", userid);
        ticketRepository.truncateUserTicketCounts();
        ticketRepository.updateUserTicketCounts();
        List<UserTicketCountDTO> ticketCounts = ticketRepository.getUserTicketCountsFromTable();
        model.addAttribute("ticketCounts", ticketCounts);
        return "home";
    }





}





