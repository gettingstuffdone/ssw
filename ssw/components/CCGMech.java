/*     */ package ssw.components;
/*     */ 
/*     */ import components.PhysicalWeapon;
/*     */ import components.RangedWeapon;
/*     */ import components.abPlaceable;
/*     */ import components.ifWeapon;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class CCGMech
/*     */ {
/*  41 */   private String Name = ""; private String FullName = ""; private String ImageSrc = ""; private String Faction = ""; private String SpecialAbilities = ""; private String FlavourText = ""; private String IllustratorName = ""; private String Loadout = ""; private String Cost = "";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  50 */   private char MovementRate = 'S';
/*  51 */   private int ResourceCost = 0; private int Mass = 0; private int MunitionsAttributeCost = 0; private int TacticsAttributeCost = 0; private int LogisticsAttributeCost = 0; private int PoliticsAttributeCost = 0; private int AssemblyAttributeCost = 0; private int AttackValue = 0; private int ArmourValue = 0; private int StructureValue = 0; private int MissileDamage = 0; private int OverheatDamage = 0; private int AlphaStrikeDamage = 0; private int LeakDamage = 0;
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
/*  68 */   private static String AblSlow = " cannot block unless guarding.\n";
/*  69 */   private static String AblDasher1 = "You may move ";
/*  70 */   private static String AblDasher2 = " to your Patrol region as soon as you activate it.\n";
/*  71 */   private static String AblAP1 = "AP (";
/*  72 */   private static String AblAP2 = " deals +1 damage to any target other than a 'Mech)\n";
/*  73 */   private static String AblPA1 = "If ";
/*  74 */   private static String AblPA2 = " blocks or is blocked by at least one ";
/*  75 */   private static String AblPA3 = " 'Mech, ";
/*  76 */   private static String AblPA4 = " deals +1 damage to one of those ";
/*  77 */   private static String AblPA5 = " 'Mech.\n";
/*  78 */   private static String AblJump = "Jump (-1 attack: +1 initiative)\n";
/*  79 */   private static String AblLeak1 = "If the ";
/*  80 */   private static String AblLeak2 = " attacks and is blocked, it may deal ";
/*  81 */   private static String AblLeak3 = " of its damage to the target.\n";
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
/*     */   public CCGMech(String name, String fullname)
/*     */   {
/*  95 */     this.Name = name;
/*  96 */     this.FullName = fullname;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMovementRate(int walkSpeed)
/*     */   {
/* 118 */     if (walkSpeed <= 3) {
/* 119 */       this.MovementRate = 'S';
/* 120 */       if (walkSpeed < 3) {
/* 121 */         this.SpecialAbilities = (this.SpecialAbilities + this.Name + AblSlow + "\n");
/*     */       }
/* 123 */     } else if (walkSpeed <= 6) {
/* 124 */       this.MovementRate = 'M';
/* 125 */     } else if (walkSpeed > 6) {
/* 126 */       this.MovementRate = 'F';
/* 127 */       if (walkSpeed >= 9) {
/* 128 */         this.SpecialAbilities = (this.SpecialAbilities + AblDasher1 + this.Name + AblDasher2 + "\n");
/*     */       }
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
/*     */ 
/*     */ 
/*     */   public void setJump(int jump)
/*     */   {
/* 145 */     if (jump > 0) {
/* 146 */       this.SpecialAbilities = (this.SpecialAbilities + AblJump + "\n");
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
/*     */   public void setMass(int mass)
/*     */   {
/* 159 */     this.Mass = mass;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAttackValue(ArrayList CurrentLoadout, int MovementHeat, int TotalHeatDis)
/*     */   {
/* 211 */     double attackValue = 0.0D;
/* 212 */     double missileValue = 0.0D;
/* 213 */     int vanPPCERLLcount = 0;
/* 214 */     List<WeaponData> weaponList = new ArrayList();
/* 215 */     if (CurrentLoadout.size() > 0) {
/* 216 */       for (int i = 0; i < CurrentLoadout.size(); i++) {
/* 217 */         if ((CurrentLoadout.get(i) instanceof ifWeapon)) {
/* 218 */           ifWeapon currentWeapon = (ifWeapon)CurrentLoadout.get(i);
/* 219 */           double currentWeaponAttackValue = 0.0D;
/* 220 */           double currentMissileValue = 0.0D;
/*     */           
/*     */ 
/*     */ 
/* 224 */           boolean notRear = true;
/* 225 */           boolean notMG = true;
/* 226 */           boolean physical = false;
/* 227 */           boolean missile = false;
/* 228 */           if (((abPlaceable)currentWeapon).IsMountedRear()) {
/* 229 */             notRear = false;
/* 230 */           } else if (currentWeapon.LookupName().contains("Machine Gun")) {
/* 231 */             notMG = false;
/* 232 */             this.SpecialAbilities = (this.SpecialAbilities + AblAP1 + this.Name + AblAP2 + "\n");
/* 233 */           } else if ((currentWeapon instanceof PhysicalWeapon)) {
/* 234 */             physical = true;
/* 235 */             if (this.Mass >= 80) {
/* 236 */               currentWeaponAttackValue = 2.0D;
/*     */             } else {
/* 238 */               currentWeaponAttackValue = 1.0D;
/*     */             }
/* 240 */             this.SpecialAbilities = (this.SpecialAbilities + AblPA1 + this.Name + AblPA2 + this.MovementRate + AblPA3 + this.Name + AblPA4 + this.MovementRate + AblPA5 + "\n");
/* 241 */           } else if (currentWeapon.GetWeaponClass() == 2)
/*     */           {
/* 243 */             missile = true;
/* 244 */             int clusterSize = currentWeapon.ClusterSize();
/* 245 */             if (currentWeapon.GetRangeLong() > 15)
/*     */             {
/* 247 */               if (clusterSize == 5) {
/* 248 */                 currentMissileValue = 3.0D;
/* 249 */               } else if (clusterSize == 10) {
/* 250 */                 currentMissileValue = 6.0D;
/* 251 */               } else if (clusterSize == 15) {
/* 252 */                 currentMissileValue = 9.0D;
/* 253 */               } else if (clusterSize == 20) {
/* 254 */                 currentMissileValue = 12.0D;
/*     */               }
/* 256 */               if (((RangedWeapon)currentWeapon).GetFCS() != null) {
/* 257 */                 currentMissileValue = currentMissileValue * 4.0D / 3.0D;
/*     */               }
/* 259 */               if (currentWeapon.GetRangeMin() > 0) {
/* 260 */                 currentMissileValue *= 0.75D;
/*     */               }
/* 262 */               currentMissileValue /= 8.0D;
/* 263 */               missileValue += currentMissileValue;
/* 264 */             } else if (currentWeapon.GetRangeLong() > 12)
/*     */             {
/* 266 */               if (clusterSize == 10) {
/* 267 */                 currentMissileValue = 6.0D;
/* 268 */               } else if (clusterSize == 20) {
/* 269 */                 currentMissileValue = 12.0D;
/* 270 */               } else if (clusterSize == 30) {
/* 271 */                 currentMissileValue = 18.0D;
/* 272 */               } else if (clusterSize == 40) {
/* 273 */                 currentMissileValue = 24.0D;
/*     */               }
/* 275 */               currentMissileValue = currentMissileValue * 0.8D / 7.0D;
/* 276 */               attackValue += currentMissileValue;
/* 277 */             } else if (currentWeapon.GetRangeLong() > 9)
/*     */             {
/* 279 */               if (clusterSize == 2) {
/* 280 */                 currentMissileValue = 4.0D;
/* 281 */               } else if (clusterSize == 4) {
/* 282 */                 currentMissileValue = 8.0D;
/* 283 */               } else if (clusterSize == 6) {
/* 284 */                 currentMissileValue = 12.0D;
/*     */               }
/* 286 */               currentMissileValue /= 7.0D;
/* 287 */               attackValue += currentMissileValue;
/*     */             }
/*     */             else {
/* 290 */               if (clusterSize == 2) {
/* 291 */                 currentMissileValue = 2.0D;
/* 292 */               } else if (clusterSize == 4) {
/* 293 */                 currentMissileValue = 6.0D;
/* 294 */               } else if (clusterSize == 6) {
/* 295 */                 currentMissileValue = 4.0D;
/*     */               }
/* 297 */               if (currentWeapon.IsStreak()) {
/* 298 */                 currentMissileValue = clusterSize * 2;
/* 299 */               } else if (((RangedWeapon)currentWeapon).GetFCS() != null) {
/* 300 */                 currentMissileValue += 2.0D;
/*     */               }
/* 302 */               currentMissileValue /= 9.0D;
/* 303 */               attackValue += currentMissileValue;
/*     */             }
/* 305 */           } else if (currentWeapon.GetWeaponClass() == 0) {
/* 306 */             if (currentWeapon.GetDamageLong() == 20) {
/* 307 */               this.AlphaStrikeDamage += 2;
/* 308 */               if (currentWeapon.GetRangeLong() >= 12) {
/* 309 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 7.0D / 2.0D;
/*     */               } else {
/* 311 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 9.0D / 2.0D;
/*     */               }
/* 313 */             } else if (currentWeapon.GetDamageLong() == 10)
/*     */             {
/* 315 */               this.AlphaStrikeDamage += 1;
/* 316 */               if (currentWeapon.GetRangeLong() >= 18) {
/* 317 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 5.0D / 2.0D * 0.75D;
/* 318 */                 this.LeakDamage += 1;
/* 319 */               } else if (currentWeapon.GetRangeLong() >= 12) {
/* 320 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 7.0D / 2.0D;
/* 321 */               } else if (currentWeapon.GetRangeLong() >= 7) {
/* 322 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 9.0D / 2.0D;
/*     */               } else {
/* 324 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 11.0D / 2.0D;
/*     */               }
/* 326 */             } else if (currentWeapon.GetDamageLong() == 15)
/*     */             {
/* 328 */               currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 5.0D / 2.0D * 0.75D;
/* 329 */               this.LeakDamage += 1;
/*     */             } else {
/* 331 */               if (currentWeapon.GetRangeLong() >= 18) {
/* 332 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 5.0D;
/* 333 */               } else if (currentWeapon.GetRangeLong() >= 12) {
/* 334 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 7.0D;
/* 335 */               } else if (currentWeapon.GetRangeLong() >= 7) {
/* 336 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 9.0D;
/*     */               } else {
/* 338 */                 currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 11.0D;
/*     */               }
/* 340 */               if (currentWeapon.GetRangeMin() > 0) {
/* 341 */                 currentWeaponAttackValue *= 0.75D;
/*     */               }
/*     */             }
/*     */           } else {
/* 345 */             if (currentWeapon.GetRangeLong() >= 18) {
/* 346 */               currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 5.0D;
/* 347 */               if (currentWeapon.GetRangeLong() >= 19) {
/* 348 */                 this.LeakDamage += 1;
/*     */               } else {
/* 350 */                 vanPPCERLLcount++;
/*     */               }
/* 352 */               if (vanPPCERLLcount > 2) {
/* 353 */                 vanPPCERLLcount = 0;
/* 354 */                 this.LeakDamage += 1;
/*     */               }
/* 356 */             } else if (currentWeapon.GetRangeLong() >= 12) {
/* 357 */               currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 7.0D;
/* 358 */             } else if (currentWeapon.GetRangeLong() >= 7) {
/* 359 */               currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 9.0D;
/*     */             } else {
/* 361 */               currentWeaponAttackValue = currentWeapon.GetDamageMedium() / 11.0D;
/*     */             }
/* 363 */             if (currentWeapon.GetRangeMin() > 0) {
/* 364 */               currentWeaponAttackValue *= 0.75D;
/*     */             }
/* 366 */             if (currentWeapon.GetWeaponClass() == 1) {
/* 367 */               currentWeaponAttackValue *= 1.3D;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 372 */           if (currentWeaponAttackValue > 0.0D) {
/* 373 */             WeaponData wep = new WeaponData(null);
/* 374 */             wep.Damage = currentWeaponAttackValue;
/* 375 */             wep.Heat = currentWeapon.GetHeat();
/* 376 */             weaponList.add(wep);
/* 377 */             attackValue += currentWeaponAttackValue;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 388 */     Collections.sort(weaponList);
/* 389 */     System.out.println("Sorted");
/* 390 */     WeaponData wep; for (int c = 0; c < weaponList.size(); c++) {
/* 391 */       wep = (WeaponData)weaponList.get(c);
/*     */     }
/*     */     
/*     */ 
/* 395 */     int heat = MovementHeat;
/* 396 */     for (int c = 0; c < weaponList.size(); c++) {
/* 397 */       heat += ((WeaponData)weaponList.get(c)).Heat;
/*     */     }
/* 399 */     double overHeatDamage = 0.0D;
/* 400 */     double overHeatHeat = 0.0D;
/* 401 */     if (heat > TotalHeatDis) {
/* 402 */       for (int c = 0; c < weaponList.size(); c++) {
/* 403 */         heat -= ((WeaponData)weaponList.get(c)).Heat;
/* 404 */         overHeatHeat += ((WeaponData)weaponList.get(c)).Heat;
/* 405 */         attackValue -= ((WeaponData)weaponList.get(c)).Damage;
/* 406 */         overHeatDamage += ((WeaponData)weaponList.get(c)).Damage;
/* 407 */         if (heat <= TotalHeatDis) {
/*     */           break;
/*     */         }
/*     */       }
/* 411 */       overHeatHeat /= 5.0D;
/* 412 */       if ((int)overHeatHeat == 0) {
/* 413 */         overHeatHeat = 1.0D;
/*     */       }
/* 415 */       if ((int)overHeatDamage == 0) {
/* 416 */         overHeatDamage = 1.0D;
/*     */       }
/* 418 */       this.SpecialAbilities = (this.SpecialAbilities + "Overheat " + (int)overHeatHeat + ": " + (int)overHeatDamage + "\n");
/*     */     }
/* 420 */     this.AttackValue = ((int)attackValue);
/*     */     
/* 422 */     this.MissileDamage = ((int)missileValue);
/*     */     
/* 424 */     if (this.AlphaStrikeDamage > 0) {
/* 425 */       this.SpecialAbilities = (this.SpecialAbilities + "AlphaStrike: " + this.AlphaStrikeDamage + "\n");
/*     */     }
/* 427 */     if (this.LeakDamage > 0) {
/* 428 */       this.SpecialAbilities = (this.SpecialAbilities + AblLeak1 + this.Name + AblLeak2 + this.LeakDamage + AblLeak3 + "\n");
/*     */     }
/*     */     
/* 431 */     if (this.MissileDamage > 0) {
/* 432 */       this.SpecialAbilities = (this.SpecialAbilities + "Missile: " + this.MissileDamage + "\n");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setArmourAndStructure(int armourPoints)
/*     */   {
/* 444 */     if (armourPoints < 30) {
/* 445 */       this.ArmourValue = 0;
/* 446 */       this.StructureValue = 1;
/* 447 */     } else if (armourPoints < 50) {
/* 448 */       this.ArmourValue = 0;
/* 449 */       this.StructureValue = 2;
/* 450 */     } else if (armourPoints < 70) {
/* 451 */       if (this.MovementRate == 'F') {
/* 452 */         this.ArmourValue = 0;
/* 453 */         this.StructureValue = 3;
/*     */       } else {
/* 455 */         this.ArmourValue = 1;
/* 456 */         this.StructureValue = 2;
/*     */       }
/* 458 */     } else if (armourPoints < 90) {
/* 459 */       if (this.MovementRate == 'F') {
/* 460 */         this.ArmourValue = 0;
/* 461 */         this.StructureValue = 4;
/*     */       } else {
/* 463 */         this.ArmourValue = 1;
/* 464 */         this.StructureValue = 3;
/*     */       }
/* 466 */     } else if (armourPoints < 110) {
/* 467 */       if (this.MovementRate == 'F') {
/* 468 */         this.ArmourValue = 0;
/* 469 */         this.StructureValue = 5;
/* 470 */       } else if (this.Mass < 40) {
/* 471 */         this.ArmourValue = 0;
/* 472 */         this.StructureValue = 5;
/*     */       } else {
/* 474 */         this.ArmourValue = 1;
/* 475 */         this.StructureValue = 4;
/*     */       }
/* 477 */     } else if (armourPoints < 130) {
/* 478 */       if (this.MovementRate == 'F') {
/* 479 */         this.ArmourValue = 1;
/* 480 */         this.StructureValue = 5;
/* 481 */       } else if (this.Mass < 60) {
/* 482 */         this.ArmourValue = 1;
/* 483 */         this.StructureValue = 5;
/*     */       } else {
/* 485 */         this.ArmourValue = 2;
/* 486 */         this.StructureValue = 4;
/*     */       }
/* 488 */     } else if (armourPoints < 150) {
/* 489 */       if (this.MovementRate == 'F') {
/* 490 */         this.ArmourValue = 1;
/* 491 */         this.StructureValue = 6;
/* 492 */       } else if (this.Mass < 60) {
/* 493 */         this.ArmourValue = 1;
/* 494 */         this.StructureValue = 6;
/*     */       } else {
/* 496 */         this.ArmourValue = 2;
/* 497 */         this.StructureValue = 5;
/*     */       }
/* 499 */     } else if (armourPoints < 170) {
/* 500 */       if (this.MovementRate == 'F') {
/* 501 */         this.ArmourValue = 1;
/* 502 */         this.StructureValue = 7;
/* 503 */       } else if (this.Mass < 60) {
/* 504 */         this.ArmourValue = 1;
/* 505 */         this.StructureValue = 7;
/*     */       } else {
/* 507 */         this.ArmourValue = 2;
/* 508 */         this.StructureValue = 6;
/*     */       }
/* 510 */     } else if (armourPoints < 190) {
/* 511 */       if (this.MovementRate == 'F') {
/* 512 */         this.ArmourValue = 1;
/* 513 */         this.StructureValue = 8;
/* 514 */       } else if (this.Mass < 60) {
/* 515 */         this.ArmourValue = 1;
/* 516 */         this.StructureValue = 8;
/* 517 */       } else if (this.Mass >= 80) {
/* 518 */         this.ArmourValue = 3;
/* 519 */         this.StructureValue = 6;
/*     */       } else {
/* 521 */         this.ArmourValue = 2;
/* 522 */         this.StructureValue = 7;
/*     */       }
/* 524 */     } else if (armourPoints < 210) {
/* 525 */       if (this.Mass >= 80) {
/* 526 */         this.ArmourValue = 2;
/* 527 */         this.StructureValue = 8;
/*     */       } else {
/* 529 */         this.ArmourValue = 3;
/* 530 */         this.StructureValue = 7;
/*     */       }
/* 532 */     } else if (armourPoints < 230) {
/* 533 */       if (this.Mass >= 80) {
/* 534 */         this.ArmourValue = 2;
/* 535 */         this.StructureValue = 9;
/*     */       } else {
/* 537 */         this.ArmourValue = 3;
/* 538 */         this.StructureValue = 8;
/*     */       }
/* 540 */     } else if (armourPoints < 250) {
/* 541 */       this.ArmourValue = 3;
/* 542 */       this.StructureValue = 9;
/* 543 */     } else if (armourPoints < 270) {
/* 544 */       this.ArmourValue = 3;
/* 545 */       this.StructureValue = 10;
/* 546 */     } else if (armourPoints < 290) {
/* 547 */       this.ArmourValue = 3;
/* 548 */       this.StructureValue = 11;
/*     */     } else {
/* 550 */       this.ArmourValue = 4;
/* 551 */       this.StructureValue = 11;
/*     */     }
/*     */   }
/*     */   
/*     */   public char getSpeed() {
/* 556 */     return this.MovementRate;
/*     */   }
/*     */   
/* 559 */   public String getName() { return this.FullName; }
/*     */   
/*     */   public int getMass() {
/* 562 */     return this.Mass;
/*     */   }
/*     */   
/* 565 */   public String getSpecial() { return this.SpecialAbilities; }
/*     */   
/*     */   public int getArmour() {
/* 568 */     return this.ArmourValue;
/*     */   }
/*     */   
/* 571 */   public int getStructure() { return this.StructureValue; }
/*     */   
/*     */ 
/* 574 */   public int getAttack() { return this.AttackValue; }
/*     */   
/*     */   private class WeaponData implements Comparable<WeaponData> {
/*     */     public int Heat;
/*     */     public double Damage;
/*     */     
/*     */     private WeaponData() {}
/*     */     
/* 582 */     public int compareTo(WeaponData o) { return (int)(this.Damage - o.Damage); }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\components\CCGMech.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */