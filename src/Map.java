import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Map extends Pane {
    private int unit = 40, size;
    private int[][] map;
    private Position start;
    Rectangle rec;
    int i = 0;


    Map (String path) {
        File  file = new File(path);
        try {
            Scanner in = new Scanner(file); //Создали сканнер для читки файла
            while (in.hasNext()){
                String s = in.nextLine();
                if(s.length()<=2){ //задали данное условие чтобы прочитать первую строку файла,которая является размером карты
                    size = Integer.parseInt(s); //прочитав первую строку преобразовали ее из String в Int и указали её как размер карты
                    map = new int[getSize()][getSize()];// инициализоравали массив и указали его размер
                }else {
                    String[] space = s.split(" ");// Убрали ненужные пробелы из строки
                    for (int j = 0; j < map[0].length; j++)
                        map[i][j] = Integer.parseInt(space[j]);// Превращаем каждый элемент space array в Integer и даём значение элементу map
                    i++;
                }

            }
            //Заполняем массив и относительно цифры в строке преобразовываем его в rectangle
            for ( int i = 0; i < map.length; i++){
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] == 2) {
                        start = new Position(j, i);
                        rec = new Rectangle(unit* j,unit * i, getUnit(), getUnit());
                        rec.setFill(Color.WHITE);
                        rec.setStroke(Color.BLACK);
                    }
                    else if (map[i][j] == 0) {
                        rec = new Rectangle(unit * j,unit * i, getUnit(), getUnit());
                        rec.setFill(Color.WHITE);
                        rec.setStroke(Color.BLACK);
                    }
                    else {
                        rec = new Rectangle(unit * j,unit * i, getUnit(), getUnit());
                        rec.setFill(Color.BLACK);
                        rec.setStroke(Color.BLACK);
                    }
                    getChildren().add(rec);
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public int getUnit() {
        return unit;
    }

    public int getSize() {
        return size;
    }


    public Position getStartPosition() {
        return start;
    }
    // Получаем элемент индексов в map массиве
    public int getValueAt(int var3, int var2) {
        return map[var3][var2];
    }
}