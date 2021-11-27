import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements KeyListener {
    private final Model model;

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case 37:
                model.move("Left");
                break;
            case 38:
                model.move("Up");
                break;
            case 39:
                model.move("Right");
                break;
            case 40:
                model.move("Down");
                break;
            default:
                return;
        }
    }

    public Model getModel() {
        return model;
    }
    @Override
    public void keyReleased(KeyEvent event) {}
    @Override
    public void keyTyped(KeyEvent event) {}
}