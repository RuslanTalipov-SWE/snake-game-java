import org.junit.Test;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SnakeTest {

    /**
     * Test of changeDir method, of class Snake.
     */
    @Test
    public void testChangeDir() {
        System.out.println("changeDir");
        Snake instance = new Snake(8, 7, 10, 1, null, 1, 1);
        instance.changeDir(0, -1);
        assertEquals(0, instance.vx);
        assertEquals(-1, instance.vy);
        assertEquals(false, instance.dir);
        instance.changeDir(0, 1);
        assertEquals(0, instance.vx);
        assertEquals(-1, instance.vy);
        assertEquals(false, instance.dir);
        instance = new Snake(8, 7, 10, 1, null, 1, 1);
        instance.changeDir(-1, 0);
        assertEquals(1, instance.vx);
        assertEquals(0, instance.vy);
        assertEquals(true, instance.dir);
    }

    /**
     * Test of loadScore method, of class Snake.
     */
    @Test
    public void testLoadScore() {
        System.out.println("loadScore");
        Snake instance = new Snake(8, 7, 10, 1, null, 1, 1);
        instance.loadScore();
        int score = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("score.txt"))) {
            String line = br.readLine();
            score = Integer.parseInt(line);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        assertEquals(score, instance.bestscore);
    }

    /**
     * Test of saveScore method, of class Snake.
     */
    @Test
    public void testSaveScore() {
        System.out.println("saveScore");
        int score = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("score.txt"))) {
            String line = br.readLine();
            score = Integer.parseInt(line);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Snake instance = new Snake(8, 7, 10, 1, null, 1, 1);
        instance.saveScore();
        instance.loadScore();
        assertEquals(score, instance.bestscore);
    }

    /**
     * Test of getBestScore method, of class Snake.
     */
    @Test
    public void testGetBestScore() {
        System.out.println("getBestScore");
        Snake instance = new Snake(8, 7, 10, 1, null, 1, 1);
        int score = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("score.txt"))) {
            String line = br.readLine();
            score = Integer.parseInt(line);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        int result = instance.getBestScore();
        assertEquals(score, result);
    }

    /**
     * Test of setColor method, of class Snake.
     */
    @Test
    public void testSetColor() {
        System.out.println("setColor");
        Color cl = new Color(0, 255, 0);
        Snake instance = new Snake(8, 7, 10, 1, null, 1, 1);
        instance.setColor(cl);
        assertEquals(cl, instance.cl);
    }

    /**
     * Test of getColor method, of class Snake.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        Snake instance = new Snake(8, 7, 10, 1, null, 1, 1);
        Color cl = instance.getColor();
        assertEquals(null, cl);
    }

    /**
     * Test of getLength method, of class Snake.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        int result = instance.getLength(); //разница между размером массива и начальной длиной
        assertEquals(0, result);
    }

    /**
     * Test of getScore method, of class Snake.
     */
    @Test
    public void testGetScore() {
        System.out.println("getScore");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        int result = instance.getScore();
        assertEquals(0, result);
    }

    /**
     * Test of setScore method, of class Snake.
     */
    @Test
    public void testSetScore() {
        System.out.println("setScore");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        instance.setScore(15);
        int result = instance.getScore();
        assertEquals(15, result);
    }

    /**
     * Test of getLives method, of class Snake.
     */
    @Test
    public void testGetLives() {
        System.out.println("getLives");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        int result = instance.getLives();
        assertEquals(3, result);
    }

    /**
     * Test of setLives method, of class Snake.
     */
    @Test
    public void testSetLives() {
        System.out.println("setLives");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        instance.setLives(2);
        int result = instance.getLives();
        assertEquals(2, result);
    }

    /**
     * Test of getCoord method, of class Snake.
     */
    @Test
    public void testGetCoord() {
        System.out.println("getCoord");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        Point result = instance.getCoord();
        assertEquals(9, result.x);
        assertEquals(7, result.y);
    }

    /**
     * Test of stop method, of class Snake.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        instance.stop();
        assertEquals(0, instance.vx);
        assertEquals(0, instance.vy);
    }

    /**
     * Test of notStop method, of class Snake.
     */
    @Test
    public void testNotStop() {
        System.out.println("notStop");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        boolean result = instance.notStop();
        assertEquals(true, result);
        instance.stop();
        assertEquals(0, instance.vx);
        assertEquals(0, instance.vy);
        result = instance.notStop();
        assertEquals(false, result);
    }

    /**
     * Test of grow method, of class Snake.
     */
    @Test
    public void testGrow() {
        System.out.println("grow");
        Snake instance = new Snake(8, 7, 10, 2, null, 1, 1);
        instance.grow(10, 7);
        assertEquals(3, instance.lst.size()); //факт размер
        assertEquals(1, instance.getLength()); //относительно начальной длины
        Point result = instance.getCoord();
        assertEquals(10, result.x);
        assertEquals(7, result.y);
    }

    /**
     * Test of boundsCollision method, of class Snake.
     */
    @Test
    public void testBoundsCollision() {
        System.out.println("boundsCollision");
        Snake instance = new Snake(8, 7, 10, 1, null, 10, 10);
        boolean result = instance.boundsCollision();
        assertEquals(false, result);
        instance.lst.get(instance.lst.size() - 1).x = -2;
        instance.lst.get(instance.lst.size() - 1).y = 2;
        result = instance.boundsCollision();
        assertEquals(true, result);
        assertEquals(0, instance.vx);
        assertEquals(0, instance.vy);
        instance.vx = 1;
        instance.vy = 0;
        instance.lst.get(instance.lst.size() - 1).x = 9;
        instance.lst.get(instance.lst.size() - 1).y = 2;
        result = instance.boundsCollision();
        assertEquals(true, result);
        instance.vx = 1;
        instance.vy = 0;
        instance.lst.get(instance.lst.size() - 1).x = 4;
        instance.lst.get(instance.lst.size() - 1).y = 4;
        result = instance.boundsCollision();
        assertEquals(false, result);
    }

    /**
     * Test of snakeCollision method, of class Snake.
     */
    @Test
    public void testSnakeCollision() {
        System.out.println("snakeCollision");
        Snake instance = new Snake(8, 7, 10, 5, null, 10, 10);
        boolean result = instance.snakeCollision();
        assertEquals(false, result);
        instance.lst.get(instance.lst.size() - 1).x = 9;
        instance.lst.get(instance.lst.size() - 1).y = 6;
        instance.vx = 0;
        instance.vy = 1;
        result = instance.snakeCollision();
        assertEquals(true, result);
    }

    /**
     * Test of wallsCollision method, of class Snake.
     */
    @Test
    public void testWallsCollision() {
        System.out.println("wallsCollision");
        ArrayList<Block> walls = new ArrayList<>();
        walls.add(new Block(9, 7, 1, null));
        Snake instance = new Snake(8, 7, 10, 1, null, 10, 10);
        boolean result = instance.wallsCollision(walls);
        assertEquals(true, result);
        walls.get(0).x = 8;
        walls.get(0).y = 6;
        instance.vx = 0;
        instance.vy = -1;
        result = instance.wallsCollision(walls);
        assertEquals(true, result);
        instance.vx = 1;
        instance.vy = 0;
        result = instance.wallsCollision(walls);
        assertEquals(false, result);
    }

    /**
     * Test of MouseCollision method, of class Snake.
     */
    @Test
    public void testMouseCollision() {
        System.out.println("MouseCollision");
        ArrayList<Block> mouses = new ArrayList<>();
        mouses.add(new Block(8, 6, 1, null));
        Snake instance = new Snake(8, 7, 10, 1, null, 10, 10);
        boolean result = instance.MouseCollision(mouses);
        assertEquals(false, result);
        instance.vx = 0;
        instance.vy = -1;
        result = instance.MouseCollision(mouses);
        assertEquals(true, result);
        assertEquals(0, mouses.size());
        assertEquals(1, instance.getLength());
        assertEquals(8, instance.getCoord().x);
        assertEquals(6, instance.getCoord().y);
    }

    /**
     * Test of move method, of class Snake.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Snake instance = new Snake(8, 7, 10, 1, null, 10, 10);
        instance.move();
        assertEquals(9, instance.getCoord().x);
        assertEquals(7, instance.getCoord().y);
        instance.changeDir(0, -1);
        instance.move();
        assertEquals(9, instance.getCoord().x);
        assertEquals(6, instance.getCoord().y);
        instance.changeDir(-1, 0);
        instance.move();
        assertEquals(8, instance.getCoord().x);
        assertEquals(6, instance.getCoord().y);
    }

    /**
     * Test of draw method, of class Snake.
     */
    @Test
    public void testDraw() {
        System.out.println("draw");
    }

}
