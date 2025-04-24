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
                case 4 -> showCurrentGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }

    private static void startGameMenu(final Map<String, String> gameConfig) {
        if (!boardIsNull()){
            System.out.println("O jogo já foi iniciado.");
            return;
        }

        startGame(gameConfig);
        System.out.println("O jogo foi iniciado.");
        showCurrentGame();
    }

    private static void showCurrentGame() {
        if (boardIsNull()){
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return;
        }

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

}
