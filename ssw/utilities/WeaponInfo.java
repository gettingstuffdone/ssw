/*     */ package ssw.utilities;
/*     */ 
/*     */ import components.Ammunition;
/*     */ import components.RangedWeapon;
/*     */ import components.ifMissileGuidance;
/*     */ import components.ifWeapon;
/*     */ import java.util.Vector;
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
/*     */ public class WeaponInfo
/*     */ {
/*     */   private ifWeapon Weapon;
/*  40 */   private Vector Ammos = new Vector();
/*  41 */   private int FiringRate = 1;
/*  42 */   public static final int[][] Clusters = { { 1, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 9, 9, 9, 10, 10, 12 }, { 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 9, 9, 9, 10, 10, 12 }, { 1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 18 }, { 1, 2, 2, 3, 3, 4, 4, 5, 6, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 13, 14, 15, 16, 16, 17, 17, 17, 18, 18, 24 }, { 1, 2, 2, 3, 4, 4, 5, 5, 6, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 13, 14, 15, 16, 16, 17, 17, 17, 18, 18, 24 }, { 1, 2, 3, 3, 4, 4, 5, 5, 6, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 13, 14, 15, 16, 16, 17, 17, 17, 18, 18, 24 }, { 2, 2, 3, 3, 4, 4, 5, 5, 6, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 13, 14, 15, 16, 16, 17, 17, 17, 18, 18, 24 }, { 2, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 11, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 20, 21, 21, 22, 23, 23, 24, 32 }, { 2, 3, 3, 4, 5, 6, 6, 7, 8, 9, 10, 11, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 20, 21, 21, 22, 23, 23, 24, 32 }, { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 40 }, { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 40 } };
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
/*     */   public WeaponInfo(ifWeapon w)
/*     */   {
/*  56 */     this.Weapon = w;
/*     */   }
/*     */   
/*     */   public void SetUltraRate(int r) {
/*  60 */     if ((this.Weapon.IsUltra() & ((r >= 1) && (r <= 2)))) {
/*  61 */       this.FiringRate = r;
/*     */     }
/*     */   }
/*     */   
/*     */   public void SetRotaryRate(int r) {
/*  66 */     if ((this.Weapon.IsRotary() & ((r >= 1) && (r <= 6)))) {
/*  67 */       this.FiringRate = r;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean HasAmmo() {
/*  72 */     return this.Weapon.HasAmmo();
/*     */   }
/*     */   
/*     */   public int GetAmmoIndex() {
/*  76 */     return this.Weapon.GetAmmoIndex();
/*     */   }
/*     */   
/*     */   public int GetHeat() {
/*  80 */     return this.Weapon.GetHeat();
/*     */   }
/*     */   
/*     */   public int GetFiringRate() {
/*  84 */     return this.FiringRate;
/*     */   }
/*     */   
/*     */   public int GetBestDamage(int range) {
/*  88 */     int retval = 0;
/*     */     
/*  90 */     if (!this.Weapon.HasAmmo())
/*     */     {
/*     */ 
/*  93 */       if (this.Weapon.IsCluster()) {
/*  94 */         return GetDamageAtRange(range) * this.Weapon.ClusterSize();
/*     */       }
/*  96 */       return GetDamageAtRange(range);
/*     */     }
/*     */     
/*     */ 
/* 100 */     return retval;
/*     */   }
/*     */   
/*     */   public int GetBestAverageDamage(int range) {
/* 104 */     int retval = 0;
/*     */     
/* 106 */     if (!this.Weapon.HasAmmo())
/*     */     {
/*     */ 
/* 109 */       if (this.Weapon.IsCluster()) {
/* 110 */         return GetDamageAtRange(range) * GetAverageClusterSize();
/*     */       }
/* 112 */       return GetDamageAtRange(range);
/*     */     }
/*     */     
/*     */ 
/* 116 */     return retval;
/*     */   }
/*     */   
/*     */   public void AddAmmo(Ammunition a) {
/* 120 */     if (a.GetAmmoIndex() == this.Weapon.GetAmmoIndex()) {
/* 121 */       this.Ammos.add(a);
/*     */     }
/*     */   }
/*     */   
/*     */   public void RemoveAmmo(Ammunition a) {
/* 126 */     this.Ammos.remove(a);
/*     */   }
/*     */   
/*     */   public void ClearAmmo() {
/* 130 */     this.Ammos.clear();
/*     */   }
/*     */   
/*     */   private int GetDamageAtRange(int range) {
/* 134 */     if (range <= this.Weapon.GetRangeShort())
/* 135 */       return this.Weapon.GetDamageShort();
/* 136 */     if (range <= this.Weapon.GetRangeMedium())
/* 137 */       return this.Weapon.GetDamageMedium();
/* 138 */     if (range <= this.Weapon.GetRangeLong()) {
/* 139 */       return this.Weapon.GetDamageLong();
/*     */     }
/* 141 */     return 0;
/*     */   }
/*     */   
/*     */   private int GetAverageClusterSize()
/*     */   {
/* 146 */     int index = 7;
/* 147 */     int size = this.Weapon.GetDamageShort() * this.Weapon.ClusterSize();
/* 148 */     if (((this.Weapon instanceof RangedWeapon)) && 
/* 149 */       (((RangedWeapon)this.Weapon).IsUsingFCS())) {
/* 150 */       index += ((RangedWeapon)this.Weapon).GetFCS().GetClusterTableBonus();
/*     */     }
/*     */     
/* 153 */     if (size > 30) {
/* 154 */       size = 31;
/*     */     }
/*     */     try {
/* 157 */       return Clusters[(index - 2)][(size - 2)];
/*     */     }
/*     */     catch (Exception e) {
/* 160 */       e.printStackTrace(); }
/* 161 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\utilities\WeaponInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */