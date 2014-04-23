package htmleditor;

import static org.jhotdraw.draw.AttributeKeys.FONT_BOLD;
import static org.jhotdraw.draw.AttributeKeys.FONT_FACE;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINED;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

import org.jhotdraw.draw.DelegationSelectionTool;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Tool;
import org.jhotdraw.draw.ToolEvent;
import org.jhotdraw.draw.ToolListener;
import org.jhotdraw.draw.action.AlignAction;
import org.jhotdraw.draw.action.AttributeAction;
import org.jhotdraw.draw.action.AttributeToggler;
import org.jhotdraw.draw.action.JPopupButton;
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
        Tool selectionTool = new DelegationSelectionTool(
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
    
    public static void addFontButtonsTo(JToolBar bar, DrawingEditor editor) {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        
        JButton boldToggleButton;
        JButton italicToggleButton;
        JButton underlineToggleButton;
        
        boldToggleButton = new JButton();
        italicToggleButton = new JButton();
        underlineToggleButton = new JButton();
        
        labels.configureToolBarButton(boldToggleButton, "attributeFontBold");
        boldToggleButton.setFocusable(false);
        
        labels.configureToolBarButton(italicToggleButton, "attributeFontItalic");
        italicToggleButton.setFocusable(false);
        
        labels.configureToolBarButton(underlineToggleButton, "attributeFontUnderline");
        underlineToggleButton.setFocusable(false);
        
//        boldToggleButton.addActionListener(new AttributeToggler(editor,
//                FONT_BOLD, Boolean.TRUE, Boolean.FALSE,
//                new StyledEditorKit.BoldAction()
//                ));
//        italicToggleButton.addActionListener(new AttributeToggler(editor,
//                FONT_ITALIC, Boolean.TRUE, Boolean.FALSE,
//                new StyledEditorKit.ItalicAction()
//                ));
//        underlineToggleButton.addActionListener(new AttributeToggler(editor,
//                FONT_UNDERLINED, Boolean.TRUE, Boolean.FALSE,
//                new StyledEditorKit.UnderlineAction()
//                ));
        
        Action action = new StyledEditorKit.BoldAction();
        action.putValue(Action.NAME, "Bold");
        bar.add(action).setFocusable(false);

        action = new StyledEditorKit.ItalicAction();
        action.putValue(Action.NAME, "Italic");
        bar.add(action).setFocusable(false);

        action = new StyledEditorKit.UnderlineAction();
        action.putValue(Action.NAME, "Underline");
        bar.add(action).setFocusable(false);
        

//        bar.add(boldToggleButton).setFocusable(false);
//        bar.add(italicToggleButton).setFocusable(false);
//        bar.add(underlineToggleButton).setFocusable(false);
    }
    
//    int start = messageBodyText.getSelectionStart();
//    int end = messageBodyText.getSelectionEnd();
//
//    StringBuilder strBuilder = new StringBuilder(messageBodyText.getText());
//    strBuilder.replace(start, end, "Replace:" + messageBodyText.getSelectedText() + ".");
//    messageBodyText.setText(strBuilder.toString());
    
}
