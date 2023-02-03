package net.unknownuser.eventreader.keyboard;

import java.util.HashMap;
import java.util.Optional;

public abstract class Key {
	// alphabetic keys
	public static final short A = 30;
	public static final short B = 48;
	public static final short C = 46;
	public static final short D = 32;
	public static final short E = 18;
	public static final short F = 33;
	public static final short G = 34;
	public static final short H = 35;
	public static final short I = 23;
	public static final short J = 36;
	public static final short K = 37;
	public static final short L = 38;
	public static final short M = 50;
	public static final short N = 49;
	public static final short O = 24;
	public static final short P = 25;
	public static final short Q = 16;
	public static final short R = 19;
	public static final short S = 31;
	public static final short T = 20;
	public static final short U = 22;
	public static final short V = 47;
	public static final short W = 17;
	public static final short X = 45;
	public static final short Y = 21;
	public static final short Z = 44;
	
	// number keys
	public static final short NUMBER_1 = 2;
	public static final short NUMBER_2 = 3;
	public static final short NUMBER_3 = 4;
	public static final short NUMBER_4 = 5;
	public static final short NUMBER_5 = 6;
	public static final short NUMBER_6 = 7;
	public static final short NUMBER_7 = 8;
	public static final short NUMBER_8 = 9;
	public static final short NUMBER_9 = 10;
	public static final short NUMBER_0 = 11;
	
	// function keys
	public static final short F1 = 59;
	public static final short F2 = 60;
	public static final short F3 = 61;
	public static final short F4 = 62;
	public static final short F5 = 63;
	public static final short F6 = 64;
	public static final short F7 = 65;
	public static final short F8 = 66;
	public static final short F9 = 67;
	public static final short F10 = 68;
	public static final short F11 = 87;
	public static final short F12 = 88;
	
	// arrow keys
	public static final short UP = 103;
	public static final short LEFT = 105;
	public static final short RIGHT = 106;
	public static final short DOWN = 108;
	
	// left-right variations
	public static final short LEFTCTRL = 29;
	public static final short RIGHTCTRL = 97;
	public static final short LEFTSHIFT = 42;
	public static final short RIGHTSHIFT = 54;
	public static final short LEFTMETA = 125;
	public static final short RIGHTMETA = 126;
	public static final short LEFTALT = 56;
	public static final short RIGHTALT = 100;
	public static final short LEFTBRACE = 26;
	public static final short RIGHTBRACE = 27;
	
	// other
	public static final short MINUS = 12;
	public static final short EQUAL = 13;
	public static final short COMMA = 51;
	public static final short DOT = 52;
	public static final short SLASH = 53;
	public static final short BACKSLASH = 43;
	public static final short SEMICOLON = 39;
	public static final short APOSTROPHE = 40;
	public static final short GRAVE = 41;
	public static final short COMPOSE = 127; // the right-click emulator key
	
	// various other special keys
	public static final short ESC = 1;
	public static final short SPACE = 57;
	public static final short BACKSPACE = 14;
	public static final short TAB = 15;
	public static final short ENTER = 28;
	public static final short HOME = 102; // POS1
	public static final short PAGEUP = 104;
	public static final short PAGEDOWN = 109;
	public static final short END = 107;
	public static final short INSERT = 110;
	public static final short DELETE = 111;
	public static final short CAPSLOCK = 58;
	
	// code to character conversion map
	private static final HashMap<Short, Character> CHARMAP = new HashMap<>();
	static {
		CHARMAP.put(A, 'a');
		CHARMAP.put(B, 'b');
		CHARMAP.put(C, 'c');
		CHARMAP.put(D, 'd');
		CHARMAP.put(E, 'e');
		CHARMAP.put(F, 'f');
		CHARMAP.put(G, 'g');
		CHARMAP.put(H, 'h');
		CHARMAP.put(I, 'i');
		CHARMAP.put(J, 'j');
		CHARMAP.put(K, 'k');
		CHARMAP.put(L, 'l');
		CHARMAP.put(M, 'm');
		CHARMAP.put(N, 'n');
		CHARMAP.put(O, 'o');
		CHARMAP.put(P, 'p');
		CHARMAP.put(Q, 'q');
		CHARMAP.put(R, 'r');
		CHARMAP.put(S, 's');
		CHARMAP.put(T, 't');
		CHARMAP.put(U, 'u');
		CHARMAP.put(V, 'v');
		CHARMAP.put(W, 'w');
		CHARMAP.put(X, 'x');
		CHARMAP.put(Y, 'y');
		CHARMAP.put(Z, 'z');
	}
	
	/**
	 * Turns an InputEvent code into a char.<br>
	 * Note: In this case, a character is letter from a to z.
	 * 
	 * @param code The code for the character.
	 * 
	 * @return An optional containing the character. An empty optional, if the given code is not a
	 *         character.
	 */
	public static Optional<Character> getChar(short code) {
		return Optional.ofNullable(CHARMAP.get(code));
	}
}
