package com.tickets.repository;

import com.tickets.entity.ticket;
import com.tickets.service.UserTicketCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface ticketRepository extends JpaRepository<ticket,Long> {
    List<ticket> findByticketState(int ticketState);

    @Query("SELECT t FROM ticket t WHERE " +
            "(t.phoneNumber LIKE %?1% OR " +
            "t.city LIKE %?1% OR " +
            "t.clientName LIKE %?1% OR " +
            "t.email LIKE %?1% OR " +
            "t.serialNumber LIKE %?1% OR " +
            "t.address LIKE %?1% OR " +
            "t.subject LIKE %?1% OR " +
            "t.department LIKE %?1% OR " +
            "t.helpTopic LIKE %?1% OR " +
            "t.priority LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.category LIKE %?1% OR " +
            "t.assignedTo LIKE %?1%) AND t.ticketState = 0 " +
            "ORDER BY t.creationDate DESC")
    List<ticket> findAllByKeyword(String keyword);
    @Query("SELECT t FROM ticket t WHERE t.ticketState = 0 ORDER BY t.creationDate DESC")
    List<ticket> findClosedtickets();
    Optional<ticket> findById(Long id);

    @Query("SELECT t FROM ticket t WHERE " +
            "((t.phoneNumber LIKE %?1% OR " +
            "t.city LIKE %?1% OR " +
            "t.clientName LIKE %?1% OR " +
            "t.email LIKE %?1% OR " +
            "t.serialNumber LIKE %?1% OR " +
            "t.address LIKE %?1% OR " +
            "t.subject LIKE %?1% OR " +
            "t.department LIKE %?1% OR " +
            "t.helpTopic LIKE %?1% OR " +
            "t.priority LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.category LIKE %?1% OR " +
            "t.assignedTo LIKE %?1%) AND t.ticketState = 0) " +
            "ORDER BY t.creationDate DESC")
    List<ticket> findClosedticketsBySearch(String search);


    @Query("SELECT t FROM ticket t WHERE " +
            "(t.phoneNumber LIKE %?1% OR " +
            "t.city LIKE %?1% OR " +
            "t.clientName LIKE %?1% OR " +
            "t.email LIKE %?1% OR " +
            "t.serialNumber LIKE %?1% OR " +
            "t.address LIKE %?1% OR " +
            "t.subject LIKE %?1% OR " +
            "t.department LIKE %?1% OR " +
            "t.helpTopic LIKE %?1% OR " +
            "t.priority LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.category LIKE %?1% OR " +
            "t.assignedTo LIKE %?1%) AND t.ticketState = 1 ")
    List<ticket> findOpenticketsBySearch(String search);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE user_ticket_counts", nativeQuery = true)
    void truncateUserTicketCounts();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_ticket_counts (assigned_to, open_tickets_count, closed_tickets_count) " +
            "SELECT assigned_to, " +
            "SUM(CASE WHEN ticket_state = 1 THEN 1 ELSE 0 END) AS open_tickets_count, " +
            "SUM(CASE WHEN ticket_state = 0 THEN 1 ELSE 0 END) AS closed_tickets_count " +
            "FROM ticket " +
            "GROUP BY assigned_to", nativeQuery = true)
    void updateUserTicketCounts();

    @Query("SELECT t FROM UserTicketCountDTO t")
    List<UserTicketCountDTO> getUserTicketCountsFromTable();
}

//("SELECT t FROM ticket t WHERE "+
//        "(t.phoneNumber LIKE %?1% OR " +
//        "t.city LIKE %?1% OR " +
//        "t.fullName LIKE %?1% OR " +
//        "t.email LIKE %?1% OR " +
//        "t.serialNumber LIKE %?1% OR " +
//        "t.userName LIKE %?1% OR " +
//        "t.subject LIKE %?1% OR " +
//        "t.department LIKE %?1% OR " +
//        "t.helpTopic LIKE %?1% OR " +
//        "t.priority LIKE %?1% OR " +
//        "t.deviceModel LIKE %?1% OR " +
//        "t.category LIKE %?1% OR " +
//        "t.assignedTo LIKE %?1%) AND t.ticketState = 0 ")




//    @Query("SELECT t FROM ticket t WHERE " +
//            "((t.phoneNumber LIKE %?1% OR " +
//            "t.city LIKE %?1% OR " +
//            "t.fullName LIKE %?1% OR " +
//            "t.email LIKE %?1% OR " +
//            "t.serialNumber LIKE %?1% OR " +
//            "t.userName LIKE %?1% OR " +
//            "t.subject LIKE %?1% OR " +
//            "t.department LIKE %?1% OR " +
//            "t.helpTopic LIKE %?1% OR " +
//            "t.priority LIKE %?1% OR " +
//            "t.deviceModel LIKE %?1% OR " +
//            "t.category LIKE %?1% OR " +
//            "t.assignedTo LIKE %?1%) AND t.ticketState = 0) " +
//            "ORDER BY t.creationDate DESC")
//    List<ticket> findClosedticketsBySearch(String search);