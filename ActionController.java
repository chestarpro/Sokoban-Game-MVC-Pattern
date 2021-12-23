import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionController implements ActionListener {
    private final Model model;

    public ActionController(Model model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        model.doAction(command);
    }
}