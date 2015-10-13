package ca.gatin.todoapi.util;

public final class AppConstants {
	public static final int FAILURE = -1;
	public static final int SUCCESS = 0;
	public static final int NO_ACCOUNT_FOUND = 1;
	public static final int WRONG_PASSWORD = 2;
	public static final int USER_ALREADY_EXISTS = 3;
	public static final int UNKNOWN_ERROR = 4;
	public static final int SUCCESS_CREATE_NEW_ACCOUNT = 5;

	public static final String MESSAGE_MISSING_PARAMETER = "Missing parameter(s)";
	public static final String MESSAGE_INVALID_PARAMETER = "Invalid parameter(s)";
	public static final String MESSAGE_MISSING_ACCOUNT = "Account does not exist";
	public static final String MESSAGE_ACCOUNT_FOUND = "Account(s) successfully found";
	public static final String MESSAGE_ACCOUNT_CREATED = "Account successfully created";
	public static final String MESSAGE_ACCOUNT_NOT_FOUND = "Account(s) not found";
	public static final String MESSAGE_USERNAME_PASSWORD_COMBINATION_NOT_FOUND = "No such username & password combination found";
	public static final String MESSAGE_DB_ERROR_ACCURED = "DB error accured";
	public static final String MESSAGE_TOKEN_RETRIEVED = "Existing valid token is retrieved";
	public static final String MESSAGE_NEW_TOKEN_GENERATED = "New token generated";
	public static final String MESSAGE_RESOURCE_IN_DEVELOPMENT = "Resource is in development";


	public static final String STATUS_CODE_REQUESTED = "REQUESTED";
	public static final String STATUS_CODE_APPROVED = "APPROVED";
	public static final String STATUS_CODE_DECLINED = "DECLINED";
	public static final String STATUS_CODE_ACTIVE = "ACTIVE";
	public static final String STATUS_CODE_IN_PROGRESS = "IN_PROGRESS";
	public static final String STATUS_CODE_DEACTIVATED = "DEACTIVATED";

	public static final long DAY_IN_MILLISECONDS = 24 * 60 * 60 * 1000l;
}
