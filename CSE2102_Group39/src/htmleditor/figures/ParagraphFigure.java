package htmleditor.figures;

public class ParagraphFigure extends AbstractTextFigure {
	
    /** Creates a new instance. */
    public ParagraphFigure() {
        super();
    }
    public ParagraphFigure(String text) {
        super(text);
    }
    
    public ParagraphFigure clone() {
    	ParagraphFigure that = (ParagraphFigure) super.clone();
    	return that;
    }
}