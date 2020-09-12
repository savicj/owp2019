$(document).ready(function() { 
	
	
	//nav
	var navBtn = $('#navBtn');
	var btnLogout;
	var btnAccount;
	var btnUsers;
	var btnTickets;
	var btnMovies;

	//tbl
	var userTbl = $('#userTbl');
	var usernameInput = $('#usernameInput');
	var roleInput = $('#roleInput');
	
	var btnAdd;
	

	makeButtons();
	changeInterface();
	getUsers();
	
	
	function changeInterface(){
		$.get('UserServlet', {'action' : 'loggedInUserRole'}, function(data) {
			console.log(data.status);
			if (data.status == 'unauthenticated') {
				window.location.replace('projections.html');
			}
			if (data.status == 'success') {
				
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
    	btnTickets = $('<li id = "btnTickets" class="margina"><a class="nav-link" href="tickets.html">TICKETS</a></li>');
		
	}
	
	
	
	function getUsers(){
		var usernameFilter = usernameInput.val();
		var roleFilter = roleInput.val();
		var params = {
			'action' : "allUsers",
			'usernameFilter' : usernameFilter,
			'roleFilter' : roleFilter
		};
		console.log(params);
		
		$.get('UserServlet', params, function(data){
			var users = data.allUsers;
			console.log(users);
			if (data.status == 'success') {
				userTbl.find('tbody').remove(); 
				for(u in users){
					userTbl.append(
						'<tbody>' +
						'<tr>' +
							'<td><a href="user.html?id=' + users[u].id + '">'+ users[u].username +'</a></td>' +
							'<td>'+ dateFormat(new Date(users[u].registrationDate)) + '</a></td>' +
							'<td>' + users[u].role + '</td>' +
						'</tr>' +
						'<tbody>'
					);
				}
			}else {
				for(u in users){
					console.log(u);
					console.log('nesto ne valja');
				}
			}	
		
		});
	}
	
	
	
	
	function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2)
     
        return dateString;
    }
	
	
});
	