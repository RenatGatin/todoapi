package ca.gatin.todoapi.view;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ca.gatin.todoapi.model.Account;

@XmlRootElement
public class AccountsView {
	
	@XmlElement
	private ResultView result;
	
	@XmlElement
	private List<Account> accounts;
	
	public AccountsView() {}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public ResultView getResult() {
		return result;
	}
	public void setResult(ResultView result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "AccountsView [result=" + result + ", accounts=" + accounts + "]";
	}
	
}
