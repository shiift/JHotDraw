package htmleditor;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.CellRendererPane;
import javax.swing.JTable;

import org.jhotdraw.draw.Drawing;

public class TableFigure extends HtmlFigure{
	
	private int row;
	private int column;
	
	public TableFigure(){
		this(0,0,0,0);
	}
	
	public TableFigure(double x, double y, double width, double height){
		super(x,y,width,height);
	}
	
	public TableFigure clone() {
		TableFigure that=(TableFigure)super.clone();
		row = 3;
		column = 3;
		System.out.println((double)row);
		return that;
	}
	
//	public void drawCells(){
//		Drawing app = this.getDrawing();
////		Rectangle2D.Double currectangle = this.rectangle;
//		double rspacing = (currectangle.x+currectangle.width)/((double) row);
//		double cspacing = (currectangle.y+currectangle.height)/((double) column);
//		double curx = currectangle.x;
//		double cury = currectangle.y;
//		System.out.println(currectangle.x);
//		System.out.println(rectangle.x);
//		System.out.println(row);
//		System.out.println(currectangle.width);
//		System.out.println(rspacing);
//		for(int i = 0; i<row; i++){
//			ParagraphFigure curCell = new ParagraphFigure();
//			curCell.rectangle.x = curx;
//			curCell.rectangle.y = cury;
//			curCell.rectangle.width = cspacing;
//			curCell.rectangle.height = rspacing;
//			app.add(curCell);
//		}
//	}
	
	public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead){
		super.basicSetBounds(anchor, lead);
	}
	
	public void basicTransform(AffineTransform tx) {
		super.basicTransform(tx);
//		this.drawCells();
	}
	
	//Will have to change since the cells are text boxes
	//Figure list methods
		@Override
		public LinkedList<HtmlFigure> getObjectList(){
			return null;
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
}
