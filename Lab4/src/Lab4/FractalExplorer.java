package Lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
//импорт всех классов из библиотек AVT
public class FractalExplorer{
    // Фрейм для всех элементов
    private JFrame frame;
    // фрейм для дисплея(полотно для рисования)
    private JImageDisplay display;
    // Кнопка перегрузки факториала
    private JButton button;
    // Размеры окна
    private int sizeDisplay;
    // Генератор Mondelbrot
    private FractalGenerator generator;
    private Rectangle2D.Double tmp;

    /**
     * В конструкторе создается кнопка, иницилизируется генератор фрактала Манделброта
     * Устанавливаются значения размера экран и применяется к полотну отрисовки
     * @param sizeDisplay размер квадратного окна приложения
     */
    FractalExplorer(int sizeDisplay) {
        // установка размера дисплея из параметра конструктора
        this.sizeDisplay = sizeDisplay;
        // Установка ограничений в виде квадрата
        tmp = new Rectangle2D.Double();
        // Инициализация фрактала
        generator = new Mandelbrot();
        // Установка инициализированного значения квадрат
        generator.getInitialRange(tmp);
        // Создание кнопки
        button = new JButton("RESET");
        // Создание дисплея
        display = new JImageDisplay(sizeDisplay, sizeDisplay);
    }

    /**
     *  Установка и расположение элементов, прикрепление "слушателя" событий на кнопку и полотно при нажатии мыши
     *  Установка операции по умолчанию при закрытии окна
     */
    public void createAndShowGUI() {
        // Установка полотна для расположения элементов относительно "бортов" краев
        display.setLayout(new BorderLayout());
        // Инициализация фрейма с подписью
        frame = new JFrame("ЛАБА 4");
        // установка фрейма в дисплее
        frame.add(display, BorderLayout.CENTER);
        // установка кнопки под дисплеем
        frame.add(button, BorderLayout.SOUTH);
        // установка операции при нажатии на крестик
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // прикрепление "слушателя" событий на кнопку
        button.addActionListener(new ButtonAction());
        // прикрепление "слушателя" событий при нажатии мышкой по дисплею
        display.addMouseListener(new MouseAction());
        frame.pack();
        // Устновка фрейма видимым и недоступным для растягивания
        frame.setVisible (true);
        frame.setResizable (false);
    }

    /**
     * Отрисовка фрактала на полотне. Проходим все пиксели и подсичитываем количество итераций по формуле.
     * Если результат не -1, то рисуем пиксель по координатам расчитываемого пикселя, а цвет расчитываем по формуле
     * И поменяв цветовую схему с HSB на RGN
     */
    public void drawFractal(){
        // обходим по ширине
        for (int x = 0; x < sizeDisplay; x++)
        {// обходим по высоте
            for (int y = 0; y < sizeDisplay; y++)
            {
                double xCoord = generator.getCoord(tmp.x,
                        tmp.x + tmp.width, sizeDisplay, x); // Преобразуем координату x в число с плавающей запятой
                double yCoord = generator.getCoord(tmp.y,
                        tmp.y + tmp.height, sizeDisplay, y);// Преобразуем координату y в число с плавающей запятой
                int iteration = generator.numIterations(xCoord, yCoord);// Проводим итерации, для получения значения фрактала
                if (iteration == -1) { // Если фрактал не вышел за пределы ограничения за 2000 итераций
                    display.drawPixel(x, y, 0); // установить белый цвет для пикселя
                }
                else{
                    float hue = 0.7f + (float) iteration / 200f; // Иначе определим цветовой тон(hue) как сумму литерала 0.7 + значение итерации / 200
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f); // Преобразуем цветовой тон в стандартный RGB цвет
                    display.drawPixel(x, y, rgbColor); // Отрисуем символ
                }
            }
        }
        display.repaint(); // Перерисуем дисплей по новой
    }

    /**
     * Слушатель событий для кнопки
     */
    private class ButtonAction implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Если нажатая кнопка это RESET
            if (e.getActionCommand().equals("RESET")) {
                generator.getInitialRange(tmp); // Очистить масштаб и вернуть его к первоначальному виду
                drawFractal(); // Отрисовать фрактал с учетом ограничений
            }
        }
    }

    /**
     * Слушатель событий для мыши
     */
    private class MouseAction extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            // Получаем координаты x для Мыши
            int x = e.getX();
            double xCoord = generator.getCoord(tmp.x,
                    tmp.x + tmp.width, sizeDisplay, x); // Преобразуем координату x в число с плавающей запятой

            // Получаем координаты y для Мыши
            int y = e.getY();
            double yCoord = generator.getCoord(tmp.y,
                    tmp.y + tmp.height, sizeDisplay, y); // Преобразуем координату y в число с плавающей запятой


            generator.recenterAndZoomRange(tmp, xCoord, yCoord, 0.5); // Увеличение зума в два раза, по координатам мыши

            drawFractal(); // отрисовка фрактала
        }
    }

    public static void main(String[] args) { // Точка входа
        FractalExplorer test = new FractalExplorer(600); // Размеры окна 600 на 600
        test.createAndShowGUI();// Вызов метода для Создания элементов
        test.drawFractal(); // Отрисовка фрактала
    }
}
