import java.io.*;
import java.awt.event.KeyEvent;
import java.lang.Thread;
import javax.swing.text.Document;
import javax.swing.text.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.lang.Math;

public class Snake extends javax.swing.JFrame { 

    static final int FRAMES_PER_SECOND = 10;
    static final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
    static final int ROWS = 17;
    static final int COLUMNS = ROWS * 2;

    static char[][] board = new char[ROWS][COLUMNS];
    static char border = '.';
    static char snakeHead = '*';
    static char dir = 1;
    static char foodChar = '@';
    static LinkedList<Pos> snake = new LinkedList<>();
    static Pos food = getRandPos();
    //static Pos head = new Pos(COLUMNS / 2, ROWS / 2);
    //static Pos tail = new Pos(COLUMNS / 2 - 3, ROWS / 2);

    private javax.swing.JScrollPane jScrollPane1;
    static private javax.swing.JTextArea jTextArea1;

    public Snake() {
        initComponents();
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Snake");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jScrollPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jScrollPane1.setFocusable(true);
        jScrollPane1.setBorder(null);
        jScrollPane1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N

        jTextArea1.setFocusable(true);
        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(COLUMNS);
        jTextArea1.setFont(new java.awt.Font("monospaced", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(0, 153, 0));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(ROWS);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                );

        pack();
    }

    private void formKeyPressed(java.awt.event.KeyEvent evt) {
        //keyPress(evt);
        int keyCode = evt.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
                dir = 0;
                System.out.println("Up");
                break;
            case KeyEvent.VK_RIGHT:
                dir = 1;
                System.out.println("Right");
                break;
            case KeyEvent.VK_DOWN:
                dir = 2;
                System.out.println("Down");
                break;
            case KeyEvent.VK_LEFT:
                dir = 3;
                System.out.println("Left");
                break;
        }
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

	public static void main(String[] args) throws IOException, InterruptedException {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Snake.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Snake.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Snake.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Snake.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Snake().setVisible(true);
            }
        });

        //Sets up the board with the border
        ////////////////////////////////////
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                board[i][j] = ' ';
            }
        }
        for(int i = 0; i < board.length; i++) {
            board[i][0] = border;
            board[i][board[0].length - 1] = border;
        }
        for(int i = 0; i < board[0].length; i++) {
            board[0][i] = border;
            board[board.length - 1][i] = border;
        }
        String[] stringBoard = new String[20];
        for(int i = 0; i < board.length; i++) {
            stringBoard[i] = new String(board[i]);
        }

        //Have to sleep the thread so that I don't get nullPointerException
        Thread.sleep(1000);
        for(int i = 0; i < board.length; i++) {
            jTextArea1.append(stringBoard[i] + "\n");
        }
        //Removes the last, empty line
        int end = jTextArea1.getDocument().getLength();
        Document doc = jTextArea1.getDocument();
        try {
            doc.remove(end - 1, 1);
        }
        catch(BadLocationException e) {
            System.err.println("Bad location");
        }
        ////////////////////////////////////


        boolean run = true;
        snake.add(new Pos(COLUMNS / 2, ROWS / 2));
        for(int i = 1; i < 3; i++) {
            snake.add(new Pos(snake.peek().getX() - i, snake.peek().getY()));
        }
        snake.add(new Pos(COLUMNS / 2 - 3, ROWS / 2));

        //Main loop
        while(run) {
            display();
            Thread.sleep(SKIP_TICKS);
        }
    }



    public static boolean display() throws IOException, InterruptedException {
        boolean first = false;
        int index = 0;
        int prevX = snake.peekFirst().getX();
        int prevY = snake.peekFirst().getY();

        Pos previousPosition = snake.peekLast();

        //Display the food
        jTextArea1.replaceRange(Character.toString(foodChar), food.getPos(), food.getPos() + 1);

        ListIterator<Pos> iterate = snake.listIterator(0);

        //Cases to display the snake moving in different directions
        switch(dir) {
            //Up
            case 0:
                if(snake.peek().getY() > 1) {
                    while(iterate.hasNext()) {
                        prevX = snake.get(index).getX();
                        prevY = snake.get(index).getY();
                        iterate.next().setPos(prevX, prevY - 1);
                        index++;
                    }
                } else {
                    return false;
                }
                break;
            //Right
            case 1:
                if(snake.peek().getX() < (COLUMNS / 2) - 1) {
                    while(iterate.hasNext()) {
                        prevX = snake.get(index).getX();
                        prevY = snake.get(index).getY();
                        iterate.next().setPos(prevX + 1, prevY);
                        index++;
                    }
                } else {
                    return false;
                }
                break;
            //Down
            case 2:
                if(snake.peek().getY() < ROWS - 2) {
                    while(iterate.hasNext()) {
                        prevX = snake.get(index).getX();
                        prevY = snake.get(index).getY();
                        iterate.next().setPos(prevX, prevY + 1);
                        index++;
                    }
                } else {
                    return false;
                }
                break;
            //Left
            case 3:
                if(snake.peek().getX() > 1) {
                    while(iterate.hasNext()) {
                        prevX = snake.get(index).getX();
                        prevY = snake.get(index).getY();
                        iterate.next().setPos(prevX - 1, prevY);
                        index++;
                    }
                    //iterate.peek()
                } else {
                    return false;
                }
                break;
        }
        //Display the snake
        //iterate = snake.listIterator(1);
        //Pos headNext = iterate.next();
        if(snake.peek().getPos() < new Pos(16, 16).getPos()) { 
            jTextArea1.replaceRange(Character.toString(snakeHead), snake.peek().getPos(), snake.peek().getPos() + 1);
        }
        //attempting to erase the parts of the snake that are not there anymore
        /*System.out.println("snake 0: " + snake.get(0).getPos());
        System.out.println("snake 1: " + snake.get(1).getPos());
        System.out.println("snake 2: " + snake.get(2).getPos());
        System.out.println("snake 3: " + snake.get(3).getPos());
        */
        jTextArea1.replaceRange("p", previousPosition.getPos(), previousPosition.getPos() + 1);

        //Checks if the food has been eaten, if it has add a segment to the snake and make a new food appear
        if(snake.peek().getPos() == food.getPos()){
            snake.add(new Pos(prevX, prevY));
            food = getRandPos();
        }
        return true;
    }

    private static Pos getRandPos() {
        return new Pos((int)(Math.random() * ((COLUMNS / 2) - 2)) + 1, (int)(Math.random() * (ROWS - 2)) + 1);
    }

    private static class Pos {
        private int pos;
        private int x;
        private int y;

        public Pos(int x, int y) {
            this.x = x * 2 - 1;
            this.y = y;
            pos = (x * 2) - 1 + y + (y * COLUMNS);
        }
        public Pos() {
            this.x = 1;
            this.y = 1;
            pos = (x * 2) - 1 + y + (y * COLUMNS);
        }

        public int getPos() {
            return pos;
        }

        public int getRelPos() {
            return x; 
        }

        public int getX() {
            return (x + 1) / 2;
        }

        public int getY() {
            return y;
        }

        public void setPos(int x, int y) {
            this.x = x * 2 - 1;
            this.y = y;
            pos = (x * 2) - 1 + y + (y * COLUMNS);
        }
    }
}


