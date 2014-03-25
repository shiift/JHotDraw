package htmleditor;
import org.jhotdraw.util.*;

import java.util.*;

import javax.swing.*;

import org.jhotdraw.app.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.samples.draw.*;

import static org.jhotdraw.draw.AttributeKeys.*;

public class EditorApplicationModel extends DrawApplicationModel {
	
	public EditorApplicationModel(){
		super();		
	}
	
	private DefaultDrawingEditor getEditor(Project pr){
		DrawProject p = (DrawProject) pr;
	    
	    if (p == null) {
	        return getSharedEditor();
	    } else {
	        return (DefaultDrawingEditor) p.getEditor();
	    }
	}
	
	@Override
	public List<JToolBar> createToolBars(Application a, Project pr) {
		DefaultDrawingEditor editor = getEditor(pr); 
		
		List<JToolBar> toolBars = new LinkedList<JToolBar>();
		JToolBar newBar = new JToolBar();
		
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        
        ToolBarButtonFactory.addSelectionToolTo(newBar, editor, ToolBarButtonFactory.createDrawingActions(editor), ToolBarButtonFactory.createSelectionActions(editor));
        newBar.addSeparator();
        
        newBar.add(new SouthEast(editor)).setFocusable(false);
        
        // following is needed to test the new tool
        ToolBarButtonFactory.addToolTo(newBar, editor, new CreationTool(new RectangleFigure()), "createRectangle", labels);
        
		toolBars.add(newBar);
		return toolBars;
	}
	//How does the toolbar button factory work? Open it up in the draw.action package.
	//show the addStrokeWidthButtonTo method. 
	// It first creates a button object, then adds an AttributeAction to the button. 
	// This action defines the functionality of the button.
	// There are a bunch of actions defined in the draw.action package. 
	
	//Go back to DrawApplicationModel.java and look at the addDefaultCreationButtonsTo class. 
	//  Here is where you create buttons for establishing Tools: a 'mode of drawing'. So if you want
	//  to free draw, you need a tool for that; if you want to create a rectangle, you need to activate
	//  a tool for that too. 

	public static void main(String[] args){
        Application app;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("mac")) {
            app = new DefaultOSXApplication();
        } else if (os.startsWith("win")) {
          //  app = new DefaultMDIApplication();
            app = new DefaultSDIApplication();
        } else {
            app = new DefaultSDIApplication();
        }
		
		DrawApplicationModel myProject = new EditorApplicationModel();
		app.setModel(myProject);
		myProject.setName("William Dickson");
		myProject.setProjectClassName("org.jhotdraw.samples.draw.DrawProject");
		app.launch(args);
	}
	//Comment back here
    public static class SouthEast extends MoveAction {
        public SouthEast(DrawingEditor editor) {
            super(editor, 1, 1);
            labels.configureAction(this, "createArrow");
        }
    }

}
