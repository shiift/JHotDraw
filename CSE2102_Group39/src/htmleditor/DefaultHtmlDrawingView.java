package htmleditor;

import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;

public class DefaultHtmlDrawingView extends DefaultDrawingView {

    public DefaultHtmlDrawing getDrawing() {
        return (DefaultHtmlDrawing) super.getDrawing();
    }

    public void setDrawing(Drawing d) {
        if (this.getDrawing() != null) {
            this.getDrawing().removeDrawingListener(this);
            clearSelection();
        }
        this.drawing = d;
        if (this.getDrawing() != null) {
            this.getDrawing().addDrawingListener(this);
        }
        invalidateDimension();
        invalidate();
        if (getParent() != null) getParent().validate();
        repaint();
    }
}
