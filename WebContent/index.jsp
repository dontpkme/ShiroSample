
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="net.dpkm.shiro.Quickstart" %>

welcome, You are <%=SecurityUtils.getSubject().getPrincipal() %><br/><br/>

<% Quickstart.main(null); %>
you can check your console to see the log.<br/><br/>

<a href="./warrior.jsp">Goto Warrior Guild</a><br/><br/>

<a href="./boxing.jsp">Goto Boxing Arena</a><br/><br/>

<a href="./admin/admin.html">Goto GM's room</a><br/><br/>

<a href="./logout.jsp">Logout through Shiro Filter</a>