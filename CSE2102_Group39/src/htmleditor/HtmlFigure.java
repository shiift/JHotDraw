package htmleditor;


import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.RectangleFigure;

public class HtmlFigure extends RectangleFigure {
    
    private LinkedList<HtmlFigure> figureList;
    private HtmlFigure parent = Global.topParent;
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
    
    public HtmlFigure clone() {
        HtmlFigure that = (HtmlFigure) super.clone();
        return that;
    }
    
    public void basicTransform(AffineTransform tx) {
    	
//    	Point2D.Double refPoint = new Point2D.Double(this.getDrawBounds().x-1,this.getDrawBounds().y-1);
//    	Point2D.Double Point = new Point2D.Double(this.getDrawBounds().x,this.getDrawBounds().y);
//    	HtmlFigure _parent = (HtmlFigure) super.findFigureInside(refPoint);
//    	System.out.println("point" + "    " +Point);
//    	System.out.println("refpoint" + "    " +refPoint);
//    	System.out.println(_parent);
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
