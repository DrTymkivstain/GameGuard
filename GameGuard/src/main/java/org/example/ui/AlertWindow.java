package org.example.ui;

import javax.swing.*;

public class AlertWindow {
    private static boolean isOpen = false;

    public static void show(String message) {
        if (isOpen) return;

        SwingUtilities.invokeLater(() -> {
            isOpen = true;
            // Створюємо невидимий Frame, щоб вивести діалог поверх усіх вікон
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);

            JOptionPane.showMessageDialog(frame, message, "Система контролю", JOptionPane.WARNING_MESSAGE);

            frame.dispose(); // Прибираємо фрейм з пам'яті після закриття
            isOpen = false;
        });
    }
}
