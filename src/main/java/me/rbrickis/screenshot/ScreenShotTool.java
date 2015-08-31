package me.rbrickis.screenshot;

import me.rbrickis.screenshot.backend.Backend;
import me.rbrickis.screenshot.backend.backends.imgur.ImgurBackend;

import javax.swing.*;
import java.awt.*;

public class ScreenShotTool {

    private static Backend backend;

    /**
     * The main method, that gets ran.
     */
    public static void main(String[] args) {
        backend = new ImgurBackend();
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("ScreenShot Tool");
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(new ScreenShotForm(frame));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static Backend getBackend() {
        return backend;
    }
}
