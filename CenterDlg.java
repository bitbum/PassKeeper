// Life Application  Version 6
// CenterDlg.java
// Dave Shean - February 24, 1999

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class CenterDlg extends JDialog {
   JFrame parent;
   
   CenterDlg(JFrame par, String title, boolean modality) {
      super(par, title, modality);   // Modal or Non Modal Dialog
      setResizable(false);
      parent = par;
   }
   public void pack() {
      super.pack();
      
      // centers dialog relative to parent
      setLocationRelativeTo(parent);
   }
}