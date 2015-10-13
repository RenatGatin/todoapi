package ca.gatin.todoapi.view;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ca.gatin.todoapi.model.Account;

/**
 * 
 * @author RGatin
 * @since 12-Oct-2015
 * 
 * Represents Accounts
 *
 */
@XmlRootElement
public class AccountView {

	@XmlElement
	private ResultView result;

	@XmlElement
	private Account account;

	public AccountView() {}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "AccountView [result=" + result + ", account=" + account + "]";
	}

}