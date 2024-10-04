import java.sql.*;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/encuestas_db";
     private static final String USUARIO = "root";
      private static final String CONTRASEÑA = "EduTalon12345";

    private Connection conexion;

    public ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarRespuesta(String nombreEncuesta, String pregunta, String respuesta) {
        String sql = "INSERT INTO respuestas (nombre_encuesta, pregunta, respuesta) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombreEncuesta);
            pstmt.setString(2, pregunta);
            pstmt.setString(3, respuesta);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarEncuestas() {
        String sql = "SELECT * FROM encuestas";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nombreEncuesta = rs.getString("nombre");
                System.out.println("Encuesta cargada: " + nombreEncuesta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}