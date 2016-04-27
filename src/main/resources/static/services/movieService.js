/**
 *  (some) CRUD operations for movies
 */
angular.module('filmr').factory('MovieService', function($resource,$rootScope) {
	var moviesBaseUrl = $rootScope.API_baseUrl + "movies/";
	return $resource(moviesBaseUrl);
// end of service		
});
