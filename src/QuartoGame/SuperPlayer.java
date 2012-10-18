package QuartoGame;

import java.util.Scanner;

public class SuperPlayer extends AbstractPlayer {

    public SuperPlayer(PlayerType pType, int id) {
        super(pType, id);
    }

    public SuperPlayer(PlayerType pType, int id, int miniMaxDepth) {
        super(pType, id, miniMaxDepth);
    }

    @Override
    protected Piece pickPiece(Board board, Scanner scanner, boolean doMiniMax) {

        switch (playerType) {
            case RANDOM:
                return randomPickPiece(board, scanner, doMiniMax);
            case NOVICE:
                return novicePickPiece(board, scanner, doMiniMax);
            case MINIMAXD:
                return minmaxPickPiece(board, scanner, doMiniMax);
            case HUMAN:
                return humanPickPiece(board, scanner, doMiniMax);
        }
        return null;
    }

    @Override
    protected void placePiece(Board board, Piece piece, Scanner scanner,
                              boolean doMiniMax, boolean wrongPick) {

        switch (playerType) {
            case RANDOM:
                randomPlacePiece(board, piece, scanner, doMiniMax, wrongPick);
                break;
            case NOVICE:
                novicePlacePiece(board, piece, scanner, doMiniMax, wrongPick);
                break;
            case MINIMAXD:
                minmaxPlacePiece(board, piece, scanner, doMiniMax, wrongPick);
                break;
            case HUMAN:
                humanPlacePiece(board, piece, scanner, doMiniMax, wrongPick);
                break;
        }

    }


    private Piece randomPickPiece(Board board, Scanner scanner, boolean doMiniMax){
        return board.getRemainingPieces().get((int) Math.floor(Math.random() * board.getRemainingPieces().size()));
    }

    private Piece novicePickPiece(Board board, Scanner scanner, boolean doMiniMax){
        SuperNode root = new SuperNode(board, true, false);
        return root.bestPiece(1);
    }

    private Piece minmaxPickPiece(Board board, Scanner scanner, boolean doMiniMax){
        SuperNode root = new SuperNode(board, true, false);
        return root.bestPiece(miniMaxDepth);
    }

    private Piece humanPickPiece(Board board, Scanner scanner, boolean doMiniMax){
        return null;
    }


    private void randomPlacePiece(Board board, Piece piece, Scanner scanner,
                                  boolean doMiniMax, boolean wrongPick) {
        int place = board.getFreePlaces().get((int) Math.floor(Math.random() * board.getFreePlaces().size()));
        board.placePiece(place, piece);
    }

    private void novicePlacePiece(Board board, Piece piece, Scanner scanner,
                                  boolean doMiniMax, boolean wrongPick) {
        SuperNode root = new SuperNode(board, true, true);
        int place = root.bestPlace(1);
        board.placePiece(place, piece);
    }

    private void minmaxPlacePiece(Board board, Piece piece, Scanner scanner,
                                  boolean doMiniMax, boolean wrongPick) {
        SuperNode root = new SuperNode(board, true, true);
        int place = root.bestPlace(miniMaxDepth);
        board.placePiece(place, piece);
    }

    private void humanPlacePiece(Board board, Piece piece, Scanner scanner,
                                 boolean doMiniMax, boolean wrongPick) {

    }
}
