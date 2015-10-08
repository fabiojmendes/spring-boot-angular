/**
 * ResourceList Controller
 */
function ResourceList($scope, Resource) {
	$scope.remove = function(res) {
		Resource.delete({id: res.id}, function() {
			$scope.resources = $scope.resources.filter(function(e) { return e.id != res.id });
		});
	};

	Resource.query(function(resources) {
		$scope.resources = resources;
	});
}

/**
 * ResourceAdd Controller
 */
function ResourceAdd($scope, $location, Resource) {
	$scope.save = function() {
		$scope.res.$save(function(res) {
			$location.path("/resource/list");
		});
	};

	$scope.res = new Resource();
}

/**
 * ResourceEdit Controller
 */
function ResourceEdit($scope, $routeParams, Resource) {
	$scope.save = function() {
		$scope.res.$update(function() {
			// Display msg
		});
	};

	Resource.get({id: $routeParams.id}, function(res) {
		$scope.res = res;
	});
}
