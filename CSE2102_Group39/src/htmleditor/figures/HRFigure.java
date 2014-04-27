package htmleditor.figures;

import htmleditor.StyleBuilder;

import java.awt.geom.Point2D;


public class HRFigure extends HtmlFigure
{

	// Style attribute of the hr tag
	public HRFigure() {
		this(0,0,0,0);
	}
	
	public HRFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("hr");
		setName("Horizontal Rule");
	}
		
	public HRFigure clone() {
		HRFigure that = (HRFigure) super.clone();
		that.setTag("hr");
		that.setName("Horizontal Rule");
		return that;
	}
	
	// Horizontal rules are horizontal lines, meaning the height doesn't change
	// unlike an HtmlFigure, which is a rectangle that can change height and width
	// basicSetBounds from HtmlFigure, height stays equivalent to 1
	@Override
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
		super.basicSetBounds(anchor, lead);
		rectangle.height = 10;
	}
	
	@Override
	public void setParent(HtmlFigure parent) {
		super.setParent(parent);
		if(parent != null){
			basicSetBounds(new Point2D.Double(parent.rectangle.getMinX() + 10, rectangle.getMinY()),
					new Point2D.Double(parent.rectangle.getMaxX() - 10, rectangle.getMaxY()));
		}
	}
	
}
