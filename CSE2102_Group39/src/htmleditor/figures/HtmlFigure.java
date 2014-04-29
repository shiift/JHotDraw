package htmleditor.figures;


import htmleditor.AttributePanel;
import htmleditor.AttributeValue;
import htmleditor.DefaultHtmlDrawing;
import htmleditor.StyleBuilder;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class HtmlFigure extends RectangleFigure {

	protected LinkedList<HtmlFigure> figureList;
	protected HashMap<String, AttributeValue> attributeList;
	protected HtmlFigure parent;
	protected String tag;
	protected String name;
	protected int fileControl;
	protected int StyleControl;
	protected StyleBuilder _style;
	public boolean isTopParent = false;
	protected AttributePanel attributePanel;
	private String PageName;
	private String PageColor;
	
	/** Creates a new instance. */
	public HtmlFigure() {
		this(0, 0, 0, 0);
	}

	public HtmlFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		figureList = new LinkedList<HtmlFigure>();
		attributeList = new HashMap<String, AttributeValue>();
		_style = new StyleBuilder(this);
		parent = null;
		tag = "";
		name = "";
	}

	public HtmlFigure clone() {
		HtmlFigure that = (HtmlFigure) super.clone();
		that.figureList = new LinkedList<HtmlFigure>();
		that.attributeList = new HashMap<String, AttributeValue>();
		that._style = new StyleBuilder(that);
		that.parent = null;
		that.tag = "";
		that.name = "";

        this.addStyle("top", String.valueOf(rectangle.x));
        this.addStyle("left", String.valueOf(rectangle.y));
		return that;
	}

	//Limits movement of the object within its parent.
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
		super.basicSetBounds(anchor, lead);
		if(parent != null){
			Rectangle2D.Double pRectangle = parent.rectangle;
			rectangle.x = Math.min(anchor.x, lead.x);
			rectangle.y = Math.min(anchor.y , lead.y);
			rectangle.width = Math.max(5, Math.abs(lead.x - anchor.x));
			rectangle.height = Math.max(5, Math.abs(lead.y - anchor.y));
	
			if(!this.isChanging() || (rectangle.x != 0 || rectangle.y != 0)){
				if(rectangle.x <= pRectangle.x){
					rectangle.x = pRectangle.x;
				}
				if(rectangle.y <= pRectangle.y){
					rectangle.y = pRectangle.y;
				}		
			}
			if(rectangle.x + rectangle.width >= pRectangle.x + pRectangle.width){
				rectangle.x = pRectangle.x + pRectangle.width - rectangle.width;
			}
			if(rectangle.y + rectangle.height >= pRectangle.y + pRectangle.height){
				rectangle.y = pRectangle.y + pRectangle.height - rectangle.height;
			}
			if(rectangle.width > pRectangle.width){
				rectangle.width = pRectangle.width;
			}
			if(rectangle.height >= pRectangle.height){
				rectangle.height = pRectangle.height;
			}
		}else{
			super.basicSetBounds(anchor, lead);
		}
        this.setStyle("left", String.valueOf(rectangle.x));
		this.setStyle("top", String.valueOf(rectangle.y));
	}

	//Moves the children within the parent if it moves past the bounds of the child.
	@Override
	public void basicTransform(AffineTransform tx) {
		super.basicTransform(tx);
		getDrawing().bringToFront(this);
		for(int i = 0; i < figureList.size(); i++){
			figureList.get(i).basicTransform(new AffineTransform(1, 0, 0, 1, 0, 0));
		}
	}

	//Figure list methods
	public LinkedList<HtmlFigure> getObjectList(){
		return figureList;
	}
	public void addHtmlObject(HtmlFigure object){
		figureList.add(object);
	}

	public HtmlFigure removeHtmlFigure(int location){
		return figureList.remove(location);
	}
	
	public void clearFigureList(){
		this.figureList = new LinkedList<HtmlFigure>();
	}
	
	// Attribute list methods
	public HashMap<String, AttributeValue> getAttributeList() {
		return attributeList;
	}

	public void addHtmlAttribute(HtmlFigure figure, String attribute, String value)
	{
		figure.addHtmlAttribute(figure, attribute, value, true);
	}
	
	public void addHtmlAttribute(HtmlFigure figure, String attribute, String value, boolean editable)
	{
		AttributeValue style = new AttributeValue(value, editable);
		figure.addHtmlAttribute(attribute, style);
	}
	
	public void addHtmlAttribute(String attributeName, AttributeValue attributeValue) {
		attributeList.put(attributeName, attributeValue);
		fileControl++;
	}
	
	public AttributeValue removeHtmlAttribute(String key) {
		fileControl--;
		return attributeList.remove(key);
	}
	
	public void setAttributeList(HashMap<String, AttributeValue> list){
		attributeList = list;
		fileControl = attributeList.size();
	}


	// parent methods
	public HtmlFigure getParent() {
		return parent;
	}

	public void setParent(HtmlFigure parent) {
		this.parent = parent;
	}

	public Drawing getDrawSpace(){
		return this.getDrawing();
	}

	// Tag methods
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


	// Name methods
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// Control methods
	public void setControl(int c){
		fileControl = c;
	}
	public int getControl(){
		return fileControl;
	}
	public void setSControl(int c){
		StyleControl = c;
	}
	public int getSControl(){
		return StyleControl;
	}
	
	// Style methods
	public void addStyle(String key, String value)
	{
		_style.addStyleAttribute(key, value);
		StyleControl++;
	}
	
	public String getStyle(String key)
	{
		return _style.getStyleValue(key);
	}
	public String getStyleString()
	{
		return _style.getStyleValueString();
	}
	public void removeStyle(String key){
		_style.removeStyleAttribute(key);
		StyleControl--;
	}
	
	public HashMap<String, String> getStyleMap(){
		return _style.getStyleMap();
	}
	
	// Informs a figure that it has been added to the specified drawing.
	// Gets the attribute panel if the project is not equal to null
	@Override
	public void addNotify(Drawing d){
		super.addNotify(d);
		DefaultHtmlDrawing htmlD = (DefaultHtmlDrawing) d; 
		if(htmlD.getProject() != null){
			addFigureListener(htmlD.getProject().getAttributePanel());
			this.attributePanel = htmlD.getProject().getAttributePanel();
		}
	}
	
	public void read(DOMInput in) throws IOException {
		double x = in.getAttribute("x", 0d);
		double y = in.getAttribute("y", 0d);
		double w = in.getAttribute("w", 0d);
		double h = in.getAttribute("h", 0d);
		String n = in.getAttribute("n", "null");
		String t = in.getAttribute("t", "null");
		isTopParent = in.getAttribute("top", false);
		int _control = in.getAttribute("control", 0);
		int _control2 = in.getAttribute("scontrol", 0);
		if(isTopParent){
			this.setPageName(in.getAttribute("pname", "Page Name"));
			this.setPageColor(in.getAttribute("pcolor", "white"));
		}
		else{
			in.getAttribute("pname", "Page Name");
			in.getAttribute("pcolor", "white");
		}
		_style = new StyleBuilder(this);
		for(int i = 1; i<_control+1;i++){
			String name = in.getAttribute("n"+Integer.toString(i), null);
			String value = in.getAttribute("v"+Integer.toString(i), null);
			boolean edit = in.getAttribute("e"+Integer.toString(i), false);
			if(name!=null){
				this.addHtmlAttribute(name, new AttributeValue(value,edit));
			}
		}
		for(int i = 1; i<_control2+1;i++){
			String name = in.getAttribute("sn"+Integer.toString(i), null);
			String value = in.getAttribute("sv"+Integer.toString(i), null);
			if(name!=null){
				this._style.addStyleAttribute(name, value);
			}
		}
		this.setControl(_control);
		this.setSControl(_control2);
		this.setTag(t);
		this.setName(n);
		setBounds(new Point2D.Double(x,y), new Point2D.Double(x+w,y+h));
		readAttributes(in);
	}
	
	// Sets page elements
    private void setPageColor(String attribute) {
		PageColor = attribute;
		
	}

	private void setPageName(String attribute) {
		PageName = attribute;
	}

	public void write(DOMOutput out) throws IOException {
        Rectangle2D.Double r = getBounds();
        out.addAttribute("x", r.x);
        out.addAttribute("y", r.y);
        out.addAttribute("w", r.width);
        out.addAttribute("h", r.height);
        out.addAttribute("n", this.getName());
        out.addAttribute("t", this.getTag());
        out.addAttribute("top", this.isTopParent);
        out.addAttribute("control", fileControl);
        out.addAttribute("scontrol", StyleControl);
        out.addAttribute("pname", PageName);
        out.addAttribute("pcolor",PageColor);
        int control = 1;
        for(Entry<String, AttributeValue> entry : attributeList.entrySet()){
            out.addAttribute("n"+Integer.toString(control), entry.getKey());
            out.addAttribute("v"+Integer.toString(control), entry.getValue().getValue());
            out.addAttribute("e"+Integer.toString(control), entry.getValue().isEditable());
            control++;
        }
        control = 1;
        for(Entry<String, String> entry : _style.getStyleMap().entrySet()){
            out.addAttribute("sn"+Integer.toString(control), entry.getKey());
            out.addAttribute("sv"+Integer.toString(control), entry.getValue());
            control++;
        }
        writeAttributes(out);
    }

	public AttributePanel getAttributePanel() {
		return attributePanel;
	}

	public void setStyle(String key, String value) {
		addStyle(key,value);
		if(key.equals("top") || key.equals("left")){
    		value = value.replaceAll("[^\\d.]", "");
    		Rectangle2D.Double size = (java.awt.geom.Rectangle2D.Double) rectangle.clone();
	    	if(key.equals("top")){
	    		size.y = Double.parseDouble(value);
	    	}
	    	if(key.equals("left")){
	    		size.x = Double.parseDouble(value);
	    	}
    		restoreTo(size);
    		invalidate();
    	}
	}

	public AttributeValue getAttributeValue(String name) {
		return attributeList.get(name);
	}
	
}
