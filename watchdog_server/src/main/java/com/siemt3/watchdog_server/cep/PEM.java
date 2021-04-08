package com.siemt3.watchdog_server.cep;
/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
Permanent Engine Manager
Manager Singleton that "manages" our variables which are need to be accessed throughout the entire project

--*/
import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.common.client.module.Module;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;
import com.siemt3.watchdog_server.cep.event.apache2Events.Apache2LogEvent;
import com.siemt3.watchdog_server.cep.event.demoEvents.DemoLogEvent;
import com.siemt3.watchdog_server.cep.event.demoEvents.GudeEvent;
import com.siemt3.watchdog_server.cep.event.sshEvents.*;
import com.siemt3.watchdog_server.cep.event.sshEvents.elevated.SshDictionaryElevatedEvent;

import java.io.File;

public class PEM {

    //uses singleton pattern for access of variables below
    private static PEM instance;

    // having config runtime and runtimeuri available for when needed
    public Configuration config;
    public String runtimeURI;
    public EPRuntime runtime;
    public EPDeployment testDeployment;
    public EPDeployment sshDeployment;

    public PEM(){

        this.config = new Configuration();

        //test
        this.config.getCommon().addEventType(GudeEvent.class);
        this.config.getCommon().addEventType(DemoLogEvent.class);

        //ssh
        this.config.getCommon().addEventType(SshBaseLogEvent.class);
        this.config.getCommon().addEventType(SshDictionaryEvent.class);
        this.config.getCommon().addEventType(SshIpFilterEvent.class);
        this.config.getCommon().addEventType(SshIpEvent.class);
        this.config.getCommon().addEventType(SshRootEvent.class);
        this.config.getCommon().addEventType(SshUserEvent.class);

        this.config.getCommon().addEventType(SshDictionaryElevatedEvent.class);

        //apache2
        this.config.getCommon().addEventType(Apache2LogEvent.class);


        this.runtimeURI = "globalRuntime";
        this.runtime = EPRuntimeProvider.getRuntime(this.runtimeURI, this.config);

        //Deployments

        // setup compiler
        EPCompiler compiler = EPCompilerProvider.getCompiler();
        CompilerArguments compilerArguments = new CompilerArguments(config);

        //compile module test/demolog from file in res
        String fileName = "testStatement.epl";
        ClassLoader classLoader = getClass().getClassLoader();
        EPCompiled demoLogCompiled = null;
        try {
            File file = new File(classLoader.getResource(fileName).getFile());
            Module module = EPCompilerProvider.getCompiler().readModule(file);
            demoLogCompiled = compiler.compile(module, compilerArguments);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //compile module ssh from file in res
        String sshStatementFileName = "sshStatement.epl";
//        ClassLoader classLoader = getClass().getClassLoader();
        EPCompiled sshLogCompiled = null;
        try {
            File sshFile = new File(classLoader.getResource(sshStatementFileName).getFile());
            Module sshModule = EPCompilerProvider.getCompiler().readModule(sshFile);
            sshLogCompiled = compiler.compile(sshModule, compilerArguments);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Deploy compiled statements
        try {
            testDeployment = runtime.getDeploymentService().deploy(demoLogCompiled);
            sshDeployment = runtime.getDeploymentService().deploy(sshLogCompiled);
        } catch (EPDeployException ex) {
            // handle exception here
            throw new RuntimeException(ex);
        }
    }

    public static PEM getInstance(){
        if (PEM.instance == null){
            PEM.instance = new PEM();
        }
        return instance;
    }

}
