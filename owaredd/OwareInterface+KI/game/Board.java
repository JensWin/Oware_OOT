package game;

import gui.GameFrame;

public class Board {

	public int[][] board = new int[2][6];
	private byte spielerZug;
	private Player player[] = new Player[2];
	GameFrame frame;
	private KI ki;

	/**
	 * Board() Erstellt ein Spielfeld mit den Dimension [2][6] und mit der Zahl
	 * 4 befuellt spielerZug wird per Zufall festgelegt auf 0 oder 1
	 */
	public Board(Player player1, Player player2, GameFrame frame) {
		spielerZug = (byte) (Math.random() * 2);
		this.player[0] = player1;
		this.player[1] = player2;
		this.frame = frame;
		resetBoard();
	}

	/**
	 * Konstruktor fuer ComputerSpiel
	 *
	 * @param player1
	 * @param player2
	 * @param frame
	 */
	public Board(KI player1, Player player2, GameFrame frame) {
		this.ki = player1; // Added KI
		spielerZug = (byte) (Math.random() * 2);
		this.player[0] = player1;
		this.player[1] = player2;
		this.frame = frame;
		resetBoard();
	}

	/**
	 * resetBoard() Setzt das Spielfeld zur�ck, sodass �berall der Wert 4
	 * enthalten ist
	 */
	public void resetBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = board[i].length - 1; j >= 0; j--) {
				board[i][j] = 4;
			}
		}

	}

	/**
	 * check() ueberprueft ob ein der Zug gueltig ist
	 *
	 * @param player
	 * @param spalte
	 * @return
	 */
	public boolean check(int player, int spalte) {

		boolean forceTurn = true;

		if (player != spielerZug) { // �bergebener Spieler ungleich dem
			// aktuellen spielerZug
			return false;
		}

		for (int i : board[spielerZug ^ 1]) { // schaut auf Gegnerseite ob ein
			// Feld != 0 ist, falls ja ist
			// kein forceTurn notwendig
			if (i != 0) {
				forceTurn = false;
				break;
			}
		}

		/*
		 * Anzahl der Steine muss bei einem ForceTurn auf die Seite des anderen
		 * Spieler reichen Wenn die Anzahl der Steine im Feld gr��er als die
		 * Seite des Spielers mit abgezogenem Spaltenindex ist, damit der andere
		 * Spieler wieder Steine erh�lt, MUSS DAS SO SEIN!!!!!!!!!!!!!
		 */
		if (board[spielerZug][spalte] != 0) {
			if (forceTurn) {
				if (board[spielerZug][spalte] >= (board[spielerZug].length - spalte)) {
					return true;
				} else {

					return false;
				}

			} else {
				return true;
			}
		}

		return false;
	}

	/**
	 * steal() Sammelt Steine ein und fuegt sie zum Punktekontos des Spieler
	 * hinzu
	 *
	 * @param player
	 * @param spalte
	 */
	public void steal(int player, int spalte) {
		if (player != spielerZug) {
			if (spalte >= 0) {
				if (board[player][spalte] == 2 || board[player][spalte] == 3) {
					this.player[spielerZug].setSpielSteineSpieler(
							this.player[spielerZug].getSpielSteineSpieler() + board[player][spalte]);
					board[player][spalte] = 0;
					steal(player, --spalte);
				}
			}

		}
		frame.printPoints();
		frame.printButtonValues();
	}

	/**
	 * turn() Zum Zug ausfuehren
	 *
	 * @param player
	 * @param spalte
	 */
	public void turn(int player, int spalte) {

		int tempPunkte = this.player[spielerZug].getSpielSteineSpieler();
		if (check(player, spalte)) {

			int tmpZeile = player;
			int tmpSpalte = spalte;
			int steinAnzahl = board[tmpZeile][tmpSpalte];
			board[tmpZeile][tmpSpalte] = 0;

			while (steinAnzahl > 0) {
				tmpSpalte++;
				if (tmpSpalte >= 6) {
					tmpSpalte = 0;
					tmpZeile ^= 1;
				}

				if (tmpSpalte == spalte && tmpZeile == player) {
					tmpSpalte++;
				} else {
					board[tmpZeile][tmpSpalte] += 1;
					steinAnzahl--;

				}
			}
			steal(tmpZeile, tmpSpalte);
			spielerZug^=1;
			if (noTurnPossible()) {
				frame.gameEnde();
			} else {

				if (spielerZug == 0) {
					frame.setInfoP2("\t\t"+this.player[spielerZug ^ 1].getName() + " hat "
							+ (this.player[spielerZug ^ 1].getSpielSteineSpieler() - tempPunkte) + " Steine geraubt");// Aktualisiert
					// Text
					frame.printButtonValues(); // Aktualisiert buttons

					if (!winnerCheck() && isKITurn()) // �berpr�ft ob gegner eine KI ist
						frame.enableKIButton();
				} else {
					frame.setInfoP1("\t\t"+this.player[spielerZug ^ 1].getName() + " hat "
							+ (this.player[spielerZug ^ 1].getSpielSteineSpieler() - tempPunkte) + " Steine geraubt");// Aktualisiert
					// Text
					frame.printButtonValues(); // Aktualisiert buttons

					if (!winnerCheck() && isKITurn()) { // �berpr�ft ob gegner eine KI ist
						frame.enableKIButton();
					}
				}
			}
		}
	}


	/**
	 * winnerCheck() Ueberprueft ob ein Gewinner bereits feststeht
	 *
	 * @return true / false
	 */
	public boolean winnerCheck() {
		if (player[spielerZug ^ 1].getSpielSteineSpieler() > 24) {
			frame.gameEnde();
			return true;
		} else
			return false;
	}

	/**
	 * winningPlayer() Gibt den Gewinner zurueck
	 *
	 * @return
	 */
	public Player winningPlayer() {
		if (player[0].getSpielSteineSpieler() > player[1].getSpielSteineSpieler()) {
			return player[0];
		} else if (player[1].getSpielSteineSpieler() > player[0].getSpielSteineSpieler()) {
			return player[1];
		} else
			return null;
	}

	/**
	 * checkKIturn() Prueft ob der naechste Zug ein Computerzug ist
	 */
	public boolean isKITurn() {
		if (this.player[spielerZug].getKI()) {
			return true;
		}
		return false;
	}

	/**
	 * endeSteal() sammelt alle uebrigen Steine am Ende des Spiels ein
	 */
	public void endeSteal() {
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 6; i++) {
				player[j].setSpielSteineSpieler(player[j].getSpielSteineSpieler() + board[j][i]);
				board[j][i] = 0;
			}
		}
	}

	/**
	 * noTurnPossible() Stellt fest ob kein Zug mehr moeglich ist
	 *
	 * @return
	 */
	public boolean noTurnPossible() {

		for (int i = 0; i < 6; i++) {
			if (check(spielerZug, i) || check(spielerZug ^ 1, i)) {
				return false;
			}
		}
		return true;
	}

	public byte getSpielerZug() {
		return spielerZug;
	}

	/**
	 * toString()
	 */
	@Override
	public String toString() {
		String s = "";

		for (int j = board[0].length - 1; j >= 0; j--) { // Durchlaeuft das
			// Brett rueckwaertz
			s = s + "[" + board[0][j] + "]";
		}
		s = s + "\n"; // Zeilenumbruch
		for (int j = 0; j < board[1].length; j++) { // Durchlaeuft das Brett
			// vorwaertz
			s = s + "[" + board[1][j] + "]";
		}
		return s;
	}

}
