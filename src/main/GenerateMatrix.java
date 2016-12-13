package main;

import java.util.Arrays;
import java.util.Random;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
class GenerateMatrix {

    private static Double[] oneDimArray;
    private static Double[][] twoDimArray;
    //private static final int size = 5;

    public static void printOneDim() {
        for (double n : oneDimArray) {
            System.out.println(n + " ");
        }
    }

    static Double[] listOneDim(int size) {
        oneDimArray = new Double[size];
        for (int i = 0; i < oneDimArray.length; i++) {
            oneDimArray[i] = randomFill();
        }
        return oneDimArray;
    }

    public static void printTwoDim() {
        System.out.println(Arrays.deepToString(twoDimArray));
    }

    static Double[][] listTwoDim(int sizeX, int sizeY) {
        twoDimArray = new Double[sizeX][sizeY];
        for (int r = 0; r < sizeX; r++) {
            for (int c = 0; c < sizeY; c++) {
                twoDimArray[r][c] = randomFill();
            }
        }
        return twoDimArray;
    }

    private static Double randomFill() {

        Random rand = new Random();
        return rand.nextGaussian();
    }
}
