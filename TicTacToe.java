import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
    private final JButton[] buttons = new JButton[9];
    private boolean xTurn = true;
    private JLabel statusLabel;

    public TicTacToe() {
        setTitle("✨ Tic Tac Toe – Java Edition");
        setSize(420, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(33, 33, 33));

        // Title
        JLabel title = new JLabel("TIC TAC TOE", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        // Game grid
        JPanel grid = new JPanel(new GridLayout(3, 3, 8, 8));
        grid.setBackground(new Color(33, 33, 33));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Poppins", Font.BOLD, 48));
            buttons[i].setBackground(new Color(48, 48, 48));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(85, 85, 85), 2));
            buttons[i].addActionListener(this);
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hover effect
            buttons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    JButton b = (JButton) evt.getSource();
                    if (b.getText().equals(""))
                        b.setBackground(new Color(70, 70, 70));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    JButton b = (JButton) evt.getSource();
                    if (b.getText().equals(""))
                        b.setBackground(new Color(48, 48, 48));
                }
            });
            grid.add(buttons[i]);
        }
        add(grid, BorderLayout.CENTER);

        // Status bar
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(33, 33, 33));
        statusLabel = new JLabel("X's Turn", SwingConstants.CENTER);
        statusLabel.setForeground(new Color(255, 215, 0));
        statusLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        bottom.add(statusLabel, BorderLayout.CENTER);

        JButton resetButton = new JButton("↻ Reset");
        resetButton.setFont(new Font("Poppins", Font.PLAIN, 14));
        resetButton.setBackground(new Color(100, 100, 255));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        resetButton.addActionListener(e -> resetGame());
        bottom.add(resetButton, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        if (!b.getText().equals("")) return;

        b.setText(xTurn ? "X" : "O");
        b.setForeground(xTurn ? new Color(0, 200, 255) : new Color(255, 100, 100));
        xTurn = !xTurn;
        statusLabel.setText((xTurn ? "X" : "O") + "'s Turn");
        checkWinner();
    }

    private void checkWinner() {
        int[][] wins = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : wins) {
            String a = buttons[combo[0]].getText();
            String b = buttons[combo[1]].getText();
            String c = buttons[combo[2]].getText();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                highlightWin(combo);
                statusLabel.setText(a + " Wins 🎉");
                disableAll();
                return;
            }
        }

        boolean draw = true;
        for (JButton btn : buttons)
            if (btn.getText().equals("")) draw = false;

        if (draw) {
            statusLabel.setText("Draw 🤝");
        }
    }

    private void highlightWin(int[] combo) {
        for (int i : combo)
            buttons[i].setBackground(new Color(0, 200, 83));
    }

    private void disableAll() {
        for (JButton b : buttons)
            b.setEnabled(false);
    }

    private void resetGame() {
        for (JButton b : buttons) {
            b.setText("");
            b.setBackground(new Color(48, 48, 48));
            b.setEnabled(true);
        }
        xTurn = true;
        statusLabel.setText("X's Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
