/**
 *  (some) CRUD operations for cinemas
 */
angular.module('filmr').factory('CinemaService', ['$resource', '$rootScope', function($resource,$rootScope) {
	var cinemasBaseUrl = $rootScope.API_baseUrl + "cinemas/";
	return $resource(cinemasBaseUrl + ":id", { id : '@id' }, {
	update: {method: 'PUT'}
	});
// end of service		
}]);
