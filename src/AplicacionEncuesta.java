import javax.swing.SwingUtilities;

public class AplicacionEncuesta {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazEncuesta().setVisible(true);
            }
        });
    }
}