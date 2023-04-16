package org.thb.webshop.service;

public class TestService implements Runnable {

    private String group = "unknown";

    public TestService(String testgroup){
        this.group = testgroup;
    }

    public void run(){
        
        int len;

        if (group == null){
            len = group.length();
        } else {
            len = 0;
        }

    }
    
}
