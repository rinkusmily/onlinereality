package com.irshad.aronlinevideo.SampleApplication.vuforia;

import android.util.Log;

import com.irshad.aronlinevideo.SampleApplication.UTILS;

import android.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;

public class PostNewTarget implements TargetStatusListener {
    //Server Keys

    private String url = "https://vws.vuforia.com";
    private String targetName = "[ target name ]";
    private String image;
    private String targetId;
    private TargetStatusPoller targetStatusPoller;

    private final float pollingIntervalMinutes = 60;//poll at 1-hour interval


    public PostNewTarget(String imagen, String targetName) {
        this.image = imagen;
        this.targetName = targetName;
        this.targetId = new String();
    }

    public String getTargetId() {
        return this.targetId;
    }

    private String postTarget() throws URISyntaxException, ClientProtocolException, IOException, JSONException {
        final String[] uniqueTargetId = {""};
        Thread  thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here
                    HttpPost postRequest = new HttpPost();
                    HttpClient client = new DefaultHttpClient();
                    postRequest.setURI(new URI(url + "/targets"));
                    JSONObject requestBody = new JSONObject();

                    setRequestBody(requestBody);
                    postRequest.setEntity(new StringEntity(requestBody.toString()));
                    setHeaders(postRequest); // Must be done after setting the body

                    HttpResponse response = client.execute(postRequest);
                    String responseBody = EntityUtils.toString(response.getEntity());
                    System.out.println(responseBody);

                    JSONObject jobj = new JSONObject(responseBody);

                     uniqueTargetId[0] = jobj.has("target_id") ? jobj.getString("target_id") : "";
                    System.out.println("\nCreated target with id: " + uniqueTargetId[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


        return uniqueTargetId[0];
    }

    private void setRequestBody(JSONObject requestBody) throws IOException, JSONException {
            /*File imageFile = new File(imageLocation);
			if(!imageFile.exists()) {
				System.out.println("File location does not exist!");
				System.exit(1);
			}
			byte[] image = FileUtils.readFileToByteArray(imageFile);
			*/
        Log.e("image","-->"+image);
        requestBody.put("name", targetName); // Mandatory
        requestBody.put("width", 320.0); // Mandatory
        requestBody.put("image", image);//Base64.encodeBase64String(image)); // Mandatory
        requestBody.put("active_flag", 1); // Optional
        requestBody.put("application_metadata", Base64.encodeToString("Vuforia test metadata".getBytes(),Base64.DEFAULT)); // Optional
    }

    private void setHeaders(HttpUriRequest request) {
        SignatureBuilder sb = new SignatureBuilder();
        request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
        request.setHeader(new BasicHeader("Content-Type", "application/json"));
        request.setHeader("Authorization", "VWS " + UTILS.accessKey + ":" + sb.tmsSignature(request, UTILS.secretKey));
    }

    /**
     * Posts a new target to the Cloud database;
     * then starts a periodic polling until 'status' of created target is reported as 'success'.
     */
    public void postTargetThenPollStatus() {
        String createdTargetId = "";
        try {
            createdTargetId = postTarget();
        } catch (URISyntaxException | IOException | JSONException e) {
            e.printStackTrace();
            return;
        }

        // Poll the target status until the 'status' is 'success'
        // The TargetState will be passed to the OnTargetStatusUpdate callback
        if (createdTargetId != null && !createdTargetId.isEmpty()) {
            targetId = createdTargetId;
            targetStatusPoller = new TargetStatusPoller(pollingIntervalMinutes, createdTargetId, this);
            targetStatusPoller.startPolling();
        }
    }

    // Called with each update of the target status received by the TargetStatusPoller
    @Override
    public void OnTargetStatusUpdate(TargetState target_state) {
        if (target_state.hasState) {

            String status = target_state.getStatus();

            System.out.println("Target status is: " + (status != null ? status : "unknown"));

            if (target_state.getActiveFlag() == true && "success".equalsIgnoreCase(status)) {

                targetStatusPoller.stopPolling();

                System.out.println("Target is now in 'success' status");
            }
        }
    }


}
