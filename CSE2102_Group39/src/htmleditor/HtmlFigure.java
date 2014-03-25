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
    
    private LinkedList<HtmlFigure> figureList;
    private HtmlFigure parent;
    private boolean isData;
    private String tag;
    private String name;
    
    /** Creates a new instance. */
    public HtmlFigure() {
        this(0, 0, 0, 0);
    }
    
    public HtmlFigure(double x, double y, double width, double height) {
    	super(x, y, width, height);
    }
    
    @Override public int getLayer() {
        return -1; // stay below ConnectionFigures
    }
    
    //Figure list methods
    public LinkedList<HtmlFigure> getObjectList(){
		return figureList;
	}
    public void addHtmlObject(HtmlFigure object){
		figureList.add(object);
	}
	
	public HtmlFigure removeHtmlFigure(int location){
		return figureList.remove(location);
	}
	
	
	// parent methods
	public HtmlFigure getParent() {
		return parent;
	}
	
	public void setParent(HtmlFigure parent) {
		this.parent = parent;
	}
	
	
	//isData methods
	public boolean getData(){
		return isData;
	}
	
	public void setData(boolean val){
		isData = val;
	}
	
	
	// Tag methods
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	// Name methods
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
