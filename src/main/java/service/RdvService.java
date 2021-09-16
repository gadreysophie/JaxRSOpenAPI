package service;

import dao.RdvDao;
import dao.TypeRdvDao;
import domain.Professionnel;
import domain.Rdv;
import domain.TypeRdv;

import java.sql.Time;
import java.util.*;

public class RdvService {

    static RdvDao rdvDao = new RdvDao();

    /**
     * Retourne la liste des disponibilités d'un professionnel
     * en fonction de ses créneaux disponibles enregistrés
     * @param prof Professionnel
     * @param date Date
     * @param typeRdv Type de Rdv
     * @return la liste des créneaux disponibles par professionnel pour une date et par type de Rdv
     */
    public static HashMap<Integer, List<Time>> listCreneauxDispo(Professionnel prof, Date date, TypeRdv typeRdv){
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // Listes des temps libres dispos initialisées à vide
        List<Time> tabDebutCreneauxDispoMatin = new ArrayList<>();
        List<Time> tabFinCreneauxDispoMatin = new ArrayList<>();
        List<Time> tabDebutCreneauxDispoAprem = new ArrayList<>();
        List<Time> tabFinCreneauxDispoAprem = new ArrayList<>();
        List<Time> tabDebutCreneauxDispo = new ArrayList<>();
        List<Time> tabFinCreneauxDispo = new ArrayList<>();

        String [] joursPresenceTab = prof.getJoursDePresence().split("(?!^)");
        if(joursPresenceTab[c.get(Calendar.DAY_OF_WEEK)-1].equals("1")){
            List<Rdv> resultCreneauxRes = rdvDao.rdvsParProfessionnelEtDate(prof, date);
            Integer dureeTypeRdv = typeRdv.getDuree();
            TypeRdvDao typeRdvDao = new TypeRdvDao();
            Integer minDuree = typeRdvDao.minDureeTypeRdvByProf(prof);

            // Generate liste de creneaux dispo
            tabDebutCreneauxDispoMatin.add(prof.getHeureDebut());
            tabFinCreneauxDispoMatin.add(prof.getHeureDebutPause());
            tabDebutCreneauxDispoAprem.add(prof.getHeureFinPause());
            tabFinCreneauxDispoAprem.add(prof.getHeureFin());

            for (Rdv next : resultCreneauxRes) {

                Calendar calendar1 = GregorianCalendar.getInstance();
                calendar1.setTime(next.getDateDebut());
                Time debutRdv = Time.valueOf(calendar1.get(Calendar.HOUR_OF_DAY)+":"+calendar1.get(Calendar.MINUTE)+":00");

                Calendar calendar2 = GregorianCalendar.getInstance();
                calendar2.setTime(next.getDateFin());
                Time finRdv = Time.valueOf(calendar2.get(Calendar.HOUR_OF_DAY)+":"+calendar2.get(Calendar.MINUTE)+":00");

//                System.out.println("\nRDV suivant : " + next);
//                System.out.println("Début rdv : "+debutRdv);
//                System.out.println("Fin rdv : "+finRdv);

                if(finRdv.before(prof.getHeureFinPause()) || finRdv.equals(prof.getHeureFinPause())) {
                    constructTabOfTempsLibre(debutRdv, finRdv, tabDebutCreneauxDispoMatin, tabFinCreneauxDispoMatin);
//                    System.out.println(tabDebutCreneauxDispoMatin);
//                    System.out.println(tabFinCreneauxDispoMatin);
                }else{
                    constructTabOfTempsLibre(debutRdv, finRdv, tabDebutCreneauxDispoAprem, tabFinCreneauxDispoAprem);
//                    System.out.println(tabDebutCreneauxDispoAprem);
//                    System.out.println(tabFinCreneauxDispoAprem);
                }
            }
            constructTabOfCreneaux(tabDebutCreneauxDispoMatin, tabFinCreneauxDispoMatin, dureeTypeRdv, minDuree, tabDebutCreneauxDispo, tabFinCreneauxDispo);
            constructTabOfCreneaux(tabDebutCreneauxDispoAprem, tabFinCreneauxDispoAprem, dureeTypeRdv, minDuree, tabDebutCreneauxDispo, tabFinCreneauxDispo);
        }

//        System.out.println("\nTemps libre : ");
//        System.out.println(tabDebutCreneauxDispoMatin);
//        System.out.println(tabFinCreneauxDispoMatin);
//        System.out.println(tabDebutCreneauxDispoAprem);
//        System.out.println(tabFinCreneauxDispoAprem);

//        System.out.println("\nCréneaux disponibles : ");
//        System.out.println(tabDebutCreneauxDispo);
//        System.out.println(tabFinCreneauxDispo);

        HashMap<Integer, List<Time>> creneauxDispo = new HashMap<>();
        for (int i = 0; i < tabDebutCreneauxDispo.size(); i++) {
            List<Time> listTime = new ArrayList<>();
            listTime.add(tabDebutCreneauxDispo.get(i));
            listTime.add(tabFinCreneauxDispo.get(i));
            creneauxDispo.put(i,listTime);
        }
        return creneauxDispo;

    }

