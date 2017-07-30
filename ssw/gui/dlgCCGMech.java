/*     */ package ssw.gui;
/*     */ 
/*     */ import components.Mech;
/*     */ import java.awt.Color;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import ssw.components.CCGMech;
/*     */ 
/*     */ public class dlgCCGMech extends javax.swing.JDialog
/*     */ {
/*     */   private Mech CurMech;
/*     */   private String NL;
/*     */   private javax.swing.JButton btnClose;
/*     */   private JLabel lblArmour;
/*     */   private JLabel lblArtistAndCopyright;
/*     */   private JLabel lblAttack;
/*     */   private JLabel lblCardAbilitiesAndFlavour;
/*     */   private JLabel lblCardCostMassWeapons;
/*     */   private JLabel lblCardFactionAvailability;
/*     */   private JLabel lblCardPictureSpace;
/*     */   private JLabel lblCardTitle;
/*     */   private JLabel lblSpeed;
/*     */   
/*     */   public dlgCCGMech(java.awt.Frame parent, boolean modal, Mech m)
/*     */   {
/*  30 */     super(parent, modal);
/*  31 */     initComponents();
/*  32 */     this.CurMech = m;
/*  33 */     this.NL = System.getProperty("line.separator");
/*  34 */     CCGMech ccgMech = GenerateCCGCard();
/*  35 */     setTitle(this.CurMech.GetName() + " CCG Card");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private CCGMech GenerateCCGCard()
/*     */   {
/*  43 */     CCGMech card = new CCGMech(this.CurMech.GetName(), this.CurMech.GetFullName());
/*     */     
/*  45 */     card.setMass(this.CurMech.GetTonnage());
/*  46 */     card.setMovementRate(this.CurMech.GetWalkingMP());
/*  47 */     card.setArmourAndStructure(this.CurMech.GetArmor().GetArmorValue());
/*  48 */     card.setJump(this.CurMech.GetLoadout().GetJumpJets().GetNumJJ());
/*     */     
/*  50 */     components.ifMechLoadout loadout = this.CurMech.GetLoadout();
/*  51 */     java.util.ArrayList CurrentLoadout = loadout.GetNonCore();
/*  52 */     card.setAttackValue(CurrentLoadout, this.CurMech.GetBVMovementHeat(), this.CurMech.GetHeatSinks().TotalDissipation());
/*     */     
/*  54 */     this.lblCardTitle.setText(card.getName());
/*  55 */     this.lblCardCostMassWeapons.setText("Mass: " + card.getMass());
/*  56 */     this.lblCardAbilitiesAndFlavour.setText(card.getSpecial());
/*  57 */     this.lblArmour.setText(" " + card.getArmour() + "/" + card.getStructure());
/*  58 */     this.lblAttack.setText("  " + card.getAttack());
/*  59 */     this.lblSpeed.setText(card.getSpeed() + "");
/*     */     
/*  61 */     return card;
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
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  75 */     this.lblCardTitle = new JLabel();
/*  76 */     this.lblCardCostMassWeapons = new JLabel();
/*  77 */     this.lblCardPictureSpace = new JLabel();
/*  78 */     this.lblCardFactionAvailability = new JLabel();
/*  79 */     this.lblCardAbilitiesAndFlavour = new JLabel();
/*  80 */     this.lblArmour = new JLabel();
/*  81 */     this.lblArtistAndCopyright = new JLabel();
/*  82 */     this.lblAttack = new JLabel();
/*  83 */     this.btnClose = new javax.swing.JButton();
/*  84 */     this.lblSpeed = new JLabel();
/*     */     
/*  86 */     setDefaultCloseOperation(2);
/*     */     
/*  88 */     this.lblCardTitle.setText(" ");
/*  89 */     this.lblCardTitle.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */     
/*  91 */     this.lblCardCostMassWeapons.setText(" ");
/*  92 */     this.lblCardCostMassWeapons.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */     
/*  94 */     this.lblCardPictureSpace.setText("Picture Goes Here");
/*  95 */     this.lblCardPictureSpace.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */     
/*  97 */     this.lblCardFactionAvailability.setText(" ");
/*  98 */     this.lblCardFactionAvailability.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */     
/* 100 */     this.lblCardAbilitiesAndFlavour.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */     
/* 102 */     this.lblArmour.setText("jLabel1");
/* 103 */     this.lblArmour.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */     
/* 105 */     this.lblArtistAndCopyright.setText("jLabel2");
/* 106 */     this.lblArtistAndCopyright.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */     
/* 108 */     this.lblAttack.setText("jLabel3");
/* 109 */     this.lblAttack.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */     
/* 111 */     this.btnClose.setText("Close");
/* 112 */     this.btnClose.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(java.awt.event.ActionEvent evt) {
/* 114 */         dlgCCGMech.this.btnCloseActionPerformed(evt);
/*     */       }
/*     */       
/* 117 */     });
/* 118 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 119 */     getContentPane().setLayout(layout);
/* 120 */     layout.setHorizontalGroup(layout
/* 121 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 122 */       .addGroup(layout.createSequentialGroup()
/* 123 */       .addContainerGap()
/* 124 */       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 125 */       .addComponent(this.lblSpeed)
/* 126 */       .addComponent(this.lblCardTitle, -1, 186, 32767)
/* 127 */       .addGroup(layout.createSequentialGroup()
/* 128 */       .addComponent(this.lblCardCostMassWeapons, -2, 65, -2)
/* 129 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 130 */       .addComponent(this.lblCardPictureSpace, -1, 115, 32767))
/* 131 */       .addGroup(layout.createSequentialGroup()
/* 132 */       .addComponent(this.lblArmour, -2, 57, -2)
/* 133 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 134 */       .addComponent(this.lblArtistAndCopyright, -2, 53, -2)
/* 135 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 136 */       .addComponent(this.lblAttack, -2, 58, -2))
/* 137 */       .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 138 */       .addComponent(this.btnClose, -2, 69, -2)
/* 139 */       .addGap(54, 54, 54))
/* 140 */       .addComponent(this.lblCardAbilitiesAndFlavour, -1, 186, 32767)
/* 141 */       .addComponent(this.lblCardFactionAvailability, javax.swing.GroupLayout.Alignment.TRAILING, -1, 186, 32767))
/* 142 */       .addContainerGap()));
/*     */     
/*     */ 
/* 145 */     layout.linkSize(0, new java.awt.Component[] { this.lblArmour, this.lblArtistAndCopyright, this.lblAttack });
/*     */     
/* 147 */     layout.setVerticalGroup(layout
/* 148 */       .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
/* 149 */       .addGroup(layout.createSequentialGroup()
/* 150 */       .addComponent(this.lblCardTitle)
/* 151 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 152 */       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
/* 153 */       .addComponent(this.lblCardCostMassWeapons, -1, -1, 32767)
/* 154 */       .addComponent(this.lblCardPictureSpace, -1, 108, 32767))
/* 155 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 156 */       .addComponent(this.lblCardFactionAvailability)
/* 157 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 158 */       .addComponent(this.lblSpeed)
/* 159 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 160 */       .addComponent(this.lblCardAbilitiesAndFlavour, -2, 71, -2)
/* 161 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 162 */       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
/* 163 */       .addComponent(this.lblArmour, -1, -1, 32767)
/* 164 */       .addComponent(this.lblArtistAndCopyright, -1, -1, 32767)
/* 165 */       .addComponent(this.lblAttack, -1, 59, 32767))
/* 166 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 167 */       .addComponent(this.btnClose)
/* 168 */       .addContainerGap(25, 32767)));
/*     */     
/*     */ 
/* 171 */     pack();
/*     */   }
/*     */   
/*     */   private void btnCloseActionPerformed(java.awt.event.ActionEvent evt)
/*     */   {
/* 176 */     dispose();
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\dlgCCGMech.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */