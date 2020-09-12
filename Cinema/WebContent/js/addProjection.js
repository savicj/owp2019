$(document).ready(function() { 



	//nav
	var navBtn = $('#navBtn');
	var btnLogout ;
	var btnAccount;
	var btnUsers = $('#btnUsers');
	var btnTickets = $('#btnTickets');
	var btnMovies = $('#btnMovies');

	//tbl
	var movieInput = $('#movieSelect');
	var dateInput = $('#dateInput');
	var hallInput = $('#hallSelect');
	var priceInput = $('#priceInput');
	var projTypeSelect = $('#projTypeSelect');
	
	

	changeInterface();
	makeButtons();
	
	
	
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
				getMovies();
				getHalls();
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
		
		if(movieInput.val() == "" || projTypeSelect.val() == "" || hallInput.val() == "" || dateInput.val() == "" || priceInput.val() == ""){
			alert('You must fill all fields.')
			return;
		}
		
		var date = new Date(dateInput.val());
		let datetime = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2) + ":" + ("0" + date.getSeconds()).slice(-3);
		console.log(datetime);
        
			
		$.ajaxSetup({async: false});
	
	
		var params = {
			'action' : 'add',
			'movie' : movieInput.val(),
			'datetime' : datetime,
			'projectionType' : projTypeSelect.val(),
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
	
	});



	function getMovies(){
	
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
		var projType = projTypeSelect.val();
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

	projTypeSelect.on('change', function(event){
		getHalls();
		event.preventDefault();
		return false;
	
	});
	

    function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2)
        console.log(dateString);
     
        return dateString;
    }

});