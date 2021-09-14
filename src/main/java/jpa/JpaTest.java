package jpa;

import dao.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.ParseException;

public class JpaTest {

	public static void main(String[] args) throws ParseException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();

		ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);
		DepartementDao departementDao = new DepartementDao(manager);
		UtilisateurDao utilisateurDao = new UtilisateurDao(manager);
		RdvDao rdvDao = new RdvDao(manager);
		PersonneDao personneDao = new PersonneDao(manager);
		TypeRdvDao typeRdvDao = new TypeRdvDao(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {

			departementDao.createDepartements();

			professionnelDao.createProfessionnels();

			utilisateurDao.createUtilisateurs();

			typeRdvDao.createTypeRdvs();

			rdvDao.createRdvs();

		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();


		professionnelDao.listProfessionnelsParNom("Prof");
		professionnelDao.listProfessionnels();
		departementDao.listDepartements();
		utilisateurDao.printListUtilisateurs();
		rdvDao.listRdvs();
		personneDao.listPersonnes();
		typeRdvDao.listTypeRdvs();

		rdvDao.listRdvTest();
		typeRdvDao.listTypeRdvTest();
		rdvDao.testListCreneauxDispo();

		manager.close();
		factory.close();
	}

}
