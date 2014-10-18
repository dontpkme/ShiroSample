
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>	

<shiro:hasPermission name="melee:punch">
Gate Keeper: "You know how to punch. You must be a warrior or a fighter!"
</shiro:hasPermission>

<shiro:lacksPermission name="melee:punch">
Gate Keeper: "You don't know how to punch. Get out!"
</shiro:lacksPermission>

<br/><br/><a href="./">Go Back</a>