package Lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
//������ ���� ������� �� ��������� AVT
public class FractalExplorer{
    // ����� ��� ���� ���������
    private JFrame frame;
    // ����� ��� �������(������� ��� ���������)
    private JImageDisplay display;
    // ������ ���������� ����������
    private JButton button;
    // ������� ����
    private int sizeDisplay;
    // ��������� Mondelbrot
    private FractalGenerator generator;
    private Rectangle2D.Double tmp;

    /**
     * � ������������ ��������� ������, ��������������� ��������� �������� �����������
     * ��������������� �������� ������� ����� � ����������� � ������� ���������
     * @param sizeDisplay ������ ����������� ���� ����������
     */
    FractalExplorer(int sizeDisplay) {
        // ��������� ������� ������� �� ��������� ������������
        this.sizeDisplay = sizeDisplay;
        // ��������� ����������� � ���� ��������
        tmp = new Rectangle2D.Double();
        // ������������� ��������
        generator = new Mandelbrot();
        // ��������� ������������������� �������� �������
        generator.getInitialRange(tmp);
        // �������� ������
        button = new JButton("RESET");
        // �������� �������
        display = new JImageDisplay(sizeDisplay, sizeDisplay);
    }

    /**
     *  ��������� � ������������ ���������, ������������ "���������" ������� �� ������ � ������� ��� ������� ����
     *  ��������� �������� �� ��������� ��� �������� ����
     */
    public void createAndShowGUI() {
        // ��������� ������� ��� ������������ ��������� ������������ "������" �����
        display.setLayout(new BorderLayout());
        // ������������� ������ � ��������
        frame = new JFrame("���� 4");
        // ��������� ������ � �������
        frame.add(display, BorderLayout.CENTER);
        // ��������� ������ ��� ��������
        frame.add(button, BorderLayout.SOUTH);
        // ��������� �������� ��� ������� �� �������
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ������������ "���������" ������� �� ������
        button.addActionListener(new ButtonAction());
        // ������������ "���������" ������� ��� ������� ������ �� �������
        display.addMouseListener(new MouseAction());
        frame.pack();
        // �������� ������ ������� � ����������� ��� ������������
        frame.setVisible (true);
        frame.setResizable (false);
    }

    /**
     * ��������� �������� �� �������. �������� ��� ������� � ������������� ���������� �������� �� �������.
     * ���� ��������� �� -1, �� ������ ������� �� ����������� �������������� �������, � ���� ����������� �� �������
     * � ������� �������� ����� � HSB �� RGN
     */
    public void drawFractal(){
        // ������� �� ������
        for (int x = 0; x < sizeDisplay; x++)
        {// ������� �� ������
            for (int y = 0; y < sizeDisplay; y++)
            {
                double xCoord = generator.getCoord(tmp.x,
                        tmp.x + tmp.width, sizeDisplay, x); // ����������� ���������� x � ����� � ��������� �������
                double yCoord = generator.getCoord(tmp.y,
                        tmp.y + tmp.height, sizeDisplay, y);// ����������� ���������� y � ����� � ��������� �������
                int iteration = generator.numIterations(xCoord, yCoord);// �������� ��������, ��� ��������� �������� ��������
                if (iteration == -1) { // ���� ������� �� ����� �� ������� ����������� �� 2000 ��������
                    display.drawPixel(x, y, 0); // ���������� ����� ���� ��� �������
                }
                else{
                    float hue = 0.7f + (float) iteration / 200f; // ����� ��������� �������� ���(hue) ��� ����� �������� 0.7 + �������� �������� / 200
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f); // ����������� �������� ��� � ����������� RGB ����
                    display.drawPixel(x, y, rgbColor); // �������� ������
                }
            }
        }
        display.repaint(); // ���������� ������� �� �����
    }

    /**
     * ��������� ������� ��� ������
     */
    private class ButtonAction implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            // ���� ������� ������ ��� RESET
            if (e.getActionCommand().equals("RESET")) {
                generator.getInitialRange(tmp); // �������� ������� � ������� ��� � ��������������� ����
                drawFractal(); // ���������� ������� � ������ �����������
            }
        }
    }

    /**
     * ��������� ������� ��� ����
     */
    private class MouseAction extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            // �������� ���������� x ��� ����
            int x = e.getX();
            double xCoord = generator.getCoord(tmp.x,
                    tmp.x + tmp.width, sizeDisplay, x); // ����������� ���������� x � ����� � ��������� �������

            // �������� ���������� y ��� ����
            int y = e.getY();
            double yCoord = generator.getCoord(tmp.y,
                    tmp.y + tmp.height, sizeDisplay, y); // ����������� ���������� y � ����� � ��������� �������


            generator.recenterAndZoomRange(tmp, xCoord, yCoord, 0.5); // ���������� ���� � ��� ����, �� ����������� ����

            drawFractal(); // ��������� ��������
        }
    }

    public static void main(String[] args) { // ����� �����
        FractalExplorer test = new FractalExplorer(600); // ������� ���� 600 �� 600
        test.createAndShowGUI();// ����� ������ ��� �������� ���������
        test.drawFractal(); // ��������� ��������
    }
}
