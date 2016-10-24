package model;

@SuppressWarnings("serial")
public class AccountNotFoundException extends AccountException {

	private static final String AccountNotFoundmessage = "Following account cannot be found: ";

	public AccountNotFoundException(String name) {
		super(AccountNotFoundmessage + name);
	}

}
