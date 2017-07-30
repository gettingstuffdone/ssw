/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Mech;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextPane;
/*     */ import utilities.CostBVBreakdown;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgCostBVBreakdown
/*     */   extends JDialog
/*     */ {
/*     */   private Mech CurMech;
/*     */   private String NL;
/*     */   private JButton btnClose;
/*     */   private JPanel jPanel1;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JTextPane txtCostBV;
/*     */   
/*     */   public dlgCostBVBreakdown(Frame parent, boolean modal, Mech m)
/*     */   {
/*  41 */     super(parent, modal);
/*  42 */     initComponents();
/*  43 */     this.CurMech = m;
/*  44 */     this.NL = System.getProperty("line.separator");
/*  45 */     CostBVBreakdown Breakdown = new CostBVBreakdown(m);
/*  46 */     this.txtCostBV.setText(Breakdown.Render());
/*  47 */     this.txtCostBV.setCaretPosition(0);
/*  48 */     setTitle(this.CurMech.GetName() + " Cost/BV Breakdown");
/*     */   }
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
/*  60 */     this.jPanel1 = new JPanel();
/*  61 */     this.btnClose = new JButton();
/*  62 */     this.jScrollPane1 = new JScrollPane();
/*  63 */     this.txtCostBV = new JTextPane();
/*     */     
/*  65 */     setDefaultCloseOperation(2);
/*     */     
/*  67 */     this.btnClose.setText("Close");
/*  68 */     this.btnClose.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  70 */         dlgCostBVBreakdown.this.btnCloseActionPerformed(evt);
/*     */       }
/*  72 */     });
/*  73 */     this.jPanel1.add(this.btnClose);
/*     */     
/*  75 */     this.jScrollPane1.setHorizontalScrollBarPolicy(31);
/*  76 */     this.jScrollPane1.setVerticalScrollBarPolicy(22);
/*  77 */     this.jScrollPane1.setMaximumSize(new Dimension(600, 300));
/*  78 */     this.jScrollPane1.setMinimumSize(new Dimension(600, 300));
/*  79 */     this.jScrollPane1.setPreferredSize(new Dimension(600, 300));
/*     */     
/*  81 */     this.txtCostBV.setFont(new Font("Lucida Sans Typewriter", 0, 12));
/*  82 */     this.txtCostBV.setText("################################################################################");
/*  83 */     this.txtCostBV.setMaximumSize(new Dimension(575, 100000));
/*  84 */     this.txtCostBV.setMinimumSize(new Dimension(575, 300));
/*  85 */     this.txtCostBV.setPreferredSize(new Dimension(575, 300));
/*  86 */     this.jScrollPane1.setViewportView(this.txtCostBV);
/*     */     
/*  88 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  89 */     getContentPane().setLayout(layout);
/*  90 */     layout.setHorizontalGroup(layout
/*  91 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  92 */       .addGroup(layout.createSequentialGroup()
/*  93 */       .addGap(8, 8, 8)
/*  94 */       .addComponent(this.jScrollPane1, -1, -1, 32767)
/*  95 */       .addGap(9, 9, 9))
/*  96 */       .addGroup(layout.createSequentialGroup()
/*  97 */       .addGap(539, 539, 539)
/*  98 */       .addComponent(this.jPanel1, -2, -1, -2)));
/*     */     
/* 100 */     layout.setVerticalGroup(layout
/* 101 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 102 */       .addGroup(layout.createSequentialGroup()
/* 103 */       .addGap(9, 9, 9)
/* 104 */       .addComponent(this.jScrollPane1, -1, -1, 32767)
/* 105 */       .addComponent(this.jPanel1, -2, -1, -2)
/* 106 */       .addGap(11, 11, 11)));
/*     */     
/*     */ 
/* 109 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCloseActionPerformed(ActionEvent evt) {
/* 113 */     dispose();
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgCostBVBreakdown.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */