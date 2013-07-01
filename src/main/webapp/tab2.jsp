<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
	<script src="js/jquery-latest.min.js" type="text/javascript"> </script>
	<script src="js/jquery.subsession.js" type="text/javascript"> </script> 
</head>
<body>
<h2>Tab2</h2>
<%
	// User define value, to share with new tab...
	if (session.getAttribute("custom")==null) {
		session.setAttribute("custom", request.getParameter("custom"));	
	}

	// When this tab was open...
	if (session.getAttribute("tabopened")==null) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		session.setAttribute("tabopened", sdf.format(Calendar.getInstance().getTime()));
	}
%>
<ul>
	<li><a href="tab1.jsp">tab1</a></li>
	<li><a href="tab2.jsp">tab2</a></li>
	<li><a href="tab3.jsp">tab3</a></li>
</ul>
<h3>Subsession: 
<%Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (Cookie cookie : cookies) {
		if (cookie.getName().equals("subsession")) {%><%=cookie.getValue()%></h3><h3>Subsession breadcrumb: <%}
		if (cookie.getName().equals("subsession_breadcrumb")) {%><%=cookie.getValue()%></h3><%}
	}
}%>
<h3>Time when tabs was open: <%=session.getAttribute("tabopened")%></h3>

<form action="#">
	<input name="custom" name="custom" value="<%=session.getAttribute("custom")%>" />
	<input type="submit" />
</form>

</body>
</html>
