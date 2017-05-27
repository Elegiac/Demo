package std.demo.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulTest {
	//http://www.ruanyifeng.com/blog/2014/05/restful_api.html
	//POST(增) DELETE(删) PUT(改) GET(查)
	
	
	@RequestMapping(value="restful/demo",method = RequestMethod.DELETE)
	public void test1(HttpServletResponse response){
		System.out.println("delete method has been called");
	}
	
	@RequestMapping(value="restful/demo",method = RequestMethod.PUT)
	public void test2(){
		System.out.println("put method has been called");
	}
}
