package htmleditor;

import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public class DivFigure extends HtmlFigure
{

	public DivFigure() {
		this(0, 0, 0, 0);
	}
	
	public DivFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("div");
		setName("div");
	}
	
	public void addDivAttributes(String attribute, String value){
		this.addDivAttributes(this, attribute, value);
	}
	
	// attributes of div: style
	public void addDivAttributes(DivFigure figure, String attribute, String value)
	{
		HtmlAttribute style = new HtmlAttribute(attribute, value);
		figure.addHtmlAttribute(style);
	}
	
	public DivFigure clone(){
		DivFigure that = (DivFigure) super.clone();
		that.setTag("div");
		that.setName("div");
		addDivAttributes(that, "style", "background: #CCC");
		return that;
	}
	
	public void basicTransform(AffineTransform tx){
		super.basicTransform(tx);
	}
	
}
