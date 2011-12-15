// Pass Application  Version 1
// AboutDlg.java
// Dave Shean - March 18, 1999

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class AboutDlg extends CenterDlg implements ActionListener {
   
   TextField rowText;
   TextField colText;
   List ptList;
   
   JButton okButton;
   
   JPanel dlgPanel;
   
   AboutDlg(JFrame par) {
      super(par, "About", true);   // Modal Dialog
   	
   	okButton = new JButton("OK");
   	okButton.addActionListener(this);
   	
      dlgPanel = new JPanel();
      dlgPanel.setLayout(new BoxLayout(dlgPanel, BoxLayout.Y_AXIS));
   	
   	
   	
   	dlgPanel.add(new JLabel("Password Locker alpha 1.0"));
   
   	
   	dlgPanel.add(new JLabel("by David Shean"));
   
   	
   	dlgPanel.add(new JLabel("c 1999 - 2001 Demented Applications Group"));
   	
   	dlgPanel.add(new JLabel("http://home.earthlink.net/~eshean/"));
   
   	
   	dlgPanel.add(okButton);
   	
      Container cp = this.getContentPane();
   	
   
   	Border rb = BorderFactory.createEtchedBorder();
   	dlgPanel.setBorder(BorderFactory.createTitledBorder(rb,
       "About", TitledBorder.LEFT, TitledBorder.TOP));

   	
      cp.add(dlgPanel);
   
      pack();
   }
	
   public void actionPerformed(ActionEvent evt) {
      Object src = evt.getSource();
      if (src == okButton) {     
         System.out.println("OK pressed");
         //doOk();
         setVisible(false);
         dispose();
      }
      else {
         //doCancel();
         setVisible(false);
         dispose();

      }
         
   }	
}

      
      