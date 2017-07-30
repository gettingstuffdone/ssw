/*     */ package ssw.printpreview;
/*     */ 
/*     */ import dialog.dlgAmmoChooser;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import ssw.print.Printer;
/*     */ 
/*     */ public class dlgPreview extends JFrame implements ActionListener
/*     */ {
/*     */   private static final double DEFAULT_ZOOM_FACTOR_STEP = 0.5D;
/*     */   protected java.awt.print.Pageable pageable;
/*     */   private Printer printer;
/*     */   private Preview preview;
/*     */   private ssw.gui.ifMechForm Parent;
/*  29 */   private File MechImage = null;
/*  30 */   private File LogoImage = null;
/*  31 */   private Preferences bfbPrefs = Preferences.userRoot().node("/com/sswsuite/bfb");
/*  32 */   private Preferences sswPrefs = Preferences.userRoot().node("/com/sswsuite/ssw");
/*     */   private JButton btnBack;
/*     */   private JButton btnChangeAmmo;
/*     */   
/*     */   public dlgPreview(String title, JFrame owner, Printer printer, java.awt.print.Pageable pageable, double zoom)
/*     */   {
/*  35 */     super(title);
/*  36 */     initComponents();
/*  37 */     this.Parent = ((ssw.gui.ifMechForm)owner);
/*  38 */     this.printer = printer;
/*  39 */     this.preview = new Preview(pageable, zoom, this.spnPreview.getSize());
/*  40 */     this.spnPreview.setViewportView(this.preview);
/*     */     
/*  42 */     this.btnZoomIn.setAction(new ZoomAction("Zoom In", "magnifier-zoom.png", this.preview, 0.5D, false));
/*  43 */     this.btnZoomOut.setAction(new ZoomAction("Zoom Out", "magnifier-zoom-out.png", this.preview, -0.5D, false));
/*     */     
/*  45 */     this.btnBack.setAction(new BrowseAction("Prev", "arrow-180.png", this.preview, -1));
/*  46 */     this.btnForward.setAction(new BrowseAction("Next", "arrow.png", this.preview, 1));
/*     */     
/*  48 */     this.btnPageWidth.setAction(new ZoomAction("Width", "document-resize.png", this.preview, this.preview.getWidthZoom(), true));
/*  49 */     this.btnPageHeight.setAction(new ZoomAction("Page", "document-resize-actual.png", this.preview, this.preview.getHeightZoom(), true));
/*     */     
/*  51 */     this.chkPrintCanon.setSelected(this.sswPrefs.getBoolean("UseCanonDots", false));
/*  52 */     this.chkPrintCharts.setSelected(this.sswPrefs.getBoolean("UseCharts", false));
/*  53 */     this.chkRS.setSelected(this.sswPrefs.getBoolean("UseRS", false));
/*  54 */     this.cmbPaperSize.setSelectedIndex(this.sswPrefs.getInt("PaperSize", 0));
/*  55 */     if (this.chkRS.isSelected()) { chkRSActionPerformed(null);
/*     */     }
/*  57 */     this.chkUseHexConversion.setSelected(this.sswPrefs.getBoolean("UseMiniConversion", false));
/*  58 */     if (this.chkUseHexConversion.isSelected()) {
/*  59 */       this.lblOneHex.setEnabled(true);
/*  60 */       this.cmbHexConvFactor.setEnabled(true);
/*  61 */       this.lblInches.setEnabled(true);
/*  62 */       this.cmbHexConvFactor.setSelectedIndex(this.sswPrefs.getInt("MiniConversionRate", 0));
/*     */     }
/*     */     
/*  65 */     if (pageable.getNumberOfPages() <= 2) {
/*  66 */       this.btnBack.setVisible(false);
/*  67 */       this.btnForward.setVisible(false);
/*  68 */       this.jSeparator1.setVisible(false);
/*     */     }
/*     */     
/*  71 */     this.spnPreview.addComponentListener(new java.awt.event.ComponentAdapter()
/*     */     {
/*     */       public void componentResized(java.awt.event.ComponentEvent e)
/*     */       {
/*  74 */         dlgPreview.this.preview.setViewportSize(e.getComponent().getSize());
/*  75 */         dlgPreview.this.btnPageWidth.setAction(new ZoomAction("Width", "document-resize.png", dlgPreview.this.preview, dlgPreview.this.preview.getWidthZoom(), true));
/*  76 */         dlgPreview.this.btnPageHeight.setAction(new ZoomAction("Page", "document-resize-actual.png", dlgPreview.this.preview, dlgPreview.this.preview.getHeightZoom(), true));
/*     */       }
/*     */     });
/*     */   }
/*     */   private JButton btnChooseImage;
/*     */   private JButton btnChooseLogo;
/*     */   
/*     */   public dlgPreview(String title, JFrame owner, Printer printer, java.awt.print.Pageable pageable)
/*     */   {
/*  82 */     this(title, owner, printer, pageable, 0.0D);
/*     */   }
/*     */   
/*     */   private JButton btnCloseDialog;
/*     */   
/*     */   public dlgPreview(String title, JFrame owner, Printer printer, java.awt.print.Printable printable, java.awt.print.PageFormat format, int pages, double zoom)
/*     */   {
/*  86 */     this(title, owner, printer, new MyPageable(printable, format, pages), zoom);
/*     */   }
/*     */   
/*     */   private JButton btnForward;
/*     */   
/*     */   public dlgPreview(String title, JFrame owner, Printer printer, java.awt.print.Printable printable, java.awt.print.PageFormat format, int pages)
/*     */   {
/*  90 */     this(title, owner, printer, printable, format, pages, 0.0D);
/*     */   }
/*     */   
/*     */   private JButton btnPageHeight;
/*     */   
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/*  94 */     dispose();
/*     */   }
/*     */   
/*     */   private static class MyPageable
/*     */     implements java.awt.print.Pageable
/*     */   {
/*     */     private java.awt.print.Printable printable;
/*     */     private java.awt.print.PageFormat format;
/*     */     private int pages;
/*     */     
/*     */     public MyPageable(java.awt.print.Printable printable, java.awt.print.PageFormat format, int pages)
/*     */     {
/*  99 */       this.printable = printable;
/* 100 */       this.format = format;
/* 101 */       this.pages = pages;
/*     */     }
/*     */     
/*     */     public int getNumberOfPages()
/*     */     {
/* 105 */       return this.pages;
/*     */     }
/*     */     
/*     */     public java.awt.print.Printable getPrintable(int index)
/*     */     {
/* 109 */       if (index >= this.pages) throw new IndexOutOfBoundsException();
/* 110 */       return this.printable;
/*     */     }
/*     */     
/*     */     public java.awt.print.PageFormat getPageFormat(int index)
/*     */     {
/* 114 */       if (index >= this.pages) throw new IndexOutOfBoundsException();
/* 115 */       return this.format;
/*     */     }
/*     */   }
/*     */   
/*     */   private JButton btnPageWidth;
/*     */   private JButton btnPrint;
/*     */   private JButton btnZoomIn;
/*     */   private JButton btnZoomOut;
/*     */   private JCheckBox chkLogo;
/*     */   private JCheckBox chkPrintCanon;
/*     */   private JCheckBox chkPrintCharts;
/*     */   private JCheckBox chkPrintImage;
/*     */   
/*     */   private void refresh()
/*     */   {
/* 124 */     setPreferences();
/* 125 */     this.preview.setPageable(this.printer.Preview());
/* 126 */     this.preview.repaint();
/*     */   }
/*     */   
/*     */   private JCheckBox chkPrintWarrior;
/*     */   private JCheckBox chkRS;
/*     */   private JCheckBox chkUseHexConversion;
/*     */   
/*     */   private void setPreferences()
/*     */   {
/* 130 */     this.sswPrefs.putBoolean("UseCanonDots", this.chkPrintCanon.isSelected());
/* 131 */     this.sswPrefs.putBoolean("UseCharts", this.chkPrintCharts.isSelected());
/* 132 */     this.sswPrefs.putBoolean("UseMiniConversion", this.chkUseHexConversion.isSelected());
/*     */     
/* 134 */     this.sswPrefs.putInt("MiniConversionRate", this.cmbHexConvFactor.getSelectedIndex());
/* 135 */     this.sswPrefs.putInt("PaperSize", this.cmbPaperSize.getSelectedIndex());
/*     */   }
/*     */   
/*     */   private JComboBox cmbChartOption;
/*     */   private JComboBox cmbGunnery;
/*     */   private JComboBox cmbHexConvFactor;
/*     */   private JComboBox cmbPaperSize;
/*     */   private JComboBox cmbPiloting;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   
/*     */   private void initComponents()
/*     */   {
/* 146 */     this.spnPreview = new javax.swing.JScrollPane();
/* 147 */     this.jPanel1 = new JPanel();
/* 148 */     this.jToolBar1 = new JToolBar();
/* 149 */     this.btnBack = new JButton();
/* 150 */     this.btnForward = new JButton();
/* 151 */     this.jSeparator1 = new javax.swing.JToolBar.Separator();
/* 152 */     this.btnPageWidth = new JButton();
/* 153 */     this.btnPageHeight = new JButton();
/* 154 */     this.jSeparator3 = new javax.swing.JToolBar.Separator();
/* 155 */     this.btnZoomIn = new JButton();
/* 156 */     this.btnZoomOut = new JButton();
/* 157 */     this.jSeparator2 = new javax.swing.JToolBar.Separator();
/* 158 */     this.btnChangeAmmo = new JButton();
/* 159 */     this.jSeparator4 = new javax.swing.JToolBar.Separator();
/* 160 */     this.btnPrint = new JButton();
/* 161 */     this.btnCloseDialog = new JButton();
/* 162 */     this.pnlPrintOptions = new JPanel();
/* 163 */     this.chkPrintCharts = new JCheckBox();
/* 164 */     this.chkUseHexConversion = new JCheckBox();
/* 165 */     this.lblOneHex = new JLabel();
/* 166 */     this.cmbHexConvFactor = new JComboBox();
/* 167 */     this.lblInches = new JLabel();
/* 168 */     this.chkPrintCanon = new JCheckBox();
/* 169 */     this.chkRS = new JCheckBox();
/* 170 */     this.jPanel3 = new JPanel();
/* 171 */     this.jLabel5 = new JLabel();
/* 172 */     this.cmbPaperSize = new JComboBox();
/* 173 */     this.cmbChartOption = new JComboBox();
/* 174 */     this.pnlImageOptions = new JPanel();
/* 175 */     this.chkPrintImage = new JCheckBox();
/* 176 */     this.btnChooseImage = new JButton();
/* 177 */     this.chkLogo = new JCheckBox();
/* 178 */     this.btnChooseLogo = new JButton();
/* 179 */     this.jPanel2 = new JPanel();
/* 180 */     this.chkPrintWarrior = new JCheckBox();
/* 181 */     this.txtGroupName = new javax.swing.JTextField();
/* 182 */     this.txtWarriorName = new javax.swing.JTextField();
/* 183 */     this.cmbGunnery = new JComboBox();
/* 184 */     this.cmbPiloting = new JComboBox();
/* 185 */     this.jLabel1 = new JLabel();
/* 186 */     this.jLabel2 = new JLabel();
/* 187 */     this.jLabel3 = new JLabel();
/*     */     
/* 189 */     setDefaultCloseOperation(2);
/* 190 */     setMinimumSize(new java.awt.Dimension(1024, 768));
/*     */     
/* 192 */     this.jToolBar1.setFloatable(false);
/* 193 */     this.jToolBar1.setRollover(true);
/*     */     
/* 195 */     this.btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/arrow-180.png")));
/* 196 */     this.btnBack.setText("Prev");
/* 197 */     this.btnBack.setFocusable(false);
/* 198 */     this.btnBack.setHorizontalTextPosition(0);
/* 199 */     this.btnBack.setVerticalTextPosition(3);
/* 200 */     this.jToolBar1.add(this.btnBack);
/*     */     
/* 202 */     this.btnForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/arrow.png")));
/* 203 */     this.btnForward.setText("Next");
/* 204 */     this.btnForward.setFocusable(false);
/* 205 */     this.btnForward.setHorizontalTextPosition(0);
/* 206 */     this.btnForward.setVerticalTextPosition(3);
/* 207 */     this.jToolBar1.add(this.btnForward);
/* 208 */     this.jToolBar1.add(this.jSeparator1);
/*     */     
/* 210 */     this.btnPageWidth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document-resize.png")));
/* 211 */     this.btnPageWidth.setText("Width");
/* 212 */     this.btnPageWidth.setFocusable(false);
/* 213 */     this.btnPageWidth.setHorizontalTextPosition(0);
/* 214 */     this.btnPageWidth.setVerticalTextPosition(3);
/* 215 */     this.jToolBar1.add(this.btnPageWidth);
/*     */     
/* 217 */     this.btnPageHeight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document-resize-actual.png")));
/* 218 */     this.btnPageHeight.setText("Page");
/* 219 */     this.btnPageHeight.setFocusable(false);
/* 220 */     this.btnPageHeight.setHorizontalTextPosition(0);
/* 221 */     this.btnPageHeight.setVerticalTextPosition(3);
/* 222 */     this.jToolBar1.add(this.btnPageHeight);
/* 223 */     this.jToolBar1.add(this.jSeparator3);
/*     */     
/* 225 */     this.btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/magnifier-zoom.png")));
/* 226 */     this.btnZoomIn.setText("Zoom In");
/* 227 */     this.btnZoomIn.setFocusable(false);
/* 228 */     this.btnZoomIn.setHorizontalTextPosition(0);
/* 229 */     this.btnZoomIn.setVerticalTextPosition(3);
/* 230 */     this.jToolBar1.add(this.btnZoomIn);
/*     */     
/* 232 */     this.btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/magnifier-zoom-out.png")));
/* 233 */     this.btnZoomOut.setText("Zoom Out");
/* 234 */     this.btnZoomOut.setFocusable(false);
/* 235 */     this.btnZoomOut.setHorizontalTextPosition(0);
/* 236 */     this.btnZoomOut.setVerticalTextPosition(3);
/* 237 */     this.jToolBar1.add(this.btnZoomOut);
/* 238 */     this.jToolBar1.add(this.jSeparator2);
/*     */     
/* 240 */     this.btnChangeAmmo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/ammo.png")));
/* 241 */     this.btnChangeAmmo.setText("Ammo");
/* 242 */     this.btnChangeAmmo.setFocusable(false);
/* 243 */     this.btnChangeAmmo.setHorizontalTextPosition(0);
/* 244 */     this.btnChangeAmmo.setVerticalTextPosition(3);
/* 245 */     this.btnChangeAmmo.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 247 */         dlgPreview.this.btnChangeAmmoActionPerformed(evt);
/*     */       }
/* 249 */     });
/* 250 */     this.jToolBar1.add(this.btnChangeAmmo);
/* 251 */     this.jToolBar1.add(this.jSeparator4);
/*     */     
/* 253 */     this.btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/printer.png")));
/* 254 */     this.btnPrint.setText("Print");
/* 255 */     this.btnPrint.setFocusable(false);
/* 256 */     this.btnPrint.setHorizontalTextPosition(0);
/* 257 */     this.btnPrint.setVerticalTextPosition(3);
/* 258 */     this.btnPrint.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 260 */         dlgPreview.this.btnPrintActionPerformed(evt);
/*     */       }
/* 262 */     });
/* 263 */     this.jToolBar1.add(this.btnPrint);
/*     */     
/* 265 */     this.btnCloseDialog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/home.png")));
/* 266 */     this.btnCloseDialog.setText("Close");
/* 267 */     this.btnCloseDialog.setFocusable(false);
/* 268 */     this.btnCloseDialog.setHorizontalTextPosition(0);
/* 269 */     this.btnCloseDialog.setVerticalTextPosition(3);
/* 270 */     this.btnCloseDialog.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 272 */         dlgPreview.this.btnCloseDialogActionPerformed(evt);
/*     */       }
/* 274 */     });
/* 275 */     this.jToolBar1.add(this.btnCloseDialog);
/*     */     
/* 277 */     this.pnlPrintOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Print Options"));
/*     */     
/* 279 */     this.chkPrintCharts.setText("Tables and Movement Grid");
/* 280 */     this.chkPrintCharts.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 282 */         dlgPreview.this.chkPrintChartsActionPerformed(evt);
/*     */       }
/*     */       
/* 285 */     });
/* 286 */     this.chkUseHexConversion.setText("Use Miniatures Scale for Movement");
/* 287 */     this.chkUseHexConversion.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 289 */         dlgPreview.this.chkUseHexConversionActionPerformed(evt);
/*     */       }
/*     */       
/* 292 */     });
/* 293 */     this.lblOneHex.setText("One Hex equals");
/* 294 */     this.lblOneHex.setEnabled(false);
/*     */     
/* 296 */     this.cmbHexConvFactor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
/* 297 */     this.cmbHexConvFactor.setEnabled(false);
/* 298 */     this.cmbHexConvFactor.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 300 */         dlgPreview.this.cmbHexConvFactorActionPerformed(evt);
/*     */       }
/*     */       
/* 303 */     });
/* 304 */     this.lblInches.setText("Inches");
/* 305 */     this.lblInches.setEnabled(false);
/*     */     
/* 307 */     this.chkPrintCanon.setText("Canon Dot Patterns");
/* 308 */     this.chkPrintCanon.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 310 */         dlgPreview.this.chkPrintCanonActionPerformed(evt);
/*     */       }
/*     */       
/* 313 */     });
/* 314 */     this.chkRS.setText("RS Format");
/* 315 */     this.chkRS.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 317 */         dlgPreview.this.chkRSActionPerformed(evt);
/*     */       }
/*     */       
/* 320 */     });
/* 321 */     this.jLabel5.setText("Paper Size:");
/* 322 */     this.jPanel3.add(this.jLabel5);
/*     */     
/* 324 */     this.cmbPaperSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Letter", "A4" }));
/* 325 */     this.cmbPaperSize.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 327 */         dlgPreview.this.cmbPaperSizeActionPerformed(evt);
/*     */       }
/* 329 */     });
/* 330 */     this.jPanel3.add(this.cmbPaperSize);
/*     */     
/* 332 */     this.cmbChartOption.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Canon", "Minimal" }));
/* 333 */     this.cmbChartOption.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 335 */         dlgPreview.this.cmbChartOptionActionPerformed(evt);
/*     */       }
/*     */       
/* 338 */     });
/* 339 */     GroupLayout pnlPrintOptionsLayout = new GroupLayout(this.pnlPrintOptions);
/* 340 */     this.pnlPrintOptions.setLayout(pnlPrintOptionsLayout);
/* 341 */     pnlPrintOptionsLayout.setHorizontalGroup(pnlPrintOptionsLayout
/* 342 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 343 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 344 */       .addContainerGap()
/* 345 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 346 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 347 */       .addComponent(this.jPanel3, -2, -1, -2)
/* 348 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/* 349 */       .addComponent(this.chkRS))
/* 350 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 351 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 352 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 353 */       .addGap(21, 21, 21)
/* 354 */       .addComponent(this.lblOneHex)
/* 355 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 356 */       .addComponent(this.cmbHexConvFactor, -2, -1, -2)
/* 357 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 358 */       .addComponent(this.lblInches))
/* 359 */       .addComponent(this.chkUseHexConversion)
/* 360 */       .addComponent(this.chkPrintCanon)
/* 361 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 362 */       .addComponent(this.chkPrintCharts)
/* 363 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 364 */       .addComponent(this.cmbChartOption, -2, 88, -2)))
/* 365 */       .addGap(0, 24, 32767)))
/* 366 */       .addContainerGap()));
/*     */     
/* 368 */     pnlPrintOptionsLayout.setVerticalGroup(pnlPrintOptionsLayout
/* 369 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 370 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 371 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 372 */       .addComponent(this.chkPrintCharts)
/* 373 */       .addComponent(this.cmbChartOption, -2, -1, -2))
/* 374 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 375 */       .addComponent(this.chkPrintCanon)
/* 376 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 377 */       .addComponent(this.chkUseHexConversion)
/* 378 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 379 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 380 */       .addComponent(this.cmbHexConvFactor, -2, -1, -2)
/* 381 */       .addComponent(this.lblOneHex))
/* 382 */       .addGroup(pnlPrintOptionsLayout.createSequentialGroup()
/* 383 */       .addGap(3, 3, 3)
/* 384 */       .addComponent(this.lblInches)))
/* 385 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 386 */       .addGroup(pnlPrintOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 387 */       .addComponent(this.jPanel3, -2, -1, -2)
/* 388 */       .addComponent(this.chkRS))
/* 389 */       .addGap(0, 0, 32767)));
/*     */     
/*     */ 
/* 392 */     this.pnlImageOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Image Options"));
/*     */     
/* 394 */     this.chkPrintImage.setText("Include TRO Pic");
/* 395 */     this.chkPrintImage.setToolTipText("From Mech file");
/* 396 */     this.chkPrintImage.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 398 */         dlgPreview.this.chkPrintImageActionPerformed(evt);
/*     */       }
/*     */       
/* 401 */     });
/* 402 */     this.btnChooseImage.setText("Choose TRO Pic");
/* 403 */     this.btnChooseImage.setEnabled(false);
/* 404 */     this.btnChooseImage.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 406 */         dlgPreview.this.btnChooseImageActionPerformed(evt);
/*     */       }
/*     */       
/* 409 */     });
/* 410 */     this.chkLogo.setText("Include Logo");
/* 411 */     this.chkLogo.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 413 */         dlgPreview.this.chkLogoActionPerformed(evt);
/*     */       }
/*     */       
/* 416 */     });
/* 417 */     this.btnChooseLogo.setText("Choose Logo");
/* 418 */     this.btnChooseLogo.setEnabled(false);
/* 419 */     this.btnChooseLogo.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 421 */         dlgPreview.this.btnChooseLogoActionPerformed(evt);
/*     */       }
/*     */       
/* 424 */     });
/* 425 */     GroupLayout pnlImageOptionsLayout = new GroupLayout(this.pnlImageOptions);
/* 426 */     this.pnlImageOptions.setLayout(pnlImageOptionsLayout);
/* 427 */     pnlImageOptionsLayout.setHorizontalGroup(pnlImageOptionsLayout
/* 428 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 429 */       .addGroup(pnlImageOptionsLayout.createSequentialGroup()
/* 430 */       .addContainerGap()
/* 431 */       .addGroup(pnlImageOptionsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
/* 432 */       .addComponent(this.chkLogo, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/* 433 */       .addComponent(this.chkPrintImage, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/* 434 */       .addComponent(this.btnChooseLogo, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/* 435 */       .addComponent(this.btnChooseImage, GroupLayout.Alignment.LEADING, -1, -1, 32767))
/* 436 */       .addContainerGap()));
/*     */     
/* 438 */     pnlImageOptionsLayout.setVerticalGroup(pnlImageOptionsLayout
/* 439 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 440 */       .addGroup(pnlImageOptionsLayout.createSequentialGroup()
/* 441 */       .addComponent(this.chkPrintImage)
/* 442 */       .addGap(6, 6, 6)
/* 443 */       .addComponent(this.btnChooseImage)
/* 444 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 445 */       .addComponent(this.chkLogo)
/* 446 */       .addGap(6, 6, 6)
/* 447 */       .addComponent(this.btnChooseLogo)
/* 448 */       .addContainerGap(-1, 32767)));
/*     */     
/*     */ 
/* 451 */     this.jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Warrior Options"));
/*     */     
/* 453 */     this.chkPrintWarrior.setSelected(true);
/* 454 */     this.chkPrintWarrior.setText("Include Warrior Data");
/* 455 */     this.chkPrintWarrior.setToolTipText("From Mech file");
/* 456 */     this.chkPrintWarrior.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 458 */         dlgPreview.this.chkPrintWarriorActionPerformed(evt);
/*     */       }
/*     */       
/* 461 */     });
/* 462 */     this.txtGroupName.addFocusListener(new java.awt.event.FocusAdapter() {
/*     */       public void focusLost(java.awt.event.FocusEvent evt) {
/* 464 */         dlgPreview.this.txtGroupNameFocusLost(evt);
/*     */       }
/*     */       
/* 467 */     });
/* 468 */     this.txtWarriorName.addFocusListener(new java.awt.event.FocusAdapter() {
/*     */       public void focusLost(java.awt.event.FocusEvent evt) {
/* 470 */         dlgPreview.this.txtWarriorNameFocusLost(evt);
/*     */       }
/*     */       
/* 473 */     });
/* 474 */     this.cmbGunnery.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));
/* 475 */     this.cmbGunnery.setSelectedIndex(4);
/* 476 */     this.cmbGunnery.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 478 */         dlgPreview.this.cmbGunneryActionPerformed(evt);
/*     */       }
/*     */       
/* 481 */     });
/* 482 */     this.cmbPiloting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));
/* 483 */     this.cmbPiloting.setSelectedIndex(5);
/* 484 */     this.cmbPiloting.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 486 */         dlgPreview.this.cmbPilotingActionPerformed(evt);
/*     */       }
/*     */       
/* 489 */     });
/* 490 */     this.jLabel1.setText("Skills:");
/*     */     
/* 492 */     this.jLabel2.setText("Name:");
/*     */     
/* 494 */     this.jLabel3.setText("Unit:");
/*     */     
/* 496 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 497 */     this.jPanel2.setLayout(jPanel2Layout);
/* 498 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/* 499 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 500 */       .addGroup(jPanel2Layout.createSequentialGroup()
/* 501 */       .addContainerGap()
/* 502 */       .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 503 */       .addComponent(this.chkPrintWarrior, -1, 177, 32767)
/* 504 */       .addGroup(jPanel2Layout.createSequentialGroup()
/* 505 */       .addComponent(this.jLabel1)
/* 506 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 507 */       .addComponent(this.cmbGunnery, -2, -1, -2)
/* 508 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 509 */       .addComponent(this.cmbPiloting, -2, -1, -2))
/* 510 */       .addGroup(jPanel2Layout.createSequentialGroup()
/* 511 */       .addComponent(this.jLabel2)
/* 512 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 513 */       .addComponent(this.txtWarriorName, -2, 123, -2))
/* 514 */       .addGroup(jPanel2Layout.createSequentialGroup()
/* 515 */       .addComponent(this.jLabel3)
/* 516 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 517 */       .addComponent(this.txtGroupName, -2, 138, -2)))
/* 518 */       .addContainerGap()));
/*     */     
/*     */ 
/* 521 */     jPanel2Layout.linkSize(0, new java.awt.Component[] { this.jLabel2, this.jLabel3 });
/*     */     
/* 523 */     jPanel2Layout.linkSize(0, new java.awt.Component[] { this.txtGroupName, this.txtWarriorName });
/*     */     
/* 525 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/* 526 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 527 */       .addGroup(jPanel2Layout.createSequentialGroup()
/* 528 */       .addComponent(this.chkPrintWarrior)
/* 529 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 530 */       .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 531 */       .addComponent(this.jLabel1)
/* 532 */       .addComponent(this.cmbGunnery, -2, -1, -2)
/* 533 */       .addComponent(this.cmbPiloting, -2, -1, -2))
/* 534 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 535 */       .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 536 */       .addComponent(this.jLabel2)
/* 537 */       .addComponent(this.txtWarriorName, -2, -1, -2))
/* 538 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/* 539 */       .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 540 */       .addComponent(this.jLabel3)
/* 541 */       .addComponent(this.txtGroupName, -2, -1, -2))
/* 542 */       .addGap(55, 55, 55)));
/*     */     
/*     */ 
/* 545 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 546 */     this.jPanel1.setLayout(jPanel1Layout);
/* 547 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/* 548 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 549 */       .addGroup(jPanel1Layout.createSequentialGroup()
/* 550 */       .addContainerGap()
/* 551 */       .addComponent(this.pnlPrintOptions, -2, -1, -2)
/* 552 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 553 */       .addComponent(this.pnlImageOptions, -2, -1, -2)
/* 554 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 555 */       .addComponent(this.jPanel2, -2, -1, -2)
/* 556 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 557 */       .addComponent(this.jToolBar1, -1, 341, 32767)
/* 558 */       .addContainerGap()));
/*     */     
/* 560 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 561 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 562 */       .addGroup(jPanel1Layout.createSequentialGroup()
/* 563 */       .addContainerGap()
/* 564 */       .addComponent(this.jToolBar1, -2, -1, -2))
/* 565 */       .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
/* 566 */       .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 567 */       .addComponent(this.jPanel2, GroupLayout.Alignment.LEADING, -1, 0, 32767)
/* 568 */       .addComponent(this.pnlImageOptions, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/* 569 */       .addComponent(this.pnlPrintOptions, -1, -1, 32767))
/* 570 */       .addContainerGap()));
/*     */     
/*     */ 
/* 573 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 574 */     getContentPane().setLayout(layout);
/* 575 */     layout.setHorizontalGroup(layout
/* 576 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 577 */       .addComponent(this.jPanel1, -1, -1, 32767)
/* 578 */       .addComponent(this.spnPreview, -1, 1024, 32767));
/*     */     
/* 580 */     layout.setVerticalGroup(layout
/* 581 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 582 */       .addGroup(layout.createSequentialGroup()
/* 583 */       .addComponent(this.jPanel1, -2, 153, -2)
/* 584 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 585 */       .addComponent(this.spnPreview, -1, 609, 32767)));
/*     */     
/*     */ 
/* 588 */     pack();
/*     */   }
/*     */   
/*     */   private JLabel jLabel3;
/*     */   
/*     */   private void chkUseHexConversionActionPerformed(ActionEvent evt)
/*     */   {
/* 592 */     if (this.chkUseHexConversion.isSelected()) {
/* 593 */       this.lblOneHex.setEnabled(true);
/* 594 */       this.cmbHexConvFactor.setEnabled(true);
/* 595 */       this.lblInches.setEnabled(true);
/*     */       
/* 597 */       this.printer.setHexConversion(this.cmbHexConvFactor.getSelectedIndex() + 1);
/*     */     } else {
/* 599 */       this.lblOneHex.setEnabled(false);
/* 600 */       this.cmbHexConvFactor.setEnabled(false);
/* 601 */       this.lblInches.setEnabled(false);
/* 602 */       this.printer.setHexConversion(0);
/*     */     }
/* 604 */     refresh();
/*     */   }
/*     */   
/*     */   private JLabel jLabel5;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   
/*     */   private void chkPrintImageActionPerformed(ActionEvent evt)
/*     */   {
/* 608 */     this.btnChooseImage.setEnabled(this.chkPrintImage.isSelected());
/* 609 */     ((Print.PrintMech)this.printer.GetMechs().get(0)).setPrintMech(Boolean.valueOf(this.chkPrintImage.isSelected()));
/* 610 */     refresh();
/*     */   }
/*     */   
/*     */   private JPanel jPanel3;
/*     */   private javax.swing.JToolBar.Separator jSeparator1;
/*     */   
/*     */   private void btnChooseImageActionPerformed(ActionEvent evt)
/*     */   {
/* 614 */     String defaultDir = "";
/* 615 */     if (this.Parent != null) defaultDir = this.sswPrefs.get("LastImagePath", "");
/* 616 */     filehandlers.Media media = new filehandlers.Media();
/* 617 */     this.MechImage = media.SelectImage(defaultDir, "Select Image");
/*     */     try
/*     */     {
/* 620 */       if (this.Parent != null) {
/* 621 */         this.sswPrefs.put("LastImage", this.MechImage.getCanonicalPath());
/* 622 */         this.sswPrefs.put("LastImagePath", this.MechImage.getCanonicalPath().replace(this.MechImage.getName(), ""));
/* 623 */         this.sswPrefs.put("LastImageFile", this.MechImage.getName());
/*     */       }
/*     */       
/*     */ 
/* 627 */       this.printer.setMechImagePath(this.MechImage.getCanonicalPath());
/* 628 */       refresh();
/*     */     } catch (Exception e) {}
/*     */   }
/*     */   
/*     */   private javax.swing.JToolBar.Separator jSeparator2;
/*     */   private javax.swing.JToolBar.Separator jSeparator3;
/*     */   private javax.swing.JToolBar.Separator jSeparator4;
/*     */   
/* 636 */   private void chkLogoActionPerformed(ActionEvent evt) { this.btnChooseLogo.setEnabled(this.chkLogo.isSelected());
/* 637 */     ((Print.PrintMech)this.printer.GetMechs().get(0)).setPrintLogo(Boolean.valueOf(this.chkLogo.isSelected()));
/* 638 */     if (this.chkLogo.isSelected()) {
/* 639 */       if (this.LogoImage != null) {
/*     */         try {
/* 641 */           this.printer.setLogoPath(this.LogoImage.getCanonicalPath());
/*     */         }
/*     */         catch (java.io.IOException ie) {}
/*     */       }
/*     */     }
/*     */     else {
/* 647 */       this.printer.setLogoPath("");
/*     */     }
/* 649 */     refresh(); }
/*     */   
/*     */   private JToolBar jToolBar1;
/*     */   private JLabel lblInches;
/* 653 */   private void btnChooseLogoActionPerformed(ActionEvent evt) { String defaultDir = "";
/* 654 */     if (this.Parent != null) defaultDir = this.sswPrefs.get("LastLogo", "");
/* 655 */     filehandlers.Media media = new filehandlers.Media();
/* 656 */     this.LogoImage = media.SelectImage(defaultDir, "Select Logo");
/*     */     try
/*     */     {
/* 659 */       if (this.Parent != null) {
/* 660 */         this.sswPrefs.put("LastLogo", this.LogoImage.getCanonicalPath());
/* 661 */         this.sswPrefs.put("LastLogoPath", this.LogoImage.getCanonicalPath().replace(this.LogoImage.getName(), ""));
/* 662 */         this.sswPrefs.put("LastLogoFile", this.LogoImage.getName());
/*     */       }
/*     */       
/*     */ 
/* 666 */       this.printer.setLogoPath(this.LogoImage.getCanonicalPath());
/* 667 */       refresh(); } catch (Exception e) {} }
/*     */   
/*     */   private JLabel lblOneHex;
/*     */   private JPanel pnlImageOptions;
/*     */   private JPanel pnlPrintOptions;
/*     */   private javax.swing.JScrollPane spnPreview;
/*     */   private javax.swing.JTextField txtGroupName;
/*     */   private javax.swing.JTextField txtWarriorName;
/* 675 */   private void chkPrintCanonActionPerformed(ActionEvent evt) { this.printer.setCanon(this.chkPrintCanon.isSelected());
/* 676 */     refresh();
/*     */   }
/*     */   
/*     */   private void chkPrintChartsActionPerformed(ActionEvent evt) {
/* 680 */     this.printer.setCharts(Boolean.valueOf(this.chkPrintCharts.isSelected()));
/* 681 */     refresh();
/*     */   }
/*     */   
/*     */   private void cmbHexConvFactorActionPerformed(ActionEvent evt) {
/* 685 */     this.printer.setHexConversion(this.cmbHexConvFactor.getSelectedIndex() + 1);
/* 686 */     refresh();
/*     */   }
/*     */   
/*     */   private void btnCloseDialogActionPerformed(ActionEvent evt) {
/* 690 */     dispose();
/*     */   }
/*     */   
/*     */   private void btnPrintActionPerformed(ActionEvent evt) {
/* 694 */     if (this.Parent != null) {
/* 695 */       this.sswPrefs.putBoolean("UseCharts", this.chkPrintCharts.isSelected());
/* 696 */       this.sswPrefs.putInt("Format_Tables_Option", this.cmbChartOption.getSelectedIndex());
/* 697 */       this.sswPrefs.putBoolean("UseMiniConversion", this.chkUseHexConversion.isSelected());
/* 698 */       this.sswPrefs.putInt("MiniConversionRate", this.cmbHexConvFactor.getSelectedIndex());
/* 699 */       this.sswPrefs.putBoolean("UseCanonDots", this.chkPrintCanon.isSelected());
/* 700 */       this.sswPrefs.putBoolean("UseRS", this.chkRS.isSelected());
/* 701 */       this.sswPrefs.putInt("PaperSize", this.cmbPaperSize.getSelectedIndex());
/*     */     }
/* 703 */     refresh();
/* 704 */     this.printer.Print(false);
/* 705 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnnChangeAmmoActionPerformed(ActionEvent evt) {
/* 709 */     dlgAmmoChooser Ammo = new dlgAmmoChooser((JFrame)this.Parent, true, ((Print.PrintMech)this.printer.GetMechs().firstElement()).CurMech, this.Parent.GetData());
/* 710 */     Ammo.setLocationRelativeTo(this);
/* 711 */     if (Ammo.HasAmmo()) {
/* 712 */       Ammo.setVisible(true);
/*     */     } else {
/* 714 */       filehandlers.Media.Messager(this, "This 'Mech has no ammunition to exchange.");
/* 715 */       Ammo.dispose();
/*     */     }
/* 717 */     refresh();
/*     */   }
/*     */   
/*     */   private void chkRSActionPerformed(ActionEvent evt) {
/* 721 */     this.chkPrintCanon.setEnabled(!this.chkRS.isSelected());
/* 722 */     this.chkPrintCanon.setSelected(true);
/* 723 */     this.chkPrintCharts.setEnabled(!this.chkRS.isSelected());
/* 724 */     this.chkPrintCharts.setSelected(false);
/* 725 */     this.chkUseHexConversion.setEnabled(!this.chkRS.isSelected());
/* 726 */     this.chkUseHexConversion.setSelected(false);
/* 727 */     chkUseHexConversionActionPerformed(evt);
/* 728 */     this.cmbHexConvFactor.setSelectedIndex(0);
/* 729 */     this.chkPrintWarrior.setSelected(false);
/* 730 */     this.printer.setTRO(Boolean.valueOf(this.chkRS.isSelected()));
/* 731 */     refresh();
/*     */   }
/*     */   
/*     */   private void btnChangeAmmoActionPerformed(ActionEvent evt) {
/* 735 */     dlgAmmoChooser Ammo = new dlgAmmoChooser((JFrame)this.Parent, true, ((Print.PrintMech)this.printer.GetMechs().firstElement()).CurMech, this.Parent.GetData());
/* 736 */     Ammo.setLocationRelativeTo(this);
/* 737 */     if (Ammo.HasAmmo()) {
/* 738 */       Ammo.setVisible(true);
/*     */     } else {
/* 740 */       filehandlers.Media.Messager(this, "This 'Mech has no ammunition to exchange.");
/* 741 */       Ammo.dispose();
/*     */     }
/* 743 */     refresh();
/*     */   }
/*     */   
/*     */   private void UpdateWarriorData() {
/* 747 */     if (this.chkPrintWarrior.isSelected()) {
/* 748 */       this.printer.setWarriorData(this.cmbGunnery.getSelectedIndex(), this.cmbPiloting.getSelectedIndex(), this.txtWarriorName.getText(), this.txtGroupName.getText());
/* 749 */       refresh();
/*     */     }
/*     */   }
/*     */   
/*     */   private void chkPrintWarriorActionPerformed(ActionEvent evt) {
/* 754 */     this.cmbGunnery.setEnabled(this.chkPrintWarrior.isSelected());
/* 755 */     this.cmbPiloting.setEnabled(this.chkPrintWarrior.isSelected());
/* 756 */     this.txtWarriorName.setEnabled(this.chkPrintWarrior.isSelected());
/* 757 */     this.txtGroupName.setEnabled(this.chkPrintWarrior.isSelected());
/*     */     
/* 759 */     this.printer.setPrintWarrior(this.chkPrintWarrior.isSelected());
/* 760 */     UpdateWarriorData();
/* 761 */     refresh();
/*     */   }
/*     */   
/*     */   private void cmbGunneryActionPerformed(ActionEvent evt) {
/* 765 */     UpdateWarriorData();
/*     */   }
/*     */   
/*     */   private void cmbPilotingActionPerformed(ActionEvent evt) {
/* 769 */     UpdateWarriorData();
/*     */   }
/*     */   
/*     */   private void txtWarriorNameFocusLost(java.awt.event.FocusEvent evt) {
/* 773 */     UpdateWarriorData();
/*     */   }
/*     */   
/*     */   private void txtGroupNameFocusLost(java.awt.event.FocusEvent evt) {
/* 777 */     UpdateWarriorData();
/*     */   }
/*     */   
/*     */   private void cmbPaperSizeActionPerformed(ActionEvent evt) {
/* 781 */     switch (this.cmbPaperSize.getSelectedIndex())
/*     */     {
/*     */     case 0: 
/* 784 */       this.printer.setPaperSize(Printer.Letter);
/* 785 */       break;
/*     */     case 1: 
/* 787 */       this.printer.setPaperSize(Printer.A4);
/*     */     }
/* 789 */     refresh();
/*     */   }
/*     */   
/*     */   private void cmbChartOptionActionPerformed(ActionEvent evt) {
/* 793 */     this.printer.setChartImageOption(this.cmbChartOption.getSelectedItem().toString());
/* 794 */     refresh();
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\printpreview\dlgPreview.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */