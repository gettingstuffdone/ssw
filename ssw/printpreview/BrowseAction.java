/*    */ package ssw.printpreview;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.ImageIcon;
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
/*    */ class BrowseAction
/*    */   extends AbstractAction
/*    */ {
/*    */   protected Preview preview;
/*    */   protected int pageStep;
/*    */   
/*    */   public BrowseAction(String name, String iconName, Preview preview, int pageStep)
/*    */   {
/* 31 */     if (!name.isEmpty()) {
/* 32 */       putValue("Name", name);
/*    */     }
/*    */     
/* 35 */     if (!iconName.isEmpty()) {
/* 36 */       ImageIcon icon = null;
/* 37 */       icon = new ImageIcon(getClass().getResource("/ssw/Images/" + iconName));
/* 38 */       putValue("SmallIcon", icon);
/*    */     }
/*    */     
/* 41 */     this.preview = preview;
/* 42 */     this.pageStep = pageStep;
/*    */   }
/*    */   
/*    */   public BrowseAction(Preview preview, int pageStep) {
/* 46 */     this("", "", preview, pageStep);
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent e) {
/* 50 */     this.preview.moveIndex(this.pageStep);
/* 51 */     this.preview.repaint();
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\printpreview\BrowseAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */