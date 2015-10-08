/**
 * Navigation Controller
 */
function Navigation($rootScope, $scope, $http, $location, $route) {
	$scope.credentials = {};

	$scope.tab = function(route) {
		return $location.path().startsWith('/' + route);
	};

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
		$http.post('logout', {}).finally(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		});
	}

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
}
