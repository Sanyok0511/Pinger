package frame;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.swing.*;

import main.Pinger;

import java.awt.*;

public class CreateForm {
	static JFrame frame;
	static JPanel panel;
	static int count = 0;
	static ExecutorService exec = Executors.newCachedThreadPool();
	/**
	 * Создает фрейм и панели с лейблами с IP-адресами внешними и внутреними. 
	 * @param list Задается для добавления панелей
	 * 
	 */
	public static void start(ArrayList<Pinger> list) {
		frame = new JFrame("Project Pinger");
		frame.setJMenuBar(new MainMenu());
		frame.setAlwaysOnTop(true);
		panel = new JPanel();
		
		frame.add(panel);
		panel.setLayout(new GridLayout(0,1));
		for (Pinger pinger : list) {
			//panel.add(pinger.getPanel());
			exec.execute(pinger);
			//count++;
		}
		frame.setSize(250, 64 + 22 * count);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Скрывает панель с фрейма.
	 * @param pinger
	 * @return true - если удалось скрыть панель
	 */
	public static boolean hide(Pinger pinger) {
		panel.remove(pinger.getPanel());
		frame.setSize(250, 64 + 22 * --count);
		System.out.println(new Date() + " - " + frame.getSize() + "count: " + count);
		frame.revalidate();
		return true;
	}
	/**
	 * Возвращает панель на фрейм.
	 * @param pinger
	 * @return true - когда панель показана
	 */
	public static boolean show(Pinger pinger) {
		panel.add(pinger.getPanel());
		
		frame.setSize(250, 64 + 22 * ++count);
		System.out.println(new Date() + " - " + frame.getSize() + "count: " + count );
		frame.revalidate();
		return false;
	}
	public static void changeAlwOnTop(boolean value) {
		frame.setAlwaysOnTop(value);
	}
}
