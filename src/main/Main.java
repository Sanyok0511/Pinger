package main;

import java.util.*;

import frame.CreateForm;

public class Main {
	public static void main(String[] args) {
		ArrayList<Pinger> pingers = AddrReader.readFile();
		CreateForm.start(pingers);
	}
}
