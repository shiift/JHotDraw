package htmleditor.figures;

import htmleditor.StyleBuilder;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

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
		return that;
	}
	
	/** Color on the view will be light gray */
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead){
		super.basicSetBounds(anchor, lead);
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.LIGHT_GRAY);
			control = true;
		}
	}
	
	@Override
	public void setParent(HtmlFigure parent){
		super.setParent(parent);
		if(parent != null){
			basicSetBounds(new Point2D.Double(parent.rectangle.getMinX() + 10, rectangle.getMinY()),
					new Point2D.Double(parent.rectangle.getMaxX() - 10, rectangle.getMaxY()));
		}
	}
	
}
