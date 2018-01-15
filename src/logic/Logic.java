package logic;

import java.util.Vector;

public class Logic {


	public static Vector<String> getAllPossibleMoves(String[] board, boolean isWhiteMove) {
		Vector<String> moves = new Vector<String>();
		byte[][] positionBoard = createPositionBoard(board);

		for(String piece : board) {
			if(piece.charAt(1) == (isWhiteMove ? 'w' : 'b')) {
				int[] piecePosition = piecePositionStringToInt(piece);
				String moveString = piecePositionIntToString(piecePosition)+" nach ";
				
				int[][] possibleMoves;

				switch(piece.charAt(0)) {
				case 'p':
					int direction;
					int startRow;
					moveString = "Bauer " + moveString;
					if(isWhiteMove) {
						direction = 1;
						startRow = 1;
					} else {
						direction = -1;
						startRow = 6;
					}
					
					// vorwärts laufen
					if(isValidPosition(piecePosition, 0, 1*direction) && positionBoard[piecePosition[0]][piecePosition[1] + 1*direction] == 0) {
						moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0],piecePosition[1] + 1*direction}));
						
						// doppelt nach vorne laufen, wenn noch nicht bewegt
						if(piecePosition[1] == startRow) {
							if(positionBoard[piecePosition[0]][piecePosition[1] + 2*direction] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0],piecePosition[1] + 2*direction}));
							}
						}
					}
					
					// schräg schlagen
					if(isValidPosition(piecePosition, -1, 1*direction) && positionBoard[piecePosition[0] - 1][piecePosition[1] + 1*direction] == (isWhiteMove ? 2 : 1)) {
						moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] - 1,piecePosition[1] + 1*direction}));
					}
					// schräg schlagen
					if(isValidPosition(piecePosition, 1, 1*direction) && positionBoard[piecePosition[0] + 1][piecePosition[1] + 1*direction] == (isWhiteMove ? 2 : 1)) {
						moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + 1,piecePosition[1] + 1*direction}));
					}
					break;
				case 'r':
					moveString = "Turm " + moveString;
					for (int i = -1; i < 2; i += 2) { // vorwärts und rückwärts || links oder rechts   1    -1
						for (int j = 1; j < 7; j++) {
							// Spaltenbewegung
							if(isValidPosition(piecePosition, j*i, 0) && positionBoard[piecePosition[0] + j*i][piecePosition[1]] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + j*i,piecePosition[1]}));
								continue;
							} else if (isValidPosition(piecePosition, j*i, 0) && positionBoard[piecePosition[0] + j*i][piecePosition[1]] == (isWhiteMove ? 2 : 1)) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + j*i,piecePosition[1]}));
								break;
							} else if (isValidPosition(piecePosition, j*i, 0) && positionBoard[piecePosition[0] + j*i][piecePosition[1]] == (isWhiteMove ? 1 : 2)) {
								break;
							}
						}

						for (int k = 0; k < 8; k++) {
							// Reihenbewegung
							if(isValidPosition(piecePosition, 0, k*i) && positionBoard[piecePosition[0]][piecePosition[1] + k*i] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0],piecePosition[1] + k*i}));
								continue;
							} else if (isValidPosition(piecePosition, 0, k*i) && positionBoard[piecePosition[0]][piecePosition[1] + k*i] == (isWhiteMove ? 2 : 1)) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0],piecePosition[1] + k*i}));
								break;
							} else if (isValidPosition(piecePosition, 0, k*i) && positionBoard[piecePosition[0]][piecePosition[1] + k*i] == (isWhiteMove ? 1 : 2)) {
								break;
							}
						}
					}
					break;
				case 'b':
					moveString = "Läufer " + moveString;
					for (int i = -1; i < 2; i += 2) { // vorwärts und rückwärts || links oder rechts   1    -1
						for (int j = 1; j < 7; j++) {
							// links oben oder rechts unten
							if(isValidPosition(piecePosition, j*i, j*i) && positionBoard[piecePosition[0] + j*i][piecePosition[1]+ j*i] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + j*i,piecePosition[1]+ j*i}));
								continue;
							} else if (isValidPosition(piecePosition, j*i, j*i) && positionBoard[piecePosition[0] + j*i][piecePosition[1]+ j*i] == (isWhiteMove ? 2 : 1)) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + j*i,piecePosition[1]+ j*i}));
								break;
							} else if (isValidPosition(piecePosition, j*i, j*i) && positionBoard[piecePosition[0] + j*i][piecePosition[1]+ j*i] == (isWhiteMove ? 1 : 2)) {
								break;
							}
						}

						for (int k = 0; k < 8; k++) {
							// rechts oben oder links unten
							if(isValidPosition(piecePosition, -k*i, k*i) && positionBoard[piecePosition[0]-k*i][piecePosition[1] + k*i] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0]-k*i,piecePosition[1] + k*i}));
								continue;
							} else if (isValidPosition(piecePosition, -k*i, k*i) && positionBoard[piecePosition[0]-k*i][piecePosition[1] + k*i] == (isWhiteMove ? 2 : 1)) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0]-k*i,piecePosition[1] + k*i}));
								break;
							} else if (isValidPosition(piecePosition, -k*i, k*i) && positionBoard[piecePosition[0]-k*i][piecePosition[1] + k*i] == (isWhiteMove ? 1 : 2)) {
								break;
							}
						}
					}
					break;
				case 'q':
					moveString = "Dame " + moveString;
					
					// Gerade
					for (int i = -1; i < 2; i += 2) { // vorwärts und rückwärts || links oder rechts   1    -1
						for (int j = 1; j < 7; j++) {
							// Spaltenbewegung
							if(isValidPosition(piecePosition, j*i, 0) && positionBoard[piecePosition[0] + j*i][piecePosition[1]] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + j*i,piecePosition[1]}));
								continue;
							} else if (isValidPosition(piecePosition, j*i, 0) && positionBoard[piecePosition[0] + j*i][piecePosition[1]] == (isWhiteMove ? 2 : 1)) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + j*i,piecePosition[1]}));
								break;
							} else if (isValidPosition(piecePosition, j*i, 0) && positionBoard[piecePosition[0] + j*i][piecePosition[1]] == (isWhiteMove ? 1 : 2)) {
								break;
							}
						}

						for (int k = 0; k < 8; k++) {
							// Reihenbewegung
							if(isValidPosition(piecePosition, 0, k*i) && positionBoard[piecePosition[0]][piecePosition[1] + k*i] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0],piecePosition[1] + k*i}));
								continue;
							} else if (isValidPosition(piecePosition, 0, k*i) && positionBoard[piecePosition[0]][piecePosition[1] + k*i] == (isWhiteMove ? 2 : 1)) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0],piecePosition[1] + k*i}));
								break;
							} else if (isValidPosition(piecePosition, 0, k*i) && positionBoard[piecePosition[0]][piecePosition[1] + k*i] == (isWhiteMove ? 1 : 2)) {
								break;
							}
						}
					}
					
					// Diagonal
					for (int i = -1; i < 2; i += 2) { // vorwärts und rückwärts || links oder rechts   1    -1
						for (int j = 1; j < 7; j++) {
							// links oben oder rechts unten
							if(isValidPosition(piecePosition, j*i, j*i) && positionBoard[piecePosition[0] + j*i][piecePosition[1]+ j*i] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + j*i,piecePosition[1]+ j*i}));
								continue;
							} else if (isValidPosition(piecePosition, j*i, j*i) && positionBoard[piecePosition[0] + j*i][piecePosition[1]+ j*i] == (isWhiteMove ? 2 : 1)) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + j*i,piecePosition[1]+ j*i}));
								break;
							} else if (isValidPosition(piecePosition, j*i, j*i) && positionBoard[piecePosition[0] + j*i][piecePosition[1]+ j*i] == (isWhiteMove ? 1 : 2)) {
								break;
							}
						}

						for (int k = 0; k < 8; k++) {
							// rechts oben oder links unten
							if(isValidPosition(piecePosition, -k*i, k*i) && positionBoard[piecePosition[0]-k*i][piecePosition[1] + k*i] == 0) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0]-k*i,piecePosition[1] + k*i}));
								continue;
							} else if (isValidPosition(piecePosition, -k*i, k*i) && positionBoard[piecePosition[0]-k*i][piecePosition[1] + k*i] == (isWhiteMove ? 2 : 1)) {
								moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0]-k*i,piecePosition[1] + k*i}));
								break;
							} else if (isValidPosition(piecePosition, -k*i, k*i) && positionBoard[piecePosition[0]-k*i][piecePosition[1] + k*i] == (isWhiteMove ? 1 : 2)) {
								break;
							}
						}
					}
					break;
				case 'k':
					moveString = "König " + moveString;
					
					possibleMoves = new int[][]{{-1,-1},{-1,0},{-1,1},{1,-1},{1,0},{1,1},{0,-1},{0,1}};
					for (int[] move : possibleMoves) {
						if(isValidPosition(piecePosition, move[0], move[1]) && positionBoard[piecePosition[0] + move[0]][piecePosition[0] + move[1]] != (isWhiteMove ? 1 : 2)) {
							moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + move[0],piecePosition[1] + move[1]}));
						}
					}
					break;
				case 'n':
					moveString = "Springer " + moveString;
					possibleMoves = new int[][]{{-1,2},{-1,-2},{1,2},{1,-2},{2,1},{2,-1},{-2,-1},{-2,1}};
					for (int[] move : possibleMoves) {
						if(isValidPosition(piecePosition, move[0], move[1]) && positionBoard[piecePosition[0] + move[0]][piecePosition[0] + move[1]] != (isWhiteMove ? 1 : 2)) {
							moves.add(moveString + piecePositionIntToString(new int[]{piecePosition[0] + move[0],piecePosition[1] + move[1]}));
						}
					}
					break;
				}

			}
		}
		for (String string : moves) {
			System.out.println(string);
		}
		return moves;
	}
	
	private static boolean isValidPosition(int[] position, int offsetX, int offsetY) {
		if(position.length != 2) {
			return false;
		} else {
			if(position[0] + offsetX >= 0 && position[0] + offsetX <= 7 && position[1] + offsetY >= 0 && position[1] + offsetY <= 7) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static boolean isValidPosition(int[] position, int[] offset) {
		if(position.length != 2 || offset.length != 2) {
			return false;
		} else {
			if(position[0] + offset[0] >= 0 && position[0] + offset[0] <= 7 && position[1] + offset[1] >= 0 && position[1] + offset[1] <= 7) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static boolean isValidPosition(int[] position) {
		if(position.length != 2) {
			return false;
		} else {
			if(position[0] >= 0 && position[0] <= 7 && position[1] >= 0 && position[1] <= 7) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static byte[][] createPositionBoard(String[] board) {
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
		
		return positionBoard;
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
