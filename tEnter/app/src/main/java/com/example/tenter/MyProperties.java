package com.example.tenter;

public class MyProperties {

    private static MyProperties mInstance= null;

    public String cachetopic;
    public String selectedtopic;
    public String fbasekey="asd";

    protected MyProperties(){}

    public static synchronized MyProperties getInstance() {
        if(null == mInstance){
            mInstance = new MyProperties();
        }
        return mInstance;
    }

}
