<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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


</body>
</html>