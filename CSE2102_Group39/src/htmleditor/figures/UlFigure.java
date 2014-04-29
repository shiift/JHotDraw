package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class UlFigure extends AbstractTextFigure
{

private boolean control = false;

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
	public String getParsedText()
	{
		String parsedText = super.getParsedText();
        parsedText = parsedText.replaceAll("\\*\\*", "<li>");
		parsedText = parsedText.replaceAll("<br />", "</li><br />");
		parsedText = parsedText.concat("</li>");
		return parsedText;
	}
    
}
