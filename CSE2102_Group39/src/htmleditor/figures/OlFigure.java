package htmleditor.figures;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jhotdraw.draw.AttributeKeys;

public class OlFigure extends AbstractTextFigure
{

	private boolean control = false;

	public OlFigure() {
		setTag("ol");
		setName("Ordered List");
	}

	public OlFigure clone() {
		super.clone();
		OlFigure that = (OlFigure) super.clone();
		that.setName("Ordered List");
		that.setTag("ol");
		return that;
	}

	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) 
	{
		super.basicSetBounds(anchor, lead);
		if(control==false){
			this.setAttribute(AttributeKeys.FILL_COLOR, Color.YELLOW);
			control = true;
		}
	}

	@Override
	public String getParsedText()
	{
		String parsedText = super.getParsedText();
		parsedText = parsedText.replaceAll("<br />", "</li>");
		parsedText = parsedText.concat("</li>");
		return parsedText;
	}

}

