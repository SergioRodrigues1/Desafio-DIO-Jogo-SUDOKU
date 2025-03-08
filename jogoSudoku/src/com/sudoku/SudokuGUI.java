/**
 * Jogo de Sudoku com interface gráfica
 *
 * Esta classe implementa um jogo de Sudoku com interface gráfica Swing,
 * permitindo ao usuário jogar Sudoku em uma grade 9x9 com diferentes níveis
 * de dificuldade baseados no número de células vazias.
 *
 * O jogo gera automaticamente puzzles válidos de Sudoku, controla o estado do jogo,
 * e verifica quando o usuário completa o puzzle corretamente. A interface inclui
 * elementos visuais como:
 *   - Uma grade 9x9 com bordas especiais para destacar blocos 3x3
 *   - Status do jogo (Não iniciado, Incompleto, Completo)
 *   - Botões para reiniciar ou encerrar o jogo
 *   - Controle de entrada para permitir apenas valores válidos
 *
 * As células fixas (geradas inicialmente) aparecem em cinza e não são editáveis,
 * enquanto as células que o jogador deve preencher são brancas e editáveis.
 *
 * @author dio.bradesco.bootcamp.desafio.sudoku
 * @version 1.0
 * @see dio.bradesco.bootcamp.desafio.sudoku.generator.Generator
 * @see dio.bradesco.bootcamp.desafio.sudoku.generator.Grid
 * @see dio.bradesco.bootcamp.desafio.sudoku.GameStatus
 * @see dio.bradesco.bootcamp.desafio.sudoku.CelulaSudoku
 * @see dio.bradesco.bootcamp.desafio.sudoku.ControleInputSudoku
 */

package com.sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import com.sudoku.generator.Generator;
import com.sudoku.generator.Grid;


public class SudokuGUI {
    static final int SIZE = 9;
    public static boolean isResetting = false;
    // defina aqui o numero de células NAO FIXAS
    private final int NR_CELULAS_VAZIAS  = 48;
    /*
     * Puzzles mais fáceis tendem a ter mais números iniciais (25-30)
	   Puzzles moderados geralmente têm cerca de 20-24 números
       Puzzles difíceis podem ter apenas 17-19 números
     */

    private JFrame frame;
    private JTextField[][] celulasTF;
    private JLabel statusLabel;
    private JLabel statusField;
    private CelulaSudoku[][] celulasSudoku;

    public SudokuGUI() {
        frame = new JFrame("Jogo de Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE, 0, 0)); // Sem espaçamento automático
        celulasTF = new JTextField[SIZE][SIZE];
        celulasSudoku = new CelulaSudoku[9][9];

        gerarNovoJogo();
        statusField = new JLabel(GameStatus.NAO_INICIADO.toString());
        statusField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); // Adiciona um pouco de espaço à esquerda
        statusField.setForeground(new Color(250, 128, 114)); // salmão


        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                celulasTF[row][column] = new JTextField();
                celulasTF[row][column].setHorizontalAlignment(JTextField.CENTER);
                celulasTF[row][column].setFont(new Font("Arial", Font.BOLD, 20));
                celulasTF[row][column].setDocument(new ControleInputSudoku(celulasTF[row][column],
                        row,
                        column,
                        celulasSudoku,statusField,frame));

                if (celulasSudoku[row][column].getValor() != 0) {
                    celulasTF[row][column].setText(String.valueOf(celulasSudoku[row][column].getValor()));
                    celulasTF[row][column].setEditable(false);
                    celulasTF[row][column].setBackground(Color.LIGHT_GRAY);
                }

                // Criando as bordas para os blocos 3x3
                int top = (row % 3 == 0) ? 3 : 1;
                int left = (column % 3 == 0) ? 3 : 1;
                int bottom = (row == 8) ? 3 : ((row + 1) % 3 == 0 ? 3 : 1);
                int right = (column == 8) ? 3 : ((column + 1) % 3 == 0 ? 3 : 1);

                celulasTF[row][column].setBorder(new MatteBorder(top, left, bottom, right, Color.BLACK));
                gridPanel.add(celulasTF[row][column]);
            }
        }

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Status:");
        statusField.setOpaque(false);
        statusField.setBorder(null);

        statusPanel.add(statusLabel);
        statusPanel.add(statusField);

        JButton restartButton = new JButton("Reiniciar Jogo");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja reiniciar o jogo?",
                        "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    SudokuGUI.isResetting = true;
                    gerarNovoJogo();
                    atualizarGrid();
                    SudokuGUI.isResetting = false;
                }
            }
        });

        JButton endButton = new JButton("Terminar Jogo");
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja terminar o jogo?",
                        "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0); // Exemplo de término imediato
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(restartButton);
        buttonPanel.add(endButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(500, 600);
        frame.setVisible(true);

    }

    private void gerarNovoJogo() {
        Generator generator = new Generator();
        Grid grid = generator.generate(NR_CELULAS_VAZIAS);
        int size = grid.getSize();
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {

                if (celulasSudoku[row][column] == null) {
                    celulasSudoku[row][column] = new CelulaSudoku(grid.getCell(row, column).getValue(),
                            grid.getCell(row, column).getValue() != 0);
                } else {
                    celulasSudoku[row][column].setValor(grid.getCell(row, column).getValue());
                    celulasSudoku[row][column].setIsFixo(grid.getCell(row, column).getValue() != 0);

                }

            }
        }

    }

    private void atualizarGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (celulasSudoku[i][j].getValor() != 0) {
                    // zera jogo anterior
                    celulasTF[i][j].setText("");
                    celulasTF[i][j].setText(String.valueOf(celulasSudoku[i][j].getValor()));
                    celulasTF[i][j].setEditable(false);
                    celulasTF[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    celulasTF[i][j].setText("");
                    celulasTF[i][j].setEditable(true);
                    celulasTF[i][j].setBackground(Color.WHITE);
                }
				
				/* DEBUG
				 System.out.println("celulasSudoku[" + i + "][" + j + "] = "
				 + celulasSudoku[i][j].getValor() + " -" +
				 celulasSudoku[i][j].isFixo());
				 System.out.println("celulasTF[" + i + "][" + j + "] = "
				 + celulasTF[i][j].getText() + " -" +
				 celulasTF[i][j].isEditable());
				 */

            }
        }
        statusField.setText(GameStatus.NAO_INICIADO.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}