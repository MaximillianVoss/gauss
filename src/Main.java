import gauss.Algorithm;
import gauss.MyEquation;
import gauss.LinearSystem;

import java.util.Scanner;

public class Main {
    private static int DEFAULT_EQUATIONS_NUMBER = 2;
    private static int DEFAULT_VARIABLES_NUMBER = 2;

    public static void main(String args[]) {
        System.out.println("Do you want manual input(Y/N)");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        if (answer.equals("Y")) {
            try {
                System.out.println("Enter equations number");
                DEFAULT_EQUATIONS_NUMBER = sc.nextInt();
                System.out.println("Enter variables number)");
                DEFAULT_VARIABLES_NUMBER = sc.nextInt();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Coefficients will be generated automatically!");
        LinearSystem<Float, MyEquation> list = generateSystem();
        printSystem(list);
        int i, j;
        Algorithm<Float, MyEquation> alg = new Algorithm<Float, MyEquation>(list);
        try {
            alg.calculate();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Float[] x = new Float[DEFAULT_EQUATIONS_NUMBER];
        for (i = list.size() - 1; i >= 0; i--) {
            Float sum = 0.0f;
            for (j = list.size() - 1; j > i; j--) {
                sum += list.itemAt(i, j) * x[j];
            }
            x[i] = (list.itemAt(i, list.size()) - sum) / list.itemAt(i, j);
        }
        printSystem(list);
        printVector(x);
    }

    public static LinearSystem<Float, MyEquation> generateSystem() {
        LinearSystem<Float, MyEquation> list = new LinearSystem<Float, MyEquation>();
        int i;
        for (i = 0; i < DEFAULT_EQUATIONS_NUMBER; i++) {
            MyEquation eq = new MyEquation();
            eq.generate(DEFAULT_VARIABLES_NUMBER + 1);
            list.push(eq);
        }
        return list;
    }

    public static void printSystem(LinearSystem<Float, MyEquation> system) {
        for (int i = 0; i < system.size(); i++) {
            MyEquation temp = system.get(i);
            String s = "";
            for (int j = 0; j < temp.size(); j++) {
                s += String.format("%f; %s", system.itemAt(i, j), "\t");
            }
            System.out.println(s);
        }
        System.out.println("");
    }

    public static void printVector(Float[] x) {
        String s = "";
        for (int i = 0; i < x.length; i++) {
            s += String.format("x%d = %f; ", i, x[i]);
        }
        System.out.println(s);
    }
}
