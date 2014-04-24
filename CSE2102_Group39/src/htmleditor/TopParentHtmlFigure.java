package htmleditor;

import java.io.IOException;
import java.util.Map;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class TopParentHtmlFigure extends DivFigure {
	
	TopParentHtmlFigure(){
		
	}

	public TopParentHtmlFigure(int i, int j, int k, int l) {
		super(i,j,k,l);
		this.isTopParent = true;
	}
	
	public void read(DOMInput in) throws IOException {
		super.read(in);
    }
    public void write(DOMOutput out) throws IOException {
    	super.write(out);
    }
	

}
