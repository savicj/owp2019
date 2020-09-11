$(document).ready(function() { 
	
	
	//nav
	var navBtn = $('#navBtn');
	var btnLogout;
	var btnAccount;
	var btnUsers;
	var btnTickets;
	var btnMovies;

	//tbl
	var tblProj = $('#tblProj');
	var movieInput = $('#movieInput');
	var dateFromInput = $('#dateFromInput');
	var timeFromInput = $('#timeFromInput');
	var dateToInput = $('#dateToInput');
	var timeToInput = $('#timeToInput');
	var hallInput = $('#hallInput');
	var minPriceInput = $('#minPriceInput');
	var maxPriceInput = $('#maxPriceInput');
	var projType = $('#projType');
	
	var btnAdd;
	

	makeButtons();
	changeInterface();
	getProjections();
	
	
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
		btnAdd = $('<button id = "btnAdd" type="button" class="btn btn-dark">ADD PROJECTION</li>').on('click', function(){
			window.location.replace('addProjection.html');
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
	
	
	
	function getProjections(){
		var movieFilter = movieInput.val();
		var today = new Date();
		var dd = String(today.getDate()).padStart(2, '0');
    	var mm = String(today.getMonth() + 1).padStart(2, '0');
    	var yyyy = today.getFullYear();
    	today = yyyy + '-' + mm + '-' + dd;
		
		if(dateFromInput.val() == "" || timeFromInput.val() == ""){
			var dateFromFilter = today + " 00:00:00.000";
			var dateFrom = new Date(dateFromFilter);
			var timeFrom = dateFrom.getTime();
		}else{
			var dateFromFilter = dateFromInput.val() + " " + timeFromInput.val() + ".000";
			var dateFrom = new Date(dateFromFilter);
			var timeFrom = dateFrom.getTime();
		}
		
		if(dateToInput.val() == "" || timeToInput.val() == ""){
			var dateToFilter = today + " 23:59:59.000";
			var dateTo = new Date(dateToFilter);
			var timeTo = dateTo.getTime();
		}else{
			var dateToFilter = dateToInput.val() + " " + timeToInput.val() + ".000";
			var dateTo = new Date(dateToFilter);
			var timeTo = dateTo.getTime();
		}
		
		var minPriceFilter = minPriceInput.val();
		var maxPriceFilter = maxPriceInput.val();
		var hallFilter = hallInput.val();
		var projTypeFilter = projType.val();
		
		
		var params = {
			'movieFilter' : movieFilter,
			'dateFromFilter' : dateFromFilter,
			'dateToFilter' : dateToFilter,
			'minPriceFilter' : minPriceFilter,
			'maxPriceFilter' : maxPriceFilter,
			'hallFilter' : hallFilter,
			'projTypeFilter' : projTypeFilter			
		};
		
	
		console.log(params);
		$.get('ProjectionsServlet', params, function(data){
			var projections = data.projections;
			console.log(projections);
			if (data.status == 'success') {
				tblProj.find('tbody').remove(); 
				for(p in projections){
				
					/*switch(projections[p].projectionType){
					case "twodim":
						projections[p].projectionType = "2D";
					case "threedim":
						projections[p].projectionType = "3D";
					case "fourdim":
						projections[p].projectionType = "4D";
					}*/
				
					tblProj.append(
						'<tbody>' +
						'<tr>' +
							'<td><a href="movie.html?id=' + projections[p].movie.id + '">'+ projections[p].movie.name +'</a></td>' +
							'<td><a href="projection.html?id=' + projections[p].id + '">'+ dateFormat(new Date(projections[p].datetime)) + '</a></td>' +
							'<td>' + projections[p].projectionType + '</td>' +
							'<td>' + projections[p].hall.name + '</td>' +
							'<td>' + projections[p].price + '</td>' +
						'</tr>' +
						'<tbody>'
					);
				}
			}else {
				for(p in projections){
					console.log(projections);
					console.log('nesto ne valja');
				}
			}
		});
	}
	
	
	
	/*movieInput.on('keyup', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});
	
	dateFromInput.on('keyup', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});
	
	timeFromInput.on('keyup', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});
	
	dateToInput.on('keyup', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});
	
	timeToInput.on('keyup', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});
	
	hallInput.on('change', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});
	
	projType.on('change', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});
	
	minPriceInput.on('keyup', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});
	
	maxPriceInput.on('keyup', function(event){
		getProjections();
		event.preventDefault();
		return false;
	});*/
	
	$("#searchBtn").click(function (e){
    	getProjections();
        event.preventDefault();
        return false;
    });
    
    function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2)
     
        return dateString;
    }
	
	
	
	
	
	
	
	
	
});