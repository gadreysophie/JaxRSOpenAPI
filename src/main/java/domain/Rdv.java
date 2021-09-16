package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.Date;


@Entity
@XmlRootElement(name = "rdv")
@NamedQueries(
        {
                @NamedQuery(name="tousLesRdvParId", query="SELECT r FROM Rdv r WHERE r.id =:id"),
                @NamedQuery(name="tousLesRdvParProfEtDate", query="SELECT r FROM Rdv r WHERE r.professionnel=:prof AND  r.dateDebut >=:date AND r.dateFin <=: date2")
        }
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

    public Rdv(TypeRdv typeRdv, Professionnel professionnel, Utilisateur utilisateur, Date dateDebut) {
        this.typeRdv = typeRdv;
        this.professionnel = professionnel;
        this.utilisateur = utilisateur;
        this.dateDebut = dateDebut;

        Calendar c = Calendar.getInstance();
        c.setTime(dateDebut);
        c.add(Calendar.MINUTE, typeRdv.getDuree());
        this.dateFin = c.getTime();
    }

    @Id
    @GeneratedValue(generator="generatorIdRdv")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JsonIgnore
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
