package me.despical.snake.pawns;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Despical
 * <p>
 * Created at 21.10.2024
 */
public class Apple extends Pawn {

	private Point2D origin;
	private final Snake snake;

	public Apple(Snake snake) {
		this.snake = snake;
		this.origin = this.findNewOrigin();
	}

	private Point2D findNewOrigin() {
		while (true) {
			Point2D point = new Point2D(random.nextInt(400 / tileSize) * tileSize, random.nextInt(400 / tileSize) * tileSize);

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