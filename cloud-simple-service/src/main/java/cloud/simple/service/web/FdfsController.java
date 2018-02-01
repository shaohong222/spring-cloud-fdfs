package cloud.simple.service.web;

import cloud.simple.service.util.FastDFSClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class FdfsController {

	@Autowired
	private FastDFSClientWrapper dfsClient;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(MultipartFile jarFile) throws Exception {
		String imgUrl = dfsClient.uploadFile(jarFile);
		return imgUrl;

	}
}
