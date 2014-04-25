package htmleditor;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Map;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class TopParentHtmlFigure extends DivFigure {
	
	public TopParentHtmlFigure(){
		this(0, 0, 0, 0);	
	}

	public TopParentHtmlFigure(int i, int j, int k, int l) {
		super(i,j,k,l);
		this.isTopParent = true;
	}
	
	public void read(DOMInput in) throws IOException {
		return;
    }
    public void write(DOMOutput out) throws IOException {
    	super.write(out);
    }
    public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
    	return;
    }
}
