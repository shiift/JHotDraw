package htmleditor;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.jhotdraw.draw.FigureSelectionEvent;
import org.jhotdraw.draw.FigureSelectionListener;

public class AttributePanel extends JPanel implements FigureSelectionListener {

	public AttributePanel(){
		setPreferredSize(new Dimension(200,0));
        setBorder(new EmptyBorder(30,0,0,0));
        
        javax.swing.JTextPane textPane = new JTextPane();
        textPane.setText("HTML Attribute Pane");
        textPane.setBackground(null);
        
        add(textPane);
	}
	
	@Override
	public void selectionChanged(FigureSelectionEvent evt) {
		System.out.println("I am the AttributePanel... fear me");
		
	}

}
