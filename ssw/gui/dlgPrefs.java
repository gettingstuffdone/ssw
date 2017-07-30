/*      */ package ssw.gui;
/*      */ 
/*      */ import filehandlers.FileCommon;
/*      */ import filehandlers.Media;
/*      */ import java.awt.Color;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Insets;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import java.util.prefs.InvalidPreferencesFormatException;
/*      */ import java.util.prefs.Preferences;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.DefaultComboBoxModel;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JColorChooser;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTextField;
/*      */ 
/*      */ public class dlgPrefs
/*      */   extends JDialog
/*      */ {
/*      */   private Preferences Prefs;
/*      */   private ButtonGroup btgArmorPriority;
/*      */   private ButtonGroup btgExportSort;
/*      */   private ButtonGroup btgHeadArmor;
/*      */   private ButtonGroup btgScreenSize;
/*      */   private JButton btnAmmoNameExportInfo;
/*      */   private JButton btnAmmoNameInfo;
/*      */   private JButton btnCancel;
/*      */   private JButton btnColorArmoredBG;
/*      */   private JButton btnColorArmoredFG;
/*      */   private JButton btnColorEmptyBG;
/*      */   private JButton btnColorEmptyFG;
/*      */   private JButton btnColorHiliteBG;
/*      */   private JButton btnColorHiliteFG;
/*      */   private JButton btnColorLinkedBG;
/*      */   private JButton btnColorLinkedFG;
/*      */   private JButton btnColorLockedBG;
/*      */   private JButton btnColorLockedFG;
/*      */   private JButton btnColorNormalBG;
/*      */   private JButton btnColorNormalFG;
/*      */   private JButton btnDefaultImagePath;
/*      */   private JButton btnExport;
/*      */   private JButton btnHTMLPath;
/*      */   private JButton btnImport;
/*      */   private JButton btnMTFPath;
/*      */   private JButton btnSave;
/*      */   private JButton btnSetDefaults;
/*      */   private JButton btnTXTPath;
/*      */   private JCheckBox chkAutoAddECM;
/*      */   private JCheckBox chkCustomPercents;
/*      */   private JCheckBox chkGroupAmmoAtBottom;
/*      */   private JCheckBox chkHeatAllMP;
/*      */   private JCheckBox chkHeatEquip;
/*      */   private JCheckBox chkHeatJumpMP;
/*      */   private JCheckBox chkHeatOSWeapons;
/*      */   private JCheckBox chkHeatRearWeapons;
/*      */   private JCheckBox chkHeatSystems;
/*      */   private JCheckBox chkHeatUAC;
/*      */   private JCheckBox chkLoadLastMech;
/*      */   private JCheckBox chkMaxNotInt;
/*      */   private JCheckBox chkUpdateStartup;
/*      */   private JComboBox cmbEra;
/*      */   private JComboBox cmbHeatSinks;
/*      */   private JComboBox cmbRulesLevel;
/*      */   private JComboBox cmbTechbase;
/*      */   private JLabel jLabel1;
/*      */   private JLabel jLabel16;
/*      */   
/*      */   public dlgPrefs(Frame parent, boolean modal)
/*      */   {
/*   54 */     super(parent, modal);
/*   55 */     this.Prefs = ((ifMechForm)parent).GetPrefs();
/*   56 */     initComponents();
/*   57 */     SetState();
/*      */   }
/*      */   
/*      */   private JLabel jLabel17;
/*      */   
/*      */   private void SetState()
/*      */   {
/*   61 */     this.cmbRulesLevel.setSelectedItem(this.Prefs.get("NewMech_RulesLevel", "Tournament Legal"));
/*   62 */     this.cmbEra.setSelectedItem(this.Prefs.get("NewMech_Era", "Age of War/Star League"));
/*   63 */     cmbEraActionPerformed(null);
/*   64 */     this.cmbTechbase.setSelectedItem(this.Prefs.get("NewMech_Techbase", "Inner Sphere"));
/*   65 */     cmbTechbaseActionPerformed(null);
/*   66 */     this.cmbHeatSinks.setSelectedItem(this.Prefs.get("NewMech_Heatsinks", "Single Heat Sink"));
/*      */     
/*   68 */     this.txtHTMLPath.setText(this.Prefs.get("HTMLExportPath", "none"));
/*   69 */     this.txtTXTPath.setText(this.Prefs.get("TXTExportPath", "none"));
/*   70 */     this.txtMTFPath.setText(this.Prefs.get("MTFExportPath", "none"));
/*   71 */     this.txtImagePath.setText(this.Prefs.get("DefaultImagePath", ""));
/*   72 */     this.txtAmmoPrintName.setText(this.Prefs.get("AmmoNamePrintFormat", "@%P (%L)"));
/*   73 */     this.txtAmmoExportName.setText(this.Prefs.get("AmmoNameExportFormat", "@%P (%L)"));
/*   74 */     this.chkHeatOSWeapons.setSelected(this.Prefs.getBoolean("HeatExcludeOS", false));
/*   75 */     this.chkHeatRearWeapons.setSelected(this.Prefs.getBoolean("HeatExcludeRear", false));
/*   76 */     this.chkHeatEquip.setSelected(this.Prefs.getBoolean("HeatExcludeEquips", false));
/*   77 */     this.chkHeatSystems.setSelected(this.Prefs.getBoolean("HeatExcludeSystems", false));
/*   78 */     this.chkHeatJumpMP.setSelected(this.Prefs.getBoolean("HeatExcludeJumpMP", false));
/*   79 */     if (this.chkHeatJumpMP.isSelected()) {
/*   80 */       this.chkHeatAllMP.setSelected(this.Prefs.getBoolean("HeatExcludeAllMP", false));
/*   81 */       this.chkHeatAllMP.setEnabled(true);
/*      */     }
/*   83 */     this.chkHeatUAC.setSelected(this.Prefs.getBoolean("HeatACFullRate", false));
/*   84 */     this.chkAutoAddECM.setSelected(this.Prefs.getBoolean("AutoAddECM", true));
/*      */     
/*   86 */     this.chkMaxNotInt.setSelected(this.Prefs.getBoolean("UseMaxArmorInstead", false));
/*   87 */     this.chkCustomPercents.setSelected(this.Prefs.getBoolean("ArmorUseCustomPercent", false));
/*   88 */     chkCustomPercentsActionPerformed(null);
/*   89 */     if (this.Prefs.getBoolean("ArmorMaxHead", true)) {
/*   90 */       this.rdoArmorMaxHead.setSelected(true);
/*      */     } else {
/*   92 */       this.rdoArmorEqualHead.setSelected(true);
/*      */     }
/*   94 */     if (this.Prefs.getBoolean("ExportSortOut", false)) {
/*   95 */       this.rdoExportSortOut.setSelected(true);
/*      */     } else {
/*   97 */       this.rdoExportSortIn.setSelected(true);
/*      */     }
/*   99 */     switch (this.Prefs.getInt("ArmorPriority", 0)) {
/*      */     case 0: 
/*  101 */       this.rdoArmorTorsoPriority.setSelected(true);
/*  102 */       break;
/*      */     case 1: 
/*  104 */       this.rdoArmorArmPriority.setSelected(true);
/*  105 */       break;
/*      */     case 2: 
/*  107 */       this.rdoArmorLegPriority.setSelected(true);
/*      */     }
/*      */     
/*  110 */     this.chkGroupAmmoAtBottom.setSelected(this.Prefs.getBoolean("AmmoGroupAtBottom", true));
/*  111 */     this.chkUpdateStartup.setSelected(this.Prefs.getBoolean("CheckUpdatesAtStartup", false));
/*  112 */     this.chkLoadLastMech.setSelected(this.Prefs.getBoolean("LoadLastMech", false));
/*      */     
/*  114 */     this.lblColorEmpty.setForeground(new Color(this.Prefs.getInt("ColorEmptyItemFG", -16777216)));
/*  115 */     this.lblColorEmpty.setBackground(new Color(this.Prefs.getInt("ColorEmptyItemBG", -6684775)));
/*  116 */     this.lblColorNormal.setForeground(new Color(this.Prefs.getInt("ColorNormalItemFG", -16777216)));
/*  117 */     this.lblColorNormal.setBackground(new Color(this.Prefs.getInt("ColorNormalItemBG", -10027009)));
/*  118 */     this.lblColorArmored.setForeground(new Color(this.Prefs.getInt("ColorArmoredItemFG", -1)));
/*  119 */     this.lblColorArmored.setBackground(new Color(this.Prefs.getInt("ColorArmoredItemBG", -6710887)));
/*  120 */     this.lblColorLinked.setForeground(new Color(this.Prefs.getInt("ColorLinkedItemFG", -16777216)));
/*  121 */     this.lblColorLinked.setBackground(new Color(this.Prefs.getInt("ColorLinkedItemBG", -3618616)));
/*  122 */     this.lblColorLocked.setForeground(new Color(this.Prefs.getInt("ColorLockedItemFG", -3342337)));
/*  123 */     this.lblColorLocked.setBackground(new Color(this.Prefs.getInt("ColorLockedItemBG", -16777216)));
/*  124 */     this.lblColorHilite.setForeground(new Color(this.Prefs.getInt("ColorHiLiteItemFG", -16777216)));
/*  125 */     this.lblColorHilite.setBackground(new Color(this.Prefs.getInt("ColorHiLiteItemBG", -52)));
/*  126 */     switch (this.Prefs.getInt("SSWScreenSize", 0))
/*      */     {
/*      */     case 1: 
/*  128 */       this.rdoWidescreen.setSelected(true);
/*  129 */       break;
/*      */     default: 
/*  131 */       this.rdoNormalSize.setSelected(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private JLabel jLabel18;
/*      */   private JLabel jLabel19;
/*      */   private JLabel jLabel2;
/*      */   private JLabel jLabel20;
/*      */   
/*      */   private void SaveState()
/*      */   {
/*  137 */     this.Prefs.put("NewMech_RulesLevel", (String)this.cmbRulesLevel.getSelectedItem());
/*  138 */     this.Prefs.put("NewMech_Era", (String)this.cmbEra.getSelectedItem());
/*  139 */     this.Prefs.put("NewMech_Techbase", (String)this.cmbTechbase.getSelectedItem());
/*  140 */     this.Prefs.put("NewMech_Heatsinks", (String)this.cmbHeatSinks.getSelectedItem());
/*  141 */     this.Prefs.put("HTMLExportPath", this.txtHTMLPath.getText());
/*  142 */     this.Prefs.put("TXTExportPath", this.txtTXTPath.getText());
/*  143 */     this.Prefs.put("MTFExportPath", this.txtMTFPath.getText());
/*  144 */     this.Prefs.put("DefaultImagePath", this.txtImagePath.getText());
/*  145 */     this.Prefs.put("AmmoNamePrintFormat", this.txtAmmoPrintName.getText());
/*  146 */     this.Prefs.put("AmmoNameExportFormat", this.txtAmmoExportName.getText());
/*      */     
/*  148 */     this.Prefs.putBoolean("HeatExcludeOS", this.chkHeatOSWeapons.isSelected());
/*  149 */     this.Prefs.putBoolean("HeatExcludeRear", this.chkHeatRearWeapons.isSelected());
/*  150 */     this.Prefs.putBoolean("HeatExcludeEquips", this.chkHeatEquip.isSelected());
/*  151 */     this.Prefs.putBoolean("HeatExcludeSystems", this.chkHeatSystems.isSelected());
/*  152 */     this.Prefs.putBoolean("HeatExcludeJumpMP", this.chkHeatJumpMP.isSelected());
/*  153 */     this.Prefs.putBoolean("HeatExcludeAllMP", this.chkHeatAllMP.isSelected());
/*  154 */     this.Prefs.putBoolean("HeatACFullRate", this.chkHeatUAC.isSelected());
/*  155 */     this.Prefs.putBoolean("UseMaxArmorInstead", this.chkMaxNotInt.isSelected());
/*  156 */     this.Prefs.putBoolean("ArmorUseCustomPercent", this.chkCustomPercents.isSelected());
/*  157 */     this.Prefs.putBoolean("ArmorMaxHead", this.rdoArmorMaxHead.isSelected());
/*  158 */     this.Prefs.putBoolean("ExportSortOut", this.rdoExportSortOut.isSelected());
/*  159 */     this.Prefs.putBoolean("AmmoGroupAtBottom", this.chkGroupAmmoAtBottom.isSelected());
/*  160 */     this.Prefs.putBoolean("CheckUpdatesAtStartup", this.chkUpdateStartup.isSelected());
/*  161 */     this.Prefs.putBoolean("LoadLastMech", this.chkLoadLastMech.isSelected());
/*  162 */     this.Prefs.putBoolean("AutoAddECM", this.chkAutoAddECM.isSelected());
/*      */     
/*  164 */     if (this.chkCustomPercents.isSelected()) {
/*  165 */       this.Prefs.putInt("ArmorCTRPercent", Integer.parseInt(this.txtCTRArmor.getText()));
/*  166 */       this.Prefs.putInt("ArmorSTRPercent", Integer.parseInt(this.txtSTRArmor.getText()));
/*      */     } else {
/*  168 */       this.Prefs.putInt("ArmorCTRPercent", 25);
/*  169 */       this.Prefs.putInt("ArmorSTRPercent", 25);
/*      */     }
/*  171 */     if (this.rdoArmorTorsoPriority.isSelected()) {
/*  172 */       this.Prefs.putInt("ArmorPriority", 0);
/*  173 */     } else if (this.rdoArmorArmPriority.isSelected()) {
/*  174 */       this.Prefs.putInt("ArmorPriority", 1);
/*      */     } else {
/*  176 */       this.Prefs.putInt("ArmorPriority", 2);
/*      */     }
/*      */     
/*  179 */     this.Prefs.putInt("ColorEmptyItemFG", this.lblColorEmpty.getForeground().getRGB());
/*  180 */     this.Prefs.putInt("ColorEmptyItemBG", this.lblColorEmpty.getBackground().getRGB());
/*  181 */     this.Prefs.putInt("ColorNormalItemFG", this.lblColorNormal.getForeground().getRGB());
/*  182 */     this.Prefs.putInt("ColorNormalItemBG", this.lblColorNormal.getBackground().getRGB());
/*  183 */     this.Prefs.putInt("ColorArmoredItemFG", this.lblColorArmored.getForeground().getRGB());
/*  184 */     this.Prefs.putInt("ColorArmoredItemBG", this.lblColorArmored.getBackground().getRGB());
/*  185 */     this.Prefs.putInt("ColorLinkedItemFG", this.lblColorLinked.getForeground().getRGB());
/*  186 */     this.Prefs.putInt("ColorLinkedItemBG", this.lblColorLinked.getBackground().getRGB());
/*  187 */     this.Prefs.putInt("ColorLockedItemFG", this.lblColorLocked.getForeground().getRGB());
/*  188 */     this.Prefs.putInt("ColorLockedItemBG", this.lblColorLocked.getBackground().getRGB());
/*  189 */     this.Prefs.putInt("ColorHiLiteItemFG", this.lblColorHilite.getForeground().getRGB());
/*  190 */     this.Prefs.putInt("ColorHiLiteItemBG", this.lblColorHilite.getBackground().getRGB());
/*      */     
/*  192 */     if (this.rdoNormalSize.isSelected()) {
/*  193 */       this.Prefs.putInt("SSWScreenSize", 0);
/*  194 */     } else if (this.rdoWidescreen.isSelected()) {
/*  195 */       this.Prefs.putInt("SSWScreenSize", 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private JLabel jLabel22;
/*      */   private JLabel jLabel23;
/*      */   private JLabel jLabel24;
/*      */   private JLabel jLabel3;
/*      */   private JLabel jLabel4;
/*      */   private JLabel jLabel5;
/*      */   private JLabel jLabel7;
/*      */   private JLabel jLabel8;
/*      */   private JLabel jLabel9;
/*      */   private JPanel jPanel1;
/*      */   
/*      */   private void SetDefaults()
/*      */   {
/*  200 */     this.Prefs.put("NewMech_RulesLevel", "Tournament Legal");
/*  201 */     this.Prefs.put("NewMech_Era", "Age of War/Star League");
/*  202 */     this.Prefs.put("NewMech_Techbase", "Inner Sphere");
/*  203 */     this.Prefs.put("NewMech_Heatsinks", "Single Heat Sink");
/*  204 */     this.Prefs.put("HTMLExportPath", "none");
/*  205 */     this.Prefs.put("TXTExportPath", "none");
/*  206 */     this.Prefs.put("MTFExportPath", "none");
/*  207 */     this.Prefs.put("AmmoNamePrintFormat", "@%P (%L)");
/*  208 */     this.Prefs.put("AmmoNameExportFormat", "@%P (%L)");
/*      */     
/*  210 */     this.Prefs.putBoolean("HeatExcludeOS", false);
/*  211 */     this.Prefs.putBoolean("HeatExcludeRear", false);
/*  212 */     this.Prefs.putBoolean("HeatExcludeEquips", false);
/*  213 */     this.Prefs.putBoolean("HeatExcludeSystems", false);
/*  214 */     this.Prefs.putBoolean("HeatExcludeJumpMP", false);
/*  215 */     this.Prefs.putBoolean("HeatExcludeAllMP", false);
/*  216 */     this.Prefs.putBoolean("HeatACFullRate", false);
/*  217 */     this.Prefs.putBoolean("UseMaxArmorInstead", false);
/*  218 */     this.Prefs.putBoolean("ArmorUseCustomPercent", false);
/*  219 */     this.Prefs.putBoolean("ArmorMaxHead", true);
/*  220 */     this.Prefs.putBoolean("ExportSortOut", false);
/*  221 */     this.Prefs.putBoolean("AmmoGroupAtBottom", true);
/*  222 */     this.Prefs.putBoolean("CheckUpdatesAtStartup", false);
/*  223 */     this.Prefs.putBoolean("LoadLastMech", false);
/*  224 */     this.Prefs.putBoolean("AutoAddECM", true);
/*      */     
/*  226 */     this.Prefs.putInt("ArmorCTRPercent", 25);
/*  227 */     this.Prefs.putInt("ArmorSTRPercent", 25);
/*  228 */     this.Prefs.putInt("ArmorPriority", 0);
/*      */     
/*  230 */     this.Prefs.putInt("ColorEmptyItemFG", -16777216);
/*  231 */     this.Prefs.putInt("ColorEmptyItemBG", -6684775);
/*  232 */     this.Prefs.putInt("ColorNormalItemFG", -16777216);
/*  233 */     this.Prefs.putInt("ColorNormalItemBG", -10027009);
/*  234 */     this.Prefs.putInt("ColorArmoredItemFG", -1);
/*  235 */     this.Prefs.putInt("ColorArmoredItemBG", -6710887);
/*  236 */     this.Prefs.putInt("ColorLinkedItemFG", -16777216);
/*  237 */     this.Prefs.putInt("ColorLinkedItemBG", -3618616);
/*  238 */     this.Prefs.putInt("ColorLockedItemFG", -3342337);
/*  239 */     this.Prefs.putInt("ColorLockedItemBG", -16777216);
/*  240 */     this.Prefs.putInt("ColorHiLiteItemFG", -16777216);
/*  241 */     this.Prefs.putInt("ColorHiLiteItemBG", -52);
/*      */     
/*  243 */     this.Prefs.putInt("SSWScreenSize", 0);
/*      */     
/*  245 */     SetState();
/*      */   }
/*      */   
/*      */   private JPanel jPanel10;
/*      */   private JPanel jPanel11;
/*      */   private JPanel jPanel12;
/*      */   private JPanel jPanel13;
/*      */   private JPanel jPanel14;
/*      */   private JPanel jPanel2;
/*      */   
/*      */   private void initComponents()
/*      */   {
/*  258 */     this.btgHeadArmor = new ButtonGroup();
/*  259 */     this.btgArmorPriority = new ButtonGroup();
/*  260 */     this.btgExportSort = new ButtonGroup();
/*  261 */     this.btgScreenSize = new ButtonGroup();
/*  262 */     this.jTabbedPane1 = new JTabbedPane();
/*  263 */     this.pnlConstruction = new JPanel();
/*  264 */     this.jPanel1 = new JPanel();
/*  265 */     this.cmbRulesLevel = new JComboBox();
/*  266 */     this.cmbEra = new JComboBox();
/*  267 */     this.cmbTechbase = new JComboBox();
/*  268 */     this.cmbHeatSinks = new JComboBox();
/*  269 */     this.jLabel1 = new JLabel();
/*  270 */     this.jLabel2 = new JLabel();
/*  271 */     this.jLabel3 = new JLabel();
/*  272 */     this.jLabel4 = new JLabel();
/*  273 */     this.jPanel2 = new JPanel();
/*  274 */     this.chkCustomPercents = new JCheckBox();
/*  275 */     this.lblCTRArmor = new JLabel();
/*  276 */     this.lblSTRArmor = new JLabel();
/*  277 */     this.txtCTRArmor = new JTextField();
/*  278 */     this.txtSTRArmor = new JTextField();
/*  279 */     this.jLabel7 = new JLabel();
/*  280 */     this.rdoArmorTorsoPriority = new JRadioButton();
/*  281 */     this.rdoArmorMaxHead = new JRadioButton();
/*  282 */     this.rdoArmorEqualHead = new JRadioButton();
/*  283 */     this.rdoArmorArmPriority = new JRadioButton();
/*  284 */     this.rdoArmorLegPriority = new JRadioButton();
/*  285 */     this.jLabel8 = new JLabel();
/*  286 */     this.chkMaxNotInt = new JCheckBox();
/*  287 */     this.jPanel8 = new JPanel();
/*  288 */     this.chkHeatOSWeapons = new JCheckBox();
/*  289 */     this.chkHeatRearWeapons = new JCheckBox();
/*  290 */     this.chkHeatEquip = new JCheckBox();
/*  291 */     this.chkHeatSystems = new JCheckBox();
/*  292 */     this.chkHeatJumpMP = new JCheckBox();
/*  293 */     this.chkHeatAllMP = new JCheckBox();
/*  294 */     this.chkHeatUAC = new JCheckBox();
/*  295 */     this.jLabel19 = new JLabel();
/*  296 */     this.jPanel13 = new JPanel();
/*  297 */     this.chkAutoAddECM = new JCheckBox();
/*  298 */     this.jPanel4 = new JPanel();
/*  299 */     this.jPanel5 = new JPanel();
/*  300 */     this.lblColorEmpty = new JLabel();
/*  301 */     this.lblColorNormal = new JLabel();
/*  302 */     this.lblColorArmored = new JLabel();
/*  303 */     this.lblColorLinked = new JLabel();
/*  304 */     this.lblColorLocked = new JLabel();
/*  305 */     this.lblColorHilite = new JLabel();
/*  306 */     this.btnColorEmptyFG = new JButton();
/*  307 */     this.btnColorEmptyBG = new JButton();
/*  308 */     this.btnColorNormalFG = new JButton();
/*  309 */     this.btnColorNormalBG = new JButton();
/*  310 */     this.btnColorArmoredFG = new JButton();
/*  311 */     this.btnColorArmoredBG = new JButton();
/*  312 */     this.btnColorLinkedFG = new JButton();
/*  313 */     this.btnColorLinkedBG = new JButton();
/*  314 */     this.btnColorLockedFG = new JButton();
/*  315 */     this.btnColorLockedBG = new JButton();
/*  316 */     this.btnColorHiliteFG = new JButton();
/*  317 */     this.btnColorHiliteBG = new JButton();
/*  318 */     this.jPanel6 = new JPanel();
/*  319 */     this.jLabel16 = new JLabel();
/*  320 */     this.rdoExportSortOut = new JRadioButton();
/*  321 */     this.rdoExportSortIn = new JRadioButton();
/*  322 */     this.chkGroupAmmoAtBottom = new JCheckBox();
/*  323 */     this.jLabel17 = new JLabel();
/*  324 */     this.jLabel18 = new JLabel();
/*  325 */     this.jLabel20 = new JLabel();
/*  326 */     this.btnAmmoNameExportInfo = new JButton();
/*  327 */     this.txtAmmoExportName = new JTextField();
/*  328 */     this.jPanel3 = new JPanel();
/*  329 */     this.jLabel9 = new JLabel();
/*  330 */     this.btnAmmoNameInfo = new JButton();
/*  331 */     this.txtAmmoPrintName = new JTextField();
/*  332 */     this.jPanel7 = new JPanel();
/*  333 */     this.jPanel10 = new JPanel();
/*  334 */     this.txtHTMLPath = new JTextField();
/*  335 */     this.txtTXTPath = new JTextField();
/*  336 */     this.txtMTFPath = new JTextField();
/*  337 */     this.btnHTMLPath = new JButton();
/*  338 */     this.btnTXTPath = new JButton();
/*  339 */     this.btnMTFPath = new JButton();
/*  340 */     this.chkLoadLastMech = new JCheckBox();
/*  341 */     this.jLabel22 = new JLabel();
/*  342 */     this.jLabel23 = new JLabel();
/*  343 */     this.jLabel24 = new JLabel();
/*  344 */     this.jLabel5 = new JLabel();
/*  345 */     this.txtImagePath = new JTextField();
/*  346 */     this.btnDefaultImagePath = new JButton();
/*  347 */     this.jPanel11 = new JPanel();
/*  348 */     this.chkUpdateStartup = new JCheckBox();
/*  349 */     this.jPanel14 = new JPanel();
/*  350 */     this.rdoNormalSize = new JRadioButton();
/*  351 */     this.rdoWidescreen = new JRadioButton();
/*  352 */     this.lblScreenSizeNotice = new JLabel();
/*  353 */     this.jPanel9 = new JPanel();
/*  354 */     this.btnSave = new JButton();
/*  355 */     this.btnCancel = new JButton();
/*  356 */     this.btnSetDefaults = new JButton();
/*  357 */     this.jPanel12 = new JPanel();
/*  358 */     this.btnExport = new JButton();
/*  359 */     this.btnImport = new JButton();
/*      */     
/*  361 */     setDefaultCloseOperation(2);
/*  362 */     getContentPane().setLayout(new GridBagLayout());
/*      */     
/*  364 */     this.jTabbedPane1.setMinimumSize(new Dimension(101, 83));
/*      */     
/*  366 */     this.pnlConstruction.setLayout(new GridBagLayout());
/*      */     
/*  368 */     this.jPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "New 'Mech Defaults"));
/*  369 */     this.jPanel1.setLayout(new GridBagLayout());
/*      */     
/*  371 */     this.cmbRulesLevel.setModel(new DefaultComboBoxModel(new String[] { "Introductory", "Tournament Legal", "Advanced Rules", "Experimental Tech", "Era Specific" }));
/*  372 */     this.cmbRulesLevel.setSelectedIndex(1);
/*  373 */     this.cmbRulesLevel.setMaximumSize(new Dimension(150, 20));
/*  374 */     this.cmbRulesLevel.setMinimumSize(new Dimension(150, 20));
/*  375 */     this.cmbRulesLevel.setPreferredSize(new Dimension(150, 20));
/*  376 */     this.cmbRulesLevel.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  378 */         dlgPrefs.this.cmbRulesLevelActionPerformed(evt);
/*      */       }
/*  380 */     });
/*  381 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  382 */     gridBagConstraints.gridx = 1;
/*  383 */     gridBagConstraints.gridy = 0;
/*  384 */     this.jPanel1.add(this.cmbRulesLevel, gridBagConstraints);
/*      */     
/*  386 */     this.cmbEra.setModel(new DefaultComboBoxModel(new String[] { "Age of War/Star League", "Succession Wars", "Clan Invasion", "Dark Ages", "All Eras (non-canon)" }));
/*  387 */     this.cmbEra.setMaximumSize(new Dimension(150, 20));
/*  388 */     this.cmbEra.setMinimumSize(new Dimension(150, 20));
/*  389 */     this.cmbEra.setPreferredSize(new Dimension(150, 20));
/*  390 */     this.cmbEra.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  392 */         dlgPrefs.this.cmbEraActionPerformed(evt);
/*      */       }
/*  394 */     });
/*  395 */     gridBagConstraints = new GridBagConstraints();
/*  396 */     gridBagConstraints.gridx = 1;
/*  397 */     gridBagConstraints.gridy = 1;
/*  398 */     this.jPanel1.add(this.cmbEra, gridBagConstraints);
/*      */     
/*  400 */     this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan", "Mixed" }));
/*  401 */     this.cmbTechbase.setMaximumSize(new Dimension(150, 20));
/*  402 */     this.cmbTechbase.setMinimumSize(new Dimension(150, 20));
/*  403 */     this.cmbTechbase.setPreferredSize(new Dimension(150, 20));
/*  404 */     this.cmbTechbase.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  406 */         dlgPrefs.this.cmbTechbaseActionPerformed(evt);
/*      */       }
/*  408 */     });
/*  409 */     gridBagConstraints = new GridBagConstraints();
/*  410 */     gridBagConstraints.gridx = 1;
/*  411 */     gridBagConstraints.gridy = 2;
/*  412 */     this.jPanel1.add(this.cmbTechbase, gridBagConstraints);
/*      */     
/*  414 */     this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sinks", "Double Heat Sinks", "Laser Heat Sinks", "Compact Heat Sinks" }));
/*  415 */     this.cmbHeatSinks.setMaximumSize(new Dimension(150, 20));
/*  416 */     this.cmbHeatSinks.setMinimumSize(new Dimension(150, 20));
/*  417 */     this.cmbHeatSinks.setPreferredSize(new Dimension(150, 20));
/*  418 */     gridBagConstraints = new GridBagConstraints();
/*  419 */     gridBagConstraints.gridx = 1;
/*  420 */     gridBagConstraints.gridy = 3;
/*  421 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/*  422 */     this.jPanel1.add(this.cmbHeatSinks, gridBagConstraints);
/*      */     
/*  424 */     this.jLabel1.setText("Rules Level:");
/*  425 */     gridBagConstraints = new GridBagConstraints();
/*  426 */     gridBagConstraints.gridx = 0;
/*  427 */     gridBagConstraints.gridy = 0;
/*  428 */     gridBagConstraints.anchor = 13;
/*  429 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  430 */     this.jPanel1.add(this.jLabel1, gridBagConstraints);
/*      */     
/*  432 */     this.jLabel2.setText("Era:");
/*  433 */     gridBagConstraints = new GridBagConstraints();
/*  434 */     gridBagConstraints.gridx = 0;
/*  435 */     gridBagConstraints.gridy = 1;
/*  436 */     gridBagConstraints.anchor = 13;
/*  437 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  438 */     this.jPanel1.add(this.jLabel2, gridBagConstraints);
/*      */     
/*  440 */     this.jLabel3.setText("Techbase:");
/*  441 */     gridBagConstraints = new GridBagConstraints();
/*  442 */     gridBagConstraints.gridx = 0;
/*  443 */     gridBagConstraints.gridy = 2;
/*  444 */     gridBagConstraints.anchor = 13;
/*  445 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  446 */     this.jPanel1.add(this.jLabel3, gridBagConstraints);
/*      */     
/*  448 */     this.jLabel4.setText("Heat Sinks:");
/*  449 */     gridBagConstraints = new GridBagConstraints();
/*  450 */     gridBagConstraints.gridx = 0;
/*  451 */     gridBagConstraints.gridy = 3;
/*  452 */     gridBagConstraints.anchor = 13;
/*  453 */     gridBagConstraints.insets = new Insets(4, 0, 0, 4);
/*  454 */     this.jPanel1.add(this.jLabel4, gridBagConstraints);
/*      */     
/*  456 */     gridBagConstraints = new GridBagConstraints();
/*  457 */     gridBagConstraints.fill = 1;
/*  458 */     this.pnlConstruction.add(this.jPanel1, gridBagConstraints);
/*      */     
/*  460 */     this.jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Armor Options"));
/*  461 */     this.jPanel2.setLayout(new GridBagLayout());
/*      */     
/*  463 */     this.chkCustomPercents.setText("Use Custom Distribution Percentages");
/*  464 */     this.chkCustomPercents.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  466 */         dlgPrefs.this.chkCustomPercentsActionPerformed(evt);
/*      */       }
/*  468 */     });
/*  469 */     gridBagConstraints = new GridBagConstraints();
/*  470 */     gridBagConstraints.gridx = 0;
/*  471 */     gridBagConstraints.gridy = 1;
/*  472 */     gridBagConstraints.gridwidth = 2;
/*  473 */     gridBagConstraints.anchor = 17;
/*  474 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/*  475 */     this.jPanel2.add(this.chkCustomPercents, gridBagConstraints);
/*      */     
/*  477 */     this.lblCTRArmor.setText("Percentage CTR to CT:");
/*  478 */     this.lblCTRArmor.setEnabled(false);
/*  479 */     gridBagConstraints = new GridBagConstraints();
/*  480 */     gridBagConstraints.gridx = 0;
/*  481 */     gridBagConstraints.gridy = 2;
/*  482 */     gridBagConstraints.anchor = 13;
/*  483 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/*  484 */     this.jPanel2.add(this.lblCTRArmor, gridBagConstraints);
/*      */     
/*  486 */     this.lblSTRArmor.setText("Percentage STR to ST:");
/*  487 */     this.lblSTRArmor.setEnabled(false);
/*  488 */     gridBagConstraints = new GridBagConstraints();
/*  489 */     gridBagConstraints.gridx = 0;
/*  490 */     gridBagConstraints.gridy = 3;
/*  491 */     gridBagConstraints.anchor = 13;
/*  492 */     this.jPanel2.add(this.lblSTRArmor, gridBagConstraints);
/*      */     
/*  494 */     this.txtCTRArmor.setHorizontalAlignment(0);
/*  495 */     this.txtCTRArmor.setText("25");
/*  496 */     this.txtCTRArmor.setEnabled(false);
/*  497 */     this.txtCTRArmor.setMaximumSize(new Dimension(45, 20));
/*  498 */     this.txtCTRArmor.setMinimumSize(new Dimension(45, 20));
/*  499 */     this.txtCTRArmor.setPreferredSize(new Dimension(45, 20));
/*  500 */     gridBagConstraints = new GridBagConstraints();
/*  501 */     gridBagConstraints.gridx = 1;
/*  502 */     gridBagConstraints.gridy = 2;
/*  503 */     gridBagConstraints.anchor = 17;
/*  504 */     gridBagConstraints.insets = new Insets(4, 4, 0, 0);
/*  505 */     this.jPanel2.add(this.txtCTRArmor, gridBagConstraints);
/*      */     
/*  507 */     this.txtSTRArmor.setHorizontalAlignment(0);
/*  508 */     this.txtSTRArmor.setText("25");
/*  509 */     this.txtSTRArmor.setEnabled(false);
/*  510 */     this.txtSTRArmor.setMaximumSize(new Dimension(45, 20));
/*  511 */     this.txtSTRArmor.setMinimumSize(new Dimension(45, 20));
/*  512 */     this.txtSTRArmor.setPreferredSize(new Dimension(45, 20));
/*  513 */     gridBagConstraints = new GridBagConstraints();
/*  514 */     gridBagConstraints.gridx = 1;
/*  515 */     gridBagConstraints.gridy = 3;
/*  516 */     gridBagConstraints.anchor = 17;
/*  517 */     gridBagConstraints.insets = new Insets(0, 4, 0, 0);
/*  518 */     this.jPanel2.add(this.txtSTRArmor, gridBagConstraints);
/*      */     
/*  520 */     this.jLabel7.setText("When automatically allocating armor:");
/*  521 */     gridBagConstraints = new GridBagConstraints();
/*  522 */     gridBagConstraints.gridx = 0;
/*  523 */     gridBagConstraints.gridy = 4;
/*  524 */     gridBagConstraints.gridwidth = 2;
/*  525 */     gridBagConstraints.anchor = 17;
/*  526 */     gridBagConstraints.insets = new Insets(4, 0, 4, 0);
/*  527 */     this.jPanel2.add(this.jLabel7, gridBagConstraints);
/*      */     
/*  529 */     this.btgArmorPriority.add(this.rdoArmorTorsoPriority);
/*  530 */     this.rdoArmorTorsoPriority.setSelected(true);
/*  531 */     this.rdoArmorTorsoPriority.setText("Prioritize Torso Armor");
/*  532 */     gridBagConstraints = new GridBagConstraints();
/*  533 */     gridBagConstraints.gridx = 0;
/*  534 */     gridBagConstraints.gridy = 8;
/*  535 */     gridBagConstraints.gridwidth = 2;
/*  536 */     gridBagConstraints.anchor = 17;
/*  537 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  538 */     this.jPanel2.add(this.rdoArmorTorsoPriority, gridBagConstraints);
/*      */     
/*  540 */     this.btgHeadArmor.add(this.rdoArmorMaxHead);
/*  541 */     this.rdoArmorMaxHead.setSelected(true);
/*  542 */     this.rdoArmorMaxHead.setText("Maximize Head Armor");
/*  543 */     gridBagConstraints = new GridBagConstraints();
/*  544 */     gridBagConstraints.gridx = 0;
/*  545 */     gridBagConstraints.gridy = 5;
/*  546 */     gridBagConstraints.gridwidth = 2;
/*  547 */     gridBagConstraints.anchor = 17;
/*  548 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  549 */     this.jPanel2.add(this.rdoArmorMaxHead, gridBagConstraints);
/*      */     
/*  551 */     this.btgHeadArmor.add(this.rdoArmorEqualHead);
/*  552 */     this.rdoArmorEqualHead.setText("Allocate Head armor equally");
/*  553 */     gridBagConstraints = new GridBagConstraints();
/*  554 */     gridBagConstraints.gridx = 0;
/*  555 */     gridBagConstraints.gridy = 6;
/*  556 */     gridBagConstraints.gridwidth = 2;
/*  557 */     gridBagConstraints.anchor = 17;
/*  558 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  559 */     this.jPanel2.add(this.rdoArmorEqualHead, gridBagConstraints);
/*      */     
/*  561 */     this.btgArmorPriority.add(this.rdoArmorArmPriority);
/*  562 */     this.rdoArmorArmPriority.setText("Prioritize Arm Armor");
/*  563 */     gridBagConstraints = new GridBagConstraints();
/*  564 */     gridBagConstraints.gridx = 0;
/*  565 */     gridBagConstraints.gridy = 9;
/*  566 */     gridBagConstraints.gridwidth = 2;
/*  567 */     gridBagConstraints.anchor = 17;
/*  568 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  569 */     this.jPanel2.add(this.rdoArmorArmPriority, gridBagConstraints);
/*      */     
/*  571 */     this.btgArmorPriority.add(this.rdoArmorLegPriority);
/*  572 */     this.rdoArmorLegPriority.setText("Prioritize Leg Armor");
/*  573 */     gridBagConstraints = new GridBagConstraints();
/*  574 */     gridBagConstraints.gridx = 0;
/*  575 */     gridBagConstraints.gridy = 10;
/*  576 */     gridBagConstraints.gridwidth = 2;
/*  577 */     gridBagConstraints.anchor = 17;
/*  578 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  579 */     this.jPanel2.add(this.rdoArmorLegPriority, gridBagConstraints);
/*      */     
/*  581 */     this.jLabel8.setText("and...");
/*  582 */     gridBagConstraints = new GridBagConstraints();
/*  583 */     gridBagConstraints.gridx = 0;
/*  584 */     gridBagConstraints.gridy = 7;
/*  585 */     gridBagConstraints.gridwidth = 2;
/*  586 */     gridBagConstraints.anchor = 17;
/*  587 */     gridBagConstraints.insets = new Insets(4, 0, 4, 0);
/*  588 */     this.jPanel2.add(this.jLabel8, gridBagConstraints);
/*      */     
/*  590 */     this.chkMaxNotInt.setText("<html>Show maximum armor instead of<br />internal points on Armor tab.</html>");
/*  591 */     gridBagConstraints = new GridBagConstraints();
/*  592 */     gridBagConstraints.gridwidth = 2;
/*  593 */     gridBagConstraints.fill = 2;
/*  594 */     this.jPanel2.add(this.chkMaxNotInt, gridBagConstraints);
/*      */     
/*  596 */     gridBagConstraints = new GridBagConstraints();
/*  597 */     gridBagConstraints.gridx = 1;
/*  598 */     gridBagConstraints.gridy = 0;
/*  599 */     gridBagConstraints.gridheight = 2;
/*  600 */     gridBagConstraints.anchor = 11;
/*  601 */     this.pnlConstruction.add(this.jPanel2, gridBagConstraints);
/*      */     
/*  603 */     this.jPanel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Heat Options"));
/*  604 */     this.jPanel8.setLayout(new GridBagLayout());
/*      */     
/*  606 */     this.chkHeatOSWeapons.setText("Exclude OS weapon heat");
/*  607 */     gridBagConstraints = new GridBagConstraints();
/*  608 */     gridBagConstraints.gridx = 0;
/*  609 */     gridBagConstraints.gridy = 1;
/*  610 */     gridBagConstraints.anchor = 17;
/*  611 */     this.jPanel8.add(this.chkHeatOSWeapons, gridBagConstraints);
/*      */     
/*  613 */     this.chkHeatRearWeapons.setText("Exclude rear-facing weapon heat");
/*  614 */     gridBagConstraints = new GridBagConstraints();
/*  615 */     gridBagConstraints.gridx = 0;
/*  616 */     gridBagConstraints.gridy = 2;
/*  617 */     gridBagConstraints.anchor = 17;
/*  618 */     this.jPanel8.add(this.chkHeatRearWeapons, gridBagConstraints);
/*      */     
/*  620 */     this.chkHeatEquip.setText("Exclude equipment heat (AMS, etc...)");
/*  621 */     gridBagConstraints = new GridBagConstraints();
/*  622 */     gridBagConstraints.gridx = 0;
/*  623 */     gridBagConstraints.gridy = 3;
/*  624 */     gridBagConstraints.anchor = 17;
/*  625 */     this.jPanel8.add(this.chkHeatEquip, gridBagConstraints);
/*      */     
/*  627 */     this.chkHeatSystems.setText("Exclude armor and systems heat");
/*  628 */     gridBagConstraints = new GridBagConstraints();
/*  629 */     gridBagConstraints.gridx = 0;
/*  630 */     gridBagConstraints.gridy = 4;
/*  631 */     gridBagConstraints.anchor = 17;
/*  632 */     this.jPanel8.add(this.chkHeatSystems, gridBagConstraints);
/*      */     
/*  634 */     this.chkHeatJumpMP.setText("Exclude jumping MP heat");
/*  635 */     this.chkHeatJumpMP.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  637 */         dlgPrefs.this.chkHeatJumpMPActionPerformed(evt);
/*      */       }
/*  639 */     });
/*  640 */     gridBagConstraints = new GridBagConstraints();
/*  641 */     gridBagConstraints.gridx = 0;
/*  642 */     gridBagConstraints.gridy = 5;
/*  643 */     gridBagConstraints.anchor = 17;
/*  644 */     this.jPanel8.add(this.chkHeatJumpMP, gridBagConstraints);
/*      */     
/*  646 */     this.chkHeatAllMP.setText("Exclude ALL movement heat");
/*  647 */     this.chkHeatAllMP.setEnabled(false);
/*  648 */     gridBagConstraints = new GridBagConstraints();
/*  649 */     gridBagConstraints.gridx = 0;
/*  650 */     gridBagConstraints.gridy = 6;
/*  651 */     gridBagConstraints.anchor = 17;
/*  652 */     gridBagConstraints.insets = new Insets(0, 16, 0, 0);
/*  653 */     this.jPanel8.add(this.chkHeatAllMP, gridBagConstraints);
/*      */     
/*  655 */     this.chkHeatUAC.setText("Ultra and Rotary ACs fire at full rate");
/*  656 */     gridBagConstraints = new GridBagConstraints();
/*  657 */     gridBagConstraints.gridx = 0;
/*  658 */     gridBagConstraints.gridy = 7;
/*  659 */     gridBagConstraints.anchor = 17;
/*  660 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/*  661 */     this.jPanel8.add(this.chkHeatUAC, gridBagConstraints);
/*      */     
/*  663 */     this.jLabel19.setText("Non-BV Heat Calculations:");
/*  664 */     gridBagConstraints = new GridBagConstraints();
/*  665 */     gridBagConstraints.anchor = 17;
/*  666 */     this.jPanel8.add(this.jLabel19, gridBagConstraints);
/*      */     
/*  668 */     gridBagConstraints = new GridBagConstraints();
/*  669 */     gridBagConstraints.gridx = 0;
/*  670 */     gridBagConstraints.gridy = 1;
/*  671 */     gridBagConstraints.gridheight = 2;
/*  672 */     gridBagConstraints.anchor = 11;
/*  673 */     this.pnlConstruction.add(this.jPanel8, gridBagConstraints);
/*      */     
/*  675 */     this.jPanel13.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Usability Options"));
/*  676 */     this.jPanel13.setLayout(new BoxLayout(this.jPanel13, 2));
/*      */     
/*  678 */     this.chkAutoAddECM.setText("Auto-add ECM suite if needed");
/*  679 */     this.jPanel13.add(this.chkAutoAddECM);
/*      */     
/*  681 */     gridBagConstraints = new GridBagConstraints();
/*  682 */     gridBagConstraints.gridx = 1;
/*  683 */     gridBagConstraints.gridy = 2;
/*  684 */     gridBagConstraints.fill = 2;
/*  685 */     this.pnlConstruction.add(this.jPanel13, gridBagConstraints);
/*      */     
/*  687 */     this.jTabbedPane1.addTab("Construction", this.pnlConstruction);
/*      */     
/*  689 */     this.jPanel4.setLayout(new GridBagLayout());
/*      */     
/*  691 */     this.jPanel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Allocation Colors"));
/*  692 */     this.jPanel5.setLayout(new GridBagLayout());
/*      */     
/*  694 */     this.lblColorEmpty.setHorizontalAlignment(0);
/*  695 */     this.lblColorEmpty.setText("Empty Location");
/*  696 */     this.lblColorEmpty.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  697 */     this.lblColorEmpty.setMaximumSize(new Dimension(135, 19));
/*  698 */     this.lblColorEmpty.setMinimumSize(new Dimension(135, 19));
/*  699 */     this.lblColorEmpty.setOpaque(true);
/*  700 */     this.lblColorEmpty.setPreferredSize(new Dimension(135, 19));
/*  701 */     gridBagConstraints = new GridBagConstraints();
/*  702 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  703 */     this.jPanel5.add(this.lblColorEmpty, gridBagConstraints);
/*      */     
/*  705 */     this.lblColorNormal.setHorizontalAlignment(0);
/*  706 */     this.lblColorNormal.setText("Normal Item");
/*  707 */     this.lblColorNormal.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  708 */     this.lblColorNormal.setMaximumSize(new Dimension(135, 19));
/*  709 */     this.lblColorNormal.setMinimumSize(new Dimension(135, 19));
/*  710 */     this.lblColorNormal.setOpaque(true);
/*  711 */     this.lblColorNormal.setPreferredSize(new Dimension(135, 19));
/*  712 */     gridBagConstraints = new GridBagConstraints();
/*  713 */     gridBagConstraints.gridx = 0;
/*  714 */     gridBagConstraints.gridy = 1;
/*  715 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  716 */     this.jPanel5.add(this.lblColorNormal, gridBagConstraints);
/*      */     
/*  718 */     this.lblColorArmored.setHorizontalAlignment(0);
/*  719 */     this.lblColorArmored.setText("Armored Item");
/*  720 */     this.lblColorArmored.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  721 */     this.lblColorArmored.setMaximumSize(new Dimension(135, 19));
/*  722 */     this.lblColorArmored.setMinimumSize(new Dimension(135, 19));
/*  723 */     this.lblColorArmored.setOpaque(true);
/*  724 */     this.lblColorArmored.setPreferredSize(new Dimension(135, 19));
/*  725 */     gridBagConstraints = new GridBagConstraints();
/*  726 */     gridBagConstraints.gridx = 0;
/*  727 */     gridBagConstraints.gridy = 2;
/*  728 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  729 */     this.jPanel5.add(this.lblColorArmored, gridBagConstraints);
/*      */     
/*  731 */     this.lblColorLinked.setHorizontalAlignment(0);
/*  732 */     this.lblColorLinked.setText("Linked in Location");
/*  733 */     this.lblColorLinked.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  734 */     this.lblColorLinked.setMaximumSize(new Dimension(135, 19));
/*  735 */     this.lblColorLinked.setMinimumSize(new Dimension(135, 19));
/*  736 */     this.lblColorLinked.setOpaque(true);
/*  737 */     this.lblColorLinked.setPreferredSize(new Dimension(135, 19));
/*  738 */     gridBagConstraints = new GridBagConstraints();
/*  739 */     gridBagConstraints.gridx = 0;
/*  740 */     gridBagConstraints.gridy = 3;
/*  741 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  742 */     this.jPanel5.add(this.lblColorLinked, gridBagConstraints);
/*      */     
/*  744 */     this.lblColorLocked.setHorizontalAlignment(0);
/*  745 */     this.lblColorLocked.setText("Locked in Location");
/*  746 */     this.lblColorLocked.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  747 */     this.lblColorLocked.setMaximumSize(new Dimension(135, 19));
/*  748 */     this.lblColorLocked.setMinimumSize(new Dimension(135, 19));
/*  749 */     this.lblColorLocked.setOpaque(true);
/*  750 */     this.lblColorLocked.setPreferredSize(new Dimension(135, 19));
/*  751 */     gridBagConstraints = new GridBagConstraints();
/*  752 */     gridBagConstraints.gridx = 0;
/*  753 */     gridBagConstraints.gridy = 4;
/*  754 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  755 */     this.jPanel5.add(this.lblColorLocked, gridBagConstraints);
/*      */     
/*  757 */     this.lblColorHilite.setHorizontalAlignment(0);
/*  758 */     this.lblColorHilite.setText("Allocation HiLite");
/*  759 */     this.lblColorHilite.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  760 */     this.lblColorHilite.setMaximumSize(new Dimension(135, 19));
/*  761 */     this.lblColorHilite.setMinimumSize(new Dimension(135, 19));
/*  762 */     this.lblColorHilite.setOpaque(true);
/*  763 */     this.lblColorHilite.setPreferredSize(new Dimension(135, 19));
/*  764 */     gridBagConstraints = new GridBagConstraints();
/*  765 */     gridBagConstraints.gridx = 0;
/*  766 */     gridBagConstraints.gridy = 5;
/*  767 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/*  768 */     this.jPanel5.add(this.lblColorHilite, gridBagConstraints);
/*      */     
/*  770 */     this.btnColorEmptyFG.setText("Fore");
/*  771 */     this.btnColorEmptyFG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  773 */         dlgPrefs.this.btnColorEmptyFGActionPerformed(evt);
/*      */       }
/*  775 */     });
/*  776 */     this.jPanel5.add(this.btnColorEmptyFG, new GridBagConstraints());
/*      */     
/*  778 */     this.btnColorEmptyBG.setText("Back");
/*  779 */     this.btnColorEmptyBG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  781 */         dlgPrefs.this.btnColorEmptyBGActionPerformed(evt);
/*      */       }
/*  783 */     });
/*  784 */     this.jPanel5.add(this.btnColorEmptyBG, new GridBagConstraints());
/*      */     
/*  786 */     this.btnColorNormalFG.setText("Fore");
/*  787 */     this.btnColorNormalFG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  789 */         dlgPrefs.this.btnColorNormalFGActionPerformed(evt);
/*      */       }
/*  791 */     });
/*  792 */     gridBagConstraints = new GridBagConstraints();
/*  793 */     gridBagConstraints.gridx = 1;
/*  794 */     gridBagConstraints.gridy = 1;
/*  795 */     this.jPanel5.add(this.btnColorNormalFG, gridBagConstraints);
/*      */     
/*  797 */     this.btnColorNormalBG.setText("Back");
/*  798 */     this.btnColorNormalBG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  800 */         dlgPrefs.this.btnColorNormalBGActionPerformed(evt);
/*      */       }
/*  802 */     });
/*  803 */     gridBagConstraints = new GridBagConstraints();
/*  804 */     gridBagConstraints.gridx = 2;
/*  805 */     gridBagConstraints.gridy = 1;
/*  806 */     this.jPanel5.add(this.btnColorNormalBG, gridBagConstraints);
/*      */     
/*  808 */     this.btnColorArmoredFG.setText("Fore");
/*  809 */     this.btnColorArmoredFG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  811 */         dlgPrefs.this.btnColorArmoredFGActionPerformed(evt);
/*      */       }
/*  813 */     });
/*  814 */     gridBagConstraints = new GridBagConstraints();
/*  815 */     gridBagConstraints.gridx = 1;
/*  816 */     gridBagConstraints.gridy = 2;
/*  817 */     this.jPanel5.add(this.btnColorArmoredFG, gridBagConstraints);
/*      */     
/*  819 */     this.btnColorArmoredBG.setText("Back");
/*  820 */     this.btnColorArmoredBG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  822 */         dlgPrefs.this.btnColorArmoredBGActionPerformed(evt);
/*      */       }
/*  824 */     });
/*  825 */     gridBagConstraints = new GridBagConstraints();
/*  826 */     gridBagConstraints.gridx = 2;
/*  827 */     gridBagConstraints.gridy = 2;
/*  828 */     this.jPanel5.add(this.btnColorArmoredBG, gridBagConstraints);
/*      */     
/*  830 */     this.btnColorLinkedFG.setText("Fore");
/*  831 */     this.btnColorLinkedFG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  833 */         dlgPrefs.this.btnColorLinkedFGActionPerformed(evt);
/*      */       }
/*  835 */     });
/*  836 */     gridBagConstraints = new GridBagConstraints();
/*  837 */     gridBagConstraints.gridx = 1;
/*  838 */     gridBagConstraints.gridy = 3;
/*  839 */     this.jPanel5.add(this.btnColorLinkedFG, gridBagConstraints);
/*      */     
/*  841 */     this.btnColorLinkedBG.setText("Back");
/*  842 */     this.btnColorLinkedBG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  844 */         dlgPrefs.this.btnColorLinkedBGActionPerformed(evt);
/*      */       }
/*  846 */     });
/*  847 */     gridBagConstraints = new GridBagConstraints();
/*  848 */     gridBagConstraints.gridx = 2;
/*  849 */     gridBagConstraints.gridy = 3;
/*  850 */     this.jPanel5.add(this.btnColorLinkedBG, gridBagConstraints);
/*      */     
/*  852 */     this.btnColorLockedFG.setText("Fore");
/*  853 */     this.btnColorLockedFG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  855 */         dlgPrefs.this.btnColorLockedFGActionPerformed(evt);
/*      */       }
/*  857 */     });
/*  858 */     gridBagConstraints = new GridBagConstraints();
/*  859 */     gridBagConstraints.gridx = 1;
/*  860 */     gridBagConstraints.gridy = 4;
/*  861 */     this.jPanel5.add(this.btnColorLockedFG, gridBagConstraints);
/*      */     
/*  863 */     this.btnColorLockedBG.setText("Back");
/*  864 */     this.btnColorLockedBG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  866 */         dlgPrefs.this.btnColorLockedBGActionPerformed(evt);
/*      */       }
/*  868 */     });
/*  869 */     gridBagConstraints = new GridBagConstraints();
/*  870 */     gridBagConstraints.gridx = 2;
/*  871 */     gridBagConstraints.gridy = 4;
/*  872 */     this.jPanel5.add(this.btnColorLockedBG, gridBagConstraints);
/*      */     
/*  874 */     this.btnColorHiliteFG.setText("Fore");
/*  875 */     this.btnColorHiliteFG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  877 */         dlgPrefs.this.btnColorHiliteFGActionPerformed(evt);
/*      */       }
/*  879 */     });
/*  880 */     gridBagConstraints = new GridBagConstraints();
/*  881 */     gridBagConstraints.gridx = 1;
/*  882 */     gridBagConstraints.gridy = 5;
/*  883 */     this.jPanel5.add(this.btnColorHiliteFG, gridBagConstraints);
/*      */     
/*  885 */     this.btnColorHiliteBG.setText("Back");
/*  886 */     this.btnColorHiliteBG.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  888 */         dlgPrefs.this.btnColorHiliteBGActionPerformed(evt);
/*      */       }
/*  890 */     });
/*  891 */     gridBagConstraints = new GridBagConstraints();
/*  892 */     gridBagConstraints.gridx = 2;
/*  893 */     gridBagConstraints.gridy = 5;
/*  894 */     this.jPanel5.add(this.btnColorHiliteBG, gridBagConstraints);
/*      */     
/*  896 */     gridBagConstraints = new GridBagConstraints();
/*  897 */     gridBagConstraints.fill = 2;
/*  898 */     gridBagConstraints.anchor = 11;
/*  899 */     this.jPanel4.add(this.jPanel5, gridBagConstraints);
/*      */     
/*  901 */     this.jPanel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Export Options"));
/*  902 */     this.jPanel6.setLayout(new GridBagLayout());
/*      */     
/*  904 */     this.jLabel16.setText("<html>When sorting the equipment stat<br />blocks for export to TXT or HTML:</html>");
/*  905 */     gridBagConstraints = new GridBagConstraints();
/*  906 */     gridBagConstraints.gridwidth = 2;
/*  907 */     gridBagConstraints.anchor = 17;
/*  908 */     gridBagConstraints.insets = new Insets(0, 0, 2, 0);
/*  909 */     this.jPanel6.add(this.jLabel16, gridBagConstraints);
/*      */     
/*  911 */     this.btgExportSort.add(this.rdoExportSortOut);
/*  912 */     this.rdoExportSortOut.setText("Sort from the center of the mech out");
/*  913 */     gridBagConstraints = new GridBagConstraints();
/*  914 */     gridBagConstraints.gridx = 0;
/*  915 */     gridBagConstraints.gridy = 1;
/*  916 */     gridBagConstraints.gridwidth = 2;
/*  917 */     gridBagConstraints.anchor = 17;
/*  918 */     this.jPanel6.add(this.rdoExportSortOut, gridBagConstraints);
/*      */     
/*  920 */     this.btgExportSort.add(this.rdoExportSortIn);
/*  921 */     this.rdoExportSortIn.setSelected(true);
/*  922 */     this.rdoExportSortIn.setText("Sort from the arms in");
/*  923 */     gridBagConstraints = new GridBagConstraints();
/*  924 */     gridBagConstraints.gridx = 0;
/*  925 */     gridBagConstraints.gridy = 3;
/*  926 */     gridBagConstraints.gridwidth = 2;
/*  927 */     gridBagConstraints.anchor = 17;
/*  928 */     this.jPanel6.add(this.rdoExportSortIn, gridBagConstraints);
/*      */     
/*  930 */     this.chkGroupAmmoAtBottom.setSelected(true);
/*  931 */     this.chkGroupAmmoAtBottom.setText("Group Ammo at the bottom of the stat block");
/*  932 */     gridBagConstraints = new GridBagConstraints();
/*  933 */     gridBagConstraints.gridx = 0;
/*  934 */     gridBagConstraints.gridy = 5;
/*  935 */     gridBagConstraints.gridwidth = 2;
/*  936 */     gridBagConstraints.anchor = 17;
/*  937 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/*  938 */     this.jPanel6.add(this.chkGroupAmmoAtBottom, gridBagConstraints);
/*      */     
/*  940 */     this.jLabel17.setText("(HD, CT, RT, LT, RA, LA, RL, LL)");
/*  941 */     gridBagConstraints = new GridBagConstraints();
/*  942 */     gridBagConstraints.gridx = 0;
/*  943 */     gridBagConstraints.gridy = 2;
/*  944 */     gridBagConstraints.gridwidth = 2;
/*  945 */     gridBagConstraints.anchor = 17;
/*  946 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  947 */     this.jPanel6.add(this.jLabel17, gridBagConstraints);
/*      */     
/*  949 */     this.jLabel18.setText("(RA, LA, RT, LT, CT, HD, RL, LL)");
/*  950 */     gridBagConstraints = new GridBagConstraints();
/*  951 */     gridBagConstraints.gridx = 0;
/*  952 */     gridBagConstraints.gridy = 4;
/*  953 */     gridBagConstraints.gridwidth = 2;
/*  954 */     gridBagConstraints.anchor = 17;
/*  955 */     gridBagConstraints.insets = new Insets(0, 8, 0, 0);
/*  956 */     this.jPanel6.add(this.jLabel18, gridBagConstraints);
/*      */     
/*  958 */     this.jLabel20.setText("Export Ammo Format");
/*  959 */     gridBagConstraints = new GridBagConstraints();
/*  960 */     gridBagConstraints.gridx = 0;
/*  961 */     gridBagConstraints.gridy = 6;
/*  962 */     gridBagConstraints.gridwidth = 2;
/*  963 */     gridBagConstraints.anchor = 17;
/*  964 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/*  965 */     this.jPanel6.add(this.jLabel20, gridBagConstraints);
/*      */     
/*  967 */     this.btnAmmoNameExportInfo.setText("?");
/*  968 */     this.btnAmmoNameExportInfo.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  970 */         dlgPrefs.this.btnAmmoNameExportInfoActionPerformed(evt);
/*      */       }
/*  972 */     });
/*  973 */     gridBagConstraints = new GridBagConstraints();
/*  974 */     gridBagConstraints.gridx = 0;
/*  975 */     gridBagConstraints.gridy = 7;
/*  976 */     gridBagConstraints.anchor = 13;
/*  977 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/*  978 */     this.jPanel6.add(this.btnAmmoNameExportInfo, gridBagConstraints);
/*      */     
/*  980 */     this.txtAmmoExportName.setText("jTextField4");
/*  981 */     this.txtAmmoExportName.setMaximumSize(new Dimension(120, 20));
/*  982 */     this.txtAmmoExportName.setMinimumSize(new Dimension(120, 20));
/*  983 */     this.txtAmmoExportName.setPreferredSize(new Dimension(120, 20));
/*  984 */     gridBagConstraints = new GridBagConstraints();
/*  985 */     gridBagConstraints.gridx = 1;
/*  986 */     gridBagConstraints.gridy = 7;
/*  987 */     gridBagConstraints.anchor = 17;
/*  988 */     gridBagConstraints.insets = new Insets(4, 4, 0, 0);
/*  989 */     this.jPanel6.add(this.txtAmmoExportName, gridBagConstraints);
/*      */     
/*  991 */     gridBagConstraints = new GridBagConstraints();
/*  992 */     gridBagConstraints.gridx = 0;
/*  993 */     gridBagConstraints.gridy = 1;
/*  994 */     this.jPanel4.add(this.jPanel6, gridBagConstraints);
/*      */     
/*  996 */     this.jPanel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Printing Options"));
/*  997 */     this.jPanel3.setLayout(new GridBagLayout());
/*      */     
/*  999 */     this.jLabel9.setText("Ammo Print Format");
/* 1000 */     gridBagConstraints = new GridBagConstraints();
/* 1001 */     gridBagConstraints.gridwidth = 2;
/* 1002 */     gridBagConstraints.anchor = 17;
/* 1003 */     this.jPanel3.add(this.jLabel9, gridBagConstraints);
/*      */     
/* 1005 */     this.btnAmmoNameInfo.setText("?");
/* 1006 */     this.btnAmmoNameInfo.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1008 */         dlgPrefs.this.btnAmmoNameInfoActionPerformed(evt);
/*      */       }
/* 1010 */     });
/* 1011 */     gridBagConstraints = new GridBagConstraints();
/* 1012 */     gridBagConstraints.gridx = 0;
/* 1013 */     gridBagConstraints.gridy = 1;
/* 1014 */     gridBagConstraints.anchor = 13;
/* 1015 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 1016 */     this.jPanel3.add(this.btnAmmoNameInfo, gridBagConstraints);
/*      */     
/* 1018 */     this.txtAmmoPrintName.setText("jTextField3");
/* 1019 */     this.txtAmmoPrintName.setMaximumSize(new Dimension(120, 20));
/* 1020 */     this.txtAmmoPrintName.setMinimumSize(new Dimension(120, 20));
/* 1021 */     this.txtAmmoPrintName.setPreferredSize(new Dimension(120, 20));
/* 1022 */     gridBagConstraints = new GridBagConstraints();
/* 1023 */     gridBagConstraints.gridx = 1;
/* 1024 */     gridBagConstraints.gridy = 1;
/* 1025 */     gridBagConstraints.anchor = 17;
/* 1026 */     gridBagConstraints.insets = new Insets(4, 4, 0, 0);
/* 1027 */     this.jPanel3.add(this.txtAmmoPrintName, gridBagConstraints);
/*      */     
/* 1029 */     gridBagConstraints = new GridBagConstraints();
/* 1030 */     gridBagConstraints.gridx = 1;
/* 1031 */     gridBagConstraints.gridy = 0;
/* 1032 */     gridBagConstraints.fill = 2;
/* 1033 */     gridBagConstraints.anchor = 11;
/* 1034 */     this.jPanel4.add(this.jPanel3, gridBagConstraints);
/*      */     
/* 1036 */     this.jTabbedPane1.addTab("Functionality", this.jPanel4);
/*      */     
/* 1038 */     this.jPanel7.setPreferredSize(new Dimension(9, 381));
/* 1039 */     this.jPanel7.setLayout(new GridBagLayout());
/*      */     
/* 1041 */     this.jPanel10.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Program Paths"));
/* 1042 */     this.jPanel10.setLayout(new GridBagLayout());
/*      */     
/* 1044 */     this.txtHTMLPath.setText("jTextField6");
/* 1045 */     this.txtHTMLPath.setPreferredSize(new Dimension(200, 20));
/* 1046 */     gridBagConstraints = new GridBagConstraints();
/* 1047 */     gridBagConstraints.gridx = 0;
/* 1048 */     gridBagConstraints.gridy = 2;
/* 1049 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/* 1050 */     this.jPanel10.add(this.txtHTMLPath, gridBagConstraints);
/*      */     
/* 1052 */     this.txtTXTPath.setText("jTextField7");
/* 1053 */     this.txtTXTPath.setPreferredSize(new Dimension(200, 20));
/* 1054 */     gridBagConstraints = new GridBagConstraints();
/* 1055 */     gridBagConstraints.gridx = 0;
/* 1056 */     gridBagConstraints.gridy = 4;
/* 1057 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/* 1058 */     this.jPanel10.add(this.txtTXTPath, gridBagConstraints);
/*      */     
/* 1060 */     this.txtMTFPath.setText("jTextField8");
/* 1061 */     this.txtMTFPath.setPreferredSize(new Dimension(200, 20));
/* 1062 */     gridBagConstraints = new GridBagConstraints();
/* 1063 */     gridBagConstraints.gridx = 0;
/* 1064 */     gridBagConstraints.gridy = 6;
/* 1065 */     gridBagConstraints.insets = new Insets(0, 0, 0, 4);
/* 1066 */     this.jPanel10.add(this.txtMTFPath, gridBagConstraints);
/*      */     
/* 1068 */     this.btnHTMLPath.setText("...");
/* 1069 */     this.btnHTMLPath.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1071 */         dlgPrefs.this.btnHTMLPathActionPerformed(evt);
/*      */       }
/* 1073 */     });
/* 1074 */     gridBagConstraints = new GridBagConstraints();
/* 1075 */     gridBagConstraints.gridx = 1;
/* 1076 */     gridBagConstraints.gridy = 2;
/* 1077 */     this.jPanel10.add(this.btnHTMLPath, gridBagConstraints);
/*      */     
/* 1079 */     this.btnTXTPath.setText("...");
/* 1080 */     this.btnTXTPath.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1082 */         dlgPrefs.this.btnTXTPathActionPerformed(evt);
/*      */       }
/* 1084 */     });
/* 1085 */     gridBagConstraints = new GridBagConstraints();
/* 1086 */     gridBagConstraints.gridx = 1;
/* 1087 */     gridBagConstraints.gridy = 4;
/* 1088 */     this.jPanel10.add(this.btnTXTPath, gridBagConstraints);
/*      */     
/* 1090 */     this.btnMTFPath.setText("...");
/* 1091 */     this.btnMTFPath.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1093 */         dlgPrefs.this.btnMTFPathActionPerformed(evt);
/*      */       }
/* 1095 */     });
/* 1096 */     gridBagConstraints = new GridBagConstraints();
/* 1097 */     gridBagConstraints.gridx = 1;
/* 1098 */     gridBagConstraints.gridy = 6;
/* 1099 */     this.jPanel10.add(this.btnMTFPath, gridBagConstraints);
/*      */     
/* 1101 */     this.chkLoadLastMech.setText("Load last 'Mech on startup");
/* 1102 */     gridBagConstraints = new GridBagConstraints();
/* 1103 */     gridBagConstraints.gridx = 0;
/* 1104 */     gridBagConstraints.gridy = 0;
/* 1105 */     gridBagConstraints.gridwidth = 2;
/* 1106 */     gridBagConstraints.anchor = 17;
/* 1107 */     this.jPanel10.add(this.chkLoadLastMech, gridBagConstraints);
/*      */     
/* 1109 */     this.jLabel22.setText("Default HTML export path");
/* 1110 */     gridBagConstraints = new GridBagConstraints();
/* 1111 */     gridBagConstraints.gridx = 0;
/* 1112 */     gridBagConstraints.gridy = 1;
/* 1113 */     gridBagConstraints.gridwidth = 2;
/* 1114 */     gridBagConstraints.anchor = 17;
/* 1115 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 1116 */     this.jPanel10.add(this.jLabel22, gridBagConstraints);
/*      */     
/* 1118 */     this.jLabel23.setText("Default TXT export path");
/* 1119 */     gridBagConstraints = new GridBagConstraints();
/* 1120 */     gridBagConstraints.gridx = 0;
/* 1121 */     gridBagConstraints.gridy = 3;
/* 1122 */     gridBagConstraints.gridwidth = 2;
/* 1123 */     gridBagConstraints.anchor = 17;
/* 1124 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 1125 */     this.jPanel10.add(this.jLabel23, gridBagConstraints);
/*      */     
/* 1127 */     this.jLabel24.setText("Default MTF export path");
/* 1128 */     gridBagConstraints = new GridBagConstraints();
/* 1129 */     gridBagConstraints.gridx = 0;
/* 1130 */     gridBagConstraints.gridy = 5;
/* 1131 */     gridBagConstraints.gridwidth = 2;
/* 1132 */     gridBagConstraints.anchor = 17;
/* 1133 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 1134 */     this.jPanel10.add(this.jLabel24, gridBagConstraints);
/*      */     
/* 1136 */     this.jLabel5.setText("Default Image path");
/* 1137 */     gridBagConstraints = new GridBagConstraints();
/* 1138 */     gridBagConstraints.gridx = 0;
/* 1139 */     gridBagConstraints.gridy = 7;
/* 1140 */     gridBagConstraints.fill = 2;
/* 1141 */     gridBagConstraints.anchor = 17;
/* 1142 */     this.jPanel10.add(this.jLabel5, gridBagConstraints);
/*      */     
/* 1144 */     this.txtImagePath.setText("jTextField1");
/* 1145 */     this.txtImagePath.setPreferredSize(new Dimension(200, 20));
/* 1146 */     gridBagConstraints = new GridBagConstraints();
/* 1147 */     gridBagConstraints.gridx = 0;
/* 1148 */     gridBagConstraints.gridy = 8;
/* 1149 */     gridBagConstraints.anchor = 17;
/* 1150 */     this.jPanel10.add(this.txtImagePath, gridBagConstraints);
/*      */     
/* 1152 */     this.btnDefaultImagePath.setText("...");
/* 1153 */     this.btnDefaultImagePath.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1155 */         dlgPrefs.this.btnDefaultImagePathActionPerformed(evt);
/*      */       }
/* 1157 */     });
/* 1158 */     gridBagConstraints = new GridBagConstraints();
/* 1159 */     gridBagConstraints.gridx = 1;
/* 1160 */     gridBagConstraints.gridy = 8;
/* 1161 */     this.jPanel10.add(this.btnDefaultImagePath, gridBagConstraints);
/*      */     
/* 1163 */     gridBagConstraints = new GridBagConstraints();
/* 1164 */     gridBagConstraints.gridx = 0;
/* 1165 */     gridBagConstraints.gridy = 1;
/* 1166 */     this.jPanel7.add(this.jPanel10, gridBagConstraints);
/*      */     
/* 1168 */     this.jPanel11.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Updater Options"));
/* 1169 */     this.jPanel11.setLayout(new GridBagLayout());
/*      */     
/* 1171 */     this.chkUpdateStartup.setText("Check for updates on startup");
/* 1172 */     this.chkUpdateStartup.setEnabled(false);
/* 1173 */     this.jPanel11.add(this.chkUpdateStartup, new GridBagConstraints());
/*      */     
/* 1175 */     gridBagConstraints = new GridBagConstraints();
/* 1176 */     gridBagConstraints.gridx = 0;
/* 1177 */     gridBagConstraints.gridy = 0;
/* 1178 */     gridBagConstraints.fill = 2;
/* 1179 */     this.jPanel7.add(this.jPanel11, gridBagConstraints);
/*      */     
/* 1181 */     this.jPanel14.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Program Screen Size"));
/* 1182 */     this.jPanel14.setLayout(new GridBagLayout());
/*      */     
/* 1184 */     this.btgScreenSize.add(this.rdoNormalSize);
/* 1185 */     this.rdoNormalSize.setSelected(true);
/* 1186 */     this.rdoNormalSize.setText("Normal Size (750 wide)");
/* 1187 */     gridBagConstraints = new GridBagConstraints();
/* 1188 */     gridBagConstraints.anchor = 17;
/* 1189 */     this.jPanel14.add(this.rdoNormalSize, gridBagConstraints);
/*      */     
/* 1191 */     this.btgScreenSize.add(this.rdoWidescreen);
/* 1192 */     this.rdoWidescreen.setText("Widescreen (1280 wide)");
/* 1193 */     gridBagConstraints = new GridBagConstraints();
/* 1194 */     gridBagConstraints.gridx = 0;
/* 1195 */     gridBagConstraints.gridy = 1;
/* 1196 */     gridBagConstraints.anchor = 17;
/* 1197 */     this.jPanel14.add(this.rdoWidescreen, gridBagConstraints);
/*      */     
/* 1199 */     this.lblScreenSizeNotice.setText("Change requires restart of SSW.");
/* 1200 */     gridBagConstraints = new GridBagConstraints();
/* 1201 */     gridBagConstraints.gridx = 0;
/* 1202 */     gridBagConstraints.gridy = 2;
/* 1203 */     gridBagConstraints.anchor = 17;
/* 1204 */     this.jPanel14.add(this.lblScreenSizeNotice, gridBagConstraints);
/*      */     
/* 1206 */     gridBagConstraints = new GridBagConstraints();
/* 1207 */     gridBagConstraints.gridx = 0;
/* 1208 */     gridBagConstraints.gridy = 2;
/* 1209 */     gridBagConstraints.fill = 1;
/* 1210 */     this.jPanel7.add(this.jPanel14, gridBagConstraints);
/*      */     
/* 1212 */     this.jTabbedPane1.addTab("Program", this.jPanel7);
/*      */     
/* 1214 */     gridBagConstraints = new GridBagConstraints();
/* 1215 */     gridBagConstraints.gridwidth = 3;
/* 1216 */     getContentPane().add(this.jTabbedPane1, gridBagConstraints);
/*      */     
/* 1218 */     this.btnSave.setText("Save");
/* 1219 */     this.btnSave.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1221 */         dlgPrefs.this.btnSaveActionPerformed(evt);
/*      */       }
/* 1223 */     });
/* 1224 */     this.jPanel9.add(this.btnSave);
/*      */     
/* 1226 */     this.btnCancel.setText("Cancel");
/* 1227 */     this.btnCancel.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1229 */         dlgPrefs.this.btnCancelActionPerformed(evt);
/*      */       }
/* 1231 */     });
/* 1232 */     this.jPanel9.add(this.btnCancel);
/*      */     
/* 1234 */     gridBagConstraints = new GridBagConstraints();
/* 1235 */     gridBagConstraints.gridx = 2;
/* 1236 */     gridBagConstraints.gridy = 1;
/* 1237 */     gridBagConstraints.anchor = 13;
/* 1238 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 1239 */     getContentPane().add(this.jPanel9, gridBagConstraints);
/*      */     
/* 1241 */     this.btnSetDefaults.setText("Set Defaults");
/* 1242 */     this.btnSetDefaults.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1244 */         dlgPrefs.this.btnSetDefaultsActionPerformed(evt);
/*      */       }
/* 1246 */     });
/* 1247 */     gridBagConstraints = new GridBagConstraints();
/* 1248 */     gridBagConstraints.gridx = 0;
/* 1249 */     gridBagConstraints.gridy = 1;
/* 1250 */     gridBagConstraints.anchor = 17;
/* 1251 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 1252 */     getContentPane().add(this.btnSetDefaults, gridBagConstraints);
/*      */     
/* 1254 */     this.btnExport.setText("Export");
/* 1255 */     this.btnExport.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1257 */         dlgPrefs.this.btnExportActionPerformed(evt);
/*      */       }
/* 1259 */     });
/* 1260 */     this.jPanel12.add(this.btnExport);
/*      */     
/* 1262 */     this.btnImport.setText("Import");
/* 1263 */     this.btnImport.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1265 */         dlgPrefs.this.btnImportActionPerformed(evt);
/*      */       }
/* 1267 */     });
/* 1268 */     this.jPanel12.add(this.btnImport);
/*      */     
/* 1270 */     gridBagConstraints = new GridBagConstraints();
/* 1271 */     gridBagConstraints.gridx = 1;
/* 1272 */     gridBagConstraints.gridy = 1;
/* 1273 */     gridBagConstraints.insets = new Insets(4, 8, 0, 6);
/* 1274 */     getContentPane().add(this.jPanel12, gridBagConstraints);
/*      */     
/* 1276 */     pack();
/*      */   }
/*      */   
/*      */   private JPanel jPanel3;
/*      */   
/*      */   private void btnSetDefaultsActionPerformed(ActionEvent evt)
/*      */   {
/* 1280 */     SetDefaults();
/*      */   }
/*      */   
/*      */   private JPanel jPanel4;
/*      */   
/*      */   private void btnSaveActionPerformed(ActionEvent evt)
/*      */   {
/* 1284 */     SaveState();
/* 1285 */     dispose();
/*      */   }
/*      */   
/*      */   private JPanel jPanel5;
/*      */   private JPanel jPanel6;
/*      */   private JPanel jPanel7;
/*      */   
/*      */   private void btnCancelActionPerformed(ActionEvent evt)
/*      */   {
/* 1289 */     dispose();
/*      */   }
/*      */   
/*      */   private JPanel jPanel8;
/*      */   private JPanel jPanel9;
/*      */   private JTabbedPane jTabbedPane1;
/*      */   private JLabel lblCTRArmor;
/*      */   private void btnAmmoNameInfoActionPerformed(ActionEvent evt)
/*      */   {
/* 1294 */     String msg = "Ammo Print Name formatting.\n\n";
/* 1295 */     msg = msg + "Fill in the text field with how you would like your\n";
/* 1296 */     msg = msg + "ammunition names to be printed on the recordsheet.\n\n";
/* 1297 */     msg = msg + "You can use certain variables for information:\n";
/* 1298 */     msg = msg + "%F - Full Name of the ammunition\n";
/* 1299 */     msg = msg + "%P - Print Name (usually shorter than Full Name)\n";
/* 1300 */     msg = msg + "%L - Size of the ammo lot (number of rounds)\n\n";
/* 1301 */     msg = msg + "Example: @%P (%L)\n";
/* 1302 */     msg = msg + "Returns: @SRM-6 (15)\n";
/* 1303 */     msg = msg + "Example: [Ammo]%P (%L)\n";
/* 1304 */     msg = msg + "Returns: [Ammo]SRM-6 (15)\n";
/* 1305 */     Media.Messager(this, msg);
/*      */   }
/*      */   
/*      */   private void btnAmmoNameExportInfoActionPerformed(ActionEvent evt)
/*      */   {
/* 1309 */     btnAmmoNameInfoActionPerformed(evt);
/*      */   }
/*      */   
/*      */   private void chkHeatJumpMPActionPerformed(ActionEvent evt)
/*      */   {
/* 1313 */     if (this.chkHeatJumpMP.isSelected())
/*      */     {
/* 1314 */       this.chkHeatAllMP.setEnabled(true);
/*      */     }
/*      */     else
/*      */     {
/* 1316 */       this.chkHeatAllMP.setEnabled(false);
/* 1317 */       this.chkHeatAllMP.setSelected(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void chkCustomPercentsActionPerformed(ActionEvent evt)
/*      */   {
/* 1322 */     if (this.chkCustomPercents.isSelected())
/*      */     {
/* 1323 */       this.lblCTRArmor.setEnabled(true);
/* 1324 */       this.txtCTRArmor.setEnabled(true);
/* 1325 */       this.txtCTRArmor.setText("" + this.Prefs.getInt("ArmorCTRPercent", 25));
/* 1326 */       this.lblSTRArmor.setEnabled(true);
/* 1327 */       this.txtSTRArmor.setEnabled(true);
/* 1328 */       this.txtSTRArmor.setText("" + this.Prefs.getInt("ArmorSTRPercent", 25));
/*      */     }
/*      */     else
/*      */     {
/* 1331 */       this.txtCTRArmor.setText("25");
/* 1332 */       this.lblCTRArmor.setEnabled(false);
/* 1333 */       this.txtCTRArmor.setEnabled(false);
/* 1334 */       this.txtSTRArmor.setText("25");
/* 1335 */       this.lblSTRArmor.setEnabled(false);
/* 1336 */       this.txtSTRArmor.setEnabled(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorEmptyFGActionPerformed(ActionEvent evt)
/*      */   {
/* 1341 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorEmpty.getForeground());
/* 1342 */     if (newColor != null) {
/* 1342 */       this.lblColorEmpty.setForeground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorEmptyBGActionPerformed(ActionEvent evt)
/*      */   {
/* 1346 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorEmpty.getBackground());
/* 1347 */     if (newColor != null) {
/* 1347 */       this.lblColorEmpty.setBackground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorNormalFGActionPerformed(ActionEvent evt)
/*      */   {
/* 1351 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorNormal.getForeground());
/* 1352 */     if (newColor != null) {
/* 1352 */       this.lblColorNormal.setForeground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorNormalBGActionPerformed(ActionEvent evt)
/*      */   {
/* 1356 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorNormal.getBackground());
/* 1357 */     if (newColor != null) {
/* 1357 */       this.lblColorNormal.setBackground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorArmoredFGActionPerformed(ActionEvent evt)
/*      */   {
/* 1361 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorArmored.getForeground());
/* 1362 */     if (newColor != null) {
/* 1362 */       this.lblColorArmored.setForeground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorArmoredBGActionPerformed(ActionEvent evt)
/*      */   {
/* 1366 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorArmored.getBackground());
/* 1367 */     if (newColor != null) {
/* 1367 */       this.lblColorArmored.setBackground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorLinkedFGActionPerformed(ActionEvent evt)
/*      */   {
/* 1371 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorLinked.getForeground());
/* 1372 */     if (newColor != null) {
/* 1372 */       this.lblColorLinked.setForeground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorLinkedBGActionPerformed(ActionEvent evt)
/*      */   {
/* 1376 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorLinked.getBackground());
/* 1377 */     if (newColor != null) {
/* 1377 */       this.lblColorLinked.setBackground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorLockedFGActionPerformed(ActionEvent evt)
/*      */   {
/* 1381 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorLocked.getForeground());
/* 1382 */     if (newColor != null) {
/* 1382 */       this.lblColorLocked.setForeground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorLockedBGActionPerformed(ActionEvent evt)
/*      */   {
/* 1386 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorLocked.getBackground());
/* 1387 */     if (newColor != null) {
/* 1387 */       this.lblColorLocked.setBackground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorHiliteFGActionPerformed(ActionEvent evt)
/*      */   {
/* 1391 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorHilite.getForeground());
/* 1392 */     if (newColor != null) {
/* 1392 */       this.lblColorHilite.setForeground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnColorHiliteBGActionPerformed(ActionEvent evt)
/*      */   {
/* 1396 */     Color newColor = JColorChooser.showDialog(this, "Choose Foreground Color", this.lblColorHilite.getBackground());
/* 1397 */     if (newColor != null) {
/* 1397 */       this.lblColorHilite.setBackground(newColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnHTMLPathActionPerformed(ActionEvent evt)
/*      */   {
/* 1401 */     String path = "";
/* 1402 */     String oldpath = this.Prefs.get("HTMLExportPath", "");
/* 1403 */     JFileChooser fc = new JFileChooser();
/*      */     
/*      */ 
/*      */ 
/* 1407 */     fc.setFileSelectionMode(1);
/* 1408 */     fc.setAcceptAllFileFilterUsed(false);
/* 1409 */     if (oldpath != null) {
/* 1410 */       fc.setCurrentDirectory(new File(FileCommon.GetSafeFilename(oldpath)));
/*      */     }
/*      */     
/*      */ 
/* 1414 */     int returnVal = fc.showDialog(this, "Choose directory");
/*      */     
/*      */ 
/* 1417 */     if (returnVal == 0)
/*      */     {
/* 1418 */       path = fc.getSelectedFile().getPath();
/* 1419 */       this.txtHTMLPath.setText(path);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnTXTPathActionPerformed(ActionEvent evt)
/*      */   {
/* 1424 */     String path = "";
/* 1425 */     String oldpath = this.Prefs.get("TXTExportPath", "");
/* 1426 */     JFileChooser fc = new JFileChooser();
/*      */     
/*      */ 
/*      */ 
/* 1430 */     fc.setFileSelectionMode(1);
/* 1431 */     fc.setAcceptAllFileFilterUsed(false);
/* 1432 */     if (oldpath != null) {
/* 1433 */       fc.setCurrentDirectory(new File(FileCommon.GetSafeFilename(oldpath)));
/*      */     }
/*      */     
/*      */ 
/* 1437 */     int returnVal = fc.showDialog(this, "Choose directory");
/*      */     
/*      */ 
/* 1440 */     if (returnVal == 0)
/*      */     {
/* 1441 */       path = fc.getSelectedFile().getPath();
/* 1442 */       this.txtTXTPath.setText(path);
/*      */     }
/*      */   }
/*      */   
/*      */   private void btnMTFPathActionPerformed(ActionEvent evt)
/*      */   {
/* 1447 */     String path = "";
/* 1448 */     String oldpath = this.Prefs.get("MTFExportPath", "");
/* 1449 */     JFileChooser fc = new JFileChooser();
/*      */     
/*      */ 
/*      */ 
/* 1453 */     fc.setFileSelectionMode(1);
/* 1454 */     fc.setAcceptAllFileFilterUsed(false);
/* 1455 */     if (oldpath != null) {
/* 1456 */       fc.setCurrentDirectory(new File(FileCommon.GetSafeFilename(oldpath)));
/*      */     }
/*      */     
/*      */ 
/* 1460 */     int returnVal = fc.showDialog(this, "Choose directory");
/*      */     
/*      */ 
/* 1463 */     if (returnVal == 0)
/*      */     {
/* 1464 */       path = fc.getSelectedFile().getPath();
/* 1465 */       this.txtMTFPath.setText(path);
/*      */     }
/*      */   }
/*      */   private JLabel lblColorArmored;
/*      */   private JLabel lblColorEmpty;
/*      */   private JLabel lblColorHilite;
/*      */   private JLabel lblColorLinked;
/*      */   private JLabel lblColorLocked;
/*      */   
/*      */   private void cmbEraActionPerformed(ActionEvent evt)
/*      */   {
/* 1470 */     switch (this.cmbEra.getSelectedIndex())
/*      */     {
/*      */     case 0: 
/* 1472 */       this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/* 1473 */       switch (this.cmbRulesLevel.getSelectedIndex()) {
/*      */       case 0: 
/* 1475 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink" }));
/* 1476 */         break;
/*      */       default: 
/* 1478 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" })); }
/* 1479 */       break;
/*      */     
/*      */ 
/*      */     case 1: 
/* 1483 */       switch (this.cmbRulesLevel.getSelectedIndex()) {
/*      */       case 0: 
/* 1485 */         this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/* 1486 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink" }));
/* 1487 */         break;
/*      */       case 1: 
/* 1489 */         this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan" }));
/* 1490 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" }));
/* 1491 */         break;
/*      */       case 3: case 4: 
/* 1493 */         this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan", "Mixed" }));
/* 1494 */         if (this.cmbTechbase.getSelectedIndex() == 3) {
/* 1495 */           this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "(IS) Double Heat Sink", "(CL) Double Heat Sink" }));
/*      */         } else
/* 1497 */           this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" }));
/*      */         break;
/*      */       }
/* 1500 */       this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan" }));
/* 1501 */       this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" }));
/* 1502 */       break;
/*      */     case 2: 
/*      */     case 3: 
/*      */     case 4: 
/* 1506 */       switch (this.cmbRulesLevel.getSelectedIndex()) {
/*      */       case 0: 
/* 1508 */         this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere" }));
/* 1509 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink" }));
/* 1510 */         break;
/*      */       case 1: 
/* 1512 */         this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan" }));
/* 1513 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" }));
/* 1514 */         break;
/*      */       case 3: case 4: 
/* 1516 */         this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan", "Mixed" }));
/* 1517 */         if (this.cmbTechbase.getSelectedIndex() == 2) {
/* 1518 */           this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "(IS) Double Heat Sink", "(CL) Double Heat Sink", "Compact Heat Sink", "Laser Heat Sink" }));
/*      */         } else
/* 1520 */           this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink", "Compact Heat Sink", "Laser Heat Sink" }));
/*      */         break;
/*      */       }
/* 1523 */       this.cmbTechbase.setModel(new DefaultComboBoxModel(new String[] { "Inner Sphere", "Clan" }));
/* 1524 */       this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" }));
/*      */     }
/*      */   }
/*      */   
/*      */   private JLabel lblColorNormal;
/*      */   private JLabel lblSTRArmor;
/*      */   private JLabel lblScreenSizeNotice;
/*      */   private JPanel pnlConstruction;
/*      */   private JRadioButton rdoArmorArmPriority;
/*      */   private JRadioButton rdoArmorEqualHead;
/*      */   private JRadioButton rdoArmorLegPriority;
/*      */   
/*      */   private void cmbRulesLevelActionPerformed(ActionEvent evt)
/*      */   {
/* 1532 */     cmbEraActionPerformed(evt);
/*      */   }
/*      */   
/*      */   private JRadioButton rdoArmorMaxHead;
/*      */   private JRadioButton rdoArmorTorsoPriority;
/*      */   
/*      */   private void cmbTechbaseActionPerformed(ActionEvent evt)
/*      */   {
/* 1536 */     switch (this.cmbTechbase.getSelectedIndex())
/*      */     {
/*      */     case 0: 
/* 1538 */       switch (this.cmbRulesLevel.getSelectedIndex()) {
/*      */       case 0: 
/* 1540 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink" }));
/* 1541 */         break;
/*      */       case 3: case 4: 
/* 1543 */         if (this.cmbEra.getSelectedIndex() >= 2) {
/* 1544 */           this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink", "Compact Heat Sink" }));
/*      */         } else {
/* 1546 */           this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" }));
/*      */         }
/* 1548 */         break;
/*      */       case 1: case 2: default: 
/* 1550 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" })); }
/* 1551 */       break;
/*      */     
/*      */ 
/*      */     case 1: 
/* 1555 */       switch (this.cmbRulesLevel.getSelectedIndex()) {
/*      */       case 3: case 4: 
/* 1557 */         if (this.cmbEra.getSelectedIndex() >= 2) {
/* 1558 */           this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink", "Laser Heat Sink" }));
/*      */         } else {
/* 1560 */           this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" }));
/*      */         }
/* 1562 */         break;
/*      */       default: 
/* 1564 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "Double Heat Sink" })); }
/* 1565 */       break;
/*      */     
/*      */ 
/*      */     case 2: 
/* 1569 */       if (this.cmbEra.getSelectedIndex() >= 2) {
/* 1570 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "(IS) Double Heat Sink", "(CL) Double Heat Sink", "Compact Heat Sink", "Laser Heat Sink" }));
/*      */       } else {
/* 1572 */         this.cmbHeatSinks.setModel(new DefaultComboBoxModel(new String[] { "Single Heat Sink", "(IS) Double Heat Sink", "(CL) Double Heat Sink" }));
/*      */       }
/*      */       break;
/*      */     }
/*      */   }
/*      */   
/*      */   private JRadioButton rdoExportSortIn;
/*      */   private JRadioButton rdoExportSortOut;
/*      */   private JRadioButton rdoNormalSize;
/*      */   private JRadioButton rdoWidescreen;
/*      */   private JTextField txtAmmoExportName;
/*      */   
/*      */   private void btnExportActionPerformed(ActionEvent evt)
/*      */   {
/* 1579 */     FileOutputStream fos = null;
/* 1580 */     Media media = new Media();
/* 1581 */     File prefFile = media.SelectFile("prefs.xml", "xml", "Export Preferences To...");
/* 1582 */     SaveState();
/*      */     try
/*      */     {
/* 1584 */       fos = new FileOutputStream(prefFile.getCanonicalPath());
/* 1585 */       this.Prefs.exportSubtree(fos);
/* 1586 */       fos.close();
/* 1587 */       Media.Messager(this, "Preferences Exported."); return;
/*      */     }
/*      */     catch (FileNotFoundException ex)
/*      */     {
/* 1589 */       Logger.getLogger(dlgPrefs.class.getName()).log(Level.SEVERE, null, ex);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1591 */       Logger.getLogger(dlgPrefs.class.getName()).log(Level.SEVERE, null, e);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1594 */         fos.close();
/*      */       }
/*      */       catch (IOException ex)
/*      */       {
/* 1596 */         Logger.getLogger(dlgPrefs.class.getName()).log(Level.SEVERE, null, ex);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private JTextField txtAmmoPrintName;
/*      */   private JTextField txtCTRArmor;
/*      */   private JTextField txtHTMLPath;
/*      */   private JTextField txtImagePath;
/*      */   
/*      */   private void btnImportActionPerformed(ActionEvent evt)
/*      */   {
/* 1603 */     FileInputStream fis = null;
/* 1604 */     Media media = new Media();
/*      */     try
/*      */     {
/* 1606 */       File prefPath = media.SelectFile("prefs.xml", "xml", "Select Preferences File");
/* 1607 */       fis = new FileInputStream(prefPath);
/*      */       try
/*      */       {
/* 1609 */         Preferences.importPreferences(fis);
/* 1610 */         SetState();
/* 1611 */         Media.Messager(this, "Preferences Imported.");
/*      */       }
/*      */       catch (IOException ex)
/*      */       {
/* 1613 */         Logger.getLogger(dlgPrefs.class.getName()).log(Level.SEVERE, null, ex);
/*      */       }
/*      */       catch (InvalidPreferencesFormatException ex)
/*      */       {
/* 1615 */         Logger.getLogger(dlgPrefs.class.getName()).log(Level.SEVERE, null, ex);
/*      */       }
/*      */     }
/*      */     catch (FileNotFoundException ex)
/*      */     {
/* 1618 */       Logger.getLogger(dlgPrefs.class.getName()).log(Level.SEVERE, null, ex);
/*      */     }
/*      */   }
/*      */   
/*      */   private JTextField txtMTFPath;
/*      */   private JTextField txtSTRArmor;
/*      */   private JTextField txtTXTPath;
/*      */   private void btnDefaultImagePathActionPerformed(ActionEvent evt)
/*      */   {
/* 1623 */     Media media = new Media();
/* 1624 */     this.txtImagePath.setText(media.GetDirectorySelection(this, this.txtImagePath.getText()));
/*      */   }
/*      */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgPrefs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */