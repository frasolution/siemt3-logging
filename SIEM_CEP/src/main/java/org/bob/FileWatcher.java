package org.bob;

import org.apache.commons.io.input.ReversedLinesFileReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * @author Robert Uwe Litschel
 *
*/

public class FileWatcher {
    private File file;

    FileWatcher(File file) throws IOException, InterruptedException {
        this.file = file;
    }

    //todo; get last line for specified file only
    public String readLastLine() throws InterruptedException, IOException {
        //System.out.println("readLastLine\n");

        // set up WatchService for folder
        // define which events to watch
        WatchService watchService = FileSystems.getDefault().newWatchService();
        if(file.exists()){
            Path path = Paths.get(file.getParent());
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        }

        WatchKey key;
        ReversedLinesFileReader object = new ReversedLinesFileReader(file, StandardCharsets.UTF_8);

        //System.out.println("wait FileWatcher\n");
        while ((key = watchService.take()) != null) {
            key.reset();
            return object.readLine();
        }
        //object.close();
        return null;
    }


    public void printLastLine() throws IOException {
        System.out.println("printLastLine\n");
        ReversedLinesFileReader object = new ReversedLinesFileReader(file, StandardCharsets.UTF_8);
        try {
            System.out.println("Line - " + object.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                object.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void forReference() throws IOException, InterruptedException {
        // set up WatchService for folder
        // define which events to watch
        WatchService watchService = FileSystems.getDefault().newWatchService();
        if (file.exists()) {
            Path path = Paths.get(file.getParent());
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        }

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind() + ", File affected: " + event.context());
                //this.printLastLine();
            }
            key.reset();
        }
    }

}




