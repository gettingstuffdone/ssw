/*     */ package ssw.gui;
/*     */ 
/*     */ import components.AESSystem;
/*     */ import components.CASEII;
/*     */ import components.EmptyItem;
/*     */ import components.LocationIndex;
/*     */ import components.Mech;
/*     */ import components.MechArmor;
/*     */ import components.MultiSlotSystem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class thLATransferHandler
/*     */   extends TransferHandler
/*     */ {
/*     */   private ifMechForm Parent;
/*     */   private Mech CurMech;
/*     */   
/*     */   public thLATransferHandler(ifMechForm f, Mech m)
/*     */   {
/*  47 */     this.Parent = f;
/*  48 */     this.CurMech = m;
/*     */   }
/*     */   
/*     */ 
/*     */   public Transferable createTransferable(JComponent comp)
/*     */   {
/*  54 */     LocationDragDatagram d = new LocationDragDatagram();
/*  55 */     d.Location = 4;
/*  56 */     d.SourceIndex = ((JList)comp).getSelectedIndex();
/*  57 */     d.Locked = this.CurMech.GetLoadout().GetLACrits()[d.SourceIndex].LocationLocked();
/*  58 */     if ((this.CurMech.GetLoadout().GetLACrits()[d.SourceIndex] instanceof EmptyItem)) {
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
/*  85 */       if (((a instanceof CASEII)) || ((a instanceof MultiSlotSystem)) || ((a instanceof AESSystem)) || ((a instanceof MechArmor))) {
/*  86 */         if (DropItem.Location != 4) {
/*  87 */           return false;
/*     */         }
/*     */         
/*  90 */         JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/*  91 */         int dindex = dl.getIndex();
/*  92 */         if (((this.CurMech.GetLoadout().GetCrits(4)[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetCrits(4)[dindex].LocationLinked())) && (a != this.CurMech.GetLoadout().GetCrits(4)[dindex])) {
/*  93 */           return false;
/*     */         }
/*  95 */         if ((a instanceof CASEII)) {
/*  96 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetLACaseII() == a)) {
/*  97 */             return false;
/*     */           }
/*  99 */         } else if ((a instanceof MultiSlotSystem)) {
/* 100 */           if (this.CurMech.IsOmnimech()) {
/* 101 */             return false;
/*     */           }
/* 103 */         } else if ((a instanceof AESSystem)) {
/* 104 */           if (this.CurMech.IsOmnimech()) {
/* 105 */             return false;
/*     */           }
/* 107 */           if (a.NumCrits() + dindex > this.CurMech.GetLoadout().GetCrits(4).length) {
/* 108 */             return false;
/*     */           }
/* 110 */         } else if ((a instanceof MechArmor)) {
/* 111 */           if (this.CurMech.IsOmnimech()) {
/* 112 */             return false;
/*     */           }
/* 114 */           if ((this.CurMech.GetLoadout().GetLACrits()[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetLACrits()[dindex].LocationLocked())) {
/* 115 */             return false;
/*     */           }
/*     */         }
/*     */         else {
/* 119 */           return false;
/*     */         }
/*     */       }
/*     */       else {
/* 123 */         return false;
/*     */       }
/*     */     }
/* 126 */     if (DropItem.Empty) { return false;
/*     */     }
/* 128 */     info.setShowDropLocation(true);
/*     */     
/*     */ 
/* 131 */     return true;
/*     */   }
/*     */   
/*     */   public int getSourceActions(JComponent c)
/*     */   {
/* 136 */     return 2;
/*     */   }
/*     */   
/*     */   public boolean importData(TransferHandler.TransferSupport info)
/*     */   {
/* 141 */     if (!canImport(info)) {
/* 142 */       System.out.println("couldn't import");
/* 143 */       return false;
/*     */     }
/*     */     
/* 146 */     LocationDragDatagram DropItem = null;
/*     */     try
/*     */     {
/* 149 */       DropItem = (LocationDragDatagram)info.getTransferable().getTransferData(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram"));
/*     */     } catch (Exception e) {
/* 151 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 155 */     JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/* 156 */     int dindex = dl.getIndex();
/*     */     
/*     */ 
/*     */ 
/* 160 */     ArrayList v = new ArrayList();
/* 161 */     abPlaceable a; abPlaceable a; if (DropItem.Location == -1)
/*     */     {
/* 163 */       a = this.CurMech.GetLoadout().GetFromQueueByIndex(DropItem.SourceIndex);
/*     */     }
/*     */     else {
/* 166 */       a = this.CurMech.GetLoadout().GetCrits(DropItem.Location)[DropItem.SourceIndex];
/* 167 */       if ((a.CanSplit()) && (a.Contiguous()))
/*     */       {
/* 169 */         v = this.CurMech.GetLoadout().FindSplitIndex(a);
/* 170 */         this.CurMech.GetLoadout().UnallocateAll(a, false);
/*     */       } else {
/* 172 */         this.CurMech.GetLoadout().UnallocateByIndex(DropItem.SourceIndex, this.CurMech.GetLoadout().GetCrits(DropItem.Location));
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 178 */       if ((a.CanSplit()) && (a.Contiguous())) {
/* 179 */         if (DropItem.Location == 4) {
/* 180 */           LocationIndex loc1 = null;
/* 181 */           LocationIndex loc2 = null;
/* 182 */           for (int i = 0; i < v.size(); i++) {
/* 183 */             if (((LocationIndex)v.get(i)).Location == 4) {
/* 184 */               loc1 = (LocationIndex)v.get(i);
/*     */             } else {
/* 186 */               loc2 = (LocationIndex)v.get(i);
/*     */             }
/*     */           }
/* 189 */           if (loc1 == null) { return false;
/*     */           }
/*     */           
/* 192 */           if (loc2 == null) {
/* 193 */             if (loc1.Number + dindex > this.CurMech.GetLoadout().GetCrits(loc1.Location).length) {
/* 194 */               return SplitAllocate(a, dindex);
/*     */             }
/* 196 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc1.Location), a, dindex, loc1.Number);
/*     */           }
/*     */           else {
/* 199 */             if (loc1.Number + dindex > this.CurMech.GetLoadout().GetCrits(loc1.Location).length) {
/* 200 */               return SplitAllocate(a, dindex);
/*     */             }
/* 202 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc1.Location), a, dindex, loc1.Number);
/* 203 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc2.Location), a, loc2.Index, loc2.Number);
/*     */           }
/*     */         }
/*     */         else {
/* 207 */           return SplitAllocate(a, dindex);
/*     */         }
/*     */       } else {
/* 210 */         this.CurMech.GetLoadout().AddToLA(a, dindex);
/*     */       }
/*     */     } catch (Exception e) {
/* 213 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 214 */       JOptionPane.showMessageDialog((JFrame)this.Parent, e.getMessage());
/* 215 */       this.Parent.RefreshInfoPane();
/* 216 */       return false;
/*     */     }
/* 218 */     if (a.NumPlaced() <= 0) {
/* 219 */       this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */     }
/* 221 */     this.Parent.RefreshInfoPane();
/* 222 */     return true;
/*     */   }
/*     */   
/*     */   private boolean SplitAllocate(abPlaceable a, int dindex) throws Exception {
/* 226 */     int ToPlace = this.CurMech.GetLoadout().FreeFrom(this.CurMech.GetLoadout().GetLACrits(), dindex);
/* 227 */     if (ToPlace < a.NumCrits()) {
/* 228 */       dlgSplitCrits dlgSplit = new dlgSplitCrits((JFrame)this.Parent, true, a, 4, dindex);
/* 229 */       Point p = ((JFrame)this.Parent).getLocationOnScreen();
/* 230 */       dlgSplit.setLocation(p.x + 100, p.y + 100);
/* 231 */       dlgSplit.setVisible(true);
/* 232 */       if (dlgSplit.GetResult()) {
/* 233 */         if (a.NumPlaced() <= 0) {
/* 234 */           this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */         }
/* 236 */         this.Parent.RefreshInfoPane();
/* 237 */         dlgSplit.dispose();
/* 238 */         return true;
/*     */       }
/* 240 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 241 */       this.Parent.RefreshInfoPane();
/* 242 */       dlgSplit.dispose();
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     this.CurMech.GetLoadout().AddToLA(a, dindex);
/* 247 */     this.Parent.RefreshInfoPane();
/* 248 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\thLATransferHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */