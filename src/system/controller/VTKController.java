package system.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import system.file.FileItem;
import system.general.SystemPreference;
import system.model.LVSColorMap;
import system.model.LVSObject;
import system.model.VTKWidget;
import system.vtk.ScalarBarWidget;
import ui.awt.res.LVSConfigInfo;
import ui.awt.res.LVSStringInfo;
import vtk.vtkActor;
import vtk.vtkAlgorithm;
import vtk.vtkAxesActor;
import vtk.vtkCamera;
import vtk.vtkColorTransferFunction;
import vtk.vtkContourFilter;
import vtk.vtkCutter;
import vtk.vtkDataReader;
import vtk.vtkDataSet;
import vtk.vtkDataSetMapper;
import vtk.vtkLookupTable;
import vtk.vtkMath;
import vtk.vtkMatrix4x4;
import vtk.vtkNativeLibrary;
import vtk.vtkOrientationMarkerWidget;
import vtk.vtkPanel;
import vtk.vtkPlane;
import vtk.vtkPlaneSource;
import vtk.vtkPolyData;
import vtk.vtkPolyDataMapper;
import vtk.vtkPolyDataReader;
import vtk.vtkRectilinearGridReader;
import vtk.vtkRenderWindowInteractor;
import vtk.vtkSTLReader;
import vtk.vtkScalarBarActor;
import vtk.vtkScalarBarWidget;
import vtk.vtkScalarsToColors;
import vtk.vtkStructuredGridReader;
import vtk.vtkStructuredPointsReader;
import vtk.vtkThreshold;
import vtk.vtkTransform;
import vtk.vtkUnstructuredGridReader;

import com.leapmotion.leap.Hand;

