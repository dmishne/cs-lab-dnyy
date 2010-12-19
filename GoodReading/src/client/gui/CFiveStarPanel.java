/**
 * 
 */
package client.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Daniel
 *
 */
public class CFiveStarPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage[] stars;
	
	CFiveStarPanel(int numOfFullStars)
	{
		super();
		this.setLayout(null);
		if(numOfFullStars >= 1 && numOfFullStars <= 5)
		{
			stars = new BufferedImage[5];
			try{
				int i=0;
				for(i=1;i <= numOfFullStars;++i)
				{
					stars[i-1] = ImageIO.read(new File("gold_star_full.png"));
				}
				for(;i <= 5; ++i)
				{
					stars[i-1] = ImageIO.read(new File("gold_star_empty.png"));
				}
			}
			catch (IOException ex){
				JOptionPane.showMessageDialog(null, ex.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		int i=0;
		for(BufferedImage img : stars)
		{
			g.drawImage(img, i*40, 0, 30 , 30 , null);
			i++;
		}
	}

}

