package at.kaindorf.Theorie_Thread.ÜBUNGEN.su_doku_96;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SudokuLauncher {

    public int[][] readGameGrid(int gridNumber) throws IOException {
        char[] fieldInChar = Files.readString(Path.of("src/main/java/at/kaindorf/Theorie_Thread/ÜBUNGEN/su_doku_96/p096_sudoku.txt"))
                .split("Grid")[gridNumber]
                .substring(4)
                .toCharArray();

//        for(char c : fieldInChar){
//            System.out.printf("%c", c);
//        }

        int[][] field = new int[9][9];
//
        int j = 0;
        int k = 0;
        for(int i = 0;i < fieldInChar.length; i++){
            if(fieldInChar[i] == '\n'){
                System.out.println(fieldInChar[i+1]);
                j++;
                k++;
                i++;
            }

            if(i >= field[0].length-1){
                k = 0;
            }

            field[j][k] = fieldInChar[i];
            k++;
        }

        for(int i = 0; i < field.length;i++){
            for(int l = 0;l < field[0].length;l++){
                System.out.printf("%d", field[i][l]);
            }
            System.out.println("");
        }

//        int j = 0;
//        for(int i = 0; i < fieldInString.length(); i++){
//            field[j, i];
//            if(i)
//        }
//        for(int i = 0; i < field.length; i++){
//            for(int j = 0; j < field[0].length; j++){
//                System.out.println(String.valueOf(field[i][j]));
//            }
//        }


//        System.out.println("|" + fieldInChar + "|");
        return null;
    }

    public void runWorkers(){
        int[][] stage;

        try {
            stage = readGameGrid(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new SudokuLauncher().runWorkers();
    }
}
