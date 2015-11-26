package myapp;

import java.io.IOException;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class DemoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
        try {
            URL url = new URL("http://www.google.com");
            HTTPResponse response = fetcher.fetch(url);

            byte[] content = response.getContent();
            // if redirects are followed, this returns the final URL we are redirected to
            URL finalUrl = response.getFinalUrl();

            // 200, 404, 500, etc
            int responseCode = response.getResponseCode();
            List<HTTPHeader> headers = response.getHeaders();

            for (HTTPHeader header : headers) {
                String headerName = header.getName();
                String headerValue = header.getValue();
            }

            resp.setContentType("text/plain");
            resp.getWriter().println("{ \"name\": \"World\" }");
            resp.getWriter().println(responseCode);
        } catch (IOException e) {
            // new URL throws MalformedUrlException, which is impossible for us here
            // return Response.status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }
}
