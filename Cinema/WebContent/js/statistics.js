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


	changeInterface();
	makeButtons();
	getTickets();
	
	
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
		btnUsers = $('<li id = "btnUsers" class="margina"><a class="nav-link" href="users.html">USERS</a></li>');
    	btnTickets = $('<li id = "btnTickets" class="margina"><a class="nav-link" href="#">TICKETS</a></li>');
		
	}
	
	
	
	
	
	
	
	var noProjections = 0;
	var totalProjections = 0;
	var noTickets = 0;
	var totalTickets = 0;
	var price = 0;
	var totalPrice = 0;	
	
	function getTickets(){

		$.get('MovieServlet', {'action' : "getAll"}, function(data){
			var movies = data.movies;
			console.log(movies);
			if (data.status == 'success') {
					tblMovies.find('tbody').remove(); 
				for(m in movies){
				var from = new Date();
				var dd = String(from.getDate()).padStart(2, '0');
		    	var mm = String(from.getMonth() + 1).padStart(2, '0');
		    	var yyyy = from.getFullYear();
		    	from = yyyy + '-' + mm + '-' + dd + " 00:00:00";
		    	var to = new Date();
				var dd = String(to.getDate()).padStart(2, '0');
		    	var mm = String(to.getMonth() + 1).padStart(2, '0');
		    	var yyyy = to.getFullYear();
		    	to = yyyy + '-' + mm + '-' + dd + " 23:59:59";
				var params = {
					'movieFilter' : movies[m].id,
					'dateFromFilter' : from,
					'dateToFilter' : to,
					'minPriceFilter' : "",
					'maxPriceFilter' : "",
					'hallFilter' : "",
					'projTypeFilter' : ""			
				};
					$.get('ProjectionsServlet', params, function(data){
						if (data.status == 'success') {
							var projections = data.projections;	
							console.log(projections);
							noProjections = projections.lenght;
							totalProjections += projections.lenght;
						
							for(p in projections){
								$.get('TicketServlet', {'projectionID' : p.id}, function(data){
									if (data.status == 'success') {
										var tickets = data.tickets;
										console.log(tickets);
										noTickets = tickets.lenght;
										totalTickets += tickets.lenght;
										
										for(t in tickets){
											console.log(projections);
											price += tickets[t].projections.price;
											totalPrice += tickets[t].projections.price;
										}
									}
								});
							}
						}
							
					});
					
					tblMovies.append(
						'<tbody>' +
						'<tr>' +
							'<td><a href="movie.html?id=' + movies[m].id + '">'+ movies[m].name +'</a></td>' +
							'<td>' + noProjections + '</a></td>' +
							'<td>' + noTickets + '</td>' +
							'<td>' + price + '</td>' +
						'</tr>' +
						'<tbody>'
					);
							
				}
				$('#tblMovies tr:last').append(
					'<tr>' +
							'<td>TTOTAL</td>' +
							'<td>' + totalProjections + '</a></td>' +
							'<td>' + totalTickets + '</td>' +
							'<td>' + totalPrice + '</td>' +
						'</tr>'
				)
			}
		});
	}
	
	$("#searchBtn").click(function (e){
    	getMovies();
        event.preventDefault();
        return false;
    });
    
    
});