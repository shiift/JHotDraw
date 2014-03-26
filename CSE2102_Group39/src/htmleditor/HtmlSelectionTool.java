package htmleditor;

import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.Action;

import org.jhotdraw.draw.DelegationSelectionTool;

public class HtmlSelectionTool extends DelegationSelectionTool {
	
	public HtmlSelectionTool(){
		super();
	}

	public HtmlSelectionTool(Collection<Action> drawingActions,
			Collection<Action> selectionActions) {
		super(drawingActions, selectionActions);
	}
	
	@Override
	public void mouseReleased(MouseEvent evt){
		super.mouseReleased(evt);
		System.out.println("Mouse Released HTML");
		
	}
}
