package ai;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import logic.Logic;

public class ChezzAI {

	static String[] board = {
			"rba8","nbb8","bbc8","qbd8","kbe8","bbf8","nbg8","rbh8",
			"pba7","pbb7","pbc7","pbd7","pbe7","pbf7","pbg7","pbh7",
			"pwa2","pwb2","pwc2","pwd2","pwe2","pwf2","pwg2","pwh2",
			"rwa1","nwb1","bwc1","qwd1","kwe1","bwf1","nwg1","rwh1",
			};
	static String[] commentary;

	static Scanner scanner;

	static Random random;

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		//String[] moves = Logic.getAllPossibleMoves(board);
		scanner = new Scanner(System.in);
		random = new Random();

		loadCommentary("res/commentary_de.txt");

		/*System.out.println("Hallo,\ndies ist ein simpler Schachcomputer. Möchtest du ein Spiel spielen?");
		switch(scanner.nextLine().toLowerCase()) {
		case "ja":
		case "j":
		case "yes":
		case "y": System.out.println("Sehr schön. Hier ist das Spielfeld. Du bist weiß und beginnst."); startGame(); break;
		case "nein":
		case "no":
		case "n": System.out.println("Okay, vielleicht beim nächsten Mal. Tschüss!"); System.exit(0); break;
		
		}*/
		
		startGame();
	}

	static void startGame() throws InterruptedException {
		boolean winner = false;
		while(!winner) {
			// User am Zug
			printBoard();
			System.out.print("Bitte gib deinen Zug ein: ");
			boolean correctMove = false;
			String[] move = null;
			while(!correctMove) {
				correctMove = true;
				move = scanner.nextLine().split(" ");
				if(move.length != 4) {
					System.err.println("[Error] Falsches Zugformat: <Figur> <Startposition> nach <Endposition>");
					correctMove = false;
				}
				if(!processMove(move, true)) {
					System.out.println("[Error] Du hast keinen gültigen Zug eingegeben");
					correctMove = false;
				}
			}
			
			//Thread.sleep(2000);

			// Computer am Zug
			printBoard();
			comment();
			//moves
			Vector<String> moves = Logic.getAllPossibleMoves(board, false);
			//zufällig einen Zug daraus wählen
			String[] m = moves.get(random.nextInt(moves.size())).split(" ");
			/*for (String string : board) {
				System.out.println(string);
			}*/
			processMove(m, false);
			//processMove(move.split(" "));
			//winner = true;
		}
	}

	static void comment() {
		String[] lines = commentary[random.nextInt(100) % commentary.length].split("\n");
		for (String line : lines) {
			System.out.println(line);
		}
	}

	static void loadCommentary(String filename) throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader(filename));
		StringBuilder sb = new StringBuilder();
		while(in.hasNext()) {
			sb.append(in.nextLine());
			sb.append("___");
		}
		in.close();
		commentary = sb.toString().split("___");
	}
	
	private static void printBoardString() {
		String string = "";
		for(int i=0; i<board.length; i++) {
			string += board[i] + " ";
		}
		System.out.println(string);
	}
	
	private static void removePiece(String piece) {
		String[] newBoard = new String[board.length - 1];
		boolean pieceFound = false;
		for(int i = 0; i < board.length; i++) {
			if(board[i].equals(piece)) {
				pieceFound = true;
				continue;
			}
			if(i == (board.length - 1) && !pieceFound) {
				break;
			}
			if(!board[i].equals(piece)) {
				if(pieceFound) {
					newBoard[i - 1] = board[i];					
				} else {
					newBoard[i] = board[i];	
				}
			}
		}
		if(pieceFound) {
			board = newBoard;
		}
	}

	static boolean processMove(String[] move, boolean isWhiteMove) {
		if(move.length == 4) {
			String p = " ";
			switch(move[0].toLowerCase()) {
			case "bauer": p = "p"; break;
			case "turm": p = "r"; break;
			case "läufer": p = "b"; break;
			case "springer": p = "n"; break;
			case "dame": p = "q"; break;
			case "könig": p = "k"; break;
			}
			boolean successfulMove = false;
			String color = isWhiteMove ? "w" : "b";
			String antiColor = isWhiteMove ? "b" : "w";
			System.out.println("####");
			System.out.println(p+color+move[1]);
			System.out.println(p+color+move[3]);
			System.out.println("####");
			for(int pieceIndex = 0; pieceIndex < board.length; pieceIndex++) {
				if(board[pieceIndex].equals(p+color+move[1])) {
					board[pieceIndex] = p+color+move[3];
					successfulMove = true;
					removePiece(p+antiColor+move[3]);
					break;
				}
			}
			return successfulMove;
		}
		return false;
	}

	static void printBoard() {
		char[][] charBoard = convertBoardToCharArray();
		String output;
		output = "  #################################################\n";
		for(int row = 0; row < 8; row++) {
			for(int smallRow = 0; smallRow < 4; smallRow++) {
				if(smallRow == 1) {
					output += 8 - row+" #";
				} else {
					output += "  #";
				}
				for(int column = 0; column < 8; column++) {
					if(smallRow == 1) {
						output += "  "+charBoard[7 - row][column]+"  #";
					} else if(smallRow == 3) {
						output += "######";
					}else {
						output += "     #";
					}
				}
				output += "\n";
			}
		}
		output += "     A     B     C     D     E     F     G     H   \n";
		System.out.println(output);
	}

	private static char[][] convertBoardToCharArray() {
		char[][] charBoard = {
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '}};
		printBoardString();
		for (String piece : board) {
			char p = piece.charAt(0);
			if(piece.charAt(1) == 'w') {
				System.out.println("Test:");
				System.out.println(piece);
				p = Character.toUpperCase(p);
			}
			char c = piece.charAt(2);
			int column = c - 'a';
			int row = Integer.parseInt(piece.substring(3, 4)) - 1;
			charBoard[row][column]= p;
		}
		System.out.println("___________________________________");
		for(int i = 0; i < charBoard.length; i++) {
			for(int j = 0; j < charBoard[i].length; j++) {
				System.out.print(charBoard[7-i][j]);
			}
			System.out.println();
		}
		System.out.println("___________________________________");
		return charBoard;
	}
}