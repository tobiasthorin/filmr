/**

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

*/
angular.module('filmr').factory('MovieServicee', function($resource) {
  return $resource("/filmr/api/movies/");
});
