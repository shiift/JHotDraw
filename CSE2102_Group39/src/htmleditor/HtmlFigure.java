package htmleditor;

import static org.jhotdraw.draw.AttributeKeys.DECORATOR_INSETS;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jhotdraw.draw.AbstractConnector;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.AttributedFigure;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.ConnectionHandle;
import org.jhotdraw.draw.Connector;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.LineConnectionFigure;
import org.jhotdraw.draw.LocatorConnector;
import org.jhotdraw.draw.MoveHandle;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.draw.RelativeLocator;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.geom.Insets2DDouble;
import org.jhotdraw.samples.net.figures.NodeFigure;

import ch.randelshofer.quaqua.util.ResourceBundleUtil;

public class HtmlFigure extends RectangleFigure {
    private Rectangle2D.Double rectangle;
    
    private LinkedList<AbstractConnector> connectors;
    private static LocatorConnector north;
    private static LocatorConnector south;
    private static LocatorConnector east;
    private static LocatorConnector west;
    
    /** Creates a new instance. */
    public HtmlFigure() {
        this(0, 0, 0, 0);
    }
    
    public HtmlFigure(double x, double y, double width, double height) {
        rectangle = new Rectangle2D.Double(x, y, width, height);
        createConnectors();
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.samples.net.Labels");
    }
    
    private void createConnectors() {
        connectors = new LinkedList<AbstractConnector>();
        connectors.add(new LocatorConnector(this, new RelativeLocator(0.5,0.5)));
        for (AbstractConnector c : connectors) {
            c.setVisible(true);
        }
    }
    
    @Override public Collection<Handle> createHandles(int detailLevel) {
    	java.util.List<Handle> handles = (List<Handle>) super.createHandles(detailLevel);
        if (detailLevel == 0) {
        	LineConnectionFigure lcf = new LineConnectionFigure();
        	lcf.setAttribute(AttributeKeys.STROKE_COLOR, Color.red);
            handles.add(new ConnectionHandle(this, RelativeLocator.north(), lcf));
        }
        return handles;
    }
    
    @Override public Connector findConnector(Point2D.Double p, ConnectionFigure figure) {
        // return closest connector
        double min = java.lang.Double.MAX_VALUE;
        Connector closest = null;
        for (Connector c : connectors) {
            Point2D.Double p2 = Geom.center(c.getBounds());
            double d = Geom.length2(p.x, p.y, p2.x, p2.y);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }
    
    @Override public Connector findCompatibleConnector(Connector c, boolean isStart) {
        return connectors.getFirst();
    }
    
    public HtmlFigure clone() {
    	HtmlFigure that = (HtmlFigure) super.clone();
        that.createConnectors();
        return that;
    }
    
    @Override protected void drawConnectors(Graphics2D g) {
        for (Connector c : connectors) {
            c.draw(g);
        }
    }
    
    @Override public int getLayer() {
        return -1; // stay below ConnectionFigures
    }
}
