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
		$log.info("---");
		$log.info("BookingController: updateAvailableShowings");

        if(typeof date != "undefined") {
            var fromDate = new Date(date);
            var toDate = new Date(date);

            fromDate.setHours(0);
            fromDate.setMinutes(0);
            toDate.setHours(23);
            toDate.setMinutes(59);
        }
		getAllRelevantShowings(fromDate,toDate,undefined,onlyForMovieWithId);
	}
		

    $scope.clearSearchCriteriaAndUpdateList = function() {
        getAllRelevantShowings();
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
		$log.info("---");
		$log.info("BookingController: getAllRelevantShowings()");
		$log.info("fetching relevant showings based on selected options");
		
		BookingService.getAllRelevantShowings(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId, true).then(
				// on success
				function(response) {
					
					$log.debug(response.data);

					// new meta data in response
					var distinctMovieArray = JSON.parse(response.headers().distinct_movies);
					$log.debug("distinct movies "+distinctMovieArray);
					
					var showingsArray = response.data;

					$scope.movies = [];
					for(i=0; i<distinctMovieArray.length; i++) {
						var movie = distinctMovieArray[i];
						$scope.movies.push(movie);
					}

					$scope.relevantDates = [];
					for (i = 11; i < 23; i++) {
						var color = "#eee";
						if(isDateFoundInShowings(i,showingsArray)) color = "#4e4";
						$scope.relevantDates.push({"day":i,"color":color,"date":new Date("2016-05-"+i)});
					}

					$scope.relevantShowings = showingsArray;
                    $scope.searchCriteria = "Searching on "+fromDate+" - "+toDate+", "+onlyForMovieWithId;
					
				}, // on error
				function(error) {
					$log.error("Error! "+error);
				}
		);		
	}

}]);
