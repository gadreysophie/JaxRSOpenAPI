package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dao.UtilisateurDao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement(name = "rdv")
@NamedQueries(
        @NamedQuery(name="tousLesRdvParId", query="SELECT p FROM Rdv p WHERE p.id =:id")
)
public class Rdv {
    private Long id;

    private String nom;

    private int dureeMinimale;

    private Professionnel professionnel;

    private Utilisateur utilisateur;

    public Rdv() {
    }

    public Rdv(String nom, int dureeMinimale, Professionnel professionnel, Utilisateur utilisateur) {
        this.nom = nom;
        this.dureeMinimale = dureeMinimale;
        this.professionnel = professionnel;
        this.utilisateur = utilisateur;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDureeMinimale(){return dureeMinimale;}

    public void setDureeMinimale(int dureeMinimale) {this.dureeMinimale = dureeMinimale;}

    @ManyToOne
    @JsonIgnore
    public Professionnel getProfessionnel() {
        return professionnel;
    }

    public void setProfessionnel(Professionnel professionnel) {
        this.professionnel = professionnel;
    }

    @ManyToOne
    @JsonIgnore
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "RDV [id=" + id + ", nom=" + nom + "]";
    }
}
