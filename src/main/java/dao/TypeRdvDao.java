package dao;

import domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TypeRdvDao {

    private final EntityManager manager = EntityManagerHelper.getEntityManager();

    public void createTypeRdvs() {
        int numOfTypeRdvs = manager.createQuery("Select a From TypeRdv a", TypeRdv.class).getResultList().size();
        if (numOfTypeRdvs == 0) {
            ProfessionnelDao professionnelDao = new ProfessionnelDao();

            Professionnel professionnel = professionnelDao.searchProfessionnelById(2L);
            EntityTransaction tx = manager.getTransaction();
            tx.begin();
            manager.persist(new TypeRdv("Consultation", professionnel, 15));
            manager.persist(new TypeRdv("Expertise", professionnel, 30));
            tx.commit();
        }
    }

    public void listTypeRdvTest() {
        ProfessionnelDao professionnelDao = new ProfessionnelDao();

        Professionnel professionnel = professionnelDao.searchProfessionnelById(2L);
        List<TypeRdv> resultList = listTypeRdvsParProf(professionnel);
        System.out.println("\nNombre de type de rdv pour " + professionnel.getNom() + " " + professionnel.getPrenom() + ": " + resultList.size());
        for (TypeRdv next : resultList) {
            System.out.println("Type de rdv suivant : " + next);
        }
        System.out.println();
    }

    public void listTypeRdvs() {
        List<TypeRdv> resultList = manager.createQuery("Select a From TypeRdv a", TypeRdv.class).getResultList();
        System.out.println("\nNombre de TypeRdvs :" + resultList.size());
        for (TypeRdv next : resultList) {
            System.out.println("TypeRdv suivant : " + next);
        }
        System.out.println();
    }

    public TypeRdv searchTypeRdvById(Long id){
        return (TypeRdv) manager.createNamedQuery("typeRdvParId").setParameter("id", id).getSingleResult();

    }

    public List<TypeRdv> listTypeRdvsParProf(Professionnel prof) {
        return manager.createNamedQuery("tousLesTypeRdvParProf").setParameter("prof", prof).getResultList();
    }

    public void addTypeRdv (TypeRdv typeRdv){
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.persist(typeRdv);
        tx.commit();
    }

    public void deleteTypeRdvById (Long id){
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.remove(searchTypeRdvById(id));
        tx.commit();
    }

    public Integer minDureeTypeRdvByProf(Professionnel prof){
        return (Integer) manager.createNamedQuery("minDureeTypeRdvByProf").setParameter("prof", prof).getSingleResult();
    }

}
