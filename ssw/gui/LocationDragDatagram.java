/*    */ package ssw.gui;
/*    */ 
/*    */ import java.awt.datatransfer.DataFlavor;
/*    */ import java.awt.datatransfer.Transferable;
/*    */ import java.awt.datatransfer.UnsupportedFlavorException;
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocationDragDatagram
/*    */   implements Serializable, Transferable
/*    */ {
/* 38 */   public int Location = -1;
/* 39 */   public int SourceIndex = -1;
/* 40 */   public boolean Locked = false;
/* 41 */   public boolean Empty = false;
/*    */   
/*    */   public DataFlavor[] getTransferDataFlavors() {
/* 44 */     DataFlavor d = new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram");
/* 45 */     DataFlavor[] retval = { d };
/* 46 */     return retval;
/*    */   }
/*    */   
/*    */   public boolean isDataFlavorSupported(DataFlavor flavor) {
/* 50 */     if (flavor.getRepresentationClass() == LocationDragDatagram.class) {
/* 51 */       return true;
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */   
/*    */   public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
/*    */   {
/* 58 */     if (flavor.getRepresentationClass() == LocationDragDatagram.class) {
/* 59 */       return this;
/*    */     }
/* 61 */     throw new UnsupportedFlavorException(flavor);
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\LocationDragDatagram.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */