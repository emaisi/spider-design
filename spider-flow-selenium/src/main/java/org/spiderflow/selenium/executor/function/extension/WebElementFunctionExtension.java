package org.spiderflow.selenium.executor.function.extension;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.spiderflow.annotation.Comment;
import org.spiderflow.annotation.Example;
import org.spiderflow.executor.FunctionExtension;
import org.springframework.stereotype.Component;

@Component
public class WebElementFunctionExtension implements FunctionExtension {

    @Override
    public Class<?> support() {
        return WebElement.class;
    }

    @Comment("获取节点html内容")
    @Example("${webElementVar.sendKeys('Hello SpiderFlow')}")
    public static WebElement sendKeys(WebElement element, String keys) {
        element.sendKeys(keys);
        return element;
    }

    @Comment("获取节点html内容")
    @Example("${webElementVar.html()}")
    public static String html(WebElement element) {
        return element.getAttribute("innerHTML");
    }

    @Comment("获取节点html内容")
    @Example("${webElementVar.text()}")
    public static String text(WebElement element) {
        return element.getText();
    }

    @Comment("获取节点属性")
    @Example("${webElementVar.attr('href')}")
    public static String attr(WebElement element,String attributeName) {
        return element.getAttribute(attributeName);
    }

    @Comment("截图")
    @Example("${webElementVar.screenshot()}")
    public static byte[] screenshot(WebElement element) {
        return element.getScreenshotAs(OutputType.BYTES);
    }

}
