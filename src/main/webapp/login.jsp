<%@ include file="header.jspf" %>
<div id="content">
	<form action="login" method="post">
		<fieldset>
			<legend>Einloggen</legend>
			<table>
				<tbody>
					<tr>
						<th>
							<label for="email">E-Mail:</label>
						</th>
						<td>
							<input
								type="email"
								name="email"
								size="30"
								title="E-Mail"
								placeholder="Muster.Email@Beispiel.de"
								required="required">
						</td>
					</tr>
					<tr>
						<th>
							<label for="password">Passwort:</label>
						</th>
						<td>
							<input 
								type="password"
								name="password"
								size="30"
								title="Passwort"
								placeholder="Passwort eingeben"
								required="required">
						</td>
					</tr>
					<tr>
						<td/>
						<td>
							<input type="submit"/>
							<input type="reset"/>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<c:if test="${not empty loginMsg}">
								<b style="color: tomato;">${loginMsg}</b>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
	</form>
</div>	
<%@ include file="footer.jspf" %>