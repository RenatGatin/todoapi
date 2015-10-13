package ca.gatin.todoapi.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ca.gatin.todoapi.model.Account;
import ca.gatin.todoapi.util.AppConstants;
import ca.gatin.todoapi.view.AccountView;
import ca.gatin.todoapi.view.AccountsView;
import ca.gatin.todoapi.view.ResultView;

/**
 * 
 * @author RGatin
 * @since 12-Oct-2015
 * 
 * Testing all AccountService methods
 *
 */
public class AccountServiceTest {

	private AccountService accountService = new AccountService();
	
	@Test
	public void getAccountByIdTest() {
		AccountView account = accountService.getAccountById(1);
		Assert.assertEquals(AppConstants.SUCCESS, account.getResult().getResultCode());
	}

	@Test
	public void createAccountTest() {
		// Commented this test because do not want to overpopulate the database.
		// Uncomment it when need to test again.
		// ResultView result = accountService.createAccount("TestF", "TestL",
		// "dsfjlkj@sdfj.com", "sdfsdf");
		// Assert.assertEquals(AppConstants.SUCCESS, result.getResultCode());
	}

	/**
	 * It is just a console output test, no Assertion!
	 */
	@Test
	public void getAccountsTest() {
		AccountsView accounts = accountService.getAccounts();
		Assert.assertEquals(AppConstants.SUCCESS, accounts.getResult().getResultCode());
	}

	/**
	 * It is just a console output test, no Assertion!
	 */
	@Test
	public void getActiveNotActiveAccountsTest() {
		AccountsView activeAccounts = accountService.getActiveAccounts();
		Assert.assertEquals(AppConstants.SUCCESS, activeAccounts.getResult().getResultCode());
		AccountsView notActiveAccounts = accountService.getNotActiveAccounts();
		Assert.assertEquals(AppConstants.SUCCESS, notActiveAccounts.getResult().getResultCode());
	}

}
