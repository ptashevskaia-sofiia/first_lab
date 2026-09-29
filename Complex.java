package first_lab;

public class Complex {

    private double real;
    private double imag;

    // Конструкторы
    public Complex() {
        this.real = 0.0;
        this.imag = 0.0;
    }

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public Complex(double real) {
        this.real = real;
        this.imag = 0.0;
    }

    // Получить части комплексного числа
    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    // Сложение
    public static Complex add(Complex c1, Complex c2) {
        return new Complex(
            c1.real + c2.real,
            c1.imag + c2.imag
        );
    }

    public Complex add(Complex c) {
        return new Complex(
            this.real + c.real,
            this.imag + c.imag
        );
    }

    // Вычитание
    public static Complex subtract(Complex c1, Complex c2) {
        return new Complex(
            c1.real - c2.real,
            c1.imag - c2.imag
        );
    }

    public Complex subtract(Complex c) {
        return new Complex(
            this.real - c.real,
            this.imag - c.imag
        );
    }

    // Умножение
    public static Complex multiply(Complex c1, Complex c2) {
        return new Complex(
            c1.real * c2.real - c1.imag * c2.imag,
            c1.real * c2.imag + c1.imag * c2.real
        );
    }

    public Complex multiply(Complex c) {
        return new Complex(
            this.real * c.real - this.imag * c.imag,
            this.real * c.imag + this.imag * c.real
        );
    }

    // Деление
    public Complex divide(Complex c) {
        double denominator = c.real * c.real + c.imag * c.imag;

        if (denominator < 1e-10) {
            throw new ArithmeticException("Деление на ноль (комплексный ноль)");
        }

        double newReal =
            (this.real * c.real + this.imag * c.imag) / denominator;

        double newImag =
            (this.imag * c.real - this.real * c.imag) / denominator;

        return new Complex(newReal, newImag);
    }

    public static Complex divide(Complex c1, Complex c2) {
        return c1.divide(c2);
    }

    @Override
    public String toString() {
        String sign = (imag >= 0) ? "+" : "-";
        return String.format("(%.2f %s %.2fi)", real, sign, Math.abs(imag));
    }
}
