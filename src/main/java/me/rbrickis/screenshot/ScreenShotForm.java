package me.rbrickis.screenshot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ScreenShotForm extends JPanel {

    private Robot robot;

    private Point initial, drag;


    // This is the image we will extract our selection from
    private BufferedImage bg;

    private SelectionPane pane;

    public ScreenShotForm(JFrame parent) {
        this.pane = new SelectionPane();
        try {
            this.robot = new Robot();
            bg = robot.createScreenCapture(getScreenViewableBounds());
        } catch (AWTException e) {
            e.printStackTrace();
        }

        setLayout(null);
        add(pane);

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Create the initial point
                initial = e.getPoint();
                drag = null;
                pane.setLocation(initial);
                pane.setSize(0, 0);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                drag = e.getPoint();
                int width = (drag.x - initial.x), height = drag.y - initial.y;
                int x = initial.x, y = initial.y;
                if (width < 0) {
                    x = drag.x;
                    width = -width;
                }
                if (height < 0) {
                    y = drag.y;
                    height = -height;
                }
                pane.setBounds(x, y, width, height);
                pane.revalidate();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Rectangle rectangle = pane.getBounds();
                BufferedImage image =
                    bg.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                parent.dispose();
                EventQueue.invokeLater(() -> new ImageFrame(image));
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g.create();
        gg.drawImage(bg, 0, 0, this);
        gg.dispose();
    }

    private class SelectionPane extends JPanel {
        public SelectionPane() {
            setOpaque(false);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;

            gbc.gridy++;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            Color magenta = new Color(255, 0, 255, 25); // magenta :D
            g2d.setColor(magenta);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.drawRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }

    public Rectangle getScreenViewableBounds() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return getScreenViewableBounds(gd);
    }

    public Rectangle getScreenViewableBounds(GraphicsDevice gd) {
        Rectangle bounds = new Rectangle(0, 0, 0, 0);

        if (gd != null) {
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            bounds = gc.getBounds();

            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

            bounds.x = insets.left;
            bounds.y = insets.top;
            bounds.width += (insets.left + insets.right);
            bounds.height += (insets.top + insets.bottom);
        }
        return bounds;
    }

}
