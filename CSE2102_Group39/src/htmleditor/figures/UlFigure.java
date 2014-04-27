package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class UlFigure extends HtmlFigure
{

private boolean control = false;
	
    /** Creates a new instance. */
    public UlFigure() {
        this("Ul Text");
    }
    
    public UlFigure(String text) {  
        setTag("ul");
		setName("Unordered List");
    }
    
    public UlFigure clone() {
    	super.clone();
    	UlFigure that = (UlFigure) super.clone();
    	that.setName("Unordered List");
    	that.setTag("ul");
    	//that.(new LiFigure());
    	
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
	
}
