package htmleditor;

import htmleditor.figures.HtmlFigure;
import htmleditor.figures.ImgFigure;
import htmleditor.figures.ParagraphFigure;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.jhotdraw.app.Project;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.gui.JSheet;


public class HtmlParentAssigner{
	
	DefaultHtmlDrawing dView;
	boolean control = false;
	
	public HtmlParentAssigner(DrawingView view){
		if(view != null){
			dView = ((DefaultHtmlDrawingView) view).getDrawing();
		}
	}
	
	//Clears the parents of each object for free movement of objects.
	public void actionRelease(){
		
		//First assigns the top parent with the list of figures, necessary for when loading saved files.
		if(control == false){
			int size2 = dView.getFigures().size();
			Object[] objlist2 = new Object[size2];
			objlist2 = dView.getFigures().toArray();
			for(int k = 0; k<size2; k++){
				if(objlist2[k] instanceof HtmlFigure){
					HtmlFigure temp = (HtmlFigure) objlist2[k];
					if(temp.isTopParent == true){
						dView.setTopParent((TopParentHtmlFigure) temp);
					}
				}
			}
			control = true;
		}
		int size1 = dView.getTopParent().getDrawSpace().getFigures().size();
		Object[] objlist = new Object[size1];
		objlist = dView.getTopParent().getDrawSpace().getFigures().toArray();
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

	//Assigns the parents within the base figure to parse using the children of each parent.
	public void actionPerformed() {	
		//First assigns the top parent with the list of figures, necessary for when loading saved files.
		if(control == false){
			int size2 = dView.getFigures().size();
			Object[] objlist2 = new Object[size2];
			objlist2 = dView.getFigures().toArray();
			for(int k = 0; k<size2; k++){
				if(objlist2[k] instanceof HtmlFigure){
					HtmlFigure temp = (HtmlFigure) objlist2[k];
					if(temp.isTopParent == true){
						dView.setTopParent((TopParentHtmlFigure) temp);
					}
				}
			}
			control = true;
		}
		boolean errorControl = false;
		int size1 = dView.getTopParent().getDrawSpace().getFigures().size();
		Object[] objlist = new Object[size1];
		objlist = dView.getTopParent().getDrawSpace().getFigures().toArray();
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
					HtmlFigure curPossibleParent = figurelist.get(j);
					
					Rectangle2D.Double posRec = curPossibleParent.rectangle;
					if(figurelist.get(j) instanceof ParagraphFigure){
						posRec = figurelist.get(j).getBounds();
					}
					if(j != i){
						if(posRec.x<=curRec.x && posRec.y<=curRec.y && posRec.x + posRec.width>=curRec.x + curRec.width && posRec.y + posRec.height>=curRec.y + curRec.height){
							possibleParents.add(curPossibleParent);
						}
					}
				}

				//Possible Parents list is searched to find the smallest of the possible parents and assign it as such.
				if(possibleParents.size() != 0){
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
				else{
					DefaultHtmlDrawing htmldrawing = (DefaultHtmlDrawing) dView;
					final Project project = htmldrawing.getProject();
					JSheet.showMessageSheet(project.getComponent(),"Objects cannot be placed outside of the Base Area.",JOptionPane.ERROR_MESSAGE);
					errorControl = true;
				}
			}
			curFig.basicTransform(new AffineTransform(1, 0, 0, 1, 0, 0));
		}
		
		// Makes sure to assign children in left to right and then top to bottom order 
		HtmlFigure[] list = new HtmlFigure[figurelist.size()]; 
		figurelist.toArray(list);
		for(int i = 0;i<figurelist.size();i++){
			HtmlFigure curParent =  list[i];
			LinkedList<HtmlFigure> curIter = curParent.getObjectList();
			if(curIter.size()>1){
				curParent.clearFigureList();
				while(curIter.isEmpty()==false){
					int control = 0;
					HtmlFigure curFig = curIter.get(0);
					for(int j = 1; j<curIter.size();j++){
						HtmlFigure curComp = curIter.get(j);
						if(curComp.getLayer()<curFig.getLayer()){
							curFig = curComp;
							control = j;
						}
					}
					curIter.remove(control);
					curParent.addHtmlObject(curFig);
				}
			}
		}
		for(int i = 0;i<figurelist.size();i++){
			figurelist.get(i).basicTransform(new AffineTransform(1, 0, 0, 1, 0, 0));
		}
	}

}
