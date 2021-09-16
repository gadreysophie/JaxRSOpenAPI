package jpa;

import dao.*;

import java.text.ParseException;

public class JpaTest {

	public static void main(String[] args) throws ParseException {

		ProfessionnelDao professionnelDao = new ProfessionnelDao();
		DepartementDao departementDao = new DepartementDao();
		UtilisateurDao utilisateurDao = new UtilisateurDao();
		RdvDao rdvDao = new RdvDao();
		PersonneDao personneDao = new PersonneDao();
		TypeRdvDao typeRdvDao = new TypeRdvDao();

		try {

			departementDao.createDepartements();

			professionnelDao.createProfessionnels();

			utilisateurDao.createUtilisateurs();

			typeRdvDao.createTypeRdvs();

			rdvDao.createRdvs();

		} catch (Exception e) {
			e.printStackTrace();
		}


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
	}

}
