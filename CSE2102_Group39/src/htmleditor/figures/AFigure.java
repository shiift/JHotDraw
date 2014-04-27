package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;

public class AFigure extends AbstractTextFigure {

	private boolean control = false;
	
    /** Creates a new instance. */
    public AFigure() {
        this("Link Text");
    }
    
    public AFigure(String text) {
        setText(text);
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
