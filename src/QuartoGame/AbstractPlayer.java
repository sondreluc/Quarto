package QuartoGame;

import java.util.Scanner;

public abstract class AbstractPlayer {
    public enum PlayerType{RANDOM, NOVICE, HUMAN, MINIMAXD}
    private long timeSpent;
    protected PlayerType playerType;
    protected int playerID;
    protected int miniMaxDepth;
    private Piece bestPick;


    public AbstractPlayer(PlayerType pType, int id){
        this.playerType = pType;
        this.playerID = id;
    }
    public AbstractPlayer(PlayerType pType, int id, int miniMaxDepth){
        this.playerType = pType;
        this.playerID = id;
        this.miniMaxDepth = miniMaxDepth;
    }


    public PlayerType getPlayerType() {
        return playerType;
    }
    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }


    public int getMiniMaxDepth() {
        return miniMaxDepth;
    }
    public void setMiniMaxDepth(int miniMaxDepth) {
        this.miniMaxDepth = miniMaxDepth;
    }


    public Piece getBestPick() {
        return bestPick;
    }
    public void setBestPick(Piece bestPick) {
        this.bestPick = bestPick;
    }


    public int getPlayerID() {
        return playerID;
    }
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }


    public long getTimeSpent(){
        return timeSpent;
    }



    public Piece doPickPiece(Board board, Scanner scanner, boolean doMiniMax){
        long clockStart = System.currentTimeMillis();
        Piece piece = pickPiece(board, scanner, doMiniMax);
        timeSpent += System.currentTimeMillis() - clockStart;
        return piece;
    }

    public void doPlacePiece(Board board, Piece piece, Scanner scanner, boolean doMiniMax, boolean wrongPick){
        long clockStart = System.currentTimeMillis();
        placePiece(board, piece, scanner, doMiniMax, wrongPick);
        timeSpent += System.currentTimeMillis() - clockStart;
    }


    protected abstract Piece pickPiece(Board board, Scanner scanner, boolean doMiniMax);
    protected abstract void placePiece(Board board, Piece piece, Scanner scanner, boolean doMiniMax, boolean wrongPick);
}
