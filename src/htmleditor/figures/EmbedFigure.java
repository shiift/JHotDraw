package htmleditor.figures;

import htmleditor.AttributeValue;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

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
		this.addHtmlAttribute(that, "src", "Youtube Video ID");
		this.addHtmlAttribute(that, "type", "application/x-shockwave-flash", false);
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
	
	public AttributeValue getAttributeValue(String name) {
		AttributeValue newAtt = super.getAttributeValue(name);
		if(name.equals("src")){
			String value = "http://www.youtube.com/v/" + newAtt.getValue();
			newAtt = new AttributeValue(value);
		}
		return newAtt;
	}
		
}
