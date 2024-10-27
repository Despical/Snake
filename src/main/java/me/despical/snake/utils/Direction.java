package me.despical.snake.utils;

import javafx.scene.input.KeyCode;

import static me.despical.snake.Game.TILE_SIZE;

/**
 * @author Despical
 * <p>
 * Created at 21.10.2024
 */
public enum Direction {

	UP(0, -TILE_SIZE),
	DOWN(0, TILE_SIZE),
	LEFT(-TILE_SIZE, 0),
	RIGHT(TILE_SIZE, 0),
	NOP(0, 0);

	private final int x, y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Direction getOpposite() {
		return switch (this) {
			case UP -> DOWN;
			case DOWN -> UP;
			case LEFT -> RIGHT;
			case RIGHT -> LEFT;
			default -> NOP;
		};
	}

	public static Direction matchDirection(KeyCode keyCode) {
		for (Direction direction : Direction.values()) {
			if (direction.name().equals(keyCode.name())) {
				return direction;
			}
		}

		return Direction.NOP;
	}
}