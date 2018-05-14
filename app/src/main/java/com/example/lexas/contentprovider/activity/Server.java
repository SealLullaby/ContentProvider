package com.example.lexas.contentprovider.activity;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.util.Map;
/**
 * Created by Lexas on 2018/5/13.
 */
public class Server extends  NanoHTTPD{
    public Server() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }
}
