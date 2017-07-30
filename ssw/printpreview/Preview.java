/*     */ package ssw.printpreview;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Pageable;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import javax.swing.JComponent;
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
/*     */ class Preview
/*     */   extends JComponent
/*     */ {
/*     */   private static final int DEFAULT_PREVIEW_SIZE = 1024;
/*     */   private static final double MINIMUM_ZOOM_FACTOR = 0.1D;
/*     */   private Dimension viewportSize;
/*     */   protected Pageable pageable;
/*     */   
/*     */   public Preview(Pageable pageable, double zoom, Dimension viewport)
/*     */   {
/*  40 */     this.viewportSize = viewport;
/*  41 */     setPageable(pageable);
/*     */   }
/*     */   
/*     */   public Preview(Pageable pageable, double zoom) {
/*  45 */     this(pageable, zoom, new Dimension(1024, 768));
/*     */   }
/*     */   
/*     */   protected void paintPaper(Graphics g, PageFormat format) {
/*  49 */     g.setColor(Color.white);
/*  50 */     g.fillRect(0, 0, (int)format.getWidth(), (int)format.getHeight());
/*  51 */     g.setColor(Color.black);
/*  52 */     g.drawRect(0, 0, (int)format.getWidth() - 1, (int)format.getHeight() - 1);
/*     */   }
/*     */   
/*     */   public void paint(Graphics g) {
/*  56 */     Graphics2D g2d = (Graphics2D)g;
/*  57 */     g2d.scale(this.zoom, this.zoom);
/*     */     try {
/*  59 */       PageFormat format = this.pageable.getPageFormat(this.index);
/*  60 */       Printable printable = this.pageable.getPrintable(this.index);
/*  61 */       paintPaper(g, format);
/*  62 */       printable.print(g, format, 0);
/*     */     }
/*     */     catch (PrinterException e) {}catch (IndexOutOfBoundsException e) {}
/*     */   }
/*     */   
/*     */   public void setPageable(Pageable pageable)
/*     */   {
/*  69 */     this.pageable = pageable;
/*  70 */     PageFormat format = pageable.getPageFormat(this.index);
/*  71 */     if (this.zoom == 0.0D) {
/*  72 */       this.zoom = (this.viewportSize.width / format.getWidth());
/*     */     } else
/*  74 */       this.zoom = this.zoom;
/*  75 */     resize();
/*     */   }
/*     */   
/*     */   public void moveIndex(int indexStep) {
/*  79 */     int newIndex = this.index + indexStep;
/*     */     try {
/*  81 */       Printable printable = this.pageable.getPrintable(newIndex);
/*  82 */       resize();
/*  83 */       this.index = newIndex;
/*     */     }
/*     */     catch (IndexOutOfBoundsException ignored) {}
/*     */   }
/*     */   
/*     */   public void changeZoom(double zoom) {
/*  89 */     this.zoom = Math.max(0.1D, this.zoom + zoom);
/*  90 */     resize();
/*     */   }
/*     */   
/*     */   public void setZoom(double zoom) {
/*  94 */     this.zoom = Math.max(0.1D, zoom);
/*  95 */     resize();
/*     */   }
/*     */   
/*     */   public String getZoom() {
/*  99 */     return this.zoom + "";
/*     */   }
/*     */   
/*     */   public double getWidthZoom() {
/* 103 */     PageFormat format = this.pageable.getPageFormat(this.index);
/* 104 */     return this.viewportSize.width / format.getWidth();
/*     */   }
/*     */   
/*     */   public double getHeightZoom() {
/* 108 */     PageFormat format = this.pageable.getPageFormat(this.index);
/* 109 */     return this.viewportSize.height / format.getHeight();
/*     */   }
/*     */   
/*     */   public void resize() {
/* 113 */     PageFormat format = this.pageable.getPageFormat(this.index);
/* 114 */     int size = (int)Math.max(format.getWidth() * this.zoom, format.getHeight() * this.zoom);
/* 115 */     setPreferredSize(new Dimension(size, size));
/* 116 */     revalidate();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 120 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public void setViewportSize(Dimension viewportSize) {
/* 124 */     this.viewportSize = viewportSize;
/*     */   }
/*     */   
/*     */ 
/* 128 */   protected int index = 0;
/* 129 */   protected double zoom = 0.0D;
/*     */ }


/* Location:              C:\GDrive\SSW.jar!\ssw\printpreview\Preview.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */