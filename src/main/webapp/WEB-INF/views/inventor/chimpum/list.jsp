<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.item.list.label.code" path="code" width="10%"/>
	<acme:list-column code="inventor.item.list.label.title" path="title"/>
	<acme:list-column code="inventor.item.list.label.budget" path="budget"/>
	<acme:list-column code="inventor.item.list.label.creationMoment" path="creationMoment"/>
</acme:list>

