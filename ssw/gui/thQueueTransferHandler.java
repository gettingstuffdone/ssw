/*    */ package ssw.gui;
/*    */ 
/*    */ import java.awt.datatransfer.Transferable;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.TransferHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class thQueueTransferHandler
/*    */   extends TransferHandler
/*    */ {
/*    */   public Transferable createTransferable(JComponent comp)
/*    */   {
/* 43 */     LocationDragDatagram d = new LocationDragDatagram();
/* 44 */     d.Location = -1;
/* 45 */     d.SourceIndex = ((JList)comp).getSelectedIndex();
/* 46 */     return d;
/*    */   }
/*    */   
/*    */   public int getSourceActions(JComponent c)
/*    */   {
/* 51 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\thQueueTransferHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */