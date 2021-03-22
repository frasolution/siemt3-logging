package com.siemt3.watchdog_server;
/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
"bootstrap" method main does start our entire project

--*/
import com.siemt3.watchdog_server.cep.Engine;
import com.siemt3.watchdog_server.condb.DataBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WatchdogServerApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(WatchdogServerApplication.class, args);
		Engine.main();
	}

}
