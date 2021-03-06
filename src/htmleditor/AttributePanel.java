package htmleditor;

import htmleditor.figures.HtmlFigure;
import htmleditor.figures.TopParentHtmlFigure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.draw.FigureSelectionEvent;
import org.jhotdraw.draw.FigureSelectionListener;

// A JSplitPane makes up the AttributePanel
// The top panel is for the legend and the bottom panel
// is for the attributes of each HtmlFigures
// for what the color for each HtmlFigure represents

public class AttributePanel extends JPanel implements FigureSelectionListener, FigureListener {

	private JPanel optionsPanel;
	private JPanel colorPane;
	private GridBagConstraints c;
	private HtmlFigure[] figureArray;
	private HashMap<String, JTextField> attributeFields;
	private HashMap<String, JTextField> styleFields;
	private FigureSelectionEvent _evt;
	private int xValue = 0;
	private int yValue = 0;
	private JTextField nameField;
	private JTextField colorField;
	
	public AttributePanel(){
		
		// attributeFields are HashMaps with strings as keys and JTextFields as values
		attributeFields = new HashMap<String, JTextField>();
		styleFields = new HashMap<String, JTextField>();

		// Used for the visual appearance of the AttributePanel
		setPreferredSize(new Dimension(500,0));
        setBorder(new EmptyBorder(30,0,0,0));
        
        // Components are laid out vertically from top to bottom
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Vertical split across the main pane used
        JSplitPane mainPane = new JSplitPane();
        mainPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainPane.setDividerSize(0);
        mainPane.setBorder(null);
        
        // Adds a textPane as a header for the HTML Attribute Pane, text centered
        JTextPane textPane = new JTextPane();
        textPane.setText("HTML Attribute Pane");
        textPane.setEditable(false);
        textPane.setBackground(null);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);
        textPane.setParagraphAttributes(attribs,true);
        
        // The colorPane is instantiated
        colorPane = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        
        // The colorPaneContainer is made up of the colorPane and the 
        // colorPaneLabel, which just is text that says Color Legend
        JSplitPane colorPaneContainer = new JSplitPane();
        JTextPane colorPaneLabel = new JTextPane();
        colorPaneLabel.setText("Color Legend");
        colorPaneLabel.setEditable(false);
        colorPaneLabel.setBackground(null);
        colorPaneLabel.setParagraphAttributes(attribs,true);
        colorPaneContainer.setOrientation(JSplitPane.VERTICAL_SPLIT);
        colorPaneContainer.setDividerSize(0);
        colorPaneContainer.setBorder(null);
        colorPaneContainer.setTopComponent(colorPaneLabel);
        colorPaneContainer.setBottomComponent(colorPane);
        
        // The headerPane has the top component as the legend
        // and the bottom component as the text "Html Attribute Pane"
        JSplitPane headerPane = new JSplitPane();
        headerPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        headerPane.setBottomComponent(textPane);
        headerPane.setTopComponent(colorPaneContainer);
        headerPane.setBorder(null);
        
        // Instantiates the optionsPanel with vertical set-up
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

		// The top of the mainPane is the legend and text
		// and the bottom pane is the optionsPanel for the attributes of each HtmlFigure
		mainPane.setTopComponent(headerPane);
		mainPane.setBottomComponent(optionsPanel);

		// Add elements to the legend
		add(mainPane);

