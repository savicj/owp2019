$(document).ready(function() { 
	
	var id = window.location.search.slice(1).split('?')[0].split('=')[1];
	console.log(id);
	var user;
	var loggedInUserRole;
	var loggedInUserId;
	//nav
	var navBtn = $('#navBtn');
	var btnLogout;
	var btnAccount;
	var btnUsers;
	var btnTickets;
	var btnMovies;

	//tbl
	var userInput = $('#userInput');
	var projectionInput = $('#projectionInput')
	var projectionTypeInput = $('#projectionTypeInput');
	var hallInput = $('#hallInput');
	var seatInput = $('#seatInput');
	

	makeButtons();
	changeInterface();
	getTicket();
	
	var btnAdd;

	function changeInterface(){
		$.get('UserServlet', {'action' : 'loggedInUserRole'}, function(data) {
			console.log(data.status);
			if (data.status == 'unauthenticated') {
				window.location.replace('projections.html');
				
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
				$('#btnUpdate').append();
				
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
			$.get('UserServlet', { 'action' : "loggedInUserId"}, function(data){
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
	
	
	
	
	
	
	
	
	$('#deleteSubmit').on('click', function(){
		var params = {	'action' : "delete", 'id' : id	};
		$.post('TicketServlet', params, function(data){
			if (data.status == 'success') {
            	alert("Ticket deleted");
                window.location.replace("tickets.html");
            }else{
            	alert("Error!");
            }
		
		});
	});
	
	function getTicket(){
		$.get('TicketServlet', {	"ticketID" : id	}, function(data){
			
			if (data.status == 'success') {
				ticket = data.ticket;
				console.log(ticket + "\n" + ticket.projection)
				userInput.val(ticket.user.username);
				projectionInput.val(dateFormat(new Date(ticket.projection.date)));
				
				switch (ticket.projection.projectionType){
					case "twodim":
						projectionTypeInput.val("2D").trigger("change");
						break;
					case "threedim":
						projectionTypeInput.val("3D").trigger("change");
						break;
					case "fourdim":
						projectionTypeInput.val("4D").trigger("change");
						break;	
				}
			
				hallInput.val(ticket.projection.hall);
				seatInput.val(ticket.seat);
				priceInput.val(ticket.projection.price);

			}
		});
			
	}
	
	function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + "T" + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2);
     
        return dateString;
    }
	
	
	
	
});