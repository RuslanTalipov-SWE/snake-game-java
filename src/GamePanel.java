import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//панель с игрой
class GamePanel extends JPanel implements ActionListener, KeyListener {

    private static final Dimension PANEL_SIZE = new Dimension(460, 400); //размеры панели
    private static final int REFRESH_RATE = 300; //начальное обновление таймера
    private static final int GAME_WIDTH = 420; //ширина поля игры
    private static final int GAME_HEIGHT = 260; //высота поля игры
    private static final int GRID_SIZE = 20; //размер клетки игрового поля
    private Snake snake; //змейка - зеленая (по умолчанию)
    private Color snakeColor; //цвет змеи
    private Color backColor; //цвет фона
    private JLabel[] lbs; //под счет, жизни и текущий рекорд
    private ArrayList<Block> walls; //препятствия - серебряного цвета
    private ArrayList<Block> mouses; //мышь - красного цвета
    private JSlider len, level; //слайдеры для изменения начальной длины змеи и уровня
    private int beglvl, lvl, length; //для перезапуска
    private float mouseTime; //время генерации новой мыши
    private static final int maxMouses = 1; //макс. кол-во мышей на поле
    private Random rd; //для случайности
    private JButton control; //для старта/паузы

    //сразу создаем таймер
    private Timer timer = new Timer(REFRESH_RATE, this);

    //конструктор
    public GamePanel() {
        rd = new Random();
        snakeColor = new Color(0, 200, 0);
        backColor = new Color(240, 240, 240);
        createGUI();
        resetGame();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    //создание метки
    public JLabel createLabel(String text, int x, int y, int s1, int s2) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, s1, s2);
        return label;
    }

    //создание интерфейса управления в текущей панели
    public void createGUI() {

        //старт / пауза
        control = new JButton("Старт");
        control.setBounds(20, GAME_HEIGHT + 30, 70, 25);
        control.setFocusable(false);
        this.add(control);
        control.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //если таймер запущен
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    //если таймер выключен и змея может двигаться
                    if (snake.notStop()) {
                        timer.start();
                    }
                }
            }
        });

        //изменение начальной длины змейки
        len = new JSlider();
        len.setBounds(100, GAME_HEIGHT + 30, 80, 25);
        len.setFocusable(false);
        len.setMaximum(4);
        len.setMinimum(1);
        len.setValue(1);
        len.setMajorTickSpacing(1);
        this.add(len);
        this.add(createLabel("Размер змеи", 102, GAME_HEIGHT + 15, 100, 18));
        this.add(createLabel("1", 105, GAME_HEIGHT + 55, 10, 10));
        this.add(createLabel("4", 170, GAME_HEIGHT + 55, 10, 10));

        //начальный уровень - сложность
        level = new JSlider();
        level.setBounds(200, GAME_HEIGHT + 30, 60, 25);
        level.setFocusable(false);
        level.setMaximum(3);
        level.setMinimum(1);
        level.setValue(1);
        level.setMajorTickSpacing(1);
        this.add(level);
        this.add(createLabel("Уровень", 205, GAME_HEIGHT + 15, 100, 18));
        this.add(createLabel("1", 205, GAME_HEIGHT + 55, 10, 10));
        this.add(createLabel("3", 250, GAME_HEIGHT + 55, 10, 10));

        //перезапуск игры
        JButton start = new JButton("Сброс");
        start.setBounds(270, GAME_HEIGHT + 30, 70, 25);
        start.setFocusable(false);
        this.add(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        //цвет (змеи)
        JButton color = new JButton("Цвет змеи");
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                snakeColor = JColorChooser.showDialog(null, "Выбор цвета змеи", Color.RED);
            }
        });
        color.setBounds(50, GAME_HEIGHT + 80, 100, 23);
        color.setFocusable(false);
        this.add(color);

        //цвет задника
        JButton back = new JButton("Цвет фона");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backColor = JColorChooser.showDialog(null, "Выбор цвета фона", Color.RED);
            }
        });
        back.setBounds(200, GAME_HEIGHT + 80, 100, 23);
        back.setFocusable(false);
        this.add(back);

        //отображение счета, рекорда и жизней змеи
        lbs = new JLabel[3];
        lbs[0] = new JLabel("Рекорд: " + 0);
        lbs[1] = new JLabel("Счет: " + 0);
        lbs[2] = new JLabel("Жизни: " + 0);
        lbs[0].setBounds(430, 30, 100, 50);
        lbs[1].setBounds(430, 60, 100, 50);
        lbs[2].setBounds(430, 90, 100, 50);
        this.add(lbs[0]);
        this.add(lbs[1]);
        this.add(lbs[2]);

        //выход
        JButton exit = new JButton("Выход");
        exit.setBounds(340, GAME_HEIGHT + 30, 70, 25);
        exit.setFocusable(false);
        this.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return PANEL_SIZE;
    }

    //отрисовка всех объектов игры
    public void drawGame(Graphics g) {
        //вертикальные линии
        for (int i = 0; i < GAME_WIDTH / GRID_SIZE + 1; i++) {
            g.drawLine(i * GRID_SIZE, 0, i * GRID_SIZE, GAME_HEIGHT);
        }

        //горизонтальные линии
        for (int i = 0; i < GAME_HEIGHT / GRID_SIZE + 1; i++) {
            g.drawLine(0, i * GRID_SIZE, GAME_WIDTH, i * GRID_SIZE);
        }

        //рисуем стены
        for (int i = 0; i < walls.size(); i++) {
            walls.get(i).draw(g);
        }

        //рисуем мышей
        for (int i = 0; i < mouses.size(); i++) {
            mouses.get(i).draw(g);
        }

        //рисуем змею
        snake.draw(g);

        //отрисовка статистики
        lbs[0].setText("Рекорд: " + snake.getBestScore());
        lbs[1].setText("Счет: " + snake.getScore());
        lbs[2].setText("Жизни: " + snake.getLives());
    }

    //проверка конца игры
    public void gameOver() {
        if (snake.boundsCollision() || snake.snakeCollision() || snake.wallsCollision(walls)) {
            int lives = snake.getLives() - 1;
            if (lives == 0) {
                snake.stop();
                snake.setLives(0);
                timer.stop();
                snake.saveScore();
                JOptionPane.showMessageDialog(null, "Вы проиграли!", "Результат", JOptionPane.WARNING_MESSAGE);
            } else {
                lvl = beglvl;
                snake.saveScore();
                Color cl = snake.getColor();
                snake = new Snake(8, 7, GRID_SIZE, length, cl, GAME_WIDTH / GRID_SIZE, GAME_HEIGHT / GRID_SIZE);
                snake.setLives(lives);
                walls = new ArrayList<>();
                mouses = new ArrayList<>();
                generateLvl(lvl);
                timer.stop();
                repaint(); //перерисовка, чтобы отразить изменения
            }
        }
    }

    //перезапуск игры с текущими настройками
    public void resetGame() {
        beglvl = lvl = level.getValue();
        length = len.getValue();
        this.setBackground(backColor);
        snake = new Snake(8, 7, GRID_SIZE, length, snakeColor, GAME_WIDTH / GRID_SIZE, GAME_HEIGHT / GRID_SIZE);
        walls = new ArrayList<>();
        mouses = new ArrayList<>();
        generateLvl(lvl);
        timer.stop();
        repaint(); //перерисовка, чтобы отразить изменения
    }

    //рандомная генерация уровня
    public void generateLvl(int lvl) {
        switch (lvl) {
            case 1: {
                timer.setDelay(300);
                //создаем рандомно 2 стены на карте
                generateWalls(4);
                break;
            }
            case 2: {
                timer.setDelay(200);
                //создаем ранмдомно 4 стены на карте
                generateWalls(8);
                break;
            }
            case 3: {
                timer.setDelay(100);
                //создаем ранмдомно 8 стен на карте
                generateWalls(10);
                break;
            }
        }
        mouseTime = 0; //обнуляем таймер создания мышей
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //рисуем все тут
        drawGame(g);
    }

    //проверка стены по заданной координате
    public boolean isWall(int x, int y) {
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).x == x && walls.get(i).y == y) {
                return true;
            }
        }
        return false;
    }

    //проверка мыши по заданной координате
    public boolean isMouse(int x, int y) {
        for (int i = 0; i < mouses.size(); i++) {
            if (mouses.get(i).x == x && mouses.get(i).y == y) {
                return true;
            }
        }
        return false;
    }

    //создание новой мыши
    public void generateMouse() {
        Point pt = snake.getCoord(); //координаты змеи
        //всего на карте может быть сразу 3 мыши
        if (mouses.size() < maxMouses) {
            //ищем свободное место для мыши
            //берем случайно x и y координаты
            int x = rd.nextInt(GAME_WIDTH / GRID_SIZE);
            int y = rd.nextInt(GAME_HEIGHT / GRID_SIZE);
            //10 - кол-во попыток добавить мышь
            int attemp = 0;
            for (; attemp < 10 && ((pt.x == x && pt.y == y) || isWall(x, y) || isMouse(x, y)); attemp++) {
                x = rd.nextInt(GAME_WIDTH / GRID_SIZE);
                y = rd.nextInt(GAME_HEIGHT / GRID_SIZE);
            }
            if (attemp < 10) {
                mouses.add(new Block(x, y, GRID_SIZE, new Color(200, 0, 0)));
            }
        }
    }

    //создание стен на уровне
    public void generateWalls(int size) {
        //если стен сейчас больше чем нужно стоит
        if (walls.size() > size) {
            walls = new ArrayList<>(); //убираем старые стены
        }
        Point pt = snake.getCoord(); //координаты змеи
        //досоздаем новые стены на уровне
        int cur = walls.size();
        for (int i = 0; i < size - cur; i++) {
            //ищем свободное место для стены
            //берем случайно x и y координаты
            int x = rd.nextInt(GAME_WIDTH / GRID_SIZE);
            int y = rd.nextInt(GAME_HEIGHT / GRID_SIZE);
            //15 - кол-во попыток добавить стену
            int attemp = 0;
            for (; attemp < 15 && ((pt.x == x && pt.y == y) || isWall(x, y) || isMouse(x, y)); attemp++) {
                x = rd.nextInt(GAME_WIDTH / GRID_SIZE);
                y = rd.nextInt(GAME_HEIGHT / GRID_SIZE);
            }
            if (attemp < 15) {
                walls.add(new Block(x, y, GRID_SIZE, new Color(100, 100, 100)));
            }
        }
    }

    //переход на следующий уровень
    public void checkLvlup() {
        if (lvl == 1 && snake.getLength() >= 6) {
            lvl++; //на 2-й уровень
            generateLvl(lvl);
        }
        if (lvl == 2 && snake.getLength() >= 14) {
            lvl++; //на третий
            generateLvl(lvl);
        }
    }

    //каждый тик таймера
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        gameOver();
        if (snake.MouseCollision(mouses)) {
            checkLvlup();
        } else {
            snake.move();
        }
        mouseTime += timer.getDelay();
        //прошло 3 секунды?
        if (mouseTime >= 3000f) {
            //создаем новую мышь
            generateMouse();
            mouseTime = 0;
        }
    }

    //нажатие на клавишу
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //код символа
        int x = 0, y = 0; //направления

        //направления движений змеи
        if (code == KeyEvent.VK_LEFT) {
            x = -1;
            y = 0;
        } else if (code == KeyEvent.VK_RIGHT) {
            x = 1;
            y = 0;
        } else if (code == KeyEvent.VK_UP) {
            x = 0;
            y = -1;
        } else if (code == KeyEvent.VK_DOWN) {
            x = 0;
            y = 1;
        } else if (code == KeyEvent.VK_SPACE) {
            control.doClick();
        }

        //меняем направление движения
        if (x != 0 || y != 0) {
            snake.changeDir(x, y);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
