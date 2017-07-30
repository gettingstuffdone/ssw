/*     */ package ssw.printpreview;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Pageable;
/*     */ import java.awt.print.Printable;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.JToolBar.Separator;
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
/*     */ public class PreviewDialog
/*     */   extends JDialog
/*     */   implements ActionListener
/*     */ {
/*     */   private static final double DEFAULT_ZOOM_FACTOR_STEP = 0.5D;
/*     */   protected Pageable pageable;
/*     */   
/*     */   public PreviewDialog(String title, JFrame owner, Pageable pageable, double zoom)
/*     */   {
/*  50 */     super(owner, title, true);
/*  51 */     this.pageable = pageable;
/*  52 */     Preview preview = new Preview(pageable, zoom);
/*  53 */     JScrollPane scrollPane = new JScrollPane(preview);
/*  54 */     getContentPane().add(scrollPane, "Center");
/*  55 */     JToolBar toolbar = new JToolBar();
/*     */     
/*     */ 
/*  58 */     getContentPane().add(toolbar, "North");
/*  59 */     toolbar.add(getButton("Back", "Back24.gif", new BrowseAction(preview, -1)));
/*  60 */     toolbar.add(getButton("Forward", "Forward24.gif", new BrowseAction(preview, 1)));
/*  61 */     toolbar.add(new JToolBar.Separator());
/*  62 */     toolbar.add(getButton("Zoom +", "ZoomIn24.gif", new ZoomAction(preview, 0.5D)));
/*  63 */     toolbar.add(getButton("Zoom -", "ZoomOut24.gif", new ZoomAction(preview, -0.5D)));
/*  64 */     toolbar.add(new JToolBar.Separator());
/*  65 */     JPanel dialog = new JPanel();
/*  66 */     dialog.setLayout(new FlowLayout(2));
/*  67 */     JButton ok = new JButton("OK");
/*  68 */     ok.addActionListener(this);
/*  69 */     dialog.add(ok);
/*  70 */     getContentPane().add(dialog, "South");
/*     */   }
/*     */   
/*     */   public PreviewDialog(String title, JFrame owner, Pageable pageable) {
/*  74 */     this(title, owner, pageable, 0.0D);
/*     */   }
/*     */   
/*     */   public PreviewDialog(String title, JFrame owner, Printable printable, PageFormat format, int pages, double zoom) {
/*  78 */     this(title, owner, new MyPageable(printable, format, pages), zoom);
/*     */   }
/*     */   
/*     */ 
/*  82 */   public PreviewDialog(String title, JFrame owner, Printable printable, PageFormat format, int pages) { this(title, owner, printable, format, pages, 0.0D); }
/*     */   
/*     */   private static class MyPageable implements Pageable {
/*     */     private Printable printable;
/*     */     
/*  87 */     public MyPageable(Printable printable, PageFormat format, int pages) { this.printable = printable;
/*  88 */       this.format = format;
/*  89 */       this.pages = pages;
/*     */     }
/*     */     
/*     */     public int getNumberOfPages() {
/*  93 */       return this.pages;
/*     */     }
/*     */     
/*     */     public Printable getPrintable(int index) {
/*  97 */       if (index >= this.pages) throw new IndexOutOfBoundsException();
/*  98 */       return this.printable;
/*     */     }
/*     */     
/*     */     public PageFormat getPageFormat(int index) {
/* 102 */       if (index >= this.pages) throw new IndexOutOfBoundsException();
/* 103 */       return this.format;
/*     */     }
/*     */     
/*     */     private PageFormat format;
/*     */     private int pages;
/*     */   }
/*     */   
/*     */   private JButton getButton(String iconName)
/*     */   {
/* 112 */     return getButton(null, iconName, null);
/*     */   }
/*     */   
/*     */   private JButton getButton(String iconName, AbstractAction action) {
/* 116 */     return getButton(null, iconName, action);
/*     */   }
/*     */   
/*     */   private JButton getButton(String name, String iconName, AbstractAction action) {
/* 120 */     JButton result = null;
/*     */     
/* 122 */     ImageIcon icon = null;
/* 123 */     icon = new ImageIcon(getClass().getResource("/ssw/Images/" + iconName));
/*     */     
/* 125 */     if (action != null) {
/* 126 */       if (icon != null) action.putValue("SmallIcon", icon);
/* 127 */       if (name != null) action.putValue("Name", name);
/* 128 */       result = new JButton(action);
/*     */     } else {
/* 130 */       result = new JButton(name, icon);
/*     */     }
/* 132 */     return result;
/*     */   }
/*     */   
/*     */   private void Refresh() {
/* 136 */     repaint();
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/* 140 */     dispose();
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\printpreview\PreviewDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */