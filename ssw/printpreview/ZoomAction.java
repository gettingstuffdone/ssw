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
/*    */ class ZoomAction
/*    */   extends AbstractAction
/*    */ {
/*    */   protected Preview preview;
/*    */   protected double zoomStep;
/*    */   protected boolean resetZoom;
/*    */   
/*    */   public ZoomAction(String name, String iconName, Preview preview, double zoomStep, boolean resetZoom)
/*    */   {
/* 32 */     if (!iconName.isEmpty()) {
/* 33 */       ImageIcon icon = null;
/* 34 */       icon = new ImageIcon(getClass().getResource("/ssw/Images/" + iconName));
/* 35 */       putValue("SmallIcon", icon);
/*    */     }
/*    */     
/* 38 */     if (!name.isEmpty()) {
/* 39 */       putValue("Name", name);
/*    */     }
/*    */     
/* 42 */     this.preview = preview;
/* 43 */     this.zoomStep = zoomStep;
/* 44 */     this.resetZoom = resetZoom;
/*    */   }
/*    */   
/*    */   public ZoomAction(Preview preview, double zoomStep) {
/* 48 */     this("", "", preview, zoomStep, false);
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent e) {
/* 52 */     if (this.resetZoom) {
/* 53 */       this.preview.setZoom(this.zoomStep);
/*    */     } else {
/* 55 */       this.preview.changeZoom(this.zoomStep);
/*    */     }
/* 57 */     this.preview.repaint();
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\printpreview\ZoomAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */