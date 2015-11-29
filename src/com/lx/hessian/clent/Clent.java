package com.lx.hessian.clent;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.lx.dubbo.demo.service.DemoService;
import com.lx.hessian.service.IHelloService;

public class Clent {
	
	/** 服务URL(默认): */
	private static String HELLO_SERVICE_URL = "http://localhost:8080/hessian_1_server/hello";
	
	/** dubbo 基于 hessian 协议远程接口调用的 URL */
	private static String DEMMO_SERVICE_URL = "http://localhost:8080/provider/dubbo/dubbo_hessian/demoService";
	
	/** 连接服务超时间(默认): */
	private static int TIME_OUT = 10000;
	
	/** 服务实例 */
	private IHelloService helloService;
	
	/**dubbo hessian 协议接口调用测试*/
	private DemoService demoService;
	
	/**
	 * 构造函数,初始化远程连接
	 * 
	 * @throws Exception
	 *             连接异常
	 */
	public Clent(){
		
		try {
			
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setReadTimeout(TIME_OUT);
			helloService = (IHelloService) factory.create(IHelloService.class, HELLO_SERVICE_URL);
			demoService = (DemoService) factory.create(DemoService.class, DEMMO_SERVICE_URL);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		Clent clent = new Clent();
		
		IHelloService service1 = clent.getHelloService();
		String result = service1.sayHello("lx");
		System.out.println(result);
		
		/**
		 * 基于标准Hessian协议接口的远程调用，使用标准Hessian接口来实现远程调用，
		 * 这时就不需要关心服务提供方的所使用的开发语言，因为最终是通过HTTP的方式来访问。我们需要下载Hessian对应语言的调用实现库，才能更方便地编程
		 */
		DemoService service2 = clent.getDemoService();
		String result2 = service2.sayHello("Hello world！Call dubbo hessian service success， This is a standard hessian client");
		System.out.println(result2);
	}
	
	public IHelloService getHelloService() {
		return helloService;
	}

	public DemoService getDemoService() {
		return demoService;
	}

}
