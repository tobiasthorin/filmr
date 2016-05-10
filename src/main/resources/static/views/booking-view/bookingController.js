angular.module('filmr')
.controller('bookingController', ['$location','$rootScope', '$scope', '$routeParams', 'BookingService',
    function($location,$rootScope, $scope, $routeParams, BookingService) {

	$scope.relevantShowings = [];
	$scope.movies = [];
	$scope.relevantDates = [];

	

	// run this function as soon as page/view loads
	getAllRelevantShowings();

	
	// functions on $scope object will be available to pages/templates (html) that that use this controller (see routing in app.js)
	$scope.updateAvailableShowings = function(onlyForMovieWithId,date) {
        console.log("---");
        console.log("BookingController: updateAvailableShowings");

        var fromDate = new Date(date);
        var toDate = new Date(date);

        fromDate.setHours(0);
        fromDate.setMinutes(0);
        toDate.setHours(23);
        toDate.setMinutes(59);
		getAllRelevantShowings(fromDate,toDate,undefined,onlyForMovieWithId);
	}
		


	$scope.goToConfirm = function(id) {
		$location.path("/booking_confirm/"+id);
	}

	function isDateFoundInShowings(date,showingsArray) {
		
		for(var i=0; i<showingsArray.length; i++) {
			showing = showingsArray[i];
			var dateCompare = new Date(showing.showDateTime);
			if(dateCompare.getDate()==date) {
				return true;
			}
		}
		return false;
	}




	
	// "private" functions. not visible on $scope.  
	function getAllRelevantShowings(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId) {
		
		console.log("...");
		console.log("BookingController: getAllRelevantShowings()");
		console.log("fetching relevant showings based on selected options");
		
		BookingService.getAllRelevantShowings(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId, true).then(
				// on success
				function(response) {
					
					console.log(response.data);

					// new meta data in response
					var distinctMovieArray = JSON.parse(response.headers().distinct_movies);
					console.log("distinct movies", distinctMovieArray);
					
					var showingsArray = response.data;

					$scope.movies = [];
					for(i=0; i<distinctMovieArray.length; i++) {
						var movie = distinctMovieArray[i];
						$scope.movies.push(movie);
					}

					$scope.relevantDates = [];
					for(i=9; i<20; i++) {
						var color = "#eee";
						if(isDateFoundInShowings(i,showingsArray)) color = "#4e4";
						$scope.relevantDates.push({"day":i,"color":color,"date":new Date("2016-05-"+i)});
					}

					$scope.relevantShowings = showingsArray;
					
				}, // on error
				function(error) {
					console.log("error!", error);
				}
		);		
	}

}]);
