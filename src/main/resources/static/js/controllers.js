var Controllers = angular.module('app.controllers', []);

Controllers.controller('navigation',  function($rootScope, $scope, $http, $location, $route) {
	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	var authenticate = function(credentials, callback) {
		var headers = credentials ? { authorization : "Basic " + btoa(credentials.username + ":" + credentials.password) } : {};

		$http.get('/api/security/user', {	headers : headers })
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

Controllers.controller('home', function($scope, Resource) {
	Resource.query(function(resources) {
		$scope.resources = resources;
	});

	$scope.remove = function(res) {
		Resource.delete({id: res.id}, function() {
			var index = $scope.resources.indexOf(res);
			if (index > -1)	$scope.resources.splice(index, 1);
		});
	};
});

Controllers.controller('edit', function($scope, $routeParams, Resource) {
	Resource.get({id: $routeParams.id}, function(res) {
		$scope.res = res;
	});

	$scope.save = function() {
		$scope.res.$update();
	};
});

Controllers.controller('add', function($scope, $location, Resource) {
	$scope.res = new Resource();

	$scope.save = function() {
		$scope.res.$save(function(res) {
			$location.path("/");
		});
	};
});
