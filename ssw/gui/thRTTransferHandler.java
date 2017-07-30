/*     */ package ssw.gui;
/*     */ 
/*     */ import components.CASE;
/*     */ import components.CASEII;
/*     */ import components.EmptyItem;
/*     */ import components.Engine;
/*     */ import components.LocationIndex;
/*     */ import components.Mech;
/*     */ import components.MechArmor;
/*     */ import components.MechTurret;
/*     */ import components.MultiSlotSystem;
/*     */ import components.PartialWing;
/*     */ import components.SimplePlaceable;
/*     */ import components.Supercharger;
/*     */ import components.VehicularGrenadeLauncher;
/*     */ import components.abPlaceable;
/*     */ import components.ifMechLoadout;
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JList.DropLocation;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.TransferHandler;
/*     */ import javax.swing.TransferHandler.TransferSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class thRTTransferHandler
/*     */   extends TransferHandler
/*     */ {
/*     */   private ifMechForm Parent;
/*     */   private Mech CurMech;
/*     */   
/*     */   public thRTTransferHandler(ifMechForm f, Mech m)
/*     */   {
/*  47 */     this.Parent = f;
/*  48 */     this.CurMech = m;
/*     */   }
/*     */   
/*     */ 
/*     */   public Transferable createTransferable(JComponent comp)
/*     */   {
/*  54 */     LocationDragDatagram d = new LocationDragDatagram();
/*  55 */     d.Location = 3;
/*  56 */     d.SourceIndex = ((JList)comp).getSelectedIndex();
/*  57 */     d.Locked = this.CurMech.GetLoadout().GetRTCrits()[d.SourceIndex].LocationLocked();
/*  58 */     if ((this.CurMech.GetLoadout().GetRTCrits()[d.SourceIndex] instanceof EmptyItem)) {
/*  59 */       d.Empty = true;
/*     */     }
/*  61 */     return d;
/*     */   }
/*     */   
/*     */   public boolean canImport(TransferHandler.TransferSupport info)
/*     */   {
/*  66 */     if (!info.isDrop())
/*     */     {
/*  68 */       return false;
/*     */     }
/*     */     
/*  71 */     if (!info.isDataFlavorSupported(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram")))
/*     */     {
/*  73 */       return false;
/*     */     }
/*     */     
/*  76 */     LocationDragDatagram DropItem = null;
/*     */     try {
/*  78 */       DropItem = (LocationDragDatagram)info.getTransferable().getTransferData(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram"));
/*     */     } catch (Exception e) {
/*  80 */       return false;
/*     */     }
/*     */     
/*  83 */     if (DropItem.Locked) {
/*  84 */       abPlaceable a = this.CurMech.GetLoadout().GetCrits(DropItem.Location)[DropItem.SourceIndex];
/*  85 */       if (((a instanceof CASE)) || ((a instanceof CASEII)) || ((a instanceof MultiSlotSystem)) || ((a instanceof Supercharger)) || ((a instanceof Engine)) || ((a instanceof SimplePlaceable)) || ((a instanceof PartialWing)) || ((a instanceof MechArmor)) || ((a instanceof MechTurret))) {
/*  86 */         if (DropItem.Location != 3) {
/*  87 */           return false;
/*     */         }
/*     */         
/*  90 */         JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/*  91 */         int dindex = dl.getIndex();
/*  92 */         if (((this.CurMech.GetLoadout().GetCrits(3)[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetCrits(3)[dindex].LocationLinked())) && (a != this.CurMech.GetLoadout().GetCrits(3)[dindex])) {
/*  93 */           return false;
/*     */         }
/*  95 */         if ((a instanceof CASE)) {
/*  96 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetRTCase() == a)) {
/*  97 */             return false;
/*     */           }
/*  99 */         } else if ((a instanceof CASEII)) {
/* 100 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetRTCaseII() == a)) {
/* 101 */             return false;
/*     */           }
/* 103 */         } else if ((a instanceof Supercharger)) {
/* 104 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetSupercharger() == a)) {
/* 105 */             return false;
/*     */           }
/* 107 */         } else if ((a instanceof MultiSlotSystem)) {
/* 108 */           if (this.CurMech.IsOmnimech()) {
/* 109 */             return false;
/*     */           }
/* 111 */         } else if ((a instanceof SimplePlaceable)) {
/* 112 */           if (this.CurMech.IsOmnimech()) {
/* 113 */             return false;
/*     */           }
/* 115 */         } else if ((a instanceof PartialWing)) {
/* 116 */           int Size = this.CurMech.GetPartialWing().NumCrits();
/* 117 */           abPlaceable[] Loc = this.CurMech.GetLoadout().GetRTCrits();
/*     */           try {
/* 119 */             for (int i = 0; i < Size; i++) {
/* 120 */               if (((Loc[(dindex + i)].LocationLocked()) || (Loc[(dindex + i)].LocationLinked())) && (a != Loc[(dindex + i)])) {
/* 121 */                 return false;
/*     */               }
/*     */             }
/*     */           } catch (Exception e) {
/* 125 */             return false;
/*     */           }
/* 127 */         } else if ((a instanceof MechArmor)) {
/* 128 */           if (this.CurMech.IsOmnimech()) {
/* 129 */             return false;
/*     */           }
/* 131 */           if ((this.CurMech.GetLoadout().GetRTCrits()[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetRTCrits()[dindex].LocationLocked())) {
/* 132 */             return false;
/*     */           }
/* 134 */         } else if ((a instanceof MechTurret)) {
/* 135 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasRTTurret())) {
/* 136 */             return false;
/*     */           }
/* 138 */         } else if ((a instanceof Engine)) {
/* 139 */           if (this.CurMech.IsOmnimech()) {
/* 140 */             return false;
/*     */           }
/*     */           
/*     */ 
/* 144 */           int Size = this.CurMech.GetEngine().GetSideTorsoCrits();
/* 145 */           abPlaceable[] Loc = this.CurMech.GetLoadout().GetCrits(3);
/*     */           try {
/* 147 */             for (int i = 0; i < Size; i++) {
/* 148 */               if (((Loc[(dindex + i)].LocationLocked()) || (Loc[(dindex + i)].LocationLinked())) && (a != Loc[(dindex + i)])) {
/* 149 */                 return false;
/*     */               }
/*     */             }
/*     */           } catch (Exception e) {
/* 153 */             return false;
/*     */           }
/*     */         }
/*     */         else {
/* 157 */           return false;
/*     */         }
/*     */       }
/*     */       else {
/* 161 */         return false;
/*     */       }
/*     */     }
/* 164 */     if (DropItem.Empty) { return false;
/*     */     }
/* 166 */     info.setShowDropLocation(true);
/*     */     
/*     */ 
/* 169 */     return true;
/*     */   }
/*     */   
/*     */   public int getSourceActions(JComponent c)
/*     */   {
/* 174 */     return 2;
/*     */   }
/*     */   
/*     */   public boolean importData(TransferHandler.TransferSupport info)
/*     */   {
/* 179 */     if (!canImport(info)) {
/* 180 */       System.out.println("couldn't import");
/* 181 */       return false;
/*     */     }
/*     */     
/* 184 */     LocationDragDatagram DropItem = null;
/* 185 */     boolean rear = false;
/*     */     try
/*     */     {
/* 188 */       DropItem = (LocationDragDatagram)info.getTransferable().getTransferData(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram"));
/*     */     } catch (Exception e) {
/* 190 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 194 */     JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/* 195 */     int dindex = dl.getIndex();
/*     */     
/*     */ 
/*     */ 
/* 199 */     ArrayList v = new ArrayList();
/* 200 */     abPlaceable a; abPlaceable a; if (DropItem.Location == -1)
/*     */     {
/* 202 */       a = this.CurMech.GetLoadout().GetFromQueueByIndex(DropItem.SourceIndex);
/*     */     }
/*     */     else {
/* 205 */       a = this.CurMech.GetLoadout().GetCrits(DropItem.Location)[DropItem.SourceIndex];
/* 206 */       rear = a.IsMountedRear();
/* 207 */       if ((a.CanSplit()) && (a.Contiguous()))
/*     */       {
/* 209 */         v = this.CurMech.GetLoadout().FindSplitIndex(a);
/* 210 */         this.CurMech.GetLoadout().UnallocateAll(a, false);
/*     */       } else {
/* 212 */         this.CurMech.GetLoadout().UnallocateByIndex(DropItem.SourceIndex, this.CurMech.GetLoadout().GetCrits(DropItem.Location));
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 218 */       if ((a.CanSplit()) && (a.Contiguous())) {
/* 219 */         if ((a instanceof PartialWing))
/*     */         {
/* 221 */           abPlaceable[] loc = this.CurMech.GetLoadout().GetRTCrits();
/* 222 */           for (int i = 0; i < loc.length; i++) {
/* 223 */             if (loc[i] == a) loc[i] = this.CurMech.GetLoadout().GetNoItem();
/*     */           }
/* 225 */           this.CurMech.GetLoadout().AddToRT(a, dindex);
/*     */         }
/* 227 */         else if (DropItem.Location == 3) {
/* 228 */           LocationIndex loc1 = null;
/* 229 */           LocationIndex loc2 = null;
/* 230 */           for (int i = 0; i < v.size(); i++) {
/* 231 */             if (((LocationIndex)v.get(i)).Location == 3) {
/* 232 */               loc1 = (LocationIndex)v.get(i);
/*     */             } else {
/* 234 */               loc2 = (LocationIndex)v.get(i);
/*     */             }
/*     */           }
/* 237 */           if (loc1 == null) { return false;
/*     */           }
/*     */           
/* 240 */           if (loc2 == null) {
/* 241 */             if (loc1.Number + dindex > this.CurMech.GetLoadout().GetCrits(loc1.Location).length) {
/* 242 */               return SplitAllocate(a, dindex);
/*     */             }
/* 244 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc1.Location), a, dindex, loc1.Number);
/*     */           }
/*     */           else {
/* 247 */             if (loc1.Number + dindex > this.CurMech.GetLoadout().GetCrits(loc1.Location).length) {
/* 248 */               return SplitAllocate(a, dindex);
/*     */             }
/* 250 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc1.Location), a, dindex, loc1.Number);
/* 251 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc2.Location), a, loc2.Index, loc2.Number);
/*     */           }
/*     */         }
/*     */         else {
/* 255 */           return SplitAllocate(a, dindex);
/*     */         }
/*     */       }
/*     */       else {
/* 259 */         this.CurMech.GetLoadout().AddToRT(a, dindex);
/*     */       }
/*     */     } catch (Exception e) {
/* 262 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 263 */       JOptionPane.showMessageDialog((JFrame)this.Parent, e.getMessage());
/* 264 */       this.Parent.RefreshInfoPane();
/* 265 */       return false;
/*     */     }
/* 267 */     if (a.NumPlaced() <= 0) {
/* 268 */       this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */     }
/* 270 */     a.MountRear(rear);
/* 271 */     if ((a instanceof VehicularGrenadeLauncher))
/*     */     {
/* 273 */       ((VehicularGrenadeLauncher)a).SetArcFore();
/*     */     }
/* 275 */     this.Parent.RefreshInfoPane();
/* 276 */     return true;
/*     */   }
/*     */   
/*     */   private boolean SplitAllocate(abPlaceable a, int dindex) throws Exception {
/* 280 */     int ToPlace = this.CurMech.GetLoadout().FreeFrom(this.CurMech.GetLoadout().GetRTCrits(), dindex);
/* 281 */     if (ToPlace < a.NumCrits()) {
/* 282 */       dlgSplitCrits dlgSplit = new dlgSplitCrits((JFrame)this.Parent, true, a, 3, dindex);
/* 283 */       Point p = ((JFrame)this.Parent).getLocationOnScreen();
/* 284 */       dlgSplit.setLocation(p.x + 100, p.y + 100);
/* 285 */       dlgSplit.setVisible(true);
/* 286 */       if (dlgSplit.GetResult()) {
/* 287 */         if (a.NumPlaced() <= 0) {
/* 288 */           this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */         }
/* 290 */         this.Parent.RefreshInfoPane();
/* 291 */         dlgSplit.dispose();
/* 292 */         return true;
/*     */       }
/* 294 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 295 */       this.Parent.RefreshInfoPane();
/* 296 */       dlgSplit.dispose();
/* 297 */       return false;
/*     */     }
/*     */     
/* 300 */     this.CurMech.GetLoadout().AddToRT(a, dindex);
/* 301 */     this.Parent.RefreshInfoPane();
/* 302 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\thRTTransferHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */