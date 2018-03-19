package G3D_Dragon.src.g3d_dragon;

import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(laf.getName()))
                try {
                    UIManager.setLookAndFeel(laf.getClassName());
                } catch (Exception ex) {
                }
        }
        FrmPrincipal principal=new FrmPrincipal();
        principal.setVisible(true);
    }
}
