package com.siemt3.watchdog_server.services;

import com.siemt3.watchdog_server.model.Alert;
import com.siemt3.watchdog_server.repositories.AlertRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public Alert getAlert(Integer id) throws Exception {
        return alertRepository.findById(id).orElseThrow(() -> new Exception("Alert does not exist."));
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Transactional
    public Alert createOrUpdateAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    @Transactional
    public void deleteAlert(Integer id) throws Exception {
        alertRepository.deleteById(id);
    }

}