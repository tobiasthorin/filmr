/**
 *  (some) CRUD operations for booking
 */

angular.module('filmr')
	.service('BookingService', ['$http','$rootScope', function($http, $rootScope) {

		
		var showingsBaseUrl = $rootScope.API_baseUrl + "showings/";
		
		this.getAllRelevantShowings = function(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId) {
			
			// set default values if all values where not provided
			var fromDate = fromDate ? fromDate : "";
			var toDate = toDate ? toDate : "";
			var mininumAvailableTickets = mininumAvailableTickets || 2;
			var onlyForMovieWithId = onlyForMovieWithId ? onlyForMovieWithId : ""; //TODO: this is kind of hack, we should fix so paramters is not send if not set instead of empty string
			
			return $http({ 
				"url": showingsBaseUrl,
				"params": {
					"only_for_movie_with_id":onlyForMovieWithId,
					"from_date":fromDate,
					"to_date":fromDate
				}
			}
			); 
		};
		
		
		
		
		
		
		
// end of service		
}]);
