
/**
* SetMyImage.java
* @author Sajjad HTLO
*
* This is a utility class for displaying an image on a JPanel.
*
*/

package Project;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class SetMyImage extends JPanel {

    private final Image img;

    public SetMyImage(Image img) {
        this.img = img;
        this.setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,00,00,null);
    }
}
