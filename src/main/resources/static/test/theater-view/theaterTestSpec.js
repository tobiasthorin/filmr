'use strict';

describe("cinemaController.js", function () {

    //Specify module
    beforeEach(module('filmr'));

    // Variables and parameters
    var fetchedTheater;
    var MockedTheaterService;
	var seat;
    var $scope;
	var $rootScope = {};

    var $controller;

    var $routeParams = {};

    //Mocks
    beforeEach(function () {

        $routeParams = {
            'cinema_id': 1,
            'theater_id': 2,
            'width': 10,
            'depth': 7
        };
	    $rootScope.errorHandler = function(){
		    console.log("Error!");
	    };
	    seat = {state: "ENABLED"}

        MockedTheaterService = {
            'get': function (params) {
                return {
                    '$promise': {
                        then: function (success, fail) {

                            fetchedTheater = {
                                id: params.id,
                                name: 'testTheater',
                                disabled: false,
                                cinema: {id: 1},
                                rows: [
                                    {
                                        seats: [
                                            {seatName: 1},
                                            {seatName: 2},
                                            {seatName: 3}

                                        ]
                                    },
                                    {
                                        seats: [
                                            {seatName: 4},
                                            {seatName: 5},
                                            {seatName: 6}

                                        ]
                                    },
                                    {
                                        seats: [
                                            {seatName: 7},
                                            {seatName: 8},
                                            {seatName: 9}

                                        ]
                                    },
                                    {
                                        seats: [
                                            {seatName: 7},
                                            {seatName: 8},
                                            {seatName: 9}

                                        ]
                                    }
                                ]
                            };
                            success(fetchedTheater);
                        }
                    }
                }
            },
            'update': function (params) {
                return {
                    '$promise': {
                        then: function (success, fail) {
                            if (params.id && params.name && params.numberOfSeats && params.cinema) {
                                success();
                            } else {
                                fail();
                            }
                        }
                    }
                }
            },
	        'save': function (params) {
		        return {
			        '$promise': {
				        then: function (success, fail) {
					        if (params.name && params.numberOfSeats && params.cinema) {
						        success(fetchedTheater);
					        } else {
						        fail();
					        }
				        }
			        }
		        }
	        }
        };


    });

    //Injections, defining which mocked services to use instead of the real ones
    beforeEach(inject(function (_$controller_) {
        $controller = _$controller_;
        $scope = {};


        $controller('theaterController', {
            $scope: $scope, $routeParams: $routeParams,
            TheaterService: MockedTheaterService,
	        $rootScope: $rootScope
        });
    }));

    /* Test specifications */

    it("Fetches the theater specified in the route", function () {
        if ($routeParams.theater_id) {
            expect(fetchedTheater.id).toEqual($routeParams.theater_id);
        }
    });

    describe("Sets the scope variables to the content of the fetched theater", function () {
        it("Sets the original theater name", function () {
            expect($scope.original_name).toEqual(fetchedTheater.name);
        });

        it("Sets the theater", function () {
            expect($scope.currentTheater).toEqual(fetchedTheater);
        });
    });



    describe("Height and with variables", function () {
        it("Sets the width and height variables when a theater id is specified", function () {
            expect($scope.theaterDepth).toEqual(fetchedTheater.rows.length);
            expect($scope.theaterWidth).toEqual(fetchedTheater.rows[0].seats.length);
        });

        it("Sets the width and height variables when a theater id is NOT specified", function () {
            $routeParams.theater_id = 0;
            $scope.newTheater();
            expect($scope.theaterDepth).toEqual($routeParams.depth);
            expect($scope.theaterWidth).toEqual($routeParams.width);
        });

        it("Sets the width and height variables when a theater id is NOT specified, nor are the route parameters", function () {
            $routeParams.theater_id = 0;
            $routeParams.depth = 0;
            $routeParams.width = 0;
            $scope.newTheater();
            expect($scope.theaterDepth).toEqual($scope.defaultDepth);
            expect($scope.theaterWidth).toEqual($scope.defaultWidth);
        });
    });

	describe("Save theater", function(){
		it("Saves a theater with name", function(){
			expect($scope.currentTheater).toEqual(fetchedTheater);
		});
	});

	describe("Updating seat state", function(){
		it("Enable to disable", function(){
			$scope.toggleSeatState(seat);
			expect(seat.state).toEqual("DISABLED");
		});
		it("Disabled to Not_A_Seat", function(){
			$scope.toggleSeatState(seat);
			$scope.toggleSeatState(seat);
			expect(seat.state).toEqual("NOT_A_SEAT");
		});
		it("Not_A_Seat to Enables", function(){
			$scope.toggleSeatState(seat);
			$scope.toggleSeatState(seat);
			$scope.toggleSeatState(seat);
			expect(seat.state).toEqual("ENABLED");
		});

	})


});
