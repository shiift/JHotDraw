package htmleditor;

import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.FONT_SIZE;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINED;
import static org.jhotdraw.draw.AttributeKeys.TEXT;
import static org.jhotdraw.draw.AttributeKeys.TEXT_COLOR;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FontSizeHandle;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.MoveHandle;
import org.jhotdraw.draw.RelativeLocator;
import org.jhotdraw.draw.TextHolder;
import org.jhotdraw.draw.TextTool;
import org.jhotdraw.draw.Tool;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.geom.Insets2DDouble;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class ParagraphFigure extends HtmlFigure implements TextHolder {

	protected Point2D.Double origin = new Point2D.Double();
	transient private TextLayout textLayout;
	private boolean editable = true;
	
	public ParagraphFigure(){
		this(0,0,0,0);
	}

	public ParagraphFigure(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.setText("text");
	}
	
	@Override
	public ParagraphFigure clone(){
		ParagraphFigure that = (ParagraphFigure) super.clone();
		return that;
	}
	
	//Implemented Methods
	@Override
	public boolean isEditable() {
		return this.editable;
	}
	
	public void setEditable(boolean b){
		this.editable = b;
	}

	@Override
	public Font getFont() {
		return AttributeKeys.getFont(this);
	}

	@Override
	public Color getTextColor() {
		return TEXT_COLOR.get(this);
	}

	@Override
	public Color getFillColor() {
		return FILL_COLOR.get(this);
	}

	@Override
	public TextHolder getLabelFor() {
		return this;
	}

	@Override
	public int getTabSize() {
		// TextHandler had 8.
		return 8;
	}

	@Override
	public String getText() {
		return (String) getAttribute(TEXT);
	}

	@Override
	public void setText(String text) {
		 setAttribute(TEXT, text);
	}

	@Override
	public int getTextColumns() {
		return (getText() == null) ? 4 : Math.max(getText().length(), 4);
	}

	@Override
	public void setFontSize(float size) {
		FONT_SIZE.set(this, new Double(size));
	}

	@Override
	public float getFontSize() {
		return FONT_SIZE.get(this).floatValue();
	}

	@Override
	public Insets2DDouble getInsets() {
		return new Insets2DDouble(0,0,0,0);
	}

	protected void drawText(java.awt.Graphics2D g) {
        if (getText() != null || isEditable()) {
            TextLayout layout = getTextLayout();
            layout.draw(g, (float) origin.x, (float) (origin.y + layout.getAscent()));
        }
    }
	
	protected TextLayout getTextLayout() {
        if (textLayout == null) {
            String text = getText();
            if (text == null || text.length() == 0) {
                text = " ";
            }
            
            FontRenderContext frc = getFontRenderContext();
            HashMap<TextAttribute,Object> textAttributes = new HashMap<TextAttribute,Object>();
            textAttributes.put(TextAttribute.FONT, getFont());
            if (FONT_UNDERLINED.get(this)) {
                textAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
            }
            textLayout = new TextLayout(text, textAttributes, frc);
        }
        return textLayout;
    }
	
	public Rectangle2D.Double getBounds() {
        TextLayout layout = getTextLayout();
        Rectangle2D.Double r = new Rectangle2D.Double(origin.x, origin.y, layout.getAdvance(), 
                layout.getAscent() + layout.getDescent());
        return r;
    }
    public Dimension2DDouble getPreferredSize() {
        Rectangle2D.Double b = getBounds();
        return new Dimension2DDouble(b.width, b.height);
    }
    public Rectangle2D.Double getFigureDrawBounds() {
        if (getText() == null) {
            return getBounds();
        } else {
            TextLayout layout = getTextLayout();
            Rectangle2D.Double r = new Rectangle2D.Double(
                    origin.x, origin.y, layout.getAdvance(), layout.getAscent()
                    );
            Rectangle2D lBounds = layout.getBounds();
            if (! lBounds.isEmpty() && ! Double.isNaN(lBounds.getX())) {
                r.add(new Rectangle2D.Double(
                        lBounds.getX()+origin.x,
                        (lBounds.getY()+origin.y+layout.getAscent()),
                        lBounds.getWidth(),
                        lBounds.getHeight()
                        ));
            }
            // grow by two pixels to take antialiasing into account
            Geom.grow(r, 2d, 2d);
            return r;
        }
    }
    protected void drawStroke(java.awt.Graphics2D g) {
    }
    protected void drawFill(java.awt.Graphics2D g) {
    }
    
    public boolean contains(Point2D.Double p) {
        if (getBounds().contains(p)) {
            return true;
        }
        if (decorator != null) {
            updateDecoratorBounds();
            return decorator.contains(p);
        }
        return false;
    }
    
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        if (detailLevel == 0) {
        handles.add(new MoveHandle(this, RelativeLocator.northWest()));
        handles.add(new MoveHandle(this, RelativeLocator.northEast()));
        handles.add(new MoveHandle(this, RelativeLocator.southEast()));
        handles.add(new FontSizeHandle(this));
        }
        return handles;
    }
    
    protected void validate() {
        super.validate();
        textLayout = null;
    }
    public Tool getTool(Point2D.Double p) {
        return (isEditable() && contains(p)) ? new TextTool(this) : null;
    }
    
    public void read(DOMInput in) throws IOException {
        setBounds(
                new Point2D.Double(in.getAttribute("x",0d), in.getAttribute("y",0d)),
                new Point2D.Double(0, 0)
                );
        readAttributes(in);
    }
    
    public void write(DOMOutput out) throws IOException {
        Rectangle2D.Double b = getBounds();
        out.addAttribute("x",b.x);
        out.addAttribute("y",b.y);
        writeAttributes(out);
    }
    
    public void invalidate() {
        super.invalidate();
        textLayout = null;
    }
    
    public void restoreTo(Object geometry) {
        Point2D.Double p = (Point2D.Double) geometry;
        origin.x = p.x;
        origin.y = p.y;
    }

    public Object getRestoreData() {
        return origin.clone();
    }
    
    @Override
    public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
        super.basicSetBounds(anchor, lead);
    	origin = new Point2D.Double(anchor.x, anchor.y);
    }
    @Override
    public void basicTransform(AffineTransform tx) {
        super.basicTransform(tx);
    }
	
}
