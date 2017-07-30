/*     */ package ssw.print;
/*     */ 
/*     */ import Print.BattleforceCardPrinter;
/*     */ import Print.BattleforcePrinter;
/*     */ import Print.PaperSize;
/*     */ import Print.PrintMech;
/*     */ import battleforce.BattleForce;
/*     */ import common.CommonTools;
/*     */ import components.Mech;
/*     */ import filehandlers.ImageTracker;
/*     */ import filehandlers.Media;
/*     */ import java.awt.print.Book;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Paper;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.JFrame;
/*     */ import ssw.gui.dlgPrintBattleforce;
/*     */ import ssw.gui.dlgPrintSavedMechOptions;
/*     */ import ssw.gui.ifMechForm;
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
/*     */ public class Printer
/*     */ {
/*     */   private ifMechForm Parent;
/*  46 */   private Vector<PrintMech> Mechs = new Vector();
/*  47 */   private Vector battleforces = new Vector();
/*     */   private BattleForce battleforce;
/*     */   private BattleforcePrinter printForce;
/*  50 */   private String jobName = "SSW Batch Print"; private String logoPath = ""; private String MechImagePath = ""; private String ChartImageOption = "";
/*     */   
/*     */ 
/*     */ 
/*  54 */   private Boolean Charts = Boolean.valueOf(true);
/*  55 */   private Boolean Canon = Boolean.valueOf(true);
/*  56 */   private Boolean useDialog = Boolean.valueOf(true);
/*  57 */   private Boolean TRO = Boolean.valueOf(false);
/*     */   
/*  59 */   private ImageTracker images = new ImageTracker();
/*     */   
/*  61 */   private Book pages = new Book();
/*  62 */   private Paper paper = new Paper();
/*  63 */   private PageFormat page = new PageFormat();
/*  64 */   private PrinterJob job = PrinterJob.getPrinterJob();
/*     */   
/*     */ 
/*  67 */   public static final PaperSize Letter = new PaperSize(8.5D, 11.0D);
/*  68 */   public static final PaperSize A4 = new PaperSize(595, 842, 18, 18, 559, 806);
/*  69 */   public static final PaperSize Legal = new PaperSize(8.5D, 14.0D);
/*     */   
/*     */   public Printer() {
/*  72 */     this(null);
/*     */   }
/*     */   
/*     */   public Printer(ifMechForm p) {
/*  76 */     this.Parent = p;
/*  77 */     setPaperSize(Letter);
/*  78 */     this.images.preLoadMechImages();
/*     */   }
/*     */   
/*     */   public String getJobName() {
/*  82 */     return this.jobName;
/*     */   }
/*     */   
/*     */   public void setJobName(String jobName) {
/*  86 */     this.jobName = jobName.trim();
/*     */   }
/*     */   
/*     */   public PrinterJob getJob() {
/*  90 */     return this.job;
/*     */   }
/*     */   
/*     */   public void setPaperSize(PaperSize s) {
/*  94 */     this.paper.setSize(s.PaperWidth, s.PaperHeight);
/*  95 */     this.paper.setImageableArea(s.ImageableX, s.ImageableY, s.ImageableWidth, s.ImageableHeight);
/*  96 */     if (s == A4)
/*     */     {
/*  98 */       for (PrintMech pm : this.Mechs)
/*     */       {
/* 100 */         pm.setA4();
/*     */       }
/* 102 */     } else if (s == Letter)
/*     */     {
/* 104 */       for (PrintMech pm : this.Mechs)
/*     */       {
/* 106 */         pm.setLetter();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Boolean getCharts() {
/* 112 */     return this.Charts;
/*     */   }
/*     */   
/*     */   public Vector GetMechs() {
/* 116 */     return this.Mechs;
/*     */   }
/*     */   
/*     */   public void setCharts(Boolean Charts) {
/* 120 */     this.Charts = Charts;
/* 121 */     for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 122 */       PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 123 */       pm.setCharts(Charts);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setCanon(boolean Canon) {
/* 128 */     this.Canon = Boolean.valueOf(Canon);
/* 129 */     for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 130 */       PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 131 */       pm.setCanon(Canon);
/*     */     }
/*     */   }
/*     */   
/*     */   public Boolean getCanon() {
/* 136 */     return this.Canon;
/*     */   }
/*     */   
/*     */   public Boolean getTRO() {
/* 140 */     return this.TRO;
/*     */   }
/*     */   
/*     */   public void setTRO(Boolean TRO) {
/* 144 */     this.TRO = TRO;
/* 145 */     for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 146 */       PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 147 */       pm.setTRO(TRO.booleanValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void setHexConversion(int Rate) {
/* 152 */     for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 153 */       PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 154 */       pm.SetMiniConversion(Rate);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPrintWarrior(boolean value) {
/* 159 */     for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 160 */       PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 161 */       pm.setPrintPilot(Boolean.valueOf(value));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setWarriorData(int Gunnery, int Piloting, String Name, String Unit) {
/* 166 */     for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 167 */       PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 168 */       pm.SetPilotData(Name, Gunnery, Piloting);
/* 169 */       pm.setGroupName(Unit);
/*     */     }
/*     */   }
/*     */   
/*     */   public void AddMech(Mech m, String Mechwarrior, int Gunnery, int Piloting, boolean Charts, boolean PilotInfo, boolean AdjBV) {
/* 174 */     double BV = m.GetCurrentBV();
/* 175 */     if (AdjBV) { BV = CommonTools.GetAdjustedBV(m.GetCurrentBV(), Gunnery, Piloting);
/*     */     }
/* 177 */     PrintMech pm = new PrintMech(m, this.images);
/* 178 */     pm.SetPilotData(Mechwarrior, Gunnery, Piloting);
/* 179 */     pm.SetOptions(Charts, PilotInfo, BV);
/* 180 */     pm.setCanon(this.Canon.booleanValue());
/* 181 */     this.Mechs.add(pm);
/*     */   }
/*     */   
/*     */   public void AddMech(Mech m, String Mechwarrior, int Gunnery, int Piloting) {
/* 185 */     AddMech(m, Mechwarrior, Gunnery, Piloting, this.Charts.booleanValue(), true, true);
/*     */   }
/*     */   
/*     */   public void AddMech(Mech m) {
/* 189 */     AddMech(m, "", 4, 5, this.Charts.booleanValue(), true, true);
/*     */   }
/*     */   
/*     */   public void AddForce(BattleForce f) {
/* 193 */     this.images.preLoadBattleForceImages();
/*     */     
/* 195 */     this.battleforces.add(new BattleforceCardPrinter(f, this.images));
/*     */   }
/*     */   
/*     */   public void Clear() {
/* 199 */     this.Mechs.removeAllElements();
/* 200 */     this.battleforces.removeAllElements();
/*     */   }
/*     */   
/*     */   public void Print(Mech m) {
/* 204 */     AddMech(m);
/* 205 */     Print();
/*     */   }
/*     */   
/*     */   public void PrintSingles() {
/* 209 */     if (this.Mechs.size() == 0) { return;
/*     */     }
/* 211 */     this.page.setPaper(this.paper);
/*     */     
/* 213 */     if (this.useDialog.booleanValue()) {
/* 214 */       if (this.Mechs.size() == 1) {
/* 215 */         if (PrintDialog((PrintMech)this.Mechs.get(0)).booleanValue()) {}
/*     */       }
/* 217 */       else if (!BatchDialog().booleanValue()) { return;
/*     */       }
/*     */     }
/*     */     
/* 221 */     for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 222 */       PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 223 */       this.job.setJobName(pm.CurMech.GetFullName().trim());
/* 224 */       this.job.setPrintable(pm);
/*     */       try {
/* 226 */         this.job.print();
/*     */       } catch (PrinterException ex) {
/* 228 */         Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void Print(boolean useDialog) {
/* 234 */     this.useDialog = Boolean.valueOf(useDialog);
/* 235 */     Print();
/*     */   }
/*     */   
/*     */   public void Print()
/*     */   {
/* 240 */     if (this.Mechs.size() == 0) { return;
/*     */     }
/* 242 */     this.job.setJobName(this.jobName.trim());
/*     */     
/*     */ 
/* 245 */     if (this.useDialog.booleanValue()) {
/* 246 */       if (this.Mechs.size() == 1) {
/* 247 */         if (PrintDialog((PrintMech)this.Mechs.get(0)).booleanValue()) {}
/*     */       }
/* 249 */       else if (!BatchDialog().booleanValue()) { return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 254 */     GeneratePrints();
/*     */     
/* 256 */     this.job.setPageable(this.pages);
/* 257 */     boolean DoPrint = this.job.printDialog();
/* 258 */     if (DoPrint) {
/*     */       try {
/* 260 */         this.job.print();
/*     */       } catch (PrinterException e) {
/* 262 */         System.err.println(e.getMessage());
/* 263 */         System.out.println(e.getStackTrace());
/*     */       }
/*     */       
/* 266 */       this.Mechs.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public Book Preview() {
/* 271 */     GeneratePrints();
/* 272 */     return this.pages;
/*     */   }
/*     */   
/*     */   private void GeneratePrints()
/*     */   {
/* 277 */     this.pages = new Book();
/*     */     
/*     */ 
/* 280 */     this.page.setPaper(this.paper);
/*     */     
/* 282 */     for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 283 */       PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 284 */       this.pages.append(pm, this.page);
/* 285 */       if (this.Mechs.size() == 1) this.job.setJobName(pm.CurMech.GetFullName().trim());
/*     */     }
/*     */   }
/*     */   
/*     */   private Boolean PrintDialog(PrintMech pMech)
/*     */   {
/* 291 */     dlgPrintSavedMechOptions POptions = new dlgPrintSavedMechOptions((JFrame)this.Parent, true, pMech);
/* 292 */     POptions.setTitle("Printing " + pMech.CurMech.GetFullName());
/* 293 */     POptions.setLocationRelativeTo((JFrame)this.Parent);
/*     */     
/* 295 */     POptions.setVisible(true);
/*     */     
/* 297 */     if (!POptions.Result()) {
/* 298 */       return Boolean.valueOf(false);
/*     */     }
/*     */     
/* 301 */     pMech.setPrintPilot(Boolean.valueOf(POptions.PrintPilot()));
/* 302 */     pMech.setCharts(Boolean.valueOf(POptions.PrintCharts()));
/* 303 */     pMech.setGunnery(POptions.GetGunnery());
/* 304 */     pMech.setPiloting(POptions.GetPiloting());
/* 305 */     pMech.setMechwarrior(POptions.GetWarriorName());
/* 306 */     pMech.setMechImage(POptions.getImage());
/* 307 */     pMech.setLogoImage(POptions.getLogo());
/* 308 */     pMech.setCanon(POptions.getCanon());
/* 309 */     if (POptions.UseMiniConversion()) pMech.SetMiniConversion(POptions.GetMiniConversionRate());
/* 310 */     if (POptions.UseA4Paper()) { pMech.setA4();
/*     */     }
/* 312 */     POptions.dispose();
/* 313 */     return Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   private Boolean BatchDialog() {
/* 317 */     dlgPrintSavedMechOptions POptions = new dlgPrintSavedMechOptions((JFrame)this.Parent, true);
/* 318 */     POptions.setTitle("Printing Batched Units");
/* 319 */     POptions.setLocationRelativeTo((JFrame)this.Parent);
/*     */     
/* 321 */     if (!this.logoPath.isEmpty()) {
/* 322 */       POptions.setLogo(new File(this.logoPath));
/*     */     }
/*     */     
/* 325 */     POptions.setVisible(true);
/*     */     
/* 327 */     if (!POptions.Result()) {
/* 328 */       return Boolean.valueOf(false);
/*     */     }
/*     */     
/* 331 */     for (int m = 0; m <= this.Mechs.size() - 1; m++) {
/* 332 */       PrintMech pMech = (PrintMech)this.Mechs.get(m);
/* 333 */       pMech.setPrintPilot(Boolean.valueOf(POptions.PrintPilot()));
/* 334 */       pMech.setCharts(Boolean.valueOf(POptions.PrintCharts()));
/* 335 */       pMech.setMechImage(POptions.getImage());
/* 336 */       pMech.setLogoImage(POptions.getLogo());
/* 337 */       pMech.setCanon(POptions.getCanon());
/* 338 */       if (POptions.UseMiniConversion()) { pMech.SetMiniConversion(POptions.GetMiniConversionRate());
/*     */       }
/*     */     }
/* 341 */     POptions.dispose();
/* 342 */     return Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   public String getLogoPath() {
/* 346 */     return this.logoPath;
/*     */   }
/*     */   
/*     */   public void setLogoPath(String logoPath) {
/* 350 */     this.logoPath = logoPath;
/* 351 */     if (!logoPath.isEmpty()) {
/* 352 */       Media media = new Media();
/* 353 */       for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 354 */         PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 355 */         pm.setLogoImage(media.GetImage(logoPath));
/*     */       }
/*     */     } else {
/* 358 */       for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 359 */         PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 360 */         pm.setLogoImage(null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setMechImagePath(String MechImagePath) {
/* 366 */     this.MechImagePath = MechImagePath;
/* 367 */     if (!MechImagePath.isEmpty()) {
/* 368 */       Media media = new Media();
/* 369 */       for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 370 */         PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 371 */         pm.setMechImage(media.GetImage(MechImagePath));
/*     */       }
/*     */     } else {
/* 374 */       for (int index = 0; index <= this.Mechs.size() - 1; index++) {
/* 375 */         PrintMech pm = (PrintMech)this.Mechs.get(index);
/* 376 */         pm.setMechImage(null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 381 */   public void setChartImageOption(String Option) { this.ChartImageOption = Option;
/* 382 */     for (int i = 0; i < this.Mechs.size(); i++) {
/* 383 */       PrintMech pm = (PrintMech)this.Mechs.get(i);
/* 384 */       pm.setChartImageOption(Option);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBattleForceSheet(String Type) {
/* 389 */     for (int i = 0; i < this.battleforces.size(); i++) {
/* 390 */       ((BattleforcePrinter)this.battleforces.get(i)).setType(Type);
/*     */     }
/*     */   }
/*     */   
/*     */   public Book PreviewBattleforce() {
/* 395 */     if (this.battleforces.size() == 0) return new Book();
/* 396 */     this.pages = new Book();
/* 397 */     this.page.setPaper(this.paper);
/* 398 */     for (int i = 0; i < this.battleforces.size(); i++) {
/* 399 */       this.pages.append((BattleforceCardPrinter)this.battleforces.get(i), this.page);
/*     */     }
/* 401 */     return this.pages;
/*     */   }
/*     */   
/*     */   public void PrintBattleforce(boolean useDialog) {
/* 405 */     this.useDialog = Boolean.valueOf(useDialog);
/* 406 */     PrintBattleforce();
/*     */   }
/*     */   
/*     */   public void PrintBattleforce() {
/* 410 */     if (this.battleforces.size() == 0) return;
/* 411 */     this.pages = new Book();
/* 412 */     this.page.setPaper(this.paper);
/*     */     
/* 414 */     if (this.jobName.isEmpty()) this.jobName = "BattleForce";
/* 415 */     if (this.battleforces.size() == 1) {
/* 416 */       BattleForce bf = ((BattleforcePrinter)this.battleforces.get(0)).getBattleforce();
/* 417 */       if (!bf.ForceName.isEmpty()) { this.jobName = bf.ForceName.trim();
/*     */       }
/*     */     }
/* 420 */     this.job.setJobName(this.jobName);
/*     */     
/* 422 */     if (this.useDialog.booleanValue()) {
/* 423 */       for (int i = 0; i < this.battleforces.size(); i++) {
/* 424 */         BattleforcePrinter bf = (BattleforcePrinter)this.battleforces.get(i);
/* 425 */         dlgPrintBattleforce pForce = new dlgPrintBattleforce((JFrame)this.Parent, true, bf.getBattleforce());
/* 426 */         pForce.setLocationRelativeTo((JFrame)this.Parent);
/* 427 */         pForce.setVisible(true);
/* 428 */         if (!pForce.Result) {
/* 429 */           return;
/*     */         }
/*     */         
/* 432 */         bf.setType(pForce.Sheet);
/*     */       }
/*     */     }
/*     */     
/* 436 */     for (int i = 0; i < this.battleforces.size(); i++) {
/* 437 */       this.pages.append((BattleforcePrinter)this.battleforces.get(i), this.page);
/*     */     }
/*     */     
/* 440 */     this.job.setPageable(this.pages);
/* 441 */     boolean DoPrint = this.job.printDialog();
/* 442 */     if (DoPrint) {
/*     */       try {
/* 444 */         this.job.print();
/*     */       } catch (PrinterException e) {
/* 446 */         System.err.println(e.getMessage());
/* 447 */         System.out.println(e.getStackTrace());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\print\Printer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */