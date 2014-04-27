package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class OlFigure extends ParagraphFigure
{

	private boolean control = false;

	//    /** Creates a new instance. */
	//    public OlFigure() {  
	//        setTag("ol");
	//		setName("Ordered List");
	//    }
	//    
	//    public OlFigure clone() {
	//    	super.clone();
	//    	OlFigure that = (OlFigure) super.clone();
	//    	that.setName("Ordered List");
	//    	that.setTag("ol");
	//    	//that.(new LiFigure());
	//    	
	//        return that;
	//    }

	public OlFigure() {
		setTag("ol");
		setName("Ordered List");
	}

	public OlFigure clone() {
		super.clone();
		OlFigure that = (OlFigure) super.clone();
		that.setName("Ordered List");
		that.setTag("ul");
		return that;
	}

	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.GREEN);
			control = true;
		}
	}

	@Override
	public String parseText(String newText)
	{
		super.parseText(newText);
		newText = newText.replaceAll("<br />", "</li>");
		newText = newText.replaceAll("-", "</li>");
		return newText;
	}

}

