<%--
  ~ Copyright (c) 2012. tAngo
  ~ 	Email : org.java.tango@gmail.com
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts" %>
<%@taglib prefix="c" uri="/jstl" %>
<%@taglib prefix="os" uri="/oscache" %>
<%@taglib prefix="lc" uri="/control" %>
<%
    //******************************************************************************
    //上下文路径
    String ctx = request.getContextPath();
    //公用脚本路径
    String scriptPath = ctx.concat("/WEB-RESOURCES/script");
    //公用样式路径
    String stylePath = ctx.concat("/WEB-RESOURCES/style");
    //framework 工程脚本路径
    String scp = ctx.concat("/resources/script");
    //framework 工程样式路径
    String stp = ctx.concat("/resources/style");
    //******************************************************************************
    //请在下面编辑,命名需有工程名称
%>
<c:set var="ctx" scope="request" value="${pageContext.request.contextPath}"/>
<c:set var="scp" scope="request" value="${ctx}/resources/script"/>
<c:set var="stp" scope="request" value="${ctx}/resources/style"/>
<c:set var="scriptPath" scope="request" value="${ctx}/WEB-RESOURCES/script"/>
<c:set var="stylePath" scope="request" value="${ctx}/WEB-RESOURCES/style"/>



