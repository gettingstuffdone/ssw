/*    */ package ssw.gui;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URL;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ public class Utils
/*    */ {
/*    */   public static final String jpeg = "jpeg";
/*    */   public static final String jpg = "jpg";
/*    */   public static final String gif = "gif";
/*    */   public static final String tiff = "tiff";
/*    */   public static final String tif = "tif";
/*    */   public static final String png = "png";
/*    */   
/*    */   public static String getExtension(File f)
/*    */   {
/* 19 */     String ext = null;
/* 20 */     String s = f.getName();
/* 21 */     int i = s.lastIndexOf('.');
/*    */     
/* 23 */     if ((i > 0) && (i < s.length() - 1)) {
/* 24 */       ext = s.substring(i + 1).toLowerCase();
/*    */     }
/* 26 */     return ext;
/*    */   }
/*    */   
/*    */   public static ImageIcon createImageIcon(String path)
/*    */   {
/* 31 */     URL imgURL = Utils.class.getResource(path);
/* 32 */     if (imgURL != null) {
/* 33 */       return new ImageIcon(imgURL);
/*    */     }
/* 35 */     System.err.println("Couldn't find file: " + path);
/* 36 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\Utils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */