// Pass Application  Version 1
// PassWindow.java
// Dave Shean - March 18, 1999

import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;

public class PassWindow extends JFrame
{
	protected Action changePasswordAction;
   protected Action autoSaveAction;
   protected Action confirmRemoveAction;
   protected Action aboutAction;
   protected Action fileExitAction;
	protected Action addAction;
	protected Action removeAction;
	protected Action editAction;
	
   protected File inputFile;   
	
	protected JToolBar tb;
	
	protected ScrollPane scroller;
	protected JList passList;
	
	protected JButton addButton;
	protected JButton removeButton;
	protected JButton editButton;
	
	protected PassDoc doc;
	
	protected AboutDlg aboutDlg;
	protected AddDlg addDlg;
	protected EditDlg editDlg;
	
	protected VerificationDlg verDlg;
	
	public String passWord;

	
	
	protected class WinWindowListener extends WindowAdapter {
      public void windowClosing(WindowEvent evt) {
         dispose();
      }
   }
	
   public PassWindow() {
      super("Password Guard");
   	
   	doc = new PassDoc(this);
   	
   	tb = new JToolBar();
   	tb.setFloatable(false);
   	
      setupActions();
      setupMenu();
      setupLayout();
      addWindowListener(new WinWindowListener());
      
      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
   	
   	inputFile = new File("data.txt");
   	
   	doLoad();
   	
   	verDlg = new VerificationDlg(this, passWord);
      verDlg.addWindowListener(new WinWindowListener());
      verDlg.pack();
      verDlg.setVisible(true);
   }
   
   
   protected void setupActions() {
      // Create Action objects for the new, open, save, save as, exit menu items
      changePasswordAction = new AbstractAction("Change Password...") {
         public void actionPerformed(ActionEvent evt) {
           doChangePass();
         }
      };
      
   	addAction = new AbstractAction("Add") {
         public void actionPerformed(ActionEvent evt) {
           doAdd();
         }
      };

   	removeAction = new AbstractAction("Remove") {
         public void actionPerformed(ActionEvent evt) {
           doRemove();
         }
      };

   	editAction = new AbstractAction("Edit") {
         public void actionPerformed(ActionEvent evt) {
           doEdit();
         }
      };

   	
      autoSaveAction = new AbstractAction("Auto Save On Exit") {
         public void actionPerformed(ActionEvent evt) {
           doAutoSave();
         }
      };
      
      confirmRemoveAction = new AbstractAction("Confirm On Remove") {
         public void actionPerformed(ActionEvent evt) {
           doAutoConfirm();
         }
      };      
      
      aboutAction = new AbstractAction("About...") {
         public void actionPerformed(ActionEvent evt) {
           doAbout();
         }
      };      
      
      
      fileExitAction = new AbstractAction("Exit") {
      public void actionPerformed(ActionEvent evt) {
            System.out.println("Exit action calling dispose");
      	   doExit();
            dispose();
         }
      };
   }
   
   
   protected void setupMenu() {
      JMenuBar mb = new JMenuBar();
      
      JMenu fileMenu = new JMenu("File");
      fileMenu.setMnemonic('F');
      // eliminate menu consumption problem
      //fileMenu.getPopupMenu().setLightWeightPopupEnabled(false);  
      
      JMenuItem mi;

      
      mi = fileMenu.add(fileExitAction);
      mi.setMnemonic('x');
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
       InputEvent.CTRL_MASK, false));
      
      mb.add(fileMenu);

      JMenu optMenu = new JMenu("Options");
      optMenu.setMnemonic('O');
      // eliminate menu consumption problem
      //gameMenu.getPopupMenu().setLightWeightPopupEnabled(false);      

      mi = optMenu.add(confirmRemoveAction);
      mi.setMnemonic('C');
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
       InputEvent.CTRL_MASK, false));
      
      mi = optMenu.add(autoSaveAction);
      mi.setMnemonic('A');
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
       InputEvent.CTRL_MASK, false));
       
      optMenu.addSeparator(); 
      
      mi = optMenu.add(changePasswordAction);
      mi.setMnemonic('P');
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
       InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK , false));


      mb.add(optMenu);
      
      JMenu helpMenu = new JMenu("Help");
      helpMenu.setMnemonic('H');
      
      mi = helpMenu.add(aboutAction);
      mi.setMnemonic('B');
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
       InputEvent.CTRL_MASK, false));

      mb.add(helpMenu);
      
      setJMenuBar(mb);
   }
   
	protected void setupLayout() {
      Container cp = this.getContentPane();
      
      //gridbag constraint for content pane (cp)
      GridBagConstraints cns = new GridBagConstraints();
      cp.setLayout(new GridBagLayout());
		
		addButton = tb.add(addAction);
		removeButton = tb.add(removeAction);
		removeButton.setEnabled(false);
		
		editButton = tb.add(editAction);
		editButton.setEnabled(false);

		cns.anchor = GridBagConstraints.WEST;
		cp.add(tb, cns);
		
		dataList[0] = "Empty";
	   passList = new JList(dataList);
  
      passList.setVisibleRowCount(3);
      JScrollPane scroller = new JScrollPane(passList);
		scroller.getViewport().setPreferredSize(new Dimension(200,200));
		//scroller.getViewport().setMinimumSize(new Dimension(200,200));
		
		scroller.setPreferredSize(new Dimension(200,200));
      //scroller.setMinimumSize(new Dimension(200,200));

		
		scroller.getViewport().getView().addMouseListener(new LVMouseListener());
		
		cns.insets = new Insets(2,2,2,2);
		//cns.ipadx = 20;
		
      cns.gridx = 0; cns.gridy = 1;
		cns.anchor = GridBagConstraints.WEST;
	///	cns.gridheight = 4;
		//cns.gridwidth = 4;
		cns.fill = GridBagConstraints.BOTH;
		cns.weightx = 0.5;
		cns.weighty = 0.5;
		cp.add(scroller, cns);
		
		pack();
      
      Border rb = BorderFactory.createEtchedBorder();
      scroller.setBorder(BorderFactory.createTitledBorder(rb,
       "Accounts", TitledBorder.LEFT, TitledBorder.TOP));
		
   }
	
   protected void doAbout() {
   	aboutDlg = new AboutDlg(this);
   	aboutDlg.setVisible(true);
   }
   
   protected void doAutoConfirm() {
   }
   
   protected void doAutoSave() {
   }
   
   protected void doChangePass() {
   }
	
	protected void doAdd() {
		addDlg = new AddDlg(this, doc);
      addDlg.setVisible(true);
	}
	
	protected void doEdit() {
	   if (!passList.isSelectionEmpty()) {
		   editDlg = new EditDlg(this, doc, this);
         editDlg.setVisible(true);
		}

	}
	
	protected void doRemove() {
		if (!passList.isSelectionEmpty()) {
         int ndx = passList.getSelectedIndex();
         int cntr = 0;
         Iterator itr;
         
         itr = doc.passList.iterator();
         while ( itr.hasNext() && cntr <= ndx) {   
            itr.next();
            cntr++;
         }
         itr.remove();
         update();
		}
	}
	
	protected void doExit() {
		writeDoc();
	}
	
   protected void writeDoc() {
      try {
         PrintWriter outWr = new PrintWriter(new OutputStreamWriter(
          new FileOutputStream(inputFile)));
         doc.save(outWr);
         outWr.close();
      }
      catch (IOException err) {
         JOptionPane.showMessageDialog(this, "Error saving: " +
          err.getMessage(), "Save Error", JOptionPane.OK_OPTION );

      }
   }	
	
	protected void doLoad() {
      if (inputFile.canRead()) {
         try {
            BufferedReader fileReader = 
             new BufferedReader(new InputStreamReader(
             new FileInputStream(inputFile)));
                
            doc.load(fileReader);
            fileReader.close();
         }
         catch(IOException ex) {
            JOptionPane.showMessageDialog(this,
             "Error reading " + inputFile + ": " + ex.getMessage(),
             "Load Error", 0);
            inputFile = null;
         }
      }
   }
	
	public void update() {
		Iterator itr;
		PassDoc.Record rec;
		int ndx = 0;
		
		dataList = new String[256];

		for (itr = doc.passList.iterator(); itr.hasNext();) {	
			rec = (PassDoc.Record)itr.next();
			dataList[ndx++] = rec.account;
		}
		passList.setListData(dataList); 
	}
	
	protected class LVMouseListener extends MouseAdapter {
		
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 2) {
				doEdit();
			}
		}
		
		public void mousePressed(MouseEvent evt) {
			if (!passList.isSelectionEmpty()) {
				editButton.setEnabled(true);
				removeButton.setEnabled(true);
			}
		}
	}
	
	public String[] dataList = new String[256];
}