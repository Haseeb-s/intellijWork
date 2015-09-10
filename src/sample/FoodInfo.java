package sample;

/**
 * Created by Haseeb on 9/6/2015.
 */

import java.io.*;
import java.util.Arrays;

public class FoodInfo {
    public String[][] fileInfo;

    public static void main(String[] args) {
        FoodInfo test = new FoodInfo(0);
    }

    public FoodInfo(int v) {
        this.fileInfo = getInfo(v);
    }


    public String[][] getInfo(int v) {
        String[][] fileInfo = new String[10][4];
        if (v == 1) {
            try {
                FileInputStream fstream = new FileInputStream("Machine A.txt");
                BufferedReader read = new BufferedReader(new InputStreamReader(fstream));

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 4; j++) {
                        fileInfo[i][j] = read.readLine();
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (v != 1) {
            try {
                FileInputStream fstream = new FileInputStream("Machine B.txt");
                BufferedReader read = new BufferedReader(new InputStreamReader(fstream));
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 4; j++) {
                        fileInfo[i][j] = read.readLine();
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return fileInfo;
    }

    @Override
    public String toString() {
        return "FoodInfo{" +
                "fileInfo=" + Arrays.toString(fileInfo) +
                '}';
    }
}
