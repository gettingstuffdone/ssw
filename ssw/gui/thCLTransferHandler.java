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
/*     */ public class thCLTransferHandler
/*     */   extends TransferHandler
/*     */ {
/*     */   private ifMechForm Parent;
/*     */   private Mech CurMech;
/*     */   
/*     */   public thCLTransferHandler(ifMechForm f, Mech m)
/*     */   {
/*  47 */     this.Parent = f;
/*  48 */     this.CurMech = m;
/*     */   }
/*     */   
/*     */ 
/*     */   public Transferable createTransferable(JComponent comp)
/*     */   {
/*  54 */     LocationDragDatagram d = new LocationDragDatagram();
/*  55 */     d.Location = 11;
/*  56 */     d.SourceIndex = ((JList)comp).getSelectedIndex();
/*  57 */     d.Locked = this.CurMech.GetLoadout().GetCLCrits()[d.SourceIndex].LocationLocked();
/*  58 */     if ((this.CurMech.GetLoadout().GetCLCrits()[d.SourceIndex] instanceof EmptyItem)) {
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
/*  86 */         if (DropItem.Location != 11) {
/*  87 */           return false;
/*     */         }
/*     */         
/*  90 */         JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/*  91 */         int dindex = dl.getIndex();
/*  92 */         if ((this.CurMech.GetLoadout().GetCrits(11)[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetCrits(11)[dindex].LocationLinked())) {
/*  93 */           return false;
/*     */         }
/*  95 */         if ((a instanceof CASEII)) {
/*  96 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetCLCaseII() == a)) {
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
/* 107 */           if (a.NumCrits() + dindex > this.CurMech.GetLoadout().GetCrits(11).length) {
/* 108 */             return false;
/*     */           }
/* 110 */         } else if ((a instanceof MechArmor)) {
/* 111 */           if (this.CurMech.IsOmnimech()) {
/* 112 */             return false;
/*     */           }
/* 114 */           if ((this.CurMech.GetLoadout().GetCLCrits()[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetCLCrits()[dindex].LocationLocked())) {
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
/* 147 */     boolean rear = false;
/*     */     try
/*     */     {
/* 150 */       DropItem = (LocationDragDatagram)info.getTransferable().getTransferData(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram"));
/*     */     } catch (Exception e) {
/* 152 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 156 */     JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/* 157 */     int dindex = dl.getIndex();
/*     */     
/*     */ 
/*     */ 
/* 161 */     ArrayList v = new ArrayList();
/* 162 */     abPlaceable a; abPlaceable a; if (DropItem.Location == -1)
/*     */     {
/* 164 */       a = this.CurMech.GetLoadout().GetFromQueueByIndex(DropItem.SourceIndex);
/*     */     }
/*     */     else {
/* 167 */       a = this.CurMech.GetLoadout().GetCrits(DropItem.Location)[DropItem.SourceIndex];
/* 168 */       rear = a.IsMountedRear();
/* 169 */       if ((a.CanSplit()) && (a.Contiguous()))
/*     */       {
/* 171 */         v = this.CurMech.GetLoadout().FindSplitIndex(a);
/* 172 */         this.CurMech.GetLoadout().UnallocateAll(a, false);
/*     */       } else {
/* 174 */         this.CurMech.GetLoadout().UnallocateByIndex(DropItem.SourceIndex, this.CurMech.GetLoadout().GetCrits(DropItem.Location));
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 180 */       if ((a.CanSplit()) && (a.Contiguous())) {
/* 181 */         if (DropItem.Location == 11) {
/* 182 */           LocationIndex loc1 = null;
/* 183 */           LocationIndex loc2 = null;
/* 184 */           for (int i = 0; i < v.size(); i++) {
/* 185 */             if (((LocationIndex)v.get(i)).Location == 11) {
/* 186 */               loc1 = (LocationIndex)v.get(i);
/*     */             } else {
/* 188 */               loc2 = (LocationIndex)v.get(i);
/*     */             }
/*     */           }
/* 191 */           if (loc1 == null) { return false;
/*     */           }
/*     */           
/* 194 */           if (loc2 == null) {
/* 195 */             if (loc1.Number + dindex > this.CurMech.GetLoadout().GetCrits(loc1.Location).length) {
/* 196 */               return SplitAllocate(a, dindex);
/*     */             }
/* 198 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc1.Location), a, dindex, loc1.Number);
/*     */           }
/*     */           else {
/* 201 */             if (loc1.Number + dindex > this.CurMech.GetLoadout().GetCrits(loc1.Location).length) {
/* 202 */               return SplitAllocate(a, dindex);
/*     */             }
/* 204 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc1.Location), a, dindex, loc1.Number);
/* 205 */             this.CurMech.GetLoadout().AddTo(this.CurMech.GetLoadout().GetCrits(loc2.Location), a, loc2.Index, loc2.Number);
/*     */           }
/*     */         }
/*     */         else {
/* 209 */           return SplitAllocate(a, dindex);
/*     */         }
/*     */       } else {
/* 212 */         this.CurMech.GetLoadout().AddToCL(a, dindex);
/*     */       }
/*     */     } catch (Exception e) {
/* 215 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 216 */       JOptionPane.showMessageDialog((JFrame)this.Parent, e.getMessage());
/* 217 */       this.Parent.RefreshInfoPane();
/* 218 */       return false;
/*     */     }
/* 220 */     if (a.NumPlaced() <= 0) {
/* 221 */       this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */     }
/* 223 */     a.MountRear(rear);
/* 224 */     this.Parent.RefreshInfoPane();
/* 225 */     return true;
/*     */   }
/*     */   
/*     */   private boolean SplitAllocate(abPlaceable a, int dindex) throws Exception {
/* 229 */     int ToPlace = this.CurMech.GetLoadout().FreeFrom(this.CurMech.GetLoadout().GetCLCrits(), dindex);
/* 230 */     if (ToPlace < a.NumCrits()) {
/* 231 */       dlgSplitCrits dlgSplit = new dlgSplitCrits((JFrame)this.Parent, true, a, 11, dindex);
/* 232 */       Point p = ((JFrame)this.Parent).getLocationOnScreen();
/* 233 */       dlgSplit.setLocation(p.x + 100, p.y + 100);
/* 234 */       dlgSplit.setVisible(true);
/* 235 */       if (dlgSplit.GetResult()) {
/* 236 */         if (a.NumPlaced() <= 0) {
/* 237 */           this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */         }
/* 239 */         this.Parent.RefreshInfoPane();
/* 240 */         dlgSplit.dispose();
/* 241 */         return true;
/*     */       }
/* 243 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 244 */       this.Parent.RefreshInfoPane();
/* 245 */       dlgSplit.dispose();
/* 246 */       return false;
/*     */     }
/*     */     
/* 249 */     this.CurMech.GetLoadout().AddToCL(a, dindex);
/* 250 */     this.Parent.RefreshInfoPane();
/* 251 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\thCLTransferHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */