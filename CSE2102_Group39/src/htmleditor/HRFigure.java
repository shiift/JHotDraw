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
		this.addAttribute(that, "style", _style.getStyleValueString());
		return that;
	}
	
	@Override
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		if(parent != null){
			Rectangle2D.Double pRectangle = parent.rectangle;
			rectangle.x = Math.min(anchor.x, lead.x);
			rectangle.y = Math.min(anchor.y , lead.y);
			rectangle.width = rectangle.width = Math.max(5, Math.abs(lead.x - anchor.x));
			rectangle.height = 1;
	
			if(!this.isChanging() || (rectangle.x != 0 || rectangle.y != 0)){
				if(rectangle.x <= pRectangle.x){
					rectangle.x = pRectangle.x + 10;
				}
				if(rectangle.y <= pRectangle.y){
					rectangle.y = pRectangle.y + 10;
				}		
			}
			if(rectangle.x + rectangle.width >= pRectangle.x + pRectangle.width){
				rectangle.x = pRectangle.x + pRectangle.width - rectangle.width - 10;
			}
			if(rectangle.y + rectangle.height >= pRectangle.y + pRectangle.height){
				rectangle.y = pRectangle.y + pRectangle.height - rectangle.height - 10;
			}
			if(rectangle.width > pRectangle.width - 20){
				rectangle.width = pRectangle.width - 20;
			}
			if(rectangle.height >= pRectangle.height - 20){
				rectangle.height = pRectangle.height - 20;
			}
		//this.setAttribute(AttributeKeys.FILL_COLOR, Color.BLUE);
		}
		else{
			rectangle.x = Math.min(anchor.x, lead.x);
	        rectangle.y = Math.min(anchor.y , lead.y);
	        rectangle.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
	        rectangle.height = 1;
		}
	}
	
}
