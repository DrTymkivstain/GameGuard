package org.example;

import org.example.service.AudioMonitor;
import org.example.service.GuardService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppLauncher {
    public static void main(String[] args) {
        GuardService guard = new GuardService();

        // Запуск мікрофона
        new Thread(new AudioMonitor(guard::applyPenalty)).start();

        // Запуск основного циклу (5 сек)
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(guard::update, 0, 5, TimeUnit.SECONDS);
    }
}