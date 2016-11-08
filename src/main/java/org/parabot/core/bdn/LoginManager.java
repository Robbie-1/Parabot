package org.parabot.core.bdn;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.parabot.api.io.WebUtil;
import org.parabot.core.bdn.test.Example;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author EmmaStone
 */
public class LoginManager {

    private static APIKeyParticipant[] implementers = new APIKeyParticipant[]{Example.PARTICIPANT};
    private APIKey key;
    private LoginManager context;
    private static final String endpoint = "http://v3.bdn.parabot.org/api/";
    private static final String createLogin = "users/create/login";
    private static final String retrieveLogin = "users/retrieve/login/%s";

    public LoginManager(APIKey key) {
        this.key = key;
        this.context = this;
    }

    public void setParticipants() {
        for (APIKeyParticipant participant : implementers) {
            participant.setAPIKey(key);
        }
    }

    public LoginManager getContext() {
        return context;
    }

    public static void login() {
        try {
            JSONParser parser = WebUtil.getJsonParser();
            JSONObject o = (JSONObject) parser.parse(WebUtil.getContents(endpoint + createLogin, "redirect=http://v3.bdn.parabot.org/asd/a"));
            String url = (String) o.get("url");
            String key = (String) o.get("key");
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URL(url).toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            URL urlKey = new URL(endpoint + String.format(retrieveLogin, key));
            URLConnection connection = WebUtil.getConnection(urlKey);
            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            int tries = 1;
            while (urlConnection.getResponseCode() != 200 && tries <= 10) {
                connection = WebUtil.getConnection(urlKey);
                urlConnection = (HttpURLConnection) connection;
                System.out.println("Waiting for the user to login: " + tries);
                Thread.sleep(5000);
                tries++;
            }
            if (tries > 10) {
                System.out.println("Fucking jesus christ, why does it take you so long to login...");
            } else {
                JSONObject o1 = (JSONObject) parser.parse(WebUtil.getContents(connection));
                System.out.println("API key is: " + o1.get("api"));
            }
        } catch (ParseException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
