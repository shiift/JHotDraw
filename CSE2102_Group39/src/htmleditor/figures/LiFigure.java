package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class LiFigure extends ParagraphFigure
{

private boolean control = false;
	
    /** Creates a new instance. */
    public LiFigure() {
        this("Text");
    }
    
    public LiFigure(String text) {
        setText(text);
        setTag("li");
		setName("List");
    }
    
    public LiFigure clone() {
    	super.clone();
    	LiFigure that = (LiFigure) super.clone();
    	that.setName("List");
    	that.setTag("li");
    	
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
   
    public String parseText(String newText)
    {
    	 newText = newText.replaceAll("\n", "<li>");
         newText = newText.replaceAll("B\\^", "<strong>");
         newText = newText.replaceAll("\\^B", "</strong>");
         newText = newText.replaceAll("I\\^", "<em>");
         newText = newText.replaceAll("\\^I", "</em>");
    	return newText;
    }
	
}
