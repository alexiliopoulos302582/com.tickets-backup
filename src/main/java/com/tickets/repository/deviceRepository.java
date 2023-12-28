package com.tickets.repository;

import com.tickets.entity.device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface deviceRepository extends JpaRepository<device, Integer> {



    @Query("SELECT t FROM device t WHERE " +
            "t.serialNumber LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.merchant LIKE %?1% ")
    List<device> findAllBySearch(String search);

    Optional<device> findBySerialNumber(String serialNumber);
}
