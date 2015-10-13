package ca.gatin.todoapi.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.gatin.todoapi.model.Account;
import ca.gatin.todoapi.service.AccountService;
import ca.gatin.todoapi.util.AppConstants;
import ca.gatin.todoapi.view.AccountView;
import ca.gatin.todoapi.view.AccountsView;
import ca.gatin.todoapi.view.ResultView;
import ca.gatin.todoapi.view.TokenView;

@Path("/accounts")
public class AccountResource {
	AccountService accountService = new AccountService();

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_HTML)
	public String wsTest() {
		return "REST web Service works!";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public AccountsView getAccounts() {
		return accountService.getAccounts();
	}

	@GET
	@Path("/active")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountsView getActiveAccounts() {
		return accountService.getActiveAccounts();
	}

	@GET
	@Path("/notactive")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountsView getNotActiveAccounts() {
		return accountService.getNotActiveAccounts();
	}

	@GET
	@Path("/{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountView getAccount(@PathParam("accountId") int id) {
		return accountService.getAccountById(id);
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public TokenView getToken(@FormParam("email") String email, @FormParam("password") String password) {
		return accountService.login(email, password);
	}

	@POST
	@Path("/bytoken")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountView getAccountByToken(@FormParam("token") String token) {
		return accountService.getAccountByToken(token);
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultView createAccount(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("email") String email, @FormParam("password") String password) {
		return accountService.createAccount(firstName, lastName, email, password);
	}
	
	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultView updateAccount(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("email") String email, @FormParam("password") String password) {
		//TODO: Create Account update resource		
		return new ResultView(AppConstants.FAILURE, AppConstants.MESSAGE_RESOURCE_IN_DEVELOPMENT);
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultView updateAccount(@FormParam("id") int id) {
		//TODO: Delete Account resource		
		return new ResultView(AppConstants.FAILURE, AppConstants.MESSAGE_RESOURCE_IN_DEVELOPMENT);
	}
	
	//TODO: Create Deactivate and Activate Account resources

}
