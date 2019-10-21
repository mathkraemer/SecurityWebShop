<%@ include file="header.jspf" %>
<c:forEach items="${suggestions}" var="suggestion">
   <option value="${suggestion}" />
</c:forEach>
<%@ include file="footer.jspf" %>