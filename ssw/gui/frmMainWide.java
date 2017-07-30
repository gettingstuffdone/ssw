/*       */ package ssw.gui;
/*       */ 
/*       */ import common.CommonTools;
/*       */ import common.DataFactory;
/*       */ import common.EquipmentFactory;
/*       */ import components.ActuatorSet;
/*       */ import components.Ammunition;
/*       */ import components.AvailableCode;
/*       */ import components.Equipment;
/*       */ import components.HeatSinkFactory;
/*       */ import components.InternalStructure;
/*       */ import components.JumpJetFactory;
/*       */ import components.LocationIndex;
/*       */ import components.Mech;
/*       */ import components.MechArmor;
/*       */ import components.PhysicalWeapon;
/*       */ import components.RangedWeapon;
/*       */ import components.VehicularGrenadeLauncher;
/*       */ import components.abPlaceable;
/*       */ import components.ifMechLoadout;
/*       */ import components.ifWeapon;
/*       */ import filehandlers.Media;
/*       */ import gui.TextPane;
/*       */ import java.awt.Color;
/*       */ import java.awt.Dimension;
/*       */ import java.awt.Font;
/*       */ import java.awt.GridBagConstraints;
/*       */ import java.awt.GridBagLayout;
/*       */ import java.awt.Insets;
/*       */ import java.awt.event.ActionEvent;
/*       */ import java.awt.event.ActionListener;
/*       */ import java.awt.event.MouseAdapter;
/*       */ import java.awt.event.MouseEvent;
/*       */ import java.awt.event.MouseListener;
/*       */ import java.io.File;
/*       */ import java.io.IOException;
/*       */ import java.io.PrintStream;
/*       */ import java.text.ParseException;
/*       */ import java.util.ArrayList;
/*       */ import java.util.prefs.Preferences;
/*       */ import javax.swing.BorderFactory;
/*       */ import javax.swing.DefaultComboBoxModel;
/*       */ import javax.swing.ImageIcon;
/*       */ import javax.swing.JButton;
/*       */ import javax.swing.JCheckBox;
/*       */ import javax.swing.JComboBox;
/*       */ import javax.swing.JEditorPane;
/*       */ import javax.swing.JFileChooser;
/*       */ import javax.swing.JFormattedTextField;
/*       */ import javax.swing.JLabel;
/*       */ import javax.swing.JList;
/*       */ import javax.swing.JMenu;
/*       */ import javax.swing.JMenuItem;
/*       */ import javax.swing.JPanel;
/*       */ import javax.swing.JPopupMenu;
/*       */ import javax.swing.JScrollPane;
/*       */ import javax.swing.JSeparator;
/*       */ import javax.swing.JSpinner;
/*       */ import javax.swing.JSpinner.DefaultEditor;
/*       */ import javax.swing.JTabbedPane;
/*       */ import javax.swing.JTextField;
/*       */ import javax.swing.JToolBar;
/*       */ import javax.swing.SpinnerNumberModel;
/*       */ import javax.swing.border.TitledBorder;
/*       */ import javax.swing.event.ChangeEvent;
/*       */ import javax.swing.event.ListSelectionEvent;
/*       */ import org.netbeans.lib.awtextra.AbsoluteConstraints;
/*       */ import states.ifState;
/*       */ 
/*       */ public class frmMainWide extends javax.swing.JFrame implements java.awt.datatransfer.ClipboardOwner, common.DesignForm, ifMechForm
/*       */ {
/*    72 */   String[] Selections = { "", "", "", "", "", "", "", "" };
/*       */   Mech CurMech;
/*       */   visitors.VSetArmorTonnage ArmorTons;
/*    75 */   Color RedCol = new Color(200, 0, 0); Color GreenCol = new Color(0, 40, 0);
/*       */   
/*    77 */   Object[][] Equipment = { { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null } };
/*       */   
/*       */   abPlaceable CurItem;
/*    80 */   int CurLocation = -1;
/*    81 */   JPopupMenu mnuUtilities = new JPopupMenu();
/*    82 */   JMenuItem mnuDetails = new JMenuItem("Details");
/*    83 */   JMenuItem mnuMountRear = new JMenuItem("Mount Rear");
/*    84 */   JMenuItem mnuSetVariable = new JMenuItem("Set Tonnage");
/*    85 */   JMenuItem mnuSetLotSize = new JMenuItem("Set Lot Size");
/*    86 */   JMenuItem mnuArmorComponent = new JMenuItem("Armor Component");
/*    87 */   JMenuItem mnuAddCapacitor = new JMenuItem("Add Capacitor");
/*    88 */   JMenuItem mnuAddInsulator = new JMenuItem("Add Insulator");
/*    89 */   JMenuItem mnuCaseless = new JMenuItem("Switch to Caseless");
/*    90 */   JMenuItem mnuTurret = new JMenuItem("Add to Turret");
/*    91 */   JMenuItem mnuSelective = new JMenuItem("Selective Allocate");
/*    92 */   JMenuItem mnuAuto = new JMenuItem("Auto-Allocate");
/*    93 */   JMenuItem mnuUnallocateAll = new JMenuItem("Unallocate All");
/*    94 */   JMenuItem mnuRemoveItem = new JMenuItem("Remove Item");
/*    95 */   JMenu mnuVGLArc = new JMenu();
/*    96 */   JMenuItem mnuVGLArcFore = new JMenuItem("Fore");
/*    97 */   JMenuItem mnuVGLArcForeSide = new JMenuItem("Fore-Side");
/*    98 */   JMenuItem mnuVGLArcRear = new JMenuItem("Rear");
/*    99 */   JMenuItem mnuVGLArcRearSide = new JMenuItem("Rear-Side");
/*   100 */   JMenu mnuVGLAmmo = new JMenu();
/*   101 */   JMenuItem mnuVGLAmmoFrag = new JMenuItem("Fragmentation");
/*   102 */   JMenuItem mnuVGLAmmoChaff = new JMenuItem("Chaff");
/*   103 */   JMenuItem mnuVGLAmmoIncen = new JMenuItem("Incendiary");
/*   104 */   JMenuItem mnuVGLAmmoSmoke = new JMenuItem("Smoke");
/*   105 */   JPopupMenu mnuFluff = new JPopupMenu();
/*   106 */   JMenuItem mnuFluffCut = new JMenuItem("Cut");
/*   107 */   JMenuItem mnuFluffCopy = new JMenuItem("Copy");
/*   108 */   JMenuItem mnuFluffPaste = new JMenuItem("Paste");
/*       */   
/*   110 */   TextPane Overview = new TextPane();
/*   111 */   TextPane Capabilities = new TextPane();
/*   112 */   TextPane Deployment = new TextPane();
/*   113 */   TextPane History = new TextPane();
/*   114 */   TextPane Additional = new TextPane();
/*   115 */   TextPane Variants = new TextPane();
/*   116 */   TextPane Notables = new TextPane();
/*       */   
/*       */   MechLoadoutRenderer Mechrender;
/*       */   public Preferences Prefs;
/*   120 */   boolean Load = false; boolean SetSource = true;
/*       */   
/*   122 */   private java.awt.Cursor Hourglass = new java.awt.Cursor(3);
/*   123 */   private java.awt.Cursor NormalCursor = new java.awt.Cursor(0);
/*       */   
/*       */   public DataFactory data;
/*       */   
/*   127 */   private dlgPrintBatchMechs BatchWindow = null;
/*   128 */   private filehandlers.ImageTracker imageTracker = new filehandlers.ImageTracker();
/*   129 */   public dlgOpen dOpen = new dlgOpen(this, true);
/*   130 */   public dialog.frmForce dForce = new dialog.frmForce(this, this.imageTracker);
/*       */   
/*   132 */   final int BALLISTIC = 0; final int ENERGY = 1; final int MISSILE = 2; final int PHYSICAL = 3; final int EQUIPMENT = 4; final int AMMUNITION = 6; final int SELECTED = 7; final int ARTILLERY = 5;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   140 */   private final AvailableCode PPCCapAC = new AvailableCode(0);
/*   141 */   private final AvailableCode LIAC = new AvailableCode(2);
/*   142 */   private final AvailableCode CaselessAmmoAC = new AvailableCode(0);
/*   143 */   private components.PartialWing wing = new components.PartialWing(this.CurMech);
/*   144 */   private final AvailableCode PWAC = this.wing.GetAvailability();
/*       */   private JButton btnAddEquip;
/*       */   private JButton btnAddToForceList;
/*       */   
/*       */   public frmMainWide() {
/*   149 */     this.Prefs = Preferences.userRoot().node("/com/sswsuite/ssw");
/*   150 */     this.CurMech = new Mech(this.Prefs);
/*   151 */     this.ArmorTons = new visitors.VSetArmorTonnage(this.Prefs);
/*   152 */     this.Mechrender = new MechLoadoutRenderer(this);
/*       */     
/*       */ 
/*   155 */     this.PPCCapAC.SetISCodes('E', 'X', 'X', 'E');
/*   156 */     this.PPCCapAC.SetISDates(3057, 3060, true, 3060, 0, 0, false, false);
/*   157 */     this.PPCCapAC.SetISFactions("DC", "DC", "", "");
/*   158 */     this.PPCCapAC.SetPBMAllowed(true);
/*   159 */     this.PPCCapAC.SetPIMAllowed(true);
/*   160 */     this.PPCCapAC.SetRulesLevels(3, 3, -1, -1, -1);
/*   161 */     this.LIAC.SetISCodes('E', 'F', 'F', 'X');
/*   162 */     this.LIAC.SetISDates(0, 0, false, 2575, 2820, 0, true, false);
/*   163 */     this.LIAC.SetISFactions("TH", "", "", "");
/*   164 */     this.LIAC.SetCLCodes('E', 'X', 'E', 'F');
/*   165 */     this.LIAC.SetCLDates(0, 0, false, 2575, 0, 0, false, false);
/*   166 */     this.LIAC.SetCLFactions("TH", "", "", "");
/*   167 */     this.LIAC.SetPBMAllowed(true);
/*   168 */     this.LIAC.SetPIMAllowed(true);
/*   169 */     this.LIAC.SetRulesLevels(3, 3, -1, -1, -1);
/*   170 */     this.CaselessAmmoAC.SetISCodes('D', 'X', 'X', 'E');
/*   171 */     this.CaselessAmmoAC.SetISDates(3055, 3056, true, 3056, 0, 0, false, false);
/*   172 */     this.CaselessAmmoAC.SetISFactions("FC", "FC", "", "");
/*   173 */     this.CaselessAmmoAC.SetPBMAllowed(true);
/*   174 */     this.CaselessAmmoAC.SetPIMAllowed(true);
/*   175 */     this.CaselessAmmoAC.SetRulesLevels(3, 3, -1, -1, -1);
/*       */     
/*       */ 
/*   178 */     this.pnlDamageChart = new DamageChart();
/*       */     
/*   180 */     initComponents();
/*   181 */     this.Overview.SetEditorSize(310, 580);
/*   182 */     this.Capabilities.SetEditorSize(310, 380);
/*   183 */     this.Deployment.SetEditorSize(310, 380);
/*   184 */     this.History.SetEditorSize(310, 380);
/*   185 */     this.Additional.SetEditorSize(310, 380);
/*   186 */     this.Variants.SetEditorSize(310, 380);
/*   187 */     this.Notables.SetEditorSize(310, 380);
/*   188 */     this.pnlOverview.add(this.Overview);
/*   189 */     this.pnlCapabilities.add(this.Capabilities);
/*   190 */     this.pnlDeployment.add(this.Deployment);
/*   191 */     this.pnlHistory.add(this.History);
/*   192 */     this.pnlAdditionalFluff.add(this.Additional);
/*   193 */     this.pnlVariants.add(this.Variants);
/*   194 */     this.pnlNotables.add(this.Notables);
/*   195 */     setViewToolbar(this.Prefs.getBoolean("ViewToolbar", true));
/*   196 */     setTitle("Solaris Skunk Werks 0.6.83.1");
/*   197 */     pack();
/*       */     
/*   199 */     this.mnuDetails.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*   201 */         frmMainWide.this.GetInfoOn();
/*       */       }
/*       */       
/*   204 */     });
/*   205 */     this.mnuMountRear.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*   207 */         frmMainWide.this.MountRear();
/*       */       }
/*       */       
/*   210 */     });
/*   211 */     this.mnuSetVariable.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   213 */         frmMainWide.this.SetVariableSize();
/*       */       }
/*       */       
/*   216 */     });
/*   217 */     this.mnuSetLotSize.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   219 */         frmMainWide.this.SetAmmoLotSize();
/*       */       }
/*       */       
/*   222 */     });
/*   223 */     this.mnuArmorComponent.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   225 */         frmMainWide.this.ArmorComponent();
/*       */       }
/*       */       
/*   228 */     });
/*   229 */     this.mnuAddCapacitor.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   231 */         frmMainWide.this.PPCCapacitor();
/*       */       }
/*       */       
/*   234 */     });
/*   235 */     this.mnuAddInsulator.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   237 */         frmMainWide.this.LaserInsulator();
/*       */       }
/*       */       
/*   240 */     });
/*   241 */     this.mnuTurret.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   243 */         frmMainWide.this.TurretMount();
/*       */       }
/*       */       
/*   246 */     });
/*   247 */     this.mnuCaseless.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   249 */         frmMainWide.this.SwitchCaseless();
/*       */       }
/*       */       
/*   252 */     });
/*   253 */     this.mnuVGLArcFore.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   255 */         frmMainWide.this.SetVGLArcFore();
/*       */       }
/*       */       
/*   258 */     });
/*   259 */     this.mnuVGLArcForeSide.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   261 */         frmMainWide.this.SetVGLArcForeSide();
/*       */       }
/*       */       
/*   264 */     });
/*   265 */     this.mnuVGLArcRear.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   267 */         frmMainWide.this.SetVGLArcRear();
/*       */       }
/*       */       
/*   270 */     });
/*   271 */     this.mnuVGLArcRearSide.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   273 */         frmMainWide.this.SetVGLArcRearSide();
/*       */       }
/*       */       
/*   276 */     });
/*   277 */     this.mnuVGLAmmoFrag.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   279 */         frmMainWide.this.SetVGLAmmoFrag();
/*       */       }
/*       */       
/*   282 */     });
/*   283 */     this.mnuVGLAmmoChaff.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   285 */         frmMainWide.this.SetVGLAmmoChaff();
/*       */       }
/*       */       
/*   288 */     });
/*   289 */     this.mnuVGLAmmoIncen.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   291 */         frmMainWide.this.SetVGLAmmoIncendiary();
/*       */       }
/*       */       
/*   294 */     });
/*   295 */     this.mnuVGLAmmoSmoke.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   297 */         frmMainWide.this.SetVGLAmmoSmoke();
/*       */       }
/*       */       
/*   300 */     });
/*   301 */     this.mnuSelective.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent arg0) {
/*   303 */         frmMainWide.this.SelectiveAllocate();
/*       */       }
/*       */       
/*   306 */     });
/*   307 */     this.mnuAuto.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent arg0) {
/*   309 */         frmMainWide.this.AutoAllocate();
/*       */       }
/*       */       
/*   312 */     });
/*   313 */     this.mnuUnallocateAll.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*   315 */         frmMainWide.this.UnallocateAll();
/*       */       }
/*       */       
/*   318 */     });
/*   319 */     this.mnuRemoveItem.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   321 */         frmMainWide.this.RemoveItemCritTab();
/*       */       }
/*       */       
/*   324 */     });
/*   325 */     this.mnuVGLArc.setText("Set VGL Arc");
/*   326 */     this.mnuVGLArc.add(this.mnuVGLArcFore);
/*   327 */     this.mnuVGLArc.add(this.mnuVGLArcForeSide);
/*   328 */     this.mnuVGLArc.add(this.mnuVGLArcRear);
/*   329 */     this.mnuVGLArc.add(this.mnuVGLArcRearSide);
/*       */     
/*   331 */     this.mnuVGLAmmo.setText("Set VGL Ammo");
/*   332 */     this.mnuVGLAmmo.add(this.mnuVGLAmmoFrag);
/*   333 */     this.mnuVGLAmmo.add(this.mnuVGLAmmoChaff);
/*   334 */     this.mnuVGLAmmo.add(this.mnuVGLAmmoIncen);
/*   335 */     this.mnuVGLAmmo.add(this.mnuVGLAmmoSmoke);
/*       */     
/*   337 */     this.mnuUtilities.add(this.mnuDetails);
/*   338 */     this.mnuUtilities.add(this.mnuMountRear);
/*   339 */     this.mnuUtilities.add(this.mnuSetVariable);
/*   340 */     this.mnuUtilities.add(this.mnuSetLotSize);
/*   341 */     this.mnuUtilities.add(this.mnuArmorComponent);
/*   342 */     this.mnuUtilities.add(this.mnuAddCapacitor);
/*   343 */     this.mnuUtilities.add(this.mnuAddInsulator);
/*   344 */     this.mnuUtilities.add(this.mnuCaseless);
/*   345 */     this.mnuUtilities.add(this.mnuTurret);
/*   346 */     this.mnuUtilities.add(this.mnuVGLArc);
/*   347 */     this.mnuUtilities.add(this.mnuVGLAmmo);
/*   348 */     this.mnuUtilities.add(this.mnuSelective);
/*   349 */     this.mnuUtilities.add(this.mnuAuto);
/*   350 */     this.mnuUtilities.add(this.mnuUnallocateAll);
/*   351 */     this.mnuUtilities.add(this.mnuRemoveItem);
/*       */     
/*   353 */     this.mnuSetVariable.setVisible(false);
/*   354 */     this.mnuArmorComponent.setVisible(false);
/*   355 */     this.mnuAddCapacitor.setVisible(false);
/*   356 */     this.mnuAddInsulator.setVisible(false);
/*   357 */     this.mnuTurret.setVisible(false);
/*   358 */     this.mnuCaseless.setVisible(false);
/*   359 */     this.mnuVGLArc.setVisible(false);
/*   360 */     this.mnuVGLAmmo.setVisible(false);
/*       */     
/*   362 */     this.mnuFluffCut.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   364 */         frmMainWide.this.FluffCut(frmMainWide.this.mnuFluff.getInvoker());
/*       */       }
/*       */       
/*   367 */     });
/*   368 */     this.mnuFluffCopy.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   370 */         frmMainWide.this.FluffCopy(frmMainWide.this.mnuFluff.getInvoker());
/*       */       }
/*       */       
/*   373 */     });
/*   374 */     this.mnuFluffPaste.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   376 */         frmMainWide.this.FluffPaste(frmMainWide.this.mnuFluff.getInvoker());
/*       */       }
/*       */       
/*   379 */     });
/*   380 */     this.mnuFluff.add(this.mnuFluffCut);
/*   381 */     this.mnuFluff.add(this.mnuFluffCopy);
/*   382 */     this.mnuFluff.add(this.mnuFluffPaste);
/*       */     try
/*       */     {
/*   385 */       this.CurMech.Visit(new visitors.VMechFullRecalc());
/*       */     }
/*       */     catch (Exception e) {
/*   388 */       System.err.println(e.getMessage());
/*   389 */       e.printStackTrace();
/*       */     }
/*       */     
/*       */     try
/*       */     {
/*   394 */       this.data = new DataFactory(this.CurMech);
/*       */     } catch (Exception e) {
/*   396 */       System.err.println(e.getMessage());
/*   397 */       e.printStackTrace();
/*       */     }
/*       */     
/*       */ 
/*   401 */     this.cmbRulesLevel.setSelectedItem(this.Prefs.get("NewMech_RulesLevel", "Tournament Legal"));
/*   402 */     this.cmbMechEra.setSelectedItem(this.Prefs.get("NewMech_Era", "Age of War/Star League"));
/*   403 */     this.cmbProductionEra.setSelectedIndex(0);
/*   404 */     BuildTechBaseSelector();
/*   405 */     this.cmbTechBase.setSelectedItem(this.Prefs.get("NewMech_Techbase", "Inner Sphere"));
/*       */     
/*   407 */     BuildChassisSelector();
/*   408 */     BuildEngineSelector();
/*   409 */     BuildGyroSelector();
/*   410 */     BuildCockpitSelector();
/*   411 */     BuildEnhancementSelector();
/*   412 */     BuildArmorSelector();
/*   413 */     BuildHeatsinkSelector();
/*   414 */     BuildJumpJetSelector();
/*   415 */     FixArmorSpinners();
/*   416 */     SetPatchworkArmor();
/*   417 */     RefreshSummary();
/*   418 */     RefreshInfoPane();
/*   419 */     RefreshInternalPoints();
/*   420 */     SetLoadoutArrays();
/*   421 */     SetWeaponChoosers();
/*   422 */     this.cmbInternalType.setSelectedItem("Standard Structure");
/*   423 */     this.cmbEngineType.setSelectedItem("Fusion Engine");
/*   424 */     this.cmbGyroType.setSelectedItem("Standard Gyro");
/*   425 */     this.cmbCockpitType.setSelectedItem("Standard Cockpit");
/*   426 */     this.cmbPhysEnhance.setSelectedItem("No Enhancement");
/*   427 */     this.cmbHeatSinkType.setSelectedItem(this.Prefs.get("NewMech_Heatsinks", "Single Heat Sink"));
/*   428 */     this.cmbJumpJetType.setSelectedItem("Standard Jump Jet");
/*   429 */     this.cmbArmorType.setSelectedItem("Standard Armor");
/*   430 */     this.cmbOmniVariant.setModel(new DefaultComboBoxModel(new String[] { this.CurMech.GetLoadout().GetName() }));
/*   431 */     this.lblSumPAmps.setVisible(false);
/*   432 */     this.txtSumPAmpsTon.setVisible(false);
/*   433 */     this.txtSumPAmpsACode.setVisible(false);
/*       */     
/*   435 */     this.tblWeaponManufacturers.setModel(new javax.swing.table.AbstractTableModel()
/*       */     {
/*       */       public String getColumnName(int col) {
/*   438 */         if (col == 1) {
/*   439 */           return "Manufacturer/Model";
/*       */         }
/*   441 */         return "Item Name";
/*       */       }
/*       */       
/*   444 */       public int getRowCount() { return frmMainWide.this.CurMech.GetLoadout().GetEquipment().size(); }
/*   445 */       public int getColumnCount() { return 2; }
/*       */       
/*   447 */       public Object getValueAt(int row, int col) { Object o = frmMainWide.this.CurMech.GetLoadout().GetEquipment().get(row);
/*   448 */         if (col == 1) {
/*   449 */           return ((abPlaceable)o).GetManufacturer();
/*       */         }
/*   451 */         return ((abPlaceable)o).CritName();
/*       */       }
/*       */       
/*       */       public boolean isCellEditable(int row, int col)
/*       */       {
/*   456 */         if (col == 0) {
/*   457 */           return false;
/*       */         }
/*   459 */         return true;
/*       */       }
/*       */       
/*       */       public void setValueAt(Object value, int row, int col)
/*       */       {
/*   464 */         if (col == 0) return;
/*   465 */         if (!(value instanceof String)) return;
/*   466 */         abPlaceable a = (abPlaceable)frmMainWide.this.CurMech.GetLoadout().GetEquipment().get(row);
/*   467 */         if (frmMainWide.this.chkIndividualWeapons.isSelected()) {
/*   468 */           a.SetManufacturer((String)value);
/*   469 */           fireTableCellUpdated(row, col);
/*       */         } else {
/*   471 */           ArrayList v = frmMainWide.this.CurMech.GetLoadout().GetEquipment();
/*   472 */           for (int i = 0; i < v.size(); i++) {
/*   473 */             if (filehandlers.FileCommon.LookupStripArc(((abPlaceable)v.get(i)).LookupName()).equals(filehandlers.FileCommon.LookupStripArc(a.LookupName()))) {
/*   474 */               ((abPlaceable)v.get(i)).SetManufacturer((String)value);
/*       */             }
/*       */           }
/*   477 */           fireTableDataChanged();
/*       */         }
/*       */         
/*       */       }
/*   481 */     });
/*   482 */     this.tblWeaponManufacturers.getInputMap(1).put(javax.swing.KeyStroke.getKeyStroke(9, 0, false), "selectNextRow");
/*       */     
/*       */ 
/*   485 */     if (this.Prefs.getBoolean("LoadLastMech", false)) { LoadMechFromFile(this.Prefs.get("LastOpenDirectory", "") + this.Prefs.get("LastOpenFile", ""));
/*       */     }
/*   487 */     if (!this.Prefs.get("FileToOpen", "").isEmpty()) { LoadMechFromFile(this.Prefs.get("FileToOpen", ""));
/*       */     }
/*       */     
/*   490 */     this.CurMech.SetChanged(false);
/*       */   }
/*       */   
/*       */   public Preferences GetPrefs() {
/*   494 */     return this.Prefs;
/*       */   }
/*       */   
/*       */   public Mech GetMech() {
/*   498 */     return this.CurMech;
/*       */   }
/*       */   
/*       */   public abPlaceable GetCurItem() {
/*   502 */     return this.CurItem;
/*       */   }
/*       */   
/*       */ 
/*   506 */   public DataFactory GetData() { return this.data; }
/*       */   
/*       */   private JButton btnAddVariant;
/*       */   private JButton btnArmorTons;
/*   510 */   public dialog.frmForce GetForceDialogue() { return this.dForce; }
/*       */   
/*       */   private JButton btnAutoAllocate;
/*       */   
/*   514 */   public MechLoadoutRenderer GetLoadoutRenderer() { return this.Mechrender; }
/*       */   
/*       */   private JCheckBox btnBalanceArmor;
/*       */   private JButton btnBracketChart;
/*       */   private JButton btnChatInfo;
/*       */   private JButton btnClearEquip;
/*       */   
/*   521 */   private void SetWeaponChoosers() { this.data.Rebuild(this.CurMech);
/*   522 */     this.Equipment[1] = this.data.GetEquipment().GetEnergyWeapons(this.CurMech);
/*   523 */     this.Equipment[2] = this.data.GetEquipment().GetMissileWeapons(this.CurMech);
/*   524 */     this.Equipment[0] = this.data.GetEquipment().GetBallisticWeapons(this.CurMech);
/*   525 */     this.Equipment[3] = this.data.GetEquipment().GetPhysicalWeapons(this.CurMech);
/*   526 */     this.Equipment[5] = this.data.GetEquipment().GetArtillery(this.CurMech);
/*   527 */     this.Equipment[4] = this.data.GetEquipment().GetEquipment(this.CurMech);
/*   528 */     this.Equipment[6] = { " " };
/*   529 */     if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/*   530 */       this.Equipment[7] = { " " };
/*       */     } else {
/*   532 */       this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */     }
/*       */     
/*   535 */     for (int i = 0; i < this.Equipment.length; i++) {
/*   536 */       if (this.Equipment[i] == null) {
/*   537 */         this.Equipment[i] = { " " };
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   542 */     this.lstChooseEnergy.setListData(this.Equipment[1]);
/*   543 */     this.lstChooseMissile.setListData(this.Equipment[2]);
/*   544 */     this.lstChooseBallistic.setListData(this.Equipment[0]);
/*   545 */     this.lstChoosePhysical.setListData(this.Equipment[3]);
/*   546 */     this.lstChooseEquipment.setListData(this.Equipment[4]);
/*   547 */     this.lstChooseAmmunition.setListData(this.Equipment[6]);
/*       */     
/*   549 */     this.lstChooseArtillery.setListData(this.Equipment[5]);
/*       */   }
/*       */   
/*       */   private JButton btnClearImage;
/*       */   private JButton btnClearLoadout;
/*       */   private JButton btnCompactCrits;
/*   555 */   private void SetLoadoutArrays() { if (this.CurMech.IsQuad())
/*       */     {
/*   557 */       ((TitledBorder)this.pnlLACrits.getBorder()).setTitle("Left Front Leg");
/*   558 */       ((TitledBorder)this.pnlRACrits.getBorder()).setTitle("Right Front Leg");
/*   559 */       ((TitledBorder)this.pnlLLCrits.getBorder()).setTitle("Left Rear Leg");
/*   560 */       ((TitledBorder)this.pnlRLCrits.getBorder()).setTitle("Right Rear Leg");
/*   561 */       this.lstLACrits.setVisibleRowCount(6);
/*   562 */       this.lstRACrits.setVisibleRowCount(6);
/*       */     } else {
/*   564 */       ((TitledBorder)this.pnlLACrits.getBorder()).setTitle("Left Arm");
/*   565 */       ((TitledBorder)this.pnlRACrits.getBorder()).setTitle("Right Arm");
/*   566 */       ((TitledBorder)this.pnlLLCrits.getBorder()).setTitle("Left Leg");
/*   567 */       ((TitledBorder)this.pnlRLCrits.getBorder()).setTitle("Right Leg");
/*   568 */       this.lstLACrits.setVisibleRowCount(12);
/*   569 */       this.lstRACrits.setVisibleRowCount(12);
/*       */     }
/*       */     
/*   572 */     CheckActuators();
/*       */     
/*       */ 
/*   575 */     this.lstHDCrits.setListData(this.CurMech.GetLoadout().GetHDCrits());
/*   576 */     this.lstCTCrits.setListData(this.CurMech.GetLoadout().GetCTCrits());
/*   577 */     this.lstLTCrits.setListData(this.CurMech.GetLoadout().GetLTCrits());
/*   578 */     this.lstRTCrits.setListData(this.CurMech.GetLoadout().GetRTCrits());
/*   579 */     this.lstLACrits.setListData(this.CurMech.GetLoadout().GetLACrits());
/*   580 */     this.lstRACrits.setListData(this.CurMech.GetLoadout().GetRACrits());
/*   581 */     this.lstLLCrits.setListData(this.CurMech.GetLoadout().GetLLCrits());
/*   582 */     this.lstRLCrits.setListData(this.CurMech.GetLoadout().GetRLCrits());
/*   583 */     this.lstCritsToPlace.setListData(this.CurMech.GetLoadout().GetQueue().toArray());
/*       */   }
/*       */   
/*       */   public void setMech(Mech m) {
/*   587 */     this.CurMech = m;
/*   588 */     LoadMechIntoGUI();
/*       */   }
/*       */   
/*       */   public void CheckActuators() {
/*   592 */     if (this.CurMech.IsQuad()) {
/*   593 */       this.chkLALowerArm.setEnabled(false);
/*   594 */       this.chkRALowerArm.setEnabled(false);
/*   595 */       this.chkLAHand.setEnabled(false);
/*   596 */       this.chkRAHand.setEnabled(false);
/*   597 */       this.chkLALowerArm.setSelected(true);
/*   598 */       this.chkRALowerArm.setSelected(true);
/*   599 */       this.chkLAHand.setSelected(true);
/*   600 */       this.chkRAHand.setSelected(true);
/*       */     } else {
/*   602 */       if (this.CurMech.GetActuators().LockedLeft()) {
/*   603 */         this.chkLALowerArm.setEnabled(false);
/*   604 */         this.chkLAHand.setEnabled(false);
/*       */       } else {
/*   606 */         this.chkLALowerArm.setEnabled(true);
/*   607 */         this.chkLAHand.setEnabled(true);
/*       */       }
/*   609 */       if (this.CurMech.GetActuators().LockedRight()) {
/*   610 */         this.chkRALowerArm.setEnabled(false);
/*   611 */         this.chkRAHand.setEnabled(false);
/*       */       } else {
/*   613 */         this.chkRALowerArm.setEnabled(true);
/*   614 */         this.chkRAHand.setEnabled(true);
/*       */       }
/*   616 */       if (this.CurMech.GetActuators().LeftLowerInstalled()) {
/*   617 */         this.chkLALowerArm.setSelected(true);
/*   618 */         if (this.CurMech.GetActuators().LeftHandInstalled()) {
/*   619 */           this.chkLAHand.setSelected(true);
/*       */         } else {
/*   621 */           this.chkLAHand.setSelected(false);
/*       */         }
/*       */       } else {
/*   624 */         this.chkLALowerArm.setSelected(false);
/*   625 */         this.chkLAHand.setSelected(false);
/*       */       }
/*   627 */       if (this.CurMech.GetActuators().RightLowerInstalled()) {
/*   628 */         this.chkRALowerArm.setSelected(true);
/*   629 */         if (this.CurMech.GetActuators().RightHandInstalled()) {
/*   630 */           this.chkRAHand.setSelected(true);
/*       */         } else {
/*   632 */           this.chkRAHand.setSelected(false);
/*       */         }
/*       */       } else {
/*   635 */         this.chkRALowerArm.setSelected(false);
/*   636 */         this.chkRAHand.setSelected(false);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void FixWalkMPSpinner()
/*       */   {
/*   643 */     int MaxWalk = this.CurMech.GetMaxWalkMP();
/*   644 */     int CurWalk = this.CurMech.GetWalkingMP();
/*       */     
/*       */ 
/*       */ 
/*   648 */     if (CurWalk > MaxWalk) CurWalk = MaxWalk;
/*       */     try
/*       */     {
/*   651 */       this.CurMech.SetWalkMP(CurWalk);
/*       */     } catch (Exception e) {
/*   653 */       Media.Messager(e.getMessage() + "\nSetting Walk MP to 1.  Please reset to desired speed.");
/*       */       try {
/*   655 */         this.CurMech.SetWalkMP(1);
/*       */       } catch (Exception e1) {
/*   657 */         Media.Messager(this, "Fatal error while attempting to set Walk MP to 1:\n" + e1.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*   658 */         GetNewMech();
/*   659 */         return;
/*       */       }
/*       */     }
/*   662 */     this.lblRunMP.setText("" + this.CurMech.GetRunningMP());
/*       */     
/*       */ 
/*   665 */     this.spnWalkMP.setModel(new SpinnerNumberModel(CurWalk, 1, MaxWalk, 1));
/*       */   }
/*       */   
/*       */   private void BuildChassisSelector()
/*       */   {
/*   670 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   674 */     ifState[] check = this.CurMech.GetIntStruc().GetStates(this.CurMech.IsQuad());
/*   675 */     for (int i = 0; i < check.length; i++) {
/*   676 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   677 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   682 */     String[] temp = new String[list.size()];
/*   683 */     for (int i = 0; i < list.size(); i++) {
/*   684 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   688 */     this.cmbInternalType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildEngineSelector()
/*       */   {
/*   693 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   697 */     ifState[] check = this.CurMech.GetEngine().GetStates();
/*   698 */     for (int i = 0; i < check.length; i++) {
/*   699 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   700 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   705 */     String[] temp = new String[list.size()];
/*   706 */     for (int i = 0; i < list.size(); i++) {
/*   707 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   711 */     this.cmbEngineType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildGyroSelector()
/*       */   {
/*   716 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   720 */     ifState[] check = this.CurMech.GetGyro().GetStates();
/*   721 */     for (int i = 0; i < check.length; i++) {
/*   722 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   723 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   728 */     String[] temp = new String[list.size()];
/*   729 */     for (int i = 0; i < list.size(); i++) {
/*   730 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   734 */     this.cmbGyroType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildCockpitSelector()
/*       */   {
/*   739 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   743 */     ifState[] check = this.CurMech.GetCockpit().GetStates();
/*   744 */     for (int i = 0; i < check.length; i++) {
/*   745 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   746 */         list.add(check[i].LookupName());
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   751 */     String[] temp = new String[list.size()];
/*   752 */     for (int i = 0; i < list.size(); i++) {
/*   753 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   757 */     this.cmbCockpitType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildEnhancementSelector()
/*       */   {
/*   762 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   766 */     ifState[] check = this.CurMech.GetPhysEnhance().GetStates();
/*   767 */     for (int i = 0; i < check.length; i++) {
/*   768 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   769 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   774 */     String[] temp = new String[list.size()];
/*   775 */     for (int i = 0; i < list.size(); i++) {
/*   776 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   780 */     this.cmbPhysEnhance.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildJumpJetSelector()
/*       */   {
/*   785 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   789 */     ifState[] check = this.CurMech.GetJumpJets().GetStates();
/*   790 */     for (int i = 0; i < check.length; i++) {
/*   791 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   792 */         list.add(check[i].LookupName());
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   797 */     String[] temp = new String[list.size()];
/*   798 */     for (int i = 0; i < list.size(); i++) {
/*   799 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   803 */     this.cmbJumpJetType.setModel(new DefaultComboBoxModel(temp));
/*   804 */     if (temp.length > 0) {
/*   805 */       EnableJumpJets(true);
/*   806 */       this.cmbJumpJetType.setSelectedItem(this.CurMech.GetJumpJets().LookupName());
/*       */     } else {
/*   808 */       EnableJumpJets(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private JButton btnDeleteVariant;
/*       */   private JButton btnEfficientArmor;
/*       */   private JButton btnExportClipboardIcon;
/*       */   private JButton btnExportHTML;
/*   816 */   private void FixJJSpinnerModel() { int min = 0;
/*   817 */     int max = 0;
/*   818 */     int current = 0;
/*       */     
/*   820 */     if (this.CurMech.IsOmnimech()) {
/*   821 */       min = this.CurMech.GetJumpJets().GetBaseLoadoutNumJJ();
/*       */     }
/*       */     
/*   824 */     if (this.CurMech.GetJumpJets().IsImproved()) {
/*   825 */       if ((this.CurMech.GetArmor().IsHardened()) && (!this.CurMech.GetJumpJets().IsImproved())) {
/*   826 */         max = this.CurMech.GetRunningMP() - 1;
/*       */       } else {
/*   828 */         max = this.CurMech.GetRunningMP();
/*       */       }
/*       */     } else {
/*   831 */       max = this.CurMech.GetWalkingMP();
/*       */     }
/*       */     
/*   834 */     current = this.CurMech.GetJumpJets().GetNumJJ();
/*       */     
/*       */ 
/*   837 */     if (current > max) {
/*   838 */       for (; current > max; current--) {
/*   839 */         this.CurMech.GetJumpJets().DecrementNumJJ();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   844 */     if (current < min) {
/*   845 */       for (; current < min; current++) {
/*   846 */         this.CurMech.GetJumpJets().IncrementNumJJ();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   851 */     if (this.CurMech.GetJumpJets().GetNumJJ() > 0)
/*       */     {
/*   853 */       this.txtJJModel.setEnabled(true);
/*       */     }
/*       */     else {
/*   856 */       this.txtJJModel.setEnabled(false);
/*       */     }
/*       */     
/*   859 */     this.spnJumpMP.setModel(new SpinnerNumberModel(current, min, max, 1));
/*       */   }
/*       */   
/*       */   private void FixHeatSinkSpinnerModel()
/*       */   {
/*   864 */     if (this.CurMech.IsOmnimech()) {
/*   865 */       this.spnNumberOfHS.setModel(new SpinnerNumberModel(this.CurMech
/*   866 */         .GetHeatSinks().GetNumHS(), this.CurMech.GetHeatSinks().GetBaseLoadoutNumHS(), 65, 1));
/*       */     } else {
/*   868 */       this.spnNumberOfHS.setModel(new SpinnerNumberModel(this.CurMech
/*   869 */         .GetHeatSinks().GetNumHS(), this.CurMech.GetEngine().FreeHeatSinks(), 65, 1));
/*       */     }
/*       */   }
/*       */   
/*       */   private void FixJumpBoosterSpinnerModel() {
/*   874 */     int current = this.CurMech.GetJumpBoosterMP();
/*       */     
/*   876 */     this.spnBoosterMP.setModel(new SpinnerNumberModel(current, 0, 20, 1));
/*       */   }
/*       */   
/*       */   private void BuildHeatsinkSelector()
/*       */   {
/*   881 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   885 */     ifState[] check = this.CurMech.GetHeatSinks().GetStates();
/*   886 */     for (int i = 0; i < check.length; i++) {
/*   887 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   888 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   893 */     String[] temp = new String[list.size()];
/*   894 */     for (int i = 0; i < list.size(); i++) {
/*   895 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   899 */     this.cmbHeatSinkType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildArmorSelector()
/*       */   {
/*   904 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   908 */     ifState[] check = this.CurMech.GetArmor().GetStates();
/*   909 */     for (int i = 0; i < check.length; i++) {
/*   910 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   911 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   916 */     String[] temp = new String[list.size()];
/*   917 */     for (int i = 0; i < list.size(); i++) {
/*   918 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   922 */     this.cmbArmorType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildPatchworkChoosers()
/*       */   {
/*   927 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   931 */     ifState[] check = this.CurMech.GetArmor().GetPatchworkStates();
/*   932 */     for (int i = 0; i < check.length; i++) {
/*   933 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   934 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   939 */     String[] temp = new String[list.size()];
/*   940 */     for (int i = 0; i < list.size(); i++) {
/*   941 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   945 */     this.cmbPWHDType.setModel(new DefaultComboBoxModel(temp));
/*   946 */     this.cmbPWCTType.setModel(new DefaultComboBoxModel(temp));
/*   947 */     this.cmbPWLTType.setModel(new DefaultComboBoxModel(temp));
/*   948 */     this.cmbPWRTType.setModel(new DefaultComboBoxModel(temp));
/*   949 */     this.cmbPWLAType.setModel(new DefaultComboBoxModel(temp));
/*   950 */     this.cmbPWRAType.setModel(new DefaultComboBoxModel(temp));
/*   951 */     this.cmbPWLLType.setModel(new DefaultComboBoxModel(temp));
/*   952 */     this.cmbPWRLType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildTechBaseSelector() {
/*   956 */     switch (this.CurMech.GetEra()) {
/*       */     case 0: 
/*   958 */       this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/*   959 */       break;
/*       */     default: 
/*   961 */       if (this.CurMech.GetRulesLevel() >= 3) {
/*   962 */         this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan", "Mixed" }));
/*   963 */       } else if (this.CurMech.GetRulesLevel() == 0) {
/*   964 */         this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/*       */       } else {
/*   966 */         this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan" }));
/*       */       }
/*       */       break;
/*       */     }
/*       */     try {
/*   971 */       this.cmbTechBase.setSelectedIndex(this.CurMech.GetTechbase());
/*       */     } catch (Exception e) {
/*   973 */       Media.Messager("Could not set the Techbase due to changes.\nReverting to Inner Sphere.");
/*   974 */       this.cmbTechBase.setSelectedIndex(0);
/*       */     }
/*       */   }
/*       */   
/*       */   private void BuildMechTypeSelector() {
/*   979 */     switch (this.CurMech.GetRulesLevel()) {
/*       */     case 0: 
/*   981 */       this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech" }));
/*   982 */       this.CurMech.SetModern();
/*   983 */       break;
/*       */     case 4: 
/*   985 */       if (this.CurMech.GetEra() == 1) {
/*   986 */         this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech", "IndustrialMech" }));
/*   987 */         this.CurMech.SetModern();
/*       */       } else {
/*   989 */         this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech", "IndustrialMech", "Primitive BattleMech", "Primitive IndustrialMech" }));
/*       */       }
/*   991 */       break;
/*       */     default: 
/*   993 */       this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech", "IndustrialMech" }));
/*   994 */       this.CurMech.SetModern();
/*       */     }
/*       */     try
/*       */     {
/*   998 */       if (this.CurMech.IsIndustrialmech()) {
/*   999 */         if (this.CurMech.IsPrimitive()) {
/*  1000 */           this.cmbMechType.setSelectedIndex(3);
/*       */         } else {
/*  1002 */           this.cmbMechType.setSelectedIndex(1);
/*       */         }
/*       */       }
/*  1005 */       else if (this.CurMech.IsPrimitive()) {
/*  1006 */         this.cmbMechType.setSelectedIndex(2);
/*       */       } else {
/*  1008 */         this.cmbMechType.setSelectedIndex(0);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/*  1012 */       Media.Messager("Could not set the 'Mech type due to changes.\nReverting to a BattleMech.");
/*  1013 */       this.cmbMechType.setSelectedIndex(0);
/*       */     }
/*       */   }
/*       */   
/*       */   public String BuildLookupName(ifState s) {
/*  1018 */     String retval = s.LookupName();
/*  1019 */     if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/*  1020 */       if (s.HasCounterpart()) {
/*  1021 */         if (s.GetAvailability().GetTechBase() == 1) {
/*  1022 */           return "(CL) " + retval;
/*       */         }
/*  1024 */         return "(IS) " + retval;
/*       */       }
/*       */       
/*  1027 */       return retval;
/*       */     }
/*       */     
/*  1030 */     return retval;
/*       */   }
/*       */   
/*       */   private JButton btnExportHTMLIcon;
/*       */   private JButton btnExportMTF;
/*       */   private JButton btnExportMTFIcon;
/*       */   private JButton btnExportTXT;
/*       */   
/*  1038 */   private void RefreshEquipment() { components.ifMissileGuidance ArtCheck = new components.ArtemisIVFCS(null);
/*  1039 */     if (CommonTools.IsAllowed(ArtCheck.GetAvailability(), this.CurMech)) {
/*  1040 */       this.chkFCSAIV.setEnabled(true);
/*       */     } else {
/*  1042 */       this.chkFCSAIV.setSelected(false);
/*  1043 */       this.chkFCSAIV.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1047 */     ArtCheck = new components.ArtemisVFCS(null);
/*  1048 */     if (CommonTools.IsAllowed(ArtCheck.GetAvailability(), this.CurMech)) {
/*  1049 */       this.chkFCSAV.setEnabled(true);
/*       */     } else {
/*  1051 */       this.chkFCSAV.setSelected(false);
/*  1052 */       this.chkFCSAV.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1056 */     ArtCheck = new components.ApolloFCS(null);
/*  1057 */     if (CommonTools.IsAllowed(ArtCheck.GetAvailability(), this.CurMech)) {
/*  1058 */       this.chkFCSApollo.setEnabled(true);
/*       */     } else {
/*  1060 */       this.chkFCSApollo.setSelected(false);
/*  1061 */       this.chkFCSApollo.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1065 */     if (CommonTools.IsAllowed(this.CurMech.GetTC().GetAvailability(), this.CurMech)) {
/*  1066 */       this.chkUseTC.setEnabled(true);
/*  1067 */       if (this.CurMech.UsingTC()) {
/*  1068 */         this.chkUseTC.setSelected(true);
/*       */       } else {
/*  1070 */         this.chkUseTC.setSelected(false);
/*       */       }
/*       */     } else {
/*  1073 */       this.chkUseTC.setSelected(false);
/*  1074 */       this.chkUseTC.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1078 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetCTCase().GetAvailability(), this.CurMech)) {
/*  1079 */       this.chkCTCASE.setEnabled(true);
/*  1080 */       this.chkLTCASE.setEnabled(true);
/*  1081 */       this.chkRTCASE.setEnabled(true);
/*       */     } else {
/*  1083 */       this.chkCTCASE.setSelected(false);
/*  1084 */       this.chkCTCASE.setEnabled(false);
/*  1085 */       this.chkLTCASE.setSelected(false);
/*  1086 */       this.chkLTCASE.setEnabled(false);
/*  1087 */       this.chkRTCASE.setSelected(false);
/*  1088 */       this.chkRTCASE.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1092 */     if (this.CurMech.GetCockpit().CanUseCommandConsole()) if ((CommonTools.IsAllowed(this.CurMech.GetCommandConsole().GetAvailability(), this.CurMech) & !this.chkFHES.isSelected())) {
/*  1093 */         this.chkCommandConsole.setEnabled(true);
/*       */         break label408; }
/*  1095 */     this.chkCommandConsole.setEnabled(false);
/*  1096 */     this.chkCommandConsole.setSelected(false);
/*       */     label408:
/*  1098 */     if ((this.CurMech.CanUseFHES()) && (CommonTools.IsAllowed(this.CurMech.GetFHESAC(), this.CurMech))) {
/*  1099 */       this.chkFHES.setEnabled(true);
/*       */     } else {
/*  1101 */       this.chkFHES.setEnabled(false);
/*  1102 */       this.chkFHES.setSelected(false);
/*       */     }
/*  1104 */     if (this.CurMech.GetCockpit().IsTorsoMounted())
/*       */     {
/*  1106 */       this.chkEjectionSeat.setEnabled(false);
/*  1107 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*  1109 */     if (this.CurMech.IsIndustrialmech()) {
/*  1110 */       this.chkEjectionSeat.setEnabled(true);
/*       */     } else {
/*  1112 */       this.chkEjectionSeat.setEnabled(false);
/*  1113 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/*  1117 */     if (CommonTools.IsAllowed(this.CurMech.GetNullSig().GetAvailability(), this.CurMech)) {
/*  1118 */       this.chkNullSig.setEnabled(true);
/*       */     } else {
/*  1120 */       this.chkNullSig.setEnabled(false);
/*  1121 */       this.chkNullSig.setSelected(false);
/*       */     }
/*  1123 */     if (CommonTools.IsAllowed(this.CurMech.GetVoidSig().GetAvailability(), this.CurMech)) {
/*  1124 */       this.chkVoidSig.setEnabled(true);
/*       */     } else {
/*  1126 */       this.chkVoidSig.setEnabled(false);
/*  1127 */       this.chkVoidSig.setSelected(false);
/*       */     }
/*  1129 */     if (CommonTools.IsAllowed(this.CurMech.GetChameleon().GetAvailability(), this.CurMech)) {
/*  1130 */       this.chkCLPS.setEnabled(true);
/*       */     } else {
/*  1132 */       this.chkCLPS.setEnabled(false);
/*  1133 */       this.chkCLPS.setSelected(false);
/*       */     }
/*  1135 */     if (CommonTools.IsAllowed(this.CurMech.GetBlueShield().GetAvailability(), this.CurMech)) {
/*  1136 */       this.chkBSPFD.setEnabled(true);
/*       */     } else {
/*  1138 */       this.chkBSPFD.setEnabled(false);
/*  1139 */       this.chkBSPFD.setSelected(false);
/*       */     }
/*  1141 */     if (this.CurMech.IsIndustrialmech()) {
/*  1142 */       this.chkEnviroSealing.setEnabled(true);
/*  1143 */       this.chkEjectionSeat.setEnabled(true);
/*       */     } else {
/*  1145 */       this.chkEnviroSealing.setEnabled(false);
/*  1146 */       this.chkEjectionSeat.setEnabled(false);
/*  1147 */       this.chkEnviroSealing.setSelected(false);
/*  1148 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*  1150 */     if (CommonTools.IsAllowed(this.CurMech.GetTracks().GetAvailability(), this.CurMech)) {
/*  1151 */       this.chkTracks.setEnabled(true);
/*       */     } else {
/*  1153 */       this.chkTracks.setEnabled(false);
/*  1154 */       this.chkTracks.setSelected(false);
/*       */     }
/*  1156 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetSupercharger().GetAvailability(), this.CurMech)) {
/*  1157 */       this.chkSupercharger.setEnabled(true);
/*  1158 */       this.cmbSCLoc.setEnabled(true);
/*  1159 */       this.lblSupercharger.setEnabled(true);
/*       */     } else {
/*  1161 */       this.chkSupercharger.setEnabled(false);
/*  1162 */       this.cmbSCLoc.setEnabled(false);
/*  1163 */       this.lblSupercharger.setEnabled(false);
/*       */     }
/*  1165 */     if ((CommonTools.IsAllowed(this.PWAC, this.CurMech) & !this.CurMech.IsOmnimech())) {
/*  1166 */       this.chkPartialWing.setEnabled(true);
/*       */     } else {
/*  1168 */       this.chkPartialWing.setEnabled(false);
/*       */     }
/*  1170 */     this.chkPartialWing.setSelected(this.CurMech.UsingPartialWing());
/*  1171 */     if ((CommonTools.IsAllowed(this.CurMech.GetJumpBooster().GetAvailability(), this.CurMech) & !this.CurMech.IsOmnimech())) {
/*  1172 */       this.chkBoosters.setEnabled(true);
/*  1173 */       FixJumpBoosterSpinnerModel();
/*       */     } else {
/*       */       try {
/*  1176 */         this.CurMech.SetJumpBooster(false);
/*       */       }
/*       */       catch (Exception e) {
/*  1179 */         System.err.println("Could not remove Jump Booster!");
/*       */       }
/*  1181 */       this.chkBoosters.setEnabled(false);
/*  1182 */       this.chkBoosters.setSelected(false);
/*  1183 */       FixJumpBoosterSpinnerModel();
/*       */     }
/*  1185 */     this.chkBoosters.setSelected(this.CurMech.UsingJumpBooster());
/*  1186 */     if (this.CurMech.UsingJumpBooster()) {
/*  1187 */       this.spnBoosterMP.setEnabled(true);
/*       */     } else {
/*  1189 */       this.spnBoosterMP.setEnabled(false);
/*       */     }
/*  1191 */     if (CommonTools.IsAllowed(this.CurMech.GetLLAES().GetAvailability(), this.CurMech)) {
/*  1192 */       this.chkRAAES.setEnabled(true);
/*  1193 */       this.chkLAAES.setEnabled(true);
/*  1194 */       this.chkLegAES.setEnabled(true);
/*       */     } else {
/*  1196 */       this.chkRAAES.setSelected(false);
/*  1197 */       this.chkLAAES.setSelected(false);
/*  1198 */       this.chkLegAES.setSelected(false);
/*  1199 */       this.chkRAAES.setEnabled(false);
/*  1200 */       this.chkLAAES.setEnabled(false);
/*  1201 */       this.chkLegAES.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1205 */     this.chkBoobyTrap.setSelected(false);
/*  1206 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetBoobyTrap().GetAvailability(), this.CurMech)) {
/*  1207 */       this.chkBoobyTrap.setEnabled(true);
/*  1208 */       if (this.CurMech.GetLoadout().HasBoobyTrap()) this.chkBoobyTrap.setSelected(true);
/*       */     } else {
/*  1210 */       this.chkBoobyTrap.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1214 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetCTCaseII().GetAvailability(), this.CurMech)) {
/*  1215 */       this.chkHDCASE2.setEnabled(true);
/*  1216 */       this.chkCTCASE2.setEnabled(true);
/*  1217 */       this.chkLTCASE2.setEnabled(true);
/*  1218 */       this.chkRTCASE2.setEnabled(true);
/*  1219 */       this.chkLACASE2.setEnabled(true);
/*  1220 */       this.chkRACASE2.setEnabled(true);
/*  1221 */       this.chkLLCASE2.setEnabled(true);
/*  1222 */       this.chkRLCASE2.setEnabled(true);
/*       */     } else {
/*       */       try {
/*  1225 */         this.chkHDCASE2.setEnabled(false);
/*  1226 */         this.chkHDCASE2.setSelected(false);
/*  1227 */         this.CurMech.GetLoadout().SetHDCASEII(false, -1, false);
/*  1228 */         this.chkCTCASE2.setEnabled(false);
/*  1229 */         this.chkCTCASE2.setSelected(false);
/*  1230 */         this.CurMech.GetLoadout().SetCTCASEII(false, -1, false);
/*  1231 */         this.chkLTCASE2.setEnabled(false);
/*  1232 */         this.chkLTCASE2.setSelected(false);
/*  1233 */         this.CurMech.GetLoadout().SetLTCASEII(false, -1, false);
/*  1234 */         this.chkRTCASE2.setEnabled(false);
/*  1235 */         this.chkRTCASE2.setSelected(false);
/*  1236 */         this.CurMech.GetLoadout().SetRTCASEII(false, -1, false);
/*  1237 */         this.chkLACASE2.setEnabled(false);
/*  1238 */         this.chkLACASE2.setSelected(false);
/*  1239 */         this.CurMech.GetLoadout().SetLACASEII(false, -1, false);
/*  1240 */         this.chkRACASE2.setEnabled(false);
/*  1241 */         this.chkRACASE2.setSelected(false);
/*  1242 */         this.CurMech.GetLoadout().SetRACASEII(false, -1, false);
/*  1243 */         this.chkLLCASE2.setEnabled(false);
/*  1244 */         this.chkLLCASE2.setSelected(false);
/*  1245 */         this.CurMech.GetLoadout().SetLLCASEII(false, -1, false);
/*  1246 */         this.chkRLCASE2.setEnabled(false);
/*  1247 */         this.chkRLCASE2.setSelected(false);
/*  1248 */         this.CurMech.GetLoadout().SetRLCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/*  1251 */         System.err.println(e.getMessage());
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1256 */     if (this.CurMech.GetLoadout().GetTechBase() > 0) {
/*  1257 */       this.chkClanCASE.setEnabled(true);
/*       */     } else {
/*  1259 */       this.CurMech.GetLoadout().SetClanCASE(false);
/*  1260 */       this.chkClanCASE.setSelected(false);
/*  1261 */       this.chkClanCASE.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1265 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetHDTurret().GetAvailability(), this.CurMech)) {
/*  1266 */       if (this.CurMech.GetLoadout().CanUseHDTurret()) {
/*  1267 */         this.chkHDTurret.setEnabled(true);
/*       */       } else {
/*  1269 */         this.chkHDTurret.setSelected(false);
/*  1270 */         this.chkHDTurret.setEnabled(false);
/*       */       }
/*  1272 */       if (this.CurMech.GetLoadout().CanUseLTTurret()) {
/*  1273 */         this.chkLTTurret.setEnabled(true);
/*       */       } else {
/*  1275 */         this.chkLTTurret.setSelected(false);
/*  1276 */         this.chkLTTurret.setEnabled(false);
/*       */       }
/*  1278 */       if (this.CurMech.GetLoadout().CanUseRTTurret()) {
/*  1279 */         this.chkRTTurret.setEnabled(true);
/*       */       } else {
/*  1281 */         this.chkRTTurret.setSelected(false);
/*  1282 */         this.chkRTTurret.setEnabled(false);
/*       */       }
/*       */     } else {
/*  1285 */       this.chkHDTurret.setSelected(false);
/*  1286 */       this.chkHDTurret.setEnabled(false);
/*  1287 */       this.chkLTTurret.setSelected(false);
/*  1288 */       this.chkLTTurret.setEnabled(false);
/*  1289 */       this.chkRTTurret.setSelected(false);
/*  1290 */       this.chkRTTurret.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1294 */     if (!this.chkFCSAIV.isEnabled()) {
/*       */       try {
/*  1296 */         this.CurMech.SetFCSArtemisIV(false);
/*       */       } catch (Exception e) {
/*  1298 */         Media.Messager(this, e.getMessage());
/*       */       }
/*  1300 */       this.chkFCSAIV.setSelected(false);
/*       */     }
/*  1302 */     else if (this.CurMech.UsingArtemisIV()) {
/*  1303 */       this.chkFCSAIV.setSelected(true);
/*       */     } else {
/*  1305 */       this.chkFCSAIV.setSelected(false);
/*       */     }
/*       */     
/*  1308 */     if (!this.chkFCSAV.isEnabled()) {
/*       */       try {
/*  1310 */         this.CurMech.SetFCSArtemisV(false);
/*       */       } catch (Exception e) {
/*  1312 */         Media.Messager(this, e.getMessage());
/*       */       }
/*  1314 */       this.chkFCSAV.setSelected(false);
/*       */     }
/*  1316 */     else if (this.CurMech.UsingArtemisV()) {
/*  1317 */       this.chkFCSAV.setSelected(true);
/*       */     } else {
/*  1319 */       this.chkFCSAV.setSelected(false);
/*       */     }
/*       */     
/*  1322 */     if (!this.chkFCSApollo.isEnabled()) {
/*       */       try {
/*  1324 */         this.CurMech.SetFCSApollo(false);
/*       */       } catch (Exception e) {
/*  1326 */         Media.Messager(this, e.getMessage());
/*       */       }
/*  1328 */       this.chkFCSApollo.setSelected(false);
/*       */     }
/*  1330 */     else if (this.CurMech.UsingApollo()) {
/*  1331 */       this.chkFCSApollo.setSelected(true);
/*       */     } else {
/*  1333 */       this.chkFCSApollo.setSelected(false);
/*       */     }
/*       */     
/*  1336 */     if (!this.chkSupercharger.isEnabled()) {
/*       */       try {
/*  1338 */         this.CurMech.GetLoadout().SetSupercharger(false, 0, -1);
/*       */       } catch (Exception e) {
/*  1340 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */       
/*  1343 */     } else if (this.CurMech.GetLoadout().HasSupercharger()) {
/*  1344 */       this.chkSupercharger.setSelected(true);
/*       */     } else {
/*  1346 */       this.chkSupercharger.setSelected(false);
/*       */     }
/*       */     
/*  1349 */     if (!this.chkHDTurret.isEnabled()) {
/*       */       try {
/*  1351 */         this.CurMech.GetLoadout().SetHDTurret(false, -1);
/*       */       } catch (Exception e) {
/*  1353 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */       
/*  1356 */     } else if (this.CurMech.GetLoadout().HasHDTurret()) {
/*  1357 */       this.chkHDTurret.setSelected(true);
/*       */     } else {
/*  1359 */       this.chkHDTurret.setSelected(false);
/*       */     }
/*       */     
/*  1362 */     if (!this.chkLTTurret.isEnabled()) {
/*       */       try {
/*  1364 */         this.CurMech.GetLoadout().SetLTTurret(false, -1);
/*       */       } catch (Exception e) {
/*  1366 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */       
/*  1369 */     } else if (this.CurMech.GetLoadout().HasLTTurret()) {
/*  1370 */       this.chkLTTurret.setSelected(true);
/*       */     } else {
/*  1372 */       this.chkLTTurret.setSelected(false);
/*       */     }
/*       */     
/*  1375 */     if (!this.chkRTTurret.isEnabled()) {
/*       */       try {
/*  1377 */         this.CurMech.GetLoadout().SetRTTurret(false, -1);
/*       */       } catch (Exception e) {
/*  1379 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */       
/*  1382 */     } else if (this.CurMech.GetLoadout().HasRTTurret()) {
/*  1383 */       this.chkRTTurret.setSelected(true);
/*       */     } else {
/*  1385 */       this.chkRTTurret.setSelected(false);
/*       */     }
/*       */     
/*  1388 */     if (!this.chkUseTC.isEnabled()) this.CurMech.UseTC(false, false);
/*  1389 */     if (!this.chkCTCASE.isEnabled()) this.CurMech.RemoveCTCase();
/*  1390 */     if (!this.chkLTCASE.isEnabled()) this.CurMech.RemoveLTCase();
/*  1391 */     if (!this.chkRTCASE.isEnabled()) this.CurMech.RemoveRTCase();
/*  1392 */     this.chkClanCASE.setSelected(this.CurMech.GetLoadout().IsUsingClanCASE());
/*       */     
/*  1394 */     if (this.CurMech.GetRulesLevel() >= 3) {
/*  1395 */       this.chkFractional.setEnabled(true);
/*       */     } else {
/*  1397 */       this.chkFractional.setEnabled(false);
/*  1398 */       this.CurMech.SetFractionalAccounting(false);
/*       */     }
/*  1400 */     this.chkFractional.setSelected(this.CurMech.UsingFractionalAccounting());
/*       */     
/*  1402 */     if (this.CurMech.IsOmnimech())
/*       */     {
/*       */ 
/*  1405 */       this.chkNullSig.setEnabled(false);
/*  1406 */       this.chkVoidSig.setEnabled(false);
/*  1407 */       this.chkBSPFD.setEnabled(false);
/*  1408 */       this.chkCLPS.setEnabled(false);
/*  1409 */       this.chkEnviroSealing.setEnabled(false);
/*  1410 */       this.chkEjectionSeat.setEnabled(false);
/*  1411 */       this.chkRAAES.setEnabled(false);
/*  1412 */       this.chkLAAES.setEnabled(false);
/*  1413 */       this.chkLegAES.setEnabled(false);
/*  1414 */       this.chkCommandConsole.setEnabled(false);
/*  1415 */       this.chkFHES.setEnabled(false);
/*  1416 */       this.chkTracks.setEnabled(false);
/*       */       
/*       */ 
/*  1419 */       if (this.CurMech.GetBaseLoadout().HasSupercharger()) {
/*  1420 */         this.chkSupercharger.setEnabled(false);
/*  1421 */         this.cmbSCLoc.setEnabled(false);
/*  1422 */         this.lblSupercharger.setEnabled(false);
/*       */       }
/*       */       
/*       */ 
/*  1426 */       if (this.CurMech.GetBaseLoadout().HasCTCASE()) {
/*  1427 */         this.chkCTCASE.setEnabled(false);
/*       */       }
/*  1429 */       if (this.CurMech.GetBaseLoadout().HasLTCASE()) {
/*  1430 */         this.chkLTCASE.setEnabled(false);
/*       */       }
/*  1432 */       if (this.CurMech.GetBaseLoadout().HasRTCASE()) {
/*  1433 */         this.chkRTCASE.setEnabled(false);
/*       */       }
/*       */       
/*  1436 */       if (this.CurMech.GetBaseLoadout().HasHDCASEII()) {
/*  1437 */         this.chkHDCASE2.setEnabled(false);
/*       */       }
/*  1439 */       if (this.CurMech.GetBaseLoadout().HasCTCASEII()) {
/*  1440 */         this.chkCTCASE2.setEnabled(false);
/*       */       }
/*  1442 */       if (this.CurMech.GetBaseLoadout().HasLTCASEII()) {
/*  1443 */         this.chkLTCASE2.setEnabled(false);
/*       */       }
/*  1445 */       if (this.CurMech.GetBaseLoadout().HasRTCASEII()) {
/*  1446 */         this.chkRTCASE2.setEnabled(false);
/*       */       }
/*  1448 */       if (this.CurMech.GetBaseLoadout().HasLACASEII()) {
/*  1449 */         this.chkLACASE2.setEnabled(false);
/*       */       }
/*  1451 */       if (this.CurMech.GetBaseLoadout().HasRACASEII()) {
/*  1452 */         this.chkRACASE2.setEnabled(false);
/*       */       }
/*  1454 */       if (this.CurMech.GetBaseLoadout().HasLLCASEII()) {
/*  1455 */         this.chkLLCASE2.setEnabled(false);
/*       */       }
/*  1457 */       if (this.CurMech.GetBaseLoadout().HasRLCASEII()) {
/*  1458 */         this.chkRLCASE2.setEnabled(false);
/*       */       }
/*  1460 */       if (this.CurMech.GetBaseLoadout().HasHDTurret()) {
/*  1461 */         this.chkHDTurret.setEnabled(false);
/*       */       }
/*  1463 */       if (this.CurMech.GetBaseLoadout().HasLTTurret()) {
/*  1464 */         this.chkLTTurret.setEnabled(false);
/*       */       }
/*  1466 */       if (this.CurMech.GetBaseLoadout().HasRTTurret()) {
/*  1467 */         this.chkRTTurret.setEnabled(false);
/*       */       }
/*       */     } else {
/*       */       try {
/*  1471 */         if (!this.chkNullSig.isEnabled()) this.CurMech.SetNullSig(false);
/*  1472 */         if (!this.chkVoidSig.isEnabled()) this.CurMech.SetVoidSig(false);
/*  1473 */         if (!this.chkBSPFD.isEnabled()) this.CurMech.SetBlueShield(false);
/*  1474 */         if (!this.chkCLPS.isEnabled()) this.CurMech.SetChameleon(false);
/*  1475 */         if (!this.chkEnviroSealing.isEnabled()) this.CurMech.SetEnviroSealing(false);
/*  1476 */         if (!this.chkLegAES.isEnabled()) this.CurMech.SetLegAES(false, null);
/*  1477 */         if (!this.chkRAAES.isEnabled()) this.CurMech.SetRAAES(false, -1);
/*  1478 */         if (!this.chkLAAES.isEnabled()) this.CurMech.SetLAAES(false, -1);
/*  1479 */         if (!this.chkCommandConsole.isEnabled()) this.CurMech.SetCommandConsole(false);
/*  1480 */         if (!this.chkFHES.isEnabled()) this.CurMech.SetFHES(false);
/*  1481 */         if (!this.chkTracks.isEnabled()) this.CurMech.SetTracks(false);
/*       */       }
/*       */       catch (Exception e) {
/*  1484 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcGyro()
/*       */   {
/*  1491 */     String OldVal = BuildLookupName(this.CurMech.GetGyro().GetCurrentState());
/*  1492 */     String LookupVal = (String)this.cmbGyroType.getSelectedItem();
/*  1493 */     if (OldVal.equals(LookupVal)) return;
/*  1494 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1496 */       this.CurMech.Visit(v);
/*  1497 */       if ((this.CurMech.GetCockpit().RequiresGyro()) && (this.CurMech.GetGyro().NumCrits() == 0))
/*  1498 */         throw new Exception("The selected cockpit requires a gyro.");
/*       */     } catch (Exception e) {
/*  1500 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1502 */         Media.Messager(this, "The new gyro type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous gyro type.");
/*  1503 */         this.CurMech.Visit(v);
/*  1504 */         this.cmbGyroType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1507 */         Media.Messager(this, "Fatal error while attempting to revert to the old gyro type:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1508 */         GetNewMech();
/*       */       }
/*       */     } }
/*       */   
/*       */   private JButton btnExportTextIcon;
/*       */   private JButton btnForceList;
/*       */   private JButton btnLoadImage;
/*       */   private JButton btnLockChassis;
/*  1516 */   private void RecalcCockpit() { String OldVal = this.CurMech.GetCockpit().LookupName();
/*  1517 */     String LookupVal = (String)this.cmbCockpitType.getSelectedItem();
/*  1518 */     if (OldVal.equals(LookupVal)) return;
/*  1519 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1521 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1523 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1525 */         Media.Messager(this, "The new cockpit type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous cockpit type.");
/*  1526 */         this.CurMech.Visit(v);
/*  1527 */         this.cmbCockpitType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1530 */         Media.Messager(this, "Fatal error while attempting to revert to the old cockpit type:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1531 */         GetNewMech();
/*       */       }
/*       */     }
/*       */     
/*  1535 */     if (!this.CurMech.GetGyro().LookupName().equals(this.cmbGyroType.getSelectedItem().toString())) {
/*  1536 */       this.cmbGyroType.setSelectedItem(this.CurMech.GetGyro().LookupName());
/*       */     }
/*       */     
/*  1539 */     if ((this.CurMech.GetCockpit().CanUseCommandConsole()) && (CommonTools.IsAllowed(this.CurMech.GetCommandConsole().GetAvailability(), this.CurMech))) {
/*  1540 */       this.chkCommandConsole.setEnabled(true);
/*  1541 */       this.chkCommandConsole.setSelected(this.CurMech.HasCommandConsole());
/*       */     } else {
/*  1543 */       this.chkCommandConsole.setEnabled(false);
/*  1544 */       this.chkCommandConsole.setSelected(false);
/*       */     }
/*  1546 */     if ((this.CurMech.CanUseFHES()) && (CommonTools.IsAllowed(this.CurMech.GetFHESAC(), this.CurMech))) {
/*  1547 */       this.chkFHES.setEnabled(true);
/*       */     } else {
/*  1549 */       this.chkFHES.setSelected(false);
/*  1550 */       this.chkFHES.setSelected(false);
/*       */     }
/*  1552 */     if (this.CurMech.GetCockpit().IsTorsoMounted())
/*       */     {
/*  1554 */       this.chkEjectionSeat.setEnabled(false);
/*  1555 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*  1557 */     if (this.CurMech.IsIndustrialmech()) {
/*  1558 */       this.chkEjectionSeat.setEnabled(true);
/*       */     } else {
/*  1560 */       this.chkEjectionSeat.setEnabled(false);
/*  1561 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*       */     
/*  1564 */     if (this.CurMech.GetLoadout().HasHDTurret()) {
/*       */       try {
/*  1566 */         this.CurMech.GetLoadout().SetHDTurret(false, -1);
/*       */       } catch (Exception e) {
/*  1568 */         Media.Messager("Fatal error trying to remove head turret.\nRestarting with new 'Mech.  Sorry.");
/*  1569 */         GetNewMech();
/*  1570 */         return;
/*       */       }
/*       */     }
/*  1573 */     this.chkHDTurret.setSelected(false);
/*  1574 */     this.chkHDTurret.setEnabled(false);
/*       */   }
/*       */   
/*       */   private void RecalcEnhancements()
/*       */   {
/*  1579 */     String OldVal = BuildLookupName(this.CurMech.GetPhysEnhance().GetCurrentState());
/*  1580 */     String LookupVal = (String)this.cmbPhysEnhance.getSelectedItem();
/*  1581 */     if (OldVal.equals(LookupVal)) return;
/*  1582 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1584 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1586 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1588 */         Media.Messager(this, "The new enhancement type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous enhancement.");
/*  1589 */         this.CurMech.Visit(v);
/*  1590 */         this.cmbPhysEnhance.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1593 */         Media.Messager(this, "Fatal error while attempting to revert to the old enhancement:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1594 */         GetNewMech();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcJumpJets()
/*       */   {
/*  1601 */     String OldVal = this.CurMech.GetJumpJets().LookupName();
/*  1602 */     String LookupVal = (String)this.cmbJumpJetType.getSelectedItem();
/*  1603 */     if (LookupVal == null) return;
/*  1604 */     if (OldVal.equals(LookupVal)) return;
/*  1605 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1607 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1609 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1611 */         Media.Messager(this, "The new jump jet type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous jump jet type.");
/*  1612 */         this.CurMech.Visit(v);
/*  1613 */         this.cmbJumpJetType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1616 */         Media.Messager(this, "Fatal error while attempting to revert to the old jump jets:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1617 */         GetNewMech();
/*  1618 */         return;
/*       */       }
/*       */     }
/*  1621 */     FixJJSpinnerModel();
/*       */   }
/*       */   
/*       */   private void RecalcHeatSinks()
/*       */   {
/*  1626 */     String OldVal = BuildLookupName(this.CurMech.GetHeatSinks().GetCurrentState());
/*  1627 */     String LookupVal = (String)this.cmbHeatSinkType.getSelectedItem();
/*  1628 */     if (OldVal.equals(LookupVal)) return;
/*  1629 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1631 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1633 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1635 */         Media.Messager(this, "The new heat sink type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous heat sink type.");
/*  1636 */         this.CurMech.Visit(v);
/*  1637 */         this.cmbHeatSinkType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1640 */         Media.Messager(this, "Fatal error while attempting to revert to the old heat sinks:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1641 */         GetNewMech();
/*  1642 */         return;
/*       */       }
/*       */     }
/*  1645 */     FixHeatSinkSpinnerModel();
/*       */   }
/*       */   
/*       */   private void RecalcIntStruc()
/*       */   {
/*  1650 */     String OldVal = BuildLookupName(this.CurMech.GetIntStruc().GetCurrentState());
/*  1651 */     String LookupVal = (String)this.cmbInternalType.getSelectedItem();
/*  1652 */     if (OldVal.equals(LookupVal)) return;
/*  1653 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1655 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1657 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1659 */         Media.Messager(this, "The new internal structure is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous internal structure.");
/*  1660 */         this.CurMech.Visit(v);
/*  1661 */         this.cmbInternalType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1664 */         Media.Messager(this, "Fatal error while attempting to revert to the old internal structure:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1665 */         GetNewMech();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcEngine()
/*       */   {
/*  1672 */     int OldFreeHS = this.CurMech.GetEngine().FreeHeatSinks();
/*       */     
/*       */ 
/*       */ 
/*  1676 */     String OldVal = BuildLookupName(this.CurMech.GetEngine().GetCurrentState());
/*  1677 */     String LookupVal = (String)this.cmbEngineType.getSelectedItem();
/*  1678 */     if (OldVal.equals(LookupVal)) return;
/*  1679 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1681 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1683 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1685 */         Media.Messager(this, "The new engine type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous engine.");
/*  1686 */         this.CurMech.Visit(v);
/*  1687 */         this.cmbEngineType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1690 */         Media.Messager(this, "Fatal error while attempting to revert to the old engine:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1691 */         GetNewMech();
/*  1692 */         return;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1698 */     if (this.CurMech.GetEngine().FreeHeatSinks() != OldFreeHS)
/*       */     {
/*  1700 */       this.CurMech.GetHeatSinks().SetNumHS(this.CurMech.GetEngine().FreeHeatSinks());
/*       */       
/*  1702 */       this.CurMech.GetHeatSinks().ReCalculate();
/*  1703 */       this.spnNumberOfHS.setModel(new SpinnerNumberModel(this.CurMech
/*  1704 */         .GetHeatSinks().GetNumHS(), this.CurMech.GetEngine().FreeHeatSinks(), 65, 1));
/*       */     }
/*       */     
/*       */ 
/*  1708 */     if (this.CurMech.GetEngine().IsNuclear()) {
/*  1709 */       this.lblSumPAmps.setVisible(false);
/*  1710 */       this.txtSumPAmpsTon.setVisible(false);
/*  1711 */       this.txtSumPAmpsACode.setVisible(false);
/*       */     } else {
/*  1713 */       this.lblSumPAmps.setVisible(true);
/*  1714 */       this.txtSumPAmpsTon.setVisible(true);
/*  1715 */       this.txtSumPAmpsACode.setVisible(true);
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcArmor()
/*       */   {
/*  1721 */     String OldVal = BuildLookupName(this.CurMech.GetArmor().GetCurrentState());
/*  1722 */     String LookupVal = (String)this.cmbArmorType.getSelectedItem();
/*  1723 */     if (OldVal.equals(LookupVal)) return;
/*  1724 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1726 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1728 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1730 */         Media.Messager(this, "The new armor type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous armor.");
/*  1731 */         this.CurMech.Visit(v);
/*  1732 */         this.cmbArmorType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1735 */         Media.Messager(this, "Fatal error while attempting to revert to the old armor:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1736 */         GetNewMech();
/*       */       }
/*       */     }
/*  1739 */     if ((this.CurMech.GetArmor().IsStealth()) && 
/*  1740 */       (!AddECM())) {
/*  1741 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1743 */         Media.Messager(this, "No ECM Suite was available for this armor type!\nReverting to the previous armor.");
/*  1744 */         this.CurMech.Visit(v);
/*  1745 */         this.cmbArmorType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e) {
/*  1748 */         Media.Messager(this, "Fatal error while attempting to revert to the old armor:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1749 */         GetNewMech();
/*       */       }
/*       */     }
/*       */     
/*  1753 */     SetPatchworkArmor();
/*       */   }
/*       */   
/*       */   private void SetPatchworkArmor() {
/*  1757 */     if (this.CurMech.GetArmor().IsPatchwork()) {
/*  1758 */       this.pnlPatchworkChoosers.setVisible(true);
/*  1759 */       BuildPatchworkChoosers();
/*  1760 */       if (this.CurMech.IsQuad()) {
/*  1761 */         this.lblPWLALoc.setText("FLL Armor: ");
/*  1762 */         this.lblPWRALoc.setText("FRL Armor: ");
/*  1763 */         this.lblPWLLLoc.setText("RLL Armor: ");
/*  1764 */         this.lblPWRLLoc.setText("RRL Armor: ");
/*       */       } else {
/*  1766 */         this.lblPWLALoc.setText("LA Armor: ");
/*  1767 */         this.lblPWRALoc.setText("RA Armor: ");
/*  1768 */         this.lblPWLLLoc.setText("LL Armor: ");
/*  1769 */         this.lblPWRLLoc.setText("RL Armor: ");
/*       */       }
/*  1771 */       this.cmbPWHDType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetHDArmorType()));
/*  1772 */       this.cmbPWCTType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetCTArmorType()));
/*  1773 */       this.cmbPWLTType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetLTArmorType()));
/*  1774 */       this.cmbPWRTType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetRTArmorType()));
/*  1775 */       this.cmbPWLAType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetLAArmorType()));
/*  1776 */       this.cmbPWRAType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetRAArmorType()));
/*  1777 */       this.cmbPWLLType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetLLArmorType()));
/*  1778 */       this.cmbPWRLType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetRLArmorType()));
/*       */     } else {
/*  1780 */       this.pnlPatchworkChoosers.setVisible(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcPatchworkArmor(int Loc) {
/*  1785 */     visitors.VArmorSetPatchworkLocation LCVis = new visitors.VArmorSetPatchworkLocation();
/*  1786 */     LCVis.SetLocation(Loc);
/*  1787 */     if (this.CurMech.GetBaseTechbase() == 1) {
/*  1788 */       LCVis.SetClan(false);
/*       */     }
/*  1790 */     switch (Loc) {
/*       */     case 0: 
/*  1792 */       LCVis.SetPatchworkType((String)this.cmbPWHDType.getSelectedItem());
/*  1793 */       break;
/*       */     case 1: 
/*  1795 */       LCVis.SetPatchworkType((String)this.cmbPWCTType.getSelectedItem());
/*  1796 */       break;
/*       */     case 2: 
/*  1798 */       LCVis.SetPatchworkType((String)this.cmbPWLTType.getSelectedItem());
/*  1799 */       break;
/*       */     case 3: 
/*  1801 */       LCVis.SetPatchworkType((String)this.cmbPWRTType.getSelectedItem());
/*  1802 */       break;
/*       */     case 4: 
/*  1804 */       LCVis.SetPatchworkType((String)this.cmbPWLAType.getSelectedItem());
/*  1805 */       break;
/*       */     case 5: 
/*  1807 */       LCVis.SetPatchworkType((String)this.cmbPWRAType.getSelectedItem());
/*  1808 */       break;
/*       */     case 6: 
/*  1810 */       LCVis.SetPatchworkType((String)this.cmbPWLLType.getSelectedItem());
/*  1811 */       break;
/*       */     case 7: 
/*  1813 */       LCVis.SetPatchworkType((String)this.cmbPWRLType.getSelectedItem());
/*       */     }
/*       */     try
/*       */     {
/*  1817 */       LCVis.Visit(this.CurMech);
/*       */     } catch (Exception e) {
/*  1819 */       Media.Messager(this, e.getMessage());
/*  1820 */       switch (Loc) {
/*       */       case 0: 
/*  1822 */         this.cmbPWHDType.setSelectedItem(this.CurMech.GetArmor().GetHDArmorType().LookupName());
/*       */       }
/*       */     }
/*  1825 */     this.cmbPWCTType.setSelectedItem(this.CurMech.GetArmor().GetCTArmorType().LookupName());
/*  1826 */     return;
/*       */     
/*  1828 */     this.cmbPWLTType.setSelectedItem(this.CurMech.GetArmor().GetLTArmorType().LookupName());
/*  1829 */     return;
/*       */     
/*  1831 */     this.cmbPWRTType.setSelectedItem(this.CurMech.GetArmor().GetRTArmorType().LookupName());
/*  1832 */     return;
/*       */     
/*  1834 */     this.cmbPWLAType.setSelectedItem(this.CurMech.GetArmor().GetLAArmorType().LookupName());
/*  1835 */     return;
/*       */     
/*  1837 */     this.cmbPWRAType.setSelectedItem(this.CurMech.GetArmor().GetRAArmorType().LookupName());
/*  1838 */     return;
/*       */     
/*  1840 */     this.cmbPWLLType.setSelectedItem(this.CurMech.GetArmor().GetLLArmorType().LookupName());
/*  1841 */     return;
/*       */     
/*  1843 */     this.cmbPWRLType.setSelectedItem(this.CurMech.GetArmor().GetRLArmorType().LookupName());
/*       */   }
/*       */   
/*       */   private JButton btnMaxArmor;
/*       */   private JButton btnNewIcon;
/*       */   private JButton btnOpen;
/*       */   private JButton btnOptionsIcon;
/*       */   
/*  1851 */   private void RecalcEquipment() { boolean clan = false;
/*  1852 */     switch (this.CurMech.GetTechbase())
/*       */     {
/*       */     case 1: 
/*       */     case 2: 
/*  1856 */       clan = true;
/*       */     }
/*  1858 */     if (this.chkCTCASE.isSelected()) {
/*       */       try {
/*  1860 */         this.CurMech.AddCTCase();
/*       */       } catch (Exception e) {
/*  1862 */         Media.Messager(this, e.getMessage());
/*  1863 */         this.chkCTCASE.setSelected(false);
/*       */       }
/*       */     }
/*  1866 */     if (this.chkLTCASE.isSelected()) {
/*       */       try {
/*  1868 */         this.CurMech.AddLTCase();
/*       */       } catch (Exception e) {
/*  1870 */         Media.Messager(this, e.getMessage());
/*  1871 */         this.chkLTCASE.setSelected(false);
/*       */       }
/*       */     }
/*  1874 */     if (this.chkRTCASE.isSelected()) {
/*       */       try {
/*  1876 */         this.CurMech.AddRTCase();
/*       */       } catch (Exception e) {
/*  1878 */         Media.Messager(this, e.getMessage());
/*  1879 */         this.chkRTCASE.setSelected(false);
/*       */       }
/*       */     }
/*       */     
/*  1883 */     if (this.chkHDCASE2.isSelected()) {
/*       */       try {
/*  1885 */         if (!this.CurMech.GetLoadout().HasHDCASEII()) {
/*  1886 */           this.CurMech.GetLoadout().SetHDCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1889 */         Media.Messager(this, e.getMessage());
/*  1890 */         this.chkHDCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1893 */     if (this.chkCTCASE2.isSelected()) {
/*       */       try {
/*  1895 */         if (!this.CurMech.GetLoadout().HasCTCASEII()) {
/*  1896 */           this.CurMech.GetLoadout().SetCTCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1899 */         Media.Messager(this, e.getMessage());
/*  1900 */         this.chkCTCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1903 */     if (this.chkLTCASE2.isSelected()) {
/*       */       try {
/*  1905 */         if (!this.CurMech.GetLoadout().HasLTCASEII()) {
/*  1906 */           this.CurMech.GetLoadout().SetLTCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1909 */         Media.Messager(this, e.getMessage());
/*  1910 */         this.chkLTCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1913 */     if (this.chkRTCASE2.isSelected()) {
/*       */       try {
/*  1915 */         if (!this.CurMech.GetLoadout().HasRTCASEII()) {
/*  1916 */           this.CurMech.GetLoadout().SetRTCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1919 */         Media.Messager(this, e.getMessage());
/*  1920 */         this.chkRTCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1923 */     if (this.chkLACASE2.isSelected()) {
/*       */       try {
/*  1925 */         if (!this.CurMech.GetLoadout().HasLACASEII()) {
/*  1926 */           this.CurMech.GetLoadout().SetLACASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1929 */         Media.Messager(this, e.getMessage());
/*  1930 */         this.chkLACASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1933 */     if (this.chkRACASE2.isSelected()) {
/*       */       try {
/*  1935 */         if (!this.CurMech.GetLoadout().HasRACASEII()) {
/*  1936 */           this.CurMech.GetLoadout().SetRACASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1939 */         Media.Messager(this, e.getMessage());
/*  1940 */         this.chkRACASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1943 */     if (this.chkLLCASE2.isSelected()) {
/*       */       try {
/*  1945 */         if (!this.CurMech.GetLoadout().HasLLCASEII()) {
/*  1946 */           this.CurMech.GetLoadout().SetLLCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1949 */         Media.Messager(this, e.getMessage());
/*  1950 */         this.chkLLCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1953 */     if (this.chkRLCASE2.isSelected()) {
/*       */       try {
/*  1955 */         if (!this.CurMech.GetLoadout().HasRLCASEII()) {
/*  1956 */           this.CurMech.GetLoadout().SetRLCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1959 */         Media.Messager(this, e.getMessage());
/*  1960 */         this.chkRLCASE2.setSelected(false);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void CheckOmnimech()
/*       */   {
/*  1967 */     if (CommonTools.IsAllowed(this.CurMech.GetOmniMechAvailability(), this.CurMech)) {
/*  1968 */       this.chkOmnimech.setEnabled(true);
/*       */     } else {
/*  1970 */       this.chkOmnimech.setEnabled(false);
/*  1971 */       this.chkOmnimech.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1976 */     if (this.chkOmnimech.isEnabled()) {
/*  1977 */       if (this.chkOmnimech.isSelected()) {
/*  1978 */         this.btnLockChassis.setEnabled(true);
/*       */       } else {
/*  1980 */         this.btnLockChassis.setEnabled(false);
/*       */       }
/*       */     } else {
/*  1983 */       this.btnLockChassis.setEnabled(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private void SaveOmniFluffInfo() {
/*  1988 */     if (this.SetSource) {
/*  1989 */       this.CurMech.SetSource(this.txtSource.getText());
/*  1990 */       this.CurMech.SetEra(this.cmbMechEra.getSelectedIndex());
/*  1991 */       this.CurMech.SetProductionEra(this.cmbProductionEra.getSelectedIndex());
/*  1992 */       this.CurMech.SetYearRestricted(this.chkYearRestrict.isSelected());
/*       */       try {
/*  1994 */         this.CurMech.SetYear(Integer.parseInt(this.txtProdYear.getText()), this.chkYearRestrict.isSelected());
/*       */       }
/*       */       catch (Exception e) {
/*  1997 */         switch (this.cmbMechEra.getSelectedIndex()) {
/*       */         case 0: 
/*  1999 */           this.CurMech.SetYear(2750, false);
/*       */         }
/*       */       }
/*  2002 */       this.CurMech.SetYear(3025, false);
/*  2003 */       return;
/*       */       
/*  2005 */       this.CurMech.SetYear(3070, false);
/*  2006 */       return;
/*       */       
/*  2008 */       this.CurMech.SetYear(3132, false);
/*  2009 */       return;
/*       */       
/*  2011 */       this.CurMech.SetYear(0, false);
/*       */     }
/*       */   }
/*       */   
/*       */   private JButton btnPostToS7;
/*       */   private JButton btnPrintIcon;
/*       */   private JButton btnPrintPreview;
/*       */   private JButton btnRemainingArmor;
/*  2019 */   private void LoadOmniFluffInfo() { this.cmbRulesLevel.setSelectedIndex(this.CurMech.GetRulesLevel());
/*  2020 */     this.cmbMechEra.setSelectedIndex(this.CurMech.GetEra());
/*  2021 */     this.cmbProductionEra.setSelectedIndex(this.CurMech.GetProductionEra());
/*  2022 */     this.txtSource.setText(this.CurMech.GetSource());
/*  2023 */     this.txtProdYear.setText("" + this.CurMech.GetYear());
/*  2024 */     BuildTechBaseSelector();
/*       */   }
/*       */   
/*       */   private void RefreshInternalPoints() {
/*  2028 */     if (this.Prefs.getBoolean("UseMaxArmorInstead", false)) {
/*  2029 */       this.lblHDHeader.setText("Max");
/*  2030 */       this.lblCTHeader.setText("Max");
/*  2031 */       this.lblLTHeader.setText("Max");
/*  2032 */       this.lblRTHeader.setText("Max");
/*  2033 */       this.lblLAHeader.setText("Max");
/*  2034 */       this.lblRAHeader.setText("Max");
/*  2035 */       this.lblLLHeader.setText("Max");
/*  2036 */       this.lblRLHeader.setText("Max");
/*  2037 */       this.lblHDIntPts.setText("" + this.CurMech.GetIntStruc().GetHeadPoints() * 3);
/*  2038 */       this.lblCTIntPts.setText("" + this.CurMech.GetIntStruc().GetCTPoints() * 2);
/*  2039 */       this.lblLTIntPts.setText("" + this.CurMech.GetIntStruc().GetSidePoints() * 2);
/*  2040 */       this.lblRTIntPts.setText("" + this.CurMech.GetIntStruc().GetSidePoints() * 2);
/*  2041 */       this.lblLAIntPts.setText("" + this.CurMech.GetIntStruc().GetArmPoints() * 2);
/*  2042 */       this.lblRAIntPts.setText("" + this.CurMech.GetIntStruc().GetArmPoints() * 2);
/*  2043 */       this.lblLLIntPts.setText("" + this.CurMech.GetIntStruc().GetLegPoints() * 2);
/*  2044 */       this.lblRLIntPts.setText("" + this.CurMech.GetIntStruc().GetLegPoints() * 2);
/*       */     } else {
/*  2046 */       this.lblHDHeader.setText("Internal");
/*  2047 */       this.lblCTHeader.setText("Internal");
/*  2048 */       this.lblLTHeader.setText("Internal");
/*  2049 */       this.lblRTHeader.setText("Internal");
/*  2050 */       this.lblLAHeader.setText("Internal");
/*  2051 */       this.lblRAHeader.setText("Internal");
/*  2052 */       this.lblLLHeader.setText("Internal");
/*  2053 */       this.lblRLHeader.setText("Internal");
/*  2054 */       this.lblHDIntPts.setText("" + this.CurMech.GetIntStruc().GetHeadPoints());
/*  2055 */       this.lblCTIntPts.setText("" + this.CurMech.GetIntStruc().GetCTPoints());
/*  2056 */       this.lblLTIntPts.setText("" + this.CurMech.GetIntStruc().GetSidePoints());
/*  2057 */       this.lblRTIntPts.setText("" + this.CurMech.GetIntStruc().GetSidePoints());
/*  2058 */       this.lblLAIntPts.setText("" + this.CurMech.GetIntStruc().GetArmPoints());
/*  2059 */       this.lblRAIntPts.setText("" + this.CurMech.GetIntStruc().GetArmPoints());
/*  2060 */       this.lblLLIntPts.setText("" + this.CurMech.GetIntStruc().GetLegPoints());
/*  2061 */       this.lblRLIntPts.setText("" + this.CurMech.GetIntStruc().GetLegPoints());
/*       */     }
/*       */   }
/*       */   
/*       */   private void RefreshSummary()
/*       */   {
/*  2067 */     this.txtSumIntTon.setText("" + this.CurMech.GetIntStruc().GetTonnage());
/*  2068 */     this.txtSumEngTon.setText("" + this.CurMech.GetEngine().GetTonnage());
/*  2069 */     this.txtSumGyrTon.setText("" + this.CurMech.GetGyro().GetTonnage());
/*  2070 */     this.txtSumCocTon.setText("" + this.CurMech.GetCockpit().GetTonnage());
/*  2071 */     this.txtSumEnhTon.setText("" + this.CurMech.GetPhysEnhance().GetTonnage());
/*  2072 */     this.txtSumHSTon.setText("" + this.CurMech.GetHeatSinks().GetTonnage());
/*  2073 */     this.txtSumJJTon.setText("" + this.CurMech.GetJumpJets().GetTonnage());
/*  2074 */     this.txtSumArmorTon.setText("" + this.CurMech.GetArmor().GetTonnage());
/*  2075 */     this.txtSumPAmpsTon.setText("" + this.CurMech.GetLoadout().GetPowerAmplifier().GetTonnage());
/*  2076 */     this.txtSumIntCrt.setText("" + this.CurMech.GetIntStruc().NumCrits());
/*  2077 */     this.txtSumEngCrt.setText("" + this.CurMech.GetEngine().ReportCrits());
/*  2078 */     this.txtSumGyrCrt.setText("" + this.CurMech.GetGyro().NumCrits());
/*  2079 */     this.txtSumCocCrt.setText("" + this.CurMech.GetCockpit().ReportCrits());
/*  2080 */     this.txtSumEnhCrt.setText("" + this.CurMech.GetPhysEnhance().NumCrits());
/*  2081 */     this.txtSumHSCrt.setText("" + this.CurMech.GetHeatSinks().NumCrits());
/*  2082 */     this.txtSumJJCrt.setText("" + this.CurMech.GetJumpJets().ReportCrits());
/*  2083 */     this.txtSumArmorCrt.setText("" + this.CurMech.GetArmor().NumCrits());
/*  2084 */     this.txtSumIntACode.setText(this.CurMech.GetIntStruc().GetAvailability().GetBestCombinedCode());
/*  2085 */     this.txtSumEngACode.setText(this.CurMech.GetEngine().GetAvailability().GetBestCombinedCode());
/*  2086 */     this.txtSumGyrACode.setText(this.CurMech.GetGyro().GetAvailability().GetBestCombinedCode());
/*  2087 */     this.txtSumCocACode.setText(this.CurMech.GetCockpit().GetAvailability().GetBestCombinedCode());
/*  2088 */     this.txtSumHSACode.setText(this.CurMech.GetHeatSinks().GetAvailability().GetBestCombinedCode());
/*  2089 */     this.txtSumEnhACode.setText(this.CurMech.GetPhysEnhance().GetAvailability().GetBestCombinedCode());
/*  2090 */     this.txtSumJJACode.setText(this.CurMech.GetJumpJets().GetAvailability().GetBestCombinedCode());
/*  2091 */     this.txtSumPAmpsACode.setText(this.CurMech.GetLoadout().GetPowerAmplifier().GetAvailability().GetBestCombinedCode());
/*       */     
/*       */ 
/*  2094 */     this.lblArmorPoints.setText(this.CurMech.GetArmor().GetArmorValue() + " of " + this.CurMech.GetArmor().GetMaxArmor() + " Armor Points");
/*  2095 */     this.lblArmorCoverage.setText(this.CurMech.GetArmor().GetCoverage() + "% Coverage");
/*  2096 */     this.lblArmorTonsWasted.setText(this.CurMech.GetArmor().GetWastedTonnage() + " Tons Wasted");
/*  2097 */     this.lblAVInLot.setText(this.CurMech.GetArmor().GetWastedAV() + " Points Left In This 1/2 Ton Lot");
/*       */     
/*       */ 
/*  2100 */     battleforce.BattleForceStats bfs = new battleforce.BattleForceStats(this.CurMech);
/*       */     
/*  2102 */     this.lblBFMV.setText(bfs.getMovement());
/*  2103 */     this.lblBFWt.setText(bfs.getWeight() + "");
/*  2104 */     this.lblBFArmor.setText(bfs.getArmor() + "");
/*  2105 */     this.lblBFStructure.setText(bfs.getInternal() + "");
/*  2106 */     this.lblBFPoints.setText("" + bfs.getPointValue());
/*       */     
/*       */ 
/*  2109 */     this.lblBFShort.setText("" + bfs.getShort());
/*  2110 */     this.lblBFMedium.setText("" + bfs.getMedium());
/*  2111 */     this.lblBFLong.setText("" + bfs.getLong());
/*  2112 */     this.lblBFExtreme.setText("" + bfs.getExtreme());
/*  2113 */     this.lblBFOV.setText("" + bfs.getOverheat());
/*       */     
/*  2115 */     this.lblBFSA.setText(bfs.getAbilitiesString());
/*       */     
/*  2117 */     this.jTextAreaBFConversion.setText(bfs.getBFConversionData());
/*       */   }
/*       */   
/*       */ 
/*       */   public void RefreshInfoPane()
/*       */   {
/*  2123 */     if (this.CurMech.GetCurrentTons() > this.CurMech.GetTonnage()) {
/*  2124 */       this.txtInfoTonnage.setForeground(this.RedCol);
/*  2125 */       this.txtInfoFreeTons.setForeground(this.RedCol);
/*       */     } else {
/*  2127 */       this.txtInfoTonnage.setForeground(this.GreenCol);
/*  2128 */       this.txtInfoFreeTons.setForeground(this.GreenCol);
/*       */     }
/*  2130 */     if (this.CurMech.GetLoadout().FreeCrits() - this.CurMech.GetLoadout().UnplacedCrits() < 0) {
/*  2131 */       this.txtInfoFreeCrits.setForeground(this.RedCol);
/*  2132 */       this.txtInfoUnplaced.setForeground(this.RedCol);
/*       */     } else {
/*  2134 */       this.txtInfoFreeCrits.setForeground(this.GreenCol);
/*  2135 */       this.txtInfoUnplaced.setForeground(this.GreenCol);
/*       */     }
/*       */     
/*  2138 */     if (this.CurMech.UsingFractionalAccounting()) {
/*  2139 */       this.txtInfoTonnage.setText("Tons: " + CommonTools.RoundFractionalTons(this.CurMech.GetCurrentTons()));
/*  2140 */       this.txtInfoFreeTons.setText("Free Tons: " + CommonTools.RoundFractionalTons(this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons()));
/*       */     } else {
/*  2142 */       this.txtInfoTonnage.setText("Tons: " + this.CurMech.GetCurrentTons());
/*  2143 */       this.txtInfoFreeTons.setText("Free Tons: " + (this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons()));
/*       */     }
/*  2145 */     this.txtInfoMaxHeat.setText("Max Heat: " + this.CurMech.GetMaxHeat());
/*  2146 */     this.txtInfoHeatDiss.setText("Heat Dissipation: " + this.CurMech.GetHeatSinks().TotalDissipation());
/*  2147 */     this.txtInfoFreeCrits.setText("Free Crits: " + this.CurMech.GetLoadout().FreeCrits());
/*  2148 */     this.txtInfoUnplaced.setText("Unplaced Crits: " + this.CurMech.GetLoadout().UnplacedCrits());
/*  2149 */     this.txtInfoBattleValue.setText("BV: " + String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/*  2150 */     this.txtInfoCost.setText("Cost: " + String.format("%1$,.0f", new Object[] { Double.valueOf(Math.floor(this.CurMech.GetTotalCost() + 0.5D)) }));
/*  2151 */     this.txtEngineRating.setText("" + this.CurMech.GetEngine().GetRating());
/*  2152 */     this.txtChatInfo.setText(" " + this.CurMech.GetChatInfo());
/*  2153 */     this.txtChatInfo.setCaretPosition(0);
/*       */     
/*       */ 
/*  2156 */     String temp = "Max W/R/J/B: ";
/*  2157 */     temp = temp + this.CurMech.GetAdjustedWalkingMP(false, true) + "/";
/*  2158 */     temp = temp + this.CurMech.GetAdjustedRunningMP(false, true) + "/";
/*  2159 */     temp = temp + this.CurMech.GetAdjustedJumpingMP(false) + "/";
/*  2160 */     temp = temp + this.CurMech.GetAdjustedBoosterMP(false);
/*  2161 */     this.lblMoveSummary.setText(temp);
/*       */     
/*       */ 
/*  2164 */     this.lstCritsToPlace.setListData(this.CurMech.GetLoadout().GetQueue().toArray());
/*  2165 */     this.lstCritsToPlace.repaint();
/*       */     
/*       */ 
/*  2168 */     this.lstHDCrits.repaint();
/*  2169 */     this.lstCTCrits.repaint();
/*  2170 */     this.lstLTCrits.repaint();
/*  2171 */     this.lstRTCrits.repaint();
/*  2172 */     this.lstLACrits.repaint();
/*  2173 */     this.lstRACrits.repaint();
/*  2174 */     this.lstLLCrits.repaint();
/*  2175 */     this.lstRLCrits.repaint();
/*       */     
/*  2177 */     javax.swing.table.AbstractTableModel m = (javax.swing.table.AbstractTableModel)this.tblWeaponManufacturers.getModel();
/*  2178 */     m.fireTableDataChanged();
/*       */     
/*  2180 */     CheckEquipment();
/*       */     
/*  2182 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   public void QuickSave() {
/*  2186 */     File saveFile = GetSaveFile("ssw", this.Prefs.get("LastOpenDirectory", ""), true, false);
/*  2187 */     if (saveFile != null)
/*       */     {
/*  2189 */       String curLoadout = this.CurMech.GetLoadout().GetName();
/*  2190 */       filehandlers.MechWriter XMLw = new filehandlers.MechWriter(this.CurMech);
/*       */       try {
/*  2192 */         XMLw.WriteXML(saveFile.getCanonicalPath());
/*  2193 */         this.CurMech.SetCurLoadout(curLoadout);
/*       */       }
/*       */       catch (IOException e) {}
/*       */     }
/*       */     else {
/*  2198 */       mnuSaveActionPerformed(null);
/*       */     }
/*       */   }
/*       */   
/*       */   private void CheckEquipment()
/*       */   {
/*  2204 */     if (this.CurMech.UsingArtemisIV()) {
/*  2205 */       this.chkFCSAIV.setSelected(true);
/*       */     } else {
/*  2207 */       this.chkFCSAIV.setSelected(false);
/*       */     }
/*  2209 */     if (this.CurMech.UsingArtemisV()) {
/*  2210 */       this.chkFCSAV.setSelected(true);
/*       */     } else {
/*  2212 */       this.chkFCSAV.setSelected(false);
/*       */     }
/*  2214 */     if (this.CurMech.UsingApollo()) {
/*  2215 */       this.chkFCSApollo.setSelected(true);
/*       */     } else {
/*  2217 */       this.chkFCSApollo.setSelected(false);
/*       */     }
/*       */     
/*  2220 */     if (this.CurMech.UsingTC()) {
/*  2221 */       this.chkUseTC.setSelected(true);
/*       */     } else {
/*  2223 */       this.chkUseTC.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/*  2227 */     if (this.CurMech.HasCTCase()) {
/*  2228 */       this.chkCTCASE.setSelected(true);
/*       */     } else {
/*  2230 */       this.chkCTCASE.setSelected(false);
/*       */     }
/*  2232 */     if (this.CurMech.HasLTCase()) {
/*  2233 */       this.chkLTCASE.setSelected(true);
/*       */     } else {
/*  2235 */       this.chkLTCASE.setSelected(false);
/*       */     }
/*  2237 */     if (this.CurMech.HasRTCase()) {
/*  2238 */       this.chkRTCASE.setSelected(true);
/*       */     } else {
/*  2240 */       this.chkRTCASE.setSelected(false);
/*       */     }
/*       */     
/*  2243 */     if (this.CurMech.GetLoadout().HasHDCASEII()) {
/*  2244 */       this.chkHDCASE2.setSelected(true);
/*       */     } else {
/*  2246 */       this.chkHDCASE2.setSelected(false);
/*       */     }
/*  2248 */     if (this.CurMech.GetLoadout().HasCTCASEII()) {
/*  2249 */       this.chkCTCASE2.setSelected(true);
/*       */     } else {
/*  2251 */       this.chkCTCASE2.setSelected(false);
/*       */     }
/*  2253 */     if (this.CurMech.GetLoadout().HasLTCASEII()) {
/*  2254 */       this.chkLTCASE2.setSelected(true);
/*       */     } else {
/*  2256 */       this.chkLTCASE2.setSelected(false);
/*       */     }
/*  2258 */     if (this.CurMech.GetLoadout().HasRTCASEII()) {
/*  2259 */       this.chkRTCASE2.setSelected(true);
/*       */     } else {
/*  2261 */       this.chkRTCASE2.setSelected(false);
/*       */     }
/*  2263 */     if (this.CurMech.GetLoadout().HasLACASEII()) {
/*  2264 */       this.chkLACASE2.setSelected(true);
/*       */     } else {
/*  2266 */       this.chkLACASE2.setSelected(false);
/*       */     }
/*  2268 */     if (this.CurMech.GetLoadout().HasRACASEII()) {
/*  2269 */       this.chkRACASE2.setSelected(true);
/*       */     } else {
/*  2271 */       this.chkRACASE2.setSelected(false);
/*       */     }
/*  2273 */     if (this.CurMech.GetLoadout().HasLLCASEII()) {
/*  2274 */       this.chkLLCASE2.setSelected(true);
/*       */     } else {
/*  2276 */       this.chkLLCASE2.setSelected(false);
/*       */     }
/*  2278 */     if (this.CurMech.GetLoadout().HasRLCASEII()) {
/*  2279 */       this.chkRLCASE2.setSelected(true);
/*       */     } else {
/*  2281 */       this.chkRLCASE2.setSelected(false);
/*       */     }
/*       */     
/*  2284 */     if (this.CurMech.GetLoadout().HasHDTurret()) {
/*  2285 */       this.chkHDTurret.setSelected(true);
/*       */     } else {
/*  2287 */       this.chkHDTurret.setSelected(false);
/*       */     }
/*  2289 */     if (this.CurMech.GetLoadout().HasLTTurret()) {
/*  2290 */       this.chkLTTurret.setSelected(true);
/*       */     } else {
/*  2292 */       this.chkLTTurret.setSelected(false);
/*       */     }
/*  2294 */     if (this.CurMech.GetLoadout().HasRTTurret()) {
/*  2295 */       this.chkRTTurret.setSelected(true);
/*       */     } else {
/*  2297 */       this.chkRTTurret.setSelected(false);
/*       */     }
/*       */     
/*  2300 */     if (this.CurMech.GetLoadout().HasSupercharger()) {
/*  2301 */       this.chkSupercharger.setSelected(true);
/*  2302 */       this.cmbSCLoc.setSelectedItem(filehandlers.FileCommon.EncodeLocation(this.CurMech.GetLoadout().Find(this.CurMech.GetLoadout().GetSupercharger()), false));
/*       */     } else {
/*  2304 */       this.chkSupercharger.setSelected(false);
/*       */     }
/*  2306 */     if (this.CurMech.GetLoadout().IsUsingClanCASE()) {
/*  2307 */       this.chkClanCASE.setSelected(true);
/*       */     } else {
/*  2309 */       this.chkClanCASE.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/*  2313 */     if (!this.CurMech.IsQuad()) {
/*  2314 */       if (this.CurMech.GetActuators().LeftLowerInstalled()) {
/*  2315 */         this.chkLALowerArm.setSelected(true);
/*       */       } else {
/*  2317 */         this.chkLALowerArm.setSelected(false);
/*       */       }
/*  2319 */       if (this.CurMech.GetActuators().LeftHandInstalled()) {
/*  2320 */         this.chkLAHand.setSelected(true);
/*       */       } else {
/*  2322 */         this.chkLAHand.setSelected(false);
/*       */       }
/*  2324 */       if (this.CurMech.GetActuators().RightLowerInstalled()) {
/*  2325 */         this.chkRALowerArm.setSelected(true);
/*       */       } else {
/*  2327 */         this.chkRALowerArm.setSelected(false);
/*       */       }
/*  2329 */       if (this.CurMech.GetActuators().RightHandInstalled()) {
/*  2330 */         this.chkRAHand.setSelected(true);
/*       */       } else {
/*  2332 */         this.chkRAHand.setSelected(false);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void CheckAES()
/*       */   {
/*  2339 */     if (this.chkLegAES.isSelected()) {
/*  2340 */       if (this.CurMech.GetTonnage() > 55)
/*       */       {
/*  2342 */         this.chkLegAES.setSelected(false);
/*       */         try {
/*  2344 */           this.CurMech.SetLegAES(false, null);
/*       */         } catch (Exception e) {
/*  2346 */           System.err.println(e.getMessage());
/*       */         }
/*       */       }
/*       */       else {
/*       */         try {
/*  2351 */           this.CurMech.SetLegAES(false, null);
/*  2352 */           this.CurMech.SetLegAES(true, null);
/*       */         } catch (Exception e) {
/*  2354 */           this.chkLegAES.setSelected(false);
/*       */         }
/*       */       }
/*       */     }
/*  2358 */     if (this.chkRAAES.isSelected()) {
/*  2359 */       if (this.CurMech.IsQuad()) {
/*  2360 */         this.chkRAAES.setSelected(false);
/*       */       } else {
/*  2362 */         int index = this.CurMech.GetLoadout().FindIndex(this.CurMech.GetRAAES()).Index;
/*       */         try {
/*  2364 */           this.CurMech.SetRAAES(false, -1);
/*  2365 */           this.CurMech.SetRAAES(true, index);
/*       */         } catch (Exception e) {
/*  2367 */           this.chkRAAES.setSelected(false);
/*       */         }
/*       */       }
/*       */     }
/*  2371 */     if (this.chkLAAES.isSelected()) {
/*  2372 */       if (this.CurMech.IsQuad()) {
/*  2373 */         this.chkLAAES.setSelected(false);
/*       */       } else {
/*  2375 */         int index = this.CurMech.GetLoadout().FindIndex(this.CurMech.GetLAAES()).Index;
/*       */         try {
/*  2377 */           this.CurMech.SetLAAES(false, -1);
/*  2378 */           this.CurMech.SetLAAES(true, index);
/*       */         } catch (Exception e) {
/*  2380 */           this.chkLAAES.setSelected(false);
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void FixArmorSpinners()
/*       */   {
/*  2388 */     MechArmor a = this.CurMech.GetArmor();
/*  2389 */     this.spnHDArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(0), 0, 9, 1));
/*  2390 */     this.spnCTArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(1), 0, a.GetLocationMax(1), 1));
/*  2391 */     this.spnLTArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(2), 0, a.GetLocationMax(2), 1));
/*  2392 */     this.spnRTArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(3), 0, a.GetLocationMax(3), 1));
/*  2393 */     this.spnLAArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(4), 0, a.GetLocationMax(4), 1));
/*  2394 */     this.spnRAArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(5), 0, a.GetLocationMax(5), 1));
/*  2395 */     this.spnLLArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(6), 0, a.GetLocationMax(6), 1));
/*  2396 */     this.spnRLArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(7), 0, a.GetLocationMax(7), 1));
/*  2397 */     this.spnCTRArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(8), 0, a.GetLocationMax(1), 1));
/*  2398 */     this.spnLTRArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(9), 0, a.GetLocationMax(2), 1));
/*  2399 */     this.spnRTRArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(10), 0, a.GetLocationMax(3), 1));
/*       */   }
/*       */   
/*       */   private void SaveSelections()
/*       */   {
/*  2404 */     this.Selections[0] = BuildLookupName(this.CurMech.GetIntStruc().GetCurrentState());
/*  2405 */     this.Selections[1] = BuildLookupName(this.CurMech.GetEngine().GetCurrentState());
/*  2406 */     this.Selections[2] = BuildLookupName(this.CurMech.GetGyro().GetCurrentState());
/*  2407 */     this.Selections[3] = BuildLookupName(this.CurMech.GetCockpit().GetCurrentState());
/*  2408 */     this.Selections[4] = BuildLookupName(this.CurMech.GetPhysEnhance().GetCurrentState());
/*  2409 */     this.Selections[5] = BuildLookupName(this.CurMech.GetHeatSinks().GetCurrentState());
/*  2410 */     this.Selections[6] = BuildLookupName(this.CurMech.GetJumpJets().GetCurrentState());
/*  2411 */     this.Selections[7] = BuildLookupName(this.CurMech.GetArmor().GetCurrentState());
/*       */   }
/*       */   
/*       */ 
/*       */   private JButton btnRemoveEquip;
/*       */   private JButton btnRemoveItemCrits;
/*       */   private JButton btnRenameVariant;
/*       */   private JButton btnSaveIcon;
/*       */   private JButton btnSelectiveAllocate;
/*       */   private JCheckBox chkAverageDamage;
/*       */   private JCheckBox chkBSPFD;
/*       */   private JCheckBox chkBoobyTrap;
/*       */   
/*       */   private void LoadSelections()
/*       */   {
/*  2426 */     this.cmbInternalType.setSelectedItem(this.Selections[0]);
/*  2427 */     this.cmbEngineType.setSelectedItem(this.Selections[1]);
/*  2428 */     this.cmbGyroType.setSelectedItem(this.Selections[2]);
/*  2429 */     this.cmbCockpitType.setSelectedItem(this.Selections[3]);
/*  2430 */     this.cmbPhysEnhance.setSelectedItem(this.Selections[4]);
/*  2431 */     this.cmbHeatSinkType.setSelectedItem(this.Selections[5]);
/*  2432 */     this.cmbJumpJetType.setSelectedItem(this.Selections[6]);
/*  2433 */     this.cmbArmorType.setSelectedItem(this.Selections[7]);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void RevertToStandardArmor()
/*       */   {
/*  2440 */     this.cmbArmorType.setSelectedItem("Standard Armor");
/*       */   }
/*       */   
/*       */   private void ResetAmmo()
/*       */   {
/*  2445 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();ArrayList wep = new ArrayList();
/*       */     
/*       */ 
/*  2448 */     for (int i = 0; i < v.size(); i++) {
/*  2449 */       Object a = v.get(i);
/*  2450 */       if ((a instanceof ifWeapon)) {
/*  2451 */         if (((ifWeapon)a).HasAmmo()) {
/*  2452 */           wep.add(a);
/*       */         }
/*  2454 */       } else if (((a instanceof Equipment)) && 
/*  2455 */         (((Equipment)a).HasAmmo())) {
/*  2456 */         wep.add(a);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  2462 */     Object[] result = { " " };
/*  2463 */     if (wep.size() > 0)
/*       */     {
/*  2465 */       int[] key = new int[wep.size()];
/*  2466 */       for (int i = 0; i < wep.size(); i++) {
/*  2467 */         if ((wep.get(i) instanceof ifWeapon)) {
/*  2468 */           key[i] = ((ifWeapon)wep.get(i)).GetAmmoIndex();
/*  2469 */         } else if ((wep.get(i) instanceof Equipment)) {
/*  2470 */           key[i] = ((Equipment)wep.get(i)).GetAmmoIndex();
/*       */         }
/*       */       }
/*  2473 */       result = this.data.GetEquipment().GetAmmo(key, this.CurMech);
/*       */     }
/*       */     
/*       */ 
/*  2477 */     this.Equipment[6] = result;
/*  2478 */     this.lstChooseAmmunition.setListData(result);
/*  2479 */     this.lstChooseAmmunition.repaint();
/*       */   }
/*       */   
/*       */   private void SelectiveAllocate() { dlgSelectiveAllocate Selec;
/*       */     dlgSelectiveAllocate Selec;
/*  2484 */     if (this.CurItem.Contiguous()) {
/*  2485 */       components.EquipmentCollection e = this.CurMech.GetLoadout().GetCollection(this.CurItem);
/*  2486 */       if (e == null) {
/*  2487 */         return;
/*       */       }
/*  2489 */       Selec = new dlgSelectiveAllocate(this, true, e);
/*       */     }
/*       */     else {
/*  2492 */       Selec = new dlgSelectiveAllocate(this, true, this.CurItem);
/*       */     }
/*  2494 */     Selec.setLocationRelativeTo(this);
/*  2495 */     Selec.setVisible(true);
/*  2496 */     RefreshSummary();
/*  2497 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void AutoAllocate() {
/*  2501 */     if (this.CurItem.Contiguous()) {
/*  2502 */       components.EquipmentCollection e = this.CurMech.GetLoadout().GetCollection(this.CurItem);
/*  2503 */       if (e == null) {
/*  2504 */         return;
/*       */       }
/*  2506 */       this.CurMech.GetLoadout().AutoAllocate(e);
/*       */     }
/*       */     else {
/*  2509 */       this.CurMech.GetLoadout().AutoAllocate(this.CurItem);
/*       */     }
/*  2511 */     RefreshSummary();
/*  2512 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void ResetTonnageSelector() {
/*  2516 */     int tons = this.CurMech.GetTonnage();
/*  2517 */     if (tons < 15) {
/*  2518 */       this.cmbTonnage.setSelectedIndex(0);
/*  2519 */       this.lblMechType.setText("Ultralight Mech");
/*  2520 */     } else if ((tons > 10) && (tons < 20)) {
/*  2521 */       this.cmbTonnage.setSelectedIndex(1);
/*  2522 */       this.lblMechType.setText("Ultralight Mech");
/*  2523 */     } else if ((tons > 15) && (tons < 25)) {
/*  2524 */       this.cmbTonnage.setSelectedIndex(2);
/*  2525 */       this.lblMechType.setText("Light Mech");
/*  2526 */     } else if ((tons > 20) && (tons < 30)) {
/*  2527 */       this.cmbTonnage.setSelectedIndex(3);
/*  2528 */       this.lblMechType.setText("Light Mech");
/*  2529 */     } else if ((tons > 25) && (tons < 35)) {
/*  2530 */       this.cmbTonnage.setSelectedIndex(4);
/*  2531 */       this.lblMechType.setText("Light Mech");
/*  2532 */     } else if ((tons > 30) && (tons < 40)) {
/*  2533 */       this.cmbTonnage.setSelectedIndex(5);
/*  2534 */       this.lblMechType.setText("Light Mech");
/*  2535 */     } else if ((tons > 35) && (tons < 45)) {
/*  2536 */       this.cmbTonnage.setSelectedIndex(6);
/*  2537 */       this.lblMechType.setText("Medium Mech");
/*  2538 */     } else if ((tons > 40) && (tons < 50)) {
/*  2539 */       this.cmbTonnage.setSelectedIndex(7);
/*  2540 */       this.lblMechType.setText("Medium Mech");
/*  2541 */     } else if ((tons > 45) && (tons < 55)) {
/*  2542 */       this.cmbTonnage.setSelectedIndex(8);
/*  2543 */       this.lblMechType.setText("Medium Mech");
/*  2544 */     } else if ((tons > 50) && (tons < 60)) {
/*  2545 */       this.cmbTonnage.setSelectedIndex(9);
/*  2546 */       this.lblMechType.setText("Medium Mech");
/*  2547 */     } else if ((tons > 55) && (tons < 65)) {
/*  2548 */       this.cmbTonnage.setSelectedIndex(10);
/*  2549 */       this.lblMechType.setText("Heavy Mech");
/*  2550 */     } else if ((tons > 60) && (tons < 70)) {
/*  2551 */       this.cmbTonnage.setSelectedIndex(11);
/*  2552 */       this.lblMechType.setText("Heavy Mech");
/*  2553 */     } else if ((tons > 65) && (tons < 75)) {
/*  2554 */       this.cmbTonnage.setSelectedIndex(12);
/*  2555 */       this.lblMechType.setText("Heavy Mech");
/*  2556 */     } else if ((tons > 70) && (tons < 80)) {
/*  2557 */       this.cmbTonnage.setSelectedIndex(13);
/*  2558 */       this.lblMechType.setText("Heavy Mech");
/*  2559 */     } else if ((tons > 75) && (tons < 85)) {
/*  2560 */       this.cmbTonnage.setSelectedIndex(14);
/*  2561 */       this.lblMechType.setText("Assault Mech");
/*  2562 */     } else if ((tons > 80) && (tons < 90)) {
/*  2563 */       this.cmbTonnage.setSelectedIndex(15);
/*  2564 */       this.lblMechType.setText("Assault Mech");
/*  2565 */     } else if ((tons > 85) && (tons < 95)) {
/*  2566 */       this.cmbTonnage.setSelectedIndex(16);
/*  2567 */       this.lblMechType.setText("Assault Mech");
/*  2568 */     } else if ((tons > 90) && (tons < 100)) {
/*  2569 */       this.cmbTonnage.setSelectedIndex(17);
/*  2570 */       this.lblMechType.setText("Assault Mech");
/*       */     } else {
/*  2572 */       this.cmbTonnage.setSelectedIndex(18);
/*  2573 */       this.lblMechType.setText("Assault Mech");
/*       */     }
/*       */   }
/*       */   
/*       */   private void GetNewMech() {
/*  2578 */     boolean Omni = this.CurMech.IsOmnimech();
/*  2579 */     this.cmbMotiveType.setSelectedIndex(0);
/*  2580 */     this.CurMech = new Mech(this.Prefs);
/*       */     
/*  2582 */     this.chkYearRestrict.setSelected(false);
/*  2583 */     this.txtProdYear.setText("");
/*  2584 */     this.cmbMechEra.setEnabled(true);
/*  2585 */     this.cmbProductionEra.setEnabled(true);
/*  2586 */     this.cmbTechBase.setEnabled(true);
/*  2587 */     this.txtProdYear.setEnabled(true);
/*       */     
/*  2589 */     this.cmbRulesLevel.setSelectedItem(this.Prefs.get("NewMech_RulesLevel", "Tournament Legal"));
/*  2590 */     this.cmbMechEra.setSelectedItem(this.Prefs.get("NewMech_Era", "Age of War/Star League"));
/*  2591 */     this.cmbProductionEra.setSelectedIndex(0);
/*       */     
/*  2593 */     if (Omni) {
/*  2594 */       UnlockGUIFromOmni();
/*       */     }
/*       */     
/*  2597 */     this.CurMech.SetEra(this.cmbMechEra.getSelectedIndex());
/*  2598 */     this.CurMech.SetProductionEra(this.cmbProductionEra.getSelectedIndex());
/*  2599 */     this.CurMech.SetRulesLevel(this.cmbRulesLevel.getSelectedIndex());
/*  2600 */     switch (this.CurMech.GetEra()) {
/*       */     case 0: 
/*  2602 */       this.CurMech.SetYear(2750, false);
/*  2603 */       break;
/*       */     case 1: 
/*  2605 */       this.CurMech.SetYear(3025, false);
/*  2606 */       break;
/*       */     case 2: 
/*  2608 */       this.CurMech.SetYear(3070, false);
/*  2609 */       break;
/*       */     case 3: 
/*  2611 */       this.CurMech.SetYear(3130, false);
/*  2612 */       break;
/*       */     case 4: 
/*  2614 */       this.CurMech.SetYear(0, false);
/*       */     }
/*       */     
/*  2617 */     BuildTechBaseSelector();
/*  2618 */     this.cmbTechBase.setSelectedItem(this.Prefs.get("NewMech_Techbase", "Inner Sphere"));
/*  2619 */     switch (this.cmbTechBase.getSelectedIndex()) {
/*       */     case 0: 
/*  2621 */       this.CurMech.SetInnerSphere();
/*  2622 */       break;
/*       */     case 1: 
/*  2624 */       this.CurMech.SetClan();
/*  2625 */       break;
/*       */     case 2: 
/*  2627 */       this.CurMech.SetMixed();
/*       */     }
/*       */     
/*  2630 */     if (this.CurMech.IsIndustrialmech()) {
/*  2631 */       this.cmbMechType.setSelectedIndex(1);
/*       */     } else {
/*  2633 */       this.cmbMechType.setSelectedIndex(0);
/*       */     }
/*  2635 */     this.txtMechName.setText(this.CurMech.GetName());
/*  2636 */     this.txtMechModel.setText(this.CurMech.GetModel());
/*       */     
/*  2638 */     FixTransferHandlers();
/*       */     try {
/*  2640 */       this.CurMech.Visit(new visitors.VMechFullRecalc());
/*       */     }
/*       */     catch (Exception e) {
/*  2643 */       System.err.println(e.getMessage());
/*  2644 */       e.printStackTrace();
/*       */     }
/*       */     
/*  2647 */     ResetTonnageSelector();
/*  2648 */     BuildChassisSelector();
/*  2649 */     BuildEngineSelector();
/*  2650 */     BuildGyroSelector();
/*  2651 */     BuildCockpitSelector();
/*  2652 */     BuildEnhancementSelector();
/*  2653 */     BuildHeatsinkSelector();
/*  2654 */     BuildJumpJetSelector();
/*  2655 */     BuildArmorSelector();
/*  2656 */     CheckOmnimech();
/*  2657 */     this.cmbInternalType.setSelectedItem("Standard Structure");
/*  2658 */     this.cmbEngineType.setSelectedItem("Fusion Engine");
/*  2659 */     this.cmbGyroType.setSelectedItem("Standard Gyro");
/*  2660 */     this.cmbCockpitType.setSelectedItem("Standard Cockpit");
/*  2661 */     this.cmbPhysEnhance.setSelectedItem("No Enhancement");
/*  2662 */     this.cmbHeatSinkType.setSelectedItem(this.Prefs.get("NewMech_Heatsinks", "Single Heat Sink"));
/*  2663 */     this.cmbJumpJetType.setSelectedItem("Standard Jump Jet");
/*  2664 */     this.cmbArmorType.setSelectedItem("Standard Armor");
/*  2665 */     FixWalkMPSpinner();
/*  2666 */     FixJJSpinnerModel();
/*  2667 */     FixHeatSinkSpinnerModel();
/*  2668 */     RefreshInternalPoints();
/*  2669 */     FixArmorSpinners();
/*  2670 */     this.data.Rebuild(this.CurMech);
/*  2671 */     RefreshEquipment();
/*  2672 */     this.chkCTCASE.setSelected(false);
/*  2673 */     this.chkLTCASE.setSelected(false);
/*  2674 */     this.chkRTCASE.setSelected(false);
/*  2675 */     this.chkHDCASE2.setSelected(false);
/*  2676 */     this.chkCTCASE2.setSelected(false);
/*  2677 */     this.chkLTCASE2.setSelected(false);
/*  2678 */     this.chkRTCASE2.setSelected(false);
/*  2679 */     this.chkLACASE2.setSelected(false);
/*  2680 */     this.chkRACASE2.setSelected(false);
/*  2681 */     this.chkLLCASE2.setSelected(false);
/*  2682 */     this.chkRLCASE2.setSelected(false);
/*  2683 */     this.chkNullSig.setSelected(false);
/*  2684 */     this.chkVoidSig.setSelected(false);
/*  2685 */     this.chkBSPFD.setSelected(false);
/*  2686 */     this.chkCLPS.setSelected(false);
/*  2687 */     this.chkTracks.setSelected(false);
/*  2688 */     SetLoadoutArrays();
/*  2689 */     RefreshSummary();
/*  2690 */     RefreshInfoPane();
/*  2691 */     SetWeaponChoosers();
/*  2692 */     ResetAmmo();
/*       */     
/*  2694 */     this.Overview.StartNewDocument();
/*  2695 */     this.Capabilities.StartNewDocument();
/*  2696 */     this.History.StartNewDocument();
/*  2697 */     this.Deployment.StartNewDocument();
/*  2698 */     this.Variants.StartNewDocument();
/*  2699 */     this.Notables.StartNewDocument();
/*  2700 */     this.Additional.StartNewDocument();
/*  2701 */     this.txtManufacturer.setText("");
/*  2702 */     this.txtManufacturerLocation.setText("");
/*  2703 */     this.txtEngineManufacturer.setText("");
/*  2704 */     this.txtArmorModel.setText("");
/*  2705 */     this.txtChassisModel.setText("");
/*  2706 */     this.txtJJModel.setText("");
/*  2707 */     this.txtCommSystem.setText("");
/*  2708 */     this.txtTNTSystem.setText("");
/*  2709 */     this.txtSource.setText("");
/*  2710 */     this.lblFluffImage.setIcon(null);
/*       */     
/*  2712 */     if (this.cmbMechEra.getSelectedIndex() == 4) {
/*  2713 */       this.chkYearRestrict.setEnabled(false);
/*       */     } else {
/*  2715 */       this.chkYearRestrict.setEnabled(true);
/*       */     }
/*  2717 */     this.CurMech.SetChanged(false);
/*  2718 */     setTitle("Solaris Skunk Werks 0.6.83.1");
/*       */   }
/*       */   
/*       */   private void GetInfoOn()
/*       */   {
/*  2723 */     if (((this.CurItem instanceof ifWeapon)) || ((this.CurItem instanceof Ammunition))) {
/*  2724 */       dlgWeaponInfo WepInfo = new dlgWeaponInfo(this, true);
/*  2725 */       WepInfo.setLocationRelativeTo(this);
/*  2726 */       WepInfo.setVisible(true);
/*       */     } else {
/*  2728 */       dlgPlaceableInfo ItemInfo = new dlgPlaceableInfo(this, true);
/*  2729 */       ItemInfo.setLocationRelativeTo(this);
/*  2730 */       ItemInfo.setVisible(true);
/*       */     }
/*       */   }
/*       */   
/*       */   private void UnallocateAll()
/*       */   {
/*  2736 */     this.CurMech.GetLoadout().UnallocateAll(this.CurItem, false);
/*  2737 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void MountRear()
/*       */   {
/*  2742 */     if (this.CurItem.IsMountedRear()) {
/*  2743 */       this.CurItem.MountRear(false);
/*       */     } else {
/*  2745 */       this.CurItem.MountRear(true);
/*       */     }
/*  2747 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */ 
/*       */   private void ShowInfoOn(abPlaceable p)
/*       */   {
/*  2753 */     AvailableCode AC = p.GetAvailability();
/*       */     
/*  2755 */     this.lblInfoAVSL.setText(AC.GetISSLCode() + " / " + AC.GetCLSLCode());
/*  2756 */     this.lblInfoAVSW.setText(AC.GetISSWCode() + " / " + AC.GetCLSWCode());
/*  2757 */     this.lblInfoAVCI.setText(AC.GetISCICode() + " / " + AC.GetCLCICode());
/*  2758 */     switch (AC.GetTechBase()) {
/*       */     case 0: 
/*  2760 */       this.lblInfoIntro.setText(AC.GetISIntroDate() + " (" + AC.GetISIntroFaction() + ")");
/*  2761 */       if (AC.WentExtinctIS()) {
/*  2762 */         this.lblInfoExtinct.setText("" + AC.GetISExtinctDate());
/*       */       } else {
/*  2764 */         this.lblInfoExtinct.setText("--");
/*       */       }
/*  2766 */       if (AC.WasReIntrodIS()) {
/*  2767 */         this.lblInfoReintro.setText(AC.GetISReIntroDate() + " (" + AC.GetISReIntroFaction() + ")");
/*       */       } else {
/*  2769 */         this.lblInfoReintro.setText("--");
/*       */       }
/*  2771 */       break;
/*       */     case 1: 
/*  2773 */       this.lblInfoIntro.setText(AC.GetCLIntroDate() + " (" + AC.GetCLIntroFaction() + ")");
/*  2774 */       if (AC.WentExtinctCL()) {
/*  2775 */         this.lblInfoExtinct.setText("" + AC.GetCLExtinctDate());
/*       */       } else {
/*  2777 */         this.lblInfoExtinct.setText("--");
/*       */       }
/*  2779 */       if (AC.WasReIntrodCL()) {
/*  2780 */         this.lblInfoReintro.setText(AC.GetCLReIntroDate() + " (" + AC.GetCLReIntroFaction() + ")");
/*       */       } else {
/*  2782 */         this.lblInfoReintro.setText("--");
/*       */       }
/*  2784 */       break;
/*       */     case 2: 
/*  2786 */       this.lblInfoIntro.setText(AC.GetISIntroDate() + " (" + AC.GetISIntroFaction() + ") / " + AC.GetCLIntroDate() + " (" + AC.GetCLIntroFaction() + ")");
/*  2787 */       if (AC.WentExtinctIS()) {
/*  2788 */         this.lblInfoExtinct.setText("" + AC.GetISExtinctDate());
/*       */       } else {
/*  2790 */         this.lblInfoExtinct.setText("--");
/*       */       }
/*  2792 */       if (AC.WentExtinctCL()) {
/*  2793 */         this.lblInfoExtinct.setText(this.lblInfoExtinct.getText() + " / " + AC.GetCLExtinctDate());
/*       */       } else {
/*  2795 */         this.lblInfoExtinct.setText(this.lblInfoExtinct.getText() + " / --");
/*       */       }
/*  2797 */       if (AC.WasReIntrodIS()) {
/*  2798 */         this.lblInfoReintro.setText(AC.GetISReIntroDate() + " (" + AC.GetISReIntroFaction() + ")");
/*       */       } else {
/*  2800 */         this.lblInfoReintro.setText("--");
/*       */       }
/*  2802 */       if (AC.WasReIntrodCL()) {
/*  2803 */         this.lblInfoReintro.setText(this.lblInfoReintro.getText() + " / " + AC.GetCLReIntroDate() + " (" + AC.GetCLReIntroFaction() + ")");
/*       */       } else {
/*  2805 */         this.lblInfoReintro.setText(this.lblInfoReintro.getText() + " / --");
/*       */       }
/*       */       break;
/*       */     }
/*  2809 */     if (this.CurMech.IsIndustrialmech()) {
/*  2810 */       switch (AC.GetRulesLevel_IM()) {
/*       */       case 0: 
/*  2812 */         this.lblInfoRulesLevel.setText("Introductory");
/*  2813 */         break;
/*       */       case 1: 
/*  2815 */         this.lblInfoRulesLevel.setText("Tournament");
/*  2816 */         break;
/*       */       case 2: 
/*  2818 */         this.lblInfoRulesLevel.setText("Advanced");
/*  2819 */         break;
/*       */       case 3: 
/*  2821 */         this.lblInfoRulesLevel.setText("Experimental");
/*  2822 */         break;
/*       */       case 4: 
/*  2824 */         this.lblInfoRulesLevel.setText("Era Specific");
/*  2825 */         break;
/*       */       default: 
/*  2827 */         this.lblInfoRulesLevel.setText("??");break;
/*       */       }
/*       */     } else {
/*  2830 */       switch (AC.GetRulesLevel_BM()) {
/*       */       case 0: 
/*  2832 */         this.lblInfoRulesLevel.setText("Introductory");
/*  2833 */         break;
/*       */       case 1: 
/*  2835 */         this.lblInfoRulesLevel.setText("Tournament");
/*  2836 */         break;
/*       */       case 2: 
/*  2838 */         this.lblInfoRulesLevel.setText("Advanced");
/*  2839 */         break;
/*       */       case 3: 
/*  2841 */         this.lblInfoRulesLevel.setText("Experimental");
/*  2842 */         break;
/*       */       case 4: 
/*  2844 */         this.lblInfoRulesLevel.setText("Era Specific");
/*  2845 */         break;
/*       */       default: 
/*  2847 */         this.lblInfoRulesLevel.setText("??");
/*       */       }
/*       */     }
/*  2850 */     this.lblInfoName.setText(p.CritName());
/*  2851 */     this.lblInfoTonnage.setText("" + p.GetTonnage());
/*  2852 */     this.lblInfoCrits.setText("" + p.NumCrits());
/*  2853 */     this.lblInfoCost.setText("" + String.format("%1$,.0f", new Object[] { Double.valueOf(p.GetCost()) }));
/*  2854 */     this.lblInfoBV.setText(CommonTools.GetAggregateReportBV(p));
/*       */     
/*       */ 
/*  2857 */     String restrict = "";
/*  2858 */     if (!p.CanAllocHD()) {
/*  2859 */       restrict = restrict + "No Head, ";
/*       */     }
/*  2861 */     if (!p.CanAllocCT()) {
/*  2862 */       restrict = restrict + "No Center Torso, ";
/*       */     }
/*  2864 */     if (!p.CanAllocTorso()) {
/*  2865 */       restrict = restrict + "No Side Torsos, ";
/*       */     }
/*  2867 */     if (!p.CanAllocArms()) {
/*  2868 */       restrict = restrict + "No Arms, ";
/*       */     }
/*  2870 */     if (!p.CanAllocLegs()) {
/*  2871 */       restrict = restrict + "No Legs, ";
/*       */     }
/*  2873 */     if (p.CanSplit()) {
/*  2874 */       restrict = restrict + "Can Split, ";
/*       */     }
/*       */     
/*       */ 
/*  2878 */     if ((p instanceof ifWeapon)) {
/*  2879 */       ifWeapon w = (ifWeapon)p;
/*  2880 */       this.lblInfoType.setText(w.GetType());
/*       */       
/*  2882 */       if ((w.IsUltra()) || (w.IsRotary())) {
/*  2883 */         this.lblInfoHeat.setText(w.GetHeat() + "/shot");
/*       */       }
/*  2885 */       else if ((w instanceof RangedWeapon)) {
/*  2886 */         if (((RangedWeapon)w).IsUsingCapacitor()) {
/*  2887 */           this.lblInfoHeat.setText(w.GetHeat() + "*");
/*  2888 */         } else if (((RangedWeapon)w).IsUsingInsulator()) {
/*  2889 */           this.lblInfoHeat.setText(w.GetHeat() + " (I)");
/*       */         } else {
/*  2891 */           this.lblInfoHeat.setText("" + w.GetHeat());
/*       */         }
/*       */       } else {
/*  2894 */         this.lblInfoHeat.setText("" + w.GetHeat());
/*       */       }
/*       */       
/*       */ 
/*  2898 */       if (w.GetWeaponClass() == 2) {
/*  2899 */         this.lblInfoDamage.setText(w.GetDamageShort() + "/msl");
/*  2900 */       } else if (w.GetWeaponClass() == 3) {
/*  2901 */         this.lblInfoDamage.setText(w.GetDamageShort() + "A");
/*  2902 */       } else if ((w instanceof components.MGArray)) {
/*  2903 */         this.lblInfoDamage.setText(w.GetDamageShort() + "/gun");
/*  2904 */       } else if ((w.GetDamageShort() == w.GetDamageMedium()) && (w.GetDamageShort() == w.GetDamageLong())) {
/*  2905 */         if ((w.IsUltra()) || (w.IsRotary())) {
/*  2906 */           this.lblInfoDamage.setText(w.GetDamageShort() + "/shot");
/*       */         }
/*  2908 */         else if ((w instanceof RangedWeapon)) {
/*  2909 */           if (((RangedWeapon)w).IsUsingCapacitor()) {
/*  2910 */             this.lblInfoDamage.setText(w.GetDamageShort() + "*");
/*       */           } else {
/*  2912 */             this.lblInfoDamage.setText("" + w.GetDamageShort());
/*       */           }
/*       */         } else {
/*  2915 */           this.lblInfoDamage.setText("" + w.GetDamageShort());
/*       */         }
/*       */         
/*       */       }
/*  2919 */       else if ((w instanceof RangedWeapon)) {
/*  2920 */         if (((RangedWeapon)w).IsUsingCapacitor()) {
/*  2921 */           this.lblInfoDamage.setText(w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong() + "*");
/*       */         } else {
/*  2923 */           this.lblInfoDamage.setText(w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong());
/*       */         }
/*       */       } else {
/*  2926 */         this.lblInfoDamage.setText(w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong());
/*       */       }
/*       */       
/*       */ 
/*  2930 */       if (w.GetRangeLong() < 1) {
/*  2931 */         if (w.GetRangeMedium() < 1) {
/*  2932 */           if (w.GetWeaponClass() == 3) {
/*  2933 */             this.lblInfoRange.setText(w.GetRangeShort() + " boards");
/*       */           } else {
/*  2935 */             this.lblInfoRange.setText(w.GetRangeShort() + "");
/*       */           }
/*       */         } else {
/*  2938 */           this.lblInfoRange.setText(w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/-");
/*       */         }
/*       */       } else {
/*  2941 */         this.lblInfoRange.setText(w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/" + w.GetRangeLong());
/*       */       }
/*       */       
/*  2944 */       if (w.HasAmmo()) {
/*  2945 */         this.lblInfoAmmo.setText("" + w.GetAmmoLotSize());
/*       */       } else {
/*  2947 */         this.lblInfoAmmo.setText("--");
/*       */       }
/*  2949 */       this.lblInfoSpecials.setText(w.GetSpecials());
/*  2950 */       if (w.OmniRestrictActuators()) {
/*  2951 */         restrict = restrict + "Omni Actuator Restricted";
/*       */       }
/*  2953 */     } else if ((p instanceof Ammunition)) {
/*  2954 */       Ammunition a = (Ammunition)p;
/*  2955 */       this.lblInfoType.setText("--");
/*  2956 */       this.lblInfoHeat.setText("--");
/*  2957 */       if (a.ClusterGrouping() > 1) {
/*  2958 */         this.lblInfoDamage.setText(a.GetDamageShort() + "/hit");
/*       */       } else {
/*  2960 */         this.lblInfoDamage.setText(a.GetDamageShort() + "");
/*       */       }
/*  2962 */       if (a.GetLongRange() < 1) {
/*  2963 */         if (a.GetMediumRange() < 1) {
/*  2964 */           this.lblInfoRange.setText(a.GetShortRange() + " boards");
/*       */         } else {
/*  2966 */           this.lblInfoRange.setText(a.GetMinRange() + "/" + a.GetShortRange() + "/" + a.GetMediumRange() + "/-");
/*       */         }
/*       */       } else {
/*  2969 */         this.lblInfoRange.setText(a.GetMinRange() + "/" + a.GetShortRange() + "/" + a.GetMediumRange() + "/" + a.GetLongRange());
/*       */       }
/*  2971 */       this.lblInfoAmmo.setText("" + a.GetLotSize());
/*  2972 */       if (a.IsExplosive()) {
/*  2973 */         this.lblInfoSpecials.setText("Explosive");
/*       */       } else {
/*  2975 */         this.lblInfoSpecials.setText("--");
/*       */       }
/*  2977 */     } else if ((p instanceof Equipment)) {
/*  2978 */       Equipment e = (Equipment)p;
/*  2979 */       this.lblInfoType.setText(e.GetType());
/*  2980 */       this.lblInfoHeat.setText("" + e.GetHeat());
/*  2981 */       this.lblInfoDamage.setText("--");
/*  2982 */       if ((e.GetShortRange() <= 0) && (e.GetMediumRange() <= 0)) {
/*  2983 */         if (e.GetLongRange() > 0) {
/*  2984 */           this.lblInfoRange.setText("" + e.GetLongRange());
/*       */         } else {
/*  2986 */           this.lblInfoRange.setText("--");
/*       */         }
/*       */       } else {
/*  2989 */         this.lblInfoRange.setText("0/" + e.GetShortRange() + "/" + e.GetMediumRange() + "/" + e.GetLongRange());
/*       */       }
/*  2991 */       if (e.HasAmmo()) {
/*  2992 */         this.lblInfoAmmo.setText("" + e.GetAmmo());
/*       */       } else {
/*  2994 */         this.lblInfoAmmo.setText("--");
/*       */       }
/*  2996 */       this.lblInfoSpecials.setText(e.GetSpecials());
/*       */     } else {
/*  2998 */       this.lblInfoType.setText("--");
/*  2999 */       this.lblInfoHeat.setText("--");
/*  3000 */       this.lblInfoDamage.setText("--");
/*  3001 */       this.lblInfoRange.setText("--");
/*  3002 */       this.lblInfoAmmo.setText("--");
/*  3003 */       this.lblInfoSpecials.setText("--");
/*       */     }
/*       */     
/*       */ 
/*  3007 */     if (restrict.length() > 0) {
/*  3008 */       if (restrict.endsWith(", ")) {
/*  3009 */         restrict = restrict.substring(0, restrict.length() - 2);
/*       */       }
/*  3011 */       this.lblInfoMountRestrict.setText(restrict);
/*       */     } else {
/*  3013 */       this.lblInfoMountRestrict.setText("None");
/*       */     }
/*  3015 */     this.lblInfoMountRestrict.setText(this.lblInfoMountRestrict.getText() + " MM Name " + p.MegaMekName(false));
/*       */   }
/*       */   
/*       */   private void RemoveItemCritTab() {
/*  3019 */     if ((!this.CurItem.CoreComponent()) && (this.CurItem.Contiguous())) {
/*  3020 */       this.CurMech.GetLoadout().Remove(this.CurItem);
/*       */       
/*       */ 
/*  3023 */       if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/*  3024 */         this.Equipment[7] = { " " };
/*       */       } else {
/*  3026 */         this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  3031 */       if (this.CurMech.UsingTC()) {
/*  3032 */         this.CurMech.UnallocateTC();
/*       */       }
/*       */       
/*       */ 
/*  3036 */       ResetAmmo();
/*       */       
/*       */ 
/*  3039 */       RefreshSummary();
/*  3040 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   private void SolidifyMech()
/*       */   {
/*  3047 */     int year = 0;
/*  3048 */     this.CurMech.SetName(this.txtMechName.getText());
/*  3049 */     this.CurMech.SetModel(this.txtMechModel.getText());
/*  3050 */     if (this.txtProdYear.getText().isEmpty()) {
/*  3051 */       switch (this.cmbMechEra.getSelectedIndex()) {
/*       */       case 0: 
/*  3053 */         this.CurMech.SetYear(2750, false);
/*  3054 */         break;
/*       */       case 1: 
/*  3056 */         this.CurMech.SetYear(3025, false);
/*  3057 */         break;
/*       */       case 2: 
/*  3059 */         this.CurMech.SetYear(3070, false);
/*  3060 */         break;
/*       */       case 3: 
/*  3062 */         this.CurMech.SetYear(3132, false);
/*       */       }
/*       */     } else {
/*       */       try
/*       */       {
/*  3067 */         year = Integer.parseInt(this.txtProdYear.getText());
/*  3068 */         this.CurMech.SetYear(year, true);
/*       */       } catch (NumberFormatException n) {
/*  3070 */         Media.Messager(this, "The production year is not a number.");
/*  3071 */         this.tbpMainTabPane.setSelectedComponent(this.pnlBasicSetup);
/*  3072 */         return;
/*       */       }
/*       */     }
/*       */     
/*  3076 */     this.CurMech.SetOverview(this.Overview.GetText());
/*  3077 */     this.CurMech.SetCapabilities(this.Capabilities.GetText());
/*  3078 */     this.CurMech.SetHistory(this.History.GetText());
/*  3079 */     this.CurMech.SetDeployment(this.Deployment.GetText());
/*  3080 */     this.CurMech.SetVariants(this.Variants.GetText());
/*  3081 */     this.CurMech.SetNotables(this.Notables.GetText());
/*  3082 */     this.CurMech.SetAdditional(this.Additional.GetText());
/*  3083 */     this.CurMech.SetCompany(this.txtManufacturer.getText());
/*  3084 */     this.CurMech.SetLocation(this.txtManufacturerLocation.getText());
/*  3085 */     this.CurMech.SetEngineManufacturer(this.txtEngineManufacturer.getText());
/*  3086 */     this.CurMech.SetArmorModel(this.txtArmorModel.getText());
/*  3087 */     this.CurMech.SetChassisModel(this.txtChassisModel.getText());
/*  3088 */     if (this.CurMech.GetJumpJets().GetNumJJ() > 0) {
/*  3089 */       this.CurMech.SetJJModel(this.txtJJModel.getText());
/*       */     }
/*  3091 */     this.CurMech.SetCommSystem(this.txtCommSystem.getText());
/*  3092 */     this.CurMech.SetTandTSystem(this.txtTNTSystem.getText());
/*  3093 */     this.CurMech.SetSource(this.txtSource.getText());
/*       */   }
/*       */   
/*       */   private void EnableJumpJets(boolean enable)
/*       */   {
/*  3098 */     if (enable) {
/*  3099 */       this.spnJumpMP.setEnabled(true);
/*  3100 */       if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetJumpJets().GetNumJJ() > 0)) {
/*  3101 */         this.cmbJumpJetType.setEnabled(false);
/*       */       } else {
/*  3103 */         this.cmbJumpJetType.setEnabled(true);
/*       */       }
/*       */     } else {
/*  3106 */       this.CurMech.GetJumpJets().ClearJumpJets();
/*  3107 */       this.spnJumpMP.setEnabled(false);
/*  3108 */       this.cmbJumpJetType.setEnabled(false);
/*       */     }
/*  3110 */     FixJJSpinnerModel();
/*       */   }
/*       */   
/*       */   private void FixTransferHandlers()
/*       */   {
/*  3115 */     this.lstHDCrits.setTransferHandler(new thHDTransferHandler(this, this.CurMech));
/*  3116 */     this.lstCTCrits.setTransferHandler(new thCTTransferHandler(this, this.CurMech));
/*  3117 */     this.lstLTCrits.setTransferHandler(new thLTTransferHandler(this, this.CurMech));
/*  3118 */     this.lstRTCrits.setTransferHandler(new thRTTransferHandler(this, this.CurMech));
/*  3119 */     this.lstLACrits.setTransferHandler(new thLATransferHandler(this, this.CurMech));
/*  3120 */     this.lstRACrits.setTransferHandler(new thRATransferHandler(this, this.CurMech));
/*  3121 */     this.lstLLCrits.setTransferHandler(new thLLTransferHandler(this, this.CurMech));
/*  3122 */     this.lstRLCrits.setTransferHandler(new thRLTransferHandler(this, this.CurMech));
/*       */   }
/*       */   
/*       */   private void LockGUIForOmni()
/*       */   {
/*  3127 */     this.chkOmnimech.setSelected(true);
/*  3128 */     this.chkOmnimech.setEnabled(false);
/*  3129 */     this.mnuUnlock.setEnabled(true);
/*  3130 */     this.cmbTonnage.setEnabled(false);
/*  3131 */     this.cmbMechType.setEnabled(false);
/*  3132 */     this.cmbMotiveType.setEnabled(false);
/*  3133 */     this.cmbInternalType.setEnabled(false);
/*  3134 */     this.cmbEngineType.setEnabled(false);
/*  3135 */     this.cmbGyroType.setEnabled(false);
/*  3136 */     this.cmbCockpitType.setEnabled(false);
/*  3137 */     this.cmbPhysEnhance.setEnabled(false);
/*  3138 */     this.cmbHeatSinkType.setEnabled(false);
/*  3139 */     this.spnHDArmor.setEnabled(false);
/*  3140 */     this.spnCTArmor.setEnabled(false);
/*  3141 */     this.spnLTArmor.setEnabled(false);
/*  3142 */     this.spnRTArmor.setEnabled(false);
/*  3143 */     this.spnLAArmor.setEnabled(false);
/*  3144 */     this.spnRAArmor.setEnabled(false);
/*  3145 */     this.spnLLArmor.setEnabled(false);
/*  3146 */     this.spnRLArmor.setEnabled(false);
/*  3147 */     this.spnCTRArmor.setEnabled(false);
/*  3148 */     this.spnLTRArmor.setEnabled(false);
/*  3149 */     this.spnRTRArmor.setEnabled(false);
/*  3150 */     this.cmbArmorType.setEnabled(false);
/*  3151 */     this.btnMaxArmor.setEnabled(false);
/*  3152 */     this.btnArmorTons.setEnabled(false);
/*  3153 */     this.btnRemainingArmor.setEnabled(false);
/*  3154 */     this.btnEfficientArmor.setEnabled(false);
/*  3155 */     this.btnBalanceArmor.setEnabled(false);
/*  3156 */     this.btnLockChassis.setEnabled(false);
/*  3157 */     this.chkYearRestrict.setEnabled(false);
/*  3158 */     if (this.CurMech.GetJumpJets().GetNumJJ() > 0) {
/*  3159 */       this.cmbJumpJetType.setEnabled(false);
/*       */     }
/*  3161 */     this.spnWalkMP.setEnabled(false);
/*  3162 */     if (this.chkFCSAIV.isSelected()) {
/*  3163 */       this.chkFCSAIV.setEnabled(false);
/*       */     }
/*  3165 */     if (this.chkFCSAV.isSelected()) {
/*  3166 */       this.chkFCSAV.setEnabled(false);
/*       */     }
/*  3168 */     if (this.chkFCSApollo.isSelected()) {
/*  3169 */       this.chkFCSApollo.setEnabled(false);
/*       */     }
/*  3171 */     if (this.chkCTCASE.isSelected()) {
/*  3172 */       this.chkCTCASE.setEnabled(false);
/*       */     }
/*  3174 */     if (this.chkLTCASE.isSelected()) {
/*  3175 */       this.chkLTCASE.setEnabled(false);
/*       */     }
/*  3177 */     if (this.chkRTCASE.isSelected()) {
/*  3178 */       this.chkRTCASE.setEnabled(false);
/*       */     }
/*       */     
/*  3181 */     if (this.chkHDCASE2.isSelected()) {
/*  3182 */       this.chkHDCASE2.setEnabled(false);
/*       */     }
/*  3184 */     if (this.chkCTCASE2.isSelected()) {
/*  3185 */       this.chkCTCASE2.setEnabled(false);
/*       */     }
/*  3187 */     if (this.chkLTCASE2.isSelected()) {
/*  3188 */       this.chkLTCASE2.setEnabled(false);
/*       */     }
/*  3190 */     if (this.chkRTCASE2.isSelected()) {
/*  3191 */       this.chkRTCASE2.setEnabled(false);
/*       */     }
/*  3193 */     if (this.chkLACASE2.isSelected()) {
/*  3194 */       this.chkLACASE2.setEnabled(false);
/*       */     }
/*  3196 */     if (this.chkRACASE2.isSelected()) {
/*  3197 */       this.chkRACASE2.setEnabled(false);
/*       */     }
/*  3199 */     if (this.chkLLCASE2.isSelected()) {
/*  3200 */       this.chkLLCASE2.setEnabled(false);
/*       */     }
/*  3202 */     if (this.chkRLCASE2.isSelected()) {
/*  3203 */       this.chkRLCASE2.setEnabled(false);
/*       */     }
/*  3205 */     if (this.chkHDTurret.isSelected()) {
/*  3206 */       this.chkHDTurret.setEnabled(false);
/*       */     }
/*  3208 */     if (this.chkLTTurret.isSelected()) {
/*  3209 */       this.chkLTTurret.setEnabled(false);
/*       */     }
/*  3211 */     if (this.chkRTTurret.isSelected()) {
/*  3212 */       this.chkRTTurret.setEnabled(false);
/*       */     }
/*       */     
/*  3215 */     this.chkFractional.setEnabled(false);
/*  3216 */     this.chkNullSig.setEnabled(false);
/*  3217 */     this.chkVoidSig.setEnabled(false);
/*  3218 */     this.chkCLPS.setEnabled(false);
/*  3219 */     this.chkBSPFD.setEnabled(false);
/*  3220 */     this.chkEnviroSealing.setEnabled(false);
/*  3221 */     this.chkEjectionSeat.setEnabled(false);
/*  3222 */     this.chkRAAES.setEnabled(false);
/*  3223 */     this.chkLAAES.setEnabled(false);
/*  3224 */     this.chkLegAES.setEnabled(false);
/*  3225 */     if (this.CurMech.GetBaseLoadout().HasSupercharger()) {
/*  3226 */       this.chkSupercharger.setEnabled(false);
/*  3227 */       this.lblSupercharger.setEnabled(false);
/*  3228 */       this.cmbSCLoc.setEnabled(false);
/*       */     }
/*       */     
/*  3231 */     CheckActuators();
/*       */     
/*       */ 
/*  3234 */     this.cmbOmniVariant.setEnabled(true);
/*  3235 */     this.btnAddVariant.setEnabled(true);
/*  3236 */     this.btnDeleteVariant.setEnabled(true);
/*  3237 */     this.btnRenameVariant.setEnabled(true);
/*       */   }
/*       */   
/*       */ 
/*       */   private void UnlockGUIFromOmni()
/*       */   {
/*  3243 */     this.chkOmnimech.setSelected(false);
/*  3244 */     this.chkOmnimech.setEnabled(true);
/*  3245 */     this.mnuUnlock.setEnabled(false);
/*  3246 */     this.cmbTonnage.setEnabled(true);
/*  3247 */     this.cmbMechType.setEnabled(true);
/*  3248 */     this.cmbMotiveType.setEnabled(true);
/*  3249 */     this.cmbInternalType.setEnabled(true);
/*  3250 */     this.cmbEngineType.setEnabled(true);
/*  3251 */     this.cmbGyroType.setEnabled(true);
/*  3252 */     this.cmbCockpitType.setEnabled(true);
/*  3253 */     this.cmbPhysEnhance.setEnabled(true);
/*  3254 */     this.cmbHeatSinkType.setEnabled(true);
/*  3255 */     this.spnHDArmor.setEnabled(true);
/*  3256 */     this.spnCTArmor.setEnabled(true);
/*  3257 */     this.spnLTArmor.setEnabled(true);
/*  3258 */     this.spnRTArmor.setEnabled(true);
/*  3259 */     this.spnLAArmor.setEnabled(true);
/*  3260 */     this.spnRAArmor.setEnabled(true);
/*  3261 */     this.spnLLArmor.setEnabled(true);
/*  3262 */     this.spnRLArmor.setEnabled(true);
/*  3263 */     this.spnCTRArmor.setEnabled(true);
/*  3264 */     this.spnLTRArmor.setEnabled(true);
/*  3265 */     this.spnRTRArmor.setEnabled(true);
/*  3266 */     this.cmbArmorType.setEnabled(true);
/*  3267 */     this.btnMaxArmor.setEnabled(true);
/*  3268 */     this.btnArmorTons.setEnabled(true);
/*  3269 */     this.btnRemainingArmor.setEnabled(true);
/*  3270 */     this.btnEfficientArmor.setEnabled(true);
/*  3271 */     this.btnBalanceArmor.setEnabled(true);
/*  3272 */     this.cmbJumpJetType.setEnabled(true);
/*  3273 */     this.btnLockChassis.setEnabled(true);
/*  3274 */     this.chkFCSAIV.setEnabled(true);
/*  3275 */     this.chkFCSAV.setEnabled(true);
/*  3276 */     this.chkFCSApollo.setEnabled(true);
/*  3277 */     this.chkCTCASE.setEnabled(true);
/*  3278 */     this.chkLTCASE.setEnabled(true);
/*  3279 */     this.chkRTCASE.setEnabled(true);
/*  3280 */     this.chkHDCASE2.setEnabled(true);
/*  3281 */     this.chkCTCASE2.setEnabled(true);
/*  3282 */     this.chkLTCASE2.setEnabled(true);
/*  3283 */     this.chkRTCASE2.setEnabled(true);
/*  3284 */     this.chkLACASE2.setEnabled(true);
/*  3285 */     this.chkRACASE2.setEnabled(true);
/*  3286 */     this.chkLLCASE2.setEnabled(true);
/*  3287 */     this.chkRLCASE2.setEnabled(true);
/*  3288 */     this.chkHDTurret.setEnabled(true);
/*  3289 */     this.chkLTTurret.setEnabled(true);
/*  3290 */     this.chkRTTurret.setEnabled(true);
/*  3291 */     this.chkOmnimech.setSelected(false);
/*  3292 */     this.chkOmnimech.setEnabled(true);
/*  3293 */     this.btnLockChassis.setEnabled(false);
/*  3294 */     this.spnWalkMP.setEnabled(true);
/*  3295 */     this.chkYearRestrict.setEnabled(true);
/*  3296 */     this.chkNullSig.setEnabled(true);
/*  3297 */     this.chkVoidSig.setEnabled(true);
/*  3298 */     this.chkCLPS.setEnabled(true);
/*  3299 */     this.chkBSPFD.setEnabled(true);
/*  3300 */     this.chkSupercharger.setEnabled(true);
/*  3301 */     this.lblSupercharger.setEnabled(true);
/*  3302 */     this.cmbSCLoc.setEnabled(true);
/*  3303 */     if (this.CurMech.IsIndustrialmech()) {
/*  3304 */       this.chkEnviroSealing.setEnabled(true);
/*  3305 */       this.chkEjectionSeat.setEnabled(true);
/*       */     } else {
/*  3307 */       this.chkEnviroSealing.setEnabled(false);
/*  3308 */       this.chkEjectionSeat.setEnabled(false);
/*       */     }
/*       */     
/*  3311 */     this.cmbOmniVariant.setEnabled(false);
/*  3312 */     this.btnAddVariant.setEnabled(false);
/*  3313 */     this.btnDeleteVariant.setEnabled(false);
/*  3314 */     this.btnRenameVariant.setEnabled(false);
/*       */   }
/*       */   
/*       */   private void RefreshOmniVariants() {
/*  3318 */     ArrayList v = this.CurMech.GetLoadouts();
/*  3319 */     String[] variants = new String[v.size()];
/*  3320 */     if (v.size() <= 0) {
/*  3321 */       variants = new String[] { "Base Loadout" };
/*       */     } else {
/*  3323 */       for (int i = 0; i < v.size(); i++) {
/*  3324 */         variants[i] = ((ifMechLoadout)v.get(i)).GetName();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  3329 */     this.txtSource.setText(this.CurMech.GetSource());
/*  3330 */     this.cmbOmniVariant.setModel(new DefaultComboBoxModel(variants));
/*  3331 */     this.cmbOmniVariant.setSelectedItem(this.CurMech.GetLoadout().GetName());
/*       */   }
/*       */   
/*       */ 
/*       */   private void RefreshOmniChoices()
/*       */   {
/*  3337 */     CheckActuators();
/*  3338 */     CheckEquipment();
/*       */   }
/*       */   
/*       */   private boolean VerifyMech(ActionEvent evt)
/*       */   {
/*  3343 */     String CurLoadout = "";
/*  3344 */     this.SetSource = false;
/*  3345 */     if (this.CurMech.IsOmnimech()) {
/*  3346 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/*       */ 
/*  3350 */     if (this.CurMech.GetName().isEmpty()) {
/*  3351 */       Media.Messager(this, "Your mech needs a name first.");
/*  3352 */       this.tbpMainTabPane.setSelectedComponent(this.pnlBasicSetup);
/*  3353 */       this.txtMechName.requestFocusInWindow();
/*  3354 */       this.SetSource = true;
/*  3355 */       return false;
/*       */     }
/*       */     
/*       */ 
/*  3359 */     if (!this.CurMech.ValidateECM()) {
/*  3360 */       Media.Messager("This 'Mech requires an ECM system of some sort to be valid.\nPlease install an ECM system.");
/*  3361 */       this.tbpMainTabPane.setSelectedComponent(this.pnlEquipment);
/*  3362 */       this.SetSource = true;
/*  3363 */       return false;
/*       */     }
/*       */     
/*       */ 
/*  3367 */     if (this.CurMech.IsOmnimech()) {
/*  3368 */       ArrayList v = this.CurMech.GetLoadouts();
/*  3369 */       for (int i = 0; i < v.size(); i++) {
/*  3370 */         this.CurMech.SetCurLoadout(((ifMechLoadout)v.get(i)).GetName());
/*  3371 */         if (this.CurMech.GetLoadout().GetQueue().size() != 0) {
/*  3372 */           Media.Messager(this, "You must place all items in the " + 
/*  3373 */             ((ifMechLoadout)v.get(i)).GetName() + " loadout first.");
/*  3374 */           this.cmbOmniVariant.setSelectedItem(((ifMechLoadout)v.get(i)).GetName());
/*  3375 */           cmbOmniVariantActionPerformed(evt);
/*  3376 */           this.tbpMainTabPane.setSelectedComponent(this.pnlEquipment);
/*  3377 */           this.SetSource = true;
/*  3378 */           return false;
/*       */         }
/*       */       }
/*       */     }
/*  3382 */     else if (this.CurMech.GetLoadout().GetQueue().size() != 0) {
/*  3383 */       Media.Messager(this, "You must place all items first.");
/*  3384 */       this.tbpMainTabPane.setSelectedComponent(this.pnlEquipment);
/*  3385 */       this.SetSource = true;
/*  3386 */       return false;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  3391 */     if (this.CurMech.IsOmnimech()) {
/*  3392 */       ArrayList v = this.CurMech.GetLoadouts();
/*  3393 */       for (int i = 0; i < v.size(); i++) {
/*  3394 */         this.CurMech.SetCurLoadout(((ifMechLoadout)v.get(i)).GetName());
/*  3395 */         if (this.CurMech.GetCurrentTons() > this.CurMech.GetTonnage()) {
/*  3396 */           Media.Messager(this, ((ifMechLoadout)v.get(i)).GetName() + " loadout is overweight.  Reduce the weight\nto equal or below the mech's tonnage.");
/*       */           
/*  3398 */           this.cmbOmniVariant.setSelectedItem(((ifMechLoadout)v.get(i)).GetName());
/*  3399 */           cmbOmniVariantActionPerformed(evt);
/*  3400 */           this.tbpMainTabPane.setSelectedComponent(this.pnlBasicSetup);
/*  3401 */           this.SetSource = true;
/*  3402 */           return false;
/*       */         }
/*       */       }
/*       */     }
/*  3406 */     else if (this.CurMech.GetCurrentTons() > this.CurMech.GetTonnage()) {
/*  3407 */       Media.Messager(this, "This mech is overweight.  Reduce the weight to\nequal or below the mech's tonnage.");
/*  3408 */       this.tbpMainTabPane.setSelectedComponent(this.pnlBasicSetup);
/*  3409 */       this.SetSource = true;
/*  3410 */       return false;
/*       */     }
/*       */     
/*  3413 */     if (this.CurMech.IsOmnimech()) {
/*  3414 */       this.CurMech.SetCurLoadout(CurLoadout);
/*       */     }
/*  3416 */     this.SetSource = true;
/*  3417 */     return true;
/*       */   }
/*       */   
/*       */   private void ConfigureUtilsMenu(java.awt.Component c)
/*       */   {
/*  3422 */     boolean armor = (LegalArmoring(this.CurItem)) && (CommonTools.IsAllowed(abPlaceable.ArmoredAC, this.CurMech));
/*  3423 */     boolean cap = (LegalCapacitor(this.CurItem)) && (CommonTools.IsAllowed(this.PPCCapAC, this.CurMech));
/*  3424 */     boolean insul = (LegalInsulator(this.CurItem)) && (CommonTools.IsAllowed(this.LIAC, this.CurMech));
/*  3425 */     boolean caseless = (LegalCaseless(this.CurItem)) && (CommonTools.IsAllowed(this.CaselessAmmoAC, this.CurMech));
/*  3426 */     boolean lotchange = LegalLotChange(this.CurItem);
/*  3427 */     boolean turreted = LegalTurretMount(this.CurItem);
/*  3428 */     this.mnuArmorComponent.setEnabled(armor);
/*  3429 */     this.mnuAddCapacitor.setEnabled(cap);
/*  3430 */     this.mnuAddInsulator.setEnabled(insul);
/*  3431 */     this.mnuCaseless.setEnabled(caseless);
/*  3432 */     this.mnuArmorComponent.setVisible(armor);
/*  3433 */     this.mnuAddCapacitor.setVisible(cap);
/*  3434 */     this.mnuAddInsulator.setVisible(insul);
/*  3435 */     this.mnuCaseless.setVisible(caseless);
/*  3436 */     this.mnuSetLotSize.setVisible(lotchange);
/*  3437 */     this.mnuTurret.setVisible(turreted);
/*  3438 */     if (armor) {
/*  3439 */       if (this.CurItem.IsArmored()) {
/*  3440 */         this.mnuArmorComponent.setText("Unarmor Component");
/*       */       } else {
/*  3442 */         this.mnuArmorComponent.setText("Armor Component");
/*       */       }
/*       */     }
/*  3445 */     if ((turreted) && ((this.CurItem instanceof RangedWeapon))) {
/*  3446 */       if (((RangedWeapon)this.CurItem).IsTurreted()) {
/*  3447 */         this.mnuTurret.setText("Remove from Turret");
/*       */       } else {
/*  3449 */         this.mnuTurret.setText("Add to Turret");
/*       */       }
/*       */     }
/*  3452 */     if (((cap) || (insul) || (caseless)) && 
/*  3453 */       ((this.CurItem instanceof RangedWeapon))) {
/*  3454 */       if (((RangedWeapon)this.CurItem).IsUsingCapacitor()) {
/*  3455 */         this.mnuAddCapacitor.setText("Remove Capacitor");
/*       */       } else {
/*  3457 */         this.mnuAddCapacitor.setText("Add Capacitor");
/*       */       }
/*  3459 */       if (((RangedWeapon)this.CurItem).IsUsingInsulator()) {
/*  3460 */         this.mnuAddInsulator.setText("Remove Insulator");
/*       */       } else {
/*  3462 */         this.mnuAddInsulator.setText("Add Insulator");
/*       */       }
/*  3464 */       if (((RangedWeapon)this.CurItem).IsCaseless()) {
/*  3465 */         this.mnuCaseless.setText("Switch from Caseless");
/*       */       } else {
/*  3467 */         this.mnuCaseless.setText("Switch to Caseless");
/*       */       }
/*       */     }
/*       */     
/*  3471 */     if ((c == this.lstCTCrits) || (c == this.lstLTCrits) || (c == this.lstRTCrits)) {
/*  3472 */       if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3473 */         this.mnuVGLAmmo.setVisible(true);
/*  3474 */         this.mnuVGLArc.setVisible(true);
/*  3475 */         switch (((VehicularGrenadeLauncher)this.CurItem).GetAmmoType()) {
/*       */         case 1: 
/*  3477 */           this.mnuVGLAmmoFrag.setText("Fragmentation");
/*  3478 */           this.mnuVGLAmmoChaff.setText("* Chaff");
/*  3479 */           this.mnuVGLAmmoIncen.setText("Incendiary");
/*  3480 */           this.mnuVGLAmmoSmoke.setText("Smoke");
/*  3481 */           break;
/*       */         case 0: 
/*  3483 */           this.mnuVGLAmmoFrag.setText("* Fragmentation");
/*  3484 */           this.mnuVGLAmmoChaff.setText("Chaff");
/*  3485 */           this.mnuVGLAmmoIncen.setText("Incendiary");
/*  3486 */           this.mnuVGLAmmoSmoke.setText("Smoke");
/*  3487 */           break;
/*       */         case 2: 
/*  3489 */           this.mnuVGLAmmoFrag.setText("Fragmentation");
/*  3490 */           this.mnuVGLAmmoChaff.setText("Chaff");
/*  3491 */           this.mnuVGLAmmoIncen.setText("* Incendiary");
/*  3492 */           this.mnuVGLAmmoSmoke.setText("Smoke");
/*  3493 */           break;
/*       */         case 3: 
/*  3495 */           this.mnuVGLAmmoFrag.setText("Fragmentation");
/*  3496 */           this.mnuVGLAmmoChaff.setText("Chaff");
/*  3497 */           this.mnuVGLAmmoIncen.setText("Incendiary");
/*  3498 */           this.mnuVGLAmmoSmoke.setText("* Smoke");
/*       */         }
/*       */         
/*  3501 */         switch (((VehicularGrenadeLauncher)this.CurItem).GetCurrentArc()) {
/*       */         case 0: 
/*  3503 */           this.mnuVGLArcFore.setText("* Fore");
/*  3504 */           this.mnuVGLArcForeSide.setText("Fore-Side");
/*  3505 */           this.mnuVGLArcRear.setText("Rear");
/*  3506 */           this.mnuVGLArcRearSide.setText("Rear-Side");
/*  3507 */           break;
/*       */         case 2: 
/*  3509 */           this.mnuVGLArcFore.setText("Fore");
/*  3510 */           this.mnuVGLArcForeSide.setText("* Fore-Side");
/*  3511 */           this.mnuVGLArcRear.setText("Rear");
/*  3512 */           this.mnuVGLArcRearSide.setText("Rear-Side");
/*  3513 */           break;
/*       */         case 1: 
/*  3515 */           this.mnuVGLArcFore.setText("Fore");
/*  3516 */           this.mnuVGLArcForeSide.setText("Fore-Side");
/*  3517 */           this.mnuVGLArcRear.setText("* Rear");
/*  3518 */           this.mnuVGLArcRearSide.setText("Rear-Side");
/*  3519 */           break;
/*       */         case 3: 
/*  3521 */           this.mnuVGLArcFore.setText("Fore");
/*  3522 */           this.mnuVGLArcForeSide.setText("Fore-Side");
/*  3523 */           this.mnuVGLArcRear.setText("Rear");
/*  3524 */           this.mnuVGLArcRearSide.setText("* Rear-Side");
/*       */         }
/*       */       }
/*       */       else {
/*  3528 */         this.mnuVGLAmmo.setVisible(false);
/*  3529 */         this.mnuVGLArc.setVisible(false);
/*       */       }
/*       */     } else {
/*  3532 */       this.mnuVGLAmmo.setVisible(false);
/*  3533 */       this.mnuVGLArc.setVisible(false);
/*       */     }
/*  3535 */     if (this.CurMech.GetLoadout().Find(this.CurItem) < 11) {
/*  3536 */       if ((this.CurItem instanceof components.EmptyItem)) {
/*  3537 */         this.mnuUnallocateAll.setText("Unallocate All");
/*  3538 */         this.mnuUnallocateAll.setEnabled(false);
/*  3539 */       } else if (!this.CurItem.LocationLocked()) {
/*  3540 */         if (this.CurItem.Contiguous()) {
/*  3541 */           this.mnuUnallocateAll.setText("Unallocate " + this.CurItem.CritName());
/*       */         } else {
/*  3543 */           this.mnuUnallocateAll.setText("Unallocate All");
/*       */         }
/*  3545 */         this.mnuUnallocateAll.setEnabled(true);
/*       */       } else {
/*  3547 */         this.mnuUnallocateAll.setText("Unallocate All");
/*  3548 */         this.mnuUnallocateAll.setEnabled(false);
/*       */       }
/*  3550 */       if ((c == this.lstHDCrits) || (c == this.lstCTCrits) || (c == this.lstLTCrits) || (c == this.lstRTCrits) || (c == this.lstLLCrits) || (c == this.lstRLCrits) || ((this.CurMech.IsQuad()) && ((c == this.lstRACrits) || (c == this.lstLACrits)))) {
/*  3551 */         if (this.CurItem.CanMountRear()) {
/*  3552 */           this.mnuMountRear.setEnabled(true);
/*  3553 */           if (this.CurItem.IsMountedRear()) {
/*  3554 */             this.mnuMountRear.setText("Unmount Rear ");
/*       */           } else {
/*  3556 */             this.mnuMountRear.setText("Mount Rear ");
/*       */           }
/*       */         } else {
/*  3559 */           this.mnuMountRear.setEnabled(false);
/*  3560 */           this.mnuMountRear.setText("Mount Rear ");
/*       */         }
/*       */       } else {
/*  3563 */         this.mnuMountRear.setEnabled(false);
/*  3564 */         this.mnuMountRear.setText("Mount Rear ");
/*       */       }
/*  3566 */       if (this.CurItem.Contiguous()) {
/*  3567 */         components.EquipmentCollection C = this.CurMech.GetLoadout().GetCollection(this.CurItem);
/*  3568 */         if (C == null) {
/*  3569 */           this.mnuAuto.setEnabled(false);
/*  3570 */           this.mnuSelective.setEnabled(false);
/*       */         } else {
/*  3572 */           this.mnuAuto.setEnabled(true);
/*  3573 */           this.mnuSelective.setEnabled(true);
/*       */         }
/*       */       } else {
/*  3576 */         this.mnuSelective.setEnabled(true);
/*  3577 */         this.mnuAuto.setEnabled(true);
/*       */       }
/*       */     } else {
/*  3580 */       if (this.CurItem.Contiguous()) {
/*  3581 */         components.EquipmentCollection C = this.CurMech.GetLoadout().GetCollection(this.CurItem);
/*  3582 */         if (C == null) {
/*  3583 */           this.mnuAuto.setEnabled(false);
/*  3584 */           this.mnuSelective.setEnabled(false);
/*       */         } else {
/*  3586 */           this.mnuAuto.setEnabled(true);
/*  3587 */           this.mnuSelective.setEnabled(true);
/*       */         }
/*       */       } else {
/*  3590 */         this.mnuSelective.setEnabled(true);
/*  3591 */         this.mnuAuto.setEnabled(true);
/*       */       }
/*  3593 */       this.mnuUnallocateAll.setText("Unallocate All");
/*  3594 */       this.mnuUnallocateAll.setEnabled(false);
/*  3595 */       this.mnuMountRear.setEnabled(false);
/*  3596 */       this.mnuMountRear.setText("Mount Rear ");
/*       */     }
/*  3598 */     if ((this.CurItem instanceof Equipment)) {
/*  3599 */       if (((Equipment)this.CurItem).IsVariableSize()) {
/*  3600 */         this.mnuSetVariable.setVisible(true);
/*       */       } else {
/*  3602 */         this.mnuSetVariable.setVisible(false);
/*       */       }
/*       */     } else {
/*  3605 */       this.mnuSetVariable.setVisible(false);
/*       */     }
/*  3607 */     if ((this.CurItem.CoreComponent()) || (this.CurItem.LocationLinked())) {
/*  3608 */       this.mnuRemoveItem.setEnabled(false);
/*       */     } else {
/*  3610 */       this.mnuRemoveItem.setEnabled(true);
/*       */     }
/*       */   }
/*       */   
/*       */   private void SetAmmoLotSize() {
/*  3615 */     if ((this.CurItem instanceof Ammunition)) {
/*  3616 */       dlgAmmoLotSize ammo = new dlgAmmoLotSize(this, true, (Ammunition)this.CurItem);
/*  3617 */       ammo.setLocationRelativeTo(this);
/*  3618 */       ammo.setVisible(true);
/*       */     }
/*  3620 */     RefreshSummary();
/*  3621 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void ArmorComponent()
/*       */   {
/*  3626 */     if (this.CurItem.IsArmored()) {
/*  3627 */       this.CurItem.ArmorComponent(false);
/*       */     } else {
/*  3629 */       this.CurItem.ArmorComponent(true);
/*       */     }
/*  3631 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void PPCCapacitor()
/*       */   {
/*  3636 */     if ((this.CurItem instanceof RangedWeapon)) {
/*  3637 */       if (((RangedWeapon)this.CurItem).IsUsingCapacitor()) {
/*  3638 */         abPlaceable p = ((RangedWeapon)this.CurItem).GetCapacitor();
/*  3639 */         ((RangedWeapon)this.CurItem).UseCapacitor(false);
/*  3640 */         this.CurMech.GetLoadout().Remove(p);
/*       */       } else {
/*  3642 */         ((RangedWeapon)this.CurItem).UseCapacitor(true);
/*  3643 */         abPlaceable p = ((RangedWeapon)this.CurItem).GetCapacitor();
/*  3644 */         LocationIndex Loc = this.CurMech.GetLoadout().FindIndex(this.CurItem);
/*  3645 */         if (Loc.Location != -1) {
/*       */           try {
/*  3647 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(Loc.Location), p, Loc.Index + this.CurItem.NumCrits(), 1);
/*       */           }
/*       */           catch (Exception e) {
/*       */             try {
/*  3651 */               this.CurMech.GetLoadout().UnallocateAll(this.CurItem, false);
/*       */ 
/*       */ 
/*       */             }
/*       */             catch (Exception e1)
/*       */             {
/*       */ 
/*  3658 */               Media.Messager(this, "Fatal error adding a PPC Capacitor:\n" + e.getMessage() + "\nThe Capacitor will be removed.");
/*  3659 */               ((RangedWeapon)this.CurItem).UseCapacitor(false);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  3665 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void LaserInsulator()
/*       */   {
/*  3670 */     if ((this.CurItem instanceof RangedWeapon)) {
/*  3671 */       if (((RangedWeapon)this.CurItem).IsUsingInsulator()) {
/*  3672 */         abPlaceable p = ((RangedWeapon)this.CurItem).GetInsulator();
/*  3673 */         ((RangedWeapon)this.CurItem).UseInsulator(false);
/*  3674 */         this.CurMech.GetLoadout().Remove(p);
/*       */       } else {
/*  3676 */         ((RangedWeapon)this.CurItem).UseInsulator(true);
/*  3677 */         abPlaceable p = ((RangedWeapon)this.CurItem).GetInsulator();
/*  3678 */         LocationIndex Loc = this.CurMech.GetLoadout().FindIndex(this.CurItem);
/*  3679 */         if (Loc.Location != -1) {
/*       */           try {
/*  3681 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(Loc.Location), p, Loc.Index + this.CurItem.NumCrits(), 1);
/*       */           }
/*       */           catch (Exception e) {
/*       */             try {
/*  3685 */               this.CurMech.GetLoadout().UnallocateAll(this.CurItem, false);
/*       */ 
/*       */ 
/*       */             }
/*       */             catch (Exception e1)
/*       */             {
/*       */ 
/*  3692 */               Media.Messager(this, "Fatal error adding a Laser Insulator:\n" + e.getMessage() + "\nThe Insulator will be removed.");
/*  3693 */               ((RangedWeapon)this.CurItem).UseInsulator(false);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  3699 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void TurretMount() {
/*  3703 */     if ((this.CurItem instanceof RangedWeapon)) {
/*  3704 */       RangedWeapon w = (RangedWeapon)this.CurItem;
/*  3705 */       int location = this.CurMech.GetLoadout().Find(this.CurItem);
/*  3706 */       if (w.IsTurreted()) {
/*  3707 */         if (location == 0) {
/*  3708 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetHDTurret());
/*  3709 */         } else if (location == 2) {
/*  3710 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetLTTurret());
/*  3711 */         } else if (location == 3) {
/*  3712 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetRTTurret());
/*       */         } else {
/*  3714 */           Media.Messager(this, "Cannot remove from turret!");
/*       */         }
/*       */         
/*       */       }
/*  3718 */       else if (location == 0) {
/*  3719 */         w.AddToTurret(this.CurMech.GetLoadout().GetHDTurret());
/*  3720 */       } else if (location == 2) {
/*  3721 */         w.AddToTurret(this.CurMech.GetLoadout().GetLTTurret());
/*  3722 */       } else if (location == 3) {
/*  3723 */         w.AddToTurret(this.CurMech.GetLoadout().GetRTTurret());
/*       */       } else {
/*  3725 */         Media.Messager(this, "Cannot add to turret!");
/*  3726 */         return;
/*       */       }
/*       */     }
/*  3729 */     else if ((this.CurItem instanceof components.MGArray)) {
/*  3730 */       components.MGArray w = (components.MGArray)this.CurItem;
/*  3731 */       int location = this.CurMech.GetLoadout().Find(this.CurItem);
/*  3732 */       if (w.IsTurreted()) {
/*  3733 */         if (location == 0) {
/*  3734 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetHDTurret());
/*  3735 */         } else if (location == 2) {
/*  3736 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetLTTurret());
/*  3737 */         } else if (location == 3) {
/*  3738 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetRTTurret());
/*       */         } else {
/*  3740 */           Media.Messager(this, "Cannot remove from turret!");
/*       */         }
/*       */         
/*       */       }
/*  3744 */       else if (location == 0) {
/*  3745 */         w.AddToTurret(this.CurMech.GetLoadout().GetHDTurret());
/*  3746 */       } else if (location == 2) {
/*  3747 */         w.AddToTurret(this.CurMech.GetLoadout().GetLTTurret());
/*  3748 */       } else if (location == 3) {
/*  3749 */         w.AddToTurret(this.CurMech.GetLoadout().GetRTTurret());
/*       */       } else {
/*  3751 */         Media.Messager(this, "Cannot add to turret!");
/*  3752 */         return;
/*       */       }
/*       */     }
/*       */     
/*  3756 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void SwitchCaseless() {
/*  3760 */     if ((this.CurItem instanceof RangedWeapon)) {
/*  3761 */       RangedWeapon r = (RangedWeapon)this.CurItem;
/*       */       
/*  3763 */       int origIDX = r.GetAmmoIndex();
/*       */       
/*       */ 
/*  3766 */       r.SetCaseless(!r.IsCaseless());
/*  3767 */       int newIDX = r.GetAmmoIndex();
/*       */       
/*       */ 
/*  3770 */       ArrayList check = this.CurMech.GetLoadout().GetNonCore();
/*  3771 */       ArrayList replace = new ArrayList();
/*       */       
/*  3773 */       boolean HasOrig = false;
/*  3774 */       for (int i = 0; i < check.size(); i++) {
/*  3775 */         abPlaceable p = (abPlaceable)check.get(i);
/*  3776 */         if (((p instanceof RangedWeapon)) && 
/*  3777 */           (((RangedWeapon)p).GetAmmoIndex() == origIDX)) {
/*  3778 */           HasOrig = true;
/*       */         }
/*       */         
/*  3781 */         if ((p instanceof Ammunition)) {
/*  3782 */           replace.add(p);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  3787 */       if (!HasOrig) {
/*  3788 */         Object[] newammo = this.data.GetEquipment().GetAmmo(newIDX, this.CurMech);
/*  3789 */         for (int i = 0; i < replace.size(); i++) {
/*  3790 */           abPlaceable p = (abPlaceable)replace.get(i);
/*  3791 */           if (((Ammunition)p).GetAmmoIndex() == origIDX) {
/*  3792 */             this.CurMech.GetLoadout().Remove(p);
/*  3793 */             if (newammo.length > 0) {
/*  3794 */               p = this.data.GetEquipment().GetCopy((abPlaceable)newammo[0], this.CurMech);
/*  3795 */               this.CurMech.GetLoadout().AddToQueue(p);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  3801 */     RefreshSummary();
/*  3802 */     RefreshInfoPane();
/*  3803 */     SetWeaponChoosers();
/*  3804 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   public void SetVGLArcFore() {
/*  3808 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3809 */       ((VehicularGrenadeLauncher)this.CurItem).SetArcFore();
/*  3810 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLArcRear() {
/*  3815 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3816 */       ((VehicularGrenadeLauncher)this.CurItem).SetArcRear();
/*  3817 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLArcForeSide() {
/*  3822 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3823 */       ((VehicularGrenadeLauncher)this.CurItem).SetArcForeSide();
/*  3824 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLArcRearSide() {
/*  3829 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3830 */       ((VehicularGrenadeLauncher)this.CurItem).SetArcRearSide();
/*  3831 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLAmmoFrag() {
/*  3836 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3837 */       ((VehicularGrenadeLauncher)this.CurItem).SetAmmoFrag();
/*  3838 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLAmmoChaff() {
/*  3843 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3844 */       ((VehicularGrenadeLauncher)this.CurItem).SetAmmoChaff();
/*  3845 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLAmmoIncendiary() {
/*  3850 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3851 */       ((VehicularGrenadeLauncher)this.CurItem).SetAmmoIncen();
/*  3852 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLAmmoSmoke() {
/*  3857 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3858 */       ((VehicularGrenadeLauncher)this.CurItem).SetAmmoSmoke();
/*  3859 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVariableSize() {
/*  3864 */     if (((this.CurItem instanceof Equipment)) && 
/*  3865 */       (((Equipment)this.CurItem).IsVariableSize())) {
/*  3866 */       dlgVariableSize SetTons = new dlgVariableSize(this, true, (Equipment)this.CurItem);
/*  3867 */       SetTons.setLocationRelativeTo(this);
/*  3868 */       SetTons.setVisible(true);
/*  3869 */       LocationIndex li = this.CurMech.GetLoadout().FindIndex(this.CurItem);
/*  3870 */       if (li.Location >= 0) {
/*       */         try {
/*  3872 */           this.CurMech.GetLoadout().UnallocateAll(this.CurItem, false);
/*  3873 */           this.CurMech.GetLoadout().AddTo(this.CurItem, li.Location, li.Index);
/*       */         } catch (Exception e) {
/*  3875 */           this.CurMech.GetLoadout().UnallocateAll(this.CurItem, true);
/*       */         }
/*       */       }
/*  3878 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public boolean LegalArmoring(abPlaceable p)
/*       */   {
/*  3885 */     if (p.CanArmor()) {
/*  3886 */       if (this.CurMech.GetLoadout().GetName().equals("Base Loadout")) {
/*  3887 */         return true;
/*       */       }
/*  3889 */       if ((p instanceof components.Engine)) return false;
/*  3890 */       if ((p instanceof components.Gyro)) return false;
/*  3891 */       if ((p instanceof components.Cockpit)) return false;
/*  3892 */       if ((p instanceof components.Actuator)) {
/*  3893 */         if (!((components.Actuator)p).IsOmniArmorable()) return false;
/*  3894 */         LocationIndex Loc = this.CurMech.GetLoadout().FindIndex(p);
/*  3895 */         if ((Loc.Location == 4) && (Loc.Index == 2) && 
/*  3896 */           (this.CurMech.GetBaseLoadout().GetActuators().LeftLowerInstalled())) { return false;
/*       */         }
/*  3898 */         if ((Loc.Location == 5) && (Loc.Index == 2) && 
/*  3899 */           (this.CurMech.GetBaseLoadout().GetActuators().RightLowerInstalled())) { return false;
/*       */         }
/*  3901 */         if ((Loc.Location == 4) && (Loc.Index == 3) && 
/*  3902 */           (this.CurMech.GetBaseLoadout().GetActuators().LeftHandInstalled())) { return false;
/*       */         }
/*  3904 */         if ((Loc.Location == 5) && (Loc.Index == 3) && 
/*  3905 */           (this.CurMech.GetBaseLoadout().GetActuators().RightHandInstalled())) { return false;
/*       */         }
/*       */       }
/*  3908 */       if ((p instanceof components.SimplePlaceable)) return false;
/*  3909 */       if (this.CurMech.GetBaseLoadout().GetNonCore().contains(p)) return false;
/*  3910 */       return true;
/*       */     }
/*       */     
/*  3913 */     return false;
/*       */   }
/*       */   
/*       */   public boolean LegalCapacitor(abPlaceable p)
/*       */   {
/*  3918 */     if (!(p instanceof RangedWeapon)) return false;
/*  3919 */     return ((RangedWeapon)p).CanUseCapacitor();
/*       */   }
/*       */   
/*       */   public boolean LegalInsulator(abPlaceable p) {
/*  3923 */     if (!(p instanceof RangedWeapon)) return false;
/*  3924 */     return ((RangedWeapon)p).CanUseInsulator();
/*       */   }
/*       */   
/*       */   public boolean LegalCaseless(abPlaceable p) {
/*  3928 */     if (!(p instanceof RangedWeapon)) return false;
/*  3929 */     return ((RangedWeapon)p).CanUseCaselessAmmo();
/*       */   }
/*       */   
/*       */   public boolean LegalTurretMount(abPlaceable p) {
/*  3933 */     if ((!(p instanceof RangedWeapon)) && (!(p instanceof components.MGArray))) return false;
/*  3934 */     int location = this.CurMech.GetLoadout().Find(p);
/*  3935 */     if (location == 0) {
/*  3936 */       if ((this.CurMech.IsOmnimech()) && 
/*  3937 */         (this.CurMech.GetBaseLoadout().GetHDTurret() == this.CurMech.GetLoadout().GetHDTurret())) {
/*  3938 */         return false;
/*       */       }
/*       */       
/*  3941 */       return this.CurMech.GetLoadout().HasHDTurret();
/*       */     }
/*  3943 */     if (location == 2) {
/*  3944 */       if ((this.CurMech.IsOmnimech()) && 
/*  3945 */         (this.CurMech.GetBaseLoadout().GetLTTurret() == this.CurMech.GetLoadout().GetLTTurret())) {
/*  3946 */         return false;
/*       */       }
/*       */       
/*  3949 */       return this.CurMech.GetLoadout().HasLTTurret();
/*       */     }
/*  3951 */     if (location == 3) {
/*  3952 */       if ((this.CurMech.IsOmnimech()) && 
/*  3953 */         (this.CurMech.GetBaseLoadout().GetRTTurret() == this.CurMech.GetLoadout().GetRTTurret())) {
/*  3954 */         return false;
/*       */       }
/*       */       
/*  3957 */       return this.CurMech.GetLoadout().HasRTTurret();
/*       */     }
/*  3959 */     return false;
/*       */   }
/*       */   
/*       */   public boolean LegalLotChange(abPlaceable p) {
/*  3963 */     if (!(p instanceof Ammunition)) return false;
/*  3964 */     if (this.CurMech.UsingFractionalAccounting()) return true;
/*  3965 */     return false;
/*       */   }
/*       */   
/*       */   private void PrintMech(Mech m) {
/*  3969 */     ssw.print.Printer printer = new ssw.print.Printer(this);
/*  3970 */     printer.Print(m);
/*       */   }
/*       */   
/*       */   private void UpdateBasicChart() {
/*  3974 */     int[] fchart = null;int[] lchart = null;int[] rchart = null;int[] rrchart = null;
/*  3975 */     if (this.chkChartFront.isSelected()) {
/*  3976 */       fchart = GetFrontDamageChart();
/*       */     }
/*  3978 */     if (this.chkChartRear.isSelected()) {
/*  3979 */       rrchart = GetRearDamageChart();
/*       */     }
/*  3981 */     if (this.chkChartRight.isSelected()) {
/*  3982 */       rchart = GetRightArmDamageChart();
/*       */     }
/*  3984 */     if (this.chkChartLeft.isSelected()) {
/*  3985 */       lchart = GetLeftArmDamageChart();
/*       */     }
/*  3987 */     int gridx = 1;
/*  3988 */     int gridy = 1;
/*  3989 */     if (fchart != null) {
/*  3990 */       for (int i = 0; i < fchart.length; i++) {
/*  3991 */         if (fchart[i] > 0) {
/*  3992 */           if (fchart[i] > gridy) gridy = fchart[i];
/*  3993 */           if (i > gridx) gridx = i;
/*       */         }
/*       */       }
/*       */     }
/*  3997 */     if (rchart != null) {
/*  3998 */       for (int i = 0; i < rchart.length; i++) {
/*  3999 */         if (rchart[i] > 0) {
/*  4000 */           if (rchart[i] > gridy) gridy = rchart[i];
/*  4001 */           if (i > gridx) gridx = i;
/*       */         }
/*       */       }
/*       */     }
/*  4005 */     if (lchart != null) {
/*  4006 */       for (int i = 0; i < lchart.length; i++) {
/*  4007 */         if (lchart[i] > 0) {
/*  4008 */           if (lchart[i] > gridy) gridy = lchart[i];
/*  4009 */           if (i > gridx) gridx = i;
/*       */         }
/*       */       }
/*       */     }
/*  4013 */     if (rrchart != null) {
/*  4014 */       for (int i = 0; i < rrchart.length; i++) {
/*  4015 */         if (rrchart[i] > 0) {
/*  4016 */           if (rrchart[i] > gridy) gridy = rrchart[i];
/*  4017 */           if (i > gridx) gridx = i;
/*       */         }
/*       */       }
/*       */     }
/*  4021 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*  4022 */     int TotalDamage = 0;
/*  4023 */     double TonsWeapons = 0.0D;double TonsEquips = 0.0D;
/*       */     
/*  4025 */     for (int i = 0; i < v.size(); i++) {
/*  4026 */       abPlaceable p = (abPlaceable)v.get(i);
/*       */       
/*       */ 
/*  4029 */       if ((p instanceof ifWeapon))
/*       */       {
/*  4031 */         TonsWeapons += p.GetTonnage();
/*  4032 */         TotalDamage += GetMaxDamage((ifWeapon)p);
/*       */       }
/*  4034 */       else if ((p instanceof Ammunition)) {
/*  4035 */         TonsWeapons += p.GetTonnage();
/*       */       } else {
/*  4037 */         TonsEquips += p.GetTonnage();
/*       */       }
/*       */     }
/*       */     
/*  4041 */     TonsEquips += this.CurMech.GetCaseTonnage();
/*  4042 */     TonsEquips += this.CurMech.GetCASEIITonnage();
/*  4043 */     TonsEquips += this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons();
/*       */     
/*  4045 */     ((DamageChart)this.pnlDamageChart).ClearCharts();
/*  4046 */     if (this.chkShowTextNotGraph.isSelected()) {
/*  4047 */       ((DamageChart)this.pnlDamageChart).SetTextView(true);
/*  4048 */       if (this.chkChartFront.isSelected()) {
/*  4049 */         ((DamageChart)this.pnlDamageChart).AddChart(fchart, Color.RED);
/*       */       }
/*  4051 */       if (this.chkChartRight.isSelected()) {
/*  4052 */         ((DamageChart)this.pnlDamageChart).AddChart(rchart, Color.GREEN);
/*       */       }
/*  4054 */       if (this.chkChartLeft.isSelected()) {
/*  4055 */         ((DamageChart)this.pnlDamageChart).AddChart(lchart, Color.ORANGE);
/*       */       }
/*  4057 */       if (this.chkChartRear.isSelected()) {
/*  4058 */         ((DamageChart)this.pnlDamageChart).AddChart(rrchart, Color.PINK);
/*       */       }
/*       */     } else {
/*  4061 */       ((DamageChart)this.pnlDamageChart).SetTextView(false);
/*  4062 */       ((DamageChart)this.pnlDamageChart).SetGridSize(gridx + 1, gridy + 1);
/*  4063 */       if (this.chkChartRear.isSelected()) {
/*  4064 */         ((DamageChart)this.pnlDamageChart).AddChart(rrchart, Color.PINK);
/*       */       }
/*  4066 */       if (this.chkChartLeft.isSelected()) {
/*  4067 */         ((DamageChart)this.pnlDamageChart).AddChart(lchart, Color.ORANGE);
/*       */       }
/*  4069 */       if (this.chkChartRight.isSelected()) {
/*  4070 */         ((DamageChart)this.pnlDamageChart).AddChart(rchart, Color.GREEN);
/*       */       }
/*  4072 */       if (this.chkChartFront.isSelected()) {
/*  4073 */         ((DamageChart)this.pnlDamageChart).AddChart(fchart, Color.RED);
/*       */       }
/*       */     }
/*  4076 */     this.lblTonPercStructure.setText(String.format("%1$,.2f", new Object[] { Double.valueOf((this.CurMech.GetIntStruc().GetTonnage() + this.CurMech.GetCockpit().GetTonnage() + this.CurMech.GetGyro().GetTonnage()) / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4077 */     this.lblTonPercEngine.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetEngine().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4078 */     this.lblTonPercHeatSinks.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetHeatSinks().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4079 */     this.lblTonPercEnhance.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetPhysEnhance().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4080 */     this.lblTonPercArmor.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetArmor().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4081 */     this.lblTonPercJumpJets.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetJumpJets().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4082 */     this.lblTonPercWeapons.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(TonsWeapons / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4083 */     this.lblTonPercEquips.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(TonsEquips / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4084 */     this.lblDamagePerTon.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(TotalDamage / this.CurMech.GetTonnage()) }));
/*       */   }
/*       */   
/*       */   private int GetMaxDamage(ifWeapon w) {
/*  4088 */     int mult = 1;
/*  4089 */     if (w.IsUltra()) {
/*  4090 */       mult = 2;
/*       */     }
/*  4092 */     if (w.IsRotary()) {
/*  4093 */       mult = 6;
/*       */     }
/*  4095 */     if ((w.GetDamageLong() >= w.GetDamageMedium()) && (w.GetDamageLong() >= w.GetDamageShort())) {
/*  4096 */       if (w.GetWeaponClass() == 2) {
/*  4097 */         return w.GetDamageLong() * mult * w.ClusterSize();
/*       */       }
/*  4099 */       return w.GetDamageLong() * mult;
/*       */     }
/*       */     
/*  4102 */     if ((w.GetDamageMedium() >= w.GetDamageLong()) && (w.GetDamageMedium() >= w.GetDamageShort())) {
/*  4103 */       if (w.GetWeaponClass() == 2) {
/*  4104 */         return w.GetDamageMedium() * mult * w.ClusterSize();
/*       */       }
/*  4106 */       return w.GetDamageMedium() * mult;
/*       */     }
/*       */     
/*  4109 */     if (w.GetWeaponClass() == 2) {
/*  4110 */       return w.GetDamageShort() * mult * w.ClusterSize();
/*       */     }
/*  4112 */     return w.GetDamageShort() * mult;
/*       */   }
/*       */   
/*       */   private JCheckBox chkBoosters;
/*       */   private JCheckBox chkCLPS;
/*       */   private JCheckBox chkCTCASE;
/*       */   private JCheckBox chkCTCASE2;
/*       */   
/*       */   public void lostOwnership(java.awt.datatransfer.Clipboard aClipboard, java.awt.datatransfer.Transferable aContents) {}
/*       */   
/*       */   public int GetLocation(JList list) {
/*  4123 */     if (list == this.lstHDCrits) return 0;
/*  4124 */     if (list == this.lstCTCrits) return 1;
/*  4125 */     if (list == this.lstLTCrits) return 2;
/*  4126 */     if (list == this.lstRTCrits) return 3;
/*  4127 */     if (list == this.lstLACrits) return 4;
/*  4128 */     if (list == this.lstRACrits) return 5;
/*  4129 */     if (list == this.lstLLCrits) return 6;
/*  4130 */     if (list == this.lstRLCrits) return 7;
/*  4131 */     return -1;
/*       */   }
/*       */   
/*       */   public void CheckTonnage(boolean RulesChange)
/*       */   {
/*  4136 */     if (RulesChange) {
/*  4137 */       if ((!this.CurMech.IsIndustrialmech()) && 
/*  4138 */         (this.CurMech.GetRulesLevel() < 3) && (this.CurMech.GetTonnage() < 20)) {
/*  4139 */         this.cmbTonnage.setSelectedItem("20");
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*  4144 */     else if (!this.CurMech.IsIndustrialmech())
/*       */     {
/*  4146 */       if ((this.CurMech.GetRulesLevel() < 3) && (this.CurMech.GetTonnage() < 20) && 
/*  4147 */         (this.CurMech.GetTonnage() < 20)) {
/*  4148 */         this.cmbRulesLevel.setSelectedIndex(3);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void CheckFileName(String s)
/*       */     throws Exception
/*       */   {
/*  4156 */     if (s.contains("\\")) {
/*  4157 */       throw new Exception("The Mech name or model contains a back slash\nwhich should be removed before saving.");
/*       */     }
/*  4159 */     if (s.contains("/")) {
/*  4160 */       throw new Exception("The Mech name or model contains a forward slash\nwhich should be removed before saving.");
/*       */     }
/*  4162 */     if (s.contains("*")) {
/*  4163 */       throw new Exception("The Mech name or model contains an asterisk\nwhich should be removed before saving.");
/*       */     }
/*       */   }
/*       */   
/*       */   private void SolidifyJJManufacturer()
/*       */   {
/*  4169 */     if (((!this.txtJJModel.getText().equals("")) || (!this.CurMech.GetJJModel().equals(""))) && 
/*  4170 */       (!this.txtJJModel.getText().equals(this.CurMech.GetJJModel()))) {
/*  4171 */       this.CurMech.SetJJModel(this.txtJJModel.getText());
/*       */     }
/*       */     
/*  4174 */     this.txtJJModel.setText(this.CurMech.GetJJModel());
/*       */   }
/*       */   
/*       */   private int[] GetFrontDamageChart()
/*       */   {
/*  4179 */     int[] chart = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*       */     
/*       */ 
/*  4182 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*       */     
/*  4184 */     for (int i = 0; i < 40; i++) {
/*  4185 */       for (int j = 0; j < v.size(); j++) {
/*  4186 */         if ((v.get(j) instanceof ifWeapon)) {
/*  4187 */           ifWeapon w = (ifWeapon)v.get(j);
/*  4188 */           if (!((abPlaceable)w).IsMountedRear()) {
/*  4189 */             if (this.chkAverageDamage.isSelected()) {
/*  4190 */               chart[i] += CommonTools.GetAverageDamageAtRange(w, i);
/*       */             } else {
/*  4192 */               chart[i] += GetDamageAtRange(w, i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  4198 */     return chart;
/*       */   }
/*       */   
/*       */   private int[] GetRightArmDamageChart()
/*       */   {
/*  4203 */     int[] chart = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*       */     
/*       */ 
/*  4206 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*       */     
/*  4208 */     for (int i = 0; i < 40; i++) {
/*  4209 */       for (int j = 0; j < v.size(); j++) {
/*  4210 */         if ((v.get(j) instanceof ifWeapon)) {
/*  4211 */           ifWeapon w = (ifWeapon)v.get(j);
/*  4212 */           if (this.CurMech.GetLoadout().Find((abPlaceable)w) == 5) {
/*  4213 */             if (this.chkAverageDamage.isSelected()) {
/*  4214 */               chart[i] += CommonTools.GetAverageDamageAtRange(w, i);
/*       */             } else {
/*  4216 */               chart[i] += GetDamageAtRange(w, i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  4222 */     return chart;
/*       */   }
/*       */   
/*       */   private int[] GetLeftArmDamageChart()
/*       */   {
/*  4227 */     int[] chart = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*       */     
/*       */ 
/*  4230 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*       */     
/*  4232 */     for (int i = 0; i < 40; i++) {
/*  4233 */       for (int j = 0; j < v.size(); j++) {
/*  4234 */         if ((v.get(j) instanceof ifWeapon)) {
/*  4235 */           ifWeapon w = (ifWeapon)v.get(j);
/*  4236 */           if (this.CurMech.GetLoadout().Find((abPlaceable)w) == 4) {
/*  4237 */             if (this.chkAverageDamage.isSelected()) {
/*  4238 */               chart[i] += CommonTools.GetAverageDamageAtRange(w, i);
/*       */             } else {
/*  4240 */               chart[i] += GetDamageAtRange(w, i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  4246 */     return chart;
/*       */   }
/*       */   
/*       */   private int[] GetRearDamageChart()
/*       */   {
/*  4251 */     int[] chart = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*       */     
/*       */ 
/*  4254 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*  4255 */     boolean flip = (!this.CurMech.GetLoadout().GetActuators().LeftLowerInstalled()) || (!this.CurMech.GetLoadout().GetActuators().RightLowerInstalled());
/*       */     
/*  4257 */     for (int i = 0; i < 40; i++) {
/*  4258 */       for (int j = 0; j < v.size(); j++) {
/*  4259 */         if ((v.get(j) instanceof ifWeapon)) {
/*  4260 */           ifWeapon w = (ifWeapon)v.get(j);
/*  4261 */           int Loc = this.CurMech.GetLoadout().Find((abPlaceable)w);
/*  4262 */           if ((((abPlaceable)w).IsMountedRear()) || (((Loc == 4) || (Loc == 5)) && (flip))) {
/*  4263 */             if (this.chkAverageDamage.isSelected()) {
/*  4264 */               chart[i] += CommonTools.GetAverageDamageAtRange(w, i);
/*       */             } else {
/*  4266 */               chart[i] += GetDamageAtRange(w, i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  4272 */     return chart;
/*       */   }
/*       */   
/*       */   private int GetDamageAtRange(ifWeapon w, int range) {
/*  4276 */     int mult = 1;
/*  4277 */     if (w.IsUltra()) {
/*  4278 */       mult = 2;
/*       */     }
/*  4280 */     if (w.IsRotary()) {
/*  4281 */       mult = 6;
/*       */     }
/*  4283 */     if ((w instanceof components.MGArray)) {
/*  4284 */       mult = ((components.MGArray)w).GetNumMGs();
/*       */     }
/*       */     
/*  4287 */     if (w.GetRangeLong() <= 0) {
/*  4288 */       if (w.GetRangeMedium() <= 0) {
/*  4289 */         if (range <= w.GetRangeShort()) {
/*  4290 */           if (w.GetWeaponClass() == 2) {
/*  4291 */             return w.GetDamageShort() * mult * w.ClusterSize();
/*       */           }
/*  4293 */           return w.GetDamageShort() * mult;
/*       */         }
/*       */         
/*  4296 */         return 0;
/*       */       }
/*       */       
/*  4299 */       if (range <= w.GetRangeShort()) {
/*  4300 */         if (w.GetWeaponClass() == 2) {
/*  4301 */           return w.GetDamageShort() * mult * w.ClusterSize();
/*       */         }
/*  4303 */         return w.GetDamageShort() * mult;
/*       */       }
/*  4305 */       if (range <= w.GetRangeMedium()) {
/*  4306 */         if (w.GetWeaponClass() == 2) {
/*  4307 */           return w.GetDamageMedium() * mult * w.ClusterSize();
/*       */         }
/*  4309 */         return w.GetDamageMedium() * mult;
/*       */       }
/*       */       
/*  4312 */       return 0;
/*       */     }
/*       */     
/*       */ 
/*  4316 */     if (range <= w.GetRangeShort()) {
/*  4317 */       if (w.GetWeaponClass() == 2) {
/*  4318 */         return w.GetDamageShort() * mult * w.ClusterSize();
/*       */       }
/*  4320 */       return w.GetDamageShort() * mult;
/*       */     }
/*  4322 */     if (range <= w.GetRangeMedium()) {
/*  4323 */       if (w.GetWeaponClass() == 2) {
/*  4324 */         return w.GetDamageMedium() * mult * w.ClusterSize();
/*       */       }
/*  4326 */       return w.GetDamageMedium() * mult;
/*       */     }
/*  4328 */     if (range <= w.GetRangeLong()) {
/*  4329 */       if (w.GetWeaponClass() == 2) {
/*  4330 */         return w.GetDamageLong() * mult * w.ClusterSize();
/*       */       }
/*  4332 */       return w.GetDamageLong() * mult;
/*       */     }
/*       */     
/*  4335 */     return 0; }
/*       */   
/*       */   private JCheckBox chkChartFront;
/*       */   private JCheckBox chkChartLeft;
/*       */   private JCheckBox chkChartRear;
/*       */   private JCheckBox chkChartRight;
/*  4341 */   private File GetSaveFile(final String extension, String path, boolean autooverwrite, boolean singleloadout) { String filename = "";
/*  4342 */     boolean overwrite = false;
/*       */     
/*       */ 
/*  4345 */     SolidifyMech();
/*  4346 */     if (!VerifyMech(null)) {
/*  4347 */       return null;
/*       */     }
/*       */     
/*       */ 
/*  4351 */     if ((this.CurMech.IsOmnimech()) && (singleloadout)) {
/*  4352 */       if (this.CurMech.GetModel().isEmpty()) {
/*  4353 */         filename = this.CurMech.GetName() + " " + this.CurMech.GetLoadout().GetName() + "." + extension;
/*       */       }
/*       */       else {
/*  4356 */         filename = this.CurMech.GetName() + " " + this.CurMech.GetModel() + " " + this.CurMech.GetLoadout().GetName() + "." + extension;
/*       */       }
/*       */     }
/*  4359 */     else if (this.CurMech.GetModel().isEmpty()) {
/*  4360 */       filename = this.CurMech.GetName() + "." + extension;
/*       */     } else {
/*  4362 */       filename = this.CurMech.GetName() + " " + this.CurMech.GetModel() + "." + extension;
/*       */     }
/*       */     
/*       */ 
/*  4366 */     filename = CommonTools.FormatFileName(filename);
/*       */     
/*       */     try
/*       */     {
/*  4370 */       CheckFileName(filename);
/*       */     } catch (Exception e) {
/*  4372 */       Media.Messager(this, "There was a problem with the filename:\n" + e
/*  4373 */         .getMessage() + "\nSaving will continue but you should change the filename.");
/*       */     }
/*       */     
/*       */ 
/*  4377 */     if (autooverwrite) {
/*  4378 */       File testfile = new File(path + File.separator + filename);
/*  4379 */       if (testfile.exists()) {
/*  4380 */         int choice = javax.swing.JOptionPane.showConfirmDialog(this, "A file with the specified name already exists\n" + testfile + "\nDo you want to overwrite it?", "Overwrite file", 0);
/*       */         
/*       */ 
/*  4383 */         if (choice != 1) {
/*  4384 */           overwrite = true;
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*  4389 */     File retval = null;
/*  4390 */     if ((autooverwrite) && (overwrite)) {
/*  4391 */       retval = new File(path + File.separator + filename);
/*       */     }
/*       */     else {
/*  4394 */       File directory = new File(path);
/*  4395 */       JFileChooser fc = new JFileChooser();
/*  4396 */       fc.setCurrentDirectory(directory);
/*  4397 */       fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter()
/*       */       {
/*       */         public boolean accept(File f) {
/*  4400 */           if (f.isDirectory()) {
/*  4401 */             return true;
/*       */           }
/*       */           
/*  4404 */           String checkext = Utils.getExtension(f);
/*  4405 */           if (checkext != null) {
/*  4406 */             if (checkext.equals(extension)) {
/*  4407 */               return true;
/*       */             }
/*  4409 */             return false;
/*       */           }
/*       */           
/*  4412 */           return false;
/*       */         }
/*       */         
/*       */         public String getDescription()
/*       */         {
/*  4417 */           return "*." + extension;
/*       */         }
/*  4419 */       });
/*  4420 */       fc.setAcceptAllFileFilterUsed(false);
/*  4421 */       fc.setSelectedFile(new File(filename));
/*       */       
/*       */ 
/*  4424 */       int returnval = fc.showDialog(this, "Save to " + extension);
/*  4425 */       if (returnval != 0) return null;
/*  4426 */       retval = fc.getSelectedFile();
/*  4427 */       if (retval.exists()) {
/*  4428 */         int choice = javax.swing.JOptionPane.showConfirmDialog(this, "A file with the specified name already exists\n" + retval + "\nDo you want to overwrite it?", "Overwrite file", 0);
/*       */         
/*       */ 
/*  4431 */         if (choice == 1) {
/*  4432 */           Media.Messager(this, "The 'Mech was not saved.");
/*  4433 */           return null;
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*  4438 */     return retval;
/*       */   }
/*       */   
/*       */   private void FluffCut(java.awt.Component c) {
/*  4442 */     String cut = "";
/*  4443 */     if ((c instanceof JEditorPane)) {
/*  4444 */       JEditorPane j = (JEditorPane)c;
/*  4445 */       if (j.getSelectedText() == null)
/*       */       {
/*  4447 */         cut = j.getText();
/*  4448 */         j.setText("");
/*       */       }
/*       */       else {
/*  4451 */         cut = j.getSelectedText();
/*  4452 */         j.setText(j.getText().replace(cut, ""));
/*       */       }
/*       */     }
/*  4455 */     if ((c instanceof JTextField)) {
/*  4456 */       JTextField j = (JTextField)c;
/*  4457 */       if (j.getSelectedText() == null)
/*       */       {
/*  4459 */         cut = j.getText();
/*  4460 */         j.setText("");
/*       */       }
/*       */       else {
/*  4463 */         cut = j.getSelectedText();
/*  4464 */         j.setText(j.getText().replace(cut, ""));
/*       */       }
/*       */     }
/*  4467 */     java.awt.datatransfer.StringSelection export = new java.awt.datatransfer.StringSelection(cut);
/*  4468 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/*  4469 */     clipboard.setContents(export, this);
/*       */   }
/*       */   
/*       */   private void FluffCopy(java.awt.Component c) {
/*  4473 */     String copy = "";
/*  4474 */     if ((c instanceof JEditorPane)) {
/*  4475 */       JEditorPane j = (JEditorPane)c;
/*  4476 */       if (j.getSelectedText() == null)
/*       */       {
/*  4478 */         copy = j.getText();
/*       */       }
/*       */       else {
/*  4481 */         copy = j.getSelectedText();
/*       */       }
/*       */     }
/*  4484 */     if ((c instanceof JTextField)) {
/*  4485 */       JTextField j = (JTextField)c;
/*  4486 */       if (j.getSelectedText() == null)
/*       */       {
/*  4488 */         copy = j.getText();
/*       */       }
/*       */       else {
/*  4491 */         copy = j.getSelectedText();
/*       */       }
/*       */     }
/*  4494 */     java.awt.datatransfer.StringSelection export = new java.awt.datatransfer.StringSelection(copy);
/*  4495 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/*  4496 */     clipboard.setContents(export, this);
/*       */   }
/*       */   
/*       */   private void FluffPaste(java.awt.Component c)
/*       */   {
/*  4501 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/*  4502 */     String txtimport = null;
/*       */     try {
/*  4504 */       txtimport = (String)clipboard.getData(java.awt.datatransfer.DataFlavor.stringFlavor);
/*       */     } catch (Exception e) {
/*  4506 */       System.err.println(e.getMessage());
/*  4507 */       e.printStackTrace();
/*  4508 */       return;
/*       */     }
/*  4510 */     if (txtimport == null) return;
/*  4511 */     if ((c instanceof JEditorPane)) {
/*  4512 */       JEditorPane j = (JEditorPane)c;
/*  4513 */       int insert = j.getCaretPosition();
/*  4514 */       String paste = j.getText().substring(0, insert) + txtimport + j.getText().substring(insert);
/*  4515 */       j.setText(paste);
/*       */     }
/*  4517 */     if ((c instanceof JTextField)) {
/*  4518 */       JTextField j = (JTextField)c;
/*  4519 */       int insert = j.getCaretPosition();
/*  4520 */       String paste = j.getText().substring(0, insert) + txtimport + j.getText().substring(insert);
/*  4521 */       j.setText(paste);
/*       */     }
/*       */   }
/*       */   
/*       */   private boolean AddECM()
/*       */   {
/*  4527 */     if (this.Prefs.getBoolean("AutoAddECM", true)) {
/*  4528 */       if (!this.CurMech.ValidateECM()) {
/*  4529 */         abPlaceable a = this.data.GetEquipment().GetEquipmentByName("Guardian ECM Suite", this.CurMech);
/*  4530 */         if (a == null) {
/*  4531 */           a = this.data.GetEquipment().GetEquipmentByName("Angel ECM", this.CurMech);
/*  4532 */           if (a == null) {
/*  4533 */             a = this.data.GetEquipment().GetEquipmentByName("ECM Suite", this.CurMech);
/*  4534 */             if (a == null) {
/*  4535 */               a = this.data.GetEquipment().GetEquipmentByName("Watchdog CEWS", this.CurMech);
/*  4536 */               if (a == null) {
/*  4537 */                 return false;
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*  4542 */         this.CurMech.GetLoadout().AddToQueue(a);
/*       */       }
/*  4544 */       return true;
/*       */     }
/*  4546 */     Media.Messager(this, "Please add an appropriate ECM Suite to complement this\n system.  The 'Mech is not valid without an ECM Suite.");
/*  4547 */     return true;
/*       */   }
/*       */   
/*       */   private JCheckBox chkClanCASE;
/*       */   private JCheckBox chkCommandConsole;
/*       */   private JCheckBox chkEjectionSeat;
/*       */   private JCheckBox chkEnviroSealing;
/*       */   private JCheckBox chkFCSAIV;
/*       */   private JCheckBox chkFCSAV;
/*       */   private JCheckBox chkFCSApollo;
/*       */   private JCheckBox chkFHES;
/*       */   
/*       */   private void initComponents()
/*       */   {
/*  4561 */     this.tlbIconBar = new JToolBar();
/*  4562 */     this.btnNewIcon = new JButton();
/*  4563 */     this.btnOpen = new JButton();
/*  4564 */     this.btnSaveIcon = new JButton();
/*  4565 */     this.btnPrintPreview = new JButton();
/*  4566 */     this.jSeparator24 = new javax.swing.JToolBar.Separator();
/*  4567 */     this.btnPrintIcon = new JButton();
/*  4568 */     this.jSeparator22 = new javax.swing.JToolBar.Separator();
/*  4569 */     this.btnExportClipboardIcon = new JButton();
/*  4570 */     this.btnExportHTMLIcon = new JButton();
/*  4571 */     this.btnExportTextIcon = new JButton();
/*  4572 */     this.btnExportMTFIcon = new JButton();
/*  4573 */     this.btnChatInfo = new JButton();
/*  4574 */     this.jSeparator23 = new javax.swing.JToolBar.Separator();
/*  4575 */     this.btnPostToS7 = new JButton();
/*  4576 */     this.jSeparator25 = new javax.swing.JToolBar.Separator();
/*  4577 */     this.btnAddToForceList = new JButton();
/*  4578 */     this.btnForceList = new JButton();
/*  4579 */     this.jSeparator26 = new javax.swing.JToolBar.Separator();
/*  4580 */     this.btnOptionsIcon = new JButton();
/*  4581 */     this.jSeparator21 = new javax.swing.JToolBar.Separator();
/*  4582 */     this.lblSelectVariant = new JLabel();
/*  4583 */     this.cmbOmniVariant = new JComboBox();
/*  4584 */     this.tbpMainTabPane = new JTabbedPane();
/*  4585 */     this.pnlBasicSetup = new JPanel();
/*  4586 */     this.pnlBasicInformation = new JPanel();
/*  4587 */     this.lblMechName = new JLabel();
/*  4588 */     this.txtMechName = new JTextField();
/*  4589 */     this.lblModel = new JLabel();
/*  4590 */     this.txtMechModel = new JTextField();
/*  4591 */     this.lblMechEra = new JLabel();
/*  4592 */     this.cmbMechEra = new JComboBox();
/*  4593 */     this.lblEraYears = new JLabel();
/*  4594 */     this.lblProdYear = new JLabel();
/*  4595 */     this.txtProdYear = new JTextField();
/*  4596 */     this.chkYearRestrict = new JCheckBox();
/*  4597 */     this.lblTechBase = new JLabel();
/*  4598 */     this.cmbTechBase = new JComboBox();
/*  4599 */     this.cmbRulesLevel = new JComboBox();
/*  4600 */     this.lblRulesLevel = new JLabel();
/*  4601 */     this.jLabel65 = new JLabel();
/*  4602 */     this.txtSource = new JTextField();
/*  4603 */     this.jSeparator28 = new JSeparator();
/*  4604 */     this.jSeparator29 = new JSeparator();
/*  4605 */     this.cmbProductionEra = new JComboBox();
/*  4606 */     this.pnlChassis = new JPanel();
/*  4607 */     this.lblTonnage = new JLabel();
/*  4608 */     this.cmbTonnage = new JComboBox();
/*  4609 */     this.lblMechType = new JLabel();
/*  4610 */     this.lblMotiveType = new JLabel();
/*  4611 */     this.cmbMotiveType = new JComboBox();
/*  4612 */     this.lblEngineType = new JLabel();
/*  4613 */     this.cmbEngineType = new JComboBox();
/*  4614 */     this.lblInternalType = new JLabel();
/*  4615 */     this.cmbInternalType = new JComboBox();
/*  4616 */     this.lblGyroType = new JLabel();
/*  4617 */     this.cmbGyroType = new JComboBox();
/*  4618 */     this.lblCockpit = new JLabel();
/*  4619 */     this.cmbCockpitType = new JComboBox();
/*  4620 */     this.lblPhysEnhance = new JLabel();
/*  4621 */     this.cmbPhysEnhance = new JComboBox();
/*  4622 */     this.chkOmnimech = new JCheckBox();
/*  4623 */     this.cmbMechType = new JComboBox();
/*  4624 */     this.lblUnitType = new JLabel();
/*  4625 */     this.chkCommandConsole = new JCheckBox();
/*  4626 */     this.chkFractional = new JCheckBox();
/*  4627 */     this.pnlHeatSinks = new JPanel();
/*  4628 */     this.lblHeatSinkType = new JLabel();
/*  4629 */     this.cmbHeatSinkType = new JComboBox();
/*  4630 */     this.lblHSNumber = new JLabel();
/*  4631 */     this.spnNumberOfHS = new JSpinner();
/*  4632 */     this.pnlMovement = new JPanel();
/*  4633 */     this.lblWalkMP = new JLabel();
/*  4634 */     this.spnWalkMP = new JSpinner();
/*  4635 */     this.lblRunMPLabel = new JLabel();
/*  4636 */     this.lblRunMP = new JLabel();
/*  4637 */     this.lblJumpMP = new JLabel();
/*  4638 */     this.spnJumpMP = new JSpinner();
/*  4639 */     this.cmbJumpJetType = new JComboBox();
/*  4640 */     this.jLabel36 = new JLabel();
/*  4641 */     this.jLabel53 = new JLabel();
/*  4642 */     this.spnBoosterMP = new JSpinner();
/*  4643 */     this.chkBoosters = new JCheckBox();
/*  4644 */     this.lblMoveSummary = new JLabel();
/*  4645 */     this.jLabel1 = new JLabel();
/*  4646 */     this.txtEngineRating = new JTextField();
/*  4647 */     this.pnlOmniInfo = new JPanel();
/*  4648 */     this.btnLockChassis = new JButton();
/*  4649 */     this.btnAddVariant = new JButton();
/*  4650 */     this.btnDeleteVariant = new JButton();
/*  4651 */     this.btnRenameVariant = new JButton();
/*  4652 */     this.pnlBasicSummary = new JPanel();
/*  4653 */     this.lblSumStructure = new JLabel();
/*  4654 */     this.txtSumIntTon = new JTextField();
/*  4655 */     this.lblSumEngine = new JLabel();
/*  4656 */     this.txtSumEngTon = new JTextField();
/*  4657 */     this.lblSumGyro = new JLabel();
/*  4658 */     this.txtSumGyrTon = new JTextField();
/*  4659 */     this.lblSumHeadItem = new JLabel();
/*  4660 */     this.lblSumHeadTons = new JLabel();
/*  4661 */     this.lblSumHeadCrits = new JLabel();
/*  4662 */     this.txtSumIntCrt = new JTextField();
/*  4663 */     this.txtSumEngCrt = new JTextField();
/*  4664 */     this.txtSumGyrCrt = new JTextField();
/*  4665 */     this.lblSumCockpit = new JLabel();
/*  4666 */     this.txtSumCocTon = new JTextField();
/*  4667 */     this.txtSumCocCrt = new JTextField();
/*  4668 */     this.lblSumEnhance = new JLabel();
/*  4669 */     this.txtSumEnhTon = new JTextField();
/*  4670 */     this.txtSumEnhCrt = new JTextField();
/*  4671 */     this.lblSumHeatSinks = new JLabel();
/*  4672 */     this.txtSumHSTon = new JTextField();
/*  4673 */     this.txtSumHSCrt = new JTextField();
/*  4674 */     this.lblSumJJ = new JLabel();
/*  4675 */     this.txtSumJJTon = new JTextField();
/*  4676 */     this.txtSumJJCrt = new JTextField();
/*  4677 */     this.txtSumIntACode = new JTextField();
/*  4678 */     this.txtSumEngACode = new JTextField();
/*  4679 */     this.txtSumGyrACode = new JTextField();
/*  4680 */     this.txtSumCocACode = new JTextField();
/*  4681 */     this.txtSumHSACode = new JTextField();
/*  4682 */     this.txtSumEnhACode = new JTextField();
/*  4683 */     this.txtSumJJACode = new JTextField();
/*  4684 */     this.lblSumHeadAvailable = new JLabel();
/*  4685 */     this.txtSumPAmpsTon = new JTextField();
/*  4686 */     this.txtSumPAmpsACode = new JTextField();
/*  4687 */     this.lblSumPAmps = new JLabel();
/*  4688 */     this.jPanel4 = new JPanel();
/*  4689 */     this.chkCLPS = new JCheckBox();
/*  4690 */     this.chkNullSig = new JCheckBox();
/*  4691 */     this.chkBSPFD = new JCheckBox();
/*  4692 */     this.chkVoidSig = new JCheckBox();
/*  4693 */     this.chkSupercharger = new JCheckBox();
/*  4694 */     this.cmbSCLoc = new JComboBox();
/*  4695 */     this.chkBoobyTrap = new JCheckBox();
/*  4696 */     this.chkPartialWing = new JCheckBox();
/*  4697 */     this.chkFHES = new JCheckBox();
/*  4698 */     this.lblSupercharger = new JLabel();
/*  4699 */     this.jLabel57 = new JLabel();
/*  4700 */     this.jPanel6 = new JPanel();
/*  4701 */     this.chkEjectionSeat = new JCheckBox();
/*  4702 */     this.chkEnviroSealing = new JCheckBox();
/*  4703 */     this.chkTracks = new JCheckBox();
/*  4704 */     this.pnlFrontArmor = new JPanel();
/*  4705 */     this.pnlRLArmorBox = new JPanel();
/*  4706 */     this.lblRLHeader = new JLabel();
/*  4707 */     this.lblRLIntPts = new JLabel();
/*  4708 */     this.lblRLArmorHeader = new JLabel();
/*  4709 */     this.spnRLArmor = new JSpinner();
/*  4710 */     this.pnlLLArmorBox = new JPanel();
/*  4711 */     this.lblLLHeader = new JLabel();
/*  4712 */     this.lblLLIntPts = new JLabel();
/*  4713 */     this.lblLLArmorHeader = new JLabel();
/*  4714 */     this.spnLLArmor = new JSpinner();
/*  4715 */     this.pnlRAArmorBox = new JPanel();
/*  4716 */     this.lblRAHeader = new JLabel();
/*  4717 */     this.lblRAIntPts = new JLabel();
/*  4718 */     this.lblRAArmorHeader = new JLabel();
/*  4719 */     this.spnRAArmor = new JSpinner();
/*  4720 */     this.pnlHDArmorBox = new JPanel();
/*  4721 */     this.lblHDHeader = new JLabel();
/*  4722 */     this.lblHDIntPts = new JLabel();
/*  4723 */     this.lblHDArmorHeader = new JLabel();
/*  4724 */     this.spnHDArmor = new JSpinner();
/*  4725 */     this.pnlCTArmorBox = new JPanel();
/*  4726 */     this.lblCTHeader = new JLabel();
/*  4727 */     this.lblCTIntPts = new JLabel();
/*  4728 */     this.lblCTArmorHeader = new JLabel();
/*  4729 */     this.spnCTArmor = new JSpinner();
/*  4730 */     this.pnlLTArmorBox = new JPanel();
/*  4731 */     this.lblLTHeader = new JLabel();
/*  4732 */     this.lblLTIntPts = new JLabel();
/*  4733 */     this.lblLTArmorHeader = new JLabel();
/*  4734 */     this.spnLTArmor = new JSpinner();
/*  4735 */     this.pnlRTArmorBox = new JPanel();
/*  4736 */     this.lblRTHeader = new JLabel();
/*  4737 */     this.lblRTIntPts = new JLabel();
/*  4738 */     this.lblRTArmorHeader = new JLabel();
/*  4739 */     this.spnRTArmor = new JSpinner();
/*  4740 */     this.pnlLAArmorBox = new JPanel();
/*  4741 */     this.lblLAHeader = new JLabel();
/*  4742 */     this.lblLAIntPts = new JLabel();
/*  4743 */     this.lblLAArmorHeader = new JLabel();
/*  4744 */     this.spnLAArmor = new JSpinner();
/*  4745 */     this.pnlRearArmor = new JPanel();
/*  4746 */     this.pnlRTRArmorBox = new JPanel();
/*  4747 */     this.lblRTRArmorHeader = new JLabel();
/*  4748 */     this.spnRTRArmor = new JSpinner();
/*  4749 */     this.pnlCTRArmorBox = new JPanel();
/*  4750 */     this.lblCTRArmorHeader = new JLabel();
/*  4751 */     this.spnCTRArmor = new JSpinner();
/*  4752 */     this.pnlLTRArmorBox = new JPanel();
/*  4753 */     this.lblLTRArmorHeader = new JLabel();
/*  4754 */     this.spnLTRArmor = new JSpinner();
/*  4755 */     this.pnlArmor = new JPanel();
/*  4756 */     this.pnlArmorInfo = new JPanel();
/*  4757 */     this.lblArmorCoverage = new JLabel();
/*  4758 */     this.lblArmorPoints = new JLabel();
/*  4759 */     this.txtSumArmorTon = new JTextField();
/*  4760 */     this.txtSumArmorCrt = new JTextField();
/*  4761 */     this.lblSumHeadTons1 = new JLabel();
/*  4762 */     this.lblSumHeadCrits1 = new JLabel();
/*  4763 */     this.lblArmorTonsWasted = new JLabel();
/*  4764 */     this.lblAVInLot = new JLabel();
/*  4765 */     this.pnlArmorSetup = new JPanel();
/*  4766 */     this.btnMaxArmor = new JButton();
/*  4767 */     this.btnArmorTons = new JButton();
/*  4768 */     this.cmbArmorType = new JComboBox();
/*  4769 */     this.lblArmorType = new JLabel();
/*  4770 */     this.btnBalanceArmor = new JCheckBox();
/*  4771 */     this.btnEfficientArmor = new JButton();
/*  4772 */     this.btnRemainingArmor = new JButton();
/*  4773 */     this.pnlPatchworkChoosers = new JPanel();
/*  4774 */     this.lblPWHDLoc = new JLabel();
/*  4775 */     this.lblPWCTLoc = new JLabel();
/*  4776 */     this.lblPWLTLoc = new JLabel();
/*  4777 */     this.lblPWRTLoc = new JLabel();
/*  4778 */     this.lblPWLALoc = new JLabel();
/*  4779 */     this.lblPWRALoc = new JLabel();
/*  4780 */     this.lblPWLLLoc = new JLabel();
/*  4781 */     this.cmbPWHDType = new JComboBox();
/*  4782 */     this.cmbPWCTType = new JComboBox();
/*  4783 */     this.cmbPWLTType = new JComboBox();
/*  4784 */     this.cmbPWRTType = new JComboBox();
/*  4785 */     this.cmbPWLAType = new JComboBox();
/*  4786 */     this.cmbPWRAType = new JComboBox();
/*  4787 */     this.cmbPWLLType = new JComboBox();
/*  4788 */     this.cmbPWRLType = new JComboBox();
/*  4789 */     this.lblPWRLLoc = new JLabel();
/*  4790 */     this.pnlEquipment = new JPanel();
/*  4791 */     this.tbpWeaponChooser = new JTabbedPane();
/*  4792 */     this.pnlBallistic = new JPanel();
/*  4793 */     this.jSeparator3 = new JSeparator();
/*  4794 */     this.jScrollPane8 = new JScrollPane();
/*  4795 */     this.lstChooseBallistic = new JList();
/*  4796 */     this.jSeparator4 = new JSeparator();
/*  4797 */     this.pnlEnergy = new JPanel();
/*  4798 */     this.jSeparator2 = new JSeparator();
/*  4799 */     this.jScrollPane9 = new JScrollPane();
/*  4800 */     this.lstChooseEnergy = new JList();
/*  4801 */     this.jSeparator1 = new JSeparator();
/*  4802 */     this.pnlMissile = new JPanel();
/*  4803 */     this.jSeparator5 = new JSeparator();
/*  4804 */     this.jScrollPane19 = new JScrollPane();
/*  4805 */     this.lstChooseMissile = new JList();
/*  4806 */     this.jSeparator6 = new JSeparator();
/*  4807 */     this.pnlPhysical = new JPanel();
/*  4808 */     this.jSeparator8 = new JSeparator();
/*  4809 */     this.jScrollPane20 = new JScrollPane();
/*  4810 */     this.lstChoosePhysical = new JList();
/*  4811 */     this.jSeparator7 = new JSeparator();
/*  4812 */     this.pnlEquipmentChooser = new JPanel();
/*  4813 */     this.jSeparator10 = new JSeparator();
/*  4814 */     this.jScrollPane21 = new JScrollPane();
/*  4815 */     this.lstChooseEquipment = new JList();
/*  4816 */     this.jSeparator9 = new JSeparator();
/*  4817 */     this.pnlArtillery = new JPanel();
/*  4818 */     this.jSeparator18 = new JSeparator();
/*  4819 */     this.jScrollPane24 = new JScrollPane();
/*  4820 */     this.lstChooseArtillery = new JList();
/*  4821 */     this.jSeparator19 = new JSeparator();
/*  4822 */     this.pnlAmmunition = new JPanel();
/*  4823 */     this.jSeparator11 = new JSeparator();
/*  4824 */     this.jScrollPane22 = new JScrollPane();
/*  4825 */     this.lstChooseAmmunition = new JList();
/*  4826 */     this.jSeparator12 = new JSeparator();
/*  4827 */     this.pnlSpecials = new JPanel();
/*  4828 */     this.jLabel16 = new JLabel();
/*  4829 */     this.chkUseTC = new JCheckBox();
/*  4830 */     this.chkFCSAIV = new JCheckBox();
/*  4831 */     this.chkFCSAV = new JCheckBox();
/*  4832 */     this.chkFCSApollo = new JCheckBox();
/*  4833 */     this.chkClanCASE = new JCheckBox();
/*  4834 */     this.pnlControls = new JPanel();
/*  4835 */     this.btnRemoveEquip = new JButton();
/*  4836 */     this.btnClearEquip = new JButton();
/*  4837 */     this.btnAddEquip = new JButton();
/*  4838 */     this.cmbNumEquips = new JComboBox();
/*  4839 */     this.pnlEquipInfo = new JPanel();
/*  4840 */     this.jLabel17 = new JLabel();
/*  4841 */     this.jLabel18 = new JLabel();
/*  4842 */     this.jLabel19 = new JLabel();
/*  4843 */     this.lblInfoAVSL = new JLabel();
/*  4844 */     this.lblInfoAVSW = new JLabel();
/*  4845 */     this.lblInfoAVCI = new JLabel();
/*  4846 */     this.jLabel20 = new JLabel();
/*  4847 */     this.jLabel21 = new JLabel();
/*  4848 */     this.jLabel22 = new JLabel();
/*  4849 */     this.lblInfoIntro = new JLabel();
/*  4850 */     this.lblInfoExtinct = new JLabel();
/*  4851 */     this.lblInfoReintro = new JLabel();
/*  4852 */     this.jLabel23 = new JLabel();
/*  4853 */     this.jLabel24 = new JLabel();
/*  4854 */     this.jLabel25 = new JLabel();
/*  4855 */     this.jLabel26 = new JLabel();
/*  4856 */     this.jLabel27 = new JLabel();
/*  4857 */     this.lblInfoName = new JLabel();
/*  4858 */     this.lblInfoType = new JLabel();
/*  4859 */     this.lblInfoHeat = new JLabel();
/*  4860 */     this.lblInfoDamage = new JLabel();
/*  4861 */     this.lblInfoRange = new JLabel();
/*  4862 */     this.jSeparator13 = new JSeparator();
/*  4863 */     this.jLabel28 = new JLabel();
/*  4864 */     this.jLabel29 = new JLabel();
/*  4865 */     this.jLabel30 = new JLabel();
/*  4866 */     this.jLabel31 = new JLabel();
/*  4867 */     this.lblInfoAmmo = new JLabel();
/*  4868 */     this.lblInfoTonnage = new JLabel();
/*  4869 */     this.lblInfoCrits = new JLabel();
/*  4870 */     this.lblInfoSpecials = new JLabel();
/*  4871 */     this.jSeparator14 = new JSeparator();
/*  4872 */     this.jLabel32 = new JLabel();
/*  4873 */     this.lblInfoCost = new JLabel();
/*  4874 */     this.jLabel34 = new JLabel();
/*  4875 */     this.lblInfoBV = new JLabel();
/*  4876 */     this.jLabel33 = new JLabel();
/*  4877 */     this.lblInfoMountRestrict = new JLabel();
/*  4878 */     this.jLabel55 = new JLabel();
/*  4879 */     this.lblInfoRulesLevel = new JLabel();
/*  4880 */     this.pnlLACrits = new JPanel();
/*  4881 */     this.scrLACrits = new JScrollPane();
/*  4882 */     this.lstLACrits = new JList();
/*  4883 */     this.chkLALowerArm = new JCheckBox();
/*  4884 */     this.chkLAHand = new JCheckBox();
/*  4885 */     this.chkLACASE2 = new JCheckBox();
/*  4886 */     this.chkLAAES = new JCheckBox();
/*  4887 */     this.pnlLTCrits = new JPanel();
/*  4888 */     this.chkLTCASE = new JCheckBox();
/*  4889 */     this.jScrollPane12 = new JScrollPane();
/*  4890 */     this.lstLTCrits = new JList();
/*  4891 */     this.chkLTCASE2 = new JCheckBox();
/*  4892 */     this.chkLTTurret = new JCheckBox();
/*  4893 */     this.pnlHDCrits = new JPanel();
/*  4894 */     this.chkHDTurret = new JCheckBox();
/*  4895 */     this.chkHDCASE2 = new JCheckBox();
/*  4896 */     this.jScrollPane10 = new JScrollPane();
/*  4897 */     this.lstHDCrits = new JList();
/*  4898 */     this.pnlRTCrits = new JPanel();
/*  4899 */     this.jScrollPane13 = new JScrollPane();
/*  4900 */     this.lstRTCrits = new JList();
/*  4901 */     this.chkRTCASE = new JCheckBox();
/*  4902 */     this.chkRTCASE2 = new JCheckBox();
/*  4903 */     this.chkRTTurret = new JCheckBox();
/*  4904 */     this.pnlCTCrits = new JPanel();
/*  4905 */     this.jScrollPane11 = new JScrollPane();
/*  4906 */     this.lstCTCrits = new JList();
/*  4907 */     this.chkCTCASE = new JCheckBox();
/*  4908 */     this.chkCTCASE2 = new JCheckBox();
/*  4909 */     this.pnlLLCrits = new JPanel();
/*  4910 */     this.jScrollPane16 = new JScrollPane();
/*  4911 */     this.lstLLCrits = new JList();
/*  4912 */     this.chkLLCASE2 = new JCheckBox();
/*  4913 */     this.pnlRLCrits = new JPanel();
/*  4914 */     this.jScrollPane17 = new JScrollPane();
/*  4915 */     this.lstRLCrits = new JList();
/*  4916 */     this.chkRLCASE2 = new JCheckBox();
/*  4917 */     this.jPanel5 = new JPanel();
/*  4918 */     this.jLabel59 = new JLabel();
/*  4919 */     this.chkLegAES = new JCheckBox();
/*  4920 */     this.jLabel61 = new JLabel();
/*  4921 */     this.pnlRACrits = new JPanel();
/*  4922 */     this.scrRACrits = new JScrollPane();
/*  4923 */     this.lstRACrits = new JList();
/*  4924 */     this.chkRALowerArm = new JCheckBox();
/*  4925 */     this.chkRAHand = new JCheckBox();
/*  4926 */     this.chkRACASE2 = new JCheckBox();
/*  4927 */     this.chkRAAES = new JCheckBox();
/*  4928 */     this.pnlEquipmentToPlace = new JPanel();
/*  4929 */     this.jScrollPane18 = new JScrollPane();
/*  4930 */     this.lstCritsToPlace = new JList();
/*  4931 */     this.btnRemoveItemCrits = new JButton();
/*  4932 */     this.btnAutoAllocate = new JButton();
/*  4933 */     this.btnSelectiveAllocate = new JButton();
/*  4934 */     this.btnCompactCrits = new JButton();
/*  4935 */     this.btnClearLoadout = new JButton();
/*  4936 */     this.pnlFluff = new JPanel();
/*  4937 */     this.pnlImage = new JPanel();
/*  4938 */     this.lblFluffImage = new JLabel();
/*  4939 */     this.jPanel1 = new JPanel();
/*  4940 */     this.btnLoadImage = new JButton();
/*  4941 */     this.btnClearImage = new JButton();
/*  4942 */     this.tbpFluffEditors = new JTabbedPane();
/*  4943 */     this.pnlOverview = new JPanel();
/*  4944 */     this.pnlCapabilities = new JPanel();
/*  4945 */     this.pnlHistory = new JPanel();
/*  4946 */     this.pnlDeployment = new JPanel();
/*  4947 */     this.pnlVariants = new JPanel();
/*  4948 */     this.pnlNotables = new JPanel();
/*  4949 */     this.pnlAdditionalFluff = new JPanel();
/*  4950 */     this.pnlExport = new JPanel();
/*  4951 */     this.btnExportTXT = new JButton();
/*  4952 */     this.btnExportHTML = new JButton();
/*  4953 */     this.btnExportMTF = new JButton();
/*  4954 */     this.pnlManufacturers = new JPanel();
/*  4955 */     this.jLabel8 = new JLabel();
/*  4956 */     this.jLabel9 = new JLabel();
/*  4957 */     this.jLabel10 = new JLabel();
/*  4958 */     this.jLabel12 = new JLabel();
/*  4959 */     this.jLabel11 = new JLabel();
/*  4960 */     this.jLabel13 = new JLabel();
/*  4961 */     this.jLabel14 = new JLabel();
/*  4962 */     this.jLabel15 = new JLabel();
/*  4963 */     this.txtManufacturer = new JTextField();
/*  4964 */     this.txtEngineManufacturer = new JTextField();
/*  4965 */     this.txtArmorModel = new JTextField();
/*  4966 */     this.txtChassisModel = new JTextField();
/*  4967 */     this.txtCommSystem = new JTextField();
/*  4968 */     this.txtTNTSystem = new JTextField();
/*  4969 */     this.pnlWeaponsManufacturers = new JPanel();
/*  4970 */     this.chkIndividualWeapons = new JCheckBox();
/*  4971 */     this.scpWeaponManufacturers = new JScrollPane();
/*  4972 */     this.tblWeaponManufacturers = new javax.swing.JTable();
/*  4973 */     this.txtManufacturerLocation = new JTextField();
/*  4974 */     this.jLabel35 = new JLabel();
/*  4975 */     this.txtJJModel = new JTextField();
/*  4976 */     this.pnlCharts = new JPanel();
/*  4977 */     this.jPanel2 = new JPanel();
/*  4978 */     this.jLabel39 = new JLabel();
/*  4979 */     this.lblTonPercStructure = new JLabel();
/*  4980 */     this.jLabel43 = new JLabel();
/*  4981 */     this.lblTonPercEngine = new JLabel();
/*  4982 */     this.jLabel54 = new JLabel();
/*  4983 */     this.lblTonPercHeatSinks = new JLabel();
/*  4984 */     this.jLabel56 = new JLabel();
/*  4985 */     this.lblTonPercEnhance = new JLabel();
/*  4986 */     this.jLabel58 = new JLabel();
/*  4987 */     this.lblTonPercArmor = new JLabel();
/*  4988 */     this.jLabel60 = new JLabel();
/*  4989 */     this.lblTonPercJumpJets = new JLabel();
/*  4990 */     this.jLabel62 = new JLabel();
/*  4991 */     this.lblTonPercWeapons = new JLabel();
/*  4992 */     this.jLabel64 = new JLabel();
/*  4993 */     this.lblTonPercEquips = new JLabel();
/*  4994 */     this.jPanel3 = new JPanel();
/*  4995 */     this.jLabel41 = new JLabel();
/*  4996 */     this.lblDamagePerTon = new JLabel();
/*  4997 */     this.pnlDamageChart = new DamageChart();
/*  4998 */     this.lblLegendTitle = new JLabel();
/*  4999 */     this.chkChartFront = new JCheckBox();
/*  5000 */     this.chkChartRear = new JCheckBox();
/*  5001 */     this.chkChartRight = new JCheckBox();
/*  5002 */     this.chkChartLeft = new JCheckBox();
/*  5003 */     this.btnBracketChart = new JButton();
/*  5004 */     this.chkAverageDamage = new JCheckBox();
/*  5005 */     this.chkShowTextNotGraph = new JCheckBox();
/*  5006 */     this.pnlBFStats = new JPanel();
/*  5007 */     this.jLabel66 = new JLabel();
/*  5008 */     this.jLabel67 = new JLabel();
/*  5009 */     this.jLabel68 = new JLabel();
/*  5010 */     this.jLabel69 = new JLabel();
/*  5011 */     this.jLabel70 = new JLabel();
/*  5012 */     this.jLabel71 = new JLabel();
/*  5013 */     this.jLabel72 = new JLabel();
/*  5014 */     this.jLabel73 = new JLabel();
/*  5015 */     this.jLabel74 = new JLabel();
/*  5016 */     this.jLabel75 = new JLabel();
/*  5017 */     this.lblBFMV = new JLabel();
/*  5018 */     this.lblBFWt = new JLabel();
/*  5019 */     this.lblBFOV = new JLabel();
/*  5020 */     this.lblBFExtreme = new JLabel();
/*  5021 */     this.lblBFShort = new JLabel();
/*  5022 */     this.lblBFMedium = new JLabel();
/*  5023 */     this.lblBFLong = new JLabel();
/*  5024 */     this.lblBFArmor = new JLabel();
/*  5025 */     this.lblBFStructure = new JLabel();
/*  5026 */     this.lblBFSA = new JLabel();
/*  5027 */     this.jLabel37 = new JLabel();
/*  5028 */     this.lblBFPoints = new JLabel();
/*  5029 */     this.jPanel7 = new JPanel();
/*  5030 */     this.jScrollPane14 = new JScrollPane();
/*  5031 */     this.jTextAreaBFConversion = new javax.swing.JTextArea();
/*  5032 */     this.pnlInfoPanel = new JPanel();
/*  5033 */     this.txtInfoTonnage = new JTextField();
/*  5034 */     this.txtInfoFreeTons = new JTextField();
/*  5035 */     this.txtInfoMaxHeat = new JTextField();
/*  5036 */     this.txtInfoHeatDiss = new JTextField();
/*  5037 */     this.txtInfoFreeCrits = new JTextField();
/*  5038 */     this.txtInfoUnplaced = new JTextField();
/*  5039 */     this.txtInfoBattleValue = new JTextField();
/*  5040 */     this.txtInfoCost = new JTextField();
/*  5041 */     this.txtChatInfo = new JTextField();
/*  5042 */     this.mnuMainMenu = new javax.swing.JMenuBar();
/*  5043 */     this.mnuFile = new JMenu();
/*  5044 */     this.mnuNewMech = new JMenuItem();
/*  5045 */     this.mnuLoad = new JMenuItem();
/*  5046 */     this.mnuOpen = new JMenuItem();
/*  5047 */     this.mnuImport = new JMenu();
/*  5048 */     this.mnuImportHMP = new JMenuItem();
/*  5049 */     this.mnuBatchHMP = new JMenuItem();
/*  5050 */     this.jSeparator16 = new JSeparator();
/*  5051 */     this.mnuSave = new JMenuItem();
/*  5052 */     this.mnuSaveAs = new JMenuItem();
/*  5053 */     this.mnuExport = new JMenu();
/*  5054 */     this.mnuExportHTML = new JMenuItem();
/*  5055 */     this.mnuExportMTF = new JMenuItem();
/*  5056 */     this.mnuExportTXT = new JMenuItem();
/*  5057 */     this.mnuExportClipboard = new JMenuItem();
/*  5058 */     this.mnuCreateTCGMech = new JMenuItem();
/*  5059 */     this.jSeparator20 = new JSeparator();
/*  5060 */     this.mnuPrint = new JMenu();
/*  5061 */     this.mnuPrintCurrentMech = new JMenuItem();
/*  5062 */     this.mnuPrintSavedMech = new JMenuItem();
/*  5063 */     this.mnuPrintBatch = new JMenuItem();
/*  5064 */     this.mnuPrintPreview = new JMenuItem();
/*  5065 */     this.mnuPostS7 = new JMenuItem();
/*  5066 */     this.jSeparator17 = new JSeparator();
/*  5067 */     this.mnuExit = new JMenuItem();
/*  5068 */     this.mnuClearFluff = new JMenu();
/*  5069 */     this.mnuSummary = new JMenuItem();
/*  5070 */     this.mnuCostBVBreakdown = new JMenuItem();
/*  5071 */     this.mnuTextTRO = new JMenuItem();
/*  5072 */     this.jSeparator15 = new JSeparator();
/*  5073 */     this.mnuBFB = new JMenuItem();
/*  5074 */     this.jSeparator27 = new JSeparator();
/*  5075 */     this.mnuOptions = new JMenuItem();
/*  5076 */     this.mnuViewToolbar = new javax.swing.JCheckBoxMenuItem();
/*  5077 */     this.mnuClearUserData = new JMenuItem();
/*  5078 */     this.jSeparator30 = new JSeparator();
/*  5079 */     this.mnuUnlock = new JMenuItem();
/*  5080 */     this.jMenuItem1 = new JMenuItem();
/*  5081 */     this.mnuHelp = new JMenu();
/*  5082 */     this.mnuCredits = new JMenuItem();
/*  5083 */     this.mnuAboutSSW = new JMenuItem();
/*       */     
/*  5085 */     setDefaultCloseOperation(3);
/*  5086 */     setMinimumSize(new Dimension(750, 515));
/*  5087 */     addWindowListener(new java.awt.event.WindowAdapter() {
/*       */       public void windowClosing(java.awt.event.WindowEvent evt) {
/*  5089 */         frmMainWide.this.formWindowClosing(evt);
/*       */       }
/*  5091 */     });
/*  5092 */     getContentPane().setLayout(new GridBagLayout());
/*       */     
/*  5094 */     this.tlbIconBar.setFloatable(false);
/*  5095 */     this.tlbIconBar.setFocusable(false);
/*  5096 */     this.tlbIconBar.setMaximumSize(new Dimension(30, 30));
/*  5097 */     this.tlbIconBar.setMinimumSize(new Dimension(30, 30));
/*  5098 */     this.tlbIconBar.setPreferredSize(new Dimension(30, 30));
/*  5099 */     this.tlbIconBar.setRequestFocusEnabled(false);
/*  5100 */     this.tlbIconBar.setVerifyInputWhenFocusTarget(false);
/*       */     
/*  5102 */     this.btnNewIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document--plus.png")));
/*  5103 */     this.btnNewIcon.setToolTipText("New Mech");
/*  5104 */     this.btnNewIcon.setFocusable(false);
/*  5105 */     this.btnNewIcon.setHorizontalTextPosition(0);
/*  5106 */     this.btnNewIcon.setVerticalTextPosition(3);
/*  5107 */     this.btnNewIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5109 */         frmMainWide.this.btnNewIconActionPerformed(evt);
/*       */       }
/*  5111 */     });
/*  5112 */     this.tlbIconBar.add(this.btnNewIcon);
/*       */     
/*  5114 */     this.btnOpen.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/folder-open-document.png")));
/*  5115 */     this.btnOpen.setToolTipText("Open Mech");
/*  5116 */     this.btnOpen.setFocusable(false);
/*  5117 */     this.btnOpen.setHorizontalTextPosition(0);
/*  5118 */     this.btnOpen.setVerticalTextPosition(3);
/*  5119 */     this.btnOpen.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5121 */         frmMainWide.this.btnOpenActionPerformed(evt);
/*       */       }
/*  5123 */     });
/*  5124 */     this.tlbIconBar.add(this.btnOpen);
/*       */     
/*  5126 */     this.btnSaveIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/disk-black.png")));
/*  5127 */     this.btnSaveIcon.setToolTipText("Save Mech");
/*  5128 */     this.btnSaveIcon.setFocusable(false);
/*  5129 */     this.btnSaveIcon.setHorizontalTextPosition(0);
/*  5130 */     this.btnSaveIcon.setVerticalTextPosition(3);
/*  5131 */     this.btnSaveIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5133 */         frmMainWide.this.btnSaveIconActionPerformed(evt);
/*       */       }
/*  5135 */     });
/*  5136 */     this.tlbIconBar.add(this.btnSaveIcon);
/*       */     
/*  5138 */     this.btnPrintPreview.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/projection-screen.png")));
/*  5139 */     this.btnPrintPreview.setToolTipText("Print Preview");
/*  5140 */     this.btnPrintPreview.setFocusable(false);
/*  5141 */     this.btnPrintPreview.setHorizontalTextPosition(0);
/*  5142 */     this.btnPrintPreview.setVerticalTextPosition(3);
/*  5143 */     this.btnPrintPreview.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5145 */         frmMainWide.this.btnPrintPreviewActionPerformed(evt);
/*       */       }
/*  5147 */     });
/*  5148 */     this.tlbIconBar.add(this.btnPrintPreview);
/*  5149 */     this.tlbIconBar.add(this.jSeparator24);
/*       */     
/*  5151 */     this.btnPrintIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/printer.png")));
/*  5152 */     this.btnPrintIcon.setToolTipText("Print Current Mech");
/*  5153 */     this.btnPrintIcon.setFocusable(false);
/*  5154 */     this.btnPrintIcon.setHorizontalTextPosition(0);
/*  5155 */     this.btnPrintIcon.setVerticalTextPosition(3);
/*  5156 */     this.btnPrintIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5158 */         frmMainWide.this.btnPrintIconActionPerformed(evt);
/*       */       }
/*  5160 */     });
/*  5161 */     this.tlbIconBar.add(this.btnPrintIcon);
/*  5162 */     this.tlbIconBar.add(this.jSeparator22);
/*       */     
/*  5164 */     this.btnExportClipboardIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document-clipboard.png")));
/*  5165 */     this.btnExportClipboardIcon.setToolTipText("Export Text to Clipboard");
/*  5166 */     this.btnExportClipboardIcon.setFocusable(false);
/*  5167 */     this.btnExportClipboardIcon.setHorizontalTextPosition(0);
/*  5168 */     this.btnExportClipboardIcon.setVerticalTextPosition(3);
/*  5169 */     this.btnExportClipboardIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5171 */         frmMainWide.this.btnExportClipboardIconActionPerformed(evt);
/*       */       }
/*  5173 */     });
/*  5174 */     this.tlbIconBar.add(this.btnExportClipboardIcon);
/*       */     
/*  5176 */     this.btnExportHTMLIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document-image.png")));
/*  5177 */     this.btnExportHTMLIcon.setToolTipText("Export HTML");
/*  5178 */     this.btnExportHTMLIcon.setFocusable(false);
/*  5179 */     this.btnExportHTMLIcon.setHorizontalTextPosition(0);
/*  5180 */     this.btnExportHTMLIcon.setVerticalTextPosition(3);
/*  5181 */     this.btnExportHTMLIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5183 */         frmMainWide.this.btnExportHTMLIconActionPerformed(evt);
/*       */       }
/*  5185 */     });
/*  5186 */     this.tlbIconBar.add(this.btnExportHTMLIcon);
/*       */     
/*  5188 */     this.btnExportTextIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document-text.png")));
/*  5189 */     this.btnExportTextIcon.setToolTipText("Export Text");
/*  5190 */     this.btnExportTextIcon.setFocusable(false);
/*  5191 */     this.btnExportTextIcon.setHorizontalTextPosition(0);
/*  5192 */     this.btnExportTextIcon.setVerticalTextPosition(3);
/*  5193 */     this.btnExportTextIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5195 */         frmMainWide.this.btnExportTextIconActionPerformed(evt);
/*       */       }
/*  5197 */     });
/*  5198 */     this.tlbIconBar.add(this.btnExportTextIcon);
/*       */     
/*  5200 */     this.btnExportMTFIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document--arrow.png")));
/*  5201 */     this.btnExportMTFIcon.setToolTipText("Export MTF");
/*  5202 */     this.btnExportMTFIcon.setFocusable(false);
/*  5203 */     this.btnExportMTFIcon.setHorizontalTextPosition(0);
/*  5204 */     this.btnExportMTFIcon.setVerticalTextPosition(3);
/*  5205 */     this.btnExportMTFIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5207 */         frmMainWide.this.btnExportMTFIconActionPerformed(evt);
/*       */       }
/*  5209 */     });
/*  5210 */     this.tlbIconBar.add(this.btnExportMTFIcon);
/*       */     
/*  5212 */     this.btnChatInfo.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/balloon.png")));
/*  5213 */     this.btnChatInfo.setToolTipText("Copy Chat Line");
/*  5214 */     this.btnChatInfo.setFocusable(false);
/*  5215 */     this.btnChatInfo.setHorizontalTextPosition(0);
/*  5216 */     this.btnChatInfo.setVerticalTextPosition(3);
/*  5217 */     this.btnChatInfo.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5219 */         frmMainWide.this.btnChatInfoActionPerformed(evt);
/*       */       }
/*  5221 */     });
/*  5222 */     this.tlbIconBar.add(this.btnChatInfo);
/*  5223 */     this.tlbIconBar.add(this.jSeparator23);
/*       */     
/*  5225 */     this.btnPostToS7.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/server.png")));
/*  5226 */     this.btnPostToS7.setToolTipText("Upload to Solaris7.com");
/*  5227 */     this.btnPostToS7.setFocusable(false);
/*  5228 */     this.btnPostToS7.setHorizontalTextPosition(0);
/*  5229 */     this.btnPostToS7.setVerticalTextPosition(3);
/*  5230 */     this.btnPostToS7.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5232 */         frmMainWide.this.btnPostToS7ActionPerformed(evt);
/*       */       }
/*  5234 */     });
/*  5235 */     this.tlbIconBar.add(this.btnPostToS7);
/*  5236 */     this.tlbIconBar.add(this.jSeparator25);
/*       */     
/*  5238 */     this.btnAddToForceList.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/clipboard--plus.png")));
/*  5239 */     this.btnAddToForceList.setToolTipText("Add  to Force List");
/*  5240 */     this.btnAddToForceList.setFocusable(false);
/*  5241 */     this.btnAddToForceList.setHorizontalTextPosition(0);
/*  5242 */     this.btnAddToForceList.setVerticalTextPosition(3);
/*  5243 */     this.btnAddToForceList.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5245 */         frmMainWide.this.btnAddToForceListActionPerformed(evt);
/*       */       }
/*  5247 */     });
/*  5248 */     this.tlbIconBar.add(this.btnAddToForceList);
/*       */     
/*  5250 */     this.btnForceList.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/clipboard.png")));
/*  5251 */     this.btnForceList.setToolTipText("Force List");
/*  5252 */     this.btnForceList.setFocusable(false);
/*  5253 */     this.btnForceList.setHorizontalTextPosition(0);
/*  5254 */     this.btnForceList.setVerticalTextPosition(3);
/*  5255 */     this.btnForceList.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5257 */         frmMainWide.this.btnForceListActionPerformed(evt);
/*       */       }
/*  5259 */     });
/*  5260 */     this.tlbIconBar.add(this.btnForceList);
/*  5261 */     this.tlbIconBar.add(this.jSeparator26);
/*       */     
/*  5263 */     this.btnOptionsIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/gear.png")));
/*  5264 */     this.btnOptionsIcon.setToolTipText("View Options");
/*  5265 */     this.btnOptionsIcon.setFocusable(false);
/*  5266 */     this.btnOptionsIcon.setHorizontalTextPosition(0);
/*  5267 */     this.btnOptionsIcon.setVerticalTextPosition(3);
/*  5268 */     this.btnOptionsIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5270 */         frmMainWide.this.btnOptionsIconActionPerformed(evt);
/*       */       }
/*  5272 */     });
/*  5273 */     this.tlbIconBar.add(this.btnOptionsIcon);
/*  5274 */     this.tlbIconBar.add(this.jSeparator21);
/*       */     
/*  5276 */     this.lblSelectVariant.setText("Selected Variant: ");
/*  5277 */     this.lblSelectVariant.setEnabled(false);
/*  5278 */     this.tlbIconBar.add(this.lblSelectVariant);
/*       */     
/*  5280 */     this.cmbOmniVariant.setEnabled(false);
/*  5281 */     this.cmbOmniVariant.setMaximumSize(new Dimension(150, 20));
/*  5282 */     this.cmbOmniVariant.setMinimumSize(new Dimension(150, 20));
/*  5283 */     this.cmbOmniVariant.setPreferredSize(new Dimension(150, 20));
/*  5284 */     this.cmbOmniVariant.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5286 */         frmMainWide.this.cmbOmniVariantActionPerformed(evt);
/*       */       }
/*  5288 */     });
/*  5289 */     this.tlbIconBar.add(this.cmbOmniVariant);
/*       */     
/*  5291 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  5292 */     gridBagConstraints.fill = 1;
/*  5293 */     gridBagConstraints.anchor = 18;
/*  5294 */     getContentPane().add(this.tlbIconBar, gridBagConstraints);
/*       */     
/*  5296 */     this.pnlBasicSetup.setLayout(new GridBagLayout());
/*       */     
/*  5298 */     this.pnlBasicInformation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Basic Information"));
/*  5299 */     this.pnlBasicInformation.setLayout(new GridBagLayout());
/*       */     
/*  5301 */     this.lblMechName.setText("Mech Name:");
/*  5302 */     gridBagConstraints = new GridBagConstraints();
/*  5303 */     gridBagConstraints.anchor = 13;
/*  5304 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5305 */     this.pnlBasicInformation.add(this.lblMechName, gridBagConstraints);
/*       */     
/*  5307 */     this.txtMechName.setMaximumSize(new Dimension(150, 20));
/*  5308 */     this.txtMechName.setMinimumSize(new Dimension(150, 20));
/*  5309 */     this.txtMechName.setPreferredSize(new Dimension(150, 20));
/*  5310 */     gridBagConstraints = new GridBagConstraints();
/*  5311 */     gridBagConstraints.gridwidth = 2;
/*  5312 */     this.pnlBasicInformation.add(this.txtMechName, gridBagConstraints);
/*  5313 */     MouseListener mlMechName = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  5315 */         if (e.isPopupTrigger())
/*  5316 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  5320 */         if (e.isPopupTrigger()) {
/*  5321 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  5324 */     };
/*  5325 */     this.txtMechName.addMouseListener(mlMechName);
/*       */     
/*  5327 */     this.lblModel.setText("Model:");
/*  5328 */     gridBagConstraints = new GridBagConstraints();
/*  5329 */     gridBagConstraints.gridx = 0;
/*  5330 */     gridBagConstraints.gridy = 1;
/*  5331 */     gridBagConstraints.anchor = 13;
/*  5332 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5333 */     this.pnlBasicInformation.add(this.lblModel, gridBagConstraints);
/*       */     
/*  5335 */     this.txtMechModel.setMaximumSize(new Dimension(150, 20));
/*  5336 */     this.txtMechModel.setMinimumSize(new Dimension(150, 20));
/*  5337 */     this.txtMechModel.setPreferredSize(new Dimension(150, 20));
/*  5338 */     gridBagConstraints = new GridBagConstraints();
/*  5339 */     gridBagConstraints.gridx = 1;
/*  5340 */     gridBagConstraints.gridy = 1;
/*  5341 */     gridBagConstraints.gridwidth = 2;
/*  5342 */     this.pnlBasicInformation.add(this.txtMechModel, gridBagConstraints);
/*  5343 */     MouseListener mlMechModel = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  5345 */         if (e.isPopupTrigger())
/*  5346 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  5350 */         if (e.isPopupTrigger()) {
/*  5351 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  5354 */     };
/*  5355 */     this.txtMechModel.addMouseListener(mlMechModel);
/*       */     
/*  5357 */     this.lblMechEra.setText("Era:");
/*  5358 */     gridBagConstraints = new GridBagConstraints();
/*  5359 */     gridBagConstraints.gridx = 0;
/*  5360 */     gridBagConstraints.gridy = 4;
/*  5361 */     gridBagConstraints.anchor = 13;
/*  5362 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5363 */     this.pnlBasicInformation.add(this.lblMechEra, gridBagConstraints);
/*       */     
/*  5365 */     this.cmbMechEra.setModel(new DefaultComboBoxModel(new String[] { "Age of War/Star League", "Succession Wars", "Clan Invasion", "Dark Ages", "All Eras (non-canon)" }));
/*  5366 */     this.cmbMechEra.setMaximumSize(new Dimension(150, 20));
/*  5367 */     this.cmbMechEra.setMinimumSize(new Dimension(150, 20));
/*  5368 */     this.cmbMechEra.setPreferredSize(new Dimension(150, 20));
/*  5369 */     this.cmbMechEra.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5371 */         frmMainWide.this.cmbMechEraActionPerformed(evt);
/*       */       }
/*  5373 */     });
/*  5374 */     gridBagConstraints = new GridBagConstraints();
/*  5375 */     gridBagConstraints.gridx = 1;
/*  5376 */     gridBagConstraints.gridy = 4;
/*  5377 */     gridBagConstraints.gridwidth = 2;
/*  5378 */     this.pnlBasicInformation.add(this.cmbMechEra, gridBagConstraints);
/*       */     
/*  5380 */     this.lblEraYears.setText("2443~2800");
/*  5381 */     gridBagConstraints = new GridBagConstraints();
/*  5382 */     gridBagConstraints.gridx = 1;
/*  5383 */     gridBagConstraints.gridy = 6;
/*  5384 */     gridBagConstraints.insets = new Insets(2, 0, 2, 0);
/*  5385 */     this.pnlBasicInformation.add(this.lblEraYears, gridBagConstraints);
/*       */     
/*  5387 */     this.lblProdYear.setText("Prod Year/Era:");
/*  5388 */     gridBagConstraints = new GridBagConstraints();
/*  5389 */     gridBagConstraints.gridx = 0;
/*  5390 */     gridBagConstraints.gridy = 7;
/*  5391 */     gridBagConstraints.anchor = 13;
/*  5392 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5393 */     this.pnlBasicInformation.add(this.lblProdYear, gridBagConstraints);
/*       */     
/*  5395 */     this.txtProdYear.setHorizontalAlignment(0);
/*  5396 */     this.txtProdYear.setDisabledTextColor(new Color(0, 0, 0));
/*  5397 */     this.txtProdYear.setMaximumSize(new Dimension(60, 20));
/*  5398 */     this.txtProdYear.setMinimumSize(new Dimension(60, 20));
/*  5399 */     this.txtProdYear.setPreferredSize(new Dimension(60, 20));
/*  5400 */     gridBagConstraints = new GridBagConstraints();
/*  5401 */     gridBagConstraints.gridx = 1;
/*  5402 */     gridBagConstraints.gridy = 7;
/*  5403 */     this.pnlBasicInformation.add(this.txtProdYear, gridBagConstraints);
/*  5404 */     MouseListener mlProdYear = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  5406 */         if (e.isPopupTrigger())
/*  5407 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  5411 */         if (e.isPopupTrigger()) {
/*  5412 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  5415 */     };
/*  5416 */     this.txtProdYear.addMouseListener(mlProdYear);
/*       */     
/*  5418 */     this.chkYearRestrict.setText("Restrict Availability by Year");
/*  5419 */     this.chkYearRestrict.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5421 */         frmMainWide.this.chkYearRestrictActionPerformed(evt);
/*       */       }
/*  5423 */     });
/*  5424 */     gridBagConstraints = new GridBagConstraints();
/*  5425 */     gridBagConstraints.gridx = 1;
/*  5426 */     gridBagConstraints.gridy = 8;
/*  5427 */     gridBagConstraints.gridwidth = 2;
/*  5428 */     gridBagConstraints.anchor = 17;
/*  5429 */     gridBagConstraints.insets = new Insets(2, 0, 2, 0);
/*  5430 */     this.pnlBasicInformation.add(this.chkYearRestrict, gridBagConstraints);
/*       */     
/*  5432 */     this.lblTechBase.setText("Tech Base:");
/*  5433 */     gridBagConstraints = new GridBagConstraints();
/*  5434 */     gridBagConstraints.gridx = 0;
/*  5435 */     gridBagConstraints.gridy = 5;
/*  5436 */     gridBagConstraints.anchor = 13;
/*  5437 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5438 */     this.pnlBasicInformation.add(this.lblTechBase, gridBagConstraints);
/*       */     
/*  5440 */     this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/*  5441 */     this.cmbTechBase.setMaximumSize(new Dimension(150, 20));
/*  5442 */     this.cmbTechBase.setMinimumSize(new Dimension(150, 20));
/*  5443 */     this.cmbTechBase.setPreferredSize(new Dimension(150, 20));
/*  5444 */     this.cmbTechBase.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5446 */         frmMainWide.this.cmbTechBaseActionPerformed(evt);
/*       */       }
/*  5448 */     });
/*  5449 */     gridBagConstraints = new GridBagConstraints();
/*  5450 */     gridBagConstraints.gridx = 1;
/*  5451 */     gridBagConstraints.gridy = 5;
/*  5452 */     gridBagConstraints.gridwidth = 2;
/*  5453 */     this.pnlBasicInformation.add(this.cmbTechBase, gridBagConstraints);
/*       */     
/*  5455 */     this.cmbRulesLevel.setModel(new DefaultComboBoxModel(new String[] { "Introductory", "Tournament Legal", "Advanced Rules", "Experimental Tech", "Era Specific" }));
/*  5456 */     this.cmbRulesLevel.setSelectedIndex(1);
/*  5457 */     this.cmbRulesLevel.setMaximumSize(new Dimension(150, 20));
/*  5458 */     this.cmbRulesLevel.setMinimumSize(new Dimension(150, 20));
/*  5459 */     this.cmbRulesLevel.setPreferredSize(new Dimension(150, 20));
/*  5460 */     this.cmbRulesLevel.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5462 */         frmMainWide.this.cmbRulesLevelActionPerformed(evt);
/*       */       }
/*  5464 */     });
/*  5465 */     gridBagConstraints = new GridBagConstraints();
/*  5466 */     gridBagConstraints.gridx = 1;
/*  5467 */     gridBagConstraints.gridy = 3;
/*  5468 */     gridBagConstraints.gridwidth = 2;
/*  5469 */     this.pnlBasicInformation.add(this.cmbRulesLevel, gridBagConstraints);
/*       */     
/*  5471 */     this.lblRulesLevel.setText("Rules Level:");
/*  5472 */     gridBagConstraints = new GridBagConstraints();
/*  5473 */     gridBagConstraints.gridx = 0;
/*  5474 */     gridBagConstraints.gridy = 3;
/*  5475 */     gridBagConstraints.anchor = 13;
/*  5476 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5477 */     this.pnlBasicInformation.add(this.lblRulesLevel, gridBagConstraints);
/*       */     
/*  5479 */     this.jLabel65.setText("Source:");
/*  5480 */     gridBagConstraints = new GridBagConstraints();
/*  5481 */     gridBagConstraints.gridx = 0;
/*  5482 */     gridBagConstraints.gridy = 2;
/*  5483 */     gridBagConstraints.anchor = 13;
/*  5484 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/*  5485 */     this.pnlBasicInformation.add(this.jLabel65, gridBagConstraints);
/*       */     
/*  5487 */     this.txtSource.setMaximumSize(new Dimension(150, 20));
/*  5488 */     this.txtSource.setMinimumSize(new Dimension(150, 20));
/*  5489 */     this.txtSource.setPreferredSize(new Dimension(150, 20));
/*  5490 */     gridBagConstraints = new GridBagConstraints();
/*  5491 */     gridBagConstraints.gridx = 1;
/*  5492 */     gridBagConstraints.gridy = 2;
/*  5493 */     gridBagConstraints.gridwidth = 2;
/*  5494 */     this.pnlBasicInformation.add(this.txtSource, gridBagConstraints);
/*  5495 */     MouseListener mlSource = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  5497 */         if (e.isPopupTrigger())
/*  5498 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  5502 */         if (e.isPopupTrigger()) {
/*  5503 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  5506 */     };
/*  5507 */     this.txtSource.addMouseListener(mlSource);
/*  5508 */     this.pnlBasicInformation.add(this.jSeparator28, new GridBagConstraints());
/*  5509 */     this.pnlBasicInformation.add(this.jSeparator29, new GridBagConstraints());
/*       */     
/*  5511 */     this.cmbProductionEra.setModel(new DefaultComboBoxModel(new String[] { "Age of War", "Star League", "Early Succession War", "Late Succession War", "Clan Invasion", "Civil War", "Jihad", "Early Republic", "Late Republic", "Dark Ages" }));
/*  5512 */     this.cmbProductionEra.setLightWeightPopupEnabled(false);
/*  5513 */     this.cmbProductionEra.setMaximumSize(new Dimension(90, 20));
/*  5514 */     this.cmbProductionEra.setMinimumSize(new Dimension(90, 20));
/*  5515 */     this.cmbProductionEra.setPreferredSize(new Dimension(90, 20));
/*  5516 */     this.cmbProductionEra.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5518 */         frmMainWide.this.cmbProductionEraActionPerformed(evt);
/*       */       }
/*  5520 */     });
/*  5521 */     gridBagConstraints = new GridBagConstraints();
/*  5522 */     gridBagConstraints.gridx = 2;
/*  5523 */     gridBagConstraints.gridy = 7;
/*  5524 */     this.pnlBasicInformation.add(this.cmbProductionEra, gridBagConstraints);
/*       */     
/*  5526 */     gridBagConstraints = new GridBagConstraints();
/*  5527 */     gridBagConstraints.gridx = 0;
/*  5528 */     gridBagConstraints.gridy = 0;
/*  5529 */     gridBagConstraints.gridheight = 3;
/*  5530 */     gridBagConstraints.fill = 1;
/*  5531 */     this.pnlBasicSetup.add(this.pnlBasicInformation, gridBagConstraints);
/*       */     
/*  5533 */     this.pnlChassis.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chassis"));
/*  5534 */     this.pnlChassis.setLayout(new GridBagLayout());
/*       */     
/*  5536 */     this.lblTonnage.setText("Tonnage:");
/*  5537 */     gridBagConstraints = new GridBagConstraints();
/*  5538 */     gridBagConstraints.anchor = 13;
/*  5539 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5540 */     this.pnlChassis.add(this.lblTonnage, gridBagConstraints);
/*       */     
/*  5542 */     this.cmbTonnage.setModel(new DefaultComboBoxModel(new String[] { "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100" }));
/*  5543 */     this.cmbTonnage.setSelectedIndex(2);
/*  5544 */     this.cmbTonnage.setMaximumSize(new Dimension(60, 20));
/*  5545 */     this.cmbTonnage.setMinimumSize(new Dimension(60, 20));
/*  5546 */     this.cmbTonnage.setPreferredSize(new Dimension(60, 20));
/*  5547 */     this.cmbTonnage.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5549 */         frmMainWide.this.cmbTonnageActionPerformed(evt);
/*       */       }
/*  5551 */     });
/*  5552 */     gridBagConstraints = new GridBagConstraints();
/*  5553 */     gridBagConstraints.anchor = 17;
/*  5554 */     this.pnlChassis.add(this.cmbTonnage, gridBagConstraints);
/*       */     
/*  5556 */     this.lblMechType.setText("Light Mech");
/*  5557 */     gridBagConstraints = new GridBagConstraints();
/*  5558 */     gridBagConstraints.anchor = 17;
/*  5559 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/*  5560 */     this.pnlChassis.add(this.lblMechType, gridBagConstraints);
/*       */     
/*  5562 */     this.lblMotiveType.setText("Motive Type:");
/*  5563 */     gridBagConstraints = new GridBagConstraints();
/*  5564 */     gridBagConstraints.gridx = 0;
/*  5565 */     gridBagConstraints.gridy = 2;
/*  5566 */     gridBagConstraints.anchor = 13;
/*  5567 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5568 */     this.pnlChassis.add(this.lblMotiveType, gridBagConstraints);
/*       */     
/*  5570 */     this.cmbMotiveType.setModel(new DefaultComboBoxModel(new String[] { "Biped", "Quad" }));
/*  5571 */     this.cmbMotiveType.setMaximumSize(new Dimension(150, 20));
/*  5572 */     this.cmbMotiveType.setMinimumSize(new Dimension(150, 20));
/*  5573 */     this.cmbMotiveType.setPreferredSize(new Dimension(150, 20));
/*  5574 */     this.cmbMotiveType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5576 */         frmMainWide.this.cmbMotiveTypeActionPerformed(evt);
/*       */       }
/*  5578 */     });
/*  5579 */     gridBagConstraints = new GridBagConstraints();
/*  5580 */     gridBagConstraints.gridx = 1;
/*  5581 */     gridBagConstraints.gridy = 2;
/*  5582 */     gridBagConstraints.gridwidth = 2;
/*  5583 */     gridBagConstraints.anchor = 17;
/*  5584 */     this.pnlChassis.add(this.cmbMotiveType, gridBagConstraints);
/*       */     
/*  5586 */     this.lblEngineType.setText("Engine Type:");
/*  5587 */     gridBagConstraints = new GridBagConstraints();
/*  5588 */     gridBagConstraints.gridx = 0;
/*  5589 */     gridBagConstraints.gridy = 5;
/*  5590 */     gridBagConstraints.anchor = 13;
/*  5591 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5592 */     this.pnlChassis.add(this.lblEngineType, gridBagConstraints);
/*       */     
/*  5594 */     this.cmbEngineType.setModel(new DefaultComboBoxModel(new String[] { "Fusion", "Fusion XL" }));
/*  5595 */     this.cmbEngineType.setMaximumSize(new Dimension(150, 20));
/*  5596 */     this.cmbEngineType.setMinimumSize(new Dimension(150, 20));
/*  5597 */     this.cmbEngineType.setPreferredSize(new Dimension(150, 20));
/*  5598 */     this.cmbEngineType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5600 */         frmMainWide.this.cmbEngineTypeActionPerformed(evt);
/*       */       }
/*  5602 */     });
/*  5603 */     gridBagConstraints = new GridBagConstraints();
/*  5604 */     gridBagConstraints.gridx = 1;
/*  5605 */     gridBagConstraints.gridy = 5;
/*  5606 */     gridBagConstraints.gridwidth = 2;
/*  5607 */     gridBagConstraints.anchor = 17;
/*  5608 */     this.pnlChassis.add(this.cmbEngineType, gridBagConstraints);
/*       */     
/*  5610 */     this.lblInternalType.setText("Internal Structure:");
/*  5611 */     gridBagConstraints = new GridBagConstraints();
/*  5612 */     gridBagConstraints.gridx = 0;
/*  5613 */     gridBagConstraints.gridy = 4;
/*  5614 */     gridBagConstraints.anchor = 13;
/*  5615 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5616 */     this.pnlChassis.add(this.lblInternalType, gridBagConstraints);
/*       */     
/*  5618 */     this.cmbInternalType.setModel(new DefaultComboBoxModel(new String[] { "Standard", "Endo Steel" }));
/*  5619 */     this.cmbInternalType.setMaximumSize(new Dimension(150, 20));
/*  5620 */     this.cmbInternalType.setMinimumSize(new Dimension(150, 20));
/*  5621 */     this.cmbInternalType.setPreferredSize(new Dimension(150, 20));
/*  5622 */     this.cmbInternalType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5624 */         frmMainWide.this.cmbInternalTypeActionPerformed(evt);
/*       */       }
/*  5626 */     });
/*  5627 */     gridBagConstraints = new GridBagConstraints();
/*  5628 */     gridBagConstraints.gridx = 1;
/*  5629 */     gridBagConstraints.gridy = 4;
/*  5630 */     gridBagConstraints.gridwidth = 2;
/*  5631 */     this.pnlChassis.add(this.cmbInternalType, gridBagConstraints);
/*       */     
/*  5633 */     this.lblGyroType.setText("Gyro Type:");
/*  5634 */     gridBagConstraints = new GridBagConstraints();
/*  5635 */     gridBagConstraints.gridx = 0;
/*  5636 */     gridBagConstraints.gridy = 6;
/*  5637 */     gridBagConstraints.anchor = 13;
/*  5638 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5639 */     this.pnlChassis.add(this.lblGyroType, gridBagConstraints);
/*       */     
/*  5641 */     this.cmbGyroType.setModel(new DefaultComboBoxModel(new String[] { "Standard" }));
/*  5642 */     this.cmbGyroType.setMaximumSize(new Dimension(150, 20));
/*  5643 */     this.cmbGyroType.setMinimumSize(new Dimension(150, 20));
/*  5644 */     this.cmbGyroType.setPreferredSize(new Dimension(150, 20));
/*  5645 */     this.cmbGyroType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5647 */         frmMainWide.this.cmbGyroTypeActionPerformed(evt);
/*       */       }
/*  5649 */     });
/*  5650 */     gridBagConstraints = new GridBagConstraints();
/*  5651 */     gridBagConstraints.gridx = 1;
/*  5652 */     gridBagConstraints.gridy = 6;
/*  5653 */     gridBagConstraints.gridwidth = 2;
/*  5654 */     this.pnlChassis.add(this.cmbGyroType, gridBagConstraints);
/*       */     
/*  5656 */     this.lblCockpit.setText("Cockpit Type:");
/*  5657 */     gridBagConstraints = new GridBagConstraints();
/*  5658 */     gridBagConstraints.gridx = 0;
/*  5659 */     gridBagConstraints.gridy = 7;
/*  5660 */     gridBagConstraints.anchor = 13;
/*  5661 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5662 */     this.pnlChassis.add(this.lblCockpit, gridBagConstraints);
/*       */     
/*  5664 */     this.cmbCockpitType.setModel(new DefaultComboBoxModel(new String[] { "Standard" }));
/*  5665 */     this.cmbCockpitType.setMaximumSize(new Dimension(150, 20));
/*  5666 */     this.cmbCockpitType.setMinimumSize(new Dimension(150, 20));
/*  5667 */     this.cmbCockpitType.setPreferredSize(new Dimension(150, 20));
/*  5668 */     this.cmbCockpitType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5670 */         frmMainWide.this.cmbCockpitTypeActionPerformed(evt);
/*       */       }
/*  5672 */     });
/*  5673 */     gridBagConstraints = new GridBagConstraints();
/*  5674 */     gridBagConstraints.gridx = 1;
/*  5675 */     gridBagConstraints.gridy = 7;
/*  5676 */     gridBagConstraints.gridwidth = 2;
/*  5677 */     this.pnlChassis.add(this.cmbCockpitType, gridBagConstraints);
/*       */     
/*  5679 */     this.lblPhysEnhance.setText("Enhancements:");
/*  5680 */     gridBagConstraints = new GridBagConstraints();
/*  5681 */     gridBagConstraints.gridx = 0;
/*  5682 */     gridBagConstraints.gridy = 9;
/*  5683 */     gridBagConstraints.anchor = 13;
/*  5684 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5685 */     this.pnlChassis.add(this.lblPhysEnhance, gridBagConstraints);
/*       */     
/*  5687 */     this.cmbPhysEnhance.setModel(new DefaultComboBoxModel(new String[] { "None", "MASC" }));
/*  5688 */     this.cmbPhysEnhance.setMaximumSize(new Dimension(150, 20));
/*  5689 */     this.cmbPhysEnhance.setMinimumSize(new Dimension(150, 20));
/*  5690 */     this.cmbPhysEnhance.setPreferredSize(new Dimension(150, 20));
/*  5691 */     this.cmbPhysEnhance.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5693 */         frmMainWide.this.cmbPhysEnhanceActionPerformed(evt);
/*       */       }
/*  5695 */     });
/*  5696 */     gridBagConstraints = new GridBagConstraints();
/*  5697 */     gridBagConstraints.gridx = 1;
/*  5698 */     gridBagConstraints.gridy = 9;
/*  5699 */     gridBagConstraints.gridwidth = 2;
/*  5700 */     this.pnlChassis.add(this.cmbPhysEnhance, gridBagConstraints);
/*       */     
/*  5702 */     this.chkOmnimech.setText("Omnimech");
/*  5703 */     this.chkOmnimech.setEnabled(false);
/*  5704 */     this.chkOmnimech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5706 */         frmMainWide.this.chkOmnimechActionPerformed(evt);
/*       */       }
/*  5708 */     });
/*  5709 */     gridBagConstraints = new GridBagConstraints();
/*  5710 */     gridBagConstraints.gridx = 1;
/*  5711 */     gridBagConstraints.gridy = 3;
/*  5712 */     gridBagConstraints.gridwidth = 2;
/*  5713 */     gridBagConstraints.anchor = 13;
/*  5714 */     gridBagConstraints.insets = new Insets(2, 0, 2, 2);
/*  5715 */     this.pnlChassis.add(this.chkOmnimech, gridBagConstraints);
/*       */     
/*  5717 */     this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech", "IndustrialMech" }));
/*  5718 */     this.cmbMechType.setMaximumSize(new Dimension(150, 20));
/*  5719 */     this.cmbMechType.setMinimumSize(new Dimension(150, 20));
/*  5720 */     this.cmbMechType.setPreferredSize(new Dimension(150, 20));
/*  5721 */     this.cmbMechType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5723 */         frmMainWide.this.cmbMechTypeActionPerformed(evt);
/*       */       }
/*  5725 */     });
/*  5726 */     gridBagConstraints = new GridBagConstraints();
/*  5727 */     gridBagConstraints.gridx = 1;
/*  5728 */     gridBagConstraints.gridy = 1;
/*  5729 */     gridBagConstraints.gridwidth = 2;
/*  5730 */     this.pnlChassis.add(this.cmbMechType, gridBagConstraints);
/*       */     
/*  5732 */     this.lblUnitType.setText("Mech Type:");
/*  5733 */     gridBagConstraints = new GridBagConstraints();
/*  5734 */     gridBagConstraints.gridx = 0;
/*  5735 */     gridBagConstraints.gridy = 1;
/*  5736 */     gridBagConstraints.anchor = 13;
/*  5737 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5738 */     this.pnlChassis.add(this.lblUnitType, gridBagConstraints);
/*       */     
/*  5740 */     this.chkCommandConsole.setText("Use Command Console");
/*  5741 */     this.chkCommandConsole.setEnabled(false);
/*  5742 */     this.chkCommandConsole.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5744 */         frmMainWide.this.chkCommandConsoleActionPerformed(evt);
/*       */       }
/*  5746 */     });
/*  5747 */     gridBagConstraints = new GridBagConstraints();
/*  5748 */     gridBagConstraints.gridx = 0;
/*  5749 */     gridBagConstraints.gridy = 8;
/*  5750 */     gridBagConstraints.gridwidth = 3;
/*  5751 */     gridBagConstraints.anchor = 13;
/*  5752 */     this.pnlChassis.add(this.chkCommandConsole, gridBagConstraints);
/*       */     
/*  5754 */     this.chkFractional.setText("Use Fractional Accounting");
/*  5755 */     this.chkFractional.setEnabled(false);
/*  5756 */     this.chkFractional.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5758 */         frmMainWide.this.chkFractionalActionPerformed(evt);
/*       */       }
/*  5760 */     });
/*  5761 */     gridBagConstraints = new GridBagConstraints();
/*  5762 */     gridBagConstraints.gridx = 0;
/*  5763 */     gridBagConstraints.gridy = 10;
/*  5764 */     gridBagConstraints.gridwidth = 3;
/*  5765 */     this.pnlChassis.add(this.chkFractional, gridBagConstraints);
/*       */     
/*  5767 */     gridBagConstraints = new GridBagConstraints();
/*  5768 */     gridBagConstraints.gridx = 0;
/*  5769 */     gridBagConstraints.gridy = 3;
/*  5770 */     gridBagConstraints.gridheight = 2;
/*  5771 */     gridBagConstraints.fill = 2;
/*  5772 */     gridBagConstraints.anchor = 11;
/*  5773 */     this.pnlBasicSetup.add(this.pnlChassis, gridBagConstraints);
/*       */     
/*  5775 */     this.pnlHeatSinks.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Heat Sinks"));
/*  5776 */     this.pnlHeatSinks.setLayout(new GridBagLayout());
/*       */     
/*  5778 */     this.lblHeatSinkType.setText("Heat Sink Type:");
/*  5779 */     gridBagConstraints = new GridBagConstraints();
/*  5780 */     gridBagConstraints.anchor = 13;
/*  5781 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5782 */     this.pnlHeatSinks.add(this.lblHeatSinkType, gridBagConstraints);
/*       */     
/*  5784 */     this.cmbHeatSinkType.setModel(new DefaultComboBoxModel(new String[] { "Single", "Double" }));
/*  5785 */     this.cmbHeatSinkType.setMaximumSize(new Dimension(150, 20));
/*  5786 */     this.cmbHeatSinkType.setMinimumSize(new Dimension(150, 20));
/*  5787 */     this.cmbHeatSinkType.setPreferredSize(new Dimension(150, 20));
/*  5788 */     this.cmbHeatSinkType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5790 */         frmMainWide.this.cmbHeatSinkTypeActionPerformed(evt);
/*       */       }
/*  5792 */     });
/*  5793 */     gridBagConstraints = new GridBagConstraints();
/*  5794 */     gridBagConstraints.gridwidth = 2;
/*  5795 */     this.pnlHeatSinks.add(this.cmbHeatSinkType, gridBagConstraints);
/*       */     
/*  5797 */     this.lblHSNumber.setText("Number of Heat Sinks:");
/*  5798 */     gridBagConstraints = new GridBagConstraints();
/*  5799 */     gridBagConstraints.gridx = 0;
/*  5800 */     gridBagConstraints.gridy = 1;
/*  5801 */     gridBagConstraints.anchor = 13;
/*  5802 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5803 */     this.pnlHeatSinks.add(this.lblHSNumber, gridBagConstraints);
/*       */     
/*  5805 */     this.spnNumberOfHS.setModel(new SpinnerNumberModel(10, 10, 65, 1));
/*  5806 */     this.spnNumberOfHS.setMaximumSize(new Dimension(45, 20));
/*  5807 */     this.spnNumberOfHS.setMinimumSize(new Dimension(45, 20));
/*  5808 */     this.spnNumberOfHS.setPreferredSize(new Dimension(45, 20));
/*  5809 */     this.spnNumberOfHS.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  5811 */         frmMainWide.this.spnNumberOfHSStateChanged(evt);
/*       */       }
/*  5813 */     });
/*  5814 */     gridBagConstraints = new GridBagConstraints();
/*  5815 */     gridBagConstraints.gridx = 1;
/*  5816 */     gridBagConstraints.gridy = 1;
/*  5817 */     this.pnlHeatSinks.add(this.spnNumberOfHS, gridBagConstraints);
/*       */     
/*  5819 */     gridBagConstraints = new GridBagConstraints();
/*  5820 */     gridBagConstraints.gridx = 1;
/*  5821 */     gridBagConstraints.gridy = 2;
/*  5822 */     gridBagConstraints.fill = 1;
/*  5823 */     this.pnlBasicSetup.add(this.pnlHeatSinks, gridBagConstraints);
/*       */     
/*  5825 */     this.pnlMovement.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Movement"));
/*  5826 */     this.pnlMovement.setLayout(new GridBagLayout());
/*       */     
/*  5828 */     this.lblWalkMP.setText("Walking MP:");
/*  5829 */     gridBagConstraints = new GridBagConstraints();
/*  5830 */     gridBagConstraints.anchor = 13;
/*  5831 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5832 */     this.pnlMovement.add(this.lblWalkMP, gridBagConstraints);
/*       */     
/*  5834 */     this.spnWalkMP.setModel(new SpinnerNumberModel(1, 1, 20, 1));
/*  5835 */     this.spnWalkMP.setMaximumSize(new Dimension(45, 20));
/*  5836 */     this.spnWalkMP.setMinimumSize(new Dimension(45, 20));
/*  5837 */     this.spnWalkMP.setPreferredSize(new Dimension(45, 20));
/*  5838 */     this.spnWalkMP.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  5840 */         frmMainWide.this.spnWalkMPStateChanged(evt);
/*       */       }
/*  5842 */     });
/*  5843 */     gridBagConstraints = new GridBagConstraints();
/*  5844 */     gridBagConstraints.anchor = 17;
/*  5845 */     this.pnlMovement.add(this.spnWalkMP, gridBagConstraints);
/*       */     
/*  5847 */     this.lblRunMPLabel.setText("Running MP:");
/*  5848 */     gridBagConstraints = new GridBagConstraints();
/*  5849 */     gridBagConstraints.gridx = 0;
/*  5850 */     gridBagConstraints.gridy = 1;
/*  5851 */     gridBagConstraints.anchor = 13;
/*  5852 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5853 */     this.pnlMovement.add(this.lblRunMPLabel, gridBagConstraints);
/*       */     
/*  5855 */     this.lblRunMP.setHorizontalAlignment(0);
/*  5856 */     this.lblRunMP.setText("2");
/*  5857 */     this.lblRunMP.setMaximumSize(new Dimension(45, 20));
/*  5858 */     this.lblRunMP.setMinimumSize(new Dimension(45, 20));
/*  5859 */     this.lblRunMP.setPreferredSize(new Dimension(45, 20));
/*  5860 */     gridBagConstraints = new GridBagConstraints();
/*  5861 */     gridBagConstraints.gridx = 1;
/*  5862 */     gridBagConstraints.gridy = 1;
/*  5863 */     gridBagConstraints.anchor = 17;
/*  5864 */     this.pnlMovement.add(this.lblRunMP, gridBagConstraints);
/*       */     
/*  5866 */     this.lblJumpMP.setText("Jumping MP:");
/*  5867 */     gridBagConstraints = new GridBagConstraints();
/*  5868 */     gridBagConstraints.gridx = 0;
/*  5869 */     gridBagConstraints.gridy = 2;
/*  5870 */     gridBagConstraints.anchor = 13;
/*  5871 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5872 */     this.pnlMovement.add(this.lblJumpMP, gridBagConstraints);
/*       */     
/*  5874 */     this.spnJumpMP.setModel(new SpinnerNumberModel(0, 0, 1, 1));
/*  5875 */     this.spnJumpMP.setMaximumSize(new Dimension(45, 20));
/*  5876 */     this.spnJumpMP.setMinimumSize(new Dimension(45, 20));
/*  5877 */     this.spnJumpMP.setPreferredSize(new Dimension(45, 20));
/*  5878 */     this.spnJumpMP.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  5880 */         frmMainWide.this.spnJumpMPStateChanged(evt);
/*       */       }
/*  5882 */     });
/*  5883 */     gridBagConstraints = new GridBagConstraints();
/*  5884 */     gridBagConstraints.gridx = 1;
/*  5885 */     gridBagConstraints.gridy = 2;
/*  5886 */     gridBagConstraints.anchor = 17;
/*  5887 */     this.pnlMovement.add(this.spnJumpMP, gridBagConstraints);
/*       */     
/*  5889 */     this.cmbJumpJetType.setModel(new DefaultComboBoxModel(new String[] { "Standard Jump Jet", "Improved Jump Jet" }));
/*  5890 */     this.cmbJumpJetType.setMaximumSize(new Dimension(150, 20));
/*  5891 */     this.cmbJumpJetType.setMinimumSize(new Dimension(150, 20));
/*  5892 */     this.cmbJumpJetType.setPreferredSize(new Dimension(150, 20));
/*  5893 */     this.cmbJumpJetType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5895 */         frmMainWide.this.cmbJumpJetTypeActionPerformed(evt);
/*       */       }
/*  5897 */     });
/*  5898 */     gridBagConstraints = new GridBagConstraints();
/*  5899 */     gridBagConstraints.gridx = 2;
/*  5900 */     gridBagConstraints.gridy = 2;
/*  5901 */     gridBagConstraints.anchor = 17;
/*  5902 */     gridBagConstraints.insets = new Insets(2, 4, 0, 0);
/*  5903 */     this.pnlMovement.add(this.cmbJumpJetType, gridBagConstraints);
/*       */     
/*  5905 */     this.jLabel36.setText("Jump Jet Type:");
/*  5906 */     gridBagConstraints = new GridBagConstraints();
/*  5907 */     gridBagConstraints.gridx = 2;
/*  5908 */     gridBagConstraints.gridy = 1;
/*  5909 */     this.pnlMovement.add(this.jLabel36, gridBagConstraints);
/*       */     
/*  5911 */     this.jLabel53.setText("Booster MP:");
/*  5912 */     this.jLabel53.setEnabled(false);
/*  5913 */     gridBagConstraints = new GridBagConstraints();
/*  5914 */     gridBagConstraints.gridx = 0;
/*  5915 */     gridBagConstraints.gridy = 3;
/*  5916 */     gridBagConstraints.anchor = 13;
/*  5917 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5918 */     this.pnlMovement.add(this.jLabel53, gridBagConstraints);
/*       */     
/*  5920 */     this.spnBoosterMP.setEnabled(false);
/*  5921 */     this.spnBoosterMP.setMaximumSize(new Dimension(45, 20));
/*  5922 */     this.spnBoosterMP.setMinimumSize(new Dimension(45, 20));
/*  5923 */     this.spnBoosterMP.setPreferredSize(new Dimension(45, 20));
/*  5924 */     this.spnBoosterMP.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  5926 */         frmMainWide.this.spnBoosterMPStateChanged(evt);
/*       */       }
/*  5928 */     });
/*  5929 */     gridBagConstraints = new GridBagConstraints();
/*  5930 */     gridBagConstraints.gridx = 1;
/*  5931 */     gridBagConstraints.gridy = 3;
/*  5932 */     this.pnlMovement.add(this.spnBoosterMP, gridBagConstraints);
/*       */     
/*  5934 */     this.chkBoosters.setText("'Mech Jump Boosters");
/*  5935 */     this.chkBoosters.setEnabled(false);
/*  5936 */     this.chkBoosters.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5938 */         frmMainWide.this.chkBoostersActionPerformed(evt);
/*       */       }
/*  5940 */     });
/*  5941 */     gridBagConstraints = new GridBagConstraints();
/*  5942 */     gridBagConstraints.gridx = 2;
/*  5943 */     gridBagConstraints.gridy = 3;
/*  5944 */     gridBagConstraints.insets = new Insets(2, 0, 0, 0);
/*  5945 */     this.pnlMovement.add(this.chkBoosters, gridBagConstraints);
/*       */     
/*  5947 */     this.lblMoveSummary.setText("W/R/J/B: 12/20/12/12");
/*  5948 */     gridBagConstraints = new GridBagConstraints();
/*  5949 */     gridBagConstraints.anchor = 17;
/*  5950 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  5951 */     this.pnlMovement.add(this.lblMoveSummary, gridBagConstraints);
/*       */     
/*  5953 */     this.jLabel1.setText("Engine Rating: ");
/*  5954 */     gridBagConstraints = new GridBagConstraints();
/*  5955 */     gridBagConstraints.gridx = 0;
/*  5956 */     gridBagConstraints.gridy = 4;
/*  5957 */     gridBagConstraints.gridwidth = 2;
/*  5958 */     gridBagConstraints.anchor = 13;
/*  5959 */     gridBagConstraints.insets = new Insets(2, 0, 0, 0);
/*  5960 */     this.pnlMovement.add(this.jLabel1, gridBagConstraints);
/*       */     
/*  5962 */     this.txtEngineRating.setHorizontalAlignment(0);
/*  5963 */     this.txtEngineRating.setText("100");
/*  5964 */     this.txtEngineRating.setDisabledTextColor(new Color(0, 0, 0));
/*  5965 */     this.txtEngineRating.setEnabled(false);
/*  5966 */     this.txtEngineRating.setMaximumSize(new Dimension(65, 20));
/*  5967 */     this.txtEngineRating.setMinimumSize(new Dimension(65, 20));
/*  5968 */     this.txtEngineRating.setPreferredSize(new Dimension(65, 20));
/*  5969 */     gridBagConstraints = new GridBagConstraints();
/*  5970 */     gridBagConstraints.gridx = 2;
/*  5971 */     gridBagConstraints.gridy = 4;
/*  5972 */     gridBagConstraints.anchor = 17;
/*  5973 */     gridBagConstraints.insets = new Insets(2, 4, 0, 0);
/*  5974 */     this.pnlMovement.add(this.txtEngineRating, gridBagConstraints);
/*       */     
/*  5976 */     gridBagConstraints = new GridBagConstraints();
/*  5977 */     gridBagConstraints.gridx = 1;
/*  5978 */     gridBagConstraints.gridy = 0;
/*  5979 */     gridBagConstraints.gridheight = 2;
/*  5980 */     gridBagConstraints.fill = 1;
/*  5981 */     this.pnlBasicSetup.add(this.pnlMovement, gridBagConstraints);
/*       */     
/*  5983 */     this.pnlOmniInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Omnimech Configuration"));
/*  5984 */     this.pnlOmniInfo.setLayout(new GridBagLayout());
/*       */     
/*  5986 */     this.btnLockChassis.setText("Lock Chassis");
/*  5987 */     this.btnLockChassis.setEnabled(false);
/*  5988 */     this.btnLockChassis.setMaximumSize(new Dimension(200, 23));
/*  5989 */     this.btnLockChassis.setMinimumSize(new Dimension(105, 23));
/*  5990 */     this.btnLockChassis.setPreferredSize(new Dimension(120, 23));
/*  5991 */     this.btnLockChassis.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5993 */         frmMainWide.this.btnLockChassisActionPerformed(evt);
/*       */       }
/*  5995 */     });
/*  5996 */     gridBagConstraints = new GridBagConstraints();
/*  5997 */     gridBagConstraints.gridx = 0;
/*  5998 */     gridBagConstraints.gridy = 0;
/*  5999 */     this.pnlOmniInfo.add(this.btnLockChassis, gridBagConstraints);
/*       */     
/*  6001 */     this.btnAddVariant.setText("Add Variant");
/*  6002 */     this.btnAddVariant.setEnabled(false);
/*  6003 */     this.btnAddVariant.setMaximumSize(new Dimension(200, 23));
/*  6004 */     this.btnAddVariant.setMinimumSize(new Dimension(80, 23));
/*  6005 */     this.btnAddVariant.setPreferredSize(new Dimension(120, 23));
/*  6006 */     this.btnAddVariant.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6008 */         frmMainWide.this.btnAddVariantActionPerformed(evt);
/*       */       }
/*  6010 */     });
/*  6011 */     gridBagConstraints = new GridBagConstraints();
/*  6012 */     gridBagConstraints.gridx = 0;
/*  6013 */     gridBagConstraints.gridy = 1;
/*  6014 */     this.pnlOmniInfo.add(this.btnAddVariant, gridBagConstraints);
/*       */     
/*  6016 */     this.btnDeleteVariant.setText("Delete Variant");
/*  6017 */     this.btnDeleteVariant.setEnabled(false);
/*  6018 */     this.btnDeleteVariant.setMaximumSize(new Dimension(200, 23));
/*  6019 */     this.btnDeleteVariant.setMinimumSize(new Dimension(80, 23));
/*  6020 */     this.btnDeleteVariant.setPreferredSize(new Dimension(120, 23));
/*  6021 */     this.btnDeleteVariant.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6023 */         frmMainWide.this.btnDeleteVariantActionPerformed(evt);
/*       */       }
/*  6025 */     });
/*  6026 */     gridBagConstraints = new GridBagConstraints();
/*  6027 */     gridBagConstraints.gridx = 0;
/*  6028 */     gridBagConstraints.gridy = 2;
/*  6029 */     this.pnlOmniInfo.add(this.btnDeleteVariant, gridBagConstraints);
/*       */     
/*  6031 */     this.btnRenameVariant.setText("Rename Variant");
/*  6032 */     this.btnRenameVariant.setEnabled(false);
/*  6033 */     this.btnRenameVariant.setMinimumSize(new Dimension(80, 23));
/*  6034 */     this.btnRenameVariant.setPreferredSize(new Dimension(120, 23));
/*  6035 */     this.btnRenameVariant.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6037 */         frmMainWide.this.btnRenameVariantActionPerformed(evt);
/*       */       }
/*  6039 */     });
/*  6040 */     gridBagConstraints = new GridBagConstraints();
/*  6041 */     gridBagConstraints.gridx = 0;
/*  6042 */     gridBagConstraints.gridy = 3;
/*  6043 */     this.pnlOmniInfo.add(this.btnRenameVariant, gridBagConstraints);
/*       */     
/*  6045 */     gridBagConstraints = new GridBagConstraints();
/*  6046 */     gridBagConstraints.gridx = 4;
/*  6047 */     gridBagConstraints.gridy = 0;
/*  6048 */     gridBagConstraints.fill = 2;
/*  6049 */     gridBagConstraints.anchor = 11;
/*  6050 */     this.pnlBasicSetup.add(this.pnlOmniInfo, gridBagConstraints);
/*       */     
/*  6052 */     this.pnlBasicSummary.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Basic Setup Summary"));
/*  6053 */     this.pnlBasicSummary.setLayout(new GridBagLayout());
/*       */     
/*  6055 */     this.lblSumStructure.setText("Internal Structure:");
/*  6056 */     gridBagConstraints = new GridBagConstraints();
/*  6057 */     gridBagConstraints.gridx = 0;
/*  6058 */     gridBagConstraints.gridy = 1;
/*  6059 */     gridBagConstraints.anchor = 13;
/*  6060 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6061 */     this.pnlBasicSummary.add(this.lblSumStructure, gridBagConstraints);
/*       */     
/*  6063 */     this.txtSumIntTon.setHorizontalAlignment(11);
/*  6064 */     this.txtSumIntTon.setText("000.00");
/*  6065 */     this.txtSumIntTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6066 */     this.txtSumIntTon.setEnabled(false);
/*  6067 */     this.txtSumIntTon.setMaximumSize(new Dimension(50, 20));
/*  6068 */     this.txtSumIntTon.setMinimumSize(new Dimension(50, 20));
/*  6069 */     this.txtSumIntTon.setPreferredSize(new Dimension(50, 20));
/*  6070 */     gridBagConstraints = new GridBagConstraints();
/*  6071 */     gridBagConstraints.gridx = 1;
/*  6072 */     gridBagConstraints.gridy = 1;
/*  6073 */     this.pnlBasicSummary.add(this.txtSumIntTon, gridBagConstraints);
/*       */     
/*  6075 */     this.lblSumEngine.setText("Engine:");
/*  6076 */     gridBagConstraints = new GridBagConstraints();
/*  6077 */     gridBagConstraints.gridx = 0;
/*  6078 */     gridBagConstraints.gridy = 2;
/*  6079 */     gridBagConstraints.anchor = 13;
/*  6080 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6081 */     this.pnlBasicSummary.add(this.lblSumEngine, gridBagConstraints);
/*       */     
/*  6083 */     this.txtSumEngTon.setHorizontalAlignment(11);
/*  6084 */     this.txtSumEngTon.setText("000.00");
/*  6085 */     this.txtSumEngTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6086 */     this.txtSumEngTon.setEnabled(false);
/*  6087 */     this.txtSumEngTon.setMaximumSize(new Dimension(50, 20));
/*  6088 */     this.txtSumEngTon.setMinimumSize(new Dimension(50, 20));
/*  6089 */     this.txtSumEngTon.setPreferredSize(new Dimension(50, 20));
/*  6090 */     gridBagConstraints = new GridBagConstraints();
/*  6091 */     gridBagConstraints.gridx = 1;
/*  6092 */     gridBagConstraints.gridy = 2;
/*  6093 */     this.pnlBasicSummary.add(this.txtSumEngTon, gridBagConstraints);
/*       */     
/*  6095 */     this.lblSumGyro.setText("Gyro:");
/*  6096 */     gridBagConstraints = new GridBagConstraints();
/*  6097 */     gridBagConstraints.gridx = 0;
/*  6098 */     gridBagConstraints.gridy = 3;
/*  6099 */     gridBagConstraints.anchor = 13;
/*  6100 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6101 */     this.pnlBasicSummary.add(this.lblSumGyro, gridBagConstraints);
/*       */     
/*  6103 */     this.txtSumGyrTon.setHorizontalAlignment(11);
/*  6104 */     this.txtSumGyrTon.setText("000.00");
/*  6105 */     this.txtSumGyrTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6106 */     this.txtSumGyrTon.setEnabled(false);
/*  6107 */     this.txtSumGyrTon.setMaximumSize(new Dimension(50, 20));
/*  6108 */     this.txtSumGyrTon.setMinimumSize(new Dimension(50, 20));
/*  6109 */     this.txtSumGyrTon.setPreferredSize(new Dimension(50, 20));
/*  6110 */     gridBagConstraints = new GridBagConstraints();
/*  6111 */     gridBagConstraints.gridx = 1;
/*  6112 */     gridBagConstraints.gridy = 3;
/*  6113 */     this.pnlBasicSummary.add(this.txtSumGyrTon, gridBagConstraints);
/*       */     
/*  6115 */     this.lblSumHeadItem.setText("Item");
/*  6116 */     this.pnlBasicSummary.add(this.lblSumHeadItem, new GridBagConstraints());
/*       */     
/*  6118 */     this.lblSumHeadTons.setText("Tonnage");
/*  6119 */     this.pnlBasicSummary.add(this.lblSumHeadTons, new GridBagConstraints());
/*       */     
/*  6121 */     this.lblSumHeadCrits.setText("Crits");
/*  6122 */     this.pnlBasicSummary.add(this.lblSumHeadCrits, new GridBagConstraints());
/*       */     
/*  6124 */     this.txtSumIntCrt.setHorizontalAlignment(11);
/*  6125 */     this.txtSumIntCrt.setText("00");
/*  6126 */     this.txtSumIntCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6127 */     this.txtSumIntCrt.setEnabled(false);
/*  6128 */     this.txtSumIntCrt.setMaximumSize(new Dimension(40, 20));
/*  6129 */     this.txtSumIntCrt.setMinimumSize(new Dimension(40, 20));
/*  6130 */     this.txtSumIntCrt.setPreferredSize(new Dimension(40, 20));
/*  6131 */     gridBagConstraints = new GridBagConstraints();
/*  6132 */     gridBagConstraints.gridx = 2;
/*  6133 */     gridBagConstraints.gridy = 1;
/*  6134 */     this.pnlBasicSummary.add(this.txtSumIntCrt, gridBagConstraints);
/*       */     
/*  6136 */     this.txtSumEngCrt.setHorizontalAlignment(11);
/*  6137 */     this.txtSumEngCrt.setText("00");
/*  6138 */     this.txtSumEngCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6139 */     this.txtSumEngCrt.setEnabled(false);
/*  6140 */     this.txtSumEngCrt.setMaximumSize(new Dimension(40, 20));
/*  6141 */     this.txtSumEngCrt.setMinimumSize(new Dimension(40, 20));
/*  6142 */     this.txtSumEngCrt.setPreferredSize(new Dimension(40, 20));
/*  6143 */     gridBagConstraints = new GridBagConstraints();
/*  6144 */     gridBagConstraints.gridx = 2;
/*  6145 */     gridBagConstraints.gridy = 2;
/*  6146 */     this.pnlBasicSummary.add(this.txtSumEngCrt, gridBagConstraints);
/*       */     
/*  6148 */     this.txtSumGyrCrt.setHorizontalAlignment(11);
/*  6149 */     this.txtSumGyrCrt.setText("00");
/*  6150 */     this.txtSumGyrCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6151 */     this.txtSumGyrCrt.setEnabled(false);
/*  6152 */     this.txtSumGyrCrt.setMaximumSize(new Dimension(40, 20));
/*  6153 */     this.txtSumGyrCrt.setMinimumSize(new Dimension(40, 20));
/*  6154 */     this.txtSumGyrCrt.setPreferredSize(new Dimension(40, 20));
/*  6155 */     gridBagConstraints = new GridBagConstraints();
/*  6156 */     gridBagConstraints.gridx = 2;
/*  6157 */     gridBagConstraints.gridy = 3;
/*  6158 */     this.pnlBasicSummary.add(this.txtSumGyrCrt, gridBagConstraints);
/*       */     
/*  6160 */     this.lblSumCockpit.setText("Cockpit:");
/*  6161 */     gridBagConstraints = new GridBagConstraints();
/*  6162 */     gridBagConstraints.gridx = 0;
/*  6163 */     gridBagConstraints.gridy = 4;
/*  6164 */     gridBagConstraints.anchor = 13;
/*  6165 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6166 */     this.pnlBasicSummary.add(this.lblSumCockpit, gridBagConstraints);
/*       */     
/*  6168 */     this.txtSumCocTon.setHorizontalAlignment(11);
/*  6169 */     this.txtSumCocTon.setText("000.00");
/*  6170 */     this.txtSumCocTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6171 */     this.txtSumCocTon.setEnabled(false);
/*  6172 */     this.txtSumCocTon.setMaximumSize(new Dimension(50, 20));
/*  6173 */     this.txtSumCocTon.setMinimumSize(new Dimension(50, 20));
/*  6174 */     this.txtSumCocTon.setPreferredSize(new Dimension(50, 20));
/*  6175 */     gridBagConstraints = new GridBagConstraints();
/*  6176 */     gridBagConstraints.gridx = 1;
/*  6177 */     gridBagConstraints.gridy = 4;
/*  6178 */     this.pnlBasicSummary.add(this.txtSumCocTon, gridBagConstraints);
/*       */     
/*  6180 */     this.txtSumCocCrt.setHorizontalAlignment(11);
/*  6181 */     this.txtSumCocCrt.setText("00");
/*  6182 */     this.txtSumCocCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6183 */     this.txtSumCocCrt.setEnabled(false);
/*  6184 */     this.txtSumCocCrt.setMaximumSize(new Dimension(40, 20));
/*  6185 */     this.txtSumCocCrt.setMinimumSize(new Dimension(40, 20));
/*  6186 */     this.txtSumCocCrt.setPreferredSize(new Dimension(40, 20));
/*  6187 */     gridBagConstraints = new GridBagConstraints();
/*  6188 */     gridBagConstraints.gridx = 2;
/*  6189 */     gridBagConstraints.gridy = 4;
/*  6190 */     this.pnlBasicSummary.add(this.txtSumCocCrt, gridBagConstraints);
/*       */     
/*  6192 */     this.lblSumEnhance.setText("Enhancements:");
/*  6193 */     gridBagConstraints = new GridBagConstraints();
/*  6194 */     gridBagConstraints.gridx = 0;
/*  6195 */     gridBagConstraints.gridy = 6;
/*  6196 */     gridBagConstraints.anchor = 13;
/*  6197 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6198 */     this.pnlBasicSummary.add(this.lblSumEnhance, gridBagConstraints);
/*       */     
/*  6200 */     this.txtSumEnhTon.setHorizontalAlignment(11);
/*  6201 */     this.txtSumEnhTon.setText("000.00");
/*  6202 */     this.txtSumEnhTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6203 */     this.txtSumEnhTon.setEnabled(false);
/*  6204 */     this.txtSumEnhTon.setMaximumSize(new Dimension(50, 20));
/*  6205 */     this.txtSumEnhTon.setMinimumSize(new Dimension(50, 20));
/*  6206 */     this.txtSumEnhTon.setPreferredSize(new Dimension(50, 20));
/*  6207 */     gridBagConstraints = new GridBagConstraints();
/*  6208 */     gridBagConstraints.gridx = 1;
/*  6209 */     gridBagConstraints.gridy = 6;
/*  6210 */     this.pnlBasicSummary.add(this.txtSumEnhTon, gridBagConstraints);
/*       */     
/*  6212 */     this.txtSumEnhCrt.setHorizontalAlignment(11);
/*  6213 */     this.txtSumEnhCrt.setText("00");
/*  6214 */     this.txtSumEnhCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6215 */     this.txtSumEnhCrt.setEnabled(false);
/*  6216 */     this.txtSumEnhCrt.setMaximumSize(new Dimension(40, 20));
/*  6217 */     this.txtSumEnhCrt.setMinimumSize(new Dimension(40, 20));
/*  6218 */     this.txtSumEnhCrt.setPreferredSize(new Dimension(40, 20));
/*  6219 */     gridBagConstraints = new GridBagConstraints();
/*  6220 */     gridBagConstraints.gridx = 2;
/*  6221 */     gridBagConstraints.gridy = 6;
/*  6222 */     this.pnlBasicSummary.add(this.txtSumEnhCrt, gridBagConstraints);
/*       */     
/*  6224 */     this.lblSumHeatSinks.setText("Heat Sinks:");
/*  6225 */     gridBagConstraints = new GridBagConstraints();
/*  6226 */     gridBagConstraints.gridx = 0;
/*  6227 */     gridBagConstraints.gridy = 5;
/*  6228 */     gridBagConstraints.anchor = 13;
/*  6229 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6230 */     this.pnlBasicSummary.add(this.lblSumHeatSinks, gridBagConstraints);
/*       */     
/*  6232 */     this.txtSumHSTon.setHorizontalAlignment(11);
/*  6233 */     this.txtSumHSTon.setText("000.00");
/*  6234 */     this.txtSumHSTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6235 */     this.txtSumHSTon.setEnabled(false);
/*  6236 */     this.txtSumHSTon.setMaximumSize(new Dimension(50, 20));
/*  6237 */     this.txtSumHSTon.setMinimumSize(new Dimension(50, 20));
/*  6238 */     this.txtSumHSTon.setPreferredSize(new Dimension(50, 20));
/*  6239 */     gridBagConstraints = new GridBagConstraints();
/*  6240 */     gridBagConstraints.gridx = 1;
/*  6241 */     gridBagConstraints.gridy = 5;
/*  6242 */     this.pnlBasicSummary.add(this.txtSumHSTon, gridBagConstraints);
/*       */     
/*  6244 */     this.txtSumHSCrt.setHorizontalAlignment(11);
/*  6245 */     this.txtSumHSCrt.setText("00");
/*  6246 */     this.txtSumHSCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6247 */     this.txtSumHSCrt.setEnabled(false);
/*  6248 */     this.txtSumHSCrt.setMaximumSize(new Dimension(40, 20));
/*  6249 */     this.txtSumHSCrt.setMinimumSize(new Dimension(40, 20));
/*  6250 */     this.txtSumHSCrt.setPreferredSize(new Dimension(40, 20));
/*  6251 */     gridBagConstraints = new GridBagConstraints();
/*  6252 */     gridBagConstraints.gridx = 2;
/*  6253 */     gridBagConstraints.gridy = 5;
/*  6254 */     this.pnlBasicSummary.add(this.txtSumHSCrt, gridBagConstraints);
/*       */     
/*  6256 */     this.lblSumJJ.setText("Jump Jets:");
/*  6257 */     gridBagConstraints = new GridBagConstraints();
/*  6258 */     gridBagConstraints.gridx = 0;
/*  6259 */     gridBagConstraints.gridy = 7;
/*  6260 */     gridBagConstraints.anchor = 13;
/*  6261 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6262 */     this.pnlBasicSummary.add(this.lblSumJJ, gridBagConstraints);
/*       */     
/*  6264 */     this.txtSumJJTon.setHorizontalAlignment(11);
/*  6265 */     this.txtSumJJTon.setText("000.00");
/*  6266 */     this.txtSumJJTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6267 */     this.txtSumJJTon.setEnabled(false);
/*  6268 */     this.txtSumJJTon.setMaximumSize(new Dimension(50, 20));
/*  6269 */     this.txtSumJJTon.setMinimumSize(new Dimension(50, 20));
/*  6270 */     this.txtSumJJTon.setPreferredSize(new Dimension(50, 20));
/*  6271 */     gridBagConstraints = new GridBagConstraints();
/*  6272 */     gridBagConstraints.gridx = 1;
/*  6273 */     gridBagConstraints.gridy = 7;
/*  6274 */     this.pnlBasicSummary.add(this.txtSumJJTon, gridBagConstraints);
/*       */     
/*  6276 */     this.txtSumJJCrt.setHorizontalAlignment(11);
/*  6277 */     this.txtSumJJCrt.setText("00");
/*  6278 */     this.txtSumJJCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6279 */     this.txtSumJJCrt.setEnabled(false);
/*  6280 */     this.txtSumJJCrt.setMaximumSize(new Dimension(40, 20));
/*  6281 */     this.txtSumJJCrt.setMinimumSize(new Dimension(40, 20));
/*  6282 */     this.txtSumJJCrt.setPreferredSize(new Dimension(40, 20));
/*  6283 */     gridBagConstraints = new GridBagConstraints();
/*  6284 */     gridBagConstraints.gridx = 2;
/*  6285 */     gridBagConstraints.gridy = 7;
/*  6286 */     this.pnlBasicSummary.add(this.txtSumJJCrt, gridBagConstraints);
/*       */     
/*  6288 */     this.txtSumIntACode.setHorizontalAlignment(0);
/*  6289 */     this.txtSumIntACode.setText("A/C-E-D");
/*  6290 */     this.txtSumIntACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6291 */     this.txtSumIntACode.setEnabled(false);
/*  6292 */     this.txtSumIntACode.setMaximumSize(new Dimension(65, 20));
/*  6293 */     this.txtSumIntACode.setMinimumSize(new Dimension(65, 20));
/*  6294 */     this.txtSumIntACode.setPreferredSize(new Dimension(65, 20));
/*  6295 */     gridBagConstraints = new GridBagConstraints();
/*  6296 */     gridBagConstraints.gridx = 3;
/*  6297 */     gridBagConstraints.gridy = 1;
/*  6298 */     gridBagConstraints.anchor = 17;
/*  6299 */     this.pnlBasicSummary.add(this.txtSumIntACode, gridBagConstraints);
/*       */     
/*  6301 */     this.txtSumEngACode.setHorizontalAlignment(0);
/*  6302 */     this.txtSumEngACode.setText("C-E-D");
/*  6303 */     this.txtSumEngACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6304 */     this.txtSumEngACode.setEnabled(false);
/*  6305 */     this.txtSumEngACode.setMaximumSize(new Dimension(65, 20));
/*  6306 */     this.txtSumEngACode.setMinimumSize(new Dimension(65, 20));
/*  6307 */     this.txtSumEngACode.setPreferredSize(new Dimension(65, 20));
/*  6308 */     gridBagConstraints = new GridBagConstraints();
/*  6309 */     gridBagConstraints.gridx = 3;
/*  6310 */     gridBagConstraints.gridy = 2;
/*  6311 */     gridBagConstraints.anchor = 17;
/*  6312 */     this.pnlBasicSummary.add(this.txtSumEngACode, gridBagConstraints);
/*       */     
/*  6314 */     this.txtSumGyrACode.setHorizontalAlignment(0);
/*  6315 */     this.txtSumGyrACode.setText("C-E-D");
/*  6316 */     this.txtSumGyrACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6317 */     this.txtSumGyrACode.setEnabled(false);
/*  6318 */     this.txtSumGyrACode.setMaximumSize(new Dimension(65, 20));
/*  6319 */     this.txtSumGyrACode.setMinimumSize(new Dimension(65, 20));
/*  6320 */     this.txtSumGyrACode.setPreferredSize(new Dimension(65, 20));
/*  6321 */     gridBagConstraints = new GridBagConstraints();
/*  6322 */     gridBagConstraints.gridx = 3;
/*  6323 */     gridBagConstraints.gridy = 3;
/*  6324 */     gridBagConstraints.anchor = 17;
/*  6325 */     this.pnlBasicSummary.add(this.txtSumGyrACode, gridBagConstraints);
/*       */     
/*  6327 */     this.txtSumCocACode.setHorizontalAlignment(0);
/*  6328 */     this.txtSumCocACode.setText("C-E-D");
/*  6329 */     this.txtSumCocACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6330 */     this.txtSumCocACode.setEnabled(false);
/*  6331 */     this.txtSumCocACode.setMaximumSize(new Dimension(65, 20));
/*  6332 */     this.txtSumCocACode.setMinimumSize(new Dimension(65, 20));
/*  6333 */     this.txtSumCocACode.setPreferredSize(new Dimension(65, 20));
/*  6334 */     gridBagConstraints = new GridBagConstraints();
/*  6335 */     gridBagConstraints.gridx = 3;
/*  6336 */     gridBagConstraints.gridy = 4;
/*  6337 */     gridBagConstraints.anchor = 17;
/*  6338 */     this.pnlBasicSummary.add(this.txtSumCocACode, gridBagConstraints);
/*       */     
/*  6340 */     this.txtSumHSACode.setHorizontalAlignment(0);
/*  6341 */     this.txtSumHSACode.setText("C-E-D");
/*  6342 */     this.txtSumHSACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6343 */     this.txtSumHSACode.setEnabled(false);
/*  6344 */     this.txtSumHSACode.setMaximumSize(new Dimension(65, 20));
/*  6345 */     this.txtSumHSACode.setMinimumSize(new Dimension(65, 20));
/*  6346 */     this.txtSumHSACode.setPreferredSize(new Dimension(65, 20));
/*  6347 */     gridBagConstraints = new GridBagConstraints();
/*  6348 */     gridBagConstraints.gridx = 3;
/*  6349 */     gridBagConstraints.gridy = 5;
/*  6350 */     gridBagConstraints.anchor = 17;
/*  6351 */     this.pnlBasicSummary.add(this.txtSumHSACode, gridBagConstraints);
/*       */     
/*  6353 */     this.txtSumEnhACode.setHorizontalAlignment(0);
/*  6354 */     this.txtSumEnhACode.setText("C-E-D");
/*  6355 */     this.txtSumEnhACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6356 */     this.txtSumEnhACode.setEnabled(false);
/*  6357 */     this.txtSumEnhACode.setMaximumSize(new Dimension(65, 20));
/*  6358 */     this.txtSumEnhACode.setMinimumSize(new Dimension(65, 20));
/*  6359 */     this.txtSumEnhACode.setPreferredSize(new Dimension(65, 20));
/*  6360 */     gridBagConstraints = new GridBagConstraints();
/*  6361 */     gridBagConstraints.gridx = 3;
/*  6362 */     gridBagConstraints.gridy = 6;
/*  6363 */     gridBagConstraints.anchor = 17;
/*  6364 */     this.pnlBasicSummary.add(this.txtSumEnhACode, gridBagConstraints);
/*       */     
/*  6366 */     this.txtSumJJACode.setHorizontalAlignment(0);
/*  6367 */     this.txtSumJJACode.setText("C-E-D");
/*  6368 */     this.txtSumJJACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6369 */     this.txtSumJJACode.setEnabled(false);
/*  6370 */     this.txtSumJJACode.setMaximumSize(new Dimension(65, 20));
/*  6371 */     this.txtSumJJACode.setMinimumSize(new Dimension(65, 20));
/*  6372 */     this.txtSumJJACode.setPreferredSize(new Dimension(65, 20));
/*  6373 */     gridBagConstraints = new GridBagConstraints();
/*  6374 */     gridBagConstraints.gridx = 3;
/*  6375 */     gridBagConstraints.gridy = 7;
/*  6376 */     gridBagConstraints.anchor = 17;
/*  6377 */     this.pnlBasicSummary.add(this.txtSumJJACode, gridBagConstraints);
/*       */     
/*  6379 */     this.lblSumHeadAvailable.setText("Availability");
/*  6380 */     this.pnlBasicSummary.add(this.lblSumHeadAvailable, new GridBagConstraints());
/*       */     
/*  6382 */     this.txtSumPAmpsTon.setHorizontalAlignment(11);
/*  6383 */     this.txtSumPAmpsTon.setText("000.00");
/*  6384 */     this.txtSumPAmpsTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6385 */     this.txtSumPAmpsTon.setEnabled(false);
/*  6386 */     this.txtSumPAmpsTon.setMaximumSize(new Dimension(50, 20));
/*  6387 */     this.txtSumPAmpsTon.setMinimumSize(new Dimension(50, 20));
/*  6388 */     this.txtSumPAmpsTon.setPreferredSize(new Dimension(50, 20));
/*  6389 */     gridBagConstraints = new GridBagConstraints();
/*  6390 */     gridBagConstraints.gridx = 1;
/*  6391 */     gridBagConstraints.gridy = 8;
/*  6392 */     this.pnlBasicSummary.add(this.txtSumPAmpsTon, gridBagConstraints);
/*       */     
/*  6394 */     this.txtSumPAmpsACode.setHorizontalAlignment(0);
/*  6395 */     this.txtSumPAmpsACode.setText("C-E-D");
/*  6396 */     this.txtSumPAmpsACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6397 */     this.txtSumPAmpsACode.setEnabled(false);
/*  6398 */     this.txtSumPAmpsACode.setMaximumSize(new Dimension(65, 20));
/*  6399 */     this.txtSumPAmpsACode.setMinimumSize(new Dimension(65, 20));
/*  6400 */     this.txtSumPAmpsACode.setPreferredSize(new Dimension(65, 20));
/*  6401 */     gridBagConstraints = new GridBagConstraints();
/*  6402 */     gridBagConstraints.gridx = 3;
/*  6403 */     gridBagConstraints.gridy = 8;
/*  6404 */     gridBagConstraints.anchor = 17;
/*  6405 */     this.pnlBasicSummary.add(this.txtSumPAmpsACode, gridBagConstraints);
/*       */     
/*  6407 */     this.lblSumPAmps.setText("Power Amplifiers:");
/*  6408 */     gridBagConstraints = new GridBagConstraints();
/*  6409 */     gridBagConstraints.gridx = 0;
/*  6410 */     gridBagConstraints.gridy = 8;
/*  6411 */     gridBagConstraints.anchor = 13;
/*  6412 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6413 */     this.pnlBasicSummary.add(this.lblSumPAmps, gridBagConstraints);
/*       */     
/*  6415 */     gridBagConstraints = new GridBagConstraints();
/*  6416 */     gridBagConstraints.gridx = 1;
/*  6417 */     gridBagConstraints.gridy = 3;
/*  6418 */     gridBagConstraints.fill = 1;
/*  6419 */     this.pnlBasicSetup.add(this.pnlBasicSummary, gridBagConstraints);
/*       */     
/*  6421 */     this.jPanel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Experimental Equipment"));
/*  6422 */     this.jPanel4.setLayout(new GridBagLayout());
/*       */     
/*  6424 */     this.chkCLPS.setText("Chameleon LPS");
/*  6425 */     this.chkCLPS.setEnabled(false);
/*  6426 */     this.chkCLPS.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6428 */         frmMainWide.this.chkCLPSActionPerformed(evt);
/*       */       }
/*  6430 */     });
/*  6431 */     gridBagConstraints = new GridBagConstraints();
/*  6432 */     gridBagConstraints.gridx = 0;
/*  6433 */     gridBagConstraints.gridy = 1;
/*  6434 */     gridBagConstraints.gridwidth = 3;
/*  6435 */     gridBagConstraints.anchor = 17;
/*  6436 */     this.jPanel4.add(this.chkCLPS, gridBagConstraints);
/*       */     
/*  6438 */     this.chkNullSig.setText("Null Signature System");
/*  6439 */     this.chkNullSig.setEnabled(false);
/*  6440 */     this.chkNullSig.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6442 */         frmMainWide.this.chkNullSigActionPerformed(evt);
/*       */       }
/*  6444 */     });
/*  6445 */     gridBagConstraints = new GridBagConstraints();
/*  6446 */     gridBagConstraints.gridx = 0;
/*  6447 */     gridBagConstraints.gridy = 2;
/*  6448 */     gridBagConstraints.gridwidth = 3;
/*  6449 */     gridBagConstraints.anchor = 17;
/*  6450 */     this.jPanel4.add(this.chkNullSig, gridBagConstraints);
/*       */     
/*  6452 */     this.chkBSPFD.setText("Blue Shield PFD");
/*  6453 */     this.chkBSPFD.setEnabled(false);
/*  6454 */     this.chkBSPFD.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6456 */         frmMainWide.this.chkBSPFDActionPerformed(evt);
/*       */       }
/*  6458 */     });
/*  6459 */     gridBagConstraints = new GridBagConstraints();
/*  6460 */     gridBagConstraints.gridx = 0;
/*  6461 */     gridBagConstraints.gridy = 3;
/*  6462 */     gridBagConstraints.gridwidth = 3;
/*  6463 */     gridBagConstraints.anchor = 17;
/*  6464 */     this.jPanel4.add(this.chkBSPFD, gridBagConstraints);
/*       */     
/*  6466 */     this.chkVoidSig.setText("Void Signature System");
/*  6467 */     this.chkVoidSig.setEnabled(false);
/*  6468 */     this.chkVoidSig.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6470 */         frmMainWide.this.chkVoidSigActionPerformed(evt);
/*       */       }
/*  6472 */     });
/*  6473 */     gridBagConstraints = new GridBagConstraints();
/*  6474 */     gridBagConstraints.gridx = 0;
/*  6475 */     gridBagConstraints.gridy = 4;
/*  6476 */     gridBagConstraints.gridwidth = 3;
/*  6477 */     gridBagConstraints.anchor = 17;
/*  6478 */     this.jPanel4.add(this.chkVoidSig, gridBagConstraints);
/*       */     
/*  6480 */     this.chkSupercharger.setText("Supercharger");
/*  6481 */     this.chkSupercharger.setEnabled(false);
/*  6482 */     this.chkSupercharger.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6484 */         frmMainWide.this.chkSuperchargerActionPerformed(evt);
/*       */       }
/*  6486 */     });
/*  6487 */     gridBagConstraints = new GridBagConstraints();
/*  6488 */     gridBagConstraints.gridx = 0;
/*  6489 */     gridBagConstraints.gridy = 5;
/*  6490 */     gridBagConstraints.gridwidth = 3;
/*  6491 */     gridBagConstraints.anchor = 17;
/*  6492 */     this.jPanel4.add(this.chkSupercharger, gridBagConstraints);
/*       */     
/*  6494 */     this.cmbSCLoc.setModel(new DefaultComboBoxModel(new String[] { "CT", "LT", "RT" }));
/*  6495 */     this.cmbSCLoc.setEnabled(false);
/*  6496 */     this.cmbSCLoc.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6498 */         frmMainWide.this.cmbSCLocActionPerformed(evt);
/*       */       }
/*  6500 */     });
/*  6501 */     gridBagConstraints = new GridBagConstraints();
/*  6502 */     gridBagConstraints.gridx = 2;
/*  6503 */     gridBagConstraints.gridy = 6;
/*  6504 */     gridBagConstraints.anchor = 17;
/*  6505 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  6506 */     this.jPanel4.add(this.cmbSCLoc, gridBagConstraints);
/*       */     
/*  6508 */     this.chkBoobyTrap.setText("Booby Trap");
/*  6509 */     this.chkBoobyTrap.setEnabled(false);
/*  6510 */     this.chkBoobyTrap.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6512 */         frmMainWide.this.chkBoobyTrapActionPerformed(evt);
/*       */       }
/*  6514 */     });
/*  6515 */     gridBagConstraints = new GridBagConstraints();
/*  6516 */     gridBagConstraints.gridx = 0;
/*  6517 */     gridBagConstraints.gridy = 7;
/*  6518 */     gridBagConstraints.gridwidth = 3;
/*  6519 */     gridBagConstraints.anchor = 17;
/*  6520 */     this.jPanel4.add(this.chkBoobyTrap, gridBagConstraints);
/*       */     
/*  6522 */     this.chkPartialWing.setText("Partial Wing");
/*  6523 */     this.chkPartialWing.setEnabled(false);
/*  6524 */     this.chkPartialWing.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6526 */         frmMainWide.this.chkPartialWingActionPerformed(evt);
/*       */       }
/*  6528 */     });
/*  6529 */     gridBagConstraints = new GridBagConstraints();
/*  6530 */     gridBagConstraints.gridx = 0;
/*  6531 */     gridBagConstraints.gridy = 8;
/*  6532 */     gridBagConstraints.gridwidth = 3;
/*  6533 */     gridBagConstraints.anchor = 17;
/*  6534 */     this.jPanel4.add(this.chkPartialWing, gridBagConstraints);
/*       */     
/*  6536 */     this.chkFHES.setText("Full-Head Ejection System");
/*  6537 */     this.chkFHES.setEnabled(false);
/*  6538 */     this.chkFHES.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6540 */         frmMainWide.this.chkFHESActionPerformed(evt);
/*       */       }
/*  6542 */     });
/*  6543 */     gridBagConstraints = new GridBagConstraints();
/*  6544 */     gridBagConstraints.gridx = 0;
/*  6545 */     gridBagConstraints.gridy = 0;
/*  6546 */     gridBagConstraints.gridwidth = 3;
/*  6547 */     gridBagConstraints.anchor = 17;
/*  6548 */     this.jPanel4.add(this.chkFHES, gridBagConstraints);
/*       */     
/*  6550 */     this.lblSupercharger.setText("Install in:");
/*  6551 */     this.lblSupercharger.setEnabled(false);
/*  6552 */     gridBagConstraints = new GridBagConstraints();
/*  6553 */     gridBagConstraints.gridx = 1;
/*  6554 */     gridBagConstraints.gridy = 6;
/*  6555 */     gridBagConstraints.fill = 2;
/*  6556 */     gridBagConstraints.anchor = 13;
/*  6557 */     this.jPanel4.add(this.lblSupercharger, gridBagConstraints);
/*       */     
/*  6559 */     this.jLabel57.setText("        ");
/*  6560 */     gridBagConstraints = new GridBagConstraints();
/*  6561 */     gridBagConstraints.gridx = 0;
/*  6562 */     gridBagConstraints.gridy = 6;
/*  6563 */     this.jPanel4.add(this.jLabel57, gridBagConstraints);
/*       */     
/*  6565 */     gridBagConstraints = new GridBagConstraints();
/*  6566 */     gridBagConstraints.gridx = 4;
/*  6567 */     gridBagConstraints.gridy = 3;
/*  6568 */     gridBagConstraints.fill = 1;
/*  6569 */     this.pnlBasicSetup.add(this.jPanel4, gridBagConstraints);
/*       */     
/*  6571 */     this.jPanel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Industrial Equipment"));
/*  6572 */     this.jPanel6.setLayout(new javax.swing.BoxLayout(this.jPanel6, 3));
/*       */     
/*  6574 */     this.chkEjectionSeat.setText("Ejection Seat");
/*  6575 */     this.chkEjectionSeat.setEnabled(false);
/*  6576 */     this.chkEjectionSeat.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6578 */         frmMainWide.this.chkEjectionSeatActionPerformed(evt);
/*       */       }
/*  6580 */     });
/*  6581 */     this.jPanel6.add(this.chkEjectionSeat);
/*       */     
/*  6583 */     this.chkEnviroSealing.setText("Environmental Sealing");
/*  6584 */     this.chkEnviroSealing.setEnabled(false);
/*  6585 */     this.chkEnviroSealing.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6587 */         frmMainWide.this.chkEnviroSealingActionPerformed(evt);
/*       */       }
/*  6589 */     });
/*  6590 */     this.jPanel6.add(this.chkEnviroSealing);
/*       */     
/*  6592 */     this.chkTracks.setText("Tracks");
/*  6593 */     this.chkTracks.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6595 */         frmMainWide.this.chkTracksActionPerformed(evt);
/*       */       }
/*  6597 */     });
/*  6598 */     this.jPanel6.add(this.chkTracks);
/*       */     
/*  6600 */     gridBagConstraints = new GridBagConstraints();
/*  6601 */     gridBagConstraints.gridx = 4;
/*  6602 */     gridBagConstraints.gridy = 1;
/*  6603 */     gridBagConstraints.gridheight = 2;
/*  6604 */     gridBagConstraints.fill = 2;
/*  6605 */     gridBagConstraints.anchor = 17;
/*  6606 */     this.pnlBasicSetup.add(this.jPanel6, gridBagConstraints);
/*       */     
/*  6608 */     this.pnlFrontArmor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Forward Armor"));
/*  6609 */     this.pnlFrontArmor.setLayout(new GridBagLayout());
/*       */     
/*  6611 */     this.pnlRLArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RL", 2, 0));
/*  6612 */     this.pnlRLArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6614 */     this.lblRLHeader.setText("Internal");
/*  6615 */     this.pnlRLArmorBox.add(this.lblRLHeader, new GridBagConstraints());
/*       */     
/*  6617 */     this.lblRLIntPts.setHorizontalAlignment(0);
/*  6618 */     this.lblRLIntPts.setText("00");
/*  6619 */     this.lblRLIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6620 */     this.lblRLIntPts.setMaximumSize(new Dimension(45, 20));
/*  6621 */     this.lblRLIntPts.setMinimumSize(new Dimension(45, 20));
/*  6622 */     this.lblRLIntPts.setPreferredSize(new Dimension(45, 20));
/*  6623 */     gridBagConstraints = new GridBagConstraints();
/*  6624 */     gridBagConstraints.gridx = 0;
/*  6625 */     gridBagConstraints.gridy = 1;
/*  6626 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6627 */     this.pnlRLArmorBox.add(this.lblRLIntPts, gridBagConstraints);
/*       */     
/*  6629 */     this.lblRLArmorHeader.setText("Armor");
/*  6630 */     gridBagConstraints = new GridBagConstraints();
/*  6631 */     gridBagConstraints.gridx = 0;
/*  6632 */     gridBagConstraints.gridy = 2;
/*  6633 */     this.pnlRLArmorBox.add(this.lblRLArmorHeader, gridBagConstraints);
/*       */     
/*  6635 */     this.spnRLArmor.setMaximumSize(new Dimension(45, 20));
/*  6636 */     this.spnRLArmor.setMinimumSize(new Dimension(45, 20));
/*  6637 */     this.spnRLArmor.setPreferredSize(new Dimension(45, 20));
/*  6638 */     this.spnRLArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6640 */         frmMainWide.this.spnRLArmorStateChanged(evt);
/*       */       }
/*  6642 */     });
/*  6643 */     gridBagConstraints = new GridBagConstraints();
/*  6644 */     gridBagConstraints.gridx = 0;
/*  6645 */     gridBagConstraints.gridy = 3;
/*  6646 */     this.pnlRLArmorBox.add(this.spnRLArmor, gridBagConstraints);
/*       */     
/*  6648 */     gridBagConstraints = new GridBagConstraints();
/*  6649 */     gridBagConstraints.gridx = 3;
/*  6650 */     gridBagConstraints.gridy = 2;
/*  6651 */     this.pnlFrontArmor.add(this.pnlRLArmorBox, gridBagConstraints);
/*       */     
/*  6653 */     this.pnlLLArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LL", 2, 0));
/*  6654 */     this.pnlLLArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6656 */     this.lblLLHeader.setText("Internal");
/*  6657 */     this.pnlLLArmorBox.add(this.lblLLHeader, new GridBagConstraints());
/*       */     
/*  6659 */     this.lblLLIntPts.setHorizontalAlignment(0);
/*  6660 */     this.lblLLIntPts.setText("00");
/*  6661 */     this.lblLLIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6662 */     this.lblLLIntPts.setMaximumSize(new Dimension(45, 20));
/*  6663 */     this.lblLLIntPts.setMinimumSize(new Dimension(45, 20));
/*  6664 */     this.lblLLIntPts.setPreferredSize(new Dimension(45, 20));
/*  6665 */     gridBagConstraints = new GridBagConstraints();
/*  6666 */     gridBagConstraints.gridx = 0;
/*  6667 */     gridBagConstraints.gridy = 1;
/*  6668 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6669 */     this.pnlLLArmorBox.add(this.lblLLIntPts, gridBagConstraints);
/*       */     
/*  6671 */     this.lblLLArmorHeader.setText("Armor");
/*  6672 */     gridBagConstraints = new GridBagConstraints();
/*  6673 */     gridBagConstraints.gridx = 0;
/*  6674 */     gridBagConstraints.gridy = 2;
/*  6675 */     this.pnlLLArmorBox.add(this.lblLLArmorHeader, gridBagConstraints);
/*       */     
/*  6677 */     this.spnLLArmor.setMaximumSize(new Dimension(45, 20));
/*  6678 */     this.spnLLArmor.setMinimumSize(new Dimension(45, 20));
/*  6679 */     this.spnLLArmor.setPreferredSize(new Dimension(45, 20));
/*  6680 */     this.spnLLArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6682 */         frmMainWide.this.spnLLArmorStateChanged(evt);
/*       */       }
/*  6684 */     });
/*  6685 */     gridBagConstraints = new GridBagConstraints();
/*  6686 */     gridBagConstraints.gridx = 0;
/*  6687 */     gridBagConstraints.gridy = 3;
/*  6688 */     this.pnlLLArmorBox.add(this.spnLLArmor, gridBagConstraints);
/*       */     
/*  6690 */     gridBagConstraints = new GridBagConstraints();
/*  6691 */     gridBagConstraints.gridx = 1;
/*  6692 */     gridBagConstraints.gridy = 2;
/*  6693 */     this.pnlFrontArmor.add(this.pnlLLArmorBox, gridBagConstraints);
/*       */     
/*  6695 */     this.pnlRAArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RA", 2, 0));
/*  6696 */     this.pnlRAArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6698 */     this.lblRAHeader.setText("Internal");
/*  6699 */     this.pnlRAArmorBox.add(this.lblRAHeader, new GridBagConstraints());
/*       */     
/*  6701 */     this.lblRAIntPts.setHorizontalAlignment(0);
/*  6702 */     this.lblRAIntPts.setText("00");
/*  6703 */     this.lblRAIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6704 */     this.lblRAIntPts.setMaximumSize(new Dimension(45, 20));
/*  6705 */     this.lblRAIntPts.setMinimumSize(new Dimension(45, 20));
/*  6706 */     this.lblRAIntPts.setPreferredSize(new Dimension(45, 20));
/*  6707 */     gridBagConstraints = new GridBagConstraints();
/*  6708 */     gridBagConstraints.gridx = 0;
/*  6709 */     gridBagConstraints.gridy = 1;
/*  6710 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6711 */     this.pnlRAArmorBox.add(this.lblRAIntPts, gridBagConstraints);
/*       */     
/*  6713 */     this.lblRAArmorHeader.setText("Armor");
/*  6714 */     gridBagConstraints = new GridBagConstraints();
/*  6715 */     gridBagConstraints.gridx = 0;
/*  6716 */     gridBagConstraints.gridy = 2;
/*  6717 */     this.pnlRAArmorBox.add(this.lblRAArmorHeader, gridBagConstraints);
/*       */     
/*  6719 */     this.spnRAArmor.setModel(new SpinnerNumberModel(0, 0, 100, 1));
/*  6720 */     this.spnRAArmor.setMaximumSize(new Dimension(45, 20));
/*  6721 */     this.spnRAArmor.setMinimumSize(new Dimension(45, 20));
/*  6722 */     this.spnRAArmor.setPreferredSize(new Dimension(45, 20));
/*  6723 */     this.spnRAArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6725 */         frmMainWide.this.spnRAArmorStateChanged(evt);
/*       */       }
/*  6727 */     });
/*  6728 */     gridBagConstraints = new GridBagConstraints();
/*  6729 */     gridBagConstraints.gridx = 0;
/*  6730 */     gridBagConstraints.gridy = 3;
/*  6731 */     this.pnlRAArmorBox.add(this.spnRAArmor, gridBagConstraints);
/*       */     
/*  6733 */     gridBagConstraints = new GridBagConstraints();
/*  6734 */     gridBagConstraints.gridx = 4;
/*  6735 */     gridBagConstraints.gridy = 1;
/*  6736 */     this.pnlFrontArmor.add(this.pnlRAArmorBox, gridBagConstraints);
/*       */     
/*  6738 */     this.pnlHDArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Head", 2, 0));
/*  6739 */     this.pnlHDArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6741 */     this.lblHDHeader.setText("Internal");
/*  6742 */     this.pnlHDArmorBox.add(this.lblHDHeader, new GridBagConstraints());
/*       */     
/*  6744 */     this.lblHDIntPts.setHorizontalAlignment(0);
/*  6745 */     this.lblHDIntPts.setText("00");
/*  6746 */     this.lblHDIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6747 */     this.lblHDIntPts.setMaximumSize(new Dimension(45, 20));
/*  6748 */     this.lblHDIntPts.setMinimumSize(new Dimension(45, 20));
/*  6749 */     this.lblHDIntPts.setPreferredSize(new Dimension(45, 20));
/*  6750 */     gridBagConstraints = new GridBagConstraints();
/*  6751 */     gridBagConstraints.gridx = 0;
/*  6752 */     gridBagConstraints.gridy = 1;
/*  6753 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6754 */     this.pnlHDArmorBox.add(this.lblHDIntPts, gridBagConstraints);
/*       */     
/*  6756 */     this.lblHDArmorHeader.setText("Armor");
/*  6757 */     gridBagConstraints = new GridBagConstraints();
/*  6758 */     gridBagConstraints.gridx = 0;
/*  6759 */     gridBagConstraints.gridy = 2;
/*  6760 */     this.pnlHDArmorBox.add(this.lblHDArmorHeader, gridBagConstraints);
/*       */     
/*  6762 */     this.spnHDArmor.setModel(new SpinnerNumberModel(0, 0, 9, 1));
/*  6763 */     this.spnHDArmor.setMaximumSize(new Dimension(45, 20));
/*  6764 */     this.spnHDArmor.setMinimumSize(new Dimension(45, 20));
/*  6765 */     this.spnHDArmor.setPreferredSize(new Dimension(45, 20));
/*  6766 */     this.spnHDArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6768 */         frmMainWide.this.spnHDArmorStateChanged(evt);
/*       */       }
/*  6770 */     });
/*  6771 */     gridBagConstraints = new GridBagConstraints();
/*  6772 */     gridBagConstraints.gridx = 0;
/*  6773 */     gridBagConstraints.gridy = 3;
/*  6774 */     this.pnlHDArmorBox.add(this.spnHDArmor, gridBagConstraints);
/*       */     
/*  6776 */     gridBagConstraints = new GridBagConstraints();
/*  6777 */     gridBagConstraints.gridx = 2;
/*  6778 */     gridBagConstraints.gridy = 0;
/*  6779 */     this.pnlFrontArmor.add(this.pnlHDArmorBox, gridBagConstraints);
/*       */     
/*  6781 */     this.pnlCTArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "CT", 2, 0));
/*  6782 */     this.pnlCTArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6784 */     this.lblCTHeader.setText("Internal");
/*  6785 */     this.pnlCTArmorBox.add(this.lblCTHeader, new GridBagConstraints());
/*       */     
/*  6787 */     this.lblCTIntPts.setHorizontalAlignment(0);
/*  6788 */     this.lblCTIntPts.setText("00");
/*  6789 */     this.lblCTIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6790 */     this.lblCTIntPts.setMaximumSize(new Dimension(45, 20));
/*  6791 */     this.lblCTIntPts.setMinimumSize(new Dimension(45, 20));
/*  6792 */     this.lblCTIntPts.setPreferredSize(new Dimension(45, 20));
/*  6793 */     gridBagConstraints = new GridBagConstraints();
/*  6794 */     gridBagConstraints.gridx = 0;
/*  6795 */     gridBagConstraints.gridy = 1;
/*  6796 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6797 */     this.pnlCTArmorBox.add(this.lblCTIntPts, gridBagConstraints);
/*       */     
/*  6799 */     this.lblCTArmorHeader.setText("Armor");
/*  6800 */     gridBagConstraints = new GridBagConstraints();
/*  6801 */     gridBagConstraints.gridx = 0;
/*  6802 */     gridBagConstraints.gridy = 2;
/*  6803 */     this.pnlCTArmorBox.add(this.lblCTArmorHeader, gridBagConstraints);
/*       */     
/*  6805 */     this.spnCTArmor.setMaximumSize(new Dimension(45, 20));
/*  6806 */     this.spnCTArmor.setMinimumSize(new Dimension(45, 20));
/*  6807 */     this.spnCTArmor.setPreferredSize(new Dimension(45, 20));
/*  6808 */     this.spnCTArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6810 */         frmMainWide.this.spnCTArmorStateChanged(evt);
/*       */       }
/*  6812 */     });
/*  6813 */     gridBagConstraints = new GridBagConstraints();
/*  6814 */     gridBagConstraints.gridx = 0;
/*  6815 */     gridBagConstraints.gridy = 3;
/*  6816 */     this.pnlCTArmorBox.add(this.spnCTArmor, gridBagConstraints);
/*       */     
/*  6818 */     gridBagConstraints = new GridBagConstraints();
/*  6819 */     gridBagConstraints.gridx = 2;
/*  6820 */     gridBagConstraints.gridy = 1;
/*  6821 */     gridBagConstraints.gridheight = 2;
/*  6822 */     gridBagConstraints.anchor = 11;
/*  6823 */     this.pnlFrontArmor.add(this.pnlCTArmorBox, gridBagConstraints);
/*       */     
/*  6825 */     this.pnlLTArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LT", 2, 0));
/*  6826 */     this.pnlLTArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6828 */     this.lblLTHeader.setText("Internal");
/*  6829 */     this.pnlLTArmorBox.add(this.lblLTHeader, new GridBagConstraints());
/*       */     
/*  6831 */     this.lblLTIntPts.setHorizontalAlignment(0);
/*  6832 */     this.lblLTIntPts.setText("00");
/*  6833 */     this.lblLTIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6834 */     this.lblLTIntPts.setMaximumSize(new Dimension(45, 20));
/*  6835 */     this.lblLTIntPts.setMinimumSize(new Dimension(45, 20));
/*  6836 */     this.lblLTIntPts.setPreferredSize(new Dimension(45, 20));
/*  6837 */     gridBagConstraints = new GridBagConstraints();
/*  6838 */     gridBagConstraints.gridx = 0;
/*  6839 */     gridBagConstraints.gridy = 1;
/*  6840 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6841 */     this.pnlLTArmorBox.add(this.lblLTIntPts, gridBagConstraints);
/*       */     
/*  6843 */     this.lblLTArmorHeader.setText("Armor");
/*  6844 */     gridBagConstraints = new GridBagConstraints();
/*  6845 */     gridBagConstraints.gridx = 0;
/*  6846 */     gridBagConstraints.gridy = 2;
/*  6847 */     this.pnlLTArmorBox.add(this.lblLTArmorHeader, gridBagConstraints);
/*       */     
/*  6849 */     this.spnLTArmor.setMaximumSize(new Dimension(45, 20));
/*  6850 */     this.spnLTArmor.setMinimumSize(new Dimension(45, 20));
/*  6851 */     this.spnLTArmor.setPreferredSize(new Dimension(45, 20));
/*  6852 */     this.spnLTArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6854 */         frmMainWide.this.spnLTArmorStateChanged(evt);
/*       */       }
/*  6856 */     });
/*  6857 */     gridBagConstraints = new GridBagConstraints();
/*  6858 */     gridBagConstraints.gridx = 0;
/*  6859 */     gridBagConstraints.gridy = 3;
/*  6860 */     this.pnlLTArmorBox.add(this.spnLTArmor, gridBagConstraints);
/*       */     
/*  6862 */     gridBagConstraints = new GridBagConstraints();
/*  6863 */     gridBagConstraints.gridx = 1;
/*  6864 */     gridBagConstraints.gridy = 0;
/*  6865 */     gridBagConstraints.gridheight = 2;
/*  6866 */     this.pnlFrontArmor.add(this.pnlLTArmorBox, gridBagConstraints);
/*       */     
/*  6868 */     this.pnlRTArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RT", 2, 0));
/*  6869 */     this.pnlRTArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6871 */     this.lblRTHeader.setText("Internal");
/*  6872 */     this.pnlRTArmorBox.add(this.lblRTHeader, new GridBagConstraints());
/*       */     
/*  6874 */     this.lblRTIntPts.setHorizontalAlignment(0);
/*  6875 */     this.lblRTIntPts.setText("00");
/*  6876 */     this.lblRTIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6877 */     this.lblRTIntPts.setMaximumSize(new Dimension(45, 20));
/*  6878 */     this.lblRTIntPts.setMinimumSize(new Dimension(45, 20));
/*  6879 */     this.lblRTIntPts.setPreferredSize(new Dimension(45, 20));
/*  6880 */     gridBagConstraints = new GridBagConstraints();
/*  6881 */     gridBagConstraints.gridx = 0;
/*  6882 */     gridBagConstraints.gridy = 1;
/*  6883 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6884 */     this.pnlRTArmorBox.add(this.lblRTIntPts, gridBagConstraints);
/*       */     
/*  6886 */     this.lblRTArmorHeader.setText("Armor");
/*  6887 */     gridBagConstraints = new GridBagConstraints();
/*  6888 */     gridBagConstraints.gridx = 0;
/*  6889 */     gridBagConstraints.gridy = 2;
/*  6890 */     this.pnlRTArmorBox.add(this.lblRTArmorHeader, gridBagConstraints);
/*       */     
/*  6892 */     this.spnRTArmor.setMaximumSize(new Dimension(45, 20));
/*  6893 */     this.spnRTArmor.setMinimumSize(new Dimension(45, 20));
/*  6894 */     this.spnRTArmor.setPreferredSize(new Dimension(45, 20));
/*  6895 */     this.spnRTArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6897 */         frmMainWide.this.spnRTArmorStateChanged(evt);
/*       */       }
/*  6899 */     });
/*  6900 */     gridBagConstraints = new GridBagConstraints();
/*  6901 */     gridBagConstraints.gridx = 0;
/*  6902 */     gridBagConstraints.gridy = 3;
/*  6903 */     this.pnlRTArmorBox.add(this.spnRTArmor, gridBagConstraints);
/*       */     
/*  6905 */     gridBagConstraints = new GridBagConstraints();
/*  6906 */     gridBagConstraints.gridx = 3;
/*  6907 */     gridBagConstraints.gridy = 0;
/*  6908 */     gridBagConstraints.gridheight = 2;
/*  6909 */     this.pnlFrontArmor.add(this.pnlRTArmorBox, gridBagConstraints);
/*       */     
/*  6911 */     this.pnlLAArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LA", 2, 0));
/*  6912 */     this.pnlLAArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6914 */     this.lblLAHeader.setText("Internal");
/*  6915 */     this.pnlLAArmorBox.add(this.lblLAHeader, new GridBagConstraints());
/*       */     
/*  6917 */     this.lblLAIntPts.setHorizontalAlignment(0);
/*  6918 */     this.lblLAIntPts.setText("00");
/*  6919 */     this.lblLAIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6920 */     this.lblLAIntPts.setMaximumSize(new Dimension(45, 20));
/*  6921 */     this.lblLAIntPts.setMinimumSize(new Dimension(45, 20));
/*  6922 */     this.lblLAIntPts.setPreferredSize(new Dimension(45, 20));
/*  6923 */     gridBagConstraints = new GridBagConstraints();
/*  6924 */     gridBagConstraints.gridx = 0;
/*  6925 */     gridBagConstraints.gridy = 1;
/*  6926 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6927 */     this.pnlLAArmorBox.add(this.lblLAIntPts, gridBagConstraints);
/*       */     
/*  6929 */     this.lblLAArmorHeader.setText("Armor");
/*  6930 */     gridBagConstraints = new GridBagConstraints();
/*  6931 */     gridBagConstraints.gridx = 0;
/*  6932 */     gridBagConstraints.gridy = 2;
/*  6933 */     this.pnlLAArmorBox.add(this.lblLAArmorHeader, gridBagConstraints);
/*       */     
/*  6935 */     this.spnLAArmor.setMaximumSize(new Dimension(45, 20));
/*  6936 */     this.spnLAArmor.setMinimumSize(new Dimension(45, 20));
/*  6937 */     this.spnLAArmor.setPreferredSize(new Dimension(45, 20));
/*  6938 */     this.spnLAArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6940 */         frmMainWide.this.spnLAArmorStateChanged(evt);
/*       */       }
/*  6942 */     });
/*  6943 */     gridBagConstraints = new GridBagConstraints();
/*  6944 */     gridBagConstraints.gridx = 0;
/*  6945 */     gridBagConstraints.gridy = 3;
/*  6946 */     this.pnlLAArmorBox.add(this.spnLAArmor, gridBagConstraints);
/*       */     
/*  6948 */     gridBagConstraints = new GridBagConstraints();
/*  6949 */     gridBagConstraints.gridx = 0;
/*  6950 */     gridBagConstraints.gridy = 1;
/*  6951 */     this.pnlFrontArmor.add(this.pnlLAArmorBox, gridBagConstraints);
/*       */     
/*  6953 */     this.pnlRearArmor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Rear Armor"));
/*  6954 */     this.pnlRearArmor.setLayout(new GridBagLayout());
/*       */     
/*  6956 */     this.pnlRTRArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RTR", 2, 0));
/*  6957 */     this.pnlRTRArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6959 */     this.lblRTRArmorHeader.setText("Armor");
/*  6960 */     this.pnlRTRArmorBox.add(this.lblRTRArmorHeader, new GridBagConstraints());
/*       */     
/*  6962 */     this.spnRTRArmor.setMaximumSize(new Dimension(45, 20));
/*  6963 */     this.spnRTRArmor.setMinimumSize(new Dimension(45, 20));
/*  6964 */     this.spnRTRArmor.setPreferredSize(new Dimension(45, 20));
/*  6965 */     this.spnRTRArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6967 */         frmMainWide.this.spnRTRArmorStateChanged(evt);
/*       */       }
/*  6969 */     });
/*  6970 */     gridBagConstraints = new GridBagConstraints();
/*  6971 */     gridBagConstraints.gridx = 0;
/*  6972 */     gridBagConstraints.gridy = 1;
/*  6973 */     this.pnlRTRArmorBox.add(this.spnRTRArmor, gridBagConstraints);
/*       */     
/*  6975 */     gridBagConstraints = new GridBagConstraints();
/*  6976 */     gridBagConstraints.gridx = 2;
/*  6977 */     gridBagConstraints.gridy = 0;
/*  6978 */     this.pnlRearArmor.add(this.pnlRTRArmorBox, gridBagConstraints);
/*       */     
/*  6980 */     this.pnlCTRArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "CTR", 2, 0));
/*  6981 */     this.pnlCTRArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6983 */     this.lblCTRArmorHeader.setText("Armor");
/*  6984 */     this.pnlCTRArmorBox.add(this.lblCTRArmorHeader, new GridBagConstraints());
/*       */     
/*  6986 */     this.spnCTRArmor.setMaximumSize(new Dimension(45, 20));
/*  6987 */     this.spnCTRArmor.setMinimumSize(new Dimension(45, 20));
/*  6988 */     this.spnCTRArmor.setPreferredSize(new Dimension(45, 20));
/*  6989 */     this.spnCTRArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6991 */         frmMainWide.this.spnCTRArmorStateChanged(evt);
/*       */       }
/*  6993 */     });
/*  6994 */     gridBagConstraints = new GridBagConstraints();
/*  6995 */     gridBagConstraints.gridx = 0;
/*  6996 */     gridBagConstraints.gridy = 1;
/*  6997 */     this.pnlCTRArmorBox.add(this.spnCTRArmor, gridBagConstraints);
/*       */     
/*  6999 */     gridBagConstraints = new GridBagConstraints();
/*  7000 */     gridBagConstraints.gridx = 1;
/*  7001 */     gridBagConstraints.gridy = 0;
/*  7002 */     this.pnlRearArmor.add(this.pnlCTRArmorBox, gridBagConstraints);
/*       */     
/*  7004 */     this.pnlLTRArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LTR", 2, 0));
/*  7005 */     this.pnlLTRArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  7007 */     this.lblLTRArmorHeader.setText("Armor");
/*  7008 */     this.pnlLTRArmorBox.add(this.lblLTRArmorHeader, new GridBagConstraints());
/*       */     
/*  7010 */     this.spnLTRArmor.setMaximumSize(new Dimension(45, 20));
/*  7011 */     this.spnLTRArmor.setMinimumSize(new Dimension(45, 20));
/*  7012 */     this.spnLTRArmor.setPreferredSize(new Dimension(45, 20));
/*  7013 */     this.spnLTRArmor.addChangeListener(new javax.swing.event.ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  7015 */         frmMainWide.this.spnLTRArmorStateChanged(evt);
/*       */       }
/*  7017 */     });
/*  7018 */     gridBagConstraints = new GridBagConstraints();
/*  7019 */     gridBagConstraints.gridx = 0;
/*  7020 */     gridBagConstraints.gridy = 1;
/*  7021 */     this.pnlLTRArmorBox.add(this.spnLTRArmor, gridBagConstraints);
/*       */     
/*  7023 */     gridBagConstraints = new GridBagConstraints();
/*  7024 */     gridBagConstraints.gridx = 0;
/*  7025 */     gridBagConstraints.gridy = 0;
/*  7026 */     this.pnlRearArmor.add(this.pnlLTRArmorBox, gridBagConstraints);
/*       */     
/*  7028 */     gridBagConstraints = new GridBagConstraints();
/*  7029 */     gridBagConstraints.gridx = 0;
/*  7030 */     gridBagConstraints.gridy = 3;
/*  7031 */     gridBagConstraints.gridwidth = 5;
/*  7032 */     this.pnlFrontArmor.add(this.pnlRearArmor, gridBagConstraints);
/*       */     
/*  7034 */     gridBagConstraints = new GridBagConstraints();
/*  7035 */     gridBagConstraints.gridx = 2;
/*  7036 */     gridBagConstraints.gridy = 0;
/*  7037 */     gridBagConstraints.gridheight = 4;
/*  7038 */     gridBagConstraints.anchor = 11;
/*  7039 */     this.pnlBasicSetup.add(this.pnlFrontArmor, gridBagConstraints);
/*       */     
/*  7041 */     this.pnlArmor.setLayout(new GridBagLayout());
/*       */     
/*  7043 */     this.pnlArmorInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Armor Information"));
/*  7044 */     this.pnlArmorInfo.setLayout(new GridBagLayout());
/*       */     
/*  7046 */     this.lblArmorCoverage.setText("100% Coverage");
/*  7047 */     gridBagConstraints = new GridBagConstraints();
/*  7048 */     gridBagConstraints.gridx = 2;
/*  7049 */     gridBagConstraints.gridy = 1;
/*  7050 */     gridBagConstraints.anchor = 13;
/*  7051 */     gridBagConstraints.insets = new Insets(0, 8, 0, 2);
/*  7052 */     this.pnlArmorInfo.add(this.lblArmorCoverage, gridBagConstraints);
/*       */     
/*  7054 */     this.lblArmorPoints.setText("999 of 999 Armor Points");
/*  7055 */     gridBagConstraints = new GridBagConstraints();
/*  7056 */     gridBagConstraints.gridx = 2;
/*  7057 */     gridBagConstraints.gridy = 0;
/*  7058 */     gridBagConstraints.anchor = 13;
/*  7059 */     gridBagConstraints.insets = new Insets(0, 8, 0, 2);
/*  7060 */     this.pnlArmorInfo.add(this.lblArmorPoints, gridBagConstraints);
/*       */     
/*  7062 */     this.txtSumArmorTon.setHorizontalAlignment(11);
/*  7063 */     this.txtSumArmorTon.setText("000.00");
/*  7064 */     this.txtSumArmorTon.setDisabledTextColor(new Color(0, 0, 0));
/*  7065 */     this.txtSumArmorTon.setEnabled(false);
/*  7066 */     this.txtSumArmorTon.setMaximumSize(new Dimension(50, 20));
/*  7067 */     this.txtSumArmorTon.setMinimumSize(new Dimension(50, 20));
/*  7068 */     this.txtSumArmorTon.setPreferredSize(new Dimension(50, 20));
/*  7069 */     gridBagConstraints = new GridBagConstraints();
/*  7070 */     gridBagConstraints.gridx = 0;
/*  7071 */     gridBagConstraints.gridy = 1;
/*  7072 */     gridBagConstraints.anchor = 17;
/*  7073 */     gridBagConstraints.insets = new Insets(0, 2, 2, 0);
/*  7074 */     this.pnlArmorInfo.add(this.txtSumArmorTon, gridBagConstraints);
/*       */     
/*  7076 */     this.txtSumArmorCrt.setHorizontalAlignment(11);
/*  7077 */     this.txtSumArmorCrt.setText("00");
/*  7078 */     this.txtSumArmorCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  7079 */     this.txtSumArmorCrt.setEnabled(false);
/*  7080 */     this.txtSumArmorCrt.setMaximumSize(new Dimension(40, 20));
/*  7081 */     this.txtSumArmorCrt.setMinimumSize(new Dimension(40, 20));
/*  7082 */     this.txtSumArmorCrt.setPreferredSize(new Dimension(40, 20));
/*  7083 */     gridBagConstraints = new GridBagConstraints();
/*  7084 */     gridBagConstraints.gridx = 1;
/*  7085 */     gridBagConstraints.gridy = 1;
/*  7086 */     gridBagConstraints.anchor = 17;
/*  7087 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/*  7088 */     this.pnlArmorInfo.add(this.txtSumArmorCrt, gridBagConstraints);
/*       */     
/*  7090 */     this.lblSumHeadTons1.setText("Tonnage");
/*  7091 */     gridBagConstraints = new GridBagConstraints();
/*  7092 */     gridBagConstraints.gridx = 0;
/*  7093 */     gridBagConstraints.gridy = 0;
/*  7094 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/*  7095 */     this.pnlArmorInfo.add(this.lblSumHeadTons1, gridBagConstraints);
/*       */     
/*  7097 */     this.lblSumHeadCrits1.setText("Crits");
/*  7098 */     this.pnlArmorInfo.add(this.lblSumHeadCrits1, new GridBagConstraints());
/*       */     
/*  7100 */     this.lblArmorTonsWasted.setText("0.00 Tons Wasted");
/*  7101 */     gridBagConstraints = new GridBagConstraints();
/*  7102 */     gridBagConstraints.gridx = 0;
/*  7103 */     gridBagConstraints.gridy = 2;
/*  7104 */     gridBagConstraints.gridwidth = 3;
/*  7105 */     gridBagConstraints.anchor = 13;
/*  7106 */     gridBagConstraints.insets = new Insets(2, 0, 0, 2);
/*  7107 */     this.pnlArmorInfo.add(this.lblArmorTonsWasted, gridBagConstraints);
/*       */     
/*  7109 */     this.lblAVInLot.setText("99 Points Left In This 1/2 Ton Lot");
/*  7110 */     gridBagConstraints = new GridBagConstraints();
/*  7111 */     gridBagConstraints.gridx = 0;
/*  7112 */     gridBagConstraints.gridy = 3;
/*  7113 */     gridBagConstraints.gridwidth = 3;
/*  7114 */     gridBagConstraints.anchor = 13;
/*  7115 */     gridBagConstraints.insets = new Insets(6, 0, 2, 2);
/*  7116 */     this.pnlArmorInfo.add(this.lblAVInLot, gridBagConstraints);
/*       */     
/*  7118 */     gridBagConstraints = new GridBagConstraints();
/*  7119 */     gridBagConstraints.gridx = 0;
/*  7120 */     gridBagConstraints.gridy = 1;
/*  7121 */     gridBagConstraints.fill = 2;
/*  7122 */     gridBagConstraints.anchor = 18;
/*  7123 */     this.pnlArmor.add(this.pnlArmorInfo, gridBagConstraints);
/*       */     
/*  7125 */     this.pnlArmorSetup.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Armor Setup"));
/*  7126 */     this.pnlArmorSetup.setLayout(new GridBagLayout());
/*       */     
/*  7128 */     this.btnMaxArmor.setText("Maximize Armor Tonnage");
/*  7129 */     this.btnMaxArmor.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7131 */         frmMainWide.this.btnMaxArmorActionPerformed(evt);
/*       */       }
/*  7133 */     });
/*  7134 */     gridBagConstraints = new GridBagConstraints();
/*  7135 */     gridBagConstraints.gridx = 0;
/*  7136 */     gridBagConstraints.gridy = 5;
/*  7137 */     gridBagConstraints.gridwidth = 2;
/*  7138 */     gridBagConstraints.fill = 2;
/*  7139 */     gridBagConstraints.insets = new Insets(2, 2, 0, 2);
/*  7140 */     this.pnlArmorSetup.add(this.btnMaxArmor, gridBagConstraints);
/*       */     
/*  7142 */     this.btnArmorTons.setText("Set Armor Tonnage");
/*  7143 */     this.btnArmorTons.setMaximumSize(new Dimension(194, 25));
/*  7144 */     this.btnArmorTons.setMinimumSize(new Dimension(194, 25));
/*  7145 */     this.btnArmorTons.setPreferredSize(new Dimension(194, 25));
/*  7146 */     this.btnArmorTons.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7148 */         frmMainWide.this.btnArmorTonsActionPerformed(evt);
/*       */       }
/*  7150 */     });
/*  7151 */     gridBagConstraints = new GridBagConstraints();
/*  7152 */     gridBagConstraints.gridx = 0;
/*  7153 */     gridBagConstraints.gridy = 2;
/*  7154 */     gridBagConstraints.gridwidth = 2;
/*  7155 */     gridBagConstraints.fill = 2;
/*  7156 */     gridBagConstraints.insets = new Insets(2, 2, 0, 2);
/*  7157 */     this.pnlArmorSetup.add(this.btnArmorTons, gridBagConstraints);
/*       */     
/*  7159 */     this.cmbArmorType.setModel(new DefaultComboBoxModel(new String[] { "Industrial", "Standard", "Ferro-Fibrous" }));
/*  7160 */     this.cmbArmorType.setSelectedIndex(1);
/*  7161 */     this.cmbArmorType.setMaximumSize(new Dimension(150, 20));
/*  7162 */     this.cmbArmorType.setMinimumSize(new Dimension(150, 20));
/*  7163 */     this.cmbArmorType.setPreferredSize(new Dimension(150, 20));
/*  7164 */     this.cmbArmorType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7166 */         frmMainWide.this.cmbArmorTypeActionPerformed(evt);
/*       */       }
/*  7168 */     });
/*  7169 */     gridBagConstraints = new GridBagConstraints();
/*  7170 */     gridBagConstraints.gridx = 1;
/*  7171 */     gridBagConstraints.gridy = 0;
/*  7172 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/*  7173 */     this.pnlArmorSetup.add(this.cmbArmorType, gridBagConstraints);
/*       */     
/*  7175 */     this.lblArmorType.setText("Armor Type:");
/*  7176 */     gridBagConstraints = new GridBagConstraints();
/*  7177 */     gridBagConstraints.gridx = 0;
/*  7178 */     gridBagConstraints.gridy = 0;
/*  7179 */     gridBagConstraints.anchor = 13;
/*  7180 */     gridBagConstraints.insets = new Insets(0, 2, 2, 2);
/*  7181 */     this.pnlArmorSetup.add(this.lblArmorType, gridBagConstraints);
/*       */     
/*  7183 */     this.btnBalanceArmor.setSelected(true);
/*  7184 */     this.btnBalanceArmor.setText("Balance Armor by Location");
/*  7185 */     gridBagConstraints = new GridBagConstraints();
/*  7186 */     gridBagConstraints.gridx = 0;
/*  7187 */     gridBagConstraints.gridy = 1;
/*  7188 */     gridBagConstraints.gridwidth = 2;
/*  7189 */     gridBagConstraints.anchor = 13;
/*  7190 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/*  7191 */     this.pnlArmorSetup.add(this.btnBalanceArmor, gridBagConstraints);
/*       */     
/*  7193 */     this.btnEfficientArmor.setText("Use Efficient Maximum");
/*  7194 */     this.btnEfficientArmor.setMaximumSize(new Dimension(194, 25));
/*  7195 */     this.btnEfficientArmor.setMinimumSize(new Dimension(194, 25));
/*  7196 */     this.btnEfficientArmor.setPreferredSize(new Dimension(194, 25));
/*  7197 */     this.btnEfficientArmor.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7199 */         frmMainWide.this.btnEfficientArmorActionPerformed(evt);
/*       */       }
/*  7201 */     });
/*  7202 */     gridBagConstraints = new GridBagConstraints();
/*  7203 */     gridBagConstraints.gridx = 0;
/*  7204 */     gridBagConstraints.gridy = 4;
/*  7205 */     gridBagConstraints.gridwidth = 2;
/*  7206 */     gridBagConstraints.fill = 2;
/*  7207 */     gridBagConstraints.insets = new Insets(2, 2, 0, 2);
/*  7208 */     this.pnlArmorSetup.add(this.btnEfficientArmor, gridBagConstraints);
/*       */     
/*  7210 */     this.btnRemainingArmor.setText("Use Remaining Tonnage");
/*  7211 */     this.btnRemainingArmor.setMaximumSize(new Dimension(194, 25));
/*  7212 */     this.btnRemainingArmor.setMinimumSize(new Dimension(194, 25));
/*  7213 */     this.btnRemainingArmor.setPreferredSize(new Dimension(194, 25));
/*  7214 */     this.btnRemainingArmor.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7216 */         frmMainWide.this.btnRemainingArmorActionPerformed(evt);
/*       */       }
/*  7218 */     });
/*  7219 */     gridBagConstraints = new GridBagConstraints();
/*  7220 */     gridBagConstraints.gridx = 0;
/*  7221 */     gridBagConstraints.gridy = 3;
/*  7222 */     gridBagConstraints.gridwidth = 2;
/*  7223 */     gridBagConstraints.fill = 2;
/*  7224 */     gridBagConstraints.insets = new Insets(2, 2, 0, 2);
/*  7225 */     this.pnlArmorSetup.add(this.btnRemainingArmor, gridBagConstraints);
/*       */     
/*  7227 */     gridBagConstraints = new GridBagConstraints();
/*  7228 */     gridBagConstraints.gridx = 0;
/*  7229 */     gridBagConstraints.gridy = 0;
/*  7230 */     gridBagConstraints.fill = 2;
/*  7231 */     gridBagConstraints.anchor = 18;
/*  7232 */     this.pnlArmor.add(this.pnlArmorSetup, gridBagConstraints);
/*       */     
/*  7234 */     this.pnlPatchworkChoosers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Patchwork Armor Types"));
/*  7235 */     this.pnlPatchworkChoosers.setLayout(new GridBagLayout());
/*       */     
/*  7237 */     this.lblPWHDLoc.setText("Head Armor: ");
/*  7238 */     gridBagConstraints = new GridBagConstraints();
/*  7239 */     gridBagConstraints.anchor = 13;
/*  7240 */     this.pnlPatchworkChoosers.add(this.lblPWHDLoc, gridBagConstraints);
/*       */     
/*  7242 */     this.lblPWCTLoc.setText("CT Armor: ");
/*  7243 */     gridBagConstraints = new GridBagConstraints();
/*  7244 */     gridBagConstraints.gridx = 0;
/*  7245 */     gridBagConstraints.gridy = 1;
/*  7246 */     gridBagConstraints.anchor = 13;
/*  7247 */     this.pnlPatchworkChoosers.add(this.lblPWCTLoc, gridBagConstraints);
/*       */     
/*  7249 */     this.lblPWLTLoc.setText("LT Armor: ");
/*  7250 */     gridBagConstraints = new GridBagConstraints();
/*  7251 */     gridBagConstraints.gridx = 0;
/*  7252 */     gridBagConstraints.gridy = 2;
/*  7253 */     gridBagConstraints.anchor = 13;
/*  7254 */     this.pnlPatchworkChoosers.add(this.lblPWLTLoc, gridBagConstraints);
/*       */     
/*  7256 */     this.lblPWRTLoc.setText("RT Armor: ");
/*  7257 */     gridBagConstraints = new GridBagConstraints();
/*  7258 */     gridBagConstraints.gridx = 0;
/*  7259 */     gridBagConstraints.gridy = 3;
/*  7260 */     gridBagConstraints.anchor = 13;
/*  7261 */     this.pnlPatchworkChoosers.add(this.lblPWRTLoc, gridBagConstraints);
/*       */     
/*  7263 */     this.lblPWLALoc.setText("LA Armor: ");
/*  7264 */     gridBagConstraints = new GridBagConstraints();
/*  7265 */     gridBagConstraints.gridx = 0;
/*  7266 */     gridBagConstraints.gridy = 4;
/*  7267 */     gridBagConstraints.anchor = 13;
/*  7268 */     this.pnlPatchworkChoosers.add(this.lblPWLALoc, gridBagConstraints);
/*       */     
/*  7270 */     this.lblPWRALoc.setText("RA Armor: ");
/*  7271 */     gridBagConstraints = new GridBagConstraints();
/*  7272 */     gridBagConstraints.gridx = 0;
/*  7273 */     gridBagConstraints.gridy = 5;
/*  7274 */     gridBagConstraints.anchor = 13;
/*  7275 */     this.pnlPatchworkChoosers.add(this.lblPWRALoc, gridBagConstraints);
/*       */     
/*  7277 */     this.lblPWLLLoc.setText("LL Armor: ");
/*  7278 */     gridBagConstraints = new GridBagConstraints();
/*  7279 */     gridBagConstraints.gridx = 0;
/*  7280 */     gridBagConstraints.gridy = 6;
/*  7281 */     gridBagConstraints.anchor = 13;
/*  7282 */     this.pnlPatchworkChoosers.add(this.lblPWLLLoc, gridBagConstraints);
/*       */     
/*  7284 */     this.cmbPWHDType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7285 */     this.cmbPWHDType.setMaximumSize(new Dimension(150, 20));
/*  7286 */     this.cmbPWHDType.setMinimumSize(new Dimension(150, 20));
/*  7287 */     this.cmbPWHDType.setPreferredSize(new Dimension(150, 20));
/*  7288 */     this.cmbPWHDType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7290 */         frmMainWide.this.cmbPWHDTypeActionPerformed(evt);
/*       */       }
/*  7292 */     });
/*  7293 */     this.pnlPatchworkChoosers.add(this.cmbPWHDType, new GridBagConstraints());
/*       */     
/*  7295 */     this.cmbPWCTType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7296 */     this.cmbPWCTType.setMaximumSize(new Dimension(150, 20));
/*  7297 */     this.cmbPWCTType.setMinimumSize(new Dimension(150, 20));
/*  7298 */     this.cmbPWCTType.setPreferredSize(new Dimension(150, 20));
/*  7299 */     this.cmbPWCTType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7301 */         frmMainWide.this.cmbPWCTTypeActionPerformed(evt);
/*       */       }
/*  7303 */     });
/*  7304 */     gridBagConstraints = new GridBagConstraints();
/*  7305 */     gridBagConstraints.gridx = 1;
/*  7306 */     gridBagConstraints.gridy = 1;
/*  7307 */     this.pnlPatchworkChoosers.add(this.cmbPWCTType, gridBagConstraints);
/*       */     
/*  7309 */     this.cmbPWLTType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7310 */     this.cmbPWLTType.setMaximumSize(new Dimension(150, 20));
/*  7311 */     this.cmbPWLTType.setMinimumSize(new Dimension(150, 20));
/*  7312 */     this.cmbPWLTType.setPreferredSize(new Dimension(150, 20));
/*  7313 */     this.cmbPWLTType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7315 */         frmMainWide.this.cmbPWLTTypeActionPerformed(evt);
/*       */       }
/*  7317 */     });
/*  7318 */     gridBagConstraints = new GridBagConstraints();
/*  7319 */     gridBagConstraints.gridx = 1;
/*  7320 */     gridBagConstraints.gridy = 2;
/*  7321 */     this.pnlPatchworkChoosers.add(this.cmbPWLTType, gridBagConstraints);
/*       */     
/*  7323 */     this.cmbPWRTType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7324 */     this.cmbPWRTType.setMaximumSize(new Dimension(150, 20));
/*  7325 */     this.cmbPWRTType.setMinimumSize(new Dimension(150, 20));
/*  7326 */     this.cmbPWRTType.setPreferredSize(new Dimension(150, 20));
/*  7327 */     this.cmbPWRTType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7329 */         frmMainWide.this.cmbPWRTTypeActionPerformed(evt);
/*       */       }
/*  7331 */     });
/*  7332 */     gridBagConstraints = new GridBagConstraints();
/*  7333 */     gridBagConstraints.gridx = 1;
/*  7334 */     gridBagConstraints.gridy = 3;
/*  7335 */     this.pnlPatchworkChoosers.add(this.cmbPWRTType, gridBagConstraints);
/*       */     
/*  7337 */     this.cmbPWLAType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7338 */     this.cmbPWLAType.setMaximumSize(new Dimension(150, 20));
/*  7339 */     this.cmbPWLAType.setMinimumSize(new Dimension(150, 20));
/*  7340 */     this.cmbPWLAType.setPreferredSize(new Dimension(150, 20));
/*  7341 */     this.cmbPWLAType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7343 */         frmMainWide.this.cmbPWLATypeActionPerformed(evt);
/*       */       }
/*  7345 */     });
/*  7346 */     gridBagConstraints = new GridBagConstraints();
/*  7347 */     gridBagConstraints.gridx = 1;
/*  7348 */     gridBagConstraints.gridy = 4;
/*  7349 */     this.pnlPatchworkChoosers.add(this.cmbPWLAType, gridBagConstraints);
/*       */     
/*  7351 */     this.cmbPWRAType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7352 */     this.cmbPWRAType.setMaximumSize(new Dimension(150, 20));
/*  7353 */     this.cmbPWRAType.setMinimumSize(new Dimension(150, 20));
/*  7354 */     this.cmbPWRAType.setPreferredSize(new Dimension(150, 20));
/*  7355 */     this.cmbPWRAType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7357 */         frmMainWide.this.cmbPWRATypeActionPerformed(evt);
/*       */       }
/*  7359 */     });
/*  7360 */     gridBagConstraints = new GridBagConstraints();
/*  7361 */     gridBagConstraints.gridx = 1;
/*  7362 */     gridBagConstraints.gridy = 5;
/*  7363 */     this.pnlPatchworkChoosers.add(this.cmbPWRAType, gridBagConstraints);
/*       */     
/*  7365 */     this.cmbPWLLType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7366 */     this.cmbPWLLType.setMaximumSize(new Dimension(150, 20));
/*  7367 */     this.cmbPWLLType.setMinimumSize(new Dimension(150, 20));
/*  7368 */     this.cmbPWLLType.setPreferredSize(new Dimension(150, 20));
/*  7369 */     this.cmbPWLLType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7371 */         frmMainWide.this.cmbPWLLTypeActionPerformed(evt);
/*       */       }
/*  7373 */     });
/*  7374 */     gridBagConstraints = new GridBagConstraints();
/*  7375 */     gridBagConstraints.gridx = 1;
/*  7376 */     gridBagConstraints.gridy = 6;
/*  7377 */     this.pnlPatchworkChoosers.add(this.cmbPWLLType, gridBagConstraints);
/*       */     
/*  7379 */     this.cmbPWRLType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7380 */     this.cmbPWRLType.setMaximumSize(new Dimension(150, 20));
/*  7381 */     this.cmbPWRLType.setMinimumSize(new Dimension(150, 20));
/*  7382 */     this.cmbPWRLType.setPreferredSize(new Dimension(150, 20));
/*  7383 */     this.cmbPWRLType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7385 */         frmMainWide.this.cmbPWRLTypeActionPerformed(evt);
/*       */       }
/*  7387 */     });
/*  7388 */     gridBagConstraints = new GridBagConstraints();
/*  7389 */     gridBagConstraints.gridx = 1;
/*  7390 */     gridBagConstraints.gridy = 7;
/*  7391 */     this.pnlPatchworkChoosers.add(this.cmbPWRLType, gridBagConstraints);
/*       */     
/*  7393 */     this.lblPWRLLoc.setText("RL Armor: ");
/*  7394 */     gridBagConstraints = new GridBagConstraints();
/*  7395 */     gridBagConstraints.gridx = 0;
/*  7396 */     gridBagConstraints.gridy = 7;
/*  7397 */     gridBagConstraints.anchor = 13;
/*  7398 */     this.pnlPatchworkChoosers.add(this.lblPWRLLoc, gridBagConstraints);
/*       */     
/*  7400 */     gridBagConstraints = new GridBagConstraints();
/*  7401 */     gridBagConstraints.gridx = 0;
/*  7402 */     gridBagConstraints.gridy = 2;
/*  7403 */     gridBagConstraints.fill = 2;
/*  7404 */     gridBagConstraints.anchor = 11;
/*  7405 */     this.pnlArmor.add(this.pnlPatchworkChoosers, gridBagConstraints);
/*       */     
/*  7407 */     gridBagConstraints = new GridBagConstraints();
/*  7408 */     gridBagConstraints.gridheight = 5;
/*  7409 */     gridBagConstraints.anchor = 11;
/*  7410 */     this.pnlBasicSetup.add(this.pnlArmor, gridBagConstraints);
/*       */     
/*  7412 */     this.tbpMainTabPane.addTab("Basic Setup", this.pnlBasicSetup);
/*       */     
/*  7414 */     this.pnlEquipment.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/*  7416 */     this.tbpWeaponChooser.setTabPlacement(4);
/*  7417 */     this.tbpWeaponChooser.setMaximumSize(new Dimension(300, 300));
/*  7418 */     this.tbpWeaponChooser.setMinimumSize(new Dimension(300, 300));
/*  7419 */     this.tbpWeaponChooser.setPreferredSize(new Dimension(300, 300));
/*       */     
/*  7421 */     this.pnlBallistic.setLayout(new javax.swing.BoxLayout(this.pnlBallistic, 1));
/*       */     
/*  7423 */     this.jSeparator3.setOrientation(1);
/*  7424 */     this.jSeparator3.setAlignmentX(0.0F);
/*  7425 */     this.jSeparator3.setAlignmentY(0.0F);
/*  7426 */     this.pnlBallistic.add(this.jSeparator3);
/*       */     
/*  7428 */     this.jScrollPane8.setHorizontalScrollBarPolicy(31);
/*  7429 */     this.jScrollPane8.setVerticalScrollBarPolicy(22);
/*  7430 */     this.jScrollPane8.setMaximumSize(new Dimension(200, 260));
/*  7431 */     this.jScrollPane8.setMinimumSize(new Dimension(200, 260));
/*  7432 */     this.jScrollPane8.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7434 */     this.lstChooseBallistic.setModel(new javax.swing.AbstractListModel() {
/*  7435 */       String[] strings = { "Placeholder" };
/*  7436 */       public int getSize() { return this.strings.length; }
/*  7437 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7438 */     });
/*  7439 */     this.lstChooseBallistic.setSelectionMode(0);
/*  7440 */     this.lstChooseBallistic.setMaximumSize(new Dimension(180, 10000));
/*  7441 */     this.lstChooseBallistic.setMinimumSize(new Dimension(180, 100));
/*  7442 */     this.lstChooseBallistic.setPreferredSize(null);
/*  7443 */     this.lstChooseBallistic.setVisibleRowCount(16);
/*  7444 */     this.lstChooseBallistic.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7446 */         frmMainWide.this.lstChooseBallisticValueChanged(evt);
/*       */       }
/*  7448 */     });
/*  7449 */     MouseListener mlBallistic = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7451 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7452 */           frmMainWide.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7455 */     };
/*  7456 */     this.lstChooseBallistic.addMouseListener(mlBallistic);
/*  7457 */     this.lstChooseBallistic.setCellRenderer(new EquipmentListRenderer(this));
/*  7458 */     this.jScrollPane8.setViewportView(this.lstChooseBallistic);
/*       */     
/*  7460 */     this.pnlBallistic.add(this.jScrollPane8);
/*       */     
/*  7462 */     this.jSeparator4.setOrientation(1);
/*  7463 */     this.jSeparator4.setAlignmentX(0.0F);
/*  7464 */     this.jSeparator4.setAlignmentY(0.0F);
/*  7465 */     this.pnlBallistic.add(this.jSeparator4);
/*       */     
/*  7467 */     this.tbpWeaponChooser.addTab("Ballistic", this.pnlBallistic);
/*       */     
/*  7469 */     this.pnlEnergy.setLayout(new javax.swing.BoxLayout(this.pnlEnergy, 1));
/*       */     
/*  7471 */     this.jSeparator2.setOrientation(1);
/*  7472 */     this.jSeparator2.setAlignmentX(0.0F);
/*  7473 */     this.jSeparator2.setAlignmentY(0.0F);
/*  7474 */     this.pnlEnergy.add(this.jSeparator2);
/*       */     
/*  7476 */     this.jScrollPane9.setHorizontalScrollBarPolicy(31);
/*  7477 */     this.jScrollPane9.setVerticalScrollBarPolicy(22);
/*  7478 */     this.jScrollPane9.setMaximumSize(new Dimension(200, 260));
/*  7479 */     this.jScrollPane9.setMinimumSize(new Dimension(200, 260));
/*  7480 */     this.jScrollPane9.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7482 */     this.lstChooseEnergy.setModel(new javax.swing.AbstractListModel() {
/*  7483 */       String[] strings = { "Placeholder" };
/*  7484 */       public int getSize() { return this.strings.length; }
/*  7485 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7486 */     });
/*  7487 */     this.lstChooseEnergy.setSelectionMode(0);
/*  7488 */     this.lstChooseEnergy.setMaximumSize(new Dimension(180, 10000));
/*  7489 */     this.lstChooseEnergy.setMinimumSize(new Dimension(180, 100));
/*  7490 */     this.lstChooseEnergy.setPreferredSize(null);
/*  7491 */     this.lstChooseEnergy.setVisibleRowCount(16);
/*  7492 */     this.lstChooseEnergy.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7494 */         frmMainWide.this.lstChooseEnergyValueChanged(evt);
/*       */       }
/*  7496 */     });
/*  7497 */     MouseListener mlEnergy = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7499 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7500 */           frmMainWide.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7503 */     };
/*  7504 */     this.lstChooseEnergy.addMouseListener(mlEnergy);
/*  7505 */     this.lstChooseEnergy.setCellRenderer(new EquipmentListRenderer(this));
/*  7506 */     this.jScrollPane9.setViewportView(this.lstChooseEnergy);
/*       */     
/*  7508 */     this.pnlEnergy.add(this.jScrollPane9);
/*       */     
/*  7510 */     this.jSeparator1.setOrientation(1);
/*  7511 */     this.jSeparator1.setAlignmentX(0.0F);
/*  7512 */     this.jSeparator1.setAlignmentY(0.0F);
/*  7513 */     this.pnlEnergy.add(this.jSeparator1);
/*       */     
/*  7515 */     this.tbpWeaponChooser.addTab("Energy", this.pnlEnergy);
/*       */     
/*  7517 */     this.pnlMissile.setLayout(new javax.swing.BoxLayout(this.pnlMissile, 1));
/*       */     
/*  7519 */     this.jSeparator5.setOrientation(1);
/*  7520 */     this.jSeparator5.setAlignmentX(0.0F);
/*  7521 */     this.jSeparator5.setAlignmentY(0.0F);
/*  7522 */     this.pnlMissile.add(this.jSeparator5);
/*       */     
/*  7524 */     this.jScrollPane19.setHorizontalScrollBarPolicy(31);
/*  7525 */     this.jScrollPane19.setVerticalScrollBarPolicy(22);
/*  7526 */     this.jScrollPane19.setMaximumSize(new Dimension(200, 260));
/*  7527 */     this.jScrollPane19.setMinimumSize(new Dimension(200, 260));
/*  7528 */     this.jScrollPane19.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7530 */     this.lstChooseMissile.setModel(new javax.swing.AbstractListModel() {
/*  7531 */       String[] strings = { "Placeholder" };
/*  7532 */       public int getSize() { return this.strings.length; }
/*  7533 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7534 */     });
/*  7535 */     this.lstChooseMissile.setSelectionMode(0);
/*  7536 */     this.lstChooseMissile.setMaximumSize(new Dimension(180, 10000));
/*  7537 */     this.lstChooseMissile.setMinimumSize(new Dimension(180, 100));
/*  7538 */     this.lstChooseMissile.setPreferredSize(null);
/*  7539 */     this.lstChooseMissile.setVisibleRowCount(16);
/*  7540 */     this.lstChooseMissile.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7542 */         frmMainWide.this.lstChooseMissileValueChanged(evt);
/*       */       }
/*  7544 */     });
/*  7545 */     MouseListener mlMissile = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7547 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7548 */           frmMainWide.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7551 */     };
/*  7552 */     this.lstChooseMissile.addMouseListener(mlMissile);
/*  7553 */     this.lstChooseMissile.setCellRenderer(new EquipmentListRenderer(this));
/*  7554 */     this.jScrollPane19.setViewportView(this.lstChooseMissile);
/*       */     
/*  7556 */     this.pnlMissile.add(this.jScrollPane19);
/*       */     
/*  7558 */     this.jSeparator6.setOrientation(1);
/*  7559 */     this.jSeparator6.setAlignmentX(0.0F);
/*  7560 */     this.jSeparator6.setAlignmentY(0.0F);
/*  7561 */     this.pnlMissile.add(this.jSeparator6);
/*       */     
/*  7563 */     this.tbpWeaponChooser.addTab("Missile", this.pnlMissile);
/*       */     
/*  7565 */     this.pnlPhysical.setLayout(new javax.swing.BoxLayout(this.pnlPhysical, 1));
/*       */     
/*  7567 */     this.jSeparator8.setOrientation(1);
/*  7568 */     this.jSeparator8.setAlignmentX(0.0F);
/*  7569 */     this.jSeparator8.setAlignmentY(0.0F);
/*  7570 */     this.pnlPhysical.add(this.jSeparator8);
/*       */     
/*  7572 */     this.jScrollPane20.setHorizontalScrollBarPolicy(31);
/*  7573 */     this.jScrollPane20.setVerticalScrollBarPolicy(22);
/*  7574 */     this.jScrollPane20.setMaximumSize(new Dimension(200, 260));
/*  7575 */     this.jScrollPane20.setMinimumSize(new Dimension(200, 260));
/*  7576 */     this.jScrollPane20.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7578 */     this.lstChoosePhysical.setModel(new javax.swing.AbstractListModel() {
/*  7579 */       String[] strings = { "Placeholder" };
/*  7580 */       public int getSize() { return this.strings.length; }
/*  7581 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7582 */     });
/*  7583 */     this.lstChoosePhysical.setSelectionMode(0);
/*  7584 */     this.lstChoosePhysical.setMaximumSize(new Dimension(180, 10000));
/*  7585 */     this.lstChoosePhysical.setMinimumSize(new Dimension(180, 100));
/*  7586 */     this.lstChoosePhysical.setPreferredSize(null);
/*  7587 */     this.lstChoosePhysical.setVisibleRowCount(16);
/*  7588 */     this.lstChoosePhysical.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7590 */         frmMainWide.this.lstChoosePhysicalValueChanged(evt);
/*       */       }
/*  7592 */     });
/*  7593 */     MouseListener mlPhysical = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7595 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7596 */           frmMainWide.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7599 */     };
/*  7600 */     this.lstChoosePhysical.addMouseListener(mlPhysical);
/*  7601 */     this.lstChoosePhysical.setCellRenderer(new EquipmentListRenderer(this));
/*  7602 */     this.jScrollPane20.setViewportView(this.lstChoosePhysical);
/*       */     
/*  7604 */     this.pnlPhysical.add(this.jScrollPane20);
/*       */     
/*  7606 */     this.jSeparator7.setOrientation(1);
/*  7607 */     this.jSeparator7.setAlignmentX(0.0F);
/*  7608 */     this.jSeparator7.setAlignmentY(0.0F);
/*  7609 */     this.pnlPhysical.add(this.jSeparator7);
/*       */     
/*  7611 */     this.tbpWeaponChooser.addTab("Physical", this.pnlPhysical);
/*       */     
/*  7613 */     this.pnlEquipmentChooser.setLayout(new javax.swing.BoxLayout(this.pnlEquipmentChooser, 1));
/*       */     
/*  7615 */     this.jSeparator10.setOrientation(1);
/*  7616 */     this.jSeparator10.setAlignmentX(0.0F);
/*  7617 */     this.jSeparator10.setAlignmentY(0.0F);
/*  7618 */     this.pnlEquipmentChooser.add(this.jSeparator10);
/*       */     
/*  7620 */     this.jScrollPane21.setHorizontalScrollBarPolicy(31);
/*  7621 */     this.jScrollPane21.setVerticalScrollBarPolicy(22);
/*  7622 */     this.jScrollPane21.setMaximumSize(new Dimension(200, 260));
/*  7623 */     this.jScrollPane21.setMinimumSize(new Dimension(200, 260));
/*  7624 */     this.jScrollPane21.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7626 */     this.lstChooseEquipment.setModel(new javax.swing.AbstractListModel() {
/*  7627 */       String[] strings = { "Placeholder" };
/*  7628 */       public int getSize() { return this.strings.length; }
/*  7629 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7630 */     });
/*  7631 */     this.lstChooseEquipment.setSelectionMode(0);
/*  7632 */     this.lstChooseEquipment.setMaximumSize(new Dimension(180, 10000));
/*  7633 */     this.lstChooseEquipment.setMinimumSize(new Dimension(180, 100));
/*  7634 */     this.lstChooseEquipment.setPreferredSize(null);
/*  7635 */     this.lstChooseEquipment.setVisibleRowCount(16);
/*  7636 */     this.lstChooseEquipment.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7638 */         frmMainWide.this.lstChooseEquipmentValueChanged(evt);
/*       */       }
/*  7640 */     });
/*  7641 */     MouseListener mlEquipment = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7643 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7644 */           frmMainWide.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7647 */     };
/*  7648 */     this.lstChooseEquipment.addMouseListener(mlEquipment);
/*  7649 */     this.lstChooseEquipment.setCellRenderer(new EquipmentListRenderer(this));
/*  7650 */     this.jScrollPane21.setViewportView(this.lstChooseEquipment);
/*       */     
/*  7652 */     this.pnlEquipmentChooser.add(this.jScrollPane21);
/*       */     
/*  7654 */     this.jSeparator9.setOrientation(1);
/*  7655 */     this.jSeparator9.setAlignmentX(0.0F);
/*  7656 */     this.jSeparator9.setAlignmentY(0.0F);
/*  7657 */     this.pnlEquipmentChooser.add(this.jSeparator9);
/*       */     
/*  7659 */     this.tbpWeaponChooser.addTab("Equipment", this.pnlEquipmentChooser);
/*       */     
/*  7661 */     this.pnlArtillery.setLayout(new javax.swing.BoxLayout(this.pnlArtillery, 1));
/*       */     
/*  7663 */     this.jSeparator18.setOrientation(1);
/*  7664 */     this.jSeparator18.setAlignmentX(0.0F);
/*  7665 */     this.jSeparator18.setAlignmentY(0.0F);
/*  7666 */     this.pnlArtillery.add(this.jSeparator18);
/*       */     
/*  7668 */     this.jScrollPane24.setHorizontalScrollBarPolicy(31);
/*  7669 */     this.jScrollPane24.setVerticalScrollBarPolicy(22);
/*  7670 */     this.jScrollPane24.setMaximumSize(new Dimension(200, 260));
/*  7671 */     this.jScrollPane24.setMinimumSize(new Dimension(200, 260));
/*  7672 */     this.jScrollPane24.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7674 */     this.lstChooseArtillery.setModel(new javax.swing.AbstractListModel() {
/*  7675 */       String[] strings = { "Placeholder" };
/*  7676 */       public int getSize() { return this.strings.length; }
/*  7677 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7678 */     });
/*  7679 */     this.lstChooseArtillery.setSelectionMode(0);
/*  7680 */     this.lstChooseArtillery.setMaximumSize(new Dimension(180, 10000));
/*  7681 */     this.lstChooseArtillery.setMinimumSize(new Dimension(180, 100));
/*  7682 */     this.lstChooseArtillery.setPreferredSize(null);
/*  7683 */     this.lstChooseArtillery.setVisibleRowCount(16);
/*  7684 */     this.lstChooseArtillery.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7686 */         frmMainWide.this.lstChooseArtilleryValueChanged(evt);
/*       */       }
/*  7688 */     });
/*  7689 */     MouseListener mlArtillery = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7691 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7692 */           frmMainWide.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7695 */     };
/*  7696 */     this.lstChooseArtillery.addMouseListener(mlArtillery);
/*  7697 */     this.lstChooseArtillery.setCellRenderer(new EquipmentListRenderer(this));
/*  7698 */     this.jScrollPane24.setViewportView(this.lstChooseArtillery);
/*       */     
/*  7700 */     this.pnlArtillery.add(this.jScrollPane24);
/*       */     
/*  7702 */     this.jSeparator19.setOrientation(1);
/*  7703 */     this.jSeparator19.setAlignmentX(0.0F);
/*  7704 */     this.jSeparator19.setAlignmentY(0.0F);
/*  7705 */     this.pnlArtillery.add(this.jSeparator19);
/*       */     
/*  7707 */     this.tbpWeaponChooser.addTab("Artillery", this.pnlArtillery);
/*       */     
/*  7709 */     this.pnlAmmunition.setLayout(new javax.swing.BoxLayout(this.pnlAmmunition, 1));
/*       */     
/*  7711 */     this.jSeparator11.setOrientation(1);
/*  7712 */     this.jSeparator11.setAlignmentX(0.0F);
/*  7713 */     this.jSeparator11.setAlignmentY(0.0F);
/*  7714 */     this.pnlAmmunition.add(this.jSeparator11);
/*       */     
/*  7716 */     this.jScrollPane22.setHorizontalScrollBarPolicy(31);
/*  7717 */     this.jScrollPane22.setVerticalScrollBarPolicy(22);
/*  7718 */     this.jScrollPane22.setMaximumSize(new Dimension(200, 260));
/*  7719 */     this.jScrollPane22.setMinimumSize(new Dimension(200, 260));
/*  7720 */     this.jScrollPane22.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7722 */     this.lstChooseAmmunition.setModel(new javax.swing.AbstractListModel() {
/*  7723 */       String[] strings = { "Placeholder" };
/*  7724 */       public int getSize() { return this.strings.length; }
/*  7725 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7726 */     });
/*  7727 */     this.lstChooseAmmunition.setSelectionMode(0);
/*  7728 */     this.lstChooseAmmunition.setMaximumSize(new Dimension(180, 10000));
/*  7729 */     this.lstChooseAmmunition.setMinimumSize(new Dimension(180, 100));
/*  7730 */     this.lstChooseAmmunition.setPreferredSize(null);
/*  7731 */     this.lstChooseAmmunition.setVisibleRowCount(16);
/*  7732 */     this.lstChooseAmmunition.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7734 */         frmMainWide.this.lstChooseAmmunitionValueChanged(evt);
/*       */       }
/*  7736 */     });
/*  7737 */     MouseListener mlAmmo = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7739 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7740 */           frmMainWide.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7743 */     };
/*  7744 */     this.lstChooseAmmunition.addMouseListener(mlAmmo);
/*  7745 */     this.lstChooseAmmunition.setCellRenderer(new EquipmentListRenderer(this));
/*  7746 */     this.jScrollPane22.setViewportView(this.lstChooseAmmunition);
/*       */     
/*  7748 */     this.pnlAmmunition.add(this.jScrollPane22);
/*       */     
/*  7750 */     this.jSeparator12.setOrientation(1);
/*  7751 */     this.jSeparator12.setAlignmentX(0.0F);
/*  7752 */     this.jSeparator12.setAlignmentY(0.0F);
/*  7753 */     this.pnlAmmunition.add(this.jSeparator12);
/*       */     
/*  7755 */     this.tbpWeaponChooser.addTab("Ammunition", this.pnlAmmunition);
/*       */     
/*  7757 */     this.pnlEquipment.add(this.tbpWeaponChooser, new AbsoluteConstraints(10, 10, -1, -1));
/*       */     
/*  7759 */     this.pnlSpecials.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Specials"));
/*  7760 */     this.pnlSpecials.setLayout(new GridBagLayout());
/*       */     
/*  7762 */     this.jLabel16.setText("Missile Guidance:");
/*  7763 */     gridBagConstraints = new GridBagConstraints();
/*  7764 */     gridBagConstraints.fill = 2;
/*  7765 */     gridBagConstraints.anchor = 17;
/*  7766 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/*  7767 */     this.pnlSpecials.add(this.jLabel16, gridBagConstraints);
/*       */     
/*  7769 */     this.chkUseTC.setText("Targeting Computer");
/*  7770 */     this.chkUseTC.setEnabled(false);
/*  7771 */     this.chkUseTC.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7773 */         frmMainWide.this.chkUseTCActionPerformed(evt);
/*       */       }
/*  7775 */     });
/*  7776 */     gridBagConstraints = new GridBagConstraints();
/*  7777 */     gridBagConstraints.gridx = 0;
/*  7778 */     gridBagConstraints.gridy = 4;
/*  7779 */     gridBagConstraints.anchor = 17;
/*  7780 */     gridBagConstraints.insets = new Insets(8, 2, 0, 0);
/*  7781 */     this.pnlSpecials.add(this.chkUseTC, gridBagConstraints);
/*       */     
/*  7783 */     this.chkFCSAIV.setText("Use Artemis IV");
/*  7784 */     this.chkFCSAIV.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7786 */         frmMainWide.this.chkFCSAIVActionPerformed(evt);
/*       */       }
/*  7788 */     });
/*  7789 */     gridBagConstraints = new GridBagConstraints();
/*  7790 */     gridBagConstraints.gridx = 0;
/*  7791 */     gridBagConstraints.gridy = 1;
/*  7792 */     gridBagConstraints.anchor = 17;
/*  7793 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  7794 */     this.pnlSpecials.add(this.chkFCSAIV, gridBagConstraints);
/*       */     
/*  7796 */     this.chkFCSAV.setText("Use Artemis V");
/*  7797 */     this.chkFCSAV.setEnabled(false);
/*  7798 */     this.chkFCSAV.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7800 */         frmMainWide.this.chkFCSAVActionPerformed(evt);
/*       */       }
/*  7802 */     });
/*  7803 */     gridBagConstraints = new GridBagConstraints();
/*  7804 */     gridBagConstraints.gridx = 0;
/*  7805 */     gridBagConstraints.gridy = 2;
/*  7806 */     gridBagConstraints.anchor = 17;
/*  7807 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  7808 */     this.pnlSpecials.add(this.chkFCSAV, gridBagConstraints);
/*       */     
/*  7810 */     this.chkFCSApollo.setText("Use MRM Apollo");
/*  7811 */     this.chkFCSApollo.setEnabled(false);
/*  7812 */     this.chkFCSApollo.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7814 */         frmMainWide.this.chkFCSApolloActionPerformed(evt);
/*       */       }
/*  7816 */     });
/*  7817 */     gridBagConstraints = new GridBagConstraints();
/*  7818 */     gridBagConstraints.gridx = 0;
/*  7819 */     gridBagConstraints.gridy = 3;
/*  7820 */     gridBagConstraints.anchor = 17;
/*  7821 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  7822 */     this.pnlSpecials.add(this.chkFCSApollo, gridBagConstraints);
/*       */     
/*  7824 */     this.chkClanCASE.setText("Use Clan CASE");
/*  7825 */     this.chkClanCASE.setEnabled(false);
/*  7826 */     this.chkClanCASE.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7828 */         frmMainWide.this.chkClanCASEActionPerformed(evt);
/*       */       }
/*  7830 */     });
/*  7831 */     gridBagConstraints = new GridBagConstraints();
/*  7832 */     gridBagConstraints.gridx = 0;
/*  7833 */     gridBagConstraints.gridy = 5;
/*  7834 */     gridBagConstraints.anchor = 17;
/*  7835 */     gridBagConstraints.insets = new Insets(8, 2, 0, 0);
/*  7836 */     this.pnlSpecials.add(this.chkClanCASE, gridBagConstraints);
/*       */     
/*  7838 */     this.pnlEquipment.add(this.pnlSpecials, new AbsoluteConstraints(310, 90, 160, 180));
/*       */     
/*  7840 */     this.pnlControls.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Controls"));
/*  7841 */     this.pnlControls.setLayout(new GridBagLayout());
/*       */     
/*  7843 */     this.btnRemoveEquip.setText("<<");
/*  7844 */     this.btnRemoveEquip.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7846 */         frmMainWide.this.btnRemoveEquipActionPerformed(evt);
/*       */       }
/*  7848 */     });
/*  7849 */     gridBagConstraints = new GridBagConstraints();
/*  7850 */     gridBagConstraints.insets = new Insets(0, 0, 0, 8);
/*  7851 */     this.pnlControls.add(this.btnRemoveEquip, gridBagConstraints);
/*       */     
/*  7853 */     this.btnClearEquip.setText("Clear");
/*  7854 */     this.btnClearEquip.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7856 */         frmMainWide.this.btnClearEquipActionPerformed(evt);
/*       */       }
/*  7858 */     });
/*  7859 */     gridBagConstraints = new GridBagConstraints();
/*  7860 */     gridBagConstraints.gridx = 0;
/*  7861 */     gridBagConstraints.gridy = 1;
/*  7862 */     gridBagConstraints.insets = new Insets(4, 0, 0, 8);
/*  7863 */     this.pnlControls.add(this.btnClearEquip, gridBagConstraints);
/*       */     
/*  7865 */     this.btnAddEquip.setText(">>");
/*  7866 */     this.btnAddEquip.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7868 */         frmMainWide.this.btnAddEquipActionPerformed(evt);
/*       */       }
/*  7870 */     });
/*  7871 */     gridBagConstraints = new GridBagConstraints();
/*  7872 */     gridBagConstraints.gridx = 1;
/*  7873 */     gridBagConstraints.gridy = 0;
/*  7874 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  7875 */     this.pnlControls.add(this.btnAddEquip, gridBagConstraints);
/*       */     
/*  7877 */     this.cmbNumEquips.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
/*  7878 */     gridBagConstraints = new GridBagConstraints();
/*  7879 */     gridBagConstraints.gridx = 1;
/*  7880 */     gridBagConstraints.gridy = 1;
/*  7881 */     gridBagConstraints.insets = new Insets(4, 8, 0, 0);
/*  7882 */     this.pnlControls.add(this.cmbNumEquips, gridBagConstraints);
/*       */     
/*  7884 */     this.pnlEquipment.add(this.pnlControls, new AbsoluteConstraints(310, 10, 160, -1));
/*       */     
/*  7886 */     this.pnlEquipInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Information"));
/*  7887 */     this.pnlEquipInfo.setLayout(new GridBagLayout());
/*       */     
/*  7889 */     this.jLabel17.setText("Availability(AoW/SL)");
/*  7890 */     gridBagConstraints = new GridBagConstraints();
/*  7891 */     gridBagConstraints.gridx = 0;
/*  7892 */     gridBagConstraints.gridy = 3;
/*  7893 */     gridBagConstraints.anchor = 17;
/*  7894 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/*  7895 */     this.pnlEquipInfo.add(this.jLabel17, gridBagConstraints);
/*       */     
/*  7897 */     this.jLabel18.setText("Availability (SW)");
/*  7898 */     gridBagConstraints = new GridBagConstraints();
/*  7899 */     gridBagConstraints.gridx = 0;
/*  7900 */     gridBagConstraints.gridy = 4;
/*  7901 */     gridBagConstraints.anchor = 17;
/*  7902 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/*  7903 */     this.pnlEquipInfo.add(this.jLabel18, gridBagConstraints);
/*       */     
/*  7905 */     this.jLabel19.setText("Availability (CI)");
/*  7906 */     gridBagConstraints = new GridBagConstraints();
/*  7907 */     gridBagConstraints.gridx = 0;
/*  7908 */     gridBagConstraints.gridy = 5;
/*  7909 */     gridBagConstraints.anchor = 17;
/*  7910 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/*  7911 */     this.pnlEquipInfo.add(this.jLabel19, gridBagConstraints);
/*  7912 */     gridBagConstraints = new GridBagConstraints();
/*  7913 */     gridBagConstraints.gridx = 1;
/*  7914 */     gridBagConstraints.gridy = 3;
/*  7915 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7916 */     this.pnlEquipInfo.add(this.lblInfoAVSL, gridBagConstraints);
/*  7917 */     gridBagConstraints = new GridBagConstraints();
/*  7918 */     gridBagConstraints.gridx = 1;
/*  7919 */     gridBagConstraints.gridy = 4;
/*  7920 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7921 */     this.pnlEquipInfo.add(this.lblInfoAVSW, gridBagConstraints);
/*  7922 */     gridBagConstraints = new GridBagConstraints();
/*  7923 */     gridBagConstraints.gridx = 1;
/*  7924 */     gridBagConstraints.gridy = 5;
/*  7925 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7926 */     this.pnlEquipInfo.add(this.lblInfoAVCI, gridBagConstraints);
/*       */     
/*  7928 */     this.jLabel20.setText("Introduction");
/*  7929 */     gridBagConstraints = new GridBagConstraints();
/*  7930 */     gridBagConstraints.gridx = 2;
/*  7931 */     gridBagConstraints.gridy = 3;
/*  7932 */     gridBagConstraints.gridwidth = 2;
/*  7933 */     gridBagConstraints.anchor = 13;
/*  7934 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7935 */     this.pnlEquipInfo.add(this.jLabel20, gridBagConstraints);
/*       */     
/*  7937 */     this.jLabel21.setText("Extinction");
/*  7938 */     gridBagConstraints = new GridBagConstraints();
/*  7939 */     gridBagConstraints.gridx = 2;
/*  7940 */     gridBagConstraints.gridy = 4;
/*  7941 */     gridBagConstraints.gridwidth = 2;
/*  7942 */     gridBagConstraints.anchor = 13;
/*  7943 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7944 */     this.pnlEquipInfo.add(this.jLabel21, gridBagConstraints);
/*       */     
/*  7946 */     this.jLabel22.setText("Reintroduction");
/*  7947 */     gridBagConstraints = new GridBagConstraints();
/*  7948 */     gridBagConstraints.gridx = 2;
/*  7949 */     gridBagConstraints.gridy = 5;
/*  7950 */     gridBagConstraints.gridwidth = 2;
/*  7951 */     gridBagConstraints.anchor = 13;
/*  7952 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7953 */     this.pnlEquipInfo.add(this.jLabel22, gridBagConstraints);
/*  7954 */     gridBagConstraints = new GridBagConstraints();
/*  7955 */     gridBagConstraints.gridx = 4;
/*  7956 */     gridBagConstraints.gridy = 3;
/*  7957 */     gridBagConstraints.anchor = 17;
/*  7958 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7959 */     this.pnlEquipInfo.add(this.lblInfoIntro, gridBagConstraints);
/*  7960 */     gridBagConstraints = new GridBagConstraints();
/*  7961 */     gridBagConstraints.gridx = 4;
/*  7962 */     gridBagConstraints.gridy = 4;
/*  7963 */     gridBagConstraints.anchor = 17;
/*  7964 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7965 */     this.pnlEquipInfo.add(this.lblInfoExtinct, gridBagConstraints);
/*  7966 */     gridBagConstraints = new GridBagConstraints();
/*  7967 */     gridBagConstraints.gridx = 4;
/*  7968 */     gridBagConstraints.gridy = 5;
/*  7969 */     gridBagConstraints.anchor = 17;
/*  7970 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  7971 */     this.pnlEquipInfo.add(this.lblInfoReintro, gridBagConstraints);
/*       */     
/*  7973 */     this.jLabel23.setText("Name");
/*  7974 */     gridBagConstraints = new GridBagConstraints();
/*  7975 */     gridBagConstraints.anchor = 17;
/*  7976 */     gridBagConstraints.insets = new Insets(4, 0, 0, 3);
/*  7977 */     this.pnlEquipInfo.add(this.jLabel23, gridBagConstraints);
/*       */     
/*  7979 */     this.jLabel24.setText("Type");
/*  7980 */     gridBagConstraints = new GridBagConstraints();
/*  7981 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  7982 */     this.pnlEquipInfo.add(this.jLabel24, gridBagConstraints);
/*       */     
/*  7984 */     this.jLabel25.setText("Heat");
/*  7985 */     gridBagConstraints = new GridBagConstraints();
/*  7986 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  7987 */     this.pnlEquipInfo.add(this.jLabel25, gridBagConstraints);
/*       */     
/*  7989 */     this.jLabel26.setText("Damage");
/*  7990 */     gridBagConstraints = new GridBagConstraints();
/*  7991 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  7992 */     this.pnlEquipInfo.add(this.jLabel26, gridBagConstraints);
/*       */     
/*  7994 */     this.jLabel27.setText("Range");
/*  7995 */     gridBagConstraints = new GridBagConstraints();
/*  7996 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  7997 */     this.pnlEquipInfo.add(this.jLabel27, gridBagConstraints);
/*       */     
/*  7999 */     this.lblInfoName.setText(" ");
/*  8000 */     gridBagConstraints = new GridBagConstraints();
/*  8001 */     gridBagConstraints.gridx = 0;
/*  8002 */     gridBagConstraints.gridy = 1;
/*  8003 */     gridBagConstraints.anchor = 17;
/*  8004 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/*  8005 */     this.pnlEquipInfo.add(this.lblInfoName, gridBagConstraints);
/*  8006 */     gridBagConstraints = new GridBagConstraints();
/*  8007 */     gridBagConstraints.gridx = 1;
/*  8008 */     gridBagConstraints.gridy = 1;
/*  8009 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8010 */     this.pnlEquipInfo.add(this.lblInfoType, gridBagConstraints);
/*  8011 */     gridBagConstraints = new GridBagConstraints();
/*  8012 */     gridBagConstraints.gridx = 2;
/*  8013 */     gridBagConstraints.gridy = 1;
/*  8014 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8015 */     this.pnlEquipInfo.add(this.lblInfoHeat, gridBagConstraints);
/*  8016 */     gridBagConstraints = new GridBagConstraints();
/*  8017 */     gridBagConstraints.gridx = 3;
/*  8018 */     gridBagConstraints.gridy = 1;
/*  8019 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8020 */     this.pnlEquipInfo.add(this.lblInfoDamage, gridBagConstraints);
/*  8021 */     gridBagConstraints = new GridBagConstraints();
/*  8022 */     gridBagConstraints.gridx = 4;
/*  8023 */     gridBagConstraints.gridy = 1;
/*  8024 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8025 */     this.pnlEquipInfo.add(this.lblInfoRange, gridBagConstraints);
/*       */     
/*  8027 */     this.jSeparator13.setBorder(BorderFactory.createEtchedBorder());
/*  8028 */     gridBagConstraints = new GridBagConstraints();
/*  8029 */     gridBagConstraints.gridx = 0;
/*  8030 */     gridBagConstraints.gridy = 2;
/*  8031 */     gridBagConstraints.gridwidth = 0;
/*  8032 */     gridBagConstraints.fill = 2;
/*  8033 */     gridBagConstraints.insets = new Insets(4, 0, 4, 0);
/*  8034 */     this.pnlEquipInfo.add(this.jSeparator13, gridBagConstraints);
/*       */     
/*  8036 */     this.jLabel28.setText("Ammo");
/*  8037 */     gridBagConstraints = new GridBagConstraints();
/*  8038 */     gridBagConstraints.gridx = 5;
/*  8039 */     gridBagConstraints.gridy = 0;
/*  8040 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8041 */     this.pnlEquipInfo.add(this.jLabel28, gridBagConstraints);
/*       */     
/*  8043 */     this.jLabel29.setText("Tonnage");
/*  8044 */     gridBagConstraints = new GridBagConstraints();
/*  8045 */     gridBagConstraints.gridx = 6;
/*  8046 */     gridBagConstraints.gridy = 0;
/*  8047 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8048 */     this.pnlEquipInfo.add(this.jLabel29, gridBagConstraints);
/*       */     
/*  8050 */     this.jLabel30.setText("Crits");
/*  8051 */     gridBagConstraints = new GridBagConstraints();
/*  8052 */     gridBagConstraints.gridx = 7;
/*  8053 */     gridBagConstraints.gridy = 0;
/*  8054 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8055 */     this.pnlEquipInfo.add(this.jLabel30, gridBagConstraints);
/*       */     
/*  8057 */     this.jLabel31.setText("Specials");
/*  8058 */     gridBagConstraints = new GridBagConstraints();
/*  8059 */     gridBagConstraints.gridx = 8;
/*  8060 */     gridBagConstraints.gridy = 0;
/*  8061 */     gridBagConstraints.insets = new Insets(4, 3, 0, 0);
/*  8062 */     this.pnlEquipInfo.add(this.jLabel31, gridBagConstraints);
/*  8063 */     gridBagConstraints = new GridBagConstraints();
/*  8064 */     gridBagConstraints.gridx = 5;
/*  8065 */     gridBagConstraints.gridy = 1;
/*  8066 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8067 */     this.pnlEquipInfo.add(this.lblInfoAmmo, gridBagConstraints);
/*  8068 */     gridBagConstraints = new GridBagConstraints();
/*  8069 */     gridBagConstraints.gridx = 6;
/*  8070 */     gridBagConstraints.gridy = 1;
/*  8071 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8072 */     this.pnlEquipInfo.add(this.lblInfoTonnage, gridBagConstraints);
/*  8073 */     gridBagConstraints = new GridBagConstraints();
/*  8074 */     gridBagConstraints.gridx = 7;
/*  8075 */     gridBagConstraints.gridy = 1;
/*  8076 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8077 */     this.pnlEquipInfo.add(this.lblInfoCrits, gridBagConstraints);
/*  8078 */     gridBagConstraints = new GridBagConstraints();
/*  8079 */     gridBagConstraints.gridx = 8;
/*  8080 */     gridBagConstraints.gridy = 1;
/*  8081 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/*  8082 */     this.pnlEquipInfo.add(this.lblInfoSpecials, gridBagConstraints);
/*       */     
/*  8084 */     this.jSeparator14.setBorder(BorderFactory.createEtchedBorder());
/*  8085 */     gridBagConstraints = new GridBagConstraints();
/*  8086 */     gridBagConstraints.gridx = 0;
/*  8087 */     gridBagConstraints.gridy = 6;
/*  8088 */     gridBagConstraints.gridwidth = 0;
/*  8089 */     gridBagConstraints.fill = 2;
/*  8090 */     gridBagConstraints.insets = new Insets(4, 0, 4, 0);
/*  8091 */     this.pnlEquipInfo.add(this.jSeparator14, gridBagConstraints);
/*       */     
/*  8093 */     this.jLabel32.setText("Cost");
/*  8094 */     gridBagConstraints = new GridBagConstraints();
/*  8095 */     gridBagConstraints.gridx = 5;
/*  8096 */     gridBagConstraints.gridy = 4;
/*  8097 */     gridBagConstraints.gridwidth = 2;
/*  8098 */     gridBagConstraints.anchor = 13;
/*  8099 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8100 */     this.pnlEquipInfo.add(this.jLabel32, gridBagConstraints);
/*  8101 */     gridBagConstraints = new GridBagConstraints();
/*  8102 */     gridBagConstraints.gridx = 7;
/*  8103 */     gridBagConstraints.gridy = 4;
/*  8104 */     gridBagConstraints.gridwidth = 2;
/*  8105 */     gridBagConstraints.anchor = 17;
/*  8106 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/*  8107 */     this.pnlEquipInfo.add(this.lblInfoCost, gridBagConstraints);
/*       */     
/*  8109 */     this.jLabel34.setText("BV");
/*  8110 */     gridBagConstraints = new GridBagConstraints();
/*  8111 */     gridBagConstraints.gridx = 5;
/*  8112 */     gridBagConstraints.gridy = 5;
/*  8113 */     gridBagConstraints.gridwidth = 2;
/*  8114 */     gridBagConstraints.anchor = 13;
/*  8115 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8116 */     this.pnlEquipInfo.add(this.jLabel34, gridBagConstraints);
/*  8117 */     gridBagConstraints = new GridBagConstraints();
/*  8118 */     gridBagConstraints.gridx = 7;
/*  8119 */     gridBagConstraints.gridy = 5;
/*  8120 */     gridBagConstraints.gridwidth = 2;
/*  8121 */     gridBagConstraints.anchor = 17;
/*  8122 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/*  8123 */     this.pnlEquipInfo.add(this.lblInfoBV, gridBagConstraints);
/*       */     
/*  8125 */     this.jLabel33.setText("Mounting Restrictions");
/*  8126 */     gridBagConstraints = new GridBagConstraints();
/*  8127 */     gridBagConstraints.gridwidth = 2;
/*  8128 */     gridBagConstraints.anchor = 17;
/*  8129 */     gridBagConstraints.insets = new Insets(0, 0, 4, 3);
/*  8130 */     this.pnlEquipInfo.add(this.jLabel33, gridBagConstraints);
/*  8131 */     gridBagConstraints = new GridBagConstraints();
/*  8132 */     gridBagConstraints.gridx = 2;
/*  8133 */     gridBagConstraints.gridy = 7;
/*  8134 */     gridBagConstraints.gridwidth = 7;
/*  8135 */     gridBagConstraints.anchor = 17;
/*  8136 */     gridBagConstraints.insets = new Insets(0, 3, 4, 0);
/*  8137 */     this.pnlEquipInfo.add(this.lblInfoMountRestrict, gridBagConstraints);
/*       */     
/*  8139 */     this.jLabel55.setText("Rules Level");
/*  8140 */     gridBagConstraints = new GridBagConstraints();
/*  8141 */     gridBagConstraints.gridx = 5;
/*  8142 */     gridBagConstraints.gridy = 3;
/*  8143 */     gridBagConstraints.gridwidth = 2;
/*  8144 */     gridBagConstraints.anchor = 13;
/*  8145 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8146 */     this.pnlEquipInfo.add(this.jLabel55, gridBagConstraints);
/*  8147 */     gridBagConstraints = new GridBagConstraints();
/*  8148 */     gridBagConstraints.gridx = 7;
/*  8149 */     gridBagConstraints.gridy = 3;
/*  8150 */     gridBagConstraints.gridwidth = 2;
/*  8151 */     gridBagConstraints.anchor = 17;
/*  8152 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/*  8153 */     this.pnlEquipInfo.add(this.lblInfoRulesLevel, gridBagConstraints);
/*       */     
/*  8155 */     this.pnlEquipment.add(this.pnlEquipInfo, new AbsoluteConstraints(10, 320, 610, -1));
/*       */     
/*  8157 */     this.pnlLACrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Left Arm", 2, 0));
/*  8158 */     this.pnlLACrits.setMaximumSize(new Dimension(114, 256));
/*  8159 */     this.pnlLACrits.setMinimumSize(new Dimension(114, 256));
/*  8160 */     this.pnlLACrits.setLayout(new GridBagLayout());
/*       */     
/*  8162 */     this.scrLACrits.setMinimumSize(new Dimension(105, 87));
/*  8163 */     this.scrLACrits.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8165 */     this.lstLACrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8166 */     this.lstLACrits.setModel(new javax.swing.AbstractListModel() {
/*  8167 */       String[] strings = { "Left Arm", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8168 */       public int getSize() { return this.strings.length; }
/*  8169 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8170 */     });
/*  8171 */     this.lstLACrits.setDragEnabled(true);
/*  8172 */     this.lstLACrits.setMaximumSize(new Dimension(98, 50));
/*  8173 */     this.lstLACrits.setMinimumSize(new Dimension(98, 50));
/*  8174 */     this.lstLACrits.setPreferredSize(new Dimension(98, 50));
/*  8175 */     this.lstLACrits.setVisibleRowCount(12);
/*  8176 */     this.lstLACrits.setTransferHandler(new thLATransferHandler(this, this.CurMech));
/*  8177 */     this.lstLACrits.setDropMode(javax.swing.DropMode.ON);
/*  8178 */     MouseListener mlLACrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8180 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8181 */           int index = frmMainWide.this.lstLACrits.locationToIndex(e.getPoint());
/*  8182 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLACrits();
/*  8183 */           if (!a[index].LocationLocked()) {
/*  8184 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8185 */               frmMainWide.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8187 */               frmMainWide.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8190 */           frmMainWide.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8194 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8195 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLACrits();
/*  8196 */           int index = frmMainWide.this.lstLACrits.locationToIndex(e.getPoint());
/*  8197 */           frmMainWide.this.CurItem = a[index];
/*  8198 */           frmMainWide.this.CurLocation = 4;
/*  8199 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8200 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8204 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8205 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLACrits();
/*  8206 */           int index = frmMainWide.this.lstLACrits.locationToIndex(e.getPoint());
/*  8207 */           frmMainWide.this.CurItem = a[index];
/*  8208 */           frmMainWide.this.CurLocation = 4;
/*  8209 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8210 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8212 */           int index = frmMainWide.this.lstLACrits.locationToIndex(e.getPoint());
/*  8213 */           frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetLACrits()[index];
/*  8214 */           frmMainWide.this.CurLocation = 4;
/*       */         }
/*       */       }
/*  8217 */     };
/*  8218 */     this.lstLACrits.addMouseListener(mlLACrits);
/*  8219 */     this.lstLACrits.setCellRenderer(this.Mechrender);
/*  8220 */     this.scrLACrits.setViewportView(this.lstLACrits);
/*       */     
/*  8222 */     gridBagConstraints = new GridBagConstraints();
/*  8223 */     gridBagConstraints.gridx = 0;
/*  8224 */     gridBagConstraints.gridy = 1;
/*  8225 */     this.pnlLACrits.add(this.scrLACrits, gridBagConstraints);
/*       */     
/*  8227 */     this.chkLALowerArm.setSelected(true);
/*  8228 */     this.chkLALowerArm.setText("Lower Arm");
/*  8229 */     this.chkLALowerArm.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8231 */         frmMainWide.this.chkLALowerArmActionPerformed(evt);
/*       */       }
/*  8233 */     });
/*  8234 */     gridBagConstraints = new GridBagConstraints();
/*  8235 */     gridBagConstraints.gridx = 0;
/*  8236 */     gridBagConstraints.gridy = 2;
/*  8237 */     gridBagConstraints.anchor = 17;
/*  8238 */     this.pnlLACrits.add(this.chkLALowerArm, gridBagConstraints);
/*       */     
/*  8240 */     this.chkLAHand.setSelected(true);
/*  8241 */     this.chkLAHand.setText("Hand");
/*  8242 */     this.chkLAHand.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8244 */         frmMainWide.this.chkLAHandActionPerformed(evt);
/*       */       }
/*  8246 */     });
/*  8247 */     gridBagConstraints = new GridBagConstraints();
/*  8248 */     gridBagConstraints.gridx = 0;
/*  8249 */     gridBagConstraints.gridy = 3;
/*  8250 */     gridBagConstraints.anchor = 17;
/*  8251 */     this.pnlLACrits.add(this.chkLAHand, gridBagConstraints);
/*       */     
/*  8253 */     this.chkLACASE2.setText("C.A.S.E. II");
/*  8254 */     this.chkLACASE2.setEnabled(false);
/*  8255 */     this.chkLACASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8257 */         frmMainWide.this.chkLACASE2ActionPerformed(evt);
/*       */       }
/*  8259 */     });
/*  8260 */     gridBagConstraints = new GridBagConstraints();
/*  8261 */     gridBagConstraints.gridx = 0;
/*  8262 */     gridBagConstraints.gridy = 4;
/*  8263 */     gridBagConstraints.anchor = 17;
/*  8264 */     this.pnlLACrits.add(this.chkLACASE2, gridBagConstraints);
/*       */     
/*  8266 */     this.chkLAAES.setText("A.E.S.");
/*  8267 */     this.chkLAAES.setEnabled(false);
/*  8268 */     this.chkLAAES.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8270 */         frmMainWide.this.chkLAAESActionPerformed(evt);
/*       */       }
/*  8272 */     });
/*  8273 */     gridBagConstraints = new GridBagConstraints();
/*  8274 */     gridBagConstraints.anchor = 17;
/*  8275 */     this.pnlLACrits.add(this.chkLAAES, gridBagConstraints);
/*       */     
/*  8277 */     this.pnlEquipment.add(this.pnlLACrits, new AbsoluteConstraints(620, 100, 117, -1));
/*       */     
/*  8279 */     this.pnlLTCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Left Torso", 2, 0));
/*  8280 */     this.pnlLTCrits.setMaximumSize(new Dimension(114, 235));
/*  8281 */     this.pnlLTCrits.setMinimumSize(new Dimension(114, 235));
/*  8282 */     this.pnlLTCrits.setPreferredSize(new Dimension(257, 232));
/*  8283 */     this.pnlLTCrits.setLayout(new GridBagLayout());
/*       */     
/*  8285 */     this.chkLTCASE.setText("C.A.S.E.");
/*  8286 */     this.chkLTCASE.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8288 */         frmMainWide.this.chkLTCASEActionPerformed(evt);
/*       */       }
/*  8290 */     });
/*  8291 */     gridBagConstraints = new GridBagConstraints();
/*  8292 */     gridBagConstraints.gridx = 0;
/*  8293 */     gridBagConstraints.gridy = 2;
/*  8294 */     gridBagConstraints.anchor = 17;
/*  8295 */     this.pnlLTCrits.add(this.chkLTCASE, gridBagConstraints);
/*       */     
/*  8297 */     this.jScrollPane12.setMinimumSize(new Dimension(105, 183));
/*  8298 */     this.jScrollPane12.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8300 */     this.lstLTCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8301 */     this.lstLTCrits.setModel(new javax.swing.AbstractListModel() {
/*  8302 */       String[] strings = { "Left Torso", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8303 */       public int getSize() { return this.strings.length; }
/*  8304 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8305 */     });
/*  8306 */     this.lstLTCrits.setDragEnabled(true);
/*  8307 */     this.lstLTCrits.setMaximumSize(new Dimension(98, 50));
/*  8308 */     this.lstLTCrits.setMinimumSize(new Dimension(98, 50));
/*  8309 */     this.lstLTCrits.setPreferredSize(new Dimension(98, 50));
/*  8310 */     this.lstLTCrits.setVisibleRowCount(12);
/*  8311 */     this.lstLTCrits.setTransferHandler(new thLTTransferHandler(this, this.CurMech));
/*  8312 */     this.lstLTCrits.setDropMode(javax.swing.DropMode.ON);
/*  8313 */     MouseListener mlLTCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8315 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8316 */           int index = frmMainWide.this.lstLTCrits.locationToIndex(e.getPoint());
/*  8317 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLTCrits();
/*  8318 */           if (!a[index].LocationLocked()) {
/*  8319 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8320 */               frmMainWide.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8322 */               frmMainWide.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8325 */           frmMainWide.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8329 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8330 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLTCrits();
/*  8331 */           int index = frmMainWide.this.lstLTCrits.locationToIndex(e.getPoint());
/*  8332 */           frmMainWide.this.CurItem = a[index];
/*  8333 */           frmMainWide.this.CurLocation = 2;
/*  8334 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8335 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8339 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8340 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLTCrits();
/*  8341 */           int index = frmMainWide.this.lstLTCrits.locationToIndex(e.getPoint());
/*  8342 */           frmMainWide.this.CurItem = a[index];
/*  8343 */           frmMainWide.this.CurLocation = 2;
/*  8344 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8345 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8347 */           int index = frmMainWide.this.lstLTCrits.locationToIndex(e.getPoint());
/*  8348 */           frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetLTCrits()[index];
/*  8349 */           frmMainWide.this.CurLocation = 2;
/*       */         }
/*       */       }
/*  8352 */     };
/*  8353 */     this.lstLTCrits.addMouseListener(mlLTCrits);
/*  8354 */     this.lstLTCrits.setCellRenderer(this.Mechrender);
/*  8355 */     this.jScrollPane12.setViewportView(this.lstLTCrits);
/*       */     
/*  8357 */     gridBagConstraints = new GridBagConstraints();
/*  8358 */     gridBagConstraints.gridx = 0;
/*  8359 */     gridBagConstraints.gridy = 1;
/*  8360 */     this.pnlLTCrits.add(this.jScrollPane12, gridBagConstraints);
/*       */     
/*  8362 */     this.chkLTCASE2.setText("C.A.S.E. II");
/*  8363 */     this.chkLTCASE2.setEnabled(false);
/*  8364 */     this.chkLTCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8366 */         frmMainWide.this.chkLTCASE2ActionPerformed(evt);
/*       */       }
/*  8368 */     });
/*  8369 */     gridBagConstraints = new GridBagConstraints();
/*  8370 */     gridBagConstraints.gridx = 0;
/*  8371 */     gridBagConstraints.gridy = 3;
/*  8372 */     gridBagConstraints.anchor = 17;
/*  8373 */     this.pnlLTCrits.add(this.chkLTCASE2, gridBagConstraints);
/*       */     
/*  8375 */     this.chkLTTurret.setText("Turret");
/*  8376 */     this.chkLTTurret.setEnabled(false);
/*  8377 */     this.chkLTTurret.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8379 */         frmMainWide.this.chkLTTurretActionPerformed(evt);
/*       */       }
/*  8381 */     });
/*  8382 */     gridBagConstraints = new GridBagConstraints();
/*  8383 */     gridBagConstraints.gridx = 0;
/*  8384 */     gridBagConstraints.gridy = 0;
/*  8385 */     gridBagConstraints.anchor = 17;
/*  8386 */     this.pnlLTCrits.add(this.chkLTTurret, gridBagConstraints);
/*       */     
/*  8388 */     this.pnlEquipment.add(this.pnlLTCrits, new AbsoluteConstraints(740, 60, 117, 270));
/*       */     
/*  8390 */     this.pnlHDCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Head", 2, 0));
/*  8391 */     this.pnlHDCrits.setMaximumSize(new Dimension(116, 120));
/*  8392 */     this.pnlHDCrits.setMinimumSize(new Dimension(116, 120));
/*  8393 */     this.pnlHDCrits.setLayout(new GridBagLayout());
/*       */     
/*  8395 */     this.chkHDTurret.setText("Turret");
/*  8396 */     this.chkHDTurret.setEnabled(false);
/*  8397 */     this.chkHDTurret.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8399 */         frmMainWide.this.chkHDTurretActionPerformed(evt);
/*       */       }
/*  8401 */     });
/*  8402 */     gridBagConstraints = new GridBagConstraints();
/*  8403 */     gridBagConstraints.gridx = 0;
/*  8404 */     gridBagConstraints.gridy = 0;
/*  8405 */     gridBagConstraints.anchor = 17;
/*  8406 */     this.pnlHDCrits.add(this.chkHDTurret, gridBagConstraints);
/*       */     
/*  8408 */     this.chkHDCASE2.setText("C.A.S.E. II");
/*  8409 */     this.chkHDCASE2.setEnabled(false);
/*  8410 */     this.chkHDCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8412 */         frmMainWide.this.chkHDCASE2ActionPerformed(evt);
/*       */       }
/*  8414 */     });
/*  8415 */     gridBagConstraints = new GridBagConstraints();
/*  8416 */     gridBagConstraints.gridx = 0;
/*  8417 */     gridBagConstraints.gridy = 2;
/*  8418 */     gridBagConstraints.anchor = 17;
/*  8419 */     this.pnlHDCrits.add(this.chkHDCASE2, gridBagConstraints);
/*       */     
/*  8421 */     this.jScrollPane10.setPreferredSize(new Dimension(105, 87));
/*       */     
/*  8423 */     this.lstHDCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8424 */     this.lstHDCrits.setModel(new javax.swing.AbstractListModel() {
/*  8425 */       String[] strings = { "Head", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" };
/*  8426 */       public int getSize() { return this.strings.length; }
/*  8427 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8428 */     });
/*  8429 */     this.lstHDCrits.setDragEnabled(true);
/*  8430 */     this.lstHDCrits.setMaximumSize(new Dimension(98, 50));
/*  8431 */     this.lstHDCrits.setMinimumSize(new Dimension(98, 50));
/*  8432 */     this.lstHDCrits.setPreferredSize(new Dimension(98, 50));
/*  8433 */     this.lstHDCrits.setVisibleRowCount(6);
/*  8434 */     this.lstHDCrits.setTransferHandler(new thHDTransferHandler(this, this.CurMech));
/*  8435 */     this.lstHDCrits.setDropMode(javax.swing.DropMode.ON);
/*  8436 */     MouseListener mlHDCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8438 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8439 */           int index = frmMainWide.this.lstHDCrits.locationToIndex(e.getPoint());
/*  8440 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetHDCrits();
/*  8441 */           if (!a[index].LocationLocked()) {
/*  8442 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8443 */               frmMainWide.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8445 */               frmMainWide.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8448 */           frmMainWide.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8452 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8453 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetHDCrits();
/*  8454 */           int index = frmMainWide.this.lstHDCrits.locationToIndex(e.getPoint());
/*  8455 */           frmMainWide.this.CurItem = a[index];
/*  8456 */           frmMainWide.this.CurLocation = 0;
/*  8457 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8458 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8462 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8463 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetHDCrits();
/*  8464 */           int index = frmMainWide.this.lstHDCrits.locationToIndex(e.getPoint());
/*  8465 */           frmMainWide.this.CurItem = a[index];
/*  8466 */           frmMainWide.this.CurLocation = 0;
/*  8467 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8468 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8470 */           int index = frmMainWide.this.lstHDCrits.locationToIndex(e.getPoint());
/*  8471 */           frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetHDCrits()[index];
/*  8472 */           frmMainWide.this.CurLocation = 0;
/*       */         }
/*       */       }
/*  8475 */     };
/*  8476 */     this.lstHDCrits.addMouseListener(mlHDCrits);
/*  8477 */     this.lstHDCrits.setCellRenderer(this.Mechrender);
/*  8478 */     this.jScrollPane10.setViewportView(this.lstHDCrits);
/*       */     
/*  8480 */     gridBagConstraints = new GridBagConstraints();
/*  8481 */     gridBagConstraints.gridx = 0;
/*  8482 */     gridBagConstraints.gridy = 1;
/*  8483 */     gridBagConstraints.fill = 1;
/*  8484 */     this.pnlHDCrits.add(this.jScrollPane10, gridBagConstraints);
/*       */     
/*  8486 */     this.pnlEquipment.add(this.pnlHDCrits, new AbsoluteConstraints(860, 10, 117, 165));
/*       */     
/*  8488 */     this.pnlRTCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Right Torso", 2, 0));
/*  8489 */     this.pnlRTCrits.setMaximumSize(new Dimension(114, 233));
/*  8490 */     this.pnlRTCrits.setMinimumSize(new Dimension(114, 233));
/*  8491 */     this.pnlRTCrits.setLayout(new GridBagLayout());
/*       */     
/*  8493 */     this.jScrollPane13.setMinimumSize(new Dimension(105, 183));
/*  8494 */     this.jScrollPane13.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8496 */     this.lstRTCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8497 */     this.lstRTCrits.setModel(new javax.swing.AbstractListModel() {
/*  8498 */       String[] strings = { "Right Torso", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8499 */       public int getSize() { return this.strings.length; }
/*  8500 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8501 */     });
/*  8502 */     this.lstRTCrits.setDragEnabled(true);
/*  8503 */     this.lstRTCrits.setMaximumSize(new Dimension(98, 50));
/*  8504 */     this.lstRTCrits.setMinimumSize(new Dimension(98, 50));
/*  8505 */     this.lstRTCrits.setPreferredSize(new Dimension(98, 50));
/*  8506 */     this.lstRTCrits.setVisibleRowCount(12);
/*  8507 */     this.lstRTCrits.setTransferHandler(new thRTTransferHandler(this, this.CurMech));
/*  8508 */     this.lstRTCrits.setDropMode(javax.swing.DropMode.ON);
/*  8509 */     MouseListener mlRTCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8511 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8512 */           int index = frmMainWide.this.lstRTCrits.locationToIndex(e.getPoint());
/*  8513 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRTCrits();
/*  8514 */           if (!a[index].LocationLocked()) {
/*  8515 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8516 */               frmMainWide.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8518 */               frmMainWide.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8521 */           frmMainWide.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8525 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8526 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRTCrits();
/*  8527 */           int index = frmMainWide.this.lstRTCrits.locationToIndex(e.getPoint());
/*  8528 */           frmMainWide.this.CurItem = a[index];
/*  8529 */           frmMainWide.this.CurLocation = 3;
/*  8530 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8531 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8535 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8536 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRTCrits();
/*  8537 */           int index = frmMainWide.this.lstRTCrits.locationToIndex(e.getPoint());
/*  8538 */           frmMainWide.this.CurItem = a[index];
/*  8539 */           frmMainWide.this.CurLocation = 3;
/*  8540 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8541 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8543 */           int index = frmMainWide.this.lstRTCrits.locationToIndex(e.getPoint());
/*  8544 */           frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetRTCrits()[index];
/*  8545 */           frmMainWide.this.CurLocation = 3;
/*       */         }
/*       */       }
/*  8548 */     };
/*  8549 */     this.lstRTCrits.addMouseListener(mlRTCrits);
/*  8550 */     this.lstRTCrits.setCellRenderer(this.Mechrender);
/*  8551 */     this.jScrollPane13.setViewportView(this.lstRTCrits);
/*       */     
/*  8553 */     gridBagConstraints = new GridBagConstraints();
/*  8554 */     gridBagConstraints.gridx = 0;
/*  8555 */     gridBagConstraints.gridy = 1;
/*  8556 */     this.pnlRTCrits.add(this.jScrollPane13, gridBagConstraints);
/*       */     
/*  8558 */     this.chkRTCASE.setText("C.A.S.E.");
/*  8559 */     this.chkRTCASE.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8561 */         frmMainWide.this.chkRTCASEActionPerformed(evt);
/*       */       }
/*  8563 */     });
/*  8564 */     gridBagConstraints = new GridBagConstraints();
/*  8565 */     gridBagConstraints.gridx = 0;
/*  8566 */     gridBagConstraints.gridy = 2;
/*  8567 */     gridBagConstraints.anchor = 17;
/*  8568 */     this.pnlRTCrits.add(this.chkRTCASE, gridBagConstraints);
/*       */     
/*  8570 */     this.chkRTCASE2.setText("C.A.S.E. II");
/*  8571 */     this.chkRTCASE2.setEnabled(false);
/*  8572 */     this.chkRTCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8574 */         frmMainWide.this.chkRTCASE2ActionPerformed(evt);
/*       */       }
/*  8576 */     });
/*  8577 */     gridBagConstraints = new GridBagConstraints();
/*  8578 */     gridBagConstraints.gridx = 0;
/*  8579 */     gridBagConstraints.gridy = 3;
/*  8580 */     gridBagConstraints.anchor = 17;
/*  8581 */     this.pnlRTCrits.add(this.chkRTCASE2, gridBagConstraints);
/*       */     
/*  8583 */     this.chkRTTurret.setText("Turret");
/*  8584 */     this.chkRTTurret.setEnabled(false);
/*  8585 */     this.chkRTTurret.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8587 */         frmMainWide.this.chkRTTurretActionPerformed(evt);
/*       */       }
/*  8589 */     });
/*  8590 */     gridBagConstraints = new GridBagConstraints();
/*  8591 */     gridBagConstraints.gridx = 0;
/*  8592 */     gridBagConstraints.gridy = 0;
/*  8593 */     gridBagConstraints.anchor = 17;
/*  8594 */     this.pnlRTCrits.add(this.chkRTTurret, gridBagConstraints);
/*       */     
/*  8596 */     this.pnlEquipment.add(this.pnlRTCrits, new AbsoluteConstraints(980, 60, 117, 270));
/*       */     
/*  8598 */     this.pnlCTCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Center Torso", 2, 0));
/*  8599 */     this.pnlCTCrits.setMaximumSize(new Dimension(114, 233));
/*  8600 */     this.pnlCTCrits.setMinimumSize(new Dimension(114, 233));
/*  8601 */     this.pnlCTCrits.setLayout(new GridBagLayout());
/*       */     
/*  8603 */     this.jScrollPane11.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8605 */     this.lstCTCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8606 */     this.lstCTCrits.setModel(new javax.swing.AbstractListModel() {
/*  8607 */       String[] strings = { "Center Torso", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8608 */       public int getSize() { return this.strings.length; }
/*  8609 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8610 */     });
/*  8611 */     this.lstCTCrits.setDragEnabled(true);
/*  8612 */     this.lstCTCrits.setMaximumSize(new Dimension(98, 50));
/*  8613 */     this.lstCTCrits.setMinimumSize(new Dimension(98, 50));
/*  8614 */     this.lstCTCrits.setPreferredSize(new Dimension(98, 50));
/*  8615 */     this.lstCTCrits.setVisibleRowCount(12);
/*  8616 */     this.lstCTCrits.setTransferHandler(new thCTTransferHandler(this, this.CurMech));
/*  8617 */     this.lstCTCrits.setDropMode(javax.swing.DropMode.ON);
/*  8618 */     MouseListener mlCTCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8620 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8621 */           int index = frmMainWide.this.lstCTCrits.locationToIndex(e.getPoint());
/*  8622 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetCTCrits();
/*  8623 */           if (!a[index].LocationLocked()) {
/*  8624 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8625 */               frmMainWide.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8627 */               frmMainWide.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8630 */           frmMainWide.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8634 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8635 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetCTCrits();
/*  8636 */           int index = frmMainWide.this.lstCTCrits.locationToIndex(e.getPoint());
/*  8637 */           frmMainWide.this.CurItem = a[index];
/*  8638 */           frmMainWide.this.CurLocation = 1;
/*  8639 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8640 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8644 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8645 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetCTCrits();
/*  8646 */           int index = frmMainWide.this.lstCTCrits.locationToIndex(e.getPoint());
/*  8647 */           frmMainWide.this.CurItem = a[index];
/*  8648 */           frmMainWide.this.CurLocation = 1;
/*  8649 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8650 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8652 */           int index = frmMainWide.this.lstCTCrits.locationToIndex(e.getPoint());
/*  8653 */           frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetCTCrits()[index];
/*  8654 */           frmMainWide.this.CurLocation = 1;
/*       */         }
/*       */       }
/*  8657 */     };
/*  8658 */     this.lstCTCrits.addMouseListener(mlCTCrits);
/*  8659 */     this.lstCTCrits.setCellRenderer(this.Mechrender);
/*  8660 */     this.jScrollPane11.setViewportView(this.lstCTCrits);
/*       */     
/*  8662 */     this.pnlCTCrits.add(this.jScrollPane11, new GridBagConstraints());
/*       */     
/*  8664 */     this.chkCTCASE.setText("C.A.S.E.");
/*  8665 */     this.chkCTCASE.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8667 */         frmMainWide.this.chkCTCASEActionPerformed(evt);
/*       */       }
/*  8669 */     });
/*  8670 */     gridBagConstraints = new GridBagConstraints();
/*  8671 */     gridBagConstraints.gridx = 0;
/*  8672 */     gridBagConstraints.gridy = 1;
/*  8673 */     gridBagConstraints.anchor = 17;
/*  8674 */     this.pnlCTCrits.add(this.chkCTCASE, gridBagConstraints);
/*       */     
/*  8676 */     this.chkCTCASE2.setText("C.A.S.E. II");
/*  8677 */     this.chkCTCASE2.setEnabled(false);
/*  8678 */     this.chkCTCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8680 */         frmMainWide.this.chkCTCASE2ActionPerformed(evt);
/*       */       }
/*  8682 */     });
/*  8683 */     gridBagConstraints = new GridBagConstraints();
/*  8684 */     gridBagConstraints.gridx = 0;
/*  8685 */     gridBagConstraints.gridy = 2;
/*  8686 */     gridBagConstraints.anchor = 17;
/*  8687 */     this.pnlCTCrits.add(this.chkCTCASE2, gridBagConstraints);
/*       */     
/*  8689 */     this.pnlEquipment.add(this.pnlCTCrits, new AbsoluteConstraints(860, 180, 117, -1));
/*       */     
/*  8691 */     this.pnlLLCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Left Leg", 2, 0));
/*  8692 */     this.pnlLLCrits.setMaximumSize(new Dimension(116, 120));
/*  8693 */     this.pnlLLCrits.setMinimumSize(new Dimension(116, 120));
/*  8694 */     this.pnlLLCrits.setLayout(new GridBagLayout());
/*       */     
/*  8696 */     this.jScrollPane16.setMinimumSize(new Dimension(105, 87));
/*  8697 */     this.jScrollPane16.setPreferredSize(new Dimension(105, 87));
/*       */     
/*  8699 */     this.lstLLCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8700 */     this.lstLLCrits.setModel(new javax.swing.AbstractListModel() {
/*  8701 */       String[] strings = { "Left Leg", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" };
/*  8702 */       public int getSize() { return this.strings.length; }
/*  8703 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8704 */     });
/*  8705 */     this.lstLLCrits.setDragEnabled(true);
/*  8706 */     this.lstLLCrits.setMaximumSize(new Dimension(98, 50));
/*  8707 */     this.lstLLCrits.setMinimumSize(new Dimension(98, 50));
/*  8708 */     this.lstLLCrits.setPreferredSize(new Dimension(98, 50));
/*  8709 */     this.lstLLCrits.setVisibleRowCount(6);
/*  8710 */     this.lstLLCrits.setTransferHandler(new thLLTransferHandler(this, this.CurMech));
/*  8711 */     this.lstLLCrits.setDropMode(javax.swing.DropMode.ON);
/*  8712 */     MouseListener mlLLCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8714 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8715 */           int index = frmMainWide.this.lstLLCrits.locationToIndex(e.getPoint());
/*  8716 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLLCrits();
/*  8717 */           if (!a[index].LocationLocked()) {
/*  8718 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8719 */               frmMainWide.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8721 */               frmMainWide.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8724 */           frmMainWide.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8728 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8729 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLLCrits();
/*  8730 */           int index = frmMainWide.this.lstLLCrits.locationToIndex(e.getPoint());
/*  8731 */           frmMainWide.this.CurItem = a[index];
/*  8732 */           frmMainWide.this.CurLocation = 6;
/*  8733 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8734 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8738 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8739 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetLLCrits();
/*  8740 */           int index = frmMainWide.this.lstLLCrits.locationToIndex(e.getPoint());
/*  8741 */           frmMainWide.this.CurItem = a[index];
/*  8742 */           frmMainWide.this.CurLocation = 6;
/*  8743 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8744 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8746 */           int index = frmMainWide.this.lstLLCrits.locationToIndex(e.getPoint());
/*  8747 */           frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetLLCrits()[index];
/*  8748 */           frmMainWide.this.CurLocation = 6;
/*       */         }
/*       */       }
/*  8751 */     };
/*  8752 */     this.lstLLCrits.addMouseListener(mlLLCrits);
/*  8753 */     this.lstLLCrits.setCellRenderer(this.Mechrender);
/*  8754 */     this.jScrollPane16.setViewportView(this.lstLLCrits);
/*       */     
/*  8756 */     this.pnlLLCrits.add(this.jScrollPane16, new GridBagConstraints());
/*       */     
/*  8758 */     this.chkLLCASE2.setText("C.A.S.E. II");
/*  8759 */     this.chkLLCASE2.setEnabled(false);
/*  8760 */     this.chkLLCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8762 */         frmMainWide.this.chkLLCASE2ActionPerformed(evt);
/*       */       }
/*  8764 */     });
/*  8765 */     gridBagConstraints = new GridBagConstraints();
/*  8766 */     gridBagConstraints.gridx = 0;
/*  8767 */     gridBagConstraints.gridy = 1;
/*  8768 */     gridBagConstraints.anchor = 17;
/*  8769 */     this.pnlLLCrits.add(this.chkLLCASE2, gridBagConstraints);
/*       */     
/*  8771 */     this.pnlEquipment.add(this.pnlLLCrits, new AbsoluteConstraints(740, 330, 117, -1));
/*       */     
/*  8773 */     this.pnlRLCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Right Leg", 2, 0));
/*  8774 */     this.pnlRLCrits.setMaximumSize(new Dimension(116, 120));
/*  8775 */     this.pnlRLCrits.setMinimumSize(new Dimension(116, 120));
/*  8776 */     this.pnlRLCrits.setLayout(new GridBagLayout());
/*       */     
/*  8778 */     this.jScrollPane17.setMinimumSize(new Dimension(105, 87));
/*  8779 */     this.jScrollPane17.setPreferredSize(new Dimension(105, 87));
/*       */     
/*  8781 */     this.lstRLCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8782 */     this.lstRLCrits.setModel(new javax.swing.AbstractListModel() {
/*  8783 */       String[] strings = { "Right Leg", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" };
/*  8784 */       public int getSize() { return this.strings.length; }
/*  8785 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8786 */     });
/*  8787 */     this.lstRLCrits.setDragEnabled(true);
/*  8788 */     this.lstRLCrits.setMaximumSize(new Dimension(98, 50));
/*  8789 */     this.lstRLCrits.setMinimumSize(new Dimension(98, 50));
/*  8790 */     this.lstRLCrits.setPreferredSize(new Dimension(98, 50));
/*  8791 */     this.lstRLCrits.setVisibleRowCount(6);
/*  8792 */     this.lstRLCrits.setTransferHandler(new thRLTransferHandler(this, this.CurMech));
/*  8793 */     this.lstRLCrits.setDropMode(javax.swing.DropMode.ON);
/*  8794 */     MouseListener mlRLCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8796 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8797 */           int index = frmMainWide.this.lstRLCrits.locationToIndex(e.getPoint());
/*  8798 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRLCrits();
/*  8799 */           if (!a[index].LocationLocked()) {
/*  8800 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8801 */               frmMainWide.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8803 */               frmMainWide.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8806 */           frmMainWide.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8810 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8811 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRLCrits();
/*  8812 */           int index = frmMainWide.this.lstRLCrits.locationToIndex(e.getPoint());
/*  8813 */           frmMainWide.this.CurItem = a[index];
/*  8814 */           frmMainWide.this.CurLocation = 7;
/*  8815 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8816 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8820 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8821 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRLCrits();
/*  8822 */           int index = frmMainWide.this.lstRLCrits.locationToIndex(e.getPoint());
/*  8823 */           frmMainWide.this.CurItem = a[index];
/*  8824 */           frmMainWide.this.CurLocation = 7;
/*  8825 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8826 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8828 */           int index = frmMainWide.this.lstRLCrits.locationToIndex(e.getPoint());
/*  8829 */           frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetRLCrits()[index];
/*  8830 */           frmMainWide.this.CurLocation = 7;
/*       */         }
/*       */       }
/*  8833 */     };
/*  8834 */     this.lstRLCrits.addMouseListener(mlRLCrits);
/*  8835 */     this.lstRLCrits.setCellRenderer(this.Mechrender);
/*  8836 */     this.jScrollPane17.setViewportView(this.lstRLCrits);
/*       */     
/*  8838 */     this.pnlRLCrits.add(this.jScrollPane17, new GridBagConstraints());
/*       */     
/*  8840 */     this.chkRLCASE2.setText("C.A.S.E. II");
/*  8841 */     this.chkRLCASE2.setEnabled(false);
/*  8842 */     this.chkRLCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8844 */         frmMainWide.this.chkRLCASE2ActionPerformed(evt);
/*       */       }
/*  8846 */     });
/*  8847 */     gridBagConstraints = new GridBagConstraints();
/*  8848 */     gridBagConstraints.gridx = 0;
/*  8849 */     gridBagConstraints.gridy = 1;
/*  8850 */     gridBagConstraints.anchor = 17;
/*  8851 */     this.pnlRLCrits.add(this.chkRLCASE2, gridBagConstraints);
/*       */     
/*  8853 */     this.pnlEquipment.add(this.pnlRLCrits, new AbsoluteConstraints(980, 330, 117, -1));
/*       */     
/*  8855 */     this.jPanel5.setLayout(new GridBagLayout());
/*       */     
/*  8857 */     this.jLabel59.setText("<--");
/*  8858 */     this.jPanel5.add(this.jLabel59, new GridBagConstraints());
/*       */     
/*  8860 */     this.chkLegAES.setText("A.E.S.");
/*  8861 */     this.chkLegAES.setEnabled(false);
/*  8862 */     this.chkLegAES.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8864 */         frmMainWide.this.chkLegAESActionPerformed(evt);
/*       */       }
/*  8866 */     });
/*  8867 */     this.jPanel5.add(this.chkLegAES, new GridBagConstraints());
/*       */     
/*  8869 */     this.jLabel61.setText("-->");
/*  8870 */     this.jPanel5.add(this.jLabel61, new GridBagConstraints());
/*       */     
/*  8872 */     this.pnlEquipment.add(this.jPanel5, new AbsoluteConstraints(860, 430, 115, 30));
/*       */     
/*  8874 */     this.pnlRACrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Right Arm", 2, 0));
/*  8875 */     this.pnlRACrits.setMaximumSize(new Dimension(114, 256));
/*  8876 */     this.pnlRACrits.setMinimumSize(new Dimension(114, 256));
/*  8877 */     this.pnlRACrits.setLayout(new GridBagLayout());
/*       */     
/*  8879 */     this.scrRACrits.setMinimumSize(new Dimension(105, 87));
/*  8880 */     this.scrRACrits.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8882 */     this.lstRACrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8883 */     this.lstRACrits.setModel(new javax.swing.AbstractListModel() {
/*  8884 */       String[] strings = { "Right Arm", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8885 */       public int getSize() { return this.strings.length; }
/*  8886 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8887 */     });
/*  8888 */     this.lstRACrits.setDragEnabled(true);
/*  8889 */     this.lstRACrits.setMaximumSize(new Dimension(98, 50));
/*  8890 */     this.lstRACrits.setMinimumSize(new Dimension(98, 50));
/*  8891 */     this.lstRACrits.setPreferredSize(new Dimension(98, 50));
/*  8892 */     this.lstRACrits.setVisibleRowCount(12);
/*  8893 */     this.lstRACrits.setTransferHandler(new thRATransferHandler(this, this.CurMech));
/*  8894 */     this.lstRACrits.setDropMode(javax.swing.DropMode.ON);
/*  8895 */     MouseListener mlRACrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8897 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8898 */           int index = frmMainWide.this.lstRACrits.locationToIndex(e.getPoint());
/*  8899 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRACrits();
/*  8900 */           if (!a[index].LocationLocked()) {
/*  8901 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8902 */               frmMainWide.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8904 */               frmMainWide.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8907 */           frmMainWide.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8911 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8912 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRACrits();
/*  8913 */           int index = frmMainWide.this.lstRACrits.locationToIndex(e.getPoint());
/*  8914 */           frmMainWide.this.CurItem = a[index];
/*  8915 */           frmMainWide.this.CurLocation = 5;
/*  8916 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8917 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8921 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8922 */           abPlaceable[] a = frmMainWide.this.CurMech.GetLoadout().GetRACrits();
/*  8923 */           int index = frmMainWide.this.lstRACrits.locationToIndex(e.getPoint());
/*  8924 */           frmMainWide.this.CurItem = a[index];
/*  8925 */           frmMainWide.this.CurLocation = 5;
/*  8926 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  8927 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8929 */           int index = frmMainWide.this.lstRACrits.locationToIndex(e.getPoint());
/*  8930 */           frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetRACrits()[index];
/*  8931 */           frmMainWide.this.CurLocation = 5;
/*       */         }
/*       */       }
/*  8934 */     };
/*  8935 */     this.lstRACrits.addMouseListener(mlRACrits);
/*  8936 */     this.lstRACrits.setCellRenderer(this.Mechrender);
/*  8937 */     this.scrRACrits.setViewportView(this.lstRACrits);
/*       */     
/*  8939 */     gridBagConstraints = new GridBagConstraints();
/*  8940 */     gridBagConstraints.gridx = 0;
/*  8941 */     gridBagConstraints.gridy = 1;
/*  8942 */     this.pnlRACrits.add(this.scrRACrits, gridBagConstraints);
/*       */     
/*  8944 */     this.chkRALowerArm.setSelected(true);
/*  8945 */     this.chkRALowerArm.setText("Lower Arm");
/*  8946 */     this.chkRALowerArm.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8948 */         frmMainWide.this.chkRALowerArmActionPerformed(evt);
/*       */       }
/*  8950 */     });
/*  8951 */     gridBagConstraints = new GridBagConstraints();
/*  8952 */     gridBagConstraints.gridx = 0;
/*  8953 */     gridBagConstraints.gridy = 2;
/*  8954 */     gridBagConstraints.anchor = 17;
/*  8955 */     this.pnlRACrits.add(this.chkRALowerArm, gridBagConstraints);
/*       */     
/*  8957 */     this.chkRAHand.setSelected(true);
/*  8958 */     this.chkRAHand.setText("Hand");
/*  8959 */     this.chkRAHand.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8961 */         frmMainWide.this.chkRAHandActionPerformed(evt);
/*       */       }
/*  8963 */     });
/*  8964 */     gridBagConstraints = new GridBagConstraints();
/*  8965 */     gridBagConstraints.gridx = 0;
/*  8966 */     gridBagConstraints.gridy = 3;
/*  8967 */     gridBagConstraints.anchor = 17;
/*  8968 */     this.pnlRACrits.add(this.chkRAHand, gridBagConstraints);
/*       */     
/*  8970 */     this.chkRACASE2.setText("C.A.S.E. II");
/*  8971 */     this.chkRACASE2.setEnabled(false);
/*  8972 */     this.chkRACASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8974 */         frmMainWide.this.chkRACASE2ActionPerformed(evt);
/*       */       }
/*  8976 */     });
/*  8977 */     gridBagConstraints = new GridBagConstraints();
/*  8978 */     gridBagConstraints.gridx = 0;
/*  8979 */     gridBagConstraints.gridy = 4;
/*  8980 */     gridBagConstraints.anchor = 17;
/*  8981 */     this.pnlRACrits.add(this.chkRACASE2, gridBagConstraints);
/*       */     
/*  8983 */     this.chkRAAES.setText("A.E.S.");
/*  8984 */     this.chkRAAES.setEnabled(false);
/*  8985 */     this.chkRAAES.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8987 */         frmMainWide.this.chkRAAESActionPerformed(evt);
/*       */       }
/*  8989 */     });
/*  8990 */     gridBagConstraints = new GridBagConstraints();
/*  8991 */     gridBagConstraints.anchor = 17;
/*  8992 */     this.pnlRACrits.add(this.chkRAAES, gridBagConstraints);
/*       */     
/*  8994 */     this.pnlEquipment.add(this.pnlRACrits, new AbsoluteConstraints(1100, 100, 117, -1));
/*       */     
/*  8996 */     this.pnlEquipmentToPlace.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Equipment to Place", 2, 0));
/*  8997 */     this.pnlEquipmentToPlace.setMaximumSize(new Dimension(146, 330));
/*  8998 */     this.pnlEquipmentToPlace.setMinimumSize(new Dimension(146, 330));
/*  8999 */     this.pnlEquipmentToPlace.setLayout(new javax.swing.BoxLayout(this.pnlEquipmentToPlace, 3));
/*       */     
/*  9001 */     this.jScrollPane18.setHorizontalScrollBarPolicy(31);
/*       */     
/*  9003 */     this.lstCritsToPlace.setFont(Print.PrintConsts.BaseCritFont);
/*  9004 */     this.lstCritsToPlace.setModel(new javax.swing.AbstractListModel() {
/*  9005 */       String[] strings = { "Selected", "Item 2", "Item 3", "Item 4", "Item 5" };
/*  9006 */       public int getSize() { return this.strings.length; }
/*  9007 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  9008 */     });
/*  9009 */     this.lstCritsToPlace.setSelectionMode(0);
/*  9010 */     this.lstCritsToPlace.setDragEnabled(true);
/*  9011 */     this.lstCritsToPlace.setMaximumSize(new Dimension(150, 10000));
/*  9012 */     this.lstCritsToPlace.setMinimumSize(new Dimension(150, 80));
/*  9013 */     this.lstCritsToPlace.setName("[150, 80]");
/*  9014 */     this.lstCritsToPlace.setVisibleRowCount(20);
/*  9015 */     MouseListener mlCritsToPlace = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9017 */         int Index = frmMainWide.this.lstCritsToPlace.locationToIndex(e.getPoint());
/*  9018 */         if (Index < 0) return;
/*  9019 */         frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetFromQueueByIndex(Index);
/*  9020 */         if (e.isPopupTrigger()) {
/*  9021 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  9022 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*  9024 */         else if (frmMainWide.this.CurItem.Contiguous()) {
/*  9025 */           components.EquipmentCollection C = frmMainWide.this.CurMech.GetLoadout().GetCollection(frmMainWide.this.CurItem);
/*  9026 */           if (C == null) {
/*  9027 */             frmMainWide.this.btnAutoAllocate.setEnabled(false);
/*  9028 */             frmMainWide.this.btnSelectiveAllocate.setEnabled(false);
/*       */           } else {
/*  9030 */             frmMainWide.this.btnAutoAllocate.setEnabled(true);
/*  9031 */             frmMainWide.this.btnSelectiveAllocate.setEnabled(true);
/*       */           }
/*       */         } else {
/*  9034 */           frmMainWide.this.btnAutoAllocate.setEnabled(true);
/*  9035 */           frmMainWide.this.btnSelectiveAllocate.setEnabled(true);
/*       */         }
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9040 */         int Index = frmMainWide.this.lstCritsToPlace.locationToIndex(e.getPoint());
/*  9041 */         if (Index < 0) return;
/*  9042 */         frmMainWide.this.CurItem = frmMainWide.this.CurMech.GetLoadout().GetFromQueueByIndex(Index);
/*  9043 */         if (e.isPopupTrigger()) {
/*  9044 */           frmMainWide.this.ConfigureUtilsMenu(e.getComponent());
/*  9045 */           frmMainWide.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*  9047 */         else if (frmMainWide.this.CurItem.Contiguous()) {
/*  9048 */           components.EquipmentCollection C = frmMainWide.this.CurMech.GetLoadout().GetCollection(frmMainWide.this.CurItem);
/*  9049 */           if (C == null) {
/*  9050 */             frmMainWide.this.btnAutoAllocate.setEnabled(false);
/*  9051 */             frmMainWide.this.btnSelectiveAllocate.setEnabled(false);
/*       */           } else {
/*  9053 */             frmMainWide.this.btnAutoAllocate.setEnabled(true);
/*  9054 */             frmMainWide.this.btnSelectiveAllocate.setEnabled(true);
/*       */           }
/*       */         } else {
/*  9057 */           frmMainWide.this.btnAutoAllocate.setEnabled(true);
/*  9058 */           frmMainWide.this.btnSelectiveAllocate.setEnabled(true);
/*       */         }
/*       */         
/*       */       }
/*  9062 */     };
/*  9063 */     this.lstCritsToPlace.addMouseListener(mlCritsToPlace);
/*  9064 */     this.lstCritsToPlace.setTransferHandler(new thQueueTransferHandler());
/*  9065 */     this.lstCritsToPlace.setCellRenderer(new EquipmentListRenderer(this));
/*  9066 */     this.lstCritsToPlace.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  9068 */         frmMainWide.this.lstCritsToPlaceValueChanged(evt);
/*       */       }
/*  9070 */     });
/*  9071 */     this.lstCritsToPlace.addKeyListener(new java.awt.event.KeyAdapter() {
/*       */       public void keyPressed(java.awt.event.KeyEvent evt) {
/*  9073 */         frmMainWide.this.lstCritsToPlaceKeyPressed(evt);
/*       */       }
/*  9075 */     });
/*  9076 */     this.jScrollPane18.setViewportView(this.lstCritsToPlace);
/*       */     
/*  9078 */     this.pnlEquipmentToPlace.add(this.jScrollPane18);
/*       */     
/*  9080 */     this.btnRemoveItemCrits.setText("Remove Item");
/*  9081 */     this.btnRemoveItemCrits.setEnabled(false);
/*  9082 */     this.btnRemoveItemCrits.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9084 */         frmMainWide.this.btnRemoveItemCritsActionPerformed(evt);
/*       */       }
/*  9086 */     });
/*  9087 */     this.pnlEquipmentToPlace.add(this.btnRemoveItemCrits);
/*       */     
/*  9089 */     this.pnlEquipment.add(this.pnlEquipmentToPlace, new AbsoluteConstraints(470, 10, 150, 310));
/*       */     
/*  9091 */     this.btnAutoAllocate.setText("Auto Allocate");
/*  9092 */     this.btnAutoAllocate.setEnabled(false);
/*  9093 */     this.btnAutoAllocate.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9095 */         frmMainWide.this.btnAutoAllocateActionPerformed(evt);
/*       */       }
/*  9097 */     });
/*  9098 */     this.pnlEquipment.add(this.btnAutoAllocate, new AbsoluteConstraints(630, 20, 150, -1));
/*       */     
/*  9100 */     this.btnSelectiveAllocate.setText("Selective");
/*  9101 */     this.btnSelectiveAllocate.setEnabled(false);
/*  9102 */     this.btnSelectiveAllocate.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9104 */         frmMainWide.this.btnSelectiveAllocateActionPerformed(evt);
/*       */       }
/*  9106 */     });
/*  9107 */     this.pnlEquipment.add(this.btnSelectiveAllocate, new AbsoluteConstraints(630, 50, 100, -1));
/*       */     
/*  9109 */     this.btnCompactCrits.setText("Compact");
/*  9110 */     this.btnCompactCrits.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9112 */         frmMainWide.this.btnCompactCritsActionPerformed(evt);
/*       */       }
/*  9114 */     });
/*  9115 */     this.pnlEquipment.add(this.btnCompactCrits, new AbsoluteConstraints(630, 400, 100, -1));
/*       */     
/*  9117 */     this.btnClearLoadout.setText("Clear");
/*  9118 */     this.btnClearLoadout.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9120 */         frmMainWide.this.btnClearLoadoutActionPerformed(evt);
/*       */       }
/*  9122 */     });
/*  9123 */     this.pnlEquipment.add(this.btnClearLoadout, new AbsoluteConstraints(630, 430, 100, -1));
/*       */     
/*  9125 */     this.tbpMainTabPane.addTab("Equipment", this.pnlEquipment);
/*       */     
/*  9127 */     this.pnlFluff.setLayout(new GridBagLayout());
/*       */     
/*  9129 */     this.pnlImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fluff Image", 0, 0, new Font("Arial", 0, 11)));
/*  9130 */     this.pnlImage.setLayout(new GridBagLayout());
/*       */     
/*  9132 */     this.lblFluffImage.setHorizontalAlignment(0);
/*  9133 */     this.lblFluffImage.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  9134 */     this.lblFluffImage.setMaximumSize(new Dimension(290, 350));
/*  9135 */     this.lblFluffImage.setMinimumSize(new Dimension(290, 350));
/*  9136 */     this.lblFluffImage.setPreferredSize(new Dimension(290, 350));
/*  9137 */     gridBagConstraints = new GridBagConstraints();
/*  9138 */     gridBagConstraints.gridx = 0;
/*  9139 */     gridBagConstraints.gridy = 1;
/*  9140 */     gridBagConstraints.anchor = 18;
/*  9141 */     this.pnlImage.add(this.lblFluffImage, gridBagConstraints);
/*       */     
/*  9143 */     this.btnLoadImage.setText("Load Image");
/*  9144 */     this.btnLoadImage.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9146 */         frmMainWide.this.btnLoadImageActionPerformed(evt);
/*       */       }
/*  9148 */     });
/*  9149 */     this.jPanel1.add(this.btnLoadImage);
/*       */     
/*  9151 */     this.btnClearImage.setText("Clear Image");
/*  9152 */     this.btnClearImage.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9154 */         frmMainWide.this.btnClearImageActionPerformed(evt);
/*       */       }
/*  9156 */     });
/*  9157 */     this.jPanel1.add(this.btnClearImage);
/*       */     
/*  9159 */     gridBagConstraints = new GridBagConstraints();
/*  9160 */     gridBagConstraints.anchor = 13;
/*  9161 */     gridBagConstraints.insets = new Insets(0, 0, 4, 4);
/*  9162 */     this.pnlImage.add(this.jPanel1, gridBagConstraints);
/*       */     
/*  9164 */     gridBagConstraints = new GridBagConstraints();
/*  9165 */     gridBagConstraints.gridx = 2;
/*  9166 */     gridBagConstraints.gridy = 0;
/*  9167 */     gridBagConstraints.anchor = 18;
/*  9168 */     this.pnlFluff.add(this.pnlImage, gridBagConstraints);
/*       */     
/*  9170 */     this.tbpFluffEditors.setTabPlacement(4);
/*  9171 */     this.tbpFluffEditors.setMaximumSize(new Dimension(550, 455));
/*  9172 */     this.tbpFluffEditors.setMinimumSize(new Dimension(550, 455));
/*  9173 */     this.tbpFluffEditors.setPreferredSize(new Dimension(550, 455));
/*       */     
/*  9175 */     this.pnlOverview.setMaximumSize(new Dimension(427, 485));
/*  9176 */     this.pnlOverview.setMinimumSize(new Dimension(427, 485));
/*  9177 */     this.pnlOverview.setLayout(new javax.swing.BoxLayout(this.pnlOverview, 1));
/*  9178 */     this.tbpFluffEditors.addTab("Overview", this.pnlOverview);
/*       */     
/*  9180 */     this.pnlCapabilities.setMaximumSize(new Dimension(427, 485));
/*  9181 */     this.pnlCapabilities.setMinimumSize(new Dimension(427, 485));
/*  9182 */     this.pnlCapabilities.setLayout(new javax.swing.BoxLayout(this.pnlCapabilities, 1));
/*  9183 */     this.tbpFluffEditors.addTab("Capabilities", this.pnlCapabilities);
/*       */     
/*  9185 */     this.pnlHistory.setLayout(new javax.swing.BoxLayout(this.pnlHistory, 1));
/*  9186 */     this.tbpFluffEditors.addTab("Battle History", this.pnlHistory);
/*       */     
/*  9188 */     this.pnlDeployment.setLayout(new javax.swing.BoxLayout(this.pnlDeployment, 1));
/*  9189 */     this.tbpFluffEditors.addTab("Deployment", this.pnlDeployment);
/*       */     
/*  9191 */     this.pnlVariants.setLayout(new javax.swing.BoxLayout(this.pnlVariants, 1));
/*  9192 */     this.tbpFluffEditors.addTab("Variants", this.pnlVariants);
/*       */     
/*  9194 */     this.pnlNotables.setLayout(new javax.swing.BoxLayout(this.pnlNotables, 1));
/*  9195 */     this.tbpFluffEditors.addTab("Notables", this.pnlNotables);
/*       */     
/*  9197 */     this.pnlAdditionalFluff.setLayout(new javax.swing.BoxLayout(this.pnlAdditionalFluff, 1));
/*  9198 */     this.tbpFluffEditors.addTab("Additional", this.pnlAdditionalFluff);
/*       */     
/*  9200 */     gridBagConstraints = new GridBagConstraints();
/*  9201 */     gridBagConstraints.gridx = 1;
/*  9202 */     gridBagConstraints.gridy = 0;
/*  9203 */     gridBagConstraints.gridheight = 2;
/*  9204 */     gridBagConstraints.anchor = 12;
/*  9205 */     gridBagConstraints.insets = new Insets(0, 5, 0, 6);
/*  9206 */     this.pnlFluff.add(this.tbpFluffEditors, gridBagConstraints);
/*       */     
/*  9208 */     this.pnlExport.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Export", 0, 0, new Font("Arial", 0, 11)));
/*  9209 */     this.pnlExport.setLayout(new GridBagLayout());
/*       */     
/*  9211 */     this.btnExportTXT.setText("to TXT");
/*  9212 */     this.btnExportTXT.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9214 */         frmMainWide.this.btnExportTXTActionPerformed(evt);
/*       */       }
/*  9216 */     });
/*  9217 */     gridBagConstraints = new GridBagConstraints();
/*  9218 */     gridBagConstraints.anchor = 17;
/*  9219 */     this.pnlExport.add(this.btnExportTXT, gridBagConstraints);
/*       */     
/*  9221 */     this.btnExportHTML.setText("to HTML");
/*  9222 */     this.btnExportHTML.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9224 */         frmMainWide.this.btnExportHTMLActionPerformed(evt);
/*       */       }
/*  9226 */     });
/*  9227 */     gridBagConstraints = new GridBagConstraints();
/*  9228 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/*  9229 */     this.pnlExport.add(this.btnExportHTML, gridBagConstraints);
/*       */     
/*  9231 */     this.btnExportMTF.setText("to MegaMek");
/*  9232 */     this.btnExportMTF.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9234 */         frmMainWide.this.btnExportMTFActionPerformed(evt);
/*       */       }
/*  9236 */     });
/*  9237 */     gridBagConstraints = new GridBagConstraints();
/*  9238 */     gridBagConstraints.anchor = 13;
/*  9239 */     this.pnlExport.add(this.btnExportMTF, gridBagConstraints);
/*       */     
/*  9241 */     gridBagConstraints = new GridBagConstraints();
/*  9242 */     gridBagConstraints.gridx = 2;
/*  9243 */     gridBagConstraints.gridy = 1;
/*  9244 */     gridBagConstraints.anchor = 11;
/*  9245 */     this.pnlFluff.add(this.pnlExport, gridBagConstraints);
/*       */     
/*  9247 */     this.pnlManufacturers.setBorder(BorderFactory.createEtchedBorder());
/*  9248 */     this.pnlManufacturers.setLayout(new GridBagLayout());
/*       */     
/*  9250 */     this.jLabel8.setFont(new Font("Arial", 1, 12));
/*  9251 */     this.jLabel8.setText("Manufacturer Information");
/*  9252 */     this.jLabel8.setMaximumSize(new Dimension(175, 15));
/*  9253 */     this.jLabel8.setMinimumSize(new Dimension(175, 15));
/*  9254 */     this.jLabel8.setPreferredSize(new Dimension(175, 15));
/*  9255 */     gridBagConstraints = new GridBagConstraints();
/*  9256 */     gridBagConstraints.gridx = 0;
/*  9257 */     gridBagConstraints.gridy = 0;
/*  9258 */     gridBagConstraints.gridwidth = 2;
/*  9259 */     gridBagConstraints.fill = 2;
/*  9260 */     gridBagConstraints.anchor = 18;
/*  9261 */     gridBagConstraints.insets = new Insets(5, 10, 0, 0);
/*  9262 */     this.pnlManufacturers.add(this.jLabel8, gridBagConstraints);
/*       */     
/*  9264 */     this.jLabel9.setFont(new Font("Arial", 0, 11));
/*  9265 */     this.jLabel9.setHorizontalAlignment(4);
/*  9266 */     this.jLabel9.setText("Manufacturing Company:");
/*  9267 */     gridBagConstraints = new GridBagConstraints();
/*  9268 */     gridBagConstraints.gridx = 0;
/*  9269 */     gridBagConstraints.gridy = 1;
/*  9270 */     gridBagConstraints.fill = 2;
/*  9271 */     gridBagConstraints.anchor = 18;
/*  9272 */     gridBagConstraints.insets = new Insets(10, 10, 0, 0);
/*  9273 */     this.pnlManufacturers.add(this.jLabel9, gridBagConstraints);
/*       */     
/*  9275 */     this.jLabel10.setFont(new Font("Arial", 0, 11));
/*  9276 */     this.jLabel10.setHorizontalAlignment(4);
/*  9277 */     this.jLabel10.setText("Location:");
/*  9278 */     gridBagConstraints = new GridBagConstraints();
/*  9279 */     gridBagConstraints.gridx = 0;
/*  9280 */     gridBagConstraints.gridy = 2;
/*  9281 */     gridBagConstraints.fill = 2;
/*  9282 */     gridBagConstraints.ipadx = 76;
/*  9283 */     gridBagConstraints.anchor = 18;
/*  9284 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9285 */     this.pnlManufacturers.add(this.jLabel10, gridBagConstraints);
/*       */     
/*  9287 */     this.jLabel12.setFont(new Font("Arial", 0, 11));
/*  9288 */     this.jLabel12.setHorizontalAlignment(4);
/*  9289 */     this.jLabel12.setText("Engine Manufacturer:");
/*  9290 */     gridBagConstraints = new GridBagConstraints();
/*  9291 */     gridBagConstraints.gridx = 0;
/*  9292 */     gridBagConstraints.gridy = 4;
/*  9293 */     gridBagConstraints.fill = 2;
/*  9294 */     gridBagConstraints.ipadx = 17;
/*  9295 */     gridBagConstraints.anchor = 18;
/*  9296 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9297 */     this.pnlManufacturers.add(this.jLabel12, gridBagConstraints);
/*       */     
/*  9299 */     this.jLabel11.setFont(new Font("Arial", 0, 11));
/*  9300 */     this.jLabel11.setHorizontalAlignment(4);
/*  9301 */     this.jLabel11.setText("Armor Model:");
/*  9302 */     gridBagConstraints = new GridBagConstraints();
/*  9303 */     gridBagConstraints.gridx = 0;
/*  9304 */     gridBagConstraints.gridy = 5;
/*  9305 */     gridBagConstraints.fill = 2;
/*  9306 */     gridBagConstraints.ipadx = 56;
/*  9307 */     gridBagConstraints.anchor = 18;
/*  9308 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9309 */     this.pnlManufacturers.add(this.jLabel11, gridBagConstraints);
/*       */     
/*  9311 */     this.jLabel13.setFont(new Font("Arial", 0, 11));
/*  9312 */     this.jLabel13.setHorizontalAlignment(4);
/*  9313 */     this.jLabel13.setText("Chassis Model:");
/*  9314 */     gridBagConstraints = new GridBagConstraints();
/*  9315 */     gridBagConstraints.gridx = 0;
/*  9316 */     gridBagConstraints.gridy = 3;
/*  9317 */     gridBagConstraints.fill = 2;
/*  9318 */     gridBagConstraints.ipadx = 47;
/*  9319 */     gridBagConstraints.anchor = 18;
/*  9320 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9321 */     this.pnlManufacturers.add(this.jLabel13, gridBagConstraints);
/*       */     
/*  9323 */     this.jLabel14.setFont(new Font("Arial", 0, 11));
/*  9324 */     this.jLabel14.setHorizontalAlignment(4);
/*  9325 */     this.jLabel14.setText("Communications System:");
/*  9326 */     gridBagConstraints = new GridBagConstraints();
/*  9327 */     gridBagConstraints.gridx = 0;
/*  9328 */     gridBagConstraints.gridy = 7;
/*  9329 */     gridBagConstraints.fill = 2;
/*  9330 */     gridBagConstraints.anchor = 18;
/*  9331 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9332 */     this.pnlManufacturers.add(this.jLabel14, gridBagConstraints);
/*       */     
/*  9334 */     this.jLabel15.setFont(new Font("Arial", 0, 11));
/*  9335 */     this.jLabel15.setHorizontalAlignment(4);
/*  9336 */     this.jLabel15.setText("Targeting and Tracking:");
/*  9337 */     gridBagConstraints = new GridBagConstraints();
/*  9338 */     gridBagConstraints.gridx = 0;
/*  9339 */     gridBagConstraints.gridy = 8;
/*  9340 */     gridBagConstraints.fill = 2;
/*  9341 */     gridBagConstraints.ipadx = 7;
/*  9342 */     gridBagConstraints.anchor = 18;
/*  9343 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9344 */     this.pnlManufacturers.add(this.jLabel15, gridBagConstraints);
/*       */     
/*  9346 */     this.txtManufacturer.setFont(new Font("Arial", 0, 11));
/*  9347 */     gridBagConstraints = new GridBagConstraints();
/*  9348 */     gridBagConstraints.gridx = 1;
/*  9349 */     gridBagConstraints.gridy = 1;
/*  9350 */     gridBagConstraints.fill = 2;
/*  9351 */     gridBagConstraints.ipadx = 184;
/*  9352 */     gridBagConstraints.anchor = 18;
/*  9353 */     gridBagConstraints.insets = new Insets(10, 2, 0, 11);
/*  9354 */     this.pnlManufacturers.add(this.txtManufacturer, gridBagConstraints);
/*  9355 */     MouseListener mlManufacturer = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9357 */         if (e.isPopupTrigger())
/*  9358 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9362 */         if (e.isPopupTrigger()) {
/*  9363 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9366 */     };
/*  9367 */     this.txtManufacturer.addMouseListener(mlManufacturer);
/*       */     
/*  9369 */     this.txtEngineManufacturer.setFont(new Font("Arial", 0, 11));
/*  9370 */     gridBagConstraints = new GridBagConstraints();
/*  9371 */     gridBagConstraints.gridx = 1;
/*  9372 */     gridBagConstraints.gridy = 4;
/*  9373 */     gridBagConstraints.fill = 2;
/*  9374 */     gridBagConstraints.ipadx = 184;
/*  9375 */     gridBagConstraints.anchor = 18;
/*  9376 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9377 */     this.pnlManufacturers.add(this.txtEngineManufacturer, gridBagConstraints);
/*  9378 */     MouseListener mlEngineManufacturer = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9380 */         if (e.isPopupTrigger())
/*  9381 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9385 */         if (e.isPopupTrigger()) {
/*  9386 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9389 */     };
/*  9390 */     this.txtEngineManufacturer.addMouseListener(mlEngineManufacturer);
/*       */     
/*  9392 */     this.txtArmorModel.setFont(new Font("Arial", 0, 11));
/*  9393 */     gridBagConstraints = new GridBagConstraints();
/*  9394 */     gridBagConstraints.gridx = 1;
/*  9395 */     gridBagConstraints.gridy = 5;
/*  9396 */     gridBagConstraints.fill = 2;
/*  9397 */     gridBagConstraints.ipadx = 184;
/*  9398 */     gridBagConstraints.anchor = 18;
/*  9399 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9400 */     this.pnlManufacturers.add(this.txtArmorModel, gridBagConstraints);
/*  9401 */     MouseListener mlArmorModel = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9403 */         if (e.isPopupTrigger())
/*  9404 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9408 */         if (e.isPopupTrigger()) {
/*  9409 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9412 */     };
/*  9413 */     this.txtArmorModel.addMouseListener(mlArmorModel);
/*       */     
/*  9415 */     this.txtChassisModel.setFont(new Font("Arial", 0, 11));
/*  9416 */     gridBagConstraints = new GridBagConstraints();
/*  9417 */     gridBagConstraints.gridx = 1;
/*  9418 */     gridBagConstraints.gridy = 3;
/*  9419 */     gridBagConstraints.fill = 2;
/*  9420 */     gridBagConstraints.ipadx = 184;
/*  9421 */     gridBagConstraints.anchor = 18;
/*  9422 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9423 */     this.pnlManufacturers.add(this.txtChassisModel, gridBagConstraints);
/*  9424 */     MouseListener mlChassisModel = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9426 */         if (e.isPopupTrigger())
/*  9427 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9431 */         if (e.isPopupTrigger()) {
/*  9432 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9435 */     };
/*  9436 */     this.txtChassisModel.addMouseListener(mlChassisModel);
/*       */     
/*  9438 */     this.txtCommSystem.setFont(new Font("Arial", 0, 11));
/*  9439 */     gridBagConstraints = new GridBagConstraints();
/*  9440 */     gridBagConstraints.gridx = 1;
/*  9441 */     gridBagConstraints.gridy = 7;
/*  9442 */     gridBagConstraints.fill = 2;
/*  9443 */     gridBagConstraints.ipadx = 184;
/*  9444 */     gridBagConstraints.anchor = 18;
/*  9445 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9446 */     this.pnlManufacturers.add(this.txtCommSystem, gridBagConstraints);
/*  9447 */     MouseListener mlCommSystem = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9449 */         if (e.isPopupTrigger())
/*  9450 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9454 */         if (e.isPopupTrigger()) {
/*  9455 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9458 */     };
/*  9459 */     this.txtCommSystem.addMouseListener(mlCommSystem);
/*       */     
/*  9461 */     this.txtTNTSystem.setFont(new Font("Arial", 0, 11));
/*  9462 */     gridBagConstraints = new GridBagConstraints();
/*  9463 */     gridBagConstraints.gridx = 1;
/*  9464 */     gridBagConstraints.gridy = 8;
/*  9465 */     gridBagConstraints.fill = 2;
/*  9466 */     gridBagConstraints.ipadx = 184;
/*  9467 */     gridBagConstraints.anchor = 18;
/*  9468 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9469 */     this.pnlManufacturers.add(this.txtTNTSystem, gridBagConstraints);
/*  9470 */     MouseListener mlTNTSystem = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9472 */         if (e.isPopupTrigger())
/*  9473 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9477 */         if (e.isPopupTrigger()) {
/*  9478 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9481 */     };
/*  9482 */     this.txtTNTSystem.addMouseListener(mlTNTSystem);
/*       */     
/*  9484 */     this.pnlWeaponsManufacturers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapons Manufacturers", 0, 0, new Font("Arial", 0, 11)));
/*  9485 */     this.pnlWeaponsManufacturers.setFont(new Font("Arial", 0, 11));
/*  9486 */     this.pnlWeaponsManufacturers.setMinimumSize(new Dimension(315, 260));
/*  9487 */     this.pnlWeaponsManufacturers.setLayout(new GridBagLayout());
/*       */     
/*  9489 */     this.chkIndividualWeapons.setText("Assign manufacturers individually");
/*  9490 */     gridBagConstraints = new GridBagConstraints();
/*  9491 */     gridBagConstraints.gridx = 0;
/*  9492 */     gridBagConstraints.gridy = 1;
/*  9493 */     gridBagConstraints.anchor = 17;
/*  9494 */     gridBagConstraints.insets = new Insets(4, 10, 4, 0);
/*  9495 */     this.pnlWeaponsManufacturers.add(this.chkIndividualWeapons, gridBagConstraints);
/*       */     
/*  9497 */     this.scpWeaponManufacturers.setPreferredSize(new Dimension(452, 392));
/*       */     
/*  9499 */     this.tblWeaponManufacturers.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null }, { null, null }, { null, null }, { null, null } }, new String[] { "Weapon", "Manufacturer" })
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9510 */       boolean[] canEdit = { false, true };
/*       */       
/*       */ 
/*       */       public boolean isCellEditable(int rowIndex, int columnIndex)
/*       */       {
/*  9515 */         return this.canEdit[columnIndex];
/*       */       }
/*  9517 */     });
/*  9518 */     this.scpWeaponManufacturers.setViewportView(this.tblWeaponManufacturers);
/*       */     
/*  9520 */     gridBagConstraints = new GridBagConstraints();
/*  9521 */     gridBagConstraints.ipadx = 280;
/*  9522 */     gridBagConstraints.ipady = 180;
/*  9523 */     this.pnlWeaponsManufacturers.add(this.scpWeaponManufacturers, gridBagConstraints);
/*       */     
/*  9525 */     gridBagConstraints = new GridBagConstraints();
/*  9526 */     gridBagConstraints.gridx = 0;
/*  9527 */     gridBagConstraints.gridy = 9;
/*  9528 */     gridBagConstraints.gridwidth = 2;
/*  9529 */     gridBagConstraints.anchor = 18;
/*  9530 */     gridBagConstraints.insets = new Insets(2, 10, 2, 10);
/*  9531 */     this.pnlManufacturers.add(this.pnlWeaponsManufacturers, gridBagConstraints);
/*  9532 */     gridBagConstraints = new GridBagConstraints();
/*  9533 */     gridBagConstraints.gridx = 1;
/*  9534 */     gridBagConstraints.gridy = 2;
/*  9535 */     gridBagConstraints.fill = 2;
/*  9536 */     gridBagConstraints.ipadx = 184;
/*  9537 */     gridBagConstraints.anchor = 18;
/*  9538 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9539 */     this.pnlManufacturers.add(this.txtManufacturerLocation, gridBagConstraints);
/*  9540 */     MouseListener mlManufacturerLocation = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9542 */         if (e.isPopupTrigger())
/*  9543 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9547 */         if (e.isPopupTrigger()) {
/*  9548 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9551 */     };
/*  9552 */     this.txtManufacturerLocation.addMouseListener(mlManufacturerLocation);
/*       */     
/*  9554 */     this.jLabel35.setFont(new Font("Arial", 0, 11));
/*  9555 */     this.jLabel35.setHorizontalAlignment(4);
/*  9556 */     this.jLabel35.setText("Jump Jet Model:");
/*  9557 */     gridBagConstraints = new GridBagConstraints();
/*  9558 */     gridBagConstraints.gridx = 0;
/*  9559 */     gridBagConstraints.gridy = 6;
/*  9560 */     gridBagConstraints.fill = 2;
/*  9561 */     gridBagConstraints.ipadx = 44;
/*  9562 */     gridBagConstraints.anchor = 18;
/*  9563 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9564 */     this.pnlManufacturers.add(this.jLabel35, gridBagConstraints);
/*       */     
/*  9566 */     this.txtJJModel.setEnabled(false);
/*  9567 */     gridBagConstraints = new GridBagConstraints();
/*  9568 */     gridBagConstraints.gridx = 1;
/*  9569 */     gridBagConstraints.gridy = 6;
/*  9570 */     gridBagConstraints.fill = 2;
/*  9571 */     gridBagConstraints.ipadx = 184;
/*  9572 */     gridBagConstraints.anchor = 18;
/*  9573 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9574 */     this.pnlManufacturers.add(this.txtJJModel, gridBagConstraints);
/*  9575 */     MouseListener mlJJModel = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9577 */         if (e.isPopupTrigger())
/*  9578 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9582 */         if (e.isPopupTrigger()) {
/*  9583 */           frmMainWide.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9586 */     };
/*  9587 */     this.txtJJModel.addMouseListener(mlJJModel);
/*       */     
/*  9589 */     gridBagConstraints = new GridBagConstraints();
/*  9590 */     gridBagConstraints.gridx = 0;
/*  9591 */     gridBagConstraints.gridy = 0;
/*  9592 */     gridBagConstraints.gridheight = 2;
/*  9593 */     gridBagConstraints.anchor = 11;
/*  9594 */     gridBagConstraints.insets = new Insets(7, 0, 0, 0);
/*  9595 */     this.pnlFluff.add(this.pnlManufacturers, gridBagConstraints);
/*       */     
/*  9597 */     this.tbpMainTabPane.addTab("   Fluff   ", this.pnlFluff);
/*       */     
/*  9599 */     this.pnlCharts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/*  9601 */     this.jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Total Tonnage Percentages"));
/*  9602 */     this.jPanel2.setLayout(new GridBagLayout());
/*       */     
/*  9604 */     this.jLabel39.setText("Structural Components:");
/*  9605 */     this.jPanel2.add(this.jLabel39, new GridBagConstraints());
/*       */     
/*  9607 */     this.lblTonPercStructure.setText("000.00%");
/*  9608 */     gridBagConstraints = new GridBagConstraints();
/*  9609 */     gridBagConstraints.anchor = 17;
/*  9610 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9611 */     this.jPanel2.add(this.lblTonPercStructure, gridBagConstraints);
/*       */     
/*  9613 */     this.jLabel43.setText("Engine:");
/*  9614 */     gridBagConstraints = new GridBagConstraints();
/*  9615 */     gridBagConstraints.gridx = 0;
/*  9616 */     gridBagConstraints.gridy = 1;
/*  9617 */     gridBagConstraints.anchor = 13;
/*  9618 */     this.jPanel2.add(this.jLabel43, gridBagConstraints);
/*       */     
/*  9620 */     this.lblTonPercEngine.setText("000.00%");
/*  9621 */     gridBagConstraints = new GridBagConstraints();
/*  9622 */     gridBagConstraints.gridx = 1;
/*  9623 */     gridBagConstraints.gridy = 1;
/*  9624 */     gridBagConstraints.anchor = 17;
/*  9625 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9626 */     this.jPanel2.add(this.lblTonPercEngine, gridBagConstraints);
/*       */     
/*  9628 */     this.jLabel54.setText("Heat Sinks:");
/*  9629 */     gridBagConstraints = new GridBagConstraints();
/*  9630 */     gridBagConstraints.gridx = 0;
/*  9631 */     gridBagConstraints.gridy = 2;
/*  9632 */     gridBagConstraints.anchor = 13;
/*  9633 */     this.jPanel2.add(this.jLabel54, gridBagConstraints);
/*       */     
/*  9635 */     this.lblTonPercHeatSinks.setText("000.00%");
/*  9636 */     gridBagConstraints = new GridBagConstraints();
/*  9637 */     gridBagConstraints.gridx = 1;
/*  9638 */     gridBagConstraints.gridy = 2;
/*  9639 */     gridBagConstraints.anchor = 17;
/*  9640 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9641 */     this.jPanel2.add(this.lblTonPercHeatSinks, gridBagConstraints);
/*       */     
/*  9643 */     this.jLabel56.setText("Enhancements:");
/*  9644 */     gridBagConstraints = new GridBagConstraints();
/*  9645 */     gridBagConstraints.gridx = 0;
/*  9646 */     gridBagConstraints.gridy = 3;
/*  9647 */     gridBagConstraints.anchor = 13;
/*  9648 */     this.jPanel2.add(this.jLabel56, gridBagConstraints);
/*       */     
/*  9650 */     this.lblTonPercEnhance.setText("000.00%");
/*  9651 */     gridBagConstraints = new GridBagConstraints();
/*  9652 */     gridBagConstraints.gridx = 1;
/*  9653 */     gridBagConstraints.gridy = 3;
/*  9654 */     gridBagConstraints.anchor = 17;
/*  9655 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9656 */     this.jPanel2.add(this.lblTonPercEnhance, gridBagConstraints);
/*       */     
/*  9658 */     this.jLabel58.setText("Armor:");
/*  9659 */     gridBagConstraints = new GridBagConstraints();
/*  9660 */     gridBagConstraints.gridx = 0;
/*  9661 */     gridBagConstraints.gridy = 4;
/*  9662 */     gridBagConstraints.anchor = 13;
/*  9663 */     this.jPanel2.add(this.jLabel58, gridBagConstraints);
/*       */     
/*  9665 */     this.lblTonPercArmor.setText("000.00%");
/*  9666 */     gridBagConstraints = new GridBagConstraints();
/*  9667 */     gridBagConstraints.gridx = 1;
/*  9668 */     gridBagConstraints.gridy = 4;
/*  9669 */     gridBagConstraints.anchor = 17;
/*  9670 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9671 */     this.jPanel2.add(this.lblTonPercArmor, gridBagConstraints);
/*       */     
/*  9673 */     this.jLabel60.setText("Jump Jets:");
/*  9674 */     gridBagConstraints = new GridBagConstraints();
/*  9675 */     gridBagConstraints.gridx = 0;
/*  9676 */     gridBagConstraints.gridy = 5;
/*  9677 */     gridBagConstraints.anchor = 13;
/*  9678 */     this.jPanel2.add(this.jLabel60, gridBagConstraints);
/*       */     
/*  9680 */     this.lblTonPercJumpJets.setText("000.00%");
/*  9681 */     gridBagConstraints = new GridBagConstraints();
/*  9682 */     gridBagConstraints.gridx = 1;
/*  9683 */     gridBagConstraints.gridy = 5;
/*  9684 */     gridBagConstraints.anchor = 17;
/*  9685 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9686 */     this.jPanel2.add(this.lblTonPercJumpJets, gridBagConstraints);
/*       */     
/*  9688 */     this.jLabel62.setText("Weapons and Ammo:");
/*  9689 */     gridBagConstraints = new GridBagConstraints();
/*  9690 */     gridBagConstraints.gridx = 0;
/*  9691 */     gridBagConstraints.gridy = 6;
/*  9692 */     gridBagConstraints.anchor = 13;
/*  9693 */     this.jPanel2.add(this.jLabel62, gridBagConstraints);
/*       */     
/*  9695 */     this.lblTonPercWeapons.setText("000.00%");
/*  9696 */     gridBagConstraints = new GridBagConstraints();
/*  9697 */     gridBagConstraints.gridx = 1;
/*  9698 */     gridBagConstraints.gridy = 6;
/*  9699 */     gridBagConstraints.anchor = 17;
/*  9700 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9701 */     this.jPanel2.add(this.lblTonPercWeapons, gridBagConstraints);
/*       */     
/*  9703 */     this.jLabel64.setText("Equipment/Pod Space:");
/*  9704 */     gridBagConstraints = new GridBagConstraints();
/*  9705 */     gridBagConstraints.gridx = 0;
/*  9706 */     gridBagConstraints.gridy = 7;
/*  9707 */     gridBagConstraints.anchor = 13;
/*  9708 */     this.jPanel2.add(this.jLabel64, gridBagConstraints);
/*       */     
/*  9710 */     this.lblTonPercEquips.setText("000.00%");
/*  9711 */     gridBagConstraints = new GridBagConstraints();
/*  9712 */     gridBagConstraints.gridx = 1;
/*  9713 */     gridBagConstraints.gridy = 7;
/*  9714 */     gridBagConstraints.anchor = 17;
/*  9715 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9716 */     this.jPanel2.add(this.lblTonPercEquips, gridBagConstraints);
/*       */     
/*  9718 */     this.pnlCharts.add(this.jPanel2, new AbsoluteConstraints(10, 10, 220, 150));
/*       */     
/*  9720 */     this.jPanel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Other Statistics"));
/*  9721 */     this.jPanel3.setLayout(new GridBagLayout());
/*       */     
/*  9723 */     this.jLabel41.setText("Damage / 'Mech Tonnage:");
/*  9724 */     gridBagConstraints = new GridBagConstraints();
/*  9725 */     gridBagConstraints.anchor = 13;
/*  9726 */     this.jPanel3.add(this.jLabel41, gridBagConstraints);
/*       */     
/*  9728 */     this.lblDamagePerTon.setText("000.00");
/*  9729 */     gridBagConstraints = new GridBagConstraints();
/*  9730 */     gridBagConstraints.anchor = 17;
/*  9731 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  9732 */     this.jPanel3.add(this.lblDamagePerTon, gridBagConstraints);
/*       */     
/*  9734 */     this.pnlCharts.add(this.jPanel3, new AbsoluteConstraints(230, 10, 230, 50));
/*       */     
/*  9736 */     this.pnlDamageChart.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  9737 */     this.pnlDamageChart.setLayout(null);
/*  9738 */     this.pnlCharts.add(this.pnlDamageChart, new AbsoluteConstraints(10, 170, 670, 280));
/*       */     
/*  9740 */     this.lblLegendTitle.setText("Chart Options:");
/*  9741 */     this.pnlCharts.add(this.lblLegendTitle, new AbsoluteConstraints(470, 10, 140, -1));
/*       */     
/*  9743 */     this.chkChartFront.setBackground(Color.red);
/*  9744 */     this.chkChartFront.setSelected(true);
/*  9745 */     this.chkChartFront.setText("Show Front Arc Weapons");
/*  9746 */     this.chkChartFront.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9748 */         frmMainWide.this.chkChartFrontActionPerformed(evt);
/*       */       }
/*  9750 */     });
/*  9751 */     this.pnlCharts.add(this.chkChartFront, new AbsoluteConstraints(480, 30, 200, -1));
/*       */     
/*  9753 */     this.chkChartRear.setBackground(Color.pink);
/*  9754 */     this.chkChartRear.setSelected(true);
/*  9755 */     this.chkChartRear.setText("Show Rear Arc Weapons");
/*  9756 */     this.chkChartRear.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9758 */         frmMainWide.this.chkChartRearActionPerformed(evt);
/*       */       }
/*  9760 */     });
/*  9761 */     this.pnlCharts.add(this.chkChartRear, new AbsoluteConstraints(480, 60, 200, -1));
/*       */     
/*  9763 */     this.chkChartRight.setBackground(Color.green);
/*  9764 */     this.chkChartRight.setSelected(true);
/*  9765 */     this.chkChartRight.setText("Show Right Arm Arc Weapons");
/*  9766 */     this.chkChartRight.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9768 */         frmMainWide.this.chkChartRightActionPerformed(evt);
/*       */       }
/*  9770 */     });
/*  9771 */     this.pnlCharts.add(this.chkChartRight, new AbsoluteConstraints(480, 90, 200, -1));
/*       */     
/*  9773 */     this.chkChartLeft.setBackground(Color.orange);
/*  9774 */     this.chkChartLeft.setSelected(true);
/*  9775 */     this.chkChartLeft.setText("Show Left Arm Arc Weapons");
/*  9776 */     this.chkChartLeft.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9778 */         frmMainWide.this.chkChartLeftActionPerformed(evt);
/*       */       }
/*  9780 */     });
/*  9781 */     this.pnlCharts.add(this.chkChartLeft, new AbsoluteConstraints(480, 120, 200, -1));
/*       */     
/*  9783 */     this.btnBracketChart.setText("Show Weapon Bracket Chart");
/*  9784 */     this.btnBracketChart.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9786 */         frmMainWide.this.btnBracketChartActionPerformed(evt);
/*       */       }
/*  9788 */     });
/*  9789 */     this.pnlCharts.add(this.btnBracketChart, new AbsoluteConstraints(240, 70, 210, -1));
/*       */     
/*  9791 */     this.chkAverageDamage.setText("Show Average Damage");
/*  9792 */     this.chkAverageDamage.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9794 */         frmMainWide.this.chkAverageDamageActionPerformed(evt);
/*       */       }
/*  9796 */     });
/*  9797 */     this.pnlCharts.add(this.chkAverageDamage, new AbsoluteConstraints(240, 110, -1, -1));
/*       */     
/*  9799 */     this.chkShowTextNotGraph.setText("Show Text Instead of Graph");
/*  9800 */     this.chkShowTextNotGraph.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9802 */         frmMainWide.this.chkShowTextNotGraphActionPerformed(evt);
/*       */       }
/*  9804 */     });
/*  9805 */     this.pnlCharts.add(this.chkShowTextNotGraph, new AbsoluteConstraints(240, 130, -1, -1));
/*       */     
/*  9807 */     this.pnlBFStats.setBorder(BorderFactory.createTitledBorder("BattleForce Stats"));
/*  9808 */     this.pnlBFStats.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/*  9810 */     this.jLabel66.setText("MV");
/*  9811 */     this.pnlBFStats.add(this.jLabel66, new AbsoluteConstraints(20, 30, -1, -1));
/*       */     
/*  9813 */     this.jLabel67.setText("S (+0)");
/*  9814 */     this.pnlBFStats.add(this.jLabel67, new AbsoluteConstraints(60, 30, -1, -1));
/*       */     
/*  9816 */     this.jLabel68.setText("M (+2)");
/*  9817 */     this.pnlBFStats.add(this.jLabel68, new AbsoluteConstraints(110, 30, -1, -1));
/*       */     
/*  9819 */     this.jLabel69.setText("L (+4)");
/*  9820 */     this.pnlBFStats.add(this.jLabel69, new AbsoluteConstraints(160, 30, -1, -1));
/*       */     
/*  9822 */     this.jLabel70.setText("E (+6)");
/*  9823 */     this.pnlBFStats.add(this.jLabel70, new AbsoluteConstraints(210, 30, -1, -1));
/*       */     
/*  9825 */     this.jLabel71.setText("Wt.");
/*  9826 */     this.pnlBFStats.add(this.jLabel71, new AbsoluteConstraints(260, 30, -1, -1));
/*       */     
/*  9828 */     this.jLabel72.setText("OV");
/*  9829 */     this.pnlBFStats.add(this.jLabel72, new AbsoluteConstraints(300, 30, -1, -1));
/*       */     
/*  9831 */     this.jLabel73.setText("Armor:");
/*  9832 */     this.pnlBFStats.add(this.jLabel73, new AbsoluteConstraints(330, 30, -1, -1));
/*       */     
/*  9834 */     this.jLabel74.setText("Structure:");
/*  9835 */     this.pnlBFStats.add(this.jLabel74, new AbsoluteConstraints(330, 60, -1, -1));
/*       */     
/*  9837 */     this.jLabel75.setText("Special Abilities:");
/*  9838 */     this.pnlBFStats.add(this.jLabel75, new AbsoluteConstraints(20, 110, -1, -1));
/*       */     
/*  9840 */     this.lblBFMV.setHorizontalAlignment(0);
/*  9841 */     this.lblBFMV.setText("0");
/*  9842 */     this.pnlBFStats.add(this.lblBFMV, new AbsoluteConstraints(10, 50, 30, -1));
/*       */     
/*  9844 */     this.lblBFWt.setHorizontalAlignment(0);
/*  9845 */     this.lblBFWt.setText("1");
/*  9846 */     this.pnlBFStats.add(this.lblBFWt, new AbsoluteConstraints(250, 50, 30, -1));
/*       */     
/*  9848 */     this.lblBFOV.setHorizontalAlignment(0);
/*  9849 */     this.lblBFOV.setText("0");
/*  9850 */     this.pnlBFStats.add(this.lblBFOV, new AbsoluteConstraints(290, 50, 30, -1));
/*       */     
/*  9852 */     this.lblBFExtreme.setHorizontalAlignment(0);
/*  9853 */     this.lblBFExtreme.setText("0");
/*  9854 */     this.pnlBFStats.add(this.lblBFExtreme, new AbsoluteConstraints(210, 50, 30, -1));
/*       */     
/*  9856 */     this.lblBFShort.setHorizontalAlignment(0);
/*  9857 */     this.lblBFShort.setText("0");
/*  9858 */     this.pnlBFStats.add(this.lblBFShort, new AbsoluteConstraints(60, 50, 30, -1));
/*       */     
/*  9860 */     this.lblBFMedium.setHorizontalAlignment(0);
/*  9861 */     this.lblBFMedium.setText("0");
/*  9862 */     this.pnlBFStats.add(this.lblBFMedium, new AbsoluteConstraints(110, 50, 30, -1));
/*       */     
/*  9864 */     this.lblBFLong.setHorizontalAlignment(0);
/*  9865 */     this.lblBFLong.setText("0");
/*  9866 */     this.pnlBFStats.add(this.lblBFLong, new AbsoluteConstraints(160, 50, 30, -1));
/*       */     
/*  9868 */     this.lblBFArmor.setHorizontalAlignment(0);
/*  9869 */     this.lblBFArmor.setText("0");
/*  9870 */     this.pnlBFStats.add(this.lblBFArmor, new AbsoluteConstraints(390, 30, 30, -1));
/*       */     
/*  9872 */     this.lblBFStructure.setHorizontalAlignment(0);
/*  9873 */     this.lblBFStructure.setText("0");
/*  9874 */     this.pnlBFStats.add(this.lblBFStructure, new AbsoluteConstraints(390, 60, 30, -1));
/*       */     
/*  9876 */     this.lblBFSA.setText("Placeholder");
/*  9877 */     this.pnlBFStats.add(this.lblBFSA, new AbsoluteConstraints(20, 130, 430, 20));
/*       */     
/*  9879 */     this.jLabel37.setText("Points:");
/*  9880 */     this.pnlBFStats.add(this.jLabel37, new AbsoluteConstraints(430, 30, -1, -1));
/*       */     
/*  9882 */     this.lblBFPoints.setText("0");
/*  9883 */     this.pnlBFStats.add(this.lblBFPoints, new AbsoluteConstraints(490, 30, -1, -1));
/*       */     
/*  9885 */     this.pnlCharts.add(this.pnlBFStats, new AbsoluteConstraints(690, 10, 520, 200));
/*       */     
/*  9887 */     this.jPanel7.setBorder(BorderFactory.createTitledBorder("Conversion Steps"));
/*  9888 */     this.jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/*  9890 */     this.jTextAreaBFConversion.setColumns(20);
/*  9891 */     this.jTextAreaBFConversion.setEditable(false);
/*  9892 */     this.jTextAreaBFConversion.setRows(5);
/*  9893 */     this.jScrollPane14.setViewportView(this.jTextAreaBFConversion);
/*       */     
/*  9895 */     this.jPanel7.add(this.jScrollPane14, new AbsoluteConstraints(10, 20, 500, 200));
/*       */     
/*  9897 */     this.pnlCharts.add(this.jPanel7, new AbsoluteConstraints(690, 220, 520, 230));
/*       */     
/*  9899 */     this.tbpMainTabPane.addTab("Charts & BattleForce", this.pnlCharts);
/*       */     
/*  9901 */     gridBagConstraints = new GridBagConstraints();
/*  9902 */     gridBagConstraints.gridx = 0;
/*  9903 */     gridBagConstraints.gridy = 1;
/*  9904 */     getContentPane().add(this.tbpMainTabPane, gridBagConstraints);
/*       */     
/*  9906 */     this.pnlInfoPanel.setMaximumSize(new Dimension(32767, 26));
/*  9907 */     this.pnlInfoPanel.setMinimumSize(new Dimension(730, 26));
/*  9908 */     this.pnlInfoPanel.setLayout(new javax.swing.BoxLayout(this.pnlInfoPanel, 2));
/*       */     
/*  9910 */     this.txtInfoTonnage.setEditable(false);
/*  9911 */     this.txtInfoTonnage.setFont(new Font("Arial", 0, 11));
/*  9912 */     this.txtInfoTonnage.setHorizontalAlignment(0);
/*  9913 */     this.txtInfoTonnage.setText("Tons: 000.00");
/*  9914 */     this.txtInfoTonnage.setDisabledTextColor(new Color(0, 0, 0));
/*  9915 */     this.txtInfoTonnage.setMaximumSize(new Dimension(72, 20));
/*  9916 */     this.txtInfoTonnage.setMinimumSize(new Dimension(72, 20));
/*  9917 */     this.txtInfoTonnage.setPreferredSize(new Dimension(72, 20));
/*  9918 */     this.pnlInfoPanel.add(this.txtInfoTonnage);
/*       */     
/*  9920 */     this.txtInfoFreeTons.setEditable(false);
/*  9921 */     this.txtInfoFreeTons.setFont(new Font("Arial", 0, 11));
/*  9922 */     this.txtInfoFreeTons.setHorizontalAlignment(0);
/*  9923 */     this.txtInfoFreeTons.setText("Free Tons: 000.00");
/*  9924 */     this.txtInfoFreeTons.setMaximumSize(new Dimension(96, 20));
/*  9925 */     this.txtInfoFreeTons.setMinimumSize(new Dimension(96, 20));
/*  9926 */     this.txtInfoFreeTons.setPreferredSize(new Dimension(96, 20));
/*  9927 */     this.pnlInfoPanel.add(this.txtInfoFreeTons);
/*       */     
/*  9929 */     this.txtInfoMaxHeat.setEditable(false);
/*  9930 */     this.txtInfoMaxHeat.setFont(new Font("Arial", 0, 11));
/*  9931 */     this.txtInfoMaxHeat.setHorizontalAlignment(0);
/*  9932 */     this.txtInfoMaxHeat.setText("Max Heat: 000");
/*  9933 */     this.txtInfoMaxHeat.setMaximumSize(new Dimension(77, 20));
/*  9934 */     this.txtInfoMaxHeat.setMinimumSize(new Dimension(77, 20));
/*  9935 */     this.txtInfoMaxHeat.setPreferredSize(new Dimension(77, 20));
/*  9936 */     this.pnlInfoPanel.add(this.txtInfoMaxHeat);
/*       */     
/*  9938 */     this.txtInfoHeatDiss.setEditable(false);
/*  9939 */     this.txtInfoHeatDiss.setFont(new Font("Arial", 0, 11));
/*  9940 */     this.txtInfoHeatDiss.setHorizontalAlignment(0);
/*  9941 */     this.txtInfoHeatDiss.setText("Heat Dissipation: 000");
/*  9942 */     this.txtInfoHeatDiss.setMaximumSize(new Dimension(118, 20));
/*  9943 */     this.txtInfoHeatDiss.setMinimumSize(new Dimension(118, 20));
/*  9944 */     this.txtInfoHeatDiss.setPreferredSize(new Dimension(118, 20));
/*  9945 */     this.pnlInfoPanel.add(this.txtInfoHeatDiss);
/*       */     
/*  9947 */     this.txtInfoFreeCrits.setEditable(false);
/*  9948 */     this.txtInfoFreeCrits.setFont(new Font("Arial", 0, 11));
/*  9949 */     this.txtInfoFreeCrits.setHorizontalAlignment(0);
/*  9950 */     this.txtInfoFreeCrits.setText("Free Crits: -00");
/*  9951 */     this.txtInfoFreeCrits.setMaximumSize(new Dimension(77, 20));
/*  9952 */     this.txtInfoFreeCrits.setMinimumSize(new Dimension(77, 20));
/*  9953 */     this.txtInfoFreeCrits.setPreferredSize(new Dimension(77, 20));
/*  9954 */     this.pnlInfoPanel.add(this.txtInfoFreeCrits);
/*       */     
/*  9956 */     this.txtInfoUnplaced.setEditable(false);
/*  9957 */     this.txtInfoUnplaced.setFont(new Font("Arial", 0, 11));
/*  9958 */     this.txtInfoUnplaced.setHorizontalAlignment(0);
/*  9959 */     this.txtInfoUnplaced.setText("Unplaced Crits: 00");
/*  9960 */     this.txtInfoUnplaced.setMaximumSize(new Dimension(110, 20));
/*  9961 */     this.txtInfoUnplaced.setMinimumSize(new Dimension(110, 20));
/*  9962 */     this.txtInfoUnplaced.setPreferredSize(new Dimension(110, 20));
/*  9963 */     this.pnlInfoPanel.add(this.txtInfoUnplaced);
/*       */     
/*  9965 */     this.txtInfoBattleValue.setEditable(false);
/*  9966 */     this.txtInfoBattleValue.setFont(new Font("Arial", 0, 11));
/*  9967 */     this.txtInfoBattleValue.setHorizontalAlignment(0);
/*  9968 */     this.txtInfoBattleValue.setText("BV: 00,000");
/*  9969 */     this.txtInfoBattleValue.setMaximumSize(new Dimension(62, 20));
/*  9970 */     this.txtInfoBattleValue.setMinimumSize(new Dimension(62, 20));
/*  9971 */     this.txtInfoBattleValue.setPreferredSize(new Dimension(62, 20));
/*  9972 */     this.pnlInfoPanel.add(this.txtInfoBattleValue);
/*       */     
/*  9974 */     this.txtInfoCost.setEditable(false);
/*  9975 */     this.txtInfoCost.setFont(new Font("Arial", 0, 11));
/*  9976 */     this.txtInfoCost.setHorizontalAlignment(0);
/*  9977 */     this.txtInfoCost.setText("Cost: 000,000,000");
/*  9978 */     this.txtInfoCost.setMaximumSize(new Dimension(125, 20));
/*  9979 */     this.txtInfoCost.setMinimumSize(new Dimension(125, 20));
/*  9980 */     this.txtInfoCost.setPreferredSize(new Dimension(125, 20));
/*  9981 */     this.pnlInfoPanel.add(this.txtInfoCost);
/*       */     
/*  9983 */     this.txtChatInfo.setBackground(new Color(238, 238, 238));
/*  9984 */     this.txtChatInfo.setEditable(false);
/*  9985 */     this.txtChatInfo.setFont(new Font("Dialog", 0, 11));
/*  9986 */     this.txtChatInfo.setMaximumSize(new Dimension(475, 20));
/*  9987 */     this.txtChatInfo.setMinimumSize(new Dimension(475, 20));
/*  9988 */     this.txtChatInfo.setPreferredSize(new Dimension(475, 20));
/*  9989 */     this.pnlInfoPanel.add(this.txtChatInfo);
/*       */     
/*  9991 */     gridBagConstraints = new GridBagConstraints();
/*  9992 */     gridBagConstraints.gridx = 0;
/*  9993 */     gridBagConstraints.gridy = 2;
/*  9994 */     gridBagConstraints.fill = 2;
/*  9995 */     gridBagConstraints.insets = new Insets(4, 4, 4, 0);
/*  9996 */     getContentPane().add(this.pnlInfoPanel, gridBagConstraints);
/*       */     
/*  9998 */     this.mnuFile.setText("File");
/*  9999 */     this.mnuFile.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10001 */         frmMainWide.this.mnuFileActionPerformed(evt);
/*       */       }
/*       */       
/* 10004 */     });
/* 10005 */     this.mnuNewMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(78, 8));
/* 10006 */     this.mnuNewMech.setText("New Mech");
/* 10007 */     this.mnuNewMech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10009 */         frmMainWide.this.mnuNewMechActionPerformed(evt);
/*       */       }
/* 10011 */     });
/* 10012 */     this.mnuFile.add(this.mnuNewMech);
/*       */     
/* 10014 */     this.mnuLoad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(76, 8));
/* 10015 */     this.mnuLoad.setText("Load Mech");
/* 10016 */     this.mnuLoad.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10018 */         frmMainWide.this.mnuLoadActionPerformed(evt);
/*       */       }
/* 10020 */     });
/* 10021 */     this.mnuFile.add(this.mnuLoad);
/*       */     
/* 10023 */     this.mnuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, 8));
/* 10024 */     this.mnuOpen.setText("Open");
/* 10025 */     this.mnuOpen.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10027 */         frmMainWide.this.mnuOpenActionPerformed(evt);
/*       */       }
/* 10029 */     });
/* 10030 */     this.mnuFile.add(this.mnuOpen);
/*       */     
/* 10032 */     this.mnuImport.setText("Import Mech...");
/*       */     
/* 10034 */     this.mnuImportHMP.setText("from Heavy Metal Pro (HMP)");
/* 10035 */     this.mnuImportHMP.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10037 */         frmMainWide.this.mnuImportHMPActionPerformed(evt);
/*       */       }
/* 10039 */     });
/* 10040 */     this.mnuImport.add(this.mnuImportHMP);
/*       */     
/* 10042 */     this.mnuBatchHMP.setText("Batch Import HMP Files");
/* 10043 */     this.mnuBatchHMP.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10045 */         frmMainWide.this.mnuBatchHMPActionPerformed(evt);
/*       */       }
/* 10047 */     });
/* 10048 */     this.mnuImport.add(this.mnuBatchHMP);
/*       */     
/* 10050 */     this.mnuFile.add(this.mnuImport);
/* 10051 */     this.mnuFile.add(this.jSeparator16);
/*       */     
/* 10053 */     this.mnuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(83, 8));
/* 10054 */     this.mnuSave.setText("Save Mech");
/* 10055 */     this.mnuSave.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10057 */         frmMainWide.this.mnuSaveActionPerformed(evt);
/*       */       }
/* 10059 */     });
/* 10060 */     this.mnuFile.add(this.mnuSave);
/*       */     
/* 10062 */     this.mnuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(83, 10));
/* 10063 */     this.mnuSaveAs.setText("Save Mech As...");
/* 10064 */     this.mnuSaveAs.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10066 */         frmMainWide.this.mnuSaveAsActionPerformed(evt);
/*       */       }
/* 10068 */     });
/* 10069 */     this.mnuFile.add(this.mnuSaveAs);
/*       */     
/* 10071 */     this.mnuExport.setText("Export Mech...");
/*       */     
/* 10073 */     this.mnuExportHTML.setText("to HTML (Web)");
/* 10074 */     this.mnuExportHTML.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10076 */         frmMainWide.this.mnuExportHTMLActionPerformed(evt);
/*       */       }
/* 10078 */     });
/* 10079 */     this.mnuExport.add(this.mnuExportHTML);
/*       */     
/* 10081 */     this.mnuExportMTF.setText("to MTF (MegaMek)");
/* 10082 */     this.mnuExportMTF.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10084 */         frmMainWide.this.mnuExportMTFActionPerformed(evt);
/*       */       }
/* 10086 */     });
/* 10087 */     this.mnuExport.add(this.mnuExportMTF);
/*       */     
/* 10089 */     this.mnuExportTXT.setText("to TXT (Text)");
/* 10090 */     this.mnuExportTXT.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10092 */         frmMainWide.this.mnuExportTXTActionPerformed(evt);
/*       */       }
/* 10094 */     });
/* 10095 */     this.mnuExport.add(this.mnuExportTXT);
/*       */     
/* 10097 */     this.mnuExportClipboard.setText("to Clipboard (Text)");
/* 10098 */     this.mnuExportClipboard.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10100 */         frmMainWide.this.mnuExportClipboardActionPerformed(evt);
/*       */       }
/* 10102 */     });
/* 10103 */     this.mnuExport.add(this.mnuExportClipboard);
/*       */     
/* 10105 */     this.mnuCreateTCGMech.setText("to TCG Format (Card)");
/* 10106 */     this.mnuCreateTCGMech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10108 */         frmMainWide.this.mnuCreateTCGMechActionPerformed(evt);
/*       */       }
/* 10110 */     });
/* 10111 */     this.mnuExport.add(this.mnuCreateTCGMech);
/*       */     
/* 10113 */     this.mnuFile.add(this.mnuExport);
/* 10114 */     this.mnuFile.add(this.jSeparator20);
/*       */     
/* 10116 */     this.mnuPrint.setText("Print");
/*       */     
/* 10118 */     this.mnuPrintCurrentMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 2));
/* 10119 */     this.mnuPrintCurrentMech.setText("Current Mech");
/* 10120 */     this.mnuPrintCurrentMech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10122 */         frmMainWide.this.mnuPrintCurrentMechActionPerformed(evt);
/*       */       }
/* 10124 */     });
/* 10125 */     this.mnuPrint.add(this.mnuPrintCurrentMech);
/*       */     
/* 10127 */     this.mnuPrintSavedMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 3));
/* 10128 */     this.mnuPrintSavedMech.setText("Saved Mech");
/* 10129 */     this.mnuPrintSavedMech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10131 */         frmMainWide.this.mnuPrintSavedMechActionPerformed(evt);
/*       */       }
/* 10133 */     });
/* 10134 */     this.mnuPrint.add(this.mnuPrintSavedMech);
/*       */     
/* 10136 */     this.mnuPrintBatch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(66, 3));
/* 10137 */     this.mnuPrintBatch.setText("Batch Print Mechs");
/* 10138 */     this.mnuPrintBatch.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10140 */         frmMainWide.this.mnuPrintBatchActionPerformed(evt);
/*       */       }
/* 10142 */     });
/* 10143 */     this.mnuPrint.add(this.mnuPrintBatch);
/*       */     
/* 10145 */     this.mnuFile.add(this.mnuPrint);
/*       */     
/* 10147 */     this.mnuPrintPreview.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 10));
/* 10148 */     this.mnuPrintPreview.setText("Print Preview");
/* 10149 */     this.mnuPrintPreview.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10151 */         frmMainWide.this.mnuPrintPreviewActionPerformed(evt);
/*       */       }
/* 10153 */     });
/* 10154 */     this.mnuFile.add(this.mnuPrintPreview);
/*       */     
/* 10156 */     this.mnuPostS7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 8));
/* 10157 */     this.mnuPostS7.setText("Post Mech to Solaris7.com");
/* 10158 */     this.mnuPostS7.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10160 */         frmMainWide.this.mnuPostS7ActionPerformed(evt);
/*       */       }
/* 10162 */     });
/* 10163 */     this.mnuFile.add(this.mnuPostS7);
/* 10164 */     this.mnuFile.add(this.jSeparator17);
/*       */     
/* 10166 */     this.mnuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(88, 8));
/* 10167 */     this.mnuExit.setText("Exit");
/* 10168 */     this.mnuExit.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10170 */         frmMainWide.this.mnuExitActionPerformed(evt);
/*       */       }
/* 10172 */     });
/* 10173 */     this.mnuFile.add(this.mnuExit);
/*       */     
/* 10175 */     this.mnuMainMenu.add(this.mnuFile);
/*       */     
/* 10177 */     this.mnuClearFluff.setText("Tools");
/*       */     
/* 10179 */     this.mnuSummary.setAccelerator(javax.swing.KeyStroke.getKeyStroke(85, 8));
/* 10180 */     this.mnuSummary.setText("Show Summary");
/* 10181 */     this.mnuSummary.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10183 */         frmMainWide.this.mnuSummaryActionPerformed(evt);
/*       */       }
/* 10185 */     });
/* 10186 */     this.mnuClearFluff.add(this.mnuSummary);
/*       */     
/* 10188 */     this.mnuCostBVBreakdown.setText("Cost/BV Breakdown");
/* 10189 */     this.mnuCostBVBreakdown.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10191 */         frmMainWide.this.mnuCostBVBreakdownActionPerformed(evt);
/*       */       }
/* 10193 */     });
/* 10194 */     this.mnuClearFluff.add(this.mnuCostBVBreakdown);
/*       */     
/* 10196 */     this.mnuTextTRO.setText("Show Text TRO Format");
/* 10197 */     this.mnuTextTRO.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10199 */         frmMainWide.this.mnuTextTROActionPerformed(evt);
/*       */       }
/* 10201 */     });
/* 10202 */     this.mnuClearFluff.add(this.mnuTextTRO);
/* 10203 */     this.mnuClearFluff.add(this.jSeparator15);
/*       */     
/* 10205 */     this.mnuBFB.setText("Load Force Balancer");
/* 10206 */     this.mnuBFB.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10208 */         frmMainWide.this.mnuBFBActionPerformed(evt);
/*       */       }
/* 10210 */     });
/* 10211 */     this.mnuClearFluff.add(this.mnuBFB);
/* 10212 */     this.mnuClearFluff.add(this.jSeparator27);
/*       */     
/* 10214 */     this.mnuOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, 8));
/* 10215 */     this.mnuOptions.setText("Preferences");
/* 10216 */     this.mnuOptions.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10218 */         frmMainWide.this.mnuOptionsActionPerformed(evt);
/*       */       }
/* 10220 */     });
/* 10221 */     this.mnuClearFluff.add(this.mnuOptions);
/*       */     
/* 10223 */     this.mnuViewToolbar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(84, 8));
/* 10224 */     this.mnuViewToolbar.setSelected(true);
/* 10225 */     this.mnuViewToolbar.setText("View Toolbar");
/* 10226 */     this.mnuViewToolbar.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10228 */         frmMainWide.this.mnuViewToolbarActionPerformed(evt);
/*       */       }
/* 10230 */     });
/* 10231 */     this.mnuClearFluff.add(this.mnuViewToolbar);
/*       */     
/* 10233 */     this.mnuClearUserData.setText("Clear User Data");
/* 10234 */     this.mnuClearUserData.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10236 */         frmMainWide.this.mnuClearUserDataActionPerformed(evt);
/*       */       }
/* 10238 */     });
/* 10239 */     this.mnuClearFluff.add(this.mnuClearUserData);
/* 10240 */     this.mnuClearFluff.add(this.jSeparator30);
/*       */     
/* 10242 */     this.mnuUnlock.setText("Unlock Chassis");
/* 10243 */     this.mnuUnlock.setEnabled(false);
/* 10244 */     this.mnuUnlock.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10246 */         frmMainWide.this.mnuUnlockActionPerformed(evt);
/*       */       }
/* 10248 */     });
/* 10249 */     this.mnuClearFluff.add(this.mnuUnlock);
/*       */     
/* 10251 */     this.jMenuItem1.setText("Clear All Fluff");
/* 10252 */     this.jMenuItem1.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10254 */         frmMainWide.this.jMenuItem1ActionPerformed(evt);
/*       */       }
/* 10256 */     });
/* 10257 */     this.mnuClearFluff.add(this.jMenuItem1);
/*       */     
/* 10259 */     this.mnuMainMenu.add(this.mnuClearFluff);
/*       */     
/* 10261 */     this.mnuHelp.setText("Help");
/*       */     
/* 10263 */     this.mnuCredits.setText("Credits");
/* 10264 */     this.mnuCredits.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10266 */         frmMainWide.this.mnuCreditsActionPerformed(evt);
/*       */       }
/* 10268 */     });
/* 10269 */     this.mnuHelp.add(this.mnuCredits);
/*       */     
/* 10271 */     this.mnuAboutSSW.setText("About SSW");
/* 10272 */     this.mnuAboutSSW.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10274 */         frmMainWide.this.mnuAboutSSWActionPerformed(evt);
/*       */       }
/* 10276 */     });
/* 10277 */     this.mnuHelp.add(this.mnuAboutSSW);
/*       */     
/* 10279 */     this.mnuMainMenu.add(this.mnuHelp);
/*       */     
/* 10281 */     setJMenuBar(this.mnuMainMenu);
/*       */     
/* 10283 */     pack();
/*       */   }
/*       */   
/*       */   private void mnuExitActionPerformed(ActionEvent evt) {
/* 10287 */     if (this.CurMech.HasChanged()) {
/* 10288 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 10290 */       if (choice == 1) return;
/*       */     }
/* 10292 */     CloseProgram();
/*       */   }
/*       */   
/*       */   private void CloseProgram() {
/*       */     try {
/* 10297 */       if (this.BatchWindow != null) this.BatchWindow.dispose();
/* 10298 */       if (this.dOpen != null) this.dOpen.dispose();
/* 10299 */       if (this.dForce != null) this.dForce.dispose();
/*       */     } catch (Exception e) {
/* 10301 */       System.out.println(e.getMessage());
/*       */     }
/* 10303 */     System.out.flush();
/*       */     
/* 10305 */     System.exit(0);
/*       */   }
/*       */   
/*       */   private void mnuCreditsActionPerformed(ActionEvent evt) {
/* 10309 */     dlgCredits Credits = new dlgCredits(this, true);
/* 10310 */     Credits.setLocationRelativeTo(this);
/* 10311 */     Credits.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuOptionsActionPerformed(ActionEvent evt) {
/* 10315 */     dlgPrefs preferences = new dlgPrefs(this, true);
/* 10316 */     preferences.setLocationRelativeTo(this);
/* 10317 */     preferences.setVisible(true);
/* 10318 */     this.Mechrender.Reset();
/* 10319 */     ResetAmmo();
/* 10320 */     RefreshInternalPoints();
/* 10321 */     RefreshSummary();
/* 10322 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */ 
/*       */   private void chkArtemisSRMActionPerformed(ActionEvent evt) {}
/*       */   
/*       */ 
/*       */   private void chkArtemisLRMActionPerformed(ActionEvent evt) {}
/*       */   
/*       */   private void chkArtemisMMLActionPerformed(ActionEvent evt) {}
/*       */   
/*       */   private void mnuSummaryActionPerformed(ActionEvent evt)
/*       */   {
/* 10335 */     SolidifyMech();
/* 10336 */     dlgSummaryInfo Summary = new dlgSummaryInfo(this, true);
/* 10337 */     Summary.setLocationRelativeTo(this);
/* 10338 */     Summary.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuAboutSSWActionPerformed(ActionEvent evt) {
/* 10342 */     dlgAboutBox about = new dlgAboutBox(this, true);
/* 10343 */     about.setLocationRelativeTo(this);
/* 10344 */     about.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuExportTXTActionPerformed(ActionEvent evt) {
/* 10348 */     this.SetSource = false;
/* 10349 */     btnExportTXTActionPerformed(evt);
/* 10350 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */   private void mnuExportHTMLActionPerformed(ActionEvent evt) {
/* 10354 */     this.SetSource = false;
/* 10355 */     btnExportHTMLActionPerformed(evt);
/* 10356 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */   private void mnuExportMTFActionPerformed(ActionEvent evt) {
/* 10360 */     this.SetSource = false;
/* 10361 */     btnExportMTFActionPerformed(evt);
/* 10362 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */   private void mnuNewMechActionPerformed(ActionEvent evt) {
/* 10366 */     if (this.CurMech.HasChanged()) {
/* 10367 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 10369 */       if (choice == 1) return;
/*       */     }
/* 10371 */     GetNewMech();
/* 10372 */     this.Prefs.put("Currentfile", "");
/*       */   }
/*       */   
/*       */   private void mnuOpenActionPerformed(ActionEvent evt)
/*       */   {
/* 10377 */     if (this.CurMech.HasChanged()) {
/* 10378 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 10380 */       if (choice == 1) return;
/*       */     }
/* 10382 */     this.dOpen.Requestor = 0;
/* 10383 */     this.dOpen.setLocationRelativeTo(null);
/*       */     
/* 10385 */     this.dOpen.setSize(1024, 600);
/* 10386 */     this.dOpen.setVisible(true);
/*       */   }
/*       */   
/*       */   private void cmbOmniVariantActionPerformed(ActionEvent evt) {
/* 10390 */     SaveOmniFluffInfo();
/* 10391 */     String variant = (String)this.cmbOmniVariant.getSelectedItem();
/* 10392 */     boolean changed = this.CurMech.HasChanged();
/*       */     
/* 10394 */     this.CurMech.SetCurLoadout(variant);
/*       */     
/*       */ 
/* 10397 */     LoadOmniFluffInfo();
/* 10398 */     FixTransferHandlers();
/* 10399 */     SetLoadoutArrays();
/* 10400 */     SetWeaponChoosers();
/* 10401 */     BuildJumpJetSelector();
/* 10402 */     this.cmbJumpJetType.setSelectedItem(this.CurMech.GetJumpJets().LookupName());
/* 10403 */     FixJJSpinnerModel();
/* 10404 */     FixHeatSinkSpinnerModel();
/* 10405 */     RefreshOmniVariants();
/* 10406 */     RefreshEquipment();
/* 10407 */     RefreshOmniChoices();
/* 10408 */     RefreshSummary();
/* 10409 */     RefreshInfoPane();
/*       */     
/*       */ 
/*       */ 
/* 10413 */     this.CurMech.SetChanged(changed);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private void mnuPostS7ActionPerformed(ActionEvent evt)
/*       */   {
/* 10423 */     QuickSave();
/*       */     
/* 10425 */     String CurLoadout = "";
/* 10426 */     if (this.CurMech.IsOmnimech()) {
/* 10427 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/*       */ 
/* 10431 */     SolidifyMech();
/*       */     
/* 10433 */     if (!VerifyMech(evt)) {
/* 10434 */       return;
/*       */     }
/*       */     
/* 10437 */     dlgPostToSolaris7 PostS7 = new dlgPostToSolaris7(this, true);
/* 10438 */     PostS7.setLocationRelativeTo(this);
/* 10439 */     PostS7.setVisible(true);
/*       */     
/*       */ 
/* 10442 */     this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 10443 */     cmbOmniVariantActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void mnuClearUserDataActionPerformed(ActionEvent evt) {
/* 10447 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "This will remove all Solaris 7 user data.\nAre you sure you want to continue?", "Clear User Data?", 0);
/*       */     
/* 10449 */     if (choice == 1) {
/* 10450 */       return;
/*       */     }
/* 10452 */     this.Prefs.put("S7Callsign", "");
/* 10453 */     this.Prefs.put("S7Password", "");
/* 10454 */     this.Prefs.put("S7UserID", "");
/*       */   }
/*       */   
/*       */ 
/*       */   private void mnuSaveActionPerformed(ActionEvent evt)
/*       */   {
/* 10460 */     setCursor(this.Hourglass);
/* 10461 */     File savemech = GetSaveFile("ssw", this.Prefs.get("LastOpenDirectory", ""), true, false);
/* 10462 */     if (savemech == null) {
/* 10463 */       setCursor(this.NormalCursor);
/* 10464 */       return;
/*       */     }
/*       */     
/*       */     try
/*       */     {
/* 10469 */       this.Prefs.put("LastOpenDirectory", savemech.getCanonicalPath().replace(savemech.getName(), ""));
/* 10470 */       this.Prefs.put("LastOpenFile", savemech.getName());
/* 10471 */       this.Prefs.put("Currentfile", savemech.getCanonicalPath());
/*       */     } catch (IOException e) {
/* 10473 */       Media.Messager(this, "There was a problem with the file:\n" + e.getMessage());
/* 10474 */       setCursor(this.NormalCursor);
/* 10475 */       return;
/*       */     }
/*       */     
/*       */ 
/* 10479 */     String CurLoadout = "";
/* 10480 */     if (this.CurMech.IsOmnimech()) {
/* 10481 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/* 10482 */       SaveOmniFluffInfo();
/*       */     }
/*       */     
/*       */ 
/* 10486 */     filehandlers.MechWriter XMLw = new filehandlers.MechWriter(this.CurMech);
/*       */     try {
/* 10488 */       String file = savemech.getCanonicalPath();
/* 10489 */       String ext = Utils.getExtension(savemech);
/* 10490 */       if ((ext == null) || (ext.equals(""))) {
/* 10491 */         file = file + ".ssw";
/*       */       }
/* 10493 */       else if (!ext.equals("ssw")) {
/* 10494 */         file.replace("." + ext, ".ssw");
/*       */       }
/*       */       
/* 10497 */       XMLw.WriteXML(file);
/*       */       
/*       */ 
/* 10500 */       if ((evt != null) && (evt.getActionCommand().equals("Save Mech"))) {
/* 10501 */         Media.Messager(this, "Mech saved successfully:\n" + file);
/*       */       }
/*       */     } catch (IOException e) {
/* 10504 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 10505 */       setCursor(this.NormalCursor);
/* 10506 */       return;
/*       */     }
/*       */     
/*       */ 
/* 10510 */     if (this.CurMech.IsOmnimech()) {
/* 10511 */       this.SetSource = false;
/* 10512 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 10513 */       cmbOmniVariantActionPerformed(evt);
/* 10514 */       this.SetSource = true;
/*       */     }
/*       */     
/* 10517 */     setCursor(this.NormalCursor);
/* 10518 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/* 10519 */     this.CurMech.SetChanged(false);
/*       */   }
/*       */   
/*       */   private void mnuLoadActionPerformed(ActionEvent evt) {
/* 10523 */     if (this.CurMech.HasChanged()) {
/* 10524 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 10526 */       if (choice == 1) { return;
/*       */       }
/*       */     }
/* 10529 */     Mech m = LoadMech();
/* 10530 */     if (m == null) {
/* 10531 */       return;
/*       */     }
/* 10533 */     this.CurMech = m;
/* 10534 */     LoadMechIntoGUI();
/* 10535 */     this.CurMech.SetChanged(false);
/*       */   }
/*       */   
/*       */   private void mnuSaveAsActionPerformed(ActionEvent evt) {
/* 10539 */     setCursor(this.Hourglass);
/* 10540 */     File savemech = GetSaveFile("ssw", this.Prefs.get("LastOpenDirectory", ""), false, false);
/* 10541 */     if (savemech == null) {
/* 10542 */       setCursor(this.NormalCursor);
/* 10543 */       return;
/*       */     }
/*       */     
/*       */ 
/* 10547 */     String CurLoadout = "";
/* 10548 */     if (this.CurMech.IsOmnimech()) {
/* 10549 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 10554 */     this.CurMech.SetSolaris7ID("0");
/*       */     
/*       */ 
/* 10557 */     filehandlers.MechWriter XMLw = new filehandlers.MechWriter(this.CurMech);
/*       */     try {
/* 10559 */       String file = savemech.getCanonicalPath();
/* 10560 */       String ext = Utils.getExtension(savemech);
/* 10561 */       if ((ext == null) || (ext.equals(""))) {
/* 10562 */         file = file + ".ssw";
/*       */       }
/* 10564 */       else if (!ext.equals("ssw")) {
/* 10565 */         file.replace("." + ext, ".ssw");
/*       */       }
/*       */       
/* 10568 */       XMLw.WriteXML(file);
/*       */       
/* 10570 */       Media.Messager(this, "Mech saved successfully:\n" + file);
/*       */     } catch (IOException e) {
/* 10572 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 10573 */       setCursor(this.NormalCursor);
/* 10574 */       return;
/*       */     }
/*       */     
/*       */     try
/*       */     {
/* 10579 */       this.Prefs.put("LastOpenDirectory", savemech.getCanonicalPath().replace(savemech.getName(), ""));
/* 10580 */       this.Prefs.put("LastOpenFile", savemech.getName());
/*       */     } catch (IOException e) {
/* 10582 */       Media.Messager(this, "There was a problem with the file:\n" + e.getMessage());
/* 10583 */       setCursor(this.NormalCursor);
/* 10584 */       return;
/*       */     }
/*       */     
/*       */ 
/* 10588 */     if (this.CurMech.IsOmnimech()) {
/* 10589 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 10590 */       cmbOmniVariantActionPerformed(evt);
/*       */     }
/* 10592 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/* 10593 */     this.CurMech.SetChanged(false);
/* 10594 */     setCursor(this.NormalCursor);
/*       */   }
/*       */   
/*       */   private void mnuCostBVBreakdownActionPerformed(ActionEvent evt) {
/* 10598 */     SolidifyMech();
/* 10599 */     dlgCostBVBreakdown costbv = new dlgCostBVBreakdown(this, true, this.CurMech);
/* 10600 */     costbv.setLocationRelativeTo(this);
/* 10601 */     costbv.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuPrintCurrentMechActionPerformed(ActionEvent evt)
/*       */   {
/* 10606 */     SolidifyMech();
/*       */     
/* 10608 */     if (VerifyMech(new ActionEvent(this, 1234567890, null))) {
/* 10609 */       PrintMech(this.CurMech);
/*       */     }
/*       */   }
/*       */   
/*       */   private void mnuPrintSavedMechActionPerformed(ActionEvent evt) {
/* 10614 */     Mech m = LoadMech();
/* 10615 */     if (m != null)
/* 10616 */       PrintMech(m);
/*       */   }
/*       */   
/*       */   public Mech LoadMech() {
/* 10620 */     Mech m = null;
/*       */     
/* 10622 */     File tempFile = new File(this.Prefs.get("LastOpenDirectory", ""));
/* 10623 */     JFileChooser fc = new JFileChooser();
/* 10624 */     fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
/*       */       public boolean accept(File f) {
/* 10626 */         if (f.isDirectory()) {
/* 10627 */           return true;
/*       */         }
/*       */         
/* 10630 */         String extension = Utils.getExtension(f);
/* 10631 */         if (extension != null) {
/* 10632 */           if (extension.equals("ssw")) {
/* 10633 */             return true;
/*       */           }
/* 10635 */           return false;
/*       */         }
/*       */         
/* 10638 */         return false;
/*       */       }
/*       */       
/*       */       public String getDescription()
/*       */       {
/* 10643 */         return "*.ssw";
/*       */       }
/* 10645 */     });
/* 10646 */     fc.setAcceptAllFileFilterUsed(false);
/* 10647 */     fc.setCurrentDirectory(tempFile);
/* 10648 */     int returnVal = fc.showDialog(this, "Load Mech");
/* 10649 */     if (returnVal != 0) return m;
/* 10650 */     File loadmech = fc.getSelectedFile();
/* 10651 */     String filename = "";
/*       */     try {
/* 10653 */       filename = loadmech.getCanonicalPath();
/* 10654 */       this.Prefs.put("LastOpenDirectory", loadmech.getCanonicalPath().replace(loadmech.getName(), ""));
/* 10655 */       this.Prefs.put("LastOpenFile", loadmech.getName());
/* 10656 */       this.Prefs.put("Currentfile", loadmech.getCanonicalPath());
/*       */     } catch (Exception e) {
/* 10658 */       Media.Messager(this, "There was a problem opening the file:\n" + e.getMessage());
/* 10659 */       return m;
/*       */     }
/*       */     try
/*       */     {
/* 10663 */       filehandlers.MechReader XMLr = new filehandlers.MechReader();
/* 10664 */       m = XMLr.ReadMech(filename, this.data);
/* 10665 */       if (XMLr.GetMessages().length() > 0) {
/* 10666 */         dlgTextExport Message = new dlgTextExport(this, true, XMLr.GetMessages());
/* 10667 */         Message.setLocationRelativeTo(this);
/* 10668 */         Message.setVisible(true);
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 10673 */       if (e.getMessage() == null) {
/* 10674 */         Media.Messager(this, "An unknown error has occured.  The log file has been updated.");
/* 10675 */         e.printStackTrace();
/*       */       } else {
/* 10677 */         Media.Messager(this, e.getMessage());
/* 10678 */         e.printStackTrace();
/*       */       }
/* 10680 */       return m;
/*       */     }
/*       */     
/* 10683 */     return m;
/*       */   }
/*       */   
/*       */   private void LoadMechFromFile(String filename)
/*       */   {
/* 10688 */     Mech m = null;
/* 10689 */     if (!filename.isEmpty()) {
/*       */       try {
/* 10691 */         filehandlers.MechReader XMLr = new filehandlers.MechReader();
/* 10692 */         m = XMLr.ReadMech(filename, this.data);
/* 10693 */         this.CurMech = m;
/* 10694 */         LoadMechIntoGUI();
/* 10695 */         this.Prefs.put("Currentfile", filename);
/*       */       }
/*       */       catch (Exception e) {
/* 10698 */         Media.Messager(e.getMessage());
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   public void LoadMechIntoGUI()
/*       */   {
/* 10705 */     this.Load = true;
/*       */     
/*       */ 
/* 10708 */     UnlockGUIFromOmni();
/* 10709 */     BuildMechTypeSelector();
/* 10710 */     if (this.CurMech.IsQuad()) {
/* 10711 */       this.cmbMotiveType.setSelectedIndex(1);
/* 10712 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("FLL");
/* 10713 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("FRL");
/* 10714 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("RLL");
/* 10715 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RRL");
/* 10716 */       this.scrRACrits.setPreferredSize(new Dimension(105, 87));
/* 10717 */       this.scrLACrits.setPreferredSize(new Dimension(105, 87));
/*       */     } else {
/* 10719 */       this.cmbMotiveType.setSelectedIndex(0);
/* 10720 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("LA");
/* 10721 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("RA");
/* 10722 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("LL");
/* 10723 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RL");
/* 10724 */       this.scrRACrits.setPreferredSize(new Dimension(105, 170));
/* 10725 */       this.scrLACrits.setPreferredSize(new Dimension(105, 170));
/*       */     }
/* 10727 */     if (this.CurMech.IsIndustrialmech()) {
/* 10728 */       if (this.CurMech.IsPrimitive()) {
/* 10729 */         this.cmbMechType.setSelectedIndex(3);
/*       */       } else {
/* 10731 */         this.cmbMechType.setSelectedIndex(1);
/*       */       }
/*       */     }
/* 10734 */     else if (this.CurMech.IsPrimitive()) {
/* 10735 */       this.cmbMechType.setSelectedIndex(2);
/*       */     } else {
/* 10737 */       this.cmbMechType.setSelectedIndex(0);
/*       */     }
/*       */     
/* 10740 */     this.chkYearRestrict.setSelected(this.CurMech.IsYearRestricted());
/* 10741 */     this.txtProdYear.setText("" + this.CurMech.GetYear());
/* 10742 */     this.cmbMechEra.setEnabled(true);
/* 10743 */     this.cmbProductionEra.setEnabled(true);
/* 10744 */     this.cmbTechBase.setEnabled(true);
/* 10745 */     this.txtProdYear.setEnabled(true);
/* 10746 */     switch (this.CurMech.GetEra()) {
/*       */     case 0: 
/* 10748 */       this.lblEraYears.setText("2443 ~ 2800");
/* 10749 */       break;
/*       */     case 1: 
/* 10751 */       this.lblEraYears.setText("2801 ~ 3050");
/* 10752 */       break;
/*       */     case 2: 
/* 10754 */       this.lblEraYears.setText("3051 ~ 3085");
/* 10755 */       break;
/*       */     case 3: 
/* 10757 */       this.lblEraYears.setText("3086 on");
/* 10758 */       break;
/*       */     case 4: 
/* 10760 */       this.lblEraYears.setText("Any");
/*       */     }
/*       */     
/*       */     
/* 10764 */     this.cmbRulesLevel.setSelectedIndex(this.CurMech.GetRulesLevel());
/* 10765 */     this.cmbMechEra.setSelectedIndex(this.CurMech.GetEra());
/* 10766 */     this.cmbProductionEra.setSelectedIndex(this.CurMech.GetProductionEra());
/*       */     
/*       */ 
/* 10769 */     this.Load = false;
/*       */     
/* 10771 */     if (this.chkYearRestrict.isSelected()) {
/* 10772 */       this.cmbMechEra.setEnabled(false);
/* 10773 */       this.cmbTechBase.setEnabled(false);
/* 10774 */       this.txtProdYear.setEnabled(false);
/*       */     }
/* 10776 */     this.txtMechName.setText(this.CurMech.GetName());
/* 10777 */     this.txtMechModel.setText(this.CurMech.GetModel());
/*       */     
/* 10779 */     if (this.CurMech.IsOmnimech()) {
/* 10780 */       LockGUIForOmni();
/* 10781 */       RefreshOmniVariants();
/* 10782 */       RefreshOmniChoices();
/*       */     }
/*       */     
/* 10785 */     BuildTechBaseSelector();
/* 10786 */     this.cmbTechBase.setSelectedIndex(this.CurMech.GetLoadout().GetTechBase());
/*       */     
/* 10788 */     FixTransferHandlers();
/*       */     
/* 10790 */     ResetTonnageSelector();
/* 10791 */     BuildChassisSelector();
/* 10792 */     BuildEngineSelector();
/* 10793 */     BuildGyroSelector();
/* 10794 */     BuildCockpitSelector();
/* 10795 */     BuildEnhancementSelector();
/* 10796 */     BuildHeatsinkSelector();
/* 10797 */     BuildJumpJetSelector();
/* 10798 */     BuildArmorSelector();
/* 10799 */     this.cmbInternalType.setSelectedItem(BuildLookupName(this.CurMech.GetIntStruc().GetCurrentState()));
/* 10800 */     this.cmbEngineType.setSelectedItem(BuildLookupName(this.CurMech.GetEngine().GetCurrentState()));
/* 10801 */     this.cmbGyroType.setSelectedItem(BuildLookupName(this.CurMech.GetGyro().GetCurrentState()));
/* 10802 */     this.cmbCockpitType.setSelectedItem(this.CurMech.GetCockpit().LookupName());
/* 10803 */     this.cmbPhysEnhance.setSelectedItem(BuildLookupName(this.CurMech.GetPhysEnhance().GetCurrentState()));
/* 10804 */     this.cmbHeatSinkType.setSelectedItem(BuildLookupName(this.CurMech.GetHeatSinks().GetCurrentState()));
/* 10805 */     this.cmbJumpJetType.setSelectedItem(this.CurMech.GetJumpJets().LookupName());
/* 10806 */     this.cmbArmorType.setSelectedItem(BuildLookupName(this.CurMech.GetArmor().GetCurrentState()));
/* 10807 */     SetPatchworkArmor();
/* 10808 */     FixWalkMPSpinner();
/* 10809 */     FixHeatSinkSpinnerModel();
/* 10810 */     FixJJSpinnerModel();
/* 10811 */     RefreshInternalPoints();
/* 10812 */     FixArmorSpinners();
/* 10813 */     this.data.Rebuild(this.CurMech);
/* 10814 */     RefreshEquipment();
/* 10815 */     this.chkCTCASE.setSelected(this.CurMech.HasCTCase());
/* 10816 */     this.chkLTCASE.setSelected(this.CurMech.HasLTCase());
/* 10817 */     this.chkRTCASE.setSelected(this.CurMech.HasRTCase());
/* 10818 */     this.chkUseTC.setSelected(this.CurMech.UsingTC());
/* 10819 */     this.chkClanCASE.setSelected(this.CurMech.GetLoadout().IsUsingClanCASE());
/* 10820 */     this.chkNullSig.setSelected(this.CurMech.HasNullSig());
/* 10821 */     this.chkVoidSig.setSelected(this.CurMech.HasVoidSig());
/* 10822 */     this.chkBSPFD.setSelected(this.CurMech.HasBlueShield());
/* 10823 */     this.chkCLPS.setSelected(this.CurMech.HasChameleon());
/* 10824 */     this.chkEnviroSealing.setSelected(this.CurMech.HasEnviroSealing());
/* 10825 */     this.chkEjectionSeat.setSelected(this.CurMech.HasEjectionSeat());
/* 10826 */     this.chkCommandConsole.setSelected(this.CurMech.HasCommandConsole());
/* 10827 */     this.chkFHES.setSelected(this.CurMech.HasFHES());
/* 10828 */     this.chkTracks.setSelected(this.CurMech.HasTracks());
/* 10829 */     this.chkRAAES.setSelected(this.CurMech.HasRAAES());
/* 10830 */     this.chkLAAES.setSelected(this.CurMech.HasLAAES());
/* 10831 */     this.chkLegAES.setSelected(this.CurMech.HasLegAES());
/* 10832 */     SetLoadoutArrays();
/* 10833 */     RefreshSummary();
/* 10834 */     RefreshInfoPane();
/* 10835 */     SetWeaponChoosers();
/* 10836 */     ResetAmmo();
/*       */     
/*       */ 
/* 10839 */     Media media = new Media();
/* 10840 */     media.blankLogo(this.lblFluffImage);
/* 10841 */     media.setLogo(this.lblFluffImage, media.DetermineMatchingImage(this.CurMech.GetName(), this.CurMech.GetModel(), this.CurMech.GetSSWImage()));
/*       */     
/* 10843 */     this.Overview.SetText(this.CurMech.GetOverview());
/* 10844 */     this.Capabilities.SetText(this.CurMech.GetCapabilities());
/* 10845 */     this.History.SetText(this.CurMech.GetHistory());
/* 10846 */     this.Deployment.SetText(this.CurMech.GetDeployment());
/* 10847 */     this.Variants.SetText(this.CurMech.GetVariants());
/* 10848 */     this.Notables.SetText(this.CurMech.GetNotables());
/* 10849 */     this.Additional.SetText(this.CurMech.GetAdditional());
/* 10850 */     this.txtManufacturer.setText(this.CurMech.GetCompany());
/* 10851 */     this.txtManufacturerLocation.setText(this.CurMech.GetLocation());
/* 10852 */     this.txtEngineManufacturer.setText(this.CurMech.GetEngineManufacturer());
/* 10853 */     this.txtArmorModel.setText(this.CurMech.GetArmorModel());
/* 10854 */     this.txtChassisModel.setText(this.CurMech.GetChassisModel());
/* 10855 */     if (this.CurMech.GetJumpJets().GetNumJJ() > 0) {
/* 10856 */       this.txtJJModel.setEnabled(true);
/*       */     }
/* 10858 */     this.txtSource.setText(this.CurMech.GetSource());
/*       */     
/*       */ 
/* 10861 */     this.txtJJModel.setText(this.CurMech.GetJJModel());
/* 10862 */     this.txtCommSystem.setText(this.CurMech.GetCommSystem());
/* 10863 */     this.txtTNTSystem.setText(this.CurMech.GetTandTSystem());
/*       */     
/*       */ 
/* 10866 */     if (this.CurMech.GetEngine().IsNuclear()) {
/* 10867 */       this.lblSumPAmps.setVisible(false);
/* 10868 */       this.txtSumPAmpsTon.setVisible(false);
/* 10869 */       this.txtSumPAmpsACode.setVisible(false);
/*       */     } else {
/* 10871 */       this.lblSumPAmps.setVisible(true);
/* 10872 */       this.txtSumPAmpsTon.setVisible(true);
/* 10873 */       this.txtSumPAmpsACode.setVisible(true);
/*       */     }
/*       */     
/* 10876 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/*       */   }
/*       */   
/*       */   private void mnuExportClipboardActionPerformed(ActionEvent evt)
/*       */   {
/* 10881 */     String CurLoadout = "";
/* 10882 */     String output = "";
/*       */     
/* 10884 */     if (this.CurMech.IsOmnimech()) {
/* 10885 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/*       */ 
/* 10889 */     SolidifyMech();
/*       */     
/* 10891 */     if (!VerifyMech(evt)) {
/* 10892 */       return;
/*       */     }
/*       */     
/* 10895 */     filehandlers.TXTWriter txtw = new filehandlers.TXTWriter(this.CurMech);
/* 10896 */     output = txtw.GetTextExport();
/* 10897 */     java.awt.datatransfer.StringSelection export = new java.awt.datatransfer.StringSelection(output);
/*       */     
/*       */ 
/* 10900 */     if (this.CurMech.IsOmnimech()) {
/* 10901 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 10902 */       cmbOmniVariantActionPerformed(evt);
/*       */     }
/* 10904 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/* 10905 */     clipboard.setContents(export, this);
/*       */   }
/*       */   
/*       */   private void btnSaveIconActionPerformed(ActionEvent evt) {
/* 10909 */     mnuSaveActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnPrintIconActionPerformed(ActionEvent evt) {
/* 10913 */     mnuPrintCurrentMechActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnNewIconActionPerformed(ActionEvent evt) {
/* 10917 */     mnuNewMechActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnOptionsIconActionPerformed(ActionEvent evt) {
/* 10921 */     mnuOptionsActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnOpenActionPerformed(ActionEvent evt)
/*       */   {
/* 10926 */     mnuOpenActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void mnuViewToolbarActionPerformed(ActionEvent evt) {
/* 10930 */     setViewToolbar(this.mnuViewToolbar.getState());
/*       */   }
/*       */   
/*       */   private void btnExportHTMLIconActionPerformed(ActionEvent evt) {
/* 10934 */     mnuExportHTMLActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnExportTextIconActionPerformed(ActionEvent evt) {
/* 10938 */     mnuExportTXTActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnExportMTFIconActionPerformed(ActionEvent evt) {
/* 10942 */     mnuExportMTFActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnExportClipboardIconActionPerformed(ActionEvent evt) {
/* 10946 */     mnuExportClipboardActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnPostToS7ActionPerformed(ActionEvent evt) {
/* 10950 */     mnuPostS7ActionPerformed(evt);
/*       */   }
/*       */   
/*       */ 
/*       */   private void mnuFileActionPerformed(ActionEvent evt) {}
/*       */   
/*       */   private void mnuPrintBatchActionPerformed(ActionEvent evt)
/*       */   {
/* 10958 */     if (this.BatchWindow == null) this.BatchWindow = new dlgPrintBatchMechs(this, true);
/* 10959 */     this.BatchWindow.setLocationRelativeTo(this);
/* 10960 */     this.BatchWindow.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuPrintPreviewActionPerformed(ActionEvent evt) {
/* 10964 */     ssw.print.Printer printer = new ssw.print.Printer(this);
/* 10965 */     printer.setCharts(Boolean.valueOf(this.Prefs.getBoolean("UseCharts", false)));
/* 10966 */     printer.setCanon(this.Prefs.getBoolean("UseCanonDots", false));
/* 10967 */     printer.AddMech(this.CurMech);
/*       */     
/*       */ 
/* 10970 */     ssw.printpreview.dlgPreview preview = new ssw.printpreview.dlgPreview(this.CurMech.GetFullName(), this, printer, printer.Preview(), 0.0D);
/* 10971 */     preview.setSize(1024, 768);
/* 10972 */     preview.setLocationRelativeTo(null);
/* 10973 */     preview.setVisible(true);
/*       */   }
/*       */   
/*       */   private void btnPrintPreviewActionPerformed(ActionEvent evt) {
/* 10977 */     mnuPrintPreviewActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void formWindowClosing(java.awt.event.WindowEvent evt) {
/* 10981 */     CloseProgram();
/*       */   }
/*       */   
/*       */   private void btnForceListActionPerformed(ActionEvent evt) {
/* 10985 */     this.dForce.setLocationRelativeTo(this);
/* 10986 */     this.dForce.setVisible(true);
/*       */   }
/*       */   
/*       */   private void btnAddToForceListActionPerformed(ActionEvent evt) {
/* 10990 */     this.SetSource = false;
/* 10991 */     SolidifyMech();
/* 10992 */     QuickSave();
/* 10993 */     if (VerifyMech(evt)) {
/* 10994 */       this.dForce.Add(this.CurMech, this.Prefs.get("Currentfile", ""));
/*       */     }
/* 10996 */     this.CurMech.SetCurLoadout((String)this.cmbOmniVariant.getSelectedItem());
/* 10997 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */ 
/*       */   private void mnuCreateTCGMechActionPerformed(ActionEvent evt)
/*       */   {
/* 11003 */     SolidifyMech();
/* 11004 */     dlgCCGMech ccgMech = new dlgCCGMech(this, true, this.CurMech);
/* 11005 */     ccgMech.setLocationRelativeTo(this);
/* 11006 */     ccgMech.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuTextTROActionPerformed(ActionEvent evt) {
/* 11010 */     this.SetSource = false;
/* 11011 */     SolidifyMech();
/* 11012 */     dlgTextExport Text = new dlgTextExport(this, true, this.CurMech);
/* 11013 */     Text.setLocationRelativeTo(this);
/* 11014 */     Text.setVisible(true);
/* 11015 */     this.CurMech.SetCurLoadout((String)this.cmbOmniVariant.getSelectedItem());
/* 11016 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */   private void mnuImportHMPActionPerformed(ActionEvent evt) {
/* 11020 */     if (this.CurMech.HasChanged()) {
/* 11021 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 11023 */       if (choice == 1) { return;
/*       */       }
/*       */     }
/*       */     
/* 11027 */     Mech m = null;
/*       */     
/* 11029 */     File tempFile = new File(this.Prefs.get("LastOpenDirectory", ""));
/* 11030 */     JFileChooser fc = new JFileChooser();
/* 11031 */     fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
/*       */       public boolean accept(File f) {
/* 11033 */         if (f.isDirectory()) {
/* 11034 */           return true;
/*       */         }
/*       */         
/* 11037 */         String extension = Utils.getExtension(f);
/* 11038 */         if (extension != null) {
/* 11039 */           if (extension.equals("hmp")) {
/* 11040 */             return true;
/*       */           }
/* 11042 */           return false;
/*       */         }
/*       */         
/* 11045 */         return false;
/*       */       }
/*       */       
/*       */       public String getDescription()
/*       */       {
/* 11050 */         return "*.hmp";
/*       */       }
/* 11052 */     });
/* 11053 */     fc.setAcceptAllFileFilterUsed(false);
/* 11054 */     fc.setCurrentDirectory(tempFile);
/* 11055 */     int returnVal = fc.showDialog(this, "Import HMP File");
/* 11056 */     if (returnVal != 0) return;
/* 11057 */     File loadmech = fc.getSelectedFile();
/* 11058 */     String filename = "";
/*       */     try {
/* 11060 */       filename = loadmech.getCanonicalPath();
/* 11061 */       this.Prefs.put("LastOpenDirectory", loadmech.getCanonicalPath().replace(loadmech.getName(), ""));
/* 11062 */       this.Prefs.put("LastOpenFile", loadmech.getName());
/*       */     } catch (Exception e) {
/* 11064 */       Media.Messager(this, "There was a problem opening the file:\n" + e.getMessage());
/* 11065 */       return;
/*       */     }
/*       */     
/* 11068 */     String Messages = "";
/*       */     try {
/* 11070 */       ssw.filehandlers.HMPReader HMPr = new ssw.filehandlers.HMPReader();
/* 11071 */       m = HMPr.GetMech(filename, false);
/* 11072 */       Messages = HMPr.GetErrors();
/*       */     }
/*       */     catch (Exception e) {
/* 11075 */       if (e.getMessage() == null) {
/* 11076 */         Media.Messager(this, "An unknown error has occured.  The log file has been updated.");
/* 11077 */         e.printStackTrace();
/*       */       } else {
/* 11079 */         Media.Messager(this, e.getMessage());
/*       */       }
/* 11081 */       return;
/*       */     }
/*       */     
/* 11084 */     if (Messages.length() > 0) {
/* 11085 */       dlgTextExport msgs = new dlgTextExport(this, false, Messages);
/* 11086 */       msgs.setLocationRelativeTo(this);
/* 11087 */       msgs.setVisible(true);
/*       */     }
/*       */     
/* 11090 */     this.CurMech = m;
/* 11091 */     LoadMechIntoGUI();
/* 11092 */     this.CurMech.SetChanged(false);
/*       */   }
/*       */   
/*       */   private void btnChatInfoActionPerformed(ActionEvent evt) {
/* 11096 */     java.awt.datatransfer.StringSelection export = new java.awt.datatransfer.StringSelection(this.CurMech.GetChatInfo());
/* 11097 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/* 11098 */     clipboard.setContents(export, this);
/*       */   }
/*       */   
/*       */   private void mnuUnlockActionPerformed(ActionEvent evt) {
/* 11102 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to unlock the chassis?\nAll omnimech loadouts will be deleted\nand the 'Mech will revert to its base loadout.", "Unlock Chassis?", 0);
/*       */     
/*       */ 
/*       */ 
/* 11106 */     if (choice == 1) {
/* 11107 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11111 */     this.CurMech.UnlockChassis();
/* 11112 */     FixTransferHandlers();
/* 11113 */     SetLoadoutArrays();
/* 11114 */     FixJJSpinnerModel();
/* 11115 */     FixHeatSinkSpinnerModel();
/* 11116 */     LoadMechIntoGUI();
/*       */   }
/*       */   
/*       */   private void mnuBatchHMPActionPerformed(ActionEvent evt) {
/* 11120 */     dlgBatchHMP batch = new dlgBatchHMP(this, true);
/* 11121 */     batch.setLocationRelativeTo(this);
/* 11122 */     batch.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuBFBActionPerformed(ActionEvent evt) {
/* 11126 */     String[] call = { "java", "-Xmx256m", "-jar", "bfb.jar" };
/*       */     try {
/* 11128 */       Runtime.getRuntime().exec(call);
/*       */     } catch (Exception ex) {
/* 11130 */       Media.Messager("Error while trying to open BFB\n" + ex.getMessage());
/* 11131 */       System.out.println(ex.getMessage());
/*       */     }
/*       */   }
/*       */   
/*       */   private void jMenuItem1ActionPerformed(ActionEvent evt) {
/* 11136 */     this.Overview.StartNewDocument();
/* 11137 */     this.Capabilities.StartNewDocument();
/* 11138 */     this.History.StartNewDocument();
/* 11139 */     this.Deployment.StartNewDocument();
/* 11140 */     this.Variants.StartNewDocument();
/* 11141 */     this.Notables.StartNewDocument();
/* 11142 */     this.Additional.StartNewDocument();
/* 11143 */     this.txtManufacturer.setText("");
/* 11144 */     this.txtManufacturerLocation.setText("");
/* 11145 */     this.txtEngineManufacturer.setText("");
/* 11146 */     this.txtArmorModel.setText("");
/* 11147 */     this.txtChassisModel.setText("");
/* 11148 */     this.txtJJModel.setText("");
/* 11149 */     this.txtCommSystem.setText("");
/* 11150 */     this.txtTNTSystem.setText("");
/*       */   }
/*       */   
/*       */   private void chkShowTextNotGraphActionPerformed(ActionEvent evt) {
/* 11154 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkAverageDamageActionPerformed(ActionEvent evt) {
/* 11158 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void btnBracketChartActionPerformed(ActionEvent evt) {
/* 11162 */     dlgBracketCharts charts = new dlgBracketCharts(this, true, this.CurMech);
/* 11163 */     charts.setLocationRelativeTo(this);
/* 11164 */     charts.setVisible(true);
/*       */   }
/*       */   
/*       */   private void chkChartLeftActionPerformed(ActionEvent evt) {
/* 11168 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkChartRightActionPerformed(ActionEvent evt) {
/* 11172 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkChartRearActionPerformed(ActionEvent evt) {
/* 11176 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkChartFrontActionPerformed(ActionEvent evt) {
/* 11180 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */ 
/*       */   private void btnExportMTFActionPerformed(ActionEvent evt)
/*       */   {
/* 11186 */     String dir = this.Prefs.get("MTFExportPath", "none");
/* 11187 */     if (dir.equals("none")) {
/* 11188 */       dir = this.Prefs.get("LastOpenDirectory", "");
/*       */     }
/* 11190 */     File savemech = GetSaveFile("mtf", dir, false, true);
/* 11191 */     if (savemech == null) {
/* 11192 */       return;
/*       */     }
/*       */     
/* 11195 */     String filename = "";
/* 11196 */     IO.MTFWriter mtfw = new IO.MTFWriter(this.CurMech);
/*       */     try {
/* 11198 */       filename = savemech.getCanonicalPath();
/* 11199 */       mtfw.WriteMTF(filename);
/*       */     } catch (IOException e) {
/* 11201 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 11202 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11206 */     Media.Messager(this, "Mech saved successfully to MTF:\n" + filename);
/* 11207 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/*       */   }
/*       */   
/*       */   private void btnExportHTMLActionPerformed(ActionEvent evt)
/*       */   {
/* 11212 */     String CurLoadout = "";
/* 11213 */     if (this.CurMech.IsOmnimech()) {
/* 11214 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/* 11217 */     String dir = this.Prefs.get("HTMLExportPath", "none");
/* 11218 */     if (dir.equals("none")) {
/* 11219 */       dir = this.Prefs.get("LastOpenDirectory", "");
/*       */     }
/* 11221 */     File savemech = GetSaveFile("html", dir, false, false);
/* 11222 */     if (savemech == null) {
/* 11223 */       return;
/*       */     }
/*       */     
/* 11226 */     String filename = "";
/* 11227 */     ssw.filehandlers.HTMLWriter HTMw = new ssw.filehandlers.HTMLWriter(this.CurMech);
/*       */     try {
/* 11229 */       filename = savemech.getCanonicalPath();
/* 11230 */       HTMw.WriteHTML("Data/Templates/Mech_HTML.html", filename);
/*       */     } catch (IOException e) {
/* 11232 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 11233 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11237 */     Media.Messager(this, "Mech saved successfully to HTML:\n" + filename);
/*       */     
/*       */ 
/* 11240 */     if (this.CurMech.IsOmnimech()) {
/* 11241 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 11242 */       cmbOmniVariantActionPerformed(evt);
/*       */     }
/* 11244 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/*       */   }
/*       */   
/*       */   private void btnExportTXTActionPerformed(ActionEvent evt)
/*       */   {
/* 11249 */     String CurLoadout = "";
/* 11250 */     if (this.CurMech.IsOmnimech()) {
/* 11251 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/* 11254 */     String dir = this.Prefs.get("TXTExportPath", "none");
/* 11255 */     if (dir.equals("none")) {
/* 11256 */       dir = this.Prefs.get("LastOpenDirectory", "");
/*       */     }
/* 11258 */     File savemech = GetSaveFile("txt", dir, false, false);
/* 11259 */     if (savemech == null) {
/* 11260 */       return;
/*       */     }
/*       */     
/* 11263 */     String filename = "";
/* 11264 */     filehandlers.TXTWriter txtw = new filehandlers.TXTWriter(this.CurMech);
/*       */     try {
/* 11266 */       filename = savemech.getCanonicalPath();
/* 11267 */       txtw.WriteTXT(filename);
/*       */     } catch (IOException e) {
/* 11269 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 11270 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11274 */     Media.Messager(this, "Mech saved successfully to TXT:\n" + filename);
/*       */     
/*       */ 
/* 11277 */     if (this.CurMech.IsOmnimech()) {
/* 11278 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 11279 */       cmbOmniVariantActionPerformed(evt);
/*       */     }
/* 11281 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/*       */   }
/*       */   
/*       */   private void btnClearImageActionPerformed(ActionEvent evt)
/*       */   {
/* 11286 */     this.lblFluffImage.setIcon(null);
/* 11287 */     this.CurMech.SetSSWImage("");
/*       */   }
/*       */   
/*       */ 
/*       */   private void btnLoadImageActionPerformed(ActionEvent evt)
/*       */   {
/* 11293 */     JFileChooser fc = new JFileChooser();
/*       */     
/*       */ 
/* 11296 */     ImageIcon newFluffImage = (ImageIcon)this.lblFluffImage.getIcon();
/*       */     
/*       */ 
/*       */ 
/* 11300 */     fc.addChoosableFileFilter(new ImageFilter());
/* 11301 */     fc.setAcceptAllFileFilterUsed(false);
/* 11302 */     if (!this.Prefs.get("LastImagePath", "").isEmpty()) {
/* 11303 */       fc.setCurrentDirectory(new File(this.Prefs.get("LastImagePath", "")));
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 11311 */     fc.setAccessory(new ImagePreview(fc));
/*       */     
/*       */ 
/* 11314 */     int returnVal = fc.showDialog(this, "Attach");
/*       */     
/*       */ 
/* 11317 */     if (returnVal == 0) {
/*       */       try {
/* 11319 */         this.Prefs.put("LastImagePath", fc.getSelectedFile().getCanonicalPath().replace(fc.getSelectedFile().getName(), ""));
/* 11320 */         this.Prefs.put("LastImageFile", fc.getSelectedFile().getName());
/*       */         
/* 11322 */         newFluffImage = new ImageIcon(fc.getSelectedFile().getPath());
/*       */         
/* 11324 */         if (newFluffImage == null) { return;
/*       */         }
/* 11326 */         int h = newFluffImage.getIconHeight();
/* 11327 */         int w = newFluffImage.getIconWidth();
/* 11328 */         if ((w > 290) || (h > 350)) {
/* 11329 */           if (w > h)
/*       */           {
/* 11331 */             newFluffImage = new ImageIcon(newFluffImage.getImage().getScaledInstance(290, -1, 1));
/*       */           }
/*       */           else {
/* 11334 */             newFluffImage = new ImageIcon(newFluffImage.getImage().getScaledInstance(-1, 350, 1));
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception e) {}
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 11345 */     this.lblFluffImage.setIcon(newFluffImage);
/* 11346 */     this.CurMech.SetSSWImage(fc.getSelectedFile().getPath());
/*       */   }
/*       */   
/*       */   private void chkLegAESActionPerformed(ActionEvent evt) {
/* 11350 */     if (this.chkLegAES.isSelected() == this.CurMech.HasLegAES()) return;
/*       */     try {
/* 11352 */       if (this.chkLegAES.isSelected()) {
/* 11353 */         this.CurMech.SetLegAES(true, null);
/*       */       } else {
/* 11355 */         this.CurMech.SetLegAES(false, null);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 11359 */       this.chkLegAES.setSelected(this.CurMech.HasLegAES());
/* 11360 */       Media.Messager(this, e.getMessage());
/* 11361 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11365 */     RefreshSummary();
/* 11366 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnSelectiveAllocateActionPerformed(ActionEvent evt) {
/* 11370 */     SelectiveAllocate();
/*       */   }
/*       */   
/*       */   private void btnAutoAllocateActionPerformed(ActionEvent evt) {
/* 11374 */     AutoAllocate();
/*       */   }
/*       */   
/*       */   private void btnClearLoadoutActionPerformed(ActionEvent evt) {
/* 11378 */     this.CurMech.GetLoadout().SafeMassUnallocate();
/*       */     
/*       */ 
/* 11381 */     RefreshSummary();
/* 11382 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnCompactCritsActionPerformed(ActionEvent evt) {
/* 11386 */     this.CurMech.GetLoadout().Compact();
/*       */     
/*       */ 
/* 11389 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnRemoveItemCritsActionPerformed(ActionEvent evt) {
/* 11393 */     int Index = this.lstCritsToPlace.getSelectedIndex();
/* 11394 */     if (Index < 0) {
/* 11395 */       this.btnAutoAllocate.setEnabled(false);
/* 11396 */       this.btnSelectiveAllocate.setEnabled(false);
/* 11397 */       this.btnRemoveItemCrits.setEnabled(false);
/* 11398 */       return;
/*       */     }
/* 11400 */     this.CurItem = this.CurMech.GetLoadout().GetFromQueueByIndex(Index);
/* 11401 */     RemoveItemCritTab();
/*       */   }
/*       */   
/*       */   private void lstCritsToPlaceValueChanged(ListSelectionEvent evt) {
/* 11405 */     int Index = this.lstCritsToPlace.getSelectedIndex();
/* 11406 */     if (Index < 0) {
/* 11407 */       this.btnAutoAllocate.setEnabled(false);
/* 11408 */       this.btnSelectiveAllocate.setEnabled(false);
/* 11409 */       this.btnRemoveItemCrits.setEnabled(false);
/* 11410 */       return;
/*       */     }
/* 11412 */     this.CurItem = this.CurMech.GetLoadout().GetFromQueueByIndex(Index);
/* 11413 */     ShowInfoOn(this.CurItem);
/* 11414 */     if (this.CurItem.Contiguous()) {
/* 11415 */       this.btnAutoAllocate.setEnabled(false);
/* 11416 */       this.btnSelectiveAllocate.setEnabled(false);
/* 11417 */       if (!this.CurItem.CoreComponent()) {
/* 11418 */         this.btnRemoveItemCrits.setEnabled(true);
/*       */       } else {
/* 11420 */         this.btnRemoveItemCrits.setEnabled(false);
/*       */       }
/*       */     } else {
/* 11423 */       this.btnAutoAllocate.setEnabled(true);
/* 11424 */       this.btnSelectiveAllocate.setEnabled(true);
/* 11425 */       this.btnRemoveItemCrits.setEnabled(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private void chkRLCASE2ActionPerformed(ActionEvent evt) {
/* 11430 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRLCASEII())) {
/* 11431 */       this.chkRLCASE2.setSelected(true);
/* 11432 */       return;
/*       */     }
/* 11434 */     if (this.CurMech.GetLoadout().HasRLCASEII() == this.chkRLCASE2.isSelected()) return;
/* 11435 */     if (this.chkRLCASE2.isSelected()) {
/*       */       try {
/* 11437 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 11438 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 11439 */           tech.setLocationRelativeTo(this);
/* 11440 */           tech.setVisible(true);
/* 11441 */           this.CurMech.GetLoadout().SetRLCASEII(true, -1, tech.IsClan());
/* 11442 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 11443 */           this.CurMech.GetLoadout().SetRLCASEII(true, -1, true);
/*       */         } else {
/* 11445 */           this.CurMech.GetLoadout().SetRLCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 11448 */         Media.Messager(this, e.getMessage());
/* 11449 */         this.chkRLCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11453 */         this.CurMech.GetLoadout().SetRLCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 11456 */         System.err.println("Received an error removing RL CASE II:");
/* 11457 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 11460 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLLCASE2ActionPerformed(ActionEvent evt) {
/* 11464 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasLLCASEII())) {
/* 11465 */       this.chkLLCASE2.setSelected(true);
/* 11466 */       return;
/*       */     }
/* 11468 */     if (this.CurMech.GetLoadout().HasLLCASEII() == this.chkLLCASE2.isSelected()) return;
/* 11469 */     if (this.chkLLCASE2.isSelected()) {
/*       */       try {
/* 11471 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 11472 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 11473 */           tech.setLocationRelativeTo(this);
/* 11474 */           tech.setVisible(true);
/* 11475 */           this.CurMech.GetLoadout().SetLLCASEII(true, -1, tech.IsClan());
/* 11476 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 11477 */           this.CurMech.GetLoadout().SetLLCASEII(true, -1, true);
/*       */         } else {
/* 11479 */           this.CurMech.GetLoadout().SetLLCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 11482 */         Media.Messager(this, e.getMessage());
/* 11483 */         this.chkLLCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11487 */         this.CurMech.GetLoadout().SetLLCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 11490 */         System.err.println("Received an error removing LL CASE II:");
/* 11491 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 11494 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRAAESActionPerformed(ActionEvent evt) {
/* 11498 */     if (this.chkRAAES.isSelected() == this.CurMech.HasRAAES()) return;
/*       */     try {
/* 11500 */       if (this.chkRAAES.isSelected()) {
/* 11501 */         this.CurMech.SetRAAES(true, -1);
/*       */       } else {
/* 11503 */         this.CurMech.SetRAAES(false, -1);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 11507 */       this.chkRAAES.setSelected(this.CurMech.HasRAAES());
/* 11508 */       Media.Messager(this, e.getMessage());
/* 11509 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11513 */     RefreshSummary();
/* 11514 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRACASE2ActionPerformed(ActionEvent evt) {
/* 11518 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRACASEII())) {
/* 11519 */       this.chkRACASE2.setSelected(true);
/* 11520 */       return;
/*       */     }
/* 11522 */     if (this.CurMech.GetLoadout().HasRACASEII() == this.chkRACASE2.isSelected()) return;
/* 11523 */     if (this.chkRACASE2.isSelected()) {
/*       */       try {
/* 11525 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 11526 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 11527 */           tech.setLocationRelativeTo(this);
/* 11528 */           tech.setVisible(true);
/* 11529 */           this.CurMech.GetLoadout().SetRACASEII(true, -1, tech.IsClan());
/* 11530 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 11531 */           this.CurMech.GetLoadout().SetRACASEII(true, -1, true);
/*       */         } else {
/* 11533 */           this.CurMech.GetLoadout().SetRACASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 11536 */         Media.Messager(this, e.getMessage());
/* 11537 */         this.chkRACASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11541 */         this.CurMech.GetLoadout().SetRACASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 11544 */         System.err.println("Received an error removing RA CASE II:");
/* 11545 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 11548 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRAHandActionPerformed(ActionEvent evt) {
/* 11552 */     if (this.chkRAHand.isSelected() == this.CurMech.GetActuators().RightHandInstalled()) {
/* 11553 */       return;
/*       */     }
/* 11555 */     if (this.chkRAHand.isSelected())
/*       */     {
/* 11557 */       abPlaceable[] check = this.CurMech.GetLoadout().GetRACrits();
/* 11558 */       for (int i = 0; i < check.length; i++) {
/* 11559 */         if (((check[i] instanceof ifWeapon)) && 
/* 11560 */           (((ifWeapon)check[i]).OmniRestrictActuators()) && (this.CurMech.IsOmnimech())) {
/* 11561 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the hand.");
/* 11562 */           this.chkRAHand.setSelected(false);
/* 11563 */           return;
/*       */         }
/*       */         
/* 11566 */         if (((check[i] instanceof PhysicalWeapon)) && 
/* 11567 */           (((PhysicalWeapon)check[i]).ReplacesHand())) {
/* 11568 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the hand.");
/* 11569 */           this.chkRAHand.setSelected(false);
/* 11570 */           return;
/*       */         }
/*       */       }
/*       */       
/* 11574 */       this.CurMech.GetActuators().AddRightHand();
/*       */     } else {
/* 11576 */       this.CurMech.GetActuators().RemoveRightHand();
/*       */       
/* 11578 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 11579 */       for (int i = 0; i < v.size(); i++) {
/* 11580 */         abPlaceable p = (abPlaceable)v.get(i);
/* 11581 */         if (((p instanceof PhysicalWeapon)) && 
/* 11582 */           (((PhysicalWeapon)p).RequiresHand()) && 
/* 11583 */           (this.CurMech.GetLoadout().Find(p) == 5)) {
/* 11584 */           this.CurMech.GetLoadout().UnallocateAll(p, false);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 11590 */     CheckActuators();
/* 11591 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRALowerArmActionPerformed(ActionEvent evt) {
/* 11595 */     if (this.chkRALowerArm.isSelected() == this.CurMech.GetActuators().RightLowerInstalled()) {
/* 11596 */       return;
/*       */     }
/* 11598 */     if (this.chkRALowerArm.isSelected())
/*       */     {
/* 11600 */       abPlaceable[] check = this.CurMech.GetLoadout().GetRACrits();
/* 11601 */       for (int i = 0; i < check.length; i++) {
/* 11602 */         if (((check[i] instanceof ifWeapon)) && 
/* 11603 */           (((ifWeapon)check[i]).OmniRestrictActuators()) && (this.CurMech.IsOmnimech())) {
/* 11604 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the lower arm.");
/* 11605 */           this.chkRALowerArm.setSelected(false);
/* 11606 */           return;
/*       */         }
/*       */         
/* 11609 */         if (((check[i] instanceof PhysicalWeapon)) && 
/* 11610 */           (((PhysicalWeapon)check[i]).ReplacesLowerArm())) {
/* 11611 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the lower arm.");
/* 11612 */           this.chkRALowerArm.setSelected(false);
/* 11613 */           return;
/*       */         }
/*       */       }
/*       */       
/* 11617 */       this.CurMech.GetActuators().AddRightLowerArm();
/*       */     } else {
/* 11619 */       this.CurMech.GetActuators().RemoveRightLowerArm();
/*       */       
/* 11621 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 11622 */       for (int i = 0; i < v.size(); i++) {
/* 11623 */         abPlaceable p = (abPlaceable)v.get(i);
/* 11624 */         if (((p instanceof PhysicalWeapon)) && 
/* 11625 */           (((PhysicalWeapon)p).RequiresLowerArm()) && 
/* 11626 */           (this.CurMech.GetLoadout().Find(p) == 5)) {
/* 11627 */           this.CurMech.GetLoadout().UnallocateAll(p, false);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 11633 */     CheckActuators();
/* 11634 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLAAESActionPerformed(ActionEvent evt) {
/* 11638 */     if (this.chkLAAES.isSelected() == this.CurMech.HasLAAES()) return;
/*       */     try {
/* 11640 */       if (this.chkLAAES.isSelected()) {
/* 11641 */         this.CurMech.SetLAAES(true, -1);
/*       */       } else {
/* 11643 */         this.CurMech.SetLAAES(false, -1);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 11647 */       this.chkLAAES.setSelected(this.CurMech.HasLAAES());
/* 11648 */       Media.Messager(this, e.getMessage());
/* 11649 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11653 */     RefreshSummary();
/* 11654 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLACASE2ActionPerformed(ActionEvent evt) {
/* 11658 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasLACASEII())) {
/* 11659 */       this.chkLACASE2.setSelected(true);
/* 11660 */       return;
/*       */     }
/* 11662 */     if (this.CurMech.GetLoadout().HasLACASEII() == this.chkLACASE2.isSelected()) return;
/* 11663 */     if (this.chkLACASE2.isSelected()) {
/*       */       try {
/* 11665 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 11666 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 11667 */           tech.setLocationRelativeTo(this);
/* 11668 */           tech.setVisible(true);
/* 11669 */           this.CurMech.GetLoadout().SetLACASEII(true, -1, tech.IsClan());
/* 11670 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 11671 */           this.CurMech.GetLoadout().SetLACASEII(true, -1, true);
/*       */         } else {
/* 11673 */           this.CurMech.GetLoadout().SetLACASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 11676 */         Media.Messager(this, e.getMessage());
/* 11677 */         this.chkLACASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11681 */         this.CurMech.GetLoadout().SetLACASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 11684 */         System.err.println("Received an error removing LA CASE II:");
/* 11685 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 11688 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLAHandActionPerformed(ActionEvent evt) {
/* 11692 */     if (this.chkLAHand.isSelected() == this.CurMech.GetActuators().LeftHandInstalled()) {
/* 11693 */       return;
/*       */     }
/* 11695 */     if (this.chkLAHand.isSelected())
/*       */     {
/* 11697 */       abPlaceable[] check = this.CurMech.GetLoadout().GetLACrits();
/* 11698 */       for (int i = 0; i < check.length; i++) {
/* 11699 */         if (((check[i] instanceof ifWeapon)) && 
/* 11700 */           (((ifWeapon)check[i]).OmniRestrictActuators()) && (this.CurMech.IsOmnimech())) {
/* 11701 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the hand.");
/* 11702 */           this.chkLAHand.setSelected(false);
/* 11703 */           return;
/*       */         }
/*       */         
/* 11706 */         if (((check[i] instanceof PhysicalWeapon)) && 
/* 11707 */           (((PhysicalWeapon)check[i]).ReplacesHand())) {
/* 11708 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the hand.");
/* 11709 */           this.chkLAHand.setSelected(false);
/* 11710 */           return;
/*       */         }
/*       */       }
/*       */       
/* 11714 */       this.CurMech.GetActuators().AddLeftHand();
/*       */     } else {
/* 11716 */       this.CurMech.GetActuators().RemoveLeftHand();
/*       */       
/* 11718 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 11719 */       for (int i = 0; i < v.size(); i++) {
/* 11720 */         abPlaceable p = (abPlaceable)v.get(i);
/* 11721 */         if (((p instanceof PhysicalWeapon)) && 
/* 11722 */           (((PhysicalWeapon)p).RequiresHand()) && 
/* 11723 */           (this.CurMech.GetLoadout().Find(p) == 4)) {
/* 11724 */           this.CurMech.GetLoadout().UnallocateAll(p, false);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 11730 */     CheckActuators();
/* 11731 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLALowerArmActionPerformed(ActionEvent evt) {
/* 11735 */     if (this.chkLALowerArm.isSelected() == this.CurMech.GetActuators().LeftLowerInstalled()) {
/* 11736 */       return;
/*       */     }
/* 11738 */     if (this.chkLALowerArm.isSelected())
/*       */     {
/* 11740 */       abPlaceable[] check = this.CurMech.GetLoadout().GetLACrits();
/* 11741 */       for (int i = 0; i < check.length; i++) {
/* 11742 */         if (((check[i] instanceof ifWeapon)) && 
/* 11743 */           (((ifWeapon)check[i]).OmniRestrictActuators()) && (this.CurMech.IsOmnimech())) {
/* 11744 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the lower arm.");
/* 11745 */           this.chkLALowerArm.setSelected(false);
/* 11746 */           return;
/*       */         }
/*       */         
/* 11749 */         if (((check[i] instanceof PhysicalWeapon)) && 
/* 11750 */           (((PhysicalWeapon)check[i]).ReplacesLowerArm())) {
/* 11751 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the lower arm.");
/* 11752 */           this.chkLALowerArm.setSelected(false);
/* 11753 */           return;
/*       */         }
/*       */       }
/*       */       
/* 11757 */       this.CurMech.GetActuators().AddLeftLowerArm();
/*       */     } else {
/* 11759 */       this.CurMech.GetActuators().RemoveLeftLowerArm();
/*       */       
/* 11761 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 11762 */       for (int i = 0; i < v.size(); i++) {
/* 11763 */         abPlaceable p = (abPlaceable)v.get(i);
/* 11764 */         if (((p instanceof PhysicalWeapon)) && 
/* 11765 */           (((PhysicalWeapon)p).RequiresLowerArm()) && 
/* 11766 */           (this.CurMech.GetLoadout().Find(p) == 4)) {
/* 11767 */           this.CurMech.GetLoadout().UnallocateAll(p, false);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 11773 */     CheckActuators();
/* 11774 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRTTurretActionPerformed(ActionEvent evt) {
/* 11778 */     if ((this.CurMech.IsOmnimech()) && 
/* 11779 */       (this.CurMech.GetBaseLoadout().GetRTTurret() == this.CurMech.GetLoadout().GetRTTurret())) {
/* 11780 */       this.chkRTTurret.setSelected(true);
/* 11781 */       return;
/*       */     }
/*       */     
/* 11784 */     if (this.CurMech.GetLoadout().HasRTTurret() == this.chkRTTurret.isSelected()) return;
/* 11785 */     if (this.chkRTTurret.isSelected()) {
/*       */       try {
/* 11787 */         this.CurMech.GetLoadout().SetRTTurret(true, -1);
/*       */       } catch (Exception e) {
/* 11789 */         Media.Messager(this, e.getMessage());
/* 11790 */         this.chkRTTurret.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11794 */         this.CurMech.GetLoadout().SetRTTurret(false, -1);
/*       */       } catch (Exception e) {
/* 11796 */         Media.Messager("Fatal error attempting to remove turret.\nGetting a new 'Mech, sorry...");
/*       */       }
/*       */     }
/* 11799 */     CheckEquipment();
/* 11800 */     RefreshEquipment();
/* 11801 */     RefreshSummary();
/* 11802 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRTCASE2ActionPerformed(ActionEvent evt) {
/* 11806 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRTCASEII())) {
/* 11807 */       this.chkRTCASE2.setSelected(true);
/* 11808 */       return;
/*       */     }
/* 11810 */     if (this.CurMech.GetLoadout().HasRTCASEII() == this.chkRTCASE2.isSelected()) return;
/* 11811 */     if (this.chkRTCASE2.isSelected()) {
/*       */       try {
/* 11813 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 11814 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 11815 */           tech.setLocationRelativeTo(this);
/* 11816 */           tech.setVisible(true);
/* 11817 */           this.CurMech.GetLoadout().SetRTCASEII(true, -1, tech.IsClan());
/* 11818 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 11819 */           this.CurMech.GetLoadout().SetRTCASEII(true, -1, true);
/*       */         } else {
/* 11821 */           this.CurMech.GetLoadout().SetRTCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 11824 */         Media.Messager(this, e.getMessage());
/* 11825 */         this.chkRTCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11829 */         this.CurMech.GetLoadout().SetRTCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 11832 */         System.err.println("Received an error removing RT CASE II:");
/* 11833 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 11836 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRTCASEActionPerformed(ActionEvent evt) {
/* 11840 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRTCASE())) {
/* 11841 */       this.chkRTCASE.setSelected(true);
/* 11842 */       return;
/*       */     }
/* 11844 */     if (this.CurMech.HasRTCase() == this.chkRTCASE.isSelected()) return;
/* 11845 */     if (this.chkRTCASE.isSelected()) {
/*       */       try {
/* 11847 */         this.CurMech.AddRTCase();
/*       */       } catch (Exception e) {
/* 11849 */         Media.Messager(this, e.getMessage());
/* 11850 */         this.chkRTCASE.setSelected(false);
/*       */       }
/*       */     } else {
/* 11853 */       this.CurMech.RemoveRTCase();
/*       */     }
/* 11855 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLTTurretActionPerformed(ActionEvent evt) {
/* 11859 */     if ((this.CurMech.IsOmnimech()) && 
/* 11860 */       (this.CurMech.GetBaseLoadout().GetLTTurret() == this.CurMech.GetLoadout().GetLTTurret())) {
/* 11861 */       this.chkLTTurret.setSelected(true);
/* 11862 */       return;
/*       */     }
/*       */     
/* 11865 */     if (this.CurMech.GetLoadout().HasLTTurret() == this.chkLTTurret.isSelected()) return;
/* 11866 */     if (this.chkLTTurret.isSelected()) {
/*       */       try {
/* 11868 */         this.CurMech.GetLoadout().SetLTTurret(true, -1);
/*       */       } catch (Exception e) {
/* 11870 */         Media.Messager(this, e.getMessage());
/* 11871 */         this.chkLTTurret.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11875 */         this.CurMech.GetLoadout().SetLTTurret(false, -1);
/*       */       } catch (Exception e) {
/* 11877 */         Media.Messager("Fatal error attempting to remove turret.\nGetting a new 'Mech, sorry...");
/*       */       }
/*       */     }
/* 11880 */     CheckEquipment();
/* 11881 */     RefreshEquipment();
/* 11882 */     RefreshSummary();
/* 11883 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLTCASE2ActionPerformed(ActionEvent evt) {
/* 11887 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasLTCASEII())) {
/* 11888 */       this.chkLTCASE2.setSelected(true);
/* 11889 */       return;
/*       */     }
/* 11891 */     if (this.CurMech.GetLoadout().HasLTCASEII() == this.chkLTCASE2.isSelected()) return;
/* 11892 */     if (this.chkLTCASE2.isSelected()) {
/*       */       try {
/* 11894 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 11895 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 11896 */           tech.setLocationRelativeTo(this);
/* 11897 */           tech.setVisible(true);
/* 11898 */           this.CurMech.GetLoadout().SetLTCASEII(true, -1, tech.IsClan());
/* 11899 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 11900 */           this.CurMech.GetLoadout().SetLTCASEII(true, -1, true);
/*       */         } else {
/* 11902 */           this.CurMech.GetLoadout().SetLTCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 11905 */         Media.Messager(this, e.getMessage());
/* 11906 */         this.chkLTCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11910 */         this.CurMech.GetLoadout().SetLTCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 11913 */         System.err.println("Received an error removing LT CASE II:");
/* 11914 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 11917 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLTCASEActionPerformed(ActionEvent evt) {
/* 11921 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasLTCASE())) {
/* 11922 */       this.chkLTCASE.setSelected(true);
/* 11923 */       return;
/*       */     }
/* 11925 */     if (this.CurMech.HasLTCase() == this.chkLTCASE.isSelected()) return;
/* 11926 */     if (this.chkLTCASE.isSelected()) {
/*       */       try {
/* 11928 */         this.CurMech.AddLTCase();
/*       */       } catch (Exception e) {
/* 11930 */         Media.Messager(this, e.getMessage());
/* 11931 */         this.chkLTCASE.setSelected(false);
/*       */       }
/*       */     } else {
/* 11934 */       this.CurMech.RemoveLTCase();
/*       */     }
/* 11936 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkCTCASE2ActionPerformed(ActionEvent evt) {
/* 11940 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasCTCASEII())) {
/* 11941 */       this.chkCTCASE2.setSelected(true);
/* 11942 */       return;
/*       */     }
/* 11944 */     if (this.CurMech.GetLoadout().HasCTCASEII() == this.chkCTCASE2.isSelected()) return;
/* 11945 */     if (this.chkCTCASE2.isSelected()) {
/*       */       try {
/* 11947 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 11948 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 11949 */           tech.setLocationRelativeTo(this);
/* 11950 */           tech.setVisible(true);
/* 11951 */           this.CurMech.GetLoadout().SetCTCASEII(true, -1, tech.IsClan());
/* 11952 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 11953 */           this.CurMech.GetLoadout().SetCTCASEII(true, -1, true);
/*       */         } else {
/* 11955 */           this.CurMech.GetLoadout().SetCTCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 11958 */         Media.Messager(this, e.getMessage());
/* 11959 */         this.chkCTCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 11963 */         this.CurMech.GetLoadout().SetCTCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 11966 */         System.err.println("Received an error removing CT CASE II:");
/* 11967 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 11970 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkCTCASEActionPerformed(ActionEvent evt) {
/* 11974 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasCTCASE())) {
/* 11975 */       this.chkCTCASE.setSelected(true);
/* 11976 */       return;
/*       */     }
/* 11978 */     if (this.CurMech.HasCTCase() == this.chkCTCASE.isSelected()) return;
/* 11979 */     if (this.chkCTCASE.isSelected()) {
/*       */       try {
/* 11981 */         this.CurMech.AddCTCase();
/*       */       } catch (Exception e) {
/* 11983 */         Media.Messager(this, e.getMessage());
/* 11984 */         this.chkCTCASE.setSelected(false);
/*       */       }
/*       */     } else {
/* 11987 */       this.CurMech.RemoveCTCase();
/*       */     }
/* 11989 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkHDCASE2ActionPerformed(ActionEvent evt) {
/* 11993 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasHDCASEII())) {
/* 11994 */       this.chkHDCASE2.setSelected(true);
/* 11995 */       return;
/*       */     }
/* 11997 */     if (this.CurMech.GetLoadout().HasHDCASEII() == this.chkHDCASE2.isSelected()) return;
/* 11998 */     if (this.chkHDCASE2.isSelected()) {
/*       */       try {
/* 12000 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 12001 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 12002 */           tech.setLocationRelativeTo(this);
/* 12003 */           tech.setVisible(true);
/* 12004 */           this.CurMech.GetLoadout().SetHDCASEII(true, -1, tech.IsClan());
/* 12005 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 12006 */           this.CurMech.GetLoadout().SetHDCASEII(true, -1, true);
/*       */         } else {
/* 12008 */           this.CurMech.GetLoadout().SetHDCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 12011 */         Media.Messager(this, e.getMessage());
/* 12012 */         this.chkHDCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 12016 */         this.CurMech.GetLoadout().SetHDCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 12019 */         System.err.println("Received an error removing HD CASE II:");
/* 12020 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 12023 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkHDTurretActionPerformed(ActionEvent evt) {
/* 12027 */     if ((this.CurMech.IsOmnimech()) && 
/* 12028 */       (this.CurMech.GetBaseLoadout().GetHDTurret() == this.CurMech.GetLoadout().GetHDTurret())) {
/* 12029 */       this.chkHDTurret.setSelected(true);
/* 12030 */       return;
/*       */     }
/*       */     
/* 12033 */     if (this.CurMech.GetLoadout().HasHDTurret() == this.chkHDTurret.isSelected()) return;
/* 12034 */     if (this.chkHDTurret.isSelected()) {
/*       */       try {
/* 12036 */         this.CurMech.GetLoadout().SetHDTurret(true, -1);
/*       */       } catch (Exception e) {
/* 12038 */         Media.Messager(this, e.getMessage());
/* 12039 */         this.chkHDTurret.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 12043 */         this.CurMech.GetLoadout().SetHDTurret(false, -1);
/*       */       } catch (Exception e) {
/* 12045 */         Media.Messager("Fatal error attempting to remove turret.\nGetting a new 'Mech, sorry...");
/*       */       }
/*       */     }
/* 12048 */     CheckEquipment();
/* 12049 */     RefreshEquipment();
/* 12050 */     RefreshSummary();
/* 12051 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnAddEquipActionPerformed(ActionEvent evt) {
/* 12055 */     abPlaceable a = null;
/* 12056 */     int Index = 0;
/*       */     
/*       */ 
/*       */ 
/* 12060 */     switch (this.tbpWeaponChooser.getSelectedIndex()) {
/*       */     case 0: 
/* 12062 */       if (this.lstChooseBallistic.getSelectedIndex() >= 0) {
/* 12063 */         a = (abPlaceable)this.Equipment[0][this.lstChooseBallistic.getSelectedIndex()];
/* 12064 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12065 */       break;
/*       */     case 1: 
/* 12067 */       if (this.lstChooseEnergy.getSelectedIndex() >= 0) {
/* 12068 */         a = (abPlaceable)this.Equipment[1][this.lstChooseEnergy.getSelectedIndex()];
/* 12069 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12070 */       break;
/*       */     case 2: 
/* 12072 */       if (this.lstChooseMissile.getSelectedIndex() >= 0) {
/* 12073 */         a = (abPlaceable)this.Equipment[2][this.lstChooseMissile.getSelectedIndex()];
/* 12074 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech);
/* 12075 */         if (((RangedWeapon)a).IsFCSCapable()) {
/* 12076 */           if ((this.CurMech.UsingArtemisIV()) && (
/* 12077 */             (((RangedWeapon)a).GetFCSType() == 1) || (((RangedWeapon)a).GetFCSType() == 2))) {
/* 12078 */             ((RangedWeapon)a).UseFCS(true, 1);
/*       */           }
/*       */           
/* 12081 */           if ((this.CurMech.UsingArtemisV()) && 
/* 12082 */             (((RangedWeapon)a).GetFCSType() == 2)) {
/* 12083 */             ((RangedWeapon)a).UseFCS(true, 2);
/*       */           }
/*       */           
/* 12086 */           if ((this.CurMech.UsingApollo()) && 
/* 12087 */             (((RangedWeapon)a).GetFCSType() == 3)) {
/* 12088 */             ((RangedWeapon)a).UseFCS(true, 3);
/*       */           }
/*       */         }
/*       */       }
/*       */       break;
/*       */     case 3: 
/* 12094 */       if ((this.lstChoosePhysical.getSelectedIndex() >= 0) && 
/* 12095 */         ((this.Equipment[3][this.lstChoosePhysical.getSelectedIndex()] instanceof abPlaceable)))
/*       */       {
/*       */ 
/* 12098 */         a = (abPlaceable)this.Equipment[3][this.lstChoosePhysical.getSelectedIndex()];
/* 12099 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12100 */       break;
/*       */     case 5: 
/* 12102 */       if ((this.lstChooseArtillery.getSelectedIndex() >= 0) && 
/* 12103 */         ((this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()] instanceof abPlaceable)))
/*       */       {
/*       */ 
/* 12106 */         a = (abPlaceable)this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()];
/* 12107 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12108 */       break;
/*       */     case 4: 
/* 12110 */       if ((this.lstChooseEquipment.getSelectedIndex() >= 0) && 
/* 12111 */         ((this.Equipment[4][this.lstChooseEquipment.getSelectedIndex()] instanceof abPlaceable)))
/*       */       {
/*       */ 
/* 12114 */         a = (abPlaceable)this.Equipment[4][this.lstChooseEquipment.getSelectedIndex()];
/* 12115 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12116 */       break;
/*       */     case 6: 
/* 12118 */       if (this.lstChooseAmmunition.getSelectedIndex() >= 0) {
/* 12119 */         Index = this.lstChooseAmmunition.getSelectedIndex();
/* 12120 */         if ((this.Equipment[6][Index] instanceof abPlaceable))
/*       */         {
/*       */ 
/* 12123 */           a = (abPlaceable)this.Equipment[6][Index];
/* 12124 */           a = this.data.GetEquipment().GetCopy(a, this.CurMech);
/*       */         }
/*       */       }
/*       */       break;
/*       */     }
/* 12129 */     if (a != null) {
/*       */       try {
/* 12131 */         this.CurMech.GetLoadout().CheckExclusions(a);
/* 12132 */         if (((a instanceof Equipment)) && 
/* 12133 */           (!((Equipment)a).Validate(this.CurMech))) {
/* 12134 */           if (((Equipment)a).RequiresQuad())
/* 12135 */             throw new Exception(a.CritName() + " may only be mounted on a quad 'Mech.");
/* 12136 */           if (((Equipment)a).MaxAllowed() > 0) {
/* 12137 */             throw new Exception("Only " + ((Equipment)a).MaxAllowed() + " " + a.CritName() + "(s) may be mounted on one 'Mech.");
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception e) {
/* 12142 */         Media.Messager(e.getMessage());
/* 12143 */         a = null;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 12148 */     if (a != null) {
/* 12149 */       boolean result = true;
/* 12150 */       if (((a instanceof Equipment)) && 
/* 12151 */         (((Equipment)a).IsVariableSize())) {
/* 12152 */         dlgVariableSize SetTons = new dlgVariableSize(this, true, (Equipment)a);
/* 12153 */         SetTons.setLocationRelativeTo(this);
/* 12154 */         SetTons.setVisible(true);
/* 12155 */         result = SetTons.GetResult();
/*       */       }
/*       */       
/* 12158 */       if (result) {
/* 12159 */         if ((a instanceof components.Talons)) {
/* 12160 */           if (!a.Place(this.CurMech.GetLoadout())) {
/* 12161 */             Media.Messager("Talons cannot be added because there is not enough space.");
/*       */           }
/*       */         }
/*       */         else {
/* 12165 */           this.CurMech.GetLoadout().AddToQueue(a);
/* 12166 */           for (int i = 0; i < this.cmbNumEquips.getSelectedIndex(); i++) {
/* 12167 */             a = this.data.GetEquipment().GetCopy(a, this.CurMech);
/* 12168 */             this.CurMech.GetLoadout().AddToQueue(a);
/*       */           }
/*       */         }
/*       */         
/*       */ 
/* 12173 */         if (((a instanceof ifWeapon)) && 
/* 12174 */           (((ifWeapon)a).IsTCCapable()) && (this.CurMech.UsingTC())) {
/* 12175 */           this.CurMech.UnallocateTC();
/*       */         }
/*       */         
/*       */ 
/*       */ 
/* 12180 */         ResetAmmo();
/*       */         
/* 12182 */         if ((a instanceof Ammunition))
/*       */         {
/*       */ 
/* 12185 */           this.lstChooseAmmunition.setSelectedIndex(Index);
/*       */         }
/*       */         
/*       */ 
/* 12189 */         if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/* 12190 */           this.Equipment[7] = { " " };
/*       */         } else {
/* 12192 */           this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/* 12198 */       RefreshSummary();
/* 12199 */       RefreshInfoPane();
/* 12200 */       this.cmbNumEquips.setSelectedIndex(0);
/*       */     }
/*       */   }
/*       */   
/*       */   private void btnClearEquipActionPerformed(ActionEvent evt) {
/* 12205 */     this.CurMech.GetLoadout().SafeClearLoadout();
/*       */     
/*       */ 
/* 12208 */     if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/* 12209 */       this.Equipment[7] = { " " };
/*       */     } else {
/* 12211 */       this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 12216 */     if (this.CurMech.UsingTC()) {
/* 12217 */       this.CurMech.CheckTC();
/*       */     }
/*       */     
/*       */ 
/* 12221 */     ResetAmmo();
/*       */     
/*       */ 
/* 12224 */     RefreshSummary();
/* 12225 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnRemoveEquipActionPerformed(ActionEvent evt) {
/* 12229 */     int selected = this.lstCritsToPlace.getSelectedIndex();
/* 12230 */     if (selected < 0) return;
/* 12231 */     abPlaceable p = null;
/* 12232 */     if ((this.CurMech.GetLoadout().GetQueue().get(selected) instanceof components.EquipmentCollection)) {
/* 12233 */       p = ((components.EquipmentCollection)this.CurMech.GetLoadout().GetQueue().get(selected)).GetType();
/* 12234 */       if (!this.CurMech.GetLoadout().GetNonCore().contains(p)) {
/* 12235 */         Media.Messager(this, "You may not remove this item from the loadout.");
/*       */       }
/*       */     }
/*       */     else {
/* 12239 */       p = (abPlaceable)this.CurMech.GetLoadout().GetQueue().get(selected);
/* 12240 */       if (!this.CurMech.GetLoadout().GetNonCore().contains(p)) {
/* 12241 */         Media.Messager(this, "You may not remove this item from the loadout.");
/* 12242 */         return;
/*       */       }
/*       */     }
/* 12245 */     if (p == null) return;
/* 12246 */     if ((p.LocationLocked() & !(p instanceof components.Talons))) {
/* 12247 */       Media.Messager(this, "You may not remove a locked item from the loadout.");
/* 12248 */       return;
/*       */     }
/* 12250 */     this.CurMech.GetLoadout().Remove(p);
/*       */     
/*       */ 
/*       */ 
/* 12254 */     if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/* 12255 */       this.Equipment[7] = { " " };
/*       */     } else {
/* 12257 */       this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 12262 */     if (this.CurMech.UsingTC()) {
/* 12263 */       this.CurMech.UnallocateTC();
/*       */     }
/*       */     
/*       */ 
/* 12267 */     ResetAmmo();
/*       */     
/*       */ 
/* 12270 */     RefreshSummary();
/* 12271 */     RefreshInfoPane();
/*       */     try
/*       */     {
/* 12274 */       this.lstCritsToPlace.setSelectedIndex(selected);
/*       */     }
/*       */     catch (Exception e) {}
/*       */   }
/*       */   
/*       */   private void chkClanCASEActionPerformed(ActionEvent evt)
/*       */   {
/* 12281 */     this.CurMech.GetLoadout().SetClanCASE(this.chkClanCASE.isSelected());
/* 12282 */     RefreshSummary();
/* 12283 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkFCSApolloActionPerformed(ActionEvent evt) {
/* 12287 */     if (this.CurMech.UsingApollo() == this.chkFCSApollo.isSelected()) return;
/* 12288 */     if (this.chkFCSApollo.isSelected()) {
/*       */       try {
/* 12290 */         this.CurMech.SetFCSApollo(true);
/*       */       } catch (Exception e) {
/* 12292 */         Media.Messager(this, e.getMessage());
/* 12293 */         this.chkFCSApollo.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 12297 */         this.CurMech.SetFCSApollo(false);
/*       */       } catch (Exception e) {
/* 12299 */         Media.Messager(this, e.getMessage());
/* 12300 */         this.chkFCSApollo.setSelected(true);
/*       */       }
/*       */     }
/*       */     
/* 12304 */     RefreshSummary();
/* 12305 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkFCSAVActionPerformed(ActionEvent evt) {
/* 12309 */     if (this.CurMech.UsingArtemisV() == this.chkFCSAV.isSelected()) return;
/* 12310 */     if (this.chkFCSAV.isSelected()) {
/*       */       try {
/* 12312 */         this.CurMech.SetFCSArtemisV(true);
/*       */       } catch (Exception e) {
/* 12314 */         Media.Messager(this, e.getMessage());
/* 12315 */         this.chkFCSAV.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 12319 */         this.CurMech.SetFCSArtemisV(false);
/*       */       } catch (Exception e) {
/* 12321 */         Media.Messager(this, e.getMessage());
/* 12322 */         this.chkFCSAV.setSelected(true);
/*       */       }
/*       */     }
/*       */     
/* 12326 */     RefreshSummary();
/* 12327 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkFCSAIVActionPerformed(ActionEvent evt) {
/* 12331 */     if (this.CurMech.UsingArtemisIV() == this.chkFCSAIV.isSelected()) return;
/* 12332 */     if (this.chkFCSAIV.isSelected()) {
/*       */       try {
/* 12334 */         this.CurMech.SetFCSArtemisIV(true);
/*       */       } catch (Exception e) {
/* 12336 */         Media.Messager(this, e.getMessage());
/* 12337 */         this.chkFCSAIV.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 12341 */         this.CurMech.SetFCSArtemisIV(false);
/*       */       } catch (Exception e) {
/* 12343 */         Media.Messager(this, e.getMessage());
/* 12344 */         this.chkFCSAIV.setSelected(true);
/*       */       }
/*       */     }
/*       */     
/* 12348 */     RefreshSummary();
/* 12349 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkUseTCActionPerformed(ActionEvent evt) {
/* 12353 */     if (this.CurMech.UsingTC() == this.chkUseTC.isSelected()) return;
/* 12354 */     if (this.chkUseTC.isSelected()) {
/*       */       try {
/* 12356 */         this.CurMech.GetLoadout().CheckExclusions(this.CurMech.GetTC());
/* 12357 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 12358 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 12359 */           tech.setLocationRelativeTo(this);
/* 12360 */           tech.setVisible(true);
/* 12361 */           this.CurMech.UseTC(true, tech.IsClan());
/* 12362 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 12363 */           this.CurMech.UseTC(true, true);
/*       */         } else {
/* 12365 */           this.CurMech.UseTC(true, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 12368 */         Media.Messager(this, e.getMessage());
/* 12369 */         this.CurMech.UseTC(false, false);
/*       */       }
/*       */     } else {
/* 12372 */       this.CurMech.UseTC(false, false);
/*       */     }
/*       */     
/*       */ 
/* 12376 */     RefreshSummary();
/* 12377 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void lstChooseAmmunitionValueChanged(ListSelectionEvent evt) {
/* 12381 */     if (this.lstChooseAmmunition.getSelectedIndex() < 0) return;
/* 12382 */     if (!(this.Equipment[6][this.lstChooseAmmunition.getSelectedIndex()] instanceof Ammunition)) return;
/* 12383 */     abPlaceable p = (abPlaceable)this.Equipment[6][this.lstChooseAmmunition.getSelectedIndex()];
/* 12384 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void lstChooseArtilleryValueChanged(ListSelectionEvent evt) {
/* 12388 */     if (this.lstChooseArtillery.getSelectedIndex() < 0) return;
/* 12389 */     if ((!(this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()] instanceof RangedWeapon)) && (!(this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()] instanceof VehicularGrenadeLauncher))) return;
/* 12390 */     abPlaceable p = (abPlaceable)this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()];
/* 12391 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void lstChooseEquipmentValueChanged(ListSelectionEvent evt) {
/* 12395 */     if (this.lstChooseEquipment.getSelectedIndex() < 0) { return;
/*       */     }
/* 12397 */     abPlaceable p = (abPlaceable)this.Equipment[4][this.lstChooseEquipment.getSelectedIndex()];
/* 12398 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void lstChoosePhysicalValueChanged(ListSelectionEvent evt) {
/* 12402 */     if (this.lstChoosePhysical.getSelectedIndex() < 0) return;
/* 12403 */     if (!(this.Equipment[3][this.lstChoosePhysical.getSelectedIndex()] instanceof PhysicalWeapon)) return;
/* 12404 */     abPlaceable p = (abPlaceable)this.Equipment[3][this.lstChoosePhysical.getSelectedIndex()];
/* 12405 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void lstChooseMissileValueChanged(ListSelectionEvent evt) {
/* 12409 */     if (this.lstChooseMissile.getSelectedIndex() < 0) return;
/* 12410 */     abPlaceable p = (abPlaceable)this.Equipment[2][this.lstChooseMissile.getSelectedIndex()];
/* 12411 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void lstChooseEnergyValueChanged(ListSelectionEvent evt) {
/* 12415 */     if (this.lstChooseEnergy.getSelectedIndex() < 0) return;
/* 12416 */     abPlaceable p = (abPlaceable)this.Equipment[1][this.lstChooseEnergy.getSelectedIndex()];
/* 12417 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void lstChooseBallisticValueChanged(ListSelectionEvent evt) {
/* 12421 */     if (this.lstChooseBallistic.getSelectedIndex() < 0) return;
/* 12422 */     abPlaceable p = (abPlaceable)this.Equipment[0][this.lstChooseBallistic.getSelectedIndex()];
/* 12423 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void cmbPWRLTypeActionPerformed(ActionEvent evt) {
/* 12427 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetRLArmorType()).equals((String)this.cmbPWRLType.getSelectedItem())) {
/* 12428 */       return;
/*       */     }
/* 12430 */     RecalcPatchworkArmor(7);
/*       */     
/* 12432 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12435 */     RefreshSummary();
/* 12436 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWLLTypeActionPerformed(ActionEvent evt) {
/* 12440 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetLLArmorType()).equals((String)this.cmbPWLLType.getSelectedItem())) {
/* 12441 */       return;
/*       */     }
/* 12443 */     RecalcPatchworkArmor(6);
/*       */     
/* 12445 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12448 */     RefreshSummary();
/* 12449 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWRATypeActionPerformed(ActionEvent evt) {
/* 12453 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetRAArmorType()).equals((String)this.cmbPWRAType.getSelectedItem())) {
/* 12454 */       return;
/*       */     }
/* 12456 */     RecalcPatchworkArmor(5);
/*       */     
/* 12458 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12461 */     RefreshSummary();
/* 12462 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWLATypeActionPerformed(ActionEvent evt) {
/* 12466 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetLAArmorType()).equals((String)this.cmbPWLAType.getSelectedItem())) {
/* 12467 */       return;
/*       */     }
/* 12469 */     RecalcPatchworkArmor(4);
/*       */     
/* 12471 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12474 */     RefreshSummary();
/* 12475 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWRTTypeActionPerformed(ActionEvent evt) {
/* 12479 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetRTArmorType()).equals((String)this.cmbPWRTType.getSelectedItem())) {
/* 12480 */       return;
/*       */     }
/* 12482 */     RecalcPatchworkArmor(3);
/*       */     
/* 12484 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12487 */     RefreshSummary();
/* 12488 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWLTTypeActionPerformed(ActionEvent evt) {
/* 12492 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetLTArmorType()).equals((String)this.cmbPWLTType.getSelectedItem())) {
/* 12493 */       return;
/*       */     }
/* 12495 */     RecalcPatchworkArmor(2);
/*       */     
/* 12497 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12500 */     RefreshSummary();
/* 12501 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWCTTypeActionPerformed(ActionEvent evt) {
/* 12505 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetCTArmorType()).equals((String)this.cmbPWCTType.getSelectedItem())) {
/* 12506 */       return;
/*       */     }
/* 12508 */     RecalcPatchworkArmor(1);
/*       */     
/* 12510 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12513 */     RefreshSummary();
/* 12514 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWHDTypeActionPerformed(ActionEvent evt) {
/* 12518 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetHDArmorType()).equals((String)this.cmbPWHDType.getSelectedItem())) {
/* 12519 */       return;
/*       */     }
/* 12521 */     RecalcPatchworkArmor(0);
/*       */     
/* 12523 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12526 */     RefreshSummary();
/* 12527 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnRemainingArmorActionPerformed(ActionEvent evt)
/*       */   {
/* 12532 */     double freetons = this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons() + this.CurMech.GetArmor().GetTonnage();
/*       */     
/* 12534 */     if (freetons > this.CurMech.GetArmor().GetMaxTonnage()) {
/* 12535 */       freetons = this.CurMech.GetArmor().GetMaxTonnage();
/*       */     }
/*       */     
/* 12538 */     this.ArmorTons.SetArmorTonnage(freetons);
/*       */     try {
/* 12540 */       this.CurMech.Visit(this.ArmorTons);
/*       */     }
/*       */     catch (Exception e) {
/* 12543 */       System.err.println(e.getMessage());
/* 12544 */       e.printStackTrace();
/*       */     }
/*       */     
/*       */ 
/* 12548 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 12551 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 12552 */     n.setValue(Integer.valueOf(this.CurMech.GetArmor().GetLocationArmor(0)));
/*       */     
/*       */ 
/* 12555 */     RefreshSummary();
/* 12556 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   private void btnEfficientArmorActionPerformed(ActionEvent evt)
/*       */   {
/* 12564 */     int MaxArmor = this.CurMech.GetArmor().GetMaxArmor();
/* 12565 */     int MaxLessArmor = (int)((this.CurMech.GetArmor().GetMaxTonnage() - 0.5D) * 16.0D * this.CurMech.GetArmor().GetAVMult());
/*       */     
/* 12567 */     if (MaxArmor - MaxLessArmor > 5.0D * this.CurMech.GetArmor().GetAVMult())
/*       */     {
/* 12569 */       this.ArmorTons.SetArmorTonnage(this.CurMech.GetArmor().GetMaxTonnage());
/*       */     }
/*       */     else {
/* 12572 */       this.ArmorTons.SetArmorTonnage(this.CurMech.GetArmor().GetMaxTonnage() - 0.5D);
/*       */     }
/*       */     
/*       */     try
/*       */     {
/* 12577 */       this.CurMech.Visit(this.ArmorTons);
/*       */     }
/*       */     catch (Exception e) {
/* 12580 */       System.err.println(e.getMessage());
/* 12581 */       e.printStackTrace();
/*       */     }
/*       */     
/*       */ 
/* 12585 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 12588 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 12589 */     n.setValue(Integer.valueOf(this.CurMech.GetArmor().GetLocationArmor(0)));
/*       */     
/*       */ 
/* 12592 */     RefreshSummary();
/* 12593 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbArmorTypeActionPerformed(ActionEvent evt) {
/* 12597 */     if (BuildLookupName(this.CurMech.GetArmor().GetCurrentState()).equals((String)this.cmbArmorType.getSelectedItem())) {
/* 12598 */       return;
/*       */     }
/* 12600 */     RecalcArmor();
/*       */     
/* 12602 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 12605 */     RefreshSummary();
/* 12606 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnArmorTonsActionPerformed(ActionEvent evt)
/*       */   {
/* 12611 */     dlgArmorTonnage ArmorDialogue = new dlgArmorTonnage(this, true);
/* 12612 */     ArmorDialogue.setLocationRelativeTo(this);
/* 12613 */     ArmorDialogue.setVisible(true);
/*       */     
/*       */ 
/* 12616 */     if (ArmorDialogue.NewTonnage()) {
/* 12617 */       double result = ArmorDialogue.GetResult();
/* 12618 */       this.ArmorTons.SetArmorTonnage(result);
/*       */       try {
/* 12620 */         this.CurMech.Visit(this.ArmorTons);
/*       */       }
/*       */       catch (Exception e) {
/* 12623 */         System.err.println(e.getMessage());
/* 12624 */         e.printStackTrace();
/*       */       }
/*       */       
/* 12627 */       ArmorDialogue.dispose();
/*       */     } else {
/* 12629 */       ArmorDialogue.dispose();
/*       */     }
/*       */     
/*       */ 
/* 12633 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 12636 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 12637 */     n.setValue(Integer.valueOf(this.CurMech.GetArmor().GetLocationArmor(0)));
/*       */     
/*       */ 
/* 12640 */     RefreshSummary();
/* 12641 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnMaxArmorActionPerformed(ActionEvent evt)
/*       */   {
/* 12646 */     MechArmor a = this.CurMech.GetArmor();
/*       */     
/*       */ 
/* 12649 */     a.SetArmor(0, 9);
/* 12650 */     a.SetArmor(4, a.GetLocationMax(4));
/* 12651 */     a.SetArmor(5, a.GetLocationMax(5));
/* 12652 */     a.SetArmor(6, a.GetLocationMax(6));
/* 12653 */     a.SetArmor(7, a.GetLocationMax(7));
/*       */     
/*       */ 
/* 12656 */     int rear = Math.round(a.GetLocationMax(1) * this.Prefs.getInt("ArmorCTRPercent", 25) / 100);
/* 12657 */     a.SetArmor(8, rear);
/* 12658 */     a.SetArmor(1, a.GetLocationMax(1) - rear);
/* 12659 */     rear = Math.round(a.GetLocationMax(2) * this.Prefs.getInt("ArmorSTRPercent", 25) / 100);
/* 12660 */     a.SetArmor(9, rear);
/* 12661 */     a.SetArmor(2, a.GetLocationMax(2) - rear);
/* 12662 */     rear = Math.round(a.GetLocationMax(3) * this.Prefs.getInt("ArmorSTRPercent", 25) / 100);
/* 12663 */     a.SetArmor(10, rear);
/* 12664 */     a.SetArmor(3, a.GetLocationMax(3) - rear);
/*       */     
/*       */ 
/* 12667 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 12670 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 12671 */     n.setValue(Integer.valueOf(a.GetLocationArmor(0)));
/*       */     
/*       */ 
/* 12674 */     RefreshSummary();
/* 12675 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnLTRArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 12680 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLTRArmor.getModel();
/* 12681 */     javax.swing.JComponent editor = this.spnLTRArmor.getEditor();
/* 12682 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 12686 */       this.spnLTRArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 12690 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 12691 */         tf.setValue(this.spnLTRArmor.getValue());
/*       */       }
/* 12693 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12697 */     MechArmor a = this.CurMech.GetArmor();
/* 12698 */     int curmech = a.GetLocationArmor(9);
/* 12699 */     int curframe = n.getNumber().intValue();
/* 12700 */     if (curframe > curmech) {
/* 12701 */       while (curframe > curmech) {
/* 12702 */         a.IncrementArmor(9);
/* 12703 */         curframe--;
/*       */       }
/*       */     }
/* 12706 */     while (curmech > curframe) {
/* 12707 */       a.DecrementArmor(9);
/* 12708 */       curmech = a.GetLocationArmor(9);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 12713 */     n = (SpinnerNumberModel)this.spnLTArmor.getModel();
/* 12714 */     n.setValue(Integer.valueOf(a.GetLocationArmor(2)));
/*       */     
/*       */ 
/* 12717 */     if (this.btnBalanceArmor.isSelected()) {
/* 12718 */       n = (SpinnerNumberModel)this.spnLTRArmor.getModel();
/* 12719 */       a.SetArmor(10, n.getNumber().intValue());
/* 12720 */       n = (SpinnerNumberModel)this.spnRTRArmor.getModel();
/* 12721 */       n.setValue(Integer.valueOf(a.GetLocationArmor(10)));
/*       */     }
/*       */     
/*       */ 
/* 12725 */     RefreshSummary();
/* 12726 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnCTRArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 12731 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnCTRArmor.getModel();
/* 12732 */     javax.swing.JComponent editor = this.spnCTRArmor.getEditor();
/* 12733 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 12737 */       this.spnCTRArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 12741 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 12742 */         tf.setValue(this.spnCTRArmor.getValue());
/*       */       }
/* 12744 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12748 */     MechArmor a = this.CurMech.GetArmor();
/* 12749 */     int curmech = a.GetLocationArmor(8);
/* 12750 */     int curframe = n.getNumber().intValue();
/* 12751 */     if (curframe > curmech) {
/* 12752 */       while (curframe > curmech) {
/* 12753 */         a.IncrementArmor(8);
/* 12754 */         curframe--;
/*       */       }
/*       */     }
/* 12757 */     while (curmech > curframe) {
/* 12758 */       a.DecrementArmor(8);
/* 12759 */       curmech = a.GetLocationArmor(8);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 12764 */     n = (SpinnerNumberModel)this.spnCTArmor.getModel();
/* 12765 */     n.setValue(Integer.valueOf(a.GetLocationArmor(1)));
/*       */     
/*       */ 
/* 12768 */     RefreshSummary();
/* 12769 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnRTRArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 12774 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRTRArmor.getModel();
/* 12775 */     javax.swing.JComponent editor = this.spnRTRArmor.getEditor();
/* 12776 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 12780 */       this.spnRTRArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 12784 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 12785 */         tf.setValue(this.spnRTRArmor.getValue());
/*       */       }
/* 12787 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12791 */     MechArmor a = this.CurMech.GetArmor();
/* 12792 */     int curmech = a.GetLocationArmor(10);
/* 12793 */     int curframe = n.getNumber().intValue();
/* 12794 */     if (curframe > curmech) {
/* 12795 */       while (curframe > curmech) {
/* 12796 */         a.IncrementArmor(10);
/* 12797 */         curframe--;
/*       */       }
/*       */     }
/* 12800 */     while (curmech > curframe) {
/* 12801 */       a.DecrementArmor(10);
/* 12802 */       curmech = a.GetLocationArmor(10);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 12807 */     n = (SpinnerNumberModel)this.spnRTArmor.getModel();
/* 12808 */     n.setValue(Integer.valueOf(a.GetLocationArmor(3)));
/*       */     
/*       */ 
/*       */ 
/* 12812 */     if (this.btnBalanceArmor.isSelected()) {
/* 12813 */       n = (SpinnerNumberModel)this.spnRTRArmor.getModel();
/* 12814 */       a.SetArmor(9, n.getNumber().intValue());
/* 12815 */       n = (SpinnerNumberModel)this.spnLTRArmor.getModel();
/* 12816 */       n.setValue(Integer.valueOf(a.GetLocationArmor(9)));
/*       */     }
/*       */     
/*       */ 
/* 12820 */     RefreshSummary();
/* 12821 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnLAArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 12826 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLAArmor.getModel();
/* 12827 */     javax.swing.JComponent editor = this.spnLAArmor.getEditor();
/* 12828 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 12832 */       this.spnLAArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 12836 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 12837 */         tf.setValue(this.spnLAArmor.getValue());
/*       */       }
/* 12839 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12843 */     MechArmor a = this.CurMech.GetArmor();
/* 12844 */     int curmech = a.GetLocationArmor(4);
/* 12845 */     int curframe = n.getNumber().intValue();
/* 12846 */     if (curframe > curmech) {
/* 12847 */       while (curframe > curmech) {
/* 12848 */         a.IncrementArmor(4);
/* 12849 */         curframe--;
/*       */       }
/*       */     }
/* 12852 */     while (curmech > curframe) {
/* 12853 */       a.DecrementArmor(4);
/* 12854 */       curmech = a.GetLocationArmor(4);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 12859 */     if (this.btnBalanceArmor.isSelected()) {
/* 12860 */       a.SetArmor(5, n.getNumber().intValue());
/* 12861 */       n = (SpinnerNumberModel)this.spnRAArmor.getModel();
/* 12862 */       n.setValue(Integer.valueOf(a.GetLocationArmor(5)));
/*       */     }
/*       */     
/*       */ 
/* 12866 */     RefreshSummary();
/* 12867 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnRTArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 12872 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRTArmor.getModel();
/* 12873 */     javax.swing.JComponent editor = this.spnRTArmor.getEditor();
/* 12874 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 12878 */       this.spnRTArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 12882 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 12883 */         tf.setValue(this.spnRTArmor.getValue());
/*       */       }
/* 12885 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12889 */     MechArmor a = this.CurMech.GetArmor();
/* 12890 */     int curmech = a.GetLocationArmor(3);
/* 12891 */     int curframe = n.getNumber().intValue();
/* 12892 */     if (curframe > curmech) {
/* 12893 */       while (curframe > curmech) {
/* 12894 */         a.IncrementArmor(3);
/* 12895 */         curframe--;
/*       */       }
/*       */     }
/* 12898 */     while (curmech > curframe) {
/* 12899 */       a.DecrementArmor(3);
/* 12900 */       curmech = a.GetLocationArmor(3);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 12905 */     n = (SpinnerNumberModel)this.spnRTRArmor.getModel();
/* 12906 */     n.setValue(Integer.valueOf(a.GetLocationArmor(10)));
/*       */     
/*       */ 
/* 12909 */     if (this.btnBalanceArmor.isSelected()) {
/* 12910 */       n = (SpinnerNumberModel)this.spnRTArmor.getModel();
/* 12911 */       a.SetArmor(2, n.getNumber().intValue());
/* 12912 */       n = (SpinnerNumberModel)this.spnLTArmor.getModel();
/* 12913 */       n.setValue(Integer.valueOf(a.GetLocationArmor(2)));
/*       */     }
/*       */     
/*       */ 
/* 12917 */     RefreshSummary();
/* 12918 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnLTArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 12923 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLTArmor.getModel();
/* 12924 */     javax.swing.JComponent editor = this.spnLTArmor.getEditor();
/* 12925 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 12929 */       this.spnLTArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 12933 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 12934 */         tf.setValue(this.spnLTArmor.getValue());
/*       */       }
/* 12936 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12940 */     MechArmor a = this.CurMech.GetArmor();
/* 12941 */     int curmech = a.GetLocationArmor(2);
/* 12942 */     int curframe = n.getNumber().intValue();
/* 12943 */     if (curframe > curmech) {
/* 12944 */       while (curframe > curmech) {
/* 12945 */         a.IncrementArmor(2);
/* 12946 */         curframe--;
/*       */       }
/*       */     }
/* 12949 */     while (curmech > curframe) {
/* 12950 */       a.DecrementArmor(2);
/* 12951 */       curmech = a.GetLocationArmor(2);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 12956 */     n = (SpinnerNumberModel)this.spnLTRArmor.getModel();
/* 12957 */     n.setValue(Integer.valueOf(a.GetLocationArmor(9)));
/*       */     
/*       */ 
/* 12960 */     if (this.btnBalanceArmor.isSelected()) {
/* 12961 */       n = (SpinnerNumberModel)this.spnLTArmor.getModel();
/* 12962 */       a.SetArmor(3, n.getNumber().intValue());
/* 12963 */       n = (SpinnerNumberModel)this.spnRTArmor.getModel();
/* 12964 */       n.setValue(Integer.valueOf(a.GetLocationArmor(3)));
/*       */     }
/*       */     
/*       */ 
/* 12968 */     RefreshSummary();
/* 12969 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnCTArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 12974 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnCTArmor.getModel();
/* 12975 */     javax.swing.JComponent editor = this.spnCTArmor.getEditor();
/* 12976 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 12980 */       this.spnCTArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 12984 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 12985 */         tf.setValue(this.spnCTArmor.getValue());
/*       */       }
/* 12987 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12991 */     MechArmor a = this.CurMech.GetArmor();
/* 12992 */     int curmech = a.GetLocationArmor(1);
/* 12993 */     int curframe = n.getNumber().intValue();
/* 12994 */     if (curframe > curmech) {
/* 12995 */       while (curframe > curmech) {
/* 12996 */         a.IncrementArmor(1);
/* 12997 */         curframe--;
/*       */       }
/*       */     }
/* 13000 */     while (curmech > curframe) {
/* 13001 */       a.DecrementArmor(1);
/* 13002 */       curmech = a.GetLocationArmor(1);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13007 */     n = (SpinnerNumberModel)this.spnCTRArmor.getModel();
/* 13008 */     n.setValue(Integer.valueOf(a.GetLocationArmor(8)));
/*       */     
/*       */ 
/* 13011 */     RefreshSummary();
/* 13012 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnHDArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 13017 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 13018 */     javax.swing.JComponent editor = this.spnHDArmor.getEditor();
/* 13019 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 13023 */       this.spnHDArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 13027 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 13028 */         tf.setValue(this.spnHDArmor.getValue());
/*       */       }
/* 13030 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13034 */     MechArmor a = this.CurMech.GetArmor();
/* 13035 */     int curmech = a.GetLocationArmor(0);
/* 13036 */     int curframe = n.getNumber().intValue();
/* 13037 */     if (curframe > curmech) {
/* 13038 */       while (curframe > curmech) {
/* 13039 */         a.IncrementArmor(0);
/* 13040 */         curframe--;
/*       */       }
/*       */     }
/* 13043 */     while (curmech > curframe) {
/* 13044 */       a.DecrementArmor(0);
/* 13045 */       curmech = a.GetLocationArmor(0);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13050 */     RefreshSummary();
/* 13051 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnRAArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 13056 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRAArmor.getModel();
/* 13057 */     javax.swing.JComponent editor = this.spnRAArmor.getEditor();
/* 13058 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 13062 */       this.spnRAArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 13066 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 13067 */         tf.setValue(this.spnRAArmor.getValue());
/*       */       }
/* 13069 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13073 */     MechArmor a = this.CurMech.GetArmor();
/* 13074 */     int curmech = a.GetLocationArmor(5);
/* 13075 */     int curframe = n.getNumber().intValue();
/* 13076 */     if (curframe > curmech) {
/* 13077 */       while (curframe > curmech) {
/* 13078 */         a.IncrementArmor(5);
/* 13079 */         curframe--;
/*       */       }
/*       */     }
/* 13082 */     while (curmech > curframe) {
/* 13083 */       a.DecrementArmor(5);
/* 13084 */       curmech = a.GetLocationArmor(5);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13089 */     if (this.btnBalanceArmor.isSelected()) {
/* 13090 */       a.SetArmor(4, n.getNumber().intValue());
/* 13091 */       n = (SpinnerNumberModel)this.spnLAArmor.getModel();
/* 13092 */       n.setValue(Integer.valueOf(a.GetLocationArmor(4)));
/*       */     }
/*       */     
/*       */ 
/* 13096 */     RefreshSummary();
/* 13097 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnLLArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 13102 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLLArmor.getModel();
/* 13103 */     javax.swing.JComponent editor = this.spnLLArmor.getEditor();
/* 13104 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 13108 */       this.spnLLArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 13112 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 13113 */         tf.setValue(this.spnLLArmor.getValue());
/*       */       }
/* 13115 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13119 */     MechArmor a = this.CurMech.GetArmor();
/* 13120 */     int curmech = a.GetLocationArmor(6);
/* 13121 */     int curframe = n.getNumber().intValue();
/* 13122 */     if (curframe > curmech) {
/* 13123 */       while (curframe > curmech) {
/* 13124 */         a.IncrementArmor(6);
/* 13125 */         curframe--;
/*       */       }
/*       */     }
/* 13128 */     while (curmech > curframe) {
/* 13129 */       a.DecrementArmor(6);
/* 13130 */       curmech = a.GetLocationArmor(6);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13135 */     if (this.btnBalanceArmor.isSelected()) {
/* 13136 */       a.SetArmor(7, n.getNumber().intValue());
/* 13137 */       n = (SpinnerNumberModel)this.spnRLArmor.getModel();
/* 13138 */       n.setValue(Integer.valueOf(a.GetLocationArmor(7)));
/*       */     }
/*       */     
/*       */ 
/* 13142 */     RefreshSummary();
/* 13143 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnRLArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 13148 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRLArmor.getModel();
/* 13149 */     javax.swing.JComponent editor = this.spnRLArmor.getEditor();
/* 13150 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 13154 */       this.spnRLArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 13158 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 13159 */         tf.setValue(this.spnRLArmor.getValue());
/*       */       }
/* 13161 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13165 */     MechArmor a = this.CurMech.GetArmor();
/* 13166 */     int curmech = a.GetLocationArmor(7);
/* 13167 */     int curframe = n.getNumber().intValue();
/* 13168 */     if (curframe > curmech) {
/* 13169 */       while (curframe > curmech) {
/* 13170 */         a.IncrementArmor(7);
/* 13171 */         curframe--;
/*       */       }
/*       */     }
/* 13174 */     while (curmech > curframe) {
/* 13175 */       a.DecrementArmor(7);
/* 13176 */       curmech = a.GetLocationArmor(7);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13181 */     if (this.btnBalanceArmor.isSelected()) {
/* 13182 */       a.SetArmor(6, n.getNumber().intValue());
/* 13183 */       n = (SpinnerNumberModel)this.spnLLArmor.getModel();
/* 13184 */       n.setValue(Integer.valueOf(a.GetLocationArmor(6)));
/*       */     }
/*       */     
/*       */ 
/* 13188 */     RefreshSummary();
/* 13189 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkTracksActionPerformed(ActionEvent evt)
/*       */   {
/* 13194 */     if (this.chkTracks.isSelected() == this.CurMech.HasTracks()) return;
/*       */     try {
/* 13196 */       if (this.chkTracks.isSelected()) {
/* 13197 */         this.CurMech.SetTracks(true);
/*       */       } else {
/* 13199 */         this.CurMech.SetTracks(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13202 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13204 */       this.chkTracks.setSelected(this.CurMech.HasTracks());
/* 13205 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13209 */     RefreshSummary();
/* 13210 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkEnviroSealingActionPerformed(ActionEvent evt)
/*       */   {
/* 13215 */     if (this.chkEnviroSealing.isSelected() == this.CurMech.HasEnviroSealing()) return;
/*       */     try {
/* 13217 */       if (this.chkEnviroSealing.isSelected()) {
/* 13218 */         this.CurMech.SetEnviroSealing(true);
/*       */       } else {
/* 13220 */         this.CurMech.SetEnviroSealing(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13223 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13225 */       this.chkEnviroSealing.setSelected(this.CurMech.HasEnviroSealing());
/* 13226 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13230 */     RefreshSummary();
/* 13231 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkEjectionSeatActionPerformed(ActionEvent evt) {
/* 13235 */     if (this.chkEjectionSeat.isSelected() == this.CurMech.HasEjectionSeat()) return;
/*       */     try {
/* 13237 */       if (this.chkEjectionSeat.isSelected()) {
/* 13238 */         this.CurMech.SetEjectionSeat(true);
/*       */       } else {
/* 13240 */         this.CurMech.SetEjectionSeat(false);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 13244 */       this.chkEjectionSeat.setSelected(this.CurMech.HasEjectionSeat());
/* 13245 */       Media.Messager(this, e.getMessage());
/* 13246 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13250 */     RefreshSummary();
/* 13251 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkFHESActionPerformed(ActionEvent evt) {
/* 13255 */     if (this.chkFHES.isSelected() == this.CurMech.HasFHES()) return;
/* 13256 */     if (this.chkFHES.isSelected()) {
/* 13257 */       this.CurMech.SetFHES(true);
/*       */     } else {
/* 13259 */       this.CurMech.SetFHES(false);
/*       */     }
/*       */     
/*       */ 
/* 13263 */     RefreshEquipment();
/* 13264 */     RefreshSummary();
/* 13265 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkPartialWingActionPerformed(ActionEvent evt) {
/* 13269 */     if (this.chkPartialWing.isSelected() == this.CurMech.UsingPartialWing()) return;
/* 13270 */     if (this.chkPartialWing.isSelected()) {
/*       */       try {
/* 13272 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 13273 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 13274 */           tech.setLocationRelativeTo(this);
/* 13275 */           tech.setVisible(true);
/* 13276 */           this.CurMech.SetPartialWing(this.chkPartialWing.isSelected(), tech.IsClan());
/* 13277 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 13278 */           this.CurMech.SetPartialWing(this.chkPartialWing.isSelected(), true);
/*       */         } else {
/* 13280 */           this.CurMech.SetPartialWing(this.chkPartialWing.isSelected(), false);
/*       */         }
/*       */       } catch (Exception e) {
/* 13283 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */     } else {
/*       */       try {
/* 13287 */         this.CurMech.SetPartialWing(false);
/*       */       } catch (Exception e) {
/* 13289 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 13294 */     RefreshEquipment();
/* 13295 */     RefreshSummary();
/* 13296 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbSCLocActionPerformed(ActionEvent evt) {
/* 13300 */     int curLoc = this.CurMech.GetLoadout().Find(this.CurMech.GetLoadout().GetSupercharger());
/* 13301 */     int DesiredLoc = filehandlers.FileCommon.DecodeLocation((String)this.cmbSCLoc.getSelectedItem());
/* 13302 */     if (curLoc == DesiredLoc) return;
/* 13303 */     if (this.CurMech.GetLoadout().HasSupercharger()) {
/*       */       try {
/* 13305 */         this.CurMech.GetLoadout().SetSupercharger(true, DesiredLoc, -1);
/*       */       } catch (Exception e) {
/* 13307 */         Media.Messager(this, e.getMessage());
/* 13308 */         this.chkSupercharger.setSelected(false);
/*       */         
/* 13310 */         RefreshSummary();
/* 13311 */         RefreshInfoPane();
/* 13312 */         return;
/*       */       }
/*       */     }
/*       */     
/* 13316 */     RefreshSummary();
/* 13317 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkSuperchargerActionPerformed(ActionEvent evt) {
/* 13321 */     if (this.CurMech.GetLoadout().HasSupercharger() == this.chkSupercharger.isSelected()) {
/* 13322 */       return;
/*       */     }
/*       */     try {
/* 13325 */       this.CurMech.GetLoadout().SetSupercharger(this.chkSupercharger.isSelected(), filehandlers.FileCommon.DecodeLocation((String)this.cmbSCLoc.getSelectedItem()), -1);
/*       */     } catch (Exception e) {
/* 13327 */       Media.Messager(this, e.getMessage());
/*       */       try {
/* 13329 */         this.CurMech.GetLoadout().SetSupercharger(false, 0, -1);
/*       */       }
/*       */       catch (Exception x) {
/* 13332 */         Media.Messager(this, x.getMessage());
/*       */         
/* 13334 */         RefreshSummary();
/* 13335 */         RefreshInfoPane();
/*       */       }
/* 13337 */       this.chkSupercharger.setSelected(false);
/*       */       
/* 13339 */       RefreshSummary();
/* 13340 */       RefreshInfoPane();
/* 13341 */       return;
/*       */     }
/*       */     
/* 13344 */     RefreshSummary();
/* 13345 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkVoidSigActionPerformed(ActionEvent evt)
/*       */   {
/* 13350 */     if (this.chkVoidSig.isSelected() == this.CurMech.HasVoidSig()) return;
/*       */     try {
/* 13352 */       if (this.chkVoidSig.isSelected()) {
/* 13353 */         this.CurMech.SetVoidSig(true);
/* 13354 */         if (!AddECM()) {
/* 13355 */           this.CurMech.SetVoidSig(false);
/* 13356 */           throw new Exception("No ECM Suite was available to support the Void Signature System!\nUninstalling system.");
/*       */         }
/*       */       } else {
/* 13359 */         this.CurMech.SetVoidSig(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13362 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13364 */       this.chkVoidSig.setSelected(this.CurMech.HasVoidSig());
/* 13365 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13369 */     RefreshSummary();
/* 13370 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkBSPFDActionPerformed(ActionEvent evt)
/*       */   {
/* 13375 */     if (this.chkBSPFD.isSelected() == this.CurMech.HasBlueShield()) return;
/*       */     try {
/* 13377 */       if (this.chkBSPFD.isSelected()) {
/* 13378 */         this.CurMech.SetBlueShield(true);
/*       */       } else {
/* 13380 */         this.CurMech.SetBlueShield(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13383 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13385 */       this.chkBSPFD.setSelected(this.CurMech.HasBlueShield());
/* 13386 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13390 */     RefreshSummary();
/* 13391 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkNullSigActionPerformed(ActionEvent evt)
/*       */   {
/* 13396 */     if (this.chkNullSig.isSelected() == this.CurMech.HasNullSig()) return;
/*       */     try {
/* 13398 */       if (this.chkNullSig.isSelected()) {
/* 13399 */         this.CurMech.SetNullSig(true);
/*       */       } else {
/* 13401 */         this.CurMech.SetNullSig(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13404 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13406 */       this.chkNullSig.setSelected(this.CurMech.HasNullSig());
/* 13407 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13411 */     RefreshSummary();
/* 13412 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkCLPSActionPerformed(ActionEvent evt)
/*       */   {
/* 13417 */     if (this.chkCLPS.isSelected() == this.CurMech.HasChameleon()) return;
/*       */     try {
/* 13419 */       if (this.chkCLPS.isSelected()) {
/* 13420 */         this.CurMech.SetChameleon(true);
/*       */       } else {
/* 13422 */         this.CurMech.SetChameleon(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13425 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13427 */       this.chkCLPS.setSelected(this.CurMech.HasChameleon());
/* 13428 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13432 */     RefreshSummary();
/* 13433 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnRenameVariantActionPerformed(ActionEvent evt) {
/* 13437 */     SaveOmniFluffInfo();
/* 13438 */     String VariantName = "";
/*       */     
/*       */ 
/* 13441 */     dlgOmniBase input = new dlgOmniBase(this, true);
/* 13442 */     input.setTitle("Name this variant");
/* 13443 */     input.setLocationRelativeTo(this);
/* 13444 */     input.setVisible(true);
/* 13445 */     if (input.WasCanceled()) {
/* 13446 */       input.dispose();
/* 13447 */       return;
/*       */     }
/* 13449 */     VariantName = input.GetInput();
/* 13450 */     input.dispose();
/*       */     
/*       */ 
/* 13453 */     if (this.CurMech.GetBaseLoadout().GetName().equals(VariantName)) {
/* 13454 */       Media.Messager(this, "\"" + VariantName + "\" is reserved for the base loadout and cannot be used\nto name this loadout.  Please choose another name.");
/* 13455 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13459 */     ArrayList Loadouts = this.CurMech.GetLoadouts();
/* 13460 */     for (int i = 0; i < Loadouts.size(); i++) {
/* 13461 */       if (((ifMechLoadout)Loadouts.get(i)).GetName().equals(VariantName)) {
/* 13462 */         Media.Messager(this, "Could not rename the loadout because\nthe name given matches an existing loadout.");
/* 13463 */         return;
/*       */       }
/*       */     }
/*       */     
/* 13467 */     this.CurMech.GetLoadout().SetName(VariantName);
/* 13468 */     RefreshOmniVariants();
/*       */   }
/*       */   
/*       */   private void btnDeleteVariantActionPerformed(ActionEvent evt)
/*       */   {
/* 13473 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this variant?", "Delete Variant?", 0);
/*       */     
/* 13475 */     if (choice == 1) {
/* 13476 */       return;
/*       */     }
/* 13478 */     if (this.CurMech.GetLoadout().GetName().equals("Base Loadout")) {
/* 13479 */       Media.Messager(this, "You cannot remove the base chassis.");
/* 13480 */       return;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13485 */     this.CurMech.RemoveLoadout(this.CurMech.GetLoadout().GetName());
/*       */     
/*       */ 
/* 13488 */     LoadOmniFluffInfo();
/* 13489 */     RefreshOmniVariants();
/* 13490 */     FixTransferHandlers();
/* 13491 */     SetLoadoutArrays();
/* 13492 */     SetWeaponChoosers();
/* 13493 */     BuildJumpJetSelector();
/* 13494 */     FixJJSpinnerModel();
/* 13495 */     FixHeatSinkSpinnerModel();
/* 13496 */     RefreshOmniChoices();
/* 13497 */     SolidifyJJManufacturer();
/* 13498 */     RefreshSummary();
/* 13499 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnAddVariantActionPerformed(ActionEvent evt) {
/* 13503 */     SaveOmniFluffInfo();
/* 13504 */     String VariantName = "";
/*       */     
/*       */ 
/* 13507 */     dlgOmniBase input = new dlgOmniBase(this, true);
/* 13508 */     input.setTitle("Name this variant");
/* 13509 */     input.setLocationRelativeTo(this);
/* 13510 */     input.setVisible(true);
/* 13511 */     if (input.WasCanceled()) {
/* 13512 */       input.dispose();
/* 13513 */       return;
/*       */     }
/* 13515 */     VariantName = input.GetInput();
/* 13516 */     input.dispose();
/*       */     
/*       */ 
/*       */     try
/*       */     {
/* 13521 */       this.CurMech.AddLoadout(VariantName);
/*       */     }
/*       */     catch (Exception e) {
/* 13524 */       Media.Messager(this, e.getMessage());
/* 13525 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13529 */     LoadOmniFluffInfo();
/* 13530 */     FixTransferHandlers();
/* 13531 */     SetLoadoutArrays();
/* 13532 */     SetWeaponChoosers();
/* 13533 */     BuildJumpJetSelector();
/* 13534 */     FixJJSpinnerModel();
/* 13535 */     FixHeatSinkSpinnerModel();
/* 13536 */     RefreshOmniVariants();
/* 13537 */     RefreshOmniChoices();
/* 13538 */     SolidifyJJManufacturer();
/* 13539 */     RefreshSummary();
/* 13540 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnLockChassisActionPerformed(ActionEvent evt)
/*       */   {
/* 13545 */     SaveOmniFluffInfo();
/* 13546 */     String VariantName = "";
/*       */     
/*       */ 
/* 13549 */     if (this.CurMech.GetLoadout().GetQueue().size() != 0) {
/* 13550 */       Media.Messager(this, "You must place all items first.");
/* 13551 */       this.tbpMainTabPane.setSelectedComponent(this.pnlEquipment);
/* 13552 */       return;
/*       */     }
/*       */     
/* 13555 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to lock the chassis?\nAll items in the base loadout will be locked in location\nand most chassis specifications will be locked.", "Lock Chassis?", 0);
/*       */     
/*       */ 
/*       */ 
/* 13559 */     if (choice == 1) {
/* 13560 */       return;
/*       */     }
/*       */     
/* 13563 */     dlgOmniBase input = new dlgOmniBase(this, true);
/* 13564 */     input.setTitle("Name the first variant");
/* 13565 */     input.setLocationRelativeTo(this);
/* 13566 */     input.setVisible(true);
/* 13567 */     if (input.WasCanceled()) {
/* 13568 */       input.dispose();
/* 13569 */       return;
/*       */     }
/* 13571 */     VariantName = input.GetInput();
/* 13572 */     input.dispose();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/* 13577 */     if ("Base Loadout".equals(VariantName)) {
/* 13578 */       Media.Messager(this, "\"" + VariantName + "\" is reserved for the base loadout and cannot be used\nfor a new loadout.  Please choose another name.");
/* 13579 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13583 */     this.CurMech.SetOmnimech(VariantName);
/* 13584 */     this.chkOmnimech.setEnabled(false);
/* 13585 */     FixTransferHandlers();
/* 13586 */     SetLoadoutArrays();
/* 13587 */     FixJJSpinnerModel();
/* 13588 */     FixHeatSinkSpinnerModel();
/* 13589 */     LockGUIForOmni();
/* 13590 */     RefreshOmniVariants();
/* 13591 */     RefreshOmniChoices();
/* 13592 */     SolidifyJJManufacturer();
/* 13593 */     RefreshSummary();
/* 13594 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkBoostersActionPerformed(ActionEvent evt) {
/* 13598 */     if (this.chkBoosters.isSelected() == this.CurMech.UsingJumpBooster()) return;
/*       */     try {
/* 13600 */       this.CurMech.SetJumpBooster(this.chkBoosters.isSelected());
/*       */     } catch (Exception e) {
/* 13602 */       Media.Messager(this, e.getMessage());
/*       */     }
/* 13604 */     this.spnBoosterMP.setEnabled(this.CurMech.UsingJumpBooster());
/* 13605 */     FixJumpBoosterSpinnerModel();
/*       */     
/*       */ 
/* 13608 */     RefreshEquipment();
/* 13609 */     RefreshSummary();
/* 13610 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnBoosterMPStateChanged(ChangeEvent evt)
/*       */   {
/* 13615 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnBoosterMP.getModel();
/* 13616 */     javax.swing.JComponent editor = this.spnBoosterMP.getEditor();
/* 13617 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 13621 */       this.spnBoosterMP.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 13625 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 13626 */         tf.setValue(this.spnBoosterMP.getValue());
/*       */       }
/* 13628 */       return;
/*       */     }
/*       */     
/* 13631 */     this.CurMech.GetJumpBooster().SetBoostMP(n.getNumber().intValue());
/*       */     
/*       */ 
/* 13634 */     FixJumpBoosterSpinnerModel();
/* 13635 */     RefreshSummary();
/* 13636 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbJumpJetTypeActionPerformed(ActionEvent evt) {
/* 13640 */     RecalcJumpJets();
/* 13641 */     RefreshSummary();
/* 13642 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnJumpMPStateChanged(ChangeEvent evt)
/*       */   {
/* 13647 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnJumpMP.getModel();
/* 13648 */     javax.swing.JComponent editor = this.spnJumpMP.getEditor();
/* 13649 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/* 13650 */     int NumJJ = this.CurMech.GetJumpJets().GetNumJJ();
/*       */     
/*       */     try
/*       */     {
/* 13654 */       this.spnWalkMP.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 13658 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 13659 */         tf.setValue(this.spnWalkMP.getValue());
/*       */       }
/* 13661 */       return;
/*       */     }
/*       */     
/* 13664 */     if (n.getNumber().intValue() > NumJJ)
/*       */     {
/* 13666 */       for (int i = NumJJ; i < n.getNumber().intValue(); i++) {
/* 13667 */         this.CurMech.GetJumpJets().IncrementNumJJ();
/*       */       }
/*       */       
/*       */     } else {
/* 13671 */       for (int i = NumJJ; i > n.getNumber().intValue(); i--) {
/* 13672 */         this.CurMech.GetJumpJets().DecrementNumJJ();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 13677 */     if (n.getNumber().intValue() > 0)
/*       */     {
/* 13679 */       this.txtJJModel.setEnabled(true);
/*       */     }
/*       */     else {
/* 13682 */       this.txtJJModel.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/* 13686 */     RefreshSummary();
/* 13687 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnWalkMPStateChanged(ChangeEvent evt)
/*       */   {
/* 13692 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnWalkMP.getModel();
/* 13693 */     javax.swing.JComponent editor = this.spnWalkMP.getEditor();
/* 13694 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 13698 */       this.spnWalkMP.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 13702 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 13703 */         tf.setValue(this.spnWalkMP.getValue());
/*       */       }
/* 13705 */       return;
/*       */     }
/*       */     try
/*       */     {
/* 13709 */       this.CurMech.SetWalkMP(n.getNumber().intValue());
/*       */     } catch (Exception e) {
/* 13711 */       Media.Messager(e.getMessage());
/* 13712 */       this.spnWalkMP.setValue(this.spnWalkMP.getPreviousValue());
/*       */     }
/* 13714 */     this.lblRunMP.setText("" + this.CurMech.GetRunningMP());
/*       */     
/*       */ 
/*       */ 
/* 13718 */     FixJJSpinnerModel();
/* 13719 */     this.CurMech.GetHeatSinks().ReCalculate();
/* 13720 */     this.CurMech.GetLoadout().UnallocateFuelTanks();
/*       */     
/*       */ 
/* 13723 */     RefreshSummary();
/* 13724 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnNumberOfHSStateChanged(ChangeEvent evt)
/*       */   {
/* 13729 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnNumberOfHS.getModel();
/* 13730 */     int NumHS = this.CurMech.GetHeatSinks().GetNumHS();
/* 13731 */     javax.swing.JComponent editor = this.spnNumberOfHS.getEditor();
/* 13732 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 13736 */       this.spnNumberOfHS.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 13740 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 13741 */         tf.setValue(this.spnNumberOfHS.getValue());
/*       */       }
/* 13743 */       return;
/*       */     }
/*       */     
/* 13746 */     if (n.getNumber().intValue() > NumHS)
/*       */     {
/* 13748 */       for (int i = NumHS; i < n.getNumber().intValue(); i++) {
/* 13749 */         this.CurMech.GetHeatSinks().IncrementNumHS();
/*       */       }
/*       */       
/*       */     } else {
/* 13753 */       for (int i = NumHS; i > n.getNumber().intValue(); i--) {
/* 13754 */         this.CurMech.GetHeatSinks().DecrementNumHS();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 13759 */     RefreshSummary();
/* 13760 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbHeatSinkTypeActionPerformed(ActionEvent evt) {
/* 13764 */     if (BuildLookupName(this.CurMech.GetHeatSinks().GetCurrentState()).equals((String)this.cmbHeatSinkType.getSelectedItem())) {
/* 13765 */       return;
/*       */     }
/* 13767 */     RecalcHeatSinks();
/* 13768 */     RefreshSummary();
/* 13769 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkFractionalActionPerformed(ActionEvent evt) {
/* 13773 */     if (this.chkFractional.isSelected() == this.CurMech.UsingFractionalAccounting()) return;
/* 13774 */     this.CurMech.SetFractionalAccounting(this.chkFractional.isSelected());
/* 13775 */     if (!this.CurMech.UsingFractionalAccounting()) {
/* 13776 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 13777 */       for (int i = 0; i < v.size(); i++) {
/* 13778 */         if ((v.get(i) instanceof Ammunition)) {
/* 13779 */           ((Ammunition)v.get(i)).ResetLotSize();
/*       */         }
/*       */       }
/*       */     }
/*       */     
/* 13784 */     RefreshEquipment();
/* 13785 */     RefreshSummary();
/* 13786 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkCommandConsoleActionPerformed(ActionEvent evt) {
/* 13790 */     if (this.chkCommandConsole.isSelected() == this.CurMech.HasCommandConsole()) return;
/* 13791 */     if (this.chkCommandConsole.isSelected()) {
/* 13792 */       if (!this.CurMech.SetCommandConsole(true)) {
/* 13793 */         Media.Messager(this, "Command Console cannot be allocated.");
/* 13794 */         this.chkCommandConsole.setSelected(false);
/*       */       }
/*       */     } else {
/* 13797 */       this.CurMech.SetCommandConsole(false);
/*       */     }
/*       */     
/*       */ 
/* 13801 */     RefreshEquipment();
/* 13802 */     RefreshSummary();
/* 13803 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbMechTypeActionPerformed(ActionEvent evt) {
/* 13807 */     switch (this.cmbMechType.getSelectedIndex()) {
/*       */     case 0: 
/* 13809 */       if (((!this.CurMech.IsIndustrialmech() ? 1 : 0) & (!this.CurMech.IsPrimitive() ? 1 : 0)) != 0) return;
/* 13810 */       this.CurMech.SetModern();
/* 13811 */       this.CurMech.SetBattlemech();
/* 13812 */       break;
/*       */     case 1: 
/* 13814 */       if ((this.CurMech.IsIndustrialmech() & !this.CurMech.IsPrimitive())) return;
/* 13815 */       this.CurMech.SetModern();
/* 13816 */       this.CurMech.SetIndustrialmech();
/* 13817 */       break;
/*       */     case 2: 
/* 13819 */       if ((!this.CurMech.IsIndustrialmech()) && (this.CurMech.IsPrimitive())) return;
/* 13820 */       this.CurMech.SetPrimitive();
/* 13821 */       this.CurMech.SetBattlemech();
/* 13822 */       break;
/*       */     case 3: 
/* 13824 */       if ((this.CurMech.IsIndustrialmech()) && (this.CurMech.IsPrimitive())) return;
/* 13825 */       this.CurMech.SetPrimitive();
/* 13826 */       this.CurMech.SetIndustrialmech();
/*       */     }
/*       */     
/*       */     
/*       */ 
/* 13831 */     CheckTonnage(false);
/*       */     
/*       */ 
/* 13834 */     SetLoadoutArrays();
/*       */     
/*       */ 
/* 13837 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 13840 */     SaveSelections();
/* 13841 */     BuildChassisSelector();
/* 13842 */     BuildEngineSelector();
/* 13843 */     BuildGyroSelector();
/* 13844 */     BuildCockpitSelector();
/* 13845 */     BuildEnhancementSelector();
/* 13846 */     BuildHeatsinkSelector();
/* 13847 */     BuildJumpJetSelector();
/* 13848 */     BuildArmorSelector();
/* 13849 */     RefreshEquipment();
/* 13850 */     CheckOmnimech();
/*       */     
/*       */ 
/* 13853 */     LoadSelections();
/*       */     
/* 13855 */     RecalcEngine();
/* 13856 */     FixWalkMPSpinner();
/* 13857 */     FixJJSpinnerModel();
/* 13858 */     RecalcGyro();
/* 13859 */     RecalcIntStruc();
/* 13860 */     RecalcCockpit();
/* 13861 */     this.CurMech.GetActuators().PlaceActuators();
/* 13862 */     RecalcHeatSinks();
/* 13863 */     RecalcJumpJets();
/* 13864 */     RecalcEnhancements();
/* 13865 */     RecalcArmor();
/* 13866 */     RecalcEquipment();
/*       */     
/*       */ 
/*       */ 
/* 13870 */     this.CurMech.GetLoadout().FlushIllegal();
/*       */     
/*       */ 
/*       */ 
/* 13874 */     RefreshSummary();
/* 13875 */     RefreshInfoPane();
/* 13876 */     SetWeaponChoosers();
/* 13877 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   private void chkOmnimechActionPerformed(ActionEvent evt) {
/* 13881 */     if (this.chkOmnimech.isSelected()) {
/* 13882 */       this.btnLockChassis.setEnabled(true);
/*       */     } else {
/* 13884 */       this.btnLockChassis.setEnabled(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private void cmbPhysEnhanceActionPerformed(ActionEvent evt) {
/* 13889 */     if (BuildLookupName(this.CurMech.GetPhysEnhance().GetCurrentState()).equals((String)this.cmbPhysEnhance.getSelectedItem())) {
/* 13890 */       return;
/*       */     }
/*       */     
/* 13893 */     RecalcEnhancements();
/*       */     
/*       */     try
/*       */     {
/* 13897 */       this.CurMech.GetLoadout().CheckExclusions(this.CurMech.GetPhysEnhance());
/*       */     } catch (Exception e) {
/* 13899 */       Media.Messager(this, e.getMessage());
/* 13900 */       this.cmbPhysEnhance.setSelectedItem("No Enhancement");
/* 13901 */       RecalcEnhancements();
/* 13902 */       return;
/*       */     }
/*       */     
/* 13905 */     this.lblRunMP.setText("" + this.CurMech.GetRunningMP());
/*       */     
/*       */ 
/* 13908 */     RefreshSummary();
/* 13909 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbCockpitTypeActionPerformed(ActionEvent evt) {
/* 13913 */     if (this.CurMech.GetCockpit().LookupName().equals((String)this.cmbCockpitType.getSelectedItem())) {
/* 13914 */       return;
/*       */     }
/* 13916 */     RecalcCockpit();
/*       */     
/*       */ 
/* 13919 */     RefreshEquipment();
/* 13920 */     RefreshSummary();
/* 13921 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbGyroTypeActionPerformed(ActionEvent evt) {
/* 13925 */     if (BuildLookupName(this.CurMech.GetGyro().GetCurrentState()).equals((String)this.cmbGyroType.getSelectedItem())) {
/* 13926 */       return;
/*       */     }
/* 13928 */     RecalcGyro();
/*       */     
/*       */ 
/* 13931 */     RefreshSummary();
/* 13932 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbInternalTypeActionPerformed(ActionEvent evt) {
/* 13936 */     if (BuildLookupName(this.CurMech.GetIntStruc().GetCurrentState()).equals((String)this.cmbInternalType.getSelectedItem())) {
/* 13937 */       return;
/*       */     }
/* 13939 */     RecalcIntStruc();
/*       */     
/*       */ 
/* 13942 */     RefreshSummary();
/* 13943 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbEngineTypeActionPerformed(ActionEvent evt) {
/* 13947 */     if (BuildLookupName(this.CurMech.GetEngine().GetCurrentState()).equals((String)this.cmbEngineType.getSelectedItem()))
/*       */     {
/* 13949 */       if (this.CurMech.GetEngine().IsNuclear()) {
/* 13950 */         if (this.cmbJumpJetType.getSelectedItem() == null) {
/* 13951 */           EnableJumpJets(false);
/*       */         } else {
/* 13953 */           EnableJumpJets(true);
/*       */         }
/*       */       } else {
/* 13956 */         EnableJumpJets(false);
/*       */       }
/* 13958 */       return;
/*       */     }
/* 13960 */     RecalcEngine();
/*       */     
/*       */ 
/* 13963 */     if (this.CurMech.GetEngine().IsNuclear()) {
/* 13964 */       if (this.cmbJumpJetType.getSelectedItem() == null) {
/* 13965 */         EnableJumpJets(false);
/*       */       } else {
/* 13967 */         EnableJumpJets(true);
/*       */       }
/*       */     } else {
/* 13970 */       EnableJumpJets(false);
/*       */     }
/*       */     
/*       */ 
/* 13974 */     if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/* 13975 */       this.Equipment[7] = { " " };
/*       */     } else {
/* 13977 */       this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13982 */     RefreshSummary();
/* 13983 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbMotiveTypeActionPerformed(ActionEvent evt) {
/* 13987 */     if (this.cmbMotiveType.getSelectedIndex() == 0)
/*       */     {
/* 13989 */       if (!this.CurMech.IsQuad()) { return;
/*       */       }
/*       */       
/* 13992 */       if (this.CurMech.GetCockpit().CritName().equals("Robotic Cockpit")) {
/* 13993 */         this.cmbCockpitType.setSelectedItem("Standard Cockpit");
/* 13994 */         RecalcCockpit();
/*       */       }
/* 13996 */       this.CurMech.SetBiped();
/*       */       
/*       */ 
/* 13999 */       this.cmbInternalType.setSelectedIndex(0);
/* 14000 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("LA");
/* 14001 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("RA");
/* 14002 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("LL");
/* 14003 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RL");
/* 14004 */       this.scrRACrits.setPreferredSize(new Dimension(105, 170));
/* 14005 */       this.scrLACrits.setPreferredSize(new Dimension(105, 170));
/*       */     }
/*       */     else {
/* 14008 */       if (this.CurMech.IsQuad()) return;
/* 14009 */       this.CurMech.SetQuad();
/*       */       
/*       */ 
/* 14012 */       this.cmbInternalType.setSelectedIndex(0);
/* 14013 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("FLL");
/* 14014 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("FRL");
/* 14015 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("RLL");
/* 14016 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RRL");
/* 14017 */       this.scrRACrits.setPreferredSize(new Dimension(105, 87));
/* 14018 */       this.scrLACrits.setPreferredSize(new Dimension(105, 87));
/*       */     }
/*       */     
/*       */ 
/* 14022 */     SetLoadoutArrays();
/*       */     
/*       */ 
/* 14025 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 14028 */     CheckAES();
/*       */     
/*       */ 
/* 14031 */     RefreshSummary();
/* 14032 */     RefreshInfoPane();
/* 14033 */     RefreshInternalPoints();
/* 14034 */     SetWeaponChoosers();
/*       */   }
/*       */   
/*       */ 
/*       */   private void cmbTonnageActionPerformed(ActionEvent evt)
/*       */   {
/* 14040 */     int Tons = 0;
/* 14041 */     switch (this.cmbTonnage.getSelectedIndex())
/*       */     {
/*       */     case 0: 
/* 14044 */       this.lblMechType.setText("Ultralight Mech");
/* 14045 */       Tons = 10;
/* 14046 */       break;
/*       */     
/*       */     case 1: 
/* 14049 */       this.lblMechType.setText("Ultralight Mech");
/* 14050 */       Tons = 15;
/* 14051 */       break;
/*       */     
/*       */     case 2: 
/* 14054 */       this.lblMechType.setText("Light Mech");
/* 14055 */       Tons = 20;
/* 14056 */       break;
/*       */     
/*       */     case 3: 
/* 14059 */       this.lblMechType.setText("Light Mech");
/* 14060 */       Tons = 25;
/* 14061 */       break;
/*       */     
/*       */     case 4: 
/* 14064 */       this.lblMechType.setText("Light Mech");
/* 14065 */       Tons = 30;
/* 14066 */       break;
/*       */     
/*       */     case 5: 
/* 14069 */       this.lblMechType.setText("Light Mech");
/* 14070 */       Tons = 35;
/* 14071 */       break;
/*       */     
/*       */     case 6: 
/* 14074 */       this.lblMechType.setText("Medium Mech");
/* 14075 */       Tons = 40;
/* 14076 */       break;
/*       */     
/*       */     case 7: 
/* 14079 */       this.lblMechType.setText("Medium Mech");
/* 14080 */       Tons = 45;
/* 14081 */       break;
/*       */     
/*       */     case 8: 
/* 14084 */       this.lblMechType.setText("Medium Mech");
/* 14085 */       Tons = 50;
/* 14086 */       break;
/*       */     
/*       */     case 9: 
/* 14089 */       this.lblMechType.setText("Medium Mech");
/* 14090 */       Tons = 55;
/* 14091 */       break;
/*       */     
/*       */     case 10: 
/* 14094 */       this.lblMechType.setText("Heavy Mech");
/* 14095 */       Tons = 60;
/* 14096 */       break;
/*       */     
/*       */     case 11: 
/* 14099 */       this.lblMechType.setText("Heavy Mech");
/* 14100 */       Tons = 65;
/* 14101 */       break;
/*       */     
/*       */     case 12: 
/* 14104 */       this.lblMechType.setText("Heavy Mech");
/* 14105 */       Tons = 70;
/* 14106 */       break;
/*       */     
/*       */     case 13: 
/* 14109 */       this.lblMechType.setText("Heavy Mech");
/* 14110 */       Tons = 75;
/* 14111 */       break;
/*       */     
/*       */     case 14: 
/* 14114 */       this.lblMechType.setText("Assault Mech");
/* 14115 */       Tons = 80;
/* 14116 */       break;
/*       */     
/*       */     case 15: 
/* 14119 */       this.lblMechType.setText("Assault Mech");
/* 14120 */       Tons = 85;
/* 14121 */       break;
/*       */     
/*       */     case 16: 
/* 14124 */       this.lblMechType.setText("Assault Mech");
/* 14125 */       Tons = 90;
/* 14126 */       break;
/*       */     
/*       */     case 17: 
/* 14129 */       this.lblMechType.setText("Assault Mech");
/* 14130 */       Tons = 95;
/* 14131 */       break;
/*       */     
/*       */     case 18: 
/* 14134 */       this.lblMechType.setText("Assault Mech");
/* 14135 */       Tons = 100;
/*       */     }
/*       */     
/*       */     
/* 14139 */     if (this.CurMech.GetTonnage() == Tons) {
/* 14140 */       return;
/*       */     }
/* 14142 */     this.CurMech.SetTonnage(Tons);
/*       */     
/*       */ 
/*       */ 
/* 14146 */     CheckTonnage(false);
/*       */     
/*       */ 
/* 14149 */     FixWalkMPSpinner();
/* 14150 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14153 */     this.CurMech.GetHeatSinks().ReCalculate();
/* 14154 */     this.CurMech.GetArmor().Recalculate();
/*       */     
/*       */ 
/* 14157 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 14160 */     this.CurMech.CheckPhysicals();
/*       */     
/*       */ 
/* 14163 */     CheckAES();
/*       */     
/*       */ 
/* 14166 */     RefreshInternalPoints();
/* 14167 */     RefreshSummary();
/* 14168 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbRulesLevelActionPerformed(ActionEvent evt) {
/* 14172 */     if (this.Load) return;
/* 14173 */     int NewLevel = this.cmbRulesLevel.getSelectedIndex();
/* 14174 */     int OldLevel = this.CurMech.GetLoadout().GetRulesLevel();
/* 14175 */     int OldType = this.cmbMechType.getSelectedIndex();
/* 14176 */     int OldTech = this.CurMech.GetTechbase();
/*       */     
/* 14178 */     if (OldLevel == NewLevel)
/*       */     {
/* 14180 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14184 */     if (this.CurMech.IsOmnimech())
/*       */     {
/* 14186 */       if (this.CurMech.GetLoadout().SetRulesLevel(NewLevel))
/*       */       {
/* 14188 */         if (OldLevel > NewLevel)
/*       */         {
/* 14190 */           this.CurMech.GetLoadout().FlushIllegal();
/*       */         }
/* 14192 */         BuildTechBaseSelector();
/* 14193 */         this.cmbTechBase.setSelectedIndex(this.CurMech.GetLoadout().GetTechBase());
/* 14194 */         BuildJumpJetSelector();
/* 14195 */         RefreshEquipment();
/* 14196 */         RecalcEquipment();
/*       */       }
/*       */       else {
/* 14199 */         Media.Messager(this, "You cannot set an OmniMech's loadout to a Rules Level\nlower than it's chassis' Rules Level.");
/* 14200 */         this.cmbRulesLevel.setSelectedIndex(this.CurMech.GetLoadout().GetRulesLevel());
/*       */       }
/*       */     }
/*       */     else {
/* 14204 */       this.CurMech.SetRulesLevel(NewLevel);
/* 14205 */       BuildMechTypeSelector();
/* 14206 */       CheckTonnage(true);
/*       */       
/*       */ 
/* 14209 */       SaveSelections();
/* 14210 */       BuildTechBaseSelector();
/* 14211 */       if (OldTech >= this.cmbTechBase.getItemCount())
/*       */       {
/* 14213 */         switch (OldTech)
/*       */         {
/*       */         case 0: 
/* 14216 */           System.err.println("Fatal Error when reseting techbase, Inner Sphere not available.");
/* 14217 */           break;
/*       */         
/*       */         default: 
/* 14220 */           this.cmbTechBase.setSelectedIndex(0);
/* 14221 */           cmbTechBaseActionPerformed(null);
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/* 14229 */       this.CurMech.GetLoadout().FlushIllegal();
/*       */       
/*       */ 
/* 14232 */       BuildChassisSelector();
/* 14233 */       BuildEngineSelector();
/* 14234 */       BuildGyroSelector();
/* 14235 */       BuildCockpitSelector();
/* 14236 */       BuildEnhancementSelector();
/* 14237 */       BuildHeatsinkSelector();
/* 14238 */       BuildJumpJetSelector();
/* 14239 */       BuildArmorSelector();
/* 14240 */       FixWalkMPSpinner();
/* 14241 */       FixJJSpinnerModel();
/* 14242 */       RefreshEquipment();
/*       */       
/*       */ 
/* 14245 */       LoadSelections();
/*       */       
/* 14247 */       RecalcEngine();
/* 14248 */       RecalcGyro();
/* 14249 */       RecalcIntStruc();
/* 14250 */       RecalcCockpit();
/* 14251 */       this.CurMech.GetActuators().PlaceActuators();
/* 14252 */       RecalcHeatSinks();
/* 14253 */       RecalcJumpJets();
/* 14254 */       RecalcEnhancements();
/* 14255 */       RecalcArmor();
/* 14256 */       RecalcEquipment();
/*       */     }
/*       */     
/*       */ 
/* 14260 */     RefreshSummary();
/* 14261 */     RefreshInfoPane();
/* 14262 */     SetWeaponChoosers();
/* 14263 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   private void cmbTechBaseActionPerformed(ActionEvent evt) {
/* 14267 */     if (this.Load) { return;
/*       */     }
/* 14269 */     if (this.CurMech.IsOmnimech()) {
/* 14270 */       if (this.CurMech.GetLoadout().GetTechBase() != this.cmbTechBase.getSelectedIndex()) {}
/*       */     }
/* 14272 */     else if (this.CurMech.GetTechbase() == this.cmbTechBase.getSelectedIndex()) { return;
/*       */     }
/*       */     
/* 14275 */     if (this.CurMech.IsOmnimech()) {
/* 14276 */       boolean check = this.CurMech.SetTechBase(this.cmbTechBase.getSelectedIndex());
/* 14277 */       if (!check) {
/* 14278 */         Media.Messager(this, "An OmniMech can only use the base chassis' Tech Base\nor Mixed Tech.  Resetting.");
/* 14279 */         this.cmbTechBase.setSelectedIndex(this.CurMech.GetLoadout().GetTechBase());
/* 14280 */         return;
/*       */       }
/* 14282 */       RefreshEquipment();
/*       */     }
/*       */     else {
/* 14285 */       switch (this.cmbTechBase.getSelectedIndex()) {
/*       */       case 0: 
/* 14287 */         this.CurMech.SetInnerSphere();
/* 14288 */         break;
/*       */       case 1: 
/* 14290 */         this.CurMech.SetClan();
/* 14291 */         break;
/*       */       case 2: 
/* 14293 */         this.CurMech.SetMixed();
/*       */       }
/*       */       
/*       */       
/*       */ 
/*       */ 
/* 14299 */       SaveSelections();
/*       */       
/* 14301 */       this.data.Rebuild(this.CurMech);
/*       */       
/*       */ 
/* 14304 */       BuildChassisSelector();
/* 14305 */       BuildEngineSelector();
/* 14306 */       BuildGyroSelector();
/* 14307 */       BuildCockpitSelector();
/* 14308 */       BuildEnhancementSelector();
/* 14309 */       BuildHeatsinkSelector();
/* 14310 */       BuildJumpJetSelector();
/* 14311 */       BuildArmorSelector();
/* 14312 */       RefreshEquipment();
/* 14313 */       FixWalkMPSpinner();
/* 14314 */       FixJJSpinnerModel();
/* 14315 */       CheckOmnimech();
/*       */       
/*       */ 
/* 14318 */       if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 14319 */         this.CurMech.GetLoadout().SetClanCASE(true);
/*       */       }
/*       */       
/*       */ 
/* 14323 */       LoadSelections();
/*       */       
/*       */ 
/* 14326 */       RecalcEngine();
/* 14327 */       RecalcGyro();
/* 14328 */       RecalcIntStruc();
/* 14329 */       RecalcCockpit();
/* 14330 */       this.CurMech.GetActuators().PlaceActuators();
/* 14331 */       RecalcHeatSinks();
/* 14332 */       RecalcJumpJets();
/* 14333 */       RecalcEnhancements();
/* 14334 */       RecalcArmor();
/*       */     }
/*       */     
/* 14337 */     RecalcEquipment();
/* 14338 */     SetWeaponChoosers();
/* 14339 */     this.chkUseTC.setSelected(false);
/*       */     
/*       */ 
/* 14342 */     RefreshSummary();
/* 14343 */     RefreshInfoPane();
/* 14344 */     SetWeaponChoosers();
/*       */   }
/*       */   
/*       */   private void chkYearRestrictActionPerformed(ActionEvent evt)
/*       */   {
/* 14349 */     int year = 0;
/* 14350 */     if (this.CurMech.IsYearRestricted() == this.chkYearRestrict.isSelected()) { return;
/*       */     }
/*       */     
/* 14353 */     if (!this.chkYearRestrict.isSelected()) {
/* 14354 */       this.cmbMechEra.setEnabled(true);
/* 14355 */       this.cmbProductionEra.setEnabled(true);
/* 14356 */       this.cmbTechBase.setEnabled(true);
/* 14357 */       this.txtProdYear.setEnabled(true);
/* 14358 */       this.CurMech.SetYearRestricted(false);
/* 14359 */       switch (this.cmbMechEra.getSelectedIndex()) {
/*       */       case 0: 
/* 14361 */         this.CurMech.SetYear(2750, false);
/* 14362 */         break;
/*       */       case 1: 
/* 14364 */         this.CurMech.SetYear(3025, false);
/* 14365 */         break;
/*       */       case 2: 
/* 14367 */         this.CurMech.SetYear(3070, false);
/* 14368 */         break;
/*       */       case 3: 
/* 14370 */         this.CurMech.SetYear(3132, false);
/* 14371 */         break;
/*       */       case 4: 
/* 14373 */         this.CurMech.SetYear(0, false);
/*       */       }
/*       */     }
/*       */     else
/*       */     {
/*       */       try {
/* 14379 */         year = Integer.parseInt(this.txtProdYear.getText());
/*       */       } catch (NumberFormatException n) {
/* 14381 */         Media.Messager(this, "The production year is not a number.");
/* 14382 */         this.txtProdYear.setText("");
/* 14383 */         this.chkYearRestrict.setSelected(false);
/* 14384 */         return;
/*       */       }
/*       */       
/*       */ 
/* 14388 */       switch (this.cmbMechEra.getSelectedIndex())
/*       */       {
/*       */       case 0: 
/* 14391 */         if ((year < 2443) || (year > 2800)) {
/* 14392 */           Media.Messager(this, "The year does not fall within this era.");
/* 14393 */           this.txtProdYear.setText("");
/* 14394 */           this.chkYearRestrict.setSelected(false); return;
/*       */         }
/*       */         
/*       */ 
/*       */         break;
/*       */       case 1: 
/* 14400 */         if ((year < 2801) || (year > 3050)) {
/* 14401 */           Media.Messager(this, "The year does not fall within this era.");
/* 14402 */           this.txtProdYear.setText("");
/* 14403 */           this.chkYearRestrict.setSelected(false); return;
/*       */         }
/*       */         
/*       */ 
/*       */         break;
/*       */       case 2: 
/* 14409 */         if ((year < 3051) || (year > 3085)) {
/* 14410 */           Media.Messager(this, "The year does not fall within this era.");
/* 14411 */           this.txtProdYear.setText("");
/* 14412 */           this.chkYearRestrict.setSelected(false); return;
/*       */         }
/*       */         
/*       */ 
/*       */         break;
/*       */       case 3: 
/* 14418 */         if (year < 3085) {
/* 14419 */           Media.Messager(this, "The year does not fall within this era.");
/* 14420 */           this.txtProdYear.setText("");
/* 14421 */           this.chkYearRestrict.setSelected(false); return;
/*       */         }
/*       */         
/*       */ 
/*       */         break;
/*       */       case 4: 
/* 14427 */         this.chkYearRestrict.setSelected(false);
/* 14428 */         this.chkYearRestrict.setEnabled(false);
/*       */       }
/*       */       
/*       */       
/* 14432 */       this.cmbMechEra.setEnabled(false);
/* 14433 */       this.cmbTechBase.setEnabled(false);
/* 14434 */       this.txtProdYear.setEnabled(false);
/* 14435 */       this.CurMech.SetYear(year, true);
/* 14436 */       this.CurMech.SetYearRestricted(true);
/*       */     }
/*       */     
/*       */ 
/* 14440 */     SaveSelections();
/*       */     
/*       */ 
/* 14443 */     BuildChassisSelector();
/* 14444 */     BuildEngineSelector();
/* 14445 */     BuildGyroSelector();
/* 14446 */     BuildCockpitSelector();
/* 14447 */     BuildEnhancementSelector();
/* 14448 */     BuildHeatsinkSelector();
/* 14449 */     BuildJumpJetSelector();
/* 14450 */     BuildArmorSelector();
/* 14451 */     RefreshEquipment();
/* 14452 */     CheckOmnimech();
/*       */     
/*       */ 
/* 14455 */     LoadSelections();
/*       */     
/*       */ 
/* 14458 */     RecalcEngine();
/* 14459 */     RecalcGyro();
/* 14460 */     RecalcIntStruc();
/* 14461 */     RecalcCockpit();
/* 14462 */     this.CurMech.GetActuators().PlaceActuators();
/* 14463 */     RecalcHeatSinks();
/* 14464 */     RecalcJumpJets();
/* 14465 */     RecalcEnhancements();
/* 14466 */     RecalcArmor();
/* 14467 */     RecalcEquipment();
/*       */     
/* 14469 */     this.CurMech.GetLoadout().FlushIllegal();
/*       */     
/*       */ 
/* 14472 */     RefreshSummary();
/* 14473 */     RefreshInfoPane();
/* 14474 */     SetWeaponChoosers();
/* 14475 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   private void cmbMechEraActionPerformed(ActionEvent evt) {
/* 14479 */     if (this.Load) { return;
/*       */     }
/*       */     
/* 14482 */     if (this.CurMech.GetEra() == this.cmbMechEra.getSelectedIndex()) {
/* 14483 */       return;
/*       */     }
/* 14485 */     if ((this.CurMech.IsOmnimech()) && 
/* 14486 */       (this.cmbMechEra.getSelectedIndex() < this.CurMech.GetBaseEra())) {
/* 14487 */       Media.Messager(this, "An OmniMech loadout cannot have an era lower than the main loadout.");
/* 14488 */       this.cmbMechEra.setSelectedIndex(this.CurMech.GetBaseEra());
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/* 14494 */     int tbsave = this.cmbTechBase.getSelectedIndex();
/*       */     
/*       */ 
/* 14497 */     switch (this.cmbMechEra.getSelectedIndex()) {
/*       */     case 0: 
/* 14499 */       this.lblEraYears.setText("2443 ~ 2800");
/* 14500 */       this.txtProdYear.setText("");
/* 14501 */       this.CurMech.SetEra(0);
/* 14502 */       this.CurMech.SetYear(2750, false);
/* 14503 */       if (!this.CurMech.IsOmnimech()) this.chkYearRestrict.setEnabled(true);
/*       */       break;
/*       */     case 1: 
/* 14506 */       this.lblEraYears.setText("2801 ~ 3050");
/* 14507 */       this.txtProdYear.setText("");
/* 14508 */       this.CurMech.SetEra(1);
/* 14509 */       this.CurMech.SetYear(3025, false);
/* 14510 */       if (!this.CurMech.IsOmnimech()) this.chkYearRestrict.setEnabled(true);
/*       */       break;
/*       */     case 2: 
/* 14513 */       this.lblEraYears.setText("3051 ~ 3085");
/* 14514 */       this.txtProdYear.setText("");
/* 14515 */       this.CurMech.SetEra(2);
/* 14516 */       this.CurMech.SetYear(3075, false);
/* 14517 */       if (!this.CurMech.IsOmnimech()) this.chkYearRestrict.setEnabled(true);
/*       */       break;
/*       */     case 3: 
/* 14520 */       this.lblEraYears.setText("3086 on");
/* 14521 */       this.txtProdYear.setText("");
/* 14522 */       this.CurMech.SetEra(3);
/* 14523 */       this.CurMech.SetYear(3132, false);
/* 14524 */       if (!this.CurMech.IsOmnimech()) this.chkYearRestrict.setEnabled(true);
/*       */       break;
/*       */     case 4: 
/* 14527 */       this.lblEraYears.setText("Any");
/* 14528 */       this.txtProdYear.setText("");
/* 14529 */       this.CurMech.SetEra(4);
/* 14530 */       this.CurMech.SetYear(0, false);
/* 14531 */       this.chkYearRestrict.setEnabled(false);
/*       */     }
/*       */     
/*       */     
/* 14535 */     if (this.CurMech.IsOmnimech()) {
/* 14536 */       BuildJumpJetSelector();
/* 14537 */       RefreshEquipment();
/* 14538 */       RefreshSummary();
/* 14539 */       RefreshInfoPane();
/* 14540 */       SetWeaponChoosers();
/* 14541 */       ResetAmmo();
/* 14542 */       return;
/*       */     }
/*       */     
/* 14545 */     BuildTechBaseSelector();
/* 14546 */     BuildMechTypeSelector();
/*       */     
/*       */ 
/* 14549 */     if (tbsave < this.cmbTechBase.getItemCount())
/*       */     {
/* 14551 */       this.cmbTechBase.setSelectedIndex(tbsave);
/*       */     }
/*       */     else
/*       */     {
/* 14555 */       this.cmbTechBase.setSelectedIndex(0);
/* 14556 */       this.CurMech.SetInnerSphere();
/*       */     }
/*       */     
/*       */ 
/* 14560 */     SaveSelections();
/*       */     
/*       */ 
/* 14563 */     BuildChassisSelector();
/* 14564 */     BuildEngineSelector();
/* 14565 */     BuildGyroSelector();
/* 14566 */     BuildCockpitSelector();
/* 14567 */     BuildEnhancementSelector();
/* 14568 */     BuildHeatsinkSelector();
/* 14569 */     BuildJumpJetSelector();
/* 14570 */     BuildArmorSelector();
/* 14571 */     FixWalkMPSpinner();
/* 14572 */     FixJJSpinnerModel();
/* 14573 */     RefreshEquipment();
/* 14574 */     CheckOmnimech();
/*       */     
/*       */ 
/* 14577 */     LoadSelections();
/*       */     
/*       */ 
/* 14580 */     RecalcEngine();
/* 14581 */     RecalcGyro();
/* 14582 */     RecalcIntStruc();
/* 14583 */     RecalcCockpit();
/* 14584 */     this.CurMech.GetActuators().PlaceActuators();
/* 14585 */     RecalcHeatSinks();
/* 14586 */     RecalcJumpJets();
/* 14587 */     RecalcEnhancements();
/* 14588 */     RecalcArmor();
/* 14589 */     RecalcEquipment();
/*       */     
/*       */ 
/*       */ 
/* 14593 */     this.CurMech.GetLoadout().FlushIllegal();
/*       */     
/*       */ 
/*       */ 
/* 14597 */     RefreshSummary();
/* 14598 */     RefreshInfoPane();
/* 14599 */     SetWeaponChoosers();
/* 14600 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   private void lstCritsToPlaceKeyPressed(java.awt.event.KeyEvent evt) {
/* 14604 */     if (evt.getKeyCode() == 127) {
/* 14605 */       btnRemoveEquipActionPerformed(new ActionEvent(evt.getSource(), evt.getID(), null));
/*       */     }
/*       */   }
/*       */   
/*       */   private void chkBoobyTrapActionPerformed(ActionEvent evt) {
/* 14610 */     if (this.chkBoobyTrap.isSelected() == this.CurMech.GetLoadout().HasBoobyTrap()) return;
/*       */     try {
/* 14612 */       this.CurMech.GetLoadout().SetBoobyTrap(this.chkBoobyTrap.isSelected());
/*       */     } catch (Exception e) {
/* 14614 */       Media.Messager(this, e.getMessage());
/* 14615 */       this.chkBoobyTrap.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/* 14619 */     RefreshEquipment();
/* 14620 */     RefreshSummary();
/* 14621 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbProductionEraActionPerformed(ActionEvent evt) {
/* 14625 */     this.CurMech.SetProductionEra(this.cmbProductionEra.getSelectedIndex());
/*       */   }
/*       */   
/*       */   private void setViewToolbar(boolean Visible)
/*       */   {
/* 14630 */     this.tlbIconBar.setVisible(Visible);
/* 14631 */     this.Prefs.putBoolean("ViewToolbar", Visible);
/* 14632 */     this.mnuViewToolbar.setState(Visible);
/* 14633 */     if (Visible) {
/* 14634 */       if (getHeight() != 600) setSize(750, 600);
/*       */     }
/* 14636 */     else if (getHeight() != 575) { setSize(750, 575);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   private JCheckBox chkFractional;
/*       */   
/*       */   private JCheckBox chkHDCASE2;
/*       */   
/*       */   private JCheckBox chkHDTurret;
/*       */   
/*       */   private JCheckBox chkIndividualWeapons;
/*       */   
/*       */   private JCheckBox chkLAAES;
/*       */   
/*       */   private JCheckBox chkLACASE2;
/*       */   
/*       */   private JCheckBox chkLAHand;
/*       */   
/*       */   private JCheckBox chkLALowerArm;
/*       */   
/*       */   private JCheckBox chkLLCASE2;
/*       */   
/*       */   private JCheckBox chkLTCASE;
/*       */   
/*       */   private JCheckBox chkLTCASE2;
/*       */   
/*       */   private JCheckBox chkLTTurret;
/*       */   
/*       */   private JCheckBox chkLegAES;
/*       */   
/*       */   private JCheckBox chkNullSig;
/*       */   
/*       */   private JCheckBox chkOmnimech;
/*       */   
/*       */   private JCheckBox chkPartialWing;
/*       */   
/*       */   private JCheckBox chkRAAES;
/*       */   
/*       */   private JCheckBox chkRACASE2;
/*       */   
/*       */   private JCheckBox chkRAHand;
/*       */   
/*       */   private JCheckBox chkRALowerArm;
/*       */   
/*       */   private JCheckBox chkRLCASE2;
/*       */   
/*       */   private JCheckBox chkRTCASE;
/*       */   
/*       */   private JCheckBox chkRTCASE2;
/*       */   
/*       */   private JCheckBox chkRTTurret;
/*       */   
/*       */   private JCheckBox chkShowTextNotGraph;
/*       */   
/*       */   private JCheckBox chkSupercharger;
/*       */   
/*       */   private JCheckBox chkTracks;
/*       */   
/*       */   private JCheckBox chkUseTC;
/*       */   
/*       */   private JCheckBox chkVoidSig;
/*       */   
/*       */   private JCheckBox chkYearRestrict;
/*       */   
/*       */   private JComboBox cmbArmorType;
/*       */   
/*       */   private JComboBox cmbCockpitType;
/*       */   
/*       */   private JComboBox cmbEngineType;
/*       */   
/*       */   private JComboBox cmbGyroType;
/*       */   
/*       */   private JComboBox cmbHeatSinkType;
/*       */   
/*       */   private JComboBox cmbInternalType;
/*       */   
/*       */   private JComboBox cmbJumpJetType;
/*       */   
/*       */   private JComboBox cmbMechEra;
/*       */   
/*       */   private JComboBox cmbMechType;
/*       */   
/*       */   private JComboBox cmbMotiveType;
/*       */   
/*       */   private JComboBox cmbNumEquips;
/*       */   
/*       */   private JComboBox cmbOmniVariant;
/*       */   
/*       */   private JComboBox cmbPWCTType;
/*       */   
/*       */   private JComboBox cmbPWHDType;
/*       */   
/*       */   private JComboBox cmbPWLAType;
/*       */   
/*       */   private JComboBox cmbPWLLType;
/*       */   
/*       */   private JComboBox cmbPWLTType;
/*       */   
/*       */   private JComboBox cmbPWRAType;
/*       */   
/*       */   private JComboBox cmbPWRLType;
/*       */   
/*       */   private JComboBox cmbPWRTType;
/*       */   
/*       */   private JComboBox cmbPhysEnhance;
/*       */   
/*       */   private JComboBox cmbProductionEra;
/*       */   
/*       */   private JComboBox cmbRulesLevel;
/*       */   
/*       */   private JComboBox cmbSCLoc;
/*       */   
/*       */   private JComboBox cmbTechBase;
/*       */   
/*       */   private JComboBox cmbTonnage;
/*       */   
/*       */   private JLabel jLabel1;
/*       */   
/*       */   private JLabel jLabel10;
/*       */   
/*       */   private JLabel jLabel11;
/*       */   
/*       */   private JLabel jLabel12;
/*       */   
/*       */   private JLabel jLabel13;
/*       */   private JLabel jLabel14;
/*       */   private JLabel jLabel15;
/*       */   private JLabel jLabel16;
/*       */   private JLabel jLabel17;
/*       */   private JLabel jLabel18;
/*       */   private JLabel jLabel19;
/*       */   private JLabel jLabel20;
/*       */   private JLabel jLabel21;
/*       */   private JLabel jLabel22;
/*       */   private JLabel jLabel23;
/*       */   private JLabel jLabel24;
/*       */   private JLabel jLabel25;
/*       */   private JLabel jLabel26;
/*       */   private JLabel jLabel27;
/*       */   private JLabel jLabel28;
/*       */   private JLabel jLabel29;
/*       */   private JLabel jLabel30;
/*       */   private JLabel jLabel31;
/*       */   private JLabel jLabel32;
/*       */   private JLabel jLabel33;
/*       */   private JLabel jLabel34;
/*       */   private JLabel jLabel35;
/*       */   private JLabel jLabel36;
/*       */   private JLabel jLabel37;
/*       */   private JLabel jLabel39;
/*       */   private JLabel jLabel41;
/*       */   private JLabel jLabel43;
/*       */   private JLabel jLabel53;
/*       */   private JLabel jLabel54;
/*       */   private JLabel jLabel55;
/*       */   private JLabel jLabel56;
/*       */   private JLabel jLabel57;
/*       */   private JLabel jLabel58;
/*       */   private JLabel jLabel59;
/*       */   private JLabel jLabel60;
/*       */   private JLabel jLabel61;
/*       */   private JLabel jLabel62;
/*       */   private JLabel jLabel64;
/*       */   private JLabel jLabel65;
/*       */   private JLabel jLabel66;
/*       */   private JLabel jLabel67;
/*       */   private JLabel jLabel68;
/*       */   private JLabel jLabel69;
/*       */   private JLabel jLabel70;
/*       */   private JLabel jLabel71;
/*       */   private JLabel jLabel72;
/*       */   private JLabel jLabel73;
/*       */   private JLabel jLabel74;
/*       */   private JLabel jLabel75;
/*       */   private JLabel jLabel8;
/*       */   private JLabel jLabel9;
/*       */   private JMenuItem jMenuItem1;
/*       */   private JPanel jPanel1;
/*       */   private JPanel jPanel2;
/*       */   private JPanel jPanel3;
/*       */   private JPanel jPanel4;
/*       */   private JPanel jPanel5;
/*       */   private JPanel jPanel6;
/*       */   private JPanel jPanel7;
/*       */   private JScrollPane jScrollPane10;
/*       */   private JScrollPane jScrollPane11;
/*       */   private JScrollPane jScrollPane12;
/*       */   private JScrollPane jScrollPane13;
/*       */   private JScrollPane jScrollPane14;
/*       */   private JScrollPane jScrollPane16;
/*       */   private JScrollPane jScrollPane17;
/*       */   private JScrollPane jScrollPane18;
/*       */   private JScrollPane jScrollPane19;
/*       */   private JScrollPane jScrollPane20;
/*       */   private JScrollPane jScrollPane21;
/*       */   private JScrollPane jScrollPane22;
/*       */   private JScrollPane jScrollPane24;
/*       */   private JScrollPane jScrollPane8;
/*       */   private JScrollPane jScrollPane9;
/*       */   private JSeparator jSeparator1;
/*       */   private JSeparator jSeparator10;
/*       */   private JSeparator jSeparator11;
/*       */   private JSeparator jSeparator12;
/*       */   private JSeparator jSeparator13;
/*       */   private JSeparator jSeparator14;
/*       */   private JSeparator jSeparator15;
/*       */   private JSeparator jSeparator16;
/*       */   private JSeparator jSeparator17;
/*       */   private JSeparator jSeparator18;
/*       */   private JSeparator jSeparator19;
/*       */   private JSeparator jSeparator2;
/*       */   private JSeparator jSeparator20;
/*       */   private javax.swing.JToolBar.Separator jSeparator21;
/*       */   private javax.swing.JToolBar.Separator jSeparator22;
/*       */   private javax.swing.JToolBar.Separator jSeparator23;
/*       */   private javax.swing.JToolBar.Separator jSeparator24;
/*       */   private javax.swing.JToolBar.Separator jSeparator25;
/*       */   private javax.swing.JToolBar.Separator jSeparator26;
/*       */   private JSeparator jSeparator27;
/*       */   private JSeparator jSeparator28;
/*       */   private JSeparator jSeparator29;
/*       */   private JSeparator jSeparator3;
/*       */   private JSeparator jSeparator30;
/*       */   private JSeparator jSeparator4;
/*       */   private JSeparator jSeparator5;
/*       */   private JSeparator jSeparator6;
/*       */   private JSeparator jSeparator7;
/*       */   private JSeparator jSeparator8;
/*       */   private JSeparator jSeparator9;
/*       */   private javax.swing.JTextArea jTextAreaBFConversion;
/*       */   private JLabel lblAVInLot;
/*       */   private JLabel lblArmorCoverage;
/*       */   private JLabel lblArmorPoints;
/*       */   private JLabel lblArmorTonsWasted;
/*       */   private JLabel lblArmorType;
/*       */   private JLabel lblBFArmor;
/*       */   private JLabel lblBFExtreme;
/*       */   private JLabel lblBFLong;
/*       */   private JLabel lblBFMV;
/*       */   private JLabel lblBFMedium;
/*       */   private JLabel lblBFOV;
/*       */   private JLabel lblBFPoints;
/*       */   private JLabel lblBFSA;
/*       */   private JLabel lblBFShort;
/*       */   private JLabel lblBFStructure;
/*       */   private JLabel lblBFWt;
/*       */   private JLabel lblCTArmorHeader;
/*       */   private JLabel lblCTHeader;
/*       */   private JLabel lblCTIntPts;
/*       */   private JLabel lblCTRArmorHeader;
/*       */   private JLabel lblCockpit;
/*       */   private JLabel lblDamagePerTon;
/*       */   private JLabel lblEngineType;
/*       */   private JLabel lblEraYears;
/*       */   private JLabel lblFluffImage;
/*       */   private JLabel lblGyroType;
/*       */   private JLabel lblHDArmorHeader;
/*       */   private JLabel lblHDHeader;
/*       */   private JLabel lblHDIntPts;
/*       */   private JLabel lblHSNumber;
/*       */   private JLabel lblHeatSinkType;
/*       */   private JLabel lblInfoAVCI;
/*       */   private JLabel lblInfoAVSL;
/*       */   private JLabel lblInfoAVSW;
/*       */   private JLabel lblInfoAmmo;
/*       */   private JLabel lblInfoBV;
/*       */   private JLabel lblInfoCost;
/*       */   private JLabel lblInfoCrits;
/*       */   private JLabel lblInfoDamage;
/*       */   private JLabel lblInfoExtinct;
/*       */   private JLabel lblInfoHeat;
/*       */   private JLabel lblInfoIntro;
/*       */   private JLabel lblInfoMountRestrict;
/*       */   private JLabel lblInfoName;
/*       */   private JLabel lblInfoRange;
/*       */   private JLabel lblInfoReintro;
/*       */   private JLabel lblInfoRulesLevel;
/*       */   private JLabel lblInfoSpecials;
/*       */   private JLabel lblInfoTonnage;
/*       */   private JLabel lblInfoType;
/*       */   private JLabel lblInternalType;
/*       */   private JLabel lblJumpMP;
/*       */   private JLabel lblLAArmorHeader;
/*       */   private JLabel lblLAHeader;
/*       */   private JLabel lblLAIntPts;
/*       */   private JLabel lblLLArmorHeader;
/*       */   private JLabel lblLLHeader;
/*       */   private JLabel lblLLIntPts;
/*       */   private JLabel lblLTArmorHeader;
/*       */   private JLabel lblLTHeader;
/*       */   private JLabel lblLTIntPts;
/*       */   private JLabel lblLTRArmorHeader;
/*       */   private JLabel lblLegendTitle;
/*       */   private JLabel lblMechEra;
/*       */   private JLabel lblMechName;
/*       */   private JLabel lblMechType;
/*       */   private JLabel lblModel;
/*       */   private JLabel lblMotiveType;
/*       */   private JLabel lblMoveSummary;
/*       */   private JLabel lblPWCTLoc;
/*       */   private JLabel lblPWHDLoc;
/*       */   private JLabel lblPWLALoc;
/*       */   private JLabel lblPWLLLoc;
/*       */   private JLabel lblPWLTLoc;
/*       */   private JLabel lblPWRALoc;
/*       */   private JLabel lblPWRLLoc;
/*       */   private JLabel lblPWRTLoc;
/*       */   private JLabel lblPhysEnhance;
/*       */   private JLabel lblProdYear;
/*       */   private JLabel lblRAArmorHeader;
/*       */   private JLabel lblRAHeader;
/*       */   private JLabel lblRAIntPts;
/*       */   private JLabel lblRLArmorHeader;
/*       */   private JLabel lblRLHeader;
/*       */   private JLabel lblRLIntPts;
/*       */   private JLabel lblRTArmorHeader;
/*       */   private JLabel lblRTHeader;
/*       */   private JLabel lblRTIntPts;
/*       */   private JLabel lblRTRArmorHeader;
/*       */   private JLabel lblRulesLevel;
/*       */   private JLabel lblRunMP;
/*       */   private JLabel lblRunMPLabel;
/*       */   private JLabel lblSelectVariant;
/*       */   private JLabel lblSumCockpit;
/*       */   private JLabel lblSumEngine;
/*       */   private JLabel lblSumEnhance;
/*       */   private JLabel lblSumGyro;
/*       */   private JLabel lblSumHeadAvailable;
/*       */   private JLabel lblSumHeadCrits;
/*       */   private JLabel lblSumHeadCrits1;
/*       */   private JLabel lblSumHeadItem;
/*       */   private JLabel lblSumHeadTons;
/*       */   private JLabel lblSumHeadTons1;
/*       */   private JLabel lblSumHeatSinks;
/*       */   private JLabel lblSumJJ;
/*       */   private JLabel lblSumPAmps;
/*       */   private JLabel lblSumStructure;
/*       */   private JLabel lblSupercharger;
/*       */   private JLabel lblTechBase;
/*       */   private JLabel lblTonPercArmor;
/*       */   private JLabel lblTonPercEngine;
/*       */   private JLabel lblTonPercEnhance;
/*       */   private JLabel lblTonPercEquips;
/*       */   private JLabel lblTonPercHeatSinks;
/*       */   private JLabel lblTonPercJumpJets;
/*       */   private JLabel lblTonPercStructure;
/*       */   private JLabel lblTonPercWeapons;
/*       */   private JLabel lblTonnage;
/*       */   private JLabel lblUnitType;
/*       */   private JLabel lblWalkMP;
/*       */   private JList lstCTCrits;
/*       */   private JList lstChooseAmmunition;
/*       */   private JList lstChooseArtillery;
/*       */   private JList lstChooseBallistic;
/*       */   private JList lstChooseEnergy;
/*       */   private JList lstChooseEquipment;
/*       */   private JList lstChooseMissile;
/*       */   private JList lstChoosePhysical;
/*       */   private JList lstCritsToPlace;
/*       */   private JList lstHDCrits;
/*       */   private JList lstLACrits;
/*       */   private JList lstLLCrits;
/*       */   private JList lstLTCrits;
/*       */   private JList lstRACrits;
/*       */   private JList lstRLCrits;
/*       */   private JList lstRTCrits;
/*       */   private JMenuItem mnuAboutSSW;
/*       */   private JMenuItem mnuBFB;
/*       */   private JMenuItem mnuBatchHMP;
/*       */   private JMenu mnuClearFluff;
/*       */   private JMenuItem mnuClearUserData;
/*       */   private JMenuItem mnuCostBVBreakdown;
/*       */   private JMenuItem mnuCreateTCGMech;
/*       */   private JMenuItem mnuCredits;
/*       */   private JMenuItem mnuExit;
/*       */   private JMenu mnuExport;
/*       */   private JMenuItem mnuExportClipboard;
/*       */   private JMenuItem mnuExportHTML;
/*       */   private JMenuItem mnuExportMTF;
/*       */   private JMenuItem mnuExportTXT;
/*       */   private JMenu mnuFile;
/*       */   private JMenu mnuHelp;
/*       */   private JMenu mnuImport;
/*       */   private JMenuItem mnuImportHMP;
/*       */   private JMenuItem mnuLoad;
/*       */   private javax.swing.JMenuBar mnuMainMenu;
/*       */   private JMenuItem mnuNewMech;
/*       */   private JMenuItem mnuOpen;
/*       */   private JMenuItem mnuOptions;
/*       */   private JMenuItem mnuPostS7;
/*       */   private JMenu mnuPrint;
/*       */   private JMenuItem mnuPrintBatch;
/*       */   private JMenuItem mnuPrintCurrentMech;
/*       */   private JMenuItem mnuPrintPreview;
/*       */   private JMenuItem mnuPrintSavedMech;
/*       */   private JMenuItem mnuSave;
/*       */   private JMenuItem mnuSaveAs;
/*       */   private JMenuItem mnuSummary;
/*       */   private JMenuItem mnuTextTRO;
/*       */   private JMenuItem mnuUnlock;
/*       */   private javax.swing.JCheckBoxMenuItem mnuViewToolbar;
/*       */   private JPanel pnlAdditionalFluff;
/*       */   private JPanel pnlAmmunition;
/*       */   private JPanel pnlArmor;
/*       */   private JPanel pnlArmorInfo;
/*       */   private JPanel pnlArmorSetup;
/*       */   private JPanel pnlArtillery;
/*       */   private JPanel pnlBFStats;
/*       */   private JPanel pnlBallistic;
/*       */   private JPanel pnlBasicInformation;
/*       */   private JPanel pnlBasicSetup;
/*       */   private JPanel pnlBasicSummary;
/*       */   private JPanel pnlCTArmorBox;
/*       */   private JPanel pnlCTCrits;
/*       */   private JPanel pnlCTRArmorBox;
/*       */   private JPanel pnlCapabilities;
/*       */   private JPanel pnlCharts;
/*       */   private JPanel pnlChassis;
/*       */   private JPanel pnlControls;
/*       */   private JPanel pnlDamageChart;
/*       */   private JPanel pnlDeployment;
/*       */   private JPanel pnlEnergy;
/*       */   private JPanel pnlEquipInfo;
/*       */   private JPanel pnlEquipment;
/*       */   private JPanel pnlEquipmentChooser;
/*       */   private JPanel pnlEquipmentToPlace;
/*       */   private JPanel pnlExport;
/*       */   private JPanel pnlFluff;
/*       */   private JPanel pnlFrontArmor;
/*       */   private JPanel pnlHDArmorBox;
/*       */   private JPanel pnlHDCrits;
/*       */   private JPanel pnlHeatSinks;
/*       */   private JPanel pnlHistory;
/*       */   private JPanel pnlImage;
/*       */   private JPanel pnlInfoPanel;
/*       */   private JPanel pnlLAArmorBox;
/*       */   private JPanel pnlLACrits;
/*       */   private JPanel pnlLLArmorBox;
/*       */   private JPanel pnlLLCrits;
/*       */   private JPanel pnlLTArmorBox;
/*       */   private JPanel pnlLTCrits;
/*       */   private JPanel pnlLTRArmorBox;
/*       */   private JPanel pnlManufacturers;
/*       */   private JPanel pnlMissile;
/*       */   private JPanel pnlMovement;
/*       */   private JPanel pnlNotables;
/*       */   private JPanel pnlOmniInfo;
/*       */   private JPanel pnlOverview;
/*       */   private JPanel pnlPatchworkChoosers;
/*       */   private JPanel pnlPhysical;
/*       */   private JPanel pnlRAArmorBox;
/*       */   private JPanel pnlRACrits;
/*       */   private JPanel pnlRLArmorBox;
/*       */   private JPanel pnlRLCrits;
/*       */   private JPanel pnlRTArmorBox;
/*       */   private JPanel pnlRTCrits;
/*       */   private JPanel pnlRTRArmorBox;
/*       */   private JPanel pnlRearArmor;
/*       */   private JPanel pnlSpecials;
/*       */   private JPanel pnlVariants;
/*       */   private JPanel pnlWeaponsManufacturers;
/*       */   private JScrollPane scpWeaponManufacturers;
/*       */   private JScrollPane scrLACrits;
/*       */   private JScrollPane scrRACrits;
/*       */   private JSpinner spnBoosterMP;
/*       */   private JSpinner spnCTArmor;
/*       */   private JSpinner spnCTRArmor;
/*       */   private JSpinner spnHDArmor;
/*       */   private JSpinner spnJumpMP;
/*       */   private JSpinner spnLAArmor;
/*       */   private JSpinner spnLLArmor;
/*       */   private JSpinner spnLTArmor;
/*       */   private JSpinner spnLTRArmor;
/*       */   private JSpinner spnNumberOfHS;
/*       */   private JSpinner spnRAArmor;
/*       */   private JSpinner spnRLArmor;
/*       */   private JSpinner spnRTArmor;
/*       */   private JSpinner spnRTRArmor;
/*       */   private JSpinner spnWalkMP;
/*       */   private javax.swing.JTable tblWeaponManufacturers;
/*       */   private JTabbedPane tbpFluffEditors;
/*       */   private JTabbedPane tbpMainTabPane;
/*       */   private JTabbedPane tbpWeaponChooser;
/*       */   private JToolBar tlbIconBar;
/*       */   private JTextField txtArmorModel;
/*       */   private JTextField txtChassisModel;
/*       */   private JTextField txtChatInfo;
/*       */   private JTextField txtCommSystem;
/*       */   private JTextField txtEngineManufacturer;
/*       */   private JTextField txtEngineRating;
/*       */   private JTextField txtInfoBattleValue;
/*       */   private JTextField txtInfoCost;
/*       */   private JTextField txtInfoFreeCrits;
/*       */   private JTextField txtInfoFreeTons;
/*       */   private JTextField txtInfoHeatDiss;
/*       */   private JTextField txtInfoMaxHeat;
/*       */   private JTextField txtInfoTonnage;
/*       */   private JTextField txtInfoUnplaced;
/*       */   private JTextField txtJJModel;
/*       */   private JTextField txtManufacturer;
/*       */   private JTextField txtManufacturerLocation;
/*       */   private JTextField txtMechModel;
/*       */   private JTextField txtMechName;
/*       */   private JTextField txtProdYear;
/*       */   private JTextField txtSource;
/*       */   private JTextField txtSumArmorCrt;
/*       */   private JTextField txtSumArmorTon;
/*       */   private JTextField txtSumCocACode;
/*       */   private JTextField txtSumCocCrt;
/*       */   private JTextField txtSumCocTon;
/*       */   private JTextField txtSumEngACode;
/*       */   private JTextField txtSumEngCrt;
/*       */   private JTextField txtSumEngTon;
/*       */   private JTextField txtSumEnhACode;
/*       */   private JTextField txtSumEnhCrt;
/*       */   private JTextField txtSumEnhTon;
/*       */   private JTextField txtSumGyrACode;
/*       */   private JTextField txtSumGyrCrt;
/*       */   private JTextField txtSumGyrTon;
/*       */   private JTextField txtSumHSACode;
/*       */   private JTextField txtSumHSCrt;
/*       */   private JTextField txtSumHSTon;
/*       */   private JTextField txtSumIntACode;
/*       */   private JTextField txtSumIntCrt;
/*       */   private JTextField txtSumIntTon;
/*       */   private JTextField txtSumJJACode;
/*       */   private JTextField txtSumJJCrt;
/*       */   private JTextField txtSumJJTon;
/*       */   private JTextField txtSumPAmpsACode;
/*       */   private JTextField txtSumPAmpsTon;
/*       */   private JTextField txtTNTSystem;
/*       */   public filehandlers.ImageTracker getImageTracker()
/*       */   {
/* 15170 */     return this.imageTracker;
/*       */   }
/*       */   
/*       */   public void setUnit(ArrayList v) {
/* 15174 */     setMech((Mech)v.get(0));
/*       */   }
/*       */   
/*       */   public void loadUnitIntoGUI() {
/* 15178 */     LoadMechIntoGUI();
/*       */   }
/*       */   
/*       */   public void showOpenDialog() {
/* 15182 */     this.dOpen.Requestor = 1;
/* 15183 */     this.dOpen.setVisible(true);
/*       */   }
/*       */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\frmMainWide.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */