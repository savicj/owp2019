$(document).ready(function() { 
	

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
    //getTicket();
    getSeats()
	
	
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
                    navBtn.append(btnAccount);
                    navBtn.append(btnTickets);
                    navBtn.append(btnLogout);
				}
				if (data.loggedInUserRole == 'USER'){
                    window.location.replace('projections.html');
                }
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
	
	
    
    function getSeats(){
        $.get('HallServlet', {'projection' : "1"} , function(data){
            var hall = data.hall;
            console.log(hall);
            $.get('SeatServlet', {'hall' : hall.name}, function(data){
                var seats = data.seats;
                console.log(seats);
                for(s in seats){
                    while(seats[s].num.includes("A"))
                    $('#row1.seats').append(
                    	'<li class="seat">' + 
                        '<input type="checkbox" id="1" />'+
                        '<label class="seatLabel" for="1">' + seats[s].num + '</label>' +
                    '</li>'
                    );
                }
            });
        });
    }
	
	function dateFormat(date){
        let dateString = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + "T" + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2);
     
        return dateString;
    }
	
	
	
	
});