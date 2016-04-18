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

        System.out.println("====�յ���Ϣ��");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		out = response.getWriter();
		String userName = request.getParameter("name");
		String password = request.getParameter("password");
		String sql = "select username,password from accounts where username= ? and password= ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		// ׼�����û���������д�����ݿ�
		try {
			conn = GetDatabaseConnection.getConnection();
			/*ʹ��PreparedStatement�����SQLע������⡣ʹ��PreparedStatementʵ���û������������֤���ܡ�
			preparedStatement��Statement�����࣬��ʾԤ�����SQL���Ķ���
			��ʹ��PreparedStatement����ִ��SQL����ʱ��
			������ݿ����ͽ���������������������������е�Ԥ����SQL��������ظ�ʹ�á�
			*/
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, password);
			rs = ps.executeQuery();

			System.out.println("ִ��"+sql);
			if(rs.next()){
                System.out.println("��¼�ɹ���");
                out.print("��¼�ɹ�");
            }else{
                System.out.println("��¼ʧ�ܣ�");
                out.print("��¼ʧ��");
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
