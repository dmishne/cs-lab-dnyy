package client.gui.reviews;

import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.*;

import common.data.CBookReview;

public class mainf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<CBookReview> arg=new LinkedList<CBookReview>();
		arg.add(new CBookReview("123","test","the hobbit","book sucks","30/12/2010", 1,"30/12/2010","daniel"));
		arg.add(new CBookReview("123","test1","the hobbit","book rocks","30/12/2010", 1,"30/12/2010","daniel"));
		arg.add(new CBookReview("321","test","the two towers","book sucks","30/12/2010", 1,"30/12/2010","daniel"));
		arg.add(new CBookReview("321","test1","the two towers" ,"book rocks","30/12/2010", 1,"30/12/2010","daniel"));
		arg.add(new CBookReview("444","test","don't let me in" ,"book sucks","30/12/2010", 1,"30/12/2010","daniel"));
		arg.add(new CBookReview("444","test1","don't let me in","book rocks","30/12/2010", 1,"30/12/2010","daniel"));

		JFrame a=new JFrame();
		CReviewResults sub=new CReviewResults(arg);
		JPanel blah=new JPanel();
		blah.setPreferredSize(new Dimension(300,300));
		blah.add( new JLabel("this is blah"),null);
		blah.add(sub.getPanel(),null);
		blah.setVisible(true);
		a.add(blah);
		a.setLayout(null);
		a.pack();
		a.setVisible(true);
		a.setContentPane(blah);
	}

}
