<%@ include file="header.jspf" %>

<div id="content">
	
	<form action="register" method="post">
		<fieldset>
			<legend>Registrieren</legend>
			<table>
				<tbody>
					<tr>
						<th>
							<label for="forename">Vorname:</label>
						</th>
						<td>
							<input 
								type="text"
								name="forename"
								size="50"
								title="Vorname"
								placeholder="Vorname eingeben."
								required="required">
						</td>	
					</tr>
					<tr>
						<th>
							<label id="surename">Nachname:</label>
						</th>
						<td>
							<input 
								type="text"
								name="surename"
								size="50"
								title="Nachname"
								placeholder="Nachname eingeben."
								required="required">
						</td>
					</tr>
					<tr>
						<th>
							<label for="zip">PLZ:</label>
						</th>
						<td>
							<input 
								type="text"
								name="zip"
								size="10"
								title="Postleitzahl"
								placeholder="Postleitzahl eingeben."
								required="required">	
						</td>
						<th>
							<label for="place">Ort:</label>
						</th>
						<td>
							<input 
								type="text"
								name="place"
								size="40"
								title="Ort"
								placeholder="Ort eingeben."
								required="required">
						</td>
					</tr>
					<tr>
						<th>
							<label for="street">Straße:</label>
						</th>
						<td>
							<input 
								type="text"
								name="street"
								size="40"
								title="Straße"
								placeholder="Straße eingeben."
								required="required">
						</td>
						<th>
							<label for="houseNumber">Hausnummer:</label>
						</th>
						<td>
							<input 
								type="text"
								name="houseNumber"
								size="10"
								title="Hausnummer"
								placeholder="Hausnummer eingeben."
								required="required">
						</td>
					</tr>
					<tr>
						<th>
							<label for="email">E-Mail:</label>
						</th>
						<td>
							<input
								type="email"
								name="email"
								size="50"
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
								size="50"
								title="Passwort"
								placeholder="Passwort eingeben."
								required="required">
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit"/>
						</td>
						<td>
							<input type="reset"/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
	</form>
</div>	
<%@ include file="footer.jspf" %>