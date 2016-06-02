package game;

public class KI extends Player {
	
	private int difficulty;

	public KI(int difficulty) {
		super("Computer");
		super.KI = true;
		this.difficulty = difficulty;

	}

	public void turn(Board board) {
		if (difficulty == 0) {
			turnEasy(board);
		} else if (difficulty == 1) {
			turnNormal(board);
		} else if (difficulty == 2) {
			turnHard(board);
		}

	}

	private void turnEasy(Board board) {
		int i;
		while (true) {
			i = (int) (Math.random() * 6);
			if (board.check(0, i)) {
				board.turn(0, i);
				break;
			}
		}

	}

	private void turnNormal(Board board) {
		
		int pointCounter = 0;
		int tempCounter;
		int saveTurn = 0;
		int[][] boardArray = new int[2][6];

		for (int i = 0; i < 6; i++) {
			tempCounter = 0;
			if (board.board[0][i] == 0) {
				tempCounter = -1000;
			}
			for (int m = 0; m < 2; m++) {
				for (int k = 0; k < 6; k++) {
					boardArray[m][k] = board.board[m][k];
				}
			}

			tempCounter += fakeTurn(board, 0, i, boardArray);

			if (pointCounter == tempCounter) {
				if (Math.random() < 0.5) { // Random bei gleichheit
					pointCounter = tempCounter;
					saveTurn = i;
				}
			} else if (pointCounter < tempCounter) {
				saveTurn = i;
				pointCounter = tempCounter;
			}
		}
		board.turn(0, saveTurn);
	}

	private void turnHard(Board board) {
		int pointCounter = 0;
		int tempCounter = 0; // Score f�r die Z�ge, ein geholter Stein gibt +1
								// ein verlorender gibt -1
		int saveTurn=0; // am ende wir der Zug mit dem besten Score ausgef�hrt
		int[][] boardArray1 = new int[2][6];
		int[][] boardArray2 = new int[2][6];
		int[][] boardArray3 = new int[2][6];

		for (int i1 = 0; i1 < 6; i1++) { // alle m�glichkeiten f�r den ersten
											// Zug
			tempCounter =0;
			if (board.check(0, i1)) {
				tempCounter+=100;
					
					for (int m = 0; m < 2; m++) {
						for (int k = 0; k < 6; k++) {
							boardArray1[m][k] = board.board[m][k]; // Array wird
																	// jedes
																	// mal
																	// zur�ck
																	// gesetzt
						}
					}

					tempCounter += fakeTurn(board, 0, i1, boardArray1); // addiert
																		// die
																		// gewonnenen
																		// Punkte

					for (int i2 = 0; i2 < 6; i2++) { // alle m�glichkeiten f�r
														// den
														// zweiten Zug (Gegner)

						for (int m = 0; m < 2; m++) {
							for (int k = 0; k < 6; k++) {
								boardArray2[m][k] = boardArray1[m][k]; // Array
																		// wird
																		// jedes
																		// mal
																		// auf
																		// das
																		// array
																		// zuvor
																		// zur�ckgesetzt
							}
						}
						tempCounter -= fakeTurn(board, 0, i1, boardArray2); // sub.
																			// die
																			// verlorenden
																			// Punkte

						for (int i3 = 0; i3 < 6; i3++) { // alle m�glichkeiten
															// f�r den
															// dritten Zug

							for (int m = 0; m < 2; m++) {
								for (int k = 0; k < 6; k++) {
									boardArray3[m][k] = boardArray2[m][k]; // Array
																			// wird
																			// jedes
																			// mal
																			// auf
																			// das
																			// array
																			// zuvor
																			// zur�ckgesetzt
								}
							}
							tempCounter += fakeTurn(board, 0, i1, boardArray3); // addiert
																				// die
																				// gewonnenen
																				// Punkte

							if (pointCounter == tempCounter) {
								if (Math.random() < 0.5) { // Random true or
															// false 50/50
															// bei Gleichheit
									pointCounter = tempCounter;
									saveTurn = i1;
								}
							} else if (pointCounter < tempCounter) { // der
																		// bessere
																		// Zug
																		// wird
																		// gespeichert
								saveTurn = i1;
								pointCounter = tempCounter;
							}

						}
					
				}
			}
		}
	
	
		board.turn(0, saveTurn); // der richtige Zug wird ausgef�hrt
	}

	private int fakeTurn(Board board, int player, int spalte, int[][] boardArray) {
		int points = 0;
			
		if (board.check(player, spalte)) {	//�berpr�ft auf m�glichkeit
			int tmpZeile = player;
			int tmpSpalte = spalte;
			int steinAnzahl = boardArray[tmpZeile][tmpSpalte];
			boardArray[tmpZeile][tmpSpalte] = 0;
			while (steinAnzahl > 0) {
				tmpSpalte++;
				if (tmpSpalte >= 6) {
					tmpSpalte = 0;
					tmpZeile ^= 1;
				}

				if (tmpSpalte == spalte && tmpZeile == player) {
					tmpSpalte++;
				} else {
					boardArray[tmpZeile][tmpSpalte] += 1;
					steinAnzahl--;

				}
			}

			return points += fakeSteal(board, tmpZeile, tmpSpalte, boardArray);

		}
		return points;
	}

	private int fakeSteal(Board board, int player, int spalte, int[][] boardArray) {
	
		int tempPoints = 0;
		if (player != board.getSpielerZug()) {	//darf nicht der aktive Spieler sein
			if (spalte >= 0) {			//bis auf Mulde 0
				if (boardArray[player][spalte] == 2
						|| boardArray[player][spalte] == 3) {		//bei 2 oder 3 wird geklaut
					tempPoints += boardArray[player][spalte];		//punkte werden hochgez�hlt
					boardArray[player][spalte] = 0;		//auf dem geklauten Feld liegen danach 0 Steine
					return tempPoints += fakeSteal(board, player, --spalte,		//ruft die methode auf ein feld vorher auf und gibt punkte zur�ck
							boardArray);
				}
			}

		}
		return 0;

	}

}
