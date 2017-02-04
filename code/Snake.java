import java.io.*;
import java.awt.event.KeyEvent;

public class Snake extends javax.swing.JFrame {

    static final int FRAMES_PER_SECOND = 10;
    static final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    static char[][] board = new char[20][20];
    static char border = '.';
    static char dir = 0;

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

        jScrollPane1.setBorder(null);
        jScrollPane1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(0, 153, 0));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                );

        pack();
    }

    private void formKeyPressed(java.awt.event.KeyEvent evt) {
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
        for(int i = 0; i < board.length; i++) {
            board[0][i] = border;
            board[board.length - 1][i] = border;
            board[i][0] = border;
            board[i][board[0].length - 1] = border;
        }
        String[] stringBoard = new String[20];
        for(int i = 0; i < board.length; i++) {
            stringBoard[i] = new String(board[i]);
        }
        for(int i = 0; i < board.length; i++) {
            //System.out.println("i: " + i + "\nj: " + j);
            jTextArea1.append(stringBoard[i] + "\n");
        }

        boolean run = true;
        KeyEvent kb;

        /*Main loop
        while(run) {
            display();
            Thread.sleep(SKIP_TICKS);
        }
        */
    }



    public static void display() throws IOException, InterruptedException {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch(IOException e) {
            System.out.println("IOException");
        } catch(InterruptedException e) {
            System.out.println("InterruptedException");
        }

        //Prints the board to the console
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Arrow keys to move. \'Ctrl + C\' to exit");
        System.out.println(dir);
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
