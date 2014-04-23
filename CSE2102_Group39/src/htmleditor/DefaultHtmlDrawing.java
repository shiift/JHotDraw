package htmleditor;

import java.awt.Color;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DefaultDrawing;


public class DefaultHtmlDrawing extends DefaultDrawing {
	private DrawProject project;
	private TopParentHtmlFigure topParent;
	
	public DefaultHtmlDrawing() {
    }
	
	public void setProject(DrawProject project){
		this.project = project;  
	}
	
	public DrawProject getProject(){
		return this.project;
	}
	
	public void createTopParent(){
		topParent = new TopParentHtmlFigure(100, 100, 600, 800);
        topParent.addHtmlAttribute(topParent, "style", "background:#FFF;width:800px;margin:0 auto;border:1px solid black;", false);
        topParent.isTopParent = true;
        topParent.setName("Base Page");
		topParent.setAttribute(AttributeKeys.FILL_COLOR, Color.LIGHT_GRAY);
		topParent.setParent(null);
	}
	
	public TopParentHtmlFigure getTopParent(){
		return topParent;
	}
}
