package ui.awt.res;

import java.awt.Image;
import java.awt.Toolkit;

/** ************************************************************
 * This the texture manager holding all Swing component texture
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class SwingTextureManager {
	public static final String LVS_LOGO_PATH = "rsc/LVS_logo.png";
	public static final String COLORMAP_ICON_PATH = "rsc/Icon/colormap_icon.png";
	public static final String GESTURE_ICON_PATH = "rsc/Icon/gesture_icon.png";
	public static final String LEFT_ICON_PATH = "rsc/Icon/left_icon.png";
	public static final String NEW_FILE_ICON_PATH = "rsc/Icon/new_icon.png";
	public static final String OFF_ICON_PATH = "rsc/Icon/off_icon.png";
	public static final String ON_ICON_PATH = "rsc/Icon/on_icon.png";
	public static final String OPEN_ICON_PATH = "rsc/Icon/open_icon.png";
	public static final String OPACITY_ICON_PATH = "rsc/Icon/opacity_icon.png";
	public static final String PRESENTATION_ICON_PATH = "rsc/Icon/presentation_icon.png";
	public static final String REPRESENTATION_ICON_PATH = "rsc/Icon/representation_icon.png";
	public static final String RIGHT_ICON_PATH = "rsc/Icon/right_icon.png";
	public static final String SAVE_AS_ICON_PATH = "rsc/Icon/save_as_icon.png";
	public static final String SAVE_ICON_PATH = "rsc/Icon/save_icon.png";
	public static final String TRACKING_ICON_PATH = "rsc/Icon/tracking_icon.png";
	public static final String IMPORT_ICON_PATH = "rsc/Icon/import_icon.png";
	public static final String BUTTON_BG_1_PATH = "rsc/BG/Button_BG1.png";
	public static final String BUTTON_BG_2_PATH = "rsc/BG/Button_BG2.png";
	public static final String GREY_CIRCLE_PATH = "rsc/BG/grey_circle.png";
	public static final String DEFAULT_SLICE_TRANSLATE_ICON_PATH = "rsc/Button/default_slice_translate_icon.png";
	public static final String ENABLE_SLICE_TRANSLATE_ICON_PATH = "rsc/Button/enable_slice_translate_icon.png";
	// Tutorial
	public static final String TUT_ITEM_PATH = "rsc/BUTTON/tut_item.png";
	public static final String TUT_ITEM2_PATH = "rsc/BUTTON/tut_item2.png";
	public static final String TUT_M_1_PATH = "rsc/Tutorial/Tut_Menu_1.png";
	public static final String TUT_M_2_PATH = "rsc/Tutorial/Tut_Menu_2.png";
	public static final String TUT_M_3_PATH = "rsc/Tutorial/Tut_Menu_3.png";
	public static final String TUT_M_4_PATH = "rsc/Tutorial/Tut_Menu_4.png";
	public static final String TUT_M_5_PATH = "rsc/Tutorial/Tut_Menu_5.png";
	public static final String TUT_M_6_PATH = "rsc/Tutorial/Tut_Menu_6.png";
	public static final String TUT_GESTURE_PATH = "rsc/Tutorial/Tut_Gesture.png";
	public static final String TUT_MOVEMENT_PATH = "rsc/Tutorial/Tut_Movement.png";
	public static final String TUT_SWIPE_PATH = "rsc/Tutorial/Tut_Swipe.png";
	public static final String TUT_CLICK_PATH = "rsc/Tutorial/Tut_Click.png";
	public static final String TUT_ZOOM_PATH = "rsc/Tutorial/Tut_Zoom.png";
	
	public static Image LVS_LOGO_IMAGE = null;
	public static Image COLORMAP_ICON_IMAGE = null;
	public static Image GESTURE_ICON_IMAGE = null;
	public static Image LEFT_ICON_IMAGE = null;
	public static Image LEFT_ICON_20_IMAGE = null;
	public static Image NEW_FILE_ICON_IMAGE = null;
	public static Image OFF_ICON_IMAGE = null;
	public static Image ON_ICON_IMAGE = null;
	public static Image OPEN_ICON_IMAGE = null;
	public static Image OPACITY_ICON_IMAGE = null;
	public static Image PRESENTATION_ICON_IMAGE = null;
	public static Image REPRESENTATION_ICON_IMAGE = null;
	public static Image RIGHT_ICON_IMAGE = null;
	public static Image RIGHT_ICON_20_IMAGE = null;
	public static Image SAVE_AS_ICON_IMAGE = null;
	public static Image SAVE_ICON_IMAGE = null;
	public static Image TRACKING_ICON_IMAGE = null;
	public static Image IMPORT_ICON_IMAGE = null;
	public static Image BUTTON_BG_1_IMAGE = null;
	public static Image BUTTON_BG_2_IMAGE = null;
	public static Image GREY_CIRCLE_IMAGE = null;
	public static Image DEFAULT_SLICE_TRANSLATE_ICON_IMAGE = null;
	public static Image ENABLE_SLICE_TRANSLATE_ICON_IMAGE = null;
	// Tutorial
	public static Image TUT_ITEM_IMAGE = null;
	public static Image TUT_ITEM2_IMAGE = null;
	public static Image TUT_M_1_IMAGE = null;
	public static Image TUT_M_2_IMAGE = null;
	public static Image TUT_M_3_IMAGE = null;
	public static Image TUT_M_4_IMAGE = null;
	public static Image TUT_M_5_IMAGE = null;
	public static Image TUT_M_6_IMAGE = null;
	public static Image TUT_GESTURE_IMAGE = null;
	public static Image TUT_MOVEMENT_IMAGE = null;
	public static Image TUT_SWIPE_IMAGE = null;
	public static Image TUT_CLICK_IMAGE = null;
	public static Image TUT_ZOOM_IMAGE = null;
	
	/**
	 * Load a swing component texture and resize it
	 */
	public static void loadTexture(){		
		LVS_LOGO_IMAGE = Toolkit.getDefaultToolkit().getImage(LVS_LOGO_PATH);
		COLORMAP_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(COLORMAP_ICON_PATH);
		COLORMAP_ICON_IMAGE = COLORMAP_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_ICON_W, LVSSwingInfo.TOOLBAR_ICON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		GESTURE_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(GESTURE_ICON_PATH);
		GESTURE_ICON_IMAGE = GESTURE_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		LEFT_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(LEFT_ICON_PATH);
		LEFT_ICON_IMAGE = LEFT_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		LEFT_ICON_20_IMAGE = Toolkit.getDefaultToolkit().getImage(LEFT_ICON_PATH);
		LEFT_ICON_20_IMAGE = LEFT_ICON_20_IMAGE.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
		NEW_FILE_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(NEW_FILE_ICON_PATH);
		NEW_FILE_ICON_IMAGE = NEW_FILE_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		OFF_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(OFF_ICON_PATH);
		OFF_ICON_IMAGE = OFF_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_ICON_W, LVSSwingInfo.TOOLBAR_ICON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		ON_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(ON_ICON_PATH);
		ON_ICON_IMAGE = ON_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_ICON_W, LVSSwingInfo.TOOLBAR_ICON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		OPEN_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(OPEN_ICON_PATH);
		OPEN_ICON_IMAGE = OPEN_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		OPACITY_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(OPACITY_ICON_PATH);
		OPACITY_ICON_IMAGE = OPACITY_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_ICON_W, LVSSwingInfo.TOOLBAR_ICON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		PRESENTATION_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(PRESENTATION_ICON_PATH);
		PRESENTATION_ICON_IMAGE = PRESENTATION_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		REPRESENTATION_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(REPRESENTATION_ICON_PATH);
		REPRESENTATION_ICON_IMAGE = REPRESENTATION_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_ICON_W, LVSSwingInfo.TOOLBAR_ICON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		RIGHT_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(RIGHT_ICON_PATH);
		RIGHT_ICON_IMAGE = RIGHT_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		RIGHT_ICON_20_IMAGE = Toolkit.getDefaultToolkit().getImage(RIGHT_ICON_PATH);
		RIGHT_ICON_20_IMAGE = RIGHT_ICON_20_IMAGE.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
		SAVE_AS_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(SAVE_AS_ICON_PATH);
		SAVE_AS_ICON_IMAGE = SAVE_AS_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		SAVE_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(SAVE_ICON_PATH);
		SAVE_ICON_IMAGE = SAVE_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		TRACKING_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(TRACKING_ICON_PATH);
		TRACKING_ICON_IMAGE = TRACKING_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		IMPORT_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(IMPORT_ICON_PATH);
		IMPORT_ICON_IMAGE = IMPORT_ICON_IMAGE.getScaledInstance( LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		BUTTON_BG_1_IMAGE = Toolkit.getDefaultToolkit().getImage(BUTTON_BG_1_PATH);
		BUTTON_BG_2_IMAGE = Toolkit.getDefaultToolkit().getImage(BUTTON_BG_2_PATH);
		GREY_CIRCLE_IMAGE = Toolkit.getDefaultToolkit().getImage(GREY_CIRCLE_PATH);
		DEFAULT_SLICE_TRANSLATE_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(DEFAULT_SLICE_TRANSLATE_ICON_PATH);
		DEFAULT_SLICE_TRANSLATE_ICON_IMAGE = DEFAULT_SLICE_TRANSLATE_ICON_IMAGE.getScaledInstance( LVSSwingInfo.OVERLAYER_BUTTON_SIZE, LVSSwingInfo.OVERLAYER_BUTTON_SIZE,  java.awt.Image.SCALE_SMOOTH ) ;
		ENABLE_SLICE_TRANSLATE_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage(ENABLE_SLICE_TRANSLATE_ICON_PATH);
		ENABLE_SLICE_TRANSLATE_ICON_IMAGE = ENABLE_SLICE_TRANSLATE_ICON_IMAGE.getScaledInstance( LVSSwingInfo.OVERLAYER_BUTTON_SIZE, LVSSwingInfo.OVERLAYER_BUTTON_SIZE,  java.awt.Image.SCALE_SMOOTH ) ;
		
		// Tutorial
		TUT_ITEM_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_ITEM_PATH);
		TUT_ITEM_IMAGE = TUT_ITEM_IMAGE.getScaledInstance( LVSSwingInfo.TUTORIAL_ITEM_SIZE, LVSSwingInfo.TUTORIAL_ITEM_SIZE,  java.awt.Image.SCALE_SMOOTH );
		TUT_ITEM2_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_ITEM2_PATH);
		TUT_ITEM2_IMAGE = TUT_ITEM2_IMAGE.getScaledInstance( LVSSwingInfo.TUTORIAL_ITEM_SIZE, LVSSwingInfo.TUTORIAL_ITEM_SIZE,  java.awt.Image.SCALE_SMOOTH );
		TUT_M_1_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_M_1_PATH);
		TUT_M_2_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_M_2_PATH);
		TUT_M_3_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_M_3_PATH);
		TUT_M_4_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_M_4_PATH);
		TUT_M_5_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_M_5_PATH);
		TUT_M_6_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_M_6_PATH);
		TUT_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_GESTURE_PATH);
		TUT_MOVEMENT_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_MOVEMENT_PATH);
		TUT_SWIPE_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_SWIPE_PATH);
		TUT_CLICK_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_CLICK_PATH);
		TUT_ZOOM_IMAGE = Toolkit.getDefaultToolkit().getImage(TUT_ZOOM_PATH);
	}
}
