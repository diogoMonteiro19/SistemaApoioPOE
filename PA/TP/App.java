import pt.isec.pa.apoio_poe.model.ApoioPOEManager;
import pt.isec.pa.apoio_poe.ui.text.ApoioPOETextUI;

public class App {
    public static void main(String[] args) throws Exception {
        ApoioPOEManager manager = new ApoioPOEManager();
        ApoioPOETextUI ui = new ApoioPOETextUI(manager);

      ui.start();
    }
}
