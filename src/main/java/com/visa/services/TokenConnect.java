/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visa.services;

import com.mastercard.api.core.ApiConfig;
import com.mastercard.api.core.exception.*;
import com.mastercard.api.core.model.*;
import com.mastercard.api.core.model.map.*;
import com.mastercard.api.core.security.oauth.OAuthAuthentication;
import com.mastercard.api.mdestokenconnect.*;
import com.mastercard.api.core.security.mdes.*;

import java.io.*;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.http.*;

/**
 *
 * @author wuthichailee
 */

public class TokenConnect {

    public String retrieveTokenRequestors() throws Exception {
        err("Token Requestor Invoked");
        
        // Authenticate to MDES Sandbox
        authenticate();

        try {
            RequestMap map = new RequestMap();
            map.set("requestId", "123456");
            map.set("accountRanges[0]", "512345678901");
            map.set("accountRanges[1]", "512345678902");
            map.set("accountRanges[2]", "512345678903");
            GetEligibleTokenRequestors response = GetEligibleTokenRequestors.create(map);

            out(response, "responseId"); //-->123456
            out(response, "tokenRequestors[0].tokenRequestorId"); //-->12345678901
            out(response, "tokenRequestors[0].name"); //-->Token Requestor 1
            out(response, "tokenRequestors[0].consumerFacingEntityName"); //-->Sunday Store
            out(response, "tokenRequestors[0].imageAssetId"); //-->dbc55444-496a-4896-b41c-5d5e2dd431e2
            out(response, "tokenRequestors[0].tokenRequestorType"); //-->MERCHANT
            out(response, "tokenRequestors[0].enabledAccountRanges[0]"); //-->5123456789010000000
            out(response, "tokenRequestors[0].enabledAccountRanges[1]"); //-->5123456789020000000
            out(response, "tokenRequestors[0].enabledAccountRanges[2]"); //-->5123456789030000000
            out(response, "tokenRequestors[0].supportedPushMethods[0]"); //-->IOS
            out(response, "tokenRequestors[0].supportedPushMethods[1]"); //-->WEB
            out(response, "tokenRequestors[0].supportsMultiplePushedCards"); //-->true
            out(response, "tokenRequestors[1].tokenRequestorId"); //-->12345678903
            out(response, "tokenRequestors[1].name"); //-->Token Requestor 2
            out(response, "tokenRequestors[1].consumerFacingEntityName"); //-->Happy Watch
            out(response, "tokenRequestors[1].imageAssetId"); //-->554dbc44-496a-4896-b41c-dd431e25d5e4
            out(response, "tokenRequestors[1].tokenRequestorType"); //-->WALLET
            out(response, "tokenRequestors[1].walletId"); //-->222
            out(response, "tokenRequestors[1].enabledAccountRanges[0]"); //-->5123456789020000000
            out(response, "tokenRequestors[1].supportedPushMethods[0]"); //-->ANDROID
            out(response, "tokenRequestors[1].supportedPushMethods[1]"); //-->WEB
            out(response, "tokenRequestors[1].supportsMultiplePushedCards"); //-->false
            out(response, "tokenRequestors[1].supportedAccountHolderData[0]"); //-->NAME
            out(response, "tokenRequestors[1].supportedAccountHolderData[1]"); //-->ADDRESS
            out(response, "tokenRequestors[1].supportedAccountHolderData[2]"); //-->EMAIL_ADDRESS
            out(response, "tokenRequestors[1].supportedAccountHolderData[3]"); //-->MOBILE_PHONE_NUMBER
           
            // This sample shows looping through tokenRequestors
            System.out.println("This sample shows looping through tokenRequestors");
            for(Map<String,Object> item : (List<Map<String, Object>>) response.get("tokenRequestors")) {
                out(item, "tokenRequestorId");
                out(item, "name");
                out(item, "consumerFacingEntityName");
                out(item, "imageAssetId");
                out(item, "tokenRequestorType");
                out(item, "enabledAccountRanges");
                out(item, "supportedPushMethods");
                out(item, "supportsMultiplePushedCards");
                out(item, "supportedAccountHolderData");
                out(item, "walletId");
                
            }

            System.out.println("Token Connect Result");
            System.out.println(response.values());
            return response.values().toString();
           
        } catch (ApiException e) {
            err("HttpStatus: "+e.getHttpStatus());
            err("Message: "+e.getMessage());
            err("ReasonCode: "+e.getReasonCode());
            err("Source: "+e.getSource());
            
            return errorResponse(e);
        }
    }

