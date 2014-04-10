package htmleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class HtmlExporter implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		HtmlParentAssigner.actionPerformed();
		try {
			HtmlParser.createFile(Global.topParent);
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HtmlParentAssigner.actionRelease();
	}

}
