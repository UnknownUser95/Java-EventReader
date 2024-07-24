package net.unknownuser.eventreader.keyboard;

import java.util.*;

public enum KeyboardKey {
	// alphabetic keys
	A(30),
	B(48),
	C(46),
	D(32),
	E(18),
	F(33),
	G(34),
	H(35),
	I(23),
	J(36),
	K(37),
	L(38),
	M(50),
	N(49),
	O(24),
	P(25),
	Q(16),
	R(19),
	S(31),
	T(20),
	U(22),
	V(47),
	W(17),
	X(45),
	Y(21),
	Z(44),
	
	// number keys
	NUMBER_1(2),
	NUMBER_2(3),
	NUMBER_3(4),
	NUMBER_4(5),
	NUMBER_5(6),
	NUMBER_6(7),
	NUMBER_7(8),
	NUMBER_8(9),
	NUMBER_9(10),
	NUMBER_0(11),
	
	// function keys
	F1(59),
	F2(60),
	F3(61),
	F4(62),
	F5(63),
	F6(64),
	F7(65),
	F8(66),
	F9(67),
	F10(68),
	F11(87),
	F12(88),
	
	// arrow keys
	UP(103),
	LEFT(105),
	RIGHT(106),
	DOWN(108),
	
	// left-right variations
	LEFTCTRL(29),
	RIGHTCTRL(97),
	LEFTSHIFT(42),
	RIGHTSHIFT(54),
	LEFTMETA(125),
	RIGHTMETA(126),
	LEFTALT(56),
	RIGHTALT(100),
	LEFTBRACE(26),
	RIGHTBRACE(27),
	
	// other
	MINUS(12),
	EQUAL(13),
	COMMA(51),
	DOT(52),
	SLASH(53),
	BACKSLASH(43),
	SEMICOLON(39),
	APOSTROPHE(40),
	GRAVE(41),
	COMPOSE(127),    // the right-click emulator key
	ESC(1),
	SPACE(57),
	BACKSPACE(14),
	TAB(15),
	ENTER(28),
	HOME(102), // or POS1
	PAGEUP(104),
	PAGEDOWN(109),
	END(107),
	INSERT(110),
	DELETE(111),
	CAPSLOCK(58);
	
	// code to character conversion map
	private static final HashMap<Short, Character> CHARMAP = new HashMap<>();
	static {
		CHARMAP.put(A.value, 'a');
		CHARMAP.put(B.value, 'b');
		CHARMAP.put(C.value, 'c');
		CHARMAP.put(D.value, 'd');
		CHARMAP.put(E.value, 'e');
		CHARMAP.put(F.value, 'f');
		CHARMAP.put(G.value, 'g');
		CHARMAP.put(H.value, 'h');
		CHARMAP.put(I.value, 'i');
		CHARMAP.put(J.value, 'j');
		CHARMAP.put(K.value, 'k');
		CHARMAP.put(L.value, 'l');
		CHARMAP.put(M.value, 'm');
		CHARMAP.put(N.value, 'n');
		CHARMAP.put(O.value, 'o');
		CHARMAP.put(P.value, 'p');
		CHARMAP.put(Q.value, 'q');
		CHARMAP.put(R.value, 'r');
		CHARMAP.put(S.value, 's');
		CHARMAP.put(T.value, 't');
		CHARMAP.put(U.value, 'u');
		CHARMAP.put(V.value, 'v');
		CHARMAP.put(W.value, 'w');
		CHARMAP.put(X.value, 'x');
		CHARMAP.put(Y.value, 'y');
		CHARMAP.put(Z.value, 'z');
	}
	public final short value;
	KeyboardKey(int value) {
		this.value = (short) value;
	}

	/**
	 * Turns an InputEvent code into a char.<br>
	 * Note: In this case, a character is letter from a to z.
	 *
	 * @param code The code for the character.
	 *
	 * @return An optional containing the character. An empty optional, if the given code is not a character.
	 */
	public static Optional<Character> getChar(short code) {
		return Optional.ofNullable(CHARMAP.get(code));
	}
	
	public static Optional<KeyboardKey> fromShort(short value) {
		for (KeyboardKey key : values()) {
			if (key.value == value) {
				return Optional.of(key);
			}
		}
		
		return Optional.empty();
	}
}
