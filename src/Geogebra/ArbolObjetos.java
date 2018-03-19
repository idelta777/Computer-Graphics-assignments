package geogebra;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ArbolObjetos extends JPanel {
	public ArbolObjetos() {
		DefaultMutableTreeNode objetosLibres = new DefaultMutableTreeNode("Objetos Libres");
		DefaultTreeModel modelo = new DefaultTreeModel(objetosLibres);
		
		DefaultMutableTreeNode objetosDependientes = new DefaultMutableTreeNode("Objetos Dependientes");
		DefaultTreeModel modelo2 = new DefaultTreeModel(objetosDependientes);
		
		JTree arbol = new JTree(modelo);
		JTree arbol2 = new JTree(modelo2);
		
		this.setLayout(new BorderLayout());
		
		this.add(arbol, BorderLayout.NORTH);
		this.add(arbol2, BorderLayout.CENTER);
	}
}
