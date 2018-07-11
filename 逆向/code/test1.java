package test;

import java.io.Console;


public class test1 {

	public static char[] key;

	public static void main(String[] paramArrayOfString) {
		key = new char[10];
		key[0] = 'A';
		key[1] = 'o';
		key[2] = 'J';
		key[3] = 'k';
		key[4] = 'V';
		key[5] = 'h';
		key[6] = 'L';
		key[7] = 'w';
		key[8] = 'U';
		key[9] = 'R';
		Console localConsole = System.console();
		String str = "";
		while (!str.equals("ThisIsth3mag1calString4458"))
			str = localConsole.readLine("Enter password:", new Object[0]);
		for (int i = 0; i < key.length; i++)
			System.out.print(key[i]);
		System.out.println("");
	}
}
