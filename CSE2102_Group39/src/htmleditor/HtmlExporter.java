package htmleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HtmlExporter implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		HtmlParser.createDummyOutput();
		
	}

}