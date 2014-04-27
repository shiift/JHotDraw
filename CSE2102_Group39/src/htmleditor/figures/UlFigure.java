package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class UlFigure extends ParagraphFigure
{

private boolean control = false;
	
	//public LiFigure _list;
//	public ParagraphFigure _list;
//
//    /** Creates a new instance. */
//    
//    public UlFigure() {  
//        setTag("ul");
//		setName("Unordered List");
//		//_list = new LiFigure();
//		_list = new ParagraphFigure("text");
//    }
//    
//    public UlFigure clone() {
//    	super.clone();
//    	UlFigure that = (UlFigure) super.clone();
//    	that.setName("Unordered List");
//    	that.setTag("ul");
//    	//that._list = new LiFigure();
//    	that._list.clone();
//    	
//        return that;
//    }

	public UlFigure() {
		setTag("ul");
		setName("Unordered List");
	}
	
	public UlFigure clone() {
		super.clone();
		UlFigure that = (UlFigure) super.clone();
		that.setName("Unordered List");
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
