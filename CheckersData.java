package project;

import java.util.ArrayList;

/**
 * @author Yu-Pin Liang, COM S 572, Fall 2020
 * 
 *         An object of this class holds data about a game of checkers. It knows
 *         what kind of piece is on each square of the checkerboard. Note that
 *         RED moves "up" the board (i.e. row number decreases) while BLACK
 *         moves "down" the board (i.e. row number increases). Methods are
 *         provided to return lists of available legal moves.
 */

public class CheckersData {

	/*
	 * The following constants represent the possible contents of a square on the
	 * board. The constants RED and BLACK also represent players in the game.
	 */

	static final int EMPTY = 0, RED = 1, RED_KING = 2, BLACK = 3, BLACK_KING = 4;

	int[][] board; // board[r][c] is the contents of row r, column c.

	/**
	 * Constructor. Create the board and set it up for a new game.
	 */
	CheckersData() {
		board = new int[8][8];
		setUpGame();
	}

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";

	/**
	 * make a deep copy
	 */
	public CheckersData(CheckersData old) {
		this.board = old.board;
	}

	/**
	 * public method to calculate howmany piece (normal and king) for each player on this state
	 * 
	 * @param player player color of the player, RED or BLACK
	 */
	//public int howmany(int player) 
	public double howmany(int player) 
	
	{
		int howmany = 0;
		int king =0;
		if (player == 1) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[1].length; j++) {
					if (board[i][j] == 1||board[i][j] == 2) {
						howmany++;
					}
					
					else if (board[i][j] == 2) {
						king++;
					}
				}

			}
		}

		else if (player == 3) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[1].length; j++) {
					if (board[i][j] == 3||board[i][j] == 4) {
						howmany++;
					}
					
					else if (board[i][j] == 4) {
						king++;
					}
				}

			}
		}
		return howmany+1.25*king;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < board.length; i++) {
			int[] row = board[i];
			sb.append(8 - i).append(" ");
			for (int n : row) {
				if (n == 0) {
					sb.append(" ");
				} else if (n == 1) {
					sb.append(ANSI_RED + "R" + ANSI_RESET);
				} else if (n == 2) {
					sb.append(ANSI_RED + "K" + ANSI_RESET);
				} else if (n == 3) {
					sb.append(ANSI_YELLOW + "B" + ANSI_RESET);
				} else if (n == 4) {
					sb.append(ANSI_YELLOW + "K" + ANSI_RESET);
				}
				sb.append(" ");
			}
			sb.append(System.lineSeparator());
		}
		sb.append("  a b c d e f g h");

		return sb.toString();
	}

	/**
	 * Set up the board with checkers in position for the beginning of a game. Note
	 * that checkers can only be found in squares that satisfy row % 2 == col % 2.
	 * At the start of the game, all such squares in the first three rows contain
	 * black squares and all such squares in the last three rows contain red
	 * squares.
	 */
	void setUpGame() {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {

				if ((i == 0 || i == 2) && (j % 2 != 0)) {
					board[i][j] = 3;
				}

				else if ((i == 1) && (j % 2 == 0)) {
					board[i][j] = 3;
				}

				else if (i == 3 || i == 4) {
					board[i][j] = 0;

				}

				else if ((i == 5 || i == 7) && j % 2 == 0) {
					board[i][j] = 1;
				}

				else if (i == 6 && j % 2 != 0) {
					board[i][j] = 1;
				}

			}
		}
	}

	/**
	 * Return the contents of the square in the specified row and column.
	 */
	int pieceAt(int row, int col) {
		return board[row][col];
	}

	/**
	 * Make the specified move. It is assumed that move is non-null and that the
	 * move it represents is legal.
	 * 
	 * @return true if the piece becomes a king, otherwise false
	 */
	boolean makeMove(CheckersMove move) {
		return makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
	}

	/**
	 * Make the move from (fromRow,fromCol) to (toRow,toCol). It is assumed that
	 * this move is legal. If the move is a jump, the jumped piece is removed from
	 * the board. If a piece moves to the last row on the opponent's side of the
	 * board, the piece becomes a king.
	 *
	 * @param fromRow row index of the from square
	 * @param fromCol column index of the from square
	 * @param toRow   row index of the to square
	 * @param toCol   column index of the to square
	 * @return true if the piece becomes a king, otherwise false
	 */

	boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
		// Todo: update the board for the given move.
		// You need to take care of the following situations:
		// 1. move the piece from (fromRow,fromCol) to (toRow,toCol)
		// 2. if this move is a jump, remove the captured piece
		// 3. if the piece moves into the kings row on the opponent's side of the board,
		// crowned it as a king
		CheckersMove jump= new CheckersMove(fromRow, fromCol, toRow, toCol);
		boolean king = false;
		if (jump.isJump()) {
			board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = 0;

			int from = board[fromRow][fromCol];
			board[fromRow][fromCol] = 0;
			board[toRow][toCol] = from;

			if (toRow == 0 && board[fromRow][fromCol] != 4) {
				board[fromRow][fromCol] = 0;
				board[toRow][toCol] = 2;
				king = true;
			}

			else if (toRow == 7 && board[fromRow][fromCol] != 2) {
				board[fromRow][fromCol] = 0;
				board[toRow][toCol] = 4;
				king = true;
			}
		}

		else {

			int from = board[fromRow][fromCol];
			board[fromRow][fromCol] = 0;
			board[toRow][toCol] = from;

			if (toRow == 0 && board[fromRow][fromCol] != 4) {
				board[fromRow][fromCol] = 0;
				board[toRow][toCol] = 2;
				king = true;
			}

			else if (toRow == 7 && board[fromRow][fromCol] != 2) {
				board[fromRow][fromCol] = 0;
				board[toRow][toCol] = 4;
				king = true;
			}

		}

		return king;
	}

	/**
	 * private helper method to check the piece is jumpable or not
	 * 
	 * @param player  player color of the player, RED or BLACK
	 * @param fromRow row index of the from square
	 * @param fromCol column index of the from square
	 * @param midRow  middle row index between the from and to square
	 * @param midCol  middle column index between the from and to square
	 * @param toRow   row index of the to square
	 * @param toCol   column index of the to square
	 * @return true if the piece is jumpable, otherwise false
	 */

	private boolean jumpable(int player, int fromRow, int fromCol, int midRow, int midCol, int toRow, int toCol) {
		if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8)
			return false;
		if (board[toRow][toCol] != 0)
			return false;
		if (player == 1) {
			if (board[fromRow][fromCol] == 1 && toRow > fromRow)
				return false;
			if (board[midRow][midCol] != 3 && board[midRow][midCol] != 4)
				return false;
			return true;
		} else {
			if (board[fromRow][fromCol] == 3 && toRow < fromRow)
				return false;
			if (board[midRow][midCol] != 1 && board[midRow][midCol] != 2)
				return false;
			return true;
		}
	}

	/**
	 * 
	 * private helper method to check the piece is movable or not
	 * 
	 * @param player  player color of the player, RED or BLACK
	 * @param fromRow row index of the from square
	 * @param fromCol column index of the from square
	 * @param toRow   row index of the to square
	 * @param toCol   column index of the to square
	 * @return true if the piece is movable, otherwise false
	 */

	private boolean movable(int player, int fromRow, int fromCol, int toRow, int toCol) {
		if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8)
			return false;

		if (board[toRow][toCol] != 0)
			return false;

		if (player == 1) {
			if (board[fromRow][fromCol] == 1 && toRow > fromRow)
				return false;
			return true;
		} else {
			if (board[fromRow][fromCol] == 3 && toRow < fromRow)
				return false;
			return true;
		}

	}

	/**
	 * Return an array containing all the legal CheckersMoves for the specified
	 * player on the current board. If the player has no legal moves, null is
	 * returned. The value of player should be one of the constants RED or BLACK; if
	 * not, null is returned. If the returned value is non-null, it consists
	 * entirely of jump moves or entirely of regular moves, since if the player can
	 * jump, only jumps are legal moves.
	 *
	 * @param player color of the player, RED or BLACK
	 */
	CheckersMove[] getLegalMoves(int player) {
		if (player != 1 && player != 3)
			return null;

		int king = 0;
		if (player == 1)
			king = 2;
		else
			king = 4;

		ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == player || board[row][col] == king) {
					if (jumpable(player, row, col, row + 1, col + 1, row + 2, col + 2))
						moves.add(new CheckersMove(row, col, row + 2, col + 2));
					if (jumpable(player, row, col, row - 1, col + 1, row - 2, col + 2))
						moves.add(new CheckersMove(row, col, row - 2, col + 2));
					if (jumpable(player, row, col, row + 1, col - 1, row + 2, col - 2))
						moves.add(new CheckersMove(row, col, row + 2, col - 2));
					if (jumpable(player, row, col, row - 1, col - 1, row - 2, col - 2))
						moves.add(new CheckersMove(row, col, row - 2, col - 2));
				}
			}
		}

		if (moves.size() == 0) {
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (board[row][col] == player || board[row][col] == king) {
						if (movable(player, row, col, row + 1, col + 1))
							moves.add(new CheckersMove(row, col, row + 1, col + 1));
						if (movable(player, row, col, row - 1, col + 1))
							moves.add(new CheckersMove(row, col, row - 1, col + 1));
						if (movable(player, row, col, row + 1, col - 1))
							moves.add(new CheckersMove(row, col, row + 1, col - 1));
						if (movable(player, row, col, row - 1, col - 1))
							moves.add(new CheckersMove(row, col, row - 1, col - 1));
					}
				}
			}
		}

		if (moves.size() == 0)
			return null;
		else {
			CheckersMove[] movepath = new CheckersMove[moves.size()];
			for (int i = 0; i < moves.size(); i++)
				movepath[i] = moves.get(i);
			return movepath;
		}
	}

	/**
	 * Return a list of the legal jumps that the specified player can make starting
	 * from the specified row and column. If no such jumps are possible, null is
	 * returned. The logic is similar to the logic of the getLegalMoves() method.
	 *
	 * @param player The player of the current jump, either RED or BLACK.
	 * @param row    row index of the start square.
	 * @param col    col index of the start square.
	 */
	CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {

		if (player != 1 && player != 3)
			return null;
		int king = 0;
		if (player == 1)
			king = 2;
		else
			king = 4;

		ArrayList<CheckersMove> jump = new ArrayList<CheckersMove>();

		if (board[row][col] == player || board[row][col] == king) {
			if (jumpable(player, row, col, row + 1, col + 1, row + 2, col + 2))
				jump.add(new CheckersMove(row, col, row + 2, col + 2));
			if (jumpable(player, row, col, row - 1, col + 1, row - 2, col + 2))
				jump.add(new CheckersMove(row, col, row - 2, col + 2));
			if (jumpable(player, row, col, row + 1, col - 1, row + 2, col - 2))
				jump.add(new CheckersMove(row, col, row + 2, col - 2));
			if (jumpable(player, row, col, row - 1, col - 1, row - 2, col - 2))
				jump.add(new CheckersMove(row, col, row - 2, col - 2));
		}

		if (jump.size() == 0)
			return null;
		else {
			CheckersMove[] jumppath = new CheckersMove[jump.size()];
			for (int i = 0; i < jump.size(); i++)
				jumppath[i] = jump.get(i);
			return jumppath;
		}

	}

}