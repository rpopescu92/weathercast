package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.urlfetch.*;
import model.WeatherResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.URL;

public class WeatherServlet extends HttpServlet {

    private static final String url = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=2de143494c0b295cca9337e1e96b00e0";

    private final FetchOptions opt = FetchOptions.Builder.doNotValidateCertificate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

       /* URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
        try {
            URL url = new URL("http://www.google.com");
            com.google.appengine.api.urlfetch.HTTPRequest ret = new com.google.appengine.api.urlfetch.HTTPRequest(url);
            //response = fetcher.fetch(url);

            //byte[] content = response.getContent();
            // if redirects are followed, this returns the final URL we are redirected to
           // URL finalUrl = response.getFinalUrl();

            // 200, 404, 500, etc
            int responseCode = ret.getResponseCode();
            //List<HTTPHeader> headers = response.getHeaders();

            /*for (HTTPHeader header : headers) {
                String headerName = header.getName();
                String headerValue = header.getValue();
            }

            resp.setContentType("text/plain");
            resp.getWriter().println("{ \"name\": \"World\" }");
            resp.getWriter().println(responseCode);
            ret.setPayload(request.getPayload());
            ret.getFetchOptions().setDeadline(DEFAULT_TIMEOUT);
        } catch (IOException e) {
            // new URL throws MalformedUrlException, which is impossible for us here
            // return Response.status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }*/


    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cityName = req.getParameter("cityName");
        String urlRequest = String.format(url, cityName);


        URL url = new URL(urlRequest);
        HTTPRequest request = new HTTPRequest(url, HTTPMethod.GET, opt);
        URLFetchService service = URLFetchServiceFactory.getURLFetchService();
        HTTPResponse response = service.fetch(request);
        byte[] content = response.getContent();
        String stringContent = new String(content,"UTF-8");

        WeatherResponse weatherResponse = objectMapper.readValue(stringContent, WeatherResponse.class);
        resp.getWriter().println(weatherResponse.getName());

    }

    private Object getObjectFromBytes(byte[] responseContent) {
        ByteArrayInputStream bis = new ByteArrayInputStream(responseContent);
        ObjectInput in = null;
        Object response = null;
        try {
            in = new ObjectInputStream(bis);
            response = in.readObject();
        } catch (Exception e) {
            return "Error while deserializng";
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return response;
    }

}
