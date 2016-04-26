/**
 *  (some) CRUD operations for movies
 * TODO: MOVE TO WHERE A MOVIE SERVICE SHOULD BE FOUND
 */

angular.module('filmr')
	.service('MovieService', ['$http','$rootScope', function($http, $rootScope) {

	
		var moviesBaseUrl = $rootScope.API_baseUrl + "movies/";
		
		this.getAllMovies = function() {	
			return $http().get(moviesBaseUrl);
		}
	); 
};
	
// end of service		
}]);
