package org.example.service;

import javax.sound.sampled.*;
import java.util.function.Consumer;

public class AudioMonitor implements Runnable {
    private final Consumer<Integer> onViolation; // Коллбек для штрафу

    public AudioMonitor(Consumer<Integer> onViolation) {
        this.onViolation = onViolation;
    }

    @Override
    public void run() {
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        try (TargetDataLine line = (TargetDataLine) AudioSystem.getLine(new DataLine.Info(TargetDataLine.class, format))) {
            line.open(format);
            line.start();
            byte[] buffer = new byte[2048];
            while (true) {
                line.read(buffer, 0, buffer.length);
                if (calculateRMS(buffer) > 800) {
                    onViolation.accept(5); // Штраф 5 хв
                    Thread.sleep(5000);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private double calculateRMS(byte[] buffer) {
        long sum = 0;
        for (int i = 0; i < buffer.length; i += 2) {
            short sample = (short) ((buffer[i + 1] << 8) | (buffer[i] & 0xff));
            sum += sample * sample;
        }
        return Math.sqrt(sum / (buffer.length / 2.0));
    }
}