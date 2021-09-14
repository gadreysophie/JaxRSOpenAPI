package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dao.UtilisateurDao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.Date;


@Entity
@XmlRootElement(name = "rdv")
@NamedQueries(
        @NamedQuery(name="tousLesRdvParId", query="SELECT p FROM Rdv p WHERE p.id =:id")
)
public class Rdv {
    private Long id;

    private TypeRdv typeRdv;

    private Professionnel professionnel;

    private Utilisateur utilisateur;

    private Date dateDebut;

    private Date dateFin;

    public Rdv() {
    }

    public Rdv(TypeRdv typeRdv, Professionnel professionnel, Utilisateur utilisateur, Date dateDebut, Date dateFin) {
        this.typeRdv = typeRdv;
        this.professionnel = professionnel;
        this.utilisateur = utilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public TypeRdv getTypeRdv() {
        return typeRdv;
    }

    public void setTypeRdv(TypeRdv typeRdv) {
        this.typeRdv = typeRdv;
    }

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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "RDV [id=" + id + ", Type de RDV=" + typeRdv + ", professionnel=" + professionnel + ", " +
                "utilisateur=" + utilisateur + ", Date début=" + dateDebut + ", date fin=" + dateFin + "]";
    }
}
