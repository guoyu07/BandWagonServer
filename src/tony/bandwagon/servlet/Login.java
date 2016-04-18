package tony.bandwagon.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tony.bandwagon.servlet.util.GetDatabaseConnection;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private static Statement stmt;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		PrintWriter out = null;

        System.out.println("====收到消息！");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		out = response.getWriter();
		String userName = request.getParameter("name");
		String password = request.getParameter("password");
		String sql = "select username,password from accounts where username= ? and password= ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		// 准备将用户名和密码写入数据库
		try {
			conn = GetDatabaseConnection.getConnection();
			/*使用PreparedStatement来解决SQL注入的问题。使用PreparedStatement实现用户名和密码的验证功能。
			preparedStatement是Statement的子类，表示预编译的SQL语句的对象。
			在使用PreparedStatement对象执行SQL命令时，
			命令被数据库编译和解析，并放入命令缓冲区。缓冲区中的预编译SQL命令可以重复使用。
			*/
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, password);
			rs = ps.executeQuery();

			System.out.println("执行"+sql);
			if(rs.next()){
                System.out.println("登录成功！");
                out.print("登录成功");
            }else{
                System.out.println("登录失败！");
                out.print("登录失败");
            }


			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ClassNotFoundException | SQLException e");
			e.printStackTrace();
			
		}
		finally
		{
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(ps!= null)
				try {
					ps.close();
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

		System.out.println("====" + userName + "=========" + password);
		// doGet(request, response);		
		
		
	}

}
