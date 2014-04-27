package htmleditor.figures;

import static org.jhotdraw.draw.AttributeKeys.TEXT;

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
    
    public String getText() {
        return (String) getAttribute(TEXT);
    }
    
    public void setText(String newText)
    {
    	setAttribute(TEXT, newText);
        newText = parseText(newText);
    }
    
    @Override
    public String parseText(String newText)
    {
    	super.parseText(newText);
    	newText = newText.replaceAll("<br />", "</li><li>");
    	return newText;
    }
	
}
