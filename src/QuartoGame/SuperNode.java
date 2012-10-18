package QuartoGame;

import java.util.ArrayList;

public class SuperNode {
    private Board gameState;
    private ArrayList<SuperNode> children;
    private boolean isMe;
    private boolean isPlace;

    public SuperNode(Board gameState, boolean isMe, boolean isPlace){
        this.gameState = gameState;
        this.isMe = isMe;
        this.isPlace = isPlace;
    }

    public int bestPlace(int depth){
        int bestPlace = -1;
        children = new ArrayList<SuperNode>();
        int size = gameState.getRemainingPieces().size();
        int bestValue = -10000;
        for (int i = 0; i < size; i++) {
            Board newGameState = new Board();

            newGameState.setBoard(gameState.getBoard());
            newGameState.setPieces(gameState.getPieces());
            newGameState.placePiece(gameState.getFreePlaces().get(i), gameState.getActivePiece());
            SuperNode newNode = new SuperNode(newGameState, isMe, !isPlace);
            int newValue = newNode.getValue(depth, bestValue, 10000);
            if(newValue > bestValue) {
                bestValue = newValue;
                bestPlace = gameState.getFreePlaces().get(i);
            }
        }
        return bestPlace;
    }

    public Piece bestPiece(int depth){
        Piece bestPiece = null;
        children = new ArrayList<SuperNode>();
        int size = gameState.getRemainingPieces().size();
        int bestValue = -10000;
        for (int i = 0; i < size; i++) {
            Board newGameState = new Board();

            newGameState.setBoard(gameState.getBoard());
            newGameState.setPieces(gameState.getPieces());
            newGameState.setActivePiece(gameState.getRemainingPieces().get(i));
            SuperNode newNode = new SuperNode(newGameState, !isMe, !isPlace);
            int newValue = newNode.getValue(depth, bestValue, 10000);
            if(newValue > bestValue) {
                bestValue = newValue;
                bestPiece = gameState.getRemainingPieces().get(i);
            }
        }
        return bestPiece;
    }

    private int getValue(int depth, int initAlpha, int initBeta){
        if(gameState.checkForWinner(false)){
            return isMe? 9999 : -9999;
        }
        else if(depth == 0){
            // return the heuristic value of node
            return 0;
        }
        else{
            int alpha = initAlpha;
            int beta = initBeta;
            if(isPlace){
                children = new ArrayList<SuperNode>();
                int size = gameState.getFreePlaces().size();

                for (int i = 0; i < size; i++) {
                    Board newGameState = new Board();

                    newGameState.setBoard(gameState.getBoard());
                    newGameState.setPieces(gameState.getPieces());
                    newGameState.placePiece(gameState.getFreePlaces().get(i), gameState.getActivePiece());
                    children.add(new SuperNode(newGameState, isMe, !isPlace));
                }
            }
            else{
                children = new ArrayList<SuperNode>();
                int size = gameState.getRemainingPieces().size();

                for (int i = 0; i < size; i++) {
                    Board newGameState = new Board();

                    newGameState.setBoard(gameState.getBoard());
                    newGameState.setPieces(gameState.getPieces());
                    newGameState.setActivePiece(gameState.getRemainingPieces().get(i));
                    children.add(new SuperNode(newGameState, !isMe, !isPlace));
                }
            }

            if (isMe){
                for(SuperNode child : children){
                    // Se her etter feil ;)
                    alpha = Math.max(alpha, child.getValue(depth -1, alpha, beta));
                    if (initBeta <= alpha) break; // (Beta cut-off)
                }
                return alpha;
            }
            else{
                for(SuperNode child : children){
                    initBeta = Math.min(initBeta, child.getValue(depth -1, alpha, beta));
                    if (initBeta <= alpha) break; // (Alpha cut-off)
                }
                return beta;
            }
        }
    }
}