    public String retrieveAsset(String assetId) throws Exception {
        err("retrieveAsset invoked with asset " + assetId);
        
        // Authenticate to MDES Sandbox
        authenticate();
        
        try {
            RequestMap map = new RequestMap();
            map.set("AssetId", assetId); //"dbc55444-496a-4896-b41c-5d5e2dd431e2");
            GetAsset response = GetAsset.read("", map);
     
            out(response, "mediaContents[0].type"); //-->image/png
            out(response, "mediaContents[0].height"); //-->192
            out(response, "mediaContents[0].width"); //-->192
            out(response, "mediaContents[0].data"); //-->iVBORw0KGgoAAAANSUhE
            out(response, "mediaContents[1].type"); //-->image/svg+xml
            out(response, "mediaContents[1].data"); //-->iVBORw0KGgoAAAANSUhE
            
            // This sample shows looping through mediaContents
            System.out.println("This sample shows looping through mediaContents");
            for(Map<String,Object> item : (List<Map<String, Object>>) response.get("mediaContents")) {
                out(item, "type");
                out(item, "height");
                out(item, "width");
                out(item, "data");
            }

            System.out.println("Asset Result");
            System.out.println(response.values());
            return response.values().toString();
            
        } catch (ApiException e) {
          err("HttpStatus: "+e.getHttpStatus());
          err("Message: "+e.getMessage());
          err("ReasonCode: "+e.getReasonCode());
          err("Source: "+e.getSource());
          
          return errorResponse(e);
        }
    }

    
    public String pushCard(String tokenRequestorId) throws Exception {
        err("pushCard invoked with token requestor id " + tokenRequestorId);
        
        // Authenticate to MDES Sandbox
        authenticate();
         
        InputStream publicCertificate = TokenConnect.class.getResourceAsStream("/credential/164401.crt");
        InputStream privateKey = RequestMap.class.getClassLoader().getResourceAsStream("/credential/private.key");
        //if you are given a publicKeyFingerprint
       // ApiConfig.addCryptographyInterceptor(new MDESCryptography(publicCertificate, privateKey, "Insert PublicKeyFingerprint Here"));
        //otherwise
       
        ApiConfig.addCryptographyInterceptor(new MDESCryptography(publicCertificate, null, "243e6992ea467f1cbb9973facfcc3bf17b5cd007"));

        String jsonResult = "";
        
        try {
            RequestMap map = new RequestMap();
            map.set("requestId", "123456");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.cardAccountData.accountNumber", "5123456789012346");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.cardAccountData.expiryMonth", "12");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.cardAccountData.expiryYear", "18");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderName", "Doe/John");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderAddress.line1", "100 First Street");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderAddress.line2", "Apt. 4B");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderAddress.city", "St. Louis");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderAddress.countrySubdivision", "MO");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderAddress.postalCode", "61000");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderAddress.country", "USA");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderEmailAddress", "john.doe@anymail.com");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderMobilePhoneNumber.countryDialInCode", "1");
            map.set("pushFundingAccount.encryptedPayload.encryptedData.accountHolderData.accountHolderMobilePhoneNumber.phoneNumber", "7181234567");
            map.set("tokenRequestorId", "50153069329"); //tokenRequestorId); //"50123456789"
            
            PushAccount response = PushAccount.create(map);
  
            out(response, "responseId"); //-->123456
            out(response, "pushAccountReceipt"); //-->MCC-72672009-c934-482a-990b-8c6e6a2f5e11
            out(response, "availablePushMethods[0].type"); //-->IOS
            out(response, "availablePushMethods[0].uri"); //-->tokenrequestor1://com.tokenrequestor1.pushtoken
            out(response, "availablePushMethods[1].type"); //-->WEB
            out(response, "availablePushMethods[1].uri"); //-->http://www.tokenrequestor1.com/pushtoken
            
            // This sample shows looping through availablePushMethods
            System.out.println("This sample shows looping through availablePushMethods");
            for(Map<String,Object> item : (List<Map<String, Object>>) response.get("availablePushMethods")) {
                out(item, "type");
                out(item, "uri");
            }

            System.out.println("PushCard Result");
            System.out.println(response.values());
            
            jsonResult = "{ \"id\": \"" + response.get("responseId") + "\"" +
                         ", \"pushAccountReceipt\": \"" + response.get("pushAccountReceipt") + "\"" +
                         ", \"availablePushMethods\": " + 
                         "[{\"type\": \"" + response.get("availablePushMethods[0].type") + "\"" +
                         ", \"uri\": \"" + response.get("availablePushMethods[0].uri") + "\"}";
            if (response.get("availablePushMethods[1].type") != null) {
                jsonResult = jsonResult + ", {\"type\": \"" + response.get("availablePushMethods[1].type") + "\"";  
                jsonResult = jsonResult + ", \"uri\": \"" + response.get("availablePushMethods[1].uri") + "\"}";
            }
            jsonResult = jsonResult + "]}";
           
            return jsonResult;

        } catch (ApiException e) {
          err("HttpStatus: "+e.getHttpStatus());
          err("Message: "+e.getMessage());
          err("ReasonCode: "+e.getReasonCode());
          err("Source: "+e.getSource());
          
          return errorResponse(e);
        }
    }
    
    private String errorResponse(ApiException e) {
        return "{ \"status\": " + e.getHttpStatus() + 
                 ", \"message\": " + e.getMessage() +
                 ", \"code\": " + e.getReasonCode() +
                 ", \"source\": " + e.getSource() + "}";
    }
    
    private void out(SmartMap response, String key) {
        System.out.println(key+"-->"+response.get(key));
    }

    private void out(Map<String,Object> map, String key) {
        System.out.println(key+"--->"+map.get(key));
    }

    private void err(String message) {
        System.err.println(message);
    }

    private void authenticate() {
        String consumerKey = "GpkDS4nXYNmCE50P2xU2Wp2I2sDxR_TPXeOWiSGI3293e413!92b632b54f974a56a5125015f6b14b930000000000000000";   // You should copy this from "My Keys" on your project page e.g. UTfbhDCSeNYvJpLL5l028sWL9it739PYh6LU5lZja15xcRpY!fd209e6c579dc9d7be52da93d35ae6b6c167c174690b72fa
        String keyAlias = "MDESToken";   // For production: change this to the key alias you chose when you created your production key
        String keyPassword = "Leelaluck12@";   // For production: change this to the key alias you chose when you created your production key
     
        InputStream is = TokenConnect.class.getResourceAsStream("/credential/MDESToken-sandbox.p12");
       
        ApiConfig.setAuthentication(new OAuthAuthentication(consumerKey, is, keyAlias, keyPassword));   // You only need to set this once
        ApiConfig.setDebug(true);   // Enable http wire logging
        
        // This is needed to change the environment to run the sample code. For production: use ApiConfig.setSandbox(false);
        ApiConfig.setEnvironment(Environment.parse("sandbox"));
    }
}