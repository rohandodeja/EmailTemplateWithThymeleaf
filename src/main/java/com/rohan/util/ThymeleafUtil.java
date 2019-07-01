package com.rohan.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ThymeleafUtil {

	@Autowired
	private TemplateEngine tempTemplateEngine;

	/**
	 * This method is use get Processed Html
	 * 
	 * @param templateName
	 * @param dynamicValueMap
	 * @return
	 */
	public String getProcessedHtml(Map<String, Object> model, String templateName) {

		Context context = new Context();

		if (model != null) {
			model.forEach((key, value) -> context.setVariable(key, value));
			return tempTemplateEngine.process(templateName, context);
		}
		return "";

	}
}
