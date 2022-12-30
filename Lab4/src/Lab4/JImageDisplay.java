package Lab4;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends javax.swing.JComponent // ����� ��� ��������� ����
{
    private BufferedImage test; // ��� �������� ����������� � ��� ��������� ���������� ����� BufferedImage, � �� Image

    public JImageDisplay(int width, int height) // ����������� � ���������� ������� ����
    {
        // ������������� ������� ���� � �������� ������� RGB
        test = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        super.setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) { // ���������� ������� ��������� ����, ����� �������� � Frame ��������
        super.paintComponent(g);
        // ��������� � ��������� ����������� � ������������� ���������� � ������� (������ � - 0 � - 0, ������ � ������
        // �����-��, ��� � �����������
        g.drawImage(test, 0, 0, test.getWidth(),test.getHeight(), null);
    }

    public void clearImage() // ������� �����������. � ������� �� ������������
    {
        int[] pixels = new int[getHeight() * getWidth()];
        test.setRGB(0,0,getWidth(),getHeight(), pixels, 0, 1);
    }

    public void drawPixel(int x, int y, int rgbColor) // ��������� ��������, �� ���� ��������� ����� � ������� �� �������
    {
        test.setRGB(x,y,rgbColor);
    }
}
