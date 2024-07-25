
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Details")
public class Details extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String pickup = request.getParameter("pickup");
        String dropoff = request.getParameter("dropoff");

        
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vsb", "root", "1234");

            String sql = "INSERT INTO bookings (name, pickup, dropoff) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, pickup);
            statement.setString(3, dropoff);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
            	RequestDispatcher rd1 = request.getRequestDispatcher("Selection.html");
                rd1.forward(request, response);
            } else {
                response.getWriter().println("Failed to book taxi.");
            }

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}