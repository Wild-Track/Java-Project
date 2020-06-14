package Graphique.Controls;

import javax.swing.JPanel;
import java.awt.LayoutManager;

public abstract class Page extends JPanel {

    private static final long serialVersionUID = 4245759204888535730L;

    public Page() {
        super();
    }

    public Page(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public void onRefresh() {

    }

    public abstract String getTitre();

}