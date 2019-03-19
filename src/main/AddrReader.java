package main;

import java.io.*;
import java.util.*;

import log.typeLog;
import static log.LogWriter.*;


public class AddrReader {
		public static ArrayList<Pinger> readFile() {
			BufferedReader reader;
			ArrayList<Pinger> pingers = new ArrayList<>();
			try {
				String temp;
				reader = new BufferedReader(new InputStreamReader(new FileInputStream("./ips.ini"),"Cp1251"));
				while ((temp = reader.readLine()) != null)
						{
							if (temp.startsWith("#")) continue;
							if (!temp.equals("")) {
									//System.out.println(temp);
									pingers.add(new Pinger(temp.split("\\|")[0], temp.split("\\|")[1], temp.split("\\|")[2]));
							}
							
						}
				System.out.println("End reading file!");
				reader.close();
			} catch (IOException ex) {
				writeLogFile(typeLog.ERROR, "Error reading ips.ini");
			}
			return pingers;
		}
		
		public static void main(String[] args) {
			readFile();
		}
}
