/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Darlan
 */
class Conexao {
    
    private final String HOST = "jdbc:mysql://localhost:3306/curso?useTimezone=true&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "root";
    
    public Connection conectar() throws SQLException {
        Connection conexao = DriverManager.getConnection(HOST, USER, PASSWORD);
        return conexao;
    }
    
}
