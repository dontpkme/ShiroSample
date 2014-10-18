
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="org.apache.shiro.subject.Subject" %>
<%@ page import="org.apache.shiro.authc.UsernamePasswordToken" %>
<%
String username = request.getParameter("username");
String password = request.getParameter("password");

try {
	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	Subject currentUser = SecurityUtils.getSubject();
	currentUser.login(token);

	// login successfully!
	
} catch (Exception e) {
	// login failed!
}
%>
<script> location.href="/shiro"; </script>