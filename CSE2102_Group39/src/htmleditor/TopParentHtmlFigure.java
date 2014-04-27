package htmleditor;

import htmleditor.figures.DivFigure;
import htmleditor.figures.HtmlFigure;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class TopParentHtmlFigure extends HtmlFigure {
	
	public DefaultHtmlDrawing dView;
	
	public TopParentHtmlFigure(){
		this(0, 0, 0, 0);	
	}

	public TopParentHtmlFigure(int i, int j, int k, int l) {
		super(i,j,k,l);
		this.isTopParent = true;
	}
	
	public boolean contains(Point2D.Double p) {
		return false;
	}
	
	public void read(DOMInput in) throws IOException {
		super.read(in);
    }
    public void write(DOMOutput out) throws IOException {
    	super.write(out);
    }
}
