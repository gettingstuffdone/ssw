/*    */ package ssw.gui;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.io.File;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JFileChooser;
/*    */ 
/*    */ public class ImagePreview extends javax.swing.JComponent implements java.beans.PropertyChangeListener
/*    */ {
/* 11 */   ImageIcon thumbnail = null;
/* 12 */   File file = null;
/*    */   
/*    */   public ImagePreview(JFileChooser fc) {
/* 15 */     setPreferredSize(new java.awt.Dimension(100, 50));
/* 16 */     fc.addPropertyChangeListener(this);
/*    */   }
/*    */   
/*    */   public void loadImage() {
/* 20 */     if (this.file == null) {
/* 21 */       this.thumbnail = null;
/* 22 */       return;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 28 */     ImageIcon tmpIcon = new ImageIcon(this.file.getPath());
/* 29 */     if (tmpIcon != null) {
/* 30 */       if (tmpIcon.getIconWidth() > 90)
/*    */       {
/* 32 */         this.thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(90, -1, 1));
/*    */       }
/*    */       else {
/* 35 */         this.thumbnail = tmpIcon;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void propertyChange(PropertyChangeEvent e) {
/* 41 */     boolean update = false;
/* 42 */     String prop = e.getPropertyName();
/*    */     
/*    */ 
/* 45 */     if ("directoryChanged".equals(prop)) {
/* 46 */       this.file = null;
/* 47 */       update = true;
/*    */ 
/*    */     }
/* 50 */     else if ("SelectedFileChangedProperty".equals(prop)) {
/* 51 */       this.file = ((File)e.getNewValue());
/* 52 */       update = true;
/*    */     }
/*    */     
/*    */ 
/* 56 */     if (update) {
/* 57 */       this.thumbnail = null;
/* 58 */       if (isShowing()) {
/* 59 */         loadImage();
/* 60 */         repaint();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   protected void paintComponent(Graphics g)
/*    */   {
/* 67 */     if (this.thumbnail == null) {
/* 68 */       loadImage();
/*    */     }
/* 70 */     if (this.thumbnail != null) {
/* 71 */       int x = getWidth() / 2 - this.thumbnail.getIconWidth() / 2;
/* 72 */       int y = getHeight() / 2 - this.thumbnail.getIconHeight() / 2;
/*    */       
/* 74 */       if (y < 0) {
/* 75 */         y = 0;
/*    */       }
/*    */       
/* 78 */       if (x < 5) {
/* 79 */         x = 5;
/*    */       }
/* 81 */       this.thumbnail.paintIcon(this, g, x, y);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\ImagePreview.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */