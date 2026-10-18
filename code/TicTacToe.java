/*
 * Author : Naveen Pattihal
 * Roll no : 2629
 * Description: Implementation of a simple 2-player GUI-based Tic-Tac-Toe board game 
 *              using Java Swing. It demonstrates basic Object-Oriented Programming (OOP) 
 *              concepts: Abstraction (GameOperations interface), Inheritance (BaseGame parent class), 
 *              Encapsulation (private board fields and state control), and Polymorphism 
 *              (event handling and interface method implementation).
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface GameOperations {
    void resetGame();
    boolean checkWin();
}

class BaseGame {
    protected String title;

    public BaseGame(String title) {
        this.title = title;
    }

    public void showWelcome() {
        System.out.println("Starting: " + title);
    }
}

public class TicTacToe extends BaseGame implements GameOperations, ActionListener {
    private JFrame frame;
    private JButton[] buttons = new JButton[9];
    private JLabel statusLabel;
    private boolean isXTurn = true;
    private int moveCount = 0;

    public TicTacToe() {
        super("Tic-Tac-Toe Game");
        showWelcome();
        initUI();
    }

    private void initUI() {
        frame = new JFrame(title);
        frame.setSize(350, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setPreferredSize(new Dimension(350, 40));
        frame.add(statusLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3, 5, 5));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            boardPanel.add(buttons[i]);
        }
        frame.add(boardPanel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Reset Game");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 14));
        restartButton.addActionListener(e -> resetGame());
        frame.add(restartButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (!clickedButton.getText().equals("")) {
            return;
        }

        if (isXTurn) {
            clickedButton.setText("X");
            clickedButton.setForeground(Color.BLUE);
        } else {
            clickedButton.setText("O");
            clickedButton.setForeground(Color.RED);
        }

        moveCount++;

        if (checkWin()) {
            String winner = isXTurn ? "Player X" : "Player O";
            statusLabel.setText(winner + " Wins!");
            disableButtons();
            JOptionPane.showMessageDialog(frame, winner + " wins the game!");
        } else if (moveCount == 9) {
            statusLabel.setText("It's a Draw!");
            JOptionPane.showMessageDialog(frame, "Game ended in a draw!");
        } else {
            isXTurn = !isXTurn;
            statusLabel.setText("Player " + (isXTurn ? "X" : "O") + "'s Turn");
        }
    }

    @Override
    public boolean checkWin() {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] condition : winConditions) {
            String b1 = buttons[condition[0]].getText();
            String b2 = buttons[condition[1]].getText();
            String b3 = buttons[condition[2]].getText();

            if (!b1.equals("") && b1.equals(b2) && b2.equals(b3)) {
                return true;
            }
        }
        return false;
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    @Override
    public void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
        isXTurn = true;
        moveCount = 0;
        statusLabel.setText("Player X's Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}
