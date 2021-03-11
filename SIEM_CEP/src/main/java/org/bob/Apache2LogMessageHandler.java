package org.bob;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.espertech.esper.runtime.client.EPEventService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nicolas Roth, Robert Uwe Litschel
 *
 */
public class Apache2LogMessageHandler {

    private EPEventService eventService;

    public Apache2LogMessageHandler(EPEventService eventService) throws IOException, InterruptedException {
        //System.out.println("Apache2LogMessageHandler\n");
        this.eventService = eventService;

        //Start FileWatcher
        File file = new File("/var/log/apache2/access.log");
        FileWatcher fileWatcher = new FileWatcher(file);

        while (true){
            this.readLog(fileWatcher);
        }
    }

    private void readLog(FileWatcher fileWatcher) {

        try {
            String log =  fileWatcher.readLastLine();
            //System.out.println("fileWatcher: " + log);
            InputStream inputStream = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8));
            ObjectMapper mapper = new ObjectMapper();
            JsonParser jsonParser = mapper.getFactory().createParser(inputStream);

            while (jsonParser.nextToken() != null) {

                @SuppressWarnings("unchecked")
                Map<String, String> apache2LogLine = mapper.readValue(jsonParser, Map.class);

                if (apache2LogLine.get("status") != null){
                    //this.apache2LogEvent(apache2LogLine.get("status"));
                    this.apache2LogEvent(log);
                }
            }
        } catch (Exception e) {
            //todo
        }
    }

    private void apache2LogEvent(String message) {
        //System.out.println("apache2LogEvent\n");
        this.eventService.sendEventBean(new Apache2LogMessage(message), "Apache2LogMessage");
    }


}
