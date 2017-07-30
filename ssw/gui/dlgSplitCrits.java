/*     */ package ssw.gui;
/*     */ 
/*     */ import components.ActuatorSet;
/*     */ import components.Mech;
/*     */ import components.abPlaceable;
/*     */ import components.ifMechLoadout;
/*     */ import components.ifWeapon;
/*     */ import filehandlers.Media;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgSplitCrits
/*     */   extends JDialog
/*     */ {
/*  40 */   int FirstLoc = 0; int CritsPlaced = 0; int FirstIndex = 0;
/*     */   
/*     */ 
/*  43 */   boolean result = false;
/*     */   abPlaceable ItemToPlace;
/*     */   ifMechForm Parent;
/*     */   
/*  47 */   public dlgSplitCrits(Frame parent, boolean modal, abPlaceable p, int first, int findex) throws Exception { super(parent, modal);
/*  48 */     this.Parent = ((ifMechForm)parent);
/*  49 */     this.ItemToPlace = p;
/*  50 */     initComponents();
/*  51 */     setTitle("Split Criticals");
/*  52 */     setResizable(false);
/*  53 */     this.lblItemName.setText("Allocating " + p.CritName());
/*  54 */     this.FirstLoc = first;
/*  55 */     this.FirstIndex = findex;
/*     */     
/*     */ 
/*  58 */     switch (this.FirstLoc) {
/*     */     case 0: 
/*  60 */       if (!p.CanAllocHD()) {
/*  61 */         throw new Exception(p.CritName() + " cannot be allocated to the head.");
/*     */       }
/*     */       break;
/*     */     case 1: 
/*  65 */       if (!p.CanAllocCT()) {
/*  66 */         throw new Exception(p.CritName() + " cannot be allocated to the center torso.");
/*     */       }
/*     */       break;
/*     */     case 2: case 3: 
/*  70 */       if (!p.CanAllocTorso()) {
/*  71 */         throw new Exception(p.CritName() + " cannot be allocated to a side torso.");
/*     */       }
/*     */       break;
/*     */     case 4: case 5: 
/*  75 */       if (!p.CanAllocArms()) {
/*  76 */         throw new Exception(p.CritName() + " cannot be allocated to the arms.");
/*     */       }
/*     */       break;
/*     */     case 6: case 7: 
/*  80 */       if (!p.CanAllocLegs()) {
/*  81 */         throw new Exception(p.CritName() + " cannot be allocated to the legs.");
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*     */     
/*     */     
/*  88 */     switch (this.FirstLoc) {
/*     */     case 1: 
/*  90 */       this.lblFirstLoc.setText("Center Torso");
/*  91 */       this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Left Torso", "Right Torso" }));
/*  92 */       break;
/*     */     case 2: 
/*  94 */       this.lblFirstLoc.setText("Left Torso");
/*  95 */       if (((!p.CanAllocArms() ? 1 : 0) & (!p.CanAllocLegs() ? 1 : 0)) != 0) {
/*  96 */         this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Center Torso" }));
/*     */       }
/*  98 */       else if ((p instanceof ifWeapon)) {
/*  99 */         if ((((ifWeapon)p).OmniRestrictActuators()) && (this.Parent.GetMech().GetActuators().LeftLowerInstalled()) && (this.Parent.GetMech().IsOmnimech())) {
/* 100 */           this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Center Torso", "Left Leg" }));
/*     */         } else {
/* 102 */           this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Center Torso", "Left Leg", "Left Arm" }));
/*     */         }
/*     */       } else {
/* 105 */         this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Center Torso", "Left Leg", "Left Arm" }));
/*     */       }
/*     */       
/* 108 */       break;
/*     */     case 3: 
/* 110 */       this.lblFirstLoc.setText("Right Torso");
/* 111 */       if (((!p.CanAllocArms() ? 1 : 0) & (!p.CanAllocLegs() ? 1 : 0)) != 0) {
/* 112 */         this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Center Torso" }));
/*     */       }
/* 114 */       else if ((p instanceof ifWeapon)) {
/* 115 */         if ((((ifWeapon)p).OmniRestrictActuators()) && (this.Parent.GetMech().GetActuators().RightLowerInstalled()) && (this.Parent.GetMech().IsOmnimech())) {
/* 116 */           this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Center Torso", "Right Leg" }));
/*     */         } else {
/* 118 */           this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Center Torso", "Right Leg", "Right Arm" }));
/*     */         }
/*     */       } else {
/* 121 */         this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Center Torso", "Right Leg", "Right Arm" }));
/*     */       }
/*     */       
/* 124 */       break;
/*     */     case 4: 
/* 126 */       this.lblFirstLoc.setText("Left Arm");
/* 127 */       this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Left Torso" }));
/* 128 */       break;
/*     */     case 5: 
/* 130 */       this.lblFirstLoc.setText("Right Arm");
/* 131 */       this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Right Torso" }));
/* 132 */       break;
/*     */     case 6: 
/* 134 */       this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Left Torso" }));
/* 135 */       this.lblFirstLoc.setText("Left Leg");
/* 136 */       break;
/*     */     case 7: 
/* 138 */       this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Right Torso" }));
/* 139 */       this.lblFirstLoc.setText("Right Leg");
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/* 145 */   public boolean GetResult() { return this.result; }
/*     */   
/*     */   private JButton btnCancel;
/*     */   private JButton btnOkay;
/*     */   private JComboBox cmbSecondLoc;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel lblFirstLoc;
/*     */   private JLabel lblItemName;
/*     */   private void initComponents() {
/* 157 */     this.cmbSecondLoc = new JComboBox();
/* 158 */     this.jLabel1 = new JLabel();
/* 159 */     this.jLabel2 = new JLabel();
/* 160 */     this.lblFirstLoc = new JLabel();
/* 161 */     this.btnOkay = new JButton();
/* 162 */     this.btnCancel = new JButton();
/* 163 */     this.jLabel3 = new JLabel();
/* 164 */     this.jLabel4 = new JLabel();
/* 165 */     this.lblItemName = new JLabel();
/*     */     
/* 167 */     setDefaultCloseOperation(2);
/* 168 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/* 170 */     this.cmbSecondLoc.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/* 171 */     this.cmbSecondLoc.setMaximumSize(new Dimension(100, 20));
/* 172 */     this.cmbSecondLoc.setMinimumSize(new Dimension(100, 20));
/* 173 */     this.cmbSecondLoc.setPreferredSize(new Dimension(100, 20));
/* 174 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 175 */     gridBagConstraints.gridx = 1;
/* 176 */     gridBagConstraints.gridy = 4;
/* 177 */     gridBagConstraints.anchor = 17;
/* 178 */     gridBagConstraints.insets = new Insets(4, 8, 0, 0);
/* 179 */     getContentPane().add(this.cmbSecondLoc, gridBagConstraints);
/*     */     
/* 181 */     this.jLabel1.setText("First Location");
/* 182 */     gridBagConstraints = new GridBagConstraints();
/* 183 */     gridBagConstraints.gridx = 0;
/* 184 */     gridBagConstraints.gridy = 3;
/* 185 */     gridBagConstraints.anchor = 13;
/* 186 */     getContentPane().add(this.jLabel1, gridBagConstraints);
/*     */     
/* 188 */     this.jLabel2.setText("Second Location");
/* 189 */     gridBagConstraints = new GridBagConstraints();
/* 190 */     gridBagConstraints.gridx = 0;
/* 191 */     gridBagConstraints.gridy = 4;
/* 192 */     gridBagConstraints.anchor = 13;
/* 193 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 194 */     getContentPane().add(this.jLabel2, gridBagConstraints);
/*     */     
/* 196 */     this.lblFirstLoc.setText("<First>");
/* 197 */     gridBagConstraints = new GridBagConstraints();
/* 198 */     gridBagConstraints.gridx = 1;
/* 199 */     gridBagConstraints.gridy = 3;
/* 200 */     gridBagConstraints.anchor = 17;
/* 201 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/* 202 */     getContentPane().add(this.lblFirstLoc, gridBagConstraints);
/*     */     
/* 204 */     this.btnOkay.setText("Okay");
/* 205 */     this.btnOkay.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 207 */         dlgSplitCrits.this.btnOkayActionPerformed(evt);
/*     */       }
/* 209 */     });
/* 210 */     gridBagConstraints = new GridBagConstraints();
/* 211 */     gridBagConstraints.gridx = 0;
/* 212 */     gridBagConstraints.gridy = 5;
/* 213 */     gridBagConstraints.anchor = 13;
/* 214 */     gridBagConstraints.insets = new Insets(8, 0, 0, 8);
/* 215 */     getContentPane().add(this.btnOkay, gridBagConstraints);
/*     */     
/* 217 */     this.btnCancel.setText("Cancel");
/* 218 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 220 */         dlgSplitCrits.this.btnCancelActionPerformed(evt);
/*     */       }
/* 222 */     });
/* 223 */     gridBagConstraints = new GridBagConstraints();
/* 224 */     gridBagConstraints.gridx = 1;
/* 225 */     gridBagConstraints.gridy = 5;
/* 226 */     gridBagConstraints.anchor = 17;
/* 227 */     gridBagConstraints.insets = new Insets(8, 0, 0, 0);
/* 228 */     getContentPane().add(this.btnCancel, gridBagConstraints);
/*     */     
/* 230 */     this.jLabel3.setText("Choose a location for the rest");
/* 231 */     gridBagConstraints = new GridBagConstraints();
/* 232 */     gridBagConstraints.gridx = 0;
/* 233 */     gridBagConstraints.gridy = 1;
/* 234 */     gridBagConstraints.gridwidth = 2;
/* 235 */     getContentPane().add(this.jLabel3, gridBagConstraints);
/*     */     
/* 237 */     this.jLabel4.setText("of the Item's critical slots:");
/* 238 */     gridBagConstraints = new GridBagConstraints();
/* 239 */     gridBagConstraints.gridx = 0;
/* 240 */     gridBagConstraints.gridy = 2;
/* 241 */     gridBagConstraints.gridwidth = 2;
/* 242 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/* 243 */     getContentPane().add(this.jLabel4, gridBagConstraints);
/*     */     
/* 245 */     this.lblItemName.setText("Allocating Ultra Autocannon/20");
/* 246 */     gridBagConstraints = new GridBagConstraints();
/* 247 */     gridBagConstraints.gridwidth = 2;
/* 248 */     gridBagConstraints.insets = new Insets(0, 0, 8, 0);
/* 249 */     getContentPane().add(this.lblItemName, gridBagConstraints);
/*     */     
/* 251 */     pack();
/*     */   }
/*     */   
/*     */   private void btnOkayActionPerformed(ActionEvent evt)
/*     */   {
/* 256 */     switch (this.FirstLoc) {
/*     */     case 1: 
/* 258 */       switch (this.cmbSecondLoc.getSelectedIndex()) {
/*     */       case 0: 
/*     */         try {
/* 261 */           this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 2);
/*     */         } catch (Exception e) {
/* 263 */           Media.Messager(this, e.getMessage());
/* 264 */           this.result = false;
/* 265 */           setVisible(false);
/* 266 */           return;
/*     */         }
/*     */       case 1: 
/*     */         try
/*     */         {
/* 271 */           this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 3);
/*     */         } catch (Exception e) {
/* 273 */           Media.Messager(this, e.getMessage());
/* 274 */           this.result = false;
/* 275 */           setVisible(false);
/* 276 */           return;
/*     */         }
/*     */       }
/*     */       
/* 280 */       break;
/*     */     case 2: 
/* 282 */       switch (this.cmbSecondLoc.getSelectedIndex()) {
/*     */       case 0: 
/*     */         try {
/* 285 */           this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 1);
/*     */         } catch (Exception e) {
/* 287 */           Media.Messager(this, e.getMessage());
/* 288 */           this.result = false;
/* 289 */           setVisible(false);
/* 290 */           return;
/*     */         }
/*     */       case 1: 
/*     */         try
/*     */         {
/* 295 */           this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 6);
/*     */         } catch (Exception e) {
/* 297 */           Media.Messager(this, e.getMessage());
/* 298 */           this.result = false;
/* 299 */           setVisible(false);
/* 300 */           return;
/*     */         }
/*     */       case 2: 
/*     */         try
/*     */         {
/* 305 */           this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 4);
/*     */         } catch (Exception e) {
/* 307 */           Media.Messager(this, e.getMessage());
/* 308 */           this.result = false;
/* 309 */           setVisible(false);
/* 310 */           return;
/*     */         }
/*     */       }
/*     */       
/* 314 */       break;
/*     */     case 3: 
/* 316 */       switch (this.cmbSecondLoc.getSelectedIndex()) {
/*     */       case 0: 
/*     */         try {
/* 319 */           this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 1);
/*     */         } catch (Exception e) {
/* 321 */           Media.Messager(this, e.getMessage());
/* 322 */           this.result = false;
/* 323 */           setVisible(false);
/* 324 */           return;
/*     */         }
/*     */       case 1: 
/*     */         try
/*     */         {
/* 329 */           this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 7);
/*     */         } catch (Exception e) {
/* 331 */           Media.Messager(this, e.getMessage());
/* 332 */           this.result = false;
/* 333 */           setVisible(false);
/* 334 */           return;
/*     */         }
/*     */       case 2: 
/*     */         try
/*     */         {
/* 339 */           this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 5);
/*     */         } catch (Exception e) {
/* 341 */           Media.Messager(this, e.getMessage());
/* 342 */           this.result = false;
/* 343 */           setVisible(false);
/* 344 */           return;
/*     */         }
/*     */       }
/*     */       
/* 348 */       break;
/*     */     case 4: 
/*     */       try
/*     */       {
/* 352 */         this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 2);
/*     */       } catch (Exception e) {
/* 354 */         Media.Messager(this, e.getMessage());
/* 355 */         this.result = false;
/* 356 */         setVisible(false);
/* 357 */         return;
/*     */       }
/*     */     
/*     */     case 5: 
/*     */       try
/*     */       {
/* 363 */         this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 3);
/*     */       } catch (Exception e) {
/* 365 */         Media.Messager(this, e.getMessage());
/* 366 */         this.result = false;
/* 367 */         setVisible(false);
/* 368 */         return;
/*     */       }
/*     */     
/*     */     case 6: 
/*     */       try
/*     */       {
/* 374 */         this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 2);
/*     */       } catch (Exception e) {
/* 376 */         Media.Messager(this, e.getMessage());
/* 377 */         this.result = false;
/* 378 */         setVisible(false);
/* 379 */         return;
/*     */       }
/*     */     
/*     */     case 7: 
/*     */       try
/*     */       {
/* 385 */         this.Parent.GetMech().GetLoadout().SplitAllocate(this.ItemToPlace, this.FirstLoc, this.FirstIndex, 3);
/*     */       } catch (Exception e) {
/* 387 */         Media.Messager(this, e.getMessage());
/* 388 */         this.result = false;
/* 389 */         setVisible(false);
/* 390 */         return;
/*     */       }
/*     */     }
/*     */     
/* 394 */     this.result = true;
/* 395 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnCancelActionPerformed(ActionEvent evt)
/*     */   {
/* 400 */     this.result = false;
/* 401 */     setVisible(false);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgSplitCrits.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */