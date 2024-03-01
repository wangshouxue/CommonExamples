// IMyAidlInterface.aidl
package com.example.myaidldemo2;

// Declare any non-default types here with import statements

interface IMyAidlInterface {//建完需要重新rebuild，否则使用时找不到
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void sendMsg(String msg);
    String getMsg();
}