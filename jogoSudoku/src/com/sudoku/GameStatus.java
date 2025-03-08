/**
 * Enumeration que representa os possíveis estados do jogo de Sudoku
 *
 * Esta enumeração define os três estados possíveis do jogo de Sudoku:
 * <ul>
 * <li>NAO_INICIADO: O jogo foi criado mas o jogador ainda não inseriu nenhum valor
 * <li>INCOMPLETO: O jogador começou a preencher o tabuleiro, mas ainda não terminou
 * <li>COMPLETO: O jogador preencheu todo o tabuleiro corretamente
 * </ul>
 *
 * Cada estado possui uma descrição textual que é exibida na interface do jogo.
 * O estado COMPLETO inclui uma mensagem de parabenização ao jogador.
 *
 * @author dio.bradesco.bootcamp.desafio.sudoku
 * @version 1.0
 * @see dio.bradesco.bootcamp.desafio.sudoku.SudokuGUI
 * @see dio.bradesco.bootcamp.desafio.sudoku.ControleInputSudoku
 */

package com.sudoku;

public enum GameStatus {
    NAO_INICIADO("NÃO INICIADO"), INCOMPLETO("INCOMPLETO"), COMPLETO("PARABÉNS!! VOCÊ COMPLETOU O JOGO");

    private final String descricao;

    GameStatus(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}