/**
 *  (some) CRUD operations for cinemas
 */
angular.module('filmr').factory('CinemaService', function($resource,$rootScope) {
	var cinemasBaseUrl = $rootScope.API_baseUrl + "cinemas/";
	return $resource(cinemasBaseUrl + ":id");
// end of service		
});
