package com.rohan.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailController {
	
	@Autowired
	MailUtil mailUtil;
	
	
	@RequestMapping(value="/sendmail", method = RequestMethod.POST)
	public void sendMail(@RequestBody EmailDto emailDto) {
		mailUtil.sendMailViaGmail(emailDto.getToEmail(), emailDto.getSubject(), emailDto);
	}
}
