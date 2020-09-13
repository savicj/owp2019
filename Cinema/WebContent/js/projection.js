$(document).ready(function() { 
	
	var id = window.location.search.slice(1).split('?')[0].split('=')[1];
	console.log(id);
	var projection;
	//nav
	var navBtn = $('#navBtn');
	var btnLogout;
	var btnAccount;
	var btnUsers;
	var btnTickets;
	var btnMovies;

	//tbl
	var movieInput = $('#movieInput');
	var dateInput = $('#dateInput');
	var hallInput = $('#hallInput');
	var priceInput = $('#priceInput');
	var projTypeInput = $('#projectionTypeInput');
	
	

	makeButtons();
	changeInterface();
	getProjection();
		
	
	function changeInterface(){
		$.get('UserServlet', {'action' : 'loggedInUserRole'}, function(data) {
			console.log(data.status);
			if (data.status == 'unauthenticated') {
				$('#btnLogout').remove();
				$('#btnAccount').remove();
				$('#btnUsers').remove();
				$('#btnTickets').remove();
				$('#btnDelete').remove();
				$('#btnUpdate').remove();
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
					$('#btnUpdate').append();
									
				}
				if (data.loggedInUserRole == 'USER')
					$('#btnDelete').remove();
					
				navBtn.append(btnAccount);
				navBtn.append(btnTickets);
				navBtn.append(btnLogout);
				return;
			}
		});
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
					loggedInUserId = data.loggedInUserId;
					let url = 'user.html?id=' + loggedInUserId;
					console.log('user id' + loggedInUserId);
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
				alert('logged in');
				window.location.reload();
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
	
	
	function getProjection(){
		var params = {	
				'projectionId' : id,
		};
		console.log(params);
		
		$.get('ProjectionsServlet', params, function(data) {
			if(data.status == 'success') {
				projection = data.projection;
				console.log(projection);
				switch (projection.projectionType){
					case "twodim":
						projTypeInput.val("2D").trigger("change");
						break;
					case "threedim":
						projTypeInput.val("3D").trigger("change");
						break;
					case "fourdim":
						projTypeInput.val("4D").trigger("change");
						break;	
				}
				
				movieInput.val(projection.movie.name).trigger("change");
				dateInput.val(dateFormat(new Date(projection.datetime))).trigger("change");
				hallInput.val(projection.hall.name).trigger("change");
				priceInput.val(projection.price).trigger("change");	
				
			}			
		});
	
	}
	
	
	$('#btnUpdate').on('click', function(event) {
		var date = new Date(dateInput.val());
		let datetime = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2) + ":" + ("0" + date.getSeconds()).slice(-3);
		console.log(datetime);
		
		$.ajaxSetup({async: false});
		 
		var params = {
			'action' : 'update',
			'id' : id,
			'movie' : movieInput.val(),
			'datetime' : datetime,
			'projectionType' : projTypeInput.val(),
			'hall' : hallInput.val(),
			'price' : priceInput.val()
			
		};

		console.log(params);
		
		$.post('ProjectionsServlet', params, function(data) {
			if(data.status == 'success'){
				alert('Success');
				window.location.replace('projections.html');
			}else{
				alert('Error');
			}
		
		});

		event.preventDefault();
		return false;
	});
	
	
	$('#deleteSubmit').on('click', function(){
		var params = {	'action' : "delete", 'id' : id	};
		$.post('ProjectionsServlet', params, function(data){
			if (data.status == 'success') {
            	alert("Projection deleted");
                window.location.replace("projections.html");
            }else{
            	alert("Error!");
            }
		
		});
	});
	
	/*function getMovies(){
	
		var params = {
			'movieFilter' : '',
			'genreFilter' : '',
			'minDurationFilter' : '1',
			'maxDurationFilter' : '500',
			'distributorFilter' : '',
			'countryFilter' : '',
			'fromYearFilter' : '1900',
			'toYearFilter' : '2020'		
		};
		$.get('MovieServlet', params, function(data) {
			if(data.status == 'success'){
				var movies = data.movies;
				console.log(data.movies);
				for(m in movies){
					movieName = movies[m].name;
					$('#movieSelect').append(`<option value="${movieName}">${movieName}</option>`);
				}
			}
		});
	
	}

	function getHalls(){
		$(hallInput).empty();
		var projType = projTypeInput.val();
		var projTypeFilter;
		switch (projType){
					case "2D":
						projTypeFilter = "twodim";
						break;
					case "3D":
						projTypeFilter = "threedim";
						break;
					case "4D":
						projTypeFilter = "fourdim";
						break;	
				}
		var params = {
    		'projTypeFilter' : projTypeFilter,
    	};
		console.log(params);
    	
    	$.get('HallServlet', params, function(data){
    		if(data.status == 'success'){
    			var halls = data.halls;
    			console.log(halls);
    			for(h in halls){
    				var hall = halls[h].name;
    				$('#hallSelect').append(`<option value="${hall}">${hall}</option>`);
    			}
    		}
    	});
	}

	projTypeInput.on('change', function(event){
		getHalls();
		event.preventDefault();
		return false;
	
	});*/
	
	function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + "T" + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2);
     
        return dateString;
    }
	
	
	
	
	
});