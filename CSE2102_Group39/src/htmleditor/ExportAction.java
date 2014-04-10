package htmleditor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.Project;
import org.jhotdraw.app.action.AbstractProjectAction;
import org.jhotdraw.app.action.SaveAction;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.event.SheetEvent;
import org.jhotdraw.gui.event.SheetListener;
import org.jhotdraw.io.ExtensionFileFilter;
import org.jhotdraw.util.ResourceBundleUtil;
import org.jhotdraw.util.Worker;

public class ExportAction extends AbstractProjectAction {
	public final static String ID = "export";
	protected boolean saveAs;
    protected Component oldFocusOwner;
	
    /** Creates a new instance. */
    public ExportAction(Application app) {
        this(app, false);
    }
    /** Creates a new instance. */
    public ExportAction(Application app, boolean saveAs) {
        super(app);
        this.saveAs = saveAs;
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }
    
    public void actionPerformed(ActionEvent evt) {
        final Project project = getCurrentProject();
        if (project.isEnabled()) {
            oldFocusOwner = SwingUtilities.getWindowAncestor(project.getComponent()).getFocusOwner();
            project.setEnabled(false);

            JFileChooser fileChooser = project.getSaveChooser();
            
            JSheet.showSaveSheet(fileChooser, project.getComponent(), new SheetListener() {
                public void optionSelected(final SheetEvent evt) {
                    if (evt.getOption() == JFileChooser.APPROVE_OPTION) {
                        final File file;
                            file = evt.getFileChooser().getSelectedFile();
                        saveToFile(project, file);
                    } else {
                        project.setEnabled(true);
                        if (oldFocusOwner != null) {
                            oldFocusOwner.requestFocus();
                        }
                    }
                }
            });
        }
    }
    
    protected void saveToFile(final Project project, final File file) {
        project.execute(new Worker() {
            public Object construct() {
                try {
            		HtmlParser.createFile(Global.topParent, file);
                    return null;
                } catch (IOException e) {
                    return e;
                }
            }
            public void finished(Object value) {
                fileSaved(project, file, value);
            }
        });
    }
    
    /**
     * XXX - Change type of value to Throwable
     *
     * @param value is either null for success or a Throwable on failure.
     */
    protected void fileSaved(final Project project, File file, Object value) {
        if (value != null) {
            JSheet.showMessageSheet(project.getComponent(),
            "<html>"+UIManager.getString("OptionPane.css")+
            "<b>Couldn't save to the file \""+file+"\".<p>"+
            "Reason: "+value,
            JOptionPane.ERROR_MESSAGE
            );
        }
        project.setEnabled(true);
        SwingUtilities.getWindowAncestor(project.getComponent()).toFront();
        if (oldFocusOwner != null) {
            oldFocusOwner.requestFocus();
        }
    }
}
