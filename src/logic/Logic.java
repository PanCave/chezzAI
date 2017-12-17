package logic;

import java.util.Vector;

public class Logic {


	public static Vector<String> getAllPossibleMoves(String[] board, boolean isWhiteMove) {
		Vector<String> moves = new Vector<String>();
		byte[][] positionBoard = {
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0}};

		for (String piece : board) {
			int[] piecePosition = piecePositionStringToInt(piece);
			if(piece.charAt(1) == 'w') {
				positionBoard[piecePosition[0]][piecePosition[1]] = 1;
			} else {
				positionBoard[piecePosition[0]][piecePosition[1]] = 2;
			}
		}

		for(String piece : board) {
			if(piece.charAt(1) == (isWhiteMove ? 'w' : 'b')) {
				System.out.println("Checking moves for: "+piece);
				int[] piecePosition = piecePositionStringToInt(piece);
				String moveString = piecePositionIntToString(piecePosition)+" nach ";

				switch(piece.charAt(0)) {
				case 'p':
					int direction;
					int startRow;
					moveString = "Bauer " + moveString;
					if(isWhiteMove) {
						direction = 1;
						startRow = 6;
					} else {
						direction = -1;
						startRow = 1;
					}

					if(piecePosition[1] + 1*direction < 8 && piecePosition[1] + 1*direction > 0 && positionBoard[piecePosition[0]][piecePosition[1] + 1*direction] == 0) {
						moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0],piecePosition[1] + 1*direction}));
						if(piecePosition[1] == startRow) {
							if(positionBoard[piecePosition[0]][piecePosition[1] + 2*direction] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0],piecePosition[1] + 2*direction}));
							}
						}
					}
					if(piecePosition[1] + 1*direction < 8 && piecePosition[1] + 1*direction > 0 && piecePosition[0] - 1 < 8 && piecePosition[0] - 1 > 0 && positionBoard[piecePosition[0] - 1][piecePosition[1] + 1*direction] == (isWhiteMove ? 2 : 1)) {
						moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] - 1,piecePosition[1] + 1*direction}));
					}
					if(piecePosition[1] + 1*direction < 8 && piecePosition[1] + 1*direction > 0 &&
							piecePosition[0] + 1 < 8 && piecePosition[0] + 1 > 0 &&
							positionBoard[piecePosition[0] + 1][piecePosition[1] + 1*direction] == (isWhiteMove ? 2 : 1)) {
						moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + 1,piecePosition[1] + 1*direction}));
					}
					break;
	/*			case 'r':
					for { // vorwärts und rückwärts || links oder rechts   1    -1
						for {
							// SPaltenbewegung
						}

						for {
							// Reihenbewegung
						}
					}
				case 'b':
					for { // vorwärts und rückwärts || links oder rechts   1    -1
						for {

						}

						for {

						}
					}
				case 'q':
				case 'k':
					for {
						for {
							for {

							}
						}
					}
				case 'n':

	*/
				}

			}
		}
		for (String string : moves) {
			System.out.println(string);
		}
		return moves;
	}

	public static String piecePositionIntToString(int[] piecePosition) {
		return ""+(char)(piecePosition[0]+'a')+(piecePosition[1] + 1);
	}

	public static int[] piecePositionStringToInt(String piece) {
		int[] piecePosition = new int[2];
		piecePosition[0] = piece.charAt(2) - 'a';
		piecePosition[1] = Integer.parseInt(piece.substring(3, 4)) - 1;
		return piecePosition;
	};

}
