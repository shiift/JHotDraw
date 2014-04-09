package htmleditor;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.draw.FigureSelectionEvent;
import org.jhotdraw.draw.FigureSelectionListener;

public class AttributePanel extends JPanel implements FigureSelectionListener, FigureListener {
	
	private JPanel optionsPanel;
	private HtmlFigure[] figureArray;
	private HashMap<String, JTextField> attributeFields;
		
	public AttributePanel(){
		
		attributeFields = new HashMap<String, JTextField>();
		
		setPreferredSize(new Dimension(250,0));
        setBorder(new EmptyBorder(30,0,0,0));
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JSplitPane mainPane = new JSplitPane();
        mainPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainPane.setDividerSize(0);
        
        javax.swing.JTextPane textPane = new JTextPane();
        textPane.setText("HTML Attribute Pane");
        textPane.setBackground(null);
        
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        
        mainPane.setTopComponent(textPane);
        mainPane.setBottomComponent(optionsPanel);
        
        add(mainPane);
	}
	
	@Override
	public void selectionChanged(FigureSelectionEvent evt) {
		attributeFields.clear();
		optionsPanel.removeAll();
		
		Collection<Figure> figureCol = evt.getView().getSelectedFigures();
		figureArray = figureCol.toArray(new HtmlFigure[figureCol.size()]);
		
		HashMap<String, AttributeValue> allAttributes = new HashMap<String, AttributeValue>();
		HashMap<String, AttributeValue> sharedAttributes = new HashMap<String, AttributeValue>();
		
		if(figureArray.length == 1){
			sharedAttributes = figureArray[0].getAttributeList();
		}else{
			for(int i = 0; i < figureArray.length; i++){
				HtmlFigure cFigure = (HtmlFigure) figureArray[i];
				for(Entry<String, AttributeValue> entry : cFigure.getAttributeList().entrySet()){
					if(allAttributes.containsKey(entry.getKey())){
						sharedAttributes.put(entry.getKey(), entry.getValue());
					}
					allAttributes.put(entry.getKey(), entry.getValue());
				}
			}
		}
		
		for(Entry<String, AttributeValue> entry : sharedAttributes.entrySet()){
			ActionListener setListener = new ActionListener() {
	        	public void actionPerformed(ActionEvent e){
	        		for(int i = 0; i < figureArray.length; i++){
	        			HtmlFigure cFigure = (HtmlFigure) figureArray[i];
	        			cFigure.addHtmlAttribute(e.getActionCommand(), new AttributeValue(attributeFields.get(e.getActionCommand()).getText()));
	        			System.out.println(e.getActionCommand() + ": " + attributeFields.get(e.getActionCommand()).getText());
	        		}
	        	}
	        };
			
			JPanel newPanel = new JPanel();
			newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));
			
			newPanel.add(new JLabel(entry.getKey()));
			
			JTextField newField = new JTextField(entry.getValue().getValue());
			newField.setMaximumSize(new Dimension(100, 20));
			newField.setEditable(entry.getValue().isEditable());
			
			newPanel.add(newField);
			attributeFields.put(entry.getKey(), newField);
			
			JButton newButton = new JButton("Set " + entry.getKey());
			newButton.addActionListener(setListener);
			newButton.setActionCommand(entry.getKey());
			newButton.setEnabled(entry.getValue().isEditable());
			
			newPanel.add(newButton);
			
			optionsPanel.add(newPanel);
		}
		
		revalidate();
		repaint();
	}

	@Override
	public void figureAreaInvalidated(FigureEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void figureAttributeChanged(FigureEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void figureChanged(FigureEvent e) {
		HtmlFigure figure = (HtmlFigure) e.getFigure();
		
		for(Entry<String, AttributeValue> entry : figure.getAttributeList().entrySet()){
			if(attributeFields.containsKey(entry.getKey())){
				attributeFields.get(entry.getKey()).setText(entry.getValue().getValue());
			}
		}
		
		revalidate();
		repaint();
	}

	@Override
	public void figureAdded(FigureEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void figureRemoved(FigureEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void figureRequestRemove(FigureEvent e) {
		// TODO Auto-generated method stub
		
	}

}
