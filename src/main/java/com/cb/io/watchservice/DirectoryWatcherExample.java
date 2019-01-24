package com.cb.io.watchservice;

import java.io.IOException;
import java.nio.file.*;

/**
 * 通过WatchService 监控目录下文件的变化
 */
public class DirectoryWatcherExample {
    public static void main(String[] args) throws InterruptedException, IOException {
        WatchService watchService
          = FileSystems.getDefault().newWatchService();
 
        Path path = Paths.get(System.getProperty("user.home"));
 
        path.register(
          watchService, 
            StandardWatchEventKinds.ENTRY_CREATE,
              StandardWatchEventKinds.ENTRY_DELETE, 
                StandardWatchEventKinds.ENTRY_MODIFY);
 
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(
                  "Event kind:" + event.kind() 
                    + ". File affected: " + event.context() + ".");
            }
            key.reset();
        }
    }
}