/** ************************************************************
 * This class is the controller which control the rendering of
 * VTK. Including reader, mapper, filter. The current VTK pipeline
 * does not allow recursive input which means multiple actor from 
 * different file cannot be displayed at once. However, multiple
 * filter options within the same file can be displayed at the 
 * same time.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class VTKController extends LVSObject{
	public static final double DEFAULT_OPACITY = 1.0;
	public final static int CONTOUR_DEFAULT_STEP = 5;
	public final static double[] SLICE_DEFAULT_NORMAL = {1,0,0};
	private WidgetController widgetController;
	private vtkPanel panel;
	private vtkRenderWindowInteractor interactor;
	private List<vtkActor> actorList;
	private vtkActor emptyActor;
	private vtkActor sliceTransActor;
	private List<VTKWidget> widgetList;
	private boolean isSliceTrans;
	private vtkMatrix4x4 m4;
	private double XTrans = 0;
	private double YTrans = 0;
	private double ZTrans = 0;
	
	/**
	 * Constructor
	 * 
	 * @param	systemController	controller of system    
	 * @param	systemPreference	User's Preference of the system
	 */
	public VTKController(SystemController systemController, SystemPreference systemPreference) {
		super(systemController, systemPreference);
		loadVTKLibrary();
		actorList = new ArrayList<vtkActor>();
        interactor = new vtkRenderWindowInteractor();
		widgetController = new WidgetController(super.getSystemController(), super.getSystemPreference(), this);
        panel = new vtkPanel();
		emptyActor = new vtkActor();
		widgetList = new ArrayList<VTKWidget>();
	}
	
	/**
	 * Function called to set up the rendering panel
	 * 
	 */
	public void setupPanel(){
		// setting up panel
        panel.GetRenderer().SetBackground(0.6, 0.6, 0.6);
        panel.setPreferredSize(getSystemController().getSwingController().getVTKContainerSize());
        panel.setSize(getSystemController().getSwingController().getVTKContainerSize());
        interactor.SetRenderWindow(panel.GetRenderWindow());
	}
	
	/**
	 * Function call to extract information such as scalar range
	 * @param path Path of file want to extract
	 */
	public void extractInfo(FileItem item){
		File file = new File(item.getPath());
		if (getDataSet(file) != null){
			// Get scalar range
			double[] scalarRange = getDataSet(file).GetScalarRange();
			item.setDefaultRange(scalarRange);
			// Get data range
			vtkDataSetMapper mapper = new vtkDataSetMapper();
			mapper.SetInputData(getDataSet(file));
			mapper.Update();
			vtkActor actor = new vtkActor();
			actor.SetMapper(mapper);
			double rangeX = (actor.GetXRange()[1] - actor.GetXRange()[0]) / 2;
			double rangeY = (actor.GetYRange()[1] - actor.GetYRange()[0]) / 2;
			double rangeZ = (actor.GetYRange()[1] - actor.GetYRange()[0]) / 2;
			double deviation = 0;
			if (rangeX > rangeY) {
				if (rangeX > rangeZ) {
					deviation = rangeX;
				} else {
					deviation = rangeZ;
				}
			} else if (rangeY > rangeZ) {
				deviation = rangeY;
			} else {
				deviation = rangeZ;
			}
			item.setDataDeviation(deviation);
		} else{
			double[] temp = {0,1};
			item.setDefaultRange(temp);
			item.setDataDeviation(100);
		}
	}
	
	/**
	 * Function called to launch VTK side functions 
	 * addListToPanel must be called such that at least one actor is added to panel
	 * or else the panel will not be created and not shown
	 * 
	 */
	public vtkPanel runVTK(){
		setupPanel();		
		addListToPanel();
		initView();
		addOrientationWidget();
		//Testing();
		//widgetController.updateWidget();
		
		return panel;
	}
	
	/**
	 * Sample data for testing use
	 */
	public void Testing(){
		File file = new File("ge.vtk");

		vtkDataSet dataSet = getDataSet(file);
		
		vtkLookupTable table = new vtkLookupTable();
		table.Build();
		vtkDataSetMapper m = new vtkDataSetMapper();
		m.SetInputData(dataSet);
		m.SetLookupTable(table);
		m.SetScalarRange(dataSet.GetScalarRange());
		m.Update();
		vtkActor a = new vtkActor();
		a.SetMapper(m);
		this.translateActor(a);
		panel.GetRenderer().AddActor(a);
		
		vtkScalarBarActor scalarBar = new vtkScalarBarActor();
		scalarBar.SetOrientationToHorizontal();
		scalarBar.SetLookupTable(table);
		scalarBar.SetWidth(50);
		//
		vtkScalarBarWidget scalarWidget = new vtkScalarBarWidget();
		scalarWidget.SetInteractor(interactor);
		scalarWidget.SetScalarBarActor(scalarBar);
		scalarWidget.On();
	}
	
	/**
	 * Function called to update the VTKPanel when actor is added or removed
	 * int showingActor is used to determine showing all actor in the panel
	 * or each of them on separated panel. When other function called and 
	 * change the value of showingActor, this function should be called to 
	 * update the VTK panel.
	 * 
	 * Show all actor is currently disabled
	 * 
	 * -1 = show all
	 * 0 >= id of actor being shown
	 */
	public void updateVTKPanel(){
		System.out.println("VTK Updating");
		if (this.getSystemController().getCurrentFile() == null || this.getSystemController().getCurrentFile().getFileList().size() <= 0){
			actorList = new ArrayList<vtkActor>();
			this.addListToPanel();
			return;
		}
		int showingActor = this.getSystemController().getCurrentFile().getShowingActor();
		List<FileItem> fileList = this.getSystemController().getCurrentFile().getFileList();
		widgetList.clear();
		if (fileList.size() > 0){
			if (showingActor == -1){
				for (int i = 0 ; i < fileList.size() ; i++){
					File file = new File(fileList.get(i).getPath());
					if (file.exists()){
						fileToActor(fileList.get(i));
					}else{
						int result = this.getSystemController().getMessageFactory().showMessageDialog(3, LVSStringInfo.FILE_NOT_FOUND, LVSConfigInfo.FILE_NOT_FOUND_OPTIONS);
						switch(result){
						case 0:
							fileList.remove(i);
							i--;
							break;
						case 1:
							String[] fileInfo = this.getSystemController().getObjectHandler().chooseReadPath(LVSConfigInfo.OBJ_FILTER_DESC, LVSConfigInfo.OBJ_FILTER_OPTION);
							if (fileInfo[0] != null && fileInfo[1] !=null){
								if (!fileInfo[0].equals("") && !fileInfo[1].equals("")){
									fileList.get(i).setName(fileInfo[0]);
									fileList.get(i).setPath(fileInfo[1]);
									file = new File(fileList.get(i).getPath());
									extractInfo(fileList.get(i));
									fileToActor(fileList.get(i));
								}
							}
							break;
						case 2:
							break;
						default:
						}
					}
				}
			}else{
				actorList = new ArrayList<vtkActor>();
				FileItem item = super.getSystemController().getCurrentFile().getItemById(showingActor);
				this.implementFunctions(item);
				fileToActor(item);
			}
			this.addListToPanel();
			this.updateWidget();

			panel.GetRenderer().GetActiveCamera().OrthogonalizeViewUp();
		}
	}
	
	/**
	 * Convert a file into a vtkActor following the VTK pipeline
	 */
	public void fileToActor(FileItem item){
		File file = new File(item.getPath());
		vtkDataSet dataSet = getDataSet(file);
		applyAlgorithm(dataSet, item);
	}
	
	/**
	 * Function call to setup and show widget
	 * This function has to be called after the panel set or else widget will not be shown
	 */
	public void updateWidget(){
		for (int i = 0 ; i < widgetList.size() ; i++){
			widgetList.get(i).initWidget();
		}
	}
	
	/**
	 * Function called check the data type in order to use the correct reader and returning dataSet
	 * 
	 * @param	File	the file open by user
	 * @return dataSet DataSet obtain from the file
	 */
	public vtkDataSet getDataSet(File file){
		String ext = getExtension(file);
		vtkDataSet dataSet = null;
		if (ext.equals("vtk")){
			vtkDataReader reader = new vtkDataReader();
			reader.SetFileName(file.getAbsolutePath());
			reader.Update();
			if (reader.IsFileUnstructuredGrid() == 1){
				reader = new vtkUnstructuredGridReader();
				reader.SetFileName(file.getAbsolutePath());
				reader.Update();
				dataSet = ((vtkUnstructuredGridReader)reader).GetOutput();
			}else if (reader.IsFileRectilinearGrid() == 1){
				reader = new vtkRectilinearGridReader();
				reader.SetFileName(file.getAbsolutePath());
				reader.Update();
				dataSet = ((vtkRectilinearGridReader)reader).GetOutput();
			}else if (reader.IsFileStructuredGrid() == 1){
				reader = new vtkStructuredGridReader();
				reader.SetFileName(file.getAbsolutePath());
				reader.Update();
				dataSet = ((vtkStructuredGridReader)reader).GetOutput();
			}else if (reader.IsFileStructuredPoints() == 1){
				reader = new vtkStructuredPointsReader();
				reader.SetFileName(file.getAbsolutePath());
				reader.Update();
				dataSet = ((vtkStructuredPointsReader)reader).GetOutput();
			}else if (reader.IsFilePolyData() == 1){
				reader = new vtkPolyDataReader();
				reader.SetFileName(file.getAbsolutePath());
				reader.Update();
				dataSet = ((vtkPolyDataReader)reader).GetOutput();
			}
		}else if (ext.equals("stl")){
			vtkSTLReader reader = new vtkSTLReader();
			reader.SetFileName(file.getAbsolutePath());
			reader.Update();
			dataSet = reader.GetOutput();
		}
		return dataSet;
	}
	
	/**
	 * Function called to apply different VTK algorithm 
	 * This function can be modify for recursion in the future
	 * The filter visibility is referring to the current LVSFile
	 * 
	 * @param	dataSet	DataSet containing the data
	 * @param	item FileItem object containing all the information and setting of that imported file
	 * @return newDataSet DataSet object containing the data, this data set could be modified by filter or not.
	 */
	public void applyAlgorithm(vtkDataSet dataSet, FileItem item){
		boolean changed = false;
		double opacity = item.getOpacity();
		int representation = item.getRepresentation();
		vtkScalarsToColors colorScalar = null;
		if (super.getSystemPreference().getCurrentColorMap() != -1){
			colorScalar = setColorMapping(dataSet);
		}else{
			colorScalar = new vtkLookupTable();
			colorScalar.Build();
		}
		if (item != null){
			if (item.getContour().isVisiable()){
				changed = true;
				vtkDataSet newDataSet = this.performContour(dataSet, item);
				mapToActor(newDataSet, colorScalar, opacity, representation);
			}
			if (item.getThreshold().isVisiable()){
				changed = true;
				vtkDataSet newDataSet = this.performThreshold(dataSet, item);
				mapToActor(newDataSet, colorScalar, opacity, representation);
			}
			if (item.getSlice().isVisiable()){
				changed = true;
				vtkDataSet newDataSet = this.performSlice(dataSet, item);
				mapToActor(newDataSet, colorScalar, opacity, representation);
			}
			if (item.getScalarBar().isVisiable()){
				ScalarBarWidget scalarBar = new ScalarBarWidget(this, colorScalar, interactor, item);
				scalarBar.initWidget();
				widgetList.add(scalarBar);
			}
			if (!changed){
				mapToActor(dataSet, colorScalar, opacity, representation);
			}
		}
	}
	
	/**
	 * Set up color scalar and mapping it into target vtkColorsToScalars
	 * @return colorTransferFunction
	 */
	public vtkColorTransferFunction setColorMapping(vtkDataSet dataSet){
		vtkColorTransferFunction colorTransferFunction = new vtkColorTransferFunction();
		LVSColorMap colorMap = super.getSystemPreference().getChosenColorMap();
		if (colorMap.getColorSpace().equals("Diverging")){
			colorTransferFunction.SetColorSpaceToDiverging();
		}else if (colorMap.getColorSpace().equals("RGB")){
			colorTransferFunction.SetColorSpaceToRGB();
		}else if (colorMap.getColorSpace().equals("Lab")){
			colorTransferFunction.SetColorSpaceToLab();
		}else if (colorMap.getColorSpace().equals("HSV")){
			colorTransferFunction.SetColorSpaceToHSV();
		}else{
			super.getSystemController().getMessageFactory().showMessageDialog(2, "Unrecognized Color Space from " + colorMap.getName() + ".", null);
			super.getSystemPreference().setCurrentColorMap(-1);
		}
		double[][] RGBPoints = colorMap.getRGBPoints();
		for (int i = 0 ; i < RGBPoints.length ; i++){
			double rangeValue = ((dataSet.GetScalarRange()[1] - dataSet.GetScalarRange()[0]) * RGBPoints[i][0]) + dataSet.GetScalarRange()[0];
			colorTransferFunction.AddRGBPoint(rangeValue, RGBPoints[i][1], RGBPoints[i][2], RGBPoints[i][3]);
		}
		colorTransferFunction.SetScaleToLinear();
		
		return colorTransferFunction;
	}
	
	/**
	 * Function call to perform contour filter to the data set
	 * 
	 * @param	dataSet	DataSet containing the data
	 * @param	item FileItem object containing all the information and setting of that imported file
	 * @return newDataSet DataSet object containing the data, this data set is modified by vtkContourFilter.
	 */
	public vtkDataSet performContour(vtkDataSet dataSet, FileItem item){
		vtkContourFilter algorithm = new vtkContourFilter();
		vtkDataSet newDataSet = null;
		algorithm.SetInputData(dataSet);
		
		double[] actualValue = item.getContour().getActualValue();
		if (actualValue == null){
			item.getContour().setCustomRange(item.getDefaultRange());
			item.getContour().setStep(CONTOUR_DEFAULT_STEP);
			item.getContour().generateRange();
			actualValue = item.getContour().getActualValue();
		}
		for (int i = 0 ; i < actualValue.length ; i++ ){
			algorithm.SetValue(i, actualValue[i]);
		}
		algorithm.Update();
		newDataSet = algorithm.GetOutput();
		
		return newDataSet;
	}
	
	/**
	 * Function call to perform slicing using vtkCutter
	 * 
	 * @param	dataSet	DataSet containing the data
	 * @param	item FileItem object containing all the information and setting of that imported file
	 * @return newDataSet DataSet object containing the data, this data set is modified by vtkCutter.
	 */
	public vtkDataSet performSlice(vtkDataSet dataSet, FileItem item){
		vtkCutter algorithm = new vtkCutter();
		vtkDataSet newDataSet = null;
		algorithm.SetInputData(dataSet);
		vtkPlane plane = new vtkPlane();
		
		double xTrans = item.getSlice().getXTrans();
		double yTrans = item.getSlice().getYTrans();
		double zTrans = item.getSlice().getZTrans();
		double[] center = dataSet.GetCenter();
		plane.SetOrigin(center[0] + xTrans, center[1] + yTrans, center[2] + zTrans);
		
		// Normal
		double[] normal = item.getSlice().getNormalValue();
		if (normal == null){	
			plane.SetNormal(SLICE_DEFAULT_NORMAL);
		}else{
			plane.SetNormal(normal);
		}
		algorithm.SetCutFunction(plane);
		algorithm.Update();
		newDataSet = algorithm.GetOutput();
		
		return newDataSet;
	}
	
	/**
	 * Function call to perform thresholding to the data set
	 * 
	 * @param	dataSet	DataSet containing the data
	 * @param	item FileItem object containing all the information and setting of that imported file
	 * @return newDataSet DataSet object containing the data, this data set is modified by vtkThreshold.
	 */
	public vtkDataSet performThreshold(vtkDataSet dataSet, FileItem item){
		vtkThreshold algorithm = new vtkThreshold();
		vtkDataSet newDataSet = null;
		algorithm.SetInputData(dataSet);
		
		double[] range = item.getThreshold().getCustomRange();
		if (range == null){
			double[] defaultRange = item.getDefaultRange();
			algorithm.ThresholdBetween(defaultRange[0], defaultRange[1]);
		}else{
			algorithm.ThresholdBetween(range[0], range[1]);
		}
		algorithm.Update();
		newDataSet = algorithm.GetOutput();
		
		return newDataSet;
	}
	
	/**
	 * Function called map the output from filter to an actor
	 * 
	 * @param	dataSet	The plain data to display
	 * @param	colorScalar Color Scalar
	 * @param	opactiy Opacity of target actor
	 * @param	representation Representation Option 
	 */
	public void mapToActor(vtkDataSet dataSet, vtkScalarsToColors colorScalar, double opacity, int representation){
		vtkDataSetMapper mapper = new vtkDataSetMapper();
		mapper.SetInputData(dataSet);
		mapper.SetLookupTable(colorScalar);
        mapper.SetScalarRange(dataSet.GetScalarRange());
        mapper.Update();
        vtkActor actor = new vtkActor();
        actor.GetProperty().SetOpacity(opacity);
        actor.SetMapper(mapper);
        this.translateActor(actor);
        switch(representation){
        case 0:
            actor.GetProperty().SetRepresentationToSurface();
            break;
        case 1:
            actor.GetProperty().SetRepresentationToPoints();
        	break;
        case 2:
            actor.GetProperty().SetRepresentationToWireframe();
        	break;
        }
        actorList.add(actor);
	}
	
	/**
	 * Function called map the output from filter to an actor
	 * 
	 * @param	dataSet	The plain data to display
	 * @param	algorithm The data after transform or filter 
	 * @param	colorScalar Color Scalar
	 * @param	opactiy Opacity of target actor
	 */
	public void mapToActor(vtkDataSet dataSet, vtkAlgorithm algorithm, vtkScalarsToColors colorScalar, double opacity){
		vtkDataSetMapper mapper = new vtkDataSetMapper();
		mapper.SetInputConnection(algorithm.GetOutputPort());
        mapper.SetLookupTable(colorScalar);
        mapper.SetScalarRange(dataSet.GetScalarRange());
        mapper.Update();
        vtkActor actor = new vtkActor();
        actor.SetMapper(mapper);
        this.translateActor(actor);
        actorList.add(actor);
	}
	
	/**
	 * Function call to translate the actor to origin 0,0,0 in order to favorite multiple actor display in same panel
	 * 
	 */
	public void translateActor(vtkActor actor){
		double x = actor.GetCenter()[0];
		double y = actor.GetCenter()[1];
		double z = actor.GetCenter()[2];
		
		vtkTransform trans = new vtkTransform();
		trans.Push();
		trans.Identity();
		trans.PostMultiply();
		trans.Translate(-x, -y, -z);
		actor.SetUserTransform(trans);
	}
	
	/**
	 * Implement LVS system functions
	 */
	public void implementFunctions(FileItem item){
		sliceTransformFunction(item);
	}
	
	/**
	 * The native resetCamera() from VTK is not working.
	 * This Method reset the camera to the initial position and orientation.
	 */
	public void resetCamera(vtkCamera cam){
	    if( cam == null)
	        return;
	    cam.SetPosition(10 , 0, 100);
	    cam.SetViewUp(0, 0, 0);
	}
	
	/**
	 * Perform sliceTransform function if slice is visible and sliceTranslate is enabled.
	 * @param item
	 */
	public void sliceTransformFunction(FileItem item){
		if (item.getSlice().isVisiable() && isSliceTrans){
			setupSliceTransActor(item);
			actorList.add(sliceTransActor);
			// reset camera and light
			this.resetCamera(panel.GetRenderer().GetActiveCamera());
			panel.GetRenderer().GetActiveCamera().OrthogonalizeViewUp();
			panel.GetRenderer().RemoveAllLights();
			panel.GetRenderer().CreateLight();
		} else if (item.getSlice().isVisiable() && !isSliceTrans){
			saveSliceTransform(item);
		}
	}
	
	/**
	 * Save user changes on slice transform
	 */
	public void saveSliceTransform(FileItem item){
		if (sliceTransActor != null){
			double[] newCenter = sliceTransActor.GetCenter();
			item.getSlice().setXTrans(newCenter[0]);
			item.getSlice().setYTrans(newCenter[1]);
			item.getSlice().setZTrans(newCenter[2]);
		}
	}
	
	/**
	 * Set up the sliceTransformActor from FileItem
	 * @param item
	 */
	public void setupSliceTransActor(FileItem item){
		// setup a plane with normal {0,0,1} 
		vtkPlaneSource planeSource = new vtkPlaneSource();
		double range = item.getDataDeviation();
		planeSource.SetCenter(-range, -range, 0);
		planeSource.SetPoint1(-range, range, 0);
		planeSource.SetPoint2(range, -range, 0);
		planeSource.Update();
		double[] n1 = { 0, 0, 1 };
		double[] n2 = item.getSlice().getNormalValue();
		m4 = generateTransformMatrix(n1, n2);
		vtkTransform t = new vtkTransform();
		t.Push();
		t.Identity();
		t.PostMultiply();
		t.SetMatrix(m4);
		t.Translate(XTrans, YTrans, ZTrans);
		t.Update();
		vtkPolyData planeData = planeSource.GetOutput();
		vtkPolyDataMapper pmapper = new vtkPolyDataMapper();
		pmapper.SetInputData(planeData);
		pmapper.Update();
		sliceTransActor = new vtkActor();
		sliceTransActor.SetUserTransform(t);
		sliceTransActor.SetMapper(pmapper);
		// add normal dataSet for viewing
		File file = new File(item.getPath());
		vtkDataSet dataSet = getDataSet(file);
		double opacity = item.getOpacity();
		int representation = item.getRepresentation();
		vtkScalarsToColors colorScalar = null;
		if (super.getSystemPreference().getCurrentColorMap() != -1){
			colorScalar = setColorMapping(dataSet);
		}else{
			colorScalar = new vtkLookupTable();
			colorScalar.Build();
		}
		this.mapToActor(dataSet, colorScalar, opacity, representation);
	}
	
	/**
	 * Translate slice actor base on user input
	 */
	public void translateSliceActor(){
		FileItem item = super.getSystemController().getCurrentFile().getCurrentActor();
		int width = super.getSystemPreference().getScreenWidth();
		int height = super.getSystemPreference().getScreenHeight();
		Hand hand = super.getSystemController().getLeapMotionFrameController().getStyleHand();
		if (hand == null)
			return;
		int[] handPos = this.getSystemController().getLeapMotionFrameController().getNormalizedHandPosition(hand);
		float handX = handPos[0];
		float handY = handPos[1];
		int dz = (int) (hand.palmVelocity().getZ()) / 50;
		float handZ = 0;
		handZ =  (float) (1 + (0.5 * dz / 10));

		double speed = item.getSlice().getTransformRate();
		if (speed <= 0){
			speed = 0.5;
		}
		if (handX < width * 0.3){
			XTrans -= speed;
		}else if (handX > width * 0.7){
			XTrans += speed;
		}
		if (handY < height * 0.3){
			YTrans += speed;
		}else if (handY > height * 0.7){
			YTrans -= speed;
		}
		if (handZ < 0.85){
			ZTrans -= speed;
		}
		if (handZ > 1.15){
			ZTrans += speed;
		}		
		
		vtkTransform t = new vtkTransform();
		t.PostMultiply();
		t.SetMatrix(m4);
		t.Translate(XTrans, YTrans, ZTrans);
		sliceTransActor.SetUserTransform(t);
		sliceTransActor.Modified();
		interactor.GetRenderWindow().Render();
		
	}
	
	/**
	 * Generate the transform 4x4 Matrix by transforming vector n1 and target vector n2
	 * @param n1 Normal of the panel wish to be transform
	 * @param n2 Normal of the panel wish to be transform to
	 * @return m4 vtkMatrix4x4
	 */
	public vtkMatrix4x4 generateTransformMatrix(double[] n1, double[] n2){
		double[] result = new double[3];
		vtkMath math = new vtkMath();
		math.Cross(n1, n2, result);
		double costheta = math.Dot(n1, n2);
		double sintheta = math.Norm(result);
		double theta = Math.atan2(sintheta, costheta);
		if (sintheta != 0) {
			result[0] /= sintheta;
			result[1] /= sintheta;
			result[2] /= sintheta;
		}
		// convert to quaternion
		costheta = Math.cos(0.5 * theta);
		sintheta = Math.sin(0.5 * theta);
		double[] quaternion = new double[4];
		quaternion[0] = costheta;
		quaternion[1] = result[0] * sintheta;
		quaternion[2] = result[1] * sintheta;
		quaternion[3] = result[2] * sintheta;
		double[][] matrix = new double[4][4];
		matrix = this.quaternionToMatrix(quaternion);
		// Parse the 4x4 Matrix to vtkMatrix4x4 object
		vtkMatrix4x4 m4 = new vtkMatrix4x4();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				m4.SetElement(i, j, matrix[i][j]);
			}
		}
		return m4;
	}
	
	/**
	 * Convert quaternion to a 4x4 matrix
	 * @param quaternion Result quaternion from two vectors normal
	 * @return Result Double[][] Matrix 4x4 
	 */
	public double[][] quaternionToMatrix(double[] quaternion) {
		double[][] result = new double[4][4];
		double w = quaternion[0];
		double x = quaternion[1];
		double y = quaternion[2];
		double z = quaternion[3];
		result[0][0] = 1 - 2 * (y * y + z * z);
		result[0][1] = 2 * (x * y - z * w);
		result[0][2] = 2 * (x * z + y * w);
		result[0][3] = 0;
		result[1][0] = 2 * (x * y + z * w);
		result[1][1] = 1 - 2 * (x * x + z * z);
		result[1][2] = 2 * (y * z - x * w);
		result[1][3] = 0;
		result[2][0] = 2 * (x * z - y * w);
		result[2][1] = 2 * (y * z + x * w);
		result[2][2] = 1 - 2 * (x * x + y * y);
		result[2][3] = 0;
		result[3][0] = 0;
		result[3][1] = 0;
		result[3][2] = 0;
		result[3][3] = 1;

		return result;
	}
	
	/**
	 * Function called set the initial view
	 * Seems like this function no longer require
	 * 
	 * @param	dataSet	The data which user want to use as center
	 */
	public void initView(){
		panel.GetRenderer().AutomaticLightCreationOn();
		panel.GetRenderer().LightFollowCameraOn();
	}
	
	/**
	 * Add Orientation Widget which shows the current orientation of camera
	 */
	public void addOrientationWidget(){
		vtkAxesActor axes = new vtkAxesActor();
		vtkOrientationMarkerWidget widget = new vtkOrientationMarkerWidget();
		widget.SetOutlineColor(0.9300, 0.5700, 0.1300);
		widget.SetOrientationMarker(axes);
		widget.SetInteractor(interactor);
		widget.SetViewport(0.0, 0.0, 0.2, 0.2);
		widget.SetEnabled(1);
		widget.InteractiveOff();
	}
	
	/**
	 * Function adding all actor into the panel
	 * 
	 */
	public void addListToPanel(){
		// Add a empty actor such that the panel will show up even actorList = 0
		panel.GetRenderer().RemoveAllViewProps();
		panel.GetRenderer().AddActor(emptyActor);
		for (int i = 0 ; i <actorList.size() ; i++){
			panel.GetRenderer().RemoveActor(emptyActor);
			panel.GetRenderer().AddActor(actorList.get(i));
		}
		//panel.Render();
		panel.GetRenderer().ResetCamera();
		panel.validate();
		panel.repaint();
		//System.out.println("Num of actor: " + panel.GetRenderer().GetActors().GetNumberOfItems());
	}
	
	/**
	 * Function to check is all VTK native library is loaded
	 * 
	 */
	public void loadVTKLibrary(){
		System.setProperty("java.library.path", ".");
		System.out.println("Make sure the search path is correct: ");
		System.out.println(System.getProperty("java.library.path"));
		if (!vtkNativeLibrary.LoadAllNativeLibraries()) {
			for (vtkNativeLibrary lib : vtkNativeLibrary.values()) {
				if (!lib.IsLoaded())
					System.out.println(lib.GetLibraryName() + " not loaded");
				else
					System.out.println(lib.GetLibraryName() + " loaded");
			}
		}
		System.out.println("VTK Loaded.");
		vtkNativeLibrary.DisableOutputWindow(null);
	}
	
	// Attributes Set and Get
	public vtkPanel getVTKPanel(){
		return this.panel;
	}
	
	public vtkRenderWindowInteractor getVTKRenderWindowInteractor(){
		return this.interactor;
	}
	
	public List<vtkActor> getActorList(){
		return this.actorList;
	}

	public boolean isSliceTrans() {
		return isSliceTrans;
	}

	public void setSliceTrans(boolean isSliceTrans) {
		this.isSliceTrans = isSliceTrans;
	}
}
