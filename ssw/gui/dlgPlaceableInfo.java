/*     */ package ssw.gui;
/*     */ 
/*     */ import components.AvailableCode;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class dlgPlaceableInfo extends javax.swing.JDialog
/*     */ {
/*     */   ifMechForm Parent;
/*     */   private javax.swing.JButton btnClose;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel10;
/*     */   private JLabel jLabel11;
/*     */   private JLabel jLabel12;
/*     */   private JLabel jLabel13;
/*     */   private JLabel jLabel14;
/*     */   private JLabel jLabel16;
/*     */   private JLabel jLabel18;
/*     */   private JLabel jLabel19;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel20;
/*     */   private JLabel jLabel24;
/*     */   private JLabel jLabel25;
/*     */   private JLabel jLabel26;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JLabel jLabel6;
/*     */   private JLabel jLabel7;
/*     */   private JLabel jLabel8;
/*     */   private JLabel jLabel9;
/*     */   private javax.swing.JSeparator jSeparator1;
/*     */   private javax.swing.JSeparator jSeparator2;
/*     */   
/*     */   public dlgPlaceableInfo(java.awt.Frame parent, boolean modal)
/*     */   {
/*  40 */     super(parent, modal);
/*  41 */     initComponents();
/*  42 */     this.Parent = ((ifMechForm)parent);
/*  43 */     setTitle("Item Information");
/*  44 */     SetState();
/*  45 */     pack();
/*     */   }
/*     */   
/*     */   private void SetState() {
/*  49 */     components.abPlaceable a = this.Parent.GetCurItem();
/*  50 */     AvailableCode AC = a.GetAvailability();
/*     */     
/*  52 */     switch (AC.GetTechBase()) {
/*     */     case 0: 
/*  54 */       this.pnlClan.setVisible(false);
/*  55 */       this.lblTechRating.setText(AC.GetISTechRating() + "");
/*  56 */       break;
/*     */     case 1: 
/*  58 */       this.pnlInnerSphere.setVisible(false);
/*  59 */       this.lblTechRating.setText(AC.GetCLTechRating() + "");
/*  60 */       break;
/*     */     case 2: 
/*  62 */       this.lblTechRating.setText(AC.GetISTechRating() + " (IS) / " + AC.GetCLTechRating() + " (CL)");
/*     */     }
/*     */     
/*     */     
/*  66 */     this.lblName.setText(a.ActualName());
/*  67 */     this.lblTonnage.setText("" + a.GetTonnage());
/*     */     
/*  69 */     this.lblCost.setText(String.format("%1$,.4f", new Object[] { Double.valueOf(a.GetCost()) }));
/*  70 */     this.lblBV.setText(common.CommonTools.GetAggregateReportBV(a));
/*  71 */     this.lblISACSL.setText("" + AC.GetISSLCode());
/*  72 */     this.lblISACSW.setText("" + AC.GetISSWCode());
/*  73 */     this.lblISACCI.setText("" + AC.GetISCICode());
/*  74 */     this.lblISIntro.setText(AC.GetISIntroDate() + " (" + AC.GetISIntroFaction() + ")");
/*  75 */     if (AC.WentExtinctIS()) {
/*  76 */       this.lblISExtinct.setText("" + AC.GetISExtinctDate());
/*     */     } else {
/*  78 */       this.lblISExtinct.setText("--");
/*     */     }
/*  80 */     if (AC.WasReIntrodIS()) {
/*  81 */       this.lblISReIntro.setText(AC.GetISReIntroDate() + " (" + AC.GetISReIntroFaction() + ")");
/*     */     } else {
/*  83 */       this.lblISReIntro.setText("--");
/*     */     }
/*  85 */     if (AC.Is_ISPrototype()) {
/*  86 */       String temp = "Status: R&D Start Date: " + AC.GetISRandDStartDate() + " (" + AC.GetISRandDFaction() + "), ";
/*  87 */       temp = temp + "Prototype: " + AC.GetISPrototypeDate() + " (" + AC.GetISPrototypeFaction() + ")";
/*  88 */       this.lblISExtraInfo.setText(temp);
/*     */     } else {
/*  90 */       this.lblISExtraInfo.setText("Status: Production Equipment");
/*     */     }
/*  92 */     this.lblClanACSL.setText("" + AC.GetCLSLCode());
/*  93 */     this.lblClanACSW.setText("" + AC.GetCLSWCode());
/*  94 */     this.lblClanACCI.setText("" + AC.GetCLCICode());
/*  95 */     this.lblClanIntro.setText(AC.GetCLIntroDate() + " (" + AC.GetCLIntroFaction() + ")");
/*  96 */     if (AC.WentExtinctCL()) {
/*  97 */       this.lblClanExtinct.setText("" + AC.GetCLExtinctDate());
/*     */     } else {
/*  99 */       this.lblClanExtinct.setText("--");
/*     */     }
/* 101 */     if (AC.WasReIntrodCL()) {
/* 102 */       this.lblClanReIntro.setText(AC.GetCLReIntroDate() + " (" + AC.GetCLReIntroFaction() + ")");
/*     */     } else {
/* 104 */       this.lblClanReIntro.setText("--");
/*     */     }
/* 106 */     if (AC.Is_CLPrototype()) {
/* 107 */       String temp = "Status: R&D Start Date: " + AC.GetCLRandDStartDate() + " (" + AC.GetCLRandDFaction() + "), ";
/* 108 */       temp = temp + "Prototype: " + AC.GetCLPrototypeDate() + " (" + AC.GetCLPrototypeFaction() + ")";
/* 109 */       this.lblClanExtraInfo.setText(temp);
/*     */     } else {
/* 111 */       this.lblClanExtraInfo.setText("Status: Production Equipment");
/*     */     }
/* 113 */     this.lblRulesBM.setText(common.CommonTools.GetRulesLevelString(AC.GetRulesLevel_BM()));
/* 114 */     this.lblRulesIM.setText(common.CommonTools.GetRulesLevelString(AC.GetRulesLevel_IM()));
/* 115 */     this.lblBookRef.setText(a.BookReference());
/*     */   }
/*     */   
/*     */ 
/*     */   private javax.swing.JSeparator jSeparator3;
/*     */   private javax.swing.JSeparator jSeparator4;
/*     */   private JLabel lblBV;
/*     */   private JLabel lblBookRef;
/*     */   private JLabel lblClanACCI;
/*     */   private JLabel lblClanACSL;
/*     */   
/*     */   private void initComponents()
/*     */   {
/* 128 */     this.jLabel4 = new JLabel();
/* 129 */     this.lblTechRating = new JLabel();
/* 130 */     this.lblName = new JLabel();
/* 131 */     this.jLabel10 = new JLabel();
/* 132 */     this.lblBV = new JLabel();
/* 133 */     this.jLabel12 = new JLabel();
/* 134 */     this.lblTonnage = new JLabel();
/* 135 */     this.jSeparator1 = new javax.swing.JSeparator();
/* 136 */     this.btnClose = new javax.swing.JButton();
/* 137 */     this.jLabel14 = new JLabel();
/* 138 */     this.lblCost = new JLabel();
/* 139 */     this.jLabel6 = new JLabel();
/* 140 */     this.lblRulesBM = new JLabel();
/* 141 */     this.jSeparator2 = new javax.swing.JSeparator();
/* 142 */     this.lblRulesIM = new JLabel();
/* 143 */     this.jLabel13 = new JLabel();
/* 144 */     this.jSeparator3 = new javax.swing.JSeparator();
/* 145 */     this.pnlClan = new JPanel();
/* 146 */     this.jLabel11 = new JLabel();
/* 147 */     this.lblClanExtraInfo = new JLabel();
/* 148 */     this.jLabel3 = new JLabel();
/* 149 */     this.jLabel2 = new JLabel();
/* 150 */     this.jLabel1 = new JLabel();
/* 151 */     this.lblClanACCI = new JLabel();
/* 152 */     this.lblClanACSW = new JLabel();
/* 153 */     this.lblClanACSL = new JLabel();
/* 154 */     this.jLabel9 = new JLabel();
/* 155 */     this.jLabel8 = new JLabel();
/* 156 */     this.jLabel7 = new JLabel();
/* 157 */     this.lblClanReIntro = new JLabel();
/* 158 */     this.lblClanExtinct = new JLabel();
/* 159 */     this.lblClanIntro = new JLabel();
/* 160 */     this.pnlInnerSphere = new JPanel();
/* 161 */     this.jLabel18 = new JLabel();
/* 162 */     this.lblISACSL = new JLabel();
/* 163 */     this.lblISACSW = new JLabel();
/* 164 */     this.lblISACCI = new JLabel();
/* 165 */     this.jLabel24 = new JLabel();
/* 166 */     this.jLabel25 = new JLabel();
/* 167 */     this.jLabel26 = new JLabel();
/* 168 */     this.lblISIntro = new JLabel();
/* 169 */     this.lblISExtinct = new JLabel();
/* 170 */     this.jLabel20 = new JLabel();
/* 171 */     this.jLabel19 = new JLabel();
/* 172 */     this.lblISReIntro = new JLabel();
/* 173 */     this.lblISExtraInfo = new JLabel();
/* 174 */     this.jLabel16 = new JLabel();
/* 175 */     this.jSeparator4 = new javax.swing.JSeparator();
/* 176 */     this.jLabel5 = new JLabel();
/* 177 */     this.lblBookRef = new JLabel();
/*     */     
/* 179 */     setDefaultCloseOperation(2);
/* 180 */     getContentPane().setLayout(new java.awt.GridBagLayout());
/*     */     
/* 182 */     this.jLabel4.setText("Tech Rating");
/* 183 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 184 */     gridBagConstraints.gridx = 0;
/* 185 */     gridBagConstraints.gridy = 1;
/* 186 */     gridBagConstraints.insets = new Insets(4, 2, 0, 2);
/* 187 */     getContentPane().add(this.jLabel4, gridBagConstraints);
/*     */     
/* 189 */     this.lblTechRating.setText("X (IS)/X (CL)");
/* 190 */     gridBagConstraints = new GridBagConstraints();
/* 191 */     gridBagConstraints.gridx = 0;
/* 192 */     gridBagConstraints.gridy = 2;
/* 193 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/* 194 */     getContentPane().add(this.lblTechRating, gridBagConstraints);
/*     */     
/* 196 */     this.lblName.setFont(new java.awt.Font("Dialog", 1, 14));
/* 197 */     this.lblName.setText("Hyper Assault Gauss-40");
/* 198 */     gridBagConstraints = new GridBagConstraints();
/* 199 */     gridBagConstraints.gridx = 0;
/* 200 */     gridBagConstraints.gridy = 0;
/* 201 */     gridBagConstraints.gridwidth = 3;
/* 202 */     gridBagConstraints.fill = 2;
/* 203 */     gridBagConstraints.anchor = 17;
/* 204 */     gridBagConstraints.insets = new Insets(4, 4, 0, 2);
/* 205 */     getContentPane().add(this.lblName, gridBagConstraints);
/*     */     
/* 207 */     this.jLabel10.setText("Tonnage");
/* 208 */     gridBagConstraints = new GridBagConstraints();
/* 209 */     gridBagConstraints.gridx = 2;
/* 210 */     gridBagConstraints.gridy = 1;
/* 211 */     gridBagConstraints.insets = new Insets(4, 2, 0, 4);
/* 212 */     getContentPane().add(this.jLabel10, gridBagConstraints);
/*     */     
/* 214 */     this.lblBV.setText("000.00/000.00");
/* 215 */     gridBagConstraints = new GridBagConstraints();
/* 216 */     gridBagConstraints.gridx = 1;
/* 217 */     gridBagConstraints.gridy = 2;
/* 218 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/* 219 */     getContentPane().add(this.lblBV, gridBagConstraints);
/*     */     
/* 221 */     this.jLabel12.setText("BV");
/* 222 */     gridBagConstraints = new GridBagConstraints();
/* 223 */     gridBagConstraints.gridx = 1;
/* 224 */     gridBagConstraints.gridy = 1;
/* 225 */     gridBagConstraints.insets = new Insets(4, 2, 0, 2);
/* 226 */     getContentPane().add(this.jLabel12, gridBagConstraints);
/*     */     
/* 228 */     this.lblTonnage.setText("000.00");
/* 229 */     gridBagConstraints = new GridBagConstraints();
/* 230 */     gridBagConstraints.gridx = 2;
/* 231 */     gridBagConstraints.gridy = 2;
/* 232 */     gridBagConstraints.insets = new Insets(0, 2, 0, 6);
/* 233 */     getContentPane().add(this.lblTonnage, gridBagConstraints);
/*     */     
/* 235 */     this.jSeparator1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 236 */     gridBagConstraints = new GridBagConstraints();
/* 237 */     gridBagConstraints.gridx = 0;
/* 238 */     gridBagConstraints.gridy = 9;
/* 239 */     gridBagConstraints.gridwidth = 3;
/* 240 */     gridBagConstraints.fill = 2;
/* 241 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 242 */     getContentPane().add(this.jSeparator1, gridBagConstraints);
/*     */     
/* 244 */     this.btnClose.setText("Close");
/* 245 */     this.btnClose.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(java.awt.event.ActionEvent evt) {
/* 247 */         dlgPlaceableInfo.this.btnCloseActionPerformed(evt);
/*     */       }
/* 249 */     });
/* 250 */     gridBagConstraints = new GridBagConstraints();
/* 251 */     gridBagConstraints.gridx = 0;
/* 252 */     gridBagConstraints.gridy = 13;
/* 253 */     gridBagConstraints.gridwidth = 3;
/* 254 */     gridBagConstraints.anchor = 13;
/* 255 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 256 */     getContentPane().add(this.btnClose, gridBagConstraints);
/*     */     
/* 258 */     this.jLabel14.setText("Cost:");
/* 259 */     gridBagConstraints = new GridBagConstraints();
/* 260 */     gridBagConstraints.gridx = 0;
/* 261 */     gridBagConstraints.gridy = 4;
/* 262 */     gridBagConstraints.gridwidth = 2;
/* 263 */     gridBagConstraints.anchor = 17;
/* 264 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 265 */     getContentPane().add(this.jLabel14, gridBagConstraints);
/*     */     
/* 267 */     this.lblCost.setText("000,000,000,000.00");
/* 268 */     gridBagConstraints = new GridBagConstraints();
/* 269 */     gridBagConstraints.gridx = 2;
/* 270 */     gridBagConstraints.gridy = 4;
/* 271 */     gridBagConstraints.anchor = 17;
/* 272 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 273 */     getContentPane().add(this.lblCost, gridBagConstraints);
/*     */     
/* 275 */     this.jLabel6.setText("Rules Level (BattleMech):");
/* 276 */     gridBagConstraints = new GridBagConstraints();
/* 277 */     gridBagConstraints.gridx = 0;
/* 278 */     gridBagConstraints.gridy = 5;
/* 279 */     gridBagConstraints.gridwidth = 2;
/* 280 */     gridBagConstraints.anchor = 17;
/* 281 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 282 */     getContentPane().add(this.jLabel6, gridBagConstraints);
/*     */     
/* 284 */     this.lblRulesBM.setText("Experimental");
/* 285 */     gridBagConstraints = new GridBagConstraints();
/* 286 */     gridBagConstraints.gridx = 2;
/* 287 */     gridBagConstraints.gridy = 5;
/* 288 */     gridBagConstraints.anchor = 17;
/* 289 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 290 */     getContentPane().add(this.lblRulesBM, gridBagConstraints);
/*     */     
/* 292 */     this.jSeparator2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 293 */     gridBagConstraints = new GridBagConstraints();
/* 294 */     gridBagConstraints.gridx = 0;
/* 295 */     gridBagConstraints.gridy = 3;
/* 296 */     gridBagConstraints.gridwidth = 3;
/* 297 */     gridBagConstraints.fill = 2;
/* 298 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 299 */     getContentPane().add(this.jSeparator2, gridBagConstraints);
/*     */     
/* 301 */     this.lblRulesIM.setText("Experimental");
/* 302 */     gridBagConstraints = new GridBagConstraints();
/* 303 */     gridBagConstraints.gridx = 2;
/* 304 */     gridBagConstraints.gridy = 6;
/* 305 */     gridBagConstraints.anchor = 17;
/* 306 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 307 */     getContentPane().add(this.lblRulesIM, gridBagConstraints);
/*     */     
/* 309 */     this.jLabel13.setText("Rules Level (IndustrialMech):");
/* 310 */     gridBagConstraints = new GridBagConstraints();
/* 311 */     gridBagConstraints.gridx = 0;
/* 312 */     gridBagConstraints.gridy = 6;
/* 313 */     gridBagConstraints.gridwidth = 2;
/* 314 */     gridBagConstraints.anchor = 17;
/* 315 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 316 */     getContentPane().add(this.jLabel13, gridBagConstraints);
/*     */     
/* 318 */     this.jSeparator3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 319 */     gridBagConstraints = new GridBagConstraints();
/* 320 */     gridBagConstraints.gridx = 0;
/* 321 */     gridBagConstraints.gridy = 7;
/* 322 */     gridBagConstraints.gridwidth = 3;
/* 323 */     gridBagConstraints.fill = 2;
/* 324 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 325 */     getContentPane().add(this.jSeparator3, gridBagConstraints);
/*     */     
/* 327 */     this.pnlClan.setLayout(new java.awt.GridBagLayout());
/*     */     
/* 329 */     this.jLabel11.setText("Clan Availability");
/* 330 */     gridBagConstraints = new GridBagConstraints();
/* 331 */     gridBagConstraints.gridx = 0;
/* 332 */     gridBagConstraints.gridy = 0;
/* 333 */     gridBagConstraints.gridwidth = 4;
/* 334 */     gridBagConstraints.anchor = 17;
/* 335 */     this.pnlClan.add(this.jLabel11, gridBagConstraints);
/*     */     
/* 337 */     this.lblClanExtraInfo.setText("Status: R&D Start Date: 9999 (CJF), Prototype: 9999 (CJF)");
/* 338 */     gridBagConstraints = new GridBagConstraints();
/* 339 */     gridBagConstraints.gridx = 0;
/* 340 */     gridBagConstraints.gridy = 1;
/* 341 */     gridBagConstraints.gridwidth = 4;
/* 342 */     gridBagConstraints.anchor = 17;
/* 343 */     gridBagConstraints.insets = new Insets(2, 0, 2, 0);
/* 344 */     this.pnlClan.add(this.lblClanExtraInfo, gridBagConstraints);
/*     */     
/* 346 */     this.jLabel3.setText("Availability (CI)");
/* 347 */     gridBagConstraints = new GridBagConstraints();
/* 348 */     gridBagConstraints.gridx = 0;
/* 349 */     gridBagConstraints.gridy = 4;
/* 350 */     gridBagConstraints.anchor = 17;
/* 351 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 352 */     this.pnlClan.add(this.jLabel3, gridBagConstraints);
/*     */     
/* 354 */     this.jLabel2.setText("Availability (SW)");
/* 355 */     gridBagConstraints = new GridBagConstraints();
/* 356 */     gridBagConstraints.gridx = 0;
/* 357 */     gridBagConstraints.gridy = 3;
/* 358 */     gridBagConstraints.anchor = 17;
/* 359 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 360 */     this.pnlClan.add(this.jLabel2, gridBagConstraints);
/*     */     
/* 362 */     this.jLabel1.setText("Availability (AOW/SL)");
/* 363 */     gridBagConstraints = new GridBagConstraints();
/* 364 */     gridBagConstraints.gridx = 0;
/* 365 */     gridBagConstraints.gridy = 2;
/* 366 */     gridBagConstraints.anchor = 17;
/* 367 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 368 */     this.pnlClan.add(this.jLabel1, gridBagConstraints);
/*     */     
/* 370 */     this.lblClanACCI.setText("X");
/* 371 */     gridBagConstraints = new GridBagConstraints();
/* 372 */     gridBagConstraints.gridx = 1;
/* 373 */     gridBagConstraints.gridy = 4;
/* 374 */     gridBagConstraints.anchor = 17;
/* 375 */     gridBagConstraints.insets = new Insets(0, 2, 0, 6);
/* 376 */     this.pnlClan.add(this.lblClanACCI, gridBagConstraints);
/*     */     
/* 378 */     this.lblClanACSW.setText("X");
/* 379 */     gridBagConstraints = new GridBagConstraints();
/* 380 */     gridBagConstraints.gridx = 1;
/* 381 */     gridBagConstraints.gridy = 3;
/* 382 */     gridBagConstraints.anchor = 17;
/* 383 */     gridBagConstraints.insets = new Insets(0, 2, 0, 6);
/* 384 */     this.pnlClan.add(this.lblClanACSW, gridBagConstraints);
/*     */     
/* 386 */     this.lblClanACSL.setText("X");
/* 387 */     gridBagConstraints = new GridBagConstraints();
/* 388 */     gridBagConstraints.gridx = 1;
/* 389 */     gridBagConstraints.gridy = 2;
/* 390 */     gridBagConstraints.anchor = 17;
/* 391 */     gridBagConstraints.insets = new Insets(0, 2, 0, 6);
/* 392 */     this.pnlClan.add(this.lblClanACSL, gridBagConstraints);
/*     */     
/* 394 */     this.jLabel9.setText("Reintroduction");
/* 395 */     gridBagConstraints = new GridBagConstraints();
/* 396 */     gridBagConstraints.gridx = 2;
/* 397 */     gridBagConstraints.gridy = 4;
/* 398 */     gridBagConstraints.anchor = 13;
/* 399 */     gridBagConstraints.insets = new Insets(0, 6, 0, 2);
/* 400 */     this.pnlClan.add(this.jLabel9, gridBagConstraints);
/*     */     
/* 402 */     this.jLabel8.setText("Extinction");
/* 403 */     gridBagConstraints = new GridBagConstraints();
/* 404 */     gridBagConstraints.gridx = 2;
/* 405 */     gridBagConstraints.gridy = 3;
/* 406 */     gridBagConstraints.anchor = 13;
/* 407 */     gridBagConstraints.insets = new Insets(0, 6, 0, 2);
/* 408 */     this.pnlClan.add(this.jLabel8, gridBagConstraints);
/*     */     
/* 410 */     this.jLabel7.setText("Introduction");
/* 411 */     gridBagConstraints = new GridBagConstraints();
/* 412 */     gridBagConstraints.gridx = 2;
/* 413 */     gridBagConstraints.gridy = 2;
/* 414 */     gridBagConstraints.anchor = 13;
/* 415 */     gridBagConstraints.insets = new Insets(0, 6, 0, 2);
/* 416 */     this.pnlClan.add(this.jLabel7, gridBagConstraints);
/*     */     
/* 418 */     this.lblClanReIntro.setText("9999 (TH)");
/* 419 */     gridBagConstraints = new GridBagConstraints();
/* 420 */     gridBagConstraints.gridx = 3;
/* 421 */     gridBagConstraints.gridy = 4;
/* 422 */     gridBagConstraints.anchor = 17;
/* 423 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 424 */     this.pnlClan.add(this.lblClanReIntro, gridBagConstraints);
/*     */     
/* 426 */     this.lblClanExtinct.setText("9999 (TH)");
/* 427 */     gridBagConstraints = new GridBagConstraints();
/* 428 */     gridBagConstraints.gridx = 3;
/* 429 */     gridBagConstraints.gridy = 3;
/* 430 */     gridBagConstraints.anchor = 17;
/* 431 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 432 */     this.pnlClan.add(this.lblClanExtinct, gridBagConstraints);
/*     */     
/* 434 */     this.lblClanIntro.setText("9999 (TH)");
/* 435 */     gridBagConstraints = new GridBagConstraints();
/* 436 */     gridBagConstraints.gridx = 3;
/* 437 */     gridBagConstraints.gridy = 2;
/* 438 */     gridBagConstraints.anchor = 17;
/* 439 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 440 */     this.pnlClan.add(this.lblClanIntro, gridBagConstraints);
/*     */     
/* 442 */     gridBagConstraints = new GridBagConstraints();
/* 443 */     gridBagConstraints.gridx = 0;
/* 444 */     gridBagConstraints.gridy = 10;
/* 445 */     gridBagConstraints.gridwidth = 3;
/* 446 */     gridBagConstraints.fill = 2;
/* 447 */     gridBagConstraints.anchor = 17;
/* 448 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 449 */     getContentPane().add(this.pnlClan, gridBagConstraints);
/*     */     
/* 451 */     this.pnlInnerSphere.setLayout(new java.awt.GridBagLayout());
/*     */     
/* 453 */     this.jLabel18.setText("Availability (AOW/SL)");
/* 454 */     gridBagConstraints = new GridBagConstraints();
/* 455 */     gridBagConstraints.gridx = 0;
/* 456 */     gridBagConstraints.gridy = 2;
/* 457 */     gridBagConstraints.anchor = 17;
/* 458 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 459 */     this.pnlInnerSphere.add(this.jLabel18, gridBagConstraints);
/*     */     
/* 461 */     this.lblISACSL.setText("X");
/* 462 */     gridBagConstraints = new GridBagConstraints();
/* 463 */     gridBagConstraints.gridx = 1;
/* 464 */     gridBagConstraints.gridy = 2;
/* 465 */     gridBagConstraints.anchor = 17;
/* 466 */     gridBagConstraints.insets = new Insets(0, 2, 0, 6);
/* 467 */     this.pnlInnerSphere.add(this.lblISACSL, gridBagConstraints);
/*     */     
/* 469 */     this.lblISACSW.setText("X");
/* 470 */     gridBagConstraints = new GridBagConstraints();
/* 471 */     gridBagConstraints.gridx = 1;
/* 472 */     gridBagConstraints.gridy = 3;
/* 473 */     gridBagConstraints.anchor = 17;
/* 474 */     gridBagConstraints.insets = new Insets(0, 2, 0, 6);
/* 475 */     this.pnlInnerSphere.add(this.lblISACSW, gridBagConstraints);
/*     */     
/* 477 */     this.lblISACCI.setText("X");
/* 478 */     gridBagConstraints = new GridBagConstraints();
/* 479 */     gridBagConstraints.gridx = 1;
/* 480 */     gridBagConstraints.gridy = 4;
/* 481 */     gridBagConstraints.anchor = 17;
/* 482 */     gridBagConstraints.insets = new Insets(0, 2, 0, 6);
/* 483 */     this.pnlInnerSphere.add(this.lblISACCI, gridBagConstraints);
/*     */     
/* 485 */     this.jLabel24.setText("Introduction");
/* 486 */     gridBagConstraints = new GridBagConstraints();
/* 487 */     gridBagConstraints.gridx = 2;
/* 488 */     gridBagConstraints.gridy = 2;
/* 489 */     gridBagConstraints.anchor = 13;
/* 490 */     gridBagConstraints.insets = new Insets(0, 6, 0, 2);
/* 491 */     this.pnlInnerSphere.add(this.jLabel24, gridBagConstraints);
/*     */     
/* 493 */     this.jLabel25.setText("Extinction");
/* 494 */     gridBagConstraints = new GridBagConstraints();
/* 495 */     gridBagConstraints.gridx = 2;
/* 496 */     gridBagConstraints.gridy = 3;
/* 497 */     gridBagConstraints.anchor = 13;
/* 498 */     gridBagConstraints.insets = new Insets(0, 6, 0, 2);
/* 499 */     this.pnlInnerSphere.add(this.jLabel25, gridBagConstraints);
/*     */     
/* 501 */     this.jLabel26.setText("Reintroduction");
/* 502 */     gridBagConstraints = new GridBagConstraints();
/* 503 */     gridBagConstraints.gridx = 2;
/* 504 */     gridBagConstraints.gridy = 4;
/* 505 */     gridBagConstraints.anchor = 13;
/* 506 */     gridBagConstraints.insets = new Insets(0, 6, 0, 2);
/* 507 */     this.pnlInnerSphere.add(this.jLabel26, gridBagConstraints);
/*     */     
/* 509 */     this.lblISIntro.setText("9999 (TH)");
/* 510 */     gridBagConstraints = new GridBagConstraints();
/* 511 */     gridBagConstraints.gridx = 3;
/* 512 */     gridBagConstraints.gridy = 2;
/* 513 */     gridBagConstraints.anchor = 17;
/* 514 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 515 */     this.pnlInnerSphere.add(this.lblISIntro, gridBagConstraints);
/*     */     
/* 517 */     this.lblISExtinct.setText("9999 (TH)");
/* 518 */     gridBagConstraints = new GridBagConstraints();
/* 519 */     gridBagConstraints.gridx = 3;
/* 520 */     gridBagConstraints.gridy = 3;
/* 521 */     gridBagConstraints.anchor = 17;
/* 522 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 523 */     this.pnlInnerSphere.add(this.lblISExtinct, gridBagConstraints);
/*     */     
/* 525 */     this.jLabel20.setText("Availability (CI)");
/* 526 */     gridBagConstraints = new GridBagConstraints();
/* 527 */     gridBagConstraints.gridx = 0;
/* 528 */     gridBagConstraints.gridy = 4;
/* 529 */     gridBagConstraints.anchor = 17;
/* 530 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 531 */     this.pnlInnerSphere.add(this.jLabel20, gridBagConstraints);
/*     */     
/* 533 */     this.jLabel19.setText("Availability (SW)");
/* 534 */     gridBagConstraints = new GridBagConstraints();
/* 535 */     gridBagConstraints.gridx = 0;
/* 536 */     gridBagConstraints.gridy = 3;
/* 537 */     gridBagConstraints.anchor = 17;
/* 538 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 539 */     this.pnlInnerSphere.add(this.jLabel19, gridBagConstraints);
/*     */     
/* 541 */     this.lblISReIntro.setText("9999 (TH)");
/* 542 */     gridBagConstraints = new GridBagConstraints();
/* 543 */     gridBagConstraints.gridx = 3;
/* 544 */     gridBagConstraints.gridy = 4;
/* 545 */     gridBagConstraints.anchor = 17;
/* 546 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 547 */     this.pnlInnerSphere.add(this.lblISReIntro, gridBagConstraints);
/*     */     
/* 549 */     this.lblISExtraInfo.setText("Status: R&D Start Date: 9999 (CJF), Prototype: 9999 (CJF)");
/* 550 */     gridBagConstraints = new GridBagConstraints();
/* 551 */     gridBagConstraints.gridx = 0;
/* 552 */     gridBagConstraints.gridy = 1;
/* 553 */     gridBagConstraints.gridwidth = 4;
/* 554 */     gridBagConstraints.anchor = 17;
/* 555 */     gridBagConstraints.insets = new Insets(2, 0, 2, 0);
/* 556 */     this.pnlInnerSphere.add(this.lblISExtraInfo, gridBagConstraints);
/*     */     
/* 558 */     this.jLabel16.setText("Inner Sphere Availability");
/* 559 */     gridBagConstraints = new GridBagConstraints();
/* 560 */     gridBagConstraints.gridx = 0;
/* 561 */     gridBagConstraints.gridy = 0;
/* 562 */     gridBagConstraints.gridwidth = 4;
/* 563 */     gridBagConstraints.anchor = 17;
/* 564 */     this.pnlInnerSphere.add(this.jLabel16, gridBagConstraints);
/*     */     
/* 566 */     gridBagConstraints = new GridBagConstraints();
/* 567 */     gridBagConstraints.gridx = 0;
/* 568 */     gridBagConstraints.gridy = 8;
/* 569 */     gridBagConstraints.gridwidth = 3;
/* 570 */     gridBagConstraints.fill = 2;
/* 571 */     gridBagConstraints.anchor = 17;
/* 572 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 573 */     getContentPane().add(this.pnlInnerSphere, gridBagConstraints);
/* 574 */     gridBagConstraints = new GridBagConstraints();
/* 575 */     gridBagConstraints.gridx = 0;
/* 576 */     gridBagConstraints.gridy = 11;
/* 577 */     gridBagConstraints.gridwidth = 3;
/* 578 */     gridBagConstraints.fill = 2;
/* 579 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 580 */     getContentPane().add(this.jSeparator4, gridBagConstraints);
/*     */     
/* 582 */     this.jLabel5.setText("Book Reference:");
/* 583 */     gridBagConstraints = new GridBagConstraints();
/* 584 */     gridBagConstraints.gridx = 0;
/* 585 */     gridBagConstraints.gridy = 12;
/* 586 */     gridBagConstraints.anchor = 17;
/* 587 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 588 */     getContentPane().add(this.jLabel5, gridBagConstraints);
/*     */     
/* 590 */     this.lblBookRef.setText("jLabel15");
/* 591 */     gridBagConstraints = new GridBagConstraints();
/* 592 */     gridBagConstraints.gridx = 1;
/* 593 */     gridBagConstraints.gridy = 12;
/* 594 */     gridBagConstraints.gridwidth = 2;
/* 595 */     gridBagConstraints.fill = 2;
/* 596 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/* 597 */     getContentPane().add(this.lblBookRef, gridBagConstraints);
/*     */     
/* 599 */     pack(); }
/*     */   
/*     */   private JLabel lblClanACSW;
/*     */   
/* 603 */   private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) { dispose(); }
/*     */   
/*     */   private JLabel lblClanExtinct;
/*     */   private JLabel lblClanExtraInfo;
/*     */   private JLabel lblClanIntro;
/*     */   private JLabel lblClanReIntro;
/*     */   private JLabel lblCost;
/*     */   private JLabel lblISACCI;
/*     */   private JLabel lblISACSL;
/*     */   private JLabel lblISACSW;
/*     */   private JLabel lblISExtinct;
/*     */   private JLabel lblISExtraInfo;
/*     */   private JLabel lblISIntro;
/*     */   private JLabel lblISReIntro;
/*     */   private JLabel lblName;
/*     */   private JLabel lblRulesBM;
/*     */   private JLabel lblRulesIM;
/*     */   private JLabel lblTechRating;
/*     */   private JLabel lblTonnage;
/*     */   private JPanel pnlClan;
/*     */   private JPanel pnlInnerSphere;
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgPlaceableInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */