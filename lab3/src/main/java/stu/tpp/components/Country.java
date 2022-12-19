package stu.tpp.components;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "country")
@Entity
public class Country {
    @Id
    @Column(name = "country_id")
    private String country;

    @Column(name = "rating")
    private Integer rating;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<Film> films = new ArrayList<>();

    public Country(String country, Integer rating, List<Film> films) {
        this.country = country;
        this.rating = rating;
        this.films = films;
    }
    public Country() {
    }

    public String getCountry() {
        return country;
    }

    public Integer getRating() {
        return rating;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    @Override
    public String toString() {
        String filmName = " ";
        for(Film film : films){
            filmName += film.getName() + ", ";
        }

        return  "country: " + country +
                ", films: '" + filmName  + '\''+
                ", rating = " + rating;
    }
}
