/*     */ package ssw.gui;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class dlgAboutBox extends javax.swing.JDialog
/*     */ {
/*     */   ifMechForm Parent;
/*     */   private JButton btnClose;
/*     */   private JButton btnLicense;
/*     */   private JButton jButton1;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JLabel jLabel6;
/*     */   private JLabel jLabel7;
/*     */   private JLabel jLabel8;
/*     */   private JLabel jLabel9;
/*     */   private JLabel lblAppName;
/*     */   private JLabel lblAuthor;
/*     */   private JLabel lblAuthorLabel;
/*     */   private JLabel lblEmail;
/*     */   private JLabel lblEmailLabel;
/*     */   private JLabel lblLogo;
/*     */   private JLabel lblRelease;
/*     */   private JLabel lblReleaseLabel;
/*     */   private JLabel lblVersion;
/*     */   private JLabel lblVersionLabel;
/*     */   
/*     */   public dlgAboutBox(java.awt.Frame parent, boolean modal)
/*     */   {
/*  40 */     super(parent, modal);
/*  41 */     this.Parent = ((ifMechForm)parent);
/*  42 */     initComponents();
/*  43 */     setResizable(false);
/*  44 */     setTitle("About Solaris Skunk Werks");
/*  45 */     this.lblAppName.setText("Solaris Skunk Werks");
/*  46 */     this.lblVersion.setText("0.6.83.1");
/*  47 */     this.lblRelease.setText("Beta 2");
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
/*  59 */     this.jButton1 = new JButton();
/*  60 */     this.lblLogo = new JLabel();
/*  61 */     this.lblAppName = new JLabel();
/*  62 */     this.lblAuthorLabel = new JLabel();
/*  63 */     this.lblAuthor = new JLabel();
/*  64 */     this.lblVersionLabel = new JLabel();
/*  65 */     this.lblVersion = new JLabel();
/*  66 */     this.lblEmailLabel = new JLabel();
/*  67 */     this.lblEmail = new JLabel();
/*  68 */     this.btnClose = new JButton();
/*  69 */     this.jLabel1 = new JLabel();
/*  70 */     this.jLabel2 = new JLabel();
/*  71 */     this.jLabel3 = new JLabel();
/*  72 */     this.jLabel4 = new JLabel();
/*  73 */     this.jLabel5 = new JLabel();
/*  74 */     this.btnLicense = new JButton();
/*  75 */     this.lblReleaseLabel = new JLabel();
/*  76 */     this.lblRelease = new JLabel();
/*  77 */     this.jLabel6 = new JLabel();
/*  78 */     this.jLabel7 = new JLabel();
/*  79 */     this.jLabel8 = new JLabel();
/*  80 */     this.jLabel9 = new JLabel();
/*     */     
/*  82 */     this.jButton1.setText("jButton1");
/*     */     
/*  84 */     setDefaultCloseOperation(2);
/*  85 */     getContentPane().setLayout(new java.awt.GridBagLayout());
/*     */     
/*  87 */     this.lblLogo.setFont(new Font("Arial", 0, 11));
/*  88 */     this.lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/SSW_Logo.png")));
/*  89 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  90 */     gridBagConstraints.gridheight = 10;
/*  91 */     gridBagConstraints.anchor = 17;
/*  92 */     gridBagConstraints.insets = new Insets(0, 6, 0, 6);
/*  93 */     getContentPane().add(this.lblLogo, gridBagConstraints);
/*     */     
/*  95 */     this.lblAppName.setFont(new Font("Arial", 1, 18));
/*  96 */     this.lblAppName.setText("Solaris Skunk Werks");
/*  97 */     gridBagConstraints = new GridBagConstraints();
/*  98 */     gridBagConstraints.gridwidth = 2;
/*  99 */     gridBagConstraints.anchor = 16;
/* 100 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 101 */     getContentPane().add(this.lblAppName, gridBagConstraints);
/*     */     
/* 103 */     this.lblAuthorLabel.setFont(new Font("Arial", 1, 11));
/* 104 */     this.lblAuthorLabel.setText("Originator:");
/* 105 */     gridBagConstraints = new GridBagConstraints();
/* 106 */     gridBagConstraints.gridx = 1;
/* 107 */     gridBagConstraints.gridy = 1;
/* 108 */     gridBagConstraints.ipadx = 2;
/* 109 */     gridBagConstraints.ipady = 2;
/* 110 */     gridBagConstraints.anchor = 13;
/* 111 */     gridBagConstraints.insets = new Insets(2, 2, 2, 2);
/* 112 */     getContentPane().add(this.lblAuthorLabel, gridBagConstraints);
/*     */     
/* 114 */     this.lblAuthor.setFont(new Font("Arial", 0, 11));
/* 115 */     this.lblAuthor.setText("Justin Bengtson");
/* 116 */     gridBagConstraints = new GridBagConstraints();
/* 117 */     gridBagConstraints.gridx = 2;
/* 118 */     gridBagConstraints.gridy = 1;
/* 119 */     gridBagConstraints.ipadx = 2;
/* 120 */     gridBagConstraints.ipady = 2;
/* 121 */     gridBagConstraints.anchor = 17;
/* 122 */     gridBagConstraints.insets = new Insets(2, 2, 2, 2);
/* 123 */     getContentPane().add(this.lblAuthor, gridBagConstraints);
/*     */     
/* 125 */     this.lblVersionLabel.setFont(new Font("Arial", 1, 11));
/* 126 */     this.lblVersionLabel.setText("Version:");
/* 127 */     gridBagConstraints = new GridBagConstraints();
/* 128 */     gridBagConstraints.gridx = 1;
/* 129 */     gridBagConstraints.gridy = 7;
/* 130 */     gridBagConstraints.anchor = 13;
/* 131 */     gridBagConstraints.insets = new Insets(2, 2, 2, 2);
/* 132 */     getContentPane().add(this.lblVersionLabel, gridBagConstraints);
/*     */     
/* 134 */     this.lblVersion.setFont(new Font("Arial", 0, 11));
/* 135 */     this.lblVersion.setText("0.0.1");
/* 136 */     gridBagConstraints = new GridBagConstraints();
/* 137 */     gridBagConstraints.gridx = 2;
/* 138 */     gridBagConstraints.gridy = 7;
/* 139 */     gridBagConstraints.anchor = 17;
/* 140 */     gridBagConstraints.insets = new Insets(2, 2, 2, 2);
/* 141 */     getContentPane().add(this.lblVersion, gridBagConstraints);
/*     */     
/* 143 */     this.lblEmailLabel.setFont(new Font("Arial", 1, 11));
/* 144 */     this.lblEmailLabel.setText("Email:");
/* 145 */     gridBagConstraints = new GridBagConstraints();
/* 146 */     gridBagConstraints.gridx = 1;
/* 147 */     gridBagConstraints.gridy = 6;
/* 148 */     gridBagConstraints.anchor = 13;
/* 149 */     gridBagConstraints.insets = new Insets(2, 2, 2, 2);
/* 150 */     getContentPane().add(this.lblEmailLabel, gridBagConstraints);
/*     */     
/* 152 */     this.lblEmail.setFont(new Font("Arial", 0, 11));
/* 153 */     this.lblEmail.setText("Skyhigh@SolarisSkunkWerks.com");
/* 154 */     gridBagConstraints = new GridBagConstraints();
/* 155 */     gridBagConstraints.gridx = 2;
/* 156 */     gridBagConstraints.gridy = 6;
/* 157 */     gridBagConstraints.anchor = 17;
/* 158 */     gridBagConstraints.insets = new Insets(2, 2, 2, 6);
/* 159 */     getContentPane().add(this.lblEmail, gridBagConstraints);
/*     */     
/* 161 */     this.btnClose.setText("Close");
/* 162 */     this.btnClose.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 164 */         dlgAboutBox.this.btnCloseActionPerformed(evt);
/*     */       }
/* 166 */     });
/* 167 */     gridBagConstraints = new GridBagConstraints();
/* 168 */     gridBagConstraints.gridx = 2;
/* 169 */     gridBagConstraints.gridy = 9;
/* 170 */     gridBagConstraints.anchor = 14;
/* 171 */     gridBagConstraints.insets = new Insets(0, 0, 2, 2);
/* 172 */     getContentPane().add(this.btnClose, gridBagConstraints);
/*     */     
/* 174 */     this.jLabel1.setHorizontalAlignment(0);
/* 175 */     this.jLabel1.setText("Copyright (c) 2008, Justin R. Bengtson (poopshotgun@yahoo.com)");
/* 176 */     gridBagConstraints = new GridBagConstraints();
/* 177 */     gridBagConstraints.gridx = 0;
/* 178 */     gridBagConstraints.gridy = 10;
/* 179 */     gridBagConstraints.gridwidth = 0;
/* 180 */     gridBagConstraints.fill = 2;
/* 181 */     gridBagConstraints.insets = new Insets(8, 0, 0, 0);
/* 182 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/* 184 */     this.jLabel2.setText("    BattleTech, Mech, BattleMech and MechWarrior are Registered");
/* 185 */     gridBagConstraints = new GridBagConstraints();
/* 186 */     gridBagConstraints.gridx = 0;
/* 187 */     gridBagConstraints.gridy = 11;
/* 188 */     gridBagConstraints.gridwidth = 0;
/* 189 */     gridBagConstraints.fill = 2;
/* 190 */     gridBagConstraints.insets = new Insets(8, 0, 0, 0);
/* 191 */     getContentPane().add(this.jLabel2, gridBagConstraints);
/*     */     
/* 193 */     this.jLabel3.setText("    Trademarks of  WizKids, LLC.  Original BattleTech material");
/* 194 */     gridBagConstraints = new GridBagConstraints();
/* 195 */     gridBagConstraints.gridx = 0;
/* 196 */     gridBagConstraints.gridy = 12;
/* 197 */     gridBagConstraints.gridwidth = 0;
/* 198 */     gridBagConstraints.fill = 2;
/* 199 */     getContentPane().add(this.jLabel3, gridBagConstraints);
/*     */     
/* 201 */     this.jLabel4.setText("    Copyright by WizKids, LLC.");
/* 202 */     gridBagConstraints = new GridBagConstraints();
/* 203 */     gridBagConstraints.gridx = 0;
/* 204 */     gridBagConstraints.gridy = 13;
/* 205 */     gridBagConstraints.gridwidth = 0;
/* 206 */     gridBagConstraints.fill = 2;
/* 207 */     getContentPane().add(this.jLabel4, gridBagConstraints);
/*     */     
/* 209 */     this.jLabel5.setText("    All Rights Reserved.  Used without permission. ");
/* 210 */     gridBagConstraints = new GridBagConstraints();
/* 211 */     gridBagConstraints.gridx = 0;
/* 212 */     gridBagConstraints.gridy = 14;
/* 213 */     gridBagConstraints.gridwidth = 0;
/* 214 */     gridBagConstraints.fill = 2;
/* 215 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 216 */     getContentPane().add(this.jLabel5, gridBagConstraints);
/*     */     
/* 218 */     this.btnLicense.setText("Show Software License");
/* 219 */     this.btnLicense.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 221 */         dlgAboutBox.this.btnLicenseActionPerformed(evt);
/*     */       }
/* 223 */     });
/* 224 */     gridBagConstraints = new GridBagConstraints();
/* 225 */     gridBagConstraints.gridx = 1;
/* 226 */     gridBagConstraints.gridy = 15;
/* 227 */     gridBagConstraints.gridwidth = 2;
/* 228 */     gridBagConstraints.anchor = 14;
/* 229 */     gridBagConstraints.insets = new Insets(8, 0, 2, 2);
/* 230 */     getContentPane().add(this.btnLicense, gridBagConstraints);
/*     */     
/* 232 */     this.lblReleaseLabel.setFont(new Font("Tahoma", 1, 11));
/* 233 */     this.lblReleaseLabel.setText("Release:");
/* 234 */     gridBagConstraints = new GridBagConstraints();
/* 235 */     gridBagConstraints.gridx = 1;
/* 236 */     gridBagConstraints.gridy = 8;
/* 237 */     gridBagConstraints.anchor = 13;
/* 238 */     gridBagConstraints.insets = new Insets(2, 2, 2, 2);
/* 239 */     getContentPane().add(this.lblReleaseLabel, gridBagConstraints);
/*     */     
/* 241 */     this.lblRelease.setText("Beta 1");
/* 242 */     gridBagConstraints = new GridBagConstraints();
/* 243 */     gridBagConstraints.gridx = 2;
/* 244 */     gridBagConstraints.gridy = 8;
/* 245 */     gridBagConstraints.anchor = 17;
/* 246 */     gridBagConstraints.insets = new Insets(2, 2, 2, 2);
/* 247 */     getContentPane().add(this.lblRelease, gridBagConstraints);
/*     */     
/* 249 */     this.jLabel6.setText("Current Developers:");
/* 250 */     gridBagConstraints = new GridBagConstraints();
/* 251 */     gridBagConstraints.gridx = 1;
/* 252 */     gridBagConstraints.gridy = 2;
/* 253 */     gridBagConstraints.gridwidth = 2;
/* 254 */     gridBagConstraints.anchor = 17;
/* 255 */     getContentPane().add(this.jLabel6, gridBagConstraints);
/*     */     
/* 257 */     this.jLabel7.setText("Specter83");
/* 258 */     gridBagConstraints = new GridBagConstraints();
/* 259 */     gridBagConstraints.gridx = 2;
/* 260 */     gridBagConstraints.gridy = 3;
/* 261 */     gridBagConstraints.anchor = 17;
/* 262 */     getContentPane().add(this.jLabel7, gridBagConstraints);
/*     */     
/* 264 */     this.jLabel8.setText("Skyhigh");
/* 265 */     gridBagConstraints = new GridBagConstraints();
/* 266 */     gridBagConstraints.gridx = 2;
/* 267 */     gridBagConstraints.gridy = 4;
/* 268 */     gridBagConstraints.anchor = 17;
/* 269 */     getContentPane().add(this.jLabel8, gridBagConstraints);
/*     */     
/* 271 */     this.jLabel9.setText("ArcAngel");
/* 272 */     gridBagConstraints = new GridBagConstraints();
/* 273 */     gridBagConstraints.gridx = 2;
/* 274 */     gridBagConstraints.gridy = 5;
/* 275 */     gridBagConstraints.anchor = 17;
/* 276 */     getContentPane().add(this.jLabel9, gridBagConstraints);
/*     */     
/* 278 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCloseActionPerformed(ActionEvent evt) {
/* 282 */     dispose();
/*     */   }
/*     */   
/*     */   private void btnLicenseActionPerformed(ActionEvent evt) {
/* 286 */     dlgLicense lwindow = new dlgLicense((javax.swing.JFrame)this.Parent, true);
/* 287 */     Point p = getLocationOnScreen();
/* 288 */     lwindow.setLocation(p.x, p.y);
/* 289 */     lwindow.setVisible(true);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgAboutBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */