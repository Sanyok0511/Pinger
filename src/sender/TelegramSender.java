package sender;
import java.net.URL;
import java.util.Properties;

import log.LogWriter;
import log.typeLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.net.HttpURLConnection;

public class TelegramSender {
	private static String telegramBot;
	private static String telegramChat;
	static {
		try {
			Properties properties = new Properties();
			FileInputStream inputProperties = new FileInputStream("./config/telegram.properties");
			properties.load(inputProperties);
			telegramBot = properties.getProperty("bot");
			telegramChat = properties.getProperty("chat");
			System.out.println("chat=" + telegramChat + "\nBot=" + telegramBot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] arsg) {
		sendMessage("line%0D%0Anew line");
	}
	public static synchronized void sendMessage(String msg) {
		String message = ("https://api.telegram.org/bot" + telegramBot + "/sendMessage?chat_id=" + telegramChat + "&text=" + msg).replaceAll(" ", "%20");
		try {
			URL url = new URL(message);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			System.out.println(connection.getResponseCode());
			connection.disconnect();
		} catch (IOException e) {
			LogWriter.writeLogFile(typeLog.ERROR, e.getMessage());;
			e.printStackTrace();
		}
		
	}
}


