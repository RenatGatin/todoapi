package ca.gatin.todoapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import ca.gatin.todoapi.model.Account;
import ca.gatin.todoapi.util.PropertiesUtil;
import ca.gatin.todoapi.util.TimestampUtils;

/**
 * 
 * @author RGatin
 * @since 10-Oct-2015
 * 
 *        This class handles the Database persistence
 * 
 */
public class DatabaseQueries {
	private Connection conn;
	private PreparedStatement psFindAllAccounts;
	private PreparedStatement psFindAccountsByActivity;
	private PreparedStatement psFindAccountByAccountId;
	private PreparedStatement psInsertNewAccount;
	private PreparedStatement psCheckCredentials;
	private PreparedStatement psCheckToken;
	private PreparedStatement psFindToken;
	private PreparedStatement psUpdateCurrentToken;
	private PreparedStatement psCreateNewTokenRecord;
	private PreparedStatement psGetAccountByToken;

	private final int TOKEN_EXPIRE_IN_DAYS = 30;

	private static DatabaseQueries instance;

	public static synchronized DatabaseQueries getInstance() {
		if (instance == null) {
			instance = new DatabaseQueries();
		}
		return instance;
	}

	private DatabaseQueries() {
	}

	private void openConnection() {
		Properties dbProperties = PropertiesUtil.getInstance().getDBProperties();

		try {
			String driverClass = dbProperties.getProperty("DB_DRIVER_CLASS");
			String host = dbProperties.getProperty("DB_HOST");
			String port = dbProperties.getProperty("DB_PORT");
			String databaseName = dbProperties.getProperty("DB_NAME");
			String user = dbProperties.getProperty("DB_USERNAME");
			String password = dbProperties.getProperty("DB_PASSWORD");
			String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;

			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			initializePrepareStatements();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializePrepareStatements() throws SQLException {
		String sqlFindAllAccounts = "SELECT * FROM `Account`";
		String sqlFindAccountsByActivity = "SELECT * FROM `Account` WHERE is_active = ?";
		String sqlFindAccountByAccountId = "SELECT * FROM `Account` WHERE id = ?";
		String sqlInsertNewAccount = "INSERT INTO `Account` " + "(`first_name`, `last_name`, `email`, `password`, `date_created`) " + "VALUES (?, ?, ?, ?, ?);";
		String sqlCheckCredentials = "SELECT id FROM `Account` WHERE email = ? AND password = ?";
		String sqlCheckToken = "SELECT * FROM Token	 WHERE DATE(NOW()) < DATE(date_will_expire) AND account_id = ?";
		String sqlFindToken = "SELECT * FROM Token	 WHERE account_id = ?";
		String sqlUpdateCurrentToken = "UPDATE Token SET token = ?, date_will_expire = ?, date_last_modified = ? WHERE id = ?";
		String sqlCreateNewTokenRecord = "INSERT INTO Token (token, date_will_expire, date_created, account_id) VALUES (?, ?, ?, ?)";
		String sqlGetAccountByToken = "SELECT Account.* FROM Account INNER JOIN Token ON Account.id = Token.account_id WHERE Token.token = ?";

		psFindAllAccounts = conn.prepareStatement(sqlFindAllAccounts);
		psFindAccountsByActivity = conn.prepareStatement(sqlFindAccountsByActivity);
		psFindAccountByAccountId = conn.prepareStatement(sqlFindAccountByAccountId);
		psInsertNewAccount = conn.prepareStatement(sqlInsertNewAccount);
		psCheckCredentials = conn.prepareStatement(sqlCheckCredentials);
		psCheckToken = conn.prepareStatement(sqlCheckToken);
		psFindToken = conn.prepareStatement(sqlFindToken);
		psUpdateCurrentToken = conn.prepareStatement(sqlUpdateCurrentToken);
		psCreateNewTokenRecord = conn.prepareStatement(sqlCreateNewTokenRecord);
		psGetAccountByToken = conn.prepareStatement(sqlGetAccountByToken);
	}

	/**
	 * Gets all Accounts
	 * 
	 * @return Accounts or empty list if no Account found
	 */
	public List<Account> getAccounts() {
		openConnection();
		List<Account> accountsList = new ArrayList<>();

		try {
			ResultSet rs = psFindAllAccounts.executeQuery();
			while (rs.next()) {
				Account account = parseAccount(rs);
				accountsList.add(account);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return accountsList;
	}

	/**
	 * Gets Accounts by activity (active or not active)
	 * 
	 * @return Accounts or empty list if no Account found
	 */
	public List<Account> getAccountsByActivity(boolean isActive) {
		openConnection();
		List<Account> accountsList = new ArrayList<>();

		try {
			psFindAccountsByActivity.setBoolean(1, isActive);
			ResultSet rs = psFindAccountsByActivity.executeQuery();
			while (rs.next()) {
				Account account = parseAccount(rs);

				if (account.isActive() == isActive)
					accountsList.add(account);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return accountsList;
	}

	/**
	 * Gets the first corresponding occurrence of Account
	 * 
	 * @param id
	 * @return Account or null if not match found
	 */
	public Account getAccountById(int id) {
		openConnection();
		Account account = null;

		try {
			psFindAccountByAccountId.setInt(1, id);
			ResultSet rs = psFindAccountByAccountId.executeQuery();
			if (rs.next()) {
				account = parseAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return account;
	}

	/**
	 * Receives ResultSet and creates an instance of Account object from it
	 * 
	 * @param rs
	 * @return Account
	 * @throws SQLException
	 */
	private Account parseAccount(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String email = rs.getString("email");
		String password = rs.getString("password");
		boolean isActive = rs.getBoolean("is_active");
		Date dateCreated = rs.getDate("date_created");
		Date dateLastModified = rs.getDate("date_last_modified");

		Account account = new Account(id, firstName, lastName, email, password, isActive, dateCreated, dateLastModified);
		return account;
	}

	/**
	 * 
	 * @param account
	 * @return true if successfully created
	 */
	public boolean createAccount(Account account) {
		openConnection();
		int effectedRows = 0;

		String firstName = (account.getFirstName() != null) ? "'" + account.getFirstName() + "'" : "NULL";
		String lastName = (account.getLastName() != null) ? "'" + account.getLastName() + "'" : "NULL";
		String dateCreated = TimestampUtils.getDateAndTimeStringForCurrentDate();

		try {
			int i = 1;
			psInsertNewAccount.setString(i++, firstName);
			psInsertNewAccount.setString(i++, lastName);
			psInsertNewAccount.setString(i++, account.getEmail());
			psInsertNewAccount.setString(i++, account.getPassword());
			psInsertNewAccount.setString(i++, dateCreated);
			effectedRows = psInsertNewAccount.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return effectedRows > 0;
	}

	/**
	 * Check if account with email and password exists. If yes return id, if no
	 * return -1
	 * 
	 * @param email
	 * @param password
	 * @return id
	 */
	public int checkCredentials(String email, String password) {
		openConnection();
		int id = -1;

		try {
			psCheckCredentials.setString(1, email);
			psCheckCredentials.setString(2, password);
			ResultSet rs = psCheckCredentials.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return id;
	}

	/**
	 * 
	 * Check if token record with valid expire date exists
	 * 
	 * @param id
	 * @return token or null
	 */
	public String checkToken(int id) {
		openConnection();
		String token = null;

		try {
			psCheckToken.setInt(1, id);
			ResultSet rs = psCheckToken.executeQuery();
			if (rs.next()) {
				token = rs.getString("token");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return token;
	}

	/**
	 * 
	 * Find token record by Account id
	 * 
	 * @param id
	 * @return token or null
	 */
	private int findToken(int id) {
		openConnection();
		int tokenId = -1;

		try {
			psFindToken.setInt(1, id);
			ResultSet rs = psFindToken.executeQuery();
			if (rs.next()) {
				tokenId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tokenId;
	}

	/**
	 * 1. check if token already exists 2. update current token 3. create new
	 * token record
	 * 
	 * @param id
	 * @param token
	 * @return
	 */
	public boolean saveToken(int accountId, String token) {
		boolean result = false;

		// check if token already exists
		int currentTokenId = findToken(accountId);
		if (currentTokenId > 0) {
			// update current token
			result = updateCurrentToken(currentTokenId, token);
		} else {
			// create new token record
			result = createNewTokenRecord(accountId, token);
		}

		return result;
	}

	/**
	 * Updates current token + saves modified date
	 * 
	 * @param currentTokenId
	 * @param token
	 * @return true if row effected, false not
	 */
	private boolean updateCurrentToken(int currentTokenId, String token) {
		openConnection();
		int effectedRows = 0;

		String dateWillExpire = TimestampUtils.createTokenExpireDate(TOKEN_EXPIRE_IN_DAYS);
		String dateLastModified = TimestampUtils.getDateAndTimeStringForCurrentDate();

		try {
			int i = 1;
			psUpdateCurrentToken.setString(i++, token);
			psUpdateCurrentToken.setString(i++, dateWillExpire);
			psUpdateCurrentToken.setString(i++, dateLastModified);
			psUpdateCurrentToken.setInt(i++, currentTokenId);
			effectedRows = psUpdateCurrentToken.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return effectedRows > 0;
	}

	/**
	 * Creates new token + saves created date
	 * 
	 * @param accountId
	 * @param token
	 * @return true if row effected, false not
	 */
	private boolean createNewTokenRecord(int accountId, String token) {
		openConnection();
		int effectedRows = 0;

		String dateWillExpire = TimestampUtils.createTokenExpireDate(TOKEN_EXPIRE_IN_DAYS);
		String dateCreated = TimestampUtils.getDateAndTimeStringForCurrentDate();

		try {
			int i = 1;
			psCreateNewTokenRecord.setString(i++, token);
			psCreateNewTokenRecord.setString(i++, dateWillExpire);
			psCreateNewTokenRecord.setString(i++, dateCreated);
			psCreateNewTokenRecord.setInt(i++, accountId);
			effectedRows = psCreateNewTokenRecord.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return effectedRows > 0;
	}
	
	/**
	 * Retrieves whole Account object by given token
	 * 
	 * @param token
	 * @return Account
	 */
	public Account getAccountByToken(String token) {
		openConnection();
		Account account = null;

		try {
			psGetAccountByToken.setString(1, token);
			ResultSet rs = psGetAccountByToken.executeQuery();
			if (rs.next()) {
				account = parseAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return account;
	}

}
