package test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static main.MatrixOperations.*;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class MatrixOperationsTest {

    private final static Double[][] matrix2d = {{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}};
    private final static Double[] matrix1d = {1.0, 2.0, 3.0};

    @Test
    public void sumElementsOfRowTest() {
        //given
        double expectedResult = 6.0;
        boolean check = false;
        double eps = 0.00004;

        //when
        double actualResult = sumElementsOfRow(matrix2d, 0);
        if (actualResult - expectedResult < eps) {
            check = true;
        }

        //then
        Assert.assertTrue(check);
    }

    @Test
    public void matrixMultiply1DTest() {
        //given
        String expectedResult = "[[14.0], [14.0], [14.0]]";

        //when
        Double[][] result = matrixMultiply1D(matrix2d, matrix1d);
        String actualResult = Arrays.deepToString(result);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void hadamardProductTest() {
        //given
        String expectedResult = "[[1.0, 4.0, 9.0], [1.0, 4.0, 9.0], [1.0, 4.0, 9.0]]";

        //when
        Double[][] result = hadamardProduct(matrix2d, matrix2d);
        String actualResult = Arrays.deepToString(result);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void matrixMultiplyTest() {
        //given
        String expectedResult = "[[6.0, 12.0, 18.0], [6.0, 12.0, 18.0], [6.0, 12.0, 18.0], [6.0, 12.0, 18.0], [6.0, 12.0, 18.0]]";
        Double[][] A = {{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}};

        //when
        Double[][] result = matrixMultiply(A, matrix2d);
        String actualResult = Arrays.deepToString(result);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }
}
