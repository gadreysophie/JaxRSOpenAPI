package dao;

import java.util.List;

import javax.persistence.EntityManager;

import domain.Utilisateur;


public class UtilisateurDao {

    private final EntityManager manager;

    public UtilisateurDao (EntityManager manager) {
        this.manager = manager;
    }

    public void createUtilisateurs() {
        int numOfUsers = manager.createQuery("Select a From Utilisateur a", Utilisateur.class).getResultList().size();
        if (numOfUsers == 0) {
            manager.persist(new Utilisateur("Gadrey","Sophie","sgadrey","sgadrey@univrennes.fr","sgadrey"));
            manager.persist(new Utilisateur("Le Chenadec","Erwann","elechenadec","elechenadec@univrennes.fr","elechenadec"));
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
        manager.persist(user);
    }


}
