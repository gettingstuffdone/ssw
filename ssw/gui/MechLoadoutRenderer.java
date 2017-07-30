/*     */ package ssw.gui;
/*     */ 
/*     */ import components.EmptyItem;
/*     */ import components.Equipment;
/*     */ import components.MGArray;
/*     */ import components.Mech;
/*     */ import components.MechArmor;
/*     */ import components.RangedWeapon;
/*     */ import components.abPlaceable;
/*     */ import components.ifMechLoadout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JList.DropLocation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MechLoadoutRenderer
/*     */   extends DefaultListCellRenderer
/*     */ {
/*     */   private ifMechForm Parent;
/*     */   private Color EmptyFG;
/*     */   private Color EmptyBG;
/*     */   private Color NormalFG;
/*     */   private Color NormalBG;
/*     */   private Color ArmoredFG;
/*     */   private Color ArmoredBG;
/*     */   private Color LinkedFG;
/*     */   private Color LinkedBG;
/*     */   private Color LockedFG;
/*     */   private Color LockedBG;
/*     */   private Color HiliteFG;
/*     */   private Color HiliteBG;
/*     */   
/*     */   public MechLoadoutRenderer(ifMechForm p)
/*     */   {
/*  54 */     this.Parent = p;
/*  55 */     Reset();
/*     */   }
/*     */   
/*     */   public void Reset()
/*     */   {
/*  60 */     this.EmptyFG = new Color(this.Parent.GetPrefs().getInt("ColorEmptyItemFG", -16777216));
/*  61 */     this.EmptyBG = new Color(this.Parent.GetPrefs().getInt("ColorEmptyItemBG", -6684775));
/*  62 */     this.NormalFG = new Color(this.Parent.GetPrefs().getInt("ColorNormalItemFG", -16777216));
/*  63 */     this.NormalBG = new Color(this.Parent.GetPrefs().getInt("ColorNormalItemBG", -10027009));
/*  64 */     this.ArmoredFG = new Color(this.Parent.GetPrefs().getInt("ColorArmoredItemFG", -1));
/*  65 */     this.ArmoredBG = new Color(this.Parent.GetPrefs().getInt("ColorArmoredItemBG", -6710887));
/*  66 */     this.LinkedFG = new Color(this.Parent.GetPrefs().getInt("ColorLinkedItemFG", -16777216));
/*  67 */     this.LinkedBG = new Color(this.Parent.GetPrefs().getInt("ColorLinkedItemBG", -3618616));
/*  68 */     this.LockedFG = new Color(this.Parent.GetPrefs().getInt("ColorLockedItemFG", -3342337));
/*  69 */     this.LockedBG = new Color(this.Parent.GetPrefs().getInt("ColorLockedItemBG", -16777216));
/*  70 */     this.HiliteFG = new Color(this.Parent.GetPrefs().getInt("ColorHiLiteItemFG", -16777216));
/*  71 */     this.HiliteBG = new Color(this.Parent.GetPrefs().getInt("ColorHiLiteItemBG", -52));
/*     */   }
/*     */   
/*     */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus)
/*     */   {
/*  76 */     JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
/*  77 */     String Text = "";
/*  78 */     abPlaceable[] Loc = null;
/*  79 */     int LocNum = -1;
/*  80 */     abPlaceable a = null;
/*  81 */     Color BorderCol = new Color(0, 0, 0);
/*     */     
/*  83 */     LocNum = this.Parent.GetLocation(list);
/*  84 */     switch (LocNum) {
/*     */     case 0: 
/*  86 */       Loc = this.Parent.GetMech().GetLoadout().GetHDCrits();
/*  87 */       break;
/*     */     case 1: 
/*  89 */       Loc = this.Parent.GetMech().GetLoadout().GetCTCrits();
/*  90 */       break;
/*     */     case 2: 
/*  92 */       Loc = this.Parent.GetMech().GetLoadout().GetLTCrits();
/*  93 */       break;
/*     */     case 3: 
/*  95 */       Loc = this.Parent.GetMech().GetLoadout().GetRTCrits();
/*  96 */       break;
/*     */     case 4: 
/*  98 */       Loc = this.Parent.GetMech().GetLoadout().GetLACrits();
/*  99 */       break;
/*     */     case 5: 
/* 101 */       Loc = this.Parent.GetMech().GetLoadout().GetRACrits();
/* 102 */       break;
/*     */     case 6: 
/* 104 */       Loc = this.Parent.GetMech().GetLoadout().GetLLCrits();
/* 105 */       break;
/*     */     case 7: 
/* 107 */       Loc = this.Parent.GetMech().GetLoadout().GetRLCrits();
/* 108 */       break;
/*     */     default: 
/* 110 */       Loc = null;
/*     */     }
/*     */     
/* 113 */     if ((value instanceof abPlaceable)) {
/* 114 */       a = (abPlaceable)value;
/* 115 */       if (a.IsArmored()) {
/* 116 */         label.setBackground(this.ArmoredBG);
/* 117 */         label.setForeground(this.ArmoredFG);
/* 118 */         BorderCol = this.ArmoredBG;
/* 119 */       } else if (a.LocationLinked()) {
/* 120 */         label.setBackground(this.LinkedBG);
/* 121 */         label.setForeground(this.LinkedFG);
/* 122 */         BorderCol = this.LinkedBG;
/* 123 */       } else if (a.LocationLocked()) {
/* 124 */         label.setBackground(this.LockedBG);
/* 125 */         label.setForeground(this.LockedFG);
/* 126 */         BorderCol = this.LockedBG;
/* 127 */       } else if ((a instanceof EmptyItem)) {
/* 128 */         label.setBackground(this.EmptyBG);
/* 129 */         label.setForeground(this.EmptyFG);
/* 130 */         BorderCol = this.EmptyBG;
/*     */       } else {
/* 132 */         label.setBackground(this.NormalBG);
/* 133 */         label.setForeground(this.NormalFG);
/* 134 */         BorderCol = this.NormalBG;
/*     */       }
/*     */       
/* 137 */       if (this.Parent.GetMech().GetLoadout().GetTechBase() == 2) {
/* 138 */         if ((a instanceof Equipment)) {
/* 139 */           if (((Equipment)a).IsVariableSize()) {
/* 140 */             Text = a.CritName();
/*     */           } else {
/* 142 */             Text = a.LookupName();
/*     */           }
/* 144 */         } else if ((a instanceof MechArmor)) {
/* 145 */           if (((MechArmor)a).IsPatchwork()) {
/* 146 */             Text = this.Parent.BuildLookupName(((MechArmor)a).GetLocationType(LocNum));
/*     */           } else {
/* 148 */             Text = a.LookupName();
/*     */           }
/*     */         } else {
/* 151 */           Text = a.LookupName();
/*     */         }
/*     */       }
/* 154 */       else if ((a instanceof MechArmor)) {
/* 155 */         if (((MechArmor)a).IsPatchwork()) {
/* 156 */           Text = ((MechArmor)a).CritName(LocNum);
/*     */         } else {
/* 158 */           Text = a.CritName();
/*     */         }
/*     */       } else {
/* 161 */         Text = a.CritName();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 166 */     label.setText(Text);
/*     */     
/* 168 */     JList.DropLocation dropLocation = list.getDropLocation();
/* 169 */     if ((dropLocation != null) && (dropLocation.getIndex() == index)) {
/* 170 */       int size = this.Parent.GetCurItem().NumCrits();
/* 171 */       if ((this.Parent.GetCurItem() instanceof RangedWeapon)) {
/* 172 */         if (((RangedWeapon)this.Parent.GetCurItem()).IsUsingFCS()) {
/* 173 */           size += ((abPlaceable)((RangedWeapon)this.Parent.GetCurItem()).GetFCS()).NumCrits();
/*     */         }
/* 175 */         if (((RangedWeapon)this.Parent.GetCurItem()).IsUsingCapacitor()) {
/* 176 */           size++;
/*     */         }
/* 178 */         if (((RangedWeapon)this.Parent.GetCurItem()).IsUsingInsulator()) {
/* 179 */           size++;
/*     */         }
/*     */       }
/* 182 */       if ((this.Parent.GetCurItem() instanceof MGArray)) {
/* 183 */         size += ((MGArray)this.Parent.GetCurItem()).GetNumMGs();
/*     */       }
/* 185 */       if (!this.Parent.GetCurItem().Contiguous()) {
/* 186 */         size = 1;
/*     */       }
/* 188 */       label.setBackground(this.HiliteBG);
/* 189 */       label.setForeground(this.HiliteFG);
/* 190 */       if (this.Parent.GetMech().GetLoadout().GetTechBase() == 2) {
/* 191 */         if ((this.Parent.GetCurItem() instanceof Equipment)) {
/* 192 */           if (((Equipment)this.Parent.GetCurItem()).IsVariableSize()) {
/* 193 */             Text = this.Parent.GetCurItem().CritName();
/*     */           } else {
/* 195 */             Text = this.Parent.GetCurItem().LookupName();
/*     */           }
/* 197 */         } else if ((this.Parent.GetCurItem() instanceof MechArmor)) {
/* 198 */           if (((MechArmor)this.Parent.GetCurItem()).IsPatchwork()) {
/* 199 */             Text = this.Parent.BuildLookupName(((MechArmor)this.Parent.GetCurItem()).GetLocationType(LocNum));
/*     */           } else {
/* 201 */             Text = this.Parent.BuildLookupName(((MechArmor)this.Parent.GetCurItem()).GetCurrentState());
/*     */           }
/*     */         } else {
/* 204 */           Text = this.Parent.GetCurItem().LookupName();
/*     */         }
/*     */       }
/* 207 */       else if ((this.Parent.GetCurItem() instanceof MechArmor)) {
/* 208 */         if (((MechArmor)this.Parent.GetCurItem()).IsPatchwork()) {
/* 209 */           Text = ((MechArmor)this.Parent.GetCurItem()).CritName(LocNum);
/*     */         } else {
/* 211 */           Text = this.Parent.GetCurItem().CritName();
/*     */         }
/*     */       } else {
/* 214 */         Text = this.Parent.GetCurItem().CritName();
/*     */       }
/*     */       
/* 217 */       if (size <= 1) {
/* 218 */         label.setText(Text);
/*     */       } else {
/* 220 */         label.setText("(" + size + ")" + Text);
/*     */       }
/*     */     }
/*     */     
/* 224 */     if (Loc == null) {
/* 225 */       label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
/*     */     }
/* 227 */     else if (index + 1 < Loc.length) {
/* 228 */       if ((((Loc[(index + 1)] == a ? 1 : 0) & (!(a instanceof EmptyItem) ? 1 : 0)) != 0) && (a.Contiguous()))
/*     */       {
/* 230 */         label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BorderCol));
/*     */       } else {
/* 232 */         label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
/*     */       }
/*     */     }
/*     */     else {
/* 236 */       label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
/*     */     }
/*     */     
/* 239 */     return label;
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\MechLoadoutRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */