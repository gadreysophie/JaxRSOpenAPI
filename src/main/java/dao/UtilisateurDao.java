package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import domain.Utilisateur;


public class UtilisateurDao {

    private final EntityManager manager = EntityManagerHelper.getEntityManager();

    public void createUtilisateurs() {
        int numOfUsers = manager.createQuery("Select a From Utilisateur a", Utilisateur.class).getResultList().size();
        if (numOfUsers == 0) {
            EntityTransaction tx = manager.getTransaction();
            tx.begin();
            manager.persist(new Utilisateur("Gadrey","Sophie","sgadrey","sgadrey@univrennes.fr","sgadrey"));
            manager.persist(new Utilisateur("Le Chenadec","Erwann","elechenadec","elechenadec@univrennes.fr","elechenadec"));
            tx.commit();
        }
    }

    public void printListUtilisateurs() {
        List<Utilisateur> resultList = manager.createQuery("Select a From Utilisateur a", Utilisateur.class).getResultList();
        System.out.println("\nNombre d'utilisateurs : " + resultList.size());
        for (Utilisateur next : resultList) {
            System.out.println("Utilisateur suivant : " + next);
        }
        System.out.println();
    }

    public List<Utilisateur> listUtilisateurs() {
        return manager.createQuery("Select a From Utilisateur a", Utilisateur.class).getResultList();
    }

    public Utilisateur searchUserById(Long id){
        return (Utilisateur) manager.createNamedQuery("searchUserById").setParameter("id", id).getSingleResult();
    }

    public void addUser(Utilisateur user){
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.persist(user);
        tx.commit();
    }

    public void deleteUserById (Long id){
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.remove(searchUserById(id));
        tx.commit();
    }


}
