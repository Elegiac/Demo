package std.demo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulTest {
	// http://www.ruanyifeng.com/blog/2014/05/restful_api.html
	// POST(增) DELETE(删) PUT(改) GET(查)

	@RequestMapping(value = "restful/demo", method = RequestMethod.GET)
	public Map<String, String> test0(HttpServletResponse response) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("t1", "111");
		m.put("t2", "222");
		return m;
	}

	@RequestMapping(value = "restful/demo", method = RequestMethod.DELETE)
	public void test1(HttpServletResponse response) {
		System.out.println("delete method has been called");
	}

	@RequestMapping(value = "restful/demo", method = RequestMethod.PUT)
	public void test2() {
		System.out.println("put method has been called");
	}

	@RequestMapping(value = "restful/test", method = RequestMethod.POST)
	public void test3(@RequestParam(value = "ms[]") TestModel[] ms) {
		System.out.println(ms);
	}

	static class TestModel {
		private String n1;
		private String n2;

		public String getN1() {
			return n1;
		}

		public void setN1(String n1) {
			this.n1 = n1;
		}

		public String getN2() {
			return n2;
		}

		public void setN2(String n2) {
			this.n2 = n2;
		}

		@Override
		public String toString() {
			return "TestModel [n1=" + n1 + ", n2=" + n2 + "]";
		}

	}
}
