/*     */ package ssw.gui;
/*     */ 
/*     */ import java.awt.Container;
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
/*     */ public class dlgTechBaseChooser
/*     */   extends JDialog
/*     */ {
/*  32 */   private boolean Clan = false;
/*     */   private JButton btnClan;
/*     */   private JButton btnInnerSphere;
/*     */   
/*  36 */   public dlgTechBaseChooser(Frame parent, boolean modal) { super(parent, modal);
/*  37 */     initComponents();
/*  38 */     setTitle("Choose a Tech Base");
/*     */   }
/*     */   
/*     */   public boolean IsClan() {
/*  42 */     return this.Clan;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private JLabel jLabel1;
/*     */   
/*     */ 
/*     */   private JPanel jPanel1;
/*     */   
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  55 */     this.jLabel1 = new JLabel();
/*  56 */     this.jPanel1 = new JPanel();
/*  57 */     this.btnInnerSphere = new JButton();
/*  58 */     this.btnClan = new JButton();
/*     */     
/*  60 */     setDefaultCloseOperation(2);
/*  61 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/*  63 */     this.jLabel1.setText("<html>Under Mixed Technology rules, this item can<br>be either Inner Sphere or Clan.  Please choose<br>a technology base for this item:</html>");
/*  64 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  65 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/*  66 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/*  68 */     this.btnInnerSphere.setText("Inner Sphere");
/*  69 */     this.btnInnerSphere.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  71 */         dlgTechBaseChooser.this.btnInnerSphereActionPerformed(evt);
/*     */       }
/*  73 */     });
/*  74 */     this.jPanel1.add(this.btnInnerSphere);
/*     */     
/*  76 */     this.btnClan.setText("Clan");
/*  77 */     this.btnClan.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  79 */         dlgTechBaseChooser.this.btnClanActionPerformed(evt);
/*     */       }
/*  81 */     });
/*  82 */     this.jPanel1.add(this.btnClan);
/*     */     
/*  84 */     gridBagConstraints = new GridBagConstraints();
/*  85 */     gridBagConstraints.gridx = 0;
/*  86 */     gridBagConstraints.gridy = 1;
/*  87 */     gridBagConstraints.insets = new Insets(0, 4, 4, 4);
/*  88 */     getContentPane().add(this.jPanel1, gridBagConstraints);
/*     */     
/*  90 */     pack();
/*     */   }
/*     */   
/*     */   private void btnInnerSphereActionPerformed(ActionEvent evt) {
/*  94 */     this.Clan = false;
/*  95 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnClanActionPerformed(ActionEvent evt) {
/*  99 */     this.Clan = true;
/* 100 */     setVisible(false);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgTechBaseChooser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */