/*    */ package ssw.gui;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Frame;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.GroupLayout;
/*    */ import javax.swing.GroupLayout.Alignment;
/*    */ import javax.swing.GroupLayout.ParallelGroup;
/*    */ import javax.swing.GroupLayout.SequentialGroup;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.LayoutStyle.ComponentPlacement;
/*    */ 
/*    */ public class dlgSSWFiles extends JDialog
/*    */ {
/* 19 */   public boolean result = false;
/*    */   private JButton btnCancel;
/*    */   
/*    */   public dlgSSWFiles(Frame parent, boolean modal) {
/* 23 */     super(parent, modal);
/* 24 */     initComponents();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private JButton btnSubmit;
/*    */   
/*    */ 
/*    */   private JLabel jLabel1;
/*    */   
/*    */   private void initComponents()
/*    */   {
/* 36 */     this.btnSubmit = new JButton();
/* 37 */     this.btnCancel = new JButton();
/* 38 */     this.jLabel1 = new JLabel();
/*    */     
/* 40 */     setDefaultCloseOperation(2);
/* 41 */     setTitle("Mech File Selection");
/* 42 */     setMinimumSize(new Dimension(400, 250));
/* 43 */     setModal(true);
/*    */     
/* 45 */     this.btnSubmit.setText("OK, downloaded and ready!");
/* 46 */     this.btnSubmit.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent evt) {
/* 48 */         dlgSSWFiles.this.btnSubmitActionPerformed(evt);
/*    */       }
/*    */       
/* 51 */     });
/* 52 */     this.btnCancel.setText("I'll do that later, go away");
/* 53 */     this.btnCancel.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent evt) {
/* 55 */         dlgSSWFiles.this.btnCancelActionPerformed(evt);
/*    */       }
/*    */       
/* 58 */     });
/* 59 */     this.jLabel1.setText("<html>\n<h3>Solaris Skunkwerks needs to know where you have placed your Mech files.</h3>\n<p>If you have not downloaded the zip file containing these files yet please go to <br />\n<h4>http://www.solarisskunkwerks.com/downloads</h4><br />\n and download the Master file available there. </p>\n<br />\n<p>Once this file has downloaded you can unzip it wherever you would like as the next step will be to tell SSW where you put them.</p>");
/*    */     
/* 61 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 62 */     getContentPane().setLayout(layout);
/* 63 */     layout.setHorizontalGroup(layout
/* 64 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 65 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 66 */       .addContainerGap()
/* 67 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 68 */       .addComponent(this.jLabel1, GroupLayout.Alignment.LEADING, -1, 380, 32767)
/* 69 */       .addGroup(layout.createSequentialGroup()
/* 70 */       .addComponent(this.btnSubmit)
/* 71 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 72 */       .addComponent(this.btnCancel)))
/* 73 */       .addContainerGap()));
/*    */     
/* 75 */     layout.setVerticalGroup(layout
/* 76 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 77 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 78 */       .addContainerGap()
/* 79 */       .addComponent(this.jLabel1)
/* 80 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 46, 32767)
/* 81 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 82 */       .addComponent(this.btnCancel)
/* 83 */       .addComponent(this.btnSubmit))
/* 84 */       .addContainerGap()));
/*    */     
/*    */ 
/* 87 */     pack();
/*    */   }
/*    */   
/*    */   private void btnSubmitActionPerformed(ActionEvent evt) {
/* 91 */     this.result = true;
/* 92 */     setVisible(false);
/*    */   }
/*    */   
/*    */   private void btnCancelActionPerformed(ActionEvent evt) {
/* 96 */     this.result = false;
/* 97 */     setVisible(false);
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgSSWFiles.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */