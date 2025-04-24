package br.com.dio.service;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class BoardService {

    private static Board board;
    final static int BOARD_LIMIT = 9;

    public static boolean boardIsNull(){
        return isNull(board);
    }

    public static void startGame(final Map<String, String> positions) {
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
    }

    public static List<List<Space>> getSpaces(){
        return board.getSpaces();
    }
    public static boolean changeValue(final int col, final int row, final int value){
        return board.changeValue(col, row, value);
    }

}
