import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game.
 * 
 * @author Kyle Giang 101332223
 * @version April 10, 2026
 */

public class TicTacToe implements ActionListener
{
   public static final String PLAYER_X = "X"; // player using "X"
   public static final String PLAYER_O = "O"; // player using "O"
   public static final String EMPTY = "";  // empty cell
   public static final String TIE = "T"; // game ended in a tie
 
   private String player;   // current player (PLAYER_X or PLAYER_O)

   private String winner;   // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress

   private int numFreeSquares; // number of squares still free
   
   private String board[][]; // 3x3 array representing the board
   
   private int row, col;  //row and column for internal logic board
   
   private JFrame frame;
   private JScrollPane scrollPane;
   private JButton[][] buttons;
   private JTextField status;
   private JMenuItem quitItem;
   private JMenuItem newItem;
   private JMenuBar menubar;
   private JMenu fileMenu;
   private JPanel gamePanel;   //all GUI elements
   
   /** 
    * Constructs a new Tic-Tac-Toe board GUI.
    */
   public TicTacToe()
   {
      board = new String[3][3];//initialize internal game board
      
      frame = new JFrame("TIC-TAC-TOE");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Container contentPane = frame.getContentPane();
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS)); //set up game window and layout
      
      menubar = new JMenuBar();
      frame.setJMenuBar(menubar); //create a menu bar and add to frame
      
      fileMenu = new JMenu("Options");
      menubar.add(fileMenu);             //create the menu for the menu bar
     
      quitItem = new JMenuItem("Quit"); 
      fileMenu.add(quitItem);
      
      newItem = new JMenuItem("New Game");
      fileMenu.add(newItem);               //create menu items to add to menu
      
      //create shortcuts for menu options
      final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
      newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));
      quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
      
      //listen for menu selections
      newItem.addActionListener(this); 
      quitItem.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0); 
            }
        } //anonymous subclass for quitting
      );
      
      gamePanel = new JPanel();
      gamePanel.setLayout(new GridLayout(3, 3)); //create the game board as a grid at the top of the window
      buttons = new JButton[3][3]; //initialize the jubttons
      for(int i = 0; i < 3; i++)
      {
          for(int j = 0; j < 3; j++)
          {
              buttons[i][j] = new JButton(EMPTY);
              gamePanel.add(buttons[i][j]);
              buttons[i][j].addActionListener(this); //set each individual jbutton for the start of the game
          }
      }
      gamePanel.setPreferredSize(new Dimension(300, 300));
      gamePanel.setMaximumSize(new Dimension(300, 300)); //set the grid to be a fixed square
      contentPane.add(gamePanel);
       
      status = new JTextField(50);
      status.setEditable(false);
      status.setFont(new Font(null, Font.BOLD, 18));
      status.setHorizontalAlignment(JTextField.CENTER);
      status.setText("X's Turn");
      contentPane.add(status); //status bar at the bottom of the window

      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);
      clearBoard(); //set the internal board to be empty at the beginning
   }

   /**
    * Sets everything up for a new game.  Marks all squares in the Tic Tac Toe board as empty,
    * and indicates no winner yet, 9 free squares and the current player is player X.
    */
   private void clearBoard()
   {
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            board[i][j] = EMPTY;
         }
      }
      winner = EMPTY;
      numFreeSquares = 9;
      player = PLAYER_X;     // Player X always has the first turn.
   }

   /**
    * Returns true if filling the given square gives us a winner, and false
    * otherwise.
    *
    * @param int row of square just set
    * @param int col of square just set
    * 
    * @return true if we have a winner, false otherwise
    */
   private boolean haveWinner(int row, int col) 
   {
       // unless at least 5 squares have been filled, we don't need to go any further
       // (the earliest we can have a winner is after player X's 3rd move).

       if (numFreeSquares>4) return false;

       // Note: We don't need to check all rows, columns, and diagonals, only those
       // that contain the latest filled square.  We know that we have a winner 
       // if all 3 squares are the same, as they can't all be blank (as the latest
       // filled square is one of them).

       // check row "row"
       if ( board[row][0].equals(board[row][1]) &&
            board[row][0].equals(board[row][2]) ) return true;
       
       // check column "col"
       if ( board[0][col].equals(board[1][col]) &&
            board[0][col].equals(board[2][col]) ) return true;

       // if row=col check one diagonal
       if (row==col)
          if ( board[0][0].equals(board[1][1]) &&
               board[0][0].equals(board[2][2]) ) return true;

       // if row=2-col check other diagonal
       if (row==2-col)
          if ( board[0][2].equals(board[1][1]) &&
               board[0][2].equals(board[2][0]) ) return true;

       // no winner yet
       return false;
   }
   
   /** This action listener is called when the user clicks on 
    * any of the GUI's buttons. 
    */
   public void actionPerformed(ActionEvent e)
   {
       Object o = e.getSource();
       if (o instanceof JButton) //if one of the game buttons was pressed
       {
          JButton button = (JButton)o;
          for(int i = 0; i < 3; i ++)
          {
              for(int j = 0; j < 3; j++)
              {
                  if(button == buttons[i][j])
                  {
                      row = i;
                      col = j;//find which button was pressed for internal board
                  }
              }
          }
          button.setText(player);
          button.setEnabled(false);    //change the button accordingly
          board[row][col] = player;        // fill in the square with player
          numFreeSquares--;            // decrement number of free squares

          // see if the game is over
          if (haveWinner(row,col)) 
             winner = player; // must be the player who just went
          else if (numFreeSquares==0) 
             winner = TIE; // board is full so it's a tie
         
          // change to other player (this won't do anything if game has ended)
          if (player==PLAYER_X) 
             player=PLAYER_O;
          else 
             player=PLAYER_X;
             
          if(winner != EMPTY)//if the game has ended
          {
              for(int i = 0; i < 3; i++)
              {
                  for(int j = 0; j < 3; j++)
                  {
                      buttons[i][j].setEnabled(false);//disable all buttons
                  }
              }
              if(winner == TIE)
              {
                  status.setText("Game over...Tie!");//tie message
              }
              else
              {
                  status.setText("Game over..."+winner+" wins!");//winner message
              }
          }
          else
          {
              status.setText(player + "'s turn");//game continues with the next player's turn
          }
       }
       else//menu was pressed
       {
           JMenuItem item = (JMenuItem) o;
           if(item == newItem)//new game was pressed
           {
               clearBoard();//clear internal board
               for(int i = 0; i < 3; i++)
               {
                   for(int j = 0; j < 3; j++)
                   {
                       buttons[i][j].setText(EMPTY);
                       buttons[i][j].setEnabled(true);//reset the GUI board
                   }
               }
               status.setText("X's Turn");
           }
       }
   }
}