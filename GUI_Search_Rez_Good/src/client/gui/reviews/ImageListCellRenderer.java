package client.gui.reviews;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import common.data.CBookReview;

class ImageListCellRenderer implements ListCellRenderer
{
	//this class is made only to implement the new renderer for our JList

  public Component getListCellRendererComponent(JList jlist, 
                                                Object value, 
                                                int cellIndex, 
                                                boolean isSelected, 
                                                boolean cellHasFocus)
  {
	  Component component = (Component)new CReviewPanel((CBookReview) value,cellIndex);
	  component.setBackground (isSelected ? Color.BLUE : Color.LIGHT_GRAY);
	   
	  return component;
   
  }
}