package htmleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;



public class HtmlParentAssigner implements ActionListener {
	
	
	//Assigns the parents within the base figure to parse using the children of each parent.
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i<Global.figureList.size(); i++){
			Global.figureList.get(i).clearFigureList();
		}
		for(int i = 0; i<Global.figureList.size(); i++){
			HtmlFigure curFig = Global.figureList.get(i);

			//Current Figure loop.
			if(curFig.isTopParent == false){
				Rectangle2D.Double curRec = curFig.rectangle;
				LinkedList<HtmlFigure> possibleParents = new LinkedList<HtmlFigure>();

				//Possible Parents are found, based on dimensions, and added to newly created link list for the current figure.
				for(int j = 0; j < Global.figureList.size(); j++){
					if(j != i){
						HtmlFigure curPossibleParent = Global.figureList.get(j);
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
			curFig.transform(new AffineTransform(1, 0, 0, 1, 0, 0));
		}
	}	

}
