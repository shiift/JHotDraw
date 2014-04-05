package htmleditor;


import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedList;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.RectangleFigure;

public class HtmlFigure extends RectangleFigure {

	protected LinkedList<HtmlFigure> figureList;
	protected HashMap<String, AttributeValue> attributeList;
	protected HtmlFigure parent;
	protected boolean isData;
	protected String tag;
	protected String name;
	public boolean isTopParent = false;

	/** Creates a new instance. */
	public HtmlFigure() {
		this(0, 0, 0, 0);
	}

	public HtmlFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		figureList = new LinkedList<HtmlFigure>();
		attributeList = new HashMap<String, AttributeValue>();
		parent = null;
		tag = "";
		name = "";
	}

	public HtmlFigure clone() {
		HtmlFigure that = (HtmlFigure) super.clone();
		Global.figureList.add(that);
		that.figureList = new LinkedList<HtmlFigure>();
		that.attributeList = new HashMap<String, AttributeValue>();
		that.parent = null;
		that.tag = "";
		that.name = "";
		return that;
	}

	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
		if(parent != null){
			Rectangle2D.Double pRectangle = parent.rectangle;
			rectangle.x = Math.min(anchor.x, lead.x);
			rectangle.y = Math.min(anchor.y , lead.y);
			rectangle.width = Math.max(5, Math.abs(lead.x - anchor.x));
			rectangle.height = Math.max(5, Math.abs(lead.y - anchor.y));
	
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
//			this.getDrawing().bringToFront(this);
//			for(int i = 0; i < parent.getObjectList().size(); i++){
//				HtmlFigure compFigure = parent.getObjectList().get(i);
//				if(!compFigure.equals(this)){
//					Point2D.Double tr = new Point2D.Double(this.getStartPoint().x + this.getEndPoint().x, this.getStartPoint().y);
//					Point2D.Double bl = new Point2D.Double(this.getStartPoint().x, this.getStartPoint().y + this.getEndPoint().y);
//					if(compFigure.contains(this.getStartPoint()) || compFigure.contains(this.getEndPoint())
//							|| compFigure.contains(tr) || compFigure.contains(bl)){
//					}
//					System.out.println("Collide");
//				}
//			}
		}else{
			super.basicSetBounds(anchor, lead);
		}
	}
	
//	@Override
//	public void removeNotify(Drawing d){
//		for(int i = 0; i<Global.figureList.size(); i++){
//			if(figureList.get(i).equals(this)){
//				Global.figureList.remove(i);
//				break;
//			}
//		}
//		System.out.println("REMOVED");
//		super.removeNotify(d);
//	}
	
	public void basicTransform(AffineTransform tx) {
		super.basicTransform(tx);
		for(int i = 0; i < figureList.size(); i++){
			figureList.get(i).basicTransform(new AffineTransform(1, 0, 0, 1, 0, 0));;
		}
	}
	
	public void addAttribute(HtmlFigure figure, String attribute, String value)
	{
		AttributeValue style = new AttributeValue(value);
		figure.addHtmlAttribute(attribute, style);
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
	
	public void clearFigureList(){
		this.figureList = new LinkedList<HtmlFigure>();
	}
	
	
	// Attribute list methods
	public HashMap<String, AttributeValue> getAttributeList() {
		return attributeList;
	}
	
	public void addHtmlAttribute(String attributeName, AttributeValue attributeValue) {
		attributeList.put(attributeName, attributeValue);
	}
	
	public AttributeValue removeHtmlAttribute(int location) {
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
