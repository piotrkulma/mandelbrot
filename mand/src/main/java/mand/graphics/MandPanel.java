package mand.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Piotr Kulma on 28.08.14.
 */
public class MandPanel extends JPanel implements MouseMotionListener, MouseListener {
    public static final int MAX_STEPS           = 1000;
    public static final int MAX_ITERATIONS      = 255;

    private boolean mousePressed;

    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    private int mouseBeginX, mouseBeginY;
    private int mouseEndX, mouseEndY;

    private Image image, image2;
    private Graphics buff, buff2;

    public MandPanel() {
        this.mousePressed = false;
        initMandParams();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        initBuffersIfNotInitialized();

        if(!mousePressed) {
            computeAndDrawInBuffer(buff, minX, maxX, minY, maxY);
            g.drawImage(image, 0, 0, this);
        } else {
            buff2.drawImage(image, 0, 0, this);
            drawRectangle(buff2, mouseBeginX, mouseBeginY, mouseEndX, mouseEndY);
            g.drawImage(image2, 0, 0, this);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(mousePressed) {
            mouseEndX = e.getX();
            mouseEndY = e.getY();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseBeginX = e.getX();
        mouseBeginY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        rescaleMandArea();
        resetRectanglePosition();
        repaint();
    }

    public void rescaleMandArea() {

    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void initBuffersIfNotInitialized() {
        if(image == null && buff == null) {
            image = createImage(this.getWidth(), this.getHeight());
            buff = image.getGraphics();

            image2 = createImage(this.getWidth(), this.getHeight());
            buff2 = image2.getGraphics();
        }
    }

    private void resetRectanglePosition() {
        mouseBeginX = 0;
        mouseBeginY = 0;
        mouseEndX = 0;
        mouseEndY = 0;
    }

    private void initMandParams() {
        minX = -2.0f;
        maxX = 1.0f;
        minY = -1.5f;
        maxY = 1.5f;
    }

    private void drawRectangle(Graphics g, int bx, int by, int ex, int ey) {
        g.setColor(Color.YELLOW);
        g.drawLine(bx, by, ex, by);
        g.drawLine(ex, by, ex, ey);
        g.drawLine(ex, ey, bx, ey);
        g.drawLine(bx, ey, bx, by);
    }

    private void computeAndDrawInBuffer(Graphics g, float minX, float maxX, float minY, float maxY) {
        int iter;

        float width = this.getWidth();
        float height = this.getHeight();

        float sx = width / MAX_STEPS;
        float sy = height / MAX_STEPS;

        float stepX = (maxX - minX) / MAX_STEPS;
        float stepY = (maxY - minY) / MAX_STEPS;

        float cx = minX;
        float zx, zy, tzx, cy;

        for(float i=0; i<width; i+=sx) {
            cy = minY;
            for(float j=0; j<height; j+=sy) {
                zx = 0;
                zy = 0;
                iter = 0;
                while((zx * zx - zy * zy) < 4 &&  iter < MAX_ITERATIONS) {
                    tzx = zx;
                    zx = zx * zx - zy * zy + cx;
                    zy = 2 * tzx * zy + cy;
                    iter ++;
                }

                g.setColor(new Color(0, iter, iter / MAX_ITERATIONS));
                g.drawLine((int)i, (int)j, (int)i + 1, (int)j + 1);

                cy += stepY;
            }
            cx += stepX;
        }
    }
}
