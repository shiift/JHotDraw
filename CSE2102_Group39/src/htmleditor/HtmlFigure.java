package htmleditor;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.Map;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.AttributedFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.geom.Geom;

public abstract class HtmlFigure extends AttributedFigure {

    private Rectangle2D.Double rectangle;
    
    /** Creates a new instance. */
    public HtmlFigure() {
        this(0, 0, 0, 0);
    }
    
    public HtmlFigure(double x, double y, double width, double height) {
        rectangle = new Rectangle2D.Double(x, y, width, height);
        /*
        FILL_COLOR.set(this, Color.white);
        STROKE_COLOR.set(this, Color.black);
         */
    }
    
    // DRAWING
    // SHAPE AND BOUNDS
    // ATTRIBUTES
    // EDITING
// CONNECTING
    // COMPOSITE FIGURES
    // CLONING
    // EVENT HANDLING
    public Rectangle2D.Double getBounds() {
        Rectangle2D.Double bounds = (Rectangle2D.Double) rectangle.clone();
        return bounds;
    }
    
    protected void drawFill(Graphics2D g) {
        Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
            double grow = AttributeKeys.getPerpendicularFillGrowth(this);
            Geom.grow(r, grow, grow);
        g.fill(r);
    }
    
    protected void drawStroke(Graphics2D g) {
        Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
        double grow = AttributeKeys.getPerpendicularDrawGrowth(this);
       Geom.grow(r, grow, grow);
       
        g.draw(r);
    }
    
    public Rectangle2D.Double getFigureDrawBounds() {
        Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
        double grow = AttributeKeys.getPerpendicularHitGrowth(this) + 1d;
        Geom.grow(r, grow, grow);
        return r;
    }
    /**
     * Checks if a Point2D.Double is inside the figure.
     */
    public boolean contains(Point2D.Double p) {
        Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
        double grow = AttributeKeys.getPerpendicularHitGrowth(this) + 1d;
        Geom.grow(r, grow, grow);
        return r.contains(p);
    }
    
    public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
        rectangle.x = Math.min(anchor.x, lead.x);
        rectangle.y = Math.min(anchor.y , lead.y);
        rectangle.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
        rectangle.height = Math.max(0.1, Math.abs(lead.y - anchor.y));
    }
    /**
     * Moves the Figure to a new location.
     * @param tx the transformation matrix.
     */
    public void basicTransform(AffineTransform tx) {
        Point2D.Double anchor = getStartPoint();
        Point2D.Double lead = getEndPoint();
        basicSetBounds(
                (Point2D.Double) tx.transform(anchor, anchor),
                (Point2D.Double) tx.transform(lead, lead)
                );
    }
    
    public HtmlFigure clone() {
    	HtmlFigure that = (HtmlFigure) super.clone();
        that.rectangle = (Rectangle2D.Double) this.rectangle.clone();
        return that;
    }
    public void restoreTo(Object geometry) {
        Rectangle2D.Double r = (Rectangle2D.Double) geometry;
        rectangle.x = r.x;
        rectangle.y = r.y;
        rectangle.width = r.width;
        rectangle.height = r.height;
    }
    
    public Object getRestoreData() {
        return rectangle.clone();
    }
    

}
