package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import domain.Departement;

public class DepartementDao {

    private final EntityManager manager = EntityManagerHelper.getEntityManager();

    public void createDepartements() {
        int numOfDepartements = manager.createQuery("Select a From Departement a", Departement.class).getResultList().size();
        if (numOfDepartements == 0) {
            EntityTransaction tx = manager.getTransaction();
            tx.begin();
            manager.persist(new Departement("Master 1 - CCN"));
            manager.persist(new Departement("Master 2 - CCNa"));
            tx.commit();
        }
    }

    public void listDepartements() {
        List<Departement> resultList = manager.createQuery("Select a From Departement a", Departement.class).getResultList();
        System.out.println("\nNombre de départements : " + resultList.size());
        for (Departement next : resultList) {
            System.out.println("Département suivant : " + next);
        }
        System.out.println();
    }

    public Departement searchDepartementById(Long id){
        return (Departement) manager.createNamedQuery("departementParId").setParameter("id", id).getSingleResult();

    }

    public void addDepartement (Departement departement){
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.persist(departement);
        tx.commit();
    }

    public void deleteDepartById (Long id){
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.remove(searchDepartementById(id));
        tx.commit();
    }
}
