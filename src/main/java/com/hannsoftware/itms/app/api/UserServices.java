/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.api;

import com.google.gson.Gson;
import com.hannsoftware.itms.app.domain.UserDTO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class UserServices {
        
   public static UserDTO loggedUser = null;
    
    public static String registerUser(String fullName, String username, String password, String role) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(RestAPI.API_URL+"/users/register");
            httpPost.setHeader("Content-Type", "application/json");

            UserDTO user = new UserDTO(fullName, username, password, role);
            String json = new Gson().toJson(user);

            httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                return response.getCode()+"";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while registering the User!";
        }
    }
    
     public static boolean login(String username, String password) {
       try (CloseableHttpClient client = HttpClients.createDefault()) {
        HttpPost httpPost = new HttpPost(RestAPI.API_URL + "/auth/login");
        httpPost.setHeader("Content-Type", "application/json");

        AuthRequest authRequest = new AuthRequest(username, password);
        String json = new Gson().toJson(authRequest);

        httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

        try (CloseableHttpResponse response = client.execute(httpPost)) {
            if (response.getCode() == 200) { 
                String responseBody = null;
                try {
                    responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                } catch (ParseException ex) {
                    Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                }

                Gson gson = new Gson();
                UserDTO userResponse = gson.fromJson(responseBody, UserDTO.class);

                UserServices.loggedUser = userResponse;
                return true;
            } else {
                return false;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
     }
     
     public static boolean updatePassword(String newPassword) {
       try (CloseableHttpClient client = HttpClients.createDefault()) {
        String encodedPassoword = URLEncoder.encode(newPassword, StandardCharsets.UTF_8.toString());
        HttpPost httpPost = new HttpPost(RestAPI.API_URL + "/users/"+ UserServices.loggedUser.getId() + "/update-password?newPassword=".concat(encodedPassoword));
        httpPost.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = client.execute(httpPost)) {
            if (response.getCode() == 200) { 
                return true;
            } else {
                return false;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
     }
    
}
