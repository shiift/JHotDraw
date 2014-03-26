package htmleditor;

public class DivFigure extends HtmlFigure
{

	public DivFigure() {
		this(0, 0, 0, 0);
	}
	
	public DivFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("<div>");
		setName("div");
		//addDivAttributes();
		//HtmlAttribute style = new HtmlAttribute("style", "value");
		//addHtmlAttribute(style);
	}
	
	// attributes of div: style
	public void addDivAttributes()
	{
		HtmlAttribute style = new HtmlAttribute("style", "value");
		addHtmlAttribute(style);
	}
	
	
	
}
