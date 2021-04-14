package cn.kriesz.tools;

/**
 * Circle对象
 *
 * @author Krisez
 */
public class Circle {
    /**
     * x的坐标值
     */
    public int x;
    /**
     * y的坐标值
     */
    public int y;
    /**
     * 随机色
     */
    public int color;
    /**
     * 速度
     */
    public int speed;
    /**
     * 半径
     */
    public int radius;
    /**
     * x的移动方向
     * true is right
     * false is left
     */
    public boolean xD = true;
    /**
     * y的移动方向
     * true is down
     * false is top
     */
    public boolean yD = true;

    public Circle(int x, int y, int color, int speed, int radius) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.speed = speed;
        this.radius = radius;
        if (speed == 0) {
            this.speed = 10;
        }
        if (radius == 0) {
            this.radius = 20;
        }
        yD = xD = x > y;
    }

    public void update(int maxW, int maxH) {
        if (xD) {
            x += speed;
        } else {
            x -= speed;
        }
        if (yD) {
            y += speed;
        } else {
            y -= speed;
        }
        if (x > maxW) {
            xD = false;
        } else if (x < 0) {
            xD = true;
        }
        if (y > maxH) {
            yD = false;
        } else if (y < 0) {
            yD = true;
        }
    }
}
