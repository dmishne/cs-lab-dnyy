package client.gui.generatereport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import client.core.AUser;
import client.core.CLibraryManager;
import client.gui.searchbook.CBookDetailPanel;

public class CBookReport extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back = null;

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public CBookReport() throws Exception {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(getM_jButton_back(), null);
		this.add(getChart(),null);
	}

	/**
	 * This method initializes m_jButton_back	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_back() {
		if (m_jButton_back == null) {
			m_jButton_back = new JButton();
			m_jButton_back.setLocation(new Point(258, 480));
			m_jButton_back.setText("Back");
			m_jButton_back.setSize(new Dimension(150, 34));
			m_jButton_back.addActionListener(this);
		}
		return m_jButton_back;
	}
	
	private ChartPanel getChart() throws Exception
	{
		DefaultCategoryDataset DCD = new DefaultCategoryDataset();
		HashMap<String,Integer> bookDataViews = ((CLibraryManager)AUser.getInstance()).getBookViews("2011");
		Iterator<String> itv = bookDataViews.keySet().iterator();
		while(itv.hasNext())
		{
			String month = itv.next();
			DCD.addValue(bookDataViews.get(month), "Searches", month);
		}
		HashMap<String,Integer> bookDataSales = ((CLibraryManager)AUser.getInstance()).getBookSales("2011");
		Iterator<String> its = bookDataSales.keySet().iterator();
		while(its.hasNext())
		{
			String month = its.next();
			DCD.addValue(bookDataSales.get(month), "Orders", month);
		}		
		JFreeChart bookChart = ChartFactory.createBarChart3D(CBookDetailPanel.getBook().getM_title(), "Months", "Total", DCD, PlotOrientation.VERTICAL, true, false, false);
		bookChart.setBackgroundPaint(new Color(238,238,238));
		ChartPanel bcp = new ChartPanel(bookChart);
		bcp.setLocation(5, 5);
		bcp.setSize(685,450);
		bcp.setBackground(new Color(238,238,238));
		bcp.setVisible(true);
		return bcp;
	}

	public void actionPerformed(ActionEvent ae) {
		this.setVisible(false);
	}

}
