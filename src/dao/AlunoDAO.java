
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Aluno;

public class AlunoDAO extends BaseDAO implements GenericInterface<Aluno>{

    @Override
    protected void conectar() throws SQLException {
        conexao = conexaoFactory.conectar();
    }

    @Override
    protected void desconectar() {
        if (conexao != null)
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public int inserir(Aluno entidade) {
        try {
            conectar();
            String sql = "insert into alunos (aluno) values (?)";
            PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getAluno());
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
    public boolean alterar(Aluno entidade) {
        try {
            conectar();
            String sql = "update alunos set aluno = ? where id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getAluno());
            ps.setInt(2, entidade.getId());
            return ps.executeUpdate() == 1;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public ArrayList<Aluno> obterTodos(String nome) {
        try {
            conectar();
            String sql = "select * from alunos where aluno like ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%" + nome + "%");
            
            ResultSet tabelaEmMemoria = ps.executeQuery();
            
            ArrayList<Aluno> alunos = new ArrayList<>();
            
            while (tabelaEmMemoria.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(tabelaEmMemoria.getInt("id"));
                aluno.setAluno(tabelaEmMemoria.getString("aluno"));
                alunos.add(aluno);
            }
            return alunos;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            desconectar();
        }
    }

    @Override
    public boolean apagar(int id) {
        try {
            conectar();
            String sql = "delete from alunos where id = ?";
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
    public Aluno ObterTodosId(int id) {
        Aluno aluno = null;
        
        try {
            conectar();
            String sql = "select * from alunos where id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setAluno(rs.getString("aluno"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
        return aluno;
    }

    @Override
    public int contabilizar() {
        try {
            conectar();
            String sql = "select count(id) as quantidade from alunos";
            Statement statement = conexao.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int quantidade = resultSet.getInt("quantidade");
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

    @Override
    public ArrayList<Aluno> obterTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
