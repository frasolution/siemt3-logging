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

public class Engien implements Runnable{

    private final String runtimeURI = "globalRuntime";

    public Engien() {
    }

    public static void main() throws Exception{
        Engien engien = new Engien();
        try{
            engien.run();
        }catch (Throwable t){
            System.out.println(t);
        }
    }

    public void run() {
        try {
            perform();
        } catch (Throwable t){
            System.out.println(t);
        }

    }

    private void perform () throws EPCompileException, EPDeployException{
        Configuration config = new Configuration();
        config.getCommon().addEventType(GudeEvent.class);

        EPCompiler compiler = EPCompilerProvider.getCompiler();
        CompilerArguments compilerArguments = new CompilerArguments(config);
        EPCompiled gudeCompiled;
        try {
            gudeCompiled = compiler.compile(
                    "@name('gude-event') select gude from GudeEvent",
                    compilerArguments);
        } catch (EPCompileException ex) {
            throw new RuntimeException(ex);
        }

        EPRuntime runtime = EPRuntimeProvider.getRuntime(runtimeURI, config);

        EPDeployment deployed = runtime.getDeploymentService().deploy(gudeCompiled);
        deployed.getStatements()[0].addListener((newData, oldData, statement, runtimex) -> {
            String message = (String) newData[0].get("gude");
            System.out.println(String.format("GudeEvent with msg: %s", message));
        });
    }

}
