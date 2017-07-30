/*     */ package ssw.gui;
/*     */ 
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
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgLicense
/*     */   extends JDialog
/*     */ {
/*     */   private JButton btnClose;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel16;
/*     */   private JLabel jLabel2;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JTextArea jTextArea1;
/*     */   
/*     */   public dlgLicense(Frame parent, boolean modal)
/*     */   {
/*  37 */     super(parent, modal);
/*  38 */     initComponents();
/*  39 */     setResizable(false);
/*  40 */     setTitle("SSW License Agreement");
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
/*  52 */     this.jLabel1 = new JLabel();
/*  53 */     this.jLabel2 = new JLabel();
/*  54 */     this.jLabel16 = new JLabel();
/*  55 */     this.btnClose = new JButton();
/*  56 */     this.jScrollPane1 = new JScrollPane();
/*  57 */     this.jTextArea1 = new JTextArea();
/*     */     
/*  59 */     setDefaultCloseOperation(2);
/*  60 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/*  62 */     this.jLabel1.setText("Solaris Skunk Werks");
/*  63 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  64 */     gridBagConstraints.fill = 2;
/*  65 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/*  66 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/*  68 */     this.jLabel2.setText("Copyright (c) 2008 ~ 2009, Justin R. Bengtson (LostInSpace@SolarisSkunkWerks.com)");
/*  69 */     gridBagConstraints = new GridBagConstraints();
/*  70 */     gridBagConstraints.gridx = 0;
/*  71 */     gridBagConstraints.gridy = 1;
/*  72 */     gridBagConstraints.fill = 2;
/*  73 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/*  74 */     getContentPane().add(this.jLabel2, gridBagConstraints);
/*     */     
/*  76 */     this.jLabel16.setText("All Rights Reserved.");
/*  77 */     gridBagConstraints = new GridBagConstraints();
/*  78 */     gridBagConstraints.gridx = 0;
/*  79 */     gridBagConstraints.gridy = 2;
/*  80 */     gridBagConstraints.fill = 2;
/*  81 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/*  82 */     getContentPane().add(this.jLabel16, gridBagConstraints);
/*     */     
/*  84 */     this.btnClose.setText("Close");
/*  85 */     this.btnClose.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  87 */         dlgLicense.this.btnCloseActionPerformed(evt);
/*     */       }
/*  89 */     });
/*  90 */     gridBagConstraints = new GridBagConstraints();
/*  91 */     gridBagConstraints.gridx = 0;
/*  92 */     gridBagConstraints.gridy = 4;
/*  93 */     gridBagConstraints.anchor = 14;
/*  94 */     gridBagConstraints.insets = new Insets(4, 0, 4, 4);
/*  95 */     getContentPane().add(this.btnClose, gridBagConstraints);
/*     */     
/*  97 */     this.jScrollPane1.setHorizontalScrollBarPolicy(31);
/*  98 */     this.jScrollPane1.setVerticalScrollBarPolicy(21);
/*  99 */     this.jScrollPane1.setEnabled(false);
/* 100 */     this.jScrollPane1.setMinimumSize(new Dimension(600, 350));
/* 101 */     this.jScrollPane1.setPreferredSize(new Dimension(600, 350));
/*     */     
/* 103 */     this.jTextArea1.setColumns(20);
/* 104 */     this.jTextArea1.setEditable(false);
/* 105 */     this.jTextArea1.setRows(5);
/* 106 */     this.jTextArea1.setText("Redistribution and use in source and binary forms, with or without modification,\nare permitted provided that the following conditions are met:\n\n    * Redistributions of source code must retain the above copyright notice,\n        this list of conditions and the following disclaimer.\n    * Redistributions in binary form must reproduce the above copyright notice,\n        this list of conditions and the following disclaimer in the\n        documentation and/or other materials provided with the distribution.\n    * Neither the name of Justin R. Bengtson nor the names of contributors may\n        be used to endorse or promote products derived from this software\n        without specific prior written permission.\n\nTHIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND\nANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED\nWARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\nDISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR\nANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES\n(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;\nLOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON\nANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS\nSOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");
/* 107 */     this.jTextArea1.setMinimumSize(new Dimension(600, 330));
/* 108 */     this.jTextArea1.setPreferredSize(new Dimension(600, 330));
/* 109 */     this.jScrollPane1.setViewportView(this.jTextArea1);
/*     */     
/* 111 */     gridBagConstraints = new GridBagConstraints();
/* 112 */     gridBagConstraints.gridx = 0;
/* 113 */     gridBagConstraints.gridy = 3;
/* 114 */     gridBagConstraints.fill = 2;
/* 115 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 116 */     getContentPane().add(this.jScrollPane1, gridBagConstraints);
/*     */     
/* 118 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCloseActionPerformed(ActionEvent evt) {
/* 122 */     dispose();
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgLicense.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */