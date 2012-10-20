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
        children = new ArrayList<SuperNode>();
        int size = gameState.getFreePlaces().size();
        ArrayList<Integer> bestPlaces = new ArrayList<Integer>();
        int bestValue = -10000;
        for (int i = 0; i < size; i++) {
            Board newGameState = new Board();

            newGameState.setBoard(gameState.getBoard());
            newGameState.setPieces(gameState.getPieces());
            newGameState.placePiece(gameState.getFreePlaces().get(i), gameState.getActivePiece());
            SuperNode newNode = new SuperNode(newGameState, true, false);
            int newValue = newNode.getValue(depth - 1, -10000, 10000);
            if(newValue > bestValue) {
                bestValue = newValue;
                bestPlaces.clear();
                bestPlaces.add(gameState.getFreePlaces().get(i));
            }
            else if(newValue == bestValue){
            	bestPlaces.add(gameState.getFreePlaces().get(i));
            }
        }
        return bestPlaces.get((int) Math.floor(bestPlaces.size() * Math.random()));
    }

    public Piece bestPiece(int depth){
        children = new ArrayList<SuperNode>();
        int size = gameState.getRemainingPieces().size();
        int bestValue = -10000;
        ArrayList<Piece> bestPieces = new ArrayList<Piece>();
        for (int i = 0; i < size; i++) {
            Board newGameState = new Board();

            newGameState.setBoard(gameState.getBoard());
            newGameState.setPieces(gameState.getPieces());
            newGameState.setActivePiece(gameState.getRemainingPieces().get(i));
            SuperNode newNode = new SuperNode(newGameState, false, true);
            int newValue = newNode.getValue(depth - 1, -10000, 10000);
            if(newValue > bestValue) {
            	bestValue = newValue;
                bestPieces.clear();
                bestPieces.add(gameState.getRemainingPieces().get(i));
            }
            else if(newValue == bestValue){
            	bestPieces.add(gameState.getRemainingPieces().get(i));
            }
        }
        return bestPieces.get((int) Math.floor(bestPieces.size() * Math.random()));
    }

    private int getValue(int depth, int initAlpha, int initBeta){
        if(!isPlace && gameState.checkForWinner(false)){
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
                    children.add(new SuperNode(newGameState, isMe, false));
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
                    children.add(new SuperNode(newGameState, !isMe, true));
                }
            }

            if (isMe){
            	int newDepth = depth;
            	if(!isPlace) newDepth--;
            	for(SuperNode child : children){
                    // Se her etter feil ;)
                	alpha = Math.max(alpha, child.getValue(newDepth, alpha, beta));
                    if (beta <= alpha) break; // (Beta cut-off)
                }
                return alpha;
            }
            else{
            	int newDepth = depth;
            	if(!isPlace) newDepth--;
            	for(SuperNode child : children){
                	beta = Math.min(beta, child.getValue(newDepth, alpha, beta));
                    if (beta <= alpha) break; // (Alpha cut-off)
                }
                return beta;
            }
        }
    }
}
