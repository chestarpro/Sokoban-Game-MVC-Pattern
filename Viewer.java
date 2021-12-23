import javax.swing.*;
import java.awt.*;

public class Viewer {
    private final Canvas canvas;
    private final JFrame frame;
    private JCheckBox checkBox;

    public Viewer() {
        KeyController keyController = new KeyController(this);

        Model model = keyController.getModel();
        model.initialization();

        ActionController actionController = new ActionController(model);
        VoiceController voiceController = new VoiceController(model);

        canvas = new Canvas(model);

        frame = new JFrame("Sokoban Game MVC Pattern");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(620, 650);
        frame.add(initMenuBar(actionController, voiceController), BorderLayout.NORTH);
        frame.add("Center", canvas);

        frame.setLocation(500, 50);
        frame.setVisible(true);
        frame.addKeyListener(keyController);
    }

    public void update() {
        canvas.repaint();
    }

    public boolean showDialog() {
        JOptionPane.showMessageDialog(frame,
                "       You are won!!!");
        return true;
    }

    private JMenuBar initMenuBar(ActionController controller, VoiceController voiceController) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.add(createMenuItem("Restart", controller));
        menu.add(createMenuItem("Exit", controller));
        JMenu levelMenu = new JMenu("Levels");
        for (int i = 1; i <= 9; i++) {
            levelMenu.add(createMenuItem("Level" + i, controller));
        }
        checkBox = new JCheckBox();
        checkBox.setOpaque(false);
        checkBox.setFocusable(false);
        checkBox.setFont(checkBox.getFont().deriveFont(Font.PLAIN));
        checkBox.setPreferredSize(new Dimension(100, 20));
        checkBox.setIcon(new ImageIcon("images/speaker.png"));
        checkBox.setSelectedIcon(new ImageIcon("images/mute.png"));
        checkBox.setSelected(false);
        checkBox.addActionListener(voiceController);

        menuBar.add(menu);
        menuBar.add(levelMenu);
        menuBar.add(checkBox);

        return menuBar;
    }

    private JMenuItem createMenuItem(String command, ActionController controller) {
        JMenuItem menuItem = new JMenuItem(command);
        menuItem.setActionCommand(command);
        menuItem.addActionListener(controller);
        return menuItem;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }
}