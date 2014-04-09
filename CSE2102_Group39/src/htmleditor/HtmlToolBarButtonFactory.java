package htmleditor;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Tool;
import org.jhotdraw.draw.ToolEvent;
import org.jhotdraw.draw.ToolListener;
import org.jhotdraw.draw.action.AlignAction;
import org.jhotdraw.draw.action.ToolBarButtonFactory;
import org.jhotdraw.util.ResourceBundleUtil;

public class HtmlToolBarButtonFactory extends ToolBarButtonFactory{
	
	protected HtmlToolBarButtonFactory(){
	}
	
    public static void addSelectionToolTo(JToolBar tb, final DrawingEditor editor) {
        addSelectionToolTo(tb, editor, createDrawingActions(editor), createSelectionActions(editor));
    }
    public static void addSelectionToolTo(JToolBar tb, final DrawingEditor editor,
            Collection<Action> drawingActions, Collection<Action> selectionActions) {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        
        JToggleButton t;
        Tool tool;
        HashMap<String,Object> attributes;
        
        ButtonGroup group;
        if (tb.getClientProperty("toolButtonGroup") instanceof ButtonGroup) {
            group = (ButtonGroup) tb.getClientProperty("toolButtonGroup");
        } else {
            group = new ButtonGroup();
            tb.putClientProperty("toolButtonGroup", group);
        }
        
        // Selection tool
        Tool selectionTool = new HtmlSelectionTool(
                drawingActions, selectionActions
                );
        editor.setTool(selectionTool);
        t = new JToggleButton();
        final JToggleButton defaultToolButton = t;
        
        ToolListener toolHandler;
        if (tb.getClientProperty("toolHandler") instanceof ToolListener) {
            toolHandler = (ToolListener) tb.getClientProperty("toolHandler");
        } else {
            toolHandler = new ToolListener() {
                public void toolStarted(ToolEvent event) {
                }
                
                public void toolDone(ToolEvent event) {
                    defaultToolButton.setSelected(true);
                }
                
                public void areaInvalidated(ToolEvent e) {
                }
            };
            tb.putClientProperty("toolHandler", toolHandler);
        }
        
        labels.configureToolBarButton(t, "selectionTool");
        t.setSelected(true);
        t.addItemListener(
                new ToolButtonListener(selectionTool, editor)
                );
        t.setFocusable(false);
        group.add(t);
        tb.add(t);
    }
    
    public static void addExportButtonsTo(JToolBar tb, final DrawingEditor editor){
    	ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");

    	JButton export = new JButton();
    	//labels.configureButton(export, "attributeFontBold");
    	export.setText("Export");
    	export.setFocusable(false);
		export.addActionListener(new HtmlExporter());
    	tb.add(export);
    	//tb.add(new AlignAction.West(editor)).setFocusable(false);
    	
    	
    	JToggleButton parentAssigner = new JToggleButton();
    	//labels.configureButton(export, "attributeFontBold");
    	parentAssigner.setText("Assign Parents");
    	parentAssigner.setFocusable(false);
    	parentAssigner.addItemListener(new ItemListener() {
    		public void itemStateChanged(ItemEvent e){
    			if(e.getStateChange()==ItemEvent.SELECTED){
    				HtmlParentAssigner.actionPerformed();
    			}
    			else if(e.getStateChange()==ItemEvent.DESELECTED){
    				HtmlParentAssigner.actionRelease();
    			}
    		}
    	});
    	tb.add(parentAssigner);
    }
}
