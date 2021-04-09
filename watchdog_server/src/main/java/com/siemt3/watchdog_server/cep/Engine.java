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
import com.espertech.esper.common.client.module.ParseException;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;

import com.siemt3.watchdog_server.cep.listener.apache2.Apache2AlertListener;
import com.siemt3.watchdog_server.cep.listener.apache2.Apache2BaseListener;
import com.siemt3.watchdog_server.cep.listener.apache2.Apache2WarnListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.SshAlgorithmBasicListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.SshRootBasicListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.SshUserBasicListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener.SshDictionaryFilterListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.SshIpBasicListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener.SshSuccessfulFilterListener;
import com.siemt3.watchdog_server.condb.DataBase;

import java.io.File;
import java.io.IOException;
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
//        String sshStatementFileName = "sshStatement.epl";
//        ClassLoader classLoader = getClass().getClassLoader();
//        EPCompiled sshLogCompiled = null;
//        try {
//            File sshFile = new File(classLoader.getResource(sshStatementFileName).getFile());
//            Module sshModule = EPCompilerProvider.getCompiler().readModule(sshFile);
//            sshLogCompiled = compiler.compile(sshModule, compilerArguments);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        EPDeployment sshLogDeployment;
//        try {
//            sshLogDeployment = runtime.getDeploymentService().deploy(sshLogCompiled);
//        } catch (EPDeployException ex) {
//            // handle exception here
//            throw new RuntimeException(ex);
//        }
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








        // #############################
        // apache2 module, statements and listener
        EPCompiled apache2Compiled = null;
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            File apache2File = new File(classLoader.getResource("apache2Statement.epl").getFile());
            Module apache2Module = EPCompilerProvider.getCompiler().readModule(apache2File);
            apache2Compiled = compiler.compile(apache2Module, compilerArguments);
        } catch (EPCompileException exception) {
            System.out.println("engine: EPCompileException");
        }catch (IOException exception){
            System.out.println("engine: IOException");
        }catch (ParseException exception){
            System.out.println("engine: ParseException");
        }

        try {
            deployment = runtime.getDeploymentService().deploy(apache2Compiled);
        } catch (EPDeployException ex) {
            throw new RuntimeException(ex);
        }



        //changing variable threshold
//        DeploymentIdNamePair var_threshold_404 = new DeploymentIdNamePair(deployment.getDeploymentId(), "var_threshold_404");
//        Map<DeploymentIdNamePair, Object> map = new HashMap<DeploymentIdNamePair,Object>();
//        Long thres_404 = (long) 10;
//        map.put(var_threshold_404, thres_404);
//        runtime.getVariableService().setVariableValue(map);



        //basic 404 event
        try {
            EPStatement statement_apache2 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(), "apache2-log-404");
            statement_apache2.addListener(new Apache2BaseListener());
        } catch (NullPointerException exception){
            System.out.println("NullPointerException: engine statement apache2-log-404");
        }

        //basic warn event
        try {
            EPStatement statement_apache2 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(), "apache2-basic-warning");
            statement_apache2.addListener(new Apache2WarnListener());
        } catch (NullPointerException exception){
            System.out.println("NullPointerException: engine statement 'apache2-basic-warning'");
        }


        //apache2-alert-404-single-ip
        try {
            EPStatement statement_apache2 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(), "apache2-alert-404-single-ip");
            statement_apache2.addListener(new Apache2AlertListener());
        } catch (NullPointerException exception){
            System.out.println("engine: NullPointerException - apache2-alert-404-single-ip");
        }



        //apache2-alert-404-mult-ip
        try {
            EPStatement statement_apache2 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(), "apache2-alert-404-mult-ip");
            statement_apache2.addListener(new Apache2AlertListener());
        } catch (NullPointerException exception){
            System.out.println("engine: NullPointerException - apache2-alert-404-mult-ip");
        }

        //ssl event
        try {
            EPStatement statement_apache2 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(), "apache2-ssl-error");
            statement_apache2.addListener(new Apache2AlertListener());
        } catch (NullPointerException exception){
            System.out.println("NullPointerException: engine statement apache2-ssl-error");
        }



    }
}
