/*      */ package ssw.gui;
/*      */ 
/*      */ import Force.Force;
/*      */ import Force.Unit;
/*      */ import components.Mech;
/*      */ import dialog.frmForce;
/*      */ import filehandlers.MechReader;
/*      */ import filehandlers.MechWriter;
/*      */ import filehandlers.Media;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.prefs.Preferences;
/*      */ import javax.swing.DefaultComboBoxModel;
/*      */ import javax.swing.GroupLayout;
/*      */ import javax.swing.GroupLayout.Alignment;
/*      */ import javax.swing.GroupLayout.ParallelGroup;
/*      */ import javax.swing.GroupLayout.SequentialGroup;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JProgressBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JToolBar;
/*      */ import javax.swing.JToolBar.Separator;
/*      */ import javax.swing.LayoutStyle.ComponentPlacement;
/*      */ import list.ListFilter;
/*      */ import list.UnitList;
/*      */ import list.UnitListData;
/*      */ import list.view.abView;
/*      */ import ssw.print.Printer;
/*      */ 
/*      */ public class dlgOpen extends JFrame implements java.beans.PropertyChangeListener
/*      */ {
/*      */   private ifMechForm parent;
/*   50 */   private UnitList list = new UnitList();
/*   51 */   private Media media = new Media();
/*   52 */   private String dirPath = "";
/*   53 */   private String NL = "";
/*   54 */   private String msg = "";
/*   55 */   private abView currentView = new list.view.tbTotalWarfareView(this.list);
/*   56 */   private boolean cancelledListDirSelection = false;
/*      */   
/*   58 */   public int Requestor = 0;
/*      */   public static final int SSW = 0;
/*      */   public static final int FORCE = 1;
/*      */   private JButton btnAdd2Force;
/*      */   private JButton btnChangeDir;
/*      */   private JButton btnClearFilter;
/*      */   private JButton btnExport;
/*      */   private JButton btnFilter;
/*      */   private JButton btnMagic;
/*      */   private JButton btnOpen;
/*      */   
/*      */   public dlgOpen(java.awt.Frame parent, boolean modal)
/*      */   {
/*   64 */     initComponents();
/*   65 */     ImageIcon icon = new ImageIcon(super.getClass().getResource("/ssw/Images/appicon.png"));
/*   66 */     super.setIconImage(icon.getImage());
/*   67 */     this.parent = ((ifMechForm)parent);
/*      */     
/*   69 */     this.prgResaving.setVisible(false);
/*   70 */     this.cmbTech.setModel(new DefaultComboBoxModel(new String[] { "Any Tech", "Clan", "Inner Sphere", "Mixed" }));
/*   71 */     this.cmbEra.setModel(new DefaultComboBoxModel(new String[] { "Any Era", "Age of War/Star League", "Succession Wars", "Clan Invasion", "Dark Ages", "All Eras (non-canon)" }));
/*   72 */     this.cmbRulesLevel.setModel(new DefaultComboBoxModel(new String[] { "Any Level", "Introductory", "Tournament Legal", "Advanced Rules", "Experimental Tech", "Era Specific" }));
/*   73 */     this.cmbType.setModel(new DefaultComboBoxModel(new String[] { "Any Type", "BattleMech", "IndustrialMech", "Primitive BattleMech", "Primitive IndustrialMech" }));
/*   74 */     this.cmbMotive.setModel(new DefaultComboBoxModel(new String[] { "Any Motive", "Biped", "Quad" }));
/*   75 */     this.NL = System.getProperty("line.separator");
/*      */   }
/*      */   
/*      */   private JButton btnOptions;
/*      */   private JButton btnPrint;
/*      */   private JButton btnRefresh;
/*      */   private JButton btnViewForce;
/*      */   private JCheckBox chkOmni;
/*      */   
/*      */   private void LoadMech()
/*      */   {
/*   79 */     switch (this.Requestor)
/*      */     {
/*      */     case 0: 
/*   81 */       LoadMechIntoSSW();
/*   82 */       break;
/*      */     
/*      */     case 1: 
/*   85 */       btnAdd2ForceActionPerformed(null);
/*   86 */       setVisible(false);
/*   87 */       this.parent.GetForceDialogue().setVisible(true);
/*   88 */       break;
/*      */     
/*      */     default: 
/*   91 */       LoadMechIntoSSW();
/*      */     }
/*      */   }
/*      */   
/*      */   private JComboBox cmbEra;
/*      */   private JComboBox cmbMinMP;
/*      */   private JComboBox cmbMotive;
/*      */   private JComboBox cmbRulesLevel;
/*      */   private JComboBox cmbTech;
/*      */   
/*      */   private void LoadMechIntoSSW()
/*      */   {
/*   96 */     UnitListData Data = (UnitListData)((abView)this.tblMechData.getModel()).Get(this.tblMechData.convertRowIndexToModel(this.tblMechData.getSelectedRow()));
/*      */     try
/*      */     {
/*   99 */       MechReader read = new MechReader();
/*  100 */       Mech m = read.ReadMech(this.list.getDirectory() + Data.getFilename(), this.parent.GetData());
/*  101 */       if (Data.isOmni()) {
/*  102 */         m.SetCurLoadout(Data.getConfig());
/*      */       }
/*  104 */       this.parent.setMech(m);
/*      */       
/*  106 */       this.parent.GetPrefs().put("LastOpenDirectory", this.list.getDirectory() + Data.getFilename().substring(0, Data.getFilename().lastIndexOf(File.separator) + 1));
/*  107 */       this.parent.GetPrefs().put("LastOpenFile", Data.getFilename().substring(Data.getFilename().lastIndexOf(File.separator) + 1));
/*      */       
/*  109 */       this.parent.GetMech().SetChanged(false);
/*      */       
/*  111 */       this.tblMechData.clearSelection();
/*      */       
/*  113 */       setVisible(false);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  116 */       Media.Messager((JFrame)this.parent, e.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */   private JComboBox cmbType;
/*      */   private JComboBox cmbView;
/*      */   private JPanel jPanel1;
/*      */   private JPanel jPanel10;
/*      */   private JPanel jPanel11;
/*      */   
/*      */   private void Calculate()
/*      */   {
/*  121 */     this.txtSelected.setText("0 Units Selected for 0 BV and 0 C-Bills");
/*      */     
/*  123 */     int BV = 0;
/*  124 */     double Cost = 0.0D;
/*      */     
/*  126 */     int[] rows = this.tblMechData.getSelectedRows();
/*  127 */     for (int i = 0; i < rows.length; i++) {
/*  128 */       UnitListData data = (UnitListData)((abView)this.tblMechData.getModel()).Get(this.tblMechData.convertRowIndexToModel(rows[i]));
/*  129 */       BV += data.getBV();
/*  130 */       Cost += data.getCost();
/*      */     }
/*      */     
/*  133 */     this.txtSelected.setText(rows.length + " Units Selected for " + String.format("%,d", new Object[] { Integer.valueOf(BV) }) + " BV and " + String.format("%,.2f", new Object[] { Double.valueOf(Cost) }) + " C-Bills");
/*      */   }
/*      */   
/*      */   private JPanel jPanel12;
/*      */   private JPanel jPanel13;
/*      */   private JPanel jPanel14;
/*      */   private JPanel jPanel15;
/*      */   
/*      */   public void LoadList()
/*      */   {
/*  137 */     LoadList(true);
/*      */   }
/*      */   
/*      */   private JPanel jPanel2;
/*      */   private JPanel jPanel3;
/*      */   private JPanel jPanel4;
/*      */   private JPanel jPanel5;
/*      */   
/*      */   public void LoadList(boolean useIndex)
/*      */   {
/*  141 */     this.lblStatus.setText("Loading Mechs...");
/*  142 */     this.txtSelected.setText("0 Units Selected for 0 BV and 0 C-Bills");
/*  143 */     this.tblMechData.setModel(new UnitList());
/*      */     
/*  145 */     if (this.dirPath.isEmpty()) {
/*  146 */       this.dirPath = this.parent.GetPrefs().get("ListPath", this.parent.GetPrefs().get("LastOpenDirectory", ""));
/*      */       
/*  148 */       if ((this.dirPath.isEmpty()) && (isVisible()) && (!this.cancelledListDirSelection)) {
/*  149 */         dlgSSWFiles dFiles = new dlgSSWFiles(this, true);
/*  150 */         dFiles.setLocationRelativeTo(this);
/*  151 */         dFiles.setVisible(true);
/*  152 */         if (dFiles.result) {
/*  153 */           this.dirPath = this.media.GetDirectorySelection(this, "", "Select SSW File Directory");
/*  154 */           this.parent.GetPrefs().put("ListPath", this.dirPath);
/*  155 */           if (this.dirPath.isEmpty()) {
/*  156 */             this.cancelledListDirSelection = true;
/*  157 */             setVisible(false);
/*      */           }
/*      */         } else {
/*  160 */           this.cancelledListDirSelection = true;
/*  161 */           setVisible(false);
/*      */         }
/*  163 */         dFiles.dispose();
/*      */       }
/*      */     }
/*      */     
/*  167 */     setCursor(Cursor.getPredefinedCursor(3));
/*      */     
/*  169 */     this.list = new UnitList(this.dirPath, useIndex);
/*      */     
/*  171 */     if (this.list.Size() > 0) {
/*  172 */       setupList(this.list, true);
/*      */     }
/*      */     
/*  175 */     String displayPath = this.dirPath;
/*  176 */     this.lblStatus.setText(this.list.Size() + " Mechs loaded from " + displayPath);
/*  177 */     this.lblStatus.setToolTipText(this.dirPath);
/*  178 */     this.spnMechTable.getVerticalScrollBar().setValue(0);
/*  179 */     setCursor(Cursor.getPredefinedCursor(0));
/*      */   }
/*      */   
/*      */   private JPanel jPanel6;
/*      */   private JPanel jPanel7;
/*      */   private JPanel jPanel8;
/*      */   private JPanel jPanel9;
/*      */   private void setupList(UnitList mechList, boolean forceSort)
/*      */   {
/*  183 */     ListFilter fileFilter = new ListFilter();
/*  184 */     fileFilter.setExtension(".ssw");
/*  185 */     this.currentView.list = mechList.Filter(fileFilter);
/*  186 */     this.tblMechData.setModel(this.currentView);
/*  187 */     this.currentView.setupTable(this.tblMechData);
/*      */     
/*      */ 
/*  190 */     this.lblShowing.setText("Showing " + mechList.Size() + " of " + this.list.Size());
/*      */   }
/*      */   
/*      */   private void checkSelection()
/*      */   {
/*  194 */     if (this.tblMechData.getSelectedRowCount() > 0)
/*      */     {
/*  195 */       Calculate();
/*  196 */       this.btnOpen.setEnabled(true);
/*  197 */       this.btnPrint.setEnabled(true);
/*  198 */       this.btnAdd2Force.setEnabled(true);
/*      */     }
/*      */     else
/*      */     {
/*  200 */       this.btnOpen.setEnabled(false);
/*  201 */       this.btnPrint.setEnabled(false);
/*  202 */       this.btnAdd2Force.setEnabled(false);
/*  203 */       this.txtSelected.setText("0 Units Selected for 0 BV and 0 C-Bills");
/*      */     }
/*      */   }
/*      */   
/*      */   private void batchUpdateMechs()
/*      */   {
/*  209 */     this.prgResaving.setValue(0);
/*  210 */     this.prgResaving.setVisible(true);
/*  211 */     int Response = javax.swing.JOptionPane.showConfirmDialog(this, "This will open and re-save each file in the current directory so that all files are updated with current BV and Cost calculations.\nThis process could take a few minutes, are you ready?", "Batch Mech Processing", 0);
/*  212 */     if (Response == 0)
/*      */     {
/*  213 */       this.msg = "";
/*  214 */       setCursor(Cursor.getPredefinedCursor(3));
/*      */       try
/*      */       {
/*  216 */         Resaver Saving = new Resaver(this);
/*  217 */         Saving.addPropertyChangeListener(this);
/*  218 */         Saving.execute();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  221 */         Media.Messager(this, "A fatal error occured while processing the 'Mechs:\n" + e.getMessage());
/*  222 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  225 */       this.prgResaving.setVisible(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void setTooltip(UnitListData data)
/*      */   {
/*      */     try
/*      */     {
/*  232 */       String[] dirs = data.getFilename().split("\\\\");
/*  233 */       String shortPath = "";
/*  234 */       if (dirs.length > 3) {
/*  235 */         for (int i = dirs.length - 1; i >= dirs.length - 3; i--) {
/*  236 */           shortPath = "\\" + dirs[i] + shortPath;
/*      */         }
/*      */       }
/*  239 */       this.txtSelected.setText(data.getInfo() + " (" + shortPath + ")");
/*      */     }
/*      */     catch (Exception e) {}
/*      */   }
/*      */   
/*      */   public void propertyChange(java.beans.PropertyChangeEvent e)
/*      */   {
/*  246 */     this.prgResaving.setValue(((Resaver)e.getSource()).getProgress());
/*      */   }
/*      */   
/*      */   private class Resaver
/*      */     extends javax.swing.SwingWorker<Void, Void>
/*      */   {
/*  251 */     int totalFileCount = 0; int filesUpdated = 0;
/*      */     dlgOpen Owner;
/*      */     
/*      */     public Resaver(dlgOpen owner)
/*      */     {
/*  255 */       this.Owner = owner;
/*      */     }
/*      */     
/*      */     public void done()
/*      */     {
/*  260 */       dlgOpen.this.setCursor(Cursor.getPredefinedCursor(0));
/*  261 */       if (dlgOpen.this.msg.length() > 0) {
/*  262 */         dlgTextExport Message = new dlgTextExport(this.Owner, true, dlgOpen.this.msg);
/*  263 */         Message.setLocationRelativeTo(this.Owner);
/*  264 */         Message.setVisible(true);
/*      */       } else {
/*  266 */         Media.Messager(this.Owner, this.filesUpdated + " Files Updated.  Reloading list next.");
/*      */       }
/*  268 */       dlgOpen.this.prgResaving.setVisible(false);
/*  269 */       dlgOpen.this.prgResaving.setValue(0);
/*  270 */       dlgOpen.this.LoadList(false);
/*      */     }
/*      */     
/*      */     protected Void doInBackground()
/*      */       throws Exception
/*      */     {
/*  275 */       MechReader read = new MechReader();
/*  276 */       MechWriter writer = new MechWriter();
/*      */       
/*  278 */       File FileList = new File(dlgOpen.this.dirPath);
/*      */       try {
/*  280 */         processDir(FileList, read, writer);
/*      */       } catch (IOException ie) {
/*  282 */         System.out.println(ie.getMessage());
/*  283 */         throw new Exception(dlgOpen.this.msg);
/*      */       } catch (Exception e) {
/*  285 */         throw e;
/*      */       }
/*      */       
/*  288 */       return null;
/*      */     }
/*      */     
/*      */     private void processDir(File directory, MechReader read, MechWriter writer)
/*      */       throws IOException
/*      */     {
/*  292 */       File[] files = directory.listFiles();
/*  293 */       this.totalFileCount += files.length;
/*  294 */       for (int i = 0; i < files.length; i++) {
/*  295 */         if ((files[i].isFile()) && (files[i].getCanonicalPath().endsWith(".ssw"))) {
/*  296 */           processFile(files[i], read, writer);
/*  297 */           int progress = (int)((this.filesUpdated + 1.0D) / this.totalFileCount * 100.0D);
/*  298 */           setProgress(progress);
/*  299 */         } else if (files[i].isDirectory())
/*      */         {
/*  300 */           processDir(files[i], read, writer);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     private void processFile(File file, MechReader read, MechWriter writer)
/*      */       throws IOException
/*      */     {
/*      */       try
/*      */       {
/*  307 */         Mech m = read.ReadMech(file.getCanonicalPath(), dlgOpen.this.parent.GetData());
/*      */         
/*      */ 
/*  310 */         writer.setMech(m);
/*      */         try
/*      */         {
/*  312 */           writer.WriteXML(file.getCanonicalPath());
/*  313 */           this.filesUpdated += 1;
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*  315 */           dlgOpen.this.msg = (dlgOpen.this.msg + "Could not load the following file(s):" + dlgOpen.this.NL);
/*  316 */           dlgOpen.this.msg = (dlgOpen.this.msg + file.getCanonicalPath() + dlgOpen.this.NL + dlgOpen.this.NL);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  320 */         dlgOpen.this.msg = (dlgOpen.this.msg + file.getCanonicalPath() + dlgOpen.this.NL);
/*  321 */         if (e.getMessage() == null)
/*      */         {
/*  322 */           StackTraceElement[] trace = e.getStackTrace();
/*  323 */           for (int j = 0; j < trace.length; j++) {
/*  324 */             dlgOpen.this.msg = (dlgOpen.this.msg + trace[j].toString() + dlgOpen.this.NL);
/*      */           }
/*  326 */           dlgOpen.this.msg = (dlgOpen.this.msg + dlgOpen.this.NL);
/*      */         }
/*      */         else
/*      */         {
/*  328 */           dlgOpen.this.msg = (dlgOpen.this.msg + e.getMessage() + dlgOpen.this.NL + dlgOpen.this.NL);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private JToolBar.Separator jSeparator1;
/*      */   private JToolBar.Separator jSeparator2;
/*      */   private JToolBar.Separator jSeparator3;
/*      */   private JToolBar.Separator jSeparator4;
/*      */   private JToolBar.Separator jSeparator5;
/*      */   private JLabel lblBV;
/*      */   private JLabel lblCost;
/*      */   private JLabel lblEra;
/*      */   private JLabel lblForce;
/*      */   private JLabel lblLevel;
/*      */   private JLabel lblMinMP;
/*      */   private JLabel lblMotive;
/*      */   private JLabel lblName;
/*      */   private JLabel lblShowing;
/*      */   private JLabel lblSource;
/*      */   
/*      */   private void initComponents()
/*      */   {
/*  343 */     this.txtSelected = new JLabel();
/*  344 */     this.tlbActions = new JToolBar();
/*  345 */     this.btnOpen = new JButton();
/*  346 */     this.jSeparator4 = new JToolBar.Separator();
/*  347 */     this.btnChangeDir = new JButton();
/*  348 */     this.btnPrint = new JButton();
/*  349 */     this.btnAdd2Force = new JButton();
/*  350 */     this.btnViewForce = new JButton();
/*  351 */     this.btnExport = new JButton();
/*  352 */     this.jSeparator1 = new JToolBar.Separator();
/*  353 */     this.btnOptions = new JButton();
/*  354 */     this.btnRefresh = new JButton();
/*  355 */     this.jSeparator2 = new JToolBar.Separator();
/*  356 */     this.btnMagic = new JButton();
/*  357 */     this.jSeparator3 = new JToolBar.Separator();
/*  358 */     this.lblForce = new JLabel();
/*  359 */     this.jSeparator5 = new JToolBar.Separator();
/*  360 */     this.cmbView = new JComboBox();
/*  361 */     this.spnMechTable = new JScrollPane();
/*  362 */     this.tblMechData = new JTable();
/*  363 */     this.pnlFilters = new JPanel();
/*  364 */     this.jPanel1 = new JPanel();
/*  365 */     this.btnFilter = new JButton();
/*  366 */     this.btnClearFilter = new JButton();
/*  367 */     this.jPanel14 = new JPanel();
/*  368 */     this.jPanel7 = new JPanel();
/*  369 */     this.lblMinMP = new JLabel();
/*  370 */     this.cmbMinMP = new JComboBox();
/*  371 */     this.jPanel3 = new JPanel();
/*  372 */     this.cmbEra = new JComboBox();
/*  373 */     this.lblEra = new JLabel();
/*  374 */     this.jPanel6 = new JPanel();
/*  375 */     this.lblMotive = new JLabel();
/*  376 */     this.cmbMotive = new JComboBox();
/*  377 */     this.jPanel2 = new JPanel();
/*  378 */     this.cmbTech = new JComboBox();
/*  379 */     this.lblTech = new JLabel();
/*  380 */     this.jPanel4 = new JPanel();
/*  381 */     this.lblLevel = new JLabel();
/*  382 */     this.cmbRulesLevel = new JComboBox();
/*  383 */     this.jPanel5 = new JPanel();
/*  384 */     this.lblType = new JLabel();
/*  385 */     this.cmbType = new JComboBox();
/*  386 */     this.jPanel15 = new JPanel();
/*  387 */     this.jPanel13 = new JPanel();
/*  388 */     this.lblName = new JLabel();
/*  389 */     this.txtName = new JTextField();
/*  390 */     this.jPanel12 = new JPanel();
/*  391 */     this.txtSource = new JTextField();
/*  392 */     this.lblSource = new JLabel();
/*  393 */     this.jPanel9 = new JPanel();
/*  394 */     this.txtMaxBV = new JTextField();
/*  395 */     this.lblBV = new JLabel();
/*  396 */     this.txtMinBV = new JTextField();
/*  397 */     this.jPanel10 = new JPanel();
/*  398 */     this.lblTonnage1 = new JLabel();
/*  399 */     this.txtMinYear = new JTextField();
/*  400 */     this.txtMaxYear = new JTextField();
/*  401 */     this.jPanel8 = new JPanel();
/*  402 */     this.lblTonnage = new JLabel();
/*  403 */     this.txtMaxTon = new JTextField();
/*  404 */     this.txtMinTon = new JTextField();
/*  405 */     this.jPanel11 = new JPanel();
/*  406 */     this.txtMinCost = new JTextField();
/*  407 */     this.lblCost = new JLabel();
/*  408 */     this.txtMaxCost = new JTextField();
/*  409 */     this.chkOmni = new JCheckBox();
/*  410 */     this.lblStatus = new JLabel();
/*  411 */     this.prgResaving = new JProgressBar();
/*  412 */     this.lblShowing = new JLabel();
/*      */     
/*  414 */     setDefaultCloseOperation(2);
/*  415 */     setTitle("Select Mech(s)");
/*  416 */     setMinimumSize(new Dimension(600, 500));
/*  417 */     addWindowFocusListener(new java.awt.event.WindowFocusListener() {
/*      */       public void windowGainedFocus(WindowEvent evt) {
/*  419 */         dlgOpen.this.formWindowGainedFocus(evt);
/*      */       }
/*      */       
/*      */       public void windowLostFocus(WindowEvent evt) {}
/*  423 */     });
/*  424 */     addWindowListener(new java.awt.event.WindowAdapter() {
/*      */       public void windowClosed(WindowEvent evt) {
/*  426 */         dlgOpen.this.formWindowClosed(evt);
/*      */       }
/*      */       
/*  429 */       public void windowOpened(WindowEvent evt) { dlgOpen.this.formWindowOpened(evt);
/*      */       }
/*      */ 
/*  432 */     });
/*  433 */     this.txtSelected.setText("0 Units Selected");
/*      */     
/*  435 */     this.tlbActions.setFloatable(false);
/*  436 */     this.tlbActions.setRollover(true);
/*      */     
/*  438 */     this.btnOpen.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/folder-open-mech.png")));
/*  439 */     this.btnOpen.setToolTipText("Open Mech");
/*  440 */     this.btnOpen.setEnabled(false);
/*  441 */     this.btnOpen.setFocusable(false);
/*  442 */     this.btnOpen.setHorizontalTextPosition(0);
/*  443 */     this.btnOpen.setVerticalTextPosition(3);
/*  444 */     this.btnOpen.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  446 */         dlgOpen.this.btnOpenActionPerformed(evt);
/*      */       }
/*  448 */     });
/*  449 */     this.tlbActions.add(this.btnOpen);
/*  450 */     this.tlbActions.add(this.jSeparator4);
/*      */     
/*  452 */     this.btnChangeDir.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/folders.png")));
/*  453 */     this.btnChangeDir.setToolTipText("Change Directory");
/*  454 */     this.btnChangeDir.setFocusable(false);
/*  455 */     this.btnChangeDir.setHorizontalTextPosition(0);
/*  456 */     this.btnChangeDir.setVerticalTextPosition(3);
/*  457 */     this.btnChangeDir.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  459 */         dlgOpen.this.btnChangeDirActionPerformed(evt);
/*      */       }
/*  461 */     });
/*  462 */     this.tlbActions.add(this.btnChangeDir);
/*      */     
/*  464 */     this.btnPrint.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/printer.png")));
/*  465 */     this.btnPrint.setToolTipText("Print Selected Mechs");
/*  466 */     this.btnPrint.setEnabled(false);
/*  467 */     this.btnPrint.setFocusable(false);
/*  468 */     this.btnPrint.setHorizontalTextPosition(0);
/*  469 */     this.btnPrint.setVerticalTextPosition(3);
/*  470 */     this.btnPrint.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  472 */         dlgOpen.this.btnPrintActionPerformed(evt);
/*      */       }
/*  474 */     });
/*  475 */     this.tlbActions.add(this.btnPrint);
/*      */     
/*  477 */     this.btnAdd2Force.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/clipboard--plus.png")));
/*  478 */     this.btnAdd2Force.setToolTipText("Add to Force List");
/*  479 */     this.btnAdd2Force.setEnabled(false);
/*  480 */     this.btnAdd2Force.setFocusable(false);
/*  481 */     this.btnAdd2Force.setHorizontalTextPosition(0);
/*  482 */     this.btnAdd2Force.setVerticalTextPosition(3);
/*  483 */     this.btnAdd2Force.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  485 */         dlgOpen.this.btnAdd2ForceActionPerformed(evt);
/*      */       }
/*  487 */     });
/*  488 */     this.tlbActions.add(this.btnAdd2Force);
/*      */     
/*  490 */     this.btnViewForce.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/clipboard.png")));
/*  491 */     this.btnViewForce.setToolTipText("View Force");
/*  492 */     this.btnViewForce.setFocusable(false);
/*  493 */     this.btnViewForce.setHorizontalTextPosition(0);
/*  494 */     this.btnViewForce.setVerticalTextPosition(3);
/*  495 */     this.btnViewForce.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  497 */         dlgOpen.this.btnViewForceActionPerformed(evt);
/*      */       }
/*  499 */     });
/*  500 */     this.tlbActions.add(this.btnViewForce);
/*      */     
/*  502 */     this.btnExport.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/document--arrow.png")));
/*  503 */     this.btnExport.setToolTipText("Export List to CSV");
/*  504 */     this.btnExport.setFocusable(false);
/*  505 */     this.btnExport.setHorizontalTextPosition(0);
/*  506 */     this.btnExport.setVerticalTextPosition(3);
/*  507 */     this.btnExport.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  509 */         dlgOpen.this.btnExportActionPerformed(evt);
/*      */       }
/*  511 */     });
/*  512 */     this.tlbActions.add(this.btnExport);
/*  513 */     this.tlbActions.add(this.jSeparator1);
/*      */     
/*  515 */     this.btnOptions.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/gear.png")));
/*  516 */     this.btnOptions.setToolTipText("Change Options");
/*  517 */     this.btnOptions.setFocusable(false);
/*  518 */     this.btnOptions.setHorizontalTextPosition(0);
/*  519 */     this.btnOptions.setVerticalTextPosition(3);
/*  520 */     this.btnOptions.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  522 */         dlgOpen.this.btnOptionsActionPerformed(evt);
/*      */       }
/*  524 */     });
/*  525 */     this.tlbActions.add(this.btnOptions);
/*      */     
/*  527 */     this.btnRefresh.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/arrow-circle-double.png")));
/*  528 */     this.btnRefresh.setToolTipText("Refresh Mech List");
/*  529 */     this.btnRefresh.setFocusable(false);
/*  530 */     this.btnRefresh.setHorizontalTextPosition(0);
/*  531 */     this.btnRefresh.setVerticalTextPosition(3);
/*  532 */     this.btnRefresh.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  534 */         dlgOpen.this.btnRefreshActionPerformed(evt);
/*      */       }
/*  536 */     });
/*  537 */     this.tlbActions.add(this.btnRefresh);
/*  538 */     this.tlbActions.add(this.jSeparator2);
/*      */     
/*  540 */     this.btnMagic.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/wand.png")));
/*  541 */     this.btnMagic.setToolTipText("Update Mech Files (Long Process!)");
/*  542 */     this.btnMagic.setFocusable(false);
/*  543 */     this.btnMagic.setHorizontalTextPosition(0);
/*  544 */     this.btnMagic.setVerticalTextPosition(3);
/*  545 */     this.btnMagic.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  547 */         dlgOpen.this.btnMagicActionPerformed(evt);
/*      */       }
/*  549 */     });
/*  550 */     this.tlbActions.add(this.btnMagic);
/*  551 */     this.tlbActions.add(this.jSeparator3);
/*  552 */     this.tlbActions.add(this.lblForce);
/*  553 */     this.tlbActions.add(this.jSeparator5);
/*      */     
/*  555 */     this.cmbView.setModel(new DefaultComboBoxModel(new String[] { "Total Warfare Standard", "Total Warfare Compact", "BattleForce Information", "Chat Information" }));
/*  556 */     this.cmbView.setFocusable(false);
/*  557 */     this.cmbView.setMaximumSize(new Dimension(139, 20));
/*  558 */     this.cmbView.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  560 */         dlgOpen.this.cmbViewActionPerformed(evt);
/*      */       }
/*  562 */     });
/*  563 */     this.tlbActions.add(this.cmbView);
/*      */     
/*  565 */     this.tblMechData.setAutoCreateRowSorter(true);
/*  566 */     this.tblMechData.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { new Object[0], new Object[0], new Object[0], new Object[0] }, new String[0]));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  577 */     this.tblMechData.setIntercellSpacing(new Dimension(4, 4));
/*  578 */     this.tblMechData.setShowVerticalLines(false);
/*  579 */     this.tblMechData.addMouseListener(new java.awt.event.MouseAdapter() {
/*      */       public void mouseClicked(MouseEvent evt) {
/*  581 */         dlgOpen.this.tblMechDataMouseClicked(evt);
/*      */       }
/*  583 */     });
/*  584 */     this.tblMechData.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
/*      */       public void mouseMoved(MouseEvent evt) {
/*  586 */         dlgOpen.this.tblMechDataMouseMoved(evt);
/*      */       }
/*  588 */     });
/*  589 */     this.tblMechData.addKeyListener(new java.awt.event.KeyAdapter() {
/*      */       public void keyReleased(KeyEvent evt) {
/*  591 */         dlgOpen.this.tblMechDataKeyReleased(evt);
/*      */       }
/*  593 */     });
/*  594 */     this.spnMechTable.setViewportView(this.tblMechData);
/*      */     
/*  596 */     this.pnlFilters.setBorder(javax.swing.BorderFactory.createTitledBorder("Filters"));
/*      */     
/*  598 */     this.btnFilter.setText("Filter");
/*  599 */     this.btnFilter.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  601 */         dlgOpen.this.Filter(evt);
/*      */       }
/*      */       
/*  604 */     });
/*  605 */     this.btnClearFilter.setText("Clear");
/*  606 */     this.btnClearFilter.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  608 */         dlgOpen.this.btnClearFilterFilter(evt);
/*      */       }
/*      */       
/*  611 */     });
/*  612 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/*  613 */     this.jPanel1.setLayout(jPanel1Layout);
/*  614 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/*  615 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  616 */       .addComponent(this.btnFilter)
/*  617 */       .addComponent(this.btnClearFilter));
/*      */     
/*  619 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/*  620 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  621 */       .addGroup(jPanel1Layout.createSequentialGroup()
/*  622 */       .addComponent(this.btnFilter)
/*  623 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  624 */       .addComponent(this.btnClearFilter)));
/*      */     
/*      */ 
/*  627 */     this.lblMinMP.setText("Min MP");
/*      */     
/*  629 */     this.cmbMinMP.setModel(new DefaultComboBoxModel(new String[] { "All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
/*  630 */     this.cmbMinMP.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  632 */         dlgOpen.this.cmbMinMPFilter(evt);
/*      */       }
/*      */       
/*  635 */     });
/*  636 */     GroupLayout jPanel7Layout = new GroupLayout(this.jPanel7);
/*  637 */     this.jPanel7.setLayout(jPanel7Layout);
/*  638 */     jPanel7Layout.setHorizontalGroup(jPanel7Layout
/*  639 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  640 */       .addComponent(this.lblMinMP)
/*  641 */       .addComponent(this.cmbMinMP, -2, -1, -2));
/*      */     
/*  643 */     jPanel7Layout.setVerticalGroup(jPanel7Layout
/*  644 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  645 */       .addGroup(jPanel7Layout.createSequentialGroup()
/*  646 */       .addComponent(this.lblMinMP)
/*  647 */       .addGap(1, 1, 1)
/*  648 */       .addComponent(this.cmbMinMP, -2, -1, -2)));
/*      */     
/*      */ 
/*  651 */     this.cmbEra.setModel(new DefaultComboBoxModel(new String[] { "Any Era", "Age of War/Star League", "Succession Wars", "Clan Invasion", "Dark Ages", "All Eras (non-canon)" }));
/*  652 */     this.cmbEra.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  654 */         dlgOpen.this.Filter(evt);
/*      */       }
/*      */       
/*  657 */     });
/*  658 */     this.lblEra.setText("Era");
/*      */     
/*  660 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/*  661 */     this.jPanel3.setLayout(jPanel3Layout);
/*  662 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout
/*  663 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  664 */       .addComponent(this.cmbEra, -2, 148, -2)
/*  665 */       .addComponent(this.lblEra));
/*      */     
/*  667 */     jPanel3Layout.setVerticalGroup(jPanel3Layout
/*  668 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  669 */       .addGroup(jPanel3Layout.createSequentialGroup()
/*  670 */       .addComponent(this.lblEra)
/*  671 */       .addGap(1, 1, 1)
/*  672 */       .addComponent(this.cmbEra, -2, -1, -2)));
/*      */     
/*      */ 
/*  675 */     this.lblMotive.setText("Motive Type");
/*      */     
/*  677 */     this.cmbMotive.setModel(new DefaultComboBoxModel(new String[] { "Any Motive", "Biped", "Quad" }));
/*  678 */     this.cmbMotive.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  680 */         dlgOpen.this.cmbMotiveFilter(evt);
/*      */       }
/*      */       
/*  683 */     });
/*  684 */     GroupLayout jPanel6Layout = new GroupLayout(this.jPanel6);
/*  685 */     this.jPanel6.setLayout(jPanel6Layout);
/*  686 */     jPanel6Layout.setHorizontalGroup(jPanel6Layout
/*  687 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  688 */       .addComponent(this.lblMotive)
/*  689 */       .addComponent(this.cmbMotive, -2, 118, -2));
/*      */     
/*  691 */     jPanel6Layout.setVerticalGroup(jPanel6Layout
/*  692 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  693 */       .addGroup(jPanel6Layout.createSequentialGroup()
/*  694 */       .addComponent(this.lblMotive)
/*  695 */       .addGap(1, 1, 1)
/*  696 */       .addComponent(this.cmbMotive, -2, -1, -2)));
/*      */     
/*      */ 
/*  699 */     this.cmbTech.setModel(new DefaultComboBoxModel(new String[] { "Any Tech", "Clan", "Inner Sphere", "Mixed" }));
/*  700 */     this.cmbTech.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  702 */         dlgOpen.this.Filter(evt);
/*      */       }
/*      */       
/*  705 */     });
/*  706 */     this.lblTech.setText("Technology");
/*      */     
/*  708 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/*  709 */     this.jPanel2.setLayout(jPanel2Layout);
/*  710 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/*  711 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  712 */       .addComponent(this.cmbTech, -2, 118, -2)
/*  713 */       .addComponent(this.lblTech));
/*      */     
/*  715 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/*  716 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  717 */       .addGroup(jPanel2Layout.createSequentialGroup()
/*  718 */       .addComponent(this.lblTech)
/*  719 */       .addGap(0, 0, 0)
/*  720 */       .addComponent(this.cmbTech, -2, -1, -2)
/*  721 */       .addContainerGap(-1, 32767)));
/*      */     
/*      */ 
/*  724 */     this.lblLevel.setText("Rules Level");
/*      */     
/*  726 */     this.cmbRulesLevel.setModel(new DefaultComboBoxModel(new String[] { "Any Level", "Introductory", "Tournament Legal", "Advanced Rules", "Experimental Tech", "Era Specific" }));
/*  727 */     this.cmbRulesLevel.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  729 */         dlgOpen.this.cmbRulesLevelFilter(evt);
/*      */       }
/*      */       
/*  732 */     });
/*  733 */     GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
/*  734 */     this.jPanel4.setLayout(jPanel4Layout);
/*  735 */     jPanel4Layout.setHorizontalGroup(jPanel4Layout
/*  736 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  737 */       .addComponent(this.cmbRulesLevel, -2, 148, -2)
/*  738 */       .addComponent(this.lblLevel));
/*      */     
/*  740 */     jPanel4Layout.setVerticalGroup(jPanel4Layout
/*  741 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  742 */       .addGroup(jPanel4Layout.createSequentialGroup()
/*  743 */       .addComponent(this.lblLevel)
/*  744 */       .addGap(1, 1, 1)
/*  745 */       .addComponent(this.cmbRulesLevel, -2, -1, -2)));
/*      */     
/*      */ 
/*  748 */     this.lblType.setText("Mech Type");
/*      */     
/*  750 */     this.cmbType.setModel(new DefaultComboBoxModel(new String[] { "Any Type", "BattleMech", "IndustrialMech", "Primitive BattleMech", "Primitive IndustrialMech" }));
/*  751 */     this.cmbType.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  753 */         dlgOpen.this.cmbTypeFilter(evt);
/*      */       }
/*      */       
/*  756 */     });
/*  757 */     GroupLayout jPanel5Layout = new GroupLayout(this.jPanel5);
/*  758 */     this.jPanel5.setLayout(jPanel5Layout);
/*  759 */     jPanel5Layout.setHorizontalGroup(jPanel5Layout
/*  760 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  761 */       .addComponent(this.cmbType, -2, 118, -2)
/*  762 */       .addComponent(this.lblType));
/*      */     
/*  764 */     jPanel5Layout.setVerticalGroup(jPanel5Layout
/*  765 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  766 */       .addGroup(jPanel5Layout.createSequentialGroup()
/*  767 */       .addComponent(this.lblType)
/*  768 */       .addGap(1, 1, 1)
/*  769 */       .addComponent(this.cmbType, -2, -1, -2)));
/*      */     
/*      */ 
/*  772 */     GroupLayout jPanel14Layout = new GroupLayout(this.jPanel14);
/*  773 */     this.jPanel14.setLayout(jPanel14Layout);
/*  774 */     jPanel14Layout.setHorizontalGroup(jPanel14Layout
/*  775 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  776 */       .addGroup(jPanel14Layout.createSequentialGroup()
/*  777 */       .addComponent(this.jPanel2, -2, -1, -2)
/*  778 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  779 */       .addComponent(this.jPanel3, -2, -1, -2)
/*  780 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  781 */       .addComponent(this.jPanel4, -2, -1, -2)
/*  782 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  783 */       .addComponent(this.jPanel5, -2, -1, -2)
/*  784 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  785 */       .addComponent(this.jPanel6, -2, -1, -2)
/*  786 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  787 */       .addComponent(this.jPanel7, -2, -1, -2)
/*  788 */       .addContainerGap(-1, 32767)));
/*      */     
/*  790 */     jPanel14Layout.setVerticalGroup(jPanel14Layout
/*  791 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  792 */       .addGroup(jPanel14Layout.createSequentialGroup()
/*  793 */       .addGroup(jPanel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/*  794 */       .addComponent(this.jPanel6, -1, -1, 32767)
/*  795 */       .addComponent(this.jPanel4, 0, -1, 32767)
/*  796 */       .addComponent(this.jPanel3, 0, -1, 32767)
/*  797 */       .addComponent(this.jPanel2, -1, 35, 32767)
/*  798 */       .addComponent(this.jPanel5, -1, -1, 32767))
/*  799 */       .addGap(21, 21, 21))
/*  800 */       .addGroup(jPanel14Layout.createSequentialGroup()
/*  801 */       .addComponent(this.jPanel7, -1, 45, 32767)
/*  802 */       .addContainerGap()));
/*      */     
/*      */ 
/*  805 */     this.lblName.setText("Name");
/*      */     
/*  807 */     this.txtName.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  809 */         dlgOpen.this.txtNameActionPerformed(evt);
/*      */       }
/*  811 */     });
/*  812 */     this.txtName.addKeyListener(new java.awt.event.KeyAdapter() {
/*      */       public void keyReleased(KeyEvent evt) {
/*  814 */         dlgOpen.this.txtNameKeyReleased(evt);
/*      */       }
/*      */       
/*  817 */       public void keyTyped(KeyEvent evt) { dlgOpen.this.txtNameKeyTyped(evt);
/*      */       }
/*      */ 
/*  820 */     });
/*  821 */     GroupLayout jPanel13Layout = new GroupLayout(this.jPanel13);
/*  822 */     this.jPanel13.setLayout(jPanel13Layout);
/*  823 */     jPanel13Layout.setHorizontalGroup(jPanel13Layout
/*  824 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  825 */       .addComponent(this.txtName, -2, 131, -2)
/*  826 */       .addComponent(this.lblName));
/*      */     
/*  828 */     jPanel13Layout.setVerticalGroup(jPanel13Layout
/*  829 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  830 */       .addGroup(jPanel13Layout.createSequentialGroup()
/*  831 */       .addComponent(this.lblName)
/*  832 */       .addGap(4, 4, 4)
/*  833 */       .addComponent(this.txtName, -2, -1, -2)));
/*      */     
/*      */ 
/*  836 */     this.txtSource.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  838 */         dlgOpen.this.txtSourceActionPerformed(evt);
/*      */       }
/*  840 */     });
/*  841 */     this.txtSource.addKeyListener(new java.awt.event.KeyAdapter() {
/*      */       public void keyReleased(KeyEvent evt) {
/*  843 */         dlgOpen.this.txtSourceKeyReleased(evt);
/*      */       }
/*      */       
/*  846 */     });
/*  847 */     this.lblSource.setText("Source");
/*      */     
/*  849 */     GroupLayout jPanel12Layout = new GroupLayout(this.jPanel12);
/*  850 */     this.jPanel12.setLayout(jPanel12Layout);
/*  851 */     jPanel12Layout.setHorizontalGroup(jPanel12Layout
/*  852 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  853 */       .addComponent(this.txtSource, -2, 92, -2)
/*  854 */       .addComponent(this.lblSource));
/*      */     
/*  856 */     jPanel12Layout.setVerticalGroup(jPanel12Layout
/*  857 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  858 */       .addGroup(jPanel12Layout.createSequentialGroup()
/*  859 */       .addComponent(this.lblSource)
/*  860 */       .addGap(4, 4, 4)
/*  861 */       .addComponent(this.txtSource, -2, -1, -2)));
/*      */     
/*      */ 
/*  864 */     this.txtMaxBV.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  866 */         dlgOpen.this.Filter(evt);
/*      */       }
/*  868 */     });
/*  869 */     this.txtMaxBV.addFocusListener(new FocusAdapter() {
/*      */       public void focusLost(FocusEvent evt) {
/*  871 */         dlgOpen.this.txtMaxBVFocusLost(evt);
/*      */       }
/*      */       
/*  874 */     });
/*  875 */     this.lblBV.setText("Battle Value");
/*      */     
/*  877 */     this.txtMinBV.addFocusListener(new FocusAdapter() {
/*      */       public void focusLost(FocusEvent evt) {
/*  879 */         dlgOpen.this.txtMinBVFocusLost(evt);
/*      */       }
/*      */       
/*  882 */     });
/*  883 */     GroupLayout jPanel9Layout = new GroupLayout(this.jPanel9);
/*  884 */     this.jPanel9.setLayout(jPanel9Layout);
/*  885 */     jPanel9Layout.setHorizontalGroup(jPanel9Layout
/*  886 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  887 */       .addGroup(jPanel9Layout.createSequentialGroup()
/*  888 */       .addComponent(this.txtMinBV, -2, 36, -2)
/*  889 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  890 */       .addComponent(this.txtMaxBV, -2, 36, -2))
/*  891 */       .addComponent(this.lblBV));
/*      */     
/*  893 */     jPanel9Layout.setVerticalGroup(jPanel9Layout
/*  894 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  895 */       .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  896 */       .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  897 */       .addComponent(this.txtMaxBV, -2, -1, -2)
/*  898 */       .addComponent(this.txtMinBV, -2, -1, -2))
/*  899 */       .addGroup(jPanel9Layout.createSequentialGroup()
/*  900 */       .addComponent(this.lblBV)
/*  901 */       .addGap(24, 24, 24))));
/*      */     
/*      */ 
/*  904 */     this.lblTonnage1.setText("Year");
/*      */     
/*  906 */     this.txtMinYear.addFocusListener(new FocusAdapter() {
/*      */       public void focusLost(FocusEvent evt) {
/*  908 */         dlgOpen.this.txtMinYearFocusLost(evt);
/*      */       }
/*      */       
/*  911 */     });
/*  912 */     this.txtMaxYear.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  914 */         dlgOpen.this.txtMaxYearFilter(evt);
/*      */       }
/*  916 */     });
/*  917 */     this.txtMaxYear.addFocusListener(new FocusAdapter() {
/*      */       public void focusLost(FocusEvent evt) {
/*  919 */         dlgOpen.this.txtMaxYearFocusLost(evt);
/*      */       }
/*      */       
/*  922 */     });
/*  923 */     GroupLayout jPanel10Layout = new GroupLayout(this.jPanel10);
/*  924 */     this.jPanel10.setLayout(jPanel10Layout);
/*  925 */     jPanel10Layout.setHorizontalGroup(jPanel10Layout
/*  926 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  927 */       .addComponent(this.lblTonnage1)
/*  928 */       .addGroup(jPanel10Layout.createSequentialGroup()
/*  929 */       .addComponent(this.txtMinYear, -2, 36, -2)
/*  930 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  931 */       .addComponent(this.txtMaxYear, -2, 36, -2)));
/*      */     
/*  933 */     jPanel10Layout.setVerticalGroup(jPanel10Layout
/*  934 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  935 */       .addGroup(jPanel10Layout.createSequentialGroup()
/*  936 */       .addComponent(this.lblTonnage1)
/*  937 */       .addGap(4, 4, 4)
/*  938 */       .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  939 */       .addComponent(this.txtMaxYear, -2, -1, -2)
/*  940 */       .addComponent(this.txtMinYear, -2, -1, -2))));
/*      */     
/*      */ 
/*  943 */     this.lblTonnage.setText("Tonnage");
/*      */     
/*  945 */     this.txtMaxTon.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  947 */         dlgOpen.this.txtMaxTonFilter(evt);
/*      */       }
/*  949 */     });
/*  950 */     this.txtMaxTon.addFocusListener(new FocusAdapter() {
/*      */       public void focusLost(FocusEvent evt) {
/*  952 */         dlgOpen.this.txtMaxTonFocusLost(evt);
/*      */       }
/*      */       
/*  955 */     });
/*  956 */     this.txtMinTon.addFocusListener(new FocusAdapter() {
/*      */       public void focusLost(FocusEvent evt) {
/*  958 */         dlgOpen.this.txtMinTonFocusLost(evt);
/*      */       }
/*      */       
/*  961 */     });
/*  962 */     GroupLayout jPanel8Layout = new GroupLayout(this.jPanel8);
/*  963 */     this.jPanel8.setLayout(jPanel8Layout);
/*  964 */     jPanel8Layout.setHorizontalGroup(jPanel8Layout
/*  965 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  966 */       .addGroup(jPanel8Layout.createSequentialGroup()
/*  967 */       .addComponent(this.txtMinTon, -2, 36, -2)
/*  968 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  969 */       .addComponent(this.txtMaxTon, -2, 36, -2))
/*  970 */       .addComponent(this.lblTonnage));
/*      */     
/*  972 */     jPanel8Layout.setVerticalGroup(jPanel8Layout
/*  973 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  974 */       .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  975 */       .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  976 */       .addComponent(this.txtMaxTon, -2, -1, -2)
/*  977 */       .addComponent(this.txtMinTon, -2, -1, -2))
/*  978 */       .addGroup(jPanel8Layout.createSequentialGroup()
/*  979 */       .addComponent(this.lblTonnage)
/*  980 */       .addGap(24, 24, 24))));
/*      */     
/*      */ 
/*  983 */     this.txtMinCost.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  985 */         dlgOpen.this.txtMinCostFilter(evt);
/*      */       }
/*  987 */     });
/*  988 */     this.txtMinCost.addFocusListener(new FocusAdapter() {
/*      */       public void focusLost(FocusEvent evt) {
/*  990 */         dlgOpen.this.txtMinCostFocusLost(evt);
/*      */       }
/*      */       
/*  993 */     });
/*  994 */     this.lblCost.setText("C-Bill Cost");
/*      */     
/*  996 */     this.txtMaxCost.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  998 */         dlgOpen.this.txtMaxCostFilter(evt);
/*      */       }
/* 1000 */     });
/* 1001 */     this.txtMaxCost.addFocusListener(new FocusAdapter() {
/*      */       public void focusLost(FocusEvent evt) {
/* 1003 */         dlgOpen.this.txtMaxCostFocusLost(evt);
/*      */       }
/*      */       
/* 1006 */     });
/* 1007 */     GroupLayout jPanel11Layout = new GroupLayout(this.jPanel11);
/* 1008 */     this.jPanel11.setLayout(jPanel11Layout);
/* 1009 */     jPanel11Layout.setHorizontalGroup(jPanel11Layout
/* 1010 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1011 */       .addGroup(jPanel11Layout.createSequentialGroup()
/* 1012 */       .addComponent(this.txtMinCost, -2, 80, -2)
/* 1013 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1014 */       .addComponent(this.txtMaxCost, -2, 80, -2))
/* 1015 */       .addComponent(this.lblCost));
/*      */     
/* 1017 */     jPanel11Layout.setVerticalGroup(jPanel11Layout
/* 1018 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1019 */       .addGroup(jPanel11Layout.createSequentialGroup()
/* 1020 */       .addComponent(this.lblCost)
/* 1021 */       .addGap(4, 4, 4)
/* 1022 */       .addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 1023 */       .addComponent(this.txtMinCost, -2, -1, -2)
/* 1024 */       .addComponent(this.txtMaxCost, -2, -1, -2))));
/*      */     
/*      */ 
/* 1027 */     this.chkOmni.setText("Omni Only");
/* 1028 */     this.chkOmni.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent evt) {
/* 1030 */         dlgOpen.this.chkOmniActionPerformed(evt);
/*      */       }
/*      */       
/* 1033 */     });
/* 1034 */     GroupLayout jPanel15Layout = new GroupLayout(this.jPanel15);
/* 1035 */     this.jPanel15.setLayout(jPanel15Layout);
/* 1036 */     jPanel15Layout.setHorizontalGroup(jPanel15Layout
/* 1037 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1038 */       .addGroup(jPanel15Layout.createSequentialGroup()
/* 1039 */       .addComponent(this.jPanel8, -2, -1, -2)
/* 1040 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 1041 */       .addComponent(this.jPanel9, -2, -1, -2)
/* 1042 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 1043 */       .addComponent(this.jPanel10, -2, -1, -2)
/* 1044 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 1045 */       .addComponent(this.jPanel11, -2, -1, -2)
/* 1046 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 1047 */       .addComponent(this.jPanel12, -2, -1, -2)
/* 1048 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 1049 */       .addComponent(this.jPanel13, -2, -1, -2)
/* 1050 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 1051 */       .addComponent(this.chkOmni)
/* 1052 */       .addContainerGap(-1, 32767)));
/*      */     
/* 1054 */     jPanel15Layout.setVerticalGroup(jPanel15Layout
/* 1055 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1056 */       .addGroup(jPanel15Layout.createSequentialGroup()
/* 1057 */       .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1058 */       .addComponent(this.jPanel8, -2, -1, -2)
/* 1059 */       .addComponent(this.jPanel9, -2, -1, -2)
/* 1060 */       .addComponent(this.jPanel10, -1, -1, 32767)
/* 1061 */       .addComponent(this.jPanel11, -2, -1, -2)
/* 1062 */       .addComponent(this.jPanel12, -2, -1, -2)
/* 1063 */       .addComponent(this.jPanel13, -2, -1, -2)
/* 1064 */       .addGroup(jPanel15Layout.createSequentialGroup()
/* 1065 */       .addContainerGap()
/* 1066 */       .addComponent(this.chkOmni)))
/* 1067 */       .addContainerGap()));
/*      */     
/*      */ 
/* 1070 */     GroupLayout pnlFiltersLayout = new GroupLayout(this.pnlFilters);
/* 1071 */     this.pnlFilters.setLayout(pnlFiltersLayout);
/* 1072 */     pnlFiltersLayout.setHorizontalGroup(pnlFiltersLayout
/* 1073 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1074 */       .addGroup(pnlFiltersLayout.createSequentialGroup()
/* 1075 */       .addGroup(pnlFiltersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1076 */       .addComponent(this.jPanel14, -2, -1, -2)
/* 1077 */       .addComponent(this.jPanel15, -2, -1, -2))
/* 1078 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 1079 */       .addComponent(this.jPanel1, -2, -1, -2)
/* 1080 */       .addContainerGap(165, 32767)));
/*      */     
/* 1082 */     pnlFiltersLayout.setVerticalGroup(pnlFiltersLayout
/* 1083 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1084 */       .addGroup(pnlFiltersLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1085 */       .addGroup(GroupLayout.Alignment.TRAILING, pnlFiltersLayout.createSequentialGroup()
/* 1086 */       .addComponent(this.jPanel14, -2, 36, -2)
/* 1087 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1088 */       .addComponent(this.jPanel15, -2, -1, -2))
/* 1089 */       .addGroup(GroupLayout.Alignment.TRAILING, pnlFiltersLayout.createSequentialGroup()
/* 1090 */       .addComponent(this.jPanel1, -2, -1, -2)
/* 1091 */       .addGap(19, 19, 19))));
/*      */     
/*      */ 
/* 1094 */     this.lblStatus.setText("Loading Mechs....");
/*      */     
/* 1096 */     this.prgResaving.setStringPainted(true);
/*      */     
/* 1098 */     this.lblShowing.setHorizontalAlignment(4);
/* 1099 */     this.lblShowing.setText("Showing 0 of 0");
/*      */     
/* 1101 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 1102 */     getContentPane().setLayout(layout);
/* 1103 */     layout.setHorizontalGroup(layout
/* 1104 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1105 */       .addComponent(this.tlbActions, -1, 1024, 32767)
/* 1106 */       .addGroup(layout.createSequentialGroup()
/* 1107 */       .addContainerGap()
/* 1108 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1109 */       .addComponent(this.spnMechTable, GroupLayout.Alignment.TRAILING, -1, 1004, 32767)
/* 1110 */       .addComponent(this.pnlFilters, -1, -1, 32767)
/* 1111 */       .addGroup(layout.createSequentialGroup()
/* 1112 */       .addComponent(this.lblStatus, -2, 525, -2)
/* 1113 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 323, 32767)
/* 1114 */       .addComponent(this.lblShowing, -2, 156, -2))
/* 1115 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 1116 */       .addComponent(this.txtSelected, -2, 798, -2)
/* 1117 */       .addGap(18, 18, 18)
/* 1118 */       .addComponent(this.prgResaving, -2, 188, -2)))
/* 1119 */       .addContainerGap()));
/*      */     
/* 1121 */     layout.setVerticalGroup(layout
/* 1122 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1123 */       .addGroup(layout.createSequentialGroup()
/* 1124 */       .addComponent(this.tlbActions, -2, 25, -2)
/* 1125 */       .addGap(9, 9, 9)
/* 1126 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 1127 */       .addComponent(this.prgResaving, -2, -1, -2)
/* 1128 */       .addComponent(this.txtSelected, -2, 14, -2))
/* 1129 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1130 */       .addComponent(this.spnMechTable, -1, 394, 32767)
/* 1131 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1132 */       .addComponent(this.pnlFilters, -2, -1, -2)
/* 1133 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1134 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 1135 */       .addComponent(this.lblStatus, -2, 14, -2)
/* 1136 */       .addComponent(this.lblShowing))));
/*      */     
/*      */ 
/* 1139 */     pack();
/*      */   }
/*      */   
/*      */   private JLabel lblStatus;
/*      */   
/*      */   private void tblMechDataMouseClicked(MouseEvent evt)
/*      */   {
/* 1143 */     if (evt.getClickCount() == 2) {
/* 1144 */       LoadMech();
/*      */     } else {
/* 1146 */       checkSelection();
/*      */     }
/*      */   }
/*      */   
/*      */   private JLabel lblTech;
/*      */   
/*      */   private void btnOpenActionPerformed(ActionEvent evt)
/*      */   {
/* 1151 */     LoadMech();
/*      */   }
/*      */   
/*      */   private JLabel lblTonnage;
/*      */   private void formWindowOpened(WindowEvent evt) {}
/*      */   private JLabel lblTonnage1;
/*      */   private JLabel lblType;
/*      */   private JPanel pnlFilters;
/*      */   private JProgressBar prgResaving;
/*      */   
/*      */   private void btnOptionsActionPerformed(ActionEvent evt)
/*      */   {
/* 1159 */     dlgPrefs preferences = new dlgPrefs((JFrame)this.parent, true);
/* 1160 */     preferences.setLocationRelativeTo(this);
/* 1161 */     preferences.setVisible(true);
/* 1162 */     setVisible(true);
/* 1163 */     this.parent.GetLoadoutRenderer().Reset();
/* 1164 */     LoadList();
/*      */   }
/*      */   
/*      */   private JScrollPane spnMechTable;
/*      */   
/*      */   private void btnRefreshActionPerformed(ActionEvent evt)
/*      */   {
/* 1168 */     LoadList(false);
/*      */     
/* 1170 */     setVisible(true); }
/*      */   
/*      */   private JTable tblMechData;
/*      */   
/* 1174 */   private void btnPrintActionPerformed(ActionEvent evt) { if (this.tblMechData.getSelectedRowCount() > 0) {
/* 1175 */       Printer print = new Printer();
/*      */       
/*      */       try
/*      */       {
/* 1179 */         MechReader read = new MechReader();
/* 1180 */         int[] rows = this.tblMechData.getSelectedRows();
/* 1181 */         for (int i = 0; i < rows.length; i++) {
/* 1182 */           UnitListData data = this.list.Get(this.tblMechData.convertRowIndexToModel(rows[i]));
/* 1183 */           Mech m = read.ReadMech(this.list.getDirectory() + data.getFilename(), this.parent.GetData());
/* 1184 */           if (data.isOmni()) {
/* 1185 */             m.SetCurLoadout(data.getConfig());
/*      */           }
/* 1187 */           print.AddMech(m);
/*      */         }
/* 1189 */         print.Print();
/* 1190 */         this.tblMechData.clearSelection();
/* 1191 */         checkSelection();
/*      */       } catch (Exception e) {}
/*      */     } }
/*      */   
/*      */   private JToolBar tlbActions;
/*      */   private JTextField txtMaxBV;
/*      */   private JTextField txtMaxCost;
/*      */   private JTextField txtMaxTon;
/*      */   private JTextField txtMaxYear;
/*      */   private JTextField txtMinBV;
/*      */   
/* 1202 */   private void btnMagicActionPerformed(ActionEvent evt) { batchUpdateMechs(); }
/*      */   
/*      */   private JTextField txtMinCost;
/*      */   private JTextField txtMinTon;
/*      */   
/* 1207 */   private void Filter(ActionEvent evt) { ListFilter filters = new ListFilter();
/* 1208 */     filters.setExtension(".ssw");
/*      */     
/* 1210 */     if (this.cmbTech.getSelectedIndex() > 0) filters.setTech(this.cmbTech.getSelectedItem().toString());
/* 1211 */     if (this.cmbEra.getSelectedIndex() > 0) filters.setEra(this.cmbEra.getSelectedItem().toString());
/* 1212 */     if (this.cmbType.getSelectedIndex() > 0) filters.setType(this.cmbType.getSelectedItem().toString());
/* 1213 */     if (this.cmbMotive.getSelectedIndex() > 0) filters.setMotive(this.cmbMotive.getSelectedItem().toString());
/* 1214 */     if (this.cmbRulesLevel.getSelectedIndex() > 0) filters.setLevel(this.cmbRulesLevel.getSelectedItem().toString());
/* 1215 */     if (this.cmbMinMP.getSelectedIndex() > 0) filters.setMinMP(Integer.parseInt(this.cmbMinMP.getSelectedItem().toString()));
/* 1216 */     if (!this.txtMinBV.getText().isEmpty()) {
/* 1217 */       if (this.txtMaxBV.getText().isEmpty()) {
/* 1218 */         filters.setBV(0, Integer.parseInt(this.txtMinBV.getText()));
/*      */       } else {
/* 1220 */         filters.setBV(Integer.parseInt(this.txtMinBV.getText()), Integer.parseInt(this.txtMaxBV.getText()));
/*      */       }
/*      */     }
/* 1223 */     if (!this.txtMinCost.getText().isEmpty()) {
/* 1224 */       if (this.txtMaxCost.getText().isEmpty()) {
/* 1225 */         filters.setCost(0.0D, Double.parseDouble(this.txtMinCost.getText()));
/*      */       } else {
/* 1227 */         filters.setCost(Double.parseDouble(this.txtMinCost.getText()), Double.parseDouble(this.txtMaxCost.getText()));
/*      */       }
/*      */     }
/* 1230 */     if (!this.txtMinTon.getText().isEmpty()) {
/* 1231 */       if (this.txtMaxTon.getText().isEmpty()) {
/* 1232 */         filters.setTonnage(20, Integer.parseInt(this.txtMinTon.getText()));
/*      */       } else {
/* 1234 */         filters.setTonnage(Integer.parseInt(this.txtMinTon.getText()), Integer.parseInt(this.txtMaxTon.getText()));
/*      */       }
/*      */     }
/* 1237 */     if (!this.txtMinYear.getText().isEmpty()) {
/* 1238 */       if (this.txtMaxYear.getText().isEmpty()) {
/* 1239 */         filters.setYear(Integer.parseInt(this.txtMinYear.getText()), Integer.parseInt(this.txtMinYear.getText()));
/*      */       } else {
/* 1241 */         filters.setYear(Integer.parseInt(this.txtMinYear.getText()), Integer.parseInt(this.txtMaxYear.getText()));
/*      */       }
/*      */     }
/* 1244 */     if (!this.txtName.getText().isEmpty()) filters.setName(this.txtName.getText().trim());
/* 1245 */     if (!this.txtSource.getText().isEmpty()) filters.setSource(this.txtSource.getText().trim());
/* 1246 */     filters.setIsOmni(this.chkOmni.isSelected());
/*      */     
/* 1248 */     UnitList filtered = this.list.Filter(filters);
/* 1249 */     setupList(filtered, false);
/*      */   }
/*      */   
/*      */   private JTextField txtMinYear;
/*      */   private JTextField txtName;
/*      */   private JLabel txtSelected;
/*      */   private JTextField txtSource;
/*      */   private void txtMinCostFilter(ActionEvent evt) {}
/*      */   
/*      */   private void txtMaxCostFilter(ActionEvent evt) {}
/*      */   
/*      */   private void btnClearFilterFilter(ActionEvent evt) {
/* 1261 */     setupList(this.list, false);
/*      */     
/*      */ 
/* 1264 */     this.cmbEra.setSelectedIndex(0);
/* 1265 */     this.cmbMotive.setSelectedIndex(0);
/* 1266 */     this.cmbRulesLevel.setSelectedIndex(0);
/* 1267 */     this.cmbTech.setSelectedIndex(0);
/* 1268 */     this.cmbType.setSelectedIndex(0);
/* 1269 */     this.cmbMinMP.setSelectedIndex(0);
/*      */     
/*      */ 
/* 1272 */     this.txtMinBV.setText("");
/* 1273 */     this.txtMaxBV.setText("");
/* 1274 */     this.txtMinCost.setText("");
/* 1275 */     this.txtMaxCost.setText("");
/* 1276 */     this.txtMinTon.setText("");
/* 1277 */     this.txtMaxTon.setText("");
/* 1278 */     this.txtMinYear.setText("");
/* 1279 */     this.txtMaxYear.setText("");
/* 1280 */     this.txtName.setText("");
/* 1281 */     this.chkOmni.setSelected(false);
/*      */   }
/*      */   
/*      */   private void btnAdd2ForceActionPerformed(ActionEvent evt)
/*      */   {
/* 1286 */     if (this.tblMechData.getSelectedRowCount() > 0) {
/* 1287 */       int[] rows = this.tblMechData.getSelectedRows();
/* 1288 */       UnitListData data; for (int i = 0; i < rows.length; i++) {
/* 1289 */         data = (UnitListData)((abView)this.tblMechData.getModel()).Get(this.tblMechData.convertRowIndexToModel(rows[i]));
/* 1290 */         this.parent.GetForceDialogue().getForce().AddUnit(new Unit(data));
/* 1291 */         this.parent.GetForceDialogue().getForce().RefreshBV();
/* 1292 */         this.lblForce.setText(this.lblForce.getText() + " " + data.getFullName() + " added;");
/*      */       }
/* 1294 */       this.btnViewForce.setEnabled(true);
/* 1295 */       String forceList = this.parent.GetForceDialogue().getForce().getUnits().size() + " Units Selected: ";
/* 1296 */       for (Unit u : this.parent.GetForceDialogue().getForce().getUnits()) {
/* 1297 */         forceList = forceList + " " + u.TypeModel;
/*      */       }
/* 1299 */       this.lblForce.setText(this.parent.GetForceDialogue().getForce().getUnits().size() + " Units");
/* 1300 */       this.lblForce.setToolTipText(forceList);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void txtMaxTonFilter(ActionEvent evt)
/*      */   {
/* 1307 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void cmbRulesLevelFilter(ActionEvent evt) {
/* 1311 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtNameActionPerformed(ActionEvent evt) {
/* 1315 */     Filter(null);
/*      */   }
/*      */   
/*      */ 
/*      */   private void txtNameKeyTyped(KeyEvent evt) {}
/*      */   
/*      */   private void cmbTypeFilter(ActionEvent evt)
/*      */   {
/* 1323 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void cmbMotiveFilter(ActionEvent evt) {
/* 1327 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void btnChangeDirActionPerformed(ActionEvent evt) {
/* 1331 */     this.dirPath = this.media.GetDirectorySelection((JFrame)this.parent, this.dirPath);
/* 1332 */     setVisible(true);
/* 1333 */     this.parent.GetPrefs().put("ListPath", this.dirPath);
/* 1334 */     LoadList(false);
/*      */   }
/*      */   
/*      */   private void chkOmniActionPerformed(ActionEvent evt) {
/* 1338 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtSourceActionPerformed(ActionEvent evt) {
/* 1342 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtSourceKeyReleased(KeyEvent evt) {
/* 1346 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void cmbMinMPFilter(ActionEvent evt) {
/* 1350 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void tblMechDataMouseMoved(MouseEvent evt) {
/* 1354 */     setTooltip((UnitListData)((abView)this.tblMechData.getModel()).Get(this.tblMechData.convertRowIndexToModel(this.tblMechData.rowAtPoint(evt.getPoint()))));
/*      */   }
/*      */   
/*      */   private void tblMechDataKeyReleased(KeyEvent evt) {
/* 1358 */     String entered = this.txtName.getText();
/*      */     
/* 1360 */     switch (evt.getKeyCode()) {
/*      */     case 8: 
/* 1362 */       if (!entered.isEmpty()) entered = entered.substring(0, entered.length() - 1);
/*      */       break;
/*      */     case 127: 
/* 1365 */       entered = "";
/* 1366 */       break;
/*      */     case 10: 
/* 1368 */       if (((abView)this.tblMechData.getModel()).list.Size() == 1) {
/* 1369 */         this.tblMechData.selectAll();
/* 1370 */         LoadMech();
/*      */       }
/*      */       break;
/*      */     case 16: 
/*      */     case 17: 
/*      */     case 37: 
/*      */     case 38: 
/*      */     case 39: 
/*      */     case 40: 
/* 1379 */       return;
/*      */     default: 
/* 1381 */       if ((evt.getKeyCode() == 32) || ((evt.getKeyCode() >= 45) && (evt.getKeyCode() <= 90)))
/* 1382 */         entered = entered + evt.getKeyChar();
/*      */       break;
/*      */     }
/* 1385 */     this.txtName.setText(entered);
/* 1386 */     txtNameKeyReleased(evt);
/*      */   }
/*      */   
/*      */   private void btnViewForceActionPerformed(ActionEvent evt) {
/* 1390 */     this.lblForce.setText("");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1395 */     this.parent.GetForceDialogue().setLocationRelativeTo(this);
/* 1396 */     this.parent.GetForceDialogue().setVisible(true);
/*      */   }
/*      */   
/*      */   private void formWindowClosed(WindowEvent evt) {
/* 1400 */     this.lblForce.setText("");
/*      */   }
/*      */   
/*      */ 
/*      */   private void formWindowGainedFocus(WindowEvent evt) {}
/*      */   
/*      */   private void cmbViewActionPerformed(ActionEvent evt)
/*      */   {
/* 1408 */     switch (this.cmbView.getSelectedIndex()) {
/*      */     case 0: 
/* 1410 */       this.currentView = new list.view.tbTotalWarfareView(this.list);
/* 1411 */       break;
/*      */     case 1: 
/* 1413 */       this.currentView = new list.view.tbTotalWarfareCompact(this.list);
/* 1414 */       break;
/*      */     case 2: 
/* 1416 */       this.currentView = new list.view.tbBattleForceView(this.list);
/* 1417 */       break;
/*      */     case 3: 
/* 1419 */       this.currentView = new list.view.tbChatInformation(this.list);
/* 1420 */       break;
/*      */     default: 
/* 1422 */       this.currentView = new list.view.tbTotalWarfareView(this.list);
/*      */     }
/* 1424 */     this.tblMechData.setModel(this.currentView);
/* 1425 */     this.currentView.setupTable(this.tblMechData);
/*      */   }
/*      */   
/*      */   private void txtNameKeyReleased(KeyEvent evt) {
/* 1429 */     Filter(null);
/*      */   }
/*      */   
/*      */ 
/*      */   private void txtMaxYearFilter(ActionEvent evt) {}
/*      */   
/*      */   private void txtMinTonFocusLost(FocusEvent evt)
/*      */   {
/* 1437 */     if (this.txtMaxTon.getText().isEmpty()) { this.txtMaxTon.setText(this.txtMinTon.getText());this.txtMaxTon.selectAll(); }
/* 1438 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtMinBVFocusLost(FocusEvent evt) {
/* 1442 */     if (this.txtMaxBV.getText().isEmpty()) { this.txtMaxBV.setText(this.txtMinBV.getText());this.txtMaxBV.selectAll(); }
/* 1443 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtMaxTonFocusLost(FocusEvent evt) {
/* 1447 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtMaxBVFocusLost(FocusEvent evt) {
/* 1451 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtMinYearFocusLost(FocusEvent evt) {
/* 1455 */     if (this.txtMaxYear.getText().isEmpty()) { this.txtMaxYear.setText(this.txtMinYear.getText());this.txtMaxYear.selectAll(); }
/* 1456 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtMaxYearFocusLost(FocusEvent evt) {
/* 1460 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtMinCostFocusLost(FocusEvent evt) {
/* 1464 */     if (this.txtMaxCost.getText().isEmpty()) { this.txtMaxCost.setText(this.txtMinCost.getText());this.txtMaxCost.selectAll(); }
/* 1465 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void txtMaxCostFocusLost(FocusEvent evt) {
/* 1469 */     Filter(null);
/*      */   }
/*      */   
/*      */   private void btnExportActionPerformed(ActionEvent evt) {
/* 1473 */     filehandlers.TXTWriter out = new filehandlers.TXTWriter();
/* 1474 */     String dir = "";
/* 1475 */     dir = this.media.GetDirectorySelection(this, this.parent.GetPrefs().get("ListDirectory", ""));
/* 1476 */     if (dir.isEmpty()) {
/* 1477 */       return;
/*      */     }
/*      */     
/* 1480 */     this.parent.GetPrefs().put("ListDirectory", dir);
/*      */     try {
/* 1482 */       out.WriteList(dir + File.separator + "MechListing.csv", ((abView)this.tblMechData.getModel()).list);
/* 1483 */       Media.Messager(((abView)this.tblMechData.getModel()).list.Size() + " Mechs output to " + dir + File.separator + "MechListing.csv");
/*      */     }
/*      */     catch (IOException ex) {
/* 1486 */       System.out.println(ex.getMessage());
/* 1487 */       Media.Messager("Unable to output list\n" + ex.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */   public void setVisible(boolean b)
/*      */   {
/* 1493 */     super.setVisible(b);
/* 1494 */     this.cancelledListDirSelection = false;
/* 1495 */     if (this.list.Size() == 0) LoadList();
/* 1496 */     Filter(null);
/*      */   }
/*      */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgOpen.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */