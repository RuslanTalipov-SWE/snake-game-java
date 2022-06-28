import java.awt.Color;
import java.awt.Graphics;

//базовый объект сцены
public abstract class Base {

    protected int x, y; //координаты начала
    protected int size; //размеры по x и y
    protected int vx, vy; //скорости перемещения по осям

    //конструктор
    public Base(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        vx = vy = 0;
    }

    //изменение цвета
    public abstract void setColor(Color c);

    //движение
    public abstract void move();

    //отрисовка
    public abstract void draw(Graphics g);
}