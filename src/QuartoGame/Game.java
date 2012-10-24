package QuartoGame;

import java.util.Scanner;
import QuartoGame.AbstractPlayer.PlayerType;

public class Game {

    private SuperBoard board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private AbstractPlayer activePlayer;
    int games;
    boolean log;
    boolean debug;
    boolean printBoard;

    public Game(AbstractPlayer p1, AbstractPlayer p2, int numbGames, boolean log, boolean debug, boolean printBoard) {
        this.board = new SuperBoard(true);
        this.player1 = p1;
        this.player2 = p2;
        this.activePlayer = p1;
        this.games = numbGames;
        this.log = log;
        this.debug = debug;
        this.printBoard = printBoard;
    }

    public AbstractPlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(AbstractPlayer player1) {
        this.player1 = player1;
    }

    public AbstractPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(AbstractPlayer player2) {
        this.player2 = player2;
    }

    public AbstractPlayer getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(AbstractPlayer activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public boolean isLog() {
        return log;
    }

    public void setLog(boolean log) {
        this.log = log;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isPrintBoard() {
        return printBoard;
    }

    public void setPrintBoard(boolean printBoard) {
        this.printBoard = printBoard;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(SuperBoard board) {
        this.board = board;
    }
    
    public void playTurn(boolean log, Scanner scanner, boolean doMiniMax) {

        if (log) {
            System.out.println();
            System.out.println("Remaining pieces:" + board.remainingToSting());
            System.out.println("NotWinning: " + ((SuperBoard) board).getNotWinningPieces().toString());
            board.getWinningPieceSorts();
        }

        Piece piece = this.activePlayer.doPickPiece(board, scanner, doMiniMax);
        this.board.setActivePiece(piece);
        if (log) {
            System.out.println("Player #" + this.activePlayer.getPlayerID() + " picks " + piece.toString());
        }

        if (this.activePlayer == this.player1)
            this.activePlayer = this.player2;
        else
            this.activePlayer = this.player1;

        if (log) {
            System.out.println("Player #" + this.activePlayer.getPlayerID() + "'s turn to place piece " + piece.toString());
        }

        this.activePlayer.doPlacePiece(board, piece, scanner, doMiniMax, false);

        if (log) {
            System.out.println("Player #" + this.activePlayer.getPlayerID() + " has placed " + piece.toString());
        }

    }

    public void printBoard() {
        System.out.println(board.printBoard());
    }

    public boolean isFinished(boolean log) {
        return board.checkForWinner(log);

    }

    public void initGame(Scanner scanner) {

        boolean again = true;
        while (scanner.hasNext() && again) {

            String in = scanner.next();
            String[] settings = in.split("-");
            System.out.println(settings.length);
            if (settings.length == 1) {
                if (settings[0].toUpperCase().matches("D")) {
                    break;
                }
                else if (settings[0].toUpperCase().matches("T")){
                    tournamentDemo();
                    System.exit(0);
                    break;
                }
            } else if (settings.length == 6) {
                switch (settings[0].toUpperCase()) {
                    case "H":
                        this.setPlayer1(new Player(PlayerType.HUMAN, 1));
                        break;
                    case "R":
                        this.setPlayer1(new Player(PlayerType.RANDOM, 1));
                        break;
                    case "N":
                        this.setPlayer1(new Player(PlayerType.NOVICE, 1));
                        break;
                    default:
                        if (settings[0].toUpperCase().startsWith("M")) {
                            String[] depth = settings[0].split("");
                            if (depth[2].matches("\\d")) {
                                this.setPlayer1(new Player(PlayerType.MINIMAXD, 1, Integer.parseInt(depth[2])));
                                break;
                            }
                        }
                        System.out.println("Invalid player type for player one!");
                        break;
                }
                switch (settings[1].toUpperCase()) {
                    case "H":
                        this.setPlayer2(new Player(PlayerType.HUMAN, 2));
                        break;
                    case "R":
                        this.setPlayer2(new Player(PlayerType.RANDOM, 2));
                        break;
                    case "N":
                        this.setPlayer2(new Player(PlayerType.NOVICE, 2));
                        break;
                    default:
                        if (settings[1].toUpperCase().startsWith("M")) {
                            String[] depth = settings[1].split("");
                            if (depth[2].matches("\\d")) {
                                this.setPlayer2(new Player(PlayerType.MINIMAXD, 2, Integer.parseInt(depth[2])));
                                break;
                            }
                        }
                        System.out.println("Invalid player type for player two!");
                        break;
                }
                if (settings[2].matches("\\d")) {
                    this.setGames(Integer.parseInt(settings[2]));
                } else {
                    System.out.println("Invalid input for number of games!");
                }
                if (settings[3].matches("\\d")) {
                    if (Integer.parseInt(settings[3]) == 0) {
                        this.setLog(false);
                    }
                } else {
                    System.out.println("Invalid input for Log variable(1/0)");
                }
                if (settings[4].matches("\\d")) {
                    if (Integer.parseInt(settings[4]) == 1) {
                        this.setDebug(true);
                    }
                } else {
                    System.out.println("Invalid input for Debug variable(1/0)");
                }

                if (settings[5].matches("\\d")) {
                    if (Integer.parseInt(settings[5]) == 1) {
                        this.setPrintBoard(true);
                        break;
                    }
                } else {
                    System.out.println("Invalid input for PrintBoard variable(1/0)");
                }
            }
            again = true;
            System.out.println("Please try again!");
            System.out.println("Choose settings by writing: \"Player 1 type - Player 2 type - Number of games - log On/0ff(1/0)- debug On/0ff(1/0) - printboard On/0ff(1/0)\"");

        }
    }

    public static void tournamentDemo(){

        AbstractPlayer p1;
        AbstractPlayer p2;
        int games = 100;
        boolean log = false;
        boolean debug = false;
        boolean printBoard = false;


        p1 = new Player(PlayerType.MINIMAXD, 1, 3);
        p2 = new MVPlayer(PlayerType.MINIMAXD, 1, 3);

        Game.game(p1, p2, games, log, debug, printBoard, false);

        p1 = new Player(PlayerType.MINIMAXD, 1, 3);
        p2 = new SuperPlayer(PlayerType.MINIMAXD, 1, 3, true);

        Game.game(p1, p2, games, log, debug, printBoard, false);

        p1 = new MVPlayer(PlayerType.MINIMAXD, 1, 3);
        p2 = new SuperPlayer(PlayerType.MINIMAXD, 1, 3, true);

        Game.game(p1, p2, games, log, debug, printBoard, false);

    }

    public static void game(AbstractPlayer p1, AbstractPlayer p2, int games, boolean log, boolean debug, boolean printBoard, boolean scan){
        int gameCount = 0;
        int playerOneWins = 0;
        int playerTwoWins = 0;
        int ties = 0;

        Scanner scanner = new Scanner(System.in);

        Game newGame = new Game(p1, p2, games, log, debug, printBoard);

        if(scan){

            System.out.println("WELCOME TO QUARTO!");
            System.out.println("Choose settings by writing: \"Player 1 type - Player 2 type - Number of games - log On/0ff(1/0)- debug On/0ff(1/0) - printboard On/0ff(1/0)\"");
            System.out.println("Default is: \"N-R-1-1-0-0\", to choose default type D");
            System.out.println("Available player types are: (H)Human, (R)Random, (N)Novice, (MD)Minimax to depth D(remember to choose a number for D!)");
            System.out.println("To play the Tournament Demo type T");

            newGame.initGame(scanner);
        }

        System.out.println();
        System.out.println();
        System.out.println("Plays " + newGame.getGames() + " games!");
        System.out.println("Player 1 is type: " + newGame.getPlayer1().getPlayerType().toString() + (newGame.getPlayer1().getMiniMaxDepth() > 0 ? "-" + newGame.getPlayer1().getMiniMaxDepth() : ""));
        System.out.println("Player 2 is type: " + newGame.getPlayer2().getPlayerType().toString() + (newGame.getPlayer2().getMiniMaxDepth() > 0 ? "-" + newGame.getPlayer2().getMiniMaxDepth() : ""));
        while (gameCount < newGame.getGames()) {
            System.out.print(".");
            System.out.print("\r" + Math.round(((double)gameCount/(double)games)*100) + "%");
            if (gameCount >= 1) {
                Game cloneGame = new Game(newGame.getPlayer1(), newGame.getPlayer2(), newGame.getGames(), newGame.isLog(), newGame.isDebug(), newGame.isPrintBoard());
                newGame = cloneGame;
                if(gameCount % 2 == 1)
                    newGame.setActivePlayer(p2);
            }
            boolean finished = false;

            int count = 1;

            while (!finished && count < 17 ) {
                if (count < 8) {
                    if (newGame.isLog()) {
                        System.out.println();
                        System.out.println("Round: " + count);
                    }
                    newGame.playTurn(newGame.isDebug(), scanner, false);

                    if (newGame.isPrintBoard()) {
                        newGame.printBoard();
                    }

                    if (newGame.isFinished(newGame.isLog())) {
                        if (newGame.isLog()) {
                            if (!newGame.isPrintBoard())
                                newGame.printBoard();

                            System.out.println();
                            System.out.println("The winner is: Player #" + newGame.getActivePlayer().getPlayerID() + "!");
                        }
                        finished = true;
                        if (newGame.getActivePlayer() == newGame.getPlayer1()) {
                            playerOneWins++;
                        } else {
                            playerTwoWins++;
                        }
                    } else if (newGame.getBoard().getPieces().size() == 0) {
                        System.out.println(newGame.getBoard().getPieces().size());
                        finished = true;
                        ties++;
                        if (newGame.isLog()) {
                            if (!newGame.isPrintBoard())
                                newGame.printBoard();

                            System.out.println("Its a tie!");
                        }
                    }
                } else {
                    if (newGame.isLog()) {
                        System.out.println();
                        if (newGame.getPlayer1().getPlayerType() == PlayerType.MINIMAXD || newGame.getPlayer2().getPlayerType() == PlayerType.MINIMAXD) {
                            System.out.println("Round: " + count + " Minimax engage");
                        } else {
                            System.out.println("Round: " + count);
                        }
                    }
                    newGame.playTurn(newGame.isDebug(), scanner, true);

                    if (newGame.isPrintBoard()) {
                        newGame.printBoard();
                    }

                    if (newGame.isFinished(newGame.isLog())) {
                        if (newGame.isLog()) {
                            if (!newGame.isPrintBoard())
                                newGame.printBoard();

                            System.out.println();
                            System.out.println("The winner is: Player #" + newGame.getActivePlayer().getPlayerID() + "!");
                        }
                        finished = true;
                        if (newGame.getActivePlayer() == newGame.getPlayer1()) {
                            playerOneWins++;
                        } else {
                            playerTwoWins++;
                        }
                    } else if (newGame.getBoard().getPieces().size() == 0) {
                        finished = true;
                        ties++;
                        if (newGame.isLog()) {
                            if (!newGame.isPrintBoard())
                                newGame.printBoard();

                            System.out.println("Its a tie!");
                        }
                    }
                }
                count++;
            }
            if(!finished)
                ties++;
            if(p1.getBestPick() != null)
                p1.setBestPick(null);
            if(p2.getBestPick() != null)
                p2.setBestPick(null);
            gameCount++;
        }
        if (newGame.getGames() > 1) {
            System.out.println("DONE!");
            System.out.println("Player " + p1.getPlayerID() + " of type " + p1.getClass().getName() + " won: " + playerOneWins + " times!");
            System.out.println("Player " + p2.getPlayerID() + " of type " + p2.getClass().getName() + " won: " + playerTwoWins + " times!");
            System.out.println("The game tied: " + ties + " times!!");

            System.out.println("Player " + p1.getPlayerID() + " spent " + p1.getTimeSpent() + " ms in total.");
            System.out.println("Player " + p2.getPlayerID() + " spent " + p2.getTimeSpent() + " ms in total.");

            System.out.println("Player " + p1.getPlayerID() + " spent " + p1.getTimeSpent() / games + " ms in average per game.");
            System.out.println("Player " + p2.getPlayerID() + " spent " + p2.getTimeSpent() / games + " ms in average per game.");
        }
    }

    public static void main(String[] args) {

        AbstractPlayer p1 = new Player(PlayerType.NOVICE, 1, 3);
        AbstractPlayer p2 = new Player(PlayerType.RANDOM, 2, 3);
        int games = 1;
        boolean log = true;
        boolean debug = true;
        boolean printBoard = false;

        Game.game(p1, p2, games, log, debug, printBoard, true);
    }

}
