package client.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

/** Label with custom painting of alpha composite shadow. */
public class CustomLabel extends JLabel{
	
	
	private static final long serialVersionUID = 1L;
	
	public CustomLabel()
	{
       setForeground( TEXT_COLOR );
    }

    @Override
    protected void paintComponent( final Graphics in_Graphics ){

      final Graphics2D graphics = ( Graphics2D ) in_Graphics;

      // Remember current graphics parameters
      final Object oldTextAntialiasingHint = graphics.getRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING );
       final Color oldForeground = getForeground();

      // Set rendering quality
      graphics.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
      {// Paint the shadow
        final Graphics2D shadowGraphics = ( Graphics2D ) graphics.create();

        shadowGraphics.translate( SHADOW_OFFSET_X, SHADOW_OFFSET_Y );
        shadowGraphics.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER,0.7f ) );
        setForeground( SHADOW_COLOR );

        super.paintComponent( shadowGraphics );

        shadowGraphics.dispose();
      }

      // Paint the text
      setForeground( oldForeground );
      super.paintComponent( graphics );


      // Restore rendering quality
      if( null != oldTextAntialiasingHint )
      {
        graphics.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING,oldTextAntialiasingHint );	      
      }
      else
      {
        // do nothing
      }
    }
    

    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color SHADOW_COLOR = Color.LIGHT_GRAY;
    private static final int SHADOW_OFFSET_X = 2;
    private static final int SHADOW_OFFSET_Y = 3;

  } /** End of Label with shadow. */



