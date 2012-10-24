package QuartoGame;

import java.util.Scanner;

public class SuperPlayer extends AbstractPlayer {

	private boolean heuristic;
	
    public SuperPlayer(PlayerType pType, int id) {
        super(pType, id);
    }

    public SuperPlayer(PlayerType pType, int id, int miniMaxDepth, boolean heuristic) {
        super(pType, id, miniMaxDepth);
        this.heuristic = heuristic;
    }

    @Override
    protected Piece pickPiece(Board board, Scanner scanner, boolean doMiniMax) {

        switch (playerType) {
            case RANDOM:
                return randomPickPiece(board);
            case NOVICE:
                return novicePickPiece(board);
            case MINIMAXD:
                if(doMiniMax) return minmaxPickPiece(board);
                else return novicePickPiece(board);
            case HUMAN:
                return humanPickPiece(board, scanner);
        }
        return null;
    }

    @Override
    protected void placePiece(Board board, Piece piece, Scanner scanner,
                              boolean doMiniMax, boolean wrongPick) {

        switch (playerType) {
            case RANDOM:
                randomPlacePiece(board, piece);
                break;
            case NOVICE:
                novicePlacePiece(board, piece);
                break;
            case MINIMAXD:
            	if(doMiniMax) minmaxPlacePiece(board, piece);
            	else novicePlacePiece(board, piece);
                break;
            case HUMAN:
                humanPlacePiece(board, piece, scanner, wrongPick);
                break;
        }

    }


    private Piece randomPickPiece(Board board){
    	Piece piece = board.getRemainingPieces().get((int) Math.floor(Math.random() * board.getRemainingPieces().size()));
    	board.getRemainingPieces().remove(piece);
        return piece;
    }

    private Piece novicePickPiece(Board board){
        SuperNode root = new SuperNode(board, true, false, -1);
        Piece piece = root.bestPiece(1, heuristic);
        board.getRemainingPieces().remove(piece);
        return piece;
    }

    private Piece minmaxPickPiece(Board board){
        SuperNode root = new SuperNode(board, true, false, -1);
        Piece piece = root.bestPiece(miniMaxDepth, heuristic);
        board.getRemainingPieces().remove(piece);
        return piece;
    }

    private Piece humanPickPiece(Board board, Scanner scanner){
        return null;
    }


    private void randomPlacePiece(Board board, Piece piece) {
        int place = board.getFreePlaces().get((int) Math.floor(Math.random() * board.getFreePlaces().size()));
        board.placePiece(place, piece);
    }

    private void novicePlacePiece(Board board, Piece piece) {
        SuperNode root = new SuperNode(board, true, true, -1);
        int place = root.bestPlace(1, heuristic);
        board.placePiece(place, piece);
    }

    private void minmaxPlacePiece(Board board, Piece piece) {
        SuperNode root = new SuperNode(board, true, true, -1);
        int place = root.bestPlace(miniMaxDepth, heuristic);
        board.placePiece(place, piece);
    }

    private void humanPlacePiece(Board board, Piece piece, Scanner scanner,
                                 boolean doMiniMax) {

    }
}