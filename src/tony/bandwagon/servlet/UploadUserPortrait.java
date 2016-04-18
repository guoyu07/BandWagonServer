package tony.bandwagon.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * Servlet implementation class UploadUserPortrait
 */
@WebServlet("/uploadportrait.do")
public class UploadUserPortrait extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final boolean ISREMOTE = false;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadUserPortrait() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String strUserPortraitName = null;
		
		System.out.println("收到客户端上传图片请求");
		doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		//判断奔驰请求是否是一个有上传文件的复杂性的请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		File temp;

		System.out.println("isMultipart= " +isMultipart);
		
		if(isMultipart){
			
			//因为不知道上传的文件有多大，这里没有将上传的文件直接给对象，而是在磁盘上建立一个缓冲区
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置文件的大小
			factory.setSizeThreshold(1024*1024*2);;
			//设置磁盘的位置.File类为文件或路径名，这里表示路径(目录)
			if (ISREMOTE) {
				
					temp = new File("/home/wwwroot/temp");
					System.out.println(temp.getName());
			}
			else{
					temp = new File("c:\\temp");
					System.out.println(temp.getName());
			}
			if(!temp.exists()){
				temp.mkdir();
				System.out.println("====temp.mkdir=");

			}
			ServletFileUpload upload = null;
			factory.setRepository(temp);
			
			upload= new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			//设置上传文件的最大值
			upload.setFileSizeMax(1024*1024*5);
			//设置整个上传数据的最大值
			upload.setSizeMax(1024*1024*6);
			
			try {
				out = response.getWriter();
				System.out.println("往下开始获取输入流");
				//因为list中装载了简单的字符串以及双层的文件。需要用list来封装。
				System.out.println(request.toString());
				List<FileItem> items = upload.parseRequest(request);
				//解析list中的items，若是简单数据存入数据库，若是大文件存入磁盘
				if(items != null  ){
					System.out.println("items不为空");
					for(FileItem item :items){
						if(item.isFormField()){
							System.out.println(item.getFieldName());
							System.out.println(item.getSize());
							System.out.println(item.getString());
							//获取用户名，保存下来，将得到的头像文件重命名
							strUserPortraitName = item.getString();
							
						}else{
							System.out.println(item.getName());
							try {
								if (ISREMOTE) {
									item.write(new File("/home/wwwroot/"+ item.getName()));
								}else{
									
									item.write(new File("D:\\workspace\\portrait\\"+ strUserPortraitName+".jpg"));
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					}
						
				}
				
				out.print("Upload Success");
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				out.print("Upload Failed");
			}
			
			

		}
	
	}
}
