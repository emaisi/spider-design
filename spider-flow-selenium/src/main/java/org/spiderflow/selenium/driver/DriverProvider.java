package org.spiderflow.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.spiderflow.model.SpiderNode;

import java.util.List;

public interface DriverProvider {

    public String JAVASCRIPT_DISABLED = "javascript-disabled";

    public String USER_AGENT = "user-agent";

    public String HEADLESS = "headless";

    public String IMAGE_DISABLED = "image-disabled";

    public String HIDE_SCROLLBAR = "hide-scrollbar";

    public String PLUGIN_DISABLE = "plugin-disable";

    public String JAVA_DISABLE = "java-disable";

    public String INCOGNITO = "incognito";

    public String SANDBOX = "sandbox";

    public String WINDOW_SIZE = "window-size";

    public String MAXIMIZED = "maximized";

    public String ARGUMENTS = "arguments";

    public String support();

    public WebDriver getWebDriver(SpiderNode node, String proxyStr);
}
