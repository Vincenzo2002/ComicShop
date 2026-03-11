package model.bean;

import model.interf.IBean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ProductOrder extends IBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -2036768715100700191L;
    private int idProductOrder;
    private int quantity;
    private double price;
    private int idOrder;
    private Product product;


    public ProductOrder() {
        super();
    }

    public int getIdProductOrder() {
        return idProductOrder;
    }

    public void setIdProductOrder(int idProductOrder) {
        this.idProductOrder = idProductOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder that = (ProductOrder) o;
        return idProductOrder == that.idProductOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductOrder);
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "idProductOrder=" + idProductOrder +
                ", quantity=" + quantity +
                ", price=" + price +
                ", idOrder=" + idOrder +
                ", product=" + product +
                '}';
    }
}
