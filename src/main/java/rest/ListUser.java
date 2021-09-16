package rest;

import dao.UtilisateurDao;
import domain.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="listuser",
        urlPatterns={"/ListUser"})
public class ListUser extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UtilisateurDao utilisateurDao = new UtilisateurDao();

        response.setContentType("text/html");

        StringBuilder listUsers = new StringBuilder();
        for (Utilisateur next : utilisateurDao.listUtilisateurs()) {
            listUsers.append(" <LI>Nom : ").append(next.getNom()).append(", Prénom : ").append(next.getPrenom());
        }

        PrintWriter out = response.getWriter();
        out.println("<HTML>\n<BODY>\n" +
                "<H1>Liste des utilisateurs</H1>\n" +
                "<UL>\n" +
                listUsers
                +
                "</UL>\n"+
                "</BODY></HTML>");
    }

}
