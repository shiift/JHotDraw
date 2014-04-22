package htmleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;

import org.jhotdraw.draw.Figure;



public class HtmlParentAssigner  {
	
	
	//Assigns the parents within the base figure to parse using the children of each parent.
	public static void actionPerformed() {
		int size1 = Global.topParent.getDrawSpace().getFigures().size();
		Object[] objlist = new Object[size1];
		objlist = Global.topParent.getDrawSpace().getFigures().toArray();
		LinkedList<HtmlFigure> figurelist = new LinkedList<HtmlFigure>();
		
		//Tests to see if the object in the collection is an html figure and makes a list for it.
		for(int k = 0; k<size1; k++){
			if(objlist[k] instanceof HtmlFigure){
				figurelist.add((HtmlFigure) objlist[k]);
			}
		}
		
		//Clears child lists to reassign them.
		int size = figurelist.size();
		for(int i = 0; i<size; i++){
			figurelist.get(i).clearFigureList();
		}
		for(int i = 0; i<size; i++){
			HtmlFigure curFig = figurelist.get(i);

			//Current Figure loop.
			if(curFig.isTopParent == false){
				Rectangle2D.Double curRec = new Rectangle2D.Double();
				if(curFig instanceof ParagraphFigure){
					curRec = curFig.getBounds();
				}
				else{
					curRec = curFig.rectangle;
				}
				LinkedList<HtmlFigure> possibleParents = new LinkedList<HtmlFigure>();

				//Possible Parents are found, based on dimensions, and added to newly created link list for the current figure.
				for(int j = 0; j <size; j++){
					if(j != i && (!(figurelist.get(j) instanceof ParagraphFigure))){
						HtmlFigure curPossibleParent = figurelist.get(j);
						Rectangle2D.Double posRec = curPossibleParent.rectangle;
						if(posRec.x<=curRec.x && posRec.y<=curRec.y && posRec.x + posRec.width>=curRec.x + curRec.width && posRec.y + posRec.height>=curRec.y + curRec.height){
							possibleParents.add(curPossibleParent);
						}
					}
				}

				//Possible Parents list is searched to find the smallest of the possible parents and assign it as such.
				HtmlFigure _parent = possibleParents.get(0);
				if(possibleParents.size() > 1){
					for(int j = 1; j<possibleParents.size(); j++){
						Rectangle2D.Double curParRec = _parent.rectangle;
						HtmlFigure posPar = possibleParents.get(j);
						Rectangle2D.Double posParRec = posPar.rectangle;
						if(posParRec.x>=curParRec.x && posParRec.y>=curParRec.x && posParRec.x + posParRec.width <= curParRec.x + curParRec.width && posParRec.y + posParRec.height <= curParRec.y + curParRec.height){
							_parent = posPar;
						}
					}
				}
				curFig.setParent(_parent);
				curFig.getParent().addHtmlObject(curFig);
			}
			curFig.basicTransform(new AffineTransform(1, 0, 0, 1, 0, 0));
		}
		
		// Makes sure to assign children in left to right and then top to bottom order 
		HtmlFigure[] list = new HtmlFigure[figurelist.size()]; 
		figurelist.toArray(list);
		for(int i = 0;i<figurelist.size();i++){
			LinkedList<HtmlFigure> curIter = list[i].getObjectList();
			if(curIter.size()>1){
				list[i].clearFigureList();
				while(curIter.isEmpty()==false){
					int control = 0;
					HtmlFigure curFig = curIter.get(0);
					for(int j = 1; j<curIter.size();j++){
						HtmlFigure curComp = curIter.get(j);
						if((curComp.rectangle.x<=curFig.rectangle.x && curComp.rectangle.y<(curFig.rectangle.y+curFig.rectangle.height))){
							curFig = curComp;
							control = j;
						}
					}
					curIter.remove(control);
					list[i].addHtmlObject(curFig);
					//TODO test to see if it gets stuck in an unbounded loop
				}
			}
		}

	}	
	public static void actionRelease(){
		int size1 = Global.topParent.getDrawSpace().getFigures().size();
		Object[] objlist = new Object[size1];
		objlist = Global.topParent.getDrawSpace().getFigures().toArray();
		LinkedList<HtmlFigure> figurelist = new LinkedList<HtmlFigure>();
		
		//Tests to see if the object in the collection is an html figure and makes a list for it.
		for(int k = 0; k<size1; k++){
			if(objlist[k] instanceof HtmlFigure){
				figurelist.add((HtmlFigure) objlist[k]);
			}
		}
		
		//Clears child lists to reassign them.
		int size = figurelist.size();
		for(int i = 0; i<size; i++){
			figurelist.get(i).setParent(null);
		}
	}

}
