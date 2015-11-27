<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.WeatherEntity" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <script src="./jquery-1.11.3.min.js"></script>
    <script src="./bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="./bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="./bootstrap.min.css.map" />
</head>

<body>
    <div style="margin:10px;" class="row">
        <div class="col-md-6">
            <form  action="/weather" method="post" action="servlet">
              <div class="form-group">
                <label for="exampleInputEmail1">City name</label>
                <input type="text" class="form-control" name="cityName" id="cityName" value="${fn:escapeXml(cityName)}" placeholder="City name">
              </div>
              <button type="submit" class="btn btn-default">Show weather</button>
            </form>

        </div>
    </div>

    <div style="margin:10px;" class="row">
        <div class="col-md-6">
            <table class="table table-striped">
              <thead>
                <th>City Name</th>
                <th>Temperature</th>
              </thead>


            <tbody>
                    <%
                        // Run an ancestor query to ensure we see the most up-to-date
                        // view of the Greetings belonging to the selected Guestbook.
                          List<WeatherEntity> weatherData = ObjectifyService.ofy()
                              .load()
                              .type(WeatherEntity.class)
                              .list();
                    %>

                        <%
                                for (WeatherEntity weatherEntity : weatherData) {

                         %>
                        <tr>
                        <%
                                    pageContext.setAttribute("name_content", weatherEntity.getCityName());
                                    pageContext.setAttribute("temperature_content",weatherEntity.getTemperature());

                        %>

                            <td>${fn:escapeXml(name_content)}</td>
                            <td>${fn:escapeXml(temperature_content)} Celsius</td>

                            </tr>
                        <% } %>

            </tbody>

            </table>
        </div>

    </div>
<p></p>
</body>
</html>