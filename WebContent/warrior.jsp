
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>	

<shiro:lacksRole name="warrior">
Guild Keeper: "You are not a warrior, Get out."
</shiro:lacksRole>

<shiro:hasRole name="warrior">
Guild Keeper: "You are a warrior, welcome!"
</shiro:hasRole>

<br/><br/><a href="./">Go Back</a>