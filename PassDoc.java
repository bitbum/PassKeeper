// Pass Application  Version 1
// PassDoc.java
// Dave Shean - March 18, 1999

import java.util.*;
import java.io.*;
import java.awt.Point;
import java.awt.Dimension;

public class PassDoc {
  PassWindow wnd;
	
   static public class Options implements Cloneable {
      public boolean confirm;
      public boolean autoSave;
   }

   static public class Record {
   	String account;
   	String userName;
   	String passWord;
   	String notes;
   }
 
   public List passList;
   
   protected int count;
   
   protected boolean dirty;
   
   public PassDoc(PassWindow window) {
   	this.wnd = window;
      passList = new FastList();   
   }
	
	public void add(Record record) {
		passList.add(record);
		wnd.update();
	}
	
	public void update(Record record) {
		if (!wnd.passList.isSelectionEmpty()) {
         int ndx = wnd.passList.getSelectedIndex();
			int cntr = 0;
         System.out.println("Selected Index: " + ndx);
         ListIterator itr;
         
         itr = passList.listIterator();
         while ( itr.hasNext() && cntr <= ndx) {   
            itr.next();
            //System.out.println(((Record)itr.next()).account);
            cntr++;
         }
         
         itr.set(record);
         wnd.update();
		}
	}

   
   final public int getCount()           {return count;}
   final public boolean isDirty()        {return dirty;}
	
	public synchronized void save(PrintWriter out) throws IOException {
   
      ListIterator itr;
		Record rec;
      
      itr = passList.listIterator();
		
		out.println(wnd.passWord);
		
      while (itr.hasNext()) {   
         rec = (Record)itr.next();
      	out.println(rec.account);
      	out.println(rec.userName);
      	out.println(rec.passWord);
      	out.println(rec.notes);
      }

      if (out.checkError())
         throw new IOException("Error saving current game");
      dirty = false;
   }
	
	
   public synchronized void load(BufferedReader in) throws IOException {
      int inCount;
   	ListIterator itr;
      Record rec;
      String inString;
   	
      passList.clear();
   	itr = passList.listIterator();
   	
   	rec = null;
   	
   	
      try {
      	wnd.passWord = in.readLine();
      	
         while ((inString = in.readLine()) != null) {
         	rec =  new Record();
         	System.out.println(inString);

            rec.account = inString;
         	
            inString = in.readLine();
         	System.out.println(inString);
         	rec.userName = inString;
         		
         	inString = in.readLine();
            System.out.println(inString);
            rec.passWord = inString;
         	
         	inString = in.readLine();
            System.out.println(inString);
            rec.notes = inString;
         	
         	itr.add(rec);
         	
         }
      }
      catch (NumberFormatException err) {
         throw new IOException("Data format error: " + err.getMessage());
      }
      catch (Throwable err) {
         throw new IOException(err.getMessage());
      }
   	wnd.update();
   }	
}