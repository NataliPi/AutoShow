package com.natali_pi.autoshow.model;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Natali-Pi on 05.06.2017.
 */


public final class DBRequest extends AsyncTask<String, Void, JSONArray> {
    static String MSSQL_DB = "jdbc:jtds:sqlserver://<DB_IP>:<YOUR_DB_PORT>:/<YOUR_DB_NAME>;";
    static String MSSQL_LOGIN = "<YOUR_DB_LOGIN>";
    static String MSSQL_PASS = "<YOUR_DB_PASS>";

    public void init(String ip, String login, String password) {
    MSSQL_LOGIN = login;
        MSSQL_PASS = password;
    }

    @Override
    protected JSONArray doInBackground(String... query) {
        JSONArray resultSet = new JSONArray();
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            try {
                con = DriverManager.getConnection(MSSQL_DB, MSSQL_LOGIN, MSSQL_PASS);
                if (con != null) {
                    st = con.createStatement();
                    rs = st.executeQuery(query[0]);
                    if (rs != null) {
                        int columnCount = rs.getMetaData().getColumnCount();
                        // Сохранение данных в JSONArray
                        while (rs.next()) {
                            JSONObject rowObject = new JSONObject();
                            for (int i = 1; i <= columnCount; i++) {
                                rowObject.put(rs.getMetaData().getColumnName(i), (rs.getString(i) != null) ? rs.getString(i) : "");
                            }
                            resultSet.put(rowObject);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (st != null) st.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        // TODO: вернуть результат
    }
}
