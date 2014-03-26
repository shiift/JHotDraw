package htmleditor;


import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.RectangleFigure;

public class HtmlFigure extends RectangleFigure {

	private LinkedList<HtmlFigure> figureList;
	private LinkedList<HtmlAttribute> attributeList;
	private HtmlFigure parent = Global.topParent;
	private boolean isData;
	private String tag;
	private String name;
	public boolean isTopParent = false;

	/** Creates a new instance. */
	public HtmlFigure() {
		this(0, 0, 0, 0);
	}

	public HtmlFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public HtmlFigure clone() {
		HtmlFigure that = (HtmlFigure) super.clone();
		Global.figureList.add(that);
		return that;
	}

	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
		Rectangle2D.Double pRectangle = parent.rectangle;

		rectangle.x = Math.min(anchor.x, lead.x);
		rectangle.y = Math.min(anchor.y , lead.y);
		rectangle.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
		rectangle.height = Math.max(0.1, Math.abs(lead.y - anchor.y));

		if(!this.isChanging() || (rectangle.x != 0 || rectangle.y != 0)){
			if(rectangle.x <= pRectangle.x){
				rectangle.x = pRectangle.x + 10;
			}
			if(rectangle.y <= pRectangle.y){
				rectangle.y = pRectangle.y + 10;
			}
		}
		if(rectangle.x + rectangle.width >= pRectangle.x + pRectangle.width){
			rectangle.x = pRectangle.x + pRectangle.width - rectangle.width - 10;
		}
		if(rectangle.y + rectangle.height >= pRectangle.y + pRectangle.height){
			rectangle.y = pRectangle.y + pRectangle.height - rectangle.height - 10;
		}
		if(rectangle.width > pRectangle.width - 20){
			rectangle.width = pRectangle.width - 20;
		}
		if(rectangle.height >= pRectangle.height - 20){
			rectangle.height = pRectangle.height - 20;
		}
	}
	
	public void basicTransform(AffineTransform tx) {
		super.basicTransform(tx);
//		for(int i = 0; i < figureList.size(); i++){
//			figureList.get(i).basicTransform(tx);
//		}
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
	
	
	// Attribute list methods
	public LinkedList<HtmlAttribute> getAttributeList() {
		return attributeList;
	}
	
	public void addHtmlAttribute(HtmlAttribute attribute) {
		attributeList.add(attribute);
	}
	
	public HtmlAttribute removeHtmlAttribute(int location) {
		return attributeList.remove(location);
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
