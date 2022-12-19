package stu.tpp.components;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Film {
    @Id
    @GeneratedValue
    @Column(name="film_id")
    private Long filmId;

    @Column(name="name")
    private String name;

    @Column(name="year")
    private Integer year;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "director_film",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors = new ArrayList<>();

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        String directorName = "";
        for(Director director:directors){
            directorName += director.getName() + " " + director.getSurname()+", ";
        }
        return "Film " +
                "name: '" + name + '\'' +
                ", year=" + year +
                ", directors: " + directorName +
                "country: " + country.getCountry();
    }

    public Film() {
    }
}
