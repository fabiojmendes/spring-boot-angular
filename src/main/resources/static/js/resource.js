var App = angular.module('app.resource', []);

/**
 * ResourceList Controller
 */
App.controller('ResourceList', function($scope, $location, Resource) {
	$scope.remove = function(res) {
		Resource.delete({id: res.id}, function() {
			$scope.resources = $scope.resources.filter(function(e) { return e.id != res.id });
		});
	};

	Resource.query(function(resources) {
		$scope.resources = resources;
	});
});

/**
 * ResourceAdd Controller
 */
App.controller('ResourceAdd', function($scope, $location, Resource) {
	$scope.save = function() {
		$scope.res.$save(function(res) {
			$location.path("/resource/list");
		});
	};

	$scope.res = new Resource();
});

/**
 * ResourceEdit Controller
 */
App.controller('ResourceEdit', function($scope, $routeParams, Resource) {
	$scope.save = function() {
		$scope.res.$update();
	};

	Resource.get({id: $routeParams.id}, function(res) {
		$scope.res = res;
	});
});
