package Lab4;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {// Наследуемся от генератора фрактала, т.к. Манделброт частный случай всех фракталов

    public static final int MAX_ITERATIONS = 2000; // Ограничитель итерация, так как фрактал может отрисовываться бесконечно

    @Override
    public void getInitialRange(Rectangle2D.Double range) { // Устанавливаем диапазон отрисовки,

        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    @Override
    public int numIterations(double x, double y) {
        // Переменная отвечающая за итерацию
        int iteration = 0;
        // Мнимая единица и реальное число
        double zreal = 0;
        double zimaginary = 0;

        /**
         * Вычисление Zn = Zn-1^2 + где значения являются комплексными числами
         * представленными как zreal and zimaginary, Z0=0, и точки во фрактале,
         * которые будут отображены (данны в виде X и Y).  цикл будет итерироваться
         * до Z^2 > 4 (значение по модулю Z больше чем 2) или до максимума итераций
         * который был определен ранее.
         */
        // Пока итерация не достигла максимума сумма квадратов мнимой единицы и реального значения меньше 4
        while (iteration < MAX_ITERATIONS && zreal * zreal + zimaginary * zimaginary < 4)
        {
            // Обновим новое значение реального значения прибавив x  к разнице реального значения z в квадрате и мнимой единицы в квадрате
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            // Обновим новое значение мнимой единицы по формуле 2 * реальное число * мнимую единицу + y
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;
            zreal = zrealUpdated; // установим новое значение в переменную отвечающую за реальное значение
            zimaginary = zimaginaryUpdated; // установим новую мнимую единицу в переменную отвечающую за мнимую единицу
            iteration += 1; // Увеличим счетчик итераций на 1
        }

        if (iteration == MAX_ITERATIONS) // Если достигли предела итераций
        {
            return -1; // Вернем -1
        }

        return iteration; // Если не попали в условие, то вернем итерацию
    }
}
