package model.dao;

import model.bean.Product;

public class CartEntry {

    private Product product;
    private int quantity;

    public CartEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /*Il metodo hashCode calcola un valore di hash per l'oggetto CartEntry utilizzando i suoi attributi.
    Utilizza una costante prime (31) per il calcolo del valore di hash.
    Se product è null, il valore di hash per product è 0, altrimenti viene utilizzato il valore di hash di product.
    Somma i valori di hash di product e quantity per ottenere il valore di hash finale.
    */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result + quantity;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CartEntry other = (CartEntry) obj;
        return product != null && product.getIdProduct() == other.product.getIdProduct();
    }

    @Override
    public String toString() {
        return "CartEntry [product=" + product + ", quantity=" + quantity + "]";
    }
}
