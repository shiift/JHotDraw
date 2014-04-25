package htmleditor;

import htmleditor.figures.DivFigure;

import java.awt.geom.Point2D;
import java.io.IOException;

import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class TopParentHtmlFigure extends DivFigure {
	
	public DefaultHtmlDrawing dView;
	
	public TopParentHtmlFigure(){
		this(0, 0, 0, 0);	
	}

	public TopParentHtmlFigure(int i, int j, int k, int l) {
		super(i,j,k,l);
		this.isTopParent = true;
	}
	
	public void read(DOMInput in) throws IOException {
		super.read(in);
    }
    public void write(DOMOutput out) throws IOException {
    	super.write(out);
    }
}
