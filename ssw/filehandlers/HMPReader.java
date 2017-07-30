/*      */ package ssw.filehandlers;
/*      */ 
/*      */ import common.DataFactory;
/*      */ import common.EquipmentFactory;
/*      */ import components.ActuatorSet;
/*      */ import components.HeatSink;
/*      */ import components.HeatSinkFactory;
/*      */ import components.InternalStructure;
/*      */ import components.JumpJet;
/*      */ import components.JumpJetFactory;
/*      */ import components.LocationIndex;
/*      */ import components.Mech;
/*      */ import components.MechArmor;
/*      */ import components.PhysicalEnhancement;
/*      */ import components.RangedWeapon;
/*      */ import components.abPlaceable;
/*      */ import components.ifMechLoadout;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.FileInputStream;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import visitors.ifVisitor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HMPReader
/*      */ {
/*   51 */   private Hashtable<Long, String> Common = new Hashtable();
/*   52 */   private Hashtable<Long, String> Sphere = new Hashtable();
/*   53 */   private Hashtable<Long, String> Clan = new Hashtable();
/*   54 */   private Hashtable<Long, String> Unused = new Hashtable();
/*   55 */   private Vector Errors = new Vector();
/*      */   
/*      */   public HMPReader() {
/*   58 */     BuildHash();
/*      */   }
/*      */   
/*      */   public String GetErrors() {
/*   62 */     String retval = "";
/*   63 */     for (int i = 0; i < this.Errors.size(); i++) {
/*   64 */       retval = retval + ((ErrorReport)this.Errors.get(i)).GetErrorReport() + "\n\n";
/*      */     }
/*   66 */     return retval;
/*      */   }
/*      */   
/*      */   public Mech GetMech(String filename, boolean SuppressOmniNotification) throws Exception {
/*   70 */     this.Errors.clear();
/*   71 */     byte[] buffer = new byte[5];
/*   72 */     int[] Armor = new int[11];
/*      */     
/*      */ 
/*   75 */     DataInputStream FR = new DataInputStream(new FileInputStream(filename));
/*      */     
/*   77 */     Mech m = new Mech();
/*      */     
/*      */ 
/*   80 */     FR.read(buffer);
/*   81 */     readUnsignedByte(FR);
/*   82 */     FR.skipBytes(11);
/*      */     
/*   84 */     m.SetTonnage(readUnsignedShort(FR));
/*      */     
/*   86 */     buffer = new byte[readUnsignedShort(FR)];
/*   87 */     FR.read(buffer);
/*   88 */     m.SetName(new String(buffer));
/*      */     
/*   90 */     buffer = new byte[readUnsignedShort(FR)];
/*   91 */     FR.read(buffer);
/*   92 */     m.SetModel(new String(buffer));
/*      */     
/*      */ 
/*   95 */     m.SetYear(readUnsignedShort(FR), false);
/*      */     
/*   97 */     int Rules = readUnsignedShort(FR);
/*   98 */     if ((Rules < 0) || (Rules > 3)) {
/*   99 */       throw new Exception("Invalid Rules Level: " + Rules);
/*      */     }
/*  101 */     m.SetRulesLevel(DecodeRulesLevel(Rules));
/*      */     
/*      */ 
/*      */ 
/*  105 */     readUnsignedInt(FR);
/*  106 */     FR.skipBytes(22);
/*  107 */     int bf2Length = readUnsignedShort(FR);
/*  108 */     FR.skipBytes(bf2Length);
/*      */     
/*  110 */     int TechBase = readUnsignedShort(FR);
/*  111 */     if ((TechBase < 0) || (TechBase > 2)) {
/*  112 */       throw new Exception("Invalid Tech Base: " + TechBase);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  117 */     boolean OmniMech = false;
/*  118 */     int IntStrucTechBase = TechBase;
/*  119 */     int EngineTechBase = TechBase;
/*  120 */     int HSTechBase = TechBase;
/*  121 */     int PhysicalWeaponTechBase = TechBase;
/*  122 */     int EnhanceTechBase = TechBase;
/*  123 */     int TCTechBase = TechBase;
/*  124 */     int ArmorTechBase = TechBase;
/*  125 */     if (TechBase == 2)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  130 */       IntStrucTechBase = readUnsignedShort(FR);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  135 */       EngineTechBase = readUnsignedShort(FR);
/*  136 */       HSTechBase = readUnsignedShort(FR);
/*  137 */       PhysicalWeaponTechBase = readUnsignedShort(FR);
/*  138 */       EnhanceTechBase = readUnsignedShort(FR);
/*  139 */       TCTechBase = readUnsignedShort(FR);
/*  140 */       ArmorTechBase = readUnsignedShort(FR);
/*      */     }
/*      */     
/*  143 */     int MotiveType = readUnsignedShort(FR);
/*  144 */     switch (MotiveType)
/*      */     {
/*      */     case 0: 
/*  147 */       m.SetBiped();
/*  148 */       break;
/*      */     
/*      */     case 1: 
/*  151 */       m.SetQuad();
/*  152 */       break;
/*      */     
/*      */     case 2: 
/*  155 */       throw new Exception("SSW does not support and cannot load LAMs.");
/*      */     
/*      */     case 3: 
/*  158 */       throw new Exception("SSW does not support and cannot load armless 'Mechs.");
/*      */     
/*      */     case 10: 
/*  161 */       if (!SuppressOmniNotification) {
/*  162 */         this.Errors.add(new ErrorReport("This 'Mech is flagged as an OmniMech but only one loadout will be created."));
/*      */       }
/*  164 */       OmniMech = true;
/*  165 */       m.SetBiped();
/*  166 */       break;
/*      */     
/*      */     case 11: 
/*  169 */       if (!SuppressOmniNotification) {
/*  170 */         this.Errors.add(new ErrorReport("This 'Mech is flagged as an OmniMech but only one loadout will be created."));
/*      */       }
/*  172 */       OmniMech = true;
/*  173 */       m.SetQuad();
/*  174 */       break;
/*      */     case 4: case 5: case 6: case 7: case 8: case 9: default: 
/*  176 */       throw new Exception("Invalid Motive Type: " + MotiveType);
/*      */     }
/*      */     
/*  179 */     switch (TechBase) {
/*      */     case 0: 
/*  181 */       m.SetInnerSphere();
/*  182 */       break;
/*      */     case 1: 
/*  184 */       m.SetClan();
/*  185 */       break;
/*      */     case 2: 
/*  187 */       m.SetMixed();
/*  188 */       break;
/*      */     default: 
/*  190 */       throw new Exception("Could not find a suitable Tech Base.");
/*      */     }
/*      */     
/*      */     
/*      */ 
/*  195 */     if (m.GetYear() < 2443) {
/*  196 */       m.SetEra(0);
/*  197 */     } else if ((m.GetYear() >= 2443) && (m.GetYear() < 2801)) {
/*  198 */       m.SetEra(0);
/*  199 */     } else if ((m.GetYear() >= 2801) && (m.GetYear() < 3051)) {
/*  200 */       m.SetEra(1);
/*  201 */     } else if ((m.GetYear() >= 3051) && (m.GetYear() < 3132)) {
/*  202 */       m.SetEra(2);
/*      */     } else {
/*  204 */       m.SetEra(3);
/*      */     }
/*  206 */     if (m.GetTechbase() == 1) {
/*  207 */       if (m.GetEra() < 1) {
/*  208 */         m.SetEra(1);
/*      */       } else {
/*  210 */         m.SetEra(2);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  215 */     if ((m.GetTechbase() == 2) && 
/*  216 */       (m.GetEra() < 2)) {
/*  217 */       m.SetEra(2);
/*      */     }
/*      */     
/*      */ 
/*  221 */     int IntStrucType = readUnsignedShort(FR);
/*  222 */     String IntStrucLookup = "";
/*  223 */     switch (IntStrucType) {
/*      */     case 0: 
/*  225 */       IntStrucLookup = "Standard Structure";
/*  226 */       break;
/*      */     case 1: 
/*  228 */       IntStrucLookup = BuildLookupName("Endo-Steel", m.GetTechbase(), IntStrucTechBase);
/*  229 */       break;
/*      */     case 2: 
/*  231 */       IntStrucLookup = "Composite Structure";
/*  232 */       break;
/*      */     case 3: 
/*  234 */       IntStrucLookup = "Reinforced Structure";
/*  235 */       break;
/*      */     case 4: 
/*  237 */       IntStrucLookup = "Industrial Structure";
/*  238 */       break;
/*      */     default: 
/*  240 */       throw new Exception("Invalid Internal Structure Type: " + IntStrucType);
/*      */     }
/*      */     
/*  243 */     int EngineRating = readUnsignedShort(FR);
/*  244 */     int EngineType = readUnsignedShort(FR);
/*  245 */     String EngineLookup = "";
/*  246 */     switch (EngineType) {
/*      */     case 0: 
/*  248 */       EngineLookup = "Fusion Engine";
/*  249 */       break;
/*      */     case 1: 
/*  251 */       EngineLookup = BuildLookupName("XL Engine", m.GetTechbase(), EngineTechBase);
/*  252 */       break;
/*      */     case 2: 
/*  254 */       EngineLookup = BuildLookupName("XXL Engine", m.GetTechbase(), EngineTechBase);
/*  255 */       break;
/*      */     case 3: 
/*  257 */       EngineLookup = "Compact Fusion Engine";
/*  258 */       break;
/*      */     case 4: 
/*  260 */       EngineLookup = "I.C.E. Engine";
/*  261 */       break;
/*      */     case 5: 
/*  263 */       EngineLookup = "Light Fusion Engine";
/*  264 */       break;
/*      */     default: 
/*  266 */       throw new Exception("Invalid Engine Type: " + EngineType);
/*      */     }
/*      */     
/*      */     
/*  270 */     readUnsignedShort(FR);
/*  271 */     int NumJJ = readUnsignedShort(FR);
/*      */     
/*  273 */     int NumHS = readUnsignedShort(FR);
/*  274 */     int HSType = readUnsignedShort(FR);
/*  275 */     String HSLookup = "";
/*  276 */     switch (HSType) {
/*      */     case 0: 
/*  278 */       HSLookup = "Single Heat Sink";
/*  279 */       break;
/*      */     case 1: 
/*  281 */       HSLookup = BuildLookupName("Double Heat Sink", m.GetTechbase(), HSTechBase);
/*  282 */       break;
/*      */     case 2: 
/*  284 */       throw new Exception("SSW does not yet support Compact Heat Sinks.");
/*      */     case 3: 
/*  286 */       throw new Exception("SSW does not yet support Laser Heat Sinks.");
/*      */     default: 
/*  288 */       throw new Exception("Invalid Heat Sink Type: " + HSType);
/*      */     }
/*      */     
/*  291 */     String ArmorLookup = "";
/*  292 */     int ArmorType = readUnsignedShort(FR);
/*  293 */     switch (ArmorType) {
/*      */     case 0: 
/*  295 */       ArmorLookup = "Standard Armor";
/*  296 */       break;
/*      */     case 1: 
/*  298 */       ArmorLookup = BuildLookupName("Ferro-Fibrous", m.GetTechbase(), ArmorTechBase);
/*  299 */       break;
/*      */     case 2: 
/*  301 */       ArmorLookup = BuildLookupName("Reactive Armor", m.GetTechbase(), ArmorTechBase);
/*  302 */       break;
/*      */     case 3: 
/*  304 */       ArmorLookup = BuildLookupName("Laser-Reflective", m.GetTechbase(), ArmorTechBase);
/*  305 */       break;
/*      */     case 4: 
/*  307 */       ArmorLookup = "Hardened Armor";
/*  308 */       break;
/*      */     case 5: 
/*  310 */       ArmorLookup = "Light Ferro-Fibrous";
/*  311 */       break;
/*      */     case 6: 
/*  313 */       ArmorLookup = "Heavy Ferro-Fibrous";
/*  314 */       break;
/*      */     case 7: 
/*  316 */       throw new Exception("SSW does not support pre-Tactical Operations Patchwork Armor.");
/*      */     case 8: 
/*  318 */       ArmorLookup = "Stealth Armor";
/*  319 */       break;
/*      */     default: 
/*  321 */       throw new Exception("Invalid Armor Type: " + ArmorType);
/*      */     }
/*      */     
/*  324 */     FR.skipBytes(2);
/*  325 */     Armor[4] = readUnsignedShort(FR);
/*  326 */     FR.skipBytes(4);
/*  327 */     Armor[2] = readUnsignedShort(FR);
/*  328 */     FR.skipBytes(4);
/*  329 */     Armor[6] = readUnsignedShort(FR);
/*  330 */     FR.skipBytes(4);
/*  331 */     Armor[5] = readUnsignedShort(FR);
/*  332 */     FR.skipBytes(4);
/*  333 */     Armor[3] = readUnsignedShort(FR);
/*  334 */     FR.skipBytes(2);
/*      */     
/*      */ 
/*  337 */     int JJType = readUnsignedShort(FR);
/*  338 */     String JJLookup = "";
/*  339 */     switch (JJType) {
/*      */     case 0: 
/*  341 */       JJLookup = "Standard Jump Jet";
/*  342 */       break;
/*      */     case 1: 
/*  344 */       JJLookup = "Improved Jump Jet";
/*  345 */       break;
/*      */     default: 
/*  347 */       throw new Exception("Invalid Jump Jet Type: " + JJType);
/*      */     }
/*      */     
/*      */     
/*  351 */     Armor[7] = readUnsignedShort(FR);
/*  352 */     FR.skipBytes(4);
/*  353 */     Armor[0] = readUnsignedShort(FR);
/*  354 */     FR.skipBytes(4);
/*  355 */     Armor[1] = readUnsignedShort(FR);
/*  356 */     FR.skipBytes(2);
/*  357 */     Armor[9] = readUnsignedShort(FR);
/*  358 */     Armor[10] = readUnsignedShort(FR);
/*  359 */     Armor[8] = readUnsignedShort(FR);
/*      */     
/*  361 */     int EnhanceType = readUnsignedShort(FR);
/*  362 */     String EnhanceLookup = "";
/*  363 */     switch (EnhanceType) {
/*      */     case 0: 
/*  365 */       EnhanceLookup = "No Enhancement";
/*  366 */       break;
/*      */     case 1: 
/*  368 */       EnhanceLookup = "TSM";
/*  369 */       break;
/*      */     case 2: 
/*  371 */       EnhanceLookup = BuildLookupName("MASC", m.GetTechbase(), EnhanceTechBase);
/*  372 */       break;
/*      */     case 3: 
/*  374 */       EnhanceLookup = "Industrial TSM";
/*  375 */       break;
/*      */     default: 
/*  377 */       throw new Exception("Invalid Physical Enhancement Type: " + EnhanceType);
/*      */     }
/*      */     
/*  380 */     int WeaponCount = readUnsignedShort(FR);
/*  381 */     int[][] WeaponArray = new int[WeaponCount][4];
/*  382 */     for (int i = 0; i < WeaponCount; i++) {
/*  383 */       WeaponArray[i][0] = readUnsignedShort(FR);
/*  384 */       WeaponArray[i][1] = readUnsignedShort(FR);
/*  385 */       WeaponArray[i][2] = readUnsignedShort(FR);
/*  386 */       WeaponArray[i][3] = readUnsignedShort(FR);
/*      */       
/*  388 */       FR.skipBytes(2);
/*      */       
/*      */ 
/*  391 */       FR.skipBytes(readUnsignedShort(FR));
/*      */     }
/*      */     
/*      */ 
/*  395 */     long[][] Criticals = new long[8][12];
/*  396 */     for (int i = 0; i < 12; i++) {
/*  397 */       Criticals[4][i] = readUnsignedInt(FR);
/*      */     }
/*  399 */     for (int i = 0; i < 12; i++) {
/*  400 */       Criticals[2][i] = readUnsignedInt(FR);
/*      */     }
/*  402 */     for (int i = 0; i < 12; i++) {
/*  403 */       Criticals[6][i] = readUnsignedInt(FR);
/*      */     }
/*  405 */     for (int i = 0; i < 12; i++) {
/*  406 */       Criticals[5][i] = readUnsignedInt(FR);
/*      */     }
/*  408 */     for (int i = 0; i < 12; i++) {
/*  409 */       Criticals[3][i] = readUnsignedInt(FR);
/*      */     }
/*  411 */     for (int i = 0; i < 12; i++) {
/*  412 */       Criticals[7][i] = readUnsignedInt(FR);
/*      */     }
/*  414 */     for (int i = 0; i < 12; i++) {
/*  415 */       Criticals[0][i] = readUnsignedInt(FR);
/*      */     }
/*  417 */     for (int i = 0; i < 12; i++) {
/*  418 */       Criticals[1][i] = readUnsignedInt(FR);
/*      */     }
/*      */     
/*      */ 
/*  422 */     FR.skipBytes(4);
/*      */     
/*      */ 
/*  425 */     String Fluff = "";
/*      */     
/*  427 */     buffer = new byte[readUnsignedShort(FR)];
/*  428 */     FR.read(buffer);
/*  429 */     Fluff = new String(buffer);
/*  430 */     m.SetOverview(Fluff);
/*      */     
/*  432 */     buffer = new byte[readUnsignedShort(FR)];
/*  433 */     FR.read(buffer);
/*  434 */     Fluff = new String(buffer);
/*  435 */     m.SetCapabilities(Fluff);
/*      */     
/*  437 */     buffer = new byte[readUnsignedShort(FR)];
/*  438 */     FR.read(buffer);
/*  439 */     Fluff = new String(buffer);
/*  440 */     m.SetHistory(Fluff);
/*      */     
/*  442 */     buffer = new byte[readUnsignedShort(FR)];
/*  443 */     FR.read(buffer);
/*  444 */     Fluff = new String(buffer);
/*  445 */     m.SetVariants(Fluff);
/*      */     
/*  447 */     buffer = new byte[readUnsignedShort(FR)];
/*  448 */     FR.read(buffer);
/*  449 */     Fluff = new String(buffer);
/*  450 */     m.SetNotables(Fluff);
/*      */     
/*  452 */     buffer = new byte[readUnsignedShort(FR)];
/*  453 */     FR.read(buffer);
/*  454 */     Fluff = new String(buffer);
/*  455 */     m.SetDeployment(Fluff);
/*      */     
/*      */ 
/*      */ 
/*  459 */     buffer = new byte[readUnsignedShort(FR)];
/*  460 */     FR.read(buffer);
/*  461 */     Fluff = new String(buffer);
/*      */     
/*  463 */     buffer = new byte[readUnsignedShort(FR)];
/*  464 */     FR.read(buffer);
/*  465 */     Fluff = Fluff + "\n" + new String(buffer);
/*  466 */     m.SetAdditional(Fluff);
/*      */     
/*  468 */     FR.skipBytes(8);
/*      */     
/*  470 */     String GyroLookup = "Standard Gyro";
/*  471 */     String CockpitLookup = "Standard Cockpit";
/*  472 */     boolean CommandConsole = false;
/*      */     
/*      */ 
/*  475 */     if (m.GetRulesLevel() > 2) {
/*  476 */       int GyroType = readUnsignedShort(FR);
/*  477 */       int CockpitType = readUnsignedShort(FR);
/*      */       
/*  479 */       switch (GyroType) {
/*      */       case 0: 
/*  481 */         GyroLookup = "Standard Gyro";
/*  482 */         break;
/*      */       case 1: 
/*  484 */         if (m.GetTechbase() == 1)
/*      */         {
/*  486 */           GyroLookup = "Standard Gyro";
/*  487 */           this.Errors.add(new ErrorReport("An XL Gyro was specified for a Clan TechBase, which is not allowed under current rules.\nUse either Mixed or Inner Sphere Tech.  A Standard Gyro was used instead."));
/*      */         } else {
/*  489 */           GyroLookup = "Extra-Light Gyro";
/*      */         }
/*  491 */         break;
/*      */       case 2: 
/*  493 */         if (m.GetTechbase() == 1)
/*      */         {
/*  495 */           GyroLookup = "Standard Gyro";
/*  496 */           this.Errors.add(new ErrorReport("A Compact Gyro was specified for a Clan TechBase, which is not allowed under current rules.\nUse either Mixed or Inner Sphere Tech.  A Standard Gyro was used instead."));
/*      */         } else {
/*  498 */           GyroLookup = "Compact Gyro";
/*      */         }
/*  500 */         break;
/*      */       case 3: 
/*  502 */         if (m.GetTechbase() == 1)
/*      */         {
/*  504 */           GyroLookup = "Standard Gyro";
/*  505 */           this.Errors.add(new ErrorReport("A Heavy-Duty Gyro was specified for a Clan TechBase, which is not allowed under current rules.\nUse either Mixed or Inner Sphere Tech.  A Standard Gyro was used instead."));
/*      */         } else {
/*  507 */           GyroLookup = "Heavy-Duty Gyro";
/*      */         }
/*  509 */         break;
/*      */       default: 
/*  511 */         throw new Exception("A non-standard Gyro type was specified: " + GyroType);
/*      */       }
/*      */       
/*  514 */       switch (CockpitType) {
/*      */       case 0: 
/*  516 */         CockpitLookup = "Standard Cockpit";
/*  517 */         break;
/*      */       case 1: 
/*  519 */         CockpitLookup = "Torso-Mounted Cockpit";
/*  520 */         break;
/*      */       case 2: 
/*  522 */         if (m.GetTechbase() == 1)
/*      */         {
/*  524 */           CockpitLookup = "Standard Cockpit";
/*  525 */           this.Errors.add(new ErrorReport("A Small Cockpit was specified for a Clan TechBase, which is not allowed under Tactical Operations.\nUse either Mixed or Inner Sphere Tech.  A Standard Cockpit was used instead."));
/*      */         } else {
/*  527 */           CockpitLookup = "Small Cockpit";
/*      */         }
/*  529 */         break;
/*      */       case 3: 
/*  531 */         CockpitLookup = "Standard Cockpit";
/*  532 */         CommandConsole = true;
/*  533 */         break;
/*      */       case 4: 
/*  535 */         CockpitLookup = "Standard Cockpit";
/*  536 */         CommandConsole = true;
/*  537 */         this.Errors.add(new ErrorReport("A Dual Cockpit was specified, which is not supported in SSW.\nThe 'Mech has been set to use a Command Console instead."));
/*      */       case 5: 
/*  539 */         CockpitLookup = "Industrial Cockpit";
/*  540 */         break;
/*      */       default: 
/*  542 */         throw new Exception("A non-standard Cockpit type was specified: " + CockpitType);
/*      */       }
/*      */       
/*      */     }
/*      */     
/*  547 */     FR.close();
/*      */     
/*      */ 
/*  550 */     if (Rules == 2) {
/*  551 */       for (int i = 0; i < 8; i++) {
/*  552 */         for (int j = 0; j < 12; j++) {
/*  553 */           if ((Criticals[i][j] == 85L) || (Criticals[i][j] == 113L) || (Criticals[i][j] == 201L)) {
/*  554 */             m.SetRulesLevel(2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  561 */     if (Criticals[4][3] != 4L) {
/*  562 */       m.GetActuators().RemoveLeftHand();
/*      */     }
/*  564 */     if (Criticals[4][2] != 3L) {
/*  565 */       m.GetActuators().RemoveLeftLowerArm();
/*      */     }
/*  567 */     if (Criticals[5][3] != 4L) {
/*  568 */       m.GetActuators().RemoveRightHand();
/*      */     }
/*  570 */     if (Criticals[5][2] != 3L) {
/*  571 */       m.GetActuators().RemoveRightLowerArm();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  576 */     LocationIndex[] Stealth = null;
/*  577 */     if (ArmorLookup.equals("Stealth Armor")) {
/*  578 */       long test = 35L;
/*  579 */       Vector STLocs = new Vector();
/*  580 */       for (int i = 0; i < 8; i++) {
/*  581 */         for (int j = 0; j < 12; j++)
/*      */         {
/*  583 */           if (Criticals[i][j] == test) {
/*  584 */             STLocs.add(new LocationIndex(j, i, 1));
/*  585 */             Criticals[i][j] = 0L;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  590 */       Stealth = new LocationIndex[STLocs.size()];
/*  591 */       for (int i = 0; i < STLocs.size(); i++) {
/*  592 */         Stealth[i] = ((LocationIndex)STLocs.get(i));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  597 */     int LTEngineStart = 0;int RTEngineStart = 0;
/*  598 */     for (int i = 0; i < 12; i++) {
/*  599 */       if (Criticals[2][i] == 15L) {
/*  600 */         LTEngineStart = i;
/*  601 */         break;
/*      */       }
/*      */     }
/*  604 */     for (int i = 0; i < 12; i++) {
/*  605 */       if (Criticals[3][i] == 15L) {
/*  606 */         RTEngineStart = i;
/*  607 */         break;
/*      */       }
/*      */     }
/*  610 */     LocationIndex[] EngineLocs = { new LocationIndex(LTEngineStart, 2, 1), new LocationIndex(RTEngineStart, 3, 1) };
/*      */     
/*      */ 
/*  613 */     ifVisitor v = m.Lookup(IntStrucLookup);
/*  614 */     m.Visit(v);
/*  615 */     v = m.Lookup(EngineLookup);
/*  616 */     v.LoadLocations(EngineLocs);
/*  617 */     m.Visit(v);
/*  618 */     m.SetEngineRating(EngineRating);
/*  619 */     v = m.Lookup(ArmorLookup);
/*  620 */     if (ArmorLookup.equals("Stealth Armor")) {
/*  621 */       v.LoadLocations(Stealth);
/*      */     }
/*  623 */     m.Visit(v);
/*  624 */     v = m.Lookup(GyroLookup);
/*  625 */     m.Visit(v);
/*  626 */     v = m.Lookup(CockpitLookup);
/*  627 */     m.Visit(v);
/*  628 */     m.SetCommandConsole(CommandConsole);
/*  629 */     v = m.Lookup(HSLookup);
/*  630 */     m.Visit(v);
/*  631 */     m.GetLoadout().GetHeatSinks().SetNumHS(NumHS);
/*  632 */     v = m.Lookup(EnhanceLookup);
/*  633 */     m.Visit(v);
/*  634 */     v = m.Lookup(JJLookup);
/*  635 */     m.Visit(v);
/*  636 */     for (int i = 0; i < NumJJ; i++) {
/*  637 */       m.GetLoadout().GetJumpJets().IncrementNumJJ();
/*      */     }
/*      */     
/*      */ 
/*  641 */     m.GetArmor().SetArmor(0, Armor[0]);
/*  642 */     m.GetArmor().SetArmor(1, Armor[1]);
/*  643 */     m.GetArmor().SetArmor(2, Armor[2]);
/*  644 */     m.GetArmor().SetArmor(3, Armor[3]);
/*  645 */     m.GetArmor().SetArmor(4, Armor[4]);
/*  646 */     m.GetArmor().SetArmor(5, Armor[5]);
/*  647 */     m.GetArmor().SetArmor(6, Armor[6]);
/*  648 */     m.GetArmor().SetArmor(7, Armor[7]);
/*  649 */     m.GetArmor().SetArmor(8, Armor[8]);
/*  650 */     m.GetArmor().SetArmor(9, Armor[9]);
/*  651 */     m.GetArmor().SetArmor(10, Armor[10]);
/*      */     
/*      */ 
/*      */ 
/*  655 */     double RoundAV = (m.GetArmor().GetArmorValue() - 1) / (8.0D * m.GetArmor().GetAVMult());
/*  656 */     int mid = (int)Math.floor(RoundAV + 0.9999D);
/*  657 */     RoundAV = mid * 0.5D;
/*  658 */     if (m.GetArmor().GetTonnage() > RoundAV) {
/*  659 */       m.GetArmor().SetArmor(1, m.GetArmor().GetLocationArmor(1) - 1);
/*      */     }
/*      */     
/*      */ 
/*  663 */     if (m.GetIntStruc().NumCrits() > 0)
/*      */     {
/*      */ 
/*  666 */       long test = 20L;
/*  667 */       Vector ISLocs = new Vector();
/*  668 */       for (int i = 0; i < 8; i++) {
/*  669 */         for (int j = 0; j < 12; j++)
/*      */         {
/*  671 */           if (Criticals[i][j] == test) {
/*  672 */             ISLocs.add(new LocationIndex(j, i, 1));
/*  673 */             Criticals[i][j] = 0L;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  678 */       for (int i = 0; i < ISLocs.size(); i++) {
/*  679 */         LocationIndex l = (LocationIndex)ISLocs.get(i);
/*  680 */         m.GetLoadout().AddTo(m.GetIntStruc(), l.Location, l.Index);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  685 */     if (((m.GetArmor().NumCrits() > 0 ? 1 : 0) & (!m.GetArmor().IsStealth() ? 1 : 0)) != 0)
/*      */     {
/*  687 */       long ArmorNum = 0L;
/*  688 */       String test = m.GetArmor().LookupName();
/*  689 */       if (test.equals("Ferro-Fibrous")) {
/*  690 */         ArmorNum = 21L;
/*  691 */       } else if (test.equals("Reactive Armor")) {
/*  692 */         ArmorNum = 28L;
/*  693 */       } else if (test.equals("Laser-Reflective")) {
/*  694 */         ArmorNum = 29L;
/*  695 */       } else if (test.equals("Light Ferro-Fibrous")) {
/*  696 */         ArmorNum = 33L;
/*  697 */       } else if (test.equals("Heavy Ferro-Fibrous")) {
/*  698 */         ArmorNum = 35L;
/*      */       }
/*  700 */       if (ArmorNum == 0L) throw new Exception("An armor type with critical spaces was specified but we can't find where to put them.\nLoading aborted.");
/*  701 */       Vector ARLocs = new Vector();
/*  702 */       for (int i = 0; i < 8; i++) {
/*  703 */         for (int j = 0; j < 12; j++)
/*      */         {
/*  705 */           if (Criticals[i][j] == ArmorNum) {
/*  706 */             ARLocs.add(new LocationIndex(j, i, 1));
/*  707 */             Criticals[i][j] = 0L;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  712 */       for (int i = 0; i < ARLocs.size(); i++) {
/*  713 */         LocationIndex l = (LocationIndex)ARLocs.get(i);
/*  714 */         m.GetLoadout().AddTo(m.GetArmor(), l.Location, l.Index);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  719 */     if (m.GetJumpJets().GetNumJJ() > 0) {
/*  720 */       long test = 11L;
/*  721 */       Vector JJLocs = new Vector();
/*  722 */       boolean improved = m.GetJumpJets().IsImproved();
/*  723 */       for (int i = 0; i < 8; i++) {
/*  724 */         for (int j = 0; j < 12; j++)
/*  725 */           if (Criticals[i][j] == test) {
/*  726 */             JJLocs.add(new LocationIndex(j, i, 1));
/*  727 */             Criticals[i][j] = 0L;
/*  728 */             if (improved) { j++;Criticals[i][j] = 0L;
/*      */             }
/*      */           }
/*      */       }
/*  732 */       JumpJet[] jjList = m.GetJumpJets().GetPlacedJumps();
/*  733 */       if (JJLocs.size() != jjList.length) {
/*  734 */         throw new Exception("The number of jump jets specified does not match the number allocated.\nLoading aborted.");
/*      */       }
/*      */       
/*  737 */       for (int i = 0; i < JJLocs.size(); i++)
/*      */       {
/*  739 */         LocationIndex l = (LocationIndex)JJLocs.get(i);
/*  740 */         m.GetLoadout().AddTo(jjList[i], l.Location, l.Index);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  745 */     if (m.GetHeatSinks().GetPlacedHeatSinks().length > 0) {
/*  746 */       long test = 0L;
/*  747 */       int size = 0;
/*  748 */       if (m.GetHeatSinks().IsDouble()) {
/*  749 */         test = 10L;
/*  750 */         if (m.GetHeatSinks().GetTechBase() == 1) {
/*  751 */           size = 1;
/*      */         } else {
/*  753 */           size = 2;
/*      */         }
/*      */       } else {
/*  756 */         test = 9L;
/*      */       }
/*  758 */       Vector HSLocs = new Vector();
/*  759 */       for (int i = 0; i < 8; i++) {
/*  760 */         for (int j = 0; j < 12; j++) {
/*  761 */           if (Criticals[i][j] == test) {
/*  762 */             HSLocs.add(new LocationIndex(j, i, 1));
/*  763 */             Criticals[i][j] = 0L;
/*  764 */             if (size > 0) {
/*  765 */               Criticals[i][(j + 1)] = 0L;
/*  766 */               if (size > 1) {
/*  767 */                 Criticals[i][(j + 2)] = 0L;
/*      */               }
/*      */             }
/*  770 */             j += size;
/*      */           }
/*      */         }
/*      */       }
/*  774 */       HeatSink[] HSList = m.GetHeatSinks().GetPlacedHeatSinks();
/*  775 */       if (HSLocs.size() != HSList.length) {
/*  776 */         if (OmniMech) {
/*  777 */           throw new Exception("The number of heat sinks outside the engine does not match the number allocated.\nThis is most likely an issue with fixed heat sinks in an OmniMech loadout.\nSSW does not know how many heat sinks are fixed in the base loadout.\nLoading aborted.");
/*      */         }
/*  779 */         throw new Exception("The number of heat sinks outside the engine does not match the number allocated.\nLoading aborted.");
/*      */       }
/*      */       
/*      */ 
/*  783 */       for (int i = 0; i < HSLocs.size(); i++) {
/*  784 */         LocationIndex l = (LocationIndex)HSLocs.get(i);
/*  785 */         m.GetLoadout().AddTo(HSList[i], l.Location, l.Index);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  790 */     if (m.GetPhysEnhance().NumCrits() > 0) {
/*  791 */       long test = 0L;
/*  792 */       if (m.GetPhysEnhance().Contiguous()) {
/*  793 */         test = 23L;
/*  794 */         boolean found = false;
/*  795 */         for (int i = 0; i < 8; i++) {
/*  796 */           for (int j = 0; j < 12; j++) {
/*  797 */             if (Criticals[i][j] == test) {
/*  798 */               m.GetLoadout().AddTo(m.GetPhysEnhance(), i, j);
/*  799 */               for (int x = j; x < m.GetPhysEnhance().NumCrits() + j; x++) {
/*  800 */                 Criticals[i][x] = 0L;
/*      */               }
/*  802 */               found = true;
/*  803 */               break;
/*      */             }
/*      */           }
/*  806 */           if (found)
/*      */             break;
/*      */         }
/*  809 */       } else { test = 22L;
/*  810 */         Vector TSMLocs = new Vector();
/*  811 */         for (int i = 0; i < 8; i++) {
/*  812 */           for (int j = 0; j < 12; j++) {
/*  813 */             if (Criticals[i][j] == test) {
/*  814 */               TSMLocs.add(new LocationIndex(j, i, 1));
/*  815 */               Criticals[i][j] = 0L;
/*      */             }
/*      */           }
/*      */         }
/*  819 */         if (TSMLocs.size() != m.GetPhysEnhance().NumCrits()) {
/*  820 */           throw new Exception("The number of TSM crits specified does not match the number allocated.\nLoading aborted.");
/*      */         }
/*  822 */         for (int i = 0; i < TSMLocs.size(); i++) {
/*  823 */           LocationIndex l = (LocationIndex)TSMLocs.get(i);
/*  824 */           m.GetLoadout().AddTo(m.GetPhysEnhance(), l.Location, l.Index);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  830 */     for (int i = 0; i < 8; i++) {
/*  831 */       for (int j = 0; j < 12; j++) {
/*  832 */         if (Criticals[i][j] == 25L)
/*      */         {
/*  834 */           switch (i) {
/*      */           case 1: 
/*  836 */             m.GetLoadout().SetCTCASE(true, j);
/*  837 */             break;
/*      */           case 2: 
/*  839 */             m.GetLoadout().SetLTCASE(true, j);
/*  840 */             break;
/*      */           case 3: 
/*  842 */             m.GetLoadout().SetRTCASE(true, j);
/*  843 */             break;
/*      */           default: 
/*  845 */             this.Errors.add(new ErrorReport("Inner Sphere CASE was specified for the " + LocationIndex.MechLocs[i] + "\nThis is not allowed and the item will not be added."));
/*      */           }
/*      */           
/*      */         }
/*  849 */         if (Criticals[i][j] == 38L)
/*      */         {
/*  851 */           switch (i) {
/*      */           case 0: 
/*  853 */             m.GetLoadout().SetHDCASEII(true, j, false);
/*  854 */             break;
/*      */           case 1: 
/*  856 */             m.GetLoadout().SetCTCASEII(true, j, false);
/*  857 */             break;
/*      */           case 2: 
/*  859 */             m.GetLoadout().SetLTCASEII(true, j, false);
/*  860 */             break;
/*      */           case 3: 
/*  862 */             m.GetLoadout().SetRTCASEII(true, j, false);
/*  863 */             break;
/*      */           case 4: 
/*  865 */             m.GetLoadout().SetLACASEII(true, j, false);
/*  866 */             break;
/*      */           case 5: 
/*  868 */             m.GetLoadout().SetRACASEII(true, j, false);
/*  869 */             break;
/*      */           case 6: 
/*  871 */             m.GetLoadout().SetLLCASEII(true, j, false);
/*  872 */             break;
/*      */           case 7: 
/*  874 */             m.GetLoadout().SetRLCASEII(true, j, false);
/*  875 */             break;
/*      */           default: 
/*  877 */             this.Errors.add(new ErrorReport("CASE II was specified for an invalid location.\nThe item will not be added."));
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  885 */     int TCLoc = -1;
/*  886 */     int TCIdx = -1;
/*  887 */     boolean HasTC = false;
/*  888 */     for (int i = 0; i < 8; i++) {
/*  889 */       for (int j = 0; j < 8; j++) {
/*  890 */         if (Criticals[i][j] == 18L)
/*      */         {
/*  892 */           TCLoc = i;
/*  893 */           TCIdx = j;
/*  894 */           HasTC = true;
/*  895 */           break;
/*      */         }
/*      */       }
/*  898 */       if (HasTC) {
/*      */         break;
/*      */       }
/*      */     }
/*  902 */     boolean HasFCS = false;
/*  903 */     for (int i = 0; i < 8; i++) {
/*  904 */       for (int j = 0; j < 8; j++) {
/*  905 */         if (Criticals[i][j] == 24L) {
/*  906 */           m.SetFCSArtemisIV(true);
/*  907 */           HasFCS = true;
/*  908 */           break;
/*      */         }
/*      */       }
/*  911 */       if (HasFCS) {
/*      */         break;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  917 */     for (int i = 0; i < 8; i++) {
/*  918 */       for (int j = 0; j < 12; j++) {
/*  919 */         if (((Criticals[i][j] > 0L) && (Criticals[i][j] < 17L)) || (Criticals[i][j] == 18L) || (Criticals[i][j] == 24L) || (Criticals[i][j] == 25L) || (Criticals[i][j] == 38L)) {
/*  920 */           Criticals[i][j] = 0L;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  927 */     Hashtable Other = null;
/*  928 */     if (IntStrucTechBase == 1) {
/*  929 */       Other = this.Clan;
/*      */     } else {
/*  931 */       Other = this.Sphere;
/*      */     }
/*      */     
/*      */ 
/*  935 */     DataFactory df = new DataFactory(m);
/*  936 */     for (int i = 0; i < 8; i++) {
/*  937 */       for (int j = 0; j < 12; j++) {
/*  938 */         if (Criticals[i][j] != 0L)
/*      */         {
/*  940 */           Long lookup = GetLookupNum(Criticals[i][j]);
/*  941 */           boolean rear = IsRearMounted(Criticals[i][j]);
/*  942 */           boolean found = true;
/*      */           
/*      */ 
/*      */ 
/*  946 */           String Name = (String)this.Common.get(lookup);
/*  947 */           if (Name == null) {
/*  948 */             Name = (String)Other.get(lookup);
/*  949 */             if (Name == null) {
/*  950 */               found = false;
/*      */             }
/*      */           }
/*      */           
/*  954 */           if (found)
/*      */           {
/*  956 */             if ((m.UsingArtemisIV()) && (
/*  957 */               (Name.contains("@ LRM")) || (Name.contains("@ SRM")))) { Name = Name + " (Artemis IV Capable)";
/*      */             }
/*  959 */             abPlaceable neweq = df.GetEquipment().GetByName(Name, m);
/*  960 */             if (neweq != null)
/*      */             {
/*  962 */               if (neweq.CanSplit())
/*      */               {
/*  964 */                 int S1Index = j;
/*  965 */                 int S1Num = 0;
/*  966 */                 for (int k = j; k < 12; k++) {
/*  967 */                   if ((Criticals[i][k] & 0xFFFF) == (0xFFFF & lookup.longValue())) { S1Num++;
/*      */                   }
/*      */                 }
/*  970 */                 if (S1Num == neweq.NumCrits())
/*      */                 {
/*  972 */                   m.GetLoadout().AddToQueue(neweq);
/*  973 */                   m.GetLoadout().AddTo(neweq, i, S1Index);
/*  974 */                   Criticals[i][j] = 0L;
/*  975 */                   if (neweq.NumCrits() > 1)
/*      */                   {
/*      */ 
/*  978 */                     for (int k = 1; k < neweq.NumCrits(); k++) {
/*  979 */                       Criticals[i][(j + k)] = 0L;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 else {
/*  984 */                   int SecondLoc = -1;
/*  985 */                   int ThirdLoc = -1;
/*  986 */                   int FourthLoc = -1;
/*  987 */                   switch (i) {
/*      */                   case 1: 
/*  989 */                     SecondLoc = 2;
/*  990 */                     ThirdLoc = 3;
/*  991 */                     break;
/*      */                   case 2: 
/*  993 */                     SecondLoc = 4;
/*  994 */                     ThirdLoc = 1;
/*  995 */                     FourthLoc = 6;
/*  996 */                     break;
/*      */                   case 3: 
/*  998 */                     SecondLoc = 5;
/*  999 */                     ThirdLoc = 1;
/* 1000 */                     FourthLoc = 7;
/* 1001 */                     break;
/*      */                   case 4: 
/* 1003 */                     SecondLoc = 2;
/* 1004 */                     break;
/*      */                   case 5: 
/* 1006 */                     SecondLoc = 3;
/* 1007 */                     break;
/*      */                   case 6: 
/* 1009 */                     SecondLoc = 2;
/* 1010 */                     break;
/*      */                   case 7: 
/* 1012 */                     SecondLoc = 3;
/*      */                   }
/*      */                   
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 1019 */                   int S2Num = 0;
/* 1020 */                   int S2Index = -1;
/* 1021 */                   int S2Loc = -1;
/* 1022 */                   int NumLeft = neweq.NumCrits() - S1Num;
/* 1023 */                   if (SecondLoc > 0) {
/* 1024 */                     if (ThirdLoc > 0) {
/* 1025 */                       if (FourthLoc > 0)
/*      */                       {
/* 1027 */                         for (int k = 0; k < 12; k++) {
/* 1028 */                           if (S2Index < 0) {
/* 1029 */                             if ((Criticals[SecondLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1030 */                               S2Num++;
/* 1031 */                               S2Index = k;
/* 1032 */                               S2Loc = SecondLoc;
/*      */                             }
/*      */                           }
/* 1035 */                           else if ((Criticals[SecondLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1036 */                             S2Num++;
/*      */                           }
/*      */                         }
/*      */                         
/* 1040 */                         for (int k = 0; k < 12; k++) {
/* 1041 */                           if (S2Index < 0) {
/* 1042 */                             if ((Criticals[ThirdLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1043 */                               S2Num++;
/* 1044 */                               S2Index = k;
/* 1045 */                               S2Loc = ThirdLoc;
/*      */                             }
/*      */                           }
/* 1048 */                           else if ((Criticals[ThirdLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1049 */                             S2Num++;
/*      */                           }
/*      */                         }
/*      */                         
/* 1053 */                         for (int k = 0; k < 12; k++) {
/* 1054 */                           if (S2Index < 0) {
/* 1055 */                             if ((Criticals[FourthLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1056 */                               S2Num++;
/* 1057 */                               S2Index = k;
/* 1058 */                               S2Loc = FourthLoc;
/*      */                             }
/*      */                           }
/* 1061 */                           else if ((Criticals[FourthLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1062 */                             S2Num++;
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       else
/*      */                       {
/* 1068 */                         for (int k = 0; k < 12; k++) {
/* 1069 */                           if (S2Index < 0) {
/* 1070 */                             if ((Criticals[SecondLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1071 */                               S2Num++;
/* 1072 */                               S2Index = k;
/* 1073 */                               S2Loc = SecondLoc;
/*      */                             }
/*      */                           }
/* 1076 */                           else if ((Criticals[SecondLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1077 */                             S2Num++;
/*      */                           }
/*      */                         }
/*      */                         
/* 1081 */                         for (int k = 0; k < 12; k++) {
/* 1082 */                           if (S2Index < 0) {
/* 1083 */                             if ((Criticals[ThirdLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1084 */                               S2Num++;
/* 1085 */                               S2Index = k;
/* 1086 */                               S2Loc = ThirdLoc;
/*      */                             }
/*      */                           }
/* 1089 */                           else if ((Criticals[ThirdLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1090 */                             S2Num++;
/*      */                           }
/*      */                           
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     else {
/* 1097 */                       for (int k = 0; k < 12; k++) {
/* 1098 */                         if (S2Index < 0) {
/* 1099 */                           if ((Criticals[SecondLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1100 */                             S2Num++;
/* 1101 */                             S2Index = k;
/* 1102 */                             S2Loc = SecondLoc;
/*      */                           }
/*      */                         }
/* 1105 */                         else if ((Criticals[SecondLoc][k] & 0xFFFF) == (lookup.longValue() & 0xFFFF)) {
/* 1106 */                           S2Num++;
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     
/* 1111 */                     if (S2Num < NumLeft)
/*      */                     {
/* 1113 */                       this.Errors.add(new ErrorReport(Name + "\nHas too many crits to fit in the " + LocationIndex.MechLocs[i] + "\n and we could not find another location for it.\nAdd and place the item normally."));
/*      */                     } else {
/* 1115 */                       if (S2Num > NumLeft)
/*      */                       {
/* 1117 */                         S2Num = NumLeft;
/*      */                       }
/*      */                       
/* 1120 */                       m.GetLoadout().AddToQueue(neweq);
/* 1121 */                       m.GetLoadout().RemoveFromQueue(neweq);
/* 1122 */                       m.GetLoadout().AddTo(m.GetLoadout().GetCrits(i), neweq, S1Index, S1Num);
/* 1123 */                       m.GetLoadout().AddTo(m.GetLoadout().GetCrits(S2Loc), neweq, S2Index, S2Num);
/* 1124 */                       if (rear) {
/* 1125 */                         neweq.MountRear(rear);
/*      */                       }
/*      */                       
/* 1128 */                       for (int k = S1Index; k < S1Index + S1Num; k++) {
/* 1129 */                         Criticals[i][k] = 0L;
/*      */                       }
/* 1131 */                       for (int k = S2Index; k < S2Index + S2Num; k++) {
/* 1132 */                         Criticals[S2Loc][k] = 0L;
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */               else {
/* 1139 */                 int size = neweq.NumCrits();
/* 1140 */                 if ((m.UsingArtemisIV()) && ((neweq instanceof RangedWeapon)) && 
/* 1141 */                   (m.UsingArtemisIV())) { ((RangedWeapon)neweq).UseFCS(true, 1);
/*      */                 }
/* 1143 */                 m.GetLoadout().AddToQueue(neweq);
/* 1144 */                 m.GetLoadout().AddTo(neweq, i, j);
/* 1145 */                 if (rear) {
/* 1146 */                   neweq.MountRear(rear);
/*      */                 }
/* 1148 */                 Criticals[i][j] = 0L;
/* 1149 */                 if (size > 1)
/*      */                 {
/*      */ 
/* 1152 */                   for (int k = 1; k < size; k++) {
/* 1153 */                     Criticals[i][(j + k)] = 0L;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             } else {
/* 1158 */               this.Errors.add(new ErrorReport(lookup, Name));
/*      */             }
/*      */           } else {
/* 1161 */             String name = (String)this.Unused.get(lookup);
/* 1162 */             if (name == null) {
/* 1163 */               this.Errors.add(new ErrorReport("The equipment specified by HMP_REF: " + lookup + " could not be found.\nSkipping that item."));
/*      */             } else {
/* 1165 */               this.Errors.add(new ErrorReport(name + "\nis unused by SSW or is not a valid piece of equipment.\nSkipping that item."));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1173 */     if (HasTC) {
/* 1174 */       boolean TCTB = false;
/* 1175 */       if (TCTechBase == 1) TCTB = true;
/* 1176 */       m.GetLoadout().UseTC(true, TCTB);
/* 1177 */       m.GetLoadout().AddTo(m.GetLoadout().GetTC(), TCLoc, TCIdx);
/*      */     }
/*      */     
/* 1180 */     return m;
/*      */   }
/*      */   
/*      */   private void BuildHash() {
/* 1184 */     this.Unused.put(new Long(37L), "IS2 Compact Heat Sinks");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1196 */     this.Common.put(new Long(32L), "Supercharger");
/* 1197 */     this.Common.put(new Long(39L), "Null Signature System");
/* 1198 */     this.Common.put(new Long(40L), "Coolant Pod");
/*      */     
/*      */ 
/* 1201 */     this.Common.put(new Long(248L), "Combine");
/* 1202 */     this.Common.put(new Long(249L), "Lift Hoist");
/* 1203 */     this.Common.put(new Long(250L), "Chainsaw");
/* 1204 */     this.Common.put(new Long(304L), "Backhoe");
/* 1205 */     this.Common.put(new Long(305L), "Drill");
/* 1206 */     this.Common.put(new Long(306L), "Rock Cutter");
/*      */     
/*      */ 
/* 1209 */     this.Sphere.put(new Long(17L), "Hatchet");
/* 1210 */     this.Sphere.put(new Long(31L), "Sword");
/*      */     
/*      */ 
/* 1213 */     this.Sphere.put(new Long(54L), "(IS) Laser Anti-Missile System");
/* 1214 */     this.Sphere.put(new Long(175L), "(CL) Laser Anti-Missile System");
/* 1215 */     this.Sphere.put(new Long(66L), "(IS) Anti-Missile System");
/* 1216 */     this.Sphere.put(new Long(180L), "(CL) Anti-Missile System");
/* 1217 */     this.Sphere.put(new Long(92L), "A-Pod");
/* 1218 */     this.Sphere.put(new Long(202L), "A-Pod");
/* 1219 */     this.Sphere.put(new Long(100L), "Light Active Probe");
/* 1220 */     this.Sphere.put(new Long(115L), "Beagle Active Probe");
/* 1221 */     this.Sphere.put(new Long(116L), "Bloodhound Active Probe");
/* 1222 */     this.Sphere.put(new Long(203L), "Active Probe");
/* 1223 */     this.Sphere.put(new Long(114L), "Angel ECM");
/* 1224 */     this.Sphere.put(new Long(179L), "Angel ECM");
/* 1225 */     this.Sphere.put(new Long(120L), "Guardian ECM Suite");
/* 1226 */     this.Sphere.put(new Long(204L), "ECM Suite");
/* 1227 */     this.Sphere.put(new Long(122L), "TAG");
/* 1228 */     this.Sphere.put(new Long(206L), "TAG");
/* 1229 */     this.Sphere.put(new Long(101L), "Light TAG");
/* 1230 */     this.Sphere.put(new Long(117L), "C3 Computer (Master)");
/* 1231 */     this.Sphere.put(new Long(118L), "C3 Computer (Slave)");
/* 1232 */     this.Sphere.put(new Long(119L), "Improved C3 Computer");
/* 1233 */     this.Clan.put(new Long(134L), "(IS) Laser Anti-Missile System");
/* 1234 */     this.Clan.put(new Long(59L), "(CL) Laser Anti-Missile System");
/* 1235 */     this.Clan.put(new Long(146L), "(IS) Anti-Missile System");
/* 1236 */     this.Clan.put(new Long(64L), "(CL) Anti-Missile System");
/* 1237 */     this.Clan.put(new Long(172L), "A-Pod");
/* 1238 */     this.Clan.put(new Long(86L), "A-Pod");
/* 1239 */     this.Clan.put(new Long(87L), "Active Probe");
/* 1240 */     this.Clan.put(new Long(175L), "Light Active Probe");
/* 1241 */     this.Clan.put(new Long(195L), "Beagle Active Probe");
/* 1242 */     this.Clan.put(new Long(196L), "Bloodhound Active Probe");
/* 1243 */     this.Clan.put(new Long(194L), "Angel ECM");
/* 1244 */     this.Clan.put(new Long(63L), "Angel ECM");
/* 1245 */     this.Clan.put(new Long(200L), "Guardian ECM Suite");
/* 1246 */     this.Clan.put(new Long(88L), "ECM Suite");
/* 1247 */     this.Clan.put(new Long(202L), "TAG");
/* 1248 */     this.Clan.put(new Long(90L), "TAG");
/* 1249 */     this.Clan.put(new Long(180L), "Light TAG");
/* 1250 */     this.Clan.put(new Long(197L), "C3 Computer (Master)");
/* 1251 */     this.Clan.put(new Long(198L), "C3 Computer (Slave)");
/* 1252 */     this.Clan.put(new Long(199L), "Improved C3 Computer");
/*      */     
/*      */ 
/* 1255 */     this.Unused.put(new Long(296L), "Clan Plasma Rifle");
/* 1256 */     this.Sphere.put(new Long(51L), "(IS) ER Large Laser");
/* 1257 */     this.Sphere.put(new Long(52L), "(IS) ER PPC");
/* 1258 */     this.Sphere.put(new Long(53L), "(IS) Flamer");
/* 1259 */     this.Sphere.put(new Long(55L), "(IS) Large Laser");
/* 1260 */     this.Sphere.put(new Long(56L), "(IS) Medium Laser");
/* 1261 */     this.Sphere.put(new Long(57L), "(IS) Small Laser");
/* 1262 */     this.Sphere.put(new Long(58L), "(IS) PPC");
/* 1263 */     this.Sphere.put(new Long(59L), "(IS) Large Pulse Laser");
/* 1264 */     this.Sphere.put(new Long(60L), "(IS) Medium Pulse Laser");
/* 1265 */     this.Sphere.put(new Long(61L), "(IS) Small Pulse Laser");
/* 1266 */     this.Sphere.put(new Long(72L), "(IS) Large X-Pulse Laser");
/* 1267 */     this.Sphere.put(new Long(73L), "(IS) Medium X-Pulse Laser");
/* 1268 */     this.Sphere.put(new Long(74L), "(IS) Small X-Pulse Laser");
/* 1269 */     this.Sphere.put(new Long(82L), "Heavy Flamer");
/*      */     
/* 1271 */     this.Sphere.put(new Long(88L), "(CL) ER Micro Laser");
/*      */     
/* 1273 */     this.Sphere.put(new Long(90L), "(IS) ER Medium Laser");
/* 1274 */     this.Sphere.put(new Long(91L), "(IS) ER Small Laser");
/* 1275 */     this.Sphere.put(new Long(133L), "Vehicle Flamer");
/* 1276 */     this.Sphere.put(new Long(167L), "(CL) ER Large Laser");
/* 1277 */     this.Sphere.put(new Long(168L), "(CL) ER Medium Laser");
/* 1278 */     this.Sphere.put(new Long(169L), "(CL) ER Small Laser");
/* 1279 */     this.Sphere.put(new Long(170L), "(CL) ER PPC");
/* 1280 */     this.Sphere.put(new Long(171L), "(CL) Flamer");
/* 1281 */     this.Sphere.put(new Long(176L), "(CL) Large Pulse Laser");
/* 1282 */     this.Sphere.put(new Long(177L), "(CL) Medium Pulse Laser");
/* 1283 */     this.Sphere.put(new Long(178L), "(CL) Small Pulse Laser");
/* 1284 */     this.Sphere.put(new Long(244L), "(CL) Heavy Large Laser");
/* 1285 */     this.Sphere.put(new Long(245L), "(CL) Heavy Medium Laser");
/* 1286 */     this.Sphere.put(new Long(246L), "(CL) Heavy Small Laser");
/* 1287 */     this.Sphere.put(new Long(218L), "Vehicle Flamer");
/* 1288 */     this.Clan.put(new Long(51L), "(CL) ER Large Laser");
/* 1289 */     this.Clan.put(new Long(52L), "(CL) ER Medium Laser");
/* 1290 */     this.Clan.put(new Long(53L), "(CL) ER Small Laser");
/* 1291 */     this.Clan.put(new Long(54L), "(CL) ER PPC");
/* 1292 */     this.Clan.put(new Long(55L), "(CL) Flamer");
/* 1293 */     this.Clan.put(new Long(56L), "(CL) ER Large Pulse Laser");
/* 1294 */     this.Clan.put(new Long(57L), "(CL) ER Medium Pulse Laser");
/* 1295 */     this.Clan.put(new Long(58L), "(CL) ER Small Pulse Laser");
/* 1296 */     this.Clan.put(new Long(60L), "(CL) Large Pulse Laser");
/* 1297 */     this.Clan.put(new Long(61L), "(CL) Medium Pulse Laser");
/* 1298 */     this.Clan.put(new Long(62L), "(CL) Small Pulse Laser");
/* 1299 */     this.Clan.put(new Long(91L), "(CL) ER Micro Laser");
/* 1300 */     this.Clan.put(new Long(102L), "Vehicle Flamer");
/* 1301 */     this.Clan.put(new Long(128L), "(CL) Heavy Large Laser");
/* 1302 */     this.Clan.put(new Long(129L), "(CL) Heavy Medium Laser");
/* 1303 */     this.Clan.put(new Long(130L), "(CL) Heavy Small Laser");
/* 1304 */     this.Clan.put(new Long(131L), "(IS) ER Large Laser");
/* 1305 */     this.Clan.put(new Long(132L), "(IS) ER PPC");
/* 1306 */     this.Clan.put(new Long(133L), "(IS) Flamer");
/* 1307 */     this.Clan.put(new Long(135L), "(IS) Large Laser");
/* 1308 */     this.Clan.put(new Long(136L), "(IS) Medium Laser");
/* 1309 */     this.Clan.put(new Long(137L), "(IS) Small Laser");
/* 1310 */     this.Clan.put(new Long(138L), "(IS) PPC");
/* 1311 */     this.Clan.put(new Long(139L), "(IS) Large Pulse Laser");
/* 1312 */     this.Clan.put(new Long(140L), "(IS) Medium Pulse Laser");
/* 1313 */     this.Clan.put(new Long(141L), "(IS) Small Pulse Laser");
/* 1314 */     this.Clan.put(new Long(152L), "(IS) Large X-Pulse Laser");
/* 1315 */     this.Clan.put(new Long(153L), "(IS) Medium X-Pulse Laser");
/* 1316 */     this.Clan.put(new Long(154L), "(IS) Small X-Pulse Laser");
/*      */     
/* 1318 */     this.Clan.put(new Long(168L), "(CL) Micro Pulse Laser");
/*      */     
/* 1320 */     this.Clan.put(new Long(170L), "(IS) ER Medium Laser");
/* 1321 */     this.Clan.put(new Long(171L), "(IS) ER Small Laser");
/* 1322 */     this.Clan.put(new Long(213L), "Vehicle Flamer");
/*      */     
/*      */ 
/* 1325 */     this.Common.put(new Long(289L), "(IS) Rotary AC/2");
/* 1326 */     this.Common.put(new Long(290L), "(IS) Rotary AC/5");
/* 1327 */     this.Common.put(new Long(292L), "(CL) Rotary AC/2");
/* 1328 */     this.Common.put(new Long(293L), "(CL) Rotary AC/5");
/* 1329 */     this.Sphere.put(new Long(62L), "(IS) Autocannon/2");
/* 1330 */     this.Sphere.put(new Long(63L), "(IS) Autocannon/5");
/* 1331 */     this.Sphere.put(new Long(64L), "(IS) Autocannon/10");
/* 1332 */     this.Sphere.put(new Long(65L), "(IS) Autocannon/20");
/* 1333 */     this.Sphere.put(new Long(67L), "Long Tom Artillery Cannon");
/* 1334 */     this.Sphere.put(new Long(68L), "Sniper Artillery Cannon");
/* 1335 */     this.Sphere.put(new Long(69L), "Thumper Artillery Cannon");
/* 1336 */     this.Sphere.put(new Long(70L), "(IS) Light Gauss Rifle");
/* 1337 */     this.Sphere.put(new Long(71L), "(IS) Gauss Rifle");
/* 1338 */     this.Sphere.put(new Long(75L), "(IS) LB 2-X AC");
/* 1339 */     this.Sphere.put(new Long(76L), "(IS) LB 5-X AC");
/* 1340 */     this.Sphere.put(new Long(77L), "(IS) LB 10-X AC");
/* 1341 */     this.Sphere.put(new Long(78L), "(IS) LB 20-X AC");
/* 1342 */     this.Sphere.put(new Long(79L), "(IS) Machine Gun");
/* 1343 */     this.Sphere.put(new Long(80L), "(IS) Light AC/2");
/* 1344 */     this.Sphere.put(new Long(81L), "(IS) Light AC/5");
/* 1345 */     this.Sphere.put(new Long(84L), "(IS) Ultra AC/2");
/* 1346 */     this.Sphere.put(new Long(85L), "(IS) Ultra AC/5");
/* 1347 */     this.Sphere.put(new Long(86L), "(IS) Ultra AC/10");
/* 1348 */     this.Sphere.put(new Long(87L), "(IS) Ultra AC/20");
/* 1349 */     this.Sphere.put(new Long(94L), "(CL) Light Machine Gun");
/* 1350 */     this.Sphere.put(new Long(95L), "(CL) Heavy Machine Gun");
/* 1351 */     this.Sphere.put(new Long(181L), "(CL) Gauss Rifle");
/* 1352 */     this.Sphere.put(new Long(182L), "(CL) LB 2-X AC");
/* 1353 */     this.Sphere.put(new Long(183L), "(CL) LB 5-X AC");
/* 1354 */     this.Sphere.put(new Long(184L), "(CL) LB 10-X AC");
/* 1355 */     this.Sphere.put(new Long(185L), "(CL) LB 20-X AC");
/* 1356 */     this.Sphere.put(new Long(186L), "(CL) Machine Gun");
/* 1357 */     this.Sphere.put(new Long(187L), "(CL) Ultra AC/2");
/* 1358 */     this.Sphere.put(new Long(188L), "(CL) Ultra AC/5");
/* 1359 */     this.Sphere.put(new Long(189L), "(CL) Ultra AC/10");
/* 1360 */     this.Sphere.put(new Long(190L), "(CL) Ultra AC/20");
/* 1361 */     this.Sphere.put(new Long(291L), "(IS) Heavy Gauss Rifle");
/* 1362 */     this.Clan.put(new Long(65L), "(CL) Gauss Rifle");
/* 1363 */     this.Clan.put(new Long(66L), "(CL) LB 2-X AC");
/* 1364 */     this.Clan.put(new Long(67L), "(CL) LB 5-X AC");
/* 1365 */     this.Clan.put(new Long(68L), "(CL) LB 10-X AC");
/* 1366 */     this.Clan.put(new Long(69L), "(CL) LB 20-X AC");
/* 1367 */     this.Clan.put(new Long(70L), "(CL) Machine Gun");
/* 1368 */     this.Clan.put(new Long(71L), "(CL) Ultra AC/2");
/* 1369 */     this.Clan.put(new Long(72L), "(CL) Ultra AC/5");
/* 1370 */     this.Clan.put(new Long(73L), "(CL) Ultra AC/10");
/* 1371 */     this.Clan.put(new Long(74L), "(CL) Ultra AC/20");
/* 1372 */     this.Clan.put(new Long(142L), "(IS) Autocannon/2");
/* 1373 */     this.Clan.put(new Long(143L), "(IS) Autocannon/5");
/* 1374 */     this.Clan.put(new Long(144L), "(IS) Autocannon/10");
/* 1375 */     this.Clan.put(new Long(145L), "(IS) Autocannon/20");
/* 1376 */     this.Clan.put(new Long(150L), "(IS) Light Gauss Rifle");
/* 1377 */     this.Clan.put(new Long(151L), "(IS) Gauss Rifle");
/* 1378 */     this.Clan.put(new Long(155L), "(IS) LB 2-X AC");
/* 1379 */     this.Clan.put(new Long(156L), "(IS) LB 5-X AC");
/* 1380 */     this.Clan.put(new Long(157L), "(IS) LB 10-X AC");
/* 1381 */     this.Clan.put(new Long(158L), "(IS) LB 20-X AC");
/* 1382 */     this.Clan.put(new Long(159L), "(IS) Machine Gun");
/* 1383 */     this.Clan.put(new Long(160L), "(IS) Light AC/2");
/* 1384 */     this.Clan.put(new Long(161L), "(IS) Light AC/5");
/* 1385 */     this.Clan.put(new Long(164L), "(IS) Ultra AC/2");
/* 1386 */     this.Clan.put(new Long(165L), "(IS) Ultra AC/5");
/* 1387 */     this.Clan.put(new Long(166L), "(IS) Ultra AC/10");
/* 1388 */     this.Clan.put(new Long(167L), "(IS) Ultra AC/20");
/* 1389 */     this.Clan.put(new Long(173L), "(CL) Light Machine Gun");
/* 1390 */     this.Clan.put(new Long(174L), "(CL) Heavy Machine Gun");
/* 1391 */     this.Clan.put(new Long(147L), "Long Tom Artillery Cannon");
/* 1392 */     this.Clan.put(new Long(148L), "Sniper Artillery Cannon");
/* 1393 */     this.Clan.put(new Long(149L), "Thumper Artillery Cannon");
/*      */     
/*      */ 
/* 1396 */     this.Common.put(new Long(252L), "(CL) ATM-3");
/* 1397 */     this.Common.put(new Long(253L), "(CL) ATM-6");
/* 1398 */     this.Common.put(new Long(254L), "(CL) ATM-9");
/* 1399 */     this.Common.put(new Long(255L), "(CL) ATM-12");
/* 1400 */     this.Common.put(new Long(297L), "(IS) Rocket Launcher 10");
/* 1401 */     this.Common.put(new Long(298L), "(IS) Rocket Launcher 15");
/* 1402 */     this.Common.put(new Long(299L), "(IS) Rocket Launcher 20");
/* 1403 */     this.Sphere.put(new Long(96L), "(IS) LRM-5");
/* 1404 */     this.Sphere.put(new Long(97L), "(IS) LRM-10");
/* 1405 */     this.Sphere.put(new Long(98L), "(IS) LRM-15");
/* 1406 */     this.Sphere.put(new Long(99L), "(IS) LRM-20");
/* 1407 */     this.Sphere.put(new Long(103L), "(IS) SRM-2");
/* 1408 */     this.Sphere.put(new Long(104L), "(IS) SRM-4");
/* 1409 */     this.Sphere.put(new Long(105L), "(IS) SRM-6");
/* 1410 */     this.Sphere.put(new Long(106L), "(IS) Streak SRM-2");
/* 1411 */     this.Sphere.put(new Long(107L), "(IS) Streak SRM-4");
/* 1412 */     this.Sphere.put(new Long(108L), "(IS) Streak SRM-6");
/* 1413 */     this.Sphere.put(new Long(137L), "(IS) MRM-10");
/* 1414 */     this.Sphere.put(new Long(138L), "(IS) MRM-20");
/* 1415 */     this.Sphere.put(new Long(139L), "(IS) MRM-30");
/* 1416 */     this.Sphere.put(new Long(140L), "(IS) MRM-40");
/* 1417 */     this.Sphere.put(new Long(109L), "(IS) Thunderbolt-5");
/* 1418 */     this.Sphere.put(new Long(110L), "(IS) Thunderbolt-10");
/* 1419 */     this.Sphere.put(new Long(111L), "(IS) Thunderbolt-15");
/* 1420 */     this.Sphere.put(new Long(112L), "(IS) Thunderbolt-20");
/* 1421 */     this.Sphere.put(new Long(102L), "(IS) iNarc Launcher");
/* 1422 */     this.Sphere.put(new Long(121L), "(IS) Narc Missile Beacon");
/* 1423 */     this.Sphere.put(new Long(146L), "(IS) LRT-5");
/* 1424 */     this.Sphere.put(new Long(147L), "(IS) LRT-10");
/* 1425 */     this.Sphere.put(new Long(148L), "(IS) LRT-15");
/* 1426 */     this.Sphere.put(new Long(149L), "(IS) LRT-20");
/* 1427 */     this.Sphere.put(new Long(150L), "(IS) SRT-2");
/* 1428 */     this.Sphere.put(new Long(151L), "(IS) SRT-4");
/* 1429 */     this.Sphere.put(new Long(152L), "(IS) SRT-6");
/* 1430 */     this.Sphere.put(new Long(123L), "(IS) LRM-5 (OS)");
/* 1431 */     this.Sphere.put(new Long(124L), "(IS) LRM-10 (OS)");
/* 1432 */     this.Sphere.put(new Long(125L), "(IS) LRM-15 (OS)");
/* 1433 */     this.Sphere.put(new Long(126L), "(IS) LRM-20 (OS)");
/* 1434 */     this.Sphere.put(new Long(127L), "(IS) SRM-2 (OS)");
/* 1435 */     this.Sphere.put(new Long(128L), "(IS) SRM-4 (OS)");
/* 1436 */     this.Sphere.put(new Long(129L), "(IS) SRM-6 (OS)");
/* 1437 */     this.Sphere.put(new Long(130L), "(IS) Streak SRM-2 (OS)");
/* 1438 */     this.Sphere.put(new Long(131L), "(IS) Streak SRM-4 (OS)");
/* 1439 */     this.Sphere.put(new Long(132L), "(IS) Streak SRM-6 (OS)");
/* 1440 */     this.Sphere.put(new Long(142L), "(IS) MRM-10 (OS)");
/* 1441 */     this.Sphere.put(new Long(143L), "(IS) MRM-20 (OS)");
/* 1442 */     this.Sphere.put(new Long(144L), "(IS) MRM-30 (OS)");
/* 1443 */     this.Sphere.put(new Long(145L), "(IS) MRM-40 (OS)");
/* 1444 */     this.Sphere.put(new Long(153L), "(IS) LRM-5 (iOS)");
/* 1445 */     this.Sphere.put(new Long(154L), "(IS) LRM-10 (iOS)");
/* 1446 */     this.Sphere.put(new Long(155L), "(IS) LRM-15 (iOS)");
/* 1447 */     this.Sphere.put(new Long(156L), "(IS) LRM-20 (iOS)");
/* 1448 */     this.Sphere.put(new Long(157L), "(IS) SRM-2 (iOS)");
/* 1449 */     this.Sphere.put(new Long(158L), "(IS) SRM-4 (iOS)");
/* 1450 */     this.Sphere.put(new Long(159L), "(IS) SRM-6 (iOS)");
/* 1451 */     this.Sphere.put(new Long(160L), "(IS) Streak SRM-2 (iOS)");
/* 1452 */     this.Sphere.put(new Long(161L), "(IS) Streak SRM-4 (iOS)");
/* 1453 */     this.Sphere.put(new Long(162L), "(IS) Streak SRM-6 (iOS)");
/* 1454 */     this.Sphere.put(new Long(163L), "(IS) MRM-10 (iOS)");
/* 1455 */     this.Sphere.put(new Long(164L), "(IS) MRM-20 (iOS)");
/* 1456 */     this.Sphere.put(new Long(165L), "(IS) MRM-30 (iOS)");
/* 1457 */     this.Sphere.put(new Long(166L), "(IS) MRM-40 (iOS)");
/* 1458 */     this.Sphere.put(new Long(191L), "(CL) LRM-5");
/* 1459 */     this.Sphere.put(new Long(192L), "(CL) LRM-10");
/* 1460 */     this.Sphere.put(new Long(193L), "(CL) LRM-15");
/* 1461 */     this.Sphere.put(new Long(194L), "(CL) LRM-20");
/* 1462 */     this.Sphere.put(new Long(195L), "(CL) SRM-2");
/* 1463 */     this.Sphere.put(new Long(196L), "(CL) SRM-4");
/* 1464 */     this.Sphere.put(new Long(197L), "(CL) SRM-6");
/* 1465 */     this.Sphere.put(new Long(198L), "(CL) Streak SRM-2");
/* 1466 */     this.Sphere.put(new Long(199L), "(CL) Streak SRM-4");
/* 1467 */     this.Sphere.put(new Long(200L), "(CL) Streak SRM-6");
/* 1468 */     this.Sphere.put(new Long(205L), "(CL) Narc Missile Beacon");
/* 1469 */     this.Sphere.put(new Long(208L), "(CL) LRM-5 (OS)");
/* 1470 */     this.Sphere.put(new Long(209L), "(CL) LRM-10 (OS)");
/* 1471 */     this.Sphere.put(new Long(210L), "(CL) LRM-15 (OS)");
/* 1472 */     this.Sphere.put(new Long(211L), "(CL) LRM-20 (OS)");
/* 1473 */     this.Sphere.put(new Long(212L), "(CL) SRM-2 (OS)");
/* 1474 */     this.Sphere.put(new Long(213L), "(CL) SRM-4 (OS)");
/* 1475 */     this.Sphere.put(new Long(214L), "(CL) SRM-6 (OS)");
/* 1476 */     this.Sphere.put(new Long(215L), "(CL) Streak SRM-2 (OS)");
/* 1477 */     this.Sphere.put(new Long(216L), "(CL) Streak SRM-4 (OS)");
/* 1478 */     this.Sphere.put(new Long(217L), "(CL) Streak SRM-6 (OS)");
/* 1479 */     this.Sphere.put(new Long(222L), "(CL) LRT-5");
/* 1480 */     this.Sphere.put(new Long(223L), "(CL) LRT-10");
/* 1481 */     this.Sphere.put(new Long(224L), "(CL) LRT-15");
/* 1482 */     this.Sphere.put(new Long(225L), "(CL) LRT-20");
/* 1483 */     this.Sphere.put(new Long(226L), "(CL) SRT-2");
/* 1484 */     this.Sphere.put(new Long(227L), "(CL) SRT-4");
/* 1485 */     this.Sphere.put(new Long(228L), "(CL) SRT-6");
/* 1486 */     this.Sphere.put(new Long(229L), "(CL) Streak LRM-5");
/* 1487 */     this.Sphere.put(new Long(230L), "(CL) Streak LRM-10");
/* 1488 */     this.Sphere.put(new Long(231L), "(CL) Streak LRM-15");
/* 1489 */     this.Sphere.put(new Long(232L), "(CL) Streak LRM-20");
/* 1490 */     this.Sphere.put(new Long(234L), "(CL) LRM-5 (iOS)");
/* 1491 */     this.Sphere.put(new Long(235L), "(CL) LRM-10 (iOS)");
/* 1492 */     this.Sphere.put(new Long(236L), "(CL) LRM-15 (iOS)");
/* 1493 */     this.Sphere.put(new Long(237L), "(CL) LRM-20 (iOS)");
/* 1494 */     this.Sphere.put(new Long(238L), "(CL) SRM-2 (iOS)");
/* 1495 */     this.Sphere.put(new Long(239L), "(CL) SRM-4 (iOS)");
/* 1496 */     this.Sphere.put(new Long(240L), "(CL) SRM-6 (iOS)");
/* 1497 */     this.Sphere.put(new Long(241L), "(CL) Streak SRM-2 (iOS)");
/* 1498 */     this.Sphere.put(new Long(242L), "(CL) Streak SRM-4 (iOS)");
/* 1499 */     this.Sphere.put(new Long(243L), "(CL) Streak SRM-6 (iOS)");
/* 1500 */     this.Clan.put(new Long(75L), "(CL) LRM-5");
/* 1501 */     this.Clan.put(new Long(76L), "(CL) LRM-10");
/* 1502 */     this.Clan.put(new Long(77L), "(CL) LRM-15");
/* 1503 */     this.Clan.put(new Long(78L), "(CL) LRM-20");
/* 1504 */     this.Clan.put(new Long(79L), "(CL) SRM-2");
/* 1505 */     this.Clan.put(new Long(80L), "(CL) SRM-4");
/* 1506 */     this.Clan.put(new Long(81L), "(CL) SRM-6");
/* 1507 */     this.Clan.put(new Long(82L), "(CL) Streak SRM-2");
/* 1508 */     this.Clan.put(new Long(83L), "(CL) Streak SRM-4");
/* 1509 */     this.Clan.put(new Long(84L), "(CL) Streak SRM-6");
/* 1510 */     this.Clan.put(new Long(89L), "(CL) Narc Missile Beacon");
/* 1511 */     this.Clan.put(new Long(92L), "(CL) LRM-5 (OS)");
/* 1512 */     this.Clan.put(new Long(93L), "(CL) LRM-10 (OS)");
/* 1513 */     this.Clan.put(new Long(94L), "(CL) LRM-15 (OS)");
/* 1514 */     this.Clan.put(new Long(95L), "(CL) LRM-20 (OS)");
/* 1515 */     this.Clan.put(new Long(96L), "(CL) SRM-2 (OS)");
/* 1516 */     this.Clan.put(new Long(97L), "(CL) SRM-4 (OS)");
/* 1517 */     this.Clan.put(new Long(98L), "(CL) SRM-6 (OS)");
/* 1518 */     this.Clan.put(new Long(99L), "(CL) Streak SRM-2 (OS)");
/* 1519 */     this.Clan.put(new Long(100L), "(CL) Streak SRM-4 (OS)");
/* 1520 */     this.Clan.put(new Long(101L), "(CL) Streak SRM-6 (OS)");
/* 1521 */     this.Clan.put(new Long(106L), "(CL) LRT-5");
/* 1522 */     this.Clan.put(new Long(107L), "(CL) LRT-10");
/* 1523 */     this.Clan.put(new Long(108L), "(CL) LRT-15");
/* 1524 */     this.Clan.put(new Long(109L), "(CL) LRT-20");
/* 1525 */     this.Clan.put(new Long(110L), "(CL) SRT-2");
/* 1526 */     this.Clan.put(new Long(111L), "(CL) SRT-4");
/* 1527 */     this.Clan.put(new Long(112L), "(CL) SRT-6");
/* 1528 */     this.Clan.put(new Long(113L), "(CL) Streak LRM-5");
/* 1529 */     this.Clan.put(new Long(114L), "(CL) Streak LRM-10");
/* 1530 */     this.Clan.put(new Long(115L), "(CL) Streak LRM-15");
/* 1531 */     this.Clan.put(new Long(116L), "(CL) Streak LRM-20");
/* 1532 */     this.Clan.put(new Long(118L), "(CL) LRM-5 (iOS)");
/* 1533 */     this.Clan.put(new Long(119L), "(CL) LRM-10 (iOS)");
/* 1534 */     this.Clan.put(new Long(120L), "(CL) LRM-15 (iOS)");
/* 1535 */     this.Clan.put(new Long(121L), "(CL) LRM-20 (iOS)");
/* 1536 */     this.Clan.put(new Long(122L), "(CL) SRM-2 (iOS)");
/* 1537 */     this.Clan.put(new Long(123L), "(CL) SRM-4 (iOS)");
/* 1538 */     this.Clan.put(new Long(124L), "(CL) SRM-6 (iOS)");
/* 1539 */     this.Clan.put(new Long(125L), "(CL) Streak SRM-2 (iOS)");
/* 1540 */     this.Clan.put(new Long(126L), "(CL) Streak SRM-4 (iOS)");
/* 1541 */     this.Clan.put(new Long(127L), "(CL) Streak SRM-6 (iOS)");
/* 1542 */     this.Clan.put(new Long(176L), "(IS) LRM-5");
/* 1543 */     this.Clan.put(new Long(177L), "(IS) LRM-10");
/* 1544 */     this.Clan.put(new Long(178L), "(IS) LRM-15");
/* 1545 */     this.Clan.put(new Long(179L), "(IS) LRM-20");
/* 1546 */     this.Clan.put(new Long(182L), "(IS) iNarc Launcher");
/* 1547 */     this.Clan.put(new Long(183L), "(IS) SRM-2");
/* 1548 */     this.Clan.put(new Long(184L), "(IS) SRM-4");
/* 1549 */     this.Clan.put(new Long(185L), "(IS) SRM-6");
/* 1550 */     this.Clan.put(new Long(186L), "(IS) Streak SRM-2");
/* 1551 */     this.Clan.put(new Long(187L), "(IS) Streak SRM-4");
/* 1552 */     this.Clan.put(new Long(188L), "(IS) Streak SRM-6");
/* 1553 */     this.Clan.put(new Long(189L), "(IS) Thunderbolt-5");
/* 1554 */     this.Clan.put(new Long(190L), "(IS) Thunderbolt-10");
/* 1555 */     this.Clan.put(new Long(191L), "(IS) Thunderbolt-15");
/* 1556 */     this.Clan.put(new Long(192L), "(IS) Thunderbolt-20");
/* 1557 */     this.Clan.put(new Long(201L), "(IS) Narc Missile Beacon");
/* 1558 */     this.Clan.put(new Long(203L), "(IS) LRM-5 (OS)");
/* 1559 */     this.Clan.put(new Long(204L), "(IS) LRM-10 (OS)");
/* 1560 */     this.Clan.put(new Long(205L), "(IS) LRM-15 (OS)");
/* 1561 */     this.Clan.put(new Long(206L), "(IS) LRM-20 (OS)");
/* 1562 */     this.Clan.put(new Long(207L), "(IS) SRM-2 (OS)");
/* 1563 */     this.Clan.put(new Long(208L), "(IS) SRM-4 (OS)");
/* 1564 */     this.Clan.put(new Long(209L), "(IS) SRM-6 (OS)");
/* 1565 */     this.Clan.put(new Long(210L), "(IS) Streak SRM-2 (OS)");
/* 1566 */     this.Clan.put(new Long(211L), "(IS) Streak SRM-4 (OS)");
/* 1567 */     this.Clan.put(new Long(212L), "(IS) Streak SRM-6 (OS)");
/* 1568 */     this.Clan.put(new Long(217L), "(IS) MRM-10");
/* 1569 */     this.Clan.put(new Long(218L), "(IS) MRM-20");
/* 1570 */     this.Clan.put(new Long(219L), "(IS) MRM-30");
/* 1571 */     this.Clan.put(new Long(220L), "(IS) MRM-40");
/* 1572 */     this.Clan.put(new Long(222L), "(IS) MRM-10 (OS)");
/* 1573 */     this.Clan.put(new Long(223L), "(IS) MRM-20 (OS)");
/* 1574 */     this.Clan.put(new Long(224L), "(IS) MRM-30 (OS)");
/* 1575 */     this.Clan.put(new Long(225L), "(IS) MRM-40 (OS)");
/* 1576 */     this.Clan.put(new Long(226L), "(IS) LRT-5");
/* 1577 */     this.Clan.put(new Long(227L), "(IS) LRT-10");
/* 1578 */     this.Clan.put(new Long(228L), "(IS) LRT-15");
/* 1579 */     this.Clan.put(new Long(229L), "(IS) LRT-20");
/* 1580 */     this.Clan.put(new Long(230L), "(IS) SRT-2");
/* 1581 */     this.Clan.put(new Long(231L), "(IS) SRT-4");
/* 1582 */     this.Clan.put(new Long(232L), "(IS) SRT-6");
/* 1583 */     this.Clan.put(new Long(233L), "(IS) LRM-5 (iOS)");
/* 1584 */     this.Clan.put(new Long(234L), "(IS) LRM-10 (iOS)");
/* 1585 */     this.Clan.put(new Long(235L), "(IS) LRM-15 (iOS)");
/* 1586 */     this.Clan.put(new Long(236L), "(IS) LRM-20 (iOS)");
/* 1587 */     this.Clan.put(new Long(237L), "(IS) SRM-2 (iOS)");
/* 1588 */     this.Clan.put(new Long(238L), "(IS) SRM-4 (iOS)");
/* 1589 */     this.Clan.put(new Long(239L), "(IS) SRM-6 (iOS)");
/* 1590 */     this.Clan.put(new Long(240L), "(IS) Streak SRM-2 (iOS)");
/* 1591 */     this.Clan.put(new Long(241L), "(IS) Streak SRM-4 (iOS)");
/* 1592 */     this.Clan.put(new Long(242L), "(IS) Streak SRM-6 (iOS)");
/* 1593 */     this.Clan.put(new Long(243L), "(IS) MRM-10 (iOS)");
/* 1594 */     this.Clan.put(new Long(244L), "(IS) MRM-20 (iOS)");
/* 1595 */     this.Clan.put(new Long(245L), "(IS) MRM-30 (iOS)");
/* 1596 */     this.Clan.put(new Long(246L), "(IS) MRM-40 (iOS)");
/*      */     
/*      */ 
/* 1599 */     this.Clan.put(new Long(215L), "(IS) Sniper");
/* 1600 */     this.Clan.put(new Long(216L), "(IS) Thumper");
/* 1601 */     this.Clan.put(new Long(85L), "(CL) Arrow IV Missile");
/* 1602 */     this.Clan.put(new Long(104L), "(IS) Sniper");
/* 1603 */     this.Clan.put(new Long(105L), "(IS) Thumper");
/* 1604 */     this.Sphere.put(new Long(135L), "(IS) Sniper");
/* 1605 */     this.Sphere.put(new Long(136L), "(IS) Thumper");
/* 1606 */     this.Sphere.put(new Long(113L), "(IS) Arrow IV Missile");
/* 1607 */     this.Sphere.put(new Long(201L), "(CL) Arrow IV Missile");
/* 1608 */     this.Sphere.put(new Long(220L), "(IS) Sniper");
/* 1609 */     this.Sphere.put(new Long(221L), "(IS) Thumper");
/*      */     
/*      */ 
/* 1612 */     this.Common.put(new Long(4294967948L), "(CL) @ ATM-3 (ER)");
/* 1613 */     this.Common.put(new Long(8589935244L), "(CL) @ ATM-3 (HE)");
/* 1614 */     this.Common.put(new Long(4294967949L), "(CL) @ ATM-6 (ER)");
/* 1615 */     this.Common.put(new Long(8589935245L), "(CL) @ ATM-6 (HE)");
/* 1616 */     this.Common.put(new Long(4294967950L), "(CL) @ ATM-9 (ER)");
/* 1617 */     this.Common.put(new Long(8589935246L), "(CL) @ ATM-9 (HE)");
/* 1618 */     this.Common.put(new Long(4294967951L), "(CL) @ ATM-12 (ER)");
/* 1619 */     this.Common.put(new Long(8589935247L), "(CL) @ ATM-12 (HE)");
/* 1620 */     this.Common.put(new Long(652L), "(CL) @ ATM-3");
/* 1621 */     this.Common.put(new Long(653L), "(CL) @ ATM-6");
/* 1622 */     this.Common.put(new Long(654L), "(CL) @ ATM-9");
/* 1623 */     this.Common.put(new Long(655L), "(CL) @ ATM-12");
/* 1624 */     this.Common.put(new Long(689L), "(IS) @ Rotary AC/2");
/* 1625 */     this.Common.put(new Long(690L), "(IS) @ Rotary AC/5");
/* 1626 */     this.Common.put(new Long(692L), "(CL) @ Rotary AC/2");
/* 1627 */     this.Common.put(new Long(693L), "(CL) @ Rotary AC/5");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1633 */     this.Sphere.put(new Long(462L), "(IS) @ AC/2");
/* 1634 */     this.Sphere.put(new Long(463L), "(IS) @ AC/5");
/* 1635 */     this.Sphere.put(new Long(464L), "(IS) @ AC/10");
/* 1636 */     this.Sphere.put(new Long(465L), "(IS) @ AC/20");
/* 1637 */     this.Sphere.put(new Long(466L), "(IS) @ Anti-Missile System");
/* 1638 */     this.Sphere.put(new Long(467L), "@ Long Tom Cannon");
/* 1639 */     this.Sphere.put(new Long(468L), "@ Sniper Cannon");
/* 1640 */     this.Sphere.put(new Long(469L), "@ Thumper Cannon");
/* 1641 */     this.Sphere.put(new Long(470L), "(IS) @ Light Gauss Rifle");
/* 1642 */     this.Sphere.put(new Long(471L), "@ Gauss Rifle");
/* 1643 */     this.Sphere.put(new Long(475L), "(IS) @ LB 2-X AC (Slug)");
/* 1644 */     this.Sphere.put(new Long(476L), "(IS) @ LB 5-X AC (Slug)");
/* 1645 */     this.Sphere.put(new Long(477L), "(IS) @ LB 10-X AC (Slug)");
/* 1646 */     this.Sphere.put(new Long(478L), "(IS) @ LB 20-X AC (Slug)");
/* 1647 */     this.Sphere.put(new Long(479L), "@ Machine Gun");
/* 1648 */     this.Sphere.put(new Long(480L), "@ Light AC/2");
/* 1649 */     this.Sphere.put(new Long(481L), "@ Light AC/5");
/* 1650 */     this.Sphere.put(new Long(482L), "@ Heavy Flamer");
/* 1651 */     this.Sphere.put(new Long(484L), "(IS) @ Ultra AC/2");
/* 1652 */     this.Sphere.put(new Long(485L), "(IS) @ Ultra AC/5");
/* 1653 */     this.Sphere.put(new Long(486L), "(IS) @ Ultra AC/10");
/* 1654 */     this.Sphere.put(new Long(487L), "(IS) @ Ultra AC/20");
/* 1655 */     this.Sphere.put(new Long(494L), "@ Light Machine Gun");
/* 1656 */     this.Sphere.put(new Long(495L), "@ Heavy Machine Gun");
/* 1657 */     this.Sphere.put(new Long(496L), "(IS) @ LRM-5");
/* 1658 */     this.Sphere.put(new Long(497L), "(IS) @ LRM-10");
/* 1659 */     this.Sphere.put(new Long(498L), "(IS) @ LRM-15");
/* 1660 */     this.Sphere.put(new Long(499L), "(IS) @ LRM-20");
/* 1661 */     this.Sphere.put(new Long(502L), "@ iNarc (Homing)");
/* 1662 */     this.Sphere.put(new Long(503L), "@ SRM-2");
/* 1663 */     this.Sphere.put(new Long(504L), "@ SRM-4");
/* 1664 */     this.Sphere.put(new Long(505L), "@ SRM-6");
/* 1665 */     this.Sphere.put(new Long(506L), "(IS) @ Streak SRM-2");
/* 1666 */     this.Sphere.put(new Long(507L), "(IS) @ Streak SRM-4");
/* 1667 */     this.Sphere.put(new Long(508L), "(IS) @ Streak SRM-6");
/* 1668 */     this.Sphere.put(new Long(509L), "@ Thunderbolt-5");
/* 1669 */     this.Sphere.put(new Long(510L), "@ Thunderbolt-10");
/* 1670 */     this.Sphere.put(new Long(511L), "@ Thunderbolt-15");
/* 1671 */     this.Sphere.put(new Long(512L), "@ Thunderbolt-20");
/* 1672 */     this.Sphere.put(new Long(513L), "(IS) @ Arrow IV (Homing)");
/* 1673 */     this.Sphere.put(new Long(521L), "(IS) @ Narc (Homing)");
/* 1674 */     this.Sphere.put(new Long(533L), "@ Vehicle Flamer");
/* 1675 */     this.Sphere.put(new Long(535L), "(IS) @ Sniper");
/* 1676 */     this.Sphere.put(new Long(536L), "(IS) @ Thumper");
/* 1677 */     this.Sphere.put(new Long(537L), "(IS) @ MRM-10");
/* 1678 */     this.Sphere.put(new Long(538L), "(IS) @ MRM-20");
/* 1679 */     this.Sphere.put(new Long(539L), "(IS) @ MRM-30");
/* 1680 */     this.Sphere.put(new Long(540L), "(IS) @ MRM-40");
/* 1681 */     this.Sphere.put(new Long(546L), "(IS) @ LRT-5 (Torpedo)");
/* 1682 */     this.Sphere.put(new Long(547L), "(IS) @ LRT-10 (Torpedo)");
/* 1683 */     this.Sphere.put(new Long(548L), "(IS) @ LRT-15 (Torpedo)");
/* 1684 */     this.Sphere.put(new Long(549L), "(IS) @ LRT-20 (Torpedo)");
/* 1685 */     this.Sphere.put(new Long(550L), "@ SRT-2 (Torpedo)");
/* 1686 */     this.Sphere.put(new Long(551L), "@ SRT-4 (Torpedo)");
/* 1687 */     this.Sphere.put(new Long(552L), "@ SRT-6 (Torpedo)");
/* 1688 */     this.Sphere.put(new Long(580L), "(CL) @ Anti-Missile System");
/* 1689 */     this.Sphere.put(new Long(581L), "@ Gauss Rifle");
/* 1690 */     this.Sphere.put(new Long(582L), "(CL) @ LB 2-X AC (Slug)");
/* 1691 */     this.Sphere.put(new Long(583L), "(CL) @ LB 5-X AC (Slug)");
/* 1692 */     this.Sphere.put(new Long(584L), "(CL) @ LB 10-X AC (Slug)");
/* 1693 */     this.Sphere.put(new Long(585L), "(CL) @ LB 20-X AC (Slug)");
/* 1694 */     this.Sphere.put(new Long(586L), "@ Machine Gun");
/* 1695 */     this.Sphere.put(new Long(587L), "(CL) @ Ultra AC/2");
/* 1696 */     this.Sphere.put(new Long(588L), "(CL) @ Ultra AC/5");
/* 1697 */     this.Sphere.put(new Long(589L), "(CL) @ Ultra AC/10");
/* 1698 */     this.Sphere.put(new Long(590L), "(CL) @ Ultra AC/20");
/* 1699 */     this.Sphere.put(new Long(591L), "(CL) @ LRM-5");
/* 1700 */     this.Sphere.put(new Long(592L), "(CL) @ LRM-10");
/* 1701 */     this.Sphere.put(new Long(593L), "(CL) @ LRM-15");
/* 1702 */     this.Sphere.put(new Long(594L), "(CL) @ LRM-20");
/* 1703 */     this.Sphere.put(new Long(595L), "@ SRM-2");
/* 1704 */     this.Sphere.put(new Long(596L), "@ SRM-4");
/* 1705 */     this.Sphere.put(new Long(597L), "@ SRM-6");
/* 1706 */     this.Sphere.put(new Long(598L), "(CL) @ Streak SRM-2");
/* 1707 */     this.Sphere.put(new Long(599L), "(CL) @ Streak SRM-4");
/* 1708 */     this.Sphere.put(new Long(600L), "(CL) @ Streak SRM-6");
/* 1709 */     this.Sphere.put(new Long(601L), "(CL) @ Arrow IV (Homing)");
/* 1710 */     this.Sphere.put(new Long(605L), "(CL) @ Narc (Homing)");
/* 1711 */     this.Sphere.put(new Long(618L), "@ Vehicle Flamer");
/* 1712 */     this.Sphere.put(new Long(620L), "@ Sniper");
/* 1713 */     this.Sphere.put(new Long(621L), "@ Thumper");
/* 1714 */     this.Sphere.put(new Long(622L), "(CL) @ LRT-5 (Torpedo)");
/* 1715 */     this.Sphere.put(new Long(623L), "(CL) @ LRT-10 (Torpedo)");
/* 1716 */     this.Sphere.put(new Long(624L), "(CL) @ LRT-15 (Torpedo)");
/* 1717 */     this.Sphere.put(new Long(625L), "(CL) @ LRT-20 (Torpedo)");
/* 1718 */     this.Sphere.put(new Long(626L), "@ SRT-2");
/* 1719 */     this.Sphere.put(new Long(627L), "@ SRT-4");
/* 1720 */     this.Sphere.put(new Long(628L), "@ SRT-6");
/* 1721 */     this.Sphere.put(new Long(629L), "@ Streak LRM-5");
/* 1722 */     this.Sphere.put(new Long(630L), "@ Streak LRM-10");
/* 1723 */     this.Sphere.put(new Long(631L), "@ Streak LRM-15");
/* 1724 */     this.Sphere.put(new Long(632L), "@ Streak LRM-20");
/* 1725 */     this.Sphere.put(new Long(691L), "(IS) @ Heavy Gauss Rifle");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1731 */     this.Sphere.put(new Long(4294967771L), "(IS) @ LB 2-X AC (Cluster)");
/* 1732 */     this.Sphere.put(new Long(4294967772L), "(IS) @ LB 5-X AC (Cluster)");
/* 1733 */     this.Sphere.put(new Long(4294967773L), "(IS) @ LB 10-X AC (Cluster)");
/* 1734 */     this.Sphere.put(new Long(4294967774L), "(IS) @ LB 20-X AC (Cluster)");
/* 1735 */     this.Sphere.put(new Long(4294967878L), "(CL) @ LB 2-X AC (Cluster)");
/* 1736 */     this.Sphere.put(new Long(4294967879L), "(CL) @ LB 5-X AC (Cluster)");
/* 1737 */     this.Sphere.put(new Long(4294967880L), "(CL) @ LB 10-X AC (Cluster)");
/* 1738 */     this.Sphere.put(new Long(4294967881L), "(CL) @ LB 20-X AC (Cluster)");
/*      */     
/* 1740 */     this.Clan.put(new Long(464L), "(CL) @ Anti-Missile System");
/*      */     
/* 1742 */     this.Clan.put(new Long(465L), "@ Gauss Rifle");
/* 1743 */     this.Clan.put(new Long(466L), "(CL) @ LB 2-X AC (Slug)");
/* 1744 */     this.Clan.put(new Long(467L), "(CL) @ LB 5-X AC (Slug)");
/* 1745 */     this.Clan.put(new Long(468L), "(CL) @ LB 10-X AC (Slug)");
/* 1746 */     this.Clan.put(new Long(469L), "(CL) @ LB 20-X AC (Slug)");
/* 1747 */     this.Clan.put(new Long(470L), "@ Machine Gun");
/* 1748 */     this.Clan.put(new Long(471L), "(CL) @ Ultra AC/2");
/* 1749 */     this.Clan.put(new Long(472L), "(CL) @ Ultra AC/5");
/* 1750 */     this.Clan.put(new Long(473L), "(CL) @ Ultra AC/10");
/* 1751 */     this.Clan.put(new Long(474L), "(CL) @ Ultra AC/20");
/* 1752 */     this.Clan.put(new Long(475L), "(CL) @ LRM-5");
/* 1753 */     this.Clan.put(new Long(476L), "(CL) @ LRM-10");
/* 1754 */     this.Clan.put(new Long(477L), "(CL) @ LRM-15");
/* 1755 */     this.Clan.put(new Long(478L), "(CL) @ LRM-20");
/* 1756 */     this.Clan.put(new Long(479L), "@ SRM-2");
/* 1757 */     this.Clan.put(new Long(480L), "@ SRM-4");
/* 1758 */     this.Clan.put(new Long(481L), "@ SRM-6");
/* 1759 */     this.Clan.put(new Long(482L), "(CL) @ Streak SRM-2");
/* 1760 */     this.Clan.put(new Long(483L), "(CL) @ Streak SRM-4");
/* 1761 */     this.Clan.put(new Long(484L), "(CL) @ Streak SRM-6");
/* 1762 */     this.Clan.put(new Long(485L), "(CL) @ Arrow IV (Homing)");
/* 1763 */     this.Clan.put(new Long(489L), "(CL) @ Narc (Homing)");
/*      */     
/* 1765 */     this.Clan.put(new Long(496L), "(CL) @ LRM-5");
/* 1766 */     this.Clan.put(new Long(497L), "(CL) @ LRM-10");
/* 1767 */     this.Clan.put(new Long(498L), "(CL) @ LRM-15");
/* 1768 */     this.Clan.put(new Long(499L), "(CL) @ LRM-20");
/* 1769 */     this.Clan.put(new Long(502L), "@ Vehicle Flamer");
/* 1770 */     this.Clan.put(new Long(504L), "@ Sniper");
/* 1771 */     this.Clan.put(new Long(505L), "@ Thumper");
/* 1772 */     this.Clan.put(new Long(506L), "(CL) @ LRT-5 (Torpedo)");
/* 1773 */     this.Clan.put(new Long(507L), "(CL) @ LRT-10 (Torpedo)");
/* 1774 */     this.Clan.put(new Long(508L), "(CL) @ LRT-15 (Torpedo)");
/* 1775 */     this.Clan.put(new Long(509L), "(CL) @ LRT-20 (Torpedo)");
/* 1776 */     this.Clan.put(new Long(510L), "@ SRT-2");
/* 1777 */     this.Clan.put(new Long(511L), "@ SRT-4");
/* 1778 */     this.Clan.put(new Long(512L), "@ SRT-6");
/* 1779 */     this.Clan.put(new Long(513L), "@ Streak LRM-5");
/* 1780 */     this.Clan.put(new Long(514L), "@ Streak LRM-10");
/* 1781 */     this.Clan.put(new Long(515L), "@ Streak LRM-15");
/* 1782 */     this.Clan.put(new Long(516L), "@ Streak LRM-20");
/* 1783 */     this.Clan.put(new Long(542L), "@ AC/2");
/* 1784 */     this.Clan.put(new Long(543L), "@ AC/5");
/* 1785 */     this.Clan.put(new Long(544L), "@ AC/10");
/* 1786 */     this.Clan.put(new Long(545L), "@ AC/20");
/* 1787 */     this.Clan.put(new Long(546L), "(IS) @ Anti-Missile System");
/* 1788 */     this.Clan.put(new Long(547L), "@ Long Tom Cannon");
/* 1789 */     this.Clan.put(new Long(548L), "@ Sniper Cannon");
/* 1790 */     this.Clan.put(new Long(549L), "@ Thumper Cannon");
/* 1791 */     this.Clan.put(new Long(550L), "(IS) @ Light Gauss Rifle");
/* 1792 */     this.Clan.put(new Long(551L), "@ Gauss Rifle");
/*      */     
/* 1794 */     this.Clan.put(new Long(555L), "(IS) @ LB 2-X AC (Slug)");
/* 1795 */     this.Clan.put(new Long(556L), "(IS) @ LB 5-X AC (Slug)");
/* 1796 */     this.Clan.put(new Long(557L), "(IS) @ LB 10-X AC (Slug)");
/* 1797 */     this.Clan.put(new Long(558L), "(IS) @ LB 20-X AC (Slug)");
/* 1798 */     this.Clan.put(new Long(559L), "@ Machine Gun");
/* 1799 */     this.Clan.put(new Long(560L), "@ Light AC/2");
/* 1800 */     this.Clan.put(new Long(561L), "@ Light AC/5");
/* 1801 */     this.Clan.put(new Long(564L), "(IS) @ Ultra AC/2");
/* 1802 */     this.Clan.put(new Long(565L), "(IS) @ Ultra AC/5");
/* 1803 */     this.Clan.put(new Long(566L), "(IS) @ Ultra AC/10");
/* 1804 */     this.Clan.put(new Long(567L), "(IS) @ Ultra AC/20");
/* 1805 */     this.Clan.put(new Long(573L), "@ Light Machine Gun");
/* 1806 */     this.Clan.put(new Long(574L), "@ Heavy Machine Gun");
/* 1807 */     this.Clan.put(new Long(576L), "(IS) @ LRM-5");
/* 1808 */     this.Clan.put(new Long(577L), "(IS) @ LRM-10");
/* 1809 */     this.Clan.put(new Long(578L), "(IS) @ LRM-15");
/* 1810 */     this.Clan.put(new Long(579L), "(IS) @ LRM-20");
/* 1811 */     this.Clan.put(new Long(582L), "@ iNarc (Homing)");
/* 1812 */     this.Clan.put(new Long(583L), "@ SRM-2");
/* 1813 */     this.Clan.put(new Long(584L), "@ SRM-4");
/* 1814 */     this.Clan.put(new Long(585L), "@ SRM-6");
/* 1815 */     this.Clan.put(new Long(586L), "(IS) @ Streak SRM-2");
/* 1816 */     this.Clan.put(new Long(587L), "(IS) @ Streak SRM-4");
/* 1817 */     this.Clan.put(new Long(588L), "(IS) @ Streak SRM-6");
/* 1818 */     this.Clan.put(new Long(589L), "@ Thunderbolt-5");
/* 1819 */     this.Clan.put(new Long(590L), "@ Thunderbolt-10");
/* 1820 */     this.Clan.put(new Long(591L), "@ Thunderbolt-15");
/* 1821 */     this.Clan.put(new Long(592L), "@ Thunderbolt-20");
/* 1822 */     this.Clan.put(new Long(601L), "(IS) @ Narc (Homing)");
/* 1823 */     this.Clan.put(new Long(613L), "@ Vehicle Flamer");
/* 1824 */     this.Clan.put(new Long(615L), "@ Sniper");
/* 1825 */     this.Clan.put(new Long(616L), "@ Thumper");
/* 1826 */     this.Clan.put(new Long(617L), "(IS) @ MRM-10");
/* 1827 */     this.Clan.put(new Long(618L), "(IS) @ MRM-20");
/* 1828 */     this.Clan.put(new Long(619L), "(IS) @ MRM-30");
/* 1829 */     this.Clan.put(new Long(620L), "(IS) @ MRM-40");
/* 1830 */     this.Clan.put(new Long(626L), "(IS) @ LRT-15 (Torpedo)");
/* 1831 */     this.Clan.put(new Long(627L), "(IS) @ LRT-20 (Torpedo)");
/* 1832 */     this.Clan.put(new Long(628L), "(IS) @ LRT-5 (Torpedo)");
/* 1833 */     this.Clan.put(new Long(629L), "(IS) @ LRT-10 (Torpedo)");
/* 1834 */     this.Clan.put(new Long(630L), "@ SRT-4");
/* 1835 */     this.Clan.put(new Long(631L), "@ SRT-2");
/* 1836 */     this.Clan.put(new Long(632L), "@ SRT-6");
/* 1837 */     this.Clan.put(new Long(4294967851L), "(IS) @ LB 2-X AC (Slug)");
/* 1838 */     this.Clan.put(new Long(4294967852L), "(IS) @ LB 5-X AC (Slug)");
/* 1839 */     this.Clan.put(new Long(4294967853L), "(IS) @ LB 10-X AC (Slug)");
/* 1840 */     this.Clan.put(new Long(4294967854L), "(IS) @ LB 20-X AC (Slug)");
/* 1841 */     this.Clan.put(new Long(4294967762L), "(CL) @ LB 2-X AC (Cluster)");
/* 1842 */     this.Clan.put(new Long(4294967763L), "(CL) @ LB 5-X AC (Cluster)");
/* 1843 */     this.Clan.put(new Long(4294967764L), "(CL) @ LB 10-X AC (Cluster)");
/* 1844 */     this.Clan.put(new Long(4294967765L), "(CL) @ LB 20-X AC (Cluster)");
/*      */     
/*      */ 
/* 1847 */     this.Unused.put(new Long(285L), "ISTHBAngelECMSuite");
/* 1848 */     this.Unused.put(new Long(286L), "ISTHBBloodhoundActiveProbe");
/* 1849 */     this.Unused.put(new Long(534L), "ISLongTom Ammo");
/* 1850 */     this.Unused.put(new Long(619L), "CLLongTom Ammo");
/* 1851 */     this.Unused.put(new Long(103L), "CLLongTomArtillery");
/* 1852 */     this.Unused.put(new Long(117L), "CLGrenadeLauncher");
/* 1853 */     this.Unused.put(new Long(207L), "Thunderbolt (OS)");
/* 1854 */     this.Unused.put(new Long(214L), "ISLongTomArtillery");
/* 1855 */     this.Unused.put(new Long(221L), "Grenade Launcher");
/* 1856 */     this.Unused.put(new Long(503L), "CLLongTom Ammo");
/* 1857 */     this.Unused.put(new Long(614L), "Long Tom Ammo");
/* 1858 */     this.Unused.put(new Long(694L), "CLRotaryAC10 Ammo");
/* 1859 */     this.Unused.put(new Long(695L), "CLRotaryAC20 Ammo");
/* 1860 */     this.Unused.put(new Long(700L), "Mortar/1 Ammo (THB)");
/* 1861 */     this.Unused.put(new Long(701L), "Mortar/2 Ammo (THB)");
/* 1862 */     this.Unused.put(new Long(702L), "Mortar/4 Ammo (THB)");
/* 1863 */     this.Unused.put(new Long(703L), "Mortar/8 Ammo (THB)");
/* 1864 */     this.Unused.put(new Long(670L), "ELRM-5 Ammo (THB)");
/* 1865 */     this.Unused.put(new Long(671L), "ELRM-10 Ammo (THB)");
/* 1866 */     this.Unused.put(new Long(672L), "ELRM-15 Ammo (THB)");
/* 1867 */     this.Unused.put(new Long(673L), "ELRM-20 Ammo (THB)");
/* 1868 */     this.Unused.put(new Long(674L), "LR DFM-5 Ammo (THB)");
/* 1869 */     this.Unused.put(new Long(675L), "LR DFM-10 Ammo (THB)");
/* 1870 */     this.Unused.put(new Long(676L), "LR DFM-15 Ammo (THB)");
/* 1871 */     this.Unused.put(new Long(677L), "LR DFM-20 Ammo (THB)");
/* 1872 */     this.Unused.put(new Long(678L), "SR DFM-2 Ammo (THB)");
/* 1873 */     this.Unused.put(new Long(679L), "SR DFM-4 Ammo (THB)");
/* 1874 */     this.Unused.put(new Long(680L), "SR DFM-6 Ammo (THB)");
/* 1875 */     this.Unused.put(new Long(681L), "Thunderbolt-5 Ammo (THB)");
/* 1876 */     this.Unused.put(new Long(682L), "Thunderbolt-10 Ammo (THB)");
/* 1877 */     this.Unused.put(new Long(683L), "Thunderbolt-15 Ammo (THB)");
/* 1878 */     this.Unused.put(new Long(684L), "Thunderbolt-20 Ammo (THB)");
/* 1879 */     this.Unused.put(new Long(36L), "Blue Shield (UB)");
/* 1880 */     this.Unused.put(new Long(134L), "ISLongTomArtillery");
/* 1881 */     this.Unused.put(new Long(141L), "Grenade Launcher");
/* 1882 */     this.Unused.put(new Long(207L), "Thunderbolt (OS)");
/* 1883 */     this.Unused.put(new Long(219L), "CLLongTomArtillery");
/* 1884 */     this.Unused.put(new Long(233L), "CLGrenadeLauncher");
/* 1885 */     this.Unused.put(new Long(656L), "SB Gauss Rifle Ammo (UB)");
/* 1886 */     this.Unused.put(new Long(657L), "Caseless AC/2 Ammo (THB)");
/* 1887 */     this.Unused.put(new Long(658L), "Caseless AC/5 Ammo (THB)");
/* 1888 */     this.Unused.put(new Long(659L), "Caseless AC/10 Ammo (THB)");
/* 1889 */     this.Unused.put(new Long(660L), "Caseless AC/20 Ammo (THB)");
/* 1890 */     this.Unused.put(new Long(661L), "Heavy AC/2 Ammo (THB)");
/* 1891 */     this.Unused.put(new Long(662L), "Heavy AC/5 Ammo (THB)");
/* 1892 */     this.Unused.put(new Long(663L), "Heavy AC/10 Ammo (THB)");
/* 1893 */     this.Unused.put(new Long(664L), "ISLBXAC2 Ammo (THB)");
/* 1894 */     this.Unused.put(new Long(665L), "ISLBXAC5 Ammo (THB)");
/* 1895 */     this.Unused.put(new Long(666L), "ISLBXAC20 Ammo (THB)");
/* 1896 */     this.Unused.put(new Long(667L), "IS Ultra AC/2 Ammo (THB)");
/* 1897 */     this.Unused.put(new Long(668L), "IS Ultra AC/10 Ammo (THB)");
/* 1898 */     this.Unused.put(new Long(669L), "IS Ultra AC/20 Ammo (THB)");
/* 1899 */     this.Unused.put(new Long(307L), "CLStreakLRM5 (OS)");
/* 1900 */     this.Unused.put(new Long(308L), "CLStreakLRM10 (OS)");
/* 1901 */     this.Unused.put(new Long(309L), "CLStreakLRM15 (OS)");
/* 1902 */     this.Unused.put(new Long(310L), "CLStreakLRM20 (OS)");
/* 1903 */     this.Unused.put(new Long(300L), "Mortar/1 (THB)");
/* 1904 */     this.Unused.put(new Long(301L), "Mortar/2 (THB)");
/* 1905 */     this.Unused.put(new Long(302L), "Mortar/4 (THB)");
/* 1906 */     this.Unused.put(new Long(303L), "Mortar/8 (THB)");
/* 1907 */     this.Unused.put(new Long(256L), "SB Gauss Rifle (UB)");
/* 1908 */     this.Unused.put(new Long(257L), "Caseless AC/2 (THB)");
/* 1909 */     this.Unused.put(new Long(258L), "Caseless AC/5 (THB)");
/* 1910 */     this.Unused.put(new Long(259L), "Caseless AC/10 (THB)");
/* 1911 */     this.Unused.put(new Long(260L), "Caseless AC/20 (THB)");
/* 1912 */     this.Unused.put(new Long(261L), "Heavy AC/2 (THB)");
/* 1913 */     this.Unused.put(new Long(262L), "Heavy AC/5 (THB)");
/* 1914 */     this.Unused.put(new Long(263L), "Heavy AC/10 (THB)");
/* 1915 */     this.Unused.put(new Long(264L), "ISTHBLBXAC2");
/* 1916 */     this.Unused.put(new Long(265L), "ISTHBLBXAC5");
/* 1917 */     this.Unused.put(new Long(266L), "ISTHBLBXAC20");
/* 1918 */     this.Unused.put(new Long(267L), "ISUltraAC2 (THB)");
/* 1919 */     this.Unused.put(new Long(268L), "ISUltraAC10 (THB)");
/* 1920 */     this.Unused.put(new Long(269L), "ISUltraAC20 (THB)");
/* 1921 */     this.Unused.put(new Long(270L), "ELRM-5 (THB)");
/* 1922 */     this.Unused.put(new Long(271L), "ELRM-10 (THB)");
/* 1923 */     this.Unused.put(new Long(272L), "ELRM-15 (THB)");
/* 1924 */     this.Unused.put(new Long(273L), "ELRM-20 (THB)");
/* 1925 */     this.Unused.put(new Long(274L), "LR DFM-5 (THB)");
/* 1926 */     this.Unused.put(new Long(275L), "LR DFM-10 (THB)");
/* 1927 */     this.Unused.put(new Long(276L), "LR DFM-15 (THB)");
/* 1928 */     this.Unused.put(new Long(277L), "LR DFM-20 (THB)");
/* 1929 */     this.Unused.put(new Long(278L), "SR DFM-2 (THB)");
/* 1930 */     this.Unused.put(new Long(279L), "SR DFM-4 (THB)");
/* 1931 */     this.Unused.put(new Long(280L), "SR DFM-6 (THB)");
/* 1932 */     this.Unused.put(new Long(281L), "Thunderbolt-5 (THB)");
/* 1933 */     this.Unused.put(new Long(282L), "Thunderbolt-10 (THB)");
/* 1934 */     this.Unused.put(new Long(283L), "Thunderbolt-15 (THB)");
/* 1935 */     this.Unused.put(new Long(284L), "Thunderbolt-20 (THB)");
/* 1936 */     this.Unused.put(new Long(287L), "Watchdog ECM (THB)");
/* 1937 */     this.Unused.put(new Long(288L), "IS Laser AMS (THB)");
/* 1938 */     this.Unused.put(new Long(43L), "Claw (THB)");
/* 1939 */     this.Unused.put(new Long(44L), "Mace (THB)");
/* 1940 */     this.Unused.put(new Long(45L), "Armored Cowl");
/* 1941 */     this.Unused.put(new Long(46L), "Buzzsaw (UB)");
/* 1942 */     this.Unused.put(new Long(19L), "Turret");
/* 1943 */     this.Unused.put(new Long(26L), "Variable Range TargSys");
/* 1944 */     this.Unused.put(new Long(27L), "Multi-Trac II");
/* 1945 */     this.Unused.put(new Long(30L), "Jump Booster");
/* 1946 */     this.Unused.put(new Long(294L), "CLRotaryAC10");
/* 1947 */     this.Unused.put(new Long(295L), "CLRotaryAC20");
/*      */   }
/*      */   
/*      */   private int DecodeRulesLevel(int OldRules) {
/* 1951 */     switch (OldRules) {
/*      */     case 1: 
/* 1953 */       return 0;
/*      */     
/*      */     case 2: 
/* 1956 */       return 1;
/*      */     case 3: 
/* 1958 */       return 3;
/*      */     }
/* 1960 */     return -1;
/*      */   }
/*      */   
/*      */   private short readUnsignedByte(DataInputStream dis) throws Exception
/*      */   {
/* 1965 */     short b = (short)dis.readByte();
/* 1966 */     b = (short)(b + (b < 0 ? 256 : 0));
/* 1967 */     return b;
/*      */   }
/*      */   
/*      */   private int readUnsignedShort(DataInputStream dis) throws Exception {
/* 1971 */     int b2 = readUnsignedByte(dis);
/*      */     
/* 1973 */     int b1 = readUnsignedByte(dis);
/* 1974 */     b1 <<= 8;
/*      */     
/* 1976 */     return b1 + b2;
/*      */   }
/*      */   
/*      */   private long readUnsignedInt(DataInputStream dis) throws Exception {
/* 1980 */     long b4 = readUnsignedByte(dis);
/*      */     
/* 1982 */     long b3 = readUnsignedByte(dis);
/* 1983 */     b3 <<= 8;
/*      */     
/* 1985 */     long b2 = readUnsignedByte(dis);
/* 1986 */     b2 <<= 16;
/*      */     
/* 1988 */     long b1 = readUnsignedByte(dis);
/* 1989 */     b1 <<= 32;
/*      */     
/* 1991 */     return b1 + b2 + b3 + b4;
/*      */   }
/*      */   
/*      */   private boolean IsRearMounted(long critical) {
/* 1995 */     return (critical & 0xFFFFFFFFFFFF0000) != 0L;
/*      */   }
/*      */   
/*      */ 
/*      */   private Long GetLookupNum(long l)
/*      */   {
/* 2001 */     Long retval = new Long(l & 0xFFFF);
/* 2002 */     return retval;
/*      */   }
/*      */   
/*      */   private String BuildLookupName(String name, int mechbase, int techbase) {
/* 2006 */     if (mechbase == 2) {
/* 2007 */       if (techbase == 0) {
/* 2008 */         return "(IS) " + name;
/*      */       }
/* 2010 */       return "(CL) " + name;
/*      */     }
/*      */     
/* 2013 */     return name;
/*      */   }
/*      */   
/*      */   private class ErrorReport {
/*      */     private long HMPNumber;
/*      */     private String EQName;
/* 2019 */     private String CustomError = null;
/* 2020 */     private boolean HasCustom = false;
/*      */     
/*      */     public ErrorReport(Long num, String name) {
/* 2023 */       this.HMPNumber = num.longValue();
/* 2024 */       this.EQName = name;
/*      */     }
/*      */     
/*      */     public ErrorReport(String custom) {
/* 2028 */       this.CustomError = custom;
/* 2029 */       this.HasCustom = true;
/*      */     }
/*      */     
/*      */     public String GetErrorReport() {
/* 2033 */       if (this.HasCustom) {
/* 2034 */         return this.CustomError;
/*      */       }
/* 2036 */       return "The following item could not be loaded:\nHMP_REF: " + this.HMPNumber + ", NAME: " + this.EQName;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\filehandlers\HMPReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */