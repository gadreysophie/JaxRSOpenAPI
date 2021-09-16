package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("P")
@XmlRootElement(name = "prof")
@NamedQueries(
        {
                @NamedQuery(name="tousLesProfessionnelsParNom", query="SELECT p FROM Professionnel p WHERE p.nom LIKE CONCAT('%',:name,'%') ORDER BY p.nom"),
                @NamedQuery(name="professionnelParId", query="SELECT p FROM Professionnel p WHERE p.id =:id")
        }
)
public class Professionnel extends Personne {

    private Departement departement;

    private List<Rdv> rdvs = new ArrayList<>();

    private Time heureDebut;
    private Time heureFin;
    private Time heureDebutPause;
    private Time heureFinPause;
    // Attention : du dimanche au samedi
    private String joursDePresence;

    private List<TypeRdv> typeDeRdv;


    public Professionnel() {
    }

    public Professionnel(String name, Departement department, Time heureDebut, Time heureFin, Time heureDebutPause,
                         Time heureFinPause, String joursDePresence) {
        super(name);
        this.departement = department;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.heureDebutPause = heureDebutPause;
        this.heureFinPause = heureFinPause;
        this.joursDePresence = joursDePresence;
    }

    public Professionnel(String nom, String prenom, String identifiant, String mail, String mdp, Departement department,
                         Time heureDebut, Time heureFin, Time heureDebutPause, Time heureFinPause,
                         String joursDePresence) {
        super(nom, prenom, identifiant, mail, mdp);
        this.departement = department;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.heureDebutPause = heureDebutPause;
        this.heureFinPause = heureFinPause;
        this.joursDePresence = joursDePresence;
    }

    @ManyToOne
    @JsonIgnore
    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    @OneToMany(mappedBy = "professionnel", cascade = CascadeType.PERSIST)
    @JsonIgnore
    public List<Rdv> getRdvs() {
        return rdvs;
    }

    public void setRdvs(List<Rdv> rdvs){
        this.rdvs = rdvs;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public Time getHeureDebutPause() {
        return heureDebutPause;
    }

    public void setHeureDebutPause(Time heureDebutPause) {
        this.heureDebutPause = heureDebutPause;
    }

    public Time getHeureFinPause() {
        return heureFinPause;
    }

    public void setHeureFinPause(Time heureFinPause) {
        this.heureFinPause = heureFinPause;
    }

    public String getJoursDePresence() {
        return joursDePresence;
    }

    public void setJoursDePresence(String joursDePresence) {
        this.joursDePresence = joursDePresence;
    }

    @OneToMany(mappedBy = "professionnel", cascade = CascadeType.PERSIST)
    @JsonIgnore
    public List<TypeRdv> getTypeDeRdv() {
        return typeDeRdv;
    }

    public void setTypeDeRdv(List<TypeRdv> typeDeRdv) {
        this.typeDeRdv = typeDeRdv;
    }

    @Override
    public String toString() {
        return "Professionnel [id=" + getId() + ", nom=" + getNom() + ", prénom=" + getPrenom() + ", département="
                + departement.getNom() + "]";
    }

}

