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
		return that;
	}
	
	public void assignParents(){

		for(int i = 0; i<Global.figureList.size(); i++){
			HtmlFigure curFig = Global.figureList.pop();
			//Current Figure loop.
			if(curFig.isTopParent==false){
				Rectangle2D.Double curRec = curFig.rectangle;
				LinkedList<HtmlFigure> possibleParents = new LinkedList<HtmlFigure>();
				//Possible Parents are added to newly created linklist for the current figure.
				for(int j = 0; j<Global.figureList.size(); j++){
					HtmlFigure curPossibleParent = Global.figureList.pop();
					Rectangle2D.Double posRec = curPossibleParent.rectangle;
					if(posRec.x<=curRec.x && posRec.y<=curRec.y && posRec.x + posRec.width>=curRec.x + curRec.width && posRec.y + posRec.height>=curRec.y + curRec.height){
						possibleParents.add(curPossibleParent);
					}
					Global.figureList.add(curPossibleParent);
				}
				//Possible Parents are looped to see the smallest of the parent and assign it as such.
				HtmlFigure _parent = possibleParents.pop();
				if(possibleParents.isEmpty()==false){
					for(int j = 0; j<possibleParents.size(); j++){
						Rectangle2D.Double curParRec = _parent.rectangle;
						HtmlFigure posPar = possibleParents.pop();
						Rectangle2D.Double posParRec = posPar.rectangle;
						if(posParRec.x>=curParRec.x && posParRec.y>=curParRec.x && posParRec.x + posParRec.width <= curParRec.x + curParRec.width && posParRec.y + posParRec.height <= curParRec.y + curParRec.height){
							_parent = posPar;
						}
					}
				}
				curFig.setParent(_parent);
			}
			Global.figureList.add(curFig);
		}
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