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
    private Image imagePlayerUp;
    private Image imagePlayerRight;
    private Image imagePlayerDown;
    private Image imagePlayerLeft;
    private Image imageError;
    private final Font statusFont;

    public Canvas(Model model) {
        this.model = model;
        setBackground(Color.LIGHT_GRAY);
        statusFont = new Font("Arial", Font.BOLD, 25);
        try {
            imageWall = ImageIO.read(new File("images/wall.jpg"));
            imageBox = ImageIO.read(new File("images/box.jpg"));
            imageGoal = ImageIO.read(new File("images/goal.jpg"));
            imageGround = ImageIO.read(new File("images/ground.jpg"));
            imageError = ImageIO.read(new File("images/error.png"));
            imagePlayerUp = ImageIO.read(new File("images/up.jpg"));
            imagePlayerRight = ImageIO.read(new File("images/right.jpg"));
            imagePlayerDown = ImageIO.read(new File("images/down.jpg"));
            imagePlayerLeft = ImageIO.read(new File("images/left.jpg"));
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (model.isErrorState()) {
            drawError(g);
            return;
        }
        drawCurrentLevel(g);
        drawDesktop(g);
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
                    drawCurrentImagePlayerPosition(model.getPlayerPosition(), g, x, y);

                } else if (desktop[i][j] == 2) {
                    g.drawImage(imageWall, x, y, null);

                } else if (desktop[i][j] == 3) {
                    g.drawImage(imageBox, x, y, null);

                } else if (desktop[i][j] == 4) {
                    g.drawImage(imageGoal, x, y, null);

                } else {
                    g.drawImage(imageGround, x, y, null);
                }
                x = x + width + offset;
            }
            x = start;
            y = y + height + offset;
        }
    }

    private void drawError(Graphics g) {
        g.drawImage(imageError, 130, 70, null);
    }

    private void drawCurrentLevel(Graphics g) {
        g.setFont(statusFont);
        g.setColor(Color.DARK_GRAY);
        g.drawString("Level " + model.getCurrentLevel(), 465, 35);
    }

    private void drawCurrentImagePlayerPosition(int position, Graphics g, int x, int y) {
        switch (position) {
            case 1:
                g.drawImage(imagePlayerUp, x, y, null);
                break;
            case 2:
                g.drawImage(imagePlayerRight, x, y, null);
                break;
            case 3:
                g.drawImage(imagePlayerDown, x, y, null);
                break;
            case 4:
                g.drawImage(imagePlayerLeft, x, y, null);
                break;
            default:
                return;
        }
    }
}