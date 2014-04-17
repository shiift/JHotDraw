package htmleditor;


import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class HtmlFigure extends RectangleFigure {

	protected LinkedList<HtmlFigure> figureList;
	protected HashMap<String, AttributeValue> attributeList;
	protected HtmlFigure parent;
	protected String tag;
	protected String name;
	protected String data;
	protected int fileControl;
	public boolean isTopParent = false;

	/** Creates a new instance. */
	public HtmlFigure() {
		this(0, 0, 0, 0);
	}

	public HtmlFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		figureList = new LinkedList<HtmlFigure>();
		attributeList = new HashMap<String, AttributeValue>();
		parent = Global.topParent;
		tag = "";
		name = "";
		data = "";
		this.addFigureListener(Global.attributePanel);
	}

	public HtmlFigure clone() {
		HtmlFigure that = (HtmlFigure) super.clone();
		that.figureList = new LinkedList<HtmlFigure>();
		that.attributeList = new HashMap<String, AttributeValue>();
		that.parent = Global.topParent;
		that.tag = "";
		that.name = "";
		that.data = "";
		that.addFigureListener(Global.attributePanel);
		return that;
	}

	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
		if(parent != null){
			Rectangle2D.Double pRectangle = parent.rectangle;
			rectangle.x = Math.min(anchor.x, lead.x);
			rectangle.y = Math.min(anchor.y , lead.y);
			rectangle.width = Math.max(5, Math.abs(lead.x - anchor.x));
			rectangle.height = Math.max(5, Math.abs(lead.y - anchor.y));
	
			if(!this.isChanging() || (rectangle.x != 0 || rectangle.y != 0)){
				if(rectangle.x <= pRectangle.x){
					rectangle.x = pRectangle.x + 10;
				}
				if(rectangle.y <= pRectangle.y){
					rectangle.y = pRectangle.y + 10;
				}		
			}
			if(rectangle.x + rectangle.width >= pRectangle.x + pRectangle.width){
				rectangle.x = pRectangle.x + pRectangle.width - rectangle.width - 10;
			}
			if(rectangle.y + rectangle.height >= pRectangle.y + pRectangle.height){
				rectangle.y = pRectangle.y + pRectangle.height - rectangle.height - 10;
			}
			if(rectangle.width > pRectangle.width - 20){
				rectangle.width = pRectangle.width - 20;
			}
			if(rectangle.height >= pRectangle.height - 20){
				rectangle.height = pRectangle.height - 20;
			}
//			this.getDrawing().bringToFront(this);
//			for(int i = 0; i < parent.getObjectList().size(); i++){
//				HtmlFigure compFigure = parent.getObjectList().get(i);
//				if(!compFigure.equals(this)){
//					Point2D.Double tr = new Point2D.Double(this.getStartPoint().x + this.getEndPoint().x, this.getStartPoint().y);
//					Point2D.Double bl = new Point2D.Double(this.getStartPoint().x, this.getStartPoint().y + this.getEndPoint().y);
//					if(compFigure.contains(this.getStartPoint()) || compFigure.contains(this.getEndPoint())
//							|| compFigure.contains(tr) || compFigure.contains(bl)){
//					}
//					System.out.println("Collide");
//				}
//			}
		}else{
			super.basicSetBounds(anchor, lead);
		}
	}
	
	public void basicTransform(AffineTransform tx) {
		super.basicTransform(tx);
		for(int i = 0; i < figureList.size(); i++){
			figureList.get(i).basicTransform(new AffineTransform(1, 0, 0, 1, 0, 0));;
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
		//TODO check this once saving works
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

	//isData methods
	public String getData(){
		return this.data;
	}

	public void setData(String data){
		this.data = data;
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
	
	public void setControl(int c){
		fileControl = c;
	}
	public int getControl(){
		return fileControl;
	}
	
	public void read(DOMInput in) throws IOException {
		double x = in.getAttribute("x", 0d);
		double y = in.getAttribute("y", 0d);
		double w = in.getAttribute("w", 0d);
		double h = in.getAttribute("h", 0d);
		String n = in.getAttribute("n", "null");
		String t = in.getAttribute("t", "null");
		int _control = in.getAttribute("control", 0);
		for(int i = 1; i<_control+1;i++){
			String name = in.getAttribute("n"+Integer.toString(i), null);
			String value = in.getAttribute("v"+Integer.toString(i), null);
			boolean edit = in.getAttribute("e"+Integer.toString(i), false);
			if(name!=null){
				this.addHtmlAttribute(name, new AttributeValue(value,edit));
			}
		}
		this.setControl(_control);
		this.setTag(t);
		this.setName(n);
		setBounds(new Point2D.Double(x,y), new Point2D.Double(x+w,y+h));
		readAttributes(in);
	}
    public void write(DOMOutput out) throws IOException {
        Rectangle2D.Double r = getBounds();
        out.addAttribute("x", r.x);
        out.addAttribute("y", r.y);
        out.addAttribute("w", r.width);
        out.addAttribute("h", r.height);
        out.addAttribute("n", this.getName());
        out.addAttribute("t", this.getTag());
        out.addAttribute("control", fileControl);
        System.out.println(fileControl);
        int control = 1;
        for(Entry<String, AttributeValue> entry : attributeList.entrySet()){
            out.addAttribute("n"+Integer.toString(control), entry.getKey());
            out.addAttribute("v"+Integer.toString(control), entry.getValue().getValue());
            out.addAttribute("e"+Integer.toString(control), entry.getValue().isEditable());
            control++;
        }
        writeAttributes(out);
    }
	
}
