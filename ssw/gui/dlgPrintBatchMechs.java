/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Mech;
/*     */ import filehandlers.MechReader;
/*     */ import filehandlers.Media;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Image;
/*     */ import java.awt.MediaTracker;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.File;
/*     */ import java.util.Vector;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ import ssw.print.Printer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgPrintBatchMechs
/*     */   extends JDialog
/*     */ {
/*     */   private ifMechForm parent;
/*     */   private Vector<mechData> mechList;
/*     */   
/*     */   private class mechData
/*     */   {
/*     */     public String name;
/*     */     public Mech m;
/*     */     public dlgPrintSavedMechOptions POptions;
/*     */     
/*     */     public mechData(String name, Mech m, dlgPrintSavedMechOptions POptions)
/*     */     {
/*  51 */       this.name = name;
/*  52 */       this.m = m;
/*  53 */       this.POptions = POptions;
/*     */     }
/*     */     
/*     */     public mechData(String name, Mech m) {
/*  57 */       this.name = name;
/*  58 */       this.m = m;
/*  59 */       this.POptions = new dlgPrintSavedMechOptions((JFrame)dlgPrintBatchMechs.this.parent, true, m);
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/*  64 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  71 */   public boolean isPrinted = false;
/*     */   private JButton btnAddMech;
/*     */   private JButton btnCancel;
/*     */   
/*  75 */   public dlgPrintBatchMechs(Frame parent, boolean modal) { super(parent, modal);
/*  76 */     this.parent = ((ifMechForm)parent);
/*  77 */     initComponents();
/*  78 */     this.mechList = new Vector();
/*     */   }
/*     */   
/*     */   private JButton btnClear;
/*     */   private JButton btnMechDetails;
/*     */   private JButton btnPrintAll;
/*     */   private JButton btnRemoveMech;
/*     */   private JButton jButton1;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JList lstChoosenMechs;
/*     */   private void initComponents()
/*     */   {
/*  90 */     this.jButton1 = new JButton();
/*  91 */     this.jScrollPane1 = new JScrollPane();
/*  92 */     this.lstChoosenMechs = new JList();
/*  93 */     this.btnRemoveMech = new JButton();
/*  94 */     this.btnPrintAll = new JButton();
/*  95 */     this.btnAddMech = new JButton();
/*  96 */     this.btnMechDetails = new JButton();
/*  97 */     this.btnCancel = new JButton();
/*  98 */     this.btnClear = new JButton();
/*     */     
/* 100 */     this.jButton1.setText("Cancel");
/*     */     
/* 102 */     setDefaultCloseOperation(2);
/* 103 */     setTitle("Print Multiple Mechs");
/*     */     
/* 105 */     this.lstChoosenMechs.setSelectionMode(0);
/* 106 */     this.lstChoosenMechs.addMouseListener(new MouseAdapter() {
/*     */       public void mouseClicked(MouseEvent evt) {
/* 108 */         dlgPrintBatchMechs.this.lstChoosenMechsMouseClicked(evt);
/*     */       }
/* 110 */     });
/* 111 */     this.jScrollPane1.setViewportView(this.lstChoosenMechs);
/*     */     
/* 113 */     this.btnRemoveMech.setText("Remove Mech");
/* 114 */     this.btnRemoveMech.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 116 */         dlgPrintBatchMechs.this.btnRemoveMechActionPerformed(evt);
/*     */       }
/*     */       
/* 119 */     });
/* 120 */     this.btnPrintAll.setText("Print");
/* 121 */     this.btnPrintAll.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 123 */         dlgPrintBatchMechs.this.btnPrintAllActionPerformed(evt);
/*     */       }
/*     */       
/* 126 */     });
/* 127 */     this.btnAddMech.setText("Add Mech(s)");
/* 128 */     this.btnAddMech.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 130 */         dlgPrintBatchMechs.this.btnAddMechActionPerformed(evt);
/*     */       }
/*     */       
/* 133 */     });
/* 134 */     this.btnMechDetails.setText("Mech Details");
/* 135 */     this.btnMechDetails.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 137 */         dlgPrintBatchMechs.this.btnMechDetailsActionPerformed(evt);
/*     */       }
/*     */       
/* 140 */     });
/* 141 */     this.btnCancel.setText("Cancel");
/* 142 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 144 */         dlgPrintBatchMechs.this.btnCancelActionPerformed(evt);
/*     */       }
/*     */       
/* 147 */     });
/* 148 */     this.btnClear.setText("Clear List");
/* 149 */     this.btnClear.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 151 */         dlgPrintBatchMechs.this.btnClearActionPerformed(evt);
/*     */       }
/*     */       
/* 154 */     });
/* 155 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 156 */     getContentPane().setLayout(layout);
/* 157 */     layout.setHorizontalGroup(layout
/* 158 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 159 */       .addGroup(layout.createSequentialGroup()
/* 160 */       .addContainerGap()
/* 161 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 162 */       .addComponent(this.jScrollPane1, -1, 301, 32767)
/* 163 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 164 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 165 */       .addComponent(this.btnPrintAll, -1, 93, 32767)
/* 166 */       .addComponent(this.btnAddMech, GroupLayout.Alignment.TRAILING, -1, -1, 32767))
/* 167 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 168 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/* 169 */       .addComponent(this.btnClear, -1, -1, 32767)
/* 170 */       .addComponent(this.btnMechDetails, -1, -1, 32767))
/* 171 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 172 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/* 173 */       .addComponent(this.btnRemoveMech, -1, -1, 32767)
/* 174 */       .addComponent(this.btnCancel, -1, -1, 32767))))
/* 175 */       .addContainerGap()));
/*     */     
/* 177 */     layout.setVerticalGroup(layout
/* 178 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 179 */       .addGroup(layout.createSequentialGroup()
/* 180 */       .addContainerGap()
/* 181 */       .addComponent(this.jScrollPane1, -2, 252, -2)
/* 182 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 183 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 184 */       .addComponent(this.btnRemoveMech)
/* 185 */       .addComponent(this.btnMechDetails)
/* 186 */       .addComponent(this.btnAddMech))
/* 187 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/* 188 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 189 */       .addComponent(this.btnPrintAll)
/* 190 */       .addComponent(this.btnCancel)
/* 191 */       .addComponent(this.btnClear))
/* 192 */       .addContainerGap()));
/*     */     
/*     */ 
/* 195 */     pack();
/*     */   }
/*     */   
/*     */   private void btnAddMechActionPerformed(ActionEvent evt) {
/* 199 */     File[] files = SelectMechs();
/* 200 */     if (files.length > 0)
/*     */     {
/* 202 */       for (int i = 0; i <= files.length - 1; i++) {
/* 203 */         Mech m = LoadMechFromFile(files[i]);
/* 204 */         this.mechList.add(new mechData(BuildMechName(m), m));
/* 205 */         this.lstChoosenMechs.setListData(this.mechList);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void btnPrintAllActionPerformed(ActionEvent evt) {
/* 211 */     Printer printer = new Printer(this.parent);
/* 212 */     for (int i = 0; i < this.mechList.size(); i++) {
/* 213 */       mechData current = (mechData)this.mechList.get(i);
/* 214 */       printer.AddMech(current.m, current.POptions.GetWarriorName(), current.POptions.GetGunnery(), current.POptions.GetPiloting());
/*     */     }
/* 216 */     printer.Print();
/*     */     
/*     */ 
/* 219 */     btnClearActionPerformed(null);
/* 220 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnMechDetailsActionPerformed(ActionEvent evt) {
/* 224 */     mechData selected = (mechData)this.lstChoosenMechs.getSelectedValue();
/* 225 */     if (selected == null) {
/* 226 */       return;
/*     */     }
/* 228 */     dlgPrintSavedMechOptions POptions = selected.POptions;
/* 229 */     Mech m = selected.m;
/* 230 */     POptions.setLocationRelativeTo((JFrame)this.parent);
/* 231 */     POptions.setVisible(true);
/* 232 */     selected.name = BuildMechName(m, POptions);
/*     */   }
/*     */   
/*     */   private void btnRemoveMechActionPerformed(ActionEvent evt) {
/* 236 */     mechData selected = (mechData)this.lstChoosenMechs.getSelectedValue();
/* 237 */     if (selected == null) {
/* 238 */       return;
/*     */     }
/* 240 */     this.mechList.remove(selected);
/* 241 */     this.lstChoosenMechs.setListData(this.mechList);
/*     */   }
/*     */   
/*     */   private void btnCancelActionPerformed(ActionEvent evt) {
/* 245 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void lstChoosenMechsMouseClicked(MouseEvent evt) {
/* 249 */     if (evt.getClickCount() >= 2) {
/* 250 */       this.parent.setMech(((mechData)this.lstChoosenMechs.getSelectedValue()).m);
/* 251 */       setVisible(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void btnClearActionPerformed(ActionEvent evt) {
/* 256 */     this.mechList.removeAllElements();
/* 257 */     this.lstChoosenMechs.setListData(this.mechList);
/*     */   }
/*     */   
/*     */   public Image GetImage(String filename) {
/* 261 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 262 */     Image retval = toolkit.getImage(filename);
/* 263 */     MediaTracker mediaTracker = new MediaTracker(this);
/* 264 */     mediaTracker.addImage(retval, 0);
/*     */     try {
/* 266 */       mediaTracker.waitForID(0);
/*     */     } catch (InterruptedException ie) {
/* 268 */       Media.Messager(this, "Interrupted while loading image " + filename);
/* 269 */       return null;
/*     */     }
/* 271 */     return retval;
/*     */   }
/*     */   
/*     */   private String BuildMechName(Mech m, dlgPrintSavedMechOptions po) {
/* 275 */     String name = m.GetFullName();
/* 276 */     if (po.PrintPilot()) {
/* 277 */       name = name + " [" + po.GetWarriorName() + "] [" + po.GetGunnery() + "/" + po.GetPiloting() + "]";
/*     */     }
/* 279 */     return name.replace(" []", "");
/*     */   }
/*     */   
/*     */   private String BuildMechName(Mech m) {
/* 283 */     return m.GetFullName() + " [4/5]";
/*     */   }
/*     */   
/*     */   private File[] SelectMechs() {
/* 287 */     File[] files = null;
/* 288 */     File tempFile = new File(this.parent.GetPrefs().get("LastOpenDirectory", ""));
/* 289 */     JFileChooser fc = new JFileChooser();
/* 290 */     fc.setMultiSelectionEnabled(true);
/* 291 */     fc.addChoosableFileFilter(new FileFilter() {
/*     */       public boolean accept(File f) {
/* 293 */         if (f.isDirectory()) {
/* 294 */           return true;
/*     */         }
/*     */         
/* 297 */         String extension = Utils.getExtension(f);
/* 298 */         if (extension != null) {
/* 299 */           if (extension.equals("ssw")) {
/* 300 */             return true;
/*     */           }
/* 302 */           return false;
/*     */         }
/*     */         
/* 305 */         return false;
/*     */       }
/*     */       
/*     */       public String getDescription()
/*     */       {
/* 310 */         return "*.ssw";
/*     */       }
/* 312 */     });
/* 313 */     fc.setAcceptAllFileFilterUsed(false);
/* 314 */     fc.setCurrentDirectory(tempFile);
/* 315 */     int returnVal = fc.showDialog(this, "Select Mech(s)");
/* 316 */     if (returnVal == 0) {
/* 317 */       files = fc.getSelectedFiles();
/*     */     }
/* 319 */     return files;
/*     */   }
/*     */   
/*     */   private Mech LoadMechFromFile(File file) {
/* 323 */     Mech m = null;
/*     */     try {
/* 325 */       MechReader XMLr = new MechReader();
/* 326 */       m = XMLr.ReadMech(file.getCanonicalPath(), this.parent.GetData());
/*     */     }
/*     */     catch (Exception e) {
/* 329 */       Media.Messager(this, e.getMessage());
/*     */     }
/* 331 */     return m;
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgPrintBatchMechs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */