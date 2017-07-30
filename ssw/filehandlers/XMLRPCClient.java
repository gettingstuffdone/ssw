/*     */ package ssw.filehandlers;
/*     */ 
/*     */ import common.CommonTools;
/*     */ import components.Mech;
/*     */ import filehandlers.FileCommon;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLRPCClient
/*     */ {
/*     */   private String Server;
/*     */   
/*     */   public XMLRPCClient(String server)
/*     */   {
/*  51 */     this.Server = server;
/*     */   }
/*     */   
/*     */   public String[][] GetArmoryList(int UserID) throws Exception {
/*  55 */     String[][] retval = (String[][])null;
/*     */     
/*  57 */     String send = "<?xml version=\"1.0\" encoding =\"UTF-8\"?>\r\n";
/*  58 */     send = send + "<methodCall>\r\n";
/*  59 */     send = send + "\t<methodName>Member_Armories</methodName>\r\n";
/*  60 */     send = send + "\t<params>\r\n";
/*  61 */     send = send + "\t\t<param>\r\n";
/*  62 */     send = send + "\t\t\t<value>\r\n";
/*  63 */     send = send + "\t\t\t\t<string>" + UserID + "</string>\r\n";
/*  64 */     send = send + "\t\t\t</value>\r\n";
/*  65 */     send = send + "\t\t</param>\r\n";
/*  66 */     send = send + "\t</params>\r\n";
/*  67 */     send = send + "</methodCall>\r\n";
/*     */     
/*  69 */     Document dc = MethodCall(send);
/*  70 */     Armory[] AList = ExtractArmories(dc);
/*     */     
/*  72 */     retval = new String[AList.length][2];
/*  73 */     for (int i = 0; i < AList.length; i++) {
/*  74 */       retval[i][0] = AList[i].Name;
/*  75 */       retval[i][1] = AList[i].ID;
/*     */     }
/*  77 */     return retval;
/*     */   }
/*     */   
/*     */   public int GetMemberID(String UserName, String Password) throws Exception {
/*  81 */     int retval = -1;
/*     */     
/*  83 */     String send = "<?xml version=\"1.0\" encoding =\"UTF-8\"?>\r\n";
/*  84 */     send = send + "<methodCall>\r\n";
/*  85 */     send = send + "\t<methodName>Member_Validate</methodName>\r\n";
/*  86 */     send = send + "\t<params>\r\n";
/*  87 */     send = send + "\t\t<param>\r\n";
/*  88 */     send = send + "\t\t\t<value>\r\n";
/*  89 */     send = send + "\t\t\t\t<string>" + UserName + "</string>\r\n";
/*  90 */     send = send + "\t\t\t</value>\r\n";
/*  91 */     send = send + "\t\t</param>\r\n";
/*  92 */     send = send + "\t\t<param>\r\n";
/*  93 */     send = send + "\t\t\t<value>\r\n";
/*  94 */     send = send + "\t\t\t\t<string>" + Password + "</string>\r\n";
/*  95 */     send = send + "\t\t\t</value>\r\n";
/*  96 */     send = send + "\t\t</param>\r\n";
/*  97 */     send = send + "\t</params>\r\n";
/*  98 */     send = send + "</methodCall>\r\n";
/*     */     
/* 100 */     Document dc = MethodCall(send);
/* 101 */     retval = ExtractMemberID(dc);
/*     */     
/* 103 */     return retval;
/*     */   }
/*     */   
/*     */   public String[] GetTROImages(String date) throws Exception {
/* 107 */     String[] retval = null;
/*     */     
/* 109 */     String send = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
/* 110 */     send = send + "<methodCall>\r\n";
/* 111 */     send = send + "\t<methodName>TRO_Images</methodName>\r\n";
/* 112 */     send = send + "\t<params>\r\n";
/* 113 */     send = send + "\t\t<param>\r\n";
/* 114 */     send = send + "\t\t\t<value>\r\n";
/* 115 */     send = send + "\t\t\t\t<string>0</string>\r\n";
/* 116 */     send = send + "\t\t\t</value>\r\n";
/* 117 */     send = send + "\t\t</param>\r\n";
/* 118 */     send = send + "\t\t<param>\r\n";
/* 119 */     send = send + "\t\t\t<value>\r\n";
/* 120 */     send = send + "\t\t\t\t<string>battlemech</string>\r\n";
/* 121 */     send = send + "\t\t\t</value>\r\n";
/* 122 */     send = send + "\t\t</param>\r\n";
/* 123 */     send = send + "\t\t<param>\r\n";
/* 124 */     send = send + "\t\t\t<value>\r\n";
/* 125 */     send = send + "\t\t\t\t<string>" + date + "</string>\r\n";
/* 126 */     send = send + "\t\t\t</value>\r\n";
/* 127 */     send = send + "\t\t</param>\r\n";
/* 128 */     send = send + "\t</params>\r\n";
/* 129 */     send = send + "</methodCall>\r\n";
/*     */     
/* 131 */     Document dc = MethodCall(send);
/* 132 */     retval = ExtractImageList(dc);
/*     */     
/* 134 */     return retval;
/*     */   }
/*     */   
/*     */   public String[] GetUserImages(int UserID) throws Exception {
/* 138 */     String[] retval = null;
/*     */     
/* 140 */     String send = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
/* 141 */     send = send + "<methodCall>\r\n";
/* 142 */     send = send + "\t<methodName>TRO_Images_User</methodName>\r\n";
/* 143 */     send = send + "\t<params>\r\n";
/* 144 */     send = send + "\t\t<param>\r\n";
/* 145 */     send = send + "\t\t\t<value>\r\n";
/* 146 */     send = send + "\t\t\t\t<string>" + UserID + "</string>\r\n";
/* 147 */     send = send + "\t\t\t</value>\r\n";
/* 148 */     send = send + "\t\t</param>\r\n";
/* 149 */     send = send + "\t\t<param>\r\n";
/* 150 */     send = send + "\t\t\t<value>\r\n";
/* 151 */     send = send + "\t\t\t\t<string>battlemech</string>\r\n";
/* 152 */     send = send + "\t\t\t</value>\r\n";
/* 153 */     send = send + "\t\t</param>\r\n";
/* 154 */     send = send + "\t</params>\r\n";
/* 155 */     send = send + "</methodCall>\r\n";
/*     */     
/* 157 */     Document dc = MethodCall(send);
/* 158 */     retval = ExtractImageList(dc);
/*     */     
/* 160 */     return retval;
/*     */   }
/*     */   
/*     */   public String PostToSolaris7(String UserID, String ArmoryID, String HTML, String ImageID, String TROYear, Mech m) throws Exception {
/* 164 */     String retval = "";
/*     */     
/* 166 */     String send = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
/* 167 */     send = send + "<methodCall>\r\n";
/* 168 */     send = send + "\t<methodName>TRO_BattleMech_Post2010</methodName>\r\n";
/* 169 */     send = send + "\t<params>\r\n";
/* 170 */     send = send + "\t\t<param>\r\n";
/* 171 */     send = send + "\t\t\t<value>\r\n";
/* 172 */     send = send + "\t\t\t\t<string>" + m.GetSolaris7ID() + "</string>\r\n";
/* 173 */     send = send + "\t\t\t</value>\r\n";
/* 174 */     send = send + "\t\t</param>\r\n";
/* 175 */     send = send + "\t\t<param>\r\n";
/* 176 */     send = send + "\t\t\t<value>\r\n";
/* 177 */     send = send + "\t\t\t\t<string>" + ArmoryID + "</string>\r\n";
/* 178 */     send = send + "\t\t\t</value>\r\n";
/* 179 */     send = send + "\t\t</param>\r\n";
/* 180 */     send = send + "\t\t<param>\r\n";
/* 181 */     send = send + "\t\t\t<value>\r\n";
/* 182 */     send = send + "\t\t\t\t<string>" + UserID + "</string>\r\n";
/* 183 */     send = send + "\t\t\t</value>\r\n";
/* 184 */     send = send + "\t\t</param>\r\n";
/* 185 */     send = send + "\t\t<param>\r\n";
/* 186 */     send = send + "\t\t\t<value>\r\n";
/* 187 */     send = send + "\t\t\t\t<string>" + m.GetName() + "</string>\r\n";
/* 188 */     send = send + "\t\t\t</value>\r\n";
/* 189 */     send = send + "\t\t</param>\r\n";
/* 190 */     send = send + "\t\t<param>\r\n";
/* 191 */     send = send + "\t\t\t<value>\r\n";
/* 192 */     send = send + "\t\t\t\t<string>" + m.GetModel() + "</string>\r\n";
/* 193 */     send = send + "\t\t\t</value>\r\n";
/* 194 */     send = send + "\t\t</param>\r\n";
/* 195 */     send = send + "\t\t<param>\r\n";
/* 196 */     send = send + "\t\t\t<value>\r\n";
/* 197 */     send = send + "\t\t\t\t<string>" + m.GetTonnage() + "</string>\r\n";
/* 198 */     send = send + "\t\t\t</value>\r\n";
/* 199 */     send = send + "\t\t</param>\r\n";
/* 200 */     send = send + "\t\t<param>\r\n";
/* 201 */     send = send + "\t\t\t<value>\r\n";
/* 202 */     if (m.IsQuad()) {
/* 203 */       send = send + "\t\t\t\t<string>Quad</string>\r\n";
/*     */     } else {
/* 205 */       send = send + "\t\t\t\t<string>Biped</string>\r\n";
/*     */     }
/* 207 */     send = send + "\t\t\t</value>\r\n";
/* 208 */     send = send + "\t\t</param>\r\n";
/* 209 */     send = send + "\t\t<param>\r\n";
/* 210 */     send = send + "\t\t\t<value>\r\n";
/* 211 */     switch (m.GetBaseTechbase()) {
/*     */     case 0: 
/* 213 */       send = send + "\t\t\t\t<string>Inner Sphere</string>\r\n";
/* 214 */       break;
/*     */     case 1: 
/* 216 */       send = send + "\t\t\t\t<string>Clan</string>\r\n";
/* 217 */       break;
/*     */     case 2: 
/* 219 */       send = send + "\t\t\t\t<string>Mixed</string>\r\n";
/*     */     }
/*     */     
/* 222 */     send = send + "\t\t\t</value>\r\n";
/* 223 */     send = send + "\t\t</param>\r\n";
/* 224 */     send = send + "\t\t<param>\r\n";
/* 225 */     send = send + "\t\t\t<value>\r\n";
/* 226 */     send = send + "\t\t\t\t<string>" + m.GetDeprecatedLevel() + "</string>\r\n";
/* 227 */     send = send + "\t\t\t</value>\r\n";
/* 228 */     send = send + "\t\t</param>\r\n";
/* 229 */     send = send + "\t\t<param>\r\n";
/* 230 */     send = send + "\t\t\t<value>\r\n";
/* 231 */     send = send + "\t\t\t\t<string>" + CommonTools.GetRulesLevelString(m.GetRulesLevel()) + "</string>\r\n";
/* 232 */     send = send + "\t\t\t</value>\r\n";
/* 233 */     send = send + "\t\t</param>\r\n";
/* 234 */     send = send + "\t\t<param>\r\n";
/* 235 */     send = send + "\t\t\t<value>\r\n";
/* 236 */     send = send + "\t\t\t\t<string>" + CommonTools.DecodeEra(m.GetEra()) + "</string>\r\n";
/* 237 */     send = send + "\t\t\t</value>\r\n";
/* 238 */     send = send + "\t\t</param>\r\n";
/* 239 */     send = send + "\t\t<param>\r\n";
/* 240 */     send = send + "\t\t\t<value>\r\n";
/* 241 */     send = send + "\t\t\t\t<string>" + TROYear + "</string>\r\n";
/* 242 */     send = send + "\t\t\t</value>\r\n";
/* 243 */     send = send + "\t\t</param>\r\n";
/* 244 */     send = send + "\t\t<param>\r\n";
/* 245 */     send = send + "\t\t\t<value>\r\n";
/* 246 */     send = send + "\t\t\t\t<string>" + m.GetYear() + "</string>\r\n";
/* 247 */     send = send + "\t\t\t</value>\r\n";
/* 248 */     send = send + "\t\t</param>\r\n";
/* 249 */     send = send + "\t\t<param>\r\n";
/* 250 */     send = send + "\t\t\t<value>\r\n";
/* 251 */     send = send + "\t\t\t\t<string>" + ImageID + "</string>\r\n";
/* 252 */     send = send + "\t\t\t</value>\r\n";
/* 253 */     send = send + "\t\t</param>\r\n";
/* 254 */     send = send + "\t\t<param>\r\n";
/* 255 */     send = send + "\t\t\t<value>\r\n";
/* 256 */     send = send + "\t\t\t\t<string>" + FileCommon.EncodeFluff(HTML) + "</string>\r\n";
/* 257 */     send = send + "\t\t\t</value>\r\n";
/* 258 */     send = send + "\t\t</param>\r\n";
/* 259 */     send = send + "\t</params>\r\n";
/* 260 */     send = send + "</methodCall>\r\n";
/*     */     
/* 262 */     Document dc = MethodCall(send);
/* 263 */     retval = ExtractMechID(dc);
/*     */     
/* 265 */     return retval;
/*     */   }
/*     */   
/*     */   private HttpURLConnection GetConnection() throws Exception {
/*     */     try {
/* 270 */       URL u = new URL(this.Server);
/* 271 */       URLConnection uc = u.openConnection();
/* 272 */       HttpURLConnection connection = (HttpURLConnection)uc;
/* 273 */       connection.setDoOutput(true);
/* 274 */       connection.setDoInput(true);
/* 275 */       connection.setRequestMethod("POST");
/* 276 */       return connection;
/*     */     } catch (Exception e) {
/* 278 */       throw e;
/*     */     }
/*     */   }
/*     */   
/*     */   private Document MethodCall(String send) throws Exception {
/* 283 */     HttpURLConnection connection = null;
/* 284 */     OutputStream out = null;
/* 285 */     OutputStreamWriter wout = null;
/* 286 */     InputStream in = null;
/* 287 */     Document dc = null;
/*     */     try {
/* 289 */       connection = GetConnection();
/* 290 */       out = connection.getOutputStream();
/* 291 */       wout = new OutputStreamWriter(out, "UTF-8");
/* 292 */       wout.write("RPCxml=" + URLEncoder.encode(send, "UTF-8"));
/*     */       
/* 294 */       wout.flush();
/* 295 */       out.close();
/*     */       
/* 297 */       in = connection.getInputStream();
/* 298 */       dc = GetXML(in);
/* 299 */       if (dc == null) {
/* 300 */         if (in != null) in.close();
/* 301 */         if (out != null) out.close();
/* 302 */         if (connection != null) connection.disconnect();
/* 303 */         throw new Exception("An error occured with the server:\nNo data was returned.\nPlease try the request again later.");
/*     */       }
/*     */       
/* 306 */       in.close();
/* 307 */       out.close();
/* 308 */       connection.disconnect();
/*     */     } catch (Exception e) {
/* 310 */       if (in != null) in.close();
/* 311 */       if (out != null) out.close();
/* 312 */       if (connection != null) connection.disconnect();
/* 313 */       throw e;
/*     */     }
/* 315 */     return dc;
/*     */   }
/*     */   
/*     */   private Document GetXML(InputStream is) throws Exception
/*     */   {
/* 320 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 321 */     DocumentBuilder db = dbf.newDocumentBuilder();
/* 322 */     Document retval = db.parse(is);
/* 323 */     if (retval.hasChildNodes()) {
/* 324 */       return retval;
/*     */     }
/* 326 */     return null;
/*     */   }
/*     */   
/*     */   private int ExtractMemberID(Document d) throws Exception
/*     */   {
/* 331 */     int retval = -1;
/* 332 */     NodeList nl = d.getElementsByTagName("member");
/* 333 */     if (nl.getLength() < 1) {
/* 334 */       if (d.getElementsByTagName("fault").getLength() > 0) {
/* 335 */         throw new Exception("A fault was returned by the server and no Member ID was returned.");
/*     */       }
/* 337 */       throw new Exception("Could not find a list of XML members while retrieving MemberID.\nDocument contains no data.");
/*     */     }
/*     */     
/* 340 */     for (int i = 0; i < nl.getLength(); i++) {
/* 341 */       NodeList nodes = nl.item(i).getChildNodes();
/* 342 */       if ((nodes.item(0).getTextContent() != null) && 
/* 343 */         (nodes.item(0).getTextContent().equals("MemberID"))) {
/*     */         try {
/* 345 */           return Integer.parseInt(nodes.item(1).getTextContent());
/*     */         } catch (Exception e) {
/* 347 */           throw new Exception("Could not extract the Member ID.\nEither the username and password are incorrect\nor no meaningful data was returned.");
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 352 */     return retval;
/*     */   }
/*     */   
/*     */   private String ExtractMechID(Document d) throws Exception {
/* 356 */     String retval = "0";
/* 357 */     NodeList nl = d.getElementsByTagName("i4");
/* 358 */     if (nl.getLength() < 1) {
/* 359 */       if (d.getElementsByTagName("fault").getLength() > 0) {
/* 360 */         throw new Exception("A fault was returned by the server and the mech was not posted.");
/*     */       }
/* 362 */       throw new Exception("Could not find a list of XML members while retrieving the Mech ID.\nDocument contains no data.");
/*     */     }
/*     */     
/* 365 */     retval = nl.item(0).getTextContent();
/* 366 */     return retval;
/*     */   }
/*     */   
/*     */   private Armory[] ExtractArmories(Document d) throws Exception {
/* 370 */     Armory[] retval = null;
/* 371 */     Vector v = new Vector();
/*     */     
/* 373 */     NodeList nl = d.getElementsByTagName("struct");
/* 374 */     if (nl.getLength() < 1) {
/* 375 */       if (d.getElementsByTagName("fault").getLength() > 0) {
/* 376 */         throw new Exception("A fault was returned by the server and no Armories were returned.");
/*     */       }
/* 378 */       throw new Exception("Could not find a list of XML structs while retrieving Armories.\nDocument contains no data.");
/*     */     }
/*     */     
/*     */ 
/* 382 */     for (int i = 1; i < nl.getLength(); i++) {
/* 383 */       NodeList nodes = nl.item(i).getChildNodes();
/* 384 */       if (nodes.getLength() == 2)
/*     */       {
/* 386 */         String ID = "";
/* 387 */         String Name = "";
/* 388 */         Node member1 = nodes.item(0);
/* 389 */         Node member2 = nodes.item(1);
/* 390 */         if (!member1.getFirstChild().getTextContent().equals("ArmoryID")) break;
/* 391 */         ID = member1.getLastChild().getTextContent();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 396 */         if (!member2.getFirstChild().getTextContent().equals("Name")) break;
/* 397 */         Name = member2.getLastChild().getTextContent();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 402 */         Armory a = new Armory(null);
/* 403 */         a.ID = ID;
/* 404 */         a.Name = Name;
/* 405 */         v.add(a);
/*     */       }
/*     */     }
/* 408 */     if (v.size() > 0) {
/* 409 */       retval = new Armory[v.size()];
/* 410 */       for (int i = 0; i < v.size(); i++) {
/* 411 */         retval[i] = ((Armory)v.get(i));
/*     */       }
/*     */     }
/* 414 */     return retval;
/*     */   }
/*     */   
/*     */   private String[] ExtractImageList(Document d) throws Exception {
/* 418 */     Vector v = new Vector();
/* 419 */     NodeList nl = d.getElementsByTagName("struct");
/* 420 */     if (nl.getLength() < 1) {
/* 421 */       if (d.getElementsByTagName("fault").getLength() > 0) {
/* 422 */         throw new Exception("A fault was returned by the server and no Images were returned.");
/*     */       }
/* 424 */       throw new Exception("Could not find a list of XML structs while retrieving Images.\nDocument contains no data.");
/*     */     }
/*     */     
/*     */ 
/* 428 */     for (int i = 1; i < nl.getLength(); i++) {
/* 429 */       NodeList nodes = nl.item(i).getChildNodes();
/* 430 */       ImageID im = new ImageID(null);
/*     */       
/* 432 */       if (nodes.getLength() == 3)
/*     */       {
/* 434 */         Node member1 = nodes.item(0);
/* 435 */         Node member2 = nodes.item(1);
/* 436 */         Node member3 = nodes.item(2);
/*     */         
/* 438 */         if (!member1.getFirstChild().getTextContent().equals("ImageID")) break;
/* 439 */         im.ID = member1.getLastChild().getTextContent();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 445 */         if (!member2.getFirstChild().getTextContent().equals("Name")) break;
/* 446 */         im.Name = member2.getLastChild().getTextContent();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 452 */         if (!member3.getFirstChild().getTextContent().equals("URL")) break;
/* 453 */         im.URL = member3.getLastChild().getTextContent();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 459 */       v.add(im);
/*     */     }
/*     */     
/* 462 */     String[] retval = new String[v.size()];
/* 463 */     for (int i = 0; i < v.size(); i++) {
/* 464 */       retval[i] = ((ImageID)v.get(i)).toString();
/*     */     }
/* 466 */     return retval; }
/*     */   
/*     */   private class Armory { private Armory() {}
/*     */     
/* 470 */     public String ID = "";
/* 471 */     public String Name = "";
/*     */     
/*     */ 
/* 474 */     public String toString() { return this.ID + " " + this.Name; }
/*     */   }
/*     */   
/*     */   private class ImageID { private ImageID() {}
/*     */     
/* 479 */     public String ID = "";
/* 480 */     public String Name = "";
/* 481 */     public String URL = "";
/*     */     
/*     */     public String toString() {
/* 484 */       return this.ID + "," + this.Name + "," + this.URL;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\filehandlers\XMLRPCClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */