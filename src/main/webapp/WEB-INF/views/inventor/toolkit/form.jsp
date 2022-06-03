
  
<%@page language="java"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="inventor.toolkit.form.label.code" path="code"/>
	<acme:input-textbox code="inventor.toolkit.form.label.title" path="title"/>
	<acme:input-textbox code="inventor.toolkit.form.label.descripcion" path="descripcion"/>
	<acme:input-textbox code="inventor.toolkit.form.label.assemblyNotes" path="assemblyNotes"/>
	<acme:input-url code="inventor.toolkit.form.label.link" path="link"/>
	
	<jstl:choose>
	
		<jstl:when test="${command == 'show' && status == 'PUBLISHED'}">
			<acme:input-money code="inventor.toolkit.form.label.retailPrice" path="retailPrice" readonly="true"/>
			<acme:input-textbox code="inventor.toolkit.form.label.status" path="status" readonly="true"/>
			<acme:button code="inventor.toolkit.form.buttom.items" action="/inventor/quantity/list-by-toolkit?id=${id}"/>
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && status == 'NON_PUBLISHED'}">
			<acme:input-money code="inventor.toolkit.form.label.retailPrice" path="retailPrice" readonly="true"/>
			<acme:input-textbox code="inventor.toolkit.form.label.status" path="status" readonly="true"/>	
			<acme:button code="inventor.toolkit.form.buttom.items" action="/inventor/quantity/list-by-toolkit?id=${id}"/>
			<acme:submit code="inventor.toolkit.form.button.update" action="/inventor/toolkit/update"/>
			<acme:submit code="inventor.toolkit.form.button.delete" action="/inventor/toolkit/delete"/>
			<acme:submit code="inventor.toolkit.form.button.publish" action="/inventor/toolkit/publish"/>
			
		</jstl:when>
		
		
		<jstl:when test = "${command == 'create' }">
			<acme:submit code = "inventor.toolkit.form.button.create" action = "/inventor/toolkit/create"/>
		</jstl:when>
	
	</jstl:choose>


</acme:form>