/*     */ package ssw.gui;
/*     */ 
/*     */ import battleforce.BattleForce;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Vector;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ 
/*     */ public class dlgPrintBattleforce extends JDialog
/*     */ {
/*     */   private BattleForce force;
/*  24 */   public boolean Result = false;
/*  25 */   public String Sheet = "Inner Sphere";
/*     */   private JRadioButton btnCL;
/*     */   private JRadioButton btnCS;
/*     */   
/*  29 */   public dlgPrintBattleforce(Frame parent, boolean modal, BattleForce force) { super(parent, modal);
/*  30 */     initComponents();
/*     */     
/*  32 */     this.force = force;
/*  33 */     this.lblForce.setText(this.lblForce.getText().replace("{Force}", force.ForceName).replace("{Units}", force.BattleForceStats.size() + ""));
/*  34 */     setTitle(force.ForceName);
/*     */     
/*  36 */     this.btnIS.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  38 */         dlgPrintBattleforce.this.Sheet = "Inner Sphere";
/*     */       }
/*     */       
/*  41 */     });
/*  42 */     this.btnCL.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  44 */         dlgPrintBattleforce.this.Sheet = "Clan";
/*     */       }
/*     */       
/*  47 */     });
/*  48 */     this.btnCS.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  50 */         dlgPrintBattleforce.this.Sheet = "Comstar";
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private JButton btnCancel;
/*     */   private ButtonGroup btnGrpUnitSize;
/*     */   private JRadioButton btnIS;
/*     */   private JButton btnPrint;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JLabel lblForce;
/*     */   private void initComponents()
/*     */   {
/*  64 */     this.btnGrpUnitSize = new ButtonGroup();
/*  65 */     this.jPanel1 = new JPanel();
/*  66 */     this.btnIS = new JRadioButton();
/*  67 */     this.btnCS = new JRadioButton();
/*  68 */     this.btnCL = new JRadioButton();
/*  69 */     this.btnPrint = new JButton();
/*  70 */     this.btnCancel = new JButton();
/*  71 */     this.jPanel2 = new JPanel();
/*  72 */     this.lblForce = new JLabel();
/*     */     
/*  74 */     setDefaultCloseOperation(2);
/*  75 */     setTitle("BattleForce Print Options");
/*     */     
/*  77 */     this.jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sheet Type"));
/*     */     
/*  79 */     this.btnGrpUnitSize.add(this.btnIS);
/*  80 */     this.btnIS.setText("Inner Sphere Lance (4)");
/*     */     
/*  82 */     this.btnGrpUnitSize.add(this.btnCS);
/*  83 */     this.btnCS.setText("Comstar/Word of Blake Level II (6)");
/*     */     
/*  85 */     this.btnGrpUnitSize.add(this.btnCL);
/*  86 */     this.btnCL.setText("Clan Star (5)");
/*     */     
/*  88 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/*  89 */     this.jPanel1.setLayout(jPanel1Layout);
/*  90 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/*  91 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  92 */       .addGroup(jPanel1Layout.createSequentialGroup()
/*  93 */       .addContainerGap()
/*  94 */       .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  95 */       .addComponent(this.btnCS)
/*  96 */       .addComponent(this.btnCL)
/*  97 */       .addComponent(this.btnIS))
/*  98 */       .addContainerGap(103, 32767)));
/*     */     
/* 100 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 101 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 102 */       .addGroup(jPanel1Layout.createSequentialGroup()
/* 103 */       .addComponent(this.btnIS)
/* 104 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 105 */       .addComponent(this.btnCL)
/* 106 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 107 */       .addComponent(this.btnCS)));
/*     */     
/*     */ 
/* 110 */     this.btnPrint.setText("Print");
/* 111 */     this.btnPrint.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 113 */         dlgPrintBattleforce.this.btnPrintActionPerformed(evt);
/*     */       }
/*     */       
/* 116 */     });
/* 117 */     this.btnCancel.setText("Cancel");
/* 118 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 120 */         dlgPrintBattleforce.this.btnCancelActionPerformed(evt);
/*     */       }
/*     */       
/* 123 */     });
/* 124 */     this.lblForce.setText("{Force} {Units} Units");
/*     */     
/* 126 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 127 */     this.jPanel2.setLayout(jPanel2Layout);
/* 128 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/* 129 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 130 */       .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
/* 131 */       .addContainerGap()
/* 132 */       .addComponent(this.lblForce, -1, 314, 32767)
/* 133 */       .addContainerGap()));
/*     */     
/* 135 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/* 136 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 137 */       .addGroup(jPanel2Layout.createSequentialGroup()
/* 138 */       .addContainerGap()
/* 139 */       .addComponent(this.lblForce)
/* 140 */       .addContainerGap(-1, 32767)));
/*     */     
/*     */ 
/* 143 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 144 */     getContentPane().setLayout(layout);
/* 145 */     layout.setHorizontalGroup(layout
/* 146 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 147 */       .addComponent(this.jPanel2, -1, -1, 32767)
/* 148 */       .addGroup(layout.createSequentialGroup()
/* 149 */       .addContainerGap()
/* 150 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 151 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 152 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 188, -2)
/* 153 */       .addComponent(this.btnPrint)
/* 154 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 155 */       .addComponent(this.btnCancel))
/* 156 */       .addComponent(this.jPanel1, GroupLayout.Alignment.TRAILING, -1, -1, 32767))
/* 157 */       .addContainerGap()));
/*     */     
/* 159 */     layout.setVerticalGroup(layout
/* 160 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 161 */       .addGroup(layout.createSequentialGroup()
/* 162 */       .addComponent(this.jPanel2, -2, -1, -2)
/* 163 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 164 */       .addComponent(this.jPanel1, -2, -1, -2)
/* 165 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 166 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 167 */       .addComponent(this.btnPrint)
/* 168 */       .addComponent(this.btnCancel))
/* 169 */       .addContainerGap(-1, 32767)));
/*     */     
/*     */ 
/* 172 */     pack();
/*     */   }
/*     */   
/*     */   private void btnPrintActionPerformed(ActionEvent evt) {
/* 176 */     this.Result = true;
/* 177 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnCancelActionPerformed(ActionEvent evt) {
/* 181 */     setVisible(false);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgPrintBattleforce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */