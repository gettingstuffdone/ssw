/*      */ package ssw.filehandlers;
/*      */ 
/*      */ import battleforce.BattleForceStats;
/*      */ import battleforce.BattleForceTools;
/*      */ import common.CommonTools;
/*      */ import components.Ammunition;
/*      */ import components.AvailableCode;
/*      */ import components.Cockpit;
/*      */ import components.Engine;
/*      */ import components.Equipment;
/*      */ import components.Gyro;
/*      */ import components.HeatSinkFactory;
/*      */ import components.InternalStructure;
/*      */ import components.JumpJetFactory;
/*      */ import components.MGArray;
/*      */ import components.Mech;
/*      */ import components.MechArmor;
/*      */ import components.MechModifier;
/*      */ import components.MechanicalJumpBooster;
/*      */ import components.MultiSlotSystem;
/*      */ import components.PhysicalEnhancement;
/*      */ import components.PowerAmplifier;
/*      */ import components.RangedWeapon;
/*      */ import components.SimplePlaceable;
/*      */ import components.abPlaceable;
/*      */ import components.ifMechLoadout;
/*      */ import components.ifMissileGuidance;
/*      */ import components.ifWeapon;
/*      */ import filehandlers.FileCommon;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import states.ifArmor;
/*      */ import states.ifState;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HTMLWriter
/*      */ {
/*      */   private Mech CurMech;
/*   46 */   private Hashtable lookup = new Hashtable(90);
/*   47 */   private String NL = "<br />"; private String tformat = "";
/*      */   
/*      */   public HTMLWriter(Mech m)
/*      */   {
/*   51 */     this.CurMech = m;
/*      */     
/*      */ 
/*   54 */     if (this.CurMech.IsOmnimech()) {
/*   55 */       this.CurMech.SetCurLoadout("Base Loadout");
/*      */     }
/*   57 */     if (this.CurMech.UsingFractionalAccounting()) {
/*   58 */       this.tformat = "$7.3f";
/*      */     } else {
/*   60 */       this.tformat = "$6.2f";
/*      */     }
/*   62 */     BuildHash();
/*      */   }
/*      */   
/*      */   public void WriteHTML(String template, String destfile) throws IOException {
/*   66 */     BufferedWriter FW = new BufferedWriter(new FileWriter(destfile));
/*   67 */     BufferedReader FR = new BufferedReader(new FileReader(template));
/*   68 */     boolean EOF = false;
/*   69 */     String read = "";
/*   70 */     String write = "";
/*   71 */     ArrayList equip = new ArrayList();
/*   72 */     ArrayList omni = new ArrayList();
/*   73 */     ArrayList armor = new ArrayList();
/*      */     
/*      */ 
/*      */ 
/*   77 */     while (!EOF)
/*      */     {
/*   79 */       read = FR.readLine();
/*   80 */       if (read == null)
/*      */       {
/*   82 */         EOF = true;
/*      */ 
/*      */       }
/*   85 */       else if (read.contains("<+-SSW_START_EQUIPMENT_FLUFF_BLOCK-+>"))
/*      */       {
/*   87 */         equip.clear();
/*   88 */         boolean end = false;
/*   89 */         while (!end) {
/*   90 */           read = FR.readLine();
/*      */           
/*   92 */           if (read == null) { throw new IOException("Unexpected EOF: No End Equipment Fluff tag.");
/*      */           }
/*   94 */           if (read.contains("<+-SSW_END_EQUIPMENT_FLUFF_BLOCK-+>")) {
/*   95 */             end = true;
/*      */           } else {
/*   97 */             equip.add(read);
/*      */           }
/*      */         }
/*  100 */         abPlaceable[] a = GetEquips(true);
/*  101 */         FW.write(BuildEquipLines(equip, a, true));
/*  102 */         FW.newLine();
/*  103 */       } else if (read.contains("<+-SSW_START_EQUIPMENT_STAT_BLOCK-+>"))
/*      */       {
/*  105 */         equip.clear();
/*  106 */         boolean end = false;
/*  107 */         while (!end) {
/*  108 */           read = FR.readLine();
/*      */           
/*  110 */           if (read == null) { throw new IOException("Unexpected EOF: No End Equipment tag.");
/*      */           }
/*  112 */           if (read.contains("<+-SSW_END_EQUIPMENT_STAT_BLOCK-+>")) {
/*  113 */             end = true;
/*      */           } else {
/*  115 */             equip.add(read);
/*      */           }
/*      */         }
/*  118 */         abPlaceable[] a = GetEquips(false);
/*  119 */         FW.write(BuildEquipLines(equip, a, false));
/*  120 */         FW.newLine();
/*  121 */       } else if (read.contains("<+-SSW_START_OMNIMECH_STAT_BLOCK-+>"))
/*      */       {
/*  123 */         equip.clear();
/*  124 */         omni.clear();
/*  125 */         boolean end = false;
/*  126 */         while (!end) {
/*  127 */           read = FR.readLine();
/*      */           
/*  129 */           if (read == null) { throw new IOException("Unexpected EOF: No End Omnimech tag.");
/*      */           }
/*  131 */           if (read.contains("<+-SSW_END_OMNIMECH_STAT_BLOCK-+>")) {
/*  132 */             end = true;
/*      */           } else {
/*  134 */             omni.add(read);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  139 */         if (this.CurMech.IsOmnimech()) {
/*      */           try
/*      */           {
/*  142 */             FW.write(BuildOmniLines(omni));
/*  143 */             FW.newLine();
/*      */           } catch (IOException e) {
/*  145 */             throw e;
/*      */           }
/*      */         }
/*  148 */       } else if (read.contains("<+-SSW_START_NORMAL_ARMOR_BLOCK-+>"))
/*      */       {
/*  150 */         armor.clear();
/*  151 */         boolean end = false;
/*  152 */         while (!end) {
/*  153 */           read = FR.readLine();
/*      */           
/*  155 */           if (read == null) { throw new IOException("Unexpected EOF: No End Normal Armor tag.");
/*      */           }
/*  157 */           if (read.contains("<+-SSW_END_NORMAL_ARMOR_BLOCK-+>")) {
/*  158 */             end = true;
/*      */           } else {
/*  160 */             armor.add(read);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  165 */         if (!this.CurMech.GetArmor().IsPatchwork()) {
/*      */           try
/*      */           {
/*  168 */             for (int i = 0; i < armor.size(); i++) {
/*  169 */               FW.write(ProcessLine((String)armor.get(i)));
/*  170 */               FW.newLine();
/*      */             }
/*      */           } catch (IOException e) {
/*  173 */             throw e;
/*      */           }
/*      */         }
/*  176 */       } else if (read.contains("<+-SSW_START_PATCHWORK_ARMOR_BLOCK-+>"))
/*      */       {
/*  178 */         armor.clear();
/*  179 */         boolean end = false;
/*  180 */         while (!end) {
/*  181 */           read = FR.readLine();
/*      */           
/*  183 */           if (read == null) { throw new IOException("Unexpected EOF: No End Patchwork Armor tag.");
/*      */           }
/*  185 */           if (read.contains("<+-SSW_END_PATCHWORK_ARMOR_BLOCK-+>")) {
/*  186 */             end = true;
/*      */           } else {
/*  188 */             armor.add(read);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  193 */         if (this.CurMech.GetArmor().IsPatchwork()) {
/*      */           try
/*      */           {
/*  196 */             for (int i = 0; i < armor.size(); i++) {
/*  197 */               FW.write(ProcessLine((String)armor.get(i)));
/*  198 */               FW.newLine();
/*      */             }
/*      */           } catch (IOException e) {
/*  201 */             throw e;
/*      */           }
/*      */         }
/*  204 */       } else if (read.contains("<+-SSW_REMOVE_IF_OMNI_NO_FIXED-+>"))
/*      */       {
/*      */ 
/*  207 */         if (this.CurMech.IsOmnimech()) {
/*  208 */           if (this.CurMech.GetLoadout().GetNonCore().size() > 0)
/*      */           {
/*  210 */             write = ProcessLine(read);
/*  211 */             FW.write(write);
/*  212 */             FW.newLine();
/*      */           }
/*      */         }
/*      */         else {
/*  216 */           write = ProcessLine(read);
/*  217 */           FW.write(write);
/*  218 */           FW.newLine();
/*      */         }
/*      */       }
/*      */       else {
/*  222 */         write = ProcessLine(read);
/*  223 */         FW.write(write);
/*  224 */         FW.newLine();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  230 */     FW.close();
/*  231 */     FR.close();
/*      */   }
/*      */   
/*      */ 
/*      */   private String ProcessLine(String read)
/*      */   {
/*  237 */     String retval = "";
/*  238 */     String getval = "";
/*  239 */     boolean RemoveIfBlank = false;
/*  240 */     boolean DitchString = false;
/*  241 */     if (read.contains("<+-SSW_REMOVE_IF_BLANK-+>"))
/*      */     {
/*  243 */       RemoveIfBlank = true;
/*      */     }
/*      */     
/*  246 */     if (read.contains("<+-SSW_"))
/*      */     {
/*  248 */       String[] s = read.split("<\\+-SSW_");
/*      */       
/*  250 */       if (s.length > 2)
/*      */       {
/*  252 */         retval = retval + s[0];
/*  253 */         for (int i = 1; i < s.length; i++)
/*      */         {
/*  255 */           String[] s1 = s[i].split("-\\+>");
/*      */           
/*  257 */           String test = "<+-SSW_" + s1[0] + "-+>";
/*  258 */           if (!test.equals("<+-SSW_REMOVE_IF_BLANK-+>"))
/*      */           {
/*  260 */             if (RemoveIfBlank) {
/*  261 */               getval = (String)this.lookup.get(test);
/*  262 */               if (getval != null) {
/*  263 */                 if (getval.equals(""))
/*      */                 {
/*  265 */                   DitchString = true;
/*      */                 } else {
/*  267 */                   retval = retval + getval;
/*      */                 }
/*      */               }
/*      */             } else {
/*  271 */               retval = retval + this.lookup.get(test);
/*      */             }
/*      */           }
/*      */           
/*  275 */           for (int j = 1; j < s1.length; j++) {
/*  276 */             retval = retval + s1[j];
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  281 */         retval = retval + s[0];
/*  282 */         String[] s1 = s[1].split("-\\+>");
/*      */         
/*  284 */         retval = retval + this.lookup.get(new StringBuilder().append("<+-SSW_").append(s1[0]).append("-+>").toString());
/*      */         
/*  286 */         for (int i = 1; i < s1.length; i++) {
/*  287 */           retval = retval + s1[i];
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*  292 */       retval = read;
/*      */     }
/*  294 */     if (DitchString) {
/*  295 */       return "";
/*      */     }
/*  297 */     return retval;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String BuildEquipLines(ArrayList lines, abPlaceable[] equips, boolean fluff)
/*      */   {
/*  304 */     String retval = "";
/*      */     
/*  306 */     if (equips.length <= 0) { return retval;
/*      */     }
/*  308 */     if (fluff)
/*      */     {
/*      */ 
/*  311 */       int numthistype = 1;
/*  312 */       abPlaceable cur = equips[0];
/*  313 */       equips[0] = null;
/*      */       
/*      */ 
/*  316 */       if (equips.length <= 1) {
/*  317 */         retval = retval + ProcessEquipFluffLines(cur, numthistype, lines);
/*  318 */         return retval;
/*      */       }
/*      */       
/*      */ 
/*  322 */       for (int i = 1; i <= equips.length; i++)
/*      */       {
/*  324 */         for (int j = 0; j < equips.length; j++) {
/*  325 */           if (equips[j] != null) {
/*  326 */             if ((equips[j] instanceof Equipment)) {
/*  327 */               if (((Equipment)equips[j]).IsVariableSize()) {
/*  328 */                 if ((equips[j].CritName().equals(cur.CritName())) && (equips[j].GetManufacturer().equals(cur.GetManufacturer()))) {
/*  329 */                   numthistype++;
/*  330 */                   equips[j] = null;
/*      */                 }
/*      */               }
/*  333 */               else if ((FileCommon.LookupStripArc(equips[j].LookupName()).equals(FileCommon.LookupStripArc(cur.LookupName()))) && (equips[j].GetManufacturer().equals(cur.GetManufacturer()))) {
/*  334 */                 numthistype++;
/*  335 */                 equips[j] = null;
/*      */               }
/*      */               
/*      */             }
/*  339 */             else if ((FileCommon.LookupStripArc(equips[j].LookupName()).equals(FileCommon.LookupStripArc(cur.LookupName()))) && (equips[j].GetManufacturer().equals(cur.GetManufacturer()))) {
/*  340 */               numthistype++;
/*  341 */               equips[j] = null;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  348 */         retval = retval + ProcessEquipFluffLines(cur, numthistype, lines);
/*      */         
/*      */ 
/*  351 */         cur = null;
/*  352 */         numthistype = 0;
/*  353 */         for (int j = 0; j < equips.length; j++) {
/*  354 */           if (equips[j] != null) {
/*  355 */             cur = equips[j];
/*  356 */             equips[j] = null;
/*  357 */             numthistype = 1;
/*  358 */             break;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  363 */         if (cur == null)
/*      */           break;
/*      */       }
/*      */     } else {
/*  367 */       int numthisloc = 1;
/*  368 */       abPlaceable cur = equips[0];
/*  369 */       equips[0] = null;
/*      */       
/*  371 */       if (equips.length <= 1) {
/*  372 */         retval = retval + ProcessEquipStatLines(cur, lines, 1);
/*  373 */         return retval;
/*      */       }
/*      */       
/*      */ 
/*  377 */       for (int i = 1; i <= equips.length; i++)
/*      */       {
/*  379 */         if ((!(cur instanceof MultiSlotSystem)) && (!(cur instanceof MechanicalJumpBooster))) { if (!(cur.CanSplit() | !cur.Contiguous())) {}
/*      */         }
/*      */         else {
/*  382 */           retval = retval + ProcessEquipStatLines(cur, lines, 1);
/*      */           break label730; }
/*  384 */         int loc = this.CurMech.GetLoadout().Find(cur);
/*  385 */         for (int j = 0; j < equips.length; j++) {
/*  386 */           if (equips[j] != null) {
/*  387 */             if ((equips[j] instanceof Equipment)) {
/*  388 */               if (((Equipment)equips[j]).IsVariableSize()) {
/*  389 */                 if ((equips[j].CritName().equals(cur.CritName())) && (this.CurMech.GetLoadout().Find(equips[j]) == loc)) {
/*  390 */                   numthisloc++;
/*  391 */                   equips[j] = null;
/*      */                 }
/*      */               }
/*  394 */               else if ((equips[j].LookupName().equals(cur.LookupName())) && (this.CurMech.GetLoadout().Find(equips[j]) == loc)) {
/*  395 */                 numthisloc++;
/*  396 */                 equips[j] = null;
/*      */               }
/*      */               
/*      */             }
/*  400 */             else if ((equips[j].LookupName().equals(cur.LookupName())) && (this.CurMech.GetLoadout().Find(equips[j]) == loc)) {
/*  401 */               numthisloc++;
/*  402 */               equips[j] = null;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  409 */         retval = retval + ProcessEquipStatLines(cur, lines, numthisloc);
/*      */         
/*      */         label730:
/*      */         
/*  413 */         cur = null;
/*  414 */         numthisloc = 0;
/*  415 */         for (int j = 0; j < equips.length; j++) {
/*  416 */           if (equips[j] != null) {
/*  417 */             cur = equips[j];
/*  418 */             equips[j] = null;
/*  419 */             numthisloc = 1;
/*  420 */             break;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  425 */         if (cur == null)
/*      */           break;
/*      */       }
/*      */     }
/*  429 */     return retval;
/*      */   }
/*      */   
/*      */   private String BuildOmniLines(ArrayList lines) throws IOException
/*      */   {
/*  434 */     String retval = "";
/*  435 */     ArrayList loadouts = this.CurMech.GetLoadouts();
/*  436 */     ArrayList EQLines = new ArrayList();
/*      */     
/*  438 */     for (int i = 0; i < loadouts.size(); i++)
/*      */     {
/*  440 */       this.CurMech.SetCurLoadout(((ifMechLoadout)loadouts.get(i)).GetName());
/*  441 */       EQLines.clear();
/*      */       
/*      */ 
/*  444 */       for (int j = 0; j < lines.size(); j++) {
/*  445 */         String test = (String)lines.get(j);
/*      */         
/*  447 */         if (test.contains("<+-SSW_START_EQUIPMENT_STAT_BLOCK-+>"))
/*      */         {
/*  449 */           boolean end = false;
/*  450 */           int add = j + 1;
/*  451 */           String read = "";
/*  452 */           while (!end) {
/*  453 */             read = (String)lines.get(add);
/*      */             
/*  455 */             if (read == null) { throw new IOException("Unexpected EOF: No End Equipment tag in Omnimech block.");
/*      */             }
/*  457 */             if (read.contains("<+-SSW_END_EQUIPMENT_STAT_BLOCK-+>")) {
/*  458 */               end = true;
/*      */             } else {
/*  460 */               EQLines.add(read);
/*  461 */               add++;
/*      */             }
/*      */           }
/*  464 */           abPlaceable[] a = GetEquips(false);
/*  465 */           retval = retval + BuildEquipLines(EQLines, a, false);
/*  466 */           j = add;
/*      */         } else {
/*  468 */           retval = retval + ProcessOmniLine(test);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  474 */     this.CurMech.SetCurLoadout("Base Loadout");
/*  475 */     return retval;
/*      */   }
/*      */   
/*      */ 
/*      */   private String ProcessOmniLine(String read)
/*      */   {
/*  481 */     String retval = "";
/*  482 */     String getval = "";
/*  483 */     boolean RemoveIfBlank = false;
/*  484 */     boolean DitchString = false;
/*  485 */     if (read.contains("<+-SSW_REMOVE_IF_BLANK-+>"))
/*      */     {
/*  487 */       RemoveIfBlank = true;
/*      */     }
/*      */     
/*  490 */     if (read.contains("<+-SSW_"))
/*      */     {
/*  492 */       String[] s = read.split("<\\+-SSW_");
/*      */       
/*  494 */       if (s.length > 2)
/*      */       {
/*  496 */         retval = retval + s[0];
/*  497 */         for (int i = 1; i < s.length; i++)
/*      */         {
/*  499 */           String[] s1 = s[i].split("-\\+>");
/*      */           
/*  501 */           String test = "<+-SSW_" + s1[0] + "-+>";
/*  502 */           if (!test.equals("<+-SSW_REMOVE_IF_BLANK-+>"))
/*      */           {
/*  504 */             if (RemoveIfBlank) {
/*  505 */               getval = GetOmniValue(test);
/*  506 */               if (getval != null) {
/*  507 */                 if (getval.equals(""))
/*      */                 {
/*  509 */                   DitchString = true;
/*      */                 } else {
/*  511 */                   retval = retval + getval;
/*      */                 }
/*      */               }
/*      */             } else {
/*  515 */               retval = retval + GetOmniValue(test);
/*      */             }
/*      */           }
/*      */           
/*  519 */           for (int j = 1; j < s1.length; j++) {
/*  520 */             retval = retval + s1[j];
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  525 */         retval = retval + s[0];
/*  526 */         String[] s1 = s[1].split("-\\+>");
/*      */         
/*  528 */         retval = retval + GetOmniValue(new StringBuilder().append("<+-SSW_").append(s1[0]).append("-+>").toString());
/*      */         
/*  530 */         for (int i = 1; i < s1.length; i++) {
/*  531 */           retval = retval + s1[i];
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*  536 */       retval = read;
/*      */     }
/*  538 */     if (DitchString) {
/*  539 */       return "";
/*      */     }
/*  541 */     return retval + System.getProperty("line.separator");
/*      */   }
/*      */   
/*      */ 
/*      */   private String GetOmniValue(String tag)
/*      */   {
/*  547 */     BattleForceStats bfs = new BattleForceStats(this.CurMech);
/*  548 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_NAME-+>"))
/*  549 */       return this.CurMech.GetLoadout().GetName();
/*  550 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BV-+>"))
/*  551 */       return String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) });
/*  552 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_RULES_LEVEL-+>")) {
/*  553 */       if (this.CurMech.GetBaseRulesLevel() == this.CurMech.GetLoadout().GetRulesLevel()) {
/*  554 */         return "";
/*      */       }
/*  556 */       return CommonTools.GetRulesLevelString(this.CurMech.GetLoadout().GetRulesLevel());
/*      */     }
/*  558 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_TECHBASE-+>")) {
/*  559 */       if (this.CurMech.GetLoadout().GetTechBase() != this.CurMech.GetBaseTechbase()) {
/*  560 */         return CommonTools.GetTechbaseString(this.CurMech.GetLoadout().GetTechBase());
/*      */       }
/*  562 */       return "";
/*      */     }
/*  564 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_COST-+>"))
/*  565 */       return String.format("%1$,.0f", new Object[] { Double.valueOf(this.CurMech.GetTotalCost()) });
/*  566 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_ACTUATOR_LINE-+>"))
/*  567 */       return FileCommon.BuildActuators(this.CurMech, true);
/*  568 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_HEATSINK_SPACE-+>"))
/*  569 */       return "" + this.CurMech.GetHeatSinks().NumCrits();
/*  570 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_HEATSINK_LOCATION_LINE-+>"))
/*  571 */       return FileCommon.GetHeatSinkLocations(this.CurMech);
/*  572 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_HEATSINK_TONNAGE-+>"))
/*  573 */       return FormatTonnage(this.CurMech.GetHeatSinks().GetLoadoutTonnage(), 1);
/*  574 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_HEATSINK_COUNT-+>"))
/*  575 */       return "" + this.CurMech.GetHeatSinks().GetNumHS();
/*  576 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_HEATSINK_DISSIPATION-+>"))
/*  577 */       return "" + this.CurMech.GetHeatSinks().TotalDissipation();
/*  578 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_HEATSINK_DISSIPATION_LINE-+>"))
/*  579 */       return GetHeatSinkLine();
/*  580 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_JUMPJET_SPACE-+>"))
/*  581 */       return "" + this.CurMech.GetJumpJets().ReportCrits();
/*  582 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_SPEED_JUMP_MP-+>")) {
/*  583 */       if (this.CurMech.GetJumpJets().GetNumJJ() == 0) {
/*  584 */         return "";
/*      */       }
/*  586 */       if (this.CurMech.UsingPartialWing()) {
/*  587 */         return this.CurMech.GetJumpJets().GetNumJJ() + " (" + this.CurMech.GetAdjustedJumpingMP(true) + ")";
/*      */       }
/*  589 */       return "" + this.CurMech.GetJumpJets().GetNumJJ();
/*      */     }
/*      */     
/*  592 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_JUMPJET_LOCATION_LINE-+>"))
/*  593 */       return FileCommon.GetJumpJetLocations(this.CurMech);
/*  594 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_JUMPJET_TONNAGE-+>"))
/*  595 */       return FormatTonnage(this.CurMech.GetJumpJets().GetTonnage(), 1);
/*  596 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_CASE_TONNAGE-+>")) {
/*  597 */       if ((this.CurMech.GetLoadout().GetTechBase() >= 1) && (this.CurMech.GetLoadout().IsUsingClanCASE())) {
/*  598 */         int[] Locs = this.CurMech.GetLoadout().FindExplosiveInstances();
/*  599 */         boolean check = false;
/*  600 */         for (int i = 0; i < Locs.length; i++) {
/*  601 */           if (Locs[i] > 0) {
/*  602 */             check = true;
/*      */           }
/*      */         }
/*  605 */         if (check) {
/*  606 */           return FormatTonnage(this.CurMech.GetCaseTonnage(), 1);
/*      */         }
/*  608 */         return "";
/*      */       }
/*      */       
/*  611 */       if (this.CurMech.GetCaseTonnage() <= 0.0D) {
/*  612 */         return "";
/*      */       }
/*  614 */       return FormatTonnage(this.CurMech.GetCaseTonnage(), 1);
/*      */     }
/*      */     
/*  617 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_CASE_LOCATION_LINE-+>"))
/*  618 */       return FileCommon.GetCaseLocations(this.CurMech);
/*  619 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_CASEII_TONNAGE-+>")) {
/*  620 */       if (this.CurMech.GetCASEIITonnage() <= 0.0D) {
/*  621 */         return "";
/*      */       }
/*  623 */       return FormatTonnage(this.CurMech.GetCASEIITonnage(), 1);
/*      */     }
/*  625 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_CASEII_LOCATION_LINE-+>"))
/*  626 */       return FileCommon.GetCaseIILocations(this.CurMech);
/*  627 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_AVAILABILITY-+>"))
/*  628 */       return this.CurMech.GetAvailability().GetBestCombinedCode();
/*  629 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_JUMPJET_TYPE-+>")) {
/*  630 */       if (this.CurMech.GetJumpJets().IsImproved()) {
/*  631 */         return "Improved";
/*      */       }
/*  633 */       return "Standard";
/*      */     }
/*  635 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_POWER_AMP_TONNAGE-+>")) {
/*  636 */       if (this.CurMech.GetEngine().IsNuclear()) {
/*  637 */         return "";
/*      */       }
/*  639 */       if (this.CurMech.GetLoadout().GetPowerAmplifier().GetTonnage() > 0.0D) {
/*  640 */         return FormatTonnage(this.CurMech.GetLoadout().GetPowerAmplifier().GetTonnage(), 1);
/*      */       }
/*  642 */       return "";
/*      */     }
/*      */     
/*  645 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_DAMAGE_STRING-+>")) {
/*  646 */       int[] BFdmg = this.CurMech.GetBFDamage(bfs);
/*  647 */       return BFdmg[0] + "/" + BFdmg[1] + "/" + BFdmg[2] + "/" + BFdmg[3]; }
/*  648 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_DAMAGE_SHORT-+>"))
/*  649 */       return "" + this.CurMech.GetBFDamage(bfs)[0];
/*  650 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_DAMAGE_MEDIUM-+>"))
/*  651 */       return "" + this.CurMech.GetBFDamage(bfs)[1];
/*  652 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_DAMAGE_LONG-+>"))
/*  653 */       return "" + this.CurMech.GetBFDamage(bfs)[2];
/*  654 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_DAMAGE_EXTREME-+>"))
/*  655 */       return "" + this.CurMech.GetBFDamage(bfs)[3];
/*  656 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_OVERHEAT-+>"))
/*  657 */       return "" + this.CurMech.GetBFDamage(bfs)[4];
/*  658 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_ARMOR-+>"))
/*  659 */       return "" + this.CurMech.GetBFArmor();
/*  660 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_STRUCTURE-+>"))
/*  661 */       return "" + this.CurMech.GetBFStructure();
/*  662 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_POINTS-+>"))
/*  663 */       return "" + this.CurMech.GetBFPoints();
/*  664 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_SIZE-+>"))
/*  665 */       return "" + this.CurMech.GetBFSize();
/*  666 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_MOVEMENT-+>"))
/*  667 */       return BattleForceTools.GetMovementString(this.CurMech);
/*  668 */     if (tag.equals("<+-SSW_OMNI_LOADOUT_BF_SPECIALS-+>")) {
/*  669 */       return "" + bfs.getAbilitiesString();
/*      */     }
/*  671 */     return "";
/*      */   }
/*      */   
/*      */   private String ProcessEquipFluffLines(abPlaceable a, int num, ArrayList lines)
/*      */   {
/*  676 */     String retval = "";
/*  677 */     String test = "";
/*  678 */     String plural = "";
/*  679 */     if (num > 1) {
/*  680 */       plural = "s";
/*      */     }
/*      */     
/*  683 */     for (int i = 0; i < lines.size(); i++) {
/*  684 */       test = (String)lines.get(i);
/*  685 */       if (test.contains("<+-SSW_"))
/*      */       {
/*  687 */         String[] s = test.split("<\\+-SSW_");
/*      */         
/*  689 */         if (s.length > 2)
/*      */         {
/*  691 */           retval = retval + s[0];
/*  692 */           for (int j = 1; j < s.length; j++)
/*      */           {
/*  694 */             String[] s1 = s[j].split("-\\+>");
/*      */             
/*  696 */             String check = "<+-SSW_" + s1[0] + "-+>";
/*      */             
/*  698 */             if (check.equals("<+-SSW_EQUIP_COUNT_THIS_TYPE-+>")) {
/*  699 */               retval = retval + "" + num;
/*  700 */             } else if (check.equals("<+-SSW_EQUIP_NAME-+>")) {
/*  701 */               if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/*  702 */                 if ((a instanceof Equipment)) {
/*  703 */                   if (((Equipment)a).IsVariableSize()) {
/*  704 */                     retval = retval + FileCommon.LookupStripArc(a.CritName());
/*      */                   } else {
/*  706 */                     retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */                   }
/*      */                 } else {
/*  709 */                   retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */                 }
/*      */               } else {
/*  712 */                 retval = retval + FileCommon.LookupStripArc(a.CritName()) + plural;
/*      */               }
/*  714 */             } else if (check.equals("<+-SSW_EQUIP_FULL_NAME-+>")) {
/*  715 */               if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/*  716 */                 if ((a instanceof RangedWeapon)) {
/*  717 */                   if (((RangedWeapon)a).IsUsingFCS()) {
/*  718 */                     retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural + " w/ " + ((abPlaceable)((RangedWeapon)a).GetFCS()).LookupName();
/*      */                   } else {
/*  720 */                     retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */                   }
/*  722 */                 } else if ((a instanceof Equipment)) {
/*  723 */                   if (((Equipment)a).IsVariableSize()) {
/*  724 */                     retval = retval + FileCommon.LookupStripArc(a.CritName());
/*      */                   } else {
/*  726 */                     retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */                   }
/*      */                 } else {
/*  729 */                   retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */                 }
/*      */               }
/*  732 */               else if ((a instanceof RangedWeapon)) {
/*  733 */                 if (((RangedWeapon)a).IsUsingFCS()) {
/*  734 */                   retval = retval + FileCommon.LookupStripArc(a.CritName()) + plural + " w/ " + ((abPlaceable)((RangedWeapon)a).GetFCS()).CritName();
/*      */                 } else {
/*  736 */                   retval = retval + FileCommon.LookupStripArc(a.CritName()) + plural;
/*      */                 }
/*      */               } else {
/*  739 */                 retval = retval + FileCommon.LookupStripArc(a.CritName()) + plural;
/*      */               }
/*      */             }
/*  742 */             else if (check.equals("<+-SSW_EQUIP_MANUFACTURER-+>")) {
/*  743 */               retval = retval + a.GetManufacturer();
/*      */             }
/*      */             
/*      */ 
/*  747 */             for (int k = 1; k < s1.length; k++) {
/*  748 */               retval = retval + s1[k];
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/*  753 */           retval = retval + s[0];
/*  754 */           String[] s1 = s[1].split("-\\+>");
/*      */           
/*  756 */           String check = "<+-SSW_" + s1[0] + "-+>";
/*      */           
/*  758 */           if (check.equals("<+-SSW_EQUIP_COUNT_THIS_TYPE-+>")) {
/*  759 */             if (num > 1) {
/*  760 */               retval = retval + num + " ";
/*      */             }
/*  762 */           } else if (check.equals("<+-SSW_EQUIP_NAME-+>")) {
/*  763 */             if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/*  764 */               if ((a instanceof Equipment)) {
/*  765 */                 if (((Equipment)a).IsVariableSize()) {
/*  766 */                   retval = retval + FileCommon.LookupStripArc(a.CritName());
/*      */                 } else {
/*  768 */                   retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */                 }
/*      */               } else {
/*  771 */                 retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */               }
/*      */             } else {
/*  774 */               retval = retval + FileCommon.LookupStripArc(a.CritName()) + plural;
/*      */             }
/*  776 */           } else if (check.equals("<+-SSW_EQUIP_FULL_NAME-+>")) {
/*  777 */             if (this.CurMech.GetLoadout().GetTechBase() == 2) {
/*  778 */               if ((a instanceof RangedWeapon)) {
/*  779 */                 if (((RangedWeapon)a).IsUsingFCS()) {
/*  780 */                   retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural + " w/ " + ((abPlaceable)((RangedWeapon)a).GetFCS()).LookupName();
/*      */                 } else {
/*  782 */                   retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */                 }
/*  784 */               } else if ((a instanceof Equipment)) {
/*  785 */                 if (((Equipment)a).IsVariableSize()) {
/*  786 */                   retval = retval + FileCommon.LookupStripArc(a.CritName());
/*      */                 } else {
/*  788 */                   retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */                 }
/*      */               } else {
/*  791 */                 retval = retval + FileCommon.LookupStripArc(a.LookupName()) + plural;
/*      */               }
/*      */             }
/*  794 */             else if ((a instanceof RangedWeapon)) {
/*  795 */               if (((RangedWeapon)a).IsUsingFCS()) {
/*  796 */                 retval = retval + FileCommon.LookupStripArc(a.CritName()) + plural + " w/ " + ((abPlaceable)((RangedWeapon)a).GetFCS()).CritName();
/*      */               } else {
/*  798 */                 retval = retval + FileCommon.LookupStripArc(a.CritName()) + plural;
/*      */               }
/*      */             } else {
/*  801 */               retval = retval + FileCommon.LookupStripArc(a.CritName()) + plural;
/*      */             }
/*      */           }
/*  804 */           else if (check.equals("<+-SSW_EQUIP_MANUFACTURER-+>")) {
/*  805 */             retval = retval + a.GetManufacturer();
/*      */           }
/*      */           
/*      */ 
/*  809 */           for (int j = 1; j < s1.length; j++) {
/*  810 */             retval = retval + s1[j];
/*      */           }
/*      */         }
/*  813 */         retval = retval + System.getProperty("line.separator");
/*      */       }
/*      */       else {
/*  816 */         retval = retval + test + System.getProperty("line.separator");
/*      */       }
/*      */     }
/*      */     
/*  820 */     return retval;
/*      */   }
/*      */   
/*      */   private String ProcessEquipStatLines(abPlaceable a, ArrayList lines, int numthisloc) {
/*  824 */     String retval = "";
/*  825 */     String test = "";
/*      */     
/*  827 */     for (int i = 0; i < lines.size(); i++) {
/*  828 */       test = (String)lines.get(i);
/*  829 */       if (test.contains("<+-SSW_"))
/*      */       {
/*  831 */         String[] s = test.split("<\\+-SSW_");
/*      */         
/*  833 */         if (s.length > 2)
/*      */         {
/*  835 */           retval = retval + s[0];
/*  836 */           for (int j = 1; j < s.length; j++)
/*      */           {
/*  838 */             String[] s1 = s[j].split("-\\+>");
/*      */             
/*  840 */             String check = "<+-SSW_" + s1[0] + "-+>";
/*      */             
/*  842 */             if (check.equals("<+-SSW_EQUIP_NAME-+>")) {
/*  843 */               String plural = "";
/*  844 */               if (numthisloc > 1) {
/*  845 */                 plural = "s";
/*      */               }
/*  847 */               if ((a instanceof Ammunition)) {
/*  848 */                 retval = retval + FileCommon.FormatAmmoExportName((Ammunition)a, numthisloc);
/*      */               } else {
/*  850 */                 retval = retval + FileCommon.GetExportName(this.CurMech, a);
/*  851 */                 retval = retval + plural;
/*      */               }
/*  853 */             } else if (check.equals("<+-SSW_EQUIP_COUNT_THIS_LOC-+>")) {
/*  854 */               if ((!(a instanceof Ammunition)) && 
/*  855 */                 (numthisloc > 1)) {
/*  856 */                 retval = retval + numthisloc + " ";
/*      */               }
/*      */             }
/*  859 */             else if (check.equals("<+-SSW_EQUIP_MANUFACTURER-+>")) {
/*  860 */               retval = retval + a.GetManufacturer();
/*  861 */             } else if (check.equals("<+-SSW_EQUIP_TONNAGE-+>")) {
/*  862 */               if ((a instanceof RangedWeapon)) {
/*  863 */                 if (((RangedWeapon)a).IsUsingFCS()) {
/*  864 */                   double tons = a.GetTonnage() - ((abPlaceable)((RangedWeapon)a).GetFCS()).GetTonnage();
/*  865 */                   retval = retval + FormatTonnage(tons, numthisloc);
/*  866 */                 } else if (((RangedWeapon)a).IsInArray()) {
/*  867 */                   MGArray m = ((RangedWeapon)a).GetMyArray();
/*  868 */                   retval = retval + FormatTonnage(m.GetMGTons(), numthisloc);
/*  869 */                 } else if (((RangedWeapon)a).IsUsingCapacitor()) {
/*  870 */                   retval = retval + FormatTonnage(a.GetTonnage() - 1.0D, numthisloc);
/*      */                 } else {
/*  872 */                   retval = retval + FormatTonnage(a.GetTonnage(), numthisloc);
/*      */                 }
/*  874 */               } else if ((a instanceof ifMissileGuidance)) {
/*  875 */                 retval = retval + FormatTonnage(a.GetTonnage(), numthisloc);
/*  876 */               } else if ((a instanceof MGArray)) {
/*  877 */                 retval = retval + FormatTonnage(((MGArray)a).GetBaseTons(), numthisloc);
/*      */               } else {
/*  879 */                 retval = retval + FormatTonnage(a.GetTonnage(), numthisloc);
/*      */               }
/*  881 */             } else if (check.equals("<+-SSW_EQUIP_CRITS-+>")) {
/*  882 */               if (a.CanSplit()) {
/*  883 */                 retval = retval + FileCommon.DecodeCrits(this.CurMech.GetLoadout().FindInstances(a));
/*      */               }
/*  885 */               else if ((a instanceof MGArray)) {
/*  886 */                 if (numthisloc > 1) {
/*  887 */                   retval = retval + numthisloc;
/*      */                 } else {
/*  889 */                   retval = retval + "1";
/*      */                 }
/*  891 */               } else if ((a instanceof MultiSlotSystem)) {
/*  892 */                 retval = retval + ((MultiSlotSystem)a).ReportCrits();
/*  893 */               } else if ((a instanceof MechanicalJumpBooster)) {
/*  894 */                 if (this.CurMech.IsQuad()) {
/*  895 */                   retval = retval + 8;
/*      */                 } else {
/*  897 */                   retval = retval + 4;
/*      */                 }
/*      */               }
/*  900 */               else if (numthisloc > 1) {
/*  901 */                 retval = retval + a.NumCrits() * numthisloc;
/*      */               } else {
/*  903 */                 retval = retval + a.NumCrits();
/*      */               }
/*      */               
/*      */             }
/*  907 */             else if (check.equals("<+-SSW_EQUIP_LOCATION-+>")) {
/*  908 */               if (a.CanSplit()) {
/*  909 */                 retval = retval + FileCommon.EncodeLocations(this.CurMech.GetLoadout().FindInstances(a), this.CurMech.IsQuad());
/*      */               }
/*  911 */               else if (((a instanceof MultiSlotSystem)) || ((a instanceof MechanicalJumpBooster))) {
/*  912 */                 retval = retval + "*";
/*      */               } else {
/*  914 */                 retval = retval + FileCommon.EncodeLocation(this.CurMech.GetLoadout().Find(a), this.CurMech.IsQuad());
/*      */               }
/*      */             }
/*  917 */             else if (check.equals("<+-SSW_EQUIP_HEAT-+>")) {
/*  918 */               if ((a instanceof ifWeapon)) {
/*  919 */                 if (((ifWeapon)a).IsUltra()) {
/*  920 */                   retval = retval + ((ifWeapon)a).GetHeat() + " /shot (" + ((ifWeapon)a).GetBVHeat() * numthisloc + " max)";
/*  921 */                 } else if (((ifWeapon)a).IsRotary()) {
/*  922 */                   retval = retval + ((ifWeapon)a).GetHeat() + " /shot (" + ((ifWeapon)a).GetBVHeat() * numthisloc + " max)";
/*      */                 } else {
/*  924 */                   retval = retval + "" + ((ifWeapon)a).GetHeat() * numthisloc;
/*      */                 }
/*  926 */               } else if ((a instanceof Equipment)) {
/*  927 */                 retval = retval + "" + ((Equipment)a).GetHeat() * numthisloc;
/*  928 */               } else if (a.GetMechModifier() != null) {
/*  929 */                 retval = retval + a.GetMechModifier().HeatAdder();
/*      */               } else {
/*  931 */                 retval = retval + "--";
/*      */               }
/*  933 */             } else if (check.equals("<+-SSW_EQUIP_DAMAGE-+>")) {
/*  934 */               if ((a instanceof ifWeapon)) {
/*  935 */                 ifWeapon w = (ifWeapon)a;
/*  936 */                 if (w.GetWeaponClass() == 2) {
/*  937 */                   if (w.IsCluster()) {
/*  938 */                     retval = retval + w.GetDamageShort() + "/msl";
/*      */                   } else {
/*  940 */                     retval = retval + w.GetDamageShort() + "";
/*      */                   }
/*  942 */                 } else if ((w.GetDamageShort() == w.GetDamageMedium()) && (w.GetDamageShort() == w.GetDamageLong())) {
/*  943 */                   if ((w.IsUltra()) || (w.IsRotary())) {
/*  944 */                     retval = retval + w.GetDamageShort() + "/shot";
/*      */                   } else {
/*  946 */                     retval = retval + w.GetDamageShort();
/*      */                   }
/*      */                 } else {
/*  949 */                   retval = retval + w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong();
/*      */                 }
/*      */               } else {
/*  952 */                 retval = retval + "--";
/*      */               }
/*  954 */             } else if (check.equals("<+-SSW_EQUIP_RANGE-+>")) {
/*  955 */               if ((a instanceof ifWeapon)) {
/*  956 */                 ifWeapon w = (ifWeapon)a;
/*  957 */                 if (w.GetRangeLong() < 1) {
/*  958 */                   if (w.GetRangeMedium() < 1) {
/*  959 */                     retval = retval + w.GetRangeShort();
/*      */                   } else {
/*  961 */                     retval = retval + w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/-";
/*      */                   }
/*      */                 } else {
/*  964 */                   retval = retval + w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/" + w.GetRangeLong();
/*      */                 }
/*  966 */               } else if ((a instanceof Equipment)) {
/*  967 */                 Equipment e = (Equipment)a;
/*  968 */                 if ((e.GetShortRange() <= 0) && (e.GetMediumRange() <= 0)) {
/*  969 */                   if (e.GetLongRange() > 0) {
/*  970 */                     retval = retval + e.GetLongRange();
/*      */                   } else {
/*  972 */                     retval = retval + "--";
/*      */                   }
/*      */                 } else {
/*  975 */                   retval = retval + "0/" + e.GetShortRange() + "/" + e.GetMediumRange() + "/" + e.GetLongRange();
/*      */                 }
/*      */               } else {
/*  978 */                 retval = retval + "--";
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  983 */             for (int k = 1; k < s1.length; k++) {
/*  984 */               retval = retval + s1[k];
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/*  989 */           retval = retval + s[0];
/*  990 */           String[] s1 = s[1].split("-\\+>");
/*      */           
/*  992 */           String check = "<+-SSW_" + s1[0] + "-+>";
/*      */           
/*  994 */           if (check.equals("<+-SSW_EQUIP_NAME-+>")) {
/*  995 */             if ((a instanceof Ammunition)) {
/*  996 */               retval = retval + FileCommon.FormatAmmoExportName((Ammunition)a, numthisloc);
/*      */             } else {
/*  998 */               String plural = "";
/*  999 */               if (numthisloc > 1) {
/* 1000 */                 plural = "s";
/*      */               }
/* 1002 */               retval = retval + FileCommon.GetExportName(this.CurMech, a);
/* 1003 */               retval = retval + plural;
/*      */             }
/* 1005 */           } else if (check.equals("<+-SSW_EQUIP_COUNT_THIS_LOC-+>")) {
/* 1006 */             if ((!(a instanceof Ammunition)) && 
/* 1007 */               (numthisloc > 1)) {
/* 1008 */               retval = retval + numthisloc + " ";
/*      */             }
/*      */           }
/* 1011 */           else if (check.equals("<+-SSW_EQUIP_MANUFACTURER-+>")) {
/* 1012 */             retval = retval + a.GetManufacturer();
/* 1013 */           } else if (check.equals("<+-SSW_EQUIP_TONNAGE-+>")) {
/* 1014 */             if ((a instanceof RangedWeapon)) {
/* 1015 */               if (((RangedWeapon)a).IsUsingFCS()) {
/* 1016 */                 double tons = a.GetTonnage() - ((abPlaceable)((RangedWeapon)a).GetFCS()).GetTonnage();
/* 1017 */                 retval = retval + FormatTonnage(tons, numthisloc);
/* 1018 */               } else if (((RangedWeapon)a).IsInArray()) {
/* 1019 */                 MGArray m = ((RangedWeapon)a).GetMyArray();
/* 1020 */                 retval = retval + FormatTonnage(m.GetMGTons(), numthisloc);
/* 1021 */               } else if (((RangedWeapon)a).IsUsingCapacitor()) {
/* 1022 */                 retval = retval + FormatTonnage(a.GetTonnage() - 1.0D, numthisloc);
/*      */               } else {
/* 1024 */                 retval = retval + FormatTonnage(a.GetTonnage(), numthisloc);
/*      */               }
/* 1026 */             } else if ((a instanceof ifMissileGuidance)) {
/* 1027 */               retval = retval + FormatTonnage(a.GetTonnage(), numthisloc);
/* 1028 */             } else if ((a instanceof MGArray)) {
/* 1029 */               retval = retval + FormatTonnage(((MGArray)a).GetBaseTons(), numthisloc);
/*      */             } else {
/* 1031 */               retval = retval + FormatTonnage(a.GetTonnage(), numthisloc);
/*      */             }
/* 1033 */           } else if (check.equals("<+-SSW_EQUIP_CRITS-+>")) {
/* 1034 */             if (a.CanSplit()) {
/* 1035 */               retval = retval + FileCommon.DecodeCrits(this.CurMech.GetLoadout().FindInstances(a));
/*      */             }
/* 1037 */             else if ((a instanceof MGArray)) {
/* 1038 */               if (numthisloc > 1) {
/* 1039 */                 retval = retval + numthisloc;
/*      */               } else {
/* 1041 */                 retval = retval + "1";
/*      */               }
/* 1043 */             } else if ((a instanceof MultiSlotSystem)) {
/* 1044 */               retval = retval + ((MultiSlotSystem)a).ReportCrits();
/* 1045 */             } else if ((a instanceof MechanicalJumpBooster)) {
/* 1046 */               if (this.CurMech.IsQuad()) {
/* 1047 */                 retval = retval + 8;
/*      */               } else {
/* 1049 */                 retval = retval + 4;
/*      */               }
/*      */             }
/* 1052 */             else if (numthisloc > 1) {
/* 1053 */               retval = retval + a.NumCrits() * numthisloc;
/*      */             } else {
/* 1055 */               retval = retval + a.NumCrits();
/*      */             }
/*      */             
/*      */           }
/* 1059 */           else if (check.equals("<+-SSW_EQUIP_LOCATION-+>")) {
/* 1060 */             if (a.CanSplit()) {
/* 1061 */               retval = retval + FileCommon.EncodeLocations(this.CurMech.GetLoadout().FindInstances(a), this.CurMech.IsQuad());
/*      */             }
/* 1063 */             else if (((a instanceof MultiSlotSystem)) || ((a instanceof MechanicalJumpBooster))) {
/* 1064 */               retval = retval + "*";
/*      */             } else {
/* 1066 */               retval = retval + FileCommon.EncodeLocation(this.CurMech.GetLoadout().Find(a), this.CurMech.IsQuad());
/*      */             }
/*      */           }
/* 1069 */           else if (check.equals("<+-SSW_EQUIP_HEAT-+>")) {
/* 1070 */             if ((a instanceof ifWeapon)) {
/* 1071 */               if (((ifWeapon)a).IsUltra()) {
/* 1072 */                 retval = retval + ((ifWeapon)a).GetHeat() + " /shot (" + ((ifWeapon)a).GetBVHeat() * numthisloc + " max)";
/* 1073 */               } else if (((ifWeapon)a).IsRotary()) {
/* 1074 */                 retval = retval + ((ifWeapon)a).GetHeat() + " /shot (" + ((ifWeapon)a).GetBVHeat() * numthisloc + " max)";
/*      */               } else {
/* 1076 */                 retval = retval + "" + ((ifWeapon)a).GetHeat() * numthisloc;
/*      */               }
/* 1078 */             } else if ((a instanceof Equipment)) {
/* 1079 */               retval = retval + "" + ((Equipment)a).GetHeat() * numthisloc;
/* 1080 */             } else if (a.GetMechModifier() != null) {
/* 1081 */               retval = retval + a.GetMechModifier().HeatAdder();
/*      */             } else {
/* 1083 */               retval = retval + "--";
/*      */             }
/* 1085 */           } else if (check.equals("<+-SSW_EQUIP_DAMAGE-+>")) {
/* 1086 */             if ((a instanceof ifWeapon)) {
/* 1087 */               ifWeapon w = (ifWeapon)a;
/* 1088 */               if (w.GetWeaponClass() == 2) {
/* 1089 */                 if (w.IsCluster()) {
/* 1090 */                   retval = retval + w.GetDamageShort() + "/msl";
/*      */                 } else {
/* 1092 */                   retval = retval + w.GetDamageShort() + "";
/*      */                 }
/* 1094 */               } else if ((w.GetDamageShort() == w.GetDamageMedium()) && (w.GetDamageShort() == w.GetDamageLong())) {
/* 1095 */                 if ((w.IsUltra()) || (w.IsRotary())) {
/* 1096 */                   retval = retval + w.GetDamageShort() + "/shot";
/*      */                 } else {
/* 1098 */                   retval = retval + w.GetDamageShort();
/*      */                 }
/*      */               } else {
/* 1101 */                 retval = retval + w.GetDamageShort() + "/" + w.GetDamageMedium() + "/" + w.GetDamageLong();
/*      */               }
/*      */             } else {
/* 1104 */               retval = retval + "--";
/*      */             }
/* 1106 */           } else if (check.equals("<+-SSW_EQUIP_RANGE-+>")) {
/* 1107 */             if ((a instanceof ifWeapon)) {
/* 1108 */               ifWeapon w = (ifWeapon)a;
/* 1109 */               if (w.GetRangeLong() < 1) {
/* 1110 */                 if (w.GetRangeMedium() < 1) {
/* 1111 */                   retval = retval + w.GetRangeShort();
/*      */                 } else {
/* 1113 */                   retval = retval + w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/-";
/*      */                 }
/*      */               } else {
/* 1116 */                 retval = retval + w.GetRangeMin() + "/" + w.GetRangeShort() + "/" + w.GetRangeMedium() + "/" + w.GetRangeLong();
/*      */               }
/* 1118 */             } else if ((a instanceof Equipment)) {
/* 1119 */               Equipment e = (Equipment)a;
/* 1120 */               if ((e.GetShortRange() <= 0) && (e.GetMediumRange() <= 0)) {
/* 1121 */                 if (e.GetLongRange() > 0) {
/* 1122 */                   retval = retval + e.GetLongRange();
/*      */                 } else {
/* 1124 */                   retval = retval + "--";
/*      */                 }
/*      */               } else {
/* 1127 */                 retval = retval + "0/" + e.GetShortRange() + "/" + e.GetMediumRange() + "/" + e.GetLongRange();
/*      */               }
/*      */             } else {
/* 1130 */               retval = retval + "--";
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1135 */           for (int j = 1; j < s1.length; j++) {
/* 1136 */             retval = retval + s1[j];
/*      */           }
/*      */         }
/* 1139 */         retval = retval + System.getProperty("line.separator");
/*      */       }
/*      */       else {
/* 1142 */         retval = retval + test + System.getProperty("line.separator");
/*      */       }
/*      */     }
/*      */     
/* 1146 */     return retval;
/*      */   }
/*      */   
/*      */   private abPlaceable[] GetEquips(boolean fluff)
/*      */   {
/* 1151 */     ArrayList v = this.CurMech.GetLoadout().GetNonCore();
/* 1152 */     ArrayList ret = new ArrayList();
/* 1153 */     if (fluff) {
/* 1154 */       ArrayList EQ = new ArrayList();
/*      */       
/*      */ 
/* 1157 */       for (int i = 0; i < v.size(); i++) {
/* 1158 */         if ((v.get(i) instanceof ifWeapon)) {
/* 1159 */           ret.add(v.get(i));
/*      */         } else {
/* 1161 */           EQ.add(v.get(i));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1166 */       Object[] o = this.CurMech.SortWeapons(ret, false);
/* 1167 */       ret.clear();
/* 1168 */       for (int i = 0; i < o.length; i++) {
/* 1169 */         ret.add(o[i]);
/*      */       }
/*      */       
/*      */ 
/* 1173 */       for (int i = 0; i < EQ.size(); i++) {
/* 1174 */         if (!(EQ.get(i) instanceof Ammunition)) {
/* 1175 */           ret.add(EQ.get(i));
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 1180 */       ret = (ArrayList)v.clone();
/*      */       
/*      */ 
/* 1183 */       if (this.CurMech.GetPhysEnhance().IsMASC()) {
/* 1184 */         ret.add(this.CurMech.GetPhysEnhance());
/*      */       }
/* 1186 */       if (this.CurMech.UsingTC()) {
/* 1187 */         ret.add(this.CurMech.GetTC());
/*      */       }
/* 1189 */       if (this.CurMech.HasCommandConsole()) {
/* 1190 */         ret.add(this.CurMech.GetCommandConsole());
/*      */       }
/* 1192 */       if (this.CurMech.UsingPartialWing()) {
/* 1193 */         ret.add(this.CurMech.GetPartialWing());
/*      */       }
/* 1195 */       if (this.CurMech.GetLoadout().HasSupercharger()) {
/* 1196 */         ret.add(this.CurMech.GetLoadout().GetSupercharger());
/*      */       }
/* 1198 */       if (this.CurMech.GetLoadout().HasHDTurret()) {
/* 1199 */         ret.add(this.CurMech.GetLoadout().GetHDTurret());
/*      */       }
/* 1201 */       if (this.CurMech.GetLoadout().HasLTTurret()) {
/* 1202 */         ret.add(this.CurMech.GetLoadout().GetLTTurret());
/*      */       }
/* 1204 */       if (this.CurMech.GetLoadout().HasRTTurret()) {
/* 1205 */         ret.add(this.CurMech.GetLoadout().GetRTTurret());
/*      */       }
/* 1207 */       if (this.CurMech.IsQuad()) {
/* 1208 */         if (this.CurMech.HasLegAES()) {
/* 1209 */           ret.add(this.CurMech.GetRAAES());
/* 1210 */           ret.add(this.CurMech.GetLAAES());
/* 1211 */           ret.add(this.CurMech.GetRLAES());
/* 1212 */           ret.add(this.CurMech.GetLLAES());
/*      */         }
/*      */       } else {
/* 1215 */         if (this.CurMech.HasRAAES()) {
/* 1216 */           ret.add(this.CurMech.GetRAAES());
/*      */         }
/* 1218 */         if (this.CurMech.HasLAAES()) {
/* 1219 */           ret.add(this.CurMech.GetLAAES());
/*      */         }
/* 1221 */         if (this.CurMech.HasLegAES()) {
/* 1222 */           ret.add(this.CurMech.GetRLAES());
/* 1223 */           ret.add(this.CurMech.GetLLAES());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1228 */       ret = FileCommon.SortEquipmentForStats(this.CurMech, ret);
/*      */       
/*      */ 
/* 1231 */       for (int i = 0; i < ret.size(); i++) {
/* 1232 */         if ((ret.get(i) instanceof RangedWeapon)) {
/* 1233 */           if (((RangedWeapon)ret.get(i)).IsUsingFCS()) {
/* 1234 */             ret.add(i + 1, ((RangedWeapon)ret.get(i)).GetFCS());
/*      */           }
/* 1236 */           if (((RangedWeapon)ret.get(i)).IsUsingCapacitor()) {
/* 1237 */             ret.add(i + 1, ((RangedWeapon)ret.get(i)).GetCapacitor());
/*      */           }
/* 1239 */         } else if ((ret.get(i) instanceof MGArray)) {
/* 1240 */           ret.add(i + 1, ((MGArray)ret.get(i)).GetMGs()[0]);
/* 1241 */           ret.add(i + 2, ((MGArray)ret.get(i)).GetMGs()[1]);
/* 1242 */           if (((MGArray)ret.get(i)).GetMGs()[2] != null) {
/* 1243 */             ret.add(i + 3, ((MGArray)ret.get(i)).GetMGs()[2]);
/*      */           }
/* 1245 */           if (((MGArray)ret.get(i)).GetMGs()[3] != null) {
/* 1246 */             ret.add(i + 4, ((MGArray)ret.get(i)).GetMGs()[3]);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1252 */       if (this.CurMech.HasNullSig()) {
/* 1253 */         ret.add(this.CurMech.GetNullSig());
/*      */       }
/* 1255 */       if (this.CurMech.HasChameleon()) {
/* 1256 */         ret.add(this.CurMech.GetChameleon());
/*      */       }
/* 1258 */       if (this.CurMech.HasVoidSig()) {
/* 1259 */         ret.add(this.CurMech.GetVoidSig());
/*      */       }
/* 1261 */       if (this.CurMech.HasBlueShield()) {
/* 1262 */         ret.add(this.CurMech.GetBlueShield());
/*      */       }
/* 1264 */       if (this.CurMech.UsingJumpBooster()) {
/* 1265 */         ret.add(this.CurMech.GetJumpBooster());
/*      */       }
/* 1267 */       if (this.CurMech.HasEnviroSealing()) {
/* 1268 */         ret.add(this.CurMech.GetEnviroSealing());
/*      */       }
/* 1270 */       if (this.CurMech.HasTracks()) {
/* 1271 */         ret.add(this.CurMech.GetTracks());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1276 */     abPlaceable[] retval = new abPlaceable[ret.size()];
/* 1277 */     for (int i = 0; i < ret.size(); i++) {
/* 1278 */       retval[i] = ((abPlaceable)ret.get(i));
/*      */     }
/* 1280 */     return retval;
/*      */   }
/*      */   
/*      */   private void BuildHash()
/*      */   {
/* 1285 */     this.lookup.put("<+-SSW_NAME-+>", this.CurMech.GetName());
/* 1286 */     this.lookup.put("<+-SSW_MODEL-+>", this.CurMech.GetModel());
/* 1287 */     if (this.CurMech.IsPrimitive()) {
/* 1288 */       this.lookup.put("<+-SSW_TECHBASE-+>", CommonTools.GetTechbaseString(this.CurMech.GetBaseTechbase()) + " (Primitive)");
/*      */     } else {
/* 1290 */       this.lookup.put("<+-SSW_TECHBASE-+>", CommonTools.GetTechbaseString(this.CurMech.GetBaseTechbase()));
/*      */     }
/* 1292 */     this.lookup.put("<+-SSW_TONNAGE-+>", FormatTonnage(this.CurMech.GetTonnage(), 1));
/* 1293 */     this.lookup.put("<+-SSW_DRY_TONNAGE-+>", FormatTonnage(this.CurMech.GetCurrentDryTons(), 1));
/* 1294 */     AvailableCode AC = this.CurMech.GetAvailability();
/* 1295 */     this.lookup.put("<+-SSW_AVAILABILITY-+>", AC.GetBestCombinedCode());
/* 1296 */     this.lookup.put("<+-SSW_PROD_YEAR-+>", "" + this.CurMech.GetYear());
/* 1297 */     switch (AC.GetTechBase()) {
/*      */     case 0: 
/* 1299 */       if (AC.WentExtinctIS()) {
/* 1300 */         if (AC.WasReIntrodIS()) {
/* 1301 */           if (AC.GetISIntroDate() >= AC.GetISReIntroDate()) {
/* 1302 */             if (this.CurMech.YearWasSpecified()) {
/* 1303 */               this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/* 1304 */               if (this.CurMech.GetYear() >= AC.GetISReIntroDate()) {
/* 1305 */                 this.lookup.put("<+-SSW_EXTINCT_BY-+>", "Never");
/*      */               } else {
/* 1307 */                 this.lookup.put("<+-SSW_EXTINCT_BY-+>", "" + AC.GetISExtinctDate());
/*      */               }
/*      */             } else {
/* 1310 */               this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "" + AC.GetISIntroDate());
/* 1311 */               this.lookup.put("<+-SSW_EXTINCT_BY-+>", "Never");
/*      */             }
/*      */           }
/* 1314 */           else if (this.CurMech.YearWasSpecified()) {
/* 1315 */             this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/* 1316 */             if (this.CurMech.GetYear() >= AC.GetISReIntroDate()) {
/* 1317 */               this.lookup.put("<+-SSW_EXTINCT_BY-+>", "Never");
/*      */             } else {
/* 1319 */               this.lookup.put("<+-SSW_EXTINCT_BY-+>", "" + AC.GetISExtinctDate());
/*      */             }
/*      */           } else {
/* 1322 */             this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "" + AC.GetISIntroDate());
/* 1323 */             this.lookup.put("<+-SSW_EXTINCT_BY-+>", "" + AC.GetISExtinctDate());
/*      */           }
/*      */         }
/*      */         else {
/* 1327 */           if (this.CurMech.YearWasSpecified()) {
/* 1328 */             this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/*      */           } else {
/* 1330 */             this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "" + AC.GetISIntroDate());
/*      */           }
/* 1332 */           this.lookup.put("<+-SSW_EXTINCT_BY-+>", "" + AC.GetISExtinctDate());
/*      */         }
/*      */       } else {
/* 1335 */         if (this.CurMech.YearWasSpecified()) {
/* 1336 */           this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/*      */         } else {
/* 1338 */           this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "" + AC.GetISIntroDate());
/*      */         }
/* 1340 */         this.lookup.put("<+-SSW_EXTINCT_BY-+>", "Never");
/*      */       }
/* 1342 */       break;
/*      */     case 1: 
/* 1344 */       if (AC.WentExtinctCL()) {
/* 1345 */         if (AC.WasReIntrodCL()) {
/* 1346 */           if (AC.GetCLIntroDate() >= AC.GetCLReIntroDate()) {
/* 1347 */             if (this.CurMech.YearWasSpecified()) {
/* 1348 */               this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/*      */             } else {
/* 1350 */               this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "" + AC.GetCLIntroDate());
/*      */             }
/* 1352 */             this.lookup.put("<+-SSW_EXTINCT_BY-+>", "Never");
/*      */           } else {
/* 1354 */             if (this.CurMech.YearWasSpecified()) {
/* 1355 */               this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/*      */             } else {
/* 1357 */               this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "" + AC.GetCLIntroDate());
/*      */             }
/* 1359 */             this.lookup.put("<+-SSW_EXTINCT_BY-+>", "" + AC.GetCLExtinctDate());
/*      */           }
/*      */         } else {
/* 1362 */           if (this.CurMech.YearWasSpecified()) {
/* 1363 */             this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/*      */           } else {
/* 1365 */             this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "" + AC.GetCLIntroDate());
/*      */           }
/* 1367 */           this.lookup.put("<+-SSW_EXTINCT_BY-+>", "" + AC.GetCLExtinctDate());
/*      */         }
/*      */       } else {
/* 1370 */         if (this.CurMech.YearWasSpecified()) {
/* 1371 */           this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/*      */         } else {
/* 1373 */           this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "" + AC.GetCLIntroDate());
/*      */         }
/* 1375 */         this.lookup.put("<+-SSW_EXTINCT_BY-+>", "Never");
/*      */       }
/* 1377 */       break;
/*      */     case 2: 
/* 1379 */       this.lookup.put("<+-SSW_EARLIEST_YEAR-+>", "");
/* 1380 */       this.lookup.put("<+-SSW_EXTINCT_BY-+>", "Unknown");
/*      */     }
/*      */     
/* 1383 */     this.lookup.put("<+-SSW_OVERVIEW-+>", ProcessFluffString(this.CurMech.GetOverview()));
/* 1384 */     this.lookup.put("<+-SSW_CAPABILITIES-+>", ProcessFluffString(this.CurMech.GetCapabilities()));
/* 1385 */     this.lookup.put("<+-SSW_BATTLE_HISTORY-+>", ProcessFluffString(this.CurMech.GetHistory()));
/* 1386 */     this.lookup.put("<+-SSW_DEPLOYMENT-+>", ProcessFluffString(this.CurMech.GetDeployment()));
/* 1387 */     this.lookup.put("<+-SSW_VARIANTS-+>", ProcessFluffString(this.CurMech.GetVariants()));
/* 1388 */     this.lookup.put("<+-SSW_NOTABLES-+>", ProcessFluffString(this.CurMech.GetNotables()));
/* 1389 */     this.lookup.put("<+-SSW_ADDITIONAL-+>", ProcessFluffString(this.CurMech.GetAdditional()));
/* 1390 */     this.lookup.put("<+-SSW_MANUFACTURER-+>", this.CurMech.GetCompany());
/* 1391 */     this.lookup.put("<+-SSW_MANUFACTURER_LOCATION-+>", this.CurMech.GetLocation());
/* 1392 */     this.lookup.put("<+-SSW_MANUFACTURER_ENGINE-+>", this.CurMech.GetEngineManufacturer() + " " + this.CurMech.GetEngine().GetRating() + " " + this.CurMech.GetEngine());
/* 1393 */     this.lookup.put("<+-SSW_MANUFACTURER_CHASSIS-+>", this.CurMech.GetChassisModel() + " " + this.CurMech.GetIntStruc().CritName());
/* 1394 */     if ((this.CurMech.HasCTCase()) || (this.CurMech.HasLTCase()) || (this.CurMech.HasRTCase())) {
/* 1395 */       this.lookup.put("<+-SSW_MANUFACTURER_ARMOR-+>", this.CurMech.GetArmorModel() + " " + this.CurMech.GetArmor().CritName() + " w/ CASE");
/*      */     } else {
/* 1397 */       this.lookup.put("<+-SSW_MANUFACTURER_ARMOR-+>", this.CurMech.GetArmorModel() + " " + this.CurMech.GetArmor().CritName());
/*      */     }
/* 1399 */     this.lookup.put("<+-SSW_MANUFACTURER_JUMPJETS-+>", this.CurMech.GetJJModel());
/* 1400 */     this.lookup.put("<+-SSW_MANUFACTURER_COMM_SYSTEM-+>", GetCommSystem());
/* 1401 */     this.lookup.put("<+-SSW_MANUFACTURER_T_AND_T_SYSTEM-+>", GetTandTSystem());
/* 1402 */     this.lookup.put("<+-SSW_CHASSIS_TONNAGE-+>", FormatTonnage(this.CurMech.GetIntStruc().GetTonnage(), 1));
/* 1403 */     this.lookup.put("<+-SSW_ARMOR_TONNAGE-+>", FormatTonnage(this.CurMech.GetArmor().GetTonnage(), 1));
/* 1404 */     this.lookup.put("<+-SSW_ENGINE_TONNAGE-+>", FormatTonnage(this.CurMech.GetEngine().GetTonnage(), 1));
/* 1405 */     this.lookup.put("<+-SSW_GYRO_TONNAGE-+>", FormatTonnage(this.CurMech.GetGyro().GetTonnage(), 1));
/* 1406 */     this.lookup.put("<+-SSW_COCKPIT_TONNAGE-+>", FormatTonnage(this.CurMech.GetCockpit().GetTonnage(), 1));
/* 1407 */     this.lookup.put("<+-SSW_HEATSINK_TONNAGE-+>", FormatTonnage(this.CurMech.GetHeatSinks().GetTonnage(), 1));
/* 1408 */     this.lookup.put("<+-SSW_JUMPJET_TONNAGE-+>", FormatTonnage(this.CurMech.GetJumpJets().GetTonnage(), 1));
/* 1409 */     this.lookup.put("<+-SSW_ENHANCEMENT_TONNAGE-+>", FormatTonnage(this.CurMech.GetPhysEnhance().GetTonnage(), 1));
/*      */     
/* 1411 */     this.lookup.put("<+-SSW_EQUIPMENT_TOTAL_TONNAGE-+>", "");
/* 1412 */     this.lookup.put("<+-SSW_HD_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(0));
/* 1413 */     this.lookup.put("<+-SSW_CT_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(1));
/* 1414 */     this.lookup.put("<+-SSW_LT_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(2));
/* 1415 */     this.lookup.put("<+-SSW_RT_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(3));
/* 1416 */     if (this.CurMech.GetArmor().GetLocationArmor(3) != this.CurMech.GetArmor().GetLocationArmor(2)) {
/* 1417 */       this.lookup.put("<+-SSW_TORSO_ARMOR-+>", "LT: " + this.CurMech.GetArmor().GetLocationArmor(2) + " RT: " + this.CurMech.GetArmor().GetLocationArmor(3));
/*      */     } else {
/* 1419 */       this.lookup.put("<+-SSW_TORSO_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(2));
/*      */     }
/* 1421 */     this.lookup.put("<+-SSW_LA_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(4));
/* 1422 */     this.lookup.put("<+-SSW_RA_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(5));
/* 1423 */     if (this.CurMech.GetArmor().GetLocationArmor(5) != this.CurMech.GetArmor().GetLocationArmor(4)) {
/* 1424 */       this.lookup.put("<+-SSW_ARM_ARMOR-+>", "LA: " + this.CurMech.GetArmor().GetLocationArmor(4) + " RA: " + this.CurMech.GetArmor().GetLocationArmor(5));
/*      */     } else {
/* 1426 */       this.lookup.put("<+-SSW_ARM_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(4));
/*      */     }
/* 1428 */     this.lookup.put("<+-SSW_LL_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(6));
/* 1429 */     this.lookup.put("<+-SSW_RL_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(7));
/* 1430 */     if (this.CurMech.GetArmor().GetLocationArmor(7) != this.CurMech.GetArmor().GetLocationArmor(6)) {
/* 1431 */       this.lookup.put("<+-SSW_LEG_ARMOR-+>", "LL: " + this.CurMech.GetArmor().GetLocationArmor(6) + " RL: " + this.CurMech.GetArmor().GetLocationArmor(7));
/*      */     } else {
/* 1433 */       this.lookup.put("<+-SSW_LEG_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(6));
/*      */     }
/* 1435 */     this.lookup.put("<+-SSW_CTR_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(8));
/* 1436 */     this.lookup.put("<+-SSW_LTR_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(9));
/* 1437 */     this.lookup.put("<+-SSW_RTR_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(10));
/* 1438 */     if (this.CurMech.GetArmor().GetLocationArmor(10) != this.CurMech.GetArmor().GetLocationArmor(9)) {
/* 1439 */       this.lookup.put("<+-SSW_TORSO_REAR_ARMOR-+>", "LTR: " + this.CurMech.GetArmor().GetLocationArmor(9) + " RTR: " + this.CurMech.GetArmor().GetLocationArmor(10));
/*      */     } else {
/* 1441 */       this.lookup.put("<+-SSW_TORSO_REAR_ARMOR-+>", "" + this.CurMech.GetArmor().GetLocationArmor(9));
/*      */     }
/* 1443 */     this.lookup.put("<+-SSW_HD_ARMOR_TYPE-+>", " (" + this.CurMech.GetArmor().GetHDArmorType().LookupName() + ")");
/* 1444 */     this.lookup.put("<+-SSW_CT_ARMOR_TYPE-+>", " (" + this.CurMech.GetArmor().GetCTArmorType().LookupName() + ")");
/* 1445 */     this.lookup.put("<+-SSW_LT_ARMOR_TYPE-+>", " (" + this.CurMech.GetArmor().GetLTArmorType().LookupName() + ")");
/* 1446 */     this.lookup.put("<+-SSW_RT_ARMOR_TYPE-+>", " (" + this.CurMech.GetArmor().GetRTArmorType().LookupName() + ")");
/* 1447 */     this.lookup.put("<+-SSW_LA_ARMOR_TYPE-+>", " (" + this.CurMech.GetArmor().GetLAArmorType().LookupName() + ")");
/* 1448 */     this.lookup.put("<+-SSW_RA_ARMOR_TYPE-+>", " (" + this.CurMech.GetArmor().GetRAArmorType().LookupName() + ")");
/* 1449 */     this.lookup.put("<+-SSW_LL_ARMOR_TYPE-+>", " (" + this.CurMech.GetArmor().GetLLArmorType().LookupName() + ")");
/* 1450 */     this.lookup.put("<+-SSW_RL_ARMOR_TYPE-+>", " (" + this.CurMech.GetArmor().GetRLArmorType().LookupName() + ")");
/* 1451 */     this.lookup.put("<+-SSW_HD_INTERNAL-+>", "" + this.CurMech.GetIntStruc().GetHeadPoints());
/* 1452 */     this.lookup.put("<+-SSW_CT_INTERNAL-+>", "" + this.CurMech.GetIntStruc().GetCTPoints());
/* 1453 */     this.lookup.put("<+-SSW_TORSO_INTERNAL-+>", "" + this.CurMech.GetIntStruc().GetSidePoints());
/* 1454 */     this.lookup.put("<+-SSW_ARM_INTERNAL-+>", "" + this.CurMech.GetIntStruc().GetArmPoints());
/* 1455 */     this.lookup.put("<+-SSW_LEG_INTERNAL-+>", "" + this.CurMech.GetIntStruc().GetLegPoints());
/* 1456 */     this.lookup.put("<+-SSW_ARMOR_COVERAGE-+>", "" + this.CurMech.GetArmor().GetCoverage());
/* 1457 */     this.lookup.put("<+-SSW_JUMPJET_COUNT-+>", "" + this.CurMech.GetJumpJets().GetNumJJ());
/* 1458 */     this.lookup.put("<+-SSW_JUMPJET_DISTANCE-+>", GetJumpJetDistanceLine());
/* 1459 */     this.lookup.put("<+-SSW_SPEED_WALK_KMPH-+>", CommonTools.FormatSpeed(this.CurMech.GetWalkingMP() * 10.8D) + " km/h");
/* 1460 */     if (this.CurMech.GetAdjustedRunningMP(false, true) != this.CurMech.GetRunningMP()) {
/* 1461 */       this.lookup.put("<+-SSW_SPEED_RUN_KMPH-+>", CommonTools.FormatSpeed(this.CurMech.GetRunningMP() * 10.8D) + " km/h (" + CommonTools.FormatSpeed(this.CurMech.GetAdjustedRunningMP(false, true) * 10.8D) + " km/h)");
/*      */     } else {
/* 1463 */       this.lookup.put("<+-SSW_SPEED_RUN_KMPH-+>", CommonTools.FormatSpeed(this.CurMech.GetRunningMP() * 10.8D) + " km/h");
/*      */     }
/* 1465 */     this.lookup.put("<+-SSW_SPEED_WALK_MP-+>", "" + this.CurMech.GetWalkingMP());
/* 1466 */     if (this.CurMech.GetAdjustedRunningMP(false, true) != this.CurMech.GetRunningMP()) {
/* 1467 */       this.lookup.put("<+-SSW_SPEED_RUN_MP-+>", this.CurMech.GetRunningMP() + " (" + this.CurMech.GetAdjustedRunningMP(false, true) + ")");
/*      */     } else {
/* 1469 */       this.lookup.put("<+-SSW_SPEED_RUN_MP-+>", "" + this.CurMech.GetRunningMP());
/*      */     }
/* 1471 */     this.lookup.put("<+-SSW_SPEED_JUMP_MP-+>", GetJumpingMPLine());
/* 1472 */     this.lookup.put("<+-SSW_COST-+>", String.format("%1$,.0f", new Object[] { Double.valueOf(this.CurMech.GetTotalCost()) }));
/* 1473 */     this.lookup.put("<+-SSW_DRY_COST-+>", String.format("%1$,.0f", new Object[] { Double.valueOf(this.CurMech.GetDryCost()) }));
/* 1474 */     this.lookup.put("<+-SSW_BV2-+>", String.format("%1$,d", new Object[] { Integer.valueOf(this.CurMech.GetCurrentBV()) }));
/* 1475 */     this.lookup.put("<+-SSW_ENGINE_SPACE-+>", "" + this.CurMech.GetEngine().ReportCrits());
/* 1476 */     this.lookup.put("<+-SSW_CHASSIS_SPACE-+>", "" + this.CurMech.GetIntStruc().NumCrits());
/* 1477 */     this.lookup.put("<+-SSW_CHASSIS_LOCATION_LINE-+>", FileCommon.GetInternalLocations(this.CurMech));
/* 1478 */     this.lookup.put("<+-SSW_COCKPIT_SPACE-+>", "" + this.CurMech.GetCockpit().ReportCrits());
/* 1479 */     this.lookup.put("<+-SSW_GYRO_SPACE-+>", "" + this.CurMech.GetGyro().NumCrits());
/* 1480 */     this.lookup.put("<+-SSW_ARMOR_SPACE-+>", "" + this.CurMech.GetArmor().NumCrits());
/* 1481 */     this.lookup.put("<+-SSW_ARMOR_LOCATION_LINE-+>", FileCommon.GetArmorLocations(this.CurMech));
/* 1482 */     this.lookup.put("<+-SSW_HEATSINK_TOTAL_SPACE-+>", "" + this.CurMech.GetHeatSinks().NumCrits());
/* 1483 */     this.lookup.put("<+-SSW_HEATSINK_LOCATION_LINE-+>", FileCommon.GetHeatSinkLocations(this.CurMech));
/* 1484 */     this.lookup.put("<+-SSW_ACTUATOR_LINE-+>", FileCommon.BuildActuators(this.CurMech, true));
/* 1485 */     if (FileCommon.NeedsLegActuatorLine(this.CurMech)) {
/* 1486 */       this.lookup.put("<+-SSW_LEG_ACTUATOR_LINE-+>", FileCommon.BuildLegActuators(this.CurMech, true));
/*      */     } else {
/* 1488 */       this.lookup.put("<+-SSW_LEG_ACTUATOR_LINE-+>", "");
/*      */     }
/* 1490 */     this.lookup.put("<+-SSW_ENHANCEMENT_SPACE-+>", "" + this.CurMech.GetPhysEnhance().NumCrits());
/* 1491 */     this.lookup.put("<+-SSW_ENHANCEMENT_LOCATION_LINE-+>", FileCommon.GetTSMLocations(this.CurMech));
/* 1492 */     this.lookup.put("<+-SSW_JUMPJET_SPACE-+>", "" + this.CurMech.GetJumpJets().ReportCrits());
/* 1493 */     this.lookup.put("<+-SSW_JUMPJET_LOCATION_LINE-+>", FileCommon.GetJumpJetLocations(this.CurMech));
/* 1494 */     this.lookup.put("<+-SSW_ARMOR_FACTOR-+>", "" + this.CurMech.GetArmor().GetArmorValue());
/* 1495 */     this.lookup.put("<+-SSW_ENGINE_RATING-+>", "" + this.CurMech.GetEngine().GetRating());
/* 1496 */     this.lookup.put("<+-SSW_ENGINE_TYPE-+>", FileCommon.GetExportName(this.CurMech, this.CurMech.GetEngine()));
/* 1497 */     this.lookup.put("<+-SSW_HEATSINK_DISSIPATION_LINE-+>", GetHeatSinkLine());
/* 1498 */     if (this.CurMech.GetHeatSinks().GetNumHS() < this.CurMech.GetEngine().InternalHeatSinks()) {
/* 1499 */       this.lookup.put("<+-SSW_HEATSINKS_IN_ENGINE-+>", "" + this.CurMech.GetHeatSinks().GetNumHS());
/*      */     } else {
/* 1501 */       this.lookup.put("<+-SSW_HEATSINKS_IN_ENGINE-+>", "" + this.CurMech.GetEngine().InternalHeatSinks());
/*      */     }
/* 1503 */     if (this.CurMech.GetTechBase() == 2) {
/* 1504 */       this.lookup.put("<+-SSW_HEATSINK_TYPE-+>", this.CurMech.GetHeatSinks().GetCurrentState().LookupName());
/*      */     }
/* 1506 */     else if (this.CurMech.GetHeatSinks().IsDouble()) {
/* 1507 */       this.lookup.put("<+-SSW_HEATSINK_TYPE-+>", "Double");
/* 1508 */     } else if (this.CurMech.GetHeatSinks().IsCompact()) {
/* 1509 */       this.lookup.put("<+-SSW_HEATSINK_TYPE-+>", "Compact");
/* 1510 */     } else if (this.CurMech.GetHeatSinks().IsLaser()) {
/* 1511 */       this.lookup.put("<+-SSW_HEATSINK_TYPE-+>", "Laser");
/*      */     } else {
/* 1513 */       this.lookup.put("<+-SSW_HEATSINK_TYPE-+>", "Single");
/*      */     }
/*      */     
/* 1516 */     this.lookup.put("<+-SSW_GYRO_TYPE-+>", FileCommon.GetExportName(this.CurMech, this.CurMech.GetGyro()));
/* 1517 */     this.lookup.put("<+-SSW_COCKPIT_TYPE-+>", FileCommon.GetExportName(this.CurMech, this.CurMech.GetCockpit()));
/* 1518 */     if (FileCommon.NeedsCockpitComponentLine(this.CurMech)) {
/* 1519 */       this.lookup.put("<+-SSW_COCKPIT_COMPONENT_LINE-+>", FileCommon.GetCockpitComponentLine(this.CurMech));
/*      */     } else {
/* 1521 */       this.lookup.put("<+-SSW_COCKPIT_COMPONENT_LINE-+>", "");
/*      */     }
/* 1523 */     this.lookup.put("<+-SSW_ARMOR_TYPE-+>", this.CurMech.GetArmor().CritName());
/* 1524 */     this.lookup.put("<+-SSW_JUMPJET_TYPE-+>", GetJumpJetTypeLine());
/* 1525 */     this.lookup.put("<+-SSW_HEATSINK_COUNT-+>", "" + this.CurMech.GetHeatSinks().GetNumHS());
/* 1526 */     this.lookup.put("<+-SSW_HEATSINK_DISSIPATION-+>", "" + this.CurMech.GetHeatSinks().TotalDissipation());
/* 1527 */     this.lookup.put("<+-SSW_INTERNAL_TYPE-+>", this.CurMech.GetIntStruc().CritName());
/* 1528 */     this.lookup.put("<+-SSW_CASE_LOCATION_LINE-+>", FileCommon.GetCaseLocations(this.CurMech));
/* 1529 */     this.lookup.put("<+-SSW_CASE_TONNAGE-+>", FormatTonnage(this.CurMech.GetCaseTonnage(), 1));
/* 1530 */     this.lookup.put("<+-SSW_CASEII_LOCATION_LINE-+>", FileCommon.GetCaseIILocations(this.CurMech));
/* 1531 */     this.lookup.put("<+-SSW_CASEII_TONNAGE-+>", FormatTonnage(this.CurMech.GetCASEIITonnage(), 1));
/* 1532 */     if (this.CurMech.IsQuad()) {
/* 1533 */       if (this.CurMech.IsIndustrialmech()) {
/* 1534 */         this.lookup.put("<+-SSW_CHASSIS_CONFIG-+>", "Quad IndustrialMech");
/*      */       } else {
/* 1536 */         this.lookup.put("<+-SSW_CHASSIS_CONFIG-+>", "Quad");
/*      */       }
/* 1538 */       this.lookup.put("<+-SSW_ARM_LOCATION_NAME-+>", "R/L Front Leg");
/* 1539 */       this.lookup.put("<+-SSW_LEG_LOCATION_NAME-+>", "R/L Rear Leg");
/* 1540 */       this.lookup.put("<+-SSW_LA_LOCATION_NAME-+>", "Front Left Leg");
/* 1541 */       this.lookup.put("<+-SSW_RA_LOCATION_NAME-+>", "Front Right Leg");
/* 1542 */       this.lookup.put("<+-SSW_LL_LOCATION_NAME-+>", "Left Rear Leg");
/* 1543 */       this.lookup.put("<+-SSW_RL_LOCATION_NAME-+>", "Right Rear Leg");
/* 1544 */       this.lookup.put("<+-SSW_ARM_LOCATION_LONGNAME-+>", "Right/Left Front Leg");
/* 1545 */       this.lookup.put("<+-SSW_LEG_LOCATION_LONGNAME-+>", "Right/Left Rear Leg");
/*      */     } else {
/* 1547 */       if (this.CurMech.IsIndustrialmech()) {
/* 1548 */         this.lookup.put("<+-SSW_CHASSIS_CONFIG-+>", "Biped IndustrialMech");
/*      */       } else {
/* 1550 */         this.lookup.put("<+-SSW_CHASSIS_CONFIG-+>", "Biped");
/*      */       }
/* 1552 */       this.lookup.put("<+-SSW_ARM_LOCATION_NAME-+>", "R/L Arm");
/* 1553 */       this.lookup.put("<+-SSW_LEG_LOCATION_NAME-+>", "R/L Leg");
/* 1554 */       this.lookup.put("<+-SSW_LA_LOCATION_NAME-+>", "Left Arm");
/* 1555 */       this.lookup.put("<+-SSW_RA_LOCATION_NAME-+>", "Right Arm");
/* 1556 */       this.lookup.put("<+-SSW_LL_LOCATION_NAME-+>", "Left Leg");
/* 1557 */       this.lookup.put("<+-SSW_RL_LOCATION_NAME-+>", "Right Leg");
/* 1558 */       this.lookup.put("<+-SSW_ARM_LOCATION_LONGNAME-+>", "Right/Left Arm");
/* 1559 */       this.lookup.put("<+-SSW_LEG_LOCATION_LONGNAME-+>", "Right/Left Leg");
/*      */     }
/* 1561 */     this.lookup.put("<+-SSW_RULES_LEVEL-+>", CommonTools.GetRulesLevelString(this.CurMech.GetRulesLevel()));
/* 1562 */     if (this.CurMech.IsOmnimech()) {
/* 1563 */       this.lookup.put("<+-SSW_POD_TONNAGE-+>", FormatTonnage(this.CurMech.GetTonnage() - this.CurMech.GetCurrentTons(), 1));
/*      */     } else {
/* 1565 */       this.lookup.put("<+-SSW_POD_TONNAGE-+>", "");
/*      */     }
/* 1567 */     if (this.CurMech.GetEngine().IsNuclear()) {
/* 1568 */       this.lookup.put("<+-SSW_POWER_AMP_TONNAGE-+>", "");
/*      */     }
/* 1570 */     else if (this.CurMech.GetLoadout().GetPowerAmplifier().GetTonnage() > 0.0D) {
/* 1571 */       this.lookup.put("<+-SSW_POWER_AMP_TONNAGE-+>", FormatTonnage(this.CurMech.GetLoadout().GetPowerAmplifier().GetTonnage(), 1));
/*      */     } else {
/* 1573 */       this.lookup.put("<+-SSW_POWER_AMP_TONNAGE-+>", "");
/*      */     }
/*      */     
/*      */ 
/* 1577 */     this.lookup.put("<+-SSW_REMOVE_IF_OMNI_NO_FIXED-+>", "");
/* 1578 */     this.lookup.put("<+-SSW_MULTISLOTNOTES-+>", BuildMultiSlotNotes());
/* 1579 */     if (this.CurMech.HasEjectionSeat()) {
/* 1580 */       this.lookup.put("<+-SSW_EJECTIONSEAT_TONNAGE-+>", "" + this.CurMech.GetEjectionSeat().GetTonnage());
/*      */     } else {
/* 1582 */       this.lookup.put("<+-SSW_EJECTIONSEAT_TONNAGE-+>", "");
/*      */     }
/* 1584 */     BattleForceStats bfs = new BattleForceStats(this.CurMech);
/* 1585 */     int[] BFdmg = this.CurMech.GetBFDamage(bfs);
/* 1586 */     this.lookup.put("<+-SSW_BF_DAMAGE_STRING-+>", BFdmg[0] + "/" + BFdmg[1] + "/" + BFdmg[2] + "/" + BFdmg[3]);
/* 1587 */     this.lookup.put("<+-SSW_BF_DAMAGE_SHORT-+>", "" + BFdmg[0]);
/* 1588 */     this.lookup.put("<+-SSW_BF_DAMAGE_MEDIUM-+>", "" + BFdmg[1]);
/* 1589 */     this.lookup.put("<+-SSW_BF_DAMAGE_LONG-+>", "" + BFdmg[2]);
/* 1590 */     this.lookup.put("<+-SSW_BF_DAMAGE_EXTREME-+>", "" + BFdmg[3]);
/* 1591 */     this.lookup.put("<+-SSW_BF_OVERHEAT-+>", "" + BFdmg[4]);
/* 1592 */     this.lookup.put("<+-SSW_BF_ARMOR-+>", "" + this.CurMech.GetBFArmor());
/* 1593 */     this.lookup.put("<+-SSW_BF_STRUCTURE-+>", "" + this.CurMech.GetBFStructure());
/* 1594 */     this.lookup.put("<+-SSW_BF_POINTS-+>", "" + this.CurMech.GetBFPoints());
/* 1595 */     this.lookup.put("<+-SSW_BF_SIZE-+>", "" + this.CurMech.GetBFSize());
/* 1596 */     this.lookup.put("<+-SSW_BF_MOVEMENT-+>", BattleForceTools.GetMovementString(this.CurMech));
/* 1597 */     this.lookup.put("<+-SSW_BF_SPECIALS-+>", bfs.getAbilitiesString());
/* 1598 */     if (this.CurMech.UsingFractionalAccounting()) {
/* 1599 */       this.lookup.put("<+-SSW_USING_FRACTIONAL_ACCOUNTING-+>", "Fractional Accounting");
/*      */     } else {
/* 1601 */       this.lookup.put("<+-SSW_USING_FRACTIONAL_ACCOUNTING-+>", "");
/*      */     }
/*      */   }
/*      */   
/*      */   private String GetHeatSinkLine()
/*      */   {
/* 1607 */     String retval = "";
/* 1608 */     if (this.CurMech.GetHeatSinks().IsDouble()) {
/* 1609 */       retval = this.CurMech.GetHeatSinks().GetNumHS() + " (" + this.CurMech.GetHeatSinks().TotalDissipation() + ")";
/*      */     } else {
/* 1611 */       retval = this.CurMech.GetHeatSinks().GetNumHS() + "";
/*      */     }
/* 1613 */     return retval;
/*      */   }
/*      */   
/*      */   private String GetCommSystem() {
/* 1617 */     return this.CurMech.GetCommSystem();
/*      */   }
/*      */   
/*      */   private String GetTandTSystem() {
/* 1621 */     return this.CurMech.GetTandTSystem();
/*      */   }
/*      */   
/*      */   private String ProcessFluffString(String fluff)
/*      */   {
/* 1626 */     if ((fluff.equals("")) || (fluff.equals("\n")) || (fluff.equals("\n\r"))) {
/* 1627 */       return "";
/*      */     }
/* 1629 */     String retval = "";
/* 1630 */     fluff = fluff.replaceAll("\n\r", "\n");
/* 1631 */     fluff = fluff.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
/* 1632 */     fluff = fluff.replaceAll(":tab:", "");
/* 1633 */     String[] s = fluff.split("\n", -1);
/*      */     
/* 1635 */     for (int i = 0; i < s.length; i++) {
/* 1636 */       retval = retval + s[i] + this.NL;
/*      */     }
/*      */     
/* 1639 */     return retval;
/*      */   }
/*      */   
/*      */   private String BuildMultiSlotNotes() {
/* 1643 */     String retval = "";
/* 1644 */     if (this.CurMech.HasNullSig()) {
/* 1645 */       retval = retval + "* The " + this.CurMech.GetNullSig().LookupName() + " occupies 1 slot in every location except the HD." + this.NL;
/*      */     }
/* 1647 */     if (this.CurMech.HasVoidSig()) {
/* 1648 */       retval = retval + "* The " + this.CurMech.GetVoidSig().LookupName() + " occupies 1 slot in every location except the HD." + this.NL;
/*      */     }
/* 1650 */     if (this.CurMech.HasChameleon()) {
/* 1651 */       retval = retval + "* The " + this.CurMech.GetChameleon().LookupName() + " occupies 1 slot in every location except the HD and CT." + this.NL;
/*      */     }
/* 1653 */     if (this.CurMech.HasBlueShield()) {
/* 1654 */       retval = retval + "* The " + this.CurMech.GetBlueShield().LookupName() + " occupies 1 slot in every location except the HD." + this.NL;
/*      */     }
/* 1656 */     if (this.CurMech.UsingJumpBooster()) {
/* 1657 */       retval = retval + "* The " + this.CurMech.GetJumpBooster().LookupName() + " occupies 2 slots in each leg." + this.NL;
/*      */     }
/* 1659 */     if (this.CurMech.HasEnviroSealing()) {
/* 1660 */       retval = retval + "* The " + this.CurMech.GetEnviroSealing().LookupName() + " occupies 1 slot in every location." + this.NL;
/*      */     }
/* 1662 */     if (this.CurMech.HasTracks()) {
/* 1663 */       retval = retval + "* " + this.CurMech.GetTracks().LookupName() + " occupy 1 slot in every leg location." + this.NL;
/*      */     }
/* 1665 */     return retval;
/*      */   }
/*      */   
/*      */   private String FormatTonnage(double d, int num) {
/* 1669 */     return String.format("%1" + this.tformat, new Object[] { Double.valueOf(d * num) });
/*      */   }
/*      */   
/*      */   private String GetJumpJetDistanceLine() {
/* 1673 */     String retval = this.CurMech.GetJumpJets().GetNumJJ() * 30 + " meters";
/* 1674 */     if (this.CurMech.GetAdjustedJumpingMP(false) != this.CurMech.GetJumpJets().GetNumJJ()) {
/* 1675 */       retval = retval + " (" + this.CurMech.GetAdjustedJumpingMP(false) * 30 + " meters)";
/*      */     }
/* 1677 */     if (this.CurMech.UsingJumpBooster()) {
/* 1678 */       retval = retval + " / " + this.CurMech.GetJumpBoosterMP() * 30 + " meters";
/* 1679 */       if (this.CurMech.GetJumpBoosterMP() != this.CurMech.GetAdjustedBoosterMP(false)) {
/* 1680 */         retval = retval + " (" + this.CurMech.GetAdjustedBoosterMP(false) * 30 + " meters)";
/*      */       }
/*      */     }
/* 1683 */     return retval;
/*      */   }
/*      */   
/*      */   private String GetJumpingMPLine() {
/* 1687 */     String retval = "" + this.CurMech.GetJumpJets().GetNumJJ();
/* 1688 */     if (this.CurMech.GetAdjustedJumpingMP(false) != this.CurMech.GetJumpJets().GetNumJJ()) {
/* 1689 */       retval = retval + " (" + this.CurMech.GetAdjustedJumpingMP(false) + ")";
/*      */     }
/* 1691 */     if (this.CurMech.UsingJumpBooster()) {
/* 1692 */       retval = retval + " / " + this.CurMech.GetJumpBoosterMP();
/* 1693 */       if (this.CurMech.GetJumpBoosterMP() != this.CurMech.GetAdjustedBoosterMP(false)) {
/* 1694 */         retval = retval + " (" + this.CurMech.GetAdjustedBoosterMP(false) + ")";
/*      */       }
/*      */     }
/* 1697 */     return retval;
/*      */   }
/*      */   
/*      */   private String GetJumpJetTypeLine() {
/* 1701 */     String retval = "";
/* 1702 */     if (this.CurMech.UsingJumpBooster()) {
/* 1703 */       if (this.CurMech.GetJumpJets().GetNumJJ() <= 0) {
/* 1704 */         retval = "Jump Booster";
/*      */       }
/* 1706 */       else if (this.CurMech.GetJumpJets().IsImproved()) {
/* 1707 */         retval = "Improved + Jump Booster";
/*      */       }
/* 1709 */       else if (this.CurMech.GetJumpJets().IsUMU()) {
/* 1710 */         retval = "UMU + Jump Booster";
/*      */       } else {
/* 1712 */         retval = "Standard + Jump Booster";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 1717 */     else if (this.CurMech.GetJumpJets().GetNumJJ() <= 0) {
/* 1718 */       retval = "";
/*      */     }
/* 1720 */     else if (this.CurMech.GetJumpJets().IsImproved()) {
/* 1721 */       retval = "Improved";
/*      */     }
/* 1723 */     else if (this.CurMech.GetJumpJets().IsUMU()) {
/* 1724 */       retval = "UMU";
/*      */     } else {
/* 1726 */       retval = "Standard";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1731 */     return retval;
/*      */   }
/*      */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\filehandlers\HTMLWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */