package dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import domain.*;

public class RdvDao {
    private EntityManager manager;

    public RdvDao (javax.persistence.EntityManager manager) {
        this.manager = manager;
    }

    public void createRdvs() throws ParseException {
        int numOfRdvs = manager.createQuery("Select a From Rdv a", Rdv.class).getResultList().size();
        if (numOfRdvs == 0) {
            ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);
            UtilisateurDao utilisateurDao = new UtilisateurDao(manager);
            TypeRdvDao typeRdvDao = new TypeRdvDao(manager);

            Professionnel professionnel = professionnelDao.professionnelsParId(4L);
            Utilisateur utilisateur = utilisateurDao.searchUserById(6L);
            TypeRdv typeRdv = typeRdvDao.typeRdvsParId(7L);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            manager.persist(new Rdv(typeRdv, professionnel, utilisateur, dateFormat.parse("2021-10-29 11:30"), dateFormat.parse("2021-10-29 12:00")));
            manager.persist(new Rdv(typeRdv, professionnel, utilisateur, dateFormat.parse("2021-10-29 14:30"), dateFormat.parse("2021-10-29 15:00")));
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
