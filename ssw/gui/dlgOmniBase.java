/*     */ package ssw.gui;
/*     */ 
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
/*     */ import javax.swing.JPanel;
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
/*     */ public class dlgOmniBase
/*     */   extends JDialog
/*     */ {
/*  34 */   String input = "";
/*  35 */   boolean cancel = false;
/*     */   private JButton Okay;
/*     */   
/*     */   public dlgOmniBase(Frame parent, boolean modal) {
/*  39 */     super(parent, modal);
/*  40 */     initComponents();
/*     */   }
/*     */   
/*     */   public String GetInput() {
/*  44 */     return this.input;
/*     */   }
/*     */   
/*     */   public boolean WasCanceled() {
/*  48 */     return this.cancel;
/*     */   }
/*     */   
/*     */ 
/*     */   private JButton btnCancel;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JPanel jPanel1;
/*     */   private JTextField txtLoadoutName;
/*     */   private void initComponents()
/*     */   {
/*  60 */     this.jLabel1 = new JLabel();
/*  61 */     this.jLabel2 = new JLabel();
/*  62 */     this.jLabel3 = new JLabel();
/*  63 */     this.txtLoadoutName = new JTextField();
/*  64 */     this.jPanel1 = new JPanel();
/*  65 */     this.Okay = new JButton();
/*  66 */     this.btnCancel = new JButton();
/*     */     
/*  68 */     setDefaultCloseOperation(2);
/*  69 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/*  71 */     this.jLabel1.setText("Omnimechs may have many different loadouts and");
/*  72 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  73 */     gridBagConstraints.anchor = 17;
/*  74 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/*  76 */     this.jLabel2.setText("each requires a name to differentiate it from others.");
/*  77 */     gridBagConstraints = new GridBagConstraints();
/*  78 */     gridBagConstraints.gridx = 0;
/*  79 */     gridBagConstraints.gridy = 1;
/*  80 */     gridBagConstraints.anchor = 17;
/*  81 */     getContentPane().add(this.jLabel2, gridBagConstraints);
/*     */     
/*  83 */     this.jLabel3.setText("What is the name of this loadout?");
/*  84 */     gridBagConstraints = new GridBagConstraints();
/*  85 */     gridBagConstraints.gridx = 0;
/*  86 */     gridBagConstraints.gridy = 2;
/*  87 */     gridBagConstraints.anchor = 17;
/*  88 */     gridBagConstraints.insets = new Insets(3, 0, 3, 0);
/*  89 */     getContentPane().add(this.jLabel3, gridBagConstraints);
/*     */     
/*  91 */     this.txtLoadoutName.setMaximumSize(new Dimension(200, 20));
/*  92 */     this.txtLoadoutName.setMinimumSize(new Dimension(200, 20));
/*  93 */     this.txtLoadoutName.setPreferredSize(new Dimension(200, 20));
/*  94 */     gridBagConstraints = new GridBagConstraints();
/*  95 */     gridBagConstraints.gridx = 0;
/*  96 */     gridBagConstraints.gridy = 3;
/*  97 */     gridBagConstraints.anchor = 17;
/*  98 */     getContentPane().add(this.txtLoadoutName, gridBagConstraints);
/*     */     
/* 100 */     this.Okay.setText("Okay");
/* 101 */     this.Okay.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 103 */         dlgOmniBase.this.OkayActionPerformed(evt);
/*     */       }
/* 105 */     });
/* 106 */     this.jPanel1.add(this.Okay);
/*     */     
/* 108 */     this.btnCancel.setText("Cancel");
/* 109 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 111 */         dlgOmniBase.this.btnCancelActionPerformed(evt);
/*     */       }
/* 113 */     });
/* 114 */     this.jPanel1.add(this.btnCancel);
/*     */     
/* 116 */     gridBagConstraints = new GridBagConstraints();
/* 117 */     gridBagConstraints.gridx = 0;
/* 118 */     gridBagConstraints.gridy = 4;
/* 119 */     gridBagConstraints.anchor = 13;
/* 120 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 121 */     getContentPane().add(this.jPanel1, gridBagConstraints);
/*     */     
/* 123 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCancelActionPerformed(ActionEvent evt) {
/* 127 */     this.cancel = true;
/* 128 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void OkayActionPerformed(ActionEvent evt)
/*     */   {
/* 133 */     if ((this.txtLoadoutName.getText().length() <= 0) || (this.txtLoadoutName.getText() == null)) {
/* 134 */       Media.Messager(this, "Please enter a name for this variant.");
/*     */     } else {
/* 136 */       this.input = this.txtLoadoutName.getText();
/* 137 */       setVisible(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgOmniBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */