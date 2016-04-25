angular.module('filmr')
.controller('bookingController', ['$window','$rootScope', '$scope', '$routeParams', 'BookingService', function($window,$rootScope, $scope, $routeParams, BookingService) {

	$scope.relevantShowings = [];
	$scope.movies = [];
	$scope.relevantDates = [];
	$scope.id = $routeParams.id;
	

	//TODO: Split these two logic (show many showings and book confirm paga) into own controllers.
 
	// run this function as soon as page/view loads
	getAllRelevantShowings(null,null,null,null);
	getCurrentShowingToBookIfSet();

	//---

	
	// functions on $scope object will be available to pages/templates (html) that that use this controller (see routing in app.js)
	$scope.updateAvailableShowings = function(onlyForMovieWithId,date) {
		getAllRelevantShowings(date,date,null,onlyForMovieWithId);
	}
	
	$scope.functionForBtnClick = function() {
		alert("clicked");
	}
	


	$scope.goToConfirm = function(id) {
		$window.location.href="/filmr/#/book/confirm/"+id;
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


	function getCurrentShowingToBookIfSet() {
		$scope.showing = BookingService.getShowing($routeParams.id).then(
			function(response) {
				$scope.showing = response.data;
			}
		);


		console.log("----");
	}

	
	// "private" functions. not visible on $scope.  
	function getAllRelevantShowings(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId) {
		
		console.log("fetching relevant showings based on selected options");
		
		// TODO: gather relevant variables to limit showings:  fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId
		
		BookingService.getAllRelevantShowings(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId).then(
				// on success
				function(response) {
					
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
					for(i=20; i<31; i++) {
						var color = "#eee";
						if(isDateFoundInShowings(i,showingsArray)) color = "#4e4";
						$scope.relevantDates.push({"day":i,"color":color,"date":new Date("2016-04-"+i)});
					}

					$scope.relevantShowings = showingsArray;
					
				}, // on error
				function(error) {
					console.log("error!", error);
				}
		);		
	}

}]);
