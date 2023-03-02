package org.spiderflow;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@MapperScan("org.spiderflow.*.mapper")
public class SpiderApplication implements ServletContextInitializer{

	public static void main(String[] args) throws IOException {

		SpringApplication.run(SpiderApplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//设置文本缓存1M
		servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize", Integer.toString((1024 * 1024)));
	}



	/**
	 * 分页插件 3.5.X
	 * @author zhengkai.blog.csdn.net
	 */
	@Bean
	public PaginationInnerInterceptor paginationInnerInterceptor() {
		PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
//		paginationInterceptor.setMaxLimit(-1L);
		// 开启 count 的 join 优化,只针对部分 left join
		paginationInterceptor.setOptimizeJoin(false);
		return paginationInterceptor;
	}
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(){
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		mybatisPlusInterceptor.setInterceptors(Collections.singletonList(paginationInnerInterceptor()));
		return mybatisPlusInterceptor;
	}

}
