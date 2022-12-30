package Lab4;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends javax.swing.JComponent //  ласс дл€ отрисовки окна
{
    private BufferedImage test; // ƒл€ создани€ изображени€ и его изменени€ используем класс BufferedImage, а не Image

    public JImageDisplay(int width, int height) //  онструктор с установкой размера окна
    {
        // ”станавливаем размеры окна и цветовую политру RGB
        test = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        super.setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) { // ѕерегрузка базовой отрисовки окна, чтобы добавить в Frame картинку
        super.paintComponent(g);
        // добавл€ем в отрисовку изображение и устанавливаем координаты и размеры (коорды х - 0 у - 0, высота и ширина
        // така€-же, что у изображени€
        g.drawImage(test, 0, 0, test.getWidth(),test.getHeight(), null);
    }

    public void clearImage() // ќчистка изображени€. ¬ проекте не используетс€
    {
        int[] pixels = new int[getHeight() * getWidth()];
        test.setRGB(0,0,getWidth(),getHeight(), pixels, 0, 1);
    }

    public void drawPixel(int x, int y, int rgbColor) // ќтрисовка пикселей, то есть установка цвета в пикселе по коордам
    {
        test.setRGB(x,y,rgbColor);
    }
}
