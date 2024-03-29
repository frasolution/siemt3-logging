package com.siemt3.watchdog_server.cep;

/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
Esper Engine implementation code
- actual code is in method perform
- currently contains proof of concept code


--*/
import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;

import com.siemt3.watchdog_server.cep.listener.apache2.Apache2AlertListener;
import com.siemt3.watchdog_server.cep.listener.apache2.Apache2BaseListener;
import com.siemt3.watchdog_server.cep.listener.apache2.Apache2WarnListener;
import com.siemt3.watchdog_server.cep.listener.port.PortBlockScanListener;
import com.siemt3.watchdog_server.cep.listener.port.PortDistBlockScanListener;
import com.siemt3.watchdog_server.cep.listener.port.PortDistHorizontalScanListener;
import com.siemt3.watchdog_server.cep.listener.port.PortDistVerticalScanListener;
import com.siemt3.watchdog_server.cep.listener.port.PortHorizontalScanListener;
import com.siemt3.watchdog_server.cep.listener.port.PortVerticalScanListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener.*;
import com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener.SshDictionaryFilterListener;
import com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener.SshSuccessfulFilterListener;
import com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener.SshDictionaryElevatedListener;
import com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener.SshIpElevatedListener;
import com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener.SshRootElevatedListener;
import com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener.SshUserElevatedListener;

public class Engine{

    public static void main() {
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
            System.out.println(message);
        });

        // -----------------------------------------------

        // -------------------SSH-------------------------
        EPDeployment sshDeployment = PEM.getInstance().sshDeployment;

        attacher(sshDeployment, "ssh-dictionary-filter-statement", new SshDictionaryFilterListener());

        attacher(sshDeployment, "ssh-root-filter-statement", new SshRootBasicListener());

        attacher(sshDeployment, "ssh-algorithm-filter-statement", new SshAlgorithmBasicListener());

        attacher(sshDeployment, "ssh-user-filter-statement", new SshUserBasicListener());

        attacher(sshDeployment, "ssh-ip-filter-statement", new SshIpBasicListener());

        attacher(sshDeployment, "ssh-successful-filter-statement", new SshSuccessfulFilterListener());

        attacher(sshDeployment, "ssh-dictionary-basic-statement", new SshDictionaryBasicListener());

        attacher(sshDeployment, "ssh-dictionary-elevated-statement", new SshDictionaryElevatedListener());

        attacher(sshDeployment, "ssh-root-elevated-statement", new SshRootElevatedListener());

        attacher(sshDeployment, "ssh-user-elevated-statement", new SshUserElevatedListener());

        attacher(sshDeployment, "ssh-ip-elevated-statement", new SshIpElevatedListener());

        // -------------------Port-------------------------

        EPDeployment portDeployment = PEM.getInstance().portDeployment;

        attacher(portDeployment, "port-vertical-scan-statement", new PortVerticalScanListener());

        attacher(portDeployment, "port-dist-vertical-scan-statement", new PortDistVerticalScanListener());

        attacher(portDeployment, "port-horizontal-scan-statement", new PortHorizontalScanListener());

        attacher(portDeployment, "port-dist-horizontal-scan-statement", new PortDistHorizontalScanListener());

        attacher(portDeployment, "port-block-scan-statement", new PortBlockScanListener());
        
        attacher(portDeployment, "port-dist-block-scan-statement", new PortDistBlockScanListener());

        
        // ------------------apache2-----------------------
        EPDeployment apache2Deployment = PEM.getInstance().apache2Deployment;

        //basic 404 event
        attacher(apache2Deployment, "apache2-log-404" , new Apache2BaseListener() );

        //basic warn event
        attacher(apache2Deployment, "apache2-basic-warning", new Apache2WarnListener());

        //apache2-alert-404-single-ip
        attacher(apache2Deployment, "apache2-alert-404-single-ip", new Apache2AlertListener());

        //apache2-alert-404-mult-ip
        attacher(apache2Deployment, "apache2-alert-404-mult-ip", new Apache2AlertListener());

        //ssl event
        attacher(apache2Deployment, "apache2-ssl-error", new Apache2AlertListener() );

    }

    private static void attacher(EPDeployment epDeployment, String statement, UpdateListener listener){
        try{
            PEM.getInstance().runtime
                    .getDeploymentService()
                    .getStatement(epDeployment.getDeploymentId(), statement)
                    .addListener(listener);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}
