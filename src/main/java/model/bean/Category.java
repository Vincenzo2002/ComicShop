package model.bean;

import model.interf.IBean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Category extends IBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -8585393897098292081L;
    private int idCategory;
    private String name;

    public Category() {
        super();
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return idCategory == category.idCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategory);
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", name='" + name + '\'' +
                '}';
    }
}
