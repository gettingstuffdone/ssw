/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Mech;
/*     */ import filehandlers.TXTWriter;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextPane;
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
/*     */ public class dlgTextExport
/*     */   extends JDialog
/*     */ {
/*     */   private JButton btnClose;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JTextPane txtTextExport;
/*     */   
/*     */   public dlgTextExport(Frame parent, boolean modal, Mech m)
/*     */   {
/*  38 */     super(parent, modal);
/*  39 */     initComponents();
/*  40 */     TXTWriter t = new TXTWriter(m);
/*  41 */     this.txtTextExport.setText(t.GetTextExport());
/*  42 */     this.txtTextExport.setCaretPosition(0);
/*  43 */     setTitle(m.GetName());
/*     */   }
/*     */   
/*     */   public dlgTextExport(Frame parent, boolean modal, String msg) {
/*  47 */     super(parent, modal);
/*  48 */     initComponents();
/*  49 */     this.txtTextExport.setText(msg);
/*  50 */     this.txtTextExport.setCaretPosition(0);
/*  51 */     setTitle("Batch Resave Error List");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  64 */     this.jScrollPane1 = new JScrollPane();
/*  65 */     this.txtTextExport = new JTextPane();
/*  66 */     this.btnClose = new JButton();
/*     */     
/*  68 */     setDefaultCloseOperation(2);
/*  69 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/*  71 */     this.jScrollPane1.setHorizontalScrollBarPolicy(31);
/*  72 */     this.jScrollPane1.setVerticalScrollBarPolicy(22);
/*  73 */     this.jScrollPane1.setMaximumSize(new Dimension(600, 300));
/*  74 */     this.jScrollPane1.setMinimumSize(new Dimension(600, 300));
/*  75 */     this.jScrollPane1.setPreferredSize(new Dimension(600, 300));
/*     */     
/*  77 */     this.txtTextExport.setFont(new Font("Lucida Sans Typewriter", 0, 12));
/*  78 */     this.txtTextExport.setText("################################################################################");
/*  79 */     this.txtTextExport.setMaximumSize(new Dimension(575, 200000));
/*  80 */     this.txtTextExport.setMinimumSize(new Dimension(575, 300));
/*  81 */     this.txtTextExport.setPreferredSize(new Dimension(575, 300));
/*  82 */     this.jScrollPane1.setViewportView(this.txtTextExport);
/*     */     
/*  84 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  85 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/*  86 */     getContentPane().add(this.jScrollPane1, gridBagConstraints);
/*     */     
/*  88 */     this.btnClose.setText("Close");
/*  89 */     this.btnClose.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  91 */         dlgTextExport.this.btnCloseActionPerformed(evt);
/*     */       }
/*  93 */     });
/*  94 */     gridBagConstraints = new GridBagConstraints();
/*  95 */     gridBagConstraints.gridx = 0;
/*  96 */     gridBagConstraints.gridy = 1;
/*  97 */     gridBagConstraints.anchor = 13;
/*  98 */     gridBagConstraints.insets = new Insets(4, 0, 4, 4);
/*  99 */     getContentPane().add(this.btnClose, gridBagConstraints);
/*     */     
/* 101 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCloseActionPerformed(ActionEvent evt) {
/* 105 */     dispose();
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgTextExport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */