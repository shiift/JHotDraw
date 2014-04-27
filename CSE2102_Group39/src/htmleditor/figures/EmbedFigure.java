package htmleditor.figures;

import htmleditor.AttributeValue;
import htmleditor.StyleBuilder;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.HashMap;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

/** Creates an image for HTML*/
public class EmbedFigure extends HtmlFigure
{

	// Width, height, and style variables are for attributes of the ImgFigure
	public double _width;
	public double _height;	
	private boolean control = false;
	
	public EmbedFigure() {
		this(0, 0, 0, 0);
	}
	
	public EmbedFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("embed");
		setName("Video");
	}
		
	public EmbedFigure clone()
	{
		EmbedFigure that = (EmbedFigure) super.clone();
		that.setTag("embed");
		that.setName("Video");
		this.addHtmlAttribute(that, "src", "Video id");
		this.addHtmlAttribute(that, "type", "application/x-shockwave-flash");
		this.addHtmlAttribute(that, "width", "100px", false);
		this.addHtmlAttribute(that, "height", "100px", false);
		return that;
	}
	
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		this.getAttributeList().get("width").setValue(this.rectangle.width + "px");
		this.getAttributeList().get("height").setValue(this.rectangle.height + "px");
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.PINK);
			control = true;
		}
	}
	
	public void setSrc()
	{
		String value = getAttributeList().get("src").getValue();
		value = "http://www.youtube.com/v/" + value;
		getAttributeList().get("src").setValue(value);
	}
		
}
