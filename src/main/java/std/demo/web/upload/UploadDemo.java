package std.demo.web.upload;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class UploadDemo {
	@RequestMapping("toUpload")
	public String toUpload() {
		return "upload";

	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public boolean upload(// @RequestParam("file") MultipartFile uploadFile,
			HttpServletRequest request) throws IllegalStateException, IOException {

		// DiskFileItemFactory factory = new DiskFileItemFactory();
		// // 设置内存阀值，超过后写入临时文件
		// // factory.setSizeThreshold(10240000*5);
		// // 设置临时文件存储位置
		// // factory.setRepository(new
		// File(request.getRealPath("/upload/temp")));
		// ServletFileUpload upload = new ServletFileUpload(factory);
		//
		// // 设置单个文件的最大上传size
		// // upload.setFileSizeMax(10240000*5);
		// // 设置整个request的最大size
		// // upload.setSizeMax(10240000*5);
		// // 注册监听类
		// upload.setProgressListener(new UploadListener());
		//
		// try {
		// List<FileItem> items = upload.parseRequest(request);
		// // 处理文件上传
		// for (FileItem item : items) {
		// System.out.println(item.getName());
		// }
		//
		// System.out.println("upload completed");
		//
		// } catch (FileUploadException e) {
		// e.printStackTrace();
		// // uploadExceptionHandle(request,"上传文件时发生错误:"+e.getMessage());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// // uploadExceptionHandle(request,"保存上传文件时发生错误:"+e.getMessage());
		// }

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		System.out.println(files);

		// uploadFile.transferTo(new
		// File("C:\\Users\\sinoadmin\\Desktop\\upload\\" +
		// uploadFile.getOriginalFilename()));
		return true;
	}

	@RequestMapping(value = "progress", method = RequestMethod.POST)
	@ResponseBody
	public Progress initCreateInfo(HttpServletRequest request) {
		Progress status = (Progress) request.getSession().getAttribute("status");
		return status;
	}
}
