package me.despical.snake.pawns;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import me.despical.snake.Game;

/**
 * @author Despical
 * <p>
 * Created at 21.10.2024
 */
public class Apple extends Pawn {

    private final Snake snake;
    private Point2D origin;

    public Apple(Snake snake) {
        this.snake = snake;
        this.origin = this.findNewOrigin();
    }

    private Point2D findNewOrigin() {
        while (true) {
            Point2D point = new Point2D(random.nextInt(Game.BOARD_WIDTH / tileSize) * tileSize, random.nextInt(Game.BOARD_HEIGHT / tileSize) * tileSize);

            if (isInsideSnake(point)) {
                continue;
            }

            return point;
        }
    }

    private boolean isInsideSnake(Point2D point) {
        double x = point.getX(), y = point.getY();

        for (Point2D segment : snake.getSegments()) {
            double appleX = x / tileSize;
            double appleY = y / tileSize;

            double segmentX = segment.getX() / tileSize;
            double segmentY = segment.getY() / tileSize;

            if (Math.floor(appleX) == Math.floor(segmentX) && Math.floor(appleY) == Math.floor(segmentY)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(Color.RED);
        graphics.fillOval(origin.getX(), origin.getY(), tileSize, tileSize);
    }

    public void checkSnakePosition() {
        Point2D headPos = snake.getSegments().get(0);

        if (headPos.getX() == origin.getX() && headPos.getY() == origin.getY()) {
            snake.grow();
            origin = this.findNewOrigin();
        }
    }
}