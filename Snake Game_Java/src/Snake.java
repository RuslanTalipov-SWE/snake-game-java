import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//класс змеи которая будет перемещаться по полю
public class Snake extends Base {

    ArrayList<Block> lst; //змея состоящая из блоков
    Color cl; //цвет
    int len; //начальный размер змеи
    int w, h; //границы поля
    int score; //счет - кол-во съеденных мышей
    int bestscore; //рекорд, который берем из файла
    int lives; //кол-во жизней (3)
    boolean dir; //возможности сменить направление

    public Snake(int x, int y, int size, int len, Color cl, int w, int h) {
        super(x, y, size);
        this.len = len;
        this.w = w;
        this.h = h;
        this.cl = cl;
        vx = 1;
        vy = 0;
        dir = true;
        lst = new ArrayList<>();
        score = 0;
        lives = 3;
        loadScore();
        for (int i = 0; i < len; i++) {
            lst.add(new Block(x + i, y, size, cl)); //первый элемент это голова змеи
        }
    }

    //смена траектории
    public void changeDir(int vvx, int vvy) {
        //проверяем скорости
        if (vx == 1 && vvy != 0 || vx == -1 && vvy != 0 || vy == 1 && vvx != 0 || vy == -1 && vvx != 0) {
            if (dir) {
                vx = vvx;
                vy = vvy;
                dir = false; //меняем направление головы только один раз за шаг
            }
        }
    }

    //подгрузка рекорда из файла
    public void loadScore() {
        try {
            File f = new File("score.txt");
            //если файл существует и это не директория
            if (f.exists() && !f.isDirectory()) {
                try (BufferedReader br = new BufferedReader(new FileReader("score.txt"))) {
                    String line = br.readLine();
                    bestscore = Integer.parseInt(line);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                bestscore = 0;
                f.createNewFile();
                //пишем нуль
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("score.txt"))) {
                    bw.write(bestscore + "");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //перезапись рекорда
    public void saveScore() {
        //перезаписываем если текущий счет больше старого
        if (score > bestscore) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("score.txt"))) {
                //перезаписываем если текущий счет больше старого
                bw.write(score + "");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    //возврат рекорда
    public int getBestScore() {
        return bestscore;
    }

    //изменение цвета змеи
    @Override
    public void setColor(Color cl) {
        this.cl = cl;
        for (int i = 0; i < lst.size(); i++) {
            lst.get(i).setColor(this.cl);
        }
    }

    //получение цвета
    public Color getColor() {
        return cl;
    }

    //получаем длину змеи которую она наростила за игру
    public int getLength() {
        return lst.size() - len;
    }

    //получение счета
    public int getScore() {
        return score;
    }

    //изменение счета
    public void setScore(int value) {
        score = value;
    }

    //получение жизней
    public int getLives() {
        return lives;
    }

    //изменение жизней
    public void setLives(int value) {
        lives = value;
    }

    //получаем координаты головы
    public Point getCoord() {
        return new Point(lst.get(lst.size() - 1).x, lst.get(lst.size() - 1).y);
    }

    //остановка змеи
    public void stop() {
        vx = vy = 0;
    }

    //змейка не стоит на месте
    public boolean notStop() {
        return vx != 0 || vy != 0;
    }

    //змея растет от того что съела мышь
    public void grow(int x0, int y0) {
        Block head = new Block(x0, y0, size, cl);
        lst.add(head);
        score++;
    }

    //проверяем выход за границы - удар об стену
    public boolean boundsCollision() {
        //выход за границы справа или слева
        x = lst.get(lst.size() - 1).x;
        y = lst.get(lst.size() - 1).y;
        if (x + vx >= w) {
            x = 0;
        } else if (x + vx < 0) {
            x = w - 1;
        }
        //выход за границы снизу или сверху
        if (y + vy >= h) {
            y = 0;
        } else if (y + vy < 0) {
            y = h - 1;
        }
        if (x == lst.get(lst.size() - 1).x && y == lst.get(lst.size() - 1).y) {
            return false;
        } else {
            stop();
            return true; //конец игры
        }
    }

    //проверяем столкновение змеи со своим хвостом
    public boolean snakeCollision() {
        x = lst.get(lst.size() - 1).x;
        y = lst.get(lst.size() - 1).y;
        for (int i = lst.size() - 2; i >= 0; i--) {
            if (x + vx == lst.get(i).x && y + vy == lst.get(i).y) {
                stop();
                return true;
            }
        }
        return false;
    }

    //проверяем столкновение с препятствием
    public boolean wallsCollision(ArrayList<Block> walls) {
        x = lst.get(lst.size() - 1).x;
        y = lst.get(lst.size() - 1).y;
        for (int i = 0; i < walls.size(); i++) {
            if (x + vx == walls.get(i).x && y + vy == walls.get(i).y) {
                stop();
                return true;
            }
        }
        return false;
    }

    //проверяем столкновение с мышью
    public boolean MouseCollision(ArrayList<Block> mouses) {
        x = lst.get(lst.size() - 1).x;
        y = lst.get(lst.size() - 1).y;
        for (int i = 0; i < mouses.size(); i++) {
            if (x + vx == mouses.get(i).x && y + vy == mouses.get(i).y) {
                grow(x + vx, y + vy); //добавляем новую голову
                mouses.remove(i); //удаляем мышь
                return true;
            }
        }
        return false;
    }

    @Override
    public void move() {
        int oldx = lst.get(lst.size() - 1).x;
        int oldy = lst.get(lst.size() - 1).y;
        lst.get(lst.size() - 1).x += vx;
        lst.get(lst.size() - 1).y += vy;
        for (int i = lst.size() - 2; i >= 0; i--) {
            int tx = lst.get(i).x;
            int ty = lst.get(i).y;
            lst.get(i).x = oldx;
            lst.get(i).y = oldy;
            oldx = tx;
            oldy = ty;
        }
        dir = true;
    }

    @Override
    public void draw(Graphics g) {
        //начинаем с хвоста
        for (int i = lst.size() - 1; i >= 0; i--) {
            lst.get(i).draw(g);
        }
    }

}
