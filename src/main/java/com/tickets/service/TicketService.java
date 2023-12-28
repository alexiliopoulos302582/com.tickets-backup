package com.tickets.service;


import com.tickets.repository.ticketRepository;

import com.tickets.entity.ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TicketService {
    private final ticketRepository ticketRepository;

    @Autowired
    public TicketService(ticketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    public ticket saveticket(ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Optional<ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<ticket> findAllByKeyword(String keyword) {
        if (keyword != null) {
            return ticketRepository.findAllByKeyword(keyword);

        }
        return ticketRepository.findClosedtickets();
    }


}
