package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Turma;

/**
 *
 * @author Darlan
 */
public class TurmaDAO extends BaseDAO implements GenericInterface<Turma>{

    @Override
    protected void conectar() throws SQLException {
        conexao = conexaoFactory.conectar();
    }

    @Override
    protected void desconectar() {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int inserir(Turma entidade) {
        try {
            conectar();
            String sql = "insert into turmas (turma) values (?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getTurma());
            ps.execute();
            return 0;
         } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            desconectar();
        }
    }

    @Override
    public boolean alterar(Turma entidade) {
        try {
            conectar();
            String sql = "update turmas set turma = ? where id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getTurma());
            ps.setInt(2, entidade.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public ArrayList<Turma> obterTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean apagar(int id) {
        try {
            conectar();
            String sql = "delete from turmas where id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            int quantidadeAfetada = ps.executeUpdate();
            return quantidadeAfetada == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public Turma ObterTodosId(int id) {
        Turma turma = null;
        
        try {
            conectar();
            String sql = "select * from turmas where id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setTurma(rs.getString("turma"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            desconectar();
        }
        return turma;
    }

    @Override
    public int contabilizar() {
        try {
            conectar();
            String sql = "select count(id) as QUANTIDADE from turmas";
            Statement statement = conexao.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int quantidade = resultSet.getInt("QUANTIDADE");
                return quantidade;
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; 
        } finally {
            desconectar();
        }
    }

    public ArrayList<Turma> obterTodos(String nome) {
        try {
            conectar();
            String sql = "select * from turmas where turma like ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%" + nome + "%");
            
            ResultSet tabelaEmMemoria = ps.executeQuery();
            
            ArrayList<Turma> turmas = new ArrayList<>();
            
            while (tabelaEmMemoria.next()) {
                Turma turma = new Turma();
                turma.setId(tabelaEmMemoria.getInt("id"));
                turma.setTurma(tabelaEmMemoria.getString("turma"));
                turmas.add(turma);
            }
            return turmas;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            desconectar();
        }
    }
    
}
