import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VoiceController implements ActionListener {

    private final Model model;

    public VoiceController(Model model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (model.isSelected()) {
            model.setVolume(0);
        } else {
            model.setVolume(0.9f);
        }
    }
}