package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "typerdv")
public class TypeRdv {
    private Long id;

    private String nom;

    private Professionnel professionnel;

    private Integer duree;

    public TypeRdv() {
    }

    public TypeRdv(String nom, Professionnel professionnel, Integer duree) {
        this.nom = nom;
        this.professionnel = professionnel;
        this.duree = duree;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @ManyToOne
    @JsonIgnore
    public Professionnel getProfessionnel() {
        return professionnel;
    }

    public void setProfessionnel(Professionnel professionnel) {
        this.professionnel = professionnel;
    }
}
