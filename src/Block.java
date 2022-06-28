import java.awt.Color;
import java.awt.Graphics;

//квадрат заданного цвета
public class Block extends Base {

    private Color cl;

    public Block(int x, int y, int size, Color cl) {
        super(x, y, size);
        this.cl = cl;
    }

    //изменение цвета
    @Override
    public void setColor(Color cl) {
        this.cl = cl;
    }

    //блок не двигается
    @Override
    public void move() {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    //но мы его можем нарисовать
    @Override
    public void draw(Graphics g) {
        int m = 1;
        g.setColor(cl);
        g.fillRect(x * size + m, y * size + m, size - m, size - m);
    }

}