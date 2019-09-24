/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Aluno;
import modelo.Boletim;
import modelo.Turma;

/**
 *
 * @author Darlan
 */
public class BoletimDAO extends BaseDAO implements GenericInterface<Boletim> {

    @Override
    protected void conectar() throws SQLException {
        conexao = conexaoFactory.conectar();
    }

    @Override
    protected void desconectar() {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int inserir(Boletim entidade) {
        try {
            conectar();
            String sql = "insert into boletins (id_turma, id_aluno, nota1, nota2, nota3, media) values (?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, entidade.getIdTurma());
            ps.setInt(2, entidade.getIdAluno());
            ps.setDouble(3, entidade.getNota1());
            ps.setDouble(4, entidade.getNota2());
            ps.setDouble(5, entidade.getNota3());
            ps.setDouble(6, entidade.getMedia());
            ps.execute();
            ResultSet tabela = ps.getGeneratedKeys();
            if (tabela.next()) {
                // retorna os ids que foram gerados na tebela de Boletins
                return tabela.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            desconectar();
        }
    }

    @Override
    public boolean alterar(Boletim entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Boletim> obterTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean apagar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boletim ObterTodosId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contabilizar() {
        try {
            conectar();
            String sql = "select count(id) as quantidade from boletins";
            Statement statement = conexao.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()){
                int quantidade = rs.getInt("quantidade");
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

    public ArrayList<Boletim> obterTodosBoletim(String buscaTurma, String buscaAluno) {
        buscaTurma = "%" + buscaTurma + "%";
        buscaAluno = "%" + buscaAluno + "%";
        ArrayList<Boletim> boletins = new ArrayList<>();
        
        try {
            conectar();  
            String sql = "select * from boletins"
                    + " inner join turmas on (boletins.id_turma = turmas.id)"
                    + " inner join alunos on (boletins.id_aluno = alunos.id)"
                    + "where turmas.turma like ? and alunos.aluno like ? order by boletins.id";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, buscaTurma);
            ps.setString(2, buscaAluno);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Boletim boletim = new Boletim();
                boletim.setId(rs.getInt("boletins.id"));
                boletim.setNota1(rs.getDouble("boletins.nota1"));
                boletim.setNota2(rs.getDouble("boletins.nota2"));
                boletim.setNota3(rs.getDouble("boletins.nota3"));
                boletim.setMedia(rs.getDouble("boletins.media"));
                boletim.setIdTurma(rs.getInt("Boletins.id_turma"));
                boletim.setIdAluno(rs.getInt("Boletins.id_aluno"));
                
                Turma turma = new Turma();
                turma.setId(rs.getInt("turmas.id"));
                turma.setTurma(rs.getString("turmas.turma"));
                boletim.setTurma(turma);
                
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("alunos.id"));
                aluno.setAluno(rs.getString("alunos.aluno"));
                boletim.setAluno(aluno);
                
                boletins.add(boletim);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
        return boletins;
    }
    
}
