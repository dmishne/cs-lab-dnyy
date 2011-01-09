package client.gui.generatereport;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class CUserReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		DefaultCategoryDataset DCD = new DefaultCategoryDataset();
		DCD.addValue(4,(Comparable<String>)"Searches",(Comparable<String>)"Jan");
		DCD.addValue(2,(Comparable<String>)"Searches",(Comparable<String>)"Feb");
		DCD.addValue(3,(Comparable<String>)"Searches",(Comparable<String>)"Mar");
		DCD.addValue(10,(Comparable<String>)"Searches",(Comparable<String>)"Apr");
		DCD.addValue(15,(Comparable<String>)"Searches",(Comparable<String>)"May");
		DCD.addValue(6,(Comparable<String>)"Searches",(Comparable<String>)"Jun");
		DCD.addValue(33,(Comparable<String>)"Searches",(Comparable<String>)"Jul");
		DCD.addValue(8,(Comparable<String>)"Searches",(Comparable<String>)"Aug");
		DCD.addValue(39,(Comparable<String>)"Searches",(Comparable<String>)"Sep");
		DCD.addValue(50,(Comparable<String>)"Searches",(Comparable<String>)"Oct");
		DCD.addValue(11,(Comparable<String>)"Searches",(Comparable<String>)"Nov");
		DCD.addValue(17,(Comparable<String>)"Searches",(Comparable<String>)"Dec");
	
		
		
		DCD.addValue(2,(Comparable<String>)"Orders",(Comparable<String>)"Jan");
		DCD.addValue(1,(Comparable<String>)"Orders",(Comparable<String>)"Feb");
		DCD.addValue(2,(Comparable<String>)"Orders",(Comparable<String>)"Mar");
		DCD.addValue(7,(Comparable<String>)"Orders",(Comparable<String>)"Apr");
		DCD.addValue(10,(Comparable<String>)"Orders",(Comparable<String>)"May");
		DCD.addValue(3,(Comparable<String>)"Orders",(Comparable<String>)"Jun");
		DCD.addValue(24,(Comparable<String>)"Orders",(Comparable<String>)"Jul");
		DCD.addValue(4,(Comparable<String>)"Orders",(Comparable<String>)"Aug");
		DCD.addValue(30,(Comparable<String>)"Orders",(Comparable<String>)"Sep");
		DCD.addValue(44,(Comparable<String>)"Orders",(Comparable<String>)"Oct");
		DCD.addValue(10,(Comparable<String>)"Orders",(Comparable<String>)"Nov");
		DCD.addValue(17,(Comparable<String>)"Orders",(Comparable<String>)"Dec");

		
		
		
		
		JFreeChart userChart = ChartFactory.createBarChart3D("Harry Potter", "Months", "Total", DCD, PlotOrientation.VERTICAL, true, false, false);
		
		ChartPanel cp = new ChartPanel(userChart);
		
		JFrame mainFrame = new JFrame("Checking");
		mainFrame.setSize(700, 550);
		mainFrame.setResizable(false);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(BorderLayout.CENTER,cp);
		mainFrame.setVisible(true);

	}

}
