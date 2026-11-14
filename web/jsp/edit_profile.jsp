<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Edit Profile</title></head>
<body>
<h2>Edit Profile</h2>
<%
    String username = (String) request.getAttribute("username");
    String address  = (String) request.getAttribute("address");
    String bio      = (String) request.getAttribute("bio");
    String message  = (String) request.getAttribute("message");
    if (address == null) address = "";
    if (bio == null) bio = "";
    if (message != null) {
%>
<p style="color:green"><b><%= message %></b></p>
<%  } %>
<form method="post" action="<%= request.getContextPath() %>/profile">
    Username: <input type="text" value="<%= username %>" readonly/><br/><br/>
    Address: <input type="text" name="address" value="<%= address %>" size="40"/><br/><br/>
    Bio: <textarea name="bio" rows="3" cols="40"><%= bio %></textarea><br/><br/>
    <input type="submit" value="Save Changes"/>
</form>

<form method="post" action="<%= request.getContextPath() %>/profile">
    <input type="hidden" name="action" value="delete"/>
    <input type="submit" value="Delete Profile"/>
</form>
</body>
</html>
