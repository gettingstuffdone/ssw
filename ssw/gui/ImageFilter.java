/*    */ package ssw.gui;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ public class ImageFilter
/*    */   extends FileFilter
/*    */ {
/*    */   public boolean accept(File f)
/*    */   {
/* 11 */     if (f.isDirectory()) {
/* 12 */       return true;
/*    */     }
/*    */     
/* 15 */     String extension = Utils.getExtension(f);
/* 16 */     if (extension != null) {
/* 17 */       if ((extension.equals("tiff")) || 
/* 18 */         (extension.equals("tif")) || 
/* 19 */         (extension.equals("gif")) || 
/* 20 */         (extension.equals("jpeg")) || 
/* 21 */         (extension.equals("jpg")) || 
/* 22 */         (extension.equals("png"))) {
/* 23 */         return true;
/*    */       }
/* 25 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 29 */     return false;
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 34 */     return "*.gif, *.jpg, *.png, *.tif";
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\ImageFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */