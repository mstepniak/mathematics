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

        Double[][] function = calculateFunction(arrayW0, arrayW1, arrayW2, x0);
        Double[][] functionPlus = calculateFunction(arrayW0,arrayW1,arrayW2,x0PlusEps);
        Double[][] functionMinus = calculateFunction(arrayW0,arrayW1,arrayW2,x0MinusEps);

        System.out.println("Value of function is " + Arrays.deepToString(function));

        Double[][] dfdx2 = arrayW2;
        Double[][] dz1dx0 = arrayW0;
        Double[][] dz2dx1 = arrayW1;
        Double[][] dotX1 = hadamardProduct(arrayW1, scalarMinusMatrix(arrayW1, 1.0));
        Double[][] dotX2 = hadamardProduct(arrayW2, scalarMinusMatrix(arrayW2, 1.0));

        Double[][] dx1dz1 = diagonalMatrix(dotX1);
        Double[][] dx2dz2 = diagonalMatrix(dotX2);

        Double[][] temp1 = matrixMultiply(dfdx2, dx2dz2);
        Double[][] temp2 = matrixMultiply(temp1, dz2dx1);
        Double[][] temp3 = matrixMultiply(temp2, dx1dz1);
        Double[][] finalResult = matrixMultiply(temp3, dz1dx0);
        System.out.println("Value of df/dx0 is " + Arrays.deepToString(finalResult));
        Double[][] finiteDiffenceAprox = finiteDiffAprox(functionPlus, functionMinus);
        System.out.println("Value of approximation is " + Arrays.deepToString(finiteDiffenceAprox));

        double sumOfFirstColumn = sumElementsOfRow(finalResult, 0);
        double sumOfSecondColumn = sumElementsOfRow(finalResult, 1);
        System.out.println("Value of first derivative is " + sumOfFirstColumn);
        System.out.println("Value of second derivative is " + sumOfSecondColumn);

    }

    private static double sumElementsOfRow(Double[][] A, int column){
        double result = 0.0000;
        for (Double[] aA : A) {
            result += aA[column];
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
        Double[][] result = new Double[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                result[i][j] = 0.00000;
            }
        }

        for (int r = 0; r < aRows; r++) {
            for (int c = 0; c < aRows; c++) {
                if (r == c) {
                    if (rowMatrix[r] == null) {
                        result[r][c] = 0.00000;
                    } else {
                        result[r][c] = rowMatrix[r][c];
                    }
                } else {
                    result[r][c] = 0.00000;
                }
            }
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
                result[r][c] = 1 / (Math.pow(Math.exp(1.0), -Z[r][c]) + 1);
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