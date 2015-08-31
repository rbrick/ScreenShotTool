package me.rbrickis.screenshot;

import me.rbrickis.screenshot.backend.Result;
import me.rbrickis.screenshot.backend.backends.imgur.ImgurBackend;
import me.rbrickis.screenshot.backend.backends.imgur.ImgurImage;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public class ImageFrame extends JFrame {

    public ImageFrame(BufferedImage image) {
        super("Upload");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.NORMAL);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        JPanel panel = new JPanel();
        panel.add(new JLabel(new ImageIcon(image)));
        JButton upload = new JButton("Upload");
        setResizable(false);
        upload.addActionListener(e -> {
            ImageFrame.this.dispose();
            JFrame frame = new JFrame("Uploaded");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setResizable(false);
            ImgurBackend backend = (ImgurBackend) ScreenShotTool.getBackend();
            ImgurImage img = new ImgurImage();
            img.setClientId("REDACTED");
            img.setTitle(UUID.randomUUID().toString());
            img.setDescription("...");
            img.setImage(image);
            Result result = backend.upload(img);
            String link =
                result.getResponse().get("data").getAsJsonObject().get("link").getAsString();
            JPanel cpPanel = new JPanel();
            JButton copy = new JButton("Copy");
            JButton visit = new JButton("Visit");

            JTextField text = new JTextField(link);

            copy.addActionListener(event -> {
                StringSelection selection = new StringSelection(link);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
                System.exit(0);
            });

            visit.addActionListener(event -> {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                    System.exit(0);
                } catch (IOException | URISyntaxException e1) {
                   /* IGNORED */
                }
            });

            cpPanel.add(copy);
            cpPanel.add(visit);
            cpPanel.add(text);
            frame.add(cpPanel);
            frame.pack();
            frame.setVisible(true);


        });
        upload.setBounds(300, 300, 20, 20);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> System.exit(0));
        panel.add(upload);
        panel.add(cancel);
        this.add(panel);
        pack();
        setVisible(true);
    }
}
