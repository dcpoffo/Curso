
package dao;

import com.mysql.cj.protocol.Resultset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String sql = "insert into alunos (id_turma, aluno, nota1, nota2, nota3) values (?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, entidade.getIdTurma());
            ps.setString(2, entidade.getAluno());
            ps.setDouble(3, entidade.getNota1());
            ps.setDouble(4, entidade.getNota2());
            ps.setDouble(5, entidade.getNote3());
            ps.execute();
            ResultSet tabela = ps.getGeneratedKeys();
            if (tabela.next()) {
                return tabela.getInt(1);
            }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Aluno> obterTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean apagar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Aluno ObterTodosId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contabilizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
