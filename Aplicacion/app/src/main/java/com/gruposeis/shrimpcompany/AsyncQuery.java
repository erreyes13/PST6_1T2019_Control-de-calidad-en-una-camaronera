package com.gruposeis.shrimpcompany;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AsyncQuery extends AsyncTask<String[],Void,String[]> {

    private Connection conexionMySQL;
    private Statement st = null;
    private ResultSet rs = null;

    /**
     * Este metodo nos permite ingresar a la base de datos obtener y enviar datos hacia ella.

     */
    protected String[] doInBackground(String[]... datos) {
        String sql = datos[0][5];
        String resultadoSQL = "";
        String[] totalResultadoSQL = null;
        int numColumnas = 0;
        int numFilas = 0;
        String SERVIDOR = datos[0][0];
        String PUERTO = datos[0][1];
        String BD = datos[0][2];
        String USUARIO = datos[0][3];
        String PASSWORD = datos[0][4];

        try{
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BD,
                    USUARIO,PASSWORD);

            st = conexionMySQL.createStatement();
            if(sql.contains("INSERT")){
                Log.d("Query: ",sql);
                st.executeUpdate(sql);
            }
            else {
            rs = st.executeQuery(sql);
            rs.last();
            numFilas = rs.getRow();
            if(numFilas == 0)
            {
                resultadoSQL = "No se ha producido ningún resultado. Revise la consulta realizada.\n";
            }else
            {
                for(int i=1;i<=numColumnas;i++){
                    if(i>1) resultadoSQL += ",";
                    resultadoSQL += rs.getMetaData().getColumnName(i);
                }
                resultadoSQL += "\n";
                rs.beforeFirst();
                while (rs.next())
                {
                    numColumnas = rs.getMetaData().getColumnCount();
                    for(int i=1;i<=numColumnas;i++){
                        if(i>1) resultadoSQL += ",";
                        resultadoSQL += rs.getString(i);
                    }
                    resultadoSQL += "\n";
                }
            }
            totalResultadoSQL = new String[]{ resultadoSQL,String.valueOf(numFilas),String.valueOf(numColumnas)};
            }

        }catch(SQLException ex)
        {
            Log.d("Error de conexion", ex.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                st.close();
                conexionMySQL.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return totalResultadoSQL;
    }



}
