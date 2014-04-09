package htmleditor;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class ImgFigure extends HtmlFigure
{

	public double _width;
	public double _height;
	public StyleBuilder _style = new StyleBuilder();
	
	public ImgFigure() {
		this(0, 0, 0, 0);
	}
	
	public ImgFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("img");
		setName("img");
	}
		
	public ImgFigure clone()
	{
		ImgFigure that = (ImgFigure) super.clone();
		that.setTag("img");
		that.setName("img");
		this.addHtmlAttribute(that, "src", "#");
		this.addHtmlAttribute(that, "alt", "THIS IMAGE");
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
		//this.setAttribute(AttributeKeys.FILL_COLOR, Color.BLUE);
	}
		
}
