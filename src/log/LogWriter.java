package log;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class LogWriter {
	private static SimpleDateFormat df;
	
	static {
		df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	}
	public static synchronized void writeLogFile(typeLog type, String msg) {
		Date date = new Date();
							
		File file;
		BufferedWriter writer;
		try {
			if (type == typeLog.ERROR) {
				file = new File("./logs/Error.log");
				if (!file.exists()) file.createNewFile();
				writer = new BufferedWriter(new FileWriter(file, true));
			}
			else {
				file = new File("./logs/Connection.log");
				if (!file.exists()) file.createNewFile();
				
				writer = new BufferedWriter(new FileWriter(file, true));
			}
			writer.write(df.format(date) + " - "+ msg + ";\n");
			writer.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	public static void main(String[] args) {
		writeLogFile(typeLog.CONNECTION_STATUS,"Lost Connection");
	}
}
