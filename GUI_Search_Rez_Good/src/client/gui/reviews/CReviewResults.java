package client.gui.reviews;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import client.gui.CSearchResultPanel.SRPDecision;

import common.data.CBookReview;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CReviewResults extends JPanel implements ActionListener// JScrollPane
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane m_holder;
	private JList m_list;
	private static CReviewResults m_this;
	private JLabel m_jLabel_title;
	private JButton m_jButton_back;
	private SRPDecision m_lastChoice = SRPDecision.BACK; 
	
	public static JPanel getSearchReviewPanel(LinkedList<CBookReview> arg)
	{
		if(m_this == null )
			m_this = new CReviewResults();
		m_this.initialize(arg.toArray());
		return m_this;
	}
	
	private void initialize(Object[] arr)
	{
		
		//create list
		m_list=new JList(arr);
		m_list.setCellRenderer(new ImageListCellRenderer());
		m_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);	
		m_list.setSize(new Dimension(635, 400));
		m_list.setLocation(new Point(50, 50));
		m_list.setBackground(this.getBackground());
	
		//create scroll pane
		m_holder = new JScrollPane(m_list);
		m_holder.setPreferredSize(new Dimension(700,590));
		m_holder.setSize(new Dimension(700, 590));
		m_holder.setLocation(new Point(5, 5));
		m_holder.getViewport().setView(m_list);
		m_holder.setSize(new Dimension(635, 400));
		m_holder.setLocation(new Point(50, 50));
		
		//clear all
		m_this.removeAll();
		
		//add fancy label
		m_jLabel_title = new JLabel();
		m_jLabel_title.setText("Search Results");
		m_jLabel_title.setLocation(new Point(0, 0));
		m_jLabel_title.setHorizontalTextPosition(SwingConstants.LEADING);
		m_jLabel_title.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_title.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		m_jLabel_title.setSize(new Dimension(700, 35));
		
		//add panes
		m_this.add(m_jLabel_title);
		m_this.add(m_holder);
		m_this.add(getM_jButton_back()); //add back button
		//set size and layout
	//	m_this.setVisible(true);
		this.setSize(700, 550);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(700, 550));
	}
	
	private CReviewResults()
	{
		super();
			
	} 	

	public JScrollPane getPanel()
	{
		return m_holder;
	}
	private JButton getM_jButton_back() {
		if (m_jButton_back == null) {
			m_jButton_back = new JButton();
			m_jButton_back.setPreferredSize(new Dimension(208, 34));
			m_jButton_back.setSize(new Dimension(208, 34));
			m_jButton_back.setLocation(new Point(94, 480));
			m_jButton_back.setText("Back");
			m_jButton_back.addActionListener(this);
		}
		return m_jButton_back;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if(source == m_jButton_back)
		{
			this.m_lastChoice = SRPDecision.BACK;
			this.setVisible(false);
		}
		
	}
	
	//internal class for use of CReviewResults only
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

}
