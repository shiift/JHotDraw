package htmleditor;

import java.awt.geom.Point2D;

public class ImgFigure extends HtmlFigure
{

	public double _width;
	public double _height;
	
	public ImgFigure() {
		this(0, 0, 0, 0);
	}
	
	public ImgFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		setTag("img");
		setName("img");
		addImgAttributes(this);
	}
	
	// attributes of img: src, alt, height, width
	public void addImgAttributes(ImgFigure figure)
	{
		HtmlAttribute src = new HtmlAttribute("src", "img.gif");
		figure.addHtmlAttribute(src);
		HtmlAttribute alt = new HtmlAttribute("alt", "Image");
		figure.addHtmlAttribute(alt);
		HtmlAttribute height = new HtmlAttribute("height", String.valueOf(_height));
		figure.addHtmlAttribute(height);
		HtmlAttribute width = new HtmlAttribute("width", String.valueOf(_width));
		figure.addHtmlAttribute(width);
	}
		
	public ImgFigure clone()
	{
		ImgFigure that = (ImgFigure) super.clone();
		that.setTag("img");
		that.setName("img");
		addImgAttributes(that);
		return that;
	}
	
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		_width = rectangle.width;
		_height = rectangle.height;
	}
		
}
