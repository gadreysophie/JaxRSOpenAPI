package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement(name = "typeRdv")
@NamedQueries(
        {
                @NamedQuery(name="typeRdvParId", query="SELECT t FROM TypeRdv t WHERE t.id =:id"),
                @NamedQuery(name="tousLesTypeRdvParProf", query="SELECT t FROM TypeRdv t WHERE t.professionnel =:prof"),
                @NamedQuery(name="minDureeTypeRdvByProf", query="SELECT MIN(t.duree) FROM TypeRdv t WHERE t.professionnel =:prof"),
        }
)

public class TypeRdv {
    private Long id;

    private String nom;

    private Professionnel professionnel;

    private Integer duree;

    private List<Rdv> rdvs = new ArrayList<>();

    public TypeRdv() {
    }

    public TypeRdv(String nom, Professionnel professionnel, Integer duree) {
        this.nom = nom;
        this.professionnel = professionnel;
        this.duree = duree;
    }

    @Id
    @GeneratedValue(generator="idTypeRdv")
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

    @OneToMany(mappedBy = "typeRdv", cascade = CascadeType.PERSIST)
    @JsonIgnore
    public List<Rdv> getRdvs() {
        return rdvs;
    }

    public void setRdvs(List<Rdv> rdvs) {
        this.rdvs = rdvs;
    }

    @Override
    public String toString() {
        return "Type de RDV [id=" + id + ", professionnel=" + professionnel + ", nom=" + nom + ", durée=" + duree + "]";
    }
}
