/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Mech;
/*     */ import components.MechArmor;
/*     */ import filehandlers.Media;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
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
/*     */ public class dlgArmorTonnage
/*     */   extends JDialog
/*     */ {
/*  36 */   private boolean NewTonnage = false;
/*     */   private double result;
/*     */   private JButton btnCancel;
/*     */   private JButton btnOkay;
/*     */   
/*  41 */   public dlgArmorTonnage(Frame parent, boolean modal) { super(parent, modal);
/*  42 */     initComponents();
/*  43 */     setResizable(false);
/*  44 */     setTitle("Set Armor Tonnage");
/*  45 */     setDefaultCloseOperation(1);
/*  46 */     double max = ((ifMechForm)parent).GetMech().GetArmor().GetMaxTonnage();
/*  47 */     this.lblMaxArmor.setText("Max Armor Tonnage: " + max);
/*     */   }
/*     */   
/*     */   public double GetResult() {
/*  51 */     return this.result;
/*     */   }
/*     */   
/*     */   public boolean NewTonnage() {
/*  55 */     return this.NewTonnage;
/*     */   }
/*     */   
/*     */ 
/*     */   private JLabel lblInfo;
/*     */   
/*     */   private JLabel lblMaxArmor;
/*     */   
/*     */   private JTextField txtArmorTons;
/*     */   
/*     */   private void initComponents()
/*     */   {
/*  67 */     this.lblInfo = new JLabel();
/*  68 */     this.txtArmorTons = new JTextField();
/*  69 */     this.btnOkay = new JButton();
/*  70 */     this.btnCancel = new JButton();
/*  71 */     this.lblMaxArmor = new JLabel();
/*     */     
/*  73 */     setDefaultCloseOperation(2);
/*  74 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/*  76 */     this.lblInfo.setText("Enter desired armor tonnage:");
/*  77 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  78 */     gridBagConstraints.gridx = 0;
/*  79 */     gridBagConstraints.gridy = 0;
/*  80 */     gridBagConstraints.gridwidth = 2;
/*  81 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/*  82 */     getContentPane().add(this.lblInfo, gridBagConstraints);
/*     */     
/*  84 */     this.txtArmorTons.setHorizontalAlignment(0);
/*  85 */     this.txtArmorTons.setMaximumSize(new Dimension(90, 20));
/*  86 */     this.txtArmorTons.setMinimumSize(new Dimension(90, 20));
/*  87 */     this.txtArmorTons.setPreferredSize(new Dimension(90, 20));
/*  88 */     gridBagConstraints = new GridBagConstraints();
/*  89 */     gridBagConstraints.gridx = 0;
/*  90 */     gridBagConstraints.gridy = 1;
/*  91 */     gridBagConstraints.gridwidth = 2;
/*  92 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/*  93 */     getContentPane().add(this.txtArmorTons, gridBagConstraints);
/*  94 */     this.txtArmorTons.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*  97 */         dlgArmorTonnage.this.btnOkayActionPerformed(e);
/*     */       }
/*     */       
/* 100 */     });
/* 101 */     this.btnOkay.setText("Okay");
/* 102 */     this.btnOkay.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 104 */         dlgArmorTonnage.this.btnOkayActionPerformed(evt);
/*     */       }
/* 106 */     });
/* 107 */     gridBagConstraints = new GridBagConstraints();
/* 108 */     gridBagConstraints.gridx = 0;
/* 109 */     gridBagConstraints.gridy = 3;
/* 110 */     gridBagConstraints.fill = 2;
/* 111 */     gridBagConstraints.anchor = 17;
/* 112 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 113 */     getContentPane().add(this.btnOkay, gridBagConstraints);
/*     */     
/* 115 */     this.btnCancel.setText("Cancel");
/* 116 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 118 */         dlgArmorTonnage.this.btnCancelActionPerformed(evt);
/*     */       }
/* 120 */     });
/* 121 */     gridBagConstraints = new GridBagConstraints();
/* 122 */     gridBagConstraints.gridx = 1;
/* 123 */     gridBagConstraints.gridy = 3;
/* 124 */     gridBagConstraints.fill = 2;
/* 125 */     gridBagConstraints.anchor = 13;
/* 126 */     gridBagConstraints.insets = new Insets(4, 4, 4, 0);
/* 127 */     getContentPane().add(this.btnCancel, gridBagConstraints);
/*     */     
/* 129 */     this.lblMaxArmor.setText("Max Armor Tonnage: 100.00");
/* 130 */     gridBagConstraints = new GridBagConstraints();
/* 131 */     gridBagConstraints.gridx = 0;
/* 132 */     gridBagConstraints.gridy = 2;
/* 133 */     gridBagConstraints.gridwidth = 2;
/* 134 */     gridBagConstraints.fill = 2;
/* 135 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/* 136 */     getContentPane().add(this.lblMaxArmor, gridBagConstraints);
/*     */     
/* 138 */     pack();
/*     */   }
/*     */   
/*     */   private void btnOkayActionPerformed(ActionEvent evt)
/*     */   {
/*     */     try {
/* 144 */       this.result = Double.parseDouble(this.txtArmorTons.getText());
/*     */     } catch (NumberFormatException n) {
/* 146 */       Media.Messager(this, "Please enter a valid number");
/* 147 */       this.txtArmorTons.setText("");
/* 148 */       this.NewTonnage = false;
/* 149 */       return;
/*     */     }
/*     */     
/*     */ 
/* 153 */     this.NewTonnage = true;
/* 154 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnCancelActionPerformed(ActionEvent evt) {
/* 158 */     this.NewTonnage = false;
/* 159 */     setVisible(false);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgArmorTonnage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */