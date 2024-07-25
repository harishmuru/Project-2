

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Actaxi
 */
@WebServlet("/Actaxi")
public class Actaxi extends HttpServlet {
	   private static final long serialVersionUID = 1L;

	    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/vsb";
	    private static final String JDBC_USER = "root";
	    private static final String JDBC_PASSWORD = "1234";

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        response.setContentType("text/html;charset=UTF-8");

	        // Retrieve form parameters
	        String phoneNo = request.getParameter("phoneno");
	        String amount = request.getParameter("amount");
	        String time = request.getParameter("time");

	        // Database connection and SQL query
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "INSERT INTO acbooking (phoneno, amount, timing) VALUES (?, ?, ?)";

	        try {     
	            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

	         
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, phoneNo);
	            stmt.setString(2, amount);
	            stmt.setString(3, time);

	            
	            int rowsAffected = stmt.executeUpdate();

	            
	            PrintWriter out = response.getWriter();
	            out.println("<!DOCTYPE html>");
	            out.println("<html>");
	            out.println("<head>");
	            out.println("<meta charset=\"UTF-8\">");
	            out.println("<title>Booking Confirmation</title>");
	            out.println("<style>");
	            out.println("body { font-family: Arial, sans-serif; text-align: center; }");
	            out.println("h1 { margin-top: 50px; }");
	            out.println("</style>");
	            out.println("</head>");
	            out.println("<body>");
	            out.println("<h1>AC Taxi Booking Confirmation</h1>");
	            if (rowsAffected > 0) {
	                out.println("<p>Booking saved successfully!</p>");
	            } else {
	                out.println("<p>Error: Unable to save booking.</p>");
	            }
	            out.println("</body>");
	            out.println("</html>");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            
	            try {
	                if (stmt != null) {
	                    stmt.close();
	                }
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
