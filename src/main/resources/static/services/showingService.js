/**
 * CRUD operations for Showings
 */
angular.module('filmr').factory('ShowingService', ['$resource', '$rootScope', function($resource, $rootScope){
    var showingBaseUrl = $rootScope.API_baseUrl + "showings/";
    return $resource(showingBaseUrl+":id", { id: '@id' }, {
        update: {method: 'PUT'},
    });

    //End of service
}]);

angular.module('filmr').factory('ScheduleService', ['$resource', '$rootScope', function($resource, $rootScope) {
	var showingBaseUrl = $rootScope.API_baseUrl + "showings/schedule";
	return $resource(showingBaseUrl + ":id", {id: '@id'}, {
		query: {method: 'GET', isArray: false}

	});

}]);