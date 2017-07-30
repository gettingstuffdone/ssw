/*     */ package ssw.gui;
/*     */ 
/*     */ import filehandlers.Media;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.swing.AbstractListModel;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import ssw.filehandlers.XMLRPCClient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgBrowseS7Images
/*     */   extends JDialog
/*     */ {
/*  46 */   Vector<ImageID> ImageList = new Vector();
/*  47 */   int UserID = -1; int defaultIndex = 0;
/*     */   
/*  49 */   String ImageID = "-1"; String ImageName = "none";
/*     */   
/*  51 */   private Media media = new Media();
/*  52 */   private Cursor Hourglass = new Cursor(3);
/*  53 */   private Cursor NormalCursor = new Cursor(0);
/*     */   private JButton brnCancel;
/*     */   
/*     */   public dlgBrowseS7Images(Frame parent, boolean modal, int user, String Image) {
/*  57 */     super(parent, modal);
/*  58 */     initComponents();
/*  59 */     this.UserID = user;
/*     */     try {
/*  61 */       LoadImageList();
/*     */     } catch (Exception e) {
/*  63 */       Media.Messager(this, e.getMessage());
/*  64 */       dispose();
/*     */     }
/*     */     
/*     */ 
/*  68 */     DefaultListModel listModel = new DefaultListModel();
/*  69 */     for (Iterator localIterator = this.ImageList.iterator(); localIterator.hasNext(); 
/*     */         
/*  71 */         this.defaultIndex = (listModel.size() - 1))
/*     */     {
/*  69 */       ImageID id = (ImageID)localIterator.next();
/*  70 */       listModel.addElement(id);
/*  71 */       if ((!id.ID.equals(Image)) && (!id.Name.equals(Image))) {}
/*     */     }
/*     */     
/*     */ 
/*  75 */     this.lstImages.setModel(listModel);
/*     */     
/*     */ 
/*  78 */     this.lstImages.setSelectedIndex(this.defaultIndex);
/*     */   }
/*     */   
/*     */   private void LoadImageList() throws Exception
/*     */   {
/*  83 */     String filename = "Data/Solaris7/S7Images";
/*     */     try
/*     */     {
/*  86 */       BufferedReader FR = new BufferedReader(new FileReader(filename));
/*     */       
/*  88 */       boolean EOF = false;
/*  89 */       String read = "";
/*  90 */       while (!EOF) {
/*     */         try {
/*  92 */           read = FR.readLine();
/*  93 */           if (read == null)
/*     */           {
/*  95 */             EOF = true;
/*     */           } else {
/*  97 */             this.ImageList.add(ProcessString(read));
/*     */           }
/*     */         }
/*     */         catch (IOException e) {
/* 101 */           EOF = true;
/*     */         }
/*     */       }
/*     */       
/* 105 */       FR.close();
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 110 */         XMLRPCClient serve = new XMLRPCClient("http://www.solaris7.com/service/index.asp");
/* 111 */         String[] List = serve.GetUserImages(this.UserID);
/*     */         
/*     */ 
/* 114 */         ImageID sep = new ImageID(null);
/* 115 */         sep.ID = "-1";
/* 116 */         sep.Name = "---  User Images ---";
/* 117 */         sep.URL = (".." + File.separator + "Images" + File.separator + "No_Image.png");
/* 118 */         this.ImageList.add(0, sep);
/*     */         
/*     */ 
/* 121 */         for (int i = 1; 
/* 122 */             i < List.length; i++) {
/* 123 */           this.ImageList.add(i, ProcessString(List[i]));
/*     */         }
/*     */         
/*     */ 
/* 127 */         sep = new ImageID(null);
/* 128 */         sep.ID = "-1";
/* 129 */         sep.Name = "---  Solaris 7 TRO Images ---";
/* 130 */         sep.URL = (".." + File.separator + "Images" + File.separator + "No_Image.png");
/* 131 */         this.ImageList.add(i, sep);
/*     */       }
/*     */       catch (Exception f)
/*     */       {
/* 135 */         throw f;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 139 */       if ((e instanceof FileNotFoundException))
/*     */       {
/* 141 */         File file = new File("Data/Solaris7/S7Images");
/*     */         try
/*     */         {
/* 144 */           file.createNewFile();
/*     */           
/*     */ 
/* 147 */           XMLRPCClient serve = new XMLRPCClient("http://www.solaris7.com/service/index.asp");
/* 148 */           String[] List = serve.GetTROImages("01/01/1900");
/*     */           
/*     */ 
/* 151 */           BufferedWriter FR = new BufferedWriter(new FileWriter(filename));
/* 152 */           for (int i = 0; i < List.length; i++) {
/* 153 */             FR.write(List[i]);
/* 154 */             FR.newLine();
/*     */           }
/* 156 */           FR.close();
/*     */           
/*     */ 
/* 159 */           LoadImageList();
/* 160 */           return;
/*     */         }
/*     */         catch (Exception f) {
/* 163 */           throw f;
/*     */         }
/*     */       }
/*     */       
/* 167 */       throw e;
/*     */     }
/*     */   }
/*     */   
/*     */   private ImageID ProcessString(String s)
/*     */   {
/* 173 */     ImageID retval = new ImageID(null);
/* 174 */     String[] c = s.split(",");
/* 175 */     retval.ID = c[0];
/* 176 */     retval.Name = c[1];
/* 177 */     retval.URL = c[2].replace(" ", "%20");
/* 178 */     return retval;
/*     */   }
/*     */   
/*     */   private String GetURL(String s) {
/* 182 */     return s.replaceAll(" ", "%20");
/*     */   }
/*     */   
/*     */   public String GetImageID() {
/* 186 */     return this.ImageID;
/*     */   }
/*     */   
/*     */   public String GetImageName() {
/* 190 */     return this.ImageName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private JButton btnUseImage;
/*     */   
/*     */ 
/*     */   private JLabel jLabel1;
/*     */   
/*     */   private void initComponents()
/*     */   {
/* 202 */     this.jLabel1 = new JLabel();
/* 203 */     this.jPanel1 = new JPanel();
/* 204 */     this.btnUseImage = new JButton();
/* 205 */     this.brnCancel = new JButton();
/* 206 */     this.lblImage = new JLabel();
/* 207 */     this.jScrollPane2 = new JScrollPane();
/* 208 */     this.lstImages = new JList();
/*     */     
/* 210 */     setDefaultCloseOperation(2);
/* 211 */     setTitle("Select Image");
/*     */     
/* 213 */     this.jLabel1.setText("Use the following image:");
/*     */     
/* 215 */     this.btnUseImage.setText("Use Image");
/* 216 */     this.btnUseImage.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 218 */         dlgBrowseS7Images.this.btnUseImageActionPerformed(evt);
/*     */       }
/* 220 */     });
/* 221 */     this.jPanel1.add(this.btnUseImage);
/*     */     
/* 223 */     this.brnCancel.setText("Cancel");
/* 224 */     this.brnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 226 */         dlgBrowseS7Images.this.brnCancelActionPerformed(evt);
/*     */       }
/* 228 */     });
/* 229 */     this.jPanel1.add(this.brnCancel);
/*     */     
/* 231 */     this.lblImage.setHorizontalAlignment(0);
/* 232 */     this.lblImage.setIcon(new ImageIcon(getClass().getResource("/ssw/Images/No_Image.png")));
/* 233 */     this.lblImage.setBorder(BorderFactory.createEtchedBorder());
/* 234 */     this.lblImage.setMaximumSize(new Dimension(300, 500));
/* 235 */     this.lblImage.setMinimumSize(new Dimension(300, 500));
/* 236 */     this.lblImage.setPreferredSize(new Dimension(300, 500));
/*     */     
/* 238 */     this.lstImages.setModel(new AbstractListModel() {
/* 239 */       String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
/* 240 */       public int getSize() { return this.strings.length; }
/* 241 */       public Object getElementAt(int i) { return this.strings[i]; }
/* 242 */     });
/* 243 */     this.lstImages.setSelectionMode(0);
/* 244 */     this.lstImages.addListSelectionListener(new ListSelectionListener() {
/*     */       public void valueChanged(ListSelectionEvent evt) {
/* 246 */         dlgBrowseS7Images.this.lstImagesValueChanged(evt);
/*     */       }
/* 248 */     });
/* 249 */     this.jScrollPane2.setViewportView(this.lstImages);
/*     */     
/* 251 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 252 */     getContentPane().setLayout(layout);
/* 253 */     layout.setHorizontalGroup(layout
/* 254 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 255 */       .addGroup(layout.createSequentialGroup()
/* 256 */       .addContainerGap()
/* 257 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 258 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 259 */       .addComponent(this.jScrollPane2, -1, 264, 32767)
/* 260 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 261 */       .addComponent(this.lblImage, -1, 387, 32767))
/* 262 */       .addComponent(this.jPanel1, GroupLayout.Alignment.TRAILING, -2, -1, -2)
/* 263 */       .addComponent(this.jLabel1))
/* 264 */       .addContainerGap()));
/*     */     
/* 266 */     layout.setVerticalGroup(layout
/* 267 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 268 */       .addGroup(layout.createSequentialGroup()
/* 269 */       .addContainerGap()
/* 270 */       .addComponent(this.jLabel1)
/* 271 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 272 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 273 */       .addComponent(this.jScrollPane2, -1, 500, 32767)
/* 274 */       .addComponent(this.lblImage, -1, -1, 32767))
/* 275 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 276 */       .addComponent(this.jPanel1, -2, -1, -2)
/* 277 */       .addGap(11, 11, 11)));
/*     */     
/*     */ 
/* 280 */     pack();
/*     */   }
/*     */   
/*     */   private void brnCancelActionPerformed(ActionEvent evt) {
/* 284 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void btnUseImageActionPerformed(ActionEvent evt) {
/* 288 */     this.ImageID = ((ImageID)this.lstImages.getModel().getElementAt(this.lstImages.getSelectedIndex())).ID;
/* 289 */     this.ImageName = ((ImageID)this.lstImages.getModel().getElementAt(this.lstImages.getSelectedIndex())).Name;
/* 290 */     setVisible(false);
/*     */   }
/*     */   
/*     */   private void lstImagesValueChanged(ListSelectionEvent evt) {
/* 294 */     if (this.lstImages.getSelectedIndex() > -1) {
/* 295 */       this.media.setLogo(this.lblImage, ((ImageID)this.lstImages.getModel().getElementAt(this.lstImages.getSelectedIndex())).URL);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private JPanel jPanel1;
/*     */   
/*     */   private JScrollPane jScrollPane2;
/*     */   
/*     */   private JLabel lblImage;
/*     */   private JList lstImages;
/*     */   private class ImageID
/*     */   {
/*     */     private ImageID() {}
/*     */     
/* 310 */     public String ID = "";
/* 311 */     public String Name = "";
/* 312 */     public String URL = "";
/*     */     
/*     */     public String toString() {
/* 315 */       return this.Name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgBrowseS7Images.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */