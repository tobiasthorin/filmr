/**
 *  (some) CRUD operations for booking
 */

angular.module('filmr')
	.service('TheaterService', ['$http','$rootScope', function($http, $rootScope) {

		
		var theatersBaseUrl = $rootScope.API_baseUrl + "theaters/";
		
		this.getAllTheaters = function() {

			return $http({ 

					"url": theatersBaseUrl,
					"params": {
							   
						}
			}
			); // 
		};
		
		
		
		
		
		
		
// end of service		
}]);
