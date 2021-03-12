package com.siemt3.watchdog_server.repositories;

import com.siemt3.watchdog_server.model.Alert;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Integer> {

    @Override
    List<Alert> findAll();

    @Override
    Optional<Alert> findById(Integer id);



}
