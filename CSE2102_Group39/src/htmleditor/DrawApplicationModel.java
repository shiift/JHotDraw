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

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.*;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;

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

//Modified DefaultApplicationModel to add custom tools among other usages.
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
        tb.setName(labels.getString("attributesToolBarTitle"));
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
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new HRFigure()), "createHR", htmlLabels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new TextAreaTool(new ParagraphFigure()), "createTextArea", labels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new UlFigure()), "createUl", htmlLabels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new OlFigure()), "createOl", htmlLabels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new EmbedFigure()), "createEmbed", htmlLabels);
        HtmlToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new IFrameFigure()), "createIFrame", htmlLabels);
                
        JPopupButton fontPopupButton;
        
        fontPopupButton = new JPopupButton();
        
        labels.configureToolBarButton(fontPopupButton, "attributeFont");
        fontPopupButton.setFocusable(false);
        
        Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        HashSet<String> fontExclusionList = new HashSet<String>(Arrays.asList(new String[] {
            // Mac OS X 10.3 Font Exclusion List
            "#GungSeo", "#HeadLineA", "#PCMyungjo", "#PilGi", "Al Bayan", "Apple LiGothic",
            "Apple LiSung", "AppleMyungjo", "Arial Hebrew", "Ayuthaya", "Baghdad",
            "BiauKai", "Charcoal CY", "Corsiva Hebrew", "DecoType Naskh",
            "Devanagari MT", "Fang Song", "GB18030 Bitmap", "Geeza Pro",
            "Geezah", "Geneva CY", "Gujarati MT", "Gurmukhi MT", "Hei",
            "Helvetica CY", "Hiragino Kaku Gothic Std", "Hiragino Maru Gothic Pro",
            "Hiragino Mincho Pro", "Hiragino Kaku Gothic Pro",
            "InaiMathi",
            "Kai",
            "Krungthep", "KufiStandardGK", "LiHei Pro", "LiSong Pro",
            "Mshtakan",
            "Monaco CY",
            "Nadeem",
            "New Peninim MT", "Osaka",
            "Plantagenet Cherokee",
            "Raanana", "STFangsong", "STHeiti",
            "STKaiti", "STSong", "Sathu", "Silom",
            "Thonburi", "Times CY",
            
            // Windows XP Professional Font Exclusion List
            "Arial Unicode MS", "Batang", "Estrangelo Edessa", "Gautami",
            "Kartika", "Latha", "Lucida Sans Unicode", "Mangal", "Marlett",
            "MS Mincho", "MS Outlook", "MV Boli", "OCR-B-10 BT",
            "Raavi", "Shruti", "SimSun", "Sylfaen", "Symbol", "Tunga",
            "Vrinda", "Wingdings", "Wingdings 2", "Wingdings 3",
            "ZWAdobeF"
        }));
        LinkedList<Font> fontList = new LinkedList<Font>();
        for (int i=0; i < allFonts.length; i++) {
            if (! fontExclusionList.contains(allFonts[i].getFamily())) {
                fontList.add(allFonts[i]);
            }
        }
        allFonts = new Font[fontList.size()];
        allFonts = (Font[]) fontList.toArray(allFonts);
        Arrays.sort(allFonts, new Comparator<Font>() {
            public int compare(Font f1, Font f2) {
                int result = f1.getFamily().compareTo(f2.getFamily());
                if (result == 0) {
                    result = f1.getFontName().compareTo(f2.getFontName());
                }
                return result;
            }
        });
        LinkedList<Font> fontFamilies = new LinkedList<Font>();
        JMenu submenu = null;
        for (int i=0; i < allFonts.length; i++) {
            if (submenu != null) {
                if (! allFonts[i].getFamily().equals(allFonts[i - 1].getFamily())) {
                    submenu = null;
                }
            }
            if (submenu == null) {
                if (i < allFonts.length - 2
                        && allFonts[i].getFamily().equals(allFonts[i + 1].getFamily())) {
                    fontFamilies.add(allFonts[i]);
                    submenu = new JMenu(allFonts[i].getFamily());
                    fontPopupButton.add(submenu);
                    
                }
            }
            Action action = new AttributeAction(
                    editor,
                    FONT_FACE,
                    allFonts[i],
                    (submenu == null) ? allFonts[i].getFamily() : allFonts[i].getFontName(),
                    null,
                    new StyledEditorKit.FontFamilyAction(allFonts[i].getFontName(),allFonts[i].getFamily())
                    );
            
            if (submenu == null) {
                fontFamilies.add(allFonts[i]);
                fontPopupButton.add(action);
            }
        }
        fontPopupButton.setColumnCount( Math.max(1, fontFamilies.size()/32), true);
        
        tb.add(fontPopupButton).setFocusable(false);
    }    
}
