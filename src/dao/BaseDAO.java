
package dao;

import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author Darlan
 */
public abstract class BaseDAO {
    
    protected Conexao conexaoFactory = new Conexao();
    protected Connection conexao = null;
    protected abstract void conectar() throws SQLException;
    protected abstract void desconectar();
}
