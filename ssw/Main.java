/*     */ package ssw;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import ssw.gui.frmMain;
/*     */ import ssw.gui.frmMainWide;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */ {
/*     */   public static void main(String[] args)
/*     */   {
/*  49 */     Preferences prefs = Preferences.userRoot().node("/com/sswsuite/ssw");
/*  50 */     prefs.remove("FileToOpen");
/*  51 */     int screensize = prefs.getInt("SSWScreenSize", 0);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  57 */     if (args.length >= 1) {
/*  58 */       prefs.put("FileToOpen", args[0].toString());
/*     */     }
/*     */     
/*  61 */     Runtime runtime = Runtime.getRuntime();
/*  62 */     System.out.println("Memory Allocated [" + runtime.maxMemory() / 1000L + "]");
/*     */     
/*     */ 
/*  65 */     if (runtime.maxMemory() < 256000000L) {
/*     */       try {
/*  67 */         String[] call = { "java", "-Xmx256m", "-jar", "SSW.jar" };
/*  68 */         runtime.exec(call);
/*  69 */         System.exit(0);
/*     */       } catch (Exception e) {
/*  71 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  76 */     SetupLogFile("Logs/SSW_Log.txt");
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  81 */       if (!UIManager.getSystemLookAndFeelClassName().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")) {
/*  82 */         UIManager.put("swing.boldMetal", Boolean.FALSE);
/*  83 */         UIDefaults uiDefaults = UIManager.getDefaults();
/*  84 */         Font f = uiDefaults.getFont("Label.font");
/*  85 */         uiDefaults.put("Label.font", f.deriveFont(f.getStyle(), 10.0F));
/*  86 */         f = uiDefaults.getFont("ComboBox.font");
/*  87 */         uiDefaults.put("ComboBox.font", f.deriveFont(f.getStyle(), 10.0F));
/*  88 */         f = uiDefaults.getFont("Button.font");
/*  89 */         uiDefaults.put("Button.font", f.deriveFont(f.getStyle(), 10.0F));
/*  90 */         f = uiDefaults.getFont("CheckBox.font");
/*  91 */         uiDefaults.put("CheckBox.font", f.deriveFont(f.getStyle(), 10.0F));
/*  92 */         UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
/*     */       } else {
/*  94 */         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */       }
/*     */     } catch (UnsupportedLookAndFeelException e) {
/*  97 */       e.printStackTrace();
/*  98 */       System.err.flush();
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/* 101 */       e.printStackTrace();
/* 102 */       System.err.flush();
/*     */     }
/*     */     catch (InstantiationException e) {
/* 105 */       e.printStackTrace();
/* 106 */       System.err.flush();
/*     */     }
/*     */     catch (IllegalAccessException e) {
/* 109 */       e.printStackTrace();
/* 110 */       System.err.flush();
/*     */     }
/*     */     
/* 113 */     EventQueue.invokeLater(new Runnable() {
/*     */       public void run() {
/*     */         JFrame MainFrame;
/* 116 */         switch (this.val$screensize) {
/*     */         case 1: 
/* 118 */           JFrame MainFrame = new frmMainWide();
/* 119 */           MainFrame.setSize(1240, 610);
/* 120 */           break;
/*     */         default: 
/* 122 */           MainFrame = new frmMain();
/* 123 */           MainFrame.setSize(750, 610);
/*     */         }
/*     */         
/*     */         
/* 127 */         MainFrame.setLocationRelativeTo(null);
/* 128 */         MainFrame.setResizable(true);
/* 129 */         MainFrame.setDefaultCloseOperation(2);
/* 130 */         MainFrame.setVisible(true);
/*     */       }
/*     */       
/* 133 */     });
/* 134 */     System.out.flush();
/* 135 */     System.err.flush();
/*     */   }
/*     */   
/*     */   private static void SetupLogFile(String LogFile)
/*     */   {
/*     */     try
/*     */     {
/* 142 */       PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(LogFile), 64));
/* 143 */       System.setOut(ps);
/* 144 */       System.setErr(ps);
/* 145 */       System.out.println("Log File open for business...");
/*     */     } catch (Exception e) {
/* 147 */       System.err.println("Unable to send output to " + LogFile);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\Main.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */