import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
public class TonksRecordFrame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("TonksRecord");
		frame.setPreferredSize(new Dimension(700, 700));
		frame.setMaximumSize(new Dimension(700, 700));
		frame.setMinimumSize(new Dimension(700, 700));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(new TonksPanel());
		frame.setVisible(true);
		
	}
}
