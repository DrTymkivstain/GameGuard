package org.example.service;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Properties;

public class StatsService {
    private static final String FILE_NAME = "guard_stats.properties";

    public Properties load() {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(FILE_NAME))) {
            props.load(in);
        } catch (IOException ignored) {}
        return props;
    }

    public void save(int minutes) {
        Properties props = new Properties();
        props.setProperty("lastDate", LocalDate.now().toString());
        props.setProperty("minutesUsed", String.valueOf(minutes));
        try (OutputStream out = Files.newOutputStream(Paths.get(FILE_NAME))) {
            props.store(out, null);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
