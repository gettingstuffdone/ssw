/*     */ package ssw.gui;
/*     */ 
/*     */ import components.EquipmentCollection;
/*     */ import components.Mech;
/*     */ import components.abPlaceable;
/*     */ import components.ifMechLoadout;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.text.ParseException;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JSpinner.DefaultEditor;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgSelectiveAllocate
/*     */   extends JDialog
/*     */ {
/*     */   ifMechForm Parent;
/*  41 */   abPlaceable Item = null;
/*  42 */   EquipmentCollection Items = null;
/*     */   ifMechLoadout CurLoadout;
/*  44 */   int total = 0;
/*  45 */   int[] Crits = { 0, 0, 0, 0, 0, 0, 0, 0 };
/*  46 */   int[] Alloc = { 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */   private JButton btnCancel;
/*     */   private JButton btnOkay;
/*     */   private JPanel jPanel1; private JLabel lblAllocateItem;
/*  50 */   public dlgSelectiveAllocate(Frame parent, boolean modal, abPlaceable p) { super(parent, modal);
/*  51 */     initComponents();
/*  52 */     setTitle("Selective Allocation");
/*  53 */     setResizable(false);
/*  54 */     this.Parent = ((ifMechForm)parent);
/*  55 */     this.Item = p;
/*  56 */     this.CurLoadout = this.Parent.GetMech().GetLoadout();
/*  57 */     InitializeSingle(); }
/*     */   
/*     */   private JLabel lblCTQuant;
/*     */   
/*  61 */   public dlgSelectiveAllocate(Frame parent, boolean modal, EquipmentCollection e) { super(parent, modal);
/*  62 */     initComponents();
/*  63 */     setTitle("Selective Allocation");
/*  64 */     setResizable(false);
/*  65 */     this.Parent = ((ifMechForm)parent);
/*  66 */     this.Items = e;
/*  67 */     this.CurLoadout = this.Parent.GetMech().GetLoadout();
/*  68 */     InitializeCollection(); }
/*     */   
/*     */   private JLabel lblHDQuant;
/*     */   
/*  72 */   private void InitializeSingle() { if (this.Parent.GetMech().IsQuad()) {
/*  73 */       ((TitledBorder)this.pnlRA.getBorder()).setTitle("RFL");
/*  74 */       ((TitledBorder)this.pnlLA.getBorder()).setTitle("LFL");
/*  75 */       ((TitledBorder)this.pnlRL.getBorder()).setTitle("RRL");
/*  76 */       ((TitledBorder)this.pnlLL.getBorder()).setTitle("LRL");
/*     */     } else {
/*  78 */       ((TitledBorder)this.pnlRA.getBorder()).setTitle("RA");
/*  79 */       ((TitledBorder)this.pnlLA.getBorder()).setTitle("LA");
/*  80 */       ((TitledBorder)this.pnlRL.getBorder()).setTitle("RL");
/*  81 */       ((TitledBorder)this.pnlLL.getBorder()).setTitle("LL");
/*     */     }
/*     */     
/*     */ 
/*  85 */     this.Crits[0] = this.CurLoadout.FreeCrits(this.CurLoadout.GetHDCrits());
/*  86 */     this.Crits[1] = this.CurLoadout.FreeCrits(this.CurLoadout.GetCTCrits());
/*  87 */     this.Crits[2] = this.CurLoadout.FreeCrits(this.CurLoadout.GetLTCrits());
/*  88 */     this.Crits[3] = this.CurLoadout.FreeCrits(this.CurLoadout.GetRTCrits());
/*  89 */     this.Crits[4] = this.CurLoadout.FreeCrits(this.CurLoadout.GetLACrits());
/*  90 */     this.Crits[5] = this.CurLoadout.FreeCrits(this.CurLoadout.GetRACrits());
/*  91 */     this.Crits[6] = this.CurLoadout.FreeCrits(this.CurLoadout.GetLLCrits());
/*  92 */     this.Crits[7] = this.CurLoadout.FreeCrits(this.CurLoadout.GetRLCrits());
/*     */     
/*  94 */     this.lblAllocateItem.setText("Allocating " + this.Item.CritName());
/*  95 */     this.lblItemCrits.setText(this.Item.NumPlaced() + " of " + this.Item.NumCrits() + " Allocated");
/*  96 */     this.lblHDQuant.setText("0 of " + this.Crits[0]);
/*  97 */     this.lblCTQuant.setText("0 of " + this.Crits[1]);
/*  98 */     this.lblLTQuant.setText("0 of " + this.Crits[2]);
/*  99 */     this.lblRTQuant.setText("0 of " + this.Crits[3]);
/* 100 */     this.lblLAQuant.setText("0 of " + this.Crits[4]);
/* 101 */     this.lblRAQuant.setText("0 of " + this.Crits[5]);
/* 102 */     this.lblLLQuant.setText("0 of " + this.Crits[6]);
/* 103 */     this.lblRLQuant.setText("0 of " + this.Crits[7]);
/*     */     
/*     */ 
/* 106 */     this.spnHDCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[0], 1));
/* 107 */     this.spnCTCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[1], 1));
/* 108 */     this.spnLTCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[2], 1));
/* 109 */     this.spnRTCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[3], 1));
/* 110 */     this.spnLACrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[4], 1));
/* 111 */     this.spnRACrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[5], 1));
/* 112 */     this.spnLLCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[6], 1));
/* 113 */     this.spnRLCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[7], 1));
/*     */     
/*     */ 
/* 116 */     this.total = this.Item.NumPlaced();
/*     */   }
/*     */   
/*     */   private JLabel lblItemCrits;
/* 120 */   private void InitializeCollection() { abPlaceable CurItem = this.Items.GetType();
/* 121 */     int size = CurItem.NumCrits();
/*     */     
/* 123 */     if (this.Parent.GetMech().IsQuad()) {
/* 124 */       ((TitledBorder)this.pnlRA.getBorder()).setTitle("RFL");
/* 125 */       ((TitledBorder)this.pnlLA.getBorder()).setTitle("LFL");
/* 126 */       ((TitledBorder)this.pnlRL.getBorder()).setTitle("RRL");
/* 127 */       ((TitledBorder)this.pnlLL.getBorder()).setTitle("LRL");
/*     */     } else {
/* 129 */       ((TitledBorder)this.pnlRA.getBorder()).setTitle("RA");
/* 130 */       ((TitledBorder)this.pnlLA.getBorder()).setTitle("LA");
/* 131 */       ((TitledBorder)this.pnlRL.getBorder()).setTitle("RL");
/* 132 */       ((TitledBorder)this.pnlLL.getBorder()).setTitle("LL");
/*     */     }
/*     */     
/*     */ 
/* 136 */     this.Crits[0] = GetNumSlots(size, this.CurLoadout.FreeCrits(this.CurLoadout.GetHDCrits()));
/* 137 */     this.Crits[1] = GetNumSlots(size, this.CurLoadout.FreeCrits(this.CurLoadout.GetCTCrits()));
/* 138 */     this.Crits[2] = GetNumSlots(size, this.CurLoadout.FreeCrits(this.CurLoadout.GetLTCrits()));
/* 139 */     this.Crits[3] = GetNumSlots(size, this.CurLoadout.FreeCrits(this.CurLoadout.GetRTCrits()));
/* 140 */     this.Crits[4] = GetNumSlots(size, this.CurLoadout.FreeCrits(this.CurLoadout.GetLACrits()));
/* 141 */     this.Crits[5] = GetNumSlots(size, this.CurLoadout.FreeCrits(this.CurLoadout.GetRACrits()));
/* 142 */     this.Crits[6] = GetNumSlots(size, this.CurLoadout.FreeCrits(this.CurLoadout.GetLLCrits()));
/* 143 */     this.Crits[7] = GetNumSlots(size, this.CurLoadout.FreeCrits(this.CurLoadout.GetRLCrits()));
/*     */     
/* 145 */     this.lblAllocateItem.setText("Allocating " + CurItem.CritName());
/* 146 */     this.lblItemCrits.setText("0 of " + this.Items.GetSize() + " Allocated");
/* 147 */     this.lblHDQuant.setText("0 of " + this.Crits[0]);
/* 148 */     this.lblCTQuant.setText("0 of " + this.Crits[1]);
/* 149 */     this.lblLTQuant.setText("0 of " + this.Crits[2]);
/* 150 */     this.lblRTQuant.setText("0 of " + this.Crits[3]);
/* 151 */     this.lblLAQuant.setText("0 of " + this.Crits[4]);
/* 152 */     this.lblRAQuant.setText("0 of " + this.Crits[5]);
/* 153 */     this.lblLLQuant.setText("0 of " + this.Crits[6]);
/* 154 */     this.lblRLQuant.setText("0 of " + this.Crits[7]);
/*     */     
/*     */ 
/* 157 */     this.spnHDCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[0], 1));
/* 158 */     this.spnCTCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[1], 1));
/* 159 */     this.spnLTCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[2], 1));
/* 160 */     this.spnRTCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[3], 1));
/* 161 */     this.spnLACrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[4], 1));
/* 162 */     this.spnRACrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[5], 1));
/* 163 */     this.spnLLCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[6], 1));
/* 164 */     this.spnRLCrits.setModel(new SpinnerNumberModel(0, 0, this.Crits[7], 1));
/*     */     
/*     */ 
/* 167 */     this.total = 0; }
/*     */   
/*     */   private JLabel lblLAQuant;
/*     */   
/* 171 */   private int GetNumSlots(int size, int free) { return (int)Math.floor(free / size); }
/*     */   
/*     */   private JLabel lblLLQuant;
/*     */   private JLabel lblLTQuant;
/*     */   private JLabel lblRAQuant;
/*     */   private JLabel lblRLQuant;
/*     */   private JLabel lblRTQuant;
/*     */   private JPanel pnlCT;
/*     */   private JPanel pnlHD;
/*     */   private JPanel pnlLA;
/*     */   private JPanel pnlLL;
/*     */   
/* 183 */   private void initComponents() { this.pnlHD = new JPanel();
/* 184 */     this.lblHDQuant = new JLabel();
/* 185 */     this.spnHDCrits = new JSpinner();
/* 186 */     this.pnlCT = new JPanel();
/* 187 */     this.lblCTQuant = new JLabel();
/* 188 */     this.spnCTCrits = new JSpinner();
/* 189 */     this.pnlLT = new JPanel();
/* 190 */     this.lblLTQuant = new JLabel();
/* 191 */     this.spnLTCrits = new JSpinner();
/* 192 */     this.pnlRT = new JPanel();
/* 193 */     this.lblRTQuant = new JLabel();
/* 194 */     this.spnRTCrits = new JSpinner();
/* 195 */     this.pnlLA = new JPanel();
/* 196 */     this.lblLAQuant = new JLabel();
/* 197 */     this.spnLACrits = new JSpinner();
/* 198 */     this.pnlRA = new JPanel();
/* 199 */     this.lblRAQuant = new JLabel();
/* 200 */     this.spnRACrits = new JSpinner();
/* 201 */     this.pnlLL = new JPanel();
/* 202 */     this.lblLLQuant = new JLabel();
/* 203 */     this.spnLLCrits = new JSpinner();
/* 204 */     this.pnlRL = new JPanel();
/* 205 */     this.lblRLQuant = new JLabel();
/* 206 */     this.spnRLCrits = new JSpinner();
/* 207 */     this.jPanel1 = new JPanel();
/* 208 */     this.btnOkay = new JButton();
/* 209 */     this.btnCancel = new JButton();
/* 210 */     this.lblItemCrits = new JLabel();
/* 211 */     this.lblAllocateItem = new JLabel();
/*     */     
/* 213 */     setDefaultCloseOperation(2);
/* 214 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/* 216 */     this.pnlHD.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "HD", 2, 0));
/* 217 */     this.pnlHD.setLayout(new BorderLayout());
/*     */     
/* 219 */     this.lblHDQuant.setHorizontalAlignment(0);
/* 220 */     this.lblHDQuant.setText("00 of 00");
/* 221 */     this.pnlHD.add(this.lblHDQuant, "Center");
/*     */     
/* 223 */     this.spnHDCrits.setMaximumSize(new Dimension(50, 25));
/* 224 */     this.spnHDCrits.setMinimumSize(new Dimension(50, 25));
/* 225 */     this.spnHDCrits.setPreferredSize(new Dimension(50, 25));
/* 226 */     this.spnHDCrits.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 228 */         dlgSelectiveAllocate.this.spnHDCritsStateChanged(evt);
/*     */       }
/* 230 */     });
/* 231 */     this.pnlHD.add(this.spnHDCrits, "First");
/*     */     
/* 233 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 234 */     gridBagConstraints.gridx = 2;
/* 235 */     gridBagConstraints.gridy = 1;
/* 236 */     getContentPane().add(this.pnlHD, gridBagConstraints);
/*     */     
/* 238 */     this.pnlCT.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "CT", 2, 0));
/* 239 */     this.pnlCT.setLayout(new BorderLayout());
/*     */     
/* 241 */     this.lblCTQuant.setHorizontalAlignment(0);
/* 242 */     this.lblCTQuant.setText("00 of 00");
/* 243 */     this.pnlCT.add(this.lblCTQuant, "Center");
/*     */     
/* 245 */     this.spnCTCrits.setMaximumSize(new Dimension(50, 25));
/* 246 */     this.spnCTCrits.setMinimumSize(new Dimension(50, 25));
/* 247 */     this.spnCTCrits.setPreferredSize(new Dimension(50, 25));
/* 248 */     this.spnCTCrits.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 250 */         dlgSelectiveAllocate.this.spnCTCritsStateChanged(evt);
/*     */       }
/* 252 */     });
/* 253 */     this.pnlCT.add(this.spnCTCrits, "First");
/*     */     
/* 255 */     gridBagConstraints = new GridBagConstraints();
/* 256 */     gridBagConstraints.gridx = 2;
/* 257 */     gridBagConstraints.gridy = 2;
/* 258 */     getContentPane().add(this.pnlCT, gridBagConstraints);
/*     */     
/* 260 */     this.pnlLT.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LT", 2, 0));
/* 261 */     this.pnlLT.setLayout(new BorderLayout());
/*     */     
/* 263 */     this.lblLTQuant.setHorizontalAlignment(0);
/* 264 */     this.lblLTQuant.setText("00 of 00");
/* 265 */     this.pnlLT.add(this.lblLTQuant, "Center");
/*     */     
/* 267 */     this.spnLTCrits.setMaximumSize(new Dimension(50, 25));
/* 268 */     this.spnLTCrits.setMinimumSize(new Dimension(50, 25));
/* 269 */     this.spnLTCrits.setPreferredSize(new Dimension(50, 25));
/* 270 */     this.spnLTCrits.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 272 */         dlgSelectiveAllocate.this.spnLTCritsStateChanged(evt);
/*     */       }
/* 274 */     });
/* 275 */     this.pnlLT.add(this.spnLTCrits, "First");
/*     */     
/* 277 */     gridBagConstraints = new GridBagConstraints();
/* 278 */     gridBagConstraints.gridx = 1;
/* 279 */     gridBagConstraints.gridy = 1;
/* 280 */     gridBagConstraints.gridheight = 2;
/* 281 */     getContentPane().add(this.pnlLT, gridBagConstraints);
/*     */     
/* 283 */     this.pnlRT.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RT", 2, 0));
/* 284 */     this.pnlRT.setLayout(new BorderLayout());
/*     */     
/* 286 */     this.lblRTQuant.setHorizontalAlignment(0);
/* 287 */     this.lblRTQuant.setText("00 of 00");
/* 288 */     this.pnlRT.add(this.lblRTQuant, "Center");
/*     */     
/* 290 */     this.spnRTCrits.setMaximumSize(new Dimension(50, 25));
/* 291 */     this.spnRTCrits.setMinimumSize(new Dimension(50, 25));
/* 292 */     this.spnRTCrits.setPreferredSize(new Dimension(50, 25));
/* 293 */     this.spnRTCrits.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 295 */         dlgSelectiveAllocate.this.spnRTCritsStateChanged(evt);
/*     */       }
/* 297 */     });
/* 298 */     this.pnlRT.add(this.spnRTCrits, "First");
/*     */     
/* 300 */     gridBagConstraints = new GridBagConstraints();
/* 301 */     gridBagConstraints.gridx = 3;
/* 302 */     gridBagConstraints.gridy = 1;
/* 303 */     gridBagConstraints.gridheight = 2;
/* 304 */     getContentPane().add(this.pnlRT, gridBagConstraints);
/*     */     
/* 306 */     this.pnlLA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LA", 2, 0));
/* 307 */     this.pnlLA.setLayout(new BorderLayout());
/*     */     
/* 309 */     this.lblLAQuant.setHorizontalAlignment(0);
/* 310 */     this.lblLAQuant.setText("00 of 00");
/* 311 */     this.pnlLA.add(this.lblLAQuant, "Center");
/*     */     
/* 313 */     this.spnLACrits.setMaximumSize(new Dimension(50, 25));
/* 314 */     this.spnLACrits.setMinimumSize(new Dimension(50, 25));
/* 315 */     this.spnLACrits.setPreferredSize(new Dimension(50, 25));
/* 316 */     this.spnLACrits.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 318 */         dlgSelectiveAllocate.this.spnLACritsStateChanged(evt);
/*     */       }
/* 320 */     });
/* 321 */     this.pnlLA.add(this.spnLACrits, "First");
/*     */     
/* 323 */     gridBagConstraints = new GridBagConstraints();
/* 324 */     gridBagConstraints.gridx = 0;
/* 325 */     gridBagConstraints.gridy = 2;
/* 326 */     getContentPane().add(this.pnlLA, gridBagConstraints);
/*     */     
/* 328 */     this.pnlRA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RA", 2, 0));
/* 329 */     this.pnlRA.setLayout(new BorderLayout());
/*     */     
/* 331 */     this.lblRAQuant.setHorizontalAlignment(0);
/* 332 */     this.lblRAQuant.setText("00 of 00");
/* 333 */     this.pnlRA.add(this.lblRAQuant, "Center");
/*     */     
/* 335 */     this.spnRACrits.setMaximumSize(new Dimension(50, 25));
/* 336 */     this.spnRACrits.setMinimumSize(new Dimension(50, 25));
/* 337 */     this.spnRACrits.setPreferredSize(new Dimension(50, 25));
/* 338 */     this.spnRACrits.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 340 */         dlgSelectiveAllocate.this.spnRACritsStateChanged(evt);
/*     */       }
/* 342 */     });
/* 343 */     this.pnlRA.add(this.spnRACrits, "First");
/*     */     
/* 345 */     gridBagConstraints = new GridBagConstraints();
/* 346 */     gridBagConstraints.gridx = 4;
/* 347 */     gridBagConstraints.gridy = 2;
/* 348 */     getContentPane().add(this.pnlRA, gridBagConstraints);
/*     */     
/* 350 */     this.pnlLL.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LL", 2, 0));
/* 351 */     this.pnlLL.setLayout(new BorderLayout());
/*     */     
/* 353 */     this.lblLLQuant.setHorizontalAlignment(4);
/* 354 */     this.lblLLQuant.setText("00 of 00");
/* 355 */     this.lblLLQuant.setHorizontalTextPosition(0);
/* 356 */     this.pnlLL.add(this.lblLLQuant, "Center");
/*     */     
/* 358 */     this.spnLLCrits.setMaximumSize(new Dimension(50, 25));
/* 359 */     this.spnLLCrits.setMinimumSize(new Dimension(50, 25));
/* 360 */     this.spnLLCrits.setPreferredSize(new Dimension(50, 25));
/* 361 */     this.spnLLCrits.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 363 */         dlgSelectiveAllocate.this.spnLLCritsStateChanged(evt);
/*     */       }
/* 365 */     });
/* 366 */     this.pnlLL.add(this.spnLLCrits, "First");
/*     */     
/* 368 */     gridBagConstraints = new GridBagConstraints();
/* 369 */     gridBagConstraints.gridx = 1;
/* 370 */     gridBagConstraints.gridy = 3;
/* 371 */     getContentPane().add(this.pnlLL, gridBagConstraints);
/*     */     
/* 373 */     this.pnlRL.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RL", 2, 0));
/* 374 */     this.pnlRL.setLayout(new BorderLayout());
/*     */     
/* 376 */     this.lblRLQuant.setHorizontalAlignment(0);
/* 377 */     this.lblRLQuant.setText("00 of 00");
/* 378 */     this.pnlRL.add(this.lblRLQuant, "Center");
/*     */     
/* 380 */     this.spnRLCrits.setMaximumSize(new Dimension(50, 25));
/* 381 */     this.spnRLCrits.setMinimumSize(new Dimension(50, 25));
/* 382 */     this.spnRLCrits.setPreferredSize(new Dimension(50, 25));
/* 383 */     this.spnRLCrits.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent evt) {
/* 385 */         dlgSelectiveAllocate.this.spnRLCritsStateChanged(evt);
/*     */       }
/* 387 */     });
/* 388 */     this.pnlRL.add(this.spnRLCrits, "First");
/*     */     
/* 390 */     gridBagConstraints = new GridBagConstraints();
/* 391 */     gridBagConstraints.gridx = 3;
/* 392 */     gridBagConstraints.gridy = 3;
/* 393 */     getContentPane().add(this.pnlRL, gridBagConstraints);
/*     */     
/* 395 */     this.btnOkay.setText("Okay");
/* 396 */     this.btnOkay.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 398 */         dlgSelectiveAllocate.this.btnOkayActionPerformed(evt);
/*     */       }
/* 400 */     });
/* 401 */     this.jPanel1.add(this.btnOkay);
/*     */     
/* 403 */     this.btnCancel.setText("Cancel");
/* 404 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 406 */         dlgSelectiveAllocate.this.btnCancelActionPerformed(evt);
/*     */       }
/* 408 */     });
/* 409 */     this.jPanel1.add(this.btnCancel);
/*     */     
/* 411 */     gridBagConstraints = new GridBagConstraints();
/* 412 */     gridBagConstraints.gridx = 0;
/* 413 */     gridBagConstraints.gridy = 5;
/* 414 */     gridBagConstraints.gridwidth = 0;
/* 415 */     gridBagConstraints.anchor = 13;
/* 416 */     getContentPane().add(this.jPanel1, gridBagConstraints);
/*     */     
/* 418 */     this.lblItemCrits.setText("00 of 00 Allocated");
/* 419 */     gridBagConstraints = new GridBagConstraints();
/* 420 */     gridBagConstraints.gridx = 0;
/* 421 */     gridBagConstraints.gridy = 4;
/* 422 */     gridBagConstraints.gridwidth = 0;
/* 423 */     gridBagConstraints.insets = new Insets(8, 0, 8, 0);
/* 424 */     getContentPane().add(this.lblItemCrits, gridBagConstraints);
/*     */     
/* 426 */     this.lblAllocateItem.setText("Allocating Endo-Steel");
/* 427 */     gridBagConstraints = new GridBagConstraints();
/* 428 */     gridBagConstraints.gridx = 0;
/* 429 */     gridBagConstraints.gridy = 0;
/* 430 */     gridBagConstraints.gridwidth = 0;
/* 431 */     gridBagConstraints.insets = new Insets(0, 0, 8, 0);
/* 432 */     getContentPane().add(this.lblAllocateItem, gridBagConstraints);
/*     */     
/* 434 */     pack(); }
/*     */   
/*     */   private JPanel pnlLT;
/*     */   private JPanel pnlRA;
/*     */   
/* 439 */   private void btnCancelActionPerformed(ActionEvent evt) { dispose(); }
/*     */   
/*     */   private JPanel pnlRL;
/*     */   private JPanel pnlRT;
/*     */   
/*     */   private void btnOkayActionPerformed(ActionEvent evt) {
/* 445 */     try { for (int i = 0; i < this.Alloc[0]; i++) {
/* 446 */         if (this.Item == null) {
/* 447 */           this.CurLoadout.AddToHD(this.Items.GetType());
/*     */         } else {
/* 449 */           this.CurLoadout.AddToHD(this.Item);
/*     */         }
/*     */       }
/* 452 */       for (int i = 0; i < this.Alloc[1]; i++) {
/* 453 */         if (this.Item == null) {
/* 454 */           this.CurLoadout.AddToCT(this.Items.GetType());
/*     */         } else {
/* 456 */           this.CurLoadout.AddToCT(this.Item);
/*     */         }
/*     */       }
/* 459 */       for (int i = 0; i < this.Alloc[2]; i++) {
/* 460 */         if (this.Item == null) {
/* 461 */           this.CurLoadout.AddToLT(this.Items.GetType());
/*     */         } else {
/* 463 */           this.CurLoadout.AddToLT(this.Item);
/*     */         }
/*     */       }
/* 466 */       for (int i = 0; i < this.Alloc[3]; i++) {
/* 467 */         if (this.Item == null) {
/* 468 */           this.CurLoadout.AddToRT(this.Items.GetType());
/*     */         } else {
/* 470 */           this.CurLoadout.AddToRT(this.Item);
/*     */         }
/*     */       }
/* 473 */       for (int i = 0; i < this.Alloc[4]; i++) {
/* 474 */         if (this.Item == null) {
/* 475 */           this.CurLoadout.AddToLA(this.Items.GetType());
/*     */         } else {
/* 477 */           this.CurLoadout.AddToLA(this.Item);
/*     */         }
/*     */       }
/* 480 */       for (int i = 0; i < this.Alloc[5]; i++) {
/* 481 */         if (this.Item == null) {
/* 482 */           this.CurLoadout.AddToRA(this.Items.GetType());
/*     */         } else {
/* 484 */           this.CurLoadout.AddToRA(this.Item);
/*     */         }
/*     */       }
/* 487 */       for (int i = 0; i < this.Alloc[6]; i++) {
/* 488 */         if (this.Item == null) {
/* 489 */           this.CurLoadout.AddToLL(this.Items.GetType());
/*     */         } else {
/* 491 */           this.CurLoadout.AddToLL(this.Item);
/*     */         }
/*     */       }
/* 494 */       for (int i = 0; i < this.Alloc[7]; i++) {
/* 495 */         if (this.Item == null) {
/* 496 */           this.CurLoadout.AddToRL(this.Items.GetType());
/*     */         } else {
/* 498 */           this.CurLoadout.AddToRL(this.Item);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*     */ 
/* 505 */     dispose(); }
/*     */   
/*     */   private JSpinner spnCTCrits;
/*     */   private JSpinner spnHDCrits;
/*     */   
/* 510 */   private void spnHDCritsStateChanged(ChangeEvent evt) { SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDCrits.getModel();
/* 511 */     JComponent editor = this.spnHDCrits.getEditor();
/* 512 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 516 */       this.spnHDCrits.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 520 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 521 */         tf.setValue(this.spnHDCrits.getValue());
/*     */       }
/* 523 */       return;
/*     */     }
/*     */     
/* 526 */     int change = 0;
/* 527 */     if (this.Item == null) {
/* 528 */       change = n.getNumber().intValue() - this.Alloc[0];
/* 529 */       if (change + this.total > this.Items.GetSize()) {
/* 530 */         int diff = change + this.total - this.Items.GetSize();
/* 531 */         change -= diff;
/* 532 */         this.spnHDCrits.setValue(Integer.valueOf(change + this.Alloc[0]));
/* 533 */         tf.setValue(this.spnHDCrits.getValue());
/*     */       }
/*     */     }
/*     */     else {
/* 537 */       change = n.getNumber().intValue() - this.Alloc[0];
/* 538 */       if (change + this.total > this.Item.NumCrits()) {
/* 539 */         int diff = change + this.total - this.Item.NumCrits();
/* 540 */         change -= diff;
/* 541 */         this.spnHDCrits.setValue(Integer.valueOf(change + this.Alloc[0]));
/* 542 */         tf.setValue(this.spnHDCrits.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 547 */     this.Alloc[0] += change;
/* 548 */     this.total += change;
/*     */     
/*     */ 
/* 551 */     this.lblHDQuant.setText(this.Alloc[0] + " of " + this.Crits[0]);
/* 552 */     if (this.Item == null) {
/* 553 */       this.lblItemCrits.setText(this.total + " of " + this.Items.GetSize() + " Allocated");
/*     */     } else
/* 555 */       this.lblItemCrits.setText(this.total + " of " + this.Item.NumCrits() + " Allocated"); }
/*     */   
/*     */   private JSpinner spnLACrits;
/*     */   private JSpinner spnLLCrits;
/*     */   private JSpinner spnLTCrits;
/*     */   
/* 561 */   private void spnRTCritsStateChanged(ChangeEvent evt) { SpinnerNumberModel n = (SpinnerNumberModel)this.spnRTCrits.getModel();
/* 562 */     JComponent editor = this.spnRTCrits.getEditor();
/* 563 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 567 */       this.spnRTCrits.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 571 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 572 */         tf.setValue(this.spnRTCrits.getValue());
/*     */       }
/* 574 */       return;
/*     */     }
/*     */     
/* 577 */     int change = 0;
/* 578 */     if (this.Item == null) {
/* 579 */       change = n.getNumber().intValue() - this.Alloc[3];
/* 580 */       if (change + this.total > this.Items.GetSize()) {
/* 581 */         int diff = change + this.total - this.Items.GetSize();
/* 582 */         change -= diff;
/* 583 */         this.spnRTCrits.setValue(Integer.valueOf(change + this.Alloc[3]));
/* 584 */         tf.setValue(this.spnRTCrits.getValue());
/*     */       }
/*     */     }
/*     */     else {
/* 588 */       change = n.getNumber().intValue() - this.Alloc[3];
/* 589 */       if (change + this.total > this.Item.NumCrits()) {
/* 590 */         int diff = change + this.total - this.Item.NumCrits();
/* 591 */         change -= diff;
/* 592 */         this.spnRTCrits.setValue(Integer.valueOf(change + this.Alloc[3]));
/* 593 */         tf.setValue(this.spnRTCrits.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 598 */     this.Alloc[3] += change;
/* 599 */     this.total += change;
/*     */     
/*     */ 
/* 602 */     this.lblRTQuant.setText(this.Alloc[3] + " of " + this.Crits[3]);
/* 603 */     if (this.Item == null) {
/* 604 */       this.lblItemCrits.setText(this.total + " of " + this.Items.GetSize() + " Allocated");
/*     */     } else
/* 606 */       this.lblItemCrits.setText(this.total + " of " + this.Item.NumCrits() + " Allocated");
/*     */   }
/*     */   
/*     */   private JSpinner spnRACrits;
/*     */   private JSpinner spnRLCrits;
/*     */   private JSpinner spnRTCrits;
/* 612 */   private void spnLTCritsStateChanged(ChangeEvent evt) { SpinnerNumberModel n = (SpinnerNumberModel)this.spnLTCrits.getModel();
/* 613 */     JComponent editor = this.spnLTCrits.getEditor();
/* 614 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 618 */       this.spnLTCrits.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 622 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 623 */         tf.setValue(this.spnLTCrits.getValue());
/*     */       }
/* 625 */       return;
/*     */     }
/*     */     
/* 628 */     int change = 0;
/* 629 */     if (this.Item == null) {
/* 630 */       change = n.getNumber().intValue() - this.Alloc[2];
/* 631 */       if (change + this.total > this.Items.GetSize()) {
/* 632 */         int diff = change + this.total - this.Items.GetSize();
/* 633 */         change -= diff;
/* 634 */         this.spnLTCrits.setValue(Integer.valueOf(change + this.Alloc[2]));
/* 635 */         tf.setValue(this.spnLTCrits.getValue());
/*     */       }
/*     */     }
/*     */     else {
/* 639 */       change = n.getNumber().intValue() - this.Alloc[2];
/* 640 */       if (change + this.total > this.Item.NumCrits()) {
/* 641 */         int diff = change + this.total - this.Item.NumCrits();
/* 642 */         change -= diff;
/* 643 */         this.spnLTCrits.setValue(Integer.valueOf(change + this.Alloc[2]));
/* 644 */         tf.setValue(this.spnLTCrits.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 649 */     this.Alloc[2] += change;
/* 650 */     this.total += change;
/*     */     
/*     */ 
/* 653 */     this.lblLTQuant.setText(this.Alloc[2] + " of " + this.Crits[2]);
/* 654 */     if (this.Item == null) {
/* 655 */       this.lblItemCrits.setText(this.total + " of " + this.Items.GetSize() + " Allocated");
/*     */     } else {
/* 657 */       this.lblItemCrits.setText(this.total + " of " + this.Item.NumCrits() + " Allocated");
/*     */     }
/*     */   }
/*     */   
/*     */   private void spnCTCritsStateChanged(ChangeEvent evt)
/*     */   {
/* 663 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnCTCrits.getModel();
/* 664 */     JComponent editor = this.spnCTCrits.getEditor();
/* 665 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 669 */       this.spnCTCrits.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 673 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 674 */         tf.setValue(this.spnCTCrits.getValue());
/*     */       }
/* 676 */       return;
/*     */     }
/*     */     
/* 679 */     int change = 0;
/* 680 */     if (this.Item == null) {
/* 681 */       change = n.getNumber().intValue() - this.Alloc[1];
/* 682 */       if (change + this.total > this.Items.GetSize()) {
/* 683 */         int diff = change + this.total - this.Items.GetSize();
/* 684 */         change -= diff;
/* 685 */         this.spnCTCrits.setValue(Integer.valueOf(change + this.Alloc[1]));
/* 686 */         tf.setValue(this.spnCTCrits.getValue());
/*     */       }
/*     */     }
/*     */     else {
/* 690 */       change = n.getNumber().intValue() - this.Alloc[1];
/* 691 */       if (change + this.total > this.Item.NumCrits()) {
/* 692 */         int diff = change + this.total - this.Item.NumCrits();
/* 693 */         change -= diff;
/* 694 */         this.spnCTCrits.setValue(Integer.valueOf(change + this.Alloc[1]));
/* 695 */         tf.setValue(this.spnCTCrits.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 700 */     this.Alloc[1] += change;
/* 701 */     this.total += change;
/*     */     
/*     */ 
/* 704 */     this.lblCTQuant.setText(this.Alloc[1] + " of " + this.Crits[1]);
/* 705 */     if (this.Item == null) {
/* 706 */       this.lblItemCrits.setText(this.total + " of " + this.Items.GetSize() + " Allocated");
/*     */     } else {
/* 708 */       this.lblItemCrits.setText(this.total + " of " + this.Item.NumCrits() + " Allocated");
/*     */     }
/*     */   }
/*     */   
/*     */   private void spnLACritsStateChanged(ChangeEvent evt)
/*     */   {
/* 714 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLACrits.getModel();
/* 715 */     JComponent editor = this.spnLACrits.getEditor();
/* 716 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 720 */       this.spnLACrits.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 724 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 725 */         tf.setValue(this.spnLACrits.getValue());
/*     */       }
/* 727 */       return;
/*     */     }
/*     */     
/* 730 */     int change = 0;
/* 731 */     if (this.Item == null) {
/* 732 */       change = n.getNumber().intValue() - this.Alloc[4];
/* 733 */       if (change + this.total > this.Items.GetSize()) {
/* 734 */         int diff = change + this.total - this.Items.GetSize();
/* 735 */         change -= diff;
/* 736 */         this.spnLACrits.setValue(Integer.valueOf(change + this.Alloc[4]));
/* 737 */         tf.setValue(this.spnLACrits.getValue());
/*     */       }
/*     */     }
/*     */     else {
/* 741 */       change = n.getNumber().intValue() - this.Alloc[4];
/* 742 */       if (change + this.total > this.Item.NumCrits()) {
/* 743 */         int diff = change + this.total - this.Item.NumCrits();
/* 744 */         change -= diff;
/* 745 */         this.spnLACrits.setValue(Integer.valueOf(change + this.Alloc[4]));
/* 746 */         tf.setValue(this.spnLACrits.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 751 */     this.Alloc[4] += change;
/* 752 */     this.total += change;
/*     */     
/*     */ 
/* 755 */     this.lblLAQuant.setText(this.Alloc[4] + " of " + this.Crits[4]);
/* 756 */     if (this.Item == null) {
/* 757 */       this.lblItemCrits.setText(this.total + " of " + this.Items.GetSize() + " Allocated");
/*     */     } else {
/* 759 */       this.lblItemCrits.setText(this.total + " of " + this.Item.NumCrits() + " Allocated");
/*     */     }
/*     */   }
/*     */   
/*     */   private void spnRACritsStateChanged(ChangeEvent evt)
/*     */   {
/* 765 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRACrits.getModel();
/* 766 */     JComponent editor = this.spnRACrits.getEditor();
/* 767 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 771 */       this.spnRACrits.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 775 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 776 */         tf.setValue(this.spnRACrits.getValue());
/*     */       }
/* 778 */       return;
/*     */     }
/*     */     
/* 781 */     int change = 0;
/* 782 */     if (this.Item == null) {
/* 783 */       change = n.getNumber().intValue() - this.Alloc[5];
/* 784 */       if (change + this.total > this.Items.GetSize()) {
/* 785 */         int diff = change + this.total - this.Items.GetSize();
/* 786 */         change -= diff;
/* 787 */         this.spnRACrits.setValue(Integer.valueOf(change + this.Alloc[5]));
/* 788 */         tf.setValue(this.spnRACrits.getValue());
/*     */       }
/*     */     }
/*     */     else {
/* 792 */       change = n.getNumber().intValue() - this.Alloc[5];
/* 793 */       if (change + this.total > this.Item.NumCrits()) {
/* 794 */         int diff = change + this.total - this.Item.NumCrits();
/* 795 */         change -= diff;
/* 796 */         this.spnRACrits.setValue(Integer.valueOf(change + this.Alloc[5]));
/* 797 */         tf.setValue(this.spnRACrits.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 802 */     this.Alloc[5] += change;
/* 803 */     this.total += change;
/*     */     
/*     */ 
/* 806 */     this.lblRAQuant.setText(this.Alloc[5] + " of " + this.Crits[5]);
/* 807 */     if (this.Item == null) {
/* 808 */       this.lblItemCrits.setText(this.total + " of " + this.Items.GetSize() + " Allocated");
/*     */     } else {
/* 810 */       this.lblItemCrits.setText(this.total + " of " + this.Item.NumCrits() + " Allocated");
/*     */     }
/*     */   }
/*     */   
/*     */   private void spnLLCritsStateChanged(ChangeEvent evt)
/*     */   {
/* 816 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLLCrits.getModel();
/* 817 */     JComponent editor = this.spnLLCrits.getEditor();
/* 818 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 822 */       this.spnLLCrits.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 826 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 827 */         tf.setValue(this.spnLLCrits.getValue());
/*     */       }
/* 829 */       return;
/*     */     }
/*     */     
/* 832 */     int change = 0;
/* 833 */     if (this.Item == null) {
/* 834 */       change = n.getNumber().intValue() - this.Alloc[6];
/* 835 */       if (change + this.total > this.Items.GetSize()) {
/* 836 */         int diff = change + this.total - this.Items.GetSize();
/* 837 */         change -= diff;
/* 838 */         this.spnLLCrits.setValue(Integer.valueOf(change + this.Alloc[6]));
/* 839 */         tf.setValue(this.spnLLCrits.getValue());
/*     */       }
/*     */     }
/*     */     else {
/* 843 */       change = n.getNumber().intValue() - this.Alloc[6];
/* 844 */       if (change + this.total > this.Item.NumCrits()) {
/* 845 */         int diff = change + this.total - this.Item.NumCrits();
/* 846 */         change -= diff;
/* 847 */         this.spnLLCrits.setValue(Integer.valueOf(change + this.Alloc[6]));
/* 848 */         tf.setValue(this.spnLLCrits.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 853 */     this.Alloc[6] += change;
/* 854 */     this.total += change;
/*     */     
/*     */ 
/* 857 */     this.lblLLQuant.setText(this.Alloc[6] + " of " + this.Crits[6]);
/* 858 */     if (this.Item == null) {
/* 859 */       this.lblItemCrits.setText(this.total + " of " + this.Items.GetSize() + " Allocated");
/*     */     } else {
/* 861 */       this.lblItemCrits.setText(this.total + " of " + this.Item.NumCrits() + " Allocated");
/*     */     }
/*     */   }
/*     */   
/*     */   private void spnRLCritsStateChanged(ChangeEvent evt)
/*     */   {
/* 867 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRLCrits.getModel();
/* 868 */     JComponent editor = this.spnRLCrits.getEditor();
/* 869 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*     */     
/*     */     try
/*     */     {
/* 873 */       this.spnRLCrits.commitEdit();
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 877 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 878 */         tf.setValue(this.spnRLCrits.getValue());
/*     */       }
/* 880 */       return;
/*     */     }
/*     */     
/* 883 */     int change = 0;
/* 884 */     if (this.Item == null) {
/* 885 */       change = n.getNumber().intValue() - this.Alloc[7];
/* 886 */       if (change + this.total > this.Items.GetSize()) {
/* 887 */         int diff = change + this.total - this.Items.GetSize();
/* 888 */         change -= diff;
/* 889 */         this.spnRLCrits.setValue(Integer.valueOf(change + this.Alloc[7]));
/* 890 */         tf.setValue(this.spnRLCrits.getValue());
/*     */       }
/*     */     }
/*     */     else {
/* 894 */       change = n.getNumber().intValue() - this.Alloc[7];
/* 895 */       if (change + this.total > this.Item.NumCrits()) {
/* 896 */         int diff = change + this.total - this.Item.NumCrits();
/* 897 */         change -= diff;
/* 898 */         this.spnRLCrits.setValue(Integer.valueOf(change + this.Alloc[7]));
/* 899 */         tf.setValue(this.spnRLCrits.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 904 */     this.Alloc[7] += change;
/* 905 */     this.total += change;
/*     */     
/*     */ 
/* 908 */     this.lblRLQuant.setText(this.Alloc[7] + " of " + this.Crits[7]);
/* 909 */     if (this.Item == null) {
/* 910 */       this.lblItemCrits.setText(this.total + " of " + this.Items.GetSize() + " Allocated");
/*     */     } else {
/* 912 */       this.lblItemCrits.setText(this.total + " of " + this.Item.NumCrits() + " Allocated");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgSelectiveAllocate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */