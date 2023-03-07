package org.spiderdesign.selenium.listener;

import org.spiderdesign.context.SpiderContext;
import org.spiderdesign.listener.SpiderListener;
import org.spiderdesign.selenium.utils.SeleniumResponseHolder;
import org.springframework.stereotype.Component;

@Component
public class SeleniumListener implements SpiderListener{

	@Override
	public void beforeStart(SpiderContext context) {

	}

	@Override
	public void afterEnd(SpiderContext context) {
		SeleniumResponseHolder.clear(context);
	}

}
