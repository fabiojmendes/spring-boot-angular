var hello = angular.module('hello', [ 'ngRoute', 'ngResource' ]);

hello.config(function($routeProvider, $httpProvider) {

	$routeProvider
		.when('/', {
			templateUrl : 'home.html',
			controller : 'home'
		})
		.when('/login', {
			templateUrl : 'login.html',
			controller : 'navigation'
		})
		.when('/add', {
			templateUrl : 'form.html',
			controller : 'add'
		})
		.when('/edit/:resId', {
			templateUrl : 'form.html',
			controller : 'edit'
		})
		.when('/delete/:resId', {
			template : 'none',
			controller : 'delete'
		})
		.otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

});

hello.controller('navigation',  function($rootScope, $scope, $http, $location, $route) {
	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	var authenticate = function(credentials, callback) {
		var headers = credentials ? { authorization : "Basic " + btoa(credentials.username + ":" + credentials.password) } : {};

		$http.get('user', {	headers : headers })
			.success(function(data) {
				if (data.name) {
					$rootScope.authenticated = true;
				} else {
					$rootScope.authenticated = false;
				}
				callback && callback($rootScope.authenticated);
			})
			.error(function() {
				$rootScope.authenticated = false;
				callback && callback(false);
			});
	}

	authenticate();

	$scope.credentials = {};
	$scope.login = function() {
		authenticate($scope.credentials, function(authenticated) {
			if (authenticated) {
				$location.path("/");
				$scope.error = false;
				$rootScope.authenticated = true;
			} else {
				$location.path("/login");
				$scope.error = true;
				$rootScope.authenticated = false;
			}
		})
	};

	$scope.logout = function() {
		$http.post('logout', {}).success(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			$rootScope.authenticated = false;
		});
	}
});

hello.controller('home', function($scope, $resource) {
	var Resource = $resource('/resource')
	Resource.query(function(resources) {
		$scope.resources = resources;
	})
});

hello.controller('edit', function($scope, $resource, $routeParams) {
	var Resource = $resource('/resource/:id', {id: '@id'}, {
		update: {method: 'PUT'}
	});

	Resource.get({id: $routeParams.resId}, function(res) {
		$scope.res = res;
	});

	$scope.save = function() {
		$scope.res.$update();
	};
});

hello.controller('add', function($scope, $resource, $routeParams, $location) {
	var Resource = $resource('/resource');

	$scope.res = new Resource();

	$scope.save = function() {
		$scope.res.$save(function(res) {
			$location.path("/");
		});
	};
});

hello.controller('delete', function($scope, $resource, $routeParams, $location) {
	$resource('/resource/:id').delete({id: $routeParams.resId}, function() {
		$location.path("/");
	});
});
