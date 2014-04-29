package htmleditor.figures;

import htmleditor.DefaultHtmlDrawing;

import java.awt.geom.Point2D;
import java.io.IOException;

import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

//The base page that starts with the editor.
public class TopParentHtmlFigure extends HtmlFigure {
	
	public DefaultHtmlDrawing dView;
	private String PageName;
	private String PageColor;
	
	public TopParentHtmlFigure(){
		this(0, 0, 0, 0);	
	}

	public TopParentHtmlFigure(int i, int j, int k, int l) {
		super(i,j,k,l);
		this.setTag("div");
		this.setName("Base Page");
		PageName = "Page Name";
		PageColor = "White";
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
    
    public String getPageColor(){
    	return PageColor;
    }
    public String getPageName(){
    	return PageName;
    }
    public void setPageColor(String color){
    	PageColor = color;
    }
    public void setPageName(String name){
    	PageName = name;
    }
}
