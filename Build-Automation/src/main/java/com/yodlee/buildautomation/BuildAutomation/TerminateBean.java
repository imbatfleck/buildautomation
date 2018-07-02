package com.yodlee.buildautomation.BuildAutomation;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminateBean {
	@PreDestroy
    public void onDestroy() throws Exception {
        BuildAutomationApplication.logger.info("Spring container is destroyed");
    }
}
