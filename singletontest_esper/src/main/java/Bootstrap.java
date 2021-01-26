import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.runtime.client.EPEventService;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        Engien.main();
        Configuration config = new Configuration();
        config.getCommon().addEventType(GudeEvent.class);
        String runtimeURI = "globalRuntime";
        EPRuntime runtime = EPRuntimeProvider.getRuntime(runtimeURI, config);

        EPEventService epService = runtime.getEventService();
        epService.sendEventBean(new GudeEvent("test"), "GudeEvent");
    }


}
