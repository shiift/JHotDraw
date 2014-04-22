package htmleditor;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class HRFigure extends HtmlFigure
{

	public StyleBuilder _style = new StyleBuilder();
	
	public HRFigure() {
		this(0, 0, 0, 0);
	}
	
	public HRFigure(double x, double y, double width, double height) {
		super(x, y, width, 1);
		setTag("hr");
		setName("horizontal rule");
	}
		
	public HRFigure clone()
	{
		HRFigure that = (HRFigure) super.clone();
		that.setTag("hr");
		that.setName("horizontal rule");
		this.addHtmlAttribute(that, "style", _style.getStyleValueString());
		return that;
	}
	
	@Override
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		rectangle.height = 1;
	}
	
	@Override
	public void setParent(HtmlFigure parent){
		super.setParent(parent);
		if(parent != null){
			basicSetBounds(new Point2D.Double(parent.rectangle.getMinX() + 10, rectangle.getMinY()),
					new Point2D.Double(parent.rectangle.getMaxX() - 10, rectangle.getMaxY()));
		}
	}
	
}
