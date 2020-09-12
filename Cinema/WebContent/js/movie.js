$(document).ready(function() { 
	
	var id = window.location.search.slice(1).split('?')[0].split('=')[1];
	console.log(id);
	var movie;
	//nav
	var navBtn = $('#navBtn');
	var btnLogout;
	var btnAccount;
	var btnUsers;
	var btnTickets;
	var btnMovies;

	//tbl
	var movieInput = $('#movieInput');
	var directorsInput = $('#directorsInput');
	var actorsInput = $('#actorsInput');
	var genresInput = $('#genresInput');
	var durationInput = $('#durationInput');
	var distributorInput = $('#distributorInput');
	var countryInput = $('#countryInput');
	var yearInput = $('#yearInput');
	var overviewInput = $('#overviewInput');
	
	

	makeButtons();
	changeInterface();
	getMovie();
	
	
	
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
				if (data.loggedInUserRole == 'USER'){
					$('#btnDelete').remove();
				}
					
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
		btnUsers = $('<li id = "btnUsers" class="margina"><a class="nav-link" href="#">USERS</a></li>');
    	btnTickets = $('<li id = "btnTickets" class="margina"><a class="nav-link" href="#">TICKETS</a></li>');
		//btnDelete = $('<button type="button" class="btn btn-dark" id="btnDelete">DELETE</button>');
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

	
	
	function getMovie(){
		
		var params = {	'movieid' : id	};
		console.log(params);
		
		$.get('MovieServlet', params, function(data){
			if(data.status == 'success'){
			movie = data.movie;
			console.log(movie);
			
			
			movieInput.val(movie.name);
			directorsInput.val(movie.directors);
			actorsInput.val(movie.actors);
			genresInput.val(movie.genre);
			durationInput.val(movie.duration);
			distributorInput.val(movie.distributor);
			countryInput.val(movie.originCountry);
			yearInput.val(movie.year);
			overviewInput.val(movie.overview);
	
			
			}
		
		});
	
	}
	
	
	$('#deleteSubmit').on('click', function(){
		var params = {	'action' : "delete", 'id' : id	};
		$.post('MovieServlet', params, function(data){
			if (data.status == 'success') {
            	alert("Movie deleted");
                window.location.replace("movies.html");
            }else{
            	alert("Error!");
            }
		
		});
	});


});