package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.urlfetch.*;
import com.googlecode.objectify.ObjectifyService;
import entities.WeatherEntity;
import model.WeatherResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Date;

public class WeatherServlet extends HttpServlet {

    private static final String url = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=2de143494c0b295cca9337e1e96b00e0";

    private final FetchOptions opt = FetchOptions.Builder.doNotValidateCertificate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String cityName = req.getParameter("cityName");
        String urlRequest = String.format(url, cityName);


        URL url = new URL(urlRequest);
        HTTPRequest request = new HTTPRequest(url, HTTPMethod.GET, opt);
        URLFetchService service = URLFetchServiceFactory.getURLFetchService();
        HTTPResponse response = service.fetch(request);
        byte[] content = response.getContent();
        String stringContent = new String(content,"UTF-8");

        WeatherResponse weatherResponse = objectMapper.readValue(stringContent, WeatherResponse.class);
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCityName(weatherResponse.getName());
        weatherEntity.setCountry(weatherResponse.getSys().getCountry());
        weatherEntity.setTemperature(weatherResponse.getMain().getTemp()-273);
        weatherEntity.setDate(new Date());
        weatherEntity.setMood(weatherResponse.getWeather().get(0).getMain());
        weatherEntity.setId(weatherResponse.getId());

        saveData(weatherEntity);

        req.setAttribute("cityWeather", weatherEntity);
        req.getRequestDispatcher("/weather.jsp").forward(req, resp);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
        ObjectifyService.ofy().clear();
        req.getRequestDispatcher("/weather.jsp").forward(req, resp);
    }

    private void saveData(WeatherEntity entity){
        ObjectifyService.ofy().save().entity(entity).now();


    }

}
