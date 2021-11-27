import javax.swing.*;

public class Viewer {
    private final Canvas canvas;
    private final JFrame frame;

    public Viewer() {
        Controller controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);
        canvas.setBackground(canvas.getCanvasColor());

        frame = new JFrame("Sokoban Game MVC Pattern");
        frame.setSize(500, 550);
        frame.add("Center", canvas);
        frame.setLocation(500, 0);
        frame.setVisible(true);
        frame.addKeyListener(controller);
    }

    public void update() {
        canvas.repaint();
    }

    public void isError(boolean check) {
        canvas.setInitializationLevelError(check);
    }

    public boolean showDialog() {
        JOptionPane.showMessageDialog(frame, "You are won!!!");
        return true;
    }
}