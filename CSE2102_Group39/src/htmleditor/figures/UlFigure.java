package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class UlFigure extends HtmlFigure
{

private boolean control = false;
	
    /** Creates a new instance. */
    public UlFigure() {
        this("Link Text");
    }
    
    public UlFigure(String text) {
        //setText(text);
        setTag("a");
		setName("Anchor");
    }
    
    public AFigure clone() {
    	super.clone();
    	AFigure that = (AFigure) super.clone();
    	that.setName("Anchor");
    	that.setTag("a");
    	
    	this.addHtmlAttribute(that, "href", "http://www.web.com");
        return that;
    }
    
    public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.ORANGE);
			control = true;
		}
	}
	
}
