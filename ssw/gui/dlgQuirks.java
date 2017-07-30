/*     */ package ssw.gui;
/*     */ 
/*     */ import common.DataFactory;
/*     */ import components.Quirk;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
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
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import list.view.tbQuirks;
/*     */ 
/*     */ public class dlgQuirks
/*     */   extends JDialog
/*     */ {
/*     */   private DataFactory list;
/*     */   private ArrayList<Quirk> existingQuirks;
/*     */   private tbQuirks tblQ;
/*     */   private JButton btnAdd;
/*     */   private JButton btnDone;
/*     */   private JButton btnRemove;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JScrollPane jScrollPane2;
/*     */   private JLabel lblBattleMechQuirks;
/*     */   private JTable tblList;
/*     */   private JTable tblSelected;
/*     */   
/*     */   public dlgQuirks(Frame parent, boolean modal, DataFactory quirks, ArrayList<Quirk> currentQuirks)
/*     */   {
/*  43 */     super(parent, modal);
/*  44 */     initComponents();
/*     */     
/*  46 */     this.list = quirks;
/*  47 */     this.existingQuirks = currentQuirks;
/*     */     
/*  49 */     this.tblQ = new tbQuirks(currentQuirks);
/*  50 */     this.tblQ.setupTable(this.tblSelected);
/*  51 */     this.tblList.setModel(new tbQuirks(this.list.GetQuirks()));
/*     */   }
/*     */   
/*     */   private void Refresh()
/*     */   {
/*  56 */     this.tblQ.setupTable(this.tblSelected);
/*  57 */     this.tblQ.fireTableChanged(null);
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
/*  69 */     this.lblBattleMechQuirks = new JLabel();
/*  70 */     this.jScrollPane1 = new JScrollPane();
/*  71 */     this.tblList = new JTable();
/*  72 */     this.jScrollPane2 = new JScrollPane();
/*  73 */     this.tblSelected = new JTable();
/*  74 */     this.btnAdd = new JButton();
/*  75 */     this.btnRemove = new JButton();
/*  76 */     this.btnDone = new JButton();
/*     */     
/*  78 */     setDefaultCloseOperation(2);
/*     */     
/*  80 */     this.lblBattleMechQuirks.setFont(new Font("Arial", 1, 12));
/*  81 */     this.lblBattleMechQuirks.setText("BattleMech Quirks");
/*  82 */     this.lblBattleMechQuirks.setMaximumSize(new Dimension(175, 15));
/*  83 */     this.lblBattleMechQuirks.setMinimumSize(new Dimension(175, 15));
/*  84 */     this.lblBattleMechQuirks.setPreferredSize(new Dimension(175, 15));
/*     */     
/*  86 */     this.tblList.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null }, { null, null, null, null } }, new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
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
/*  97 */     this.jScrollPane1.setViewportView(this.tblList);
/*     */     
/*  99 */     this.tblSelected.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null }, { null, null, null, null } }, new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
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
/* 110 */     this.jScrollPane2.setViewportView(this.tblSelected);
/*     */     
/* 112 */     this.btnAdd.setText(">>");
/* 113 */     this.btnAdd.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 115 */         dlgQuirks.this.btnAddActionPerformed(evt);
/*     */       }
/*     */       
/* 118 */     });
/* 119 */     this.btnRemove.setText("<<");
/* 120 */     this.btnRemove.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 122 */         dlgQuirks.this.btnRemoveActionPerformed(evt);
/*     */       }
/*     */       
/* 125 */     });
/* 126 */     this.btnDone.setText("Done");
/* 127 */     this.btnDone.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 129 */         dlgQuirks.this.btnDoneActionPerformed(evt);
/*     */       }
/*     */       
/* 132 */     });
/* 133 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 134 */     getContentPane().setLayout(layout);
/* 135 */     layout.setHorizontalGroup(layout
/* 136 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 137 */       .addGroup(layout.createSequentialGroup()
/* 138 */       .addContainerGap()
/* 139 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 140 */       .addGroup(layout.createSequentialGroup()
/* 141 */       .addComponent(this.jScrollPane1, -2, 254, -2)
/* 142 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 143 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 144 */       .addComponent(this.btnRemove)
/* 145 */       .addComponent(this.btnAdd))
/* 146 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 13, 32767)
/* 147 */       .addComponent(this.jScrollPane2, -2, 245, -2))
/* 148 */       .addGroup(layout.createSequentialGroup()
/* 149 */       .addComponent(this.lblBattleMechQuirks, -2, -1, -2)
/* 150 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 348, 32767)
/* 151 */       .addComponent(this.btnDone)))
/* 152 */       .addContainerGap()));
/*     */     
/*     */ 
/* 155 */     layout.linkSize(0, new Component[] { this.jScrollPane1, this.jScrollPane2 });
/*     */     
/* 157 */     layout.setVerticalGroup(layout
/* 158 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 159 */       .addGroup(layout.createSequentialGroup()
/* 160 */       .addContainerGap()
/* 161 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 162 */       .addGroup(layout.createSequentialGroup()
/* 163 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 164 */       .addComponent(this.lblBattleMechQuirks, -2, -1, -2)
/* 165 */       .addComponent(this.btnDone))
/* 166 */       .addGap(19, 19, 19)
/* 167 */       .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 168 */       .addComponent(this.jScrollPane2, GroupLayout.Alignment.TRAILING, -1, 236, 32767)
/* 169 */       .addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 236, 32767))
/* 170 */       .addContainerGap())
/* 171 */       .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 172 */       .addComponent(this.btnAdd)
/* 173 */       .addGap(26, 26, 26)
/* 174 */       .addComponent(this.btnRemove)
/* 175 */       .addGap(111, 111, 111)))));
/*     */     
/*     */ 
/* 178 */     pack();
/*     */   }
/*     */   
/*     */   private void btnAddActionPerformed(ActionEvent evt) {
/* 182 */     Quirk q = (Quirk)((tbQuirks)this.tblList.getModel()).get(this.tblList.convertRowIndexToModel(this.tblList.getSelectedRow()));
/* 183 */     this.tblQ.quirklist.add(q);
/* 184 */     Refresh();
/*     */   }
/*     */   
/*     */   private void btnRemoveActionPerformed(ActionEvent evt) {
/* 188 */     Quirk q = (Quirk)((tbQuirks)this.tblSelected.getModel()).get(this.tblSelected.convertRowIndexToModel(this.tblSelected.getSelectedRow()));
/* 189 */     this.tblQ.quirklist.remove(q);
/* 190 */     Refresh();
/*     */   }
/*     */   
/*     */   private void btnDoneActionPerformed(ActionEvent evt) {
/* 194 */     setVisible(false);
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgQuirks.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */