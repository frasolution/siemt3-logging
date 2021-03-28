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

import com.siemt3.watchdog_server.cep.listener.apache2.Apache2BaseListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.*;
import com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener.SshDictionaryFilterListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener.SshSuccessfulFilterListener;
import com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener.SshDictionaryElevatedListener;
import com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener.SshIpElevatedListener;
import com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener.SshRootElevatedListener;
import com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener.SshUserElevatedListener;
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

        // log listener outputs failure events
        EPStatement demologStatement = runtime.getDeploymentService()
                .getStatement(PEM.getInstance().testDeployment.getDeploymentId(), "demolog-statement");
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
        EPDeployment sshDeployment = PEM.getInstance().sshDeployment;

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-dictionary-filter-statement")
                .addListener(new SshDictionaryFilterListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-root-filter-statement")
                .addListener(new SshRootBasicListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-algorithm-filter-statement")
                .addListener(new SshAlgorithmBasicListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-user-filter-statement")
                .addListener(new SshUserBasicListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-ip-filter-statement")
                .addListener(new SshIpBasicListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-successful-filter-statement")
                .addListener(new SshSuccessfulFilterListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-dictionary-basic-statement")
                .addListener(new SshDictionaryBasicListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-dictionary-elevated-statement")
                .addListener(new SshDictionaryElevatedListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-root-elevated-statement")
                .addListener(new SshRootElevatedListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-user-elevated-statement")
                .addListener(new SshUserElevatedListener());

        runtime.getDeploymentService()
                .getStatement(sshDeployment.getDeploymentId(), "ssh-ip-elevated-statement")
                .addListener(new SshIpElevatedListener());

        // #############################
        // apache2 module, statements and listener
        EPCompiled apache2Compiled = null;
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            File apache2File = new File(classLoader.getResource("apache2Statement.epl").getFile());
            Module apache2Module = EPCompilerProvider.getCompiler().readModule(apache2File);
            apache2Compiled = compiler.compile(apache2Module, compilerArguments);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            deployment = runtime.getDeploymentService().deploy(apache2Compiled);
        } catch (EPDeployException ex) {
            throw new RuntimeException(ex);
        }

        EPStatement statement_apache2 = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(),
                "apache2-log-404");
        statement_apache2.addListener(new Apache2BaseListener());

    }
}
