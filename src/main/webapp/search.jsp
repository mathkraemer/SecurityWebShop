<%@ include file="header.jspf" %>
<script src="resources/js/jquery-3.3.1.min.js"></script>
<script>
	$(document).on('keyup', '.inputBox',function(){
			$.ajax({
				url : 'autoComplete',
				data : {'search' : $(this).val()}
			}).done(function(result, jqXHR){
				$('#suggestions').html(result);
			});
	});

	$(document).on('click', '.shoppingCartButton', function(){
		var item = $('#itemId').val();
		var div=$('#shoppingCart').html();
		$.ajax({
			type : 'POST',
			url : 'buy',
			data : {'item' :item}
		}).done(function(result, jqXHR){
			$('#shoppingCart').load(location.href.concat('.jsp')+' #shoppingCart>*','');
		});
	});
	
</script>
	<div id="content">
	<article>
		<section>
			<form action="search" method="post">
				<fieldset>
					<legend>Suchen</legend>
					<table>
						<tbody>
							<tr>
								<th>
									<label for="search">Suche:</label>
								</th>
								<td>
									<input 
										class="inputBox"
										type="text"
										name="search"
										list="suggestions"
										size="40"
										title="Suchtext"
										placeholder="Suchebegriff eingeben"
										autocomplete="off">

									<datalist id="suggestions">
										<option value="Suche....">
										<c:forEach var="suggestion" items="${suggestions}">
   											<option value="${suggestion}" />
										</c:forEach>
									</datalist>

								</td>
								<td>
									<input type="submit">
									<input type="reset">
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</form>
		</section>
	</article>
	<c:forEach var="item" items="${items}">
		<article>
			<section>
				<form>
					<fieldset>
						<legend>ID: ${item.id}</legend>
						<h2>${item.title}</h2>
						<p>${item.description}</p>
						<aside>
							<img src="picture?id=${item.id}">
						</aside>
						<p>${item.price} €</p>
						<c:if test="${not empty customer}">
							<c:choose>
								<c:when test="${empty item.sold}">
									<input id="itemId" type="text" name="itemId"  value="${item.id}">
									<input type="button" class="shoppingCartButton" value="Zum Warenkorb hinzufügen">
								</c:when>
								<c:otherwise>
									<b>Verkauft am ${item.sold}</b>
									<b>an ${item.buyer_id}</b>
								</c:otherwise>
							</c:choose>
						</c:if>
					</fieldset>
				</form>
			</section>
		</article>
	</c:forEach>
	</div>
	
<%@ include file="footer.jspf" %>