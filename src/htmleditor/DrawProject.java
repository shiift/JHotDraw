/*
 * @(#)DrawProject.java  1.1  2006-06-10
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
 *
 */
package htmleditor;

import htmleditor.figures.TopParentHtmlFigure;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jhotdraw.app.AbstractProject;
import org.jhotdraw.app.action.RedoAction;
import org.jhotdraw.app.action.UndoAction;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.GridConstrainer;
import org.jhotdraw.draw.action.ToolBarButtonFactory;
import org.jhotdraw.gui.PlacardScrollPaneLayout;
import org.jhotdraw.io.ExtensionFileFilter;
import org.jhotdraw.undo.UndoRedoManager;
import org.jhotdraw.xml.NanoXMLLiteDOMInput;
import org.jhotdraw.xml.NanoXMLLiteDOMOutput;

/**
 * A drawing project.
 *
 * @author Werner Randelshofer
 * @version 1.1 2006-06-10 Extended to support DefaultDrawApplicationModel.
 * <br>1.0 2006-02-07 Created.
 */
public class DrawProject extends AbstractProject {

	/**
	 * Each DrawProject uses its own undo redo manager.
	 * This allows for undoing and redoing actions per project.
	 */
	private UndoRedoManager undo;

	/**
	 * Depending on the type of an application, there may be one editor per
	 * project, or a single shared editor for all projects.
	 */
	private DrawingEditor editor;

	/**
	 * Creates a new Project.
	 */
	public DrawProject() {
	}

	/**
	 * Initializes the project.
	 */
	public void init() {
		super.init();

		initComponents();

		view.addFigureSelectionListener(attributePanel);

		scrollPane.setLayout(new PlacardScrollPaneLayout());
		scrollPane.setBorder(new EmptyBorder(0,0,0,0));

		setEditor(new DefaultDrawingEditor());
		view.setDOMFactory(new DrawFigureFactory());
		undo = new UndoRedoManager();
		DefaultHtmlDrawing htmlDrawing = new DefaultHtmlDrawing();
		htmlDrawing.setProject(this);
		view.setDrawing(htmlDrawing);
		view.getDrawing().addUndoableEditListener(undo);
		initActions();
		undo.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				setHasUnsavedChanges(undo.hasSignificantEdits());
			}
		});

		JPanel placardPanel = new JPanel(new BorderLayout());
		javax.swing.AbstractButton pButton;
		pButton = ToolBarButtonFactory.createZoomButton(view);
		pButton.putClientProperty("Quaqua.Button.style","placard");
		pButton.putClientProperty("Quaqua.Component.visualMargin",new Insets(0,0,0,0));
		pButton.setFont(UIManager.getFont("SmallSystemFont"));
		placardPanel.add(pButton, BorderLayout.WEST);
		view.setConstrainer(new GridConstrainer(10,10));
		scrollPane.add(placardPanel, JScrollPane.LOWER_LEFT_CORNER);

		DefaultHtmlDrawing draw = (DefaultHtmlDrawing) view.getDrawing();
		topParent = draw.createTopParent(view);
	}

	public DrawingEditor getEditor() {
		return editor;
	}
	public void setEditor(DrawingEditor newValue) {
		DrawingEditor oldValue = editor;
		if (oldValue != null) {
			oldValue.remove(view);
		}
		editor = newValue;
		if (newValue != null) {
			newValue.add(view);
		}
	}

	/**
	 * Initializes project specific actions.
	 */
	private void initActions() {
		putAction(UndoAction.ID, undo.getUndoAction());
		putAction(RedoAction.ID, undo.getRedoAction());
	}
	protected void setHasUnsavedChanges(boolean newValue) {
		super.setHasUnsavedChanges(newValue);
		undo.setHasSignificantEdits(newValue);
	}

	/**
	 * Writes the project to the specified file.
	 */
	public void write(File f) throws IOException {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(f));
			NanoXMLLiteDOMOutput domo = new NanoXMLLiteDOMOutput(view.getDOMFactory());
			domo.openElement("PlasmaDraw");
			domo.writeObject(view.getDrawing());
			domo.closeElement();
			domo.save(out);
		} finally {
			if (out != null) try { out.close(); } catch (IOException e) {};
		}
	}

	/**
	 * Reads the project from the specified file.
	 */
	public void read(File f) throws IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			NanoXMLLiteDOMInput domi = new NanoXMLLiteDOMInput(view.getDOMFactory(), in);
			domi.openElement("PlasmaDraw");
			final Drawing drawing = (Drawing) domi.readObject();
			domi.closeElement();
			SwingUtilities.invokeAndWait(new Runnable() { public void run() {
				view.getDrawing().removeUndoableEditListener(undo);
				view.setDrawing(drawing);
				view.getDrawing().addUndoableEditListener(undo);
				undo.discardAllEdits();
			}});
		} catch (InterruptedException e) {
			InternalError error = new InternalError();
			e.initCause(e);
			throw error;
		} catch (InvocationTargetException e) {
			InternalError error = new InternalError();
			e.initCause(e);
			throw error;
		} finally {
			if (in != null) try { in.close(); } catch (IOException e) {};
		}
	}

	public void export(File file) {
		try {
			HtmlParser.createFile(view.getDrawing().getTopParent(), file);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets a drawing editor for the project.
	 */
	public void setDrawingEditor(DrawingEditor newValue) {
		if (editor != null) {
			editor.remove(view);
		}
		editor = newValue;
		if (editor != null) {
			editor.add(view);
		}
	}

	/**
	 * Gets the drawing editor of the project.
	 */
	public DrawingEditor getDrawingEditor() {
		return editor;
	}

	/**
	 * Clears the project.
	 */
	public void clear() {
		DefaultHtmlDrawing htmlDrawing = new DefaultHtmlDrawing();
		htmlDrawing.setProject(this);
		view.setDrawing(htmlDrawing);
		htmlDrawing.createTopParent(view);
		undo.discardAllEdits();
	}

	@Override protected JFileChooser createOpenChooser() {
		JFileChooser c = super.createOpenChooser();
		c.addChoosableFileFilter(new ExtensionFileFilter("Drawing","xml"));
		return c;
	}
	@Override protected JFileChooser createSaveChooser() {
		JFileChooser c = super.createSaveChooser();
		c.addChoosableFileFilter(new ExtensionFileFilter("Drawing","xml"));
		return c;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		JSplitPane mainPane = new JSplitPane();
		mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		mainPane.setBorder(null);
		
		setAttributePanel(new AttributePanel());

		scrollPane = new javax.swing.JScrollPane();
		view = new DefaultHtmlDrawingView();

		setLayout(new java.awt.BorderLayout());

		scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(view);

		mainPane.setLeftComponent(attributePanel);
		mainPane.setRightComponent(scrollPane);
		mainPane.setDividerLocation(400);
		
		add(mainPane, java.awt.BorderLayout.CENTER);

	}// </editor-fold>//GEN-END:initComponents


	public AttributePanel getAttributePanel() {
		return attributePanel;
	}

	public void setAttributePanel(AttributePanel attributePanel) {
		this.attributePanel = attributePanel;
	}
	
	@Override
	public void setFile(File newValue){
		super.setFile(newValue);
		view.getDrawing().setTopParent(topParent);
	}


	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane scrollPane;
	private DefaultHtmlDrawingView view;
	private AttributePanel attributePanel;
	private TopParentHtmlFigure topParent;
	// End of variables declaration//GEN-END:variables

}
