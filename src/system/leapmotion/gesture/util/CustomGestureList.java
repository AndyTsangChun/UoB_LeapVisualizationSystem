package system.leapmotion.gesture.util;
import java.util.ArrayList;
import java.util.List;

import system.controller.GestureController;
import system.model.CustomGesture;

/** ************************************************************
 * This class is a list which storing custom gesture.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class CustomGestureList{
	private GestureController gestureController;
	private List<CustomGesture> customGestureList;
	
	public CustomGestureList(GestureController gestureController){
		this.gestureController = gestureController;
		customGestureList = new ArrayList<CustomGesture>();
	}
	
	public void addGesture(CustomGesture customGesture){
		customGestureList.add(customGesture);
	}
	
	public void removeGesture(CustomGestureType gestureType){
		for (int i = 0 ; i < customGestureList.size() ; i++){
			if(customGestureList.get(i).getCustomGestureType() == gestureType){
				customGestureList.remove(i);
				break;
			}
		}
	}
	
	// Attributes Set and Get
	public List<CustomGesture> getCustomGestureList(){
		return this.customGestureList;
	}
	
	public CustomGesture getCustomGesture(CustomGestureType gestureType){
		for (int i = 0 ; i < customGestureList.size() ; i++){
			if(customGestureList.get(i).getCustomGestureType() == gestureType){
				return customGestureList.get(i);
			}
		}
		return null;
	}
}
