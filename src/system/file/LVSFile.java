package system.file;

import java.util.ArrayList;
import java.util.List;

/** ************************************************************
 * This class is an Object Class which holds all the attribute of
 * an LVS File. Including a list holds all the imported file
 * and parameters referencing the displayed actor in VTKPanel.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSFile implements java.io.Serializable{
	public static final int STATUS_SAVED = 0;
	public static final int STATUS_EDITED = 1;
	private List<FileItem> fileList;
	private String fileName;
	private String path;
	private int status = 0;
	private int showingActor = 0;
	
	/**
	 * Constructor
	 * @param name Name of the imported file
	 * @param path Path of the imported file
	 */
	public LVSFile(String name, String path) {
		this.fileList = new ArrayList<FileItem>();
		this.setFileName(name);
		this.setStatus(0);
		this.setPath(path);
	}

	/**
	 * Import new file
	 * @param item Imported File Object
	 */
	public void addFile(FileItem item){
		fileList.add(item);
	}
	
	/**
	 * Remove file by id
	 * @param id ID of the file user want to remove
	 */
	public void deleteFile(int id){
		for (int i = 0 ; i < fileList.size() ; i++){
			if (fileList.get(i).getId() == id){
				fileList.remove(i);
			}
			if (i >= id && i < fileList.size()){
				//System.out.print("Getting : " + fileList.get(i).getId() + " | ");
				fileList.get(i).setId(fileList.get(i).getId() - 1);
				//System.out.println("ChangeTo : " + fileList.get(i).getId());
			}
		}
	}
	
	/**
	 * Print all file's path
	 */
	public void printAllFile(){
		for (int i = 0 ; i < fileList.size() ; i++){
			System.out.println(fileList.get(i).getPath());
		}
	}

	/**
	 * Change the display actor to previous by decrement showingActor
	 */
	public void previousActor(){
		if (this.showingActor > 0)
			this.showingActor--;
	}
	
	/**
	 * Change the display actor to previous by Increment showingActor
	 */
	public void nextActor(){
		if (this.showingActor < fileList.size() - 1)
			this.showingActor++;
	}

	// Attributes Set and Get
	public void setFileList(List<FileItem> fileList){
		this.fileList = fileList;
	}
	
	public List<FileItem> getFileList(){
		return this.fileList;
	}
	
	public FileItem getItemById(int id){
		FileItem file = null;
		for (int i = 0 ; i < fileList.size() ; i++){
			if (fileList.get(i).getId() == id)
				file = fileList.get(i);
		}
		return file;
	}

	public FileItem getCurrentActor(){
		return this.getItemById(showingActor);
	}
	public String getFileName() {
		return fileName;
	}
	
	public int getNextItemID(){
		int max = -1;
		for (int i = 0 ; i < fileList.size() ; i++){
			int id = fileList.get(i).getId();
			if (id > max){
				max = id;
			}else if (id == max){
				System.out.println("File Id Error");
			}
		}
		max++;
		return max; 
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String toString(){
		return this.fileName;
	}

	public int getShowingActor() {
		return showingActor;
	}

	public void setShowingActor(int showingActor) {
		this.showingActor = showingActor;
	}
}
