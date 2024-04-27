// Pierce Gray
// Computer Science II
// 4/27/2024
// Personal Programming Exercise
// An implementation of the game tic-tac-toe with a computer opponent that uses the minimax algorithm to determine the best move.

public class TicTacToe {
    public static final int EMPTY = 0;
    public static final int X = 1;
    public static final int O = 2;

    public static final int UNKNOWN = -1;
    public static final int LOSS = 0;
    public static final int DRAW = 1;
    public static final int WIN = 2;

    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe.");
        int[] board = new int[9];
        printBoard(board);

        while (true) {
            System.out.print("Enter your move (1 - 9): ");
            int move = Integer.parseInt(System.console().readLine());
            if (board[move - 1] == EMPTY) {
                board[move - 1] = X;
            }
            else {
                System.out.println("Invalid move");
                continue;
            }
            checkEnding(board);

            // Computer move
            board = move(board);
            printBoard(board);
            checkEnding(board);
        }
    }

    public static int[] move(int[] board) {
        int[] newBoard = board.clone();
        int bestMove = -1;
        int bestValue = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                newBoard[i] = O;
                int value = minimax(newBoard, false);
                if (value > bestValue) {
                    bestMove = i;
                    bestValue = value;
                }
                newBoard[i] = EMPTY;
            }
        }
        newBoard[bestMove] = O;
        return newBoard;
    }

    public static void checkEnding(int[] board) {
        int value = leaf(board);
        if (value == WIN) {
            System.out.println("\nComputer Wins:");
            printBoard(board);
            System.exit(0);
        }
        else if (value == LOSS) {
            System.out.println("\nPlayer Wins:");
            printBoard(board);
            System.exit(0);
        }
        else if (value == DRAW) {
            System.out.println("\nDraw:");
            printBoard(board);
            System.exit(0);
        }
    }

    public static int minimax(int[] board, boolean isMaximizing) {
        // Check for win, loss, or draw
        int value = leaf(board);
        if (value != UNKNOWN) {
            return value;
        }
        if (isMaximizing) {
            int max = -100;
            for (int i = 0; i < 9; i++) {
                if (board[i] == EMPTY) {
                    int[] newBoard = board.clone();
                    newBoard[i] = O;
                    int result = minimax(newBoard, false);
                    max = Math.max(max, result);
                }
            }
            return max;
        }
        else {
            int min = 100;
            for (int i = 0; i < 9; i++) {
                if (board[i] == EMPTY) {
                    int[] newBoard = board.clone();
                    newBoard[i] = X;
                    int result = minimax(newBoard, true);
                    min = Math.min(min, result);
                }
            }
            return min;
        }
    }

    public static int leaf(int[] board) {
        // Check horizontal
        for (int i = 0; i < 9; i += 3) {
            if (board[i] != EMPTY && board[i] == board[i + 1] && board[i] == board[i + 2]) {
                return board[i] == O ? WIN : LOSS;
            }
        }
        // Check vertical
        for (int i = 0; i < 3; i++) {
            if (board[i] != EMPTY && board[i] == board[i + 3] && board[i] == board[i + 6]) {
                return board[i] == O ? WIN : LOSS;
            }
        }
        // Check diagonal
        if (board[0] != EMPTY && board[0] == board[4] && board[0] == board[8]) {
            return board[0] == O ? WIN : LOSS;
        }
        if (board[2] != EMPTY && board[2] == board[4] && board[2] == board[6]) {
            return board[2] == O ? WIN : LOSS;
        }
        // Check for draw
        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                return UNKNOWN;
            }
        }
        return DRAW;
    }

    public static void printBoard(int[] board) {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                System.out.print(" " + (i + 1) + " ");
            }
            else if (board[i] == X) {
                System.out.print(" X ");
            }
            else {
                System.out.print(" O ");
            }
            if (i % 3 == 2) {
                System.out.println("\n");
            }
        }
    }
}