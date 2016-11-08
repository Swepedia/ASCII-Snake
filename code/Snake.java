import java.io.*;
import java.util.Arrays;
import java.awt.event.KeyEvent;
import jline.Terminal;

public class Snake 
{

    static final int FRAMES_PER_SECOND = 10;
    static final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    static char[][] board = new char[20][20];
    static char border = '.';
    static char dir = 0;

	public static void main(String[] args) throws IOException, InterruptedException
	{
        //Opens a new console window to run the game in
        Runtime runtime = Runtime.getRuntime();
        if(System.console() == null)
        {
            try
            {
                runtime.exec("cmd /c start cmd.exe");
            }
            catch(IOException e)
            {
                System.out.println("IOException");
                runtime.exit(1);
            }
        }
        Console console = System.console();

        //Sets up the board with the border
        for(int i = 0; i < board.length; i++)
        {
            board[0][i] = border;
            board[board.length - 1][i] = border;
            board[i][0] = border;
            board[i][board[0].length - 1] = border;
        }

        //display();
        //runtime.exit(0);
	
        long nextTick = System.currentTimeMillis();
        int sleep = 0;
        boolean run = true;
        KeyEvent kb;

        //Main loop
        while(run) {
            display();
            Thread.sleep(SKIP_TICKS);
        }

        
    }
    public static void display() throws IOException, InterruptedException
        {
            /*try
            {
                Runtime.getRuntime().exec("cmd /c cls");
            }
            catch(IOException e)
            {
                System.out.println("IOException");
                runtime.exit(1);
            }*/

            //Clears the console screen before printing the board to it
            try
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            catch(IOException e)
            {
                System.out.println("IOException");
            }
            catch(InterruptedException e)
            {
                System.out.println("InterruptedException");
            }

            //Prints the board to the console
            for(int i = 0; i < board.length; i++)
            {
                for(int j = 0; j < board[0].length; j++)
                {
                    //board[i][j] = 48;
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("Arrow keys to move. \'Ctrl + C\' to exit");
        }

    public static void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
                dir = 0;
                break;
            case KeyEvent.VK_RIGHT:
                dir = 1;
                break;
            case KeyEvent.VK_DOWN:
                dir = 2;
                break;
            case KeyEvent.VK_LEFT:
                dir = 3;
                break;
        }
    }
}
