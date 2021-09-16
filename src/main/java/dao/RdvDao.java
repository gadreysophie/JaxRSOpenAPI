package dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import domain.*;
import service.RdvService;

public class RdvDao {

    private final EntityManager manager = EntityManagerHelper.getEntityManager();


    public void createRdvs() throws ParseException {
        int numOfRdvs = manager.createQuery("Select a From Rdv a", Rdv.class).getResultList().size();
        if (numOfRdvs == 0) {
            ProfessionnelDao professionnelDao = new ProfessionnelDao();
            UtilisateurDao utilisateurDao = new UtilisateurDao();
            TypeRdvDao typeRdvDao = new TypeRdvDao();

            Professionnel professionnel = professionnelDao.searchProfessionnelById(2L);
            Utilisateur utilisateur = utilisateurDao.searchUserById(4L);
            Utilisateur utilisateur2 = utilisateurDao.searchUserById(3L);
            TypeRdv typeRdv = typeRdvDao.searchTypeRdvById(1L);
            TypeRdv typeRdv2 = typeRdvDao.searchTypeRdvById(2L);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            EntityTransaction tx = manager.getTransaction();
            tx.begin();
            manager.persist(new Rdv(typeRdv, professionnel, utilisateur, dateFormat.parse("2021-10-29 11:30")));
            manager.persist(new Rdv(typeRdv2, professionnel, utilisateur, dateFormat.parse("2021-10-29 14:30")));
            manager.persist(new Rdv(typeRdv2, professionnel, utilisateur2, dateFormat.parse("2021-10-29 10:30")));
            manager.persist(new Rdv(typeRdv, professionnel, utilisateur2, dateFormat.parse("2021-10-29 16:00")));
            manager.persist(new Rdv(typeRdv, professionnel, utilisateur2, dateFormat.parse("2021-10-29 09:00")));
            manager.persist(new Rdv(typeRdv, professionnel, utilisateur2, dateFormat.parse("2021-10-29 12:15")));
            manager.persist(new Rdv(typeRdv, professionnel, utilisateur2, dateFormat.parse("2021-10-29 14:00")));
            manager.persist(new Rdv(typeRdv2, professionnel, utilisateur2, dateFormat.parse("2021-10-29 16:30")));
            tx.commit();
        }
    }

    public void listRdvTest() throws ParseException {
        ProfessionnelDao professionnelDao = new ProfessionnelDao();

        Professionnel professionnel = professionnelDao.searchProfessionnelById(2L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateDuJour = "2021-10-29";
        List<Rdv> resultList = rdvsParProfessionnelEtDate(professionnel, dateFormat.parse(dateDuJour + " 00:00"));
        System.out.println("\nNombre de rdvs au " + dateDuJour + " pour " + professionnel.getNom() + " " + professionnel.getPrenom() + ": " + resultList.size());
        for (Rdv next : resultList) {
            System.out.println("Rdv suivant : " + next);
        }
        System.out.println();
    }

    public void listRdvs() {
        List<Rdv> resultList = manager.createQuery("Select a From Rdv a", Rdv.class).getResultList();
        System.out.println("\nNombre de rdvs :" + resultList.size());
        for (Rdv next : resultList) {
            System.out.println("Rdv suivant : " + next);
        }
        System.out.println();
    }

    public Rdv searchRdvById(Long id){
        return (Rdv) manager.createNamedQuery("tousLesRdvParId").setParameter("id", id).getSingleResult();
    }

    public List<Rdv> rdvsParProfessionnelEtDate(Professionnel prof, Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return manager.createNamedQuery("tousLesRdvParProfEtDate").setParameter("prof", prof).
                setParameter("date",date).setParameter("date2",c.getTime()).getResultList();

    }

    public void addRdv (Rdv rdv){
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.persist(rdv);
        tx.commit();
    }


    public void testListCreneauxDispo() throws ParseException {
        ProfessionnelDao professionnelDao = new ProfessionnelDao();
        TypeRdvDao typeRdvDao = new TypeRdvDao();

        Professionnel professionnel = professionnelDao.searchProfessionnelById(2L);
        TypeRdv typeRdv = typeRdvDao.searchTypeRdvById(1L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateDuJour = "2021-10-29";
        HashMap<Integer, List<Time>> creneauxDispo = RdvService.listCreneauxDispo(professionnel, dateFormat.parse(dateDuJour + " 00:00"), typeRdv);
        System.out.println("\nListe de créneaux disponibles pour le " + dateDuJour +" avec " + professionnel.getPrenom() + " " + professionnel.getNom() + " :");
        System.out.println(creneauxDispo);
    }

    public void deleteRdvById (Long id){
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.remove(searchRdvById(id));
        tx.commit();
    }
}
