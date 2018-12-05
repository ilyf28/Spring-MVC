package base.module.controller;

import javax.servlet.http.HttpSession;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private final String FACEBOOK_APP_ID = "";
	private final String FACEBOOK_APP_SECRET = "";
	private final String FACEBOOK_REDIRECT_URI = "http://localhost:8080/facebook/authorization";
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		logger.info("Log in page");
		
		return "login";
	}
	
	@RequestMapping(value = "/facebook/signin", method = RequestMethod.GET)
	public String facebookSignin(HttpSession session) {
		logger.info("Facebook Sign in page");
		
		String url = "https://www.facebook.com/v2.8/dialog/oauth";
		url += "?client_id=" + FACEBOOK_APP_ID;
		url += "&redirect_uri=" + FACEBOOK_REDIRECT_URI;
		url += "&scope=public_profile,email";
		
		return "redirect:" + url;
	}
	
	@RequestMapping(value = "/facebook/authorization")
	public String getFacebookAuthorizationCode(HttpSession session, String code, String state) throws Exception {
		logger.debug("Facebook Authorization Code : " + code);
		
		String accessToken = getFacebookAccessToken(session, code);
		getFacebookUserData(session, accessToken);
		
		return "redirect:/";
	}
	
	private String getFacebookAccessToken(HttpSession session, String code) throws Exception {
		String url = "https://graph.facebook.com/v2.8/oauth/access_token";
		url += "?client_id=" + FACEBOOK_APP_ID;
		url += "&redirect_uri=" + FACEBOOK_REDIRECT_URI;
		url += "&client_secret=" + FACEBOOK_APP_SECRET;
		url += "&code=" + code;
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		String rawString = client.execute(get, new BasicResponseHandler());
		logger.debug("Facebook Access Token Raw String: " + rawString);
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(rawString);
		String facebookAccessToken = (String) jsonObject.get("access_token");
		logger.debug("Facebook Access Token: " + facebookAccessToken);
		
		session.setAttribute("FACEBOOK_ACCESS_TOKEN", facebookAccessToken);
		
		return facebookAccessToken;
	}
	
	private void getFacebookUserData(HttpSession session, String accessToken) throws Exception {
		String url = "https://graph.facebook.com/me";
		url += "?access_token=" + accessToken;
		url += "&fields=id,name,email,picture";
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		String rawString = client.execute(get, new BasicResponseHandler());
		logger.debug("Facebook User Data Raw String: " + rawString);
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(rawString);
		logger.debug("Facebook User Data JSON: " + jsonObject);
	}
}
