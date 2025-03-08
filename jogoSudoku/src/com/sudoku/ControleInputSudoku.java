/**
 * Controle de entrada para células do jogo Sudoku
 *
 * Esta classe estende PlainDocument para controlar a entrada de dados nas células
 * do jogo Sudoku, garantindo que apenas valores válidos sejam inseridos.
 * Implementa as seguintes funcionalidades:
 *
 * <ul>
 * <li>Validação de entrada (apenas números de 1 a 9)
 * <li>Verificação de duplicatas em linhas e colunas
 * <li>Atualização do estado interno do jogo quando valores são inseridos ou removidos
 * <li>Atualização do status do jogo (Não iniciado, Incompleto, Completo)
 * <li>Exibição de mensagens de erro para entradas inválidas
 * </ul>
 *
 * A classe mantém referências ao componente da interface, à posição da célula na grade,
 * ao array de células do jogo e ao componente de exibição de status.
 *
 * @author dio.bradesco.bootcamp.desafio.sudoku
 * @version 1.0
 * @see javax.swing.text.PlainDocument
 * @see dio.bradesco.bootcamp.desafio.sudoku.CelulaSudoku
 * @see dio.bradesco.bootcamp.desafio.sudoku.GameStatus
 * @see dio.bradesco.bootcamp.desafio.sudoku.SudokuGUI
 */

package com.sudoku;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.*;

class ControleInputSudoku extends PlainDocument {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JTextField celulaInput;
    private CelulaSudoku[][] celulasSudoku;
    private JLabel statusField;
    private int row;
    private int column;
    private JFrame framePrincipal;

    public ControleInputSudoku(JTextField celulaInput, int r, int c, CelulaSudoku[][] celulasSudoku,
                               JLabel statusField, JFrame framePrincipal) {

        this.celulaInput = celulaInput;
        this.celulasSudoku = celulasSudoku;
        this.statusField = statusField;
        this.row = r;
        this.column = c;
        this.framePrincipal = framePrincipal;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;

        // Verifica se a string contém apenas um número entre 1 e 9
        if (str.matches("[1-9]") && getLength() == 0) {
            super.insertString(offset, str, attr);
            if (checaSevalido()) {
                atualizarValorStatus();
            }
            ;
        }
    }

    private boolean checaSevalido() throws BadLocationException {
        String text = celulaInput.getText();
        if (text.isEmpty() || text.isBlank()) {
            return true; // Se estiver vazio, não há o que validar
        }

        int value = Integer.parseInt(text);

        // Verifica duplicatas na linha
        for (int col = 0; col < SudokuGUI.SIZE; col++) {
            if (col != column && celulasSudoku[row][col].getValor() == value) {
                exibirErro("Erro: Valor " + value + " já existe na linha " + (row + 1) + "!");
                return false;
            }
        }

        // Verifica duplicatas na coluna
        for (int r = 0; r < SudokuGUI.SIZE; r++) {
            if (r != row && celulasSudoku[r][column].getValor() == value) {
                exibirErro("Erro: Valor " + value + " já existe na coluna " + (column + 1) + "!");
                return false;
            }
        }

        return true;
    }

    // Método para exibir erro e limpar a célula
    private void exibirErro(String mensagem) throws BadLocationException {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this.framePrincipal, mensagem, "Entrada inválida", JOptionPane.WARNING_MESSAGE);

            // Remover o valor inválido apenas após o usuário fechar o diálogo
            SwingUtilities.invokeLater(() -> {
                try {
                    super.remove(0, getLength()); // Agora a remoção ocorre somente após o usuário clicar em "OK"
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        if (SudokuGUI.isResetting) {
            super.remove(offs, len);
            return;
        }
        super.remove(offs, len);
        atualizarValorStatus();
    }

    private void atualizarValorStatus() throws BadLocationException {
        // Para cada célula, verifica se foi alterada e atualiza o valor e o status

        String text = celulaInput.getText();
        if (!text.isEmpty() && !text.isBlank()) {
            int value = Integer.parseInt(text); // Converte para número
            celulasSudoku[row][column].setValor(value);

        } else {
            celulasSudoku[row][column].setValor(0);

        }

        // Após todas as células serem atualizadas, chama o método para atualizar o
        // status do jogo
        atualizarStatusJogo();
    }

    private void atualizarStatusJogo() {
        boolean iniciado = false;
        boolean completo = true;
        final String status;
        // Verifica se todas as células estão preenchidas
        for (int row = 0; row < SudokuGUI.SIZE; row++) {
            for (int column = 0; column < SudokuGUI.SIZE; column++) {
                if (celulasSudoku[row][column].getValor() == 0) {
                    completo = false; // Se houver pelo menos uma célula vazia, não está completo
                } else if (!celulasSudoku[row][column].isFixo()) {
                    iniciado = true; // Pelo menos uma célula preenchida indica que foi iniciado
                }
            }
        }

        // Define o status baseado na lógica

        if (completo) {
            status = GameStatus.COMPLETO.toString();
        } else {
            status = iniciado ? GameStatus.INCOMPLETO.toString() : GameStatus.NAO_INICIADO.toString();
        }

        // Atualiza o campo de status na interface gráfica
        SwingUtilities.invokeLater(() -> statusField.setText(status));
    }

}