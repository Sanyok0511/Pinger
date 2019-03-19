package main;

import java.awt.Color;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.awt.GridLayout;
import java.io.IOException;

import log.typeLog;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import frame.CreateForm;
import static log.LogWriter.*;
import static sender.TelegramSender.sendMessage;
/**
 * 
 * @author Pokrovskiy Alexander
 * @version 0.001a
 *
 */


public class Pinger implements Runnable {
	private String newLine = "%0D%0A";
	InetAddress lan, wan;
	String comment;
	JPanel panel;
	JLabel labelLan, labelWan;
	boolean hiden = true;
	int pingLan = 4, pingWan = 4;
	public String toString() {
		return (labelWan != null) ? "Lan IP: " + labelLan.getText() + ", WAN IP: " + labelWan.getText() + " - " + comment :
			"Lan IP: " + labelLan.getText() + " - " + comment;
	}
	/**
	 * По Lan и Wan адресам создается объект, который содержит панель с двумя JLabel (или с LAN, если WAN отсутствует)
	 * @param lan Lan IP-address 
	 * @param wan WAN IP-address
	 * @param comment Address
	 */
	public Pinger(String lan, String wan, String comment) {
		try {
			this.lan = InetAddress.getByName(lan);
			if (!wan.isEmpty())
				this.wan = InetAddress.getByName(wan);
			this.comment = comment;
			System.out.println("Added: " + lan + " : " + wan + " : " + comment + " ::: " + wan.toString());
			
		} catch (UnknownHostException ex) {
			writeLogFile(typeLog.ERROR, "UnknowHost" + lan.toString() + "//" + wan.toString());
			return;
		}
		panel = new JPanel();
		
		labelLan = new JLabel(lan, JLabel.CENTER);
		labelLan.setOpaque(true);
		labelLan.setBackground(Color.YELLOW);
		labelLan.setBorder(new MatteBorder(0,0,1,1,Color.BLUE));
		
		if (!wan.isEmpty()) {
			labelWan = new JLabel(wan, JLabel.CENTER);
			labelWan.setOpaque(true);
			labelWan.setBackground(Color.YELLOW);
			labelWan.setBorder(new MatteBorder(0,0,1,1,Color.BLUE));
		}
				
		panel.setLayout(new GridLayout(1,2));
		panel.add(labelLan);
		if (labelWan != null)
			panel.add(labelWan);
		panel.setBorder(new MatteBorder(2,2,2,2, Color.ORANGE));
		panel.setToolTipText(comment);
	}
	/**
	 * Позращает панель с двумя JLabel, на которых прописаны IP-адреса
	 * @return
	 */
	public JPanel getPanel() {
		return panel;
	}
	/**
	 * Проверяет доступность по LAN-адресу и по WAN (если доступно), если больше 5 пакетов потеряно, то вернет False, и окрасит JLabel в красный. 
	 * @return true if lan and wan IP-addresses isReachable(500) for 5 times, else return false
	 */
	public boolean ping() {
		if (pingLan > 20) pingLan = 4;
		if (pingWan > 20) pingWan = 4;
		try {
			if (lan.isReachable(500)) pingLan = 0; else pingLan++;
			if (wan != null) if (wan.isReachable(500)) pingWan = 0; else pingWan++;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (pingLan > 4) labelLan.setBackground(Color.RED); else labelLan.setBackground(Color.GREEN);
		if (labelWan != null ) if (pingWan > 4) labelWan.setBackground(Color.RED); else labelWan.setBackground(Color.GREEN);
		return ((pingLan < 5) & (pingWan < 5));
	}
	public void run() {
		while (!Thread.interrupted()) {
			try {
				TimeUnit.SECONDS.sleep(5);
				// Если узел не пингуется по одному из адресов, то проверить, скрыта ли панель, если да, то нужно вывести панель на фрейм
				if (!ping() & hiden) { 
					hiden = CreateForm.show(this);
					writeLogFile(typeLog.CONNECTION_STATUS, this + " connection LOST");
					if (labelWan != null) sendMessage(
							"Lost connection:" + newLine + 
							"Lan address: " + labelLan.getText() + newLine + 
							"Wan address: " + labelWan.getText() + newLine + 
							"Address: " + comment);
					else sendMessage(
							"Lost connection:" + newLine +
							"Lan address: " + labelLan.getText() + newLine +
							"Address: " + comment);
					//System.out.println("Lost connection: " + this); 
					}
				// Если узел пингуется и не скрыт, то скрывает панель с фрейма
				if (ping() & !hiden) { 
					hiden = CreateForm.hide(this);
					writeLogFile(typeLog.CONNECTION_STATUS, this + " connection OK");
					if (labelWan != null) sendMessage(
							"Connection restored:" + newLine +
							"Lan address: " + labelLan.getText() + newLine +
							"Wan address: " + labelWan.getText() + newLine +
							"Address: " + comment);
					else sendMessage(
							"Connection restored:" + newLine +
							"Lan address: " + labelLan.getText() + newLine +
							"Address: " + comment);
					//System.out.println("Connection restored: " + this);
					}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
		}
	}
	
}
