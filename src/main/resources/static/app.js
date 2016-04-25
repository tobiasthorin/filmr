// wrapped in a IIFE, good habit. (doensn't pollute global name-space)
(function() {
	var app = angular.module('filmr', ['ngRoute']); // name  [dependencies] , name is referenced in html tag
	

	// ROUTING - what should happen when url changes to path x
	app.config( function( $routeProvider) {

		$routeProvider
		.when('/', {
			title: 'Home',
			activeTab: 'Home',
			templateUrl: 'home-view/home.html',
		})
		.when('/book', {
			title: 'Book',
			activeTab: 'Book',
			templateUrl: 'booking-view/showings.html',
			controller: 'bookingController'
		})
		// booking 
		.when('/book/confirm/:id', {
			title: 'Book',
			activeTab: 'Book',
			templateUrl: 'booking-view/confirm.html', 
			controller: 'bookingController'
				
		})
		// admin
		.when('/admin', {
			title: 'Admin',
			activeTab: 'Admin',
			templateUrl: 'admin-view/admin.html', 
			controller: 'adminController'
				
		}).otherwise({redirectTo: "/"})
	});
	
	
	// middle-ware
	
	// make title names and other functionality accessible for every page, and every controller/service injecting $rootScope
	app.run(['$rootScope', function($rootScope) {
	    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
	        
	    	$rootScope.baseUrl = "/filmr/#/";
	    	$rootScope.API_baseUrl = "/filmr/api/";
	    	
	    	$rootScope.title = current.$$route.title;
	        $rootScope.activeTab = current.$$route.activeTab;  
	        
	    });
	}]);

})(); // run at once

