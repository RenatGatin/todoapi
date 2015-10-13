package ca.gatin.todoapi.view;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author RGatin
 * @since 12-Oct-2015
 * 
 * Represents Access token
 *
 */
@XmlRootElement
public class TokenView {

	@XmlElement
	private ResultView result;

	@XmlElement
	private String token;

	public TokenView() {}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TokenView [result=" + result + ", token=" + token + "]";
	}

}
