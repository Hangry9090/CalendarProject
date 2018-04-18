/**
 * 
 */
package scheduler;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.*;

/**
 * @author jacobwalton
 *
 */
public class MainGUI extends NetbeansGUI {
	private Scheduler scheduler;
	private LoginView loginView;
	
	/**
	 * 
	 */
	public MainGUI() {
		super();
		
		scheduler = new Scheduler();
		
		//add Export action listener
		this.viewExportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportToIcs();
            }
        });
		
		//add import action listener in view window
		this.viewImportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importViewSchedule();
            }
        });
		
		//add import action listener in view window
		this.compareImportIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importCompareSchedule();
            }
        });
		
	}

	
	/*
	 * Method to respond when import is hit in the compare tab.
	 */
	protected void importCompareSchedule() {
		loginView = LoginView.getInstance(scheduler);
	}

	/*
	 * Method to respond when import button is hit in view window.
	 */
	protected void importViewSchedule() {
		loginView = LoginView.getInstance(scheduler);
	}

	/*
	 * Method to respond when export button is clicked.
	 */
    protected void exportToIcs() {
		System.out.println("Export hit");
		String savePath;
		JFileChooser dialog = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ICS Files", "ics");
		dialog.setFileFilter(filter);
		int returnVal = dialog.showSaveDialog(getParent());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			savePath = dialog.getSelectedFile().getAbsolutePath();
			System.out.println("You chose to open this file: " + savePath);
		}
	
		
		//userSchedule.outputFile(savePath);
		
	}

	/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainGUI mainFrame = new MainGUI();
                mainFrame.setResizable(true);
                mainFrame.setVisible(true);
            }
        });
    }

}
