<%@ include file="header.jspf" %>

	<div id="content">
		<%
			if(application.getAttribute("counter") == null){
				application.setAttribute("counter", Long.valueOf(0));
			}
			
			Long count = (Long) application.getAttribute("counter");
			long n = count.longValue()+1;
			application.setAttribute("counter", Long.valueOf(n));
			
			out.println("<h3>Aufrufe seit dem Deployment: "+n+"</h3>");
		%>
		
		<fieldset>
			<legend>Mitteilungen</legend>
			<p>Eingeloggt: <c:out value="${customer.foreName}" default="-"/></p>
			<p>Nachrichten: <c:out value="${msg}" default="-"/></p>
		</fieldset>
		
		<script>
			function getJSessionId(){
			    var jsId = document.cookie.match(/JSESSIONID=[^;]+/);
			    if(jsId != null) {
			        if (jsId instanceof Array)
			            jsId = jsId[0].substring(11);
			        else
			            jsId = jsId.substring(11);
			    }
			    console.log(jsId);
			    return jsId;
			}
		</script>

	</div>

<%@ include file="footer.jspf" %>