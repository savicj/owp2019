$(document).ready(function() { 
	
	
	//nav
	var navBtn = $('#navBtn');
	var btnLogout;
	var btnAccount;
	var btnUsers;
	var btnTickets;
	var btnMovies;

	//tbl
	var tblTickets = $('#tblTickets');
	var userInput = $('#userInput');
	var projectionInput = $('#projectionInput');
	var ticketInput = $('#ticketInput');
	
	var btnAdd;
	

	makeButtons();
	changeInterface();
	getTickets();
	
	function changeInterface(){
		$.get('UserServlet', {'action' : 'loggedInUserRole'}, function(data) {
			console.log(data.status);
			if (data.status == 'unauthenticated') {
				window.location.replace("projections.html");
				
				return;
			}
			if (data.status == 'success') {
				$('#btnRegister').hide();
				$('#btnLogin').hide();
				console.log(data.loggedInUserRole);
				
				if (data.loggedInUserRole == 'ADMIN') {
					navBtn.append(btnUsers);
					navBtn.append(btnAccount);
					navBtn.append(btnTickets);
					navBtn.append(btnLogout);
					$('#mySection').append(btnAdd);

					
				}
				if(data.loggedInUserRole == 'USER')
					window.location.replace("projections.html");
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
		
		
		btnAccount = $('<li id="btnAccount"><a class="nav-link" href="">ACCOUNT</a></li>').on('click', function(event){
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
		btnAdd = $('<button id = "btnAdd" type="button" class="btn btn-dark">ADD TICKET</li>').on('click', function(){
			window.location.replace('addTicket.html');
			return;
			});
	}
	
	

	
	function getTickets(){

		var projectionFilter  = projectionInput.val();
		var userFilter = userInput.val();
		
		var params = {
			'user' : userFilter,
			'projection' : projectionFilter
		};
		
		$.get('TicketServlet', params, function(data){
			var tickets = data.tickets;
			console.log(tickets);
			if (data.status == 'success') {
				tblTickets.find('tbody').remove(); 
				for(t in tickets){
					tblTickets.append(
						'<tbody>' +
						'<tr>' +
							'<td><a href="projection.html?id=' + tickets[t].projection.id + '">'+ tickets[t].projection.id + '</a></td>' +
							'<td><a href="ticket.html?id=' + tickets[t].id + '">'+ dateFormat(new Date(tickets[t].date)) +'</a></td>' +
							'<td><a href="user.html?id=' + tickets[t].user.id + '">'+ tickets[t].user.username +'</a></td>' +
						'</tr>' +
						'<tbody>'
					);
				}
			}
		});
	}
	
	
	
	
	$("#searchBtn").click(function (e){
    	getTickets();
        event.preventDefault();
        return false;
    });
    
    function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2)
     
        return dateString;
    }
	
	
	
	
});