package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import domain.*;

public class RdvDao {
    private final EntityManager manager;

    public RdvDao (javax.persistence.EntityManager manager) {
        this.manager = manager;
    }

    public void createRdvs() throws ParseException {
        int numOfRdvs = manager.createQuery("Select a From Rdv a", Rdv.class).getResultList().size();
        if (numOfRdvs == 0) {
            ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);
            UtilisateurDao utilisateurDao = new UtilisateurDao(manager);
            TypeRdvDao typeRdvDao = new TypeRdvDao(manager);

            Professionnel professionnel = professionnelDao.professionnelsParId(2L);
            Utilisateur utilisateur = utilisateurDao.searchUserById(4L);
            TypeRdv typeRdv = typeRdvDao.typeRdvsParId(1L);
            TypeRdv typeRdv2 = typeRdvDao.typeRdvsParId(2L);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            manager.persist(new Rdv(typeRdv, professionnel, utilisateur, dateFormat.parse("2021-10-29 11:30")));
            manager.persist(new Rdv(typeRdv2, professionnel, utilisateur, dateFormat.parse("2021-10-30 14:30")));
        }
    }

    public void listRdvTest() throws ParseException {
        ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);

        Professionnel professionnel = professionnelDao.professionnelsParId(2L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateDuJour = "2021-10-29";
        List<Rdv> resultList = rdvsParProfessionnelEtDate(professionnel, dateFormat.parse(dateDuJour + " 00:00"));
        System.out.println("Nombre de rdvs au " + dateDuJour + " pour " + professionnel.getNom() + " " + professionnel.getPrenom() + ": " + resultList.size());
        for (Rdv next : resultList) {
            System.out.println("Rdv suivant : " + next);
        }
    }

    public void listRdvs() {
        List<Rdv> resultList = manager.createQuery("Select a From Rdv a", Rdv.class).getResultList();
        System.out.println("Nombre de rdvs :" + resultList.size());
        for (Rdv next : resultList) {
            System.out.println("Rdv suivant : " + next);
        }
    }

    public Rdv rdvParId(Long id){
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
        manager.persist(rdv);
    }

    public void listCreneauxDispo(Professionnel prof, Date date, TypeRdv typeRdv){
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // Liste de créneaux dispo initialisée à vide

        String [] joursPresenceTab = prof.getJoursDePresence().split("(?!^)");
        if(joursPresenceTab[c.get(Calendar.DAY_OF_WEEK)-1].equals("1")){
            List<Rdv> resultCreneauxRes = rdvsParProfessionnelEtDate(prof, date);
            Integer dureeTypeRdv = typeRdv.getDuree();


            // Minimum duree
            TypeRdvDao typeRdvDao = new TypeRdvDao(manager);
            //Integer minDuree = ;
            System.out.println("\nDuree minimum : " + typeRdvDao.minDureeTypeRdvByProf(prof));
            // Generate liste de creneaux dispo

        }
    }

    public void testListCreneauxDispo() throws ParseException {
        ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);
        TypeRdvDao typeRdvDao = new TypeRdvDao(manager);

        Professionnel professionnel = professionnelDao.professionnelsParId(2L);
        TypeRdv typeRdv = typeRdvDao.typeRdvsParId(2L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateDuJour = "2021-10-29";
        listCreneauxDispo(professionnel, dateFormat.parse(dateDuJour + " 00:00"), typeRdv);

    }

}
