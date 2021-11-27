import java.io.*;

public class Levels {

    public int[][] nextLevel(String fileName) throws Exception {
        File file = new File(fileName);
        String contentFile = getContentFile(file);
        int[][] desktop = convertStringIntoTwoDimensionArray(contentFile);
        return desktop;
    }

    public int[][] convertStringIntoTwoDimensionArray(String line) {
        int n = line.length();
        int row = 0;

        for (int i = 0; i < n; i++) {
            char symbol = line.charAt(i);
            if (symbol == '.') {
                row = row + 1;
            }
        }
        int[][] array = new int[row][];

        int column = 0;
        int index = 0;
        for (int i = 0; i < n; i++) {
            char symbol = line.charAt(i);
            if (symbol != '.') {
                column = column + 1;
            } else if (symbol == '.') {
                array[index] = new int[column];
                index = index + 1;
                column = 0;
            }
        }

        row = 0;
        column = 0;
        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
            if (symbol != '.') {
                array[row][column] = Integer.parseInt("" + symbol);
                column = column + 1;
            } else if (symbol == '.') {
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
                if (('0' <= symbol && symbol <= '5') || symbol == '.') {
                    array[index] = symbol;
                    index = index + 1;
                }
            }
            String content = new String(array, 0, index);
            return content;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File Not Found Exception! " + e);
        } catch (IOException ioe) {
            throw new FileNotFoundException("Basic I/O Exception! " + ioe);
        }
    }
}