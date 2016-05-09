/**
 *  (some) CRUD operations for booking
 */

angular.module('filmr')
	.service('BookingService', ['$http','$rootScope', function($http, $rootScope) {

		
		var showingsBaseUrl = $rootScope.API_baseUrl + "showings/";
		
		this.getAllRelevantShowings = function(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId,include_distinct_movies_in_header) {
			
			// set default values if all values where not provided. TODO: use typeof undefined
			var fromDate = fromDate ? fromDate : "";
			var toDate = toDate ? toDate : "";
            var include_distinct_movies_in_header = include_distinct_movies_in_header ? include_distinct_movies_in_header : false;
			var mininumAvailableTickets = mininumAvailableTickets || 2;
			var onlyForMovieWithId = onlyForMovieWithId ? onlyForMovieWithId : ""; //TODO: this is kind of hack, we should fix so paramters is not send if not set instead of empty string
			

            var params = {
					"only_for_movie_with_id":onlyForMovieWithId,
					"from_date":fromDate,
					"to_date":fromDate,
                    "include_distinct_movies_in_header":include_distinct_movies_in_header
			};

			return $http({ 
				"url": showingsBaseUrl,
				"params": params
			}
			); 
		};

		this.createShowing = function(showing){
			return $http.post(showingsBaseUrl, showing);
		}
		

		this.getShowing = function(id) {

			var url = showingsBaseUrl+id;
			console.log(url);
			return $http.get(url);
		}
		

		
		
// end of service		
}]);
