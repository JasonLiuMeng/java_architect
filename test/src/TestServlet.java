import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//System.out.println("·ÃÎÊ·þÎñÆ÷...");
			byte[] data = new byte[1024*1024];
	}

	
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
	}
}
