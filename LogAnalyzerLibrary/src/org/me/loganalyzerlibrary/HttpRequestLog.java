/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.me.loganalyzerlibrary;

/**
 *
 * @author nasaam
 */
public class HttpRequestLog {

    String remoteHost;
    String remoteLogName;
    String dateTime;
    String timezone;
    String method;
    String requestedResource;
    String protocol;
    String responseCode;
    String requestedResourceSize;
    String referer;// for combined log format
    String userAgent;//for combined log format;
    String entryLabel;
    public static final String LABEL_FOR_NORMAL_ACTIVITY = "NORMAL";
    public static final String LABEL_FOR_ABNORMAL_ACTIVITY = "ABNORMAL";

    public String[] getLogEntry() {
        String[] data = {remoteHost, dateTime, method, requestedResource, protocol, responseCode, requestedResourceSize, referer, userAgent};
        return data;
    }

}
