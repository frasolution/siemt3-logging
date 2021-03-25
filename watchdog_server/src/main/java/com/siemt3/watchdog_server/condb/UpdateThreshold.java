package com.siemt3.watchdog_server.condb;

import com.espertech.esper.common.internal.util.DeploymentIdNamePair;
import com.siemt3.watchdog_server.GlobalVariables;
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
        ArrayList<Threshold> arrayList = DataBase.fetch();
        arrayList.forEach((threshold) -> {
            String name = threshold.name;
            DeploymentIdNamePair deploymentIdNamePairCount = new DeploymentIdNamePair(PEM.getInstance().globalDeployment.getDeploymentId(), name );
            Map<DeploymentIdNamePair, Object> map = new HashMap<DeploymentIdNamePair,Object>();
            int count = threshold.number;
            map.put(deploymentIdNamePairCount, count);
            System.out.println(count + name);
            PEM.getInstance().runtime.getVariableService().setVariableValue(map);
        });
        Thread.sleep(GlobalVariables.DBUPDATECYCLE);
        loop();
    }
}
