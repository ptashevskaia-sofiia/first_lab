package first_lab;

import java.util.Scanner;

import first_lab.Complex;
import first_lab.Matrix;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1 — Сложение");
            System.out.println("2 — Вычитание");
            System.out.println("3 — Умножение");
            System.out.println("4 — Обратная матрица");
            System.out.println("5 — Деление (A / B = A * B⁻¹)");
            System.out.println("6 — Определитель");
            System.out.println("0 — Выход");
            System.out.print("Выберите операцию: ");

            int choice = sc.nextInt();

            if (choice == 0) {
                break;
            }

            try {
                Matrix m1;
                Matrix m2;
                Matrix res;

                if (choice == 4) {
                    m1 = readMatrix(sc, "первой");

                    res = Matrix.inverseMatrix(m1);

                    System.out.println("Обратная матрица:\n" + res);

                    Matrix check = Matrix.multiply(m1, res);

                    System.out.println("Проверка (A * A⁻¹):\n" + check);

                } else if (choice == 5) {
                    m1 = readMatrix(sc, "делимого (A)");
                    m2 = readMatrix(sc, "делителя (B)");

                    res = Matrix.divideMatrix(m1, m2);

                    System.out.println("Результат деления (A / B):\n" + res);

                } else if (choice == 6) {
                    m1 = readMatrix(sc, "матрицы");

                    Complex determinant = Matrix.det(m1);

                    System.out.println("Определитель матрицы: " + determinant);

                } else if (choice >= 1 && choice <= 3) {
                    m1 = readMatrix(sc, "первой");
                    m2 = readMatrix(sc, "второй");

                    res = switch (choice) {
                        case 1 -> Matrix.add(m1, m2);
                        case 2 -> Matrix.subtract(m1, m2);
                        case 3 -> Matrix.multiply(m1, m2);
                        default -> throw new IllegalStateException();
                    };

                    String opName = switch (choice) {
                        case 1 -> "Сложение";
                        case 2 -> "Вычитание";
                        case 3 -> "Умножение";
                        default -> "";
                    };

                    System.out.println(
                        opName + ":\n" + res
                    );

                } else {
                    System.out.println("Ошибка: неизвестный пункт меню");
                }

            } catch (IllegalArgumentException e) {System.out.println("Ошибка: " + e.getMessage());
            } catch (ArithmeticException e) {System.out.println("Ошибка: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static Matrix readMatrix(
        Scanner sc,
        String name
    ) {
        System.out.print("Введите количество строк для " + name + ": ");
        int rows = sc.nextInt();

        System.out.print("Введите количество столбцов для " + name + ": ");
        int cols = sc.nextInt();

        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Количество строк и столбцов должно быть больше 0");
        }

        Complex[][] data =
            new Complex[rows][cols];

        System.out.println("Введите элементы " + name + " (вещественная часть, мнимая часть):");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf(
                    "Элемент [%d][%d]: ",i,j);

                double re = sc.nextDouble();
                double im = sc.nextDouble();

                data[i][j] =
                    new Complex(re, im);
            }
        }

        return new Matrix(data);
    }
}