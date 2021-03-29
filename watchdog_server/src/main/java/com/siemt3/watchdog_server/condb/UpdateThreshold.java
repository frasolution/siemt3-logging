package com.siemt3.watchdog_server.condb;

import com.espertech.esper.common.internal.util.DeploymentIdNamePair;
import com.espertech.esper.runtime.client.EPDeployment;
import com.siemt3.watchdog_server.GlobalVariables;
import com.siemt3.watchdog_server.cep.PEM;
import com.siemt3.watchdog_server.model.Threshold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

public class UpdateThreshold implements Runnable {
    public UpdateThreshold(){}

    public static void main() throws Exception {
        UpdateThreshold updateThreshold = new UpdateThreshold();
        try {
            updateThreshold.run();
        } catch (Throwable t) {
            System.out.println(t);
        }
    }

    public void run(){
        try{
            loop();
        } catch (Throwable t){
            System.out.println(t);
        }
    }

    private void loop() throws Exception{
        ArrayList<Threshold> arrayList = DataBase.fetch();

        // get all deployments before loop to reduce access counts
        EPDeployment ssh = PEM.getInstance().sshDeployment;
        EPDeployment test = PEM.getInstance().testDeployment;

        arrayList.forEach((threshold) -> {
            String name = threshold.name;
            String type = threshold.type;
            int count = threshold.number;
            EPDeployment localDeployment = null;

            switch(type){
                case "ssh":
                    localDeployment = ssh;
                    break;
                case "test":
                    localDeployment = test;
                    break;
                default:
                    break;
            }
            // NOTE  clean code pay a huge inefficiency cost!!!
            // Ideally we use a full map for each deployment to be put in the place of the old one
            DeploymentIdNamePair deploymentIdNamePairCount = new DeploymentIdNamePair(localDeployment.getDeploymentId(), name );
            Map<DeploymentIdNamePair, Object> map = new HashMap<DeploymentIdNamePair,Object>();
            map.put(deploymentIdNamePairCount, count);

            if (DEBUG_FLAG) {
                System.out.println(count + name);
            }

            PEM.getInstance().runtime.getVariableService().setVariableValue(map);
        });
        Thread.sleep(GlobalVariables.DBUPDATECYCLE);
        loop();
    }
}
