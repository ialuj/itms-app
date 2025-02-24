/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hannsoftware.itms.app.domain.CommentsDTO;
import com.hannsoftware.itms.app.domain.TicketDTO;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class TicketServices {
    
    public static String registerTicket(String title, String description, String priority, String category) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = RestAPI.API_URL.concat("/tickets/register");
            
            String employeeId = UserServices.loggedUser.getId()+"";
            url = url.concat("?employeeId=").concat(employeeId);

            TicketDTO ticketDTO = new TicketDTO(title, description, priority, category);
            String json = new Gson().toJson(ticketDTO);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                return response.getCode()+"";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while registering the User!";
        }
    }
    
    public static int getNumberOfTicketsByStatus(String status) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
           String url = RestAPI.API_URL.concat("/tickets");
           String encodedStatus = URLEncoder.encode(status, StandardCharsets.UTF_8.toString());
           if("Employee".equalsIgnoreCase(UserServices.loggedUser.getRole())) {
                String employeeId = UserServices.loggedUser.getId() + "";
                url = url.concat("/employee/").concat(employeeId).concat("/status/count?status=").concat(encodedStatus);
            } else if("IT Support".equalsIgnoreCase(UserServices.loggedUser.getRole())) {
                url = url.concat("/status/count?status=").concat(encodedStatus);
            }

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = client.execute(httpGet)) {
                if(response.getCode() == 200) {
                    String responseBody = null;
                try {
                    responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    Gson gson = new Gson();
                    String value = gson.fromJson(responseBody, String.class);
                   return Integer.valueOf(value);
                } catch (ParseException ex) {
                    Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static int getNumberOfUnsolvedTickets() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = RestAPI.API_URL.concat("/tickets");
            if("Employee".equalsIgnoreCase(UserServices.loggedUser.getRole())) {
                String employeeId = UserServices.loggedUser.getId() + "";
                url = url.concat("/employee/").concat(employeeId).concat("/unsolved");
            } else if("IT Support".equalsIgnoreCase(UserServices.loggedUser.getRole())) {
                url = url.concat("/unsolved");
            }

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = client.execute(httpGet)) {
                if(response.getCode() == 200) {
                try {
                    String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    Gson gson = new Gson();
                    String value = gson.fromJson(responseBody, String.class);
                   return Integer.valueOf(value);
                } catch (ParseException ex) {
                    Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static List<TicketDTO> getTicketsByStatus(String status) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
           String url = RestAPI.API_URL.concat("/tickets");
           String encodedStatus = URLEncoder.encode(status, StandardCharsets.UTF_8.toString());
           if("Employee".equalsIgnoreCase(UserServices.loggedUser.getRole())) {
                String employeeId = UserServices.loggedUser.getId() + "";
                url = url.concat("/employee/").concat(employeeId).concat("/status?status=").concat(encodedStatus);
            } else if("IT Support".equalsIgnoreCase(UserServices.loggedUser.getRole())) {
                url = url.concat("/status?status=").concat(encodedStatus);
            }

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getCode() == 200) { 
                try {
                    String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    Type listType = new TypeToken<List<TicketDTO>>() {}.getType();
                    Gson gson = new Gson();
                    List<TicketDTO> tickets = gson.fromJson(responseBody, listType);
                    return tickets;
                } catch (ParseException ex) {
                    Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                return new ArrayList<>();
            }
        }
           
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return null;
    }

    public static List<CommentsDTO> getCommentsByTicketId(Long ticketId) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
           String url = RestAPI.API_URL.concat("/tickets/").concat(ticketId.toString()).concat("/comments");
           

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getCode() == 200) { 
                try {
                    String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    Type listType = new TypeToken<List<CommentsDTO>>() {}.getType();
                    Gson gson = new Gson();
                    List<CommentsDTO> comments = gson.fromJson(responseBody, listType);
                    return comments;
                } catch (ParseException ex) {
                    Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                return new ArrayList<>();
            }
        }
           
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return null;
    }

    public static List<TicketDTO> getAllUnsolvedTickets() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
           String url = RestAPI.API_URL.concat("/tickets/unsolved-tickets");
           

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getCode() == 200) { 
                try {
                    String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    Type listType = new TypeToken<List<TicketDTO>>() {}.getType();
                    Gson gson = new Gson();
                    List<TicketDTO> comments = gson.fromJson(responseBody, listType);
                    return comments;
                } catch (ParseException ex) {
                    Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                return new ArrayList<>();
            }
        }
           
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return null;
    }

    public static String addComment(Long ticketId, String comment) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = RestAPI.API_URL.concat("/tickets/").concat(ticketId.toString()).concat("/comment");
            
            String employeeId = UserServices.loggedUser.getId()+"";
            url = url.concat("?userId=").concat(employeeId);

            CommentsDTO commentsDTO = new CommentsDTO();
            commentsDTO.setComment(comment);
            String json = new Gson().toJson(commentsDTO);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                return response.getCode()+"";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while adding a comment on Ticket with ID: ".concat(ticketId.toString());
        }
    }

    public static String updateTicketStatus(Long ticketId, String newStatus) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = RestAPI.API_URL.concat("/tickets/").concat(ticketId.toString()).concat("/update-status");
            
            String employeeId = UserServices.loggedUser.getId()+"";
            String encodedStatus = URLEncoder.encode(newStatus, StandardCharsets.UTF_8.toString());
            url = url.concat("?userId=").concat(employeeId).concat("?status=").concat(encodedStatus);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                return response.getCode()+"";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while updating Ticket with ID: ".concat(ticketId.toString());
        }
    }
}
