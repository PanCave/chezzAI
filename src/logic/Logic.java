package logic;

public class Logic {
	static byte[][] positionBoard = new byte[8][8];
	public static String[] getAllPossibleMoves(String[] board) {
		for(int i = 0; i<= 7; i++) {
			for(int a = 0; a<=7; a++) {
				for(int z = 1; z<= board.length; z++){

					if((int)board[z].charAt(3) == 49 + a && (board[z].charAt(2) - 'a')-a == 0 ){
						if(board[z].charAt(1) == 'b') {
							positionBoard[i][a] = 1;
							break;
						} else {
							positionBoard[i][a] = 2;
							break;
						}

						} else {

							positionBoard[i][a] = 0;
							break;
						}
				}
				System.out.println(positionBoard[i][a]);

			}

		}
	}
}
