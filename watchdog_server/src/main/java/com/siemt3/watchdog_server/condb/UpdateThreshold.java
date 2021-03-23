package com.siemt3.watchdog_server.condb;

import com.espertech.esper.common.internal.util.DeploymentIdNamePair;
import com.siemt3.watchdog_server.cep.Engine;
import com.siemt3.watchdog_server.cep.PEM;
import com.siemt3.watchdog_server.model.Threshold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        HashMap<String, ArrayList<Threshold>> hashMap = DataBase.fetch();
        ArrayList<Threshold> test = hashMap.get("test");
        test.forEach((threshold) -> {
            DeploymentIdNamePair deploymentIdNamePairCount = new DeploymentIdNamePair(PEM.getInstance().demoLogDeployment.getDeploymentId(), threshold.name);
            Map<DeploymentIdNamePair, Object> map = new HashMap<DeploymentIdNamePair,Object>();
            int count = threshold.count;
            map.put(deploymentIdNamePairCount, count);
            System.out.println(count + threshold.name);
            PEM.getInstance().runtime.getVariableService().setVariableValue(map);

        });
        Thread.sleep(60000);
        loop();
    }
}
