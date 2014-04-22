package htmleditor;

import org.jhotdraw.draw.DefaultDrawing;


public class DefaultHtmlDrawing extends DefaultDrawing {
	private DrawProject project;
	
	public DefaultHtmlDrawing() {
    }
	
	public void setProject(DrawProject project){
		this.project = project;  
	}
	
	public DrawProject getProject(){
		return this.project;
	}
}
