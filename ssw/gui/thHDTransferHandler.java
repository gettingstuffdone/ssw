/*     */ package ssw.gui;
/*     */ 
/*     */ import components.CASEII;
/*     */ import components.EmptyItem;
/*     */ import components.Mech;
/*     */ import components.MechArmor;
/*     */ import components.abPlaceable;
/*     */ import components.ifMechLoadout;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.PrintStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class thHDTransferHandler
/*     */   extends TransferHandler
/*     */ {
/*     */   private ifMechForm Parent;
/*     */   private Mech CurMech;
/*     */   
/*     */   public thHDTransferHandler(ifMechForm f, Mech m)
/*     */   {
/*  45 */     this.Parent = f;
/*  46 */     this.CurMech = m;
/*     */   }
/*     */   
/*     */ 
/*     */   public Transferable createTransferable(JComponent comp)
/*     */   {
/*  52 */     LocationDragDatagram d = new LocationDragDatagram();
/*  53 */     d.Location = 0;
/*  54 */     d.SourceIndex = ((JList)comp).getSelectedIndex();
/*  55 */     d.Locked = this.CurMech.GetLoadout().GetHDCrits()[d.SourceIndex].LocationLocked();
/*  56 */     if ((this.CurMech.GetLoadout().GetHDCrits()[d.SourceIndex] instanceof EmptyItem)) {
/*  57 */       d.Empty = true;
/*     */     }
/*  59 */     return d;
/*     */   }
/*     */   
/*     */   public boolean canImport(TransferHandler.TransferSupport info)
/*     */   {
/*  64 */     if (!info.isDrop())
/*     */     {
/*  66 */       return false;
/*     */     }
/*     */     
/*  69 */     if (!info.isDataFlavorSupported(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram")))
/*     */     {
/*  71 */       return false;
/*     */     }
/*     */     
/*  74 */     LocationDragDatagram DropItem = null;
/*     */     try {
/*  76 */       DropItem = (LocationDragDatagram)info.getTransferable().getTransferData(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram"));
/*     */     } catch (Exception e) {
/*  78 */       return false;
/*     */     }
/*     */     
/*  81 */     if (DropItem.Locked) {
/*  82 */       abPlaceable a = this.CurMech.GetLoadout().GetCrits(DropItem.Location)[DropItem.SourceIndex];
/*  83 */       if (((a instanceof CASEII)) || ((a instanceof MechArmor))) {
/*  84 */         if (DropItem.Location != 0) {
/*  85 */           return false;
/*     */         }
/*     */         
/*  88 */         JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/*  89 */         int dindex = dl.getIndex();
/*  90 */         if ((a instanceof CASEII)) {
/*  91 */           if ((this.CurMech.GetLoadout().GetCrits(0)[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetCrits(0)[dindex].LocationLinked())) {
/*  92 */             return false;
/*     */           }
/*  94 */           if ((this.CurMech.IsOmnimech()) && (this.CurMech.GetBaseLoadout().GetHDCaseII() == a)) {
/*  95 */             return false;
/*     */           }
/*  97 */         } else if ((a instanceof MechArmor)) {
/*  98 */           if (this.CurMech.IsOmnimech()) {
/*  99 */             return false;
/*     */           }
/* 101 */           if ((this.CurMech.GetLoadout().GetHDCrits()[dindex].LocationLocked()) || (this.CurMech.GetLoadout().GetHDCrits()[dindex].LocationLocked())) {
/* 102 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 107 */         return false;
/*     */       }
/*     */     }
/* 110 */     if (DropItem.Empty) { return false;
/*     */     }
/* 112 */     info.setShowDropLocation(true);
/*     */     
/*     */ 
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public int getSourceActions(JComponent c)
/*     */   {
/* 120 */     return 2;
/*     */   }
/*     */   
/*     */   public boolean importData(TransferHandler.TransferSupport info)
/*     */   {
/* 125 */     if (!canImport(info)) {
/* 126 */       System.out.println("couldn't import");
/* 127 */       return false;
/*     */     }
/*     */     
/* 130 */     LocationDragDatagram DropItem = null;
/* 131 */     boolean rear = false;
/*     */     try
/*     */     {
/* 134 */       DropItem = (LocationDragDatagram)info.getTransferable().getTransferData(new DataFlavor(LocationDragDatagram.class, "Location Drag Datagram"));
/*     */     } catch (Exception e) {
/* 136 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 140 */     JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
/* 141 */     int dindex = dl.getIndex();
/*     */     
/*     */     abPlaceable a;
/*     */     abPlaceable a;
/* 145 */     if (DropItem.Location == -1)
/*     */     {
/* 147 */       a = this.CurMech.GetLoadout().GetFromQueueByIndex(DropItem.SourceIndex);
/*     */     }
/*     */     else {
/* 150 */       a = this.CurMech.GetLoadout().GetCrits(DropItem.Location)[DropItem.SourceIndex];
/* 151 */       rear = a.IsMountedRear();
/* 152 */       if ((a.CanSplit()) && (a.Contiguous())) {
/* 153 */         this.CurMech.GetLoadout().UnallocateAll(a, false);
/*     */       } else {
/* 155 */         this.CurMech.GetLoadout().UnallocateByIndex(DropItem.SourceIndex, this.CurMech.GetLoadout().GetCrits(DropItem.Location));
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 161 */       this.CurMech.GetLoadout().AddToHD(a, dindex);
/*     */     } catch (Exception e) {
/* 163 */       this.CurMech.GetLoadout().AddToQueue(a);
/* 164 */       JOptionPane.showMessageDialog((JFrame)this.Parent, e.getMessage());
/* 165 */       this.Parent.RefreshInfoPane();
/* 166 */       return false;
/*     */     }
/* 168 */     if (a.NumPlaced() <= 0) {
/* 169 */       this.CurMech.GetLoadout().RemoveFromQueue(a);
/*     */     }
/* 171 */     a.MountRear(rear);
/* 172 */     this.Parent.RefreshInfoPane();
/* 173 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\thHDTransferHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */