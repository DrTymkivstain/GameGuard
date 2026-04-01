package org.example.service;


import org.example.ui.AlertWindow;

import java.time.LocalDate;
import java.util.Properties;

public class GuardService {
    private static final String PROCESS_NAME = "RobloxPlayerBeta";
    private static final int MAX_MINUTES = 60;

    private final StatsService statsService = new StatsService();
    private int currentMinutes;
    private long lastTick = System.currentTimeMillis();

    public void update() {
        Properties props = statsService.load();
        String today = LocalDate.now().toString();

        // Скидання дня
        if (!today.equals(props.getProperty("lastDate", ""))) {
            currentMinutes = 0;
        } else {
            currentMinutes = Integer.parseInt(props.getProperty("minutesUsed", "0"));
        }

        boolean isRunning = ProcessHandle.allProcesses()
                .anyMatch(p -> p.info().command().orElse("").contains(PROCESS_NAME));

        if (isRunning) {
            if (currentMinutes >= MAX_MINUTES) {
                killRoblox();
                AlertWindow.show("Час вичерпано! Йди вчи Java.");
                return;
            }

            if (System.currentTimeMillis() - lastTick >= 60_000) {
                applyPenalty(1);
                lastTick = System.currentTimeMillis();
            }
        }
    }

    public synchronized void applyPenalty(int mins) {
        currentMinutes += mins;
        statsService.save(currentMinutes);

        // Якщо штраф більше 1 хвилини (тобто це крик, а не просто тик таймера)
        if (mins > 1) {
            AlertWindow.show("Не кричи! За погану поведінку мінус " + mins + " хвилин ігор.");
        }
    }

    private void killRoblox() {
        ProcessHandle.allProcesses()
                .filter(p -> p.info().command().orElse("").contains(PROCESS_NAME))
                .forEach(ProcessHandle::destroyForcibly);
    }
}