package stu.tpp.components;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Director {
    @Id
    @GeneratedValue
    @Column(name="director_id")
    private Long directorId;

    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;

    @ManyToMany(mappedBy = "directors", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Film> films = new ArrayList<>();

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public Director() {
    }

    @Override
    public String toString() {
        String filmName = "'";
        for(Film film : films){
            filmName += film.getName() + "', ";
        }
        return  "Name: '" + name +
                ", surname: " + surname+
                ", films: " + filmName;
    }
}
