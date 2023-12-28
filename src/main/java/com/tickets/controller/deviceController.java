package com.tickets.controller;

import com.tickets.entity.device;
import com.tickets.repository.deviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class deviceController {

    @Autowired
    public deviceRepository deviceRepository;


    @GetMapping("/device")
    public String showdevices(@RequestParam(name = "search", required = false)
                              String search, Model model) {
        List<device> devices = deviceRepository.findAll();

        if (search != null && !search.isEmpty()) {
            devices = deviceRepository.findAllBySearch(search);

        } else {
            devices = deviceRepository.findAll();
        }


        model.addAttribute("devices", devices);
        model.addAttribute("search", search);

        return "device";
    }


    @GetMapping("/device/{id}")
    public String showupdatescreen(@PathVariable("id") Integer id, Model model) {
        Optional<device> optionalDevice = deviceRepository.findById(id);
        device device = optionalDevice.orElse(null);
        model.addAttribute("device", device);
        return "updateserial";

    }

    @PostMapping("device")
    public String updateserial(@ModelAttribute("device") device updatedevice, Model model) {
        device existingdevice = deviceRepository.findById(updatedevice.getId()).orElse(null);

        if (existingdevice != null) {
            existingdevice.setDeviceModel(updatedevice.getDeviceModel());
            existingdevice.setMerchant(updatedevice.getMerchant());
            existingdevice.setSerialNumber(updatedevice.getSerialNumber());

            deviceRepository.save(existingdevice);
        }return "redirect:/device?success2";

    }

    @PostMapping("/device/export")
    public String devicesExport() {
        ExportSerialNumbersList_txt.exportserialnums_txt();
        return "redirect:/device";
    }

    @GetMapping("/adddevice")
    public String adddevice(Model model) {
        model.addAttribute("device", new device());


        return "adddevice";
    }
    @PostMapping("/adddevice")
    public String adddevicetodb(@ModelAttribute device device) {
        deviceRepository.save(device);


        return "redirect:/adddevice?success";
    }


}
