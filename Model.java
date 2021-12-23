import java.io.File;

public class Model {
    private final Viewer viewer;
    private int[][] desktop;
    private int[][] arrayIndexesGoal;
    private int indexX;
    private int indexY;
    private int playerPosition;
    private final Levels levels;
    private boolean isOk;
    private final Sound boxInPositionSound;
    private final Sound wonSound;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        boxInPositionSound = new Sound(new File("sound/point.wav"));
        wonSound = new Sound(new File("sound/won.wav"));
        isOk = true;
        levels = new Levels();
        playerPosition = 3;
    }

    public void doAction(String command) {
        switch (command) {
            case "Level1":
                levels.setLevel(1);

                break;
            case "Level2":
                levels.setLevel(2);

                break;
            case "Level3":
                levels.setLevel(3);

                break;
            case "Level4":
                levels.setLevel(4);

                break;
            case "Level5":
                levels.setLevel(5);

                break;
            case "Level6":
                levels.setLevel(6);

                break;
            case "Level7":
                levels.setLevel(7);

                break;
            case "Level8":
                levels.setLevel(8);

                break;
            case "Level9":
                levels.setLevel(9);
                break;
            case "Restart":
                levels.setLevel(levels.getLevel() - 1);
                break;
            case "Exit":
                System.exit(0);
        }

        isOk = true;
        initialization();
        playerPosition = 3;
        viewer.update();
    }

    public void move(String direction) {
        if (direction.equals("Up")) {
            playerPosition = 1;
            moveUp();
        } else if (direction.equals("Right")) {
            playerPosition = 2;
            moveRight();
        } else if (direction.equals("Down")) {
            playerPosition = 3;
            moveDown();
        } else if (direction.equals("Left")) {
            playerPosition = 4;
            moveLeft();
        } else {
            return;
        }

        checkGoal();
        viewer.update();
        won();
    }

    public void initialization() {
        desktop = levels.nextLevel();

        checkWall(desktop);
        checkPlayerCount(desktop);
        checkBoxAndGoalCount(desktop);

        initializationPlayerPosition(desktop);
        initializationGoalPosition(desktop);
    }

    private void checkGoal() {
        int x;
        int y;
        for (int i = 0; i < arrayIndexesGoal.length; i++) {
            x = arrayIndexesGoal[i][0];
            y = arrayIndexesGoal[i][1];
            if (desktop[x][y] == 0) {
                desktop[x][y] = 4;
                break;
            }
        }
    }

    private void won() {
        boolean isWon = true;
        int x;
        int y;
        for (int i = 0; i < arrayIndexesGoal.length; i++) {
            x = arrayIndexesGoal[i][0];
            y = arrayIndexesGoal[i][1];
            if (desktop[x][y] != 3) {
                isWon = false;
                break;
            }
        }
        if (isWon) {
            boxInPositionSound.stop();
            wonSound.play();
            if (viewer.showDialog()) {
                initialization();
                playerPosition = 3;
                viewer.update();
            }
        }
    }

    private void moveUp() {
        if (desktop[indexX - 1][indexY] == 3) {
            if (desktop[indexX - 2][indexY] == 0) {
                desktop[indexX - 1][indexY] = 0;
                desktop[indexX - 2][indexY] = 3;

            } else if (desktop[indexX - 2][indexY] == 4) {
                desktop[indexX - 1][indexY] = 0;
                desktop[indexX - 2][indexY] = 3;
                boxInPositionSound.play();
            }
        }

        if (desktop[indexX - 1][indexY] == 0 || desktop[indexX - 1][indexY] == 4) {
            desktop[indexX][indexY] = 0;
            indexX = indexX - 1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveRight() {
        if (desktop[indexX][indexY + 1] == 3) {
            if (desktop[indexX][indexY + 2] == 0) {
                desktop[indexX][indexY + 1] = 0;
                desktop[indexX][indexY + 2] = 3;
            } else if (desktop[indexX][indexY + 2] == 4) {
                desktop[indexX][indexY + 1] = 0;
                desktop[indexX][indexY + 2] = 3;
                boxInPositionSound.play();
            }
        }
        if (desktop[indexX][indexY + 1] == 0 || desktop[indexX][indexY + 1] == 4) {
            desktop[indexX][indexY] = 0;
            indexY = indexY + 1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveDown() {
        if (desktop[indexX + 1][indexY] == 3) {
            if (desktop[indexX + 2][indexY] == 0) {
                desktop[indexX + 1][indexY] = 0;
                desktop[indexX + 2][indexY] = 3;
            } else if (desktop[indexX + 2][indexY] == 4) {
                desktop[indexX + 1][indexY] = 0;
                desktop[indexX + 2][indexY] = 3;
                boxInPositionSound.play();
            }
        }

        if (desktop[indexX + 1][indexY] == 0 || desktop[indexX + 1][indexY] == 4) {
            desktop[indexX][indexY] = 0;
            indexX = indexX + 1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveLeft() {
        if (desktop[indexX][indexY - 1] == 3) {
            if (desktop[indexX][indexY - 2] == 0) {
                desktop[indexX][indexY - 1] = 0;
                desktop[indexX][indexY - 2] = 3;
            } else if (desktop[indexX][indexY - 2] == 4) {
                desktop[indexX][indexY - 1] = 0;
                desktop[indexX][indexY - 2] = 3;
                boxInPositionSound.play();
            }
        }

        if (desktop[indexX][indexY - 1] == 0 || desktop[indexX][indexY - 1] == 4) {
            desktop[indexX][indexY] = 0;
            indexY = indexY - 1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void initializationGoalPosition(int[][] desktop) {
        int size = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 4) {
                    size = size + 1;
                }
            }
        }

        arrayIndexesGoal = new int[size][];
        int index = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 4) {
                    arrayIndexesGoal[index] = new int[]{i, j};
                    index = index + 1;
                }
            }
        }
    }

    private void initializationPlayerPosition(int[][] desktop) {
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1) {
                    indexX = i;
                    indexY = j;
                    this.desktop[indexX][indexY] = 1;
                    return;
                }
            }
        }
    }

    private void checkPlayerCount(int[][] desktop) {
        int count = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1) {
                    count = count + 1;
                }
                if (count > 1) {
                    isOk = false;
                    return;
                }
            }
        }
        if (count == 0) {
            isOk = false;
        }
    }

    private void checkBoxAndGoalCount(int[][] desktop) {
        int countBox = 0;
        int countGoal = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 3) {
                    countBox = countBox + 1;
                }
                if (desktop[i][j] == 4) {
                    countGoal = countGoal + 1;
                }
            }
        }

        if (countBox != countGoal) isOk = false;

        if (countBox == 0 && countGoal == 0) isOk = false;
    }

    private void checkWall(int[][] desktop) {
        for (int i = 0; i < desktop.length; i++) {
            if (desktop[0][i] != 2) {
                isOk = false;
                return;
            }
            if (i > 0 && i < desktop.length - 1) {
                if (desktop[i][desktop.length - 1] != 2) {
                    isOk = false;
                    return;
                }
            }
            if (desktop[desktop.length - 1][i] != 2) {
                isOk = false;
                return;
            }
        }
    }

    public int[][] getDesktop() {
        return desktop;
    }

    public boolean isErrorState() {
        return !isOk;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setVolume(float volume) {
        wonSound.setVolume(volume);
        boxInPositionSound.setVolume(volume);
    }

    public boolean isSelected() {
        return viewer.getCheckBox().isSelected();
    }

    public int getCurrentLevel() {
        return levels.getLevel() - 1;
    }
}