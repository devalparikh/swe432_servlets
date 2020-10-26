<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
  <script>
    var servletURL = window.location.origin;
  </script>
<title>Java Servlets</title>
</head>
<%@ page import="java.util.Date" %>
<body>
<h2>Hello this is our page for assignments regarding Java Servlets</h2>
<strong>Current Time is</strong>: <%=new Date() %>
<p>Assignments</p>

<button onclick="window.location.assign(servletURL+'/assignment5');">Assignment 5 | Logical Predicates</button>
<button onclick="window.location.assign(servletURL+'/assignment7');">Assignment 7</button>
</body>
</html>
