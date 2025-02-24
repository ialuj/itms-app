/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hannsoftware.itms.app.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hannsoftware.itms.app.domain.AuditLogDTO;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class AuditLogServices {
    
    public static List<AuditLogDTO> getAllAuditLogs() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
           String url = RestAPI.API_URL.concat("/auditlogs");
           

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getCode() == 200) { 
                try {
                    String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    Type listType = new TypeToken<List<AuditLogDTO>>() {}.getType();
                    Gson gson = new Gson();
                    List<AuditLogDTO> auditLogs = gson.fromJson(responseBody, listType);
                    return auditLogs;
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
    
}
