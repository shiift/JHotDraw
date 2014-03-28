package htmleditor;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import org.jhotdraw.draw.AttributeKeys;

public class DivFigure extends HtmlFigure
{

	public DivFigure() {
		this(0, 0, 0, 0);
	}
	
	public DivFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("div");
		setName("div");
	}
	
	public DivFigure clone(){
		DivFigure that = (DivFigure) super.clone();
		that.setTag("div");
		that.setName("div");
		addAttribute(that, "style", "background: #CCC");
		return that;
	}
	
	public void basicTransform(AffineTransform tx){
		super.basicTransform(tx);
	}
	
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead){
		super.basicSetBounds(anchor, lead);
		//this.setAttribute(AttributeKeys.FILL_COLOR, Color.LIGHT_GRAY);
	}
	
}
