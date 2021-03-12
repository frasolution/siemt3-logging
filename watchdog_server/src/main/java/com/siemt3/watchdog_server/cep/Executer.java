package com.siemt3.watchdog_server.cep;

import com.espertech.esper.common.internal.epl.join.strategy.ExecNode;
import com.siemt3.watchdog_server.model.Alert;
import com.siemt3.watchdog_server.services.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Component
public class Executer implements CommandLineRunner {
    @Autowired
    private AlertService myService;

    @Override
    public void run(String... args) throws Exception {
        String[] arr = new String[1];
        int i = 0;
        for (String s :
                args) {
            if (i > 1)
                break;
            arr[i] = s;
            i++;
            System.out.println(s);
        }
        System.out.println(arr[0]);
        Alert newAlert = new Alert();
        newAlert.setEventId("MSG EXECUTER");
        newAlert.setEventType(arr[0]);
        newAlert.setEventName("MSG EXECUTER");
        newAlert.setCreationDateTime();
        myService.createOrUpdateAlert(newAlert);
    }

    public void run(Alert alert) throws Exception {
        myService.createOrUpdateAlert(alert);
    }

}
