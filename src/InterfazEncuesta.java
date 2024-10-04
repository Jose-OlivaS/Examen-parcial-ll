import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class InterfazEncuesta extends JFrame {
    private JComboBox<String> encuestaSelector;
    private JLabel preguntaLabel;
    private JTextField respuestaField;
    private JButton siguienteButton;
    private JButton finalizarButton;

    private List<Encuesta> encuestas;
    private Encuesta encuestaActual;
    private ConexionBD conexionBD;

    public InterfazEncuesta() {
        setTitle("Parcial II");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        conexionBD = new ConexionBD();

        inicializarEncuestas();

        encuestaSelector = new JComboBox<>(new String[]{"Encuesta de Satisfacción", "Encuesta de Preferencias"});
        preguntaLabel = new JLabel("   Seleccione una encuesta para comenzar");
        respuestaField = new JTextField(20);
        siguienteButton = new JButton("Siguiente");
        finalizarButton = new JButton("Finalizar");

        JPanel panelSuperior = new JPanel();
          panelSuperior.add(new JLabel("Seleccione una encuesta: "));
           panelSuperior.add(encuestaSelector);

         JPanel panelCentral = new JPanel(new GridLayout(3, 1));
         panelCentral.add(preguntaLabel);
        panelCentral.add(respuestaField);

         JPanel panelInferior = new JPanel();
         panelInferior.add(siguienteButton);
        panelInferior.add(finalizarButton);

        add(panelSuperior, BorderLayout.NORTH);
         add(panelCentral, BorderLayout.CENTER);
         add(panelInferior, BorderLayout.SOUTH);

        encuestaSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarEncuesta();
            }
        });

        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siguientePregunta();
            }
        });

        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarEncuesta();
            }
        });

         siguienteButton.setEnabled(false);
        finalizarButton.setEnabled(false);
    }

    private void inicializarEncuestas() {
        conexionBD.cargarEncuestas();

        encuestas = Arrays.asList(
                new Encuesta("Encuesta de Satisfacción", Arrays.asList(
                         "   ¿Qué tan satisfecho está con mi aplicación?",
                         "   ¿Aprobaría mi aplicación?",
                        "   ¿Qué cambiaria de mi aplicación?"
                ), conexionBD),
                new Encuesta("Encuesta de Preferencias", Arrays.asList(
                         "   ¿Prefieres Java o Python?",
                        "   ¿Prefieres IntelliJ o Netbeans?",
                         "   ¿Prefieres MySql o MongoDB?"
                ), conexionBD)
        );
    }

    private void iniciarEncuesta() {
        String nombreEncuesta = (String) encuestaSelector.getSelectedItem();
        for (Encuesta encuesta : encuestas) {
            if (encuesta.getNombre().equals(nombreEncuesta)) {
                encuestaActual = encuesta;
                break;
            }
        }

        if (encuestaActual != null) {
            encuestaActual.reiniciar();
            mostrarPregunta();
            siguienteButton.setEnabled(true);
            finalizarButton.setEnabled(true);
        }
    }

    private void mostrarPregunta() {
        String pregunta = encuestaActual.getPreguntaActual();
        if (pregunta != null) {
            preguntaLabel.setText(pregunta);
            respuestaField.setText("");
        }
    }

    private void siguientePregunta() {
        guardarRespuesta();
        if (!encuestaActual.encuestaCompletada()) {
            mostrarPregunta();
        } else {
            finalizarEncuesta();
        }
    }

    private void guardarRespuesta() {
        encuestaActual.agregarRespuesta(respuestaField.getText());
    }

    private void finalizarEncuesta() {
        if (!encuestaActual.encuestaCompletada()) {
            guardarRespuesta();
        }

        JOptionPane.showMessageDialog(this,
                "¡Gracias por completar la encuesta!\nSus respuestas han sido guardadas.",
                "Encuesta Finalizada",
                JOptionPane.INFORMATION_MESSAGE);

         preguntaLabel.setText("   Seleccione una encuesta para comenzar");
        respuestaField.setText("");
         siguienteButton.setEnabled(false);
        finalizarButton.setEnabled(false);
    }

    @Override
    public void dispose() {
        conexionBD.cerrarConexion();
        super.dispose();
    }
}