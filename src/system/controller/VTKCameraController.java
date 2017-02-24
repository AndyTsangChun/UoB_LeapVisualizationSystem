package system.controller;

import system.general.SystemPreference;
import system.model.LVSObject;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

/** ************************************************************
 * This class is the controller which control the camera from 
 * VTK panel to provide function like rotation, shifting and
 * zooming.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class VTKCameraController extends LVSObject{
	public VTKController vtkController;
	
	/**
	 * Constructor
	 * 
	 * @param	systemController	controller of system    
	 * @param	systemPreference	User's Preference of the system
	 */
	public VTKCameraController(SystemController systemController, SystemPreference systemPreference, VTKController vtkController) {
		super(systemController, systemPreference);
		this.vtkController = vtkController;
	}

	/**
	 * Function called to rotate the object by call Azimuth() or Elevation() from VTK Camera
	 * Azimuth for Horizontal Axis
	 * Elevation for Vertical Axis
	 * 
	 */
	public void rotate(){
		if (!this.getSystemController().getVTKController().isSliceTrans()){
			Hand hand = this.getSystemController().getLeapMotionFrameController().getStyleHand();
			int[] handPos = this.getSystemController().getLeapMotionFrameController().getNormalizedHandPosition(hand);
			int width = super.getSystemPreference().getScreenWidth();
			int height = super.getSystemPreference().getScreenHeight();
			float handX = handPos[0];
			float handY = handPos[1];
			
			float rotate_X = 0, rotate_Y = 0;
			int w = super.getSystemPreference().getScreenWidth();
			int h = super.getSystemPreference().getScreenHeight();
			if (handX < width * 0.3 || handX > width * 0.7){
				rotate_X = (super.getSystemPreference().getRotateSpeed()*(((w/2) - handX) / (w/2)));
			}
			if (handY < height * 0.3 || handY > height * 0.7){
				rotate_Y = (-super.getSystemPreference().getRotateSpeed()*(((h/2) - handY) / (h/2)));
			}
			//System.out.println("X : " + rotate_X + ", Y : " + rotate_Y);
			vtkController.getVTKPanel().UpdateLight();
			vtkController.getVTKPanel().GetRenderer().GetActiveCamera().Azimuth(rotate_X);
			vtkController.getVTKPanel().GetRenderer().GetActiveCamera().Elevation(rotate_Y);
			// Line call camera to recalculate the vector, prevent flickering
			vtkController.getVTKPanel().GetRenderer().GetActiveCamera().OrthogonalizeViewUp();
			vtkController.getVTKPanel().GetRenderWindow().Render();
		}
	}
	
	/**
	 * Function called to zoom in or out the object by call Dolly() from VTK Camera
	 * 
	 */
	public void zoom(){
		Hand hand = this.getSystemController().getLeapMotionFrameController().getStyleHand();
		int dz = (int) (hand.palmVelocity().getZ()) / 50;
		
		float zoom_Z = 0;
		zoom_Z =  (float) (1 + (0.5 * dz / 10));
		vtkController.getVTKPanel().GetRenderer().GetActiveCamera().Zoom(zoom_Z);
		vtkController.getVTKPanel().GetRenderWindow().Render();
	}
	
	/**
	 * Function called to shift the image by call Yaw() or Pitch() from VTK Camera
	 * Yaw for Horizontal Axis
	 * Pitch for Vertical Axis
	 * 
	 * @param	Frame	the current frame using
	 */
	public void shift(Frame frame){
		
	}
	
	// Attributes Set and Get
	public VTKController getVTKController(){
		return this.vtkController;
	}
}
