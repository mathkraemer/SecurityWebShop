<%@ include file="header.jspf" %>
	<div id="content">
	<form action="sell" method="post" enctype="multipart/form-data">
		<fieldset>
			<legend>Anbieten</legend>
			<table>
				<tbody>
					<tr>
						<th>
							<label for="title">Titel:</label>
						</th>
						<td>
							<input 
								type="text"
								name="title"
								size="40"
								title="Bitte einen Titel für den Artikel angeben."
								placeholder="Titel eingeben"
								required="required">
						</td>
					</tr>
					<tr>
						<th>
							<label for="description">Beschreibung:</label>
						</th>
						<td>
							<textarea name="description" rows="10" cols="100" maxlength="1000">
							</textarea>
						</td>
					</tr>
					<tr>
						<th>
							<label for="price">Preis:</label>
						</th>
						<td>
							<input
								type="text"
								name="price"
								size="40"
								title="Bitte einen Preis für den Artikel angeben."
								placeholder="Preis eingeben"
								required="required">
						</td>
					</tr>
					<tr>
						<th>
							<label for="picture">Foto:</label>
						</th>
						<td>
							<input type="file" name="picture">
						</td>
					</tr>
					<tr>
						<td/>
						<td>
							<input type="submit">
							<input type="reset">
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>	
	</form>
	</div>
<%@ include file="footer.jspf" %>