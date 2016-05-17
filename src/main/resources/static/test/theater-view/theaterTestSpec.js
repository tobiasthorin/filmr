'use strict';

describe("cinemaController.js", function () {

    //Specify module
    beforeEach(module('filmr'));

    // Variables and parameters
    var fetchedTheater;
    var MockedTheaterService;
    var $scope;
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
            }
        };


    });

    //Injections, defining which mocked services to use instead of the real ones
    beforeEach(inject(function (_$controller_) {
        $controller = _$controller_;
        $scope = {};

        $controller('theaterController', {
            $scope: $scope, $routeParams: $routeParams,
            TheaterService: MockedTheaterService
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


});
