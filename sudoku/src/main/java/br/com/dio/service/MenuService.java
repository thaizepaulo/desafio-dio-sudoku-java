package br.com.dio.service;

import java.util.Map;
import java.util.Scanner;

import static br.com.dio.service.BoardService.*;
import static br.com.dio.template.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;

public class MenuService {

    private final static Scanner scanner = new Scanner(System.in);

    public static void createMenu(final Map<String, String> gameConfig) {
        var option = -1;
        while (true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGameMenu(gameConfig);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }

    private static void startGameMenu(final Map<String, String> gameConfig) {
        if (validateIfBoardIsNull()) return;

        startGame(gameConfig);
        System.out.println("O jogo foi iniciado.");
        showCurrentGame();
    }

    private static void inputNumber() {
        if (validateIfBoardIsNotNull()) return;

        System.out.println("Informe a coluna que em que o número será inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha que em que o número será inserido");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que vai entrar na posição [%s,%s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);
        if (!changeValue(col, row, value)){
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
            return;
        }
        System.out.printf("O número %s foi incluído na posição [%s,%s].\n", value, col, row);
        showCurrentGame();
    }

    private static void removeNumber() {
        if (validateIfBoardIsNotNull()) return;

        System.out.println("Informe a coluna que em que o número será removido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha que em que o número será removido");
        var row = runUntilGetValidNumber(0, 8);
        if (!clearValue(col, row)){
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
        }
        System.out.printf("O número da posição [%s,%s] foi excluído.\n", col, row);
        showCurrentGame();
    }

    private static void clearGame() {
        if (validateIfBoardIsNotNull()) return;

        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso?");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")){
            System.out.println("Informe 'sim' ou 'não'");
            confirm = scanner.next();
        }

        if(confirm.equalsIgnoreCase("sim")){
            reset();
        }
    }

    private static void showCurrentGame() {
        if (validateIfBoardIsNotNull()) return;

        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", getArgs());
    }

    private static Object[] getArgs() {
        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: getSpaces()){
                args[argPos ++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        return args;
    }

    private static void showGameStatus() {
        if (validateIfBoardIsNotNull()) return;

        System.out.printf("O jogo atualmente se encontra no status %s\n", getStatus().getLabel());
        if(hasErrors()){
            System.out.println("O jogo contém erros");
        } else {
            System.out.println("O jogo não contém erros");
        }
    }

    private static boolean validateIfBoardIsNull() {
        if (!boardIsNull()){
            System.out.println("O jogo já foi iniciado.");
            return true;
        }
        return false;
    }

    private static boolean validateIfBoardIsNotNull() {
        if (boardIsNull()){
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return true;
        }
        return false;
    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while (current < min || current > max){
            System.out.printf("Informe um número entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }

}
