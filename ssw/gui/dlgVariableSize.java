/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Equipment;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.text.ParseException;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JSpinner.DefaultEditor;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgVariableSize
/*     */   extends JDialog
/*     */ {
/*     */   private Equipment CurEquip;
/*     */   private double CurTons;
/*  38 */   private boolean result = true;
/*     */   private JButton btnCancel;
/*     */   
/*     */   public dlgVariableSize(Frame parent, boolean modal, Equipment e) {
/*  42 */     super(parent, modal);
/*  43 */     initComponents();
/*  44 */     this.CurEquip = e;
/*  45 */     this.CurTons = this.CurEquip.GetTonnage();
/*  46 */     SetState();
/*     */   }
/*     */   
/*     */   private void SetState() {
/*  50 */     this.spnTonnage.setModel(new SpinnerNumberModel(this.CurTons, this.CurEquip
/*  51 */       .GetMinTons(), this.CurEquip.GetMaxTons(), this.CurEquip.GetVariableIncrement()));
/*  52 */     setTitle("Setting tonnage for " + this.CurEquip);
/*     */   }
/*     */   
/*     */   public boolean GetResult() {
/*  56 */     return this.result;
/*     */   }
/*     */   
/*     */ 
/*     */   private JButton btnOkay;
/*     */   
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JSpinner spnTonnage;
/*     */   private void initComponents()
/*     */   {
/*  69 */     this.jPanel1 = new JPanel();
/*  70 */     this.btnOkay = new JButton();
/*  71 */     this.btnCancel = new JButton();
/*  72 */     this.jPanel2 = new JPanel();
/*  73 */     this.jLabel2 = new JLabel();
/*  74 */     this.spnTonnage = new JSpinner();
/*  75 */     this.jLabel1 = new JLabel();
/*     */     
/*  77 */     setDefaultCloseOperation(2);
/*  78 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/*  80 */     this.btnOkay.setText("Okay");
/*  81 */     this.btnOkay.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  83 */         dlgVariableSize.this.btnOkayActionPerformed(evt);
/*     */       }
/*  85 */     });
/*  86 */     this.jPanel1.add(this.btnOkay);
/*     */     
/*  88 */     this.btnCancel.setText("Cancel");
/*  89 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  91 */         dlgVariableSize.this.btnCancelActionPerformed(evt);
/*     */       }
/*  93 */     });
/*  94 */     this.jPanel1.add(this.btnCancel);
/*     */     
/*  96 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  97 */     gridBagConstraints.gridx = 0;
/*  98 */     gridBagConstraints.gridy = 2;
/*  99 */     getContentPane().add(this.jPanel1, gridBagConstraints);
/*     */     
/* 101 */     this.jLabel2.setText("Tonnage:");
/* 102 */     this.jPanel2.add(this.jLabel2);
/*     */     
/* 104 */     this.spnTonnage.setMinimumSize(new Dimension(75, 20));
/* 105 */     this.spnTonnage.setPreferredSize(new Dimension(75, 20));
/* 106 */     this.spnTonnage.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 108 */         dlgVariableSize.this.spnTonnageStateChanged(evt);
/*     */       }
/* 110 */     });
/* 111 */     this.jPanel2.add(this.spnTonnage);
/*     */     
/* 113 */     gridBagConstraints = new GridBagConstraints();
/* 114 */     gridBagConstraints.gridx = 0;
/* 115 */     gridBagConstraints.gridy = 1;
/* 116 */     gridBagConstraints.fill = 2;
/* 117 */     getContentPane().add(this.jPanel2, gridBagConstraints);
/*     */     
/* 119 */     this.jLabel1.setText("Choose a tonnage:");
/* 120 */     gridBagConstraints = new GridBagConstraints();
/* 121 */     gridBagConstraints.fill = 2;
/* 122 */     gridBagConstraints.anchor = 17;
/* 123 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 124 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/* 126 */     pack();
/*     */   }
/*     */   
/*     */   private void spnTonnageStateChanged(ChangeEvent evt) {
/* 130 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnTonnage.getModel();
/* 131 */     JComponent editor = this.spnTonnage.getEditor();
/* 132 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 136 */       this.spnTonnage.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 140 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 141 */         tf.setValue(this.spnTonnage.getValue());
/*     */       }
/* 143 */       return;
/*     */     }
/*     */     
/* 146 */     this.CurTons = n.getNumber().doubleValue();
/*     */   }
/*     */   
/*     */   private void btnOkayActionPerformed(ActionEvent evt)
/*     */   {
/* 151 */     double value = Math.ceil(this.CurTons / this.CurEquip.GetVariableIncrement()) * this.CurEquip.GetVariableIncrement();
/* 152 */     if (value > this.CurEquip.GetMaxTons()) value = this.CurEquip.GetMaxTons();
/* 153 */     if (value < this.CurEquip.GetMinTons()) value = this.CurEquip.GetMinTons();
/* 154 */     this.CurEquip.SetTonnage(value);
/* 155 */     this.result = true;
/* 156 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnCancelActionPerformed(ActionEvent evt) {
/* 160 */     this.result = false;
/* 161 */     setVisible(false);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgVariableSize.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */