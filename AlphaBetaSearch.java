package project;


/**
 * @author Yu-Pin Liang, COM S 572, Fall 2020
 *
 */


public class AlphaBetaSearch {
	
	private CheckersData board;
	// An instance of this class will be created in the Checkers.Board
	// It would be better to keep the default constructor.

	/**
	 * @param board
	 */
	public void setCheckersData(CheckersData board) {
		this.board = board;
	}


	/**
	 * You need to implement the Alpha-Beta pruning algorithm here to find the best
	 * move at current stage. The input parameter legalMoves contains all the
	 * possible moves. It contains four integers: fromRow, fromCol, toRow, toCol
	 * which represents a move from (fromRow, fromCol) to (toRow, toCol). It also
	 * provides a utility method `isJump` to see whether this move is a jump or a
	 * simple move.
	 *
	 * pick a good one to move
	 *
	 * @param legalMoves All the legal moves for the agent at current step.
	 */
	public CheckersMove makeMove(CheckersMove[] legalMoves) {
		CheckersData future = new CheckersData();
		CheckersData current = new CheckersData();
		CheckersMove result = null;

		double score = -999999;

		for (int i = 0; i < legalMoves.length; i++) {
			future = current;
			future.makeMove(legalMoves[i]);
			double value = maxValue(future, 1, -99999, 99999, 10);

			if (value > score) {
				result = legalMoves[i];
				score = value;
				future = null;
			}

			future = null;
		}
		return result;
	}
	
	
	
	
	public double maxValue(CheckersData state, int player, double alpha, double beta, int depth) {
		CheckersData future = new CheckersData();
		CheckersData current = new CheckersData();
		if (depth == 0) {
			return (future.howmany(3) - future.howmany(1));
		}
		double value = -99999;
		int legalLength = state.getLegalMoves(3).length;

		for (int i = 0; i < legalLength; i++) {
			future = current;
			future.makeMove(state.getLegalMoves(3)[i]);
			
			value = minValue(future, player, alpha, beta, depth - 1);
			if (value >= beta)
				return beta;
			if (value >= alpha)
				alpha = value;
			future = null;

		}
		return alpha;
	}

	public double minValue(CheckersData state, int player, double alpha, double beta, int depth) {
		CheckersData future = new CheckersData();
		CheckersData current = new CheckersData();
		if (depth == 0) {
			return (future.howmany(3) - future.howmany(1));
		}

		double value = 99999;
		int legalLength = state.getLegalMoves(1).length;
		for (int i = 0; i < legalLength; i++) {
			future = current;
			future.makeMove(state.getLegalMoves(1)[i]);

			
			value = maxValue(future, player, alpha, beta, depth - 1);
			if (value <= alpha)
				return alpha;
			if (value < beta)
				beta = value;
			future = null;
		}
		return beta;
	}
}
