import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by Piotr Kulma on 28.08.14.
 */
public class MandPanel extends JPanel implements MouseMotionListener {
    public final static int maxIter = 255;

    public final static float minX = -2.0f;
    public final static float maxX = 1f;

    public final static float minY = -1.5f;
    public final static float maxY = 1.5f;

    public MandPanel() {
        this.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Image image = createImage(this.getWidth(), this.getHeight());
        Graphics buff = image.getGraphics();

        computeAndDrawInBuffer(buff);

        g.drawImage(image, 0, 0, this);
    }

    private void computeAndDrawInBuffer(Graphics buff) {
        int iter;

        float width = this.getWidth();
        float height = this.getHeight();

        float sx = width / 1000;
        float sy = height / 1000;

        float stepX = (maxX - minX) / 1000;
        float stepY = (maxY - minY) / 1000;

        float cx = minX;
        float zx, zy, tzx, cy;

        for(float i=0; i<width; i+=sx) {
            cy = minY;
            for(float j=0; j<height; j+=sy) {
                zx = 0;
                zy = 0;
                iter = 0;
                while((zx * zx - zy * zy) < 4 &&  iter < maxIter) {
                    tzx = zx;
                    zx = zx * zx - zy * zy + cx;
                    zy = 2 * tzx * zy + cy;
                    iter ++;
                }

                buff.setColor(new Color(0, iter, iter/255));
                buff.drawLine((int)i, (int)j, (int)i + 1, (int)j + 1);

                cy += stepY;
            }
            cx += stepX;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println();
    }
}
