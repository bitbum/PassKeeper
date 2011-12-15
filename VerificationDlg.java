// Pass Application  Version 1
// VerificationDlg.java
// Dave Shean - March 18, 1999

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class VerificationDlg extends CenterDlg implements ActionListener {
   
   TextField passField;
   List ptList;
	GridBagConstraints cns;
   
   JButton okButton;
	JButton cancelButton;
   
   JPanel dlgPanel;
	
	JFrame par;

	String pwd;
	
   VerificationDlg(JFrame par, String pwd) {
      super(par, "Password Keeper - Enter Password", true);   // Modal Dialog
   	
   	this.par = par;
   	this.pwd = pwd;
      
      okButton = new JButton("OK");
      okButton.addActionListener(this);
      
      dlgPanel = new JPanel();
      dlgPanel.setLayout(new GridBagLayout());
      cns = new GridBagConstraints();
      
      cns.gridx = cns.gridy = 0;
   	cns.insets = new Insets(2,2,2,2);
   	
      dlgPanel.add(new JLabel("Password"), cns);
   	
   	cns.gridx++;
      
   	passField = new TextField(30);
   	
   	dlgPanel.add(passField, cns);
   	
   	cns.gridy++;
   	//cns.gridx = 0;
      
      dlgPanel.add(okButton, cns);
   	
   	cancelButton = new JButton("Cancel");
   	
   	cns.gridx++;
   	
   	dlgPanel.add(cancelButton, cns);
      
      Container cp = this.getContentPane();
      
   
      Border rb = BorderFactory.createEtchedBorder();
      dlgPanel.setBorder(BorderFactory.createTitledBorder(rb,
       "Enter Password", TitledBorder.LEFT, TitledBorder.TOP));

      
      cp.add(dlgPanel);
   
      pack();
   }
   
   public void actionPerformed(ActionEvent evt) {
      Object src = evt.getSource();
      if (src == okButton) {     
         System.out.println("OK pressed");
         doOk();
         setVisible(false);
         dispose();
      }
      else {
         //doCancel();
         setVisible(false);
         dispose();

      }
         
   }   

   protected void doOk() {
   	if (pwd != null) {
   		if (passField.getText().equals(pwd)) {
            ((PassWindow)par).passWord = passField.getText();
            ((PassWindow)par).setVisible(true);
   		}
   		else
   	   {
   	   }
   	}
   	else {
         ((PassWindow)par).passWord = passField.getText();
   	   ((PassWindow)par).setVisible(true);
   	}
   }
}