/**
 * Representa uma célula individual no tabuleiro de Sudoku
 *
 * Esta classe modela uma célula no jogo de Sudoku, mantendo seu valor numérico
 * e seu estado (fixo ou editável). Células fixas são aquelas preenchidas 
 * inicialmente pelo gerador de puzzles e não podem ser modificadas pelo jogador.
 *
 * Cada célula contém:
 * <ul>
 * <li>Um valor numérico (0 para células vazias, 1-9 para células preenchidas)
 * <li>Um indicador booleano que determina se a célula é fixa (não editável) ou não
 * </ul>
 *
 * A classe fornece métodos para acessar e modificar tanto o valor quanto o estado
 * da célula.
 *
 * @author dio.bradesco.bootcamp.desafio.sudoku
 * @version 1.0
 * @see dio.bradesco.bootcamp.desafio.sudoku.SudokuGUI
 * @see dio.bradesco.bootcamp.desafio.sudoku.ControleInputSudoku
 */

package com.sudoku;

public class CelulaSudoku {
    private int valor;
    private boolean fixo;

    public CelulaSudoku(int valor, boolean fixo) {
        this.valor = valor;
        this.fixo = fixo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isFixo() {
        return fixo;
    }

    public void setIsFixo(boolean fixo) {
        this.fixo = fixo;
    }
}