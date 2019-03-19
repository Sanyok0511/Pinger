package frame;

import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JMenu menuOption, menuHelp;
	JCheckBoxMenuItem alwOnTop;
	public MainMenu() {
		menuOption = new JMenu("Option");
		alwOnTop = new JCheckBoxMenuItem("Always on top");
		alwOnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateForm.changeAlwOnTop(alwOnTop.isSelected());
			}
		});
		menuOption.add(alwOnTop);
		menuHelp = new JMenu("Help");
		add(menuOption);
		add(menuHelp);
		
	}
}
