package hitzseb.javaeedemo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;

	public LoginServlet() {
		super();
		this.userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			password = PasswordHasher.hashPassword(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		User user = userDAO.getUserByUsername(username);
		if (password.equals(user.getPassword())) {
			HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}

}
