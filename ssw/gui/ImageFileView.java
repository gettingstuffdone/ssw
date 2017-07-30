/*    */ package ssw.gui;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ public class ImageFileView extends javax.swing.filechooser.FileView
/*    */ {
/*  8 */   ImageIcon jpgIcon = Utils.createImageIcon("../Images/jpgIcon.gif");
/*  9 */   ImageIcon gifIcon = Utils.createImageIcon("../Images/gifIcon.gif");
/* 10 */   ImageIcon tiffIcon = Utils.createImageIcon("../Images/tiffIcon.gif");
/* 11 */   ImageIcon pngIcon = Utils.createImageIcon("../Images/pngIcon.png");
/*    */   
/*    */   public String getName(File f)
/*    */   {
/* 15 */     return null;
/*    */   }
/*    */   
/*    */   public String getDescription(File f)
/*    */   {
/* 20 */     return null;
/*    */   }
/*    */   
/*    */   public Boolean isTraversable(File f)
/*    */   {
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public String getTypeDescription(File f)
/*    */   {
/* 30 */     String extension = Utils.getExtension(f);
/* 31 */     String type = null;
/*    */     
/* 33 */     if (extension != null) {
/* 34 */       if ((extension.equals("jpeg")) || 
/* 35 */         (extension.equals("jpg"))) {
/* 36 */         type = "JPEG Image";
/* 37 */       } else if (extension.equals("gif")) {
/* 38 */         type = "GIF Image";
/* 39 */       } else if ((extension.equals("tiff")) || 
/* 40 */         (extension.equals("tif"))) {
/* 41 */         type = "TIFF Image";
/* 42 */       } else if (extension.equals("png")) {
/* 43 */         type = "PNG Image";
/*    */       }
/*    */     }
/* 46 */     return type;
/*    */   }
/*    */   
/*    */   public javax.swing.Icon getIcon(File f)
/*    */   {
/* 51 */     String extension = Utils.getExtension(f);
/* 52 */     javax.swing.Icon icon = null;
/*    */     
/* 54 */     if (extension != null) {
/* 55 */       if ((extension.equals("jpeg")) || 
/* 56 */         (extension.equals("jpg"))) {
/* 57 */         icon = this.jpgIcon;
/* 58 */       } else if (extension.equals("gif")) {
/* 59 */         icon = this.gifIcon;
/* 60 */       } else if ((extension.equals("tiff")) || 
/* 61 */         (extension.equals("tif"))) {
/* 62 */         icon = this.tiffIcon;
/* 63 */       } else if (extension.equals("png")) {
/* 64 */         icon = this.pngIcon;
/*    */       }
/*    */     }
/* 67 */     return icon;
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\ImageFileView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */