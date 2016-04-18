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
		
		System.out.println("�յ��ͻ����ϴ�ͼƬ����");
		doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		//�жϱ��������Ƿ���һ�����ϴ��ļ��ĸ����Ե�����
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		File temp;

		System.out.println("isMultipart= " +isMultipart);
		
		if(isMultipart){
			
			//��Ϊ��֪���ϴ����ļ��ж������û�н��ϴ����ļ�ֱ�Ӹ����󣬶����ڴ����Ͻ���һ��������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//�����ļ��Ĵ�С
			factory.setSizeThreshold(1024*1024*2);;
			//���ô��̵�λ��.File��Ϊ�ļ���·�����������ʾ·��(Ŀ¼)
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
			//�����ϴ��ļ������ֵ
			upload.setFileSizeMax(1024*1024*5);
			//���������ϴ����ݵ����ֵ
			upload.setSizeMax(1024*1024*6);
			
			try {
				out = response.getWriter();
				System.out.println("���¿�ʼ��ȡ������");
				//��Ϊlist��װ���˼򵥵��ַ����Լ�˫����ļ�����Ҫ��list����װ��
				System.out.println(request.toString());
				List<FileItem> items = upload.parseRequest(request);
				//����list�е�items�����Ǽ����ݴ������ݿ⣬���Ǵ��ļ��������
				if(items != null  ){
					System.out.println("items��Ϊ��");
					for(FileItem item :items){
						if(item.isFormField()){
							System.out.println(item.getFieldName());
							System.out.println(item.getSize());
							System.out.println(item.getString());
							//��ȡ�û������������������õ���ͷ���ļ�������
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
