package dao;

import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;

import domain.Departement;
import domain.Professionnel;
import domain.Rdv;
import domain.Utilisateur;

public class RdvDao {
    private EntityManager manager;

    public RdvDao (javax.persistence.EntityManager manager) {
        this.manager = manager;
    }

    public void createRdvs() {
        int numOfRdvs = manager.createQuery("Select a From Rdv a", Rdv.class).getResultList().size();
        if (numOfRdvs == 0) {
            ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);
            UtilisateurDao utilisateurDao = new UtilisateurDao(manager);

            Professionnel professionnel = professionnelDao.professionnelsParId(4L);
            Utilisateur utilisateur = utilisateurDao.searchUserById(6L);
            manager.persist(new Rdv("MAN", professionnel, utilisateur));
            manager.persist(new Rdv("TAA", professionnel, utilisateur));
        }
    }

    public void listRdvs() {
        List<Rdv> resultList = manager.createQuery("Select a From Rdv a", Rdv.class).getResultList();
        System.out.println("Nombre de rdvs :" + resultList.size());
        for (Rdv next : resultList) {
            System.out.println("Rdv suivant : " + next);
        }
    }

    public Rdv rdvsParId(Long id){
        return (Rdv) manager.createNamedQuery("tousLesRdvParId").setParameter("id", id).getSingleResult();

    }

    public void addRdv (Rdv rdv){
        manager.persist(rdv);
    }

}
