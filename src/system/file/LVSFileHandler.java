package system.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JFrame;

import system.bugreport.ExceptionCatcher;
import system.controller.SystemController;
import system.general.SystemPreference;
import system.model.LVSHandler;
import ui.awt.res.LVSConfigInfo;
import ui.awt.res.LVSStringInfo;

/** ************************************************************
 * This class is File Handler which handles all the operation
 * working with LVSFile, for instance, import, save and update.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSFileHandler extends LVSHandler{
	/**
	 * Constructor
	 * @param systemController	controller of system    
	 * @param systemPreference	User's Preference of the system
	 * @param frame	Main Frame
	 */
	public LVSFileHandler(SystemController systemController, SystemPreference systemPreference, JFrame frame) {
		super(systemController, systemPreference, frame);
	}
	
	/**
	 * Serialize the current file
	 * 
	 * @param path Absolute path of the saving destination
	 * @param file The current file
	 */
	public void writeFile(String path, LVSFile file){
		String message = "";
		if (path == null){
			message = "File not exsist.";
			super.getSystemController().updateSystemStatus(message);
			System.out.println(message);
			return;
		}else if (file == null){
			message = "Nothing to save.";
			super.getSystemController().updateSystemStatus(message);
			System.out.println(message);
			return;
		}
		if (this.checkFile(path) && file != null){
			try {
				FileOutputStream fileOut = new FileOutputStream(path);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
		        out.writeObject(file);
		        out.close();
		        fileOut.close();
		        super.getSystemController().getSwingController().changeFrameName(file.getFileName());
			} catch (FileNotFoundException e) {
				message = "File Not Found";
				super.getSystemController().updateSystemStatus(message);
				System.out.println(message);
				ExceptionCatcher.logException(e);
			} catch (IOException e){
				message = "IO Error";
				super.getSystemController().updateSystemStatus(message);
				System.out.println(message);
				ExceptionCatcher.logException(e);
			}
		}else if (!path.equals("") && file != null){
			this.createFile(path);
			this.writeFile(path, file);
		}
	}
	
	
	
	/**
	 * Read the file and deserialize it
	 * 
	 * @param fileInfo Array holds Name and Path of the file user selected
	 * @return file The deserialized object
	 */
	public LVSFile readFile(String[] fileInfo){
		LVSFile file = null;
		List<FileItem> list = null;
		if (fileInfo[0] != null && fileInfo[1] != null){
			if (!fileInfo[0].equals("") && !fileInfo[1].equals("")){
				try {
					FileInputStream fileIn = new FileInputStream(fileInfo[1]);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					file = (LVSFile) in.readObject();
			        in.close();
				} catch (IOException e) {
					ExceptionCatcher.logException(e);
				} catch (ClassNotFoundException e) {
					ExceptionCatcher.logException(e);
				}
			}
		}
		
        return file;
	}
	
	/**
	 * Check has LVS file or not
	 * @return boolean has file
	 */
	public boolean hasFile(){
		return super.getSystemController().getCurrentFile() != null;
	}
	
	/**
	 * Check file status
	 * @return int
	 */
	public int fileStatus(){
		return super.getSystemController().getCurrentFile().getStatus();
	}
	
	/**
	 * Show message dialog with saving options
	 * 0 = save
	 * 1 = save as
	 * 2 = cancel
	 */
	public void saveFileRequest(){
		int result = super.getSystemController().getMessageFactory().showMessageDialog(3, " ( "+super.getSystemController().getCurrentFile().getFileName()+ " ) " + LVSStringInfo.SAVE_MESSAGE, LVSConfigInfo.SAVE_OPTIONS);
		switch(result){
		case 0:
			saveLVSFile();
			super.getSystemController().getCurrentFile().setStatus(LVSFile.STATUS_SAVED);
			break;
		case 1:
			String[] fileInfo = this.chooseSavePath();
			String saveAsPath = fileInfo[1];
			this.writeFile(saveAsPath, super.getSystemController().getCurrentFile());
			super.getSystemController().getCurrentFile().setStatus(LVSFile.STATUS_SAVED);
			break;
		case 2:
			break;
		default:
			System.out.println("Error");
		}
	}
	
	/**
	 * Create new LVS file
	 */
	public void newLVSFile(){
		if (hasFile() && fileStatus() == 1){
			this.saveFileRequest();
		}else{
			LVSFile file = new LVSFile(LVSConfigInfo.DEFAULT_FILE_NAME, null);
			super.getSystemController().setCurrentFile(file);
			super.getSystemController().getSwingController().updateVTK();
		}
	}
	
	/**
	 * Save current LVS file
	 */
	public void saveLVSFile(){
		LVSFile file = super.getSystemController().getCurrentFile();
		String message = "";
		if (file != null){
			String savePath = file.getPath();
			if (savePath == null){
				String[] fileInfo = this.chooseSavePath();
				file.setFileName(fileInfo[0]);
				file.setPath(fileInfo[1]);
				savePath = fileInfo[1];
			}
			this.writeFile(savePath, file);
			message = "File Saved.";
			super.getSystemController().getCurrentFile().setStatus(LVSFile.STATUS_SAVED);
		}else{
			message = "Nothing to save";
		}
		super.getSystemController().updateSystemStatus(message);
		System.out.println(message);
	}
	
	/**
	 * Open a LVS file
	 */
	public void openLVSFile(){
		String[] openFileInfo = chooseReadPath(LVSConfigInfo.LVS_FILTER_DESC, LVSConfigInfo.LVS_FILTER_OPTION);
		LVSFile file = readFile(openFileInfo);
		if (file != null){
			checkFileItem(file);
			getSystemController().setCurrentFile(file);
			getSystemController().getSwingController().fileLoaded(file);
		}
	}
	
	/**
	 * Save as a new LVS file
	 */
	public void saveAsLVSFile(){
		String[] fileInfo = chooseSavePath();
		String name = fileInfo[0];
		String savePath = fileInfo[1];
		if (getSystemController().getCurrentFile() !=null && savePath != null && !savePath.equals("")){
			getSystemController().getCurrentFile().setFileName(name);
			getSystemController().getCurrentFile().setPath(savePath);
			writeFile(savePath, getSystemController().getCurrentFile());
		}else{
			String message = "No file to be saved or no path selected.";
			super.getSystemController().updateSystemStatus(message);
			System.out.println(message);
		}
	}
	
	/**
	 * Check file item if the item is not exist, ask user to re-link or remove the file
	 * @param file LVSFile system currently working with
	 */
	public void checkFileItem(LVSFile file){
		List<FileItem> fileList = file.getFileList();
		for (int i = 0 ; i < fileList.size() ; i++){
			File f = new File(fileList.get(i).getPath());
			if (!f.exists()){
				int result = this.getSystemController().getMessageFactory().showMessageDialog(3, fileList.get(i).getName() + LVSStringInfo.FILE_NOT_FOUND, LVSConfigInfo.FILE_NOT_FOUND_OPTIONS);
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
							super.getSystemController().getVTKController().extractInfo(fileList.get(i));
						}
					}
					break;
				case 2:
					break;
				default:
				}
			}
		}
	}
}
