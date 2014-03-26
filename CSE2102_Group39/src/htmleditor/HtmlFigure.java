package htmleditor;


import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import org.jhotdraw.draw.RectangleFigure;

public class HtmlFigure extends RectangleFigure {
    
	private Rectangle2D.Double figure;
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
    
    public HtmlFigure clone() {
        HtmlFigure that = (HtmlFigure) super.clone();
        return that;
    }
    
    /*
     * Make something like this but so the object attached is the hihgest layer.
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
