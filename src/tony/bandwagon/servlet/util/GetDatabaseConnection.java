package tony.bandwagon.servlet.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class GetDatabaseConnection {

	
	private static final boolean ISREMOTE = false;

	public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
		// ͨ������conn.ini�ļ�����ȡ���ݿ����ӵ���ϸ��Ϣ
		Properties props = new Properties();
		FileInputStream in;
		if (ISREMOTE) {
			in = new FileInputStream("/home/wwwroot/conn.ini");
		}else{
			in = new FileInputStream("D:\\conn.ini");
		}
		props.load(in);
		in.close();
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		// �������ݿ�����
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(url, username, password);
	}
}
