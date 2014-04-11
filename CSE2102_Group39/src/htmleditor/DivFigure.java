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
		that.setName("div");
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
		if(getParent() != null){
			rectangle.width = getParent().rectangle.width - 20;
		}
	}
	public void read(DOMInput in) throws IOException {
        double x = in.getAttribute("x", 0d);
        double y = in.getAttribute("y", 0d);
        double w = in.getAttribute("w", 0d);
        double h = in.getAttribute("h", 0d);
        setBounds(new Point2D.Double(x,y), new Point2D.Double(x+w,y+h));
        readAttributes(in);
        in.openElement("model");
        in.openElement("attributeList");
        setAttributeList((HashMap<String, AttributeValue>) in.readObject());
        in.closeElement();
        in.closeElement();
    }
    public void write(DOMOutput out) throws IOException {
        Rectangle2D.Double r = getBounds();
        out.addAttribute("x", r.x);
        out.addAttribute("y", r.y);
        writeAttributes(out);
        out.openElement("model");
        out.openElement("attributeList");
        out.writeObject(getAttributeList());
        out.closeElement();
        out.closeElement();
    }
	
}
