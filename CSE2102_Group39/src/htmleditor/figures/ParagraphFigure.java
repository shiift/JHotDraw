package htmleditor.figures;

public class ParagraphFigure extends AbstractTextFigure {
	
    /** Creates a new instance. */
    public ParagraphFigure() {
        this("Text");
    }
    public ParagraphFigure(String text) {
        setText(text);
        setTag("font");
		setName("Font");
    }
    
    public ParagraphFigure clone() {
    	return (ParagraphFigure) super.clone();
    }
}