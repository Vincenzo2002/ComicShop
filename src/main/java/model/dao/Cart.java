package model.dao;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

public class Cart implements Serializable {
    @Serial
    private static final long serialVersionUID = -5793790927557578337L;
    private HashSet<CartEntry> product = new HashSet<>();

    public Cart() {
        this.product = new HashSet<>();
    }

    //Restituisce un iteratore per iterare sugli elementi del carrello
    public Iterator<CartEntry> getProduct() {
        return this.product.iterator();
    }

    //Restituisce il numero di prodotti nel carrello
    public int getNumProduct() {
        return this.product.size();
    }

    public void set(CartEntry entry) {
        //Aggiorna un prodotto del carrello
        CartEntry product = this.findById(entry.getProduct().getIdProduct());
        //Se la quantita e minore di 0 elimina il prodotto
        if (entry.getQuantity() <= 0) {
            this.product.remove(product);
            return;
        }
        //Se il prodtto esiste gia nel carrello e la quantità è diversa aggiorna la quantita
        if (product != null && product.getQuantity() != entry.getQuantity()) {
            this.product.remove(product);
            this.product.add(entry);
        //Se il prodotto non esiste lo aggiunge
        } else if (product == null) {
            this.product.add(entry);
        }
    }

    public void removeProduct(int productId) {
        //Elimina un prodotto dal carrello in base all'id
        Iterator<CartEntry> iterator = product.iterator();
        while (iterator.hasNext()) {
            CartEntry entry = iterator.next();
            if (entry.getProduct().getIdProduct() == productId) {
                iterator.remove(); // Rimuove l'elemento dalla lista
            }
        }
    }

    //Elimina tutti i prodotti dal carrello
    public void clear() {
        this.product.clear();
    }

    //Cerca e restituisce un CartEntry nel carrello in base al suo ID. Se non trova l'elemento, restituisce null.
    public CartEntry findById(int productId) {
        for (CartEntry c : this.product) {
            if (c.getProduct().getIdProduct() == productId) return c;
        }
        return null;
    }

    //Restituisce una rappresentazione in stringa del carrello, includendo i prodotti contenuti.
    @Override
    public String toString() {
        return "Cart [product=" + product + "]";
    }
}
