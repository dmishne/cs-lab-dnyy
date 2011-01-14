package client.gui.generatereport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import client.core.AUser;
import client.core.CLibraryManager;
import client.gui.searchbook.CBookDetailPanel;

/**
 * CBookReport define the panel which represents the Books Histogram.
 */
public class CBookReport extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back = null;
	
	/**
	 *  String of names of all the months of the year
	 */
	private final String[] m_months = {"Jan","Feb","Mars","Apr","May","June","July","Aug","Sep","Oct","Nov","Dec"};
	private JComboBox m_jComboBox_Years = null;
	
	/**
	 * Vector of available years in database.
	 */
	private Vector<String> m_years = null;  //  @jve:decl-index=0:
	/**
	 * Save the most recent year. 
	 */
	private String m_lastYear;
	private ChartPanel m_chart = null;
	
	/**
	 * CBookReport() is the default constructor
	 * @throws Exception 
	 */
	public CBookReport() throws Exception {
		super();
		initialize();
	}

	/**
	 * initialize() initializes this class
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		m_years = ((CLibraryManager)AUser.getInstance()).getYears();
		setMaxYear();
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(getM_jButton_back(), null);
		this.add(getM_jComboBox_Years(), null);
		this.add(getChart(),null);
	}

	/**
	 * This method initializes m_jButton_back	
	 * Pressing on this button change the panel to the previous one.	
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
	
	
	/**
	 * Build, year relevant Histogram panel.
	 * @return Histogram panel.
	 * @throws Exception
	 */
	private ChartPanel getChart() throws Exception
	{
		if(m_chart == null)
		{
			DefaultCategoryDataset DCD = new DefaultCategoryDataset();
			HashMap<String,Integer> bookDataViews = ((CLibraryManager)AUser.getInstance()).getBookViews((String)getM_jComboBox_Years().getSelectedItem());
			for(int i=0;i<12;i++)
			{
				DCD.addValue(bookDataViews.get(m_months[i]), "Searches", m_months[i]);
			}
			HashMap<String,Integer> bookDataSales = ((CLibraryManager)AUser.getInstance()).getBookSales((String)getM_jComboBox_Years().getSelectedItem());
			for(int i=0;i<12;i++)
			{
				DCD.addValue(bookDataSales.get(m_months[i]), "Orders", m_months[i]);
			}		
			JFreeChart bookChart = ChartFactory.createBarChart3D(CBookDetailPanel.getBook().getM_title() + " - " + (String)getM_jComboBox_Years().getSelectedItem(), "Months", "Total", DCD, PlotOrientation.VERTICAL, true, false, false);
			bookChart.setBackgroundPaint(new Color(238,238,238));
			m_chart = new ChartPanel(bookChart);
			m_chart.setLocation(5,0);
			m_chart.setSize(685,440);
			m_chart.setBackground(new Color(238,238,238));
			m_chart.setVisible(true);
		}
		return m_chart;
	}

	/**
	 * On any button pressing from this panel, the panel changes to the
	 * previous one.
	 */
	public void actionPerformed(ActionEvent ae) {
		this.setVisible(false);
	}

	/**
	 * This method initializes m_jComboBox_Years	
	 * 	
	 * @return javax.swing.JComboBox	
	 * @throws Exception 
	 */
	private JComboBox getM_jComboBox_Years() throws Exception {
		if (m_jComboBox_Years == null) {
			m_jComboBox_Years = new JComboBox(m_years);
			m_jComboBox_Years.setSize(new Dimension(150, 25));
			m_jComboBox_Years.setPreferredSize(new Dimension(150, 25));
			m_jComboBox_Years.setLocation(new Point(258, 445));
			m_jComboBox_Years.setSelectedItem(m_lastYear);
			m_jComboBox_Years.addItemListener(this);
		}
		return m_jComboBox_Years;
	}

	/**
	 * Find and setting the most recent year.
	 */
	private void setMaxYear()
	{
		String max = m_years.get(0);
		for(String temp: m_years)
		{
			if(temp.compareTo(max)>0)
			{
				max = temp;
			}
		}
		m_lastYear = max;
	}

	/**
	 * On changing the year from the years combobox, this method is called.
	 * itemStateChanged updates the chart to the relevent year.
	 * @param ie ItemEvent
	 */
	public void itemStateChanged(ItemEvent ie) {
		if(ie.getItemSelectable() == m_jComboBox_Years)
		{
			this.remove(m_chart);
			m_chart = null;
			try {
				this.add(getChart(),null);
				this.validate();
				this.repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
