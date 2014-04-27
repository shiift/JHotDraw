package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class IFrameFigure extends ImgFigure
{

	public IFrameFigure() {
		this(0, 0, 0, 0);
	}
	
	public IFrameFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("iframe");
		setName("IFrame");
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
	
//	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
//	{
//		super.basicSetBounds(anchor, lead);
//		this.getAttributeList().get("width").setValue(this.rectangle.width + "px");
//		this.getAttributeList().get("height").setValue(this.rectangle.height + "px");
//		if(control==false){
//			this.setAttribute(AttributeKeys.FILL_COLOR, Color.CYAN);
//			control = true;
//		}
//	}
	
}
