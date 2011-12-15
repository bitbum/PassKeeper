// Pass Application  Version 1
// EditDlg.java
// Dave Shean - March 18, 1999


import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class EditDlg extends CenterDlg implements ActionListener {
   
   TextField rowText;
   TextField colText;
   java.util.List ptList;
   
   int result;
   
   JButton okButton;
   JButton cancelButton;
   JTextField accountBox;
   JTextField userNameBox;
   JTextField passBox;
   JTextArea notesBox;
   
   PassDoc.Record rec;
   PassDoc doc;
	PassWindow wnd;
   
   JPanel dlgPanel;
   
   EditDlg(JFrame par, PassDoc doc, PassWindow wnd) {
      super(par, "Edit an account", true);   // Modal Dialog
      
      this.doc = doc;
   	this.wnd = wnd;
      
      rec = new PassDoc.Record();
      
      Border rb = BorderFactory.createEtchedBorder();
      
      okButton = new JButton("OK");
      cancelButton = new JButton("Cancel");
      
      okButton.addActionListener(this);
      
      dlgPanel = new JPanel();
      dlgPanel.setLayout(new GridBagLayout());
      GridBagConstraints cns = new GridBagConstraints();
      cns.gridx = 0;
      cns.gridy = 0;
      cns.insets = new Insets(2,2,2,2);
      
      cns.anchor = GridBagConstraints.WEST;
      
      
      dlgPanel.add(new JLabel("Account: "), cns);
   
      cns.gridx++;
      cns.gridy = 0;
      
      dlgPanel.add(accountBox = new JTextField(30), cns);
      
      cns.gridy++;
      cns.gridx = 0;
      
      dlgPanel.add(new JLabel("User Name: "), cns);

      cns.gridx++;
      cns.gridy = 1;
      
      dlgPanel.add(userNameBox = new JTextField(30), cns);
      
      cns.gridy++;
      cns.gridx = 0;
      
      dlgPanel.add(new JLabel("Password: "), cns);
      
      cns.gridx++;
      cns.gridy = 2;
 
      dlgPanel.add(passBox = new JTextField(30), cns);
      
      cns.gridy++;
      cns.gridx = 0;
      
      dlgPanel.add(new JLabel("Notes: "), cns);
      
      cns.gridx++;
      cns.gridy = 3;
      
      notesBox = new JTextArea(10, 30);
      notesBox.setBorder(BorderFactory.createLineBorder(Color.gray));

      dlgPanel.add(notesBox, cns);
      
      cns.gridy++;
      cns.gridx = 1;
      cns.anchor = GridBagConstraints.CENTER;
      
      Box box = new Box(BoxLayout.X_AXIS);
      box.add(okButton);
      box.add(Box.createRigidArea(new Dimension(10, 10)));
      box.add(cancelButton);
      
      
      dlgPanel.add(box, cns);
   
      
      Container cp = this.getContentPane();
      
   
      
      dlgPanel.setBorder(BorderFactory.createTitledBorder(rb,
       "About", TitledBorder.LEFT, TitledBorder.TOP));

      
      cp.add(dlgPanel);
   
      pack();
   	
   	loadRecord();
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
         doCancel();
         setVisible(false);
         dispose();

      }
         
   }   
   
   void doCancel() {
      result = 0;
   }
	void doOk() {
		rec.account = accountBox.getText();
      rec.userName = userNameBox.getText();
      rec.passWord = passBox.getText();
      rec.notes = notesBox.getText();
      
      //passList.add(rec);
      result  = 1;
		doc.update(rec);
	}
   
   protected void loadRecord() {
   	int ndx = wnd.passList.getSelectedIndex();
   	
      Iterator itr;
      
      int cntr = 0;
      
   	itr = doc.passList.iterator();
      while ( itr.hasNext() && cntr < ndx) {   
         itr.next();
         cntr++;
      }
      
   	PassDoc.Record rec = (PassDoc.Record)itr.next();
   		
   		
   	accountBox.setText(rec.account);
      userNameBox.setText(rec.userName);
      passBox.setText(rec.passWord);
      notesBox.setText(rec.notes);
      
      //passList.add(rec);
      //result  = 1;
      //doc.update(rec);
   }
}