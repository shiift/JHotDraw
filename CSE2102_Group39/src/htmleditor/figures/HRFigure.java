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
		super(x, y, width, 1);
		setTag("hr");
		setName("Horizontal Rule");
		this.addStyle("width", "50px");
	}
		
	public HRFigure clone() {
		HRFigure that = (HRFigure) super.clone();
		that.setTag("hr");
		that.setName("Horizontal Rule");
		return that;
	}
	
}
