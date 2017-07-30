/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Mech;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class dlgPrintSavedMechOptions extends javax.swing.JDialog
/*     */ {
/*     */   private Mech CurMech;
/*     */   private ifMechForm Parent;
/*  20 */   private boolean Result = false;
/*  21 */   private java.io.File MechImage = null;
/*  22 */   private java.io.File LogoImage = null;
/*  23 */   filehandlers.Media media = new filehandlers.Media();
/*     */   private JButton btnCancel;
/*     */   private JButton btnChangeAmmo;
/*     */   private JButton btnChooseImage; private JButton btnChooseLogo;
/*  27 */   public dlgPrintSavedMechOptions(java.awt.Frame parent, boolean modal, Mech m, String Warrior, int Gunnery, int Piloting) { super(parent, modal);
/*  28 */     initComponents();
/*  29 */     this.Parent = ((ifMechForm)parent);
/*     */     
/*  31 */     if (m == null) {
/*  32 */       this.CurMech = null;
/*  33 */       this.pnlBattleMech.setVisible(false);
/*  34 */       setSize(getWidth(), getHeight() - this.pnlBattleMech.getHeight());
/*  35 */       this.btnChooseLogo.setEnabled(false);
/*  36 */       this.btnChooseImage.setEnabled(false);
/*  37 */       this.lblImage.setText("The Mech Image must be set already.");
/*     */     } else {
/*  39 */       this.CurMech = m;
/*  40 */       if (!this.media.DetermineMatchingImage(this.CurMech.GetName(), this.CurMech.GetModel(), this.CurMech.GetSSWImage()).isEmpty()) {
/*  41 */         this.MechImage = new java.io.File(this.media.DetermineMatchingImage(this.CurMech.GetName(), this.CurMech.GetModel(), this.CurMech.GetSSWImage()));
/*     */       }
/*  43 */       this.media.setLogo(this.lblImage, this.MechImage);
/*  44 */       this.pnlBattleMech.setBorder(javax.swing.BorderFactory.createTitledBorder(this.CurMech.GetFullName() + " Information"));
/*  45 */       this.lblBaseBV.setText(String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/*  46 */       this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(common.CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) }));
/*  47 */       this.txtWarriorName.setText(Warrior);
/*  48 */       this.cmbGunnery.setSelectedIndex(Gunnery);
/*  49 */       this.cmbPiloting.setSelectedIndex(Piloting);
/*     */       
/*  51 */       if (this.CurMech.IsOmnimech()) {
/*  52 */         java.util.ArrayList Loadouts = this.CurMech.GetLoadouts();
/*     */         
/*  54 */         this.cmbOmniVariant.setMaximumRowCount(Loadouts.size() + 1);
/*  55 */         for (int i = 0; i < Loadouts.size(); i++) {
/*  56 */           components.ifMechLoadout tempLoadout = (components.ifMechLoadout)Loadouts.get(i);
/*  57 */           this.cmbOmniVariant.addItem(tempLoadout.GetName());
/*  58 */           if (this.CurMech.GetLoadout().GetName().equals(tempLoadout.GetName())) this.cmbOmniVariant.setSelectedItem(tempLoadout.GetName());
/*     */         }
/*  60 */         this.cmbOmniVariant.removeItem("Not an Omni-Mech");
/*     */       }
/*     */       else {
/*  63 */         this.cmbOmniVariant.setVisible(false);
/*     */       }
/*     */     }
/*     */     
/*  67 */     if (this.Parent != null) {
/*  68 */       this.chkPrintCharts.setSelected(this.Parent.GetPrefs().getBoolean("UseCharts", false));
/*  69 */       this.chkPrintCanon.setSelected(this.Parent.GetPrefs().getBoolean("UseCanonDots", false));
/*  70 */       this.chkMWStats.setSelected(this.Parent.GetPrefs().getBoolean("NoPilot", false));
/*  71 */       if (this.Parent.GetPrefs().getBoolean("NoPilot", false)) {
/*  72 */         chkMWStatsActionPerformed(null);
/*     */       }
/*  74 */       if (this.Parent.GetPrefs().getBoolean("UseA4", false)) {
/*  75 */         this.cmbPaperSize.setSelectedIndex(1);
/*     */       }
/*  77 */       this.chkUseHexConversion.setSelected(this.Parent.GetPrefs().getBoolean("UseMiniConversion", false));
/*  78 */       if (this.chkUseHexConversion.isSelected()) {
/*  79 */         this.lblOneHex.setEnabled(true);
/*  80 */         this.cmbHexConvFactor.setEnabled(true);
/*  81 */         this.lblInches.setEnabled(true);
/*  82 */         this.cmbHexConvFactor.setSelectedIndex(this.Parent.GetPrefs().getInt("MiniConversionRate", 0));
/*     */       }
/*     */       
/*  85 */       if (this.Parent.GetPrefs().get("LastLogo", "").length() > 0) {
/*  86 */         this.LogoImage = new java.io.File(this.Parent.GetPrefs().get("LastLogo", ""));
/*  87 */         this.media.setLogo(this.lblLogo, this.Parent.GetPrefs().get("LastLogo", ""));
/*     */       } } }
/*     */   
/*     */   private JButton btnPrint;
/*     */   private JCheckBox chkLogo;
/*     */   
/*  93 */   public dlgPrintSavedMechOptions(java.awt.Frame parent, boolean modal, Mech m) { this(parent, modal, m, "", 4, 5); }
/*     */   
/*     */   private JCheckBox chkMWStats;
/*     */   
/*  97 */   public dlgPrintSavedMechOptions(java.awt.Frame parent, boolean modal, Print.PrintMech m) { this(parent, modal, m.CurMech, m.getMechwarrior(), m.getGunnery(), m.getPiloting()); }
/*     */   
/*     */   private JCheckBox chkPrintCanon;
/*     */   
/* 101 */   public dlgPrintSavedMechOptions(java.awt.Frame parent, boolean modal) { this(parent, modal, null, "", 4, 5); }
/*     */   
/*     */   private JCheckBox chkPrintCharts;
/*     */   private JCheckBox chkPrintImage;
/*     */   private JCheckBox chkStats;
/*     */   private JCheckBox chkUseHexConversion;
/*     */   private JComboBox cmbGunnery;
/*     */   private JComboBox cmbHexConvFactor;
/*     */   private JComboBox cmbOmniVariant;
/*     */   private JComboBox cmbPaperSize;
/*     */   
/*     */   private void initComponents() {
/* 113 */     this.jPanel1 = new JPanel();
/* 114 */     this.btnPrint = new JButton();
/* 115 */     this.btnCancel = new JButton();
/* 116 */     this.jPanel4 = new JPanel();
/* 117 */     this.pnlBattleMech = new JPanel();
/* 118 */     this.cmbOmniVariant = new JComboBox();
/* 119 */     this.txtWarriorName = new javax.swing.JTextField();
/* 120 */     this.jPanel2 = new JPanel();
/* 121 */     this.lblAdjustBVLabel = new JLabel();
/* 122 */     this.lblAdjustBV = new JLabel();
/* 123 */     this.jLabel3 = new JLabel();
/* 124 */     this.lblBaseBV = new JLabel();
/* 125 */     this.jLabel4 = new JLabel();
/* 126 */     this.cmbGunnery = new JComboBox();
/* 127 */     this.jLabel1 = new JLabel();
/* 128 */     this.jLabel2 = new JLabel();
/* 129 */     this.cmbPiloting = new JComboBox();
/* 130 */     this.pnlPrintOptions = new JPanel();
/* 131 */     this.chkPrintCharts = new JCheckBox();
/* 132 */     this.chkUseHexConversion = new JCheckBox();
/* 133 */     this.lblOneHex = new JLabel();
/* 134 */     this.cmbHexConvFactor = new JComboBox();
/* 135 */     this.lblInches = new JLabel();
/* 136 */     this.jPanel3 = new JPanel();
/* 137 */     this.jLabel5 = new JLabel();
/* 138 */     this.cmbPaperSize = new JComboBox();
/* 139 */     this.chkMWStats = new JCheckBox();
/* 140 */     this.chkPrintCanon = new JCheckBox();
/* 141 */     this.pnlImageOptions = new JPanel();
/* 142 */     this.chkPrintImage = new JCheckBox();
/* 143 */     this.btnChooseImage = new JButton();
/* 144 */     this.lblImage = new JLabel();
/* 145 */     this.chkLogo = new JCheckBox();
/* 146 */     this.chkStats = new JCheckBox();
/* 147 */     this.lblLogo = new JLabel();
/* 148 */     this.lblStats = new JLabel();
/* 149 */     this.btnChooseLogo = new JButton();
/* 150 */     this.jPanel5 = new JPanel();
/* 151 */     this.btnChangeAmmo = new JButton();
/*     */     
/* 153 */     setDefaultCloseOperation(2);
/* 154 */     setTitle("Print Saved Mech");
/*     */     
/* 156 */     this.btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/printer.png")));
/* 157 */     this.btnPrint.setText("Print");
/* 158 */     this.btnPrint.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 160 */         dlgPrintSavedMechOptions.this.btnPrintActionPerformed(evt);
/*     */       }
/* 162 */     });
/* 163 */     this.jPanel1.add(this.btnPrint);
/*     */     
/* 165 */     this.btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/cross-script.png")));
/* 166 */     this.btnCancel.setText("Cancel");
/* 167 */     this.btnCancel.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 169 */         dlgPrintSavedMechOptions.this.btnCancelActionPerformed(evt);
/*     */       }
/* 171 */     });
/* 172 */     this.jPanel1.add(this.btnCancel);
/*     */     
/* 174 */     this.jPanel4.setLayout(new java.awt.GridBagLayout());
/*     */     
/* 176 */     this.pnlBattleMech.setBorder(javax.swing.BorderFactory.createTitledBorder("Battlemech Information"));
/*     */     
/* 178 */     this.cmbOmniVariant.setMaximumRowCount(1);
/* 179 */     this.cmbOmniVariant.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Not an Omni-Mech" }));
/* 180 */     this.cmbOmniVariant.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 182 */         dlgPrintSavedMechOptions.this.cmbOmniVariantActionPerformed(evt);
/*     */       }
/*     */       
/* 185 */     });
/* 186 */     this.txtWarriorName.setMaximumSize(new java.awt.Dimension(150, 20));
/* 187 */     this.txtWarriorName.setMinimumSize(new java.awt.Dimension(150, 20));
/* 188 */     this.txtWarriorName.setPreferredSize(new java.awt.Dimension(150, 20));
/*     */     
/* 190 */     this.jPanel2.setLayout(new java.awt.GridBagLayout());
/*     */     
/* 192 */     this.lblAdjustBVLabel.setText("Adjusted BV:");
/*     */     
/* 194 */     this.lblAdjustBV.setText("00,000");
/*     */     
/* 196 */     this.jLabel3.setText("Base BV:");
/*     */     
/* 198 */     this.lblBaseBV.setText("00,000");
/*     */     
/* 200 */     this.jLabel4.setText("Mechwarrior");
/*     */     
/* 202 */     this.cmbGunnery.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" }));
/* 203 */     this.cmbGunnery.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 205 */         dlgPrintSavedMechOptions.this.cmbGunneryActionPerformed(evt);
/*     */       }
/*     */       
/* 208 */     });
/* 209 */     this.jLabel1.setText("Gunnery");
/*     */     
/* 211 */     this.jLabel2.setText("Piloting");
/*     */     
/* 213 */     this.cmbPiloting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" }));
/* 214 */     this.cmbPiloting.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 216 */         dlgPrintSavedMechOptions.this.cmbPilotingActionPerformed(evt);
/*     */       }
/*     */       
/* 219 */     });
/* 220 */     GroupLayout pnlBattleMechLayout = new GroupLayout(this.pnlBattleMech);
/* 221 */     this.pnlBattleMech.setLayout(pnlBattleMechLayout);
/* 222 */     pnlBattleMechLayout.setHorizontalGroup(pnlBattleMechLayout
/* 223 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 224 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 225 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 226 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 227 */       .addGap(30, 30, 30)
/* 228 */       .addComponent(this.jPanel2, -2, -1, -2))
/* 229 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 230 */       .addContainerGap()
/* 231 */       .addComponent(this.cmbOmniVariant, -2, 123, -2)
/* 232 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 233 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 234 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 235 */       .addComponent(this.jLabel4)
/* 236 */       .addGap(133, 133, 133)
/* 237 */       .addComponent(this.jLabel1))
/* 238 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 239 */       .addComponent(this.txtWarriorName, -2, 188, -2)
/* 240 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
/* 241 */       .addComponent(this.cmbGunnery, -2, -1, -2)))
/* 242 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 243 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 244 */       .addComponent(this.jLabel2)
/* 245 */       .addComponent(this.cmbPiloting, -2, -1, -2))
/* 246 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, 32767)
/* 247 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 248 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 249 */       .addGap(20, 20, 20)
/* 250 */       .addComponent(this.jLabel3)
/* 251 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 252 */       .addComponent(this.lblBaseBV))
/* 253 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 254 */       .addComponent(this.lblAdjustBVLabel)
/* 255 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 256 */       .addComponent(this.lblAdjustBV)))))
/* 257 */       .addContainerGap()));
/*     */     
/* 259 */     pnlBattleMechLayout.setVerticalGroup(pnlBattleMechLayout
/* 260 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 261 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 262 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 263 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 264 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 265 */       .addComponent(this.jLabel4)
/* 266 */       .addComponent(this.jLabel1)
/* 267 */       .addComponent(this.jLabel2))
/* 268 */       .addGap(1, 1, 1)
/* 269 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 270 */       .addComponent(this.txtWarriorName, -2, -1, -2)
/* 271 */       .addComponent(this.cmbOmniVariant, -2, -1, -2)
/* 272 */       .addComponent(this.cmbGunnery, -2, -1, -2)
/* 273 */       .addComponent(this.cmbPiloting, -2, -1, -2)))
/* 274 */       .addGroup(pnlBattleMechLayout.createSequentialGroup()
/* 275 */       .addContainerGap()
/* 276 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 277 */       .addComponent(this.jLabel3)
/* 278 */       .addComponent(this.lblBaseBV))
/* 279 */       .addGap(1, 1, 1)
/* 280 */       .addGroup(pnlBattleMechLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 281 */       .addComponent(this.lblAdjustBVLabel)
/* 282 */       .addComponent(this.lblAdjustBV))
/* 283 */       .addGap(145, 145, 145)
/* 284 */       .addComponent(this.jPanel2, -2, -1, -2)))
/* 285 */       .addContainerGap(-1, 32767)));
/*     */     
/*     */ 
/* 288 */     this.pnlPrintOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Print Options"));
/*     */     
/* 290 */     this.chkPrintCharts.setText("Print Tables and Movement Grid");
/* 291 */     this.chkPrintCharts.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 293 */         dlgPrintSavedMechOptions.this.chkPrintChartsActionPerformed(evt);
/*     */       }
/*     */       
/* 296 */     });
/* 297 */     this.chkUseHexConversion.setText("Use Miniatures Scale for Movement");
/* 298 */     this.chkUseHexConversion.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 300 */         dlgPrintSavedMechOptions.this.chkUseHexConversionActionPerformed(evt);
/*     */       }
/*     */       
/* 303 */     });
/* 304 */     this.lblOneHex.setText("One Hex equals");
/* 305 */     this.lblOneHex.setEnabled(false);
/*     */     
/* 307 */     this.cmbHexConvFactor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
/* 308 */     this.cmbHexConvFactor.setEnabled(false);
/*     */     
/* 310 */     this.lblInches.setText("Inches");
/* 311 */     this.lblInches.setEnabled(false);
/*     */     
/* 313 */     this.jLabel5.setText("Paper Size:");
/* 314 */     this.jPanel3.add(this.jLabel5);
/*     */     
/* 316 */     this.cmbPaperSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Letter", "A4" }));
/* 317 */     this.jPanel3.add(this.cmbPaperSize);
/*     */     
/* 319 */     this.chkMWStats.setText("Do NOT print MechWarrior stats");
/* 320 */     this.chkMWStats.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 322 */         dlgPrintSavedMechOptions.this.chkMWStatsActionPerformed(evt);
/*     */       }
/*     */       
/* 325 */     });
/* 326 */     this.chkPrintCanon.setText("Print Canon Dot Patterns");
/*     */     
/* 328 */     GroupLayout pnlPrintOptionsLayout = new GroupLayout(this.pnlPrintOptions);
/* 329 */     this.pnlPrintOptions.setLayout(pnlPrintOptionsLayout);
/* 330 */     pnlPrintOptionsLayout.setHorizontalGroup(pnlPrintOptionsLayout
/* 331 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 332 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 333 */       .addContainerGap()
/* 334 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 335 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 336 */       .addGap(21, 21, 21)
/* 337 */       .addComponent(this.lblOneHex)
/* 338 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 339 */       .addComponent(this.cmbHexConvFactor, -2, -1, -2)
/* 340 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 341 */       .addComponent(this.lblInches))
/* 342 */       .addComponent(this.chkUseHexConversion)
/* 343 */       .addComponent(this.chkPrintCanon)
/* 344 */       .addComponent(this.chkPrintCharts)
/* 345 */       .addComponent(this.jPanel3, -2, -1, -2)
/* 346 */       .addComponent(this.chkMWStats))
/* 347 */       .addContainerGap()));
/*     */     
/* 349 */     pnlPrintOptionsLayout.setVerticalGroup(pnlPrintOptionsLayout
/* 350 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 351 */       .addGroup(GroupLayout.Alignment.TRAILING, pnlPrintOptionsLayout.createSequentialGroup()
/* 352 */       .addComponent(this.chkPrintCharts)
/* 353 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 354 */       .addComponent(this.chkPrintCanon)
/* 355 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 356 */       .addComponent(this.chkUseHexConversion)
/* 357 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 358 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 359 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 360 */       .addComponent(this.cmbHexConvFactor, -2, -1, -2)
/* 361 */       .addComponent(this.lblOneHex))
/* 362 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 363 */       .addGap(3, 3, 3)
/* 364 */       .addComponent(this.lblInches)))
/* 365 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, 32767)
/* 366 */       .addComponent(this.chkMWStats)
/* 367 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
/* 368 */       .addComponent(this.jPanel3, -2, -1, -2)
/* 369 */       .addContainerGap()));
/*     */     
/*     */ 
/* 372 */     this.pnlImageOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Image Options"));
/*     */     
/* 374 */     this.chkPrintImage.setText("Print Mech");
/* 375 */     this.chkPrintImage.setToolTipText("From Mech file");
/* 376 */     this.chkPrintImage.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 378 */         dlgPrintSavedMechOptions.this.chkPrintImageActionPerformed(evt);
/*     */       }
/*     */       
/* 381 */     });
/* 382 */     this.btnChooseImage.setText("Choose Image");
/* 383 */     this.btnChooseImage.setEnabled(false);
/* 384 */     this.btnChooseImage.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 386 */         dlgPrintSavedMechOptions.this.btnChooseImageActionPerformed(evt);
/*     */       }
/*     */       
/* 389 */     });
/* 390 */     this.lblImage.setBackground(new java.awt.Color(255, 255, 255));
/* 391 */     this.lblImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 392 */     this.lblImage.setOpaque(true);
/*     */     
/* 394 */     this.chkLogo.setText("Print Logo");
/* 395 */     this.chkLogo.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 397 */         dlgPrintSavedMechOptions.this.chkLogoActionPerformed(evt);
/*     */       }
/*     */       
/* 400 */     });
/* 401 */     this.chkStats.setText("Print Statistics");
/* 402 */     this.chkStats.setEnabled(false);
/* 403 */     this.chkStats.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 405 */         dlgPrintSavedMechOptions.this.chkStatsActionPerformed(evt);
/*     */       }
/*     */       
/* 408 */     });
/* 409 */     this.lblLogo.setBackground(new java.awt.Color(255, 255, 255));
/* 410 */     this.lblLogo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 411 */     this.lblLogo.setOpaque(true);
/*     */     
/* 413 */     this.lblStats.setBackground(new java.awt.Color(255, 255, 255));
/* 414 */     this.lblStats.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/* 415 */     this.lblStats.setEnabled(false);
/* 416 */     this.lblStats.setOpaque(true);
/*     */     
/* 418 */     this.btnChooseLogo.setText("Choose Logo");
/* 419 */     this.btnChooseLogo.setEnabled(false);
/* 420 */     this.btnChooseLogo.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 422 */         dlgPrintSavedMechOptions.this.btnChooseLogoActionPerformed(evt);
/*     */       }
/*     */       
/* 425 */     });
/* 426 */     GroupLayout pnlImageOptionsLayout = new GroupLayout(this.pnlImageOptions);
/* 427 */     this.pnlImageOptions.setLayout(pnlImageOptionsLayout);
/* 428 */     pnlImageOptionsLayout.setHorizontalGroup(pnlImageOptionsLayout
/* 429 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 430 */       .addGroup(pnlImageOptionsLayout.createSequentialGroup()
/* 431 */       .addContainerGap()
/* 432 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 433 */       .addComponent(this.btnChooseImage)
/* 434 */       .addComponent(this.chkPrintImage, -1, 127, 32767)
/* 435 */       .addComponent(this.lblImage, -2, 110, -2))
/* 436 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 437 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 438 */       .addGroup(pnlImageOptionsLayout.createSequentialGroup()
/* 439 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 440 */       .addComponent(this.chkLogo, GroupLayout.Alignment.LEADING)
/* 441 */       .addComponent(this.lblLogo, GroupLayout.Alignment.LEADING, -2, 110, -2))
/* 442 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 443 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/* 444 */       .addComponent(this.lblStats, -2, 110, -2)
/* 445 */       .addComponent(this.chkStats, -1, -1, 32767)))
/* 446 */       .addComponent(this.btnChooseLogo))));
/*     */     
/* 448 */     pnlImageOptionsLayout.setVerticalGroup(pnlImageOptionsLayout
/* 449 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 450 */       .addGroup(pnlImageOptionsLayout.createSequentialGroup()
/* 451 */       .addContainerGap()
/* 452 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 453 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 454 */       .addComponent(this.chkLogo)
/* 455 */       .addComponent(this.chkStats))
/* 456 */       .addComponent(this.chkPrintImage))
/* 457 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 458 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 459 */       .addComponent(this.lblLogo, -2, 116, -2)
/* 460 */       .addComponent(this.lblStats, -2, 116, -2)
/* 461 */       .addComponent(this.lblImage, -2, 116, -2))
/* 462 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/* 463 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 464 */       .addComponent(this.btnChooseImage)
/* 465 */       .addComponent(this.btnChooseLogo))));
/*     */     
/*     */ 
/* 468 */     this.btnChangeAmmo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/ammo.png")));
/* 469 */     this.btnChangeAmmo.setText("Change Ammo");
/* 470 */     this.btnChangeAmmo.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 472 */         dlgPrintSavedMechOptions.this.btnChangeAmmoActionPerformed(evt);
/*     */       }
/* 474 */     });
/* 475 */     this.jPanel5.add(this.btnChangeAmmo);
/*     */     
/* 477 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 478 */     getContentPane().setLayout(layout);
/* 479 */     layout.setHorizontalGroup(layout
/* 480 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 481 */       .addGroup(layout.createSequentialGroup()
/* 482 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
/* 483 */       .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
/* 484 */       .addGap(18, 18, 18)
/* 485 */       .addComponent(this.pnlBattleMech, -1, -1, 32767))
/* 486 */       .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
/* 487 */       .addContainerGap()
/* 488 */       .addComponent(this.jPanel4, -2, -1, -2)
/* 489 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 490 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/* 491 */       .addGroup(layout.createSequentialGroup()
/* 492 */       .addComponent(this.jPanel5, -2, -1, -2)
/* 493 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/* 494 */       .addComponent(this.jPanel1, -2, -1, -2))
/* 495 */       .addGroup(layout.createSequentialGroup()
/* 496 */       .addComponent(this.pnlPrintOptions, -2, -1, -2)
/* 497 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 498 */       .addComponent(this.pnlImageOptions, -2, -1, -2)))))
/* 499 */       .addContainerGap()));
/*     */     
/* 501 */     layout.setVerticalGroup(layout
/* 502 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 503 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 504 */       .addContainerGap(12, 32767)
/* 505 */       .addComponent(this.pnlBattleMech, -2, 85, -2)
/* 506 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 507 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 508 */       .addComponent(this.pnlImageOptions, -2, -1, -2)
/* 509 */       .addComponent(this.pnlPrintOptions, -2, -1, -2))
/* 510 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 511 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 512 */       .addComponent(this.jPanel4, -2, -1, -2)
/* 513 */       .addComponent(this.jPanel5, -2, -1, -2)
/* 514 */       .addComponent(this.jPanel1, -2, -1, -2))));
/*     */     
/*     */ 
/* 517 */     pack(); }
/*     */   
/*     */   private JComboBox cmbPiloting;
/*     */   
/* 521 */   private void btnPrintActionPerformed(ActionEvent evt) { if (this.Parent != null) {
/* 522 */       this.Parent.GetPrefs().putBoolean("UseA4", UseA4Paper());
/* 523 */       this.Parent.GetPrefs().putBoolean("UseCharts", this.chkPrintCharts.isSelected());
/* 524 */       this.Parent.GetPrefs().putBoolean("NoPilot", this.chkMWStats.isSelected());
/* 525 */       this.Parent.GetPrefs().putBoolean("UseMiniConversion", this.chkUseHexConversion.isSelected());
/* 526 */       this.Parent.GetPrefs().putInt("MiniConversionRate", this.cmbHexConvFactor.getSelectedIndex());
/* 527 */       this.Parent.GetPrefs().putBoolean("UseCanonDots", this.chkPrintCanon.isSelected());
/*     */     }
/*     */     
/* 530 */     this.Result = true;
/* 531 */     setVisible(false); }
/*     */   
/*     */   private JLabel jLabel1;
/*     */   
/* 535 */   private void btnCancelActionPerformed(ActionEvent evt) { setVisible(false); }
/*     */   
/*     */   private JLabel jLabel2;
/*     */   
/* 539 */   private void cmbGunneryActionPerformed(ActionEvent evt) { this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(common.CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) })); }
/*     */   
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/* 543 */   private void cmbPilotingActionPerformed(ActionEvent evt) { this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(common.CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) })); }
/*     */   
/*     */   private void chkMWStatsActionPerformed(ActionEvent evt)
/*     */   {
/* 547 */     if (this.pnlBattleMech.isVisible()) {
/* 548 */       if (this.chkMWStats.isSelected()) {
/* 549 */         this.lblAdjustBV.setText(String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/* 550 */         this.cmbGunnery.setSelectedItem("4");
/* 551 */         this.cmbPiloting.setSelectedItem("5");
/* 552 */         this.cmbGunnery.setEnabled(false);
/* 553 */         this.cmbPiloting.setEnabled(false);
/* 554 */         this.txtWarriorName.setEnabled(false);
/*     */       } else {
/* 556 */         this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(common.CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) }));
/* 557 */         this.cmbGunnery.setEnabled(true);
/* 558 */         this.cmbPiloting.setEnabled(true);
/* 559 */         this.txtWarriorName.setEnabled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void cmbOmniVariantActionPerformed(ActionEvent evt) {
/* 565 */     this.CurMech.SetCurLoadout((String)this.cmbOmniVariant.getSelectedItem());
/* 566 */     this.lblBaseBV.setText(String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/* 567 */     this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(common.CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) }));
/*     */   }
/*     */   
/*     */   private void chkUseHexConversionActionPerformed(ActionEvent evt) {
/* 571 */     if (this.chkUseHexConversion.isSelected()) {
/* 572 */       this.lblOneHex.setEnabled(true);
/* 573 */       this.cmbHexConvFactor.setEnabled(true);
/* 574 */       this.lblInches.setEnabled(true);
/*     */     } else {
/* 576 */       this.lblOneHex.setEnabled(false);
/* 577 */       this.cmbHexConvFactor.setEnabled(false);
/* 578 */       this.lblInches.setEnabled(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void btnChooseImageActionPerformed(ActionEvent evt) {
/* 583 */     String defaultDir = "";
/* 584 */     if (this.Parent != null) defaultDir = this.Parent.GetPrefs().get("LastImagePath", "");
/* 585 */     this.MechImage = this.media.SelectImage(defaultDir, "Select Image");
/*     */     try
/*     */     {
/* 588 */       if (this.Parent != null) {
/* 589 */         this.Parent.GetPrefs().put("LastImage", this.MechImage.getCanonicalPath());
/* 590 */         this.Parent.GetPrefs().put("LastImagePath", this.MechImage.getCanonicalPath().replace(this.MechImage.getName(), ""));
/* 591 */         this.Parent.GetPrefs().put("LastImageFile", this.MechImage.getName());
/*     */       }
/*     */       
/* 594 */       this.media.setLogo(this.lblImage, this.MechImage);
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   private void chkPrintImageActionPerformed(ActionEvent evt)
/*     */   {
/* 601 */     if (this.CurMech != null) {
/* 602 */       this.btnChooseImage.setEnabled(this.chkPrintImage.isSelected());
/*     */     }
/*     */   }
/*     */   
/*     */   private void chkLogoActionPerformed(ActionEvent evt) {
/* 607 */     if (this.CurMech != null) this.btnChooseLogo.setEnabled(this.chkLogo.isSelected());
/*     */   }
/*     */   
/*     */   private void chkStatsActionPerformed(ActionEvent evt) {
/* 611 */     this.chkPrintImage.setEnabled(!this.chkStats.isSelected());
/* 612 */     this.btnChooseImage.setEnabled(!this.chkStats.isSelected());
/*     */   }
/*     */   
/*     */   private void btnChooseLogoActionPerformed(ActionEvent evt) {
/* 616 */     String defaultDir = "";
/* 617 */     if (this.Parent != null) defaultDir = this.Parent.GetPrefs().get("LastLogo", "");
/* 618 */     this.LogoImage = this.media.SelectImage(defaultDir, "Select Logo");
/*     */     try
/*     */     {
/* 621 */       if (this.Parent != null) {
/* 622 */         this.Parent.GetPrefs().put("LastLogo", this.LogoImage.getCanonicalPath());
/* 623 */         this.Parent.GetPrefs().put("LastLogoPath", this.LogoImage.getCanonicalPath().replace(this.LogoImage.getName(), ""));
/* 624 */         this.Parent.GetPrefs().put("LastLogoFile", this.LogoImage.getName());
/*     */       }
/*     */       
/* 627 */       this.media.setLogo(this.lblLogo, this.LogoImage);
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   private JLabel jLabel5;
/*     */   private JPanel jPanel1;
/*     */   
/*     */   private void btnChangeAmmoActionPerformed(ActionEvent evt) {
/*     */     try {
/* 636 */       dialog.dlgAmmoChooser Ammo = new dialog.dlgAmmoChooser(this, false, this.CurMech, this.Parent.GetData());
/* 637 */       Ammo.setLocationRelativeTo(this);
/* 638 */       if (Ammo.HasAmmo()) {
/* 639 */         Ammo.setVisible(true);
/*     */       } else {
/* 641 */         filehandlers.Media.Messager(this, "This 'Mech has no ammunition to exchange.");
/* 642 */         Ammo.dispose();
/*     */       }
/*     */     } catch (Exception e) {
/* 645 */       filehandlers.Media.Messager(this, "There was an error altering the ammunition on this 'Mech:\n" + e.getMessage());
/*     */     } }
/*     */   
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   
/*     */   private void chkPrintChartsActionPerformed(ActionEvent evt) {}
/*     */   private JPanel jPanel4;
/*     */   
/* 654 */   public boolean Result() { return this.Result; }
/*     */   
/*     */   private JPanel jPanel5;
/*     */   
/* 658 */   public boolean PrintPilot() { return !this.chkMWStats.isSelected(); }
/*     */   
/*     */   private JLabel lblAdjustBV;
/*     */   private JLabel lblAdjustBVLabel;
/* 662 */   public String GetWarriorName() { return this.txtWarriorName.getText(); }
/*     */   
/*     */   public int GetGunnery()
/*     */   {
/* 666 */     return this.cmbGunnery.getSelectedIndex();
/*     */   }
/*     */   
/*     */   public int GetPiloting() {
/* 670 */     return this.cmbPiloting.getSelectedIndex();
/*     */   }
/*     */   
/*     */   public boolean AdjustBV() {
/* 674 */     return true;
/*     */   }
/*     */   
/*     */   public double GetAdjustedBV() {
/* 678 */     return common.CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex());
/*     */   }
/*     */   
/*     */   public boolean PrintCharts() {
/* 682 */     return this.chkPrintCharts.isSelected();
/*     */   }
/*     */   
/*     */   public boolean UseA4Paper() {
/* 686 */     if (this.cmbPaperSize.getSelectedIndex() == 0) {
/* 687 */       return false;
/*     */     }
/* 689 */     return true; }
/*     */   private JLabel lblBaseBV;
/*     */   private JLabel lblImage;
/*     */   private JLabel lblInches;
/*     */   
/* 694 */   public boolean UseMiniConversion() { return this.chkUseHexConversion.isSelected(); }
/*     */   
/*     */   private JLabel lblLogo;
/*     */   
/* 698 */   public int GetMiniConversionRate() { switch (this.cmbHexConvFactor.getSelectedIndex()) {
/*     */     case 0: 
/* 700 */       return 1;
/*     */     case 1: 
/* 702 */       return 2;
/*     */     case 2: 
/* 704 */       return 3;
/*     */     case 3: 
/* 706 */       return 4;
/*     */     case 4: 
/* 708 */       return 5;
/*     */     }
/* 710 */     return 1; }
/*     */   
/*     */   private JLabel lblOneHex;
/*     */   private JLabel lblStats;
/*     */   
/* 715 */   public boolean getCanon() { return this.chkPrintCanon.isSelected(); }
/*     */   
/*     */   public java.awt.Image getImage() {
/* 718 */     if ((this.chkPrintImage.isSelected()) && (this.MechImage != null)) {
/*     */       try {
/* 720 */         return this.media.GetImage(this.MechImage.getCanonicalPath());
/*     */       } catch (java.io.IOException ex) {
/* 722 */         return null;
/*     */       } catch (NullPointerException np) {
/* 724 */         return null;
/*     */       }
/*     */     }
/* 727 */     return null; }
/*     */   
/*     */   private JPanel pnlBattleMech;
/*     */   private JPanel pnlImageOptions;
/*     */   
/* 732 */   public java.awt.Image getLogo() { if ((this.chkLogo.isSelected()) && (this.LogoImage != null)) {
/*     */       try {
/* 734 */         return this.media.GetImage(this.LogoImage.getCanonicalPath());
/*     */       } catch (java.io.IOException ex) {
/* 736 */         return null;
/*     */       } catch (NullPointerException np) {
/* 738 */         return null;
/*     */       }
/*     */     }
/* 741 */     return null;
/*     */   }
/*     */   
/*     */   private JPanel pnlPrintOptions;
/*     */   private javax.swing.JTextField txtWarriorName;
/*     */   public void setLogo(java.io.File logo) {
/* 747 */     this.LogoImage = logo;
/* 748 */     this.media.setLogo(this.lblLogo, logo);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgPrintSavedMechOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */