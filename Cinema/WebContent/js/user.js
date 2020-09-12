$(document).ready(function() { 
	
	var id = window.location.search.slice(1).split('?')[0].split('=')[1];
	console.log(id);
	var user;
	//nav
	var navBtn = $('#navBtn');
	var btnLogout;
	var btnAccount;
	var btnUsers;
	var btnTickets;
	var btnMovies;

	//tbl
	var usernameInput = $('#usernameInput');
	var passwordInput = $('#passwordInput');
	var roleSelect = $('#roleSelect');
	var dateInput = $('#dateInput');
	
	

	makeButtons();
	changeInterface();
	getUser();
	
	
	
	function changeInterface(){
		$.get('UserServlet', {'action' : 'loggedInUserRole'}, function(data) {
			console.log(data.status);
			if (data.status == 'unauthenticated') {
				$('#btnLogout').remove();
				$('#btnAccount').remove();
				$('#btnUsers').remove();
				$('#btnTickets').remove();
				$('#btnDelete').remove();
				$('#btnLogin').show();
				$('#btnRegister').show();
				
			}
			if (data.status == 'success') {
				$('#btnRegister').hide();
				$('#btnLogin').hide();
				console.log(data.loggedInUserRole);
				
				if (data.loggedInUserRole == 'ADMIN') {
					navBtn.append(btnUsers);
					$('#btnDelete').append();
					
					
				}
				if (data.loggedInUserRole == 'USER')
					$('#btnDelete').remove();
					
				navBtn.append(btnAccount);
				navBtn.append(btnTickets);
				navBtn.append(btnLogout);
				return;
			}
		})
	};
	
	
	function makeButtons() {
		btnLogout = $('<li id="btnLogout"><a class="nav-link" href="#">LOGOUT</a></li>').on('click', function(){
			$.get('LogoutServlet', function(data) {
				console.log(data);
				if(data.status == 'unauthenticated'){
					changeInterface();
					console.log('Logged out');
					return;
				}else {
					console.log('Error');
				}
			});
			$('input').val('');
			location.reload();
			return false;
		});
		
		
		btnAccount = $('<li id="btnAccount"><a class="nav-link" href="">ACCOUNT</a></li>').on('click', function(){
			let param = { 'action' : "loggedInUserId"};
			$.get('UserServlet', param, function(data){
				if(data == 'success') {
						let url = 'user.html?id=' + data.loggedInUserId;
						window.location.replace(url);
				}
			});
		});
		btnUsers = $('<li id = "btnUsers" class="margina"><a class="nav-link" href="users.html">USERS</a></li>');
    	btnTickets = $('<li id = "btnTickets" class="margina"><a class="nav-link" href="tickets.html">TICKETS</a></li>');
	}
	
	
	
	
	$('#loginSubmit').on('click', function(event) { 
		var userNameInput = $('#userNameInput');
		var passwordInput = $('#passwordInput');
		var userName = userNameInput.val();
		var password = passwordInput.val();

		var params = {
			'userName': userName, 
			'password': password
		}
		console.log(params);
		
		$.post('LoginServlet', params, function(data) {
			console.log(data);

			if (data.status == 'failure') {
				userNameInput.val('');
				passwordInput.val('');
				alert("Username or password are incorrect.");
				return;
			}
			if (data.status == 'success') {
				window.location.replace('projections.html');
			}
		});
		
		event.preventDefault();
		return false;
	});
	
	
	
	$('#registrationSubmit').on('click', function(event) {
		var userName = $('#registrationInputUsername');
		var password = $('#registrationInputPassword');
		var repeatPassword = $('#registrationInputRepeatPassword');
		
		if(userName.val() == "" || password.val() == "" || repeatPassword.val() == ""){
			alert("All fields must be filled out.");
			return;
		}
		
		if(password.val() != repeatPassword.val()){
			alert("Passwords do not match.");
			return;
		}
		
		$.ajaxSetup({async: false});
		
		var params = {
			'userName': userName.val(), 
			'password': password.val(),
			'repeatPassword' : repeatPassword.val()
		}
		
		$.post('RegistrationServlet', params, function(data) {
			console.log(data);
			
			if(data.status == 'success'){
				alert('Registration successfull.');
				window.location.replace('projections.html');
			}else{
				alert(data.message);
			}
		});
		
	});
	
	
	function getUser(){
		
		$.get('UserServlet', {'action' : 'loggedInUserId'}, function(data){
			
		});
		
		
		
		
		/*var params = {	
				'action' : "getUser",
				'userId' : id,
		};
		console.log(params);
		
		$.get('UserServlet', params, function(data) {
			if(data.status == 'success') {
				user = data.user;
				console.log(user);
								
				usernameInput.val(user.username).trigger("change");
				dateInput.val(dateFormat(new Date(projection.datetime))).trigger("change");
				passwordInput.val(user.password).trigger("change");
				roleSelect.val(user.role).trigger("change");	
				
			}
		});*/
	
	}
	
	$('#deleteSubmit').on('click', function(){
		var params = {	'action' : "delete", 'id' : id	};
		$.post('UserServlet', params, function(data){
			if (data.status == 'success') {
            	alert("User deleted");
                window.location.replace("users.html");
            }else{
            	alert("Error!");
            }
		
		});
	});
	
	
	function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + "T" + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2);
     
        return dateString;
    }
	
	
	
	
	
});