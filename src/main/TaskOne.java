package main;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class TaskOne {

    private static final double eps = Math.pow(10.0, -6.0);
    private static final double testValue = Math.pow(10.0, -4.0);

    public static void main(String[] args) {

        Double[] arrayX = GenerateMatrix.listOneDim(5);
        //generateMatrix.printOneDim();
        Double[] arrayDx = new Double[5];
        Double[] jacobian = new Double[5];

        for (int i = 0; i < arrayX.length; i++) {
            arrayDx[i] = arrayX[i] * 2.0;
        }//end for
        System.out.println("arrayX:");

        for (double n : arrayX) {
            System.out.println(n + " ");
        }
        System.out.println("arrayDx:");

        for (double n : arrayDx) {
            System.out.println(n + " ");
        }
        for (int i = 0; i < jacobian.length; i++) {
            jacobian[i] = (Math.pow(arrayX[i]+eps, 2.0)-Math.pow(arrayX[i]-eps, 2.0))/2*eps;
            if (jacobian[i]< testValue) {
                System.out.println(true);
            }
            else {
                System.out.println(false);
            }
        }
    }

    public static class MyUnit {

        public String concatenate(String one, String two) {
            return one + two;
        }
    }
}
