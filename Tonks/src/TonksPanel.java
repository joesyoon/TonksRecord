import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TonksPanel extends JPanel{
	private Map<String, Integer> map = new TreeMap<String, Integer>();
	private int goal;
	private Queue<String> choice = new LinkedList<String>();
	private String firstName;
	private boolean first = true;
	private int form = 0;
	
	public TonksPanel() {
		setLayout(null);
		Font font = new Font("Impact", Font.BOLD, 30);
		JLabel title = new JLabel("Tonks Record");
		title.setFont(font);
		title.setBounds(250, 0, 200, 50);
		add(title);
		
		JLabel description = new JLabel("This program will keep track of people's scores until a loser is decided!");
		description.setFont(new Font("Century Gothic", Font.LAYOUT_LEFT_TO_RIGHT, 19));
		description.setBounds(30, 30, 700, 50);
		add(description);
		
		JSeparator split = new JSeparator(SwingConstants.HORIZONTAL);
		split.setForeground(Color.BLACK);
		split.setBounds(0, 77, 700, 50);
		add(split);
		
		JLabel finalScore = new JLabel("What final score are you going up to?");
		finalScore.setFont(new Font("Century Gothic", Font.LAYOUT_LEFT_TO_RIGHT, 19));
		finalScore.setBounds(30, 70, 400, 50);
		add(finalScore);
		
		JTextField jtFinal = new JTextField(10);
		jtFinal.setBounds(387, 87, 193, 20);
		
		JLabel nameAdd = new JLabel("Please add the player's name: ");
		nameAdd.setFont(new Font("Century Gothic", Font.LAYOUT_LEFT_TO_RIGHT, 19));
		nameAdd.setBounds(30, 100, 400, 50);
		add(nameAdd);
		
		JTextField jtAdd = new JTextField(10);
		jtAdd.setBounds(320, 118, 260, 20);
		
		JLabel indicator = new JLabel();
		indicator.setBounds(124, 223, 400, 50);
		indicator.setFont(new Font("Impact", Font.LAYOUT_LEFT_TO_RIGHT, 19));
		
		JButton btFinalAdd = new JButton("Add");
		btFinalAdd.setBounds(590, 86, 90, 19);
		btFinalAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goal = Integer.parseInt(jtFinal.getText());
				indicator.setText("" + goal + " | ");
				jtFinal.setText(null);
			}
		});
		
		JLabel question = new JLabel("Did the tonks caller...");
		question.setBounds(95, 150, 400, 50);
		question.setFont(new Font("Impact", Font.LAYOUT_LEFT_TO_RIGHT, 19));
		
		JLabel versa =  new JLabel("Select an option above");
		versa.setFont(new Font("Impact", Font.LAYOUT_LEFT_TO_RIGHT, 19));
		versa.setBounds(230, 200, 400, 50);
		
		JButton btSucceed = new JButton("Succeed");
		btSucceed.setBounds(340, 167, 90, 19);
		btSucceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addChoice();
				form = 1;
				first = true;
				firstName = choice.remove();
				versa.setText("How many points did " + firstName + " have?");
			}
		});
		
		JButton btFail = new JButton("Fail");
		btFail.setBounds(508, 167, 90, 19);
		btFail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				form = 2;
				versa.setText("Who failed the tonks call?");
			}
		});
		
		JTextField increment = new JTextField(10);
		increment.setBounds(168, 240, 300, 20);
		
		JButton btAdd = new JButton("Add");
		btAdd.setBounds(590, 118, 90, 19);
		
		JTextArea stats = new JTextArea();
		stats.setFont(new Font("Century Gothic", Font.BOLD, 30));
		stats.setBounds(50, 100, 100, 12);
		stats.setEditable(true);
		btAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = jtAdd.getText().toLowerCase();
				map = addMap(name, map);
				jtAdd.setText(null);
				String roster = "";
				for(String s: map.keySet()) {
					roster = roster + s + " " + map.get(s) + "\n";
				}
				stats.setText(roster);
			}
		});
		
		JButton btIncAdd = new JButton("Add");
		btIncAdd.setBounds(480, 240, 90, 19);
		btIncAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(form == 2) {
					String loser = increment.getText();
					for(String word: map.keySet()) {
						if(loser.equalsIgnoreCase(word)) {
							map.put(word, map.get(word) + 30);
						}
					}
					increment.setText(null);
					versa.setText("Select an option above");
					form = 0;
				} else if (form == 1) {
					if(first) {
						int points = Integer.parseInt(increment.getText());
						for(String s: map.keySet()) {
							if(firstName.equalsIgnoreCase(s)) {
								map.put(s, map.get(s) + points);
							}
						}
						increment.setText(null);
						if(choice.size() == 1) {
							first = false;
						}
						if(!choice.isEmpty()) {
							firstName = choice.remove();
						}
						versa.setText("How many points did " + firstName + " have?");
					} else {
						int points = Integer.parseInt(increment.getText());
						for(String s: map.keySet()) {
							if(firstName.equalsIgnoreCase(s)) {
								map.put(s, map.get(s) + points);
							}
						}
						form = 0;
						increment.setText(null);
						versa.setText("Select an option above");
					}
				}
				String roster = "";
				for(String word: map.keySet()) {
					roster = roster + word + " " + map.get(word) + "\n";
				}
				stats.setText(roster);
				for(String s: map.keySet()) {
					int points = map.get(s);
					if(points >= goal) {
						stats.setText("GG " + s + " loses! \n Please press the reset \n button and play again.");
					}
				}
			}
		});
		
		JScrollPane scroll = new JScrollPane(stats);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(30, 300, 630, 300);
		
		JButton reset = new JButton("Reset");
		reset.setBounds(150, 630, 90, 19);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stats.setText(null);
				form = 0;
				increment.setText(null);
				map = new TreeMap<String, Integer>();
				choice.clear();
				jtFinal.setText(null);
				goal = 0;
				jtAdd.setText(null);
				versa.setText("Select an option above");
				indicator.setText(null);
			}
		});
		
		JButton exit = new JButton("Exit");
		exit.setBounds(435, 630, 90, 19);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(exit);
		add(reset);
		add(versa);
		add(btIncAdd);
		add(increment);
		add(btFail);
		add(btSucceed);
		add(question);
		add(indicator);
		add(btFinalAdd);
		add(jtFinal);
		add(scroll);
		add(btAdd);		
		add(jtAdd);
		
	}
	
	public void addChoice() {
		for(String s: map.keySet()) {
			choice.add(s);
		}
	}
	
	public Map<String, Integer> addMap(String name, Map<String, Integer> map) {
		if(!map.containsKey(name)) {
			map.put(name, 0);
		}
		return map;
	}

}
