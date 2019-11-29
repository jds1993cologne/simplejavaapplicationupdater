package de.systeme.system.simplejavaapplicationupdater;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class UpdaterProgressMonitor extends JDialog{

	private static final long serialVersionUID = 1843642078212793403L;
	private JLabel jLText;
	private Container cp;
	
	/**
	 * opens the dialog and sets the status text
	 * 
	 * @param parentComponent	the parent Component
	 * @param startText			the first status text
	 */
	public UpdaterProgressMonitor(Component parentComponent, String startText) {
		
		super((Frame) parentComponent);
		
		setSize(300, 75);
		
		cp = getContentPane();
		
		jLText = new JLabel(startText);
		jLText.setBounds(10, 10, 275, 20);
		
		cp.add(jLText);
		
		JProgressBar bar = new JProgressBar();
		bar.setBounds(10, 40, 275, 20);
		bar.setIndeterminate(true);
		
		cp.add(bar);
		
		setResizable(false);
		setVisible(true);
		
	}

	/**
	 * updates the status text
	 * 
	 * @param text	the new status text
	 */
	public void setText(String text) {
		jLText.setText(text);		
	}

	/**
	 * closes the monitor
	 */
	public void dismiss() {
		dismiss();
	}

}