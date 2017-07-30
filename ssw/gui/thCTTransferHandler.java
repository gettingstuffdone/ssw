/*     */ package ssw.gui;
/*     */ 
/*     */ import components.CASE;
/*     */ import components.CASEII;
/*     */ import components.Cockpit;
/*     */ import components.EmptyItem;
/*     */ import components.LocationIndex;
/*     */ import components.Mech;
/*     */ import components.MechArmor;
/*     */ import components.MechTurret;
/*     */ import components.MultiSlotSystem;
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
/*     */ 
/*     */ public class thCTTransferHandler
/*     */   extends TransferHandler
/*     */ {
/*     */   private ifMechForm Parent;
/*     */   private Mech CurMech;
/*     */   
/*     */   public thCTTransferHandler(ifMechForm f, Mech m)
/*     */   {
/*  47 */     this.Parent = f;
/*  48 */     this.CurMech = m;
/*     */   }
/*     */   
/*     */ 
/*     */   public Transferable createTransferable(JComponent comp)
/*     */   {
/*  54 */     LocationDragDatagram d = new LocationDragDatagram();
/*  55 */     d.Location = 1;
/*  56 */     d.SourceIndex = ((JList)comp).getSelectedIndex();
/*  57 */     d.Locked = this.CurMech.GetLoadout().GetCTCrits()[d.SourceIndex].LocationLocked();
/*  58 */     if ((this.CurMech.GetLoadout().GetCTCrits()[d.SourceIndex] instanceof EmptyItem)) {
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
/*  80 */       System.out.println(e);
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     if (DropItem.Locked) {
/*  85 */       abPlaceable a = this.CurMech.GetLoadout().GetCrits(DropItem.Location)[DropItem.SourceIndex];
/*  86 */       if (((a instanceof CASE)) || ((a instanceof CASEII)) || ((a instanceof MultiSlotSystem)) || ((a instanceof Supercharger)) || ((a instanceof Cockpit)) || ((a instanceof SimplePlaceable)) || ((a instanceof MechArmor)) || ((a instanceof MechTurret))) {
/*  87 */         if (DropItem.Location != 1) {
/*  88 */           return false;
/*     */         }
/*     */         
/*  91 */         JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/*  92 */         int dindex = dl.getIndex();
/*  93 */         if ((this.CurMech.GetLoadout().GetCrits(1)[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetCrits(1)[dindex].LocationLinked())) {
/*  94 */           return false;
/*     */         }
/*  96 */         if ((a instanceof CASE)) {
/*  97 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetCTCase() == a)) {
/*  98 */             return false;
/*     */           }
/* 100 */         } else if ((a instanceof CASEII)) {
/* 101 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetCTCaseII() == a)) {
/* 102 */             return false;
/*     */           }
/* 104 */         } else if ((a instanceof Supercharger)) {
/* 105 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetSupercharger() == a)) {
/* 106 */             return false;
/*     */           }
/* 108 */         } else if ((a instanceof MultiSlotSystem)) {
/* 109 */           if (this.CurMech.IsOmnimech()) {
/* 110 */             return false;
/*     */           }
/* 112 */         } else if ((a instanceof MechArmor)) {
/* 113 */           if (this.CurMech.IsOmnimech()) {
/* 114 */             return false;
/*     */           }
/* 116 */           if ((this.CurMech.GetLoadout().GetCTCrits()[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetCTCrits()[dindex].LocationLocked())) {
/* 117 */             return false;
/*     */           }
/* 119 */         } else if ((a instanceof MechTurret)) {
/* 120 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().HasHDTurret())) {
/* 121 */             return false;
/*     */           }
/* 123 */         } else if ((a instanceof Cockpit)) {
/* 124 */           if (this.CurMech.IsOmnimech()) {
/* 125 */             return false;
/*     */           }
/* 127 */         } else if ((a instanceof SimplePlaceable)) {
/* 128 */           if (this.CurMech.IsOmnimech()) {
/* 129 */             return false;
/*     */           }
/*     */         }
/*     */         else {
/* 133 */           return false;
/*     */         }
/*     */       }
/*     */       else {
/* 137 */         return false;
/*     */       }
/*     */     }
/* 140 */     if (DropItem.Empty) { return false;
/*     */     }
/* 142 */     info.setShowDropLocation(true);
/*     */     
/*     */ 
/* 145 */     return true;
/*     */   }
/*     */   
/*     */   public int getSourceActions(JComponent c)
/*     */   {
/* 150 */     return 2;
/*     */   }
/*     */   
/*     */   public boolean importData(TransferHandler.TransferSupport info)
/*     */   {
/* 155 */     if (!canImport(info)) {
/* 156 */       System.out.println("couldn't import");
/* 157 */       return false;
/*     */     }
/*     */     
/* 160 */     LocationDragDatagram DropItem = null;
/* 161 */     boolean rear = false;
/*     */     try
/*     */     {
/* 164 */       DropItem = (LocationDragDatagram)info.getTransferable().getTransferData(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram"));
/*     */     } catch (Exception e) {
/* 166 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 170 */     JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/* 171 */     int dindex = dl.getIndex();
/*     */     
/*     */ 
/*     */ 
/* 175 */     ArrayList v = new ArrayList();
/* 176 */     abPlaceable a; abPlaceable a; if (DropItem.Location == -1)
/*     */     {
/* 178 */       a = this.CurMech.GetLoadout().GetFromQueueByIndex(DropItem.SourceIndex);
/*     */     }
/*     */     else {
/* 181 */       a = this.CurMech.GetLoadout().GetCrits(DropItem.Location)[DropItem.SourceIndex];
/* 182 */       rear = a.IsMountedRear();
/* 183 */       if ((a.CanSplit()) && (a.Contiguous()))
/*     */       {
/* 185 */         v = this.CurMech.GetLoadout().FindSplitIndex(a);
/* 186 */         this.CurMech.GetLoadout().UnallocateAll(a, false);
/*     */       } else {
/* 188 */         this.CurMech.GetLoadout().UnallocateByIndex(DropItem.SourceIndex, this.CurMech.GetLoadout().GetCrits(DropItem.Location));
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 194 */       if ((a.CanSplit()) && (a.Contiguous())) {
/* 195 */         if (DropItem.Location == 1) {
/* 196 */           LocationIndex loc1 = null;
/* 197 */           LocationIndex loc2 = null;
/* 198 */           for (int i = 0; i < v.size(); i++) {
/* 199 */             if (((LocationIndex)v.get(i)).Location == 1) {
/* 200 */               loc1 = (LocationIndex)v.get(i);
/*     */             } else {
/* 202 */               loc2 = (LocationIndex)v.get(i);
/*     */             }
/*     */           }
/* 205 */           if (loc1 == null) { return false;
/*     */           }
/*     */           
/* 208 */           if (loc2 == null) {
/* 209 */             if (loc1.Number + dindex > this.CurMech.GetLoadout().GetCrits(loc1.Location).length) {
/* 210 */               return SplitAllocate(a, dindex);
/*     */             }
/* 212 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc1.Location), a, dindex, loc1.Number);
/*     */           }
/*     */           else {
/* 215 */             if (loc1.Number + dindex > this.CurMech.GetLoadout().GetCrits(loc1.Location).length) {
/* 216 */               return SplitAllocate(a, dindex);
/*     */             }
/* 218 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc1.Location), a, dindex, loc1.Number);
/* 219 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc2.Location), a, loc2.Index, loc2.Number);
/*     */           }
/*     */         }
/*     */         else {
/* 223 */           return SplitAllocate(a, dindex);
/*     */         }
/*     */       } else {
/* 226 */         this.CurMech.GetLoadout().AddToCT(a, dindex);
/*     */       }
/*     */     } catch (Exception e) {
/* 229 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 230 */       JOptionPane.showMessageDialog((JFrame)this.Parent, e.getMessage());
/* 231 */       this.Parent.RefreshInfoPane();
/* 232 */       return false;
/*     */     }
/* 234 */     if (a.NumPlaced() <= 0) {
/* 235 */       this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */     }
/* 237 */     a.MountRear(rear);
/* 238 */     if ((a instanceof VehicularGrenadeLauncher))
/*     */     {
/* 240 */       ((VehicularGrenadeLauncher)a).SetArcFore();
/*     */     }
/* 242 */     this.Parent.RefreshInfoPane();
/* 243 */     return true;
/*     */   }
/*     */   
/*     */   private boolean SplitAllocate(abPlaceable a, int dindex) throws Exception {
/* 247 */     int ToPlace = this.CurMech.GetLoadout().FreeFrom(this.CurMech.GetLoadout().GetCTCrits(), dindex);
/* 248 */     if (ToPlace < a.NumCrits()) {
/* 249 */       dlgSplitCrits dlgSplit = new dlgSplitCrits((JFrame)this.Parent, true, a, 1, dindex);
/* 250 */       Point p = ((JFrame)this.Parent).getLocationOnScreen();
/* 251 */       dlgSplit.setLocation(p.x + 100, p.y + 100);
/* 252 */       dlgSplit.setVisible(true);
/* 253 */       if (dlgSplit.GetResult()) {
/* 254 */         if (a.NumPlaced() <= 0) {
/* 255 */           this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */         }
/* 257 */         this.Parent.RefreshInfoPane();
/* 258 */         dlgSplit.dispose();
/* 259 */         return true;
/*     */       }
/* 261 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 262 */       this.Parent.RefreshInfoPane();
/* 263 */       dlgSplit.dispose();
/* 264 */       return false;
/*     */     }
/*     */     
/* 267 */     this.CurMech.GetLoadout().AddToCT(a, dindex);
/* 268 */     this.Parent.RefreshInfoPane();
/* 269 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\thCTTransferHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */