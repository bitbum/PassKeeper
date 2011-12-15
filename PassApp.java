// Pass Application  Version 1
// PassApp.java
// Dave Shean - March 18, 1999

import java.awt.event.*;

public class PassApp extends WindowAdapter {
   
   static private PassApp app = new PassApp();
   private PassWindow passWin;

   //final static public LifeApp getLifeApp() {
   //   return app;
   //}
   
   final public PassWindow getPassWin()     {
      return passWin;
   }

   public static void main(String args[]) {
      app.passWin = new PassWindow();
      app.passWin.addWindowListener(app);
      app.passWin.pack();               // Create interface element and pack it
   	
  
      //app.passWin.setVisible(true);     // Newer replacement for show().
   }

   public void windowClosed(WindowEvent evt) {
      System.exit(0);
   }
}