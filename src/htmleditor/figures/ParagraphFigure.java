package htmleditor.figures;

import java.awt.Color;

import org.jhotdraw.draw.AttributeKeys;

public class ParagraphFigure extends AbstractTextFigure {
	
    /** Creates a new instance. */
    public ParagraphFigure() {
        super();
    }
    public ParagraphFigure(String text) {
        super(text);
		this.setAttribute(AttributeKeys.FILL_COLOR, null);
    }
    
    public ParagraphFigure clone() {
    	ParagraphFigure that = (ParagraphFigure) super.clone();
    	return that;
    }
}