package first_lab;

public class Matrix {

    private Complex[][] elements;
    private int rows;
    private int cols;

    // Конструкторы
    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Количество строк и столбцов должно быть больше 0");
        }

        this.rows = rows;
        this.cols = cols;
        this.elements = new Complex[rows][cols];

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                elements[i][j] = new Complex();
            }
        }
    }

    public Matrix(Complex[][] elements) {
        if (elements == null || elements.length == 0 || elements[0].length == 0) {
            throw new IllegalArgumentException("Матрица не может быть пустой");
        }

        this.rows = elements.length;
        this.cols = elements[0].length;
        this.elements = new Complex[rows][cols];

        for (int i = 0; i < rows; i++) {
            if (elements[i] == null || elements[i].length != cols) {
                throw new IllegalArgumentException(
                    "Все строки матрицы должны иметь одинаковую длину");
            }

            for (int j = 0; j < cols; j++) {
                if (elements[i][j] == null) {
                    throw new IllegalArgumentException(
                        "Элементы матрицы не могут быть null"
                    );
                }
                this.elements[i][j] = elements[i][j];
            }
        }
    }

    // Установка элемента
    public void setElement(int row, int col, Complex value) {
        checkIndex(row, col);

        if (value == null) {
            throw new IllegalArgumentException("Элемент не может быть null");
        }

        elements[row][col] = value;
    }

    // Вывод
    public void print() {
        System.out.print(this);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.append(elements[i][j]).append("  ");
            }
            result.append('\n');
        }

        return result.toString();
    }

    // Получение строк и столбцов
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    // Получение элемента
    public Complex getElement(int row, int col) {
        checkIndex(row, col);
        return elements[row][col];
    }

    private void checkIndex(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Индекcы выходят за границы матрицы");
        }
    }

    private static void checkSameSize(Matrix m1, Matrix m2) {
        if (m1.rows != m2.rows || m1.cols != m2.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
    }

    // Сложение
    public static Matrix add(Matrix m1, Matrix m2) {
        checkSameSize(m1, m2);

        Complex[][] newElements =
            new Complex[m1.rows][m1.cols];

        for (int i = 0; i < m1.rows; ++i) {
            for (int j = 0; j < m1.cols; ++j) {
                newElements[i][j] =
                    m1.elements[i][j].add(m2.elements[i][j]);
            }
        }

        return new Matrix(newElements);
    }

    // Вычитание
    public static Matrix subtract(Matrix m1, Matrix m2) {
        checkSameSize(m1, m2);

        Complex[][] newElements =
            new Complex[m1.rows][m1.cols];

        for (int i = 0; i < m1.rows; ++i) {
            for (int j = 0; j < m1.cols; ++j) {
                newElements[i][j] =
                    m1.elements[i][j].subtract(m2.elements[i][j]);
            }
        }

        return new Matrix(newElements);
    }

    // Умножение
    public static Matrix multiply(Matrix m1, Matrix m2) {
        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException("Число столбцов первой матрицы должно совпадать с числом строк второй");
        }

        Complex[][] newElements =
            new Complex[m1.rows][m2.cols];

        for (int i = 0; i < m1.rows; ++i) {
            for (int j = 0; j < m2.cols; ++j) {
                Complex sum = new Complex();

                for (int k = 0; k < m1.cols; ++k) {
                    sum = sum.add(
                        m1.elements[i][k].multiply(m2.elements[k][j])
                    );
                }

                newElements[i][j] = sum;
            }
        }

        return new Matrix(newElements);
    }

    // Транспонирование
    public static Matrix transpose(Matrix m) {
        Complex[][] newElements =
            new Complex[m.cols][m.rows];

        for (int i = 0; i < m.cols; ++i) {
            for (int j = 0; j < m.rows; ++j) {
                newElements[i][j] = m.elements[j][i];
            }
        }

        return new Matrix(newElements);
    }

    // Минор
    public static Matrix getMinor(Matrix m, int row, int col) {
        if (m.rows <= 1 || m.cols <= 1) {
            throw new IllegalArgumentException("Минор матрицы 1x1 не может быть представлен обычной Matrix");
        }

        Complex[][] minor =new Complex[m.rows - 1][m.cols - 1];

        int ni = 0;

        for (int i = 0; i < m.rows; ++i) {
            if (i == row) {
                continue;
            }

            int nj = 0;

            for (int j = 0; j < m.cols; ++j) {
                if (j == col) {
                    continue;
                }

                minor[ni][nj] = m.elements[i][j];
                ++nj;
            }

            ++ni;
        }

        return new Matrix(minor);
    }

    // Определитель
    public static Complex det(Matrix m) {
        if (m.rows != m.cols) {
            throw new IllegalArgumentException(
                "Матрица не квадратная"
            );
        }

        if (m.rows == 1) {
            return m.elements[0][0];
        }

        if (m.rows == 2) {
            Complex a1 =
                m.elements[0][0].multiply(m.elements[1][1]);

            Complex a2 =
                m.elements[0][1].multiply(m.elements[1][0]);

            return a1.subtract(a2);
        }

        Complex sum = new Complex();

        for (int j = 0; j < m.cols; ++j) {
            int sign = (j % 2 == 0) ? 1 : -1;

            Matrix minor = getMinor(m, 0, j);

            Complex term =
                m.elements[0][j]
                    .multiply(new Complex(sign))
                    .multiply(det(minor));

            sum = sum.add(term);
        }

        return sum;
    }

    // Матрица алгебраических дополнений
    public static Matrix algMatrix(Matrix m) {
        if (m.rows != m.cols) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }

        if (m.rows == 1) {
            return new Matrix(
                new Complex[][] {
                    { new Complex(1) }
                }
            );
        }

        Complex[][] result = new Complex[m.rows][m.cols];

        for (int i = 0; i < m.rows; ++i) {
            for (int j = 0; j < m.cols; ++j) {
                Matrix minor = getMinor(m, i, j);
                Complex minorDet = det(minor);

                int sign = ((i + j) % 2 == 0) ? 1 : -1;

                result[i][j] = new Complex(sign).multiply(minorDet);
            }
        }

        return new Matrix(result);
    }

    // Обратная матрица
    public static Matrix inverseMatrix(Matrix m) {
        if (m.rows != m.cols) {
            throw new IllegalArgumentException("Матрица не квадратная: обратная матрица не существует");
        }

        Complex d = det(m);

        if (d.getReal() == 0.0 && d.getImag() == 0.0) {
            throw new IllegalArgumentException("Определитель матрицы равен 0: матрица не имеет обратной");
        }

        if (m.rows == 1) {
            return new Matrix(
                new Complex[][] {
                    { new Complex(1).divide(m.elements[0][0]) }
                }
            );
        }

        Matrix algebraic = algMatrix(m);
        Matrix transposed = transpose(algebraic);
        Complex invDet = new Complex(1).divide(d);

        Complex[][] result = new Complex[m.rows][m.cols];

        for (int i = 0; i < m.rows; ++i) {
            for (int j = 0; j < m.cols; ++j) {
                result[i][j] =
                    transposed.elements[i][j].multiply(invDet);
            }
        }

        return new Matrix(result);
    }

    // Деление матриц
    public static Matrix divideMatrix(Matrix m1, Matrix m2) {
        if (m2.rows != m2.cols) {
            throw new IllegalArgumentException("Матрица B должна быть квадратной");
        }

        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException("Несовместимые размеры для умножения");
        }

        Matrix inverse = inverseMatrix(m2);
        return multiply(m1, inverse);
    }
}
