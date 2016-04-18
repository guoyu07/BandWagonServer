package tony.bandwagon.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tony.bandwagon.servlet.util.GetDatabaseConnection;

/**
 * Servlet implementation class CreateNewUser
 */
public class CreateNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private static Statement stmt;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateNewUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * response.getWriter().append("Served at: "
		 * ).append(request.getContextPath());
		 * 
		 */
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		// 获取客户端传递的username和password
		System.out.println("接受到post=========");
		
		PrintWriter out = null;
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		out = response.getWriter();
		String userName = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		// 准备将用户名和密码写入数据库
		try {
			conn = GetDatabaseConnection.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			System.out.println("=======getconnection成功111==========");
			System.out.println("=======getconnection1==========");
			String query = "insert ignore into accounts (username,password,email) values('"
			//		String query = "insert ignore into accounts (username,password) values('"

					+ userName
					+ "','"
					+ password
					+ "','"
					+ email
					+ "')";
			System.out.println("=======getconnection成功1111111==========");

			// 查询用户选择的数据表
			System.out.println(query);
			System.out.println("===111= " + userName + " ==222== "+password+" ===333==" + email);
			stmt.executeUpdate(query);
			out.print("注册用户成功");
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ClassNotFoundException | SQLException e");
			e.printStackTrace();
		}
		finally
		{
			if(stmt!= null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 		
			if(conn!= null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		}
		
	}

}
