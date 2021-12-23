import java.io.*;
import java.net.Socket;

public class Levels {
    private int level;
    private final int PORT_NUMBER = 4445;
    private final String HOST_NAME = "194.152.37.7";

    public Levels() {
        level = 1;
    }

    public int[][] nextLevel() {
        int[][] desktop = null;

        switch (level) {
            case 1:
                desktop = getFirstLevel();
                break;
            case 2:
                desktop = getSecondLevel();
                break;
            case 3:
                desktop = getThirdLevel();
                break;
            case 4:
                desktop = getFileLevel("levels/level4.sok");
                break;
            case 5:
                desktop = getFileLevel("levels/level5.sok");
                break;
            case 6:
                desktop = getFileLevel("levels/level6.sok");
                break;
            case 7:
                desktop = getServerLevel("Seven");
                break;
            case 8:
                desktop = getServerLevel("Eight");
                break;
            case 9:
                desktop = getServerLevel("Nine");
                break;
            default:
                level = 1;
                desktop = getFirstLevel();
                break;
        }
        level = level + 1;
        return desktop;
    }

    public int[][] getFirstLevel() {
        return new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 1, 0, 0, 0, 0, 2},
                {2, 0, 4, 0, 0, 0, 0, 4, 0, 2},
                {2, 0, 3, 0, 0, 0, 0, 3, 0, 2},
                {2, 0, 3, 0, 0, 0, 0, 3, 0, 2},
                {2, 2, 2, 2, 0, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 4, 0, 0, 4, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
        };
    }

    private int[][] getSecondLevel() {
        return new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 4, 0, 0, 2, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 2, 0, 4, 4, 0, 2},
                {2, 2, 3, 2, 2, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 2, 2, 3, 2, 2},
                {2, 0, 2, 2, 0, 0, 0, 0, 0, 2},
                {2, 0, 1, 2, 0, 0, 3, 0, 0, 2},
                {2, 0, 3, 2, 0, 0, 0, 0, 0, 2},
                {2, 0, 4, 2, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
        };
    }

    private int[][] getThirdLevel() {
        return new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 2, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 2, 0, 3, 3, 0, 0, 0, 2},
                {2, 4, 0, 2, 0, 0, 3, 0, 0, 2},
                {2, 0, 4, 0, 1, 0, 0, 3, 0, 2},
                {2, 0, 0, 0, 0, 2, 0, 3, 0, 2},
                {2, 0, 0, 0, 4, 0, 2, 0, 0, 2},
                {2, 0, 0, 0, 0, 4, 0, 2, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 4, 2, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
        };
    }

    public int[][] getFileLevel(String fileName) {
        File file = new File(fileName);
        String contentFile = null;
        try {
            contentFile = getContentFile(file);
            int[][] desktop = convertStringIntoTwoDimensionArray(contentFile);
            return desktop;
        } catch (Exception e) {
            this.level = 1;
            return getFirstLevel();
        }
    }

    public int[][] getServerLevel(String level) {
        try (Socket socket = new Socket(HOST_NAME, PORT_NUMBER);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            out.println(level);
            Desktop serverDesktop = (Desktop) in.readObject();
            int[][] desktop = serverDesktop.getDesktop();
            return desktop;
        } catch (IOException | ClassNotFoundException ioe) {
            this.level = 1;
            return getFirstLevel();
        }
    }

    public int[][] convertStringIntoTwoDimensionArray(String line) {
        int n = line.length();
        int row = 0;

        for (int i = 0; i < n; i++) {
            char symbol = line.charAt(i);
            if (symbol == '\n') {
                row = row + 1;
            }
        }

        int[][] array = new int[row][];

        int column = 0;
        int index = 0;
        for (int i = 0; i < n; i++) {
            char symbol = line.charAt(i);
            if (symbol != '\n') {
                column = column + 1;
            } else {
                array[index] = new int[column];
                index = index + 1;
                column = 0;
            }
        }

        row = 0;
        column = 0;
        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
            if (symbol != '\n') {
                array[row][column] = Integer.parseInt("" + symbol);
                column = column + 1;
            } else {
                row = row + 1;
                column = 0;
            }
        }
        return array;
    }

    private String getContentFile(File file) throws Exception {
        try (FileInputStream in = new FileInputStream(file)) {
            int size = (int) file.length();

            char[] array = new char[size];
            int index = 0;
            int unicode;
            while ((unicode = in.read()) != -1) {
                char symbol = (char) unicode;
                if (('0' <= symbol && symbol <= '5') || symbol == '\n') {
                    array[index] = symbol;
                    index = index + 1;
                }
            }

            array[index] = '\n';
            String content = new String(array, 0, index + 1);
            return content;

        } catch (FileNotFoundException fnfe) {
            throw new FileNotFoundException("File Not Found Exception! " + fnfe);
        } catch (IOException ioe) {
            throw new FileNotFoundException("Basic I/O Exception! " + ioe);
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}