package dao;

import java.util.List;

import javax.persistence.EntityManager;

import domain.Departement;

public class DepartementDao {

    private final EntityManager manager;

    public DepartementDao (javax.persistence.EntityManager manager) {
        this.manager = manager;
    }

    public void createDepartements() {
        int numOfDepartements = manager.createQuery("Select a From Departement a", Departement.class).getResultList().size();
        if (numOfDepartements == 0) {
            manager.persist(new Departement("Master 1 - CCN"));
            manager.persist(new Departement("Master 2 - CCNa"));
        }
    }

    public void listDepartements() {
        List<Departement> resultList = manager.createQuery("Select a From Departement a", Departement.class).getResultList();
        System.out.println("Nombre de départements : " + resultList.size());
        for (Departement next : resultList) {
            System.out.println("Département suivant : " + next);
        }
    }

    public Departement departementsParId(Long id){
        return (Departement) manager.createNamedQuery("tousLesDepartementsParId").setParameter("id", id).getSingleResult();

    }

    public void addDepartement (Departement departement){
        manager.persist(departement);
    }
}
