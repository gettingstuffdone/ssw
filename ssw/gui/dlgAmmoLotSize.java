/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Ammunition;
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
/*     */ public class dlgAmmoLotSize
/*     */   extends JDialog
/*     */ {
/*     */   private Ammunition Ammo;
/*     */   private int CurAmmo;
/*  38 */   private boolean result = true;
/*     */   private JButton btnCancel;
/*     */   
/*     */   public dlgAmmoLotSize(Frame parent, boolean modal, Ammunition a) {
/*  42 */     super(parent, modal);
/*  43 */     initComponents();
/*  44 */     this.Ammo = a;
/*  45 */     this.CurAmmo = this.Ammo.GetLotSize();
/*  46 */     SetState();
/*     */   }
/*     */   
/*     */   private void SetState() {
/*  50 */     this.spnTonnage.setModel(new SpinnerNumberModel(this.Ammo.GetLotSize(), 1, this.Ammo.GetMaxLotSize(), 1));
/*  51 */     setTitle("Setting ammo tonnage.");
/*     */   }
/*     */   
/*     */   public boolean GetResult() {
/*  55 */     return this.result;
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
/*  68 */     this.jPanel1 = new JPanel();
/*  69 */     this.btnOkay = new JButton();
/*  70 */     this.btnCancel = new JButton();
/*  71 */     this.jPanel2 = new JPanel();
/*  72 */     this.jLabel2 = new JLabel();
/*  73 */     this.spnTonnage = new JSpinner();
/*  74 */     this.jLabel1 = new JLabel();
/*     */     
/*  76 */     setDefaultCloseOperation(2);
/*  77 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/*  79 */     this.btnOkay.setText("Okay");
/*  80 */     this.btnOkay.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  82 */         dlgAmmoLotSize.this.btnOkayActionPerformed(evt);
/*     */       }
/*  84 */     });
/*  85 */     this.jPanel1.add(this.btnOkay);
/*     */     
/*  87 */     this.btnCancel.setText("Cancel");
/*  88 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  90 */         dlgAmmoLotSize.this.btnCancelActionPerformed(evt);
/*     */       }
/*  92 */     });
/*  93 */     this.jPanel1.add(this.btnCancel);
/*     */     
/*  95 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  96 */     gridBagConstraints.gridx = 0;
/*  97 */     gridBagConstraints.gridy = 2;
/*  98 */     getContentPane().add(this.jPanel1, gridBagConstraints);
/*     */     
/* 100 */     this.jLabel2.setText("Lot Size:");
/* 101 */     this.jPanel2.add(this.jLabel2);
/*     */     
/* 103 */     this.spnTonnage.setMinimumSize(new Dimension(75, 20));
/* 104 */     this.spnTonnage.setPreferredSize(new Dimension(75, 20));
/* 105 */     this.spnTonnage.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 107 */         dlgAmmoLotSize.this.spnTonnageStateChanged(evt);
/*     */       }
/* 109 */     });
/* 110 */     this.jPanel2.add(this.spnTonnage);
/*     */     
/* 112 */     gridBagConstraints = new GridBagConstraints();
/* 113 */     gridBagConstraints.gridx = 0;
/* 114 */     gridBagConstraints.gridy = 1;
/* 115 */     gridBagConstraints.fill = 2;
/* 116 */     getContentPane().add(this.jPanel2, gridBagConstraints);
/*     */     
/* 118 */     this.jLabel1.setText("Set Ammunition Amount:");
/* 119 */     gridBagConstraints = new GridBagConstraints();
/* 120 */     gridBagConstraints.fill = 2;
/* 121 */     gridBagConstraints.anchor = 17;
/* 122 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 123 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/* 125 */     pack();
/*     */   }
/*     */   
/*     */   private void spnTonnageStateChanged(ChangeEvent evt) {
/* 129 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnTonnage.getModel();
/* 130 */     JComponent editor = this.spnTonnage.getEditor();
/* 131 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 135 */       this.spnTonnage.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 139 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 140 */         tf.setValue(this.spnTonnage.getValue());
/*     */       }
/* 142 */       return;
/*     */     }
/*     */     
/* 145 */     this.CurAmmo = n.getNumber().intValue();
/*     */   }
/*     */   
/*     */   private void btnOkayActionPerformed(ActionEvent evt)
/*     */   {
/* 150 */     int value = this.CurAmmo;
/* 151 */     if (value > this.Ammo.GetMaxLotSize()) value = this.Ammo.GetMaxLotSize();
/* 152 */     if (value < 1) value = 1;
/* 153 */     this.Ammo.SetLotSize(this.CurAmmo);
/* 154 */     this.result = true;
/* 155 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnCancelActionPerformed(ActionEvent evt) {
/* 159 */     this.result = false;
/* 160 */     setVisible(false);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgAmmoLotSize.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */