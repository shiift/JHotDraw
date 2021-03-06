package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

/** Creates an image for HTML*/
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
		return that;
	}
	
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.CYAN);
			control = true;
		}
	}
	
	@Override
	public void basicTransform(AffineTransform tx) {
		super.basicTransform(tx);
		this.getAttributeList().get("width").setValue(this.rectangle.width + "px");
		this.getAttributeList().get("height").setValue(this.rectangle.height + "px");
	}
		
}
