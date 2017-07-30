/*     */ package ssw.gui;
/*     */ 
/*     */ import filehandlers.MechWriter;
/*     */ import filehandlers.Media;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.io.File;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JTextPane;
/*     */ import ssw.filehandlers.HMPReader;
/*     */ 
/*     */ public class dlgBatchHMP extends JDialog implements java.beans.PropertyChangeListener
/*     */ {
/*     */   private JButton btnClose;
/*     */   private JButton btnDestination;
/*     */   private JButton btnImport;
/*     */   private JButton btnSource;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JProgressBar prgImporting;
/*     */   private JTextField txtDestination;
/*     */   private JTextPane txtMessages;
/*     */   private JTextField txtSource;
/*     */   
/*     */   public dlgBatchHMP(java.awt.Frame parent, boolean modal)
/*     */   {
/*  45 */     super(parent, modal);
/*  46 */     initComponents();
/*  47 */     setTitle("Import Multiple HMP Files");
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent e) {
/*  51 */     this.prgImporting.setValue(((Importer)e.getSource()).getProgress());
/*     */   }
/*     */   
/*     */   private class Importer extends javax.swing.SwingWorker<Void, Void> {
/*     */     dlgBatchHMP Owner;
/*     */     
/*  57 */     public Importer(dlgBatchHMP owner) { this.Owner = owner; }
/*     */     
/*     */ 
/*     */     public void done()
/*     */     {
/*  62 */       dlgBatchHMP.this.setCursor(Cursor.getPredefinedCursor(0));
/*  63 */       Media.Messager("Finished!  Check the log for errors.");
/*  64 */       dlgBatchHMP.this.prgImporting.setValue(0);
/*     */     }
/*     */     
/*     */     protected Void doInBackground() throws Exception
/*     */     {
/*  69 */       String Messages = "";String MsgTemp = "";
/*  70 */       int LastMsgLength = Messages.length();
/*  71 */       HMPReader HMPr = new HMPReader();
/*  72 */       MechWriter XMLw = new MechWriter();
/*  73 */       Vector<File> files = new Vector();
/*     */       
/*     */ 
/*  76 */       File d = new File(dlgBatchHMP.this.txtSource.getText());
/*  77 */       if (d.isDirectory()) {
/*  78 */         if (d.listFiles() == null) {
/*  79 */           throw new Exception("There are no files in the source directory.\nCannot continue.");
/*     */         }
/*  81 */         for (File f : d.listFiles()) {
/*  82 */           if ((f.isFile()) && (f.getPath().endsWith(".hmp"))) {
/*  83 */             files.add(f);
/*     */           }
/*     */         }
/*     */       } else {
/*  87 */         throw new Exception("The source is not a directory.\nCannot continue.");
/*     */       }
/*     */       
/*  90 */       if (files.size() < 1) {
/*  91 */         throw new Exception("No HMP files found in the source directory.\nCannot continue.");
/*     */       }
/*     */       
/*  94 */       for (int i = 0; i < files.size(); i++) {
/*  95 */         File f = (File)files.get(i);
/*  96 */         String basename = f.getName().replace(".hmp", "");
/*     */         
/*     */         try
/*     */         {
/* 100 */           components.Mech m = HMPr.GetMech(f.getCanonicalPath(), true);
/* 101 */           MsgTemp = HMPr.GetErrors();
/*     */           
/*     */ 
/* 104 */           XMLw.setMech(m);
/* 105 */           XMLw.WriteXML(dlgBatchHMP.this.txtDestination.getText() + File.separator + basename + ".ssw");
/*     */         }
/*     */         catch (Exception e) {
/* 108 */           if (e.getMessage() == null) {
/* 109 */             Messages = Messages + "An unknown error has occured.\n" + f.getName() + " is not loadable.\n\n";
/*     */           } else {
/* 111 */             Messages = Messages + e.getMessage() + "\n" + f.getName() + "\n\n";
/*     */           }
/*     */         }
/*     */         
/* 115 */         if (MsgTemp.length() > 0) {
/* 116 */           Messages = Messages + MsgTemp + "\n" + f.getName() + "\n\n";
/*     */         }
/*     */         
/* 119 */         if (Messages.length() != LastMsgLength) {
/* 120 */           LastMsgLength = Messages.length();
/* 121 */           dlgBatchHMP.this.txtMessages.setText(Messages);
/* 122 */           dlgBatchHMP.this.txtMessages.setCaretPosition(0);
/*     */         }
/*     */         
/* 125 */         int progress = (int)((i + 1.0D) / files.size() * 100.0D);
/* 126 */         setProgress(progress);
/*     */       }
/*     */       
/* 129 */       return null;
/*     */     }
/*     */   }
/*     */   
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
/* 143 */     this.jScrollPane1 = new JScrollPane();
/* 144 */     this.txtMessages = new JTextPane();
/* 145 */     this.jPanel1 = new JPanel();
/* 146 */     this.btnClose = new JButton();
/* 147 */     this.jPanel2 = new JPanel();
/* 148 */     this.jLabel1 = new JLabel();
/* 149 */     this.txtSource = new JTextField();
/* 150 */     this.btnSource = new JButton();
/* 151 */     this.jLabel2 = new JLabel();
/* 152 */     this.txtDestination = new JTextField();
/* 153 */     this.btnDestination = new JButton();
/* 154 */     this.jPanel3 = new JPanel();
/* 155 */     this.btnImport = new JButton();
/* 156 */     this.prgImporting = new JProgressBar();
/*     */     
/* 158 */     setDefaultCloseOperation(2);
/* 159 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/* 161 */     this.jScrollPane1.setHorizontalScrollBarPolicy(31);
/* 162 */     this.jScrollPane1.setVerticalScrollBarPolicy(22);
/* 163 */     this.jScrollPane1.setMaximumSize(new Dimension(600, 300));
/* 164 */     this.jScrollPane1.setMinimumSize(new Dimension(600, 300));
/* 165 */     this.jScrollPane1.setPreferredSize(new Dimension(600, 300));
/*     */     
/* 167 */     this.txtMessages.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 12));
/* 168 */     this.txtMessages.setMaximumSize(new Dimension(575, 200000));
/* 169 */     this.txtMessages.setMinimumSize(new Dimension(575, 300));
/* 170 */     this.txtMessages.setPreferredSize(new Dimension(575, 300));
/* 171 */     this.jScrollPane1.setViewportView(this.txtMessages);
/*     */     
/* 173 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 174 */     gridBagConstraints.gridx = 0;
/* 175 */     gridBagConstraints.gridy = 2;
/* 176 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/* 177 */     getContentPane().add(this.jScrollPane1, gridBagConstraints);
/*     */     
/* 179 */     this.btnClose.setText("Close");
/* 180 */     this.btnClose.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 182 */         dlgBatchHMP.this.btnCloseActionPerformed(evt);
/*     */       }
/* 184 */     });
/* 185 */     this.jPanel1.add(this.btnClose);
/*     */     
/* 187 */     gridBagConstraints = new GridBagConstraints();
/* 188 */     gridBagConstraints.gridx = 0;
/* 189 */     gridBagConstraints.gridy = 3;
/* 190 */     gridBagConstraints.anchor = 13;
/* 191 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 192 */     getContentPane().add(this.jPanel1, gridBagConstraints);
/*     */     
/* 194 */     this.jPanel2.setLayout(new GridBagLayout());
/*     */     
/* 196 */     this.jLabel1.setText("Source Directory:");
/* 197 */     gridBagConstraints = new GridBagConstraints();
/* 198 */     gridBagConstraints.anchor = 17;
/* 199 */     this.jPanel2.add(this.jLabel1, gridBagConstraints);
/*     */     
/* 201 */     this.txtSource.setEditable(false);
/* 202 */     this.txtSource.setMinimumSize(new Dimension(200, 20));
/* 203 */     this.txtSource.setPreferredSize(new Dimension(200, 20));
/* 204 */     gridBagConstraints = new GridBagConstraints();
/* 205 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 206 */     this.jPanel2.add(this.txtSource, gridBagConstraints);
/*     */     
/* 208 */     this.btnSource.setText("...");
/* 209 */     this.btnSource.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 211 */         dlgBatchHMP.this.btnSourceActionPerformed(evt);
/*     */       }
/* 213 */     });
/* 214 */     this.jPanel2.add(this.btnSource, new GridBagConstraints());
/*     */     
/* 216 */     this.jLabel2.setText("Destination Directory:");
/* 217 */     gridBagConstraints = new GridBagConstraints();
/* 218 */     gridBagConstraints.gridx = 0;
/* 219 */     gridBagConstraints.gridy = 1;
/* 220 */     gridBagConstraints.anchor = 17;
/* 221 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 222 */     this.jPanel2.add(this.jLabel2, gridBagConstraints);
/*     */     
/* 224 */     this.txtDestination.setEditable(false);
/* 225 */     this.txtDestination.setMinimumSize(new Dimension(200, 20));
/* 226 */     this.txtDestination.setPreferredSize(new Dimension(200, 20));
/* 227 */     gridBagConstraints = new GridBagConstraints();
/* 228 */     gridBagConstraints.gridx = 1;
/* 229 */     gridBagConstraints.gridy = 1;
/* 230 */     gridBagConstraints.insets = new Insets(4, 4, 0, 4);
/* 231 */     this.jPanel2.add(this.txtDestination, gridBagConstraints);
/*     */     
/* 233 */     this.btnDestination.setText("...");
/* 234 */     this.btnDestination.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 236 */         dlgBatchHMP.this.btnDestinationActionPerformed(evt);
/*     */       }
/* 238 */     });
/* 239 */     gridBagConstraints = new GridBagConstraints();
/* 240 */     gridBagConstraints.gridx = 2;
/* 241 */     gridBagConstraints.gridy = 1;
/* 242 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 243 */     this.jPanel2.add(this.btnDestination, gridBagConstraints);
/*     */     
/* 245 */     gridBagConstraints = new GridBagConstraints();
/* 246 */     gridBagConstraints.anchor = 17;
/* 247 */     gridBagConstraints.insets = new Insets(4, 4, 4, 4);
/* 248 */     getContentPane().add(this.jPanel2, gridBagConstraints);
/*     */     
/* 250 */     this.btnImport.setText("Import");
/* 251 */     this.btnImport.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 253 */         dlgBatchHMP.this.btnImportActionPerformed(evt);
/*     */       }
/* 255 */     });
/* 256 */     this.jPanel3.add(this.btnImport);
/*     */     
/* 258 */     this.prgImporting.setMinimumSize(new Dimension(148, 20));
/* 259 */     this.prgImporting.setPreferredSize(new Dimension(148, 20));
/* 260 */     this.prgImporting.setStringPainted(true);
/* 261 */     this.jPanel3.add(this.prgImporting);
/*     */     
/* 263 */     gridBagConstraints = new GridBagConstraints();
/* 264 */     gridBagConstraints.gridx = 0;
/* 265 */     gridBagConstraints.gridy = 1;
/* 266 */     gridBagConstraints.anchor = 17;
/* 267 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 268 */     getContentPane().add(this.jPanel3, gridBagConstraints);
/*     */     
/* 270 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCloseActionPerformed(ActionEvent evt) {
/* 274 */     dispose();
/*     */   }
/*     */   
/*     */   private void btnSourceActionPerformed(ActionEvent evt) {
/* 278 */     Media media = new Media();
/* 279 */     String dirPath = media.GetDirectorySelection(this);
/* 280 */     this.txtSource.setText(dirPath);
/*     */   }
/*     */   
/*     */   private void btnDestinationActionPerformed(ActionEvent evt) {
/* 284 */     Media media = new Media();
/* 285 */     String dirPath = media.GetDirectorySelection(this);
/* 286 */     this.txtDestination.setText(dirPath);
/*     */   }
/*     */   
/*     */   private void btnImportActionPerformed(ActionEvent evt) {
/* 290 */     this.prgImporting.setValue(0);
/* 291 */     if (this.txtSource.getText().length() < 1) {
/* 292 */       Media.Messager(this, "The Source directory is empty.\nPlease choose a Source directory.");
/* 293 */       return;
/*     */     }
/* 295 */     if (this.txtDestination.getText().length() < 1) {
/* 296 */       Media.Messager(this, "The Destination directory is empty.\nPlease choose a Destination directory.");
/* 297 */       return;
/*     */     }
/* 299 */     int Response = javax.swing.JOptionPane.showConfirmDialog(this, "This will import each HMP file in the Source directory\nand save it to an SSW file in the Destination directory.\nThis process could take a few minutes, are you ready?", "Batch HMP Import", 0);
/* 300 */     if (Response == 0) {
/* 301 */       setCursor(Cursor.getPredefinedCursor(3));
/*     */       try {
/* 303 */         Importer Import = new Importer(this);
/* 304 */         Import.addPropertyChangeListener(this);
/* 305 */         Import.execute();
/*     */       }
/*     */       catch (Exception e) {
/* 308 */         Media.Messager(this, "A fatal error occured while processing the 'Mechs:\n" + e.getMessage());
/* 309 */         e.printStackTrace();
/*     */       }
/* 311 */       setCursor(Cursor.getPredefinedCursor(0));
/* 312 */       this.prgImporting.setValue(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgBatchHMP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */