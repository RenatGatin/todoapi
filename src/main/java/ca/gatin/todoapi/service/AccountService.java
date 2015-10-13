package ca.gatin.todoapi.service;

import java.util.List;

import ca.gatin.todoapi.database.DatabaseQueries;
import ca.gatin.todoapi.model.Account;
import ca.gatin.todoapi.util.AppConstants;
import ca.gatin.todoapi.util.SessionIdentifierGenerator;
import ca.gatin.todoapi.view.AccountView;
import ca.gatin.todoapi.view.AccountsView;
import ca.gatin.todoapi.view.ResultView;
import ca.gatin.todoapi.view.TokenView;

/**
 * 
 * @author RGatin
 * @since 10-Oct-2015
 * 
 *        This class contains all business logic related to Accounts
 * 
 */
public class AccountService {

	/**
	 * Get all available Accounts (active and not active)
	 * 
	 * @return AccountsView
	 */
	public AccountsView getAccounts() {
		AccountsView accountsView = new AccountsView();
		List<Account> accounts = DatabaseQueries.getInstance().getAccounts();
		ResultView result = new ResultView();

		if (accounts != null) {
			if (accounts.size() > 0) {
				result.setResultCode(AppConstants.SUCCESS);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_FOUND);
				accountsView.setAccounts(accounts);
			} else {
				result.setResultCode(AppConstants.NO_ACCOUNT_FOUND);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_NOT_FOUND);
			}
		} else {
			result.setResultCode(AppConstants.FAILURE);
			result.setResultMessage(AppConstants.MESSAGE_DB_ERROR_ACCURED);
		}
		accountsView.setResult(result);

		return accountsView;
	}

	/**
	 * Get all only active Accounts
	 * 
	 * @return AccountsView
	 */
	public AccountsView getActiveAccounts() {
		AccountsView accountsView = new AccountsView();
		List<Account> accounts = DatabaseQueries.getInstance().getAccountsByActivity(true);
		ResultView result = new ResultView();

		if (accounts != null) {
			if (accounts.size() > 0) {
				result.setResultCode(AppConstants.SUCCESS);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_FOUND);
				accountsView.setAccounts(accounts);
			} else {
				result.setResultCode(AppConstants.NO_ACCOUNT_FOUND);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_NOT_FOUND);
			}
		} else {
			result.setResultCode(AppConstants.FAILURE);
			result.setResultMessage(AppConstants.MESSAGE_DB_ERROR_ACCURED);
		}
		accountsView.setResult(result);

		return accountsView;
	}

	/**
	 * Get all only not active Accounts
	 * 
	 * @return AccountsView
	 */
	public AccountsView getNotActiveAccounts() {
		AccountsView accountsView = new AccountsView();
		List<Account> accounts = DatabaseQueries.getInstance().getAccountsByActivity(false);
		ResultView result = new ResultView();

		if (accounts != null) {
			if (accounts.size() > 0) {
				result.setResultCode(AppConstants.SUCCESS);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_FOUND);
				accountsView.setAccounts(accounts);
			} else {
				result.setResultCode(AppConstants.NO_ACCOUNT_FOUND);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_NOT_FOUND);
			}
		} else {
			result.setResultCode(AppConstants.FAILURE);
			result.setResultMessage(AppConstants.MESSAGE_DB_ERROR_ACCURED);
		}
		accountsView.setResult(result);

		return accountsView;
	}

	/**
	 * Get Account by given id
	 * 
	 * @param id
	 * @return AccountsView
	 */
	public AccountView getAccountById(int id) {
		AccountView accountView = new AccountView();
		ResultView result = new ResultView();

		if (id > 0) {
			Account account = DatabaseQueries.getInstance().getAccountById(id);
			if (account != null) {
				result.setResultCode(AppConstants.SUCCESS);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_FOUND);
				accountView.setAccount(account);
			} else {
				result.setResultCode(AppConstants.NO_ACCOUNT_FOUND);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_NOT_FOUND);
			}
		} else {
			result.setResultCode(AppConstants.FAILURE);
			result.setResultMessage(AppConstants.MESSAGE_INVALID_PARAMETER);
		}
		accountView.setResult(result);

		return accountView;
	}

	/**
	 * Persist the Account in the database
	 * 
	 * @param firstName
	 *            (can be null)
	 * @param lastName
	 *            (can be null)
	 * @param email
	 * @param password
	 * @return true if successfully created
	 */
	public ResultView createAccount(String firstName, String lastName, String email, String password) {
		ResultView result = new ResultView();

		if (email != null && password != null) {

			Account account = new Account();
			account.setFirstName(firstName);
			account.setLastName(lastName);
			account.setEmail(email);
			account.setPassword(password);

			boolean isAccountSuccessfullyCreated = DatabaseQueries.getInstance().createAccount(account);

			if (isAccountSuccessfullyCreated) {
				result.fill(AppConstants.SUCCESS_CREATE_NEW_ACCOUNT, AppConstants.MESSAGE_ACCOUNT_CREATED);
			} else {
				result.fill(AppConstants.FAILURE, AppConstants.MESSAGE_DB_ERROR_ACCURED);
			}
		} else {
			result.fill(AppConstants.FAILURE, AppConstants.MESSAGE_MISSING_PARAMETER);
		}

		return result;
	}

	/**
	 * 1. check if active account exists 2. check if valid token allready exists
	 * 3. generate access token 4. save token to db
	 * 
	 * @param email
	 * @param password
	 * @return TokenView
	 */
	public TokenView login(String email, String password) {
		TokenView tokenView = new TokenView();
		ResultView result = new ResultView();
		String token;

		if (email != null && password != null) {
			// 1. check if active account exists
			int id = DatabaseQueries.getInstance().checkCredentials(email, password);

			// 2. check if valid token allready exists
			if (id > 0) {
				token = DatabaseQueries.getInstance().checkToken(id);
				if (token != null) {
					result.fill(AppConstants.SUCCESS, AppConstants.MESSAGE_TOKEN_RETRIEVED);
					tokenView.setToken(token);
					tokenView.setResult(result);
				} else {
					// 3. generate access token
					token = SessionIdentifierGenerator.nextSessionId();
					// 4. save token to db
					boolean isTokenSaved = DatabaseQueries.getInstance().saveToken(id, token);
					if (isTokenSaved) {
						result.fill(AppConstants.SUCCESS, AppConstants.MESSAGE_NEW_TOKEN_GENERATED);
						tokenView.setToken(token);
						tokenView.setResult(result);
					} else {
						result.fill(AppConstants.FAILURE, AppConstants.MESSAGE_DB_ERROR_ACCURED);
						tokenView.setResult(result);
					}
				}
			} else {
				result.fill(AppConstants.NO_ACCOUNT_FOUND, AppConstants.MESSAGE_USERNAME_PASSWORD_COMBINATION_NOT_FOUND);
				tokenView.setResult(result);
			}
		}

		return tokenView;
	}

	/**
	 * Tries to retrieve the Account buy token (Token expire date is not checked)
	 * 
	 * @param token
	 * @return
	 */
	public AccountView getAccountByToken(String token) {
		AccountView accountView = new AccountView();
		ResultView result = new ResultView();

		if (token != null && !token.equals("")) {
			
			//TODO: Check it Token is not expired first
			// ...
			
			Account account = DatabaseQueries.getInstance().getAccountByToken(token);
			if (account != null) {
				result.setResultCode(AppConstants.SUCCESS);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_FOUND);
				accountView.setAccount(account);
			} else {
				result.setResultCode(AppConstants.NO_ACCOUNT_FOUND);
				result.setResultMessage(AppConstants.MESSAGE_ACCOUNT_NOT_FOUND);
			}
		} else {
			result.setResultCode(AppConstants.FAILURE);
			result.setResultMessage(AppConstants.MESSAGE_MISSING_PARAMETER);
		}
		accountView.setResult(result);

		return accountView;
	}
}
