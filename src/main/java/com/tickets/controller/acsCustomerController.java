package com.tickets.controller;


import com.tickets.entity.acsCustomer;
import com.tickets.repository.AcsCustomerRepository;
import com.tickets.service.AcsCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class acsCustomerController {

    @Autowired
    private AcsCustomerService acsCustomerService;

    @Autowired
    private AcsCustomerRepository acsCustomerRepository;

    @GetMapping("/registration/acsCustomer")
    public String showRegistrationForm(Model model) {
        model.addAttribute("acsCustomer", new acsCustomer());
        return "acsCustomer";
    }

    @PostMapping("/registration/acsCustomer")
    public String registerAcsCustomer(@ModelAttribute("acsCustomer") acsCustomer acsCustomer, Model model) {
        // Perform validation and registration logic
        acsCustomerService.registerAcsCustomer(acsCustomer);

        // Set success parameter for displaying success message in the template
        model.addAttribute("success", "successfully registered new merchant");
        return "redirect:/registration/acsCustomer?success";
    }


    @GetMapping("/test")
    public String showest(@RequestParam(name="search",required = false) String search ,
                          Model model) {
        List<acsCustomer> acsCustomers = acsCustomerRepository.findAll();

        if (search != null && !search.isEmpty()) {
            acsCustomers = acsCustomerService.findCustomers(search);
        } else acsCustomerRepository.findAll();

        model.addAttribute("acsCustomer", acsCustomers);
        model.addAttribute("search",search);
        return "test";
    }


    @GetMapping("/acs_list")
    public String showacslist(
            @RequestParam(name = "search",required = false) String search,
            Model model) {
        List<acsCustomer> acsCustomers = acsCustomerService.getAllCustomers();
        if (search != null && !search.isEmpty()) {
            acsCustomers = acsCustomerService.findCustomers(search);
        } else {
            acsCustomers = acsCustomerRepository.findAll();
        }

        model.addAttribute("acsCustomer", acsCustomers);
        model.addAttribute("search", search);
        return "acs_list";
    }
    @GetMapping("/acs_list/{id}")
    public String showUpdateMerchantForm(@PathVariable("id") Integer id, Model model) {

        Optional<acsCustomer> optionalAcsCustomer = acsCustomerRepository.findById(id);
        acsCustomer acsCustomer = optionalAcsCustomer.orElse(null);
        model.addAttribute("acsCustomer", acsCustomer);
        return "updateMerchant";
    }

    @PostMapping("/acs_list")
    public String updateMerchant(@ModelAttribute("acsCustomer") acsCustomer updateacscustomer,Model model) {
        acsCustomer existingcustomer = acsCustomerRepository.findById(updateacscustomer.getId()).orElse(null);
        if (existingcustomer != null) {

        existingcustomer.setAcsCustomerId(updateacscustomer.getAcsCustomerId());
        existingcustomer.setAcsClientName(updateacscustomer.getAcsClientName());
        existingcustomer.setAcsAFM(updateacscustomer.getAcsAFM());
        existingcustomer.setAcsCity(updateacscustomer.getAcsCity());
        existingcustomer.setAcsAddress(updateacscustomer.getAcsAddress());
        existingcustomer.setAcsEmail(updateacscustomer.getAcsEmail());
        existingcustomer.setAcsPhoneNumber(updateacscustomer.getAcsPhoneNumber());

        acsCustomerRepository.save(existingcustomer);
        }

        return "redirect:/acs_list?success2";
    }


    @PostMapping("/acs_list/export")
    public String acsListExport() {
        acsListExport.exportacsList();
        return "redirect:/acs_list";
    }




}

















