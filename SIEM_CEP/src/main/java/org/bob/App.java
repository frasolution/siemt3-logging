package org.bob;

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
import com.espertech.esper.runtime.client.EPRuntimeProvider;
import com.espertech.esper.runtime.client.EPStatement;

import java.io.File;
import java.io.IOException;

/**
 * @author Nicolas Roth, Robert Uwe Litschel
 *
 */
public class App {

    public static void main(String[] args) throws IOException, ParseException {

        new App();
    }

    public App() throws IOException, ParseException {
        // log4j initialization
        org.apache.log4j.BasicConfigurator.configure();

        Configuration cfg = new Configuration();
        cfg.getCommon().addEventType(Apache2LogMessage.class);
        cfg.getCommon().addEventType(Apache2FailedLogMessage.class);
        cfg.getCommon().addEventType(Apache2Alert.class);

        EPRuntime runtime = EPRuntimeProvider.getDefaultRuntime(cfg);
        setupApache2LogCEP(cfg, runtime);

        this.startApache2LogThread(runtime);
    }


    private void  setupApache2LogCEP(Configuration cfg, EPRuntime runtime) throws IOException, ParseException {
        CompilerArguments compilerArguments = new CompilerArguments(cfg);
        //todo: adjust path
        File file = new File("/home/ace/code/apache2.epl");
        Module module = EPCompilerProvider.getCompiler().readModule(file);

        EPCompiler compiler = EPCompilerProvider.getCompiler();
        EPCompiled epCompiled;
        try {
            //epCompiled = compiler.compile("@name('apache2.txt-log-400-message') insert into Apache2FailedLogMessage select message from Apache2LogMessage where message like '%400%'", compilerArguments);
            epCompiled = compiler.compile(module, compilerArguments);
        }
        catch (EPCompileException ex) {
            throw new RuntimeException(ex);
        }

        EPDeployment deployment;
        try {
            deployment = runtime.getDeploymentService().deploy(epCompiled);
        } catch (EPDeployException ex) {
            throw new RuntimeException(ex);
        }

        EPStatement statement = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(), "apache2-400-alert");
        statement.addListener( (newData, oldData, statementx, runtimex) -> {
            Long failedAttempts = (Long) newData[0].get("failedAttempts");
            System.out.println(String.format("Apache2Alert: failed attempts: %d", failedAttempts));
        });
    }


    private void startApache2LogThread(EPRuntime runtime) {
        System.out.printf("new Thread\n");
        new Thread(() -> {
            try {
                new Apache2LogMessageHandler(runtime.getEventService());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
