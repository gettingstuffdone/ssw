/*     */ package ssw.gui;
/*     */ 
/*     */ import common.CommonTools;
/*     */ import components.Ammunition;
/*     */ import components.AvailableCode;
/*     */ import components.abPlaceable;
/*     */ import components.ifWeapon;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSeparator;
/*     */ 
/*     */ public class dlgWeaponInfo extends javax.swing.JDialog
/*     */ {
/*     */   ifMechForm Parent;
/*     */   private JButton btnClose;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel12;
/*     */   private JLabel jLabel14;
/*     */   private JLabel jLabel15;
/*     */   private JLabel jLabel18;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel20;
/*     */   private JLabel jLabel21;
/*     */   private JLabel jLabel23;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JLabel jLabel6;
/*     */   private JLabel jLabel7;
/*     */   private JLabel jLabel8;
/*     */   private JLabel jLabel9;
/*     */   
/*     */   public dlgWeaponInfo(java.awt.Frame parent, boolean modal)
/*     */   {
/*  39 */     super(parent, modal);
/*  40 */     initComponents();
/*     */     
/*  42 */     this.Parent = ((ifMechForm)parent);
/*  43 */     SetState();
/*  44 */     pack(); }
/*     */   
/*     */   private JSeparator jSeparator1;
/*     */   private JSeparator jSeparator2;
/*     */   
/*  49 */   private void SetState() { ifWeapon w = null;
/*  50 */     Ammunition a = null;
/*  51 */     String restrict = "";
/*  52 */     AvailableCode AC = this.Parent.GetCurItem().GetAvailability();
/*  53 */     if ((this.Parent.GetCurItem() instanceof Ammunition)) {
/*  54 */       setTitle("Ammunition Information");
/*  55 */       a = (Ammunition)this.Parent.GetCurItem();
/*  56 */       this.lblName.setText(a.ActualName());
/*  57 */       this.lblType.setText("Ammo");
/*  58 */       this.lblHeat.setText("--");
/*  59 */       if (a.GetWeaponClass() == 2) {
/*  60 */         this.lblDamage.setText(a.GetDamageShort() + "/msl");
/*  61 */       } else if (a.GetWeaponClass() == 3) {
/*  62 */         this.lblDamage.setText(a.GetDamageShort() + "A");
/*  63 */       } else if ((a.GetDamageShort() == a.GetDamageMedium()) && (a.GetDamageShort() == a.GetDamageLong())) {
/*  64 */         this.lblDamage.setText("" + a.GetDamageShort());
/*     */       } else {
/*  66 */         this.lblDamage.setText(a.GetDamageShort() + "/" + a.GetDamageMedium() + "/" + a.GetDamageLong());
/*     */       }
/*  68 */       if (a.GetLongRange() < 1) {
/*  69 */         if (a.GetMediumRange() < 1) {
/*  70 */           if (a.GetWeaponClass() == 3) {
/*  71 */             this.lblRange.setText(a.GetShortRange() + " boards");
/*     */           } else {
/*  73 */             this.lblRange.setText(a.GetShortRange() + "");
/*     */           }
/*     */         } else {
/*  76 */           this.lblRange.setText(a.GetMinRange() + "/" + a.GetShortRange() + "/" + a.GetMediumRange() + "/-");
/*     */         }
/*     */       } else {
/*  79 */         this.lblRange.setText(a.GetMinRange() + "/" + a.GetShortRange() + "/" + a.GetMediumRange() + "/" + a.GetLongRange());
/*     */       }
/*  81 */       this.lblAmmo.setText("" + a.GetLotSize());
/*  82 */       this.lblToHit.setText(a.GetToHitShort() + "/" + a.GetToHitMedium() + "/" + a.GetToHitLong());
/*  83 */       this.lblFCSClass.setText(components.ifMissileGuidance.FCS_NAMES[a.GetFCSType()]);
/*  84 */       this.lblSpecials.setText("--");
/*  85 */       this.lblTonnage.setText("" + a.GetTonnage());
/*  86 */       this.lblCrits.setText("" + a.NumCrits());
/*  87 */       this.lblCost.setText("" + CommonTools.RoundFractionalCost(a.GetCost()));
/*  88 */       this.lblBV.setText(CommonTools.GetAggregateReportBV(a));
/*     */       
/*  90 */       if (!a.CanAllocHD()) {
/*  91 */         restrict = restrict + "No Head, ";
/*     */       }
/*  93 */       if (!a.CanAllocCT()) {
/*  94 */         restrict = restrict + "No Center Torso, ";
/*     */       }
/*  96 */       if (!a.CanAllocTorso()) {
/*  97 */         restrict = restrict + "No Side Torsos, ";
/*     */       }
/*  99 */       if (!a.CanAllocArms()) {
/* 100 */         restrict = restrict + "No Arms, ";
/*     */       }
/* 102 */       if (!a.CanAllocLegs()) {
/* 103 */         restrict = restrict + "No Legs, ";
/*     */       }
/* 105 */       if (a.CanSplit()) {
/* 106 */         restrict = restrict + "Can Split, ";
/*     */       }
/*     */     } else {
/* 109 */       setTitle("Weapon Information");
/* 110 */       w = (ifWeapon)this.Parent.GetCurItem();
/* 111 */       this.lblName.setText(((abPlaceable)w).ActualName());
/* 112 */       this.lblType.setText(w.GetType());
/* 113 */       this.lblHeat.setText("" + w.GetHeat());
/* 114 */       if (w.GetWeaponClass() == 2) {
/* 115 */         this.lblDamage.setText(w.GetDamageShort() + "/msl");
/* 116 */       } else if (w.GetWeaponClass() == 3) {
/* 117 */         this.lblDamage.setText(w.GetDamageShort() + "A");
/* 118 */       } else if ((w.GetDamageShort() == w.GetDamageMedium()) && (w.GetDamageShort() == w.GetDamageLong())) {
/* 119 */         if ((w.IsUltra()) || (w.IsRotary())) {
/* 120 */           this.lblDamage.setText(w.GetDamageShort() + "/shot");
/*     */         } else {
/* 122 */           this.lblDamage.setText("" + w.GetDamageShort());
/*     */         }
/*     */       } else {
/* 125 */         this.lblDamage.setText(w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong());
/*     */       }
/* 127 */       if (w.GetRangeLong() < 1) {
/* 128 */         if (w.GetRangeMedium() < 1) {
/* 129 */           if (w.GetWeaponClass() == 3) {
/* 130 */             this.lblRange.setText(w.GetRangeShort() + " boards");
/*     */           } else {
/* 132 */             this.lblRange.setText(w.GetRangeShort() + "");
/*     */           }
/*     */         } else {
/* 135 */           this.lblRange.setText(w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/-");
/*     */         }
/*     */       } else {
/* 138 */         this.lblRange.setText(w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/" + w.GetRangeLong());
/*     */       }
/* 140 */       if (w.HasAmmo()) {
/* 141 */         this.lblAmmo.setText("" + w.GetAmmoLotSize());
/*     */       } else {
/* 143 */         this.lblAmmo.setText("--");
/*     */       }
/* 145 */       String tohit = "";
/* 146 */       if (w.GetToHitShort() >= 0) {
/* 147 */         tohit = tohit + "+";
/*     */       }
/* 149 */       tohit = tohit + w.GetToHitShort() + "/";
/* 150 */       if (w.GetToHitMedium() >= 0) {
/* 151 */         tohit = tohit + "+";
/*     */       }
/* 153 */       tohit = tohit + w.GetToHitMedium() + "/";
/* 154 */       if (w.GetToHitLong() >= 0) {
/* 155 */         tohit = tohit + "+";
/*     */       }
/* 157 */       tohit = tohit + w.GetToHitLong();
/* 158 */       this.lblToHit.setText(tohit);
/* 159 */       this.lblFCSClass.setText(components.ifMissileGuidance.FCS_NAMES[w.GetFCSType()]);
/* 160 */       this.lblSpecials.setText(w.GetSpecials());
/* 161 */       this.lblTonnage.setText("" + ((abPlaceable)w).GetTonnage());
/* 162 */       this.lblCrits.setText("" + ((abPlaceable)w).NumCrits());
/* 163 */       this.lblCost.setText("" + ((abPlaceable)w).GetCost());
/* 164 */       this.lblBV.setText(CommonTools.GetAggregateReportBV((abPlaceable)w));
/*     */       
/* 166 */       if (!((abPlaceable)w).CanAllocHD()) {
/* 167 */         restrict = restrict + "No Head, ";
/*     */       }
/* 169 */       if (!((abPlaceable)w).CanAllocCT()) {
/* 170 */         restrict = restrict + "No Center Torso, ";
/*     */       }
/* 172 */       if (!((abPlaceable)w).CanAllocTorso()) {
/* 173 */         restrict = restrict + "No Side Torsos, ";
/*     */       }
/* 175 */       if (!((abPlaceable)w).CanAllocArms()) {
/* 176 */         restrict = restrict + "No Arms, ";
/*     */       }
/* 178 */       if (!((abPlaceable)w).CanAllocLegs()) {
/* 179 */         restrict = restrict + "No Legs, ";
/*     */       }
/* 181 */       if (((abPlaceable)w).CanSplit()) {
/* 182 */         restrict = restrict + "Can Split, ";
/*     */       }
/* 184 */       if (w.OmniRestrictActuators()) {
/* 185 */         restrict = restrict + "Omni Actuator Restricted";
/*     */       }
/*     */     }
/*     */     
/* 189 */     switch (AC.GetTechBase()) {
/*     */     case 0: 
/* 191 */       this.pnlClanAvailability.setVisible(false);
/* 192 */       break;
/*     */     case 1: 
/* 194 */       this.pnlISAvailability.setVisible(false);
/*     */     }
/*     */     
/*     */     
/* 198 */     this.lblISTechRating.setText("" + AC.GetISTechRating());
/* 199 */     this.lblISAVSL.setText("" + AC.GetISSLCode());
/* 200 */     this.lblISAVSW.setText("" + AC.GetISSWCode());
/* 201 */     this.lblISAVCI.setText("" + AC.GetISCICode());
/* 202 */     this.lblISIntro.setText(AC.GetISIntroDate() + " (" + AC.GetISIntroFaction() + ")");
/* 203 */     if (AC.WentExtinctIS()) {
/* 204 */       this.lblISExtinct.setText("" + AC.GetISExtinctDate());
/*     */     } else {
/* 206 */       this.lblISExtinct.setText("--");
/*     */     }
/* 208 */     if (AC.WasReIntrodIS()) {
/* 209 */       this.lblISReIntro.setText(AC.GetISReIntroDate() + " (" + AC.GetISReIntroFaction() + ")");
/*     */     } else {
/* 211 */       this.lblISReIntro.setText("--");
/*     */     }
/* 213 */     if (AC.Is_ISPrototype()) {
/* 214 */       String temp = "Status: R&D Start Date: " + AC.GetISRandDStartDate() + " (" + AC.GetISRandDFaction() + "), ";
/* 215 */       temp = temp + "Prototype: " + AC.GetISPrototypeDate() + " (" + AC.GetISPrototypeFaction() + ")";
/* 216 */       this.lblISExtraInfo.setText(temp);
/*     */     } else {
/* 218 */       this.lblISExtraInfo.setText("Status: Production Equipment");
/*     */     }
/*     */     
/* 221 */     this.lblClanTechRating.setText("" + AC.GetCLTechRating());
/* 222 */     this.lblClanAVSL.setText("" + AC.GetCLSLCode());
/* 223 */     this.lblClanAVSW.setText("" + AC.GetCLSWCode());
/* 224 */     this.lblClanAVCI.setText("" + AC.GetCLCICode());
/* 225 */     this.lblClanIntro.setText(AC.GetCLIntroDate() + " (" + AC.GetCLIntroFaction() + ")");
/* 226 */     if (AC.WentExtinctCL()) {
/* 227 */       this.lblClanExtinct.setText("" + AC.GetCLExtinctDate());
/*     */     } else {
/* 229 */       this.lblClanExtinct.setText("--");
/*     */     }
/* 231 */     if (AC.WasReIntrodCL()) {
/* 232 */       this.lblClanReIntro.setText(AC.GetCLReIntroDate() + " (" + AC.GetCLReIntroFaction() + ")");
/*     */     } else {
/* 234 */       this.lblClanReIntro.setText("--");
/*     */     }
/* 236 */     if (AC.Is_CLPrototype()) {
/* 237 */       String temp = "Status: R&D Start Date: " + AC.GetCLRandDStartDate() + " (" + AC.GetCLRandDFaction() + "), ";
/* 238 */       temp = temp + "Prototype: " + AC.GetCLPrototypeDate() + " (" + AC.GetCLPrototypeFaction() + ")";
/* 239 */       this.lblClanExtraInfo.setText(temp);
/*     */     } else {
/* 241 */       this.lblClanExtraInfo.setText("Status: Production Equipment");
/*     */     }
/*     */     
/* 244 */     this.lblRulesBM.setText(CommonTools.GetRulesLevelString(AC.GetRulesLevel_BM()));
/* 245 */     this.lblRulesIM.setText(CommonTools.GetRulesLevelString(AC.GetRulesLevel_IM()));
/*     */     
/* 247 */     if (restrict.length() > 0) {
/* 248 */       if (restrict.endsWith(", ")) {
/* 249 */         restrict = restrict.substring(0, restrict.length() - 2);
/*     */       }
/* 251 */       this.lblMountingRestrictions.setText(restrict);
/*     */     } else {
/* 253 */       this.lblMountingRestrictions.setText("None");
/*     */     }
/* 255 */     this.lblBookRef.setText(this.Parent.GetCurItem().BookReference()); }
/*     */   
/*     */   private JSeparator jSeparator3;
/*     */   private JSeparator jSeparator4;
/*     */   private JSeparator jSeparator5;
/*     */   private JLabel lblAmmo;
/*     */   private JLabel lblBV;
/*     */   private JLabel lblBookRef;
/*     */   private JLabel lblClanAVCI;
/*     */   private JLabel lblClanAVSL;
/*     */   private JLabel lblClanAVSW;
/*     */   
/* 267 */   private void initComponents() { this.lblInfoHeat = new JLabel();
/* 268 */     this.lblInfoRange = new JLabel();
/* 269 */     this.lblInfoAmmo = new JLabel();
/* 270 */     this.lblInfoTonnage = new JLabel();
/* 271 */     this.lblInfoCrits = new JLabel();
/* 272 */     this.lblInfoSpecial = new JLabel();
/* 273 */     this.lblName = new JLabel();
/* 274 */     this.lblHeat = new JLabel();
/* 275 */     this.lblRange = new JLabel();
/* 276 */     this.lblAmmo = new JLabel();
/* 277 */     this.lblTonnage = new JLabel();
/* 278 */     this.lblCrits = new JLabel();
/* 279 */     this.lblSpecials = new JLabel();
/* 280 */     this.lblInfoType = new JLabel();
/* 281 */     this.lblType = new JLabel();
/* 282 */     this.lblInfoDamage = new JLabel();
/* 283 */     this.lblDamage = new JLabel();
/* 284 */     this.btnClose = new JButton();
/* 285 */     this.jSeparator1 = new JSeparator();
/* 286 */     this.lblInfoCost = new JLabel();
/* 287 */     this.lblInfoBV = new JLabel();
/* 288 */     this.lblCost = new JLabel();
/* 289 */     this.lblBV = new JLabel();
/* 290 */     this.jSeparator2 = new JSeparator();
/* 291 */     this.jLabel1 = new JLabel();
/* 292 */     this.jLabel2 = new JLabel();
/* 293 */     this.lblRulesBM = new JLabel();
/* 294 */     this.lblRulesIM = new JLabel();
/* 295 */     this.pnlClanAvailability = new JPanel();
/* 296 */     this.lblInfoAVCI = new JLabel();
/* 297 */     this.lblInfoAVSW = new JLabel();
/* 298 */     this.lblInfoAVSL = new JLabel();
/* 299 */     this.lblClanAVSL = new JLabel();
/* 300 */     this.lblClanAVSW = new JLabel();
/* 301 */     this.lblClanAVCI = new JLabel();
/* 302 */     this.lblInfoReIntro = new JLabel();
/* 303 */     this.lblInfoExtinct = new JLabel();
/* 304 */     this.lblInfoIntro = new JLabel();
/* 305 */     this.lblClanReIntro = new JLabel();
/* 306 */     this.lblClanExtinct = new JLabel();
/* 307 */     this.lblClanIntro = new JLabel();
/* 308 */     this.jLabel3 = new JLabel();
/* 309 */     this.lblClanExtraInfo = new JLabel();
/* 310 */     this.jLabel18 = new JLabel();
/* 311 */     this.lblClanTechRating = new JLabel();
/* 312 */     this.jSeparator3 = new JSeparator();
/* 313 */     this.pnlISAvailability = new JPanel();
/* 314 */     this.jLabel4 = new JLabel();
/* 315 */     this.lblISExtraInfo = new JLabel();
/* 316 */     this.jLabel6 = new JLabel();
/* 317 */     this.jLabel7 = new JLabel();
/* 318 */     this.jLabel8 = new JLabel();
/* 319 */     this.lblISAVSL = new JLabel();
/* 320 */     this.lblISAVSW = new JLabel();
/* 321 */     this.lblISAVCI = new JLabel();
/* 322 */     this.jLabel12 = new JLabel();
/* 323 */     this.lblISIntro = new JLabel();
/* 324 */     this.jLabel14 = new JLabel();
/* 325 */     this.jLabel15 = new JLabel();
/* 326 */     this.lblISReIntro = new JLabel();
/* 327 */     this.lblISExtinct = new JLabel();
/* 328 */     this.jLabel20 = new JLabel();
/* 329 */     this.lblISTechRating = new JLabel();
/* 330 */     this.jSeparator4 = new JSeparator();
/* 331 */     this.lblToHit = new JLabel();
/* 332 */     this.jLabel21 = new JLabel();
/* 333 */     this.lblFCSClass = new JLabel();
/* 334 */     this.jLabel23 = new JLabel();
/* 335 */     this.lblMountingRestrictions = new JLabel();
/* 336 */     this.jLabel5 = new JLabel();
/* 337 */     this.jSeparator5 = new JSeparator();
/* 338 */     this.jLabel9 = new JLabel();
/* 339 */     this.lblBookRef = new JLabel();
/*     */     
/* 341 */     setDefaultCloseOperation(2);
/* 342 */     getContentPane().setLayout(new java.awt.GridBagLayout());
/*     */     
/* 344 */     this.lblInfoHeat.setText("Heat");
/* 345 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 346 */     gridBagConstraints.gridx = 1;
/* 347 */     gridBagConstraints.gridy = 1;
/* 348 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/* 349 */     getContentPane().add(this.lblInfoHeat, gridBagConstraints);
/*     */     
/* 351 */     this.lblInfoRange.setText("Range");
/* 352 */     gridBagConstraints = new GridBagConstraints();
/* 353 */     gridBagConstraints.gridx = 3;
/* 354 */     gridBagConstraints.gridy = 1;
/* 355 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/* 356 */     getContentPane().add(this.lblInfoRange, gridBagConstraints);
/*     */     
/* 358 */     this.lblInfoAmmo.setText("Ammo");
/* 359 */     gridBagConstraints = new GridBagConstraints();
/* 360 */     gridBagConstraints.gridx = 4;
/* 361 */     gridBagConstraints.gridy = 1;
/* 362 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/* 363 */     getContentPane().add(this.lblInfoAmmo, gridBagConstraints);
/*     */     
/* 365 */     this.lblInfoTonnage.setText("Tonnage");
/* 366 */     gridBagConstraints = new GridBagConstraints();
/* 367 */     gridBagConstraints.gridx = 5;
/* 368 */     gridBagConstraints.gridy = 1;
/* 369 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/* 370 */     getContentPane().add(this.lblInfoTonnage, gridBagConstraints);
/*     */     
/* 372 */     this.lblInfoCrits.setText("Crits");
/* 373 */     gridBagConstraints = new GridBagConstraints();
/* 374 */     gridBagConstraints.gridx = 6;
/* 375 */     gridBagConstraints.gridy = 1;
/* 376 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/* 377 */     getContentPane().add(this.lblInfoCrits, gridBagConstraints);
/*     */     
/* 379 */     this.lblInfoSpecial.setText("Specials");
/* 380 */     gridBagConstraints = new GridBagConstraints();
/* 381 */     gridBagConstraints.gridx = 7;
/* 382 */     gridBagConstraints.gridy = 1;
/* 383 */     gridBagConstraints.insets = new Insets(4, 3, 0, 4);
/* 384 */     getContentPane().add(this.lblInfoSpecial, gridBagConstraints);
/*     */     
/* 386 */     this.lblName.setFont(new java.awt.Font("Dialog", 1, 14));
/* 387 */     this.lblName.setText("Hyper Assault Gauss 40");
/* 388 */     gridBagConstraints = new GridBagConstraints();
/* 389 */     gridBagConstraints.gridx = 0;
/* 390 */     gridBagConstraints.gridy = 0;
/* 391 */     gridBagConstraints.gridwidth = 8;
/* 392 */     gridBagConstraints.fill = 2;
/* 393 */     gridBagConstraints.anchor = 17;
/* 394 */     gridBagConstraints.insets = new Insets(4, 4, 0, 3);
/* 395 */     getContentPane().add(this.lblName, gridBagConstraints);
/*     */     
/* 397 */     this.lblHeat.setText("999");
/* 398 */     gridBagConstraints = new GridBagConstraints();
/* 399 */     gridBagConstraints.gridx = 1;
/* 400 */     gridBagConstraints.gridy = 2;
/* 401 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 402 */     getContentPane().add(this.lblHeat, gridBagConstraints);
/*     */     
/* 404 */     this.lblRange.setText("999/999/999/999");
/* 405 */     gridBagConstraints = new GridBagConstraints();
/* 406 */     gridBagConstraints.gridx = 3;
/* 407 */     gridBagConstraints.gridy = 2;
/* 408 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 409 */     getContentPane().add(this.lblRange, gridBagConstraints);
/*     */     
/* 411 */     this.lblAmmo.setText("999");
/* 412 */     gridBagConstraints = new GridBagConstraints();
/* 413 */     gridBagConstraints.gridx = 4;
/* 414 */     gridBagConstraints.gridy = 2;
/* 415 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 416 */     getContentPane().add(this.lblAmmo, gridBagConstraints);
/*     */     
/* 418 */     this.lblTonnage.setText("999.9");
/* 419 */     gridBagConstraints = new GridBagConstraints();
/* 420 */     gridBagConstraints.gridx = 5;
/* 421 */     gridBagConstraints.gridy = 2;
/* 422 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 423 */     getContentPane().add(this.lblTonnage, gridBagConstraints);
/*     */     
/* 425 */     this.lblCrits.setText("999");
/* 426 */     gridBagConstraints = new GridBagConstraints();
/* 427 */     gridBagConstraints.gridx = 6;
/* 428 */     gridBagConstraints.gridy = 2;
/* 429 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 430 */     getContentPane().add(this.lblCrits, gridBagConstraints);
/*     */     
/* 432 */     this.lblSpecials.setText("AI/H/X/C/F/C5/20");
/* 433 */     gridBagConstraints = new GridBagConstraints();
/* 434 */     gridBagConstraints.gridx = 7;
/* 435 */     gridBagConstraints.gridy = 2;
/* 436 */     gridBagConstraints.insets = new Insets(0, 3, 0, 4);
/* 437 */     getContentPane().add(this.lblSpecials, gridBagConstraints);
/*     */     
/* 439 */     this.lblInfoType.setText("Type");
/* 440 */     gridBagConstraints = new GridBagConstraints();
/* 441 */     gridBagConstraints.gridx = 0;
/* 442 */     gridBagConstraints.gridy = 1;
/* 443 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/* 444 */     getContentPane().add(this.lblInfoType, gridBagConstraints);
/*     */     
/* 446 */     this.lblType.setText("DB");
/* 447 */     gridBagConstraints = new GridBagConstraints();
/* 448 */     gridBagConstraints.gridx = 0;
/* 449 */     gridBagConstraints.gridy = 2;
/* 450 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 451 */     getContentPane().add(this.lblType, gridBagConstraints);
/*     */     
/* 453 */     this.lblInfoDamage.setText("Damage");
/* 454 */     gridBagConstraints = new GridBagConstraints();
/* 455 */     gridBagConstraints.gridx = 2;
/* 456 */     gridBagConstraints.gridy = 1;
/* 457 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/* 458 */     getContentPane().add(this.lblInfoDamage, gridBagConstraints);
/*     */     
/* 460 */     this.lblDamage.setText("999/999/999");
/* 461 */     gridBagConstraints = new GridBagConstraints();
/* 462 */     gridBagConstraints.gridx = 2;
/* 463 */     gridBagConstraints.gridy = 2;
/* 464 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 465 */     getContentPane().add(this.lblDamage, gridBagConstraints);
/*     */     
/* 467 */     this.btnClose.setText("Close");
/* 468 */     this.btnClose.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(java.awt.event.ActionEvent evt) {
/* 470 */         dlgWeaponInfo.this.btnCloseActionPerformed(evt);
/*     */       }
/* 472 */     });
/* 473 */     gridBagConstraints = new GridBagConstraints();
/* 474 */     gridBagConstraints.gridx = 0;
/* 475 */     gridBagConstraints.gridy = 16;
/* 476 */     gridBagConstraints.gridwidth = 8;
/* 477 */     gridBagConstraints.anchor = 13;
/* 478 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 479 */     getContentPane().add(this.btnClose, gridBagConstraints);
/*     */     
/* 481 */     this.jSeparator1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 482 */     gridBagConstraints = new GridBagConstraints();
/* 483 */     gridBagConstraints.gridx = 0;
/* 484 */     gridBagConstraints.gridy = 12;
/* 485 */     gridBagConstraints.gridwidth = 8;
/* 486 */     gridBagConstraints.fill = 2;
/* 487 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 488 */     getContentPane().add(this.jSeparator1, gridBagConstraints);
/*     */     
/* 490 */     this.lblInfoCost.setText("Cost");
/* 491 */     gridBagConstraints = new GridBagConstraints();
/* 492 */     gridBagConstraints.gridx = 5;
/* 493 */     gridBagConstraints.gridy = 4;
/* 494 */     gridBagConstraints.anchor = 13;
/* 495 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 496 */     getContentPane().add(this.lblInfoCost, gridBagConstraints);
/*     */     
/* 498 */     this.lblInfoBV.setText("BV");
/* 499 */     gridBagConstraints = new GridBagConstraints();
/* 500 */     gridBagConstraints.gridx = 5;
/* 501 */     gridBagConstraints.gridy = 5;
/* 502 */     gridBagConstraints.anchor = 13;
/* 503 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/* 504 */     getContentPane().add(this.lblInfoBV, gridBagConstraints);
/*     */     
/* 506 */     this.lblCost.setText("9999999");
/* 507 */     gridBagConstraints = new GridBagConstraints();
/* 508 */     gridBagConstraints.gridx = 6;
/* 509 */     gridBagConstraints.gridy = 4;
/* 510 */     gridBagConstraints.gridwidth = 2;
/* 511 */     gridBagConstraints.anchor = 17;
/* 512 */     gridBagConstraints.insets = new Insets(0, 3, 0, 4);
/* 513 */     getContentPane().add(this.lblCost, gridBagConstraints);
/*     */     
/* 515 */     this.lblBV.setText("999");
/* 516 */     gridBagConstraints = new GridBagConstraints();
/* 517 */     gridBagConstraints.gridx = 6;
/* 518 */     gridBagConstraints.gridy = 5;
/* 519 */     gridBagConstraints.gridwidth = 2;
/* 520 */     gridBagConstraints.anchor = 17;
/* 521 */     gridBagConstraints.insets = new Insets(0, 3, 0, 4);
/* 522 */     getContentPane().add(this.lblBV, gridBagConstraints);
/*     */     
/* 524 */     this.jSeparator2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 525 */     gridBagConstraints = new GridBagConstraints();
/* 526 */     gridBagConstraints.gridx = 0;
/* 527 */     gridBagConstraints.gridy = 7;
/* 528 */     gridBagConstraints.gridwidth = 8;
/* 529 */     gridBagConstraints.fill = 2;
/* 530 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 531 */     getContentPane().add(this.jSeparator2, gridBagConstraints);
/*     */     
/* 533 */     this.jLabel1.setText("Rules Level (BattleMech)");
/* 534 */     gridBagConstraints = new GridBagConstraints();
/* 535 */     gridBagConstraints.gridx = 0;
/* 536 */     gridBagConstraints.gridy = 8;
/* 537 */     gridBagConstraints.gridwidth = 2;
/* 538 */     gridBagConstraints.anchor = 17;
/* 539 */     gridBagConstraints.insets = new Insets(0, 4, 0, 2);
/* 540 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/* 542 */     this.jLabel2.setText("Rules Level (IndustrialMech)");
/* 543 */     gridBagConstraints = new GridBagConstraints();
/* 544 */     gridBagConstraints.gridx = 0;
/* 545 */     gridBagConstraints.gridy = 9;
/* 546 */     gridBagConstraints.gridwidth = 2;
/* 547 */     gridBagConstraints.anchor = 17;
/* 548 */     gridBagConstraints.insets = new Insets(0, 4, 0, 2);
/* 549 */     getContentPane().add(this.jLabel2, gridBagConstraints);
/*     */     
/* 551 */     this.lblRulesBM.setText("Tournament Legal");
/* 552 */     gridBagConstraints = new GridBagConstraints();
/* 553 */     gridBagConstraints.gridx = 2;
/* 554 */     gridBagConstraints.gridy = 8;
/* 555 */     gridBagConstraints.gridwidth = 3;
/* 556 */     gridBagConstraints.anchor = 17;
/* 557 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 558 */     getContentPane().add(this.lblRulesBM, gridBagConstraints);
/*     */     
/* 560 */     this.lblRulesIM.setText("Tournament Legal");
/* 561 */     gridBagConstraints = new GridBagConstraints();
/* 562 */     gridBagConstraints.gridx = 2;
/* 563 */     gridBagConstraints.gridy = 9;
/* 564 */     gridBagConstraints.gridwidth = 3;
/* 565 */     gridBagConstraints.anchor = 17;
/* 566 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 567 */     getContentPane().add(this.lblRulesIM, gridBagConstraints);
/*     */     
/* 569 */     this.pnlClanAvailability.setLayout(new java.awt.GridBagLayout());
/*     */     
/* 571 */     this.lblInfoAVCI.setText("Availability (CI)");
/* 572 */     gridBagConstraints = new GridBagConstraints();
/* 573 */     gridBagConstraints.gridx = 0;
/* 574 */     gridBagConstraints.gridy = 5;
/* 575 */     gridBagConstraints.anchor = 17;
/* 576 */     gridBagConstraints.insets = new Insets(0, 0, 0, 5);
/* 577 */     this.pnlClanAvailability.add(this.lblInfoAVCI, gridBagConstraints);
/*     */     
/* 579 */     this.lblInfoAVSW.setText("Availability (SW)");
/* 580 */     gridBagConstraints = new GridBagConstraints();
/* 581 */     gridBagConstraints.gridx = 0;
/* 582 */     gridBagConstraints.gridy = 4;
/* 583 */     gridBagConstraints.anchor = 17;
/* 584 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 585 */     this.pnlClanAvailability.add(this.lblInfoAVSW, gridBagConstraints);
/*     */     
/* 587 */     this.lblInfoAVSL.setText("Availability (AoW/SL)");
/* 588 */     gridBagConstraints = new GridBagConstraints();
/* 589 */     gridBagConstraints.gridx = 0;
/* 590 */     gridBagConstraints.gridy = 3;
/* 591 */     gridBagConstraints.anchor = 17;
/* 592 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 593 */     this.pnlClanAvailability.add(this.lblInfoAVSL, gridBagConstraints);
/*     */     
/* 595 */     this.lblClanAVSL.setText("X");
/* 596 */     gridBagConstraints = new GridBagConstraints();
/* 597 */     gridBagConstraints.gridx = 1;
/* 598 */     gridBagConstraints.gridy = 3;
/* 599 */     gridBagConstraints.anchor = 17;
/* 600 */     gridBagConstraints.insets = new Insets(0, 2, 0, 4);
/* 601 */     this.pnlClanAvailability.add(this.lblClanAVSL, gridBagConstraints);
/*     */     
/* 603 */     this.lblClanAVSW.setText("X");
/* 604 */     gridBagConstraints = new GridBagConstraints();
/* 605 */     gridBagConstraints.gridx = 1;
/* 606 */     gridBagConstraints.gridy = 4;
/* 607 */     gridBagConstraints.anchor = 17;
/* 608 */     gridBagConstraints.insets = new Insets(0, 2, 0, 4);
/* 609 */     this.pnlClanAvailability.add(this.lblClanAVSW, gridBagConstraints);
/*     */     
/* 611 */     this.lblClanAVCI.setText("X");
/* 612 */     gridBagConstraints = new GridBagConstraints();
/* 613 */     gridBagConstraints.gridx = 1;
/* 614 */     gridBagConstraints.gridy = 5;
/* 615 */     gridBagConstraints.anchor = 17;
/* 616 */     gridBagConstraints.insets = new Insets(0, 2, 0, 4);
/* 617 */     this.pnlClanAvailability.add(this.lblClanAVCI, gridBagConstraints);
/*     */     
/* 619 */     this.lblInfoReIntro.setText("Reintroduction");
/* 620 */     gridBagConstraints = new GridBagConstraints();
/* 621 */     gridBagConstraints.gridx = 2;
/* 622 */     gridBagConstraints.gridy = 5;
/* 623 */     gridBagConstraints.anchor = 13;
/* 624 */     gridBagConstraints.insets = new Insets(0, 4, 0, 2);
/* 625 */     this.pnlClanAvailability.add(this.lblInfoReIntro, gridBagConstraints);
/*     */     
/* 627 */     this.lblInfoExtinct.setText("Extinction");
/* 628 */     gridBagConstraints = new GridBagConstraints();
/* 629 */     gridBagConstraints.gridx = 2;
/* 630 */     gridBagConstraints.gridy = 4;
/* 631 */     gridBagConstraints.anchor = 13;
/* 632 */     gridBagConstraints.insets = new Insets(0, 4, 0, 2);
/* 633 */     this.pnlClanAvailability.add(this.lblInfoExtinct, gridBagConstraints);
/*     */     
/* 635 */     this.lblInfoIntro.setText("Introduction");
/* 636 */     gridBagConstraints = new GridBagConstraints();
/* 637 */     gridBagConstraints.gridx = 2;
/* 638 */     gridBagConstraints.gridy = 3;
/* 639 */     gridBagConstraints.anchor = 13;
/* 640 */     gridBagConstraints.insets = new Insets(0, 4, 0, 2);
/* 641 */     this.pnlClanAvailability.add(this.lblInfoIntro, gridBagConstraints);
/*     */     
/* 643 */     this.lblClanReIntro.setText("9999 (TH)");
/* 644 */     gridBagConstraints = new GridBagConstraints();
/* 645 */     gridBagConstraints.gridx = 3;
/* 646 */     gridBagConstraints.gridy = 5;
/* 647 */     gridBagConstraints.anchor = 17;
/* 648 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 649 */     this.pnlClanAvailability.add(this.lblClanReIntro, gridBagConstraints);
/*     */     
/* 651 */     this.lblClanExtinct.setText("9999 (TH)");
/* 652 */     gridBagConstraints = new GridBagConstraints();
/* 653 */     gridBagConstraints.gridx = 3;
/* 654 */     gridBagConstraints.gridy = 4;
/* 655 */     gridBagConstraints.anchor = 17;
/* 656 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 657 */     this.pnlClanAvailability.add(this.lblClanExtinct, gridBagConstraints);
/*     */     
/* 659 */     this.lblClanIntro.setText("9999 (TH)");
/* 660 */     gridBagConstraints = new GridBagConstraints();
/* 661 */     gridBagConstraints.gridx = 3;
/* 662 */     gridBagConstraints.gridy = 3;
/* 663 */     gridBagConstraints.anchor = 17;
/* 664 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 665 */     this.pnlClanAvailability.add(this.lblClanIntro, gridBagConstraints);
/*     */     
/* 667 */     this.jLabel3.setText("Clan Availability");
/* 668 */     gridBagConstraints = new GridBagConstraints();
/* 669 */     gridBagConstraints.gridwidth = 4;
/* 670 */     gridBagConstraints.anchor = 17;
/* 671 */     this.pnlClanAvailability.add(this.jLabel3, gridBagConstraints);
/*     */     
/* 673 */     this.lblClanExtraInfo.setText("Status: R&D Start Date: 9999 (CJF), Prototype: 9999 (CJF)");
/* 674 */     gridBagConstraints = new GridBagConstraints();
/* 675 */     gridBagConstraints.gridx = 0;
/* 676 */     gridBagConstraints.gridy = 1;
/* 677 */     gridBagConstraints.gridwidth = 4;
/* 678 */     gridBagConstraints.anchor = 17;
/* 679 */     gridBagConstraints.insets = new Insets(2, 0, 2, 0);
/* 680 */     this.pnlClanAvailability.add(this.lblClanExtraInfo, gridBagConstraints);
/*     */     
/* 682 */     this.jLabel18.setText("Tech Rating");
/* 683 */     gridBagConstraints = new GridBagConstraints();
/* 684 */     gridBagConstraints.gridx = 0;
/* 685 */     gridBagConstraints.gridy = 2;
/* 686 */     gridBagConstraints.anchor = 17;
/* 687 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 688 */     this.pnlClanAvailability.add(this.jLabel18, gridBagConstraints);
/*     */     
/* 690 */     this.lblClanTechRating.setText("X");
/* 691 */     gridBagConstraints = new GridBagConstraints();
/* 692 */     gridBagConstraints.gridx = 1;
/* 693 */     gridBagConstraints.gridy = 2;
/* 694 */     gridBagConstraints.anchor = 17;
/* 695 */     gridBagConstraints.insets = new Insets(0, 2, 0, 4);
/* 696 */     this.pnlClanAvailability.add(this.lblClanTechRating, gridBagConstraints);
/*     */     
/* 698 */     gridBagConstraints = new GridBagConstraints();
/* 699 */     gridBagConstraints.gridx = 0;
/* 700 */     gridBagConstraints.gridy = 13;
/* 701 */     gridBagConstraints.gridwidth = 8;
/* 702 */     gridBagConstraints.anchor = 17;
/* 703 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 704 */     getContentPane().add(this.pnlClanAvailability, gridBagConstraints);
/*     */     
/* 706 */     this.jSeparator3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 707 */     gridBagConstraints = new GridBagConstraints();
/* 708 */     gridBagConstraints.gridx = 0;
/* 709 */     gridBagConstraints.gridy = 10;
/* 710 */     gridBagConstraints.gridwidth = 8;
/* 711 */     gridBagConstraints.fill = 2;
/* 712 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 713 */     getContentPane().add(this.jSeparator3, gridBagConstraints);
/*     */     
/* 715 */     this.pnlISAvailability.setLayout(new java.awt.GridBagLayout());
/*     */     
/* 717 */     this.jLabel4.setText("Inner Sphere Availability");
/* 718 */     gridBagConstraints = new GridBagConstraints();
/* 719 */     gridBagConstraints.gridwidth = 4;
/* 720 */     gridBagConstraints.anchor = 17;
/* 721 */     this.pnlISAvailability.add(this.jLabel4, gridBagConstraints);
/*     */     
/* 723 */     this.lblISExtraInfo.setText("Status: R&D Start Date: 9999 (CJF), Prototype: 9999 (CJF)");
/* 724 */     gridBagConstraints = new GridBagConstraints();
/* 725 */     gridBagConstraints.gridx = 0;
/* 726 */     gridBagConstraints.gridy = 1;
/* 727 */     gridBagConstraints.gridwidth = 4;
/* 728 */     gridBagConstraints.anchor = 17;
/* 729 */     gridBagConstraints.insets = new Insets(2, 0, 2, 0);
/* 730 */     this.pnlISAvailability.add(this.lblISExtraInfo, gridBagConstraints);
/*     */     
/* 732 */     this.jLabel6.setText("Availability (AoW/SL)");
/* 733 */     gridBagConstraints = new GridBagConstraints();
/* 734 */     gridBagConstraints.gridx = 0;
/* 735 */     gridBagConstraints.gridy = 3;
/* 736 */     gridBagConstraints.anchor = 17;
/* 737 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 738 */     this.pnlISAvailability.add(this.jLabel6, gridBagConstraints);
/*     */     
/* 740 */     this.jLabel7.setText("Availability (SW)");
/* 741 */     gridBagConstraints = new GridBagConstraints();
/* 742 */     gridBagConstraints.gridx = 0;
/* 743 */     gridBagConstraints.gridy = 4;
/* 744 */     gridBagConstraints.anchor = 17;
/* 745 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 746 */     this.pnlISAvailability.add(this.jLabel7, gridBagConstraints);
/*     */     
/* 748 */     this.jLabel8.setText("Availability (CI)");
/* 749 */     gridBagConstraints = new GridBagConstraints();
/* 750 */     gridBagConstraints.gridx = 0;
/* 751 */     gridBagConstraints.gridy = 5;
/* 752 */     gridBagConstraints.anchor = 17;
/* 753 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 754 */     this.pnlISAvailability.add(this.jLabel8, gridBagConstraints);
/*     */     
/* 756 */     this.lblISAVSL.setText("X");
/* 757 */     gridBagConstraints = new GridBagConstraints();
/* 758 */     gridBagConstraints.gridx = 1;
/* 759 */     gridBagConstraints.gridy = 3;
/* 760 */     gridBagConstraints.anchor = 17;
/* 761 */     gridBagConstraints.insets = new Insets(0, 2, 0, 4);
/* 762 */     this.pnlISAvailability.add(this.lblISAVSL, gridBagConstraints);
/*     */     
/* 764 */     this.lblISAVSW.setText("X");
/* 765 */     gridBagConstraints = new GridBagConstraints();
/* 766 */     gridBagConstraints.gridx = 1;
/* 767 */     gridBagConstraints.gridy = 4;
/* 768 */     gridBagConstraints.anchor = 17;
/* 769 */     gridBagConstraints.insets = new Insets(0, 2, 0, 4);
/* 770 */     this.pnlISAvailability.add(this.lblISAVSW, gridBagConstraints);
/*     */     
/* 772 */     this.lblISAVCI.setText("X");
/* 773 */     gridBagConstraints = new GridBagConstraints();
/* 774 */     gridBagConstraints.gridx = 1;
/* 775 */     gridBagConstraints.gridy = 5;
/* 776 */     gridBagConstraints.anchor = 17;
/* 777 */     gridBagConstraints.insets = new Insets(0, 2, 0, 4);
/* 778 */     this.pnlISAvailability.add(this.lblISAVCI, gridBagConstraints);
/*     */     
/* 780 */     this.jLabel12.setText("Reintroduction");
/* 781 */     gridBagConstraints = new GridBagConstraints();
/* 782 */     gridBagConstraints.gridx = 2;
/* 783 */     gridBagConstraints.gridy = 5;
/* 784 */     gridBagConstraints.anchor = 13;
/* 785 */     gridBagConstraints.insets = new Insets(0, 4, 0, 2);
/* 786 */     this.pnlISAvailability.add(this.jLabel12, gridBagConstraints);
/*     */     
/* 788 */     this.lblISIntro.setText("9999 (TH)");
/* 789 */     gridBagConstraints = new GridBagConstraints();
/* 790 */     gridBagConstraints.gridx = 3;
/* 791 */     gridBagConstraints.gridy = 3;
/* 792 */     gridBagConstraints.anchor = 17;
/* 793 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 794 */     this.pnlISAvailability.add(this.lblISIntro, gridBagConstraints);
/*     */     
/* 796 */     this.jLabel14.setText("Extinction");
/* 797 */     gridBagConstraints = new GridBagConstraints();
/* 798 */     gridBagConstraints.gridx = 2;
/* 799 */     gridBagConstraints.gridy = 4;
/* 800 */     gridBagConstraints.anchor = 13;
/* 801 */     gridBagConstraints.insets = new Insets(0, 4, 0, 2);
/* 802 */     this.pnlISAvailability.add(this.jLabel14, gridBagConstraints);
/*     */     
/* 804 */     this.jLabel15.setText("Introduction");
/* 805 */     gridBagConstraints = new GridBagConstraints();
/* 806 */     gridBagConstraints.gridx = 2;
/* 807 */     gridBagConstraints.gridy = 3;
/* 808 */     gridBagConstraints.anchor = 13;
/* 809 */     gridBagConstraints.insets = new Insets(0, 4, 0, 2);
/* 810 */     this.pnlISAvailability.add(this.jLabel15, gridBagConstraints);
/*     */     
/* 812 */     this.lblISReIntro.setText("9999 (TH)");
/* 813 */     gridBagConstraints = new GridBagConstraints();
/* 814 */     gridBagConstraints.gridx = 3;
/* 815 */     gridBagConstraints.gridy = 5;
/* 816 */     gridBagConstraints.anchor = 17;
/* 817 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 818 */     this.pnlISAvailability.add(this.lblISReIntro, gridBagConstraints);
/*     */     
/* 820 */     this.lblISExtinct.setText("9999 (TH)");
/* 821 */     gridBagConstraints = new GridBagConstraints();
/* 822 */     gridBagConstraints.gridx = 3;
/* 823 */     gridBagConstraints.gridy = 4;
/* 824 */     gridBagConstraints.anchor = 17;
/* 825 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 826 */     this.pnlISAvailability.add(this.lblISExtinct, gridBagConstraints);
/*     */     
/* 828 */     this.jLabel20.setText("Tech Rating");
/* 829 */     gridBagConstraints = new GridBagConstraints();
/* 830 */     gridBagConstraints.gridx = 0;
/* 831 */     gridBagConstraints.gridy = 2;
/* 832 */     gridBagConstraints.anchor = 17;
/* 833 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 834 */     this.pnlISAvailability.add(this.jLabel20, gridBagConstraints);
/*     */     
/* 836 */     this.lblISTechRating.setText("X");
/* 837 */     gridBagConstraints = new GridBagConstraints();
/* 838 */     gridBagConstraints.gridx = 1;
/* 839 */     gridBagConstraints.gridy = 2;
/* 840 */     gridBagConstraints.anchor = 17;
/* 841 */     gridBagConstraints.insets = new Insets(0, 2, 0, 4);
/* 842 */     this.pnlISAvailability.add(this.lblISTechRating, gridBagConstraints);
/*     */     
/* 844 */     gridBagConstraints = new GridBagConstraints();
/* 845 */     gridBagConstraints.gridx = 0;
/* 846 */     gridBagConstraints.gridy = 11;
/* 847 */     gridBagConstraints.gridwidth = 8;
/* 848 */     gridBagConstraints.anchor = 17;
/* 849 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 850 */     getContentPane().add(this.pnlISAvailability, gridBagConstraints);
/*     */     
/* 852 */     this.jSeparator4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 853 */     gridBagConstraints = new GridBagConstraints();
/* 854 */     gridBagConstraints.gridx = 0;
/* 855 */     gridBagConstraints.gridy = 3;
/* 856 */     gridBagConstraints.gridwidth = 8;
/* 857 */     gridBagConstraints.fill = 2;
/* 858 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 859 */     getContentPane().add(this.jSeparator4, gridBagConstraints);
/*     */     
/* 861 */     this.lblToHit.setText("+10/+10/+10");
/* 862 */     gridBagConstraints = new GridBagConstraints();
/* 863 */     gridBagConstraints.gridx = 3;
/* 864 */     gridBagConstraints.gridy = 5;
/* 865 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/* 866 */     getContentPane().add(this.lblToHit, gridBagConstraints);
/*     */     
/* 868 */     this.jLabel21.setText("To-Hit");
/* 869 */     gridBagConstraints = new GridBagConstraints();
/* 870 */     gridBagConstraints.gridx = 3;
/* 871 */     gridBagConstraints.gridy = 4;
/* 872 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 873 */     getContentPane().add(this.jLabel21, gridBagConstraints);
/*     */     
/* 875 */     this.lblFCSClass.setText("Artemis IV");
/* 876 */     gridBagConstraints = new GridBagConstraints();
/* 877 */     gridBagConstraints.gridx = 1;
/* 878 */     gridBagConstraints.gridy = 5;
/* 879 */     gridBagConstraints.gridwidth = 2;
/* 880 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/* 881 */     getContentPane().add(this.lblFCSClass, gridBagConstraints);
/*     */     
/* 883 */     this.jLabel23.setText("Missile FCS Class");
/* 884 */     gridBagConstraints = new GridBagConstraints();
/* 885 */     gridBagConstraints.gridx = 1;
/* 886 */     gridBagConstraints.gridy = 4;
/* 887 */     gridBagConstraints.gridwidth = 2;
/* 888 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/* 889 */     getContentPane().add(this.jLabel23, gridBagConstraints);
/*     */     
/* 891 */     this.lblMountingRestrictions.setText("No HD, No CT, No Side Torsos, No Arms, No Legs, Omni Actuator Restricted");
/* 892 */     gridBagConstraints = new GridBagConstraints();
/* 893 */     gridBagConstraints.gridx = 2;
/* 894 */     gridBagConstraints.gridy = 6;
/* 895 */     gridBagConstraints.gridwidth = 6;
/* 896 */     gridBagConstraints.anchor = 17;
/* 897 */     gridBagConstraints.insets = new Insets(4, 4, 0, 0);
/* 898 */     getContentPane().add(this.lblMountingRestrictions, gridBagConstraints);
/*     */     
/* 900 */     this.jLabel5.setText("Mounting Restrictions:");
/* 901 */     gridBagConstraints = new GridBagConstraints();
/* 902 */     gridBagConstraints.gridx = 0;
/* 903 */     gridBagConstraints.gridy = 6;
/* 904 */     gridBagConstraints.gridwidth = 2;
/* 905 */     gridBagConstraints.anchor = 17;
/* 906 */     gridBagConstraints.insets = new Insets(4, 4, 0, 0);
/* 907 */     getContentPane().add(this.jLabel5, gridBagConstraints);
/* 908 */     gridBagConstraints = new GridBagConstraints();
/* 909 */     gridBagConstraints.gridx = 0;
/* 910 */     gridBagConstraints.gridy = 14;
/* 911 */     gridBagConstraints.gridwidth = 8;
/* 912 */     gridBagConstraints.fill = 2;
/* 913 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 914 */     getContentPane().add(this.jSeparator5, gridBagConstraints);
/*     */     
/* 916 */     this.jLabel9.setText("Book Reference:");
/* 917 */     gridBagConstraints = new GridBagConstraints();
/* 918 */     gridBagConstraints.gridx = 0;
/* 919 */     gridBagConstraints.gridy = 15;
/* 920 */     gridBagConstraints.gridwidth = 2;
/* 921 */     gridBagConstraints.anchor = 17;
/* 922 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 923 */     getContentPane().add(this.jLabel9, gridBagConstraints);
/*     */     
/* 925 */     this.lblBookRef.setText("jLabel10");
/* 926 */     gridBagConstraints = new GridBagConstraints();
/* 927 */     gridBagConstraints.gridx = 2;
/* 928 */     gridBagConstraints.gridy = 15;
/* 929 */     gridBagConstraints.gridwidth = 6;
/* 930 */     gridBagConstraints.fill = 2;
/* 931 */     gridBagConstraints.anchor = 17;
/* 932 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/* 933 */     getContentPane().add(this.lblBookRef, gridBagConstraints);
/*     */     
/* 935 */     pack(); }
/*     */   
/*     */   private JLabel lblClanExtinct;
/*     */   
/* 939 */   private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) { dispose(); }
/*     */   
/*     */   private JLabel lblClanExtraInfo;
/*     */   private JLabel lblClanIntro;
/*     */   private JLabel lblClanReIntro;
/*     */   private JLabel lblClanTechRating;
/*     */   private JLabel lblCost;
/*     */   private JLabel lblCrits;
/*     */   private JLabel lblDamage;
/*     */   private JLabel lblFCSClass;
/*     */   private JLabel lblHeat;
/*     */   private JLabel lblISAVCI;
/*     */   private JLabel lblISAVSL;
/*     */   private JLabel lblISAVSW;
/*     */   private JLabel lblISExtinct;
/*     */   private JLabel lblISExtraInfo;
/*     */   private JLabel lblISIntro;
/*     */   private JLabel lblISReIntro;
/*     */   private JLabel lblISTechRating;
/*     */   private JLabel lblInfoAVCI;
/*     */   private JLabel lblInfoAVSL;
/*     */   private JLabel lblInfoAVSW;
/*     */   private JLabel lblInfoAmmo;
/*     */   private JLabel lblInfoBV;
/*     */   private JLabel lblInfoCost;
/*     */   private JLabel lblInfoCrits;
/*     */   private JLabel lblInfoDamage;
/*     */   private JLabel lblInfoExtinct;
/*     */   private JLabel lblInfoHeat;
/*     */   private JLabel lblInfoIntro;
/*     */   private JLabel lblInfoRange;
/*     */   private JLabel lblInfoReIntro;
/*     */   private JLabel lblInfoSpecial;
/*     */   private JLabel lblInfoTonnage;
/*     */   private JLabel lblInfoType;
/*     */   private JLabel lblMountingRestrictions;
/*     */   private JLabel lblName;
/*     */   private JLabel lblRange;
/*     */   private JLabel lblRulesBM;
/*     */   private JLabel lblRulesIM;
/*     */   private JLabel lblSpecials;
/*     */   private JLabel lblToHit;
/*     */   private JLabel lblTonnage;
/*     */   private JLabel lblType;
/*     */   private JPanel pnlClanAvailability;
/*     */   private JPanel pnlISAvailability;
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgWeaponInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */