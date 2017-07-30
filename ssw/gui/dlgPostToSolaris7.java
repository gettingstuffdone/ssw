/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Mech;
/*     */ import filehandlers.Media;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import ssw.filehandlers.HTMLWriter;
/*     */ import ssw.filehandlers.XMLRPCClient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgPostToSolaris7
/*     */   extends JDialog
/*     */ {
/*  46 */   private String Callsign = ""; private String Password = ""; private String UserImage = "-1"; private String ImageName = "none";
/*     */   
/*     */ 
/*     */ 
/*  50 */   private int UserID = -1;
/*  51 */   private String[][] Armories = (String[][])null;
/*     */   
/*  53 */   private Cursor Hourglass = new Cursor(3);
/*  54 */   private Cursor NormalCursor = new Cursor(0);
/*     */   private ifMechForm Parent;
/*     */   private Mech CurMech;
/*     */   
/*  58 */   public dlgPostToSolaris7(Frame parent, boolean modal) { super(parent, modal);
/*  59 */     this.Parent = ((ifMechForm)parent);
/*  60 */     this.CurMech = this.Parent.GetMech();
/*  61 */     setTitle("Post to Solaris7.com");
/*  62 */     initComponents();
/*  63 */     this.Callsign = this.Parent.GetPrefs().get("S7Callsign", "");
/*  64 */     this.Password = this.Parent.GetPrefs().get("S7Password", "");
/*  65 */     this.UserID = this.Parent.GetPrefs().getInt("S7UserID", -1);
/*  66 */     this.txtCallsign.setText(this.Callsign);
/*  67 */     this.txtPassword.setText(this.Password);
/*     */     
/*  69 */     if (((!this.Callsign.equals("") ? 1 : 0) & (!this.Password.equals("") ? 1 : 0)) != 0) {
/*  70 */       this.chkSaveInfo.setSelected(true);
/*     */     }
/*     */     
/*     */ 
/*  74 */     if (((!this.CurMech.GetSolaris7ImageID().equals("0") ? 1 : 0) | (!this.CurMech.GetSolaris7ImageID().equals("-1") ? 1 : 0)) != 0) {
/*  75 */       this.UserImage = this.CurMech.GetSolaris7ImageID();
/*  76 */       this.txtImageName.setText(this.CurMech.GetSolaris7ImageID());
/*     */     } }
/*     */   
/*     */   private JButton btnBrowseImages;
/*     */   
/*  81 */   private String GetHTMLFromFile(String Filename) throws Exception { BufferedReader FR = new BufferedReader(new FileReader(Filename));
/*  82 */     String retval = "";
/*  83 */     boolean EOF = false;
/*     */     
/*  85 */     while (!EOF) {
/*  86 */       String read = FR.readLine();
/*  87 */       if (read == null) {
/*  88 */         EOF = true;
/*     */       } else {
/*  90 */         read = read.replaceAll(":tab:", "");
/*  91 */         retval = retval + read.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
/*     */       }
/*     */     }
/*     */     
/*  95 */     FR.close();
/*  96 */     return retval; }
/*     */   
/*     */   private JButton btnCancel;
/*     */   
/* 100 */   private String GetTROYear() { switch (this.cmbTROYear.getSelectedIndex()) {
/*     */     case 0: 
/* 102 */       return "2750";
/*     */     case 1: 
/* 104 */       return "3025";
/*     */     case 2: 
/* 106 */       return "3026";
/*     */     case 3: 
/* 108 */       return "3039";
/*     */     case 4: 
/* 110 */       return "3050";
/*     */     case 5: 
/* 112 */       return "3055";
/*     */     case 6: 
/* 114 */       return "3057";
/*     */     case 7: 
/* 116 */       return "3058";
/*     */     case 8: 
/* 118 */       return "3060";
/*     */     case 9: 
/* 120 */       return "3067";
/*     */     case 10: 
/* 122 */       return "3075";
/*     */     }
/* 124 */     return "2750"; }
/*     */   
/*     */   private JButton btnGetArmories;
/*     */   private JButton btnPost;
/*     */   private JCheckBox chkSaveInfo;
/*     */   private JComboBox cmbArmories;
/*     */   private JComboBox cmbTROYear;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   
/* 138 */   private void initComponents() { this.jPanel1 = new JPanel();
/* 139 */     this.lblCallsign = new JLabel();
/* 140 */     this.lblPassword = new JLabel();
/* 141 */     this.txtCallsign = new JTextField();
/* 142 */     this.jLabel2 = new JLabel();
/* 143 */     this.chkSaveInfo = new JCheckBox();
/* 144 */     this.txtPassword = new JPasswordField();
/* 145 */     this.jPanel2 = new JPanel();
/* 146 */     this.jLabel1 = new JLabel();
/* 147 */     this.cmbArmories = new JComboBox();
/* 148 */     this.jLabel3 = new JLabel();
/* 149 */     this.txtImageName = new JTextField();
/* 150 */     this.btnBrowseImages = new JButton();
/* 151 */     this.jLabel4 = new JLabel();
/* 152 */     this.cmbTROYear = new JComboBox();
/* 153 */     this.jPanel3 = new JPanel();
/* 154 */     this.btnPost = new JButton();
/* 155 */     this.btnCancel = new JButton();
/* 156 */     this.jPanel4 = new JPanel();
/* 157 */     this.btnGetArmories = new JButton();
/*     */     
/* 159 */     setDefaultCloseOperation(2);
/* 160 */     getContentPane().setLayout(new GridBagLayout());
/*     */     
/* 162 */     this.jPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Login Information"));
/* 163 */     this.jPanel1.setLayout(new GridBagLayout());
/*     */     
/* 165 */     this.lblCallsign.setText("Callsign");
/* 166 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 167 */     gridBagConstraints.gridx = 0;
/* 168 */     gridBagConstraints.gridy = 1;
/* 169 */     gridBagConstraints.anchor = 13;
/* 170 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 171 */     this.jPanel1.add(this.lblCallsign, gridBagConstraints);
/*     */     
/* 173 */     this.lblPassword.setText("Password");
/* 174 */     gridBagConstraints = new GridBagConstraints();
/* 175 */     gridBagConstraints.gridx = 0;
/* 176 */     gridBagConstraints.gridy = 2;
/* 177 */     gridBagConstraints.anchor = 13;
/* 178 */     gridBagConstraints.insets = new Insets(0, 4, 0, 4);
/* 179 */     this.jPanel1.add(this.lblPassword, gridBagConstraints);
/*     */     
/* 181 */     this.txtCallsign.setMaximumSize(new Dimension(150, 20));
/* 182 */     this.txtCallsign.setMinimumSize(new Dimension(150, 20));
/* 183 */     this.txtCallsign.setPreferredSize(new Dimension(150, 20));
/* 184 */     gridBagConstraints = new GridBagConstraints();
/* 185 */     gridBagConstraints.gridx = 1;
/* 186 */     gridBagConstraints.gridy = 1;
/* 187 */     gridBagConstraints.anchor = 17;
/* 188 */     this.jPanel1.add(this.txtCallsign, gridBagConstraints);
/*     */     
/* 190 */     this.jLabel2.setText("Enter your Solaris7.com user info:");
/* 191 */     gridBagConstraints = new GridBagConstraints();
/* 192 */     gridBagConstraints.gridwidth = 2;
/* 193 */     gridBagConstraints.anchor = 17;
/* 194 */     gridBagConstraints.insets = new Insets(0, 2, 2, 2);
/* 195 */     this.jPanel1.add(this.jLabel2, gridBagConstraints);
/*     */     
/* 197 */     this.chkSaveInfo.setText("Save account information");
/* 198 */     gridBagConstraints = new GridBagConstraints();
/* 199 */     gridBagConstraints.gridx = 0;
/* 200 */     gridBagConstraints.gridy = 3;
/* 201 */     gridBagConstraints.gridwidth = 2;
/* 202 */     gridBagConstraints.anchor = 17;
/* 203 */     gridBagConstraints.insets = new Insets(4, 4, 0, 0);
/* 204 */     this.jPanel1.add(this.chkSaveInfo, gridBagConstraints);
/*     */     
/* 206 */     this.txtPassword.setMaximumSize(new Dimension(150, 20));
/* 207 */     this.txtPassword.setMinimumSize(new Dimension(150, 20));
/* 208 */     this.txtPassword.setPreferredSize(new Dimension(150, 20));
/* 209 */     gridBagConstraints = new GridBagConstraints();
/* 210 */     gridBagConstraints.gridx = 1;
/* 211 */     gridBagConstraints.gridy = 2;
/* 212 */     this.jPanel1.add(this.txtPassword, gridBagConstraints);
/*     */     
/* 214 */     gridBagConstraints = new GridBagConstraints();
/* 215 */     gridBagConstraints.fill = 2;
/* 216 */     gridBagConstraints.anchor = 17;
/* 217 */     getContentPane().add(this.jPanel1, gridBagConstraints);
/*     */     
/* 219 */     this.jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Choose Armory"));
/* 220 */     this.jPanel2.setLayout(new GridBagLayout());
/*     */     
/* 222 */     this.jLabel1.setText("Post the Mech to this Armory:");
/* 223 */     gridBagConstraints = new GridBagConstraints();
/* 224 */     gridBagConstraints.gridwidth = 2;
/* 225 */     gridBagConstraints.anchor = 17;
/* 226 */     gridBagConstraints.insets = new Insets(0, 2, 4, 2);
/* 227 */     this.jPanel2.add(this.jLabel1, gridBagConstraints);
/*     */     
/* 229 */     this.cmbArmories.setModel(new DefaultComboBoxModel(new String[] { "none" }));
/* 230 */     this.cmbArmories.setEnabled(false);
/* 231 */     this.cmbArmories.setMaximumSize(new Dimension(230, 20));
/* 232 */     this.cmbArmories.setMinimumSize(new Dimension(230, 20));
/* 233 */     this.cmbArmories.setPreferredSize(new Dimension(230, 20));
/* 234 */     gridBagConstraints = new GridBagConstraints();
/* 235 */     gridBagConstraints.gridx = 0;
/* 236 */     gridBagConstraints.gridy = 1;
/* 237 */     gridBagConstraints.gridwidth = 2;
/* 238 */     gridBagConstraints.anchor = 17;
/* 239 */     this.jPanel2.add(this.cmbArmories, gridBagConstraints);
/*     */     
/* 241 */     this.jLabel3.setText("Use this image:");
/* 242 */     gridBagConstraints = new GridBagConstraints();
/* 243 */     gridBagConstraints.gridx = 0;
/* 244 */     gridBagConstraints.gridy = 3;
/* 245 */     gridBagConstraints.gridwidth = 2;
/* 246 */     gridBagConstraints.anchor = 17;
/* 247 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 248 */     this.jPanel2.add(this.jLabel3, gridBagConstraints);
/*     */     
/* 250 */     this.txtImageName.setText("none");
/* 251 */     this.txtImageName.setDisabledTextColor(new Color(0, 0, 0));
/* 252 */     this.txtImageName.setEnabled(false);
/* 253 */     this.txtImageName.setMaximumSize(new Dimension(150, 20));
/* 254 */     this.txtImageName.setMinimumSize(new Dimension(150, 20));
/* 255 */     this.txtImageName.setPreferredSize(new Dimension(150, 20));
/* 256 */     gridBagConstraints = new GridBagConstraints();
/* 257 */     gridBagConstraints.gridx = 0;
/* 258 */     gridBagConstraints.gridy = 4;
/* 259 */     gridBagConstraints.anchor = 17;
/* 260 */     this.jPanel2.add(this.txtImageName, gridBagConstraints);
/*     */     
/* 262 */     this.btnBrowseImages.setText("Browse");
/* 263 */     this.btnBrowseImages.setEnabled(false);
/* 264 */     this.btnBrowseImages.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 266 */         dlgPostToSolaris7.this.btnBrowseImagesActionPerformed(evt);
/*     */       }
/* 268 */     });
/* 269 */     gridBagConstraints = new GridBagConstraints();
/* 270 */     gridBagConstraints.gridx = 1;
/* 271 */     gridBagConstraints.gridy = 4;
/* 272 */     this.jPanel2.add(this.btnBrowseImages, gridBagConstraints);
/*     */     
/* 274 */     this.jLabel4.setHorizontalAlignment(4);
/* 275 */     this.jLabel4.setText("Select TRO Year:");
/* 276 */     gridBagConstraints = new GridBagConstraints();
/* 277 */     gridBagConstraints.gridx = 0;
/* 278 */     gridBagConstraints.gridy = 2;
/* 279 */     gridBagConstraints.fill = 2;
/* 280 */     gridBagConstraints.anchor = 17;
/* 281 */     gridBagConstraints.insets = new Insets(4, 0, 0, 4);
/* 282 */     this.jPanel2.add(this.jLabel4, gridBagConstraints);
/*     */     
/* 284 */     this.cmbTROYear.setModel(new DefaultComboBoxModel(new String[] { "2750", "3025", "3026", "3039", "3050", "3055", "3057", "3058", "3060", "3067", "3075" }));
/* 285 */     this.cmbTROYear.setEnabled(false);
/* 286 */     gridBagConstraints = new GridBagConstraints();
/* 287 */     gridBagConstraints.gridx = 1;
/* 288 */     gridBagConstraints.gridy = 2;
/* 289 */     gridBagConstraints.fill = 2;
/* 290 */     gridBagConstraints.anchor = 13;
/* 291 */     gridBagConstraints.insets = new Insets(4, 0, 0, 0);
/* 292 */     this.jPanel2.add(this.cmbTROYear, gridBagConstraints);
/*     */     
/* 294 */     gridBagConstraints = new GridBagConstraints();
/* 295 */     gridBagConstraints.gridx = 0;
/* 296 */     gridBagConstraints.gridy = 2;
/* 297 */     gridBagConstraints.fill = 2;
/* 298 */     gridBagConstraints.anchor = 17;
/* 299 */     getContentPane().add(this.jPanel2, gridBagConstraints);
/*     */     
/* 301 */     this.btnPost.setText("Post Mech");
/* 302 */     this.btnPost.setEnabled(false);
/* 303 */     this.btnPost.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 305 */         dlgPostToSolaris7.this.btnPostActionPerformed(evt);
/*     */       }
/* 307 */     });
/* 308 */     this.jPanel3.add(this.btnPost);
/*     */     
/* 310 */     this.btnCancel.setText("Cancel");
/* 311 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 313 */         dlgPostToSolaris7.this.btnCancelActionPerformed(evt);
/*     */       }
/* 315 */     });
/* 316 */     this.jPanel3.add(this.btnCancel);
/*     */     
/* 318 */     gridBagConstraints = new GridBagConstraints();
/* 319 */     gridBagConstraints.gridx = 0;
/* 320 */     gridBagConstraints.gridy = 3;
/* 321 */     gridBagConstraints.anchor = 13;
/* 322 */     gridBagConstraints.insets = new Insets(2, 0, 0, 0);
/* 323 */     getContentPane().add(this.jPanel3, gridBagConstraints);
/*     */     
/* 325 */     this.btnGetArmories.setText("Retrieve Armories");
/* 326 */     this.btnGetArmories.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 328 */         dlgPostToSolaris7.this.btnGetArmoriesActionPerformed(evt);
/*     */       }
/* 330 */     });
/* 331 */     this.jPanel4.add(this.btnGetArmories);
/*     */     
/* 333 */     gridBagConstraints = new GridBagConstraints();
/* 334 */     gridBagConstraints.gridx = 0;
/* 335 */     gridBagConstraints.gridy = 1;
/* 336 */     gridBagConstraints.anchor = 13;
/* 337 */     getContentPane().add(this.jPanel4, gridBagConstraints);
/*     */     
/* 339 */     pack(); }
/*     */   
/*     */   private JPanel jPanel3;
/*     */   
/* 343 */   private void btnCancelActionPerformed(ActionEvent evt) { dispose(); }
/*     */   
/*     */   private JPanel jPanel4;
/*     */   
/* 347 */   private void btnGetArmoriesActionPerformed(ActionEvent evt) { setCursor(this.Hourglass);
/* 348 */     XMLRPCClient serve = new XMLRPCClient("http://www.solaris7.com/service/index.asp");
/* 349 */     this.Callsign = this.txtCallsign.getText();
/* 350 */     this.Password = String.copyValueOf(this.txtPassword.getPassword());
/*     */     try
/*     */     {
/* 353 */       if (this.UserID == -1) {
/* 354 */         this.UserID = serve.GetMemberID(this.Callsign, this.Password);
/* 355 */         this.Parent.GetPrefs().put("S7Callsign", this.Callsign);
/* 356 */         this.Parent.GetPrefs().put("S7Password", this.Password);
/*     */       }
/* 358 */       if (this.UserID != -1) {
/* 359 */         this.Armories = serve.GetArmoryList(this.UserID);
/*     */       } else {
/* 361 */         Media.Messager(this, "Could not retrieve a MemberID from this Callsign or Password.\nThe Mech cannot be posted.");
/* 362 */         setCursor(this.NormalCursor);
/* 363 */         return;
/*     */       }
/*     */     } catch (Exception e) {
/* 366 */       Media.Messager(this, e.getMessage());
/* 367 */       setCursor(this.NormalCursor);
/* 368 */       return;
/*     */     }
/*     */     
/* 371 */     if (this.Armories != null) {
/* 372 */       String[] input = new String[this.Armories.length];
/* 373 */       for (int i = 0; i < this.Armories.length; i++) {
/* 374 */         input[i] = this.Armories[i][0];
/*     */       }
/* 376 */       this.cmbArmories.setModel(new DefaultComboBoxModel(input));
/* 377 */       this.cmbArmories.setEnabled(true);
/* 378 */       this.btnPost.setEnabled(true);
/* 379 */       this.btnBrowseImages.setEnabled(true);
/* 380 */       this.cmbTROYear.setEnabled(true);
/* 381 */       setCursor(this.NormalCursor);
/*     */     } else {
/* 383 */       Media.Messager(this, "No armories were returned from this Callsign or Password.\nThe Mech cannot be posted.");
/* 384 */       setCursor(this.NormalCursor);
/* 385 */       return;
/*     */     } }
/*     */   
/*     */   private JLabel lblCallsign;
/*     */   private JLabel lblPassword;
/*     */   
/* 391 */   private void btnPostActionPerformed(ActionEvent evt) { setCursor(this.Hourglass);
/* 392 */     if (this.chkSaveInfo.isSelected()) {
/* 393 */       this.Callsign = this.txtCallsign.getText();
/* 394 */       this.Password = String.copyValueOf(this.txtPassword.getPassword());
/* 395 */       this.Parent.GetPrefs().put("S7Callsign", this.Callsign);
/* 396 */       this.Parent.GetPrefs().put("S7Password", this.Password);
/* 397 */       this.Parent.GetPrefs().putInt("S7UserID", this.UserID);
/*     */     }
/*     */     
/*     */ 
/* 401 */     String file = "";
/* 402 */     XMLRPCClient serve = new XMLRPCClient("http://www.solaris7.com/service/index.asp");
/*     */     
/* 404 */     if (this.CurMech.GetModel().isEmpty()) {
/* 405 */       file = this.CurMech.GetName() + ".html";
/*     */     } else {
/* 407 */       file = this.CurMech.GetName() + " " + this.CurMech.GetModel() + ".html";
/*     */     }
/* 409 */     if (!this.Parent.GetPrefs().get("HTMLExportPath", "none").equals("none")) {
/* 410 */       file = this.Parent.GetPrefs().get("HTMLExportPath", "none") + File.separator + file;
/*     */     }
/*     */     
/* 413 */     HTMLWriter HTMw = new HTMLWriter(this.CurMech);
/* 414 */     String HTMLout = "";
/*     */     try
/*     */     {
/* 417 */       HTMw.WriteHTML("Data/Templates/Mech_HTML.html", file);
/* 418 */       HTMLout = GetHTMLFromFile(file);
/*     */     } catch (IOException e) {
/* 420 */       Media.Messager(this, "There was a problem writing or reading the file:\n" + e.getMessage());
/* 421 */       setCursor(this.NormalCursor);
/* 422 */       return;
/*     */     } catch (Exception e) {
/* 424 */       Media.Messager(this, e.getMessage());
/* 425 */       setCursor(this.NormalCursor);
/* 426 */       return;
/*     */     }
/*     */     
/* 429 */     String ArmoryID = this.Armories[this.cmbArmories.getSelectedIndex()][1];
/* 430 */     String TROYear = GetTROYear();
/* 431 */     String MechID = "0";
/*     */     try {
/* 433 */       MechID = serve.PostToSolaris7("" + this.UserID, ArmoryID, HTMLout, this.UserImage, TROYear, this.CurMech);
/*     */     } catch (Exception e) {
/* 435 */       Media.Messager(this, "There was a problem with the server:\n" + e.getMessage());
/* 436 */       setCursor(this.NormalCursor);
/* 437 */       return;
/*     */     }
/*     */     
/* 440 */     if (MechID.equals("0")) {
/* 441 */       Media.Messager(this, "Mech was not posted to Solaris7.com.  The reason is unknown.");
/*     */     } else {
/* 443 */       Media.Messager(this, "Mech successfully posted to Solaris7.com!");
/* 444 */       this.CurMech.SetSolaris7ID(MechID);
/* 445 */       if (((!this.UserImage.equals("-1") ? 1 : 0) | (!this.UserImage.equals("0") ? 1 : 0)) != 0) {
/* 446 */         this.CurMech.SetSolaris7ImageID(this.UserImage);
/*     */       }
/* 448 */       this.Parent.QuickSave();
/*     */     }
/* 450 */     setCursor(this.NormalCursor);
/* 451 */     dispose(); }
/*     */   
/*     */   private JTextField txtCallsign;
/*     */   
/* 455 */   private void btnBrowseImagesActionPerformed(ActionEvent evt) { setCursor(this.Hourglass);
/* 456 */     dlgBrowseS7Images ImageViewer = new dlgBrowseS7Images((JFrame)this.Parent, true, this.UserID, this.CurMech.GetSolaris7ImageID());
/* 457 */     Point p = ((JFrame)this.Parent).getLocationOnScreen();
/* 458 */     ImageViewer.setLocation(p.x + 100, p.y);
/*     */     
/* 460 */     ImageViewer.setVisible(true);
/* 461 */     if (!ImageViewer.GetImageID().equals("-1")) {
/* 462 */       this.UserImage = ImageViewer.GetImageID();
/* 463 */       this.ImageName = ImageViewer.GetImageName();
/*     */     }
/* 465 */     ImageViewer.dispose();
/* 466 */     this.txtImageName.setText(this.ImageName);
/* 467 */     setCursor(this.NormalCursor);
/*     */   }
/*     */   
/*     */   private JTextField txtImageName;
/*     */   private JPasswordField txtPassword;
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgPostToSolaris7.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */