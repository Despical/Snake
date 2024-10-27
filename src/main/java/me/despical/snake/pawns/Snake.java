package me.despical.snake.pawns;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import me.despical.snake.Game;
import me.despical.snake.utils.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Despical
 * <p>
 * Created at 21.10.2024
 */
public class Snake extends Pawn {

	private boolean grow;
	private Direction direction;

	private final List<Point2D> segments;

	public Snake() {
		this.segments = new ArrayList<>();
		this.segments.add(new Point2D(200, 200));

		Direction[] values = Direction.values();

		direction = values[random.nextInt(values.length - 1)];
	}

	@Override
	public void draw(GraphicsContext graphics) {
		graphics.clearRect(0, 0, boardWidth, boardHeight);
		graphics.setFill(Color.BLACK);
		graphics.fillRect(0, 0, boardHeight, boardHeight);

		for (int i = 1; i < segments.size(); i++) {
			Point2D point = segments.get(i);

			graphics.setFill(Color.GRAY);
			graphics.fillRect(point.getX(), point.getY(), tileSize, tileSize);

			if (Game.SHOW_SEGMENT_INDEXES) {
				graphics.setFill(Color.BLACK);
				graphics.fillText(Integer.toString(i), point.getX() + tileSize / 2D, point.getY() + tileSize / 2D);
			}
		}

		Point2D headPos = segments.get(0);
		graphics.setFill(Color.WHITE);
		graphics.fillRect(headPos.getX(), headPos.getY(), tileSize, tileSize);
	}

	public void setDirection(Direction direction) {
		if (segments.size() > 1 && this.direction == direction.getOpposite()) {
			return;
		}

		this.direction = direction;
	}

	public void updatePosition() {
		Point2D snakeHead = segments.get(0);

		if (grow) {
			Point2D lastSegmentPos = segments.get(segments.size() - 1);

			this.segments.add(lastSegmentPos);
			this.grow = false;
		}

		if (Game.WRAP_AROUND) {
			for (int i = segments.size() - 1; i > 0; i--) {
				segments.set(i, segments.get(i - 1));
			}

			double newX = snakeHead.getX() + direction.getX();
			double newY = snakeHead.getY() + direction.getY();

			if (newX < 0) {
				newX = boardWidth - tileSize;
			} else if (newX >= boardWidth) {
				newX = 0;
			}

			if (newY < 0) {
				newY = boardHeight - tileSize;
			} else if (newY >= boardHeight) {
				newY = 0;
			}

			snakeHead = new Point2D(newX, newY);
			segments.set(0, snakeHead);
			return;
		}

		segments.add(0, snakeHead.add(direction.getX(), direction.getY()));
		segments.remove(segments.size() - 1);
	}

	public boolean isGameOver() {
		Point2D headPos = segments.get(0);

		if (!Game.WRAP_AROUND && (headPos.getX() < 0 || headPos.getX() > Game.BOARD_WIDTH - tileSize || headPos.getY() < 0 || headPos.getY() > Game.BOARD_HEIGHT - tileSize)) {
			return true;
		}

		if (segments.size() == 1) {
			return false;
		}

		for (int i = 1; i < segments.size(); i++) {
			if (headPos.equals(segments.get(i))) {
				return true;
			}
		}

		return false;
	}

	public boolean hasCollectedAllApples() {
		return segments.size() == (Game.BOARD_WIDTH / tileSize) * (Game.BOARD_HEIGHT / tileSize);
	}

	public void grow() {
		this.grow = true;
	}

	public List<Point2D> getSegments() {
		return segments;
	}
}