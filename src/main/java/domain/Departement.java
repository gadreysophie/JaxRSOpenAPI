package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "dept")
@NamedQueries(
        {
                @NamedQuery(name="departementParId", query="SELECT p FROM Departement p WHERE p.id =:id")
        }
)
public class Departement {
    private Long id;

    private String nom;

    private List<Professionnel> professionnels = new ArrayList<>();

    public Departement() {
    }

    public Departement(String nom) {
        this.nom = nom;
    }

    @Id
    @GeneratedValue(generator="generatorIdDepartement")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Departement [id=" + id + ", nom=" + nom + "]";
    }


    @OneToMany(mappedBy = "departement", cascade = CascadeType.PERSIST)
    public List<Professionnel> getProfessionnels() {
        return professionnels;
    }

    public void setProfessionnels(List<Professionnel> professionnels) {
        this.professionnels = professionnels;
    }


}