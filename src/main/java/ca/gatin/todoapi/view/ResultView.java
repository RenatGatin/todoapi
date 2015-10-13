package ca.gatin.todoapi.view;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author RGatin
 * @since 12-Oct-2015
 * 
 * Provides more extended REST APIs call result
 *
 */
@XmlRootElement
public class ResultView {
	@XmlElement
	private int resultCode;
	@XmlElement
	private String resultMessage;

	public ResultView() {}

	public ResultView(int resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public void fill(int resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	@Override
	public String toString() {
		return "ResultView [resultCode=" + resultCode + ", resultMessage=" + resultMessage + "]";
	}
	
}
