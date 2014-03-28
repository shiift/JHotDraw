package htmleditor;

public class ImageFigure extends HtmlFigure
{

	public double _width;
	public double _height;
	
	public ImageFigure() {
		this(0, 0, 0, 0);
	}
	
	public ImageFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		_width = width;
		_height = height;
		setTag("img");
		setName("img");
		addImgAttributes(this);
	}
	
	// attributes of img: src, alt, height, width
	public void addImgAttributes(ImageFigure figure)
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
		
	public ImageFigure clone(){
		ImageFigure that = (ImageFigure) super.clone();
		that.setTag("img");
		that.setName("img");
		addImgAttributes(this);
		return that;
	}
		
}
