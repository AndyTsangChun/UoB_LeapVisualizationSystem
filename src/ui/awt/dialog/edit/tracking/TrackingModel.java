package ui.awt.dialog.edit.tracking;

import system.general.SystemPreference;

/** ************************************************************
 * Tracking Model of Hand
 * 
 * @author atc1992andy
 * @version 1.2
 *
 * ***********************************************************/
public class TrackingModel {
	private SystemPreference systemPreference;
	private boolean isShowLeftThumb;
	private boolean isShowLeftIndex;
	private boolean isShowLeftMiddle;
	private boolean isShowLeftRing;
	private boolean isShowLeftPinky;
	private boolean isShowRightThumb;
	private boolean isShowRightIndex;
	private boolean isShowRightMiddle;
	private boolean isShowRightRing;
	private boolean isShowRightPinky;
	private boolean isShowLeftPalm;
	private boolean isShowRightPalm;
	
	public TrackingModel(SystemPreference systemPreference) {
		this.systemPreference = systemPreference;
		// init model base of system preference
		isShowLeftThumb = systemPreference.isShowLeftThumb();
		isShowLeftIndex = systemPreference.isShowLeftIndex();
		isShowLeftMiddle = systemPreference.isShowLeftMiddle();
		isShowLeftRing = systemPreference.isShowLeftRing();
		isShowLeftPinky = systemPreference.isShowLeftPinky();
		isShowRightThumb = systemPreference.isShowRightThumb();
		isShowRightIndex = systemPreference.isShowRightIndex();
		isShowRightMiddle = systemPreference.isShowRightMiddle();
		isShowRightRing = systemPreference.isShowRightRing();
		isShowRightPinky = systemPreference.isShowRightPinky();
		isShowLeftPalm = systemPreference.isShowLeftPalm();
		isShowRightPalm = systemPreference.isShowRightPalm();
	}
	
	public void all(){
		setShowLeftThumb(true);
		setShowLeftIndex(true);
		setShowLeftMiddle(true);
		setShowLeftRing(true);
		setShowLeftPinky(true);
		setShowRightThumb(true);
		setShowRightIndex(true);
		setShowRightMiddle(true);
		setShowRightRing(true);
		setShowRightPinky(true);
		setShowLeftPalm(true);
		setShowRightPalm(true);
	}
	
	public void allFingers(){
		setShowLeftThumb(true);
		setShowLeftIndex(true);
		setShowLeftMiddle(true);
		setShowLeftRing(true);
		setShowLeftPinky(true);
		setShowRightThumb(true);
		setShowRightIndex(true);
		setShowRightMiddle(true);
		setShowRightRing(true);
		setShowRightPinky(true);
	}
	
	public void leftFingers(){
		setShowLeftThumb(true);
		setShowLeftIndex(true);
		setShowLeftMiddle(true);
		setShowLeftRing(true);
		setShowLeftPinky(true);
	}
	
	public void rightFingers(){
		setShowRightThumb(true);
		setShowRightIndex(true);
		setShowRightMiddle(true);
		setShowRightRing(true);
		setShowRightPinky(true);
	}
	
	public void palms(){
		setShowLeftPalm(true);
		setShowRightPalm(true);
	}
	
	public void none(){
		setShowLeftThumb(false);
		setShowLeftIndex(false);
		setShowLeftMiddle(false);
		setShowLeftRing(false);
		setShowLeftPinky(false);
		setShowRightThumb(false);
		setShowRightIndex(false);
		setShowRightMiddle(false);
		setShowRightRing(false);
		setShowRightPinky(false);
		setShowLeftPalm(false);
		setShowRightPalm(false);
	}
	
	// Attributes Set and Get
	public boolean isShowLeftThumb() {
		return isShowLeftThumb;
	}

	public void setShowLeftThumb(boolean isShowLeftThumb) {
		this.isShowLeftThumb = isShowLeftThumb;
		systemPreference.setShowLeftThumb(isShowLeftThumb);
	}

	public boolean isShowLeftIndex() {
		return isShowLeftIndex;
	}

	public void setShowLeftIndex(boolean isShowLeftIndex) {
		this.isShowLeftIndex = isShowLeftIndex;
		systemPreference.setShowLeftIndex(isShowLeftIndex);
	}

	public boolean isShowLeftMiddle() {
		return isShowLeftMiddle;
	}

	public void setShowLeftMiddle(boolean isShowLeftMiddle) {
		this.isShowLeftMiddle = isShowLeftMiddle;
		systemPreference.setShowLeftMiddle(isShowLeftMiddle);
	}

	public boolean isShowLeftRing() {
		return isShowLeftRing;
	}

	public void setShowLeftRing(boolean isShowLeftRing) {
		this.isShowLeftRing = isShowLeftRing;
		systemPreference.setShowLeftRing(isShowLeftRing);
	}

	public boolean isShowLeftPinky() {
		return isShowLeftPinky;
	}

	public void setShowLeftPinky(boolean isShowLeftPinky) {
		this.isShowLeftPinky = isShowLeftPinky;
		systemPreference.setShowLeftPinky(isShowLeftPinky);
	}

	public boolean isShowRightThumb() {
		return isShowRightThumb;
	}

	public void setShowRightThumb(boolean isShowRightThumb) {
		this.isShowRightThumb = isShowRightThumb;
		systemPreference.setShowRightThumb(isShowRightThumb);
	}

	public boolean isShowRightIndex() {
		return isShowRightIndex;
	}

	public void setShowRightIndex(boolean isShowRightIndex) {
		this.isShowRightIndex = isShowRightIndex;
		systemPreference.setShowRightIndex(isShowRightIndex);
	}

	public boolean isShowRightMiddle() {
		return isShowRightMiddle;
	}

	public void setShowRightMiddle(boolean isShowRightMiddle) {
		this.isShowRightMiddle = isShowRightMiddle;
		systemPreference.setShowRightMiddle(isShowRightMiddle);
	}

	public boolean isShowRightRing() {
		return isShowRightRing;
	}

	public void setShowRightRing(boolean isShowRightRing) {
		this.isShowRightRing = isShowRightRing;
		systemPreference.setShowRightRing(isShowRightRing);
	}

	public boolean isShowRightPinky() {
		return isShowRightPinky;
	}

	public void setShowRightPinky(boolean isShowRightPinky) {
		this.isShowRightPinky = isShowRightPinky;
		systemPreference.setShowRightPinky(isShowRightPinky);
	}

	public boolean isShowLeftPalm() {
		return isShowLeftPalm;
	}

	public void setShowLeftPalm(boolean isShowLeftPalm) {
		this.isShowLeftPalm = isShowLeftPalm;
		systemPreference.setShowLeftPalm(isShowLeftPalm);
	}

	public boolean isShowRightPalm() {
		return isShowRightPalm;
	}

	public void setShowRightPalm(boolean isShowRightPalm) {
		this.isShowRightPalm = isShowRightPalm;
		systemPreference.setShowRightPalm(isShowRightPalm);
	}

}
