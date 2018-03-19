package g3d_graficadora;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Panel;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PnlOpciones extends Panel {
    private JSlider sldX = new JSlider(SwingConstants.HORIZONTAL, -180, 180, 0);
    private JSlider sldY = new JSlider(SwingConstants.HORIZONTAL, -180, 180, 0);
    private JSlider sldZ = new JSlider(SwingConstants.HORIZONTAL, -180, 180, 0);
    private JSlider sldTransparencia = new JSlider(SwingConstants.HORIZONTAL, 2, 200, 100);
    public JSlider sldEscala = new JSlider(SwingConstants.HORIZONTAL, 1, 30, 10);
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    Graficadora3D Ap;

    static ButtonGroup GrupoRB = new ButtonGroup();
    private JButton bColor = new JButton();
    private JPanel pColor = new JPanel();
    private JButton bGraficar = new JButton();
    private JTextField tfEcuacion = new JTextField();

    public PnlOpciones(Graficadora3D ap) {
        Ap = ap;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setSize(new Dimension(224, 421));
        sldX.setBounds(new Rectangle(15, 140, 200, 20));
        sldX.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldX_ancestorMoved(e);
            }
        });
        sldY.setBounds(new Rectangle(15, 190, 200, 16));
        sldY.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldY_ancestorMoved(e);
            }
        });
        sldZ.setBounds(new Rectangle(15, 240, 200, 16));
        sldZ.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldZ_ancestorMoved(e);
            }
        });
        sldTransparencia.setBounds(new Rectangle(15, 340, 200, 16));
        sldTransparencia.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldAlpha_ancestorMoved(e);
            }
        });
        sldEscala.setBounds(new Rectangle(15, 290, 200, 16));
        sldEscala.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldEscala_ancestorMoved(e);
            }
            });
        jLabel1.setText("Escala");
        jLabel1.setBounds(new Rectangle(15, 260, 115, 25));
        jLabel2.setText("Grosor");
        jLabel2.setBounds(new Rectangle(15, 310, 105, 25));
        jLabel3.setText("Rotacion en Z");
        jLabel3.setBounds(new Rectangle(15, 210, 90, 25));
        jLabel4.setText("Rotacion en Y");
        jLabel4.setBounds(new Rectangle(15, 160, 90, 25));
        jLabel5.setText("Rotacion en X");
        jLabel5.setBounds(new Rectangle(15, 110, 90, 25));
        bColor.setText("Color");
        bColor.setBounds(new Rectangle(15, 75, 90, 25));
        bColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    bColor_actionPerformed(e);
                }
            });
        pColor.setBounds(new Rectangle(140, 75, 55, 25));
        pColor.setBackground(Color.CYAN);
        bGraficar.setText("Graficar");
        bGraficar.setBounds(new Rectangle(105, 45, 90, 25));
        bGraficar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    bGraficar_actionPerformed(e);
                }
            });
        tfEcuacion.setBounds(new Rectangle(15, 20, 180, 25));
        tfEcuacion.setText("x*x+z*z");
        this.add(tfEcuacion, null);
        this.add(bGraficar, null);
        this.add(pColor, null);
        this.add(bColor, null);
        this.add(jLabel5, null);
        this.add(jLabel4, null);
        this.add(jLabel3, null);
        this.add(jLabel2, null);
        this.add(jLabel1, null);
        this.add(sldZ, null);
        this.add(sldY, null);
        this.add(sldEscala, null);
        this.add(sldTransparencia, null);
        this.add(sldX, null);
    }

    private void sldX_ancestorMoved(ChangeEvent e) {
        Ap.setValores(sldX.getValue(), sldY.getValue(), sldZ.getValue());
    }

    private void sldY_ancestorMoved(ChangeEvent e) {
        Ap.setValores(sldX.getValue(), sldY.getValue(), sldZ.getValue());
    }

    private void sldZ_ancestorMoved(ChangeEvent e) {
        Ap.setValores(sldX.getValue(), sldY.getValue(), sldZ.getValue());
    }

    private void sldEscala_ancestorMoved(ChangeEvent e) {
        Ap.setEscala((double)sldEscala.getValue() / 100);
    }

    private void sldAlpha_ancestorMoved(ChangeEvent e) {
        Ap.setTransparencia(sldTransparencia.getValue());
    }

    private void bColor_actionPerformed(ActionEvent e) {
        Color c=JColorChooser.showDialog(this, "Color", null);
        pColor.setBackground(c);
        Ap.setColor(c);
    }

    private void bGraficar_actionPerformed(ActionEvent e) {
        String ec=tfEcuacion.getText();
        Ap.setEcuacion(ec);
    }
}
