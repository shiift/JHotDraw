package htmleditor;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class DivFigure extends HtmlFigure
{

	public StyleBuilder _style = new StyleBuilder();
	private boolean control = false;
	
	public DivFigure() {
		this(0, 0, 0, 0);
	}
	
	public DivFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("div");
		setName("div");
		_style.addStyleAttribute("background", "#CCC");
	}
	
	public DivFigure clone(){
		DivFigure that = (DivFigure) super.clone();
		that.setTag("div");
		that.setName("Division");
		addHtmlAttribute(that, "style", _style.getStyleValueString());
		return that;
	}
	
	public void basicTransform(AffineTransform tx){
		super.basicTransform(tx);
	}
	
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
	
	public void read(DOMInput in) throws IOException {
		super.read(in);
    }
    public void write(DOMOutput out) throws IOException {
    	super.write(out);
    }
	
}
