package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class IFrameFigure extends HtmlFigure
{

	private boolean control = false;
	
	// Creates IFrame instance
	public IFrameFigure() {
		this(0, 0, 0, 0);
	}
	
	public IFrameFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("iframe");
		setName("IFrame");
	}
		
	public IFrameFigure clone()
	{
		IFrameFigure that = (IFrameFigure) super.clone();
		that.setTag("iframe");
		that.setName("IFrame");
		that.addHtmlAttribute(that, "src", "Enter Website");
		that.addStyle("width", String.valueOf(rectangle.width));
		that.addStyle("height", String.valueOf(rectangle.height));
		return that;
	}
	
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.RED);
			control = true;
		}
		this.setStyle("width", String.valueOf(rectangle.width));
		this.setStyle("height", String.valueOf(rectangle.height));
	}
	
}
