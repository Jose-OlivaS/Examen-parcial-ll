import java.util.ArrayList;
import java.util.List;

public class Encuesta {
     private String nombre;
    private List<String> preguntas;
    private List<String> respuestas;
    private int preguntaActual;
    private ConexionBD conexionBD;

    public Encuesta(String nombre, List<String> preguntas, ConexionBD conexionBD) {
         this.nombre = nombre;
          this.preguntas = preguntas;
           this.respuestas = new ArrayList<>();
         this.preguntaActual = 0;
         this.conexionBD = conexionBD;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPreguntaActual() {
        if (preguntaActual < preguntas.size()) {
            return preguntas.get(preguntaActual);
        }
        return null;
    }

    public void agregarRespuesta(String respuesta) {
        respuestas.add(respuesta);
        conexionBD.guardarRespuesta(nombre, preguntas.get(preguntaActual), respuesta);
        preguntaActual++;
    }

    public boolean encuestaCompletada() {
        return preguntaActual >= preguntas.size();
    }

    public void reiniciar() {
        respuestas.clear();
        preguntaActual = 0;
    }
}