        addColorPane("div", Color.LIGHT_GRAY, "Division");
        addColorPane("a", Color.ORANGE, "Anchor");
        addColorPane("img", Color.CYAN, "Image");
        addColorPane("p", Color.WHITE, "Paragraph");
        addColorPane("ul", Color.GREEN, "Unordered List");
        addColorPane("ol", Color.YELLOW, "Ordered List");
        addColorPane("embed", Color.PINK, "Embedded Video");
        addColorPane("iframe", Color.RED, "Embedded Web Page");

	}

	@Override
	public void selectionChanged(FigureSelectionEvent evt) {
		_evt = evt;
		
		// Clears the attributes when you select a different figure in the view
		attributeFields.clear();
		optionsPanel.removeAll();

		// Get the currently selected figures in the view
		Collection<Figure> figureCol = evt.getView().getSelectedFigures();
		figureArray = figureCol.toArray(new HtmlFigure[figureCol.size()]);

		// Create a HashMap of all of the Attributes as well as all of the
		// attributes that are shared between the various figures that are selected
		HashMap<String, AttributeValue> allAttributes = new HashMap<String, AttributeValue>();
		HashMap<String, AttributeValue> sharedAttributes = new HashMap<String, AttributeValue>();

		// Set up the namePanel and make it set up horizontally
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		JTextPane objectName = new JTextPane();

		// If only one figure is selected, then the sharedAttributes are just the attributes
		// Of that figure and the text is set to the name of the figure
		if(figureArray.length == 1){
			sharedAttributes = figureArray[0].getAttributeList();
			objectName.setText(figureArray[0].getName());
		}else{
			// For each figure selected
			for(int i = 0; i < figureArray.length; i++){
				HtmlFigure cFigure = (HtmlFigure) figureArray[i];
				// For each entry of the attribute HashMap of the HtmlFigure, if all of the
				// HtmlFigures have that attribute, then it goes into the sharedAttribute HashMap
				// If it is editable, then it is put in allAttributes
				for(Entry<String, AttributeValue> entry : cFigure.getAttributeList().entrySet()){
					if(allAttributes.containsKey(entry.getKey())){
						sharedAttributes.put(entry.getKey(), entry.getValue());
					}
					if(entry.getValue().isEditable()){
						allAttributes.put(entry.getKey(), entry.getValue());
					}
				}
			}
			if(figureArray.length == 0){
				objectName.setText("None");
			}else{
				objectName.setText("Multiple Figures");
			}
		}

		// Add the HtmlFigure's name (or "Multiple Figures") to the optionsPanel
		objectName.setMaximumSize(new Dimension(400, 30));
		objectName.setEditable(false);
		objectName.setBackground(null);
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);
		objectName.setParagraphAttributes(attribs,true);
		namePanel.add(objectName);

		optionsPanel.add(namePanel);

		JTextField attributeTitle = new JTextField();
		attributeTitle.setText("Attributes");
		attributeTitle.setMaximumSize(new Dimension(100, 30));
		attributeTitle.setEditable(false);
		attributeTitle.setBorder(null);
		attributeTitle.setBackground(null);
		attributeTitle.setAlignmentX(CENTER_ALIGNMENT);
		optionsPanel.add(attributeTitle);

		// For each attribute that is shared, for each HtmlFigure that is
		// selected, add the changed attribute to the HashMap in the HtmlFigure
		for(Entry<String, AttributeValue> entry : sharedAttributes.entrySet()){
			ActionListener setListener = new ActionListener() {
				public void actionPerformed(ActionEvent e){
					for(int i = 0; i < figureArray.length; i++){
						HtmlFigure cFigure = (HtmlFigure) figureArray[i];
						cFigure.addHtmlAttribute(e.getActionCommand(), 
								new AttributeValue(attributeFields.get(e.getActionCommand()).getText()));
					}
					paneChanged();
				}
			};
			ActionListener setListenerRemove = new ActionListener() {
				public void actionPerformed(ActionEvent e){
					for(int i = 0; i < figureArray.length; i++){
						HtmlFigure cFigure = (HtmlFigure) figureArray[i];
						cFigure.removeHtmlAttribute(e.getActionCommand());
					}
					paneChanged();
				}
			};

			JPanel newPanel = new JPanel();
			newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));

			JLabel label = new JLabel(entry.getKey());
			label.setToolTipText(entry.getKey());
			newPanel.add(label);

			JTextField newField = new JTextField(entry.getValue().getValue());
			newField.setToolTipText(entry.getValue().getValue());
			newField.setMaximumSize(new Dimension(100, 30));
			newField.setEditable(entry.getValue().isEditable());

			newPanel.add(newField);
			attributeFields.put(entry.getKey(), newField);

			//Set and Remove Buttons
			JButton newButton = new JButton("Set " + entry.getKey());
			newButton.addActionListener(setListener);
			newButton.setActionCommand(entry.getKey());
			newButton.setEnabled(entry.getValue().isEditable());

			JButton newButtonR = new JButton("X");
			newButtonR.setToolTipText("Remove Attribute");
			newButtonR.addActionListener(setListenerRemove);
			newButtonR.setActionCommand(entry.getKey());
			if(entry.getKey().equals("src")||
					entry.getKey().equals("height")||
					entry.getKey().equals("width")||
					entry.getKey().equals("alt")||
					entry.getKey().equals("href")||
					entry.getKey().equals("type")){
				newButtonR.setEnabled(false);
			}

			newPanel.add(newButton);
			newPanel.add(newButtonR);

			optionsPanel.add(newPanel);
		}

		//Add Attribute
		JButton addAttribute = new JButton();
		addAttribute.setText("Add Attribute");
		addAttribute.setAlignmentX(CENTER_ALIGNMENT);
		addAttribute.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e) {
			String[] nameval = createAttribute();
			if(!nameval[0].equals("")){
				for(int i = 0; i < figureArray.length; i++){
					HtmlFigure cFigure = (HtmlFigure) figureArray[i];
					cFigure.addHtmlAttribute(cFigure, nameval[0], nameval[1]);
				}
			}
			paneChanged();
		}});
		optionsPanel.add(addAttribute);


		// StyleBuilder Fields
		styleFields.clear();

		JTextField styleBuilderTitle = new JTextField();
		styleBuilderTitle.setText("Styles");
		styleBuilderTitle.setEditable(false);
		styleBuilderTitle.setBackground(null);
		styleBuilderTitle.setMaximumSize(new Dimension(100, 30));
		styleBuilderTitle.setBorder(null);
		styleBuilderTitle.setAlignmentX(CENTER_ALIGNMENT);

		optionsPanel.add(styleBuilderTitle);

		HashMap<String, String> allStyles = new HashMap<String, String>();
		HashMap<String, String> sharedStyles = new HashMap<String, String>();

		if(figureArray.length == 1){
			sharedStyles = figureArray[0].getStyleMap();
		}else{
			// For each figure selected
			for(int i = 0; i < figureArray.length; i++){
				HtmlFigure cFigure = (HtmlFigure) figureArray[i];
				for(Entry<String, String> entry : cFigure.getStyleMap().entrySet()){
					if(allStyles.containsKey(entry.getKey())){
						sharedStyles.put(entry.getKey(), entry.getValue());
					}
					allStyles.put(entry.getKey(), entry.getValue());
				}
			}
		}

		for(Entry<String, String> entry : sharedStyles.entrySet()){
			ActionListener setListener = new ActionListener() {
				public void actionPerformed(ActionEvent e){
					for(int i = 0; i < figureArray.length; i++){
						HtmlFigure cFigure = (HtmlFigure) figureArray[i];
						cFigure.setStyle(e.getActionCommand(), 
								styleFields.get(e.getActionCommand()).getText());
					}
					paneChanged();
				}
			};
			ActionListener setListenerRemove = new ActionListener() {
				public void actionPerformed(ActionEvent e){
					for(int i = 0; i < figureArray.length; i++){
						HtmlFigure cFigure = (HtmlFigure) figureArray[i];
						cFigure.removeStyle(e.getActionCommand());
					}
					paneChanged();
				}
			};

			JPanel newPanel = new JPanel();
			newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));

			JLabel label = new JLabel(entry.getKey());
			label.setToolTipText(entry.getKey());
			newPanel.add(label);

			JTextField newField = new JTextField(entry.getValue());
			newField.setToolTipText(entry.getValue());
			newField.setMaximumSize(new Dimension(100, 30));

			newPanel.add(newField);
			styleFields.put(entry.getKey(), newField);

			//Set and Remove Buttons
			JButton newButton = new JButton("Set " + entry.getKey());
			newButton.addActionListener(setListener);
			newButton.setActionCommand(entry.getKey());

			JButton newButtonR = new JButton("X");
			newButtonR.setToolTipText("Remove Style");
			newButtonR.addActionListener(setListenerRemove);
			newButtonR.setActionCommand(entry.getKey());
			if(entry.getKey().equals("src")||
					entry.getKey().equals("left")||
					entry.getKey().equals("top")||
					entry.getKey().equals("height")||
					entry.getKey().equals("width")||
					entry.getKey().equals("font-size")||
					entry.getKey().equals("font-family")){
				newButtonR.setEnabled(false);
			}

			newPanel.add(newButton);
			newPanel.add(newButtonR);

			optionsPanel.add(newPanel);
		}

		//Add Style Button.
		JButton addStyleB = new JButton();
		addStyleB.setText("Add Style");
		addStyleB.setAlignmentX(CENTER_ALIGNMENT);
		addStyleB.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e) {
			String[] nameval = createStyle();
			if(!nameval[0].equals("")){
				for(int i = 0; i < figureArray.length; i++){
					HtmlFigure cFigure = (HtmlFigure) figureArray[i];
					cFigure.addStyle(nameval[0], nameval[1]);
				}
			}
			paneChanged();
		}});
		optionsPanel.add(addStyleB);
		
		final TopParentHtmlFigure topP = ((DefaultHtmlDrawingView) _evt.getView()).getDrawing().getTopParent();
		
		ActionListener setPageListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				topP.setPageName(nameField.getText());
				paneChanged();
			}
		};
		
		// Sets options for the page, such as page name and page color
		JTextField pageBuilderTitle = new JTextField();
		pageBuilderTitle.setText("Page Options");
		pageBuilderTitle.setEditable(false);
		pageBuilderTitle.setBackground(null);
		pageBuilderTitle.setMaximumSize(new Dimension(100, 30));
		pageBuilderTitle.setBorder(null);
		pageBuilderTitle.setAlignmentX(CENTER_ALIGNMENT);
		optionsPanel.add(pageBuilderTitle);

		JPanel pnamePanel = new JPanel();
		pnamePanel.setLayout(new BoxLayout(pnamePanel, BoxLayout.X_AXIS));
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText("Page Name");
		pnamePanel.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setText(topP.getPageName());
		nameField.setToolTipText("Page Title");
		nameField.setMaximumSize(new Dimension(100, 30));
		pnamePanel.add(nameField);
		
		JButton nameButton = new JButton();
		nameButton.setText("Set Name");
		nameButton.addActionListener(setPageListener);
		nameButton.setActionCommand(null);
		pnamePanel.add(nameButton);
		
		optionsPanel.add(pnamePanel);
		
		ActionListener setColorListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				topP.setPageColor(colorField.getText());
				paneChanged();
			}
		};

		JPanel pcolorPanel = new JPanel();
		pcolorPanel.setLayout(new BoxLayout(pcolorPanel, BoxLayout.X_AXIS));
		
		JLabel colorLabel = new JLabel();
		colorLabel.setText("Page Color");
		pcolorPanel.add(colorLabel);
		
		colorField = new JTextField();
		colorField.setText(topP.getPageColor());
		colorField.setToolTipText("Page Color");
		colorField.setMaximumSize(new Dimension(100, 30));
		pcolorPanel.add(colorField);
		
		JButton colorButton = new JButton();
		colorButton.setText("Set Color");
		colorButton.addActionListener(setColorListener);
		colorButton.setActionCommand(null);
		pcolorPanel.add(colorButton);
		
		optionsPanel.add(pcolorPanel);
		
		revalidate();
		repaint();
	}

	//Input methods for new user created Styles and Attributes.
	public String[] createStyle(){
		String name = JOptionPane.showInputDialog(this.getParent(), "What is the style name?");
		if(name == null || name.equals("")){ return new String[]{"",""}; }
		String value = JOptionPane.showInputDialog(this.getParent(), "What is the style value?");
		return new String[]{name, value};
	}
	public String[] createAttribute(){
		String name = JOptionPane.showInputDialog(this.getParent(), "What is the attribute name?");
		if(name == null || name.equals("")){ return new String[]{"",""}; }
		String value = JOptionPane.showInputDialog(this.getParent(), "What is the attribute value?");
		return new String[]{name, value};
	}
	
	@Override
	public void figureAreaInvalidated(FigureEvent e) {
	}

	@Override
	public void figureAttributeChanged(FigureEvent e) {
	}

	@Override
	public void figureChanged(FigureEvent e) {
		HtmlFigure figure = (HtmlFigure) e.getFigure();
		
		for(Entry<String, AttributeValue> entry : figure.getAttributeList().entrySet()){
			if(attributeFields.containsKey(entry.getKey())){
				attributeFields.get(entry.getKey()).setText(entry.getValue().getValue());
			}
		}
		for(Entry<String, String> entry : figure.getStyleMap().entrySet()){
			if(styleFields.containsKey(entry.getKey())){
				styleFields.get(entry.getKey()).setText(entry.getValue());
			}
		}
	}

	@Override
	public void figureAdded(FigureEvent e) {
	}

	@Override
	public void figureRemoved(FigureEvent e) {
	}

	@Override
	public void figureRequestRemove(FigureEvent e) {
	}
	
	// Using the color parameter that is given, set it
	// as the background of the panel created and the
	// text parameter is put over the color and
	// added to the colorPane to create the legend
	public void addColorPane(String name, Color color, String text){
		JPanel newPanel = new JPanel();
		newPanel.setBackground(color);
		JTextPane figureText = new JTextPane();
		figureText.setBackground(null);
		figureText.setText(name);
		figureText.setToolTipText(text);
		newPanel.add(figureText);
		newPanel.setSize(new Dimension(20, 20));
		newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		newPanel.setToolTipText(text);
		c.gridx = xValue;
		c.gridy = yValue;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		xValue++;
		if (xValue == 4)
		{
			xValue = 0;
			yValue++;
		}
		colorPane.add(newPanel, c);
		revalidate();
		repaint();
	}
	
	public void paneChanged(){
		selectionChanged(_evt);
	}

}
