/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
//  */

// CODIGO FORNECIDO NA ESPECIFICAÇÃO

package lista;

/**
 *
 * @author victoriocarvalho
 */
public class No<T> {
    private T valor;
    private No<T> prox;

    
    public No(T valor){
        this.valor = valor;
        this.prox=null;
    }
    /**
     * @return the valor
     */
    public T getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    /**
     * @return the prox
     */
    public No<T> getProx() {
        return prox;
    }

    /**
     * @param prox the prox to set
     */
    public void setProx(No<T> prox) {
        this.prox = prox;
    }
    
    
}
