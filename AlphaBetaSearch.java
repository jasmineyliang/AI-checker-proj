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
		CheckersMove result = null;
		double score = -999999;

		for (int i = 0; i < legalMoves.length; i++) {
			CheckersData future = new CheckersData(this.board);
			future.makeMove(legalMoves[i]);
			double value = maxValue(future, 1, -99999, 99999, 10);

			if (value > score) {
				result = legalMoves[i];
				score = value;
			}
		}
		return result;
	}




	public double maxValue(CheckersData state, int player, double alpha, double beta, int depth) {
		if (depth == 0) {
			return (state.howmany(3) - state.howmany(1));
		}
		CheckersMove[] moves = state.getLegalMoves(3);
		if (moves == null) {
			return (state.howmany(3) - state.howmany(1));
		}

		for (int i = 0; i < moves.length; i++) {
			CheckersData future = new CheckersData(state);
			future.makeMove(moves[i]);

			double value = minValue(future, player, alpha, beta, depth - 1);
			if (value >= beta)
				return beta;
			if (value >= alpha)
				alpha = value;
		}
		return alpha;
	}

	public double minValue(CheckersData state, int player, double alpha, double beta, int depth) {
		if (depth == 0) {
			return (state.howmany(3) - state.howmany(1));
		}
		CheckersMove[] moves = state.getLegalMoves(1);
		if (moves == null) {
			return (state.howmany(3) - state.howmany(1));
		}
		for (int i = 0; i < moves.length; i++) {
			CheckersData future = new CheckersData(state);
			future.makeMove(moves[i]);

			double value = maxValue(future, player, alpha, beta, depth - 1);
			if (value <= alpha)
				return alpha;
			if (value < beta)
				beta = value;
		}
		return beta;
	}
}
