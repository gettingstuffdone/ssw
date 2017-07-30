/*       */ package ssw.gui;
/*       */ 
/*       */ import common.CommonTools;
/*       */ import common.DataFactory;
/*       */ import common.EquipmentFactory;
/*       */ import components.ActuatorSet;
/*       */ import components.Ammunition;
/*       */ import components.AvailableCode;
/*       */ import components.Cockpit;
/*       */ import components.Engine;
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
/*       */ import javax.swing.AbstractListModel;
/*       */ import javax.swing.BorderFactory;
/*       */ import javax.swing.BoxLayout;
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
/*       */ import javax.swing.JTable;
/*       */ import javax.swing.JTextField;
/*       */ import javax.swing.JToolBar;
/*       */ import javax.swing.SpinnerNumberModel;
/*       */ import javax.swing.border.TitledBorder;
/*       */ import javax.swing.event.ChangeEvent;
/*       */ import javax.swing.event.ChangeListener;
/*       */ import javax.swing.event.ListSelectionEvent;
/*       */ import org.netbeans.lib.awtextra.AbsoluteConstraints;
/*       */ import states.ifState;
/*       */ 
/*       */ public class frmMain extends javax.swing.JFrame implements java.awt.datatransfer.ClipboardOwner, common.DesignForm, ifMechForm
/*       */ {
/*    78 */   java.awt.event.FocusAdapter spinners = new java.awt.event.FocusAdapter()
/*       */   {
/*       */     public void focusGained(java.awt.event.FocusEvent e) {
/*    81 */       if ((e.getSource() instanceof javax.swing.text.JTextComponent)) {
/*    82 */         final javax.swing.text.JTextComponent textComponent = (javax.swing.text.JTextComponent)e.getSource();
/*    83 */         javax.swing.SwingUtilities.invokeLater(new Runnable() {
/*       */           public void run() {
/*    85 */             textComponent.selectAll();
/*       */           }
/*       */         });
/*       */       }
/*       */     }
/*       */   };
/*       */   
/*    92 */   String[] Selections = { "", "", "", "", "", "", "", "" };
/*       */   Mech CurMech;
/*       */   visitors.VSetArmorTonnage ArmorTons;
/*    95 */   Color RedCol = new Color(200, 0, 0); Color GreenCol = new Color(0, 40, 0);
/*       */   
/*    97 */   Object[][] Equipment = { { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null } };
/*       */   
/*       */   abPlaceable CurItem;
/*   100 */   int CurLocation = -1;
/*   101 */   JPopupMenu mnuUtilities = new JPopupMenu();
/*   102 */   JMenuItem mnuDetails = new JMenuItem("Details");
/*   103 */   JMenuItem mnuMountRear = new JMenuItem("Mount Rear");
/*   104 */   JMenuItem mnuSetVariable = new JMenuItem("Set Tonnage");
/*   105 */   JMenuItem mnuSetLotSize = new JMenuItem("Set Lot Size");
/*   106 */   JMenuItem mnuArmorComponent = new JMenuItem("Armor Component");
/*   107 */   JMenuItem mnuAddCapacitor = new JMenuItem("Add Capacitor");
/*   108 */   JMenuItem mnuAddInsulator = new JMenuItem("Add Insulator");
/*   109 */   JMenuItem mnuCaseless = new JMenuItem("Switch to Caseless");
/*   110 */   JMenuItem mnuTurret = new JMenuItem("Add to Turret");
/*   111 */   JMenuItem mnuSelective = new JMenuItem("Selective Allocate");
/*   112 */   JMenuItem mnuAuto = new JMenuItem("Auto-Allocate");
/*   113 */   JMenuItem mnuUnallocateAll = new JMenuItem("Unallocate All");
/*   114 */   JMenuItem mnuRemoveItem = new JMenuItem("Remove Item");
/*   115 */   JMenuItem mnuDumper = new JMenuItem("Add Dumper");
/*   116 */   JMenu mnuVGLArc = new JMenu();
/*   117 */   JMenuItem mnuVGLArcFore = new JMenuItem("Fore");
/*   118 */   JMenuItem mnuVGLArcForeSide = new JMenuItem("Fore-Side");
/*   119 */   JMenuItem mnuVGLArcRear = new JMenuItem("Rear");
/*   120 */   JMenuItem mnuVGLArcRearSide = new JMenuItem("Rear-Side");
/*   121 */   JMenu mnuVGLAmmo = new JMenu();
/*   122 */   JMenuItem mnuVGLAmmoFrag = new JMenuItem("Fragmentation");
/*   123 */   JMenuItem mnuVGLAmmoChaff = new JMenuItem("Chaff");
/*   124 */   JMenuItem mnuVGLAmmoIncen = new JMenuItem("Incendiary");
/*   125 */   JMenuItem mnuVGLAmmoSmoke = new JMenuItem("Smoke");
/*   126 */   JPopupMenu mnuFluff = new JPopupMenu();
/*   127 */   JMenuItem mnuFluffCut = new JMenuItem("Cut");
/*   128 */   JMenuItem mnuFluffCopy = new JMenuItem("Copy");
/*   129 */   JMenuItem mnuFluffPaste = new JMenuItem("Paste");
/*       */   
/*   131 */   TextPane Overview = new TextPane();
/*   132 */   TextPane Capabilities = new TextPane();
/*   133 */   TextPane Deployment = new TextPane();
/*   134 */   TextPane History = new TextPane();
/*   135 */   TextPane Additional = new TextPane();
/*   136 */   TextPane Variants = new TextPane();
/*   137 */   TextPane Notables = new TextPane();
/*       */   
/*       */   MechLoadoutRenderer Mechrender;
/*       */   public Preferences Prefs;
/*   141 */   boolean Load = false; boolean SetSource = true;
/*       */   
/*   143 */   private java.awt.Cursor Hourglass = new java.awt.Cursor(3);
/*   144 */   private java.awt.Cursor NormalCursor = new java.awt.Cursor(0);
/*       */   
/*       */   public DataFactory data;
/*   147 */   public ArrayList<components.Quirk> quirks = new ArrayList();
/*       */   
/*   149 */   private dlgPrintBatchMechs BatchWindow = null;
/*   150 */   private filehandlers.ImageTracker imageTracker = new filehandlers.ImageTracker();
/*   151 */   public dlgOpen dOpen = new dlgOpen(this, true);
/*   152 */   public dialog.frmForce dForce = new dialog.frmForce(this, this.imageTracker);
/*       */   
/*   154 */   final int BALLISTIC = 0; final int ENERGY = 1; final int MISSILE = 2; final int PHYSICAL = 3; final int EQUIPMENT = 4; final int AMMUNITION = 6; final int SELECTED = 7; final int ARTILLERY = 5;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   162 */   private final AvailableCode PPCCapAC = new AvailableCode(0);
/*   163 */   private final AvailableCode LIAC = new AvailableCode(2);
/*   164 */   private final AvailableCode CaselessAmmoAC = new AvailableCode(0);
/*   165 */   private components.PartialWing wing = new components.PartialWing(this.CurMech);
/*   166 */   private final AvailableCode PWAC = this.wing.GetAvailability();
/*       */   private JButton btnAddEquip;
/*       */   private JButton btnAddQuirk;
/*       */   
/*   170 */   public frmMain() { this.Prefs = Preferences.userRoot().node("/com/sswsuite/ssw");
/*   171 */     this.CurMech = new Mech(this.Prefs);
/*   172 */     this.ArmorTons = new visitors.VSetArmorTonnage(this.Prefs);
/*   173 */     this.Mechrender = new MechLoadoutRenderer(this);
/*       */     
/*       */ 
/*   176 */     this.PPCCapAC.SetISCodes('E', 'X', 'X', 'E');
/*   177 */     this.PPCCapAC.SetISDates(3057, 3060, true, 3060, 0, 0, false, false);
/*   178 */     this.PPCCapAC.SetISFactions("DC", "DC", "", "");
/*   179 */     this.PPCCapAC.SetPBMAllowed(true);
/*   180 */     this.PPCCapAC.SetPIMAllowed(true);
/*   181 */     this.PPCCapAC.SetRulesLevels(3, 3, -1, -1, -1);
/*   182 */     this.LIAC.SetISCodes('E', 'F', 'F', 'X');
/*   183 */     this.LIAC.SetISDates(0, 0, false, 2575, 2820, 0, true, false);
/*   184 */     this.LIAC.SetISFactions("TH", "", "", "");
/*   185 */     this.LIAC.SetCLCodes('E', 'X', 'E', 'F');
/*   186 */     this.LIAC.SetCLDates(0, 0, false, 2575, 0, 0, false, false);
/*   187 */     this.LIAC.SetCLFactions("TH", "", "", "");
/*   188 */     this.LIAC.SetPBMAllowed(true);
/*   189 */     this.LIAC.SetPIMAllowed(true);
/*   190 */     this.LIAC.SetRulesLevels(3, 3, -1, -1, -1);
/*   191 */     this.CaselessAmmoAC.SetISCodes('D', 'X', 'X', 'E');
/*   192 */     this.CaselessAmmoAC.SetISDates(3055, 3056, true, 3056, 0, 0, false, false);
/*   193 */     this.CaselessAmmoAC.SetISFactions("FC", "FC", "", "");
/*   194 */     this.CaselessAmmoAC.SetPBMAllowed(true);
/*   195 */     this.CaselessAmmoAC.SetPIMAllowed(true);
/*   196 */     this.CaselessAmmoAC.SetRulesLevels(3, 3, -1, -1, -1);
/*       */     
/*       */ 
/*   199 */     this.pnlDamageChart = new DamageChart();
/*       */     
/*   201 */     initComponents();
/*   202 */     this.Overview.SetEditorSize(310, 380);
/*   203 */     this.Capabilities.SetEditorSize(310, 380);
/*   204 */     this.Deployment.SetEditorSize(310, 380);
/*   205 */     this.History.SetEditorSize(310, 380);
/*   206 */     this.Additional.SetEditorSize(310, 380);
/*   207 */     this.Variants.SetEditorSize(310, 380);
/*   208 */     this.Notables.SetEditorSize(310, 380);
/*   209 */     this.pnlOverview.add(this.Overview);
/*   210 */     this.pnlCapabilities.add(this.Capabilities);
/*   211 */     this.pnlDeployment.add(this.Deployment);
/*   212 */     this.pnlHistory.add(this.History);
/*   213 */     this.pnlAdditionalFluff.add(this.Additional);
/*   214 */     this.pnlVariants.add(this.Variants);
/*   215 */     this.pnlNotables.add(this.Notables);
/*   216 */     setViewToolbar(this.Prefs.getBoolean("ViewToolbar", true));
/*   217 */     setTitle("Solaris Skunk Werks 0.6.83.1");
/*   218 */     pack();
/*       */     
/*   220 */     this.mnuDetails.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*   222 */         frmMain.this.GetInfoOn();
/*       */       }
/*       */       
/*   225 */     });
/*   226 */     this.mnuMountRear.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*   228 */         frmMain.this.MountRear();
/*       */       }
/*       */       
/*   231 */     });
/*   232 */     this.mnuSetVariable.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   234 */         frmMain.this.SetVariableSize();
/*       */       }
/*       */       
/*   237 */     });
/*   238 */     this.mnuSetLotSize.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   240 */         frmMain.this.SetAmmoLotSize();
/*       */       }
/*       */       
/*   243 */     });
/*   244 */     this.mnuArmorComponent.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   246 */         frmMain.this.ArmorComponent();
/*       */       }
/*       */       
/*   249 */     });
/*   250 */     this.mnuAddCapacitor.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   252 */         frmMain.this.PPCCapacitor();
/*       */       }
/*       */       
/*   255 */     });
/*   256 */     this.mnuAddInsulator.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   258 */         frmMain.this.LaserInsulator();
/*       */       }
/*       */       
/*   261 */     });
/*   262 */     this.mnuTurret.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   264 */         frmMain.this.TurretMount();
/*       */       }
/*       */       
/*   267 */     });
/*   268 */     this.mnuDumper.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   270 */         frmMain.this.DumperMount();
/*       */       }
/*       */       
/*   273 */     });
/*   274 */     this.mnuCaseless.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   276 */         frmMain.this.SwitchCaseless();
/*       */       }
/*       */       
/*   279 */     });
/*   280 */     this.mnuVGLArcFore.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   282 */         frmMain.this.SetVGLArcFore();
/*       */       }
/*       */       
/*   285 */     });
/*   286 */     this.mnuVGLArcForeSide.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   288 */         frmMain.this.SetVGLArcForeSide();
/*       */       }
/*       */       
/*   291 */     });
/*   292 */     this.mnuVGLArcRear.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   294 */         frmMain.this.SetVGLArcRear();
/*       */       }
/*       */       
/*   297 */     });
/*   298 */     this.mnuVGLArcRearSide.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   300 */         frmMain.this.SetVGLArcRearSide();
/*       */       }
/*       */       
/*   303 */     });
/*   304 */     this.mnuVGLAmmoFrag.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   306 */         frmMain.this.SetVGLAmmoFrag();
/*       */       }
/*       */       
/*   309 */     });
/*   310 */     this.mnuVGLAmmoChaff.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   312 */         frmMain.this.SetVGLAmmoChaff();
/*       */       }
/*       */       
/*   315 */     });
/*   316 */     this.mnuVGLAmmoIncen.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   318 */         frmMain.this.SetVGLAmmoIncendiary();
/*       */       }
/*       */       
/*   321 */     });
/*   322 */     this.mnuVGLAmmoSmoke.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   324 */         frmMain.this.SetVGLAmmoSmoke();
/*       */       }
/*       */       
/*   327 */     });
/*   328 */     this.mnuSelective.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent arg0) {
/*   330 */         frmMain.this.SelectiveAllocate();
/*       */       }
/*       */       
/*   333 */     });
/*   334 */     this.mnuAuto.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent arg0) {
/*   336 */         frmMain.this.AutoAllocate();
/*       */       }
/*       */       
/*   339 */     });
/*   340 */     this.mnuUnallocateAll.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*   342 */         frmMain.this.UnallocateAll();
/*       */       }
/*       */       
/*   345 */     });
/*   346 */     this.mnuRemoveItem.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   348 */         frmMain.this.RemoveItemCritTab();
/*       */       }
/*       */       
/*   351 */     });
/*   352 */     this.mnuVGLArc.setText("Set VGL Arc");
/*   353 */     this.mnuVGLArc.add(this.mnuVGLArcFore);
/*   354 */     this.mnuVGLArc.add(this.mnuVGLArcForeSide);
/*   355 */     this.mnuVGLArc.add(this.mnuVGLArcRear);
/*   356 */     this.mnuVGLArc.add(this.mnuVGLArcRearSide);
/*       */     
/*   358 */     this.mnuVGLAmmo.setText("Set VGL Ammo");
/*   359 */     this.mnuVGLAmmo.add(this.mnuVGLAmmoFrag);
/*   360 */     this.mnuVGLAmmo.add(this.mnuVGLAmmoChaff);
/*   361 */     this.mnuVGLAmmo.add(this.mnuVGLAmmoIncen);
/*   362 */     this.mnuVGLAmmo.add(this.mnuVGLAmmoSmoke);
/*       */     
/*   364 */     this.mnuUtilities.add(this.mnuDetails);
/*   365 */     this.mnuUtilities.add(this.mnuMountRear);
/*   366 */     this.mnuUtilities.add(this.mnuSetVariable);
/*   367 */     this.mnuUtilities.add(this.mnuSetLotSize);
/*   368 */     this.mnuUtilities.add(this.mnuArmorComponent);
/*   369 */     this.mnuUtilities.add(this.mnuAddCapacitor);
/*   370 */     this.mnuUtilities.add(this.mnuAddInsulator);
/*   371 */     this.mnuUtilities.add(this.mnuCaseless);
/*   372 */     this.mnuUtilities.add(this.mnuTurret);
/*   373 */     this.mnuUtilities.add(this.mnuVGLArc);
/*   374 */     this.mnuUtilities.add(this.mnuVGLAmmo);
/*   375 */     this.mnuUtilities.add(this.mnuSelective);
/*   376 */     this.mnuUtilities.add(this.mnuAuto);
/*   377 */     this.mnuUtilities.add(this.mnuDumper);
/*   378 */     this.mnuUtilities.add(this.mnuUnallocateAll);
/*   379 */     this.mnuUtilities.add(this.mnuRemoveItem);
/*       */     
/*   381 */     this.mnuSetVariable.setVisible(false);
/*   382 */     this.mnuArmorComponent.setVisible(false);
/*   383 */     this.mnuAddCapacitor.setVisible(false);
/*   384 */     this.mnuAddInsulator.setVisible(false);
/*   385 */     this.mnuTurret.setVisible(false);
/*   386 */     this.mnuCaseless.setVisible(false);
/*   387 */     this.mnuVGLArc.setVisible(false);
/*   388 */     this.mnuVGLAmmo.setVisible(false);
/*       */     
/*   390 */     this.mnuFluffCut.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   392 */         frmMain.this.FluffCut(frmMain.this.mnuFluff.getInvoker());
/*       */       }
/*       */       
/*   395 */     });
/*   396 */     this.mnuFluffCopy.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   398 */         frmMain.this.FluffCopy(frmMain.this.mnuFluff.getInvoker());
/*       */       }
/*       */       
/*   401 */     });
/*   402 */     this.mnuFluffPaste.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent e) {
/*   404 */         frmMain.this.FluffPaste(frmMain.this.mnuFluff.getInvoker());
/*       */       }
/*       */       
/*   407 */     });
/*   408 */     this.mnuFluff.add(this.mnuFluffCut);
/*   409 */     this.mnuFluff.add(this.mnuFluffCopy);
/*   410 */     this.mnuFluff.add(this.mnuFluffPaste);
/*       */     try
/*       */     {
/*   413 */       this.CurMech.Visit(new visitors.VMechFullRecalc());
/*       */     }
/*       */     catch (Exception e) {
/*   416 */       System.err.println(e.getMessage());
/*   417 */       e.printStackTrace();
/*       */     }
/*       */     
/*       */     try
/*       */     {
/*   422 */       this.data = new DataFactory(this.CurMech);
/*       */     } catch (Exception e) {
/*   424 */       System.err.println(e.getMessage());
/*   425 */       e.printStackTrace();
/*       */     }
/*       */     
/*       */ 
/*   429 */     this.cmbRulesLevel.setSelectedItem(this.Prefs.get("NewMech_RulesLevel", "Tournament Legal"));
/*   430 */     this.cmbMechEra.setSelectedItem(this.Prefs.get("NewMech_Era", "Age of War/Star League"));
/*   431 */     BuildTechBaseSelector();
/*   432 */     this.cmbTechBase.setSelectedItem(this.Prefs.get("NewMech_Techbase", "Inner Sphere"));
/*       */     
/*   434 */     BuildChassisSelector();
/*   435 */     BuildEngineSelector();
/*   436 */     BuildGyroSelector();
/*   437 */     BuildCockpitSelector();
/*   438 */     BuildEnhancementSelector();
/*   439 */     BuildArmorSelector();
/*   440 */     BuildHeatsinkSelector();
/*   441 */     BuildJumpJetSelector();
/*   442 */     FixArmorSpinners();
/*   443 */     SetPatchworkArmor();
/*   444 */     RefreshSummary();
/*   445 */     RefreshInfoPane();
/*   446 */     RefreshInternalPoints();
/*   447 */     SetLoadoutArrays();
/*   448 */     SetWeaponChoosers();
/*   449 */     this.cmbInternalType.setSelectedItem("Standard Structure");
/*   450 */     this.cmbEngineType.setSelectedItem("Fusion Engine");
/*   451 */     this.cmbGyroType.setSelectedItem("Standard Gyro");
/*   452 */     this.cmbCockpitType.setSelectedItem("Standard Cockpit");
/*   453 */     this.cmbPhysEnhance.setSelectedItem("No Enhancement");
/*   454 */     this.cmbHeatSinkType.setSelectedItem(this.Prefs.get("NewMech_Heatsinks", "Single Heat Sink"));
/*   455 */     this.cmbJumpJetType.setSelectedItem("Standard Jump Jet");
/*   456 */     this.cmbArmorType.setSelectedItem("Standard Armor");
/*   457 */     this.cmbOmniVariant.setModel(new DefaultComboBoxModel(new String[] { this.CurMech.GetLoadout().GetName() }));
/*   458 */     this.lblSumPAmps.setVisible(false);
/*   459 */     this.txtSumPAmpsTon.setVisible(false);
/*   460 */     this.txtSumPAmpsACode.setVisible(false);
/*       */     
/*   462 */     this.tblWeaponManufacturers.setModel(new javax.swing.table.AbstractTableModel()
/*       */     {
/*       */       public String getColumnName(int col) {
/*   465 */         if (col == 1) {
/*   466 */           return "Manufacturer/Model";
/*       */         }
/*   468 */         return "Item Name";
/*       */       }
/*       */       
/*   471 */       public int getRowCount() { return frmMain.this.CurMech.GetLoadout().GetEquipment().size(); }
/*   472 */       public int getColumnCount() { return 2; }
/*       */       
/*   474 */       public Object getValueAt(int row, int col) { Object o = frmMain.this.CurMech.GetLoadout().GetEquipment().get(row);
/*   475 */         if (col == 1) {
/*   476 */           return ((abPlaceable)o).GetManufacturer();
/*       */         }
/*   478 */         return ((abPlaceable)o).CritName();
/*       */       }
/*       */       
/*       */       public boolean isCellEditable(int row, int col)
/*       */       {
/*   483 */         if (col == 0) {
/*   484 */           return false;
/*       */         }
/*   486 */         return true;
/*       */       }
/*       */       
/*       */       public void setValueAt(Object value, int row, int col)
/*       */       {
/*   491 */         if (col == 0) return;
/*   492 */         if (!(value instanceof String)) return;
/*   493 */         abPlaceable a = (abPlaceable)frmMain.this.CurMech.GetLoadout().GetEquipment().get(row);
/*   494 */         if (frmMain.this.chkIndividualWeapons.isSelected()) {
/*   495 */           a.SetManufacturer((String)value);
/*   496 */           fireTableCellUpdated(row, col);
/*       */         } else {
/*   498 */           ArrayList v = frmMain.this.CurMech.GetLoadout().GetEquipment();
/*   499 */           for (int i = 0; i < v.size(); i++) {
/*   500 */             if (filehandlers.FileCommon.LookupStripArc(((abPlaceable)v.get(i)).LookupName()).equals(filehandlers.FileCommon.LookupStripArc(a.LookupName()))) {
/*   501 */               ((abPlaceable)v.get(i)).SetManufacturer((String)value);
/*       */             }
/*       */           }
/*   504 */           fireTableDataChanged();
/*       */         }
/*       */         
/*       */       }
/*   508 */     });
/*   509 */     this.tblWeaponManufacturers.getInputMap(1).put(javax.swing.KeyStroke.getKeyStroke(9, 0, false), "selectNextRow");
/*       */     
/*       */ 
/*   512 */     if (this.Prefs.getBoolean("LoadLastMech", false)) { LoadMechFromFile(this.Prefs.get("LastOpenDirectory", "") + this.Prefs.get("LastOpenFile", ""));
/*       */     }
/*   514 */     if (!this.Prefs.get("FileToOpen", "").isEmpty()) { LoadMechFromFile(this.Prefs.get("FileToOpen", ""));
/*       */     }
/*       */     
/*   517 */     this.CurMech.SetChanged(false);
/*       */     
/*       */ 
/*   520 */     this.pnlCLArmorBox.setVisible(false);
/*   521 */     this.pnlCLCrits.setVisible(false);
/*       */   }
/*       */   
/*       */   public Preferences GetPrefs() {
/*   525 */     return this.Prefs;
/*       */   }
/*       */   
/*       */   public Mech GetMech() {
/*   529 */     return this.CurMech;
/*       */   }
/*       */   
/*       */   public abPlaceable GetCurItem() {
/*   533 */     return this.CurItem;
/*       */   }
/*       */   
/*       */ 
/*   537 */   public DataFactory GetData() { return this.data; }
/*       */   
/*       */   private JButton btnAddToForceList;
/*       */   private JButton btnAddVariant;
/*   541 */   public dialog.frmForce GetForceDialogue() { return this.dForce; }
/*       */   
/*       */   private JButton btnArmorTons;
/*       */   
/*   545 */   public MechLoadoutRenderer GetLoadoutRenderer() { return this.Mechrender; }
/*       */   
/*       */   private JButton btnAutoAllocate;
/*       */   private JCheckBox btnBalanceArmor;
/*       */   private JButton btnBracketChart;
/*       */   private JButton btnChatInfo;
/*       */   
/*   552 */   private void SetWeaponChoosers() { this.data.Rebuild(this.CurMech);
/*   553 */     this.Equipment[1] = this.data.GetEquipment().GetEnergyWeapons(this.CurMech);
/*   554 */     this.Equipment[2] = this.data.GetEquipment().GetMissileWeapons(this.CurMech);
/*   555 */     this.Equipment[0] = this.data.GetEquipment().GetBallisticWeapons(this.CurMech);
/*   556 */     this.Equipment[3] = this.data.GetEquipment().GetPhysicalWeapons(this.CurMech);
/*   557 */     this.Equipment[5] = this.data.GetEquipment().GetArtillery(this.CurMech);
/*   558 */     this.Equipment[4] = this.data.GetEquipment().GetEquipment(this.CurMech);
/*   559 */     this.Equipment[6] = { " " };
/*   560 */     if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/*   561 */       this.Equipment[7] = { " " };
/*       */     } else {
/*   563 */       this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */     }
/*       */     
/*   566 */     for (int i = 0; i < this.Equipment.length; i++) {
/*   567 */       if (this.Equipment[i] == null) {
/*   568 */         this.Equipment[i] = { " " };
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   573 */     this.lstChooseEnergy.setListData(this.Equipment[1]);
/*   574 */     this.lstChooseMissile.setListData(this.Equipment[2]);
/*   575 */     this.lstChooseBallistic.setListData(this.Equipment[0]);
/*   576 */     this.lstChoosePhysical.setListData(this.Equipment[3]);
/*   577 */     this.lstChooseEquipment.setListData(this.Equipment[4]);
/*   578 */     this.lstChooseAmmunition.setListData(this.Equipment[6]);
/*   579 */     this.lstSelectedEquipment.setListData(this.Equipment[7]);
/*   580 */     this.lstChooseArtillery.setListData(this.Equipment[5]);
/*   581 */     this.lstSelectedEquipment.repaint();
/*       */   }
/*       */   
/*       */   private JButton btnClearEquip;
/*       */   private JButton btnClearImage;
/*       */   private JButton btnClearLoadout;
/*   587 */   private void SetLoadoutArrays() { if (this.CurMech.IsQuad())
/*       */     {
/*   589 */       ((TitledBorder)this.pnlLACrits.getBorder()).setTitle("Left Front Leg");
/*   590 */       ((TitledBorder)this.pnlRACrits.getBorder()).setTitle("Right Front Leg");
/*   591 */       ((TitledBorder)this.pnlLLCrits.getBorder()).setTitle("Left Rear Leg");
/*   592 */       ((TitledBorder)this.pnlRLCrits.getBorder()).setTitle("Right Rear Leg");
/*   593 */       this.lstLACrits.setVisibleRowCount(6);
/*   594 */       this.lstRACrits.setVisibleRowCount(6);
/*   595 */     } else if (this.CurMech.IsTripod()) {
/*   596 */       ((TitledBorder)this.pnlLACrits.getBorder()).setTitle("Left Arm");
/*   597 */       ((TitledBorder)this.pnlRACrits.getBorder()).setTitle("Right Arm");
/*   598 */       ((TitledBorder)this.pnlLLCrits.getBorder()).setTitle("Left Leg");
/*   599 */       ((TitledBorder)this.pnlRLCrits.getBorder()).setTitle("Right Leg");
/*   600 */       this.pnlCLCrits.setVisible(true);
/*   601 */       this.lstLACrits.setVisibleRowCount(12);
/*   602 */       this.lstRACrits.setVisibleRowCount(12);
/*       */     } else {
/*   604 */       ((TitledBorder)this.pnlLACrits.getBorder()).setTitle("Left Arm");
/*   605 */       ((TitledBorder)this.pnlRACrits.getBorder()).setTitle("Right Arm");
/*   606 */       ((TitledBorder)this.pnlLLCrits.getBorder()).setTitle("Left Leg");
/*   607 */       ((TitledBorder)this.pnlRLCrits.getBorder()).setTitle("Right Leg");
/*   608 */       this.lstLACrits.setVisibleRowCount(12);
/*   609 */       this.lstRACrits.setVisibleRowCount(12);
/*       */     }
/*       */     
/*   612 */     CheckActuators();
/*       */     
/*       */ 
/*   615 */     this.lstHDCrits.setListData(this.CurMech.GetLoadout().GetHDCrits());
/*   616 */     this.lstCTCrits.setListData(this.CurMech.GetLoadout().GetCTCrits());
/*   617 */     this.lstLTCrits.setListData(this.CurMech.GetLoadout().GetLTCrits());
/*   618 */     this.lstRTCrits.setListData(this.CurMech.GetLoadout().GetRTCrits());
/*   619 */     this.lstLACrits.setListData(this.CurMech.GetLoadout().GetLACrits());
/*   620 */     this.lstRACrits.setListData(this.CurMech.GetLoadout().GetRACrits());
/*   621 */     this.lstLLCrits.setListData(this.CurMech.GetLoadout().GetLLCrits());
/*   622 */     this.lstRLCrits.setListData(this.CurMech.GetLoadout().GetRLCrits());
/*   623 */     this.lstCritsToPlace.setListData(this.CurMech.GetLoadout().GetQueue().toArray());
/*       */   }
/*       */   
/*       */   public void setMech(Mech m) {
/*   627 */     this.CurMech = m;
/*   628 */     LoadMechIntoGUI();
/*       */   }
/*       */   
/*       */   public void CheckActuators() {
/*   632 */     if (this.CurMech.IsQuad()) {
/*   633 */       this.chkLALowerArm.setEnabled(false);
/*   634 */       this.chkRALowerArm.setEnabled(false);
/*   635 */       this.chkLAHand.setEnabled(false);
/*   636 */       this.chkRAHand.setEnabled(false);
/*   637 */       this.chkLALowerArm.setSelected(true);
/*   638 */       this.chkRALowerArm.setSelected(true);
/*   639 */       this.chkLAHand.setSelected(true);
/*   640 */       this.chkRAHand.setSelected(true);
/*       */     } else {
/*   642 */       if (this.CurMech.GetActuators().LockedLeft()) {
/*   643 */         this.chkLALowerArm.setEnabled(false);
/*   644 */         this.chkLAHand.setEnabled(false);
/*       */       } else {
/*   646 */         this.chkLALowerArm.setEnabled(true);
/*   647 */         this.chkLAHand.setEnabled(true);
/*       */       }
/*   649 */       if (this.CurMech.GetActuators().LockedRight()) {
/*   650 */         this.chkRALowerArm.setEnabled(false);
/*   651 */         this.chkRAHand.setEnabled(false);
/*       */       } else {
/*   653 */         this.chkRALowerArm.setEnabled(true);
/*   654 */         this.chkRAHand.setEnabled(true);
/*       */       }
/*   656 */       if (this.CurMech.GetActuators().LeftLowerInstalled()) {
/*   657 */         this.chkLALowerArm.setSelected(true);
/*   658 */         if (this.CurMech.GetActuators().LeftHandInstalled()) {
/*   659 */           this.chkLAHand.setSelected(true);
/*       */         } else {
/*   661 */           this.chkLAHand.setSelected(false);
/*       */         }
/*       */       } else {
/*   664 */         this.chkLALowerArm.setSelected(false);
/*   665 */         this.chkLAHand.setSelected(false);
/*       */       }
/*   667 */       if (this.CurMech.GetActuators().RightLowerInstalled()) {
/*   668 */         this.chkRALowerArm.setSelected(true);
/*   669 */         if (this.CurMech.GetActuators().RightHandInstalled()) {
/*   670 */           this.chkRAHand.setSelected(true);
/*       */         } else {
/*   672 */           this.chkRAHand.setSelected(false);
/*       */         }
/*       */       } else {
/*   675 */         this.chkRALowerArm.setSelected(false);
/*   676 */         this.chkRAHand.setSelected(false);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void FixWalkMPSpinner()
/*       */   {
/*   683 */     int MaxWalk = this.CurMech.GetMaxWalkMP();
/*   684 */     int CurWalk = this.CurMech.GetWalkingMP();
/*       */     
/*       */ 
/*       */ 
/*   688 */     if (CurWalk > MaxWalk) CurWalk = MaxWalk;
/*       */     try
/*       */     {
/*   691 */       this.CurMech.SetWalkMP(CurWalk);
/*       */     } catch (Exception e) {
/*   693 */       Media.Messager(e.getMessage() + "\nSetting Walk MP to 1.  Please reset to desired speed.");
/*       */       try {
/*   695 */         this.CurMech.SetWalkMP(1);
/*       */       } catch (Exception e1) {
/*   697 */         Media.Messager(this, "Fatal error while attempting to set Walk MP to 1:\n" + e1.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*   698 */         GetNewMech();
/*   699 */         return;
/*       */       }
/*       */     }
/*   702 */     this.lblRunMP.setText("" + this.CurMech.GetRunningMP());
/*       */     
/*       */ 
/*   705 */     this.spnWalkMP.setModel(new SpinnerNumberModel(CurWalk, 1, MaxWalk, 1));
/*   706 */     ((JSpinner.DefaultEditor)this.spnWalkMP.getEditor()).getTextField().addFocusListener(this.spinners);
/*       */   }
/*       */   
/*       */   private void BuildChassisSelector()
/*       */   {
/*   711 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   715 */     ifState[] check = this.CurMech.GetIntStruc().GetStates(this.CurMech.IsQuad());
/*   716 */     for (int i = 0; i < check.length; i++) {
/*   717 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   718 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   723 */     String[] temp = new String[list.size()];
/*   724 */     for (int i = 0; i < list.size(); i++) {
/*   725 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   729 */     this.cmbInternalType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildEngineSelector()
/*       */   {
/*   734 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   738 */     ifState[] check = this.CurMech.GetEngine().GetStates();
/*   739 */     for (int i = 0; i < check.length; i++) {
/*   740 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   741 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   746 */     String[] temp = new String[list.size()];
/*   747 */     for (int i = 0; i < list.size(); i++) {
/*   748 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   752 */     this.cmbEngineType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildGyroSelector()
/*       */   {
/*   757 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   761 */     ifState[] check = this.CurMech.GetGyro().GetStates();
/*   762 */     for (int i = 0; i < check.length; i++) {
/*   763 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   764 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   769 */     String[] temp = new String[list.size()];
/*   770 */     for (int i = 0; i < list.size(); i++) {
/*   771 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   775 */     this.cmbGyroType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildCockpitSelector()
/*       */   {
/*   780 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   784 */     ifState[] check = this.CurMech.GetCockpit().GetStates();
/*   785 */     for (int i = 0; i < check.length; i++) {
/*   786 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   787 */         list.add(check[i].LookupName());
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   792 */     String[] temp = new String[list.size()];
/*   793 */     for (int i = 0; i < list.size(); i++) {
/*   794 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   798 */     this.cmbCockpitType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildEnhancementSelector()
/*       */   {
/*   803 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   807 */     ifState[] check = this.CurMech.GetPhysEnhance().GetStates();
/*   808 */     for (int i = 0; i < check.length; i++) {
/*   809 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   810 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   815 */     String[] temp = new String[list.size()];
/*   816 */     for (int i = 0; i < list.size(); i++) {
/*   817 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   821 */     this.cmbPhysEnhance.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildJumpJetSelector()
/*       */   {
/*   826 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   830 */     ifState[] check = this.CurMech.GetJumpJets().GetStates();
/*   831 */     for (int i = 0; i < check.length; i++) {
/*   832 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   833 */         list.add(check[i].LookupName());
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   838 */     String[] temp = new String[list.size()];
/*   839 */     for (int i = 0; i < list.size(); i++) {
/*   840 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   844 */     this.cmbJumpJetType.setModel(new DefaultComboBoxModel(temp));
/*   845 */     if (temp.length > 0) {
/*   846 */       EnableJumpJets(true);
/*   847 */       this.cmbJumpJetType.setSelectedItem(this.CurMech.GetJumpJets().LookupName());
/*       */     } else {
/*   849 */       EnableJumpJets(false);
/*       */     } }
/*       */   
/*       */   private JButton btnCompactCrits;
/*       */   private JButton btnDeleteVariant;
/*       */   private JButton btnEfficientArmor;
/*       */   private JButton btnExportClipboardIcon;
/*       */   private JButton btnExportHTML;
/*   857 */   private void FixJJSpinnerModel() { int min = 0;
/*   858 */     int max = 0;
/*   859 */     int current = 0;
/*       */     
/*   861 */     if (this.CurMech.IsOmnimech()) {
/*   862 */       min = this.CurMech.GetJumpJets().GetBaseLoadoutNumJJ();
/*       */     }
/*       */     
/*   865 */     if (this.CurMech.GetJumpJets().IsImproved()) {
/*   866 */       if ((this.CurMech.GetArmor().IsHardened()) && (!this.CurMech.GetJumpJets().IsImproved())) {
/*   867 */         max = this.CurMech.GetRunningMP() - 1;
/*       */       } else {
/*   869 */         max = this.CurMech.GetRunningMP();
/*       */       }
/*       */     } else {
/*   872 */       max = this.CurMech.GetWalkingMP();
/*       */     }
/*       */     
/*   875 */     current = this.CurMech.GetJumpJets().GetNumJJ();
/*       */     
/*       */ 
/*   878 */     if (current > max) {
/*   879 */       for (; current > max; current--) {
/*   880 */         this.CurMech.GetJumpJets().DecrementNumJJ();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   885 */     if (current < min) {
/*   886 */       for (; current < min; current++) {
/*   887 */         this.CurMech.GetJumpJets().IncrementNumJJ();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   892 */     if (this.CurMech.GetJumpJets().GetNumJJ() > 0)
/*       */     {
/*   894 */       this.txtJJModel.setEnabled(true);
/*       */     }
/*       */     else {
/*   897 */       this.txtJJModel.setEnabled(false);
/*       */     }
/*       */     
/*   900 */     this.spnJumpMP.setModel(new SpinnerNumberModel(current, min, max, 1));
/*   901 */     ((JSpinner.DefaultEditor)this.spnJumpMP.getEditor()).getTextField().addFocusListener(this.spinners);
/*       */   }
/*       */   
/*       */   private void FixHeatSinkSpinnerModel()
/*       */   {
/*   906 */     if (this.CurMech.IsOmnimech()) {
/*   907 */       this.spnNumberOfHS.setModel(new SpinnerNumberModel(this.CurMech
/*   908 */         .GetHeatSinks().GetNumHS(), this.CurMech.GetHeatSinks().GetBaseLoadoutNumHS(), 65, 1));
/*       */     } else {
/*   910 */       this.spnNumberOfHS.setModel(new SpinnerNumberModel(this.CurMech
/*   911 */         .GetHeatSinks().GetNumHS(), this.CurMech.GetEngine().FreeHeatSinks(), 65, 1));
/*       */     }
/*       */     
/*   914 */     ((JSpinner.DefaultEditor)this.spnNumberOfHS.getEditor()).getTextField().addFocusListener(this.spinners);
/*       */   }
/*       */   
/*       */   private void FixJumpBoosterSpinnerModel() {
/*   918 */     int current = this.CurMech.GetJumpBoosterMP();
/*       */     
/*   920 */     this.spnBoosterMP.setModel(new SpinnerNumberModel(current, 0, 20, 1));
/*       */   }
/*       */   
/*       */   private void BuildHeatsinkSelector()
/*       */   {
/*   925 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   929 */     ifState[] check = this.CurMech.GetHeatSinks().GetStates();
/*   930 */     for (int i = 0; i < check.length; i++) {
/*   931 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   932 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   937 */     String[] temp = new String[list.size()];
/*   938 */     for (int i = 0; i < list.size(); i++) {
/*   939 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   943 */     this.cmbHeatSinkType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildArmorSelector()
/*       */   {
/*   948 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   952 */     ifState[] check = this.CurMech.GetArmor().GetStates();
/*   953 */     for (int i = 0; i < check.length; i++) {
/*   954 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   955 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   960 */     String[] temp = new String[list.size()];
/*   961 */     for (int i = 0; i < list.size(); i++) {
/*   962 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   966 */     this.cmbArmorType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildPatchworkChoosers()
/*       */   {
/*   971 */     ArrayList list = new ArrayList();
/*       */     
/*       */ 
/*       */ 
/*   975 */     ifState[] check = this.CurMech.GetArmor().GetPatchworkStates();
/*   976 */     for (int i = 0; i < check.length; i++) {
/*   977 */       if (CommonTools.IsAllowed(check[i].GetAvailability(), this.CurMech)) {
/*   978 */         list.add(BuildLookupName(check[i]));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   983 */     String[] temp = new String[list.size()];
/*   984 */     for (int i = 0; i < list.size(); i++) {
/*   985 */       temp[i] = ((String)list.get(i));
/*       */     }
/*       */     
/*       */ 
/*   989 */     this.cmbPWHDType.setModel(new DefaultComboBoxModel(temp));
/*   990 */     this.cmbPWCTType.setModel(new DefaultComboBoxModel(temp));
/*   991 */     this.cmbPWLTType.setModel(new DefaultComboBoxModel(temp));
/*   992 */     this.cmbPWRTType.setModel(new DefaultComboBoxModel(temp));
/*   993 */     this.cmbPWLAType.setModel(new DefaultComboBoxModel(temp));
/*   994 */     this.cmbPWRAType.setModel(new DefaultComboBoxModel(temp));
/*   995 */     this.cmbPWLLType.setModel(new DefaultComboBoxModel(temp));
/*   996 */     this.cmbPWRLType.setModel(new DefaultComboBoxModel(temp));
/*       */   }
/*       */   
/*       */   private void BuildTechBaseSelector() {
/*  1000 */     switch (this.CurMech.GetEra()) {
/*       */     case 0: 
/*  1002 */       this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/*  1003 */       break;
/*       */     default: 
/*  1005 */       if (this.CurMech.GetRulesLevel() >= 3) {
/*  1006 */         this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan", "Mixed" }));
/*  1007 */       } else if (this.CurMech.GetRulesLevel() == 0) {
/*  1008 */         this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/*       */       } else {
/*  1010 */         this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan" }));
/*       */       }
/*       */       break;
/*       */     }
/*       */     try {
/*  1015 */       this.cmbTechBase.setSelectedIndex(this.CurMech.GetTechbase());
/*       */     } catch (Exception e) {
/*  1017 */       Media.Messager("Could not set the Techbase due to changes.\nReverting to Inner Sphere.");
/*  1018 */       this.cmbTechBase.setSelectedIndex(0);
/*       */     }
/*       */   }
/*       */   
/*       */   private void BuildMechTypeSelector() {
/*  1023 */     switch (this.CurMech.GetRulesLevel()) {
/*       */     case 0: 
/*  1025 */       this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech" }));
/*  1026 */       this.CurMech.SetModern();
/*  1027 */       break;
/*       */     case 4: 
/*  1029 */       if (this.CurMech.GetEra() == 1) {
/*  1030 */         this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech", "IndustrialMech" }));
/*  1031 */         this.CurMech.SetModern();
/*       */       } else {
/*  1033 */         this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech", "IndustrialMech", "Primitive BattleMech", "Primitive IndustrialMech" }));
/*       */       }
/*  1035 */       break;
/*       */     default: 
/*  1037 */       this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech", "IndustrialMech" }));
/*  1038 */       this.CurMech.SetModern();
/*       */     }
/*       */     try
/*       */     {
/*  1042 */       if (this.CurMech.IsIndustrialmech()) {
/*  1043 */         if (this.CurMech.IsPrimitive()) {
/*  1044 */           this.cmbMechType.setSelectedIndex(3);
/*       */         } else {
/*  1046 */           this.cmbMechType.setSelectedIndex(1);
/*       */         }
/*       */       }
/*  1049 */       else if (this.CurMech.IsPrimitive()) {
/*  1050 */         this.cmbMechType.setSelectedIndex(2);
/*       */       } else {
/*  1052 */         this.cmbMechType.setSelectedIndex(0);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/*  1056 */       Media.Messager("Could not set the 'Mech type due to changes.\nReverting to a BattleMech.");
/*  1057 */       this.cmbMechType.setSelectedIndex(0);
/*       */     }
/*       */   }
/*       */   
/*       */   public String BuildLookupName(ifState s) {
/*  1062 */     String retval = s.LookupName();
/*  1063 */     if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/*  1064 */       if (s.HasCounterpart()) {
/*  1065 */         if (s.GetAvailability().GetTechBase() == 1) {
/*  1066 */           return "(CL) " + retval;
/*       */         }
/*  1068 */         return "(IS) " + retval;
/*       */       }
/*       */       
/*  1071 */       return retval;
/*       */     }
/*       */     
/*  1074 */     return retval;
/*       */   }
/*       */   
/*       */   private JButton btnExportHTMLIcon;
/*       */   private JButton btnExportMTF;
/*       */   private JButton btnExportMTFIcon;
/*       */   private JButton btnExportTXT;
/*       */   
/*  1082 */   private void RefreshEquipment() { components.ifMissileGuidance ArtCheck = new components.ArtemisIVFCS(null);
/*  1083 */     if (CommonTools.IsAllowed(ArtCheck.GetAvailability(), this.CurMech)) {
/*  1084 */       this.chkFCSAIV.setEnabled(true);
/*       */     } else {
/*  1086 */       this.chkFCSAIV.setSelected(false);
/*  1087 */       this.chkFCSAIV.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1091 */     ArtCheck = new components.ArtemisVFCS(null);
/*  1092 */     if (CommonTools.IsAllowed(ArtCheck.GetAvailability(), this.CurMech)) {
/*  1093 */       this.chkFCSAV.setEnabled(true);
/*       */     } else {
/*  1095 */       this.chkFCSAV.setSelected(false);
/*  1096 */       this.chkFCSAV.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1100 */     ArtCheck = new components.ApolloFCS(null);
/*  1101 */     if (CommonTools.IsAllowed(ArtCheck.GetAvailability(), this.CurMech)) {
/*  1102 */       this.chkFCSApollo.setEnabled(true);
/*       */     } else {
/*  1104 */       this.chkFCSApollo.setSelected(false);
/*  1105 */       this.chkFCSApollo.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1109 */     if (CommonTools.IsAllowed(this.CurMech.GetTC().GetAvailability(), this.CurMech)) {
/*  1110 */       this.chkUseTC.setEnabled(true);
/*  1111 */       if (this.CurMech.UsingTC()) {
/*  1112 */         this.chkUseTC.setSelected(true);
/*       */       } else {
/*  1114 */         this.chkUseTC.setSelected(false);
/*       */       }
/*       */     } else {
/*  1117 */       this.chkUseTC.setSelected(false);
/*  1118 */       this.chkUseTC.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1122 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetCTCase().GetAvailability(), this.CurMech)) {
/*  1123 */       this.chkCTCASE.setEnabled(true);
/*  1124 */       this.chkLTCASE.setEnabled(true);
/*  1125 */       this.chkRTCASE.setEnabled(true);
/*       */     } else {
/*  1127 */       this.chkCTCASE.setSelected(false);
/*  1128 */       this.chkCTCASE.setEnabled(false);
/*  1129 */       this.chkLTCASE.setSelected(false);
/*  1130 */       this.chkLTCASE.setEnabled(false);
/*  1131 */       this.chkRTCASE.setSelected(false);
/*  1132 */       this.chkRTCASE.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1136 */     if (this.CurMech.GetCockpit().CanUseCommandConsole()) if ((CommonTools.IsAllowed(this.CurMech.GetCommandConsole().GetAvailability(), this.CurMech) & !this.chkFHES.isSelected())) {
/*  1137 */         this.chkCommandConsole.setEnabled(true);
/*       */         break label408; }
/*  1139 */     this.chkCommandConsole.setEnabled(false);
/*  1140 */     this.chkCommandConsole.setSelected(false);
/*       */     label408:
/*  1142 */     if ((this.CurMech.CanUseFHES()) && (CommonTools.IsAllowed(this.CurMech.GetFHESAC(), this.CurMech))) {
/*  1143 */       this.chkFHES.setEnabled(true);
/*       */     } else {
/*  1145 */       this.chkFHES.setEnabled(false);
/*  1146 */       this.chkFHES.setSelected(false);
/*       */     }
/*  1148 */     if (this.CurMech.GetCockpit().IsTorsoMounted())
/*       */     {
/*  1150 */       this.chkEjectionSeat.setEnabled(false);
/*  1151 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*  1153 */     if (this.CurMech.IsIndustrialmech()) {
/*  1154 */       this.chkEjectionSeat.setEnabled(true);
/*       */     } else {
/*  1156 */       this.chkEjectionSeat.setEnabled(false);
/*  1157 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/*  1161 */     if (CommonTools.IsAllowed(this.CurMech.GetNullSig().GetAvailability(), this.CurMech)) {
/*  1162 */       this.chkNullSig.setEnabled(true);
/*       */     } else {
/*  1164 */       this.chkNullSig.setEnabled(false);
/*  1165 */       this.chkNullSig.setSelected(false);
/*       */     }
/*  1167 */     if (CommonTools.IsAllowed(this.CurMech.GetVoidSig().GetAvailability(), this.CurMech)) {
/*  1168 */       this.chkVoidSig.setEnabled(true);
/*       */     } else {
/*  1170 */       this.chkVoidSig.setEnabled(false);
/*  1171 */       this.chkVoidSig.setSelected(false);
/*       */     }
/*  1173 */     if (CommonTools.IsAllowed(this.CurMech.GetChameleon().GetAvailability(), this.CurMech)) {
/*  1174 */       this.chkCLPS.setEnabled(true);
/*       */     } else {
/*  1176 */       this.chkCLPS.setEnabled(false);
/*  1177 */       this.chkCLPS.setSelected(false);
/*       */     }
/*  1179 */     if (CommonTools.IsAllowed(this.CurMech.GetBlueShield().GetAvailability(), this.CurMech)) {
/*  1180 */       this.chkBSPFD.setEnabled(true);
/*       */     } else {
/*  1182 */       this.chkBSPFD.setEnabled(false);
/*  1183 */       this.chkBSPFD.setSelected(false);
/*       */     }
/*  1185 */     if (this.CurMech.IsIndustrialmech()) {
/*  1186 */       this.chkEnviroSealing.setEnabled(true);
/*  1187 */       this.chkEjectionSeat.setEnabled(true);
/*       */     } else {
/*  1189 */       this.chkEnviroSealing.setEnabled(false);
/*  1190 */       this.chkEjectionSeat.setEnabled(false);
/*  1191 */       this.chkEnviroSealing.setSelected(false);
/*  1192 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*  1194 */     if (CommonTools.IsAllowed(this.CurMech.GetTracks().GetAvailability(), this.CurMech)) {
/*  1195 */       this.chkTracks.setEnabled(true);
/*       */     } else {
/*  1197 */       this.chkTracks.setEnabled(false);
/*  1198 */       this.chkTracks.setSelected(false);
/*       */     }
/*  1200 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetSupercharger().GetAvailability(), this.CurMech)) {
/*  1201 */       this.chkSupercharger.setEnabled(true);
/*  1202 */       this.cmbSCLoc.setEnabled(true);
/*  1203 */       this.lblSupercharger.setEnabled(true);
/*       */     } else {
/*  1205 */       this.chkSupercharger.setEnabled(false);
/*  1206 */       this.cmbSCLoc.setEnabled(false);
/*  1207 */       this.lblSupercharger.setEnabled(false);
/*       */     }
/*  1209 */     if ((CommonTools.IsAllowed(this.PWAC, this.CurMech) & !this.CurMech.IsOmnimech())) {
/*  1210 */       this.chkPartialWing.setEnabled(true);
/*       */     } else {
/*  1212 */       this.chkPartialWing.setEnabled(false);
/*       */     }
/*  1214 */     this.chkPartialWing.setSelected(this.CurMech.UsingPartialWing());
/*  1215 */     if ((CommonTools.IsAllowed(this.CurMech.GetJumpBooster().GetAvailability(), this.CurMech) & !this.CurMech.IsOmnimech())) {
/*  1216 */       this.chkBoosters.setEnabled(true);
/*  1217 */       FixJumpBoosterSpinnerModel();
/*       */     } else {
/*       */       try {
/*  1220 */         this.CurMech.SetJumpBooster(false);
/*       */       }
/*       */       catch (Exception e) {
/*  1223 */         System.err.println("Could not remove Jump Booster!");
/*       */       }
/*  1225 */       this.chkBoosters.setEnabled(false);
/*  1226 */       this.chkBoosters.setSelected(false);
/*  1227 */       FixJumpBoosterSpinnerModel();
/*       */     }
/*  1229 */     this.chkBoosters.setSelected(this.CurMech.UsingJumpBooster());
/*  1230 */     if (this.CurMech.UsingJumpBooster()) {
/*  1231 */       this.spnBoosterMP.setEnabled(true);
/*       */     } else {
/*  1233 */       this.spnBoosterMP.setEnabled(false);
/*       */     }
/*  1235 */     if (CommonTools.IsAllowed(this.CurMech.GetLLAES().GetAvailability(), this.CurMech)) {
/*  1236 */       this.chkRAAES.setEnabled(true);
/*  1237 */       this.chkLAAES.setEnabled(true);
/*  1238 */       this.chkLegAES.setEnabled(true);
/*       */     } else {
/*  1240 */       this.chkRAAES.setSelected(false);
/*  1241 */       this.chkLAAES.setSelected(false);
/*  1242 */       this.chkLegAES.setSelected(false);
/*  1243 */       this.chkRAAES.setEnabled(false);
/*  1244 */       this.chkLAAES.setEnabled(false);
/*  1245 */       this.chkLegAES.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1249 */     this.chkBoobyTrap.setSelected(false);
/*  1250 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetBoobyTrap().GetAvailability(), this.CurMech)) {
/*  1251 */       this.chkBoobyTrap.setEnabled(true);
/*  1252 */       if (this.CurMech.GetLoadout().HasBoobyTrap()) this.chkBoobyTrap.setSelected(true);
/*       */     } else {
/*  1254 */       this.chkBoobyTrap.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1259 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetCTCaseII().GetAvailability(), this.CurMech)) {
/*  1260 */       this.chkHDCASE2.setEnabled(true);
/*  1261 */       this.chkCTCASE2.setEnabled(true);
/*  1262 */       this.chkLTCASE2.setEnabled(true);
/*  1263 */       this.chkRTCASE2.setEnabled(true);
/*  1264 */       this.chkLACASE2.setEnabled(true);
/*  1265 */       this.chkRACASE2.setEnabled(true);
/*  1266 */       this.chkLLCASE2.setEnabled(true);
/*  1267 */       this.chkRLCASE2.setEnabled(true);
/*       */     } else {
/*       */       try {
/*  1270 */         this.chkHDCASE2.setEnabled(false);
/*  1271 */         this.chkHDCASE2.setSelected(false);
/*  1272 */         this.CurMech.GetLoadout().SetHDCASEII(false, -1, false);
/*  1273 */         this.chkCTCASE2.setEnabled(false);
/*  1274 */         this.chkCTCASE2.setSelected(false);
/*  1275 */         this.CurMech.GetLoadout().SetCTCASEII(false, -1, false);
/*  1276 */         this.chkLTCASE2.setEnabled(false);
/*  1277 */         this.chkLTCASE2.setSelected(false);
/*  1278 */         this.CurMech.GetLoadout().SetLTCASEII(false, -1, false);
/*  1279 */         this.chkRTCASE2.setEnabled(false);
/*  1280 */         this.chkRTCASE2.setSelected(false);
/*  1281 */         this.CurMech.GetLoadout().SetRTCASEII(false, -1, false);
/*  1282 */         this.chkLACASE2.setEnabled(false);
/*  1283 */         this.chkLACASE2.setSelected(false);
/*  1284 */         this.CurMech.GetLoadout().SetLACASEII(false, -1, false);
/*  1285 */         this.chkRACASE2.setEnabled(false);
/*  1286 */         this.chkRACASE2.setSelected(false);
/*  1287 */         this.CurMech.GetLoadout().SetRACASEII(false, -1, false);
/*  1288 */         this.chkLLCASE2.setEnabled(false);
/*  1289 */         this.chkLLCASE2.setSelected(false);
/*  1290 */         this.CurMech.GetLoadout().SetLLCASEII(false, -1, false);
/*  1291 */         this.chkRLCASE2.setEnabled(false);
/*  1292 */         this.chkRLCASE2.setSelected(false);
/*  1293 */         this.CurMech.GetLoadout().SetRLCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/*  1296 */         System.err.println(e.getMessage());
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1301 */     if (this.CurMech.GetLoadout().GetTechBase() > 0) {
/*  1302 */       this.chkClanCASE.setEnabled(true);
/*       */     } else {
/*  1304 */       this.CurMech.GetLoadout().SetClanCASE(false);
/*  1305 */       this.chkClanCASE.setSelected(false);
/*  1306 */       this.chkClanCASE.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1310 */     if (CommonTools.IsAllowed(this.CurMech.GetLoadout().GetHDTurret().GetAvailability(), this.CurMech)) {
/*  1311 */       if (this.CurMech.GetLoadout().CanUseHDTurret()) {
/*  1312 */         this.chkHDTurret.setEnabled(true);
/*       */       } else {
/*  1314 */         this.chkHDTurret.setSelected(false);
/*  1315 */         this.chkHDTurret.setEnabled(false);
/*       */       }
/*  1317 */       if (this.CurMech.GetLoadout().CanUseLTTurret()) {
/*  1318 */         this.chkLTTurret.setEnabled(true);
/*       */       } else {
/*  1320 */         this.chkLTTurret.setSelected(false);
/*  1321 */         this.chkLTTurret.setEnabled(false);
/*       */       }
/*  1323 */       if (this.CurMech.GetLoadout().CanUseRTTurret()) {
/*  1324 */         this.chkRTTurret.setEnabled(true);
/*       */       } else {
/*  1326 */         this.chkRTTurret.setSelected(false);
/*  1327 */         this.chkRTTurret.setEnabled(false);
/*       */       }
/*       */     } else {
/*  1330 */       this.chkHDTurret.setSelected(false);
/*  1331 */       this.chkHDTurret.setEnabled(false);
/*  1332 */       this.chkLTTurret.setSelected(false);
/*  1333 */       this.chkLTTurret.setEnabled(false);
/*  1334 */       this.chkRTTurret.setSelected(false);
/*  1335 */       this.chkRTTurret.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/*  1339 */     if (!this.chkFCSAIV.isEnabled()) {
/*       */       try {
/*  1341 */         this.CurMech.SetFCSArtemisIV(false);
/*       */       } catch (Exception e) {
/*  1343 */         Media.Messager(this, e.getMessage());
/*       */       }
/*  1345 */       this.chkFCSAIV.setSelected(false);
/*       */     }
/*  1347 */     else if (this.CurMech.UsingArtemisIV()) {
/*  1348 */       this.chkFCSAIV.setSelected(true);
/*       */     } else {
/*  1350 */       this.chkFCSAIV.setSelected(false);
/*       */     }
/*       */     
/*  1353 */     if (!this.chkFCSAV.isEnabled()) {
/*       */       try {
/*  1355 */         this.CurMech.SetFCSArtemisV(false);
/*       */       } catch (Exception e) {
/*  1357 */         Media.Messager(this, e.getMessage());
/*       */       }
/*  1359 */       this.chkFCSAV.setSelected(false);
/*       */     }
/*  1361 */     else if (this.CurMech.UsingArtemisV()) {
/*  1362 */       this.chkFCSAV.setSelected(true);
/*       */     } else {
/*  1364 */       this.chkFCSAV.setSelected(false);
/*       */     }
/*       */     
/*  1367 */     if (!this.chkFCSApollo.isEnabled()) {
/*       */       try {
/*  1369 */         this.CurMech.SetFCSApollo(false);
/*       */       } catch (Exception e) {
/*  1371 */         Media.Messager(this, e.getMessage());
/*       */       }
/*  1373 */       this.chkFCSApollo.setSelected(false);
/*       */     }
/*  1375 */     else if (this.CurMech.UsingApollo()) {
/*  1376 */       this.chkFCSApollo.setSelected(true);
/*       */     } else {
/*  1378 */       this.chkFCSApollo.setSelected(false);
/*       */     }
/*       */     
/*  1381 */     if (!this.chkSupercharger.isEnabled()) {
/*       */       try {
/*  1383 */         this.CurMech.GetLoadout().SetSupercharger(false, 0, -1);
/*       */       } catch (Exception e) {
/*  1385 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */       
/*  1388 */     } else if (this.CurMech.GetLoadout().HasSupercharger()) {
/*  1389 */       this.chkSupercharger.setSelected(true);
/*       */     } else {
/*  1391 */       this.chkSupercharger.setSelected(false);
/*       */     }
/*       */     
/*  1394 */     if (!this.chkHDTurret.isEnabled()) {
/*       */       try {
/*  1396 */         this.CurMech.GetLoadout().SetHDTurret(false, -1);
/*       */       } catch (Exception e) {
/*  1398 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */       
/*  1401 */     } else if (this.CurMech.GetLoadout().HasHDTurret()) {
/*  1402 */       this.chkHDTurret.setSelected(true);
/*       */     } else {
/*  1404 */       this.chkHDTurret.setSelected(false);
/*       */     }
/*       */     
/*  1407 */     if (!this.chkLTTurret.isEnabled()) {
/*       */       try {
/*  1409 */         this.CurMech.GetLoadout().SetLTTurret(false, -1);
/*       */       } catch (Exception e) {
/*  1411 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */       
/*  1414 */     } else if (this.CurMech.GetLoadout().HasLTTurret()) {
/*  1415 */       this.chkLTTurret.setSelected(true);
/*       */     } else {
/*  1417 */       this.chkLTTurret.setSelected(false);
/*       */     }
/*       */     
/*  1420 */     if (!this.chkRTTurret.isEnabled()) {
/*       */       try {
/*  1422 */         this.CurMech.GetLoadout().SetRTTurret(false, -1);
/*       */       } catch (Exception e) {
/*  1424 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */       
/*  1427 */     } else if (this.CurMech.GetLoadout().HasRTTurret()) {
/*  1428 */       this.chkRTTurret.setSelected(true);
/*       */     } else {
/*  1430 */       this.chkRTTurret.setSelected(false);
/*       */     }
/*       */     
/*  1433 */     if (!this.chkUseTC.isEnabled()) this.CurMech.UseTC(false, false);
/*  1434 */     if (!this.chkCTCASE.isEnabled()) this.CurMech.RemoveCTCase();
/*  1435 */     if (!this.chkLTCASE.isEnabled()) this.CurMech.RemoveLTCase();
/*  1436 */     if (!this.chkRTCASE.isEnabled()) this.CurMech.RemoveRTCase();
/*  1437 */     this.chkClanCASE.setSelected(this.CurMech.GetLoadout().IsUsingClanCASE());
/*       */     
/*  1439 */     if (this.CurMech.GetRulesLevel() >= 3) {
/*  1440 */       this.chkFractional.setEnabled(true);
/*       */     } else {
/*  1442 */       this.chkFractional.setEnabled(false);
/*  1443 */       this.CurMech.SetFractionalAccounting(false);
/*       */     }
/*  1445 */     this.chkFractional.setSelected(this.CurMech.UsingFractionalAccounting());
/*       */     
/*  1447 */     if (this.CurMech.IsOmnimech())
/*       */     {
/*       */ 
/*  1450 */       this.chkNullSig.setEnabled(false);
/*  1451 */       this.chkVoidSig.setEnabled(false);
/*  1452 */       this.chkBSPFD.setEnabled(false);
/*  1453 */       this.chkCLPS.setEnabled(false);
/*  1454 */       this.chkEnviroSealing.setEnabled(false);
/*  1455 */       this.chkEjectionSeat.setEnabled(false);
/*  1456 */       this.chkRAAES.setEnabled(false);
/*  1457 */       this.chkLAAES.setEnabled(false);
/*  1458 */       this.chkLegAES.setEnabled(false);
/*  1459 */       this.chkCommandConsole.setEnabled(false);
/*  1460 */       this.chkFHES.setEnabled(false);
/*  1461 */       this.chkTracks.setEnabled(false);
/*       */       
/*       */ 
/*  1464 */       if (this.CurMech.GetBaseLoadout().HasSupercharger()) {
/*  1465 */         this.chkSupercharger.setEnabled(false);
/*  1466 */         this.cmbSCLoc.setEnabled(false);
/*  1467 */         this.lblSupercharger.setEnabled(false);
/*       */       }
/*       */       
/*       */ 
/*  1471 */       if (this.CurMech.GetBaseLoadout().HasCTCASE()) {
/*  1472 */         this.chkCTCASE.setEnabled(false);
/*       */       }
/*  1474 */       if (this.CurMech.GetBaseLoadout().HasLTCASE()) {
/*  1475 */         this.chkLTCASE.setEnabled(false);
/*       */       }
/*  1477 */       if (this.CurMech.GetBaseLoadout().HasRTCASE()) {
/*  1478 */         this.chkRTCASE.setEnabled(false);
/*       */       }
/*       */       
/*  1481 */       if (this.CurMech.GetBaseLoadout().HasHDCASEII()) {
/*  1482 */         this.chkHDCASE2.setEnabled(false);
/*       */       }
/*  1484 */       if (this.CurMech.GetBaseLoadout().HasCTCASEII()) {
/*  1485 */         this.chkCTCASE2.setEnabled(false);
/*       */       }
/*  1487 */       if (this.CurMech.GetBaseLoadout().HasLTCASEII()) {
/*  1488 */         this.chkLTCASE2.setEnabled(false);
/*       */       }
/*  1490 */       if (this.CurMech.GetBaseLoadout().HasRTCASEII()) {
/*  1491 */         this.chkRTCASE2.setEnabled(false);
/*       */       }
/*  1493 */       if (this.CurMech.GetBaseLoadout().HasLACASEII()) {
/*  1494 */         this.chkLACASE2.setEnabled(false);
/*       */       }
/*  1496 */       if (this.CurMech.GetBaseLoadout().HasRACASEII()) {
/*  1497 */         this.chkRACASE2.setEnabled(false);
/*       */       }
/*  1499 */       if (this.CurMech.GetBaseLoadout().HasLLCASEII()) {
/*  1500 */         this.chkLLCASE2.setEnabled(false);
/*       */       }
/*  1502 */       if (this.CurMech.GetBaseLoadout().HasRLCASEII()) {
/*  1503 */         this.chkRLCASE2.setEnabled(false);
/*       */       }
/*  1505 */       if (this.CurMech.GetBaseLoadout().HasHDTurret()) {
/*  1506 */         this.chkHDTurret.setEnabled(false);
/*       */       }
/*  1508 */       if (this.CurMech.GetBaseLoadout().HasLTTurret()) {
/*  1509 */         this.chkLTTurret.setEnabled(false);
/*       */       }
/*  1511 */       if (this.CurMech.GetBaseLoadout().HasRTTurret()) {
/*  1512 */         this.chkRTTurret.setEnabled(false);
/*       */       }
/*       */     } else {
/*       */       try {
/*  1516 */         if (!this.chkNullSig.isEnabled()) this.CurMech.SetNullSig(false);
/*  1517 */         if (!this.chkVoidSig.isEnabled()) this.CurMech.SetVoidSig(false);
/*  1518 */         if (!this.chkBSPFD.isEnabled()) this.CurMech.SetBlueShield(false);
/*  1519 */         if (!this.chkCLPS.isEnabled()) this.CurMech.SetChameleon(false);
/*  1520 */         if (!this.chkEnviroSealing.isEnabled()) this.CurMech.SetEnviroSealing(false);
/*  1521 */         if (!this.chkLegAES.isEnabled()) this.CurMech.SetLegAES(false, null);
/*  1522 */         if (!this.chkRAAES.isEnabled()) this.CurMech.SetRAAES(false, -1);
/*  1523 */         if (!this.chkLAAES.isEnabled()) this.CurMech.SetLAAES(false, -1);
/*  1524 */         if (!this.chkCommandConsole.isEnabled()) this.CurMech.SetCommandConsole(false);
/*  1525 */         if (!this.chkFHES.isEnabled()) this.CurMech.SetFHES(false);
/*  1526 */         if (!this.chkTracks.isEnabled()) this.CurMech.SetTracks(false);
/*       */       }
/*       */       catch (Exception e) {
/*  1529 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcGyro()
/*       */   {
/*  1536 */     String OldVal = BuildLookupName(this.CurMech.GetGyro().GetCurrentState());
/*  1537 */     String LookupVal = (String)this.cmbGyroType.getSelectedItem();
/*  1538 */     if (OldVal.equals(LookupVal)) return;
/*  1539 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1541 */       this.CurMech.Visit(v);
/*  1542 */       if ((this.CurMech.GetCockpit().RequiresGyro()) && (this.CurMech.GetGyro().NumCrits() == 0))
/*  1543 */         throw new Exception("The selected cockpit requires a gyro.");
/*       */     } catch (Exception e) {
/*  1545 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1547 */         Media.Messager(this, "The new gyro type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous gyro type.");
/*  1548 */         this.CurMech.Visit(v);
/*  1549 */         this.cmbGyroType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1552 */         Media.Messager(this, "Fatal error while attempting to revert to the old gyro type:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1553 */         GetNewMech();
/*       */       }
/*       */     } }
/*       */   
/*       */   private JButton btnExportTextIcon;
/*       */   private JButton btnForceList;
/*       */   private JButton btnLoadImage;
/*       */   private JButton btnLockChassis;
/*  1561 */   private void RecalcCockpit() { String OldVal = this.CurMech.GetCockpit().LookupName();
/*  1562 */     String LookupVal = (String)this.cmbCockpitType.getSelectedItem();
/*  1563 */     if (OldVal.equals(LookupVal)) return;
/*  1564 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try
/*       */     {
/*  1567 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1569 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1571 */         Media.Messager(this, "The new cockpit type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous cockpit type.");
/*  1572 */         this.CurMech.Visit(v);
/*  1573 */         this.cmbCockpitType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1576 */         Media.Messager(this, "Fatal error while attempting to revert to the old cockpit type:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1577 */         GetNewMech();
/*       */       }
/*       */     }
/*       */     
/*  1581 */     if (!this.CurMech.GetGyro().LookupName().equals(this.cmbGyroType.getSelectedItem().toString())) {
/*  1582 */       this.cmbGyroType.setSelectedItem(this.CurMech.GetGyro().LookupName());
/*       */     }
/*       */     
/*  1585 */     if ((this.CurMech.GetCockpit().CanUseCommandConsole()) && (CommonTools.IsAllowed(this.CurMech.GetCommandConsole().GetAvailability(), this.CurMech))) {
/*  1586 */       this.chkCommandConsole.setEnabled(true);
/*  1587 */       this.chkCommandConsole.setSelected(this.CurMech.HasCommandConsole());
/*       */     } else {
/*  1589 */       this.chkCommandConsole.setEnabled(false);
/*  1590 */       this.chkCommandConsole.setSelected(false);
/*       */     }
/*  1592 */     if ((this.CurMech.CanUseFHES()) && (CommonTools.IsAllowed(this.CurMech.GetFHESAC(), this.CurMech))) {
/*  1593 */       this.chkFHES.setEnabled(true);
/*       */     } else {
/*  1595 */       this.chkFHES.setSelected(false);
/*  1596 */       this.chkFHES.setSelected(false);
/*       */     }
/*  1598 */     if (this.CurMech.GetCockpit().IsTorsoMounted())
/*       */     {
/*  1600 */       this.chkEjectionSeat.setEnabled(false);
/*  1601 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*  1603 */     if (this.CurMech.IsIndustrialmech()) {
/*  1604 */       this.chkEjectionSeat.setEnabled(true);
/*       */     } else {
/*  1606 */       this.chkEjectionSeat.setEnabled(false);
/*  1607 */       this.chkEjectionSeat.setSelected(false);
/*       */     }
/*       */     
/*  1610 */     if (this.CurMech.GetLoadout().HasHDTurret()) {
/*       */       try {
/*  1612 */         this.CurMech.GetLoadout().SetHDTurret(false, -1);
/*       */       } catch (Exception e) {
/*  1614 */         Media.Messager("Fatal error trying to remove head turret.\nRestarting with new 'Mech.  Sorry.");
/*  1615 */         GetNewMech();
/*  1616 */         return;
/*       */       }
/*       */     }
/*  1619 */     this.chkHDTurret.setSelected(false);
/*  1620 */     this.chkHDTurret.setEnabled(false);
/*       */   }
/*       */   
/*       */   private void RecalcEnhancements()
/*       */   {
/*  1625 */     String OldVal = BuildLookupName(this.CurMech.GetPhysEnhance().GetCurrentState());
/*  1626 */     String LookupVal = (String)this.cmbPhysEnhance.getSelectedItem();
/*  1627 */     if (OldVal.equals(LookupVal)) return;
/*  1628 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1630 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1632 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1634 */         Media.Messager(this, "The new enhancement type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous enhancement.");
/*  1635 */         this.CurMech.Visit(v);
/*  1636 */         this.cmbPhysEnhance.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1639 */         Media.Messager(this, "Fatal error while attempting to revert to the old enhancement:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1640 */         GetNewMech();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcJumpJets()
/*       */   {
/*  1647 */     String OldVal = this.CurMech.GetJumpJets().LookupName();
/*  1648 */     String LookupVal = (String)this.cmbJumpJetType.getSelectedItem();
/*  1649 */     if (LookupVal == null) return;
/*  1650 */     if (OldVal.equals(LookupVal)) return;
/*  1651 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1653 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1655 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1657 */         Media.Messager(this, "The new jump jet type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous jump jet type.");
/*  1658 */         this.CurMech.Visit(v);
/*  1659 */         this.cmbJumpJetType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1662 */         Media.Messager(this, "Fatal error while attempting to revert to the old jump jets:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1663 */         GetNewMech();
/*  1664 */         return;
/*       */       }
/*       */     }
/*  1667 */     FixJJSpinnerModel();
/*       */   }
/*       */   
/*       */   private void RecalcHeatSinks()
/*       */   {
/*  1672 */     String OldVal = BuildLookupName(this.CurMech.GetHeatSinks().GetCurrentState());
/*  1673 */     String LookupVal = (String)this.cmbHeatSinkType.getSelectedItem();
/*  1674 */     if (OldVal.equals(LookupVal)) return;
/*  1675 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1677 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1679 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1681 */         Media.Messager(this, "The new heat sink type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous heat sink type.");
/*  1682 */         this.CurMech.Visit(v);
/*  1683 */         this.cmbHeatSinkType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1686 */         Media.Messager(this, "Fatal error while attempting to revert to the old heat sinks:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1687 */         GetNewMech();
/*  1688 */         return;
/*       */       }
/*       */     }
/*  1691 */     FixHeatSinkSpinnerModel();
/*       */   }
/*       */   
/*       */   private void RecalcIntStruc()
/*       */   {
/*  1696 */     String OldVal = BuildLookupName(this.CurMech.GetIntStruc().GetCurrentState());
/*  1697 */     String LookupVal = (String)this.cmbInternalType.getSelectedItem();
/*  1698 */     if (OldVal.equals(LookupVal)) return;
/*  1699 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1701 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1703 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1705 */         Media.Messager(this, "The new internal structure is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous internal structure.");
/*  1706 */         this.CurMech.Visit(v);
/*  1707 */         this.cmbInternalType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1710 */         Media.Messager(this, "Fatal error while attempting to revert to the old internal structure:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1711 */         GetNewMech();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcEngine()
/*       */   {
/*  1718 */     int OldFreeHS = this.CurMech.GetEngine().FreeHeatSinks();
/*       */     
/*       */ 
/*       */ 
/*  1722 */     String OldVal = BuildLookupName(this.CurMech.GetEngine().GetCurrentState());
/*  1723 */     String LookupVal = (String)this.cmbEngineType.getSelectedItem();
/*  1724 */     if (OldVal.equals(LookupVal)) return;
/*  1725 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1727 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1729 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1731 */         Media.Messager(this, "The new engine type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous engine.");
/*  1732 */         this.CurMech.Visit(v);
/*  1733 */         this.cmbEngineType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1736 */         Media.Messager(this, "Fatal error while attempting to revert to the old engine:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1737 */         GetNewMech();
/*  1738 */         return;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1744 */     if (this.CurMech.GetEngine().FreeHeatSinks() != OldFreeHS)
/*       */     {
/*  1746 */       this.CurMech.GetHeatSinks().SetNumHS(this.CurMech.GetEngine().FreeHeatSinks());
/*       */       
/*  1748 */       this.CurMech.GetHeatSinks().ReCalculate();
/*  1749 */       this.spnNumberOfHS.setModel(new SpinnerNumberModel(this.CurMech
/*  1750 */         .GetHeatSinks().GetNumHS(), this.CurMech.GetEngine().FreeHeatSinks(), 65, 1));
/*       */       
/*  1752 */       ((JSpinner.DefaultEditor)this.spnNumberOfHS.getEditor()).getTextField().addFocusListener(this.spinners);
/*       */     }
/*       */     
/*       */ 
/*  1756 */     if (this.CurMech.GetEngine().IsNuclear()) {
/*  1757 */       this.lblSumPAmps.setVisible(false);
/*  1758 */       this.txtSumPAmpsTon.setVisible(false);
/*  1759 */       this.txtSumPAmpsACode.setVisible(false);
/*       */     } else {
/*  1761 */       this.lblSumPAmps.setVisible(true);
/*  1762 */       this.txtSumPAmpsTon.setVisible(true);
/*  1763 */       this.txtSumPAmpsACode.setVisible(true);
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcArmor()
/*       */   {
/*  1769 */     String OldVal = BuildLookupName(this.CurMech.GetArmor().GetCurrentState());
/*  1770 */     String LookupVal = (String)this.cmbArmorType.getSelectedItem();
/*  1771 */     if (OldVal.equals(LookupVal)) return;
/*  1772 */     visitors.ifVisitor v = this.CurMech.Lookup(LookupVal);
/*       */     try {
/*  1774 */       this.CurMech.Visit(v);
/*       */     } catch (Exception e) {
/*  1776 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1778 */         Media.Messager(this, "The new armor type is not valid.  Error:\n" + e.getMessage() + "\nReverting to the previous armor.");
/*  1779 */         this.CurMech.Visit(v);
/*  1780 */         this.cmbArmorType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e1) {
/*  1783 */         Media.Messager(this, "Fatal error while attempting to revert to the old armor:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1784 */         GetNewMech();
/*       */       }
/*       */     }
/*  1787 */     if ((this.CurMech.GetArmor().IsStealth()) && 
/*  1788 */       (!AddECM())) {
/*  1789 */       v = this.CurMech.Lookup(OldVal);
/*       */       try {
/*  1791 */         Media.Messager(this, "No ECM Suite was available for this armor type!\nReverting to the previous armor.");
/*  1792 */         this.CurMech.Visit(v);
/*  1793 */         this.cmbArmorType.setSelectedItem(OldVal);
/*       */       }
/*       */       catch (Exception e) {
/*  1796 */         Media.Messager(this, "Fatal error while attempting to revert to the old armor:\n" + e.getMessage() + "\nStarting over with a new 'Mech.  Sorry.");
/*  1797 */         GetNewMech();
/*       */       }
/*       */     }
/*       */     
/*  1801 */     SetPatchworkArmor();
/*       */   }
/*       */   
/*       */   private void SetPatchworkArmor() {
/*  1805 */     if (this.CurMech.GetArmor().IsPatchwork()) {
/*  1806 */       this.pnlPatchworkChoosers.setVisible(true);
/*  1807 */       BuildPatchworkChoosers();
/*  1808 */       if (this.CurMech.IsQuad()) {
/*  1809 */         this.lblPWLALoc.setText("FLL Armor: ");
/*  1810 */         this.lblPWRALoc.setText("FRL Armor: ");
/*  1811 */         this.lblPWLLLoc.setText("RLL Armor: ");
/*  1812 */         this.lblPWRLLoc.setText("RRL Armor: ");
/*       */       } else {
/*  1814 */         this.lblPWLALoc.setText("LA Armor: ");
/*  1815 */         this.lblPWRALoc.setText("RA Armor: ");
/*  1816 */         this.lblPWLLLoc.setText("LL Armor: ");
/*  1817 */         this.lblPWRLLoc.setText("RL Armor: ");
/*       */       }
/*  1819 */       this.cmbPWHDType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetHDArmorType()));
/*  1820 */       this.cmbPWCTType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetCTArmorType()));
/*  1821 */       this.cmbPWLTType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetLTArmorType()));
/*  1822 */       this.cmbPWRTType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetRTArmorType()));
/*  1823 */       this.cmbPWLAType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetLAArmorType()));
/*  1824 */       this.cmbPWRAType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetRAArmorType()));
/*  1825 */       this.cmbPWLLType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetLLArmorType()));
/*  1826 */       this.cmbPWRLType.setSelectedItem(BuildLookupName((ifState)this.CurMech.GetArmor().GetRLArmorType()));
/*       */     } else {
/*  1828 */       this.pnlPatchworkChoosers.setVisible(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private void RecalcPatchworkArmor(int Loc) {
/*  1833 */     visitors.VArmorSetPatchworkLocation LCVis = new visitors.VArmorSetPatchworkLocation();
/*  1834 */     LCVis.SetLocation(Loc);
/*  1835 */     if (this.CurMech.GetBaseTechbase() == 1) {
/*  1836 */       LCVis.SetClan(false);
/*       */     }
/*  1838 */     switch (Loc) {
/*       */     case 0: 
/*  1840 */       LCVis.SetPatchworkType((String)this.cmbPWHDType.getSelectedItem());
/*  1841 */       break;
/*       */     case 1: 
/*  1843 */       LCVis.SetPatchworkType((String)this.cmbPWCTType.getSelectedItem());
/*  1844 */       break;
/*       */     case 2: 
/*  1846 */       LCVis.SetPatchworkType((String)this.cmbPWLTType.getSelectedItem());
/*  1847 */       break;
/*       */     case 3: 
/*  1849 */       LCVis.SetPatchworkType((String)this.cmbPWRTType.getSelectedItem());
/*  1850 */       break;
/*       */     case 4: 
/*  1852 */       LCVis.SetPatchworkType((String)this.cmbPWLAType.getSelectedItem());
/*  1853 */       break;
/*       */     case 5: 
/*  1855 */       LCVis.SetPatchworkType((String)this.cmbPWRAType.getSelectedItem());
/*  1856 */       break;
/*       */     case 6: 
/*  1858 */       LCVis.SetPatchworkType((String)this.cmbPWLLType.getSelectedItem());
/*  1859 */       break;
/*       */     case 7: 
/*  1861 */       LCVis.SetPatchworkType((String)this.cmbPWRLType.getSelectedItem());
/*       */     }
/*       */     try
/*       */     {
/*  1865 */       LCVis.Visit(this.CurMech);
/*       */     } catch (Exception e) {
/*  1867 */       Media.Messager(this, e.getMessage());
/*  1868 */       switch (Loc) {
/*       */       case 0: 
/*  1870 */         this.cmbPWHDType.setSelectedItem(this.CurMech.GetArmor().GetHDArmorType().LookupName());
/*       */       }
/*       */     }
/*  1873 */     this.cmbPWCTType.setSelectedItem(this.CurMech.GetArmor().GetCTArmorType().LookupName());
/*  1874 */     return;
/*       */     
/*  1876 */     this.cmbPWLTType.setSelectedItem(this.CurMech.GetArmor().GetLTArmorType().LookupName());
/*  1877 */     return;
/*       */     
/*  1879 */     this.cmbPWRTType.setSelectedItem(this.CurMech.GetArmor().GetRTArmorType().LookupName());
/*  1880 */     return;
/*       */     
/*  1882 */     this.cmbPWLAType.setSelectedItem(this.CurMech.GetArmor().GetLAArmorType().LookupName());
/*  1883 */     return;
/*       */     
/*  1885 */     this.cmbPWRAType.setSelectedItem(this.CurMech.GetArmor().GetRAArmorType().LookupName());
/*  1886 */     return;
/*       */     
/*  1888 */     this.cmbPWLLType.setSelectedItem(this.CurMech.GetArmor().GetLLArmorType().LookupName());
/*  1889 */     return;
/*       */     
/*  1891 */     this.cmbPWRLType.setSelectedItem(this.CurMech.GetArmor().GetRLArmorType().LookupName());
/*       */   }
/*       */   
/*       */   private JButton btnMaxArmor;
/*       */   private JButton btnNewIcon;
/*       */   private JButton btnOpen;
/*       */   private JButton btnOptionsIcon;
/*       */   
/*  1899 */   private void RecalcEquipment() { boolean clan = false;
/*  1900 */     switch (this.CurMech.GetTechbase())
/*       */     {
/*       */     case 1: 
/*       */     case 2: 
/*  1904 */       clan = true;
/*       */     }
/*  1906 */     if (this.chkCTCASE.isSelected()) {
/*       */       try {
/*  1908 */         this.CurMech.AddCTCase();
/*       */       } catch (Exception e) {
/*  1910 */         Media.Messager(this, e.getMessage());
/*  1911 */         this.chkCTCASE.setSelected(false);
/*       */       }
/*       */     }
/*  1914 */     if (this.chkLTCASE.isSelected()) {
/*       */       try {
/*  1916 */         this.CurMech.AddLTCase();
/*       */       } catch (Exception e) {
/*  1918 */         Media.Messager(this, e.getMessage());
/*  1919 */         this.chkLTCASE.setSelected(false);
/*       */       }
/*       */     }
/*  1922 */     if (this.chkRTCASE.isSelected()) {
/*       */       try {
/*  1924 */         this.CurMech.AddRTCase();
/*       */       } catch (Exception e) {
/*  1926 */         Media.Messager(this, e.getMessage());
/*  1927 */         this.chkRTCASE.setSelected(false);
/*       */       }
/*       */     }
/*       */     
/*  1931 */     if (this.chkHDCASE2.isSelected()) {
/*       */       try {
/*  1933 */         if (!this.CurMech.GetLoadout().HasHDCASEII()) {
/*  1934 */           this.CurMech.GetLoadout().SetHDCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1937 */         Media.Messager(this, e.getMessage());
/*  1938 */         this.chkHDCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1941 */     if (this.chkCTCASE2.isSelected()) {
/*       */       try {
/*  1943 */         if (!this.CurMech.GetLoadout().HasCTCASEII()) {
/*  1944 */           this.CurMech.GetLoadout().SetCTCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1947 */         Media.Messager(this, e.getMessage());
/*  1948 */         this.chkCTCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1951 */     if (this.chkLTCASE2.isSelected()) {
/*       */       try {
/*  1953 */         if (!this.CurMech.GetLoadout().HasLTCASEII()) {
/*  1954 */           this.CurMech.GetLoadout().SetLTCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1957 */         Media.Messager(this, e.getMessage());
/*  1958 */         this.chkLTCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1961 */     if (this.chkRTCASE2.isSelected()) {
/*       */       try {
/*  1963 */         if (!this.CurMech.GetLoadout().HasRTCASEII()) {
/*  1964 */           this.CurMech.GetLoadout().SetRTCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1967 */         Media.Messager(this, e.getMessage());
/*  1968 */         this.chkRTCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1971 */     if (this.chkLACASE2.isSelected()) {
/*       */       try {
/*  1973 */         if (!this.CurMech.GetLoadout().HasLACASEII()) {
/*  1974 */           this.CurMech.GetLoadout().SetLACASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1977 */         Media.Messager(this, e.getMessage());
/*  1978 */         this.chkLACASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1981 */     if (this.chkRACASE2.isSelected()) {
/*       */       try {
/*  1983 */         if (!this.CurMech.GetLoadout().HasRACASEII()) {
/*  1984 */           this.CurMech.GetLoadout().SetRACASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1987 */         Media.Messager(this, e.getMessage());
/*  1988 */         this.chkRACASE2.setSelected(false);
/*       */       }
/*       */     }
/*  1991 */     if (this.chkLLCASE2.isSelected()) {
/*       */       try {
/*  1993 */         if (!this.CurMech.GetLoadout().HasLLCASEII()) {
/*  1994 */           this.CurMech.GetLoadout().SetLLCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  1997 */         Media.Messager(this, e.getMessage());
/*  1998 */         this.chkLLCASE2.setSelected(false);
/*       */       }
/*       */     }
/*  2001 */     if (this.chkRLCASE2.isSelected()) {
/*       */       try {
/*  2003 */         if (!this.CurMech.GetLoadout().HasRLCASEII()) {
/*  2004 */           this.CurMech.GetLoadout().SetRLCASEII(true, -1, clan);
/*       */         }
/*       */       } catch (Exception e) {
/*  2007 */         Media.Messager(this, e.getMessage());
/*  2008 */         this.chkRLCASE2.setSelected(false);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void CheckOmnimech()
/*       */   {
/*  2015 */     if (CommonTools.IsAllowed(this.CurMech.GetOmniMechAvailability(), this.CurMech)) {
/*  2016 */       this.chkOmnimech.setEnabled(true);
/*       */     } else {
/*  2018 */       this.chkOmnimech.setEnabled(false);
/*  2019 */       this.chkOmnimech.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  2024 */     if (this.chkOmnimech.isEnabled()) {
/*  2025 */       if (this.chkOmnimech.isSelected()) {
/*  2026 */         this.btnLockChassis.setEnabled(true);
/*       */       } else {
/*  2028 */         this.btnLockChassis.setEnabled(false);
/*       */       }
/*       */     } else {
/*  2031 */       this.btnLockChassis.setEnabled(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private void SaveOmniFluffInfo() {
/*  2036 */     if (this.SetSource) {
/*  2037 */       this.CurMech.SetSource(this.txtSource.getText());
/*  2038 */       this.CurMech.SetEra(this.cmbMechEra.getSelectedIndex());
/*  2039 */       this.CurMech.SetProductionEra(this.cmbProductionEra.getSelectedIndex());
/*  2040 */       this.CurMech.SetYearRestricted(this.chkYearRestrict.isSelected());
/*       */       try {
/*  2042 */         this.CurMech.SetYear(Integer.parseInt(this.txtProdYear.getText()), this.chkYearRestrict.isSelected());
/*       */       }
/*       */       catch (Exception e) {
/*  2045 */         switch (this.cmbMechEra.getSelectedIndex()) {
/*       */         case 0: 
/*  2047 */           this.CurMech.SetYear(2750, false);
/*       */         }
/*       */       }
/*  2050 */       this.CurMech.SetYear(3025, false);
/*  2051 */       return;
/*       */       
/*  2053 */       this.CurMech.SetYear(3070, false);
/*  2054 */       return;
/*       */       
/*  2056 */       this.CurMech.SetYear(3132, false);
/*  2057 */       return;
/*       */       
/*  2059 */       this.CurMech.SetYear(0, false);
/*       */     } }
/*       */   
/*       */   private JButton btnPostToS7;
/*       */   private JButton btnPrintIcon;
/*       */   private JButton btnPrintPreview;
/*       */   private JButton btnRemainingArmor;
/*       */   private JButton btnRemoveEquip;
/*  2067 */   private void LoadOmniFluffInfo() { this.cmbRulesLevel.setSelectedIndex(this.CurMech.GetRulesLevel());
/*  2068 */     this.cmbMechEra.setSelectedIndex(this.CurMech.GetEra());
/*  2069 */     this.cmbProductionEra.setSelectedIndex(this.CurMech.GetProductionEra());
/*  2070 */     this.txtSource.setText(this.CurMech.GetSource());
/*  2071 */     this.txtProdYear.setText("" + this.CurMech.GetYear());
/*  2072 */     BuildTechBaseSelector();
/*       */   }
/*       */   
/*       */   private void RefreshInternalPoints() {
/*  2076 */     if (this.Prefs.getBoolean("UseMaxArmorInstead", false)) {
/*  2077 */       this.lblHDHeader.setText("Max");
/*  2078 */       this.lblCTHeader.setText("Max");
/*  2079 */       this.lblLTHeader.setText("Max");
/*  2080 */       this.lblRTHeader.setText("Max");
/*  2081 */       this.lblLAHeader.setText("Max");
/*  2082 */       this.lblRAHeader.setText("Max");
/*  2083 */       this.lblLLHeader.setText("Max");
/*  2084 */       this.lblRLHeader.setText("Max");
/*  2085 */       this.lblHDIntPts.setText("" + this.CurMech.GetIntStruc().GetHeadPoints() * 3);
/*  2086 */       this.lblCTIntPts.setText("" + this.CurMech.GetIntStruc().GetCTPoints() * 2);
/*  2087 */       this.lblLTIntPts.setText("" + this.CurMech.GetIntStruc().GetSidePoints() * 2);
/*  2088 */       this.lblRTIntPts.setText("" + this.CurMech.GetIntStruc().GetSidePoints() * 2);
/*  2089 */       this.lblLAIntPts.setText("" + this.CurMech.GetIntStruc().GetArmPoints() * 2);
/*  2090 */       this.lblRAIntPts.setText("" + this.CurMech.GetIntStruc().GetArmPoints() * 2);
/*  2091 */       this.lblLLIntPts.setText("" + this.CurMech.GetIntStruc().GetLegPoints() * 2);
/*  2092 */       this.lblRLIntPts.setText("" + this.CurMech.GetIntStruc().GetLegPoints() * 2);
/*       */     } else {
/*  2094 */       this.lblHDHeader.setText("Internal");
/*  2095 */       this.lblCTHeader.setText("Internal");
/*  2096 */       this.lblLTHeader.setText("Internal");
/*  2097 */       this.lblRTHeader.setText("Internal");
/*  2098 */       this.lblLAHeader.setText("Internal");
/*  2099 */       this.lblRAHeader.setText("Internal");
/*  2100 */       this.lblLLHeader.setText("Internal");
/*  2101 */       this.lblRLHeader.setText("Internal");
/*  2102 */       this.lblHDIntPts.setText("" + this.CurMech.GetIntStruc().GetHeadPoints());
/*  2103 */       this.lblCTIntPts.setText("" + this.CurMech.GetIntStruc().GetCTPoints());
/*  2104 */       this.lblLTIntPts.setText("" + this.CurMech.GetIntStruc().GetSidePoints());
/*  2105 */       this.lblRTIntPts.setText("" + this.CurMech.GetIntStruc().GetSidePoints());
/*  2106 */       this.lblLAIntPts.setText("" + this.CurMech.GetIntStruc().GetArmPoints());
/*  2107 */       this.lblRAIntPts.setText("" + this.CurMech.GetIntStruc().GetArmPoints());
/*  2108 */       this.lblLLIntPts.setText("" + this.CurMech.GetIntStruc().GetLegPoints());
/*  2109 */       this.lblRLIntPts.setText("" + this.CurMech.GetIntStruc().GetLegPoints());
/*       */     }
/*       */   }
/*       */   
/*       */   private void RefreshSummary()
/*       */   {
/*  2115 */     this.txtSumIntTon.setText("" + this.CurMech.GetIntStruc().GetTonnage());
/*  2116 */     this.txtSumEngTon.setText("" + this.CurMech.GetEngine().GetTonnage());
/*  2117 */     this.txtSumGyrTon.setText("" + this.CurMech.GetGyro().GetTonnage());
/*  2118 */     this.txtSumCocTon.setText("" + this.CurMech.GetCockpit().GetTonnage());
/*  2119 */     this.txtSumEnhTon.setText("" + this.CurMech.GetPhysEnhance().GetTonnage());
/*  2120 */     this.txtSumHSTon.setText("" + this.CurMech.GetHeatSinks().GetTonnage());
/*  2121 */     this.txtSumJJTon.setText("" + this.CurMech.GetJumpJets().GetTonnage());
/*  2122 */     this.txtSumArmorTon.setText("" + this.CurMech.GetArmor().GetTonnage());
/*  2123 */     this.txtSumPAmpsTon.setText("" + this.CurMech.GetLoadout().GetPowerAmplifier().GetTonnage());
/*  2124 */     this.txtSumIntCrt.setText("" + this.CurMech.GetIntStruc().NumCrits());
/*  2125 */     this.txtSumEngCrt.setText("" + this.CurMech.GetEngine().ReportCrits());
/*  2126 */     this.txtSumGyrCrt.setText("" + this.CurMech.GetGyro().NumCrits());
/*  2127 */     this.txtSumCocCrt.setText("" + this.CurMech.GetCockpit().ReportCrits());
/*  2128 */     this.txtSumEnhCrt.setText("" + this.CurMech.GetPhysEnhance().NumCrits());
/*  2129 */     this.txtSumHSCrt.setText("" + this.CurMech.GetHeatSinks().NumCrits());
/*  2130 */     this.txtSumJJCrt.setText("" + this.CurMech.GetJumpJets().ReportCrits());
/*  2131 */     this.txtSumArmorCrt.setText("" + this.CurMech.GetArmor().NumCrits());
/*  2132 */     this.txtSumIntACode.setText(this.CurMech.GetIntStruc().GetAvailability().GetBestCombinedCode());
/*  2133 */     this.txtSumEngACode.setText(this.CurMech.GetEngine().GetAvailability().GetBestCombinedCode());
/*  2134 */     this.txtSumGyrACode.setText(this.CurMech.GetGyro().GetAvailability().GetBestCombinedCode());
/*  2135 */     this.txtSumCocACode.setText(this.CurMech.GetCockpit().GetAvailability().GetBestCombinedCode());
/*  2136 */     this.txtSumHSACode.setText(this.CurMech.GetHeatSinks().GetAvailability().GetBestCombinedCode());
/*  2137 */     this.txtSumEnhACode.setText(this.CurMech.GetPhysEnhance().GetAvailability().GetBestCombinedCode());
/*  2138 */     this.txtSumJJACode.setText(this.CurMech.GetJumpJets().GetAvailability().GetBestCombinedCode());
/*  2139 */     this.txtSumPAmpsACode.setText(this.CurMech.GetLoadout().GetPowerAmplifier().GetAvailability().GetBestCombinedCode());
/*       */     
/*       */ 
/*  2142 */     this.lblArmorPoints.setText(this.CurMech.GetArmor().GetArmorValue() + " of " + this.CurMech.GetArmor().GetMaxArmor() + " Armor Points");
/*  2143 */     this.lblArmorCoverage.setText(this.CurMech.GetArmor().GetCoverage() + "% Coverage");
/*  2144 */     this.lblArmorTonsWasted.setText(this.CurMech.GetArmor().GetWastedTonnage() + " Tons Wasted");
/*  2145 */     this.lblAVInLot.setText(this.CurMech.GetArmor().GetWastedAV() + " Points Left In This 1/2 Ton Lot");
/*       */     
/*       */ 
/*  2148 */     battleforce.BattleForceStats bfs = new battleforce.BattleForceStats(this.CurMech);
/*       */     
/*  2150 */     this.lblBFMV.setText(bfs.getMovement());
/*  2151 */     this.lblBFWt.setText(bfs.getWeight() + "");
/*  2152 */     this.lblBFArmor.setText(bfs.getArmor() + "");
/*  2153 */     this.lblBFStructure.setText(bfs.getInternal() + "");
/*  2154 */     this.lblBFPoints.setText("" + bfs.getPointValue());
/*       */     
/*       */ 
/*  2157 */     this.lblBFShort.setText("" + bfs.getShort());
/*  2158 */     this.lblBFMedium.setText("" + bfs.getMedium());
/*  2159 */     this.lblBFLong.setText("" + bfs.getLong());
/*  2160 */     this.lblBFExtreme.setText("" + bfs.getExtreme());
/*  2161 */     this.lblBFOV.setText("" + bfs.getOverheat());
/*       */     
/*  2163 */     this.lblBFSA.setText(bfs.getAbilitiesString());
/*       */     
/*  2165 */     this.jTextAreaBFConversion.setText(bfs.getBFConversionData());
/*       */   }
/*       */   
/*       */ 
/*       */   public void RefreshInfoPane()
/*       */   {
/*  2171 */     if (this.CurMech.GetCurrentTons() > this.CurMech.GetTonnage()) {
/*  2172 */       this.txtInfoTonnage.setForeground(this.RedCol);
/*  2173 */       this.txtInfoFreeTons.setForeground(this.RedCol);
/*       */     } else {
/*  2175 */       this.txtInfoTonnage.setForeground(this.GreenCol);
/*  2176 */       this.txtInfoFreeTons.setForeground(this.GreenCol);
/*       */     }
/*  2178 */     if (this.CurMech.GetLoadout().FreeCrits() - this.CurMech.GetLoadout().UnplacedCrits() < 0) {
/*  2179 */       this.txtInfoFreeCrits.setForeground(this.RedCol);
/*  2180 */       this.txtInfoUnplaced.setForeground(this.RedCol);
/*       */     } else {
/*  2182 */       this.txtInfoFreeCrits.setForeground(this.GreenCol);
/*  2183 */       this.txtInfoUnplaced.setForeground(this.GreenCol);
/*       */     }
/*       */     
/*  2186 */     if (this.CurMech.UsingFractionalAccounting()) {
/*  2187 */       this.txtInfoTonnage.setText("Tons: " + CommonTools.RoundFractionalTons(this.CurMech.GetCurrentTons()));
/*  2188 */       this.txtInfoFreeTons.setText("Free Tons: " + CommonTools.RoundFractionalTons(this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons()));
/*       */     } else {
/*  2190 */       this.txtInfoTonnage.setText("Tons: " + this.CurMech.GetCurrentTons());
/*  2191 */       this.txtInfoFreeTons.setText("Free Tons: " + (this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons()));
/*       */     }
/*  2193 */     this.txtInfoMaxHeat.setText("Max Heat: " + this.CurMech.GetMaxHeat());
/*  2194 */     this.txtInfoHeatDiss.setText("Heat Dissipation: " + this.CurMech.GetHeatSinks().TotalDissipation());
/*  2195 */     this.txtInfoFreeCrits.setText("Free Crits: " + this.CurMech.GetLoadout().FreeCrits());
/*  2196 */     this.txtInfoUnplaced.setText("Unplaced Crits: " + this.CurMech.GetLoadout().UnplacedCrits());
/*  2197 */     this.txtInfoBattleValue.setText("BV: " + String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/*  2198 */     this.txtInfoCost.setText("Cost: " + String.format("%1$,.0f", new Object[] { Double.valueOf(Math.floor(this.CurMech.GetTotalCost() + 0.5D)) }));
/*  2199 */     this.txtEngineRating.setText("" + this.CurMech.GetEngine().GetRating());
/*       */     
/*       */ 
/*  2202 */     String temp = "Max W/R/J/B: ";
/*  2203 */     temp = temp + this.CurMech.GetAdjustedWalkingMP(false, true) + "/";
/*  2204 */     temp = temp + this.CurMech.GetAdjustedRunningMP(false, true) + "/";
/*  2205 */     temp = temp + this.CurMech.GetAdjustedJumpingMP(false) + "/";
/*  2206 */     temp = temp + this.CurMech.GetAdjustedBoosterMP(false);
/*  2207 */     this.lblMoveSummary.setText(temp);
/*       */     
/*       */ 
/*  2210 */     this.lstCritsToPlace.setListData(this.CurMech.GetLoadout().GetQueue().toArray());
/*  2211 */     this.lstCritsToPlace.repaint();
/*       */     
/*       */ 
/*  2214 */     this.lstHDCrits.repaint();
/*  2215 */     this.lstCTCrits.repaint();
/*  2216 */     this.lstLTCrits.repaint();
/*  2217 */     this.lstRTCrits.repaint();
/*  2218 */     this.lstLACrits.repaint();
/*  2219 */     this.lstRACrits.repaint();
/*  2220 */     this.lstLLCrits.repaint();
/*  2221 */     this.lstRLCrits.repaint();
/*  2222 */     this.lstSelectedEquipment.repaint();
/*  2223 */     javax.swing.table.AbstractTableModel m = (javax.swing.table.AbstractTableModel)this.tblWeaponManufacturers.getModel();
/*  2224 */     m.fireTableDataChanged();
/*       */     
/*  2226 */     CheckEquipment();
/*       */     
/*  2228 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   public void QuickSave() {
/*  2232 */     File saveFile = GetSaveFile("ssw", this.Prefs.get("LastOpenDirectory", ""), true, false);
/*  2233 */     if (saveFile != null)
/*       */     {
/*  2235 */       String curLoadout = this.CurMech.GetLoadout().GetName();
/*  2236 */       filehandlers.MechWriter XMLw = new filehandlers.MechWriter(this.CurMech);
/*       */       try {
/*  2238 */         XMLw.WriteXML(saveFile.getCanonicalPath());
/*  2239 */         this.CurMech.SetCurLoadout(curLoadout);
/*       */       }
/*       */       catch (IOException e) {}
/*       */     }
/*       */     else {
/*  2244 */       mnuSaveActionPerformed(null);
/*       */     }
/*       */   }
/*       */   
/*       */   private void CheckEquipment()
/*       */   {
/*  2250 */     if (this.CurMech.UsingArtemisIV()) {
/*  2251 */       this.chkFCSAIV.setSelected(true);
/*       */     } else {
/*  2253 */       this.chkFCSAIV.setSelected(false);
/*       */     }
/*  2255 */     if (this.CurMech.UsingArtemisV()) {
/*  2256 */       this.chkFCSAV.setSelected(true);
/*       */     } else {
/*  2258 */       this.chkFCSAV.setSelected(false);
/*       */     }
/*  2260 */     if (this.CurMech.UsingApollo()) {
/*  2261 */       this.chkFCSApollo.setSelected(true);
/*       */     } else {
/*  2263 */       this.chkFCSApollo.setSelected(false);
/*       */     }
/*       */     
/*  2266 */     if (this.CurMech.UsingTC()) {
/*  2267 */       this.chkUseTC.setSelected(true);
/*       */     } else {
/*  2269 */       this.chkUseTC.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/*  2273 */     if (this.CurMech.HasCTCase()) {
/*  2274 */       this.chkCTCASE.setSelected(true);
/*       */     } else {
/*  2276 */       this.chkCTCASE.setSelected(false);
/*       */     }
/*  2278 */     if (this.CurMech.HasLTCase()) {
/*  2279 */       this.chkLTCASE.setSelected(true);
/*       */     } else {
/*  2281 */       this.chkLTCASE.setSelected(false);
/*       */     }
/*  2283 */     if (this.CurMech.HasRTCase()) {
/*  2284 */       this.chkRTCASE.setSelected(true);
/*       */     } else {
/*  2286 */       this.chkRTCASE.setSelected(false);
/*       */     }
/*       */     
/*  2289 */     if (this.CurMech.GetLoadout().HasHDCASEII()) {
/*  2290 */       this.chkHDCASE2.setSelected(true);
/*       */     } else {
/*  2292 */       this.chkHDCASE2.setSelected(false);
/*       */     }
/*  2294 */     if (this.CurMech.GetLoadout().HasCTCASEII()) {
/*  2295 */       this.chkCTCASE2.setSelected(true);
/*       */     } else {
/*  2297 */       this.chkCTCASE2.setSelected(false);
/*       */     }
/*  2299 */     if (this.CurMech.GetLoadout().HasLTCASEII()) {
/*  2300 */       this.chkLTCASE2.setSelected(true);
/*       */     } else {
/*  2302 */       this.chkLTCASE2.setSelected(false);
/*       */     }
/*  2304 */     if (this.CurMech.GetLoadout().HasRTCASEII()) {
/*  2305 */       this.chkRTCASE2.setSelected(true);
/*       */     } else {
/*  2307 */       this.chkRTCASE2.setSelected(false);
/*       */     }
/*  2309 */     if (this.CurMech.GetLoadout().HasLACASEII()) {
/*  2310 */       this.chkLACASE2.setSelected(true);
/*       */     } else {
/*  2312 */       this.chkLACASE2.setSelected(false);
/*       */     }
/*  2314 */     if (this.CurMech.GetLoadout().HasRACASEII()) {
/*  2315 */       this.chkRACASE2.setSelected(true);
/*       */     } else {
/*  2317 */       this.chkRACASE2.setSelected(false);
/*       */     }
/*  2319 */     if (this.CurMech.GetLoadout().HasLLCASEII()) {
/*  2320 */       this.chkLLCASE2.setSelected(true);
/*       */     } else {
/*  2322 */       this.chkLLCASE2.setSelected(false);
/*       */     }
/*  2324 */     if (this.CurMech.GetLoadout().HasRLCASEII()) {
/*  2325 */       this.chkRLCASE2.setSelected(true);
/*       */     } else {
/*  2327 */       this.chkRLCASE2.setSelected(false);
/*       */     }
/*       */     
/*  2330 */     if (this.CurMech.GetLoadout().HasHDTurret()) {
/*  2331 */       this.chkHDTurret.setSelected(true);
/*       */     } else {
/*  2333 */       this.chkHDTurret.setSelected(false);
/*       */     }
/*  2335 */     if (this.CurMech.GetLoadout().HasLTTurret()) {
/*  2336 */       this.chkLTTurret.setSelected(true);
/*       */     } else {
/*  2338 */       this.chkLTTurret.setSelected(false);
/*       */     }
/*  2340 */     if (this.CurMech.GetLoadout().HasRTTurret()) {
/*  2341 */       this.chkRTTurret.setSelected(true);
/*       */     } else {
/*  2343 */       this.chkRTTurret.setSelected(false);
/*       */     }
/*       */     
/*  2346 */     if (this.CurMech.GetLoadout().HasSupercharger()) {
/*  2347 */       this.chkSupercharger.setSelected(true);
/*  2348 */       this.cmbSCLoc.setSelectedItem(filehandlers.FileCommon.EncodeLocation(this.CurMech.GetLoadout().Find(this.CurMech.GetLoadout().GetSupercharger()), false));
/*       */     } else {
/*  2350 */       this.chkSupercharger.setSelected(false);
/*       */     }
/*  2352 */     if (this.CurMech.GetLoadout().IsUsingClanCASE()) {
/*  2353 */       this.chkClanCASE.setSelected(true);
/*       */     } else {
/*  2355 */       this.chkClanCASE.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/*  2359 */     if (!this.CurMech.IsQuad()) {
/*  2360 */       if (this.CurMech.GetActuators().LeftLowerInstalled()) {
/*  2361 */         this.chkLALowerArm.setSelected(true);
/*       */       } else {
/*  2363 */         this.chkLALowerArm.setSelected(false);
/*       */       }
/*  2365 */       if (this.CurMech.GetActuators().LeftHandInstalled()) {
/*  2366 */         this.chkLAHand.setSelected(true);
/*       */       } else {
/*  2368 */         this.chkLAHand.setSelected(false);
/*       */       }
/*  2370 */       if (this.CurMech.GetActuators().RightLowerInstalled()) {
/*  2371 */         this.chkRALowerArm.setSelected(true);
/*       */       } else {
/*  2373 */         this.chkRALowerArm.setSelected(false);
/*       */       }
/*  2375 */       if (this.CurMech.GetActuators().RightHandInstalled()) {
/*  2376 */         this.chkRAHand.setSelected(true);
/*       */       } else {
/*  2378 */         this.chkRAHand.setSelected(false);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void CheckAES()
/*       */   {
/*  2385 */     if (this.chkLegAES.isSelected()) {
/*  2386 */       if (this.CurMech.GetTonnage() > 55)
/*       */       {
/*  2388 */         this.chkLegAES.setSelected(false);
/*       */         try {
/*  2390 */           this.CurMech.SetLegAES(false, null);
/*       */         } catch (Exception e) {
/*  2392 */           System.err.println(e.getMessage());
/*       */         }
/*       */       }
/*       */       else {
/*       */         try {
/*  2397 */           this.CurMech.SetLegAES(false, null);
/*  2398 */           this.CurMech.SetLegAES(true, null);
/*       */         } catch (Exception e) {
/*  2400 */           this.chkLegAES.setSelected(false);
/*       */         }
/*       */       }
/*       */     }
/*  2404 */     if (this.chkRAAES.isSelected()) {
/*  2405 */       if (this.CurMech.IsQuad()) {
/*  2406 */         this.chkRAAES.setSelected(false);
/*       */       } else {
/*  2408 */         int index = this.CurMech.GetLoadout().FindIndex(this.CurMech.GetRAAES()).Index;
/*       */         try {
/*  2410 */           this.CurMech.SetRAAES(false, -1);
/*  2411 */           this.CurMech.SetRAAES(true, index);
/*       */         } catch (Exception e) {
/*  2413 */           this.chkRAAES.setSelected(false);
/*       */         }
/*       */       }
/*       */     }
/*  2417 */     if (this.chkLAAES.isSelected()) {
/*  2418 */       if (this.CurMech.IsQuad()) {
/*  2419 */         this.chkLAAES.setSelected(false);
/*       */       } else {
/*  2421 */         int index = this.CurMech.GetLoadout().FindIndex(this.CurMech.GetLAAES()).Index;
/*       */         try {
/*  2423 */           this.CurMech.SetLAAES(false, -1);
/*  2424 */           this.CurMech.SetLAAES(true, index);
/*       */         } catch (Exception e) {
/*  2426 */           this.chkLAAES.setSelected(false);
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void FixArmorSpinners()
/*       */   {
/*  2434 */     MechArmor a = this.CurMech.GetArmor();
/*  2435 */     this.spnHDArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(0), 0, 9, 1));
/*  2436 */     this.spnCTArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(1), 0, a.GetLocationMax(1), 1));
/*  2437 */     this.spnLTArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(2), 0, a.GetLocationMax(2), 1));
/*  2438 */     this.spnRTArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(3), 0, a.GetLocationMax(3), 1));
/*  2439 */     this.spnLAArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(4), 0, a.GetLocationMax(4), 1));
/*  2440 */     this.spnRAArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(5), 0, a.GetLocationMax(5), 1));
/*  2441 */     this.spnLLArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(6), 0, a.GetLocationMax(6), 1));
/*  2442 */     this.spnRLArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(7), 0, a.GetLocationMax(7), 1));
/*  2443 */     this.spnCLArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(11), 0, a.GetLocationMax(11), 1));
/*  2444 */     this.spnCTRArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(8), 0, a.GetLocationMax(1), 1));
/*  2445 */     this.spnLTRArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(9), 0, a.GetLocationMax(2), 1));
/*  2446 */     this.spnRTRArmor.setModel(new SpinnerNumberModel(a.GetLocationArmor(10), 0, a.GetLocationMax(3), 1));
/*       */     
/*       */ 
/*  2449 */     ((JSpinner.DefaultEditor)this.spnHDArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2450 */     ((JSpinner.DefaultEditor)this.spnCTArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2451 */     ((JSpinner.DefaultEditor)this.spnCTRArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2452 */     ((JSpinner.DefaultEditor)this.spnRTArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2453 */     ((JSpinner.DefaultEditor)this.spnRTRArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2454 */     ((JSpinner.DefaultEditor)this.spnLTArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2455 */     ((JSpinner.DefaultEditor)this.spnLTRArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2456 */     ((JSpinner.DefaultEditor)this.spnRAArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2457 */     ((JSpinner.DefaultEditor)this.spnRLArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2458 */     ((JSpinner.DefaultEditor)this.spnCLArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2459 */     ((JSpinner.DefaultEditor)this.spnLAArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*  2460 */     ((JSpinner.DefaultEditor)this.spnLLArmor.getEditor()).getTextField().addFocusListener(this.spinners);
/*       */   }
/*       */   
/*       */   private void SaveSelections()
/*       */   {
/*  2465 */     this.Selections[0] = BuildLookupName(this.CurMech.GetIntStruc().GetCurrentState());
/*  2466 */     this.Selections[1] = BuildLookupName(this.CurMech.GetEngine().GetCurrentState());
/*  2467 */     this.Selections[2] = BuildLookupName(this.CurMech.GetGyro().GetCurrentState());
/*  2468 */     this.Selections[3] = BuildLookupName(this.CurMech.GetCockpit().GetCurrentState());
/*  2469 */     this.Selections[4] = BuildLookupName(this.CurMech.GetPhysEnhance().GetCurrentState());
/*  2470 */     this.Selections[5] = BuildLookupName(this.CurMech.GetHeatSinks().GetCurrentState());
/*  2471 */     this.Selections[6] = BuildLookupName(this.CurMech.GetJumpJets().GetCurrentState());
/*  2472 */     this.Selections[7] = BuildLookupName(this.CurMech.GetArmor().GetCurrentState());
/*       */   }
/*       */   
/*       */ 
/*       */   private JButton btnRemoveItemCrits;
/*       */   private JButton btnRenameVariant;
/*       */   private JButton btnSaveIcon;
/*       */   private JButton btnSelectiveAllocate;
/*       */   private JCheckBox chkAverageDamage;
/*       */   private JCheckBox chkBSPFD;
/*       */   private JCheckBox chkBoobyTrap;
/*       */   private JCheckBox chkBoosters;
/*       */   
/*       */   private void LoadSelections()
/*       */   {
/*  2487 */     this.cmbInternalType.setSelectedItem(this.Selections[0]);
/*  2488 */     this.cmbEngineType.setSelectedItem(this.Selections[1]);
/*  2489 */     this.cmbGyroType.setSelectedItem(this.Selections[2]);
/*  2490 */     this.cmbCockpitType.setSelectedItem(this.Selections[3]);
/*  2491 */     this.cmbPhysEnhance.setSelectedItem(this.Selections[4]);
/*  2492 */     this.cmbHeatSinkType.setSelectedItem(this.Selections[5]);
/*  2493 */     this.cmbJumpJetType.setSelectedItem(this.Selections[6]);
/*  2494 */     this.cmbArmorType.setSelectedItem(this.Selections[7]);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void RevertToStandardArmor()
/*       */   {
/*  2501 */     this.cmbArmorType.setSelectedItem("Standard Armor");
/*       */   }
/*       */   
/*       */   private void ResetAmmo()
/*       */   {
/*  2506 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();ArrayList wep = new ArrayList();
/*       */     
/*       */ 
/*  2509 */     for (int i = 0; i < v.size(); i++) {
/*  2510 */       Object a = v.get(i);
/*  2511 */       if ((a instanceof ifWeapon)) {
/*  2512 */         if (((ifWeapon)a).HasAmmo()) {
/*  2513 */           wep.add(a);
/*       */         }
/*  2515 */       } else if (((a instanceof Equipment)) && 
/*  2516 */         (((Equipment)a).HasAmmo())) {
/*  2517 */         wep.add(a);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  2523 */     Object[] result = { " " };
/*  2524 */     if (wep.size() > 0)
/*       */     {
/*  2526 */       int[] key = new int[wep.size()];
/*  2527 */       for (int i = 0; i < wep.size(); i++) {
/*  2528 */         if ((wep.get(i) instanceof ifWeapon)) {
/*  2529 */           key[i] = ((ifWeapon)wep.get(i)).GetAmmoIndex();
/*  2530 */         } else if ((wep.get(i) instanceof Equipment)) {
/*  2531 */           key[i] = ((Equipment)wep.get(i)).GetAmmoIndex();
/*       */         }
/*       */       }
/*  2534 */       result = this.data.GetEquipment().GetAmmo(key, this.CurMech);
/*       */     }
/*       */     
/*       */ 
/*  2538 */     this.Equipment[6] = result;
/*  2539 */     this.lstChooseAmmunition.setListData(result);
/*  2540 */     this.lstChooseAmmunition.repaint();
/*       */   }
/*       */   
/*       */   private void SelectiveAllocate() { dlgSelectiveAllocate Selec;
/*       */     dlgSelectiveAllocate Selec;
/*  2545 */     if (this.CurItem.Contiguous()) {
/*  2546 */       components.EquipmentCollection e = this.CurMech.GetLoadout().GetCollection(this.CurItem);
/*  2547 */       if (e == null) {
/*  2548 */         return;
/*       */       }
/*  2550 */       Selec = new dlgSelectiveAllocate(this, true, e);
/*       */     }
/*       */     else {
/*  2553 */       Selec = new dlgSelectiveAllocate(this, true, this.CurItem);
/*       */     }
/*  2555 */     Selec.setLocationRelativeTo(this);
/*  2556 */     Selec.setVisible(true);
/*  2557 */     RefreshSummary();
/*  2558 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void AutoAllocate() {
/*  2562 */     if (this.CurItem.Contiguous()) {
/*  2563 */       components.EquipmentCollection e = this.CurMech.GetLoadout().GetCollection(this.CurItem);
/*  2564 */       if (e == null) {
/*  2565 */         return;
/*       */       }
/*  2567 */       this.CurMech.GetLoadout().AutoAllocate(e);
/*       */     }
/*       */     else {
/*  2570 */       this.CurMech.GetLoadout().AutoAllocate(this.CurItem);
/*       */     }
/*  2572 */     RefreshSummary();
/*  2573 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void ResetTonnageSelector() {
/*  2577 */     int tons = this.CurMech.GetTonnage();
/*  2578 */     if (tons < 15) {
/*  2579 */       this.cmbTonnage.setSelectedIndex(0);
/*  2580 */       this.lblMechType.setText("Ultralight Mech");
/*  2581 */     } else if ((tons > 10) && (tons < 20)) {
/*  2582 */       this.cmbTonnage.setSelectedIndex(1);
/*  2583 */       this.lblMechType.setText("Ultralight Mech");
/*  2584 */     } else if ((tons > 15) && (tons < 25)) {
/*  2585 */       this.cmbTonnage.setSelectedIndex(2);
/*  2586 */       this.lblMechType.setText("Light Mech");
/*  2587 */     } else if ((tons > 20) && (tons < 30)) {
/*  2588 */       this.cmbTonnage.setSelectedIndex(3);
/*  2589 */       this.lblMechType.setText("Light Mech");
/*  2590 */     } else if ((tons > 25) && (tons < 35)) {
/*  2591 */       this.cmbTonnage.setSelectedIndex(4);
/*  2592 */       this.lblMechType.setText("Light Mech");
/*  2593 */     } else if ((tons > 30) && (tons < 40)) {
/*  2594 */       this.cmbTonnage.setSelectedIndex(5);
/*  2595 */       this.lblMechType.setText("Light Mech");
/*  2596 */     } else if ((tons > 35) && (tons < 45)) {
/*  2597 */       this.cmbTonnage.setSelectedIndex(6);
/*  2598 */       this.lblMechType.setText("Medium Mech");
/*  2599 */     } else if ((tons > 40) && (tons < 50)) {
/*  2600 */       this.cmbTonnage.setSelectedIndex(7);
/*  2601 */       this.lblMechType.setText("Medium Mech");
/*  2602 */     } else if ((tons > 45) && (tons < 55)) {
/*  2603 */       this.cmbTonnage.setSelectedIndex(8);
/*  2604 */       this.lblMechType.setText("Medium Mech");
/*  2605 */     } else if ((tons > 50) && (tons < 60)) {
/*  2606 */       this.cmbTonnage.setSelectedIndex(9);
/*  2607 */       this.lblMechType.setText("Medium Mech");
/*  2608 */     } else if ((tons > 55) && (tons < 65)) {
/*  2609 */       this.cmbTonnage.setSelectedIndex(10);
/*  2610 */       this.lblMechType.setText("Heavy Mech");
/*  2611 */     } else if ((tons > 60) && (tons < 70)) {
/*  2612 */       this.cmbTonnage.setSelectedIndex(11);
/*  2613 */       this.lblMechType.setText("Heavy Mech");
/*  2614 */     } else if ((tons > 65) && (tons < 75)) {
/*  2615 */       this.cmbTonnage.setSelectedIndex(12);
/*  2616 */       this.lblMechType.setText("Heavy Mech");
/*  2617 */     } else if ((tons > 70) && (tons < 80)) {
/*  2618 */       this.cmbTonnage.setSelectedIndex(13);
/*  2619 */       this.lblMechType.setText("Heavy Mech");
/*  2620 */     } else if ((tons > 75) && (tons < 85)) {
/*  2621 */       this.cmbTonnage.setSelectedIndex(14);
/*  2622 */       this.lblMechType.setText("Assault Mech");
/*  2623 */     } else if ((tons > 80) && (tons < 90)) {
/*  2624 */       this.cmbTonnage.setSelectedIndex(15);
/*  2625 */       this.lblMechType.setText("Assault Mech");
/*  2626 */     } else if ((tons > 85) && (tons < 95)) {
/*  2627 */       this.cmbTonnage.setSelectedIndex(16);
/*  2628 */       this.lblMechType.setText("Assault Mech");
/*  2629 */     } else if ((tons > 90) && (tons < 100)) {
/*  2630 */       this.cmbTonnage.setSelectedIndex(17);
/*  2631 */       this.lblMechType.setText("Assault Mech");
/*       */     } else {
/*  2633 */       this.cmbTonnage.setSelectedIndex(18);
/*  2634 */       this.lblMechType.setText("Assault Mech");
/*       */     }
/*       */   }
/*       */   
/*       */   private void GetNewMech() {
/*  2639 */     boolean Omni = this.CurMech.IsOmnimech();
/*  2640 */     this.cmbMotiveType.setSelectedIndex(0);
/*  2641 */     this.CurMech = new Mech(this.Prefs);
/*       */     
/*  2643 */     this.chkYearRestrict.setSelected(false);
/*  2644 */     this.txtProdYear.setText("");
/*  2645 */     this.cmbMechEra.setEnabled(true);
/*  2646 */     this.cmbProductionEra.setEnabled(true);
/*  2647 */     this.cmbTechBase.setEnabled(true);
/*  2648 */     this.txtProdYear.setEnabled(true);
/*       */     
/*  2650 */     this.cmbRulesLevel.setSelectedItem(this.Prefs.get("NewMech_RulesLevel", "Tournament Legal"));
/*  2651 */     this.cmbMechEra.setSelectedItem(this.Prefs.get("NewMech_Era", "Age of War/Star League"));
/*  2652 */     this.cmbProductionEra.setSelectedIndex(0);
/*       */     
/*  2654 */     if (Omni) {
/*  2655 */       UnlockGUIFromOmni();
/*       */     }
/*       */     
/*  2658 */     this.CurMech.SetEra(this.cmbMechEra.getSelectedIndex());
/*  2659 */     this.CurMech.SetProductionEra(this.cmbProductionEra.getSelectedIndex());
/*  2660 */     this.CurMech.SetRulesLevel(this.cmbRulesLevel.getSelectedIndex());
/*  2661 */     switch (this.CurMech.GetEra()) {
/*       */     case 0: 
/*  2663 */       this.CurMech.SetYear(2750, false);
/*  2664 */       break;
/*       */     case 1: 
/*  2666 */       this.CurMech.SetYear(3025, false);
/*  2667 */       break;
/*       */     case 2: 
/*  2669 */       this.CurMech.SetYear(3070, false);
/*  2670 */       break;
/*       */     case 3: 
/*  2672 */       this.CurMech.SetYear(3130, false);
/*  2673 */       break;
/*       */     case 4: 
/*  2675 */       this.CurMech.SetYear(0, false);
/*       */     }
/*       */     
/*  2678 */     BuildTechBaseSelector();
/*  2679 */     this.cmbTechBase.setSelectedItem(this.Prefs.get("NewMech_Techbase", "Inner Sphere"));
/*  2680 */     switch (this.cmbTechBase.getSelectedIndex()) {
/*       */     case 0: 
/*  2682 */       this.CurMech.SetInnerSphere();
/*  2683 */       break;
/*       */     case 1: 
/*  2685 */       this.CurMech.SetClan();
/*  2686 */       break;
/*       */     case 2: 
/*  2688 */       this.CurMech.SetMixed();
/*       */     }
/*       */     
/*  2691 */     if (this.CurMech.IsIndustrialmech()) {
/*  2692 */       this.cmbMechType.setSelectedIndex(1);
/*       */     } else {
/*  2694 */       this.cmbMechType.setSelectedIndex(0);
/*       */     }
/*  2696 */     this.txtMechName.setText(this.CurMech.GetName());
/*  2697 */     this.txtMechModel.setText(this.CurMech.GetModel());
/*       */     
/*  2699 */     FixTransferHandlers();
/*       */     try {
/*  2701 */       this.CurMech.Visit(new visitors.VMechFullRecalc());
/*       */     }
/*       */     catch (Exception e) {
/*  2704 */       System.err.println(e.getMessage());
/*  2705 */       e.printStackTrace();
/*       */     }
/*       */     
/*  2708 */     ResetTonnageSelector();
/*  2709 */     BuildChassisSelector();
/*  2710 */     BuildEngineSelector();
/*  2711 */     BuildGyroSelector();
/*  2712 */     BuildCockpitSelector();
/*  2713 */     BuildEnhancementSelector();
/*  2714 */     BuildHeatsinkSelector();
/*  2715 */     BuildJumpJetSelector();
/*  2716 */     BuildArmorSelector();
/*  2717 */     CheckOmnimech();
/*  2718 */     this.cmbInternalType.setSelectedItem("Standard Structure");
/*  2719 */     this.cmbEngineType.setSelectedItem("Fusion Engine");
/*  2720 */     this.cmbGyroType.setSelectedItem("Standard Gyro");
/*  2721 */     this.cmbCockpitType.setSelectedItem("Standard Cockpit");
/*  2722 */     this.cmbPhysEnhance.setSelectedItem("No Enhancement");
/*  2723 */     this.cmbHeatSinkType.setSelectedItem(this.Prefs.get("NewMech_Heatsinks", "Single Heat Sink"));
/*  2724 */     this.cmbJumpJetType.setSelectedItem("Standard Jump Jet");
/*  2725 */     this.cmbArmorType.setSelectedItem("Standard Armor");
/*  2726 */     FixWalkMPSpinner();
/*  2727 */     FixJJSpinnerModel();
/*  2728 */     FixHeatSinkSpinnerModel();
/*  2729 */     RefreshInternalPoints();
/*  2730 */     FixArmorSpinners();
/*  2731 */     this.data.Rebuild(this.CurMech);
/*  2732 */     RefreshEquipment();
/*  2733 */     this.chkCTCASE.setSelected(false);
/*  2734 */     this.chkLTCASE.setSelected(false);
/*  2735 */     this.chkRTCASE.setSelected(false);
/*  2736 */     this.chkHDCASE2.setSelected(false);
/*  2737 */     this.chkCTCASE2.setSelected(false);
/*  2738 */     this.chkLTCASE2.setSelected(false);
/*  2739 */     this.chkRTCASE2.setSelected(false);
/*  2740 */     this.chkLACASE2.setSelected(false);
/*  2741 */     this.chkRACASE2.setSelected(false);
/*  2742 */     this.chkLLCASE2.setSelected(false);
/*  2743 */     this.chkRLCASE2.setSelected(false);
/*  2744 */     this.chkNullSig.setSelected(false);
/*  2745 */     this.chkVoidSig.setSelected(false);
/*  2746 */     this.chkBSPFD.setSelected(false);
/*  2747 */     this.chkCLPS.setSelected(false);
/*  2748 */     this.chkTracks.setSelected(false);
/*  2749 */     SetLoadoutArrays();
/*  2750 */     RefreshSummary();
/*  2751 */     RefreshInfoPane();
/*  2752 */     SetWeaponChoosers();
/*  2753 */     ResetAmmo();
/*       */     
/*  2755 */     this.Overview.StartNewDocument();
/*  2756 */     this.Capabilities.StartNewDocument();
/*  2757 */     this.History.StartNewDocument();
/*  2758 */     this.Deployment.StartNewDocument();
/*  2759 */     this.Variants.StartNewDocument();
/*  2760 */     this.Notables.StartNewDocument();
/*  2761 */     this.Additional.StartNewDocument();
/*  2762 */     this.txtManufacturer.setText("");
/*  2763 */     this.txtManufacturerLocation.setText("");
/*  2764 */     this.txtEngineManufacturer.setText("");
/*  2765 */     this.txtArmorModel.setText("");
/*  2766 */     this.txtChassisModel.setText("");
/*  2767 */     this.txtJJModel.setText("");
/*  2768 */     this.txtCommSystem.setText("");
/*  2769 */     this.txtTNTSystem.setText("");
/*  2770 */     this.txtSource.setText("");
/*  2771 */     this.lblFluffImage.setIcon(null);
/*       */     
/*       */ 
/*  2774 */     this.tblWeaponManufacturers.setModel(new javax.swing.table.AbstractTableModel()
/*       */     {
/*       */       public String getColumnName(int col) {
/*  2777 */         if (col == 1) {
/*  2778 */           return "Manufacturer/Model";
/*       */         }
/*  2780 */         return "Item Name";
/*       */       }
/*       */       
/*  2783 */       public int getRowCount() { return frmMain.this.CurMech.GetLoadout().GetEquipment().size(); }
/*  2784 */       public int getColumnCount() { return 2; }
/*       */       
/*  2786 */       public Object getValueAt(int row, int col) { Object o = frmMain.this.CurMech.GetLoadout().GetEquipment().get(row);
/*  2787 */         if (col == 1) {
/*  2788 */           return ((abPlaceable)o).GetManufacturer();
/*       */         }
/*  2790 */         return ((abPlaceable)o).CritName();
/*       */       }
/*       */       
/*       */       public boolean isCellEditable(int row, int col)
/*       */       {
/*  2795 */         if (col == 0) {
/*  2796 */           return false;
/*       */         }
/*  2798 */         return true;
/*       */       }
/*       */       
/*       */       public void setValueAt(Object value, int row, int col)
/*       */       {
/*  2803 */         if (col == 0) return;
/*  2804 */         if (!(value instanceof String)) return;
/*  2805 */         abPlaceable a = (abPlaceable)frmMain.this.CurMech.GetLoadout().GetEquipment().get(row);
/*  2806 */         if (frmMain.this.chkIndividualWeapons.isSelected()) {
/*  2807 */           a.SetManufacturer((String)value);
/*  2808 */           fireTableCellUpdated(row, col);
/*       */         } else {
/*  2810 */           ArrayList v = frmMain.this.CurMech.GetLoadout().GetEquipment();
/*  2811 */           for (int i = 0; i < v.size(); i++) {
/*  2812 */             if (filehandlers.FileCommon.LookupStripArc(((abPlaceable)v.get(i)).LookupName()).equals(filehandlers.FileCommon.LookupStripArc(a.LookupName()))) {
/*  2813 */               ((abPlaceable)v.get(i)).SetManufacturer((String)value);
/*       */             }
/*       */           }
/*  2816 */           fireTableDataChanged();
/*       */         }
/*       */         
/*       */       }
/*  2820 */     });
/*  2821 */     this.tblWeaponManufacturers.getInputMap(1).put(javax.swing.KeyStroke.getKeyStroke(9, 0, false), "selectNextRow");
/*       */     
/*  2823 */     if (this.cmbMechEra.getSelectedIndex() == 4) {
/*  2824 */       this.chkYearRestrict.setEnabled(false);
/*       */     } else {
/*  2826 */       this.chkYearRestrict.setEnabled(true);
/*       */     }
/*  2828 */     this.CurMech.SetChanged(false);
/*  2829 */     setTitle("Solaris Skunk Werks 0.6.83.1");
/*       */   }
/*       */   
/*       */   private void GetInfoOn()
/*       */   {
/*  2834 */     if (((this.CurItem instanceof ifWeapon)) || ((this.CurItem instanceof Ammunition))) {
/*  2835 */       dlgWeaponInfo WepInfo = new dlgWeaponInfo(this, true);
/*  2836 */       WepInfo.setLocationRelativeTo(this);
/*  2837 */       WepInfo.setVisible(true);
/*       */     } else {
/*  2839 */       dlgPlaceableInfo ItemInfo = new dlgPlaceableInfo(this, true);
/*  2840 */       ItemInfo.setLocationRelativeTo(this);
/*  2841 */       ItemInfo.setVisible(true);
/*       */     }
/*       */   }
/*       */   
/*       */   private void UnallocateAll()
/*       */   {
/*  2847 */     this.CurMech.GetLoadout().UnallocateAll(this.CurItem, false);
/*  2848 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void MountRear()
/*       */   {
/*  2853 */     if (this.CurItem.IsMountedRear()) {
/*  2854 */       this.CurItem.MountRear(false);
/*       */     } else {
/*  2856 */       this.CurItem.MountRear(true);
/*       */     }
/*  2858 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */ 
/*       */   private void ShowInfoOn(abPlaceable p)
/*       */   {
/*  2864 */     AvailableCode AC = p.GetAvailability();
/*       */     
/*  2866 */     this.lblInfoAVSL.setText(AC.GetISSLCode() + " / " + AC.GetCLSLCode());
/*  2867 */     this.lblInfoAVSW.setText(AC.GetISSWCode() + " / " + AC.GetCLSWCode());
/*  2868 */     this.lblInfoAVCI.setText(AC.GetISCICode() + " / " + AC.GetCLCICode());
/*  2869 */     switch (AC.GetTechBase()) {
/*       */     case 0: 
/*  2871 */       this.lblInfoIntro.setText(AC.GetISIntroDate() + " (" + AC.GetISIntroFaction() + ")");
/*  2872 */       if (AC.WentExtinctIS()) {
/*  2873 */         this.lblInfoExtinct.setText("" + AC.GetISExtinctDate());
/*       */       } else {
/*  2875 */         this.lblInfoExtinct.setText("--");
/*       */       }
/*  2877 */       if (AC.WasReIntrodIS()) {
/*  2878 */         this.lblInfoReintro.setText(AC.GetISReIntroDate() + " (" + AC.GetISReIntroFaction() + ")");
/*       */       } else {
/*  2880 */         this.lblInfoReintro.setText("--");
/*       */       }
/*  2882 */       break;
/*       */     case 1: 
/*  2884 */       this.lblInfoIntro.setText(AC.GetCLIntroDate() + " (" + AC.GetCLIntroFaction() + ")");
/*  2885 */       if (AC.WentExtinctCL()) {
/*  2886 */         this.lblInfoExtinct.setText("" + AC.GetCLExtinctDate());
/*       */       } else {
/*  2888 */         this.lblInfoExtinct.setText("--");
/*       */       }
/*  2890 */       if (AC.WasReIntrodCL()) {
/*  2891 */         this.lblInfoReintro.setText(AC.GetCLReIntroDate() + " (" + AC.GetCLReIntroFaction() + ")");
/*       */       } else {
/*  2893 */         this.lblInfoReintro.setText("--");
/*       */       }
/*  2895 */       break;
/*       */     case 2: 
/*  2897 */       this.lblInfoIntro.setText(AC.GetISIntroDate() + " (" + AC.GetISIntroFaction() + ") / " + AC.GetCLIntroDate() + " (" + AC.GetCLIntroFaction() + ")");
/*  2898 */       if (AC.WentExtinctIS()) {
/*  2899 */         this.lblInfoExtinct.setText("" + AC.GetISExtinctDate());
/*       */       } else {
/*  2901 */         this.lblInfoExtinct.setText("--");
/*       */       }
/*  2903 */       if (AC.WentExtinctCL()) {
/*  2904 */         this.lblInfoExtinct.setText(this.lblInfoExtinct.getText() + " / " + AC.GetCLExtinctDate());
/*       */       } else {
/*  2906 */         this.lblInfoExtinct.setText(this.lblInfoExtinct.getText() + " / --");
/*       */       }
/*  2908 */       if (AC.WasReIntrodIS()) {
/*  2909 */         this.lblInfoReintro.setText(AC.GetISReIntroDate() + " (" + AC.GetISReIntroFaction() + ")");
/*       */       } else {
/*  2911 */         this.lblInfoReintro.setText("--");
/*       */       }
/*  2913 */       if (AC.WasReIntrodCL()) {
/*  2914 */         this.lblInfoReintro.setText(this.lblInfoReintro.getText() + " / " + AC.GetCLReIntroDate() + " (" + AC.GetCLReIntroFaction() + ")");
/*       */       } else {
/*  2916 */         this.lblInfoReintro.setText(this.lblInfoReintro.getText() + " / --");
/*       */       }
/*       */       break;
/*       */     }
/*  2920 */     if (this.CurMech.IsIndustrialmech()) {
/*  2921 */       switch (AC.GetRulesLevel_IM()) {
/*       */       case 0: 
/*  2923 */         this.lblInfoRulesLevel.setText("Introductory");
/*  2924 */         break;
/*       */       case 1: 
/*  2926 */         this.lblInfoRulesLevel.setText("Tournament");
/*  2927 */         break;
/*       */       case 2: 
/*  2929 */         this.lblInfoRulesLevel.setText("Advanced");
/*  2930 */         break;
/*       */       case 3: 
/*  2932 */         this.lblInfoRulesLevel.setText("Experimental");
/*  2933 */         break;
/*       */       case 4: 
/*  2935 */         this.lblInfoRulesLevel.setText("Era Specific");
/*  2936 */         break;
/*       */       default: 
/*  2938 */         this.lblInfoRulesLevel.setText("??");break;
/*       */       }
/*       */     } else {
/*  2941 */       switch (AC.GetRulesLevel_BM()) {
/*       */       case 0: 
/*  2943 */         this.lblInfoRulesLevel.setText("Introductory");
/*  2944 */         break;
/*       */       case 1: 
/*  2946 */         this.lblInfoRulesLevel.setText("Tournament");
/*  2947 */         break;
/*       */       case 2: 
/*  2949 */         this.lblInfoRulesLevel.setText("Advanced");
/*  2950 */         break;
/*       */       case 3: 
/*  2952 */         this.lblInfoRulesLevel.setText("Experimental");
/*  2953 */         break;
/*       */       case 4: 
/*  2955 */         this.lblInfoRulesLevel.setText("Era Specific");
/*  2956 */         break;
/*       */       default: 
/*  2958 */         this.lblInfoRulesLevel.setText("??");
/*       */       }
/*       */     }
/*  2961 */     this.lblInfoName.setText(p.CritName());
/*  2962 */     this.lblInfoTonnage.setText("" + p.GetTonnage());
/*  2963 */     this.lblInfoCrits.setText("" + p.NumCrits());
/*  2964 */     this.lblInfoCost.setText("" + String.format("%1$,.0f", new Object[] { Double.valueOf(p.GetCost()) }));
/*  2965 */     if (p.LookupName().equals("Radical Heat Sink")) {
/*  2966 */       this.lblInfoBV.setText(this.CurMech.GetHeatSinks().GetNumHS() * 1.4D + "");
/*       */     } else {
/*  2968 */       this.lblInfoBV.setText(CommonTools.GetAggregateReportBV(p));
/*       */     }
/*       */     
/*  2971 */     String restrict = "";
/*  2972 */     if (!p.CanAllocHD()) {
/*  2973 */       restrict = restrict + "No Head, ";
/*       */     }
/*  2975 */     if (!p.CanAllocCT()) {
/*  2976 */       restrict = restrict + "No Center Torso, ";
/*       */     }
/*  2978 */     if (!p.CanAllocTorso()) {
/*  2979 */       restrict = restrict + "No Side Torsos, ";
/*       */     }
/*  2981 */     if (!p.CanAllocArms()) {
/*  2982 */       restrict = restrict + "No Arms, ";
/*       */     }
/*  2984 */     if (!p.CanAllocLegs()) {
/*  2985 */       restrict = restrict + "No Legs, ";
/*       */     }
/*  2987 */     if (p.CanSplit()) {
/*  2988 */       restrict = restrict + "Can Split, ";
/*       */     }
/*       */     
/*       */ 
/*  2992 */     if ((p instanceof ifWeapon)) {
/*  2993 */       ifWeapon w = (ifWeapon)p;
/*  2994 */       this.lblInfoType.setText(w.GetType());
/*       */       
/*  2996 */       if ((w.IsUltra()) || (w.IsRotary())) {
/*  2997 */         this.lblInfoHeat.setText(w.GetHeat() + "/shot");
/*       */       }
/*  2999 */       else if ((w instanceof RangedWeapon)) {
/*  3000 */         if (((RangedWeapon)w).IsUsingCapacitor()) {
/*  3001 */           this.lblInfoHeat.setText(w.GetHeat() + "*");
/*  3002 */         } else if (((RangedWeapon)w).IsUsingInsulator()) {
/*  3003 */           this.lblInfoHeat.setText(w.GetHeat() + " (I)");
/*       */         } else {
/*  3005 */           this.lblInfoHeat.setText("" + w.GetHeat());
/*       */         }
/*       */       } else {
/*  3008 */         this.lblInfoHeat.setText("" + w.GetHeat());
/*       */       }
/*       */       
/*       */ 
/*  3012 */       if (w.GetWeaponClass() == 2) {
/*  3013 */         this.lblInfoDamage.setText(w.GetDamageShort() + "/msl");
/*  3014 */       } else if (w.GetWeaponClass() == 3) {
/*  3015 */         this.lblInfoDamage.setText(w.GetDamageShort() + "A");
/*  3016 */       } else if ((w instanceof components.MGArray)) {
/*  3017 */         this.lblInfoDamage.setText(w.GetDamageShort() + "/gun");
/*  3018 */       } else if ((w.GetDamageShort() == w.GetDamageMedium()) && (w.GetDamageShort() == w.GetDamageLong())) {
/*  3019 */         if ((w.IsUltra()) || (w.IsRotary())) {
/*  3020 */           this.lblInfoDamage.setText(w.GetDamageShort() + "/shot");
/*       */         }
/*  3022 */         else if ((w instanceof RangedWeapon)) {
/*  3023 */           if (((RangedWeapon)w).IsUsingCapacitor()) {
/*  3024 */             this.lblInfoDamage.setText(w.GetDamageShort() + "*");
/*       */           } else {
/*  3026 */             this.lblInfoDamage.setText("" + w.GetDamageShort());
/*       */           }
/*       */         } else {
/*  3029 */           this.lblInfoDamage.setText("" + w.GetDamageShort());
/*       */         }
/*       */         
/*       */       }
/*  3033 */       else if ((w instanceof RangedWeapon)) {
/*  3034 */         if (((RangedWeapon)w).IsUsingCapacitor()) {
/*  3035 */           this.lblInfoDamage.setText(w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong() + "*");
/*       */         } else {
/*  3037 */           this.lblInfoDamage.setText(w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong());
/*       */         }
/*       */       } else {
/*  3040 */         this.lblInfoDamage.setText(w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong());
/*       */       }
/*       */       
/*       */ 
/*  3044 */       if (w.GetRangeLong() < 1) {
/*  3045 */         if (w.GetRangeMedium() < 1) {
/*  3046 */           if (w.GetWeaponClass() == 3) {
/*  3047 */             this.lblInfoRange.setText(w.GetRangeShort() + " boards");
/*       */           } else {
/*  3049 */             this.lblInfoRange.setText(w.GetRangeShort() + "");
/*       */           }
/*       */         } else {
/*  3052 */           this.lblInfoRange.setText(w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/-");
/*       */         }
/*       */       } else {
/*  3055 */         this.lblInfoRange.setText(w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/" + w.GetRangeLong());
/*       */       }
/*       */       
/*  3058 */       if (w.HasAmmo()) {
/*  3059 */         this.lblInfoAmmo.setText("" + w.GetAmmoLotSize());
/*       */       } else {
/*  3061 */         this.lblInfoAmmo.setText("--");
/*       */       }
/*  3063 */       this.lblInfoSpecials.setText(w.GetSpecials());
/*  3064 */       if (w.OmniRestrictActuators()) {
/*  3065 */         restrict = restrict + "Omni Actuator Restricted";
/*       */       }
/*  3067 */     } else if ((p instanceof Ammunition)) {
/*  3068 */       Ammunition a = (Ammunition)p;
/*  3069 */       this.lblInfoType.setText("--");
/*  3070 */       this.lblInfoHeat.setText("--");
/*  3071 */       if (a.ClusterGrouping() > 1) {
/*  3072 */         this.lblInfoDamage.setText(a.GetDamageShort() + "/hit");
/*       */       } else {
/*  3074 */         this.lblInfoDamage.setText(a.GetDamageShort() + "");
/*       */       }
/*  3076 */       if (a.GetLongRange() < 1) {
/*  3077 */         if (a.GetMediumRange() < 1) {
/*  3078 */           this.lblInfoRange.setText(a.GetShortRange() + " boards");
/*       */         } else {
/*  3080 */           this.lblInfoRange.setText(a.GetMinRange() + "/" + a.GetShortRange() + "/" + a.GetMediumRange() + "/-");
/*       */         }
/*       */       } else {
/*  3083 */         this.lblInfoRange.setText(a.GetMinRange() + "/" + a.GetShortRange() + "/" + a.GetMediumRange() + "/" + a.GetLongRange());
/*       */       }
/*  3085 */       this.lblInfoAmmo.setText("" + a.GetLotSize());
/*  3086 */       if (a.IsExplosive()) {
/*  3087 */         this.lblInfoSpecials.setText("Explosive");
/*       */       } else {
/*  3089 */         this.lblInfoSpecials.setText("--");
/*       */       }
/*  3091 */     } else if ((p instanceof Equipment)) {
/*  3092 */       Equipment e = (Equipment)p;
/*  3093 */       this.lblInfoType.setText(e.GetType());
/*  3094 */       this.lblInfoHeat.setText("" + e.GetHeat());
/*  3095 */       this.lblInfoDamage.setText("--");
/*  3096 */       if ((e.GetShortRange() <= 0) && (e.GetMediumRange() <= 0)) {
/*  3097 */         if (e.GetLongRange() > 0) {
/*  3098 */           this.lblInfoRange.setText("" + e.GetLongRange());
/*       */         } else {
/*  3100 */           this.lblInfoRange.setText("--");
/*       */         }
/*       */       } else {
/*  3103 */         this.lblInfoRange.setText("0/" + e.GetShortRange() + "/" + e.GetMediumRange() + "/" + e.GetLongRange());
/*       */       }
/*  3105 */       if (e.HasAmmo()) {
/*  3106 */         this.lblInfoAmmo.setText("" + e.GetAmmo());
/*       */       } else {
/*  3108 */         this.lblInfoAmmo.setText("--");
/*       */       }
/*  3110 */       this.lblInfoSpecials.setText(e.GetSpecials());
/*       */     } else {
/*  3112 */       this.lblInfoType.setText("--");
/*  3113 */       this.lblInfoHeat.setText("--");
/*  3114 */       this.lblInfoDamage.setText("--");
/*  3115 */       this.lblInfoRange.setText("--");
/*  3116 */       this.lblInfoAmmo.setText("--");
/*  3117 */       this.lblInfoSpecials.setText("--");
/*       */     }
/*       */     
/*       */ 
/*  3121 */     if (restrict.length() > 0) {
/*  3122 */       if (restrict.endsWith(", ")) {
/*  3123 */         restrict = restrict.substring(0, restrict.length() - 2);
/*       */       }
/*  3125 */       this.lblInfoMountRestrict.setText(restrict);
/*       */     } else {
/*  3127 */       this.lblInfoMountRestrict.setText("None");
/*       */     }
/*       */     
/*  3130 */     this.lblInfoMountRestrict.setText(this.lblInfoMountRestrict.getText() + " MM Name " + p.MegaMekName(false));
/*       */   }
/*       */   
/*       */   private void RemoveItemCritTab() {
/*  3134 */     if ((!this.CurItem.CoreComponent()) && (this.CurItem.Contiguous())) {
/*  3135 */       this.CurMech.GetLoadout().Remove(this.CurItem);
/*       */       
/*       */ 
/*  3138 */       if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/*  3139 */         this.Equipment[7] = { " " };
/*       */       } else {
/*  3141 */         this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */       }
/*  3143 */       this.lstSelectedEquipment.setListData(this.Equipment[7]);
/*       */       
/*       */ 
/*  3146 */       if (this.CurMech.UsingTC()) {
/*  3147 */         this.CurMech.UnallocateTC();
/*       */       }
/*       */       
/*       */ 
/*  3151 */       ResetAmmo();
/*       */       
/*       */ 
/*  3154 */       RefreshSummary();
/*  3155 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   private void SolidifyMech()
/*       */   {
/*  3162 */     int year = 0;
/*  3163 */     this.CurMech.SetName(this.txtMechName.getText());
/*  3164 */     this.CurMech.SetModel(this.txtMechModel.getText());
/*  3165 */     if (this.txtProdYear.getText().isEmpty()) {
/*  3166 */       switch (this.cmbMechEra.getSelectedIndex()) {
/*       */       case 0: 
/*  3168 */         this.CurMech.SetYear(2750, false);
/*  3169 */         break;
/*       */       case 1: 
/*  3171 */         this.CurMech.SetYear(3025, false);
/*  3172 */         break;
/*       */       case 2: 
/*  3174 */         this.CurMech.SetYear(3070, false);
/*  3175 */         break;
/*       */       case 3: 
/*  3177 */         this.CurMech.SetYear(3132, false);
/*       */       }
/*       */     } else {
/*       */       try
/*       */       {
/*  3182 */         year = Integer.parseInt(this.txtProdYear.getText());
/*  3183 */         this.CurMech.SetYear(year, true);
/*       */       } catch (NumberFormatException n) {
/*  3185 */         Media.Messager(this, "The production year is not a number.");
/*  3186 */         this.tbpMainTabPane.setSelectedComponent(this.pnlBasicSetup);
/*  3187 */         return;
/*       */       }
/*       */     }
/*       */     
/*  3191 */     this.CurMech.SetOverview(this.Overview.GetText());
/*  3192 */     this.CurMech.SetCapabilities(this.Capabilities.GetText());
/*  3193 */     this.CurMech.SetHistory(this.History.GetText());
/*  3194 */     this.CurMech.SetDeployment(this.Deployment.GetText());
/*  3195 */     this.CurMech.SetVariants(this.Variants.GetText());
/*  3196 */     this.CurMech.SetNotables(this.Notables.GetText());
/*  3197 */     this.CurMech.SetAdditional(this.Additional.GetText());
/*  3198 */     this.CurMech.SetCompany(this.txtManufacturer.getText());
/*  3199 */     this.CurMech.SetLocation(this.txtManufacturerLocation.getText());
/*  3200 */     this.CurMech.SetEngineManufacturer(this.txtEngineManufacturer.getText());
/*  3201 */     this.CurMech.SetArmorModel(this.txtArmorModel.getText());
/*  3202 */     this.CurMech.SetChassisModel(this.txtChassisModel.getText());
/*  3203 */     if (this.CurMech.GetJumpJets().GetNumJJ() > 0) {
/*  3204 */       this.CurMech.SetJJModel(this.txtJJModel.getText());
/*       */     }
/*  3206 */     this.CurMech.SetCommSystem(this.txtCommSystem.getText());
/*  3207 */     this.CurMech.SetTandTSystem(this.txtTNTSystem.getText());
/*  3208 */     this.CurMech.SetSource(this.txtSource.getText());
/*       */   }
/*       */   
/*       */   private void EnableJumpJets(boolean enable)
/*       */   {
/*  3213 */     if (enable) {
/*  3214 */       this.spnJumpMP.setEnabled(true);
/*  3215 */       if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetJumpJets().GetNumJJ() > 0)) {
/*  3216 */         this.cmbJumpJetType.setEnabled(false);
/*       */       } else {
/*  3218 */         this.cmbJumpJetType.setEnabled(true);
/*       */       }
/*       */     } else {
/*  3221 */       this.CurMech.GetJumpJets().ClearJumpJets();
/*  3222 */       this.spnJumpMP.setEnabled(false);
/*  3223 */       this.cmbJumpJetType.setEnabled(false);
/*       */     }
/*  3225 */     FixJJSpinnerModel();
/*       */   }
/*       */   
/*       */   private void FixTransferHandlers()
/*       */   {
/*  3230 */     this.lstHDCrits.setTransferHandler(new thHDTransferHandler(this, this.CurMech));
/*  3231 */     this.lstCTCrits.setTransferHandler(new thCTTransferHandler(this, this.CurMech));
/*  3232 */     this.lstLTCrits.setTransferHandler(new thLTTransferHandler(this, this.CurMech));
/*  3233 */     this.lstRTCrits.setTransferHandler(new thRTTransferHandler(this, this.CurMech));
/*  3234 */     this.lstLACrits.setTransferHandler(new thLATransferHandler(this, this.CurMech));
/*  3235 */     this.lstRACrits.setTransferHandler(new thRATransferHandler(this, this.CurMech));
/*  3236 */     this.lstLLCrits.setTransferHandler(new thLLTransferHandler(this, this.CurMech));
/*  3237 */     this.lstRLCrits.setTransferHandler(new thRLTransferHandler(this, this.CurMech));
/*       */   }
/*       */   
/*       */   private void LockGUIForOmni()
/*       */   {
/*  3242 */     this.chkOmnimech.setSelected(true);
/*  3243 */     this.chkOmnimech.setEnabled(false);
/*  3244 */     this.mnuUnlock.setEnabled(true);
/*  3245 */     this.cmbTonnage.setEnabled(false);
/*  3246 */     this.cmbMechType.setEnabled(false);
/*  3247 */     this.cmbMotiveType.setEnabled(false);
/*  3248 */     this.cmbInternalType.setEnabled(false);
/*  3249 */     this.cmbEngineType.setEnabled(false);
/*  3250 */     this.cmbGyroType.setEnabled(false);
/*  3251 */     this.cmbCockpitType.setEnabled(false);
/*  3252 */     this.cmbPhysEnhance.setEnabled(false);
/*  3253 */     this.cmbHeatSinkType.setEnabled(false);
/*  3254 */     this.spnHDArmor.setEnabled(false);
/*  3255 */     this.spnCTArmor.setEnabled(false);
/*  3256 */     this.spnLTArmor.setEnabled(false);
/*  3257 */     this.spnRTArmor.setEnabled(false);
/*  3258 */     this.spnLAArmor.setEnabled(false);
/*  3259 */     this.spnRAArmor.setEnabled(false);
/*  3260 */     this.spnLLArmor.setEnabled(false);
/*  3261 */     this.spnRLArmor.setEnabled(false);
/*  3262 */     this.spnCTRArmor.setEnabled(false);
/*  3263 */     this.spnLTRArmor.setEnabled(false);
/*  3264 */     this.spnRTRArmor.setEnabled(false);
/*  3265 */     this.cmbArmorType.setEnabled(false);
/*  3266 */     this.btnMaxArmor.setEnabled(false);
/*  3267 */     this.btnArmorTons.setEnabled(false);
/*  3268 */     this.btnRemainingArmor.setEnabled(false);
/*  3269 */     this.btnEfficientArmor.setEnabled(false);
/*  3270 */     this.btnBalanceArmor.setEnabled(false);
/*  3271 */     this.btnLockChassis.setEnabled(false);
/*  3272 */     this.chkYearRestrict.setEnabled(false);
/*  3273 */     if (this.CurMech.GetBaseLoadout().GetJumpJets().GetNumJJ() > 0) {
/*  3274 */       this.cmbJumpJetType.setEnabled(false);
/*       */     }
/*  3276 */     this.spnWalkMP.setEnabled(false);
/*  3277 */     if (this.chkFCSAIV.isSelected()) {
/*  3278 */       this.chkFCSAIV.setEnabled(false);
/*       */     }
/*  3280 */     if (this.chkFCSAV.isSelected()) {
/*  3281 */       this.chkFCSAV.setEnabled(false);
/*       */     }
/*  3283 */     if (this.chkFCSApollo.isSelected()) {
/*  3284 */       this.chkFCSApollo.setEnabled(false);
/*       */     }
/*  3286 */     if (this.chkCTCASE.isSelected()) {
/*  3287 */       this.chkCTCASE.setEnabled(false);
/*       */     }
/*  3289 */     if (this.chkLTCASE.isSelected()) {
/*  3290 */       this.chkLTCASE.setEnabled(false);
/*       */     }
/*  3292 */     if (this.chkRTCASE.isSelected()) {
/*  3293 */       this.chkRTCASE.setEnabled(false);
/*       */     }
/*       */     
/*  3296 */     if (this.chkHDCASE2.isSelected()) {
/*  3297 */       this.chkHDCASE2.setEnabled(false);
/*       */     }
/*  3299 */     if (this.chkCTCASE2.isSelected()) {
/*  3300 */       this.chkCTCASE2.setEnabled(false);
/*       */     }
/*  3302 */     if (this.chkLTCASE2.isSelected()) {
/*  3303 */       this.chkLTCASE2.setEnabled(false);
/*       */     }
/*  3305 */     if (this.chkRTCASE2.isSelected()) {
/*  3306 */       this.chkRTCASE2.setEnabled(false);
/*       */     }
/*  3308 */     if (this.chkLACASE2.isSelected()) {
/*  3309 */       this.chkLACASE2.setEnabled(false);
/*       */     }
/*  3311 */     if (this.chkRACASE2.isSelected()) {
/*  3312 */       this.chkRACASE2.setEnabled(false);
/*       */     }
/*  3314 */     if (this.chkLLCASE2.isSelected()) {
/*  3315 */       this.chkLLCASE2.setEnabled(false);
/*       */     }
/*  3317 */     if (this.chkRLCASE2.isSelected()) {
/*  3318 */       this.chkRLCASE2.setEnabled(false);
/*       */     }
/*  3320 */     if (this.chkHDTurret.isSelected()) {
/*  3321 */       this.chkHDTurret.setEnabled(false);
/*       */     }
/*  3323 */     if (this.chkLTTurret.isSelected()) {
/*  3324 */       this.chkLTTurret.setEnabled(false);
/*       */     }
/*  3326 */     if (this.chkRTTurret.isSelected()) {
/*  3327 */       this.chkRTTurret.setEnabled(false);
/*       */     }
/*       */     
/*  3330 */     this.chkFractional.setEnabled(false);
/*  3331 */     this.chkNullSig.setEnabled(false);
/*  3332 */     this.chkVoidSig.setEnabled(false);
/*  3333 */     this.chkCLPS.setEnabled(false);
/*  3334 */     this.chkBSPFD.setEnabled(false);
/*  3335 */     this.chkEnviroSealing.setEnabled(false);
/*  3336 */     this.chkEjectionSeat.setEnabled(false);
/*  3337 */     this.chkRAAES.setEnabled(false);
/*  3338 */     this.chkLAAES.setEnabled(false);
/*  3339 */     this.chkLegAES.setEnabled(false);
/*  3340 */     if (this.CurMech.GetBaseLoadout().HasSupercharger()) {
/*  3341 */       this.chkSupercharger.setEnabled(false);
/*  3342 */       this.lblSupercharger.setEnabled(false);
/*  3343 */       this.cmbSCLoc.setEnabled(false);
/*       */     }
/*       */     
/*  3346 */     CheckActuators();
/*       */     
/*       */ 
/*  3349 */     this.cmbOmniVariant.setEnabled(true);
/*  3350 */     this.btnAddVariant.setEnabled(true);
/*  3351 */     this.btnDeleteVariant.setEnabled(true);
/*  3352 */     this.btnRenameVariant.setEnabled(true);
/*       */   }
/*       */   
/*       */ 
/*       */   private void UnlockGUIFromOmni()
/*       */   {
/*  3358 */     this.chkOmnimech.setSelected(false);
/*  3359 */     this.chkOmnimech.setEnabled(true);
/*  3360 */     this.mnuUnlock.setEnabled(false);
/*  3361 */     this.cmbTonnage.setEnabled(true);
/*  3362 */     this.cmbMechType.setEnabled(true);
/*  3363 */     this.cmbMotiveType.setEnabled(true);
/*  3364 */     this.cmbInternalType.setEnabled(true);
/*  3365 */     this.cmbEngineType.setEnabled(true);
/*  3366 */     this.cmbGyroType.setEnabled(true);
/*  3367 */     this.cmbCockpitType.setEnabled(true);
/*  3368 */     this.cmbPhysEnhance.setEnabled(true);
/*  3369 */     this.cmbHeatSinkType.setEnabled(true);
/*  3370 */     this.spnHDArmor.setEnabled(true);
/*  3371 */     this.spnCTArmor.setEnabled(true);
/*  3372 */     this.spnLTArmor.setEnabled(true);
/*  3373 */     this.spnRTArmor.setEnabled(true);
/*  3374 */     this.spnLAArmor.setEnabled(true);
/*  3375 */     this.spnRAArmor.setEnabled(true);
/*  3376 */     this.spnLLArmor.setEnabled(true);
/*  3377 */     this.spnRLArmor.setEnabled(true);
/*  3378 */     this.spnCTRArmor.setEnabled(true);
/*  3379 */     this.spnLTRArmor.setEnabled(true);
/*  3380 */     this.spnRTRArmor.setEnabled(true);
/*  3381 */     this.cmbArmorType.setEnabled(true);
/*  3382 */     this.btnMaxArmor.setEnabled(true);
/*  3383 */     this.btnArmorTons.setEnabled(true);
/*  3384 */     this.btnRemainingArmor.setEnabled(true);
/*  3385 */     this.btnEfficientArmor.setEnabled(true);
/*  3386 */     this.btnBalanceArmor.setEnabled(true);
/*  3387 */     this.cmbJumpJetType.setEnabled(true);
/*  3388 */     this.btnLockChassis.setEnabled(true);
/*  3389 */     this.chkFCSAIV.setEnabled(true);
/*  3390 */     this.chkFCSAV.setEnabled(true);
/*  3391 */     this.chkFCSApollo.setEnabled(true);
/*  3392 */     this.chkCTCASE.setEnabled(true);
/*  3393 */     this.chkLTCASE.setEnabled(true);
/*  3394 */     this.chkRTCASE.setEnabled(true);
/*  3395 */     this.chkHDCASE2.setEnabled(true);
/*  3396 */     this.chkCTCASE2.setEnabled(true);
/*  3397 */     this.chkLTCASE2.setEnabled(true);
/*  3398 */     this.chkRTCASE2.setEnabled(true);
/*  3399 */     this.chkLACASE2.setEnabled(true);
/*  3400 */     this.chkRACASE2.setEnabled(true);
/*  3401 */     this.chkLLCASE2.setEnabled(true);
/*  3402 */     this.chkRLCASE2.setEnabled(true);
/*  3403 */     this.chkHDTurret.setEnabled(true);
/*  3404 */     this.chkLTTurret.setEnabled(true);
/*  3405 */     this.chkRTTurret.setEnabled(true);
/*  3406 */     this.chkOmnimech.setSelected(false);
/*  3407 */     this.chkOmnimech.setEnabled(true);
/*  3408 */     this.btnLockChassis.setEnabled(false);
/*  3409 */     this.spnWalkMP.setEnabled(true);
/*  3410 */     this.chkYearRestrict.setEnabled(true);
/*  3411 */     this.chkNullSig.setEnabled(true);
/*  3412 */     this.chkVoidSig.setEnabled(true);
/*  3413 */     this.chkCLPS.setEnabled(true);
/*  3414 */     this.chkBSPFD.setEnabled(true);
/*  3415 */     this.chkSupercharger.setEnabled(true);
/*  3416 */     this.lblSupercharger.setEnabled(true);
/*  3417 */     this.cmbSCLoc.setEnabled(true);
/*  3418 */     if (this.CurMech.IsIndustrialmech()) {
/*  3419 */       this.chkEnviroSealing.setEnabled(true);
/*  3420 */       this.chkEjectionSeat.setEnabled(true);
/*       */     } else {
/*  3422 */       this.chkEnviroSealing.setEnabled(false);
/*  3423 */       this.chkEjectionSeat.setEnabled(false);
/*       */     }
/*       */     
/*  3426 */     this.cmbOmniVariant.setEnabled(false);
/*  3427 */     this.btnAddVariant.setEnabled(false);
/*  3428 */     this.btnDeleteVariant.setEnabled(false);
/*  3429 */     this.btnRenameVariant.setEnabled(false);
/*       */   }
/*       */   
/*       */   private void RefreshOmniVariants() {
/*  3433 */     ArrayList v = this.CurMech.GetLoadouts();
/*  3434 */     String[] variants = new String[v.size()];
/*  3435 */     if (v.size() <= 0) {
/*  3436 */       variants = new String[] { "Base Loadout" };
/*       */     } else {
/*  3438 */       for (int i = 0; i < v.size(); i++) {
/*  3439 */         variants[i] = ((ifMechLoadout)v.get(i)).GetName();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  3444 */     this.txtSource.setText(this.CurMech.GetSource());
/*  3445 */     this.cmbOmniVariant.setModel(new DefaultComboBoxModel(variants));
/*  3446 */     this.cmbOmniVariant.setSelectedItem(this.CurMech.GetLoadout().GetName());
/*       */   }
/*       */   
/*       */ 
/*       */   private void RefreshOmniChoices()
/*       */   {
/*  3452 */     CheckActuators();
/*  3453 */     CheckEquipment();
/*       */   }
/*       */   
/*       */   private boolean VerifyMech(ActionEvent evt)
/*       */   {
/*  3458 */     String CurLoadout = "";
/*  3459 */     this.SetSource = false;
/*  3460 */     if (this.CurMech.IsOmnimech()) {
/*  3461 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/*       */ 
/*  3465 */     if (this.CurMech.GetName().isEmpty()) {
/*  3466 */       Media.Messager(this, "Your mech needs a name first.");
/*  3467 */       this.tbpMainTabPane.setSelectedComponent(this.pnlBasicSetup);
/*  3468 */       this.txtMechName.requestFocusInWindow();
/*  3469 */       this.SetSource = true;
/*  3470 */       return false;
/*       */     }
/*       */     
/*       */ 
/*  3474 */     if (!this.CurMech.ValidateECM()) {
/*  3475 */       Media.Messager("This 'Mech requires an ECM system of some sort to be valid.\nPlease install an ECM system.");
/*  3476 */       this.tbpMainTabPane.setSelectedComponent(this.pnlEquipment);
/*  3477 */       this.SetSource = true;
/*  3478 */       return false;
/*       */     }
/*       */     
/*       */ 
/*  3482 */     if (this.CurMech.IsOmnimech()) {
/*  3483 */       ArrayList v = this.CurMech.GetLoadouts();
/*  3484 */       for (int i = 0; i < v.size(); i++) {
/*  3485 */         this.CurMech.SetCurLoadout(((ifMechLoadout)v.get(i)).GetName());
/*  3486 */         if (this.CurMech.GetLoadout().GetQueue().size() != 0) {
/*  3487 */           Media.Messager(this, "You must place all items in the " + 
/*  3488 */             ((ifMechLoadout)v.get(i)).GetName() + " loadout first.");
/*  3489 */           this.cmbOmniVariant.setSelectedItem(((ifMechLoadout)v.get(i)).GetName());
/*  3490 */           cmbOmniVariantActionPerformed(evt);
/*  3491 */           this.tbpMainTabPane.setSelectedComponent(this.pnlCriticals);
/*  3492 */           this.SetSource = true;
/*  3493 */           return false;
/*       */         }
/*       */       }
/*       */     }
/*  3497 */     else if (this.CurMech.GetLoadout().GetQueue().size() != 0) {
/*  3498 */       Media.Messager(this, "You must place all items first.");
/*  3499 */       this.tbpMainTabPane.setSelectedComponent(this.pnlCriticals);
/*  3500 */       this.SetSource = true;
/*  3501 */       return false;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  3506 */     if (this.CurMech.IsOmnimech()) {
/*  3507 */       ArrayList v = this.CurMech.GetLoadouts();
/*  3508 */       for (int i = 0; i < v.size(); i++) {
/*  3509 */         this.CurMech.SetCurLoadout(((ifMechLoadout)v.get(i)).GetName());
/*  3510 */         if (this.CurMech.GetCurrentTons() > this.CurMech.GetTonnage()) {
/*  3511 */           Media.Messager(this, ((ifMechLoadout)v.get(i)).GetName() + " loadout is overweight.  Reduce the weight\nto equal or below the mech's tonnage.");
/*       */           
/*  3513 */           this.cmbOmniVariant.setSelectedItem(((ifMechLoadout)v.get(i)).GetName());
/*  3514 */           cmbOmniVariantActionPerformed(evt);
/*  3515 */           this.tbpMainTabPane.setSelectedComponent(this.pnlBasicSetup);
/*  3516 */           this.SetSource = true;
/*  3517 */           return false;
/*       */         }
/*       */       }
/*       */     }
/*  3521 */     else if (this.CurMech.GetCurrentTons() > this.CurMech.GetTonnage()) {
/*  3522 */       Media.Messager(this, "This mech is overweight.  Reduce the weight to\nequal or below the mech's tonnage.");
/*  3523 */       this.tbpMainTabPane.setSelectedComponent(this.pnlBasicSetup);
/*  3524 */       this.SetSource = true;
/*  3525 */       return false;
/*       */     }
/*       */     
/*  3528 */     if (this.CurMech.IsOmnimech()) {
/*  3529 */       this.CurMech.SetCurLoadout(CurLoadout);
/*       */     }
/*  3531 */     this.SetSource = true;
/*  3532 */     return true;
/*       */   }
/*       */   
/*       */   private void ConfigureUtilsMenu(java.awt.Component c)
/*       */   {
/*  3537 */     boolean armor = (LegalArmoring(this.CurItem)) && (CommonTools.IsAllowed(abPlaceable.ArmoredAC, this.CurMech));
/*  3538 */     boolean cap = (LegalCapacitor(this.CurItem)) && (CommonTools.IsAllowed(this.PPCCapAC, this.CurMech));
/*  3539 */     boolean insul = (LegalInsulator(this.CurItem)) && (CommonTools.IsAllowed(this.LIAC, this.CurMech));
/*  3540 */     boolean caseless = (LegalCaseless(this.CurItem)) && (CommonTools.IsAllowed(this.CaselessAmmoAC, this.CurMech));
/*  3541 */     boolean lotchange = LegalLotChange(this.CurItem);
/*  3542 */     boolean turreted = LegalTurretMount(this.CurItem);
/*  3543 */     boolean dumper = LegalDumper(this.CurItem);
/*  3544 */     this.mnuArmorComponent.setEnabled(armor);
/*  3545 */     this.mnuAddCapacitor.setEnabled(cap);
/*  3546 */     this.mnuAddInsulator.setEnabled(insul);
/*  3547 */     this.mnuCaseless.setEnabled(caseless);
/*  3548 */     this.mnuArmorComponent.setVisible(armor);
/*  3549 */     this.mnuAddCapacitor.setVisible(cap);
/*  3550 */     this.mnuAddInsulator.setVisible(insul);
/*  3551 */     this.mnuCaseless.setVisible(caseless);
/*  3552 */     this.mnuSetLotSize.setVisible(lotchange);
/*  3553 */     this.mnuTurret.setVisible(turreted);
/*  3554 */     this.mnuDumper.setVisible(dumper);
/*  3555 */     if (armor) {
/*  3556 */       if (this.CurItem.IsArmored()) {
/*  3557 */         this.mnuArmorComponent.setText("Unarmor Component");
/*       */       } else {
/*  3559 */         this.mnuArmorComponent.setText("Armor Component");
/*       */       }
/*       */     }
/*  3562 */     if ((turreted) && ((this.CurItem instanceof RangedWeapon))) {
/*  3563 */       if (((RangedWeapon)this.CurItem).IsTurreted()) {
/*  3564 */         this.mnuTurret.setText("Remove from Turret");
/*       */       } else {
/*  3566 */         this.mnuTurret.setText("Add to Turret");
/*       */       }
/*       */     }
/*  3569 */     if (((cap) || (insul) || (caseless)) && 
/*  3570 */       ((this.CurItem instanceof RangedWeapon))) {
/*  3571 */       if (((RangedWeapon)this.CurItem).IsUsingCapacitor()) {
/*  3572 */         this.mnuAddCapacitor.setText("Remove Capacitor");
/*       */       } else {
/*  3574 */         this.mnuAddCapacitor.setText("Add Capacitor");
/*       */       }
/*  3576 */       if (((RangedWeapon)this.CurItem).IsUsingInsulator()) {
/*  3577 */         this.mnuAddInsulator.setText("Remove Insulator");
/*       */       } else {
/*  3579 */         this.mnuAddInsulator.setText("Add Insulator");
/*       */       }
/*  3581 */       if (((RangedWeapon)this.CurItem).IsCaseless()) {
/*  3582 */         this.mnuCaseless.setText("Switch from Caseless");
/*       */       } else {
/*  3584 */         this.mnuCaseless.setText("Switch to Caseless");
/*       */       }
/*       */     }
/*       */     
/*  3588 */     if ((c == this.lstCTCrits) || (c == this.lstLTCrits) || (c == this.lstRTCrits)) {
/*  3589 */       if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3590 */         this.mnuVGLAmmo.setVisible(true);
/*  3591 */         this.mnuVGLArc.setVisible(true);
/*  3592 */         switch (((VehicularGrenadeLauncher)this.CurItem).GetAmmoType()) {
/*       */         case 1: 
/*  3594 */           this.mnuVGLAmmoFrag.setText("Fragmentation");
/*  3595 */           this.mnuVGLAmmoChaff.setText("* Chaff");
/*  3596 */           this.mnuVGLAmmoIncen.setText("Incendiary");
/*  3597 */           this.mnuVGLAmmoSmoke.setText("Smoke");
/*  3598 */           break;
/*       */         case 0: 
/*  3600 */           this.mnuVGLAmmoFrag.setText("* Fragmentation");
/*  3601 */           this.mnuVGLAmmoChaff.setText("Chaff");
/*  3602 */           this.mnuVGLAmmoIncen.setText("Incendiary");
/*  3603 */           this.mnuVGLAmmoSmoke.setText("Smoke");
/*  3604 */           break;
/*       */         case 2: 
/*  3606 */           this.mnuVGLAmmoFrag.setText("Fragmentation");
/*  3607 */           this.mnuVGLAmmoChaff.setText("Chaff");
/*  3608 */           this.mnuVGLAmmoIncen.setText("* Incendiary");
/*  3609 */           this.mnuVGLAmmoSmoke.setText("Smoke");
/*  3610 */           break;
/*       */         case 3: 
/*  3612 */           this.mnuVGLAmmoFrag.setText("Fragmentation");
/*  3613 */           this.mnuVGLAmmoChaff.setText("Chaff");
/*  3614 */           this.mnuVGLAmmoIncen.setText("Incendiary");
/*  3615 */           this.mnuVGLAmmoSmoke.setText("* Smoke");
/*       */         }
/*       */         
/*  3618 */         switch (((VehicularGrenadeLauncher)this.CurItem).GetCurrentArc()) {
/*       */         case 0: 
/*  3620 */           this.mnuVGLArcFore.setText("* Fore");
/*  3621 */           this.mnuVGLArcForeSide.setText("Fore-Side");
/*  3622 */           this.mnuVGLArcRear.setText("Rear");
/*  3623 */           this.mnuVGLArcRearSide.setText("Rear-Side");
/*  3624 */           break;
/*       */         case 2: 
/*  3626 */           this.mnuVGLArcFore.setText("Fore");
/*  3627 */           this.mnuVGLArcForeSide.setText("* Fore-Side");
/*  3628 */           this.mnuVGLArcRear.setText("Rear");
/*  3629 */           this.mnuVGLArcRearSide.setText("Rear-Side");
/*  3630 */           break;
/*       */         case 1: 
/*  3632 */           this.mnuVGLArcFore.setText("Fore");
/*  3633 */           this.mnuVGLArcForeSide.setText("Fore-Side");
/*  3634 */           this.mnuVGLArcRear.setText("* Rear");
/*  3635 */           this.mnuVGLArcRearSide.setText("Rear-Side");
/*  3636 */           break;
/*       */         case 3: 
/*  3638 */           this.mnuVGLArcFore.setText("Fore");
/*  3639 */           this.mnuVGLArcForeSide.setText("Fore-Side");
/*  3640 */           this.mnuVGLArcRear.setText("Rear");
/*  3641 */           this.mnuVGLArcRearSide.setText("* Rear-Side");
/*       */         }
/*       */       }
/*       */       else {
/*  3645 */         this.mnuVGLAmmo.setVisible(false);
/*  3646 */         this.mnuVGLArc.setVisible(false);
/*       */       }
/*       */     } else {
/*  3649 */       this.mnuVGLAmmo.setVisible(false);
/*  3650 */       this.mnuVGLArc.setVisible(false);
/*       */     }
/*  3652 */     if (this.CurMech.GetLoadout().Find(this.CurItem) < 11) {
/*  3653 */       if ((this.CurItem instanceof components.EmptyItem)) {
/*  3654 */         this.mnuUnallocateAll.setText("Unallocate All");
/*  3655 */         this.mnuUnallocateAll.setEnabled(false);
/*  3656 */       } else if (!this.CurItem.LocationLocked()) {
/*  3657 */         if (this.CurItem.Contiguous()) {
/*  3658 */           this.mnuUnallocateAll.setText("Unallocate " + this.CurItem.CritName());
/*       */         } else {
/*  3660 */           this.mnuUnallocateAll.setText("Unallocate All");
/*       */         }
/*  3662 */         this.mnuUnallocateAll.setEnabled(true);
/*       */       } else {
/*  3664 */         this.mnuUnallocateAll.setText("Unallocate All");
/*  3665 */         this.mnuUnallocateAll.setEnabled(false);
/*       */       }
/*  3667 */       if ((c == this.lstHDCrits) || (c == this.lstCTCrits) || (c == this.lstLTCrits) || (c == this.lstRTCrits) || (c == this.lstLLCrits) || (c == this.lstRLCrits) || ((this.CurMech.IsQuad()) && ((c == this.lstRACrits) || (c == this.lstLACrits)))) {
/*  3668 */         if (this.CurItem.CanMountRear()) {
/*  3669 */           this.mnuMountRear.setEnabled(true);
/*  3670 */           if (this.CurItem.IsMountedRear()) {
/*  3671 */             this.mnuMountRear.setText("Unmount Rear ");
/*       */           } else {
/*  3673 */             this.mnuMountRear.setText("Mount Rear ");
/*       */           }
/*       */         } else {
/*  3676 */           this.mnuMountRear.setEnabled(false);
/*  3677 */           this.mnuMountRear.setText("Mount Rear ");
/*       */         }
/*       */       } else {
/*  3680 */         this.mnuMountRear.setEnabled(false);
/*  3681 */         this.mnuMountRear.setText("Mount Rear ");
/*       */       }
/*  3683 */       if (this.CurItem.Contiguous()) {
/*  3684 */         components.EquipmentCollection C = this.CurMech.GetLoadout().GetCollection(this.CurItem);
/*  3685 */         if (C == null) {
/*  3686 */           this.mnuAuto.setEnabled(false);
/*  3687 */           this.mnuSelective.setEnabled(false);
/*       */         } else {
/*  3689 */           this.mnuAuto.setEnabled(true);
/*  3690 */           this.mnuSelective.setEnabled(true);
/*       */         }
/*       */       } else {
/*  3693 */         this.mnuSelective.setEnabled(true);
/*  3694 */         this.mnuAuto.setEnabled(true);
/*       */       }
/*       */     } else {
/*  3697 */       if (this.CurItem.Contiguous()) {
/*  3698 */         components.EquipmentCollection C = this.CurMech.GetLoadout().GetCollection(this.CurItem);
/*  3699 */         if (C == null) {
/*  3700 */           this.mnuAuto.setEnabled(false);
/*  3701 */           this.mnuSelective.setEnabled(false);
/*       */         } else {
/*  3703 */           this.mnuAuto.setEnabled(true);
/*  3704 */           this.mnuSelective.setEnabled(true);
/*       */         }
/*       */       } else {
/*  3707 */         this.mnuSelective.setEnabled(true);
/*  3708 */         this.mnuAuto.setEnabled(true);
/*       */       }
/*  3710 */       this.mnuUnallocateAll.setText("Unallocate All");
/*  3711 */       this.mnuUnallocateAll.setEnabled(false);
/*  3712 */       this.mnuMountRear.setEnabled(false);
/*  3713 */       this.mnuMountRear.setText("Mount Rear ");
/*       */     }
/*  3715 */     if ((this.CurItem instanceof Equipment)) {
/*  3716 */       if (((Equipment)this.CurItem).IsVariableSize()) {
/*  3717 */         this.mnuSetVariable.setVisible(true);
/*       */       } else {
/*  3719 */         this.mnuSetVariable.setVisible(false);
/*       */       }
/*       */     } else {
/*  3722 */       this.mnuSetVariable.setVisible(false);
/*       */     }
/*  3724 */     if ((this.CurItem.CoreComponent()) || (this.CurItem.LocationLinked())) {
/*  3725 */       this.mnuRemoveItem.setEnabled(false);
/*       */     } else {
/*  3727 */       this.mnuRemoveItem.setEnabled(true);
/*       */     }
/*       */   }
/*       */   
/*       */   private void SetAmmoLotSize() {
/*  3732 */     if ((this.CurItem instanceof Ammunition)) {
/*  3733 */       dlgAmmoLotSize ammo = new dlgAmmoLotSize(this, true, (Ammunition)this.CurItem);
/*  3734 */       ammo.setLocationRelativeTo(this);
/*  3735 */       ammo.setVisible(true);
/*       */     }
/*  3737 */     RefreshSummary();
/*  3738 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void ArmorComponent()
/*       */   {
/*  3743 */     if (this.CurItem.IsArmored()) {
/*  3744 */       this.CurItem.ArmorComponent(false);
/*       */     } else {
/*  3746 */       this.CurItem.ArmorComponent(true);
/*       */     }
/*  3748 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void PPCCapacitor()
/*       */   {
/*  3753 */     if ((this.CurItem instanceof RangedWeapon)) {
/*  3754 */       if (((RangedWeapon)this.CurItem).IsUsingCapacitor()) {
/*  3755 */         abPlaceable p = ((RangedWeapon)this.CurItem).GetCapacitor();
/*  3756 */         ((RangedWeapon)this.CurItem).UseCapacitor(false);
/*  3757 */         this.CurMech.GetLoadout().Remove(p);
/*       */       } else {
/*  3759 */         ((RangedWeapon)this.CurItem).UseCapacitor(true);
/*  3760 */         abPlaceable p = ((RangedWeapon)this.CurItem).GetCapacitor();
/*  3761 */         LocationIndex Loc = this.CurMech.GetLoadout().FindIndex(this.CurItem);
/*  3762 */         if (Loc.Location != -1) {
/*       */           try {
/*  3764 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(Loc.Location), p, Loc.Index + this.CurItem.NumCrits(), 1);
/*       */           }
/*       */           catch (Exception e) {
/*       */             try {
/*  3768 */               this.CurMech.GetLoadout().UnallocateAll(this.CurItem, false);
/*       */ 
/*       */ 
/*       */             }
/*       */             catch (Exception e1)
/*       */             {
/*       */ 
/*  3775 */               Media.Messager(this, "Fatal error adding a PPC Capacitor:\n" + e.getMessage() + "\nThe Capacitor will be removed.");
/*  3776 */               ((RangedWeapon)this.CurItem).UseCapacitor(false);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  3782 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void LaserInsulator()
/*       */   {
/*  3787 */     if ((this.CurItem instanceof RangedWeapon)) {
/*  3788 */       if (((RangedWeapon)this.CurItem).IsUsingInsulator()) {
/*  3789 */         abPlaceable p = ((RangedWeapon)this.CurItem).GetInsulator();
/*  3790 */         ((RangedWeapon)this.CurItem).UseInsulator(false);
/*  3791 */         this.CurMech.GetLoadout().Remove(p);
/*       */       } else {
/*  3793 */         ((RangedWeapon)this.CurItem).UseInsulator(true);
/*  3794 */         abPlaceable p = ((RangedWeapon)this.CurItem).GetInsulator();
/*  3795 */         LocationIndex Loc = this.CurMech.GetLoadout().FindIndex(this.CurItem);
/*  3796 */         if (Loc.Location != -1) {
/*       */           try {
/*  3798 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(Loc.Location), p, Loc.Index + this.CurItem.NumCrits(), 1);
/*       */           }
/*       */           catch (Exception e) {
/*       */             try {
/*  3802 */               this.CurMech.GetLoadout().UnallocateAll(this.CurItem, false);
/*       */ 
/*       */ 
/*       */             }
/*       */             catch (Exception e1)
/*       */             {
/*       */ 
/*  3809 */               Media.Messager(this, "Fatal error adding a Laser Insulator:\n" + e.getMessage() + "\nThe Insulator will be removed.");
/*  3810 */               ((RangedWeapon)this.CurItem).UseInsulator(false);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  3816 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void TurretMount() {
/*  3820 */     if ((this.CurItem instanceof RangedWeapon)) {
/*  3821 */       RangedWeapon w = (RangedWeapon)this.CurItem;
/*  3822 */       int location = this.CurMech.GetLoadout().Find(this.CurItem);
/*  3823 */       if (w.IsTurreted()) {
/*  3824 */         if (location == 0) {
/*  3825 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetHDTurret());
/*  3826 */         } else if (location == 2) {
/*  3827 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetLTTurret());
/*  3828 */         } else if (location == 3) {
/*  3829 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetRTTurret());
/*       */         } else {
/*  3831 */           Media.Messager(this, "Cannot remove from turret!");
/*       */         }
/*       */         
/*       */       }
/*  3835 */       else if (location == 0) {
/*  3836 */         w.AddToTurret(this.CurMech.GetLoadout().GetHDTurret());
/*  3837 */       } else if (location == 2) {
/*  3838 */         w.AddToTurret(this.CurMech.GetLoadout().GetLTTurret());
/*  3839 */       } else if (location == 3) {
/*  3840 */         w.AddToTurret(this.CurMech.GetLoadout().GetRTTurret());
/*       */       } else {
/*  3842 */         Media.Messager(this, "Cannot add to turret!");
/*  3843 */         return;
/*       */       }
/*       */     }
/*  3846 */     else if ((this.CurItem instanceof components.MGArray)) {
/*  3847 */       components.MGArray w = (components.MGArray)this.CurItem;
/*  3848 */       int location = this.CurMech.GetLoadout().Find(this.CurItem);
/*  3849 */       if (w.IsTurreted()) {
/*  3850 */         if (location == 0) {
/*  3851 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetHDTurret());
/*  3852 */         } else if (location == 2) {
/*  3853 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetLTTurret());
/*  3854 */         } else if (location == 3) {
/*  3855 */           w.RemoveFromTurret(this.CurMech.GetLoadout().GetRTTurret());
/*       */         } else {
/*  3857 */           Media.Messager(this, "Cannot remove from turret!");
/*       */         }
/*       */         
/*       */       }
/*  3861 */       else if (location == 0) {
/*  3862 */         w.AddToTurret(this.CurMech.GetLoadout().GetHDTurret());
/*  3863 */       } else if (location == 2) {
/*  3864 */         w.AddToTurret(this.CurMech.GetLoadout().GetLTTurret());
/*  3865 */       } else if (location == 3) {
/*  3866 */         w.AddToTurret(this.CurMech.GetLoadout().GetRTTurret());
/*       */       } else {
/*  3868 */         Media.Messager(this, "Cannot add to turret!");
/*  3869 */         return;
/*       */       }
/*       */     }
/*       */     
/*  3873 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void DumperMount() {
/*  3877 */     if ((this.CurItem instanceof Equipment)) {}
/*       */   }
/*       */   
/*       */ 
/*       */   private void SwitchCaseless()
/*       */   {
/*  3883 */     if ((this.CurItem instanceof RangedWeapon)) {
/*  3884 */       RangedWeapon r = (RangedWeapon)this.CurItem;
/*       */       
/*  3886 */       int origIDX = r.GetAmmoIndex();
/*       */       
/*       */ 
/*  3889 */       r.SetCaseless(!r.IsCaseless());
/*  3890 */       int newIDX = r.GetAmmoIndex();
/*       */       
/*       */ 
/*  3893 */       ArrayList check = this.CurMech.GetLoadout().GetNonCore();
/*  3894 */       ArrayList replace = new ArrayList();
/*       */       
/*  3896 */       boolean HasOrig = false;
/*  3897 */       for (int i = 0; i < check.size(); i++) {
/*  3898 */         abPlaceable p = (abPlaceable)check.get(i);
/*  3899 */         if (((p instanceof RangedWeapon)) && 
/*  3900 */           (((RangedWeapon)p).GetAmmoIndex() == origIDX)) {
/*  3901 */           HasOrig = true;
/*       */         }
/*       */         
/*  3904 */         if ((p instanceof Ammunition)) {
/*  3905 */           replace.add(p);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  3910 */       if (!HasOrig) {
/*  3911 */         Object[] newammo = this.data.GetEquipment().GetAmmo(newIDX, this.CurMech);
/*  3912 */         for (int i = 0; i < replace.size(); i++) {
/*  3913 */           abPlaceable p = (abPlaceable)replace.get(i);
/*  3914 */           if (((Ammunition)p).GetAmmoIndex() == origIDX) {
/*  3915 */             this.CurMech.GetLoadout().Remove(p);
/*  3916 */             if (newammo.length > 0) {
/*  3917 */               p = this.data.GetEquipment().GetCopy((abPlaceable)newammo[0], this.CurMech);
/*  3918 */               this.CurMech.GetLoadout().AddToQueue(p);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  3924 */     RefreshSummary();
/*  3925 */     RefreshInfoPane();
/*  3926 */     SetWeaponChoosers();
/*  3927 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   public void SetVGLArcFore() {
/*  3931 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3932 */       ((VehicularGrenadeLauncher)this.CurItem).SetArcFore();
/*  3933 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLArcRear() {
/*  3938 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3939 */       ((VehicularGrenadeLauncher)this.CurItem).SetArcRear();
/*  3940 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLArcForeSide() {
/*  3945 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3946 */       ((VehicularGrenadeLauncher)this.CurItem).SetArcForeSide();
/*  3947 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLArcRearSide() {
/*  3952 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3953 */       ((VehicularGrenadeLauncher)this.CurItem).SetArcRearSide();
/*  3954 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLAmmoFrag() {
/*  3959 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3960 */       ((VehicularGrenadeLauncher)this.CurItem).SetAmmoFrag();
/*  3961 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLAmmoChaff() {
/*  3966 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3967 */       ((VehicularGrenadeLauncher)this.CurItem).SetAmmoChaff();
/*  3968 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLAmmoIncendiary() {
/*  3973 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3974 */       ((VehicularGrenadeLauncher)this.CurItem).SetAmmoIncen();
/*  3975 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVGLAmmoSmoke() {
/*  3980 */     if ((this.CurItem instanceof VehicularGrenadeLauncher)) {
/*  3981 */       ((VehicularGrenadeLauncher)this.CurItem).SetAmmoSmoke();
/*  3982 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */   public void SetVariableSize() {
/*  3987 */     if (((this.CurItem instanceof Equipment)) && 
/*  3988 */       (((Equipment)this.CurItem).IsVariableSize())) {
/*  3989 */       dlgVariableSize SetTons = new dlgVariableSize(this, true, (Equipment)this.CurItem);
/*  3990 */       SetTons.setLocationRelativeTo(this);
/*  3991 */       SetTons.setVisible(true);
/*  3992 */       LocationIndex li = this.CurMech.GetLoadout().FindIndex(this.CurItem);
/*  3993 */       if (li.Location >= 0) {
/*       */         try {
/*  3995 */           this.CurMech.GetLoadout().UnallocateAll(this.CurItem, false);
/*  3996 */           this.CurMech.GetLoadout().AddTo(this.CurItem, li.Location, li.Index);
/*       */         } catch (Exception e) {
/*  3998 */           this.CurMech.GetLoadout().UnallocateAll(this.CurItem, true);
/*       */         }
/*       */       }
/*  4001 */       RefreshInfoPane();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public boolean LegalArmoring(abPlaceable p)
/*       */   {
/*  4008 */     if (p.CanArmor()) {
/*  4009 */       if (this.CurMech.GetLoadout().GetName().equals("Base Loadout")) {
/*  4010 */         return true;
/*       */       }
/*  4012 */       if ((p instanceof Engine)) return false;
/*  4013 */       if ((p instanceof components.Gyro)) return false;
/*  4014 */       if ((p instanceof Cockpit)) return false;
/*  4015 */       if ((p instanceof components.Actuator)) {
/*  4016 */         if (!((components.Actuator)p).IsOmniArmorable()) return false;
/*  4017 */         LocationIndex Loc = this.CurMech.GetLoadout().FindIndex(p);
/*  4018 */         if ((Loc.Location == 4) && (Loc.Index == 2) && 
/*  4019 */           (this.CurMech.GetBaseLoadout().GetActuators().LeftLowerInstalled())) { return false;
/*       */         }
/*  4021 */         if ((Loc.Location == 5) && (Loc.Index == 2) && 
/*  4022 */           (this.CurMech.GetBaseLoadout().GetActuators().RightLowerInstalled())) { return false;
/*       */         }
/*  4024 */         if ((Loc.Location == 4) && (Loc.Index == 3) && 
/*  4025 */           (this.CurMech.GetBaseLoadout().GetActuators().LeftHandInstalled())) { return false;
/*       */         }
/*  4027 */         if ((Loc.Location == 5) && (Loc.Index == 3) && 
/*  4028 */           (this.CurMech.GetBaseLoadout().GetActuators().RightHandInstalled())) { return false;
/*       */         }
/*       */       }
/*  4031 */       if ((p instanceof components.SimplePlaceable)) return false;
/*  4032 */       if (this.CurMech.GetBaseLoadout().GetNonCore().contains(p)) return false;
/*  4033 */       return true;
/*       */     }
/*       */     
/*  4036 */     return false;
/*       */   }
/*       */   
/*       */   public boolean LegalCapacitor(abPlaceable p)
/*       */   {
/*  4041 */     if (!(p instanceof RangedWeapon)) return false;
/*  4042 */     return ((RangedWeapon)p).CanUseCapacitor();
/*       */   }
/*       */   
/*       */   public boolean LegalInsulator(abPlaceable p) {
/*  4046 */     if (!(p instanceof RangedWeapon)) return false;
/*  4047 */     return ((RangedWeapon)p).CanUseInsulator();
/*       */   }
/*       */   
/*       */   public boolean LegalCaseless(abPlaceable p) {
/*  4051 */     if (!(p instanceof RangedWeapon)) return false;
/*  4052 */     return ((RangedWeapon)p).CanUseCaselessAmmo();
/*       */   }
/*       */   
/*       */   public boolean LegalTurretMount(abPlaceable p) {
/*  4056 */     if ((!(p instanceof RangedWeapon)) && (!(p instanceof components.MGArray))) return false;
/*  4057 */     int location = this.CurMech.GetLoadout().Find(p);
/*  4058 */     if (location == 0) {
/*  4059 */       if ((this.CurMech.IsOmnimech()) && 
/*  4060 */         (this.CurMech.GetBaseLoadout().GetHDTurret() == this.CurMech.GetLoadout().GetHDTurret())) {
/*  4061 */         return false;
/*       */       }
/*       */       
/*  4064 */       return this.CurMech.GetLoadout().HasHDTurret();
/*       */     }
/*  4066 */     if (location == 2) {
/*  4067 */       if ((this.CurMech.IsOmnimech()) && 
/*  4068 */         (this.CurMech.GetBaseLoadout().GetLTTurret() == this.CurMech.GetLoadout().GetLTTurret())) {
/*  4069 */         return false;
/*       */       }
/*       */       
/*  4072 */       return this.CurMech.GetLoadout().HasLTTurret();
/*       */     }
/*  4074 */     if (location == 3) {
/*  4075 */       if ((this.CurMech.IsOmnimech()) && 
/*  4076 */         (this.CurMech.GetBaseLoadout().GetRTTurret() == this.CurMech.GetLoadout().GetRTTurret())) {
/*  4077 */         return false;
/*       */       }
/*       */       
/*  4080 */       return this.CurMech.GetLoadout().HasRTTurret();
/*       */     }
/*  4082 */     return false;
/*       */   }
/*       */   
/*       */   public boolean LegalLotChange(abPlaceable p) {
/*  4086 */     if (!(p instanceof Ammunition)) return false;
/*  4087 */     if (this.CurMech.UsingFractionalAccounting()) return true;
/*  4088 */     return false;
/*       */   }
/*       */   
/*       */   public boolean LegalDumper(abPlaceable p) {
/*  4092 */     if (!(p instanceof Equipment)) return false;
/*  4093 */     if (((Equipment)p).CritName().equals("Cargo Container")) return true;
/*  4094 */     return false;
/*       */   }
/*       */   
/*       */   private void PrintMech(Mech m) {
/*  4098 */     ssw.print.Printer printer = new ssw.print.Printer(this);
/*  4099 */     printer.Print(m);
/*       */   }
/*       */   
/*       */   private void UpdateBasicChart() {
/*  4103 */     int[] fchart = null;int[] lchart = null;int[] rchart = null;int[] rrchart = null;
/*  4104 */     if (this.chkChartFront.isSelected()) {
/*  4105 */       fchart = GetFrontDamageChart();
/*       */     }
/*  4107 */     if (this.chkChartRear.isSelected()) {
/*  4108 */       rrchart = GetRearDamageChart();
/*       */     }
/*  4110 */     if (this.chkChartRight.isSelected()) {
/*  4111 */       rchart = GetRightArmDamageChart();
/*       */     }
/*  4113 */     if (this.chkChartLeft.isSelected()) {
/*  4114 */       lchart = GetLeftArmDamageChart();
/*       */     }
/*  4116 */     int gridx = 1;
/*  4117 */     int gridy = 1;
/*  4118 */     if (fchart != null) {
/*  4119 */       for (int i = 0; i < fchart.length; i++) {
/*  4120 */         if (fchart[i] > 0) {
/*  4121 */           if (fchart[i] > gridy) gridy = fchart[i];
/*  4122 */           if (i > gridx) gridx = i;
/*       */         }
/*       */       }
/*       */     }
/*  4126 */     if (rchart != null) {
/*  4127 */       for (int i = 0; i < rchart.length; i++) {
/*  4128 */         if (rchart[i] > 0) {
/*  4129 */           if (rchart[i] > gridy) gridy = rchart[i];
/*  4130 */           if (i > gridx) gridx = i;
/*       */         }
/*       */       }
/*       */     }
/*  4134 */     if (lchart != null) {
/*  4135 */       for (int i = 0; i < lchart.length; i++) {
/*  4136 */         if (lchart[i] > 0) {
/*  4137 */           if (lchart[i] > gridy) gridy = lchart[i];
/*  4138 */           if (i > gridx) gridx = i;
/*       */         }
/*       */       }
/*       */     }
/*  4142 */     if (rrchart != null) {
/*  4143 */       for (int i = 0; i < rrchart.length; i++) {
/*  4144 */         if (rrchart[i] > 0) {
/*  4145 */           if (rrchart[i] > gridy) gridy = rrchart[i];
/*  4146 */           if (i > gridx) gridx = i;
/*       */         }
/*       */       }
/*       */     }
/*  4150 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*  4151 */     int TotalDamage = 0;
/*  4152 */     double TonsWeapons = 0.0D;double TonsEquips = 0.0D;
/*       */     
/*  4154 */     for (int i = 0; i < v.size(); i++) {
/*  4155 */       abPlaceable p = (abPlaceable)v.get(i);
/*       */       
/*       */ 
/*  4158 */       if ((p instanceof ifWeapon))
/*       */       {
/*  4160 */         TonsWeapons += p.GetTonnage();
/*  4161 */         TotalDamage += GetMaxDamage((ifWeapon)p);
/*       */       }
/*  4163 */       else if ((p instanceof Ammunition)) {
/*  4164 */         TonsWeapons += p.GetTonnage();
/*       */       } else {
/*  4166 */         TonsEquips += p.GetTonnage();
/*       */       }
/*       */     }
/*       */     
/*  4170 */     TonsEquips += this.CurMech.GetCaseTonnage();
/*  4171 */     TonsEquips += this.CurMech.GetCASEIITonnage();
/*  4172 */     TonsEquips += this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons();
/*       */     
/*  4174 */     ((DamageChart)this.pnlDamageChart).ClearCharts();
/*  4175 */     if (this.chkShowTextNotGraph.isSelected()) {
/*  4176 */       ((DamageChart)this.pnlDamageChart).SetTextView(true);
/*  4177 */       if (this.chkChartFront.isSelected()) {
/*  4178 */         ((DamageChart)this.pnlDamageChart).AddChart(fchart, Color.RED);
/*       */       }
/*  4180 */       if (this.chkChartRight.isSelected()) {
/*  4181 */         ((DamageChart)this.pnlDamageChart).AddChart(rchart, Color.GREEN);
/*       */       }
/*  4183 */       if (this.chkChartLeft.isSelected()) {
/*  4184 */         ((DamageChart)this.pnlDamageChart).AddChart(lchart, Color.ORANGE);
/*       */       }
/*  4186 */       if (this.chkChartRear.isSelected()) {
/*  4187 */         ((DamageChart)this.pnlDamageChart).AddChart(rrchart, Color.PINK);
/*       */       }
/*       */     } else {
/*  4190 */       ((DamageChart)this.pnlDamageChart).SetTextView(false);
/*  4191 */       ((DamageChart)this.pnlDamageChart).SetGridSize(gridx + 1, gridy + 1);
/*  4192 */       if (this.chkChartRear.isSelected()) {
/*  4193 */         ((DamageChart)this.pnlDamageChart).AddChart(rrchart, Color.PINK);
/*       */       }
/*  4195 */       if (this.chkChartLeft.isSelected()) {
/*  4196 */         ((DamageChart)this.pnlDamageChart).AddChart(lchart, Color.ORANGE);
/*       */       }
/*  4198 */       if (this.chkChartRight.isSelected()) {
/*  4199 */         ((DamageChart)this.pnlDamageChart).AddChart(rchart, Color.GREEN);
/*       */       }
/*  4201 */       if (this.chkChartFront.isSelected()) {
/*  4202 */         ((DamageChart)this.pnlDamageChart).AddChart(fchart, Color.RED);
/*       */       }
/*       */     }
/*  4205 */     this.lblTonPercStructure.setText(String.format("%1$,.2f", new Object[] { Double.valueOf((this.CurMech.GetIntStruc().GetTonnage() + this.CurMech.GetCockpit().GetTonnage() + this.CurMech.GetGyro().GetTonnage()) / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4206 */     this.lblTonPercEngine.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetEngine().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4207 */     this.lblTonPercHeatSinks.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetHeatSinks().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4208 */     this.lblTonPercEnhance.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetPhysEnhance().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4209 */     this.lblTonPercArmor.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetArmor().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4210 */     this.lblTonPercJumpJets.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(this.CurMech.GetJumpJets().GetTonnage() / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4211 */     this.lblTonPercWeapons.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(TonsWeapons / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4212 */     this.lblTonPercEquips.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(TonsEquips / this.CurMech.GetTonnage() * 100.0D) }) + "%");
/*  4213 */     this.lblDamagePerTon.setText(String.format("%1$,.2f", new Object[] { Double.valueOf(TotalDamage / this.CurMech.GetTonnage()) }));
/*       */   }
/*       */   
/*       */   private int GetMaxDamage(ifWeapon w) {
/*  4217 */     int mult = 1;
/*  4218 */     if (w.IsUltra()) {
/*  4219 */       mult = 2;
/*       */     }
/*  4221 */     if (w.IsRotary()) {
/*  4222 */       mult = 6;
/*       */     }
/*  4224 */     if ((w.GetDamageLong() >= w.GetDamageMedium()) && (w.GetDamageLong() >= w.GetDamageShort())) {
/*  4225 */       if (w.GetWeaponClass() == 2) {
/*  4226 */         return w.GetDamageLong() * mult * w.ClusterSize();
/*       */       }
/*  4228 */       return w.GetDamageLong() * mult;
/*       */     }
/*       */     
/*  4231 */     if ((w.GetDamageMedium() >= w.GetDamageLong()) && (w.GetDamageMedium() >= w.GetDamageShort())) {
/*  4232 */       if (w.GetWeaponClass() == 2) {
/*  4233 */         return w.GetDamageMedium() * mult * w.ClusterSize();
/*       */       }
/*  4235 */       return w.GetDamageMedium() * mult;
/*       */     }
/*       */     
/*  4238 */     if (w.GetWeaponClass() == 2) {
/*  4239 */       return w.GetDamageShort() * mult * w.ClusterSize();
/*       */     }
/*  4241 */     return w.GetDamageShort() * mult;
/*       */   }
/*       */   
/*       */   private JCheckBox chkCLCASE2;
/*       */   private JCheckBox chkCLPS;
/*       */   private JCheckBox chkCTCASE;
/*       */   private JCheckBox chkCTCASE2;
/*       */   
/*       */   public void lostOwnership(java.awt.datatransfer.Clipboard aClipboard, java.awt.datatransfer.Transferable aContents) {}
/*       */   
/*       */   public int GetLocation(JList list) {
/*  4252 */     if (list == this.lstHDCrits) return 0;
/*  4253 */     if (list == this.lstCTCrits) return 1;
/*  4254 */     if (list == this.lstLTCrits) return 2;
/*  4255 */     if (list == this.lstRTCrits) return 3;
/*  4256 */     if (list == this.lstLACrits) return 4;
/*  4257 */     if (list == this.lstRACrits) return 5;
/*  4258 */     if (list == this.lstLLCrits) return 6;
/*  4259 */     if (list == this.lstRLCrits) return 7;
/*  4260 */     return -1;
/*       */   }
/*       */   
/*       */   public void CheckTonnage(boolean RulesChange)
/*       */   {
/*  4265 */     if (RulesChange) {
/*  4266 */       if ((!this.CurMech.IsIndustrialmech()) && 
/*  4267 */         (this.CurMech.GetRulesLevel() < 3) && (this.CurMech.GetTonnage() < 20)) {
/*  4268 */         this.cmbTonnage.setSelectedItem("20");
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*  4273 */     else if (!this.CurMech.IsIndustrialmech())
/*       */     {
/*  4275 */       if ((this.CurMech.GetRulesLevel() < 3) && (this.CurMech.GetTonnage() < 20) && 
/*  4276 */         (this.CurMech.GetTonnage() < 20)) {
/*  4277 */         this.cmbRulesLevel.setSelectedIndex(3);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void CheckFileName(String s)
/*       */     throws Exception
/*       */   {
/*  4285 */     if (s.contains("\\")) {
/*  4286 */       throw new Exception("The Mech name or model contains a back slash\nwhich should be removed before saving.");
/*       */     }
/*  4288 */     if (s.contains("/")) {
/*  4289 */       throw new Exception("The Mech name or model contains a forward slash\nwhich should be removed before saving.");
/*       */     }
/*  4291 */     if (s.contains("*")) {
/*  4292 */       throw new Exception("The Mech name or model contains an asterisk\nwhich should be removed before saving.");
/*       */     }
/*       */   }
/*       */   
/*       */   private void SolidifyJJManufacturer()
/*       */   {
/*  4298 */     if (((!this.txtJJModel.getText().equals("")) || (!this.CurMech.GetJJModel().equals(""))) && 
/*  4299 */       (!this.txtJJModel.getText().equals(this.CurMech.GetJJModel()))) {
/*  4300 */       this.CurMech.SetJJModel(this.txtJJModel.getText());
/*       */     }
/*       */     
/*  4303 */     this.txtJJModel.setText(this.CurMech.GetJJModel());
/*       */   }
/*       */   
/*       */   private int[] GetFrontDamageChart()
/*       */   {
/*  4308 */     int[] chart = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*       */     
/*       */ 
/*  4311 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*       */     
/*  4313 */     for (int i = 0; i < 40; i++) {
/*  4314 */       for (int j = 0; j < v.size(); j++) {
/*  4315 */         if ((v.get(j) instanceof ifWeapon)) {
/*  4316 */           ifWeapon w = (ifWeapon)v.get(j);
/*  4317 */           if (!((abPlaceable)w).IsMountedRear()) {
/*  4318 */             if (this.chkAverageDamage.isSelected()) {
/*  4319 */               chart[i] += CommonTools.GetAverageDamageAtRange(w, i);
/*       */             } else {
/*  4321 */               chart[i] += GetDamageAtRange(w, i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  4327 */     return chart;
/*       */   }
/*       */   
/*       */   private int[] GetRightArmDamageChart()
/*       */   {
/*  4332 */     int[] chart = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*       */     
/*       */ 
/*  4335 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*       */     
/*  4337 */     for (int i = 0; i < 40; i++) {
/*  4338 */       for (int j = 0; j < v.size(); j++) {
/*  4339 */         if ((v.get(j) instanceof ifWeapon)) {
/*  4340 */           ifWeapon w = (ifWeapon)v.get(j);
/*  4341 */           if (this.CurMech.GetLoadout().Find((abPlaceable)w) == 5) {
/*  4342 */             if (this.chkAverageDamage.isSelected()) {
/*  4343 */               chart[i] += CommonTools.GetAverageDamageAtRange(w, i);
/*       */             } else {
/*  4345 */               chart[i] += GetDamageAtRange(w, i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  4351 */     return chart;
/*       */   }
/*       */   
/*       */   private int[] GetLeftArmDamageChart()
/*       */   {
/*  4356 */     int[] chart = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*       */     
/*       */ 
/*  4359 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*       */     
/*  4361 */     for (int i = 0; i < 40; i++) {
/*  4362 */       for (int j = 0; j < v.size(); j++) {
/*  4363 */         if ((v.get(j) instanceof ifWeapon)) {
/*  4364 */           ifWeapon w = (ifWeapon)v.get(j);
/*  4365 */           if (this.CurMech.GetLoadout().Find((abPlaceable)w) == 4) {
/*  4366 */             if (this.chkAverageDamage.isSelected()) {
/*  4367 */               chart[i] += CommonTools.GetAverageDamageAtRange(w, i);
/*       */             } else {
/*  4369 */               chart[i] += GetDamageAtRange(w, i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  4375 */     return chart;
/*       */   }
/*       */   
/*       */   private int[] GetRearDamageChart()
/*       */   {
/*  4380 */     int[] chart = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*       */     
/*       */ 
/*  4383 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*  4384 */     boolean flip = (!this.CurMech.GetLoadout().GetActuators().LeftLowerInstalled()) || (!this.CurMech.GetLoadout().GetActuators().RightLowerInstalled());
/*       */     
/*  4386 */     for (int i = 0; i < 40; i++) {
/*  4387 */       for (int j = 0; j < v.size(); j++) {
/*  4388 */         if ((v.get(j) instanceof ifWeapon)) {
/*  4389 */           ifWeapon w = (ifWeapon)v.get(j);
/*  4390 */           int Loc = this.CurMech.GetLoadout().Find((abPlaceable)w);
/*  4391 */           if ((((abPlaceable)w).IsMountedRear()) || (((Loc == 4) || (Loc == 5)) && (flip))) {
/*  4392 */             if (this.chkAverageDamage.isSelected()) {
/*  4393 */               chart[i] += CommonTools.GetAverageDamageAtRange(w, i);
/*       */             } else {
/*  4395 */               chart[i] += GetDamageAtRange(w, i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  4401 */     return chart;
/*       */   }
/*       */   
/*       */   private int GetDamageAtRange(ifWeapon w, int range) {
/*  4405 */     int mult = 1;
/*  4406 */     if (w.IsUltra()) {
/*  4407 */       mult = 2;
/*       */     }
/*  4409 */     if (w.IsRotary()) {
/*  4410 */       mult = 6;
/*       */     }
/*  4412 */     if ((w instanceof components.MGArray)) {
/*  4413 */       mult = ((components.MGArray)w).GetNumMGs();
/*       */     }
/*       */     
/*  4416 */     if (w.GetRangeLong() <= 0) {
/*  4417 */       if (w.GetRangeMedium() <= 0) {
/*  4418 */         if (range <= w.GetRangeShort()) {
/*  4419 */           if (w.GetWeaponClass() == 2) {
/*  4420 */             return w.GetDamageShort() * mult * w.ClusterSize();
/*       */           }
/*  4422 */           return w.GetDamageShort() * mult;
/*       */         }
/*       */         
/*  4425 */         return 0;
/*       */       }
/*       */       
/*  4428 */       if (range <= w.GetRangeShort()) {
/*  4429 */         if (w.GetWeaponClass() == 2) {
/*  4430 */           return w.GetDamageShort() * mult * w.ClusterSize();
/*       */         }
/*  4432 */         return w.GetDamageShort() * mult;
/*       */       }
/*  4434 */       if (range <= w.GetRangeMedium()) {
/*  4435 */         if (w.GetWeaponClass() == 2) {
/*  4436 */           return w.GetDamageMedium() * mult * w.ClusterSize();
/*       */         }
/*  4438 */         return w.GetDamageMedium() * mult;
/*       */       }
/*       */       
/*  4441 */       return 0;
/*       */     }
/*       */     
/*       */ 
/*  4445 */     if (range <= w.GetRangeShort()) {
/*  4446 */       if (w.GetWeaponClass() == 2) {
/*  4447 */         return w.GetDamageShort() * mult * w.ClusterSize();
/*       */       }
/*  4449 */       return w.GetDamageShort() * mult;
/*       */     }
/*  4451 */     if (range <= w.GetRangeMedium()) {
/*  4452 */       if (w.GetWeaponClass() == 2) {
/*  4453 */         return w.GetDamageMedium() * mult * w.ClusterSize();
/*       */       }
/*  4455 */       return w.GetDamageMedium() * mult;
/*       */     }
/*  4457 */     if (range <= w.GetRangeLong()) {
/*  4458 */       if (w.GetWeaponClass() == 2) {
/*  4459 */         return w.GetDamageLong() * mult * w.ClusterSize();
/*       */       }
/*  4461 */       return w.GetDamageLong() * mult;
/*       */     }
/*       */     
/*  4464 */     return 0;
/*       */   }
/*       */   
/*       */   private JCheckBox chkChartFront;
/*       */   private JCheckBox chkChartLeft;
/*       */   
/*  4470 */   private File GetSaveFile(final String extension, String path, boolean autooverwrite, boolean singleloadout) { String filename = "";
/*  4471 */     boolean overwrite = false;
/*       */     
/*       */ 
/*  4474 */     SolidifyMech();
/*  4475 */     if (!VerifyMech(null)) {
/*  4476 */       return null;
/*       */     }
/*       */     
/*       */ 
/*  4480 */     if ((this.CurMech.IsOmnimech()) && (singleloadout)) {
/*  4481 */       if (this.CurMech.GetModel().isEmpty()) {
/*  4482 */         filename = this.CurMech.GetName() + " " + this.CurMech.GetLoadout().GetName() + "." + extension;
/*       */       }
/*       */       else {
/*  4485 */         filename = this.CurMech.GetName() + " " + this.CurMech.GetModel() + " " + this.CurMech.GetLoadout().GetName() + "." + extension;
/*       */       }
/*       */     }
/*  4488 */     else if (this.CurMech.GetModel().isEmpty()) {
/*  4489 */       filename = this.CurMech.GetName() + "." + extension;
/*       */     } else {
/*  4491 */       filename = this.CurMech.GetName() + " " + this.CurMech.GetModel() + "." + extension;
/*       */     }
/*       */     
/*       */ 
/*  4495 */     filename = CommonTools.FormatFileName(filename);
/*       */     
/*       */     try
/*       */     {
/*  4499 */       CheckFileName(filename);
/*       */     } catch (Exception e) {
/*  4501 */       Media.Messager(this, "There was a problem with the filename:\n" + e
/*  4502 */         .getMessage() + "\nSaving will continue but you should change the filename.");
/*       */     }
/*       */     
/*       */ 
/*  4506 */     if (autooverwrite) {
/*  4507 */       File testfile = new File(path + File.separator + filename);
/*  4508 */       if (testfile.exists()) {
/*  4509 */         int choice = javax.swing.JOptionPane.showConfirmDialog(this, "A file with the specified name already exists\n" + testfile + "\nDo you want to overwrite it?", "Overwrite file", 0);
/*       */         
/*       */ 
/*  4512 */         if (choice != 1) {
/*  4513 */           overwrite = true;
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*  4518 */     File retval = null;
/*  4519 */     if ((autooverwrite) && (overwrite)) {
/*  4520 */       retval = new File(path + File.separator + filename);
/*       */     }
/*       */     else {
/*  4523 */       File directory = new File(path);
/*  4524 */       JFileChooser fc = new JFileChooser();
/*  4525 */       fc.setCurrentDirectory(directory);
/*  4526 */       fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter()
/*       */       {
/*       */         public boolean accept(File f) {
/*  4529 */           if (f.isDirectory()) {
/*  4530 */             return true;
/*       */           }
/*       */           
/*  4533 */           String checkext = Utils.getExtension(f);
/*  4534 */           if (checkext != null) {
/*  4535 */             if (checkext.equals(extension)) {
/*  4536 */               return true;
/*       */             }
/*  4538 */             return false;
/*       */           }
/*       */           
/*  4541 */           return false;
/*       */         }
/*       */         
/*       */         public String getDescription()
/*       */         {
/*  4546 */           return "*." + extension;
/*       */         }
/*  4548 */       });
/*  4549 */       fc.setAcceptAllFileFilterUsed(false);
/*  4550 */       fc.setSelectedFile(new File(filename));
/*       */       
/*       */ 
/*  4553 */       int returnval = fc.showDialog(this, "Save to " + extension);
/*  4554 */       if (returnval != 0) return null;
/*  4555 */       retval = fc.getSelectedFile();
/*  4556 */       if (retval.exists()) {
/*  4557 */         int choice = javax.swing.JOptionPane.showConfirmDialog(this, "A file with the specified name already exists\n" + retval + "\nDo you want to overwrite it?", "Overwrite file", 0);
/*       */         
/*       */ 
/*  4560 */         if (choice == 1) {
/*  4561 */           Media.Messager(this, "The 'Mech was not saved.");
/*  4562 */           return null;
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*  4567 */     return retval; }
/*       */   
/*       */   private JCheckBox chkChartRear;
/*       */   
/*  4571 */   private void FluffCut(java.awt.Component c) { String cut = "";
/*  4572 */     if ((c instanceof JEditorPane)) {
/*  4573 */       JEditorPane j = (JEditorPane)c;
/*  4574 */       if (j.getSelectedText() == null)
/*       */       {
/*  4576 */         cut = j.getText();
/*  4577 */         j.setText("");
/*       */       }
/*       */       else {
/*  4580 */         cut = j.getSelectedText();
/*  4581 */         j.setText(j.getText().replace(cut, ""));
/*       */       }
/*       */     }
/*  4584 */     if ((c instanceof JTextField)) {
/*  4585 */       JTextField j = (JTextField)c;
/*  4586 */       if (j.getSelectedText() == null)
/*       */       {
/*  4588 */         cut = j.getText();
/*  4589 */         j.setText("");
/*       */       }
/*       */       else {
/*  4592 */         cut = j.getSelectedText();
/*  4593 */         j.setText(j.getText().replace(cut, ""));
/*       */       }
/*       */     }
/*  4596 */     java.awt.datatransfer.StringSelection export = new java.awt.datatransfer.StringSelection(cut);
/*  4597 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/*  4598 */     clipboard.setContents(export, this); }
/*       */   
/*       */   private JCheckBox chkChartRight;
/*       */   private JCheckBox chkClanCASE;
/*  4602 */   private void FluffCopy(java.awt.Component c) { String copy = "";
/*  4603 */     if ((c instanceof JEditorPane)) {
/*  4604 */       JEditorPane j = (JEditorPane)c;
/*  4605 */       if (j.getSelectedText() == null)
/*       */       {
/*  4607 */         copy = j.getText();
/*       */       }
/*       */       else {
/*  4610 */         copy = j.getSelectedText();
/*       */       }
/*       */     }
/*  4613 */     if ((c instanceof JTextField)) {
/*  4614 */       JTextField j = (JTextField)c;
/*  4615 */       if (j.getSelectedText() == null)
/*       */       {
/*  4617 */         copy = j.getText();
/*       */       }
/*       */       else {
/*  4620 */         copy = j.getSelectedText();
/*       */       }
/*       */     }
/*  4623 */     java.awt.datatransfer.StringSelection export = new java.awt.datatransfer.StringSelection(copy);
/*  4624 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/*  4625 */     clipboard.setContents(export, this);
/*       */   }
/*       */   
/*       */   private void FluffPaste(java.awt.Component c)
/*       */   {
/*  4630 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/*  4631 */     String txtimport = null;
/*       */     try {
/*  4633 */       txtimport = (String)clipboard.getData(java.awt.datatransfer.DataFlavor.stringFlavor);
/*       */     } catch (Exception e) {
/*  4635 */       System.err.println(e.getMessage());
/*  4636 */       e.printStackTrace();
/*  4637 */       return;
/*       */     }
/*  4639 */     if (txtimport == null) return;
/*  4640 */     if ((c instanceof JEditorPane)) {
/*  4641 */       JEditorPane j = (JEditorPane)c;
/*  4642 */       int insert = j.getCaretPosition();
/*  4643 */       String paste = j.getText().substring(0, insert) + txtimport + j.getText().substring(insert);
/*  4644 */       j.setText(paste);
/*       */     }
/*  4646 */     if ((c instanceof JTextField)) {
/*  4647 */       JTextField j = (JTextField)c;
/*  4648 */       int insert = j.getCaretPosition();
/*  4649 */       String paste = j.getText().substring(0, insert) + txtimport + j.getText().substring(insert);
/*  4650 */       j.setText(paste);
/*       */     }
/*       */   }
/*       */   
/*       */   private boolean AddECM()
/*       */   {
/*  4656 */     if (this.Prefs.getBoolean("AutoAddECM", true)) {
/*  4657 */       if (!this.CurMech.ValidateECM()) {
/*  4658 */         abPlaceable a = this.data.GetEquipment().GetEquipmentByName("Guardian ECM Suite", this.CurMech);
/*  4659 */         if (a == null) {
/*  4660 */           a = this.data.GetEquipment().GetEquipmentByName("Angel ECM", this.CurMech);
/*  4661 */           if (a == null) {
/*  4662 */             a = this.data.GetEquipment().GetEquipmentByName("ECM Suite", this.CurMech);
/*  4663 */             if (a == null) {
/*  4664 */               a = this.data.GetEquipment().GetEquipmentByName("Watchdog CEWS", this.CurMech);
/*  4665 */               if (a == null) {
/*  4666 */                 return false;
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*  4671 */         this.CurMech.GetLoadout().AddToQueue(a);
/*       */       }
/*  4673 */       return true;
/*       */     }
/*  4675 */     Media.Messager(this, "Please add an appropriate ECM Suite to complement this\n system.  The 'Mech is not valid without an ECM Suite.");
/*  4676 */     return true;
/*       */   }
/*       */   
/*       */   private JCheckBox chkCommandConsole;
/*       */   private JCheckBox chkEjectionSeat;
/*       */   private JCheckBox chkEnviroSealing;
/*       */   private JCheckBox chkFCSAIV;
/*       */   private JCheckBox chkFCSAV;
/*       */   private JCheckBox chkFCSApollo;
/*       */   private JCheckBox chkFHES;
/*       */   private JCheckBox chkFractional;
/*       */   
/*       */   private void initComponents()
/*       */   {
/*  4690 */     this.tlbIconBar = new JToolBar();
/*  4691 */     this.btnNewIcon = new JButton();
/*  4692 */     this.btnOpen = new JButton();
/*  4693 */     this.btnSaveIcon = new JButton();
/*  4694 */     this.btnPrintPreview = new JButton();
/*  4695 */     this.jSeparator24 = new javax.swing.JToolBar.Separator();
/*  4696 */     this.btnPrintIcon = new JButton();
/*  4697 */     this.jSeparator22 = new javax.swing.JToolBar.Separator();
/*  4698 */     this.btnExportClipboardIcon = new JButton();
/*  4699 */     this.btnExportHTMLIcon = new JButton();
/*  4700 */     this.btnExportTextIcon = new JButton();
/*  4701 */     this.btnExportMTFIcon = new JButton();
/*  4702 */     this.btnChatInfo = new JButton();
/*  4703 */     this.jSeparator23 = new javax.swing.JToolBar.Separator();
/*  4704 */     this.btnPostToS7 = new JButton();
/*  4705 */     this.jSeparator25 = new javax.swing.JToolBar.Separator();
/*  4706 */     this.btnAddToForceList = new JButton();
/*  4707 */     this.btnForceList = new JButton();
/*  4708 */     this.jSeparator26 = new javax.swing.JToolBar.Separator();
/*  4709 */     this.btnOptionsIcon = new JButton();
/*  4710 */     this.jSeparator21 = new javax.swing.JToolBar.Separator();
/*  4711 */     this.lblSelectVariant = new JLabel();
/*  4712 */     this.cmbOmniVariant = new JComboBox();
/*  4713 */     this.tbpMainTabPane = new JTabbedPane();
/*  4714 */     this.pnlBasicSetup = new JPanel();
/*  4715 */     this.pnlBasicInformation = new JPanel();
/*  4716 */     this.lblMechName = new JLabel();
/*  4717 */     this.txtMechName = new JTextField();
/*  4718 */     this.lblModel = new JLabel();
/*  4719 */     this.txtMechModel = new JTextField();
/*  4720 */     this.lblMechEra = new JLabel();
/*  4721 */     this.cmbMechEra = new JComboBox();
/*  4722 */     this.lblEraYears = new JLabel();
/*  4723 */     this.lblProdYear = new JLabel();
/*  4724 */     this.txtProdYear = new JTextField();
/*  4725 */     this.chkYearRestrict = new JCheckBox();
/*  4726 */     this.lblTechBase = new JLabel();
/*  4727 */     this.cmbTechBase = new JComboBox();
/*  4728 */     this.cmbRulesLevel = new JComboBox();
/*  4729 */     this.lblRulesLevel = new JLabel();
/*  4730 */     this.jLabel65 = new JLabel();
/*  4731 */     this.txtSource = new JTextField();
/*  4732 */     this.jSeparator28 = new JSeparator();
/*  4733 */     this.jSeparator29 = new JSeparator();
/*  4734 */     this.cmbProductionEra = new JComboBox();
/*  4735 */     this.pnlChassis = new JPanel();
/*  4736 */     this.lblTonnage = new JLabel();
/*  4737 */     this.cmbTonnage = new JComboBox();
/*  4738 */     this.lblMechType = new JLabel();
/*  4739 */     this.lblMotiveType = new JLabel();
/*  4740 */     this.cmbMotiveType = new JComboBox();
/*  4741 */     this.lblEngineType = new JLabel();
/*  4742 */     this.cmbEngineType = new JComboBox();
/*  4743 */     this.lblInternalType = new JLabel();
/*  4744 */     this.cmbInternalType = new JComboBox();
/*  4745 */     this.lblGyroType = new JLabel();
/*  4746 */     this.cmbGyroType = new JComboBox();
/*  4747 */     this.lblCockpit = new JLabel();
/*  4748 */     this.cmbCockpitType = new JComboBox();
/*  4749 */     this.lblPhysEnhance = new JLabel();
/*  4750 */     this.cmbPhysEnhance = new JComboBox();
/*  4751 */     this.chkOmnimech = new JCheckBox();
/*  4752 */     this.cmbMechType = new JComboBox();
/*  4753 */     this.lblUnitType = new JLabel();
/*  4754 */     this.chkCommandConsole = new JCheckBox();
/*  4755 */     this.pnlHeatSinks = new JPanel();
/*  4756 */     this.lblHeatSinkType = new JLabel();
/*  4757 */     this.cmbHeatSinkType = new JComboBox();
/*  4758 */     this.lblHSNumber = new JLabel();
/*  4759 */     this.spnNumberOfHS = new JSpinner();
/*  4760 */     this.pnlMovement = new JPanel();
/*  4761 */     this.lblWalkMP = new JLabel();
/*  4762 */     this.spnWalkMP = new JSpinner();
/*  4763 */     this.lblRunMPLabel = new JLabel();
/*  4764 */     this.lblRunMP = new JLabel();
/*  4765 */     this.lblJumpMP = new JLabel();
/*  4766 */     this.spnJumpMP = new JSpinner();
/*  4767 */     this.cmbJumpJetType = new JComboBox();
/*  4768 */     this.jLabel36 = new JLabel();
/*  4769 */     this.jLabel53 = new JLabel();
/*  4770 */     this.spnBoosterMP = new JSpinner();
/*  4771 */     this.chkBoosters = new JCheckBox();
/*  4772 */     this.lblMoveSummary = new JLabel();
/*  4773 */     this.jLabel1 = new JLabel();
/*  4774 */     this.txtEngineRating = new JTextField();
/*  4775 */     this.pnlOmniInfo = new JPanel();
/*  4776 */     this.btnLockChassis = new JButton();
/*  4777 */     this.btnAddVariant = new JButton();
/*  4778 */     this.btnDeleteVariant = new JButton();
/*  4779 */     this.btnRenameVariant = new JButton();
/*  4780 */     this.pnlBasicSummary = new JPanel();
/*  4781 */     this.lblSumStructure = new JLabel();
/*  4782 */     this.txtSumIntTon = new JTextField();
/*  4783 */     this.lblSumEngine = new JLabel();
/*  4784 */     this.txtSumEngTon = new JTextField();
/*  4785 */     this.lblSumGyro = new JLabel();
/*  4786 */     this.txtSumGyrTon = new JTextField();
/*  4787 */     this.lblSumHeadItem = new JLabel();
/*  4788 */     this.lblSumHeadTons = new JLabel();
/*  4789 */     this.lblSumHeadCrits = new JLabel();
/*  4790 */     this.txtSumIntCrt = new JTextField();
/*  4791 */     this.txtSumEngCrt = new JTextField();
/*  4792 */     this.txtSumGyrCrt = new JTextField();
/*  4793 */     this.lblSumCockpit = new JLabel();
/*  4794 */     this.txtSumCocTon = new JTextField();
/*  4795 */     this.txtSumCocCrt = new JTextField();
/*  4796 */     this.lblSumEnhance = new JLabel();
/*  4797 */     this.txtSumEnhTon = new JTextField();
/*  4798 */     this.txtSumEnhCrt = new JTextField();
/*  4799 */     this.lblSumHeatSinks = new JLabel();
/*  4800 */     this.txtSumHSTon = new JTextField();
/*  4801 */     this.txtSumHSCrt = new JTextField();
/*  4802 */     this.lblSumJJ = new JLabel();
/*  4803 */     this.txtSumJJTon = new JTextField();
/*  4804 */     this.txtSumJJCrt = new JTextField();
/*  4805 */     this.txtSumIntACode = new JTextField();
/*  4806 */     this.txtSumEngACode = new JTextField();
/*  4807 */     this.txtSumGyrACode = new JTextField();
/*  4808 */     this.txtSumCocACode = new JTextField();
/*  4809 */     this.txtSumHSACode = new JTextField();
/*  4810 */     this.txtSumEnhACode = new JTextField();
/*  4811 */     this.txtSumJJACode = new JTextField();
/*  4812 */     this.lblSumHeadAvailable = new JLabel();
/*  4813 */     this.txtSumPAmpsTon = new JTextField();
/*  4814 */     this.txtSumPAmpsACode = new JTextField();
/*  4815 */     this.lblSumPAmps = new JLabel();
/*  4816 */     this.jPanel4 = new JPanel();
/*  4817 */     this.chkCLPS = new JCheckBox();
/*  4818 */     this.chkNullSig = new JCheckBox();
/*  4819 */     this.chkBSPFD = new JCheckBox();
/*  4820 */     this.chkVoidSig = new JCheckBox();
/*  4821 */     this.chkSupercharger = new JCheckBox();
/*  4822 */     this.cmbSCLoc = new JComboBox();
/*  4823 */     this.chkBoobyTrap = new JCheckBox();
/*  4824 */     this.chkPartialWing = new JCheckBox();
/*  4825 */     this.chkFHES = new JCheckBox();
/*  4826 */     this.lblSupercharger = new JLabel();
/*  4827 */     this.jLabel57 = new JLabel();
/*  4828 */     this.jPanel6 = new JPanel();
/*  4829 */     this.chkEjectionSeat = new JCheckBox();
/*  4830 */     this.chkEnviroSealing = new JCheckBox();
/*  4831 */     this.chkTracks = new JCheckBox();
/*  4832 */     this.jPanel8 = new JPanel();
/*  4833 */     this.chkFractional = new JCheckBox();
/*  4834 */     this.pnlArmor = new JPanel();
/*  4835 */     this.pnlFrontArmor = new JPanel();
/*  4836 */     this.pnlCLArmorBox = new JPanel();
/*  4837 */     this.lblRLHeader = new JLabel();
/*  4838 */     this.lblCLIntPts = new JLabel();
/*  4839 */     this.lblRLArmorHeader = new JLabel();
/*  4840 */     this.spnCLArmor = new JSpinner();
/*  4841 */     this.pnlLLArmorBox = new JPanel();
/*  4842 */     this.lblLLHeader = new JLabel();
/*  4843 */     this.lblLLIntPts = new JLabel();
/*  4844 */     this.lblLLArmorHeader = new JLabel();
/*  4845 */     this.spnLLArmor = new JSpinner();
/*  4846 */     this.pnlRAArmorBox = new JPanel();
/*  4847 */     this.lblRAHeader = new JLabel();
/*  4848 */     this.lblRAIntPts = new JLabel();
/*  4849 */     this.lblRAArmorHeader = new JLabel();
/*  4850 */     this.spnRAArmor = new JSpinner();
/*  4851 */     this.pnlHDArmorBox = new JPanel();
/*  4852 */     this.lblHDHeader = new JLabel();
/*  4853 */     this.lblHDIntPts = new JLabel();
/*  4854 */     this.lblHDArmorHeader = new JLabel();
/*  4855 */     this.spnHDArmor = new JSpinner();
/*  4856 */     this.pnlCTArmorBox = new JPanel();
/*  4857 */     this.lblCTHeader = new JLabel();
/*  4858 */     this.lblCTIntPts = new JLabel();
/*  4859 */     this.lblCTArmorHeader = new JLabel();
/*  4860 */     this.spnCTArmor = new JSpinner();
/*  4861 */     this.pnlLTArmorBox = new JPanel();
/*  4862 */     this.lblLTHeader = new JLabel();
/*  4863 */     this.lblLTIntPts = new JLabel();
/*  4864 */     this.lblLTArmorHeader = new JLabel();
/*  4865 */     this.spnLTArmor = new JSpinner();
/*  4866 */     this.pnlRTArmorBox = new JPanel();
/*  4867 */     this.lblRTHeader = new JLabel();
/*  4868 */     this.lblRTIntPts = new JLabel();
/*  4869 */     this.lblRTArmorHeader = new JLabel();
/*  4870 */     this.spnRTArmor = new JSpinner();
/*  4871 */     this.pnlLAArmorBox = new JPanel();
/*  4872 */     this.lblLAHeader = new JLabel();
/*  4873 */     this.lblLAIntPts = new JLabel();
/*  4874 */     this.lblLAArmorHeader = new JLabel();
/*  4875 */     this.spnLAArmor = new JSpinner();
/*  4876 */     this.pnlRLArmorBox = new JPanel();
/*  4877 */     this.lblRLHeader1 = new JLabel();
/*  4878 */     this.lblRLIntPts = new JLabel();
/*  4879 */     this.lblRLArmorHeader1 = new JLabel();
/*  4880 */     this.spnRLArmor = new JSpinner();
/*  4881 */     this.pnlRearArmor = new JPanel();
/*  4882 */     this.pnlRTRArmorBox = new JPanel();
/*  4883 */     this.lblRTRArmorHeader = new JLabel();
/*  4884 */     this.spnRTRArmor = new JSpinner();
/*  4885 */     this.pnlCTRArmorBox = new JPanel();
/*  4886 */     this.lblCTRArmorHeader = new JLabel();
/*  4887 */     this.spnCTRArmor = new JSpinner();
/*  4888 */     this.pnlLTRArmorBox = new JPanel();
/*  4889 */     this.lblLTRArmorHeader = new JLabel();
/*  4890 */     this.spnLTRArmor = new JSpinner();
/*  4891 */     this.pnlArmorInfo = new JPanel();
/*  4892 */     this.lblArmorCoverage = new JLabel();
/*  4893 */     this.lblArmorPoints = new JLabel();
/*  4894 */     this.txtSumArmorTon = new JTextField();
/*  4895 */     this.txtSumArmorCrt = new JTextField();
/*  4896 */     this.lblSumHeadTons1 = new JLabel();
/*  4897 */     this.lblSumHeadCrits1 = new JLabel();
/*  4898 */     this.lblArmorTonsWasted = new JLabel();
/*  4899 */     this.lblAVInLot = new JLabel();
/*  4900 */     this.pnlArmorSetup = new JPanel();
/*  4901 */     this.btnMaxArmor = new JButton();
/*  4902 */     this.btnArmorTons = new JButton();
/*  4903 */     this.cmbArmorType = new JComboBox();
/*  4904 */     this.lblArmorType = new JLabel();
/*  4905 */     this.btnBalanceArmor = new JCheckBox();
/*  4906 */     this.btnEfficientArmor = new JButton();
/*  4907 */     this.btnRemainingArmor = new JButton();
/*  4908 */     this.pnlPatchworkChoosers = new JPanel();
/*  4909 */     this.lblPWHDLoc = new JLabel();
/*  4910 */     this.lblPWCTLoc = new JLabel();
/*  4911 */     this.lblPWLTLoc = new JLabel();
/*  4912 */     this.lblPWRTLoc = new JLabel();
/*  4913 */     this.lblPWLALoc = new JLabel();
/*  4914 */     this.lblPWRALoc = new JLabel();
/*  4915 */     this.lblPWLLLoc = new JLabel();
/*  4916 */     this.cmbPWHDType = new JComboBox();
/*  4917 */     this.cmbPWCTType = new JComboBox();
/*  4918 */     this.cmbPWLTType = new JComboBox();
/*  4919 */     this.cmbPWRTType = new JComboBox();
/*  4920 */     this.cmbPWLAType = new JComboBox();
/*  4921 */     this.cmbPWRAType = new JComboBox();
/*  4922 */     this.cmbPWLLType = new JComboBox();
/*  4923 */     this.cmbPWRLType = new JComboBox();
/*  4924 */     this.lblPWRLLoc = new JLabel();
/*  4925 */     this.lblPWRLLoc1 = new JLabel();
/*  4926 */     this.cmbPWCLType = new JComboBox();
/*  4927 */     this.pnlEquipment = new JPanel();
/*  4928 */     this.tbpWeaponChooser = new JTabbedPane();
/*  4929 */     this.pnlBallistic = new JPanel();
/*  4930 */     this.jSeparator3 = new JSeparator();
/*  4931 */     this.jScrollPane8 = new JScrollPane();
/*  4932 */     this.lstChooseBallistic = new JList();
/*  4933 */     this.jSeparator4 = new JSeparator();
/*  4934 */     this.pnlEnergy = new JPanel();
/*  4935 */     this.jSeparator2 = new JSeparator();
/*  4936 */     this.jScrollPane9 = new JScrollPane();
/*  4937 */     this.lstChooseEnergy = new JList();
/*  4938 */     this.jSeparator1 = new JSeparator();
/*  4939 */     this.pnlMissile = new JPanel();
/*  4940 */     this.jSeparator5 = new JSeparator();
/*  4941 */     this.jScrollPane19 = new JScrollPane();
/*  4942 */     this.lstChooseMissile = new JList();
/*  4943 */     this.jSeparator6 = new JSeparator();
/*  4944 */     this.pnlPhysical = new JPanel();
/*  4945 */     this.jSeparator8 = new JSeparator();
/*  4946 */     this.jScrollPane20 = new JScrollPane();
/*  4947 */     this.lstChoosePhysical = new JList();
/*  4948 */     this.jSeparator7 = new JSeparator();
/*  4949 */     this.pnlEquipmentChooser = new JPanel();
/*  4950 */     this.jSeparator10 = new JSeparator();
/*  4951 */     this.jScrollPane21 = new JScrollPane();
/*  4952 */     this.lstChooseEquipment = new JList();
/*  4953 */     this.jSeparator9 = new JSeparator();
/*  4954 */     this.pnlArtillery = new JPanel();
/*  4955 */     this.jSeparator18 = new JSeparator();
/*  4956 */     this.jScrollPane24 = new JScrollPane();
/*  4957 */     this.lstChooseArtillery = new JList();
/*  4958 */     this.jSeparator19 = new JSeparator();
/*  4959 */     this.pnlAmmunition = new JPanel();
/*  4960 */     this.jSeparator11 = new JSeparator();
/*  4961 */     this.jScrollPane22 = new JScrollPane();
/*  4962 */     this.lstChooseAmmunition = new JList();
/*  4963 */     this.jSeparator12 = new JSeparator();
/*  4964 */     this.pnlSpecials = new JPanel();
/*  4965 */     this.jLabel16 = new JLabel();
/*  4966 */     this.chkUseTC = new JCheckBox();
/*  4967 */     this.chkFCSAIV = new JCheckBox();
/*  4968 */     this.chkFCSAV = new JCheckBox();
/*  4969 */     this.chkFCSApollo = new JCheckBox();
/*  4970 */     this.chkClanCASE = new JCheckBox();
/*  4971 */     this.pnlSelected = new JPanel();
/*  4972 */     this.jScrollPane23 = new JScrollPane();
/*  4973 */     this.lstSelectedEquipment = new JList();
/*  4974 */     this.pnlControls = new JPanel();
/*  4975 */     this.btnRemoveEquip = new JButton();
/*  4976 */     this.btnClearEquip = new JButton();
/*  4977 */     this.btnAddEquip = new JButton();
/*  4978 */     this.cmbNumEquips = new JComboBox();
/*  4979 */     this.pnlEquipInfo = new JPanel();
/*  4980 */     this.jLabel17 = new JLabel();
/*  4981 */     this.jLabel18 = new JLabel();
/*  4982 */     this.jLabel19 = new JLabel();
/*  4983 */     this.lblInfoAVSL = new JLabel();
/*  4984 */     this.lblInfoAVSW = new JLabel();
/*  4985 */     this.lblInfoAVCI = new JLabel();
/*  4986 */     this.jLabel20 = new JLabel();
/*  4987 */     this.jLabel21 = new JLabel();
/*  4988 */     this.jLabel22 = new JLabel();
/*  4989 */     this.lblInfoIntro = new JLabel();
/*  4990 */     this.lblInfoExtinct = new JLabel();
/*  4991 */     this.lblInfoReintro = new JLabel();
/*  4992 */     this.jLabel23 = new JLabel();
/*  4993 */     this.jLabel24 = new JLabel();
/*  4994 */     this.jLabel25 = new JLabel();
/*  4995 */     this.jLabel26 = new JLabel();
/*  4996 */     this.jLabel27 = new JLabel();
/*  4997 */     this.lblInfoName = new JLabel();
/*  4998 */     this.lblInfoType = new JLabel();
/*  4999 */     this.lblInfoHeat = new JLabel();
/*  5000 */     this.lblInfoDamage = new JLabel();
/*  5001 */     this.lblInfoRange = new JLabel();
/*  5002 */     this.jSeparator13 = new JSeparator();
/*  5003 */     this.jLabel28 = new JLabel();
/*  5004 */     this.jLabel29 = new JLabel();
/*  5005 */     this.jLabel30 = new JLabel();
/*  5006 */     this.jLabel31 = new JLabel();
/*  5007 */     this.lblInfoAmmo = new JLabel();
/*  5008 */     this.lblInfoTonnage = new JLabel();
/*  5009 */     this.lblInfoCrits = new JLabel();
/*  5010 */     this.lblInfoSpecials = new JLabel();
/*  5011 */     this.jSeparator14 = new JSeparator();
/*  5012 */     this.jLabel32 = new JLabel();
/*  5013 */     this.lblInfoCost = new JLabel();
/*  5014 */     this.jLabel34 = new JLabel();
/*  5015 */     this.lblInfoBV = new JLabel();
/*  5016 */     this.jLabel33 = new JLabel();
/*  5017 */     this.lblInfoMountRestrict = new JLabel();
/*  5018 */     this.jLabel55 = new JLabel();
/*  5019 */     this.lblInfoRulesLevel = new JLabel();
/*  5020 */     this.pnlCriticals = new JPanel();
/*  5021 */     this.pnlHDCrits = new JPanel();
/*  5022 */     this.chkHDTurret = new JCheckBox();
/*  5023 */     this.chkHDCASE2 = new JCheckBox();
/*  5024 */     this.jScrollPane10 = new JScrollPane();
/*  5025 */     this.lstHDCrits = new JList();
/*  5026 */     this.pnlCTCrits = new JPanel();
/*  5027 */     this.jScrollPane11 = new JScrollPane();
/*  5028 */     this.lstCTCrits = new JList();
/*  5029 */     this.chkCTCASE = new JCheckBox();
/*  5030 */     this.chkCTCASE2 = new JCheckBox();
/*  5031 */     this.pnlLTCrits = new JPanel();
/*  5032 */     this.chkLTCASE = new JCheckBox();
/*  5033 */     this.jScrollPane12 = new JScrollPane();
/*  5034 */     this.lstLTCrits = new JList();
/*  5035 */     this.chkLTCASE2 = new JCheckBox();
/*  5036 */     this.chkLTTurret = new JCheckBox();
/*  5037 */     this.pnlRTCrits = new JPanel();
/*  5038 */     this.jScrollPane13 = new JScrollPane();
/*  5039 */     this.lstRTCrits = new JList();
/*  5040 */     this.chkRTCASE = new JCheckBox();
/*  5041 */     this.chkRTCASE2 = new JCheckBox();
/*  5042 */     this.chkRTTurret = new JCheckBox();
/*  5043 */     this.pnlLACrits = new JPanel();
/*  5044 */     this.scrLACrits = new JScrollPane();
/*  5045 */     this.lstLACrits = new JList();
/*  5046 */     this.chkLALowerArm = new JCheckBox();
/*  5047 */     this.chkLAHand = new JCheckBox();
/*  5048 */     this.chkLACASE2 = new JCheckBox();
/*  5049 */     this.chkLAAES = new JCheckBox();
/*  5050 */     this.pnlRACrits = new JPanel();
/*  5051 */     this.scrRACrits = new JScrollPane();
/*  5052 */     this.lstRACrits = new JList();
/*  5053 */     this.chkRALowerArm = new JCheckBox();
/*  5054 */     this.chkRAHand = new JCheckBox();
/*  5055 */     this.chkRACASE2 = new JCheckBox();
/*  5056 */     this.chkRAAES = new JCheckBox();
/*  5057 */     this.pnlLLCrits = new JPanel();
/*  5058 */     this.jScrollPane16 = new JScrollPane();
/*  5059 */     this.lstLLCrits = new JList();
/*  5060 */     this.chkLLCASE2 = new JCheckBox();
/*  5061 */     this.pnlRLCrits = new JPanel();
/*  5062 */     this.jScrollPane17 = new JScrollPane();
/*  5063 */     this.lstRLCrits = new JList();
/*  5064 */     this.chkRLCASE2 = new JCheckBox();
/*  5065 */     this.pnlEquipmentToPlace = new JPanel();
/*  5066 */     this.jScrollPane18 = new JScrollPane();
/*  5067 */     this.lstCritsToPlace = new JList();
/*  5068 */     this.btnRemoveItemCrits = new JButton();
/*  5069 */     this.onlLoadoutControls = new JPanel();
/*  5070 */     this.btnCompactCrits = new JButton();
/*  5071 */     this.btnClearLoadout = new JButton();
/*  5072 */     this.btnAutoAllocate = new JButton();
/*  5073 */     this.btnSelectiveAllocate = new JButton();
/*  5074 */     this.jPanel5 = new JPanel();
/*  5075 */     this.jLabel59 = new JLabel();
/*  5076 */     this.chkLegAES = new JCheckBox();
/*  5077 */     this.jLabel61 = new JLabel();
/*  5078 */     this.pnlCLCrits = new JPanel();
/*  5079 */     this.jScrollPane25 = new JScrollPane();
/*  5080 */     this.lstCLCrits = new JList();
/*  5081 */     this.chkCLCASE2 = new JCheckBox();
/*  5082 */     this.pnlFluff = new JPanel();
/*  5083 */     this.pnlImage = new JPanel();
/*  5084 */     this.lblFluffImage = new JLabel();
/*  5085 */     this.jPanel1 = new JPanel();
/*  5086 */     this.btnLoadImage = new JButton();
/*  5087 */     this.btnClearImage = new JButton();
/*  5088 */     this.pnlExport = new JPanel();
/*  5089 */     this.btnExportTXT = new JButton();
/*  5090 */     this.btnExportHTML = new JButton();
/*  5091 */     this.btnExportMTF = new JButton();
/*  5092 */     this.tbpFluffEditors = new JTabbedPane();
/*  5093 */     this.pnlOverview = new JPanel();
/*  5094 */     this.pnlCapabilities = new JPanel();
/*  5095 */     this.pnlHistory = new JPanel();
/*  5096 */     this.pnlDeployment = new JPanel();
/*  5097 */     this.pnlVariants = new JPanel();
/*  5098 */     this.pnlNotables = new JPanel();
/*  5099 */     this.pnlAdditionalFluff = new JPanel();
/*  5100 */     this.pnlManufacturers = new JPanel();
/*  5101 */     this.jLabel8 = new JLabel();
/*  5102 */     this.jLabel9 = new JLabel();
/*  5103 */     this.jLabel10 = new JLabel();
/*  5104 */     this.jLabel12 = new JLabel();
/*  5105 */     this.jLabel11 = new JLabel();
/*  5106 */     this.jLabel13 = new JLabel();
/*  5107 */     this.jLabel14 = new JLabel();
/*  5108 */     this.jLabel15 = new JLabel();
/*  5109 */     this.txtManufacturer = new JTextField();
/*  5110 */     this.txtEngineManufacturer = new JTextField();
/*  5111 */     this.txtArmorModel = new JTextField();
/*  5112 */     this.txtChassisModel = new JTextField();
/*  5113 */     this.txtCommSystem = new JTextField();
/*  5114 */     this.txtTNTSystem = new JTextField();
/*  5115 */     this.pnlWeaponsManufacturers = new JPanel();
/*  5116 */     this.chkIndividualWeapons = new JCheckBox();
/*  5117 */     this.scpWeaponManufacturers = new JScrollPane();
/*  5118 */     this.tblWeaponManufacturers = new JTable();
/*  5119 */     this.txtManufacturerLocation = new JTextField();
/*  5120 */     this.jLabel35 = new JLabel();
/*  5121 */     this.txtJJModel = new JTextField();
/*  5122 */     this.pnlQuirks = new JPanel();
/*  5123 */     this.lblBattleMechQuirks = new JLabel();
/*  5124 */     this.scpQuirkTable = new JScrollPane();
/*  5125 */     this.tblQuirks = new JTable();
/*  5126 */     this.btnAddQuirk = new JButton();
/*  5127 */     this.pnlCharts = new JPanel();
/*  5128 */     this.jPanel2 = new JPanel();
/*  5129 */     this.jLabel39 = new JLabel();
/*  5130 */     this.lblTonPercStructure = new JLabel();
/*  5131 */     this.jLabel43 = new JLabel();
/*  5132 */     this.lblTonPercEngine = new JLabel();
/*  5133 */     this.jLabel54 = new JLabel();
/*  5134 */     this.lblTonPercHeatSinks = new JLabel();
/*  5135 */     this.jLabel56 = new JLabel();
/*  5136 */     this.lblTonPercEnhance = new JLabel();
/*  5137 */     this.jLabel58 = new JLabel();
/*  5138 */     this.lblTonPercArmor = new JLabel();
/*  5139 */     this.jLabel60 = new JLabel();
/*  5140 */     this.lblTonPercJumpJets = new JLabel();
/*  5141 */     this.jLabel62 = new JLabel();
/*  5142 */     this.lblTonPercWeapons = new JLabel();
/*  5143 */     this.jLabel64 = new JLabel();
/*  5144 */     this.lblTonPercEquips = new JLabel();
/*  5145 */     this.jPanel3 = new JPanel();
/*  5146 */     this.jLabel41 = new JLabel();
/*  5147 */     this.lblDamagePerTon = new JLabel();
/*  5148 */     this.pnlDamageChart = new DamageChart();
/*  5149 */     this.lblLegendTitle = new JLabel();
/*  5150 */     this.chkChartFront = new JCheckBox();
/*  5151 */     this.chkChartRear = new JCheckBox();
/*  5152 */     this.chkChartRight = new JCheckBox();
/*  5153 */     this.chkChartLeft = new JCheckBox();
/*  5154 */     this.btnBracketChart = new JButton();
/*  5155 */     this.chkAverageDamage = new JCheckBox();
/*  5156 */     this.chkShowTextNotGraph = new JCheckBox();
/*  5157 */     this.pnlBattleforce = new JPanel();
/*  5158 */     this.pnlBFStats = new JPanel();
/*  5159 */     this.jLabel66 = new JLabel();
/*  5160 */     this.jLabel67 = new JLabel();
/*  5161 */     this.jLabel68 = new JLabel();
/*  5162 */     this.jLabel69 = new JLabel();
/*  5163 */     this.jLabel70 = new JLabel();
/*  5164 */     this.jLabel71 = new JLabel();
/*  5165 */     this.jLabel72 = new JLabel();
/*  5166 */     this.jLabel73 = new JLabel();
/*  5167 */     this.jLabel74 = new JLabel();
/*  5168 */     this.jLabel75 = new JLabel();
/*  5169 */     this.lblBFMV = new JLabel();
/*  5170 */     this.lblBFWt = new JLabel();
/*  5171 */     this.lblBFOV = new JLabel();
/*  5172 */     this.lblBFExtreme = new JLabel();
/*  5173 */     this.lblBFShort = new JLabel();
/*  5174 */     this.lblBFMedium = new JLabel();
/*  5175 */     this.lblBFLong = new JLabel();
/*  5176 */     this.lblBFArmor = new JLabel();
/*  5177 */     this.lblBFStructure = new JLabel();
/*  5178 */     this.lblBFSA = new JLabel();
/*  5179 */     this.jLabel37 = new JLabel();
/*  5180 */     this.lblBFPoints = new JLabel();
/*  5181 */     this.jPanel7 = new JPanel();
/*  5182 */     this.jScrollPane14 = new JScrollPane();
/*  5183 */     this.jTextAreaBFConversion = new javax.swing.JTextArea();
/*  5184 */     this.pnlInfoPanel = new JPanel();
/*  5185 */     this.txtInfoTonnage = new JTextField();
/*  5186 */     this.txtInfoFreeTons = new JTextField();
/*  5187 */     this.txtInfoMaxHeat = new JTextField();
/*  5188 */     this.txtInfoHeatDiss = new JTextField();
/*  5189 */     this.txtInfoFreeCrits = new JTextField();
/*  5190 */     this.txtInfoUnplaced = new JTextField();
/*  5191 */     this.txtInfoBattleValue = new JTextField();
/*  5192 */     this.txtInfoCost = new JTextField();
/*  5193 */     this.mnuMainMenu = new javax.swing.JMenuBar();
/*  5194 */     this.mnuFile = new JMenu();
/*  5195 */     this.mnuNewMech = new JMenuItem();
/*  5196 */     this.mnuLoad = new JMenuItem();
/*  5197 */     this.mnuOpen = new JMenuItem();
/*  5198 */     this.mnuImport = new JMenu();
/*  5199 */     this.mnuImportHMP = new JMenuItem();
/*  5200 */     this.mnuBatchHMP = new JMenuItem();
/*  5201 */     this.jSeparator16 = new JSeparator();
/*  5202 */     this.mnuSave = new JMenuItem();
/*  5203 */     this.mnuSaveAs = new JMenuItem();
/*  5204 */     this.mnuExport = new JMenu();
/*  5205 */     this.mnuExportHTML = new JMenuItem();
/*  5206 */     this.mnuExportMTF = new JMenuItem();
/*  5207 */     this.mnuExportTXT = new JMenuItem();
/*  5208 */     this.mnuExportClipboard = new JMenuItem();
/*  5209 */     this.mnuCreateTCGMech = new JMenuItem();
/*  5210 */     this.jSeparator20 = new JSeparator();
/*  5211 */     this.mnuPrint = new JMenu();
/*  5212 */     this.mnuPrintCurrentMech = new JMenuItem();
/*  5213 */     this.mnuPrintSavedMech = new JMenuItem();
/*  5214 */     this.mnuPrintBatch = new JMenuItem();
/*  5215 */     this.mnuPrintPreview = new JMenuItem();
/*  5216 */     this.mnuPostS7 = new JMenuItem();
/*  5217 */     this.jSeparator17 = new JSeparator();
/*  5218 */     this.mnuExit = new JMenuItem();
/*  5219 */     this.mnuClearFluff = new JMenu();
/*  5220 */     this.mnuSummary = new JMenuItem();
/*  5221 */     this.mnuCostBVBreakdown = new JMenuItem();
/*  5222 */     this.mnuTextTRO = new JMenuItem();
/*  5223 */     this.jSeparator15 = new JSeparator();
/*  5224 */     this.mnuBFB = new JMenuItem();
/*  5225 */     this.jSeparator27 = new JSeparator();
/*  5226 */     this.mnuOptions = new JMenuItem();
/*  5227 */     this.mnuViewToolbar = new javax.swing.JCheckBoxMenuItem();
/*  5228 */     this.mnuClearUserData = new JMenuItem();
/*  5229 */     this.jSeparator30 = new JSeparator();
/*  5230 */     this.mnuUnlock = new JMenuItem();
/*  5231 */     this.jMenuItem1 = new JMenuItem();
/*  5232 */     this.mnuHelp = new JMenu();
/*  5233 */     this.mnuCredits = new JMenuItem();
/*  5234 */     this.mnuAboutSSW = new JMenuItem();
/*       */     
/*  5236 */     setDefaultCloseOperation(3);
/*  5237 */     setMinimumSize(new Dimension(750, 515));
/*  5238 */     addWindowListener(new java.awt.event.WindowAdapter() {
/*       */       public void windowClosing(java.awt.event.WindowEvent evt) {
/*  5240 */         frmMain.this.formWindowClosing(evt);
/*       */       }
/*  5242 */     });
/*  5243 */     getContentPane().setLayout(new GridBagLayout());
/*       */     
/*  5245 */     this.tlbIconBar.setFloatable(false);
/*  5246 */     this.tlbIconBar.setFocusable(false);
/*  5247 */     this.tlbIconBar.setMaximumSize(new Dimension(30, 30));
/*  5248 */     this.tlbIconBar.setMinimumSize(new Dimension(30, 30));
/*  5249 */     this.tlbIconBar.setPreferredSize(new Dimension(30, 30));
/*  5250 */     this.tlbIconBar.setRequestFocusEnabled(false);
/*  5251 */     this.tlbIconBar.setVerifyInputWhenFocusTarget(false);
/*       */     
/*  5253 */     this.btnNewIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document--plus.png")));
/*  5254 */     this.btnNewIcon.setToolTipText("New Mech");
/*  5255 */     this.btnNewIcon.setFocusable(false);
/*  5256 */     this.btnNewIcon.setHorizontalTextPosition(0);
/*  5257 */     this.btnNewIcon.setVerticalTextPosition(3);
/*  5258 */     this.btnNewIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5260 */         frmMain.this.btnNewIconActionPerformed(evt);
/*       */       }
/*  5262 */     });
/*  5263 */     this.tlbIconBar.add(this.btnNewIcon);
/*       */     
/*  5265 */     this.btnOpen.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/folder-open-document.png")));
/*  5266 */     this.btnOpen.setToolTipText("Open Mech");
/*  5267 */     this.btnOpen.setFocusable(false);
/*  5268 */     this.btnOpen.setHorizontalTextPosition(0);
/*  5269 */     this.btnOpen.setVerticalTextPosition(3);
/*  5270 */     this.btnOpen.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5272 */         frmMain.this.btnOpenActionPerformed(evt);
/*       */       }
/*  5274 */     });
/*  5275 */     this.tlbIconBar.add(this.btnOpen);
/*       */     
/*  5277 */     this.btnSaveIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/disk-black.png")));
/*  5278 */     this.btnSaveIcon.setToolTipText("Save Mech");
/*  5279 */     this.btnSaveIcon.setFocusable(false);
/*  5280 */     this.btnSaveIcon.setHorizontalTextPosition(0);
/*  5281 */     this.btnSaveIcon.setVerticalTextPosition(3);
/*  5282 */     this.btnSaveIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5284 */         frmMain.this.btnSaveIconActionPerformed(evt);
/*       */       }
/*  5286 */     });
/*  5287 */     this.tlbIconBar.add(this.btnSaveIcon);
/*       */     
/*  5289 */     this.btnPrintPreview.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/projection-screen.png")));
/*  5290 */     this.btnPrintPreview.setToolTipText("Print Preview");
/*  5291 */     this.btnPrintPreview.setFocusable(false);
/*  5292 */     this.btnPrintPreview.setHorizontalTextPosition(0);
/*  5293 */     this.btnPrintPreview.setVerticalTextPosition(3);
/*  5294 */     this.btnPrintPreview.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5296 */         frmMain.this.btnPrintPreviewActionPerformed(evt);
/*       */       }
/*  5298 */     });
/*  5299 */     this.tlbIconBar.add(this.btnPrintPreview);
/*  5300 */     this.tlbIconBar.add(this.jSeparator24);
/*       */     
/*  5302 */     this.btnPrintIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/printer.png")));
/*  5303 */     this.btnPrintIcon.setToolTipText("Print Current Mech");
/*  5304 */     this.btnPrintIcon.setFocusable(false);
/*  5305 */     this.btnPrintIcon.setHorizontalTextPosition(0);
/*  5306 */     this.btnPrintIcon.setVerticalTextPosition(3);
/*  5307 */     this.btnPrintIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5309 */         frmMain.this.btnPrintIconActionPerformed(evt);
/*       */       }
/*  5311 */     });
/*  5312 */     this.tlbIconBar.add(this.btnPrintIcon);
/*  5313 */     this.tlbIconBar.add(this.jSeparator22);
/*       */     
/*  5315 */     this.btnExportClipboardIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document-clipboard.png")));
/*  5316 */     this.btnExportClipboardIcon.setToolTipText("Export Text to Clipboard");
/*  5317 */     this.btnExportClipboardIcon.setFocusable(false);
/*  5318 */     this.btnExportClipboardIcon.setHorizontalTextPosition(0);
/*  5319 */     this.btnExportClipboardIcon.setVerticalTextPosition(3);
/*  5320 */     this.btnExportClipboardIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5322 */         frmMain.this.btnExportClipboardIconActionPerformed(evt);
/*       */       }
/*  5324 */     });
/*  5325 */     this.tlbIconBar.add(this.btnExportClipboardIcon);
/*       */     
/*  5327 */     this.btnExportHTMLIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document-image.png")));
/*  5328 */     this.btnExportHTMLIcon.setToolTipText("Export HTML");
/*  5329 */     this.btnExportHTMLIcon.setFocusable(false);
/*  5330 */     this.btnExportHTMLIcon.setHorizontalTextPosition(0);
/*  5331 */     this.btnExportHTMLIcon.setVerticalTextPosition(3);
/*  5332 */     this.btnExportHTMLIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5334 */         frmMain.this.btnExportHTMLIconActionPerformed(evt);
/*       */       }
/*  5336 */     });
/*  5337 */     this.tlbIconBar.add(this.btnExportHTMLIcon);
/*       */     
/*  5339 */     this.btnExportTextIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document-text.png")));
/*  5340 */     this.btnExportTextIcon.setToolTipText("Export Text");
/*  5341 */     this.btnExportTextIcon.setFocusable(false);
/*  5342 */     this.btnExportTextIcon.setHorizontalTextPosition(0);
/*  5343 */     this.btnExportTextIcon.setVerticalTextPosition(3);
/*  5344 */     this.btnExportTextIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5346 */         frmMain.this.btnExportTextIconActionPerformed(evt);
/*       */       }
/*  5348 */     });
/*  5349 */     this.tlbIconBar.add(this.btnExportTextIcon);
/*       */     
/*  5351 */     this.btnExportMTFIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document--arrow.png")));
/*  5352 */     this.btnExportMTFIcon.setToolTipText("Export MTF");
/*  5353 */     this.btnExportMTFIcon.setFocusable(false);
/*  5354 */     this.btnExportMTFIcon.setHorizontalTextPosition(0);
/*  5355 */     this.btnExportMTFIcon.setVerticalTextPosition(3);
/*  5356 */     this.btnExportMTFIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5358 */         frmMain.this.btnExportMTFIconActionPerformed(evt);
/*       */       }
/*  5360 */     });
/*  5361 */     this.tlbIconBar.add(this.btnExportMTFIcon);
/*       */     
/*  5363 */     this.btnChatInfo.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/balloon.png")));
/*  5364 */     this.btnChatInfo.setToolTipText("Copy Chat Line");
/*  5365 */     this.btnChatInfo.setFocusable(false);
/*  5366 */     this.btnChatInfo.setHorizontalTextPosition(0);
/*  5367 */     this.btnChatInfo.setVerticalTextPosition(3);
/*  5368 */     this.btnChatInfo.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5370 */         frmMain.this.btnChatInfoActionPerformed(evt);
/*       */       }
/*  5372 */     });
/*  5373 */     this.tlbIconBar.add(this.btnChatInfo);
/*  5374 */     this.tlbIconBar.add(this.jSeparator23);
/*       */     
/*  5376 */     this.btnPostToS7.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/server.png")));
/*  5377 */     this.btnPostToS7.setToolTipText("Upload to Solaris7.com");
/*  5378 */     this.btnPostToS7.setFocusable(false);
/*  5379 */     this.btnPostToS7.setHorizontalTextPosition(0);
/*  5380 */     this.btnPostToS7.setVerticalTextPosition(3);
/*  5381 */     this.btnPostToS7.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5383 */         frmMain.this.btnPostToS7ActionPerformed(evt);
/*       */       }
/*  5385 */     });
/*  5386 */     this.tlbIconBar.add(this.btnPostToS7);
/*  5387 */     this.tlbIconBar.add(this.jSeparator25);
/*       */     
/*  5389 */     this.btnAddToForceList.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/clipboard--plus.png")));
/*  5390 */     this.btnAddToForceList.setToolTipText("Add  to Force List");
/*  5391 */     this.btnAddToForceList.setFocusable(false);
/*  5392 */     this.btnAddToForceList.setHorizontalTextPosition(0);
/*  5393 */     this.btnAddToForceList.setVerticalTextPosition(3);
/*  5394 */     this.btnAddToForceList.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5396 */         frmMain.this.btnAddToForceListActionPerformed(evt);
/*       */       }
/*  5398 */     });
/*  5399 */     this.tlbIconBar.add(this.btnAddToForceList);
/*       */     
/*  5401 */     this.btnForceList.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/clipboard.png")));
/*  5402 */     this.btnForceList.setToolTipText("Force List");
/*  5403 */     this.btnForceList.setFocusable(false);
/*  5404 */     this.btnForceList.setHorizontalTextPosition(0);
/*  5405 */     this.btnForceList.setVerticalTextPosition(3);
/*  5406 */     this.btnForceList.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5408 */         frmMain.this.btnForceListActionPerformed(evt);
/*       */       }
/*  5410 */     });
/*  5411 */     this.tlbIconBar.add(this.btnForceList);
/*  5412 */     this.tlbIconBar.add(this.jSeparator26);
/*       */     
/*  5414 */     this.btnOptionsIcon.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/gear.png")));
/*  5415 */     this.btnOptionsIcon.setToolTipText("View Options");
/*  5416 */     this.btnOptionsIcon.setFocusable(false);
/*  5417 */     this.btnOptionsIcon.setHorizontalTextPosition(0);
/*  5418 */     this.btnOptionsIcon.setVerticalTextPosition(3);
/*  5419 */     this.btnOptionsIcon.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5421 */         frmMain.this.btnOptionsIconActionPerformed(evt);
/*       */       }
/*  5423 */     });
/*  5424 */     this.tlbIconBar.add(this.btnOptionsIcon);
/*  5425 */     this.tlbIconBar.add(this.jSeparator21);
/*       */     
/*  5427 */     this.lblSelectVariant.setText("Selected Variant: ");
/*  5428 */     this.lblSelectVariant.setEnabled(false);
/*  5429 */     this.tlbIconBar.add(this.lblSelectVariant);
/*       */     
/*  5431 */     this.cmbOmniVariant.setEnabled(false);
/*  5432 */     this.cmbOmniVariant.setMaximumSize(new Dimension(150, 20));
/*  5433 */     this.cmbOmniVariant.setMinimumSize(new Dimension(150, 20));
/*  5434 */     this.cmbOmniVariant.setPreferredSize(new Dimension(150, 20));
/*  5435 */     this.cmbOmniVariant.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5437 */         frmMain.this.cmbOmniVariantActionPerformed(evt);
/*       */       }
/*  5439 */     });
/*  5440 */     this.tlbIconBar.add(this.cmbOmniVariant);
/*       */     
/*  5442 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  5443 */     gridBagConstraints.fill = 1;
/*  5444 */     gridBagConstraints.anchor = 18;
/*  5445 */     getContentPane().add(this.tlbIconBar, gridBagConstraints);
/*       */     
/*  5447 */     this.pnlBasicSetup.setLayout(new GridBagLayout());
/*       */     
/*  5449 */     this.pnlBasicInformation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Basic Information"));
/*  5450 */     this.pnlBasicInformation.setLayout(new GridBagLayout());
/*       */     
/*  5452 */     this.lblMechName.setText("Mech Name:");
/*  5453 */     gridBagConstraints = new GridBagConstraints();
/*  5454 */     gridBagConstraints.anchor = 13;
/*  5455 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5456 */     this.pnlBasicInformation.add(this.lblMechName, gridBagConstraints);
/*       */     
/*  5458 */     this.txtMechName.setMaximumSize(new Dimension(150, 20));
/*  5459 */     this.txtMechName.setMinimumSize(new Dimension(150, 20));
/*  5460 */     this.txtMechName.setPreferredSize(new Dimension(150, 20));
/*  5461 */     gridBagConstraints = new GridBagConstraints();
/*  5462 */     gridBagConstraints.gridwidth = 2;
/*  5463 */     this.pnlBasicInformation.add(this.txtMechName, gridBagConstraints);
/*  5464 */     MouseListener mlMechName = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  5466 */         if (e.isPopupTrigger())
/*  5467 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  5471 */         if (e.isPopupTrigger()) {
/*  5472 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  5475 */     };
/*  5476 */     this.txtMechName.addMouseListener(mlMechName);
/*       */     
/*  5478 */     this.lblModel.setText("Model:");
/*  5479 */     gridBagConstraints = new GridBagConstraints();
/*  5480 */     gridBagConstraints.gridx = 0;
/*  5481 */     gridBagConstraints.gridy = 1;
/*  5482 */     gridBagConstraints.anchor = 13;
/*  5483 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5484 */     this.pnlBasicInformation.add(this.lblModel, gridBagConstraints);
/*       */     
/*  5486 */     this.txtMechModel.setMaximumSize(new Dimension(150, 20));
/*  5487 */     this.txtMechModel.setMinimumSize(new Dimension(150, 20));
/*  5488 */     this.txtMechModel.setPreferredSize(new Dimension(150, 20));
/*  5489 */     gridBagConstraints = new GridBagConstraints();
/*  5490 */     gridBagConstraints.gridx = 1;
/*  5491 */     gridBagConstraints.gridy = 1;
/*  5492 */     gridBagConstraints.gridwidth = 2;
/*  5493 */     this.pnlBasicInformation.add(this.txtMechModel, gridBagConstraints);
/*  5494 */     MouseListener mlMechModel = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  5496 */         if (e.isPopupTrigger())
/*  5497 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  5501 */         if (e.isPopupTrigger()) {
/*  5502 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  5505 */     };
/*  5506 */     this.txtMechModel.addMouseListener(mlMechModel);
/*       */     
/*  5508 */     this.lblMechEra.setText("Era:");
/*  5509 */     gridBagConstraints = new GridBagConstraints();
/*  5510 */     gridBagConstraints.gridx = 0;
/*  5511 */     gridBagConstraints.gridy = 4;
/*  5512 */     gridBagConstraints.anchor = 13;
/*  5513 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5514 */     this.pnlBasicInformation.add(this.lblMechEra, gridBagConstraints);
/*       */     
/*  5516 */     this.cmbMechEra.setModel(new DefaultComboBoxModel(new String[] { "Age of War/Star League", "Succession Wars", "Clan Invasion", "Dark Ages", "All Eras (non-canon)" }));
/*  5517 */     this.cmbMechEra.setMaximumSize(new Dimension(150, 20));
/*  5518 */     this.cmbMechEra.setMinimumSize(new Dimension(150, 20));
/*  5519 */     this.cmbMechEra.setPreferredSize(new Dimension(150, 20));
/*  5520 */     this.cmbMechEra.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5522 */         frmMain.this.cmbMechEraActionPerformed(evt);
/*       */       }
/*  5524 */     });
/*  5525 */     gridBagConstraints = new GridBagConstraints();
/*  5526 */     gridBagConstraints.gridx = 1;
/*  5527 */     gridBagConstraints.gridy = 4;
/*  5528 */     gridBagConstraints.gridwidth = 2;
/*  5529 */     this.pnlBasicInformation.add(this.cmbMechEra, gridBagConstraints);
/*       */     
/*  5531 */     this.lblEraYears.setText("2443~2800");
/*  5532 */     gridBagConstraints = new GridBagConstraints();
/*  5533 */     gridBagConstraints.gridx = 1;
/*  5534 */     gridBagConstraints.gridy = 6;
/*  5535 */     gridBagConstraints.insets = new Insets(2, 0, 2, 0);
/*  5536 */     this.pnlBasicInformation.add(this.lblEraYears, gridBagConstraints);
/*       */     
/*  5538 */     this.lblProdYear.setText("Prod Year/Era:");
/*  5539 */     gridBagConstraints = new GridBagConstraints();
/*  5540 */     gridBagConstraints.gridx = 0;
/*  5541 */     gridBagConstraints.gridy = 7;
/*  5542 */     gridBagConstraints.anchor = 13;
/*  5543 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5544 */     this.pnlBasicInformation.add(this.lblProdYear, gridBagConstraints);
/*       */     
/*  5546 */     this.txtProdYear.setHorizontalAlignment(0);
/*  5547 */     this.txtProdYear.setDisabledTextColor(new Color(0, 0, 0));
/*  5548 */     this.txtProdYear.setMaximumSize(new Dimension(60, 20));
/*  5549 */     this.txtProdYear.setMinimumSize(new Dimension(60, 20));
/*  5550 */     this.txtProdYear.setPreferredSize(new Dimension(60, 20));
/*  5551 */     this.txtProdYear.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5553 */         frmMain.this.txtProdYearActionPerformed(evt);
/*       */       }
/*  5555 */     });
/*  5556 */     gridBagConstraints = new GridBagConstraints();
/*  5557 */     gridBagConstraints.gridx = 1;
/*  5558 */     gridBagConstraints.gridy = 7;
/*  5559 */     this.pnlBasicInformation.add(this.txtProdYear, gridBagConstraints);
/*  5560 */     MouseListener mlProdYear = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  5562 */         if (e.isPopupTrigger())
/*  5563 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  5567 */         if (e.isPopupTrigger()) {
/*  5568 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  5571 */     };
/*  5572 */     this.txtProdYear.addMouseListener(mlProdYear);
/*       */     
/*  5574 */     this.chkYearRestrict.setText("Restrict Availability by Year");
/*  5575 */     this.chkYearRestrict.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5577 */         frmMain.this.chkYearRestrictActionPerformed(evt);
/*       */       }
/*  5579 */     });
/*  5580 */     gridBagConstraints = new GridBagConstraints();
/*  5581 */     gridBagConstraints.gridx = 0;
/*  5582 */     gridBagConstraints.gridy = 8;
/*  5583 */     gridBagConstraints.gridwidth = 3;
/*  5584 */     gridBagConstraints.anchor = 13;
/*  5585 */     gridBagConstraints.insets = new Insets(2, 0, 2, 0);
/*  5586 */     this.pnlBasicInformation.add(this.chkYearRestrict, gridBagConstraints);
/*       */     
/*  5588 */     this.lblTechBase.setText("Tech Base:");
/*  5589 */     gridBagConstraints = new GridBagConstraints();
/*  5590 */     gridBagConstraints.gridx = 0;
/*  5591 */     gridBagConstraints.gridy = 5;
/*  5592 */     gridBagConstraints.anchor = 13;
/*  5593 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5594 */     this.pnlBasicInformation.add(this.lblTechBase, gridBagConstraints);
/*       */     
/*  5596 */     this.cmbTechBase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/*  5597 */     this.cmbTechBase.setMaximumSize(new Dimension(150, 20));
/*  5598 */     this.cmbTechBase.setMinimumSize(new Dimension(150, 20));
/*  5599 */     this.cmbTechBase.setPreferredSize(new Dimension(150, 20));
/*  5600 */     this.cmbTechBase.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5602 */         frmMain.this.cmbTechBaseActionPerformed(evt);
/*       */       }
/*  5604 */     });
/*  5605 */     gridBagConstraints = new GridBagConstraints();
/*  5606 */     gridBagConstraints.gridx = 1;
/*  5607 */     gridBagConstraints.gridy = 5;
/*  5608 */     gridBagConstraints.gridwidth = 2;
/*  5609 */     this.pnlBasicInformation.add(this.cmbTechBase, gridBagConstraints);
/*       */     
/*  5611 */     this.cmbRulesLevel.setModel(new DefaultComboBoxModel(new String[] { "Introductory", "Tournament Legal", "Advanced Rules", "Experimental Tech", "Era Specific" }));
/*  5612 */     this.cmbRulesLevel.setSelectedIndex(1);
/*  5613 */     this.cmbRulesLevel.setMaximumSize(new Dimension(150, 20));
/*  5614 */     this.cmbRulesLevel.setMinimumSize(new Dimension(150, 20));
/*  5615 */     this.cmbRulesLevel.setPreferredSize(new Dimension(150, 20));
/*  5616 */     this.cmbRulesLevel.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5618 */         frmMain.this.cmbRulesLevelActionPerformed(evt);
/*       */       }
/*  5620 */     });
/*  5621 */     gridBagConstraints = new GridBagConstraints();
/*  5622 */     gridBagConstraints.gridx = 1;
/*  5623 */     gridBagConstraints.gridy = 3;
/*  5624 */     gridBagConstraints.gridwidth = 2;
/*  5625 */     this.pnlBasicInformation.add(this.cmbRulesLevel, gridBagConstraints);
/*       */     
/*  5627 */     this.lblRulesLevel.setText("Rules Level:");
/*  5628 */     gridBagConstraints = new GridBagConstraints();
/*  5629 */     gridBagConstraints.gridx = 0;
/*  5630 */     gridBagConstraints.gridy = 3;
/*  5631 */     gridBagConstraints.anchor = 13;
/*  5632 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5633 */     this.pnlBasicInformation.add(this.lblRulesLevel, gridBagConstraints);
/*       */     
/*  5635 */     this.jLabel65.setText("Source:");
/*  5636 */     gridBagConstraints = new GridBagConstraints();
/*  5637 */     gridBagConstraints.gridx = 0;
/*  5638 */     gridBagConstraints.gridy = 2;
/*  5639 */     gridBagConstraints.anchor = 13;
/*  5640 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/*  5641 */     this.pnlBasicInformation.add(this.jLabel65, gridBagConstraints);
/*       */     
/*  5643 */     this.txtSource.setMaximumSize(new Dimension(150, 20));
/*  5644 */     this.txtSource.setMinimumSize(new Dimension(150, 20));
/*  5645 */     this.txtSource.setPreferredSize(new Dimension(150, 20));
/*  5646 */     gridBagConstraints = new GridBagConstraints();
/*  5647 */     gridBagConstraints.gridx = 1;
/*  5648 */     gridBagConstraints.gridy = 2;
/*  5649 */     gridBagConstraints.gridwidth = 2;
/*  5650 */     this.pnlBasicInformation.add(this.txtSource, gridBagConstraints);
/*  5651 */     MouseListener mlSource = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  5653 */         if (e.isPopupTrigger())
/*  5654 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  5658 */         if (e.isPopupTrigger()) {
/*  5659 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  5662 */     };
/*  5663 */     this.txtSource.addMouseListener(mlSource);
/*  5664 */     this.pnlBasicInformation.add(this.jSeparator28, new GridBagConstraints());
/*  5665 */     this.pnlBasicInformation.add(this.jSeparator29, new GridBagConstraints());
/*       */     
/*  5667 */     this.cmbProductionEra.setModel(new DefaultComboBoxModel(new String[] { "Age of War", "Star League", "Early Succession War", "Late Succession War", "Clan Invasion", "Civil War", "Jihad", "Early Republic", "Late Republic", "Dark Ages" }));
/*  5668 */     this.cmbProductionEra.setMaximumSize(new Dimension(90, 20));
/*  5669 */     this.cmbProductionEra.setMinimumSize(new Dimension(90, 20));
/*  5670 */     this.cmbProductionEra.setPreferredSize(new Dimension(90, 20));
/*  5671 */     this.cmbProductionEra.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5673 */         frmMain.this.cmbProductionEraActionPerformed(evt);
/*       */       }
/*  5675 */     });
/*  5676 */     gridBagConstraints = new GridBagConstraints();
/*  5677 */     gridBagConstraints.gridx = 2;
/*  5678 */     gridBagConstraints.gridy = 7;
/*  5679 */     this.pnlBasicInformation.add(this.cmbProductionEra, gridBagConstraints);
/*       */     
/*  5681 */     gridBagConstraints = new GridBagConstraints();
/*  5682 */     gridBagConstraints.gridx = 0;
/*  5683 */     gridBagConstraints.gridy = 0;
/*  5684 */     gridBagConstraints.gridheight = 3;
/*  5685 */     gridBagConstraints.fill = 1;
/*  5686 */     this.pnlBasicSetup.add(this.pnlBasicInformation, gridBagConstraints);
/*       */     
/*  5688 */     this.pnlChassis.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chassis"));
/*  5689 */     this.pnlChassis.setLayout(new GridBagLayout());
/*       */     
/*  5691 */     this.lblTonnage.setText("Tonnage:");
/*  5692 */     gridBagConstraints = new GridBagConstraints();
/*  5693 */     gridBagConstraints.anchor = 13;
/*  5694 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5695 */     this.pnlChassis.add(this.lblTonnage, gridBagConstraints);
/*       */     
/*  5697 */     this.cmbTonnage.setModel(new DefaultComboBoxModel(new String[] { "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100" }));
/*  5698 */     this.cmbTonnage.setSelectedIndex(2);
/*  5699 */     this.cmbTonnage.setMaximumSize(new Dimension(60, 20));
/*  5700 */     this.cmbTonnage.setMinimumSize(new Dimension(60, 20));
/*  5701 */     this.cmbTonnage.setPreferredSize(new Dimension(60, 20));
/*  5702 */     this.cmbTonnage.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5704 */         frmMain.this.cmbTonnageActionPerformed(evt);
/*       */       }
/*  5706 */     });
/*  5707 */     gridBagConstraints = new GridBagConstraints();
/*  5708 */     gridBagConstraints.anchor = 17;
/*  5709 */     this.pnlChassis.add(this.cmbTonnage, gridBagConstraints);
/*       */     
/*  5711 */     this.lblMechType.setText("Light Mech");
/*  5712 */     gridBagConstraints = new GridBagConstraints();
/*  5713 */     gridBagConstraints.anchor = 17;
/*  5714 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/*  5715 */     this.pnlChassis.add(this.lblMechType, gridBagConstraints);
/*       */     
/*  5717 */     this.lblMotiveType.setText("Motive Type:");
/*  5718 */     gridBagConstraints = new GridBagConstraints();
/*  5719 */     gridBagConstraints.gridx = 0;
/*  5720 */     gridBagConstraints.gridy = 2;
/*  5721 */     gridBagConstraints.anchor = 13;
/*  5722 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5723 */     this.pnlChassis.add(this.lblMotiveType, gridBagConstraints);
/*       */     
/*  5725 */     this.cmbMotiveType.setModel(new DefaultComboBoxModel(new String[] { "Biped", "Quad", "Tripod" }));
/*  5726 */     this.cmbMotiveType.setMaximumSize(new Dimension(150, 20));
/*  5727 */     this.cmbMotiveType.setMinimumSize(new Dimension(150, 20));
/*  5728 */     this.cmbMotiveType.setPreferredSize(new Dimension(150, 20));
/*  5729 */     this.cmbMotiveType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5731 */         frmMain.this.cmbMotiveTypeActionPerformed(evt);
/*       */       }
/*  5733 */     });
/*  5734 */     gridBagConstraints = new GridBagConstraints();
/*  5735 */     gridBagConstraints.gridx = 1;
/*  5736 */     gridBagConstraints.gridy = 2;
/*  5737 */     gridBagConstraints.gridwidth = 2;
/*  5738 */     gridBagConstraints.anchor = 17;
/*  5739 */     this.pnlChassis.add(this.cmbMotiveType, gridBagConstraints);
/*       */     
/*  5741 */     this.lblEngineType.setText("Engine Type:");
/*  5742 */     gridBagConstraints = new GridBagConstraints();
/*  5743 */     gridBagConstraints.gridx = 0;
/*  5744 */     gridBagConstraints.gridy = 5;
/*  5745 */     gridBagConstraints.anchor = 13;
/*  5746 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5747 */     this.pnlChassis.add(this.lblEngineType, gridBagConstraints);
/*       */     
/*  5749 */     this.cmbEngineType.setModel(new DefaultComboBoxModel(new String[] { "Fusion", "Fusion XL" }));
/*  5750 */     this.cmbEngineType.setMaximumSize(new Dimension(150, 20));
/*  5751 */     this.cmbEngineType.setMinimumSize(new Dimension(150, 20));
/*  5752 */     this.cmbEngineType.setPreferredSize(new Dimension(150, 20));
/*  5753 */     this.cmbEngineType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5755 */         frmMain.this.cmbEngineTypeActionPerformed(evt);
/*       */       }
/*  5757 */     });
/*  5758 */     gridBagConstraints = new GridBagConstraints();
/*  5759 */     gridBagConstraints.gridx = 1;
/*  5760 */     gridBagConstraints.gridy = 5;
/*  5761 */     gridBagConstraints.gridwidth = 2;
/*  5762 */     gridBagConstraints.anchor = 17;
/*  5763 */     this.pnlChassis.add(this.cmbEngineType, gridBagConstraints);
/*       */     
/*  5765 */     this.lblInternalType.setText("Internal Structure:");
/*  5766 */     gridBagConstraints = new GridBagConstraints();
/*  5767 */     gridBagConstraints.gridx = 0;
/*  5768 */     gridBagConstraints.gridy = 4;
/*  5769 */     gridBagConstraints.anchor = 13;
/*  5770 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5771 */     this.pnlChassis.add(this.lblInternalType, gridBagConstraints);
/*       */     
/*  5773 */     this.cmbInternalType.setModel(new DefaultComboBoxModel(new String[] { "Standard", "Endo Steel" }));
/*  5774 */     this.cmbInternalType.setMaximumSize(new Dimension(150, 20));
/*  5775 */     this.cmbInternalType.setMinimumSize(new Dimension(150, 20));
/*  5776 */     this.cmbInternalType.setPreferredSize(new Dimension(150, 20));
/*  5777 */     this.cmbInternalType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5779 */         frmMain.this.cmbInternalTypeActionPerformed(evt);
/*       */       }
/*  5781 */     });
/*  5782 */     gridBagConstraints = new GridBagConstraints();
/*  5783 */     gridBagConstraints.gridx = 1;
/*  5784 */     gridBagConstraints.gridy = 4;
/*  5785 */     gridBagConstraints.gridwidth = 2;
/*  5786 */     this.pnlChassis.add(this.cmbInternalType, gridBagConstraints);
/*       */     
/*  5788 */     this.lblGyroType.setText("Gyro Type:");
/*  5789 */     gridBagConstraints = new GridBagConstraints();
/*  5790 */     gridBagConstraints.gridx = 0;
/*  5791 */     gridBagConstraints.gridy = 6;
/*  5792 */     gridBagConstraints.anchor = 13;
/*  5793 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5794 */     this.pnlChassis.add(this.lblGyroType, gridBagConstraints);
/*       */     
/*  5796 */     this.cmbGyroType.setModel(new DefaultComboBoxModel(new String[] { "Standard" }));
/*  5797 */     this.cmbGyroType.setMaximumSize(new Dimension(150, 20));
/*  5798 */     this.cmbGyroType.setMinimumSize(new Dimension(150, 20));
/*  5799 */     this.cmbGyroType.setPreferredSize(new Dimension(150, 20));
/*  5800 */     this.cmbGyroType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5802 */         frmMain.this.cmbGyroTypeActionPerformed(evt);
/*       */       }
/*  5804 */     });
/*  5805 */     gridBagConstraints = new GridBagConstraints();
/*  5806 */     gridBagConstraints.gridx = 1;
/*  5807 */     gridBagConstraints.gridy = 6;
/*  5808 */     gridBagConstraints.gridwidth = 2;
/*  5809 */     this.pnlChassis.add(this.cmbGyroType, gridBagConstraints);
/*       */     
/*  5811 */     this.lblCockpit.setText("Cockpit Type:");
/*  5812 */     gridBagConstraints = new GridBagConstraints();
/*  5813 */     gridBagConstraints.gridx = 0;
/*  5814 */     gridBagConstraints.gridy = 7;
/*  5815 */     gridBagConstraints.anchor = 13;
/*  5816 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5817 */     this.pnlChassis.add(this.lblCockpit, gridBagConstraints);
/*       */     
/*  5819 */     this.cmbCockpitType.setModel(new DefaultComboBoxModel(new String[] { "Standard" }));
/*  5820 */     this.cmbCockpitType.setMaximumSize(new Dimension(150, 20));
/*  5821 */     this.cmbCockpitType.setMinimumSize(new Dimension(150, 20));
/*  5822 */     this.cmbCockpitType.setPreferredSize(new Dimension(150, 20));
/*  5823 */     this.cmbCockpitType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5825 */         frmMain.this.cmbCockpitTypeActionPerformed(evt);
/*       */       }
/*  5827 */     });
/*  5828 */     gridBagConstraints = new GridBagConstraints();
/*  5829 */     gridBagConstraints.gridx = 1;
/*  5830 */     gridBagConstraints.gridy = 7;
/*  5831 */     gridBagConstraints.gridwidth = 2;
/*  5832 */     this.pnlChassis.add(this.cmbCockpitType, gridBagConstraints);
/*       */     
/*  5834 */     this.lblPhysEnhance.setText("Enhancements:");
/*  5835 */     gridBagConstraints = new GridBagConstraints();
/*  5836 */     gridBagConstraints.gridx = 0;
/*  5837 */     gridBagConstraints.gridy = 9;
/*  5838 */     gridBagConstraints.anchor = 13;
/*  5839 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5840 */     this.pnlChassis.add(this.lblPhysEnhance, gridBagConstraints);
/*       */     
/*  5842 */     this.cmbPhysEnhance.setModel(new DefaultComboBoxModel(new String[] { "None", "MASC" }));
/*  5843 */     this.cmbPhysEnhance.setMaximumSize(new Dimension(150, 20));
/*  5844 */     this.cmbPhysEnhance.setMinimumSize(new Dimension(150, 20));
/*  5845 */     this.cmbPhysEnhance.setPreferredSize(new Dimension(150, 20));
/*  5846 */     this.cmbPhysEnhance.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5848 */         frmMain.this.cmbPhysEnhanceActionPerformed(evt);
/*       */       }
/*  5850 */     });
/*  5851 */     gridBagConstraints = new GridBagConstraints();
/*  5852 */     gridBagConstraints.gridx = 1;
/*  5853 */     gridBagConstraints.gridy = 9;
/*  5854 */     gridBagConstraints.gridwidth = 2;
/*  5855 */     this.pnlChassis.add(this.cmbPhysEnhance, gridBagConstraints);
/*       */     
/*  5857 */     this.chkOmnimech.setText("Omnimech");
/*  5858 */     this.chkOmnimech.setEnabled(false);
/*  5859 */     this.chkOmnimech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5861 */         frmMain.this.chkOmnimechActionPerformed(evt);
/*       */       }
/*  5863 */     });
/*  5864 */     gridBagConstraints = new GridBagConstraints();
/*  5865 */     gridBagConstraints.gridx = 1;
/*  5866 */     gridBagConstraints.gridy = 3;
/*  5867 */     gridBagConstraints.gridwidth = 2;
/*  5868 */     gridBagConstraints.anchor = 13;
/*  5869 */     gridBagConstraints.insets = new Insets(2, 0, 2, 2);
/*  5870 */     this.pnlChassis.add(this.chkOmnimech, gridBagConstraints);
/*       */     
/*  5872 */     this.cmbMechType.setModel(new DefaultComboBoxModel(new String[] { "BattleMech", "IndustrialMech" }));
/*  5873 */     this.cmbMechType.setMaximumSize(new Dimension(150, 20));
/*  5874 */     this.cmbMechType.setMinimumSize(new Dimension(150, 20));
/*  5875 */     this.cmbMechType.setPreferredSize(new Dimension(150, 20));
/*  5876 */     this.cmbMechType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5878 */         frmMain.this.cmbMechTypeActionPerformed(evt);
/*       */       }
/*  5880 */     });
/*  5881 */     gridBagConstraints = new GridBagConstraints();
/*  5882 */     gridBagConstraints.gridx = 1;
/*  5883 */     gridBagConstraints.gridy = 1;
/*  5884 */     gridBagConstraints.gridwidth = 2;
/*  5885 */     this.pnlChassis.add(this.cmbMechType, gridBagConstraints);
/*       */     
/*  5887 */     this.lblUnitType.setText("Mech Type:");
/*  5888 */     gridBagConstraints = new GridBagConstraints();
/*  5889 */     gridBagConstraints.gridx = 0;
/*  5890 */     gridBagConstraints.gridy = 1;
/*  5891 */     gridBagConstraints.anchor = 13;
/*  5892 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5893 */     this.pnlChassis.add(this.lblUnitType, gridBagConstraints);
/*       */     
/*  5895 */     this.chkCommandConsole.setText("Use Command Console");
/*  5896 */     this.chkCommandConsole.setEnabled(false);
/*  5897 */     this.chkCommandConsole.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5899 */         frmMain.this.chkCommandConsoleActionPerformed(evt);
/*       */       }
/*  5901 */     });
/*  5902 */     gridBagConstraints = new GridBagConstraints();
/*  5903 */     gridBagConstraints.gridx = 0;
/*  5904 */     gridBagConstraints.gridy = 8;
/*  5905 */     gridBagConstraints.gridwidth = 3;
/*  5906 */     gridBagConstraints.anchor = 13;
/*  5907 */     this.pnlChassis.add(this.chkCommandConsole, gridBagConstraints);
/*       */     
/*  5909 */     gridBagConstraints = new GridBagConstraints();
/*  5910 */     gridBagConstraints.gridx = 0;
/*  5911 */     gridBagConstraints.gridy = 3;
/*  5912 */     gridBagConstraints.gridheight = 2;
/*  5913 */     gridBagConstraints.fill = 2;
/*  5914 */     gridBagConstraints.anchor = 11;
/*  5915 */     this.pnlBasicSetup.add(this.pnlChassis, gridBagConstraints);
/*       */     
/*  5917 */     this.pnlHeatSinks.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Heat Sinks"));
/*  5918 */     this.pnlHeatSinks.setLayout(new GridBagLayout());
/*       */     
/*  5920 */     this.lblHeatSinkType.setText("Heat Sink Type:");
/*  5921 */     gridBagConstraints = new GridBagConstraints();
/*  5922 */     gridBagConstraints.anchor = 13;
/*  5923 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5924 */     this.pnlHeatSinks.add(this.lblHeatSinkType, gridBagConstraints);
/*       */     
/*  5926 */     this.cmbHeatSinkType.setModel(new DefaultComboBoxModel(new String[] { "Single", "Double" }));
/*  5927 */     this.cmbHeatSinkType.setMaximumSize(new Dimension(150, 20));
/*  5928 */     this.cmbHeatSinkType.setMinimumSize(new Dimension(150, 20));
/*  5929 */     this.cmbHeatSinkType.setPreferredSize(new Dimension(150, 20));
/*  5930 */     this.cmbHeatSinkType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  5932 */         frmMain.this.cmbHeatSinkTypeActionPerformed(evt);
/*       */       }
/*  5934 */     });
/*  5935 */     gridBagConstraints = new GridBagConstraints();
/*  5936 */     gridBagConstraints.gridwidth = 2;
/*  5937 */     this.pnlHeatSinks.add(this.cmbHeatSinkType, gridBagConstraints);
/*       */     
/*  5939 */     this.lblHSNumber.setText("Number of Heat Sinks:");
/*  5940 */     gridBagConstraints = new GridBagConstraints();
/*  5941 */     gridBagConstraints.gridx = 0;
/*  5942 */     gridBagConstraints.gridy = 1;
/*  5943 */     gridBagConstraints.anchor = 13;
/*  5944 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5945 */     this.pnlHeatSinks.add(this.lblHSNumber, gridBagConstraints);
/*       */     
/*  5947 */     this.spnNumberOfHS.setModel(new SpinnerNumberModel(10, 10, 65, 1));
/*  5948 */     this.spnNumberOfHS.setMaximumSize(new Dimension(45, 20));
/*  5949 */     this.spnNumberOfHS.setMinimumSize(new Dimension(45, 20));
/*  5950 */     this.spnNumberOfHS.setPreferredSize(new Dimension(45, 20));
/*  5951 */     this.spnNumberOfHS.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  5953 */         frmMain.this.spnNumberOfHSStateChanged(evt);
/*       */       }
/*  5955 */     });
/*  5956 */     gridBagConstraints = new GridBagConstraints();
/*  5957 */     gridBagConstraints.gridx = 1;
/*  5958 */     gridBagConstraints.gridy = 1;
/*  5959 */     this.pnlHeatSinks.add(this.spnNumberOfHS, gridBagConstraints);
/*       */     
/*  5961 */     gridBagConstraints = new GridBagConstraints();
/*  5962 */     gridBagConstraints.gridx = 1;
/*  5963 */     gridBagConstraints.gridy = 2;
/*  5964 */     gridBagConstraints.fill = 1;
/*  5965 */     this.pnlBasicSetup.add(this.pnlHeatSinks, gridBagConstraints);
/*       */     
/*  5967 */     this.pnlMovement.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Movement"));
/*  5968 */     this.pnlMovement.setLayout(new GridBagLayout());
/*       */     
/*  5970 */     this.lblWalkMP.setText("Walking MP:");
/*  5971 */     gridBagConstraints = new GridBagConstraints();
/*  5972 */     gridBagConstraints.anchor = 13;
/*  5973 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5974 */     this.pnlMovement.add(this.lblWalkMP, gridBagConstraints);
/*       */     
/*  5976 */     this.spnWalkMP.setModel(new SpinnerNumberModel(1, 1, 20, 1));
/*  5977 */     this.spnWalkMP.setMaximumSize(new Dimension(45, 20));
/*  5978 */     this.spnWalkMP.setMinimumSize(new Dimension(45, 20));
/*  5979 */     this.spnWalkMP.setPreferredSize(new Dimension(45, 20));
/*  5980 */     this.spnWalkMP.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  5982 */         frmMain.this.spnWalkMPStateChanged(evt);
/*       */       }
/*  5984 */     });
/*  5985 */     gridBagConstraints = new GridBagConstraints();
/*  5986 */     gridBagConstraints.anchor = 17;
/*  5987 */     this.pnlMovement.add(this.spnWalkMP, gridBagConstraints);
/*       */     
/*  5989 */     this.lblRunMPLabel.setText("Running MP:");
/*  5990 */     gridBagConstraints = new GridBagConstraints();
/*  5991 */     gridBagConstraints.gridx = 0;
/*  5992 */     gridBagConstraints.gridy = 1;
/*  5993 */     gridBagConstraints.anchor = 13;
/*  5994 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  5995 */     this.pnlMovement.add(this.lblRunMPLabel, gridBagConstraints);
/*       */     
/*  5997 */     this.lblRunMP.setHorizontalAlignment(0);
/*  5998 */     this.lblRunMP.setText("2");
/*  5999 */     this.lblRunMP.setMaximumSize(new Dimension(45, 20));
/*  6000 */     this.lblRunMP.setMinimumSize(new Dimension(45, 20));
/*  6001 */     this.lblRunMP.setPreferredSize(new Dimension(45, 20));
/*  6002 */     gridBagConstraints = new GridBagConstraints();
/*  6003 */     gridBagConstraints.gridx = 1;
/*  6004 */     gridBagConstraints.gridy = 1;
/*  6005 */     gridBagConstraints.anchor = 17;
/*  6006 */     this.pnlMovement.add(this.lblRunMP, gridBagConstraints);
/*       */     
/*  6008 */     this.lblJumpMP.setText("Jumping MP:");
/*  6009 */     gridBagConstraints = new GridBagConstraints();
/*  6010 */     gridBagConstraints.gridx = 0;
/*  6011 */     gridBagConstraints.gridy = 2;
/*  6012 */     gridBagConstraints.anchor = 13;
/*  6013 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6014 */     this.pnlMovement.add(this.lblJumpMP, gridBagConstraints);
/*       */     
/*  6016 */     this.spnJumpMP.setModel(new SpinnerNumberModel(0, 0, 1, 1));
/*  6017 */     this.spnJumpMP.setMaximumSize(new Dimension(45, 20));
/*  6018 */     this.spnJumpMP.setMinimumSize(new Dimension(45, 20));
/*  6019 */     this.spnJumpMP.setPreferredSize(new Dimension(45, 20));
/*  6020 */     this.spnJumpMP.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6022 */         frmMain.this.spnJumpMPStateChanged(evt);
/*       */       }
/*  6024 */     });
/*  6025 */     gridBagConstraints = new GridBagConstraints();
/*  6026 */     gridBagConstraints.gridx = 1;
/*  6027 */     gridBagConstraints.gridy = 2;
/*  6028 */     gridBagConstraints.anchor = 17;
/*  6029 */     this.pnlMovement.add(this.spnJumpMP, gridBagConstraints);
/*       */     
/*  6031 */     this.cmbJumpJetType.setModel(new DefaultComboBoxModel(new String[] { "Standard Jump Jet", "Improved Jump Jet" }));
/*  6032 */     this.cmbJumpJetType.setMaximumSize(new Dimension(150, 20));
/*  6033 */     this.cmbJumpJetType.setMinimumSize(new Dimension(150, 20));
/*  6034 */     this.cmbJumpJetType.setPreferredSize(new Dimension(150, 20));
/*  6035 */     this.cmbJumpJetType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6037 */         frmMain.this.cmbJumpJetTypeActionPerformed(evt);
/*       */       }
/*  6039 */     });
/*  6040 */     gridBagConstraints = new GridBagConstraints();
/*  6041 */     gridBagConstraints.gridx = 2;
/*  6042 */     gridBagConstraints.gridy = 2;
/*  6043 */     gridBagConstraints.anchor = 17;
/*  6044 */     gridBagConstraints.insets = new Insets(2, 4, 0, 0);
/*  6045 */     this.pnlMovement.add(this.cmbJumpJetType, gridBagConstraints);
/*       */     
/*  6047 */     this.jLabel36.setText("Jump Jet Type:");
/*  6048 */     gridBagConstraints = new GridBagConstraints();
/*  6049 */     gridBagConstraints.gridx = 2;
/*  6050 */     gridBagConstraints.gridy = 1;
/*  6051 */     this.pnlMovement.add(this.jLabel36, gridBagConstraints);
/*       */     
/*  6053 */     this.jLabel53.setText("Booster MP:");
/*  6054 */     this.jLabel53.setEnabled(false);
/*  6055 */     gridBagConstraints = new GridBagConstraints();
/*  6056 */     gridBagConstraints.gridx = 0;
/*  6057 */     gridBagConstraints.gridy = 3;
/*  6058 */     gridBagConstraints.anchor = 13;
/*  6059 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6060 */     this.pnlMovement.add(this.jLabel53, gridBagConstraints);
/*       */     
/*  6062 */     this.spnBoosterMP.setEnabled(false);
/*  6063 */     this.spnBoosterMP.setMaximumSize(new Dimension(45, 20));
/*  6064 */     this.spnBoosterMP.setMinimumSize(new Dimension(45, 20));
/*  6065 */     this.spnBoosterMP.setPreferredSize(new Dimension(45, 20));
/*  6066 */     this.spnBoosterMP.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6068 */         frmMain.this.spnBoosterMPStateChanged(evt);
/*       */       }
/*  6070 */     });
/*  6071 */     gridBagConstraints = new GridBagConstraints();
/*  6072 */     gridBagConstraints.gridx = 1;
/*  6073 */     gridBagConstraints.gridy = 3;
/*  6074 */     this.pnlMovement.add(this.spnBoosterMP, gridBagConstraints);
/*       */     
/*  6076 */     this.chkBoosters.setText("'Mech Jump Boosters");
/*  6077 */     this.chkBoosters.setEnabled(false);
/*  6078 */     this.chkBoosters.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6080 */         frmMain.this.chkBoostersActionPerformed(evt);
/*       */       }
/*  6082 */     });
/*  6083 */     gridBagConstraints = new GridBagConstraints();
/*  6084 */     gridBagConstraints.gridx = 2;
/*  6085 */     gridBagConstraints.gridy = 3;
/*  6086 */     gridBagConstraints.insets = new Insets(2, 0, 0, 0);
/*  6087 */     this.pnlMovement.add(this.chkBoosters, gridBagConstraints);
/*       */     
/*  6089 */     this.lblMoveSummary.setText("W/R/J/B: 12/20/12/12");
/*  6090 */     gridBagConstraints = new GridBagConstraints();
/*  6091 */     gridBagConstraints.anchor = 17;
/*  6092 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  6093 */     this.pnlMovement.add(this.lblMoveSummary, gridBagConstraints);
/*       */     
/*  6095 */     this.jLabel1.setText("Engine Rating: ");
/*  6096 */     gridBagConstraints = new GridBagConstraints();
/*  6097 */     gridBagConstraints.gridx = 0;
/*  6098 */     gridBagConstraints.gridy = 4;
/*  6099 */     gridBagConstraints.gridwidth = 2;
/*  6100 */     gridBagConstraints.anchor = 13;
/*  6101 */     gridBagConstraints.insets = new Insets(2, 0, 0, 0);
/*  6102 */     this.pnlMovement.add(this.jLabel1, gridBagConstraints);
/*       */     
/*  6104 */     this.txtEngineRating.setHorizontalAlignment(0);
/*  6105 */     this.txtEngineRating.setText("100");
/*  6106 */     this.txtEngineRating.setDisabledTextColor(new Color(0, 0, 0));
/*  6107 */     this.txtEngineRating.setEnabled(false);
/*  6108 */     this.txtEngineRating.setMaximumSize(new Dimension(65, 20));
/*  6109 */     this.txtEngineRating.setMinimumSize(new Dimension(65, 20));
/*  6110 */     this.txtEngineRating.setPreferredSize(new Dimension(65, 20));
/*  6111 */     gridBagConstraints = new GridBagConstraints();
/*  6112 */     gridBagConstraints.gridx = 2;
/*  6113 */     gridBagConstraints.gridy = 4;
/*  6114 */     gridBagConstraints.anchor = 17;
/*  6115 */     gridBagConstraints.insets = new Insets(2, 4, 0, 0);
/*  6116 */     this.pnlMovement.add(this.txtEngineRating, gridBagConstraints);
/*       */     
/*  6118 */     gridBagConstraints = new GridBagConstraints();
/*  6119 */     gridBagConstraints.gridx = 1;
/*  6120 */     gridBagConstraints.gridy = 0;
/*  6121 */     gridBagConstraints.gridheight = 2;
/*  6122 */     gridBagConstraints.fill = 1;
/*  6123 */     this.pnlBasicSetup.add(this.pnlMovement, gridBagConstraints);
/*       */     
/*  6125 */     this.pnlOmniInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Omnimech Configuration"));
/*  6126 */     this.pnlOmniInfo.setLayout(new GridBagLayout());
/*       */     
/*  6128 */     this.btnLockChassis.setText("Lock Chassis");
/*  6129 */     this.btnLockChassis.setEnabled(false);
/*  6130 */     this.btnLockChassis.setMaximumSize(new Dimension(200, 23));
/*  6131 */     this.btnLockChassis.setMinimumSize(new Dimension(105, 23));
/*  6132 */     this.btnLockChassis.setPreferredSize(new Dimension(120, 23));
/*  6133 */     this.btnLockChassis.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6135 */         frmMain.this.btnLockChassisActionPerformed(evt);
/*       */       }
/*  6137 */     });
/*  6138 */     gridBagConstraints = new GridBagConstraints();
/*  6139 */     gridBagConstraints.gridx = 0;
/*  6140 */     gridBagConstraints.gridy = 0;
/*  6141 */     this.pnlOmniInfo.add(this.btnLockChassis, gridBagConstraints);
/*       */     
/*  6143 */     this.btnAddVariant.setText("Add Variant");
/*  6144 */     this.btnAddVariant.setEnabled(false);
/*  6145 */     this.btnAddVariant.setMaximumSize(new Dimension(200, 23));
/*  6146 */     this.btnAddVariant.setMinimumSize(new Dimension(80, 23));
/*  6147 */     this.btnAddVariant.setPreferredSize(new Dimension(120, 23));
/*  6148 */     this.btnAddVariant.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6150 */         frmMain.this.btnAddVariantActionPerformed(evt);
/*       */       }
/*  6152 */     });
/*  6153 */     gridBagConstraints = new GridBagConstraints();
/*  6154 */     gridBagConstraints.gridx = 0;
/*  6155 */     gridBagConstraints.gridy = 1;
/*  6156 */     this.pnlOmniInfo.add(this.btnAddVariant, gridBagConstraints);
/*       */     
/*  6158 */     this.btnDeleteVariant.setText("Delete Variant");
/*  6159 */     this.btnDeleteVariant.setEnabled(false);
/*  6160 */     this.btnDeleteVariant.setMaximumSize(new Dimension(200, 23));
/*  6161 */     this.btnDeleteVariant.setMinimumSize(new Dimension(80, 23));
/*  6162 */     this.btnDeleteVariant.setPreferredSize(new Dimension(120, 23));
/*  6163 */     this.btnDeleteVariant.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6165 */         frmMain.this.btnDeleteVariantActionPerformed(evt);
/*       */       }
/*  6167 */     });
/*  6168 */     gridBagConstraints = new GridBagConstraints();
/*  6169 */     gridBagConstraints.gridx = 0;
/*  6170 */     gridBagConstraints.gridy = 2;
/*  6171 */     this.pnlOmniInfo.add(this.btnDeleteVariant, gridBagConstraints);
/*       */     
/*  6173 */     this.btnRenameVariant.setText("Rename Variant");
/*  6174 */     this.btnRenameVariant.setEnabled(false);
/*  6175 */     this.btnRenameVariant.setMinimumSize(new Dimension(80, 23));
/*  6176 */     this.btnRenameVariant.setPreferredSize(new Dimension(120, 23));
/*  6177 */     this.btnRenameVariant.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6179 */         frmMain.this.btnRenameVariantActionPerformed(evt);
/*       */       }
/*  6181 */     });
/*  6182 */     gridBagConstraints = new GridBagConstraints();
/*  6183 */     gridBagConstraints.gridx = 0;
/*  6184 */     gridBagConstraints.gridy = 3;
/*  6185 */     this.pnlOmniInfo.add(this.btnRenameVariant, gridBagConstraints);
/*       */     
/*  6187 */     gridBagConstraints = new GridBagConstraints();
/*  6188 */     gridBagConstraints.gridx = 2;
/*  6189 */     gridBagConstraints.gridy = 0;
/*  6190 */     gridBagConstraints.fill = 2;
/*  6191 */     gridBagConstraints.anchor = 11;
/*  6192 */     this.pnlBasicSetup.add(this.pnlOmniInfo, gridBagConstraints);
/*       */     
/*  6194 */     this.pnlBasicSummary.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Basic Setup Summary"));
/*  6195 */     this.pnlBasicSummary.setLayout(new GridBagLayout());
/*       */     
/*  6197 */     this.lblSumStructure.setText("Internal Structure:");
/*  6198 */     gridBagConstraints = new GridBagConstraints();
/*  6199 */     gridBagConstraints.gridx = 0;
/*  6200 */     gridBagConstraints.gridy = 1;
/*  6201 */     gridBagConstraints.anchor = 13;
/*  6202 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6203 */     this.pnlBasicSummary.add(this.lblSumStructure, gridBagConstraints);
/*       */     
/*  6205 */     this.txtSumIntTon.setHorizontalAlignment(11);
/*  6206 */     this.txtSumIntTon.setText("000.00");
/*  6207 */     this.txtSumIntTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6208 */     this.txtSumIntTon.setEnabled(false);
/*  6209 */     this.txtSumIntTon.setMaximumSize(new Dimension(50, 20));
/*  6210 */     this.txtSumIntTon.setMinimumSize(new Dimension(50, 20));
/*  6211 */     this.txtSumIntTon.setPreferredSize(new Dimension(50, 20));
/*  6212 */     gridBagConstraints = new GridBagConstraints();
/*  6213 */     gridBagConstraints.gridx = 1;
/*  6214 */     gridBagConstraints.gridy = 1;
/*  6215 */     this.pnlBasicSummary.add(this.txtSumIntTon, gridBagConstraints);
/*       */     
/*  6217 */     this.lblSumEngine.setText("Engine:");
/*  6218 */     gridBagConstraints = new GridBagConstraints();
/*  6219 */     gridBagConstraints.gridx = 0;
/*  6220 */     gridBagConstraints.gridy = 2;
/*  6221 */     gridBagConstraints.anchor = 13;
/*  6222 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6223 */     this.pnlBasicSummary.add(this.lblSumEngine, gridBagConstraints);
/*       */     
/*  6225 */     this.txtSumEngTon.setHorizontalAlignment(11);
/*  6226 */     this.txtSumEngTon.setText("000.00");
/*  6227 */     this.txtSumEngTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6228 */     this.txtSumEngTon.setEnabled(false);
/*  6229 */     this.txtSumEngTon.setMaximumSize(new Dimension(50, 20));
/*  6230 */     this.txtSumEngTon.setMinimumSize(new Dimension(50, 20));
/*  6231 */     this.txtSumEngTon.setPreferredSize(new Dimension(50, 20));
/*  6232 */     gridBagConstraints = new GridBagConstraints();
/*  6233 */     gridBagConstraints.gridx = 1;
/*  6234 */     gridBagConstraints.gridy = 2;
/*  6235 */     this.pnlBasicSummary.add(this.txtSumEngTon, gridBagConstraints);
/*       */     
/*  6237 */     this.lblSumGyro.setText("Gyro:");
/*  6238 */     gridBagConstraints = new GridBagConstraints();
/*  6239 */     gridBagConstraints.gridx = 0;
/*  6240 */     gridBagConstraints.gridy = 3;
/*  6241 */     gridBagConstraints.anchor = 13;
/*  6242 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6243 */     this.pnlBasicSummary.add(this.lblSumGyro, gridBagConstraints);
/*       */     
/*  6245 */     this.txtSumGyrTon.setHorizontalAlignment(11);
/*  6246 */     this.txtSumGyrTon.setText("000.00");
/*  6247 */     this.txtSumGyrTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6248 */     this.txtSumGyrTon.setEnabled(false);
/*  6249 */     this.txtSumGyrTon.setMaximumSize(new Dimension(50, 20));
/*  6250 */     this.txtSumGyrTon.setMinimumSize(new Dimension(50, 20));
/*  6251 */     this.txtSumGyrTon.setPreferredSize(new Dimension(50, 20));
/*  6252 */     gridBagConstraints = new GridBagConstraints();
/*  6253 */     gridBagConstraints.gridx = 1;
/*  6254 */     gridBagConstraints.gridy = 3;
/*  6255 */     this.pnlBasicSummary.add(this.txtSumGyrTon, gridBagConstraints);
/*       */     
/*  6257 */     this.lblSumHeadItem.setText("Item");
/*  6258 */     this.pnlBasicSummary.add(this.lblSumHeadItem, new GridBagConstraints());
/*       */     
/*  6260 */     this.lblSumHeadTons.setText("Tonnage");
/*  6261 */     this.pnlBasicSummary.add(this.lblSumHeadTons, new GridBagConstraints());
/*       */     
/*  6263 */     this.lblSumHeadCrits.setText("Crits");
/*  6264 */     this.pnlBasicSummary.add(this.lblSumHeadCrits, new GridBagConstraints());
/*       */     
/*  6266 */     this.txtSumIntCrt.setHorizontalAlignment(11);
/*  6267 */     this.txtSumIntCrt.setText("00");
/*  6268 */     this.txtSumIntCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6269 */     this.txtSumIntCrt.setEnabled(false);
/*  6270 */     this.txtSumIntCrt.setMaximumSize(new Dimension(40, 20));
/*  6271 */     this.txtSumIntCrt.setMinimumSize(new Dimension(40, 20));
/*  6272 */     this.txtSumIntCrt.setPreferredSize(new Dimension(40, 20));
/*  6273 */     gridBagConstraints = new GridBagConstraints();
/*  6274 */     gridBagConstraints.gridx = 2;
/*  6275 */     gridBagConstraints.gridy = 1;
/*  6276 */     this.pnlBasicSummary.add(this.txtSumIntCrt, gridBagConstraints);
/*       */     
/*  6278 */     this.txtSumEngCrt.setHorizontalAlignment(11);
/*  6279 */     this.txtSumEngCrt.setText("00");
/*  6280 */     this.txtSumEngCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6281 */     this.txtSumEngCrt.setEnabled(false);
/*  6282 */     this.txtSumEngCrt.setMaximumSize(new Dimension(40, 20));
/*  6283 */     this.txtSumEngCrt.setMinimumSize(new Dimension(40, 20));
/*  6284 */     this.txtSumEngCrt.setPreferredSize(new Dimension(40, 20));
/*  6285 */     gridBagConstraints = new GridBagConstraints();
/*  6286 */     gridBagConstraints.gridx = 2;
/*  6287 */     gridBagConstraints.gridy = 2;
/*  6288 */     this.pnlBasicSummary.add(this.txtSumEngCrt, gridBagConstraints);
/*       */     
/*  6290 */     this.txtSumGyrCrt.setHorizontalAlignment(11);
/*  6291 */     this.txtSumGyrCrt.setText("00");
/*  6292 */     this.txtSumGyrCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6293 */     this.txtSumGyrCrt.setEnabled(false);
/*  6294 */     this.txtSumGyrCrt.setMaximumSize(new Dimension(40, 20));
/*  6295 */     this.txtSumGyrCrt.setMinimumSize(new Dimension(40, 20));
/*  6296 */     this.txtSumGyrCrt.setPreferredSize(new Dimension(40, 20));
/*  6297 */     gridBagConstraints = new GridBagConstraints();
/*  6298 */     gridBagConstraints.gridx = 2;
/*  6299 */     gridBagConstraints.gridy = 3;
/*  6300 */     this.pnlBasicSummary.add(this.txtSumGyrCrt, gridBagConstraints);
/*       */     
/*  6302 */     this.lblSumCockpit.setText("Cockpit:");
/*  6303 */     gridBagConstraints = new GridBagConstraints();
/*  6304 */     gridBagConstraints.gridx = 0;
/*  6305 */     gridBagConstraints.gridy = 4;
/*  6306 */     gridBagConstraints.anchor = 13;
/*  6307 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6308 */     this.pnlBasicSummary.add(this.lblSumCockpit, gridBagConstraints);
/*       */     
/*  6310 */     this.txtSumCocTon.setHorizontalAlignment(11);
/*  6311 */     this.txtSumCocTon.setText("000.00");
/*  6312 */     this.txtSumCocTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6313 */     this.txtSumCocTon.setEnabled(false);
/*  6314 */     this.txtSumCocTon.setMaximumSize(new Dimension(50, 20));
/*  6315 */     this.txtSumCocTon.setMinimumSize(new Dimension(50, 20));
/*  6316 */     this.txtSumCocTon.setPreferredSize(new Dimension(50, 20));
/*  6317 */     gridBagConstraints = new GridBagConstraints();
/*  6318 */     gridBagConstraints.gridx = 1;
/*  6319 */     gridBagConstraints.gridy = 4;
/*  6320 */     this.pnlBasicSummary.add(this.txtSumCocTon, gridBagConstraints);
/*       */     
/*  6322 */     this.txtSumCocCrt.setHorizontalAlignment(11);
/*  6323 */     this.txtSumCocCrt.setText("00");
/*  6324 */     this.txtSumCocCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6325 */     this.txtSumCocCrt.setEnabled(false);
/*  6326 */     this.txtSumCocCrt.setMaximumSize(new Dimension(40, 20));
/*  6327 */     this.txtSumCocCrt.setMinimumSize(new Dimension(40, 20));
/*  6328 */     this.txtSumCocCrt.setPreferredSize(new Dimension(40, 20));
/*  6329 */     gridBagConstraints = new GridBagConstraints();
/*  6330 */     gridBagConstraints.gridx = 2;
/*  6331 */     gridBagConstraints.gridy = 4;
/*  6332 */     this.pnlBasicSummary.add(this.txtSumCocCrt, gridBagConstraints);
/*       */     
/*  6334 */     this.lblSumEnhance.setText("Enhancements:");
/*  6335 */     gridBagConstraints = new GridBagConstraints();
/*  6336 */     gridBagConstraints.gridx = 0;
/*  6337 */     gridBagConstraints.gridy = 6;
/*  6338 */     gridBagConstraints.anchor = 13;
/*  6339 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6340 */     this.pnlBasicSummary.add(this.lblSumEnhance, gridBagConstraints);
/*       */     
/*  6342 */     this.txtSumEnhTon.setHorizontalAlignment(11);
/*  6343 */     this.txtSumEnhTon.setText("000.00");
/*  6344 */     this.txtSumEnhTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6345 */     this.txtSumEnhTon.setEnabled(false);
/*  6346 */     this.txtSumEnhTon.setMaximumSize(new Dimension(50, 20));
/*  6347 */     this.txtSumEnhTon.setMinimumSize(new Dimension(50, 20));
/*  6348 */     this.txtSumEnhTon.setPreferredSize(new Dimension(50, 20));
/*  6349 */     gridBagConstraints = new GridBagConstraints();
/*  6350 */     gridBagConstraints.gridx = 1;
/*  6351 */     gridBagConstraints.gridy = 6;
/*  6352 */     this.pnlBasicSummary.add(this.txtSumEnhTon, gridBagConstraints);
/*       */     
/*  6354 */     this.txtSumEnhCrt.setHorizontalAlignment(11);
/*  6355 */     this.txtSumEnhCrt.setText("00");
/*  6356 */     this.txtSumEnhCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6357 */     this.txtSumEnhCrt.setEnabled(false);
/*  6358 */     this.txtSumEnhCrt.setMaximumSize(new Dimension(40, 20));
/*  6359 */     this.txtSumEnhCrt.setMinimumSize(new Dimension(40, 20));
/*  6360 */     this.txtSumEnhCrt.setPreferredSize(new Dimension(40, 20));
/*  6361 */     gridBagConstraints = new GridBagConstraints();
/*  6362 */     gridBagConstraints.gridx = 2;
/*  6363 */     gridBagConstraints.gridy = 6;
/*  6364 */     this.pnlBasicSummary.add(this.txtSumEnhCrt, gridBagConstraints);
/*       */     
/*  6366 */     this.lblSumHeatSinks.setText("Heat Sinks:");
/*  6367 */     gridBagConstraints = new GridBagConstraints();
/*  6368 */     gridBagConstraints.gridx = 0;
/*  6369 */     gridBagConstraints.gridy = 5;
/*  6370 */     gridBagConstraints.anchor = 13;
/*  6371 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6372 */     this.pnlBasicSummary.add(this.lblSumHeatSinks, gridBagConstraints);
/*       */     
/*  6374 */     this.txtSumHSTon.setHorizontalAlignment(11);
/*  6375 */     this.txtSumHSTon.setText("000.00");
/*  6376 */     this.txtSumHSTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6377 */     this.txtSumHSTon.setEnabled(false);
/*  6378 */     this.txtSumHSTon.setMaximumSize(new Dimension(50, 20));
/*  6379 */     this.txtSumHSTon.setMinimumSize(new Dimension(50, 20));
/*  6380 */     this.txtSumHSTon.setPreferredSize(new Dimension(50, 20));
/*  6381 */     gridBagConstraints = new GridBagConstraints();
/*  6382 */     gridBagConstraints.gridx = 1;
/*  6383 */     gridBagConstraints.gridy = 5;
/*  6384 */     this.pnlBasicSummary.add(this.txtSumHSTon, gridBagConstraints);
/*       */     
/*  6386 */     this.txtSumHSCrt.setHorizontalAlignment(11);
/*  6387 */     this.txtSumHSCrt.setText("00");
/*  6388 */     this.txtSumHSCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6389 */     this.txtSumHSCrt.setEnabled(false);
/*  6390 */     this.txtSumHSCrt.setMaximumSize(new Dimension(40, 20));
/*  6391 */     this.txtSumHSCrt.setMinimumSize(new Dimension(40, 20));
/*  6392 */     this.txtSumHSCrt.setPreferredSize(new Dimension(40, 20));
/*  6393 */     gridBagConstraints = new GridBagConstraints();
/*  6394 */     gridBagConstraints.gridx = 2;
/*  6395 */     gridBagConstraints.gridy = 5;
/*  6396 */     this.pnlBasicSummary.add(this.txtSumHSCrt, gridBagConstraints);
/*       */     
/*  6398 */     this.lblSumJJ.setText("Jump Jets:");
/*  6399 */     gridBagConstraints = new GridBagConstraints();
/*  6400 */     gridBagConstraints.gridx = 0;
/*  6401 */     gridBagConstraints.gridy = 7;
/*  6402 */     gridBagConstraints.anchor = 13;
/*  6403 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6404 */     this.pnlBasicSummary.add(this.lblSumJJ, gridBagConstraints);
/*       */     
/*  6406 */     this.txtSumJJTon.setHorizontalAlignment(11);
/*  6407 */     this.txtSumJJTon.setText("000.00");
/*  6408 */     this.txtSumJJTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6409 */     this.txtSumJJTon.setEnabled(false);
/*  6410 */     this.txtSumJJTon.setMaximumSize(new Dimension(50, 20));
/*  6411 */     this.txtSumJJTon.setMinimumSize(new Dimension(50, 20));
/*  6412 */     this.txtSumJJTon.setPreferredSize(new Dimension(50, 20));
/*  6413 */     gridBagConstraints = new GridBagConstraints();
/*  6414 */     gridBagConstraints.gridx = 1;
/*  6415 */     gridBagConstraints.gridy = 7;
/*  6416 */     this.pnlBasicSummary.add(this.txtSumJJTon, gridBagConstraints);
/*       */     
/*  6418 */     this.txtSumJJCrt.setHorizontalAlignment(11);
/*  6419 */     this.txtSumJJCrt.setText("00");
/*  6420 */     this.txtSumJJCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  6421 */     this.txtSumJJCrt.setEnabled(false);
/*  6422 */     this.txtSumJJCrt.setMaximumSize(new Dimension(40, 20));
/*  6423 */     this.txtSumJJCrt.setMinimumSize(new Dimension(40, 20));
/*  6424 */     this.txtSumJJCrt.setPreferredSize(new Dimension(40, 20));
/*  6425 */     gridBagConstraints = new GridBagConstraints();
/*  6426 */     gridBagConstraints.gridx = 2;
/*  6427 */     gridBagConstraints.gridy = 7;
/*  6428 */     this.pnlBasicSummary.add(this.txtSumJJCrt, gridBagConstraints);
/*       */     
/*  6430 */     this.txtSumIntACode.setHorizontalAlignment(0);
/*  6431 */     this.txtSumIntACode.setText("A/C-E-D");
/*  6432 */     this.txtSumIntACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6433 */     this.txtSumIntACode.setEnabled(false);
/*  6434 */     this.txtSumIntACode.setMaximumSize(new Dimension(65, 20));
/*  6435 */     this.txtSumIntACode.setMinimumSize(new Dimension(65, 20));
/*  6436 */     this.txtSumIntACode.setPreferredSize(new Dimension(65, 20));
/*  6437 */     gridBagConstraints = new GridBagConstraints();
/*  6438 */     gridBagConstraints.gridx = 3;
/*  6439 */     gridBagConstraints.gridy = 1;
/*  6440 */     gridBagConstraints.anchor = 17;
/*  6441 */     this.pnlBasicSummary.add(this.txtSumIntACode, gridBagConstraints);
/*       */     
/*  6443 */     this.txtSumEngACode.setHorizontalAlignment(0);
/*  6444 */     this.txtSumEngACode.setText("C-E-D");
/*  6445 */     this.txtSumEngACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6446 */     this.txtSumEngACode.setEnabled(false);
/*  6447 */     this.txtSumEngACode.setMaximumSize(new Dimension(65, 20));
/*  6448 */     this.txtSumEngACode.setMinimumSize(new Dimension(65, 20));
/*  6449 */     this.txtSumEngACode.setPreferredSize(new Dimension(65, 20));
/*  6450 */     gridBagConstraints = new GridBagConstraints();
/*  6451 */     gridBagConstraints.gridx = 3;
/*  6452 */     gridBagConstraints.gridy = 2;
/*  6453 */     gridBagConstraints.anchor = 17;
/*  6454 */     this.pnlBasicSummary.add(this.txtSumEngACode, gridBagConstraints);
/*       */     
/*  6456 */     this.txtSumGyrACode.setHorizontalAlignment(0);
/*  6457 */     this.txtSumGyrACode.setText("C-E-D");
/*  6458 */     this.txtSumGyrACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6459 */     this.txtSumGyrACode.setEnabled(false);
/*  6460 */     this.txtSumGyrACode.setMaximumSize(new Dimension(65, 20));
/*  6461 */     this.txtSumGyrACode.setMinimumSize(new Dimension(65, 20));
/*  6462 */     this.txtSumGyrACode.setPreferredSize(new Dimension(65, 20));
/*  6463 */     gridBagConstraints = new GridBagConstraints();
/*  6464 */     gridBagConstraints.gridx = 3;
/*  6465 */     gridBagConstraints.gridy = 3;
/*  6466 */     gridBagConstraints.anchor = 17;
/*  6467 */     this.pnlBasicSummary.add(this.txtSumGyrACode, gridBagConstraints);
/*       */     
/*  6469 */     this.txtSumCocACode.setHorizontalAlignment(0);
/*  6470 */     this.txtSumCocACode.setText("C-E-D");
/*  6471 */     this.txtSumCocACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6472 */     this.txtSumCocACode.setEnabled(false);
/*  6473 */     this.txtSumCocACode.setMaximumSize(new Dimension(65, 20));
/*  6474 */     this.txtSumCocACode.setMinimumSize(new Dimension(65, 20));
/*  6475 */     this.txtSumCocACode.setPreferredSize(new Dimension(65, 20));
/*  6476 */     gridBagConstraints = new GridBagConstraints();
/*  6477 */     gridBagConstraints.gridx = 3;
/*  6478 */     gridBagConstraints.gridy = 4;
/*  6479 */     gridBagConstraints.anchor = 17;
/*  6480 */     this.pnlBasicSummary.add(this.txtSumCocACode, gridBagConstraints);
/*       */     
/*  6482 */     this.txtSumHSACode.setHorizontalAlignment(0);
/*  6483 */     this.txtSumHSACode.setText("C-E-D");
/*  6484 */     this.txtSumHSACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6485 */     this.txtSumHSACode.setEnabled(false);
/*  6486 */     this.txtSumHSACode.setMaximumSize(new Dimension(65, 20));
/*  6487 */     this.txtSumHSACode.setMinimumSize(new Dimension(65, 20));
/*  6488 */     this.txtSumHSACode.setPreferredSize(new Dimension(65, 20));
/*  6489 */     gridBagConstraints = new GridBagConstraints();
/*  6490 */     gridBagConstraints.gridx = 3;
/*  6491 */     gridBagConstraints.gridy = 5;
/*  6492 */     gridBagConstraints.anchor = 17;
/*  6493 */     this.pnlBasicSummary.add(this.txtSumHSACode, gridBagConstraints);
/*       */     
/*  6495 */     this.txtSumEnhACode.setHorizontalAlignment(0);
/*  6496 */     this.txtSumEnhACode.setText("C-E-D");
/*  6497 */     this.txtSumEnhACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6498 */     this.txtSumEnhACode.setEnabled(false);
/*  6499 */     this.txtSumEnhACode.setMaximumSize(new Dimension(65, 20));
/*  6500 */     this.txtSumEnhACode.setMinimumSize(new Dimension(65, 20));
/*  6501 */     this.txtSumEnhACode.setPreferredSize(new Dimension(65, 20));
/*  6502 */     gridBagConstraints = new GridBagConstraints();
/*  6503 */     gridBagConstraints.gridx = 3;
/*  6504 */     gridBagConstraints.gridy = 6;
/*  6505 */     gridBagConstraints.anchor = 17;
/*  6506 */     this.pnlBasicSummary.add(this.txtSumEnhACode, gridBagConstraints);
/*       */     
/*  6508 */     this.txtSumJJACode.setHorizontalAlignment(0);
/*  6509 */     this.txtSumJJACode.setText("C-E-D");
/*  6510 */     this.txtSumJJACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6511 */     this.txtSumJJACode.setEnabled(false);
/*  6512 */     this.txtSumJJACode.setMaximumSize(new Dimension(65, 20));
/*  6513 */     this.txtSumJJACode.setMinimumSize(new Dimension(65, 20));
/*  6514 */     this.txtSumJJACode.setPreferredSize(new Dimension(65, 20));
/*  6515 */     gridBagConstraints = new GridBagConstraints();
/*  6516 */     gridBagConstraints.gridx = 3;
/*  6517 */     gridBagConstraints.gridy = 7;
/*  6518 */     gridBagConstraints.anchor = 17;
/*  6519 */     this.pnlBasicSummary.add(this.txtSumJJACode, gridBagConstraints);
/*       */     
/*  6521 */     this.lblSumHeadAvailable.setText("Availability");
/*  6522 */     this.pnlBasicSummary.add(this.lblSumHeadAvailable, new GridBagConstraints());
/*       */     
/*  6524 */     this.txtSumPAmpsTon.setHorizontalAlignment(11);
/*  6525 */     this.txtSumPAmpsTon.setText("000.00");
/*  6526 */     this.txtSumPAmpsTon.setDisabledTextColor(new Color(0, 0, 0));
/*  6527 */     this.txtSumPAmpsTon.setEnabled(false);
/*  6528 */     this.txtSumPAmpsTon.setMaximumSize(new Dimension(50, 20));
/*  6529 */     this.txtSumPAmpsTon.setMinimumSize(new Dimension(50, 20));
/*  6530 */     this.txtSumPAmpsTon.setPreferredSize(new Dimension(50, 20));
/*  6531 */     gridBagConstraints = new GridBagConstraints();
/*  6532 */     gridBagConstraints.gridx = 1;
/*  6533 */     gridBagConstraints.gridy = 8;
/*  6534 */     this.pnlBasicSummary.add(this.txtSumPAmpsTon, gridBagConstraints);
/*       */     
/*  6536 */     this.txtSumPAmpsACode.setHorizontalAlignment(0);
/*  6537 */     this.txtSumPAmpsACode.setText("C-E-D");
/*  6538 */     this.txtSumPAmpsACode.setDisabledTextColor(new Color(0, 0, 0));
/*  6539 */     this.txtSumPAmpsACode.setEnabled(false);
/*  6540 */     this.txtSumPAmpsACode.setMaximumSize(new Dimension(65, 20));
/*  6541 */     this.txtSumPAmpsACode.setMinimumSize(new Dimension(65, 20));
/*  6542 */     this.txtSumPAmpsACode.setPreferredSize(new Dimension(65, 20));
/*  6543 */     gridBagConstraints = new GridBagConstraints();
/*  6544 */     gridBagConstraints.gridx = 3;
/*  6545 */     gridBagConstraints.gridy = 8;
/*  6546 */     gridBagConstraints.anchor = 17;
/*  6547 */     this.pnlBasicSummary.add(this.txtSumPAmpsACode, gridBagConstraints);
/*       */     
/*  6549 */     this.lblSumPAmps.setText("Power Amplifiers:");
/*  6550 */     gridBagConstraints = new GridBagConstraints();
/*  6551 */     gridBagConstraints.gridx = 0;
/*  6552 */     gridBagConstraints.gridy = 8;
/*  6553 */     gridBagConstraints.anchor = 13;
/*  6554 */     gridBagConstraints.insets = new Insets(0, 2, 0, 2);
/*  6555 */     this.pnlBasicSummary.add(this.lblSumPAmps, gridBagConstraints);
/*       */     
/*  6557 */     gridBagConstraints = new GridBagConstraints();
/*  6558 */     gridBagConstraints.gridx = 1;
/*  6559 */     gridBagConstraints.gridy = 3;
/*  6560 */     gridBagConstraints.fill = 1;
/*  6561 */     this.pnlBasicSetup.add(this.pnlBasicSummary, gridBagConstraints);
/*       */     
/*  6563 */     this.jPanel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Experimental Equipment"));
/*  6564 */     this.jPanel4.setLayout(new GridBagLayout());
/*       */     
/*  6566 */     this.chkCLPS.setText("Chameleon LPS");
/*  6567 */     this.chkCLPS.setEnabled(false);
/*  6568 */     this.chkCLPS.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6570 */         frmMain.this.chkCLPSActionPerformed(evt);
/*       */       }
/*  6572 */     });
/*  6573 */     gridBagConstraints = new GridBagConstraints();
/*  6574 */     gridBagConstraints.gridx = 0;
/*  6575 */     gridBagConstraints.gridy = 1;
/*  6576 */     gridBagConstraints.gridwidth = 3;
/*  6577 */     gridBagConstraints.anchor = 17;
/*  6578 */     this.jPanel4.add(this.chkCLPS, gridBagConstraints);
/*       */     
/*  6580 */     this.chkNullSig.setText("Null Signature System");
/*  6581 */     this.chkNullSig.setEnabled(false);
/*  6582 */     this.chkNullSig.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6584 */         frmMain.this.chkNullSigActionPerformed(evt);
/*       */       }
/*  6586 */     });
/*  6587 */     gridBagConstraints = new GridBagConstraints();
/*  6588 */     gridBagConstraints.gridx = 0;
/*  6589 */     gridBagConstraints.gridy = 2;
/*  6590 */     gridBagConstraints.gridwidth = 3;
/*  6591 */     gridBagConstraints.anchor = 17;
/*  6592 */     this.jPanel4.add(this.chkNullSig, gridBagConstraints);
/*       */     
/*  6594 */     this.chkBSPFD.setText("Blue Shield PFD");
/*  6595 */     this.chkBSPFD.setEnabled(false);
/*  6596 */     this.chkBSPFD.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6598 */         frmMain.this.chkBSPFDActionPerformed(evt);
/*       */       }
/*  6600 */     });
/*  6601 */     gridBagConstraints = new GridBagConstraints();
/*  6602 */     gridBagConstraints.gridx = 0;
/*  6603 */     gridBagConstraints.gridy = 3;
/*  6604 */     gridBagConstraints.gridwidth = 3;
/*  6605 */     gridBagConstraints.anchor = 17;
/*  6606 */     this.jPanel4.add(this.chkBSPFD, gridBagConstraints);
/*       */     
/*  6608 */     this.chkVoidSig.setText("Void Signature System");
/*  6609 */     this.chkVoidSig.setEnabled(false);
/*  6610 */     this.chkVoidSig.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6612 */         frmMain.this.chkVoidSigActionPerformed(evt);
/*       */       }
/*  6614 */     });
/*  6615 */     gridBagConstraints = new GridBagConstraints();
/*  6616 */     gridBagConstraints.gridx = 0;
/*  6617 */     gridBagConstraints.gridy = 4;
/*  6618 */     gridBagConstraints.gridwidth = 3;
/*  6619 */     gridBagConstraints.anchor = 17;
/*  6620 */     this.jPanel4.add(this.chkVoidSig, gridBagConstraints);
/*       */     
/*  6622 */     this.chkSupercharger.setText("Supercharger");
/*  6623 */     this.chkSupercharger.setEnabled(false);
/*  6624 */     this.chkSupercharger.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6626 */         frmMain.this.chkSuperchargerActionPerformed(evt);
/*       */       }
/*  6628 */     });
/*  6629 */     gridBagConstraints = new GridBagConstraints();
/*  6630 */     gridBagConstraints.gridx = 0;
/*  6631 */     gridBagConstraints.gridy = 5;
/*  6632 */     gridBagConstraints.gridwidth = 3;
/*  6633 */     gridBagConstraints.anchor = 17;
/*  6634 */     this.jPanel4.add(this.chkSupercharger, gridBagConstraints);
/*       */     
/*  6636 */     this.cmbSCLoc.setModel(new DefaultComboBoxModel(new String[] { "CT", "LT", "RT" }));
/*  6637 */     this.cmbSCLoc.setEnabled(false);
/*  6638 */     this.cmbSCLoc.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6640 */         frmMain.this.cmbSCLocActionPerformed(evt);
/*       */       }
/*  6642 */     });
/*  6643 */     gridBagConstraints = new GridBagConstraints();
/*  6644 */     gridBagConstraints.gridx = 2;
/*  6645 */     gridBagConstraints.gridy = 6;
/*  6646 */     gridBagConstraints.anchor = 17;
/*  6647 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  6648 */     this.jPanel4.add(this.cmbSCLoc, gridBagConstraints);
/*       */     
/*  6650 */     this.chkBoobyTrap.setText("Booby Trap");
/*  6651 */     this.chkBoobyTrap.setEnabled(false);
/*  6652 */     this.chkBoobyTrap.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6654 */         frmMain.this.chkBoobyTrapActionPerformed(evt);
/*       */       }
/*  6656 */     });
/*  6657 */     gridBagConstraints = new GridBagConstraints();
/*  6658 */     gridBagConstraints.gridx = 0;
/*  6659 */     gridBagConstraints.gridy = 7;
/*  6660 */     gridBagConstraints.gridwidth = 3;
/*  6661 */     gridBagConstraints.anchor = 17;
/*  6662 */     this.jPanel4.add(this.chkBoobyTrap, gridBagConstraints);
/*       */     
/*  6664 */     this.chkPartialWing.setText("Partial Wing");
/*  6665 */     this.chkPartialWing.setEnabled(false);
/*  6666 */     this.chkPartialWing.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6668 */         frmMain.this.chkPartialWingActionPerformed(evt);
/*       */       }
/*  6670 */     });
/*  6671 */     gridBagConstraints = new GridBagConstraints();
/*  6672 */     gridBagConstraints.gridx = 0;
/*  6673 */     gridBagConstraints.gridy = 8;
/*  6674 */     gridBagConstraints.gridwidth = 3;
/*  6675 */     gridBagConstraints.anchor = 17;
/*  6676 */     this.jPanel4.add(this.chkPartialWing, gridBagConstraints);
/*       */     
/*  6678 */     this.chkFHES.setText("Full-Head Ejection System");
/*  6679 */     this.chkFHES.setEnabled(false);
/*  6680 */     this.chkFHES.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6682 */         frmMain.this.chkFHESActionPerformed(evt);
/*       */       }
/*  6684 */     });
/*  6685 */     gridBagConstraints = new GridBagConstraints();
/*  6686 */     gridBagConstraints.gridx = 0;
/*  6687 */     gridBagConstraints.gridy = 0;
/*  6688 */     gridBagConstraints.gridwidth = 3;
/*  6689 */     gridBagConstraints.anchor = 17;
/*  6690 */     this.jPanel4.add(this.chkFHES, gridBagConstraints);
/*       */     
/*  6692 */     this.lblSupercharger.setText("Install in:");
/*  6693 */     this.lblSupercharger.setEnabled(false);
/*  6694 */     gridBagConstraints = new GridBagConstraints();
/*  6695 */     gridBagConstraints.gridx = 1;
/*  6696 */     gridBagConstraints.gridy = 6;
/*  6697 */     gridBagConstraints.fill = 2;
/*  6698 */     gridBagConstraints.anchor = 13;
/*  6699 */     this.jPanel4.add(this.lblSupercharger, gridBagConstraints);
/*       */     
/*  6701 */     this.jLabel57.setText("        ");
/*  6702 */     gridBagConstraints = new GridBagConstraints();
/*  6703 */     gridBagConstraints.gridx = 0;
/*  6704 */     gridBagConstraints.gridy = 6;
/*  6705 */     this.jPanel4.add(this.jLabel57, gridBagConstraints);
/*       */     
/*  6707 */     gridBagConstraints = new GridBagConstraints();
/*  6708 */     gridBagConstraints.gridx = 2;
/*  6709 */     gridBagConstraints.gridy = 3;
/*  6710 */     gridBagConstraints.gridheight = 2;
/*  6711 */     gridBagConstraints.fill = 1;
/*  6712 */     this.pnlBasicSetup.add(this.jPanel4, gridBagConstraints);
/*       */     
/*  6714 */     this.jPanel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Industrial Equipment"));
/*  6715 */     this.jPanel6.setLayout(new BoxLayout(this.jPanel6, 3));
/*       */     
/*  6717 */     this.chkEjectionSeat.setText("Ejection Seat");
/*  6718 */     this.chkEjectionSeat.setEnabled(false);
/*  6719 */     this.chkEjectionSeat.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6721 */         frmMain.this.chkEjectionSeatActionPerformed(evt);
/*       */       }
/*  6723 */     });
/*  6724 */     this.jPanel6.add(this.chkEjectionSeat);
/*       */     
/*  6726 */     this.chkEnviroSealing.setText("Environmental Sealing");
/*  6727 */     this.chkEnviroSealing.setEnabled(false);
/*  6728 */     this.chkEnviroSealing.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6730 */         frmMain.this.chkEnviroSealingActionPerformed(evt);
/*       */       }
/*  6732 */     });
/*  6733 */     this.jPanel6.add(this.chkEnviroSealing);
/*       */     
/*  6735 */     this.chkTracks.setText("Tracks");
/*  6736 */     this.chkTracks.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6738 */         frmMain.this.chkTracksActionPerformed(evt);
/*       */       }
/*  6740 */     });
/*  6741 */     this.jPanel6.add(this.chkTracks);
/*       */     
/*  6743 */     gridBagConstraints = new GridBagConstraints();
/*  6744 */     gridBagConstraints.gridx = 2;
/*  6745 */     gridBagConstraints.gridy = 1;
/*  6746 */     gridBagConstraints.gridheight = 2;
/*  6747 */     gridBagConstraints.fill = 2;
/*  6748 */     gridBagConstraints.anchor = 17;
/*  6749 */     this.pnlBasicSetup.add(this.jPanel6, gridBagConstraints);
/*       */     
/*  6751 */     this.jPanel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Construction Options"));
/*  6752 */     this.jPanel8.setLayout(new GridBagLayout());
/*       */     
/*  6754 */     this.chkFractional.setText("Use Fractional Accounting");
/*  6755 */     this.chkFractional.setEnabled(false);
/*  6756 */     this.chkFractional.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  6758 */         frmMain.this.chkFractionalActionPerformed(evt);
/*       */       }
/*  6760 */     });
/*  6761 */     gridBagConstraints = new GridBagConstraints();
/*  6762 */     gridBagConstraints.gridx = 0;
/*  6763 */     gridBagConstraints.gridy = 0;
/*  6764 */     gridBagConstraints.anchor = 17;
/*  6765 */     this.jPanel8.add(this.chkFractional, gridBagConstraints);
/*       */     
/*  6767 */     gridBagConstraints = new GridBagConstraints();
/*  6768 */     gridBagConstraints.gridx = 1;
/*  6769 */     gridBagConstraints.gridy = 4;
/*  6770 */     gridBagConstraints.fill = 2;
/*  6771 */     this.pnlBasicSetup.add(this.jPanel8, gridBagConstraints);
/*       */     
/*  6773 */     this.tbpMainTabPane.addTab("Basic Setup", this.pnlBasicSetup);
/*       */     
/*  6775 */     this.pnlArmor.setLayout(new GridBagLayout());
/*       */     
/*  6777 */     this.pnlFrontArmor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Forward Armor"));
/*  6778 */     this.pnlFrontArmor.setLayout(new GridBagLayout());
/*       */     
/*  6780 */     this.pnlCLArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "CL", 2, 0));
/*  6781 */     this.pnlCLArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6783 */     this.lblRLHeader.setText("Internal");
/*  6784 */     this.pnlCLArmorBox.add(this.lblRLHeader, new GridBagConstraints());
/*       */     
/*  6786 */     this.lblCLIntPts.setHorizontalAlignment(0);
/*  6787 */     this.lblCLIntPts.setText("00");
/*  6788 */     this.lblCLIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6789 */     this.lblCLIntPts.setMaximumSize(new Dimension(45, 20));
/*  6790 */     this.lblCLIntPts.setMinimumSize(new Dimension(45, 20));
/*  6791 */     this.lblCLIntPts.setPreferredSize(new Dimension(45, 20));
/*  6792 */     gridBagConstraints = new GridBagConstraints();
/*  6793 */     gridBagConstraints.gridx = 0;
/*  6794 */     gridBagConstraints.gridy = 1;
/*  6795 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6796 */     this.pnlCLArmorBox.add(this.lblCLIntPts, gridBagConstraints);
/*       */     
/*  6798 */     this.lblRLArmorHeader.setText("Armor");
/*  6799 */     gridBagConstraints = new GridBagConstraints();
/*  6800 */     gridBagConstraints.gridx = 0;
/*  6801 */     gridBagConstraints.gridy = 2;
/*  6802 */     this.pnlCLArmorBox.add(this.lblRLArmorHeader, gridBagConstraints);
/*       */     
/*  6804 */     this.spnCLArmor.setMaximumSize(new Dimension(45, 20));
/*  6805 */     this.spnCLArmor.setMinimumSize(new Dimension(45, 20));
/*  6806 */     this.spnCLArmor.setPreferredSize(new Dimension(45, 20));
/*  6807 */     this.spnCLArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6809 */         frmMain.this.spnCLArmorStateChanged(evt);
/*       */       }
/*  6811 */     });
/*  6812 */     gridBagConstraints = new GridBagConstraints();
/*  6813 */     gridBagConstraints.gridx = 0;
/*  6814 */     gridBagConstraints.gridy = 3;
/*  6815 */     this.pnlCLArmorBox.add(this.spnCLArmor, gridBagConstraints);
/*       */     
/*  6817 */     gridBagConstraints = new GridBagConstraints();
/*  6818 */     gridBagConstraints.gridx = 2;
/*  6819 */     gridBagConstraints.gridy = 2;
/*  6820 */     this.pnlFrontArmor.add(this.pnlCLArmorBox, gridBagConstraints);
/*       */     
/*  6822 */     this.pnlLLArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LL", 2, 0));
/*  6823 */     this.pnlLLArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6825 */     this.lblLLHeader.setText("Internal");
/*  6826 */     this.pnlLLArmorBox.add(this.lblLLHeader, new GridBagConstraints());
/*       */     
/*  6828 */     this.lblLLIntPts.setHorizontalAlignment(0);
/*  6829 */     this.lblLLIntPts.setText("00");
/*  6830 */     this.lblLLIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6831 */     this.lblLLIntPts.setMaximumSize(new Dimension(45, 20));
/*  6832 */     this.lblLLIntPts.setMinimumSize(new Dimension(45, 20));
/*  6833 */     this.lblLLIntPts.setPreferredSize(new Dimension(45, 20));
/*  6834 */     gridBagConstraints = new GridBagConstraints();
/*  6835 */     gridBagConstraints.gridx = 0;
/*  6836 */     gridBagConstraints.gridy = 1;
/*  6837 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6838 */     this.pnlLLArmorBox.add(this.lblLLIntPts, gridBagConstraints);
/*       */     
/*  6840 */     this.lblLLArmorHeader.setText("Armor");
/*  6841 */     gridBagConstraints = new GridBagConstraints();
/*  6842 */     gridBagConstraints.gridx = 0;
/*  6843 */     gridBagConstraints.gridy = 2;
/*  6844 */     this.pnlLLArmorBox.add(this.lblLLArmorHeader, gridBagConstraints);
/*       */     
/*  6846 */     this.spnLLArmor.setMaximumSize(new Dimension(45, 20));
/*  6847 */     this.spnLLArmor.setMinimumSize(new Dimension(45, 20));
/*  6848 */     this.spnLLArmor.setPreferredSize(new Dimension(45, 20));
/*  6849 */     this.spnLLArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6851 */         frmMain.this.spnLLArmorStateChanged(evt);
/*       */       }
/*  6853 */     });
/*  6854 */     gridBagConstraints = new GridBagConstraints();
/*  6855 */     gridBagConstraints.gridx = 0;
/*  6856 */     gridBagConstraints.gridy = 3;
/*  6857 */     this.pnlLLArmorBox.add(this.spnLLArmor, gridBagConstraints);
/*       */     
/*  6859 */     gridBagConstraints = new GridBagConstraints();
/*  6860 */     gridBagConstraints.gridx = 1;
/*  6861 */     gridBagConstraints.gridy = 2;
/*  6862 */     this.pnlFrontArmor.add(this.pnlLLArmorBox, gridBagConstraints);
/*       */     
/*  6864 */     this.pnlRAArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RA", 2, 0));
/*  6865 */     this.pnlRAArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6867 */     this.lblRAHeader.setText("Internal");
/*  6868 */     this.pnlRAArmorBox.add(this.lblRAHeader, new GridBagConstraints());
/*       */     
/*  6870 */     this.lblRAIntPts.setHorizontalAlignment(0);
/*  6871 */     this.lblRAIntPts.setText("00");
/*  6872 */     this.lblRAIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6873 */     this.lblRAIntPts.setMaximumSize(new Dimension(45, 20));
/*  6874 */     this.lblRAIntPts.setMinimumSize(new Dimension(45, 20));
/*  6875 */     this.lblRAIntPts.setPreferredSize(new Dimension(45, 20));
/*  6876 */     gridBagConstraints = new GridBagConstraints();
/*  6877 */     gridBagConstraints.gridx = 0;
/*  6878 */     gridBagConstraints.gridy = 1;
/*  6879 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6880 */     this.pnlRAArmorBox.add(this.lblRAIntPts, gridBagConstraints);
/*       */     
/*  6882 */     this.lblRAArmorHeader.setText("Armor");
/*  6883 */     gridBagConstraints = new GridBagConstraints();
/*  6884 */     gridBagConstraints.gridx = 0;
/*  6885 */     gridBagConstraints.gridy = 2;
/*  6886 */     this.pnlRAArmorBox.add(this.lblRAArmorHeader, gridBagConstraints);
/*       */     
/*  6888 */     this.spnRAArmor.setModel(new SpinnerNumberModel(0, 0, 100, 1));
/*  6889 */     this.spnRAArmor.setMaximumSize(new Dimension(45, 20));
/*  6890 */     this.spnRAArmor.setMinimumSize(new Dimension(45, 20));
/*  6891 */     this.spnRAArmor.setPreferredSize(new Dimension(45, 20));
/*  6892 */     this.spnRAArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6894 */         frmMain.this.spnRAArmorStateChanged(evt);
/*       */       }
/*  6896 */     });
/*  6897 */     gridBagConstraints = new GridBagConstraints();
/*  6898 */     gridBagConstraints.gridx = 0;
/*  6899 */     gridBagConstraints.gridy = 3;
/*  6900 */     this.pnlRAArmorBox.add(this.spnRAArmor, gridBagConstraints);
/*       */     
/*  6902 */     gridBagConstraints = new GridBagConstraints();
/*  6903 */     gridBagConstraints.gridx = 4;
/*  6904 */     gridBagConstraints.gridy = 1;
/*  6905 */     this.pnlFrontArmor.add(this.pnlRAArmorBox, gridBagConstraints);
/*       */     
/*  6907 */     this.pnlHDArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Head", 2, 0));
/*  6908 */     this.pnlHDArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6910 */     this.lblHDHeader.setText("Internal");
/*  6911 */     this.pnlHDArmorBox.add(this.lblHDHeader, new GridBagConstraints());
/*       */     
/*  6913 */     this.lblHDIntPts.setHorizontalAlignment(0);
/*  6914 */     this.lblHDIntPts.setText("00");
/*  6915 */     this.lblHDIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6916 */     this.lblHDIntPts.setMaximumSize(new Dimension(45, 20));
/*  6917 */     this.lblHDIntPts.setMinimumSize(new Dimension(45, 20));
/*  6918 */     this.lblHDIntPts.setPreferredSize(new Dimension(45, 20));
/*  6919 */     gridBagConstraints = new GridBagConstraints();
/*  6920 */     gridBagConstraints.gridx = 0;
/*  6921 */     gridBagConstraints.gridy = 1;
/*  6922 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6923 */     this.pnlHDArmorBox.add(this.lblHDIntPts, gridBagConstraints);
/*       */     
/*  6925 */     this.lblHDArmorHeader.setText("Armor");
/*  6926 */     gridBagConstraints = new GridBagConstraints();
/*  6927 */     gridBagConstraints.gridx = 0;
/*  6928 */     gridBagConstraints.gridy = 2;
/*  6929 */     this.pnlHDArmorBox.add(this.lblHDArmorHeader, gridBagConstraints);
/*       */     
/*  6931 */     this.spnHDArmor.setModel(new SpinnerNumberModel(0, 0, 9, 1));
/*  6932 */     this.spnHDArmor.setMaximumSize(new Dimension(45, 20));
/*  6933 */     this.spnHDArmor.setMinimumSize(new Dimension(45, 20));
/*  6934 */     this.spnHDArmor.setPreferredSize(new Dimension(45, 20));
/*  6935 */     this.spnHDArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6937 */         frmMain.this.spnHDArmorStateChanged(evt);
/*       */       }
/*  6939 */     });
/*  6940 */     gridBagConstraints = new GridBagConstraints();
/*  6941 */     gridBagConstraints.gridx = 0;
/*  6942 */     gridBagConstraints.gridy = 3;
/*  6943 */     this.pnlHDArmorBox.add(this.spnHDArmor, gridBagConstraints);
/*       */     
/*  6945 */     gridBagConstraints = new GridBagConstraints();
/*  6946 */     gridBagConstraints.gridx = 2;
/*  6947 */     gridBagConstraints.gridy = 0;
/*  6948 */     this.pnlFrontArmor.add(this.pnlHDArmorBox, gridBagConstraints);
/*       */     
/*  6950 */     this.pnlCTArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "CT", 2, 0));
/*  6951 */     this.pnlCTArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6953 */     this.lblCTHeader.setText("Internal");
/*  6954 */     this.pnlCTArmorBox.add(this.lblCTHeader, new GridBagConstraints());
/*       */     
/*  6956 */     this.lblCTIntPts.setHorizontalAlignment(0);
/*  6957 */     this.lblCTIntPts.setText("00");
/*  6958 */     this.lblCTIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  6959 */     this.lblCTIntPts.setMaximumSize(new Dimension(45, 20));
/*  6960 */     this.lblCTIntPts.setMinimumSize(new Dimension(45, 20));
/*  6961 */     this.lblCTIntPts.setPreferredSize(new Dimension(45, 20));
/*  6962 */     gridBagConstraints = new GridBagConstraints();
/*  6963 */     gridBagConstraints.gridx = 0;
/*  6964 */     gridBagConstraints.gridy = 1;
/*  6965 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  6966 */     this.pnlCTArmorBox.add(this.lblCTIntPts, gridBagConstraints);
/*       */     
/*  6968 */     this.lblCTArmorHeader.setText("Armor");
/*  6969 */     gridBagConstraints = new GridBagConstraints();
/*  6970 */     gridBagConstraints.gridx = 0;
/*  6971 */     gridBagConstraints.gridy = 2;
/*  6972 */     this.pnlCTArmorBox.add(this.lblCTArmorHeader, gridBagConstraints);
/*       */     
/*  6974 */     this.spnCTArmor.setMaximumSize(new Dimension(45, 20));
/*  6975 */     this.spnCTArmor.setMinimumSize(new Dimension(45, 20));
/*  6976 */     this.spnCTArmor.setPreferredSize(new Dimension(45, 20));
/*  6977 */     this.spnCTArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  6979 */         frmMain.this.spnCTArmorStateChanged(evt);
/*       */       }
/*  6981 */     });
/*  6982 */     gridBagConstraints = new GridBagConstraints();
/*  6983 */     gridBagConstraints.gridx = 0;
/*  6984 */     gridBagConstraints.gridy = 3;
/*  6985 */     this.pnlCTArmorBox.add(this.spnCTArmor, gridBagConstraints);
/*       */     
/*  6987 */     gridBagConstraints = new GridBagConstraints();
/*  6988 */     gridBagConstraints.gridx = 2;
/*  6989 */     gridBagConstraints.gridy = 1;
/*  6990 */     gridBagConstraints.gridheight = 2;
/*  6991 */     gridBagConstraints.anchor = 11;
/*  6992 */     this.pnlFrontArmor.add(this.pnlCTArmorBox, gridBagConstraints);
/*       */     
/*  6994 */     this.pnlLTArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LT", 2, 0));
/*  6995 */     this.pnlLTArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  6997 */     this.lblLTHeader.setText("Internal");
/*  6998 */     this.pnlLTArmorBox.add(this.lblLTHeader, new GridBagConstraints());
/*       */     
/*  7000 */     this.lblLTIntPts.setHorizontalAlignment(0);
/*  7001 */     this.lblLTIntPts.setText("00");
/*  7002 */     this.lblLTIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  7003 */     this.lblLTIntPts.setMaximumSize(new Dimension(45, 20));
/*  7004 */     this.lblLTIntPts.setMinimumSize(new Dimension(45, 20));
/*  7005 */     this.lblLTIntPts.setPreferredSize(new Dimension(45, 20));
/*  7006 */     gridBagConstraints = new GridBagConstraints();
/*  7007 */     gridBagConstraints.gridx = 0;
/*  7008 */     gridBagConstraints.gridy = 1;
/*  7009 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  7010 */     this.pnlLTArmorBox.add(this.lblLTIntPts, gridBagConstraints);
/*       */     
/*  7012 */     this.lblLTArmorHeader.setText("Armor");
/*  7013 */     gridBagConstraints = new GridBagConstraints();
/*  7014 */     gridBagConstraints.gridx = 0;
/*  7015 */     gridBagConstraints.gridy = 2;
/*  7016 */     this.pnlLTArmorBox.add(this.lblLTArmorHeader, gridBagConstraints);
/*       */     
/*  7018 */     this.spnLTArmor.setMaximumSize(new Dimension(45, 20));
/*  7019 */     this.spnLTArmor.setMinimumSize(new Dimension(45, 20));
/*  7020 */     this.spnLTArmor.setPreferredSize(new Dimension(45, 20));
/*  7021 */     this.spnLTArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  7023 */         frmMain.this.spnLTArmorStateChanged(evt);
/*       */       }
/*  7025 */     });
/*  7026 */     gridBagConstraints = new GridBagConstraints();
/*  7027 */     gridBagConstraints.gridx = 0;
/*  7028 */     gridBagConstraints.gridy = 3;
/*  7029 */     this.pnlLTArmorBox.add(this.spnLTArmor, gridBagConstraints);
/*       */     
/*  7031 */     gridBagConstraints = new GridBagConstraints();
/*  7032 */     gridBagConstraints.gridx = 1;
/*  7033 */     gridBagConstraints.gridy = 0;
/*  7034 */     gridBagConstraints.gridheight = 2;
/*  7035 */     this.pnlFrontArmor.add(this.pnlLTArmorBox, gridBagConstraints);
/*       */     
/*  7037 */     this.pnlRTArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RT", 2, 0));
/*  7038 */     this.pnlRTArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  7040 */     this.lblRTHeader.setText("Internal");
/*  7041 */     this.pnlRTArmorBox.add(this.lblRTHeader, new GridBagConstraints());
/*       */     
/*  7043 */     this.lblRTIntPts.setHorizontalAlignment(0);
/*  7044 */     this.lblRTIntPts.setText("00");
/*  7045 */     this.lblRTIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  7046 */     this.lblRTIntPts.setMaximumSize(new Dimension(45, 20));
/*  7047 */     this.lblRTIntPts.setMinimumSize(new Dimension(45, 20));
/*  7048 */     this.lblRTIntPts.setPreferredSize(new Dimension(45, 20));
/*  7049 */     gridBagConstraints = new GridBagConstraints();
/*  7050 */     gridBagConstraints.gridx = 0;
/*  7051 */     gridBagConstraints.gridy = 1;
/*  7052 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  7053 */     this.pnlRTArmorBox.add(this.lblRTIntPts, gridBagConstraints);
/*       */     
/*  7055 */     this.lblRTArmorHeader.setText("Armor");
/*  7056 */     gridBagConstraints = new GridBagConstraints();
/*  7057 */     gridBagConstraints.gridx = 0;
/*  7058 */     gridBagConstraints.gridy = 2;
/*  7059 */     this.pnlRTArmorBox.add(this.lblRTArmorHeader, gridBagConstraints);
/*       */     
/*  7061 */     this.spnRTArmor.setMaximumSize(new Dimension(45, 20));
/*  7062 */     this.spnRTArmor.setMinimumSize(new Dimension(45, 20));
/*  7063 */     this.spnRTArmor.setPreferredSize(new Dimension(45, 20));
/*  7064 */     this.spnRTArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  7066 */         frmMain.this.spnRTArmorStateChanged(evt);
/*       */       }
/*  7068 */     });
/*  7069 */     gridBagConstraints = new GridBagConstraints();
/*  7070 */     gridBagConstraints.gridx = 0;
/*  7071 */     gridBagConstraints.gridy = 3;
/*  7072 */     this.pnlRTArmorBox.add(this.spnRTArmor, gridBagConstraints);
/*       */     
/*  7074 */     gridBagConstraints = new GridBagConstraints();
/*  7075 */     gridBagConstraints.gridx = 3;
/*  7076 */     gridBagConstraints.gridy = 0;
/*  7077 */     gridBagConstraints.gridheight = 2;
/*  7078 */     this.pnlFrontArmor.add(this.pnlRTArmorBox, gridBagConstraints);
/*       */     
/*  7080 */     this.pnlLAArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LA", 2, 0));
/*  7081 */     this.pnlLAArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  7083 */     this.lblLAHeader.setText("Internal");
/*  7084 */     this.pnlLAArmorBox.add(this.lblLAHeader, new GridBagConstraints());
/*       */     
/*  7086 */     this.lblLAIntPts.setHorizontalAlignment(0);
/*  7087 */     this.lblLAIntPts.setText("00");
/*  7088 */     this.lblLAIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  7089 */     this.lblLAIntPts.setMaximumSize(new Dimension(45, 20));
/*  7090 */     this.lblLAIntPts.setMinimumSize(new Dimension(45, 20));
/*  7091 */     this.lblLAIntPts.setPreferredSize(new Dimension(45, 20));
/*  7092 */     gridBagConstraints = new GridBagConstraints();
/*  7093 */     gridBagConstraints.gridx = 0;
/*  7094 */     gridBagConstraints.gridy = 1;
/*  7095 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  7096 */     this.pnlLAArmorBox.add(this.lblLAIntPts, gridBagConstraints);
/*       */     
/*  7098 */     this.lblLAArmorHeader.setText("Armor");
/*  7099 */     gridBagConstraints = new GridBagConstraints();
/*  7100 */     gridBagConstraints.gridx = 0;
/*  7101 */     gridBagConstraints.gridy = 2;
/*  7102 */     this.pnlLAArmorBox.add(this.lblLAArmorHeader, gridBagConstraints);
/*       */     
/*  7104 */     this.spnLAArmor.setMaximumSize(new Dimension(45, 20));
/*  7105 */     this.spnLAArmor.setMinimumSize(new Dimension(45, 20));
/*  7106 */     this.spnLAArmor.setPreferredSize(new Dimension(45, 20));
/*  7107 */     this.spnLAArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  7109 */         frmMain.this.spnLAArmorStateChanged(evt);
/*       */       }
/*  7111 */     });
/*  7112 */     gridBagConstraints = new GridBagConstraints();
/*  7113 */     gridBagConstraints.gridx = 0;
/*  7114 */     gridBagConstraints.gridy = 3;
/*  7115 */     this.pnlLAArmorBox.add(this.spnLAArmor, gridBagConstraints);
/*       */     
/*  7117 */     gridBagConstraints = new GridBagConstraints();
/*  7118 */     gridBagConstraints.gridx = 0;
/*  7119 */     gridBagConstraints.gridy = 1;
/*  7120 */     this.pnlFrontArmor.add(this.pnlLAArmorBox, gridBagConstraints);
/*       */     
/*  7122 */     this.pnlRLArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RL", 2, 0));
/*  7123 */     this.pnlRLArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  7125 */     this.lblRLHeader1.setText("Internal");
/*  7126 */     this.pnlRLArmorBox.add(this.lblRLHeader1, new GridBagConstraints());
/*       */     
/*  7128 */     this.lblRLIntPts.setHorizontalAlignment(0);
/*  7129 */     this.lblRLIntPts.setText("00");
/*  7130 */     this.lblRLIntPts.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  7131 */     this.lblRLIntPts.setMaximumSize(new Dimension(45, 20));
/*  7132 */     this.lblRLIntPts.setMinimumSize(new Dimension(45, 20));
/*  7133 */     this.lblRLIntPts.setPreferredSize(new Dimension(45, 20));
/*  7134 */     gridBagConstraints = new GridBagConstraints();
/*  7135 */     gridBagConstraints.gridx = 0;
/*  7136 */     gridBagConstraints.gridy = 1;
/*  7137 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/*  7138 */     this.pnlRLArmorBox.add(this.lblRLIntPts, gridBagConstraints);
/*       */     
/*  7140 */     this.lblRLArmorHeader1.setText("Armor");
/*  7141 */     gridBagConstraints = new GridBagConstraints();
/*  7142 */     gridBagConstraints.gridx = 0;
/*  7143 */     gridBagConstraints.gridy = 2;
/*  7144 */     this.pnlRLArmorBox.add(this.lblRLArmorHeader1, gridBagConstraints);
/*       */     
/*  7146 */     this.spnRLArmor.setMaximumSize(new Dimension(45, 20));
/*  7147 */     this.spnRLArmor.setMinimumSize(new Dimension(45, 20));
/*  7148 */     this.spnRLArmor.setPreferredSize(new Dimension(45, 20));
/*  7149 */     this.spnRLArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  7151 */         frmMain.this.spnRLArmorStateChanged(evt);
/*       */       }
/*  7153 */     });
/*  7154 */     gridBagConstraints = new GridBagConstraints();
/*  7155 */     gridBagConstraints.gridx = 0;
/*  7156 */     gridBagConstraints.gridy = 3;
/*  7157 */     this.pnlRLArmorBox.add(this.spnRLArmor, gridBagConstraints);
/*       */     
/*  7159 */     gridBagConstraints = new GridBagConstraints();
/*  7160 */     gridBagConstraints.gridx = 3;
/*  7161 */     gridBagConstraints.gridy = 2;
/*  7162 */     this.pnlFrontArmor.add(this.pnlRLArmorBox, gridBagConstraints);
/*       */     
/*  7164 */     gridBagConstraints = new GridBagConstraints();
/*  7165 */     gridBagConstraints.gridheight = 3;
/*  7166 */     this.pnlArmor.add(this.pnlFrontArmor, gridBagConstraints);
/*       */     
/*  7168 */     this.pnlRearArmor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Rear Armor"));
/*  7169 */     this.pnlRearArmor.setLayout(new GridBagLayout());
/*       */     
/*  7171 */     this.pnlRTRArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "RTR", 2, 0));
/*  7172 */     this.pnlRTRArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  7174 */     this.lblRTRArmorHeader.setText("Armor");
/*  7175 */     this.pnlRTRArmorBox.add(this.lblRTRArmorHeader, new GridBagConstraints());
/*       */     
/*  7177 */     this.spnRTRArmor.setMaximumSize(new Dimension(45, 20));
/*  7178 */     this.spnRTRArmor.setMinimumSize(new Dimension(45, 20));
/*  7179 */     this.spnRTRArmor.setPreferredSize(new Dimension(45, 20));
/*  7180 */     this.spnRTRArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  7182 */         frmMain.this.spnRTRArmorStateChanged(evt);
/*       */       }
/*  7184 */     });
/*  7185 */     gridBagConstraints = new GridBagConstraints();
/*  7186 */     gridBagConstraints.gridx = 0;
/*  7187 */     gridBagConstraints.gridy = 1;
/*  7188 */     this.pnlRTRArmorBox.add(this.spnRTRArmor, gridBagConstraints);
/*       */     
/*  7190 */     gridBagConstraints = new GridBagConstraints();
/*  7191 */     gridBagConstraints.gridx = 2;
/*  7192 */     gridBagConstraints.gridy = 0;
/*  7193 */     this.pnlRearArmor.add(this.pnlRTRArmorBox, gridBagConstraints);
/*       */     
/*  7195 */     this.pnlCTRArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "CTR", 2, 0));
/*  7196 */     this.pnlCTRArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  7198 */     this.lblCTRArmorHeader.setText("Armor");
/*  7199 */     this.pnlCTRArmorBox.add(this.lblCTRArmorHeader, new GridBagConstraints());
/*       */     
/*  7201 */     this.spnCTRArmor.setMaximumSize(new Dimension(45, 20));
/*  7202 */     this.spnCTRArmor.setMinimumSize(new Dimension(45, 20));
/*  7203 */     this.spnCTRArmor.setPreferredSize(new Dimension(45, 20));
/*  7204 */     this.spnCTRArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  7206 */         frmMain.this.spnCTRArmorStateChanged(evt);
/*       */       }
/*  7208 */     });
/*  7209 */     gridBagConstraints = new GridBagConstraints();
/*  7210 */     gridBagConstraints.gridx = 0;
/*  7211 */     gridBagConstraints.gridy = 1;
/*  7212 */     this.pnlCTRArmorBox.add(this.spnCTRArmor, gridBagConstraints);
/*       */     
/*  7214 */     gridBagConstraints = new GridBagConstraints();
/*  7215 */     gridBagConstraints.gridx = 1;
/*  7216 */     gridBagConstraints.gridy = 0;
/*  7217 */     this.pnlRearArmor.add(this.pnlCTRArmorBox, gridBagConstraints);
/*       */     
/*  7219 */     this.pnlLTRArmorBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LTR", 2, 0));
/*  7220 */     this.pnlLTRArmorBox.setLayout(new GridBagLayout());
/*       */     
/*  7222 */     this.lblLTRArmorHeader.setText("Armor");
/*  7223 */     this.pnlLTRArmorBox.add(this.lblLTRArmorHeader, new GridBagConstraints());
/*       */     
/*  7225 */     this.spnLTRArmor.setMaximumSize(new Dimension(45, 20));
/*  7226 */     this.spnLTRArmor.setMinimumSize(new Dimension(45, 20));
/*  7227 */     this.spnLTRArmor.setPreferredSize(new Dimension(45, 20));
/*  7228 */     this.spnLTRArmor.addChangeListener(new ChangeListener() {
/*       */       public void stateChanged(ChangeEvent evt) {
/*  7230 */         frmMain.this.spnLTRArmorStateChanged(evt);
/*       */       }
/*  7232 */     });
/*  7233 */     gridBagConstraints = new GridBagConstraints();
/*  7234 */     gridBagConstraints.gridx = 0;
/*  7235 */     gridBagConstraints.gridy = 1;
/*  7236 */     this.pnlLTRArmorBox.add(this.spnLTRArmor, gridBagConstraints);
/*       */     
/*  7238 */     gridBagConstraints = new GridBagConstraints();
/*  7239 */     gridBagConstraints.gridx = 0;
/*  7240 */     gridBagConstraints.gridy = 0;
/*  7241 */     this.pnlRearArmor.add(this.pnlLTRArmorBox, gridBagConstraints);
/*       */     
/*  7243 */     gridBagConstraints = new GridBagConstraints();
/*  7244 */     gridBagConstraints.gridx = 0;
/*  7245 */     gridBagConstraints.gridy = 3;
/*  7246 */     gridBagConstraints.fill = 2;
/*  7247 */     gridBagConstraints.anchor = 11;
/*  7248 */     this.pnlArmor.add(this.pnlRearArmor, gridBagConstraints);
/*       */     
/*  7250 */     this.pnlArmorInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Armor Information"));
/*  7251 */     this.pnlArmorInfo.setLayout(new GridBagLayout());
/*       */     
/*  7253 */     this.lblArmorCoverage.setText("100% Coverage");
/*  7254 */     gridBagConstraints = new GridBagConstraints();
/*  7255 */     gridBagConstraints.gridx = 2;
/*  7256 */     gridBagConstraints.gridy = 1;
/*  7257 */     gridBagConstraints.anchor = 13;
/*  7258 */     gridBagConstraints.insets = new Insets(0, 8, 0, 2);
/*  7259 */     this.pnlArmorInfo.add(this.lblArmorCoverage, gridBagConstraints);
/*       */     
/*  7261 */     this.lblArmorPoints.setText("999 of 999 Armor Points");
/*  7262 */     gridBagConstraints = new GridBagConstraints();
/*  7263 */     gridBagConstraints.gridx = 2;
/*  7264 */     gridBagConstraints.gridy = 0;
/*  7265 */     gridBagConstraints.anchor = 13;
/*  7266 */     gridBagConstraints.insets = new Insets(0, 8, 0, 2);
/*  7267 */     this.pnlArmorInfo.add(this.lblArmorPoints, gridBagConstraints);
/*       */     
/*  7269 */     this.txtSumArmorTon.setHorizontalAlignment(11);
/*  7270 */     this.txtSumArmorTon.setText("000.00");
/*  7271 */     this.txtSumArmorTon.setDisabledTextColor(new Color(0, 0, 0));
/*  7272 */     this.txtSumArmorTon.setEnabled(false);
/*  7273 */     this.txtSumArmorTon.setMaximumSize(new Dimension(50, 20));
/*  7274 */     this.txtSumArmorTon.setMinimumSize(new Dimension(50, 20));
/*  7275 */     this.txtSumArmorTon.setPreferredSize(new Dimension(50, 20));
/*  7276 */     gridBagConstraints = new GridBagConstraints();
/*  7277 */     gridBagConstraints.gridx = 0;
/*  7278 */     gridBagConstraints.gridy = 1;
/*  7279 */     gridBagConstraints.anchor = 17;
/*  7280 */     gridBagConstraints.insets = new Insets(0, 2, 2, 0);
/*  7281 */     this.pnlArmorInfo.add(this.txtSumArmorTon, gridBagConstraints);
/*       */     
/*  7283 */     this.txtSumArmorCrt.setHorizontalAlignment(11);
/*  7284 */     this.txtSumArmorCrt.setText("00");
/*  7285 */     this.txtSumArmorCrt.setDisabledTextColor(new Color(0, 0, 0));
/*  7286 */     this.txtSumArmorCrt.setEnabled(false);
/*  7287 */     this.txtSumArmorCrt.setMaximumSize(new Dimension(40, 20));
/*  7288 */     this.txtSumArmorCrt.setMinimumSize(new Dimension(40, 20));
/*  7289 */     this.txtSumArmorCrt.setPreferredSize(new Dimension(40, 20));
/*  7290 */     gridBagConstraints = new GridBagConstraints();
/*  7291 */     gridBagConstraints.gridx = 1;
/*  7292 */     gridBagConstraints.gridy = 1;
/*  7293 */     gridBagConstraints.anchor = 17;
/*  7294 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/*  7295 */     this.pnlArmorInfo.add(this.txtSumArmorCrt, gridBagConstraints);
/*       */     
/*  7297 */     this.lblSumHeadTons1.setText("Tonnage");
/*  7298 */     gridBagConstraints = new GridBagConstraints();
/*  7299 */     gridBagConstraints.gridx = 0;
/*  7300 */     gridBagConstraints.gridy = 0;
/*  7301 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/*  7302 */     this.pnlArmorInfo.add(this.lblSumHeadTons1, gridBagConstraints);
/*       */     
/*  7304 */     this.lblSumHeadCrits1.setText("Crits");
/*  7305 */     this.pnlArmorInfo.add(this.lblSumHeadCrits1, new GridBagConstraints());
/*       */     
/*  7307 */     this.lblArmorTonsWasted.setText("0.00 Tons Wasted");
/*  7308 */     gridBagConstraints = new GridBagConstraints();
/*  7309 */     gridBagConstraints.gridx = 0;
/*  7310 */     gridBagConstraints.gridy = 2;
/*  7311 */     gridBagConstraints.gridwidth = 3;
/*  7312 */     gridBagConstraints.anchor = 13;
/*  7313 */     gridBagConstraints.insets = new Insets(2, 0, 0, 2);
/*  7314 */     this.pnlArmorInfo.add(this.lblArmorTonsWasted, gridBagConstraints);
/*       */     
/*  7316 */     this.lblAVInLot.setText("99 Points Left In This 1/2 Ton Lot");
/*  7317 */     gridBagConstraints = new GridBagConstraints();
/*  7318 */     gridBagConstraints.gridx = 0;
/*  7319 */     gridBagConstraints.gridy = 3;
/*  7320 */     gridBagConstraints.gridwidth = 3;
/*  7321 */     gridBagConstraints.anchor = 13;
/*  7322 */     gridBagConstraints.insets = new Insets(6, 0, 2, 2);
/*  7323 */     this.pnlArmorInfo.add(this.lblAVInLot, gridBagConstraints);
/*       */     
/*  7325 */     gridBagConstraints = new GridBagConstraints();
/*  7326 */     gridBagConstraints.gridx = 1;
/*  7327 */     gridBagConstraints.gridy = 1;
/*  7328 */     gridBagConstraints.fill = 2;
/*  7329 */     gridBagConstraints.anchor = 18;
/*  7330 */     this.pnlArmor.add(this.pnlArmorInfo, gridBagConstraints);
/*       */     
/*  7332 */     this.pnlArmorSetup.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Armor Setup"));
/*  7333 */     this.pnlArmorSetup.setLayout(new GridBagLayout());
/*       */     
/*  7335 */     this.btnMaxArmor.setText("Maximize Armor Tonnage");
/*  7336 */     this.btnMaxArmor.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7338 */         frmMain.this.btnMaxArmorActionPerformed(evt);
/*       */       }
/*  7340 */     });
/*  7341 */     gridBagConstraints = new GridBagConstraints();
/*  7342 */     gridBagConstraints.gridx = 0;
/*  7343 */     gridBagConstraints.gridy = 5;
/*  7344 */     gridBagConstraints.gridwidth = 2;
/*  7345 */     gridBagConstraints.fill = 2;
/*  7346 */     gridBagConstraints.insets = new Insets(2, 2, 0, 2);
/*  7347 */     this.pnlArmorSetup.add(this.btnMaxArmor, gridBagConstraints);
/*       */     
/*  7349 */     this.btnArmorTons.setText("Set Armor Tonnage");
/*  7350 */     this.btnArmorTons.setMaximumSize(new Dimension(194, 25));
/*  7351 */     this.btnArmorTons.setMinimumSize(new Dimension(194, 25));
/*  7352 */     this.btnArmorTons.setPreferredSize(new Dimension(194, 25));
/*  7353 */     this.btnArmorTons.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7355 */         frmMain.this.btnArmorTonsActionPerformed(evt);
/*       */       }
/*  7357 */     });
/*  7358 */     gridBagConstraints = new GridBagConstraints();
/*  7359 */     gridBagConstraints.gridx = 0;
/*  7360 */     gridBagConstraints.gridy = 2;
/*  7361 */     gridBagConstraints.gridwidth = 2;
/*  7362 */     gridBagConstraints.fill = 2;
/*  7363 */     gridBagConstraints.insets = new Insets(2, 2, 0, 2);
/*  7364 */     this.pnlArmorSetup.add(this.btnArmorTons, gridBagConstraints);
/*       */     
/*  7366 */     this.cmbArmorType.setModel(new DefaultComboBoxModel(new String[] { "Industrial", "Standard", "Ferro-Fibrous" }));
/*  7367 */     this.cmbArmorType.setSelectedIndex(1);
/*  7368 */     this.cmbArmorType.setMaximumSize(new Dimension(150, 20));
/*  7369 */     this.cmbArmorType.setMinimumSize(new Dimension(150, 20));
/*  7370 */     this.cmbArmorType.setPreferredSize(new Dimension(150, 20));
/*  7371 */     this.cmbArmorType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7373 */         frmMain.this.cmbArmorTypeActionPerformed(evt);
/*       */       }
/*  7375 */     });
/*  7376 */     gridBagConstraints = new GridBagConstraints();
/*  7377 */     gridBagConstraints.gridx = 1;
/*  7378 */     gridBagConstraints.gridy = 0;
/*  7379 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/*  7380 */     this.pnlArmorSetup.add(this.cmbArmorType, gridBagConstraints);
/*       */     
/*  7382 */     this.lblArmorType.setText("Armor Type:");
/*  7383 */     gridBagConstraints = new GridBagConstraints();
/*  7384 */     gridBagConstraints.gridx = 0;
/*  7385 */     gridBagConstraints.gridy = 0;
/*  7386 */     gridBagConstraints.anchor = 13;
/*  7387 */     gridBagConstraints.insets = new Insets(0, 2, 2, 2);
/*  7388 */     this.pnlArmorSetup.add(this.lblArmorType, gridBagConstraints);
/*       */     
/*  7390 */     this.btnBalanceArmor.setSelected(true);
/*  7391 */     this.btnBalanceArmor.setText("Balance Armor by Location");
/*  7392 */     gridBagConstraints = new GridBagConstraints();
/*  7393 */     gridBagConstraints.gridx = 0;
/*  7394 */     gridBagConstraints.gridy = 1;
/*  7395 */     gridBagConstraints.gridwidth = 2;
/*  7396 */     gridBagConstraints.anchor = 13;
/*  7397 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/*  7398 */     this.pnlArmorSetup.add(this.btnBalanceArmor, gridBagConstraints);
/*       */     
/*  7400 */     this.btnEfficientArmor.setText("Use Efficient Maximum");
/*  7401 */     this.btnEfficientArmor.setMaximumSize(new Dimension(194, 25));
/*  7402 */     this.btnEfficientArmor.setMinimumSize(new Dimension(194, 25));
/*  7403 */     this.btnEfficientArmor.setPreferredSize(new Dimension(194, 25));
/*  7404 */     this.btnEfficientArmor.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7406 */         frmMain.this.btnEfficientArmorActionPerformed(evt);
/*       */       }
/*  7408 */     });
/*  7409 */     gridBagConstraints = new GridBagConstraints();
/*  7410 */     gridBagConstraints.gridx = 0;
/*  7411 */     gridBagConstraints.gridy = 4;
/*  7412 */     gridBagConstraints.gridwidth = 2;
/*  7413 */     gridBagConstraints.fill = 2;
/*  7414 */     gridBagConstraints.insets = new Insets(2, 2, 0, 2);
/*  7415 */     this.pnlArmorSetup.add(this.btnEfficientArmor, gridBagConstraints);
/*       */     
/*  7417 */     this.btnRemainingArmor.setText("Use Remaining Tonnage");
/*  7418 */     this.btnRemainingArmor.setMaximumSize(new Dimension(194, 25));
/*  7419 */     this.btnRemainingArmor.setMinimumSize(new Dimension(194, 25));
/*  7420 */     this.btnRemainingArmor.setPreferredSize(new Dimension(194, 25));
/*  7421 */     this.btnRemainingArmor.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7423 */         frmMain.this.btnRemainingArmorActionPerformed(evt);
/*       */       }
/*  7425 */     });
/*  7426 */     gridBagConstraints = new GridBagConstraints();
/*  7427 */     gridBagConstraints.gridx = 0;
/*  7428 */     gridBagConstraints.gridy = 3;
/*  7429 */     gridBagConstraints.gridwidth = 2;
/*  7430 */     gridBagConstraints.fill = 2;
/*  7431 */     gridBagConstraints.insets = new Insets(2, 2, 0, 2);
/*  7432 */     this.pnlArmorSetup.add(this.btnRemainingArmor, gridBagConstraints);
/*       */     
/*  7434 */     gridBagConstraints = new GridBagConstraints();
/*  7435 */     gridBagConstraints.gridx = 1;
/*  7436 */     gridBagConstraints.gridy = 0;
/*  7437 */     gridBagConstraints.fill = 2;
/*  7438 */     gridBagConstraints.anchor = 18;
/*  7439 */     this.pnlArmor.add(this.pnlArmorSetup, gridBagConstraints);
/*       */     
/*  7441 */     this.pnlPatchworkChoosers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Patchwork Armor Types"));
/*  7442 */     this.pnlPatchworkChoosers.setLayout(new GridBagLayout());
/*       */     
/*  7444 */     this.lblPWHDLoc.setText("Head Armor: ");
/*  7445 */     gridBagConstraints = new GridBagConstraints();
/*  7446 */     gridBagConstraints.anchor = 13;
/*  7447 */     this.pnlPatchworkChoosers.add(this.lblPWHDLoc, gridBagConstraints);
/*       */     
/*  7449 */     this.lblPWCTLoc.setText("CT Armor: ");
/*  7450 */     gridBagConstraints = new GridBagConstraints();
/*  7451 */     gridBagConstraints.gridx = 0;
/*  7452 */     gridBagConstraints.gridy = 1;
/*  7453 */     gridBagConstraints.anchor = 13;
/*  7454 */     this.pnlPatchworkChoosers.add(this.lblPWCTLoc, gridBagConstraints);
/*       */     
/*  7456 */     this.lblPWLTLoc.setText("LT Armor: ");
/*  7457 */     gridBagConstraints = new GridBagConstraints();
/*  7458 */     gridBagConstraints.gridx = 0;
/*  7459 */     gridBagConstraints.gridy = 2;
/*  7460 */     gridBagConstraints.anchor = 13;
/*  7461 */     this.pnlPatchworkChoosers.add(this.lblPWLTLoc, gridBagConstraints);
/*       */     
/*  7463 */     this.lblPWRTLoc.setText("RT Armor: ");
/*  7464 */     gridBagConstraints = new GridBagConstraints();
/*  7465 */     gridBagConstraints.gridx = 0;
/*  7466 */     gridBagConstraints.gridy = 3;
/*  7467 */     gridBagConstraints.anchor = 13;
/*  7468 */     this.pnlPatchworkChoosers.add(this.lblPWRTLoc, gridBagConstraints);
/*       */     
/*  7470 */     this.lblPWLALoc.setText("LA Armor: ");
/*  7471 */     gridBagConstraints = new GridBagConstraints();
/*  7472 */     gridBagConstraints.gridx = 0;
/*  7473 */     gridBagConstraints.gridy = 4;
/*  7474 */     gridBagConstraints.anchor = 13;
/*  7475 */     this.pnlPatchworkChoosers.add(this.lblPWLALoc, gridBagConstraints);
/*       */     
/*  7477 */     this.lblPWRALoc.setText("RA Armor: ");
/*  7478 */     gridBagConstraints = new GridBagConstraints();
/*  7479 */     gridBagConstraints.gridx = 0;
/*  7480 */     gridBagConstraints.gridy = 5;
/*  7481 */     gridBagConstraints.anchor = 13;
/*  7482 */     this.pnlPatchworkChoosers.add(this.lblPWRALoc, gridBagConstraints);
/*       */     
/*  7484 */     this.lblPWLLLoc.setText("LL Armor: ");
/*  7485 */     gridBagConstraints = new GridBagConstraints();
/*  7486 */     gridBagConstraints.gridx = 0;
/*  7487 */     gridBagConstraints.gridy = 6;
/*  7488 */     gridBagConstraints.anchor = 13;
/*  7489 */     this.pnlPatchworkChoosers.add(this.lblPWLLLoc, gridBagConstraints);
/*       */     
/*  7491 */     this.cmbPWHDType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7492 */     this.cmbPWHDType.setMaximumSize(new Dimension(150, 20));
/*  7493 */     this.cmbPWHDType.setMinimumSize(new Dimension(150, 20));
/*  7494 */     this.cmbPWHDType.setPreferredSize(new Dimension(150, 20));
/*  7495 */     this.cmbPWHDType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7497 */         frmMain.this.cmbPWHDTypeActionPerformed(evt);
/*       */       }
/*  7499 */     });
/*  7500 */     this.pnlPatchworkChoosers.add(this.cmbPWHDType, new GridBagConstraints());
/*       */     
/*  7502 */     this.cmbPWCTType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7503 */     this.cmbPWCTType.setMaximumSize(new Dimension(150, 20));
/*  7504 */     this.cmbPWCTType.setMinimumSize(new Dimension(150, 20));
/*  7505 */     this.cmbPWCTType.setPreferredSize(new Dimension(150, 20));
/*  7506 */     this.cmbPWCTType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7508 */         frmMain.this.cmbPWCTTypeActionPerformed(evt);
/*       */       }
/*  7510 */     });
/*  7511 */     gridBagConstraints = new GridBagConstraints();
/*  7512 */     gridBagConstraints.gridx = 1;
/*  7513 */     gridBagConstraints.gridy = 1;
/*  7514 */     this.pnlPatchworkChoosers.add(this.cmbPWCTType, gridBagConstraints);
/*       */     
/*  7516 */     this.cmbPWLTType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7517 */     this.cmbPWLTType.setMaximumSize(new Dimension(150, 20));
/*  7518 */     this.cmbPWLTType.setMinimumSize(new Dimension(150, 20));
/*  7519 */     this.cmbPWLTType.setPreferredSize(new Dimension(150, 20));
/*  7520 */     this.cmbPWLTType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7522 */         frmMain.this.cmbPWLTTypeActionPerformed(evt);
/*       */       }
/*  7524 */     });
/*  7525 */     gridBagConstraints = new GridBagConstraints();
/*  7526 */     gridBagConstraints.gridx = 1;
/*  7527 */     gridBagConstraints.gridy = 2;
/*  7528 */     this.pnlPatchworkChoosers.add(this.cmbPWLTType, gridBagConstraints);
/*       */     
/*  7530 */     this.cmbPWRTType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7531 */     this.cmbPWRTType.setMaximumSize(new Dimension(150, 20));
/*  7532 */     this.cmbPWRTType.setMinimumSize(new Dimension(150, 20));
/*  7533 */     this.cmbPWRTType.setPreferredSize(new Dimension(150, 20));
/*  7534 */     this.cmbPWRTType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7536 */         frmMain.this.cmbPWRTTypeActionPerformed(evt);
/*       */       }
/*  7538 */     });
/*  7539 */     gridBagConstraints = new GridBagConstraints();
/*  7540 */     gridBagConstraints.gridx = 1;
/*  7541 */     gridBagConstraints.gridy = 3;
/*  7542 */     this.pnlPatchworkChoosers.add(this.cmbPWRTType, gridBagConstraints);
/*       */     
/*  7544 */     this.cmbPWLAType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7545 */     this.cmbPWLAType.setMaximumSize(new Dimension(150, 20));
/*  7546 */     this.cmbPWLAType.setMinimumSize(new Dimension(150, 20));
/*  7547 */     this.cmbPWLAType.setPreferredSize(new Dimension(150, 20));
/*  7548 */     this.cmbPWLAType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7550 */         frmMain.this.cmbPWLATypeActionPerformed(evt);
/*       */       }
/*  7552 */     });
/*  7553 */     gridBagConstraints = new GridBagConstraints();
/*  7554 */     gridBagConstraints.gridx = 1;
/*  7555 */     gridBagConstraints.gridy = 4;
/*  7556 */     this.pnlPatchworkChoosers.add(this.cmbPWLAType, gridBagConstraints);
/*       */     
/*  7558 */     this.cmbPWRAType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7559 */     this.cmbPWRAType.setMaximumSize(new Dimension(150, 20));
/*  7560 */     this.cmbPWRAType.setMinimumSize(new Dimension(150, 20));
/*  7561 */     this.cmbPWRAType.setPreferredSize(new Dimension(150, 20));
/*  7562 */     this.cmbPWRAType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7564 */         frmMain.this.cmbPWRATypeActionPerformed(evt);
/*       */       }
/*  7566 */     });
/*  7567 */     gridBagConstraints = new GridBagConstraints();
/*  7568 */     gridBagConstraints.gridx = 1;
/*  7569 */     gridBagConstraints.gridy = 5;
/*  7570 */     this.pnlPatchworkChoosers.add(this.cmbPWRAType, gridBagConstraints);
/*       */     
/*  7572 */     this.cmbPWLLType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7573 */     this.cmbPWLLType.setMaximumSize(new Dimension(150, 20));
/*  7574 */     this.cmbPWLLType.setMinimumSize(new Dimension(150, 20));
/*  7575 */     this.cmbPWLLType.setPreferredSize(new Dimension(150, 20));
/*  7576 */     this.cmbPWLLType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7578 */         frmMain.this.cmbPWLLTypeActionPerformed(evt);
/*       */       }
/*  7580 */     });
/*  7581 */     gridBagConstraints = new GridBagConstraints();
/*  7582 */     gridBagConstraints.gridx = 1;
/*  7583 */     gridBagConstraints.gridy = 6;
/*  7584 */     this.pnlPatchworkChoosers.add(this.cmbPWLLType, gridBagConstraints);
/*       */     
/*  7586 */     this.cmbPWRLType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7587 */     this.cmbPWRLType.setMaximumSize(new Dimension(150, 20));
/*  7588 */     this.cmbPWRLType.setMinimumSize(new Dimension(150, 20));
/*  7589 */     this.cmbPWRLType.setPreferredSize(new Dimension(150, 20));
/*  7590 */     this.cmbPWRLType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7592 */         frmMain.this.cmbPWRLTypeActionPerformed(evt);
/*       */       }
/*  7594 */     });
/*  7595 */     gridBagConstraints = new GridBagConstraints();
/*  7596 */     gridBagConstraints.gridx = 1;
/*  7597 */     gridBagConstraints.gridy = 7;
/*  7598 */     this.pnlPatchworkChoosers.add(this.cmbPWRLType, gridBagConstraints);
/*       */     
/*  7600 */     this.lblPWRLLoc.setText("RL Armor: ");
/*  7601 */     gridBagConstraints = new GridBagConstraints();
/*  7602 */     gridBagConstraints.gridx = 0;
/*  7603 */     gridBagConstraints.gridy = 7;
/*  7604 */     gridBagConstraints.anchor = 13;
/*  7605 */     this.pnlPatchworkChoosers.add(this.lblPWRLLoc, gridBagConstraints);
/*       */     
/*  7607 */     this.lblPWRLLoc1.setText("CL Armor: ");
/*  7608 */     gridBagConstraints = new GridBagConstraints();
/*  7609 */     gridBagConstraints.gridx = 0;
/*  7610 */     gridBagConstraints.gridy = 8;
/*  7611 */     gridBagConstraints.anchor = 13;
/*  7612 */     this.pnlPatchworkChoosers.add(this.lblPWRLLoc1, gridBagConstraints);
/*       */     
/*  7614 */     this.cmbPWCLType.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
/*  7615 */     this.cmbPWCLType.setMaximumSize(new Dimension(150, 20));
/*  7616 */     this.cmbPWCLType.setMinimumSize(new Dimension(150, 20));
/*  7617 */     this.cmbPWCLType.setPreferredSize(new Dimension(150, 20));
/*  7618 */     this.cmbPWCLType.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7620 */         frmMain.this.cmbPWCLTypeActionPerformed(evt);
/*       */       }
/*  7622 */     });
/*  7623 */     gridBagConstraints = new GridBagConstraints();
/*  7624 */     gridBagConstraints.gridx = 1;
/*  7625 */     gridBagConstraints.gridy = 8;
/*  7626 */     this.pnlPatchworkChoosers.add(this.cmbPWCLType, gridBagConstraints);
/*       */     
/*  7628 */     gridBagConstraints = new GridBagConstraints();
/*  7629 */     gridBagConstraints.gridx = 2;
/*  7630 */     gridBagConstraints.gridy = 0;
/*  7631 */     gridBagConstraints.gridheight = 3;
/*  7632 */     gridBagConstraints.fill = 2;
/*  7633 */     gridBagConstraints.anchor = 11;
/*  7634 */     this.pnlArmor.add(this.pnlPatchworkChoosers, gridBagConstraints);
/*       */     
/*  7636 */     this.tbpMainTabPane.addTab("  Armor  ", this.pnlArmor);
/*       */     
/*  7638 */     this.pnlEquipment.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/*  7640 */     this.tbpWeaponChooser.setTabPlacement(4);
/*  7641 */     this.tbpWeaponChooser.setMaximumSize(new Dimension(300, 300));
/*  7642 */     this.tbpWeaponChooser.setMinimumSize(new Dimension(300, 300));
/*  7643 */     this.tbpWeaponChooser.setPreferredSize(new Dimension(300, 300));
/*       */     
/*  7645 */     this.pnlBallistic.setLayout(new BoxLayout(this.pnlBallistic, 1));
/*       */     
/*  7647 */     this.jSeparator3.setOrientation(1);
/*  7648 */     this.jSeparator3.setAlignmentX(0.0F);
/*  7649 */     this.jSeparator3.setAlignmentY(0.0F);
/*  7650 */     this.pnlBallistic.add(this.jSeparator3);
/*       */     
/*  7652 */     this.jScrollPane8.setHorizontalScrollBarPolicy(31);
/*  7653 */     this.jScrollPane8.setVerticalScrollBarPolicy(22);
/*  7654 */     this.jScrollPane8.setMaximumSize(new Dimension(200, 260));
/*  7655 */     this.jScrollPane8.setMinimumSize(new Dimension(200, 260));
/*  7656 */     this.jScrollPane8.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7658 */     this.lstChooseBallistic.setModel(new AbstractListModel() {
/*  7659 */       String[] strings = { "Placeholder" };
/*  7660 */       public int getSize() { return this.strings.length; }
/*  7661 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7662 */     });
/*  7663 */     this.lstChooseBallistic.setSelectionMode(0);
/*  7664 */     this.lstChooseBallistic.setMaximumSize(new Dimension(180, 10000));
/*  7665 */     this.lstChooseBallistic.setMinimumSize(new Dimension(180, 100));
/*  7666 */     this.lstChooseBallistic.setPreferredSize(null);
/*  7667 */     this.lstChooseBallistic.setVisibleRowCount(16);
/*  7668 */     this.lstChooseBallistic.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7670 */         frmMain.this.lstChooseBallisticValueChanged(evt);
/*       */       }
/*  7672 */     });
/*  7673 */     MouseListener mlBallistic = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7675 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7676 */           frmMain.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7679 */     };
/*  7680 */     this.lstChooseBallistic.addMouseListener(mlBallistic);
/*  7681 */     this.lstChooseBallistic.setCellRenderer(new EquipmentListRenderer(this));
/*  7682 */     this.jScrollPane8.setViewportView(this.lstChooseBallistic);
/*       */     
/*  7684 */     this.pnlBallistic.add(this.jScrollPane8);
/*       */     
/*  7686 */     this.jSeparator4.setOrientation(1);
/*  7687 */     this.jSeparator4.setAlignmentX(0.0F);
/*  7688 */     this.jSeparator4.setAlignmentY(0.0F);
/*  7689 */     this.pnlBallistic.add(this.jSeparator4);
/*       */     
/*  7691 */     this.tbpWeaponChooser.addTab("Ballistic", this.pnlBallistic);
/*       */     
/*  7693 */     this.pnlEnergy.setLayout(new BoxLayout(this.pnlEnergy, 1));
/*       */     
/*  7695 */     this.jSeparator2.setOrientation(1);
/*  7696 */     this.jSeparator2.setAlignmentX(0.0F);
/*  7697 */     this.jSeparator2.setAlignmentY(0.0F);
/*  7698 */     this.pnlEnergy.add(this.jSeparator2);
/*       */     
/*  7700 */     this.jScrollPane9.setHorizontalScrollBarPolicy(31);
/*  7701 */     this.jScrollPane9.setVerticalScrollBarPolicy(22);
/*  7702 */     this.jScrollPane9.setMaximumSize(new Dimension(200, 260));
/*  7703 */     this.jScrollPane9.setMinimumSize(new Dimension(200, 260));
/*  7704 */     this.jScrollPane9.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7706 */     this.lstChooseEnergy.setModel(new AbstractListModel() {
/*  7707 */       String[] strings = { "Placeholder" };
/*  7708 */       public int getSize() { return this.strings.length; }
/*  7709 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7710 */     });
/*  7711 */     this.lstChooseEnergy.setSelectionMode(0);
/*  7712 */     this.lstChooseEnergy.setMaximumSize(new Dimension(180, 10000));
/*  7713 */     this.lstChooseEnergy.setMinimumSize(new Dimension(180, 100));
/*  7714 */     this.lstChooseEnergy.setPreferredSize(null);
/*  7715 */     this.lstChooseEnergy.setVisibleRowCount(16);
/*  7716 */     this.lstChooseEnergy.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7718 */         frmMain.this.lstChooseEnergyValueChanged(evt);
/*       */       }
/*  7720 */     });
/*  7721 */     MouseListener mlEnergy = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7723 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7724 */           frmMain.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7727 */     };
/*  7728 */     this.lstChooseEnergy.addMouseListener(mlEnergy);
/*  7729 */     this.lstChooseEnergy.setCellRenderer(new EquipmentListRenderer(this));
/*  7730 */     this.jScrollPane9.setViewportView(this.lstChooseEnergy);
/*       */     
/*  7732 */     this.pnlEnergy.add(this.jScrollPane9);
/*       */     
/*  7734 */     this.jSeparator1.setOrientation(1);
/*  7735 */     this.jSeparator1.setAlignmentX(0.0F);
/*  7736 */     this.jSeparator1.setAlignmentY(0.0F);
/*  7737 */     this.pnlEnergy.add(this.jSeparator1);
/*       */     
/*  7739 */     this.tbpWeaponChooser.addTab("Energy", this.pnlEnergy);
/*       */     
/*  7741 */     this.pnlMissile.setLayout(new BoxLayout(this.pnlMissile, 1));
/*       */     
/*  7743 */     this.jSeparator5.setOrientation(1);
/*  7744 */     this.jSeparator5.setAlignmentX(0.0F);
/*  7745 */     this.jSeparator5.setAlignmentY(0.0F);
/*  7746 */     this.pnlMissile.add(this.jSeparator5);
/*       */     
/*  7748 */     this.jScrollPane19.setHorizontalScrollBarPolicy(31);
/*  7749 */     this.jScrollPane19.setVerticalScrollBarPolicy(22);
/*  7750 */     this.jScrollPane19.setMaximumSize(new Dimension(200, 260));
/*  7751 */     this.jScrollPane19.setMinimumSize(new Dimension(200, 260));
/*  7752 */     this.jScrollPane19.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7754 */     this.lstChooseMissile.setModel(new AbstractListModel() {
/*  7755 */       String[] strings = { "Placeholder" };
/*  7756 */       public int getSize() { return this.strings.length; }
/*  7757 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7758 */     });
/*  7759 */     this.lstChooseMissile.setSelectionMode(0);
/*  7760 */     this.lstChooseMissile.setMaximumSize(new Dimension(180, 10000));
/*  7761 */     this.lstChooseMissile.setMinimumSize(new Dimension(180, 100));
/*  7762 */     this.lstChooseMissile.setPreferredSize(null);
/*  7763 */     this.lstChooseMissile.setVisibleRowCount(16);
/*  7764 */     this.lstChooseMissile.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7766 */         frmMain.this.lstChooseMissileValueChanged(evt);
/*       */       }
/*  7768 */     });
/*  7769 */     MouseListener mlMissile = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7771 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7772 */           frmMain.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7775 */     };
/*  7776 */     this.lstChooseMissile.addMouseListener(mlMissile);
/*  7777 */     this.lstChooseMissile.setCellRenderer(new EquipmentListRenderer(this));
/*  7778 */     this.jScrollPane19.setViewportView(this.lstChooseMissile);
/*       */     
/*  7780 */     this.pnlMissile.add(this.jScrollPane19);
/*       */     
/*  7782 */     this.jSeparator6.setOrientation(1);
/*  7783 */     this.jSeparator6.setAlignmentX(0.0F);
/*  7784 */     this.jSeparator6.setAlignmentY(0.0F);
/*  7785 */     this.pnlMissile.add(this.jSeparator6);
/*       */     
/*  7787 */     this.tbpWeaponChooser.addTab("Missile", this.pnlMissile);
/*       */     
/*  7789 */     this.pnlPhysical.setLayout(new BoxLayout(this.pnlPhysical, 1));
/*       */     
/*  7791 */     this.jSeparator8.setOrientation(1);
/*  7792 */     this.jSeparator8.setAlignmentX(0.0F);
/*  7793 */     this.jSeparator8.setAlignmentY(0.0F);
/*  7794 */     this.pnlPhysical.add(this.jSeparator8);
/*       */     
/*  7796 */     this.jScrollPane20.setHorizontalScrollBarPolicy(31);
/*  7797 */     this.jScrollPane20.setVerticalScrollBarPolicy(22);
/*  7798 */     this.jScrollPane20.setMaximumSize(new Dimension(200, 260));
/*  7799 */     this.jScrollPane20.setMinimumSize(new Dimension(200, 260));
/*  7800 */     this.jScrollPane20.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7802 */     this.lstChoosePhysical.setModel(new AbstractListModel() {
/*  7803 */       String[] strings = { "Placeholder" };
/*  7804 */       public int getSize() { return this.strings.length; }
/*  7805 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7806 */     });
/*  7807 */     this.lstChoosePhysical.setSelectionMode(0);
/*  7808 */     this.lstChoosePhysical.setMaximumSize(new Dimension(180, 10000));
/*  7809 */     this.lstChoosePhysical.setMinimumSize(new Dimension(180, 100));
/*  7810 */     this.lstChoosePhysical.setPreferredSize(null);
/*  7811 */     this.lstChoosePhysical.setVisibleRowCount(16);
/*  7812 */     this.lstChoosePhysical.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7814 */         frmMain.this.lstChoosePhysicalValueChanged(evt);
/*       */       }
/*  7816 */     });
/*  7817 */     MouseListener mlPhysical = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7819 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7820 */           frmMain.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7823 */     };
/*  7824 */     this.lstChoosePhysical.addMouseListener(mlPhysical);
/*  7825 */     this.lstChoosePhysical.setCellRenderer(new EquipmentListRenderer(this));
/*  7826 */     this.jScrollPane20.setViewportView(this.lstChoosePhysical);
/*       */     
/*  7828 */     this.pnlPhysical.add(this.jScrollPane20);
/*       */     
/*  7830 */     this.jSeparator7.setOrientation(1);
/*  7831 */     this.jSeparator7.setAlignmentX(0.0F);
/*  7832 */     this.jSeparator7.setAlignmentY(0.0F);
/*  7833 */     this.pnlPhysical.add(this.jSeparator7);
/*       */     
/*  7835 */     this.tbpWeaponChooser.addTab("Physical", this.pnlPhysical);
/*       */     
/*  7837 */     this.pnlEquipmentChooser.setLayout(new BoxLayout(this.pnlEquipmentChooser, 1));
/*       */     
/*  7839 */     this.jSeparator10.setOrientation(1);
/*  7840 */     this.jSeparator10.setAlignmentX(0.0F);
/*  7841 */     this.jSeparator10.setAlignmentY(0.0F);
/*  7842 */     this.pnlEquipmentChooser.add(this.jSeparator10);
/*       */     
/*  7844 */     this.jScrollPane21.setHorizontalScrollBarPolicy(31);
/*  7845 */     this.jScrollPane21.setVerticalScrollBarPolicy(22);
/*  7846 */     this.jScrollPane21.setMaximumSize(new Dimension(200, 260));
/*  7847 */     this.jScrollPane21.setMinimumSize(new Dimension(200, 260));
/*  7848 */     this.jScrollPane21.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7850 */     this.lstChooseEquipment.setModel(new AbstractListModel() {
/*  7851 */       String[] strings = { "Placeholder" };
/*  7852 */       public int getSize() { return this.strings.length; }
/*  7853 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7854 */     });
/*  7855 */     this.lstChooseEquipment.setSelectionMode(0);
/*  7856 */     this.lstChooseEquipment.setMaximumSize(new Dimension(180, 10000));
/*  7857 */     this.lstChooseEquipment.setMinimumSize(new Dimension(180, 100));
/*  7858 */     this.lstChooseEquipment.setPreferredSize(null);
/*  7859 */     this.lstChooseEquipment.setVisibleRowCount(16);
/*  7860 */     this.lstChooseEquipment.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7862 */         frmMain.this.lstChooseEquipmentValueChanged(evt);
/*       */       }
/*  7864 */     });
/*  7865 */     MouseListener mlEquipment = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7867 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7868 */           frmMain.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7871 */     };
/*  7872 */     this.lstChooseEquipment.addMouseListener(mlEquipment);
/*  7873 */     this.lstChooseEquipment.setCellRenderer(new EquipmentListRenderer(this));
/*  7874 */     this.jScrollPane21.setViewportView(this.lstChooseEquipment);
/*       */     
/*  7876 */     this.pnlEquipmentChooser.add(this.jScrollPane21);
/*       */     
/*  7878 */     this.jSeparator9.setOrientation(1);
/*  7879 */     this.jSeparator9.setAlignmentX(0.0F);
/*  7880 */     this.jSeparator9.setAlignmentY(0.0F);
/*  7881 */     this.pnlEquipmentChooser.add(this.jSeparator9);
/*       */     
/*  7883 */     this.tbpWeaponChooser.addTab("Equipment", this.pnlEquipmentChooser);
/*       */     
/*  7885 */     this.pnlArtillery.setLayout(new BoxLayout(this.pnlArtillery, 1));
/*       */     
/*  7887 */     this.jSeparator18.setOrientation(1);
/*  7888 */     this.jSeparator18.setAlignmentX(0.0F);
/*  7889 */     this.jSeparator18.setAlignmentY(0.0F);
/*  7890 */     this.pnlArtillery.add(this.jSeparator18);
/*       */     
/*  7892 */     this.jScrollPane24.setHorizontalScrollBarPolicy(31);
/*  7893 */     this.jScrollPane24.setVerticalScrollBarPolicy(22);
/*  7894 */     this.jScrollPane24.setMaximumSize(new Dimension(200, 260));
/*  7895 */     this.jScrollPane24.setMinimumSize(new Dimension(200, 260));
/*  7896 */     this.jScrollPane24.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7898 */     this.lstChooseArtillery.setModel(new AbstractListModel() {
/*  7899 */       String[] strings = { "Placeholder" };
/*  7900 */       public int getSize() { return this.strings.length; }
/*  7901 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7902 */     });
/*  7903 */     this.lstChooseArtillery.setSelectionMode(0);
/*  7904 */     this.lstChooseArtillery.setMaximumSize(new Dimension(180, 10000));
/*  7905 */     this.lstChooseArtillery.setMinimumSize(new Dimension(180, 100));
/*  7906 */     this.lstChooseArtillery.setPreferredSize(null);
/*  7907 */     this.lstChooseArtillery.setVisibleRowCount(16);
/*  7908 */     this.lstChooseArtillery.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7910 */         frmMain.this.lstChooseArtilleryValueChanged(evt);
/*       */       }
/*  7912 */     });
/*  7913 */     MouseListener mlArtillery = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7915 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7916 */           frmMain.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7919 */     };
/*  7920 */     this.lstChooseArtillery.addMouseListener(mlArtillery);
/*  7921 */     this.lstChooseArtillery.setCellRenderer(new EquipmentListRenderer(this));
/*  7922 */     this.jScrollPane24.setViewportView(this.lstChooseArtillery);
/*       */     
/*  7924 */     this.pnlArtillery.add(this.jScrollPane24);
/*       */     
/*  7926 */     this.jSeparator19.setOrientation(1);
/*  7927 */     this.jSeparator19.setAlignmentX(0.0F);
/*  7928 */     this.jSeparator19.setAlignmentY(0.0F);
/*  7929 */     this.pnlArtillery.add(this.jSeparator19);
/*       */     
/*  7931 */     this.tbpWeaponChooser.addTab("Artillery", this.pnlArtillery);
/*       */     
/*  7933 */     this.pnlAmmunition.setLayout(new BoxLayout(this.pnlAmmunition, 1));
/*       */     
/*  7935 */     this.jSeparator11.setOrientation(1);
/*  7936 */     this.jSeparator11.setAlignmentX(0.0F);
/*  7937 */     this.jSeparator11.setAlignmentY(0.0F);
/*  7938 */     this.pnlAmmunition.add(this.jSeparator11);
/*       */     
/*  7940 */     this.jScrollPane22.setHorizontalScrollBarPolicy(31);
/*  7941 */     this.jScrollPane22.setVerticalScrollBarPolicy(22);
/*  7942 */     this.jScrollPane22.setMaximumSize(new Dimension(200, 260));
/*  7943 */     this.jScrollPane22.setMinimumSize(new Dimension(200, 260));
/*  7944 */     this.jScrollPane22.setPreferredSize(new Dimension(200, 260));
/*       */     
/*  7946 */     this.lstChooseAmmunition.setModel(new AbstractListModel() {
/*  7947 */       String[] strings = { "Placeholder" };
/*  7948 */       public int getSize() { return this.strings.length; }
/*  7949 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  7950 */     });
/*  7951 */     this.lstChooseAmmunition.setSelectionMode(0);
/*  7952 */     this.lstChooseAmmunition.setMaximumSize(new Dimension(180, 10000));
/*  7953 */     this.lstChooseAmmunition.setMinimumSize(new Dimension(180, 100));
/*  7954 */     this.lstChooseAmmunition.setPreferredSize(null);
/*  7955 */     this.lstChooseAmmunition.setVisibleRowCount(16);
/*  7956 */     this.lstChooseAmmunition.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  7958 */         frmMain.this.lstChooseAmmunitionValueChanged(evt);
/*       */       }
/*  7960 */     });
/*  7961 */     MouseListener mlAmmo = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  7963 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  7964 */           frmMain.this.btnAddEquipActionPerformed(null);
/*       */         }
/*       */       }
/*  7967 */     };
/*  7968 */     this.lstChooseAmmunition.addMouseListener(mlAmmo);
/*  7969 */     this.lstChooseAmmunition.setCellRenderer(new EquipmentListRenderer(this));
/*  7970 */     this.jScrollPane22.setViewportView(this.lstChooseAmmunition);
/*       */     
/*  7972 */     this.pnlAmmunition.add(this.jScrollPane22);
/*       */     
/*  7974 */     this.jSeparator12.setOrientation(1);
/*  7975 */     this.jSeparator12.setAlignmentX(0.0F);
/*  7976 */     this.jSeparator12.setAlignmentY(0.0F);
/*  7977 */     this.pnlAmmunition.add(this.jSeparator12);
/*       */     
/*  7979 */     this.tbpWeaponChooser.addTab("Ammunition", this.pnlAmmunition);
/*       */     
/*  7981 */     this.pnlEquipment.add(this.tbpWeaponChooser, new AbsoluteConstraints(20, 20, -1, -1));
/*       */     
/*  7983 */     this.pnlSpecials.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Specials"));
/*  7984 */     this.pnlSpecials.setLayout(new GridBagLayout());
/*       */     
/*  7986 */     this.jLabel16.setText("Missile Guidance:");
/*  7987 */     gridBagConstraints = new GridBagConstraints();
/*  7988 */     gridBagConstraints.fill = 2;
/*  7989 */     gridBagConstraints.anchor = 17;
/*  7990 */     gridBagConstraints.insets = new Insets(0, 2, 0, 0);
/*  7991 */     this.pnlSpecials.add(this.jLabel16, gridBagConstraints);
/*       */     
/*  7993 */     this.chkUseTC.setText("Targeting Computer");
/*  7994 */     this.chkUseTC.setEnabled(false);
/*  7995 */     this.chkUseTC.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  7997 */         frmMain.this.chkUseTCActionPerformed(evt);
/*       */       }
/*  7999 */     });
/*  8000 */     gridBagConstraints = new GridBagConstraints();
/*  8001 */     gridBagConstraints.gridx = 0;
/*  8002 */     gridBagConstraints.gridy = 4;
/*  8003 */     gridBagConstraints.anchor = 17;
/*  8004 */     gridBagConstraints.insets = new Insets(8, 2, 0, 0);
/*  8005 */     this.pnlSpecials.add(this.chkUseTC, gridBagConstraints);
/*       */     
/*  8007 */     this.chkFCSAIV.setText("Use Artemis IV");
/*  8008 */     this.chkFCSAIV.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8010 */         frmMain.this.chkFCSAIVActionPerformed(evt);
/*       */       }
/*  8012 */     });
/*  8013 */     gridBagConstraints = new GridBagConstraints();
/*  8014 */     gridBagConstraints.gridx = 0;
/*  8015 */     gridBagConstraints.gridy = 1;
/*  8016 */     gridBagConstraints.anchor = 17;
/*  8017 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  8018 */     this.pnlSpecials.add(this.chkFCSAIV, gridBagConstraints);
/*       */     
/*  8020 */     this.chkFCSAV.setText("Use Artemis V");
/*  8021 */     this.chkFCSAV.setEnabled(false);
/*  8022 */     this.chkFCSAV.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8024 */         frmMain.this.chkFCSAVActionPerformed(evt);
/*       */       }
/*  8026 */     });
/*  8027 */     gridBagConstraints = new GridBagConstraints();
/*  8028 */     gridBagConstraints.gridx = 0;
/*  8029 */     gridBagConstraints.gridy = 2;
/*  8030 */     gridBagConstraints.anchor = 17;
/*  8031 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  8032 */     this.pnlSpecials.add(this.chkFCSAV, gridBagConstraints);
/*       */     
/*  8034 */     this.chkFCSApollo.setText("Use MRM Apollo");
/*  8035 */     this.chkFCSApollo.setEnabled(false);
/*  8036 */     this.chkFCSApollo.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8038 */         frmMain.this.chkFCSApolloActionPerformed(evt);
/*       */       }
/*  8040 */     });
/*  8041 */     gridBagConstraints = new GridBagConstraints();
/*  8042 */     gridBagConstraints.gridx = 0;
/*  8043 */     gridBagConstraints.gridy = 3;
/*  8044 */     gridBagConstraints.anchor = 17;
/*  8045 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  8046 */     this.pnlSpecials.add(this.chkFCSApollo, gridBagConstraints);
/*       */     
/*  8048 */     this.chkClanCASE.setText("Use Clan CASE");
/*  8049 */     this.chkClanCASE.setEnabled(false);
/*  8050 */     this.chkClanCASE.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8052 */         frmMain.this.chkClanCASEActionPerformed(evt);
/*       */       }
/*  8054 */     });
/*  8055 */     gridBagConstraints = new GridBagConstraints();
/*  8056 */     gridBagConstraints.gridx = 0;
/*  8057 */     gridBagConstraints.gridy = 5;
/*  8058 */     gridBagConstraints.anchor = 17;
/*  8059 */     gridBagConstraints.insets = new Insets(8, 2, 0, 0);
/*  8060 */     this.pnlSpecials.add(this.chkClanCASE, gridBagConstraints);
/*       */     
/*  8062 */     this.pnlEquipment.add(this.pnlSpecials, new AbsoluteConstraints(330, 100, 160, 220));
/*       */     
/*  8064 */     this.pnlSelected.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Selected Equipment"));
/*  8065 */     this.pnlSelected.setMaximumSize(new Dimension(212, 286));
/*  8066 */     this.pnlSelected.setMinimumSize(new Dimension(212, 286));
/*  8067 */     this.pnlSelected.setLayout(new BoxLayout(this.pnlSelected, 2));
/*       */     
/*  8069 */     this.jScrollPane23.setHorizontalScrollBarPolicy(31);
/*  8070 */     this.jScrollPane23.setVerticalScrollBarPolicy(22);
/*       */     
/*  8072 */     this.lstSelectedEquipment.setModel(new javax.swing.DefaultListModel());
/*       */     
/*  8074 */     this.lstSelectedEquipment.setMaximumSize(new Dimension(180, 225));
/*  8075 */     this.lstSelectedEquipment.setMinimumSize(new Dimension(180, 225));
/*  8076 */     this.lstSelectedEquipment.setPreferredSize(null);
/*  8077 */     this.lstSelectedEquipment.setVisibleRowCount(16);
/*  8078 */     this.lstSelectedEquipment.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  8080 */         frmMain.this.lstSelectedEquipmentValueChanged(evt);
/*       */       }
/*  8082 */     });
/*  8083 */     this.lstSelectedEquipment.addKeyListener(new java.awt.event.KeyAdapter() {
/*       */       public void keyPressed(java.awt.event.KeyEvent evt) {
/*  8085 */         frmMain.this.lstSelectedEquipmentKeyPressed(evt);
/*       */       }
/*  8087 */     });
/*  8088 */     MouseListener mlSelect = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  8090 */         int Index = frmMain.this.lstSelectedEquipment.locationToIndex(e.getPoint());
/*  8091 */         if (Index < 0) return;
/*  8092 */         frmMain.this.CurItem = ((abPlaceable)frmMain.this.CurMech.GetLoadout().GetNonCore().get(Index));
/*  8093 */         if (e.isPopupTrigger()) {
/*  8094 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8095 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8099 */       public void mousePressed(MouseEvent e) { int Index = frmMain.this.lstSelectedEquipment.locationToIndex(e.getPoint());
/*  8100 */         if (Index < 0) return;
/*  8101 */         frmMain.this.CurItem = ((abPlaceable)frmMain.this.CurMech.GetLoadout().GetNonCore().get(Index));
/*  8102 */         if (e.isPopupTrigger()) {
/*  8103 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8104 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  8107 */     };
/*  8108 */     this.lstSelectedEquipment.addMouseListener(mlSelect);
/*  8109 */     this.lstSelectedEquipment.setCellRenderer(new EquipmentSelectedRenderer(this));
/*  8110 */     this.jScrollPane23.setViewportView(this.lstSelectedEquipment);
/*       */     
/*  8112 */     this.pnlSelected.add(this.jScrollPane23);
/*       */     
/*  8114 */     this.pnlEquipment.add(this.pnlSelected, new AbsoluteConstraints(492, 20, 230, 300));
/*       */     
/*  8116 */     this.pnlControls.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Controls"));
/*  8117 */     this.pnlControls.setLayout(new GridBagLayout());
/*       */     
/*  8119 */     this.btnRemoveEquip.setText("<<");
/*  8120 */     this.btnRemoveEquip.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8122 */         frmMain.this.btnRemoveEquipActionPerformed(evt);
/*       */       }
/*  8124 */     });
/*  8125 */     gridBagConstraints = new GridBagConstraints();
/*  8126 */     gridBagConstraints.insets = new Insets(0, 0, 0, 8);
/*  8127 */     this.pnlControls.add(this.btnRemoveEquip, gridBagConstraints);
/*       */     
/*  8129 */     this.btnClearEquip.setText("Clear");
/*  8130 */     this.btnClearEquip.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8132 */         frmMain.this.btnClearEquipActionPerformed(evt);
/*       */       }
/*  8134 */     });
/*  8135 */     gridBagConstraints = new GridBagConstraints();
/*  8136 */     gridBagConstraints.gridx = 0;
/*  8137 */     gridBagConstraints.gridy = 1;
/*  8138 */     gridBagConstraints.insets = new Insets(4, 0, 0, 8);
/*  8139 */     this.pnlControls.add(this.btnClearEquip, gridBagConstraints);
/*       */     
/*  8141 */     this.btnAddEquip.setText(">>");
/*  8142 */     this.btnAddEquip.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8144 */         frmMain.this.btnAddEquipActionPerformed(evt);
/*       */       }
/*  8146 */     });
/*  8147 */     gridBagConstraints = new GridBagConstraints();
/*  8148 */     gridBagConstraints.gridx = 1;
/*  8149 */     gridBagConstraints.gridy = 0;
/*  8150 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  8151 */     this.pnlControls.add(this.btnAddEquip, gridBagConstraints);
/*       */     
/*  8153 */     this.cmbNumEquips.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
/*  8154 */     gridBagConstraints = new GridBagConstraints();
/*  8155 */     gridBagConstraints.gridx = 1;
/*  8156 */     gridBagConstraints.gridy = 1;
/*  8157 */     gridBagConstraints.insets = new Insets(4, 8, 0, 0);
/*  8158 */     this.pnlControls.add(this.cmbNumEquips, gridBagConstraints);
/*       */     
/*  8160 */     this.pnlEquipment.add(this.pnlControls, new AbsoluteConstraints(330, 20, 160, -1));
/*       */     
/*  8162 */     this.pnlEquipInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Information"));
/*  8163 */     this.pnlEquipInfo.setLayout(new GridBagLayout());
/*       */     
/*  8165 */     this.jLabel17.setText("Availability(AoW/SL)");
/*  8166 */     gridBagConstraints = new GridBagConstraints();
/*  8167 */     gridBagConstraints.gridx = 0;
/*  8168 */     gridBagConstraints.gridy = 3;
/*  8169 */     gridBagConstraints.anchor = 17;
/*  8170 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/*  8171 */     this.pnlEquipInfo.add(this.jLabel17, gridBagConstraints);
/*       */     
/*  8173 */     this.jLabel18.setText("Availability (SW)");
/*  8174 */     gridBagConstraints = new GridBagConstraints();
/*  8175 */     gridBagConstraints.gridx = 0;
/*  8176 */     gridBagConstraints.gridy = 4;
/*  8177 */     gridBagConstraints.anchor = 17;
/*  8178 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/*  8179 */     this.pnlEquipInfo.add(this.jLabel18, gridBagConstraints);
/*       */     
/*  8181 */     this.jLabel19.setText("Availability (CI)");
/*  8182 */     gridBagConstraints = new GridBagConstraints();
/*  8183 */     gridBagConstraints.gridx = 0;
/*  8184 */     gridBagConstraints.gridy = 5;
/*  8185 */     gridBagConstraints.anchor = 17;
/*  8186 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/*  8187 */     this.pnlEquipInfo.add(this.jLabel19, gridBagConstraints);
/*  8188 */     gridBagConstraints = new GridBagConstraints();
/*  8189 */     gridBagConstraints.gridx = 1;
/*  8190 */     gridBagConstraints.gridy = 3;
/*  8191 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8192 */     this.pnlEquipInfo.add(this.lblInfoAVSL, gridBagConstraints);
/*  8193 */     gridBagConstraints = new GridBagConstraints();
/*  8194 */     gridBagConstraints.gridx = 1;
/*  8195 */     gridBagConstraints.gridy = 4;
/*  8196 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8197 */     this.pnlEquipInfo.add(this.lblInfoAVSW, gridBagConstraints);
/*  8198 */     gridBagConstraints = new GridBagConstraints();
/*  8199 */     gridBagConstraints.gridx = 1;
/*  8200 */     gridBagConstraints.gridy = 5;
/*  8201 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8202 */     this.pnlEquipInfo.add(this.lblInfoAVCI, gridBagConstraints);
/*       */     
/*  8204 */     this.jLabel20.setText("Introduction");
/*  8205 */     gridBagConstraints = new GridBagConstraints();
/*  8206 */     gridBagConstraints.gridx = 2;
/*  8207 */     gridBagConstraints.gridy = 3;
/*  8208 */     gridBagConstraints.gridwidth = 2;
/*  8209 */     gridBagConstraints.anchor = 13;
/*  8210 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8211 */     this.pnlEquipInfo.add(this.jLabel20, gridBagConstraints);
/*       */     
/*  8213 */     this.jLabel21.setText("Extinction");
/*  8214 */     gridBagConstraints = new GridBagConstraints();
/*  8215 */     gridBagConstraints.gridx = 2;
/*  8216 */     gridBagConstraints.gridy = 4;
/*  8217 */     gridBagConstraints.gridwidth = 2;
/*  8218 */     gridBagConstraints.anchor = 13;
/*  8219 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8220 */     this.pnlEquipInfo.add(this.jLabel21, gridBagConstraints);
/*       */     
/*  8222 */     this.jLabel22.setText("Reintroduction");
/*  8223 */     gridBagConstraints = new GridBagConstraints();
/*  8224 */     gridBagConstraints.gridx = 2;
/*  8225 */     gridBagConstraints.gridy = 5;
/*  8226 */     gridBagConstraints.gridwidth = 2;
/*  8227 */     gridBagConstraints.anchor = 13;
/*  8228 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8229 */     this.pnlEquipInfo.add(this.jLabel22, gridBagConstraints);
/*  8230 */     gridBagConstraints = new GridBagConstraints();
/*  8231 */     gridBagConstraints.gridx = 4;
/*  8232 */     gridBagConstraints.gridy = 3;
/*  8233 */     gridBagConstraints.anchor = 17;
/*  8234 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8235 */     this.pnlEquipInfo.add(this.lblInfoIntro, gridBagConstraints);
/*  8236 */     gridBagConstraints = new GridBagConstraints();
/*  8237 */     gridBagConstraints.gridx = 4;
/*  8238 */     gridBagConstraints.gridy = 4;
/*  8239 */     gridBagConstraints.anchor = 17;
/*  8240 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8241 */     this.pnlEquipInfo.add(this.lblInfoExtinct, gridBagConstraints);
/*  8242 */     gridBagConstraints = new GridBagConstraints();
/*  8243 */     gridBagConstraints.gridx = 4;
/*  8244 */     gridBagConstraints.gridy = 5;
/*  8245 */     gridBagConstraints.anchor = 17;
/*  8246 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8247 */     this.pnlEquipInfo.add(this.lblInfoReintro, gridBagConstraints);
/*       */     
/*  8249 */     this.jLabel23.setText("Name");
/*  8250 */     gridBagConstraints = new GridBagConstraints();
/*  8251 */     gridBagConstraints.anchor = 17;
/*  8252 */     gridBagConstraints.insets = new Insets(4, 0, 0, 3);
/*  8253 */     this.pnlEquipInfo.add(this.jLabel23, gridBagConstraints);
/*       */     
/*  8255 */     this.jLabel24.setText("Type");
/*  8256 */     gridBagConstraints = new GridBagConstraints();
/*  8257 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8258 */     this.pnlEquipInfo.add(this.jLabel24, gridBagConstraints);
/*       */     
/*  8260 */     this.jLabel25.setText("Heat");
/*  8261 */     gridBagConstraints = new GridBagConstraints();
/*  8262 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8263 */     this.pnlEquipInfo.add(this.jLabel25, gridBagConstraints);
/*       */     
/*  8265 */     this.jLabel26.setText("Damage");
/*  8266 */     gridBagConstraints = new GridBagConstraints();
/*  8267 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8268 */     this.pnlEquipInfo.add(this.jLabel26, gridBagConstraints);
/*       */     
/*  8270 */     this.jLabel27.setText("Range");
/*  8271 */     gridBagConstraints = new GridBagConstraints();
/*  8272 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8273 */     this.pnlEquipInfo.add(this.jLabel27, gridBagConstraints);
/*       */     
/*  8275 */     this.lblInfoName.setText(" ");
/*  8276 */     gridBagConstraints = new GridBagConstraints();
/*  8277 */     gridBagConstraints.gridx = 0;
/*  8278 */     gridBagConstraints.gridy = 1;
/*  8279 */     gridBagConstraints.anchor = 17;
/*  8280 */     gridBagConstraints.insets = new Insets(0, 0, 0, 3);
/*  8281 */     this.pnlEquipInfo.add(this.lblInfoName, gridBagConstraints);
/*  8282 */     gridBagConstraints = new GridBagConstraints();
/*  8283 */     gridBagConstraints.gridx = 1;
/*  8284 */     gridBagConstraints.gridy = 1;
/*  8285 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8286 */     this.pnlEquipInfo.add(this.lblInfoType, gridBagConstraints);
/*  8287 */     gridBagConstraints = new GridBagConstraints();
/*  8288 */     gridBagConstraints.gridx = 2;
/*  8289 */     gridBagConstraints.gridy = 1;
/*  8290 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8291 */     this.pnlEquipInfo.add(this.lblInfoHeat, gridBagConstraints);
/*  8292 */     gridBagConstraints = new GridBagConstraints();
/*  8293 */     gridBagConstraints.gridx = 3;
/*  8294 */     gridBagConstraints.gridy = 1;
/*  8295 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8296 */     this.pnlEquipInfo.add(this.lblInfoDamage, gridBagConstraints);
/*  8297 */     gridBagConstraints = new GridBagConstraints();
/*  8298 */     gridBagConstraints.gridx = 4;
/*  8299 */     gridBagConstraints.gridy = 1;
/*  8300 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8301 */     this.pnlEquipInfo.add(this.lblInfoRange, gridBagConstraints);
/*       */     
/*  8303 */     this.jSeparator13.setBorder(BorderFactory.createEtchedBorder());
/*  8304 */     gridBagConstraints = new GridBagConstraints();
/*  8305 */     gridBagConstraints.gridx = 0;
/*  8306 */     gridBagConstraints.gridy = 2;
/*  8307 */     gridBagConstraints.gridwidth = 0;
/*  8308 */     gridBagConstraints.fill = 2;
/*  8309 */     gridBagConstraints.insets = new Insets(4, 0, 4, 0);
/*  8310 */     this.pnlEquipInfo.add(this.jSeparator13, gridBagConstraints);
/*       */     
/*  8312 */     this.jLabel28.setText("Ammo");
/*  8313 */     gridBagConstraints = new GridBagConstraints();
/*  8314 */     gridBagConstraints.gridx = 5;
/*  8315 */     gridBagConstraints.gridy = 0;
/*  8316 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8317 */     this.pnlEquipInfo.add(this.jLabel28, gridBagConstraints);
/*       */     
/*  8319 */     this.jLabel29.setText("Tonnage");
/*  8320 */     gridBagConstraints = new GridBagConstraints();
/*  8321 */     gridBagConstraints.gridx = 6;
/*  8322 */     gridBagConstraints.gridy = 0;
/*  8323 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8324 */     this.pnlEquipInfo.add(this.jLabel29, gridBagConstraints);
/*       */     
/*  8326 */     this.jLabel30.setText("Crits");
/*  8327 */     gridBagConstraints = new GridBagConstraints();
/*  8328 */     gridBagConstraints.gridx = 7;
/*  8329 */     gridBagConstraints.gridy = 0;
/*  8330 */     gridBagConstraints.insets = new Insets(4, 3, 0, 3);
/*  8331 */     this.pnlEquipInfo.add(this.jLabel30, gridBagConstraints);
/*       */     
/*  8333 */     this.jLabel31.setText("Specials");
/*  8334 */     gridBagConstraints = new GridBagConstraints();
/*  8335 */     gridBagConstraints.gridx = 8;
/*  8336 */     gridBagConstraints.gridy = 0;
/*  8337 */     gridBagConstraints.insets = new Insets(4, 3, 0, 0);
/*  8338 */     this.pnlEquipInfo.add(this.jLabel31, gridBagConstraints);
/*  8339 */     gridBagConstraints = new GridBagConstraints();
/*  8340 */     gridBagConstraints.gridx = 5;
/*  8341 */     gridBagConstraints.gridy = 1;
/*  8342 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8343 */     this.pnlEquipInfo.add(this.lblInfoAmmo, gridBagConstraints);
/*  8344 */     gridBagConstraints = new GridBagConstraints();
/*  8345 */     gridBagConstraints.gridx = 6;
/*  8346 */     gridBagConstraints.gridy = 1;
/*  8347 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8348 */     this.pnlEquipInfo.add(this.lblInfoTonnage, gridBagConstraints);
/*  8349 */     gridBagConstraints = new GridBagConstraints();
/*  8350 */     gridBagConstraints.gridx = 7;
/*  8351 */     gridBagConstraints.gridy = 1;
/*  8352 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8353 */     this.pnlEquipInfo.add(this.lblInfoCrits, gridBagConstraints);
/*  8354 */     gridBagConstraints = new GridBagConstraints();
/*  8355 */     gridBagConstraints.gridx = 8;
/*  8356 */     gridBagConstraints.gridy = 1;
/*  8357 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/*  8358 */     this.pnlEquipInfo.add(this.lblInfoSpecials, gridBagConstraints);
/*       */     
/*  8360 */     this.jSeparator14.setBorder(BorderFactory.createEtchedBorder());
/*  8361 */     gridBagConstraints = new GridBagConstraints();
/*  8362 */     gridBagConstraints.gridx = 0;
/*  8363 */     gridBagConstraints.gridy = 6;
/*  8364 */     gridBagConstraints.gridwidth = 0;
/*  8365 */     gridBagConstraints.fill = 2;
/*  8366 */     gridBagConstraints.insets = new Insets(4, 0, 4, 0);
/*  8367 */     this.pnlEquipInfo.add(this.jSeparator14, gridBagConstraints);
/*       */     
/*  8369 */     this.jLabel32.setText("Cost");
/*  8370 */     gridBagConstraints = new GridBagConstraints();
/*  8371 */     gridBagConstraints.gridx = 5;
/*  8372 */     gridBagConstraints.gridy = 4;
/*  8373 */     gridBagConstraints.gridwidth = 2;
/*  8374 */     gridBagConstraints.anchor = 13;
/*  8375 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8376 */     this.pnlEquipInfo.add(this.jLabel32, gridBagConstraints);
/*  8377 */     gridBagConstraints = new GridBagConstraints();
/*  8378 */     gridBagConstraints.gridx = 7;
/*  8379 */     gridBagConstraints.gridy = 4;
/*  8380 */     gridBagConstraints.gridwidth = 2;
/*  8381 */     gridBagConstraints.anchor = 17;
/*  8382 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/*  8383 */     this.pnlEquipInfo.add(this.lblInfoCost, gridBagConstraints);
/*       */     
/*  8385 */     this.jLabel34.setText("BV");
/*  8386 */     gridBagConstraints = new GridBagConstraints();
/*  8387 */     gridBagConstraints.gridx = 5;
/*  8388 */     gridBagConstraints.gridy = 5;
/*  8389 */     gridBagConstraints.gridwidth = 2;
/*  8390 */     gridBagConstraints.anchor = 13;
/*  8391 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8392 */     this.pnlEquipInfo.add(this.jLabel34, gridBagConstraints);
/*  8393 */     gridBagConstraints = new GridBagConstraints();
/*  8394 */     gridBagConstraints.gridx = 7;
/*  8395 */     gridBagConstraints.gridy = 5;
/*  8396 */     gridBagConstraints.gridwidth = 2;
/*  8397 */     gridBagConstraints.anchor = 17;
/*  8398 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/*  8399 */     this.pnlEquipInfo.add(this.lblInfoBV, gridBagConstraints);
/*       */     
/*  8401 */     this.jLabel33.setText("Mounting Restrictions");
/*  8402 */     gridBagConstraints = new GridBagConstraints();
/*  8403 */     gridBagConstraints.gridwidth = 2;
/*  8404 */     gridBagConstraints.anchor = 17;
/*  8405 */     gridBagConstraints.insets = new Insets(0, 0, 4, 3);
/*  8406 */     this.pnlEquipInfo.add(this.jLabel33, gridBagConstraints);
/*  8407 */     gridBagConstraints = new GridBagConstraints();
/*  8408 */     gridBagConstraints.gridx = 2;
/*  8409 */     gridBagConstraints.gridy = 7;
/*  8410 */     gridBagConstraints.gridwidth = 7;
/*  8411 */     gridBagConstraints.anchor = 17;
/*  8412 */     gridBagConstraints.insets = new Insets(0, 3, 4, 0);
/*  8413 */     this.pnlEquipInfo.add(this.lblInfoMountRestrict, gridBagConstraints);
/*       */     
/*  8415 */     this.jLabel55.setText("Rules Level");
/*  8416 */     gridBagConstraints = new GridBagConstraints();
/*  8417 */     gridBagConstraints.gridx = 5;
/*  8418 */     gridBagConstraints.gridy = 3;
/*  8419 */     gridBagConstraints.gridwidth = 2;
/*  8420 */     gridBagConstraints.anchor = 13;
/*  8421 */     gridBagConstraints.insets = new Insets(0, 3, 0, 3);
/*  8422 */     this.pnlEquipInfo.add(this.jLabel55, gridBagConstraints);
/*  8423 */     gridBagConstraints = new GridBagConstraints();
/*  8424 */     gridBagConstraints.gridx = 7;
/*  8425 */     gridBagConstraints.gridy = 3;
/*  8426 */     gridBagConstraints.gridwidth = 2;
/*  8427 */     gridBagConstraints.anchor = 17;
/*  8428 */     gridBagConstraints.insets = new Insets(0, 3, 0, 0);
/*  8429 */     this.pnlEquipInfo.add(this.lblInfoRulesLevel, gridBagConstraints);
/*       */     
/*  8431 */     this.pnlEquipment.add(this.pnlEquipInfo, new AbsoluteConstraints(17, 325, 710, -1));
/*       */     
/*  8433 */     this.tbpMainTabPane.addTab("Equipment", this.pnlEquipment);
/*       */     
/*  8435 */     this.pnlCriticals.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/*  8437 */     this.pnlHDCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Head", 2, 0));
/*  8438 */     this.pnlHDCrits.setMaximumSize(new Dimension(116, 120));
/*  8439 */     this.pnlHDCrits.setMinimumSize(new Dimension(116, 120));
/*  8440 */     this.pnlHDCrits.setLayout(new GridBagLayout());
/*       */     
/*  8442 */     this.chkHDTurret.setText("Turret");
/*  8443 */     this.chkHDTurret.setEnabled(false);
/*  8444 */     this.chkHDTurret.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8446 */         frmMain.this.chkHDTurretActionPerformed(evt);
/*       */       }
/*  8448 */     });
/*  8449 */     gridBagConstraints = new GridBagConstraints();
/*  8450 */     gridBagConstraints.gridx = 0;
/*  8451 */     gridBagConstraints.gridy = 0;
/*  8452 */     gridBagConstraints.anchor = 17;
/*  8453 */     this.pnlHDCrits.add(this.chkHDTurret, gridBagConstraints);
/*       */     
/*  8455 */     this.chkHDCASE2.setText("C.A.S.E. II");
/*  8456 */     this.chkHDCASE2.setEnabled(false);
/*  8457 */     this.chkHDCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8459 */         frmMain.this.chkHDCASE2ActionPerformed(evt);
/*       */       }
/*  8461 */     });
/*  8462 */     gridBagConstraints = new GridBagConstraints();
/*  8463 */     gridBagConstraints.gridx = 0;
/*  8464 */     gridBagConstraints.gridy = 2;
/*  8465 */     gridBagConstraints.anchor = 17;
/*  8466 */     this.pnlHDCrits.add(this.chkHDCASE2, gridBagConstraints);
/*       */     
/*  8468 */     this.jScrollPane10.setPreferredSize(new Dimension(105, 87));
/*       */     
/*  8470 */     this.lstHDCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8471 */     this.lstHDCrits.setModel(new AbstractListModel() {
/*  8472 */       String[] strings = { "Head", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" };
/*  8473 */       public int getSize() { return this.strings.length; }
/*  8474 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8475 */     });
/*  8476 */     this.lstHDCrits.setDragEnabled(true);
/*  8477 */     this.lstHDCrits.setMaximumSize(new Dimension(98, 50));
/*  8478 */     this.lstHDCrits.setMinimumSize(new Dimension(98, 50));
/*  8479 */     this.lstHDCrits.setPreferredSize(new Dimension(98, 50));
/*  8480 */     this.lstHDCrits.setVisibleRowCount(6);
/*  8481 */     this.lstHDCrits.setTransferHandler(new thHDTransferHandler(this, this.CurMech));
/*  8482 */     this.lstHDCrits.setDropMode(javax.swing.DropMode.ON);
/*  8483 */     MouseListener mlHDCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8485 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8486 */           int index = frmMain.this.lstHDCrits.locationToIndex(e.getPoint());
/*  8487 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetHDCrits();
/*  8488 */           if (!a[index].LocationLocked()) {
/*  8489 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8490 */               frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8492 */               frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8495 */           frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8499 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8500 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetHDCrits();
/*  8501 */           int index = frmMain.this.lstHDCrits.locationToIndex(e.getPoint());
/*  8502 */           frmMain.this.CurItem = a[index];
/*  8503 */           frmMain.this.CurLocation = 0;
/*  8504 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8505 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8509 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8510 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetHDCrits();
/*  8511 */           int index = frmMain.this.lstHDCrits.locationToIndex(e.getPoint());
/*  8512 */           frmMain.this.CurItem = a[index];
/*  8513 */           frmMain.this.CurLocation = 0;
/*  8514 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8515 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8517 */           int index = frmMain.this.lstHDCrits.locationToIndex(e.getPoint());
/*  8518 */           frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetHDCrits()[index];
/*  8519 */           frmMain.this.CurLocation = 0;
/*       */         }
/*       */       }
/*  8522 */     };
/*  8523 */     this.lstHDCrits.addMouseListener(mlHDCrits);
/*  8524 */     this.lstHDCrits.setCellRenderer(this.Mechrender);
/*  8525 */     this.jScrollPane10.setViewportView(this.lstHDCrits);
/*       */     
/*  8527 */     gridBagConstraints = new GridBagConstraints();
/*  8528 */     gridBagConstraints.gridx = 0;
/*  8529 */     gridBagConstraints.gridy = 1;
/*  8530 */     gridBagConstraints.fill = 1;
/*  8531 */     this.pnlHDCrits.add(this.jScrollPane10, gridBagConstraints);
/*       */     
/*  8533 */     this.pnlCriticals.add(this.pnlHDCrits, new AbsoluteConstraints(240, 10, 117, 165));
/*       */     
/*  8535 */     this.pnlCTCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Center Torso", 2, 0));
/*  8536 */     this.pnlCTCrits.setMaximumSize(new Dimension(114, 233));
/*  8537 */     this.pnlCTCrits.setMinimumSize(new Dimension(114, 233));
/*  8538 */     this.pnlCTCrits.setLayout(new GridBagLayout());
/*       */     
/*  8540 */     this.jScrollPane11.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8542 */     this.lstCTCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8543 */     this.lstCTCrits.setModel(new AbstractListModel() {
/*  8544 */       String[] strings = { "Center Torso", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8545 */       public int getSize() { return this.strings.length; }
/*  8546 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8547 */     });
/*  8548 */     this.lstCTCrits.setDragEnabled(true);
/*  8549 */     this.lstCTCrits.setMaximumSize(new Dimension(98, 50));
/*  8550 */     this.lstCTCrits.setMinimumSize(new Dimension(98, 50));
/*  8551 */     this.lstCTCrits.setPreferredSize(new Dimension(98, 50));
/*  8552 */     this.lstCTCrits.setVisibleRowCount(12);
/*  8553 */     this.lstCTCrits.setTransferHandler(new thCTTransferHandler(this, this.CurMech));
/*  8554 */     this.lstCTCrits.setDropMode(javax.swing.DropMode.ON);
/*  8555 */     MouseListener mlCTCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8557 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8558 */           int index = frmMain.this.lstCTCrits.locationToIndex(e.getPoint());
/*  8559 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetCTCrits();
/*  8560 */           if (!a[index].LocationLocked()) {
/*  8561 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8562 */               frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8564 */               frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8567 */           frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8571 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8572 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetCTCrits();
/*  8573 */           int index = frmMain.this.lstCTCrits.locationToIndex(e.getPoint());
/*  8574 */           frmMain.this.CurItem = a[index];
/*  8575 */           frmMain.this.CurLocation = 1;
/*  8576 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8577 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8581 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8582 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetCTCrits();
/*  8583 */           int index = frmMain.this.lstCTCrits.locationToIndex(e.getPoint());
/*  8584 */           frmMain.this.CurItem = a[index];
/*  8585 */           frmMain.this.CurLocation = 1;
/*  8586 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8587 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8589 */           int index = frmMain.this.lstCTCrits.locationToIndex(e.getPoint());
/*  8590 */           frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetCTCrits()[index];
/*  8591 */           frmMain.this.CurLocation = 1;
/*       */         }
/*       */       }
/*  8594 */     };
/*  8595 */     this.lstCTCrits.addMouseListener(mlCTCrits);
/*  8596 */     this.lstCTCrits.setCellRenderer(this.Mechrender);
/*  8597 */     this.jScrollPane11.setViewportView(this.lstCTCrits);
/*       */     
/*  8599 */     this.pnlCTCrits.add(this.jScrollPane11, new GridBagConstraints());
/*       */     
/*  8601 */     this.chkCTCASE.setText("C.A.S.E.");
/*  8602 */     this.chkCTCASE.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8604 */         frmMain.this.chkCTCASEActionPerformed(evt);
/*       */       }
/*  8606 */     });
/*  8607 */     gridBagConstraints = new GridBagConstraints();
/*  8608 */     gridBagConstraints.gridx = 0;
/*  8609 */     gridBagConstraints.gridy = 1;
/*  8610 */     gridBagConstraints.anchor = 17;
/*  8611 */     this.pnlCTCrits.add(this.chkCTCASE, gridBagConstraints);
/*       */     
/*  8613 */     this.chkCTCASE2.setText("C.A.S.E. II");
/*  8614 */     this.chkCTCASE2.setEnabled(false);
/*  8615 */     this.chkCTCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8617 */         frmMain.this.chkCTCASE2ActionPerformed(evt);
/*       */       }
/*  8619 */     });
/*  8620 */     gridBagConstraints = new GridBagConstraints();
/*  8621 */     gridBagConstraints.gridx = 0;
/*  8622 */     gridBagConstraints.gridy = 2;
/*  8623 */     gridBagConstraints.anchor = 17;
/*  8624 */     this.pnlCTCrits.add(this.chkCTCASE2, gridBagConstraints);
/*       */     
/*  8626 */     this.pnlCriticals.add(this.pnlCTCrits, new AbsoluteConstraints(240, 180, 117, -1));
/*       */     
/*  8628 */     this.pnlLTCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Left Torso", 2, 0));
/*  8629 */     this.pnlLTCrits.setMaximumSize(new Dimension(114, 235));
/*  8630 */     this.pnlLTCrits.setMinimumSize(new Dimension(114, 235));
/*  8631 */     this.pnlLTCrits.setPreferredSize(new Dimension(257, 232));
/*  8632 */     this.pnlLTCrits.setLayout(new GridBagLayout());
/*       */     
/*  8634 */     this.chkLTCASE.setText("C.A.S.E.");
/*  8635 */     this.chkLTCASE.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8637 */         frmMain.this.chkLTCASEActionPerformed(evt);
/*       */       }
/*  8639 */     });
/*  8640 */     gridBagConstraints = new GridBagConstraints();
/*  8641 */     gridBagConstraints.gridx = 0;
/*  8642 */     gridBagConstraints.gridy = 2;
/*  8643 */     gridBagConstraints.anchor = 17;
/*  8644 */     this.pnlLTCrits.add(this.chkLTCASE, gridBagConstraints);
/*       */     
/*  8646 */     this.jScrollPane12.setMinimumSize(new Dimension(105, 183));
/*  8647 */     this.jScrollPane12.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8649 */     this.lstLTCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8650 */     this.lstLTCrits.setModel(new AbstractListModel() {
/*  8651 */       String[] strings = { "Left Torso", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8652 */       public int getSize() { return this.strings.length; }
/*  8653 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8654 */     });
/*  8655 */     this.lstLTCrits.setDragEnabled(true);
/*  8656 */     this.lstLTCrits.setMaximumSize(new Dimension(98, 50));
/*  8657 */     this.lstLTCrits.setMinimumSize(new Dimension(98, 50));
/*  8658 */     this.lstLTCrits.setPreferredSize(new Dimension(98, 50));
/*  8659 */     this.lstLTCrits.setVisibleRowCount(12);
/*  8660 */     this.lstLTCrits.setTransferHandler(new thLTTransferHandler(this, this.CurMech));
/*  8661 */     this.lstLTCrits.setDropMode(javax.swing.DropMode.ON);
/*  8662 */     MouseListener mlLTCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8664 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8665 */           int index = frmMain.this.lstLTCrits.locationToIndex(e.getPoint());
/*  8666 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLTCrits();
/*  8667 */           if (!a[index].LocationLocked()) {
/*  8668 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8669 */               frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8671 */               frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8674 */           frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8678 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8679 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLTCrits();
/*  8680 */           int index = frmMain.this.lstLTCrits.locationToIndex(e.getPoint());
/*  8681 */           frmMain.this.CurItem = a[index];
/*  8682 */           frmMain.this.CurLocation = 2;
/*  8683 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8684 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8688 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8689 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLTCrits();
/*  8690 */           int index = frmMain.this.lstLTCrits.locationToIndex(e.getPoint());
/*  8691 */           frmMain.this.CurItem = a[index];
/*  8692 */           frmMain.this.CurLocation = 2;
/*  8693 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8694 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8696 */           int index = frmMain.this.lstLTCrits.locationToIndex(e.getPoint());
/*  8697 */           frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetLTCrits()[index];
/*  8698 */           frmMain.this.CurLocation = 2;
/*       */         }
/*       */       }
/*  8701 */     };
/*  8702 */     this.lstLTCrits.addMouseListener(mlLTCrits);
/*  8703 */     this.lstLTCrits.setCellRenderer(this.Mechrender);
/*  8704 */     this.jScrollPane12.setViewportView(this.lstLTCrits);
/*       */     
/*  8706 */     gridBagConstraints = new GridBagConstraints();
/*  8707 */     gridBagConstraints.gridx = 0;
/*  8708 */     gridBagConstraints.gridy = 1;
/*  8709 */     this.pnlLTCrits.add(this.jScrollPane12, gridBagConstraints);
/*       */     
/*  8711 */     this.chkLTCASE2.setText("C.A.S.E. II");
/*  8712 */     this.chkLTCASE2.setEnabled(false);
/*  8713 */     this.chkLTCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8715 */         frmMain.this.chkLTCASE2ActionPerformed(evt);
/*       */       }
/*  8717 */     });
/*  8718 */     gridBagConstraints = new GridBagConstraints();
/*  8719 */     gridBagConstraints.gridx = 0;
/*  8720 */     gridBagConstraints.gridy = 3;
/*  8721 */     gridBagConstraints.anchor = 17;
/*  8722 */     this.pnlLTCrits.add(this.chkLTCASE2, gridBagConstraints);
/*       */     
/*  8724 */     this.chkLTTurret.setText("Turret");
/*  8725 */     this.chkLTTurret.setEnabled(false);
/*  8726 */     this.chkLTTurret.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8728 */         frmMain.this.chkLTTurretActionPerformed(evt);
/*       */       }
/*  8730 */     });
/*  8731 */     gridBagConstraints = new GridBagConstraints();
/*  8732 */     gridBagConstraints.gridx = 0;
/*  8733 */     gridBagConstraints.gridy = 0;
/*  8734 */     gridBagConstraints.anchor = 17;
/*  8735 */     this.pnlLTCrits.add(this.chkLTTurret, gridBagConstraints);
/*       */     
/*  8737 */     this.pnlCriticals.add(this.pnlLTCrits, new AbsoluteConstraints(125, 25, 117, 270));
/*       */     
/*  8739 */     this.pnlRTCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Right Torso", 2, 0));
/*  8740 */     this.pnlRTCrits.setMaximumSize(new Dimension(114, 233));
/*  8741 */     this.pnlRTCrits.setMinimumSize(new Dimension(114, 233));
/*  8742 */     this.pnlRTCrits.setLayout(new GridBagLayout());
/*       */     
/*  8744 */     this.jScrollPane13.setMinimumSize(new Dimension(105, 183));
/*  8745 */     this.jScrollPane13.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8747 */     this.lstRTCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8748 */     this.lstRTCrits.setModel(new AbstractListModel() {
/*  8749 */       String[] strings = { "Right Torso", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8750 */       public int getSize() { return this.strings.length; }
/*  8751 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8752 */     });
/*  8753 */     this.lstRTCrits.setDragEnabled(true);
/*  8754 */     this.lstRTCrits.setMaximumSize(new Dimension(98, 50));
/*  8755 */     this.lstRTCrits.setMinimumSize(new Dimension(98, 50));
/*  8756 */     this.lstRTCrits.setPreferredSize(new Dimension(98, 50));
/*  8757 */     this.lstRTCrits.setVisibleRowCount(12);
/*  8758 */     this.lstRTCrits.setTransferHandler(new thRTTransferHandler(this, this.CurMech));
/*  8759 */     this.lstRTCrits.setDropMode(javax.swing.DropMode.ON);
/*  8760 */     MouseListener mlRTCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8762 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8763 */           int index = frmMain.this.lstRTCrits.locationToIndex(e.getPoint());
/*  8764 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRTCrits();
/*  8765 */           if (!a[index].LocationLocked()) {
/*  8766 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8767 */               frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8769 */               frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8772 */           frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8776 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8777 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRTCrits();
/*  8778 */           int index = frmMain.this.lstRTCrits.locationToIndex(e.getPoint());
/*  8779 */           frmMain.this.CurItem = a[index];
/*  8780 */           frmMain.this.CurLocation = 3;
/*  8781 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8782 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8786 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8787 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRTCrits();
/*  8788 */           int index = frmMain.this.lstRTCrits.locationToIndex(e.getPoint());
/*  8789 */           frmMain.this.CurItem = a[index];
/*  8790 */           frmMain.this.CurLocation = 3;
/*  8791 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8792 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8794 */           int index = frmMain.this.lstRTCrits.locationToIndex(e.getPoint());
/*  8795 */           frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetRTCrits()[index];
/*  8796 */           frmMain.this.CurLocation = 3;
/*       */         }
/*       */       }
/*  8799 */     };
/*  8800 */     this.lstRTCrits.addMouseListener(mlRTCrits);
/*  8801 */     this.lstRTCrits.setCellRenderer(this.Mechrender);
/*  8802 */     this.jScrollPane13.setViewportView(this.lstRTCrits);
/*       */     
/*  8804 */     gridBagConstraints = new GridBagConstraints();
/*  8805 */     gridBagConstraints.gridx = 0;
/*  8806 */     gridBagConstraints.gridy = 1;
/*  8807 */     this.pnlRTCrits.add(this.jScrollPane13, gridBagConstraints);
/*       */     
/*  8809 */     this.chkRTCASE.setText("C.A.S.E.");
/*  8810 */     this.chkRTCASE.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8812 */         frmMain.this.chkRTCASEActionPerformed(evt);
/*       */       }
/*  8814 */     });
/*  8815 */     gridBagConstraints = new GridBagConstraints();
/*  8816 */     gridBagConstraints.gridx = 0;
/*  8817 */     gridBagConstraints.gridy = 2;
/*  8818 */     gridBagConstraints.anchor = 17;
/*  8819 */     this.pnlRTCrits.add(this.chkRTCASE, gridBagConstraints);
/*       */     
/*  8821 */     this.chkRTCASE2.setText("C.A.S.E. II");
/*  8822 */     this.chkRTCASE2.setEnabled(false);
/*  8823 */     this.chkRTCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8825 */         frmMain.this.chkRTCASE2ActionPerformed(evt);
/*       */       }
/*  8827 */     });
/*  8828 */     gridBagConstraints = new GridBagConstraints();
/*  8829 */     gridBagConstraints.gridx = 0;
/*  8830 */     gridBagConstraints.gridy = 3;
/*  8831 */     gridBagConstraints.anchor = 17;
/*  8832 */     this.pnlRTCrits.add(this.chkRTCASE2, gridBagConstraints);
/*       */     
/*  8834 */     this.chkRTTurret.setText("Turret");
/*  8835 */     this.chkRTTurret.setEnabled(false);
/*  8836 */     this.chkRTTurret.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8838 */         frmMain.this.chkRTTurretActionPerformed(evt);
/*       */       }
/*  8840 */     });
/*  8841 */     gridBagConstraints = new GridBagConstraints();
/*  8842 */     gridBagConstraints.gridx = 0;
/*  8843 */     gridBagConstraints.gridy = 0;
/*  8844 */     gridBagConstraints.anchor = 17;
/*  8845 */     this.pnlRTCrits.add(this.chkRTTurret, gridBagConstraints);
/*       */     
/*  8847 */     this.pnlCriticals.add(this.pnlRTCrits, new AbsoluteConstraints(355, 25, 117, 270));
/*       */     
/*  8849 */     this.pnlLACrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Left Arm", 2, 0));
/*  8850 */     this.pnlLACrits.setMaximumSize(new Dimension(114, 256));
/*  8851 */     this.pnlLACrits.setMinimumSize(new Dimension(114, 256));
/*  8852 */     this.pnlLACrits.setLayout(new GridBagLayout());
/*       */     
/*  8854 */     this.scrLACrits.setMinimumSize(new Dimension(105, 87));
/*  8855 */     this.scrLACrits.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8857 */     this.lstLACrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8858 */     this.lstLACrits.setModel(new AbstractListModel() {
/*  8859 */       String[] strings = { "Left Arm", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8860 */       public int getSize() { return this.strings.length; }
/*  8861 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8862 */     });
/*  8863 */     this.lstLACrits.setDragEnabled(true);
/*  8864 */     this.lstLACrits.setMaximumSize(new Dimension(98, 50));
/*  8865 */     this.lstLACrits.setMinimumSize(new Dimension(98, 50));
/*  8866 */     this.lstLACrits.setPreferredSize(new Dimension(98, 50));
/*  8867 */     this.lstLACrits.setVisibleRowCount(12);
/*  8868 */     this.lstLACrits.setTransferHandler(new thLATransferHandler(this, this.CurMech));
/*  8869 */     this.lstLACrits.setDropMode(javax.swing.DropMode.ON);
/*  8870 */     MouseListener mlLACrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8872 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8873 */           int index = frmMain.this.lstLACrits.locationToIndex(e.getPoint());
/*  8874 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLACrits();
/*  8875 */           if (!a[index].LocationLocked()) {
/*  8876 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8877 */               frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  8879 */               frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  8882 */           frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  8886 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8887 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLACrits();
/*  8888 */           int index = frmMain.this.lstLACrits.locationToIndex(e.getPoint());
/*  8889 */           frmMain.this.CurItem = a[index];
/*  8890 */           frmMain.this.CurLocation = 4;
/*  8891 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8892 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  8896 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  8897 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLACrits();
/*  8898 */           int index = frmMain.this.lstLACrits.locationToIndex(e.getPoint());
/*  8899 */           frmMain.this.CurItem = a[index];
/*  8900 */           frmMain.this.CurLocation = 4;
/*  8901 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  8902 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  8904 */           int index = frmMain.this.lstLACrits.locationToIndex(e.getPoint());
/*  8905 */           frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetLACrits()[index];
/*  8906 */           frmMain.this.CurLocation = 4;
/*       */         }
/*       */       }
/*  8909 */     };
/*  8910 */     this.lstLACrits.addMouseListener(mlLACrits);
/*  8911 */     this.lstLACrits.setCellRenderer(this.Mechrender);
/*  8912 */     this.scrLACrits.setViewportView(this.lstLACrits);
/*       */     
/*  8914 */     gridBagConstraints = new GridBagConstraints();
/*  8915 */     gridBagConstraints.gridx = 0;
/*  8916 */     gridBagConstraints.gridy = 1;
/*  8917 */     this.pnlLACrits.add(this.scrLACrits, gridBagConstraints);
/*       */     
/*  8919 */     this.chkLALowerArm.setSelected(true);
/*  8920 */     this.chkLALowerArm.setText("Lower Arm");
/*  8921 */     this.chkLALowerArm.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8923 */         frmMain.this.chkLALowerArmActionPerformed(evt);
/*       */       }
/*  8925 */     });
/*  8926 */     gridBagConstraints = new GridBagConstraints();
/*  8927 */     gridBagConstraints.gridx = 0;
/*  8928 */     gridBagConstraints.gridy = 2;
/*  8929 */     gridBagConstraints.anchor = 17;
/*  8930 */     this.pnlLACrits.add(this.chkLALowerArm, gridBagConstraints);
/*       */     
/*  8932 */     this.chkLAHand.setSelected(true);
/*  8933 */     this.chkLAHand.setText("Hand");
/*  8934 */     this.chkLAHand.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8936 */         frmMain.this.chkLAHandActionPerformed(evt);
/*       */       }
/*  8938 */     });
/*  8939 */     gridBagConstraints = new GridBagConstraints();
/*  8940 */     gridBagConstraints.gridx = 0;
/*  8941 */     gridBagConstraints.gridy = 3;
/*  8942 */     gridBagConstraints.anchor = 17;
/*  8943 */     this.pnlLACrits.add(this.chkLAHand, gridBagConstraints);
/*       */     
/*  8945 */     this.chkLACASE2.setText("C.A.S.E. II");
/*  8946 */     this.chkLACASE2.setEnabled(false);
/*  8947 */     this.chkLACASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8949 */         frmMain.this.chkLACASE2ActionPerformed(evt);
/*       */       }
/*  8951 */     });
/*  8952 */     gridBagConstraints = new GridBagConstraints();
/*  8953 */     gridBagConstraints.gridx = 0;
/*  8954 */     gridBagConstraints.gridy = 4;
/*  8955 */     gridBagConstraints.anchor = 17;
/*  8956 */     this.pnlLACrits.add(this.chkLACASE2, gridBagConstraints);
/*       */     
/*  8958 */     this.chkLAAES.setText("A.E.S.");
/*  8959 */     this.chkLAAES.setEnabled(false);
/*  8960 */     this.chkLAAES.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  8962 */         frmMain.this.chkLAAESActionPerformed(evt);
/*       */       }
/*  8964 */     });
/*  8965 */     gridBagConstraints = new GridBagConstraints();
/*  8966 */     gridBagConstraints.anchor = 17;
/*  8967 */     this.pnlLACrits.add(this.chkLAAES, gridBagConstraints);
/*       */     
/*  8969 */     this.pnlCriticals.add(this.pnlLACrits, new AbsoluteConstraints(10, 35, 117, -1));
/*       */     
/*  8971 */     this.pnlRACrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Right Arm", 2, 0));
/*  8972 */     this.pnlRACrits.setMaximumSize(new Dimension(114, 256));
/*  8973 */     this.pnlRACrits.setMinimumSize(new Dimension(114, 256));
/*  8974 */     this.pnlRACrits.setLayout(new GridBagLayout());
/*       */     
/*  8976 */     this.scrRACrits.setMinimumSize(new Dimension(105, 87));
/*  8977 */     this.scrRACrits.setPreferredSize(new Dimension(105, 170));
/*       */     
/*  8979 */     this.lstRACrits.setFont(Print.PrintConsts.BaseCritFont);
/*  8980 */     this.lstRACrits.setModel(new AbstractListModel() {
/*  8981 */       String[] strings = { "Right Arm", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12" };
/*  8982 */       public int getSize() { return this.strings.length; }
/*  8983 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  8984 */     });
/*  8985 */     this.lstRACrits.setDragEnabled(true);
/*  8986 */     this.lstRACrits.setMaximumSize(new Dimension(98, 50));
/*  8987 */     this.lstRACrits.setMinimumSize(new Dimension(98, 50));
/*  8988 */     this.lstRACrits.setPreferredSize(new Dimension(98, 50));
/*  8989 */     this.lstRACrits.setVisibleRowCount(12);
/*  8990 */     this.lstRACrits.setTransferHandler(new thRATransferHandler(this, this.CurMech));
/*  8991 */     this.lstRACrits.setDropMode(javax.swing.DropMode.ON);
/*  8992 */     MouseListener mlRACrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  8994 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  8995 */           int index = frmMain.this.lstRACrits.locationToIndex(e.getPoint());
/*  8996 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRACrits();
/*  8997 */           if (!a[index].LocationLocked()) {
/*  8998 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  8999 */               frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  9001 */               frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  9004 */           frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  9008 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  9009 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRACrits();
/*  9010 */           int index = frmMain.this.lstRACrits.locationToIndex(e.getPoint());
/*  9011 */           frmMain.this.CurItem = a[index];
/*  9012 */           frmMain.this.CurLocation = 5;
/*  9013 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  9014 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  9018 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  9019 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRACrits();
/*  9020 */           int index = frmMain.this.lstRACrits.locationToIndex(e.getPoint());
/*  9021 */           frmMain.this.CurItem = a[index];
/*  9022 */           frmMain.this.CurLocation = 5;
/*  9023 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  9024 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  9026 */           int index = frmMain.this.lstRACrits.locationToIndex(e.getPoint());
/*  9027 */           frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetRACrits()[index];
/*  9028 */           frmMain.this.CurLocation = 5;
/*       */         }
/*       */       }
/*  9031 */     };
/*  9032 */     this.lstRACrits.addMouseListener(mlRACrits);
/*  9033 */     this.lstRACrits.setCellRenderer(this.Mechrender);
/*  9034 */     this.scrRACrits.setViewportView(this.lstRACrits);
/*       */     
/*  9036 */     gridBagConstraints = new GridBagConstraints();
/*  9037 */     gridBagConstraints.gridx = 0;
/*  9038 */     gridBagConstraints.gridy = 1;
/*  9039 */     this.pnlRACrits.add(this.scrRACrits, gridBagConstraints);
/*       */     
/*  9041 */     this.chkRALowerArm.setSelected(true);
/*  9042 */     this.chkRALowerArm.setText("Lower Arm");
/*  9043 */     this.chkRALowerArm.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9045 */         frmMain.this.chkRALowerArmActionPerformed(evt);
/*       */       }
/*  9047 */     });
/*  9048 */     gridBagConstraints = new GridBagConstraints();
/*  9049 */     gridBagConstraints.gridx = 0;
/*  9050 */     gridBagConstraints.gridy = 2;
/*  9051 */     gridBagConstraints.anchor = 17;
/*  9052 */     this.pnlRACrits.add(this.chkRALowerArm, gridBagConstraints);
/*       */     
/*  9054 */     this.chkRAHand.setSelected(true);
/*  9055 */     this.chkRAHand.setText("Hand");
/*  9056 */     this.chkRAHand.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9058 */         frmMain.this.chkRAHandActionPerformed(evt);
/*       */       }
/*  9060 */     });
/*  9061 */     gridBagConstraints = new GridBagConstraints();
/*  9062 */     gridBagConstraints.gridx = 0;
/*  9063 */     gridBagConstraints.gridy = 3;
/*  9064 */     gridBagConstraints.anchor = 17;
/*  9065 */     this.pnlRACrits.add(this.chkRAHand, gridBagConstraints);
/*       */     
/*  9067 */     this.chkRACASE2.setText("C.A.S.E. II");
/*  9068 */     this.chkRACASE2.setEnabled(false);
/*  9069 */     this.chkRACASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9071 */         frmMain.this.chkRACASE2ActionPerformed(evt);
/*       */       }
/*  9073 */     });
/*  9074 */     gridBagConstraints = new GridBagConstraints();
/*  9075 */     gridBagConstraints.gridx = 0;
/*  9076 */     gridBagConstraints.gridy = 4;
/*  9077 */     gridBagConstraints.anchor = 17;
/*  9078 */     this.pnlRACrits.add(this.chkRACASE2, gridBagConstraints);
/*       */     
/*  9080 */     this.chkRAAES.setText("A.E.S.");
/*  9081 */     this.chkRAAES.setEnabled(false);
/*  9082 */     this.chkRAAES.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9084 */         frmMain.this.chkRAAESActionPerformed(evt);
/*       */       }
/*  9086 */     });
/*  9087 */     gridBagConstraints = new GridBagConstraints();
/*  9088 */     gridBagConstraints.anchor = 17;
/*  9089 */     this.pnlRACrits.add(this.chkRAAES, gridBagConstraints);
/*       */     
/*  9091 */     this.pnlCriticals.add(this.pnlRACrits, new AbsoluteConstraints(470, 35, 117, -1));
/*       */     
/*  9093 */     this.pnlLLCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Left Leg", 2, 0));
/*  9094 */     this.pnlLLCrits.setMaximumSize(new Dimension(116, 120));
/*  9095 */     this.pnlLLCrits.setMinimumSize(new Dimension(116, 120));
/*  9096 */     this.pnlLLCrits.setLayout(new GridBagLayout());
/*       */     
/*  9098 */     this.jScrollPane16.setMinimumSize(new Dimension(105, 87));
/*  9099 */     this.jScrollPane16.setPreferredSize(new Dimension(105, 87));
/*       */     
/*  9101 */     this.lstLLCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  9102 */     this.lstLLCrits.setModel(new AbstractListModel() {
/*  9103 */       String[] strings = { "Left Leg", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" };
/*  9104 */       public int getSize() { return this.strings.length; }
/*  9105 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  9106 */     });
/*  9107 */     this.lstLLCrits.setDragEnabled(true);
/*  9108 */     this.lstLLCrits.setMaximumSize(new Dimension(98, 50));
/*  9109 */     this.lstLLCrits.setMinimumSize(new Dimension(98, 50));
/*  9110 */     this.lstLLCrits.setPreferredSize(new Dimension(98, 50));
/*  9111 */     this.lstLLCrits.setVisibleRowCount(6);
/*  9112 */     this.lstLLCrits.setTransferHandler(new thLLTransferHandler(this, this.CurMech));
/*  9113 */     this.lstLLCrits.setDropMode(javax.swing.DropMode.ON);
/*  9114 */     MouseListener mlLLCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  9116 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  9117 */           int index = frmMain.this.lstLLCrits.locationToIndex(e.getPoint());
/*  9118 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLLCrits();
/*  9119 */           if (!a[index].LocationLocked()) {
/*  9120 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  9121 */               frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  9123 */               frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  9126 */           frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  9130 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  9131 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLLCrits();
/*  9132 */           int index = frmMain.this.lstLLCrits.locationToIndex(e.getPoint());
/*  9133 */           frmMain.this.CurItem = a[index];
/*  9134 */           frmMain.this.CurLocation = 6;
/*  9135 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  9136 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  9140 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  9141 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLLCrits();
/*  9142 */           int index = frmMain.this.lstLLCrits.locationToIndex(e.getPoint());
/*  9143 */           frmMain.this.CurItem = a[index];
/*  9144 */           frmMain.this.CurLocation = 6;
/*  9145 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  9146 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  9148 */           int index = frmMain.this.lstLLCrits.locationToIndex(e.getPoint());
/*  9149 */           frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetLLCrits()[index];
/*  9150 */           frmMain.this.CurLocation = 6;
/*       */         }
/*       */       }
/*  9153 */     };
/*  9154 */     this.lstLLCrits.addMouseListener(mlLLCrits);
/*  9155 */     this.lstLLCrits.setCellRenderer(this.Mechrender);
/*  9156 */     this.jScrollPane16.setViewportView(this.lstLLCrits);
/*       */     
/*  9158 */     this.pnlLLCrits.add(this.jScrollPane16, new GridBagConstraints());
/*       */     
/*  9160 */     this.chkLLCASE2.setText("C.A.S.E. II");
/*  9161 */     this.chkLLCASE2.setEnabled(false);
/*  9162 */     this.chkLLCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9164 */         frmMain.this.chkLLCASE2ActionPerformed(evt);
/*       */       }
/*  9166 */     });
/*  9167 */     gridBagConstraints = new GridBagConstraints();
/*  9168 */     gridBagConstraints.gridx = 0;
/*  9169 */     gridBagConstraints.gridy = 1;
/*  9170 */     gridBagConstraints.anchor = 17;
/*  9171 */     this.pnlLLCrits.add(this.chkLLCASE2, gridBagConstraints);
/*       */     
/*  9173 */     this.pnlCriticals.add(this.pnlLLCrits, new AbsoluteConstraints(125, 320, 117, -1));
/*       */     
/*  9175 */     this.pnlRLCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Right Leg", 2, 0));
/*  9176 */     this.pnlRLCrits.setMaximumSize(new Dimension(116, 120));
/*  9177 */     this.pnlRLCrits.setMinimumSize(new Dimension(116, 120));
/*  9178 */     this.pnlRLCrits.setLayout(new GridBagLayout());
/*       */     
/*  9180 */     this.jScrollPane17.setMinimumSize(new Dimension(105, 87));
/*  9181 */     this.jScrollPane17.setPreferredSize(new Dimension(105, 87));
/*       */     
/*  9183 */     this.lstRLCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  9184 */     this.lstRLCrits.setModel(new AbstractListModel() {
/*  9185 */       String[] strings = { "Right Leg", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" };
/*  9186 */       public int getSize() { return this.strings.length; }
/*  9187 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  9188 */     });
/*  9189 */     this.lstRLCrits.setDragEnabled(true);
/*  9190 */     this.lstRLCrits.setMaximumSize(new Dimension(98, 50));
/*  9191 */     this.lstRLCrits.setMinimumSize(new Dimension(98, 50));
/*  9192 */     this.lstRLCrits.setPreferredSize(new Dimension(98, 50));
/*  9193 */     this.lstRLCrits.setVisibleRowCount(6);
/*  9194 */     this.lstRLCrits.setTransferHandler(new thRLTransferHandler(this, this.CurMech));
/*  9195 */     this.lstRLCrits.setDropMode(javax.swing.DropMode.ON);
/*  9196 */     MouseListener mlRLCrits = new MouseAdapter() {
/*       */       public void mouseClicked(MouseEvent e) {
/*  9198 */         if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
/*  9199 */           int index = frmMain.this.lstRLCrits.locationToIndex(e.getPoint());
/*  9200 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRLCrits();
/*  9201 */           if (!a[index].LocationLocked()) {
/*  9202 */             if ((a[index].CanSplit()) && (a[index].Contiguous())) {
/*  9203 */               frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false);
/*       */             } else {
/*  9205 */               frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a);
/*       */             }
/*       */           }
/*  9208 */           frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*       */       
/*  9212 */       public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) {
/*  9213 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRLCrits();
/*  9214 */           int index = frmMain.this.lstRLCrits.locationToIndex(e.getPoint());
/*  9215 */           frmMain.this.CurItem = a[index];
/*  9216 */           frmMain.this.CurLocation = 7;
/*  9217 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  9218 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*       */       
/*  9222 */       public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) {
/*  9223 */           abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetRLCrits();
/*  9224 */           int index = frmMain.this.lstRLCrits.locationToIndex(e.getPoint());
/*  9225 */           frmMain.this.CurItem = a[index];
/*  9226 */           frmMain.this.CurLocation = 7;
/*  9227 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  9228 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         } else {
/*  9230 */           int index = frmMain.this.lstRLCrits.locationToIndex(e.getPoint());
/*  9231 */           frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetRLCrits()[index];
/*  9232 */           frmMain.this.CurLocation = 7;
/*       */         }
/*       */       }
/*  9235 */     };
/*  9236 */     this.lstRLCrits.addMouseListener(mlRLCrits);
/*  9237 */     this.lstRLCrits.setCellRenderer(this.Mechrender);
/*  9238 */     this.jScrollPane17.setViewportView(this.lstRLCrits);
/*       */     
/*  9240 */     this.pnlRLCrits.add(this.jScrollPane17, new GridBagConstraints());
/*       */     
/*  9242 */     this.chkRLCASE2.setText("C.A.S.E. II");
/*  9243 */     this.chkRLCASE2.setEnabled(false);
/*  9244 */     this.chkRLCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9246 */         frmMain.this.chkRLCASE2ActionPerformed(evt);
/*       */       }
/*  9248 */     });
/*  9249 */     gridBagConstraints = new GridBagConstraints();
/*  9250 */     gridBagConstraints.gridx = 0;
/*  9251 */     gridBagConstraints.gridy = 1;
/*  9252 */     gridBagConstraints.anchor = 17;
/*  9253 */     this.pnlRLCrits.add(this.chkRLCASE2, gridBagConstraints);
/*       */     
/*  9255 */     this.pnlCriticals.add(this.pnlRLCrits, new AbsoluteConstraints(355, 320, 117, -1));
/*       */     
/*  9257 */     this.pnlEquipmentToPlace.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Equipment to Place", 2, 0));
/*  9258 */     this.pnlEquipmentToPlace.setMaximumSize(new Dimension(146, 330));
/*  9259 */     this.pnlEquipmentToPlace.setMinimumSize(new Dimension(146, 330));
/*  9260 */     this.pnlEquipmentToPlace.setLayout(new BoxLayout(this.pnlEquipmentToPlace, 3));
/*       */     
/*  9262 */     this.jScrollPane18.setHorizontalScrollBarPolicy(31);
/*       */     
/*  9264 */     this.lstCritsToPlace.setFont(Print.PrintConsts.BaseCritFont);
/*  9265 */     this.lstCritsToPlace.setModel(new AbstractListModel() {
/*  9266 */       String[] strings = { "Selected", "Item 2", "Item 3", "Item 4", "Item 5" };
/*  9267 */       public int getSize() { return this.strings.length; }
/*  9268 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  9269 */     });
/*  9270 */     this.lstCritsToPlace.setSelectionMode(0);
/*  9271 */     this.lstCritsToPlace.setDragEnabled(true);
/*  9272 */     this.lstCritsToPlace.setMaximumSize(new Dimension(150, 10000));
/*  9273 */     this.lstCritsToPlace.setMinimumSize(new Dimension(150, 80));
/*  9274 */     this.lstCritsToPlace.setName("[150, 80]");
/*  9275 */     this.lstCritsToPlace.setVisibleRowCount(20);
/*  9276 */     MouseListener mlCritsToPlace = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9278 */         int Index = frmMain.this.lstCritsToPlace.locationToIndex(e.getPoint());
/*  9279 */         if (Index < 0) return;
/*  9280 */         frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetFromQueueByIndex(Index);
/*  9281 */         if (e.isPopupTrigger()) {
/*  9282 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  9283 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*  9285 */         else if (frmMain.this.CurItem.Contiguous()) {
/*  9286 */           components.EquipmentCollection C = frmMain.this.CurMech.GetLoadout().GetCollection(frmMain.this.CurItem);
/*  9287 */           if (C == null) {
/*  9288 */             frmMain.this.btnAutoAllocate.setEnabled(false);
/*  9289 */             frmMain.this.btnSelectiveAllocate.setEnabled(false);
/*       */           } else {
/*  9291 */             frmMain.this.btnAutoAllocate.setEnabled(true);
/*  9292 */             frmMain.this.btnSelectiveAllocate.setEnabled(true);
/*       */           }
/*       */         } else {
/*  9295 */           frmMain.this.btnAutoAllocate.setEnabled(true);
/*  9296 */           frmMain.this.btnSelectiveAllocate.setEnabled(true);
/*       */         }
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9301 */         int Index = frmMain.this.lstCritsToPlace.locationToIndex(e.getPoint());
/*  9302 */         if (Index < 0) return;
/*  9303 */         frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetFromQueueByIndex(Index);
/*  9304 */         if (e.isPopupTrigger()) {
/*  9305 */           frmMain.this.ConfigureUtilsMenu(e.getComponent());
/*  9306 */           frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*  9308 */         else if (frmMain.this.CurItem.Contiguous()) {
/*  9309 */           components.EquipmentCollection C = frmMain.this.CurMech.GetLoadout().GetCollection(frmMain.this.CurItem);
/*  9310 */           if (C == null) {
/*  9311 */             frmMain.this.btnAutoAllocate.setEnabled(false);
/*  9312 */             frmMain.this.btnSelectiveAllocate.setEnabled(false);
/*       */           } else {
/*  9314 */             frmMain.this.btnAutoAllocate.setEnabled(true);
/*  9315 */             frmMain.this.btnSelectiveAllocate.setEnabled(true);
/*       */           }
/*       */         } else {
/*  9318 */           frmMain.this.btnAutoAllocate.setEnabled(true);
/*  9319 */           frmMain.this.btnSelectiveAllocate.setEnabled(true);
/*       */         }
/*       */         
/*       */       }
/*  9323 */     };
/*  9324 */     this.lstCritsToPlace.addMouseListener(mlCritsToPlace);
/*  9325 */     this.lstCritsToPlace.setTransferHandler(new thQueueTransferHandler());
/*  9326 */     this.lstCritsToPlace.setCellRenderer(new EquipmentListRenderer(this));
/*  9327 */     this.lstCritsToPlace.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*       */       public void valueChanged(ListSelectionEvent evt) {
/*  9329 */         frmMain.this.lstCritsToPlaceValueChanged(evt);
/*       */       }
/*  9331 */     });
/*  9332 */     this.jScrollPane18.setViewportView(this.lstCritsToPlace);
/*       */     
/*  9334 */     this.pnlEquipmentToPlace.add(this.jScrollPane18);
/*       */     
/*  9336 */     this.btnRemoveItemCrits.setText("Remove Item");
/*  9337 */     this.btnRemoveItemCrits.setEnabled(false);
/*  9338 */     this.btnRemoveItemCrits.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9340 */         frmMain.this.btnRemoveItemCritsActionPerformed(evt);
/*       */       }
/*  9342 */     });
/*  9343 */     this.pnlEquipmentToPlace.add(this.btnRemoveItemCrits);
/*       */     
/*  9345 */     this.pnlCriticals.add(this.pnlEquipmentToPlace, new AbsoluteConstraints(590, 10, 150, 360));
/*       */     
/*  9347 */     this.onlLoadoutControls.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Loadout Controls", 1, 0));
/*  9348 */     this.onlLoadoutControls.setLayout(new GridBagLayout());
/*       */     
/*  9350 */     this.btnCompactCrits.setText("Compact");
/*  9351 */     this.btnCompactCrits.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9353 */         frmMain.this.btnCompactCritsActionPerformed(evt);
/*       */       }
/*  9355 */     });
/*  9356 */     gridBagConstraints = new GridBagConstraints();
/*  9357 */     gridBagConstraints.fill = 1;
/*  9358 */     gridBagConstraints.insets = new Insets(0, 0, 2, 2);
/*  9359 */     this.onlLoadoutControls.add(this.btnCompactCrits, gridBagConstraints);
/*       */     
/*  9361 */     this.btnClearLoadout.setText("Clear");
/*  9362 */     this.btnClearLoadout.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9364 */         frmMain.this.btnClearLoadoutActionPerformed(evt);
/*       */       }
/*  9366 */     });
/*  9367 */     gridBagConstraints = new GridBagConstraints();
/*  9368 */     gridBagConstraints.gridx = 0;
/*  9369 */     gridBagConstraints.gridy = 1;
/*  9370 */     gridBagConstraints.fill = 1;
/*  9371 */     gridBagConstraints.insets = new Insets(2, 0, 0, 2);
/*  9372 */     this.onlLoadoutControls.add(this.btnClearLoadout, gridBagConstraints);
/*       */     
/*  9374 */     this.btnAutoAllocate.setText("Auto-Allocate");
/*  9375 */     this.btnAutoAllocate.setEnabled(false);
/*  9376 */     this.btnAutoAllocate.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9378 */         frmMain.this.btnAutoAllocateActionPerformed(evt);
/*       */       }
/*  9380 */     });
/*  9381 */     gridBagConstraints = new GridBagConstraints();
/*  9382 */     gridBagConstraints.gridx = 1;
/*  9383 */     gridBagConstraints.gridy = 0;
/*  9384 */     gridBagConstraints.fill = 1;
/*  9385 */     gridBagConstraints.insets = new Insets(0, 2, 2, 0);
/*  9386 */     this.onlLoadoutControls.add(this.btnAutoAllocate, gridBagConstraints);
/*       */     
/*  9388 */     this.btnSelectiveAllocate.setText("Selective-Allocate");
/*  9389 */     this.btnSelectiveAllocate.setEnabled(false);
/*  9390 */     this.btnSelectiveAllocate.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9392 */         frmMain.this.btnSelectiveAllocateActionPerformed(evt);
/*       */       }
/*  9394 */     });
/*  9395 */     gridBagConstraints = new GridBagConstraints();
/*  9396 */     gridBagConstraints.gridx = 1;
/*  9397 */     gridBagConstraints.gridy = 1;
/*  9398 */     gridBagConstraints.fill = 1;
/*  9399 */     gridBagConstraints.insets = new Insets(2, 2, 0, 0);
/*  9400 */     this.onlLoadoutControls.add(this.btnSelectiveAllocate, gridBagConstraints);
/*       */     
/*  9402 */     this.pnlCriticals.add(this.onlLoadoutControls, new AbsoluteConstraints(470, 370, 270, 90));
/*       */     
/*  9404 */     this.jPanel5.setLayout(new GridBagLayout());
/*       */     
/*  9406 */     this.jLabel59.setText("<--");
/*  9407 */     this.jPanel5.add(this.jLabel59, new GridBagConstraints());
/*       */     
/*  9409 */     this.chkLegAES.setText("A.E.S.");
/*  9410 */     this.chkLegAES.setEnabled(false);
/*  9411 */     this.chkLegAES.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9413 */         frmMain.this.chkLegAESActionPerformed(evt);
/*       */       }
/*  9415 */     });
/*  9416 */     this.jPanel5.add(this.chkLegAES, new GridBagConstraints());
/*       */     
/*  9418 */     this.jLabel61.setText("-->");
/*  9419 */     this.jPanel5.add(this.jLabel61, new GridBagConstraints());
/*       */     
/*  9421 */     this.pnlCriticals.add(this.jPanel5, new AbsoluteConstraints(240, 430, 115, 30));
/*       */     
/*  9423 */     this.pnlCLCrits.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Center Leg", 2, 0));
/*  9424 */     this.pnlCLCrits.setMaximumSize(new Dimension(116, 120));
/*  9425 */     this.pnlCLCrits.setMinimumSize(new Dimension(116, 120));
/*  9426 */     this.pnlCLCrits.setLayout(new GridBagLayout());
/*       */     
/*  9428 */     this.jScrollPane25.setMinimumSize(new Dimension(105, 87));
/*  9429 */     this.jScrollPane25.setPreferredSize(new Dimension(105, 87));
/*       */     
/*  9431 */     this.lstCLCrits.setFont(Print.PrintConsts.BaseCritFont);
/*  9432 */     this.lstCLCrits.setModel(new AbstractListModel() {
/*  9433 */       String[] strings = { "Left Leg", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" };
/*  9434 */       public int getSize() { return this.strings.length; }
/*  9435 */       public Object getElementAt(int i) { return this.strings[i]; }
/*  9436 */     });
/*  9437 */     this.lstCLCrits.setDragEnabled(true);
/*  9438 */     this.lstCLCrits.setMaximumSize(new Dimension(98, 50));
/*  9439 */     this.lstCLCrits.setMinimumSize(new Dimension(98, 50));
/*  9440 */     this.lstCLCrits.setPreferredSize(new Dimension(98, 50));
/*  9441 */     this.lstCLCrits.setVisibleRowCount(6);
/*  9442 */     this.lstCLCrits.setTransferHandler(new thCLTransferHandler(this, this.CurMech));this.lstCLCrits.setDropMode(javax.swing.DropMode.ON);MouseListener mlCLCrits = new MouseAdapter() { public void mousePressed(MouseEvent e) { if (e.isPopupTrigger()) { abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetCLCrits();int index = frmMain.this.lstCLCrits.locationToIndex(e.getPoint());frmMain.this.CurItem = a[index];frmMain.this.CurLocation = 11;frmMain.this.ConfigureUtilsMenu(e.getComponent());frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY()); } else { int index = frmMain.this.lstCLCrits.locationToIndex(e.getPoint());frmMain.this.CurItem = frmMain.this.CurMech.GetLoadout().GetCLCrits()[index];frmMain.this.CurLocation = 11; } } public void mouseReleased(MouseEvent e) { if (e.isPopupTrigger()) { abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetCLCrits();int index = frmMain.this.lstLLCrits.locationToIndex(e.getPoint());frmMain.this.CurItem = a[index];frmMain.this.CurLocation = 11;frmMain.this.ConfigureUtilsMenu(e.getComponent());frmMain.this.mnuUtilities.show(e.getComponent(), e.getX(), e.getY()); } } public void mouseClicked(MouseEvent e) { if ((e.getClickCount() == 2) && (e.getButton() == 1)) { int index = frmMain.this.lstCLCrits.locationToIndex(e.getPoint());abPlaceable[] a = frmMain.this.CurMech.GetLoadout().GetLLCrits(); if (!a[index].LocationLocked()) if ((a[index].CanSplit()) && (a[index].Contiguous())) frmMain.this.CurMech.GetLoadout().UnallocateAll(a[index], false); else frmMain.this.CurMech.GetLoadout().UnallocateByIndex(index, a); frmMain.this.RefreshInfoPane();
/*       */         }
/*       */       }
/*  9441 */     };
/*  9442 */     this.lstCLCrits.addMouseListener(mlCLCrits);this.lstCLCrits.setCellRenderer(this.Mechrender);
/*  9443 */     this.jScrollPane25.setViewportView(this.lstCLCrits);
/*       */     
/*  9445 */     this.pnlCLCrits.add(this.jScrollPane25, new GridBagConstraints());
/*       */     
/*  9447 */     this.chkCLCASE2.setText("C.A.S.E. II");
/*  9448 */     this.chkCLCASE2.setEnabled(false);
/*  9449 */     this.chkCLCASE2.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9451 */         frmMain.this.chkCLCASE2ActionPerformed(evt);
/*       */       }
/*  9453 */     });
/*  9454 */     gridBagConstraints = new GridBagConstraints();
/*  9455 */     gridBagConstraints.gridx = 0;
/*  9456 */     gridBagConstraints.gridy = 1;
/*  9457 */     gridBagConstraints.anchor = 17;
/*  9458 */     this.pnlCLCrits.add(this.chkCLCASE2, gridBagConstraints);
/*       */     
/*  9460 */     this.pnlCriticals.add(this.pnlCLCrits, new AbsoluteConstraints(10, 320, 117, -1));
/*       */     
/*  9462 */     this.tbpMainTabPane.addTab("Criticals", this.pnlCriticals);
/*       */     
/*  9464 */     this.pnlFluff.setLayout(new GridBagLayout());
/*       */     
/*  9466 */     this.pnlImage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fluff Image", 0, 0, new Font("Arial", 0, 11)));
/*  9467 */     this.pnlImage.setLayout(new GridBagLayout());
/*       */     
/*  9469 */     this.lblFluffImage.setHorizontalAlignment(0);
/*  9470 */     this.lblFluffImage.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  9471 */     this.lblFluffImage.setMaximumSize(new Dimension(290, 350));
/*  9472 */     this.lblFluffImage.setMinimumSize(new Dimension(290, 350));
/*  9473 */     this.lblFluffImage.setPreferredSize(new Dimension(290, 350));
/*  9474 */     gridBagConstraints = new GridBagConstraints();
/*  9475 */     gridBagConstraints.gridx = 0;
/*  9476 */     gridBagConstraints.gridy = 1;
/*  9477 */     gridBagConstraints.anchor = 18;
/*  9478 */     this.pnlImage.add(this.lblFluffImage, gridBagConstraints);
/*       */     
/*  9480 */     this.btnLoadImage.setText("Load Image");
/*  9481 */     this.btnLoadImage.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9483 */         frmMain.this.btnLoadImageActionPerformed(evt);
/*       */       }
/*  9485 */     });
/*  9486 */     this.jPanel1.add(this.btnLoadImage);
/*       */     
/*  9488 */     this.btnClearImage.setText("Clear Image");
/*  9489 */     this.btnClearImage.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9491 */         frmMain.this.btnClearImageActionPerformed(evt);
/*       */       }
/*  9493 */     });
/*  9494 */     this.jPanel1.add(this.btnClearImage);
/*       */     
/*  9496 */     gridBagConstraints = new GridBagConstraints();
/*  9497 */     gridBagConstraints.anchor = 13;
/*  9498 */     gridBagConstraints.insets = new Insets(0, 0, 4, 4);
/*  9499 */     this.pnlImage.add(this.jPanel1, gridBagConstraints);
/*       */     
/*  9501 */     gridBagConstraints = new GridBagConstraints();
/*  9502 */     gridBagConstraints.gridx = 0;
/*  9503 */     gridBagConstraints.gridy = 0;
/*  9504 */     gridBagConstraints.gridheight = 2;
/*  9505 */     gridBagConstraints.anchor = 18;
/*  9506 */     this.pnlFluff.add(this.pnlImage, gridBagConstraints);
/*       */     
/*  9508 */     this.pnlExport.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Export", 0, 0, new Font("Arial", 0, 11)));
/*  9509 */     this.pnlExport.setLayout(new GridBagLayout());
/*       */     
/*  9511 */     this.btnExportTXT.setText("to TXT");
/*  9512 */     this.btnExportTXT.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9514 */         frmMain.this.btnExportTXTActionPerformed(evt);
/*       */       }
/*  9516 */     });
/*  9517 */     gridBagConstraints = new GridBagConstraints();
/*  9518 */     gridBagConstraints.anchor = 17;
/*  9519 */     this.pnlExport.add(this.btnExportTXT, gridBagConstraints);
/*       */     
/*  9521 */     this.btnExportHTML.setText("to HTML");
/*  9522 */     this.btnExportHTML.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9524 */         frmMain.this.btnExportHTMLActionPerformed(evt);
/*       */       }
/*  9526 */     });
/*  9527 */     gridBagConstraints = new GridBagConstraints();
/*  9528 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/*  9529 */     this.pnlExport.add(this.btnExportHTML, gridBagConstraints);
/*       */     
/*  9531 */     this.btnExportMTF.setText("to MegaMek");
/*  9532 */     this.btnExportMTF.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9534 */         frmMain.this.btnExportMTFActionPerformed(evt);
/*       */       }
/*  9536 */     });
/*  9537 */     gridBagConstraints = new GridBagConstraints();
/*  9538 */     gridBagConstraints.anchor = 13;
/*  9539 */     this.pnlExport.add(this.btnExportMTF, gridBagConstraints);
/*       */     
/*  9541 */     gridBagConstraints = new GridBagConstraints();
/*  9542 */     gridBagConstraints.gridx = 0;
/*  9543 */     gridBagConstraints.gridy = 2;
/*  9544 */     gridBagConstraints.anchor = 11;
/*  9545 */     this.pnlFluff.add(this.pnlExport, gridBagConstraints);
/*       */     
/*  9547 */     this.tbpFluffEditors.setTabPlacement(2);
/*  9548 */     this.tbpFluffEditors.setMaximumSize(new Dimension(420, 455));
/*  9549 */     this.tbpFluffEditors.setMinimumSize(new Dimension(420, 455));
/*  9550 */     this.tbpFluffEditors.setPreferredSize(new Dimension(420, 455));
/*       */     
/*  9552 */     this.pnlOverview.setMaximumSize(new Dimension(427, 485));
/*  9553 */     this.pnlOverview.setMinimumSize(new Dimension(427, 485));
/*  9554 */     this.pnlOverview.setLayout(new BoxLayout(this.pnlOverview, 1));
/*  9555 */     this.tbpFluffEditors.addTab("Overview", this.pnlOverview);
/*       */     
/*  9557 */     this.pnlCapabilities.setMaximumSize(new Dimension(427, 485));
/*  9558 */     this.pnlCapabilities.setMinimumSize(new Dimension(427, 485));
/*  9559 */     this.pnlCapabilities.setLayout(new BoxLayout(this.pnlCapabilities, 1));
/*  9560 */     this.tbpFluffEditors.addTab("Capabilities", this.pnlCapabilities);
/*       */     
/*  9562 */     this.pnlHistory.setLayout(new BoxLayout(this.pnlHistory, 1));
/*  9563 */     this.tbpFluffEditors.addTab("Battle History", this.pnlHistory);
/*       */     
/*  9565 */     this.pnlDeployment.setLayout(new BoxLayout(this.pnlDeployment, 1));
/*  9566 */     this.tbpFluffEditors.addTab("Deployment", this.pnlDeployment);
/*       */     
/*  9568 */     this.pnlVariants.setLayout(new BoxLayout(this.pnlVariants, 1));
/*  9569 */     this.tbpFluffEditors.addTab("Variants", this.pnlVariants);
/*       */     
/*  9571 */     this.pnlNotables.setLayout(new BoxLayout(this.pnlNotables, 1));
/*  9572 */     this.tbpFluffEditors.addTab("Notables", this.pnlNotables);
/*       */     
/*  9574 */     this.pnlAdditionalFluff.setLayout(new BoxLayout(this.pnlAdditionalFluff, 1));
/*  9575 */     this.tbpFluffEditors.addTab("Additional", this.pnlAdditionalFluff);
/*       */     
/*  9577 */     this.pnlManufacturers.setLayout(new GridBagLayout());
/*       */     
/*  9579 */     this.jLabel8.setFont(new Font("Arial", 1, 12));
/*  9580 */     this.jLabel8.setText("Manufacturer Information");
/*  9581 */     this.jLabel8.setMaximumSize(new Dimension(175, 15));
/*  9582 */     this.jLabel8.setMinimumSize(new Dimension(175, 15));
/*  9583 */     this.jLabel8.setPreferredSize(new Dimension(175, 15));
/*  9584 */     gridBagConstraints = new GridBagConstraints();
/*  9585 */     gridBagConstraints.gridx = 0;
/*  9586 */     gridBagConstraints.gridy = 0;
/*  9587 */     gridBagConstraints.gridwidth = 2;
/*  9588 */     gridBagConstraints.fill = 2;
/*  9589 */     gridBagConstraints.anchor = 18;
/*  9590 */     gridBagConstraints.insets = new Insets(5, 10, 0, 0);
/*  9591 */     this.pnlManufacturers.add(this.jLabel8, gridBagConstraints);
/*       */     
/*  9593 */     this.jLabel9.setFont(new Font("Arial", 0, 11));
/*  9594 */     this.jLabel9.setHorizontalAlignment(4);
/*  9595 */     this.jLabel9.setText("Manufacturing Company:");
/*  9596 */     gridBagConstraints = new GridBagConstraints();
/*  9597 */     gridBagConstraints.gridx = 0;
/*  9598 */     gridBagConstraints.gridy = 1;
/*  9599 */     gridBagConstraints.fill = 2;
/*  9600 */     gridBagConstraints.anchor = 18;
/*  9601 */     gridBagConstraints.insets = new Insets(10, 10, 0, 0);
/*  9602 */     this.pnlManufacturers.add(this.jLabel9, gridBagConstraints);
/*       */     
/*  9604 */     this.jLabel10.setFont(new Font("Arial", 0, 11));
/*  9605 */     this.jLabel10.setHorizontalAlignment(4);
/*  9606 */     this.jLabel10.setText("Location:");
/*  9607 */     gridBagConstraints = new GridBagConstraints();
/*  9608 */     gridBagConstraints.gridx = 0;
/*  9609 */     gridBagConstraints.gridy = 2;
/*  9610 */     gridBagConstraints.fill = 2;
/*  9611 */     gridBagConstraints.ipadx = 76;
/*  9612 */     gridBagConstraints.anchor = 18;
/*  9613 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9614 */     this.pnlManufacturers.add(this.jLabel10, gridBagConstraints);
/*       */     
/*  9616 */     this.jLabel12.setFont(new Font("Arial", 0, 11));
/*  9617 */     this.jLabel12.setHorizontalAlignment(4);
/*  9618 */     this.jLabel12.setText("Engine Manufacturer:");
/*  9619 */     gridBagConstraints = new GridBagConstraints();
/*  9620 */     gridBagConstraints.gridx = 0;
/*  9621 */     gridBagConstraints.gridy = 4;
/*  9622 */     gridBagConstraints.fill = 2;
/*  9623 */     gridBagConstraints.ipadx = 17;
/*  9624 */     gridBagConstraints.anchor = 18;
/*  9625 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9626 */     this.pnlManufacturers.add(this.jLabel12, gridBagConstraints);
/*       */     
/*  9628 */     this.jLabel11.setFont(new Font("Arial", 0, 11));
/*  9629 */     this.jLabel11.setHorizontalAlignment(4);
/*  9630 */     this.jLabel11.setText("Armor Model:");
/*  9631 */     gridBagConstraints = new GridBagConstraints();
/*  9632 */     gridBagConstraints.gridx = 0;
/*  9633 */     gridBagConstraints.gridy = 5;
/*  9634 */     gridBagConstraints.fill = 2;
/*  9635 */     gridBagConstraints.ipadx = 56;
/*  9636 */     gridBagConstraints.anchor = 18;
/*  9637 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9638 */     this.pnlManufacturers.add(this.jLabel11, gridBagConstraints);
/*       */     
/*  9640 */     this.jLabel13.setFont(new Font("Arial", 0, 11));
/*  9641 */     this.jLabel13.setHorizontalAlignment(4);
/*  9642 */     this.jLabel13.setText("Chassis Model:");
/*  9643 */     gridBagConstraints = new GridBagConstraints();
/*  9644 */     gridBagConstraints.gridx = 0;
/*  9645 */     gridBagConstraints.gridy = 3;
/*  9646 */     gridBagConstraints.fill = 2;
/*  9647 */     gridBagConstraints.ipadx = 47;
/*  9648 */     gridBagConstraints.anchor = 18;
/*  9649 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9650 */     this.pnlManufacturers.add(this.jLabel13, gridBagConstraints);
/*       */     
/*  9652 */     this.jLabel14.setFont(new Font("Arial", 0, 11));
/*  9653 */     this.jLabel14.setHorizontalAlignment(4);
/*  9654 */     this.jLabel14.setText("Communications System:");
/*  9655 */     gridBagConstraints = new GridBagConstraints();
/*  9656 */     gridBagConstraints.gridx = 0;
/*  9657 */     gridBagConstraints.gridy = 7;
/*  9658 */     gridBagConstraints.fill = 2;
/*  9659 */     gridBagConstraints.anchor = 18;
/*  9660 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9661 */     this.pnlManufacturers.add(this.jLabel14, gridBagConstraints);
/*       */     
/*  9663 */     this.jLabel15.setFont(new Font("Arial", 0, 11));
/*  9664 */     this.jLabel15.setHorizontalAlignment(4);
/*  9665 */     this.jLabel15.setText("Targeting and Tracking:");
/*  9666 */     gridBagConstraints = new GridBagConstraints();
/*  9667 */     gridBagConstraints.gridx = 0;
/*  9668 */     gridBagConstraints.gridy = 8;
/*  9669 */     gridBagConstraints.fill = 2;
/*  9670 */     gridBagConstraints.ipadx = 7;
/*  9671 */     gridBagConstraints.anchor = 18;
/*  9672 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9673 */     this.pnlManufacturers.add(this.jLabel15, gridBagConstraints);
/*       */     
/*  9675 */     this.txtManufacturer.setFont(new Font("Arial", 0, 11));
/*  9676 */     gridBagConstraints = new GridBagConstraints();
/*  9677 */     gridBagConstraints.gridx = 1;
/*  9678 */     gridBagConstraints.gridy = 1;
/*  9679 */     gridBagConstraints.fill = 2;
/*  9680 */     gridBagConstraints.ipadx = 184;
/*  9681 */     gridBagConstraints.anchor = 18;
/*  9682 */     gridBagConstraints.insets = new Insets(10, 2, 0, 11);
/*  9683 */     this.pnlManufacturers.add(this.txtManufacturer, gridBagConstraints);
/*  9684 */     MouseListener mlManufacturer = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9686 */         if (e.isPopupTrigger())
/*  9687 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9691 */         if (e.isPopupTrigger()) {
/*  9692 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9695 */     };
/*  9696 */     this.txtManufacturer.addMouseListener(mlManufacturer);
/*       */     
/*  9698 */     this.txtEngineManufacturer.setFont(new Font("Arial", 0, 11));
/*  9699 */     gridBagConstraints = new GridBagConstraints();
/*  9700 */     gridBagConstraints.gridx = 1;
/*  9701 */     gridBagConstraints.gridy = 4;
/*  9702 */     gridBagConstraints.fill = 2;
/*  9703 */     gridBagConstraints.ipadx = 184;
/*  9704 */     gridBagConstraints.anchor = 18;
/*  9705 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9706 */     this.pnlManufacturers.add(this.txtEngineManufacturer, gridBagConstraints);
/*  9707 */     MouseListener mlEngineManufacturer = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9709 */         if (e.isPopupTrigger())
/*  9710 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9714 */         if (e.isPopupTrigger()) {
/*  9715 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9718 */     };
/*  9719 */     this.txtEngineManufacturer.addMouseListener(mlEngineManufacturer);
/*       */     
/*  9721 */     this.txtArmorModel.setFont(new Font("Arial", 0, 11));
/*  9722 */     gridBagConstraints = new GridBagConstraints();
/*  9723 */     gridBagConstraints.gridx = 1;
/*  9724 */     gridBagConstraints.gridy = 5;
/*  9725 */     gridBagConstraints.fill = 2;
/*  9726 */     gridBagConstraints.ipadx = 184;
/*  9727 */     gridBagConstraints.anchor = 18;
/*  9728 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9729 */     this.pnlManufacturers.add(this.txtArmorModel, gridBagConstraints);
/*  9730 */     MouseListener mlArmorModel = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9732 */         if (e.isPopupTrigger())
/*  9733 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9737 */         if (e.isPopupTrigger()) {
/*  9738 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9741 */     };
/*  9742 */     this.txtArmorModel.addMouseListener(mlArmorModel);
/*       */     
/*  9744 */     this.txtChassisModel.setFont(new Font("Arial", 0, 11));
/*  9745 */     gridBagConstraints = new GridBagConstraints();
/*  9746 */     gridBagConstraints.gridx = 1;
/*  9747 */     gridBagConstraints.gridy = 3;
/*  9748 */     gridBagConstraints.fill = 2;
/*  9749 */     gridBagConstraints.ipadx = 184;
/*  9750 */     gridBagConstraints.anchor = 18;
/*  9751 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9752 */     this.pnlManufacturers.add(this.txtChassisModel, gridBagConstraints);
/*  9753 */     MouseListener mlChassisModel = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9755 */         if (e.isPopupTrigger())
/*  9756 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9760 */         if (e.isPopupTrigger()) {
/*  9761 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9764 */     };
/*  9765 */     this.txtChassisModel.addMouseListener(mlChassisModel);
/*       */     
/*  9767 */     this.txtCommSystem.setFont(new Font("Arial", 0, 11));
/*  9768 */     gridBagConstraints = new GridBagConstraints();
/*  9769 */     gridBagConstraints.gridx = 1;
/*  9770 */     gridBagConstraints.gridy = 7;
/*  9771 */     gridBagConstraints.fill = 2;
/*  9772 */     gridBagConstraints.ipadx = 184;
/*  9773 */     gridBagConstraints.anchor = 18;
/*  9774 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9775 */     this.pnlManufacturers.add(this.txtCommSystem, gridBagConstraints);
/*  9776 */     MouseListener mlCommSystem = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9778 */         if (e.isPopupTrigger())
/*  9779 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9783 */         if (e.isPopupTrigger()) {
/*  9784 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9787 */     };
/*  9788 */     this.txtCommSystem.addMouseListener(mlCommSystem);
/*       */     
/*  9790 */     this.txtTNTSystem.setFont(new Font("Arial", 0, 11));
/*  9791 */     gridBagConstraints = new GridBagConstraints();
/*  9792 */     gridBagConstraints.gridx = 1;
/*  9793 */     gridBagConstraints.gridy = 8;
/*  9794 */     gridBagConstraints.fill = 2;
/*  9795 */     gridBagConstraints.ipadx = 184;
/*  9796 */     gridBagConstraints.anchor = 18;
/*  9797 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9798 */     this.pnlManufacturers.add(this.txtTNTSystem, gridBagConstraints);
/*  9799 */     MouseListener mlTNTSystem = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9801 */         if (e.isPopupTrigger())
/*  9802 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9806 */         if (e.isPopupTrigger()) {
/*  9807 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9810 */     };
/*  9811 */     this.txtTNTSystem.addMouseListener(mlTNTSystem);
/*       */     
/*  9813 */     this.pnlWeaponsManufacturers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapons Manufacturers", 0, 0, new Font("Arial", 0, 11)));
/*  9814 */     this.pnlWeaponsManufacturers.setFont(new Font("Arial", 0, 11));
/*  9815 */     this.pnlWeaponsManufacturers.setMinimumSize(new Dimension(315, 260));
/*  9816 */     this.pnlWeaponsManufacturers.setLayout(new GridBagLayout());
/*       */     
/*  9818 */     this.chkIndividualWeapons.setText("Assign manufacturers individually");
/*  9819 */     gridBagConstraints = new GridBagConstraints();
/*  9820 */     gridBagConstraints.gridx = 0;
/*  9821 */     gridBagConstraints.gridy = 1;
/*  9822 */     gridBagConstraints.anchor = 17;
/*  9823 */     gridBagConstraints.insets = new Insets(4, 10, 4, 0);
/*  9824 */     this.pnlWeaponsManufacturers.add(this.chkIndividualWeapons, gridBagConstraints);
/*       */     
/*  9826 */     this.scpWeaponManufacturers.setPreferredSize(new Dimension(452, 392));
/*       */     
/*  9828 */     this.tblWeaponManufacturers.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null }, { null, null }, { null, null }, { null, null } }, new String[] { "Weapon", "Manufacturer" })
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
/*  9839 */       boolean[] canEdit = { false, true };
/*       */       
/*       */ 
/*       */       public boolean isCellEditable(int rowIndex, int columnIndex)
/*       */       {
/*  9844 */         return this.canEdit[columnIndex];
/*       */       }
/*  9846 */     });
/*  9847 */     this.scpWeaponManufacturers.setViewportView(this.tblWeaponManufacturers);
/*       */     
/*  9849 */     gridBagConstraints = new GridBagConstraints();
/*  9850 */     gridBagConstraints.ipadx = 280;
/*  9851 */     gridBagConstraints.ipady = 180;
/*  9852 */     this.pnlWeaponsManufacturers.add(this.scpWeaponManufacturers, gridBagConstraints);
/*       */     
/*  9854 */     gridBagConstraints = new GridBagConstraints();
/*  9855 */     gridBagConstraints.gridx = 0;
/*  9856 */     gridBagConstraints.gridy = 9;
/*  9857 */     gridBagConstraints.gridwidth = 2;
/*  9858 */     gridBagConstraints.anchor = 18;
/*  9859 */     gridBagConstraints.insets = new Insets(2, 10, 2, 10);
/*  9860 */     this.pnlManufacturers.add(this.pnlWeaponsManufacturers, gridBagConstraints);
/*  9861 */     gridBagConstraints = new GridBagConstraints();
/*  9862 */     gridBagConstraints.gridx = 1;
/*  9863 */     gridBagConstraints.gridy = 2;
/*  9864 */     gridBagConstraints.fill = 2;
/*  9865 */     gridBagConstraints.ipadx = 184;
/*  9866 */     gridBagConstraints.anchor = 18;
/*  9867 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9868 */     this.pnlManufacturers.add(this.txtManufacturerLocation, gridBagConstraints);
/*  9869 */     MouseListener mlManufacturerLocation = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9871 */         if (e.isPopupTrigger())
/*  9872 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9876 */         if (e.isPopupTrigger()) {
/*  9877 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9880 */     };
/*  9881 */     this.txtManufacturerLocation.addMouseListener(mlManufacturerLocation);
/*       */     
/*  9883 */     this.jLabel35.setFont(new Font("Arial", 0, 11));
/*  9884 */     this.jLabel35.setHorizontalAlignment(4);
/*  9885 */     this.jLabel35.setText("Jump Jet Model:");
/*  9886 */     gridBagConstraints = new GridBagConstraints();
/*  9887 */     gridBagConstraints.gridx = 0;
/*  9888 */     gridBagConstraints.gridy = 6;
/*  9889 */     gridBagConstraints.fill = 2;
/*  9890 */     gridBagConstraints.ipadx = 44;
/*  9891 */     gridBagConstraints.anchor = 18;
/*  9892 */     gridBagConstraints.insets = new Insets(0, 10, 0, 0);
/*  9893 */     this.pnlManufacturers.add(this.jLabel35, gridBagConstraints);
/*       */     
/*  9895 */     this.txtJJModel.setEnabled(false);
/*  9896 */     gridBagConstraints = new GridBagConstraints();
/*  9897 */     gridBagConstraints.gridx = 1;
/*  9898 */     gridBagConstraints.gridy = 6;
/*  9899 */     gridBagConstraints.fill = 2;
/*  9900 */     gridBagConstraints.ipadx = 184;
/*  9901 */     gridBagConstraints.anchor = 18;
/*  9902 */     gridBagConstraints.insets = new Insets(0, 2, 0, 11);
/*  9903 */     this.pnlManufacturers.add(this.txtJJModel, gridBagConstraints);
/*  9904 */     MouseListener mlJJModel = new MouseAdapter() {
/*       */       public void mouseReleased(MouseEvent e) {
/*  9906 */         if (e.isPopupTrigger())
/*  9907 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */       }
/*       */       
/*       */       public void mousePressed(MouseEvent e) {
/*  9911 */         if (e.isPopupTrigger()) {
/*  9912 */           frmMain.this.mnuFluff.show(e.getComponent(), e.getX(), e.getY());
/*       */         }
/*       */       }
/*  9915 */     };
/*  9916 */     this.txtJJModel.addMouseListener(mlJJModel);
/*       */     
/*  9918 */     this.tbpFluffEditors.addTab("Manufacturers", this.pnlManufacturers);
/*       */     
/*  9920 */     this.lblBattleMechQuirks.setFont(new Font("Arial", 1, 12));
/*  9921 */     this.lblBattleMechQuirks.setText("BattleMech Quirks");
/*  9922 */     this.lblBattleMechQuirks.setMaximumSize(new Dimension(175, 15));
/*  9923 */     this.lblBattleMechQuirks.setMinimumSize(new Dimension(175, 15));
/*  9924 */     this.lblBattleMechQuirks.setPreferredSize(new Dimension(175, 15));
/*       */     
/*  9926 */     this.tblQuirks.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null }, { null, null }, { null, null }, { null, null } }, new String[] { "Quirk", "Cost" })
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
/*  9937 */       Class[] types = { String.class, Integer.class };
/*       */       
/*       */ 
/*  9940 */       boolean[] canEdit = { false, false };
/*       */       
/*       */ 
/*       */       public Class getColumnClass(int columnIndex)
/*       */       {
/*  9945 */         return this.types[columnIndex];
/*       */       }
/*       */       
/*       */       public boolean isCellEditable(int rowIndex, int columnIndex) {
/*  9949 */         return this.canEdit[columnIndex];
/*       */       }
/*  9951 */     });
/*  9952 */     this.tblQuirks.setColumnSelectionAllowed(true);
/*  9953 */     this.tblQuirks.getTableHeader().setReorderingAllowed(false);
/*  9954 */     this.scpQuirkTable.setViewportView(this.tblQuirks);
/*  9955 */     this.tblQuirks.getColumnModel().getSelectionModel().setSelectionMode(0);
/*  9956 */     this.tblQuirks.getColumnModel().getColumn(0).setResizable(false);
/*  9957 */     this.tblQuirks.getColumnModel().getColumn(1).setResizable(false);
/*  9958 */     this.tblQuirks.getColumnModel().getColumn(1).setPreferredWidth(5);
/*       */     
/*  9960 */     this.btnAddQuirk.setText("Add Quirk");
/*  9961 */     this.btnAddQuirk.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/*  9963 */         frmMain.this.btnAddQuirkActionPerformed(evt);
/*       */       }
/*       */       
/*  9966 */     });
/*  9967 */     javax.swing.GroupLayout pnlQuirksLayout = new javax.swing.GroupLayout(this.pnlQuirks);
/*  9968 */     this.pnlQuirks.setLayout(pnlQuirksLayout);
/*  9969 */     pnlQuirksLayout.setHorizontalGroup(pnlQuirksLayout
/*  9970 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/*  9971 */       .addComponent(this.lblBattleMechQuirks, -2, -1, -2)
/*  9972 */       .addGroup(pnlQuirksLayout.createSequentialGroup()
/*  9973 */       .addContainerGap()
/*  9974 */       .addGroup(pnlQuirksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/*  9975 */       .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQuirksLayout.createSequentialGroup()
/*  9976 */       .addGap(0, 230, 32767)
/*  9977 */       .addComponent(this.btnAddQuirk))
/*  9978 */       .addComponent(this.scpQuirkTable, -1, 309, 32767))
/*  9979 */       .addContainerGap()));
/*       */     
/*  9981 */     pnlQuirksLayout.setVerticalGroup(pnlQuirksLayout
/*  9982 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/*  9983 */       .addGroup(pnlQuirksLayout.createSequentialGroup()
/*  9984 */       .addComponent(this.lblBattleMechQuirks, -2, -1, -2)
/*  9985 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/*  9986 */       .addComponent(this.scpQuirkTable, -2, 389, -2)
/*  9987 */       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
/*  9988 */       .addComponent(this.btnAddQuirk)
/*  9989 */       .addContainerGap()));
/*       */     
/*       */ 
/*  9992 */     this.tbpFluffEditors.addTab("Quirks", this.pnlQuirks);
/*  9993 */     this.pnlQuirks.getAccessibleContext().setAccessibleName("Quirks");
/*  9994 */     this.pnlQuirks.getAccessibleContext().setAccessibleParent(this.tbpFluffEditors);
/*       */     
/*  9996 */     gridBagConstraints = new GridBagConstraints();
/*  9997 */     gridBagConstraints.gridx = 1;
/*  9998 */     gridBagConstraints.gridy = 0;
/*  9999 */     gridBagConstraints.gridheight = 3;
/* 10000 */     gridBagConstraints.anchor = 12;
/* 10001 */     gridBagConstraints.insets = new Insets(0, 5, 0, 6);
/* 10002 */     this.pnlFluff.add(this.tbpFluffEditors, gridBagConstraints);
/*       */     
/* 10004 */     this.tbpMainTabPane.addTab("   Fluff   ", this.pnlFluff);
/*       */     
/* 10006 */     this.pnlCharts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/* 10008 */     this.jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Total Tonnage Percentages"));
/* 10009 */     this.jPanel2.setLayout(new GridBagLayout());
/*       */     
/* 10011 */     this.jLabel39.setText("Structural Components:");
/* 10012 */     gridBagConstraints = new GridBagConstraints();
/* 10013 */     gridBagConstraints.anchor = 13;
/* 10014 */     this.jPanel2.add(this.jLabel39, gridBagConstraints);
/*       */     
/* 10016 */     this.lblTonPercStructure.setText("000.00%");
/* 10017 */     gridBagConstraints = new GridBagConstraints();
/* 10018 */     gridBagConstraints.anchor = 17;
/* 10019 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10020 */     this.jPanel2.add(this.lblTonPercStructure, gridBagConstraints);
/*       */     
/* 10022 */     this.jLabel43.setText("Engine:");
/* 10023 */     gridBagConstraints = new GridBagConstraints();
/* 10024 */     gridBagConstraints.gridx = 0;
/* 10025 */     gridBagConstraints.gridy = 1;
/* 10026 */     gridBagConstraints.anchor = 13;
/* 10027 */     this.jPanel2.add(this.jLabel43, gridBagConstraints);
/*       */     
/* 10029 */     this.lblTonPercEngine.setText("000.00%");
/* 10030 */     gridBagConstraints = new GridBagConstraints();
/* 10031 */     gridBagConstraints.gridx = 1;
/* 10032 */     gridBagConstraints.gridy = 1;
/* 10033 */     gridBagConstraints.anchor = 17;
/* 10034 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10035 */     this.jPanel2.add(this.lblTonPercEngine, gridBagConstraints);
/*       */     
/* 10037 */     this.jLabel54.setText("Heat Sinks:");
/* 10038 */     gridBagConstraints = new GridBagConstraints();
/* 10039 */     gridBagConstraints.gridx = 0;
/* 10040 */     gridBagConstraints.gridy = 2;
/* 10041 */     gridBagConstraints.anchor = 13;
/* 10042 */     this.jPanel2.add(this.jLabel54, gridBagConstraints);
/*       */     
/* 10044 */     this.lblTonPercHeatSinks.setText("000.00%");
/* 10045 */     gridBagConstraints = new GridBagConstraints();
/* 10046 */     gridBagConstraints.gridx = 1;
/* 10047 */     gridBagConstraints.gridy = 2;
/* 10048 */     gridBagConstraints.anchor = 17;
/* 10049 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10050 */     this.jPanel2.add(this.lblTonPercHeatSinks, gridBagConstraints);
/*       */     
/* 10052 */     this.jLabel56.setText("Enhancements:");
/* 10053 */     gridBagConstraints = new GridBagConstraints();
/* 10054 */     gridBagConstraints.gridx = 0;
/* 10055 */     gridBagConstraints.gridy = 3;
/* 10056 */     gridBagConstraints.anchor = 13;
/* 10057 */     this.jPanel2.add(this.jLabel56, gridBagConstraints);
/*       */     
/* 10059 */     this.lblTonPercEnhance.setText("000.00%");
/* 10060 */     gridBagConstraints = new GridBagConstraints();
/* 10061 */     gridBagConstraints.gridx = 1;
/* 10062 */     gridBagConstraints.gridy = 3;
/* 10063 */     gridBagConstraints.anchor = 17;
/* 10064 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10065 */     this.jPanel2.add(this.lblTonPercEnhance, gridBagConstraints);
/*       */     
/* 10067 */     this.jLabel58.setText("Armor:");
/* 10068 */     gridBagConstraints = new GridBagConstraints();
/* 10069 */     gridBagConstraints.gridx = 0;
/* 10070 */     gridBagConstraints.gridy = 4;
/* 10071 */     gridBagConstraints.anchor = 13;
/* 10072 */     this.jPanel2.add(this.jLabel58, gridBagConstraints);
/*       */     
/* 10074 */     this.lblTonPercArmor.setText("000.00%");
/* 10075 */     gridBagConstraints = new GridBagConstraints();
/* 10076 */     gridBagConstraints.gridx = 1;
/* 10077 */     gridBagConstraints.gridy = 4;
/* 10078 */     gridBagConstraints.anchor = 17;
/* 10079 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10080 */     this.jPanel2.add(this.lblTonPercArmor, gridBagConstraints);
/*       */     
/* 10082 */     this.jLabel60.setText("Jump Jets:");
/* 10083 */     gridBagConstraints = new GridBagConstraints();
/* 10084 */     gridBagConstraints.gridx = 0;
/* 10085 */     gridBagConstraints.gridy = 5;
/* 10086 */     gridBagConstraints.anchor = 13;
/* 10087 */     this.jPanel2.add(this.jLabel60, gridBagConstraints);
/*       */     
/* 10089 */     this.lblTonPercJumpJets.setText("000.00%");
/* 10090 */     gridBagConstraints = new GridBagConstraints();
/* 10091 */     gridBagConstraints.gridx = 1;
/* 10092 */     gridBagConstraints.gridy = 5;
/* 10093 */     gridBagConstraints.anchor = 17;
/* 10094 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10095 */     this.jPanel2.add(this.lblTonPercJumpJets, gridBagConstraints);
/*       */     
/* 10097 */     this.jLabel62.setText("Weapons and Ammo:");
/* 10098 */     gridBagConstraints = new GridBagConstraints();
/* 10099 */     gridBagConstraints.gridx = 0;
/* 10100 */     gridBagConstraints.gridy = 6;
/* 10101 */     gridBagConstraints.anchor = 13;
/* 10102 */     this.jPanel2.add(this.jLabel62, gridBagConstraints);
/*       */     
/* 10104 */     this.lblTonPercWeapons.setText("000.00%");
/* 10105 */     gridBagConstraints = new GridBagConstraints();
/* 10106 */     gridBagConstraints.gridx = 1;
/* 10107 */     gridBagConstraints.gridy = 6;
/* 10108 */     gridBagConstraints.anchor = 17;
/* 10109 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10110 */     this.jPanel2.add(this.lblTonPercWeapons, gridBagConstraints);
/*       */     
/* 10112 */     this.jLabel64.setText("Equipment/Pod Space:");
/* 10113 */     gridBagConstraints = new GridBagConstraints();
/* 10114 */     gridBagConstraints.gridx = 0;
/* 10115 */     gridBagConstraints.gridy = 7;
/* 10116 */     gridBagConstraints.anchor = 13;
/* 10117 */     this.jPanel2.add(this.jLabel64, gridBagConstraints);
/*       */     
/* 10119 */     this.lblTonPercEquips.setText("000.00%");
/* 10120 */     gridBagConstraints = new GridBagConstraints();
/* 10121 */     gridBagConstraints.gridx = 1;
/* 10122 */     gridBagConstraints.gridy = 7;
/* 10123 */     gridBagConstraints.anchor = 17;
/* 10124 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10125 */     this.jPanel2.add(this.lblTonPercEquips, gridBagConstraints);
/*       */     
/* 10127 */     this.pnlCharts.add(this.jPanel2, new AbsoluteConstraints(10, 10, 230, 150));
/*       */     
/* 10129 */     this.jPanel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Other Statistics"));
/* 10130 */     this.jPanel3.setLayout(new GridBagLayout());
/*       */     
/* 10132 */     this.jLabel41.setText("Damage / 'Mech Tonnage:");
/* 10133 */     gridBagConstraints = new GridBagConstraints();
/* 10134 */     gridBagConstraints.anchor = 13;
/* 10135 */     this.jPanel3.add(this.jLabel41, gridBagConstraints);
/*       */     
/* 10137 */     this.lblDamagePerTon.setText("000.00");
/* 10138 */     gridBagConstraints = new GridBagConstraints();
/* 10139 */     gridBagConstraints.anchor = 17;
/* 10140 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/* 10141 */     this.jPanel3.add(this.lblDamagePerTon, gridBagConstraints);
/*       */     
/* 10143 */     this.pnlCharts.add(this.jPanel3, new AbsoluteConstraints(240, 10, 230, 50));
/*       */     
/* 10145 */     this.pnlDamageChart.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/* 10146 */     this.pnlDamageChart.setLayout(null);
/* 10147 */     this.pnlCharts.add(this.pnlDamageChart, new AbsoluteConstraints(10, 170, 720, 280));
/*       */     
/* 10149 */     this.lblLegendTitle.setText("Chart Options:");
/* 10150 */     this.pnlCharts.add(this.lblLegendTitle, new AbsoluteConstraints(480, 10, 140, -1));
/*       */     
/* 10152 */     this.chkChartFront.setBackground(Color.red);
/* 10153 */     this.chkChartFront.setSelected(true);
/* 10154 */     this.chkChartFront.setText("Show Front Arc Weapons");
/* 10155 */     this.chkChartFront.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10157 */         frmMain.this.chkChartFrontActionPerformed(evt);
/*       */       }
/* 10159 */     });
/* 10160 */     this.pnlCharts.add(this.chkChartFront, new AbsoluteConstraints(500, 30, 210, -1));
/*       */     
/* 10162 */     this.chkChartRear.setBackground(Color.pink);
/* 10163 */     this.chkChartRear.setSelected(true);
/* 10164 */     this.chkChartRear.setText("Show Rear Arc Weapons");
/* 10165 */     this.chkChartRear.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10167 */         frmMain.this.chkChartRearActionPerformed(evt);
/*       */       }
/* 10169 */     });
/* 10170 */     this.pnlCharts.add(this.chkChartRear, new AbsoluteConstraints(500, 55, 210, -1));
/*       */     
/* 10172 */     this.chkChartRight.setBackground(Color.green);
/* 10173 */     this.chkChartRight.setSelected(true);
/* 10174 */     this.chkChartRight.setText("Show Right Arm Arc Weapons");
/* 10175 */     this.chkChartRight.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10177 */         frmMain.this.chkChartRightActionPerformed(evt);
/*       */       }
/* 10179 */     });
/* 10180 */     this.pnlCharts.add(this.chkChartRight, new AbsoluteConstraints(500, 80, 210, -1));
/*       */     
/* 10182 */     this.chkChartLeft.setBackground(Color.orange);
/* 10183 */     this.chkChartLeft.setSelected(true);
/* 10184 */     this.chkChartLeft.setText("Show Left Arm Arc Weapons");
/* 10185 */     this.chkChartLeft.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10187 */         frmMain.this.chkChartLeftActionPerformed(evt);
/*       */       }
/* 10189 */     });
/* 10190 */     this.pnlCharts.add(this.chkChartLeft, new AbsoluteConstraints(500, 105, 210, -1));
/*       */     
/* 10192 */     this.btnBracketChart.setText("Show Weapon Bracket Chart");
/* 10193 */     this.btnBracketChart.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10195 */         frmMain.this.btnBracketChartActionPerformed(evt);
/*       */       }
/* 10197 */     });
/* 10198 */     this.pnlCharts.add(this.btnBracketChart, new AbsoluteConstraints(250, 70, 210, -1));
/*       */     
/* 10200 */     this.chkAverageDamage.setText("Show Average Damage");
/* 10201 */     this.chkAverageDamage.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10203 */         frmMain.this.chkAverageDamageActionPerformed(evt);
/*       */       }
/* 10205 */     });
/* 10206 */     this.pnlCharts.add(this.chkAverageDamage, new AbsoluteConstraints(250, 110, -1, -1));
/*       */     
/* 10208 */     this.chkShowTextNotGraph.setText("Show Text Instead of Graph");
/* 10209 */     this.chkShowTextNotGraph.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10211 */         frmMain.this.chkShowTextNotGraphActionPerformed(evt);
/*       */       }
/* 10213 */     });
/* 10214 */     this.pnlCharts.add(this.chkShowTextNotGraph, new AbsoluteConstraints(250, 130, -1, -1));
/*       */     
/* 10216 */     this.tbpMainTabPane.addTab("Charts", this.pnlCharts);
/*       */     
/* 10218 */     this.pnlBattleforce.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/* 10220 */     this.pnlBFStats.setBorder(BorderFactory.createTitledBorder("BattleForce Stats"));
/* 10221 */     this.pnlBFStats.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/* 10223 */     this.jLabel66.setText("MV");
/* 10224 */     this.pnlBFStats.add(this.jLabel66, new AbsoluteConstraints(20, 30, -1, -1));
/*       */     
/* 10226 */     this.jLabel67.setText("S (+0)");
/* 10227 */     this.pnlBFStats.add(this.jLabel67, new AbsoluteConstraints(60, 30, -1, -1));
/*       */     
/* 10229 */     this.jLabel68.setText("M (+2)");
/* 10230 */     this.pnlBFStats.add(this.jLabel68, new AbsoluteConstraints(110, 30, -1, -1));
/*       */     
/* 10232 */     this.jLabel69.setText("L (+4)");
/* 10233 */     this.pnlBFStats.add(this.jLabel69, new AbsoluteConstraints(160, 30, -1, -1));
/*       */     
/* 10235 */     this.jLabel70.setText("E (+6)");
/* 10236 */     this.pnlBFStats.add(this.jLabel70, new AbsoluteConstraints(210, 30, -1, -1));
/*       */     
/* 10238 */     this.jLabel71.setText("Wt.");
/* 10239 */     this.pnlBFStats.add(this.jLabel71, new AbsoluteConstraints(260, 30, -1, -1));
/*       */     
/* 10241 */     this.jLabel72.setText("OV");
/* 10242 */     this.pnlBFStats.add(this.jLabel72, new AbsoluteConstraints(300, 30, -1, -1));
/*       */     
/* 10244 */     this.jLabel73.setText("Armor:");
/* 10245 */     this.pnlBFStats.add(this.jLabel73, new AbsoluteConstraints(350, 30, -1, -1));
/*       */     
/* 10247 */     this.jLabel74.setText("Structure:");
/* 10248 */     this.pnlBFStats.add(this.jLabel74, new AbsoluteConstraints(350, 60, -1, -1));
/*       */     
/* 10250 */     this.jLabel75.setText("Special Abilities:");
/* 10251 */     this.pnlBFStats.add(this.jLabel75, new AbsoluteConstraints(20, 110, -1, -1));
/*       */     
/* 10253 */     this.lblBFMV.setHorizontalAlignment(0);
/* 10254 */     this.lblBFMV.setText("0");
/* 10255 */     this.pnlBFStats.add(this.lblBFMV, new AbsoluteConstraints(10, 50, 30, -1));
/*       */     
/* 10257 */     this.lblBFWt.setHorizontalAlignment(0);
/* 10258 */     this.lblBFWt.setText("1");
/* 10259 */     this.pnlBFStats.add(this.lblBFWt, new AbsoluteConstraints(250, 50, 30, -1));
/*       */     
/* 10261 */     this.lblBFOV.setHorizontalAlignment(0);
/* 10262 */     this.lblBFOV.setText("0");
/* 10263 */     this.pnlBFStats.add(this.lblBFOV, new AbsoluteConstraints(290, 50, 30, -1));
/*       */     
/* 10265 */     this.lblBFExtreme.setHorizontalAlignment(0);
/* 10266 */     this.lblBFExtreme.setText("0");
/* 10267 */     this.pnlBFStats.add(this.lblBFExtreme, new AbsoluteConstraints(210, 50, 30, -1));
/*       */     
/* 10269 */     this.lblBFShort.setHorizontalAlignment(0);
/* 10270 */     this.lblBFShort.setText("0");
/* 10271 */     this.pnlBFStats.add(this.lblBFShort, new AbsoluteConstraints(60, 50, 30, -1));
/*       */     
/* 10273 */     this.lblBFMedium.setHorizontalAlignment(0);
/* 10274 */     this.lblBFMedium.setText("0");
/* 10275 */     this.pnlBFStats.add(this.lblBFMedium, new AbsoluteConstraints(110, 50, 30, -1));
/*       */     
/* 10277 */     this.lblBFLong.setHorizontalAlignment(0);
/* 10278 */     this.lblBFLong.setText("0");
/* 10279 */     this.pnlBFStats.add(this.lblBFLong, new AbsoluteConstraints(160, 50, 30, -1));
/*       */     
/* 10281 */     this.lblBFArmor.setHorizontalAlignment(0);
/* 10282 */     this.lblBFArmor.setText("0");
/* 10283 */     this.pnlBFStats.add(this.lblBFArmor, new AbsoluteConstraints(410, 30, 30, -1));
/*       */     
/* 10285 */     this.lblBFStructure.setHorizontalAlignment(0);
/* 10286 */     this.lblBFStructure.setText("0");
/* 10287 */     this.pnlBFStats.add(this.lblBFStructure, new AbsoluteConstraints(410, 60, 30, -1));
/*       */     
/* 10289 */     this.lblBFSA.setText("Placeholder");
/* 10290 */     this.pnlBFStats.add(this.lblBFSA, new AbsoluteConstraints(20, 130, 430, 20));
/*       */     
/* 10292 */     this.jLabel37.setText("Points:");
/* 10293 */     this.pnlBFStats.add(this.jLabel37, new AbsoluteConstraints(460, 30, -1, -1));
/*       */     
/* 10295 */     this.lblBFPoints.setText("0");
/* 10296 */     this.pnlBFStats.add(this.lblBFPoints, new AbsoluteConstraints(510, 30, -1, -1));
/*       */     
/* 10298 */     this.pnlBattleforce.add(this.pnlBFStats, new AbsoluteConstraints(20, 10, 690, 200));
/*       */     
/* 10300 */     this.jPanel7.setBorder(BorderFactory.createTitledBorder("Conversion Steps"));
/* 10301 */     this.jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
/*       */     
/* 10303 */     this.jTextAreaBFConversion.setColumns(20);
/* 10304 */     this.jTextAreaBFConversion.setEditable(false);
/* 10305 */     this.jTextAreaBFConversion.setRows(5);
/* 10306 */     this.jScrollPane14.setViewportView(this.jTextAreaBFConversion);
/*       */     
/* 10308 */     this.jPanel7.add(this.jScrollPane14, new AbsoluteConstraints(10, 20, 660, 190));
/*       */     
/* 10310 */     this.pnlBattleforce.add(this.jPanel7, new AbsoluteConstraints(20, 220, 690, 230));
/*       */     
/* 10312 */     this.tbpMainTabPane.addTab("BattleForce", this.pnlBattleforce);
/*       */     
/* 10314 */     gridBagConstraints = new GridBagConstraints();
/* 10315 */     gridBagConstraints.gridx = 0;
/* 10316 */     gridBagConstraints.gridy = 1;
/* 10317 */     getContentPane().add(this.tbpMainTabPane, gridBagConstraints);
/*       */     
/* 10319 */     this.pnlInfoPanel.setMaximumSize(new Dimension(32767, 26));
/* 10320 */     this.pnlInfoPanel.setMinimumSize(new Dimension(730, 26));
/* 10321 */     this.pnlInfoPanel.setLayout(new BoxLayout(this.pnlInfoPanel, 2));
/*       */     
/* 10323 */     this.txtInfoTonnage.setEditable(false);
/* 10324 */     this.txtInfoTonnage.setFont(new Font("Arial", 0, 11));
/* 10325 */     this.txtInfoTonnage.setHorizontalAlignment(0);
/* 10326 */     this.txtInfoTonnage.setText("Tons: 000.00");
/* 10327 */     this.txtInfoTonnage.setDisabledTextColor(new Color(0, 0, 0));
/* 10328 */     this.txtInfoTonnage.setMaximumSize(new Dimension(72, 20));
/* 10329 */     this.txtInfoTonnage.setMinimumSize(new Dimension(72, 20));
/* 10330 */     this.txtInfoTonnage.setPreferredSize(new Dimension(72, 20));
/* 10331 */     this.pnlInfoPanel.add(this.txtInfoTonnage);
/*       */     
/* 10333 */     this.txtInfoFreeTons.setEditable(false);
/* 10334 */     this.txtInfoFreeTons.setFont(new Font("Arial", 0, 11));
/* 10335 */     this.txtInfoFreeTons.setHorizontalAlignment(0);
/* 10336 */     this.txtInfoFreeTons.setText("Free Tons: 000.00");
/* 10337 */     this.txtInfoFreeTons.setMaximumSize(new Dimension(96, 20));
/* 10338 */     this.txtInfoFreeTons.setMinimumSize(new Dimension(96, 20));
/* 10339 */     this.txtInfoFreeTons.setPreferredSize(new Dimension(96, 20));
/* 10340 */     this.pnlInfoPanel.add(this.txtInfoFreeTons);
/*       */     
/* 10342 */     this.txtInfoMaxHeat.setEditable(false);
/* 10343 */     this.txtInfoMaxHeat.setFont(new Font("Arial", 0, 11));
/* 10344 */     this.txtInfoMaxHeat.setHorizontalAlignment(0);
/* 10345 */     this.txtInfoMaxHeat.setText("Max Heat: 000");
/* 10346 */     this.txtInfoMaxHeat.setMaximumSize(new Dimension(77, 20));
/* 10347 */     this.txtInfoMaxHeat.setMinimumSize(new Dimension(77, 20));
/* 10348 */     this.txtInfoMaxHeat.setPreferredSize(new Dimension(77, 20));
/* 10349 */     this.pnlInfoPanel.add(this.txtInfoMaxHeat);
/*       */     
/* 10351 */     this.txtInfoHeatDiss.setEditable(false);
/* 10352 */     this.txtInfoHeatDiss.setFont(new Font("Arial", 0, 11));
/* 10353 */     this.txtInfoHeatDiss.setHorizontalAlignment(0);
/* 10354 */     this.txtInfoHeatDiss.setText("Heat Dissipation: 000");
/* 10355 */     this.txtInfoHeatDiss.setMaximumSize(new Dimension(118, 20));
/* 10356 */     this.txtInfoHeatDiss.setMinimumSize(new Dimension(118, 20));
/* 10357 */     this.txtInfoHeatDiss.setPreferredSize(new Dimension(118, 20));
/* 10358 */     this.pnlInfoPanel.add(this.txtInfoHeatDiss);
/*       */     
/* 10360 */     this.txtInfoFreeCrits.setEditable(false);
/* 10361 */     this.txtInfoFreeCrits.setFont(new Font("Arial", 0, 11));
/* 10362 */     this.txtInfoFreeCrits.setHorizontalAlignment(0);
/* 10363 */     this.txtInfoFreeCrits.setText("Free Crits: -00");
/* 10364 */     this.txtInfoFreeCrits.setMaximumSize(new Dimension(77, 20));
/* 10365 */     this.txtInfoFreeCrits.setMinimumSize(new Dimension(77, 20));
/* 10366 */     this.txtInfoFreeCrits.setPreferredSize(new Dimension(77, 20));
/* 10367 */     this.pnlInfoPanel.add(this.txtInfoFreeCrits);
/*       */     
/* 10369 */     this.txtInfoUnplaced.setEditable(false);
/* 10370 */     this.txtInfoUnplaced.setFont(new Font("Arial", 0, 11));
/* 10371 */     this.txtInfoUnplaced.setHorizontalAlignment(0);
/* 10372 */     this.txtInfoUnplaced.setText("Unplaced Crits: 00");
/* 10373 */     this.txtInfoUnplaced.setMaximumSize(new Dimension(110, 20));
/* 10374 */     this.txtInfoUnplaced.setMinimumSize(new Dimension(110, 20));
/* 10375 */     this.txtInfoUnplaced.setPreferredSize(new Dimension(110, 20));
/* 10376 */     this.pnlInfoPanel.add(this.txtInfoUnplaced);
/*       */     
/* 10378 */     this.txtInfoBattleValue.setEditable(false);
/* 10379 */     this.txtInfoBattleValue.setFont(new Font("Arial", 0, 11));
/* 10380 */     this.txtInfoBattleValue.setHorizontalAlignment(0);
/* 10381 */     this.txtInfoBattleValue.setText("BV: 00,000");
/* 10382 */     this.txtInfoBattleValue.setMaximumSize(new Dimension(62, 20));
/* 10383 */     this.txtInfoBattleValue.setMinimumSize(new Dimension(62, 20));
/* 10384 */     this.txtInfoBattleValue.setPreferredSize(new Dimension(62, 20));
/* 10385 */     this.pnlInfoPanel.add(this.txtInfoBattleValue);
/*       */     
/* 10387 */     this.txtInfoCost.setEditable(false);
/* 10388 */     this.txtInfoCost.setFont(new Font("Arial", 0, 11));
/* 10389 */     this.txtInfoCost.setHorizontalAlignment(0);
/* 10390 */     this.txtInfoCost.setText("Cost: 000,000,000");
/* 10391 */     this.txtInfoCost.setMaximumSize(new Dimension(125, 20));
/* 10392 */     this.txtInfoCost.setMinimumSize(new Dimension(125, 20));
/* 10393 */     this.txtInfoCost.setPreferredSize(new Dimension(125, 20));
/* 10394 */     this.pnlInfoPanel.add(this.txtInfoCost);
/*       */     
/* 10396 */     gridBagConstraints = new GridBagConstraints();
/* 10397 */     gridBagConstraints.gridx = 0;
/* 10398 */     gridBagConstraints.gridy = 2;
/* 10399 */     gridBagConstraints.insets = new Insets(0, 0, 4, 0);
/* 10400 */     getContentPane().add(this.pnlInfoPanel, gridBagConstraints);
/*       */     
/* 10402 */     this.mnuFile.setText("File");
/* 10403 */     this.mnuFile.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10405 */         frmMain.this.mnuFileActionPerformed(evt);
/*       */       }
/*       */       
/* 10408 */     });
/* 10409 */     this.mnuNewMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(78, 8));
/* 10410 */     this.mnuNewMech.setText("New Mech");
/* 10411 */     this.mnuNewMech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10413 */         frmMain.this.mnuNewMechActionPerformed(evt);
/*       */       }
/* 10415 */     });
/* 10416 */     this.mnuFile.add(this.mnuNewMech);
/*       */     
/* 10418 */     this.mnuLoad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(76, 8));
/* 10419 */     this.mnuLoad.setText("Load Mech");
/* 10420 */     this.mnuLoad.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10422 */         frmMain.this.mnuLoadActionPerformed(evt);
/*       */       }
/* 10424 */     });
/* 10425 */     this.mnuFile.add(this.mnuLoad);
/*       */     
/* 10427 */     this.mnuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, 8));
/* 10428 */     this.mnuOpen.setText("Open");
/* 10429 */     this.mnuOpen.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10431 */         frmMain.this.mnuOpenActionPerformed(evt);
/*       */       }
/* 10433 */     });
/* 10434 */     this.mnuFile.add(this.mnuOpen);
/*       */     
/* 10436 */     this.mnuImport.setText("Import Mech...");
/*       */     
/* 10438 */     this.mnuImportHMP.setText("from Heavy Metal Pro (HMP)");
/* 10439 */     this.mnuImportHMP.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10441 */         frmMain.this.mnuImportHMPActionPerformed(evt);
/*       */       }
/* 10443 */     });
/* 10444 */     this.mnuImport.add(this.mnuImportHMP);
/*       */     
/* 10446 */     this.mnuBatchHMP.setText("Batch Import HMP Files");
/* 10447 */     this.mnuBatchHMP.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10449 */         frmMain.this.mnuBatchHMPActionPerformed(evt);
/*       */       }
/* 10451 */     });
/* 10452 */     this.mnuImport.add(this.mnuBatchHMP);
/*       */     
/* 10454 */     this.mnuFile.add(this.mnuImport);
/* 10455 */     this.mnuFile.add(this.jSeparator16);
/*       */     
/* 10457 */     this.mnuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(83, 8));
/* 10458 */     this.mnuSave.setText("Save Mech");
/* 10459 */     this.mnuSave.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10461 */         frmMain.this.mnuSaveActionPerformed(evt);
/*       */       }
/* 10463 */     });
/* 10464 */     this.mnuFile.add(this.mnuSave);
/*       */     
/* 10466 */     this.mnuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(83, 10));
/* 10467 */     this.mnuSaveAs.setText("Save Mech As...");
/* 10468 */     this.mnuSaveAs.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10470 */         frmMain.this.mnuSaveAsActionPerformed(evt);
/*       */       }
/* 10472 */     });
/* 10473 */     this.mnuFile.add(this.mnuSaveAs);
/*       */     
/* 10475 */     this.mnuExport.setText("Export Mech...");
/*       */     
/* 10477 */     this.mnuExportHTML.setText("to HTML (Web)");
/* 10478 */     this.mnuExportHTML.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10480 */         frmMain.this.mnuExportHTMLActionPerformed(evt);
/*       */       }
/* 10482 */     });
/* 10483 */     this.mnuExport.add(this.mnuExportHTML);
/*       */     
/* 10485 */     this.mnuExportMTF.setText("to MTF (MegaMek)");
/* 10486 */     this.mnuExportMTF.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10488 */         frmMain.this.mnuExportMTFActionPerformed(evt);
/*       */       }
/* 10490 */     });
/* 10491 */     this.mnuExport.add(this.mnuExportMTF);
/*       */     
/* 10493 */     this.mnuExportTXT.setText("to TXT (Text)");
/* 10494 */     this.mnuExportTXT.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10496 */         frmMain.this.mnuExportTXTActionPerformed(evt);
/*       */       }
/* 10498 */     });
/* 10499 */     this.mnuExport.add(this.mnuExportTXT);
/*       */     
/* 10501 */     this.mnuExportClipboard.setText("to Clipboard (Text)");
/* 10502 */     this.mnuExportClipboard.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10504 */         frmMain.this.mnuExportClipboardActionPerformed(evt);
/*       */       }
/* 10506 */     });
/* 10507 */     this.mnuExport.add(this.mnuExportClipboard);
/*       */     
/* 10509 */     this.mnuCreateTCGMech.setText("to TCG Format (Card)");
/* 10510 */     this.mnuCreateTCGMech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10512 */         frmMain.this.mnuCreateTCGMechActionPerformed(evt);
/*       */       }
/* 10514 */     });
/* 10515 */     this.mnuExport.add(this.mnuCreateTCGMech);
/*       */     
/* 10517 */     this.mnuFile.add(this.mnuExport);
/* 10518 */     this.mnuFile.add(this.jSeparator20);
/*       */     
/* 10520 */     this.mnuPrint.setText("Print");
/*       */     
/* 10522 */     this.mnuPrintCurrentMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 2));
/* 10523 */     this.mnuPrintCurrentMech.setText("Current Mech");
/* 10524 */     this.mnuPrintCurrentMech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10526 */         frmMain.this.mnuPrintCurrentMechActionPerformed(evt);
/*       */       }
/* 10528 */     });
/* 10529 */     this.mnuPrint.add(this.mnuPrintCurrentMech);
/*       */     
/* 10531 */     this.mnuPrintSavedMech.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 3));
/* 10532 */     this.mnuPrintSavedMech.setText("Saved Mech");
/* 10533 */     this.mnuPrintSavedMech.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10535 */         frmMain.this.mnuPrintSavedMechActionPerformed(evt);
/*       */       }
/* 10537 */     });
/* 10538 */     this.mnuPrint.add(this.mnuPrintSavedMech);
/*       */     
/* 10540 */     this.mnuPrintBatch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(66, 3));
/* 10541 */     this.mnuPrintBatch.setText("Batch Print Mechs");
/* 10542 */     this.mnuPrintBatch.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10544 */         frmMain.this.mnuPrintBatchActionPerformed(evt);
/*       */       }
/* 10546 */     });
/* 10547 */     this.mnuPrint.add(this.mnuPrintBatch);
/*       */     
/* 10549 */     this.mnuFile.add(this.mnuPrint);
/*       */     
/* 10551 */     this.mnuPrintPreview.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 10));
/* 10552 */     this.mnuPrintPreview.setText("Print Preview");
/* 10553 */     this.mnuPrintPreview.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10555 */         frmMain.this.mnuPrintPreviewActionPerformed(evt);
/*       */       }
/* 10557 */     });
/* 10558 */     this.mnuFile.add(this.mnuPrintPreview);
/*       */     
/* 10560 */     this.mnuPostS7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(80, 8));
/* 10561 */     this.mnuPostS7.setText("Post Mech to Solaris7.com");
/* 10562 */     this.mnuPostS7.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10564 */         frmMain.this.mnuPostS7ActionPerformed(evt);
/*       */       }
/* 10566 */     });
/* 10567 */     this.mnuFile.add(this.mnuPostS7);
/* 10568 */     this.mnuFile.add(this.jSeparator17);
/*       */     
/* 10570 */     this.mnuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(88, 8));
/* 10571 */     this.mnuExit.setText("Exit");
/* 10572 */     this.mnuExit.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10574 */         frmMain.this.mnuExitActionPerformed(evt);
/*       */       }
/* 10576 */     });
/* 10577 */     this.mnuFile.add(this.mnuExit);
/*       */     
/* 10579 */     this.mnuMainMenu.add(this.mnuFile);
/*       */     
/* 10581 */     this.mnuClearFluff.setText("Tools");
/*       */     
/* 10583 */     this.mnuSummary.setAccelerator(javax.swing.KeyStroke.getKeyStroke(85, 8));
/* 10584 */     this.mnuSummary.setText("Show Summary");
/* 10585 */     this.mnuSummary.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10587 */         frmMain.this.mnuSummaryActionPerformed(evt);
/*       */       }
/* 10589 */     });
/* 10590 */     this.mnuClearFluff.add(this.mnuSummary);
/*       */     
/* 10592 */     this.mnuCostBVBreakdown.setText("Cost/BV Breakdown");
/* 10593 */     this.mnuCostBVBreakdown.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10595 */         frmMain.this.mnuCostBVBreakdownActionPerformed(evt);
/*       */       }
/* 10597 */     });
/* 10598 */     this.mnuClearFluff.add(this.mnuCostBVBreakdown);
/*       */     
/* 10600 */     this.mnuTextTRO.setText("Show Text TRO Format");
/* 10601 */     this.mnuTextTRO.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10603 */         frmMain.this.mnuTextTROActionPerformed(evt);
/*       */       }
/* 10605 */     });
/* 10606 */     this.mnuClearFluff.add(this.mnuTextTRO);
/* 10607 */     this.mnuClearFluff.add(this.jSeparator15);
/*       */     
/* 10609 */     this.mnuBFB.setText("Load Force Balancer");
/* 10610 */     this.mnuBFB.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10612 */         frmMain.this.mnuBFBActionPerformed(evt);
/*       */       }
/* 10614 */     });
/* 10615 */     this.mnuClearFluff.add(this.mnuBFB);
/* 10616 */     this.mnuClearFluff.add(this.jSeparator27);
/*       */     
/* 10618 */     this.mnuOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, 8));
/* 10619 */     this.mnuOptions.setText("Preferences");
/* 10620 */     this.mnuOptions.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10622 */         frmMain.this.mnuOptionsActionPerformed(evt);
/*       */       }
/* 10624 */     });
/* 10625 */     this.mnuClearFluff.add(this.mnuOptions);
/*       */     
/* 10627 */     this.mnuViewToolbar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(84, 8));
/* 10628 */     this.mnuViewToolbar.setSelected(true);
/* 10629 */     this.mnuViewToolbar.setText("View Toolbar");
/* 10630 */     this.mnuViewToolbar.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10632 */         frmMain.this.mnuViewToolbarActionPerformed(evt);
/*       */       }
/* 10634 */     });
/* 10635 */     this.mnuClearFluff.add(this.mnuViewToolbar);
/*       */     
/* 10637 */     this.mnuClearUserData.setText("Clear User Data");
/* 10638 */     this.mnuClearUserData.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10640 */         frmMain.this.mnuClearUserDataActionPerformed(evt);
/*       */       }
/* 10642 */     });
/* 10643 */     this.mnuClearFluff.add(this.mnuClearUserData);
/* 10644 */     this.mnuClearFluff.add(this.jSeparator30);
/*       */     
/* 10646 */     this.mnuUnlock.setText("Unlock Chassis");
/* 10647 */     this.mnuUnlock.setEnabled(false);
/* 10648 */     this.mnuUnlock.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10650 */         frmMain.this.mnuUnlockActionPerformed(evt);
/*       */       }
/* 10652 */     });
/* 10653 */     this.mnuClearFluff.add(this.mnuUnlock);
/*       */     
/* 10655 */     this.jMenuItem1.setText("Clear All Fluff");
/* 10656 */     this.jMenuItem1.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10658 */         frmMain.this.jMenuItem1ActionPerformed(evt);
/*       */       }
/* 10660 */     });
/* 10661 */     this.mnuClearFluff.add(this.jMenuItem1);
/*       */     
/* 10663 */     this.mnuMainMenu.add(this.mnuClearFluff);
/*       */     
/* 10665 */     this.mnuHelp.setText("Help");
/*       */     
/* 10667 */     this.mnuCredits.setText("Credits");
/* 10668 */     this.mnuCredits.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10670 */         frmMain.this.mnuCreditsActionPerformed(evt);
/*       */       }
/* 10672 */     });
/* 10673 */     this.mnuHelp.add(this.mnuCredits);
/*       */     
/* 10675 */     this.mnuAboutSSW.setText("About SSW");
/* 10676 */     this.mnuAboutSSW.addActionListener(new ActionListener() {
/*       */       public void actionPerformed(ActionEvent evt) {
/* 10678 */         frmMain.this.mnuAboutSSWActionPerformed(evt);
/*       */       }
/* 10680 */     });
/* 10681 */     this.mnuHelp.add(this.mnuAboutSSW);
/*       */     
/* 10683 */     this.mnuMainMenu.add(this.mnuHelp);
/*       */     
/* 10685 */     setJMenuBar(this.mnuMainMenu);
/*       */     
/* 10687 */     pack();
/*       */   }
/*       */   
/*       */   private void mnuExitActionPerformed(ActionEvent evt) {
/* 10691 */     if (this.CurMech.HasChanged()) {
/* 10692 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 10694 */       if (choice == 1) return;
/*       */     }
/* 10696 */     CloseProgram();
/*       */   }
/*       */   
/*       */   private void CloseProgram() {
/*       */     try {
/* 10701 */       if (this.BatchWindow != null) this.BatchWindow.dispose();
/* 10702 */       if (this.dOpen != null) this.dOpen.dispose();
/* 10703 */       if (this.dForce != null) this.dForce.dispose();
/*       */     } catch (Exception e) {
/* 10705 */       System.out.println(e.getMessage());
/*       */     }
/* 10707 */     System.out.flush();
/*       */     
/* 10709 */     System.exit(0);
/*       */   }
/*       */   
/*       */ 
/*       */   private void btnLoadImageActionPerformed(ActionEvent evt)
/*       */   {
/* 10715 */     JFileChooser fc = new JFileChooser();
/*       */     
/*       */ 
/* 10718 */     ImageIcon newFluffImage = (ImageIcon)this.lblFluffImage.getIcon();
/*       */     
/*       */ 
/*       */ 
/* 10722 */     fc.addChoosableFileFilter(new ImageFilter());
/* 10723 */     fc.setAcceptAllFileFilterUsed(false);
/* 10724 */     if (!this.Prefs.get("LastImagePath", "").isEmpty()) {
/* 10725 */       fc.setCurrentDirectory(new File(this.Prefs.get("LastImagePath", "")));
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 10733 */     fc.setAccessory(new ImagePreview(fc));
/*       */     
/*       */ 
/* 10736 */     int returnVal = fc.showDialog(this, "Attach");
/*       */     
/*       */ 
/* 10739 */     if (returnVal == 0) {
/*       */       try
/*       */       {
/* 10742 */         this.Prefs.put("LastImagePath", fc.getSelectedFile().getCanonicalPath().replace(fc.getSelectedFile().getName(), ""));
/* 10743 */         this.Prefs.put("LastImageFile", fc.getSelectedFile().getName());
/*       */         
/* 10745 */         newFluffImage = new ImageIcon(fc.getSelectedFile().getPath());
/*       */         
/* 10747 */         if (newFluffImage == null) { return;
/*       */         }
/* 10749 */         int h = newFluffImage.getIconHeight();
/* 10750 */         int w = newFluffImage.getIconWidth();
/* 10751 */         if ((w > 290) || (h > 350)) {
/* 10752 */           if (w > h)
/*       */           {
/* 10754 */             newFluffImage = new ImageIcon(newFluffImage.getImage().getScaledInstance(290, -1, 1));
/*       */           }
/*       */           else {
/* 10757 */             newFluffImage = new ImageIcon(newFluffImage.getImage().getScaledInstance(-1, 350, 1));
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
/* 10768 */     this.lblFluffImage.setIcon(newFluffImage);
/* 10769 */     this.CurMech.SetSSWImage(fc.getSelectedFile().getPath());
/*       */   }
/*       */   
/*       */   private void btnClearImageActionPerformed(ActionEvent evt)
/*       */   {
/* 10774 */     this.lblFluffImage.setIcon(null);
/* 10775 */     this.CurMech.SetSSWImage("");
/*       */   }
/*       */   
/*       */   private void cmbHeatSinkTypeActionPerformed(ActionEvent evt) {
/* 10779 */     if (BuildLookupName(this.CurMech.GetHeatSinks().GetCurrentState()).equals((String)this.cmbHeatSinkType.getSelectedItem())) {
/* 10780 */       return;
/*       */     }
/* 10782 */     RecalcHeatSinks();
/* 10783 */     RefreshSummary();
/* 10784 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnNumberOfHSStateChanged(ChangeEvent evt)
/*       */   {
/* 10789 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnNumberOfHS.getModel();
/* 10790 */     int NumHS = this.CurMech.GetHeatSinks().GetNumHS();
/* 10791 */     javax.swing.JComponent editor = this.spnNumberOfHS.getEditor();
/* 10792 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 10796 */       this.spnNumberOfHS.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 10800 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 10801 */         tf.setValue(this.spnNumberOfHS.getValue());
/*       */       }
/* 10803 */       return;
/*       */     }
/*       */     
/* 10806 */     if (n.getNumber().intValue() > NumHS)
/*       */     {
/* 10808 */       for (int i = NumHS; i < n.getNumber().intValue(); i++) {
/* 10809 */         this.CurMech.GetHeatSinks().IncrementNumHS();
/*       */       }
/*       */       
/*       */     } else {
/* 10813 */       for (int i = NumHS; i > n.getNumber().intValue(); i--) {
/* 10814 */         this.CurMech.GetHeatSinks().DecrementNumHS();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 10819 */     RefreshSummary();
/* 10820 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbMechEraActionPerformed(ActionEvent evt) {
/* 10824 */     if (this.Load) { return;
/*       */     }
/*       */     
/* 10827 */     if (this.CurMech.GetEra() == this.cmbMechEra.getSelectedIndex()) {
/* 10828 */       return;
/*       */     }
/* 10830 */     if ((this.CurMech.IsOmnimech()) && 
/* 10831 */       (this.cmbMechEra.getSelectedIndex() < this.CurMech.GetBaseEra())) {
/* 10832 */       Media.Messager(this, "An OmniMech loadout cannot have an era lower than the main loadout.");
/* 10833 */       this.cmbMechEra.setSelectedIndex(this.CurMech.GetBaseEra());
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/* 10839 */     int tbsave = this.cmbTechBase.getSelectedIndex();
/*       */     
/*       */ 
/* 10842 */     switch (this.cmbMechEra.getSelectedIndex()) {
/*       */     case 0: 
/* 10844 */       this.lblEraYears.setText("2443 ~ 2800");
/* 10845 */       this.txtProdYear.setText("");
/* 10846 */       this.CurMech.SetEra(0);
/* 10847 */       this.CurMech.SetYear(2750, false);
/* 10848 */       if (!this.CurMech.IsOmnimech()) this.chkYearRestrict.setEnabled(true);
/*       */       break;
/*       */     case 1: 
/* 10851 */       this.lblEraYears.setText("2801 ~ 3050");
/* 10852 */       this.txtProdYear.setText("");
/* 10853 */       this.CurMech.SetEra(1);
/* 10854 */       this.CurMech.SetYear(3025, false);
/* 10855 */       if (!this.CurMech.IsOmnimech()) this.chkYearRestrict.setEnabled(true);
/*       */       break;
/*       */     case 2: 
/* 10858 */       this.lblEraYears.setText("3051 ~ 3085");
/* 10859 */       this.txtProdYear.setText("");
/* 10860 */       this.CurMech.SetEra(2);
/* 10861 */       this.CurMech.SetYear(3075, false);
/* 10862 */       if (!this.CurMech.IsOmnimech()) this.chkYearRestrict.setEnabled(true);
/*       */       break;
/*       */     case 3: 
/* 10865 */       this.lblEraYears.setText("3086 on");
/* 10866 */       this.txtProdYear.setText("");
/* 10867 */       this.CurMech.SetEra(3);
/* 10868 */       this.CurMech.SetYear(3132, false);
/* 10869 */       if (!this.CurMech.IsOmnimech()) this.chkYearRestrict.setEnabled(true);
/*       */       break;
/*       */     case 4: 
/* 10872 */       this.lblEraYears.setText("Any");
/* 10873 */       this.txtProdYear.setText("");
/* 10874 */       this.CurMech.SetEra(4);
/* 10875 */       this.CurMech.SetYear(0, false);
/* 10876 */       this.chkYearRestrict.setEnabled(false);
/*       */     }
/*       */     
/*       */     
/* 10880 */     if (this.CurMech.IsOmnimech()) {
/* 10881 */       BuildJumpJetSelector();
/* 10882 */       RefreshEquipment();
/* 10883 */       RefreshSummary();
/* 10884 */       RefreshInfoPane();
/* 10885 */       SetWeaponChoosers();
/* 10886 */       ResetAmmo();
/* 10887 */       return;
/*       */     }
/*       */     
/* 10890 */     BuildTechBaseSelector();
/* 10891 */     BuildMechTypeSelector();
/*       */     
/*       */ 
/* 10894 */     if (tbsave < this.cmbTechBase.getItemCount())
/*       */     {
/* 10896 */       this.cmbTechBase.setSelectedIndex(tbsave);
/*       */     }
/*       */     else
/*       */     {
/* 10900 */       this.cmbTechBase.setSelectedIndex(0);
/* 10901 */       this.CurMech.SetInnerSphere();
/*       */     }
/*       */     
/*       */ 
/* 10905 */     SaveSelections();
/*       */     
/*       */ 
/* 10908 */     BuildChassisSelector();
/* 10909 */     BuildEngineSelector();
/* 10910 */     BuildGyroSelector();
/* 10911 */     BuildCockpitSelector();
/* 10912 */     BuildEnhancementSelector();
/* 10913 */     BuildHeatsinkSelector();
/* 10914 */     BuildJumpJetSelector();
/* 10915 */     BuildArmorSelector();
/* 10916 */     FixWalkMPSpinner();
/* 10917 */     FixJJSpinnerModel();
/* 10918 */     RefreshEquipment();
/* 10919 */     CheckOmnimech();
/*       */     
/*       */ 
/* 10922 */     LoadSelections();
/*       */     
/*       */ 
/* 10925 */     RecalcEngine();
/* 10926 */     RecalcGyro();
/* 10927 */     RecalcIntStruc();
/* 10928 */     RecalcCockpit();
/* 10929 */     this.CurMech.GetActuators().PlaceActuators();
/* 10930 */     RecalcHeatSinks();
/* 10931 */     RecalcJumpJets();
/* 10932 */     RecalcEnhancements();
/* 10933 */     RecalcArmor();
/* 10934 */     RecalcEquipment();
/*       */     
/*       */ 
/*       */ 
/* 10938 */     this.CurMech.GetLoadout().FlushIllegal();
/*       */     
/*       */ 
/*       */ 
/* 10942 */     RefreshSummary();
/* 10943 */     RefreshInfoPane();
/* 10944 */     SetWeaponChoosers();
/* 10945 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   private void spnWalkMPStateChanged(ChangeEvent evt)
/*       */   {
/* 10950 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnWalkMP.getModel();
/* 10951 */     javax.swing.JComponent editor = this.spnWalkMP.getEditor();
/* 10952 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 10956 */       this.spnWalkMP.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 10960 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 10961 */         tf.setValue(this.spnWalkMP.getValue());
/*       */       }
/* 10963 */       return;
/*       */     }
/*       */     try
/*       */     {
/* 10967 */       this.CurMech.SetWalkMP(n.getNumber().intValue());
/*       */     } catch (Exception e) {
/* 10969 */       Media.Messager(e.getMessage());
/* 10970 */       this.spnWalkMP.setValue(this.spnWalkMP.getPreviousValue());
/*       */     }
/* 10972 */     this.lblRunMP.setText("" + this.CurMech.GetRunningMP());
/*       */     
/*       */ 
/*       */ 
/* 10976 */     FixJJSpinnerModel();
/* 10977 */     this.CurMech.GetHeatSinks().ReCalculate();
/* 10978 */     this.CurMech.GetLoadout().UnallocateFuelTanks();
/*       */     
/*       */ 
/* 10981 */     RefreshSummary();
/* 10982 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnJumpMPStateChanged(ChangeEvent evt)
/*       */   {
/* 10987 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnJumpMP.getModel();
/* 10988 */     javax.swing.JComponent editor = this.spnJumpMP.getEditor();
/* 10989 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/* 10990 */     int NumJJ = this.CurMech.GetJumpJets().GetNumJJ();
/*       */     
/*       */     try
/*       */     {
/* 10994 */       this.spnWalkMP.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 10998 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 10999 */         tf.setValue(this.spnWalkMP.getValue());
/*       */       }
/* 11001 */       return;
/*       */     }
/*       */     
/* 11004 */     if (n.getNumber().intValue() > NumJJ)
/*       */     {
/* 11006 */       for (int i = NumJJ; i < n.getNumber().intValue(); i++) {
/* 11007 */         this.CurMech.GetJumpJets().IncrementNumJJ();
/*       */       }
/*       */       
/*       */     } else {
/* 11011 */       for (int i = NumJJ; i > n.getNumber().intValue(); i--) {
/* 11012 */         this.CurMech.GetJumpJets().DecrementNumJJ();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 11017 */     if (n.getNumber().intValue() > 0)
/*       */     {
/* 11019 */       this.txtJJModel.setEnabled(true);
/*       */     }
/*       */     else {
/* 11022 */       this.txtJJModel.setEnabled(false);
/*       */     }
/*       */     
/*       */ 
/* 11026 */     RefreshSummary();
/* 11027 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */ 
/*       */   private void cmbTonnageActionPerformed(ActionEvent evt)
/*       */   {
/* 11033 */     int Tons = 0;
/* 11034 */     switch (this.cmbTonnage.getSelectedIndex())
/*       */     {
/*       */     case 0: 
/* 11037 */       this.lblMechType.setText("Ultralight Mech");
/* 11038 */       Tons = 10;
/* 11039 */       break;
/*       */     
/*       */     case 1: 
/* 11042 */       this.lblMechType.setText("Ultralight Mech");
/* 11043 */       Tons = 15;
/* 11044 */       break;
/*       */     
/*       */     case 2: 
/* 11047 */       this.lblMechType.setText("Light Mech");
/* 11048 */       Tons = 20;
/* 11049 */       break;
/*       */     
/*       */     case 3: 
/* 11052 */       this.lblMechType.setText("Light Mech");
/* 11053 */       Tons = 25;
/* 11054 */       break;
/*       */     
/*       */     case 4: 
/* 11057 */       this.lblMechType.setText("Light Mech");
/* 11058 */       Tons = 30;
/* 11059 */       break;
/*       */     
/*       */     case 5: 
/* 11062 */       this.lblMechType.setText("Light Mech");
/* 11063 */       Tons = 35;
/* 11064 */       break;
/*       */     
/*       */     case 6: 
/* 11067 */       this.lblMechType.setText("Medium Mech");
/* 11068 */       Tons = 40;
/* 11069 */       break;
/*       */     
/*       */     case 7: 
/* 11072 */       this.lblMechType.setText("Medium Mech");
/* 11073 */       Tons = 45;
/* 11074 */       break;
/*       */     
/*       */     case 8: 
/* 11077 */       this.lblMechType.setText("Medium Mech");
/* 11078 */       Tons = 50;
/* 11079 */       break;
/*       */     
/*       */     case 9: 
/* 11082 */       this.lblMechType.setText("Medium Mech");
/* 11083 */       Tons = 55;
/* 11084 */       break;
/*       */     
/*       */     case 10: 
/* 11087 */       this.lblMechType.setText("Heavy Mech");
/* 11088 */       Tons = 60;
/* 11089 */       break;
/*       */     
/*       */     case 11: 
/* 11092 */       this.lblMechType.setText("Heavy Mech");
/* 11093 */       Tons = 65;
/* 11094 */       break;
/*       */     
/*       */     case 12: 
/* 11097 */       this.lblMechType.setText("Heavy Mech");
/* 11098 */       Tons = 70;
/* 11099 */       break;
/*       */     
/*       */     case 13: 
/* 11102 */       this.lblMechType.setText("Heavy Mech");
/* 11103 */       Tons = 75;
/* 11104 */       break;
/*       */     
/*       */     case 14: 
/* 11107 */       this.lblMechType.setText("Assault Mech");
/* 11108 */       Tons = 80;
/* 11109 */       break;
/*       */     
/*       */     case 15: 
/* 11112 */       this.lblMechType.setText("Assault Mech");
/* 11113 */       Tons = 85;
/* 11114 */       break;
/*       */     
/*       */     case 16: 
/* 11117 */       this.lblMechType.setText("Assault Mech");
/* 11118 */       Tons = 90;
/* 11119 */       break;
/*       */     
/*       */     case 17: 
/* 11122 */       this.lblMechType.setText("Assault Mech");
/* 11123 */       Tons = 95;
/* 11124 */       break;
/*       */     
/*       */     case 18: 
/* 11127 */       this.lblMechType.setText("Assault Mech");
/* 11128 */       Tons = 100;
/*       */     }
/*       */     
/*       */     
/* 11132 */     if (this.CurMech.GetTonnage() == Tons) {
/* 11133 */       return;
/*       */     }
/* 11135 */     this.CurMech.SetTonnage(Tons);
/*       */     
/*       */ 
/*       */ 
/* 11139 */     CheckTonnage(false);
/*       */     
/*       */ 
/* 11142 */     FixWalkMPSpinner();
/* 11143 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 11146 */     this.CurMech.GetHeatSinks().ReCalculate();
/* 11147 */     this.CurMech.GetArmor().Recalculate();
/*       */     
/*       */ 
/* 11150 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 11153 */     this.CurMech.CheckPhysicals();
/*       */     
/*       */ 
/* 11156 */     CheckAES();
/*       */     
/*       */ 
/* 11159 */     RefreshInternalPoints();
/* 11160 */     RefreshSummary();
/* 11161 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbTechBaseActionPerformed(ActionEvent evt) {
/* 11165 */     if (this.Load) { return;
/*       */     }
/* 11167 */     if (this.CurMech.IsOmnimech()) {
/* 11168 */       if (this.CurMech.GetLoadout().GetTechBase() != this.cmbTechBase.getSelectedIndex()) {}
/*       */     }
/* 11170 */     else if (this.CurMech.GetTechbase() == this.cmbTechBase.getSelectedIndex()) { return;
/*       */     }
/*       */     
/* 11173 */     if (this.CurMech.IsOmnimech()) {
/* 11174 */       boolean check = this.CurMech.SetTechBase(this.cmbTechBase.getSelectedIndex());
/* 11175 */       if (!check) {
/* 11176 */         Media.Messager(this, "An OmniMech can only use the base chassis' Tech Base\nor Mixed Tech.  Resetting.");
/* 11177 */         this.cmbTechBase.setSelectedIndex(this.CurMech.GetLoadout().GetTechBase());
/* 11178 */         return;
/*       */       }
/* 11180 */       RefreshEquipment();
/*       */     }
/*       */     else {
/* 11183 */       switch (this.cmbTechBase.getSelectedIndex()) {
/*       */       case 0: 
/* 11185 */         this.CurMech.SetInnerSphere();
/* 11186 */         break;
/*       */       case 1: 
/* 11188 */         this.CurMech.SetClan();
/* 11189 */         break;
/*       */       case 2: 
/* 11191 */         this.CurMech.SetMixed();
/*       */       }
/*       */       
/*       */       
/*       */ 
/*       */ 
/* 11197 */       SaveSelections();
/*       */       
/* 11199 */       this.data.Rebuild(this.CurMech);
/*       */       
/*       */ 
/* 11202 */       BuildChassisSelector();
/* 11203 */       BuildEngineSelector();
/* 11204 */       BuildGyroSelector();
/* 11205 */       BuildCockpitSelector();
/* 11206 */       BuildEnhancementSelector();
/* 11207 */       BuildHeatsinkSelector();
/* 11208 */       BuildJumpJetSelector();
/* 11209 */       BuildArmorSelector();
/* 11210 */       RefreshEquipment();
/* 11211 */       FixWalkMPSpinner();
/* 11212 */       FixJJSpinnerModel();
/* 11213 */       CheckOmnimech();
/*       */       
/*       */ 
/* 11216 */       if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 11217 */         this.CurMech.GetLoadout().SetClanCASE(true);
/*       */       }
/*       */       
/*       */ 
/* 11221 */       LoadSelections();
/*       */       
/*       */ 
/* 11224 */       RecalcEngine();
/* 11225 */       RecalcGyro();
/* 11226 */       RecalcIntStruc();
/* 11227 */       RecalcCockpit();
/* 11228 */       this.CurMech.GetActuators().PlaceActuators();
/* 11229 */       RecalcHeatSinks();
/* 11230 */       RecalcJumpJets();
/* 11231 */       RecalcEnhancements();
/* 11232 */       RecalcArmor();
/*       */     }
/*       */     
/* 11235 */     RecalcEquipment();
/* 11236 */     SetWeaponChoosers();
/* 11237 */     this.chkUseTC.setSelected(false);
/*       */     
/*       */ 
/* 11240 */     RefreshSummary();
/* 11241 */     RefreshInfoPane();
/* 11242 */     SetWeaponChoosers();
/*       */   }
/*       */   
/*       */   private void cmbPhysEnhanceActionPerformed(ActionEvent evt) {
/* 11246 */     if (BuildLookupName(this.CurMech.GetPhysEnhance().GetCurrentState()).equals((String)this.cmbPhysEnhance.getSelectedItem())) {
/* 11247 */       return;
/*       */     }
/*       */     
/* 11250 */     RecalcEnhancements();
/*       */     
/*       */     try
/*       */     {
/* 11254 */       this.CurMech.GetLoadout().CheckExclusions(this.CurMech.GetPhysEnhance());
/*       */     } catch (Exception e) {
/* 11256 */       Media.Messager(this, e.getMessage());
/* 11257 */       this.cmbPhysEnhance.setSelectedItem("No Enhancement");
/* 11258 */       RecalcEnhancements();
/* 11259 */       return;
/*       */     }
/*       */     
/* 11262 */     this.lblRunMP.setText("" + this.CurMech.GetRunningMP());
/*       */     
/*       */ 
/* 11265 */     RefreshSummary();
/* 11266 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbCockpitTypeActionPerformed(ActionEvent evt) {
/* 11270 */     if (this.CurMech.GetCockpit().LookupName().equals((String)this.cmbCockpitType.getSelectedItem())) {
/* 11271 */       return;
/*       */     }
/* 11273 */     RecalcCockpit();
/*       */     
/*       */ 
/* 11276 */     RefreshEquipment();
/* 11277 */     RefreshSummary();
/* 11278 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbGyroTypeActionPerformed(ActionEvent evt) {
/* 11282 */     if (BuildLookupName(this.CurMech.GetGyro().GetCurrentState()).equals((String)this.cmbGyroType.getSelectedItem())) {
/* 11283 */       return;
/*       */     }
/* 11285 */     RecalcGyro();
/*       */     
/*       */ 
/* 11288 */     RefreshSummary();
/* 11289 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbEngineTypeActionPerformed(ActionEvent evt) {
/* 11293 */     if (BuildLookupName(this.CurMech.GetEngine().GetCurrentState()).equals((String)this.cmbEngineType.getSelectedItem()))
/*       */     {
/* 11295 */       if (this.CurMech.GetEngine().IsNuclear()) {
/* 11296 */         if (this.cmbJumpJetType.getSelectedItem() == null) {
/* 11297 */           EnableJumpJets(false);
/*       */         } else {
/* 11299 */           EnableJumpJets(true);
/*       */         }
/*       */       } else {
/* 11302 */         EnableJumpJets(false);
/*       */       }
/* 11304 */       return;
/*       */     }
/* 11306 */     RecalcEngine();
/*       */     
/*       */ 
/* 11309 */     if (this.CurMech.GetEngine().IsNuclear()) {
/* 11310 */       if (this.cmbJumpJetType.getSelectedItem() == null) {
/* 11311 */         EnableJumpJets(false);
/*       */       } else {
/* 11313 */         EnableJumpJets(true);
/*       */       }
/*       */     } else {
/* 11316 */       EnableJumpJets(false);
/*       */     }
/*       */     
/*       */ 
/* 11320 */     if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/* 11321 */       this.Equipment[7] = { " " };
/*       */     } else {
/* 11323 */       this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */     }
/* 11325 */     this.lstSelectedEquipment.setListData(this.Equipment[7]);
/*       */     
/*       */ 
/* 11328 */     RefreshSummary();
/* 11329 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbInternalTypeActionPerformed(ActionEvent evt) {
/* 11333 */     if (BuildLookupName(this.CurMech.GetIntStruc().GetCurrentState()).equals((String)this.cmbInternalType.getSelectedItem())) {
/* 11334 */       return;
/*       */     }
/* 11336 */     RecalcIntStruc();
/*       */     
/*       */ 
/* 11339 */     RefreshSummary();
/* 11340 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbMotiveTypeActionPerformed(ActionEvent evt) {
/* 11344 */     switch (this.cmbMotiveType.getSelectedIndex())
/*       */     {
/*       */     case 0: 
/* 11347 */       if ((this.CurMech.GetLoadout() instanceof components.BipedLoadout)) {
/* 11348 */         return;
/*       */       }
/* 11350 */       if (this.CurMech.GetCockpit().CritName().equals("Robotic Cockpit")) {
/* 11351 */         this.cmbCockpitType.setSelectedItem("Standard Cockpit");
/* 11352 */         RecalcCockpit();
/*       */       }
/*       */       
/* 11355 */       this.CurMech.SetBiped();
/*       */       
/*       */ 
/* 11358 */       this.cmbInternalType.setSelectedIndex(0);
/* 11359 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("LA");
/* 11360 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("RA");
/* 11361 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("LL");
/* 11362 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RL");
/* 11363 */       this.pnlCLArmorBox.setVisible(false);
/* 11364 */       this.pnlCLCrits.setVisible(false);
/* 11365 */       this.scrRACrits.setPreferredSize(new Dimension(105, 170));
/* 11366 */       this.scrLACrits.setPreferredSize(new Dimension(105, 170));
/* 11367 */       break;
/*       */     
/*       */     case 1: 
/* 11370 */       if ((this.CurMech.GetLoadout() instanceof components.QuadLoadout))
/* 11371 */         return;
/* 11372 */       this.CurMech.SetQuad();
/*       */       
/*       */ 
/* 11375 */       this.cmbInternalType.setSelectedIndex(0);
/* 11376 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("FLL");
/* 11377 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("FRL");
/* 11378 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("RLL");
/* 11379 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RRL");
/* 11380 */       this.pnlCLArmorBox.setVisible(false);
/* 11381 */       this.pnlCLCrits.setVisible(false);
/* 11382 */       this.scrRACrits.setPreferredSize(new Dimension(105, 87));
/* 11383 */       this.scrLACrits.setPreferredSize(new Dimension(105, 87));
/* 11384 */       break;
/*       */     case 2: 
/* 11386 */       if ((this.CurMech.GetLoadout() instanceof components.TripodLoadout))
/* 11387 */         return;
/* 11388 */       this.CurMech.SetTripod();
/*       */       
/*       */ 
/* 11391 */       this.cmbInternalType.setSelectedIndex(0);
/* 11392 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("LA");
/* 11393 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("RA");
/* 11394 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("LL");
/* 11395 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RL");
/* 11396 */       this.pnlCLArmorBox.setVisible(true);
/* 11397 */       this.pnlCLCrits.setVisible(true);
/* 11398 */       this.scrRACrits.setPreferredSize(new Dimension(105, 170));
/* 11399 */       this.scrLACrits.setPreferredSize(new Dimension(105, 170));
/*       */     }
/*       */     
/*       */     
/* 11403 */     SetLoadoutArrays();
/*       */     
/*       */ 
/* 11406 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 11409 */     CheckAES();
/*       */     
/*       */ 
/* 11412 */     RefreshSummary();
/* 11413 */     RefreshInfoPane();
/* 11414 */     RefreshInternalPoints();
/* 11415 */     SetWeaponChoosers();
/*       */   }
/*       */   
/*       */   private void mnuCreditsActionPerformed(ActionEvent evt) {
/* 11419 */     dlgCredits Credits = new dlgCredits(this, true);
/* 11420 */     Credits.setLocationRelativeTo(this);
/* 11421 */     Credits.setVisible(true);
/*       */   }
/*       */   
/*       */   private void cmbArmorTypeActionPerformed(ActionEvent evt) {
/* 11425 */     if (BuildLookupName(this.CurMech.GetArmor().GetCurrentState()).equals((String)this.cmbArmorType.getSelectedItem())) {
/* 11426 */       return;
/*       */     }
/* 11428 */     RecalcArmor();
/*       */     
/* 11430 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 11433 */     RefreshSummary();
/* 11434 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void mnuOptionsActionPerformed(ActionEvent evt) {
/* 11438 */     dlgPrefs preferences = new dlgPrefs(this, true);
/* 11439 */     preferences.setLocationRelativeTo(this);
/* 11440 */     preferences.setVisible(true);
/* 11441 */     this.Mechrender.Reset();
/* 11442 */     ResetAmmo();
/* 11443 */     RefreshInternalPoints();
/* 11444 */     RefreshSummary();
/* 11445 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnHDArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11450 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 11451 */     javax.swing.JComponent editor = this.spnHDArmor.getEditor();
/* 11452 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11456 */       this.spnHDArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11460 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11461 */         tf.setValue(this.spnHDArmor.getValue());
/*       */       }
/* 11463 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11467 */     MechArmor a = this.CurMech.GetArmor();
/* 11468 */     int curmech = a.GetLocationArmor(0);
/* 11469 */     int curframe = n.getNumber().intValue();
/* 11470 */     if (curframe > curmech) {
/* 11471 */       while (curframe > curmech) {
/* 11472 */         a.IncrementArmor(0);
/* 11473 */         curframe--;
/*       */       }
/*       */     }
/* 11476 */     while (curmech > curframe) {
/* 11477 */       a.DecrementArmor(0);
/* 11478 */       curmech = a.GetLocationArmor(0);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11483 */     RefreshSummary();
/* 11484 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnRAArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11489 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRAArmor.getModel();
/* 11490 */     javax.swing.JComponent editor = this.spnRAArmor.getEditor();
/* 11491 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11495 */       this.spnRAArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11499 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11500 */         tf.setValue(this.spnRAArmor.getValue());
/*       */       }
/* 11502 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11506 */     MechArmor a = this.CurMech.GetArmor();
/* 11507 */     int curmech = a.GetLocationArmor(5);
/* 11508 */     int curframe = n.getNumber().intValue();
/* 11509 */     if (curframe > curmech) {
/* 11510 */       while (curframe > curmech) {
/* 11511 */         a.IncrementArmor(5);
/* 11512 */         curframe--;
/*       */       }
/*       */     }
/* 11515 */     while (curmech > curframe) {
/* 11516 */       a.DecrementArmor(5);
/* 11517 */       curmech = a.GetLocationArmor(5);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11522 */     if (this.btnBalanceArmor.isSelected()) {
/* 11523 */       a.SetArmor(4, n.getNumber().intValue());
/* 11524 */       n = (SpinnerNumberModel)this.spnLAArmor.getModel();
/* 11525 */       n.setValue(Integer.valueOf(a.GetLocationArmor(4)));
/*       */     }
/*       */     
/*       */ 
/* 11529 */     RefreshSummary();
/* 11530 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnRTArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11535 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRTArmor.getModel();
/* 11536 */     javax.swing.JComponent editor = this.spnRTArmor.getEditor();
/* 11537 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11541 */       this.spnRTArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11545 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11546 */         tf.setValue(this.spnRTArmor.getValue());
/*       */       }
/* 11548 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11552 */     MechArmor a = this.CurMech.GetArmor();
/* 11553 */     int curmech = a.GetLocationArmor(3);
/* 11554 */     int curframe = n.getNumber().intValue();
/* 11555 */     if (curframe > curmech) {
/* 11556 */       while (curframe > curmech) {
/* 11557 */         a.IncrementArmor(3);
/* 11558 */         curframe--;
/*       */       }
/*       */     }
/* 11561 */     while (curmech > curframe) {
/* 11562 */       a.DecrementArmor(3);
/* 11563 */       curmech = a.GetLocationArmor(3);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11568 */     n = (SpinnerNumberModel)this.spnRTRArmor.getModel();
/* 11569 */     n.setValue(Integer.valueOf(a.GetLocationArmor(10)));
/*       */     
/*       */ 
/* 11572 */     if (this.btnBalanceArmor.isSelected()) {
/* 11573 */       n = (SpinnerNumberModel)this.spnRTArmor.getModel();
/* 11574 */       a.SetArmor(2, n.getNumber().intValue());
/* 11575 */       n = (SpinnerNumberModel)this.spnLTArmor.getModel();
/* 11576 */       n.setValue(Integer.valueOf(a.GetLocationArmor(2)));
/*       */     }
/*       */     
/*       */ 
/* 11580 */     RefreshSummary();
/* 11581 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnRTRArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11586 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRTRArmor.getModel();
/* 11587 */     javax.swing.JComponent editor = this.spnRTRArmor.getEditor();
/* 11588 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11592 */       this.spnRTRArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11596 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11597 */         tf.setValue(this.spnRTRArmor.getValue());
/*       */       }
/* 11599 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11603 */     MechArmor a = this.CurMech.GetArmor();
/* 11604 */     int curmech = a.GetLocationArmor(10);
/* 11605 */     int curframe = n.getNumber().intValue();
/* 11606 */     if (curframe > curmech) {
/* 11607 */       while (curframe > curmech) {
/* 11608 */         a.IncrementArmor(10);
/* 11609 */         curframe--;
/*       */       }
/*       */     }
/* 11612 */     while (curmech > curframe) {
/* 11613 */       a.DecrementArmor(10);
/* 11614 */       curmech = a.GetLocationArmor(10);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11619 */     n = (SpinnerNumberModel)this.spnRTArmor.getModel();
/* 11620 */     n.setValue(Integer.valueOf(a.GetLocationArmor(3)));
/*       */     
/*       */ 
/*       */ 
/* 11624 */     if (this.btnBalanceArmor.isSelected()) {
/* 11625 */       n = (SpinnerNumberModel)this.spnRTRArmor.getModel();
/* 11626 */       a.SetArmor(9, n.getNumber().intValue());
/* 11627 */       n = (SpinnerNumberModel)this.spnLTRArmor.getModel();
/* 11628 */       n.setValue(Integer.valueOf(a.GetLocationArmor(9)));
/*       */     }
/*       */     
/*       */ 
/* 11632 */     RefreshSummary();
/* 11633 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnLAArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11638 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLAArmor.getModel();
/* 11639 */     javax.swing.JComponent editor = this.spnLAArmor.getEditor();
/* 11640 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11644 */       this.spnLAArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11648 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11649 */         tf.setValue(this.spnLAArmor.getValue());
/*       */       }
/* 11651 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11655 */     MechArmor a = this.CurMech.GetArmor();
/* 11656 */     int curmech = a.GetLocationArmor(4);
/* 11657 */     int curframe = n.getNumber().intValue();
/* 11658 */     if (curframe > curmech) {
/* 11659 */       while (curframe > curmech) {
/* 11660 */         a.IncrementArmor(4);
/* 11661 */         curframe--;
/*       */       }
/*       */     }
/* 11664 */     while (curmech > curframe) {
/* 11665 */       a.DecrementArmor(4);
/* 11666 */       curmech = a.GetLocationArmor(4);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11671 */     if (this.btnBalanceArmor.isSelected()) {
/* 11672 */       a.SetArmor(5, n.getNumber().intValue());
/* 11673 */       n = (SpinnerNumberModel)this.spnRAArmor.getModel();
/* 11674 */       n.setValue(Integer.valueOf(a.GetLocationArmor(5)));
/*       */     }
/*       */     
/*       */ 
/* 11678 */     RefreshSummary();
/* 11679 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnLTArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11684 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLTArmor.getModel();
/* 11685 */     javax.swing.JComponent editor = this.spnLTArmor.getEditor();
/* 11686 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11690 */       this.spnLTArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11694 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11695 */         tf.setValue(this.spnLTArmor.getValue());
/*       */       }
/* 11697 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11701 */     MechArmor a = this.CurMech.GetArmor();
/* 11702 */     int curmech = a.GetLocationArmor(2);
/* 11703 */     int curframe = n.getNumber().intValue();
/* 11704 */     if (curframe > curmech) {
/* 11705 */       while (curframe > curmech) {
/* 11706 */         a.IncrementArmor(2);
/* 11707 */         curframe--;
/*       */       }
/*       */     }
/* 11710 */     while (curmech > curframe) {
/* 11711 */       a.DecrementArmor(2);
/* 11712 */       curmech = a.GetLocationArmor(2);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11717 */     n = (SpinnerNumberModel)this.spnLTRArmor.getModel();
/* 11718 */     n.setValue(Integer.valueOf(a.GetLocationArmor(9)));
/*       */     
/*       */ 
/* 11721 */     if (this.btnBalanceArmor.isSelected()) {
/* 11722 */       n = (SpinnerNumberModel)this.spnLTArmor.getModel();
/* 11723 */       a.SetArmor(3, n.getNumber().intValue());
/* 11724 */       n = (SpinnerNumberModel)this.spnRTArmor.getModel();
/* 11725 */       n.setValue(Integer.valueOf(a.GetLocationArmor(3)));
/*       */     }
/*       */     
/*       */ 
/* 11729 */     RefreshSummary();
/* 11730 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnLTRArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11735 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLTRArmor.getModel();
/* 11736 */     javax.swing.JComponent editor = this.spnLTRArmor.getEditor();
/* 11737 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11741 */       this.spnLTRArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11745 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11746 */         tf.setValue(this.spnLTRArmor.getValue());
/*       */       }
/* 11748 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11752 */     MechArmor a = this.CurMech.GetArmor();
/* 11753 */     int curmech = a.GetLocationArmor(9);
/* 11754 */     int curframe = n.getNumber().intValue();
/* 11755 */     if (curframe > curmech) {
/* 11756 */       while (curframe > curmech) {
/* 11757 */         a.IncrementArmor(9);
/* 11758 */         curframe--;
/*       */       }
/*       */     }
/* 11761 */     while (curmech > curframe) {
/* 11762 */       a.DecrementArmor(9);
/* 11763 */       curmech = a.GetLocationArmor(9);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11768 */     n = (SpinnerNumberModel)this.spnLTArmor.getModel();
/* 11769 */     n.setValue(Integer.valueOf(a.GetLocationArmor(2)));
/*       */     
/*       */ 
/* 11772 */     if (this.btnBalanceArmor.isSelected()) {
/* 11773 */       n = (SpinnerNumberModel)this.spnLTRArmor.getModel();
/* 11774 */       a.SetArmor(10, n.getNumber().intValue());
/* 11775 */       n = (SpinnerNumberModel)this.spnRTRArmor.getModel();
/* 11776 */       n.setValue(Integer.valueOf(a.GetLocationArmor(10)));
/*       */     }
/*       */     
/*       */ 
/* 11780 */     RefreshSummary();
/* 11781 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnCTArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11786 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnCTArmor.getModel();
/* 11787 */     javax.swing.JComponent editor = this.spnCTArmor.getEditor();
/* 11788 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11792 */       this.spnCTArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11796 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11797 */         tf.setValue(this.spnCTArmor.getValue());
/*       */       }
/* 11799 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11803 */     MechArmor a = this.CurMech.GetArmor();
/* 11804 */     int curmech = a.GetLocationArmor(1);
/* 11805 */     int curframe = n.getNumber().intValue();
/* 11806 */     if (curframe > curmech) {
/* 11807 */       while (curframe > curmech) {
/* 11808 */         a.IncrementArmor(1);
/* 11809 */         curframe--;
/*       */       }
/*       */     }
/* 11812 */     while (curmech > curframe) {
/* 11813 */       a.DecrementArmor(1);
/* 11814 */       curmech = a.GetLocationArmor(1);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11819 */     n = (SpinnerNumberModel)this.spnCTRArmor.getModel();
/* 11820 */     n.setValue(Integer.valueOf(a.GetLocationArmor(8)));
/*       */     
/*       */ 
/* 11823 */     RefreshSummary();
/* 11824 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnCTRArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11829 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnCTRArmor.getModel();
/* 11830 */     javax.swing.JComponent editor = this.spnCTRArmor.getEditor();
/* 11831 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11835 */       this.spnCTRArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11839 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11840 */         tf.setValue(this.spnCTRArmor.getValue());
/*       */       }
/* 11842 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11846 */     MechArmor a = this.CurMech.GetArmor();
/* 11847 */     int curmech = a.GetLocationArmor(8);
/* 11848 */     int curframe = n.getNumber().intValue();
/* 11849 */     if (curframe > curmech) {
/* 11850 */       while (curframe > curmech) {
/* 11851 */         a.IncrementArmor(8);
/* 11852 */         curframe--;
/*       */       }
/*       */     }
/* 11855 */     while (curmech > curframe) {
/* 11856 */       a.DecrementArmor(8);
/* 11857 */       curmech = a.GetLocationArmor(8);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11862 */     n = (SpinnerNumberModel)this.spnCTArmor.getModel();
/* 11863 */     n.setValue(Integer.valueOf(a.GetLocationArmor(1)));
/*       */     
/*       */ 
/* 11866 */     RefreshSummary();
/* 11867 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnLLArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11872 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnLLArmor.getModel();
/* 11873 */     javax.swing.JComponent editor = this.spnLLArmor.getEditor();
/* 11874 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11878 */       this.spnLLArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11882 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11883 */         tf.setValue(this.spnLLArmor.getValue());
/*       */       }
/* 11885 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11889 */     MechArmor a = this.CurMech.GetArmor();
/* 11890 */     int curmech = a.GetLocationArmor(6);
/* 11891 */     int curframe = n.getNumber().intValue();
/* 11892 */     if (curframe > curmech) {
/* 11893 */       while (curframe > curmech) {
/* 11894 */         a.IncrementArmor(6);
/* 11895 */         curframe--;
/*       */       }
/*       */     }
/* 11898 */     while (curmech > curframe) {
/* 11899 */       a.DecrementArmor(6);
/* 11900 */       curmech = a.GetLocationArmor(6);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11905 */     if (this.btnBalanceArmor.isSelected()) {
/* 11906 */       a.SetArmor(7, n.getNumber().intValue());
/* 11907 */       n = (SpinnerNumberModel)this.spnRLArmor.getModel();
/* 11908 */       n.setValue(Integer.valueOf(a.GetLocationArmor(7)));
/*       */     }
/*       */     
/*       */ 
/* 11912 */     RefreshSummary();
/* 11913 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnCLArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 11918 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnCLArmor.getModel();
/* 11919 */     javax.swing.JComponent editor = this.spnCLArmor.getEditor();
/* 11920 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 11924 */       this.spnCLArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 11928 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 11929 */         tf.setValue(this.spnCLArmor.getValue());
/*       */       }
/* 11931 */       return;
/*       */     }
/*       */     
/*       */ 
/* 11935 */     MechArmor a = this.CurMech.GetArmor();
/* 11936 */     int curmech = a.GetLocationArmor(11);
/* 11937 */     int curframe = n.getNumber().intValue();
/* 11938 */     if (curframe > curmech) {
/* 11939 */       while (curframe > curmech) {
/* 11940 */         a.IncrementArmor(11);
/* 11941 */         curframe--;
/*       */       }
/*       */     }
/* 11944 */     while (curmech > curframe) {
/* 11945 */       a.DecrementArmor(11);
/* 11946 */       curmech = a.GetLocationArmor(11);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11951 */     if (this.btnBalanceArmor.isSelected()) {
/* 11952 */       a.SetArmor(6, n.getNumber().intValue());
/* 11953 */       n = (SpinnerNumberModel)this.spnLLArmor.getModel();
/* 11954 */       n.setValue(Integer.valueOf(a.GetLocationArmor(6)));
/*       */     }
/*       */     
/*       */ 
/* 11958 */     RefreshSummary();
/* 11959 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnMaxArmorActionPerformed(ActionEvent evt)
/*       */   {
/* 11964 */     MechArmor a = this.CurMech.GetArmor();
/*       */     
/*       */ 
/* 11967 */     a.SetArmor(0, 9);
/* 11968 */     a.SetArmor(4, a.GetLocationMax(4));
/* 11969 */     a.SetArmor(5, a.GetLocationMax(5));
/* 11970 */     a.SetArmor(6, a.GetLocationMax(6));
/* 11971 */     a.SetArmor(7, a.GetLocationMax(7));
/*       */     
/*       */ 
/* 11974 */     int rear = Math.round(a.GetLocationMax(1) * this.Prefs.getInt("ArmorCTRPercent", 25) / 100);
/* 11975 */     a.SetArmor(8, rear);
/* 11976 */     a.SetArmor(1, a.GetLocationMax(1) - rear);
/* 11977 */     rear = Math.round(a.GetLocationMax(2) * this.Prefs.getInt("ArmorSTRPercent", 25) / 100);
/* 11978 */     a.SetArmor(9, rear);
/* 11979 */     a.SetArmor(2, a.GetLocationMax(2) - rear);
/* 11980 */     rear = Math.round(a.GetLocationMax(3) * this.Prefs.getInt("ArmorSTRPercent", 25) / 100);
/* 11981 */     a.SetArmor(10, rear);
/* 11982 */     a.SetArmor(3, a.GetLocationMax(3) - rear);
/*       */     
/*       */ 
/* 11985 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 11988 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 11989 */     n.setValue(Integer.valueOf(a.GetLocationArmor(0)));
/*       */     
/*       */ 
/* 11992 */     RefreshSummary();
/* 11993 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnArmorTonsActionPerformed(ActionEvent evt)
/*       */   {
/* 11998 */     dlgArmorTonnage ArmorDialogue = new dlgArmorTonnage(this, true);
/* 11999 */     ArmorDialogue.setLocationRelativeTo(this);
/* 12000 */     ArmorDialogue.setVisible(true);
/*       */     
/*       */ 
/* 12003 */     if (ArmorDialogue.NewTonnage()) {
/* 12004 */       double result = ArmorDialogue.GetResult();
/* 12005 */       this.ArmorTons.SetArmorTonnage(result);
/*       */       try {
/* 12007 */         this.CurMech.Visit(this.ArmorTons);
/*       */       }
/*       */       catch (Exception e) {
/* 12010 */         System.err.println(e.getMessage());
/* 12011 */         e.printStackTrace();
/*       */       }
/*       */       
/* 12014 */       ArmorDialogue.dispose();
/*       */     } else {
/* 12016 */       ArmorDialogue.dispose();
/*       */     }
/*       */     
/*       */ 
/* 12020 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 12023 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 12024 */     n.setValue(Integer.valueOf(this.CurMech.GetArmor().GetLocationArmor(0)));
/*       */     
/*       */ 
/* 12027 */     RefreshSummary();
/* 12028 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLAHandActionPerformed(ActionEvent evt) {
/* 12032 */     if (this.chkLAHand.isSelected() == this.CurMech.GetActuators().LeftHandInstalled()) {
/* 12033 */       return;
/*       */     }
/* 12035 */     if (this.chkLAHand.isSelected())
/*       */     {
/* 12037 */       abPlaceable[] check = this.CurMech.GetLoadout().GetLACrits();
/* 12038 */       for (int i = 0; i < check.length; i++) {
/* 12039 */         if (((check[i] instanceof ifWeapon)) && 
/* 12040 */           (((ifWeapon)check[i]).OmniRestrictActuators()) && (this.CurMech.IsOmnimech())) {
/* 12041 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the hand.");
/* 12042 */           this.chkLAHand.setSelected(false);
/* 12043 */           return;
/*       */         }
/*       */         
/* 12046 */         if (((check[i] instanceof PhysicalWeapon)) && 
/* 12047 */           (((PhysicalWeapon)check[i]).ReplacesHand())) {
/* 12048 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the hand.");
/* 12049 */           this.chkLAHand.setSelected(false);
/* 12050 */           return;
/*       */         }
/*       */       }
/*       */       
/* 12054 */       this.CurMech.GetActuators().AddLeftHand();
/*       */     } else {
/* 12056 */       this.CurMech.GetActuators().RemoveLeftHand();
/*       */       
/* 12058 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 12059 */       for (int i = 0; i < v.size(); i++) {
/* 12060 */         abPlaceable p = (abPlaceable)v.get(i);
/* 12061 */         if (((p instanceof PhysicalWeapon)) && 
/* 12062 */           (((PhysicalWeapon)p).RequiresHand()) && 
/* 12063 */           (this.CurMech.GetLoadout().Find(p) == 4)) {
/* 12064 */           this.CurMech.GetLoadout().UnallocateAll(p, false);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 12070 */     CheckActuators();
/* 12071 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLALowerArmActionPerformed(ActionEvent evt) {
/* 12075 */     if (this.chkLALowerArm.isSelected() == this.CurMech.GetActuators().LeftLowerInstalled()) {
/* 12076 */       return;
/*       */     }
/* 12078 */     if (this.chkLALowerArm.isSelected())
/*       */     {
/* 12080 */       abPlaceable[] check = this.CurMech.GetLoadout().GetLACrits();
/* 12081 */       for (int i = 0; i < check.length; i++) {
/* 12082 */         if (((check[i] instanceof ifWeapon)) && 
/* 12083 */           (((ifWeapon)check[i]).OmniRestrictActuators()) && (this.CurMech.IsOmnimech())) {
/* 12084 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the lower arm.");
/* 12085 */           this.chkLALowerArm.setSelected(false);
/* 12086 */           return;
/*       */         }
/*       */         
/* 12089 */         if (((check[i] instanceof PhysicalWeapon)) && 
/* 12090 */           (((PhysicalWeapon)check[i]).ReplacesLowerArm())) {
/* 12091 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the lower arm.");
/* 12092 */           this.chkLALowerArm.setSelected(false);
/* 12093 */           return;
/*       */         }
/*       */       }
/*       */       
/* 12097 */       this.CurMech.GetActuators().AddLeftLowerArm();
/*       */     } else {
/* 12099 */       this.CurMech.GetActuators().RemoveLeftLowerArm();
/*       */       
/* 12101 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 12102 */       for (int i = 0; i < v.size(); i++) {
/* 12103 */         abPlaceable p = (abPlaceable)v.get(i);
/* 12104 */         if (((p instanceof PhysicalWeapon)) && 
/* 12105 */           (((PhysicalWeapon)p).RequiresLowerArm()) && 
/* 12106 */           (this.CurMech.GetLoadout().Find(p) == 4)) {
/* 12107 */           this.CurMech.GetLoadout().UnallocateAll(p, false);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 12113 */     CheckActuators();
/* 12114 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRAHandActionPerformed(ActionEvent evt) {
/* 12118 */     if (this.chkRAHand.isSelected() == this.CurMech.GetActuators().RightHandInstalled()) {
/* 12119 */       return;
/*       */     }
/* 12121 */     if (this.chkRAHand.isSelected())
/*       */     {
/* 12123 */       abPlaceable[] check = this.CurMech.GetLoadout().GetRACrits();
/* 12124 */       for (int i = 0; i < check.length; i++) {
/* 12125 */         if (((check[i] instanceof ifWeapon)) && 
/* 12126 */           (((ifWeapon)check[i]).OmniRestrictActuators()) && (this.CurMech.IsOmnimech())) {
/* 12127 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the hand.");
/* 12128 */           this.chkRAHand.setSelected(false);
/* 12129 */           return;
/*       */         }
/*       */         
/* 12132 */         if (((check[i] instanceof PhysicalWeapon)) && 
/* 12133 */           (((PhysicalWeapon)check[i]).ReplacesHand())) {
/* 12134 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the hand.");
/* 12135 */           this.chkRAHand.setSelected(false);
/* 12136 */           return;
/*       */         }
/*       */       }
/*       */       
/* 12140 */       this.CurMech.GetActuators().AddRightHand();
/*       */     } else {
/* 12142 */       this.CurMech.GetActuators().RemoveRightHand();
/*       */       
/* 12144 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 12145 */       for (int i = 0; i < v.size(); i++) {
/* 12146 */         abPlaceable p = (abPlaceable)v.get(i);
/* 12147 */         if (((p instanceof PhysicalWeapon)) && 
/* 12148 */           (((PhysicalWeapon)p).RequiresHand()) && 
/* 12149 */           (this.CurMech.GetLoadout().Find(p) == 5)) {
/* 12150 */           this.CurMech.GetLoadout().UnallocateAll(p, false);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 12156 */     CheckActuators();
/* 12157 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRALowerArmActionPerformed(ActionEvent evt) {
/* 12161 */     if (this.chkRALowerArm.isSelected() == this.CurMech.GetActuators().RightLowerInstalled()) {
/* 12162 */       return;
/*       */     }
/* 12164 */     if (this.chkRALowerArm.isSelected())
/*       */     {
/* 12166 */       abPlaceable[] check = this.CurMech.GetLoadout().GetRACrits();
/* 12167 */       for (int i = 0; i < check.length; i++) {
/* 12168 */         if (((check[i] instanceof ifWeapon)) && 
/* 12169 */           (((ifWeapon)check[i]).OmniRestrictActuators()) && (this.CurMech.IsOmnimech())) {
/* 12170 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the lower arm.");
/* 12171 */           this.chkRALowerArm.setSelected(false);
/* 12172 */           return;
/*       */         }
/*       */         
/* 12175 */         if (((check[i] instanceof PhysicalWeapon)) && 
/* 12176 */           (((PhysicalWeapon)check[i]).ReplacesLowerArm())) {
/* 12177 */           Media.Messager(this, check[i].LookupName() + " prevents the installation of the lower arm.");
/* 12178 */           this.chkRALowerArm.setSelected(false);
/* 12179 */           return;
/*       */         }
/*       */       }
/*       */       
/* 12183 */       this.CurMech.GetActuators().AddRightLowerArm();
/*       */     } else {
/* 12185 */       this.CurMech.GetActuators().RemoveRightLowerArm();
/*       */       
/* 12187 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 12188 */       for (int i = 0; i < v.size(); i++) {
/* 12189 */         abPlaceable p = (abPlaceable)v.get(i);
/* 12190 */         if (((p instanceof PhysicalWeapon)) && 
/* 12191 */           (((PhysicalWeapon)p).RequiresLowerArm()) && 
/* 12192 */           (this.CurMech.GetLoadout().Find(p) == 5)) {
/* 12193 */           this.CurMech.GetLoadout().UnallocateAll(p, false);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 12199 */     CheckActuators();
/* 12200 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkCTCASEActionPerformed(ActionEvent evt) {
/* 12204 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasCTCASE())) {
/* 12205 */       this.chkCTCASE.setSelected(true);
/* 12206 */       return;
/*       */     }
/* 12208 */     if (this.CurMech.HasCTCase() == this.chkCTCASE.isSelected()) return;
/* 12209 */     if (this.chkCTCASE.isSelected()) {
/*       */       try {
/* 12211 */         this.CurMech.AddCTCase();
/*       */       } catch (Exception e) {
/* 12213 */         Media.Messager(this, e.getMessage());
/* 12214 */         this.chkCTCASE.setSelected(false);
/*       */       }
/*       */     } else {
/* 12217 */       this.CurMech.RemoveCTCase();
/*       */     }
/* 12219 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRTCASEActionPerformed(ActionEvent evt) {
/* 12223 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRTCASE())) {
/* 12224 */       this.chkRTCASE.setSelected(true);
/* 12225 */       return;
/*       */     }
/* 12227 */     if (this.CurMech.HasRTCase() == this.chkRTCASE.isSelected()) return;
/* 12228 */     if (this.chkRTCASE.isSelected()) {
/*       */       try {
/* 12230 */         this.CurMech.AddRTCase();
/*       */       } catch (Exception e) {
/* 12232 */         Media.Messager(this, e.getMessage());
/* 12233 */         this.chkRTCASE.setSelected(false);
/*       */       }
/*       */     } else {
/* 12236 */       this.CurMech.RemoveRTCase();
/*       */     }
/* 12238 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLTCASEActionPerformed(ActionEvent evt) {
/* 12242 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasLTCASE())) {
/* 12243 */       this.chkLTCASE.setSelected(true);
/* 12244 */       return;
/*       */     }
/* 12246 */     if (this.CurMech.HasLTCase() == this.chkLTCASE.isSelected()) return;
/* 12247 */     if (this.chkLTCASE.isSelected()) {
/*       */       try {
/* 12249 */         this.CurMech.AddLTCase();
/*       */       } catch (Exception e) {
/* 12251 */         Media.Messager(this, e.getMessage());
/* 12252 */         this.chkLTCASE.setSelected(false);
/*       */       }
/*       */     } else {
/* 12255 */       this.CurMech.RemoveLTCase();
/*       */     }
/* 12257 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnRemoveEquipActionPerformed(ActionEvent evt) {
/* 12261 */     if (this.lstSelectedEquipment.getSelectedIndex() < 0) return;
/* 12262 */     int[] selected = this.lstSelectedEquipment.getSelectedIndices();
/* 12263 */     if (selected.length == 0) { return;
/*       */     }
/* 12265 */     for (int i = selected.length - 1; i >= 0; i--)
/*       */     {
/* 12267 */       abPlaceable p = (abPlaceable)this.CurMech.GetLoadout().GetNonCore().get(selected[i]);
/* 12268 */       if ((p.LocationLocked() & !(p instanceof components.Talons))) {
/* 12269 */         Media.Messager(this, "You may not remove a locked item from the loadout.");
/* 12270 */         return;
/*       */       }
/* 12272 */       this.CurMech.GetLoadout().Remove(p);
/*       */     }
/*       */     
/*       */ 
/* 12276 */     if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/* 12277 */       this.Equipment[7] = { " " };
/*       */     } else {
/* 12279 */       this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */     }
/* 12281 */     this.lstSelectedEquipment.setListData(this.Equipment[7]);
/*       */     
/*       */ 
/* 12284 */     if (this.CurMech.UsingTC()) {
/* 12285 */       this.CurMech.UnallocateTC();
/*       */     }
/*       */     
/*       */ 
/* 12289 */     ResetAmmo();
/*       */     
/*       */ 
/* 12292 */     RefreshSummary();
/* 12293 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnAddEquipActionPerformed(ActionEvent evt) {
/* 12297 */     abPlaceable a = null;
/* 12298 */     int Index = 0;
/*       */     
/*       */ 
/*       */ 
/* 12302 */     switch (this.tbpWeaponChooser.getSelectedIndex()) {
/*       */     case 0: 
/* 12304 */       if (this.lstChooseBallistic.getSelectedIndex() >= 0) {
/* 12305 */         a = (abPlaceable)this.Equipment[0][this.lstChooseBallistic.getSelectedIndex()];
/* 12306 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12307 */       break;
/*       */     case 1: 
/* 12309 */       if (this.lstChooseEnergy.getSelectedIndex() >= 0) {
/* 12310 */         a = (abPlaceable)this.Equipment[1][this.lstChooseEnergy.getSelectedIndex()];
/* 12311 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12312 */       break;
/*       */     case 2: 
/* 12314 */       if (this.lstChooseMissile.getSelectedIndex() >= 0) {
/* 12315 */         a = (abPlaceable)this.Equipment[2][this.lstChooseMissile.getSelectedIndex()];
/* 12316 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech);
/* 12317 */         if (((RangedWeapon)a).IsFCSCapable()) {
/* 12318 */           if ((this.CurMech.UsingArtemisIV()) && (
/* 12319 */             (((RangedWeapon)a).GetFCSType() == 1) || (((RangedWeapon)a).GetFCSType() == 2))) {
/* 12320 */             ((RangedWeapon)a).UseFCS(true, 1);
/*       */           }
/*       */           
/* 12323 */           if ((this.CurMech.UsingArtemisV()) && 
/* 12324 */             (((RangedWeapon)a).GetFCSType() == 2)) {
/* 12325 */             ((RangedWeapon)a).UseFCS(true, 2);
/*       */           }
/*       */           
/* 12328 */           if ((this.CurMech.UsingApollo()) && 
/* 12329 */             (((RangedWeapon)a).GetFCSType() == 3)) {
/* 12330 */             ((RangedWeapon)a).UseFCS(true, 3);
/*       */           }
/*       */         }
/*       */       }
/*       */       break;
/*       */     case 3: 
/* 12336 */       if ((this.lstChoosePhysical.getSelectedIndex() >= 0) && 
/* 12337 */         ((this.Equipment[3][this.lstChoosePhysical.getSelectedIndex()] instanceof abPlaceable)))
/*       */       {
/*       */ 
/* 12340 */         a = (abPlaceable)this.Equipment[3][this.lstChoosePhysical.getSelectedIndex()];
/* 12341 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12342 */       break;
/*       */     case 5: 
/* 12344 */       if ((this.lstChooseArtillery.getSelectedIndex() >= 0) && 
/* 12345 */         ((this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()] instanceof abPlaceable)))
/*       */       {
/*       */ 
/* 12348 */         a = (abPlaceable)this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()];
/* 12349 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12350 */       break;
/*       */     case 4: 
/* 12352 */       if ((this.lstChooseEquipment.getSelectedIndex() >= 0) && 
/* 12353 */         ((this.Equipment[4][this.lstChooseEquipment.getSelectedIndex()] instanceof abPlaceable)))
/*       */       {
/*       */ 
/* 12356 */         a = (abPlaceable)this.Equipment[4][this.lstChooseEquipment.getSelectedIndex()];
/* 12357 */         a = this.data.GetEquipment().GetCopy(a, this.CurMech); }
/* 12358 */       break;
/*       */     case 6: 
/* 12360 */       if (this.lstChooseAmmunition.getSelectedIndex() >= 0) {
/* 12361 */         Index = this.lstChooseAmmunition.getSelectedIndex();
/* 12362 */         if ((this.Equipment[6][Index] instanceof abPlaceable))
/*       */         {
/*       */ 
/* 12365 */           a = (abPlaceable)this.Equipment[6][Index];
/* 12366 */           a = this.data.GetEquipment().GetCopy(a, this.CurMech);
/*       */         }
/*       */       }
/*       */       break;
/*       */     }
/* 12371 */     if (a != null) {
/*       */       try {
/* 12373 */         this.CurMech.GetLoadout().CheckExclusions(a);
/* 12374 */         if (((a instanceof Equipment)) && 
/* 12375 */           (!((Equipment)a).Validate(this.CurMech))) {
/* 12376 */           if (((Equipment)a).RequiresQuad())
/* 12377 */             throw new Exception(a.CritName() + " may only be mounted on a quad 'Mech.");
/* 12378 */           if (((Equipment)a).MaxAllowed() > 0) {
/* 12379 */             throw new Exception("Only " + ((Equipment)a).MaxAllowed() + " " + a.CritName() + "(s) may be mounted on one 'Mech.");
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception e) {
/* 12384 */         Media.Messager(e.getMessage());
/* 12385 */         a = null;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 12390 */     if (a != null) {
/* 12391 */       boolean result = true;
/* 12392 */       if (((a instanceof Equipment)) && 
/* 12393 */         (((Equipment)a).IsVariableSize())) {
/* 12394 */         dlgVariableSize SetTons = new dlgVariableSize(this, true, (Equipment)a);
/* 12395 */         SetTons.setLocationRelativeTo(this);
/* 12396 */         SetTons.setVisible(true);
/* 12397 */         result = SetTons.GetResult();
/*       */       }
/*       */       
/* 12400 */       if (result) {
/* 12401 */         if ((a instanceof components.Talons)) {
/* 12402 */           if (!a.Place(this.CurMech.GetLoadout())) {
/* 12403 */             Media.Messager("Talons cannot be added because there is not enough space.");
/*       */           }
/*       */         }
/*       */         else {
/* 12407 */           this.CurMech.GetLoadout().AddToQueue(a);
/* 12408 */           for (int i = 0; i < this.cmbNumEquips.getSelectedIndex(); i++) {
/* 12409 */             a = this.data.GetEquipment().GetCopy(a, this.CurMech);
/* 12410 */             this.CurMech.GetLoadout().AddToQueue(a);
/*       */           }
/*       */         }
/*       */         
/*       */ 
/* 12415 */         if (((a instanceof ifWeapon)) && 
/* 12416 */           (((ifWeapon)a).IsTCCapable()) && (this.CurMech.UsingTC())) {
/* 12417 */           this.CurMech.UnallocateTC();
/*       */         }
/*       */         
/*       */ 
/*       */ 
/* 12422 */         ResetAmmo();
/*       */         
/* 12424 */         if ((a instanceof Ammunition))
/*       */         {
/*       */ 
/* 12427 */           this.lstChooseAmmunition.setSelectedIndex(Index);
/*       */         }
/*       */         
/*       */ 
/* 12431 */         if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/* 12432 */           this.Equipment[7] = { " " };
/*       */         } else {
/* 12434 */           this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */         }
/* 12436 */         this.lstSelectedEquipment.setListData(this.Equipment[7]);
/*       */       }
/*       */       
/*       */ 
/* 12440 */       RefreshSummary();
/* 12441 */       RefreshInfoPane();
/* 12442 */       this.cmbNumEquips.setSelectedIndex(0);
/*       */     }
/*       */   }
/*       */   
/*       */   private void btnClearEquipActionPerformed(ActionEvent evt) {
/* 12447 */     this.CurMech.GetLoadout().SafeClearLoadout();
/*       */     
/*       */ 
/* 12450 */     if (this.CurMech.GetLoadout().GetNonCore().toArray().length <= 0) {
/* 12451 */       this.Equipment[7] = { " " };
/*       */     } else {
/* 12453 */       this.Equipment[7] = this.CurMech.GetLoadout().GetNonCore().toArray();
/*       */     }
/* 12455 */     this.lstSelectedEquipment.setListData(this.Equipment[7]);
/*       */     
/*       */ 
/* 12458 */     if (this.CurMech.UsingTC()) {
/* 12459 */       this.CurMech.CheckTC();
/*       */     }
/*       */     
/*       */ 
/* 12463 */     ResetAmmo();
/*       */     
/*       */ 
/* 12466 */     RefreshSummary();
/* 12467 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnClearLoadoutActionPerformed(ActionEvent evt) {
/* 12471 */     this.CurMech.GetLoadout().SafeMassUnallocate();
/*       */     
/*       */ 
/* 12474 */     RefreshSummary();
/* 12475 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void lstChooseMissileValueChanged(ListSelectionEvent evt) {
/* 12479 */     if (this.lstChooseMissile.getSelectedIndex() < 0) return;
/* 12480 */     abPlaceable p = (abPlaceable)this.Equipment[2][this.lstChooseMissile.getSelectedIndex()];
/* 12481 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void lstChooseEnergyValueChanged(ListSelectionEvent evt) {
/* 12485 */     if (this.lstChooseEnergy.getSelectedIndex() < 0) return;
/* 12486 */     abPlaceable p = (abPlaceable)this.Equipment[1][this.lstChooseEnergy.getSelectedIndex()];
/* 12487 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void lstChooseAmmunitionValueChanged(ListSelectionEvent evt) {
/* 12491 */     if (this.lstChooseAmmunition.getSelectedIndex() < 0) return;
/* 12492 */     if (!(this.Equipment[6][this.lstChooseAmmunition.getSelectedIndex()] instanceof Ammunition)) return;
/* 12493 */     abPlaceable p = (abPlaceable)this.Equipment[6][this.lstChooseAmmunition.getSelectedIndex()];
/* 12494 */     ShowInfoOn(p);
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
/*       */   private void lstChooseBallisticValueChanged(ListSelectionEvent evt)
/*       */   {
/* 12507 */     if (this.lstChooseBallistic.getSelectedIndex() < 0) return;
/* 12508 */     abPlaceable p = (abPlaceable)this.Equipment[0][this.lstChooseBallistic.getSelectedIndex()];
/* 12509 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void chkUseTCActionPerformed(ActionEvent evt) {
/* 12513 */     if (this.CurMech.UsingTC() == this.chkUseTC.isSelected()) return;
/* 12514 */     if (this.chkUseTC.isSelected()) {
/*       */       try {
/* 12516 */         this.CurMech.GetLoadout().CheckExclusions(this.CurMech.GetTC());
/* 12517 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 12518 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 12519 */           tech.setLocationRelativeTo(this);
/* 12520 */           tech.setVisible(true);
/* 12521 */           this.CurMech.UseTC(true, tech.IsClan());
/* 12522 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 12523 */           this.CurMech.UseTC(true, true);
/*       */         } else {
/* 12525 */           this.CurMech.UseTC(true, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 12528 */         Media.Messager(this, e.getMessage());
/* 12529 */         this.CurMech.UseTC(false, false);
/*       */       }
/*       */     } else {
/* 12532 */       this.CurMech.UseTC(false, false);
/*       */     }
/*       */     
/*       */ 
/* 12536 */     RefreshSummary();
/* 12537 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnCompactCritsActionPerformed(ActionEvent evt) {
/* 12541 */     this.CurMech.GetLoadout().Compact();
/*       */     
/*       */ 
/* 12544 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void lstChooseEquipmentValueChanged(ListSelectionEvent evt) {
/* 12548 */     if (this.lstChooseEquipment.getSelectedIndex() < 0) { return;
/*       */     }
/* 12550 */     abPlaceable p = (abPlaceable)this.Equipment[4][this.lstChooseEquipment.getSelectedIndex()];
/* 12551 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void mnuSummaryActionPerformed(ActionEvent evt) {
/* 12555 */     SolidifyMech();
/* 12556 */     dlgSummaryInfo Summary = new dlgSummaryInfo(this, true);
/* 12557 */     Summary.setLocationRelativeTo(this);
/* 12558 */     Summary.setVisible(true);
/*       */   }
/*       */   
/*       */ 
/*       */   private void btnExportMTFActionPerformed(ActionEvent evt)
/*       */   {
/* 12564 */     String dir = this.Prefs.get("MTFExportPath", "none");
/* 12565 */     if (dir.equals("none")) {
/* 12566 */       dir = this.Prefs.get("LastOpenDirectory", "");
/*       */     }
/* 12568 */     File savemech = GetSaveFile("mtf", dir, false, true);
/* 12569 */     if (savemech == null) {
/* 12570 */       return;
/*       */     }
/*       */     
/* 12573 */     String filename = "";
/* 12574 */     IO.MTFWriter mtfw = new IO.MTFWriter(this.CurMech);
/*       */     try {
/* 12576 */       filename = savemech.getCanonicalPath();
/* 12577 */       mtfw.WriteMTF(filename);
/*       */     } catch (IOException e) {
/* 12579 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 12580 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12584 */     Media.Messager(this, "Mech saved successfully to MTF:\n" + filename);
/* 12585 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/*       */   }
/*       */   
/*       */   private void lstChoosePhysicalValueChanged(ListSelectionEvent evt) {
/* 12589 */     if (this.lstChoosePhysical.getSelectedIndex() < 0) return;
/* 12590 */     if (!(this.Equipment[3][this.lstChoosePhysical.getSelectedIndex()] instanceof PhysicalWeapon)) return;
/* 12591 */     abPlaceable p = (abPlaceable)this.Equipment[3][this.lstChoosePhysical.getSelectedIndex()];
/* 12592 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void btnExportTXTActionPerformed(ActionEvent evt)
/*       */   {
/* 12597 */     String CurLoadout = "";
/* 12598 */     if (this.CurMech.IsOmnimech()) {
/* 12599 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/* 12602 */     String dir = this.Prefs.get("TXTExportPath", "none");
/* 12603 */     if (dir.equals("none")) {
/* 12604 */       dir = this.Prefs.get("LastOpenDirectory", "");
/*       */     }
/* 12606 */     File savemech = GetSaveFile("txt", dir, false, false);
/* 12607 */     if (savemech == null) {
/* 12608 */       return;
/*       */     }
/*       */     
/* 12611 */     String filename = "";
/* 12612 */     filehandlers.TXTWriter txtw = new filehandlers.TXTWriter(this.CurMech);
/*       */     try {
/* 12614 */       filename = savemech.getCanonicalPath();
/* 12615 */       txtw.WriteTXT(filename);
/*       */     } catch (IOException e) {
/* 12617 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 12618 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12622 */     Media.Messager(this, "Mech saved successfully to TXT:\n" + filename);
/*       */     
/*       */ 
/* 12625 */     if (this.CurMech.IsOmnimech()) {
/* 12626 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 12627 */       cmbOmniVariantActionPerformed(evt);
/*       */     }
/* 12629 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/*       */   }
/*       */   
/*       */   private void btnExportHTMLActionPerformed(ActionEvent evt)
/*       */   {
/* 12634 */     String CurLoadout = "";
/* 12635 */     if (this.CurMech.IsOmnimech()) {
/* 12636 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/* 12639 */     String dir = this.Prefs.get("HTMLExportPath", "none");
/* 12640 */     if (dir.equals("none")) {
/* 12641 */       dir = this.Prefs.get("LastOpenDirectory", "");
/*       */     }
/* 12643 */     File savemech = GetSaveFile("html", dir, false, false);
/* 12644 */     if (savemech == null) {
/* 12645 */       return;
/*       */     }
/*       */     
/* 12648 */     String filename = "";
/* 12649 */     ssw.filehandlers.HTMLWriter HTMw = new ssw.filehandlers.HTMLWriter(this.CurMech);
/*       */     try {
/* 12651 */       filename = savemech.getCanonicalPath();
/* 12652 */       HTMw.WriteHTML("Data/Templates/Mech_HTML.html", filename);
/*       */     } catch (IOException e) {
/* 12654 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 12655 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12659 */     Media.Messager(this, "Mech saved successfully to HTML:\n" + filename);
/*       */     
/*       */ 
/* 12662 */     if (this.CurMech.IsOmnimech()) {
/* 12663 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 12664 */       cmbOmniVariantActionPerformed(evt);
/*       */     }
/* 12666 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/*       */   }
/*       */   
/*       */   private void mnuAboutSSWActionPerformed(ActionEvent evt) {
/* 12670 */     dlgAboutBox about = new dlgAboutBox(this, true);
/* 12671 */     about.setLocationRelativeTo(this);
/* 12672 */     about.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuExportTXTActionPerformed(ActionEvent evt) {
/* 12676 */     this.SetSource = false;
/* 12677 */     btnExportTXTActionPerformed(evt);
/* 12678 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */   private void mnuExportHTMLActionPerformed(ActionEvent evt) {
/* 12682 */     this.SetSource = false;
/* 12683 */     btnExportHTMLActionPerformed(evt);
/* 12684 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */   private void mnuExportMTFActionPerformed(ActionEvent evt) {
/* 12688 */     this.SetSource = false;
/* 12689 */     btnExportMTFActionPerformed(evt);
/* 12690 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */   private void chkYearRestrictActionPerformed(ActionEvent evt)
/*       */   {
/* 12695 */     int year = 0;
/* 12696 */     if (this.CurMech.IsYearRestricted() == this.chkYearRestrict.isSelected()) { return;
/*       */     }
/*       */     
/* 12699 */     if (!this.chkYearRestrict.isSelected()) {
/* 12700 */       this.cmbMechEra.setEnabled(true);
/* 12701 */       this.cmbTechBase.setEnabled(true);
/* 12702 */       this.txtProdYear.setEnabled(true);
/* 12703 */       this.CurMech.SetYearRestricted(false);
/* 12704 */       switch (this.cmbMechEra.getSelectedIndex()) {
/*       */       case 0: 
/* 12706 */         this.CurMech.SetYear(2750, false);
/* 12707 */         break;
/*       */       case 1: 
/* 12709 */         this.CurMech.SetYear(3025, false);
/* 12710 */         break;
/*       */       case 2: 
/* 12712 */         this.CurMech.SetYear(3070, false);
/* 12713 */         break;
/*       */       case 3: 
/* 12715 */         this.CurMech.SetYear(3132, false);
/* 12716 */         break;
/*       */       case 4: 
/* 12718 */         this.CurMech.SetYear(0, false);
/*       */       }
/*       */     }
/*       */     else
/*       */     {
/*       */       try {
/* 12724 */         year = Integer.parseInt(this.txtProdYear.getText());
/*       */       } catch (NumberFormatException n) {
/* 12726 */         Media.Messager(this, "The production year is not a number.");
/* 12727 */         this.txtProdYear.setText("");
/* 12728 */         this.chkYearRestrict.setSelected(false);
/* 12729 */         return;
/*       */       }
/*       */       
/*       */ 
/* 12733 */       switch (this.cmbMechEra.getSelectedIndex())
/*       */       {
/*       */       case 0: 
/* 12736 */         if ((year < 2443) || (year > 2800)) {
/* 12737 */           Media.Messager(this, "The year does not fall within this era.");
/* 12738 */           this.txtProdYear.setText("");
/* 12739 */           this.chkYearRestrict.setSelected(false); return;
/*       */         }
/*       */         
/*       */ 
/*       */         break;
/*       */       case 1: 
/* 12745 */         if ((year < 2801) || (year > 3050)) {
/* 12746 */           Media.Messager(this, "The year does not fall within this era.");
/* 12747 */           this.txtProdYear.setText("");
/* 12748 */           this.chkYearRestrict.setSelected(false); return;
/*       */         }
/*       */         
/*       */ 
/*       */         break;
/*       */       case 2: 
/* 12754 */         if ((year < 3051) || (year > 3085)) {
/* 12755 */           Media.Messager(this, "The year does not fall within this era.");
/* 12756 */           this.txtProdYear.setText("");
/* 12757 */           this.chkYearRestrict.setSelected(false); return;
/*       */         }
/*       */         
/*       */ 
/*       */         break;
/*       */       case 3: 
/* 12763 */         if (year < 3085) {
/* 12764 */           Media.Messager(this, "The year does not fall within this era.");
/* 12765 */           this.txtProdYear.setText("");
/* 12766 */           this.chkYearRestrict.setSelected(false); return;
/*       */         }
/*       */         
/*       */ 
/*       */         break;
/*       */       case 4: 
/* 12772 */         this.chkYearRestrict.setSelected(false);
/* 12773 */         this.chkYearRestrict.setEnabled(false);
/*       */       }
/*       */       
/*       */       
/* 12777 */       this.cmbMechEra.setEnabled(false);
/* 12778 */       this.cmbTechBase.setEnabled(false);
/* 12779 */       this.txtProdYear.setEnabled(false);
/* 12780 */       this.CurMech.SetYear(year, true);
/* 12781 */       this.CurMech.SetYearRestricted(true);
/*       */     }
/*       */     
/*       */ 
/* 12785 */     SaveSelections();
/*       */     
/*       */ 
/* 12788 */     BuildChassisSelector();
/* 12789 */     BuildEngineSelector();
/* 12790 */     BuildGyroSelector();
/* 12791 */     BuildCockpitSelector();
/* 12792 */     BuildEnhancementSelector();
/* 12793 */     BuildHeatsinkSelector();
/* 12794 */     BuildJumpJetSelector();
/* 12795 */     BuildArmorSelector();
/* 12796 */     RefreshEquipment();
/* 12797 */     CheckOmnimech();
/*       */     
/*       */ 
/* 12800 */     LoadSelections();
/*       */     
/*       */ 
/* 12803 */     RecalcEngine();
/* 12804 */     RecalcGyro();
/* 12805 */     RecalcIntStruc();
/* 12806 */     RecalcCockpit();
/* 12807 */     this.CurMech.GetActuators().PlaceActuators();
/* 12808 */     RecalcHeatSinks();
/* 12809 */     RecalcJumpJets();
/* 12810 */     RecalcEnhancements();
/* 12811 */     RecalcArmor();
/* 12812 */     RecalcEquipment();
/*       */     
/* 12814 */     this.CurMech.GetLoadout().FlushIllegal();
/*       */     
/*       */ 
/* 12817 */     RefreshSummary();
/* 12818 */     RefreshInfoPane();
/* 12819 */     SetWeaponChoosers();
/* 12820 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   private void cmbJumpJetTypeActionPerformed(ActionEvent evt) {
/* 12824 */     RecalcJumpJets();
/* 12825 */     RefreshSummary();
/* 12826 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void mnuNewMechActionPerformed(ActionEvent evt) {
/* 12830 */     if (this.CurMech.HasChanged()) {
/* 12831 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 12833 */       if (choice == 1) return;
/*       */     }
/* 12835 */     GetNewMech();
/* 12836 */     this.Prefs.put("Currentfile", "");
/*       */   }
/*       */   
/*       */   private void cmbRulesLevelActionPerformed(ActionEvent evt) {
/* 12840 */     if (this.Load) return;
/* 12841 */     int NewLevel = this.cmbRulesLevel.getSelectedIndex();
/* 12842 */     int OldLevel = this.CurMech.GetLoadout().GetRulesLevel();
/* 12843 */     int OldType = this.cmbMechType.getSelectedIndex();
/* 12844 */     int OldTech = this.CurMech.GetTechbase();
/*       */     
/* 12846 */     if (OldLevel == NewLevel)
/*       */     {
/* 12848 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12852 */     if (this.CurMech.IsOmnimech())
/*       */     {
/* 12854 */       if (this.CurMech.GetLoadout().SetRulesLevel(NewLevel))
/*       */       {
/* 12856 */         if (OldLevel > NewLevel)
/*       */         {
/* 12858 */           this.CurMech.GetLoadout().FlushIllegal();
/*       */         }
/* 12860 */         BuildTechBaseSelector();
/* 12861 */         this.cmbTechBase.setSelectedIndex(this.CurMech.GetLoadout().GetTechBase());
/* 12862 */         BuildJumpJetSelector();
/* 12863 */         RefreshEquipment();
/* 12864 */         RecalcEquipment();
/*       */       }
/*       */       else {
/* 12867 */         Media.Messager(this, "You cannot set an OmniMech's loadout to a Rules Level\nlower than it's chassis' Rules Level.");
/* 12868 */         this.cmbRulesLevel.setSelectedIndex(this.CurMech.GetLoadout().GetRulesLevel());
/*       */       }
/*       */     }
/*       */     else {
/* 12872 */       this.CurMech.SetRulesLevel(NewLevel);
/* 12873 */       BuildMechTypeSelector();
/* 12874 */       CheckTonnage(true);
/*       */       
/*       */ 
/* 12877 */       SaveSelections();
/* 12878 */       BuildTechBaseSelector();
/* 12879 */       if (OldTech >= this.cmbTechBase.getItemCount())
/*       */       {
/* 12881 */         switch (OldTech)
/*       */         {
/*       */         case 0: 
/* 12884 */           System.err.println("Fatal Error when reseting techbase, Inner Sphere not available.");
/* 12885 */           break;
/*       */         
/*       */         default: 
/* 12888 */           this.cmbTechBase.setSelectedIndex(0);
/* 12889 */           cmbTechBaseActionPerformed(null);
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/* 12897 */       this.CurMech.GetLoadout().FlushIllegal();
/*       */       
/*       */ 
/* 12900 */       BuildChassisSelector();
/* 12901 */       BuildEngineSelector();
/* 12902 */       BuildGyroSelector();
/* 12903 */       BuildCockpitSelector();
/* 12904 */       BuildEnhancementSelector();
/* 12905 */       BuildHeatsinkSelector();
/* 12906 */       BuildJumpJetSelector();
/* 12907 */       BuildArmorSelector();
/* 12908 */       FixWalkMPSpinner();
/* 12909 */       FixJJSpinnerModel();
/* 12910 */       RefreshEquipment();
/*       */       
/*       */ 
/* 12913 */       LoadSelections();
/*       */       
/* 12915 */       RecalcEngine();
/* 12916 */       RecalcGyro();
/* 12917 */       RecalcIntStruc();
/* 12918 */       RecalcCockpit();
/* 12919 */       this.CurMech.GetActuators().PlaceActuators();
/* 12920 */       RecalcHeatSinks();
/* 12921 */       RecalcJumpJets();
/* 12922 */       RecalcEnhancements();
/* 12923 */       RecalcArmor();
/* 12924 */       RecalcEquipment();
/*       */     }
/*       */     
/*       */ 
/* 12928 */     RefreshSummary();
/* 12929 */     RefreshInfoPane();
/* 12930 */     SetWeaponChoosers();
/* 12931 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   private void btnLockChassisActionPerformed(ActionEvent evt)
/*       */   {
/* 12936 */     SaveOmniFluffInfo();
/* 12937 */     String VariantName = "";
/*       */     
/*       */ 
/* 12940 */     if (!this.CurMech.GetLoadout().GetQueue().isEmpty()) {
/* 12941 */       Media.Messager(this, "You must place all items first.");
/* 12942 */       this.tbpMainTabPane.setSelectedComponent(this.pnlCriticals);
/* 12943 */       return;
/*       */     }
/*       */     
/* 12946 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to lock the chassis?\nAll items in the base loadout will be locked in location\nand most chassis specifications will be locked.", "Lock Chassis?", 0);
/*       */     
/*       */ 
/*       */ 
/* 12950 */     if (choice == 1) {
/* 12951 */       return;
/*       */     }
/*       */     
/* 12954 */     dlgOmniBase input = new dlgOmniBase(this, true);
/* 12955 */     input.setTitle("Name the first variant");
/* 12956 */     input.setLocationRelativeTo(this);
/* 12957 */     input.setVisible(true);
/* 12958 */     if (input.WasCanceled()) {
/* 12959 */       input.dispose();
/* 12960 */       return;
/*       */     }
/* 12962 */     VariantName = input.GetInput();
/* 12963 */     input.dispose();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/* 12968 */     if ("Base Loadout".equals(VariantName)) {
/* 12969 */       Media.Messager(this, "\"" + VariantName + "\" is reserved for the base loadout and cannot be used\nfor a new loadout.  Please choose another name.");
/* 12970 */       return;
/*       */     }
/*       */     
/*       */ 
/* 12974 */     this.CurMech.SetOmnimech(VariantName);
/* 12975 */     this.chkOmnimech.setEnabled(false);
/* 12976 */     FixTransferHandlers();
/* 12977 */     SetLoadoutArrays();
/* 12978 */     FixJJSpinnerModel();
/* 12979 */     FixHeatSinkSpinnerModel();
/* 12980 */     LockGUIForOmni();
/* 12981 */     RefreshOmniVariants();
/* 12982 */     RefreshOmniChoices();
/* 12983 */     SolidifyJJManufacturer();
/* 12984 */     RefreshSummary();
/* 12985 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkOmnimechActionPerformed(ActionEvent evt) {
/* 12989 */     if (this.chkOmnimech.isSelected()) {
/* 12990 */       this.btnLockChassis.setEnabled(true);
/*       */     } else {
/* 12992 */       this.btnLockChassis.setEnabled(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private void btnAddVariantActionPerformed(ActionEvent evt) {
/* 12997 */     SaveOmniFluffInfo();
/* 12998 */     String VariantName = "";
/*       */     
/*       */ 
/* 13001 */     dlgOmniBase input = new dlgOmniBase(this, true);
/* 13002 */     input.setTitle("Name this variant");
/* 13003 */     input.setLocationRelativeTo(this);
/* 13004 */     input.setVisible(true);
/* 13005 */     if (input.WasCanceled()) {
/* 13006 */       input.dispose();
/* 13007 */       return;
/*       */     }
/* 13009 */     VariantName = input.GetInput();
/* 13010 */     input.dispose();
/*       */     
/*       */ 
/*       */     try
/*       */     {
/* 13015 */       this.CurMech.AddLoadout(VariantName);
/*       */     }
/*       */     catch (Exception e) {
/* 13018 */       Media.Messager(this, e.getMessage());
/* 13019 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13023 */     LoadOmniFluffInfo();
/* 13024 */     FixTransferHandlers();
/* 13025 */     SetLoadoutArrays();
/* 13026 */     SetWeaponChoosers();
/* 13027 */     BuildJumpJetSelector();
/* 13028 */     FixJJSpinnerModel();
/* 13029 */     FixHeatSinkSpinnerModel();
/* 13030 */     RefreshOmniVariants();
/* 13031 */     RefreshOmniChoices();
/* 13032 */     SolidifyJJManufacturer();
/* 13033 */     RefreshSummary();
/* 13034 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void mnuOpenActionPerformed(ActionEvent evt)
/*       */   {
/* 13039 */     if (this.CurMech.HasChanged()) {
/* 13040 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 13042 */       if (choice == 1) return;
/*       */     }
/* 13044 */     this.dOpen.Requestor = 0;
/* 13045 */     this.dOpen.setLocationRelativeTo(null);
/*       */     
/* 13047 */     this.dOpen.setSize(1024, 600);
/* 13048 */     this.dOpen.setVisible(true);
/*       */   }
/*       */   
/*       */   private void btnDeleteVariantActionPerformed(ActionEvent evt)
/*       */   {
/* 13053 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this variant?", "Delete Variant?", 0);
/*       */     
/* 13055 */     if (choice == 1) {
/* 13056 */       return;
/*       */     }
/* 13058 */     if (this.CurMech.GetLoadout().GetName().equals("Base Loadout")) {
/* 13059 */       Media.Messager(this, "You cannot remove the base chassis.");
/* 13060 */       return;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13065 */     this.CurMech.RemoveLoadout(this.CurMech.GetLoadout().GetName());
/*       */     
/*       */ 
/* 13068 */     LoadOmniFluffInfo();
/* 13069 */     RefreshOmniVariants();
/* 13070 */     FixTransferHandlers();
/* 13071 */     SetLoadoutArrays();
/* 13072 */     SetWeaponChoosers();
/* 13073 */     BuildJumpJetSelector();
/* 13074 */     FixJJSpinnerModel();
/* 13075 */     FixHeatSinkSpinnerModel();
/* 13076 */     RefreshOmniChoices();
/* 13077 */     SolidifyJJManufacturer();
/* 13078 */     RefreshSummary();
/* 13079 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbOmniVariantActionPerformed(ActionEvent evt) {
/* 13083 */     SaveOmniFluffInfo();
/* 13084 */     String variant = (String)this.cmbOmniVariant.getSelectedItem();
/* 13085 */     boolean changed = this.CurMech.HasChanged();
/*       */     
/* 13087 */     this.CurMech.SetCurLoadout(variant);
/*       */     
/*       */ 
/* 13090 */     LoadOmniFluffInfo();
/* 13091 */     FixTransferHandlers();
/* 13092 */     SetLoadoutArrays();
/* 13093 */     SetWeaponChoosers();
/* 13094 */     BuildJumpJetSelector();
/* 13095 */     this.cmbJumpJetType.setSelectedItem(this.CurMech.GetJumpJets().LookupName());
/* 13096 */     FixJJSpinnerModel();
/* 13097 */     FixHeatSinkSpinnerModel();
/* 13098 */     RefreshOmniVariants();
/* 13099 */     RefreshEquipment();
/* 13100 */     RefreshOmniChoices();
/* 13101 */     RefreshSummary();
/* 13102 */     RefreshInfoPane();
/*       */     
/*       */ 
/*       */ 
/* 13106 */     this.CurMech.SetChanged(changed);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private void mnuPostS7ActionPerformed(ActionEvent evt)
/*       */   {
/* 13116 */     QuickSave();
/*       */     
/* 13118 */     String CurLoadout = "";
/* 13119 */     if (this.CurMech.IsOmnimech()) {
/* 13120 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/*       */ 
/* 13124 */     SolidifyMech();
/*       */     
/* 13126 */     if (!VerifyMech(evt)) {
/* 13127 */       return;
/*       */     }
/*       */     
/* 13130 */     dlgPostToSolaris7 PostS7 = new dlgPostToSolaris7(this, true);
/* 13131 */     PostS7.setLocationRelativeTo(this);
/* 13132 */     PostS7.setVisible(true);
/*       */     
/*       */ 
/* 13135 */     this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 13136 */     cmbOmniVariantActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void mnuClearUserDataActionPerformed(ActionEvent evt) {
/* 13140 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "This will remove all Solaris 7 user data.\nAre you sure you want to continue?", "Clear User Data?", 0);
/*       */     
/* 13142 */     if (choice == 1) {
/* 13143 */       return;
/*       */     }
/* 13145 */     this.Prefs.put("S7Callsign", "");
/* 13146 */     this.Prefs.put("S7Password", "");
/* 13147 */     this.Prefs.put("S7UserID", "");
/*       */   }
/*       */   
/*       */ 
/*       */   private void mnuSaveActionPerformed(ActionEvent evt)
/*       */   {
/* 13153 */     setCursor(this.Hourglass);
/*       */     
/* 13155 */     File savemech = GetSaveFile("ssw", this.Prefs.get("LastOpenDirectory", ""), true, false);
/* 13156 */     if (savemech == null) {
/* 13157 */       setCursor(this.NormalCursor);
/* 13158 */       return;
/*       */     }
/*       */     
/*       */     try
/*       */     {
/* 13163 */       this.Prefs.put("LastOpenDirectory", savemech.getCanonicalPath().replace(savemech.getName(), ""));
/* 13164 */       this.Prefs.put("LastOpenFile", savemech.getName());
/* 13165 */       this.Prefs.put("Currentfile", savemech.getCanonicalPath());
/*       */     } catch (IOException e) {
/* 13167 */       Media.Messager(this, "There was a problem with the file:\n" + e.getMessage());
/* 13168 */       setCursor(this.NormalCursor);
/* 13169 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13173 */     String CurLoadout = "";
/* 13174 */     if (this.CurMech.IsOmnimech()) {
/* 13175 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/* 13176 */       SaveOmniFluffInfo();
/*       */     }
/*       */     
/*       */ 
/* 13180 */     filehandlers.MechWriter XMLw = new filehandlers.MechWriter(this.CurMech);
/*       */     try {
/* 13182 */       String file = savemech.getCanonicalPath();
/* 13183 */       String ext = Utils.getExtension(savemech);
/* 13184 */       if ((ext == null) || (ext.equals(""))) {
/* 13185 */         file = file + ".ssw";
/*       */       }
/* 13187 */       else if (!ext.equals("ssw")) {
/* 13188 */         file.replace("." + ext, ".ssw");
/*       */       }
/*       */       
/* 13191 */       XMLw.WriteXML(file);
/*       */       
/*       */ 
/* 13194 */       if ((evt != null) && (evt.getActionCommand().equals("Save Mech"))) {
/* 13195 */         Media.Messager(this, "Mech saved successfully:\n" + file);
/*       */       }
/*       */     } catch (IOException e) {
/* 13198 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 13199 */       setCursor(this.NormalCursor);
/* 13200 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13204 */     if (this.CurMech.IsOmnimech()) {
/* 13205 */       this.SetSource = false;
/* 13206 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 13207 */       cmbOmniVariantActionPerformed(evt);
/* 13208 */       this.SetSource = true;
/*       */     }
/*       */     
/* 13211 */     setCursor(this.NormalCursor);
/* 13212 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/* 13213 */     this.CurMech.SetChanged(false);
/*       */   }
/*       */   
/*       */   private void mnuLoadActionPerformed(ActionEvent evt) {
/* 13217 */     if (this.CurMech.HasChanged()) {
/* 13218 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 13220 */       if (choice == 1) { return;
/*       */       }
/*       */     }
/* 13223 */     Mech m = LoadMech();
/* 13224 */     if (m == null) {
/* 13225 */       return;
/*       */     }
/* 13227 */     this.CurMech = m;
/* 13228 */     LoadMechIntoGUI();
/* 13229 */     this.CurMech.SetChanged(false);
/*       */   }
/*       */   
/*       */   private void mnuSaveAsActionPerformed(ActionEvent evt) {
/* 13233 */     setCursor(this.Hourglass);
/* 13234 */     File savemech = GetSaveFile("ssw", this.Prefs.get("LastOpenDirectory", ""), false, false);
/* 13235 */     if (savemech == null) {
/* 13236 */       setCursor(this.NormalCursor);
/* 13237 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13241 */     String CurLoadout = "";
/* 13242 */     if (this.CurMech.IsOmnimech()) {
/* 13243 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 13248 */     this.CurMech.SetSolaris7ID("0");
/*       */     
/*       */ 
/* 13251 */     filehandlers.MechWriter XMLw = new filehandlers.MechWriter(this.CurMech);
/*       */     try {
/* 13253 */       String file = savemech.getCanonicalPath();
/* 13254 */       String ext = Utils.getExtension(savemech);
/* 13255 */       if ((ext == null) || (ext.equals(""))) {
/* 13256 */         file = file + ".ssw";
/*       */       }
/* 13258 */       else if (!ext.equals("ssw")) {
/* 13259 */         file.replace("." + ext, ".ssw");
/*       */       }
/*       */       
/* 13262 */       XMLw.WriteXML(file);
/*       */       
/* 13264 */       Media.Messager(this, "Mech saved successfully:\n" + file);
/*       */     } catch (IOException e) {
/* 13266 */       Media.Messager(this, "There was a problem writing the file:\n" + e.getMessage());
/* 13267 */       setCursor(this.NormalCursor);
/* 13268 */       return;
/*       */     }
/*       */     
/*       */     try
/*       */     {
/* 13273 */       this.Prefs.put("LastOpenDirectory", savemech.getCanonicalPath().replace(savemech.getName(), ""));
/* 13274 */       this.Prefs.put("LastOpenFile", savemech.getName());
/*       */     } catch (IOException e) {
/* 13276 */       Media.Messager(this, "There was a problem with the file:\n" + e.getMessage());
/* 13277 */       setCursor(this.NormalCursor);
/* 13278 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13282 */     if (this.CurMech.IsOmnimech()) {
/* 13283 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 13284 */       cmbOmniVariantActionPerformed(evt);
/*       */     }
/* 13286 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/* 13287 */     this.CurMech.SetChanged(false);
/* 13288 */     setCursor(this.NormalCursor);
/*       */   }
/*       */   
/*       */   private void lstSelectedEquipmentValueChanged(ListSelectionEvent evt) {
/* 13292 */     if (this.lstSelectedEquipment.getSelectedIndex() < 0) return;
/*       */     try
/*       */     {
/* 13295 */       p = (abPlaceable)this.CurMech.GetLoadout().GetNonCore().get(this.lstSelectedEquipment.getSelectedIndex());
/*       */     } catch (Exception e) { abPlaceable p;
/*       */       return; }
/*       */     abPlaceable p;
/* 13299 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void btnSelectiveAllocateActionPerformed(ActionEvent evt) {
/* 13303 */     SelectiveAllocate();
/*       */   }
/*       */   
/*       */   private void btnAutoAllocateActionPerformed(ActionEvent evt) {
/* 13307 */     AutoAllocate();
/*       */   }
/*       */   
/*       */   private void lstCritsToPlaceValueChanged(ListSelectionEvent evt) {
/* 13311 */     int Index = this.lstCritsToPlace.getSelectedIndex();
/* 13312 */     if (Index < 0) {
/* 13313 */       this.btnAutoAllocate.setEnabled(false);
/* 13314 */       this.btnSelectiveAllocate.setEnabled(false);
/* 13315 */       this.btnRemoveItemCrits.setEnabled(false);
/* 13316 */       return;
/*       */     }
/* 13318 */     this.CurItem = this.CurMech.GetLoadout().GetFromQueueByIndex(Index);
/* 13319 */     if (this.CurItem.Contiguous()) {
/* 13320 */       this.btnAutoAllocate.setEnabled(false);
/* 13321 */       this.btnSelectiveAllocate.setEnabled(false);
/* 13322 */       if (!this.CurItem.CoreComponent()) {
/* 13323 */         this.btnRemoveItemCrits.setEnabled(true);
/*       */       } else {
/* 13325 */         this.btnRemoveItemCrits.setEnabled(false);
/*       */       }
/*       */     } else {
/* 13328 */       this.btnAutoAllocate.setEnabled(true);
/* 13329 */       this.btnSelectiveAllocate.setEnabled(true);
/* 13330 */       this.btnRemoveItemCrits.setEnabled(false);
/*       */     }
/*       */   }
/*       */   
/*       */   private void mnuCostBVBreakdownActionPerformed(ActionEvent evt) {
/* 13335 */     SolidifyMech();
/* 13336 */     dlgCostBVBreakdown costbv = new dlgCostBVBreakdown(this, true, this.CurMech);
/* 13337 */     costbv.setLocationRelativeTo(this);
/* 13338 */     costbv.setVisible(true);
/*       */   }
/*       */   
/*       */   private void lstChooseArtilleryValueChanged(ListSelectionEvent evt) {
/* 13342 */     if (this.lstChooseArtillery.getSelectedIndex() < 0) return;
/* 13343 */     if ((!(this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()] instanceof RangedWeapon)) && (!(this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()] instanceof VehicularGrenadeLauncher))) return;
/* 13344 */     abPlaceable p = (abPlaceable)this.Equipment[5][this.lstChooseArtillery.getSelectedIndex()];
/* 13345 */     ShowInfoOn(p);
/*       */   }
/*       */   
/*       */   private void mnuPrintCurrentMechActionPerformed(ActionEvent evt)
/*       */   {
/* 13350 */     SolidifyMech();
/*       */     
/* 13352 */     if (VerifyMech(new ActionEvent(this, 1234567890, null))) {
/* 13353 */       PrintMech(this.CurMech);
/*       */     }
/*       */   }
/*       */   
/*       */   private void mnuPrintSavedMechActionPerformed(ActionEvent evt) {
/* 13358 */     Mech m = LoadMech();
/* 13359 */     if (m != null) {
/* 13360 */       PrintMech(m);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private void btnEfficientArmorActionPerformed(ActionEvent evt)
/*       */   {
/* 13368 */     int MaxArmor = this.CurMech.GetArmor().GetMaxArmor();
/* 13369 */     int MaxLessArmor = (int)((this.CurMech.GetArmor().GetMaxTonnage() - 0.5D) * 16.0D * this.CurMech.GetArmor().GetAVMult());
/*       */     
/* 13371 */     if (MaxArmor - MaxLessArmor > 5.0D * this.CurMech.GetArmor().GetAVMult())
/*       */     {
/* 13373 */       this.ArmorTons.SetArmorTonnage(this.CurMech.GetArmor().GetMaxTonnage());
/*       */     }
/*       */     else {
/* 13376 */       this.ArmorTons.SetArmorTonnage(this.CurMech.GetArmor().GetMaxTonnage() - 0.5D);
/*       */     }
/*       */     
/*       */     try
/*       */     {
/* 13381 */       this.CurMech.Visit(this.ArmorTons);
/*       */     }
/*       */     catch (Exception e) {
/* 13384 */       System.err.println(e.getMessage());
/* 13385 */       e.printStackTrace();
/*       */     }
/*       */     
/*       */ 
/* 13389 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 13392 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 13393 */     n.setValue(Integer.valueOf(this.CurMech.GetArmor().GetLocationArmor(0)));
/*       */     
/*       */ 
/* 13396 */     RefreshSummary();
/* 13397 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkNullSigActionPerformed(ActionEvent evt)
/*       */   {
/* 13402 */     if (this.chkNullSig.isSelected() == this.CurMech.HasNullSig()) return;
/*       */     try {
/* 13404 */       if (this.chkNullSig.isSelected()) {
/* 13405 */         this.CurMech.SetNullSig(true);
/*       */       } else {
/* 13407 */         this.CurMech.SetNullSig(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13410 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13412 */       this.chkNullSig.setSelected(this.CurMech.HasNullSig());
/* 13413 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13417 */     RefreshSummary();
/* 13418 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkCLPSActionPerformed(ActionEvent evt)
/*       */   {
/* 13423 */     if (this.chkCLPS.isSelected() == this.CurMech.HasChameleon()) return;
/*       */     try {
/* 13425 */       if (this.chkCLPS.isSelected()) {
/* 13426 */         this.CurMech.SetChameleon(true);
/*       */       } else {
/* 13428 */         this.CurMech.SetChameleon(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13431 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13433 */       this.chkCLPS.setSelected(this.CurMech.HasChameleon());
/* 13434 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13438 */     RefreshSummary();
/* 13439 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkBSPFDActionPerformed(ActionEvent evt)
/*       */   {
/* 13444 */     if (this.chkBSPFD.isSelected() == this.CurMech.HasBlueShield()) return;
/*       */     try {
/* 13446 */       if (this.chkBSPFD.isSelected()) {
/* 13447 */         this.CurMech.SetBlueShield(true);
/*       */       } else {
/* 13449 */         this.CurMech.SetBlueShield(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13452 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13454 */       this.chkBSPFD.setSelected(this.CurMech.HasBlueShield());
/* 13455 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13459 */     RefreshSummary();
/* 13460 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkVoidSigActionPerformed(ActionEvent evt)
/*       */   {
/* 13465 */     if (this.chkVoidSig.isSelected() == this.CurMech.HasVoidSig()) return;
/*       */     try {
/* 13467 */       if (this.chkVoidSig.isSelected()) {
/* 13468 */         this.CurMech.SetVoidSig(true);
/* 13469 */         if (!AddECM()) {
/* 13470 */           this.CurMech.SetVoidSig(false);
/* 13471 */           throw new Exception("No ECM Suite was available to support the Void Signature System!\nUninstalling system.");
/*       */         }
/*       */       } else {
/* 13474 */         this.CurMech.SetVoidSig(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 13477 */       Media.Messager(this, e.getMessage());
/*       */       
/* 13479 */       this.chkVoidSig.setSelected(this.CurMech.HasVoidSig());
/* 13480 */       return;
/*       */     }
/*       */     
/*       */ 
/* 13484 */     RefreshSummary();
/* 13485 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnRemainingArmorActionPerformed(ActionEvent evt)
/*       */   {
/* 13490 */     double freetons = this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons() + this.CurMech.GetArmor().GetTonnage();
/*       */     
/* 13492 */     if (freetons > this.CurMech.GetArmor().GetMaxTonnage()) {
/* 13493 */       freetons = this.CurMech.GetArmor().GetMaxTonnage();
/*       */     }
/*       */     
/* 13496 */     this.ArmorTons.SetArmorTonnage(freetons);
/*       */     try {
/* 13498 */       this.CurMech.Visit(this.ArmorTons);
/*       */     }
/*       */     catch (Exception e) {
/* 13501 */       System.err.println(e.getMessage());
/* 13502 */       e.printStackTrace();
/*       */     }
/*       */     
/*       */ 
/* 13506 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 13509 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnHDArmor.getModel();
/* 13510 */     n.setValue(Integer.valueOf(this.CurMech.GetArmor().GetLocationArmor(0)));
/*       */     
/*       */ 
/* 13513 */     RefreshSummary();
/* 13514 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbSCLocActionPerformed(ActionEvent evt) {
/* 13518 */     int curLoc = this.CurMech.GetLoadout().Find(this.CurMech.GetLoadout().GetSupercharger());
/* 13519 */     int DesiredLoc = filehandlers.FileCommon.DecodeLocation((String)this.cmbSCLoc.getSelectedItem());
/* 13520 */     if (curLoc == DesiredLoc) return;
/* 13521 */     if (this.CurMech.GetLoadout().HasSupercharger()) {
/*       */       try {
/* 13523 */         this.CurMech.GetLoadout().SetSupercharger(true, DesiredLoc, -1);
/*       */       } catch (Exception e) {
/* 13525 */         Media.Messager(this, e.getMessage());
/* 13526 */         this.chkSupercharger.setSelected(false);
/*       */         
/* 13528 */         RefreshSummary();
/* 13529 */         RefreshInfoPane();
/* 13530 */         return;
/*       */       }
/*       */     }
/*       */     
/* 13534 */     RefreshSummary();
/* 13535 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkSuperchargerActionPerformed(ActionEvent evt) {
/* 13539 */     if (this.CurMech.GetLoadout().HasSupercharger() == this.chkSupercharger.isSelected()) {
/* 13540 */       return;
/*       */     }
/*       */     try {
/* 13543 */       this.CurMech.GetLoadout().SetSupercharger(this.chkSupercharger.isSelected(), filehandlers.FileCommon.DecodeLocation((String)this.cmbSCLoc.getSelectedItem()), -1);
/*       */     } catch (Exception e) {
/* 13545 */       Media.Messager(this, e.getMessage());
/*       */       try {
/* 13547 */         this.CurMech.GetLoadout().SetSupercharger(false, 0, -1);
/*       */       }
/*       */       catch (Exception x) {
/* 13550 */         Media.Messager(this, x.getMessage());
/*       */         
/* 13552 */         RefreshSummary();
/* 13553 */         RefreshInfoPane();
/*       */       }
/* 13555 */       this.chkSupercharger.setSelected(false);
/*       */       
/* 13557 */       RefreshSummary();
/* 13558 */       RefreshInfoPane();
/* 13559 */       return;
/*       */     }
/*       */     
/* 13562 */     RefreshSummary();
/* 13563 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   public Mech LoadMech() {
/* 13567 */     Mech m = null;
/*       */     
/* 13569 */     File tempFile = new File(this.Prefs.get("LastOpenDirectory", ""));
/* 13570 */     JFileChooser fc = new JFileChooser();
/* 13571 */     fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
/*       */       public boolean accept(File f) {
/* 13573 */         if (f.isDirectory()) {
/* 13574 */           return true;
/*       */         }
/*       */         
/* 13577 */         String extension = Utils.getExtension(f);
/* 13578 */         if (extension != null) {
/* 13579 */           if (extension.equals("ssw")) {
/* 13580 */             return true;
/*       */           }
/* 13582 */           return false;
/*       */         }
/*       */         
/* 13585 */         return false;
/*       */       }
/*       */       
/*       */       public String getDescription()
/*       */       {
/* 13590 */         return "*.ssw";
/*       */       }
/* 13592 */     });
/* 13593 */     fc.setAcceptAllFileFilterUsed(false);
/* 13594 */     fc.setCurrentDirectory(tempFile);
/* 13595 */     int returnVal = fc.showDialog(this, "Load Mech");
/* 13596 */     if (returnVal != 0) return m;
/* 13597 */     File loadmech = fc.getSelectedFile();
/* 13598 */     String filename = "";
/*       */     try {
/* 13600 */       filename = loadmech.getCanonicalPath();
/* 13601 */       this.Prefs.put("LastOpenDirectory", loadmech.getCanonicalPath().replace(loadmech.getName(), ""));
/* 13602 */       this.Prefs.put("LastOpenFile", loadmech.getName());
/* 13603 */       this.Prefs.put("Currentfile", loadmech.getCanonicalPath());
/*       */     } catch (Exception e) {
/* 13605 */       Media.Messager(this, "There was a problem opening the file:\n" + e.getMessage());
/* 13606 */       return m;
/*       */     }
/*       */     try
/*       */     {
/* 13610 */       filehandlers.MechReader XMLr = new filehandlers.MechReader();
/* 13611 */       m = XMLr.ReadMech(filename, this.data);
/* 13612 */       if (XMLr.GetMessages().length() > 0) {
/* 13613 */         dlgTextExport Message = new dlgTextExport(this, true, XMLr.GetMessages());
/* 13614 */         Message.setLocationRelativeTo(this);
/* 13615 */         Message.setVisible(true);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 13619 */       if (e.getMessage() == null) {
/* 13620 */         Media.Messager(this, "An unknown error has occured.  The log file has been updated.");
/* 13621 */         e.printStackTrace();
/*       */       } else {
/* 13623 */         Media.Messager(this, e.getMessage());
/* 13624 */         e.printStackTrace();
/*       */       }
/* 13626 */       return m;
/*       */     }
/*       */     
/* 13629 */     return m;
/*       */   }
/*       */   
/*       */   private void LoadMechFromFile(String filename)
/*       */   {
/* 13634 */     Mech m = null;
/* 13635 */     if (!filename.isEmpty()) {
/*       */       try {
/* 13637 */         filehandlers.MechReader XMLr = new filehandlers.MechReader();
/* 13638 */         m = XMLr.ReadMech(filename, this.data);
/* 13639 */         this.CurMech = m;
/* 13640 */         LoadMechIntoGUI();
/* 13641 */         this.Prefs.put("Currentfile", filename);
/*       */       }
/*       */       catch (Exception e) {
/* 13644 */         Media.Messager(e.getMessage());
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   public void LoadMechIntoGUI()
/*       */   {
/* 13651 */     this.Load = true;
/*       */     
/*       */ 
/* 13654 */     UnlockGUIFromOmni();
/* 13655 */     BuildMechTypeSelector();
/* 13656 */     if (this.CurMech.IsQuad()) {
/* 13657 */       this.cmbMotiveType.setSelectedIndex(1);
/* 13658 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("FLL");
/* 13659 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("FRL");
/* 13660 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("RLL");
/* 13661 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RRL");
/* 13662 */       this.scrRACrits.setPreferredSize(new Dimension(105, 87));
/* 13663 */       this.scrLACrits.setPreferredSize(new Dimension(105, 87));
/*       */     } else {
/* 13665 */       this.cmbMotiveType.setSelectedIndex(0);
/* 13666 */       ((TitledBorder)this.pnlLAArmorBox.getBorder()).setTitle("LA");
/* 13667 */       ((TitledBorder)this.pnlRAArmorBox.getBorder()).setTitle("RA");
/* 13668 */       ((TitledBorder)this.pnlLLArmorBox.getBorder()).setTitle("LL");
/* 13669 */       ((TitledBorder)this.pnlRLArmorBox.getBorder()).setTitle("RL");
/* 13670 */       this.scrRACrits.setPreferredSize(new Dimension(105, 170));
/* 13671 */       this.scrLACrits.setPreferredSize(new Dimension(105, 170));
/*       */     }
/* 13673 */     if (this.CurMech.IsIndustrialmech()) {
/* 13674 */       if (this.CurMech.IsPrimitive()) {
/* 13675 */         this.cmbMechType.setSelectedIndex(3);
/*       */       } else {
/* 13677 */         this.cmbMechType.setSelectedIndex(1);
/*       */       }
/*       */     }
/* 13680 */     else if (this.CurMech.IsPrimitive()) {
/* 13681 */       this.cmbMechType.setSelectedIndex(2);
/*       */     } else {
/* 13683 */       this.cmbMechType.setSelectedIndex(0);
/*       */     }
/*       */     
/* 13686 */     this.chkYearRestrict.setSelected(this.CurMech.IsYearRestricted());
/* 13687 */     this.txtProdYear.setText("" + this.CurMech.GetYear());
/* 13688 */     this.cmbMechEra.setEnabled(true);
/* 13689 */     this.cmbTechBase.setEnabled(true);
/* 13690 */     this.txtProdYear.setEnabled(true);
/* 13691 */     switch (this.CurMech.GetEra()) {
/*       */     case 0: 
/* 13693 */       this.lblEraYears.setText("2443 ~ 2800");
/* 13694 */       break;
/*       */     case 1: 
/* 13696 */       this.lblEraYears.setText("2801 ~ 3050");
/* 13697 */       break;
/*       */     case 2: 
/* 13699 */       this.lblEraYears.setText("3051 ~ 3085");
/* 13700 */       break;
/*       */     case 3: 
/* 13702 */       this.lblEraYears.setText("3086 on");
/* 13703 */       break;
/*       */     case 4: 
/* 13705 */       this.lblEraYears.setText("Any");
/*       */     }
/*       */     
/*       */     
/* 13709 */     this.cmbRulesLevel.setSelectedIndex(this.CurMech.GetRulesLevel());
/* 13710 */     this.cmbMechEra.setSelectedIndex(this.CurMech.GetEra());
/* 13711 */     this.cmbProductionEra.setSelectedIndex(this.CurMech.GetProductionEra());
/*       */     
/*       */ 
/* 13714 */     this.Load = false;
/*       */     
/* 13716 */     if (this.chkYearRestrict.isSelected()) {
/* 13717 */       this.cmbMechEra.setEnabled(false);
/* 13718 */       this.cmbTechBase.setEnabled(false);
/* 13719 */       this.txtProdYear.setEnabled(false);
/*       */     }
/* 13721 */     this.txtMechName.setText(this.CurMech.GetName());
/* 13722 */     this.txtMechModel.setText(this.CurMech.GetModel());
/*       */     
/* 13724 */     if (this.CurMech.IsOmnimech()) {
/* 13725 */       LockGUIForOmni();
/* 13726 */       RefreshOmniVariants();
/* 13727 */       RefreshOmniChoices();
/*       */     }
/*       */     
/* 13730 */     BuildTechBaseSelector();
/* 13731 */     this.cmbTechBase.setSelectedIndex(this.CurMech.GetLoadout().GetTechBase());
/*       */     
/* 13733 */     FixTransferHandlers();
/*       */     
/* 13735 */     ResetTonnageSelector();
/* 13736 */     BuildChassisSelector();
/* 13737 */     BuildEngineSelector();
/* 13738 */     BuildGyroSelector();
/* 13739 */     BuildCockpitSelector();
/* 13740 */     BuildEnhancementSelector();
/* 13741 */     BuildHeatsinkSelector();
/* 13742 */     BuildJumpJetSelector();
/* 13743 */     BuildArmorSelector();
/* 13744 */     this.cmbInternalType.setSelectedItem(BuildLookupName(this.CurMech.GetIntStruc().GetCurrentState()));
/* 13745 */     this.cmbEngineType.setSelectedItem(BuildLookupName(this.CurMech.GetEngine().GetCurrentState()));
/* 13746 */     this.cmbGyroType.setSelectedItem(BuildLookupName(this.CurMech.GetGyro().GetCurrentState()));
/* 13747 */     this.cmbCockpitType.setSelectedItem(this.CurMech.GetCockpit().LookupName());
/* 13748 */     this.cmbPhysEnhance.setSelectedItem(BuildLookupName(this.CurMech.GetPhysEnhance().GetCurrentState()));
/* 13749 */     this.cmbHeatSinkType.setSelectedItem(BuildLookupName(this.CurMech.GetHeatSinks().GetCurrentState()));
/* 13750 */     this.cmbJumpJetType.setSelectedItem(this.CurMech.GetJumpJets().LookupName());
/* 13751 */     this.cmbArmorType.setSelectedItem(BuildLookupName(this.CurMech.GetArmor().GetCurrentState()));
/* 13752 */     SetPatchworkArmor();
/* 13753 */     FixWalkMPSpinner();
/* 13754 */     FixHeatSinkSpinnerModel();
/* 13755 */     FixJJSpinnerModel();
/* 13756 */     RefreshInternalPoints();
/* 13757 */     FixArmorSpinners();
/* 13758 */     this.data.Rebuild(this.CurMech);
/* 13759 */     RefreshEquipment();
/* 13760 */     this.chkCTCASE.setSelected(this.CurMech.HasCTCase());
/* 13761 */     this.chkLTCASE.setSelected(this.CurMech.HasLTCase());
/* 13762 */     this.chkRTCASE.setSelected(this.CurMech.HasRTCase());
/* 13763 */     this.chkUseTC.setSelected(this.CurMech.UsingTC());
/* 13764 */     this.chkClanCASE.setSelected(this.CurMech.GetLoadout().IsUsingClanCASE());
/* 13765 */     this.chkNullSig.setSelected(this.CurMech.HasNullSig());
/* 13766 */     this.chkVoidSig.setSelected(this.CurMech.HasVoidSig());
/* 13767 */     this.chkBSPFD.setSelected(this.CurMech.HasBlueShield());
/* 13768 */     this.chkCLPS.setSelected(this.CurMech.HasChameleon());
/* 13769 */     this.chkEnviroSealing.setSelected(this.CurMech.HasEnviroSealing());
/* 13770 */     this.chkEjectionSeat.setSelected(this.CurMech.HasEjectionSeat());
/* 13771 */     this.chkCommandConsole.setSelected(this.CurMech.HasCommandConsole());
/* 13772 */     this.chkFHES.setSelected(this.CurMech.HasFHES());
/* 13773 */     this.chkTracks.setSelected(this.CurMech.HasTracks());
/* 13774 */     this.chkRAAES.setSelected(this.CurMech.HasRAAES());
/* 13775 */     this.chkLAAES.setSelected(this.CurMech.HasLAAES());
/* 13776 */     this.chkLegAES.setSelected(this.CurMech.HasLegAES());
/* 13777 */     SetLoadoutArrays();
/* 13778 */     RefreshSummary();
/* 13779 */     RefreshInfoPane();
/* 13780 */     SetWeaponChoosers();
/* 13781 */     ResetAmmo();
/*       */     
/*       */ 
/* 13784 */     Media media = new Media();
/* 13785 */     media.blankLogo(this.lblFluffImage);
/* 13786 */     media.setLogo(this.lblFluffImage, media.DetermineMatchingImage(this.CurMech.GetName(), this.CurMech.GetModel(), this.CurMech.GetSSWImage()));
/*       */     
/* 13788 */     this.Overview.SetText(this.CurMech.GetOverview());
/* 13789 */     this.Capabilities.SetText(this.CurMech.GetCapabilities());
/* 13790 */     this.History.SetText(this.CurMech.GetHistory());
/* 13791 */     this.Deployment.SetText(this.CurMech.GetDeployment());
/* 13792 */     this.Variants.SetText(this.CurMech.GetVariants());
/* 13793 */     this.Notables.SetText(this.CurMech.GetNotables());
/* 13794 */     this.Additional.SetText(this.CurMech.GetAdditional());
/* 13795 */     this.txtManufacturer.setText(this.CurMech.GetCompany());
/* 13796 */     this.txtManufacturerLocation.setText(this.CurMech.GetLocation());
/* 13797 */     this.txtEngineManufacturer.setText(this.CurMech.GetEngineManufacturer());
/* 13798 */     this.txtArmorModel.setText(this.CurMech.GetArmorModel());
/* 13799 */     this.txtChassisModel.setText(this.CurMech.GetChassisModel());
/* 13800 */     if (this.CurMech.GetJumpJets().GetNumJJ() > 0) {
/* 13801 */       this.txtJJModel.setEnabled(true);
/*       */     }
/* 13803 */     this.txtSource.setText(this.CurMech.GetSource());
/*       */     
/*       */ 
/* 13806 */     this.txtJJModel.setText(this.CurMech.GetJJModel());
/* 13807 */     this.txtCommSystem.setText(this.CurMech.GetCommSystem());
/* 13808 */     this.txtTNTSystem.setText(this.CurMech.GetTandTSystem());
/*       */     
/*       */ 
/* 13811 */     if (this.CurMech.GetEngine().IsNuclear()) {
/* 13812 */       this.lblSumPAmps.setVisible(false);
/* 13813 */       this.txtSumPAmpsTon.setVisible(false);
/* 13814 */       this.txtSumPAmpsACode.setVisible(false);
/*       */     } else {
/* 13816 */       this.lblSumPAmps.setVisible(true);
/* 13817 */       this.txtSumPAmpsTon.setVisible(true);
/* 13818 */       this.txtSumPAmpsACode.setVisible(true);
/*       */     }
/*       */     
/* 13821 */     setTitle("SSW 0.6.83.1 - " + this.CurMech.GetName() + " " + this.CurMech.GetModel());
/*       */   }
/*       */   
/*       */   private void mnuExportClipboardActionPerformed(ActionEvent evt)
/*       */   {
/* 13826 */     String CurLoadout = "";
/* 13827 */     String output = "";
/*       */     
/* 13829 */     if (this.CurMech.IsOmnimech()) {
/* 13830 */       CurLoadout = this.CurMech.GetLoadout().GetName();
/*       */     }
/*       */     
/*       */ 
/* 13834 */     SolidifyMech();
/*       */     
/* 13836 */     if (!VerifyMech(evt)) {
/* 13837 */       return;
/*       */     }
/*       */     
/* 13840 */     filehandlers.TXTWriter txtw = new filehandlers.TXTWriter(this.CurMech);
/* 13841 */     output = txtw.GetTextExport();
/* 13842 */     java.awt.datatransfer.StringSelection export = new java.awt.datatransfer.StringSelection(output);
/*       */     
/*       */ 
/* 13845 */     if (this.CurMech.IsOmnimech()) {
/* 13846 */       this.cmbOmniVariant.setSelectedItem(CurLoadout);
/* 13847 */       cmbOmniVariantActionPerformed(evt);
/*       */     }
/* 13849 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/* 13850 */     clipboard.setContents(export, this);
/*       */   }
/*       */   
/*       */   private void btnSaveIconActionPerformed(ActionEvent evt) {
/* 13854 */     mnuSaveActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnPrintIconActionPerformed(ActionEvent evt) {
/* 13858 */     mnuPrintCurrentMechActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnNewIconActionPerformed(ActionEvent evt) {
/* 13862 */     mnuNewMechActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnOptionsIconActionPerformed(ActionEvent evt) {
/* 13866 */     mnuOptionsActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnOpenActionPerformed(ActionEvent evt)
/*       */   {
/* 13871 */     mnuOpenActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void mnuViewToolbarActionPerformed(ActionEvent evt) {
/* 13875 */     setViewToolbar(this.mnuViewToolbar.getState());
/*       */   }
/*       */   
/*       */   private void btnExportHTMLIconActionPerformed(ActionEvent evt) {
/* 13879 */     mnuExportHTMLActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnExportTextIconActionPerformed(ActionEvent evt) {
/* 13883 */     mnuExportTXTActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnExportMTFIconActionPerformed(ActionEvent evt) {
/* 13887 */     mnuExportMTFActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnExportClipboardIconActionPerformed(ActionEvent evt) {
/* 13891 */     mnuExportClipboardActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnPostToS7ActionPerformed(ActionEvent evt) {
/* 13895 */     mnuPostS7ActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void btnRemoveItemCritsActionPerformed(ActionEvent evt) {
/* 13899 */     int Index = this.lstCritsToPlace.getSelectedIndex();
/* 13900 */     if (Index < 0) {
/* 13901 */       this.btnAutoAllocate.setEnabled(false);
/* 13902 */       this.btnSelectiveAllocate.setEnabled(false);
/* 13903 */       this.btnRemoveItemCrits.setEnabled(false);
/* 13904 */       return;
/*       */     }
/* 13906 */     this.CurItem = this.CurMech.GetLoadout().GetFromQueueByIndex(Index);
/* 13907 */     RemoveItemCritTab();
/*       */   }
/*       */   
/*       */   private void chkCTCASE2ActionPerformed(ActionEvent evt) {
/* 13911 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasCTCASEII())) {
/* 13912 */       this.chkCTCASE2.setSelected(true);
/* 13913 */       return;
/*       */     }
/* 13915 */     if (this.CurMech.GetLoadout().HasCTCASEII() == this.chkCTCASE2.isSelected()) return;
/* 13916 */     if (this.chkCTCASE2.isSelected()) {
/*       */       try {
/* 13918 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 13919 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 13920 */           tech.setLocationRelativeTo(this);
/* 13921 */           tech.setVisible(true);
/* 13922 */           this.CurMech.GetLoadout().SetCTCASEII(true, -1, tech.IsClan());
/* 13923 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 13924 */           this.CurMech.GetLoadout().SetCTCASEII(true, -1, true);
/*       */         } else {
/* 13926 */           this.CurMech.GetLoadout().SetCTCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 13929 */         Media.Messager(this, e.getMessage());
/* 13930 */         this.chkCTCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 13934 */         this.CurMech.GetLoadout().SetCTCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 13937 */         System.err.println("Received an error removing CT CASE II:");
/* 13938 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 13941 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRACASE2ActionPerformed(ActionEvent evt) {
/* 13945 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRACASEII())) {
/* 13946 */       this.chkRACASE2.setSelected(true);
/* 13947 */       return;
/*       */     }
/* 13949 */     if (this.CurMech.GetLoadout().HasRACASEII() == this.chkRACASE2.isSelected()) return;
/* 13950 */     if (this.chkRACASE2.isSelected()) {
/*       */       try {
/* 13952 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 13953 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 13954 */           tech.setLocationRelativeTo(this);
/* 13955 */           tech.setVisible(true);
/* 13956 */           this.CurMech.GetLoadout().SetRACASEII(true, -1, tech.IsClan());
/* 13957 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 13958 */           this.CurMech.GetLoadout().SetRACASEII(true, -1, true);
/*       */         } else {
/* 13960 */           this.CurMech.GetLoadout().SetRACASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 13963 */         Media.Messager(this, e.getMessage());
/* 13964 */         this.chkRACASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 13968 */         this.CurMech.GetLoadout().SetRACASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 13971 */         System.err.println("Received an error removing RA CASE II:");
/* 13972 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 13975 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRTCASE2ActionPerformed(ActionEvent evt) {
/* 13979 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRTCASEII())) {
/* 13980 */       this.chkRTCASE2.setSelected(true);
/* 13981 */       return;
/*       */     }
/* 13983 */     if (this.CurMech.GetLoadout().HasRTCASEII() == this.chkRTCASE2.isSelected()) return;
/* 13984 */     if (this.chkRTCASE2.isSelected()) {
/*       */       try {
/* 13986 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 13987 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 13988 */           tech.setLocationRelativeTo(this);
/* 13989 */           tech.setVisible(true);
/* 13990 */           this.CurMech.GetLoadout().SetRTCASEII(true, -1, tech.IsClan());
/* 13991 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 13992 */           this.CurMech.GetLoadout().SetRTCASEII(true, -1, true);
/*       */         } else {
/* 13994 */           this.CurMech.GetLoadout().SetRTCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 13997 */         Media.Messager(this, e.getMessage());
/* 13998 */         this.chkRTCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14002 */         this.CurMech.GetLoadout().SetRTCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 14005 */         System.err.println("Received an error removing RT CASE II:");
/* 14006 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 14009 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRLCASE2ActionPerformed(ActionEvent evt) {
/* 14013 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRLCASEII())) {
/* 14014 */       this.chkRLCASE2.setSelected(true);
/* 14015 */       return;
/*       */     }
/* 14017 */     if (this.CurMech.GetLoadout().HasRLCASEII() == this.chkRLCASE2.isSelected()) return;
/* 14018 */     if (this.chkRLCASE2.isSelected()) {
/*       */       try {
/* 14020 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 14021 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 14022 */           tech.setLocationRelativeTo(this);
/* 14023 */           tech.setVisible(true);
/* 14024 */           this.CurMech.GetLoadout().SetRLCASEII(true, -1, tech.IsClan());
/* 14025 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 14026 */           this.CurMech.GetLoadout().SetRLCASEII(true, -1, true);
/*       */         } else {
/* 14028 */           this.CurMech.GetLoadout().SetRLCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 14031 */         Media.Messager(this, e.getMessage());
/* 14032 */         this.chkRLCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14036 */         this.CurMech.GetLoadout().SetRLCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 14039 */         System.err.println("Received an error removing RL CASE II:");
/* 14040 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 14043 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkHDCASE2ActionPerformed(ActionEvent evt) {
/* 14047 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasHDCASEII())) {
/* 14048 */       this.chkHDCASE2.setSelected(true);
/* 14049 */       return;
/*       */     }
/* 14051 */     if (this.CurMech.GetLoadout().HasHDCASEII() == this.chkHDCASE2.isSelected()) return;
/* 14052 */     if (this.chkHDCASE2.isSelected()) {
/*       */       try {
/* 14054 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 14055 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 14056 */           tech.setLocationRelativeTo(this);
/* 14057 */           tech.setVisible(true);
/* 14058 */           this.CurMech.GetLoadout().SetHDCASEII(true, -1, tech.IsClan());
/* 14059 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 14060 */           this.CurMech.GetLoadout().SetHDCASEII(true, -1, true);
/*       */         } else {
/* 14062 */           this.CurMech.GetLoadout().SetHDCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 14065 */         Media.Messager(this, e.getMessage());
/* 14066 */         this.chkHDCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14070 */         this.CurMech.GetLoadout().SetHDCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 14073 */         System.err.println("Received an error removing HD CASE II:");
/* 14074 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 14077 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLTCASE2ActionPerformed(ActionEvent evt) {
/* 14081 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasLTCASEII())) {
/* 14082 */       this.chkLTCASE2.setSelected(true);
/* 14083 */       return;
/*       */     }
/* 14085 */     if (this.CurMech.GetLoadout().HasLTCASEII() == this.chkLTCASE2.isSelected()) return;
/* 14086 */     if (this.chkLTCASE2.isSelected()) {
/*       */       try {
/* 14088 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 14089 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 14090 */           tech.setLocationRelativeTo(this);
/* 14091 */           tech.setVisible(true);
/* 14092 */           this.CurMech.GetLoadout().SetLTCASEII(true, -1, tech.IsClan());
/* 14093 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 14094 */           this.CurMech.GetLoadout().SetLTCASEII(true, -1, true);
/*       */         } else {
/* 14096 */           this.CurMech.GetLoadout().SetLTCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 14099 */         Media.Messager(this, e.getMessage());
/* 14100 */         this.chkLTCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14104 */         this.CurMech.GetLoadout().SetLTCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 14107 */         System.err.println("Received an error removing LT CASE II:");
/* 14108 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 14111 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLLCASE2ActionPerformed(ActionEvent evt) {
/* 14115 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasLLCASEII())) {
/* 14116 */       this.chkLLCASE2.setSelected(true);
/* 14117 */       return;
/*       */     }
/* 14119 */     if (this.CurMech.GetLoadout().HasLLCASEII() == this.chkLLCASE2.isSelected()) return;
/* 14120 */     if (this.chkLLCASE2.isSelected()) {
/*       */       try {
/* 14122 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 14123 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 14124 */           tech.setLocationRelativeTo(this);
/* 14125 */           tech.setVisible(true);
/* 14126 */           this.CurMech.GetLoadout().SetLLCASEII(true, -1, tech.IsClan());
/* 14127 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 14128 */           this.CurMech.GetLoadout().SetLLCASEII(true, -1, true);
/*       */         } else {
/* 14130 */           this.CurMech.GetLoadout().SetLLCASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 14133 */         Media.Messager(this, e.getMessage());
/* 14134 */         this.chkLLCASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14138 */         this.CurMech.GetLoadout().SetLLCASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 14141 */         System.err.println("Received an error removing LL CASE II:");
/* 14142 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 14145 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLACASE2ActionPerformed(ActionEvent evt) {
/* 14149 */     if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasLACASEII())) {
/* 14150 */       this.chkLACASE2.setSelected(true);
/* 14151 */       return;
/*       */     }
/* 14153 */     if (this.CurMech.GetLoadout().HasLACASEII() == this.chkLACASE2.isSelected()) return;
/* 14154 */     if (this.chkLACASE2.isSelected()) {
/*       */       try {
/* 14156 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 14157 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 14158 */           tech.setLocationRelativeTo(this);
/* 14159 */           tech.setVisible(true);
/* 14160 */           this.CurMech.GetLoadout().SetLACASEII(true, -1, tech.IsClan());
/* 14161 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 14162 */           this.CurMech.GetLoadout().SetLACASEII(true, -1, true);
/*       */         } else {
/* 14164 */           this.CurMech.GetLoadout().SetLACASEII(true, -1, false);
/*       */         }
/*       */       } catch (Exception e) {
/* 14167 */         Media.Messager(this, e.getMessage());
/* 14168 */         this.chkLACASE2.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14172 */         this.CurMech.GetLoadout().SetLACASEII(false, -1, false);
/*       */       }
/*       */       catch (Exception e) {
/* 14175 */         System.err.println("Received an error removing LA CASE II:");
/* 14176 */         System.err.println(e.getStackTrace());
/*       */       }
/*       */     }
/* 14179 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */ 
/*       */   private void mnuFileActionPerformed(ActionEvent evt) {}
/*       */   
/*       */   private void mnuPrintBatchActionPerformed(ActionEvent evt)
/*       */   {
/* 14187 */     if (this.BatchWindow == null) this.BatchWindow = new dlgPrintBatchMechs(this, true);
/* 14188 */     this.BatchWindow.setLocationRelativeTo(this);
/* 14189 */     this.BatchWindow.setVisible(true);
/*       */   }
/*       */   
/*       */   private void cmbMechTypeActionPerformed(ActionEvent evt) {
/* 14193 */     switch (this.cmbMechType.getSelectedIndex()) {
/*       */     case 0: 
/* 14195 */       if (((!this.CurMech.IsIndustrialmech() ? 1 : 0) & (!this.CurMech.IsPrimitive() ? 1 : 0)) != 0) return;
/* 14196 */       this.CurMech.SetModern();
/* 14197 */       this.CurMech.SetBattlemech();
/* 14198 */       break;
/*       */     case 1: 
/* 14200 */       if ((this.CurMech.IsIndustrialmech() & !this.CurMech.IsPrimitive())) return;
/* 14201 */       this.CurMech.SetModern();
/* 14202 */       this.CurMech.SetIndustrialmech();
/* 14203 */       break;
/*       */     case 2: 
/* 14205 */       if ((!this.CurMech.IsIndustrialmech()) && (this.CurMech.IsPrimitive())) return;
/* 14206 */       this.CurMech.SetPrimitive();
/* 14207 */       this.CurMech.SetBattlemech();
/* 14208 */       break;
/*       */     case 3: 
/* 14210 */       if ((this.CurMech.IsIndustrialmech()) && (this.CurMech.IsPrimitive())) return;
/* 14211 */       this.CurMech.SetPrimitive();
/* 14212 */       this.CurMech.SetIndustrialmech();
/*       */     }
/*       */     
/*       */     
/*       */ 
/* 14217 */     CheckTonnage(false);
/*       */     
/*       */ 
/* 14220 */     SetLoadoutArrays();
/*       */     
/*       */ 
/* 14223 */     FixArmorSpinners();
/*       */     
/*       */ 
/* 14226 */     SaveSelections();
/* 14227 */     BuildChassisSelector();
/* 14228 */     BuildEngineSelector();
/* 14229 */     BuildGyroSelector();
/* 14230 */     BuildCockpitSelector();
/* 14231 */     BuildEnhancementSelector();
/* 14232 */     BuildHeatsinkSelector();
/* 14233 */     BuildJumpJetSelector();
/* 14234 */     BuildArmorSelector();
/* 14235 */     RefreshEquipment();
/* 14236 */     CheckOmnimech();
/*       */     
/*       */ 
/* 14239 */     LoadSelections();
/*       */     
/* 14241 */     RecalcEngine();
/* 14242 */     FixWalkMPSpinner();
/* 14243 */     FixJJSpinnerModel();
/* 14244 */     RecalcGyro();
/* 14245 */     RecalcIntStruc();
/* 14246 */     RecalcCockpit();
/* 14247 */     this.CurMech.GetActuators().PlaceActuators();
/* 14248 */     RecalcHeatSinks();
/* 14249 */     RecalcJumpJets();
/* 14250 */     RecalcEnhancements();
/* 14251 */     RecalcArmor();
/* 14252 */     RecalcEquipment();
/*       */     
/*       */ 
/*       */ 
/* 14256 */     this.CurMech.GetLoadout().FlushIllegal();
/*       */     
/*       */ 
/*       */ 
/* 14260 */     RefreshSummary();
/* 14261 */     RefreshInfoPane();
/* 14262 */     SetWeaponChoosers();
/* 14263 */     ResetAmmo();
/*       */   }
/*       */   
/*       */   private void chkEnviroSealingActionPerformed(ActionEvent evt)
/*       */   {
/* 14268 */     if (this.chkEnviroSealing.isSelected() == this.CurMech.HasEnviroSealing()) return;
/*       */     try {
/* 14270 */       if (this.chkEnviroSealing.isSelected()) {
/* 14271 */         this.CurMech.SetEnviroSealing(true);
/*       */       } else {
/* 14273 */         this.CurMech.SetEnviroSealing(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 14276 */       Media.Messager(this, e.getMessage());
/*       */       
/* 14278 */       this.chkEnviroSealing.setSelected(this.CurMech.HasEnviroSealing());
/* 14279 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14283 */     RefreshSummary();
/* 14284 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkEjectionSeatActionPerformed(ActionEvent evt)
/*       */   {
/* 14289 */     if (this.chkEjectionSeat.isSelected() == this.CurMech.HasEjectionSeat()) return;
/*       */     try {
/* 14291 */       if (this.chkEjectionSeat.isSelected()) {
/* 14292 */         this.CurMech.SetEjectionSeat(true);
/*       */       } else {
/* 14294 */         this.CurMech.SetEjectionSeat(false);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 14298 */       this.chkEjectionSeat.setSelected(this.CurMech.HasEjectionSeat());
/* 14299 */       Media.Messager(this, e.getMessage());
/* 14300 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14304 */     RefreshSummary();
/* 14305 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void mnuPrintPreviewActionPerformed(ActionEvent evt)
/*       */   {
/* 14310 */     ssw.print.Printer printer = new ssw.print.Printer(this);
/* 14311 */     printer.setCharts(Boolean.valueOf(this.Prefs.getBoolean("UseCharts", false)));
/* 14312 */     printer.setCanon(this.Prefs.getBoolean("UseCanonDots", false));
/* 14313 */     printer.AddMech(this.CurMech);
/*       */     
/*       */ 
/* 14316 */     ssw.printpreview.dlgPreview preview = new ssw.printpreview.dlgPreview(this.CurMech.GetFullName(), this, printer, printer.Preview(), 0.0D);
/* 14317 */     preview.setSize(1024, 768);
/* 14318 */     preview.setLocationRelativeTo(null);
/* 14319 */     preview.setVisible(true);
/*       */   }
/*       */   
/*       */   private void btnPrintPreviewActionPerformed(ActionEvent evt) {
/* 14323 */     mnuPrintPreviewActionPerformed(evt);
/*       */   }
/*       */   
/*       */   private void chkLegAESActionPerformed(ActionEvent evt) {
/* 14327 */     if (this.chkLegAES.isSelected() == this.CurMech.HasLegAES()) return;
/*       */     try {
/* 14329 */       if (this.chkLegAES.isSelected()) {
/* 14330 */         this.CurMech.SetLegAES(true, null);
/*       */       } else {
/* 14332 */         this.CurMech.SetLegAES(false, null);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 14336 */       this.chkLegAES.setSelected(this.CurMech.HasLegAES());
/* 14337 */       Media.Messager(this, e.getMessage());
/* 14338 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14342 */     RefreshSummary();
/* 14343 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLAAESActionPerformed(ActionEvent evt) {
/* 14347 */     if (this.chkLAAES.isSelected() == this.CurMech.HasLAAES()) return;
/*       */     try {
/* 14349 */       if (this.chkLAAES.isSelected()) {
/* 14350 */         this.CurMech.SetLAAES(true, -1);
/*       */       } else {
/* 14352 */         this.CurMech.SetLAAES(false, -1);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 14356 */       this.chkLAAES.setSelected(this.CurMech.HasLAAES());
/* 14357 */       Media.Messager(this, e.getMessage());
/* 14358 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14362 */     RefreshSummary();
/* 14363 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRAAESActionPerformed(ActionEvent evt) {
/* 14367 */     if (this.chkRAAES.isSelected() == this.CurMech.HasRAAES()) return;
/*       */     try {
/* 14369 */       if (this.chkRAAES.isSelected()) {
/* 14370 */         this.CurMech.SetRAAES(true, -1);
/*       */       } else {
/* 14372 */         this.CurMech.SetRAAES(false, -1);
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/* 14376 */       this.chkRAAES.setSelected(this.CurMech.HasRAAES());
/* 14377 */       Media.Messager(this, e.getMessage());
/* 14378 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14382 */     RefreshSummary();
/* 14383 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void formWindowClosing(java.awt.event.WindowEvent evt) {
/* 14387 */     CloseProgram();
/*       */   }
/*       */   
/*       */   private void chkTracksActionPerformed(ActionEvent evt)
/*       */   {
/* 14392 */     if (this.chkTracks.isSelected() == this.CurMech.HasTracks()) return;
/*       */     try {
/* 14394 */       if (this.chkTracks.isSelected()) {
/* 14395 */         this.CurMech.SetTracks(true);
/*       */       } else {
/* 14397 */         this.CurMech.SetTracks(false);
/*       */       }
/*       */     } catch (Exception e) {
/* 14400 */       Media.Messager(this, e.getMessage());
/*       */       
/* 14402 */       this.chkTracks.setSelected(this.CurMech.HasTracks());
/* 14403 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14407 */     RefreshSummary();
/* 14408 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void btnForceListActionPerformed(ActionEvent evt)
/*       */   {
/* 14413 */     this.dForce.setLocationRelativeTo(this);
/* 14414 */     this.dForce.setVisible(true);
/*       */   }
/*       */   
/*       */   private void btnAddToForceListActionPerformed(ActionEvent evt) {
/* 14418 */     this.SetSource = false;
/* 14419 */     SolidifyMech();
/* 14420 */     QuickSave();
/* 14421 */     if (VerifyMech(evt)) {
/* 14422 */       this.dForce.Add(this.CurMech, this.Prefs.get("Currentfile", ""));
/*       */     }
/* 14424 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */ 
/*       */   private void mnuCreateTCGMechActionPerformed(ActionEvent evt)
/*       */   {
/* 14430 */     SolidifyMech();
/* 14431 */     dlgCCGMech ccgMech = new dlgCCGMech(this, true, this.CurMech);
/* 14432 */     ccgMech.setLocationRelativeTo(this);
/* 14433 */     ccgMech.setVisible(true);
/*       */   }
/*       */   
/*       */   private void mnuTextTROActionPerformed(ActionEvent evt) {
/* 14437 */     this.SetSource = false;
/* 14438 */     SolidifyMech();
/* 14439 */     dlgTextExport Text = new dlgTextExport(this, true, this.CurMech);
/* 14440 */     Text.setLocationRelativeTo(this);
/* 14441 */     Text.setVisible(true);
/* 14442 */     this.CurMech.SetCurLoadout((String)this.cmbOmniVariant.getSelectedItem());
/* 14443 */     this.SetSource = true;
/*       */   }
/*       */   
/*       */   private void chkChartFrontActionPerformed(ActionEvent evt) {
/* 14447 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkChartRearActionPerformed(ActionEvent evt) {
/* 14451 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkChartRightActionPerformed(ActionEvent evt) {
/* 14455 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkChartLeftActionPerformed(ActionEvent evt) {
/* 14459 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkCommandConsoleActionPerformed(ActionEvent evt) {
/* 14463 */     if (this.chkCommandConsole.isSelected() == this.CurMech.HasCommandConsole()) return;
/* 14464 */     if (this.chkCommandConsole.isSelected()) {
/* 14465 */       if (!this.CurMech.SetCommandConsole(true)) {
/* 14466 */         Media.Messager(this, "Command Console cannot be allocated.");
/* 14467 */         this.chkCommandConsole.setSelected(false);
/*       */       }
/*       */     } else {
/* 14470 */       this.CurMech.SetCommandConsole(false);
/*       */     }
/*       */     
/*       */ 
/* 14474 */     RefreshEquipment();
/* 14475 */     RefreshSummary();
/* 14476 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkFCSAIVActionPerformed(ActionEvent evt) {
/* 14480 */     if (this.CurMech.UsingArtemisIV() == this.chkFCSAIV.isSelected()) return;
/* 14481 */     if (this.chkFCSAIV.isSelected()) {
/*       */       try {
/* 14483 */         this.CurMech.SetFCSArtemisIV(true);
/*       */       } catch (Exception e) {
/* 14485 */         Media.Messager(this, e.getMessage());
/* 14486 */         this.chkFCSAIV.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14490 */         this.CurMech.SetFCSArtemisIV(false);
/*       */       } catch (Exception e) {
/* 14492 */         Media.Messager(this, e.getMessage());
/* 14493 */         this.chkFCSAIV.setSelected(true);
/*       */       }
/*       */     }
/*       */     
/* 14497 */     RefreshSummary();
/* 14498 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkFCSAVActionPerformed(ActionEvent evt) {
/* 14502 */     if (this.CurMech.UsingArtemisV() == this.chkFCSAV.isSelected()) return;
/* 14503 */     if (this.chkFCSAV.isSelected()) {
/*       */       try {
/* 14505 */         this.CurMech.SetFCSArtemisV(true);
/*       */       } catch (Exception e) {
/* 14507 */         Media.Messager(this, e.getMessage());
/* 14508 */         this.chkFCSAV.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14512 */         this.CurMech.SetFCSArtemisV(false);
/*       */       } catch (Exception e) {
/* 14514 */         Media.Messager(this, e.getMessage());
/* 14515 */         this.chkFCSAV.setSelected(true);
/*       */       }
/*       */     }
/*       */     
/* 14519 */     RefreshSummary();
/* 14520 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkFCSApolloActionPerformed(ActionEvent evt) {
/* 14524 */     if (this.CurMech.UsingApollo() == this.chkFCSApollo.isSelected()) return;
/* 14525 */     if (this.chkFCSApollo.isSelected()) {
/*       */       try {
/* 14527 */         this.CurMech.SetFCSApollo(true);
/*       */       } catch (Exception e) {
/* 14529 */         Media.Messager(this, e.getMessage());
/* 14530 */         this.chkFCSApollo.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14534 */         this.CurMech.SetFCSApollo(false);
/*       */       } catch (Exception e) {
/* 14536 */         Media.Messager(this, e.getMessage());
/* 14537 */         this.chkFCSApollo.setSelected(true);
/*       */       }
/*       */     }
/*       */     
/* 14541 */     RefreshSummary();
/* 14542 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkClanCASEActionPerformed(ActionEvent evt) {
/* 14546 */     this.CurMech.GetLoadout().SetClanCASE(this.chkClanCASE.isSelected());
/* 14547 */     RefreshSummary();
/* 14548 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void lstSelectedEquipmentKeyPressed(java.awt.event.KeyEvent evt) {
/* 14552 */     if (evt.getKeyCode() == 127) {
/* 14553 */       btnRemoveEquipActionPerformed(new ActionEvent(evt.getSource(), evt.getID(), null));
/*       */     }
/*       */   }
/*       */   
/*       */   private void mnuImportHMPActionPerformed(ActionEvent evt) {
/* 14558 */     if (this.CurMech.HasChanged()) {
/* 14559 */       int choice = javax.swing.JOptionPane.showConfirmDialog(this, "The current 'Mech has changed.\nDo you want to discard those changes?", "Discard Changes?", 0);
/*       */       
/* 14561 */       if (choice == 1) { return;
/*       */       }
/*       */     }
/*       */     
/* 14565 */     Mech m = null;
/*       */     
/* 14567 */     File tempFile = new File(this.Prefs.get("LastOpenDirectory", ""));
/* 14568 */     JFileChooser fc = new JFileChooser();
/* 14569 */     fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
/*       */       public boolean accept(File f) {
/* 14571 */         if (f.isDirectory()) {
/* 14572 */           return true;
/*       */         }
/*       */         
/* 14575 */         String extension = Utils.getExtension(f);
/* 14576 */         if (extension != null) {
/* 14577 */           if (extension.equals("hmp")) {
/* 14578 */             return true;
/*       */           }
/* 14580 */           return false;
/*       */         }
/*       */         
/* 14583 */         return false;
/*       */       }
/*       */       
/*       */       public String getDescription()
/*       */       {
/* 14588 */         return "*.hmp";
/*       */       }
/* 14590 */     });
/* 14591 */     fc.setAcceptAllFileFilterUsed(false);
/* 14592 */     fc.setCurrentDirectory(tempFile);
/* 14593 */     int returnVal = fc.showDialog(this, "Import HMP File");
/* 14594 */     if (returnVal != 0) return;
/* 14595 */     File loadmech = fc.getSelectedFile();
/* 14596 */     String filename = "";
/*       */     try {
/* 14598 */       filename = loadmech.getCanonicalPath();
/* 14599 */       this.Prefs.put("LastOpenDirectory", loadmech.getCanonicalPath().replace(loadmech.getName(), ""));
/* 14600 */       this.Prefs.put("LastOpenFile", loadmech.getName());
/*       */     } catch (Exception e) {
/* 14602 */       Media.Messager(this, "There was a problem opening the file:\n" + e.getMessage());
/* 14603 */       return;
/*       */     }
/*       */     
/* 14606 */     String Messages = "";
/*       */     try {
/* 14608 */       ssw.filehandlers.HMPReader HMPr = new ssw.filehandlers.HMPReader();
/* 14609 */       m = HMPr.GetMech(filename, false);
/* 14610 */       Messages = HMPr.GetErrors();
/*       */     }
/*       */     catch (Exception e) {
/* 14613 */       if (e.getMessage() == null) {
/* 14614 */         Media.Messager(this, "An unknown error has occured.  The log file has been updated.");
/* 14615 */         e.printStackTrace();
/*       */       } else {
/* 14617 */         Media.Messager(this, e.getMessage());
/*       */       }
/* 14619 */       return;
/*       */     }
/*       */     
/* 14622 */     if (Messages.length() > 0) {
/* 14623 */       dlgTextExport msgs = new dlgTextExport(this, false, Messages);
/* 14624 */       msgs.setLocationRelativeTo(this);
/* 14625 */       msgs.setVisible(true);
/*       */     }
/*       */     
/* 14628 */     this.CurMech = m;
/* 14629 */     LoadMechIntoGUI();
/* 14630 */     this.CurMech.SetChanged(false);
/*       */   }
/*       */   
/*       */   private void btnChatInfoActionPerformed(ActionEvent evt) {
/* 14634 */     java.awt.datatransfer.StringSelection export = new java.awt.datatransfer.StringSelection(this.CurMech.GetChatInfo());
/* 14635 */     java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
/* 14636 */     clipboard.setContents(export, this);
/*       */   }
/*       */   
/*       */   private void chkFHESActionPerformed(ActionEvent evt) {
/* 14640 */     if (this.chkFHES.isSelected() == this.CurMech.HasFHES()) return;
/* 14641 */     if (this.chkFHES.isSelected()) {
/* 14642 */       this.CurMech.SetFHES(true);
/*       */     } else {
/* 14644 */       this.CurMech.SetFHES(false);
/*       */     }
/*       */     
/*       */ 
/* 14648 */     RefreshEquipment();
/* 14649 */     RefreshSummary();
/* 14650 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkPartialWingActionPerformed(ActionEvent evt) {
/* 14654 */     if (this.chkPartialWing.isSelected() == this.CurMech.UsingPartialWing()) return;
/* 14655 */     if (this.chkPartialWing.isSelected()) {
/*       */       try {
/* 14657 */         if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/* 14658 */           dlgTechBaseChooser tech = new dlgTechBaseChooser(this, true);
/* 14659 */           tech.setLocationRelativeTo(this);
/* 14660 */           tech.setVisible(true);
/* 14661 */           this.CurMech.SetPartialWing(this.chkPartialWing.isSelected(), tech.IsClan());
/* 14662 */         } else if (this.CurMech.GetLoadout().GetTechBase() == 1) {
/* 14663 */           this.CurMech.SetPartialWing(this.chkPartialWing.isSelected(), true);
/*       */         } else {
/* 14665 */           this.CurMech.SetPartialWing(this.chkPartialWing.isSelected(), false);
/*       */         }
/*       */       } catch (Exception e) {
/* 14668 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */     } else {
/*       */       try {
/* 14672 */         this.CurMech.SetPartialWing(false);
/*       */       } catch (Exception e) {
/* 14674 */         Media.Messager(this, e.getMessage());
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 14679 */     RefreshEquipment();
/* 14680 */     RefreshSummary();
/* 14681 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void mnuUnlockActionPerformed(ActionEvent evt) {
/* 14685 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to unlock the chassis?\nAll omnimech loadouts will be deleted\nand the 'Mech will revert to its base loadout.", "Unlock Chassis?", 0);
/*       */     
/*       */ 
/*       */ 
/* 14689 */     if (choice == 1) {
/* 14690 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14694 */     this.CurMech.UnlockChassis();
/* 14695 */     FixTransferHandlers();
/* 14696 */     SetLoadoutArrays();
/* 14697 */     FixJJSpinnerModel();
/* 14698 */     FixHeatSinkSpinnerModel();
/* 14699 */     LoadMechIntoGUI();
/*       */   }
/*       */   
/*       */   private void btnBracketChartActionPerformed(ActionEvent evt) {
/* 14703 */     dlgBracketCharts charts = new dlgBracketCharts(this, true, this.CurMech);
/* 14704 */     charts.setLocationRelativeTo(this);
/* 14705 */     charts.setVisible(true);
/*       */   }
/*       */   
/*       */   private void chkFractionalActionPerformed(ActionEvent evt) {
/* 14709 */     if (this.chkFractional.isSelected() == this.CurMech.UsingFractionalAccounting()) return;
/* 14710 */     this.CurMech.SetFractionalAccounting(this.chkFractional.isSelected());
/* 14711 */     if (!this.CurMech.UsingFractionalAccounting()) {
/* 14712 */       ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 14713 */       for (int i = 0; i < v.size(); i++) {
/* 14714 */         if ((v.get(i) instanceof Ammunition)) {
/* 14715 */           ((Ammunition)v.get(i)).ResetLotSize();
/*       */         }
/*       */       }
/*       */     }
/*       */     
/* 14720 */     RefreshEquipment();
/* 14721 */     RefreshSummary();
/* 14722 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void mnuBatchHMPActionPerformed(ActionEvent evt) {
/* 14726 */     dlgBatchHMP batch = new dlgBatchHMP(this, true);
/* 14727 */     batch.setLocationRelativeTo(this);
/* 14728 */     batch.setVisible(true);
/*       */   }
/*       */   
/*       */   private void chkAverageDamageActionPerformed(ActionEvent evt) {
/* 14732 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void chkShowTextNotGraphActionPerformed(ActionEvent evt) {
/* 14736 */     UpdateBasicChart();
/*       */   }
/*       */   
/*       */   private void btnRenameVariantActionPerformed(ActionEvent evt) {
/* 14740 */     SaveOmniFluffInfo();
/* 14741 */     String VariantName = "";
/*       */     
/*       */ 
/* 14744 */     dlgOmniBase input = new dlgOmniBase(this, true);
/* 14745 */     input.setTitle("Name this variant");
/* 14746 */     input.setLocationRelativeTo(this);
/* 14747 */     input.setVisible(true);
/* 14748 */     if (input.WasCanceled()) {
/* 14749 */       input.dispose();
/* 14750 */       return;
/*       */     }
/* 14752 */     VariantName = input.GetInput();
/* 14753 */     input.dispose();
/*       */     
/*       */ 
/* 14756 */     if (this.CurMech.GetBaseLoadout().GetName().equals(VariantName)) {
/* 14757 */       Media.Messager(this, "\"" + VariantName + "\" is reserved for the base loadout and cannot be used\nto name this loadout.  Please choose another name.");
/* 14758 */       return;
/*       */     }
/*       */     
/*       */ 
/* 14762 */     ArrayList Loadouts = this.CurMech.GetLoadouts();
/* 14763 */     for (int i = 0; i < Loadouts.size(); i++) {
/* 14764 */       if (((ifMechLoadout)Loadouts.get(i)).GetName().equals(VariantName)) {
/* 14765 */         Media.Messager(this, "Could not rename the loadout because\nthe name given matches an existing loadout.");
/* 14766 */         return;
/*       */       }
/*       */     }
/*       */     
/* 14770 */     this.CurMech.GetLoadout().SetName(VariantName);
/* 14771 */     RefreshOmniVariants();
/*       */   }
/*       */   
/*       */   private void cmbPWHDTypeActionPerformed(ActionEvent evt) {
/* 14775 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetHDArmorType()).equals((String)this.cmbPWHDType.getSelectedItem())) {
/* 14776 */       return;
/*       */     }
/* 14778 */     RecalcPatchworkArmor(0);
/*       */     
/* 14780 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14783 */     RefreshSummary();
/* 14784 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWCTTypeActionPerformed(ActionEvent evt) {
/* 14788 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetCTArmorType()).equals((String)this.cmbPWCTType.getSelectedItem())) {
/* 14789 */       return;
/*       */     }
/* 14791 */     RecalcPatchworkArmor(1);
/*       */     
/* 14793 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14796 */     RefreshSummary();
/* 14797 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWLTTypeActionPerformed(ActionEvent evt)
/*       */   {
/* 14802 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetLTArmorType()).equals((String)this.cmbPWLTType.getSelectedItem())) {
/* 14803 */       return;
/*       */     }
/* 14805 */     RecalcPatchworkArmor(2);
/*       */     
/* 14807 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14810 */     RefreshSummary();
/* 14811 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWRTTypeActionPerformed(ActionEvent evt)
/*       */   {
/* 14816 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetRTArmorType()).equals((String)this.cmbPWRTType.getSelectedItem())) {
/* 14817 */       return;
/*       */     }
/* 14819 */     RecalcPatchworkArmor(3);
/*       */     
/* 14821 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14824 */     RefreshSummary();
/* 14825 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWLATypeActionPerformed(ActionEvent evt)
/*       */   {
/* 14830 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetLAArmorType()).equals((String)this.cmbPWLAType.getSelectedItem())) {
/* 14831 */       return;
/*       */     }
/* 14833 */     RecalcPatchworkArmor(4);
/*       */     
/* 14835 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14838 */     RefreshSummary();
/* 14839 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWRATypeActionPerformed(ActionEvent evt)
/*       */   {
/* 14844 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetRAArmorType()).equals((String)this.cmbPWRAType.getSelectedItem())) {
/* 14845 */       return;
/*       */     }
/* 14847 */     RecalcPatchworkArmor(5);
/*       */     
/* 14849 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14852 */     RefreshSummary();
/* 14853 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWLLTypeActionPerformed(ActionEvent evt)
/*       */   {
/* 14858 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetLLArmorType()).equals((String)this.cmbPWLLType.getSelectedItem())) {
/* 14859 */       return;
/*       */     }
/* 14861 */     RecalcPatchworkArmor(6);
/*       */     
/* 14863 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14866 */     RefreshSummary();
/* 14867 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbPWRLTypeActionPerformed(ActionEvent evt)
/*       */   {
/* 14872 */     if (BuildLookupName((ifState)this.CurMech.GetArmor().GetRLArmorType()).equals((String)this.cmbPWRLType.getSelectedItem())) {
/* 14873 */       return;
/*       */     }
/* 14875 */     RecalcPatchworkArmor(7);
/*       */     
/* 14877 */     FixJJSpinnerModel();
/*       */     
/*       */ 
/* 14880 */     RefreshSummary();
/* 14881 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkHDTurretActionPerformed(ActionEvent evt)
/*       */   {
/* 14886 */     if ((this.CurMech.IsOmnimech()) && 
/* 14887 */       (this.CurMech.GetBaseLoadout().GetHDTurret() == this.CurMech.GetLoadout().GetHDTurret())) {
/* 14888 */       this.chkHDTurret.setSelected(true);
/* 14889 */       return;
/*       */     }
/*       */     
/* 14892 */     if (this.CurMech.GetLoadout().HasHDTurret() == this.chkHDTurret.isSelected()) return;
/* 14893 */     if (this.chkHDTurret.isSelected()) {
/*       */       try {
/* 14895 */         this.CurMech.GetLoadout().SetHDTurret(true, -1);
/*       */       } catch (Exception e) {
/* 14897 */         Media.Messager(this, e.getMessage());
/* 14898 */         this.chkHDTurret.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14902 */         this.CurMech.GetLoadout().SetHDTurret(false, -1);
/*       */       } catch (Exception e) {
/* 14904 */         Media.Messager("Fatal error attempting to remove turret.\nGetting a new 'Mech, sorry...");
/*       */       }
/*       */     }
/* 14907 */     CheckEquipment();
/* 14908 */     RefreshEquipment();
/* 14909 */     RefreshSummary();
/* 14910 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkLTTurretActionPerformed(ActionEvent evt) {
/* 14914 */     if ((this.CurMech.IsOmnimech()) && 
/* 14915 */       (this.CurMech.GetBaseLoadout().GetLTTurret() == this.CurMech.GetLoadout().GetLTTurret())) {
/* 14916 */       this.chkLTTurret.setSelected(true);
/* 14917 */       return;
/*       */     }
/*       */     
/* 14920 */     if (this.CurMech.GetLoadout().HasLTTurret() == this.chkLTTurret.isSelected()) return;
/* 14921 */     if (this.chkLTTurret.isSelected()) {
/*       */       try {
/* 14923 */         this.CurMech.GetLoadout().SetLTTurret(true, -1);
/*       */       } catch (Exception e) {
/* 14925 */         Media.Messager(this, e.getMessage());
/* 14926 */         this.chkLTTurret.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14930 */         this.CurMech.GetLoadout().SetLTTurret(false, -1);
/*       */       } catch (Exception e) {
/* 14932 */         Media.Messager("Fatal error attempting to remove turret.\nGetting a new 'Mech, sorry...");
/*       */       }
/*       */     }
/* 14935 */     CheckEquipment();
/* 14936 */     RefreshEquipment();
/* 14937 */     RefreshSummary();
/* 14938 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkRTTurretActionPerformed(ActionEvent evt) {
/* 14942 */     if ((this.CurMech.IsOmnimech()) && 
/* 14943 */       (this.CurMech.GetBaseLoadout().GetRTTurret() == this.CurMech.GetLoadout().GetRTTurret())) {
/* 14944 */       this.chkRTTurret.setSelected(true);
/* 14945 */       return;
/*       */     }
/*       */     
/* 14948 */     if (this.CurMech.GetLoadout().HasRTTurret() == this.chkRTTurret.isSelected()) return;
/* 14949 */     if (this.chkRTTurret.isSelected()) {
/*       */       try {
/* 14951 */         this.CurMech.GetLoadout().SetRTTurret(true, -1);
/*       */       } catch (Exception e) {
/* 14953 */         Media.Messager(this, e.getMessage());
/* 14954 */         this.chkRTTurret.setSelected(false);
/*       */       }
/*       */     } else {
/*       */       try {
/* 14958 */         this.CurMech.GetLoadout().SetRTTurret(false, -1);
/*       */       } catch (Exception e) {
/* 14960 */         Media.Messager("Fatal error attempting to remove turret.\nGetting a new 'Mech, sorry...");
/*       */       }
/*       */     }
/* 14963 */     CheckEquipment();
/* 14964 */     RefreshEquipment();
/* 14965 */     RefreshSummary();
/* 14966 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void mnuBFBActionPerformed(ActionEvent evt) {
/* 14970 */     String[] call = { "java", "-Xmx256m", "-jar", "bfb.jar" };
/*       */     try {
/* 14972 */       Runtime.getRuntime().exec(call);
/*       */     } catch (Exception ex) {
/* 14974 */       Media.Messager("Error while trying to open BFB\n" + ex.getMessage());
/* 14975 */       System.out.println(ex.getMessage());
/*       */     }
/*       */   }
/*       */   
/*       */   private void jMenuItem1ActionPerformed(ActionEvent evt) {
/* 14980 */     this.Overview.StartNewDocument();
/* 14981 */     this.Capabilities.StartNewDocument();
/* 14982 */     this.History.StartNewDocument();
/* 14983 */     this.Deployment.StartNewDocument();
/* 14984 */     this.Variants.StartNewDocument();
/* 14985 */     this.Notables.StartNewDocument();
/* 14986 */     this.Additional.StartNewDocument();
/* 14987 */     this.txtManufacturer.setText("");
/* 14988 */     this.txtManufacturerLocation.setText("");
/* 14989 */     this.txtEngineManufacturer.setText("");
/* 14990 */     this.txtArmorModel.setText("");
/* 14991 */     this.txtChassisModel.setText("");
/* 14992 */     this.txtJJModel.setText("");
/* 14993 */     this.txtCommSystem.setText("");
/* 14994 */     this.txtTNTSystem.setText("");
/*       */   }
/*       */   
/*       */   private void chkBoostersActionPerformed(ActionEvent evt) {
/* 14998 */     if (this.chkBoosters.isSelected() == this.CurMech.UsingJumpBooster()) return;
/*       */     try {
/* 15000 */       this.CurMech.SetJumpBooster(this.chkBoosters.isSelected());
/*       */     } catch (Exception e) {
/* 15002 */       Media.Messager(this, e.getMessage());
/*       */     }
/* 15004 */     this.spnBoosterMP.setEnabled(this.CurMech.UsingJumpBooster());
/* 15005 */     FixJumpBoosterSpinnerModel();
/*       */     
/*       */ 
/* 15008 */     RefreshEquipment();
/* 15009 */     RefreshSummary();
/* 15010 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void spnBoosterMPStateChanged(ChangeEvent evt)
/*       */   {
/* 15015 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnBoosterMP.getModel();
/* 15016 */     javax.swing.JComponent editor = this.spnBoosterMP.getEditor();
/* 15017 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 15021 */       this.spnBoosterMP.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 15025 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 15026 */         tf.setValue(this.spnBoosterMP.getValue());
/*       */       }
/* 15028 */       return;
/*       */     }
/*       */     
/* 15031 */     this.CurMech.GetJumpBooster().SetBoostMP(n.getNumber().intValue());
/*       */     
/*       */ 
/* 15034 */     FixJumpBoosterSpinnerModel();
/* 15035 */     RefreshSummary();
/* 15036 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void chkBoobyTrapActionPerformed(ActionEvent evt) {
/* 15040 */     if (this.chkBoobyTrap.isSelected() == this.CurMech.GetLoadout().HasBoobyTrap()) return;
/*       */     try {
/* 15042 */       this.CurMech.GetLoadout().SetBoobyTrap(this.chkBoobyTrap.isSelected());
/*       */     } catch (Exception e) {
/* 15044 */       Media.Messager(this, e.getMessage());
/* 15045 */       this.chkBoobyTrap.setSelected(false);
/*       */     }
/*       */     
/*       */ 
/* 15049 */     RefreshEquipment();
/* 15050 */     RefreshSummary();
/* 15051 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */   private void cmbProductionEraActionPerformed(ActionEvent evt) {
/* 15055 */     this.CurMech.SetProductionEra(this.cmbProductionEra.getSelectedIndex());
/*       */   }
/*       */   
/*       */   private void btnAddQuirkActionPerformed(ActionEvent evt) {
/* 15059 */     dlgQuirks qmanage = new dlgQuirks(this, true, this.data, this.quirks);
/* 15060 */     qmanage.setLocationRelativeTo(this);
/* 15061 */     qmanage.setVisible(true);
/* 15062 */     this.tblQuirks.setModel(new list.view.tbQuirks(this.quirks));
/*       */   }
/*       */   
/*       */   private void spnRLArmorStateChanged(ChangeEvent evt)
/*       */   {
/* 15067 */     SpinnerNumberModel n = (SpinnerNumberModel)this.spnRLArmor.getModel();
/* 15068 */     javax.swing.JComponent editor = this.spnRLArmor.getEditor();
/* 15069 */     JFormattedTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
/*       */     
/*       */     try
/*       */     {
/* 15073 */       this.spnRLArmor.commitEdit();
/*       */     }
/*       */     catch (ParseException pe)
/*       */     {
/* 15077 */       if ((editor instanceof JSpinner.DefaultEditor)) {
/* 15078 */         tf.setValue(this.spnRLArmor.getValue());
/*       */       }
/* 15080 */       return;
/*       */     }
/*       */     
/*       */ 
/* 15084 */     MechArmor a = this.CurMech.GetArmor();
/* 15085 */     int curmech = a.GetLocationArmor(7);
/* 15086 */     int curframe = n.getNumber().intValue();
/* 15087 */     if (curframe > curmech) {
/* 15088 */       while (curframe > curmech) {
/* 15089 */         a.IncrementArmor(7);
/* 15090 */         curframe--;
/*       */       }
/*       */     }
/* 15093 */     while (curmech > curframe) {
/* 15094 */       a.DecrementArmor(7);
/* 15095 */       curmech = a.GetLocationArmor(7);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 15100 */     if (this.btnBalanceArmor.isSelected()) {
/* 15101 */       a.SetArmor(6, n.getNumber().intValue());
/* 15102 */       n = (SpinnerNumberModel)this.spnLLArmor.getModel();
/* 15103 */       n.setValue(Integer.valueOf(a.GetLocationArmor(6)));
/*       */     }
/*       */     
/*       */ 
/* 15107 */     RefreshSummary();
/* 15108 */     RefreshInfoPane();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private void cmbPWCLTypeActionPerformed(ActionEvent evt) {}
/*       */   
/*       */ 
/*       */ 
/*       */   private void chkCLCASE2ActionPerformed(ActionEvent evt) {}
/*       */   
/*       */ 
/*       */   private void txtProdYearActionPerformed(ActionEvent evt) {}
/*       */   
/*       */ 
/*       */   private void setViewToolbar(boolean Visible)
/*       */   {
/* 15125 */     this.tlbIconBar.setVisible(Visible);
/* 15126 */     this.Prefs.putBoolean("ViewToolbar", Visible);
/* 15127 */     this.mnuViewToolbar.setState(Visible);
/* 15128 */     if (Visible) {
/* 15129 */       if (getHeight() != 600) setSize(750, 600);
/*       */     }
/* 15131 */     else if (getHeight() != 575) { setSize(750, 575);
/*       */     }
/*       */   }
/*       */   
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
/*       */   private JComboBox cmbPWCLType;
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
/*       */   
/*       */   private JLabel jLabel14;
/*       */   
/*       */   private JLabel jLabel15;
/*       */   
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
/*       */   private JPanel jPanel8;
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
/*       */   private JScrollPane jScrollPane23;
/*       */   private JScrollPane jScrollPane24;
/*       */   private JScrollPane jScrollPane25;
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
/*       */   private JLabel lblBattleMechQuirks;
/*       */   private JLabel lblCLIntPts;
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
/*       */   private JLabel lblPWRLLoc1;
/*       */   private JLabel lblPWRTLoc;
/*       */   private JLabel lblPhysEnhance;
/*       */   private JLabel lblProdYear;
/*       */   private JLabel lblRAArmorHeader;
/*       */   private JLabel lblRAHeader;
/*       */   private JLabel lblRAIntPts;
/*       */   private JLabel lblRLArmorHeader;
/*       */   private JLabel lblRLArmorHeader1;
/*       */   private JLabel lblRLHeader;
/*       */   private JLabel lblRLHeader1;
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
/*       */   private JList lstCLCrits;
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
/*       */   private JList lstSelectedEquipment;
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
/*       */   private JPanel onlLoadoutControls;
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
/*       */   private JPanel pnlBattleforce;
/*       */   private JPanel pnlCLArmorBox;
/*       */   private JPanel pnlCLCrits;
/*       */   private JPanel pnlCTArmorBox;
/*       */   private JPanel pnlCTCrits;
/*       */   private JPanel pnlCTRArmorBox;
/*       */   private JPanel pnlCapabilities;
/*       */   private JPanel pnlCharts;
/*       */   private JPanel pnlChassis;
/*       */   private JPanel pnlControls;
/*       */   private JPanel pnlCriticals;
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
/*       */   private JPanel pnlQuirks;
/*       */   private JPanel pnlRAArmorBox;
/*       */   private JPanel pnlRACrits;
/*       */   private JPanel pnlRLArmorBox;
/*       */   private JPanel pnlRLCrits;
/*       */   private JPanel pnlRTArmorBox;
/*       */   private JPanel pnlRTCrits;
/*       */   private JPanel pnlRTRArmorBox;
/*       */   private JPanel pnlRearArmor;
/*       */   private JPanel pnlSelected;
/*       */   private JPanel pnlSpecials;
/*       */   private JPanel pnlVariants;
/*       */   private JPanel pnlWeaponsManufacturers;
/*       */   private JScrollPane scpQuirkTable;
/*       */   private JScrollPane scpWeaponManufacturers;
/*       */   private JScrollPane scrLACrits;
/*       */   private JScrollPane scrRACrits;
/*       */   private JSpinner spnBoosterMP;
/*       */   private JSpinner spnCLArmor;
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
/*       */   private JTable tblQuirks;
/*       */   private JTable tblWeaponManufacturers;
/*       */   private JTabbedPane tbpFluffEditors;
/*       */   private JTabbedPane tbpMainTabPane;
/*       */   private JTabbedPane tbpWeaponChooser;
/*       */   private JToolBar tlbIconBar;
/*       */   private JTextField txtArmorModel;
/*       */   private JTextField txtChassisModel;
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
/* 15687 */     return this.imageTracker;
/*       */   }
/*       */   
/*       */   public void setUnit(ArrayList v) {
/* 15691 */     setMech((Mech)v.get(0));
/*       */   }
/*       */   
/*       */   public void loadUnitIntoGUI() {
/* 15695 */     LoadMechIntoGUI();
/*       */   }
/*       */   
/*       */   public void showOpenDialog() {
/* 15699 */     this.dOpen.Requestor = 1;
/* 15700 */     this.dOpen.setVisible(true);
/*       */   }
/*       */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\frmMain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */