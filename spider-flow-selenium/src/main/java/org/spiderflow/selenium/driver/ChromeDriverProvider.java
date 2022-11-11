package org.spiderflow.selenium.driver;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.spiderflow.model.SpiderNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ChromeDriverProvider implements DriverProvider {

    @Value("${selenium.driver.chrome:null}")
    private String chromeDriverPath;

    @Override
    public String support() {
        return chromeDriverPath != null ? "chrome" : null;
    }

    @Override
    public WebDriver getWebDriver(SpiderNode node, String proxyStr) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        String userAgent = node.getStringJsonValue(USER_AGENT);
        //设置User-Agent
        if(StringUtils.isNotBlank(userAgent)){
            options.addArguments("user-agent=\"" + userAgent +"\"");
        }
        //禁用JS
        if("1".equals(node.getStringJsonValue(JAVASCRIPT_DISABLED))){
            options.addArguments("–-disable-javascript");
        }
        //禁用加载图片
        if("1".equals(node.getStringJsonValue(IMAGE_DISABLED))){
            options.addArguments("blink-settings=imagesEnabled=false");
        }
        //隐藏滚动条
        if("1".equals(node.getStringJsonValue(HIDE_SCROLLBAR))){
            options.addArguments("--hide-scrollbars");
        }
        //无头模式
        if("1".equals(node.getStringJsonValue(HEADLESS))){
            options.setHeadless(true);
        }
        //禁用沙盒模式
        if(!"1".equals(node.getStringJsonValue(SANDBOX))){
            options.addArguments("--no-sandbox");
        }
        //隐身模式
        if("1".equals(node.getStringJsonValue(INCOGNITO))){
            options.addArguments("--incognito");
        }
        //禁用插件
        if("1".equals(node.getStringJsonValue(PLUGIN_DISABLE))){
            options.addArguments("--disable-plugins");
        }
        //禁用Java
        if("1".equals(node.getStringJsonValue(JAVA_DISABLE))){
            options.addArguments("--disable-java");
        }
		//设置窗口大小
		String windowSize = node.getStringJsonValue(WINDOW_SIZE);
        if(StringUtils.isNotBlank(windowSize)){
            options.addArguments("--window-size=" + windowSize);
        }
        //最大化
        if("1".equals(node.getStringJsonValue(MAXIMIZED))){
            options.addArguments("--start-maximized");
        }
        //设置其他参数
		String arguments = node.getStringJsonValue(ARGUMENTS);
        if(StringUtils.isNotBlank(arguments)){
        	options.addArguments(Arrays.asList(arguments.split("\r\n")));
		}
		//设置代理
        if (StringUtils.isNotBlank(proxyStr)) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyStr);
            options.setProxy(proxy);
        }
        return new ChromeDriver(options);
    }
}
