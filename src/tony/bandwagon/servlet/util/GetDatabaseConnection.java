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
		// 通过加载conn.ini文件来获取数据库连接的详细信息
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
		// 加载数据库驱动
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(url, username, password);
	}
}
