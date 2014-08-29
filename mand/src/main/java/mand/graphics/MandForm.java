package mand.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Piotr Kulma on 28.08.14.
 */
public class MandForm extends JFrame{
    private MandPanel panel;

    public MandForm() {
        super("Mandelbrot set");
        initForm();
        initAndAddPanel();
    }

    private void initAndAddPanel() {
        panel = new MandPanel();
        this.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void initForm() {
        this.setSize(800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
