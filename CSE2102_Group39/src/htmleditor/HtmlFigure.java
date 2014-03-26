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
    private HtmlFigure parent = Global.topParent;
    private boolean isData;
    
    /** Creates a new instance. */
    public HtmlFigure() {
        this(0, 0, 0, 0);
    }
    
    public HtmlFigure(double x, double y, double width, double height) {
    	super(x, y, width, height);
    }
    
    public HtmlFigure clone() {
        HtmlFigure that = (HtmlFigure) super.clone();
        return that;
    }
    
    public void basicTransform(AffineTransform tx) {
    	super.basicTransform(tx);
//    	boolean changed = false;
//    	java.awt.geom.Rectangle2D.Double pLoc = parent.getDrawBounds();
//    	double x = this.getDrawBounds().x;
//    	double y = this.getDrawBounds().y;
//    	double w = this.getDrawBounds().width;
//    	double h = this.getDrawBounds().height;
//    	double xf = this.getDrawBounds().x;
//    	double yf = this.getDrawBounds().y;
//    	double wf = this.getDrawBounds().width;
//    	double hf = this.getDrawBounds().height;
//    	double x2 = this.getDrawBounds().width+x;
//    	double y2 = this.getDrawBounds().height+y;
//    	double pw = pLoc.x+pLoc.width;
//    	double ph = pLoc.y+pLoc.height;
//    	if (x<pLoc.x){
//    		x = pLoc.x;
//    		changed = true;
//    	}
//    	if (y<pLoc.y){
//    		y = pLoc.y;
//    		changed = true;
//    	}
//    	if (x2>pw){
//    		w = pw;
//    		changed = true;
//    	}
//    	if (y2>ph){
//    		h = ph;
//    		changed = true;
//    	}
//
//    	if(changed){
//    		this.setBounds(new java.awt.geom.Rectangle2D.Double(x, y, w, h));
//    	}else{
//    		super.basicTransform(tx);
//    	}
//    	System.out.println("C" + "  " + x + "  " + y);
//    	System.out.println("P" + "  " + pLoc.x + "  " + pLoc.y);
    	
    	
    }

    
    /*
     * Make something like this but so the object attached is the highest layer.
     * 
    @Override public int getLayer() {
        return -1; // stay below ConnectionFigures
    }
    */
    
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
	
	
	//isData methods
	public boolean getData(){
		return isData;
	}
	
	public void setData(boolean val){
		isData = val;
	}
}
