package G3D_Dragon.src.g3d_dragon;

import java.applet.Applet;

import java.awt.Dimension;

import java.awt.Panel;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PnlOpciones extends Panel {
    private JSlider sldX = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
    private JSlider sldY = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
    private JSlider sldZ = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
    private JSlider sldEscala = new JSlider(SwingConstants.HORIZONTAL, 1, 30, 15);
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    Dragon Ap;
    public JRadioButton rdbCuerpo = new JRadioButton("Cuerpo", true);
    public JRadioButton rdbCuello = new JRadioButton("Cuello");
    private JRadioButton rdbCabeza = new JRadioButton("Cabeza");
    private JRadioButton rdbColaS1 = new JRadioButton("Cola seccion 1");
    private JRadioButton rdbColaS2 = new JRadioButton("Cola seccion 2");
    private JRadioButton rdbColaS3 = new JRadioButton("Cola seccion 3");
    private JRadioButton rdbPataDelanteraI = new JRadioButton("Pata delantera izquierda");
    private JRadioButton rdbPataDelanteraD = new JRadioButton("Pata delantera derecha");
    private JRadioButton rdbRodillaDelanteraI = new JRadioButton("Rodilla delantera izquierda");
    private JRadioButton rdbRodillaDelanteraD = new JRadioButton("Rodilla delantera derecha");
    private JRadioButton rdbPataTraseraI = new JRadioButton("Pata trasera izquierda");
    private JRadioButton rdbRodillaTraseraI = new JRadioButton("Rodilla trasera izquierda");
    private JRadioButton rdbPataTraseraD = new JRadioButton("Pata trasera derecha");
    private JRadioButton rdbRodillaTraseraD = new JRadioButton("Rodilla trasera derecha");
    private JRadioButton rdbAlas = new JRadioButton("Mover Alas");

    static ButtonGroup GrupoRB = new ButtonGroup();

    public PnlOpciones(Dragon ap) {
        Ap = ap;
        initGrupoRB();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        this.setLayout(null);
        this.setSize(new Dimension(224, 628));
        sldX.setBounds(new Rectangle(10, 425, 200, 20));
        sldX.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldX_ancestorMoved(e);
            }
        });
        sldY.setBounds(new Rectangle(10, 475, 200, 16));
        sldY.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldY_ancestorMoved(e);
            }
        });
        sldZ.setBounds(new Rectangle(10, 525, 200, 16));
        sldZ.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldZ_ancestorMoved(e);
            }
        });
        sldEscala.setBounds(new Rectangle(10, 575, 200, 16));
        sldEscala.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sldEscala_ancestorMoved(e);
            }
        });
        jLabel1.setText("Escala");
        jLabel1.setBounds(new Rectangle(10, 545, 115, 25));
        jLabel3.setText("Rotacion en Z");
        jLabel3.setBounds(new Rectangle(10, 495, 90, 25));
        jLabel4.setText("Rotacion en Y");
        jLabel4.setBounds(new Rectangle(10, 445, 90, 25));
        jLabel5.setText("Rotacion en X");
        jLabel5.setBounds(new Rectangle(10, 395, 90, 25));
        rdbCuello.setBounds(new Rectangle(25, 40, 110, 20));
        rdbCabeza.setBounds(new Rectangle(25, 65, 110, 20));
        rdbColaS1.setBounds(new Rectangle(25, 90, 110, 20));
        rdbCuello.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbCabeza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbColaS1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbColaS2.setBounds(new Rectangle(25, 115, 110, 20));
        rdbColaS2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbColaS3.setBounds(new Rectangle(25, 140, 110, 20));
        rdbColaS3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbPataDelanteraI.setBounds(new Rectangle(25, 165, 165, 20));
        rdbPataDelanteraI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbPataDelanteraD.setBounds(new Rectangle(25, 215, 180, 20));
        rdbPataDelanteraD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbRodillaDelanteraI.setBounds(new Rectangle(25, 190, 180, 20));
        rdbRodillaDelanteraI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbRodillaDelanteraD.setBounds(new Rectangle(25, 240, 165, 20));
        rdbRodillaDelanteraD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbPataTraseraI.setBounds(new Rectangle(25, 265, 165, 20));
        rdbPataTraseraI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbRodillaTraseraI.setBounds(new Rectangle(25, 290, 180, 20));
        rdbRodillaTraseraI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbPataTraseraD.setBounds(new Rectangle(25, 315, 180, 20));
        rdbPataTraseraD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbRodillaTraseraD.setBounds(new Rectangle(25, 340, 165, 20));
        rdbRodillaTraseraD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbAlas.setBounds(new Rectangle(25, 365, 165, 20));
        rdbAlas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        rdbCuerpo.setBounds(new Rectangle(25, 15, 110, 20));
        rdbCuerpo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdbSelected_actionPerformed(e);
            }
        });
        this.add(rdbAlas, null);
        this.add(rdbRodillaTraseraD, null);
        this.add(rdbPataTraseraD, null);
        this.add(rdbRodillaTraseraI, null);
        this.add(rdbPataTraseraI, null);
        this.add(rdbRodillaDelanteraD, null);
        this.add(rdbRodillaDelanteraI, null);
        this.add(rdbPataDelanteraD, null);
        this.add(rdbPataDelanteraI, null);
        this.add(rdbCuerpo, null);
        this.add(rdbColaS3, null);
        this.add(rdbColaS2, null);
        this.add(rdbColaS1, null);
        this.add(rdbCabeza, null);
        this.add(rdbCuello, null);
        this.add(jLabel5, null);
        this.add(jLabel4, null);
        this.add(jLabel3, null);
        this.add(jLabel1, null);
        this.add(sldZ, null);
        this.add(sldY, null);
        this.add(sldEscala, null);
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

    private void sldAlpha_ancestorMoved(ChangeEvent e) {
        //Ap.setAlpha(sldAlpha.getValue() * 100);
    }


    private void sldEscala_ancestorMoved(ChangeEvent e) {
        Ap.setEscala((double)sldEscala.getValue() / 100);
    }

    private void initGrupoRB() {
        GrupoRB.add(rdbCuerpo);                
        GrupoRB.add(rdbCuello);
        GrupoRB.add(rdbCabeza);
        GrupoRB.add(rdbColaS1);
        GrupoRB.add(rdbColaS2);
        GrupoRB.add(rdbColaS3);
        GrupoRB.add(rdbPataDelanteraI);
        GrupoRB.add(rdbRodillaDelanteraI);
        GrupoRB.add(rdbPataDelanteraD);
        GrupoRB.add(rdbRodillaDelanteraD);
        GrupoRB.add(rdbPataTraseraI);
        GrupoRB.add(rdbRodillaTraseraI);
        GrupoRB.add(rdbPataTraseraD);
        GrupoRB.add(rdbRodillaTraseraD);
        GrupoRB.add(rdbAlas);
    }

    void setValoresSld(int[][] V) {
        sldX.setMinimum(V[0][0]);
        sldX.setMaximum(V[0][1]);
        sldX.setValue(V[0][2]);
        sldY.setMinimum(V[1][0]);
        sldY.setMaximum(V[1][1]);
        sldY.setValue(V[1][2]);
        sldZ.setMinimum(V[2][0]);
        sldZ.setMaximum(V[2][1]);
        sldZ.setValue(V[2][2]);
    }

    private void rdbSelected_actionPerformed(ActionEvent e) {
        if (rdbCuerpo.isSelected()) {
            Ap.setExtremidad(Ap.CUERPO);
        } else if (rdbCuello.isSelected()) {
            Ap.setExtremidad(Ap.CUELLO);
        } else if (rdbCabeza.isSelected()) {
            Ap.setExtremidad(Ap.CABEZA);
        } else if (rdbColaS1.isSelected()) {
            Ap.setExtremidad(Ap.COLAS1);
        } else if (rdbColaS2.isSelected()) {
            Ap.setExtremidad(Ap.COLAS2);
        } else if (rdbColaS3.isSelected()) {
            Ap.setExtremidad(Ap.COLAS3);
        } else if (rdbPataDelanteraI.isSelected()) {
            Ap.setExtremidad(Ap.PATADIZQ);
        } else if (rdbRodillaDelanteraI.isSelected()) {
            Ap.setExtremidad(Ap.RODILLADIZQ);
        } else if (rdbPataDelanteraD.isSelected()) {
            Ap.setExtremidad(Ap.PATADDER);
        } else if (rdbRodillaDelanteraD.isSelected()) {
            Ap.setExtremidad(Ap.RODILLADDER);
        } else if (rdbPataTraseraI.isSelected()) {
            Ap.setExtremidad(Ap.PATATIZQ);
        } else if (rdbRodillaTraseraI.isSelected()) {
            Ap.setExtremidad(Ap.RODILLATIZQ);
        } else if (rdbPataTraseraD.isSelected()) {
            Ap.setExtremidad(Ap.PATATDER);
        } else if (rdbRodillaTraseraD.isSelected()) {
            Ap.setExtremidad(Ap.RODILLATDER);
        } else if (rdbAlas.isSelected()) {
            Ap.setExtremidad(Ap.ALAS);
        }
    }
}
