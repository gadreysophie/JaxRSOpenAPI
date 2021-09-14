package dao;

import domain.*;

import javax.persistence.EntityManager;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TypeRdvDao {

    private final EntityManager manager;

    public TypeRdvDao (javax.persistence.EntityManager manager) {
        this.manager = manager;
    }

    public void createTypeRdvs() {
        int numOfTypeRdvs = manager.createQuery("Select a From TypeRdv a", TypeRdv.class).getResultList().size();
        if (numOfTypeRdvs == 0) {
            ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);

            Professionnel professionnel = professionnelDao.professionnelsParId(4L);
            manager.persist(new TypeRdv("Rdv 1", professionnel, 15));
            manager.persist(new TypeRdv("Rdv 2", professionnel, 30));
        }
    }

    public void listTypeRdvTest() throws ParseException {
        ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);

        Professionnel professionnel = professionnelDao.professionnelsParId(4L);
        List<TypeRdv> resultList = listTypeRdvsParProf(professionnel);
        System.out.println("Nombre de type de rdv pour " + professionnel.getNom() + " " + professionnel.getPrenom() + ": " + resultList.size());
        for (TypeRdv next : resultList) {
            System.out.println("Type de rdv suivant : " + next);
        }
    }

    public void listTypeRdvs() {
        List<TypeRdv> resultList = manager.createQuery("Select a From TypeRdv a", TypeRdv.class).getResultList();
        System.out.println("Nombre de TypeRdvs :" + resultList.size());
        for (TypeRdv next : resultList) {
            System.out.println("TypeRdv suivant : " + next);
        }
    }

    public TypeRdv typeRdvsParId(Long id){
        return (TypeRdv) manager.createNamedQuery("tousLesTypeRdvParId").setParameter("id", id).getSingleResult();

    }

    public List<TypeRdv> listTypeRdvsParProf(Professionnel prof) {
        return manager.createNamedQuery("tousLesTypeRdvParProf").setParameter("prof", prof).getResultList();
    }

    public void addTypeRdv (TypeRdv typeRdv){
        manager.persist(typeRdv);
    }


}
