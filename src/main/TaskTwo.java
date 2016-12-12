package main;

import java.util.Arrays;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class TaskTwo {

    public static void main(String[] args) {
        System.out.println("Task 2");

        double eps = Math.pow(10.0, -6.0);
        int[] hArray = {5, 10, 10, 2};

        Double[][] arrayW0 = GenerateMatrix.listTwoDim(hArray[1], hArray[0]);
        Double[][] arrayW1 = GenerateMatrix.listTwoDim(hArray[2], hArray[1]);
        Double[][] arrayW2 = GenerateMatrix.listTwoDim(hArray[3], hArray[2]);

        Double x0[] = GenerateMatrix.listOneDim(5);
        Double[] x0PlusEps = addOrSubtractScalar(x0, eps, 0);
        Double[] x0MinusEps = addOrSubtractScalar(x0, eps, 1);

        System.out.println("x0: " + Arrays.deepToString(x0));

        Double[][] functionPlus = calculateFunction(arrayW0,arrayW1,arrayW2,x0PlusEps);
        Double[][] functionMinus = calculateFunction(arrayW0,arrayW1,arrayW2,x0MinusEps);

        Double[][] z1 = matrixMultiply1D(arrayW0, x0);
        Double[][] sigma1 = sigmaEquation(z1);
        Double[][] x1 = matrixMultiply(arrayW1, sigma1);
        Double[][] sigma2 = sigmaEquation(x1);
        Double[][] function = matrixMultiply(arrayW2, sigma2);

        System.out.println("Value of function is " + Arrays.deepToString(function));

        Double[][] hadamardX1 = hadamardProduct(sigma1, scalarMinusMatrix(sigma1, 1.0));
        Double[][] hadamardX2 = hadamardProduct(sigma2, scalarMinusMatrix(sigma2, 1.0));

        Double[][] dx1dz1 = diagonalMatrix(hadamardX1);
        Double[][] dx2dz2 = diagonalMatrix(hadamardX2);

        Double[][] temp1 = matrixMultiply(dx1dz1, arrayW0);
        Double[][] temp2 = matrixMultiply(arrayW1, temp1);
        Double[][] temp3 = matrixMultiply(dx2dz2, temp2);
        Double[][] finalResult = matrixMultiply(arrayW2, temp3);

        System.out.println("Value of df/dx0 is " + Arrays.deepToString(finalResult));
        Double[][] finiteDiffenceAprox = finiteDiffAprox(functionPlus, functionMinus);
        System.out.println("Value of approximation is " + Arrays.deepToString(finiteDiffenceAprox));

        double sumOfFirstRow = sumElementsOfRow(finalResult, 0);
        double sumOfSecondRow = sumElementsOfRow(finalResult, 1);
        System.out.println("Value of first derivative is " + sumOfFirstRow);
        System.out.println("Value of second derivative is " + sumOfSecondRow);

        if (sumOfFirstRow-finiteDiffenceAprox[0][0]< 0.0004 && sumOfSecondRow-finiteDiffenceAprox[1][0]< 0.0004) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }

    private static double sumElementsOfRow(Double[][] A, int column){
        double result = 0.0000;
        for (int i =0; i<A[0].length; i++) {
            result += A[column][i];
        }
        return result;
    }

    public static Double[][] matrixMultiply1D(Double[][] A, Double[] B) {
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = 1;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bRows
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k];
                }
            }
        }
        return C;
    }

    private static Double[][] calculateFunction(Double[][] arrayW0, Double[][] arrayW1, Double[][] arrayW2, Double[] x0) {

        Double[][] z1 = matrixMultiply1D(arrayW0, x0);
        Double[][] sigma1 = sigmaEquation(z1);
        Double[][] x1 = matrixMultiply(arrayW1, sigma1);
        Double[][] sigma2 = sigmaEquation(x1);

        return matrixMultiply(arrayW2, sigma2);
    }

    private static Double[][] finiteDiffAprox(Double[][] plus, Double[][] minus) {
        double eps = Math.pow(10.0, -6.0);
        int aRows = plus.length;
        int aColumns = plus[0].length;
        Double[][] result = new Double[aRows][aColumns];

        for (int r = 0; r < aRows; r++) {
            for (int c = 0; c < aColumns; c++) {
                result[r][c] = (plus[r][c] - minus[r][c]) / (2 * eps);
            }
        }
        return result;
    }

    private static Double[][] diagonalMatrix(Double[][] rowMatrix) {
        int aRows = rowMatrix.length;
        Double[][] result = new Double[aRows][aRows];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < aRows; j++) {
                result[i][j] = 0.00000;
            }
        }
        for (int i = 0; i<aRows;i++){
            result[i][i] = rowMatrix[i][0];
        }
        return result;
    }

    private static Double[][] scalarMinusMatrix(Double[][] A, Double scalar) {

        int aRows = A.length;
        int aColumns = A[0].length;
        Double[][] result = new Double[aRows][aColumns];

        for (int r = 0; r < aRows; r++) {
            for (int c = 0; c < aColumns; c++) {
                result[r][c] = scalar - A[r][c];
            }
        }
        return result;
    }

    private static Double[][] hadamardProduct(Double[][] A, Double[][] B) {
        int aRows = A.length;
        int aColumns = A[0].length;
        Double[][] result = new Double[aRows][aColumns];

        for (int r = 0; r < aRows; r++) {
            for (int c = 0; c < aColumns; c++) {
                result[r][c] = A[r][c] * B[r][c];
            }
        }
        return result;
    }

    /**
     * @param A      matrix.
     * @param scalar constant to add or subtract.
     * @param i      select 0 for adding and 1 for subtraction.
     * @return new matrix
     */
    private static Double[] addOrSubtractScalar(Double[] A, Double scalar, int i) {
        int aRows = A.length;
        Double[] result = new Double[aRows];

        for (int r = 0; r < aRows; r++) {
            if (i == 0) {
                result[r] = A[r] + scalar;
            } else {
                result[r] = A[r] - scalar;
            }
        }
        return result;
    }

    private static Double[][] sigmaEquation(Double[][] Z) {
        int zRows = Z.length;
        int zColumns = Z[0].length;
        Double[][] result = new Double[zRows][zColumns];

        for (int r = 0; r < zRows; r++) {
            for (int c = 0; c < zColumns; c++) {
                result[r][c] = 1 / (Math.pow(Math.E, -Z[r][c]) + 1);
            }
        }
        return result;
    }

    private static Double[][] scalarMultiply(Double[][] A, Double scale) {

        int aRows = A.length;
        int aColumns = A[0].length;
        Double[][] result = new Double[aRows][aColumns];

        for (int r = 0; r < aRows; r++) {
            for (int c = 0; c < aColumns; c++) {
                result[r][c] = A[r][c] * scale;
            }
        }
        return result;
    }

    public static Double[][] matrixMultiply(Double[][] A, Double[][] B) {
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
}