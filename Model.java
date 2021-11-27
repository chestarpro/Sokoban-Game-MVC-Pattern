public class Model {
    private final Viewer viewer;
    private int[][] desktop;
    private int[][] goalsPosition;
    private int[][] basicLevel;
    private int indexX;
    private int indexY;
    private final Levels levels;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        levels = new Levels();
        initialization();
    }

    private void initialization() {
        basicLevel = new int[][]{
                {2, 2, 2, 2, 2},
                {2, 0, 0, 0, 2},
                {2, 0, 0, 0, 2, 2, 2},
                {2, 3, 3, 3, 1, 0, 2},
                {2, 4, 4, 4, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2}
        };
        desktop = basicLevel;
        initializationPlayerPosition(desktop);
        initializationGoalPosition(desktop);
    }

    private void nextLevel() {
        try {
            desktop = levels.nextLevel("levels/level3.sok");
            for (int i = 0; i < desktop.length; i++) {
                for (int j = 0; j < desktop[i].length; j++) {
                    System.out.print(desktop[i][j] + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            initialization();
            return;
        }

        viewer.isError(checkWall(desktop));
        viewer.isError(checkPlayerCount(desktop));
        viewer.isError(checkBoxAndGoalCount(desktop));
        viewer.update();
        initializationPlayerPosition(desktop);
        initializationGoalPosition(desktop);
    }

    public void move(String direction) {
        if (direction.equals("Up")) {
            moveUp();
        } else if (direction.equals("Right")) {
            moveRight();
        } else if (direction.equals("Down")) {
            moveDown();
        } else if (direction.equals("Left")) {
            moveLeft();
        } else {
            return;
        }

        checkGoal();

        viewer.update();
        won();
    }

    private void checkGoal() {
        int x;
        int y;
        for (int i = 0; i < goalsPosition.length; i++) {
            x = goalsPosition[i][0];
            y = goalsPosition[i][1];
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
        for (int i = 0; i < goalsPosition.length; i++) {
            x = goalsPosition[i][0];
            y = goalsPosition[i][1];
            if (desktop[x][y] != 3) {
                isWon = false;
                break;
            }
        }
        if (isWon) {
            if (viewer.showDialog()) {
                nextLevel();
                viewer.update();
            }
        }
    }

    private void moveUp() {
        if (desktop[indexX - 1][indexY] == 3) {
            if (desktop[indexX - 2][indexY] == 0 || desktop[indexX - 2][indexY] == 4) {
                desktop[indexX - 1][indexY] = 0;
                desktop[indexX - 2][indexY] = 3;
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
            if (desktop[indexX][indexY + 2] == 0 || desktop[indexX][indexY + 2] == 4) {
                desktop[indexX][indexY + 1] = 0;
                desktop[indexX][indexY + 2] = 3;
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
            if (desktop[indexX + 2][indexY] == 0 || desktop[indexX + 2][indexY] == 4) {
                desktop[indexX + 1][indexY] = 0;
                desktop[indexX + 2][indexY] = 3;
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
            if (desktop[indexX][indexY - 2] == 0 || desktop[indexX][indexY - 2] == 4) {
                desktop[indexX][indexY - 1] = 0;
                desktop[indexX][indexY - 2] = 3;
            }
        }

        if (desktop[indexX][indexY - 1] == 0 || desktop[indexX][indexY - 1] == 4) {
            desktop[indexX][indexY] = 0;
            indexY = indexY - 1;
            desktop[indexX][indexY] = 1;
        }
    }

    public int[][] getDesktop() {
        return desktop;
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

        goalsPosition = new int[size][];
        int[] temp;
        int index = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 4) {
                    temp = new int[2];
                    temp[0] = i;
                    temp[1] = j;
                    goalsPosition[index] = temp;
                    index++;
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

    private boolean checkPlayerCount(int[][] desktop) {
        int count = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1) {
                    count = count + 1;
                }
                if (count > 1) return false;
            }
        }
        return count == 1;
    }

    private boolean checkBoxAndGoalCount(int[][] desktop) {
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
        return countBox == countGoal;
    }

    private boolean checkWall(int[][] desktop) {
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (i == 0) {
                    if (desktop[i][j] == 1 || desktop[i][j] == 3 || desktop[i][j] == 4) {
                        return true;
                    }
                } else if (i < desktop.length - 1) {
                    if (desktop[i][0] == 1 || desktop[i][0] == 3 || desktop[i][0] == 4 ||
                            desktop[i][desktop[i].length - 1] == 1 || desktop[i][desktop[i].length - 1] == 3 ||
                            desktop[i][desktop[i].length - 1] == 4) {
                        return true;
                    }
                } else if (i == desktop.length - 1) {
                    if (desktop[i][j] == 1 || desktop[i][j] == 3 || desktop[i][j] == 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}