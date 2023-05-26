/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
     public static String convertirFecha(Date fecha) {
        String formato = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            formato = sdf.format(fecha);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formato;
    }

    public static String convertirHora(Date hora) {
        String formato = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            formato = sdf.format(hora);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formato;
    }
}
