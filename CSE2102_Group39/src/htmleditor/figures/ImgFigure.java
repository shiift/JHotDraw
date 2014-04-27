package htmleditor.figures;

import htmleditor.StyleBuilder;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class ImgFigure extends HtmlFigure
{

	// Width, height, and style variables are for attributes of the ImgFigure
	public double _width;
	public double _height;	
	private boolean control = false;
	
	public ImgFigure() {
		this(0, 0, 0, 0);
	}
	
	public ImgFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("img");
		setName("Image");
	}
		
	public ImgFigure clone()
	{
		ImgFigure that = (ImgFigure) super.clone();
		that.setTag("img");
		that.setName("Image");
		this.addHtmlAttribute(that, "src", "img.jpg");
		this.addHtmlAttribute(that, "alt", "Alternate Text");
		this.addHtmlAttribute(that, "width", "100px", false);
		this.addHtmlAttribute(that, "height", "100px", false);
		this.addHtmlAttribute(that, "style", _style.getStyleValueString());
		return that;
	}
	
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		this.getAttributeList().get("width").setValue(this.rectangle.width + "px");
		this.getAttributeList().get("height").setValue(this.rectangle.height + "px");
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.CYAN);
			control = true;
		}
	}
	public void read(DOMInput in) throws IOException {
		super.read(in);
    }
    public void write(DOMOutput out) throws IOException {
    	super.write(out);
    }
		
}
