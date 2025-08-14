/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.me.loganalyzerlibrary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author nasaam
 */
public class LogAnalyzerLibClass {

    Scanner reader;
    File file;
    public int numberOfHeadRequest, numberOfGetRequest, numberOfPostRequest;
    public int numberOfPutRequest, numberOfPatchRequest, numberOfTraceRequest;
    public int numberOfDeleteRequest, numberOfConnectRequest, numberOfOptionsRequest;
    public int numberOfOtherRequest;
    public ArrayList<String> distinctRemoteHosts;
    public HashMap<String, Integer> remoteHostVector;
    public HashMap<String, Double[]> responseDataVector;
    public HashMap<Integer,Integer> responseCodeVector;
    public ArrayList<HttpRequestLog> logEntries;

    public LogAnalyzerLibClass(String filename) {
        try {
            file = new File(filename);
            reader = new Scanner(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        numberOfHeadRequest = numberOfGetRequest = numberOfPostRequest = 0;
        numberOfPutRequest = numberOfPatchRequest = numberOfTraceRequest = 0;
        numberOfDeleteRequest = numberOfConnectRequest = numberOfOptionsRequest = 0;
        numberOfOtherRequest=0;
        distinctRemoteHosts = new ArrayList();
        remoteHostVector = new HashMap();
        responseDataVector=new HashMap();
        responseCodeVector=new HashMap();
        logEntries=new ArrayList();
    }

    public void parseLogData() {
        String line = null;
        String referer="";
        String userAgent="";
        if (reader != null) {
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                referer="";
                userAgent="";
                //System.out.println(line);
                String[] logEntry = line.split(" ");
                if(logEntry.length<9)
                    continue;
                if (logEntry[9].contains("-") || logEntry[9].contains("\n") || logEntry.length<9) {
                    continue;
                }
                try {
                    Integer num1 = Integer.parseInt(logEntry[8]);
                    Integer num2 = Integer.parseInt(logEntry[9]);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                recordNumberOfRequestMethod(logEntry[5].substring(1));
                recordRemoteHostNumberOfRequests(logEntry[0].replace('"', ' '));
                recordResponseDataSizeAndAverage(logEntry[0].replace('"', ' '), (double) Integer.valueOf(logEntry[9]));
                recordNumberOfRequestsPerResponseCode(Integer.valueOf(logEntry[8]));
                if (logEntry.length > 10) {
                    referer=logEntry[10];
                    userAgent="";
                    for (int i = 11; i < logEntry.length; i++) {
                        userAgent=userAgent+" "+logEntry[i];
                    }
                }
                HttpRequestLog newLog= new HttpRequestLog();
                newLog.remoteHost=logEntry[0].replace('"', ' ');
                newLog.remoteLogName=logEntry[2];
                newLog.dateTime=logEntry[3].replace('[', ' ');
                newLog.timezone=logEntry[4].replace(']', ' ');
                newLog.method=logEntry[5].substring(1);
                newLog.requestedResource=logEntry[6];
                newLog.protocol=logEntry[7].replace('"', ' ');
                newLog.responseCode=logEntry[8];
                newLog.requestedResourceSize=logEntry[9];
                newLog.referer=referer;
                newLog.userAgent=userAgent;
                logEntries.add(newLog);
            }
        }
    }
    public void recordNumberOfRequestsPerResponseCode(Integer responseCode){
        if(!responseCodeVector.containsKey(responseCode)){
            responseCodeVector.put(responseCode, 1);
        }
        else{
            Integer value=responseCodeVector.get(responseCode);
            responseCodeVector.put(responseCode, value+1);
        }
    }

    public void recordResponseDataSizeAndAverage(String remoteHost, double newSize) {
        if (!responseDataVector.containsKey(remoteHost)) {
            Double[] totalAndAverage = {newSize, newSize, 1.0d};
            responseDataVector.put(remoteHost, totalAndAverage);
        } else {
            Double[] totalAndAverage = responseDataVector.get(remoteHost);
            totalAndAverage[2]++;
            totalAndAverage[0] = totalAndAverage[0] + newSize;
            totalAndAverage[1] = totalAndAverage[0] / totalAndAverage[2];
            responseDataVector.put(remoteHost, totalAndAverage);
        }
    }

    public void recordRemoteHostNumberOfRequests(String remoteHost) {
        if (!distinctRemoteHosts.contains(remoteHost)) {
            distinctRemoteHosts.add(remoteHost);
            remoteHostVector.put(remoteHost, 1);
        } else {
            Integer recordRemoteHostCount = remoteHostVector.get(remoteHost);
            recordRemoteHostCount++;
            remoteHostVector.put(remoteHost, recordRemoteHostCount);
        }
    }

    public void recordNumberOfRequestMethod(String logEntry) {
        if (logEntry.equals("HEAD")) {
            numberOfHeadRequest++;
        } else if (logEntry.equals("GET")) {
            numberOfGetRequest++;
        } else if (logEntry.equals("POST")) {
            numberOfPostRequest++;
        } else if (logEntry.equals("PUT")) {
            numberOfPutRequest++;
        } else if (logEntry.equals("DELETE")) {
            numberOfDeleteRequest++;
        } else if (logEntry.equals("TRACE")) {
            numberOfTraceRequest++;
        } else if (logEntry.equals("PATCH")) {
            numberOfPatchRequest++;
        } else if (logEntry.equals("OPTIONS")) {
            numberOfOptionsRequest++;
        } else if (logEntry.equals("CONNECT")) {
            numberOfConnectRequest++;
        }
        else{
            numberOfOtherRequest++;
            System.out.println("Miscellaneous: "+logEntry);
        }
    }
}
