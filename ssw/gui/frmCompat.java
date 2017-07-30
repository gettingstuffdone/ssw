/*     */ package ssw.gui;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToolBar;
/*     */ 
/*     */ public class frmCompat extends javax.swing.JFrame
/*     */ {
/*     */   private JButton btnAddToForceList;
/*     */   private JButton btnChatInfo;
/*     */   private JButton btnExportClipboardIcon;
/*     */   private JButton btnExportHTMLIcon;
/*     */   private JButton btnExportMTFIcon;
/*     */   private JButton btnExportTextIcon;
/*     */   private JButton btnForceList;
/*     */   private JButton btnNewIcon;
/*     */   private JButton btnOpen;
/*     */   private JButton btnOptionsIcon;
/*     */   private JButton btnPostToS7;
/*     */   private JButton btnPrintIcon;
/*     */   private JButton btnPrintPreview;
/*     */   private JButton btnSaveIcon;
/*     */   private javax.swing.JCheckBox chkCommandConsole;
/*     */   private javax.swing.JCheckBox chkOmnimech;
/*     */   private javax.swing.JCheckBox chkYearRestrict;
/*     */   
/*     */   public frmCompat()
/*     */   {
/*  37 */     initComponents();
/*     */   }
/*     */   
/*     */   private JComboBox cmbCockpitType;
/*     */   private JComboBox cmbEngineType;
/*     */   private JComboBox cmbFluffEra;
/*     */   private JComboBox cmbGyroType;
/*     */   private JComboBox cmbInternalType;
/*     */   private JComboBox cmbMechEra;
/*     */   
/*     */   private void initComponents()
/*     */   {
/*  49 */     this.tlbIconBar = new JToolBar();
/*  50 */     this.btnNewIcon = new JButton();
/*  51 */     this.btnOpen = new JButton();
/*  52 */     this.btnSaveIcon = new JButton();
/*  53 */     this.btnPrintPreview = new JButton();
/*  54 */     this.jSeparator1 = new javax.swing.JToolBar.Separator();
/*  55 */     this.btnPrintIcon = new JButton();
/*  56 */     this.jSeparator2 = new javax.swing.JToolBar.Separator();
/*  57 */     this.btnExportClipboardIcon = new JButton();
/*  58 */     this.btnExportHTMLIcon = new JButton();
/*  59 */     this.btnExportTextIcon = new JButton();
/*  60 */     this.btnExportMTFIcon = new JButton();
/*  61 */     this.btnChatInfo = new JButton();
/*  62 */     this.jSeparator3 = new javax.swing.JToolBar.Separator();
/*  63 */     this.btnPostToS7 = new JButton();
/*  64 */     this.jSeparator4 = new javax.swing.JToolBar.Separator();
/*  65 */     this.btnAddToForceList = new JButton();
/*  66 */     this.btnForceList = new JButton();
/*  67 */     this.jSeparator5 = new javax.swing.JToolBar.Separator();
/*  68 */     this.btnOptionsIcon = new JButton();
/*  69 */     this.jSeparator6 = new javax.swing.JToolBar.Separator();
/*  70 */     this.lblSelectVariant = new JLabel();
/*  71 */     this.cmbOmniVariant = new JComboBox();
/*  72 */     this.tbpMainTabPane = new javax.swing.JTabbedPane();
/*  73 */     this.pnlBasicSetup = new JPanel();
/*  74 */     this.pnlBasicSetupLeft = new JPanel();
/*  75 */     this.pnlBasicInformation = new JPanel();
/*  76 */     this.pnlBasicInfo1 = new JPanel();
/*  77 */     this.lblMechName = new JLabel();
/*  78 */     this.txtMechName = new JTextField();
/*  79 */     this.pnlBasicInfo2 = new JPanel();
/*  80 */     this.lblModel = new JLabel();
/*  81 */     this.txtMechModel = new JTextField();
/*  82 */     this.pnlBasicInfo3 = new JPanel();
/*  83 */     this.lblMechSource = new JLabel();
/*  84 */     this.txtSource = new JTextField();
/*  85 */     this.pnlBasicInfo4 = new JPanel();
/*  86 */     this.lblRulesLevel = new JLabel();
/*  87 */     this.cmbRulesLevel = new JComboBox();
/*  88 */     this.pnlBasicInfo5 = new JPanel();
/*  89 */     this.lblMechEra = new JLabel();
/*  90 */     this.cmbMechEra = new JComboBox();
/*  91 */     this.pnlBasicInfo6 = new JPanel();
/*  92 */     this.lblTechBase = new JLabel();
/*  93 */     this.cmbTechBase = new JComboBox();
/*  94 */     this.pnlBasicInfo7 = new JPanel();
/*  95 */     this.lblEraYears = new JLabel();
/*  96 */     this.pnlBasicInfo8 = new JPanel();
/*  97 */     this.lblProdYear = new JLabel();
/*  98 */     this.txtProdYear = new JTextField();
/*  99 */     this.pnlBasicInfo9 = new JPanel();
/* 100 */     this.chkYearRestrict = new javax.swing.JCheckBox();
/* 101 */     this.pnlBasicInfo10 = new JPanel();
/* 102 */     this.jLabel9 = new JLabel();
/* 103 */     this.cmbFluffEra = new JComboBox();
/* 104 */     this.pnlChassis = new JPanel();
/* 105 */     this.pnlChassis1 = new JPanel();
/* 106 */     this.lblTonnage = new JLabel();
/* 107 */     this.cmbTonnage = new JComboBox();
/* 108 */     this.lblMechType = new JLabel();
/* 109 */     this.pnlChassis2 = new JPanel();
/* 110 */     this.lblUnitType = new JLabel();
/* 111 */     this.cmbMechType = new JComboBox();
/* 112 */     this.pnlChassis3 = new JPanel();
/* 113 */     this.lblMotiveType = new JLabel();
/* 114 */     this.cmbMotiveType = new JComboBox();
/* 115 */     this.pnlChassis4 = new JPanel();
/* 116 */     this.chkOmnimech = new javax.swing.JCheckBox();
/* 117 */     this.pnlChassis5 = new JPanel();
/* 118 */     this.lblInternalType = new JLabel();
/* 119 */     this.cmbInternalType = new JComboBox();
/* 120 */     this.pnlChassis6 = new JPanel();
/* 121 */     this.lblEngineType = new JLabel();
/* 122 */     this.cmbEngineType = new JComboBox();
/* 123 */     this.pnlChassis7 = new JPanel();
/* 124 */     this.lblGyroType = new JLabel();
/* 125 */     this.cmbGyroType = new JComboBox();
/* 126 */     this.pnlChassis8 = new JPanel();
/* 127 */     this.lblCockpit = new JLabel();
/* 128 */     this.cmbCockpitType = new JComboBox();
/* 129 */     this.pnlChassis9 = new JPanel();
/* 130 */     this.chkCommandConsole = new javax.swing.JCheckBox();
/* 131 */     this.pnlChassis10 = new JPanel();
/* 132 */     this.lblPhysEnhance = new JLabel();
/* 133 */     this.cmbPhysEnhance = new JComboBox();
/* 134 */     this.pnlBasicSetupCenter = new JPanel();
/* 135 */     this.pnlBasicSetupRight = new JPanel();
/* 136 */     this.pnlArmor = new JPanel();
/* 137 */     this.pnlEquipment = new JPanel();
/* 138 */     this.pnlCriticals = new JPanel();
/* 139 */     this.pnlFluff = new JPanel();
/* 140 */     this.pnlCharts = new JPanel();
/* 141 */     this.pnlBattleforce = new JPanel();
/* 142 */     this.pnlInfoPanel = new JPanel();
/* 143 */     this.txtInfoTonnage = new JTextField();
/* 144 */     this.txtInfoFreeTons = new JTextField();
/* 145 */     this.txtInfoMaxHeat = new JTextField();
/* 146 */     this.txtInfoHeatDiss = new JTextField();
/* 147 */     this.jTextField4 = new JTextField();
/* 148 */     this.txtInfoUnplaced = new JTextField();
/* 149 */     this.txtInfoBattleValue = new JTextField();
/* 150 */     this.txtInfoCost = new JTextField();
/* 151 */     this.mnuMainMenu = new javax.swing.JMenuBar();
/* 152 */     this.mnuFile = new JMenu();
/* 153 */     this.mnuNewMech = new JMenuItem();
/* 154 */     this.mnuLoad = new JMenuItem();
/* 155 */     this.mnuOpen = new JMenuItem();
/* 156 */     this.mnuImport = new JMenu();
/* 157 */     this.mnuImportHMP = new JMenuItem();
/* 158 */     this.mnuBatchHMP = new JMenuItem();
/* 159 */     this.jSeparator7 = new javax.swing.JSeparator();
/* 160 */     this.mnuSave = new JMenuItem();
/* 161 */     this.mnuSaveAs = new JMenuItem();
/* 162 */     this.mnuExport = new JMenu();
/* 163 */     this.mnuExportHTML = new JMenuItem();
/* 164 */     this.mnuExportMTF = new JMenuItem();
/* 165 */     this.mnuExportTXT = new JMenuItem();
/* 166 */     this.mnuExportClipboard = new JMenuItem();
/* 167 */     this.mnuCreateTCGMech = new JMenuItem();
/* 168 */     this.jSeparator8 = new javax.swing.JSeparator();
/* 169 */     this.mnuPrint = new JMenu();
/* 170 */     this.mnuPrintCurrentMech = new JMenuItem();
/* 171 */     this.mnuPrintSavedMech = new JMenuItem();
/* 172 */     this.mnuPrintBatch = new JMenuItem();
/* 173 */     this.mnuPrintPreview = new JMenuItem();
/* 174 */     this.mnuPostS7 = new JMenuItem();
/* 175 */     this.jSeparator9 = new javax.swing.JSeparator();
/* 176 */     this.mnuExit = new JMenuItem();
/* 177 */     this.mnuTools = new JMenu();
/* 178 */     this.mnuSummary = new JMenuItem();
/* 179 */     this.mnuCostBVBreakdown = new JMenuItem();
/* 180 */     this.mnuTextTRO = new JMenuItem();
/* 181 */     this.mnuUnlock = new JMenuItem();
/* 182 */     this.jSeparator10 = new javax.swing.JSeparator();
/* 183 */     this.mnuOptions = new JMenuItem();
/* 184 */     this.mnuViewToolbar = new javax.swing.JCheckBoxMenuItem();
/* 185 */     this.mnuClearUserData = new JMenuItem();
/* 186 */     this.mnuHelp = new JMenu();
/* 187 */     this.mnuCredits = new JMenuItem();
/* 188 */     this.mnuAboutSSW = new JMenuItem();
/*     */     
/* 190 */     setDefaultCloseOperation(3);
/* 191 */     getContentPane().setLayout(new BoxLayout(getContentPane(), 3));
/*     */     
/* 193 */     this.tlbIconBar.setFloatable(false);
/* 194 */     this.tlbIconBar.setRollover(true);
/* 195 */     this.tlbIconBar.setFocusable(false);
/* 196 */     this.tlbIconBar.setPreferredSize(new Dimension(30, 30));
/* 197 */     this.tlbIconBar.setRequestFocusEnabled(false);
/* 198 */     this.tlbIconBar.setVerifyInputWhenFocusTarget(false);
/*     */     
/* 200 */     this.btnNewIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document--plus.png")));
/* 201 */     this.btnNewIcon.setToolTipText("Create New 'Mech");
/* 202 */     this.btnNewIcon.setFocusable(false);
/* 203 */     this.btnNewIcon.setHorizontalTextPosition(0);
/* 204 */     this.btnNewIcon.setMinimumSize(new Dimension(28, 28));
/* 205 */     this.btnNewIcon.setPreferredSize(new Dimension(28, 28));
/* 206 */     this.btnNewIcon.setVerticalTextPosition(3);
/* 207 */     this.tlbIconBar.add(this.btnNewIcon);
/*     */     
/* 209 */     this.btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/folder-open-document.png")));
/* 210 */     this.btnOpen.setToolTipText("Open Dialogue");
/* 211 */     this.btnOpen.setFocusable(false);
/* 212 */     this.btnOpen.setHorizontalTextPosition(0);
/* 213 */     this.btnOpen.setMinimumSize(new Dimension(28, 28));
/* 214 */     this.btnOpen.setPreferredSize(new Dimension(28, 28));
/* 215 */     this.btnOpen.setVerticalTextPosition(3);
/* 216 */     this.tlbIconBar.add(this.btnOpen);
/*     */     
/* 218 */     this.btnSaveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/disk-black.png")));
/* 219 */     this.btnSaveIcon.setToolTipText("Save Current 'Mech");
/* 220 */     this.btnSaveIcon.setFocusable(false);
/* 221 */     this.btnSaveIcon.setHorizontalTextPosition(0);
/* 222 */     this.btnSaveIcon.setMinimumSize(new Dimension(28, 28));
/* 223 */     this.btnSaveIcon.setPreferredSize(new Dimension(28, 28));
/* 224 */     this.btnSaveIcon.setVerticalTextPosition(3);
/* 225 */     this.tlbIconBar.add(this.btnSaveIcon);
/*     */     
/* 227 */     this.btnPrintPreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/projection-screen.png")));
/* 228 */     this.btnPrintPreview.setToolTipText("Print Preview");
/* 229 */     this.btnPrintPreview.setFocusable(false);
/* 230 */     this.btnPrintPreview.setHorizontalTextPosition(0);
/* 231 */     this.btnPrintPreview.setMinimumSize(new Dimension(28, 28));
/* 232 */     this.btnPrintPreview.setPreferredSize(new Dimension(28, 28));
/* 233 */     this.btnPrintPreview.setVerticalTextPosition(3);
/* 234 */     this.tlbIconBar.add(this.btnPrintPreview);
/* 235 */     this.tlbIconBar.add(this.jSeparator1);
/*     */     
/* 237 */     this.btnPrintIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/printer.png")));
/* 238 */     this.btnPrintIcon.setToolTipText("Print This 'Mech");
/* 239 */     this.btnPrintIcon.setFocusable(false);
/* 240 */     this.btnPrintIcon.setHorizontalTextPosition(0);
/* 241 */     this.btnPrintIcon.setMinimumSize(new Dimension(28, 28));
/* 242 */     this.btnPrintIcon.setPreferredSize(new Dimension(28, 28));
/* 243 */     this.btnPrintIcon.setVerticalTextPosition(3);
/* 244 */     this.tlbIconBar.add(this.btnPrintIcon);
/* 245 */     this.tlbIconBar.add(this.jSeparator2);
/*     */     
/* 247 */     this.btnExportClipboardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document-clipboard.png")));
/* 248 */     this.btnExportClipboardIcon.setToolTipText("Export 'Mech to Clipboard in TRO Format");
/* 249 */     this.btnExportClipboardIcon.setFocusable(false);
/* 250 */     this.btnExportClipboardIcon.setHorizontalTextPosition(0);
/* 251 */     this.btnExportClipboardIcon.setMinimumSize(new Dimension(28, 28));
/* 252 */     this.btnExportClipboardIcon.setPreferredSize(new Dimension(28, 28));
/* 253 */     this.btnExportClipboardIcon.setVerticalTextPosition(3);
/* 254 */     this.tlbIconBar.add(this.btnExportClipboardIcon);
/*     */     
/* 256 */     this.btnExportHTMLIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document-image.png")));
/* 257 */     this.btnExportHTMLIcon.setToolTipText("Export 'Mech to HTML");
/* 258 */     this.btnExportHTMLIcon.setFocusable(false);
/* 259 */     this.btnExportHTMLIcon.setHorizontalTextPosition(0);
/* 260 */     this.btnExportHTMLIcon.setMinimumSize(new Dimension(28, 28));
/* 261 */     this.btnExportHTMLIcon.setPreferredSize(new Dimension(28, 28));
/* 262 */     this.btnExportHTMLIcon.setVerticalTextPosition(3);
/* 263 */     this.tlbIconBar.add(this.btnExportHTMLIcon);
/*     */     
/* 265 */     this.btnExportTextIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document-text.png")));
/* 266 */     this.btnExportTextIcon.setToolTipText("Export 'Mech to Text/TRO File");
/* 267 */     this.btnExportTextIcon.setFocusable(false);
/* 268 */     this.btnExportTextIcon.setHorizontalTextPosition(0);
/* 269 */     this.btnExportTextIcon.setMinimumSize(new Dimension(28, 28));
/* 270 */     this.btnExportTextIcon.setPreferredSize(new Dimension(28, 28));
/* 271 */     this.btnExportTextIcon.setVerticalTextPosition(3);
/* 272 */     this.tlbIconBar.add(this.btnExportTextIcon);
/*     */     
/* 274 */     this.btnExportMTFIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document--arrow.png")));
/* 275 */     this.btnExportMTFIcon.setToolTipText("Export 'Mech to MTF (MegaMek) Format");
/* 276 */     this.btnExportMTFIcon.setFocusable(false);
/* 277 */     this.btnExportMTFIcon.setHorizontalTextPosition(0);
/* 278 */     this.btnExportMTFIcon.setMinimumSize(new Dimension(28, 28));
/* 279 */     this.btnExportMTFIcon.setPreferredSize(new Dimension(28, 28));
/* 280 */     this.btnExportMTFIcon.setVerticalTextPosition(3);
/* 281 */     this.tlbIconBar.add(this.btnExportMTFIcon);
/*     */     
/* 283 */     this.btnChatInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/balloon.png")));
/* 284 */     this.btnChatInfo.setToolTipText("Export 'Mech to Clipboard in Chat Format (Brief Info)");
/* 285 */     this.btnChatInfo.setFocusable(false);
/* 286 */     this.btnChatInfo.setHorizontalTextPosition(0);
/* 287 */     this.btnChatInfo.setMinimumSize(new Dimension(28, 28));
/* 288 */     this.btnChatInfo.setPreferredSize(new Dimension(28, 28));
/* 289 */     this.btnChatInfo.setVerticalTextPosition(3);
/* 290 */     this.tlbIconBar.add(this.btnChatInfo);
/* 291 */     this.tlbIconBar.add(this.jSeparator3);
/*     */     
/* 293 */     this.btnPostToS7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/server.png")));
/* 294 */     this.btnPostToS7.setToolTipText("Post 'Mech to Solaris7 in HTML Format (Requires Account)");
/* 295 */     this.btnPostToS7.setFocusable(false);
/* 296 */     this.btnPostToS7.setHorizontalTextPosition(0);
/* 297 */     this.btnPostToS7.setVerticalTextPosition(3);
/* 298 */     this.tlbIconBar.add(this.btnPostToS7);
/* 299 */     this.tlbIconBar.add(this.jSeparator4);
/*     */     
/* 301 */     this.btnAddToForceList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/clipboard--plus.png")));
/* 302 */     this.btnAddToForceList.setToolTipText("Add This 'Mech to the Force List");
/* 303 */     this.btnAddToForceList.setFocusable(false);
/* 304 */     this.btnAddToForceList.setHorizontalTextPosition(0);
/* 305 */     this.btnAddToForceList.setVerticalTextPosition(3);
/* 306 */     this.tlbIconBar.add(this.btnAddToForceList);
/*     */     
/* 308 */     this.btnForceList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/clipboard.png")));
/* 309 */     this.btnForceList.setToolTipText("View Current Force List");
/* 310 */     this.btnForceList.setFocusable(false);
/* 311 */     this.btnForceList.setHorizontalTextPosition(0);
/* 312 */     this.btnForceList.setVerticalTextPosition(3);
/* 313 */     this.tlbIconBar.add(this.btnForceList);
/* 314 */     this.tlbIconBar.add(this.jSeparator5);
/*     */     
/* 316 */     this.btnOptionsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/gear.png")));
/* 317 */     this.btnOptionsIcon.setToolTipText("View/Change User Preferences");
/* 318 */     this.btnOptionsIcon.setFocusable(false);
/* 319 */     this.btnOptionsIcon.setHorizontalTextPosition(0);
/* 320 */     this.btnOptionsIcon.setVerticalTextPosition(3);
/* 321 */     this.tlbIconBar.add(this.btnOptionsIcon);
/* 322 */     this.tlbIconBar.add(this.jSeparator6);
/*     */     
/* 324 */     this.lblSelectVariant.setText("Selected Variant: ");
/* 325 */     this.lblSelectVariant.setEnabled(false);
/* 326 */     this.tlbIconBar.add(this.lblSelectVariant);
/*     */     
/* 328 */     this.cmbOmniVariant.setToolTipText("Change OmniMech Variant");
/* 329 */     this.cmbOmniVariant.setEnabled(false);
/* 330 */     this.cmbOmniVariant.setMinimumSize(new Dimension(150, 20));
/* 331 */     this.cmbOmniVariant.setPreferredSize(new Dimension(150, 20));
/* 332 */     this.tlbIconBar.add(this.cmbOmniVariant);
/*     */     
/* 334 */     getContentPane().add(this.tlbIconBar);
/*     */     
/* 336 */     this.pnlBasicSetup.setLayout(new BoxLayout(this.pnlBasicSetup, 2));
/*     */     
/* 338 */     this.pnlBasicSetupLeft.setLayout(new BoxLayout(this.pnlBasicSetupLeft, 3));
/*     */     
/* 340 */     this.pnlBasicInformation.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Basic Information"));
/* 341 */     this.pnlBasicInformation.setLayout(new BoxLayout(this.pnlBasicInformation, 3));
/*     */     
/* 343 */     this.pnlBasicInfo1.setLayout(new BoxLayout(this.pnlBasicInfo1, 2));
/* 344 */     this.pnlBasicInfo1.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 346 */     this.lblMechName.setText("Mech Name: ");
/* 347 */     this.pnlBasicInfo1.add(this.lblMechName);
/*     */     
/* 349 */     this.txtMechName.setMinimumSize(new Dimension(150, 20));
/* 350 */     this.txtMechName.setPreferredSize(new Dimension(150, 20));
/* 351 */     this.pnlBasicInfo1.add(this.txtMechName);
/*     */     
/* 353 */     this.pnlBasicInformation.add(this.pnlBasicInfo1);
/*     */     
/* 355 */     this.pnlBasicInfo2.setLayout(new BoxLayout(this.pnlBasicInfo2, 2));
/* 356 */     this.pnlBasicInfo2.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 358 */     this.lblModel.setText("Model: ");
/* 359 */     this.pnlBasicInfo2.add(this.lblModel);
/*     */     
/* 361 */     this.txtMechModel.setMinimumSize(new Dimension(150, 20));
/* 362 */     this.txtMechModel.setPreferredSize(new Dimension(150, 20));
/* 363 */     this.pnlBasicInfo2.add(this.txtMechModel);
/*     */     
/* 365 */     this.pnlBasicInformation.add(this.pnlBasicInfo2);
/*     */     
/* 367 */     this.pnlBasicInfo3.setLayout(new BoxLayout(this.pnlBasicInfo3, 2));
/* 368 */     this.pnlBasicInfo3.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 370 */     this.lblMechSource.setText("Source: ");
/* 371 */     this.pnlBasicInfo3.add(this.lblMechSource);
/*     */     
/* 373 */     this.txtSource.setMinimumSize(new Dimension(150, 20));
/* 374 */     this.txtSource.setPreferredSize(new Dimension(150, 20));
/* 375 */     this.pnlBasicInfo3.add(this.txtSource);
/*     */     
/* 377 */     this.pnlBasicInformation.add(this.pnlBasicInfo3);
/*     */     
/* 379 */     this.pnlBasicInfo4.setLayout(new BoxLayout(this.pnlBasicInfo4, 2));
/* 380 */     this.pnlBasicInfo4.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 382 */     this.lblRulesLevel.setText("Rules Level: ");
/* 383 */     this.pnlBasicInfo4.add(this.lblRulesLevel);
/*     */     
/* 385 */     this.cmbRulesLevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 386 */     this.cmbRulesLevel.setMinimumSize(new Dimension(150, 20));
/* 387 */     this.cmbRulesLevel.setPreferredSize(new Dimension(150, 20));
/* 388 */     this.pnlBasicInfo4.add(this.cmbRulesLevel);
/*     */     
/* 390 */     this.pnlBasicInformation.add(this.pnlBasicInfo4);
/*     */     
/* 392 */     this.pnlBasicInfo5.setLayout(new BoxLayout(this.pnlBasicInfo5, 2));
/* 393 */     this.pnlBasicInfo5.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 395 */     this.lblMechEra.setText("Tech Era: ");
/* 396 */     this.pnlBasicInfo5.add(this.lblMechEra);
/*     */     
/* 398 */     this.cmbMechEra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 399 */     this.cmbMechEra.setMinimumSize(new Dimension(150, 20));
/* 400 */     this.cmbMechEra.setPreferredSize(new Dimension(150, 20));
/* 401 */     this.pnlBasicInfo5.add(this.cmbMechEra);
/*     */     
/* 403 */     this.pnlBasicInformation.add(this.pnlBasicInfo5);
/*     */     
/* 405 */     this.pnlBasicInfo6.setLayout(new BoxLayout(this.pnlBasicInfo6, 2));
/* 406 */     this.pnlBasicInfo6.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 408 */     this.lblTechBase.setText("Tech Base: ");
/* 409 */     this.pnlBasicInfo6.add(this.lblTechBase);
/*     */     
/* 411 */     this.cmbTechBase.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 412 */     this.cmbTechBase.setMinimumSize(new Dimension(150, 20));
/* 413 */     this.cmbTechBase.setPreferredSize(new Dimension(150, 20));
/* 414 */     this.pnlBasicInfo6.add(this.cmbTechBase);
/*     */     
/* 416 */     this.pnlBasicInformation.add(this.pnlBasicInfo6);
/*     */     
/* 418 */     this.pnlBasicInfo7.setLayout(new BoxLayout(this.pnlBasicInfo7, 2));
/* 419 */     this.pnlBasicInfo7.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 421 */     this.lblEraYears.setText("2443~2800");
/* 422 */     this.pnlBasicInfo7.add(this.lblEraYears);
/*     */     
/* 424 */     this.pnlBasicInformation.add(this.pnlBasicInfo7);
/*     */     
/* 426 */     this.pnlBasicInfo8.setLayout(new BoxLayout(this.pnlBasicInfo8, 2));
/* 427 */     this.pnlBasicInfo8.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 429 */     this.lblProdYear.setText("Production Year: ");
/* 430 */     this.pnlBasicInfo8.add(this.lblProdYear);
/*     */     
/* 432 */     this.txtProdYear.setMinimumSize(new Dimension(150, 20));
/* 433 */     this.txtProdYear.setPreferredSize(new Dimension(150, 20));
/* 434 */     this.pnlBasicInfo8.add(this.txtProdYear);
/*     */     
/* 436 */     this.pnlBasicInformation.add(this.pnlBasicInfo8);
/*     */     
/* 438 */     this.pnlBasicInfo9.setLayout(new BoxLayout(this.pnlBasicInfo9, 2));
/* 439 */     this.pnlBasicInfo9.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 441 */     this.chkYearRestrict.setText("Restrict Availability by Year");
/* 442 */     this.pnlBasicInfo9.add(this.chkYearRestrict);
/*     */     
/* 444 */     this.pnlBasicInformation.add(this.pnlBasicInfo9);
/*     */     
/* 446 */     this.pnlBasicInfo10.setLayout(new BoxLayout(this.pnlBasicInfo10, 2));
/* 447 */     this.pnlBasicInfo10.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 449 */     this.jLabel9.setText("Fluff Era: ");
/* 450 */     this.pnlBasicInfo10.add(this.jLabel9);
/*     */     
/* 452 */     this.cmbFluffEra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 453 */     this.cmbFluffEra.setMinimumSize(new Dimension(150, 20));
/* 454 */     this.cmbFluffEra.setPreferredSize(new Dimension(150, 20));
/* 455 */     this.pnlBasicInfo10.add(this.cmbFluffEra);
/*     */     
/* 457 */     this.pnlBasicInformation.add(this.pnlBasicInfo10);
/*     */     
/* 459 */     this.pnlBasicSetupLeft.add(this.pnlBasicInformation);
/*     */     
/* 461 */     this.pnlChassis.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chassis"));
/* 462 */     this.pnlChassis.setLayout(new BoxLayout(this.pnlChassis, 3));
/*     */     
/* 464 */     this.pnlChassis1.setLayout(new BoxLayout(this.pnlChassis1, 2));
/* 465 */     this.pnlChassis1.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 467 */     this.lblTonnage.setText("Tonnage: ");
/* 468 */     this.pnlChassis1.add(this.lblTonnage);
/*     */     
/* 470 */     this.cmbTonnage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 471 */     this.cmbTonnage.setMinimumSize(new Dimension(60, 20));
/* 472 */     this.cmbTonnage.setPreferredSize(new Dimension(60, 20));
/* 473 */     this.pnlChassis1.add(this.cmbTonnage);
/*     */     
/* 475 */     this.lblMechType.setText("Light Mech");
/* 476 */     this.pnlChassis1.add(this.lblMechType);
/*     */     
/* 478 */     this.pnlChassis.add(this.pnlChassis1);
/*     */     
/* 480 */     this.pnlChassis2.setLayout(new BoxLayout(this.pnlChassis2, 2));
/* 481 */     this.pnlChassis2.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 483 */     this.lblUnitType.setText("Mech Type: ");
/* 484 */     this.pnlChassis2.add(this.lblUnitType);
/*     */     
/* 486 */     this.cmbMechType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 487 */     this.cmbMechType.setMinimumSize(new Dimension(150, 20));
/* 488 */     this.cmbMechType.setPreferredSize(new Dimension(150, 20));
/* 489 */     this.pnlChassis2.add(this.cmbMechType);
/*     */     
/* 491 */     this.pnlChassis.add(this.pnlChassis2);
/*     */     
/* 493 */     this.pnlChassis3.setLayout(new BoxLayout(this.pnlChassis3, 2));
/* 494 */     this.pnlChassis3.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 496 */     this.lblMotiveType.setText("Motive Type: ");
/* 497 */     this.pnlChassis3.add(this.lblMotiveType);
/*     */     
/* 499 */     this.cmbMotiveType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 500 */     this.cmbMotiveType.setMinimumSize(new Dimension(150, 20));
/* 501 */     this.cmbMotiveType.setPreferredSize(new Dimension(150, 20));
/* 502 */     this.pnlChassis3.add(this.cmbMotiveType);
/*     */     
/* 504 */     this.pnlChassis.add(this.pnlChassis3);
/*     */     
/* 506 */     this.pnlChassis4.setLayout(new BoxLayout(this.pnlChassis4, 2));
/* 507 */     this.pnlChassis4.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 509 */     this.chkOmnimech.setText("OmniMech");
/* 510 */     this.pnlChassis4.add(this.chkOmnimech);
/*     */     
/* 512 */     this.pnlChassis.add(this.pnlChassis4);
/*     */     
/* 514 */     this.pnlChassis5.setLayout(new BoxLayout(this.pnlChassis5, 2));
/* 515 */     this.pnlChassis5.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 517 */     this.lblInternalType.setText("Internal Structure: ");
/* 518 */     this.pnlChassis5.add(this.lblInternalType);
/*     */     
/* 520 */     this.cmbInternalType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 521 */     this.cmbInternalType.setMinimumSize(new Dimension(150, 20));
/* 522 */     this.cmbInternalType.setPreferredSize(new Dimension(150, 20));
/* 523 */     this.pnlChassis5.add(this.cmbInternalType);
/*     */     
/* 525 */     this.pnlChassis.add(this.pnlChassis5);
/*     */     
/* 527 */     this.pnlChassis6.setLayout(new BoxLayout(this.pnlChassis6, 2));
/* 528 */     this.pnlChassis6.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 530 */     this.lblEngineType.setText("Engine Type: ");
/* 531 */     this.pnlChassis6.add(this.lblEngineType);
/*     */     
/* 533 */     this.cmbEngineType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 534 */     this.cmbEngineType.setMinimumSize(new Dimension(150, 20));
/* 535 */     this.cmbEngineType.setPreferredSize(new Dimension(150, 20));
/* 536 */     this.pnlChassis6.add(this.cmbEngineType);
/*     */     
/* 538 */     this.pnlChassis.add(this.pnlChassis6);
/*     */     
/* 540 */     this.pnlChassis7.setLayout(new BoxLayout(this.pnlChassis7, 2));
/* 541 */     this.pnlChassis7.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 543 */     this.lblGyroType.setText("Gyro Type: ");
/* 544 */     this.pnlChassis7.add(this.lblGyroType);
/*     */     
/* 546 */     this.cmbGyroType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 547 */     this.cmbGyroType.setMinimumSize(new Dimension(150, 20));
/* 548 */     this.cmbGyroType.setPreferredSize(new Dimension(150, 20));
/* 549 */     this.pnlChassis7.add(this.cmbGyroType);
/*     */     
/* 551 */     this.pnlChassis.add(this.pnlChassis7);
/*     */     
/* 553 */     this.pnlChassis8.setLayout(new BoxLayout(this.pnlChassis8, 2));
/* 554 */     this.pnlChassis8.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 556 */     this.lblCockpit.setText("Cockpit Type: ");
/* 557 */     this.pnlChassis8.add(this.lblCockpit);
/*     */     
/* 559 */     this.cmbCockpitType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 560 */     this.cmbCockpitType.setMinimumSize(new Dimension(150, 20));
/* 561 */     this.cmbCockpitType.setPreferredSize(new Dimension(150, 20));
/* 562 */     this.pnlChassis8.add(this.cmbCockpitType);
/*     */     
/* 564 */     this.pnlChassis.add(this.pnlChassis8);
/*     */     
/* 566 */     this.pnlChassis9.setLayout(new BoxLayout(this.pnlChassis9, 2));
/* 567 */     this.pnlChassis9.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 569 */     this.chkCommandConsole.setText("Use Command Console");
/* 570 */     this.pnlChassis9.add(this.chkCommandConsole);
/*     */     
/* 572 */     this.pnlChassis.add(this.pnlChassis9);
/*     */     
/* 574 */     this.pnlChassis10.setLayout(new BoxLayout(this.pnlChassis10, 2));
/* 575 */     this.pnlChassis10.add(javax.swing.Box.createHorizontalGlue());
/*     */     
/* 577 */     this.lblPhysEnhance.setText("Enhancements: ");
/* 578 */     this.pnlChassis10.add(this.lblPhysEnhance);
/*     */     
/* 580 */     this.cmbPhysEnhance.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 581 */     this.cmbPhysEnhance.setMinimumSize(new Dimension(150, 20));
/* 582 */     this.cmbPhysEnhance.setPreferredSize(new Dimension(150, 20));
/* 583 */     this.pnlChassis10.add(this.cmbPhysEnhance);
/*     */     
/* 585 */     this.pnlChassis.add(this.pnlChassis10);
/*     */     
/* 587 */     this.pnlBasicSetupLeft.add(this.pnlChassis);
/*     */     
/* 589 */     this.pnlBasicSetup.add(this.pnlBasicSetupLeft);
/*     */     
/* 591 */     this.pnlBasicSetupCenter.setLayout(new BoxLayout(this.pnlBasicSetupCenter, 3));
/* 592 */     this.pnlBasicSetup.add(this.pnlBasicSetupCenter);
/*     */     
/* 594 */     this.pnlBasicSetupRight.setLayout(new BoxLayout(this.pnlBasicSetupRight, 3));
/* 595 */     this.pnlBasicSetup.add(this.pnlBasicSetupRight);
/*     */     
/* 597 */     this.tbpMainTabPane.addTab("Basic Setup", this.pnlBasicSetup);
/*     */     
/* 599 */     GroupLayout pnlArmorLayout = new GroupLayout(this.pnlArmor);
/* 600 */     this.pnlArmor.setLayout(pnlArmorLayout);
/* 601 */     pnlArmorLayout.setHorizontalGroup(pnlArmorLayout
/* 602 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 603 */       .addGap(0, 800, 32767));
/*     */     
/* 605 */     pnlArmorLayout.setVerticalGroup(pnlArmorLayout
/* 606 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 607 */       .addGap(0, 489, 32767));
/*     */     
/*     */ 
/* 610 */     this.tbpMainTabPane.addTab("Armor", this.pnlArmor);
/*     */     
/* 612 */     GroupLayout pnlEquipmentLayout = new GroupLayout(this.pnlEquipment);
/* 613 */     this.pnlEquipment.setLayout(pnlEquipmentLayout);
/* 614 */     pnlEquipmentLayout.setHorizontalGroup(pnlEquipmentLayout
/* 615 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 616 */       .addGap(0, 800, 32767));
/*     */     
/* 618 */     pnlEquipmentLayout.setVerticalGroup(pnlEquipmentLayout
/* 619 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 620 */       .addGap(0, 489, 32767));
/*     */     
/*     */ 
/* 623 */     this.tbpMainTabPane.addTab("Equipment", this.pnlEquipment);
/*     */     
/* 625 */     GroupLayout pnlCriticalsLayout = new GroupLayout(this.pnlCriticals);
/* 626 */     this.pnlCriticals.setLayout(pnlCriticalsLayout);
/* 627 */     pnlCriticalsLayout.setHorizontalGroup(pnlCriticalsLayout
/* 628 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 629 */       .addGap(0, 800, 32767));
/*     */     
/* 631 */     pnlCriticalsLayout.setVerticalGroup(pnlCriticalsLayout
/* 632 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 633 */       .addGap(0, 489, 32767));
/*     */     
/*     */ 
/* 636 */     this.tbpMainTabPane.addTab("Criticals", this.pnlCriticals);
/*     */     
/* 638 */     GroupLayout pnlFluffLayout = new GroupLayout(this.pnlFluff);
/* 639 */     this.pnlFluff.setLayout(pnlFluffLayout);
/* 640 */     pnlFluffLayout.setHorizontalGroup(pnlFluffLayout
/* 641 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 642 */       .addGap(0, 800, 32767));
/*     */     
/* 644 */     pnlFluffLayout.setVerticalGroup(pnlFluffLayout
/* 645 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 646 */       .addGap(0, 489, 32767));
/*     */     
/*     */ 
/* 649 */     this.tbpMainTabPane.addTab("Fluff", this.pnlFluff);
/*     */     
/* 651 */     GroupLayout pnlChartsLayout = new GroupLayout(this.pnlCharts);
/* 652 */     this.pnlCharts.setLayout(pnlChartsLayout);
/* 653 */     pnlChartsLayout.setHorizontalGroup(pnlChartsLayout
/* 654 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 655 */       .addGap(0, 800, 32767));
/*     */     
/* 657 */     pnlChartsLayout.setVerticalGroup(pnlChartsLayout
/* 658 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 659 */       .addGap(0, 489, 32767));
/*     */     
/*     */ 
/* 662 */     this.tbpMainTabPane.addTab("Charts", this.pnlCharts);
/*     */     
/* 664 */     GroupLayout pnlBattleforceLayout = new GroupLayout(this.pnlBattleforce);
/* 665 */     this.pnlBattleforce.setLayout(pnlBattleforceLayout);
/* 666 */     pnlBattleforceLayout.setHorizontalGroup(pnlBattleforceLayout
/* 667 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 668 */       .addGap(0, 800, 32767));
/*     */     
/* 670 */     pnlBattleforceLayout.setVerticalGroup(pnlBattleforceLayout
/* 671 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 672 */       .addGap(0, 489, 32767));
/*     */     
/*     */ 
/* 675 */     this.tbpMainTabPane.addTab("BattleForce", this.pnlBattleforce);
/*     */     
/* 677 */     getContentPane().add(this.tbpMainTabPane);
/*     */     
/* 679 */     this.pnlInfoPanel.setLayout(new BoxLayout(this.pnlInfoPanel, 2));
/*     */     
/* 681 */     this.txtInfoTonnage.setEditable(false);
/* 682 */     this.txtInfoTonnage.setHorizontalAlignment(0);
/* 683 */     this.txtInfoTonnage.setText("Tonnage");
/* 684 */     this.txtInfoTonnage.setToolTipText("'Mech Tonnage");
/* 685 */     this.txtInfoTonnage.setMinimumSize(new Dimension(72, 20));
/* 686 */     this.txtInfoTonnage.setPreferredSize(new Dimension(72, 20));
/* 687 */     this.pnlInfoPanel.add(this.txtInfoTonnage);
/*     */     
/* 689 */     this.txtInfoFreeTons.setEditable(false);
/* 690 */     this.txtInfoFreeTons.setHorizontalAlignment(0);
/* 691 */     this.txtInfoFreeTons.setText("free tonnage");
/* 692 */     this.txtInfoFreeTons.setToolTipText("Free Tonnage in 'Mech");
/* 693 */     this.txtInfoFreeTons.setMinimumSize(new Dimension(96, 20));
/* 694 */     this.txtInfoFreeTons.setPreferredSize(new Dimension(96, 20));
/* 695 */     this.pnlInfoPanel.add(this.txtInfoFreeTons);
/*     */     
/* 697 */     this.txtInfoMaxHeat.setEditable(false);
/* 698 */     this.txtInfoMaxHeat.setHorizontalAlignment(0);
/* 699 */     this.txtInfoMaxHeat.setText("max heat");
/* 700 */     this.txtInfoMaxHeat.setToolTipText("Maximum Generated Heat (Change Amount Displayed in Preferences)");
/* 701 */     this.txtInfoMaxHeat.setMinimumSize(new Dimension(77, 20));
/* 702 */     this.txtInfoMaxHeat.setPreferredSize(new Dimension(77, 20));
/* 703 */     this.pnlInfoPanel.add(this.txtInfoMaxHeat);
/*     */     
/* 705 */     this.txtInfoHeatDiss.setEditable(false);
/* 706 */     this.txtInfoHeatDiss.setHorizontalAlignment(0);
/* 707 */     this.txtInfoHeatDiss.setText("heat dissipation");
/* 708 */     this.txtInfoHeatDiss.setToolTipText("Maximum Heat Dissipated Per Game Turn");
/* 709 */     this.txtInfoHeatDiss.setMinimumSize(new Dimension(118, 20));
/* 710 */     this.txtInfoHeatDiss.setPreferredSize(new Dimension(118, 20));
/* 711 */     this.pnlInfoPanel.add(this.txtInfoHeatDiss);
/*     */     
/* 713 */     this.jTextField4.setEditable(false);
/* 714 */     this.jTextField4.setHorizontalAlignment(0);
/* 715 */     this.jTextField4.setText("free crits");
/* 716 */     this.jTextField4.setToolTipText("Free Critical Slots Available");
/* 717 */     this.jTextField4.setMinimumSize(new Dimension(77, 20));
/* 718 */     this.jTextField4.setPreferredSize(new Dimension(77, 20));
/* 719 */     this.pnlInfoPanel.add(this.jTextField4);
/*     */     
/* 721 */     this.txtInfoUnplaced.setEditable(false);
/* 722 */     this.txtInfoUnplaced.setHorizontalAlignment(0);
/* 723 */     this.txtInfoUnplaced.setText("unplaced crits");
/* 724 */     this.txtInfoUnplaced.setToolTipText("Unplaced Critical Slot Items");
/* 725 */     this.txtInfoUnplaced.setMinimumSize(new Dimension(110, 20));
/* 726 */     this.txtInfoUnplaced.setPreferredSize(new Dimension(110, 20));
/* 727 */     this.pnlInfoPanel.add(this.txtInfoUnplaced);
/*     */     
/* 729 */     this.txtInfoBattleValue.setEditable(false);
/* 730 */     this.txtInfoBattleValue.setHorizontalAlignment(0);
/* 731 */     this.txtInfoBattleValue.setText("bv");
/* 732 */     this.txtInfoBattleValue.setToolTipText("Battle Value of 'Mech");
/* 733 */     this.txtInfoBattleValue.setMinimumSize(new Dimension(62, 20));
/* 734 */     this.txtInfoBattleValue.setPreferredSize(new Dimension(62, 20));
/* 735 */     this.pnlInfoPanel.add(this.txtInfoBattleValue);
/*     */     
/* 737 */     this.txtInfoCost.setEditable(false);
/* 738 */     this.txtInfoCost.setHorizontalAlignment(0);
/* 739 */     this.txtInfoCost.setText("cost");
/* 740 */     this.txtInfoCost.setToolTipText("Cost (in C-Bills) of 'Mech");
/* 741 */     this.txtInfoCost.setMinimumSize(new Dimension(125, 20));
/* 742 */     this.txtInfoCost.setPreferredSize(new Dimension(125, 20));
/* 743 */     this.pnlInfoPanel.add(this.txtInfoCost);
/*     */     
/* 745 */     getContentPane().add(this.pnlInfoPanel);
/*     */     
/* 747 */     this.mnuFile.setText("File");
/*     */     
/* 749 */     this.mnuNewMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(78, 8));
/* 750 */     this.mnuNewMech.setText("New Mech");
/* 751 */     this.mnuNewMech.setToolTipText("Create New 'Mech");
/* 752 */     this.mnuFile.add(this.mnuNewMech);
/*     */     
/* 754 */     this.mnuLoad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(76, 8));
/* 755 */     this.mnuLoad.setText("Load Mech");
/* 756 */     this.mnuLoad.setToolTipText("Load 'Mech from File");
/* 757 */     this.mnuFile.add(this.mnuLoad);
/*     */     
/* 759 */     this.mnuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, 8));
/* 760 */     this.mnuOpen.setText("Open");
/* 761 */     this.mnuOpen.setToolTipText("Show Open Dialogue");
/* 762 */     this.mnuFile.add(this.mnuOpen);
/*     */     
/* 764 */     this.mnuImport.setText("Import Mech...");
/*     */     
/* 766 */     this.mnuImportHMP.setText("from Heavy Metal Pro (HMP)");
/* 767 */     this.mnuImportHMP.setToolTipText("");
/* 768 */     this.mnuImport.add(this.mnuImportHMP);
/*     */     
/* 770 */     this.mnuBatchHMP.setText("Batch Import HMP Files");
/* 771 */     this.mnuImport.add(this.mnuBatchHMP);
/*     */     
/* 773 */     this.mnuFile.add(this.mnuImport);
/* 774 */     this.mnuFile.add(this.jSeparator7);
/*     */     
/* 776 */     this.mnuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(83, 8));
/* 777 */     this.mnuSave.setText("Save Mech");
/* 778 */     this.mnuFile.add(this.mnuSave);
/*     */     
/* 780 */     this.mnuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(83, 10));
/* 781 */     this.mnuSaveAs.setText("Save Mech As");
/* 782 */     this.mnuFile.add(this.mnuSaveAs);
/*     */     
/* 784 */     this.mnuExport.setText("Export Mech...");
/*     */     
/* 786 */     this.mnuExportHTML.setAccelerator(javax.swing.KeyStroke.getKeyStroke(72, 2));
/* 787 */     this.mnuExportHTML.setText("to HTML (Web)");
/* 788 */     this.mnuExport.add(this.mnuExportHTML);
/*     */     
/* 790 */     this.mnuExportMTF.setAccelerator(javax.swing.KeyStroke.getKeyStroke(77, 2));
/* 791 */     this.mnuExportMTF.setText("to MTF (MegaMek)");
/* 792 */     this.mnuExport.add(this.mnuExportMTF);
/*     */     
/* 794 */     this.mnuExportTXT.setAccelerator(javax.swing.KeyStroke.getKeyStroke(84, 2));
/* 795 */     this.mnuExportTXT.setText("to TXT (Text)");
/* 796 */     this.mnuExport.add(this.mnuExportTXT);
/*     */     
/* 798 */     this.mnuExportClipboard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(67, 10));
/* 799 */     this.mnuExportClipboard.setText("to Clipboard (Text)");
/* 800 */     this.mnuExport.add(this.mnuExportClipboard);
/*     */     
/* 802 */     this.mnuCreateTCGMech.setText("to TCG Format (Card, Incomplete)");
/* 803 */     this.mnuExport.add(this.mnuCreateTCGMech);
/*     */     
/* 805 */     this.mnuFile.add(this.mnuExport);
/* 806 */     this.mnuFile.add(this.jSeparator8);
/*     */     
/* 808 */     this.mnuPrint.setText("Print");
/*     */     
/* 810 */     this.mnuPrintCurrentMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 2));
/* 811 */     this.mnuPrintCurrentMech.setText("Current Mech");
/* 812 */     this.mnuPrint.add(this.mnuPrintCurrentMech);
/*     */     
/* 814 */     this.mnuPrintSavedMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 3));
/* 815 */     this.mnuPrintSavedMech.setText("Saved Mech");
/* 816 */     this.mnuPrint.add(this.mnuPrintSavedMech);
/*     */     
/* 818 */     this.mnuPrintBatch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(66, 3));
/* 819 */     this.mnuPrintBatch.setText("Batch Print Mechs");
/* 820 */     this.mnuPrint.add(this.mnuPrintBatch);
/*     */     
/* 822 */     this.mnuFile.add(this.mnuPrint);
/*     */     
/* 824 */     this.mnuPrintPreview.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 10));
/* 825 */     this.mnuPrintPreview.setText("Print Preview");
/* 826 */     this.mnuFile.add(this.mnuPrintPreview);
/*     */     
/* 828 */     this.mnuPostS7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 8));
/* 829 */     this.mnuPostS7.setText("Post Mech to Solaris7.com");
/* 830 */     this.mnuFile.add(this.mnuPostS7);
/* 831 */     this.mnuFile.add(this.jSeparator9);
/*     */     
/* 833 */     this.mnuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(88, 8));
/* 834 */     this.mnuExit.setText("Exit");
/* 835 */     this.mnuFile.add(this.mnuExit);
/*     */     
/* 837 */     this.mnuMainMenu.add(this.mnuFile);
/*     */     
/* 839 */     this.mnuTools.setText("Tools");
/*     */     
/* 841 */     this.mnuSummary.setAccelerator(javax.swing.KeyStroke.getKeyStroke(85, 8));
/* 842 */     this.mnuSummary.setText("Show Summary");
/* 843 */     this.mnuTools.add(this.mnuSummary);
/*     */     
/* 845 */     this.mnuCostBVBreakdown.setText("Cost/BV Breakdown");
/* 846 */     this.mnuTools.add(this.mnuCostBVBreakdown);
/*     */     
/* 848 */     this.mnuTextTRO.setText("Show Text/TRO Format");
/* 849 */     this.mnuTools.add(this.mnuTextTRO);
/*     */     
/* 851 */     this.mnuUnlock.setText("Unlock Chassis");
/* 852 */     this.mnuUnlock.setEnabled(false);
/* 853 */     this.mnuTools.add(this.mnuUnlock);
/* 854 */     this.mnuTools.add(this.jSeparator10);
/*     */     
/* 856 */     this.mnuOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(82, 8));
/* 857 */     this.mnuOptions.setText("Preferences");
/* 858 */     this.mnuTools.add(this.mnuOptions);
/*     */     
/* 860 */     this.mnuViewToolbar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(84, 8));
/* 861 */     this.mnuViewToolbar.setSelected(true);
/* 862 */     this.mnuViewToolbar.setText("View Toolbar");
/* 863 */     this.mnuTools.add(this.mnuViewToolbar);
/*     */     
/* 865 */     this.mnuClearUserData.setText("Clear User Data");
/* 866 */     this.mnuTools.add(this.mnuClearUserData);
/*     */     
/* 868 */     this.mnuMainMenu.add(this.mnuTools);
/*     */     
/* 870 */     this.mnuHelp.setText("Help");
/*     */     
/* 872 */     this.mnuCredits.setText("Credits");
/* 873 */     this.mnuHelp.add(this.mnuCredits);
/*     */     
/* 875 */     this.mnuAboutSSW.setText("About Solaris Skunk Werks");
/* 876 */     this.mnuHelp.add(this.mnuAboutSSW);
/*     */     
/* 878 */     this.mnuMainMenu.add(this.mnuHelp);
/*     */     
/* 880 */     setJMenuBar(this.mnuMainMenu);
/*     */     
/* 882 */     pack();
/*     */   }
/*     */   
/*     */   private JComboBox cmbMechType;
/*     */   private JComboBox cmbMotiveType;
/*     */   private JComboBox cmbOmniVariant;
/*     */   private JComboBox cmbPhysEnhance;
/*     */   private JComboBox cmbRulesLevel;
/*     */   private JComboBox cmbTechBase;
/*     */   private JComboBox cmbTonnage;
/*     */   private JLabel jLabel9;
/*     */   private javax.swing.JToolBar.Separator jSeparator1;
/*     */   private javax.swing.JSeparator jSeparator10;
/*     */   private javax.swing.JToolBar.Separator jSeparator2;
/*     */   private javax.swing.JToolBar.Separator jSeparator3;
/*     */   private javax.swing.JToolBar.Separator jSeparator4;
/*     */   private javax.swing.JToolBar.Separator jSeparator5;
/*     */   private javax.swing.JToolBar.Separator jSeparator6;
/*     */   private javax.swing.JSeparator jSeparator7;
/*     */   private javax.swing.JSeparator jSeparator8;
/*     */   private javax.swing.JSeparator jSeparator9;
/*     */   private JTextField jTextField4;
/*     */   private JLabel lblCockpit;
/*     */   private JLabel lblEngineType;
/*     */   private JLabel lblEraYears;
/*     */   private JLabel lblGyroType;
/*     */   private JLabel lblInternalType;
/*     */   private JLabel lblMechEra;
/*     */   private JLabel lblMechName;
/*     */   private JLabel lblMechSource;
/*     */   private JLabel lblMechType;
/*     */   private JLabel lblModel;
/*     */   private JLabel lblMotiveType;
/*     */   private JLabel lblPhysEnhance;
/*     */   private JLabel lblProdYear;
/*     */   private JLabel lblRulesLevel;
/*     */   private JLabel lblSelectVariant;
/*     */   private JLabel lblTechBase;
/*     */   private JLabel lblTonnage;
/*     */   private JLabel lblUnitType;
/*     */   private JMenuItem mnuAboutSSW;
/*     */   private JMenuItem mnuBatchHMP;
/*     */   private JMenuItem mnuClearUserData;
/*     */   private JMenuItem mnuCostBVBreakdown;
/*     */   private JMenuItem mnuCreateTCGMech;
/*     */   private JMenuItem mnuCredits;
/*     */   private JMenuItem mnuExit;
/*     */   private JMenu mnuExport;
/*     */   private JMenuItem mnuExportClipboard;
/*     */   private JMenuItem mnuExportHTML;
/*     */   private JMenuItem mnuExportMTF;
/*     */   private JMenuItem mnuExportTXT;
/*     */   private JMenu mnuFile;
/*     */   private JMenu mnuHelp;
/*     */   private JMenu mnuImport;
/*     */   private JMenuItem mnuImportHMP;
/*     */   private JMenuItem mnuLoad;
/*     */   private javax.swing.JMenuBar mnuMainMenu;
/*     */   private JMenuItem mnuNewMech;
/*     */   private JMenuItem mnuOpen;
/*     */   private JMenuItem mnuOptions;
/*     */   private JMenuItem mnuPostS7;
/*     */   private JMenu mnuPrint;
/*     */   private JMenuItem mnuPrintBatch;
/*     */   private JMenuItem mnuPrintCurrentMech;
/*     */   private JMenuItem mnuPrintPreview;
/*     */   private JMenuItem mnuPrintSavedMech;
/*     */   private JMenuItem mnuSave;
/*     */   private JMenuItem mnuSaveAs;
/*     */   private JMenuItem mnuSummary;
/*     */   private JMenuItem mnuTextTRO;
/*     */   private JMenu mnuTools;
/*     */   private JMenuItem mnuUnlock;
/*     */   private javax.swing.JCheckBoxMenuItem mnuViewToolbar;
/*     */   private JPanel pnlArmor;
/*     */   private JPanel pnlBasicInfo1;
/*     */   private JPanel pnlBasicInfo10;
/*     */   private JPanel pnlBasicInfo2;
/*     */   private JPanel pnlBasicInfo3;
/*     */   private JPanel pnlBasicInfo4;
/*     */   private JPanel pnlBasicInfo5;
/*     */   private JPanel pnlBasicInfo6;
/*     */   private JPanel pnlBasicInfo7;
/*     */   private JPanel pnlBasicInfo8;
/*     */   private JPanel pnlBasicInfo9;
/*     */   private JPanel pnlBasicInformation;
/*     */   private JPanel pnlBasicSetup;
/*     */   private JPanel pnlBasicSetupCenter;
/*     */   private JPanel pnlBasicSetupLeft;
/*     */   private JPanel pnlBasicSetupRight;
/*     */   private JPanel pnlBattleforce;
/*     */   private JPanel pnlCharts;
/*     */   private JPanel pnlChassis;
/*     */   private JPanel pnlChassis1;
/*     */   private JPanel pnlChassis10;
/*     */   private JPanel pnlChassis2;
/*     */   private JPanel pnlChassis3;
/*     */   private JPanel pnlChassis4;
/*     */   private JPanel pnlChassis5;
/*     */   private JPanel pnlChassis6;
/*     */   private JPanel pnlChassis7;
/*     */   private JPanel pnlChassis8;
/*     */   private JPanel pnlChassis9;
/*     */   private JPanel pnlCriticals;
/*     */   private JPanel pnlEquipment;
/*     */   private JPanel pnlFluff;
/*     */   private JPanel pnlInfoPanel;
/*     */   private javax.swing.JTabbedPane tbpMainTabPane;
/*     */   private JToolBar tlbIconBar;
/*     */   private JTextField txtInfoBattleValue;
/*     */   private JTextField txtInfoCost;
/*     */   private JTextField txtInfoFreeTons;
/*     */   private JTextField txtInfoHeatDiss;
/*     */   private JTextField txtInfoMaxHeat;
/*     */   private JTextField txtInfoTonnage;
/*     */   private JTextField txtInfoUnplaced;
/*     */   private JTextField txtMechModel;
/*     */   private JTextField txtMechName;
/*     */   private JTextField txtProdYear;
/*     */   private JTextField txtSource;
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\frmCompat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */