package dao;

import java.util.ArrayList;

/**
 *
 * @author Darlan
 */
public interface GenericInterface<T> {
    
    int inserir(T entidade);
    
    boolean alterar(T entidade);
    
    ArrayList<T> obterTodos();
    
    boolean apagar(int id);
    
    T ObterTodosId(int id);
    
    int contabilizar();
    
}
