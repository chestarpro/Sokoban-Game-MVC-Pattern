import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Canvas extends JPanel {
    private final Model model;
    private Image imageWall;
    private Image imageBox;
    private Image imageGoal;
    private Image imageGround;
    private Image imagePlayer;
    private Image imageErrorInitializationLevel;
    private boolean isInitializationLevelError;
    private final Color canvasColor;

    public Canvas(Model model) {
        this.model = model;
        setBackground(Color.BLACK);
        setOpaque(true);
        canvasColor = new Color(135, 123, 99);
        isInitializationLevelError = true;
        try {
            imageWall = ImageIO.read(new File("images/wall.png"));
            imageBox = ImageIO.read(new File("images/box.png"));
            imageGoal = ImageIO.read(new File("images/goal.png"));
            imageGround = ImageIO.read(new File("images/ground.png"));
            imagePlayer = ImageIO.read(new File("images/player.png"));
            imageErrorInitializationLevel = ImageIO.read(new File("images/error.png"));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (isInitializationLevelError) {
            drawDesktop(g);
        } else drawErrorMessage(g);
    }

    private void drawDesktop(Graphics g) {
        int[][] desktop = model.getDesktop();
        int start = 50;
        int x = start;
        int y = start;
        int width = 50;
        int height = 50;
        int offset = 0;

        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1) {
                    g.drawImage(imagePlayer, x, y, null);

                } else if (desktop[i][j] == 2) {

                    g.drawImage(imageWall, x, y, null);
                } else if (desktop[i][j] == 3) {

                    g.drawImage(imageBox, x, y, null);
                } else if (desktop[i][j] == 4) {

                    g.drawImage(imageGoal, x, y, null);

                } else if (desktop[i][j] == 0) {
                    g.drawImage(imageGround, x, y, null);

                } else {
                    g.setColor(canvasColor);
                    g.fillRect(x, y, width, height);
                }
                x = x + width + offset;
            }
            x = start;
            y = y + height + offset;
        }
    }

    private void drawErrorMessage(Graphics g) {
        g.drawImage(imageErrorInitializationLevel, 100, 100, null);
    }

    public void setInitializationLevelError(boolean initializationLevelError) {
        isInitializationLevelError = initializationLevelError;
    }

    public Color getCanvasColor() {
        return canvasColor;
    }
}