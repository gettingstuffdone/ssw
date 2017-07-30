/*     */ package ssw.gui;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Line2D.Double;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JPanel;
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
/*     */ public class DamageChart
/*     */   extends JPanel
/*     */ {
/*  40 */   private int GridX = 1;
/*  41 */   private int GridY = 1;
/*  42 */   private Vector charts = new Vector();
/*  43 */   private Vector colors = new Vector();
/*  44 */   private boolean TextView = false;
/*     */   
/*     */   public DamageChart() {
/*  47 */     setBackground(Color.WHITE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SetGridSize(int x, int y)
/*     */   {
/*  60 */     this.GridX = x;
/*  61 */     this.GridY = y;
/*  62 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void AddChart(int[] chart, Color ccolor)
/*     */   {
/*  75 */     this.charts.add(chart);
/*  76 */     this.colors.add(ccolor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void ClearCharts()
/*     */   {
/*  84 */     this.charts.clear();
/*  85 */     this.colors.clear();
/*     */   }
/*     */   
/*     */   public void SetTextView(boolean b) {
/*  89 */     this.TextView = b;
/*  90 */     repaint();
/*     */   }
/*     */   
/*     */   public void paintComponent(Graphics g)
/*     */   {
/*  95 */     super.paintComponent(g);
/*     */     
/*  97 */     if (this.TextView) {
/*  98 */       DrawText((Graphics2D)g);
/*     */     } else {
/* 100 */       DrawGrid((Graphics2D)g);
/* 101 */       DrawCharts((Graphics2D)g);
/*     */     }
/*     */   }
/*     */   
/*     */   private void DrawCharts(Graphics2D g) {
/* 106 */     int dx = getSize().width;
/* 107 */     int dy = getSize().height;
/* 108 */     double offX = dx / this.GridX;
/* 109 */     double offY = dy / this.GridY;
/*     */     
/* 111 */     g.setStroke(new BasicStroke(2.0F));
/* 112 */     for (int i = 0; i < this.charts.size(); i++) {
/* 113 */       int[] chart = (int[])this.charts.get(i);
/* 114 */       g.setColor((Color)this.colors.get(i));
/* 115 */       for (int j = 1; j < chart.length; j++) {
/* 116 */         if (chart[j] == 0) {
/* 117 */           g.draw(new Line2D.Double((j - 1) * offX, dy - chart[(j - 1)] * offY, (j - 1) * offX, dy - chart[j] * offY));
/*     */         }
/* 119 */         else if (chart[j] < chart[(j - 1)]) {
/* 120 */           g.draw(new Line2D.Double((j - 1) * offX, dy - chart[(j - 1)] * offY, (j - 1) * offX, dy - chart[j] * offY));
/* 121 */           g.draw(new Line2D.Double((j - 1) * offX, dy - chart[j] * offY, j * offX, dy - chart[j] * offY));
/*     */         } else {
/* 123 */           g.draw(new Line2D.Double((j - 1) * offX, dy - chart[(j - 1)] * offY, j * offX, dy - chart[j] * offY));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void DrawGrid(Graphics2D g)
/*     */   {
/* 132 */     int dx = getSize().width;
/* 133 */     int dy = getSize().height;
/* 134 */     double offX = dx / this.GridX;
/* 135 */     double offY = dy / this.GridY;
/* 136 */     int linemodulo = this.GridY / 10;
/* 137 */     if (linemodulo <= 0) { linemodulo = 1;
/*     */     }
/* 139 */     g.setColor(Color.LIGHT_GRAY);
/*     */     
/* 141 */     for (int i = 1; i < this.GridX; i++) {
/* 142 */       g.draw(new Line2D.Double(offX * i, 0.0D, offX * i, dy));
/* 143 */       if (i % 5 == 0) {
/* 144 */         g.setColor(Color.BLACK);
/* 145 */         g.drawString("" + i, (int)offX * i, dy - 4);
/* 146 */         g.setColor(Color.LIGHT_GRAY);
/*     */       }
/*     */     }
/* 149 */     for (int i = 1; i < this.GridY; i++) {
/* 150 */       if (i % linemodulo == 0) {
/* 151 */         g.draw(new Line2D.Double(0.0D, dy - offY * i, dx, dy - offY * i));
/* 152 */         g.setColor(Color.BLACK);
/* 153 */         g.drawString("" + i, 1, (int)(dy - offY * i + 5.0D));
/* 154 */         g.setColor(Color.LIGHT_GRAY);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void DrawText(Graphics2D g) {
/* 160 */     Vector<Group> curgroups = new Vector();
/* 161 */     int CurX = 10;
/*     */     
/* 163 */     for (int i = 0; i < this.charts.size(); i++) {
/* 164 */       int[] chart = (int[])this.charts.get(i);
/* 165 */       g.setColor((Color)this.colors.get(i));
/* 166 */       Group newGroup = null;
/* 167 */       for (int j = 1; j < chart.length; j++)
/*     */       {
/* 169 */         if (newGroup == null) {
/* 170 */           newGroup = new Group(null);
/* 171 */           newGroup.StartRng = j;
/* 172 */           newGroup.EndRng = j;
/* 173 */           newGroup.Dmg = chart[j];
/* 174 */           curgroups.add(newGroup);
/*     */         } else {
/* 176 */           Group group = new Group(null);
/* 177 */           group.StartRng = j;
/* 178 */           group.EndRng = j;
/* 179 */           group.Dmg = chart[j];
/* 180 */           if (!newGroup.Combine(group)) {
/* 181 */             curgroups.add(group);
/* 182 */             newGroup = group;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 187 */       int CurY = 15;
/* 188 */       for (int j = 0; j < curgroups.size(); j++) {
/* 189 */         int dmg = ((Group)curgroups.get(j)).Dmg;
/* 190 */         int srng = ((Group)curgroups.get(j)).StartRng;
/* 191 */         int erng = ((Group)curgroups.get(j)).EndRng;
/* 192 */         if (dmg > 0) {
/* 193 */           if (srng == erng) {
/* 194 */             g.drawString("Range " + srng + ": " + dmg, CurX, CurY);
/*     */           } else {
/* 196 */             g.drawString("Ranges " + srng + " to " + erng + ": " + dmg, CurX, CurY);
/*     */           }
/* 198 */           CurY += 11;
/*     */         }
/*     */       }
/* 201 */       CurX += 150;
/* 202 */       curgroups.clear();
/*     */     } }
/*     */   
/*     */   private class Group { private Group() {}
/*     */     
/* 207 */     public int Dmg = 0; public int EndRng = 0; public int StartRng = 0;
/*     */     
/* 209 */     public boolean Combine(Group g) { if (this.Dmg == g.Dmg)
/*     */       {
/* 211 */         if (this.StartRng <= g.StartRng)
/*     */         {
/* 213 */           if (this.EndRng <= g.EndRng)
/*     */           {
/* 215 */             this.EndRng = g.EndRng;
/* 216 */             return true;
/*     */           }
/* 218 */           return false;
/*     */         }
/*     */         
/* 221 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 225 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\gui\DamageChart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */