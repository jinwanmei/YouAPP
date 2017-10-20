package j.jave.kernal.mail;

import javax.mail.Message.RecipientType;

public class JBccRecipient extends JRecipient {
	
	public JBccRecipient(String recipient) {
		super(recipient);
	}

	@Override
	public RecipientType getRecipientType() {
		return RecipientType.BCC;
	}
	
}