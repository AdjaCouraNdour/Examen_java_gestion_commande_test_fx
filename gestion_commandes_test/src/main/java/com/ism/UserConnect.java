package com.ism;

import com.ism.data.entities.User;

public class UserConnect {
  
    private static User userConnect;
    
  
    public static User getUserConnect() {
      return userConnect;
    }
  
    public static void setUserConnect(User userConnect) {
      UserConnect.userConnect = userConnect;
    }
  }
