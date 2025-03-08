# Desafio Sudoku - Bootcamp DIO-BRADESCO

Este projeto faz parte do desafio proposto pelo Bootcamp DIO-BRADESCO e consiste na implementação de um jogo de **Sudoku** com interface gráfica utilizando a biblioteca **Java Swing**. O objetivo é oferecer uma experiência de jogo interativa, com diferentes níveis de dificuldade baseados na quantidade de células vazias, além de garantir que o jogador siga as regras do Sudoku.

## Funcionalidades

- **Geração automática de puzzles válidos**: O gerador cria um tabuleiro de Sudoku válido para ser jogado.
- **Interface gráfica intuitiva**: A interface gráfica é construída utilizando Java Swing, com destaques visuais para os blocos 3x3.
- **Controle de entrada**: O sistema permite apenas a inserção de números de 1 a 9 e valida se os valores inseridos são válidos, evitando duplicatas nas linhas e colunas.
- **Verificação do estado do jogo**: O jogo acompanha o progresso e exibe mensagens com o status (não iniciado, incompleto, completo).
- **Opções para reiniciar ou encerrar o jogo**: O jogador pode reiniciar o jogo a qualquer momento ou finalizar quando completar o tabuleiro corretamente.

## Detalhes Técnicos

- **Gerador de Sudoku**: Implementado com base em classes de um repositório externo (a11n/sudoku).
- **Interface Gráfica (Swing)**: Exibição dinâmica do tabuleiro de Sudoku e interatividade com o jogador.
- **Validação de Entrada**: Somente números de 1 a 9 são aceitos, com restrição para evitar duplicação de valores nas linhas e colunas.
- **Estados do Jogo**: O jogo é capaz de acompanhar e exibir o status do progresso do jogador em tempo real.

### Exemplo de Imagem do Jogo

![Imagem do Jogo de Sudoku](https://github.com/SergioRodrigues1/Desafio-DIO-Jogo-SUDOKU/blob/main/teladoJogo.png)

## Estrutura do Projeto

```plaintext
├── src/
│   ├── com/sudoku/
│   │   ├── SudokuGUI.java          # Interface gráfica do jogo
│   │   ├── ControleInputSudoku.java # Controle de entrada do usuário
│   │   ├── CelulaSudoku.java        # Representação de uma célula do tabuleiro
│   │   ├── GameStatus.java          # Enumeração dos estados do jogo
│   │   ├── generator/
│   │   │   ├── Generator.java       # Lógica para geração do Sudoku
│   │   │   ├── Grid.java            # Representação da grade de Sudoku


Descrição dos Arquivos
SudokuGUI.java
Implementa a interface gráfica do jogo com Swing.
Gerencia o tabuleiro 9x9 e exibe o estado atual do jogo.
Oferece botões para reiniciar e encerrar o jogo.

ControleInputSudoku.java
Controla a entrada do usuário.
Estende PlainDocument para restringir a entrada de dados, aceitando apenas números de 1 a 9.
Impede a entrada de números duplicados nas mesmas linhas e colunas e atualiza o status do jogo.

CelulaSudoku.java
Representa uma célula individual no tabuleiro de Sudoku.
Cada célula pode ser fixa (não editável) ou editável pelo jogador.
Armazena o valor numérico e permite a manipulação do valor da célula.

GameStatus.java
Enumeração dos estados do jogo.
Define três estados possíveis:

NAO_INICIADO → O jogo foi criado, mas nenhum valor foi inserido.
INCOMPLETO → O jogador começou a preencher o tabuleiro.
COMPLETO → O tabuleiro foi preenchido corretamente.
Generator.java
Responsável pela lógica para geração do Sudoku.
Garante que o tabuleiro gerado seja válido e pode ser resolvido.

Grid.java
Representa a grade de Sudoku.
Organiza as células e permite manipulação do tabuleiro durante o jogo.

Como Executar o Jogo
1. Pré-requisitos:
Certifique-se de ter o Java JDK 8 ou superior instalado em seu computador.

2. Clonar o repositório:

Clone este repositório para o seu ambiente local:
   git clone https://github.com/SergioRodrigues1/Desafio-DIO-Jogo-SUDOKU.git
   cd Desafio-DIO-Jogo-SUDOKU/jogoSudoku

3. Compilar e Executar:

Compile e execute o código utilizando seu IDE favorita ou via terminal:
javac -d bin src/com/sudoku/*.java
java -cp bin com.sudoku.SudokuGUI

Jogando:
Após iniciar a aplicação, você verá o tabuleiro de Sudoku com algumas células já preenchidas. Você pode interagir com a interface para jogar e modificar o tabuleiro.
