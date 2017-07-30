/*    */ package ssw.filehandlers;
/*    */ 
/*    */ import components.Mech;
/*    */ import java.util.Hashtable;
/*    */ import visitors.VMechFullRecalc;
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
/*    */ 
/*    */ 
/*    */ public class MTFReader
/*    */ {
/* 36 */   private Hashtable MMLookup = new Hashtable();
/*    */   
/* 38 */   public MTFReader() { BuildHash(); }
/*    */   
/*    */   public Mech ReadMech()
/*    */   {
/* 42 */     Mech m = new Mech();
/* 43 */     VMechFullRecalc Recalc = new VMechFullRecalc();
/*    */     
/*    */ 
/*    */ 
/* 47 */     return m;
/*    */   }
/*    */   
/*    */   private void BuildHash() {}
/*    */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\filehandlers\MTFReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */