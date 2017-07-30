/*     */ package ssw.gui;
/*     */ 
/*     */ import common.CommonTools;
/*     */ import components.Mech;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Vector;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.PrintServiceLookup;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
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
/*     */ public class dlgPrintOptions
/*     */   extends JDialog
/*     */ {
/*     */   private Mech CurMech;
/*     */   private ifMechForm Parent;
/*  39 */   private boolean Result = false;
/*     */   private Vector printers;
/*     */   private JButton btnCancel;
/*     */   private JButton btnPrint;
/*     */   private JCheckBox chkAdjustBV; private JCheckBox chkMWStats;
/*  44 */   public dlgPrintOptions(Frame parent, boolean modal, Mech m) { super(parent, modal);
/*  45 */     initComponents();
/*     */     
/*  47 */     PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
/*  48 */     for (PrintService printer : services) { this.printers.add(printer);
/*     */     }
/*  50 */     for (int i = 0; i <= this.printers.size() - 1; i++) {}
/*     */     
/*     */ 
/*  53 */     this.Parent = ((ifMechForm)parent);
/*  54 */     this.CurMech = m;
/*  55 */     this.cmbGunnery.setSelectedIndex(4);
/*  56 */     this.cmbPiloting.setSelectedIndex(5);
/*     */     
/*  58 */     this.chkPrintCharts.setSelected(this.Parent.GetPrefs().getBoolean("UseCharts", false));
/*  59 */     this.chkMWStats.setSelected(this.Parent.GetPrefs().getBoolean("NoPilot", false));
/*  60 */     if (this.chkMWStats.isSelected()) {
/*  61 */       this.lblAdjustBV.setText(String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/*  62 */       this.chkAdjustBV.setSelected(false);
/*  63 */       this.chkAdjustBV.setEnabled(false);
/*  64 */       this.cmbGunnery.setEnabled(false);
/*  65 */       this.cmbPiloting.setEnabled(false);
/*  66 */       this.txtWarriorName.setEnabled(false);
/*     */     } else {
/*  68 */       this.chkAdjustBV.setSelected(this.Parent.GetPrefs().getBoolean("AdjustPG", false));
/*  69 */       this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) }));
/*     */     }
/*  71 */     if (this.Parent.GetPrefs().getBoolean("UseA4", false)) {
/*  72 */       this.cmbPaperSize.setSelectedIndex(1);
/*     */     }
/*  74 */     this.chkUseHexConversion.setEnabled(this.Parent.GetPrefs().getBoolean("UseMiniConversion", false));
/*  75 */     if (this.chkUseHexConversion.isSelected()) {
/*  76 */       this.lblOneHex.setEnabled(true);
/*  77 */       this.cmbHexConvFactor.setEnabled(true);
/*  78 */       this.lblInches.setEnabled(true);
/*  79 */       this.cmbHexConvFactor.setSelectedIndex(this.Parent.GetPrefs().getInt("MiniConversionRate", 0));
/*     */     } }
/*     */   
/*     */   private JCheckBox chkPrintCharts;
/*     */   
/*  84 */   public boolean Result() { return this.Result; }
/*     */   
/*     */   private JCheckBox chkUseHexConversion;
/*     */   
/*  88 */   public boolean PrintPilot() { return !this.chkMWStats.isSelected(); }
/*     */   
/*     */   private JComboBox cmbGunnery;
/*     */   
/*  92 */   public String GetWarriorName() { return this.txtWarriorName.getText(); }
/*     */   
/*     */   private JComboBox cmbHexConvFactor;
/*     */   
/*  96 */   public int GetGunnery() { return this.cmbGunnery.getSelectedIndex(); }
/*     */   
/*     */   private JComboBox cmbPaperSize;
/*     */   
/* 100 */   public int GetPiloting() { return this.cmbPiloting.getSelectedIndex(); }
/*     */   
/*     */   private JComboBox cmbPiloting;
/*     */   public boolean AdjustBV() {
/* 104 */     return this.chkAdjustBV.isSelected();
/*     */   }
/*     */   
/*     */   public double GetAdjustedBV() {
/* 108 */     return CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex());
/*     */   }
/*     */   
/*     */   public boolean PrintCharts() {
/* 112 */     return this.chkPrintCharts.isSelected();
/*     */   }
/*     */   
/*     */   public boolean UseA4Paper() {
/* 116 */     if (this.cmbPaperSize.getSelectedIndex() == 0) {
/* 117 */       return false;
/*     */     }
/* 119 */     return true;
/*     */   }
/*     */   
/*     */   public boolean UseMiniConversion()
/*     */   {
/* 124 */     return this.chkUseHexConversion.isSelected();
/*     */   }
/*     */   
/*     */   public double GetMiniConversionRate() {
/* 128 */     switch (this.cmbHexConvFactor.getSelectedIndex()) {
/*     */     case 0: 
/* 130 */       return 0.5D;
/*     */     case 1: 
/* 132 */       return 1.0D;
/*     */     case 2: 
/* 134 */       return 1.5D;
/*     */     case 3: 
/* 136 */       return 2.0D;
/*     */     case 4: 
/* 138 */       return 3.0D;
/*     */     case 5: 
/* 140 */       return 4.0D;
/*     */     case 6: 
/* 142 */       return 5.0D;
/*     */     }
/* 144 */     return 1.0D; }
/*     */   
/*     */   private JComboBox cmbPrinters;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JPanel jPanel4;
/*     */   private JPanel jPanel5;
/*     */   
/* 158 */   private void initComponents() { this.txtWarriorName = new JTextField();
/* 159 */     this.chkAdjustBV = new JCheckBox();
/* 160 */     this.chkPrintCharts = new JCheckBox();
/* 161 */     this.lblAdjustBVLabel = new JLabel();
/* 162 */     this.jPanel1 = new JPanel();
/* 163 */     this.btnPrint = new JButton();
/* 164 */     this.btnCancel = new JButton();
/* 165 */     this.jPanel2 = new JPanel();
/* 166 */     this.cmbGunnery = new JComboBox();
/* 167 */     this.jLabel1 = new JLabel();
/* 168 */     this.jLabel2 = new JLabel();
/* 169 */     this.cmbPiloting = new JComboBox();
/* 170 */     this.jLabel3 = new JLabel();
/* 171 */     this.lblAdjustBV = new JLabel();
/* 172 */     this.chkMWStats = new JCheckBox();
/* 173 */     this.jPanel3 = new JPanel();
/* 174 */     this.jLabel5 = new JLabel();
/* 175 */     this.cmbPaperSize = new JComboBox();
/* 176 */     this.jPanel4 = new JPanel();
/* 177 */     this.jLabel4 = new JLabel();
/* 178 */     this.cmbPrinters = new JComboBox();
/* 179 */     this.chkUseHexConversion = new JCheckBox();
/* 180 */     this.jPanel5 = new JPanel();
/* 181 */     this.lblOneHex = new JLabel();
/* 182 */     this.cmbHexConvFactor = new JComboBox();
/* 183 */     this.lblInches = new JLabel();
/*     */     
/* 185 */     setDefaultCloseOperation(2);
/* 186 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/* 188 */     this.txtWarriorName.setMaximumSize(new Dimension(150, 20));
/* 189 */     this.txtWarriorName.setMinimumSize(new Dimension(150, 20));
/* 190 */     this.txtWarriorName.setPreferredSize(new Dimension(150, 20));
/* 191 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 192 */     gridBagConstraints.gridx = 0;
/* 193 */     gridBagConstraints.gridy = 2;
/* 194 */     gridBagConstraints.fill = 2;
/* 195 */     gridBagConstraints.anchor = 17;
/* 196 */     getContentPane().add(this.txtWarriorName, gridBagConstraints);
/*     */     
/* 198 */     this.chkAdjustBV.setText("Adjust BV for Gunnery/Piloting");
/* 199 */     this.chkAdjustBV.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 201 */         dlgPrintOptions.this.chkAdjustBVActionPerformed(evt);
/*     */       }
/* 203 */     });
/* 204 */     gridBagConstraints = new GridBagConstraints();
/* 205 */     gridBagConstraints.gridx = 0;
/* 206 */     gridBagConstraints.gridy = 4;
/* 207 */     gridBagConstraints.anchor = 17;
/* 208 */     getContentPane().add(this.chkAdjustBV, gridBagConstraints);
/*     */     
/* 210 */     this.chkPrintCharts.setText("Print helpful charts on the sheet");
/* 211 */     gridBagConstraints = new GridBagConstraints();
/* 212 */     gridBagConstraints.gridx = 0;
/* 213 */     gridBagConstraints.gridy = 5;
/* 214 */     gridBagConstraints.anchor = 17;
/* 215 */     getContentPane().add(this.chkPrintCharts, gridBagConstraints);
/*     */     
/* 217 */     this.lblAdjustBVLabel.setText("Adjusted BV:");
/* 218 */     gridBagConstraints = new GridBagConstraints();
/* 219 */     gridBagConstraints.gridx = 1;
/* 220 */     gridBagConstraints.gridy = 4;
/* 221 */     gridBagConstraints.anchor = 15;
/* 222 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 223 */     getContentPane().add(this.lblAdjustBVLabel, gridBagConstraints);
/*     */     
/* 225 */     this.btnPrint.setText("Print");
/* 226 */     this.btnPrint.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 228 */         dlgPrintOptions.this.btnPrintActionPerformed(evt);
/*     */       }
/* 230 */     });
/* 231 */     this.jPanel1.add(this.btnPrint);
/*     */     
/* 233 */     this.btnCancel.setText("Cancel");
/* 234 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 236 */         dlgPrintOptions.this.btnCancelActionPerformed(evt);
/*     */       }
/* 238 */     });
/* 239 */     this.jPanel1.add(this.btnCancel);
/*     */     
/* 241 */     gridBagConstraints = new GridBagConstraints();
/* 242 */     gridBagConstraints.gridx = 0;
/* 243 */     gridBagConstraints.gridy = 9;
/* 244 */     gridBagConstraints.gridwidth = 2;
/* 245 */     gridBagConstraints.anchor = 13;
/* 246 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 247 */     getContentPane().add(this.jPanel1, gridBagConstraints);
/*     */     
/* 249 */     this.jPanel2.setLayout(new GridBagLayout());
/*     */     
/* 251 */     this.cmbGunnery.setModel(new DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" }));
/* 252 */     this.cmbGunnery.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 254 */         dlgPrintOptions.this.cmbGunneryActionPerformed(evt);
/*     */       }
/* 256 */     });
/* 257 */     gridBagConstraints = new GridBagConstraints();
/* 258 */     gridBagConstraints.gridx = 0;
/* 259 */     gridBagConstraints.gridy = 1;
/* 260 */     this.jPanel2.add(this.cmbGunnery, gridBagConstraints);
/*     */     
/* 262 */     this.jLabel1.setText("Gunnery");
/* 263 */     this.jPanel2.add(this.jLabel1, new GridBagConstraints());
/*     */     
/* 265 */     this.jLabel2.setText("Piloting");
/* 266 */     gridBagConstraints = new GridBagConstraints();
/* 267 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/* 268 */     this.jPanel2.add(this.jLabel2, gridBagConstraints);
/*     */     
/* 270 */     this.cmbPiloting.setModel(new DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" }));
/* 271 */     this.cmbPiloting.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 273 */         dlgPrintOptions.this.cmbPilotingActionPerformed(evt);
/*     */       }
/* 275 */     });
/* 276 */     gridBagConstraints = new GridBagConstraints();
/* 277 */     gridBagConstraints.gridx = 1;
/* 278 */     gridBagConstraints.gridy = 1;
/* 279 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/* 280 */     this.jPanel2.add(this.cmbPiloting, gridBagConstraints);
/*     */     
/* 282 */     gridBagConstraints = new GridBagConstraints();
/* 283 */     gridBagConstraints.gridx = 1;
/* 284 */     gridBagConstraints.gridy = 2;
/* 285 */     gridBagConstraints.gridheight = 2;
/* 286 */     gridBagConstraints.anchor = 17;
/* 287 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 288 */     getContentPane().add(this.jPanel2, gridBagConstraints);
/*     */     
/* 290 */     this.jLabel3.setText("MechWarrior Name");
/* 291 */     gridBagConstraints = new GridBagConstraints();
/* 292 */     gridBagConstraints.gridx = 0;
/* 293 */     gridBagConstraints.gridy = 1;
/* 294 */     gridBagConstraints.anchor = 17;
/* 295 */     getContentPane().add(this.jLabel3, gridBagConstraints);
/*     */     
/* 297 */     this.lblAdjustBV.setText("00,000");
/* 298 */     gridBagConstraints = new GridBagConstraints();
/* 299 */     gridBagConstraints.gridx = 1;
/* 300 */     gridBagConstraints.gridy = 5;
/* 301 */     gridBagConstraints.anchor = 11;
/* 302 */     getContentPane().add(this.lblAdjustBV, gridBagConstraints);
/*     */     
/* 304 */     this.chkMWStats.setText("Do not print MechWarrior stats");
/* 305 */     this.chkMWStats.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 307 */         dlgPrintOptions.this.chkMWStatsActionPerformed(evt);
/*     */       }
/* 309 */     });
/* 310 */     gridBagConstraints = new GridBagConstraints();
/* 311 */     gridBagConstraints.gridx = 0;
/* 312 */     gridBagConstraints.gridy = 3;
/* 313 */     gridBagConstraints.anchor = 17;
/* 314 */     getContentPane().add(this.chkMWStats, gridBagConstraints);
/*     */     
/* 316 */     this.jLabel5.setText("Paper Size:");
/* 317 */     this.jPanel3.add(this.jLabel5);
/*     */     
/* 319 */     this.cmbPaperSize.setModel(new DefaultComboBoxModel(new String[] { "Letter", "A4" }));
/* 320 */     this.jPanel3.add(this.cmbPaperSize);
/*     */     
/* 322 */     gridBagConstraints = new GridBagConstraints();
/* 323 */     gridBagConstraints.gridx = 0;
/* 324 */     gridBagConstraints.gridy = 8;
/* 325 */     gridBagConstraints.anchor = 17;
/* 326 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 327 */     getContentPane().add(this.jPanel3, gridBagConstraints);
/*     */     
/* 329 */     this.jLabel4.setText("Printer:");
/* 330 */     this.jPanel4.add(this.jLabel4);
/*     */     
/* 332 */     this.jPanel4.add(this.cmbPrinters);
/*     */     
/* 334 */     gridBagConstraints = new GridBagConstraints();
/* 335 */     gridBagConstraints.gridx = 1;
/* 336 */     gridBagConstraints.gridy = 8;
/* 337 */     gridBagConstraints.anchor = 17;
/* 338 */     getContentPane().add(this.jPanel4, gridBagConstraints);
/*     */     
/* 340 */     this.chkUseHexConversion.setText("Convert Hexes to miniature scale");
/* 341 */     this.chkUseHexConversion.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 343 */         dlgPrintOptions.this.chkUseHexConversionActionPerformed(evt);
/*     */       }
/* 345 */     });
/* 346 */     gridBagConstraints = new GridBagConstraints();
/* 347 */     gridBagConstraints.gridx = 0;
/* 348 */     gridBagConstraints.gridy = 6;
/* 349 */     gridBagConstraints.gridwidth = 2;
/* 350 */     gridBagConstraints.anchor = 17;
/* 351 */     getContentPane().add(this.chkUseHexConversion, gridBagConstraints);
/*     */     
/* 353 */     this.jPanel5.setLayout(new GridBagLayout());
/*     */     
/* 355 */     this.lblOneHex.setText("One Hex equals");
/* 356 */     this.lblOneHex.setEnabled(false);
/* 357 */     gridBagConstraints = new GridBagConstraints();
/* 358 */     gridBagConstraints.gridx = 0;
/* 359 */     gridBagConstraints.gridy = 0;
/* 360 */     gridBagConstraints.anchor = 13;
/* 361 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 362 */     this.jPanel5.add(this.lblOneHex, gridBagConstraints);
/*     */     
/* 364 */     this.cmbHexConvFactor.setModel(new DefaultComboBoxModel(new String[] { "1/2", "1", "1 1/2", "2", "3", "4", "5" }));
/* 365 */     this.cmbHexConvFactor.setEnabled(false);
/* 366 */     gridBagConstraints = new GridBagConstraints();
/* 367 */     gridBagConstraints.gridx = 1;
/* 368 */     gridBagConstraints.gridy = 0;
/* 369 */     this.jPanel5.add(this.cmbHexConvFactor, gridBagConstraints);
/*     */     
/* 371 */     this.lblInches.setText("Inches");
/* 372 */     this.lblInches.setEnabled(false);
/* 373 */     gridBagConstraints = new GridBagConstraints();
/* 374 */     gridBagConstraints.gridx = 2;
/* 375 */     gridBagConstraints.gridy = 0;
/* 376 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/* 377 */     this.jPanel5.add(this.lblInches, gridBagConstraints);
/*     */     
/* 379 */     gridBagConstraints = new GridBagConstraints();
/* 380 */     gridBagConstraints.gridx = 0;
/* 381 */     gridBagConstraints.gridy = 7;
/* 382 */     gridBagConstraints.gridwidth = 2;
/* 383 */     gridBagConstraints.anchor = 17;
/* 384 */     getContentPane().add(this.jPanel5, gridBagConstraints);
/*     */     
/* 386 */     pack(); }
/*     */   
/*     */   private JLabel lblAdjustBV;
/*     */   
/* 390 */   private void cmbPilotingActionPerformed(ActionEvent evt) { if (this.chkAdjustBV.isSelected())
/* 391 */       this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) }));
/*     */   }
/*     */   
/*     */   private JLabel lblAdjustBVLabel;
/*     */   
/* 396 */   private void cmbGunneryActionPerformed(ActionEvent evt) { if (this.chkAdjustBV.isSelected())
/* 397 */       this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) })); }
/*     */   
/*     */   private JLabel lblInches;
/*     */   private JLabel lblOneHex;
/*     */   private JTextField txtWarriorName;
/* 402 */   private void btnPrintActionPerformed(ActionEvent evt) { this.Parent.GetPrefs().putBoolean("UseA4", UseA4Paper());
/* 403 */     this.Parent.GetPrefs().putBoolean("UseCharts", this.chkPrintCharts.isSelected());
/* 404 */     this.Parent.GetPrefs().putBoolean("AdjustPG", this.chkAdjustBV.isSelected());
/* 405 */     this.Parent.GetPrefs().putBoolean("NoPilot", this.chkMWStats.isSelected());
/* 406 */     this.Parent.GetPrefs().putBoolean("UseMiniConversion", this.chkUseHexConversion.isSelected());
/* 407 */     this.Parent.GetPrefs().putInt("MiniConversionRate", this.cmbHexConvFactor.getSelectedIndex());
/*     */     
/* 409 */     this.Result = true;
/* 410 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnCancelActionPerformed(ActionEvent evt) {
/* 414 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void chkAdjustBVActionPerformed(ActionEvent evt) {
/* 418 */     if (this.chkAdjustBV.isSelected()) {
/* 419 */       this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) }));
/*     */     } else {
/* 421 */       this.lblAdjustBV.setText(String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/*     */     }
/*     */   }
/*     */   
/*     */   private void chkMWStatsActionPerformed(ActionEvent evt) {
/* 426 */     if (this.chkMWStats.isSelected()) {
/* 427 */       this.lblAdjustBV.setText(String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/* 428 */       this.chkAdjustBV.setSelected(false);
/* 429 */       this.chkAdjustBV.setEnabled(false);
/* 430 */       this.cmbGunnery.setEnabled(false);
/* 431 */       this.cmbPiloting.setEnabled(false);
/* 432 */       this.txtWarriorName.setEnabled(false);
/*     */     } else {
/* 434 */       this.lblAdjustBV.setText(String.format("%1$,.0f", new Object[] { Double.valueOf(CommonTools.GetAdjustedBV(this.CurMech.GetCurrentBV(), this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex())) }));
/* 435 */       this.cmbGunnery.setEnabled(true);
/* 436 */       this.cmbPiloting.setEnabled(true);
/* 437 */       this.txtWarriorName.setEnabled(true);
/* 438 */       this.chkAdjustBV.setEnabled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private void chkUseHexConversionActionPerformed(ActionEvent evt) {
/* 443 */     if (this.chkUseHexConversion.isSelected()) {
/* 444 */       this.lblOneHex.setEnabled(true);
/* 445 */       this.cmbHexConvFactor.setEnabled(true);
/* 446 */       this.lblInches.setEnabled(true);
/*     */     } else {
/* 448 */       this.lblOneHex.setEnabled(false);
/* 449 */       this.cmbHexConvFactor.setEnabled(false);
/* 450 */       this.lblInches.setEnabled(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgPrintOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */