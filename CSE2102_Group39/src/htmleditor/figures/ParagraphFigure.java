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
    	return (ParagraphFigure) super.clone();
    }
}