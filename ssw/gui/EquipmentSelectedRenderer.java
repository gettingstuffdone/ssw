/*    */ package ssw.gui;
/*    */ 
/*    */ import components.Equipment;
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
/*    */ public class EquipmentSelectedRenderer
/*    */   extends DefaultListCellRenderer
/*    */ {
/*    */   private ifMechForm Parent;
/* 40 */   private abPlaceable a = null;
/*    */   
/*    */   public EquipmentSelectedRenderer(ifMechForm p) {
/* 43 */     this.Parent = p;
/*    */   }
/*    */   
/*    */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus)
/*    */   {
/* 48 */     JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
/* 49 */     String Text = "";
/* 50 */     if ((value instanceof abPlaceable)) {
/* 51 */       this.a = ((abPlaceable)value);
/* 52 */       int Loc = this.Parent.GetMech().GetLoadout().Find(this.a);
/* 53 */       if (this.Parent.GetMech().GetLoadout().GetTechBase() == 2) {
/* 54 */         if ((this.a instanceof Equipment)) {
/* 55 */           if (((Equipment)this.a).IsVariableSize()) {
/* 56 */             Text = this.a.CritName();
/*    */           } else {
/* 58 */             Text = this.a.LookupName();
/*    */           }
/*    */         } else {
/* 61 */           Text = this.a.LookupName();
/*    */         }
/*    */       } else {
/* 64 */         Text = this.a.CritName();
/*    */       }
/* 66 */       if (Loc < 11) {
/* 67 */         Text = "(" + FileCommon.EncodeLocation(Loc, this.Parent.GetMech().IsQuad()) + ") " + Text;
/*    */       }
/*    */     }
/*    */     
/* 71 */     label.setText(Text);
/*    */     
/* 73 */     return label;
/*    */   }
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\EquipmentSelectedRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */