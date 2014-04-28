package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class IFrameFigure extends ImgFigure
{

	private boolean control = false;
	
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
		that.setTag("iframe");
		that.setName("IFrame");
		this.addHtmlAttribute(that, "src", "Enter Website");
		this.addHtmlAttribute(that, "width", "100px", false);
		this.addHtmlAttribute(that, "height", "100px", false);
		return that;
	}
	
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.BLUE);
			control = true;
		}
	}
	
}
