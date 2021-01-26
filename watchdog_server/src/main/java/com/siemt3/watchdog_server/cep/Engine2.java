package com.siemt3.watchdog_server.cep;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;
import com.espertech.esper.runtime.client.EPStatement;
import com.siemt3.watchdog_server.cep.event.DemoLogEvent;
import com.siemt3.watchdog_server.cep.event.GudeEvent;
import com.siemt3.watchdog_server.controller.DemologController;


public class Engine2 {

    public static void main() {
        System.out.println("lol");
        new Engine2();
    }

    public Engine2(){
        // log4j initialization
        org.apache.log4j.BasicConfigurator.configure();
        Configuration cfg = new Configuration();
        cfg.getCommon().addEventType(DemoLogEvent.class);
        cfg.getCommon().addEventType(GudeEvent.class);

        EPRuntime runtime = EPRuntimeProvider.getDefaultRuntime(cfg);
    }


    private void setupDemoLogCEP( Configuration cfg, EPRuntime runtime){
        CompilerArguments compilerArguments = new CompilerArguments(cfg);
        EPCompiler compiler = EPCompilerProvider.getCompiler();

        EPCompiled gudeCompiled;
        try {
            gudeCompiled = compiler.compile(
                    "@name('gude-event') insert into GudeEvent select message from DemoLogEvent where message like '%gude%'",
                    compilerArguments);
        } catch (EPCompileException ex) {
            throw new RuntimeException(ex);
        }

        EPDeployment deployment;
        try {
            deployment = runtime.getDeploymentService().deploy(gudeCompiled);
        } catch (EPDeployException ex) {
            // TODO: handle exception
            throw new RuntimeException(ex);
        }

        EPStatement statement = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(),
                "gude-event");
        statement.addListener((newData, oldData, statementx, runtimex) -> {
            String message = (String) newData[0].get("message");
            System.out.println(String.format("GudeEvent with msg: %s", message));
        });




        // add sshlog statement and compile
        EPCompiled demoLogCompiled;
        try {
            demoLogCompiled = compiler.compile("@name('demolog-statement') select message, timestamp from DemoLogEvent", compilerArguments);
        }
        catch (EPCompileException ex) {
            // handle exception here
            throw new RuntimeException(ex);
        }
        
        EPDeployment demoLogDeployment;
        try {
            demoLogDeployment = runtime.getDeploymentService().deploy(demoLogCompiled);
        }
        catch (EPDeployException ex) {
            // handle exception here
            throw new RuntimeException(ex);
        }

        // log listener outputs failure events
        EPStatement demologStatement = runtime.getDeploymentService().getStatement(demoLogDeployment.getDeploymentId(), "demolog-statement");
        demologStatement.addListener( (newData, oldData, statementx, runtimex) -> {
            String message = (String) newData[0].get("sus");
            System.out.println(message);
        });


    }

}
