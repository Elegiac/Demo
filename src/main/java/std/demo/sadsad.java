package std.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class sadsad {

	public static void main(String[] args) throws IOException {
		
		
//		//判断当前系统是否支持Java AWT Desktop扩展
//        if(java.awt.Desktop.isDesktopSupported()){
//            try{
//                //创建一个URI实例,注意不是URL
//                java.net.URI uri=java.net.URI.create("http://www.baidu.com/s?wd=呵呵");
//                //获取当前系统桌面扩展
//                java.awt.Desktop dp=java.awt.Desktop.getDesktop();
//                //判断系统桌面是否支持要执行的功能
//                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
//                    //获取系统默认浏览器打开链接
//                    dp.browse(uri);
//                }
//            }catch(java.lang.NullPointerException e){
//                //此为uri为空时抛出异常
//            }catch(java.io.IOException e){
//                //此为无法获取系统默认浏览器
//            }
//        }
		
		
		
//		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.baidu.com/s?wd=关键字");
//		
//		Runtime.getRuntime().addShutdownHook(new Thread(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				super.run();
//			}
//		});
//		Process p= Runtime.getRuntime().exec("shutdown -a");
//		InputStream in=p.getInputStream();
//		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(in,"gbk"));
//		String tempString = null;
//		// 按行读取号码文件
//		while ((tempString = reader.readLine()) != null) {
//			System.out.println(tempString);
//		}
//
//		reader.close();
		
		
		
		
		Process p= Runtime.getRuntime().exec("cmd /c type C:\\Users\\sinoadmin\\Desktop\\新建文本文档.txt");
		InputStream in=p.getInputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,"gbk"));
		String tempString = null;
		// 按行读取号码文件
		while ((tempString = reader.readLine()) != null) {
			System.out.println(tempString);
		}

		reader.close();
	}

}
