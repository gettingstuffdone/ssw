/*     */ package ssw.printpreview;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JToolBar;
/*     */ import ssw.print.Printer;
/*     */ 
/*     */ public class dlgBFPreview extends javax.swing.JFrame implements java.awt.event.ActionListener
/*     */ {
/*     */   private static final double DEFAULT_ZOOM_FACTOR_STEP = 0.5D;
/*     */   protected java.awt.print.Pageable pageable;
/*     */   private Printer printer;
/*     */   private Preview preview;
/*     */   private JButton btnBack;
/*     */   private JRadioButton btnCL;
/*     */   private JRadioButton btnCS;
/*     */   private JButton btnCloseDialog;
/*     */   private JButton btnForward;
/*     */   private javax.swing.ButtonGroup btnGrpUnitSize;
/*     */   private JRadioButton btnIS;
/*     */   private JButton btnPageHeight;
/*     */   private JButton btnPageWidth;
/*     */   private JButton btnPrint;
/*     */   private JButton btnZoomIn;
/*     */   private JButton btnZoomOut;
/*     */   private javax.swing.JPanel jPanel1;
/*     */   private javax.swing.JToolBar.Separator jSeparator1;
/*     */   private javax.swing.JToolBar.Separator jSeparator2;
/*     */   private javax.swing.JToolBar.Separator jSeparator3;
/*     */   private JToolBar jToolBar1;
/*     */   private javax.swing.JScrollPane spnPreview;
/*     */   
/*     */   public dlgBFPreview(String title, javax.swing.JFrame owner, Printer printer, java.awt.print.Pageable pageable, double zoom)
/*     */   {
/*  38 */     super(title);
/*  39 */     initComponents();
/*     */     
/*  41 */     this.printer = printer;
/*  42 */     this.preview = new Preview(pageable, zoom, this.spnPreview.getSize());
/*  43 */     this.spnPreview.setViewportView(this.preview);
/*     */     
/*  45 */     this.btnZoomIn.setAction(new ZoomAction("Zoom In", "magnifier-zoom.png", this.preview, 0.5D, false));
/*  46 */     this.btnZoomOut.setAction(new ZoomAction("Zoom Out", "magnifier-zoom-out.png", this.preview, -0.5D, false));
/*     */     
/*  48 */     this.btnBack.setAction(new BrowseAction("Prev", "arrow-180.png", this.preview, -1));
/*  49 */     this.btnForward.setAction(new BrowseAction("Next", "arrow.png", this.preview, 1));
/*     */     
/*  51 */     this.btnPageWidth.setAction(new ZoomAction("Width", "document-resize.png", this.preview, this.preview.getWidthZoom(), true));
/*  52 */     this.btnPageHeight.setAction(new ZoomAction("Page", "document-resize-actual.png", this.preview, this.preview.getHeightZoom(), true));
/*     */     
/*  54 */     this.spnPreview.addComponentListener(new java.awt.event.ComponentAdapter()
/*     */     {
/*     */       public void componentResized(java.awt.event.ComponentEvent e) {
/*  57 */         dlgBFPreview.this.preview.setViewportSize(e.getComponent().getSize());
/*  58 */         dlgBFPreview.this.btnPageWidth.setAction(new ZoomAction("Width", "document-resize.png", dlgBFPreview.this.preview, dlgBFPreview.this.preview.getWidthZoom(), true));
/*  59 */         dlgBFPreview.this.btnPageHeight.setAction(new ZoomAction("Page", "document-resize-actual.png", dlgBFPreview.this.preview, dlgBFPreview.this.preview.getHeightZoom(), true));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public dlgBFPreview(String title, javax.swing.JFrame owner, Printer printer, java.awt.print.Pageable pageable) {
/*  65 */     this(title, owner, printer, pageable, 0.0D);
/*     */   }
/*     */   
/*     */   public dlgBFPreview(String title, javax.swing.JFrame owner, Printer printer, java.awt.print.Printable printable, java.awt.print.PageFormat format, int pages, double zoom) {
/*  69 */     this(title, owner, printer, new MyPageable(printable, format, pages), zoom);
/*     */   }
/*     */   
/*     */   public dlgBFPreview(String title, javax.swing.JFrame owner, Printer printer, java.awt.print.Printable printable, java.awt.print.PageFormat format, int pages) {
/*  73 */     this(title, owner, printer, printable, format, pages, 0.0D);
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
/*  85 */     this.btnGrpUnitSize = new javax.swing.ButtonGroup();
/*  86 */     this.jToolBar1 = new JToolBar();
/*  87 */     this.btnBack = new JButton();
/*  88 */     this.btnForward = new JButton();
/*  89 */     this.jSeparator1 = new javax.swing.JToolBar.Separator();
/*  90 */     this.btnPageWidth = new JButton();
/*  91 */     this.btnPageHeight = new JButton();
/*  92 */     this.jSeparator3 = new javax.swing.JToolBar.Separator();
/*  93 */     this.btnZoomIn = new JButton();
/*  94 */     this.btnZoomOut = new JButton();
/*  95 */     this.jSeparator2 = new javax.swing.JToolBar.Separator();
/*  96 */     this.btnPrint = new JButton();
/*  97 */     this.btnCloseDialog = new JButton();
/*  98 */     this.spnPreview = new javax.swing.JScrollPane();
/*  99 */     this.jPanel1 = new javax.swing.JPanel();
/* 100 */     this.btnIS = new JRadioButton();
/* 101 */     this.btnCS = new JRadioButton();
/* 102 */     this.btnCL = new JRadioButton();
/*     */     
/* 104 */     setDefaultCloseOperation(2);
/* 105 */     setTitle("BattleForce Print Preview");
/*     */     
/* 107 */     this.jToolBar1.setFloatable(false);
/* 108 */     this.jToolBar1.setRollover(true);
/*     */     
/* 110 */     this.btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/arrow-180.png")));
/* 111 */     this.btnBack.setText("Prev");
/* 112 */     this.btnBack.setFocusable(false);
/* 113 */     this.btnBack.setHorizontalTextPosition(0);
/* 114 */     this.btnBack.setVerticalTextPosition(3);
/* 115 */     this.jToolBar1.add(this.btnBack);
/*     */     
/* 117 */     this.btnForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/arrow.png")));
/* 118 */     this.btnForward.setText("Next");
/* 119 */     this.btnForward.setFocusable(false);
/* 120 */     this.btnForward.setHorizontalTextPosition(0);
/* 121 */     this.btnForward.setVerticalTextPosition(3);
/* 122 */     this.jToolBar1.add(this.btnForward);
/* 123 */     this.jToolBar1.add(this.jSeparator1);
/*     */     
/* 125 */     this.btnPageWidth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document-resize.png")));
/* 126 */     this.btnPageWidth.setText("Width");
/* 127 */     this.btnPageWidth.setFocusable(false);
/* 128 */     this.btnPageWidth.setHorizontalTextPosition(0);
/* 129 */     this.btnPageWidth.setVerticalTextPosition(3);
/* 130 */     this.jToolBar1.add(this.btnPageWidth);
/*     */     
/* 132 */     this.btnPageHeight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/document-resize-actual.png")));
/* 133 */     this.btnPageHeight.setText("Page");
/* 134 */     this.btnPageHeight.setFocusable(false);
/* 135 */     this.btnPageHeight.setHorizontalTextPosition(0);
/* 136 */     this.btnPageHeight.setVerticalTextPosition(3);
/* 137 */     this.jToolBar1.add(this.btnPageHeight);
/* 138 */     this.jToolBar1.add(this.jSeparator3);
/*     */     
/* 140 */     this.btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/magnifier-zoom.png")));
/* 141 */     this.btnZoomIn.setText("Zoom In");
/* 142 */     this.btnZoomIn.setFocusable(false);
/* 143 */     this.btnZoomIn.setHorizontalTextPosition(0);
/* 144 */     this.btnZoomIn.setVerticalTextPosition(3);
/* 145 */     this.jToolBar1.add(this.btnZoomIn);
/*     */     
/* 147 */     this.btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/magnifier-zoom-out.png")));
/* 148 */     this.btnZoomOut.setText("Zoom Out");
/* 149 */     this.btnZoomOut.setFocusable(false);
/* 150 */     this.btnZoomOut.setHorizontalTextPosition(0);
/* 151 */     this.btnZoomOut.setVerticalTextPosition(3);
/* 152 */     this.jToolBar1.add(this.btnZoomOut);
/* 153 */     this.jToolBar1.add(this.jSeparator2);
/*     */     
/* 155 */     this.btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/printer.png")));
/* 156 */     this.btnPrint.setText("Print");
/* 157 */     this.btnPrint.setFocusable(false);
/* 158 */     this.btnPrint.setHorizontalTextPosition(0);
/* 159 */     this.btnPrint.setVerticalTextPosition(3);
/* 160 */     this.btnPrint.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 162 */         dlgBFPreview.this.btnPrintActionPerformed(evt);
/*     */       }
/* 164 */     });
/* 165 */     this.jToolBar1.add(this.btnPrint);
/*     */     
/* 167 */     this.btnCloseDialog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ssw/Images/home.png")));
/* 168 */     this.btnCloseDialog.setText("Close");
/* 169 */     this.btnCloseDialog.setFocusable(false);
/* 170 */     this.btnCloseDialog.setHorizontalTextPosition(0);
/* 171 */     this.btnCloseDialog.setVerticalTextPosition(3);
/* 172 */     this.btnCloseDialog.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 174 */         dlgBFPreview.this.btnCloseDialogActionPerformed(evt);
/*     */       }
/* 176 */     });
/* 177 */     this.jToolBar1.add(this.btnCloseDialog);
/*     */     
/* 179 */     this.jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sheet Type"));
/*     */     
/* 181 */     this.btnGrpUnitSize.add(this.btnIS);
/* 182 */     this.btnIS.setSelected(true);
/* 183 */     this.btnIS.setText("Inner Sphere Lance (4)");
/* 184 */     this.btnIS.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 186 */         dlgBFPreview.this.btnISActionPerformed(evt);
/*     */       }
/*     */       
/* 189 */     });
/* 190 */     this.btnGrpUnitSize.add(this.btnCS);
/* 191 */     this.btnCS.setText("Comstar/Word of Blake Level II (6)");
/* 192 */     this.btnCS.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 194 */         dlgBFPreview.this.btnCSActionPerformed(evt);
/*     */       }
/*     */       
/* 197 */     });
/* 198 */     this.btnGrpUnitSize.add(this.btnCL);
/* 199 */     this.btnCL.setText("Clan Star (5)");
/* 200 */     this.btnCL.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 202 */         dlgBFPreview.this.btnCLActionPerformed(evt);
/*     */       }
/*     */       
/* 205 */     });
/* 206 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 207 */     this.jPanel1.setLayout(jPanel1Layout);
/* 208 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/* 209 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 210 */       .addGroup(jPanel1Layout.createSequentialGroup()
/* 211 */       .addContainerGap()
/* 212 */       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 213 */       .addComponent(this.btnCS)
/* 214 */       .addComponent(this.btnCL)
/* 215 */       .addComponent(this.btnIS))
/* 216 */       .addContainerGap(24, 32767)));
/*     */     
/* 218 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 219 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 220 */       .addGroup(jPanel1Layout.createSequentialGroup()
/* 221 */       .addComponent(this.btnIS)
/* 222 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 223 */       .addComponent(this.btnCL)
/* 224 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 225 */       .addComponent(this.btnCS)));
/*     */     
/*     */ 
/* 228 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 229 */     getContentPane().setLayout(layout);
/* 230 */     layout.setHorizontalGroup(layout
/* 231 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 232 */       .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 233 */       .addComponent(this.jPanel1, -1, -1, 32767)
/* 234 */       .addGap(251, 251, 251)
/* 235 */       .addComponent(this.jToolBar1, -2, 310, -2))
/* 236 */       .addComponent(this.spnPreview, -1, 800, 32767));
/*     */     
/* 238 */     layout.setVerticalGroup(layout
/* 239 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 240 */       .addGroup(layout.createSequentialGroup()
/* 241 */       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 242 */       .addComponent(this.jToolBar1, -2, 43, -2)
/* 243 */       .addComponent(this.jPanel1, -2, -1, -2))
/* 244 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/* 245 */       .addComponent(this.spnPreview, -1, 495, 32767)));
/*     */     
/*     */ 
/* 248 */     pack();
/*     */   }
/*     */   
/*     */   private void refresh() {
/* 252 */     this.preview.setPageable(this.printer.PreviewBattleforce());
/* 253 */     this.preview.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void btnPrintActionPerformed(ActionEvent evt)
/*     */   {
/* 260 */     refresh();
/* 261 */     this.printer.Print(false);
/*     */   }
/*     */   
/*     */   private void btnCloseDialogActionPerformed(ActionEvent evt) {
/* 265 */     dispose();
/*     */   }
/*     */   
/*     */   private void btnISActionPerformed(ActionEvent evt) {
/* 269 */     this.printer.setBattleForceSheet("Inner Sphere");
/* 270 */     refresh();
/*     */   }
/*     */   
/*     */   private void btnCLActionPerformed(ActionEvent evt) {
/* 274 */     this.printer.setBattleForceSheet("Clan");
/* 275 */     refresh();
/*     */   }
/*     */   
/*     */   private void btnCSActionPerformed(ActionEvent evt) {
/* 279 */     this.printer.setBattleForceSheet("Comstar");
/* 280 */     refresh();
/*     */   }
/*     */   
/*     */ 
/* 284 */   public void actionPerformed(ActionEvent e) { dispose(); }
/*     */   
/*     */   private static class MyPageable implements java.awt.print.Pageable {
/*     */     private java.awt.print.Printable printable;
/*     */     
/* 289 */     public MyPageable(java.awt.print.Printable printable, java.awt.print.PageFormat format, int pages) { this.printable = printable;
/* 290 */       this.format = format;
/* 291 */       this.pages = pages; }
/*     */     
/*     */     private java.awt.print.PageFormat format;
/*     */     private int pages;
/* 295 */     public int getNumberOfPages() { return this.pages; }
/*     */     
/*     */     public java.awt.print.Printable getPrintable(int index)
/*     */     {
/* 299 */       if (index >= this.pages) throw new IndexOutOfBoundsException();
/* 300 */       return this.printable;
/*     */     }
/*     */     
/*     */     public java.awt.print.PageFormat getPageFormat(int index) {
/* 304 */       if (index >= this.pages) throw new IndexOutOfBoundsException();
/* 305 */       return this.format;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\printpreview\dlgBFPreview.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */