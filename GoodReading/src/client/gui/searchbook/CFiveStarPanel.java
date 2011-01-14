package client.gui.searchbook;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This panel show 5 golden stars, empty or full.
 */
public class CFiveStarPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Array of BufferedImage which will save the relevant star images.
	 */
	private BufferedImage[] stars;

	/**
	 * CFiveStarPanel - Constructor, load the relevant images.
	 * @param numOfFullStars number of full stars to be presented.
	 */
	CFiveStarPanel(int numOfFullStars)
	{
		super();
		this.setLayout(null);
		if(numOfFullStars >= 0 && numOfFullStars <= 5)
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
	
	/**
	 * paintComponent is an overloaded which in this
	 * implementation responsible on painting the stars.
	 */
	public void paintComponent(Graphics g) {
		int i=0;
		if(stars != null)
		{
			for(BufferedImage img : stars)
			{
				g.drawImage(img, i*40, 0, 30 , 30 , null);
				i++;
			}
		}
	}

}