package client.gui;


import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import client.core.CBook;
import java.awt.Point;

public class CBookDetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back_BDP = null;
	private CBook m_book;

	/**
	 * This is the default constructor
	 */
	public CBookDetailPanel(CBook book) {
		super();
		m_book = book;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(getM_jButton_back_BDP(), null);
	}

	/**
	 * This method initializes m_jButton_back_BDP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_back_BDP() {
		if (m_jButton_back_BDP == null) {
			m_jButton_back_BDP = new JButton();
			m_jButton_back_BDP.setText("Back");
			m_jButton_back_BDP.setSize(new Dimension(208, 34));
			m_jButton_back_BDP.setLocation(new Point(94, 480));
			m_jButton_back_BDP.setPreferredSize(new Dimension(208, 34));
		}
		return m_jButton_back_BDP;
	}

	/**
	 * @return the m_book
	 */
	public CBook getBook() {
		return m_book;
	}

}
