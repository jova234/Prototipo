
import java.sql.Connection;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class articulosList {

    private String nombre;
    private String marca;
    private Date fecha;
    private Date fechaSalida;
    private String tipoArti;
    private int IdArticu;

    public articulosList() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoArti() {
        return tipoArti;
    }

    public void setTipoArti(String tipoArti) {
        this.tipoArti = tipoArti;
    }

    public int getIdArticu() {
        return IdArticu;
    }

    public void setIdArticu(int IdArticu) {
        this.IdArticu = IdArticu;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void Guardar(JTextField txtNombre, JTextField txtMarca, JTextField txtFechaEntra, JTextField txtTipoArti, JComboBox cbxItems) {
        setNombre(txtNombre.getText());
        setMarca(txtMarca.getText());
        setTipoArti(txtTipoArti.getText());

        try {
            Conexion conexion = Conexion.getInstance();
            Connection miConnection = conexion.getConexion();
            String opcionSeleccionada = cbxItems.getSelectedItem().toString();
            String consulta = "";
            Date fechaActual = new Date();

            consulta = "INSERT INTO ? (NOMBRE,MARCA,FECHA,TIPOARTICULO) VALUE (?,?,?,?,SELECT ? FROM ? WHERE NOMBRE = ? )";

            PreparedStatement cs = miConnection.prepareStatement(consulta);
            cs.setString(1, getNombre());
            cs.setString(2, getMarca());
            cs.setString(3, getTipoArti());
            cs.setString(4, Util.convertirFecha(fechaActual));
            cs.setString(5, opcionSeleccionada);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se inserto correctamente el registro");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Se ingreso correctamente los registros");
        }
    }

    public void MostrarTabla(JTable tbTotalExisten) {
        Conexion conexion = Conexion.getInstance();

        DefaultTableModel modelo = new DefaultTableModel();

        TableRowSorter<TableModel> ordenarTabla = new TableRowSorter<TableModel>(modelo);
        tbTotalExisten.setRowSorter(ordenarTabla);

        String sql = "";

        modelo.addColumn("NOMBRE");
        modelo.addColumn("MARCA");
        modelo.addColumn("FECHA");
        modelo.addColumn("TIPOARTIICULO");
        modelo.addColumn("FECHASalida");
        tbTotalExisten.setModel(modelo);

        sql = "SELECT * FROM ? WHERE ELIMINAR='true'";

        String[] dao = new String[5];
        Statement st;
        try {
            st = conexion.getConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                dao[0] = rs.getString(1);
                dao[1] = rs.getString(2);
                dao[2] = rs.getString(3);
                dao[3] = rs.getString(4);
                dao[4]=rs.getString(5);
                modelo.addRow(dao);

                if (tbTotalExisten != null) {
                    tbTotalExisten.getTableHeader().setReorderingAllowed(false);
                }
            }
            tbTotalExisten.setModel(modelo);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "NO se puede mostarr la tabla: " + e);
        }
    }

    public void Seleccionar(JTable tbTotalExisten, JTextField txtNombre, JTextField txtMarca, JTextField txtFechaEntra, JComboBox cbxItems) {
        try {
            int fila = tbTotalExisten.getSelectedRow();

            if (fila >= 0) {
                txtNombre.setText(tbTotalExisten.getValueAt(fila, 0).toString());
                txtMarca.setText(tbTotalExisten.getValueAt(fila, 1).toString());

                txtFechaEntra.setText("");
                if (tbTotalExisten.getValueAt(fila, 2) != null && tbTotalExisten.getValueAt(fila, 2).toString() != null && !tbTotalExisten.getValueAt(fila, 2).toString().isEmpty()) {
                    txtFechaEntra.setText(tbTotalExisten.getValueAt(fila, 2).toString());
                }
                cbxItems.setSelectedItem("");
                if (tbTotalExisten.getValueAt(fila, 0) != null && tbTotalExisten.getValueAt(fila, 0).toString() != null && !tbTotalExisten.getValueAt(fila, 0).toString().isEmpty()) {
                    cbxItems.setSelectedItem(tbTotalExisten.getValueAt(fila, 0).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo selecionar la columna " + e);
        }
    }

    public void Eliminar(JTextField txtIdArticulo) {
        if (!txtIdArticulo.getText().isEmpty()) {
            try {
                setIdArticu(Integer.parseInt(txtIdArticulo.getText()));
                Conexion conexion = Conexion.getInstance();
                Connection miConnection = conexion.getConexion();

                String consulta = "UPDATE ? SET ELIMINADO='false', FECHA=?, FECHASalida=? WHERE IdArticu=?";

                try {
                    Date fechaActual = new Date();
                    PreparedStatement cs = miConnection.prepareStatement(consulta);
                    cs.setString(1, Util.convertirFecha(fechaActual));
                    cs.setString(2, Util.convertirFecha(fechaActual));
                    cs.setInt(3, getIdArticu());
                    cs.execute();
                    JOptionPane.showMessageDialog(null, "Se elimino ocrrectamente el registro");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el registro");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "No se puede eliminar el registro");
            }
        }
    }

}
