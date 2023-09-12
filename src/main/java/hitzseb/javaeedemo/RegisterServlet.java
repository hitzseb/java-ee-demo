package hitzseb.javaeedemo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;

    public RegisterServlet() {
        super();
        userDAO = new UserDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if (!password1.equals(password2)) {
		    request.setAttribute("passwordMismatch", true);
		    request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		
		try {
			userDAO.insertUser(username, PasswordHasher.hashPassword(password1));
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
