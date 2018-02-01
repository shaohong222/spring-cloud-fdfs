package cloud.simple.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import cloud.simple.model.User;
//import cloud.simple.service.UserServiceProvider.FeignUserService;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
	 @Autowired	 
	 RestTemplate restTemplate;
	
//	 @Autowired
//	 FeignUserService feignUserService;
	 
	 final String SERVICE_NAME="cloud-simple-service";
	 
	 @HystrixCommand(fallbackMethod = "fallbackSearchAll")
	 public List<User> readUserInfo() {
	        return restTemplate.getForObject("http://"+SERVICE_NAME+"/user", List.class);
		 //return feignUserService.readUserInfo();
	 }	 
	 private List<User> fallbackSearchAll() {
		 System.out.println("HystrixCommand fallbackMethod handle!");
		 List<User> ls = new ArrayList<User>();
		 User user = new User();
		 user.setUsername("TestHystrixCommand");
		 ls.add(user);
		 return ls;
	 }

	public String uploadFile(MultipartFile file) throws IOException {
		final String filename=file.getOriginalFilename();
		ByteArrayResource contentsAsResource = new ByteArrayResource(file.getBytes()){
			@Override
			public String getFilename(){
				return filename;
			}
		};
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("jarFile", contentsAsResource);

		return restTemplate.postForObject("http://"+SERVICE_NAME+"/upload", param,String.class);
		//return feignUserService.readUserInfo();
	}
}
