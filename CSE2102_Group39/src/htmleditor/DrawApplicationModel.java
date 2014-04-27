/*
 * @(#)DrawApplicationModel.java  1.0  June 10, 2006
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors ("JHotDraw.org")
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * JHotDraw.org ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * JHotDraw.org.
 */

package htmleditor;

import org.jhotdraw.util.*;

import htmleditor.figures.*;

import java.util.*;

import javax.swing.*;

import org.jhotdraw.app.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;

import static org.jhotdraw.draw.AttributeKeys.*;
/**
 * DrawApplicationModel.
 * 
 * 
 * 
 * @author Werner Randelshofer.
 * @version 1.0 June 10, 2006 Created.
 */
public class DrawApplicationModel extends DefaultApplicationModel {
    /**
     * This editor is shared by all projects.
     */
    private DefaultDrawingEditor sharedEditor;
    
    /** Creates a new instance. */
    public DrawApplicationModel() {
    }
    
    public DefaultDrawingEditor getSharedEditor() {
        if (sharedEditor == null) {
            sharedEditor = new DefaultDrawingEditor();
        }
        return sharedEditor;
    }
    
    public void initProject(Application a, Project p) {
        if (a.isSharingToolsAmongProjects()) {
            ((DrawProject) p).setEditor(getSharedEditor());
        }
    }
    /**
     * Creates toolbars for the application.
     * This class always returns an empty list. Subclasses may return other
     * values.
     */
    public List<JToolBar> createToolBars(Application a, Project pr) {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        DrawProject p = (DrawProject) pr;
        
        DrawingEditor editor;
        if (p == null) {
            editor = getSharedEditor();
        } else {
            editor = p.getEditor();
        }
        
        LinkedList<JToolBar> list = new LinkedList<JToolBar>();
        JToolBar tb;
        tb = new JToolBar();
        addCreationButtonsTo(tb, editor);
        tb.setName(labels.getString("drawToolBarTitle"));
        list.add(tb);
        
        tb = new JToolBar();
        HtmlToolBarButtonFactory.addExportButtonsTo(tb, editor);
        tb.setName("Export ToolBar");
        list.add(tb);
        return list;
    }
    private void addCreationButtonsTo(JToolBar tb, DrawingEditor editor) {
        addDefaultCreationButtonsTo(tb, editor, 
                HtmlToolBarButtonFactory.createDrawingActions(editor), 
                HtmlToolBarButtonFactory.createSelectionActions(editor)
                );
    }
    public void addDefaultCreationButtonsTo(JToolBar tb, final DrawingEditor editor,
            Collection<Action> drawingActions, Collection<Action> selectionActions) {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        ResourceBundleUtil htmlLabels = ResourceBundleUtil.getLAFBundle("htmleditor.Labels");
        
        HtmlToolBarButtonFactory.addSelectionToolTo(tb, editor, drawingActions, selectionActions);
        tb.addSeparator();
        
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new DivFigure()), "createDiv", htmlLabels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new ImgFigure()), "createImg", htmlLabels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new AFigure()), "createA", htmlLabels);
        //HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new HRFigure()), "createHR", htmlLabels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new TextAreaTool(new ParagraphFigure()), "createTextArea", labels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new UlFigure()), "createUl", htmlLabels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new OlFigure()), "createOl", htmlLabels);
    }    
}
