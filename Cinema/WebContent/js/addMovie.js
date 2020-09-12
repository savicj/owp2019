$(document).ready(function() { 



	//nav
	var navBtn = $('#navBtn');
	var btnLogout ;
	var btnAccount;
	var btnUsers = $('#btnUsers');
	var btnTickets = $('#btnTickets');
	var btnMovies = $('#btnMovies');

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
	
	

	changeInterface();
	makeButtons();
	
	//$('#genresInput').multiselect();
	
	function changeInterface(){
		$.get('UserServlet', {'action' : 'loggedInUserRole'}, function(data) {
			console.log(data.status);
			if (data.status == 'unauthenticated') {
				window.location.replace("projections.html");
			}
			if(data.loggedInUserRole == 'USER') {
				window.location.replace("projections.html");
			}
			if(data.loggedInUserRole == 'ADMIN'){
				
				navBtn.append(btnAccount);
				navBtn.append(btnLogout);
			}
		});
	}



	
	function makeButtons() {
		btnLogout = $('<li id="btnLogout"><a class="nav-link" href="#">LOGOUT</a></li>').on('click', function(){
			$.get('LogoutServlet', function(data) {
			console.log(data);
			if(data.status == 'unauthenticated'){
				changeInterface();
				return;
			}else {
				console.log('Error');
			}
		});
		$('input').val('');
		window.location.load('projections.html');
		return false;
		});
		
		
		btnAccount = $('<li id="btnAccount"><a class="nav-link" href="#">ACCOUNT</a></li>').on('click', function(){
			let param = { 'action' : "loggedInUserId"};
			$.get('UserServlet', param, function(data){
				console.log(data.status);
				if(data == 'success') {
						let url = 'user.html?id=' + data.loggedInUserId;
						window.location.replace(url);
						return;
				}
			});
		});
		}

		$('#addBtn').on('click', function(event) {
		
		if(movieInput.val() == "" || directorsInput.val() == "" || actorsInput.val() == "" || genresInput.val() == "" || durationInput.val() == ""  || distributorInput.val() == "" || countryInput.val() == "" || yearInput.val() == "" || overviewInput.val() == ""){
			alert('You must fill all fields.')
			return;
		}
		
			
		$.ajaxSetup({async: false});
	
	
		var params = {
			'action' : 'add',
			'movie' : movieInput.val(),
			'directors' : directorsInput.val(),
			'actors' : actorsInput.val(),
			'genres' : genresInput.val(),
			'duration' : durationInput.val(),
			'distributor' : distributorInput.val(),
			'country' : countryInput.val(),
			'year' : yearInput.val(),
			'overview' : overviewInput.val()
			
		};
		
		console.log(params);
		
		$.post('MovieServlet', params, function(data) {
			if(data.status == 'success'){
				alert('Success');
				window.location.replace('movies.html');
			}else{
				alert('Error');
			}
		
		});
	
	});
	
	
	function getGenres(){}
	
	

});