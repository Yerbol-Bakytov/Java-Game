import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class MyBotPlayer implements BotPlayer,Runnable {
    private Circle ball;
    private Map map;
    private Position position;
    private Food food;
    private int unit;

    MyBotPlayer(Map map){
        this.map = map;
        unit = map.getUnit();
        position = map.getStartPosition();
        ball = new Circle(unit / 2, unit / 2, unit / 2);
        ball.setFill(Color.RED);
        map.getChildren().add(ball);
    }
    public int getUnit(){
        return this.unit;
    }

    @Override
    public void feed(Food f) {
        this.food = f;
    }


    @Override
    public void eat() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (getPosition().getX() < food.getPosition().getX()) {
                        for (int i = getPosition().getX(); i <= food.getPosition().getX(); i++) {
                            position = new Position(i, getPosition().getY()); // меняем позицию относитель оси x
                            ball.setCenterX(i * getUnit() + getUnit() / 2); // передвигаем ball
                            getPosition().setX(i); // отправляем новые координаты
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (getPosition().getX() > food.getPosition().getX()) {
                        for (int i = getPosition().getX(); i >= food.getPosition().getX(); i--) {
                            position = new Position(i, getPosition().getY());
                            ball.setCenterX(i * getUnit() + getUnit() / 2);
                            getPosition().setX(i);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (getPosition().getY() < food.getPosition().getY()) {
                        for (int i = getPosition().getY(); i <= food.getPosition().getY(); i++) {
                            position = new Position(getPosition().getX(), i);
                            ball.setCenterY(i * getUnit() + getUnit() / 2);
                            getPosition().setY(i);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (getPosition().getY() > food.getPosition().getY()) {
                        for (int i = getPosition().getY(); i >= food.getPosition().getY(); i--) {
                            position = new Position(getPosition().getX(), i);
                            ball.setCenterY(i * getUnit() + getUnit() / 2);
                            getPosition().setY(i);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    System.out.print("");
                }
            }
        });
        thread.start();
    }

    private boolean isValid(boolean visited[][], int row, int col) { //Для проверки позиции, не вышел ли данный ход за рамки и не является ли следующий ход черной клеткой и чтобы он не повторялся
        int size = map.getSize();

        return (row >= 0) && (row < size) && (col >= 0) && (col < size) // Если все эти условие выполняется возрвращаем true если нет то false
                && map.getValueAt(row, col) != 1 && !visited[row][col];
    }

    void findShortestPath(int ballX, int ballY, int foodX, int foodY) {
        int size = map.getSize();
        boolean[][] visited = new boolean[size][size];
        Queue<Position> q = new ArrayDeque<>();

        visited[ballX][ballY] = true;

        q.add(new Position(ballX, ballY));

        while (!q.isEmpty()) {
            Position p = q.poll(); //Рассматриваем первый элемент листа
            ballX = p.getX();
            ballY = p.getY();

            if (ballX == foodX && ballY == foodY) {
                System.out.println(Arrays.deepToString(visited));
                System.exit(1);
                return;
            }

            int[][] possibleMoves = {{-1, 0}, {0, -1}, {0 , 1}, {1, 0}}; // рассматриваем все возможные ходы

            for (int k = 0; k < 4; k++)
            {
                if (isValid(visited, ballX + possibleMoves[k][0], ballY + possibleMoves[k][1]))
                {
                    visited[ballX + possibleMoves[k][0]][ballY + possibleMoves[k][1]] = true;
                    q.add(new Position(ballX + possibleMoves[k][0], ballY + possibleMoves[k][1]));
                }
            }
        }
    }




    @Override
    public void find() {
        Thread thread = new Thread(new Runnable() {
            int size = map.getSize();

            @Override
            public void run() {
                while (true) {
                    findShortestPath(getPosition().getX(), getPosition().getY(), food.getPosition().getX(), food.getPosition().getY());
                }
            }
        });
        thread.start();
    }


    @Override
    public void moveRight() {
        if (ball.getCenterX() < (map.getSize() - 1) * map.getUnit()) {
            if (map.getValueAt(position.getY(), (position.getX() + 1)) != 1) {
                position.setX(position.getX() + 1);
                ball.setCenterX(ball.getCenterX() + map.getUnit());
            }
        }
    }

    @Override
    public void moveLeft() {
        if(ball.getCenterX() - map.getUnit() >= 0 ) {
            if (map.getValueAt(position.getY(), (position.getX() - 1)) != 1) {
                position.setX(position.getX() - 1);
                ball.setCenterX(ball.getCenterX() - map.getUnit());
            }
        }
    }

    @Override
    public void moveUp() {
        if(ball.getCenterY() - map.getUnit() >= 0) {
            if (map.getValueAt((position.getY() - 1), position.getX()) != 1) {
                position.setY(position.getY() - 1);
                ball.setCenterY(ball.getCenterY() - map.getUnit());
            }
        }
    }

    @Override
    public void moveDown() {
        if(ball.getCenterY() < (map.getSize() - 1) * map.getUnit()) {
            if (map.getValueAt((position.getY() + 1), position.getX()) != 1) {
                position.setY(position.getY() + 1);
                ball.setCenterY(ball.getCenterY() + map.getUnit());
            }
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void run() {
    }
}