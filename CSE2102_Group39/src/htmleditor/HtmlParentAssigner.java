package htmleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class HtmlParentAssigner implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i<Global.figureList.size(); i++){
			HtmlFigure current = Global.figureList.pop();
			current.clearFigureList();
			Global.figureList.add(current);
		}
		for(int i = 0; i<Global.figureList.size(); i++){
			HtmlFigure curFig = Global.figureList.pop();
			//Current Figure loop.
			if(curFig.isTopParent==false){
				Rectangle2D.Double curRec = curFig.rectangle;
				LinkedList<HtmlFigure> possibleParents = new LinkedList<HtmlFigure>();
				//Possible Parents are added to newly created linklist for the current figure.
				for(int j = 0; j<Global.figureList.size(); j++){
					HtmlFigure curPossibleParent = Global.figureList.pop();
					Rectangle2D.Double posRec = curPossibleParent.rectangle;
					if(posRec.x<=curRec.x && posRec.y<=curRec.y && posRec.x + posRec.width>=curRec.x + curRec.width && posRec.y + posRec.height>=curRec.y + curRec.height){
						possibleParents.add(curPossibleParent);
					}
					Global.figureList.add(curPossibleParent);
				}
				//Possible Parents are looped to see the smallest of the parent and assign it as such.
				HtmlFigure _parent = possibleParents.pop();
				if(possibleParents.isEmpty()==false){
					for(int j = 0; j<possibleParents.size(); j++){
						Rectangle2D.Double curParRec = _parent.rectangle;
						HtmlFigure posPar = possibleParents.pop();
						Rectangle2D.Double posParRec = posPar.rectangle;
						if(posParRec.x>=curParRec.x && posParRec.y>=curParRec.x && posParRec.x + posParRec.width <= curParRec.x + curParRec.width && posParRec.y + posParRec.height <= curParRec.y + curParRec.height){
							_parent = posPar;
						}
					}
				}
				curFig.setParent(_parent);
				curFig.getParent().addHtmlObject(curFig);
			}
			Global.figureList.add(curFig);
		}
	}

}
