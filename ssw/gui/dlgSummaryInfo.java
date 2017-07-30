/*     */ package ssw.gui;
/*     */ 
/*     */ import components.AvailableCode;
/*     */ import components.Mech;
/*     */ import components.TargetingComputer;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class dlgSummaryInfo extends JDialog
/*     */ {
/*     */   ifMechForm Parent;
/*     */   private JButton btnOkay;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel10;
/*     */   private JLabel jLabel11;
/*     */   private JLabel jLabel12;
/*     */   private JLabel jLabel13;
/*     */   private JLabel jLabel14;
/*     */   private JLabel jLabel15;
/*     */   private JLabel jLabel16;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JLabel jLabel6;
/*     */   private JLabel jLabel7;
/*     */   private JLabel jLabel8;
/*     */   
/*     */   public dlgSummaryInfo(Frame parent, boolean modal)
/*     */   {
/*  38 */     super(parent, modal);
/*  39 */     this.Parent = ((ifMechForm)parent);
/*  40 */     initComponents();
/*  41 */     setResizable(false);
/*  42 */     setTitle("Basic Mech Summary");
/*  43 */     BuildLabels();
/*     */   }
/*     */   
/*     */   private void BuildLabels()
/*     */   {
/*  48 */     AvailableCode AC = this.Parent.GetMech().GetAvailability();
/*  49 */     this.lblName.setText(this.Parent.GetMech().GetName());
/*  50 */     this.lblModel.setText(this.Parent.GetMech().GetModel());
/*  51 */     this.lblActualProdYear.setText("" + this.Parent.GetMech().GetYear());
/*  52 */     switch (this.Parent.GetMech().GetLoadout().GetTechBase()) {
/*     */     case 0: 
/*  54 */       this.lblAvailability.setText(AC.GetISCombinedCode());
/*  55 */       if ((AC.WentExtinctIS()) && (AC.WasReIntrodIS())) {
/*  56 */         if (AC.GetISIntroDate() >= AC.GetISReIntroDate()) {
/*  57 */           this.lblEarliestProdYear.setText("" + AC.GetISIntroDate());
/*     */         } else {
/*  59 */           this.lblEarliestProdYear.setText(AC.GetISIntroDate() + " or " + AC.GetISReIntroDate());
/*     */         }
/*     */       } else {
/*  62 */         this.lblEarliestProdYear.setText("" + AC.GetISIntroDate());
/*     */       }
/*  64 */       if (AC.WentExtinctIS()) {
/*  65 */         if (AC.GetISIntroDate() >= AC.GetISReIntroDate()) {
/*  66 */           this.lblExtinctBy.setText("NA");
/*     */         } else {
/*  68 */           this.lblExtinctBy.setText("" + AC.GetISExtinctDate());
/*     */         }
/*     */       } else {
/*  71 */         this.lblExtinctBy.setText("NA");
/*     */       }
/*  73 */       break;
/*     */     case 1: 
/*  75 */       this.lblAvailability.setText(AC.GetCLCombinedCode());
/*  76 */       if ((AC.WentExtinctCL()) && (AC.WasReIntrodCL())) {
/*  77 */         if (AC.GetCLIntroDate() >= AC.GetCLReIntroDate()) {
/*  78 */           this.lblEarliestProdYear.setText("" + AC.GetCLIntroDate());
/*     */         } else {
/*  80 */           this.lblEarliestProdYear.setText(AC.GetCLIntroDate() + " or " + AC.GetCLReIntroDate());
/*     */         }
/*     */       } else {
/*  83 */         this.lblEarliestProdYear.setText("" + AC.GetCLIntroDate());
/*     */       }
/*  85 */       if (AC.WentExtinctCL()) {
/*  86 */         if (AC.GetCLIntroDate() >= AC.GetCLReIntroDate()) {
/*  87 */           this.lblExtinctBy.setText("NA");
/*     */         } else {
/*  89 */           this.lblExtinctBy.setText("" + AC.GetCLExtinctDate());
/*     */         }
/*     */       } else {
/*  92 */         this.lblExtinctBy.setText("NA");
/*     */       }
/*  94 */       break;
/*     */     case 2: 
/*  96 */       this.lblAvailability.setText(AC.GetBestCombinedCode());
/*  97 */       this.lblEarliestProdYear.setText("" + this.Parent.GetMech().GetYear());
/*  98 */       this.lblExtinctBy.setText("NA");
/*     */     }
/*     */     
/*     */     
/* 102 */     this.lblDefensiveBV.setText(String.format("%1$.2f", new Object[] { Double.valueOf(this.Parent.GetMech().GetDefensiveBV()) }));
/* 103 */     this.lblOffensiveBV.setText(String.format("%1$.2f", new Object[] { Double.valueOf(this.Parent.GetMech().GetOffensiveBV()) }));
/* 104 */     this.lblTotalBV.setText(String.format("%1$,d", new Object[] { Integer.valueOf(this.Parent.GetMech().GetCurrentBV()) }));
/*     */     
/* 106 */     this.lblBaseChassisCost.setText("" + (int)Math.floor(this.Parent.GetMech().GetBaseChassisCost() + 0.5D));
/* 107 */     this.lblBaseEngineCost.setText("" + (int)Math.floor(this.Parent.GetMech().GetEngine().GetCost() + 0.5D));
/* 108 */     double BEC = this.Parent.GetMech().GetEquipCost();
/* 109 */     if (this.Parent.GetMech().UsingTC()) {
/* 110 */       BEC += this.Parent.GetMech().GetTC().GetCost();
/*     */     }
/* 112 */     this.lblBaseEquipmentCost.setText("" + (int)Math.floor(BEC + 0.5D));
/* 113 */     this.lblTotalDryCost.setText("" + (int)Math.floor(this.Parent.GetMech().GetDryCost() + 0.5D));
/* 114 */     this.lblTotalCost.setText("" + (int)Math.floor(this.Parent.GetMech().GetTotalCost() + 0.5D));
/*     */     
/* 116 */     this.lblTotalDryWeight.setText(this.Parent.GetMech().GetCurrentDryTons() + " tons");
/* 117 */     this.lblTotalWeight.setText(this.Parent.GetMech().GetCurrentTons() + " tons");
/*     */   }
/*     */   
/*     */   private JLabel jLabel9;
/*     */   private JLabel lblActualProdYear;
/*     */   private JLabel lblAvailability;
/*     */   private JLabel lblBaseChassisCost;
/*     */   private JLabel lblBaseEngineCost;
/*     */   private JLabel lblBaseEquipmentCost;
/*     */   private JLabel lblDefensiveBV;
/*     */   private JLabel lblEarliestProdYear;
/*     */   
/* 129 */   private void initComponents() { this.jLabel1 = new JLabel();
/* 130 */     this.jLabel2 = new JLabel();
/* 131 */     this.jLabel3 = new JLabel();
/* 132 */     this.jLabel4 = new JLabel();
/* 133 */     this.jLabel5 = new JLabel();
/* 134 */     this.jLabel6 = new JLabel();
/* 135 */     this.jLabel7 = new JLabel();
/* 136 */     this.jLabel8 = new JLabel();
/* 137 */     this.jLabel9 = new JLabel();
/* 138 */     this.jLabel10 = new JLabel();
/* 139 */     this.jLabel11 = new JLabel();
/* 140 */     this.lblName = new JLabel();
/* 141 */     this.lblModel = new JLabel();
/* 142 */     this.lblAvailability = new JLabel();
/* 143 */     this.lblEarliestProdYear = new JLabel();
/* 144 */     this.lblActualProdYear = new JLabel();
/* 145 */     this.jLabel12 = new JLabel();
/* 146 */     this.lblBaseChassisCost = new JLabel();
/* 147 */     this.lblBaseEngineCost = new JLabel();
/* 148 */     this.lblBaseEquipmentCost = new JLabel();
/* 149 */     this.lblTotalDryCost = new JLabel();
/* 150 */     this.lblTotalCost = new JLabel();
/* 151 */     this.lblTotalDryWeight = new JLabel();
/* 152 */     this.lblTotalWeight = new JLabel();
/* 153 */     this.btnOkay = new JButton();
/* 154 */     this.jLabel13 = new JLabel();
/* 155 */     this.lblExtinctBy = new JLabel();
/* 156 */     this.jLabel14 = new JLabel();
/* 157 */     this.jLabel15 = new JLabel();
/* 158 */     this.jLabel16 = new JLabel();
/* 159 */     this.lblDefensiveBV = new JLabel();
/* 160 */     this.lblOffensiveBV = new JLabel();
/* 161 */     this.lblTotalBV = new JLabel();
/*     */     
/* 163 */     setDefaultCloseOperation(2);
/* 164 */     getContentPane().setLayout(new java.awt.GridBagLayout());
/*     */     
/* 166 */     this.jLabel1.setText("Mech Name:");
/* 167 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 168 */     gridBagConstraints.anchor = 13;
/* 169 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 170 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/* 172 */     this.jLabel2.setText("Model:");
/* 173 */     gridBagConstraints = new GridBagConstraints();
/* 174 */     gridBagConstraints.gridx = 0;
/* 175 */     gridBagConstraints.gridy = 1;
/* 176 */     gridBagConstraints.anchor = 13;
/* 177 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 178 */     getContentPane().add(this.jLabel2, gridBagConstraints);
/*     */     
/* 180 */     this.jLabel3.setText("Availability:");
/* 181 */     gridBagConstraints = new GridBagConstraints();
/* 182 */     gridBagConstraints.gridx = 0;
/* 183 */     gridBagConstraints.gridy = 2;
/* 184 */     gridBagConstraints.anchor = 13;
/* 185 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 186 */     getContentPane().add(this.jLabel3, gridBagConstraints);
/*     */     
/* 188 */     this.jLabel4.setText("Base Chassis Cost:");
/* 189 */     gridBagConstraints = new GridBagConstraints();
/* 190 */     gridBagConstraints.gridx = 0;
/* 191 */     gridBagConstraints.gridy = 9;
/* 192 */     gridBagConstraints.anchor = 13;
/* 193 */     gridBagConstraints.insets = new Insets(4, 0, 2, 4);
/* 194 */     getContentPane().add(this.jLabel4, gridBagConstraints);
/*     */     
/* 196 */     this.jLabel5.setText("Base Engine Cost:");
/* 197 */     gridBagConstraints = new GridBagConstraints();
/* 198 */     gridBagConstraints.gridx = 0;
/* 199 */     gridBagConstraints.gridy = 10;
/* 200 */     gridBagConstraints.anchor = 13;
/* 201 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 202 */     getContentPane().add(this.jLabel5, gridBagConstraints);
/*     */     
/* 204 */     this.jLabel6.setText("Base Equipment Cost:");
/* 205 */     gridBagConstraints = new GridBagConstraints();
/* 206 */     gridBagConstraints.gridx = 0;
/* 207 */     gridBagConstraints.gridy = 11;
/* 208 */     gridBagConstraints.anchor = 13;
/* 209 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 210 */     getContentPane().add(this.jLabel6, gridBagConstraints);
/*     */     
/* 212 */     this.jLabel7.setText("Total Cost (Dry):");
/* 213 */     gridBagConstraints = new GridBagConstraints();
/* 214 */     gridBagConstraints.gridx = 0;
/* 215 */     gridBagConstraints.gridy = 12;
/* 216 */     gridBagConstraints.anchor = 13;
/* 217 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 218 */     getContentPane().add(this.jLabel7, gridBagConstraints);
/*     */     
/* 220 */     this.jLabel8.setText("Total Cost:");
/* 221 */     gridBagConstraints = new GridBagConstraints();
/* 222 */     gridBagConstraints.gridx = 0;
/* 223 */     gridBagConstraints.gridy = 13;
/* 224 */     gridBagConstraints.anchor = 13;
/* 225 */     gridBagConstraints.insets = new Insets(0, 0, 4, 4);
/* 226 */     getContentPane().add(this.jLabel8, gridBagConstraints);
/*     */     
/* 228 */     this.jLabel9.setText("Total Weight (Dry):");
/* 229 */     gridBagConstraints = new GridBagConstraints();
/* 230 */     gridBagConstraints.gridx = 0;
/* 231 */     gridBagConstraints.gridy = 14;
/* 232 */     gridBagConstraints.anchor = 13;
/* 233 */     gridBagConstraints.insets = new Insets(4, 0, 2, 4);
/* 234 */     getContentPane().add(this.jLabel9, gridBagConstraints);
/*     */     
/* 236 */     this.jLabel10.setText("Total Weight:");
/* 237 */     gridBagConstraints = new GridBagConstraints();
/* 238 */     gridBagConstraints.gridx = 0;
/* 239 */     gridBagConstraints.gridy = 15;
/* 240 */     gridBagConstraints.anchor = 13;
/* 241 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/* 242 */     getContentPane().add(this.jLabel10, gridBagConstraints);
/*     */     
/* 244 */     this.jLabel11.setText("Earliest Production Year:");
/* 245 */     gridBagConstraints = new GridBagConstraints();
/* 246 */     gridBagConstraints.gridx = 0;
/* 247 */     gridBagConstraints.gridy = 4;
/* 248 */     gridBagConstraints.anchor = 13;
/* 249 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 250 */     getContentPane().add(this.jLabel11, gridBagConstraints);
/*     */     
/* 252 */     this.lblName.setText("lblName");
/* 253 */     gridBagConstraints = new GridBagConstraints();
/* 254 */     gridBagConstraints.anchor = 17;
/* 255 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 256 */     getContentPane().add(this.lblName, gridBagConstraints);
/*     */     
/* 258 */     this.lblModel.setText("lblModel");
/* 259 */     gridBagConstraints = new GridBagConstraints();
/* 260 */     gridBagConstraints.gridx = 1;
/* 261 */     gridBagConstraints.gridy = 1;
/* 262 */     gridBagConstraints.anchor = 17;
/* 263 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 264 */     getContentPane().add(this.lblModel, gridBagConstraints);
/*     */     
/* 266 */     this.lblAvailability.setText("lblAvailability");
/* 267 */     gridBagConstraints = new GridBagConstraints();
/* 268 */     gridBagConstraints.gridx = 1;
/* 269 */     gridBagConstraints.gridy = 2;
/* 270 */     gridBagConstraints.anchor = 17;
/* 271 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 272 */     getContentPane().add(this.lblAvailability, gridBagConstraints);
/*     */     
/* 274 */     this.lblEarliestProdYear.setText("lblEarliestProdYear");
/* 275 */     gridBagConstraints = new GridBagConstraints();
/* 276 */     gridBagConstraints.gridx = 1;
/* 277 */     gridBagConstraints.gridy = 4;
/* 278 */     gridBagConstraints.anchor = 17;
/* 279 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 280 */     getContentPane().add(this.lblEarliestProdYear, gridBagConstraints);
/*     */     
/* 282 */     this.lblActualProdYear.setText("lblActualProdYear");
/* 283 */     gridBagConstraints = new GridBagConstraints();
/* 284 */     gridBagConstraints.gridx = 1;
/* 285 */     gridBagConstraints.gridy = 3;
/* 286 */     gridBagConstraints.anchor = 17;
/* 287 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 288 */     getContentPane().add(this.lblActualProdYear, gridBagConstraints);
/*     */     
/* 290 */     this.jLabel12.setText("Production Year:");
/* 291 */     gridBagConstraints = new GridBagConstraints();
/* 292 */     gridBagConstraints.gridx = 0;
/* 293 */     gridBagConstraints.gridy = 3;
/* 294 */     gridBagConstraints.anchor = 13;
/* 295 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 296 */     getContentPane().add(this.jLabel12, gridBagConstraints);
/*     */     
/* 298 */     this.lblBaseChassisCost.setText("lblBaseChassisCost");
/* 299 */     gridBagConstraints = new GridBagConstraints();
/* 300 */     gridBagConstraints.gridx = 1;
/* 301 */     gridBagConstraints.gridy = 9;
/* 302 */     gridBagConstraints.anchor = 17;
/* 303 */     gridBagConstraints.insets = new Insets(4, 0, 2, 0);
/* 304 */     getContentPane().add(this.lblBaseChassisCost, gridBagConstraints);
/*     */     
/* 306 */     this.lblBaseEngineCost.setText("lblBaseEngineCost");
/* 307 */     gridBagConstraints = new GridBagConstraints();
/* 308 */     gridBagConstraints.gridx = 1;
/* 309 */     gridBagConstraints.gridy = 10;
/* 310 */     gridBagConstraints.anchor = 17;
/* 311 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 312 */     getContentPane().add(this.lblBaseEngineCost, gridBagConstraints);
/*     */     
/* 314 */     this.lblBaseEquipmentCost.setText("lblBaseEquipmentCost");
/* 315 */     gridBagConstraints = new GridBagConstraints();
/* 316 */     gridBagConstraints.gridx = 1;
/* 317 */     gridBagConstraints.gridy = 11;
/* 318 */     gridBagConstraints.anchor = 17;
/* 319 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 320 */     getContentPane().add(this.lblBaseEquipmentCost, gridBagConstraints);
/*     */     
/* 322 */     this.lblTotalDryCost.setText("lblTotalDryCost");
/* 323 */     gridBagConstraints = new GridBagConstraints();
/* 324 */     gridBagConstraints.gridx = 1;
/* 325 */     gridBagConstraints.gridy = 12;
/* 326 */     gridBagConstraints.anchor = 17;
/* 327 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 328 */     getContentPane().add(this.lblTotalDryCost, gridBagConstraints);
/*     */     
/* 330 */     this.lblTotalCost.setText("lblTotalCost");
/* 331 */     gridBagConstraints = new GridBagConstraints();
/* 332 */     gridBagConstraints.gridx = 1;
/* 333 */     gridBagConstraints.gridy = 13;
/* 334 */     gridBagConstraints.anchor = 17;
/* 335 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/* 336 */     getContentPane().add(this.lblTotalCost, gridBagConstraints);
/*     */     
/* 338 */     this.lblTotalDryWeight.setText("lblTotalDryWeight");
/* 339 */     gridBagConstraints = new GridBagConstraints();
/* 340 */     gridBagConstraints.gridx = 1;
/* 341 */     gridBagConstraints.gridy = 14;
/* 342 */     gridBagConstraints.anchor = 17;
/* 343 */     gridBagConstraints.insets = new Insets(4, 0, 2, 0);
/* 344 */     getContentPane().add(this.lblTotalDryWeight, gridBagConstraints);
/*     */     
/* 346 */     this.lblTotalWeight.setText("lblTotalWeight");
/* 347 */     gridBagConstraints = new GridBagConstraints();
/* 348 */     gridBagConstraints.gridx = 1;
/* 349 */     gridBagConstraints.gridy = 15;
/* 350 */     gridBagConstraints.anchor = 17;
/* 351 */     getContentPane().add(this.lblTotalWeight, gridBagConstraints);
/*     */     
/* 353 */     this.btnOkay.setText("Okay");
/* 354 */     this.btnOkay.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 356 */         dlgSummaryInfo.this.btnOkayActionPerformed(evt);
/*     */       }
/* 358 */     });
/* 359 */     gridBagConstraints = new GridBagConstraints();
/* 360 */     gridBagConstraints.gridx = 1;
/* 361 */     gridBagConstraints.gridy = 16;
/* 362 */     gridBagConstraints.anchor = 13;
/* 363 */     gridBagConstraints.insets = new Insets(8, 0, 0, 0);
/* 364 */     getContentPane().add(this.btnOkay, gridBagConstraints);
/*     */     
/* 366 */     this.jLabel13.setText("Extinct By:");
/* 367 */     gridBagConstraints = new GridBagConstraints();
/* 368 */     gridBagConstraints.gridx = 0;
/* 369 */     gridBagConstraints.gridy = 5;
/* 370 */     gridBagConstraints.anchor = 13;
/* 371 */     gridBagConstraints.insets = new Insets(0, 0, 4, 4);
/* 372 */     getContentPane().add(this.jLabel13, gridBagConstraints);
/*     */     
/* 374 */     this.lblExtinctBy.setText("lblExtinctBy");
/* 375 */     gridBagConstraints = new GridBagConstraints();
/* 376 */     gridBagConstraints.gridx = 1;
/* 377 */     gridBagConstraints.gridy = 5;
/* 378 */     gridBagConstraints.anchor = 17;
/* 379 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/* 380 */     getContentPane().add(this.lblExtinctBy, gridBagConstraints);
/*     */     
/* 382 */     this.jLabel14.setText("Defensive BV:");
/* 383 */     gridBagConstraints = new GridBagConstraints();
/* 384 */     gridBagConstraints.gridx = 0;
/* 385 */     gridBagConstraints.gridy = 6;
/* 386 */     gridBagConstraints.anchor = 13;
/* 387 */     gridBagConstraints.insets = new Insets(4, 0, 2, 4);
/* 388 */     getContentPane().add(this.jLabel14, gridBagConstraints);
/*     */     
/* 390 */     this.jLabel15.setText("Offensive BV:");
/* 391 */     gridBagConstraints = new GridBagConstraints();
/* 392 */     gridBagConstraints.gridx = 0;
/* 393 */     gridBagConstraints.gridy = 7;
/* 394 */     gridBagConstraints.anchor = 13;
/* 395 */     gridBagConstraints.insets = new Insets(0, 0, 2, 4);
/* 396 */     getContentPane().add(this.jLabel15, gridBagConstraints);
/*     */     
/* 398 */     this.jLabel16.setText("Total BV:");
/* 399 */     gridBagConstraints = new GridBagConstraints();
/* 400 */     gridBagConstraints.gridx = 0;
/* 401 */     gridBagConstraints.gridy = 8;
/* 402 */     gridBagConstraints.anchor = 13;
/* 403 */     gridBagConstraints.insets = new Insets(0, 0, 4, 4);
/* 404 */     getContentPane().add(this.jLabel16, gridBagConstraints);
/*     */     
/* 406 */     this.lblDefensiveBV.setText("lblDefensiveBV");
/* 407 */     gridBagConstraints = new GridBagConstraints();
/* 408 */     gridBagConstraints.gridx = 1;
/* 409 */     gridBagConstraints.gridy = 6;
/* 410 */     gridBagConstraints.anchor = 17;
/* 411 */     gridBagConstraints.insets = new Insets(4, 0, 2, 0);
/* 412 */     getContentPane().add(this.lblDefensiveBV, gridBagConstraints);
/*     */     
/* 414 */     this.lblOffensiveBV.setText("lblOffensiveBV");
/* 415 */     gridBagConstraints = new GridBagConstraints();
/* 416 */     gridBagConstraints.gridx = 1;
/* 417 */     gridBagConstraints.gridy = 7;
/* 418 */     gridBagConstraints.anchor = 17;
/* 419 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/* 420 */     getContentPane().add(this.lblOffensiveBV, gridBagConstraints);
/*     */     
/* 422 */     this.lblTotalBV.setText("lblTotalBV");
/* 423 */     gridBagConstraints = new GridBagConstraints();
/* 424 */     gridBagConstraints.gridx = 1;
/* 425 */     gridBagConstraints.gridy = 8;
/* 426 */     gridBagConstraints.anchor = 17;
/* 427 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/* 428 */     getContentPane().add(this.lblTotalBV, gridBagConstraints);
/*     */     
/* 430 */     pack(); }
/*     */   
/*     */   private JLabel lblExtinctBy;
/*     */   
/* 434 */   private void btnOkayActionPerformed(ActionEvent evt) { dispose(); }
/*     */   
/*     */   private JLabel lblModel;
/*     */   private JLabel lblName;
/*     */   private JLabel lblOffensiveBV;
/*     */   private JLabel lblTotalBV;
/*     */   private JLabel lblTotalCost;
/*     */   private JLabel lblTotalDryCost;
/*     */   private JLabel lblTotalDryWeight;
/*     */   private JLabel lblTotalWeight;
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgSummaryInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */