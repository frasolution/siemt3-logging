package com.siemt3.watchdog_server;

import com.siemt3.watchdog_server.cep.Engine2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WatchdogServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchdogServerApplication.class, args);
		Engine2.main();
	}

}
