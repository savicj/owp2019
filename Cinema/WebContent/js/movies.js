$(document).ready(function() { 
	
	
	//nav
	var navBtn = $('#navBtn');
	var btnLogout;
	var btnAccount;
	var btnUsers;
	var btnTickets;
	var btnMovies;

	//tbl
	var tblMovies = $('#tblMovies');
	var movieInput = $('#movieInput');
	var genreInput = $('#genreInput');
	var minDurationInput = $('#minDurationInput');
	var maxDurationInput = $('#maxDurationInput');
	var distributorInput = $('#distributorInput');
	var countryInput = $('#countryInput');
	var fromYearInput = $('#fromYearInput');
	var toYearInput = $('#toYearInput');
	
	var btnAdd;
	

	changeInterface();
	makeButtons();
	getMovies();
	
	
	function changeInterface(){
		$.get('UserServlet', {'action' : 'loggedInUserRole'}, function(data) {
			console.log(data.status);
			if (data.status == 'unauthenticated') {
				$('#btnLogout').remove();
				$('#btnAccount').remove();
				$('#btnUsers').remove();
				$('#btnTickets').remove();
				$('#btnLogin').show();
				$('#btnRegister').show();
				
				return;
			}
			if (data.status == 'success') {
				$('#btnRegister').hide();
				$('#btnLogin').hide();
				console.log(data.loggedInUserRole);
				
				if (data.loggedInUserRole == 'ADMIN') {
					navBtn.append(btnUsers);
					$('#mySection').append(btnAdd);
					
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
		btnAdd = $('<button id = "btnAdd" type="button" class="btn btn-dark">ADD MOVIE</li>').on('click', function(){
			window.location.replace('addMovie.html');
			return;
		});
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
				window.location.replace('movies.html');
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
				window.location.replace('movies.html');
			}else{
				alert(data.message);
			}
		});
		
	});
	
	
	
	function getMovies(){
		var movieFilter = movieInput.val();
		var genreFilter = genreInput.val();
		var minDurationFilter = minDurationInput.val();
		var maxDurationFilter = maxDurationInput.val();
		var distributorFilter = distributorInput.val();
		var countryFilter = countryInput.val();
		var fromYearFilter = fromYearInput.val();
		var toYearFilter = toYearInput.val();
		
		
		var params = {
			'movieFilter' : movieFilter,
			'genreFilter' : genreFilter,
			'minDurationFilter' : minDurationFilter,
			'maxDurationFilter' : maxDurationFilter,
			'distributorFilter' : distributorFilter,
			'countryFilter' : countryFilter,
			'fromYearFilter' : fromYearFilter,
			'toYearFilter' : toYearFilter		
		};
		
	
		console.log(params);
		$.get('MovieServlet', params, function(data){
			var movies = data.movies;
			console.log(movies);
			if (data.status == 'success') {
				tblMovies.find('tbody').remove(); 
				for(m in movies){			
					tblMovies.append(
						'<tbody>' +
						'<tr>' +
							'<td><a href="movie.html?id=' + movies[m].id + '">'+ movies[m].name +'</a></td>' +
							'<td>' + movies[m].genre + '</a></td>' +
							'<td>' + movies[m].duration + '</td>' +
							'<td>' + movies[m].distributor + '</td>' +
							'<td>' + movies[m].originCountry + '</td>' +
							'<td>' + movies[m].year + '</td>' +
						'</tr>' +
						'<tbody>'
					);
				}
			}else {
				for(m in movies){
					console.log(movies);
					console.log('nesto ne valja');
				}
			}
		});
	}
	
	$("#searchBtn").click(function (e){
    	getMovies();
        event.preventDefault();
        return false;
    });
    
    
});