package g3d_dragon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FrmPrincipal extends JFrame {
    int n;
    Dragon applet = new Dragon();

    public FrmPrincipal() {
        this.setSize(new Dimension(800, 700));
        this.setLocationRelativeTo(null);
        this.setTitle("Dragón-Crhistian Adair del Río García");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        applet.setSize(400, 400);
        applet.init();
        this.getContentPane().add(applet);
    }
}