package system.bugreport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** ************************************************************
 * This class use to catch targeted exception and log it.
 * A user report feature could further be implement, to send 
 * those log back to the developer.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ExceptionCatcher {
	static String logPath;
	static File logFile;
	
	public ExceptionCatcher(){
	}
	
	/**
	 * Log Exception in to log.txt
	 * @param e
	 */
	public static void logException(Exception e){
		Writer output;
		try {
			e.printStackTrace();
			// Get Path
			Date date = new Date();
			logFile = new File("log_"+ date.getDate()+ "" +date.getMonth()+ "" +date.getYear()+ "" + date.getTime() + ".txt");
			logPath = logFile.getAbsolutePath();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			String s =  exceptionAsString + "\n";
			// Get Date
			DateFormat tf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Calendar calobj = Calendar.getInstance();
			// Output Log
			output = new BufferedWriter(new FileWriter(logPath, true));
			output.append("Time" + tf.format(calobj.getTime())  + "\n");
			output.append(s);
			output.close();
			sw.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * read the log
	 * @return
	 */
	public static String getLog() {
		String log="",logPart="";
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(logPath));
		
			while((logPart=br.readLine()) != null){
				log+=logPart+"\n";
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return log;
	}
}
