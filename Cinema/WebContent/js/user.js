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
	var usernameInput = $('#usernameInput');
	var passwordInput = $('#passwordInput');
	var roleSelect = $('#roleSelect');
	var dateInput = $('#dateInput');
	var tblTicket = $('#tblTicket');
	

	makeButtons();
	changeInterface();
	getUser();
	getTickets();
	
	
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
	
	
	
	
	
	
	function getUser(){
		var params = {
            'id' : id,
            //'action' : "loggedInUserRole"
        };

        $.get('UserServlet', params, function(data){

            if (data.status == 'success') {
                user = data.user;

                $(usernameInput).val(user.username).trigger("change");
                $(dateInput).val(dateFormat(new Date(user.registrationDate))).trigger("change");    
                $(roleSelect).val(user.role).trigger("change");       
               
            }
	});

	}
	$('#btnUpdate').on('click', function(event) {
		$.ajaxSetup({async: false});
		 
		params = {
			'action': "update",
			'id': id, 
			'username': $('#usernameInput').val(), 
			'role' : $('#roleSelect').val()
		};
		console.log(params);
		
		$.post('UserServlet', params, function(data) {

			if (data.status == 'success') {
				alert('Update successful');
				window.location.replace('users.html');
				return;
			}else
				alert("Update error");
		});

		event.preventDefault();
		return false;
});

	
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
	
	function getTickets(){
		$.get('TicketServlet', {	"userID" : id	}, function(data){
			if(data.status == 'success'){
				tblTicket.find('tbody').remove();
				ticket = data.tickets;
				for(t in ticket){
					tblTicket.append(
						'<tbody>' +
						'<tr>' +
							'<td><a href="ticket.html?id=' + ticket[t].id + '">'+ dateformat(new Date(ticket[t].date)) +'</a></td>' +
						'</tr>' +
						'<tbody>'
					);
				}
			}
		});
	}
	
	function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + "T" + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2);
     
        return dateString;
    }
	function dateformat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2);
     
        return dateString;
    }
	
	
	
	
});