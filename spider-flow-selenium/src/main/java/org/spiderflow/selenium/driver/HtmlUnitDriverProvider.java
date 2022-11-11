package org.spiderflow.selenium.driver;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.spiderflow.model.SpiderNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HtmlUnitDriverProvider implements DriverProvider {
    @Override
    public String support() {
        return "htmlunit";
    }

    @Override
    public WebDriver getWebDriver(SpiderNode node, String proxyStr) {
        DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
        capabilities.setJavascriptEnabled(!"1".equals(node.getStringJsonValue(JAVASCRIPT_DISABLED)));
        if (StringUtils.isNotBlank(proxyStr)) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyStr);
            capabilities.setCapability(CapabilityType.PROXY, proxy);
        }
        return new HtmlUnitDriver(capabilities);
    }
}
