package com.siemt3.watchdog_server.cep;

/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
Esper Engine implementation code
- the engine gets started with main and creates a new thread. Please call main only once with "bootstrap"
- actual code is in method perform
- currently contains proof of concept code


--*/
import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.common.client.module.Module;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;

import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.SshAlgorithmBasicListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.SshRootBasicListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.SshUserBasicListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener.SshDictionaryFilterListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.SshIpBasicListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener.SshSuccessfulFilterListener;
import com.siemt3.watchdog_server.condb.DataBase;

import java.io.File;
import java.sql.SQLException;

public class Engine implements Runnable {
    private final String runtimeURI = PEM.getInstance().runtimeURI;

    public Engine() {
    }

    public static void main() throws Exception {
        Engine engien = new Engine();
        try {
            engien.run();
        } catch (Throwable t) {
            System.out.println(t);
        }
    }

    public void run() {
        try {
            perform();
        } catch (Throwable t) {
            System.out.println(t);
        }

    }

    private void perform() throws EPCompileException, EPDeployException {
        Configuration config = PEM.getInstance().config;
        EPRuntime runtime = PEM.getInstance().runtime;

        CompilerArguments compilerArguments = new CompilerArguments(config);
        EPCompiler compiler = EPCompilerProvider.getCompiler();
        // -----------------DEMO CODE ESPER ------------------------
        // event when demolog does contain "gude" as substring
        EPCompiled gudeCompiled;
        try {
            gudeCompiled = compiler.compile(
                    // the insert is not required!!!
                    // actually causes compiler error
                    "@name('gude-event') inlined_class \"\"\" \n"
                            + "import com.espertech.esper.common.client.hook.singlerowfunc.*;"
                            + "  @ExtensionSingleRowFunction(name=\"computePercent\", methodName=\"computePercent\")\n"
                            + "  public class MyUtilityClass {\n"
                            + "    public static String computePercent(String s) {\n" + "       System.out.println(s);"
                            + "       return \"gude\";" + "    }\n" + "  }\n" + "\"\"\" "
                            + "select computePercent(sus) as gude from DemoLogEvent",
                    compilerArguments);
        } catch (EPCompileException ex) {
            throw new RuntimeException(ex);
        }

        EPDeployment deployment;
        try {
            deployment = runtime.getDeploymentService().deploy(gudeCompiled);
        } catch (EPDeployException ex) {
            throw new RuntimeException(ex);
        }

        EPStatement statement = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(), "gude-event");
        statement.addListener((newData, oldData, statementx, runtimex) -> {
            String message = (String) newData[0].get("gude");
            System.out.println(String.format("GudeEvent with msg: %s", message));
        });

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

        // // add demolog statement and compile
        // EPCompiled demoLogCompiled;
        // try {
        // demoLogCompiled = compiler.compile("@name('demolog-statement') select sus
        // from DemoLogEvent where sus like '192.168.%.%'", compilerArguments);
        // }
        // catch (EPCompileException ex) {
        // // handle exception here
        // throw new RuntimeException(ex);
        // }

        EPDeployment demoLogDeployment;
        try {
            demoLogDeployment = runtime.getDeploymentService().deploy(demoLogCompiled);
        } catch (EPDeployException ex) {
            // handle exception here
            throw new RuntimeException(ex);
        }

        // log listener outputs failure events
        EPStatement demologStatement = runtime.getDeploymentService().getStatement(demoLogDeployment.getDeploymentId(),
                "demolog-statement");
        demologStatement.addListener((newData, oldData, statementx, runtimex) -> {
            String message = (String) newData[0].get("sus");
            try {
                DataBase.dbCommit(message);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            System.out.println(message);
        });

        // -----------------------------------------------

        // -------------------SSH-------------------------
        // test implementation SSH
        // add sshlog statement and compile
        // EPCompiled sshLogCompiled;
        // try {
        // sshLogCompiled = compiler.compile("@name('sshlog-statement') select log as
        // message from SSHBaseLogEvent", compilerArguments);
        // }
        // catch (EPCompileException ex) {
        // // handle exception here
        // throw new RuntimeException(ex);
        // }
        //
        // EPDeployment sshLogDeployment;
        // try {
        // sshLogDeployment = runtime.getDeploymentService().deploy(sshLogCompiled);
        // }
        // catch (EPDeployException ex) {
        // // handle exception here
        // throw new RuntimeException(ex);
        // }
        // EPStatement sshLogStatement =
        // runtime.getDeploymentService().getStatement(sshLogDeployment.getDeploymentId(),
        // "sshlog-statement");
        // sshLogStatement.addListener( (newData, oldData, statementx, runtimex) -> {
        // String message = (String) newData[0].get("message");
        // System.out.println(message);
        // });

        String sshStatementFileName = "sshStatement.epl";
        EPCompiled sshLogCompiled = null;
        try {
            File sshFile = new File(classLoader.getResource(sshStatementFileName).getFile());
            Module sshModule = EPCompilerProvider.getCompiler().readModule(sshFile);
            sshLogCompiled = compiler.compile(sshModule, compilerArguments);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EPDeployment sshLogDeployment;
        try {
            sshLogDeployment = runtime.getDeploymentService().deploy(sshLogCompiled);
        } catch (EPDeployException ex) {
            // handle exception here
            throw new RuntimeException(ex);
        }

        //TODO Abstract this into a for each loop with resolver of listener function and statement <String,Object{String, Object}>
        EPStatement listenerAttacheds1 = runtime.getDeploymentService().getStatement(sshLogDeployment.getDeploymentId(),
                "ssh-dictionary-filter-statement");
        listenerAttacheds1.addListener(new SshDictionaryFilterListener());

        EPStatement listenerAttacheds2 = runtime.getDeploymentService().getStatement(sshLogDeployment.getDeploymentId(),
                "ssh-root-filter-statement");
        listenerAttacheds2.addListener(new SshRootBasicListener());

        EPStatement listenerAttacheds3 = runtime.getDeploymentService().getStatement(sshLogDeployment.getDeploymentId(),
                "ssh-algorithm-filter-statement");
        listenerAttacheds3.addListener(new SshAlgorithmBasicListener());

        EPStatement listenerAttacheds4 = runtime.getDeploymentService().getStatement(sshLogDeployment.getDeploymentId(),
                "ssh-user-filter-statement");
        listenerAttacheds4.addListener(new SshUserBasicListener());

        EPStatement listenerAttacheds5 = runtime.getDeploymentService().getStatement(sshLogDeployment.getDeploymentId(),
                "ssh-ip-filter-statement");
        listenerAttacheds5.addListener(new SshIpBasicListener());

        EPStatement listenerAttacheds6 = runtime.getDeploymentService().getStatement(sshLogDeployment.getDeploymentId(),
                "ssh-successful-filter-statement");
        listenerAttacheds6.addListener(new SshSuccessfulFilterListener());

    }
}
