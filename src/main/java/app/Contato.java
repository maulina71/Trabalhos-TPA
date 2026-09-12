package app;

/**
 * Cada contato possui nome e telefone.
 */

public class Contato {
    
    private String nome;
    private String telefone;
    
    /**
     * Construtor da classe Contato
     * @param nome Nome do contato
     * @param telefone Telefone do contato
     */
    public Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }
    
    /**
     * Retorna o nome do contato
     * @return Nome do contato
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Define o nome do contato
     * @param nome Novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Retorna o telefone do contato
     * @return Telefone do contato
     */
    public String getTelefone() {
        return telefone;
    }
    
    /**
     * Define o telefone do contato
     * @param telefone Novo telefone
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    /**
     * @return String no formato "nome-telefone"
     */
    
    @Override
    public String toString() {
        return nome + "," + telefone;
    }
    
    /**
     * Sobrescreve equals para comparar contatos pelo telefone
     * @param obj Objeto a ser comparado
     * @return true se os telefones são iguais
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contato contato = (Contato) obj;
        return telefone.equals(contato.telefone);
    }
    
    /**
     * Sobrescreve hashCode para manter consistência com equals
     * @return Hash code baseado no telefone
     */
    @Override
    public int hashCode() {
        return telefone.hashCode();
    }
}
