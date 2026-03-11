package model.bean;

import model.interf.IBean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Feedback extends IBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -7620112958563959140L;
    private int idFeedback;
    private String title;
    private String description;
    private int score;
    private int user;
    private int product;

    public Feedback() {
        super();
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(idFeedback, feedback.idFeedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFeedback);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "idFeedback=" + idFeedback +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", score=" + score +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
