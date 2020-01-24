$(document).ready(function() { 
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');

	$('#loginSubmit').on('click', function(event) { 
		var userName = userNameInput.val();
		var password = passwordInput.val();
		console.log('userName: ' + userName);
		console.log('passwrod: ' + password);		

		var params = {
			'userName': userName, 
			'password': password
		}
		$.post('LoginServlet', params, function(data) {
			console.log('stigao odgovor!')
			console.log(data);

			if (data.status == 'failure') {
				userNameInput.val('');
				passwordInput.val('');

				return;
			}
			if (data.status == 'success') {
				window.location.replace('WebShop.html');
			}
		});
		console.log('poslat zahtev!')

		event.preventDefault();
		return false;
	});
});