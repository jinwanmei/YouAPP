package test.j.jave.framework.commons.mail;

import j.jave.kernal.mail.JCcRecipient;
import j.jave.kernal.mail.JDefaultMailService;
import j.jave.kernal.mail.JMail;
import j.jave.kernal.mail.JMailMessage;
import j.jave.kernal.mail.JSMTPProtocol;
import j.jave.kernal.mail.JToRecipient;
import j.jave.kernal.mail.JUserNamePasswordAuthenticator;


public class A {

	
	public static void main(String[] args) throws Exception {
		
		JMail mail=new JMail();
		
		JUserNamePasswordAuthenticator userNamePasswordAuthenticator
			=new JUserNamePasswordAuthenticator("jzj871227@163.com", "***********");
		mail.setMailAuthenticator(userNamePasswordAuthenticator);
		JSMTPProtocol smtpProtocol=new JSMTPProtocol("smtp.163.com", true);
		mail.setMailProtocol(smtpProtocol);
		
		JMailMessage mailMessage=new JMailMessage();
		mailMessage.setSender("jzj871227@163.com");
		mailMessage.setContent("你好！ 你是？");
		mailMessage.setSubject("我们明天去上班");
		mailMessage.addRecipient(new JToRecipient("3cgg@163.com"));
		mailMessage.addRecipient(new JToRecipient("ibleach@vip.qq.com"));
		mailMessage.addRecipient(new JCcRecipient("908501590@qq.com"));
		mail.setMailMessage(mailMessage);
		
		new JDefaultMailService().send(mail);
		
//		new JDefaultMailService().send(mail);
		
		System.out.println("end");
		
	}
}
