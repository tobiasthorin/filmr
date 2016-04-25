/**
 *  (some) CRUD operations for booking
 */

angular.module('filmr')
	.service('MovieService', ['$http','$rootScope', function($http, $rootScope) {

		
		var moviesBaseUrl = $rootScope.API_baseUrl + "movies/";
		
	this.getAllMovies = function(){
		
			return $http({ 

					"url": moviesBaseUrl,
					"params": {
							 
						}
			}
			); // 
		};
		
		
		
		
		
		
		
// end of service		
}]);
