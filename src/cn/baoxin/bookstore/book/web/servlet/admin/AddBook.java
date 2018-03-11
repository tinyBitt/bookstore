package cn.baoxin.bookstore.book.web.servlet.admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.baoxin.bookstore.book.domain.Book;
import cn.baoxin.bookstore.book.service.BookService;
import cn.baoxin.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;

@WebServlet(urlPatterns="/AddBook")
public class AddBook extends HttpServlet {
	private BookService bookService = new BookService();
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			List<FileItem> fileItemList = sfu.parseRequest(req);
			
			Map<String, Object> map = new HashMap<String, Object>();
			for(FileItem f : fileItemList){
				if(!f.isFormField()){
					//处理上传文件名为绝对路径问题
					String filename = f.getName();
					int index = filename.lastIndexOf("\\");
					if(index != -1){
						filename = filename.substring(index + 1);
					}
					//存储图片
					String realPath = req.getServletContext().getRealPath("/book_img");
					f.write(new File(realPath, filename));
					map.put("image", "book_img"+"/"+filename);
				}else{
					map.put(f.getFieldName(), f.getString("utf-8"));
				}
			}
			Book book = CommonUtils.toBean(map, Book.class);
			//为book补齐bid和category
			book.setBid(CommonUtils.uuid());
			book.setCategory(new CategoryService().findByCid((String)map.get("cid")));
			
			bookService.add(book);
			req.getRequestDispatcher("/AdminBookServlet?method=findAll").forward(req, res);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
