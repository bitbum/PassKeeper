// Pass Application  Version 1
// FastList.java
// Dave Shean - March 18, 1999

import java.util.*;

public class FastList extends AbstractSequentialList {
   Entry header;
   Entry tail;
   int size = 0;
    
   public FastList() {
      header = new Entry(null, null, null);
      tail = header.next = header.previous = header;
      
   }

   
   public int size() {
      return size;
   }
   

   private Entry entry(int index) {
      if (index < 0 || index >= size)
         throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
      
      Entry targetEntry = header;
      if (index < size/2) {
         for (int i = 0; i <= index; i++)
            targetEntry = targetEntry.next;
      } 
      else {
         for (int i = size; i > index; i--)
         targetEntry = targetEntry.previous;
      }
      return targetEntry;
   }   

   
   public void clear() {
      Iterator itr = FastList.this.iterator();
      while (itr.hasNext()) {
         itr.next();
         itr.remove();
      }
   }

   
   public boolean add(Object obj) {
        if(size > 0)
        {
            tail.next = Entry.get(obj, tail.next, tail);
            tail = tail.next;
              tail.next.previous = tail;
        }
        else
        {
            tail = header.next = Entry.get(obj, header, header);
              header.previous = header.next;
        }
        size++;
        return true;
   }
   
   
   public ListIterator listIterator() {
      return new FastListIterator(header.next, null, 0);
   }
   
   
   public ListIterator listIterator(int index) {
      if (index < 0 || index > size)
         throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
      
      Entry smallEntry, largeEntry;
      
      if (index == size) {
         largeEntry = tail.next;
         smallEntry = tail.previous;
      }
      else if (index < size / 2) {
         largeEntry = header.next;
         
         for (int nextIndex = 0; nextIndex < index; nextIndex++)
            largeEntry = largeEntry.next;
         smallEntry = largeEntry != null ? largeEntry.previous : null;
      } 
      else {
         smallEntry = header;
         
         for (int nextIndex = size; nextIndex > index; nextIndex--)
            smallEntry = smallEntry.previous;
         largeEntry = smallEntry.next;
      }     
      return new FastListIterator(largeEntry, smallEntry, index);
   }
   
   
   private class FastListIterator implements ListIterator {
      protected Entry lastReturned = header;
      protected Entry next;
      protected Entry previous;
      int nextIndex;
      

      FastListIterator(Entry next, Entry previous, int ndx) {
         this.next = next;
         this.previous = previous;
         nextIndex = ndx;
      }

      
      public void set(Object obj) {
         if (lastReturned == header)
            throw new IllegalStateException();
         lastReturned.element = obj;
      }      

      
      public boolean hasNext() {
         return nextIndex != size;
      }

      
      public Object next() {
         if (nextIndex == size)
            throw new NoSuchElementException();

         lastReturned = next;
         next = next.next;
         nextIndex++;
         return lastReturned.element;
      }

      
      public boolean hasPrevious() {
          return nextIndex != 0;
      }

      
      public Object previous() {
         if (nextIndex == 0)
            throw new NoSuchElementException();

          lastReturned = next = next.previous;
          nextIndex--;
          return lastReturned.element;
      }

      
      public int nextIndex() {
          return nextIndex;
      }

      
      public int previousIndex() {
          return nextIndex - 1;
      }
      
      
      public void remove() {
         if (lastReturned == header)
            throw new NoSuchElementException();
         
         if (nextIndex == size)
            tail = tail.previous;
         
         if (next == lastReturned)
            next = lastReturned.next;
         else
            nextIndex--;
         
         lastReturned.previous.next = lastReturned.next;
         lastReturned.next.previous = lastReturned.previous;
         
         size--;
         lastReturned.release();
         lastReturned = header;
      }


      public void add(Object obj) {
          lastReturned = header;
               
          Entry newEntry = Entry.get(obj, next, next.previous);
          newEntry.previous.next = newEntry;
          newEntry.next.previous = newEntry;
         
          if (nextIndex == size || size == 0)
             tail = newEntry;
         
          size++;
          nextIndex++;
      }
      
   }  // end FastListIterator

   
   private static class Entry {
      Object element;
      Entry next;
      Entry previous;
      static Entry freeList = null;
      static int newCntr = 0;

      Entry(Object element, Entry next, Entry previous) {
         this.element = element;
         this.next = next;
         this.previous = previous;
      }
      
      
      static Entry get(Object element, Entry next, Entry previous) {
         if (freeList == null) {
            return new Entry(element, next, previous);
         }
         Entry temp = freeList;
         freeList = freeList.next;
         temp.element = element;
         temp.next = next;
         temp.previous = previous;
         return temp;
      }
      
      
      void release() {
         element = null;
         next = freeList;
         freeList = this;
      }
   }

   
   private Entry addBefore(Object obj, Entry e) {
      Entry newEntry = Entry.get(obj, e, e.previous);
      newEntry.previous.next = newEntry;
      newEntry.next.previous = newEntry;
      size++;
      return newEntry;
   }

   
   private void remove(Entry entry) {
      if (entry == header)
      throw new NoSuchElementException();
  
      Entry temp = entry;
      entry.previous.next = entry.next;
      entry.next.previous = entry.previous;
      size--;
      temp.release();
   }
}