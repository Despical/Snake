package me.despical.snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import me.despical.snake.pawns.Apple;
import me.despical.snake.pawns.Snake;
import me.despical.snake.utils.Direction;

/**
 * @author Despical
 * <p>
 * Created at 21.10.2024
 */
public class Game extends Application {

    public static final int BOARD_HEIGHT = 400;
    public static final int BOARD_WIDTH = 400;
    public static final int TILE_SIZE = 20;
    public static final long UPDATE_INTERVAL = 250_000_000;
    public static final boolean WRAP_AROUND = true;
    public static final boolean SHOW_SEGMENT_INDEXES = false;

    private boolean gameOver = false;
    private boolean hasPressed = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(BOARD_WIDTH, BOARD_HEIGHT);
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setTextBaseline(VPos.CENTER);
        graphics.setFont(new Font("Arial", 40));

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        Snake snake = new Snake();
        Apple apple = new Apple(snake);

        primaryStage.setTitle("Snake Game by Despical");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            if (hasPressed) {
                return;
            }

            final KeyCode keyCode = event.getCode();

            switch (keyCode) {
                case UP, DOWN, LEFT, RIGHT -> {
                    snake.setDirection(Direction.matchDirection(keyCode));
                    hasPressed = true;
                }
            }
        });

        AnimationTimer gameLoop = new AnimationTimer() {

            private long lastUpdated = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdated < UPDATE_INTERVAL) {
                    return;
                }

                gameOver = snake.isGameOver();

                if (gameOver) {
                    graphics.setFill(Color.RED);
                    graphics.fillText("Game Over", BOARD_WIDTH / 2D, BOARD_HEIGHT / 2D);
                    return;
                }

                if (snake.hasCollectedAllApples()) {
                    graphics.setFill(Color.LIGHTBLUE);
                    graphics.fillText("You win!", BOARD_WIDTH / 2D, BOARD_HEIGHT / 2D);
                    return;
                }

                lastUpdated = now;

                snake.updatePosition();
                snake.draw(graphics);
                apple.checkSnakePosition();

                apple.draw(graphics);

                drawGrid(graphics);

                hasPressed = false;
            }
        };

        gameLoop.start();
    }

    private void drawGrid(GraphicsContext graphics) {
        graphics.setStroke(Color.GRAY);

        for (int x = 0; x < BOARD_WIDTH; x += TILE_SIZE) {
            graphics.strokeLine(x, 0, x, BOARD_HEIGHT);
        }

        for (int y = 0; y < BOARD_HEIGHT; y += TILE_SIZE) {
            graphics.strokeLine(0, y, BOARD_WIDTH, y);
        }
    }
}