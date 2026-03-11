package model.bean;

import model.interf.IBean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class CategoryProduct extends IBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -3498701277812188434L;
    private int idCategory;
    private int idProduct;

    public CategoryProduct() {
        super();
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryProduct that = (CategoryProduct) o;
        return idCategory == that.idCategory && idProduct == that.idProduct;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategory, idProduct);
    }

    @Override
    public String toString() {
        return "CategoryProduct{" +
                "idCategory=" + idCategory +
                ", idProduct=" + idProduct +
                '}';
    }
}
