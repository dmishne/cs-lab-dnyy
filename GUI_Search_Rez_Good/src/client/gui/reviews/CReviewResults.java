package client.gui.reviews;

import java.util.LinkedList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import common.data.CBookReview;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;

public class CReviewResults //extends JList // JScrollPane
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane m_holder;
	private JList m_list;
	
	public CReviewResults(LinkedList<CBookReview> arg)
	{
		Object[] dataList=arg.toArray();
		
		m_list=new JList(dataList);
		m_list.setCellRenderer(new ImageListCellRenderer());
		m_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);	
		m_list.setSize(new Dimension(700, 590));
		
		m_holder = new JScrollPane(m_list);
		m_holder.setPreferredSize(new Dimension(700,590));
		m_holder.setSize(new Dimension(700, 590));
		m_holder.setLocation(new Point(5, 5));
		m_holder.getViewport().setView(m_list);
	
	} 	

	public JScrollPane getPanel()
	{
		return m_holder;
	}

}
