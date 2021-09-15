package dao;

import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;

import domain.Departement;
import domain.Professionnel;

public class ProfessionnelDao {

    private final EntityManager manager;

    public ProfessionnelDao (EntityManager manager) {
        this.manager = manager;
    }

    public void createProfessionnels() {
        int numOfEmployees = manager.createQuery("Select a From Professionnel a", Professionnel.class).getResultList().size();
        if (numOfEmployees == 0) {
            DepartementDao departementDao = new DepartementDao(manager);

            Departement departement = departementDao.departementsParId(1L);
            Departement departement2 = departementDao.departementsParId(2L);
            manager.persist(new Professionnel("Vorc'h","Raoul","rvorch", "rvorch@univrennes.fr",
                    "rvorch",departement, Time.valueOf("10:30:00"), Time.valueOf("18:00:00"), Time.valueOf("12:30:00"),
                    Time.valueOf("13:30:00"), "1110110"));
            manager.persist(new Professionnel("Bousse","Marc","mbousse","mbousse@univrennes.fr",
                    "mbousse", departement2, Time.valueOf("9:00:00"), Time.valueOf("17:00:00"), Time.valueOf("12:30:00"),
                    Time.valueOf("14:00:00"),"1110110"));
        }
    }

    public void listProfessionnels() {
        List<Professionnel> resultList = manager.createQuery("Select a From Professionnel a", Professionnel.class).getResultList();
        System.out.println("\nNombre de professionnels : " + resultList.size());
        for (Professionnel next : resultList) {
            System.out.println("Professionnel suivant : " + next);
        }
        System.out.println();
    }

    public void listProfessionnelsParNom(String name){
        List<Professionnel> resultList = manager.createNamedQuery("tousLesProfessionnelsParNom").setParameter("name", name).getResultList();
        System.out.println("Nombre de professionnels : " + resultList.size());
        for (Professionnel next : resultList) {
            System.out.println("Professionnel suivant : " + next);
        }
    }

    public Professionnel professionnelsParId(Long id){
        return (Professionnel) manager.createNamedQuery("tousLesProfessionnelsParId").setParameter("id", id).getSingleResult();

    }

    public void addProf(Professionnel prof){
        manager.persist(prof);
    }


}
