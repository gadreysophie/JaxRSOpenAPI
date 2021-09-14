package dao;

import domain.*;

import javax.persistence.EntityManager;
import java.util.List;

public class TypeRdvDao {

    private EntityManager manager;

    public TypeRdvDao (javax.persistence.EntityManager manager) {
        this.manager = manager;
    }

    public void createTypeRdvs() {
        int numOfTypeRdvs = manager.createQuery("Select a From TypeRdv a", TypeRdv.class).getResultList().size();
        if (numOfTypeRdvs == 0) {
            Departement departement = new Departement("Java2");
            Professionnel professionnel = new Professionnel("Prof 3", departement);
            manager.persist(departement);
            manager.persist(professionnel);

            manager.persist(new TypeRdv("Rdv 1", professionnel, 15));
            manager.persist(new TypeRdv("Rdv 2", professionnel, 30));
        }
    }

    public void listRdvs() {
        List<TypeRdv> resultList = manager.createQuery("Select a From TypeRdv a", TypeRdv.class).getResultList();
        System.out.println("Nombre de TypeRdvs :" + resultList.size());
        for (TypeRdv next : resultList) {
            System.out.println("TypeRdv suivant : " + next);
        }
    }

    public TypeRdv typeRdvsParId(Long id){
        return (TypeRdv) manager.createNamedQuery("tousLesTypeRdvParId").setParameter("id", id).getSingleResult();

    }

    public void addTypeRdv (TypeRdv typeRdv){
        manager.persist(typeRdv);
    }


}
