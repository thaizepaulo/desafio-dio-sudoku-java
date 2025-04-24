package br.com.dio;

import java.util.stream.Stream;

import static br.com.dio.service.MenuService.createMenu;
import static java.util.stream.Collectors.toMap;

public class Main {

    public static void main(String[] args) {

        final var gameConfig = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));

        createMenu();

    }
}