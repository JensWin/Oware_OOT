package gui;

//test comment
import game.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameFrame extends Application {

	private Board board;
	Player player1;
	Player player2;

	private GridPane gridButtons;
	private Button[][] buttons;
	private Label labelP1;
	private Label labelP2;
	private Label infoLabelP1;
	private Label infoLabelP2;
	private Label infoLabelWin;
	private Button endButton;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		vsChoice(primaryStage);
	}

	/**
	 * initializeButtons() Inititalisiert die einzelnen Buttons
	 * 
	 * @param buttons
	 */
	public void initializeButtons(Button[][] buttons) {
		/*
		 * Die Reihe des oberen Spielers muss rückwärts ausgegeben werden
		 */
		int p = 0;
		for (int i = board.board[0].length - 1; i >= 0; i--) {
			buttons[0][i] = new Button(i + "");
			gridButtons.add(buttons[0][i], p, 5);
			buttons[0][i].setVisible(true);
			buttons[0][i].setPadding(new Insets(50, 50, 50, 50));
			p++;
			buttons[0][i].getStyleClass().add("buttonSpiel");
		}
		// Reihe des unteren Spielers
		for (int i = 0; i < board.board[1].length; i++) {
			buttons[1][i] = new Button(i + "");
			gridButtons.add(buttons[1][i], i, 10);
			buttons[1][i].setVisible(true);
			buttons[1][i].setPadding(new Insets(50, 50, 50, 50));
			buttons[1][i].getStyleClass().add("buttonSpiel");
		}

		/*
		 * Jeder Buttonklick soll die Methode turn aus der Klasse Board aufrufen
		 */
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 6; i++) {
				buttons[j][i].setOnAction((ActionEvent e) -> {
					for (int k = 0; k < 2; k++) {
						for (int m = 0; m < 6; m++) {
							if (buttons[k][m] == e.getSource()) {
								board.turn(k, m);
							}

						}

					}

				});
			}

		}

		printButtonValues();
	}

	/**
	 * printButtonValues() Gibt die aktuellen Werte der einzelnen Felder aus
	 */
	public void printButtonValues() {
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 6; i++) {
				if (board.board[j][i] < 10) { // Für gutes Aussehen, wenn die
												// Zahl kleiner 10 ist werden 2
												// Leerzeichen
					buttons[j][i].setText("  " + board.board[j][i]); // vor die
																		// Zahl
																		// gesetzt.
				} else
					buttons[j][i].setText(board.board[j][i] + "");

				if (board.check(j, i)) { // Buttons werden disabled
					buttons[j][i].setDisable(false);
				} else {
					buttons[j][i].setDisable(true);
				}
			}
		}
	}

	/**
	 * printPoints() Gibt die Namen und Punkte der Spieler aus
	 */
	public void printPoints() {
		labelP1.setText(player1.getName() + ": " + player1.getSpielSteineSpieler());
		labelP2.setText(player2.getName() + ": " + player2.getSpielSteineSpieler());
	}

	/**
	 * setInfo() InfoLabel wird beschrieben
	 * 
	 * @param s
	 */
	public void setInfoP1(String s) {
		infoLabelP1.setText(s);
	}
	
	 /** setInfo() InfoLabel wird beschrieben
	 * 
	 * @param s
	 */
	public void setInfoP2(String s) {
		infoLabelP2.setText(s);
	}
	
	public void setInfoWin(String s) {
		infoLabelWin.setText("\n\n"+s);
	}
	
	/**
	 * gameEnde() Wenn Spiel zuende ist, müssen alle Buttons disabled werden und
	 * der Gewinner bzw unentschieden ausgegeben werden
	 */
	public void gameEnde() {

		board.endeSteal();

		printButtonValues();
		printPoints();

		for (Button b : buttons[0]) {
			b.setDisable(true);
		}
		for (Button b : buttons[1]) {
			b.setDisable(true);
		}
		
		endButton.setDisable(true);
		if (board.winningPlayer() == null) {
			setInfoWin("Unentschieden");
		} else
			setInfoWin(board.winningPlayer().getName() + " gewinnt");

	}

	public void vsChoice(Stage primaryStage) {

		primaryStage.setTitle("OWARE");
		BorderPane border = new BorderPane();

		Button vsPlayerB = new Button("vsPlayer");
		vsPlayerB.getStyleClass().add("buttonAuswahl");
		vsPlayerB.setOnAction((ActionEvent e) -> {
			vsPlayer(primaryStage);
		});

		Button vsKIB = new Button("vsKI");
		vsKIB.getStyleClass().add("buttonAuswahl");
		vsKIB.setOnAction((ActionEvent e) -> {
			vsKI(primaryStage);
		});

		GridPane grid = new GridPane();
		grid.add(vsPlayerB, 0, 0);
		grid.add(vsKIB, 1, 0);

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(100);
		grid.setVgap(50);
		// Menübar
		MenuBar menuBar = new MenuBar();

		Menu menuHelp = new Menu("Help");

		MenuItem anleitung = new MenuItem("Anleitung");
		MenuItem credits = new MenuItem("Credits");
		
		anleitung.setOnAction(actionEvent -> anleitung(primaryStage));
		credits.setOnAction(actionEvent -> showCredits(primaryStage));

		
		menuHelp.getItems().addAll(anleitung, credits);

		menuBar.getMenus().addAll(menuHelp);

		// Menübar ende

		border.setCenter(grid);
		border.setTop(menuBar);
		Scene scene = new Scene(border, 1000, 800);
		scene.getStylesheets().add("gui/Skin.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void vsPlayer(Stage primaryStage) {
		primaryStage.setTitle("OWARE");
		BorderPane border = new BorderPane();
		GridPane grid = new GridPane();

		TextField name1Field = new TextField();

		TextField name2Field = new TextField();

		labelP1 = new Label("Name Spieler 1:\n(Max. 20 Zeichen)");
		labelP2 = new Label("Name Spieler 2:\n(Max. 20 Zeichen)");

		
		Button start = new Button("Start Game!");
		start.setOnAction((ActionEvent e) -> {
			if (name1Field.getText().isEmpty() || name1Field.getText().length() > 20) {
				player1 = new Player("Player 1");
			} else {
				player1 = new Player(name1Field.getText());
			}
			if (name2Field.getText().isEmpty() || name1Field.getText().length() > 20) {
				player2 = new Player("Player 2");
			} else {
				player2 = new Player(name2Field.getText());
			}

			board = new Board(player1, player2, this);
			Game(board, primaryStage);
		});
		
		start.getStyleClass().add("buttonAuswahl");
		grid.add(labelP1, 0, 0);
		grid.add(labelP2, 0, 1);
		grid.add(name1Field, 1, 0);
		grid.add(name2Field, 1, 1);
		grid.add(start, 1, 2);
		grid.setHgap(100);
		grid.setVgap(50);
		grid.setAlignment(Pos.CENTER);

		// Menübar
		MenuBar menuBar = new MenuBar();

		
		Menu menuHelp = new Menu("Help");
		Menu menuBack = new Menu("Back");
		
		MenuItem anleitung = new MenuItem("Anleitung");
		MenuItem credits = new MenuItem("Credits");
		MenuItem back = new MenuItem("<= Back");
		
			
		back.setOnAction((ActionEvent e) -> {
			vsChoice(primaryStage);
		});
		
		
		
		anleitung.setOnAction(actionEvent -> anleitung(primaryStage));
		credits.setOnAction(actionEvent -> showCredits(primaryStage));

		menuHelp.getItems().addAll(anleitung, credits);
		menuBack.getItems().addAll(back);
		
		menuBar.getMenus().addAll(menuBack, menuHelp);

		// Menübar ende
		
		border.setCenter(grid);
		border.setTop(menuBar);

		Scene scene = new Scene(border, 1000, 800);
		scene.getStylesheets().add("gui/Skin.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void vsKI(Stage primaryStage) {
		primaryStage.setTitle("OWARE");
		BorderPane border = new BorderPane();
		GridPane grid = new GridPane();
		GridPane gridChoice = new GridPane();

		TextField nameField = new TextField();
		Label labelP = new Label("Name Spieler:\nMax. 20 Zeichen");

		ToggleGroup group = new ToggleGroup();

		RadioButton rb1 = new RadioButton("Easy");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		RadioButton rb2 = new RadioButton("Normal");
		rb2.setToggleGroup(group);
		RadioButton rb3 = new RadioButton("Hard");
		rb3.setToggleGroup(group);

		rb1.setSelected(true);
		
		rb1.getStyleClass().add("buttonKI");
		rb2.getStyleClass().add("buttonKI");
		rb3.getStyleClass().add("buttonKI");
	
		Button start = new Button("Start Game!");
		start.getStyleClass().add("buttonAuswahl");
		start.setOnAction((ActionEvent e) -> {
			if (nameField.getText().isEmpty() || nameField.getText().length() > 20) {
				this.player2 = new Player("Player 2");
			} else {
				this.player2 = new Player(nameField.getText());
			}

			int difficulty;
			if (rb1.isSelected()) {
				difficulty = 0;
			} else if (rb2.isSelected()) {
				difficulty = 1;
			} else
				difficulty = 2;
			KI ki = new KI(difficulty);
			this.player1 = ki;
			board = new Board(ki, player2, this);
			Game(board, primaryStage);
		});
		
		grid.add(labelP, 0, 0);
		grid.add(nameField, 1, 0);
		gridChoice.add(rb1, 0, 0);
		gridChoice.add(rb2, 0, 1);
		gridChoice.add(rb3, 0, 2);
		gridChoice.setVgap(30);
		grid.add(gridChoice, 1, 1);
		grid.add(start, 1, 3);
		grid.setHgap(100);
		grid.setVgap(50);
		grid.setAlignment(Pos.CENTER);

		// Menübar
		MenuBar menuBar = new MenuBar();

		Menu menuHelp = new Menu("Help");
		Menu menuBack = new Menu("Back");
		

		MenuItem anleitung = new MenuItem("Anleitung");
		MenuItem credits = new MenuItem("Credits");
		MenuItem back = new MenuItem("<= Back");
			
		back.setOnAction((ActionEvent e) -> {
			vsChoice(primaryStage);
		});
		
		anleitung.setOnAction(actionEvent -> anleitung(primaryStage));
		credits.setOnAction(actionEvent -> showCredits(primaryStage));


		menuHelp.getItems().addAll(anleitung, credits);
		menuBack.getItems().addAll(back);
		
		menuBar.getMenus().addAll(menuBack, menuHelp);

		// Menübar ende

		border.setCenter(grid);
		border.setTop(menuBar);

		Scene scene = new Scene(border, 1000, 800);
		scene.getStylesheets().add("gui/Skin.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void showCredits(Stage primaryStage) {

		primaryStage.setTitle("Credits");

		GridPane grid = new GridPane();
		Label teamLeader = new Label("Team-Leiter:\nFabian Laier");
		Label designer = new Label("Designer:\nDominique Bost\nMelissa Zindl\nMandy Schmitt");
		Label programmierer = new Label("Programmierer:\nJens Windisch\nJan Spliethoff\nMarkus Cöllen");
		Label tester = new Label("Tester:\nJennifer Brenner\nPatrick Hentschel\nSebastian Schuler");
		Label extras = new Label("Extras:\nPaint-SkillzZz\nby Markus & Jens");

		teamLeader.getStyleClass().add("labelProg");
		designer.getStyleClass().add("labelProg");
		programmierer.getStyleClass().add("labelProg");
		tester.getStyleClass().add("labelProg");
		extras.getStyleClass().add("labelProg");

		grid.add(teamLeader, 0, 0);
		grid.add(designer, 0, 1);
		grid.add(programmierer, 1, 1);
		grid.add(tester, 2, 1);
		grid.add(extras, 0, 2);

		Button back = new Button("Back");
		back.getStyleClass().add("buttonBack");
		back.setOnAction((ActionEvent e) -> {
			vsChoice(primaryStage);
		});

		grid.add(back, 1, 2);
		grid.setHgap(50);
		grid.setVgap(50);
		grid.setAlignment(Pos.CENTER);

		Scene scene = new Scene(grid,1000, 800);
		scene.getStylesheets().add("gui/Skin.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void Game(Board board, Stage primaryStage) {

		// Top
		GridPane gridTop = new GridPane();
		labelP1 = new Label(player1.getName() + ": " + player1.getSpielSteineSpieler());
		labelP2 = new Label(player2.getName() + ": " + player2.getSpielSteineSpieler());
		infoLabelWin = new Label("");
		
		infoLabelWin.getStyleClass().add("labelWin");
		gridTop.add(labelP1, 1, 0);
		gridTop.add(labelP2, 1, 1);
		gridTop.add(infoLabelWin, 1, 3);
		gridTop.setAlignment(Pos.CENTER);

		// Middle
		gridButtons = new GridPane();
		gridButtons.setAlignment(Pos.CENTER);
		buttons = new Button[2][6];
		initializeButtons(buttons);

		// GridBottom
		GridPane gridBottom = new GridPane();
		// gridBottom.setHgap(100);
		
		endButton = new Button("Ende");
		endButton.getStyleClass().add("buttonEnde");

		endButton.setOnAction((ActionEvent e) -> {
			gameEnde();
		});

		gridBottom.add(endButton, 0, 0);
		
		
		//GridInfo
		GridPane infoGrid = new GridPane();
		infoLabelP1 = new Label("\t\tHier kommen die Infos fuer Player1!");
		infoLabelP2 = new Label("\t\tHier kommen die Infos fuer Player2!");
		
		infoGrid.add(infoLabelP1, 0, 0);
		infoGrid.add(infoLabelP2, 0, 1);
		
		gridBottom.add(infoGrid, 3, 0);
		// Menübar
		MenuBar menuBar = new MenuBar();

		Menu menuRestart = new Menu("Restart");
		Menu menuHelp = new Menu("Help");

		MenuItem CompleteMenuItem = new MenuItem("Complete");
		MenuItem anleitung = new MenuItem("Anleitung");
		MenuItem credits = new MenuItem("Credits");

		CompleteMenuItem.setOnAction(actionEvent -> restart(primaryStage));
		anleitung.setOnAction(actionEvent -> anleitung(primaryStage));
		credits.setOnAction(actionEvent -> showCredits(primaryStage));

		menuRestart.getItems().addAll(CompleteMenuItem);
		menuHelp.getItems().addAll(anleitung, credits);

		menuBar.getMenus().addAll(menuRestart);

		// Menübar ende

		// PrimaryStage
		primaryStage.setTitle("Oware");
		BorderPane master = new BorderPane();
		BorderPane king = new BorderPane();
		master.setTop(gridTop);
		master.setCenter(gridButtons);
		master.setBottom(gridBottom);
		king.setCenter(master);
		king.setTop(menuBar);
		Scene scene = new Scene(king, 1000, 800);
		scene.getStylesheets().add("gui/Skin.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		board.checkKITurn(); // Falls der KI als erstes dran ist
	}

	private void anleitung(Stage primaryStage) {
		primaryStage.setTitle("Anleitung");
		BorderPane king = new BorderPane();
		BorderPane master = new BorderPane();
		GridPane grid = new GridPane();
		Label Schritt1 = new Label("\n\n\n1. Wähle deinen Gegenspieler\n"
				+ "2. Gib deinen Namen ein und den deines Gegenspielers \n    bzw. wähle die Schwierigkeit des Gegners.\n"
				+ "3. Jeder Spieler besitzt 6 Mulden mit jeweils 4 Steinen. Gesaet wird gegen den\n    Uhrzeigersinn.Dabei wird jedesmal ein Stein im nachfolgenden Feld abgelegt.\n    Erreicht man die Ausgangsmulde, wird diese übersprungen ohne einen Stein abzulegen.\n"
				+ "4. Wenn in einem Zug zum Abschluss des Aussäens der Spielsteine in der\n    gegnerischen Mulde des letzten gesäten Steins (inklusive des letzten gesäten\n    Steins) zwei oder drei Steine liegen, dann werden diese Steine gefangen.\n    Liegen in den Mulden davor ebenfalls zwei oder drei Steine werden diese ebenfalls\n    gefangen.\n"
				+ "5. Wenn der Gegner keine Steine mehr hat, muss man so säen, dass er wieder Steine bekommt.\n    Ist dies nicht möglich nimmt der Spieler die Steine in sein Gewinndepot auf.\n"
				+ "6. Gewonnen hat der Spieler, der zuerst mehr als 24 Steine hat.");
		Schritt1.getStyleClass().add("labelProg");

		grid.add(Schritt1, 0, 0);

		// Menübar
		/*MenuBar menuBar = new MenuBar();

	
		Menu menuHelp = new Menu("Help");
		Menu menuBack = new Menu("Back");
		
		
		MenuItem anleitung = new MenuItem("Anleitung");
		MenuItem credits = new MenuItem("Credits");
		MenuItem back = new MenuItem("<= Back");
		back.setOnAction((ActionEvent e) -> {
			vsChoice(primaryStage);
		});
		
		
		anleitung.setOnAction(actionEvent -> anleitung(primaryStage));
		credits.setOnAction(actionEvent -> showCredits(primaryStage));

		
		menuHelp.getItems().addAll(anleitung, credits);
		menuBack.getItems().addAll(back);

		menuBar.getMenus().addAll(menuBack,  menuHelp);
*/
		// Menübar ende

		
		//Toolbar
		
		ToolBar tool = new ToolBar();
		Button back = new Button("Back");
		back.setOnAction((ActionEvent e) -> {
			vsChoice(primaryStage);
		});
		back.getStyleClass().add("buttonBack2");
		tool.getItems().addAll(back);
		//Toolbar
		
		grid.setHgap(50);
		grid.setVgap(50);
		grid.setAlignment(Pos.CENTER);
		
		master.setCenter(grid);
		master.setTop(tool);
		king.setTop(master);
		Scene scene = new Scene(king, 1000, 800);
		scene.getStylesheets().add("gui/Skin.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void restart(Stage primaryStage) {
		vsChoice(primaryStage);
	}
}
