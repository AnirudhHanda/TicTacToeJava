import java.util.*;

class Game{
    static boolean started = false;
    static int arr[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    static int moves = 0;
    static String compSymbol;
    static String userSymbol;
    Board bObj = new Board();

    Scanner sc = new Scanner(System.in);

    static int[][] winCombinations = {
                                        {0, 1, 2},
                                        {3, 4, 5}, 
                                        {6, 7, 8}, 
                                        {0, 3, 6}, 
                                        {1, 4, 7}, 
                                        {2, 5, 8},
                                        {0, 4, 8}, 
                                        {2, 4, 6}
                                    };

    static boolean[][] availablePositions = {
                                    {true, true, true},
                                    {true, true, true},
                                    {true, true, true}
                                };

    

    void startGame(){
        started = true;


        this.bObj.initializeBoard();
        System.out.println("Game started: ");
        this.bObj.displayBoard();
    }

    void initializeUser(String symbol){
        this.userSymbol = symbol.toUpperCase();
        System.out.println("userSymbol: "+userSymbol);
        if(symbol.equals("O") || symbol.equals("o")){
            this.compSymbol = "X";
        } else{
            this.compSymbol = "O";
        }
        System.out.println("compSymbol: "+compSymbol);
    }

    void userMove(){
        if(!started){
            return;
        }
        System.out.print("Enter the position (row column): ");
        int row = sc.nextInt();
        int column = sc.nextInt();
        bObj.changePos(row, column, userSymbol);
        moves++;
        availablePositions[row][column] = false;
        System.out.println("USER's Move: ");
        bObj.displayBoard();
        checkResult();
    }

    void compMove(){
        if(!started){
            return;
        }

        // Checking for a Blocking Move
        for(int[] i : winCombinations){
            int row1 = i[0]/3;
            int col1 = i[0]%3;
            String box1 = bObj.board[row1][col1];

            int row2 = i[1]/3;
            int col2 = i[1] % 3;
            String box2 = bObj.board[row2][col2];

            int row3 = i[2]/3;
            int col3 = i[2]%3;
            String box3 = bObj.board[row3][col3];

            if(
                box1.equals(userSymbol) &&
                box2.equals(userSymbol) &&
                box3.equals("_")
            ){
                makeMove(row3, col3);
                return;
            }
            if(
                box1.equals(userSymbol) &&
                box3.equals(userSymbol) && 
                box2.equals("_")
            ){
                makeMove(row2, col2);
                return;
            }
            if(
                box2.equals(userSymbol) &&
                box3.equals(userSymbol) &&
                box1.equals("_")
            ){
                makeMove(row1, col1);
                return;
            }
        }

        // Checking for a Winning Move
        for(int[] i : winCombinations){
            int row1 = i[0]/3;
            int col1 = i[0]%3;
            String box1 = bObj.board[row1][col1];

            int row2 = i[1]/3;
            int col2 = i[1] % 3;
            String box2 = bObj.board[row2][col2];

            int row3 = i[2]/3;
            int col3 = i[2]%3;
            String box3 = bObj.board[row3][col3];

            if(
                box1.equals(compSymbol) &&
                box2.equals(compSymbol) &&
                box3.equals("_")
            ){
                makeMove(row3, col3);
                return;
            }
            if(
                box1.equals(compSymbol) &&
                box3.equals(compSymbol) && 
                box2.equals("_")
            ){
                makeMove(row2, col2);
                return;
            }
            if(
                box2.equals(compSymbol) &&
                box3.equals(compSymbol) &&
                box1.equals("_")
            ){
                makeMove(row1, col1);
                return;
            }
        }

        // if no winning or blocking move, make a random move
        randomPos();
    }

    void makeMove(int row, int col){
        bObj.board[row][col] = compSymbol;
        moves++;
        availablePositions[row][col] = false;
        System.out.println("COMPUTER's Move: ");
        bObj.displayBoard();
        checkResult();
    }

    void randomPos(){
        if(moves < 9){
            Random random = new Random();
            int row, col;
            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (!availablePositions[row][col]);

            bObj.changePos(row, col, compSymbol);
            moves++;
            availablePositions[row][col] = false;

            System.out.println("Computer's MOVE: ");
            bObj.displayBoard();

            checkResult();
        }
    }

    void checkResult(){
        if(moves >= 5){
            if(checkWin(userSymbol)){
                System.out.println("-------------------------------------");
                System.out.println(userSymbol+" WON!");
                System.out.println("-------------------------------------");
                resetGame();
            } else if(checkWin(compSymbol)){
                System.out.println("-------------------------------------");
                System.out.println(compSymbol+" WON!");
                System.out.println("-------------------------------------");
                resetGame();
            } else if(moves == 9){
                System.out.println("-------------------------------------");
                System.out.println("It's a DRAW");
                System.out.println("-------------------------------------");
                resetGame();
            }
        }
    }

    boolean checkWin(String symbol){
        for(int[] i : winCombinations){
            int row1 = i[0]/3;
            int col1 = i[0]%3;
            String box1 = bObj.board[row1][col1];

            int row2 = i[1]/3;
            int col2 = i[1] % 3;
            String box2 = bObj.board[row2][col2];

            int row3 = i[2]/3;
            int col3 = i[2]%3;
            String box3 = bObj.board[row3][col3];

            if(
                (box1.equals(symbol)) &&
                (box2.equals(symbol)) &&
                (box3.equals(symbol))
            ){
                return true;
            }
        }
        return false;
    }

    void resetGame(){
        bObj.initializeBoard();
        started = false;
        moves = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                availablePositions[i][j] = true;
            }
        }
    }
}

class Board{
    static String[][] board;

    void initializeBoard(){
        this.board = new String[3][3];
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                this.board[i][j] = "_";
            }
        }
    } 

    void displayBoard(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                System.out.print(this.board[i][j]+" ");
            }
            System.out.println();
        }
    }

    void changePos(int row, int column, String symbol){
        board[row][column] = symbol;
    }

}


public class TicTacToe{

    static void playGame(){
        Scanner sc = new Scanner(System.in);
        Game g = new Game();
        g.startGame();
        
        
        System.out.print("Select your Symbol (O/X): ");
        String symbol = sc.next();

        g.initializeUser(symbol);

        for(int i = 0; i<5; i++){
            g.userMove();
            g.compMove();
        }
    }
    public static void main(String[] args){
        playGame();
        // Game g = new Game();
        Scanner sc = new Scanner(System.in);

        System.out.print("Do you want to play again? (y/n): ");
        String choice = sc.next();
        do{
            playGame();
            System.out.print("Do you want to play again? (y/n): ");
            choice = sc.next();
        }while(choice == "y");

        System.out.println("----------------------");
        System.out.println("Thank You for Playing");

    }
}