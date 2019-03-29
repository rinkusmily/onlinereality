package com.irshad.aronlinevideo.SampleApplication.vuforia;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import static com.irshad.aronlinevideo.SampleApplication.UTILS.accessKey;
import static com.irshad.aronlinevideo.SampleApplication.UTILS.secretKey;

/**
 * Created by irshad on 24-01-2018.
 */

public class SummeryDetails {


    private String url = "https://vws.vuforia.com";

    private void getSummary(String target_id) throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet getRequest = new HttpGet();
        HttpClient client = new DefaultHttpClient();
        getRequest.setURI(new URI(url + "/summary/"+target_id));
        setHeaders(getRequest);

        HttpResponse response = client.execute(getRequest);
        System.out.println(EntityUtils.toString(response.getEntity()));
        Log.e("response",""+EntityUtils.toString(response.getEntity()));
    }

    private void setHeaders(HttpUriRequest request) {
        SignatureBuilder sb = new SignatureBuilder();
        request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
        request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
    }

    public static void main(final String target_id) throws URISyntaxException, ClientProtocolException, IOException {
        final SummeryDetails g = new SummeryDetails();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    g.getSummary(target_id);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}

