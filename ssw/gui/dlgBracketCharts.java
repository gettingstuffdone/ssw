/*     */ package ssw.gui;
/*     */ 
/*     */ import common.CommonTools;
/*     */ import components.Ammunition;
/*     */ import components.Mech;
/*     */ import components.ifMechLoadout;
/*     */ import components.ifWeapon;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dlgBracketCharts
/*     */   extends JDialog
/*     */ {
/*     */   private Mech CurMech;
/*     */   private String[][] data;
/*     */   private JButton btnClose;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JTable tblBrackets;
/*     */   
/*     */   public dlgBracketCharts(Frame parent, boolean modal, Mech m)
/*     */   {
/*  45 */     super(parent, modal);
/*  46 */     this.CurMech = m;
/*  47 */     GetWeaponData();
/*  48 */     initComponents();
/*  49 */     ResetTable();
/*     */   }
/*     */   
/*     */   private void GetWeaponData() {
/*  53 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/*  54 */     ArrayList<ifWeapon> wep = new ArrayList();
/*  55 */     ArrayList<Ammunition> ammo = new ArrayList();
/*  56 */     ArrayList<WeaponInfo> temp = new ArrayList();
/*  57 */     int cols = 0;
/*  58 */     for (int i = 0; i < v.size(); i++) {
/*  59 */       if ((v.get(i) instanceof ifWeapon)) {
/*  60 */         ifWeapon w = (ifWeapon)v.get(i);
/*     */         
/*  62 */         boolean add = true;
/*  63 */         for (int x = 0; x < wep.size(); x++) {
/*  64 */           if (((ifWeapon)wep.get(x)).LookupName().equals(w.LookupName())) {
/*  65 */             add = false;
/*     */           }
/*     */         }
/*  68 */         if (add) {
/*  69 */           wep.add(w);
/*  70 */           if (w.GetRangeLong() > cols) cols = w.GetRangeLong();
/*     */         }
/*  72 */       } else if ((v.get(i) instanceof Ammunition)) {
/*  73 */         Ammunition a = (Ammunition)v.get(i);
/*     */         
/*  75 */         boolean add = true;
/*  76 */         for (int x = 0; x < ammo.size(); x++) {
/*  77 */           if (((Ammunition)ammo.get(x)).LookupName().equals(a.LookupName())) {
/*  78 */             add = false;
/*     */           }
/*     */         }
/*  81 */         if (add) {
/*  82 */           ammo.add(a);
/*  83 */           if (a.GetLongRange() > cols) { cols = a.GetLongRange();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  89 */     for (int i = 0; i < wep.size(); i++) {
/*  90 */       ifWeapon w = (ifWeapon)wep.get(i);
/*  91 */       if (w.HasAmmo()) {
/*  92 */         boolean added = false;
/*     */         
/*  94 */         for (int x = ammo.size() - 1; x >= 0; x--) {
/*  95 */           Ammunition a = (Ammunition)ammo.get(x);
/*  96 */           if (w.GetAmmoIndex() == a.GetAmmoIndex()) {
/*  97 */             temp.add(new WeaponInfo(w, a));
/*  98 */             ammo.remove(a);
/*  99 */             added = true;
/*     */           }
/*     */         }
/* 102 */         if (!added)
/*     */         {
/* 104 */           temp.add(new WeaponInfo(w));
/*     */         }
/*     */       } else {
/* 107 */         temp.add(new WeaponInfo(w));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 112 */     SortWeapons(temp);
/*     */     
/*     */ 
/* 115 */     cols++;
/* 116 */     this.data = new String[temp.size() * 2][cols];
/* 117 */     for (int i = 0; i < temp.size(); i++) {
/* 118 */       WeaponInfo w = (WeaponInfo)temp.get(i);
/* 119 */       for (int x = 0; x < cols; x++) {
/* 120 */         if (x == 0) {
/* 121 */           this.data[(i * 2)][x] = w.GetName();
/* 122 */           this.data[(i * 2 + 1)][x] = "";
/*     */         } else {
/* 124 */           int tohit = w.GetToHit(x);
/* 125 */           if ((this.CurMech.UsingTC()) && 
/* 126 */             (tohit != 12) && (w.CanUseTC())) { tohit--;
/*     */           }
/* 128 */           if (tohit >= 0) {
/* 129 */             this.data[(i * 2)][x] = ("+" + tohit);
/*     */           } else {
/* 131 */             this.data[(i * 2)][x] = ("" + tohit);
/*     */           }
/* 133 */           this.data[(i * 2 + 1)][x] = ("" + w.GetDamage(x));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void ResetTable() {
/* 140 */     this.tblBrackets.setDefaultRenderer(Object.class, new BracketRenderer(null));
/* 141 */     this.tblBrackets.setModel(new AbstractTableModel()
/*     */     {
/*     */       public String getColumnName(int col) {
/* 144 */         if (col == 0) {
/* 145 */           return "Weapon Name";
/*     */         }
/* 147 */         return "" + col;
/*     */       }
/*     */       
/* 150 */       public int getRowCount() { return dlgBracketCharts.this.data.length; }
/* 151 */       public int getColumnCount() { return dlgBracketCharts.this.data[0].length; }
/*     */       
/* 153 */       public Object getValueAt(int row, int col) { if (row % 2 == 1)
/*     */         {
/* 155 */           if (dlgBracketCharts.this.data[row][col].equals("0")) {
/* 156 */             return "-";
/*     */           }
/* 158 */           return dlgBracketCharts.this.data[row][col];
/*     */         }
/*     */         
/*     */ 
/* 162 */         if (dlgBracketCharts.this.data[row][col].equals("+12")) {
/* 163 */           return "-";
/*     */         }
/* 165 */         return dlgBracketCharts.this.data[row][col];
/*     */       }
/*     */     });
/*     */     
/*     */ 
/* 170 */     for (int i = 1; i < this.tblBrackets.getColumnCount(); i++) {
/* 171 */       this.tblBrackets.getColumnModel().getColumn(i).setPreferredWidth(35);
/* 172 */       this.tblBrackets.getColumnModel().getColumn(i).setMinWidth(35);
/* 173 */       this.tblBrackets.getColumnModel().getColumn(i).setMaxWidth(35);
/*     */     }
/* 175 */     this.tblBrackets.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer());
/* 176 */     this.tblBrackets.getColumnModel().getColumn(0).setMinWidth(125);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void SortWeapons(ArrayList<WeaponInfo> v)
/*     */   {
/* 184 */     int i = 1;int j = 2;
/*     */     
/* 186 */     while (i < v.size())
/*     */     {
/* 188 */       if (((WeaponInfo)v.get(i - 1)).GetLongRange() >= ((WeaponInfo)v.get(i)).GetLongRange()) {
/* 189 */         i = j;
/* 190 */         j++;
/*     */       } else {
/* 192 */         WeaponInfo swap = (WeaponInfo)v.get(i - 1);
/* 193 */         v.set(i - 1, v.get(i));
/* 194 */         v.set(i, swap);
/* 195 */         i--;
/* 196 */         if (i == 0)
/* 197 */           i = 1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private class BracketRenderer extends DefaultTableCellRenderer {
/*     */     private BracketRenderer() {}
/*     */     
/*     */     public int getHorizontalAlignment() {
/* 206 */       return 0;
/*     */     }
/*     */     
/*     */     public int getVerticalAlignment()
/*     */     {
/* 211 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeaponInfo {
/*     */     private ifWeapon wep;
/*     */     private Ammunition ammo;
/*     */     
/* 219 */     public WeaponInfo(ifWeapon w) { this.wep = w; }
/* 220 */     public WeaponInfo(ifWeapon w, Ammunition a) { this.wep = w;this.ammo = a;
/*     */     }
/*     */     
/* 223 */     public String GetName() { if (HasAmmo()) return this.ammo.CritName().replace("@", "");
/* 224 */       return this.wep.CritName();
/*     */     }
/*     */     
/*     */     public boolean HasAmmo() {
/* 228 */       if (this.ammo == null) return false;
/* 229 */       return true;
/*     */     }
/*     */     
/*     */     public int GetToHit(int range) {
/* 233 */       if (HasAmmo()) {
/* 234 */         return CommonTools.GetToHitAtRange(this.ammo, range);
/*     */       }
/* 236 */       return CommonTools.GetToHitAtRange(this.wep, range);
/*     */     }
/*     */     
/*     */     public int GetDamage(int range) {
/* 240 */       if (HasAmmo()) {
/* 241 */         return CommonTools.GetAverageDamageAtRange(this.wep, this.ammo, range);
/*     */       }
/* 243 */       return CommonTools.GetAverageDamageAtRange(this.wep, range);
/*     */     }
/*     */     
/*     */     public int GetLongRange() {
/* 247 */       if (HasAmmo()) return this.ammo.GetLongRange();
/* 248 */       return this.wep.GetRangeLong();
/*     */     }
/*     */     
/*     */     public boolean CanUseTC() {
/* 252 */       return this.wep.IsTCCapable();
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
/*     */   private void initComponents()
/*     */   {
/* 265 */     this.jScrollPane1 = new JScrollPane();
/* 266 */     this.tblBrackets = new JTable();
/* 267 */     this.btnClose = new JButton();
/*     */     
/* 269 */     setDefaultCloseOperation(2);
/*     */     
/* 271 */     this.jScrollPane1.setHorizontalScrollBarPolicy(32);
/* 272 */     this.jScrollPane1.setVerticalScrollBarPolicy(22);
/*     */     
/* 274 */     this.tblBrackets.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null }, { null, null, null, null } }, new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
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
/* 285 */     this.tblBrackets.setAutoResizeMode(0);
/* 286 */     this.tblBrackets.setAutoscrolls(false);
/* 287 */     this.jScrollPane1.setViewportView(this.tblBrackets);
/*     */     
/* 289 */     this.btnClose.setText("Close");
/* 290 */     this.btnClose.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 292 */         dlgBracketCharts.this.btnCloseActionPerformed(evt);
/*     */       }
/*     */       
/* 295 */     });
/* 296 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 297 */     getContentPane().setLayout(layout);
/* 298 */     layout.setHorizontalGroup(layout
/* 299 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 300 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 301 */       .addContainerGap()
/* 302 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 303 */       .addComponent(this.jScrollPane1, GroupLayout.Alignment.LEADING, -1, 722, 32767)
/* 304 */       .addComponent(this.btnClose))
/* 305 */       .addContainerGap()));
/*     */     
/* 307 */     layout.setVerticalGroup(layout
/* 308 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 309 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 310 */       .addContainerGap()
/* 311 */       .addComponent(this.jScrollPane1, -1, 266, 32767)
/* 312 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 313 */       .addComponent(this.btnClose)
/* 314 */       .addContainerGap()));
/*     */     
/*     */ 
/* 317 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCloseActionPerformed(ActionEvent evt) {
/* 321 */     dispose();
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgBracketCharts.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */