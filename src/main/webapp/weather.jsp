<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.WeatherEntity" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>

</head>

<body>

<form action="/weather" method="post" action="servlet">
    <div><input type="text" name="cityName" value="${fn:escapeXml(cityName)}"/></div>
    <div><input type="submit" value="Show weather"/></div>
</form>

<div id="result">
    <pre>
        ${requestScope.cityWeather}
    </pre>
    <%


        // Run an ancestor query to ensure we see the most up-to-date
        // view of the Greetings belonging to the selected Guestbook.
          List<WeatherEntity> weatherData = ObjectifyService.ofy()
              .load()
              .type(WeatherEntity.class)
              .list();

        if (weatherData.isEmpty()) {
    %>
    <p>No data recorded.</p>
    <%
        } else {
    %>
    <p>Data recorded. </p>
    <%
            for (WeatherEntity weatherEntity : weatherData) {
                pageContext.setAttribute("weather_content", weatherEntity.getCityName()+":"+weatherEntity.getTemperature());

    %>
    <p><b>${fn:escapeXml(weather_content)}</b></p>
    <%
            }
        }
    %>
</div>

</body>
</html>