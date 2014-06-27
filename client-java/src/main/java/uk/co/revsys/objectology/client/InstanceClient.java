package uk.co.revsys.objectology.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import uk.co.revsys.utils.http.HttpClient;
import uk.co.revsys.utils.http.HttpRequest;
import uk.co.revsys.utils.http.HttpResponse;

public class InstanceClient {

    private HttpClient httpClient;
    private String baseUrl;

    public InstanceClient(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }
    
    public String create(String source) throws IOException{
        HttpRequest request = HttpRequest.POST(baseUrl + "/create", "application/json", new ByteArrayInputStream(source.getBytes()));
        HttpResponse response = httpClient.invoke(request);
        if(response.getStatusCode()!=200){
            InputStream errorStream = response.getInputStream();
            if(errorStream!=null){
                String errorMessage = IOUtils.toString(errorStream);
                throw new IOException("Server returned status " + response.getStatusCode() + " - " + errorMessage);
            }
            throw new IOException("Server returned status " + response.getStatusCode());
        }
        return IOUtils.toString(response.getInputStream());
    }
    
    public String update(String instanceType, String instanceId, String source) throws IOException{
        HttpRequest request = HttpRequest.POST(baseUrl + "/"+instanceType+"/"+instanceId, "application/json", new ByteArrayInputStream(source.getBytes()));
        HttpResponse response = httpClient.invoke(request);
        if(response.getStatusCode()!=200){
            InputStream errorStream = response.getInputStream();
            if(errorStream!=null){
                String errorMessage = IOUtils.toString(errorStream);
                throw new IOException("Server returned status " + response.getStatusCode() + " - " + errorMessage);
            }
            throw new IOException("Server returned status " + response.getStatusCode());
        }
        return IOUtils.toString(response.getInputStream());
    }
    
    public String retrieve(String instanceType, String instanceId, String view) throws IOException{
        HttpRequest request = HttpRequest.GET(baseUrl + "/"+instanceType+"/"+instanceId+"?view="+view);
        HttpResponse response = httpClient.invoke(request);
        if(response.getStatusCode()!=200){
            InputStream errorStream = response.getInputStream();
            if(errorStream!=null){
                String errorMessage = IOUtils.toString(errorStream);
                throw new IOException("Server returned status " + response.getStatusCode() + " - " + errorMessage);
            }
            throw new IOException("Server returned status " + response.getStatusCode());
        }
        return IOUtils.toString(response.getInputStream());
    }
    


}
