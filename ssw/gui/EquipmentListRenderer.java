/*    */ package ssw.gui;
/*    */ 
/*    */ import components.Equipment;
/*    */ import components.EquipmentCollection;
/*    */ import components.Mech;
/*    */ import components.abPlaceable;
/*    */ import components.ifMechLoadout;
/*    */ import filehandlers.FileCommon;
/*    */ import java.awt.Component;
/*    */ import javax.swing.DefaultListCellRenderer;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EquipmentListRenderer
/*    */   extends DefaultListCellRenderer
/*    */ {
/*    */   private ifMechForm Parent;
/* 43 */   private abPlaceable a = null;
/*    */   
/*    */   public EquipmentListRenderer(ifMechForm p) {
/* 46 */     this.Parent = p;
/*    */   }
/*    */   
/*    */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus)
/*    */   {
/* 51 */     JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
/* 52 */     String Text = "";
/* 53 */     if ((value instanceof abPlaceable)) {
/* 54 */       this.a = ((abPlaceable)value);
/* 55 */       int Loc = this.Parent.GetMech().GetLoadout().Find(this.a);
/* 56 */       if (this.a.Contiguous()) {
/* 57 */         if (this.Parent.GetMech().GetLoadout().GetTechBase() == 2) {
/* 58 */           if ((this.a instanceof Equipment)) {
/* 59 */             if (((Equipment)this.a).IsVariableSize()) {
/* 60 */               Text = this.a.CritName();
/*    */             } else {
/* 62 */               Text = this.a.LookupName();
/*    */             }
/*    */           } else {
/* 65 */             Text = this.a.LookupName();
/*    */           }
/*    */         } else {
/* 68 */           Text = this.a.CritName();
/*    */         }
/* 70 */         if (Loc < 11) {
/* 71 */           Text = "(" + FileCommon.EncodeLocation(Loc, this.Parent.GetMech().IsQuad()) + ") " + Text;
/*    */         }
/*    */       } else {
/* 74 */         Text = this.a.toString();
/*    */       }
/* 76 */     } else if ((value instanceof EquipmentCollection)) {
/* 77 */       Text = value.toString();
/*    */     }
/*    */     
/* 80 */     label.setText(Text);
/*    */     
/* 82 */     return label;
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\EquipmentListRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */