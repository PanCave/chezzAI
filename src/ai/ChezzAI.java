package ai;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

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
	
	public static void main(String[] args) throws FileNotFoundException {
		//String[] moves = Logic.getAllPossibleMoves(initialBoard);
		scanner = new Scanner(System.in);
		random = new Random();
		
		loadCommentary("res/commentary_de.txt");
		
		System.out.println("Hallo,\ndies ist ein simpler Schachcomputer. Möchtest du ein Spiel spielen?");
		switch(scanner.nextLine().toLowerCase()) {
		case "ja":
		case "j":
		case "yes":
		case "y": System.out.println("Sehr schön. Hier ist das Spielfeld. Du bist weiß und beginnst."); startGame(); break;
		case "nein":
		case "no":
		case "n": System.out.println("Okay, vielleicht beim nächsten Mal. Tschüss!"); System.exit(0); break;
		}
	}
	
	static void startGame() {
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
				if(!processMove(move)) {
					System.out.println("[Error] Du hast keinen gültigen Zug eingegeben");
					correctMove = false;
				}
			}
			
			// Computer am Zug
			printBoard();
			comment();
			winner = true;
		}		
	}
	
	static void comment() {
		System.out.println(commentary[random.nextInt(100) % commentary.length]);
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
	
	static boolean processMove(String[] move) {
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
		for(int i = 0; i < board.length; i++) {
			if(board[i].equals((p+"w"+move[1]))) {
				board[i] = p+"w"+move[3];
				successfulMove = true;
			}
		}
		return successfulMove;
	}
	
	static void printBoard() {
		char[][] charBoard = convertBoardToCharArray();
		String output;
		output = "#################################################\n";
		for(int row = 0; row < 8; row++) {
			for(int smallRow = 0; smallRow < 4; smallRow++) {
			output += "#";
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
		for (String piece : board) {
			char p = piece.charAt(0);
			if(piece.charAt(1) == 'w') {
				p = Character.toUpperCase(p);
			}
			char c = piece.charAt(2);
			int column = c - 'a';
			int row = Integer.parseInt(piece.substring(3, 4)) - 1;
			charBoard[row][column]= p;
		}
		
		return charBoard;
	}

}
