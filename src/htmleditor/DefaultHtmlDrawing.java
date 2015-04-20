package htmleditor;

import htmleditor.figures.TopParentHtmlFigure;

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

	//Creates the Base Page for the Editor.
	public TopParentHtmlFigure createTopParent(DefaultHtmlDrawingView view){
		topParent = new TopParentHtmlFigure(0, 0, 1000, 1200);
        topParent.addStyle("width", "800px");
        topParent.addStyle("margin", "0 auto");
        topParent.addStyle("min-height", "800px");
        topParent.isTopParent = true;
        topParent.setName("Base Page");
		topParent.setAttribute(AttributeKeys.FILL_COLOR, Color.LIGHT_GRAY);
		topParent.setParent(null);
		view.getDrawing().add(topParent);
		return topParent;
	}

	public TopParentHtmlFigure getTopParent(){
		return topParent;
	}

	public void setTopParent(TopParentHtmlFigure tp){
		topParent = tp;
	}
}
