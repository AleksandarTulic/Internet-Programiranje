package logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

public class MyLogger {
	public final static Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
	private static final String LOG_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + 
			File.separator + "ESHOP" + File.separator + "LOG_FOLDER" + File.separator + "SupportApp" + File.separator + 
			System.currentTimeMillis() + ".log";
	
	static {
		File f = new File(LOG_PATH);
		File tmp = f.getParentFile();
		
		List<String> arr = new ArrayList<>();
		while(!tmp.exists()) {
			arr.add(tmp.getPath());
			tmp = tmp.getParentFile();
		}
		
		
		for (int i=arr.size()-1;i>=0;i--) {
			tmp = new File(arr.get(i));
			tmp.mkdir();
		}
	}
	
	static {
		LogManager.getLogManager().reset();
		logger.setLevel(Level.ALL);
		
		try {
			FileHandler fileHandler = new FileHandler(LOG_PATH);
			logger.addHandler(fileHandler);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
