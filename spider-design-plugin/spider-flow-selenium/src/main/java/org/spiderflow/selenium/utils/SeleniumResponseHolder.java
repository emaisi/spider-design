package org.spiderflow.selenium.utils;

import org.spiderflow.context.SpiderContext;
import org.spiderflow.selenium.io.SeleniumResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SeleniumResponseHolder {

    private static Map<String, List<SeleniumResponse>> driverMap = new ConcurrentHashMap<>();

    public static void clear(SpiderContext context) {
        List<SeleniumResponse> responses = driverMap.get(context.getId());
        if (responses != null) {
            for (SeleniumResponse response : responses) {
                response.quit();
            }
        }
        driverMap.remove(context.getId());
    }

    public synchronized static void add(SpiderContext context, SeleniumResponse response) {
		driverMap.computeIfAbsent(context.getId(), k -> new ArrayList<>()).add(response);
    }
}
