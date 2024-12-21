package com.ism.core.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class DataSourceImpl implements DataSource {

    protected PreparedStatement ps;
    protected Connection conn = null;

    private final String URL = "jdbc:postgresql://localhost:5432/gestion_commande"; 
    private final String USER = "postgres";
    private final String PASSWORD = "008088"; 
    private final String DRIVER = "org.postgresql.Driver"; 

    @Override
    public Connection connexion() throws SQLException {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trouvé.");
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void closeConnexion(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
    
    @Override
    public void preparedStatement(String sql) throws SQLException {
        this.connexion();
        if (sql.toUpperCase().trim().startsWith("INSERT")) {
            ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            ps=conn.prepareStatement(sql);
        }
    }

    @Override
    public ResultSet executeQuery(String query, PreparedStatement ps) throws SQLException {
        return ps.executeQuery();
    }

    @Override
    public int executeUpdate(String query, PreparedStatement ps) throws SQLException {
        return ps.executeUpdate();
    }

    @Override
    public String generateSQL(String baseQuery, Map<String, Object> conditions) {
        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        if (conditions != null && !conditions.isEmpty()) {
            queryBuilder.append(" WHERE ");
            conditions.forEach((key, value) -> {
                queryBuilder.append(key).append(" = ?");
                queryBuilder.append(" AND ");
            });
            queryBuilder.setLength(queryBuilder.length() - 5);
        }
        return queryBuilder.toString();
    }

    @Override
    public void init(String sql) throws SQLException {
        String sqlUpperCase = sql.toUpperCase().trim();

        if (sqlUpperCase.startsWith("INSERT")) {
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        } else {
            ps = conn.prepareStatement(sql);
        }
    }

    @Override
    public void setFields(PreparedStatement ps, List<Object> params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Integer) {
                    ps.setInt(i + 1, (Integer) param);
                } else if (param instanceof String) {
                    ps.setString(i + 1, (String) param);
                } else if (param instanceof Double) {
                    ps.setDouble(i + 1, (Double) param);
                } else if (param instanceof Float) {
                    ps.setFloat(i + 1, (Float) param);
                } else if (param instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) param);
                } else {
                    ps.setObject(i + 1, param);
                }
            }
        }
    }

   


}