    /**
     * Construit le tableau des disponibilités du professionnel
     * @param debutRdv heure de début du rdv
     * @param finRdv heure de fin du rdv
     * @param tabDebutCreneau tableau des heures de début des créneaux
     * @param tabFinCreneau tableau des heures de fin des créneaux
     */
    private static void constructTabOfTempsLibre(Time debutRdv, Time finRdv, List<Time> tabDebutCreneau, List<Time> tabFinCreneau){
        Time time1;
        Time time2;
        Time tmp;
        for (int i = 0; i < tabDebutCreneau.size(); i++) {
            time1 = tabDebutCreneau.get(i);
            time2 = tabFinCreneau.get(i);
//            System.out.println("Début dispo : " + time1);
//            System.out.println("Fin dispo : " + time2);

            if (debutRdv.equals(time1)) {
//                System.out.println("    Remplacer : " + time1 + " par "+finRdv);
                tabDebutCreneau.set(tabDebutCreneau.indexOf(time1), finRdv);
//                System.out.println("\n  Test : ");
//                System.out.println(tabDebutCreneau);
//                System.out.println(tabFinCreneau);
                if (tabFinCreneau.contains(finRdv)) {
                    tabDebutCreneau.remove(finRdv);
                    tabFinCreneau.remove(finRdv);
                }
                break;
            } else {
                if (debutRdv.before(time2)) {
                    tmp = time2;
                    tabFinCreneau.set(i, debutRdv);
                    if (!finRdv.equals(tmp)) {
                        tabFinCreneau.add(tabFinCreneau.size(), tmp);
                        Collections.sort(tabFinCreneau);
                        tabDebutCreneau.add(tabDebutCreneau.size(), finRdv);
                        Collections.sort(tabDebutCreneau);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Construit le tableau des disponibilités d'un professionnel en fonction de la duree d'un rdv
     * @param tabDebutTempsLibre tableau de l'heure de début de la disponibilité
     * @param tabFinTempsLibre tableau de l'heure de fin de la disponibilité
     * @param dureeRdv durée du rdv
     * @param minduree minimum de la duree des rdv d'un professionnel
     * @param tabDebutCreneau tableau des heures de début des créneaux
     * @param tabFinCreneau tableau des heures de fin des créneaux
     */
    private static void constructTabOfCreneaux(List<Time> tabDebutTempsLibre, List<Time> tabFinTempsLibre, Integer dureeRdv, Integer minduree, List<Time> tabDebutCreneau, List<Time> tabFinCreneau){
        for (int i = 0; i < tabDebutTempsLibre.size(); i++) {
            Time heureDebutTempsLibre = tabDebutTempsLibre.get(i);
            Time heureFinTempsLibre = tabFinTempsLibre.get(i);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tabDebutTempsLibre.get(i));
            calendar.add(Calendar.MINUTE, dureeRdv);
            Time heureEstimeeFinTempsLibre = Time.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":00");

            while(heureEstimeeFinTempsLibre.before(heureFinTempsLibre) || heureEstimeeFinTempsLibre.equals(heureFinTempsLibre)){
                tabDebutCreneau.add(heureDebutTempsLibre);
                tabFinCreneau.add(heureEstimeeFinTempsLibre);

                calendar.setTime(heureDebutTempsLibre);
                calendar.add(Calendar.MINUTE, minduree);
                heureDebutTempsLibre = Time.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":00");

                calendar.add(Calendar.MINUTE, dureeRdv);
                heureEstimeeFinTempsLibre = Time.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":00");
            }
        }
    }


}
