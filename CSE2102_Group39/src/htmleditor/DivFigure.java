package htmleditor;

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
		addDivAttributes();
	}
	
	// attributes of div: style
	public void addDivAttributes()
	{
		HtmlAttribute style = new HtmlAttribute("style", "background: #CCC");
		this.addHtmlAttribute(style);
	}
	
	public DivFigure clone(){
		DivFigure that = (DivFigure) super.clone();
		that.setTag("div");
		that.setName("div");
		addDivAttributes();
		return that;
	}
	
	
}
