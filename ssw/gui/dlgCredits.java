/*     */ package ssw.gui;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
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
/*     */ public class dlgCredits
/*     */   extends JDialog
/*     */ {
/*     */   private JButton btnClose;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JLabel jLabel8;
/*     */   
/*     */   public dlgCredits(Frame parent, boolean modal)
/*     */   {
/*  37 */     super(parent, modal);
/*  38 */     initComponents();
/*  39 */     setResizable(false);
/*  40 */     setTitle("SSW Credits");
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
/*  52 */     this.btnClose = new JButton();
/*  53 */     this.jLabel1 = new JLabel();
/*  54 */     this.jLabel8 = new JLabel();
/*  55 */     this.jLabel2 = new JLabel();
/*  56 */     this.jLabel3 = new JLabel();
/*  57 */     this.jLabel4 = new JLabel();
/*  58 */     this.jLabel5 = new JLabel();
/*     */     
/*  60 */     setDefaultCloseOperation(2);
/*  61 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/*  63 */     this.btnClose.setText("Close");
/*  64 */     this.btnClose.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  66 */         dlgCredits.this.btnCloseActionPerformed(evt);
/*     */       }
/*  68 */     });
/*  69 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  70 */     gridBagConstraints.gridx = 0;
/*  71 */     gridBagConstraints.gridy = 6;
/*  72 */     gridBagConstraints.anchor = 14;
/*  73 */     gridBagConstraints.insets = new Insets(4, 0, 4, 4);
/*  74 */     getContentPane().add(this.btnClose, gridBagConstraints);
/*     */     
/*  76 */     this.jLabel1.setHorizontalAlignment(0);
/*  77 */     this.jLabel1.setText("<html>Major Tom<br />Ice Hellion<br />Lady Cain<br />Kelgarian Raptor<br />Vidar<br />DarkWarrior<br />ArcAngel<br />Stolen_Thunder</html>");
/*  78 */     gridBagConstraints = new GridBagConstraints();
/*  79 */     gridBagConstraints.gridx = 0;
/*  80 */     gridBagConstraints.gridy = 1;
/*  81 */     gridBagConstraints.anchor = 17;
/*  82 */     gridBagConstraints.insets = new Insets(0, 20, 0, 4);
/*  83 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/*  85 */     this.jLabel8.setFont(new Font("Tahoma", 1, 14));
/*  86 */     this.jLabel8.setText("The SSW Alpha Testing Team");
/*  87 */     gridBagConstraints = new GridBagConstraints();
/*  88 */     gridBagConstraints.anchor = 17;
/*  89 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/*  90 */     getContentPane().add(this.jLabel8, gridBagConstraints);
/*     */     
/*  92 */     this.jLabel2.setFont(new Font("Dialog", 1, 14));
/*  93 */     this.jLabel2.setText("Past and Current Developers");
/*  94 */     gridBagConstraints = new GridBagConstraints();
/*  95 */     gridBagConstraints.gridx = 0;
/*  96 */     gridBagConstraints.gridy = 2;
/*  97 */     gridBagConstraints.anchor = 17;
/*  98 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/*  99 */     getContentPane().add(this.jLabel2, gridBagConstraints);
/*     */     
/* 101 */     this.jLabel3.setText("<html>Justin Bengtson (LostInSpace)<br />George Blouin (Skyhigh)<br />Michael Mills (Arcangel)<br />Brian Compter (Specter83)</html>");
/* 102 */     gridBagConstraints = new GridBagConstraints();
/* 103 */     gridBagConstraints.gridx = 0;
/* 104 */     gridBagConstraints.gridy = 3;
/* 105 */     gridBagConstraints.anchor = 17;
/* 106 */     gridBagConstraints.insets = new Insets(0, 20, 0, 4);
/* 107 */     getContentPane().add(this.jLabel3, gridBagConstraints);
/*     */     
/* 109 */     this.jLabel4.setFont(new Font("Dialog", 1, 14));
/* 110 */     this.jLabel4.setText("Contributors (in no particular order)");
/* 111 */     gridBagConstraints = new GridBagConstraints();
/* 112 */     gridBagConstraints.gridx = 0;
/* 113 */     gridBagConstraints.gridy = 4;
/* 114 */     gridBagConstraints.anchor = 17;
/* 115 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/* 116 */     getContentPane().add(this.jLabel4, gridBagConstraints);
/*     */     
/* 118 */     this.jLabel5.setText("<html>Major Tom<br />Ice Hellion<br />Maelwys<br />LastChanceCav<br />NPettinato<br />Chimera<br />CJ Keys<br />nckestrel</html>");
/* 119 */     gridBagConstraints = new GridBagConstraints();
/* 120 */     gridBagConstraints.gridx = 0;
/* 121 */     gridBagConstraints.gridy = 5;
/* 122 */     gridBagConstraints.anchor = 17;
/* 123 */     gridBagConstraints.insets = new Insets(0, 20, 0, 4);
/* 124 */     getContentPane().add(this.jLabel5, gridBagConstraints);
/*     */     
/* 126 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCloseActionPerformed(ActionEvent evt) {
/* 130 */     dispose();
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgCredits.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */