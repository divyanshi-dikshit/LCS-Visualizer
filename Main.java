import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("LCS Visualizer");
        frame.setSize(900, 650);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Enter String X:");
        l1.setBounds(50, 30, 150, 30);
        frame.add(l1);

        JLabel l2 = new JLabel("Enter String Y:");
        l2.setBounds(50, 70, 150, 30);
        frame.add(l2);

        JTextField t1 = new JTextField();
        t1.setBounds(200, 30, 200, 30);
        frame.add(t1);

        JTextField t2 = new JTextField();
        t2.setBounds(200, 70, 200, 30);
        frame.add(t2);

        JButton btn = new JButton("Generate LCS");
        btn.setBounds(200, 110, 150, 30);
        frame.add(btn);

        JLabel result = new JLabel("Longest Common Subsequence:");
        result.setBounds(50, 150, 500, 30);
        result.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        frame.add(result);

        JLabel lcsBox = new JLabel();
        lcsBox.setBounds(50, 180, 300, 50);
        lcsBox.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
        lcsBox.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
        lcsBox.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(lcsBox);

        JTable table1 = new JTable();
        JScrollPane sp1 = new JScrollPane(table1);
        sp1.setBounds(50, 250, 350, 300);
        frame.add(sp1);

        JTable table2 = new JTable();
        JScrollPane sp2 = new JScrollPane(table2);
        sp2.setBounds(450, 250, 350, 300);
        frame.add(sp2);

        btn.addActionListener(e -> {

            String X = t1.getText();
            String Y = t2.getText();

            int n = X.length();
            int m = Y.length();

            int[][] dp = new int[n + 1][m + 1];
            String[][] dir = new String[n + 1][m + 1];

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        dir[i][j] = "↖";
                    } else if (dp[i - 1][j] > dp[i][j - 1]) {
                        dp[i][j] = dp[i - 1][j];
                        dir[i][j] = "↑";
                    } else {
                        dp[i][j] = dp[i][j - 1];
                        dir[i][j] = "←";
                    }
                }
            }

            StringBuilder lcs = new StringBuilder();
            int i = n, j = m;

            while (i > 0 && j > 0) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    lcs.append(X.charAt(i - 1));
                    i--;
                    j--;
                } else if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }

            String finalLCS = lcs.reverse().toString();
            result.setText("Longest Common Subsequence: " + finalLCS);
            lcsBox.setText(finalLCS);

            String[][] data1 = new String[n + 2][m + 2];
            String[] col1 = new String[m + 2];

            col1[0] = " ";
            col1[1] = " ";
            for (int c = 2; c <= m + 1; c++) {
                col1[c] = String.valueOf(Y.charAt(c - 2));
            }

            for (int r = 0; r <= n + 1; r++) {
                for (int c = 0; c <= m + 1; c++) {

                    if (r == 0 && c == 0)
                        data1[r][c] = " ";

                    else if (r == 0 && c > 1)
                        data1[r][c] = String.valueOf(Y.charAt(c - 2));

                    else if (c == 0 && r > 1)
                        data1[r][c] = String.valueOf(X.charAt(r - 2));

                    else if (r == 1 || c == 1)
                        data1[r][c] = "0";

                    else
                        data1[r][c] = String.valueOf(dp[r - 1][c - 1]);
                }
            }

            table1.setModel(new DefaultTableModel(data1, col1));

            String[][] data2 = new String[n + 2][m + 2];
            String[] col2 = new String[m + 2];

            col2[0] = " ";
            col2[1] = " ";
            for (int c = 2; c <= m + 1; c++) {
                col2[c] = String.valueOf(Y.charAt(c - 2));
            }

            for (int r = 0; r <= n + 1; r++) {
                for (int c = 0; c <= m + 1; c++) {

                    if (r == 0 && c == 0)
                        data2[r][c] = " ";

                    else if (r == 0 && c > 1)
                        data2[r][c] = String.valueOf(Y.charAt(c - 2));

                    else if (c == 0 && r > 1)
                        data2[r][c] = String.valueOf(X.charAt(r - 2));

                    else if (r == 1 || c == 1)
                        data2[r][c] = "0";

                    else
                        data2[r][c] = (dir[r - 1][c - 1] == null) ? "-" : dir[r - 1][c - 1];
                }
            }

            table2.setModel(new DefaultTableModel(data2, col2));
        });

        frame.setVisible(true);
    }
}