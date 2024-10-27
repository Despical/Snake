package me.despical.snake.pawns;

import javafx.scene.canvas.GraphicsContext;
import me.despical.snake.Game;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Despical
 * <p>
 * Created at 21.10.2024
 */
public abstract class Pawn {

    protected static final int tileSize = Game.TILE_SIZE;
    protected static final int boardWidth = Game.BOARD_WIDTH;
    protected static final int boardHeight = Game.BOARD_HEIGHT;
    protected static final ThreadLocalRandom random = ThreadLocalRandom.current();

    public abstract void draw(GraphicsContext graphics);
}