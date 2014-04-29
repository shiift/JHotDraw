package htmleditor.figures;


import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jhotdraw.draw.AttributeKeys;

// Class for the div tag in HTML

public class DivFigure extends HtmlFigure
{

	// Member variables for the style attribute of the div tag
	private boolean control = false;
	
	public DivFigure() {
		this(0,0,0,0);
	}
	
	public DivFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("div");
		setName("Division");
	}
	
	public DivFigure clone(){
		DivFigure that = (DivFigure) super.clone();
		that.setTag("div");
		that.setName("Division");
		that.addStyle("width", String.valueOf(rectangle.width));
		that.addStyle("height", String.valueOf(rectangle.height));
		return that;
	}
	
	/** Color on the view will be light gray */
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead){
		super.basicSetBounds(anchor, lead);
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.LIGHT_GRAY);
			control = true;
		}
		this.setStyle("width", String.valueOf(rectangle.width));
		this.setStyle("height", String.valueOf(rectangle.height));
	}
	
	/** Sets the style of the DivFigure */
	@Override
    public void setStyle(String key, String value){
    	super.setStyle(key, value);
    	if(key.equals("width") || key.equals("height")){
    		value = value.replaceAll("[^\\d.]", "");
    		Rectangle2D.Double size = new Rectangle2D.Double();
			size = (java.awt.geom.Rectangle2D.Double) rectangle.clone();
	    	if(key.equals("width")){
	    		size.width = Double.parseDouble(value);
	    	}
	    	if(key.equals("height")){
	    		size.height = Double.parseDouble(value);
	    	}
    		restoreTo(size);
    		this.invalidate();
    	}
    }
	
}
